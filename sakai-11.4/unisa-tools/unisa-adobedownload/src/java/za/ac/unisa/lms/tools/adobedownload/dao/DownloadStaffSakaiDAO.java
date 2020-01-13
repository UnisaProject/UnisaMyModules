package za.ac.unisa.lms.tools.adobedownload.dao;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.map.ListOrderedMap;
import org.springframework.jdbc.core.JdbcTemplate;

import za.ac.unisa.lms.db.SakaiDAO;


public class DownloadStaffSakaiDAO extends SakaiDAO{
	
	public DownloadStaffSakaiDAO(){}
	//check downloads against networkcode(userId)
	public boolean checkDownloads(String userId ) throws Exception {
		boolean downloaded = false;
		String noOfRecords;

		String sql1 = " SELECT COUNT(*) as NoOfRecords "+
					  " FROM  ADOBEDL " +
					  " WHERE  NOVELL_USER_ID = '"+userId+"'";
					  
							
		try{
			noOfRecords = this.querySingleValue(sql1,"NoOfRecords");
		} catch (Exception ex) {
            throw new Exception("DownloadStaffStudentDAO: courseExist: Error occurred / "+ ex,ex);
		}

		if (Integer.parseInt(noOfRecords) > 0) {
			downloaded = true;
		} else {
			downloaded = false;
		}

		return downloaded;
		
	}
	//Get occurence
	public Integer getDownloadOcc(String userId ) throws Exception {
		
		String noOfRecords;

		
		String sql1 = " SELECT COUNT(*) "+
					  " FROM  ADOBEDL " +
					  " WHERE  NOVELL_USER_ID = '"+userId+"'";
					  
					
		try{
			noOfRecords = this.querySingleValue(sql1,"DOWNLOAD_OCC");
		} catch (Exception ex) {
            throw new Exception("DownloadStaffStudentDAO: courseExist: Error occurred / "+ ex,ex);
		}

		return new Integer(noOfRecords); //convert String to Integer 
		
		
	}
	
	
	public void insertUserRecord(String userId,String course,String reason,String otherReason) throws Exception{

		String sql2 = "INSERT INTO ADOBEDL(ID,NOVELL_USER_ID,MK_STUDY_UNIT_CODE,DOWNLOAD_DATE,DOWNLOAD_REASON,OTHER_REASON)" +
						"VALUES(ADOBEDL_0.nextval,?,?,sysdate,?,?)";
			
			try{
			JdbcTemplate jdt1 = new JdbcTemplate(super.getDataSource());
			jdt1.update(sql2,new Object[] {userId,course,reason,otherReason});
		} catch (Exception ex) {
			throw new Exception("DownloadStaffStudentDAO: insertUserRecord (Insert): Error occurred / "+ ex,ex);
		}
	}

	public void updateUserRecord(String userId,String course, int count,String reason,String otherReason) throws Exception{
		
		 count=count+1;
		String sql2 = "UPDATE ADOBEDL " +
        			  "SET    DOWNLOAD_DATE = SYSDATE,"+
        			  "DOWNLOAD_OCC="+count+","+
        			  "MK_STUDY_UNIT_CODE=upper('"+course+"'),"+
        			  "	DOWNLOAD_REASON ='"+reason+"',"+
        			  "OTHER_REASON ='"+otherReason+"'"+
        			  "WHERE  NOVELL_USER_ID = '"+userId+"'";
        
        try{
			JdbcTemplate jdt1 = new JdbcTemplate(super.getDataSource());
			jdt1.update(sql2);
		} catch (Exception ex) {
			throw new Exception("DownloadStaffStudentDAO: updateUserRecord (EDIT:update): Error occurred / "+ ex,ex);
		}
	}
	/**
	 * This method executes a query and returns a String value as result.
	 * An empty String value is returned if the query returns no results.
	 *
	 * @param query       The query to be executed on the database
	 * @param field		  The field that is queried on the database
	 * method written by: E Penzhorn
	*/
	private String querySingleValue(String query, String field){

		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
    	List queryList;
    	String result = "";

 	   queryList = jdt.queryForList(query);
 	   Iterator i = queryList.iterator();
 	   i = queryList.iterator();
 	   if (i.hasNext()) {
			 ListOrderedMap data = (ListOrderedMap) i.next();
			 if (data.get(field) == null){
			 } else {
				 result = data.get(field).toString();
			 }
 	   }
 	   return result;
	}
}
