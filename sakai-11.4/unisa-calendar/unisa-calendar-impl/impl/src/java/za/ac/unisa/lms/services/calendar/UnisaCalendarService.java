package za.ac.unisa.lms.services.calendar;

// import
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.sakaiproject.calendar.api.Calendar;
import org.sakaiproject.calendar.api.CalendarEdit;
import org.sakaiproject.calendar.api.CalendarEvent;
import org.sakaiproject.calendar.api.CalendarEventEdit;
import org.sakaiproject.calendar.impl.BaseCalendarService;
import org.sakaiproject.db.api.SqlService;
import org.sakaiproject.entity.api.Entity;
import org.sakaiproject.exception.IdUnusedException;
import org.sakaiproject.site.api.Site;
import org.sakaiproject.site.cover.SiteService;
import org.sakaiproject.tool.api.Session;
import org.sakaiproject.tool.cover.SessionManager;
import org.sakaiproject.user.api.User;
import org.sakaiproject.user.api.UserNotDefinedException;
import org.sakaiproject.user.cover.UserDirectoryService;
import org.sakaiproject.util.BaseDbDoubleStorage;
//import org.sakaiproject.util.StorageUser;
import org.sakaiproject.util.DoubleStorageUser;


import za.ac.unisa.lms.services.calendar.dao.calendarDateStudentSystemQueryDAO;
import za.ac.unisa.lms.services.calendar.dao.calendarDateSatbookSystemQueryDAO;

/**
* <p>UnisaCalendarService fills out the BaseCalendarService with a database implementation.</p>
* <p>The sql scripts in src/sql/chef_calendar.sql must be run on the database.</p>
*
* @author University of Michigan, CHEF Software Development Team
* @version $Revision: 34 $
*/
public class UnisaCalendarService
	extends BaseCalendarService
{
	
	/** The name of the db table holding calendar calendars. */
	protected String m_cTableName = "CALENDAR_CALENDAR";

	/** The name of the db table holding calendar events. */
	protected String m_rTableName = "CALENDAR_EVENT";

	/** If true, we do our locks in the remote database, otherwise we do them here. */
	protected boolean m_locksInDb = true;
	
//	private final String DEADLINE = "RGVhZGxpbmU=";
//	private final String EXAM = "RXhhbQ==";

	protected static final String[] FIELDS = { "EVENT_START", "EVENT_END" };
	
	private Log log;

	/*******************************************************************************
	* Constructors, Dependencies and their setter methods
	*******************************************************************************/

	/** Dependency: SqlService */
	protected SqlService m_sqlService = null;

	/**
	 * Dependency: SqlService.
	 * @param service The SqlService.
	 */
	public void setSqlService(SqlService service)
	{
		m_sqlService = service;
	}

	/**
	 * Configuration: set the table name for the container.
	 * @param path The table name for the container.
	 */
	public void setContainerTableName(String name)
	{
		m_cTableName = name;
	}

	/**
	 * Configuration: set the table name for the resource.
	 * @param path The table name for the resource.
	 */
	public void setResourceTableName(String name)
	{
		m_rTableName = name;
	}

	/**
	 * Configuration: set the locks-in-db
	 * @param path The storage path.
	 */
	public void setLocksInDb(String value)
	{
		m_locksInDb = new Boolean(value).booleanValue();
	}

	/** Configuration: to run the ddl on init or not. */
	protected boolean m_autoDdl = false;

	/**
	 * Configuration: to run the ddl on init or not.
	 *
	 * @param value
	 *        the auto ddl value.
	 */
	public void setAutoDdl(String value)
	{
		m_autoDdl = new Boolean(value).booleanValue();
	}

	/*******************************************************************************
	* Init and Destroy
	*******************************************************************************/

	/**
	 * Final initialization, once all dependencies are set.
	 */
	public void init()
	{
		try
		{
			// if we are auto-creating our schema, check and createsakai-legacy-component-2.0.1.jar
			if (m_autoDdl)
			{
				m_sqlService.ddl(this.getClass().getClassLoader(), "sakai_calendar");
			}

			super.init();

			//m_logger.info(this +".init(): tables: " + m_cTableName + " " + m_rTableName + " locks-in-db: " + m_locksInDb);
		}
		catch (Throwable t)
		{
			//m_logger.warn(this +".init(): ", t);
		}
		log = LogFactory.getLog(this.getClass());
		log.debug(this+".init()");
	}

	/*******************************************************************************
	* BaseCalendarService extensions
	*******************************************************************************/

	/**
	* Construct a Storage object.
	* @return The new storage object.
	*/
	protected Storage newStorage()
	{
		return new DbStorage(this);

	}	// newStorage

	/*******************************************************************************
	* Storage implementation
	*******************************************************************************/

	/**
	* Covers for the BaseDbStorage, providing Chat parameters
	* Note: base class containers are reference based, this service is still id based - converted here %%%
	*/
	protected class DbStorage
		extends BaseDbDoubleStorage
		implements Storage
	{
		Storage storage= null;
		/**
		* Construct.
		* @param user The StorageUser class to call back for creation of Resource and Edit objects.
		*/
		//Unisa change: in sakai 10 StorageUser is deprecated and replaced with  SingleStorageUser and DoubleStorageUser  https://jira.sakaiproject.org/browse/SAK-21926# 
	//	public DbStorage(StorageUser user)
		public DbStorage(DoubleStorageUser user)
		{
			// TODO: what about owner, draft?
			super(m_cTableName, "CALENDAR_ID", m_rTableName, "EVENT_ID", "CALENDAR_ID",
					"EVENT_START", /* owner, draft, pubview */null, null, null, FIELDS, m_locksInDb, "calendar", "event", user, m_sqlService);

		}	// DbStorage

		/** Calendar **/

		public boolean checkCalendar(String ref) { return super.getContainer(ref) != null; }

		public Calendar getCalendar(String ref) { return (Calendar) super.getContainer(ref); }

		public List getCalendars() { return super.getAllContainers(); }

		public CalendarEdit putCalendar(String ref)
			{ return (CalendarEdit) super.putContainer(ref); }

		public CalendarEdit editCalendar(String ref)
			{ return (CalendarEdit) super.editContainer(ref); }

		public void commitCalendar(CalendarEdit edit)
			{ super.commitContainer(edit); }

		public void cancelCalendar(CalendarEdit edit)
			{ super.cancelContainer(edit); }

		public void removeCalendar(CalendarEdit edit)
			{ super.removeContainer(edit); }

		/** Event **/

		public boolean checkEvent(Calendar calendar, String messageId)
			{ return super.checkResource(calendar, messageId); }

		public CalendarEvent getEvent(Calendar calendar, String id){
			log.debug(this+": getting calendar event id base64: "+id);
			log.debug(this+": getting calendar event id: "+id);


			if(id.startsWith("ss:")) {
				/* get the student number (which is the user_id)
				 */

				Session currentSession =SessionManager.getCurrentSession();
				String studentNr = currentSession.getUserEid();

				String count = id.substring(5,id.lastIndexOf(':'));
				if((id.startsWith("ss:x:"))||(id.startsWith("ss:r:"))) {
					log.debug(this+": this calendar event exists in student system - exam: "+id);
					try {
						return getExamEvent(calendar, id.substring(id.lastIndexOf(':')+1,id.length()), count,studentNr,id.substring(3,4));
					} catch (Exception e) {
//						throw new Exception("UnisaCalendarService: getEvent: getExamEvent: Error occurred: "+ e);
						log.debug(this+" ERROR "+e);
					}
				} else if(id.startsWith("ss:a:")) {
					log.debug(this+": this calendar event exists in student system - assignment: "+id);
					try {
						return getAssignmentEvent(calendar, id.substring(id.lastIndexOf(':')+1,id.length()), count,studentNr);
					} catch (Exception e) {
						//throw new Exception("UnisaCalendarService: getEvent: getAssignmentEvent: Error occurred: "+ e);
						log.debug(this+" ERROR "+e);
					}
				} 
				/* satbook not used anymore 
				 * SY remove 28 Jan 2014
				 * else if (id.startsWith("ss:s")) {
					log.debug(this+": this calendar event exists in satbook system: "+id);
					try {
						return getSatbookEvent(calendar, id.substring(id.lastIndexOf(':')+1,id.length()), count,studentNr);
					} catch (Exception e) {
						//throw new Exception("UnisaCalendarService: getEvent: getAssignmentEvent: Error occurred: "+ e);
						log.debug(this+" ERROR "+e);
					}					
				}*/
			}
			return (CalendarEvent) super.getResource(calendar, id);

		}

		/**
		 * Method: getExamEvent
		 * 		To get a specific exam event.
		 *
		 * @param calendar
		 * @param id = event id
		 * @param count = assignment number (retrieved from id)
		 * @return exam event
		 * @throws Exception
		 * @throws Exception
		 */
		public CalendarEvent getExamEvent(Calendar calendar, String id, String count, String studentNr, String examType) throws Exception {
//			String courseCode = id.substring(0,id.indexOf('-'));
//			String year = id.substring(id.indexOf('-')+1,id.indexOf('-')+3);
//			String semester = id.substring(id.lastIndexOf('-')+1,id.length());

			boolean isStudent = true;


			User user = null;
			if (studentNr != null) {
				try {
					user = UserDirectoryService.getUser(studentNr);
				} catch (UserNotDefinedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//request.setAttribute("user", user);
			}

			if ("Student".equalsIgnoreCase(user.getType())) {
				/*
				 * Student
				 */
				isStudent = true;
			//} else if ("Lecturer".equalsIgnoreCase(user.getType())) {
			} else if ("Instructor".equalsIgnoreCase(user.getType())) {
				/*
				 * Lecturer Get student nr input
				 */
				isStudent = false;
			} else {
				/*
				 * TO DO !!!! User unknown Go to error screen
				 */

				//throw new Exception("Payment : User type unknown");
			}

			calendarDateStudentSystemQueryDAO db = new calendarDateStudentSystemQueryDAO();
			ArrayList examEventList = new ArrayList();
			try {
				examEventList = db.getExamInformationList(studentNr,calendar.getContext(),isStudent);
			} catch (Exception e) {
				throw new Exception("UnisaCalendarService: getExamEvent: db.getExamInformationList: Error occurred: "+ e);
			}

			log.debug("preparing xml for exam event");
			/* read through the assignment list to get specific assignment event (course + assignment number) */
			for (int i=0; i < examEventList.size(); i++) {
				Entity entry = readResource(calendar,examEventList.get(i).toString());
				String tmp = entry.getId();

				// validate course and cycle
				//if (calendar.getContext().equals(tmp.substring(7))) {
					
					// validate exam number and exam type (r= re-exam; x=normal exam)
					if ((tmp.substring(5,id.lastIndexOf(':')).equals(count))&&(tmp.substring(3,4).equals(examType))) {
		                return (CalendarEvent)readResource(calendar, examEventList.get(i).toString());
					} // end if (tmp.substring(5,6).equals(count))
				//} // end if (calendar.getContext().equals(tmp.substring(7))) {
			}	// end for

			log.debug("No match found for assignment event");

			// if we get here no match was found, try single select
			String xml = "";

			log.debug("preparing xml for exam inclusion from db2b");
            return (CalendarEvent)readResource(calendar, xml);
		}


		/**
		 * Method: getAssignmentEvent
		 * 		To get a specific assignment event.
		 *
		 * @param calendar
		 * @param id = event id
		 * @param count = assignment number (retrieved from id)
		 * @return assignment event
		 * @throws Exception
		 */
		public CalendarEvent getAssignmentEvent(Calendar calendar, String id, String count, String studentNr) throws Exception{
			calendarDateStudentSystemQueryDAO db = new calendarDateStudentSystemQueryDAO();
			ArrayList assignmentEventList = new ArrayList();
			try {
				assignmentEventList = db.getAssignmentInformationList(studentNr,calendar.getContext());
				
				for(int i = 0; i < assignmentEventList.size(); i ++){

				}
			} catch (Exception e) {
				throw new Exception("UnisaCalendarService: getAssignmentEvent: db.getAssignmentInformationList: Error occurred: "+ e);
			}

			log.debug("preparing xml for assignment event");
			/* read through the assignment list to get specific assignment event (course + assignment number) */
			for (int i=0; i < assignmentEventList.size(); i++) {
				Entity entry = readResource(calendar,assignmentEventList.get(i).toString());
				String tmp = entry.getId();

				// validate course and cycle
				//if (calendar.getContext().equals(tmp.substring(7))) {
					// validate assignment number
					if (tmp.substring(5,id.lastIndexOf(':')).equals(count)) {
		                return (CalendarEvent)readResource(calendar, assignmentEventList.get(i).toString());
					} // end if (tmp.substring(5,6).equals(count))
				//} // end if (calendar.getContext().equals(tmp.substring(7))) {
			}	// end for

			log.debug("No match found for assignment event");

			// if we get here no match was found, try single select
			String xml = "";
			try {
				xml = db.getAssignmentInformation(studentNr,calendar.getContext(),count);

			} catch (Exception e) {
				throw new Exception("UnisaCalendarService: getAssignmentEvent: db.getAssignmentInformation: Error occurred: "+ e);
			}

			log.debug("preparing xml for assignment inclusion from db2b");
            return (CalendarEvent)readResource(calendar, xml);
		}

		/**
		 * Method: getSatbookEvent
		 * 		To get a specific satbook event.
		 *
		 * @param calendar
		 * @param id = event id
		 * @param count = assignment number (retrieved from id)
		 * @return exam event
		 * @throws Exception
		 * @throws Exception
		 */
		/* satbook not used anymore 
		 * SY remove 28 Jan 2014
		public CalendarEvent getSatbookEvent(Calendar calendar, String id, String count, String studentNr) throws Exception {
//			String courseCode = id.substring(0,id.indexOf('-'));
//			String year = id.substring(id.indexOf('-')+1,id.indexOf('-')+3);
//			String semester = id.substring(id.lastIndexOf('-')+1,id.length());

			boolean isStudent = true;

			User user = null;
			if (studentNr != null) {
				try {
					user = UserDirectoryService.getUserByEid(studentNr);
				} catch (UserNotDefinedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//request.setAttribute("user", user);
			}

			if ("Student".equalsIgnoreCase(user.getType())) {
				/*
				 * Student
				 
				isStudent = true;
			} else if ("Lecturer".equalsIgnoreCase(user.getType())) {
				/*
				 * Lecturer Get student nr input
				 
				isStudent = false;
			} else {
				/*
				 * TO DO !!!! User unknown Go to error screen
				 

				//throw new Exception("Payment : User type unknown");
			}

			calendarDateSatbookSystemQueryDAO db = new calendarDateSatbookSystemQueryDAO();
			ArrayList satbookEventList = new ArrayList();

			try {
				satbookEventList = db.getSatbookInformationList(studentNr,calendar.getContext(),false);
			} catch (Exception e) {
				//throw new Exception("UnisaCalendarService: getEvents: db.getExamInformation: Error occurred: "+ e);
				//System.out.println("UnisaCalendarService: getEvents: db.getSatbookInformation: Error occurred: "+e);
			}

			log.debug("preparing xml for satbook event");
			/* read through the satbook list to get specific satbook event (course + bkngid number) 
			for (int i=0; i < satbookEventList.size(); i++) {
				Entity entry = readResource(calendar,satbookEventList.get(i).toString());
				String tmp = entry.getId();
				String[] temp = null;
				temp = tmp.split(":");
				String tmpCourse = temp[3];
				String tmpId = temp[2];

				// validate course and cycle
				if (calendar.getContext().equals(tmpCourse)) {
					
					// validate satbook number and exam type (r= re-exam; x=normal exam)
					if (tmpId.equals(count)) {
		                return (CalendarEvent)readResource(calendar, satbookEventList.get(i).toString());
					} // end if (tmpId.equals(count))
				} // end if (calendar.getContext().equals(tmp.substring(7))) {
			}	// end for

			log.debug("No match found for satbook event");

			// if we get here no match was found, try single select
			String xml = "";

			log.debug("preparing xml for exam inclusion from db2b");
            return (CalendarEvent)readResource(calendar, xml);
		} */
		
		
		public List getEvents(Calendar calendar)
			{
			List dbList = super.getAllResources(calendar);
			try {
				Site site = SiteService.getSite(calendar.getContext());
				//onlcourse

				if((!site.getType().equalsIgnoreCase("onlcourse"))&&(!site.getType().equals("Course"))&&(!site.getType().equalsIgnoreCase("onlgroup"))&&(!site.getType().equalsIgnoreCase("group"))) {
					//we won't have exams or assignment deadlines if it's not a course site
					return dbList;
				}
			} catch(IdUnusedException e) {
				// should never be thrown, cos sakai must supply a valid sitename
				return dbList;
			} catch(NullPointerException e) {
				return dbList;
			}

			//if we reach this point then it's surely a course site
			// get dates from dao (created dao package in this project) for subject
			// use calendar.getContext() to get for example COS2213-06-Y1
			// strip it down to COS2213 and get the info from database
			// note 2 types; namely EXAM or DEADLINE

			/* get the student number (which is the user_id)
			 */

			Session currentSession = SessionManager.getCurrentSession();
			String studentNr = currentSession.getUserEid();
			boolean isStudent = true;

			User user = null;
			if (studentNr != null) {
				try {
					user = UserDirectoryService.getUserByEid(studentNr);
				} catch (UserNotDefinedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//request.setAttribute("user", user);
			}

			if ("Student".equalsIgnoreCase(user.getType())) {
				/*
				 * Student
				 */
				isStudent = true;
			//} else if ("Lecturer".equalsIgnoreCase(user.getType())) {
			} else if ("Instructor".equalsIgnoreCase(user.getType())) {
				/*
				 * Lecturer Get student nr input
				 */
				isStudent = false;
			} else {
				/*
				 * TO DO !!!! User unknown Go to error screen
				 */

				//throw new Exception("Payment : User type unknown");
			}

				/**
				 * select exam events from database for students
				 */
				calendarDateStudentSystemQueryDAO db = new calendarDateStudentSystemQueryDAO();
				ArrayList examEventList = new ArrayList();
				try {
					examEventList = db.getExamInformationList(studentNr,calendar.getContext(),isStudent);
				} catch (Exception e) {
					//throw new Exception("UnisaCalendarService: getEvents: db.getExamInformation: Error occurred: "+ e);
					//System.out.println("UnisaCalendarService: getEvents: db.getExamInformation: Error occurred: "+e);
				}

				// read through exam the list
				for (int i=0; i <examEventList.size(); i++) {
					Entity entry = readResource(calendar,examEventList.get(i).toString());
	                dbList.add(entry);
				}

				/**
				 * select the assignment events from the database
				 */
				ArrayList assignmentEventList = new ArrayList();
				try {
					assignmentEventList = db.getAssignmentInformationList(studentNr,calendar.getContext());

				} catch (Exception e) {
					//throw new Exception("UnisaCalendarService: getEvents: db.getExamInformation: Error occurred: "+ e);
					//System.out.println("UnisaCalendarService: getEvents: db.getAssignmentInformation: Error occurred: "+e);
				}

				/* read through the assignment list and add the dbList */
				for (int i=0; i < assignmentEventList.size(); i++) {
					Entity entry = readResource(calendar,assignmentEventList.get(i).toString());
					String tmp = entry.getId();
					//if (calendar.getContext().equals(tmp.substring(7))) {

		                dbList.add(entry);
					//}
				}
				
				/**
				 * select the satbook events from the database
				 */
				
				/* satbook not used anymore 
				 * SY remove 28 Jan 2014
				calendarDateSatbookSystemQueryDAO db2 = new calendarDateSatbookSystemQueryDAO();
				ArrayList satbookEventList = new ArrayList();
				try {
					satbookEventList = db2.getSatbookInformationList(studentNr,calendar.getContext(),false);
				} catch (Exception e) {
					//throw new Exception("UnisaCalendarService: getEvents: db.getExamInformation: Error occurred: "+ e);
					//System.out.println("UnisaCalendarService: getEvents: db.getSatbookInformation: Error occurred: "+e);
				}
				/* read through the satbook list and add the dbList 
				for (int i=0; i < satbookEventList.size(); i++) {
					Entity entry = readResource(calendar,satbookEventList.get(i).toString());
					String tmp = entry.getId();
					String[] temp = null;
					temp = tmp.split(":");
					String tmpCourse = temp[3];
					if (calendar.getContext().equals(tmpCourse)) {
		                dbList.add(entry);
					}
				}*/

			log.debug("added resource entry to list returned by getEvents(calendar)");
			return dbList;
		}

		public CalendarEventEdit putEvent(Calendar calendar,String id)
			{ return (CalendarEventEdit) super.putResource(calendar, id, null); }

		public CalendarEventEdit editEvent(Calendar calendar, String messageId)
			{ return (CalendarEventEdit) super.editResource(calendar, messageId); }

		public void commitEvent(Calendar calendar, CalendarEventEdit edit)
			{ super.commitResource(calendar, edit); }

		public void cancelEvent(Calendar calendar, CalendarEventEdit edit)
			{ super.cancelResource(calendar, edit); }

		public void removeEvent(Calendar calendar, CalendarEventEdit edit)
			{ super.removeResource(calendar, edit); }

		public List getEvents(Calendar arg0, long arg1, long arg2) {
			return storage.getEvents(arg0, arg1, arg2);
		}
		
		
		


	}   // DbStorage

}	// DbCachedCalendarService

/**********************************************************************************
*
* $Header: /cvs/sakai2/legacy/component/src/java/org/sakaiproject/component/legacy/calendar/UnisaCalendarService.java,v 1.1 2005/04/13 23:33:36 ggolden.umich.edu Exp $
*
**********************************************************************************/