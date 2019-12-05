package za.ac.unisa.lms.dao;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.map.ListOrderedMap;
import org.springframework.jdbc.core.JdbcTemplate;

import za.ac.unisa.lms.db.StudentSystemDAO;

public class EmailAddressVerificationDAO extends StudentSystemDAO {


	public String getActivationCode(String studentNr){
		String result = "";
		String query = "SELECT act_code from solact where student_nr = " + studentNr;
		result = querySingleValue(query,"ACT_CODE");

		return result;
	}

	public boolean isEmailVerified(String studentNr){
		String result = "";
		String query = "SELECT EMAIL_VERIFIED from solact where student_nr = " + studentNr;
		result = querySingleValue(query,"EMAIL_VERIFIED");
		if("Y".equals(result)) {
			return true;
		} else {
			return false;
		}
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

	String sql1 = "delete from idvalt where mk_student_nr="+studentNr;
	jdt.update(sql1);

	String sql = "insert into idvalt (mk_student_nr, email_address) values (?, ?)";
	jdt.update(sql,new Object[] {studentNr,emailAddress});

}

}
