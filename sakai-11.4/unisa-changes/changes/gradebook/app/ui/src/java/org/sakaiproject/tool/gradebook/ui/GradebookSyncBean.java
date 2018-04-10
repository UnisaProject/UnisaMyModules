/**********************************************************************************
*
* $Id: GradebookSyncBean.java 105079 2017-04-06 14:43:11Z magagjs@unisa.ac.za $
*
***********************************************************************************
*
 * Copyright (c) 2005, 2006, 2007, 2008 The Sakai Foundation, The MIT Corporation
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

package org.sakaiproject.tool.gradebook.ui;

import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.xml.rpc.ServiceException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.sakaiproject.email.api.EmailService;
import org.sakaiproject.component.cover.ComponentManager;
import org.sakaiproject.component.cover.ServerConfigurationService;
import org.sakaiproject.tool.api.ToolSession;
import org.sakaiproject.tool.cover.SessionManager;
import org.sakaiproject.tool.gradebook.business.impl.UnisaGradeBookDAO;
import org.sakaiproject.tool.gradebook.business.impl.GradebookSyncStudentSystemWebServiceServiceLocator;
import org.sakaiproject.tool.gradebook.business.impl.GradebookSyncStudentSystemWebService_PortType;
import org.sakaiproject.tool.gradebook.Assignment;
import org.sakaiproject.tool.gradebook.jsf.FacesUtil;



/**
 * Backing Bean for syncing gradebook item marks [scores] from a gradebook.
 * 
 * This Bean was added to the Gradebook tool by UNISA for the implementation
 * of a Gradebook Sync feature to update marks for Gradebook items in the UNISA 
 * Student System Database. The methods used in this bean are called in other UI Beans.
 *
 * @author <a href="mailto:magagjs@unisa.ac.za">Sifiso Magagula</a>
 */
public class GradebookSyncBean extends GradebookDependentBean implements Serializable {
    private static final Log logger = LogFactory.getLog(GradebookSyncBean.class);

    // View maintenance fields - serializable.
    private Long assignmentId;
    private Assignment assignment;
    private EmailService emailService;	
    String url = "";				
    private static final String WEBSERVICE_URL = "/unisa-axis/GradebookSyncStudentSystemWebService.jws";
    private static final String LOCAL_URL_PORT80 = "http://localhost:8080";
    private static final String LOCAL_URL_PORT82 = "http://localhost:8082";
    private static final String PORTAL_URL = "/portal";

    protected void init() {
    	if (logger.isDebugEnabled()) logger.debug("init assignment=" + assignment);
    	
    	if (assignment == null) {
	        if (assignmentId != null) {
	            assignment = getGradebookManager().getAssignment(assignmentId);
	        }
            if (assignment == null) {
                // The assignment might have been removed since this link was set up.
                if (logger.isWarnEnabled()) 
                	logger.warn("No assignmentId=" + assignmentId + " in gradebookUid " + getGradebookUid());
                // it is a new assignment
				assignment = new Assignment();
				assignment.setReleased(true);
            }
    	}
    }
    
    public String processSync(){
    	if ( assignment == null ){
			if (logger.isErrorEnabled()) logger.error("null assignment passed to processSync()");
			throw new IllegalArgumentException("null assignment passed to processSync()");
    	}
    	UnisaGradeBookDAO unisaStudentSysDao = new UnisaGradeBookDAO();
    	//Variables for webservice parameters
    	String moduleCode = "";
    	String moduleSite = "";
		String acadYear = "";
		String semPeriod = "";
		String assignmentNr = "";
		String onlineType = "";
		String primaryLecturer = "";
		String primaryLecturerEmail = "";
		
		//Catch ArrayIndex, NullPointer and Database Exceptions
		try {
			//Get webservice parameters except primaryLecturer and primaryLecturerEmail 
			String[] detailsParts = getAssignmentDetails(getGradebookManager().
					getGradebookUid(assignment.getGradebook().getId()).trim(), assignment).split("#");
			
			moduleCode = detailsParts[0];
			acadYear = "20"+detailsParts[1];	//This will only work for years after 2009
			semPeriod = detailsParts[2];
			assignmentNr = detailsParts[3];
			moduleSite = detailsParts[4];
			
			//Get online assessment type, for this specific assessment, from UNISA Student System Database 
			String dbAssessType = "";
			dbAssessType = unisaStudentSysDao.selectOnlineAssessments( acadYear, semPeriod, assignmentNr, moduleCode );
			
			if ( !dbAssessType.equals("No online assessments found") || !dbAssessType.equals("") ) {
				onlineType = dbAssessType;
			}
		
			//Get Primary Lecturer's UNISA user ID and three email addresses
			String tmpPrimaryLecturer = unisaStudentSysDao.selectPrimaryLecturer( moduleCode, acadYear, semPeriod);
			if ( tmpPrimaryLecturer.equals("") || tmpPrimaryLecturer.equals(null) || tmpPrimaryLecturer.equals("null") ) {
				//Send e-mail to portal Administrators and display error message on web page
				String subject = "Gradebook Integration - No Primary lecturer";
				String body = "Good day, <br> <br> Please note that there is no Primary Lecturer for "
					+moduleSite+". <br>Gradebook integration was not done for this module.";
				sendEmail(subject, body, "syzelle@unisa.ac.za");
				sendEmail(subject, body, "fmyburgh@unisa.ac.za");
				sendEmail(subject, body, "magagjs@unisa.ac.za");
				
				if (logger.isInfoEnabled()) logger.info("There is no Primary Lecturer for '"+moduleSite+
						" Gradebook integration was not done for this module!");
				FacesUtil.addErrorMessage(FacesUtil.getLocalizedString("validation_assignment_sync_error", 
						new String [] {"Primary Lecturer"} ));
			} else {
				String[] parts = tmpPrimaryLecturer.split("#");
				primaryLecturer = parts[0]; 		//Lecturer user ID
				String emailStudent = parts[1];		//Email from Student DB
				String emailStaff1 = parts[2];		//Email from Staff DB - Not resigned
				String emailStaff2 = parts[3];		//Email from Staff DB - Resigned
				
				if ( emailStudent.equals("none") || emailStudent.equals(" ") )
					primaryLecturerEmail = emailStaff1;
				else 
					primaryLecturerEmail = emailStudent;
				
				if ( primaryLecturerEmail.equals("none") || primaryLecturerEmail.equals(" ") ) 
					primaryLecturerEmail = emailStaff2;
					
				if (primaryLecturerEmail.equals("none") || primaryLecturerEmail.equals(" ") ) 			
					primaryLecturerEmail = "";	

			}
		} catch ( Exception e ) {
			e.printStackTrace();
			if (logger.isErrorEnabled()) 
				logger.error("Exceptions in processSync() method!! Cannot retrieve assessment details!");
			FacesUtil.addErrorMessage(FacesUtil.getLocalizedString("validation_assignment_sync_server_error"));
		}	
		
		String serverUrl = ServerConfigurationService.getString("serverUrl");
		String localPortal80 = LOCAL_URL_PORT80 + PORTAL_URL;
		String localPortal82 = LOCAL_URL_PORT82 + PORTAL_URL;
		//Check webapp environment and alter server url to correct environment (DEV or QA)
		if ( serverUrl.equals(LOCAL_URL_PORT80) || serverUrl.equals(LOCAL_URL_PORT82)
			|| serverUrl.equals(localPortal80) || serverUrl.equals(localPortal82) ) {
			serverUrl = "https://myqa.int.unisa.ac.za";		////////////CHANGE TO DEV (mydev) WHEN IMPLEMENTING!!!!!!!!!!
		} 
		
		url = serverUrl;
		url = url+WEBSERVICE_URL;

		GradebookSyncStudentSystemWebService_PortType events = null;
		
		/** Update student system with Sakai Gradebook results */
		try {
			URL url1 = new URL(url);
			events = new GradebookSyncStudentSystemWebServiceServiceLocator().getGradebookSyncStudentSystemWebService(url1);	
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
			if (logger.isErrorEnabled()) logger.error("Web service URL marlformed!");
			FacesUtil.addErrorMessage(FacesUtil.getLocalizedString("validation_assignment_sync_server_error"));
		} catch (ServiceException e3) {
			e3.printStackTrace();
			if (logger.isErrorEnabled()) logger.error("Web service creation error!");
			FacesUtil.addErrorMessage(FacesUtil.getLocalizedString("validation_assignment_sync_server_error"));
		} catch (Exception e2) {
			e2.printStackTrace();
			if (logger.isErrorEnabled()) logger.error("Web exception!");
			FacesUtil.addErrorMessage(FacesUtil.getLocalizedString("validation_assignment_sync_server_error"));
		} // end try

		//CALL Webservice IF AND ONLY IF all arguments in call are not null
		try {
			if (checkWebServiceArgs( moduleCode, moduleSite, acadYear, semPeriod,
					assignmentNr, onlineType, primaryLecturer, primaryLecturerEmail)) {
				
				events.getGradebookMarks(moduleCode, moduleSite, acadYear, semPeriod, assignmentNr, onlineType, primaryLecturer, primaryLecturerEmail);
				
				if (logger.isInfoEnabled()) logger.info("Web service call was SUCCESSFULL on '"+new Date().toString()+"'!");
				//Display sync success validation message on redirected webpage
				FacesUtil.addErrorMessage(FacesUtil.getLocalizedString("validation_assignment_sync_queued"));
			}
			
		}catch (Exception vE) {
			vE.printStackTrace();
			if (logger.isErrorEnabled()) logger.error("Exception in Web Service call on '"+new Date().toString()+"'!");
			
			FacesUtil.addErrorMessage(FacesUtil.getLocalizedString("validation_assignment_sync_server_error"));
		} // end try getGradebookMarks
		
    	//Reset navigation
		setNav("overview", "false", "false", "false", "");
		final ToolSession session = SessionManager.getCurrentToolSession();
		session.setAttribute("syncing", "false");
		
    	return "overview";
    }
    
    public String cancel() {
        // Go back to the Assignment Details page for this assignment.
        AssignmentDetailsBean assignmentDetailsBean = (AssignmentDetailsBean)FacesUtil.resolveVariable("assignmentDetailsBean");
        assignmentDetailsBean.setAssignmentId(assignmentId);
        
        //Reset navigation
		setNav(null, "false", null, "false", null);
		//Change the state of 'syncing' to false to reset breadcrumbs
		//State is changed here since we did not temper with setNav
		final ToolSession session = SessionManager.getCurrentToolSession();
		session.setAttribute("syncing", "false");
		
        return "assignmentDetails";
    }
    
    public Assignment getAssignment() {
        return assignment;
    }
    
    public void setAssignment(Assignment assignment) {
        this.assignment = assignment;
    }
    
    /**
	 * @return Returns the assignmentId.
	 */
	public Long getAssignmentId() {
		return assignmentId;
	}
	/**
	 * @param assignmentId The assignmentId to set.
	 */
	public void setAssignmentId(Long assignmentId) {
		this.assignmentId = assignmentId;
	}
	
	/**
	 * Method for use in breadcrumbs.jspf-for rendering middle level.
	 * Go to assignment details page. AssignmentBean contains duplicate of this method, 
	 * cannot migrate up to GradebookDependentBean since needs navigateBack(), defined here.
	 */
	public String navigateToAssignmentDetails() {
		return navigateBack();
	}

	/**
	 * Method for use in breadcrumbs.jspf-for rendering middle level.
	 * Go to assignment details page. InstructorViewBean and AssignmentBean contains duplicate
	 * of this method, cannot migrate up to GradebookDependentBean since needs assignmentId, 
	 * which is defined here.
	 */
	public String navigateBack() {
		String breadcrumbPage = getBreadcrumbPage();
		final Boolean middle = new Boolean((String) SessionManager.getCurrentToolSession().getAttribute("middle"));
		
		if (breadcrumbPage == null || middle) {
			AssignmentDetailsBean assignmentDetailsBean = (AssignmentDetailsBean)FacesUtil.resolveVariable("assignmentDetailsBean");
			assignmentDetailsBean.setAssignmentId(assignmentId);
			assignmentDetailsBean.setBreadcrumbPage(breadcrumbPage);
			
			breadcrumbPage = "assignmentDetails";
		}

		// wherever we go, we're not editing, and middle section
		// does not need to be shown.
		setNav(null, "false", null, "false", null);
		
		//Change the state of 'syncing' to false to reset breadcrumbs
		//state is changed here since we did not temper with setNav
		final ToolSession session = SessionManager.getCurrentToolSession();
		session.setAttribute("syncing", "false");
		
		return breadcrumbPage;
	}
	
	/**
	 * Method to extract the assignment details from Gradebook UID and 
	 * assignment object
	 * 
	 * @param gradebookUid The UID of gradebook
	 * @param assignment The assignment object
	 */
	public String getAssignmentDetails(String gradebookUid, Assignment assignment){
		if (gradebookUid == null){
			if (logger.isErrorEnabled()) logger.error("null gradebookUid passed to getAssignmentDetails()");
			throw new IllegalArgumentException("null gradebookUid passed to getAssignmentDetails()");
		}
		
		String assignmentDetailsStr = " "; //String of concatenated assignment details
		String moduleSite = " ";		   //Must be course site only (SAK11-17-S1) not group site (SAK11-17-S1-1T)
		String moduleCode = " ";			  
		String acadYear = " ";  			  
		String semPeriod = " ";			  
		String assignmentNr = " ";
		int strLength = gradebookUid.length();
		int firstDash = 0;
		int secondDash = 0;
		int thirdDash = 0;
		int firstSpace = 0;
		
		//Prevent indexOutOfBounds errors due to -1 index when using substring()
		if ( gradebookUid.indexOf("-") != -1 )
			firstDash = gradebookUid.indexOf("-");	
		if ( gradebookUid.indexOf("-") != -1 )		
			secondDash = firstDash+3;	//always 2 chars after first dash [e.g.-17-]
		if ( assignment.getName().indexOf(" ") != -1 )
			firstSpace = assignment.getName().indexOf(" ");	
		if ( gradebookUid.lastIndexOf("-") != -1 )
			thirdDash = gradebookUid.lastIndexOf("-");
		//Ensure thirdDash is truly third instead of second, get last index of string if not third dash
		if ( thirdDash == secondDash )
			thirdDash = strLength;
		/**
		 * Extract details from string of format "CCCCCC...-N...-CN...[-NNT]" 
		 * where 'C' is a char and 'N' is an int and '...' is a repeating sequence
		 * for example 'SAKAI1-10-S2'. Catch any Exceptions from substring() call
		 */
		try{ 
			//Prevent using strings where length = 0 [isEmpty() = true]
			if ( !gradebookUid.substring(0,thirdDash).trim().isEmpty() )
				moduleSite = gradebookUid.substring(0,thirdDash).trim();
		}catch (Exception e){
			e.printStackTrace();
			if (logger.isErrorEnabled()) 
				logger.error("Module Site could not be extracted from Gradebook UID!");
		}
		try{ 
			//Prevent using strings where length = 0 [isEmpty() = true]
			if ( !gradebookUid.substring(0,firstDash).trim().isEmpty() )
				moduleCode = gradebookUid.substring(0,firstDash).trim();
		}catch (Exception e){
			e.printStackTrace();
			if (logger.isErrorEnabled()) 
				logger.error("Module Code could not be extracted from Gradebook UID!");
		}
		
		try{
			if( !gradebookUid.substring(firstDash+1,secondDash).trim().isEmpty() )
				acadYear = gradebookUid.substring(firstDash+1,secondDash).trim();
		}catch (Exception e){
			e.printStackTrace();
			if (logger.isErrorEnabled()) 
				logger.error("Academic Year could not be extracted from Gradebook UID!");
			
		}try{
			if ( !gradebookUid.substring(secondDash+1,secondDash+3).trim().isEmpty() ){
				//Get period code from siteID or GB UID [e.g. S1 or Y1]
				String siteIDsemPeriod = gradebookUid.substring(secondDash+1,secondDash+3).trim();
				//Determine period code used in UNISA Student System Database
				if ( siteIDsemPeriod.startsWith("S") )
					semPeriod = gradebookUid.substring(secondDash+2,secondDash+3).trim();
				else if ( siteIDsemPeriod.equals("Y1") )
					semPeriod = "0";	//Code used in Unisa DB for year course, first part of year
				else if ( siteIDsemPeriod.equals("Y2") )
					semPeriod = "6";	//Code used in Unisa DB for year course, second part of year
			}
		}catch (Exception e){
			e.printStackTrace();
			if (logger.isErrorEnabled()) 
				logger.error("Semested Period could not be extracted from Gradebook UID!");
		}
		
		/**
		 * Extract assignment number from assignment name string 
		 * of format 'N... CC... N...' where 'N' is an int and 'C' is a char
		 * for example '16 Assignment 16'. Catch any Exceptions from substring() call
		 */
		try{
			if ( !assignment.getName().substring(0,firstSpace).trim().isEmpty() )
				assignmentNr = assignment.getName().substring(0,firstSpace).trim();
		}catch(Exception e){
			e.printStackTrace();
			if (logger.isErrorEnabled()) 
				logger.error("Assignment Number could not be extracted from Gradebook UID!");
		}
		
		//Concatenate assignment details seperated by a '#' char
		String tmpAssignmentDetailsStr = moduleCode+"#"+acadYear+"#"+semPeriod+"#"+assignmentNr+"#"+moduleSite;
		//Prevent using strings where length = 0 [isEmpty() = true]
		if( !tmpAssignmentDetailsStr.isEmpty() )
			assignmentDetailsStr = tmpAssignmentDetailsStr;
					
		return assignmentDetailsStr;
	}
	
	/**
	 * Method to check if a String of digits can be converted to the int equivalent 
	 * of the digits. This method was adapted from the Apache Commons method - 
	 * org.apache.commons.lang3.StringUtils.isNumeric(). All credit is due to Apache Commons.
	 * The method is re-invented here to avoid importing libraries for just one method.
	 * 
	 * @param str The String to check
	 */
	public boolean isStringInt(String str){
		boolean nonDigitFound = false;
		
		if( str.isEmpty() )
			return false;
		for( int i = 0; i < str.length(); i++ ){
			if ( !Character.isDigit(str.charAt(i)) ){
				//break out of loop if any char is not digit
				nonDigitFound = true;
				break; 
			}
		}
		//Check if int value wont give Number Format Exceptions
		if( !nonDigitFound ){
			try {
				Integer.parseInt(str);
				return true;
			}
			catch(NumberFormatException e){
				return false;
			}
		}
		
		return false;
	}
	
	/**
	 * Method to check the title of a Gradebook item to determine if the title 
	 * is in Format 'x assignment x' where where x is a valid assignment number.
	 * 
	 * @param assignment The assignment that has the GB title
	 */
	public boolean verifyGradebookTitle( Assignment assignment ){
		if ( assignment == null ){
			if (logger.isErrorEnabled()) 
				logger.error("null assignment passed to verifyGradebookTitle()");
			throw new IllegalArgumentException("null assignment passed to verifyGradebookTitle()");
		}
		String gbTitle = assignment.getName().trim();
		int strLength = 0, firstSpace = 0, lastSpace = 0;
		String strBeforeSpace = " ", strAfterLastSpace = " ";
		
		strLength = gbTitle.length();
		firstSpace = gbTitle.indexOf(" ");
		lastSpace = gbTitle.lastIndexOf(" ");
		
		//Check if there is only one space instead of two - wrong format
		if( firstSpace == lastSpace )
			return false;
		
		//Prevent and catch any indexOutOfBounds Exceptions due to substring() call
		try{	
			if( firstSpace != -1 )
				strBeforeSpace = gbTitle.substring( 0,firstSpace );
			if( (strLength != -1) && (lastSpace != -1) )
				strAfterLastSpace = gbTitle.substring( lastSpace+1,strLength );
		}catch( Exception e ){
			e.printStackTrace();
			if (logger.isErrorEnabled()) logger.error("String Exception in verifyGradebookTitle()");
		}
			
		//No space in GB title, before middle section, Format incorrect
		if ( strBeforeSpace.isEmpty() )
			return false;	
		
		//No space in GB title, after middle section, Format incorrect
		if ( strAfterLastSpace.isEmpty() )
			return false;
		
		//Check if string before space is an integer
		if( !isStringInt(strBeforeSpace) )
			return false;
		
		//Check if string after last occurance of space is an integer
		if( !isStringInt(strAfterLastSpace) )
			return false;
			
		return true;
	}
	
	
	/**
	 * Method to check if string parameter can be parsed as int value. The 'verifyStr' 
	 * parameter is used to pass a String array argument, which is indexed according 
	 * to position of assignment detail in the array. The 'indexArr' parameter determines 
	 * the index of array.
	 * 
	 * @param verifyStr The String to check
	 * @param indexArr The index to use for selecting error message
	 */
	public int verifyAssignmentDetails(String verifyStr, int indexArr){
		int verifyStrResult;	//String value to check int type
		
		if (isStringInt(verifyStr))
			verifyStrResult = Integer.parseInt(verifyStr);			
		else{
			verifyStrResult = -1;
			if(indexArr == 1){
				FacesUtil.addErrorMessage(FacesUtil.getLocalizedString("validation_assignment_acad_year_format",
						new String[] {verifyStr}));
				if (logger.isDebugEnabled()) 
					logger.debug("Academic Year '"+verifyStr+"' cannot be converted to an int value!");
			}else if(indexArr == 2){
					FacesUtil.addErrorMessage(FacesUtil.getLocalizedString("validation_assignment_sem_period_format",
							new String[] {verifyStr}));
					if (logger.isDebugEnabled()) 
						logger.debug("Semester Period '"+verifyStr+"' cannot be converted to an int value!");
			}else if (indexArr == 3){
					FacesUtil.addErrorMessage(FacesUtil.getLocalizedString("validation_assignment_number_format",
							new String[] {verifyStr}));
					if (logger.isDebugEnabled()) 
						logger.debug("Assignment Number '"+verifyStr+"' cannot be converted to an int value!");
			}
		}
		
		/**Append prefix '200' or '20' to 1 or 2 digit year to make 4 digit year 
		 * [e.g '09' will be '2009' AND "17" will be "2017"]
		 * Firstly, 'indexArr' must be 1 to ensure 'verifyStr' is a year string
		 * This calculation will last only one century: 2000 - 2099
		 */
		if ( indexArr == 1 ){
			String tmpVerifyStrResult = new Integer(verifyStrResult).toString();
			//Years from 2000 to 2099
			if ( (verifyStrResult >= 0) && (verifyStrResult <= 99) ){
				//Consider int conversion where year is less than 10 [e.g. <2010]
				if( verifyStrResult < 10 ){
					tmpVerifyStrResult = "200"+tmpVerifyStrResult;
					verifyStrResult = Integer.parseInt(tmpVerifyStrResult);
				}
				else if( verifyStrResult > 10 ){ //int conversion is more than 10 [>2010]
					tmpVerifyStrResult = "20"+tmpVerifyStrResult;
					verifyStrResult = Integer.parseInt(tmpVerifyStrResult);
				}
			}
		}
		
		return verifyStrResult;
	}
	
	/**
	 * Method to send email
	 * 
	 * @param subject The email subject line
	 * @param body The email body message
	 * @param emailAddress The email email address
	 */
	public void sendEmail(String subject, String body, String emailAddress) throws AddressException {
		
		emailService = (EmailService) ComponentManager.get(EmailService.class);

		String tmpEmailFrom = ServerConfigurationService.getString("noReplyEmailFrom");


		InternetAddress toEmail = new InternetAddress(emailAddress);
		InternetAddress iaTo[] = new InternetAddress[1];
		iaTo[0] = toEmail;
		InternetAddress iaHeaderTo[] = new InternetAddress[1];
		iaHeaderTo[0] = toEmail;
		InternetAddress iaReplyTo[] = new InternetAddress[1];
		iaReplyTo[0] = new InternetAddress(tmpEmailFrom);
		List<String> contentList = new ArrayList<String>();
		contentList.add("Content-Type: text/html");

		emailService.sendMail(iaReplyTo[0],iaTo,subject,body,iaHeaderTo,iaReplyTo,contentList);
	} // end of sendEmail
	
	/**
	 * Method to check if all arguments required to call webservice are not empty strings
	 * Returns false and an error web message when any one of the arguments is an empty string
	 * 
	 * @param modCode The module code
	 * @param modSite The module site
	 * @param year The academic year
	 * @param period The semester period
	 * @param assignNr The assessment number
	 * @param assignType The assessment online type
	 * @param primLectId The primary lecturer Unisa username
	 * @param primLectEmail The primary lecturer email address
	 */
	public boolean checkWebServiceArgs( String modCode, String modSite, String year, String period,
			String assignNr, String assignType, String primLectId, String primLectEmail){
		
		String logMsg = "Web service call was UNSUCCESSFULL on "+new Date().toString()+"! ";
		String modCodeStr = "'Module Code'", modSiteStr = "'Module Site'", yearStr = "'Academic Year'",
				periodStr = "'Semester Period'", assignNrStr = "'Assignment Number'", assignTypeStr = "'Online Type'", 
				primLectIdStr = "'Primary Lecturer Username'", primLectEmailStr = "'Primary Lecturer Email Address'";
				
		if ( modCode.equals("") ){
			if (logger.isInfoEnabled()) logger.info(logMsg + modCodeStr+" is empty!");
			FacesUtil.addErrorMessage(FacesUtil.getLocalizedString("validation_assignment_sync_error", 
					new String[] {modCodeStr} ));
			return false;
		}
		if ( modSite.equals("") ){
			if (logger.isInfoEnabled()) logger.info(logMsg + modSiteStr+" is empty!");
			FacesUtil.addErrorMessage(FacesUtil.getLocalizedString("validation_assignment_sync_error", 
					new String[] {modSiteStr} ));
			return false;
		}
		if ( year.equals("") ){
			if (logger.isInfoEnabled()) logger.info(logMsg + yearStr+" is empty!");
			FacesUtil.addErrorMessage(FacesUtil.getLocalizedString("validation_assignment_sync_error", 
					new String[] {yearStr} ));
			return false;
		}
		if ( period.equals("") ){
			if (logger.isInfoEnabled()) logger.info(logMsg + periodStr+" is empty!");
			FacesUtil.addErrorMessage(FacesUtil.getLocalizedString("validation_assignment_sync_error",
					new String[] {periodStr} ));
			return false;
		}
		if ( assignNr.equals("") ){
			if (logger.isInfoEnabled()) logger.info(logMsg + assignNrStr+" is empty!");
			FacesUtil.addErrorMessage(FacesUtil.getLocalizedString("validation_assignment_sync_error",
					new String[] {assignNrStr} ));
			return false;
		}
		if ( assignType.equals("") || assignType.equals("No online assessments found") ){
			if (logger.isInfoEnabled()) logger.info(logMsg + assignTypeStr+" is empty!");
			FacesUtil.addErrorMessage(FacesUtil.getLocalizedString("validation_assignment_sync_error",
					new String[] {assignTypeStr} ));
			return false;
		}
		if ( primLectId.equals("") ){
			if (logger.isInfoEnabled()) logger.info(logMsg + primLectIdStr+" is empty!");
			FacesUtil.addErrorMessage(FacesUtil.getLocalizedString("validation_assignment_sync_error", 
					new String[] {primLectIdStr} ));
			return false;
		}
		if ( primLectEmail.equals("") || primLectEmail.equals("none") ){
			if (logger.isInfoEnabled()) logger.info(logMsg + primLectEmailStr+" is empty!");
			FacesUtil.addErrorMessage(FacesUtil.getLocalizedString("validation_assignment_sync_error", 
					new String[] {primLectEmailStr} ));
			return false;
		}
		
		return true;
	}
    
}



