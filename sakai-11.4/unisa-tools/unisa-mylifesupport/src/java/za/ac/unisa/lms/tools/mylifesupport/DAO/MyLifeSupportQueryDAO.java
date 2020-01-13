package za.ac.unisa.lms.tools.mylifesupport.DAO;

import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;

import org.apache.commons.collections.map.ListOrderedMap;
import org.springframework.jdbc.core.JdbcTemplate;

import za.ac.unisa.lms.db.StudentSystemDAO;
import za.ac.unisa.lms.tools.mylifesupport.forms.MyLifeSupportForm;

public class MyLifeSupportQueryDAO extends StudentSystemDAO  {
	
	public boolean getStudentExist(String studentNr) throws Exception{

		boolean studentExist = false;
		String query = "SELECT COUNT(*) AS A "+
	   	  				"FROM  STU " +
	   	  				"WHERE NR  = " + studentNr;

		try{
			int counter = Integer.parseInt(this.querySingleValue(query,"A"));
			if (counter >= 1) {
				studentExist = true;
			}

		} catch (Exception ex) {
            throw new Exception("MyLifeSupportQueryDAO: getStudentCell: (stno: "+studentNr+") Error occurred / "+ ex,ex);
		}
		return studentExist;
	}
	



	public void getStudentEmail(MyLifeSupportForm form) throws Exception{
		MyLifeSupportForm mylifesupportform = (MyLifeSupportForm) form;

		String email = "";
		String password = "";
		String query = "select  nvl(TRIM(EXCHANGEMAIL),' ') AS EXCHANGEMAIL, nvl(TRIM(PASSWORD),' ') AS PASSWORD "+
				" from  IDVALT " +
				" where MK_STUDENT_NR = "  + mylifesupportform.getStudentNr();

		try{
			
	   		JdbcTemplate jdt = new JdbcTemplate(super.getDataSource());
    		List studentEmailList = jdt.queryForList(query);
			Iterator j = studentEmailList.iterator();
    		while (j.hasNext()) {
    			ListOrderedMap data = (ListOrderedMap) j.next();
    			email = data.get("EXCHANGEMAIL").toString();
    			password = data.get("PASSWORD").toString();
    			if ((email == null)||(email.length() == 0)||(email.equals(""))) {
    				email = "";
    			}else if((password == null)||(password.length() == 0)||(password.equals(""))) {
    				password = "";
    		} }// end while
	

		} catch (Exception ex) {
            throw new Exception("MyLifeSupportQueryDAO: getStudentEmail: (stno: "+mylifesupportform.getStudentNr()+") Error occurred / "+ ex,ex);
		}
		mylifesupportform.setMylifEmail(email);
		mylifesupportform.setMylifePwd(password);	
	}

	public void getPersonnelDetails(MyLifeSupportForm form) throws Exception{
		MyLifeSupportForm mylifesupportform = (MyLifeSupportForm) form;
		String sql="select nvl(SURNAME,' ')SURNAME,nvl(FIRST_NAMES,' ')FIRST_NAMES,nvl(Identity_nr,' ')"+
		            "Identity_nr,Birth_date,nvl(PASSPORT_NO,' ')PASSPORT_NO from Stu where NR ="+mylifesupportform.getStudentNr();
             try{
			
	   		JdbcTemplate jdt = new JdbcTemplate(super.getDataSource());
    		List studentEmailList = jdt.queryForList(sql);
			Iterator j = studentEmailList.iterator();
    		while (j.hasNext()) {
    			ListOrderedMap data = (ListOrderedMap) j.next();
    			mylifesupportform.setSurName(data.get("SURNAME").toString());
    			mylifesupportform.setFirstName(data.get("FIRST_NAMES").toString());
    			mylifesupportform.setStudentID(data.get("Identity_nr").toString());
    			mylifesupportform.setPassportNr(data.get("PASSPORT_NO").toString());
    			mylifesupportform.setBirthDate(data.get("Birth_date").toString().substring(0, 10));
    		}  	

	} catch (Exception ex) {
        throw new Exception("MyLifeSupportQueryDAO: getPersonnelDetails: (stno: "+mylifesupportform.getStudentNr()+") Error occurred / "+ ex,ex);
		}
             }
	
	public void getPhonenumber(MyLifeSupportForm form) throws Exception{
		MyLifeSupportForm mylifesupportform = (MyLifeSupportForm) form;
		String sql = "select nvl(HOME_NUMBER,' ')HOME_NUMBER ,nvl(CELL_NUMBER,' ')CELL_NUMBER from ADRPH where FK_ADRCATCODE=1 and REFERENCE_NO ="+mylifesupportform.getStudentNr();
		 try{
				
		   		JdbcTemplate jdt = new JdbcTemplate(super.getDataSource());
	    		List studentEmailList = jdt.queryForList(sql);
				Iterator j = studentEmailList.iterator();
	    		while (j.hasNext()) {
	    			ListOrderedMap data = (ListOrderedMap) j.next();
	    			mylifesupportform.setHomeNumber(data.get("HOME_NUMBER").toString());
	    			mylifesupportform.setCellNumber(data.get("CELL_NUMBER").toString());
	    		}  	
			} catch (Exception ex) {
		        throw new Exception("MyLifeSupportQueryDAO: getPhonenumber: (stno: "+mylifesupportform.getStudentNr()+") Error occurred / "+ ex,ex);
				}
		             }
	    			

	public void getStudentAddr(MyLifeSupportForm form) throws Exception{
		MyLifeSupportForm mylifesupportform = (MyLifeSupportForm) form;
		String sql ="select ADDRESS_LINE_1,ADDRESS_LINE_2,nvl(ADDRESS_LINE_3,' ') ADDRESS_LINE_3 ,nvl(ADDRESS_LINE_4,' ')ADDRESS_LINE_4,nvl(ADDRESS_LINE_5,' ') ADDRESS_LINE_5,POSTAL_CODE from ADR " +
				"where FK_ADRCATYPFK_ADRC=1 and FK_ADRCATYPFK_ADRT=1 and REFERENCE_NO ="+mylifesupportform.getStudentNr(); 
		try{
				JdbcTemplate jdt = new JdbcTemplate(getDataSource());
				List queryList = jdt.queryForList(sql);

				Iterator i = queryList.iterator();
				while (i.hasNext()) {
					ListOrderedMap data = (ListOrderedMap) i.next();
					String addr1=data.get("ADDRESS_LINE_1").toString();
					String addr2=data.get("ADDRESS_LINE_2").toString();
					String addr3=data.get("ADDRESS_LINE_3").toString();
					String addr4=data.get("ADDRESS_LINE_4").toString();
					String addr5=data.get("ADDRESS_LINE_5").toString();
					String addr6=data.get("POSTAL_CODE").toString();
					
					mylifesupportform.setStudentAddress(addr1+" "+addr2+" "+addr3+" "+addr4+" "+addr5+" Postal Code :"+addr6);
					}			
		}catch (Exception ex) {
		            throw new Exception("MyLifeSupportQueryDAO: getStudentAddr (Select): Error occurred / "+ ex,ex);
				}
	}

	public void getStuJoinInfo(MyLifeSupportForm form) throws Exception{
		MyLifeSupportForm mylifesupportform = (MyLifeSupportForm) form;
		String sql ="select mk_academic_year,nvl(registration_date,'01/Dec/99')registration_date,MK_HIGHEST_QUALIFI,nvl(SPECIALITY_CODE,' ')SPECIALITY_CODE  from   stuann "+
		           " where  mk_student_nr = "+ mylifesupportform.getStudentNr()+" and mk_academic_year = (select max(mk_academic_year)" +
				   " from stuann where status_code='RG' and  mk_student_nr = "+ mylifesupportform.getStudentNr()+  ")";
			try{
				JdbcTemplate jdt = new JdbcTemplate(getDataSource());
				List queryList = jdt.queryForList(sql);

				Iterator i = queryList.iterator();
				while (i.hasNext()) {
					ListOrderedMap data = (ListOrderedMap) i.next();

					String tmpDate=data.get("registration_date").toString().substring(0,4);
					if(tmpDate.equals("1999")){
						tmpDate="";
						mylifesupportform.setRegDate(tmpDate);
					}else{
						mylifesupportform.setRegDate(data.get("registration_date").toString().substring(0, 11));	
					}
					
					mylifesupportform.setRegStatus("Registered for "+data.get("mk_academic_year").toString());
					mylifesupportform.setHighestQual(data.get("MK_HIGHEST_QUALIFI").toString());
					mylifesupportform.setSpecialityCode(data.get("SPECIALITY_CODE").toString());
					
				}			
			}catch (Exception ex) {
			            throw new Exception("MyLifeSupportQueryDAO: getStuJoinInfo (Select): Error occurred / "+ ex,ex);
					}
	}

	/**
	 * method: querySingleValue
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
	}
	
	
