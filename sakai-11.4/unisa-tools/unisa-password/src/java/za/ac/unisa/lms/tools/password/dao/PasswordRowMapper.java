package za.ac.unisa.lms.tools.password.dao;

import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class PasswordRowMapper implements RowMapper {

	public Object mapRow(ResultSet rs,int arg1) throws SQLException {
		
		PasswordDetails passwordDetails = new PasswordDetails();	
		String requestDate="";
		String changePwd="";
		
	try {

		requestDate = rs.getString("request_date");
		if (requestDate.equals("") || requestDate == null || requestDate.equals(" ")) {
			requestDate = "";
		}
		passwordDetails.setRequestDate(requestDate);
		
		changePwd = rs.getString("change_pwd");
		if (changePwd.equals("") || changePwd == null || changePwd.equals(" ")) {
			changePwd = "";
		}
		passwordDetails.setChangePwd(changePwd);

		
	} catch (SQLException ex) {
		throw new SQLException ("PasswordRowMapper: mapRow: Error occurred / " + ex, ex);
	}
		return passwordDetails;
	}			
}
