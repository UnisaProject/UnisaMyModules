package za.ac.unisa.lms.tools.booklistadmin.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import za.ac.unisa.lms.tools.booklistadmin.module.BookModule;

public class BookSearchRowMapper1 implements RowMapper {
	public Object mapRow(ResultSet rs,int arg1) throws SQLException {
		BookModule book = new BookModule();
		book.setCourse(rs.getString("coursenr"));
		book.setAcademicYear(rs.getString("year"));
		book.setBookId(rs.getString("Booknr"));
		book.setTxtTitle(rs.getString("TITLE"));
		book.setTxtAuthor(rs.getString("AUTHOR")); 
		book.setTxtOtherAuthor(rs.getString("otherAuthor"));
		book.setTxtEdition(rs.getString("EDITION"));
		book.setTxtYear(rs.getString("Year_of_publish"));
		book.setTxtISBN(rs.getString("ISBN"));
		book.setTxtISBN1(rs.getString("ISBN1"));
		book.setTxtISBN2(rs.getString("ISBN2"));
		book.setTxtISBN3(rs.getString("ISBN3"));
		book.setTxtPublisher(rs.getString("publisher"));
		book.setTxtBookNote(rs.getString("notes"));
		book.setPublishStatus(rs.getInt("Is_Published"));
		book.setEbook_pages(rs.getString("EBOOK_PAGES"));    
		book.setEbookVolume(rs.getString("EBOOK_VOLUME")); 
		if(rs.getString("ERESERVE_TYPE").equals("J")){
			book.seteReserveType("Journal");
		}else if(rs.getString("ERESERVE_TYPE").equals("L")){
			book.seteReserveType("Law Report");
		}else if(rs.getString("ERESERVE_TYPE").equals("B")){
			book.seteReserveType("Book Chapter");
		}else{
			book.seteReserveType("");
		}
		
		return book;
	}			

}