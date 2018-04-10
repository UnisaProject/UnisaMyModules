package za.ac.unisa.lms.tools.discussionforums.dao;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class PosterRowMapper implements RowMapper{
	public Object mapRow(ResultSet rs,int arg1) throws SQLException {
		PosterDetails posterDetails = new PosterDetails();
		posterDetails.setInitials(rs.getString("INITIALS"));
		posterDetails.setSurname(rs.getString("SURNAME"));
		return posterDetails;
	}
}

