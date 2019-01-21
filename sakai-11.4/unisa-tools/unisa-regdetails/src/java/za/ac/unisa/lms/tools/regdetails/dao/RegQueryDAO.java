package za.ac.unisa.lms.tools.regdetails.dao;


import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.map.ListOrderedMap;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import za.ac.unisa.lms.dao.Gencod;
import za.ac.unisa.lms.dao.StudentSystemGeneralDAO;
import za.ac.unisa.lms.db.StudentSystemDAO;
import za.ac.unisa.lms.tools.regdetails.forms.GeneralItem;
import za.ac.unisa.lms.tools.regdetails.forms.Qualification;
import za.ac.unisa.lms.tools.regdetails.forms.StudyUnit;

public class RegQueryDAO extends StudentSystemDAO {

	private static int currentYear =-1;
	public static Log log = LogFactory.getLog(RegQueryDAO.class);

	public boolean existsStudentAnnual(String studentNr){

		/* check stuann only */
		String sql = "select * from stuann a" +
		" where a.mk_academic_year="+getCurrentYear()+
		" and a.mk_academic_period = 1"+
		" and a.mk_student_nr = " +studentNr+
		" and a.STATUS_CODE in ('RG','CA')";
		//log.debug(sql);
		
		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		List queryList = jdt.queryForList(sql);

		Iterator i = queryList.iterator();
		while (i.hasNext()) {
			return true;
		}

		return false;
	}
	
	public boolean isWrongAccountType(String studentNr){

		String sql = "select * from stuann a, acttyp c" +
		" where a.mk_academic_year="+getCurrentYear()+
		" and a.mk_academic_period = 1"+
		" and a.mk_student_nr = " +studentNr+
		" and c.code= a.fk_account_type and C.BRANCH_CODE=A.FK_BRANCH_CODE and c.contract_flag='Y'";
		//log.debug("RegQueryDAO - isWrongAccountType - sql="+sql);
		
		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		List queryList = jdt.queryForList(sql);

		Iterator i = queryList.iterator();
		while (i.hasNext()) {
			return true;
		}

		return false;
	}

	public String getStudentAnnualStatus(String studentNr){

		/* check stuann only */
		String sql = "select status_code from stuann a" +
		" where a.mk_academic_year="+getCurrentYear()+
		" and a.mk_academic_period = 1"+
		" and a.mk_student_nr = " +studentNr;
		//log.debug(sql);

		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		List queryList = jdt.queryForList(sql);

		Iterator i = queryList.iterator();
		while (i.hasNext()) {
			ListOrderedMap data = (ListOrderedMap) i.next();
			return data.get("STATUS_CODE").toString();
		}

		return "";
	}
	
	public String getStuAcaStatus(String studentNr, String qualCode) throws Exception{

		/* check stuann only */
		String sql = "select status_code from stuaca a" +
		" where a.mk_student_nr =" +studentNr +
		" and a.mk_qualification_c='" + qualCode + "'";
		//log.debug(sql);

		try {
		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		List queryList = jdt.queryForList(sql);

		Iterator i = queryList.iterator();
		while (i.hasNext()) {
			ListOrderedMap data = (ListOrderedMap) i.next();
			return data.get("STATUS_CODE").toString();
		}

		return "NOT FOUND";
		
		} catch (Exception ex) {
			throw new Exception("RegQueryDao : Error reading stuaca status / "+ ex,ex);
		}
	}

	public ArrayList getRegisteredStudyUnits(String studentNr) throws Exception{

		// Get study units for student
		ArrayList list = new ArrayList();

		/*
		 * Query:
		 * Read all study units : EXCLUDING TP's and
		 *  					  EXCLUDING suppl = (2,4,6,7,8)
		 */
		String sql = "select s.*, u.ENG_SHORT_DESCRIPT from stusun s, sun u, stuann a" +
		" where a.mk_academic_year="+getCurrentYear()+
		" and a.mk_academic_period = 1"+
		" and a.mk_student_nr = " +studentNr+
		" and s.FK_ACADEMIC_YEAR= a.mk_academic_year"+
		" and s.FK_ACADEMIC_PERIOD=1"+
		" and s.FK_STUDENT_NR = a.mk_student_nr"+
		" and s.SUPPLEMENTARY_EXAM not in (2,4,5,6,7,8)"+
		" and s.STATUS_CODE not in ('TP')"+
		" and a.STATUS_CODE in ('RG','CA')"+
		" and u.CODE = s.MK_STUDY_UNIT_CODE order by s.MK_STUDY_UNIT_CODE";
		//log.debug("getRegisteredStudyUnits - SQL="+sql);

		try{
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(sql);

			Iterator i = queryList.iterator();
			while (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				StudyUnit studyUnit = new StudyUnit();
				studyUnit.setCode(data.get("MK_STUDY_UNIT_CODE").toString());
				studyUnit.setDesc(data.get("ENG_SHORT_DESCRIPT").toString());
				studyUnit.setRegPeriod(data.get("SEMESTER_PERIOD").toString());
				studyUnit.setSupplCode(data.get("SUPPLEMENTARY_EXAM").toString());
				String sql2 = "select eng_description from gencod" +
				" where fk_gencatcode = 18 and code = '" + data.get("STATUS_CODE").toString()+"'" +
				" and in_use_flag='Y'";
				studyUnit.setStatus(this.querySingleValue(sql2,"ENG_DESCRIPTION"));
				if ("A".equalsIgnoreCase(data.get("LANGUAGE_CODE").toString())){
					studyUnit.setLanguage("Afr");
				}else{
					studyUnit.setLanguage("Eng");
				}
				// Exam Period
				if (Integer.parseInt(data.get("MK_EXAM_PERIOD").toString())==1){
					studyUnit.setExamPeriod(data.get("EXAM_YEAR").toString()+", Jan/Feb");
				}else if (Integer.parseInt(data.get("MK_EXAM_PERIOD").toString())==6){
					studyUnit.setExamPeriod(data.get("EXAM_YEAR").toString()+", May/June");
				}else if (Integer.parseInt(data.get("MK_EXAM_PERIOD").toString())==10){
					studyUnit.setExamPeriod(data.get("EXAM_YEAR").toString()+", Oct/Nov");
				}
				// Indicate non degree purpose course
				if ("00019".equalsIgnoreCase(data.get("MK_QUALIFICATION_C").toString())){
					studyUnit.setNdp("Yes");
				}else{
					studyUnit.setNdp("No");
				}
				list.add(studyUnit);
			}
		} catch (Exception ex) {
			throw new Exception("RegQueryDao : Error reading reg study units / "+ ex,ex);
		}

		return list;
	}

	public ArrayList getApplyForStudyUnits(String studentNr) throws Exception{

		// Get applied for registration study units for student
		ArrayList list = new ArrayList();
		boolean checkWorkflowStatus = false;
		boolean checkAdditionWorkflowStatus = false;
		GeneralItem wflItem = new GeneralItem();
		GeneralItem additionsWflItem = new GeneralItem();

		/*
		 * Query:
		 * 1.Read all stusun(TP) and solsun study units for the student,
		 *  take only the distinct study units between them.
		 * 2. Take those study units and read their stusun and
		 *  solsun records
		 *
		 * If a stusun and solsun record exists only show the stusun record.
		 */
		String sql = " select distinct(suncode), u.ENG_SHORT_DESCRIPT, s.*, TO_CHAR(s.REGISTRATION_DATE ,'DD Month YYYY') as indate1, o.semester_period solsun_semester_period, TO_CHAR(o.CHANGE_TIMESTAMP,'DD Month YYYY, HH24:MI') as indate2 from ("+
			   			" (select l.MK_STUDY_UNIT_CODE as suncode"+
			   			" from solsun l "+
			   			" where l.MK_STUDENT_NR ="+studentNr +")"+
			   			" union"+
			   			" (select t.MK_STUDY_UNIT_CODE as suncode"+
			   			" from stusun t, stuann a "+
			   			" where a.MK_STUDENT_NR="+studentNr +
			   			" and a.mk_academic_year="+getCurrentYear()+
			   			" and a.mk_academic_period = 1"+
			   			" and a.mk_student_nr = t.FK_STUDENT_NR"+
			   			" and t.STATUS_CODE ='TP')"+
			   			" )"+
		              " , sun u"+
		              " , stusun s"+
		              " , solsun o"+
		              " where u.CODE=suncode"+
		              " and s.fK_STUDENT_NR (+) ="+studentNr +
		              " and s.MK_STUDY_UNIT_CODE (+)=suncode"+
		              " and o.MK_STUDENT_NR (+) ="+studentNr +
		              " and o.MK_STUDY_UNIT_CODE(+)=suncode";
		//log.debug("getApplyForStudyUnits - SQL="+sql);

			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(sql);

			Iterator i = queryList.iterator();
			while (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				StudyUnit studyUnit = new StudyUnit();
				studyUnit.setCode(data.get("SUNCODE").toString());
				studyUnit.setDesc(data.get("ENG_SHORT_DESCRIPT").toString());
				if (data.get("MK_STUDY_UNIT_CODE") != null && data.get("STATUS_CODE") != null && "TP".equalsIgnoreCase(data.get("STATUS_CODE").toString()) ){
					//if stusun and solsun exist, rather display stusun
					studyUnit.setRegPeriod(data.get("SEMESTER_PERIOD").toString());
					studyUnit.setSupplCode(data.get("SUPPLEMENTARY_EXAM").toString());
					studyUnit.setStatus(data.get("INDATE1").toString());
					String status = "";
					status = getStudentAnnualStatus(studentNr);
					if ("TN".equalsIgnoreCase(status)){
						/* Student NOT registered and have TP study units -
						 * waiting for money or documentation
						 */
						studyUnit.setStatusIndicator("TN");
						// get workflow status
						if(!checkWorkflowStatus){
							wflItem = getWflReasonCode(studentNr);
							if (wflItem!= null && wflItem.getDesc() != null){
								studyUnit.setWflTimestamp(wflItem.getTimestamp());
								studyUnit.setWflDescription(wflItem.getDesc());
								studyUnit.setWflIndicator(true);// display workflow messsage
							}
							checkWorkflowStatus = true;
						}else{
							if (wflItem!= null && wflItem.getDesc() != null){
							  studyUnit.setWflTimestamp(wflItem.getTimestamp());
							  studyUnit.setWflDescription(wflItem.getDesc());
							  studyUnit.setWflIndicator(true);// display workflow messsage
							}
						}
					}else{
						/* Student is registered, but have TP study units probably
						 * because it was an addition
						 */
						studyUnit.setStatusIndicator("TP");
						// get workflow status
						if(!checkAdditionWorkflowStatus){
							additionsWflItem = getAdditionsWflReasonCode(studentNr);
							if (additionsWflItem !=null && additionsWflItem.getDesc() != null){
								studyUnit.setWflTimestamp(additionsWflItem.getTimestamp());
								studyUnit.setWflDescription(additionsWflItem.getDesc());
								studyUnit.setWflIndicator(true);// display workflow messsage
							}
							checkAdditionWorkflowStatus = true;
						}else{
							if (additionsWflItem!=null && additionsWflItem.getDesc()  != null){
							  studyUnit.setWflTimestamp(additionsWflItem.getTimestamp());
							  studyUnit.setWflDescription(additionsWflItem.getDesc());
							  studyUnit.setWflIndicator(true);// display workflow messsage
							}
						}
					}
				}else if (data.get("SOLSUN_SEMESTER_PERIOD")!= null && data.get("STATUS_CODE") == null ){
					/*
					 * if solsun exists, web registration application have not
					 * been processed
					 */
					studyUnit.setRegPeriod(data.get("SOLSUN_SEMESTER_PERIOD").toString());
					studyUnit.setStatus(data.get("INDATE2").toString());
					studyUnit.setStatusIndicator("A");
//					 get workflow status
					if(!checkWorkflowStatus){
						wflItem = getWflReasonCode(studentNr);
						if (wflItem!= null && wflItem.getDesc()  != null){
							studyUnit.setWflTimestamp(wflItem.getTimestamp());
							studyUnit.setWflDescription(wflItem.getDesc());
							studyUnit.setWflIndicator(true);// display workflow messsage
						}
						checkWorkflowStatus = true;
					}else{
						if (wflItem!= null && wflItem.getDesc()  != null){
						  studyUnit.setWflTimestamp(wflItem.getTimestamp());
						  studyUnit.setWflDescription(wflItem.getDesc());
						  studyUnit.setWflIndicator(true);// display workflow messsage
						}
					}
				}else{
					// Dont show study unit
					studyUnit = null;
				}
				if (studyUnit != null)
					list.add(studyUnit);
			}

		return list;
	}
	
	public GeneralItem getWflReasonCode(String studentNr){

		GeneralItem result = null;
		
		// read the last entry that was written away after the year module registrations opened for the current reg year
		String sql = "select TO_CHAR(s.timestamp ,'DD Month YYYY') as changeDate, s.value_changed from stuchg s,  regdat d "+
					 " where s.MK_USER_CODE=9999 and s.MK_STUDENT_NR= " +studentNr+ 
					 " and s.CHANGE_CODE='RW' and trunc(s.timestamp)>= trunc(d.FROM_DATE) and d.ACADEMIC_YEAR="+getCurrentYear()+
					 " and d.SEMESTER_PERIOD = 0 and d.TYPE='IR'"+
					 " order by s.timestamp desc";

		//log.debug(sql);

		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		List queryList = jdt.queryForList(sql);

		Iterator i = queryList.iterator();
		// return first record only
		if (i.hasNext()) {
			result = new GeneralItem();
			ListOrderedMap data = (ListOrderedMap) i.next();
			result.setCode(data.get("VALUE_CHANGED").toString().substring(0,2));
			result.setTimestamp(data.get("CHANGEDATE").toString());
			if(result.getCode() != null && !"".equals(result.getCode().trim())){
				try{ // parse string to int to remove leading zero if neccesarry
					result.setCode(String.valueOf(Integer.parseInt(result.getCode())));
					if("1".equals(result.getCode())){
						// don't show reason code 1 - student registered
						return null;
					}
				} catch(NumberFormatException e){
					// if error, code was written incorrectly to database
					log.error("RegQueryDao: Reason code from workflow invalid for student " + studentNr);
					return null;
				}

				// get reason description
				Gencod genCod = new Gencod();
				StudentSystemGeneralDAO stuGenDao = new StudentSystemGeneralDAO();
				genCod = stuGenDao.getGenCode("49",result.getCode());
				// Set description
				result.setDesc(genCod.getAfrDescription());
			}
		}

		return result;

	}
	
	public GeneralItem getAdditionsWflReasonCode(String studentNr){

		GeneralItem result = new GeneralItem();
		
		// read the last entry that was written away after the year module registrations opened for the current reg year
		String sql = "select TO_CHAR(s.timestamp ,'DD Month YYYY') as changeDate, s.value_changed from stuchg s,  stadoc a "+
					 " where s.MK_USER_CODE=9999 and s.MK_STUDENT_NR= " +studentNr+ 
					 " and s.CHANGE_CODE='RW' and trunc(s.timestamp)>= trunc(a.CREATION_DATE) "+
					 " and a.FK_STUNR= s.MK_STUDENT_NR and a.FK_STUFLGCODE = '99' order by s.timestamp desc";

		//log.debug(sql);

		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		List queryList = jdt.queryForList(sql);

		Iterator i = queryList.iterator();
		// return first record only
		if (i.hasNext()) {
			result = new GeneralItem();
			ListOrderedMap data = (ListOrderedMap) i.next();
			result.setCode(data.get("VALUE_CHANGED").toString().substring(0,2));
			result.setTimestamp(data.get("CHANGEDATE").toString());
			if(result.getCode() != null && !"".equals(result.getCode().trim())){
				try{ // parse string to int to remove leading zero if neccesarry
					result.setCode(String.valueOf(Integer.parseInt(result.getCode())));
					if("1".equals(result.getCode())){
						// don't show reason code 1 - student registered
						return null;
					}
				} catch(NumberFormatException e){
					// if error, code was written incorrectly to database
					log.error("RegQueryDao: Reason code from workflow invalid for student " + studentNr);
					return null;
				}

				// get reason description
				Gencod genCod = new Gencod();
				StudentSystemGeneralDAO stuGenDao = new StudentSystemGeneralDAO();
				genCod = stuGenDao.getGenCode("49",result.getCode());
				// Set description
				result.setDesc(genCod.getAfrDescription());
			}
		}

		return result;

	}

	public ArrayList getSupplStudyUnits(String studentNr) throws Exception{
		// Get study units for student
		ArrayList list = new ArrayList();

		String sql = "select s.*, u.ENG_SHORT_DESCRIPT from stusun s, sun u, stuann a" +
		" where a.mk_academic_year="+getCurrentYear()+
		" and a.mk_academic_period = 1"+
		" and a.mk_student_nr = " +studentNr+
		//" and s.FK_ACADEMIC_YEAR= a.mk_academic_year"+
		" and s.FK_ACADEMIC_PERIOD=1"+
		" and s.FK_STUDENT_NR = a.mk_student_nr"+
		" and s.status_code = 'RG' "+
		" and s.SUPPLEMENTARY_EXAM in (2,4,5,6,7,8)"+
		" and a.STATUS_CODE in ('RG')"+
		" and u.CODE = s.MK_STUDY_UNIT_CODE order by s.MK_STUDY_UNIT_CODE";
		//log.debug("getSupplStudyUnits - SQL="+sql);

		try{
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(sql);

			Iterator i = queryList.iterator();
			while (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				StudyUnit studyUnit = new StudyUnit();
				studyUnit.setCode(data.get("MK_STUDY_UNIT_CODE").toString());
				studyUnit.setDesc(data.get("ENG_SHORT_DESCRIPT").toString());
				if (Integer.parseInt(data.get("MK_EXAM_PERIOD").toString())==1){
					studyUnit.setExamPeriod(data.get("EXAM_YEAR").toString()+", Jan/Feb");
				}else if (Integer.parseInt(data.get("MK_EXAM_PERIOD").toString())==6){
					studyUnit.setExamPeriod(data.get("EXAM_YEAR").toString()+", May/June");
				}else if (Integer.parseInt(data.get("MK_EXAM_PERIOD").toString())==10){
					studyUnit.setExamPeriod(data.get("EXAM_YEAR").toString()+", Oct/Nov");
				}
				list.add(studyUnit);
			}
		} catch (Exception ex) {
			throw new Exception("RegQueryDao : Error reading reg study units / "+ ex,ex);
		}

		return list;
	}

	public Qualification getStudentQualification(String studentNr)
			throws Exception {
		// Get student qual code and description
		Qualification qual = new Qualification();

		String sql1 = "select s.MK_HIGHEST_QUALIFI,g.ENG_DESCRIPTION,"
				+ "g.INSTITUTION_CODE,a.FK_SPECIALITY_CODE,"
				+ "q.ENGLISH_DESCRIPTIO,g.under_post_categor"
				+ " from stuann s, grd g, stuaca a , quaspc q "
				+ " where s.MK_ACADEMIC_YEAR = '" + getCurrentYear() + "' "
				+ " and s.MK_ACADEMIC_PERIOD = 1" 
				+ " and s.MK_STUDENT_NR = '" + studentNr + "' "
				+ " and g.CODE = s.MK_HIGHEST_QUALIFI"
				+ " and a.MK_QUALIFICATION_C = s.MK_HIGHEST_QUALIFI"
				+ " and a.MK_STUDENT_NR = s.MK_STUDENT_NR"
				+ " and q.MK_QUALIFICATION_C(+) = a.mk_qualification_c"
				+ " and q.SPECIALITY_CODE(+) = a.FK_SPECIALITY_CODE";

		//log.debug("RegQueryDAO - getStudentQualification - SQL1="+sql1);
		
		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		List queryList = jdt.queryForList(sql1);

		Iterator i = queryList.iterator();
		if (i.hasNext()) {
			while (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				qual.setQualCode(data.get("MK_HIGHEST_QUALIFI").toString());
				qual.setQualDesc(data.get("ENG_DESCRIPTION").toString());
				if (data.get("FK_SPECIALITY_CODE") != null) {
					qual.setSpecCode(data.get("FK_SPECIALITY_CODE").toString());
					if (data.get("ENGLISH_DESCRIPTIO") != null) {
						qual.setSpecDesc(data.get("ENGLISH_DESCRIPTIO").toString());
					} else {
						qual.setSpecDesc("");
					}
				} else {
					qual.setSpecCode("");
					qual.setSpecDesc("");
				}
				
				if (data.get("INSTITUTION_CODE") == null) {
					qual.setInstitution("");
				} else {
					qual.setInstitution(data.get("INSTITUTION_CODE").toString());
				}
				if (data.get("UNDER_POST_CATEGOR") == null) {
					qual.setQualType("");
				} else {
					qual.setQualType(data.get("UNDER_POST_CATEGOR").toString());
				}
			}
			if ("".equalsIgnoreCase(qual.getSpecCode())) {
				/* if spec code empty read again with a space */
				String sql2 = "select ENGLISH_DESCRIPTIO" + " from quaspc "
						+ " where MK_QUALIFICATION_C = '" + qual.getQualCode() + "'"
						+ " and SPECIALITY_CODE = ' '" 
						+ " and in_use_flag = 'Y' ";
				qual.setSpecDesc(this.querySingleValue(sql2, "ENGLISH_DESCRIPTIO"));
			}
		}else{
			qual.setQualCode("");
			qual.setQualDesc("");
			qual.setSpecCode("");
			qual.setSpecDesc("");
			qual.setInstitution("");
			qual.setQualType("");
		}

		return qual;
	}

	public boolean isStudentLocal(String studentNr) {
		String country = "";
		boolean result = false;

		String sql = "Select mk_country_code from stu where nr=" + studentNr;
		country = this.querySingleValue(sql, "MK_COUNTRY_CODE");
		if ("1015".equalsIgnoreCase(country)) {
			result = true;
		}
		return result;
	}

	public void writeAuditTrail(String studentNr, String changeCode) throws Exception{

		
		try {
			int recordsAffected = 0;
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			String sql = "insert into stuchg (mk_student_nr, mk_user_code, change_code, timestamp, value_changed) values (?, ?, ?, SYSTIMESTAMP, ?)";

			recordsAffected = jdt.update(sql,new Object[] { studentNr,"9999",changeCode,""});
			if (recordsAffected<=0) {
	    		log.info("writeAuditTrail STUCHG Error Going into Retry- StudentNo: " + studentNr + " MK_USER_CODE: 9999 CHANGE_CODE: " + changeCode + " ValueChanged: " + changeCode);
		    	for (int i=0;i<10;i++) {
			    	try {
			    		/* Sleep thread 1 second because an IR was written just
			   		 	before, otherwise insert will fail on uniqueness */
						Thread.sleep(Long.parseLong("1000"));
			    		recordsAffected = jdt.update(sql,new Object[] { studentNr,"9999",changeCode,""});
						break;
					}catch(Exception ex) {
						log.info("writeAuditTrail STUCHG Error - StudentNo: " + studentNr + " Not inserted anything / " + ex);
					}
		    	}
	    	}
		} catch (Exception e) {

		}
	}

	public void writeAuditTrail(String studentNr, String changeCode, String valueChanged) throws NumberFormatException{

		
		try {
			int recordsAffected = 0;
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			String sql = "insert into stuchg (mk_student_nr, mk_user_code, change_code, timestamp, value_changed) values (?, ?, ?, SYSTIMESTAMP, ?)";

			recordsAffected = jdt.update(sql,new Object[] { studentNr,"9999",changeCode,valueChanged});
			if (recordsAffected<=0) {
	    		log.info("writeAuditTrail STUCHG Error Going into Retry- StudentNo: " + studentNr + " MK_USER_CODE: 9999 CHANGE_CODE: " + changeCode + " ValueChanged: " + changeCode);
		    	for (int i=0;i<10;i++) {
			    	try {
			    		/* Sleep thread 1 second because an IR was written just
			   		 	before, otherwise insert will fail on uniqueness */
						Thread.sleep(Long.parseLong("1000"));
			    		recordsAffected = jdt.update(sql,new Object[] { studentNr,"9999",changeCode,valueChanged});
						break;
					}catch(Exception ex) {
						log.info("writeAuditTrail STUCHG Error - StudentNo: " + studentNr + " Not inserted anything / " + ex);
					}
		    	}
	    	}
		} catch (Exception e) {

		}
	}
	
	public void writeStudentFlag(String studentNr, String flag){

		try{
			Log log = LogFactory.getLog(RegQueryDAO.class);
			Calendar cal = Calendar.getInstance();
			java.sql.Date currentDate =  new java.sql.Date(cal.getTime().getTime());

			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			try{
				String sql1 = "select count(*) from stadoc where FK_STUFLGCODE= "+ flag + " and fk_stunr="+studentNr;
				int count = jdt.queryForInt(sql1);
				if (count!=0){
					// Delete record first
					String sql = "delete from stadoc where FK_STUFLGCODE="+flag+" and fk_stunr="+studentNr;
					jdt.update(sql);
					//log.debug("RegQuery student flag "+flag +" deleted for "+studentNr+ ".");
				}
				Thread.sleep(Long.parseLong("1000"));
				//currentDate =  new java.sql.Date(cal.getTime().getTime());
				String sql = "insert into stadoc (creation_date, fk_stunr, FK_STUFLGCODE) values (sysdate, ?, ?)";
				jdt.update(sql,new Object[] {studentNr,flag});
				//log.debug("RegQuery student flag "+flag +" written for "+studentNr+ ".");
			}catch(Exception ex) {
				log.info("writeStudentFlag STADOC Error - StudentNo: " + studentNr + " Not inserted anything / " + ex);
			}
		} catch (Exception e) {

		}
		
	}

	/**
	 * This method returns false if the today's date not within valid dates
	 * on the database
	 *
	 * @param type       The type of period
	 * @param addPeriod  The addition period
	 */
	public boolean isRegPeriodValid(String type,String addPeriod) throws Exception{

		boolean result = false;
		String toDate = "";
		
		//log.debug("RegQueryDAO - isRegPeriodValid - Academic Year="+RegQueryDAO.getCurrentYear()+", type="+type+", addPeriod="+addPeriod);

		String query1 = "select to_char(to_date, 'YYYY-MM-DD') as to_date from regdat where academic_year="+RegQueryDAO.getCurrentYear()+" and type='"+type+"' and semester_period="+addPeriod;
		String query2 = "select to_char(to_date, 'YYYY-MM-DD') as to_date from regdat where academic_year="+RegQueryDAO.getCurrentYear()+" and type='"+type+"' and semester_period="+addPeriod+" and trunc(sysdate) between from_date and to_date";
		String query3 = "select to_char(to_date, 'YYYY-MM-DD') as to_date from regdat where academic_year="+RegQueryDAO.getCurrentYear()+" and type='"+type+"' and semester_period="+addPeriod+" and trunc(sysdate)>trunc(from_date)";

		try{
			Log log = LogFactory.getLog(RegQueryDAO.class);
			toDate = this.querySingleValue(query1,"TO_DATE");

			if (!"".equalsIgnoreCase(toDate)) {
				// If record exists default to false
				result = false;
				toDate="";
				if ("0001-01-01".equalsIgnoreCase(toDate)){
					toDate = this.querySingleValue(query3,"TO_DATE");
				}else{
					toDate = this.querySingleValue(query2,"TO_DATE");
				}
				if (!"".equalsIgnoreCase(toDate)) {
					result = true;
				}
			}

			//log.debug("Registration period "+addPeriod+" = "+ Boolean.toString(result));
		}catch(Exception ex){
			throw new Exception("AdditionsQueryDao : Error validating additions period / "+ ex,ex);
		}

		return result;
	}

	/**
	 * This method returns the student's name
	 *
	 * @param StudentNr       The student number
	 */
	public String getStudentName(String studentNr) throws Exception{
		// Return student name
		String name = "";

		String query = "select first_names ||' ' || surname as name" 
				+ " from stu " 
				+ " where nr = " + studentNr ;
		try{
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			name = (String) jdt.queryForObject(query, String.class);

		} catch (Exception ex) {
			throw new Exception("RegQueryDao : Error reading name for studentnr "+ studentNr+"/ "+ ex,ex);
		}

		return name;
	}

	/**
	 * This method returns the student's email address
	 *
	 * @param StudentNr       The student number
	 */
	public String getStudentEmail(String studentNr) throws Exception{

		// Return student email address
		String email = "";

		String query = "select email_address "+
		" from adrph " +
		" where reference_no = " + studentNr +
		" and fk_adrcatcode=1";
		try{
			email = querySingleValue(query,"EMAIL_ADDRESS");

		} catch (Exception ex) {
			throw new Exception("RegQueryDao : Error reading student email / "+ ex,ex);
		}

		return email;
	}

	public static int getCurrentYear() {

		Log log = LogFactory.getLog(RegQueryDAO.class);
		//log.debug("Current year not set. Setting...");
			/* jan = 0, Feb=1 , Nov=10, Dec=11 etc */
		if (Calendar.getInstance().get(Calendar.MONTH) < 10) {
			currentYear = Calendar.getInstance().get(Calendar.YEAR);
		} else {
			/*Removed for test purposes +1*/
			currentYear = (Calendar.getInstance().get(Calendar.YEAR) + 1 );
		}
		//log.debug("Returning "+currentYear+" as the current year for registration");

		return currentYear;
		//return 2018;

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
		
		//log.debug("RegQueryDAO - querySingleValue - Query="+query);

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
