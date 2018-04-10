package za.ac.unisa.lms.tools.password.dao;

import java.sql.Types;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.map.ListOrderedMap;
import org.springframework.jdbc.core.JdbcTemplate;

import za.ac.unisa.lms.db.StudentSystemDAO;

public class ForgetPasswordStudentSystemDAO extends StudentSystemDAO  {
	
	
  
    public int getStudentLastAcadYear(String studentNr) throws Exception{
		
		int lastAcadYear = 0;
		
		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		
		try{			
			lastAcadYear=jdt.queryForInt("SELECT MK_LAST_ACADEMIC_Y AS YEAR FROM  STU WHERE NR  = ?", new Object[] {studentNr});
			
		} catch (Exception ex) {
            throw new Exception("MyUnisaPassworddao: getStudentLastAcadYear: (stno: "+studentNr+") Error occurred / "+ ex,ex);
		}
		return lastAcadYear;
	}
    public String getStudentCountryCode(String studentNR) throws Exception
    {
    	JdbcTemplate jdt = new JdbcTemplate(getDataSource());
    	String counrtyCode="";
    	try{
    	String getCounrtyCode = "SELECT MK_COUNTRY_CODE FROM  STU WHERE NR = ?";    	
    	counrtyCode = (String)jdt.queryForObject(
    			getCounrtyCode, new Object[] { studentNR }, String.class);
    	if(counrtyCode.equals("")| counrtyCode==null){
    		counrtyCode="";
    	}    	
    	} catch (Exception ex) {
            throw new Exception("MyUnisaPassworddao: getStudentCountryCode: (stno: "+studentNR+") Error occurred / "+ ex,ex);
		}

      return counrtyCode;
    }
    

}
