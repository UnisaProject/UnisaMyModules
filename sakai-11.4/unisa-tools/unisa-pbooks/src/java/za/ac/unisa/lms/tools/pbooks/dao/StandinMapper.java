package za.ac.unisa.lms.tools.pbooks.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class StandinMapper implements RowMapper  {

	public Object mapRow(ResultSet rs,int arg1) throws SQLException {
		CollegeDeptDetails collegeDeptDetails = new CollegeDeptDetails();
		collegeDeptDetails.setStandInName(rs.getString("INITIALS"));
		collegeDeptDetails.setStandInSurname(rs.getString("SURNAME"));
		collegeDeptDetails.setStandInNovellcode(rs.getString("NOVELL_USER_CODE"));
		return collegeDeptDetails;
}
}
