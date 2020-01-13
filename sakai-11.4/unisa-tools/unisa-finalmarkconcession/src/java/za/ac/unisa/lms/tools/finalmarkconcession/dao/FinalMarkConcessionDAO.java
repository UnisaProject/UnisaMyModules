package za.ac.unisa.lms.tools.finalmarkconcession.dao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Connection;

import org.apache.commons.collections.map.ListOrderedMap;
import org.springframework.jdbc.core.JdbcTemplate;

import za.ac.unisa.lms.db.StudentSystemDAO;
import za.ac.unisa.lms.domain.general.Person;
import za.ac.unisa.lms.dao.general.UserDAO;
import za.ac.unisa.lms.dao.general.PersonnelDAO;
import za.ac.unisa.lms.domain.registration.*;
import za.ac.unisa.lms.dao.Gencod;
import za.ac.unisa.lms.dao.StudentSystemGeneralDAO;
import za.ac.unisa.lms.tools.finalmarkconcession.forms.*;
import za.ac.unisa.lms.tools.finalmarkconcession.Constants;
import za.ac.unisa.lms.tools.finalmarkconcession.actions.*;

public class FinalMarkConcessionDAO extends StudentSystemDAO {
	
	public DepartmentRecord getDepartment(Short code) throws Exception{
		
		DepartmentRecord dpt = new DepartmentRecord();
		String sql = "select code,eng_description,college_code" + 
        " from dpt" +
        " where code =" + code; 
						
		try{ 
		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		List queryList = jdt.queryForList(sql);
		
		Iterator i = queryList.iterator();
		while (i.hasNext()) {
			ListOrderedMap data = (ListOrderedMap) i.next();
			dpt.setCode(Short.parseShort((String)data.get("CODE").toString()));
			dpt.setDescription(data.get("ENG_DESCRIPTION").toString());
			dpt.setCollegeCode(Short.parseShort((String)data.get("COLLEGE_CODE").toString()));
			}
		}
		catch (Exception ex) {
		throw new Exception("FinalMarkConcessionDAO : Error reading DPT / " + ex,ex);
		}		
		return dpt;		
		
	}

	public String getStudyUnitDpt(String studyUnit) throws Exception{
	
		String dpt = "";
		String sql = "select mk_department_code from sun where code = '" + studyUnit + "'";
		
		try{ 
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(sql);
			
			Iterator i = queryList.iterator();
			while (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				dpt = data.get("MK_DEPARTMENT_CODE").toString();
				}
			}
			catch (Exception ex) {
			throw new Exception("FinalMarkConcession : Error reading DPT / " + ex,ex);
			}		
			return dpt;		
		
	}


	public String getResultDescription(Short code) throws Exception{
		
		String description="";
		
		String sql = "select eng_description" + 
	    " from sunres" +
	    " where code =" + code; 
						
		try{ 
		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		List queryList = jdt.queryForList(sql);
		
		Iterator i = queryList.iterator();
		while (i.hasNext()) {
			ListOrderedMap data = (ListOrderedMap) i.next();
			description = data.get("ENG_DESCRIPTION").toString();
			}
		}
		catch (Exception ex) {
		throw new Exception("FinalMarkConcessionDAO  : Error reading SUNRES / " + ex,ex);
		}		
		return description;		
		
	}

	public StudentContactRecord getStudentContactDetails(Integer studNr) throws Exception{
		StudentContactRecord contactRec = new StudentContactRecord();
		contactRec.setCellNumber("");
		contactRec.setHomeNumber("");
		contactRec.setWorkNumber("");
		contactRec.setFaxNumber("");
		contactRec.setEmailAddress("");
		contactRec.setAddressLine1("");
		contactRec.setAddressLine2("");
		contactRec.setAddressLine3("");
		contactRec.setAddressLine4("");
		contactRec.setAddressLine5("");
		contactRec.setAddressLine6("");
		contactRec.setPostalCode("");
		
		String sql = "select home_number,work_number,fax_number,cell_number,email_address" + 
	    " from adrph" +
	    " where fk_adrcatcode=1 and reference_no =" + studNr; 
						
		try{ 
		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		List queryList = jdt.queryForList(sql);
		
		Iterator i = queryList.iterator();
		while (i.hasNext()) {
			ListOrderedMap data = (ListOrderedMap) i.next();
				if (data.get("HOME_NUMBER")!=null){
					contactRec.setHomeNumber(data.get("HOME_NUMBER").toString().trim());
				}
				if (data.get("WORK_NUMBER")!=null){
					contactRec.setWorkNumber(data.get("WORK_NUMBER").toString().trim());
				}
				if (data.get("FAX_NUMBER")!=null){
					contactRec.setFaxNumber(data.get("FAX_NUMBER").toString().trim());
				}
				if (data.get("CELL_NUMBER")!=null){
					contactRec.setCellNumber(data.get("CELL_NUMBER").toString().trim());
				}
				if (data.get("EMAIL_ADDRESS")!=null){
					contactRec.setEmailAddress(data.get("EMAIL_ADDRESS").toString().trim());
				}
			}
		}
		catch (Exception ex) {
		throw new Exception("FinalMarkConcession : Error reading ADRPH / " + ex,ex);
		}	
		
		String adrSql = "select address_line_1,address_line_2,address_line_3," + 
		" address_line_4,address_line_5,address_line_6,postal_code" + 
	    " from adr" +
	    " where fk_adrcatypfk_adrc=1 and fk_adrcatypfk_adrt = 1" +
	    " and reference_no =" + studNr; 
						
		try{ 
		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		List queryList = jdt.queryForList(adrSql);
		
		Iterator i = queryList.iterator();
		while (i.hasNext()) {
			ListOrderedMap data = (ListOrderedMap) i.next();
				if (data.get("ADDRESS_LINE_1")!=null){
					contactRec.setAddressLine1(data.get("ADDRESS_LINE_1").toString().trim());
				}
				if (data.get("ADDRESS_LINE_2")!=null){
					contactRec.setAddressLine2(data.get("ADDRESS_LINE_2").toString().trim());
				}
				if (data.get("ADDRESS_LINE_3")!=null){
					contactRec.setAddressLine3(data.get("ADDRESS_LINE_3").toString().trim());
				}
				if (data.get("ADDRESS_LINE_4")!=null){
					contactRec.setAddressLine4(data.get("ADDRESS_LINE_4").toString().trim());
				}
				if (data.get("ADDRESS_LINE_5")!=null){
					contactRec.setAddressLine5(data.get("ADDRESS_LINE_5").toString().trim());
				}
				if (data.get("ADDRESS_LINE_6")!=null){
					contactRec.setAddressLine6(data.get("ADDRESS_LINE_6").toString().trim());
				}
				if (data.get("POSTAL_CODE")!=null){
					contactRec.setPostalCode(data.get("POSTAL_CODE").toString().trim());
				}
			}
		}
		catch (Exception ex) {
		throw new Exception("FinalMarkConcessionDAO  : Error reading ADR / " + ex,ex);
		}	
		
		return contactRec;
	}
	
	public CollegeRecord getCollege(Short code) throws Exception{
		
		CollegeRecord college = new CollegeRecord();
		
		String sql = "select code,eng_description,abbreviation" + 
        " from colleg" +
        " where code =" + code; 
		
		try{ 
		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		List queryList = jdt.queryForList(sql);
		
		Iterator i = queryList.iterator();
		while (i.hasNext()) {
			ListOrderedMap data = (ListOrderedMap) i.next();
			college.setCode(Short.parseShort((String)data.get("CODE").toString()));
			college.setDescription(data.get("ENG_DESCRIPTION").toString());
			college.setAbbreviation(data.get("ABBREVIATION").toString());
			}
		}
		catch (Exception ex) {
		throw new Exception("FinalMarkConcessionDAO : Error reading College / " + ex,ex);
		}		
		return college;		
		
	}
	
public Examination getNextExamination(Short examYear, Short examPeriod, String studyUnit) throws Exception{
		
	Examination exam = new Examination();
	
	String sql="Select mk_supp_exam_perio,mk_supp_exam_year" +
	" from sunpdt where" +
	" mk_exam_year = " + examYear +
	" and mk_exam_period = " + examPeriod +
	" and fk_suncode = '" + studyUnit + "'" +
	" and registration_allow ='Y' order by mk_academic_year desc, semester_period desc";
	
	try{ 
		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		List queryList = jdt.queryForList(sql);
		
		Iterator i = queryList.iterator();
		while (i.hasNext()) {
			ListOrderedMap data = (ListOrderedMap) i.next();
			exam.setExamYear(Short.parseShort((data.get("MK_SUPP_EXAM_YEAR").toString())));
			exam.setExamPeriod(Short.parseShort((data.get("MK_SUPP_EXAM_PERIO").toString())));
			break;
			}
		}
		catch (Exception ex) {
		throw new Exception("FinalMarkConcessionDAO  : Error reading SUNPDT - next (suppl) exam / " + ex,ex);
		}
		
		if (exam.getExamYear()==null){
			sql="Select mk_3rd_exam_period,mk_3rd_exam_year" +
			" from sunpdt where" +
			" mk_supp_exam_year = " + examYear +
			" and mk_supp_exam_perio = " + examPeriod +
			" and fk_suncode = '" + studyUnit + "'" +
			" and registration_allow ='Y' order by mk_academic_year desc, semester_period desc";
			
			try{ 
				JdbcTemplate jdt = new JdbcTemplate(getDataSource());
				List queryList = jdt.queryForList(sql);
				
				Iterator i = queryList.iterator();
				while (i.hasNext()) {
					ListOrderedMap data = (ListOrderedMap) i.next();
					if (data.get("mk_3rd_exam_year")!=null){
						exam.setExamYear(Short.parseShort((data.get("mk_3rd_exam_year").toString())));
					}
					if (data.get("mk_3rd_exam_period")!=null){
						exam.setExamPeriod(Short.parseShort((data.get("mk_3rd_exam_period").toString())));
					}					
					break;
					}
				}
				catch (Exception ex) {
				throw new Exception("FinalMarkConcessionDAO  : Error reading SUNPDT - next (3rd) exam / " + ex,ex);
				}
		}
		return exam;		
		
	}


	public List<DepartmentRecord> getDepartmentList(Short college) throws Exception {
		
		List<DepartmentRecord> dptList = new ArrayList<DepartmentRecord>();
		List queryList = new ArrayList();
		
		DepartmentRecord dpt = new DepartmentRecord();				
			
		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		queryList = jdt.queryForList("select code,eng_description,college_code from dpt where college_code =" + college + " and in_use_flag='Y' order by eng_description");
		for (int i=0; i<queryList.size();i++){
				dpt = new DepartmentRecord();				
				ListOrderedMap data = (ListOrderedMap) queryList.get(i);
				dpt.setCode(Short.parseShort((String)data.get("CODE").toString()));
				dpt.setDescription((String) data.get("ENG_DESCRIPTION").toString());
				dpt.setCollegeCode(Short.parseShort((String)data.get("COLLEGE_CODE").toString()));
				dptList.add(dpt);						
			}
		return dptList;
	}
	
	public Qualification getStudentQualification(Integer studentNumber, String studyUnit, Short acadYear, Short semester) throws Exception{
		
		Qualification qual = new Qualification();
		
		String sql = "select grd.code as code,grd.type as type,grd.eng_description,grd.long_eng_descripti as description" + 
        " from acasun,grd" +
        " where acasun.mk_academic_year = " + acadYear +
        " and acasun.mk_academic_period = " + semester +
        " and acasun.fk_student_nr = " + studentNumber +
        " and acasun.mk_study_unit_code = '" + studyUnit + "'" +
        " and acasun.fk_qual_code=grd.code" +
        " order by sequence_number desc";
		
		try{ 
		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		List queryList = jdt.queryForList(sql);
		
		Iterator i = queryList.iterator();
		while (i.hasNext()) {
			ListOrderedMap data = (ListOrderedMap) i.next();
			qual.setQualCode(data.get("code").toString());
			qual.setQualType(data.get("type").toString());
			qual.setQualDesc(data.get("description").toString());			
			break;
			}
		}
		catch (Exception ex) {
		throw new Exception("FinalMarkConcessionDAO  : Error reading Student Qualification / " + ex,ex);
		}		
		return qual;		
		
	}
	
	public List<Person> getResponsibleLecturerList(String studyUnit, Short examPeriod, Short examYear,String networkCode) throws Exception {
		
		Short academicYear=0;
		Short semester=0;
		
		String sql="Select mk_academic_year,semester_period" +
		" from sunpdt where" +
		" mk_exam_year = " + examYear +
		" and mk_exam_period = " + examPeriod +
		" and fk_suncode = '" + studyUnit + "'" +
		" and registration_allow ='Y' order by mk_academic_year desc, semester_period desc";
		
		try{ 
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(sql);
			
			Iterator i = queryList.iterator();
			while (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				academicYear = Short.parseShort((data.get("MK_ACADEMIC_YEAR").toString()));
				semester = Short.parseShort((data.get("SEMESTER_PERIOD").toString()));
				break;
				}
			}
			catch (Exception ex) {
			throw new Exception("FinalMarkConcessionDAO  : Error reading SUNPDT - first exam / " + ex,ex);
			}	
			
		if (academicYear==0){
			
			sql="Select mk_academic_year,semester_period" +
			" from sunpdt where" +
			" mk_supp_exam_year = " + examYear +
			" and mk_supp_exam_perio = " + examPeriod +
			" and fk_suncode = '" + studyUnit + "'" +
			" and registration_allow ='Y' order by mk_academic_year desc, semester_period desc";
			
			try{ 
				JdbcTemplate jdt = new JdbcTemplate(getDataSource());
				List queryList = jdt.queryForList(sql);
				
				Iterator i = queryList.iterator();
				while (i.hasNext()) {
					ListOrderedMap data = (ListOrderedMap) i.next();
					academicYear = Short.parseShort((data.get("MK_ACADEMIC_YEAR").toString()));
					semester = Short.parseShort((data.get("SEMESTER_PERIOD").toString()));
					break;
					}
				}
				catch (Exception ex) {
				throw new Exception("FinalMarkConcessionDAO  : Error reading SUNPDT - second exam/ " + ex,ex);
				}	
		}
		
		if (academicYear==0){
			
			sql="Select mk_academic_year,semester_period" +
			" from sunpdt where" +
			" mk_3rd_exam_year = " + examYear +
			" and mk_3rd_exam_period = " + examPeriod +
			" and fk_suncode = '" + studyUnit + "'" +
			" and registration_allow ='Y' order by mk_academic_year desc, semester_period desc";
			
			try{ 
				JdbcTemplate jdt = new JdbcTemplate(getDataSource());
				List queryList = jdt.queryForList(sql);
				
				Iterator i = queryList.iterator();
				while (i.hasNext()) {
					ListOrderedMap data = (ListOrderedMap) i.next();
					academicYear = Short.parseShort((data.get("MK_ACADEMIC_YEAR").toString()));
					semester = Short.parseShort((data.get("SEMESTER_PERIOD").toString()));
					break;
					}
				}
				catch (Exception ex) {
				throw new Exception("FinalMarkConcessionDAO  : Error reading SUNPDT - 3rd exam/ " + ex,ex);
				}	
		  }
		  Calendar cal = Calendar.getInstance();
          int currYear=cal.get(Calendar.YEAR);
		  List lecturerList=getResponsibleLecList(studyUnit,academicYear,semester);
		  if(!lecInList(lecturerList,networkCode)){
			   	if(currYear > academicYear) {
						lecturerList=getCurrResponsibleLecturerList(studyUnit,networkCode);
				} else {
					 	     if(semester==1){
							      lecturerList=getResponsibleLecList(studyUnit,(short)academicYear,(short)2);
				         	}
				}
		  }
				 return lecturerList;		
	}
	private boolean lecInList(List lecturerList,String networkCode){
		               boolean found=false;
		               if(lecturerList==null){
		            	   return false;
		               }
		               Iterator i=lecturerList.iterator();
		               while(i.hasNext()){
		            	     Person primLec=(Person)i.next();
		            	     if((primLec!=null)&&primLec.getNovellUserId().equals(networkCode)){
		            		        found=true;
		            	     }
		               }
		               return found;
	}
   public Person getPrimaryLecturer(String studyUnit, Short examPeriod, Short examYear,String networkCode) throws Exception {
		Short academicYear=0;
		Short semester=0;
		
		UserDAO dao = new UserDAO();
		
		String sql="Select mk_academic_year,semester_period" +
		" from sunpdt where" +
		" mk_exam_year = " + examYear +
		" and mk_exam_period = " + examPeriod +
		" and fk_suncode = '" + studyUnit + "'" +
		" and registration_allow ='Y' order by mk_academic_year desc, semester_period desc";
		
		try{ 
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(sql);
			
			Iterator i = queryList.iterator();
			while (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				academicYear = Short.parseShort((data.get("MK_ACADEMIC_YEAR").toString()));
				semester = Short.parseShort((data.get("SEMESTER_PERIOD").toString()));
				break;
				}
			}
			catch (Exception ex) {
			throw new Exception("FinalMarkConcessionDAO  : Error reading SUNPDT - first exam / " + ex,ex);
			}	
			
		if (academicYear==0){
			
			sql="Select mk_academic_year,semester_period" +
			" from sunpdt where" +
			" mk_supp_exam_year = " + examYear +
			" and mk_supp_exam_perio = " + examPeriod +
			" and fk_suncode = '" + studyUnit + "'" +
			" and registration_allow ='Y' order by mk_academic_year desc, semester_period desc";
			
			try{ 
				JdbcTemplate jdt = new JdbcTemplate(getDataSource());
				List queryList = jdt.queryForList(sql);
				
				Iterator i = queryList.iterator();
				while (i.hasNext()) {
					ListOrderedMap data = (ListOrderedMap) i.next();
					academicYear = Short.parseShort((data.get("MK_ACADEMIC_YEAR").toString()));
					semester = Short.parseShort((data.get("SEMESTER_PERIOD").toString()));
					break;
					}
				}
				catch (Exception ex) {
				throw new Exception("FinalMarkConcessionDAO  : Error reading SUNPDT - second exam/ " + ex,ex);
				}	
		}
		
		if (academicYear==0){
			
			sql="Select mk_academic_year,semester_period" +
			" from sunpdt where" +
			" mk_3rd_exam_year = " + examYear +
			" and mk_3rd_exam_period = " + examPeriod +
			" and fk_suncode = '" + studyUnit + "'" +
			" and registration_allow ='Y' order by mk_academic_year desc, semester_period desc";
			
			try{ 
				JdbcTemplate jdt = new JdbcTemplate(getDataSource());
				List queryList = jdt.queryForList(sql);
				
				Iterator i = queryList.iterator();
				while (i.hasNext()) {
					ListOrderedMap data = (ListOrderedMap) i.next();
					academicYear = Short.parseShort((data.get("MK_ACADEMIC_YEAR").toString()));
					semester = Short.parseShort((data.get("SEMESTER_PERIOD").toString()));
					break;
					}
				}
				catch (Exception ex) {
				throw new Exception("FinalMarkConcessionDAO  : Error reading SUNPDT - 3rd exam/ " + ex,ex);
				}	
		}
				
		  Person  primLec=getPrimLec(studyUnit,academicYear,semester);
		    if(semester==1){
		           if((primLec==null)||(!primLec.getNovellUserId().equalsIgnoreCase(networkCode))){
		        	    primLec=getPrimLec(studyUnit,(short)academicYear,(short)2);
		           }
		    }
			return primLec;		
	}
    private Person getPrimLec(String  studyUnit,short academicYear,short semester )throws Exception{
	             String sql="Select novell_user_id,persno from usrsun" +
			                " where system_type='L'" +
			                " and access_level in ('PRIML')" +
			                " and mk_study_unit_code = '" + studyUnit + "'" +
			                " and mk_academic_year = " + academicYear +
			                " and mk_semester_period = " + semester;
	                 UserDAO dao = new UserDAO();
	                 Person primLec=null;
			     try{ 
				         JdbcTemplate jdt = new JdbcTemplate(getDataSource());
				         List queryList = jdt.queryForList(sql);
						 Iterator i = queryList.iterator();
				         while (i.hasNext()) {
					            ListOrderedMap data = (ListOrderedMap) i.next();
					            try{
					                  primLec = dao.getPerson((data.get("NOVELL_USER_ID").toString()));
					                  if (primLec.getStudentSystemUser()!=null){
						                   primLec.setPersonnelNumber(primLec.getStudentSystemUser());
					                  }
					            }catch(Exception xc){
					            	primLec=new Person();
					            }
				        }
				}
				catch (Exception ex) {
				throw new Exception("FinalMarkConcessionDAO : Error reading USRSUN / " + ex,ex);
				}
			return primLec;
   }
    private List<Person> getResponsibleLecList(String  studyUnit,short academicYear,short semester )throws Exception{
    	             String sql= "Select a.novell_user_id networkCode,b.persno personnelNum "+
    			                 " from usrsun a, staff b  where system_type='L'  and access_level in ('PRIML','SECDL')  and "+
    	         		         " a.mk_study_unit_code = '"+studyUnit.trim()+"' and mk_academic_year ="+academicYear +
    	         		         " and mk_semester_period = " + semester+
    	         		         " and ( (b.RESIGN_DATE is null) or (b.resign_date >sysdate))  "+
    	         		         " and nvl(b.novell_user_id,' ')=a.novell_user_id ";
    	 		
    	              List<Person> lecturerList=new ArrayList<Person>();
    			try{ 
    				UserDAO dao = new UserDAO();
    				JdbcTemplate jdt = new JdbcTemplate(getDataSource());
    				List queryList = jdt.queryForList(sql);
    				Iterator i = queryList.iterator();
    				while (i.hasNext()) {
    					ListOrderedMap data = (ListOrderedMap) i.next();
    					Person person = new Person();
    					try{
    					        person = dao.getPerson((data.get("networkCode").toString()));
    					        if ((person!=null)&&person.getStudentSystemUser()!=null){
    						          person.setPersonnelNumber(person.getStudentSystemUser());
    					        }
    					        if (person!=null){
    					            lecturerList.add(person);
    					        }
    					}catch(Exception xc){}
    				}
    			}catch (Exception ex) {
    				throw new Exception("FinalMarkConcessionDAO : Error reading USRSUN / " + ex,ex);
    		    }		
    			return lecturerList;	
    }
	
	public String getExamDeadLine(Integer year,short period,String type,String fromOrTo) throws Exception{
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
	    	throw new Exception("FinalMarkConcessionDAO  : Error reading REGDAT / " + ex,ex);
	    }		
			return date;
		}


	public Person getStudentSystemUserfromUSR(String userNr) throws Exception{
		Person person = new Person();
		
		String sql = "select nr,personnel_no,name,e_mail,phone_number,novell_user_code,mk_department_code" + 
		" from usr" +
		" where nr='" + userNr +"'"; 
	
		try{ 
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(sql);

			Iterator i = queryList.iterator();
			while (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				person.setPersonnelNumber(replaceNull(data.get("PERSONNEL_NO")));
				person.setName(replaceNull(data.get("NAME")));
				person.setEmailAddress(replaceNull(data.get("E_MAIL")));
				person.setNovellUserId(replaceNull(data.get("NOVELL_USER_CODE")));
				person.setContactNumber(replaceNull(data.get("PHONE_NUMBER")));	
				person.setDepartmentCode(replaceNull(data.get("MK_DEPARTMENT_CODE")));
				person.setStudentSystemUser(replaceNull(data.get("NR")));
			}
	}
	catch (Exception ex) {
		throw new Exception("FinalMarkConcessionDAO : Error reading USR / " + ex,ex);
	}		
	return person;		
	}

	public boolean sendEmailToAuthoriser(Short examYear, Short examPeriod, String persno, String action) {
	String sql = "select count(*) " +
	             "from sxplog " +
	             "where year = " + examYear +
	             " and period = " + examPeriod +
	             " and type_gc78 = 'FYC'" +
	             " and action_gc79 = '" + action + "'" +
	             " and trim(request_action_from) = '" + persno.trim() + "'" +
	             " and to_char(updated_on,'YYYYMMDD') = to_char(sysdate,'YYYYMMDD')";	             
	             
	JdbcTemplate jdt = new JdbcTemplate(getDataSource());
	int result = jdt.queryForInt(sql) ;
	if (result >= 1) {
			return false;
		} else {
			return true;
		}
	}
	
	public boolean secondExamOpportunity(Short examYear, Short examPeriod, String studyUnit, Integer studentNr){
		String sql = "select count(*) from acasun" +
					 " where exam_year = " + examYear +
					 " and mk_exam_period_cod = " + examPeriod +
					 " and mk_study_unit_code = '" + studyUnit + "'" +
					 " and fk_student_nr = " + studentNr +
					 " and supplementary_exam > 1";
		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		int result = jdt.queryForInt(sql) ;
		if (result >= 1) {
				return true;
			} else {
				return false;
			}
		}	
	
public void updateAlternativeAssessmentRecord (Short examYear, Short examPeriod, String studyUnit, int studentNr, Short originalFinalMark, Short originalResultCode, AlternativeExamOpportunityRecord altExamOpt, String newStatus, String userId, String logComment,String logUserActioned) throws Exception {
	
	JdbcTemplate jdt = new JdbcTemplate(getDataSource());
	Connection connection = jdt.getDataSource().getConnection();
	connection.setAutoCommit(false);	
	PreparedStatement ps = null;	
	String sql = "";
	String updateFlag = "N";
	 
	try {	
		
		if (newStatus.equalsIgnoreCase("FORMSAVED")) {	
			
			
			sql = "update xamrmk"
					+ " set asses_opt_gc91 = ?,"
					+ " assess_opt_other = ?," 
					+ " support_prov_gc92 = ?,"
					+ " support_prod_other = ?,"			
					+ " fi_concession_mark = ?,"
					+ " revised_final_mark = ?,"
					+ " fi_zeromrk_gc239 = ?,"
					+ " track_status = ?,"
					+ " auth_respons_email = ?,"
					+ " original_final_mar = ?,"
					+ " original_result_ty = ?,"
					+ " remarker = ?"
					+ " where type = 'F'"
					+ " and exam_year = ?"
					+ " and mk_exam_period_cod = ?"
					+ " and mk_study_unit_code= ?"
					+ " and mk_student_nr= ? ";
					
				ps = connection.prepareStatement(sql);
						   
				ps.setString(1, altExamOpt.getAlternativeAssessOpt()); 
				ps.setString(2, altExamOpt.getAlternativeAssessOtherDesc()); 
				ps.setString(3, altExamOpt.getAcademicSupportOpt()); 
				ps.setString(4, altExamOpt.getAcademicSupportOtherDesc()); 
				ps.setInt(5, Integer.parseInt(altExamOpt.getConcessionMark()));
				ps.setInt(6, Integer.parseInt(altExamOpt.getFinalMark()));
				ps.setString(7, altExamOpt.getZeroMarkReason()); 
				ps.setString(8, newStatus);
				ps.setString(9, altExamOpt.getAuthResponseEmail());
				ps.setInt(10, originalFinalMark);
				ps.setShort(11,originalResultCode);
				ps.setInt(12, Integer.parseInt(altExamOpt.getResponsibleLecturer().getPersonnelNumber()));
				ps.setShort(13,examYear);
				ps.setShort(14,examPeriod);
				ps.setString(15,studyUnit);
				ps.setInt(16,studentNr);
				
				ps.executeUpdate(); 
				ps.close();
				updateFlag="Y";
				
		}
		
		if (newStatus.equalsIgnoreCase("AUTHREQCOD")) {	
			
			sql = "update xamrmk"				
					+ " set track_status = ?,"
					+ " auth_respons_email = ?"				
					+ " where type = 'F'"
					+ " and exam_year = ?"
					+ " and mk_exam_period_cod = ?"
					+ " and mk_study_unit_code= ?"
					+ " and mk_student_nr= ? ";
					
				ps = connection.prepareStatement(sql);
				
				ps.setString(1, newStatus);
				ps.setString(2, altExamOpt.getAuthResponseEmail());				
				ps.setShort(3,examYear);
				ps.setShort(4,examPeriod);
				ps.setString(5,studyUnit);
				ps.setInt(6,studentNr);
				
				ps.executeUpdate(); 
				ps.close();
				updateFlag="Y";
		}
		
		if (newStatus.equalsIgnoreCase("AUTHREQDN") || 
				newStatus.equalsIgnoreCase("AUTHORISED") || 
				newStatus.equalsIgnoreCase("AUTHCANREQ") || 
				newStatus.equalsIgnoreCase("AUTHREJCOD")  || 
				newStatus.equalsIgnoreCase("AUTHREJDN")) {	
			
			
			sql = "update xamrmk"				
					+ " set track_status = ?"			
					+ " where type = 'F'"
					+ " and exam_year = ?"
					+ " and mk_exam_period_cod = ?"
					+ " and mk_study_unit_code= ?"
					+ " and mk_student_nr= ? ";
					
				ps = connection.prepareStatement(sql);
				
				ps.setString(1, newStatus);					
				ps.setShort(2,examYear);
				ps.setShort(3,examPeriod);
				ps.setString(4,studyUnit);
				ps.setInt(5,studentNr);
				
				ps.executeUpdate();
				ps.close();
				updateFlag="Y";
		}
		
		
		if (updateFlag.equalsIgnoreCase("Y")) {
			//write log entry
			sql = "insert into sxplog (year,period,mk_study_unit_code,mk_student_nr,mk_paper_nr,action_gc79,comment0,request_action_from,type_gc78,updated_by,updated_on) " +
					"values (?,?,?,?,?,?,?,?,?,?,SYSTIMESTAMP)";
				   
			ps = connection.prepareStatement(sql);
				   		   
			ps.setShort(1,examYear);
			ps.setShort(2,examPeriod);
			ps.setString(3,studyUnit);
			ps.setInt(4,studentNr);
			ps.setInt(5,1); 					//mk_paper_nr
			ps.setString(6,newStatus); 			//action_gc79
			ps.setString(7,logComment);         //Comment0
			ps.setString(8,logUserActioned); 	//request_action_from
			ps.setString(9, "FYC"); 			//type_gc78
			ps.setString(10, userId); 			//updated_by
			ps.executeUpdate();
			ps.close();
		}
		
		connection.commit();
	}	
	catch (Exception e) {
		if (connection!=null){connection.rollback();}		
		throw new Exception("FinalMarkConcessionDAO  : Error updating concession record (XAMRMK) / " + e, e);
	} finally {		
		try { 
			if (connection!=null){
				connection.setAutoCommit(true);
				connection.close();					
			}
			if (ps!=null){ps.close();}
		} catch (Exception e) {	
				e.printStackTrace();
			}
		} 
}

public void updateAlternativeAssessmentRecordFromGradeBook (Short examYear, Short examPeriod, String studyUnit, int studentNr, Short originalFinalMark, Short originalResultCode, AlternativeExamOpportunityRecord altExamOpt, String newStatus, String userId, String logComment,String logUserActioned) throws Exception {
	
	JdbcTemplate jdt = new JdbcTemplate(getDataSource());
	Connection connection = jdt.getDataSource().getConnection();
	connection.setAutoCommit(false);	
	PreparedStatement ps = null;	
	String sql = "";
		 
	try {	
			sql = "update xamrmk"							
					+ " set fi_concession_mark = ?,"
					+ " revised_final_mark = ?,"
					+ " fi_zeromrk_gc239 = ?,"
					+ " track_status = ?,"		
					+ " original_final_mar = ?,"
					+ " original_result_ty = ?"				
					+ " where type = 'F'"
					+ " and exam_year = ?"
					+ " and mk_exam_period_cod = ?"
					+ " and mk_study_unit_code= ?"
					+ " and mk_student_nr= ? ";
					
				ps = connection.prepareStatement(sql);
			
				ps.setInt(1, Integer.parseInt(altExamOpt.getConcessionMark()));
				ps.setInt(2, Integer.parseInt(altExamOpt.getFinalMark()));
				ps.setString(3, altExamOpt.getZeroMarkReason()); 
				ps.setString(4, newStatus);				
				ps.setInt(5, originalFinalMark);
				ps.setShort(6,originalResultCode);				
				ps.setShort(7,examYear);
				ps.setShort(8,examPeriod);
				ps.setString(9,studyUnit);
				ps.setInt(10,studentNr);
				
				ps.executeUpdate(); 
				ps.close();				
		
			//write log entry
			sql = "insert into sxplog (year,period,mk_study_unit_code,mk_student_nr,mk_paper_nr,action_gc79,comment0,request_action_from,type_gc78,updated_by,updated_on) " +
					"values (?,?,?,?,?,?,?,?,?,?,SYSTIMESTAMP)";
				   
			ps = connection.prepareStatement(sql);
				   		   
			ps.setShort(1,examYear);
			ps.setShort(2,examPeriod);
			ps.setString(3,studyUnit);
			ps.setInt(4,studentNr);
			ps.setInt(5,1); 					//mk_paper_nr
			ps.setString(6,newStatus); 			//action_gc79
			ps.setString(7,logComment);         //Comment0
			ps.setString(8,logUserActioned); 	//request_action_from
			ps.setString(9, "FYC"); 			//type_gc78
			ps.setString(10, userId); 			//updated_by
			ps.executeUpdate();
			ps.close();
		
			connection.commit();
	}	
	catch (Exception e) {
		if (connection!=null){connection.rollback();}		
		throw new Exception("FinalMarkConcessionDAO  : Error updating concession record (XAMRMK) from gradebook / " + e, e);
	} finally {		
		try { 
			if (connection!=null){
				connection.setAutoCommit(true);
				connection.close();					
			}
			if (ps!=null){ps.close();}
		} catch (Exception e) {	
				e.printStackTrace();
			}
		} 
}

public void groupAlternativeAssessmentAssignment (Short examYear, Short examPeriod, String studyUnit, AlternativeExamOpportunityRecord altExamOpt, String newStatus, String userId, String logComment,String logUserActioned) throws Exception {
	
	int updateCount = 0;
			
	List<StudentResultRecord> studentList = new ArrayList<StudentResultRecord>();
	JdbcTemplate jdt = new JdbcTemplate(getDataSource());
	Connection connection = jdt.getDataSource().getConnection();
	PreparedStatement ps = null;
	ResultSet rs = null;
		 try {	
			 String sql = "select xamrmk.mk_student_nr, stxres.final_mark, stxres.mk_result_type_cod from xamrmk, stxres "	
					 		+ " where xamrmk.exam_year = ?"					 		
					 		+ " and xamrmk.mk_exam_period_cod = ?"					 		
					 		+ " and xamrmk.status_code='R'"
					 		+ " and xamrmk.type='F'"
					 		+ " and xamrmk.track_status='IDENTIFIED'"
					 		+ " and xamrmk.mk_study_unit_code=?"
					 		+ " and stxres.exam_year = xamrmk.exam_year"
					 		+ " and stxres.mk_exam_period_cod = xamrmk.mk_exam_period_cod "
					 		+ " and stxres.mk_study_unit_code = xamrmk.mk_study_unit_code"
					 		+ " and stxres.mk_student_nr = xamrmk.mk_student_nr";	
			 
				ps = connection.prepareStatement(sql);
				ps.setShort(1, examYear);
				ps.setShort(2, examPeriod);
				ps.setString(3, studyUnit);
				
				rs = ps.executeQuery();		
				
				while (rs.next()) {		
					StudentResultRecord studentResult = new StudentResultRecord();
					ResultRecord result = new ResultRecord();
					studentResult.setStudentNumber(rs.getInt("mk_student_nr"));
					result.setFinalMark(rs.getShort("final_mark"));                     //should have a final_mark and result_type as has been identified for FI concession
					result.setResultCode(rs.getShort("mk_result_type_cod"));
					studentResult.setResult(result);
					studentList.add(studentResult);
					
				}	
				ps.close();
				rs.close();
			}
	catch (Exception ex) {
		if (connection!=null){connection.close();}
		if (rs!=null){rs.close();}
		if (ps!=null){ps.close();}
		throw new Exception("FinalMarkConcessionDao : Error reading xamrmk to get studentList/ " + ex);
	}
		
	if (studentList.size()>0){	
		connection.setAutoCommit(false);
		try{ 
			//updating xamrmk with alternative assessment and status
			String sql = "update xamrmk"
							+ " set asses_opt_gc91 = ?,"
							+ " assess_opt_other = ?," 
							+ " support_prov_gc92 = ?,"
							+ " support_prod_other = ?,"	
							+ " track_status = ?,"
							+ " auth_respons_email = ?,"
							+ " original_final_mar = ?,"
							+ " original_result_ty = ?,"
							+ " fi_zeromrk_gc239 = 'N/A',"
							+ " remarker = ?"
							+ " where type = 'F'"
							+ " and exam_year = ?"
							+ " and mk_exam_period_cod = ?"
							+ " and mk_study_unit_code= ?"
							+ " and mk_student_nr= ? ";
		   
			ps = connection.prepareStatement(sql);
		   		   
			for (int i=0; i < studentList.size(); i++ ){
					
					 updateCount = updateCount + 1;
					 ps.setString(1, altExamOpt.getAlternativeAssessOpt()); 
						ps.setString(2, altExamOpt.getAlternativeAssessOtherDesc()); 
						ps.setString(3, altExamOpt.getAcademicSupportOpt()); 
						ps.setString(4, altExamOpt.getAcademicSupportOtherDesc()); 					
						ps.setString(5, newStatus);
						ps.setString(6, altExamOpt.getAuthResponseEmail());
						ps.setShort(7, ((StudentResultRecord)studentList.get(i)).getResult().getFinalMark());						
						ps.setShort(8, ((StudentResultRecord)studentList.get(i)).getResult().getResultCode());	
						ps.setInt(9, Integer.parseInt(altExamOpt.getResponsibleLecturer().getPersonnelNumber()));						
						ps.setShort(10,examYear);
						ps.setShort(11,examPeriod);
						ps.setString(12,studyUnit);
						ps.setInt(13,((StudentResultRecord)studentList.get(i)).getStudentNumber());
						ps.addBatch();
			}
		   
			ps.executeBatch();
			ps.close();
			
			
			//write log entry
			sql = "insert into sxplog (year,period,mk_study_unit_code,mk_student_nr,mk_paper_nr,action_gc79,comment0,request_action_from,type_gc78,updated_by,updated_on) " +
					"values (?,?,?,?,?,?,?,?,?,?,SYSTIMESTAMP)";
					   
			ps = connection.prepareStatement(sql);
			
			for (int i=0; i < studentList.size(); i++ ){
				ps.setShort(1,examYear);
				ps.setShort(2,examPeriod);
				ps.setString(3,studyUnit);
				ps.setInt(4, ((StudentResultRecord)studentList.get(i)).getStudentNumber());
				ps.setInt(5,1); 					//mk_paper_nr
				ps.setString(6,newStatus); 			//action_gc79
				ps.setString(7,logComment);         //Comment0
				ps.setString(8,logUserActioned); 	    //request_action_from
				ps.setString(9, "FYC"); 			//type_gc78
				ps.setString(10, userId); 			//updated_by
				ps.addBatch();
			}
			ps.executeBatch();	
			ps.close();
					
			connection.commit();
		}
		catch (Exception e) {
			if (connection!=null){connection.rollback();}		
			throw new Exception("ProcessDrivenSMSDao : Data has been rollback, error assigning group alternative assessment / " + e, e);
		} finally {		
			try { 
				if (connection!=null){
					connection.setAutoCommit(true);
					connection.close();					
				}
				if (ps!=null){ps.close();}
				if (rs!=null){rs.close();}
			} catch (Exception e) {	
					e.printStackTrace();
				}
			} 	
	}
}





public Integer getNumberOfFiConcessionRecords(Short examYear, Short examPeriod, String studyUnit, String status) throws Exception {	
	
	JdbcTemplate jdt = new JdbcTemplate(getDataSource());
	Connection con = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	
	int numberOfRows = 0;
	String sql = "select count(*) as recordCount"
			+ " from xamrmk "
			+ " where type = 'F' "
			+ " and status_code= 'R'"
			+ " and exam_year = ? "
			+ " and mk_exam_period_cod = ?"
			+ " and mk_study_unit_code= ?";
	
	if (!status.equalsIgnoreCase("All")){
		sql = sql + " and track_status=?";
	}
	
	try{ 
		con = jdt.getDataSource().getConnection();
		ps = con.prepareStatement(sql);
		ps.setShort(1, examYear);
		ps.setShort(2, examPeriod);
		ps.setString(3, studyUnit);	
		if (!status.equalsIgnoreCase("All")){
			ps.setString(4, status);
		}
		rs = ps.executeQuery();
		if (rs.next()) {
		        numberOfRows = rs.getInt(1);		        
		      } else {
		        System.out.println("error: could not get the record count");
		      }
	} catch (Exception e) {		
        throw new Exception("FinalMarkConcessionDao : Error reading number of concession records/ " + e); 
	   } finally {
	      try {
	    	if (rs!=null){rs.close();}
	        if (ps!=null){ps.close();}  	        
	        if (con!=null){con.close();}
	      } catch (Exception e) {
	        e.printStackTrace();
	      }
	    }
		return numberOfRows;
	}

public AlternativeExamOpportunityRecord getAlternativeAssessmentRecord(Short examYear, Short examPeriod, String studyUnit, Integer studentNr) throws Exception {	
	
	AlternativeExamOpportunityRecord concessionRecord = new AlternativeExamOpportunityRecord();
	JdbcTemplate jdt = new JdbcTemplate(getDataSource());
	Connection con = jdt.getDataSource().getConnection();
	PreparedStatement ps = null;
	ResultSet rs = null;
		 try {	
			 String sql = "select exam_year, mk_exam_period_cod,assess_opt_other,asses_opt_gc91,"
					        + " decline_opt_gc98,decline_opt_other,nvl(fi_concession_mark,0) as concessionMark,decode(fi_zeromrk_gc239,null,'N/A','','N/A',' ','N/A',fi_zeromrk_gc239) as code239,"
					        + " identified_date,original_final_mar,original_result_ty,nvl(revised_final_mark,0) as revisedFinalMark,"
					        + " revised_result_typ,support_prod_other,support_prov_gc92,track_status," 
					        + " remarker,auth_respons_email,"
					        + " decode(C239.ENG_DESCRIPTION,null,(select eng_description from gencod where fk_gencatcode=239 and code='N/A'),"
					        + "	'',(select eng_description from gencod where fk_gencatcode=239 and code='N/A'),"
					        + " ' ',(select eng_description from gencod where fk_gencatcode=239 and code='N/A'),C239.ENG_DESCRIPTION) as desc239, "
					        + " C92.ENG_DESCRIPTION as desc92, C91.ENG_DESCRIPTION as desc91,"
					        + " C98.ENG_DESCRIPTION as desc98,C90.ENG_DESCRIPTION as desc90"
					        + " from xamrmk, gencod c239, gencod c92, gencod c91, gencod c98, gencod c90 "
							+ " where type = 'F' "
							+ " and exam_year = ? "
							+ " and mk_exam_period_cod = ? "
							+ " and mk_study_unit_code= ?"
							+ " and mk_student_nr= ?"
							+ " and c239.code(+) = XAMRMK.FI_ZEROMRK_GC239"
							+ " and C239.FK_GENCATCODE(+)=239"
							+ " and c91.code(+) = XAMRMK.ASSES_OPT_GC91"
							+ " and C91.FK_GENCATCODE(+)=91"
							+ " and c92.code(+) = XAMRMK.SUPPORT_PROV_GC92"
							+ " and C92.FK_GENCATCODE(+)=92"
							+ " and c98.code(+) = XAMRMK.DECLINE_OPT_GC98"
							+ " and C98.FK_GENCATCODE(+)=98"
							+ " and c90.code(+) = XAMRMK.track_status" 
							+ " and C90.FK_GENCATCODE(+)=90";

			ps = con.prepareStatement(sql);
			ps.setShort(1, examYear);
			ps.setShort(2, examPeriod);
			ps.setString(3, studyUnit);
			ps.setInt(4, studentNr);
			
			rs = ps.executeQuery();
			PersonnelDAO persDao = new PersonnelDAO();
			
			while (rs.next()) {		
				
				concessionRecord.setAcademicSupportOpt(replaceNull(rs.getString("support_prov_gc92")));
				concessionRecord.setAcademicSupportDesc(replaceNull(rs.getString("desc92")));

				concessionRecord.setAcademicSupportOtherDesc(replaceNull(rs.getString("support_prod_other")));
				
				concessionRecord.setAlternativeAssessOpt(replaceNull(rs.getString("asses_opt_gc91")));	
				concessionRecord.setAlternativeAssessOptDesc(replaceNull(rs.getString("desc91")));

				concessionRecord.setAlternativeAssessOtherDesc(replaceNull(rs.getString("assess_opt_other")));
				
				concessionRecord.setAdminDeclineOpt(replaceNull(rs.getString("decline_opt_gc98")));
				concessionRecord.setAdminDeclineOptDesc(replaceNull(rs.getString("desc98")));

				concessionRecord.setAdminDeclineOther(replaceNull(rs.getString("decline_opt_other")));
				
				concessionRecord.setZeroMarkReason(replaceNull(rs.getString("code239")));
				concessionRecord.setZeroMarkReasonDesc(replaceNull(rs.getString("desc239")));
				
				concessionRecord.setStatus(replaceNull(rs.getString("track_status")));
				concessionRecord.setStatusDesc(replaceNull(rs.getString("desc90")));
				
				concessionRecord.setConcessionMark(rs.getString("concessionMark"));
				
				concessionRecord.setFinalMark(rs.getString("revisedFinalMark"));
				
				String responsibleLecturer = replaceNull(rs.getString(("remarker")));
				if (responsibleLecturer.equalsIgnoreCase("")){
					responsibleLecturer="0";
				}			
				Person person = new Person();			
				person = persDao.getPersonnelFromSTAFF(Integer.parseInt(responsibleLecturer));
				if (person.getPersonnelNumber()==null){
					person = getStudentSystemUserfromUSR(responsibleLecturer);
					if (person.getStudentSystemUser()==null){
						person.setContactNumber("");
						person.setDepartmentCode("");
						person.setEmailAddress("");
						person.setName("");
						person.setNovellUserId("");
						person.setPersonnelNumber(String.valueOf(responsibleLecturer));
						person.setStudentSystemUser("");
					}else{
						person.setPersonnelNumber(person.getStudentSystemUser());
					}
				}
				concessionRecord.setResponsibleLecturer(person);	
				concessionRecord.setAuthResponseEmail(replaceNull(rs.getString("auth_respons_email")));	
			}

		} catch (Exception ex) {
			throw new Exception(
					"FinalMarkConcessionDAO  : Error reading concession record (XAMRMK) / " + ex, ex);
		} finally {
			try {
				if (con!=null){con.close();}
				if (ps!=null){ps.close();}
				if (rs!=null){rs.close();}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		 return concessionRecord;
 }

public String getAlternativeAssessmentRecordStatus(Short examYear, Short examPeriod, String studyUnit, Integer studentNr) throws Exception {	
		
	JdbcTemplate jdt = new JdbcTemplate(getDataSource());
	Connection con = jdt.getDataSource().getConnection();
	PreparedStatement ps = null;
	ResultSet rs = null;
	String currentStatus="";
		 try {	
			 String sql = "select track_status"
					        + " from xamrmk "
							+ " where type = 'F' "
							+ " and exam_year = ? "
							+ " and mk_exam_period_cod = ? "
							+ " and mk_study_unit_code= ?"
							+ " and mk_student_nr= ? ";

			ps = con.prepareStatement(sql);
			ps.setShort(1, examYear);
			ps.setShort(2, examPeriod);
			ps.setString(3, studyUnit);
			ps.setInt(4, studentNr);			
			rs = ps.executeQuery();
			
			while (rs.next()) {	
				currentStatus=(rs.getString("track_status"));	
			}

		} catch (Exception ex) {
			throw new Exception(
					"FinalMarkConcessionDAO  : Error reading concession record current status (XAMRMK) / " + ex, ex);
		} finally {
			try {
				if (con!=null){con.close();}
				if (ps!=null){ps.close();}
				if (rs!=null){rs.close();}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		 return currentStatus;
 }

public BatchUploadRecord getStudentFiDetails(Short examYear, Short examPeriod, String studyUnit,BatchUploadRecord record) throws Exception {
	
	Connection con = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	
	try{
		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		con = jdt.getDataSource().getConnection();	
	
			//Get XAMRMK record;
			//Get result record;
			//Get YearMark
			String sql = "select xamrmk.MK_STUDY_UNIT_CODE as studyUnit,STXRES.MK_ACADEMIC_YEAR as acadYear,STXRES.SEMESTER_PERIOD as semester,nvl(STXRES.FINAL_MARK,0) as finalMark," +
			"STXRES.MK_RESULT_TYPE_COD as resultCode,nvl(STXRES.YEAR_MARK,0) as yearMark, SUNRES.ENG_DESCRIPTION as resultDesc,SUNRES.ENG_ABBREVIATION resultAbbr, XAMRMK.TRACK_STATUS as status," +	
			"nvl(XAMRMK.ASSES_OPT_GC91,' ') as assessmentAbbr,xamrmk.assess_opt_other as assessmentOther," +
			"nvl(XAMRMK.SUPPORT_PROV_GC92,' ') as supportAbbr,xamrmk.support_prod_other as supportOther," +
			"nvl(xamrmk.revised_final_mark,0) as revisedMark,nvl(xamrmk.FI_ZEROMRK_GC239,'N/A') as zeroMarkReason, nvl(xamrmk.FI_CONCESSION_MARK,0) as concessionMark, xamrmk.remarker as lecturer" +
			" from xamrmk, stxres, sunres" + 	
			" where XAMRMK.EXAM_YEAR= ?" +
			" and XAMRMK.MK_EXAM_PERIOD_COD= ?" +
			" and XAMRMK.MK_STUDY_UNIT_CODE=?" +
			" and XAMRMK.MK_STUDENT_NR=?" +
			" and XAMRMK.TYPE='F'" +
			" and XAMRMK.STATUS_CODE='R'" +		
			" and XAMRMK.EXAM_YEAR=STXRES.EXAM_YEAR" +
			" and XAMRMK.MK_EXAM_PERIOD_COD=STXRES.MK_EXAM_PERIOD_COD" +
			" and XAMRMK.MK_STUDENT_NR=STXRES.MK_STUDENT_NR" +
			" and XAMRMK.MK_STUDY_UNIT_CODE=STXRES.MK_STUDY_UNIT_CODE" +
			" and STXRES.MK_RESULT_TYPE_COD=SUNRES.CODE";
				
			ps = con.prepareStatement(sql);
			ps.setShort(1, examYear);
			ps.setShort(2, examPeriod);
			ps.setString(3, studyUnit);
			ps.setInt(4, record.getStudentNumber());
				
				rs = ps.executeQuery();
				while (rs.next()) {
					ResultRecord result = new ResultRecord();	
					AlternativeExamOpportunityRecord altExamOpt = new AlternativeExamOpportunityRecord();
					record.setResult(result);
					record.setAltExamOpt(altExamOpt);
					record.getResult().setAcademicYear(rs.getShort("acadYear"));
					record.getResult().setSemester(rs.getShort("semester"));
					record.getResult().setFinalMark(rs.getShort("finalMark"));
					record.getResult().setYearMark(rs.getDouble("yearMark"));
					record.getResult().setResultCode(rs.getShort("resultCode"));
					record.getAltExamOpt().setStatus(rs.getString("status").trim());						
					record.getAltExamOpt().setConcessionMark(rs.getString(("concessionMark").trim()));
					record.getAltExamOpt().setFinalMark(rs.getString("revisedMark").trim());
					record.getAltExamOpt().setZeroMarkReason(rs.getString("zeroMarkReason").trim());					
					record.getAltExamOpt().setFinalMark(rs.getString("revisedMark").trim());
					record.getAltExamOpt().setAlternativeAssessOpt(rs.getString("assessmentAbbr").trim());
					
					String responsibleLecturer = replaceNull(rs.getString(("lecturer")));
					if (responsibleLecturer.equalsIgnoreCase("")){
						responsibleLecturer="0";
					}			
					Person person = new Person();	
					PersonnelDAO persDao = new PersonnelDAO();
					person = persDao.getPersonnelFromSTAFF(Integer.parseInt(responsibleLecturer));
					if (person.getPersonnelNumber()==null){
						person = getStudentSystemUserfromUSR(responsibleLecturer);
						if (person.getStudentSystemUser()==null){
							person.setContactNumber("");
							person.setDepartmentCode("");
							person.setEmailAddress("");
							person.setName("");
							person.setNovellUserId("");
							person.setPersonnelNumber(String.valueOf(responsibleLecturer));
							person.setStudentSystemUser("");
						}else{
							person.setPersonnelNumber(person.getStudentSystemUser());
						}
					}
					record.getAltExamOpt().setResponsibleLecturer(person);	
					record.getAltExamOpt().setAuthResponseEmail(person.getEmailAddress());	
				}	
				
			}
	catch (Exception ex) {
		throw new Exception("FinalMarkConcessionDAO  : : Error reading table xamrmk, stxres, sunres/ " + ex, ex);				
	   } finally {
		try {
			if (con!=null){con.close();}
			if (ps!=null){ps.close();}
			if (rs!=null){rs.close();}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	
	return record;
}

public List<BatchCodAuthRecord> getFiRecordsValidForAuthReq(Short examYear, Short examPeriod, String studyUnit) throws Exception {
	
	List<BatchCodAuthRecord> fiRecordList = new ArrayList<BatchCodAuthRecord>();
	Connection con = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	
	try{
		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		con = jdt.getDataSource().getConnection();	
		
		String sql = "select xamrmk.MK_STUDENT_NR as studNr,STU.SURNAME || ' ' || STU.INITIALS || ' ' || stu.MK_TITLE as studName," +
			"xamrmk.MK_STUDY_UNIT_CODE as studyUnit,STXRES.MK_ACADEMIC_YEAR as acadYear,STXRES.SEMESTER_PERIOD as semester,STXRES.FINAL_MARK as finalMark," +
			"STXRES.MK_ACADEMIC_YEAR as acadYear,STXRES.SEMESTER_PERIOD as semester,STXRES.MK_RESULT_TYPE_COD as resultCode,STXRES.YEAR_MARK as yearMark," +
			"SUNRES.ENG_DESCRIPTION as resultDesc,SUNRES.ENG_ABBREVIATION resultAbbr, XAMRMK.TRACK_STATUS as status," +	
			"XAMRMK.ASSES_OPT_GC91 as assessmentAbbr,xamrmk.assess_opt_other as assessmentOther," +
			"XAMRMK.SUPPORT_PROV_GC92 as supportAbbr,xamrmk.support_prod_other as supportOther," +
			"xamrmk.revised_final_mark as revisedMark,xamrmk.FI_ZEROMRK_GC239 as zeroMarkReason," +
			"xamrmk.FI_CONCESSION_MARK as concessionMark, xamrmk.remarker as lecturer,xamrmk.auth_respons_email," +
			"C92.ENG_DESCRIPTION as supportDesc, C91.ENG_DESCRIPTION as assessmentDesc" +
			" from xamrmk, stxres, sunres, stu, gencod c92, gencod c91" + 	
			" where XAMRMK.EXAM_YEAR= ?" +
			" and XAMRMK.MK_EXAM_PERIOD_COD= ?" +
			" and XAMRMK.MK_STUDY_UNIT_CODE=?" +
			" and XAMRMK.TYPE='F'" +
			" and XAMRMK.STATUS_CODE='R'" +		
			" and XAMRMK.TRACK_STATUS='FORMSAVED'" +
			" and ((XAMRMK.REVISED_FINAL_MARK > 0) " +
			" or (XAMRMK.REVISED_FINAL_MARK=0 and nvl(trim(xamrmk.FI_ZEROMRK_GC239),'N/A')<>'N/A'))" +
			" and XAMRMK.EXAM_YEAR=STXRES.EXAM_YEAR" +
			" and XAMRMK.MK_EXAM_PERIOD_COD=STXRES.MK_EXAM_PERIOD_COD" +
			" and XAMRMK.MK_STUDENT_NR=STXRES.MK_STUDENT_NR" +
			" and XAMRMK.MK_STUDY_UNIT_CODE=STXRES.MK_STUDY_UNIT_CODE" +
			" and STXRES.MK_RESULT_TYPE_COD=SUNRES.CODE" +
			" and XAMRMK.MK_STUDENT_NR=STU.NR" +
			" and c91.code = XAMRMK.ASSES_OPT_GC91" +
			" and C91.FK_GENCATCODE=91" +
			" and c92.code = XAMRMK.SUPPORT_PROV_GC92" +
			" and C92.FK_GENCATCODE=92";	
				
			ps = con.prepareStatement(sql);
			ps.setShort(1, examYear);
			ps.setShort(2, examPeriod);
			ps.setString(3, studyUnit);			
				
				rs = ps.executeQuery();				
				while (rs.next()) {
					BatchCodAuthRecord record = new BatchCodAuthRecord();
					ResultRecord result = new ResultRecord();	
					AlternativeExamOpportunityRecord altExamOpt = new AlternativeExamOpportunityRecord();
					record.setResult(result);
					record.setAltExamOpt(altExamOpt);
					record.setStudentNumber(rs.getInt("studNr"));
					record.setStudentName(rs.getString("studName"));
					record.getResult().setAcademicYear(rs.getShort("acadYear"));
					record.getResult().setSemester(rs.getShort("semester"));
					record.getResult().setFinalMark(rs.getShort("finalMark"));
					record.getResult().setYearMark(rs.getDouble("yearMark"));
					record.getResult().setResultCode(rs.getShort("resultCode"));
					record.getAltExamOpt().setStatus(replaceNull(rs.getString("status")).trim());						
					record.getAltExamOpt().setConcessionMark(replaceNull(rs.getString(("concessionMark")).trim()));
					record.getAltExamOpt().setFinalMark(replaceNull(rs.getString("revisedMark")).trim());
					record.getAltExamOpt().setZeroMarkReason(replaceNull(rs.getString("zeroMarkReason")).trim());
					record.getAltExamOpt().setFinalMark(replaceNull(rs.getString("revisedMark")).trim());
					record.getAltExamOpt().setAlternativeAssessOpt(replaceNull(rs.getString("assessmentAbbr")).trim());
					record.getAltExamOpt().setAlternativeAssessOptDesc(replaceNull(rs.getString("assessmentDesc")).trim());
					record.getAltExamOpt().setAcademicSupportOpt(replaceNull(rs.getString("supportAbbr")).trim());
					record.getAltExamOpt().setAcademicSupportDesc(replaceNull(rs.getString("supportDesc")).trim());
					record.getAltExamOpt().setAuthResponseEmail(replaceNull(rs.getString("auth_respons_email")).trim());					
					
					String responsibleLecturer = replaceNull(rs.getString(("lecturer")));
					if (responsibleLecturer.equalsIgnoreCase("")){
						responsibleLecturer="0";
					}			
					Person person = new Person();	
					PersonnelDAO persDao = new PersonnelDAO();
					person = persDao.getPersonnelFromSTAFF(Integer.parseInt(responsibleLecturer));
					if (person.getPersonnelNumber()==null){
						person = getStudentSystemUserfromUSR(responsibleLecturer);
						if (person.getStudentSystemUser()==null){
							person.setContactNumber("");
							person.setDepartmentCode("");
							person.setEmailAddress("");
							person.setName("");
							person.setNovellUserId("");
							person.setPersonnelNumber(String.valueOf(responsibleLecturer));
							person.setStudentSystemUser("");
						}else{
							person.setPersonnelNumber(person.getStudentSystemUser());
						}
					}
					record.getAltExamOpt().setResponsibleLecturer(person);
					fiRecordList.add(record);
				}	
				
			}
	catch (Exception ex) {
		throw new Exception("FinalMarkConcessionDAO  : : Error reading table xamrmk, stxres, sunres/ " + ex, ex);				
	   } finally {
		try {
			if (con!=null){con.close();}
			if (ps!=null){ps.close();}
			if (rs!=null){rs.close();}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	   
	return fiRecordList;
}


public List<FinalMarkStudentRecord> getStudentFiConcessionList(Short college, Short dpt, String studyUnit, Short examYear, Short examPeriod, String status,String action, 
		FinalMarkStudentRecord firstRecord, FinalMarkStudentRecord lastRecord,String networkCode) throws Exception {
	
	List<FinalMarkStudentRecord> studentList = new ArrayList<FinalMarkStudentRecord>();
	List queryList = new ArrayList();
	
	FinalMarkStudentRecord record = new FinalMarkStudentRecord();	
	//,xamrmk.FI_ZEROMRK_GC239 as zeroMarkReason
	String sql = "select sun.academic_level as academic_level,school.ABBREVIATION as schoolAbbrev, "+
	"DPT.ENG_DESCRIPTION as dptDesc, xamrmk.MK_STUDY_UNIT_CODE as studyUnit," +
	"xamrmk.MK_STUDENT_NR as studNr,STU.SURNAME || ' ' || STU.INITIALS || ' ' || stu.MK_TITLE as studName," +
	"STXRES.MK_ACADEMIC_YEAR as acadYear,STXRES.SEMESTER_PERIOD as semester,STXRES.FINAL_MARK as finalMark," +
	"STXRES.MK_RESULT_TYPE_COD as resultCode,SUNRES.ENG_DESCRIPTION as resultDesc,SUNRES.ENG_ABBREVIATION resultAbbr, XAMRMK.TRACK_STATUS as status," +	
	"XAMRMK.ASSES_OPT_GC91 as assessmentAbbr,xamrmk.assess_opt_other as assessmentOther," +
	"XAMRMK.SUPPORT_PROV_GC92 as supportAbbr,xamrmk.support_prod_other as supportOther,xamrmk.remarker as lecturer," +
	"xamrmk.revised_final_mark as revisedMark,xamrmk.FI_ZEROMRK_GC239 as zeroMarkReason, xamrmk.FI_CONCESSION_MARK as concessionMark" +
	" from xamrmk, stu, sun, dpt, school, stxres, sunres" + 	
	" where XAMRMK.EXAM_YEAR=" + examYear +
	" and XAMRMK.MK_EXAM_PERIOD_COD=" + examPeriod +
	" and XAMRMK.TYPE='F'" +
	" and XAMRMK.STATUS_CODE='R'" +
	" and XAMRMK.MK_STUDENT_NR=stu.nr" +
	" and XAMRMK.MK_STUDY_UNIT_CODE=sun.code" +
	" and SUN.MK_DEPARTMENT_CODE=dpt.code" +
	" and SUN.COLLEGE_CODE=SCHOOL.COLLEGE_CODE" +
	" and SUN.SCHOOL_CODE=school.code" +
	" and XAMRMK.EXAM_YEAR=STXRES.EXAM_YEAR" +
	" and XAMRMK.MK_EXAM_PERIOD_COD=STXRES.MK_EXAM_PERIOD_COD" +
	" and XAMRMK.MK_STUDENT_NR=STXRES.MK_STUDENT_NR" +
	" and XAMRMK.MK_STUDY_UNIT_CODE=STXRES.MK_STUDY_UNIT_CODE" +
	" and STXRES.MK_RESULT_TYPE_COD=SUNRES.CODE";
	
	if (college != null && college.compareTo(Short.parseShort("0"))!=0){
		sql = sql.trim() + " and sun.college_code=" + college;
	}
	if (dpt != null && dpt.compareTo(Short.parseShort("0"))!=0){
		sql = sql.trim() + " and sun.MK_DEPARTMENT_CODE=" + dpt;
	}
	if (studyUnit != null && !studyUnit.equalsIgnoreCase("")){
		sql = sql.trim() + " and xamrmk.MK_STUDY_UNIT_CODE='" + studyUnit.toUpperCase() + "'";
	}
	if (status != null && !status.equalsIgnoreCase("")){
		sql = sql.trim() + " and XAMRMK.TRACK_STATUS='" + status.toUpperCase() + "'";
	}
	
	if (action.equalsIgnoreCase("NEXT")){
		if (studyUnit != null && !studyUnit.equalsIgnoreCase("")){
			sql = sql.trim() + " and xamrmk.MK_STUDENT_NR >= " + lastRecord.getStudentNumber() +
					" order by SCHOOL.ABBREVIATION,DPT.ENG_DESCRIPTION,XAMRMK.MK_STUDY_UNIT_CODE,XAMRMK.MK_STUDENT_NR";
		}
		if (dpt != null && dpt.compareTo(Short.parseShort("0"))!=0){
			sql = sql.trim() + " and ((xamrmk.MK_STUDENT_NR >= " + lastRecord.getStudentNumber() + " and xamrmk.MK_STUDY_UNIT_CODE = '" + lastRecord.getStudyUnit() + "') or" + 
					" (xamrmk.MK_STUDY_UNIT_CODE > '" + lastRecord.getStudyUnit() + "'))" +
					" order by SCHOOL.ABBREVIATION,DPT.ENG_DESCRIPTION,XAMRMK.MK_STUDY_UNIT_CODE,XAMRMK.MK_STUDENT_NR";
		}	
		if ((studyUnit == null || studyUnit.equalsIgnoreCase("")) && (dpt == null || dpt.compareTo(Short.parseShort("0"))==0)){
			sql = sql.trim() + " and ((xamrmk.MK_STUDENT_NR >= " + lastRecord.getStudentNumber() + " and xamrmk.MK_STUDY_UNIT_CODE = '" + lastRecord.getStudyUnit() + "' and dpt.eng_description = '" + lastRecord.getDptDesc() + "') or" + 
					" (xamrmk.MK_STUDY_UNIT_CODE > '" + lastRecord.getStudyUnit() + "' and dpt.eng_description = '" + lastRecord.getDptDesc() + "') or" +
					" (dpt.eng_description > '" + lastRecord.getDptDesc() + "' and school.abbreviation = '" + lastRecord.getSchoolAbbreviation() + "') or" +
					" (school.abbreviation > '" + lastRecord.getSchoolAbbreviation() + "'))" +
					" order by SCHOOL.ABBREVIATION,DPT.ENG_DESCRIPTION,XAMRMK.MK_STUDY_UNIT_CODE,XAMRMK.MK_STUDENT_NR";
		}	
				
	}
	
	if (action.equalsIgnoreCase("REDISPLAY")){
		if (studyUnit != null && !studyUnit.equalsIgnoreCase("")){
			sql = sql.trim() + " and xamrmk.MK_STUDENT_NR >= " + firstRecord.getStudentNumber() +
					" order by SCHOOL.ABBREVIATION,DPT.ENG_DESCRIPTION,XAMRMK.MK_STUDY_UNIT_CODE,XAMRMK.MK_STUDENT_NR";
		}
		if (dpt != null && dpt.compareTo(Short.parseShort("0"))!=0){
			sql = sql.trim() + " and ((xamrmk.MK_STUDENT_NR >= " + firstRecord.getStudentNumber() + " and xamrmk.MK_STUDY_UNIT_CODE = '" + firstRecord.getStudyUnit() + "') or" + 
					" (xamrmk.MK_STUDY_UNIT_CODE > '" + firstRecord.getStudyUnit() + "'))" +
					" order by SCHOOL.ABBREVIATION,DPT.ENG_DESCRIPTION,XAMRMK.MK_STUDY_UNIT_CODE,XAMRMK.MK_STUDENT_NR";
		}	
		if ((studyUnit == null || studyUnit.equalsIgnoreCase("")) && (dpt == null || dpt.compareTo(Short.parseShort("0"))==0)){
			sql = sql.trim() + " and ((xamrmk.MK_STUDENT_NR >= " + firstRecord.getStudentNumber() + " and xamrmk.MK_STUDY_UNIT_CODE = '" + firstRecord.getStudyUnit() + "' and dpt.eng_description = '" + firstRecord.getDptDesc() + "') or" + 
					" (xamrmk.MK_STUDY_UNIT_CODE > '" + firstRecord.getStudyUnit() + "' and dpt.eng_description = '" + firstRecord.getDptDesc() + "') or" +
					" (dpt.eng_description > '" + firstRecord.getDptDesc() + "' and school.abbreviation = '" + firstRecord.getSchoolAbbreviation() + "') or" +
					" (school.abbreviation > '" + firstRecord.getSchoolAbbreviation() + "'))" +
					" order by SCHOOL.ABBREVIATION,DPT.ENG_DESCRIPTION,XAMRMK.MK_STUDY_UNIT_CODE,XAMRMK.MK_STUDENT_NR";
		}	
				
	}
	
	if (action.equalsIgnoreCase("PREV")){
		if (studyUnit != null && !studyUnit.equalsIgnoreCase("")){
			sql = sql.trim() + " and xamrmk.MK_STUDENT_NR <= " + firstRecord.getStudentNumber() +
					" order by SCHOOL.ABBREVIATION desc,DPT.ENG_DESCRIPTION desc,XAMRMK.MK_STUDY_UNIT_CODE desc,XAMRMK.MK_STUDENT_NR desc";
		}
		if (dpt != null && dpt.compareTo(Short.parseShort("0"))!=0){
			sql = sql.trim() + " and ((xamrmk.MK_STUDENT_NR <= " + firstRecord.getStudentNumber() + " and xamrmk.MK_STUDY_UNIT_CODE = '" + firstRecord.getStudyUnit() + "') or" + 
					" (xamrmk.MK_STUDY_UNIT_CODE < '" + firstRecord.getStudyUnit() + "'))" +
					" order by SCHOOL.ABBREVIATION desc,DPT.ENG_DESCRIPTION desc,XAMRMK.MK_STUDY_UNIT_CODE desc,XAMRMK.MK_STUDENT_NR desc";
		}	
		if ((studyUnit == null || studyUnit.equalsIgnoreCase("")) && (dpt == null || dpt.compareTo(Short.parseShort("0"))==0)){
			sql = sql.trim() + " and ((xamrmk.MK_STUDENT_NR <= " + firstRecord.getStudentNumber() + " and xamrmk.MK_STUDY_UNIT_CODE = '" + firstRecord.getStudyUnit() + "' and dpt.eng_description = '" + firstRecord.getDptDesc() + "') or" + 
					" (xamrmk.MK_STUDY_UNIT_CODE < '" + firstRecord.getStudyUnit() + "' and dpt.eng_description = '" + firstRecord.getDptDesc() + "') or" +
					" (dpt.eng_description < '" + firstRecord.getDptDesc() + "' and school.abbreviation = '" + firstRecord.getSchoolAbbreviation() + "') or" +
					" (school.abbreviation < '" + firstRecord.getSchoolAbbreviation() + "'))" +
					" order by SCHOOL.ABBREVIATION desc,DPT.ENG_DESCRIPTION desc,XAMRMK.MK_STUDY_UNIT_CODE desc,XAMRMK.MK_STUDENT_NR desc";
		}	
	}
	
	if (action.equalsIgnoreCase("FIRST") || action.equalsIgnoreCase("EXTRACT")){
		sql = sql.trim() + " order by SCHOOL.ABBREVIATION,DPT.ENG_DESCRIPTION,XAMRMK.MK_STUDY_UNIT_CODE,XAMRMK.MK_STUDENT_NR";
	}
	
	JdbcTemplate jdt = new JdbcTemplate(getDataSource());
	queryList = jdt.queryForList(sql);
	for (int i=0; i<queryList.size();i++){
			record = new FinalMarkStudentRecord();			
			ListOrderedMap data = (ListOrderedMap) queryList.get(i);
			record.setSchoolAbbreviation(replaceNull(data.get("schoolAbbrev")));
			record.setDptDesc(data.get("dptDesc").toString());
			record.setStudyUnit(data.get("studyUnit").toString());
			Integer stuNum=Integer.parseInt(data.get("studNr").toString());
			record.setStudentNumber(stuNum);
			record.setName(data.get("studName").toString());
			Short  academicYear=Short.parseShort(data.get("acadYear").toString());
			record.setAcademicYear(academicYear);
			Short semester=Short.parseShort(data.get("semester").toString());
			record.setSemesterPeriod(semester);
			record.setFinalMark(Short.parseShort(data.get("finalMark").toString()));
			record.setResultCode(Short.parseShort(data.get("resultCode").toString()));
			record.setResultDesc(data.get("resultDesc").toString());
			record.setResultAbbr(data.get("resultAbbr").toString());
			record.setStatus(data.get("status").toString());
			String acadlevelCode=data.get("academic_level").toString();
			if(acadlevelCode.equalsIgnoreCase("H")||
			    acadlevelCode.equalsIgnoreCase("M")||
                acadlevelCode.equalsIgnoreCase("D")){
				         acadlevelCode="postgraduate";
            }else{
                	   acadlevelCode="undergraduate";
            }
			record.setAcademicLevel(acadlevelCode);			
			
			Person person = new Person();	
			String lecturer = replaceNull(data.get(("lecturer")));
			if (lecturer.equalsIgnoreCase("")){
				lecturer="0";
			}
			if (lecturer.equalsIgnoreCase("0")){
				person=getPrimaryLecturer(record.getStudyUnit(), examPeriod,examYear,networkCode);
			}else{
				PersonnelDAO persDao = new PersonnelDAO();
				person = persDao.getPersonnelFromSTAFF(Integer.parseInt(lecturer));
				if (person.getPersonnelNumber()==null){
					person = getStudentSystemUserfromUSR(lecturer);
					if (person.getStudentSystemUser()==null){
						person.setContactNumber("");
						person.setDepartmentCode("");
						person.setEmailAddress("");
						person.setName("");
						person.setNovellUserId("");
						person.setPersonnelNumber(String.valueOf(lecturer));
						person.setStudentSystemUser("");
					}else{
						person.setPersonnelNumber(person.getStudentSystemUser());
					}
				}
			}
			
			if (person==null) {
				person = new Person();
			}
			
			if (person.getName()==null){
				person.setName("");
			}
			
			record.setPrimaryLecturer(person);
			record.setRevisedFinalMark(replaceNull(data.get("revisedMark")));
			if (record.getRevisedFinalMark().equalsIgnoreCase("")){
				record.setRevisedFinalMark("0");
			}
			record.setConcessionMark(replaceNull(data.get("concessionMark")));
			if (record.getConcessionMark().equalsIgnoreCase("")){
				record.setConcessionMark("0");
			}
			record.setAssessmentAbbr(replaceNull(data.get("assessmentAbbr")));	
			StudentSystemGeneralDAO gendao = new StudentSystemGeneralDAO();
			Gencod gencod = new Gencod();
			if (record.getAssessmentAbbr() == null || record.getAssessmentAbbr().trim().equalsIgnoreCase("")){
				record.setAssessmentDesc("");
			}else{
				gencod = (Gencod)(gendao.getGenCode("91", record.getAssessmentAbbr().trim()));
				record.setAssessmentDesc(gencod.getEngDescription());					
			}
			//record.setAssessmentDesc(replaceNull(data.get("assessmentDesc")));
			record.setAssessmentOtherDesc(replaceNull(data.get("assessmentOther")));
			record.setSupportAbbr(replaceNull(data.get("supportAbbr")));	
			if (record.getSupportAbbr() == null || record.getSupportAbbr().trim().equalsIgnoreCase("")){
				record.setSupportDesc("");
			}else{
				gencod = (Gencod)(gendao.getGenCode("92", record.getSupportAbbr().trim()));
				record.setSupportDesc(gencod.getEngDescription());					
			}			
			//record.setSupportDesc(replaceNull(data.get("supportDesc")));			
			record.setSupportOtherDesc(replaceNull(data.get("supportOther")));	
			studentList.add(record);			
			if (!action.equalsIgnoreCase("EXTRACT") && i==Constants.MAX_RECORDS){				
				i=queryList.size();
			}
		}
	    return studentList;
   }

   private Examination  getExamPeriodDetails(Short academicYear,Short semester,String studyUnit ) throws Exception{
	                 String sql="select  mk_exam_year, mk_exam_period from SUNPDT where  MK_ACADEMIC_YEAR="+academicYear+
			          " and SEMESTER_PERIOD="+semester+ " and FK_SUNCODE='"+studyUnit+"'";
	                 JdbcTemplate jdt = new JdbcTemplate(getDataSource());
	       			  List queryList = jdt.queryForList(sql);
	       			  if((queryList==null)||(queryList.isEmpty())){
	       				     return null;
	       			  }
	       			 Examination  examperiodDetails=new Examination();
	       			 Iterator i = queryList.iterator();
	       			  while (i.hasNext()) {
	       				    ListOrderedMap data = (ListOrderedMap) i.next();
	       				    String examyear=data.get("mk_exam_year").toString();
	       				    if((examyear!=null)&&(!examyear.equals(""))){
	       				            examperiodDetails.setExamYear(Short.parseShort(examyear));
	       				    }
	       				    String examperiod=data.get("mk_exam_period").toString();
	       				    if((examperiod!=null)&&(!examperiod.equals(""))){
	       				          examperiodDetails.setExamPeriod(Short.parseShort(examperiod));
	       				    }
	       			}
	       			return examperiodDetails;
   }
   private int  getExamYear(Short academicYear,Short semester,String studyUnit ) throws Exception{
                             String sql="select  mk_exam_year from SUNPDT where  MK_ACADEMIC_YEAR="+academicYear+ " and SEMESTER_PERIOD="+semester+ " and FK_SUNCODE='"+studyUnit.trim()+"'";
                             JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			                 int examYear = jdt.queryForInt(sql);
			  
			return examYear;
   }
   private int  getExamPeriod(Short academicYear,Short semester,String studyUnit ) throws Exception{
	   
       String sql="select mk_exam_period  from SUNPDT where  MK_ACADEMIC_YEAR="+academicYear+ " and SEMESTER_PERIOD="+semester+ " and FK_SUNCODE='"+studyUnit.trim()+"'";
       JdbcTemplate jdt = new JdbcTemplate(getDataSource());
       int examPeriod = jdt.queryForInt(sql);
           return examPeriod;
    }
   public String  getYearMarkWeight(Short academicYear,Short semester,String studyUnit ) throws Exception{
	                     int examPeriod=getExamPeriod(academicYear,semester,studyUnit );
	                     int examYear=getExamYear(academicYear,semester,studyUnit );
                         String sql=" select year_mark_weight from FINMRK where exam_year="+examYear+
                                 " and mk_exam_period="+examPeriod+" and MK_STUDY_UNIT_CODE='"+studyUnit+"'";
                         JdbcTemplate jdt = new JdbcTemplate(getDataSource());
                         List queryList = jdt.queryForList(sql);
                         String  yearMarkWeight="";
                         Iterator i = queryList.iterator();
                         while (i.hasNext()) {
                               ListOrderedMap data = (ListOrderedMap) i.next();
                               yearMarkWeight=data.get("year_mark_weight").toString();
                        }
                      return yearMarkWeight;
   }
   public String  getStuYearMarkxx(Short academicYear,Short semester,int stuNum,String studyUnit ) throws Exception{
	                    int examPeriod=getExamPeriod(academicYear,semester,studyUnit );
                        int examYear=getExamYear(academicYear,semester,studyUnit );
                        String sql= " select year_mark from STXRES where "+
                               " STXRES.exam_year="+examYear+" and mk_exam_period_cod="+examPeriod+" and mk_student_nr="+stuNum+ 
                               " and mk_study_unit_code='"+studyUnit.trim()+"'";
                        JdbcTemplate jdt = new JdbcTemplate(getDataSource());
                        List queryList = jdt.queryForList(sql);
                        String  yearMark="";
                        Iterator i = queryList.iterator();
                        while (i.hasNext()) {
                             ListOrderedMap data = (ListOrderedMap) i.next();
                             yearMark=replaceNull(data.get("year_mark").toString());
                       }
                      return yearMark;
   }
   public String  getStuYearMark(short examYear,short examPeriod,int stuNum,String studyUnit ) throws Exception{
      
       String sql= " select year_mark from STXRES where "+
              " STXRES.exam_year="+examYear+" and mk_exam_period_cod="+examPeriod+" and mk_student_nr="+stuNum+ 
              " and mk_study_unit_code='"+studyUnit.trim()+"'";
       JdbcTemplate jdt = new JdbcTemplate(getDataSource());
       List queryList = jdt.queryForList(sql);
       String  yearMark="";
       Iterator i = queryList.iterator();
       while (i.hasNext()) {
            ListOrderedMap data = (ListOrderedMap) i.next();
            yearMark=replaceNull(data.get("year_mark").toString());
      }
     return yearMark;
}
  private String replaceNull(Object object){
		String stringValue="";
		if (object==null){
		}else{
			stringValue=object.toString().trim();
		}
	    return stringValue;
	}
	public Person getCurrPrimaryLecturer(String studyUnit,String networkCode){
		             GregorianCalendar calCurrent = new GregorianCalendar();
		             short currYear=(short)calCurrent.get(Calendar.YEAR);
		
		             String sql= "Select a.novell_user_id networkCode,b.persno  personnelNum,name,surname,initials from usrsun a, staff b  where a.system_type='L'  and access_level ='PRIML'  and "+
		        		     " UPPER(a.mk_study_unit_code) = UPPER('"+studyUnit.trim()+"') and a.mk_academic_year ="+currYear +"  and b.RESIGN_DATE is null"+
		        		     " and UPPER(b.novell_user_id)=UPPER(a.novell_user_id) and UPPER(b.novell_user_id)='"+networkCode.toUpperCase()+"'";
		             Person person =null;
		           try{ 
			  JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			  List queryList = jdt.queryForList(sql);
			  if((queryList==null)||(queryList.isEmpty())){
				     return null;
			  }
			 Iterator i = queryList.iterator();
			  while (i.hasNext()) {
				   ListOrderedMap data = (ListOrderedMap) i.next();
				   person=new Person();
				   person.setNovellUserId(data.get("networkCode").toString());
				   person.setPersonnelNumber(data.get("personnelNum").toString());
				   person.setName(data.get("surname").toString()+"  "+data.get("name").toString()+  "  "+data.get("initials").toString());
				}
			}catch (Exception ex) {
			          
			}		
			return person;		
   }
	public List<Person> getCurrResponsibleLecturerList(String studyUnit,String networkCode){
		 GregorianCalendar calCurrent = new GregorianCalendar();
         int currYear=calCurrent.get(Calendar.YEAR);
         String sql= "Select a.novell_user_id networkCode,b.persno personnelNum,name,surname,initials from usrsun a, staff b  where a.system_type='L'  and access_level in ('PRIML','SECDL')  and "+
        		     "a.mk_study_unit_code = '"+studyUnit.trim()+"' and a.mk_academic_year ="+currYear +"  and ( (b.RESIGN_DATE is null) or (b.resign_date >sysdate))"+
        		     " and nvl(b.novell_user_id,' ')=a.novell_user_id and nvl(b.novell_user_id,' ')='"+networkCode.toUpperCase()+"'";
		              List<Person> lecturerList=new ArrayList<Person>();          
		              
		 try{ 
			  JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			  List queryList = jdt.queryForList(sql);
			  Iterator i = queryList.iterator();
			  while (i.hasNext()) {
				   ListOrderedMap data = (ListOrderedMap) i.next();
				   Person person = new Person();
				   person.setNovellUserId(data.get("networkCode").toString());
				   person.setPersonnelNumber(data.get("personnelNum").toString());
				   person.setName(data.get("surname").toString()+"  "+data.get("name").toString()+  "  "+data.get("initials").toString());
				   lecturerList.add(person);
			  }
			}catch (Exception ex) {
			          
			}		
			return lecturerList;		
   }
	
	public String getStudent(int studentNr) throws Exception {
		
		PreparedStatement pst = null;
		Connection con = null;
	    ResultSet rs = null;
	    String studentName="";
	    
	    try{
		    JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		    con = jdt.getDataSource().getConnection();		
		    String sql =
		        "select STU.SURNAME || ' ' || STU.INITIALS || ' ' || stu.MK_TITLE as stuName from stu where nr = ?";
		    pst = con.prepareStatement(sql);	
		    pst.setInt(1, studentNr);
		    rs = pst.executeQuery();
		    if (rs.next()){
		    	studentName = rs.getString("stuName");	    
		    }		     
		    return studentName;
	    }
		    catch (Exception ex) {
				throw new Exception("FinalMarkConcessionDAO  : : Error reading table stu/ " + ex, ex);				
		    } finally {
				try {
					if (con!=null){con.close();}
					if (pst!=null){pst.close();}
					if (rs!=null){rs.close();}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}		
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
