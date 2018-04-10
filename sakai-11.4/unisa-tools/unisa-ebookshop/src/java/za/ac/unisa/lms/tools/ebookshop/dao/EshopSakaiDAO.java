package za.ac.unisa.lms.tools.ebookshop.dao;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.map.ListOrderedMap;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.sakaiproject.user.cover.UserDirectoryService;
import org.springframework.jdbc.core.JdbcTemplate;

import za.ac.unisa.lms.db.SakaiDAO;
import za.ac.unisa.lms.db.StudentSystemDAO;
import za.ac.unisa.lms.tools.ebookshop.forms.EshopForm;
import org.sakaiproject.db.cover.SqlService;


public class EshopSakaiDAO extends SakaiDAO{

	private Log log;

	public ArrayList getBooksAdvertised(String orderByParam) throws Exception {
		String desc="";
		if(null == orderByParam | orderByParam.equalsIgnoreCase("")) {
			orderByParam = "CATCHPHRASE";
		}
		if(orderByParam.equalsIgnoreCase("SUBMIT_DATE")) {
			desc="DESC";
		}

		log = LogFactory.getLog(this.getClass());
		ArrayList list = new ArrayList();
		EshopStudentSystemDAO studentDAO = new EshopStudentSystemDAO();

		String query = "select " +
			//"RESELLBOOK_ID, CATCHPHRASE, COURSE_CODE, DESCRIPTION, CONTACT, SUBMIT_DATE, CURRENT_DATE, TO_CHAR(CURRENT_DATE - INTERVAL '60' DAY) " +
				"RESELLBOOK_ID, CATCHPHRASE, COURSE_CODE, DESCRIPTION, CONTACT, SUBMIT_DATE, TO_CHAR(CURRENT_DATE - INTERVAL '60' DAY) as CURRENT_DATE " +
				"FROM " +
				"RESELLBOOK_INFO  " +
				"WHERE " +
				"TO_CHAR(SUBMIT_DATE,'YYYY-MM-DD') >= TO_CHAR(CURRENT_DATE - INTERVAL '60' DAY,'YYYY-MM-DD') " +
				"AND TO_CHAR(SUBMIT_DATE,'YYYY-MM-DD') <= TO_CHAR(CURRENT_DATE,'YYYY-MM-DD') ORDER BY " + orderByParam + " " + desc;

		try {
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			log.info("E-Bookshop: Retrieving all book advertisements for the last 6 months - " + query);
			List queryList = jdt.queryForList(query);
			Iterator i = queryList.iterator();
			while(i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				Advert advert = new Advert();
				advert.setBookId(data.get("RESELLBOOK_ID").toString());
				advert.setAddHeading(data.get("CATCHPHRASE").toString());
				//accomodating migration of old data (no course codes)
				try {
					advert.setCourseCode(data.get("COURSE_CODE").toString());
				}catch(NullPointerException npe) {
					advert.setCourseCode("no code");
				}
				advert.setAddText(data.get("DESCRIPTION").toString());
				////accomodating migration of old data (no contact column)
				try {
					advert.setContactDetails(data.get("CONTACT").toString());
				}catch(NullPointerException npe) {
					advert.setContactDetails("See above");
				}
				advert.setDateAdded((data.get("SUBMIT_DATE").toString()).substring(0,10));
				try {
					//advert.setCourseDescription(studentDAO.studyUnitDescription(data.get("COURSE_CODE").toString()));
				}catch(NullPointerException npe) {
					advert.setCourseDescription("no description");
				}
				list.add(advert);
			}
		}catch (Exception ex) {
			throw new Exception("E-Bookshop: Error "+ ex);
		}

		return list;
	}


	public boolean myUnisaAccountExist(String studentNr) throws Exception{
		boolean isMyUnisaStudent = false;

		if(null == studentNr | studentNr.equalsIgnoreCase("")) {
			return false;
		}else {
			log = LogFactory.getLog(this.getClass());
        	String query = "SELECT ACT_STATUS "+
                     "FROM JOIN_ACTIVATION "+
                     "WHERE USER_ID = '"+studentNr +"'";
        	try {
        		log.debug("E-Bookshop: Determine if myUnisa accounts exists: " + query);
        		String actStatus = this.querySingleValue(query,"ACT_STATUS");

        		if((null == actStatus) | actStatus.equals(null)) {
        			isMyUnisaStudent = false;
        		}
        		if (actStatus.equals("Y")) {
        			isMyUnisaStudent = true;
        		} else {
        			isMyUnisaStudent = false;
        		}

        	} catch (Exception ex) {
        		throw new Exception("E-Bookshop myUnisa account enquiry: Error occurred / "+ ex,ex);
        	}
		}
      return isMyUnisaStudent;
    }

	public boolean isAddCreator(String bookId, String studentNumber) throws Exception {
		//log = LogFactory.getLog(this.getClass());
        boolean isAddCreator = false;
        String query = "SELECT STUDENT_ID FROM RESELLBOOK_INFO WHERE RESELLBOOK_ID = " + bookId;

        try {
        	String studentId = this.querySingleValue(query,"STUDENT_ID");
        	if(studentId.equalsIgnoreCase(studentNumber)) {
        		isAddCreator = true;
        	}else {
        		isAddCreator = false;
        	}

		}catch(Exception ex) {
			throw new Exception("E-Bookshop: determining if user created the add" + ex,ex);
		}
		return isAddCreator;
	}

	public boolean isAddCreator(String bookId, String studentNumber, String password) throws Exception {
		//log = LogFactory.getLog(this.getClass());
        boolean isAddCreator = false;
        String query = "SELECT STUDENT_ID FROM RESELLBOOK_INFO WHERE (RESELLBOOK_ID = " + bookId + " AND RESELLBOOK_PASSWORD = '" + password + "')";

        try {
        	String studentId = this.querySingleValue(query,"STUDENT_ID");
        	if(studentId.equalsIgnoreCase(studentNumber)) {
        		isAddCreator = true;
        	}else {
        		isAddCreator = false;
        	}

		}catch(Exception ex) {
			throw new Exception("E-Bookshop: determining if user created the add" + ex,ex);
		}
		return isAddCreator;
	}


	public boolean authenticateMyUnisaUser(String studentNumber, String password) throws Exception {

		String userID = studentNumber;
//		User user = null;
		boolean exists = false;
		if (userID != null) {
//			user = UserDirectoryService.getUserByEid(userID);
			if(null != UserDirectoryService.authenticate(studentNumber,password)) {
				exists = true;
			}else {
				exists = false;
				return exists;
			}
		}

		return exists;
	}

	public Advert populateAdvertObject(String bookId, EshopForm eShopForm) throws Exception {
		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		log = LogFactory.getLog(this.getClass());
		String query = "SELECT * FROM RESELLBOOK_INFO WHERE RESELLBOOK_ID="+bookId;
		Advert advert = null;
		try {
			log.debug("E-Bookshop: Populating the Advert object: " + query);
			List queryList = jdt.queryForList(query);
			Iterator i = queryList.iterator();

			while(i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				advert = new Advert();
				advert.setAddHeading(data.get("CATCHPHRASE").toString());
				advert.setAddText(data.get("DESCRIPTION").toString());
				advert.setBookId(data.get("RESELLBOOK_ID").toString());
				//accomodating migration of old data (no contact column)
				try {
					advert.setContactDetails(data.get("CONTACT").toString());
				}catch(NullPointerException npe) {
					advert.setContactDetails("See above");
				}
				//accomodating migration of old data (no course codes)
				try {
					advert.setCourseCode(data.get("COURSE_CODE").toString());
				}catch(NullPointerException npe) {
					advert.setCourseCode("no code");
				}
				advert.setDateAdded((data.get("SUBMIT_DATE").toString()).substring(0,10));
				eShopForm.setAdvert(advert);
			}

        }catch (Exception ex) {
           throw new Exception("E-Bookshop myUnisa account enquiry: Error occurred / "+ ex,ex);
        }

        return advert;
	}

	public void editAdvert(Advert advert, String bookId) throws Exception {
		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		log = LogFactory.getLog(this.getClass());

		/**
		String query = "UPDATE RESELLBOOK_INFO SET " +
				"CATCHPHRASE='" + (advert.getAddHeading()).replace('\'',' ') + "'" +
				",DESCRIPTION='" + (advert.getAddText()).replace('\'',' ') + "'" +
				",COURSE_CODE='" + advert.getCourseCode().toUpperCase() +  "'" +
				",CONTACT='" + (advert.getContactDetails()).replace('\'',' ') + "' " +
				" WHERE RESELLBOOK_ID= " + bookId ;
		**/

		String query = "UPDATE RESELLBOOK_INFO SET " +
		"CATCHPHRASE=? ,DESCRIPTION=?, COURSE_CODE=?, CONTACT=? WHERE RESELLBOOK_ID= ?" ;



		log.info("Updating a advertisement using the E-bookshop tool" + query);
		try {
			SqlService.dbWrite(query,new Object[]{advert.getAddHeading(), advert.getAddText(),advert.getCourseCode().toUpperCase(),advert.getContactDetails(),bookId});
			//jdt.update(query);
		}catch(Exception ex) {
			throw new Exception("E-Bookshop: insert the updated add: Error occurred / "+ ex, ex);
		}
	}

	public void deleteAdd(String bookId) throws Exception {
		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		log = LogFactory.getLog(this.getClass());
		String query = "DELETE FROM RESELLBOOK_INFO WHERE RESELLBOOK_ID = " + bookId;
		log.info("Deleting a record from the E-bookshop tool: " + query);
		try {
			jdt.update(query);
		}catch(Exception ex) {
			throw new Exception("E-Bookshop: delete add: Error occurred /" + ex,ex);
		}
	}

	public void insertAdd(Advert advert, Student student) throws Exception {
		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		log = LogFactory.getLog(this.getClass());
	/**
		String query="INSERT INTO RESELLBOOK_INFO " +
				"(CATCHPHRASE, DESCRIPTION, SUBMIT_DATE, STUDENT_ID, RESELLBOOK_PASSWORD, COURSE_CODE, CONTACT)" +
				" VALUES " +
				"('" + advert.getAddHeading() + "'," +
				"'" + advert.getAddText() + "'," +
				"NOW()" +
 				",'" + student.getStudentNumber() + "','" +
 				"" + student.getPassword() + "'," +
 				"'" + advert.getCourseCode().toUpperCase() + "',"  +
 				"'" + advert.getContactDetails() + "')";
	**/
		String query="INSERT INTO RESELLBOOK_INFO " +
		"(RESELLBOOK_ID,CATCHPHRASE, DESCRIPTION, SUBMIT_DATE, STUDENT_ID, RESELLBOOK_PASSWORD, COURSE_CODE, CONTACT)" +
		" VALUES (RESELLBOOK_INFO_0.NEXTVAL,?,?,SYSDATE,?,?,?,?)";

		log.info("Inserting a new record into the E-bookshop tool: " + query);

		try {
			SqlService.dbWrite(query,new Object[]{advert.getAddHeading(),advert.getAddText(),student.getStudentNumber(),student.getPassword(),advert.getCourseCode().toUpperCase(),advert.getContactDetails()});
			//jdt.update(query);
		}catch(Exception ex) {
			throw new Exception("E-Bookshop: insert add: Error occurred /" + ex,ex);
		}

	}

	private String querySingleValue(String query, String field){
      JdbcTemplate jdt = new JdbcTemplate(getDataSource());
      List queryList;
      String result = "";

      queryList = jdt.queryForList(query);
      Iterator i = queryList.iterator();
      i = queryList.iterator();
      if (i.hasNext()) {
             ListOrderedMap data = (ListOrderedMap) i.next();
             if (data.get(field) == null){
             } else {
                 result = data.get(field).toString();
             }
      }
      return result;
    }


}
