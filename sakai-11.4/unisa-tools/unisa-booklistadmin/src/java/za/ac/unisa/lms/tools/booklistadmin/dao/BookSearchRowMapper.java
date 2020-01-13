package za.ac.unisa.lms.tools.booklistadmin.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import za.ac.unisa.lms.tools.booklistadmin.module.BookModule;

public class BookSearchRowMapper implements RowMapper {
	public Object mapRow(ResultSet rs,int arg1) throws SQLException {
		BookModule bookDetails = new BookModule();
		bookDetails.setBookId(rs.getString("BOOKNR"));
		bookDetails.setTxtTitle(rs.getString("TITLE"));
		bookDetails.setTxtAuthor(rs.getString("AUTHOR"));
		bookDetails.setTxtEdition(rs.getString("EDITION"));
		bookDetails.setTxtYear(rs.getString("YEAR_OF_PUBLISH"));
		bookDetails.setTxtISBN(rs.getString("ISBN"));
		bookDetails.setTxtISBN(rs.getString("ISBN1"));
		bookDetails.setTxtISBN(rs.getString("ISBN2"));
		bookDetails.setTxtISBN(rs.getString("ISBN3"));
		bookDetails.setTxtPublisher(rs.getString("PUBLISHER"));
		bookDetails.setTxtBookNote(rs.getString("NOTES"));
		bookDetails.setPublishStatus(rs.getInt("IS_PUBLISHED"));
		
		if(rs.getString("ERESERVE_TYPE") != null && rs.getString("ERESERVE_TYPE").equals("J")){
			bookDetails.seteReserveType("Journal");
		}else if(rs.getString("ERESERVE_TYPE") != null && rs.getString("ERESERVE_TYPE").equals("L")){
			bookDetails.seteReserveType("Law Report");
		}else if(rs.getString("ERESERVE_TYPE") != null && rs.getString("ERESERVE_TYPE").equals("B")){
			bookDetails.seteReserveType("Book Chapter");
		}else{
			bookDetails.seteReserveType("");
		}
		return bookDetails;
	}			

}
