package za.ac.unisa.lms.tools.prescribedbooks.dao;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
public class recommendedBooksRowMapper implements RowMapper{
	public Object mapRow(ResultSet rs,int arg1) throws SQLException {
		   recommendedBooksDetails rbooksDetails = new recommendedBooksDetails();
		  rbooksDetails.setTitle(rs.getString("Title"));
		  rbooksDetails.setAuthor(rs.getString("Author"));
		  rbooksDetails.setPubYear(rs.getString("Pyear"));
		  rbooksDetails.setEdition(rs.getString("Edition"));
		  rbooksDetails.setPublisher(rs.getString("Sname"));
		  return rbooksDetails;
	}


}

