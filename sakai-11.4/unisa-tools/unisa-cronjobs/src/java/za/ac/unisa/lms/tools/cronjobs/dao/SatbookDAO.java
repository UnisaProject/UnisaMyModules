package za.ac.unisa.lms.tools.cronjobs.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import org.apache.commons.collections.map.ListOrderedMap;
import org.apache.struts.util.LabelValueBean;
import org.sakaiproject.component.cover.ComponentManager;
import org.sakaiproject.component.cover.ServerConfigurationService;
import org.sakaiproject.email.api.EmailService;
import org.springframework.jdbc.core.JdbcTemplate;

import Srsms02h.Abean.Srsms02sSendSmsToStudList;

//import Srsms01h.Abean.Srsms01sSendSingleSms;

//import Srsms01h.Abean.Srsms01sSendSingleSms;

import za.ac.unisa.lms.db.SakaiDAO;

public class SatbookDAO extends SakaiDAO{
	
	 private EmailService emailService;

	/* Production data as on 17 Mar 2008
	 * mysql> SELECT CLASSROOM_NAME, CLASSROOM_ID FROM SATBOOK_CLASSROOMS ORDER BY CLASSROOM_ID;
+--------------------+--------------+
| CLASSROOM_NAME     | CLASSROOM_ID |
+--------------------+--------------+
| Florida            |            1 |
| Johannesburg (JSE) |            2 |
| UNISA Sunnyside    |            3 |
| Durban             |            4 |
| Pieter Maritzburg  |            5 |
| Nelspruit          |            6 |
| Middleburg         |            7 |
| Bloemfontein       |            8 |
| Klerksdorp         |            9 |
| Mafikeng           |           10 |
| Kimberley          |           11 |
| Upington           |           12 |
| Polokwane          |           13 |
| East London        |           14 |
| Port Elizabeth     |           15 |
| Parow              |           16 |
| George             |           17 |
| Stellenbosch       |           18 |
| Worcester          |           19 |
| Windhoek           |           20 |
| New Castle         |           22 |
| Rustenburg         |           23 |
| Potchefstroom      |           24 |
| Umtata             |           25 |
| Benoni             |           26 |
+--------------------+--------------+
20 rows in set (0.05 sec)
	 */
//	final String[] classroomListF = {"1" ,"2" ,"3", "4", "5", "6","7", "8","9" ,"10","11","12","13","14","15","16","17","18","19","23","22","24","25","26"};
//	final String[] regionListF =    {"23","26","10","3", "11","5","12","9","20","16","7" ,"7" ,"4" ,"8" ,"15","2" ,"18","2" ,"2", "17","24","20","6","25"};

	final String[] classroomListF = {"1" ,"2" ,"3", "4", "5", "6","7", "8","9" ,"10","12","13","14","15","17","19","23","22","25","26"};
	final String[] regionListF =    {"23","26","10","3", "11","5","12","9","20","16","7" ,"4" ,"8" ,"15","18","2", "17","24","6","25"};


	// dates in format YYYY-MM-DD
	public ArrayList<?> selectBookingList() throws Exception {
		ArrayList<?> bookingList = new ArrayList();
		SatbookOracleDAO db2 = new SatbookOracleDAO();
		int noOfEmails = 0;

		/** select all bookings that will take place in 8 days. */

		//select * from SATBOOK_BKNG_MAIN WHERE DATE_FORMAT(BKNG_START,'%Y-%m-%d') = DATE_ADD(CURDATE(), INTERVAL 8 DAY)
		String select = "SELECT BKNG_ID, BKNG_HEADING, BKNG_LECT_NOVELLID," +
				        "BKNG_LECT_TEL," +
				        "TO_CHAR(BKNG_START,'DD-MON-YYYY') AS STARTTIME, "+
				        "TO_CHAR(BKNG_START, 'HH24') AS STARTTIMEHH," +
				        "TO_CHAR(BKNG_START, 'MI') AS STARTTIMEMM," +
				        "TO_CHAR(BKNG_END,'DD-MON') AS ENDTIME, "+
				        "TO_CHAR(BKNG_END, 'HH24') AS ENDTIMEHH," +
				        "TO_CHAR(BKNG_END, 'MI') AS ENDTIMEMM," +
				        "BKNG_DESC,  nvl(BKNG_REBROADCAST,'N') AS REBROADCAST "+
		                "FROM SATBOOK_BKNG_MAIN "+
		                "WHERE TO_CHAR(BKNG_START,'YYYY-MM-DD') = TO_CHAR(sysdate+7,'YYYY-MM-DD') "+
		                "AND   BKNG_CONFIRMED = 'Y'"+
		                " AND   SYSTEM_ID = 1";
		// QA: "WHERE TO_CHAR(BKNG_START,'YYYY-MM-DD') = '2007-09-10' "+
		//"WHERE TO_CHAR(BKNG_START,'YYYY-MM-DD') = TO_CHAR(sysdate+7,'YYYY-MM-DD') "+

		try{
    		JdbcTemplate jdt = new JdbcTemplate(super.getDataSource());
    		List<?> examInfoList = jdt.queryForList(select);
			Iterator<?> j = examInfoList.iterator();
    		while (j.hasNext()) {
    			ListOrderedMap data = (ListOrderedMap) j.next();
    			String feedbackEmail = "";


    			/** Booking information */
    			String bkngId = data.get("BKNG_ID").toString();
    			String bkngHeading = data.get("BKNG_HEADING").toString();
    			String bkngStartDate = data.get("STARTTIME").toString();
    			String bkngStartTime = data.get("STARTTIMEHH").toString()+":"+data.get("STARTTIMEMM").toString();
    			String bkngEndTime = data.get("ENDTIMEHH").toString()+":"+data.get("ENDTIMEMM").toString();
    			String bkngRebroadcast = data.get("REBROADCAST").toString();

    			feedbackEmail = "<html> <body> Satbook Sms messages <br>";
    			feedbackEmail = feedbackEmail+"Booking: "+bkngHeading+" (booking id = "+bkngId+") <br> <br>";

    			ArrayList<LabelValueBean> classroomList = selectBkngClassroomDetial(bkngId);
    			String classrooms = "";
    			String forRegions="";
    			for (int m=0; m < classroomList.size() ;m++) {
    				LabelValueBean classr = classroomList.get(m);
    				if (m == 0) {
    					classrooms = classr.getValue();
    				} else {
    					classrooms = classrooms+"; "+classr.getValue();
    				}
    				for (int n=0; n < classroomListF.length; n++) {
    					if (classroomListF[n].equals(classr.getLabel())) {
    						if (forRegions.equals("")) {
    							forRegions = regionListF[n];
    						} else {
    							forRegions = forRegions+","+regionListF[n];
    						}
    					}
    				}
    			}

    			// select subjects for these bookings
    			 ArrayList<LabelValueBean> subjectList = selectBkngSubjectDetial(bkngId);

    			// get student email address linked to subject
    			if (subjectList != null) {
    				for (int i=0; i < subjectList.size(); i++) {
    					noOfEmails = 0;
    					LabelValueBean subject = subjectList.get(i);
    					// tmp[0] = subject code; tmp[1] acadPeriod; tmp[2] acadYear
    					String[] tmp = subject.getValue().split("#");
    					String subjCode = tmp[0];
    					short semesterPeriod = Short.parseShort(tmp[1]);
    					short acadYear = Short.parseShort(tmp[2]);

    					// select list of email addresses
    					/* Leave emails until discussed with Irene and Louise */
    					
    					ArrayList<?> emailList = db2.selectStudentEmailList(tmp[0],tmp[1],tmp[2],forRegions);

    					String emailHeading = "UNISA Satellite broadcast: "+tmp[0];
    					String emailBody = "<html> "+
						                   "<body> "+
    							           "Dear Student"+" <br><br>"+
    							           "Please be informed that ";
    							           if(bkngRebroadcast.equalsIgnoreCase("N")){
    							        	   emailBody=emailBody+"Satellite Broadcast";
    							           }else if(bkngRebroadcast.equalsIgnoreCase("Y")){
    							        	   emailBody=emailBody+"Satellite RE-broadcast";
    							           }
    							           emailBody=emailBody+
    							           " class for "+tmp[0]+" will take place as follow:"+" <br><br> "+
    									   "Date: "+bkngStartDate+"<br>"+
    									   "Time: "+bkngStartTime+" - "+bkngEndTime+"<br><br>"+ 
    									   "For your nearest satellite venue, you may call 011 670 9000 or contact your "+"<br>"+
    									   "nearest regional office or e-mail: "+"<a>"+"unisatv@unisa.ac.za"+"</a>"+" or " +"<a>"+"www.unisa.ac.za/utv"+"</a>"+ " to access the list of all satellite class rooms."+
        					               "</body>"+
        					               "</html>";
    					System.out.println("email: "+emailHeading);
    					System.out.println("email: "+emailBody);
    					for (int k=0; k < emailList.size(); k++) {
    						LabelValueBean email = (LabelValueBean) emailList.get(k);
    						System.out.println("email "+email.getValue());
    						noOfEmails++;
    						sendEmail(emailHeading,emailBody,email.getValue());
    					}
    					

    					String smsMsg = "";
    				/*	if (bkngRebroadcast.equalsIgnoreCase("Y")) {
    						smsMsg = tmp[0]+ " satellite RE-broadcast "+bkngStartDate+" "+bkngStartTime+"-"+bkngEndTime+" call regional office/call "+
    									"centre(011 6709000)/ http://www.unisa.ac.za/utv";
    					} else {
    						smsMsg = tmp[0]+ " satellite broadcast "+bkngStartDate+" "+bkngStartTime+"-"+bkngEndTime+" call regional office/call "+
							"centre(011 6709000)/ http://www.unisa.ac.za/utv";
    					}*/
    					if (bkngRebroadcast.equalsIgnoreCase("Y")) {
    						smsMsg = tmp[0]+ " satellite RE-broadcast class on "+bkngStartDate+"@ "+bkngStartTime+"-"+bkngEndTime+". Call 0116709000/regional office/www.unisa.ac.za/utv for nearest venue";
    					} else {
    						smsMsg = tmp[0]+ " satellite broadcast class on "+bkngStartDate+"@ "+bkngStartTime+"-"+bkngEndTime+". Call 0116709000/regional office/www.unisa.ac.za/utv for nearest venue";
    					}

    						// proxy for multiple sms
    						Srsms02sSendSmsToStudList op = new Srsms02sSendSmsToStudList();
     						operListener op1 = new operListener();
    					    op.addExceptionListener(op1);
    					    op.clear();

    					    // Input for proxy - first part - no sms will be send with this.
    					    String userCode = ServerConfigurationService.getString("satbookSMSResponsiblePerson");
    					    op.setInNovellCodeWsStaffEmailAddress(userCode.toUpperCase()); // novell code of user sending sms
    					    op.setInSmsRequestSmsMsg(smsMsg);
    					    op.setInSmsRequestReasonGc27(2); // 2= learner support
    					    op.setInSendSmsCsfStringsString1("N"); // N = don't send sms (for testing purposes)
    					    op.setInRegistrationCriteriaTypeCsfStringsString1("M"); // selected modules
    					    op.setInMagisterialCriteriaTypeCsfStringsString1("R"); // selected regions
    					    op.setInCsfClientServerCommunicationsClientVersionNumber((short) 3);
    					    op.setInCsfClientServerCommunicationsClientRevisionNumber((short) 1);
    					    op.setInCsfClientServerCommunicationsClientDevelopmentPhase("C");
    					    op.setInCsfClientServerCommunicationsClientIsGuiFlag("Y");
    					    op.setInCsfClientServerCommunicationsAction("S");
    					    op.setInWsStudyUnitPeriodDetailMkAcademicPeriod(semesterPeriod);
    					    op.setInWsStudyUnitPeriodDetailMkAcademicYear(acadYear);
    					    op.setInRegistrationGpCsfStringsString15(0,subjCode);
    					    for (int x=0; x < regionListF.length; x++) {
   					    		op.setInMagisterialGpCsfStringsString15(x,regionListF[x]);
    					    }

    					    op.execute();

    					    // output from above input, to make sure sms must be send.
    					    if (op.getOutErrorFlagCsfStringsString1().equals("Y")) {
    					    	feedbackEmail = feedbackEmail+"Subject: "+subjCode+"= SMS FAILED<br>";
    					    	System.out.println("SATBOOK SMS JOB: error occurred: "+op.getOutCsfStringsString500());
    			    			try {
    			    				sendEmail("Satbook SMS error occured","Satbook SMS error occurred (subj= : "+subjCode+" year="+acadYear+" period="+semesterPeriod+"): "+op.getOutCsfStringsString500(),"motlhtm@unisa.ac.za");
    			    			} catch (AddressException e) {
    			    				//log.info("error sending mail from LMSMIS HISTORY STATS to syzelle@unisa.ac.za");
    			    			}
    			    			try {
    			    				sendEmail("Satbook SMS error occured","Satbook SMS error occurred (subj= : "+subjCode+" year="+acadYear+" period="+semesterPeriod+"): "+op.getOutCsfStringsString500(),"syzelle@unisa.ac.za");
    			    			} catch (AddressException e) {
    			    				//log.info("error sending mail from LMSMIS HISTORY STATS to syzelle@unisa.ac.za");
    			    			}
    			    			try {
    			    				sendEmail("Satbook SMS error occured","Satbook SMS error occurred (subj= : "+subjCode+" year="+acadYear+" period="+semesterPeriod+"): "+op.getOutCsfStringsString500(),"mawaske@unisa.ac.za");
    			    			} catch (AddressException e) {
    			    				//log.info("error sending mail from LMSMIS HISTORY STATS to syzelle@unisa.ac.za");
    			    			}
    					    }
    					    String errorMessage = op.getOutCsfStringsString500();
    					    String rcCode = op.getOutSmsRequestMkRcCode();
    					    String rcCodeDesc = op.getOutRcDesriptionCsfStringsString60();
    					    double costOfTotalSms = op.getOutTotalCostIefSuppliedTotalCurrency();
    					    double costPerSms = op.getOutSmsCostCostPerSms();
    					    double availableBudget = op.getOutAvailableBudgetAmountIefSuppliedTotalCurrency();
    					    double totalBudget = op.getOutBudgetAmountIefSuppliedTotalCurrency();
    					    int totalMsgToBeSend = op.getOutSmsRequestMessageCnt();
    					    short departmentCode = op.getOutSmsRequestMkDepartmentCode();
    					    int batchNumber = op.getOutSmsRequestBatchNr();
    					    String emailSubject = "Satellite broadcasting sms";	

    					    /* if there is messages to be send, do 2nd part of proxy where sms messages will be added to the
    					     * SMSQUE table.
    					     */
    					    if (totalMsgToBeSend >= 1) {
    					    	op.setInNovellCodeWsStaffEmailAddress(userCode.toUpperCase()); // novell code of user sending sms
    					    	op.setInSmsRequestMkRcCode(rcCode);
    					    	op.setInSmsRequestMkDepartmentCode(departmentCode);
    					    	op.setInSmsRequestTotalCost(costOfTotalSms);
    					    	op.setInSmsRequestFromSystemGc26(op.getInSmsRequestFromSystemGc26());
    					    	op.setInSmsRequestSmsMsg(smsMsg);
    					    	op.setInSmsRequestBatchNr(batchNumber);
    					    	op.setInSmsRequestMkPersNr(op.getOutSmsRequestMkPersNr());
    					    	op.setInSmsRequestMessageCnt(totalMsgToBeSend);
    					    	op.setInSmsRequestReasonGc27(2);
    					    	op.setInSendSmsCsfStringsString1("Y"); // Y = send sms
    					    	op.setInFileNameWizfuncReportingControlPathAndFilename(op.getOutFileNameWizfuncReportingControlPathAndFilename());
    					    	op.setInRegistrationCriteriaTypeCsfStringsString1("M");
    					    	op.setInMagisterialCriteriaTypeCsfStringsString1("R");
        					    op.setInCsfClientServerCommunicationsClientVersionNumber((short) 3);
        					    op.setInCsfClientServerCommunicationsClientRevisionNumber((short) 1);
        					    op.setInCsfClientServerCommunicationsClientDevelopmentPhase("C");
        					    op.setInCsfClientServerCommunicationsClientIsGuiFlag("Y");
        					    op.setInCsfClientServerCommunicationsAction("S");
           					    op.setInWsStudyUnitPeriodDetailMkAcademicPeriod(semesterPeriod);
        					    op.setInWsStudyUnitPeriodDetailMkAcademicYear(acadYear);
        					    op.setInRegistrationGpCsfStringsString15(0,subjCode);
        					    for (int x=0; x < regionListF.length; x++) {
       					    		op.setInMagisterialGpCsfStringsString15(x,regionListF[x]);
        					    }

        					    op.execute();

        					    /* output for 2nd part where sms was actually added to the smsque table. */
        	        			errorMessage = op.getOutCsfStringsString500();
        					    rcCode = op.getOutSmsRequestMkRcCode();
        					    rcCodeDesc = op.getOutRcDesriptionCsfStringsString60();
        					    costOfTotalSms = op.getOutTotalCostIefSuppliedTotalCurrency();
        					    costPerSms = op.getOutSmsCostCostPerSms();
        					    availableBudget = op.getOutAvailableBudgetAmountIefSuppliedTotalCurrency();
        					    totalBudget = op.getOutBudgetAmountIefSuppliedTotalCurrency();
        					    totalMsgToBeSend = op.getOutSmsRequestMessageCnt();
        					    departmentCode = op.getOutSmsRequestMkDepartmentCode();
        					    batchNumber = op.getOutSmsRequestBatchNr();

        					    feedbackEmail = feedbackEmail+"Subject: "+subjCode+" registration year: "+acadYear+" registration period: "+semesterPeriod+"<br>";
        					    if (op.getOutErrorFlagCsfStringsString1().equals("Y")) {
        					    	feedbackEmail = feedbackEmail+"Subject: "+subjCode+" = SMS FAILED: ERROR Occurred: "+ errorMessage+"<br>";
        					    	emailSubject = "Satellite broadcasting SMS ERROR OCCURRED";	
        					    } else {
        					    	feedbackEmail = feedbackEmail+"Subject: "+subjCode+" = SMS SUCCESS<br>";
	        					    feedbackEmail = feedbackEmail+"User: "+op.getOutSecurityWsUserName()+"<br>";
	        					    feedbackEmail = feedbackEmail+"Sms message: "+smsMsg+"<br>";
	        					    feedbackEmail = feedbackEmail+"Total number of messages send: "+totalMsgToBeSend+"<br>";
	        					    feedbackEmail = feedbackEmail+"Cost per sms: "+costPerSms+"<br>";
	        					    feedbackEmail = feedbackEmail+"Total cost of messages send: "+costOfTotalSms+"<br>";
	        					    feedbackEmail = feedbackEmail+"RC Code: "+rcCode+" - "+rcCodeDesc+"<br>";
	        					    feedbackEmail = feedbackEmail+"Available budget: "+availableBudget+"<br>";
	        					    feedbackEmail = feedbackEmail+"Total Budget: "+totalBudget+"<br> ";
	        					    feedbackEmail = feedbackEmail+"For classrooms: "+classrooms+"<br>";
	        					    feedbackEmail = feedbackEmail+"Sms request batch number: "+batchNumber+" <hr>";
        					    }
	        					    
        					    if (op1.getException() != null) {
        					      	throw op1.getException();
        					    }
        					    if (op.getExitStateType() < 3) {
        					       	throw new Exception(op.getExitStateMsg());
        					    }

        					    String errorOccured = op.getOutErrorFlagCsfStringsString1();
        					    if (errorOccured.equals("Y")) {
        					    	System.out.println("SATBOOK: sms proxy Error occurred");
        					    } 

    					    } // if (totalMsgToBeSend >= 1) {
    					    // send e-mail
    				
    				
    				} // for (int i=0; i < subjectList.size(); i++) {

    				} //if (subjectList != null) {
    			
    			feedbackEmail = feedbackEmail + "</body></html>";
    	    	String emailSubject = "Satellite broadcasting sms";	
    	    	
    	        try{
    	    		ArrayList<LabelValueBean> al=selectEmailList(); 
    	    		sendEmail(emailSubject,feedbackEmail,"motlhtm@unisa.ac.za");
    	    		sendEmail(emailSubject,feedbackEmail,"syzelle@unisa.ac.za");
    	    		for(int i=0;i<al.size();i++){
    	    			LabelValueBean emailList = al.get(i);
    	    			sendEmail(emailSubject,feedbackEmail,emailList.getLabel());
    	    		}
    	    	}catch(AddressException e){
    	    		throw new Exception("za.ac.unisa.lms.db.SakaiDAO.SatbookDAO: selectBookingList: AddressException: Error occurred / "+ e,e);
    	    	}   
     
    		} // end while

    	} catch (Exception ex) {
    		throw new Exception("za.ac.unisa.lms.db.SakaiDAO.SatbookDAO: selectBookingList: Error occurred / "+ ex,ex);
    	} // end try

		return bookingList;
	}

	public ArrayList<LabelValueBean> selectBkngSubjectDetial(String bkngId) throws Exception {

		ArrayList<LabelValueBean> subjectList = new ArrayList<LabelValueBean>();


		String select = "SELECT SUBJ_CODE||'#'||SUBJ_PERIOD||'#'||SUBJ_YEAR AS SUBJECT " +
		                "FROM   SATBOOK_BKNG_SUBJECT "+
		                "WHERE  BKNG_ID = "+bkngId+" ";

		try{
    		JdbcTemplate jdt = new JdbcTemplate(super.getDataSource());
    		List<?> examInfoList = jdt.queryForList(select);
			Iterator<?> j = examInfoList.iterator();
    		while (j.hasNext()) {
    			ListOrderedMap data = (ListOrderedMap) j.next();

    			subjectList.add(new org.apache.struts.util.LabelValueBean(data.get("SUBJECT").toString(), data.get("SUBJECT").toString()));

    		} // end while

    	} catch (Exception ex) {
    		throw new Exception("za.ac.unisa.lms.db.SakaiDAO.SatbookDAO: selectBkngSubjectDetial: Error occurred / "+ ex,ex);
    	} // end try

		return subjectList;
	}

	public void sendEmail(String subject, String body, String emailAddress) throws AddressException {
		
		emailService = (EmailService) ComponentManager.get(EmailService.class);

		String tmpEmailFrom = ServerConfigurationService.getString("noReplyEmailFrom");
         System.out.println("----"+emailAddress);
		InternetAddress toEmail = new InternetAddress(emailAddress);
		InternetAddress iaTo[] = new InternetAddress[1];
		iaTo[0] = toEmail;
		InternetAddress iaHeaderTo[] = new InternetAddress[1];
		iaHeaderTo[0] = toEmail;
		InternetAddress iaReplyTo[] = new InternetAddress[1];
		iaReplyTo[0] = new InternetAddress(tmpEmailFrom);
		List<String> contentList = new ArrayList<String>();
		contentList.add("Content-Type: text/html");
		//contentList.add("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");

		emailService.sendMail(iaReplyTo[0],iaTo,subject,body,iaHeaderTo,iaReplyTo,contentList);
		//log.info("sending mail from "+iaReplyTo[0]+" to "+toEmail.toString());
	} // end of sendEmail

	public ArrayList<LabelValueBean> selectBkngClassroomDetial(String bkngId) throws Exception {

		ArrayList<LabelValueBean> classroomList = new ArrayList<LabelValueBean>();

		String select = "SELECT REGION_ID "+
        			    "FROM   SATBOOK_BKNG_CLASSROOM "+
        			    "WHERE  BKNG_ID = "+bkngId+" "+
		                "ORDER BY REGION_ID ";

    	try{
    		JdbcTemplate jdt = new JdbcTemplate(super.getDataSource());
    		List<?> examInfoList = jdt.queryForList(select);
			Iterator<?> j = examInfoList.iterator();
    		while (j.hasNext()) {
    			ListOrderedMap data = (ListOrderedMap) j.next();
    			String regionDesc = selectClassroomDesc(data.get("REGION_ID").toString());

    			classroomList.add(new org.apache.struts.util.LabelValueBean(data.get("REGION_ID").toString(), regionDesc));

    		} // end while

    	} catch (Exception ex) {
    		throw new Exception("SatbookBookingDAO: selectBkngClassroomDetial: Error occurred / "+ ex,ex);
    	} // end try

		return classroomList;
	}

	public String selectClassroomDesc(String regionCode) throws Exception {
		String regionDesc = "";

		String select = "SELECT CLASSROOM_NAME "+
		                "FROM   SATBOOK_CLASSROOMS "+
		                "WHERE  CLASSROOM_ID = '"+regionCode+"' ";

  		try{
    		regionDesc = this.querySingleValue(select,"CLASSROOM_NAME");

     	} catch (Exception ex) {
    		throw new Exception("SatbookOracleDAO: selectClassroomDesc: Error occurred / "+ ex,ex);
    	} // end try

		return regionDesc;
	}

	private class operListener implements java.awt.event.ActionListener {
		private Exception exception = null;

		operListener() {
			exception = null;
		}

		public Exception getException() {
			return exception;
		}

		public void actionPerformed(java.awt.event.ActionEvent aEvent) {
			exception = new Exception(aEvent.getActionCommand());
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
    	List<?> queryList;
    	String result = "";

 	   queryList = jdt.queryForList(query);
 	   Iterator<?> i = queryList.iterator();
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
	
	public ArrayList<LabelValueBean> selectEmailList() throws Exception {

		ArrayList<LabelValueBean> emailList = new ArrayList<LabelValueBean>();


		String select = "SELECT ASS_EMAIL FROM SATBOOK_ASSISTANTS,SATBOOK_ASSISTANT_TYPES " +
		                "WHERE SATBOOK_ASSISTANTS.ASS_TYPE_ID = SATBOOK_ASSISTANT_TYPES.ATYPE_ID " +
		                "AND SATBOOK_ASSISTANTS.ASS_TYPE_ID='1' ";

		try{
    		JdbcTemplate jdt = new JdbcTemplate(super.getDataSource());
    		List<?> examInfoList = jdt.queryForList(select);
			Iterator<?> j = examInfoList.iterator();
    		while (j.hasNext()) {
    			ListOrderedMap data = (ListOrderedMap) j.next();

    			emailList.add(new org.apache.struts.util.LabelValueBean(data.get("ASS_EMAIL").toString(), data.get("ASS_EMAIL").toString()));

    		} // end while

    	} catch (Exception ex) {
    		throw new Exception("za.ac.unisa.lms.db.SakaiDAO.SatbookDAO: selectEmailList: Error occurred / "+ ex,ex);
    	} // end try

		return emailList;
	}
	
	public void selectVCBookingListForUsers() throws Exception{
		SatbookOracleDAO db2 = new SatbookOracleDAO();
		String bkngId="";
		String bkngHeading="";
		String bkngStartDate ="";
		String bkngStartTime = "";
		String bkngEndTime = "";
		String bkngTypeId="";
		String novellId	= "";
		String typeName = "";
		String emailHeading="";
		String emailBody = "";
		
		String query = "SELECT BKNG_ID, BKNG_HEADING, BKNG_LECT_NOVELLID," +
        "BKNG_LECT_TEL," +
        "TO_CHAR(BKNG_START,'DD-MON-YYYY') AS STARTTIME, "+
        "TO_CHAR(BKNG_START, 'HH24') AS STARTTIMEHH," +
        "TO_CHAR(BKNG_START, 'MI') AS STARTTIMEMM," +
        "TO_CHAR(BKNG_END,'DD-MON') AS ENDTIME, "+
        "TO_CHAR(BKNG_END, 'HH24') AS ENDTIMEHH," +
        "TO_CHAR(BKNG_END, 'MI') AS ENDTIMEMM," +
        //,  nvl(BKNGTYPE_ID,' ') AS TYPEID
        "BKNG_DESC, BKNGTYPE_ID AS TYPEID "+
        "FROM SATBOOK_BKNG_MAIN "+
        "WHERE TO_CHAR(BKNG_START,'YYYY-MM-DD') = TO_CHAR(sysdate+7,'YYYY-MM-DD') "+
        "AND BKNG_CONFIRMED = 'Y' "+
        "AND SYSTEM_ID = 2";
		
		try{
			JdbcTemplate jdt = new JdbcTemplate(super.getDataSource());
			List<?> bookingList = jdt.queryForList(query);
			Iterator<?> records = bookingList.iterator();
			while (records.hasNext() == true){
				ListOrderedMap data = (ListOrderedMap) records.next();
				bkngId = data.get("BKNG_ID").toString();
				bkngHeading = data.get("BKNG_HEADING").toString();
				bkngStartDate = data.get("STARTTIME").toString();
				bkngStartTime = data.get("STARTTIMEHH").toString() +":"+ data.get("STARTTIMEMM").toString();
				bkngEndTime = data.get("ENDTIMEHH").toString() +":"+ data.get("ENDTIMEMM").toString();
				bkngTypeId = data.get("TYPEID").toString();
				novellId = data.get("BKNG_LECT_NOVELLID").toString();
				
				//get type of booking
				query ="SELECT TYPE_NAME FROM SATBOOK_BKNG_TYPES WHERE TYPE_ID = "+ bkngTypeId;
				typeName = this.querySingleValue(query,"TYPE_NAME");
				
				
				ArrayList<String> venueList = getVenueNames(bkngId);
				ArrayList<?> emailList = db2.getEmailAddress(novellId.toUpperCase());
				
				
				emailHeading = 	"UNISA VC Booking: "+bkngHeading;
				emailBody =		"<html> "+
								"<body> "+
								"Please be informed that a Video Conferencing "+
								"booking will take place as follow: "+ 
								" <br><br> "+
								"Type of booking: " +typeName+ "<br>"+
								"Date:            " +bkngStartDate+ "<br>"+
								"Time: 			  " +bkngStartTime+ " - " +bkngEndTime+ "<br><br>"+
								"Venues:          " + venueList +"<br><br>"+
								"</body> "+
								"</html>";
				
				for (int i=0; i < emailList.size(); i++) {
					LabelValueBean email = (LabelValueBean) emailList.get(i);
					sendEmail(emailHeading,emailBody,email.getValue());
				}
			
			}//end while
		}catch (Exception e){
			throw new Exception("za.ac.unisa.lms.db.SatbookDAO: selectVCBookingListForUsers: Error occured / "+ e,e);
		}
	}
	
	public ArrayList<String> getVenueNames(String bookingId) throws Exception{
		ArrayList<String> venueList = new ArrayList<String>();
		String query =	"SELECT SBC.REGION_ID, SC.CLASSROOM_NAME FROM SATBOOK_BKNG_CLASSROOM SBC, SATBOOK_CLASSROOMS SC "+
						"WHERE SBC.BKNG_ID = "+bookingId+
						" AND SBC.REGION_ID = SC.CLASSROOM_ID"+
						" ORDER BY SC.CLASSROOM_NAME";
			
		try{
			JdbcTemplate jdt = new JdbcTemplate(this.getDataSource());
			List<?> list = jdt.queryForList(query);
			Iterator<?> j = list.iterator();
			while (j.hasNext()){
				ListOrderedMap data = (ListOrderedMap) j.next();
				venueList.add(data.get("CLASSROOM_NAME").toString());
			}//end while
		}catch (Exception e){
			throw new Exception("za.ac.unisa.lms.db.SatbookDAO: getVenueNames: Error occurred / "+ e,e);
		}
		return venueList;
	}	
	public ArrayList<String> getMaterials(String bookingId) throws Exception{
		ArrayList<String> listOfMaterials = new ArrayList<String>();
		String query = 	"SELECT MAT_ID, MATERIAL_NAME "+
						"FROM SATBOOK_BKNG_MATERIAL, SATBOOK_MATERIALS "+
						"WHERE  BKNG_ID ="+bookingId+
						" AND MAT_ID = MATERIAL_ID "+
						"ORDER BY MATERIAL_NAME";
		try{
			JdbcTemplate jdt = new JdbcTemplate(this.getDataSource());
			List<?> list = jdt.queryForList(query);
			Iterator<?> j = list.iterator();
			while (j.hasNext()){
				ListOrderedMap data = (ListOrderedMap) j.next();
				listOfMaterials.add(data.get("MATERIAL_NAME").toString());
			}//end while
		}catch (Exception e){
			throw new Exception("za.ac.unisa.lms.db.SatbookDAO: getMaterials: Error occurred / "+ e,e);
		}
		return listOfMaterials;
	}
	public List<List> selectVenuesPerSystem() throws Exception{
		
		ArrayList<String> classId = new ArrayList<String>();
		ArrayList<String> className = new ArrayList<String>();
		ArrayList<String> classContact = new ArrayList<String>();
		List<List> venueList = new ArrayList<List>(); 
			
		String query = 	"SELECT CLASSROOM_ID,CLASSROOM_NAME,CLASSROOM_CONTACT2 FROM SATBOOK_CLASSROOMS "+
						"WHERE SYSTEM_ID = 2";
		
		try{
			JdbcTemplate jdt = new JdbcTemplate(this.getDataSource());
			List<?> list = jdt.queryForList(query);
			Iterator<?> j = list.iterator();
			while (j.hasNext()){
				ListOrderedMap data = (ListOrderedMap) j.next();
				classId.add(data.get("CLASSROOM_ID").toString());
				className.add(data.get("CLASSROOM_NAME").toString());
				classContact.add(data.get("CLASSROOM_CONTACT2").toString());
			}
			venueList.add(classId);
			venueList.add(className);
			venueList.add(classContact);
		}catch(Exception e){
			throw new Exception("za.ac.unisa.lms.db.SatbookDAO: selectVenuesPerSystem: Error occurred / "+ e,e);
		}
		//return a list of classroom id, classroom name and contact
		return venueList;
	}
	
	public void selectVCBookingListForVenues(String classRoomId,String classRoomName,String email) throws Exception{
		
		SatbookOracleDAO db = new SatbookOracleDAO();
		ArrayList<String> venueNames = new ArrayList<String>();
		ArrayList<String> materials = new ArrayList<String>();
		String bookingTypeId= "";
		String bookingId = "";
		String lectContactDetails= "";
		String lectureNovellId = "";
		String bkngHeading = "";
		String bkngDesc = "";
		String bookingType = "";
		String emailHeading= "";
		String emailBody = "";
		String telNumber= "";
		String userName = "";
		String bkngStartDate= "";
		String bkngStartTime= "";
		String bkngEndTime = "";
		boolean recFound = false;
		
		emailHeading = 	"UNISA VC Booking Schedule";
		emailBody = 	"<html> <body> "+
						"Please be informed that the following Video Conference <br>"+
						"booking will take place at your venue:  <b>"+classRoomName+"</b>";
									
		String queryString1 = "SELECT BKNG_ID FROM SATBOOK_BKNG_CLASSROOM WHERE REGION_ID = "+classRoomId;
		
		try{
			JdbcTemplate jdt = new JdbcTemplate(this.getDataSource());
			List<?> bookingList = jdt.queryForList(queryString1);
			Iterator<?> i = bookingList.iterator();
			while (i.hasNext()){
				ListOrderedMap data = (ListOrderedMap) i.next();
				bookingId = data.get("BKNG_ID").toString();
				venueNames = this.getVenueNames(bookingId);
				materials = this.getMaterials(bookingId);
				String queryString2 = 	"SELECT BKNG_ID,BKNG_HEADING, BKNG_LECT_NOVELLID, BKNG_LECT_TEL, "+
										"TO_CHAR(BKNG_START,'DD-MON-YYYY') AS STARTTIME, "+
										"TO_CHAR(BKNG_START, 'HH24') AS STARTTIMEHH, "+
										"TO_CHAR(BKNG_START, 'MI') AS STARTTIMEMM, "+
										"TO_CHAR(BKNG_END, 'DD-MON') AS ENDTIME, "+
										"TO_CHAR(BKNG_END, 'HH24') AS ENDTIMEHH, "+
										"TO_CHAR(BKNG_END, 'MI') AS ENDTIMEMM, "+
										"BKNG_DESC,BKNGTYPE_ID AS TYPEID "+
										"FROM SATBOOK_BKNG_MAIN "+ 
										"WHERE BKNG_ID = "+bookingId+" AND TO_CHAR(BKNG_START,'YYYY-MM-DD') = TO_CHAR(sysdate+7,'YYYY-MM-DD') "+
										"AND BKNG_CONFIRMED = 'Y' AND SYSTEM_ID = 2";
				//check if record found
				if (this.querySingleValue(queryString2,"TYPEID").toString() == null || this.querySingleValue(queryString2,"TYPEID").toString() == ""){
					continue;
				}
				recFound = true;
				bkngHeading = this.querySingleValue(queryString2,"BKNG_HEADING");
				bkngDesc = this.querySingleValue(queryString2,"BKNG_DESC");
				bookingTypeId = this.querySingleValue(queryString2,"TYPEID");
				lectureNovellId = this.querySingleValue(queryString2,"BKNG_LECT_NOVELLID");
				bkngStartDate = this.querySingleValue(queryString2,"STARTTIME");
				bkngStartTime = this.querySingleValue(queryString2,"STARTTIMEHH")+":"+this.querySingleValue(queryString2,"STARTTIMEMM");
				bkngEndTime = this.querySingleValue(queryString2,"ENDTIMEHH")+":"+this.querySingleValue(queryString2,"ENDTIMEMM");
				lectContactDetails = db.getLecturerContactDetails(lectureNovellId);
				String queryString3 = "SELECT TYPE_NAME FROM SATBOOK_BKNG_TYPES WHERE TYPE_ID = "+bookingTypeId;
				
				bookingType = this.querySingleValue(queryString3,"TYPE_NAME");
				String tempLectDetails[] = lectContactDetails.split("#");
				userName = tempLectDetails[0];
				telNumber = tempLectDetails[1];
				emailBody +=	"<br><br>-------------------------------------------------------<br>"+
								"Heading:             "+bkngHeading+"<br>"+
								"Type of booking:     "+bookingType+"<br>"+
								"Date:                "+bkngStartDate+"<br>"+
								"Time:                "+bkngStartTime+" - "+bkngEndTime+"<br><br>"+
								"Booking placed by:   "+userName+"<br>"+
								"Tel:                 "+telNumber+
								"<br><br>"+
								"Description:         "+bkngDesc+"<br>"+
								"Materials:           "+materials+"<br>"+
								"Venues:              "+venueNames;
								
			}
			if (recFound == true){
				emailBody += "</body></html>";
				sendEmail(emailHeading,emailBody,email);	
			}	
		}catch (Exception e){
			throw new Exception("za.ac.unisa.lms.db.SatbookDAO: selectVCBookingListForVenues: Error occurred / "+ e,e);
		}

		
	}
		
	public void selectVenueBookingList() throws Exception{
		List<List> venueList = this.selectVenuesPerSystem();
		try {
			for (int i = 0; i < venueList.get(0).size();++i){
				this.selectVCBookingListForVenues(venueList.get(0).get(i).toString(),venueList.get(1).get(i).toString(),venueList.get(2).get(i).toString());
			}
		}catch(Exception e){
			throw new Exception("za.ac.unisa.lms.db.SatbookDAO: selectVeueBookingList: Error occurred / "+ e,e);
		}
	}
}
