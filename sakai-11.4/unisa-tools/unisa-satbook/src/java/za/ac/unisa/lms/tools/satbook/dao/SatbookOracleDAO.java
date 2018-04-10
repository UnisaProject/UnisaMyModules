package za.ac.unisa.lms.tools.satbook.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.struts.util.LabelValueBean;

import org.apache.commons.collections.map.ListOrderedMap;
import org.springframework.jdbc.core.JdbcTemplate;

import za.ac.unisa.lms.db.StudentSystemDAO;
import za.ac.unisa.lms.tools.satbook.forms.BookingForm;
import za.ac.unisa.lms.tools.satbook.forms.ClassroomForm;
import za.ac.unisa.lms.tools.satbook.forms.LecturerForm;

public class SatbookOracleDAO extends StudentSystemDAO {

	public ArrayList selectRegions() throws Exception {
		ArrayList regionList = new ArrayList();
		regionList.add(new org.apache.struts.util.LabelValueBean("Select Region.. ", "0"));

		String select = "SELECT CODE, ENG_DESCRIPTION "+
		                "FROM REGOFF "+
		                "ORDER BY ENG_DESCRIPTION ";

    	try{
    		JdbcTemplate jdt = new JdbcTemplate(super.getDataSource());
    		List regionInfoList = jdt.queryForList(select);
			Iterator j = regionInfoList.iterator();
    		while (j.hasNext()) {
    			ListOrderedMap data = (ListOrderedMap) j.next();
    			regionList.add(new org.apache.struts.util.LabelValueBean(data.get("ENG_DESCRIPTION").toString(), data.get("CODE").toString()));

    		} // end while

    	} catch (Exception ex) {
    		throw new Exception("SatbookOracleDAO: selectRegions: Error occurred / "+ ex,ex);
    	} // end try

		return regionList;
	}

	public String selectRegionDesc(String regionCode) throws Exception {
		String regionDesc = "";

		String select = "SELECT ENG_DESCRIPTION "+
		                "FROM   REGOFF "+
		                "WHERE  CODE = '"+regionCode+"' ";

  		try{
    		regionDesc = this.querySingleValue(select,"ENG_DESCRIPTION");

     	} catch (Exception ex) {
    		throw new Exception("SatbookOracleDAO: selectRegionDesc: Error occurred / "+ ex,ex);
    	} // end try

		return regionDesc;
	}

	/**
	 * Test if classroom address already exist for region.
	*/
	public boolean classroomAddressExist(String regionCode) throws Exception {
		String cAddressExist;
		boolean classroomAddressExist = false;

		String sql1 = "SELECT count(*) AS A "+
						"FROM   ADR "+
						"WHERE  FK_ADRCATYPFK_ADRC = 25 "+
						"AND    FK_ADRCATYPFK_ADRT = 10 "+
						"AND    REFERENCE_NO = "+regionCode+" ";

		try{
			cAddressExist = this.querySingleValue(sql1,"A");

		} catch (Exception ex) {
			throw new Exception("SatbookOracleDAO: classroomAddressExist (SELECT): Error occurred / "+ ex,ex);
		}

		int cAddressExistInt = Integer.parseInt(cAddressExist);

		if (cAddressExistInt > 0) {
			classroomAddressExist = true;
		} else {
			classroomAddressExist = false;
		}

		return classroomAddressExist;
	}

	/**
	 * Test if classroom address already exist for region.
	*/
	public void selectClassroomAddressDetail(ClassroomForm classr) throws Exception {
		String adr1,adr2,adr3,adr4,adrp;
		ClassroomForm classroom = (ClassroomForm) classr;

		classroom.setRegionAddress1(" ");
		classroom.setRegionAddress2(" ");
		classroom.setRegionAddress3(" ");
		classroom.setRegionAddress4(" ");
		classroom.setRegionAddressPcode(" ");

		String sql1 = "SELECT   NVL(ADDRESS_LINE_1,' ') AS A1,NVL(ADDRESS_LINE_2,' ') AS A2,NVL(ADDRESS_LINE_3,' ') AS A3,NVL(ADDRESS_LINE_4,' ') AS A4,POSTAL_CODE "+
						"FROM   ADR "+
						"WHERE  FK_ADRCATYPFK_ADRC = 25 "+
						"AND    FK_ADRCATYPFK_ADRT = 10 "+
						"AND    REFERENCE_NO = "+classroom.getRegionCode()+" ";

		try{
	   		JdbcTemplate jdt = new JdbcTemplate(super.getDataSource());
    		List regionInfoList = jdt.queryForList(sql1);
			Iterator j = regionInfoList.iterator();
    		while (j.hasNext()) {
    			ListOrderedMap data = (ListOrderedMap) j.next();

  				classroom.setRegionAddress1(data.get("A1").toString());
   				classroom.setRegionAddress2(data.get("A2").toString());
   				classroom.setRegionAddress3(data.get("A3").toString());
   				classroom.setRegionAddress4(data.get("A4").toString());
    			if ((null == data.get("POSTAL_CODE").toString())||(data.get("POSTAL_CODE").toString().length() == 0)) {
    				classroom.setRegionAddressPcode(" ");
    			} else {
    				classroom.setRegionAddressPcode(data.get("POSTAL_CODE").toString());
    			}

    		} // end while

		} catch (Exception ex) {
			throw new Exception("SatbookOracleDAO: selectClassroomAddressDetail (SELECT): Error occurred / "+ ex,ex);
		}
	}

	public void insertClassroomAddress(ClassroomForm classroom) throws Exception{

		boolean classroomAddressExist = false;

		try {
			classroomAddressExist = classroomAddressExist(classroom.getRegionCode());
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		if (classroomAddressExist == false) {
			// INSERT CLASSROOM ADDRESS
			String sql3 = "INSERT INTO ADR(REFERENCE_NO,ADDRESS_LINE_1,ADDRESS_LINE_2,ADDRESS_LINE_3, "+
			        "ADDRESS_LINE_4, POSTAL_CODE, FK_ADRCATYPFK_ADRC, FK_ADRCATYPFK_ADRT) "+
					"VALUES (?,?,?,?,?,?,?,?)";

			try{
				JdbcTemplate jdt1 = new JdbcTemplate(super.getDataSource());
				jdt1.update(sql3,new Object[] {classroom.getRegionCode(),classroom.getRegionAddress1(),
						classroom.getRegionAddress2(),classroom.getRegionAddress3(),classroom.getRegionAddress4(),
						classroom.getRegionAddressPcode(), "25","10"});
			} catch (Exception ex) {
				throw new Exception("SatbookOracleDAO: insertClassroomAddress (INSERT): Error occurred / "+ ex,ex);
			}
		} else {
			// UPDATE CLASSROOM ADDRESS
			String sql4 = "UPDATE ADR " +
					"SET ADDRESS_LINE_1 = '"+classroom.getRegionAddress1()+"', " +
					"    ADDRESS_LINE_2 = '"+classroom.getRegionAddress2()+"', " +
					"    ADDRESS_LINE_3 = '"+classroom.getRegionAddress3()+"', " +
					"    ADDRESS_LINE_4 = '"+classroom.getRegionAddress4()+"', " +
					"    POSTAL_CODE = "+classroom.getRegionAddressPcode()+" "+
					"WHERE REFERENCE_NO = '"+classroom.getRegionCode()+"' "+
					"AND FK_ADRCATYPFK_ADRC = 25 "+
					"AND FK_ADRCATYPFK_ADRT = 10 ";

			try{
				JdbcTemplate jdt1 = new JdbcTemplate(super.getDataSource());
				jdt1.update(sql4,new Object[] {});
			} catch (Exception ex) {
				throw new Exception("SatbookOracleDAO: insertClassroomAddress (UPDATE): Error occurred / "+ ex,ex);
			}
		}
	}

	/**
	 * Test if lecturer exists in table STAFF.
	*/
	public String lecturerExistStaff(String lecturerNovellId) throws Exception {
		String lecturerDetail = "";

		String sql1 = "SELECT   TITLE||' '||INITIALS||' '||SURNAME||'#'||NVL(CONTACT_TELNO,' ') AS LDETAILS "+
						"FROM   STAFF "+
						"WHERE  upper(NOVELL_USER_ID) = upper('"+lecturerNovellId+"') ";

		try{
			lecturerDetail = this.querySingleValue(sql1,"LDETAILS");

		} catch (Exception ex) {
			throw new Exception("SatbookOracleDAO: lecturerExistStaff (SELECT): Error occurred / "+ ex,ex);
		}

		return lecturerDetail;
	}
	//QUERY TO SELECT LECTURER CONTACT DETAILS
    public String getlecturerContactDetails(String lecturerNovellId) throws Exception {
          String lecturerDetail = "";

          String sql1 = "SELECT NAME||' '||INITCAP(SURNAME)||'#'||NVL(CONTACT_TELNO,' ') ||'#'||NVL(EMAIL_ADDRESS,' ') AS LDETAILS "+
                              "FROM STAFF "+
                              " WHERE upper(NOVELL_USER_ID) = upper('"+lecturerNovellId+"')";
          try{
          lecturerDetail = this.querySingleValue(sql1,"LDETAILS");
          if(lecturerDetail.length()==0){
          String sql2 = "SELECT   NAME||'#'||NVL(PHONE_NUMBER,' ') ||'#'||NVL(E_MAIL,' ') AS LDETAILS "+
                "FROM   USR "+
                "WHERE  upper(NOVELL_USER_CODE) = upper('"+lecturerNovellId+"') ";
          lecturerDetail = this.querySingleValue(sql2,"LDETAILS");
          }
          } catch (Exception ex) {
                throw new Exception("SatbookOracleDAO: lecturerExistStaff (SELECT): Error occurred / "+ ex,ex);
          }

          return lecturerDetail;
    }
    
	
	/**
	 * Test if lecturer exists in table USR.
	*/
	public String lecturerExistUsr(String lecturerNovellId) throws Exception {
		String lecturerDetail = "";

		String sql1 = "SELECT   NAME||'#'||NVL(PHONE_NUMBER,' ') AS LDETAILS "+
						"FROM   USR "+
						"WHERE  upper(NOVELL_USER_CODE) = upper('"+lecturerNovellId+"') ";

		try{
			lecturerDetail = this.querySingleValue(sql1,"LDETAILS");

		} catch (Exception ex) {
			throw new Exception("SatbookOracleDAO: lecturerExistUsr (SELECT): Error occurred / "+ ex,ex);
		}

		return lecturerDetail;
	}

	public ArrayList selectSubjects(String lecturer, String acadYear, String acadPeriod) throws Exception {
		ArrayList subjectList = new ArrayList();

		String select = "SELECT MK_STUDY_UNIT_CODE "+
        			    "FROM   USRSUN "+
        			    "WHERE  SYSTEM_TYPE = 'L' "+
        			    "AND    upper(NOVELL_USER_ID) = upper('"+lecturer+"') "+
        			    "AND    ACCESS_LEVEL IN ('PRIML','SECDL','CADMN') "+
        			    "AND    MK_ACADEMIC_YEAR = "+acadYear+" "+
        			    "AND    MK_SEMESTER_PERIOD = "+acadPeriod;

    	try{
    		JdbcTemplate jdt = new JdbcTemplate(super.getDataSource());
    		List examInfoList = jdt.queryForList(select);
			Iterator j = examInfoList.iterator();
	   		while (j.hasNext()) {
    			ListOrderedMap data = (ListOrderedMap) j.next();

    			subjectList.add(new org.apache.struts.util.LabelValueBean(data.get("MK_STUDY_UNIT_CODE").toString(), data.get("MK_STUDY_UNIT_CODE").toString()));

	   		}
    	} catch (Exception ex) {
    		throw new Exception("SatbookOracleDAO: selectSubjects: Error occurred / "+ ex,ex);
    	} // end try

		return subjectList;
	}

	public ArrayList selectLinkedSubjects(String subjCode, String acadPeriod, String acadYear) throws Exception {
		ArrayList subjectList = new ArrayList();

		String select = "SELECT MK_EQUIVALENT_STUD "+
        			    "FROM   DSUNEQ "+
        			    "WHERE  ACADEMIC_YEAR = "+acadYear+" "+
        			    "AND    ACADEMIC_PERIOD = "+acadPeriod+" "+
        			    "AND    MK_STUDY_UNIT_CODE = '"+subjCode+"'";

    	try{
    		JdbcTemplate jdt = new JdbcTemplate(super.getDataSource());
    		List examInfoList = jdt.queryForList(select);
			Iterator j = examInfoList.iterator();
	   		while (j.hasNext()) {
    			ListOrderedMap data = (ListOrderedMap) j.next();

    			subjectList.add(new org.apache.struts.util.LabelValueBean(data.get("MK_EQUIVALENT_STUD").toString(), data.get("MK_STUDY_UNIT_CODE").toString()));

	   		}
    	} catch (Exception ex) {
    		throw new Exception("SatbookOracleDAO: selectLinkedSubjects: Error occurred / "+ ex,ex);
    	} // end try

		return subjectList;
	}

	/*
	 * select stusun.FK_STUDENT_NR, adrph.EMAIL_ADDRESS, solact.EMAIL_VERIFIED
from   stusun, adrph, SOLACT
where  stusun.MK_STUDY_UNIT_CODE = 'ACN101M'
and    stusun.FK_ACADEMIC_YEAR = 2007
and    stusun.FK_ACADEMIC_PERIOD = 1
and    stusun.FK_STUDENT_NR = adrph.REFERENCE_NO
and    solact.STUDENT_NR = stusun.FK_STUDENT_NR
and    solact.EMAIL_VERIFIED = 'Y'
and   (nvl(adrph.email_address,'X') != 'X' and adrph.email_address != ' ');
	 */
	public String[] selectStudentEmailList(String subjCode, String acadPeriod, String acadYear) throws Exception {
		String[] emailList = null;
		int counter = 0;

		String select = "SELECT adrph.EMAIL_ADDRESS as EMAIL "+
						"FROM   stusun, adrph, solact "+
						"WHERE  stusun.MK_STUDY_UNIT_CODE = '"+subjCode+"' "+
						"and    stusun.FK_ACADEMIC_YEAR = "+acadYear+" "+
						"and    stusun.FK_ACADEMIC_PERIOD = "+acadPeriod+" "+
						"and    stusun.FK_STUDENT_NR = adrph.REFERENCE_NO "+
						"and    solact.STUDENT_NR = stusun.FK_STUDENT_NR "+
						"and    solact.EMAIL_VERIFIED = 'Y' "+
						"and   (nvl(adrph.email_address,'X') != 'X' and adrph.email_address != ' ')";

    	try{
    		JdbcTemplate jdt = new JdbcTemplate(super.getDataSource());
    		List examInfoList = jdt.queryForList(select);
			Iterator j = examInfoList.iterator();
	   		while (j.hasNext()) {
    			ListOrderedMap data = (ListOrderedMap) j.next();
    			emailList[counter] = data.get("EMAIL").toString();
    			counter++;
	   		}
    	} catch (Exception ex) {
    		throw new Exception("SatbookOracleDAO: selectStudentEmailList: Error occurred / "+ ex,ex);
    	} // end try

		return emailList;
	}

	public String getLecturerEmailAddress(String lecturerNovellId) throws Exception {
		String lecturerEmail = "";

		String sql1 = "SELECT   EMAIL_ADDRESS "+
						"FROM   STAFF "+
						"WHERE  upper(NOVELL_USER_ID) = upper('"+lecturerNovellId+"') ";

		try{
			lecturerEmail = this.querySingleValue(sql1,"EMAIL_ADDRESS");

		} catch (Exception ex) {
			throw new Exception("SatbookOracleDAO: getLecturerEmailAddress (SELECT): Error occurred / "+ ex,ex);
		}

		if ((lecturerEmail == null)||(lecturerEmail.length()==0)) {
			String sql2 = "SELECT E_MAIL "+
						  "FROM   USR "+
						  "WHERE  upper(NOVELL_USER_CODE) = upper('"+lecturerNovellId+"') ";

			try{
				lecturerEmail = this.querySingleValue(sql2,"E_MAIL");

			} catch (Exception ex) {
				throw new Exception("SatbookOracleDAO: getLecturerEmailAddress (SELECT): Error occurred / "+ ex,ex);
			}
		}

		return lecturerEmail;
	}

	/**
	 * This method executes a query and returns a String value as result.
	 * An empty String value is returned if the query returns no results.
	 *
	 * @param query       The query to be executed on the database
	 * @param field		  The field that is queried on the database
	 * method written by: E Penzhorn
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
			 if (data.get(field) == null){
			 } else {
				 result = data.get(field).toString();
			 }
 	   }
 	   return result;
	}

	/**
	 * Test if lecturer exists in table STAFF.
	*/
	public void selectLecturerDetail(LecturerForm form) throws Exception {
		LecturerForm lecturer = (LecturerForm) form;
		String sql1 = "SELECT PERSNO, TITLE||' '||INITIALS||' '||SURNAME AS FULLNAME, NVL(NAME,' ') AS NME, "+
					  "NVL(EMAIL_ADDRESS,' ') AS EMAIL, NVL(CONTACT_TELNO,' ') AS CON1, NVL(OFFICE_NO,' ') AS CON2, "+
					  "ENG_DESCRIPTION "+
					  "FROM STAFF , DPT "+
					  "WHERE UPPER(NOVELL_USER_ID) = '"+lecturer.getNovellId()+"' "+
					  "AND   CODE = MK_DEPT_CODE";

    	try{
    		JdbcTemplate jdt = new JdbcTemplate(super.getDataSource());
    		List lectInfoList = jdt.queryForList(sql1);
			Iterator j = lectInfoList.iterator();
	   		while (j.hasNext()) {
    			ListOrderedMap data = (ListOrderedMap) j.next();

    			lecturer.setPersNumber(data.get("PERSNO").toString());
    			lecturer.setName(data.get("FULLNAME").toString());
    			lecturer.setEmail(data.get("EMAIL").toString());
    			lecturer.setContact1(data.get("CON1").toString());
    			lecturer.setContact2(data.get("CON2").toString());
    			lecturer.setDepartment(data.get("ENG_DESCRIPTION").toString());
	   		}
    	} catch (Exception ex) {
    		throw new Exception("SatbookOracleDAO: selectLecturerDetail: Error occurred / "+ ex,ex);
    	} // end try
	}
	/*public String selectSelectedSubject(String subject)throws Exception{
		 ArrayList subjectDetails = new ArrayList();

		SatbookOracleDAO db2 = new SatbookOracleDAO();
		String tmpSubject="";
		String select ="SELECT ENG_SHORT_DESCRIPT FROM SUN WHERE CODE ='"+subject+"'";
		try{
			JdbcTemplate jdt = new JdbcTemplate(super.getDataSource());
			List atList = jdt.queryForList(select);
			Iterator j = atList.iterator();
			
			while (j.hasNext()) {	
				ListOrderedMap data = (ListOrderedMap) j.next();
				tmpSubject = data.get("ENG_SHORT_DESCRIPT").toString();
				 BookingForm booking = new BookingForm();
				 booking.setSubName(data.get("ENG_SHORT_DESCRIPT").toString());
				 booking.setSelectedSubject(tmpSubject);    				
	   			
			
			}//end while
			}catch (Exception ex) {
	    		throw new Exception("SatbookBookingDAO: selectSelectedSubject: Error occurred / "+ ex,ex);
	    	} // end try
		return tmpSubject;

	}*/
	public BookingForm selectSelectedSubject(BookingForm bookingIn)throws Exception{
		 ArrayList subjectDetailsList = new ArrayList();
		 BookingForm booking = (BookingForm)bookingIn;
		 ArrayList al =new ArrayList(booking.getSelectedSubjectsAL());
		 String tmpName="";
		 String tmpSubjects1="";
		 for(int i=0;i<al.size();i++){
				LabelValueBean tmpSubjCode = (LabelValueBean) al.get(i);
				tmpName = tmpName+tmpSubjCode.getValue()+"; ";
				tmpSubjects1 = tmpSubjCode.getValue();
				
		String select ="SELECT ENG_SHORT_DESCRIPT FROM SUN WHERE CODE ='"+tmpSubjects1+"'"+
		                "ORDER BY ENG_SHORT_DESCRIPT ";
		try{
			JdbcTemplate jdt = new JdbcTemplate(super.getDataSource());
			List atList = jdt.queryForList(select);
			Iterator j = atList.iterator();
			
			while (j.hasNext()) {	
				ListOrderedMap data = (ListOrderedMap) j.next();
				
				subjectDetailsList.add(new org.apache.struts.util.LabelValueBean(data.get("ENG_SHORT_DESCRIPT").toString(), data.get("ENG_SHORT_DESCRIPT").toString()));
						
			}//end while
			}catch (Exception ex) {
	    		throw new Exception("SatbookBookingDAO: selectSelectedSubject: Error occurred / "+ ex,ex);
	    	} // end try
		 }
		       booking.setSelectedSubjectNamesAL(subjectDetailsList);
		return booking;

	}
	
	public String selectUserDpt(String user) throws Exception {
		String userDpt = "";

		String sql1 = " select nvl(eng_description,'Not Available') AS DPT"+
					  " from staff,dpt "+ 
					  " where upper(staff.novell_user_id) = upper('"+user+"')"+
					  " and   staff.mk_dept_code = dpt.code(+)";
		try{
			userDpt = this.querySingleValue(sql1,"DPT");

		} catch (Exception ex) {
			throw new Exception("SatbookOracleDAO: selectUserDpt (SELECT): Error occurred / "+ ex,ex);
		}

		if ((userDpt == null)||(userDpt.equals("Not Available"))||(userDpt.equals(""))) {
			String sql2 = " select nvl(eng_description,'Not Available') AS DPT "+
						  " from usr, dpt"+ 
						  " where upper(novell_user_code) = upper('"+user+"')"+
						  " and   usr.mk_department_code = dpt.code(+)";

			try{
				userDpt = this.querySingleValue(sql2,"DPT");

			} catch (Exception ex) {
				throw new Exception("SatbookOracleDAO: selectUserDpt (SELECT): Error occurred / "+ ex,ex);
			}
		}
		if ((userDpt == null)||(userDpt.equals("Not Available"))||(userDpt.equals(""))) {
			userDpt = "Not Available";
		}

		return userDpt;
	}
}
