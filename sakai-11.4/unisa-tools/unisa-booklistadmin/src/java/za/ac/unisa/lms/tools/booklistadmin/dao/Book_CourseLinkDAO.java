package za.ac.unisa.lms.tools.booklistadmin.dao;

import java.sql.Types;

import org.springframework.jdbc.core.PreparedStatementCreatorFactory;

public class Book_CourseLinkDAO {

	
	public PreparedStatementCreatorFactory pstsmtCreatorFactoryCourseLink;
	private static String sqlForInsertingLink="insert into pbcrsn(MK_ACADEMIC_YEAR,MK_STUDY_UNIT_CODE,CRS_NOTES,STATUS,BOOK_STATUS) " +
	    	                              "values(?,?,?,?,?)";
	private static String sqlForUpdateLink="update pbcrsn set STATUS = ? where MK_STUDY_UNIT_CODE = ? and MK_ACADEMIC_YEAR = ? "+
			                           "and book_status= ?";
	public Book_CourseLinkDAO(){
	        pstsmtCreatorFactoryCourseLink = new PreparedStatementCreatorFactory(
		                                  	"insert into CRSBS1 (Coursenr,Booknr,Course_Language,book_status,Year,Confirm)"+
		                                 	"values(?,?,?,?,?,0)",
		                                 	new int[] {Types.VARCHAR,Types.INTEGER,Types.VARCHAR,Types.VARCHAR,Types.INTEGER}
			                              );
	 }
	public void updateCourseBookLink(int status, String course,int  academicYear,String bookListType){
	    int[] types= new int[] {Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR};
	     Object[] params=new Object[]{ status,course,academicYear,bookListType};
	     DatabaseUtil databaseUtil=new  DatabaseUtil();
	    databaseUtil.update(sqlForUpdateLink, types, params);
	}
	public void insertCourseBookLink(int  academicYear, String courseId,String courseNotes,int status,String bookListType){
	int[] types= new int[] {Types.INTEGER,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR};
	Object[] params=new Object[]{ academicYear,courseId,courseNotes,status,bookListType};
	DatabaseUtil databaseUtil=new  DatabaseUtil();
	databaseUtil.update(sqlForInsertingLink, types, params);
	}
}
