package za.ac.unisa.lms.tools.auditview.dao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Date;
import java.util.Vector;


import org.apache.commons.collections.map.ListOrderedMap;
import org.sakaiproject.component.cover.ComponentManager;
import org.sakaiproject.event.api.UsageSessionService;
import org.sakaiproject.user.api.UserNotDefinedException;
import org.sakaiproject.user.api.UserDirectoryService;
import org.springframework.jdbc.core.JdbcTemplate;

import za.ac.unisa.lms.db.SakaiDAO;
import za.ac.unisa.lms.tools.auditview.forms.MyUnisaEventLog;

public class AuditViewQueryDAO extends SakaiDAO {
	private UserDirectoryService userDirectoryService;

	public AuditViewQueryDAO() {
		super();
	}

	/**
	 * Method: getAuditEvents
	 *  to select answer (as chosen by student on registration)
	 *  if student want to be included in classlist
	 *
	 * @param studentNr
	 * @throws Exception
	 */
/*
	public ArrayList getAuditrecentEvents(String eid, int start, int length) throws Exception{
		String orCondition = "";
		//Date d =new Date(2007,05,18,12,00);
		//System.out.println("The date of interest is "+d);
		try {
			orCondition = "OR S.SESSION_USER = '"+UserDirectoryService.getUserId(eid)+"' ";
		} catch (UserNotDefinedException unde){
			orCondition = "";
		}
		ArrayList eventList = new ArrayList();
		String sql = "SELECT * FROM SAKAI_SESSION_200707201729 S LEFT OUTER JOIN SAKAI_EVENT_OLD1 E " +
					"ON S.session_id = E.session_id where S.SESSION_USER = '"+eid+"' " +
					orCondition +
					//" AND E.EVENT_DATE > '2007-05-18'"+
							" ORDER by S.SESSION_START DESC, E.EVENT_DATE LIMIT "+start+","+length;
	//	System.out.println(sql);
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List eList = jdt.queryForList(sql);
			Iterator iterator = eList.iterator();
			String previousSessionId = "";
			while(iterator.hasNext()) {
				MyUnisaEventLog eventLog = new MyUnisaEventLog();
				ListOrderedMap h = (ListOrderedMap) iterator.next();
				if(h.get("SESSION_ID") == null) {
					eventLog.setSessionId("");
				} else {
					if(previousSessionId.equals(h.get("SESSION_ID").toString())) {
						eventLog.setSessionId("");
					} else {
						previousSessionId = h.get("SESSION_ID").toString();
						eventLog.setSessionId(previousSessionId);
					}
				}
				//d=new Date();

               // if ( d.after((Date)h.get("SESSION_START") ) & (UserDirectoryService.getUserId(eid).length()>12)){}
               // else
               // {
				eventLog.setSessionServer((h.get("SESSION_SERVER") == null)? "": h.get("SESSION_SERVER").toString());
				eventLog.setSessionUser((h.get("SESSION_USER") == null)? "": h.get("SESSION_USER").toString());
				eventLog.setSessionIp((h.get("SESSION_IP") == null)? "No session info": h.get("SESSION_IP").toString());
				eventLog.setSessionUserAgent((h.get("SESSION_USER_AGENT") == null)? "": h.get("SESSION_USER_AGENT").toString());
				eventLog.setSessionStart((h.get("SESSION_START") == null)? "": h.get("SESSION_START").toString());
				eventLog.setSessionEnd((h.get("SESSION_END") == null)? "": h.get("SESSION_END").toString());
				eventLog.setEventId((h.get("EVENT_ID") == null)? "archived": h.get("EVENT_ID").toString());
				//eventLog.setEventId((h.get("EVENT_ID") == null)? "": h.get("EVENT_ID").toString());
				eventLog.setEventDate((h.get("EVENT_DATE") == null)? "": h.get("EVENT_DATE").toString());
				eventLog.setEvent((h.get("EVENT") == null)? "archived": h.get("EVENT").toString());
				//eventLog.setEvent((h.get("EVENT") == null)? "": h.get("EVENT").toString());
				eventLog.setRef((h.get("REF") == null)? "": h.get("REF").toString());
				eventList.add(eventLog);
				eventLog.setEventCode((h.get("EVENT_CODE") == null)? "NULL": h.get("EVENT_CODE").toString());
			//}
	}
       return eventList;
	}
*/
public ArrayList getAuditArchivedEvents_PrevYear(String eid,String prevYear) throws Exception{
		
		userDirectoryService = (UserDirectoryService) ComponentManager.get(UserDirectoryService.class);
		String orCondition = "";
		
		try {
			orCondition = "OR S.SESSION_USER = '"+userDirectoryService.getUserId(eid)+"' ";
		} catch (UserNotDefinedException unde){
			orCondition = "";
		}
		ArrayList eventList = new ArrayList();
		String sql = "SELECT * FROM SAKAI_SESSION_HISTORY_"+prevYear+" S LEFT OUTER JOIN SAKAI_EVENT_HISTORY_"+prevYear+" E " +
					"ON S.session_id = E.session_id where S.SESSION_USER = '"+eid+"' " +
					orCondition +//" AND E.EVENT_DATE < '2007-05-18'"+
							" ORDER by E.EVENT_DATE DESC";//, E.EVENT_DATE LIMIT "+start+","+length;
		//System.out.println(sql);
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List eList = jdt.queryForList(sql);
			Iterator iterator = eList.iterator();
			String previousSessionId = "";
			while(iterator.hasNext()) {
				MyUnisaEventLog eventLog = new MyUnisaEventLog();
				ListOrderedMap h = (ListOrderedMap) iterator.next();
				if(h.get("SESSION_ID") == null) {
					eventLog.setSessionId("");
				} else {
					if(previousSessionId.equals(h.get("SESSION_ID").toString())) {
						eventLog.setSessionId("");
					} else {
						previousSessionId = h.get("SESSION_ID").toString();
						eventLog.setSessionId(previousSessionId);
					}
				}

				eventLog.setSessionServer((h.get("SESSION_SERVER") == null)? "": h.get("SESSION_SERVER").toString());
				eventLog.setSessionUser((h.get("SESSION_USER") == null)? "": h.get("SESSION_USER").toString());
				eventLog.setSessionIp((h.get("SESSION_IP") == null)? "No session info": h.get("SESSION_IP").toString());
				eventLog.setSessionUserAgent((h.get("SESSION_USER_AGENT") == null)? "": h.get("SESSION_USER_AGENT").toString());
				eventLog.setSessionStart((h.get("SESSION_START") == null)? "": h.get("SESSION_START").toString());
				eventLog.setSessionEnd((h.get("SESSION_END") == null)? "": h.get("SESSION_END").toString());
				eventLog.setEventId((h.get("EVENT_ID") == null)? "archived": h.get("EVENT_ID").toString());
				eventLog.setEventDate((h.get("EVENT_DATE") == null)? "": h.get("EVENT_DATE").toString());
				eventLog.setEvent((h.get("EVENT") == null)? "archived": h.get("EVENT").toString());
				eventLog.setRef((h.get("REF") == null)? "": h.get("REF").toString());
				eventList.add(eventLog);
				eventLog.setEventCode((h.get("EVENT_CODE") == null)? "NULL": h.get("EVENT_CODE").toString());
			}
       return eventList;
	}

	public ArrayList getAuditArchivedEvents(String eid) throws Exception{
		
		userDirectoryService = (UserDirectoryService) ComponentManager.get(UserDirectoryService.class);
		String orCondition = "";
		try {
			orCondition = "OR S.SESSION_USER = '"+userDirectoryService.getUserId(eid)+"' ";
		} catch (UserNotDefinedException unde){
			orCondition = "";
		}
		ArrayList eventList = new ArrayList();
		String sql = "SELECT * FROM SAKAI_SESSION_HISTORY S LEFT OUTER JOIN SAKAI_EVENT_HISTORY E " +
					"ON S.session_id = E.session_id where S.SESSION_USER = '"+eid+"' " +
					orCondition +//" AND E.EVENT_DATE < '2007-05-18'"+
							" ORDER by E.EVENT_DATE desc";//, E.EVENT_DATE LIMIT "+start+","+length;
		//System.out.println(sql);
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List eList = jdt.queryForList(sql);
			Iterator iterator = eList.iterator();
			String previousSessionId = "";
			while(iterator.hasNext()) {
				MyUnisaEventLog eventLog = new MyUnisaEventLog();
				ListOrderedMap h = (ListOrderedMap) iterator.next();
				if(h.get("SESSION_ID") == null) {
					eventLog.setSessionId("");
				} else {
					if(previousSessionId.equals(h.get("SESSION_ID").toString())) {
						eventLog.setSessionId("");
					} else {
						previousSessionId = h.get("SESSION_ID").toString();
						eventLog.setSessionId(previousSessionId);
					}
				}

				eventLog.setSessionServer((h.get("SESSION_SERVER") == null)? "": h.get("SESSION_SERVER").toString());
				eventLog.setSessionUser((h.get("SESSION_USER") == null)? "": h.get("SESSION_USER").toString());
				eventLog.setSessionIp((h.get("SESSION_IP") == null)? "No session info": h.get("SESSION_IP").toString());
				eventLog.setSessionUserAgent((h.get("SESSION_USER_AGENT") == null)? "": h.get("SESSION_USER_AGENT").toString());
				eventLog.setSessionStart((h.get("SESSION_START") == null)? "": h.get("SESSION_START").toString());
				eventLog.setSessionEnd((h.get("SESSION_END") == null)? "": h.get("SESSION_END").toString());
				eventLog.setEventId((h.get("EVENT_ID") == null)? "archived": h.get("EVENT_ID").toString());
				eventLog.setEventDate((h.get("EVENT_DATE") == null)? "": h.get("EVENT_DATE").toString());
				eventLog.setEvent((h.get("EVENT") == null)? "archived": h.get("EVENT").toString());
				eventLog.setRef((h.get("REF") == null)? "": h.get("REF").toString());
				eventList.add(eventLog);
				eventLog.setEventCode((h.get("EVENT_CODE") == null)? "NULL": h.get("EVENT_CODE").toString());
			}
       return eventList;
	}
/*
	public ArrayList getAuditMostrecentEvents(String eid, int start, int length) throws Exception{
		String orCondition = "";
		try {
			orCondition = "OR S.SESSION_USER = '"+UserDirectoryService.getUserId(eid)+"' ";
		} catch (UserNotDefinedException unde){
			orCondition = "";
		}
		ArrayList eventList = new ArrayList();
		String sql = "SELECT * FROM SAKAI_SESSION_20070821 S LEFT OUTER JOIN SAKAI_EVENT E " +
					"ON S.session_id = E.session_id where S.SESSION_USER = '"+eid+"' " +
					orCondition +//" AND E.EVENT_DATE < '2007-05-18'"+
							" ORDER by S.SESSION_START DESC, E.EVENT_DATE LIMIT "+start+","+length;
		//System.out.println(sql);
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List eList = jdt.queryForList(sql);
			Iterator iterator = eList.iterator();
			String previousSessionId = "";
			while(iterator.hasNext()) {
				MyUnisaEventLog eventLog = new MyUnisaEventLog();
				ListOrderedMap h = (ListOrderedMap) iterator.next();
				if(h.get("SESSION_ID") == null) {
					eventLog.setSessionId("");
				} else {
					if(previousSessionId.equals(h.get("SESSION_ID").toString())) {
						eventLog.setSessionId("");
					} else {
						previousSessionId = h.get("SESSION_ID").toString();
						eventLog.setSessionId(previousSessionId);
					}
				}

				eventLog.setSessionServer((h.get("SESSION_SERVER") == null)? "": h.get("SESSION_SERVER").toString());
				eventLog.setSessionUser((h.get("SESSION_USER") == null)? "": h.get("SESSION_USER").toString());
				eventLog.setSessionIp((h.get("SESSION_IP") == null)? "No session info": h.get("SESSION_IP").toString());
				eventLog.setSessionUserAgent((h.get("SESSION_USER_AGENT") == null)? "": h.get("SESSION_USER_AGENT").toString());
				eventLog.setSessionStart((h.get("SESSION_START") == null)? "": h.get("SESSION_START").toString());
				eventLog.setSessionEnd((h.get("SESSION_END") == null)? "": h.get("SESSION_END").toString());
				eventLog.setEventId((h.get("EVENT_ID") == null)? "archived": h.get("EVENT_ID").toString());
				eventLog.setEventDate((h.get("EVENT_DATE") == null)? "": h.get("EVENT_DATE").toString());
				eventLog.setEvent((h.get("EVENT") == null)? "archived": h.get("EVENT").toString());
				eventLog.setRef((h.get("REF") == null)? "": h.get("REF").toString());
				eventList.add(eventLog);
				eventLog.setEventCode((h.get("EVENT_CODE") == null)? "NULL": h.get("EVENT_CODE").toString());
			}
       return eventList;
	}

*/
	public ArrayList getCurrentEvents(String eid) throws Exception{
		String orCondition = "";
		userDirectoryService = (UserDirectoryService) ComponentManager.get(UserDirectoryService.class);
		try {
			orCondition = "OR S.SESSION_USER = '"+userDirectoryService.getUserId(eid)+"' ";
		} catch (UserNotDefinedException unde){
			orCondition = "";
		}
		ArrayList eventList = new ArrayList();
		String sql = "SELECT * FROM SAKAI_SESSION S LEFT OUTER JOIN SAKAI_EVENT E " +
					"ON S.session_id = E.session_id where S.SESSION_USER = '"+eid+"' " +
					orCondition +//" AND E.EVENT_DATE < '2007-05-18'"+
							" ORDER by  E.EVENT_DATE desc ";// LIMIT "+start+","+length;
		//System.out.println(sql);
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List eList = jdt.queryForList(sql);
			Iterator iterator = eList.iterator();
			String previousSessionId = "";
			while(iterator.hasNext()) {
				MyUnisaEventLog eventLog = new MyUnisaEventLog();
				ListOrderedMap h = (ListOrderedMap) iterator.next();
				if(h.get("SESSION_ID") == null) {
					eventLog.setSessionId("");
				} else {
					if(previousSessionId.equals(h.get("SESSION_ID").toString())) {
						eventLog.setSessionId("");
					} else {
						previousSessionId = h.get("SESSION_ID").toString();
						eventLog.setSessionId(previousSessionId);
					}
				}

				eventLog.setSessionServer((h.get("SESSION_SERVER") == null)? "": h.get("SESSION_SERVER").toString());
				eventLog.setSessionUser((h.get("SESSION_USER") == null)? "": h.get("SESSION_USER").toString());
				eventLog.setSessionIp((h.get("SESSION_IP") == null)? "No session info": h.get("SESSION_IP").toString());
				eventLog.setSessionUserAgent((h.get("SESSION_USER_AGENT") == null)? "": h.get("SESSION_USER_AGENT").toString());
				eventLog.setSessionStart((h.get("SESSION_START") == null)? "": h.get("SESSION_START").toString());
				eventLog.setSessionEnd((h.get("SESSION_END") == null)? "": h.get("SESSION_END").toString());
				eventLog.setEventId((h.get("EVENT_ID") == null)? "archived": h.get("EVENT_ID").toString());
				eventLog.setEventDate((h.get("EVENT_DATE") == null)? "": h.get("EVENT_DATE").toString());
				eventLog.setEvent((h.get("EVENT") == null)? "archived": h.get("EVENT").toString());
				eventLog.setRef((h.get("REF") == null)? "": h.get("REF").toString());
				eventList.add(eventLog);
				eventLog.setEventCode((h.get("EVENT_CODE") == null)? "NULL": h.get("EVENT_CODE").toString());
			}
       return eventList;
	}

	public List pager(List arrL,int start)
	{List al = new Vector();
	//int mystart=start;
	//java.util.Vector al = new java.util.Vector();
	int end =Math.min(arrL.size(),(start+100));

	try
	  {al =arrL.subList(start, end);
		//System.out.println("The start is "+start+" and the end is "+ end);
      }
	catch(Exception ex){System.err.println();
	}

	//System.out.println(" The size of the sublist is "+al.size()+" and the initial one is "+arrL.size()+" long" );
	return al;
	}


}
