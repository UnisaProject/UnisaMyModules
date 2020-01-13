package za.ac.unisa.lms.tools.mdactivity.dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.map.ListOrderedMap;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.util.LabelValueBean;
import org.springframework.jdbc.core.JdbcTemplate;

import za.ac.unisa.lms.dao.Gencod;
import za.ac.unisa.lms.dao.StudentSystemGeneralDAO;
import za.ac.unisa.lms.dao.general.PersonnelDAO;
import za.ac.unisa.lms.db.StudentSystemDAO;
import za.ac.unisa.lms.tools.mdactivity.forms.ActivityRecord;
import za.ac.unisa.lms.tools.mdactivity.forms.Promotor;
import za.ac.unisa.lms.tools.mdactivity.forms.Qualification;
import za.ac.unisa.lms.domain.general.Person;
import za.ac.unisa.lms.dao.general.PersonnelDAO;
import za.ac.unisa.lms.dao.Gencod;
import java.util.Calendar;
import java.util.Collections;

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
	
	
	/** Populate student look up list
	 * 
	 * @return LabelValueBean
	 * 			code and description for each student on the list
	 * @throws Exception
	 */

	public ArrayList<LabelValueBean> getStudentLookupList(String personnelNumber) throws Exception
		{
		ArrayList<LabelValueBean> studentList = new ArrayList<LabelValueBean>();
		
		String code = null;
		String desc = null;
		
		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		Connection connection = jdt.getDataSource().getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
			 try {	
				 String sql = "select stu.nr as valueCode,STUSUN.MK_QUALIFICATION_C || ' ' || stu.nr || ' - ' || stu.MK_TITLE || ' ' || STU.FIRST_NAMES || ' ' || STU.SURNAME as valueDesc" +
				 		" from stusun,dispro,stu,grd" + 
				 		" where STUSUN.FK_STUDENT_NR=DISPRO.FK_STUDENT_NR" + 
				 		" and STUSUN.MK_STUDY_UNIT_CODE=DISPRO.FK_STUDY_UNIT_CODE" + 
				 		" and STUSUN.STATUS_CODE in ('RG','FC')" + 
				 		" and STUSUN.FK_ACADEMIC_YEAR=?" + 
				 		" and DISPRO.MK_PROMOTOR_NR=?" + 
				 		" and STUSUN.MK_QUALIFICATION_C=grd.code" + 
				 		" and STU.NR = STUSUN.FK_STUDENT_NR" + 
				 		" order by STUSUN.MK_QUALIFICATION_C,STU.SURNAME,STU.FIRST_NAMES";			
				 
					ps = connection.prepareStatement(sql);
					ps.setInt(1, getCurrentYear());
					ps.setInt(2, Integer.parseInt(personnelNumber));
					
					rs = ps.executeQuery();	
					
					while (rs.next()) {		
						code = String.valueOf(rs.getInt("valueCode")) ;
						desc = rs.getString("valueDesc");	
						studentList.add(new org.apache.struts.util.LabelValueBean(desc, code));
						
					}	
					ps.close();
					rs.close();
					
					Collections.sort(studentList);
					connection.close();
					return studentList;
				}
		catch (Exception ex) {
			if (connection!=null){connection.close();}
			if (rs!=null){rs.close();}
			if (ps!=null){ps.close();}
			throw new Exception("MdActivityQueryDAO : Error getting studentLookupList/ " + ex);
		}
	}
	
	public String getQualDesc(String qualCode) throws Exception {
		String query = "select upper(long_eng_descripti) as ENG_DESCRIPTION from grd where code= ? ";
		String desc = "";

		try {
    	  	
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(query, new Object []{qualCode});
			Iterator i = queryList.iterator();
			if (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
					desc = data.get("ENG_DESCRIPTION").toString().toUpperCase();
			}
		} catch (Exception ex) {
			throw new Exception(
					"MdActivityQueryDAO : Error retrieving Qual description. / " + ex);
		}
		return desc;
	}

	public String getSpecDesc(String qualCode, String specCode) throws Exception {
		
		String desc = "";
		
		if (specCode == null || "0".equals(specCode) || "".equals(specCode) || "NVT".equalsIgnoreCase(specCode) || "undefined".equalsIgnoreCase(specCode)){ specCode = " ";}

		String query = "select ENGLISH_DESCRIPTIO from quaspc "
					+ " where mk_qualification_c = ? "
					+ " and speciality_code = ? ";

		try {
    	  	
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(query, new Object []{qualCode, specCode});
			Iterator i = queryList.iterator();
			if (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
					desc = data.get("ENGLISH_DESCRIPTIO").toString().toUpperCase();
			}
		} catch (Exception ex) {
			throw new Exception(
					"MdActivityQueryDAO : Error retrieving Spec description / " + ex);
		}
		return desc;
	}
	
	//Johanet 20190124 - CR2579 update student may register + write audit trail record
	public void updateStudentMayRegister(Integer stuNr, String changeValue, String changeReason, String user) throws Exception {
		
		
		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		Connection connection = jdt.getDataSource().getConnection();
		connection.setAutoCommit(false);	
		PreparedStatement ps = null;	
		String sql = "";
			 
		try {	
				sql = "update stu"							
						+ " set post_graduate_stud = ?"					
						+ " where stu.nr = ?";					
						
					ps = connection.prepareStatement(sql);
				
					ps.setString(1,changeValue.trim());
					ps.setInt(2,stuNr);	
					
					ps.executeUpdate(); 
					ps.close();	
					
					//get the latest sequence number for student and log type MDMAYREG
					int sequenceNumber = 0;
					sql = "select max(sequence_number)"
							+ " from stugenlog"
							+ " where stugenlog.mk_student_nr=?"
							+ " and stugenlog.log_type_gc322=?";					
												
					ps = connection.prepareStatement(sql);
							
					ps.setInt(1,stuNr);
					ps.setString(2,"MDMAYREG");						
					
					ResultSet rs = ps.executeQuery();
					rs = ps.executeQuery() ;
					rs.next();
					sequenceNumber = rs.getInt(1); 
					
					sequenceNumber = sequenceNumber + 1;
					
					ps.close();	
							
					//write Student general log entry - STUGENLOG						
							
					sql = "insert into stugenlog (mk_student_nr,log_type_gc322,sequence_number,action,update_on," 
							+  " update_by,program,changed_value,log_comment)" 							
							+  " values (?,?,?,?,systimestamp,?,?,?,?)";
							
							ps = connection.prepareStatement(sql);
							
							ps.setInt(1,stuNr);  											//mk_student_nr
							ps.setString(2,"MDMAYREG");                                     //log_type_gc322
							ps.setInt(3,sequenceNumber);                    				//sequence_number		
							ps.setString(4,"UPDATE");  									    //action
							ps.setString(5,user);         									//update_by
							ps.setString(6,"unisa-mdactivity");                    			//program		
							ps.setString(7,changeValue); 					                //changed_value
							ps.setString(8,changeReason); 		                      		//log_comment
						
							ps.executeUpdate();
							ps.close();
						
							connection.commit();
							connection.close();
		}
		catch (Exception e) {
			if (connection!=null){connection.rollback();}		
			throw new Exception("MdActivityQueryDAO: Error Updating permission that student may register(UPDATE STU, INSERT STUGENLOG) / " + e, e);
		} finally {		
			try { 
				if (connection!=null){
					connection.setAutoCommit(true);
					connection.close();					
				}
				if (ps!=null){ps.close();}
			} catch (Exception ex) {	
					ex.printStackTrace();
				}
			} 
	}
	
	//Johanet 20190130 - CR2579 get student may not register latest log comment
	public String getPostGraduateStudiesDeniedReason(Integer studentNr) throws Exception{
		
		String reason = "";
		
		String query = "select log_comment"				
				+ " from stugenlog where stugenlog.mk_student_nr=?"
				+ " and log_type_gc322='MDMAYREG' " 
				+ " and sequence_number=(select max(b.sequence_number) from stugenlog b where b.mk_student_nr= stugenlog.mk_student_nr"
				+ " and b.log_type_gc322='MDMAYREG' and b.changed_value='N') ";

		try {
    	  	
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(query, new Object []{studentNr});
			Iterator i = queryList.iterator();
			if (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
					reason = data.get("log_comment").toString();
			}
		} catch (Exception ex) {
			throw new Exception(
					"MdActivityQueryDAO : Error reading post graduate studies denied reason / " + ex);
		}
		
		return reason;
	}
	
	//Johanet 20190130 - CR2579 get Qual description, speciality and speciality description
	public Qualification getSTUANNQual (String studentNr, String acadyear) throws Exception{
		
		Qualification qual = new Qualification();
		String sql = "select mk_student_nr, mk_highest_qualifi, speciality_code from stuann " +
				"where mk_student_nr = " + studentNr + " and mk_academic_year = " + acadyear;
		try {
			//log.debug("MdApplicationsQuery - getMDAPPLRecord - studentNr: " + studentNr + ", sql: " + sql);
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(sql);
			Iterator i = queryList.iterator();
			while (i.hasNext()){
				ListOrderedMap data = (ListOrderedMap) i.next();				
				qual.setQualCode(data.get("mk_highest_qualifi").toString());
				qual.setSpecCode(data.get("speciality_code").toString());
				qual.setQualDesc("");
				qual.setSpecDesc("");
			}
		} catch (Exception ex) {
			throw new Exception("MdActivityQueryDAO : getSTUANNQual: Error occurred / "+ ex,ex);
		}
		try {
		qual.setQualDesc(getQualDesc(qual.getQualCode()));
		if (null!=qual.getSpecCode() && qual.getSpecCode().trim().equalsIgnoreCase("")) {
			qual.setSpecCode("n.a.");
			qual.setSpecDesc("Not applicable");
		} else {
			qual.setSpecDesc(getSpecDesc(qual.getQualCode(), qual.getSpecCode()));
		}
		
		} catch (Exception ex){
			//Do nothing
		}

		return qual;
	}
	
	//Johanet 20190129 - CR2579 get first registered date
	public String getFirstRegistrationDate(Integer studentNr, String qualCode) throws Exception{
		
		String firstRegDate="";
		
		String query = "select to_char(first_registration,'YYYYMMDD') as firstReg"
				+ " from stuaca"
				+ " where stuaca.mk_student_nr=?"
				+ " and STUACA.mk_qualification_c=?";

		try {
    	  	
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(query, new Object []{studentNr, qualCode});
			Iterator i = queryList.iterator();
			if (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				firstRegDate = data.get("firstReg").toString();
			}
		} catch (Exception ex) {
			throw new Exception(
					"MdActivityQueryDAO : Error reading first registration date for qualification / " + ex);
		}
		
		return firstRegDate;
	}
	
	//Johanet 20190129 - CR2579 get number of years registered
	public int getYearsRegistered(Integer studentNr, String qualCode) throws Exception{
		int years=0;
		
		String query = "select count(*) as yearsReg"
				+ " from stuann"
				+ " where stuann.mk_student_nr=?"
				+ " and stuann.mk_highest_qualifi=?"
				+ " and status_code='RG'";

		try {
    	  	
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(query, new Object []{studentNr, qualCode});
			Iterator i = queryList.iterator();
			if (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				years = Integer.parseInt(data.get("yearsReg").toString());
			}
		} catch (Exception ex) {
			throw new Exception(
					"MdActivityQueryDAO : Error reading stuann to get years registered for qual / " + ex);
		}
		
		return years;		
	}
	
	//Johanet 20190130 - CR2579 get number of years registered for research proposal
	public int getYearsRegisteredForResearchProposal(Integer studentNr, String qualCode) throws Exception{
		int years=0;
		
		String query = "select count(distinct temp.acadYear) as yearsReg"
				+ " from "
				+ " (select distinct acasun.mk_academic_year as acadYear"
				+ " from studis,acasun"  
				+ " where STUDIS.MK_STUDENT_NR=?" 
				+ " and STUDIS.TYPE ='P'" 
				+ " and ACASUN.FK_STUDENT_NR=STUDIS.MK_STUDENT_NR" 
				+ " and ACASUN.MK_STUDY_UNIT_CODE=STUDIS.MK_STUDY_UNIT_CODE" 
				+ " and acasun.fk_qual_code=?" 
				+ " and ACASUN.CANCELLATION_CODE = 'NC'"
				+ " union"  
				+ " select distinct STUSUN.FK_ACADEMIC_YEAR as acadYear" 
				+ " from studis,stusun"
				+ " where STUDIS.MK_STUDENT_NR=?"
				+ " and STUDIS.TYPE ='P'"
				+ " and STUSUN.FK_STUDENT_NR =STUDIS.MK_STUDENT_NR"
				+ " and STUSUN.MK_STUDY_UNIT_CODE =STUDIS.MK_STUDY_UNIT_CODE"
				+ " and STUSUN.MK_QUALIFICATION_C =?"
				+ " and STUSUN.STATUS_CODE in ('RG','FC')) temp";

		try {
    	  	
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(query, new Object []{studentNr, qualCode,studentNr, qualCode});
			Iterator i = queryList.iterator();
			if (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				years = Integer.parseInt(data.get("yearsReg").toString());
			}
		} catch (Exception ex) {
			throw new Exception(
					"MdActivityQueryDAO : Error reading studis,acasun and stusun get number of years registered for research proposal / " + ex);
		}
		
		return years;		
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
	

	public int getCurrentYear() {

		int currentYear = 0;
		log.debug("get current year - M and D activity.");
			/* jan = 0, Feb=1 , Nov=10, Dec=11 etc */
		if (Calendar.getInstance().get(Calendar.MONTH) < 9) {
			currentYear = Calendar.getInstance().get(Calendar.YEAR);
		} else {
			/*Removed for test purposes +1*/
			currentYear = (Calendar.getInstance().get(Calendar.YEAR) + 1 );
		}
		log.debug("Returning "+currentYear+" as the current year for m and d");

		return currentYear;

	}
}
