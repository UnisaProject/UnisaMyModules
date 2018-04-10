package za.ac.unisa.lms.tools.prescribedbooks.dao;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
public class PrebooksRowMapper implements RowMapper{
	public Object mapRow(ResultSet rs,int arg1) throws SQLException {
		PrebooksDetails prebooksDetails = new PrebooksDetails();
		prebooksDetails.setTitle(rs.getString("Title"));
		prebooksDetails.setAuthor(rs.getString("Author"));
		prebooksDetails.setEdition(rs.getString("Edition"));
		prebooksDetails.setPubYear(rs.getString("Pyear"));
		prebooksDetails.setPublisher(rs.getString("Sname"));
		prebooksDetails.setBookNotes(rs.getString("Bnotes"));
		prebooksDetails.setCourseNotes(rs.getString("Cnotes"));
		return prebooksDetails;
	}
}
