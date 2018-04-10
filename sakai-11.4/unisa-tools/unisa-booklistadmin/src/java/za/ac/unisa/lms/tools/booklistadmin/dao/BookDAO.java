package za.ac.unisa.lms.tools.booklistadmin.dao;

import java.sql.Types;
import java.util.Iterator;
import java.util.List;

import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;

import  za.ac.unisa.lms.tools.booklistadmin.module.BookModule;
import za.ac.unisa.lms.tools.booklistadmin.dao.BookSearchRowMapper;

public class BookDAO extends BookSearchListDAO{
	
	public void deleteBook(BookModule bookDetails,String course, String acadyear) throws Exception{
		 DatabaseUtil databaseUtil=new  DatabaseUtil();
		String sql = "delete from CRSBS1 where Coursenr = '"+course+"' and Booknr = "+bookDetails.getBookId()+" and book_status = '"+bookDetails.getTypeOfBookList()+"' and Year = "+acadyear;
		databaseUtil.update(sql);
		
		String sqlcourse = "select mk_study_unit_code from pbcrsn where mk_study_unit_code = '"+course+"' and BOOK_STATUS= '"+bookDetails.getTypeOfBookList()+"' and MK_ACADEMIC_YEAR = "+acadyear;
		String studyunit;
		try{
			studyunit = databaseUtil.querySingleValue(sqlcourse,"mk_study_unit_code");
			} catch (Exception ex) {
		    throw new Exception("CourseDAO : insertBook Error occurred / "+ ex,ex);
		 }
			if(studyunit.length()>0){
				    BookListDAO bookListDAO=new BookListDAO();
				    bookListDAO.updateCourseBookLink(7,course,Integer.parseInt(acadyear),bookDetails.getTypeOfBookList());
			}
	}
	
public BookModule getBook (String bookId) {
	DatabaseUtil databaseUtil=new  DatabaseUtil();
         BookModule book = new BookModule();
         List results = databaseUtil.query("SELECT b.Booknr AS bookNR,Title,Author,Edition,Year_of_publish,publisher,ISBN,ISBN1,ISBN2,ISBN3,Course_Language," +
         		"b.Notes AS NOTES,Confirm,Is_Published,AVAIL_AS_EBOOK,EBOOK_PAGES,EBOOK_VOLUME,URL,MC_AVAIL,MC_FORMAT,OTHER_AUTHORS,NOTE_TO_LIBRARY,nvl(ERESERVE_TYPE,' ')  ERESERVE_TYPE FROM books1 b, crsbs1 c where b.booknr=c.booknr and  b.Booknr = ?",
                         new Object[] { bookId },
                         new int[] { Types.INTEGER },
                         new BookRowMapper());
         Iterator i = results.iterator();
         if (i.hasNext()){
        	 book = (BookModule) i.next();
         }
         return book;
 }
	 
	public Integer insertBook(BookModule bookDetails, String bookType)throws Exception{	
		
		String type = "";
		String yearOfpublisher = "0";
		
		 if(bookType.equalsIgnoreCase("Journal")){
			type = "J";
			yearOfpublisher = bookDetails.getTxtYear();
		}
		else if(bookType.equalsIgnoreCase("LawReport")){
			type = "L";
		}
		else if(bookType.equalsIgnoreCase("BookChapter")){
			type = "B";
			yearOfpublisher = bookDetails.getTxtYear();
		}
		else
		{
			yearOfpublisher = bookDetails.getTxtYear();
			type = "";
		}
		
		Integer bookNr = null;
		
		DatabaseUtil databaseUtil=new  DatabaseUtil();
		
		StringBuilder insertBook = new StringBuilder("insert into BOOKS1 (Booknr,Suppliernr,Title,Author,ISBN,ISBN1,ISBN2,ISBN3,Edition," +
				"Year_Of_Publish,Notes,Publisher,is_published,OTHER_AUTHORS,NOTE_TO_LIBRARY,AVAIL_AS_EBOOK," +
				"EBOOK_VOLUME,EBOOK_PAGES,URL,MC_AVAIL,MC_FORMAT,ERESERVE_TYPE)" );
		insertBook.append(" values(BOOKS1_SEQUENCE_NR.nextval,999999,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
		
		PreparedStatementCreatorFactory pstsmtCreatorFactoryMessage = new PreparedStatementCreatorFactory(insertBook.toString(),
				new int[] {Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR, Types.VARCHAR,Types.VARCHAR,
						   Types.VARCHAR,Types.VARCHAR,Types.VARCHAR, Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR, 
						   Types.VARCHAR, Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR, Types.VARCHAR});

		PreparedStatementCreator psCreatorMessageInsert = pstsmtCreatorFactoryMessage.newPreparedStatementCreator(
				new Object[]{bookDetails.getTxtTitle(),bookDetails.getTxtAuthor(),
			       bookDetails.getTxtISBN(),bookDetails.getTxtISBN1(),bookDetails.getTxtISBN2(),
			       bookDetails.getTxtISBN3(),bookDetails.getTxtEdition(),yearOfpublisher,
			        bookDetails.getTxtBookNote(),bookDetails.getTxtPublisher(),bookDetails.getPublishStatus(),bookDetails.getOtherAuthor(),
			        bookDetails.getNoteToLibrary(),bookDetails.getAvailableAsEbook(),bookDetails.getEbookVolume(),bookDetails.getEbook_pages(),bookDetails.getUrl(),
			        bookDetails.getMasterCopy(),bookDetails.getMasterCopyFormat(),type});

		databaseUtil.update(psCreatorMessageInsert);

		try{
			bookNr = new Integer(getBooknr());					
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		String sqlcrs = "insert into CRSBS1 (Coursenr,Booknr,Course_Language,book_status,Year,Confirm)"+
			"values('"+bookDetails.getCourse()+"',"+bookNr+",'"+bookDetails.getCourseLang()+"',"+"'"+bookDetails.getTypeOfBookList()+"',"+bookDetails.getAcadYear()+",1)";
		databaseUtil.update(sqlcrs);
		
		 String sqlcourse = "select mk_study_unit_code from pbcrsn where mk_study_unit_code = '"+bookDetails.getCourse()+"' and BOOK_STATUS= '"+bookDetails.getTypeOfBookList()+"' and MK_ACADEMIC_YEAR = "+bookDetails.getAcadYear();
			String course;
			try{
				
				course = databaseUtil.querySingleValue(sqlcourse,"mk_study_unit_code");
				
			} catch (Exception ex) {
			    throw new Exception("CourseDAO : insertBook Error occurred / "+ ex,ex);
			}
			BookListDAO bookListDAO=new BookListDAO(); 
			if((course!=null)&&(course.length()>7)){
				    bookListDAO.insertCourseBookLink(bookDetails.getAcadYear(), course,"",0,bookDetails.getTypeOfBookList());
			 }else{
				   bookListDAO.updateCourseBookLink(0,course,bookDetails.getAcadYear(),bookDetails.getTypeOfBookList());
		   }
		   bookDetails = null;
		    return bookNr;
	}
	public String makeJDBCCompatible(String convert) {
		            DatabaseUtil databaseUtil=new  DatabaseUtil();
		            return databaseUtil.makeJDBCCompatible(convert);
    }
	public Integer updateBook(BookModule bookDetails) throws Exception{
		
        DatabaseUtil databaseUtil=new  DatabaseUtil();
		
		String updateBook = "Update Books1 set Booknr=?, Suppliernr=999999 ,Title=?,Author= ?, ISBN=?,ISBN1=?,ISBN2= ?," +
				"ISBN3=?,Edition=?,Year_Of_Publish=?,Notes=?,Publisher=?,is_published=?,OTHER_AUTHORS=?," +
				"NOTE_TO_LIBRARY=?,AVAIL_AS_EBOOK=?,EBOOK_VOLUME=?,EBOOK_PAGES=?,URL=?,MC_AVAIL=?," +
				"MC_FORMAT=? where booknr=?";
				
				
				PreparedStatementCreatorFactory pstsmtCreatorFactoryMessage = new PreparedStatementCreatorFactory(updateBook.toString(),
						new int[] {Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR, Types.VARCHAR,Types.VARCHAR,
								   Types.VARCHAR,Types.VARCHAR,Types.VARCHAR, Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,
								   Types.VARCHAR,Types.VARCHAR,Types.VARCHAR, Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,
								   Types.VARCHAR, Types.VARCHAR,Types.INTEGER});

				PreparedStatementCreator psCreatorMessageUpdateBook = pstsmtCreatorFactoryMessage.newPreparedStatementCreator(
						new Object[]{bookDetails.getBookId(),bookDetails.getTxtTitle(),bookDetails.getTxtAuthor(),
					       bookDetails.getTxtISBN(),bookDetails.getTxtISBN1(),bookDetails.getTxtISBN2(),
					       bookDetails.getTxtISBN3(),bookDetails.getTxtEdition(),bookDetails.getTxtYear(),
					        bookDetails.getTxtBookNote(),bookDetails.getTxtPublisher(),bookDetails.getPublishStatus(),bookDetails.getOtherAuthor(),
					        bookDetails.getNoteToLibrary(),bookDetails.getAvailableAsEbook(),bookDetails.getEbookVolume(),bookDetails.getEbook_pages(),bookDetails.getUrl(),
					        bookDetails.getMasterCopy(),bookDetails.getMasterCopyFormat(),bookDetails.getBookId()});
				
				databaseUtil.update(psCreatorMessageUpdateBook);
				
			return Integer.parseInt(bookDetails.getBookId());
	}		
	
	
	public Integer updatebookforcourses(BookModule bookDetails) throws Exception{
		Integer bookNr = new Integer(bookDetails.getBookId());	
		   DatabaseUtil databaseUtil=new  DatabaseUtil();
			
			String updateBook = "Update Books1 set Booknr=?, Suppliernr=999999 ,Title=?,Author= ?, ISBN=?,ISBN1=?,ISBN2= ?," +
					"ISBN3=?,Edition=?,Year_Of_Publish=?,Notes=?,Publisher=?,is_published=?,OTHER_AUTHORS=?," +
					"NOTE_TO_LIBRARY=?,AVAIL_AS_EBOOK=?,EBOOK_VOLUME=?,EBOOK_PAGES=?,URL=?,MC_AVAIL=?," +
					"MC_FORMAT=? where booknr=?";
					
					
					PreparedStatementCreatorFactory pstsmtCreatorFactoryMessage = new PreparedStatementCreatorFactory(updateBook.toString(),
							new int[] {Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR, Types.VARCHAR,Types.VARCHAR,
									   Types.VARCHAR,Types.VARCHAR,Types.VARCHAR, Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,
									   Types.VARCHAR,Types.VARCHAR,Types.VARCHAR, Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,
									   Types.VARCHAR, Types.VARCHAR,Types.INTEGER});

					PreparedStatementCreator psCreatorMessageUpdateBook = pstsmtCreatorFactoryMessage.newPreparedStatementCreator(
							new Object[]{bookDetails.getBookId(),bookDetails.getTxtTitle(),bookDetails.getTxtAuthor(),
						       bookDetails.getTxtISBN(),bookDetails.getTxtISBN1(),bookDetails.getTxtISBN2(),
						       bookDetails.getTxtISBN3(),bookDetails.getTxtEdition(),bookDetails.getTxtYear(),
						        bookDetails.getTxtBookNote(),bookDetails.getTxtPublisher(),bookDetails.getPublishStatus(),bookDetails.getOtherAuthor(),
						        bookDetails.getNoteToLibrary(),bookDetails.getAvailableAsEbook(),bookDetails.getEbookVolume(),bookDetails.getEbook_pages(),bookDetails.getUrl(),
						        bookDetails.getMasterCopy(),bookDetails.getMasterCopyFormat(),bookDetails.getBookId()});
					
					databaseUtil.update(psCreatorMessageUpdateBook);
		
		return bookNr;
	}
	
	public String getBooknr () throws Exception {
		
		String sql = "SELECT max(Booknr) BOOKNR from books1";
		String booknr = "";
		try{
			DatabaseUtil databaseUtil=new DatabaseUtil();
			booknr = databaseUtil.querySingleValue(sql,"BOOKNR");
		} catch (Exception ex) {
            throw new Exception("CourseDAO : getCourseList Error occurred / "+ ex,ex);
		}
		return booknr;
	}
	
}
