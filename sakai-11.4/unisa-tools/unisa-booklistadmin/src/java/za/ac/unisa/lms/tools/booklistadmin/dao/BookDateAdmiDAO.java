package za.ac.unisa.lms.tools.booklistadmin.dao;

public class BookDateAdmiDAO {

		public boolean checkAcademicYear(String booklisttype,int academic_year,int academicLevel){
	  String  query="Select ACADEMIC_YEAR  FROM BLDATES WHERE ACADEMIC_YEAR " +
	  		"="+academic_year+" AND BOOK_STATUS='"+booklisttype+"' AND STUDY_LEVEL="+academicLevel;
	  DatabaseUtil databaseUtil=new  DatabaseUtil();
	  if(!databaseUtil.querySingleValue(query,"ACADEMIC_YEAR").equals("")){
	  	  return true;
	  }else{
	  	return false;
	  }
	}
	public void deleteAcademicYear(String booklisttype,int academic_year,int academicLevel){
	      String  query="delete  from  BLDATES WHERE ACADEMIC_YEAR " +
	  		"="+academic_year+" AND BOOK_STATUS = '"+booklisttype+"' AND STUDY_LEVEL="+academicLevel;
	      DatabaseUtil databaseUtil=new  DatabaseUtil();
		     databaseUtil.update(query);
	}
	public void removedates(String bookListType,int academicyear,int level){
	    String query="UPDATE BLDATES SET ACTIVE_STATUS = 'N' WHERE BOOK_STATUS= '"+bookListType+"' AND ACADEMIC_YEAR="+academicyear+" AND STUDY_LEVEL="+level;
	    DatabaseUtil databaseUtil=new  DatabaseUtil();
	    databaseUtil.update(query);
	}
	

}
