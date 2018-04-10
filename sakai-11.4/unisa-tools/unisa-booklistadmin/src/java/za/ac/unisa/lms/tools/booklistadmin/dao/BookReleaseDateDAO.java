package za.ac.unisa.lms.tools.booklistadmin.dao;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.map.ListOrderedMap;

import za.ac.unisa.lms.tools.booklistadmin.module.BookSubmissionDateModule;

public class BookReleaseDateDAO extends BookDateAdmiDAO{
	public List getCurrSettingOfReleaseDates(String booklist_type){
	      String sql = "SELECT ACADEMIC_YEAR,RELEASE_DATE,ACTIVE_STATUS FROM BLSTURD  WHERE ACTIVE_STATUS='Y' AND BOOK_STATUS='"+booklist_type+"'";
	      List results=new java.util.ArrayList();
	      DatabaseUtil databaseUtil=new  DatabaseUtil();
		  List queryList = databaseUtil.queryForList(sql);
		  Iterator i = queryList .iterator();
		  
	  while (i.hasNext()){	
		  ListOrderedMap data = (ListOrderedMap) i.next();
	      BookSubmissionDateModule submissionDate = new BookSubmissionDateModule();
		  try{
			   submissionDate.setAcademicYear(data.get("ACADEMIC_YEAR").toString());
			   submissionDate.setReleaseDate(data.get("RELEASE_DATE").toString().substring(0,11));
			   submissionDate.setActiveStatus(data.get("ACTIVE_STATUS").toString());
			   submissionDate.setRemove(false);
	  		   results.add(submissionDate);
		  }catch(NullPointerException e ){e.printStackTrace();}
		
	  }			  
return results;
}
	
	
	public void removeAcademicYearInRelDates(String booklisttype,int academicYear){
		     String sqldelcrsbs = "UPDATE BLSTURD SET ACTIVE_STATUS ='N' WHERE ACADEMIC_YEAR ="+academicYear+" AND BOOK_STATUS='"+booklisttype+"'";
		     DatabaseUtil databaseUtil=new  DatabaseUtil();
		     databaseUtil.update(sqldelcrsbs);
	}
	public void updateLogsForReleaseDates(String networkId,int academicYear,String typeofbooklist){
		   String loggedData="Updated the release date for student view for  "+typeofbooklist +" and  for "+academicYear;
		   String query="INSERT INTO BLAAUD(MK_NETWORK_ID, ACADEMIC_YEAR," +
		   		 "BOOK_STATUS,TRANSACTION_TIME,UPDATE_INFO)" +
		   		 " VALUES('"+networkId+"',"+academicYear+",'"+typeofbooklist+"',SYSDATE,'"+loggedData+"')";
		      DatabaseUtil databaseUtil=new  DatabaseUtil();
		     databaseUtil.execute(query);	
	}
	public void updateLogsSaveReleaseDates(String networkId,int academicYear,String typeofbooklist){
		         String loggedData="Add new release date for   "+typeofbooklist +" and  for "+academicYear;
		         String query="INSERT INTO BLAAUD(MK_NETWORK_ID, ACADEMIC_YEAR," +
		   		 "BOOK_STATUS,TRANSACTION_TIME,UPDATE_INFO)" +
		   		 " VALUES('"+networkId+"',"+academicYear+",'"+typeofbooklist+"',SYSDATE,'"+loggedData+"')";
		      DatabaseUtil databaseUtil=new  DatabaseUtil();
		      databaseUtil.execute(query);	
	}
	
	public void updateReleaseDates(String releaseDate,String booklisttype,int selectedYear){
	  	          String query="UPDATE BLSTURD SET RELEASE_DATE =to_date('"+releaseDate+"','mm-dd-yyyy'), ACTIVE_STATUS ='Y'" +
	  	          		" WHERE BOOK_STATUS='"+booklisttype+"' AND ACADEMIC_YEAR ="+selectedYear;
	  	        DatabaseUtil databaseUtil=new  DatabaseUtil();
			     databaseUtil.update(query);
	}
	public void saveReleaseDates(int academicYear,String booklisttype,String releaseDate){
		   String query="INSERT INTO BLSTURD(BOOK_STATUS,ACADEMIC_YEAR,RELEASE_DATE,ACTIVE_STATUS) VALUES ('"+booklisttype+"',"+academicYear+",to_date('"+releaseDate+"','mm-dd-yyyy'),'Y')";
		   DatabaseUtil databaseUtil=new  DatabaseUtil();
		     databaseUtil.execute(query);	
	}
	public boolean checkAcademicYearInRelDates(String booklisttype,int academic_year){
        String  query="Select ACADEMIC_YEAR  FROM BLSTURD WHERE ACADEMIC_YEAR " +
 		            "="+academic_year+" AND BOOK_STATUS='"+booklisttype+"'";
         DatabaseUtil databaseUtil=new  DatabaseUtil();
  
         if(!databaseUtil.querySingleValue(query,"ACADEMIC_YEAR").equals("")){
 	            return true;
         }else{
 	            return false;
         }
}


}
