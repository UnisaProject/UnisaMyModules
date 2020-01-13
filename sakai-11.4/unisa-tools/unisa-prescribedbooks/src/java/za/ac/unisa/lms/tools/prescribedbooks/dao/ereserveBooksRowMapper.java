package za.ac.unisa.lms.tools.prescribedbooks.dao;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
public class ereserveBooksRowMapper implements RowMapper{
	public Object mapRow(ResultSet rs,int arg1) throws SQLException {
		  ereserveBooksDetails rbooksDetails = new ereserveBooksDetails();
		  rbooksDetails.setTitle(rs.getString("Title"));
		  rbooksDetails.setAuthor(rs.getString("Author"));
		  rbooksDetails.setPublisher(rs.getString("Sname"));
		  rbooksDetails.setPubYear(rs.getString("Pyear"));
		  rbooksDetails.setVolume(rs.getString("Volume"));
		  rbooksDetails.setUrl(rs.getString("URL"));
		  return rbooksDetails;
	}
}

