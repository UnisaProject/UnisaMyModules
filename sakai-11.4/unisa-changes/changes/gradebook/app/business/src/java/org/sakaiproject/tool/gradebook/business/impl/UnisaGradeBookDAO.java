/**********************************************************************************
*
* $Id: UnisaGradeBookDAO.java 319110 2017-04-24 14:42:27Z magagjs@unisa.ac.za $
*
***********************************************************************************
*
 * Copyright (c) 2005, 2006, 2007, 2008, 2009 The Sakai Foundation, The MIT Corporation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.opensource.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
*
**********************************************************************************/

package org.sakaiproject.tool.gradebook.business.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.collections.map.ListOrderedMap;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.sakaiproject.component.cover.ComponentManager;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * Data Access Object class to retrieve data from the UNISA Student System Database.
 * 
 * This DAO was added to the Gradebook tool by UNISA for the implementation
 * of a Gradebook Sync feature to update marks for Gradebook items in the UNISA 
 * Student System Database. The methods used in this DAO class are called in the
 * 'GradebookSyncBean' and 'AssignmentDetailsBean' beans.
 *
 * @author <a href="mailto:magagjs@unisa.ac.za">Sifiso Magagula</a>
 */
public class UnisaGradeBookDAO {
	private final Log logger;
	private DataSource dataSource;
	private static final String DB_ONLINE_TYPES = "'BL','DF','SA','XA'";
	private static final String DB_ASSESS_TYPE =  "H";
	private static final String DB_ASSESS_GROUP = "F";
	
	public UnisaGradeBookDAO(){
		logger = LogFactory.getLog(this.getClass());
		//Get Unisa Student System Data Source using Component Manager
		dataSource = (DataSource) ComponentManager.get("za.ac.unisa.lms.db.StudentSystemDataSource");
		
		if(logger.isDebugEnabled())	logger.debug("Got a javax.sql.DataSource from the ComponentManager");
	}
	
	/**
	 * Verify that a record of a Gradebook item assignment
	 * exists in UNISA Student System Database table UNQASS
	 * 
	 * @param year The academic year of assignment
	 * @param period The semester period of assignment
	 * @param moduleCode The module code of assignment
	 * @param assignmentNr The number of assignment
	 */
	public boolean checkStudentSysAssignment(int year, int period, String moduleCode, 
			int assignmentNr) throws Exception{
		boolean countResults = false;
		
		String sqlQuery = "SELECT COUNT(mk_study_unit_code) AS module_count"+
				" FROM UNQASS"+
				" WHERE year = "+year+
				" AND period = "+period+
				" AND mk_study_unit_code = '"+moduleCode+"'"+
				" AND assignment_nr = "+assignmentNr+
				" AND type = '"+DB_ASSESS_TYPE+"'"+
				" AND online_type_gc176 in ("+DB_ONLINE_TYPES+")"+
				" AND assess_group_gc230 = '"+DB_ASSESS_GROUP+"'";
		
		String recCount = "";	
		try{
			recCount = querySingleValue(sqlQuery,"module_count");
		}catch (Exception e){
			e.printStackTrace();
			if (logger.isErrorEnabled()) 
				logger.error("Exception in checkStudentSysAssignment() method!");
		}
 		//Catch empty string and convert to "0" for parseInt() method
 		if(recCount == " " || recCount == "" || recCount == null)
 			recCount = "0";
 		
        int moduleCount =  Integer.parseInt(recCount);
        if (moduleCount>0)
        	countResults=true;
        
        return countResults;

	}
	
	/**
	 * Method to check if assignment due date is in the past [before today's date]
	 * in the UNISA Student System Database
	 * 
	 * @param year The academic year of assignment
	 * @param period The semester period of assignment
	 * @param moduleCode The module code of assignment
	 * @param assignmentNr The number of assignment
	 */
	public boolean checkDueDate(int year, int period, String moduleCode, 
			int assignmentNr) throws Exception{
		boolean dueDateResult = false;
		DateFormat dateForm = new SimpleDateFormat( "yyyy-MM-dd" );
		String todayDateStr = dateForm.format( new Date() ); 
		String assignmentDateStr = "";		//String assignment date from database
		
		String sqlQuery = "SELECT closing_date AS due_date"+
				" FROM UNQASS"+
				" WHERE year = "+year+
				" AND period = "+period+
				" AND mk_study_unit_code = '"+moduleCode+"'"+
				" AND assignment_nr = "+assignmentNr+
				" AND type = '"+DB_ASSESS_TYPE+"'"+ 
				" AND online_type_gc176 in ("+DB_ONLINE_TYPES+")"+
				" AND assess_group_gc230 = '"+DB_ASSESS_GROUP+"'";
		try{
			assignmentDateStr = querySingleValue( sqlQuery,"due_date" );
		}catch (Exception e){
			e.printStackTrace();
			if (logger.isErrorEnabled()) 
				logger.error( "Database Exception in checkDueDate() method!" );
		}
		
 		//Catch empty date string, log and return
 		if( assignmentDateStr == " " || assignmentDateStr == "" || assignmentDateStr == null ){
 			if(logger.isInfoEnabled()) logger.info("Assignment due date is null in Database!");
 			return dueDateResult;
 		}
 		
 		//Convert date String into Date object, catch Exceptions and log
 		try{
 			Date assignmentDueDate = dateForm.parse( assignmentDateStr );
 			Date todayDate = dateForm.parse( todayDateStr );
 			
 			if ( assignmentDueDate != null && todayDate!=null ){
 				//Compare if assignment due date comes before today's date
 	 			if ( assignmentDueDate.before(todayDate) )
 	 				dueDateResult = true;
 			}
 		}catch (Exception e){
 			e.printStackTrace();
 			if(logger.isErrorEnabled()){
 				logger.error( "Date parse Exception in checkDueDate() method! "+
 						"Assignment Due Date in Database is: "+assignmentDateStr+
 						" Today's Date is: "+todayDateStr);
 			}
 		}
		
		return dueDateResult;
	}
	
	/**
	 * Method to retreive user ID and three email addresses as one string seperated by a '#'.
	 * Retrieves lecturer's UNISA user ID and email addresses from UNISA Student System 
	 * and UNISA Staff Databases. 
	 * 
	 * @author <a href="mailto:syzelle@unisa.ac.za">Sonette Yzelle</a>
	 * @param module The module code
	 * @param acadYear The academic year
	 * @param semPeriod The semester period
	*/
	public String selectPrimaryLecturer(String module, String acadYear, String semPeriod) throws Exception {

		String primLecturer = "";

		String select = "select upper(novell_user_id)||'#'||"+
          		   "nvl((select usr.e_mail from usr where novell_user_code = usrsun.novell_user_id),'none')||'#'||"+
          		   "nvl((select staff.email_address from staff where NOVELL_USER_ID = usrsun.novell_user_id and resign_date is null),'none')||'#'||"+
          		   "nvl((select staff.email_address from staff where NOVELL_USER_ID = usrsun.novell_user_id and resign_date = (select max(resign_date) from staff where NOVELL_USER_ID = usrsun.novell_user_id)),'none') AS PRIML"+
				   " FROM USRSUN"+ 
				   " where mk_study_unit_code = '"+module+"'"+ 
				   " and mk_academic_year = "+acadYear+ 
				   " and mk_semester_period = "+semPeriod+
				   " and system_type = 'L'"+ 
				   " and access_level = 'PRIML'";

        try{
        	primLecturer = this.querySingleValue(select,"PRIML").toString();

        } catch (Exception ex) {
        	ex.printStackTrace();
 			if(logger.isErrorEnabled()) logger.error( "Database Exception in selectPrimaryLecturer() method!" );
        }

		return primLecturer;
	}

	/**
	 * Method to retrieve assignment (assessment) online type, for specific assessment, from UNISA Student System
	 * Method modified by <a href="mailto:magagjs@unisa.ac.za">Sifiso Magagula</a>
	 * 
	 * @author <a href="mailto:syzelle@unisa.ac.za">Sonette Yzelle</a>
	 * @param module The module code
	 * @param acadYear The academic year
	 * @param semPeriod The semester period
	 * @param asNum The assessment number
	*/
	public String selectOnlineAssessments(String acadYear, String semPeriod, String asNum, String moduleCode) throws Exception {
		String onlineType = "";
		
		String select = "select ONLINE_TYPE_GC176"+
				   " from UNQASS "+
	               " where year = "+acadYear+
	               " and period = "+semPeriod+
	               " and type = '"+DB_ASSESS_TYPE+"'"+					// 24 Nov - Sonette add assess group type F ("Formative assessment)
	               " and ONLINE_TYPE_GC176 in ("+DB_ONLINE_TYPES+")"+
	               " and ASSESS_GROUP_GC230 = '"+DB_ASSESS_GROUP+"'"+
	               " and ASSIGNMENT_NR = "+asNum+					    //Sifiso: Added
	               " and MK_STUDY_UNIT_CODE = '"+moduleCode+"'";		//Sifiso: Added
		try{
			onlineType = this.querySingleValue( select,"ONLINE_TYPE_GC176" ).toString();
    		if ( onlineType.equals("") || onlineType.equals("null") || onlineType.equals(null) ) {
    			onlineType = "No online assessments found";
			}
    		
    	} catch (Exception ex) {
    		if(logger.isErrorEnabled()) logger.error( "Database Exception in selectOnlineAssessments() method!" );
    		throw new Exception("org.sakaiproject.tool.gradebook.business.impl.UnisaGradeBookDAO: Error occurred / "+ ex,ex);
    	}

		return onlineType;
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
    	List<?> queryList;
    	String result = "";

 	   queryList = jdt.queryForList(query);
 	   Iterator<?> i = queryList.iterator();
 	   i = queryList.iterator();							 
 	   if (i.hasNext()) {
		  //ListOrderedMap data = (ListOrderedMap) i.next(); //Unisa Changes:2017/05/03:Removed
 		  HashMap data = (HashMap) i.next();   				 //Unisa Changes:2017/05/03:Added
		  if (data.get(field) == null){
		  } else {
			  result = data.get(field).toString();
		  }
 	   }
 	   return result;
	}
	
	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
}