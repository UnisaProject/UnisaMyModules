package za.ac.unisa.lms.tools.mdrpm.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.map.ListOrderedMap;
import org.apache.struts.util.LabelValueBean;
import org.springframework.jdbc.core.JdbcTemplate;

import com.sun.java.swing.plaf.nimbus.ArrowButtonPainter;

import za.ac.unisa.lms.dao.Gencod;
import za.ac.unisa.lms.dao.StudentSystemGeneralDAO;
import za.ac.unisa.lms.dao.general.PersonnelDAO;
import za.ac.unisa.lms.dao.general.UserDAO;
import za.ac.unisa.lms.db.StudentSystemDAO;
import za.ac.unisa.lms.domain.general.Person;
import za.ac.unisa.lms.tools.mdrpm.forms.*;

public class MdRpmDao extends StudentSystemDAO {

	public College getCollege(Short dptCode) throws Exception {

		College college = new College();

		String sql = "select colleg.code,colleg.eng_description,colleg.abbreviation"
				+ " from colleg, dpt"
				+ " where colleg.code = dpt.college_code"
				+ " and dpt.code = " + dptCode;

		try {
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(sql);

			Iterator i = queryList.iterator();
			while (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				college.setCode(Short.parseShort((String) data.get("CODE")
						.toString()));
				college.setDescription(data.get("ENG_DESCRIPTION").toString());
				college.setAbbreviation(data.get("ABBREVIATION").toString());
			}
		} catch (Exception ex) {
			throw new Exception("MdRpmDAO : Error reading College / " + ex, ex);
		}
		return college;

	}
	 
	public List<LabelValueBean> getSupervisors(int collegeCode, String studyUnit, String qualCode, String filter) throws Exception {
		
		List<LabelValueBean> list = new ArrayList<LabelValueBean>();
		
		String sql ="select temp.supPersno, staff.surname || ' ' || staff.initials || ' ' || staff.title as supName" +
					" from" + 
					" (select distinct(mk_promotor_nr) as supPersno from dispro, sun" + 
					" where supervisor_flag='Y'" +
					" and dispro.fk_study_unit_code=sun.code" +
					" and sun.college_code =" + collegeCode +
					" and dispro.type='P'";
		
		if (studyUnit != null && !studyUnit.trim().equalsIgnoreCase("")){
			sql = sql + " and dispro.fk_study_unit_code='" + studyUnit + "'";
		}		
					
		sql = sql +	" and exists" +
					" (select * from stusun" +
					" where stusun.status_code in ('RG','FC')" +
					" and stusun.mk_study_unit_code = sun.code";
		
		if (qualCode != null && !qualCode.trim().equalsIgnoreCase("")){
			sql = sql + " and stusun.mk_qualification_c='" + qualCode + "'";
		}
		
		sql = sql +	" and sun.research_flag='P'" +
					" and stusun.fk_student_nr = dispro.FK_STUDENT_NR)) temp, staff" +
					" where temp.supPersno=staff.persno";
		
		if (filter != null && !filter.trim().equalsIgnoreCase("")){
			sql = sql + " staff.surname like '" + filter + "'";
		}
		
		
		sql = sql +	" order by supName";

		try {
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(sql);

			Iterator i = queryList.iterator();
			while (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				String value ="";
				String label="";
				value = data.get("supPersno").toString();
				label = data.get("supName").toString() + " (" +  value + ")";
				list.add(new org.apache.struts.util.LabelValueBean(label, value));
			}
		} catch (Exception ex) {
			throw new Exception("MdRpmDAO : Error reading SupervisorList / " + ex, ex);
		}
		list.add(0, new org.apache.struts.util.LabelValueBean("ALL", null));
		return list;	
		
		
	}	
	
	
	public Qualification getQualification(String qualCode, String specCode ) throws Exception {
		Qualification qualification = new Qualification();	
		Speciality speciality = new Speciality();
		
		String sql= "select long_eng_descripti from grd where code='" + qualCode + "'";
		
		try {
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(sql);

			Iterator i = queryList.iterator();
			while (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				qualification.setCode(qualCode);
				qualification.setDescription(replaceNull(data.get("long_eng_descripti")));				
			}
		} catch (Exception ex) {
			throw new Exception("MdRpmDAO : Error reading grd / " + ex, ex);
		}
		
		if (specCode!=null && !specCode.trim().equalsIgnoreCase("")){			
			
			sql = "select english_descriptio from quaspc where mk_qualification_c='" + qualCode  + "'" +
					" and speciality_code='" + specCode + "'";
			
			try {
				JdbcTemplate jdt = new JdbcTemplate(getDataSource());
				List queryList = jdt.queryForList(sql);

				Iterator i = queryList.iterator();
				while (i.hasNext()) {
					ListOrderedMap data = (ListOrderedMap) i.next();
					speciality.setCode(specCode);
					speciality.setDescription(replaceNull(data.get("english_descriptio")));				
				}
			} catch (Exception ex) {
				throw new Exception("MdRpmDAO : Error reading quaspc / " + ex, ex);
			}			
		}
		
		qualification.setSpeciality(speciality);
		return qualification;
	}

	public Student getStudent(int studentNr) throws Exception {

		Student student = new Student();

		String sql = "select mk_title,surname,initials,TO_CHAR(birth_date,'YYYY-MM-DD') as bday,"
				+ "MK_COUNTRY_CODE" + " from stu" + " where nr=" + studentNr;

		try {
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(sql);

			Iterator i = queryList.iterator();
			while (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				String title = "";
				String surname = "";
				String initials = "";
				title = replaceNull(data.get("mk_title"));
				surname = replaceNull(data.get("surname"));
				initials = replaceNull(data.get("initials"));
				student.setNumber(studentNr);
				student.setName(surname + ' ' + initials + ' ' + title);
				student.setPrintName(title + ' ' + initials + ' ' + surname);
				student.setBirthDate(replaceNull(data.get("bday")));
				student.setCountryCode(replaceNull(data.get("MK_COUNTRY_CODE")));
				Contact contact = new Contact();
				contact = getContactDetails(studentNr, 1);
				student.setContactInfo(contact);
			}
		} catch (Exception ex) {
			throw new Exception("Error reading STU / " + ex);
		}
		return student;

	}

	public Contact getContactDetails(Integer referenceNo, Integer category)
			throws Exception {

		Contact contact = new Contact();

		String sql = "select home_number,work_number,fax_number,email_address,cell_number"
				+ " from adrph"
				+ " where reference_no="
				+ referenceNo
				+ " and fk_adrcatcode=" + category;

		try {
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(sql);

			Iterator i = queryList.iterator();
			while (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				contact.setHomeNumber(replaceNull(data.get("home_number"))
						.trim());
				contact.setWorkNumber(replaceNull(data.get("work_number"))
						.trim());
				contact.setFaxNumber(replaceNull(data.get("fax_number")).trim());
				contact.setCellNumber(replaceNull(data.get("cell_number"))
						.trim());
				contact.setEmailAddress(replaceNull(data.get("email_address"))
						.trim());
			}
		} catch (Exception ex) {
			throw new Exception("Error reading ADRPH / " + ex);
		}
		return contact;
	}

	
	public List<Promotor> getPromotorList(int studentNr,String studyUnit) throws Exception {
		List<Promotor> list = new ArrayList<Promotor>();
		
		String sql = "select mk_promotor_nr, supervisor_flag" +
				" from dispro" +
				" where fk_student_nr="	+ studentNr	+ 
				" and fk_study_unit_code='"	+ studyUnit	+ "'" + 
				" and type='P'";
				
		try {
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(sql);

			Iterator i = queryList.iterator();
			while (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				Promotor promotor = new Promotor();
				int persNo;
				persNo = (Integer.parseInt((data.get("mk_promotor_nr").toString())));
				Person person = new Person();
				PersonnelDAO dao = new PersonnelDAO();
				person = dao.getPersonnelFromSTAFF(persNo);				
				promotor.setPerson(person);
				String supervisorFlag = (replaceNull(data.get("supervisor_flag")));
				if (supervisorFlag.toUpperCase().trim().equalsIgnoreCase("Y")){
					promotor.setIsSupervisor(true);
				}else{
					promotor.setIsSupervisor(false);
				}
				list.add(promotor);
				
			}
		} catch (Exception ex) {
			throw new Exception(
					"MdRpmDAO : Error reading promotor list / " + ex,
					ex);
		}
		
		return list;
	}
	
	public List<Qsprout> getRoutingList(String qualCode,String specCode) throws Exception {
		List<Qsprout> list = new ArrayList<Qsprout>();
		
		String sql = "select seq_nr,staff_number,final_flag" +
				" from qsprout" +
				" where mk_qual_code='"	+ qualCode	+ "'" + 
				" and type='RES'";
		
		if (specCode!=null && !specCode.trim().equalsIgnoreCase("")){
			sql = sql + " and mk_spes_code='" + specCode + "'";
		}
		
		sql = sql + " order by final_flag";
				
		try {
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(sql);

			Iterator i = queryList.iterator();
			while (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				Qsprout record = new Qsprout();
				int persNo;
				persNo = (Integer.parseInt((data.get("staff_number").toString())));
				Person person = new Person();
				PersonnelDAO dao = new PersonnelDAO();
				person = dao.getPersonnelFromSTAFF(persNo);				
				record.setPerson(person);
				record.setLevel(data.get("final_flag").toString());
				record.setSeqNr(Integer.parseInt((data.get("seq_nr").toString())));
				list.add(record);
				
			}
		} catch (Exception ex) {
			throw new Exception(
					"MdRpmDAO : Error reading promotor list / " + ex,
					ex);
		}
		
		return list;
	}
	
	public boolean existsStudis(int studentNr,String studyUnit) throws Exception {
		
		String sql="select count(*)" +
				" from studis" +
				" where mk_student_nr="	+ studentNr	+ 
				" and mk_study_unit_code='"	+ studyUnit	+ "'"; 
		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		int result = jdt.queryForInt(sql) ;
		if (result == 0) {
			return false;
		} else {
			return true;
		}
	}
	
	public boolean isExamsQa1(String userId){
		String sql = "select count(*) " +
        "from usr,funtyp " +
        "where usr.novell_user_code = '" + userId.toUpperCase().trim() + "'" +
        " and usr.nr = funtyp.fk_usrnr" +
        " and funtyp.fk_functnr=795" +
        " and funtyp.type='QA1'";      
		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		int result = jdt.queryForInt(sql) ;
		if (result == 0) {
			return false;
		} else {
			return true;
		}
	}
	
	public String getDissertationTitle(int studentNr,String studyUnit) throws Exception {
		String title="";
		
		String sql = "select title" +
				" from studis" +
				" where mk_student_nr="	+ studentNr	+ 
				" and mk_study_unit_code='"	+ studyUnit	+ "'"; 
				
		try {
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(sql);

			Iterator i = queryList.iterator();
			while (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				Promotor promotor = new Promotor();
				int persNo;
				title = (replaceNull(data.get("title")));						
			}
		} catch (Exception ex) {
			throw new Exception(
					"MdRpmDAO : Error reading promotor list / " + ex,
					ex);
		}
		
		return title;
	}
	
	public List<TrackRecord> getRPMTrackingList(int studentNr, String studyUnit,int seqNr, List<Gencod> resultTypeList) throws Exception {
		List <TrackRecord> trackingList = new ArrayList<TrackRecord>();
		
		String sql = "select to_char(timestamp,'YYYY/MM/DD') as trackDate, status_code,pers_nr,current_level, assigned_to_pers_no,assigned_to_level, recommended_value, comments" +
		" from mdtrac" +
		" where mk_student_nr=" + studentNr +
		" and mk_study_unit_code='" + studyUnit + "'" +
		" and app_sequence_nr=" + seqNr +
		" and tracking_type='RPMRESULT'";		
		
		try {
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(sql);

			Iterator i = queryList.iterator();
			while (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				TrackRecord record = new TrackRecord();
				int persNo;
				record.setSeqNr(seqNr);
				record.setDate(replaceNull(data.get("trackDate")));
			
				Person person = new Person();	
				PersonnelDAO dao = new PersonnelDAO();
				
				String current = replaceNull(data.get("pers_nr"));
				
				if (isInteger(current)){
					person = dao.getPersonnelFromSTAFF(Integer.parseInt(current));
				}
				record.setCurrentPerson(person);
								
				String assignedTo = replaceNull(data.get("assigned_to_pers_no"));	
				person = new Person();
				
				if (isInteger(assignedTo)){
					person = dao.getPersonnelFromSTAFF(Integer.parseInt(assignedTo));
				}
				record.setAssignedToPerson(person);
				
				record.setStatusCode(replaceNull(data.get("status_code")));
				record.setCurrentLevel(replaceNull(data.get("current_level")));
				record.setAssignedToLevel(replaceNull(data.get("assignned_to_level")));
				String result = replaceNull(data.get("recommended_value"));
				for (int j=0; j < resultTypeList.size();j++){
					Gencod gencod = new Gencod();
					gencod = (Gencod)resultTypeList.get(j);
					if (gencod.getCode().equalsIgnoreCase(result)){
						record.setProposedResult(gencod);
						j=resultTypeList.size();
					}
				}			
				record.setComment(replaceNull(data.get("comments")));
				trackingList.add(record);
				
			}
		} catch (Exception ex) {
			throw new Exception(
					"MdRpmDAO : Error reading tracking list / " + ex,
					ex);
		}
		
		return trackingList;
		
		
	}
	
	public List<RpmListRecord> getRpmList(int collegeCode, String studyUnit,
		String qualificationCode, String specialityCode, String criteria, String studentNr, String supervisor, String user) throws Exception {
		List<RpmListRecord> rpmList = new ArrayList<RpmListRecord>();

		String sql="select studyUnit, qualCode,specCode,studentNumber,studentName,seqNr,proposedResult,workStatus,trackStatus,referLevel,referToPersno,referTo,disTitle" +
		" from" +
		" ((select stusun.mk_study_unit_code as studyUnit," +
		"stusun.mk_qualification_c as qualCode, stuann.speciality_code as specCode," +
		"stu.nr as studentNumber,stu.mk_title || ' ' || stu.initials || ' ' || stu.surname as studentName," +
		"0 as seqNr," +
		"' ' as proposedResult," +
		"' ' as workStatus," +
		"' ' as trackStatus," +
		"' ' as referLevel," +
		"0 as referToPersno," +
		"' ' as referTo," +
		" studis.title as disTitle," +
		"grd.college_code as collegeCode" +
		" from stu, stusun, stuann, sun, grd, studis" +
		" where stusun.status_code in ('RG','FC')" +
		" and stusun.mk_study_unit_code = sun.code" +
		" and sun.research_flag='P'" +
		" and stusun.fk_student_nr = stu.nr" +
		" and stusun.mk_qualification_c=grd.code" +
		" and stusun.fk_academic_year = stu.mk_last_academic_y" +
		" and stusun.fk_student_nr = stuann.mk_student_nr" +
		" and stusun.fk_academic_year = stuann.mk_academic_year" +
		" and stuann.mk_academic_period = 1" +
		" and grd.college_code =" + collegeCode +
		" and studis.mk_student_nr(+)=stusun.fk_student_nr" +
		" and studis.mk_study_unit_code(+)=stusun.mk_study_unit_code" + 
		" and not exists (select * from mdworklist where mdworklist.worklist_type='RPMRESULT' and" + 
		" mdworklist.mk_student_nr=stusun.fk_student_nr and mdworklist.mk_study_unit_code=stusun.mk_study_unit_code))" + 
		" union" +
		" (select mdworklist.MK_STUDY_UNIT_CODE as studyUnit,mdworklist.MK_QUALIFICATION_C as qualCode," + 
		"mdworklist.SPECIALITY_CODE as specCode,mdworklist.mk_student_nr as studentNumber," + 
		"stu.mk_title || ' ' || stu.initials || ' ' || stu.surname as studentName," +
		"mdworklist.seq as seqNr," +
		"mdworklist.recommended_value as proposedResult," +
		"mdworklist.STATUS_GC210 as workStatus," +
		"mdworklist.TRACKING_STATUS_GC136 as trackStatus," +
		"mdworklist.REFER_TO_LEVEL as referLevel," +
		"mdworklist.refer_to_persno as referToPersno," +
		"staff.title || ' ' || staff.initials || ' ' || staff.surname as referTo," +
		"studis.title as disTitle," +
		"sun.COLLEGE_CODE as collegeCode" +
		" from stu, mdworklist, studis, sun, staff" +
		" where mdworklist.worklist_type='RPMRESULT'" + 
		" and stu.nr = mdworklist.mk_student_nr" +
		" and sun.code=mdworklist.mk_study_unit_code" +
		" and sun.COLLEGE_CODE=" + collegeCode +
		" and studis.mk_student_nr(+)=mdworklist.mk_student_nr" +
		" and studis.mk_study_unit_code(+)=mdworklist.mk_study_unit_code" +
		" and mdworklist.refer_to_persno=staff.persno(+))) temp" +
		" where collegeCode=" + collegeCode;  //add to have a where clause

		if (studyUnit != null && !studyUnit.trim().equalsIgnoreCase("")) {
			sql = sql + " and studyUnit='" + studyUnit + "'";
		}
		if (qualificationCode != null
				&& !qualificationCode.trim().equalsIgnoreCase("")) {
			sql = sql + " and qualCode='" + qualificationCode
					+ "'";
	}
		
		if (specialityCode != null
				&& !specialityCode.trim().equalsIgnoreCase("")) {
			sql = sql + " and specCode='" + specialityCode
					+ "'";
		}	
		if (studentNr != null
				&& !studentNr.trim().equalsIgnoreCase("")) {
			sql = sql + " and studentNumber=" + Integer.parseInt(studentNr);
		}	
		
		
		if (criteria.equalsIgnoreCase("ALL")){
			//select all
		}else if (criteria.equalsIgnoreCase("NOTSTARTED")){
			sql = sql + " and workStatus=' '";
		}else if (criteria.equalsIgnoreCase("REFER")){
			sql = sql + " and workStatus='REFER' and trackStatus='RS'";
		}else if (criteria.equalsIgnoreCase("REVIEW")){
			sql = sql + " and workStatus='REVIEW' and trackStatus='RW'";
		}else if (criteria.equalsIgnoreCase("INPROGRESS")){
			sql = sql + " and workStatus in ('REVIEW','REFER') and trackStatus in ('RW','RS')";
		}else if (criteria.equalsIgnoreCase("FINAL")){
			sql = sql + " and workStatus in ('FINAL') and trackStatus in ('FS')";
		}else if (criteria.equalsIgnoreCase("FORME")){
			sql = sql + " and workStatus in ('REVIEW','REFER') and trackStatus in ('RW','RS') and referToPersno=" + user;
		}

		try {
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(sql);

			Iterator i = queryList.iterator();
			while (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				RpmListRecord record = new RpmListRecord();
				WorklistRecord worklist = new WorklistRecord();
				record.setStudentNr(Integer.parseInt(data.get("studentNumber")
						.toString()));
				record.setStudentName(data.get("studentName").toString());
				record.setQualCode(data.get("qualCode").toString());
				record.setSpecCode(replaceNull(data.get("specCode")));
				record.setStudyUnit(data.get("studyUnit").toString());
				record.setTitle(replaceNull(data.get("disTitle")));
				record.setProposedResult(replaceNull(data.get("proposedResult")));
				worklist.setStatus(replaceNull(data.get("workStatus")));
				worklist.setTrackingStatus(replaceNull(data.get("trackStatus")));	
				worklist.setSeqNr(Integer.parseInt(data.get("seqNr").toString()));
				worklist.setReferToLevel(replaceNull(data.get("referLevel")));
				worklist.setReferToPersno(replaceNull(data.get("referTo")));
				String studentSups = "";
				List<LabelValueBean> supervisorList = new ArrayList<LabelValueBean>();
				supervisorList = getStudentSupervisors(record.getStudentNr(),
						record.getStudyUnit());
				for (int j=0; j < supervisorList.size(); j++){
					if (studentSups.equalsIgnoreCase("")){
						studentSups = ((LabelValueBean)supervisorList.get(j)).getLabel();
					}else{
						studentSups = studentSups + ", " + ((LabelValueBean)supervisorList.get(j)).getLabel();
					}					
				}				
				
				record.setSupervisor(studentSups);				
				record.setWorkListItem(worklist);
				
				//search criteria supervisor selected
				if (supervisor != null && !supervisor.equalsIgnoreCase("")){
					for (int j=0; j < supervisorList.size(); j++){
						if (supervisor.equalsIgnoreCase(((LabelValueBean)supervisorList.get(j)).getValue())){
							rpmList.add(record);
							j = supervisorList.size();
						}
						
					}
				}else{
					rpmList.add(record);
				}				

			}
		} catch (Exception ex) {
			throw new Exception("MdRpmDAO : Error reading rpmList / " + ex, ex);
		}
		return rpmList;
	}

	public List<LabelValueBean> getStudentSupervisors(int studentNr, String studyUnit)
			throws Exception {
		List<LabelValueBean> list = new ArrayList<LabelValueBean>();

		String sql = "select dispro.mk_promotor_nr, staff.title || ' ' || staff.initials || ' ' || staff.surname as supName" + 
				" from dispro, staff" + 
				" where dispro.fk_student_nr=" + studentNr + 
				" and dispro.fk_study_unit_code='" + studyUnit + "'" + 
				" and dispro.type='P'" + 
				" and dispro.supervisor_flag='Y'" +
				" and dispro.mk_promotor_nr=staff.persno";

		try {
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(sql);

			Iterator i = queryList.iterator();
			while (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				
				String value =replaceNull(data.get("mk_promotor_nr").toString());
				String label=replaceNull(data.get("supName").toString());
				list.add(new org.apache.struts.util.LabelValueBean(label.trim(), value));				
			}
		} catch (Exception ex) {
			throw new Exception(
					"MdRpmDAO : Error reading student supervisor(s) / " + ex,
					ex);
		}
		return list;

	}
	
	public void recordSignoff(RpmRecord rpm, TrackRecord tracking) throws Exception {
		//get current year
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy");		
		Calendar calendar = Calendar.getInstance();	
		Date currentDate=calendar.getTime();					
		int year = Integer.parseInt(formatter.format(currentDate));
		
		Connection connection =null;		
		
		
		try{
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());				
						
			connection = jdt.getDataSource().getConnection();
			connection.setAutoCommit(false);
			boolean worklistItemExists=false;
			
			String sql = "select count(*) from mdworklist where" +
					" mk_student_nr=" + rpm.getStudent().getNumber() +
					" and mk_study_unit_code='" + rpm.getStudyunit().getCode() + "'"  +
					" and worklist_type='RPMRESULT'" +
					" and seq =" + tracking.getSeqNr();			
			
			int result = jdt.queryForInt(sql) ;
			if (result == 0) {
				worklistItemExists = false;
			} else {
				worklistItemExists = true;
			}
	
			
			//determine if insert MDWORKLIST record or update
			if (!worklistItemExists){
					//create MDWORKLIST record
				 sql ="insert into MDWORKLIST (MK_STUDENT_NR,MK_STUDY_UNIT_CODE,WORKLIST_TYPE,SEQ,MK_QUALIFICATION_C,SPECIALITY_CODE," + 
							"REQUEST_DATE,REQUESTOR,STATUS_GC210,RECOMMENDED_VALUE,TRACKING_STATUS_GC136,REFER_TO_PERSNO,REFER_TO_LEVEL)" +
							" values (?,?,?,?,?,?,systimestamp,?,?,?,?,?,?)";
					   
					   PreparedStatement ps = connection.prepareStatement(sql);
					   
					   ps.setInt(1, rpm.getStudent().getNumber()); 
					   ps.setString(2, rpm.getStudyunit().getCode()); 
					   ps.setString(3, "RPMRESULT"); 
					   ps.setInt(4, tracking.getSeqNr());
					   ps.setString(5,rpm.getQualification().getCode());
					   ps.setString(6, rpm.getQualification().getSpeciality().getCode());					  
					   ps.setString(7, tracking.getCurrentPerson().getPersonnelNumber());
					   ps.setString(8, "REFER");
					   ps.setString(9, tracking.getProposedResult().getCode());
					   ps.setString(10, tracking.getStatusCode());
					   ps.setString(11, tracking.getAssignedToPerson().getPersonnelNumber());
					   ps.setString(12, tracking.getAssignedToLevel());
					   
					   ps.executeUpdate();	
					
			}else{
				//update MDWORKLIST
				String worklistStatus="";
				if (tracking.getStatusCode().equalsIgnoreCase("RS")){
					worklistStatus="REFER";
				}
				if (tracking.getStatusCode().equalsIgnoreCase("RW")){
					worklistStatus="REVIEW";
				}
				if (tracking.getStatusCode().equalsIgnoreCase("FS")){
					worklistStatus="FINAL";
				}
				
				sql = "update MDWORKLIST set STATUS_GC210=?,RECOMMENDED_VALUE=?,TRACKING_STATUS_GC136=?,REFER_TO_PERSNO=?," +
				"REFER_TO_LEVEL=?" +
				" where MK_STUDENT_NR=" + rpm.getStudent().getNumber() +
				" and MK_STUDY_UNIT_CODE='" + rpm.getStudyunit().getCode() + "'" +
				" and WORKLIST_TYPE='RPMRESULT'";
				
				 
					   PreparedStatement ps = connection.prepareStatement(sql);
					   
					   ps.setString(1, worklistStatus); 
					   ps.setString(2, tracking.getProposedResult().getCode()); 
					   ps.setString(3, tracking.getStatusCode()); 
					   ps.setString(4, tracking.getAssignedToPerson().getPersonnelNumber());
					   ps.setString(5, tracking.getAssignedToLevel());
					   
					   ps.executeUpdate();	
			}		  
       	   
       	   //insert record into mdtrac
       	   sql = "insert into MDTRAC (MK_STUDENT_NR,APP_SEQUENCE_NR,TIMESTAMP,STATUS_CODE,USER_CODE,PERS_NR,EMAIL_TO,COMMENTS," +
       			 "ASSIGNED_TO_PERS_NO,TRACKING_TYPE,MK_STUDY_UNIT_CODE,CURRENT_LEVEL,ASSIGNED_TO_LEVEL,RECOMMENDED_VALUE)" +
       	         " values (?,?,systimestamp,?,?,?,?,?,?,?,?,?,?,?)";
       	   
       	   		PreparedStatement ps = connection.prepareStatement(sql);
       	   
       	   		ps.setInt(1, rpm.getStudent().getNumber());
       	   		ps.setInt(2, tracking.getSeqNr()); 
       	   		ps.setString(3,tracking.getStatusCode()); 
       	   		ps.setString(4, "");
       	   		ps.setString(5, tracking.getCurrentPerson().getPersonnelNumber());
       	   		ps.setString(6, tracking.getAssignedToPerson().getEmailAddress());
       	   		ps.setString(7, tracking.getComment());
       	   		ps.setString(8, tracking.getAssignedToPerson().getPersonnelNumber());
       	   		ps.setString(9, "RPMRESULT");
       	   		ps.setString(10, rpm.getStudyunit().getCode());
       	   		ps.setString(11, tracking.getCurrentLevel());
       	   		ps.setString(12, tracking.getAssignedToLevel());
       	   		ps.setString(13, tracking.getProposedResult().getCode()); 
       
       	   ps.executeUpdate();	
       	   
       	   if (tracking.getStatusCode().equalsIgnoreCase("FS") && (tracking.getProposedResult().getCode().equalsIgnoreCase("CR") || tracking.getProposedResult().getCode().equalsIgnoreCase("NC"))){
       		   //write M&D activity
       		   
	       		int activityCode=0;
	       		String studiesApproved="N";
	       		if (tracking.getProposedResult().getCode().equalsIgnoreCase("CR")){
	       			activityCode=10;
	       			studiesApproved="Y";
	       		} else if (tracking.getProposedResult().getCode().equalsIgnoreCase("NC")){
	       			activityCode=9;
	       			studiesApproved="Y";
	       		}
	       		   
	       		sql ="INSERT INTO mdactv (MK_STUDENT_NR,MK_STUDY_UNIT_CODE,STAFF_NUMBER,ACTIVITY_CODE,ENTRY_TIMESTAMP,ACTIVITY_DATE,FEEDBACK_DATE,COMMENTS)" +
						  " VALUES (?,?,?,?,systimestamp,sysdate,null,?)";
	       		
	       		ps = connection.prepareStatement(sql);			
	       		  		       		
	       		ps.setInt(1, rpm.getStudent().getNumber());
	       		ps.setString(2, rpm.getStudyunit().getCode());
	       		ps.setInt(3, Integer.parseInt(tracking.getCurrentPerson().getPersonnelNumber()));
	       		ps.setInt(4,activityCode);
	       		ps.setString(5, tracking.getComment());	
	       		
	       		ps.executeUpdate();
	       		
	       		sql ="update stu set post_graduate_stud = ? where nr = ?";
	       		
	       		ps = connection.prepareStatement(sql);
	    		
	    		ps.setString(1, studiesApproved);
	    		ps.setInt(2,rpm.getStudent().getNumber());
	    		
	    		ps.executeUpdate();
		
       	   }       	   
       	   
       	   
       	  connection.commit();
		  connection.setAutoCommit(true);
		  connection.close();
			
		}
		catch (java.sql.SQLException ex) {
			if (connection!=null){
					connection.rollback();	
					connection.setAutoCommit(true);
					connection.close();
					if (ex.getErrorCode() == 1){
						recordSignoff(rpm, tracking); 
					}
					throw new Exception("MdRpmDAO : Data has been rollback, Error inserting records into MDWORKLIST, MDTRAC / " + ex,ex);
			}
		}
		catch (Exception ex) {
			if (connection!=null){
					connection.rollback();	
					connection.setAutoCommit(true);
					connection.close();
					throw new Exception("MdRpmDAO : Data has been rollback, Error inserting records into MDWORKLIST, MDTRAC / " + ex,ex);
			}else{
				throw new Exception("MdRpmDAO : Error reading MDWORKLIST to get latest sequence number / " + ex,ex);
			}
		}
			
	}
	
	public List<SupervisorHistoryRecord> getSupervisorHistory(int studentNr, String studyUnit) throws Exception {
		List<SupervisorHistoryRecord> list = new ArrayList<SupervisorHistoryRecord>();
		
		String sql ="select to_char(timestamp,'YYYY/MM/DD') as updatedOn, pers_nr, app_sequence_nr, comments, staff.title || ' ' || staff.initials || ' ' || staff.surname as updatedByName" +
				" from mdtrac, staff" +
				" where mdtrac.tracking_type='SUPERVISOR'" + 
				" and mk_study_unit_code='" + studyUnit + "'" +
				" and mk_student_nr=" + studentNr +
				" and mdtrac.pers_nr = staff.persno" + 
				" order by timestamp";
		
		try {
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(sql);
	
			Iterator i = queryList.iterator();
			while (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				SupervisorHistoryRecord historyRecord = new SupervisorHistoryRecord();
				
				historyRecord.setUpdatedOn(replaceNull(data.get("updatedOn")));
				historyRecord.setUpdatedBy(replaceNull(data.get("updatedByName")));
				historyRecord.setComment(replaceNull(data.get("comments")));
				
				int seq = Integer.parseInt(data.get("app_sequence_nr").toString());
				List <Promotor> promotorList = new ArrayList<Promotor>();
				
				sql = "select MK_STUDENT_NR,MK_STUDY_UNIT_CODE,SEQ,MK_PROMOTOR,SUPERVISOR_FLAG from prodispro" +
						" where MK_STUDENT_NR=" + studentNr +
						" and MK_STUDY_UNIT_CODE='" + studyUnit + "'" +
						" and SEQ=" + seq +
						" and dispro_type='P'";
				try {
					jdt = new JdbcTemplate(getDataSource());
					queryList = jdt.queryForList(sql);
			
					Iterator j = queryList.iterator();
					while (j.hasNext()) {
						ListOrderedMap data2 = (ListOrderedMap) j.next();
						Promotor promotor = new Promotor();
						
						String supervisorFlag = (replaceNull(data2.get("SUPERVISOR_FLAG"))).trim();
						if (supervisorFlag.equalsIgnoreCase("Y")){
							promotor.setIsSupervisor(true);
						}else {
							promotor.setIsSupervisor(false);
						}						
						Person person = new Person();						
						PersonnelDAO dao = new PersonnelDAO();
						person = dao.getPerson(Integer.parseInt((replaceNull(data2.get("MK_PROMOTOR"))).trim()));
						if (person.getName()==null || person.getName().trim().equalsIgnoreCase("")){
							person.setPersonnelNumber((replaceNull(data.get("MK_PROMOTOR"))).trim());
							person.setName("");
							person.setNameReverse("");
							person.setEmailAddress("");
						}
						promotor.setPerson(person);
						promotorList.add(promotor);
					}
				} catch (Exception ex) {
					throw new Exception("MdRpmDAO : Error reading Supervisor History List / " + ex, ex);
				}	
				historyRecord.setPromotorList(promotorList);
				list.add(historyRecord );
			}
		} catch (Exception ex) {
			throw new Exception("MdRpmDAO : Error reading Supervisor History List / " + ex, ex);
		}	

		return list;
	}
	
	public List<TitleHistoryRecord> getTitleHistory(int studentNr, String studyUnit) throws Exception {
		List<TitleHistoryRecord> list = new ArrayList<TitleHistoryRecord>();
		
		String sql ="select to_char(timestamp,'YYYY/MM/DD') as updatedOn, pers_nr, recommended_value as title, comments, staff.title || ' ' || staff.initials || ' ' || staff.surname as updatedByName" +
				" from mdtrac, staff" +
				" where mdtrac.tracking_type='TITLE'" + 
				" and mk_study_unit_code='" + studyUnit + "'" +
				" and mk_student_nr=" + studentNr +
				" and mdtrac.pers_nr = staff.persno" + 
				" order by timestamp";
		
		try {
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(sql);
	
			Iterator i = queryList.iterator();
			while (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				TitleHistoryRecord historyRecord = new TitleHistoryRecord();
				historyRecord.setTitle(replaceNull(data.get("title")));
				historyRecord.setUpdatedOn(replaceNull(data.get("updatedOn")));
				historyRecord.setUpdatedBy(replaceNull(data.get("updatedByName")));
				historyRecord.setComment(replaceNull(data.get("comments")));
				
				list.add(historyRecord );
			}
		} catch (Exception ex) {
			throw new Exception("MdRpmDAO : Error reading Title History List / " + ex, ex);
		}	

		return list;
	}
	
	public void recordTitleChange(RpmRecord rpm, TrackRecord tracking) throws Exception {
				
		Connection connection =null;		
		
		try{
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());				
						
			connection = jdt.getDataSource().getConnection();
			connection.setAutoCommit(false);		
			
			//Get the latest MDWORKLIST seq nr for student title change
			String sql = "select max(seq) from mdworklist where" +
					" mk_student_nr=" + rpm.getStudent().getNumber() +
					" and mk_study_unit_code='" + rpm.getStudyunit().getCode() + "'"  +
					" and worklist_type='TITLE'"; 
			int seq = jdt.queryForInt(sql) ;		
			
			seq = seq + 1;
						
			//create MDWORKLIST record
				 sql ="insert into MDWORKLIST (MK_STUDENT_NR,MK_STUDY_UNIT_CODE,WORKLIST_TYPE,SEQ,MK_QUALIFICATION_C,SPECIALITY_CODE," + 
							"REQUEST_DATE,REQUESTOR,STATUS_GC210,RECOMMENDED_VALUE,TRACKING_STATUS_GC136,REFER_TO_PERSNO,REFER_TO_LEVEL)" +
							" values (?,?,?,?,?,?,systimestamp,?,?,?,?,?,?)";
					   
					   PreparedStatement ps = connection.prepareStatement(sql);
					   
					   ps.setInt(1, rpm.getStudent().getNumber()); 
					   ps.setString(2, rpm.getStudyunit().getCode()); 
					   ps.setString(3, "TITLE"); 
					   ps.setInt(4, seq);
					   ps.setString(5,rpm.getQualification().getCode());
					   ps.setString(6, rpm.getQualification().getSpeciality().getCode());					  
					   ps.setString(7, tracking.getCurrentPerson().getPersonnelNumber());
					   ps.setString(8, "FINAL");
					   ps.setString(9, rpm.getTitle());
					   ps.setString(10, "FS");
					   ps.setString(11, "");
					   ps.setString(12, "");
					   
					   ps.executeUpdate();	
			
       	   
       	   //create record into mdtrac
       	   sql = "insert into MDTRAC (MK_STUDENT_NR,APP_SEQUENCE_NR,TIMESTAMP,STATUS_CODE,USER_CODE,PERS_NR,EMAIL_TO,COMMENTS," +
       			 "ASSIGNED_TO_PERS_NO,TRACKING_TYPE,MK_STUDY_UNIT_CODE,CURRENT_LEVEL,ASSIGNED_TO_LEVEL,RECOMMENDED_VALUE)" +
       	         " values (?,?,systimestamp,?,?,?,?,?,?,?,?,?,?,?)";
       	   
       	   		ps = connection.prepareStatement(sql);
       	   
       	   		ps.setInt(1, rpm.getStudent().getNumber());
       	   		ps.setInt(2, seq); 
       	   		ps.setString(3, "FS"); 
       	   		ps.setString(4, "");
       	   		ps.setString(5, tracking.getCurrentPerson().getPersonnelNumber());
       	   		ps.setString(6, "");
       	   		ps.setString(7, tracking.getComment());
       	   		ps.setString(8, "");
       	   		ps.setString(9, "TITLE");
       	   		ps.setString(10, rpm.getStudyunit().getCode());
       	   		ps.setString(11, "");
       	   		ps.setString(12, "");
       	   		ps.setString(13, rpm.getTitle()); 
       
       	   ps.executeUpdate();	
       	   
	       	//Update STUDIS
	       	sql ="update studis set title = ? where mk_student_nr = ? and mk_study_unit_code=?";
	   		
	   		ps = connection.prepareStatement(sql);
			
			ps.setString(1, rpm.getTitle());
			ps.setInt(2,rpm.getStudent().getNumber());
			ps.setString(3, rpm.getStudyunit().getCode());
			
			ps.executeUpdate();  
	       	   
	       	connection.commit();
			connection.setAutoCommit(true);
			connection.close();
			
		}
		catch (java.sql.SQLException ex) {
			if (connection!=null){
					connection.rollback();	
					connection.setAutoCommit(true);
					connection.close();
					if (ex.getErrorCode() == 1){
						recordSignoff(rpm, tracking); 
					}
					throw new Exception("MdRpmDAO : Data has been rollback, Error inserting records into MDWORKLIST, MDTRAC - title changes/ " + ex,ex);
			}
		}
		catch (Exception ex) {
			if (connection!=null){
					connection.rollback();	
					connection.setAutoCommit(true);
					connection.close();
					throw new Exception("MdRpmDAO : Data has been rollback, Error inserting records into MDWORKLIST, MDTRAC - title changes/ " + ex,ex);
			}else{
				throw new Exception("MdRpmDAO : Error reading MDWORKLIST to get latest sequence number - title changes/ " + ex,ex);
			}
		}
			
	}
	
	public void recordSupervisorChange(RpmRecord rpm, TrackRecord tracking) throws Exception {
		
		Connection connection =null;		
		
		try{
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());				
						
			connection = jdt.getDataSource().getConnection();
			connection.setAutoCommit(false);		
			
			//Get the latest MDWORKLIST seq nr for student title change
			String sql = "select max(seq) from mdworklist where" +
					" mk_student_nr=" + rpm.getStudent().getNumber() +
					" and mk_study_unit_code='" + rpm.getStudyunit().getCode() + "'"  +
					" and worklist_type='SUPERVISOR'"; 
			int seq = jdt.queryForInt(sql) ;		
			
			seq = seq + 1;
						
			//create MDWORKLIST record
				 sql ="insert into MDWORKLIST (MK_STUDENT_NR,MK_STUDY_UNIT_CODE,WORKLIST_TYPE,SEQ,MK_QUALIFICATION_C,SPECIALITY_CODE," + 
							"REQUEST_DATE,REQUESTOR,STATUS_GC210,RECOMMENDED_VALUE,TRACKING_STATUS_GC136,REFER_TO_PERSNO,REFER_TO_LEVEL)" +
							" values (?,?,?,?,?,?,systimestamp,?,?,?,?,?,?)";
					   
					   PreparedStatement ps = connection.prepareStatement(sql);
					   
					   ps.setInt(1, rpm.getStudent().getNumber()); 
					   ps.setString(2, rpm.getStudyunit().getCode()); 
					   ps.setString(3, "SUPERVISOR"); 
					   ps.setInt(4, seq);
					   ps.setString(5,rpm.getQualification().getCode());
					   ps.setString(6, rpm.getQualification().getSpeciality().getCode());					  
					   ps.setString(7, tracking.getCurrentPerson().getPersonnelNumber());
					   ps.setString(8, "FINAL");
					   ps.setString(9, "");
					   ps.setString(10, "FS");
					   ps.setString(11, "");
					   ps.setString(12, "");
					   
					   ps.executeUpdate();	
			
       	   
       	   //create record into mdtrac
       	   sql = "insert into MDTRAC (MK_STUDENT_NR,APP_SEQUENCE_NR,TIMESTAMP,STATUS_CODE,USER_CODE,PERS_NR,EMAIL_TO,COMMENTS," +
       			 "ASSIGNED_TO_PERS_NO,TRACKING_TYPE,MK_STUDY_UNIT_CODE,CURRENT_LEVEL,ASSIGNED_TO_LEVEL,RECOMMENDED_VALUE)" +
       	         " values (?,?,systimestamp,?,?,?,?,?,?,?,?,?,?,?)";
       	   
       	   		ps = connection.prepareStatement(sql);
       	   
       	   		ps.setInt(1, rpm.getStudent().getNumber());
       	   		ps.setInt(2, seq); 
       	   		ps.setString(3, "FS"); 
       	   		ps.setString(4, "");
       	   		ps.setString(5, tracking.getCurrentPerson().getPersonnelNumber());
       	   		ps.setString(6, "");
       	   		ps.setString(7, tracking.getComment());
       	   		ps.setString(8, "");
       	   		ps.setString(9, "SUPERVISOR");
       	   		ps.setString(10, rpm.getStudyunit().getCode());
       	   		ps.setString(11, "");
       	   		ps.setString(12, "");
       	   		ps.setString(13, ""); 
       
       	   ps.executeUpdate();	
       	   
       	  //First delete all dispro records
       	   sql = "delete from dispro where fK_STUDENT_NR=? and FK_STUDY_UNIT_CODE=?"; 
     	   
     	   		ps = connection.prepareStatement(sql);
     	   		
     	   		ps.setInt(1, rpm.getStudent().getNumber());
     	   		ps.setString(2, rpm.getStudyunit().getCode());
     	   		
     	   		ps.executeUpdate();
     	   		
       	   //Insert values into PRODISPRO for history purposes
       	   for (int i=0; i < rpm.getPromotorList().size(); i++) {
       		   Promotor promotor = new Promotor();
       		   promotor = (Promotor)rpm.getPromotorList().get(i);
       		   
       		   sql = "insert into prodispro (MK_STUDENT_NR,MK_STUDY_UNIT_CODE,SEQ,MK_PROMOTOR,SUPERVISOR_FLAG,DISPRO_TYPE,MK_DEPARTMENT_CODE)" +
           	 		" values (?,?,?,?,?,?,null)";
           	   
           	   		ps = connection.prepareStatement(sql);
           	   		
           	   		ps.setInt(1, rpm.getStudent().getNumber());
           	   		ps.setString(2, rpm.getStudyunit().getCode());
           	   		ps.setInt(3, seq);            	   		
       	   			ps.setString(4, promotor.getPerson().getPersonnelNumber()); 
       	   			if (promotor.getIsSupervisor()){
       	   				ps.setString(5, "Y");
       	   			}else{
       	   				ps.setString(5, "N");
       	   			}       	   			
       	   			ps.setString(6, "P");       	   			
       
       	   			ps.executeUpdate();
       	   		
       	   		 //Insert records into DISPRO
       	   		 sql = "insert into dispro (MK_PROMOTOR_NR,SUPERVISOR_FLAG,FK_STUDENT_NR,FK_STUDY_UNIT_CODE,TYPE,MK_DEPARTMENT_CODE)" +
             	 		" values (?,?,?,?,?,null)";
             	   
             	   	ps = connection.prepareStatement(sql);
             	   	
             	   	ps.setString(1, promotor.getPerson().getPersonnelNumber()); 
             	   	if (promotor.getIsSupervisor()){
         	   			ps.setString(2, "Y");
         	   		}else{
         	   			ps.setString(2, "N");
         	   		}       
             	   	ps.setInt(3, rpm.getStudent().getNumber());
             	   	ps.setString(4, rpm.getStudyunit().getCode());
         	   		ps.setString(5, "P");       	   			
         
         	   		ps.executeUpdate();
           	   
       	   } 
	         
	      connection.commit();
		  connection.setAutoCommit(true);
		  connection.close();
			
		}
		catch (java.sql.SQLException ex) {
			if (connection!=null){
					connection.rollback();	
					connection.setAutoCommit(true);
					connection.close();
					if (ex.getErrorCode() == 1){
						recordSignoff(rpm, tracking); 
					}
					throw new Exception("MdRpmDAO : Data has been rollback, Error inserting records into MDWORKLIST, MDTRAC - supervisor changes / " + ex,ex);
			}
		}
		catch (Exception ex) {
			if (connection!=null){
					connection.rollback();	
					connection.setAutoCommit(true);
					connection.close();
					throw new Exception("MdRpmDAO : Data has been rollback, Error inserting records into MDWORKLIST, MDTRAC - supervisor changes / " + ex,ex);
			}else{
				throw new Exception("MdRpmDAO : Error reading MDWORKLIST to get latest sequence number - supervisor changes / " + ex,ex);
			}
		}
			
	}
	
	public ArrayList<LabelValueBean> getStaffList(String staffNr, String surname) 
			throws Exception{
				// Return staff name
				ArrayList<LabelValueBean> staffList = new ArrayList<LabelValueBean>();
				String query = "";
				
				if(!"".equals(surname.trim())){

					query = "select persno,surname || ' '|| initials ||' ' ||  title as name, long_eng_desc " +
					" from staff, dpt " +
					" where surname like '" + surname.toUpperCase() +
					"%' and mk_dept_code=code";					
					
				}else{

					query = "select persno,surname || ' '|| initials ||' ' ||  title as name, long_eng_desc " +
					" from staff, dpt " +
					" where persno = " + staffNr.trim() +
					" and mk_dept_code=code";					
					
				}

				JdbcTemplate jdt = new JdbcTemplate(getDataSource());
				List queryList = jdt.queryForList(query);

				Iterator i = queryList.iterator();
				while (i.hasNext()) {
					ListOrderedMap data = (ListOrderedMap) i.next();
					String code = "";
					if ( data.get("PERSNO").toString().trim().length()==7){
						code = "0" + data.get("PERSNO").toString();
					}else{
						code = data.get("PERSNO").toString();
					}
					String desc = data.get("NAME").toString()+ ", "+ data.get("LONG_ENG_DESC").toString().trim();
					staffList.add(new org.apache.struts.util.LabelValueBean(code + " - "
								+ desc, code + desc));
				}
				
				return staffList;
	}

	private String replaceNull(Object object) {
		String stringValue = "";
		if (object == null) {
		} else {
			stringValue = object.toString().trim();
		}
		return stringValue;
	}
	
	public boolean isInteger(String stringValue) {
		try
		{
			Integer i = Integer.parseInt(stringValue);
			return true;
		}	
		catch(NumberFormatException e)
		{}
		return false;
	}

}
