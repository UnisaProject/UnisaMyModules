package za.ac.unisa.lms.tools.mdactivity.dao;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.map.ListOrderedMap;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import za.ac.unisa.lms.dao.Gencod;
import za.ac.unisa.lms.dao.StudentSystemGeneralDAO;
import za.ac.unisa.lms.dao.general.PersonnelDAO;
import za.ac.unisa.lms.db.StudentSystemDAO;
import za.ac.unisa.lms.tools.mdactivity.forms.ActivityRecord;
import za.ac.unisa.lms.tools.mdactivity.forms.Promotor;
import za.ac.unisa.lms.domain.general.Person;
import za.ac.unisa.lms.dao.general.PersonnelDAO;
import za.ac.unisa.lms.dao.Gencod;
import java.util.Calendar;

public class MdActivityQueryDAO extends StudentSystemDAO {


	public static Log log = LogFactory.getLog(MdActivityQueryDAO.class);


	/**
	 * This method returns a list of activity codes from the gen
	 *
	 * @param qualCode
	 *            The qualification code
	 */
	public ArrayList getActivityCodes() throws Exception {
		ArrayList list = new ArrayList();
		try {
			StudentSystemGeneralDAO dao = new StudentSystemGeneralDAO();
			List dbGenList = dao.getGenCodes(Short.parseShort("35"),0);
			for (int i = 0; i < dbGenList.size(); i++) {
				Gencod gencod = new Gencod();
				gencod = (Gencod) dbGenList.get(i);
				list.add(new org.apache.struts.util.LabelValueBean(gencod.getCode() +" - " + gencod.getEngDescription(), gencod.getCode() +"-" + gencod.getEngDescription()));
			}
		} catch (Exception ex) {
			throw new Exception(
					"MdActivityQueryDao : Error reading activity codes / " + ex, ex);
		}
		return list;
	}
	
	public ArrayList getMDActivities(int studNr,String studyUnit) throws Exception{
		ArrayList<ActivityRecord> list = new ArrayList<ActivityRecord>();
		try {			
			List queryList = new ArrayList();
			
			String sql = "select staff_number,activity_code, to_char(activity_date,'YYYYMMDD') as actDate," +
			" to_char(entry_timestamp,'YYYYMMDD HH24:MI:SS.FF3') as entryTimestamp,to_char(feedback_date,'YYYYMMDD') as feedbackDate,comments" +
			" from mdactv" +
			" where mk_student_nr=" + studNr +
			" and mk_study_unit_code='" + studyUnit + "'" +
			" order by activity_date";
				
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			queryList = jdt.queryForList(sql);
			for (int i=0; i<queryList.size();i++){
					ActivityRecord activity = new ActivityRecord();				
					ListOrderedMap data = (ListOrderedMap) queryList.get(i);
					activity.setActivityCode(data.get("activity_code").toString());
					String genCode = activity.getActivityCode();
					if (genCode.length()<2){
						genCode = "0" + activity.getActivityCode();
					}
					Gencod gencod = new Gencod();
					StudentSystemGeneralDAO dao = new StudentSystemGeneralDAO();					
					gencod = dao.getGenCode("35", genCode);
					if (gencod != null && gencod.getEngDescription()!=null){
						activity.setActivityDescr(gencod.getEngDescription());
					}else {
						activity.setActivityDescr("");
					}					
					activity.setUserCode(Integer.parseInt(data.get("staff_number").toString()));
					Person person = new Person();
					PersonnelDAO daopers = new PersonnelDAO();
					person = daopers.getPerson(activity.getUserCode());
					if (person!=null && person.getName()!=null){
						activity.setUserName(person.getName());
					}else{
						activity.setUserName("");
					}				
					if (data.get("comments")==null){
						activity.setComments("");	
					}else{
						activity.setComments(data.get("comments").toString());
					}
					if (data.get("actDate")==null){
						activity.setActivityDate("");
					}else{
						activity.setActivityDate(data.get("actDate").toString());
					}
					if (data.get("feedbackDate")==null){
						activity.setFeedbackDate("");
					}else{
						activity.setFeedbackDate(data.get("feedbackDate").toString());
					}	
					if (activity.getFeedbackDate().equalsIgnoreCase("00010101")){
						activity.setFeedbackDate("");
					}
					if (activity.getActivityDate().equalsIgnoreCase("00010101")){
						activity.setActivityDate("");
					}
					String timestampStr = data.get("entryTimestamp").toString();
					SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd HH:mm:ss.SSS");
					Calendar calendar = Calendar.getInstance();
					Date entryTimestamp = formatter.parse(timestampStr);
					//java.sql.Timestamp timestamp= new java.sql.Timestamp(entryTimestamp.getTime());
					calendar.setTime(entryTimestamp);
					activity.setEntryTimestamp(calendar);
						
					list.add(activity);						
				}	
		} catch (Exception ex) {
			throw new Exception(
					"MdActivityQueryDao : Error reading md activities / " + ex, ex);
		}
		return list;
	}

	/**
	 * This method executes a query and returns a String value as result.
	 * An empty String value is returned if the query returns no results.
	 *
	 * @param query     The query to be executed on the database
	 * @param field		  The field that is queried on the database
	 */
	private String querySingleValue(String query, String field){

		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		List queryList;
		String result = "";

		queryList = jdt.queryForList(query);
		Iterator i = queryList.iterator();
		i = queryList.iterator();
		if (i.hasNext()) {
			ListOrderedMap data = (ListOrderedMap) i.next();
			if (data.get(field)!= null){
				result = data.get(field).toString();
			}else{
				result = "";
			}
		}

		return result;
	}
}
