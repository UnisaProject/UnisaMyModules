package za.ac.unisa.lms.tools.biodetails.dao;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.map.ListOrderedMap;
import org.springframework.jdbc.core.JdbcTemplate;

import za.ac.unisa.lms.db.StudentSystemDAO;

@SuppressWarnings("rawtypes")
public class ContactDetailsDAO extends StudentSystemDAO {

	public String getEmailVerified(String studentNr){
		String result = "";
		String query = "SELECT email_verified from solact where student_nr = " + studentNr;
		result = querySingleValue(query,"EMAIL_VERIFIED");
		
		return result;
	}
	
	public String getActivationCode(String studentNr){
		String result = "";
		String query = "SELECT act_code from solact where student_nr = " + studentNr;
		result = querySingleValue(query,"ACT_CODE");
		
		return result;
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
			result = data.get(field).toString();
		}
		return result;
	}
	
public void writeEmailVerifiedFlag(String studentNr,String value){
			
		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
				
		String sql = "update solact set email_verified='"+value +"' where student_nr="+ studentNr;
		jdt.update(sql);
			
	}

public void writeEmailToVault(String studentNr, String emailAddress){
	
	JdbcTemplate jdt = new JdbcTemplate(getDataSource());
	/* Remove any previous entries */
	
	String sql = "update  idvalt set email_address='"+emailAddress+"' where mk_student_nr="+studentNr;
	jdt.update(sql);
		
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
		JdbcTemplate jdt = new JdbcTemplate(getDataSource());	
		email = (String) jdt.queryForObject(query, String.class);
		
	} catch (Exception ex) {
		throw new Exception("AdditionsQueryDao : Error reading student email / "+ ex,ex);
	}
	
	return email;		
}
public void updateADRPH(String studentNr, String email) throws Exception{
	
	String sql1;		
		sql1 = "UPDATE ADRPH "+
			  " SET EMAIL_ADDRESS = '"+email+"'"+
			  " WHERE FK_ADRCATCODE = 1 "+
			  " AND   REFERENCE_NO = "+studentNr;
			
		try{
	        	JdbcTemplate jdt1 = new JdbcTemplate(getDataSource());
		        jdt1.update(sql1);
	    } catch (Exception ex) {
          		throw new Exception("MyUnisaMylifeQueryDAO: updateADRPH: (stno: "+studentNr+")Error occurred /"+ ex,ex);
	    }
		
} // updateContactDetail

	
}
