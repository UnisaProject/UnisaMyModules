package za.ac.unisa.lms.dao.assessmentCriteria;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Calendar;

import org.apache.commons.collections.map.ListOrderedMap;
import org.springframework.jdbc.core.JdbcTemplate;

import za.ac.unisa.lms.dao.Gencod;
import za.ac.unisa.lms.dao.StudentSystemGeneralDAO;
import za.ac.unisa.lms.dao.general.PersonnelDAO;
import za.ac.unisa.lms.dao.general.UserDAO;
import za.ac.unisa.lms.dao.general.DepartmentDAO;
import za.ac.unisa.lms.db.StudentSystemDAO;
import za.ac.unisa.lms.domain.assessmentCriteria.*;
import za.ac.unisa.lms.domain.general.Person;
import java.text.SimpleDateFormat; 
import java.util.Date; 


public class AssessmentCriteriaDAO extends StudentSystemDAO{
	
	public FinalMarkComposition getFinalMarkComposition(Short examYear,Short examPeriod,String studyUnit) throws Exception{
		FinalMarkComposition finalMark = new FinalMarkComposition();
			
		String sql = "select year_mark_weight,examination_weight,portfolio_weight,ym_subminimum,pf_subminimum,xam_subminimum,exam_adm_meth_gc224,exam_adm_nr_ass,exam_adm_yr_submin " + 
		             "from finmrk " +
		             "where exam_year =" + examYear +
		             " and mk_exam_period =" + examPeriod +
		             " and mk_study_unit_code = '" + studyUnit.toUpperCase().trim() + "'";
		try{ 
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(sql);

			Iterator i = queryList.iterator();
			while (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				finalMark.setExamYear(examYear);
				finalMark.setExamPeriod(examPeriod);
				finalMark.setStudyUnit(studyUnit);
				finalMark.setYearMarkComponent(replaceNull(data.get("YEAR_MARK_WEIGHT")));
				finalMark.setExamComponent(replaceNull(data.get("EXAMINATION_WEIGHT")));
				finalMark.setPortfolioComponent(replaceNull(data.get("PORTFOLIO_WEIGHT")));	
				finalMark.setYearMarkSubminimum(replaceNull(data.get("YM_SUBMINIMUM")));
				finalMark.setExamSubminimum(replaceNull(data.get("XAM_SUBMINIMUM")));
				finalMark.setPortfolioSubminimum(replaceNull(data.get("PF_SUBMINIMUM")));	
				finalMark.setExamAdmissionMethod(replaceNull(data.get("exam_adm_meth_gc224")));
				finalMark.setExamAdmissionNrAss(replaceNull(data.get("exam_adm_nr_ass")));
				if (finalMark.getExamAdmissionNrAss().equalsIgnoreCase("")){
					finalMark.setExamAdmissionNrAss("0");
				}
				finalMark.setExamAdmissionYearMarkSubMin(replaceNull(data.get("exam_adm_yr_submin")));
				if (finalMark.getExamAdmissionYearMarkSubMin().equalsIgnoreCase("")){
					finalMark.setExamAdmissionYearMarkSubMin("0");
				}
				
				
			}
		}
		catch (Exception ex) {
			throw new Exception("AccessmentCriteriaDAO : Error reading Final mark composition / " + ex,ex);
		}		
		return finalMark;		
	}
	
	public Examination getFirstExamination (Short academicYear,Short semester,String studyUnit) throws Exception{
		Examination exam = new Examination();
			
		String sql="select mk_exam_year,mk_exam_period " +
		           "from sunpdt " +
		           "where mk_academic_year= " + academicYear +
		           " and mk_academic_period= 1" +
		           " and fk_suncode = '" + studyUnit.toUpperCase().trim() + "'" +
		           " and semester_period = " + semester;
		try{ 
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(sql);

			Iterator i = queryList.iterator();
			while (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				if (data.get("MK_EXAM_YEAR")==null){
					}else {exam.setYear(Short.parseShort(data.get("MK_EXAM_YEAR").toString()));}
				if (data.get("MK_EXAM_PERIOD")==null){
				}else {exam.setPeriod(Short.parseShort(data.get("MK_EXAM_PERIOD").toString()));}				
			}
		}
		catch (Exception ex) {
			throw new Exception("Error reading Study unit period detail / " + ex);
		}				
		return exam;
	}	
	
	public Sunpdt getSunpdt (Short academicYear,Short semester,String studyUnit) throws Exception{
		Examination exam = new Examination();
		Sunpdt sunpdt = new Sunpdt();
			
		String sql="select mk_exam_year,mk_exam_period,online_method_gc175,formative_assess_ind_gc197,summative_assess_ind_gc198,non_venue_exam_type_gc195" +
		           " from sunpdt" +
		           " where mk_academic_year= " + academicYear +
		           " and mk_academic_period= 1" +
		           " and fk_suncode = '" + studyUnit.toUpperCase().trim() + "'" +
		           " and semester_period = " + semester;
		try{ 
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(sql);

			Iterator i = queryList.iterator();
			while (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				if (data.get("MK_EXAM_YEAR")==null){
					}else {exam.setYear(Short.parseShort(data.get("MK_EXAM_YEAR").toString()));}
				if (data.get("MK_EXAM_PERIOD")==null){
				}else {exam.setPeriod(Short.parseShort(data.get("MK_EXAM_PERIOD").toString()));}	
				sunpdt.setFirstExam(exam);
				sunpdt.setOnlineMethod(replaceNull(data.get("online_method_gc175")));
				sunpdt.setFormativeAssessInd(replaceNull(data.get("formative_assess_ind_gc197")));
				sunpdt.setSummativeAssessInd(replaceNull(data.get("summative_assess_ind_gc198")));
				sunpdt.setNonVenueBaseExam(replaceNull(data.get("non_venue_exam_type_gc195")));
				
			}
		}
		catch (Exception ex) {
			throw new Exception("Error reading Study unit period detail / " + ex);
		}				
		return sunpdt;
	}	

	public AssLog getLatestLogEntry(Short year, Short period, String studyUnit)throws Exception{
		AssLog asslog = new AssLog();		
		
		String sql = "select type_gc52, action_gc53, comments," +
		"return_email_addr,updated_by,updated_on,request_action_frm" +
		" from asslog " +
		 "where year =" + year +
        " and period =" + period +
        " and mk_study_unit_code = '" + studyUnit.toUpperCase().trim() + "'" +
        " and type_gc52='ASSESSCRIT'" +
        " order by updated_on desc";
        
    try {
    	JdbcTemplate jdt = new JdbcTemplate(getDataSource());
    	List queryList = jdt.queryForList(sql);

    	Iterator i = queryList.iterator();
    	while (i.hasNext()) {
    		ListOrderedMap data = (ListOrderedMap) i.next();
    		asslog.setYear(year);
    		asslog.setPeriod(period);
    		asslog.setStudyUnit(studyUnit);
    		asslog.setType(replaceNull(data.get("type_gc52")));
    		asslog.setAction(replaceNull(data.get("action_gc53")));
    		asslog.setComments(replaceNull(data.get("comments")));
    		asslog.setReturnEmailAddr(replaceNull(data.get("return_email_addr")));
    		asslog.setUpdatedBy(replaceNull(data.get("updated_by")));
    		asslog.setUpdatedOn(replaceNull(data.get("updated_on")));
    		asslog.setRequestActionFrom(replaceNull(data.get("request_action_frm")));  
    		//return first record
    		return asslog;
    		}
    }
    catch (Exception ex) {
    	throw new Exception("AccessmentCriteriaDAO : Error reading ASSLOG / " + ex,ex);
    }		
		return asslog;
	}		
	
	public List<Assignment> getAssignments(Short year,Short period,String studyUnit) throws Exception{
		ArrayList<Assignment> list = new ArrayList<Assignment>();
		
		
		String sql = "select assignment_nr,unique_nr,type,nr_of_questions,negative_mark_fact," +
				     "credit_system,to_char(closing_date,'YYYYMMDD') as dueDate,compulsory,pass_mark_percenta,online_type_gc176, " +
				     "onscreen_mark_flag, to_char(file_release_date,'YYYYMMDD') as fileReleaseDate,file_size_limit, stu_specify_lang, release_results, " +
				     "to_char(pf_open_date,'YYYYMMDD') || decode(pf_open_time,null,' 00:00',to_char(pf_open_time,' HH24:MI')) as pfOpenDate,to_char(final_submit_date,'YYYYMMDD HH24:MI') as finalSubDate, assess_group_gc230 " +
		             "from unqass " +
		             "where year =" + year +
		             " and period =" + period +
		             " and mk_study_unit_code = '" + studyUnit.toUpperCase().trim() + "'" +
		             " and type in ('A','H')";
		try{ 
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(sql);

			Iterator i = queryList.iterator();
			while (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				Assignment assignment = new Assignment();
				assignment.setYear(year);
				assignment.setPeriod(period);
				assignment.setNumber(data.get("ASSIGNMENT_NR").toString());
				assignment.setUniqueNumber(data.get("UNIQUE_NR").toString());
				assignment.setFormat(data.get("TYPE").toString());	
				String onlineType = replaceNull(data.get("online_type_gc176"));
				if (onlineType.equalsIgnoreCase("BL") ||
						onlineType.equalsIgnoreCase("DF")||
						onlineType.equalsIgnoreCase("SA")||
						onlineType.equalsIgnoreCase("XA")||
						onlineType.equalsIgnoreCase("MS")||
						onlineType.equalsIgnoreCase("OR")){
					assignment.setFormat(onlineType);
				}
				assignment.setNumberQuestions(replaceNull(data.get("NR_OF_QUESTIONS")));
				assignment.setNegativeMarkingFactor(data.get("NEGATIVE_MARK_FACT").toString());
				assignment.setCreditSystem(data.get("CREDIT_SYSTEM").toString());
				assignment.setDueDate(data.get("DUEDATE").toString());	
				assignment.setSubsidyAssignment(replaceNull(data.get("COMPULSORY")));
				if (replaceNull(data.get("PASS_MARK_PERCENTA")).equalsIgnoreCase("99")){
					assignment.setCaptureOnStudentSystem(true);
				}else{
					assignment.setCaptureOnStudentSystem(false);
				}
				assignment.setType("");						
				assignment.setRepeatWeight("0");
				assignment.setAegrotatWeight("0");
				assignment.setNormalWeight("0");
				assignment.setOptionality("");
				assignment.setStudentSpecifyLang(replaceNull(data.get("stu_specify_lang")));
				assignment.setOnscreenMarkFlag(replaceNull(data.get("onscreen_mark_flag")));
				assignment.setFileReleaseDate(replaceNull(data.get("fileReleaseDate")));
				assignment.setPfOpenDate(replaceNull(data.get("pfOpenDate")));
				assignment.setReleaseFlag(replaceNull(data.get("release_results")));
				//Change BRD 2016 2015/06/07
				assignment.setMaxFileSize(replaceNull(data.get("file_size_limit")));
				if (assignment.getMaxFileSize().equalsIgnoreCase("0")){
					assignment.setMaxFileSize("");
				}
				assignment.setFinalSubmissionDate(replaceNull(data.get("finalSubDate")));
				assignment.setGroup(replaceNull(data.get("assess_group_gc230")));
				if (assignment.getGroup()==null || assignment.getGroup().trim().equalsIgnoreCase("")){
					if (assignment.getType()!=null && assignment.getType().equalsIgnoreCase("P")){
						assignment.setGroup("S");
					}else{
						assignment.setGroup("F");
					}
				}
				String sqlYrmrk = "select type,repeat_weight,suppl_weight,optionality_gc3,normal_weight,sub_type_gc206 " +
				      "from yrmrk " +
				      "where fk_unq_ass_year=" + year +
				      " and fk_unq_ass_period=" + period +
				      " and fk_unique_nr=" + Integer.parseInt(assignment.getUniqueNumber());
				try{
					List queryListYrmrk = jdt.queryForList(sqlYrmrk);
					Iterator j = queryListYrmrk.iterator();
					while (j.hasNext()){
						ListOrderedMap dataYrmrk = (ListOrderedMap) j.next();
						assignment.setType(replaceNull(dataYrmrk.get("TYPE")));						
						assignment.setRepeatWeight(replaceNull(dataYrmrk.get("REPEAT_WEIGHT")));
						assignment.setAegrotatWeight(replaceNull(dataYrmrk.get("SUPPL_WEIGHT")));
						assignment.setNormalWeight(replaceNull(dataYrmrk.get("NORMAL_WEIGHT")));
						assignment.setOptionality(replaceNull(dataYrmrk.get("OPTIONALITY_GC3")));
						assignment.setSubType(replaceNull(dataYrmrk.get("sub_type_gc206")));
					}
				}				
				catch (Exception ex) {
					throw new Exception("AssessmentCriteriaDAO : Error reading table yrmrk / " + ex);
				}				
				
				list.add(assignment);
			}
		}
		catch (Exception ex) {
			throw new Exception("AssessmentCriteriaDAO : Error reading table unqass / " + ex);
		}		
		return list;		
	}
	
	
	public String getAnswers(Integer year,Short period,Integer uniqueNr)throws Exception{
		String answers = "";
		
		String sql = "select answers " + 
		             "from stumra " +
		             "where fk_unq_ass_year =" + year +
		             " and fk_unq_ass_period =" + period +
		             " and fk_unique_nr=" + uniqueNr +
		             " and mk_student_nr = 1010101";
		try {
	    	   JdbcTemplate jdt = new JdbcTemplate(getDataSource());
				List queryList = jdt.queryForList(sql);

				Iterator i = queryList.iterator();
				while (i.hasNext()) {
					ListOrderedMap data = (ListOrderedMap) i.next();
					answers = replaceNull(data.get("ANSWERS"));
				}
		   }
		catch (Exception ex) {
			throw new Exception("AssessmentCriteriaDAO : Error reading table stumra / " + ex);		
		}
		return answers;
	}
	
	
	public List getPotentialMarkers(Integer year,Short period,String studyUnit) throws Exception {
		ArrayList list = new ArrayList();
		
//		String sql = "select distinct usrsun.novell_user_id" +
//        " from usrsun" +
//        " where usrsun.mk_study_unit_code = '" + studyUnit.toUpperCase().trim() + "'" +
//        " and usrsun.mk_academic_year = " + year +
//        " and usrsun.mk_semester_period = " + period +
//        " and usrsun.system_type = 'L'" +
//        " and usrsun.access_level is not null" +
//        " and usrsun.access_level <> ' '" +
//        " and usrsun.access_level <> 'OBSRV'";
        //" and usrsun.access_level in ('SECDL','PRIML')";
		
		//Change 20150910 - For new marker role
		String sql = "select distinct usrsun.novell_user_id" +
		        " from usrsun" +
		        " where usrsun.mk_study_unit_code = '" + studyUnit.toUpperCase().trim() + "'" +
		        " and usrsun.mk_academic_year = " + year +
		        " and usrsun.mk_semester_period = " + period +
		        " and ((usrsun.system_type = 'L'" +
		        " and usrsun.access_level in ('SECDL','PRIML','CADMN'))" +
		        " or (usrsun.system_type = 'J'" +
		        " and usrsun.access_level = 'MARKER'))";		      
        
		try{ 
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(sql);

			Iterator i = queryList.iterator();
			while (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				Marker marker = new Marker();
				UserDAO daouser = new UserDAO();
				String novellUserId=data.get("NOVELL_USER_ID").toString();
				marker.setPerson(daouser.getPerson(novellUserId));
				//Do not add as potential marker if no Personnel Number or Personnel Number not numeric
				if (marker.getPerson().getPersonnelNumber()!=null && isInteger(marker.getPerson().getPersonnelNumber())){
					String departmentCode = marker.getPerson().getDepartmentCode();
					if (!"".equalsIgnoreCase(departmentCode) && departmentCode!=null){
						DepartmentDAO daodpt = new DepartmentDAO();
						marker.setDepartmentDesc(daodpt.getDepartmentDesc((Short.parseShort(departmentCode))));
					}else{
						marker.setDepartmentDesc("");
					}
					marker.setMarkPercentage("0");	
					marker.setStatus("Active");
					if (marker.getPerson().getResignDate()!=null && 
							!marker.getPerson().getResignDate().equalsIgnoreCase("")&&
							marker.getStatus().equalsIgnoreCase("Active")){
							Calendar currentDate = Calendar.getInstance();
							Date nowDate = currentDate.getTime();
							
							SimpleDateFormat formatter=new SimpleDateFormat("yyyyMMdd");
							String str_date=marker.getPerson().getResignDate();
					        Date resignDate = (Date)formatter.parse(str_date); 
					        
					        if (resignDate.after(nowDate)){
					        	marker.setStatus("Active");
					        }else{
					        	marker.setStatus("Inactive");
					        }
					}
					if (marker.getStatus().equalsIgnoreCase("Active")){
						list.add(marker);
					}
					
				}				
			}
		}
		catch (Exception ex) {
			throw new Exception("AssessmentCriteriaDAO : Error reading table usrsun / " + ex);
		}		
		return list;		
	}
	
	public List getFileFormats(Integer uniqueNr) throws Exception{
		ArrayList list = new ArrayList();
		
		String sql = "select extention,description" +
		" from onasfm" +
		" where mk_unique_nr =" + uniqueNr;
		
		  try {
		    	JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		    	List queryList = jdt.queryForList(sql);

		    	Iterator i = queryList.iterator();
		    	while (i.hasNext()) {
		    		ListOrderedMap data = (ListOrderedMap) i.next();
		    		FileFormat fileFormat = new FileFormat();
		    		fileFormat.setExtention(replaceNull(data.get("extention")));
		    		fileFormat.setDescription(replaceNull(data.get("description")));
		    		list.add(fileFormat);
		    	}
		    }
		    catch (Exception ex) {
		    	throw new Exception("AccessmentCriteriaDAO : Error reading ONASFM / " + ex,ex);
		    }	
		return list;
	}	
	
	public List getSubmissionLanguages(Integer uniqueNr) throws Exception{
		ArrayList list = new ArrayList<SubmissionLanguage>();
		
		String sql = "select language_gc203,other_lang_desc" +
		" from onasln" +
		" where mk_unique_nr =" + uniqueNr;
		
		  try {
		    	JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		    	List queryList = jdt.queryForList(sql);

		    	Iterator i = queryList.iterator();
		    	while (i.hasNext()) {
		    		ListOrderedMap data = (ListOrderedMap) i.next();
		    		SubmissionLanguage language = new SubmissionLanguage();
		    		language.setLangangeGc203(replaceNull(data.get("language_gc203")));
		    		language.setOtherLangDesc(replaceNull(data.get("other_lang_desc")));
		    		list.add(language);
		    	}
		    }
		    catch (Exception ex) {
		    	throw new Exception("AccessmentCriteriaDAO : Error reading ONASFM / " + ex,ex);
		    }	
		return list;
	}	
	
	
	public List getMarkers(Integer dummyYear,Integer year,Short period,Integer uniqueNr,String studyUnit) throws Exception{
		ArrayList list = new ArrayList();		
		
		String sql = "select assmrk.mk_lecturer_nr,assmrk.mark_percentage" +
	    " from assmrk" +
        " where assmrk.fk_unq_ass_year =" + dummyYear +
        " and assmrk.fk_unq_ass_period =" + period +
        " and assmrk.fk_unique_nr =" + uniqueNr +
        " and assmrk.mark_percentage > 0" +
        " and assmrk.mk_lecturer_nr > 0";
                
		try{ 
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(sql);

			Iterator i = queryList.iterator();
			while (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				Marker marker = new Marker();
				PersonnelDAO daopers = new PersonnelDAO();
				Integer persno=Integer.parseInt((data.get("MK_LECTURER_NR").toString()));
 				Person staffPerson = new Person();
 				Person usrPerson = new Person();
				//marker.setPerson(daopers.getPerson(persno));
				staffPerson = daopers.getPerson(persno);
				if (staffPerson.getNovellUserId()==null || staffPerson.getNovellUserId().equalsIgnoreCase("")){
					usrPerson = new Person();
					usrPerson = daopers.getPersonnelfromUSR(String.valueOf(persno));
				}
				if (usrPerson.getPersonnelNumber()!=null && usrPerson.getPersonnelNumber().equalsIgnoreCase("")){
					marker.setPerson(usrPerson);
				}else{
					marker.setPerson(staffPerson);
				}				
				String departmentCode = marker.getPerson().getDepartmentCode();
				if (!"".equalsIgnoreCase(departmentCode) && departmentCode!=null){
					DepartmentDAO daodpt = new DepartmentDAO();
					marker.setDepartmentDesc(daodpt.getDepartmentDesc((Short.parseShort(departmentCode))));
				}else{
					marker.setDepartmentDesc("");
				}
				marker.setMarkPercentage(data.get("MARK_PERCENTAGE").toString());
				if (marker.getPerson().getNovellUserId()==null || marker.getPerson().getNovellUserId()==""){
					marker.setStatus("Inactive");
				}else{
//					String sqlUSRSUN = "select count(*) " +
//		             "from usrsun " +
//		             "where novell_user_id = '" + marker.getPerson().getNovellUserId().toUpperCase().trim() + "'" +
//		             " and mk_study_unit_code = '" + studyUnit.toUpperCase().trim() + "'" +
//		             " and mk_academic_year = " + year +
//		             " and mk_semester_period = " + period +
//		             " and system_type = 'L'" +
//				     " and usrsun.access_level is not null" +
//				     " and usrsun.access_level <> ' '" +
//				     " and usrsun.access_level <> 'OBSRV'";
//		             //" and access_level in ('SECDL','PRIML')";
					
					//Change 20150910 - For new marker role
					String sqlUSRSUN = "select count(*) " +
				             "from usrsun " +
				             "where novell_user_id = '" + marker.getPerson().getNovellUserId().toUpperCase().trim() + "'" +
				             " and mk_study_unit_code = '" + studyUnit.toUpperCase().trim() + "'" +
				             " and mk_academic_year = " + year +
				             " and mk_semester_period = " + period +
				             " and ((usrsun.system_type = 'L'" +
				             " and usrsun.access_level in ('SECDL','PRIML','CADMN'))" +
				             " or (usrsun.system_type = 'J'" +
				             " and usrsun.access_level = 'MARKER'))";	
					JdbcTemplate jdtSubQuery = new JdbcTemplate(getDataSource());
					int result = jdtSubQuery.queryForInt(sqlUSRSUN) ;
					if (result == 0) {
						marker.setStatus("Inactive");
					} else {
						marker.setStatus("Active");
					}
				}
				if (marker.getPerson().getResignDate()!=null && 
						!marker.getPerson().getResignDate().equalsIgnoreCase("")&&
						marker.getStatus().equalsIgnoreCase("Active")){
						Calendar currentDate = Calendar.getInstance();
						Date nowDate = currentDate.getTime();
						
						SimpleDateFormat formatter=new SimpleDateFormat("yyyyMMdd");
						String str_date=marker.getPerson().getResignDate();
				        Date resignDate = (Date)formatter.parse(str_date); 
				        
				        if (resignDate.after(nowDate)){
				        	marker.setStatus("Active");
				        }else{
				        	marker.setStatus("Inactive");
				        }
				 }
				String arrayLang[] = null;
				List listLang = new ArrayList<String>();
				                 
				sql = "select language_gc203,other_lang_desc" +
				" from assmrkln" +
				" where mk_unique_nr=" + uniqueNr +
				" and mk_lecturer_nr=" + marker.getPerson().getPersonnelNumber();
				
				 try {
				    	jdt = new JdbcTemplate(getDataSource());
				    	queryList = jdt.queryForList(sql);

				    	Iterator j = queryList.iterator();
				    	int count = jdt.getFetchSize();
				    	arrayLang = new String[20];				    	
				    	int index=0;
				    	while (j.hasNext()) {
				    		ListOrderedMap data1 = (ListOrderedMap) j.next();
				    		SubmissionLanguage language = new SubmissionLanguage();
				    		language.setLangangeGc203(replaceNull(data1.get("language_gc203")));
				    		language.setOtherLangDesc(replaceNull(data1.get("other_lang_desc")));
				    		if (language.getLangangeGc203().equalsIgnoreCase("OTHER")){
				    			arrayLang[index] = language.getOtherLangDesc();
				    			listLang.add(language.getOtherLangDesc());
				    		}else{
				    			arrayLang[index] = language.getLangangeGc203();
				    			StudentSystemGeneralDAO dao = new StudentSystemGeneralDAO();
				    			Gencod gencod = new Gencod();
				    			gencod=dao.getGenCode("203", language.getLangangeGc203());
				    			listLang.add(gencod.getEngDescription().toUpperCase());				    			
				    		}				    		
				    		index=index + 1;
				    	}
				    }
				    catch (Exception ex) {
				    	throw new Exception("AccessmentCriteriaDAO : Error reading ASSMRKLN / " + ex,ex);
				    }	
				marker.setListMarkLanguages(listLang); 
				//Delete null value array references
				String[] remove = {null};
				List listTemp = new ArrayList(Arrays.asList(arrayLang));
				listTemp.removeAll(new ArrayList(Arrays.asList(remove)));
				arrayLang = (String[]) listTemp.toArray(new String[listTemp.size()]);
				marker.setLanguageSelection(arrayLang);
				list.add(marker);
			}
		}
		catch (Exception ex) {
			throw new Exception("AssessmentCriteriaDAO : Error reading table assmrk / " + ex);
		}		
		return list;		
	}
	
	public String getControlDueDate(Integer year,short period,String type,String fromOrTo) throws Exception{
    String date = "";		
		
		String sql = "select to_char(from_date,'YYYYMMDD') as from_date, to_char(to_date,'YYYYMMDD') as to_date " +
		" from regdat" +
		" where academic_year =" + year +
        " and semester_period =" + period +
        " and type = '" + type.toUpperCase().trim() + "'";
               
    try {
    	JdbcTemplate jdt = new JdbcTemplate(getDataSource());
    	List queryList = jdt.queryForList(sql);

    	Iterator i = queryList.iterator();
    	while (i.hasNext()) {
    		ListOrderedMap data = (ListOrderedMap) i.next();
    		if ("TO".equalsIgnoreCase(fromOrTo)){ 
    			date=replaceNull(data.get("to_date"));
    		}
    		if ("FROM".equalsIgnoreCase(fromOrTo)){
    			date=replaceNull(data.get("from_date"));
    		}
    	}
    }
    catch (Exception ex) {
    	throw new Exception("AccessmentCriteriaDAO : Error reading REGDAT / " + ex,ex);
    }		
		return date;
	}


	private String replaceNull(Object object){
		String stringValue="";
		if (object==null){			
		}else{
			stringValue=object.toString();
		}			
		return stringValue;
	}
	

	private boolean isInteger(String stringValue) {
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