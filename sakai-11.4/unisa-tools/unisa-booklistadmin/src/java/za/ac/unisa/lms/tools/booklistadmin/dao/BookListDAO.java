package za.ac.unisa.lms.tools.booklistadmin.dao;

import java.sql.Types;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.collections.map.ListOrderedMap;
import za.ac.unisa.lms.tools.booklistadmin.module.BookModule;
public class BookListDAO extends BookListStatusDAO {
	
	
	public List getSearchedBookList (String title, String author,Integer academicYear, String subject, String typeOfbooklist,String  eReserveType) throws Exception {
		                BookSearchListDAO bookSearchListDAO=new BookSearchListDAO();
                        return bookSearchListDAO.getSearchedBookList(title, author, academicYear, subject, typeOfbooklist,eReserveType);
	}
	public List getBookList (String subject, String acadyear, String typeOfbook) throws Exception {
			DatabaseUtil databaseUtil=new  DatabaseUtil();
		List results = databaseUtil.query("SELECT b.Booknr Booknr,nvl(ERESERVE_TYPE,' ') ERESERVE_TYPE,Title,Author,Edition,Year_of_publish,publisher,ISBN,ISBN1,ISBN2,ISBN3,Course_Language,b.notes notes,Confirm,Is_Published,AVAIL_AS_EBOOK,EBOOK_PAGES," +
				"EBOOK_VOLUME,URL,MC_AVAIL,MC_FORMAT,OTHER_AUTHORS,NOTE_TO_LIBRARY FROM books1 b, crsbs1 c "+
					"where b.booknr=c.booknr and  c.CourseNr = ? and c.year= "+acadyear+" and Book_status='"+typeOfbook+"' ORDER BY Upper(Title), Upper(Author), Edition asc",
					new Object[] { subject }, new int[] { Types.VARCHAR },
				new BookRowMapper());			


		return results;
	}

	public List getBookList(String code, String acadyear, String typeOfbook,String status) throws Exception {
				DatabaseUtil databaseUtil=new  DatabaseUtil();
		ArrayList results = new ArrayList();
		List queryList;
		String sql  ="SELECT c.COURSENR coursenr,b.booknr booknr ,nvl(Title,' ') Title,nvl(Author,' ')Author, "+
	         "DECODE (edition, null, ' ', edition) edition, "+
	        "DECODE (Year_of_publish, null, ' ', Year_of_publish)  Year_of_publish , "+ 
	       "DECODE (isbn, null, ' ', isbn)  isbn,nvl(b.notes,' ') notes,nvl(ERESERVE_TYPE,' ') ereserve_type " +
		  "FROM books1 b, crsbs1 c , dpt d, pbcrsn p,sun s "+ 
		  "where b.booknr=c.booknr and d.code= "+code+" and c.year= "+acadyear+" and p.Book_status='"+typeOfbook+"' and p.STATUS="+status+" and "+
		  "p.mk_academic_year=c.year and s.code=p.mk_study_unit_code and p.mk_study_unit_code=c.coursenr  "+
		  "ORDER BY Upper(Title),Upper(Author), Edition asc ";
			 queryList = databaseUtil.queryForList(sql);
		  Iterator i = queryList .iterator();
		  String prevCourse = "";
		  
			
	  while (i.hasNext())
	  {
		  ListOrderedMap data = (ListOrderedMap) i.next();
		  BookModule pbooklistDetails = new BookModule();
			
		try{
			pbooklistDetails.seteReserveType(data.get("ereserve_type").toString());
			pbooklistDetails.setCourse(data.get("coursenr").toString());
			pbooklistDetails.setTxtTitle(data.get("Title").toString());
			pbooklistDetails.setTxtAuthor(data.get("Author").toString());
			pbooklistDetails.setTxtEdition(data.get("edition").toString());
			pbooklistDetails.setTxtYear(data.get("Year_of_publish").toString());			
			pbooklistDetails.setTxtBookNote(data.get("notes").toString());
			pbooklistDetails.setBookId(data.get("booknr").toString());
		    results.add(pbooklistDetails);
			}
	    catch(NullPointerException e ){e.printStackTrace();}		
	  }			  
	return results;
	}
	public ArrayList getBooklistExport(String year,String typeofbooklist, String listYear) throws Exception {

		ArrayList list = new ArrayList();
		DatabaseUtil databaseUtil=new  DatabaseUtil();
		
		String select ="SELECT DISTINCT c.coursenr AS course,  nvl(b.BIBLIOGRAPHICNR,' ') bibnumber, nvl(b.title,' ') title,"+
                       "  nvl(b.author,' ') firstauthor,nvl(b.booknr,'') booknr, nvl(b.other_authors,' ') otherauthors,"+
                       " nvl(b.year_of_publish,' ')publishedyear, nvl(b.edition,' ') edition,"+
                       " nvl(b.isbn,' ') isbn, nvl(b.publisher,' ') publisher,nvl(b.IS_PUBLISHED,'') is_published ," +
                       " nvl(b.notes,' ') booknotes, nvl(pc.crs_notes,' ') coursenotes,nvl(b.NOTE_TO_LIBRARY,' ') NOTE_TO_LIBRARY," +
                       " nvl(c.COURSE_LANGUAGE,' ') COURSE_LANGUAGE , nvl(b.AVAIL_AS_EBOOK,' ') ebook,nvl(b.ERESERVE_TYPE,' ') ereserve_type,"+
                       " nvl(decode(s.fk_suntypcode,12,'Semester',15,'Both','Year'),' ') yearsemester, " +
                       "nvl(b.EBOOK_VOLUME,' ') volume, nvl(b.EBOOK_PAGES,' ') pages, " +
                       "nvl(b.URL,' ') url,nvl(b.MC_AVAIL,' ') mastercopy,nvl(b.MC_FORMAT,' ') masterformat," +
                       "nvl((select studentcount from blsubc where year= "+listYear+" " +
                       	"and BLSUBC.SUBJECT=c.coursenr and language0='E' and SEMESTER=1),'') engSem1Students," +
                       "nvl((select studentcount from blsubc where year= "+listYear+" " +
                       	"and BLSUBC.SUBJECT=c.coursenr and language0='E' and SEMESTER=2),'') engSem2Students," +
                       "nvl((select studentcount from blsubc where year= "+listYear+" " +
                       	"and BLSUBC.SUBJECT=c.coursenr and language0='E' and SEMESTER=0),'') engYearStudents," +
                       "nvl((select studentcount from blsubc where year="+listYear+
                       " and BLSUBC.SUBJECT=c.coursenr and language0='A' and SEMESTER=1),'') afrSem1Students," +
                       "nvl((select studentcount from blsubc where year="+listYear+
                       " and BLSUBC.SUBJECT=c.coursenr and language0='A' and SEMESTER=2),'') afrSem2Students," +
                       "nvl((select studentcount from blsubc where year="+listYear+
                       " and BLSUBC.SUBJECT=c.coursenr and language0='A' and SEMESTER=0),'') afrYearStudents" +
                       " FROM crsbs1 c, books1 b, pbcrsn pc, sun s WHERE c.YEAR = "+year+
                       " AND pc.book_status = '"+typeofbooklist+"' and pc.book_status = c.BOOK_STATUS AND c.booknr = b.booknr " +
                       "AND c.coursenr = pc.mk_study_unit_code AND c.coursenr = s.code " +
                       "AND pc.mk_study_unit_code = s.code AND c.YEAR = pc.mk_academic_year AND pc.status >= 5 " +
                       "ORDER BY c.coursenr";
		List queryList = databaseUtil.queryForList(select);
		Iterator i = queryList.iterator();
		while(i.hasNext()) {
			ListOrderedMap data = (ListOrderedMap) i.next();
			 BookModule bookDetail = new BookModule();
			 bookDetail.seteReserveType(data.get("ereserve_Type").toString());
			 bookDetail.setCourse(data.get("course").toString());
			 bookDetail.setBibliographicnr(data.get("bibnumber").toString());
			 bookDetail.setBookId(data.get("booknr").toString());
			 bookDetail.setTxtTitle(data.get("title").toString());
			 bookDetail.setTxtAuthor(data.get("firstauthor").toString());
			 bookDetail.setTxtOtherAuthor(data.get("otherauthors").toString());
			 bookDetail.setTxtYear(data.get("publishedyear").toString());
			 bookDetail.setTxtEdition(data.get("edition").toString());
			 bookDetail.setTxtISBN(data.get("isbn").toString());
			 bookDetail.setTxtPublisher(data.get("publisher").toString());
			 if(data.get("is_published")==null){
				 bookDetail.setIsPublished("");
			 }else{
			 bookDetail.setIsPublished(data.get("is_published").toString());}
			 bookDetail.setTxtBookNote(data.get("booknotes").toString());
			 bookDetail.setCourseNote(data.get("coursenotes").toString());
			 bookDetail.setNoteToLibrary(data.get("NOTE_TO_LIBRARY").toString());
			 bookDetail.setCourseLang(data.get("COURSE_LANGUAGE").toString());
			 bookDetail.setAvailableAsEbook(data.get("ebook").toString());				 
			 bookDetail.setAcademicStatus(data.get("yearsemester").toString());
			 bookDetail.setEbookVolume(data.get("volume").toString());
			 bookDetail.setEbook_pages(data.get("pages").toString());
			 bookDetail.setUrl(data.get("url").toString());
			 bookDetail.setMasterCopy(data.get("mastercopy").toString());
			 bookDetail.setMasterCopyFormat(data.get("masterformat").toString());
			 if(data.get("engSem1Students")==null){
				 bookDetail.setEngSem1Count(""); 
			 }else{
				 bookDetail.setEngSem1Count(data.get("engSem1Students").toString());
			 }
			 if(data.get("engSem2Students")==null){
				 bookDetail.setEngSem2Count(""); 
			 }else{
				 bookDetail.setEngSem2Count(data.get("engSem2Students").toString());
			 }
			 if(data.get("engYearStudents")==null){
				 bookDetail.setEngYearCount(""); 
			 }else{
				 bookDetail.setEngYearCount(data.get("engYearStudents").toString());
			 }
			 if(data.get("afrSem1Students")==null){
				 bookDetail.setAfrSem1Count(""); 
			 }else{
				 bookDetail.setAfrSem1Count(data.get("afrSem1Students").toString());
			 }
			 if(data.get("setAfrSem2Students")==null){
				 bookDetail.setAfrSem2Count(""); 
			 }else{
				 bookDetail.setAfrSem2Count(data.get("setAfrSem2Students").toString());
			 }
			 if(data.get("afrYearStudents")==null){
				 bookDetail.setAfrYearCount(""); 
			 }else{
				 bookDetail.setAfrYearCount(data.get("afrYearStudents").toString());
			 }
			list.add(bookDetail);
		}
		
		return list;
 }
	public List getBookCopyList (String subject, String acadyear,String typeOfbooklist) throws Exception {
		int tmpYear = Integer.parseInt(acadyear);
		int acadyearLess1= Integer.parseInt(acadyear)-1; 
		DatabaseUtil databaseUtil=new  DatabaseUtil();
			List results = databaseUtil.query("SELECT distinct b.Booknr Booknr,Title,Author,Edition,Year_of_publish,publisher,ISBN,ISBN1,ISBN2,ISBN3,Course_Language,IS_PUBLISHED," +
					"CONFIRM,b.Notes Notes,AVAIL_AS_EBOOK,EBOOK_PAGES,EBOOK_VOLUME,URL,MC_AVAIL,MC_FORMAT,OTHER_AUTHORS,NOTE_TO_LIBRARY,nvl(ERESERVE_TYPE,' ') ERESERVE_TYPE FROM books1 b, crsbs1 c where b.booknr=c.booknr and  c.CourseNr = ? and c.year= ? and c.book_status= ? "+
					" and c.BOOKNR NOT IN (SELECT b.Booknr FROM books1 b, crsbs1 c where b.booknr=c.booknr and  c.CourseNr = ? and c.year= ? and c.book_status= ? ) " +
					"ORDER BY Upper(Title), Upper(Author), Edition asc",
				new Object[] { subject,acadyearLess1,typeOfbooklist, subject, tmpYear,typeOfbooklist }, new int[] { Types.VARCHAR, Types.INTEGER, Types.VARCHAR, Types.VARCHAR, Types.INTEGER, Types.VARCHAR},
				new BookRowMapper());
		return results;
	}
}
