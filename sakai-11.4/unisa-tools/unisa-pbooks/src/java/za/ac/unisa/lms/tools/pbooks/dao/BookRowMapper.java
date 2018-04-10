package za.ac.unisa.lms.tools.pbooks.dao;
	import java.sql.ResultSet;
	import java.sql.SQLException;

	import org.springframework.jdbc.core.RowMapper;

	public class BookRowMapper implements RowMapper {

		public Object mapRow(ResultSet rs,int arg1) throws SQLException {
			BookDetails bookDetails = new BookDetails();
			
			//bookDetails.setBookId(rs.getString("coursenr"));
			bookDetails.setBookId(rs.getString("BOOKNR"));
			bookDetails.setTxtTitle(rs.getString("TITLE"));
			bookDetails.setTxtAuthor(rs.getString("AUTHOR"));
			bookDetails.setTxtEdition(rs.getString("EDITION"));
			bookDetails.setTxtYear(rs.getString("YEAR_OF_PUBLISH"));
			bookDetails.setTxtISBN(rs.getString("ISBN"));
			bookDetails.setTxtISBN1(rs.getString("ISBN1"));
			bookDetails.setTxtISBN2(rs.getString("ISBN2"));
			bookDetails.setTxtISBN3(rs.getString("ISBN3"));
			bookDetails.setCourseLang(rs.getString("COURSE_LANGUAGE"));
			bookDetails.setTxtPublisher(rs.getString("PUBLISHER"));
			bookDetails.setTxtBookNote(rs.getString("NOTES"));
			bookDetails.setConfirmStatus(rs.getString("CONFIRM"));
			bookDetails.setPublishStatus(rs.getInt("IS_PUBLISHED"));
			bookDetails.setEditOption(rs.getString("CONFIRM"));
			bookDetails.setAvailableAsEbook(rs.getString("AVAIL_AS_EBOOK"));
			bookDetails.setEbook_pages(rs.getString("EBOOK_PAGES"));
			bookDetails.setEbookVolume(rs.getString("EBOOK_VOLUME"));
			bookDetails.setUrl(rs.getString("URL"));
			bookDetails.setMasterCopy(rs.getString("MC_AVAIL"));
			bookDetails.setMasterCopyFormat(rs.getString("MC_FORMAT"));
			bookDetails.setOtherAuthor(rs.getString("OTHER_AUTHORS"));
			bookDetails.setNoteToLibrary(rs.getString("NOTE_TO_LIBRARY"));
			if(rs.getString("ERESERVE_TYPE").equals("J")){
				bookDetails.seteReserveType("Journal");
			}else if(rs.getString("ERESERVE_TYPE").equals("L")){
				bookDetails.seteReserveType("Law Report");
			}else if(rs.getString("ERESERVE_TYPE").equals("B")){
				bookDetails.seteReserveType("Book Chapter");
			}else{
				bookDetails.seteReserveType("");
			}
			
			return bookDetails;
		}			
}
