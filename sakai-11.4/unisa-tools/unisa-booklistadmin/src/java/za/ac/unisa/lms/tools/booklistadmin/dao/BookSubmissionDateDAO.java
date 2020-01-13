package za.ac.unisa.lms.tools.booklistadmin.dao;

import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;

import org.apache.commons.collections.map.ListOrderedMap;
import org.springframework.jdbc.core.JdbcTemplate;

import za.ac.unisa.lms.tools.booklistadmin.module.BookSubmissionDateModule;
public class BookSubmissionDateDAO  extends BookDateAdmiDAO{
	public List getCurrSettingOfSubmissionDates(String booklist_type){
		        try{
	                   String sql = "SELECT ACADEMIC_YEAR,STUDY_LEVEL, START_DATE,END_DATE,ACTIVE_STATUS FROM BLDATES WHERE "+
		                            "ACTIVE_STATUS='Y' AND BOOK_STATUS='"+booklist_type+"'";
	                   List results=new java.util.ArrayList();
	                   DatabaseUtil databaseUtil =new DatabaseUtil(); 
		               List queryList = databaseUtil.queryForList(sql);
		               Iterator i = queryList .iterator();
		  	           while (i.hasNext()){	
		                      ListOrderedMap data = (ListOrderedMap) i.next();
	                          BookSubmissionDateModule submissionDate = new BookSubmissionDateModule();
		       			      submissionDate.setAcademicYear(data.get("ACADEMIC_YEAR").toString());
			                  submissionDate.setLevel(data.get("STUDY_LEVEL").toString());
			                  submissionDate.setOpeningDate(data.get("START_DATE").toString().substring(0,11));
			                  submissionDate.setClosingDate(data.get("END_DATE").toString().substring(0,11));
			                  submissionDate.setActiveStatus(data.get("ACTIVE_STATUS").toString());
			                  submissionDate.setRemove(false);
	  		                  results.add(submissionDate);
		  	           }
		  	            return results;
		             }catch(Exception e ){
		                 	 e.printStackTrace();
		            }
		        return new ArrayList();
	}
	    

public void updateSubmissionDates(String fromDate,String closingDate,String booklisttype,int selectedYear,int academiclevel){
	          String query="UPDATE BLDATES SET START_DATE=to_date('"+fromDate+"','mm-dd-yyyy')," +
	          		"END_DATE = to_date('"+closingDate+"','mm-dd-yyyy') WHERE BOOK_STATUS='"+booklisttype+"' AND ACADEMIC_YEAR ="+selectedYear+" and STUDY_LEVEL="+academiclevel;
	          DatabaseUtil databaseUtil=new  DatabaseUtil();
			  databaseUtil.update(query);
}
public void saveSubmissionDates(String networkId,int academicYear,int academicLevel,String booklisttype,String fromDate,String closingDate){
	   String query="INSERT INTO BLDATES(BOOK_STATUS,ACADEMIC_YEAR, STUDY_LEVEL,START_DATE, END_DATE, ACTIVE_STATUS) VALUES ('"+booklisttype+"',"+academicYear+","+academicLevel+",to_date('"+fromDate+"','mm-dd-yyyy'),to_date('"+closingDate+"','mm-dd-yyyy'),'Y')";
	   DatabaseUtil databaseUtil=new  DatabaseUtil();
	     databaseUtil.execute(query);	
}
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
public void updateLogsForUpdatingLogs(String networkId,int academicYear,int academicLevel,String typeofbooklist){
	   String loggedData="Updated opening and closing dates for  "+typeofbooklist +" and  for "+academicYear;
	   String query="INSERT INTO BLAAUD(MK_NETWORK_ID, ACADEMIC_YEAR,STUDY_LEVEL," +
	   		 "BOOK_STATUS,TRANSACTION_TIME,UPDATE_INFO)" +
	   		 " VALUES('"+networkId+"',"+academicYear+","+academicLevel+",'"+typeofbooklist+"',SYSDATE,'"+loggedData+"')";
	   DatabaseUtil databaseUtil=new  DatabaseUtil();
	   databaseUtil.execute(query);	
}
public void updateLogsForSavingDates(String networkId,int academicYear,int academicLevel,String typeofbooklist){
	   String loggedData="Add new opening and closing dates for   "+typeofbooklist +" and  for "+academicYear;
	   String query="INSERT INTO BLAAUD(MK_NETWORK_ID, ACADEMIC_YEAR,STUDY_LEVEL," +
	   		 "BOOK_STATUS,TRANSACTION_TIME,UPDATE_INFO)" +
	   		 " VALUES('"+networkId+"',"+academicYear+","+academicLevel+",'"+typeofbooklist+"',SYSDATE,'"+loggedData+"')";
	   DatabaseUtil databaseUtil=new  DatabaseUtil();
	   databaseUtil.execute(query);	
}




}
