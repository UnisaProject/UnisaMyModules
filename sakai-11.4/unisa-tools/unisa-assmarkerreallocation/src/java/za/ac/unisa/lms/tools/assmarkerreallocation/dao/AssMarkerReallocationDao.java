package za.ac.unisa.lms.tools.assmarkerreallocation.dao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.map.ListOrderedMap;
import org.apache.struts.util.LabelValueBean;
import org.springframework.jdbc.core.JdbcTemplate;

import za.ac.unisa.lms.dao.general.DepartmentDAO;
import za.ac.unisa.lms.dao.general.PersonnelDAO;
import za.ac.unisa.lms.dao.general.UserDAO;
import za.ac.unisa.lms.db.StudentSystemDAO;
import za.ac.unisa.lms.domain.assessmentCriteria.Assignment;
import za.ac.unisa.lms.domain.general.Person;
import za.ac.unisa.lms.tools.assmarkerreallocation.actions.Utilities;
import za.ac.unisa.lms.tools.assmarkerreallocation.dao.*;
import za.ac.unisa.lms.tools.assmarkerreallocation.forms.*;

public class AssMarkerReallocationDao  extends LoginDAO{
	
	public List getPotentialMarkers(Integer year,Short period,String studyUnit) throws Exception {
		ArrayList list = new ArrayList();
	     String sql="select distinct novell_user_id,access_level from usrsun"+ 
				 " where mk_study_unit_code = '"+studyUnit.toUpperCase().trim()+"'  and mk_academic_year ="+ year+
				      " and mk_semester_period ="+ period +
				      " and ((system_type = 'L'"+
				      " and access_level in ('PRIML','SECDL','CADMN')) "+
				      " OR ( system_type = 'J' and access_level ='MARKER'))";
        try{ 
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(sql);

			Iterator i = queryList.iterator();
			while (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				MarkerModel markerModel = new MarkerModel();
				UserDAO daouser = new UserDAO();
				String novellUserId=data.get("NOVELL_USER_ID").toString();
				String role=data.get("access_level").toString();
				markerModel.setPerson(daouser.getPerson(novellUserId));
				markerModel.setRole(role);
				//Do not add as potential marker if no Personnel Number or Personnel Number not numeric
				if (markerModel.getPerson().getPersonnelNumber()!=null && Utilities.isInteger(markerModel.getPerson().getPersonnelNumber())){
					String departmentCode = markerModel.getPerson().getDepartmentCode();
					if (!"".equalsIgnoreCase(departmentCode) && departmentCode!=null){
						DepartmentDAO daodpt = new DepartmentDAO();
						markerModel.setDepartmentDesc(daodpt.getDepartmentDesc((Short.parseShort(departmentCode))));
					}else{
						markerModel.setDepartmentDesc("");
					}
					markerModel.setMarkPercentage("0");	
					markerModel.setStatus("Active");
					if (markerModel.getPerson().getResignDate()!=null && 
							!markerModel.getPerson().getResignDate().equalsIgnoreCase("")&&
							markerModel.getStatus().equalsIgnoreCase("Active")){
							Calendar currentDate = Calendar.getInstance();
							Date nowDate = currentDate.getTime();
							
							SimpleDateFormat formatter=new SimpleDateFormat("yyyyMMdd");
							String str_date=markerModel.getPerson().getResignDate();
					        Date resignDate = (Date)formatter.parse(str_date); 
					        
					        if (resignDate.after(nowDate)){
					        	markerModel.setStatus("Active");
					        }else{
					        	markerModel.setStatus("Inactive");
					        }
					}
					if (markerModel.getStatus().equalsIgnoreCase("Active")){
						list.add(markerModel);
					}
					
				}				
			}
		}
		catch (Exception ex) {
			throw new Exception("AssMarkerReallocationDAO : : Error reading table usrsun / " + ex);
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
        " and (assmrk.nr_returned > 0 or assmrk.mark_percentage > 0)" +
        " and assmrk.mk_lecturer_nr > 0";
                
		try{ 
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(sql);

			Iterator i = queryList.iterator();
			while (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				MarkerModel markerModel = new MarkerModel();
				PersonnelDAO daopers = new PersonnelDAO();
				Integer persno=Integer.parseInt((data.get("MK_LECTURER_NR").toString()));
 				Person staffPerson = new Person();
 				Person usrPerson = new Person();
				//marker.setPerson(daopers.getPerson(persno));
				staffPerson = daopers.getPerson(persno);
				if (staffPerson.getPersonnelNumber()==null || staffPerson.getPersonnelNumber().equalsIgnoreCase("")){
					usrPerson = new Person();
					usrPerson = daopers.getPersonnelfromUSR(String.valueOf(persno));
					if (usrPerson.getPersonnelNumber()!=null && usrPerson.getPersonnelNumber().equalsIgnoreCase("")){
						markerModel.setPerson(usrPerson);
					}else{
						Person person = new Person();
						person.setPersonnelNumber(persno.toString());
						person.setName("Unknown");
						person.setNameReverse("Unknown");
						person.setNovellUserId("");
						markerModel.setPerson(person);
					}
				}else {
					markerModel.setPerson(staffPerson);
				}
				String departmentCode = markerModel.getPerson().getDepartmentCode();
				if (!"".equalsIgnoreCase(departmentCode) && departmentCode!=null){
					DepartmentDAO daodpt = new DepartmentDAO();
					markerModel.setDepartmentDesc(daodpt.getDepartmentDesc((Short.parseShort(departmentCode))));
				}else{
					markerModel.setDepartmentDesc("");
				}
				markerModel.setMarkPercentage(data.get("MARK_PERCENTAGE").toString());
				if (markerModel.getPerson().getNovellUserId()==null || markerModel.getPerson().getNovellUserId()==""){
					markerModel.setStatus("Inactive");
					markerModel.setRole("NONE");
				}else{
					String sqlUSRSUN = "select access_level " +
		             "from usrsun " +
		             " where novell_user_id = '" + markerModel.getPerson().getNovellUserId().toUpperCase().trim() + "'" +
		             " and mk_study_unit_code = '" + studyUnit.toUpperCase().trim() + "'" +
		             " and mk_academic_year = " + year +
		             " and mk_semester_period = " + period +
		             " and ((system_type = 'L'"+
				     " and access_level in ('PRIML','SECDL','CADMN')) "+
				     " OR ( system_type = 'J' and access_level ='MARKER'))";
					try{ 
						queryList = jdt.queryForList(sqlUSRSUN);
						Iterator j = queryList.iterator();
						markerModel.setRole("NONE");
						while (j.hasNext()) {
							ListOrderedMap dataUSRSUN = (ListOrderedMap) j.next();
							markerModel.setRole(dataUSRSUN.get("access_level").toString());
						}
						if (markerModel.getRole().equalsIgnoreCase("NONE")) {
							markerModel.setStatus("Inactive");
						} else {
							markerModel.setStatus("Active");
						}
					}
					catch (Exception ex) {
						throw new Exception("AssMarkerReallocationDAO : : Error reading table usrsun getting access_level / " + ex);
					}	
				}
				if (markerModel.getPerson().getResignDate()!=null && 
						!markerModel.getPerson().getResignDate().equalsIgnoreCase("")&& 
						markerModel.getStatus().equalsIgnoreCase("Active")){
						Calendar currentDate = Calendar.getInstance();
						Date nowDate = currentDate.getTime();
						
						SimpleDateFormat formatter=new SimpleDateFormat("yyyyMMdd");
						String str_date=markerModel.getPerson().getResignDate();
				        Date resignDate = (Date)formatter.parse(str_date); 
				        
				        if (resignDate.after(nowDate)){
				        	markerModel.setStatus("Active");
				        }else{
				        	markerModel.setStatus("Inactive");
				        }
				 }
				list.add(markerModel);
			}
		}
		catch (Exception ex) {
			throw new Exception("AssMarkerReallocationDAO : Error reading table assmrk / " + ex);
		}		
		return list;		
	}
	
	public boolean isAssPlanAuthorised(String studyUnit,Short year,Short period) {
		String sql = "select count(*) " +
		             "from asslog " +
		             "where year = " + year +
		             " and period = " + period +
		             " and mk_study_unit_code = '" + studyUnit.toUpperCase().trim() + "'" +
		             " and TYPE_GC52 = 'ASSESSCRIT'" +
		             " and ACTION_GC53 ='AUTHORISED'";
		           
		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		int result = jdt.queryForInt(sql) ;
		if (result == 0) {
			return false;
		} else {
			return true;
		}
	}
	
	public Assignment getAssignment(String studyUnit,Short year,Short period,Short assignmentNr) throws Exception {
		Assignment assignment = new Assignment();
		
		String sql = "select assignment_nr,unique_nr,type,nr_of_questions,negative_mark_fact," +
	     "credit_system,to_char(closing_date,'YYYYMMDD') as dueDate,compulsory,pass_mark_percenta" + 
        " from unqass" +
        " where year =" + year +
        " and period =" + period +
        " and mk_study_unit_code = '" + studyUnit.toUpperCase().trim() + "'" +
        " and assignment_nr =" + assignmentNr;
		try{ 
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(sql);

			Iterator i = queryList.iterator();
			while (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				assignment.setYear(year);
				assignment.setPeriod(period);
				assignment.setNumber(data.get("ASSIGNMENT_NR").toString());
				assignment.setUniqueNumber(data.get("UNIQUE_NR").toString());
				assignment.setFormat(data.get("TYPE").toString());					
			}
		}
		catch (Exception ex) {
			throw new Exception("AssMarkerReallocationDAO : Error reading unqass / " + ex);
		}		
		return assignment;	
	}
	
	public String getPrimaryLecturer(String studyUnit,Short year,Short period)throws Exception{
		String userId="";
		
		String sql = "select distinct usrsun.novell_user_id" +
	    " from usrsun" +
	    " where usrsun.mk_study_unit_code = '" + studyUnit.toUpperCase().trim() + "'" +
	    " and usrsun.mk_academic_year = " + year +
	    " and usrsun.mk_semester_period = " + period +
	    " and usrsun.system_type = 'L'" +
	    " and usrsun.access_level ='PRIML'";
		
		try{ 
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(sql);

			Iterator i = queryList.iterator();
			while (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				userId=data.get("novell_user_id").toString();								
			}
		}
		catch (Exception ex) {
			throw new Exception("AssMarkerReallocationDAO : Error reading usrsun / " + ex);
		}		
		return userId;	
	}	
	
public List getFileFormats(String studyUnit, int unique_nr) throws Exception{
		
		ArrayList list = new ArrayList();
		
		String sql = "select extention from onasfm " +
		" where mk_study_unit_code='" + studyUnit.toUpperCase().trim() + "'" +
		" and mk_unique_nr=" + unique_nr;		
		
		try{ 
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(sql);
			String fileExtention="";
			Iterator i = queryList.iterator();
			while (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				fileExtention = data.get("extention").toString();
				list.add(fileExtention);				
			}
		}
		catch (Exception ex) {
			throw new Exception("AssMarkerReallocationDao : Error reading table onasfm/ " + ex);
		}		
		return list;	
	}

public int getStudentsRegistered(String studyUnit,Short year,Short period)throws Exception{
				
		String sql = "select count(*)" +
	    " from stusun" +
	    " where FK_ACADEMIC_YEAR = " + year +
	    " and SEMESTER_PERIOD = " + period + 
	    " and MK_STUDY_UNIT_CODE = '" + studyUnit.toUpperCase().trim() + "'" +
	    " and STATUS_CODE  in ('RG','FC')";
		
		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		int result = jdt.queryForInt(sql) ;
				
		return result;
	}

public int getNrAssSubmitted(String studyUnit,Short year,Short period,Short assignmentNr,Integer persno){
	
	
	String sql = " select count(*) from assdct a,stuass b,dctstu c" +
	" where a.MK_ACADEMIC_YEAR  =" + year +
	" and a.MK_ACADEMIC_PERIOD =" + period +
	" and a.MK_STUDY_UNIT_CODE ='" + studyUnit + "'" +
	" and a.ASSIGNMENT_NR  =" + assignmentNr +
	" and a.MK_LECTURER_NR  =" + persno +
	" and A.NR = C.FK_ASS_DCT_NR" +
	" and A.MK_ACADEMIC_YEAR=B.FK_ACADEMIC_YEAR" +
	" and b.fk_academic_period=1" +
	" and A.MK_ACADEMIC_PERIOD=B.FK_SEMESTER_PERIOD" +
	" and A.MK_STUDY_UNIT_CODE=B.FK_STUDY_UNIT_CODE" +
	" and A.ASSIGNMENT_NR=B.MK_ASSIGNMENT_NR" +
	" and C.MK_STUDENT_NR =B.FK_STUDENT_NR" +
	" and B.CANCELLATION_CODE='NC'";
	
	JdbcTemplate jdt = new JdbcTemplate(getDataSource());
	int result = jdt.queryForInt(sql) ;
			
	return result;
}

public MarkerDetailRecord getMarkerDetail(String studyUnit,Short year,Short period,Short assignmentNr,Integer persno)throws Exception{
	MarkerDetailRecord markerDetail = new MarkerDetailRecord();
	int nrStudentSubmitted=0;
	int nrMarked=0;
	int totalPercentage=0;
	
	String sql = " select to_char(b.DATE_RETURNED,'YYYYMMDD') as return_date, b.PERCENTAGE_OBTAINE  from assdct a,stuass b,dctstu c" +
	" where a.MK_ACADEMIC_YEAR  =" + year +
	" and a.MK_ACADEMIC_PERIOD =" + period +
	" and a.MK_STUDY_UNIT_CODE ='" + studyUnit + "'" +
	" and a.ASSIGNMENT_NR  =" + assignmentNr +
	" and a.MK_LECTURER_NR  =" + persno +
	" and A.NR = C.FK_ASS_DCT_NR" +
	" and A.MK_ACADEMIC_YEAR=B.FK_ACADEMIC_YEAR" +
	" and b.fk_academic_period=1" +
	" and A.MK_ACADEMIC_PERIOD=B.FK_SEMESTER_PERIOD" +
	" and A.MK_STUDY_UNIT_CODE=B.FK_STUDY_UNIT_CODE" +
	" and A.ASSIGNMENT_NR=B.MK_ASSIGNMENT_NR" +
	" and C.MK_STUDENT_NR =B.FK_STUDENT_NR" +
	" and B.CANCELLATION_CODE='NC'";
	
	try{ 
		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		List queryList = jdt.queryForList(sql);

		Iterator i = queryList.iterator();
		while (i.hasNext()) {
			ListOrderedMap data = (ListOrderedMap) i.next();
			nrStudentSubmitted = nrStudentSubmitted + 1;
			String returnDate = replaceNull(data.get("return_date"));
			String percentageObtained = replaceNull(data.get("PERCENTAGE_OBTAINE"));
			
			if (returnDate.equalsIgnoreCase("") || returnDate.equalsIgnoreCase("00010101")){
			}else{
				nrMarked = nrMarked + 1;
			}	
			
			if (Utilities.isInteger(percentageObtained)){
				totalPercentage = totalPercentage + Integer.parseInt(percentageObtained);
			}			
		}
	}
	catch (Exception ex) {
		throw new Exception("AssMarkerReallocationDAO : Error reading unqass / " + ex);
	}	
	markerDetail.setMarked(nrMarked);
	markerDetail.setStudentSubmit(nrStudentSubmitted);
	markerDetail.setMarksOutstanding(nrStudentSubmitted-nrMarked);
	if (nrMarked>0){
		markerDetail.setAvgMarkPerc((int)((((double)totalPercentage)/((double)nrMarked))+0.5));
	}else{
		markerDetail.setAvgMarkPerc(0);
	}
	return markerDetail;	
}

	public void updateAssMrk(short year, short period, int uniqueNr, int lecturerNr, short markPerc ) throws Exception {
	
	String sql = "select count(*)" +
		         " from assmrk " +
		         " where FK_UNQ_ASS_YEAR = " + year + 
		 		 " and FK_UNQ_ASS_PERIOD = " + period + 
		 		 " and FK_UNIQUE_NR = " + uniqueNr + 
		 		 " and MK_LECTURER_NR = " + lecturerNr; 	
		           
		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		int result = jdt.queryForInt(sql) ;
		if (result == 0) {
			//record does not exists, insert new record
			sql = "insert into assmrk (FK_UNQ_ASS_YEAR,FK_UNQ_ASS_PERIOD,FK_UNIQUE_NR," +
			"MK_LECTURER_NR,MARK_PERCENTAGE,NR_OUTSTANDING,NR_RETURNED,AVERAGE_MARK," +
			"STATUS_FLAG)" +
			" VALUES" +
			" (" + year + 
			"," + period + 
			"," + uniqueNr + 
			"," + lecturerNr +
			"," + markPerc +
			",0,0,0,'A')"; 	
			try{ 
				jdt = new JdbcTemplate(getDataSource());
				result = jdt.update(sql);	
			}
			catch (Exception ex) {
				throw new Exception("AssMarkerReallocationDao : Error inserting ASSMRK / " + ex,ex);
			}	
		} else {
			//record exists, update record
			sql = "update assmrk set" +
			" MARK_PERCENTAGE = " + markPerc + 
			" where FK_UNQ_ASS_YEAR = " + year + 
			" and FK_UNQ_ASS_PERIOD = " + period + 
			" and FK_UNIQUE_NR = " + uniqueNr + 
			" and MK_LECTURER_NR = " + lecturerNr; 	
			try{ 
				jdt = new JdbcTemplate(getDataSource());
				result = jdt.update(sql);	
			}
			catch (Exception ex) {
				throw new Exception("AssMarkerReallocationDao : Error updating ASSMRK / " + ex,ex);
			}	
		}
	}

	public boolean isAuthorised(String novellid,String studyUnit,Short academicYear,Short semester) {
		String sql = "select count(*) " +
		             "from usrsun " +
		             "where novell_user_id = '" + novellid.toUpperCase().trim() + "'" +
		             " and mk_study_unit_code = '" + studyUnit.toUpperCase().trim() + "'" +
		             " and mk_academic_year = " + academicYear +
		             " and mk_semester_period = " + semester +
		             " and system_type = 'L'" +
		             " and access_level in ('SECDL','CADMN','PRIML')";
		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		int result = jdt.queryForInt(sql) ;
		if (result == 0) {
			return false;
		} else {
			return true;
		}
	}

 private String replaceNull(Object object){
		String stringValue="";
		if (object==null){			
		}else{
			stringValue=object.toString();
		}			
		return stringValue;
	}
}
