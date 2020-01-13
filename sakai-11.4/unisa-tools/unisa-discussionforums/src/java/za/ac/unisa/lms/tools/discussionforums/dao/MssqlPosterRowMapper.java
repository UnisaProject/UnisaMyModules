package za.ac.unisa.lms.tools.discussionforums.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class MssqlPosterRowMapper implements RowMapper{
	public Object mapRow(ResultSet rs,int arg1) throws SQLException {
		PosterDetails posterDetails = new PosterDetails();
		if (rs.getString("p_vrltrs")== null || rs.getString("p_vrltrs").equalsIgnoreCase("")){
			posterDetails.setInitials("Not");
			posterDetails.setSurname("available");
		} else {
			posterDetails.setInitials(rs.getString("p_vrltrs"));
			posterDetails.setSurname(rs.getString("p_van"));
		}
		return posterDetails;
	}
}
