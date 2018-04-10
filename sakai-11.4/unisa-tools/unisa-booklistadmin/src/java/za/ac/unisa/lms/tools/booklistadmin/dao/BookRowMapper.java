package za.ac.unisa.lms.tools.booklistadmin.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import za.ac.unisa.lms.tools.booklistadmin.module.BookModule;

public class BookRowMapper implements RowMapper {

	public Object mapRow(ResultSet rs,int arg1) throws SQLException {
		BookModule book = new BookModule();
		book.setBookId(rs.getString("bookNR"));
		book.setTxtTitle(rs.getString("TITLE"));
		book.setTxtAuthor(rs.getString("AUTHOR"));
		book.setTxtEdition(rs.getString("EDITION"));
		book.setTxtYear(rs.getString("YEAR_OF_PUBLISH"));
		book.setTxtISBN(rs.getString("ISBN"));
		book.setTxtISBN1(rs.getString("ISBN1"));
		book.setTxtISBN2(rs.getString("ISBN2"));
		book.setTxtISBN3(rs.getString("ISBN3"));
		book.setCourseLang(rs.getString("COURSE_LANGUAGE"));
		book.setTxtPublisher(rs.getString("PUBLISHER"));
		book.setTxtBookNote(rs.getString("NOTES"));
		book.setConfirmStatus(rs.getString("CONFIRM"));
		book.setPublishStatus(rs.getInt("IS_PUBLISHED"));
		book.setAvailableAsEbook(rs.getString("AVAIL_AS_Ebook"));
		book.setEbook_pages(rs.getString("Ebook_PAGES"));
		book.setEbookVolume(rs.getString("Ebook_VOLUME"));
		book.setUrl(rs.getString("URL"));
		book.setMasterCopy(rs.getString("MC_AVAIL"));
		book.setMasterCopyFormat(rs.getString("MC_FORMAT"));
		book.setOtherAuthor(rs.getString("OTHER_AUTHORS"));
		book.setNoteToLibrary(rs.getString("NOTE_TO_LIBRARY"));
		if(rs.getString("ERESERVE_TYPE").equals("J")){
			book.seteReserveType("Journal");
		}else if(rs.getString("ERESERVE_TYPE").equals("L")){
			book.seteReserveType("Law Report");
		}else if(rs.getString("ERESERVE_TYPE").equals("B")){
			book.seteReserveType("book Chapter");
		}else{
			book.seteReserveType("");
		}
		
	
		return book;
	}			
}

