package za.ac.unisa.lms.tools.booklistadmin.dao;

import java.sql.Types;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;

import za.ac.unisa.lms.db.StudentSystemDAO;
import za.ac.unisa.lms.tools.booklistadmin.module.BookModule;

public class BookListStatusDAO extends Book_CourseLinkDAO{
private PreparedStatementCreatorFactory pstsmtCreatorFactoryCourseLink;
public BookListStatusDAO(){
        pstsmtCreatorFactoryCourseLink = new PreparedStatementCreatorFactory(
	                                  	"insert into CRSBS1 (Coursenr,Booknr,Course_Language,book_status,Year,Confirm)"+
	                                 	"values(?,?,?,?,?,0)",
	                                 	new int[] {Types.VARCHAR,Types.INTEGER,Types.VARCHAR,Types.VARCHAR,Types.INTEGER}
		                              );
 }
	public String getStatus(String subject,String acadyear, String typeofBooklist) throws Exception {
        DatabaseUtil databaseUtil=new  DatabaseUtil();
String status;
   String sql = "SELECT status FROM pbcrsn where Mk_study_unit_code = '"+subject+"' and mk_academic_year= "+acadyear+" and BOOK_STATUS = '"+typeofBooklist+"' ";
   String confirmStatus = databaseUtil.querySingleValue(sql,"STATUS");
if (confirmStatus.length()> 0) {
status=confirmStatus;
}else{
status="";
}				
return status;
}
public void updateBookListStatus(String confirm,String course, String acadyear, String networkID, String typeOfbooklst)throws Exception{

DatabaseUtil databaseUtil=new  DatabaseUtil();	

String sqlcourse = "select mk_study_unit_code from pbcrsn where mk_study_unit_code = '"+course+"' and MK_ACADEMIC_YEAR = '"+acadyear+"' and BOOK_STATUS= '"+typeOfbooklst+"'";
String selectedCourse;
try{
selectedCourse = databaseUtil.querySingleValue(sqlcourse,"mk_study_unit_code");

} catch (Exception ex) {
throw new Exception("CourseDAO : updateBookListStatus Error occurred / "+ ex,ex);
}
if(selectedCourse.length()==0){		 
insertCourseBookLink(Integer.parseInt(acadyear),course,"",3,typeOfbooklst);
			
}else{
 updateCourseBookLink(Integer.parseInt(confirm),course,Integer.parseInt(acadyear),typeOfbooklst);
}

}


	

}
