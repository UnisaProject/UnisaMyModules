package za.ac.unisa.lms.tools.pbooks.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class StandInSchMapper implements RowMapper{
	public Object mapRow(ResultSet rs,int arg1) throws SQLException {
		CollegeDeptDetails collegeDeptDetails = new CollegeDeptDetails();
		collegeDeptDetails.setStandInName(rs.getString("initials"));
		collegeDeptDetails.setStandInSurname(rs.getString("surname"));
		collegeDeptDetails.setStandInNovellcode(rs.getString("novell_user_id"));
		return collegeDeptDetails;
		}

}
