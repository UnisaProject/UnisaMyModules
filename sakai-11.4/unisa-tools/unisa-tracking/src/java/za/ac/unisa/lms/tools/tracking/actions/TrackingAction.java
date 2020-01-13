package za.ac.unisa.lms.tools.tracking.actions;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.internet.InternetAddress;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.LookupDispatchAction;
import org.sakaiproject.component.cover.ComponentManager;
import org.sakaiproject.component.cover.ServerConfigurationService;
import org.sakaiproject.email.api.EmailService;
import org.sakaiproject.tool.api.Session;
import org.sakaiproject.tool.api.SessionManager;
import org.sakaiproject.user.api.User;

import za.ac.unisa.lms.tools.tracking.dao.KeyValue;
import za.ac.unisa.lms.tools.tracking.dao.TrackingDAO;
import za.ac.unisa.lms.tools.tracking.dao.WebServiceGateWay;
import za.ac.unisa.lms.tools.tracking.bo.Assignment;
import za.ac.unisa.lms.tools.tracking.bo.Consignment;
import za.ac.unisa.lms.tools.tracking.bo.Docket;
import za.ac.unisa.lms.tools.tracking.forms.DocketNumberDetails;
import za.ac.unisa.lms.tools.tracking.forms.TrackingForm;
import za.ac.unisa.lms.tools.tracking.utils.EmailValidator;

@SuppressWarnings({"unused", "rawtypes", "unchecked"})
public class TrackingAction extends LookupDispatchAction{

	private Log log = LogFactory.getLog(TrackingAction.class.getName());
	private SessionManager sessionManager;
	private EmailService emailService;
    WebServiceGateWay pGateWay;
    TrackingDAO pTrackingDAO;
    ArrayList displayDockets;
    ArrayList displayDctAssignments;
    ArrayList displayUniqueNumbers;
    private String BOOK_IN;
    private String BOOK_OUT;
    private String RESULT;
    private String SEARCH;
    private String REPORT;
    private String USER_SELECTION;
    String validationValues;
    String validationResult;
    int count;
    private String consignmentNumber;
    int conListCount;
    int docCount;
    int uniqueAssCount;
    String bookINErrorStatus;
    String bookOUTErrorStatus;
    String enteredUserAdresss;
    String CSDCode;
    String userID;
    User user;
    String host ;
    String webServiceURL ;
    String bookInOutURL ;
    private Pattern regexPattern;
	private Matcher regMatcher;
	List<Consignment> masterConsignmentList = new ArrayList<Consignment>();  
	List<Docket> masterDocketList = new ArrayList<Docket>();
	Map<String, String> mapUnqAssignments = new LinkedHashMap<String, String>();
    
	Map<String, String> processDocketList = new LinkedHashMap<String, String>();
	Map<String, String> processDocketAssignmentList = new LinkedHashMap<String, String>();
	Map<String, String> processAssignmentList = new LinkedHashMap<String, String>();
	
    public TrackingAction()
    {
        pGateWay = new WebServiceGateWay();
        pTrackingDAO = new TrackingDAO();
        displayDockets = new ArrayList();
        displayDctAssignments = new ArrayList();
        displayUniqueNumbers = new ArrayList();
        BOOK_IN = "checkin";
        BOOK_OUT = "checkout";
        RESULT = "result";
        SEARCH = "search";
        REPORT = "report";
        USER_SELECTION = "userselection";
        count = 0;
        conListCount=0;
        docCount = 0;
        uniqueAssCount = 0;
        bookINErrorStatus = null;
        bookOUTErrorStatus = null;
        
    }

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{ 
 
    	log.info("TrackingAction - execute");
    	TrackingForm pTrackingForm = (TrackingForm)form;
    	if(pTrackingForm.getHostName() == null) {
	    	pTrackingForm.setHostName(request.getHeader("Host"));
	    	log.info("TrackingAction - execute - Hostname "+pTrackingForm.getHostName());
	    	host   = pTrackingForm.getHostName();
	    	if("my.unisa.ac.za".equals(host)){
	    		webServiceURL = "http://www2.unisa.ac.za/aol/asp/sql_exec_report4.asp?export=XML&myid=CDTRACKING&ID=";
	    		bookInOutURL = "http://www2.unisa.ac.za/aol/asp/sql_exec_xml.asp?report=XML";
			}else if("mydev.int.unisa.ac.za".equals(host)) {
				webServiceURL = "http://stratusdev.unisa.ac.za/aol/asp/sql_exec_report4.asp?export=XML&myid=CDTRACKING&ID=";
				bookInOutURL = "http://stratusdev.unisa.ac.za/aol/asp/sql_exec_xml.asp?report=XML";
			}else if("myqa.int.unisa.ac.za".equals(host)){
				webServiceURL = "http://stratusqa.unisa.ac.za/aol/asp/sql_exec_report4.asp?export=XML&myid=CDTRACKING&ID=";
				bookInOutURL = "http://stratusqa.unisa.ac.za/aol/asp/sql_exec_xml.asp?report=XML";
			}else{
				webServiceURL = "http://stratusdev.unisa.ac.za/aol/asp/sql_exec_report4.asp?export=XML&myid=CDTRACKING&ID=";
				bookInOutURL = "http://stratusdev.unisa.ac.za/aol/asp/sql_exec_xml.asp?report=XML";
	
			}
			
    	}
        
    	if(request.getParameter("act") == null){
            return getUserInfo(mapping, form, request, response);
        }else{
            return super.execute(mapping, form, request, response);
        }
    }

    protected Map<String,String> getKeyMethodMap()
    {
        Map<String,String> trackingMap = new HashMap<String,String>();
        
        //Buttons
        trackingMap.put("button.back", "back");
        trackingMap.put("button.clear", "clear");
        trackingMap.put("button.report", "report");
        trackingMap.put("button.searchDetail", "searchDetail");
        trackingMap.put("button.addCon", "validateConsignmentList");
        
        //Destination Addresses
        trackingMap.put("displayCollegeInformation", "displayCollegeInformation");
        trackingMap.put("displaySchoolInformation", "displaySchoolInformation");
        trackingMap.put("displayDepartmentInformation", "displayDepartmentInformation");
        trackingMap.put("displayModuleInformation", "displayModuleInformation");
        trackingMap.put("displayProvinceInformation", "displayProvinceInformation");
        trackingMap.put("displayRegionalOfficeInformation", "displayRegionalOfficeInformation");
        trackingMap.put("displayBuildingInformation", "displayBuildingInformation");
        trackingMap.put("displayUserAddress", "displayUserAddress");
        trackingMap.put("getSavedAddress", "getSavedAddress");
        trackingMap.put("displayUserList", "displayUserList");
        trackingMap.put("report", "report");
        trackingMap.put("reportPDF", "reportPDF");
        
        trackingMap.put("searchDetail","searchDetail");
        
        trackingMap.put("getUserInfo", "getUserInfo");
        trackingMap.put("retrieveCSDInformation", "retrieveCSDInformation");

        //Input Validations
        trackingMap.put("validateConsignmentList", "validateConsignmentList");
        trackingMap.put("validateDocketNumber", "validateDocketNumber");
        trackingMap.put("validateStudentUniqueNumber", "validateStudentUniqueNumber");
        
        //Processing Book IN/OUT
        trackingMap.put("processInput", "processInput");
        return trackingMap;
    }



    //Get Consignment List, Cover Dockets and Assignments for search and results pages only

	public String displayConsignmentDetails (ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response, String outSelect) throws Exception {
		TrackingForm pTrackingForm = (TrackingForm)form;
    	ActionMessages messages = new ActionMessages();
    	String consignmentNumber = "";
    	int showConginmentInfo = 593166;
 	   	int showUniqueAssignments = 156154;
 	   	
 	   log.info("TrackingAction - displayConsignmentDetails - **************************************************************");
 	   	
    	try{
    		 
    		if (pTrackingForm.isSearch()){
    			 consignmentNumber = pTrackingForm.getSearchString();
    		}else{
    			consignmentNumber = pTrackingForm.getDisplayShipListNumber();
    		}
    		if(outSelect.equalsIgnoreCase("Result")){
    			showConginmentInfo = 889309; //697460;
         	   	showUniqueAssignments = 333995;
         	   log.info("TrackingAction - displayConsignmentDetails - RESULT - consignmentNumber="+consignmentNumber+", outselect="+outSelect+", showConginmentInfo="+showConginmentInfo+", showUniqueAssignments="+showUniqueAssignments);
    		}else{
    			log.info("TrackingAction - displayConsignmentDetails -SEARCH - consignmentNumber="+consignmentNumber+", outselect="+outSelect+", showConginmentInfo="+showConginmentInfo+", showUniqueAssignments="+showUniqueAssignments);
    		}
            
     	   	String userSelection = "";
	   		if (pTrackingForm.getSearchRadio()!= null){
	   			userSelection = "";
	   		}else{
	   			userSelection = pTrackingForm.getUserSelection().toUpperCase();
	   		}
	   		log.info("TrackingAction - displayConsignmentDetails - userSelection="+userSelection);
	   		//Get Cover Dockets from Database/Web Service
	   		log.info("TrackingAction - displayConsignmentDetails - **************************************************************");
     	   	log.info("TrackingAction - displayConsignmentDetails - (showConsignmentInfo) - consignmentNumber="+consignmentNumber+", UserSelection="+userSelection.toUpperCase());
            ArrayList getValues = pGateWay.showConsignmentInfo(webServiceURL ,showConginmentInfo, consignmentNumber,"", "value1", "value2", "value3");
            Iterator it = getValues.iterator();
            String errMsgDct="";
            String errMsgStu="";
            int dispDctCounter=0;
            String checkDct="";
            if(it.hasNext()){
	            while(it.hasNext()){
	    			KeyValue test1 = (KeyValue) it.next();
	    			if(test1.getValue1().equals("No records returned")){
	    				errMsgDct = " Docket Numbers ";
	    				log.info("TrackingAction - displayConsignmentDetails  - Docket Numbers: No records returned");
	    				log.info("TrackingAction - displayConsignmentDetails - **************************************************************");
	    			 }else{
	    				 DocketNumberDetails details = new DocketNumberDetails();
	    				 details.setDocketNumber(test1.getValue1());
	    				 if (!checkDct.equals(test1.getValue1())){
	    					 dispDctCounter++;
	    				 }
	    				 details.setDocketID(dispDctCounter);
	    				 checkDct = test1.getValue1();
	    				 log.info("TrackingAction - displayConsignmentDetails  - Docket Numbers Added="+dispDctCounter+"----"+test1.getValue1());
	    				 log.info("TrackingAction - displayConsignmentDetails - **************************************************************");
	    				 //Get Assignments linked to Dockets
	    				 log.info("TrackingAction - displayConsignmentDetails  - Docket Assignments: Successful records returned");
	    				 details.setStudentNumber(test1.getValue2());
	    				 details.setUniqueAssignmentNumber(test1.getValue3());
	    				 log.info("TrackingAction - displayConsignmentDetails  - Docket Assignments="+test1.getValue2()+ "----" + test1.getValue3());
	    				 displayDockets.add(details);
	    				 log.info("TrackingAction - displayConsignmentDetails - **************************************************************");
	                 }	
	    		}
            }else{
            	errMsgDct = " Docket Numbers ";
				log.info("TrackingAction - displayConsignmentDetails  - Docket Numbers: No records returned");
				log.info("TrackingAction - displayConsignmentDetails - **************************************************************");
            }
            
            //Getting Unique Assignments
            log.info("TrackingAction - displayConsignmentDetails - **************************************************************");
            log.info("TrackingAction - displayConsignmentDetails (showUniqueAssignmentListInfo) - consignmentNumber="+consignmentNumber+", UserSelection="+userSelection.toUpperCase());
            ArrayList uniqueNumbers = pGateWay.showUniqueAssignmentListInfo(webServiceURL ,showUniqueAssignments, consignmentNumber,userSelection.toUpperCase(),"value1", "value2", "value3");
            Iterator uniqueNumber = uniqueNumbers.iterator();
            if(uniqueNumber.hasNext()){
            	while(uniqueNumber.hasNext()){
	    			KeyValue test12 = (KeyValue) uniqueNumber.next();
	    			if(test12.getValue1().equals("No records returned")){
	    				errMsgStu = " Unique Assignments ";
	    				log.info("TrackingAction - displayConsignmentDetails  - Unique Assignments: No records returned");
	    				log.info("TrackingAction - displayConsignmentDetails - **************************************************************");
	   			 	}else{
	   			 		log.info("TrackingAction - displayConsignmentDetails  - Unique Assignments: Successful records returned");
	   			 		DocketNumberDetails stuDetails = new DocketNumberDetails();
	   			 		stuDetails.setStudentNumber(test12.getValue1());
	   			 		log.info("TrackingAction - displayConsignmentDetails  - Unique Assignments="+test12.getValue1()+ "----" + test12.getValue2());
	   			 		stuDetails.setUniqueAssignmentNumber(test12.getValue2());
	   			 		//stuDetails.setRemove1(true);
	   			 		displayUniqueNumbers.add(stuDetails);
	   			 		log.info("TrackingAction - displayConsignmentDetails - **************************************************************");
	   			 	}
	            }
            }else{
            	errMsgStu = " Unique Assignments ";
				log.info("TrackingAction - displayConsignmentDetails  - Unique Assignments: No records returned");
				log.info("TrackingAction - displayConsignmentDetails - **************************************************************");
            }
            log.info("TrackingAction - displayConsignmentDetails - Setting up Form Variables for Report Display");
            pTrackingForm.setDisplayDocketsForConsignment(displayDockets);
            pTrackingForm.setDisplayDctAssignmentsForConsignment(displayDctAssignments);
            pTrackingForm.setDisplayUniqueNumbersForConsignment(displayUniqueNumbers);
            /*
            log.info("TrackingAction - displayConsignmentDetails  - Error Checking - Dockets: "+errMsgDct+", Student: "+errMsgStu);
            log.info("TrackingAction - displayConsignmentDetails  - Error Checking Sizes - Dockets: "+displayDockets.size()+", Student: "+displayUniqueNumbers.size());
            
            if(displayDockets.size()== 0 && displayUniqueNumbers.size()== 0){ 
	            if(!errMsgDct.equals("") || !errMsgStu.equals("")){
	            	log.info("TrackingAction - displayConsignmentDetails  - Error Checking");
	            	String newErrorMsg = "Consignment list " + consignmentNumber + " doesn't contain any ";
	            	StringBuilder newError;
	            	if(!errMsgDct.equals("") && (errMsgStu.equals("") && displayUniqueNumbers.isEmpty())){
	            		log.info("TrackingAction - displayConsignmentDetails  - Docket Error");
	            		newError = (new StringBuilder()).append(newErrorMsg).append(errMsgDct);
	            	}else if(errMsgDct.equals("") && !errMsgStu.equals("")){
	            		log.info("TrackingAction - displayConsignmentDetails  - Student Error");
	            		newError = (new StringBuilder()).append(newErrorMsg).append(errMsgStu);
	            	}else{
	            		log.info("TrackingAction - displayConsignmentDetails  - Docket & Student Error");
	            		newError = (new StringBuilder()).append(newErrorMsg).append(errMsgDct).append("or").append(errMsgStu);
	            	}
	            	log.info("TrackingAction - displayConsignmentDetails  - Final Error: "+newError);
	            	messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("message.generalmessage", newError));
	                addErrors(request, messages);
	            }
            }
            */
            log.info("TrackingAction - displayConsignmentDetails  - Final Setting DisplayForConsignment");
            
            String processType =  pTrackingForm.getUserSelection().toUpperCase();
	    	
            log.info("TrackingAction - displayConsignmentDetails  - Final processType="+processType);
	    	if (processType.equalsIgnoreCase("OUT")){
	        	try{
	        		log.info("TrackingAction - displayConsignmentDetails - **************************************************************");
	        		boolean checkEmail = false;
	        		log.info("TrackingAction - displayConsignmentDetails - Check if Destination is Person or Manual with Email address, then send email");
			        if (pTrackingForm.getUsers() != null && !pTrackingForm.getUsers().equals("") && !pTrackingForm.getUsers().equals("0")){
			        	checkEmail = true;
			        }else if (pTrackingForm.getCsdmUsers() != null && !pTrackingForm.getCsdmUsers().equals("") && !pTrackingForm.getCsdmUsers().equals("0")){
			        	checkEmail = true;
			        }else if (pTrackingForm.getBuildingUsers() != null && !pTrackingForm.getBuildingUsers().equals("") && !pTrackingForm.getBuildingUsers().equals("0")){
			        	checkEmail = true;
			        }else if (pTrackingForm.getAddressType().equalsIgnoreCase("MANUAL") && pTrackingForm.getDisplayEmail() != null && !pTrackingForm.getDisplayEmail().equals("") ){
			        	checkEmail = true;
			        }
			        if (checkEmail){
		        		log.info("TrackingAction - displayConsignmentDetails  - Is User or valid Email Address,  Send Notification Email");
		        		String emailResult = sendNotificationToUser(pTrackingForm);
		        		log.info("TrackingAction - displayConsignmentDetails  - Email Result="+emailResult);
			        }
	        		log.info("TrackingAction - displayConsignmentDetails - **************************************************************");
	        	}catch(Exception ex){
	                messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("message.generalmessage", "Email notification to destination user failed. Please try again."));
	                addErrors(request, messages);
	            }
            }
        	
        }catch(Exception ex){
            messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("message.generalmessage", "Web Server not responding. Please try again."));
            addErrors(request, messages);
        }
        return null;
    }

    public ActionForward report(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{
    	return mapping.findForward("result");
    }
    
    
    public ActionForward reportPDF(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{
    	log.info("TrackingAction - reportPDF - **************************************************************");

    	//System.out.println("TrackingAction - reportPDF");
    	boolean isData = false;
    	String outColumn = "";
    	String outConsignHeader = "";
    	String outConsignColumn = "";
    	String outConsignBody = "";
		StringBuilder stringContent = new StringBuilder();
		JSONObject reportObj = new JSONObject();
		HttpSession session = request.getSession(true);
		TrackingForm pTrackingForm = (TrackingForm)form;
    	
		try{
			//Building PDFMaker Strings
			stringContent = (new StringBuilder().append(stringContent)
							.append("{")
							.append("pageSize: \'A4\',")
							.append("pageOrientation: \'portrait\',")
							.append("pageMargins: [ 40, 40, 40, 40 ],")
							.append("footer: function(page, pages) { return { columns: [ \'Signature: ___________________ Date: __/__/20__\', { alignment: \'right\', text: [ { text: \'Page\'},{ text: page.toString()}, \' of \', { text: pages.toString()}] } ], margin: [40, 0] }; },"));
							//.append("footer: function(page, pages) { return { columns: [ \'Unisa Assignment Tracking\', { alignment: \'right\', text: [ { text: \'Page\'},{ text: page.toString()}, \' of \', { text: pages.toString()}] } ], margin: [40, 0] }; },"));

		    		
			//PDF Header
			stringContent = (new StringBuilder().append(stringContent).append("content: ["));
			
			if (pTrackingForm.getUserSelection().equalsIgnoreCase("IN")){
				//stringContent = (new StringBuilder().append(stringContent).append("{text: \'RECEIPT: "+pTrackingForm.getDisplayShipListNumber()+"\', fontSize: 22, bold: true},").append("{ alignment: \'right\', image: unisaImageDataUrl, fit: [100, 100] }").append(",").append("{text: '\\n\\n'}"));
				stringContent = (new StringBuilder().append(stringContent).append("{ columns: [ { width: '*', text: \'RECEIPT: "+pTrackingForm.getDisplayShipListNumber()+"\', fontSize: 18, bold: true}, { width: '*', alignment: \'right\', image: unisaImageDataUrl, fit:[150, 150]},]}"));
			}else{
				//stringContent = (new StringBuilder().append(stringContent).append("{text: \'CONSIGNMENT LIST: "+pTrackingForm.getDisplayShipListNumber()+"\', fontSize: 22, bold: true},").append(" { image: unisaImageDataUrl, fit: [100, 100] }").append(",").append("{text: \'\\n\\n\'}"));
				stringContent = (new StringBuilder().append(stringContent).append("{ columns: [ { width: '*', text: \'CONSIGNMENT LIST: "+pTrackingForm.getDisplayShipListNumber()+"\', fontSize: 18, bold: true}, { width: '*', alignment: \'right\', image: unisaImageDataUrl, fit:[150, 150]},]}"));
				outColumn = ", 50";
		    	outConsignHeader = ", { text: \'Present\', bold: true, alignment: \'center\' }";
		    	//outConsignColumn = ", { canvas: [ { type: 'rect', w: 20,	h: 20, } ] 	}";
		    	outConsignColumn = ", \' \'";
		    	outConsignBody = ", \' \'";
			}
			stringContent = (new StringBuilder().append(stringContent).append(",{text: \'Booked "+pTrackingForm.getUserSelection()+" on "+pTrackingForm.getDisplayShipListDate()+"\\n\', fontSize: 15}"));
			stringContent = (new StringBuilder().append(stringContent).append(",{text: \'By User: "+pTrackingForm.getNovelUserId()+"\\n\\n\', fontSize: 15}"));
			log.info("TrackingAction - reportPDF - Header stringContent="+stringContent);
			log.info("TrackingAction - reportPDF - **************************************************************");
			
			//PDF Address:
			String tempAddress1="";
			String tempAddress2="";
			String tempAddress3="";
			String tempAddress4="";
			String tempPostal="";
			if (pTrackingForm.getDisplayAddress1() != null && !pTrackingForm.getDisplayAddress1().equals("") && !pTrackingForm.getDisplayAddress1().equals(" ")){
				tempAddress1 = "[ {text: \'"+pTrackingForm.getDisplayAddress1()+"\'}] ";
				if (pTrackingForm.getDisplayAddress2() != null && !pTrackingForm.getDisplayAddress2().equals("") && !pTrackingForm.getDisplayAddress2().equals(" ")){
					tempAddress2 = ", [ {text: \'"+pTrackingForm.getDisplayAddress2()+"\'}] ";
				}
				if (pTrackingForm.getDisplayAddress3() != null && !pTrackingForm.getDisplayAddress3().equals("") && !pTrackingForm.getDisplayAddress3().equals(" ")){
					tempAddress3 = ", [ {text: \'"+pTrackingForm.getDisplayAddress3()+"\'}] ";
				}
				if (pTrackingForm.getDisplayAddress4() != null && !pTrackingForm.getDisplayAddress4().equals("") && !pTrackingForm.getDisplayAddress4().equals(" ")){
					tempAddress4 = ", [ {text: \'"+pTrackingForm.getDisplayAddress4()+"\'}] ";
				}
				if (pTrackingForm.getDisplayPostal() != null && !pTrackingForm.getDisplayPostal().equals("") && !pTrackingForm.getDisplayPostal().equals(" ") && !pTrackingForm.getDisplayPostal().equals("0")){
					tempPostal   = ", [ {text: \'"+pTrackingForm.getDisplayPostal()+"\'}] ";
				}
				if (pTrackingForm.getDisplayEmail() != null && !pTrackingForm.getDisplayEmail().equals("") && !pTrackingForm.getDisplayEmail().equals(" ")){
					tempPostal   = ", [ {text: \'"+pTrackingForm.getDisplayEmail()+"\'}] ";
				}
	          			
				stringContent = (new StringBuilder().append(stringContent)
								.append(",{table: { ")
								.append("headerRows: 1, ")
								.append("widths: [ \'*\' ],")
								.append("body: [ ")
								.append("[ { text: \'Destination Address\', bold: true}], ")
		    		          	.append(tempAddress1)
		    		          	.append(tempAddress2)
		    		          	.append(tempAddress3)
		    		          	.append(tempAddress4)
		    		          	.append(tempPostal)
		    		          	.append("]")
		    		          	.append("},")
		    		          	.append("layout: {")
		    		          	.append("hLineColor: function(i, node) {")
		    		          	.append("return (i === 0 || i === node.table.body.length) ? 'gray' : 'gray';")
		    		          	.append("},")
		    		          	.append("vLineColor: function(i, node) {")
		    		          	.append("return (i === 0 || i === node.table.widths.length) ? 'gray' : 'gray';")
		    		          	.append("}")
		    		          	.append("}")
		    		          	.append("}"));
			} //End Address
			log.info("TrackingAction - reportPDF - Header + Address stringContent="+stringContent);
			log.info("TrackingAction - reportPDF - **************************************************************");
			
			//PDF Cover Dockets
			log.info("TrackingAction - reportPDF - **************************************************************");
			log.info("TrackingAction - reportPDF - getDisplayDocketsForConsignment");
			if (pTrackingForm.getDisplayDocketsForConsignment().size() >0){
				isData = true;
				stringContent = (new StringBuilder().append(stringContent).append(",{text: \'\\n\'} ")
						.append(",{table: { ")
						.append("headerRows: 1, ")
						.append("widths: [ \'*\', \'auto\', 120, 120 "+outColumn+"],")
						.append("body: [ ")
						.append("[ { text: \'Cover Dockets\', bold: true, colSpan: 2 }, \'\', { text: \'Student Number\', bold: true, alignment: \'center\' }, { text: \'Unique Assignment\', bold: true, alignment: \'center\' } "+outConsignHeader+" ]"));
				
				Iterator<DocketNumberDetails> it = pTrackingForm.getDisplayDocketsForConsignment().iterator();
				if (it.hasNext()){
					log.info("TrackingAction - reportPDF - **************************************************************");
					log.info("TrackingAction - reportPDF - Found Cover Dockets");
				     String tempDocketNumber = "";
				     while (it.hasNext()){
				    	 DocketNumberDetails detail = (DocketNumberDetails) it.next();
				    	 
				    	 log.info("TrackingAction - reportPDF - DocketNumber="+detail.getDocketNumber());
				    	 log.info("TrackingAction - reportPDF - DocketStudent="+detail.getStudentNumber());
				    	 log.info("TrackingAction - reportPDF - DocketAssignment="+detail.getUniqueAssignmentNumber());
				    	 
				    	 if (!tempDocketNumber.equalsIgnoreCase(detail.getDocketNumber())){
				    		 stringContent = (new StringBuilder().append(stringContent).append(",").append("[ {text: \'"+detail.getDocketNumber()+"\', colSpan: 2}, \'\', {text: \'"+detail.getStudentNumber()+"\', alignment: \'center\'},{text: \'"+detail.getUniqueAssignmentNumber()+"\', alignment: \'center\'} "+outConsignColumn+" ]"));
				    	 }else{
				    		 stringContent = (new StringBuilder().append(stringContent).append(",").append("[ {text: \' \', colSpan: 2}, \'\', {text: \'"+detail.getStudentNumber()+"\', alignment: \'center\'},{text: \'"+detail.getUniqueAssignmentNumber()+"\', alignment: \'center\'} "+outConsignColumn+" ]"));
				    	 }
				    	 tempDocketNumber = detail.getDocketNumber();
				     }
				     log.info("TrackingAction - reportPDF - **************************************************************");
				}else{
					log.info("TrackingAction - reportPDF - **************************************************************");
					log.info("TrackingAction - reportPDF - No Cover Dockets to display");
					log.info("TrackingAction - reportPDF - **************************************************************");
				}
				stringContent = (new StringBuilder().append(stringContent)
								.append("]")
								.append("},")
		    		          	.append("layout: {")
		    		          	.append("hLineColor: function(i, node) {")
		    		          	.append("return (i === 0 || i === node.table.body.length) ? 'gray' : 'gray';")
		    		          	.append("},")
		    		          	.append("vLineColor: function(i, node) {")
		    		          	.append("return (i === 0 || i === node.table.widths.length) ? 'gray' : 'gray';")
		    		          	.append("}")
		    		          	.append("}")
		    		          	.append("}"));
			}
			log.info("TrackingAction - reportPDF - Header + Address + Cover Docket stringContent="+stringContent);
			log.info("TrackingAction - reportPDF - **************************************************************");
			
			//PDF Cover Dockets
			log.info("TrackingAction - reportPDF - **************************************************************");
			log.info("TrackingAction - reportPDF - DisplayUniqueNumbersForConsignment");
			if (pTrackingForm.getDisplayUniqueNumbersForConsignment().size() >0){
				isData = true;
				stringContent = (new StringBuilder().append(stringContent).append(",{text: \'\\n\'} ")
						.append(",{table: { ")
						.append("headerRows: 1, ")
						.append("widths: [ \'*\', \'auto\', 120, 120 "+outColumn+"],")
						.append("body: [ ")
						.append("[ { text: \'Single Assignments\', bold: true, colSpan: 2 }, \'\', { text: \'Student Number\', bold: true, alignment: \'center\' }, { text: \'Unique Assignment\', bold: true, alignment: \'center\' } "+outConsignHeader+" ] "));
				Iterator<DocketNumberDetails> it = pTrackingForm.getDisplayUniqueNumbersForConsignment().iterator();
				if (it.hasNext()){
					log.info("TrackingAction - reportPDF - **************************************************************");
					log.info("TrackingAction - reportPDF - Found Assignments");
				     while (it.hasNext()){
				    	 DocketNumberDetails detail = (DocketNumberDetails) it.next();

				    	 log.info("TrackingAction - reportPDF - UniqueStudent="+detail.getStudentNumber());
				    	 log.info("TrackingAction - reportPDF - UniqueAssignment="+detail.getUniqueAssignmentNumber());

				    	 stringContent = (new StringBuilder().append(stringContent).append(",").append("[ { text:\' \', colSpan: 2 }, \'\', {text: \'"+detail.getStudentNumber()+"\', alignment: \'center\'},{text: \'"+detail.getUniqueAssignmentNumber()+"\', alignment: \'center\'} "+outConsignColumn+"]"));
				     }
					log.info("TrackingAction - reportPDF - **************************************************************");
				}else{
					log.info("TrackingAction - reportPDF - **************************************************************");
					log.info("TrackingAction - reportPDF - No Unique Assignments to display");
					log.info("TrackingAction - reportPDF - **************************************************************");
				}
				stringContent = (new StringBuilder().append(stringContent)
								.append("]")
								.append("},")
		    		          	.append("layout: {")
		    		          	.append("hLineColor: function(i, node) {")
		    		          	.append("return (i === 0 || i === node.table.body.length) ? 'gray' : 'gray';")
		    		          	.append("},")
		    		          	.append("vLineColor: function(i, node) {")
		    		          	.append("return (i === 0 || i === node.table.widths.length) ? 'gray' : 'gray';")
		    		          	.append("}")
		    		          	.append("}")
		    		          	.append("}"));
			}
			stringContent = (new StringBuilder().append(stringContent).append(",{text: \'\\n\'} ").append("]").append("}"));

			
			log.info("TrackingAction - reportPDF - Header + Address + Cover Docket + Assignment stringContent="+stringContent);
			log.info("TrackingAction - reportPDF - **************************************************************");
			
        }catch(Exception e){
        	reportObj.put("Error","An Error occurred while parsing the report data. / " +e +" / " +e.getMessage());
		}
		
		if (!isData){
			reportObj.put("Empty","No Data available for the PDF.");
		}else{
			reportObj.put("Report",stringContent.toString());
		}

    	// Convert the map to json
    	PrintWriter out = response.getWriter();
       	JSONObject jsonObject = JSONObject.fromObject(reportObj);
       	log.info("TrackingAction - reportPDF - Final - **************************************************************");
       	log.info("TrackingAction - reportPDF - Final - jsonObject="+jsonObject.toString());
       	log.info("TrackingAction - reportPDF - Final - **************************************************************");
       	out.write(jsonObject.toString());
       	out.flush();

    	return null; //Returning null to prevent struts from interfering with ajax/json
    }
    	
    public ActionForward displayUserList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{
		log.info("TrackingAction - displayUserList - **************************************************************");
		
		int keyCounter = 0;
		JSONObject userObj = new JSONObject();
	   	Map<String, String> mapUsers = new LinkedHashMap<String, String>();
	   	ArrayList<KeyValue> getValues = null;

		HttpSession session = request.getSession(true);
		TrackingForm pTrackingForm = (TrackingForm)form;
		
		String searchCheck = pTrackingForm.getSearchCheck().trim();
		log.info("TrackingAction - displayUserList - SearchCheck="+searchCheck);
		log.info("TrackingAction - displayUserList - **************************************************************");
        try{
            int webserviceId = 18428;
            if (pTrackingForm.getSearchCheck().equalsIgnoreCase("Surname") || pTrackingForm.getSearchCheck().equalsIgnoreCase("PersNo")){
	            if(searchCheck.equalsIgnoreCase("Surname") && pTrackingForm.getSurname() != null && pTrackingForm.getSurname().length() != 0){
	            	log.info("TrackingAction - displayUserList - getUserList - Surname="+pTrackingForm.getSurname());
	               	getValues = pGateWay.getUserList(webServiceURL ,webserviceId, "value1", "value2", "value3", "value4", "value5", "value6", pTrackingForm.getSurname());
	               	log.info("TrackingAction - displayUserList - getUserList - Surname="+pTrackingForm.getSurname()+" - After Webservice");
	            }else if (searchCheck.equalsIgnoreCase("PersNo") && pTrackingForm.getSearchPersNo() != null && pTrackingForm.getSearchPersNo().length() != 0){
	            	log.info("TrackingAction - displayUserList - getUserList - PersNo="+pTrackingForm.getSearchPersNo());
	               	getValues = pGateWay.getUserList1(webServiceURL ,webserviceId, "value1", "value2", "value3", "value4", "value5", "value6", pTrackingForm.getSearchPersNo());
	            }else{
	            	userObj.put("Empty", "No Search criteria defined. Please Enter a partial or full Surname");
	            }
            }else if (pTrackingForm.getSearchCheck().equalsIgnoreCase("College")){
	            if(pTrackingForm.getCollege() != null || pTrackingForm.getCollege().equals("-1")){
	            	log.info("TrackingAction - displayUserList - College="+pTrackingForm.getCollege());
	            	String collegeID = "";
	            	String schoolID = "";
	            	String departmentID = "";
	            	String moduleID = "";
	            	if (pTrackingForm.getCollege() != null && !pTrackingForm.getCollege().equals("-1")){
	            		collegeID = pTrackingForm.getCollege();
	            	}
	            	if (pTrackingForm.getSchool() != null && !pTrackingForm.getSchool().equals("-1")){
	            		schoolID = pTrackingForm.getSchool();
	            	}
	            	if (pTrackingForm.getDepartment() != null && !pTrackingForm.getDepartment().equals("-1")){
	            		departmentID = pTrackingForm.getDepartment();
	            	}
	            	if (pTrackingForm.getModule() != null && !pTrackingForm.getModule().equals("-1")){
	            		moduleID = pTrackingForm.getModule();
	            	}
	            	log.info("TrackingAction - displayUserList - getUserList2 - collegeID="+collegeID+", schoolID="+schoolID+", departmentID="+departmentID+"; moduleID="+moduleID);
	               	getValues = pGateWay.getUserList2(webServiceURL ,webserviceId, "value1", "value2", "value3", "value4", "value5", "value6", collegeID, schoolID, departmentID, moduleID);
	            }else{
	               	userObj.put("Empty", "No Search criteria defined. Please select at least a College");
            	}
            }else if (pTrackingForm.getSearchCheck().equalsIgnoreCase("Province")){
            	if(pTrackingForm.getProvince() != null || pTrackingForm.getProvince().equals("-1")){
	            	log.info("TrackingAction - displayUserList - Province="+pTrackingForm.getProvince());
	            	String provinceID = "";
	            	String regionID = "";
	            	if (!pTrackingForm.getProvince().equals("-1")){
	            		provinceID = pTrackingForm.getProvince();
	            	}
	            	if (!pTrackingForm.getRegion().equals("-1")){
	            		regionID = pTrackingForm.getRegion();
	            	}
	            	if (regionID.equals("") && provinceID.equals("")){
	            		log.info("TrackingAction - displayUserList - No College Selected");
		    			userObj.put("Empty", "No Search criteria defined. Please select a Province or Regional Office");
	            	}
	            	log.info("TrackingAction - displayUserList - getUserList3 - provinceID="+provinceID+", regionID="+regionID);
	               	getValues = pGateWay.getUserList3(webServiceURL ,webserviceId, "value1", "value2", "value3", "value4", "value5", "value6", provinceID, regionID);
            	}else{
            		userObj.put("Empty", "No Search criteria defined. Please select at least a Province");
            	}
            }else if (pTrackingForm.getSearchCheck().equalsIgnoreCase("Building")){
            	if (pTrackingForm.getBuilding() != null){
	            	log.info("TrackingAction - displayUserList - getUserList4 - Building="+pTrackingForm.getBuilding());
	            	String buildingName = pTrackingForm.getBuilding().replaceAll("_", " ");
	            	getValues = pGateWay.getUserList4(webServiceURL ,webserviceId, "value1", "value2", "value3", "value4", "value5", "value6", buildingName);
            	}else{
            		userObj.put("Empty", "No Search criteria defined. Please select a Building");
            	}
            }else{
        		userObj.put("Empty", "No Search criteria defined.");
            }
            
            log.info("TrackingAction - displayUserList - Getting Here - Testing getValues");
            if (!getValues.isEmpty()){
	    		Iterator<KeyValue> it = getValues.iterator();
	    		//log.info("TrackingAction - displayUserList - Iterator");
	    		
	    		if (it.hasNext()){
	    			
		    		while(it.hasNext()){
		    			//log.info("TrackingAction - displayUserList - Result Iterator Has Next");
		    			String userKey = "";
		    			StringBuilder userValue = new StringBuilder();
		    			
						KeyValue test = (KeyValue) it.next();
						//log.info("TrackingAction - displayUserList - Result StaffNumber="+test.getValue1());
						userKey = test.getValue1().trim().toString();
	
						if(test.getValue2() != null &&  !test.getValue2().isEmpty()){
							if(!test.getValue2().equals("null")){
								//log.info("TrackingAction - displayUserList - Result Surname="+test.getValue2());
								userValue = (new StringBuilder()).append(userValue).append(test.getValue2().trim().toString());
							}else{
								userValue = (new StringBuilder()).append(userValue).append("");
							}
						}else{
							userValue = (new StringBuilder()).append(userValue).append("");
						}
						if(test.getValue3() != null &&  !test.getValue3().isEmpty()){
							if(!test.getValue3().equals("null")){
								//log.info("TrackingAction - displayUserList - Result Initials="+test.getValue3());
								userValue = (new StringBuilder()).append(userValue).append("~").append(test.getValue3().trim().toString());
							}else{
								userValue = (new StringBuilder()).append(userValue).append("~").append("");
							}
						}else{
							userValue = (new StringBuilder()).append(userValue).append("~").append("");
						}
						if(test.getValue4() != null &&  !test.getValue4().isEmpty()){
							if(!test.getValue4().equals("null")){
								//log.info("TrackingAction - displayUserList - Result Title="+test.getValue4());
								userValue = (new StringBuilder()).append(userValue).append("~").append(test.getValue4().trim().toString());
							}else{
								userValue = (new StringBuilder()).append(userValue).append("~").append("");
							}
						}else{
							userValue = (new StringBuilder()).append(userValue).append("~").append("");
						}
						if(test.getValue5() != null &&  !test.getValue5().isEmpty()){
							if(!test.getValue5().equals("null")){
								//log.info("TrackingAction - displayUserList - Result FirstNames="+test.getValue5());
								userValue = (new StringBuilder()).append(userValue).append("~").append(test.getValue5().trim().toString());
							}else{
								userValue = (new StringBuilder()).append(userValue).append("~").append("");
							}
						}else{
							userValue = (new StringBuilder()).append(userValue).append("~").append("");
						}
	
		    			mapUsers.put(Integer.toString(keyCounter), userKey+"~"+userValue);
						keyCounter++;
						//log.info("TrackingAction - displayUserList - Result **************************************************************");
		    		}
		    		
		    		userObj.put("Users",mapUsers);
	    		}else{
	    			log.info("TrackingAction - displayUserList - No Person Found");
	    			userObj.put("Empty", "No User(s) Found");
	    		}
            }else{
    			log.info("TrackingAction - displayUserList - No Person Found");
    			userObj.put("Empty", "No User(s) Found. Please select College, School, Department, Module, Building or search for Person directly or check spelling.");
    		}
    		
        }catch(Exception ex){
        	log.info("TrackingAction - displayUserList - The getUserList Web Service Failed!");
        	userObj.put("Error","The getUserList Web Service Failed! Please try again / <br/>"+ex);
        }

    	// Convert the map to json
    	PrintWriter out = response.getWriter();
       	JSONObject jsonObject = JSONObject.fromObject(userObj);
       	log.info("TrackingAction - displayUserList - Final - **************************************************************");
       	log.info("TrackingAction - displayUserList - Final - jsonObject="+jsonObject.toString());
       	log.info("TrackingAction - displayUserList - Final - **************************************************************");
       	out.write(jsonObject.toString());
       	out.flush();

    	return null; //Returning null to prevent struts from interfering with ajax/json
    }

    public ActionForward displayUserAddress(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{

    	log.info("TrackingAction - displayUserAddress - Personnel/Staff");
        int addressId = 892254;
        TrackingForm pTrackingForm = (TrackingForm)form;
        pTrackingForm.setUserAddress("");
        
        int keyCounter = 0;
    	JSONObject addressObj = new JSONObject();
       	Map<String, String> mapAddresses = new LinkedHashMap<String, String>();
       	String user = pTrackingForm.getUser();
        log.info("TrackingAction - displayUserAddress - Person - USER ADDRESS="+user);

        	try{
        		ArrayList<KeyValue> getValues = pGateWay.getSelectedUserAddress(webServiceURL ,addressId, user, "value1", "value2", "value3", "value4", "value5", "value6", "Personnel");
        		Iterator<KeyValue> it = getValues.iterator();
        		
        		if (it.hasNext()){
        			String addressKey = "";
	    			StringBuilder addressValue = new StringBuilder();
	    			
					KeyValue test = (KeyValue) it.next();
					if (test.getValue1() != null && !test.getValue1().equals("")){
						//log.info("TrackingAction - displayUserAddress - StaffNumber="+test.getValue());
						//addressKey = test.getValue().trim().toString();
	
						if(test.getValue1() != null &&  !test.getValue1().isEmpty()){
							if(!test.getValue1().equals("null")){
								log.info("TrackingAction - displayUserAddress - userAddress1="+test.getValue1());
								addressValue = (new StringBuilder()).append(addressValue).append(test.getValue1().trim().toString());
							}else{
								addressValue = (new StringBuilder()).append(addressValue).append("");
							}
						}else{
							addressValue = (new StringBuilder()).append(addressValue).append("");
						}
						if(test.getValue2() != null &&  !test.getValue2().isEmpty()){
							if(!test.getValue2().equals("null")){
								log.info("TrackingAction - displayUserAddress - userAddress2="+test.getValue2());
								addressValue = (new StringBuilder()).append(addressValue).append("~").append(test.getValue2().trim().toString());
							}else{
								addressValue = (new StringBuilder()).append(addressValue).append("~").append("");
							}
						}else{
							addressValue = (new StringBuilder()).append(addressValue).append("~").append("");
						}
						if(test.getValue3() != null &&  !test.getValue3().isEmpty()){
							if(!test.getValue3().equals("null")){
								log.info("TrackingAction - displayUserAddress - userEmail="+test.getValue3());
								addressValue = (new StringBuilder()).append(addressValue).append("~").append(test.getValue3().trim().toString());
							}else{
								addressValue = (new StringBuilder()).append(addressValue).append("~").append("");
							}
						}else{
							addressValue = (new StringBuilder()).append(addressValue).append("~").append("");
						}
						
						mapAddresses.put(Integer.toString(keyCounter), addressValue.toString());

					}else{
						addressObj.put("Empty","Selected user's address not Found. Please select another user or enter address manually.");
					}
					log.info("TrackingAction - displayUserAddress - **************************************************************");

        		}else{
        			addressObj.put("Empty","Selected user's address not Found. Please select another user or enter address manually.");
        		}
        		addressObj.put("Address",mapAddresses);
            }catch(Exception ex){
            	addressObj.put("Error","The displayUserAddress Web Service Failed! Please try again / <br/>"+ex);
            }

    		// Convert the map to json
    		PrintWriter out = response.getWriter();
           	JSONObject jsonObject = JSONObject.fromObject(addressObj);
           	log.info("TrackingAction - displayUserAddress - Final - **************************************************************");
           	log.info("TrackingAction - displayUserAddress - Final - jsonObject="+jsonObject.toString());
           	log.info("TrackingAction - displayUserAddress - Final - **************************************************************");
           	out.write(jsonObject.toString());
           	out.flush();
           	
           	return null; //Returning null to prevent struts from interfering with ajax/json
    }
        
    public ActionForward getSavedAddress(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{
    	
        int addressId = 367199;
        TrackingForm pTrackingForm = (TrackingForm)form;
        
        int keyCounter = 0;
    	JSONObject addressObj = new JSONObject();
       	Map<String, String> mapAddresses = new LinkedHashMap<String, String>();

        String FromUserID = pTrackingForm.getNovelUserId().toUpperCase();
        log.info("TrackingAction - getSavedAddress - FromUserID="+FromUserID);

            try{
        		ArrayList<KeyValue> getValues = pGateWay.getSavedUserAddress(webServiceURL ,addressId, FromUserID, "value1", "value2", "value3", "value4", "value5", "value6", "value7", "value8");
        		Iterator<KeyValue> it = getValues.iterator();
        		
        		if (it.hasNext()){
        			String addressKey = "";
	    			StringBuilder addressValue = new StringBuilder();
	    			
					KeyValue test = (KeyValue) it.next();
					log.info("TrackingAction - getSavedAddress - FromUserID="+test.getValue2());
					addressKey = test.getValue2().trim().toString();

					if(test.getValue3() != null &&  !test.getValue3().isEmpty()){
						if(!test.getValue3().equals("null")){
							log.info("TrackingAction - getSavedAddress - userAddress1="+test.getValue3());
							addressValue = (new StringBuilder()).append(addressValue).append(test.getValue3().trim().toString());
						}else{
							addressValue = (new StringBuilder()).append(addressValue).append("");
						}
					}else{
						addressValue = (new StringBuilder()).append(addressValue).append("");
					}
					if(test.getValue4() != null &&  !test.getValue4().isEmpty()){
						if(!test.getValue4().equals("null")){
							log.info("TrackingAction - getSavedAddress - userAddress2="+test.getValue4());
							addressValue = (new StringBuilder()).append(addressValue).append("~").append(test.getValue4().trim().toString());
						}else{
							addressValue = (new StringBuilder()).append(addressValue).append("~").append("");
						}
					}else{
						addressValue = (new StringBuilder()).append(addressValue).append("~").append("");
					}
					if(test.getValue5() != null &&  !test.getValue5().isEmpty()){
						if(!test.getValue5().equals("null")){
							log.info("TrackingAction - getSavedAddress - userAddress3="+test.getValue5());
							addressValue = (new StringBuilder()).append(addressValue).append("~").append(test.getValue5().trim().toString());
						}else{
							addressValue = (new StringBuilder()).append(addressValue).append("~").append("");
						}
					}else{
						addressValue = (new StringBuilder()).append(addressValue).append("~").append("");
					}
					if(test.getValue6() != null &&  !test.getValue6().isEmpty()){
						if(!test.getValue6().equals("null")){
							log.info("TrackingAction - getSavedAddress - userAddress4="+test.getValue6());
							addressValue = (new StringBuilder()).append(addressValue).append("~").append(test.getValue6().trim().toString());
						}else{
							addressValue = (new StringBuilder()).append(addressValue).append("~").append("");
						}
					}else{
						addressValue = (new StringBuilder()).append(addressValue).append("~").append("");
					}
					if(test.getValue7() != null &&  !test.getValue7().isEmpty()){
						if(!test.getValue7().equals("null")){
							log.info("TrackingAction - getSavedAddress - userPostal="+test.getValue7());
							addressValue = (new StringBuilder()).append(addressValue).append("~").append(test.getValue7().trim().toString());
						}else{
							addressValue = (new StringBuilder()).append(addressValue).append("~").append("");
						}
					}else{
						addressValue = (new StringBuilder()).append(addressValue).append("~").append("");
					}
					if(test.getValue8() != null &&  !test.getValue8().isEmpty()){
						if(!test.getValue8().equals("null")){
							log.info("TrackingAction - getSavedAddress - userEmail="+test.getValue8());
							addressValue = (new StringBuilder()).append(addressValue).append("~").append(test.getValue8().trim().toString());
						}else{
							addressValue = (new StringBuilder()).append(addressValue).append("~").append("");
						}
					}else{
						addressValue = (new StringBuilder()).append(addressValue).append("~").append("");
					}
					//mapAddresses.put(Integer.toString(keyCounter), addressKey+"~"+addressValue);
					mapAddresses.put(Integer.toString(keyCounter), addressValue.toString());
					log.info("TrackingAction - getSavedAddress - **************************************************************");

        		}else{
        			addressObj.put("Empty","Selected user Address not Found");
        		}
        		addressObj.put("Address",mapAddresses);
            }catch(Exception ex){
            	addressObj.put("Error","The getSavedAddress Web Service Failed! Please try again / <br/>"+ex);
            }


		// Convert the map to json
		PrintWriter out = response.getWriter();
       	JSONObject jsonObject = JSONObject.fromObject(addressObj);
       	log.info("TrackingAction - getSavedAddress - Final - **************************************************************");
       	log.info("TrackingAction - getSavedAddress - Final - jsonObject="+jsonObject.toString());
       	log.info("TrackingAction - getSavedAddress - Final - **************************************************************");
       	out.write(jsonObject.toString());
       	out.flush();
       	
       	return null; //Returning null to prevent struts from interfering with ajax/json
    }

    //Main Method to build Arrays of Shipping Lists (Parents) with siblings : Attached Dockets (with linked Assignments) and Unique Assignments)
    public ActionForward validateConsignmentList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)throws Exception {
   
		/** Edmund Enumerate through all parameters received
	 	 * @return **/
	   	//getAllRequestParamaters(request, response);

    	JSONObject conObj = new JSONObject();
    	JSONObject dctObj = new JSONObject();
    	Consignment consignment = new Consignment();
    	List<Assignment> assignmentList = new ArrayList<Assignment>();
       	Map<String, String> mapConsignments = new LinkedHashMap<String, String>();
       	Map<String, String> mapRemoveDockets = new LinkedHashMap<String, String>();
       	Map<String, String> mapRemoveAssignments = new LinkedHashMap<String, String>();
		Map<String, String> mapConDctAssignments = new LinkedHashMap<String, String>();
		Map<String, String> mapConUnqAssignments = new LinkedHashMap<String, String>();
	   	String errorMsg = "";
	   	
		log.info("TrackingAction - validateConsignmentList");
		if(conListCount == 0){
			//log.info("conListCount"+conListCount);
			conListCount++;
		   //log.info("conListCount"+conListCount);
		}
		Session currentSession = sessionManager.getCurrentSession();
		HttpSession session = request.getSession(true);
	
		TrackingForm pTrackingForm = (TrackingForm)form;
    	ActionMessages messages = new ActionMessages();

    	log.info("TrackingAction - validateConsignmentList - Form="+pTrackingForm.getEnteredConsignmentNumber());

		try{
			consignmentNumber = pTrackingForm.getEnteredConsignmentNumber().trim();
            if(pTrackingForm.getEnteredConsignmentNumber() != null && pTrackingForm.getEnteredConsignmentNumber().length() > 0 && isNumberValid(pTrackingForm.getEnteredConsignmentNumber().trim())){
            	pTrackingForm.setConsignmentListNumber(pTrackingForm.getEnteredConsignmentNumber().trim());
                int shipListCreatedDate = 121868;      	
                //Edmund - Removed Status, have to check with Harry if In/Out matters for Consignment List
                //ArrayList getDate = pGateWay.showConsignmentListInfo(webServiceURL ,shipListCreatedDate, consignmentNumber,pTrackingForm.getUserSelection().toUpperCase(), "value1", "value4");
                ArrayList getDate = pGateWay.showConsignmentListInfo(webServiceURL ,shipListCreatedDate, consignmentNumber,"", "value1", "value4");
                for(Iterator date = getDate.iterator(); date.hasNext();){
                    KeyValue conKey = (KeyValue)date.next();
                    log.info("TrackingAction - validateConsignmentList - conKey: " + conKey.getValue());
                    if(conKey.getValue().contains("Consignment list number invalid")){
                    	errorMsg = "Consignment List "+consignmentNumber+" is invalid.<br/>Please Enter a valid Consignment List Number";
                    }else{
                    	//Test if Consignment List has been added already 
                    	for(Consignment existConsignment : masterConsignmentList) { 
                    		log.info("TrackingAction - validateConsignmentList - *******************************************************");
                    		log.info("TrackingAction - validateConsignmentList - Existing Consignment Number : " +existConsignment.getConsignmentNumber());
                    		log.info("TrackingAction - validateConsignmentList - Existing Consignment Date : " +existConsignment.getDate());			
                    		log.info("TrackingAction - validateConsignmentList - *******************************************************");
                    		if (consignmentNumber.equals(existConsignment.getConsignmentNumber())){
                    			log.info("TrackingAction - validateConsignmentList - Consignment list number already added.");
                    			errorMsg = "Consignment List "+consignmentNumber+" has already been added.<br/>Please Enter a different Consignment List Number.";
                    		}
                    	}
                    	
                    	/**
                         * Creating detail for Consignment Lists.
                         */
                    	//Adding to main list to keep track in beans
                		consignment.setConsignmentNumber(consignmentNumber);
                		consignment.setDate(conKey.getValue());
                    	//Adding to temp map to test/validate
                    	mapConsignments.put(consignmentNumber,conKey.getValue());
						/**Debug**/
                    	log.info("TrackingAction - validateConsignmentList - **************************************************************");
						Set<String> keys = mapConsignments.keySet();
				        for(String k:keys){
				            log.info("TrackingAction - validateConsignmentList - Map Put mapConsignment=" +k+" -- "+mapConsignments.get(k));
				        }
				        log.info("TrackingAction - validateConsignmentList - **************************************************************");     		
                		
				        //Adding to Main Map
				        conObj.put(consignmentNumber,conKey.getValue());
				        
                    	//Get dockets and assignments for this shipping list
                    	int showConginmentInfo = 593166;
                 	   	int showUniqueAssignments = 156154;
                 	   	//Edmund - Removed Status, have to check with Harry if In/Out matters for Consignment List
                        //ArrayList getValues = pGateWay.showConsignmentListInfo(webServiceURL ,showConginmentInfo, consignmentNumber,pTrackingForm.getUserSelection().toUpperCase() ,"id", "value1");
                 	   	ArrayList getValues = pGateWay.showConsignmentListInfo(webServiceURL ,showConginmentInfo, consignmentNumber,"" ,"id", "value1");
                        Iterator dct = getValues.iterator();
                        String errMsgDct="";
                        String errMsgStu="";
                        int conDctCount=0;
                        if(dct.hasNext()){
                        	List<Docket> dockets = new ArrayList<Docket>();
            	            while(dct.hasNext()){
            	    			KeyValue dctKey = (KeyValue) dct.next();
            	    			if(dctKey.getValue().equals("No records returned")){
            	    				errMsgDct = " Docket Numbers ";
            	    				log.info("TrackingAction - validateConsignmentList - Docket Numbers: No records returned");
            	    			}else{
            	    				conDctCount++;
            	    				log.info("TrackingAction - validateConsignmentList - Docket Numbers: Successful records returned="+dctKey.getValue());
            	    				//Add Docket to main list to check for duplicates
                                	Docket docketListCon = populateDocket(Integer.toString(conDctCount), dctKey.getValue());
                                	//Check if Cover Docket has been added manually, if so, add to new map to remove from web page
                                	if (duplicateCheckConDockets(dctKey.getValue())){
                                		log.info("TrackingAction - validateConsignmentList - Docket Number: "+dctKey.getValue()+" already manually added, adding to remove map");
                                		mapRemoveDockets.put(Integer.toString(conDctCount), dctKey.getValue());
                                	}
            	    				//Retrieve all Assignments linked to the Cover Docket
       	    						LinkedHashMap<String, String> mapDctAssignments = new LinkedHashMap<String, String>();
       								int docketAssignmentID = 702449 ;
       							    log.info("TrackingAction - validateConsignmentList - Retrieve linked Assignments for docketNumber="+dctKey.getValue());
       							    ArrayList dctAssignments = pGateWay.getDocketAssignments(webServiceURL, docketAssignmentID ,dctKey.getValue(), "value1", "value2");
       							    Iterator dctAssign = dctAssignments.iterator();
       							    int conDctAssCounter = 0;
       							    if(dctAssign.hasNext()){
       							    	List<Assignment> dctAssignList = new ArrayList<Assignment>();
       							    	while(dctAssign.hasNext()){
       							    		KeyValue test = (KeyValue) dctAssign.next();
       										if(test.getValue().equals("No records returned")){
       											log.info("TrackingAction - validateConsignmentList - No linked Assignments returned");
       										}else{
       											conDctAssCounter++;
       											log.info("TrackingAction - validateConsignmentList  - Linked Assignments: Successful records returned - Student="+test.getKey()+", Assignment="+test.getValue());
       											// Generate a Docket-Assignment map
       											mapConDctAssignments.put(String.valueOf(conDctAssCounter), test.getKey()+"~"+test.getValue());
       		                                	//Check if Assignment has been added manually, if so, add to new map to remove from web page
       		                                	if (duplicateCheckConAssignment(test.getKey(), test.getValue())){
       		                                		log.info("TrackingAction - validateConsignmentList - Assignment: "+test.getKey()+"~"+test.getValue()+" already manually added, adding to remove map");
       		                                		mapRemoveAssignments.put(String.valueOf(conDctAssCounter), test.getKey()+"~"+test.getValue());
       		                                	}
       											//Add to main list to check for duplicates
       											dctAssignList.add(populateAssignment(test.getKey(),test.getValue()));
       											/**Debug**/
       											log.info("TrackingAction - validateConsignmentList - **************************************************************");
       											Set<String> DctAssKeys = mapDctAssignments.keySet();
       									        for(String k:DctAssKeys){
       									            log.info("Map Put mapDctAssignments=" +k+" -- "+mapDctAssignments.get(k));
       									        }
       									        log.info("TrackingAction - validateConsignmentList - **************************************************************");
       										}
       							    	}
       							    	docketListCon.setAssignments(dctAssignList);
       							    }else{
       							    	log.info("TrackingAction - validateConsignmentList - No linked Assignments returned");
       							    }
            	    				log.info("TrackingAction - validateConsignmentList - Docket Numbers: " + dctKey.getValue());
            	    				dctObj.put(dctKey.getValue(), mapConDctAssignments);
            	    				if(!mapRemoveAssignments.isEmpty()){
    	            	            	log.info("TrackingAction - validateConsignmentList - (mapRemoveAssignments - docket) Adding Assignment to be removed from Unique Map on screen");
    	            	            	conObj.put("RemoveAssignments", mapRemoveAssignments);
    	            	            }
            	    				dockets.add(docketListCon);
            	                 }	
            	    		}
            	            //consignment.setDockets(docketList);
            	            //Add dockets to main consignment object
            	            if (conDctCount>0){
	            	            conObj.put("Dockets", dctObj);
	            	            if(!mapRemoveDockets.isEmpty()){
	            	            	conObj.put("RemoveDockets", mapRemoveDockets);
	            	            }
	            	            consignment.setDockets(dockets);
            	            }
                        }else{
                        	errMsgDct = " Docket Numbers ";
            				log.info("TrackingAction - validateConsignmentList - Docket Numbers: No records returned");
                        }
                        
                        //Edmund - Removed Status, have to check with Harry if In/Out matters for Consignment List
                        //ArrayList uniqueNumbers = pGateWay.showUniqueAssignmentListInfo(webServiceURL ,showUniqueAssignments, consignmentNumber,pTrackingForm.getUserSelection().toUpperCase() ,"id", "value1", "value2");
                        log.info("TrackingAction - validateConsignmentList - Unique Assignments");
                        ArrayList uniqueNumbers = pGateWay.showUniqueAssignmentListInfo(webServiceURL ,showUniqueAssignments, consignmentNumber,"" ,"value1", "value2", "value3");
                        Iterator uniqueNumber = uniqueNumbers.iterator();
                    	int conAssCount=0;
                        if(uniqueNumber.hasNext()){
                        	List<Assignment> conUnqAssignmentList = new ArrayList<Assignment>();
                        	while(uniqueNumber.hasNext()){
            	    			KeyValue assKey = (KeyValue) uniqueNumber.next();
            	    			if(assKey.getValue1().equals("No records returned")){
            	    				errMsgStu = " Unique Assignments ";
            	    				log.info("TrackingAction - validateConsignmentList - Unique Assignments: No records returned");
            	   			 	}else{
            	   			 		log.info("TrackingAction - validateConsignmentList - Unique Assignments: Successful records returned");
            	   			 		log.info("TrackingAction - validateConsignmentList - Unique Assignments: " + assKey.getValue1()+"~"+assKey.getValue2());
	                                //Check for Duplicate Unique Assignments, which may already exist in Unique Cover Dockets added manually
            	   			 		if (duplicateCheckDctAssignment(assKey.getValue1(), assKey.getValue2())){
            	   			 			log.info("TrackingAction - validateConsignmentList - Unique Assignment: "+assKey.getValue1()+"~"+assKey.getValue2()+" already in manually added Cover Docket, therefor not adding to list");
            	   			 		}else{
            	   			 			conAssCount++;
            	   			 			// Generate a map of Unique Assignments
	            	   			 		mapConUnqAssignments.put(Integer.toString(conAssCount), (String) assKey.getValue1()+"~"+(String) assKey.getValue2());
	                                	//Check if Assignment has been added manually, if so, add to new map to remove from web page
	                                	if (duplicateCheckConAssignment(assKey.getValue1(), assKey.getValue2())){
	                                		log.info("TrackingAction - validateConsignmentList - Assignment: "+assKey.getValue1()+"~"+assKey.getValue2()+" already manually added, adding to remove map");
	                                		mapRemoveAssignments.put(Integer.toString(conAssCount), assKey.getValue1()+"~"+assKey.getValue2());
	                                	}
	            	   			 		//Add Unique Assignment on Consignment list to main list for duplicate checking
	            	   			 		conUnqAssignmentList.add(populateAssignment(Integer.toString(conAssCount), (String) assKey.getValue1()+"~"+(String) assKey.getValue2()));
	            	   			 		/**Debug**/
	            	   			 		log.info("TrackingAction - validateConsignmentList - **************************************************************");
	            	   			 		Set<String> unqConAssKeys = mapConUnqAssignments.keySet();
								        for(String k:unqConAssKeys){
								            log.info("Map Put mapConUnqAssignments=" +k+" -- "+mapConUnqAssignments.get(k));
								        }
            	   			 		}
							        log.info("TrackingAction - validateConsignmentList - **************************************************************");
            	   			 	}
            	            }
                        	//Add Assignments to main Consignment map
                        	if (conAssCount > 0){
	                        	conObj.put("Assignments", mapConUnqAssignments);
	            	            if(!mapRemoveAssignments.isEmpty()){
	            	            	log.info("TrackingAction - validateConsignmentList - (mapRemoveAssignments - unique) Remove Assignment from Main Unique Map");
	            	            	Set<String> unqAssKeys = mapUnqAssignments.keySet();
							        for(String k:unqAssKeys){
							        	log.info("TrackingAction - validateConsignmentList - (mapRemoveAssignments - unique - K) "+k+"~"+mapUnqAssignments.get(k));
							        	Set<String> unqRemAssKeys = mapRemoveAssignments.keySet();
								        for(String r:unqRemAssKeys){
								        	log.info("TrackingAction - validateConsignmentList - (mapRemoveAssignments - unique - R) "+r+"~"+mapRemoveAssignments.get(r));
								        	if (mapUnqAssignments.get(k).equals(mapRemoveAssignments.get(r))){
								        		// remove value for key 2
								        		log.info("TrackingAction - validateConsignmentList - (mapRemoveAssignments from mapUnqAssignments: "+k+"~"+mapUnqAssignments.get(k));
								        		mapUnqAssignments.remove(k);
								        	}
								        }
							        }
		                        	log.info("TrackingAction - validateConsignmentList - **************************************************************");
	            	            	conObj.put("RemoveAssignments", mapRemoveAssignments);
	            	            }
	                        	consignment.setAssignments(conUnqAssignmentList);
                        	}
                        }else{
                        	errMsgStu = " Unique Assignments ";
            				log.info("TrackingAction - validateConsignmentList - Unique Assignments: No records returned");
                        }
                        
                        log.info("TrackingAction - validateConsignmentList - Error Checking - Dockets: "+errMsgDct+", Student: "+errMsgStu);
                        //log.info("TrackingAction - validateConsignmentList - Error Checking Sizes - Dockets: "+pTrackingForm.getShipList().getDockets().size()+", Assignments: "+pTrackingForm.getShipList().getAssignments().size());
                        
                        if(conDctCount== 0 && conAssCount== 0){ 
            	            if(!errMsgDct.equals("") || !errMsgStu.equals("")){
            	            	log.info("TrackingAction - validateConsignmentList - Error Checking");
            	            	String newErrorMsg = "Consignment list " + consignmentNumber + " doesn't contain any ";
            	            	StringBuilder newError;
            	            	if(!errMsgDct.equals("") && (errMsgStu.equals("") && mapConUnqAssignments.isEmpty())){
            	            		log.info("TrackingAction - validateConsignmentList - Docket Error");
            	            		newError = (new StringBuilder()).append(newErrorMsg).append(errMsgDct);
            	            	}else if(errMsgDct.equals("") && !errMsgStu.equals("")){
            	            		log.info("TrackingAction - validateConsignmentList - Student Error");
            	            		newError = (new StringBuilder()).append(newErrorMsg).append(errMsgStu);
            	            	}else{
            	            		log.info("TrackingAction - validateConsignmentList - Docket & Student Error");
            	            		newError = (new StringBuilder()).append(newErrorMsg).append(errMsgDct).append("or").append(errMsgStu);
            	            	}
            	            	newError = (new StringBuilder()).append(newErrorMsg).append(" <br>Cover Dockets or Assignments may be booked in or out on a later Consignment List.");
            	            	log.info("TrackingAction - validateConsignmentList - Final Error: "+newError.toString());
            	            	errorMsg = newError.toString();
            	            }
                        }
                        log.info("TrackingAction - validateConsignmentList - Before Building Consignment Collection");
                		//Add Dockets and Assignments to Consignment List
                        
                    		log.info("TrackingAction - validateConsignmentList - After Building Consignment Collection");
                    }
            		
            		//xxx consignment.setAssignments(assignmentList);
            		masterConsignmentList.add(consignment);
                }
            }else{
            	errorMsg = "Consignment List "+consignmentNumber+" is invalid.<br/>Please Enter a valid Consignment List Number";

                addErrors(request, messages);
            }
        }catch(Exception e){
        	errorMsg = "Web Server not responding. Please try again. / " +e +" / " +e.getMessage();

        }
		
		if(mapRemoveDockets != null ) {
			Set<String> keys = mapRemoveDockets.keySet();
	        for(String dct:keys){
	        	log.info("TrackingAction - validateConsignmentList - *******************************************************");
	        	log.info("TrackingAction - validateConsignmentList - Docket to Remove from Unique List: " +dct+" -- "+mapRemoveDockets.get(dct));
	        	log.info("TrackingAction - validateConsignmentList - *******************************************************");
	        }
		}
		if(mapRemoveAssignments != null ) {
			Set<String> keys = mapRemoveAssignments.keySet();
	        for(String ass:keys){
	        	log.info("TrackingAction - validateConsignmentList - *******************************************************");
	        	log.info("TrackingAction - validateConsignmentList - Assignment to Remove from Unique List: " +ass+" -- "+mapRemoveAssignments.get(ass));
	        	log.info("TrackingAction - validateConsignmentList - *******************************************************");
	        }
		}
		
		//Test final consignment list data for duplicate tests
		for(Consignment consignTest : masterConsignmentList) {
			log.info("TrackingAction - validateConsignmentList - *******************************************************");
			log.info("TrackingAction - validateConsignmentList - Consignment Test - Consignment Number : " +consignTest.getConsignmentNumber());
			log.info("TrackingAction - validateConsignmentList - Consignment Test - Consignment Date : " +consignTest.getDate());			
			log.info("TrackingAction - validateConsignmentList - *******************************************************");
			
			if(consignTest.getDockets() != null ) {
				for(Docket docket : consignTest.getDockets()) {
					log.info("TrackingAction - validateConsignmentList - Consignment Test - Docket Id : " +docket.getDocketID());
					log.info("TrackingAction - validateConsignmentList - Consignment Test - Docket Number : " +docket.getDocketNumber());
					log.info("TrackingAction - validateConsignmentList - *******************************************************");
					if(docket.getAssignments() != null ) {
						for(Assignment assignment : docket.getAssignments()) {
							log.info("TrackingAction - validateConsignmentList - Consignment Test - Dct Student Number : " +assignment.getStudentNumber());
							log.info("TrackingAction - validateConsignmentList - Consignment Test - Dct Unique Assignment : " +assignment.getUniqueAssignmentNumber());
							log.info("TrackingAction - validateConsignmentList - *******************************************************");
						}
					}
				}
				log.info("TrackingAction - validateConsignmentList - *******************************************************");
			}
			
			if(consignTest.getAssignments() != null) {
				for(Assignment assignment : consignTest.getAssignments()) {
					log.info("TrackingAction - validateConsignmentList - Consignment Test - Unique Student Number : " +assignment.getStudentNumber());
					log.info("TrackingAction - validateConsignmentList - Consignment Test - Unique Assignment Number : " +assignment.getUniqueAssignmentNumber());
					log.info("TrackingAction - validateConsignmentList - *******************************************************");
				}
			}
			log.info("TrackingAction - validateConsignmentList - *******************************************************");
		}

		// Convert the map to json
		PrintWriter out = response.getWriter();
        if (errorMsg != null && !errorMsg.equals("")){
        	Map<String, String> mapErrors = new LinkedHashMap<String, String>();
        	mapErrors.put("Error",errorMsg);
        	JSONObject jsonObject = JSONObject.fromObject(mapErrors);
        	out.write(jsonObject.toString());
        	out.flush();
        }else{
        	JSONObject jsonObject = JSONObject.fromObject(conObj);
        	log.info("TrackingAction - validateConsignmentList - Final - **************************************************************");
        	log.info("TrackingAction - validateConsignmentList - Final - jsonObject="+jsonObject.toString());
        	log.info("TrackingAction - validateConsignmentList - Final - **************************************************************");
        	out.write(jsonObject.toString());
        	out.flush();
        }

		return null; //Returning null to prevent struts from interfering with ajax/json
    }
    
    public ActionForward validateDocketNumber(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		/** Edmund Enumerate through all parameters received
	  	 * @return **/
	    //getAllRequestParamaters(request, response);
	    
	    String errorMsg="";
	    Map<String, String> mapRemoveAssignments = new LinkedHashMap<String, String>();
	    Map<String, String> mapRemoveConUnqAssignments = new LinkedHashMap<String, String>();
    	StringWriter outObj = new StringWriter();
    	JSONObject dctObj = new JSONObject();
    	ActionMessages messages = new ActionMessages();
    	TrackingForm pTrackingForm = (TrackingForm) form;
    	
    	log.info("TrackingAction - validateDocketNumber");
    	if(docCount == 0){
    		//log.info("docCount"+docCount);
    	   docCount++;
    	   //log.info("docCount"+docCount);
    	}
    	Session currentSession = sessionManager.getCurrentSession();
    	HttpSession session = request.getSession(true);
    	log.info("Docket number test="+pTrackingForm.getDocketNumber());

    		   try {
    		      
    			   if(pTrackingForm.getDocketNumber() != null && !pTrackingForm.getDocketNumber().trim().equals("") && isNumberValid(pTrackingForm.getDocketNumber().trim())) {
    				   int docketNumberID = 118648 ;
    				   validationValues = pGateWay.docketNumberValidationCheck(webServiceURL ,docketNumberID,Integer.parseInt(pTrackingForm.getDocketNumber().trim()));
    				   //log.info("Method Calling 2.....");
    				   //Check if Docket already in Consignment or Unique Cover Docket List
    	               if (duplicateCheckDockets(pTrackingForm.getDocketNumber().trim())){
    	                	errorMsg = "The Cover Docket "+pTrackingForm.getDocketNumber().trim()+" has already been added. <br/>Please check Consignment lists and Cover Dockets";
    	               }else{
		    				if(docCount < 49) {
		    					if(validationValues.contains("Docket number found")) {
		    						boolean dctError = false;
		    						log.info("TrackingAction - validateDocketNumber - Docket number found");
		    						docCount++;
		    						//Retrieve all Assignments linked to the Cover Docket
		    						LinkedHashMap<String, String> mapDctAssignments = new LinkedHashMap<String, String>();
		    						//Add to main Cover Docket List for duplicate check
									Docket unqDockets = populateDocket(String.valueOf(docCount), pTrackingForm.getDocketNumber().trim());
									int docketAssignmentID = 702449 ;
								    log.info("TrackingAction - validateDocketNumber - Retrieve linked Assignments for docketNumber="+pTrackingForm.getDocketNumber());
								    ArrayList dctAssignments = pGateWay.getDocketAssignments(webServiceURL, docketAssignmentID ,pTrackingForm.getDocketNumber().trim(), "value1", "value2");
								    Iterator dctAssign = dctAssignments.iterator();
								    int assCounter = 0;
								    if(dctAssign.hasNext()){					    	
								    	List<Assignment> uniqueDctAssignList = new ArrayList<Assignment>();
								    	while(dctAssign.hasNext()){
								    		KeyValue test = (KeyValue) dctAssign.next();
											if(test.getValue().equals("No records returned")){
												log.info("TrackingAction - validateDocketNumber - No linked Assignments returned");
											}else{
												assCounter++;
												log.info("TrackingAction - validateDocketNumber  - Linked Assignments: Successful records returned - Student="+test.getKey().trim()+", Assignment="+test.getValue().trim());
												// Generate a Docket-Assignment map
												mapDctAssignments.put(String.valueOf(assCounter), test.getKey().trim()+"~"+test.getValue().trim());
												//Check if Assignment is a Unique Assignment in the Consignment List, if so, add to new map to remove from web page
												if (duplicateCheckConUniqueAssignment(test.getKey().trim(), test.getValue().trim())){
       		                                		log.info("TrackingAction - validateDocketNumber - (mapRemoveConUnqAssignments) Assignment: "+test.getKey().trim()+"~"+test.getValue().trim()+" already in Consignment List, adding to remove map");
       		                                		mapRemoveConUnqAssignments.put(String.valueOf(assCounter), test.getKey().trim()+"~"+test.getValue().trim());
       		                                	}
												//Check if Assignment has been added manually, if so, add to new map to remove from web page
												if (duplicateCheckUniqueAssignment(test.getKey().trim(), test.getValue().trim())){
       		                                		log.info("TrackingAction - validateDocketNumber - (duplicateCheckUniqueAssignment) Assignment: "+test.getKey().trim()+"~"+test.getValue().trim()+" already manually added, adding to remove map");
       		                                		mapRemoveAssignments.put(String.valueOf(assCounter), test.getKey().trim()+"~"+test.getValue().trim());
       		                                	}
												//Add to main docket list for duplicate check
												uniqueDctAssignList.add(populateAssignment(test.getKey(),test.getValue()));
												/**Debug**/
												//Set<String> keys = mapDctAssignments.keySet();
										        //for(String a:keys){
										        //log.info("Map Put: " +a+" -- "+mapDctAssignments.get(a));
										        //}
											}
											
								    	}
				    					 //Add Cover Docket linked assignments to list for duplicate check
				    					 unqDockets.setAssignments(uniqueDctAssignList);
								    }else{
								    	log.info("TrackingAction - validateDocketNumber - No linked Assignments returned");
								    	dctObj.put("Empty", "Cover Docket "+pTrackingForm.getDocketNumber()+" is invalid as it has no linked Assignments");
								    	dctError = true;
								    }
								    	//log.info("TrackingAction - validateDocketNumber - Checking Linked Assignments");
								    	/**Debug**/
								        //Set<String> keys = mapDctAssignments.keySet();
								        //for(String k:keys){
								        //log.info("Map Check: " +k+" -- "+mapDctAssignments.get(k));
								        //}
								    //Remove Duplicate from Main Unique Assignment Map
		            	            if(!mapRemoveAssignments.isEmpty()){
		            	            	log.info("TrackingAction - validateDocketNumber - (mapRemoveAssignments - unique) Remove assignment from Main Unique Map");
		            	            	Set<String> unqAssKeys = mapUnqAssignments.keySet();
								        for(String k:unqAssKeys){
								        	log.info("TrackingAction - validateDocketNumber - (mapRemoveAssignments - unique - K) "+k+"~"+mapUnqAssignments.get(k));
								        	Set<String> unqRemAssKeys = mapRemoveAssignments.keySet();
									        for(String r:unqRemAssKeys){
									        	log.info("TrackingAction - validateDocketNumber - (mapRemoveAssignments - unique - R) "+r+"~"+mapRemoveAssignments.get(r));
									        	if (mapUnqAssignments.get(k).equals(mapRemoveAssignments.get(r))){
									        		// remove value for key 2
									        		log.info("TrackingAction - validateDocketNumber - (mapRemoveAssignments from mapUnqAssignments: "+k+"~"+mapUnqAssignments.get(k));
									        		mapUnqAssignments.remove(k);
									        	}
									        }
								        }
			                        	log.info("TrackingAction - validateDocketNumber - **************************************************************");
		            	            }

            	    				if(!mapRemoveAssignments.isEmpty()){
    	            	            	log.info("TrackingAction - validateDocketNumber - (mapRemoveAssignments - docket) Adding Assignment to be removed from Consignment Unique Map on screen");
    	            	            	dctObj.put("RemoveAssignments", mapRemoveAssignments);
    	            	            }
            	    				if(!mapRemoveConUnqAssignments.isEmpty()){
    	            	            	log.info("TrackingAction - validateDocketNumber - (mapRemoveConUnqAssignments - docket) Adding Assignment to be removed from Consignment Unique Map on screen");
    	            	            	dctObj.put("RemoveConAssignments", mapRemoveConUnqAssignments);
    	            	            }
            	    				if (!dctError){
				    					dctObj.put((String) pTrackingForm.getDocketNumber(), mapDctAssignments);
				    					masterDocketList.add(unqDockets);
            	    				}
		    					}else {
			    					log.info("TrackingAction - validateDocketNumber - Docket number is not valid");
			    					errorMsg = "Cover Docket "+pTrackingForm.getDocketNumber()+" is invalid.<br/>Please Enter a valid Cover Docket Number";
		    					}
		    				}else{
		    					errorMsg = "You may only enter 50 docket numbers at a time";
		    				}	    	
    	               }
	    				pTrackingForm.setDocketNumber("");
	    		   }else {
	    			    log.info("TrackingAction - validateDocketNumber - Docket number is not valid");
	    			    errorMsg = "Cover Docket "+pTrackingForm.getDocketNumber()+" is invalid.<br/>Please Enter a valid Cover Docket Number";
	    		   }
    		 	
    		   }catch(Exception ex){
    			   errorMsg = "A WebService link is not responding. Please try again later / "+ex;

    		   }

    		   //Final check of main list for duplicate testing
    		   log.info("TrackingAction - validateDocketNumber - *******************************************************");
			
				if(!masterDocketList.isEmpty()) {
					for(Docket docketTest : masterDocketList) {
						log.info("TrackingAction - validateDocketNumber - Cover Docket Test - Docket ID : " +docketTest.getDocketID());
						log.info("TrackingAction - validateDocketNumber - Cover Docket Test - Docket Number : " +docketTest.getDocketNumber());
						log.info("TrackingAction - validateDocketNumber - *******************************************************");
						if(docketTest.getAssignments() != null ) {
							for(Assignment assignment : docketTest.getAssignments()) {
								log.info("TrackingAction - validateDocketNumber - Cover Docket Test - Dct Student Number : " +assignment.getStudentNumber());
								log.info("TrackingAction - validateDocketNumber - Cover Docket Test - Dct Unique Assignment : " +assignment.getUniqueAssignmentNumber());
								log.info("TrackingAction - validateDocketNumber - *******************************************************");
							}
						}
					}
					log.info("TrackingAction - validateDocketNumber - *******************************************************");
				}
    		   
		// Convert the map to json
		PrintWriter out = response.getWriter();
		if (errorMsg != null && !errorMsg.equals("")){
		   	Map<String, String> mapErrors = new LinkedHashMap<String, String>();
		   	mapErrors.put("Error",errorMsg);
		   	JSONObject jsonObject = JSONObject.fromObject(mapErrors);
		   	out.write(jsonObject.toString());
		   	out.flush();
		}else{
		   	JSONObject jsonObject = JSONObject.fromObject(dctObj);
        	log.info("TrackingAction - validateDocketNumber - Final - **************************************************************");
        	log.info("TrackingAction - validateDocketNumber - Final - jsonObject="+jsonObject.toString());
        	log.info("TrackingAction - validateDocketNumber - Final - **************************************************************");
		   	out.write(jsonObject.toString());
		   	out.flush();
		}
		        
   		   //log.info("jsonString:"+dctObj.toString());
   		   //PrintWriter out = response.getWriter();
   		   //out.write(dctObj.toString());
   		   //out.flush();

   		   return null; //Returning null to prevent struts from interfering with ajax/json
    }

    public ActionForward validateStudentUniqueNumber(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{

		/** Edmund Enumerate through all parameters received
	  	 * @return **/
	    //getAllRequestParamaters(request, response);
	    
        String errorMsg = "";
        TrackingForm pTrackingForm = (TrackingForm)form;
        try
        {
        	log.info("TrackingAction - validateStudentUniqueNumber");
            Session currentSession = sessionManager.getCurrentSession();
            HttpSession session = request.getSession(true);

            if(pTrackingForm.getStudNo() != null && !pTrackingForm.getStudNo().trim().equals("") && isNumberValid(pTrackingForm.getStudNo().trim()) && !(pTrackingForm.getStudNo().trim().length()>8) && pTrackingForm.getUniqueAssignmentNr() != null && !pTrackingForm.getUniqueAssignmentNr().trim().equals("") && isNumberValid(pTrackingForm.getUniqueAssignmentNr().trim()))
            {
                DocketNumberDetails details = new DocketNumberDetails();
                int studentAssignID = 196148;
                validationResult = pGateWay.studentAssignNumberValidation(webServiceURL ,studentAssignID, pTrackingForm.getStudNo().trim(), pTrackingForm.getUniqueAssignmentNr().trim());
                log.info("TrackingAction - validateStudentUniqueNumber - uniqueAssCount="+uniqueAssCount);
                if (duplicateCheckAllAssignments(pTrackingForm.getStudNo().trim(), pTrackingForm.getUniqueAssignmentNr().trim())){
                	errorMsg = "The Student Number "+pTrackingForm.getStudNo().trim()+" and Unique Assignment number "+pTrackingForm.getUniqueAssignmentNr().trim()+" combination has already been added. <br/>Please check Consignment lists, Cover Dockets and Unique Assignments";
                }else{
	                if(uniqueAssCount < 49){
	                    if(validationResult.contains("Valid - assignment found")){
	                    	uniqueAssCount++;
	                        details.setStudentNumber(pTrackingForm.getStudNo().trim());
	                        details.setUniqueAssignmentNumber(pTrackingForm.getUniqueAssignmentNr().trim());
	                        log.info("TrackingAction - validateStudentUniqueNumber - StudentNumber="+pTrackingForm.getStudNo().trim()+", UniqueAssignmentNumber="+pTrackingForm.getUniqueAssignmentNr().trim());
	                        // Generate a map
	                        mapUnqAssignments.put(Integer.toString(uniqueAssCount), (String) pTrackingForm.getStudNo().trim()+"~"+(String) pTrackingForm.getUniqueAssignmentNr().trim());
	                    }else{
	                    	errorMsg = validationResult;
	                    }
	                }else{
	                	errorMsg = "You may only enter 50 docket numbers at a time";
	                }
                }
            }else {
            	errorMsg = "Assignment "+pTrackingForm.getStudNo().trim()+" / "+pTrackingForm.getUniqueAssignmentNr().trim()+" is invalid.<br/>Please Enter a valid Student and Unique Assignment Number";
 		   	}
           
        }catch(Exception ex){
        	errorMsg = "A WebService link is not responding. Please try again later / "+ex;
        }
		// Convert the map to json
		PrintWriter out = response.getWriter();
        if (errorMsg != null && !errorMsg.equals("")){
        	Map<String, String> mapErrors = new LinkedHashMap<String, String>();
        	mapErrors.put("Error",errorMsg);
        	JSONObject jsonObject = JSONObject.fromObject(mapErrors);
        	out.write(jsonObject.toString());
        	out.flush();
        }else{
        	JSONObject jsonObject = JSONObject.fromObject(mapUnqAssignments);
        	log.info("TrackingAction - validateStudentUniqueNumber - Final - **************************************************************");
        	log.info("TrackingAction - validateStudentUniqueNumber - Final - jsonObject="+jsonObject.toString());
        	log.info("TrackingAction - validateStudentUniqueNumber - Final - **************************************************************");
        	out.write(jsonObject.toString());
        	out.flush();
        }

		return null; //Returning null to prevent struts from interfering with ajax/json
    }
    


    public ActionForward retrieveCSDInformation(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
       
    	String returnType = null;
    	int webserviceId = 958843; /*VALIDATE_USER 958843 A */
    	ActionMessages messages = new ActionMessages();
        TrackingForm pTrackingForm = (TrackingForm)form;

        //Validate User
       if (pTrackingForm.getNovelUserId() != null && !pTrackingForm.getNovelUserId().isEmpty()){
            String userRecord = pGateWay.validateUser(webServiceURL, webserviceId,"value1", pTrackingForm.getNovelUserId());
            if (userRecord == null  || userRecord.equals("")){
            	log.info("TrackingAction - retrieveCSDInformation - NovelUserId NOT Found");
            	messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("message.generalmessage", "User's Personnel record not found"));
            	addErrors(request, messages);
            	return mapping.findForward(USER_SELECTION);
            }
       }else{
    	   log.info("TrackingAction - retrieveCSDInformation - novelUserId NOT Found");
    	   messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("message.generalmessage", "User's Network ID not found"));
    	   addErrors(request, messages);
    	   return mapping.findForward(USER_SELECTION);
       }
         
       try{
            HttpSession session = request.getSession(true);
            resetForm(pTrackingForm, "retrieveCSDInformation");
            pTrackingForm.setUserSelection(request.getParameter("type"));
             log.info("TrackingAction - retrieveCSDInformation - user id="+pTrackingForm.getNovelUserId());
            
            if(pTrackingForm.getNovelUserId() != null && pTrackingForm.getNovelUserId().length() > 0 && pTrackingForm.getUserSelection() != null && !pTrackingForm.getUserSelection().equals("")){
                if(pTrackingForm.getUserSelection().equals("out")){
                    returnType = BOOK_OUT;
                }else if (pTrackingForm.getUserSelection().equals("in")){
                    returnType = BOOK_IN;
                }else if (pTrackingForm.getUserSelection().equals("search")){
                	returnType = SEARCH;
                }else if (pTrackingForm.getUserSelection().equals("report")){
                	returnType = REPORT;
                }
            }else{
                returnType = "error";
            }
            log.info("TrackingAction - retrieveCSDInformation - returnType="+returnType);
       }catch(Exception e){
            messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("message.generalmessage", "retrieveCSDInformation Error! Please try again / " +e));
            addErrors(request, messages);
            e.printStackTrace();
            return mapping.findForward(USER_SELECTION);
       }
       log.info("TrackingAction - retrieveCSDInformation - returnType="+returnType);
       return mapping.findForward(returnType);
    }

    public ActionForward getUserInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	
    	String novelUserId = "";
    	int webserviceId = 958843; /*VALIDATE_USER 958843 A */
    	
    	ActionMessages messages = new ActionMessages();
    	TrackingForm pTrackingForm = (TrackingForm)form;
        sessionManager = (SessionManager) ComponentManager.get(SessionManager.class);
        Session currentSession = sessionManager.getCurrentSession();
        novelUserId = request.getParameter("NETWORKID");
        if(novelUserId != null && !novelUserId.isEmpty()){
            pTrackingForm.setNovelUserId(novelUserId);
        } else{
            userID = currentSession.getUserEid();
            pTrackingForm.setNovelUserId(currentSession.getUserEid());
        }
        
        //Validate User
       if (pTrackingForm.getNovelUserId() != null && !pTrackingForm.getNovelUserId().isEmpty()){
            String userRecord = pGateWay.validateUser(webServiceURL, webserviceId,"value1", pTrackingForm.getNovelUserId());
            if (userRecord == null  || userRecord.equals("")){
            	log.info("TrackingAction - getUserInfo - NovelUserId NOT Found");
            	messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("message.generalmessage", "User's Personnel record not found"));
            	addErrors(request, messages);
            	return mapping.findForward(USER_SELECTION);
            }
       }else{
    	   log.info("TrackingAction - getUserInfo - novelUserId NOT Found");
    	   messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("message.generalmessage", "User's Network ID not found"));
    	   addErrors(request, messages);
    	   return mapping.findForward(USER_SELECTION);
       }
       
        return mapping.findForward(USER_SELECTION);
    }

    public ActionForward clear(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	
    	log.info("TrackingAction - clear - Calling clear method");
    	TrackingForm pTrackingForm = (TrackingForm)form;
        HttpSession session = request.getSession(true);
        String returnType="";
        if(pTrackingForm.getUserSelection() != null && !pTrackingForm.getUserSelection().equals("") && pTrackingForm.getUserSelection().equals("out")){
            log.info("Calling clear method from book out");
            resetForm(pTrackingForm, "clear+"+pTrackingForm.getUserSelection());
            returnType = BOOK_OUT;
        }else if(pTrackingForm.getUserSelection() != null && !pTrackingForm.getUserSelection().equals("") && pTrackingForm.getUserSelection().equals("in")){
        	log.info("Calling clear method from book in");
        	resetForm(pTrackingForm, "clear+"+pTrackingForm.getUserSelection());
            returnType = BOOK_IN;
        }else if(pTrackingForm.getUserSelection() != null && !pTrackingForm.getUserSelection().equals("") && pTrackingForm.getUserSelection().equals("search")){
        	log.info("Calling clear method from Search");
        	resetForm(pTrackingForm, "clear+"+pTrackingForm.getUserSelection());
        	returnType = SEARCH;
        }else if(pTrackingForm.getUserSelection() != null && !pTrackingForm.getUserSelection().equals("") && pTrackingForm.getUserSelection().equals("report")){
        	log.info("Calling clear method from Report");
        	resetForm(pTrackingForm, "clear+"+pTrackingForm.getUserSelection());
        	returnType = REPORT;
        }
        return mapping.findForward(returnType);
    }

    
    public ActionForward displayCollegeInformation(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException{

    	log.info("TrackingAction - displayCollegeInformation - **************************************************************");
    	int keyCounter = 0;
    	JSONObject collegeObj = new JSONObject();
       	Map<String, String> mapColleges = new LinkedHashMap<String, String>();

        try{
    		int webserviceId = 397381;
    		ArrayList<KeyValue> getValues = pGateWay.getCollegeList(webServiceURL, webserviceId,"value1","value2");
    		Iterator<KeyValue> it = getValues.iterator();
    		
    		while(it.hasNext()){
    			KeyValue test = (KeyValue) it.next();
    			mapColleges.put(Integer.toString(keyCounter), test.getKey()+"~"+test.getValue());
    			keyCounter++;
    		}
    		collegeObj.put("Colleges",mapColleges);
        }catch(Exception ex){
        	collegeObj.put("Error","The getCollegeList Web Service Failed! Please try again / <br/>"+ex);
        }
        
		// Convert the map to json
		PrintWriter out = response.getWriter();
       	JSONObject jsonObject = JSONObject.fromObject(collegeObj);
       	log.info("TrackingAction - displayCollegeInformation - Final - **************************************************************");
       	log.info("TrackingAction - displayCollegeInformation - Final - jsonObject="+jsonObject.toString());
       	log.info("TrackingAction - displayCollegeInformation - Final - **************************************************************");
       	out.write(jsonObject.toString());
       	out.flush();

		return null; //Returning null to prevent struts from interfering with ajax/json
    }

    public ActionForward displaySchoolInformation(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException{
        
    	log.info("TrackingAction - displaySchoolInformation - **************************************************************");
    	int keyCounter = 0;
    	JSONObject schoolObj = new JSONObject();
       	Map<String, String> mapSchools = new LinkedHashMap<String, String>();
       	TrackingForm pTrackingForm = (TrackingForm)form;
       	
        try{
        	int schoolID = 964504;
        	if(pTrackingForm.getCollege() != null && !pTrackingForm.getCollege().equals("-1")){
	        	int collegeCode = Integer.parseInt(pTrackingForm.getCollege().trim());
	        	log.info("TrackingAction - displaySchoolInformation - College="+collegeCode);
	    		ArrayList<KeyValue> getValues = pGateWay.getSchoolList1(webServiceURL ,schoolID,"value1","value3",collegeCode);
	    		Iterator<KeyValue> it = getValues.iterator();
	    		
	    		while(it.hasNext()){
	    			KeyValue test = (KeyValue) it.next();
	    			mapSchools.put(Integer.toString(keyCounter), test.getKey()+"~"+test.getValue());
	    			keyCounter++;
	    		}
	    		schoolObj.put("Schools",mapSchools);
        	}
        }catch(Exception ex){
        	schoolObj.put("Error","The getSchoolList Web Service Failed! Please try again / <br/>"+ex);
        }
        
		// Convert the map to json
		PrintWriter out = response.getWriter();
       	JSONObject jsonObject = JSONObject.fromObject(schoolObj);
       	log.info("TrackingAction - displaySchoolInformation - Final - **************************************************************");
       	log.info("TrackingAction - displaySchoolInformation - Final - jsonObject="+jsonObject.toString());
       	log.info("TrackingAction - displaySchoolInformation - Final - **************************************************************");
       	out.write(jsonObject.toString());
       	out.flush();

		return null; //Returning null to prevent struts from interfering with ajax/json
    }
    
    public ActionForward displayDepartmentInformation(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException{
        
    	log.info("TrackingAction - displayDepartmentInformation - **************************************************************");
    	int keyCounter = 0;
    	JSONObject departmentObj = new JSONObject();
       	Map<String, String> mapDepartments = new LinkedHashMap<String, String>();
       	TrackingForm pTrackingForm = (TrackingForm)form;
       	
        try{
        	int departmentId = 571560;
        	
        	if(pTrackingForm.getSchool() != null && pTrackingForm.getCollege() != null && !pTrackingForm.getSchool().equals("-1") && !pTrackingForm.getCollege().equals("-1")){
	        	int collegeCode = Integer.parseInt(pTrackingForm.getCollege().trim());
	        	int schoolCode = Integer.parseInt(pTrackingForm.getSchool().trim());
	        	log.info("TrackingAction - displayDepartmentInformation - College="+collegeCode);
	        	log.info("TrackingAction - displayDepartmentInformation - School="+schoolCode);
	        	ArrayList<KeyValue> getValues = pGateWay.getDepartmentList1(webServiceURL ,departmentId,"value1","value2",collegeCode,schoolCode);
	    		Iterator<KeyValue> it = getValues.iterator();
	    		
	    		while(it.hasNext()){
	    			KeyValue test = (KeyValue) it.next();
	    			mapDepartments.put(Integer.toString(keyCounter), test.getKey()+"~"+test.getValue());
	    			keyCounter++;
	    		}
	    		departmentObj.put("Departments",mapDepartments);
        	}
        }catch(Exception ex){
        	departmentObj.put("Error","The getDepartmentList Web Service Failed! Please try again / <br/>"+ex);
        }
        
		// Convert the map to json
		PrintWriter out = response.getWriter();
       	JSONObject jsonObject = JSONObject.fromObject(departmentObj);
       	log.info("TrackingAction - displayDepartmentInformation - Final - **************************************************************");
       	log.info("TrackingAction - displayDepartmentInformation - Final - jsonObject="+jsonObject.toString());
       	log.info("TrackingAction - displayDepartmentInformation - Final - **************************************************************");
       	out.write(jsonObject.toString());
       	out.flush();

		return null; //Returning null to prevent struts from interfering with ajax/json
    }
    
    public ActionForward displayModuleInformation(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException{
        
    	log.info("TrackingAction - displayModuleInformation - **************************************************************");
    	int keyCounter = 0;
    	JSONObject moduleObj = new JSONObject();
       	Map<String, String> mapModules = new LinkedHashMap<String, String>();
       	TrackingForm pTrackingForm = (TrackingForm)form;
       	
        try{
        	int moduleID = 347652;
        	if(pTrackingForm.getSchool() != null && pTrackingForm.getCollege() != null && !pTrackingForm.getSchool().equals("-1") && !pTrackingForm.getCollege().equals("-1") && pTrackingForm.getDepartment() != null && !pTrackingForm.getDepartment().equals("-1")){
	        	int collegeCode = Integer.parseInt(pTrackingForm.getCollege().trim());
	        	int schoolCode = Integer.parseInt(pTrackingForm.getSchool().trim());
	        	int departmentCode = Integer.parseInt(pTrackingForm.getDepartment().trim());
	        	log.info("TrackingAction - displayModuleInformation - College="+collegeCode);
	        	log.info("TrackingAction - displayModuleInformation - School="+schoolCode);
	        	log.info("TrackingAction - displayModuleInformation - Department="+departmentCode);
	        	ArrayList<KeyValue> getValues = pGateWay.getModuleList1(webServiceURL ,moduleID,"value1","value2",collegeCode,schoolCode,departmentCode);
	    		Iterator<KeyValue> it = getValues.iterator();
	   		
	    		while(it.hasNext()){
	    			KeyValue test = (KeyValue) it.next();
	    			mapModules.put(Integer.toString(keyCounter), test.getKey()+"~"+test.getValue());
	    			keyCounter++;
	    		}
	    		moduleObj.put("Modules",mapModules);
        	}
        }catch(Exception ex){
        	moduleObj.put("Error","The getModuleList1 Web Service Failed! Please try again / <br/>"+ex);
        }
        
		// Convert the map to json
		PrintWriter out = response.getWriter();
       	JSONObject jsonObject = JSONObject.fromObject(moduleObj);
       	log.info("TrackingAction - displayModuleInformation - Final - **************************************************************");
       	log.info("TrackingAction - displayModuleInformation - Final - jsonObject="+jsonObject.toString());
       	log.info("TrackingAction - displayModuleInformation - Final - **************************************************************");
       	out.write(jsonObject.toString());
       	out.flush();

		return null; //Returning null to prevent struts from interfering with ajax/json
    }

public ActionForward displayProvinceInformation(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException{
    
	log.info("TrackingAction - displayProvinceInformation - **************************************************************");
	int keyCounter = 0;
	JSONObject provinceObj = new JSONObject();
   	Map<String, String> mapProvinces = new LinkedHashMap<String, String>();
   	TrackingForm pTrackingForm = (TrackingForm)form;
   	
    try{
    	int provinceWebserviceId = 304912;
    	ArrayList<KeyValue> getValues = pGateWay.getProvinceList(webServiceURL,provinceWebserviceId,"value1","value2");
    	Iterator<KeyValue> it = getValues.iterator();
   		
    	while(it.hasNext()){
    		KeyValue test = (KeyValue) it.next();
    		mapProvinces.put(Integer.toString(keyCounter), test.getKey()+"~"+test.getValue());
    		keyCounter++;
    	}
    	provinceObj.put("Provinces",mapProvinces);

    }catch(Exception ex){
    	provinceObj.put("Error","The getProvinceList Web Service Failed! Please try again / <br/>"+ex);
    }
    
	// Convert the map to json
	PrintWriter out = response.getWriter();
   	JSONObject jsonObject = JSONObject.fromObject(provinceObj);
   	log.info("TrackingAction - displayProvinceInformation - Final - **************************************************************");
   	log.info("TrackingAction - displayProvinceInformation - Final - jsonObject="+jsonObject.toString());
   	log.info("TrackingAction - displayProvinceInformation - Final - **************************************************************");
   	out.write(jsonObject.toString());
   	out.flush();

	return null; //Returning null to prevent struts from interfering with ajax/json
}

    public ActionForward displayRegionalOfficeInformation(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException{
        
    	log.info("TrackingAction - displayRegionalOfficeInformation - **************************************************************");
    	int keyCounter = 0;
    	JSONObject officeObj = new JSONObject();
       	Map<String, String> mapOffices = new LinkedHashMap<String, String>();
       	TrackingForm pTrackingForm = (TrackingForm)form;
       	
        try{
        	int regionalOfficeWebserviceId = 768398;
        	int provinceCode = Integer.parseInt(pTrackingForm.getProvince());
        	log.info("TrackingAction - displayRegionalOfficeInformation - Province="+provinceCode);
            ArrayList<KeyValue> getValues = pGateWay.getRegionalOfficeList(webServiceURL,regionalOfficeWebserviceId,"value1","value2",provinceCode);
            Iterator<KeyValue> it = getValues.iterator();
	   		
	    	while(it.hasNext()){
	    		KeyValue test = (KeyValue) it.next();
	    		mapOffices.put(Integer.toString(keyCounter), test.getKey()+"~"+test.getValue());
	    		keyCounter++;
	    	}
	    	officeObj.put("RegionalOffices",mapOffices);
        }catch(Exception ex){
        	officeObj.put("Error","The getRegionalOffice Web Service Failed! Please try again / <br/>"+ex);
        }
        
		// Convert the map to json
		PrintWriter out = response.getWriter();
       	JSONObject jsonObject = JSONObject.fromObject(officeObj);
       	log.info("TrackingAction - displayRegionalOfficeInformation - Final - **************************************************************");
       	log.info("TrackingAction - displayRegionalOfficeInformation - Final - jsonObject="+jsonObject.toString());
       	log.info("TrackingAction - displayRegionalOfficeInformation - Final - **************************************************************");
       	out.write(jsonObject.toString());
       	out.flush();

		return null; //Returning null to prevent struts from interfering with ajax/json
    }
    
    public ActionForward displayBuildingInformation(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException{
        
    	log.info("TrackingAction - displayBuildingInformation - **************************************************************");
    	int keyCounter = 0;
    	JSONObject buildingObj = new JSONObject();
       	Map<String, String> mapBuildings = new LinkedHashMap<String, String>();
       	TrackingForm pTrackingForm = (TrackingForm)form;
       	
       	log.info("TrackingAction - displayBuildingInformation - Building="+pTrackingForm.getBuilding());
       	
        try{
        	int buildingWebserviceID = 408107 ;
        	String noSpace = pTrackingForm.getBuilding().trim().replace(" ", "%20");
        	log.info("TrackingAction - displayBuildingInformation - getBuildingList - Building="+noSpace);
        	ArrayList<KeyValue> getValues = pGateWay.getBuildingList(webServiceURL ,buildingWebserviceID,"id","value1", noSpace);
           	
	    	Iterator<KeyValue> it = getValues.iterator();
	   		
	    		while(it.hasNext()){
	    			KeyValue test = (KeyValue) it.next();
	    			mapBuildings.put(Integer.toString(keyCounter), test.getValue()+"~"+test.getValue());
	    			keyCounter++;
	    		}
	    		buildingObj.put("Buildings",mapBuildings);
        }catch(Exception ex){
        	buildingObj.put("Error","The getBuildingList Web Service Failed! Please try again / <br/>"+ex);
        }
        
		// Convert the map to json
		PrintWriter out = response.getWriter();
       	JSONObject jsonObject = JSONObject.fromObject(buildingObj);
       	log.info("TrackingAction - displayBuildingInformation - Final - **************************************************************");
       	log.info("TrackingAction - displayBuildingInformation - Final - jsonObject="+jsonObject.toString());
       	log.info("TrackingAction - displayBuildingInformation - Final - **************************************************************");
       	out.write(jsonObject.toString());
       	out.flush();

		return null; //Returning null to prevent struts from interfering with ajax/json
    }
 
	public ActionForward processInput(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)throws Exception{

    	/** Edmund Enumerate through all parameters received
	 	 * @return **/
	   	//getAllRequestParamaters(request, response);
    	
	   	String processType = "";
		String addressFinal = "";
		String destinationAddress = "";
		boolean addressOK = false;
		boolean addressError = false;
	   	boolean chkConsignmentInput = false;
	   	boolean isStaff = false;
	   	
	   	TrackingForm pTrackingForm = (TrackingForm)form;
	   	Map<String, String> mapOut = new LinkedHashMap<String, String>();
	   	
	   	//Clear any previous lists
	   	resetLocal(pTrackingForm,"processInput");
	   	
	   	
	   	//Re-Initialize Final Address and re-configure below
	   	pTrackingForm.setSaveAddress1(" "); //Not used in DB (Saved as Address_Line_2). Using it for processing and display only
	   	pTrackingForm.setSaveAddress2(" ");
	   	pTrackingForm.setSaveAddress3(" ");
	   	pTrackingForm.setSaveAddress4(" ");
	   	pTrackingForm.setSaveAddress5(" ");
	   	pTrackingForm.setSavePostal("0");
		pTrackingForm.setDisplayAddress1(" "); //Not used in DB (Saved as Address_Line_2). Using it for processing and display only
	   	pTrackingForm.setDisplayAddress2(" ");
	   	pTrackingForm.setDisplayAddress3(" ");
	   	pTrackingForm.setDisplayAddress4(" ");
	   	pTrackingForm.setDisplayPostal("0");
	   	
	   	log.info("TrackingAction - processInput - **************************************************************");
		log.info("TrackingAction - processInput - Start - Process");
		log.info("TrackingAction - processInput - **************************************************************");
		
		if (pTrackingForm.getUserSelection() == null || pTrackingForm.getUserSelection().equals("")){
			log.info("TrackingAction - processInput - Start - No Process");
			mapOut.put("Error", "Illegal Process - No Input Argument");
			addressError=true;
			log.info("TrackingAction - processInput - **************************************************************");
		}else{
			processType =  pTrackingForm.getUserSelection().toUpperCase();
			log.info("TrackingAction - processInput - Start - Process="+processType);
			log.info("TrackingAction - processInput - **************************************************************");
	
			//Process Address details if BookOut (Do before rest as this is non negotiable for Booking OUT

			if (processType.equalsIgnoreCase("OUT")){
				log.info("TrackingAction - processInput - AddressType="+pTrackingForm.getAddressType());
				if (pTrackingForm.getAddressType() != null && !pTrackingForm.getAddressType().equals("")){
					String addressType =  pTrackingForm.getAddressType();
					log.info("TrackingAction - processInput - Processing Address Input");
					if (addressType.equalsIgnoreCase("DSAA")){
						log.info("TrackingAction - processInput - Destination Address=DSSA");
						addressFinal = "DSSA";
						destinationAddress = (new StringBuilder()).append(destinationAddress).append("DSAA|DSAA").toString();
						pTrackingForm.setSaveAddress1("DSAA|DSAA");
						//Populate DisplayAddress with Description for Reports and Result/PDF Printing
						pTrackingForm.setDisplayAddress1("DSAA (Directorate: Student Assessment Administration)");
					
					}else if (addressType.equalsIgnoreCase("DISPATCH")){
						log.info("TrackingAction - processInput - Destination Address=DISPATCH");
						addressFinal = "DISPATCH";
						destinationAddress = (new StringBuilder()).append(destinationAddress).append("DISPATCH|DISPATCH").toString();
						pTrackingForm.setSaveAddress1("DISPATCH|DISPATCH");
						//Populate DisplayAddress with Description for Reports and Result/PDF Printing
						pTrackingForm.setDisplayAddress1("DISPATCH");
					
					}else if (addressType.equalsIgnoreCase("PROVINCE")){
						log.info("TrackingAction - processInput - Destination Address=PROVINCE");
						addressFinal = "PROVINCE";
						if(pTrackingForm.getProvince() != null && !pTrackingForm.getProvince().equals("0")){
							log.info("TrackingAction - processInput - Destination Province="+pTrackingForm.getProvince());
							destinationAddress = (new StringBuilder()).append(destinationAddress).append("Province|").append(pTrackingForm.getProvince()).toString();
							pTrackingForm.setSaveAddress1("Province|"+pTrackingForm.getProvince());
							//Populate DisplayAddress with Description for Reports and Result/PDF Printing
							pTrackingForm.setDisplayAddress1(request.getParameter("labelProvince"));
							log.info("TrackingAction - processInput - Province Description="+request.getParameterValues("labelProvince"));
							if(pTrackingForm.getRegion() != null && !pTrackingForm.getRegion().equals("0")){
								log.info("TrackingAction - processInput - Destination Region="+pTrackingForm.getRegion());
								destinationAddress = (new StringBuilder()).append(destinationAddress).append(",Region|").append(pTrackingForm.getRegion()).toString();
								pTrackingForm.setSaveAddress2("Region|"+pTrackingForm.getRegion());
								//Populate DisplayAddress with Description for Reports and Result/PDF Printing
								pTrackingForm.setDisplayAddress2(request.getParameter("labelRegion"));
							}else{
								log.info("TrackingAction - processInput - Destination Region Empty");
							}
						}else{
							log.info("TrackingAction - processInput - Destination Province Empty");
							mapOut.put("Error", "No Destination Address.<br/>Please select at least a Province.");
							addressError=true;
						}
	
					}else if (addressType.equalsIgnoreCase("OTHER")){
						log.info("TrackingAction - processInput - Destination Address=COLLEGE");
						addressFinal = "COLLEGE";
						if(pTrackingForm.getCsdmUsers() != null && !pTrackingForm.getCsdmUsers().equals("0")){
							log.info("TrackingAction - processInput - Destination Person="+pTrackingForm.getCsdmUsers());
							destinationAddress = (new StringBuilder()).append(destinationAddress).append("Person|").append(pTrackingForm.getCsdmUsers()).toString();
							pTrackingForm.setSaveAddress1("Person|"+pTrackingForm.getCsdmUsers());
							//Populate DisplayAddress with Description for Reports and Result/PDF Printing
							pTrackingForm.setDisplayAddress1(request.getParameter("labelCsdmUsers"));
							isStaff = true;
						}else if(pTrackingForm.getCollege() != null && !pTrackingForm.getCollege().equals("0")){
							log.info("TrackingAction - processInput - Destination College="+pTrackingForm.getCollege());
							destinationAddress = (new StringBuilder()).append(destinationAddress).append("College|").append(pTrackingForm.getCollege()).toString();
							pTrackingForm.setSaveAddress1("College|"+pTrackingForm.getCollege());
							//Populate DisplayAddress with Description for Reports and Result/PDF Printing
							pTrackingForm.setDisplayAddress1(request.getParameter("labelCollege"));
							
							if(pTrackingForm.getSchool() != null && !pTrackingForm.getSchool().equals("0")){
								log.info("TrackingAction - processInput - Destination School="+pTrackingForm.getSchool());
								destinationAddress = (new StringBuilder()).append(destinationAddress).append(",School|").append(pTrackingForm.getSchool()).toString();
								pTrackingForm.setSaveAddress2("School|"+pTrackingForm.getSchool());
								//Populate DisplayAddress with Description for Reports and Result/PDF Printing
								pTrackingForm.setDisplayAddress2(request.getParameter("labelSchool"));
								
								if(pTrackingForm.getDepartment() != null && !pTrackingForm.getDepartment().equals("0")){
									log.info("TrackingAction - processInput - Destination Department="+pTrackingForm.getDepartment());
									destinationAddress = (new StringBuilder()).append(destinationAddress).append(",Department|").append(pTrackingForm.getDepartment()).toString();
									pTrackingForm.setSaveAddress3("Department|"+pTrackingForm.getDepartment());
									//Populate DisplayAddress with Description for Reports and Result/PDF Printing
									pTrackingForm.setDisplayAddress3(request.getParameter("labelDepartment"));
									
									if(pTrackingForm.getModule() != null && !pTrackingForm.getModule().equals("0")){
										log.info("TrackingAction - processInput - Destination Module="+pTrackingForm.getModule());
										destinationAddress = (new StringBuilder()).append(destinationAddress).append(",Module|").append(pTrackingForm.getModule()).toString();
										pTrackingForm.setSaveAddress4("Module|"+pTrackingForm.getModule());
										//Populate DisplayAddress with Description for Reports and Result/PDF Printing
										pTrackingForm.setDisplayAddress4(request.getParameter("labelModule"));
									}else{
										log.info("TrackingAction - processInput - Destination Module Empty");
									}
									
								}else{
									log.info("TrackingAction - processInput - Destination Department Empty");
								}
								
							}else{
								log.info("TrackingAction - processInput - Destination School Empty");
							}
						}else{
							log.info("TrackingAction - processInput - Destination College Empty");
							mapOut.put("Error", "No Destination Address.<br/>Please select at least a College or a Person.");
							addressError=true;
						}
						
					}else if (addressType.equalsIgnoreCase("BUILDING")){
						log.info("TrackingAction - processInput - Destination Address=BUILDING");
						addressFinal = "BUILDING";
						if(pTrackingForm.getBuildingUsers() != null && !pTrackingForm.getBuildingUsers().equals("0")){
							log.info("TrackingAction - processInput - Destination Person="+pTrackingForm.getBuildingUsers());
							destinationAddress = (new StringBuilder()).append(destinationAddress).append("Person|").append(pTrackingForm.getBuildingUsers()).toString();
							pTrackingForm.setSaveAddress1("Person|"+pTrackingForm.getBuildingUsers());
							//Populate DisplayAddress with Description for Reports and Result/PDF Printing
							pTrackingForm.setDisplayAddress1(request.getParameter("labelBuildingUsers"));
							isStaff = true;
						}else if(pTrackingForm.getBuilding() != null && !pTrackingForm.getBuilding().equals("0")){
								log.info("TrackingAction - processInput - Destination Building="+pTrackingForm.getBuilding());
								destinationAddress = (new StringBuilder()).append(destinationAddress).append("Building|").append(pTrackingForm.getBuilding()).toString();
								pTrackingForm.setSaveAddress1("Building|"+pTrackingForm.getBuilding());
								//Populate DisplayAddress with Description for Reports and Result/PDF Printing
								pTrackingForm.setDisplayAddress1(request.getParameter("labelBuilding"));
								
						}else{
								log.info("TrackingAction - processInput - Destination Building Empty");
								mapOut.put("Error", "No Destination Address.<br/>Please select at least a Building or a Person.");
								addressError=true;
						}
						
					}else if (addressType.equalsIgnoreCase("PERSON")){
						log.info("TrackingAction - processInput - Destination Address=PERSON");
						addressFinal = "PERSON";
						if(pTrackingForm.getUsers() != null && !pTrackingForm.getUsers().equals("0")){
							log.info("TrackingAction - processInput - Destination Person="+pTrackingForm.getUsers());
							addressOK = true;
							destinationAddress = (new StringBuilder()).append(destinationAddress).append("Person|").append(pTrackingForm.getUsers()).toString();
							pTrackingForm.setSaveAddress1("Person|"+pTrackingForm.getUsers());
							//Populate DisplayAddress with Description for Reports and Result/PDF Printing
							pTrackingForm.setDisplayAddress1(request.getParameter("labelUsers"));
							isStaff = true;
						}else{
							log.info("TrackingAction - processInput - Destination User Empty");
							mapOut.put("Error", "No Destination Address.<br/>Please select a Person.");
							addressError=true;
						}
						
					}else if (addressType.equalsIgnoreCase("MANUAL")){
						log.info("TrackingAction - processInput - Destination Address=MANUAL");
						//Check if Address lines more that 28 Characters (DB Restriction)
						if (pTrackingForm.getUserAddress1() == null || pTrackingForm.getUserAddress1().trim().equals("") || pTrackingForm.getUserAddress1().trim().equals(" ")){
							log.info("TrackingAction - processInput - Destination Address=MANUAL - Line 1 Empty");
							mapOut.put("Error", "Address Line 1 is Empty.<br/>Please enter at least one line of the destination address.");
							addressError=true;
						}
						if (pTrackingForm.getUserAddress1() != null && !pTrackingForm.getUserAddress1().trim().equals("") && pTrackingForm.getUserAddress1().trim().length() > 28){
							log.info("TrackingAction - processInput - Destination Address=MANUAL - Line 1 more than 28 Characters");
							mapOut.put("Error", "Address Line 1 is too long.<br/>Please enter maximum 28 characters.");
							addressError=true;
						}
						if (pTrackingForm.getUserAddress2() != null && !pTrackingForm.getUserAddress2().trim().equals("") && pTrackingForm.getUserAddress2().trim().length() > 28){
							log.info("TrackingAction - processInput - Destination Address=MANUAL - Line 2 more than 28 Characters");
							mapOut.put("Error", "Address Line 2 is too long.<br/>Please enter maximum 28 characters.");
							addressError=true;
						}
						if (pTrackingForm.getUserAddress3() != null && !pTrackingForm.getUserAddress3().trim().equals("") && pTrackingForm.getUserAddress3().trim().length() > 28){
							log.info("TrackingAction - processInput - Destination Address=MANUAL - Line 3 more than 28 Characters");
							mapOut.put("Error", "Address Line 3 is too long.<br/>Please enter maximum 28 characters.");
							addressError=true;
						}
						if (pTrackingForm.getUserAddress4() != null && !pTrackingForm.getUserAddress4().trim().equals("") && pTrackingForm.getUserAddress4().trim().length() > 28){
							log.info("TrackingAction - processInput - Destination Address=MANUAL - Line 4 more than 28 Characters");
							mapOut.put("Error", "Address Line 4 is too long.<br/>Please enter maximum 28 characters.");
							addressError=true;
						}
						if (pTrackingForm.getUserPostal() != null && !pTrackingForm.getUserPostal().trim().equals("") && pTrackingForm.getUserPostal().trim().length() > 28){
							log.info("TrackingAction - processInput - Destination Address=MANUAL - Postal Code more than 4 Numbers");
							mapOut.put("Error", "Postal Code is too long.<br/>Please enter maximum 4 numbers.");
							addressError=true;
						}
						if (pTrackingForm.getUserEmail() != null && !pTrackingForm.getUserEmail().trim().equals("") && pTrackingForm.getUserEmail().trim().length() > 60){
							log.info("TrackingAction - processInput - Destination Address=MANUAL - Email Address more than 60 Characters");
							mapOut.put("Error", "Postal Code is too long.<br/>Please enter maximum 60 characters.");
							addressError=true;
						}
						if (pTrackingForm.getUserEmail() != null && !pTrackingForm.getUserEmail().trim().equals("") && !pTrackingForm.getUserEmail().trim().equals(" ")){
							// Validate email address
							log.info("TrackingAction - processInput - Destination Address=MANUAL - Validate email address="+pTrackingForm.getUserEmail().trim());
							String emailErrorMsg=validateEmail(pTrackingForm);
							if(!emailErrorMsg.equals("")){
								log.info("TrackingAction - processInput - Destination Address=MANUAL - Email address="+pTrackingForm.getUserEmail().trim()+" Error="+emailErrorMsg);
								mapOut.put("Error", emailErrorMsg);
								addressError=true;
							}
						}else{
							log.info("TrackingAction - processInput - Destination Address=MANUAL - Email address is empty="+pTrackingForm.getUserEmail().trim());
							mapOut.put("Error", "Email address is empty. Please supply a valid email address to notify a department or a recipient");
							addressError=true;
						}
							
						if (!addressError){
							//Remove empty address lines by moving everything up one.
							if((pTrackingForm.getUserAddress3() == null  || pTrackingForm.getUserAddress3().trim().equals("")) && (pTrackingForm.getUserAddress4() != null && !pTrackingForm.getUserAddress4().trim().equals(""))){
								pTrackingForm.setUserAddress3(pTrackingForm.getUserAddress4().trim());
							}
							if((pTrackingForm.getUserAddress2() == null  || pTrackingForm.getUserAddress2().trim().equals("")) && (pTrackingForm.getUserAddress3() != null && !pTrackingForm.getUserAddress3().trim().equals(""))){
								pTrackingForm.setUserAddress2(pTrackingForm.getUserAddress3().trim());
							}
							if((pTrackingForm.getUserAddress1() == null  || pTrackingForm.getUserAddress1().trim().equals("")) && (pTrackingForm.getUserAddress2() != null && !pTrackingForm.getUserAddress2().trim().equals(""))){
								pTrackingForm.setUserAddress1(pTrackingForm.getUserAddress2().trim());
							}
							//Now process resulting address lines 1 - 4 (Remember they are saved as Address_Line2 to Address_line5 in the DB as Address_Line1 is now used for NovellUserID
							if(pTrackingForm.getUserAddress1() != null && !pTrackingForm.getUserAddress1().trim().equals("")){
								log.info("TrackingAction - processInput - Destination AddressLine1="+pTrackingForm.getUserAddress1().trim());
								destinationAddress = (new StringBuilder()).append(destinationAddress).append("AddressLine1|").append(pTrackingForm.getUserAddress1()).toString();
								pTrackingForm.setSaveAddress1(pTrackingForm.getUserAddress1());
								//Populate DisplayAddress with Description for Reports and Result/PDF Printing
								pTrackingForm.setDisplayAddress1(pTrackingForm.getUserAddress1());
								
								if(pTrackingForm.getUserAddress2() != null && !pTrackingForm.getUserAddress2().trim().equals("")){
									log.info("TrackingAction - processInput - Destination AddressLine2="+pTrackingForm.getUserAddress2().trim());
									destinationAddress = (new StringBuilder()).append(destinationAddress).append(",AddressLine2|").append(pTrackingForm.getUserAddress2()).toString();
									pTrackingForm.setSaveAddress2(pTrackingForm.getUserAddress2());
									//Populate DisplayAddress with Description for Reports and Result/PDF Printing
									pTrackingForm.setDisplayAddress2(pTrackingForm.getUserAddress2());
									
									if(pTrackingForm.getUserAddress3() != null && !pTrackingForm.getUserAddress3().trim().equals("")){
										log.info("TrackingAction - processInput - Destination AddressLine3="+pTrackingForm.getUserAddress3().trim());
										destinationAddress = (new StringBuilder()).append(destinationAddress).append("AddressLine3|").append(pTrackingForm.getUserAddress3()).toString();
										pTrackingForm.setSaveAddress3(pTrackingForm.getUserAddress3());
										//Populate DisplayAddress with Description for Reports and Result/PDF Printing
										pTrackingForm.setDisplayAddress3(pTrackingForm.getUserAddress3());
										
										if(pTrackingForm.getUserAddress4() != null && !pTrackingForm.getUserAddress4().trim().equals("")){
											log.info("TrackingAction - processInput - Destination AddressLine4="+pTrackingForm.getUserAddress4().trim());
											destinationAddress = (new StringBuilder()).append(destinationAddress).append("AddressLine4|").append(pTrackingForm.getUserAddress4()).toString();
											pTrackingForm.setSaveAddress4(pTrackingForm.getUserAddress4());
											//Populate DisplayAddress with Description for Reports and Result/PDF Printing
											pTrackingForm.setDisplayAddress4(pTrackingForm.getUserAddress4());
										}
									}
								}
								if(pTrackingForm.getUserPostal() != null && !pTrackingForm.getUserPostal().trim().equals("")){
									log.info("TrackingAction - processInput - Destination AddressPostal="+pTrackingForm.getUserPostal().trim());
									destinationAddress = (new StringBuilder()).append(destinationAddress).append("AddressPostal|").append(pTrackingForm.getUserPostal()).toString();
									pTrackingForm.setSavePostal(pTrackingForm.getUserPostal());
									//Populate DisplayAddress with Description for Reports and Result/PDF Printing
									pTrackingForm.setDisplayPostal(pTrackingForm.getUserPostal());
								}else{
									pTrackingForm.setSavePostal("0");
									//Populate DisplayAddress with Description for Reports and Result/PDF Printing
									pTrackingForm.setDisplayPostal("0");
								}
								
								if(pTrackingForm.getUserEmail() != null && !pTrackingForm.getUserEmail().trim().equals("")){
									log.info("TrackingAction - processInput - Destination AddressEmail="+pTrackingForm.getUserEmail().trim());
									destinationAddress = (new StringBuilder()).append(destinationAddress).append("AddressEmail|").append(pTrackingForm.getUserEmail()).toString();
									pTrackingForm.setSaveEmail(pTrackingForm.getUserEmail());
									//Populate DisplayAddress with Description for Reports and Result/PDF Printing
									pTrackingForm.setDisplayEmail(pTrackingForm.getUserEmail());
								}else{
									pTrackingForm.setSaveEmail("");
									//Populate DisplayAddress with Description for Reports and Result/PDF Printing
									pTrackingForm.setDisplayEmail("");
								}
						    }else{
						    	log.info("TrackingAction - processInput - Destination Manual Empty");
						    	mapOut.put("Error", "No Destination Address.<br/>Please Enter at least the first line of the Address.");
						    	addressError = true;
		        			}
						}
					}else{
						log.info("TrackingAction - processInput - No Destination Address (Radio button) selected");
						mapOut.put("Error", "No Destination Address (Radio button) selected.");
						addressError = true;
					}
				}else{
					log.info("TrackingAction - processInput - No Address Input to Process");
					mapOut.put("Error", "No Destination Address Selected.<br/>Please select or enter an Address.");
					addressError = true;
				}
				if (addressError){
					log.info("TrackingAction - processInput - Final Address Error");
					log.info("TrackingAction - processInput - **************************************************************");
					PrintWriter out = response.getWriter();
					JSONObject jsonObject = JSONObject.fromObject(mapOut);
					out.write(jsonObject.toString());
					out.flush();
					
					return null;
				}else{
					pTrackingForm.setDestinationAddress(destinationAddress);
					log.info("TrackingAction - processInput - Final destinationAddress="+destinationAddress);
					log.info("TrackingAction - processInput - **************************************************************");
					if (isStaff){
						//Retrieve User Address from Personnel/Staff ID
						log.info("TrackingAction - processInput - Retrieve User Address from Personell/Staff ID");
						
				        int addressId = 892254;
				        int keyCounter = 0;
				        String PersNo = "";
				        if (pTrackingForm.getUsers() != null && !pTrackingForm.getUsers().equals("") && !pTrackingForm.getUsers().equals("0")){
				        	PersNo = pTrackingForm.getUsers().trim();
				        }else if (pTrackingForm.getCsdmUsers() != null && !pTrackingForm.getCsdmUsers().equals("") && !pTrackingForm.getCsdmUsers().equals("0")){
				        	PersNo = pTrackingForm.getCsdmUsers().trim();
				        }else if (pTrackingForm.getBuildingUsers() != null && !pTrackingForm.getBuildingUsers().equals("") && !pTrackingForm.getBuildingUsers().equals("0")){
				        	PersNo = pTrackingForm.getBuildingUsers().trim();
				        }

				        log.info("TrackingAction - processInput - PersNo="+PersNo);

				        //Start Setting up new Address
				        try{
				        	ArrayList<KeyValue> getValues = pGateWay.getSelectedUserAddress(webServiceURL ,addressId, PersNo, "value1", "value2", "value3", "value4", "value5", "value6", "Personnel");
				        	Iterator<KeyValue> it = getValues.iterator();
				        		
				        	if (it.hasNext()){
				        		String addressKey = "";
					    			
								KeyValue test = (KeyValue) it.next();
								if (test.getValue1() != null && !test.getValue1().equals("")){
									//log.info("TrackingAction - processInput - StaffNumber="+test.getValue());
									//addressKey = test.getValue().trim().toString();
					
									if(test.getValue1() != null &&  !test.getValue1().isEmpty()){
										if(!test.getValue1().equals("null")){
											log.info("TrackingAction - processInput - userAddress2="+test.getValue1());
												pTrackingForm.setDisplayAddress2(test.getValue1().trim().toString());
											}else{
												pTrackingForm.setDisplayAddress2("");
											}
										}else{
											pTrackingForm.setDisplayAddress2("");
										}
										if(test.getValue2() != null &&  !test.getValue2().isEmpty()){
											if(!test.getValue2().equals("null")){
												log.info("TrackingAction - processInput - userAddress3="+test.getValue2());
												pTrackingForm.setDisplayAddress3(test.getValue2().trim().toString());
											}else{
												pTrackingForm.setDisplayAddress3("");
											}
										}else{
											pTrackingForm.setDisplayAddress3("");
										}
										if(test.getValue3() != null &&  !test.getValue3().isEmpty()){
											if(!test.getValue3().equals("null")){
												log.info("TrackingAction - processInput - userEmail="+test.getValue3());
												pTrackingForm.setDisplayEmail(test.getValue3().trim().toString());
											}else{
												pTrackingForm.setDisplayEmail("");
											}
										}else{
											pTrackingForm.setDisplayEmail("");
										}
									}else{
										log.info("TrackingAction - processInput - Selected user's address not Found.");
									}
									log.info("TrackingAction - processInput - **************************************************************");

				        		}else{
				        			log.info("TrackingAction - processInput - Selected user's address not Found.");
				        		}
				            }catch(Exception ex){
				            	log.info("Error: The displayUserAddress Web Service Failed!"+ex);
				            }
						
					}

					log.info("TrackingAction - processInput - Final DisplayAddress1="+pTrackingForm.getDisplayAddress1());
					log.info("TrackingAction - processInput - Final DisplayAddress2="+pTrackingForm.getDisplayAddress2());
					log.info("TrackingAction - processInput - Final DisplayAddress3="+pTrackingForm.getDisplayAddress3());
					log.info("TrackingAction - processInput - Final DisplayAddress4="+pTrackingForm.getDisplayAddress4());
					log.info("TrackingAction - processInput - Final DisplayPostal="+pTrackingForm.getDisplayPostal());
					log.info("TrackingAction - processInput - Final DisplayEmail="+pTrackingForm.getDisplayEmail());
				}
				log.info("TrackingAction - processInput - **************************************************************");
			}
			
			
			
			//Get all Cover Dockets in the Consignment List
			ArrayList<String> groupOfDcts = new ArrayList<String>();
	        String[] paramValuesConDct = request.getParameterValues("chkConDocket"); 
	        if (paramValuesConDct != null && paramValuesConDct.length > 0){
		        for (int i = 0; i < paramValuesConDct.length; i++) { 
		            String paramValueConDct = paramValuesConDct[i]; 
		            if (paramValueConDct !=null && !paramValueConDct.equals("")){
			            //log.info("TrackingAction - Consignment Cover Dockets - Full="+paramValueConDct);
		            	log.info("TrackingAction - processInput - List of docketnumber is "+paramValueConDct);
		        		String []  splitConDct  = paramValueConDct.trim().split("~");
		        		if(splitConDct[0] != null && !splitConDct[0].isEmpty()){
		        			log.info("TrackingAction - processInput - Consignment Cover Dockets to "+processType+"="+splitConDct[1]);
		        			processDocketList.put(Integer.toString(i), splitConDct[1]);
		        			groupOfDcts.add(splitConDct[1]);
		        			chkConsignmentInput = true;
		        		}
		        	}else{
		        		log.info("TrackingAction - processInput - No Consignment Cover Dockets to "+processType+"");
		        	}
		        } 
	        }else{
	        	log.info("TrackingAction - processInput - No Consignment Cover Dockets Selected or Entered");
	        }
	        
		    //Get all Consignment Cover Dockets - Linked Assignments
		    String[] paramValuesConDctAss = request.getParameterValues("chkConDctAssignment");
		    if (paramValuesConDctAss != null && paramValuesConDctAss.length > 0){
		        for (int i = 0; i < paramValuesConDctAss.length; i++) { 
		            String paramValueConDctAss = paramValuesConDctAss[i]; 
        			boolean testConDctAss = false;
		            if (paramValueConDctAss !=null && !paramValueConDctAss.equals("")){
			            //log.info("TrackingAction - processInput - Consignment Docket - Linked Assignments - Full="+paramValueConDctAss);
		            	log.info("TrackingAction - processInput - List of Consignment assignments is "+paramValueConDctAss);
		        		String []  splitConDctAss  = paramValueConDctAss.trim().split("~");
		        		if(splitConDctAss[0] != null && !splitConDctAss[0].isEmpty()){
		        			log.info("TrackingAction - processInput - Consignment Docket - Linked Assignments to test for Cover Docket="+splitConDctAss[1]+" -- Student Number="+splitConDctAss[2]+" -- Unique Assignment="+splitConDctAss[3]);
		        			if(groupOfDcts.size() != 0){ 
		        				log.info("TrackingAction - processInput - Consignment Docket - Test if Linked Assignments are separate or if entire Cover Docket was Booked already");
		        				for(int j = 0; j < groupOfDcts.size(); j++){
		        					//Only add Linked/Child Assignments if they are NOT in the Consignment Cover Docket list already processed as parent above
		        					log.info("TrackingAction - processInput - Consignment Docket - Testing Cover Docket="+groupOfDcts.get(j));
		        					if (splitConDctAss[1].equalsIgnoreCase(groupOfDcts.get(j))){ 
		        						testConDctAss = true;
		        						
		        					}
		        				}
		        			}else{
		        			   	log.info("TrackingAction - processInput - No Unique Consignment Cover Dockets Linked Assignments");	
		        			}
		        			if(!testConDctAss){
		        				log.info("TrackingAction - processInput - Consignment Docket, Therefore Linked Assignment="+splitConDctAss[1]+" -- Student Number="+splitConDctAss[2]+" -- Unique Assignment="+splitConDctAss[3]+" IS Unique (ADD TO LIST)");
        						processDocketAssignmentList.put(splitConDctAss[1],splitConDctAss[2]+"~"+splitConDctAss[3]);
        						chkConsignmentInput = true;
		        			}else{
		        				log.info("TrackingAction - processInput - Consignment Docket, Therefore Linked Assignment="+splitConDctAss[1]+" -- Student Number="+splitConDctAss[2]+" -- Unique Assignment="+splitConDctAss[3]+" IS NOT Unique (DO NOT ADD TO LIST)");
		        			}
		        		}else{
		        			log.info("TrackingAction - processInput - Split Consignment Docket - Linked Assignments to "+processType+" not possible - Possible malformed input (;)");
		        		}
		        	}else{
		        		log.info("TrackingAction - processInput - No Consignment Docket - Linked Assignments to "+processType+"");
		        	}
		        } 
		    }else{
		    	log.info("TrackingAction - processInput - No Consignment Docket - Linked Assignments Selected or Entered");
	        }
	
		    //Get all Consignment Unique Assignments
		    String[] paramValuesConUnqAss = request.getParameterValues("chkConUniqueAssignment");
		    if (paramValuesConUnqAss != null && paramValuesConUnqAss.length > 0){
		        for (int i = 0; i < paramValuesConUnqAss.length; i++) { 
		            String paramValueConUnqAss = paramValuesConUnqAss[i]; 
		            if (paramValueConUnqAss !=null && !paramValueConUnqAss.equals("")){
			            //log.info("TrackingAction - processInput - Consignment Unique Assignments to Book "+processType+" - Full="+paramValueConUnqAss);
		            	log.info("TrackingAction - processInput - List of unique assignments is "+paramValueConUnqAss);
		        		String []  splitConUnqAss  = paramValueConUnqAss.trim().split("~");
		        		if(splitConUnqAss[0] != null && !splitConUnqAss[0].isEmpty()){
		        			log.info("TrackingAction - processInput - Consignment Unique Assignments to Book "+processType+" - Student Number="+splitConUnqAss[1]+" -- Unique Assignment="+splitConUnqAss[2]+" (ADD TO LIST)");
		        			processAssignmentList.put(splitConUnqAss[1],splitConUnqAss[2]);
		        			chkConsignmentInput = true;
		        		}else{
		        			log.info("TrackingAction - processInput - Split Consignment Unique Assignments to Book "+processType+" not possible - Possible malformed input (;)");
		        		}
		        	}else{
		        		log.info("TrackingAction - processInput - No Consignment Unique Assignments to Book "+processType+"");
		        	}
		        } 
		    }else{
		    	log.info("TrackingAction - processInput - No Consignment Unique Assignments Selected or Entered");
	        }
			
			//Get all Unique Cover Dockets
	        String[] paramValuesDct = request.getParameterValues("chkDocket"); 
	        if (paramValuesDct != null && paramValuesDct.length > 0){
		        for (int i = 0; i < paramValuesDct.length; i++) { 
		            String paramValueDct = paramValuesDct[i]; 
		            if (paramValueDct !=null && !paramValueDct.equals("")){
			            //log.info("TrackingAction - processInput - Unique Cover Dockets - Full="+paramValueDct);
		            	log.info("TrackingAction - processInput - List of docketnumber is "+paramValueDct);
		        		String []  splitDct  = paramValueDct.trim().split("~");
		        		if(splitDct[0] != null && !splitDct[0].isEmpty()){
		        			log.info("TrackingAction - processInput - Unique Cover Dockets to "+processType+"="+splitDct[1]+" (ADD TO LIST)");
		        			processDocketList.put(Integer.toString(i), splitDct[1]);
		        			groupOfDcts.add(splitDct[1]);
		        			chkConsignmentInput = true;
		        		}
		        	}else{
		        		log.info("TrackingAction - processInput - No Unique Cover Dockets to "+processType+"");
		        	}
		        } 
	        }else{
	        	log.info("TrackingAction - processInput - No Unique Cover Dockets Selected or Entered");
	        }
	        
		    //Get all Unique Cover Dockets - Linked Assignments
		    String[] paramValuesUnqDctAss = request.getParameterValues("chkDctAssignment");
		    if (paramValuesUnqDctAss != null && paramValuesUnqDctAss.length > 0){
		        for (int i = 0; i < paramValuesUnqDctAss.length; i++) { 
		            String paramValueUnqDctAss = paramValuesUnqDctAss[i]; 
		            boolean testUnqDctAss = false;
		            if (paramValueUnqDctAss !=null && !paramValueUnqDctAss.equals("")){
			            //log.info("TrackingAction - processInput - Unique Cover Docket - Linked Assignments - Full="+paramValueUnqDctAss);
		            	log.info("TrackingAction - processInput - List of unique assignments is "+paramValueUnqDctAss);
		        		String []  splitDctAss  = paramValueUnqDctAss.trim().split("~");
		        		if(splitDctAss[0] != null && !splitDctAss[0].isEmpty()){
		        			log.info("TrackingAction - processInput - Unique Cover Docket - Linked Assignments to test for Cover Docket="+splitDctAss[1]+" -- Student Number="+splitDctAss[2]+" -- Unique Assignment="+splitDctAss[3]);
		        			if(groupOfDcts.size() != 0){ 
		        				log.info("TrackingAction - processInput -  Unique Cover Docket- Test if Linked Assignments are separate or if entire Cover Docket was Booked already");
		        				for(int j = 0; j < groupOfDcts.size(); j++){
		        					//Only add Linked/Child Assignments if they are NOT in the Consignment Cover Docket list already processed as parent above
		        					log.info("TrackingAction - processInput -  Unique Cover Docket - Testing Cover Docket="+groupOfDcts.get(j));
		        					if (splitDctAss[1].equalsIgnoreCase(groupOfDcts.get(j))){ 
		        						testUnqDctAss = true;
		        					}
		        				}
		        			}else{
		        			   	log.info("TrackingAction - processInput - No Unique Cover Dockets Linked Assignments");	
		        			}
		        			if(!testUnqDctAss){
		        				log.info("TrackingAction - processInput -  Unique Cover Docket, Therefore Linked Assignment="+splitDctAss[1]+" -- Student Number="+splitDctAss[2]+" -- Unique Assignment="+splitDctAss[3]+" IS Unique (ADD TO LIST)");
        						processDocketAssignmentList.put(splitDctAss[1],splitDctAss[2]+"~"+splitDctAss[3]);
        						chkConsignmentInput = true;
		        			}else{
		        				log.info("TrackingAction - processInput - Unique Cover Dockets, Therefore Linked Assignment="+splitDctAss[1]+" -- Student Number="+splitDctAss[2]+" -- Unique Assignment="+splitDctAss[3]+" IS NOT Unique (DO NOT ADD TO LIST)");
		        			}
		        		}else{
		        			log.info("TrackingAction - processInput - Split Unique Cover Docket - Linked Assignments to "+processType+" not possible - Possible malformed input (;)");
		        		}
		        	}else{
		        		log.info("TrackingAction - processInput - No Unique Cover Docket - Linked Assignments to "+processType+"");
		        	}
		        } 
		    }else{
		    	log.info("TrackingAction - processInput - No Unique Cover Docket - Linked Assignments Selected or Entered");
	        }
		    
		    //Get all Unique Assignments
		    String[] paramValuesUnqAss = request.getParameterValues("chkUniqueAssignment");
		    if (paramValuesUnqAss != null && paramValuesUnqAss.length > 0){
		        for (int i = 0; i < paramValuesUnqAss.length; i++) { 
		            String paramValueUnqAss = paramValuesUnqAss[i]; 
		            if (paramValueUnqAss !=null && !paramValueUnqAss.equals("")){
			            //log.info("TrackingAction - processInput - Unique Assignments to Book IN - Full="+paramValueUnqAss);
		            	log.info("TrackingAction - processInput - List of unique assignments is "+paramValueUnqAss);
		        		String []  splitter  = paramValueUnqAss.trim().split("~");
		        		if(splitter[0] != null && !splitter[0].isEmpty()){
		        			log.info("TrackingAction - processInput - Unique Assignments to "+processType+" - Student Number="+splitter[0]+" -- Unique Assignment="+splitter[1]+ " (ADD TO LIST)");
		        			processAssignmentList.put(splitter[0],splitter[1]);
		        			chkConsignmentInput = true;
		        		}else{
		        			log.info("TrackingAction - processInput - Split Unique Assignments to "+processType+" not possible - Possible malformed input (;)");
		        		}
		        	}else{
		        		log.info("TrackingAction - processInput - No Unique Assignments to "+processType+"");
		        	}
		        } 
		    }else{
		    	log.info("TrackingAction - processInput - No Unique Assignments Selected or Entered");
	        }
		   	
		    log.info("TrackingAction - processInput - **************************************************************");

		    if (!chkConsignmentInput){
		    	log.info("TrackingAction - processInput - Final - No Consignment List, Cover Docket or Unique Assignments Selected or Entered");
		    	mapOut.put("Error","You must enter or select at least one consignment list, docket number or individual assignment");
	        }else{
	        	
	        	String result = processData(mapping, form, request, response);
	        	if (result.toUpperCase().contains("SUCCESS")){
		        	log.info("TrackingAction - processInput - Final  - "+processType+" - "+result);
		        	mapOut.put("Success","Book "+processType+" Process - "+result);
		        	String [] getConList = result.split("~");
		        	pTrackingForm.setDisplayShipListNumber(getConList[1]);
		        	processResult(mapping, form, request, response);
	        	}else{
	        		log.info("TrackingAction - processInput - Final  - "+processType+" processData FAILED");
		        	mapOut.put("Error","Failure "+processType+" processData FAILED: "+result);
	        	}
	        }
	    
		}
		
	    log.info("TrackingAction - processInput - **************************************************************");
	    PrintWriter out = response.getWriter();
	    JSONObject jsonObject = JSONObject.fromObject(mapOut);
    	out.write(jsonObject.toString());
    	out.flush();
    	
    	return null; //Returning null to prevent struts from interfering with ajax/json
    }
    

    private String processData(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response){
    	
    	String result = "";
    	String processConsignmentNumber = "";
    	String consignmentListCreationStatus = "";
    	String processStatus = "";
    	String destinationAddress;
    	
    	
    	boolean errorStatus = false;
    	TrackingForm pTrackingForm = (TrackingForm)form;
    	String processType =  pTrackingForm.getUserSelection().toUpperCase();
    	
    	log.info("TrackingAction - processData - *******************************************************");
    	log.info("TrackingAction - processData - processType="+processType);
    	if (processType.equalsIgnoreCase("IN")){
    		destinationAddress = "";
    	}else{
    		destinationAddress = pTrackingForm.getDestinationAddress();
    	}
    	log.info("TrackingAction - processData - destinationAddress=["+destinationAddress+"]");
    	log.info("TrackingAction - processData - *******************************************************");
    	
    	//If BookOUT or NO Consignment List Number exists during BookIN, create new Consignment List
    	boolean isExistList = true;
    	try{
	    	if (processType.equalsIgnoreCase("IN")){
	    		if(pTrackingForm.getConsignmentListNumber() != null && !pTrackingForm.getConsignmentListNumber().isEmpty()){
	    			log.info("TrackingAction - processData - "+processType+" ConsignmentListNumber Exists=["+pTrackingForm.getConsignmentListNumber()+"]");
	    			processConsignmentNumber = pTrackingForm.getConsignmentListNumber();
	    		}else{
	    			log.info("TrackingAction - processData - "+processType+" ConsignmentListNumber Empty");
	    			isExistList = false;
	    		}
	    	}
	    	if (!isExistList || processType.equalsIgnoreCase("OUT")){
	    		log.info("TrackingAction - processData - "+pTrackingForm.getUserSelection()+" OR ConsignmentListNumber Does NOT Exist, Therefore create new Consignment List");
	       		consignmentListCreationStatus = pTrackingDAO.creatingShiplistNumber(webServiceURL, pTrackingForm.getNovelUserId().trim());
	       		log.info("TrackingAction - processData - Creating Consignment List (creatingShiplistNumber) - consignmentListCreationStatus="+consignmentListCreationStatus+", UserID="+pTrackingForm.getNovelUserId().trim());
		
	         	if(pTrackingDAO.checkNumbersOnly(consignmentListCreationStatus)){
	         		processConsignmentNumber = consignmentListCreationStatus ;
	        		log.info("TrackingAction - processData - Creating Consignment List (Success), Consignment List="+processConsignmentNumber);
	         	}else{
	         		errorStatus = true;
	         		result = "Error~"+consignmentListCreationStatus;  
	        		log.info("TrackingAction - processData - Creating Consignment List (Failed), consignmentListCreationStatus=" + consignmentListCreationStatus);
	         	}
	    	}
        }catch(NullPointerException ex){
        	result = "Error~"+consignmentListCreationStatus+ ":" +ex.getMessage();
        	errorStatus = true;
        }catch(Exception e){
        	result = "Error~"+consignmentListCreationStatus+ "~" +e.getMessage();
        	errorStatus = true;
        }
    	
    	if (!errorStatus && processConsignmentNumber != null && !processConsignmentNumber.equals("")){
	    	log.info("TrackingAction - processData - Using Consignment List for processing=" +processConsignmentNumber);
	
	    	int webServiceID = 620922 ; /**BOOK IN OR OUT 620922 A **/
	    	int countDocket = 0;
	    	int countDctAss = 0;
	    	int countUnqAss = 0;
	    	int countTotal = 0;
	    	
	    	String webAddress = bookInOutURL;
	        String fromUserID = "";
	        String toUserID = "";
	        String addFullDctRecords = "";
	        String addDctAssRecords = "";
	        String addUnqAssRecords = "";
	        
	        String baseXML = "<data>" +
	        					"<fields>" +
		        					"<field1>Booking</field1>" +
		        					"<field2>ShipNo</field2>" +
		        					"<field3>DNr</field3>" +
		        					"<field4>StuNr</field4>" +
		        					"<field5>UnqNr</field5>" +
		        					"<field6>status</field6>" +
		        					"<field7>FromUserID</field7>" +
		        					"<field8>sno</field8>" +
		        					"<field9>ToUserID</field9>" +
		        					"<field10>Adr</field10>" +
	        					"</fields>" +
	        					"<records>"; //Append Dockets and Assignments in one large XML;
	        
	        //Set up User ID's and Destination Addresses
        	fromUserID = "CDTRACKING"; //This has to be CDTRACKING as it is the default user for the web services
	        if (processType.equalsIgnoreCase("IN")){
	        	toUserID = pTrackingForm.getNovelUserId().toUpperCase();
	        }else{
	        	if (pTrackingForm.getSaveAddress1() != null && !pTrackingForm.getSaveAddress1().equalsIgnoreCase("")){
	        		if (pTrackingForm.getSaveAddress1().toUpperCase().contains("PERSON")){
	        			//log.info("PERSON="+pTrackingForm.getSaveAddress1());
	        			String [] splitPerson  = pTrackingForm.getSaveAddress1().trim().split("|");
		        		if(splitPerson[0] != null && !splitPerson[0].isEmpty()){
		        			toUserID = splitPerson[1];
		        			//log.info("PERSON Split="+toUserID);
		        		}
	        		}
	        	}
 	        }
	    	
	    	
	    	if(!processDocketList.isEmpty()) {
	    		log.info("TrackingAction - processData - *******************************************************");
	            String docketID = "";
	            String docketNumber = "";
				Set<String> keys = processDocketList.keySet();
		        for(String key:keys){
		        	countDocket++;
		        	countTotal++;
		        	docketNumber = key;
		        	docketNumber = processDocketList.get(key);
		        	
					log.info("TrackingAction - processData - DocketID=" +docketID);
					log.info("TrackingAction - processData - DocketNumber : " +docketNumber);
					log.info("TrackingAction - processData - *******************************************************");
					addFullDctRecords = (new StringBuilder()).append(addFullDctRecords)
							.append("<record id='").append(countTotal).append("'>")
								.append("<value1>").append(processType.toUpperCase().trim()).append("</value1>")
								.append("<value2>").append(processConsignmentNumber.trim()).append("</value2>")
								.append("<value3>").append(docketNumber).append("</value3>")
								.append("<value4>").append("").append("</value4>")
								.append("<value5>").append("").append("</value5>")
								.append("<value6>").append("Y").append("</value6>")
								.append("<value7>").append(fromUserID).append("</value7>")
								.append("<value8>").append("").append("</value8>")
								.append("<value9>").append(toUserID).append("</value9>")
								.append("<value10>").append(destinationAddress).append("</value10>")
							.append("</record>").toString();
				}
		        log.info("TrackingAction - processData - *******************************************************");
		        log.info("TrackingAction - processData - addFullDctRecords XML="+addFullDctRecords);
		        log.info("TrackingAction - processData - *******************************************************");
	    	}else{
	    		log.info("TrackingAction - processData - No Full Cover Dockets");
	    	}
	    	
	    	if(!processDocketAssignmentList.isEmpty()) {
	    		log.info("TrackingAction - processData - *******************************************************");
	            String docketNumber = "";
	            String studentNumber = "";
	            String assignmentNumber = "";
	            String AssignmentString = "";
				Set<String> keys = processDocketAssignmentList.keySet();
		        for(String key:keys){
		        	countDctAss++;
		        	countTotal++;
		        	docketNumber = key;
		            AssignmentString = processDocketAssignmentList.get(key);
		            
					log.info("TrackingAction - processData - docketNumber=" +docketNumber);
					log.info("TrackingAction - processData - Assignment ; String : " +AssignmentString);
					log.info("TrackingAction - processData - *******************************************************");
					
					log.info("TrackingAction - processData - Splitting ; String");
		      		String [] splitDctAss  = AssignmentString.trim().split("~");
	        		if(splitDctAss[0] != null && !splitDctAss[0].isEmpty()){
	        			studentNumber = splitDctAss[0];
	        			assignmentNumber = splitDctAss[1];
	        			
	        			log.info("TrackingAction - processData - Docket StudentNumber : " +studentNumber);
	        			log.info("TrackingAction - processData - Docket UniqueAssignment : " +assignmentNumber);
	        			log.info("TrackingAction - processData - *******************************************************");
	        			addDctAssRecords = (new StringBuilder()).append(addDctAssRecords)
								.append("<record id='").append(countTotal).append("'>")
									.append("<value1>").append(processType.toUpperCase().trim()).append("</value1>")
									.append("<value2>").append(processConsignmentNumber.trim()).append("</value2>")
									.append("<value3>").append(docketNumber).append("</value3>")
									.append("<value4>").append(studentNumber).append("</value4>")
									.append("<value5>").append(assignmentNumber).append("</value5>")
									.append("<value6>").append("Y").append("</value6>")
									.append("<value7>").append(fromUserID).append("</value7>")
									.append("<value8>").append("").append("</value8>")
									.append("<value9>").append(toUserID).append("</value9>")
									.append("<value10>").append(destinationAddress).append("</value10>")
								.append("</record>").toString();
					}
				}
			    log.info("TrackingAction - processData - *******************************************************");
			    log.info("TrackingAction - processData - addFullDctRecords XML="+addDctAssRecords);
			    log.info("TrackingAction - processData - *******************************************************");
	    	}else{
	    		log.info("TrackingAction - processData - No Cover Docket Linked Assignments");
	    	}
	    	
	    	if(!processAssignmentList.isEmpty()) {
	    		log.info("TrackingAction - processData - *******************************************************");
	    		 String studentNumber = "";
	             String assignmentNumber = "";
				Set<String> keys = processAssignmentList.keySet();
		        for(String key:keys){
		        	countUnqAss++;
		        	countTotal++;
		        	studentNumber = key;
		        	assignmentNumber = processAssignmentList.get(key);
		        	
					log.info("TrackingAction - processData - Unique studentNumber=" +studentNumber);
					log.info("TrackingAction - processData - Unique AssignmentNumber=" +assignmentNumber);
					log.info("TrackingAction - processData - *******************************************************");
					addUnqAssRecords = (new StringBuilder()).append(addUnqAssRecords)
							.append("<record id='").append(countTotal).append("'>")
								.append("<value1>").append(processType.toUpperCase().trim()).append("</value1>")
								.append("<value2>").append(processConsignmentNumber.trim()).append("</value2>")
								.append("<value3>").append("").append("</value3>")
								.append("<value4>").append(studentNumber).append("</value4>")
								.append("<value5>").append(assignmentNumber).append("</value5>")
								.append("<value6>").append("Y").append("</value6>")
								.append("<value7>").append(fromUserID).append("</value7>")
								.append("<value8>").append("").append("</value8>")
								.append("<value9>").append(toUserID).append("</value9>")
								.append("<value10>").append(destinationAddress).append("</value10>")
							.append("</record>").toString();
				}
			    log.info("TrackingAction - processData - *******************************************************");
			    log.info("TrackingAction - processData - addFullDctRecords XML="+addUnqAssRecords);
			    log.info("TrackingAction - processData - *******************************************************");
	    	}else{
	    		log.info("TrackingAction - processData - No Unique Assignments");
	    	}
	    	
	    	//Close XML
	    	log.info("TrackingAction - processData - *******************************************************");
	    	String processXML = "";
	    	processXML = (new StringBuilder()).append(baseXML).append(addFullDctRecords).append(addDctAssRecords).append(addUnqAssRecords).toString();
	    	processXML = (new StringBuilder()).append(processXML).append("</records></data>".trim()).toString();
    		log.info("TrackingAction - processData - processXML=" +processXML);
	    	processXML = processXML.trim().replace(" ", "%20");
	    	log.info("TrackingAction - processData - processXML Spaces Removed=" +processXML);
            String urlParameters = (new StringBuilder()).append("&ID=").append(webServiceID).append("&USERID=").append(fromUserID).append("&inXML=").append(processXML.trim()).toString();
            log.info("TrackingAction - processData - urlParameters=" +urlParameters);
            String URL = (new StringBuilder()).append(webAddress).append(urlParameters).toString();
            log.info("TrackingAction - processData - *******************************************************");
            try{
            	log.info("TrackingAction - processData - Executing WebService="+webServiceID);
            	
            	processStatus = pGateWay.responseFromWebService(URL, urlParameters);
            	log.info("TrackingAction - processData - *******************************************************");
            	log.info("TrackingAction - processData - Executing processStatus="+processStatus);
            	log.info("TrackingAction - processData - *******************************************************");

            	if(processStatus.contains("OK") || processStatus.contains("SUCCESS")){
            		log.info("TrackingAction - processData - (Book "+processType+") - SUCCESS");
            		
            		//Get Date & Status for Consignment number for Report/Result
            		int websrcConsignmentId = 121868; /* GET_SHPLST 121868 A */
            		ArrayList getResult = pGateWay.showConsignmentListInfo(webServiceURL ,websrcConsignmentId, processConsignmentNumber,"","id", "value4");
                	for(Iterator resultConsign = getResult.iterator(); resultConsign.hasNext();){
                        KeyValue testConsign = (KeyValue)resultConsign.next();
                       log.info("TrackingAction - searchDetail - testConsign.getValue="+testConsign.getValue());
                       if (testConsign.getValue() != null && !testConsign.getValue().isEmpty()){
                    	   pTrackingForm.setDisplayShipListDate(testConsign.getValue());
                       }
                	}
            		result = "Success~"+processConsignmentNumber+"~"+pTrackingForm.getDisplayShipListDate();
            		
            		//Once Successful, write Destination Address to ADR for next Book Out
            		if (processType.equalsIgnoreCase("OUT")){
            			String addressStatus;
            			int webSetAddressId = 710033;
            			
            			//Note: Using Entered Address 1 as 2 and 2 as 3 etc as Address 1 is used for User ID
            			log.info("TrackingAction - processData - (Final Address) - userID="+pTrackingForm.getNovelUserId()+", Line1="+pTrackingForm.getSaveAddress1()+", Line2="+pTrackingForm.getSaveAddress2()+", Line3="+pTrackingForm.getSaveAddress3()+", Line4="+pTrackingForm.getSaveAddress4()+", Consignment="+processConsignmentNumber+", Postal="+pTrackingForm.getSavePostal());
            			addressStatus = pGateWay.setChangedAddress(webServiceURL ,webSetAddressId, pTrackingForm.getNovelUserId(), pTrackingForm.getSaveAddress1(), pTrackingForm.getSaveAddress2(), pTrackingForm.getSaveAddress3(), pTrackingForm.getSaveAddress4(), processConsignmentNumber, pTrackingForm.getSavePostal(), "value1", "value2");
               			
            			if(addressStatus.contains("OK") || addressStatus.contains("SUCCESS")){
                    		log.info("TrackingAction - processData - (addressStatus) - SUCCESS");
               			}else{
               				log.info("TrackingAction - processData - (addressStatus) - FAILED");
               				result = "Failed; AddressUpdate<br/>"+processStatus+"<br/>"+addressStatus;
                    		errorStatus = true;
               			}
            		}
            	}else{
            		log.info("TrackingAction - processData - (Book OUT) - FAILED<br/>"+processStatus);
            		result = "Failed~"+processStatus;
            		errorStatus = true;
            	}
            }catch(Exception e){
            	result = "Error~"+e;
            	errorStatus = true;
            }
    	}else{
    		//errorStatus IS true;
    		log.info("TrackingAction - processData - *******************************************************");
    		log.info("TrackingAction - processData - ErrorStatus=" +errorStatus+ "Cannot Continue");
    		result = "Failed~"+errorStatus;
    	}
    	
		log.info("TrackingAction - processData - Final *************************************************");
		log.info("TrackingAction - processData - Final Result="+result);
		log.info("TrackingAction - processData - Final *************************************************");
    	return result;
    }
    
    public ActionForward processResult(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response){

	    //Get Book IN/OUT Process Results
    	
    	//PRINT_CONSIGNMENT_LIST_DCT 945964 A 
    	//PRINT_CONSIGNMENT_LIST_STU 406414 A 
    	// PRINT_RECEIPT 562557 A 
    	// PRINT_RECEIPT_DCT 697460 A 
    	// PRINT_RECEIPT_STU 333995 A 

    		log.info("TrackingAction - processResult - *******************************************************");
	
	    	String url ;
	    	if ("my.unisa.ac.za".equals(host)){
	            url = "http://www2.unisa.ac.za/aol/asp/sql_exec_report4.asp?Export=XML&ID=";
	    	}else if("mydev.int.unisa.ac.za".equals(host)){
	    	    url = "http://stratusdev.unisa.ac.za/aol/asp/sql_exec_report4.asp?Export=XML&ID=";
	    	}else if("myqa.int.unisa.ac.za".equals(host)){
	    		 url = "http://stratusqa.unisa.ac.za/aol/asp/sql_exec_report4.asp?Export=XML&ID=";
	    	}else {
	    		url = "http://stratusdev.unisa.ac.za/aol/asp/sql_exec_report4.asp?Export=XML&ID=";
	    	}
	    	
	    	TrackingForm pTrackingForm = (TrackingForm)form;
	    	String processType =  pTrackingForm.getUserSelection().toUpperCase();
	    	
	    	log.info("TrackingAction - processData - processType="+processType);
	    	if (processType.equalsIgnoreCase("IN")){
	    		
	    	}
	
	       //log.info("TrackingAction - processResult - Result URL is " + url+"ShipNo="+shipListNumber+"&Status="+processType+"&SYSTEM=SQL");
	       log.info("TrackingAction - processResult - *******************************************************");
	       //Test to see if ShipList created above does actually exist
	       	try {
	        	//Get Shiplist Dockets and Assignments for results page only
	       		log.info("TrackingAction - processResult - Final *******************************************************");
	       		log.info("TrackingAction - processResult - Final Retrieve Consignment results after Book "+processType);
	       		log.info("TrackingAction - processResult - Final *******************************************************");
				displayConsignmentDetails(mapping,form,request, response, "Result");
				log.info("TrackingAction - processResult - Return from displayConsignmentDetails");
			} catch (Exception e) {
				//messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("message.generalmessage", "Error retrieving BOOK_IN Result" + e));
			}
	       	log.info("TrackingAction - processResult - *******************************************************");
	    try{

	       
	    }catch(NullPointerException ex){
	        //messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("message.generalmessage",ex.getMessage()));
	    }
	    log.info("TrackingAction - processResult - *******************************************************");
	    log.info("TrackingAction - processResult - Redirect to RESULT");
	    log.info("TrackingAction - processResult - *******************************************************");
	    return null;
	}
    
    public ActionForward searchDetail(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException{
        
    	/** Edmund Enumerate through all parameters received
	 	 * @return **/
	   	//getAllRequestParamaters(request, response);
	   	
    	TrackingForm pTrackingForm = (TrackingForm)form;
        ActionMessages messages = new ActionMessages();
        HttpSession session = request.getSession(true);
        Map<String, String> displayDocketList = new LinkedHashMap<String, String>();
    	Map<String, String> displayDocketAssignmentList = new LinkedHashMap<String, String>();
    	Map<String, String> displayAssignmentList = new LinkedHashMap<String, String>();
    	JSONObject userObj = new JSONObject();
    	
        try
        {
        	log.info("TrackingAction - searchDetail - **************************************************************");
            String searchType =  pTrackingForm.getSearchRadio();
            String xxx = request.getParameter("searchRadio");
            log.info("TrackingAction - searchDetail - searchType="+searchType+", xxx="+xxx);
            
            if (searchType == null || searchType.equals("")){
            	userObj.put("Empty", "No Search criteria selected.");
            }else{
            	log.info("TrackingAction - searchDetail - SearchString="+pTrackingForm.getSearchString().trim());
            	log.info("TrackingAction - searchDetail - **************************************************************");
	            int websrcConsignmentId = 121868; /* GET_SHPLST 121868 A */
	            int websrcConsignDctId = 697460; /* PRINT_RECEIPT_DCT 697460 A */
	            int websrcConDctAssId = 702449;/* LIST_ASSIGN_ON_DOCKET 702449 A */
	            int websrcConsignAssId = 333995; /* PRINT_RECEIPT_STU 333995 A */
	            
	            int websrcDocketId = 12345;
	            int websrcAssignmentId = 12345;
	            int websrcUserId = 12345;
	            if(pTrackingForm.getSearchString() != null && pTrackingForm.getSearchString().length() != 0){
	            	log.info("TrackingAction - searchDetail - searchType="+searchType+", SearchString="+pTrackingForm.getSearchString().trim());
	                if(searchType.equalsIgnoreCase("Consignment")){
	                	ArrayList getResult = pGateWay.showConsignmentListInfo(webServiceURL ,websrcConsignmentId, pTrackingForm.getSearchString().trim(),"","id", "value4");
	                    //results = pGateWay.getSearch(webServiceURL ,websrcConsignmentId, pTrackingForm.getSearchString(),"Value1", "Value2");
	                	for(Iterator result = getResult.iterator(); result.hasNext();){
	                        KeyValue test1 = (KeyValue)result.next();
	                       log.info("TrackingAction - searchDetail - test1.getValue="+test1.getValue());
	                        if(test1.getValue().contains("Consignment list number invalid")){
	                            messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("message.generalmessage", "Consignment list number is not valid."));
	                            addErrors(request, messages);
	                        }else{
	                        	pTrackingForm.setDate(test1.getValue());
	                        	userObj.put("Consignment",pTrackingForm.getSearchString().trim()+"~"+test1.getValue());
	                        	//Get Cover Dockets from Database/Web Service
	                     	   	log.info("TrackingAction - searchDetail - consignmentNumber="+pTrackingForm.getSearchString().trim());
	                     	    log.info("TrackingAction - searchDetail - **************************************************************");
	                            ArrayList getValues = pGateWay.showConsignmentListInfo(webServiceURL ,websrcConsignDctId, pTrackingForm.getSearchString().trim(),"" ,"id", "value1");
	                            Iterator it = getValues.iterator();
	                            String errMsgDct="";
	                            String errMsgStu="";
	                            int countDct = 0;
	                            if(it.hasNext()){
	                            	while(it.hasNext()){
	                            		KeyValue testDct = (KeyValue) it.next();
	                	    			if(testDct.getValue().equals("No records returned")){
	                	    				errMsgDct = " Docket Numbers ";
	                	    				log.info("TrackingAction - searchDetail  - Docket Numbers: No records returned");
	                	    			}else{
	                	    				countDct++;
	                	    				log.info("TrackingAction - searchDetail  - Docket Numbers Added="+testDct.getValue());
	                	    				log.info("TrackingAction - searchDetail - **************************************************************");
	                	    				//Get Assignments per Docket
	                	    				log.info("TrackingAction - searchDetail - Retrieve linked Assignments for docketNumber="+testDct.getValue());
	                	    				ArrayList dctAssignments = pGateWay.getDocketAssignments(webServiceURL, websrcConDctAssId ,testDct.getValue(), "value1", "value2");
	                	    				Iterator dctAssign = dctAssignments.iterator();
	                	    				int conDctAssCounter = 0;
	                	    				if(dctAssign.hasNext()){
	                	    					List<Assignment> dctAssignList = new ArrayList<Assignment>();
	                	    					while(dctAssign.hasNext()){
	                	    						KeyValue testDctAss = (KeyValue) dctAssign.next();
	                	    						if(testDctAss.getValue().equals("No records returned")){
	                	    							log.info("TrackingAction - searchDetail - No linked Assignments returned");
	            									}else{
	            										conDctAssCounter++;
	            										log.info("TrackingAction - searchDetail  - Linked Assignments: Successful records returned - Student="+testDctAss.getKey()+", Assignment="+testDctAss.getValue());
	            										// Generate a Docket-Assignment map
	        	                	    				displayDocketList.put(Integer.toString(countDct), conDctAssCounter+"~"+testDct.getValue()+"~"+testDctAss.getKey()+"~"+testDctAss.getValue());
	            										log.info("TrackingAction - searchDetail - **************************************************************");
	            									}
	            							    }
	                	    				}else{
	                	    					log.info("TrackingAction - searchDetail - No linked Assignments returned");
	                	    					displayDocketList.put(Integer.toString(countDct), "1~"+testDct.getValue()+"~~");
	                	    				}
	                	    			}	
	                	    		}
	                            	if (!displayDocketList.isEmpty()){
	                            		userObj.put("Dockets",displayDocketList);
		                            }
	                            }else{
	                            	errMsgDct = " Docket Numbers ";
	                				log.info("TrackingAction - searchDetail  - Docket Numbers: No records returned");
	                            }
	                            
	                            
	                            //Getting Unique Assignments
	                            log.info("TrackingAction - searchDetail - consignmentNumber="+consignmentNumber);
	                            ArrayList uniqueNumbers = pGateWay.showUniqueAssignmentListInfo(webServiceURL ,websrcConsignAssId, consignmentNumber,"","id", "value1", "value2");
	                            Iterator uniqueNumber = uniqueNumbers.iterator();
	                            if(uniqueNumber.hasNext()){
	                            	while(uniqueNumber.hasNext()){
	                	    			KeyValue testAss = (KeyValue) uniqueNumber.next();
	                	    			if(testAss.getValue1().equals("No records returned")){
	                	    				errMsgStu = " Unique Assignments ";
	                	    				log.info("TrackingAction - searchDetail  - Unique Assignments: No records returned");
	                	   			 	}else{
	                	   			 		log.info("TrackingAction - searchDetail  - Unique Assignments="+testAss.getValue1()+ "----" + testAss.getValue2());
	                	   			 		displayAssignmentList.put(Integer.toString(countDct), testAss.getValue1()+"~"+testAss.getValue2());
	                	   			 	}
	                	            }
	                            }else{
	                            	errMsgStu = " Unique Assignments ";
	                				log.info("TrackingAction - searchDetail  - Unique Assignments: No records returned");
	                            }
	                            if (!displayAssignmentList.isEmpty()){
	                            	userObj.put("Assignments",displayAssignmentList);
	                            }
	                            /*
	                            log.info("TrackingAction - searchDetail  - Error Checking - Dockets: "+errMsgDct+", Student: "+errMsgStu);
	                            log.info("TrackingAction - searchDetail  - Error Checking Sizes - Dockets: "+displayDockets.size()+", Student: "+displayUniqueNumbers.size());
	                            
	                            if(displayDockets.size()== 0 && displayUniqueNumbers.size()== 0){ 
	                	            if(!errMsgDct.equals("") || !errMsgStu.equals("")){
	                	            	log.info("TrackingAction - searchDetail  - Error Checking");
	                	            	String newErrorMsg = "Consignment list " + consignmentNumber + " doesn't contain any ";
	                	            	StringBuilder newError;
	                	            	if(!errMsgDct.equals("") && (errMsgStu.equals("") && displayUniqueNumbers.isEmpty())){
	                	            		log.info("TrackingAction - searchDetail  - Docket Error");
	                	            		newError = (new StringBuilder()).append(newErrorMsg).append(errMsgDct);
	                	            	}else if(errMsgDct.equals("") && !errMsgStu.equals("")){
	                	            		log.info("TrackingAction - searchDetail  - Student Error");
	                	            		newError = (new StringBuilder()).append(newErrorMsg).append(errMsgStu);
	                	            	}else{
	                	            		log.info("TrackingAction - searchDetail  - Docket & Student Error");
	                	            		newError = (new StringBuilder()).append(newErrorMsg).append(errMsgDct).append("or").append(errMsgStu);
	                	            	}
	                	            	log.info("TrackingAction - searchDetail  - Final Error: "+newError);
	                	            	messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("message.generalmessage", newError));
	                	                addErrors(request, messages);
	                	            }
	                            }*/
	                        }
	                    }
	                }else if(searchType.equalsIgnoreCase("Docket")){
	                	//results = pGateWay.getSearch(webServiceURL ,websrcDocketId, pTrackingForm.getSearchString(),"Value1", "Value2");
	                }else if(searchType.equalsIgnoreCase("Assignment")){
	                    //results = pGateWay.getSearch(webServiceURL ,websrcAssignmentId, pTrackingForm.getSearchString(),"Value1", "Value2");
	                }else if(searchType.equalsIgnoreCase("Person")){
	                    //results = pGateWay.getSearch(webServiceURL ,websrcUserId, pTrackingForm.getSearchString(),"Value1", "Value2");
	                }
	                
	            }else{
	            	userObj.put("Error", "No Search String Entered");
	            }
            }

        }catch(Exception ex){
        	userObj.put("Error", "Search Web Server failed.<br/>Please try again.<br/>" + ex);
        }
        
    	// Convert the map to json
    	PrintWriter out = response.getWriter();
       	JSONObject jsonObject = JSONObject.fromObject(userObj);
       	log.info("TrackingAction - searchDetail - Final - **************************************************************");
       	log.info("TrackingAction - searchDetail - Final - jsonObject="+jsonObject.toString());
       	log.info("TrackingAction - searchDetail - Final - **************************************************************");
       	out.write(jsonObject.toString());
       	out.flush();

    	return null; //Returning null to prevent struts from interfering with ajax/json
    }

    
     public ActionForward back(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	log.info("TrackingAction - back - Calling back method");
    	TrackingForm pTrackingForm = (TrackingForm)form;
        HttpSession session = request.getSession(true);
        String returnType;
        if(pTrackingForm.getUserSelection() != null && !pTrackingForm.getUserSelection().equals("") && pTrackingForm.getUserSelection().equals("out")){
        	log.info("TrackingAction - back - Calling back method from book out");
        	resetForm(pTrackingForm,"back");
            returnType = USER_SELECTION;
        }else{
        	log.info("TrackingAction - back - Calling back method from book in");
        	if(pTrackingForm.getListOfDocketNumbers() != null && !pTrackingForm.getListOfDocketNumbers().isEmpty() && pTrackingForm.getListOfDocketNumbers().size() > 0){
        		pTrackingForm.getListOfDocketNumbers().clear();
            }
            if(pTrackingForm.getListOfStudentAssignments() != null && !pTrackingForm.getListOfStudentAssignments().isEmpty() && pTrackingForm.getListOfStudentAssignments().size() > 0){
                pTrackingForm.getListOfStudentAssignments().clear();
            }
            resetForm(pTrackingForm,"back");
            returnType = USER_SELECTION;
        }
        return mapping.findForward(returnType);
    }
    

 	private Docket populateDocket(String docketID, String docketNumber) {	
		log.info("TrackingAction - populateDocket - *******************************************************");
		log.info("TrackingAction - populateDocket - DocketID="+docketID+", Docket Number="+docketNumber);
		log.info("TrackingAction - populateDocket - *******************************************************");

		Docket docket = new Docket();
		docket.setDocketID(docketID);
		docket.setDocketNumber(docketNumber);		
		return docket;
	}
	
	private Assignment populateAssignment(String studentNumber, String uniqueAssignmentNumber) {	
		log.info("TrackingAction - populateAssignment - *******************************************************");
		log.info("TrackingAction - populateAssignment - StudentNumber="+studentNumber+", Assignment Number="+uniqueAssignmentNumber);
		log.info("TrackingAction - populateAssignment - *******************************************************");
		Assignment assignment = new Assignment();
		assignment.setStudentNumber(studentNumber);
		assignment.setUniqueAssignmentNumber(uniqueAssignmentNumber);
		return assignment;	
	}
	
	private Consignment populateConsignment(String consignmentNumber, String date) {
		Consignment consignment = new Consignment();
		consignment.setConsignmentNumber(consignmentNumber);
		consignment.setDate(date);
		return consignment;
	}
	
	public boolean duplicateCheckConDockets (String docketNumber){
		boolean check = false;
		log.info("TrackingAction - duplicateCheckConDockets - *******************************************************");
		//Use main Cover Docket list to check if docket is already in list of Cover Dockets
		if(!masterDocketList.isEmpty()) {
			for(Docket docket : masterDocketList) {
				log.info("TrackingAction - duplicateCheckConDockets - Docket Id : " +docket.getDocketID());
				log.info("TrackingAction - duplicateCheckConDockets - Docket Number : " +docket.getDocketNumber());
				log.info("TrackingAction - duplicateCheckConDockets - *******************************************************");
				if (docketNumber.equalsIgnoreCase(docket.getDocketNumber())){
					log.info("TrackingAction - duplicateCheckConDockets - Docket Number Already Exists");
					check = true;
				}else{
					log.info("TrackingAction - duplicateCheckConDockets - Docket Number unique, therefore NOT in List");
				}
			}
		}else{
			log.info("TrackingAction - duplicateCheckConDockets - No Cover Dockets in Master List");
			log.info("TrackingAction - duplicateCheckConDockets - *******************************************************");
		}
		return check;
	}

	public boolean duplicateCheckConAssignment (String studentNumber, String assignmentNumber){
		boolean check = false;
	   //Check of unique Assignment list for duplicate testing
	   log.info("TrackingAction - duplicateCheckConAssignment - *******************************************************");
	   log.info("TrackingAction - duplicateCheckConAssignment - Checking Unique Assignment Master List");
	   log.info("TrackingAction - duplicateCheckConAssignment - *******************************************************");
		if(!mapUnqAssignments.isEmpty()) {
            String AssignmentID = "";
            String AssignmentString = "";
			Set<String> keys = mapUnqAssignments.keySet();
	        for(String key:keys){

	            AssignmentID = key;
	            AssignmentString = mapUnqAssignments.get(key);
				log.info("TrackingAction - duplicateCheckConAssignment - Assignment ID : " +AssignmentID);
				log.info("TrackingAction - duplicateCheckConAssignment - Assignment ; String : " +AssignmentString);
				log.info("TrackingAction - duplicateCheckConAssignment - *******************************************************");
				log.info("TrackingAction - duplicateCheckConAssignment - Splitting ; String");
	      		String []  splitUnqAss  = AssignmentString.trim().split("~");
        		if(splitUnqAss[0] != null && !splitUnqAss[0].isEmpty()){
        			log.info("TrackingAction - duplicateCheckConAssignment - Student Number : " +splitUnqAss[0]);
        			log.info("TrackingAction - duplicateCheckConAssignment - Unique Assignment : " +splitUnqAss[1]);
        			log.info("TrackingAction - duplicateCheckConAssignment - *******************************************************");
        			if(studentNumber.equalsIgnoreCase(splitUnqAss[0]) && assignmentNumber.equalsIgnoreCase(splitUnqAss[1])) {
       					log.info("TrackingAction - duplicateCheckConAssignment - Assignment Already Exists");
       					check = true;
       				}else{
       					log.info("TrackingAction - duplicateCheckConAssignment - Assignment unique, therefore NOT in List");
       				}
				}
			}
			log.info("TrackingAction - duplicateCheckConAssignment - *******************************************************");
		}else{
			log.info("TrackingAction - duplicateCheckConAssignment - No Unique Assignments in Master List");
			log.info("TrackingAction - duplicateCheckConAssignment - *******************************************************");
		}
		return check;
	}
	
	public boolean duplicateCheckDctAssignment (String studentNumber, String assignmentNumber){
		boolean check = false;
		//Use main Cover Docket list to check if Consignment List Unique Assignment is already in list of Cover Dockets Linked Assignments
		log.info("TrackingAction - duplicateCheckDctAssignment - Checking Cover Docket Master List");
		log.info("TrackingAction - duplicateCheckDctAssignment - *******************************************************");
		if(!masterDocketList.isEmpty()) {
			for(Docket docket : masterDocketList) {
				log.info("TrackingAction - duplicateCheckDctAssignment - Docket Id : " +docket.getDocketID());
				log.info("TrackingAction - duplicateCheckDctAssignment - Docket Number : " +docket.getDocketNumber());
				log.info("TrackingAction - duplicateCheckDctAssignment - *******************************************************");
				if(docket.getAssignments() != null ) {
					for (Assignment assignment : docket.getAssignments()){
						log.info("TrackingAction - duplicateCheckDctAssignment - StudentNumber : " +assignment.getStudentNumber().trim());
						log.info("TrackingAction - duplicateCheckDctAssignment - UniqueAssignmentNumber : " +assignment.getUniqueAssignmentNumber().trim());
						if (studentNumber.equalsIgnoreCase(assignment.getStudentNumber().trim()) && assignmentNumber.equalsIgnoreCase(assignment.getUniqueAssignmentNumber().trim())){
							log.info("TrackingAction - duplicateCheckDctAssignment - Assignment Already Exists in Cover Docket List : "+docket.getDocketNumber());
							check = true;
						}else{
							log.info("TrackingAction - duplicateCheckDctAssignment - Assignment unique, therefore NOT in Cover Docket "+docket.getDocketNumber()+" List");
						}
					}
				}else{
					log.info("TrackingAction - duplicateCheckDctAssignment - No Cover Dockets linked Assignments in Master Docket List");
				}

			}
		}else{
			log.info("TrackingAction - duplicateCheckDctAssignment - No Cover Dockets in Master List");
			log.info("TrackingAction - duplicateCheckDctAssignment - *******************************************************");
		}

		return check;
	}

	public boolean duplicateCheckDockets (String docketNumber){
		boolean check = false;
		log.info("TrackingAction - duplicateCheckDockets - *******************************************************");
		//Use main consignment list to check if docket is already in Consignment list of Cover Dockets
		if(!masterConsignmentList.isEmpty()) {
			for(Consignment consignDupTest : masterConsignmentList) {
				log.info("TrackingAction - duplicateCheckDockets - Consignment Number : " +consignDupTest.getConsignmentNumber());
				log.info("TrackingAction - duplicateCheckDockets - Consignment Date : " +consignDupTest.getDate());			
				log.info("TrackingAction - duplicateCheckDockets - *******************************************************");
				
				if(consignDupTest.getDockets() != null ) {
					for(Docket docket : consignDupTest.getDockets()) {
						log.info("TrackingAction - duplicateCheckDockets - Docket Id : " +docket.getDocketID());
						log.info("TrackingAction - duplicateCheckDockets - Docket Number : " +docket.getDocketNumber());
						log.info("TrackingAction - duplicateCheckDockets - *******************************************************");
						if (docketNumber.equalsIgnoreCase(docket.getDocketNumber())){
							log.info("TrackingAction - duplicateCheckDockets - Docket Number Already Exists in Consignment List");
							check = true;
						}else{
							log.info("TrackingAction - duplicateCheckDockets - Docket Number unique, therefore NOT in consignment List");
						}
					}
				}else{
					log.info("TrackingAction - duplicateCheckDockets - No Cover Dockets in Master Consignment List");
					log.info("TrackingAction - duplicateCheckDockets - *******************************************************");
				}
			}
		}else{
			log.info("TrackingAction - duplicateCheckDockets - No Consignments in Master List");
			log.info("TrackingAction - duplicateCheckDockets - *******************************************************");
		}
		//Use main Cover Docket list to check if docket is already in list of Cover Dockets
		if(!masterDocketList.isEmpty()) {
			for(Docket docket : masterDocketList) {
				log.info("TrackingAction - duplicateCheckDockets - Docket Id : " +docket.getDocketID());
				log.info("TrackingAction - duplicateCheckDockets - Docket Number : " +docket.getDocketNumber());
				log.info("TrackingAction - duplicateCheckDockets - *******************************************************");
				if (docketNumber.equalsIgnoreCase(docket.getDocketNumber())){
					log.info("TrackingAction - duplicateCheckDockets - Docket Number Already Exists");
					check = true;
				}else{
					log.info("TrackingAction - duplicateCheckDockets - Docket Number unique, therefore NOT in List");
				}
			}
		}else{
			log.info("TrackingAction - duplicateCheckDockets - No Cover Dockets in Master List");
			log.info("TrackingAction - duplicateCheckDockets - *******************************************************");
		}
		return check;
	}				
	
	
	public boolean duplicateCheckConUniqueAssignment (String studentNumber, String assignmentNumber){
		boolean check = false;
		log.info("TrackingAction - duplicateCheckConUniqueAssignment - *******************************************************");
		log.info("TrackingAction - duplicateCheckConUniqueAssignment - Checking Consignment Master List for Unique Assignments");
		log.info("TrackingAction - duplicateCheckConUniqueAssignment - Checking StudentNumber="+studentNumber+", Unique Assignment="+assignmentNumber);
		log.info("TrackingAction - duplicateCheckConUniqueAssignment - *******************************************************");
		//Use main consignment list to check if assignment is already in Consignment list
		if(!masterConsignmentList.isEmpty()) {
			for(Consignment consignDupTest : masterConsignmentList) {
				log.info("TrackingAction - duplicateCheckConUniqueAssignment - Consignment Number : " +consignDupTest.getConsignmentNumber());
				log.info("TrackingAction - duplicateCheckConUniqueAssignment - Consignment Date : " +consignDupTest.getDate());			
				log.info("TrackingAction - duplicateCheckConUniqueAssignment - *******************************************************");
				if(consignDupTest.getAssignments() != null ) {
					for (Assignment assignment : consignDupTest.getAssignments()){
						String []  splitUnqAss  = assignment.getUniqueAssignmentNumber().trim().split("~");
		        		if(splitUnqAss[0] != null && !splitUnqAss[0].isEmpty()){
		        			log.info("TrackingAction - duplicateCheckConUniqueAssignment - Consignment Student Number : " +splitUnqAss[0].trim());
		        			log.info("TrackingAction - duplicateCheckConUniqueAssignment - Consignment Unique Assignment : " +splitUnqAss[1].trim());
		        			log.info("TrackingAction - duplicateCheckAssignment - *******************************************************");
		        			if(studentNumber.equalsIgnoreCase(splitUnqAss[0].trim()) && assignmentNumber.equalsIgnoreCase(splitUnqAss[1].trim())) {
		        				log.info("TrackingAction - duplicateCheckAssignment - Assignment Already Exists in Consignment List : "+consignDupTest.getConsignmentNumber());
		       					check = true;
		       				}else{
		       					log.info("TrackingAction - duplicateCheckAssignment - Assignment unique, therefore NOT in Consignment List:" +consignDupTest.getConsignmentNumber());
		       				}
						}
					}
				}else{
					log.info("TrackingAction - duplicateCheckAssignment - No Unique Assignments in Master Consignment List:" +consignDupTest.getConsignmentNumber());
				}
			}
		}else{
			log.info("TrackingAction - duplicateCheckConUniqueAssignment - No Consignments in Master List:");
			log.info("TrackingAction - duplicateCheckConUniqueAssignment - *******************************************************");
		}
		return check;
	}

	public boolean duplicateCheckUniqueAssignment (String studentNumber, String assignmentNumber){
		boolean check = false;
		//Check of unique Assignment list for duplicate testing
		log.info("TrackingAction - duplicateCheckUniqueAssignment - *******************************************************");
		log.info("TrackingAction - duplicateCheckUniqueAssignment - Checking Assignment Master List for Student="+studentNumber+", Assignment="+assignmentNumber);
		log.info("TrackingAction - duplicateCheckUniqueAssignment - *******************************************************");
		if(!mapUnqAssignments.isEmpty()) {
            String AssignmentID = "";
            String AssignmentString = "";
			Set<String> keys = mapUnqAssignments.keySet();
	        for(String key:keys){

	            AssignmentID = key.trim();
	            AssignmentString = mapUnqAssignments.get(key).trim();
				log.info("TrackingAction - duplicateCheckUniqueAssignment - Found Assignment ID : " +AssignmentID);
				log.info("TrackingAction - duplicateCheckUniqueAssignment - Found Assignment ; String : " +AssignmentString);
				log.info("TrackingAction - duplicateCheckUniqueAssignment - *******************************************************");
				log.info("TrackingAction - duplicateCheckUniqueAssignment - Splitting ; String");
	      		String []  splitUnqAss  = AssignmentString.trim().split("~");
        		if(splitUnqAss[0] != null && !splitUnqAss[0].isEmpty()){
        			log.info("TrackingAction - duplicateCheckUniqueAssignment - Found Student Number : " +splitUnqAss[0].trim());
        			log.info("TrackingAction - duplicateCheckUniqueAssignment - Found Unique Assignment : " +splitUnqAss[1].trim());
        			log.info("TrackingAction - duplicateCheckUniqueAssignment - *******************************************************");
        			if(studentNumber.equalsIgnoreCase(splitUnqAss[0].trim()) && assignmentNumber.equalsIgnoreCase(splitUnqAss[1].trim())) {
       					log.info("TrackingAction - duplicateCheckUniqueAssignment - Assignment Already Exists");
       					check = true;
       				}else{
       					log.info("TrackingAction - duplicateCheckUniqueAssignment - Assignment unique, therefore NOT in List");
       				}
				}
			}
			log.info("TrackingAction - duplicateCheckUniqueAssignment - *******************************************************");
		}else{
			log.info("TrackingAction - duplicateCheckUniqueAssignment - No Unique Assignments in Master List");
			log.info("TrackingAction - duplicateCheckUniqueAssignment - *******************************************************");
		}
		return check;
	}
	
	public boolean duplicateCheckAllAssignments (String studentNumber, String assignmentNumber){
		boolean check = false;
		log.info("TrackingAction - duplicateCheckAllAssignments - *******************************************************");
		log.info("TrackingAction - duplicateCheckAllAssignments - Checking Consignment Master List for Student="+studentNumber+", Assignment="+assignmentNumber);
		log.info("TrackingAction - duplicateCheckAllAssignments - *******************************************************");
		//Use main consignment list to check if assignment is already in Consignment list of Cover Dockets
		if(!masterConsignmentList.isEmpty()) {
			for(Consignment consignDupTest : masterConsignmentList) {
				log.info("TrackingAction - duplicateCheckAllAssignments - Consignment Number : " +consignDupTest.getConsignmentNumber());
				log.info("TrackingAction - duplicateCheckAllAssignments - Consignment Date : " +consignDupTest.getDate());			
				log.info("TrackingAction - duplicateCheckAllAssignments - *******************************************************");
				
				if(consignDupTest.getDockets() != null ) {
					for(Docket docket : consignDupTest.getDockets()) {
						log.info("TrackingAction - duplicateCheckAllAssignments - Found Docket Id : " +docket.getDocketID());
						log.info("TrackingAction - duplicateCheckAllAssignments - Found Docket Number : " +docket.getDocketNumber());
						log.info("TrackingAction - duplicateCheckAllAssignments - *******************************************************");
						if(docket.getAssignments() != null ) {
							for (Assignment assignment : docket.getAssignments()){
								log.info("TrackingAction - duplicateCheckAllAssignments - Found Docket StudentNumber : " +assignment.getStudentNumber().trim());
								log.info("TrackingAction - duplicateCheckAllAssignments - Found Docket UniqueAssignmentNumber : " +assignment.getUniqueAssignmentNumber().trim());
								if (studentNumber.equalsIgnoreCase(assignment.getStudentNumber().trim()) && assignmentNumber.equalsIgnoreCase(assignment.getUniqueAssignmentNumber().trim())){
									log.info("TrackingAction - duplicateCheckAllAssignments - Assignment Already Exists in Consignment List Cover Docket: "+consignDupTest.getConsignmentNumber()+", Docket: "+docket.getDocketNumber());
									check = true;
								}else{
									log.info("TrackingAction - duplicateCheckAllAssignments - Assignment unique, therefore NOT in Consignment List Cover Docket: "+consignDupTest.getConsignmentNumber()+", Docket: "+docket.getDocketNumber());
								}
							}
						}else{
							log.info("TrackingAction - duplicateCheckAllAssignments - No Cover Dockets linked Assignments in Master Consignment List: " +consignDupTest.getConsignmentNumber());
						}
					}
				}else{
					log.info("TrackingAction - duplicateCheckAllAssignments - No Cover Dockets in Master Consignment List: " +consignDupTest.getConsignmentNumber());
					log.info("TrackingAction - duplicateCheckAllAssignments - *******************************************************");
				}
				if(consignDupTest.getAssignments() != null ) {
					for (Assignment assignment : consignDupTest.getAssignments()){
						String []  splitUnqAss  = assignment.getUniqueAssignmentNumber().trim().split("~");
		        		if(splitUnqAss[0] != null && !splitUnqAss[0].isEmpty()){
		        			log.info("TrackingAction - duplicateCheckAllAssignments - Found Consignment Student Number : " +splitUnqAss[0].trim());
		        			log.info("TrackingAction - duplicateCheckAllAssignments - Found Consignment Unique Assignment : " +splitUnqAss[1].trim());
		        			log.info("TrackingAction - duplicateCheckAllAssignments - *******************************************************");
		        			if(studentNumber.equalsIgnoreCase(splitUnqAss[0].trim()) && assignmentNumber.equalsIgnoreCase(splitUnqAss[1].trim())) {
		        				log.info("TrackingAction - duplicateCheckAllAssignments - Assignment Already Exists in Consignment List : "+consignDupTest.getConsignmentNumber());
		       					check = true;
		       				}else{
		       					log.info("TrackingAction - duplicateCheckAllAssignments - Assignment unique, therefore NOT in Consignment List:" +consignDupTest.getConsignmentNumber());
		       				}
						}
					}
				}else{
					log.info("TrackingAction - duplicateCheckAllAssignments - No Unique Assignments in Master Consignment List:" +consignDupTest.getConsignmentNumber());
				}
			}
		}else{
			log.info("TrackingAction - duplicateCheckAllAssignments - No Consignments in Master List");
			log.info("TrackingAction - duplicateCheckAllAssignments - *******************************************************");
		}
		//Use main Cover Docket list to check if docket is already in list of Cover Dockets
		log.info("TrackingAction - duplicateCheckAllAssignments - Checking Unique Cover Docket Master List");
		log.info("TrackingAction - duplicateCheckAllAssignments - *******************************************************");
		if(!masterDocketList.isEmpty()) {
			for(Docket docket : masterDocketList) {
				log.info("TrackingAction - duplicateCheckAllAssignments - Found Docket Id : " +docket.getDocketID());
				log.info("TrackingAction - duplicateCheckAllAssignments - Found Docket Number : " +docket.getDocketNumber());
				log.info("TrackingAction - duplicateCheckAllAssignments - *******************************************************");
				if(docket.getAssignments() != null ) {
					for (Assignment assignment : docket.getAssignments()){
						log.info("TrackingAction - duplicateCheckAllAssignments - Found StudentNumber : " +assignment.getStudentNumber().trim());
						log.info("TrackingAction - duplicateCheckAllAssignments - Found UniqueAssignmentNumber : " +assignment.getUniqueAssignmentNumber().trim());
						if (studentNumber.equalsIgnoreCase(assignment.getStudentNumber().trim()) && assignmentNumber.equalsIgnoreCase(assignment.getUniqueAssignmentNumber().trim())){
							log.info("TrackingAction - duplicateCheckAllAssignments - Assignment Already Exists in Cover Docket List : "+docket.getDocketNumber());
							check = true;
						}else{
							log.info("TrackingAction - duplicateCheckAllAssignments - Assignment unique, therefore NOT in Cover Docket "+docket.getDocketNumber()+" List");
						}
					}
				}else{
					log.info("TrackingAction - duplicateCheckAllAssignments - No Cover Dockets linked Assignments in Master Docket List");
				}

			}
		}else{
			log.info("TrackingAction - duplicateCheckAllAssignments - No Cover Dockets in Master List");
			log.info("TrackingAction - duplicateCheckAllAssignments - *******************************************************");
		}
		
	   //Final check of unique Assignment list for duplicate testing
	   log.info("TrackingAction - duplicateCheckAllAssignments - *******************************************************");
	   log.info("TrackingAction - duplicateCheckAllAssignments - Checking Unique Assignment Master List");
	   log.info("TrackingAction - duplicateCheckAllAssignments - *******************************************************");
		if(!mapUnqAssignments.isEmpty()) {
            String AssignmentID = "";
            String AssignmentString = "";
			Set<String> keys = mapUnqAssignments.keySet();
	        for(String key:keys){

	            AssignmentID = key.trim();
	            AssignmentString = mapUnqAssignments.get(key).trim();
				log.info("TrackingAction - duplicateCheckAllAssignments - Found Assignment ID : " +AssignmentID);
				log.info("TrackingAction - duplicateCheckAllAssignments - Found Assignment ; String : " +AssignmentString);
				log.info("TrackingAction - duplicateCheckAllAssignments - *******************************************************");
				log.info("TrackingAction - duplicateCheckAllAssignments - Splitting ; String");
	      		String []  splitUnqAss  = AssignmentString.trim().split("~");
        		if(splitUnqAss[0] != null && !splitUnqAss[0].isEmpty()){
        			log.info("TrackingAction - duplicateCheckAllAssignments - Found Student Number : " +splitUnqAss[0].trim());
        			log.info("TrackingAction - duplicateCheckAllAssignments - Found Unique Assignment : " +splitUnqAss[1].trim());
        			log.info("TrackingAction - duplicateCheckAllAssignments - *******************************************************");
        			if(studentNumber.equalsIgnoreCase(splitUnqAss[0].trim()) && assignmentNumber.equalsIgnoreCase(splitUnqAss[1].trim())) {
       					log.info("TrackingAction - duplicateCheckAllAssignments - Assignment Already Exists");
       					check = true;
       				}else{
       					log.info("TrackingAction - duplicateCheckAllAssignments - Assignment unique, therefore NOT in List");
       				}
				}
			}
			log.info("TrackingAction - duplicateCheckAllAssignments - *******************************************************");
		}else{
			log.info("TrackingAction - duplicateCheckAllAssignments - No Unique Assignments in Master List");
			log.info("TrackingAction - duplicateCheckAllAssignments - *******************************************************");
		}
		return check;
	}

	public String sendNotificationToUser(TrackingForm form) throws Exception {

		String result = "";
		try {
			log.info("TrackingAction - sendNotificationToUser - **************************************************************");

			int webserviceEmailId = 355144; //GET_DEFAULT_EMAIL_ADDRESS 355144 A 
			int webserviceId = 18428; //LIST_USERS 18428 A 
			ArrayList<KeyValue> getValues = null;
			String userEmail = "";
			String toUser = "";
			StringBuilder userValue = new StringBuilder();
			StringBuilder fromUserValue = new StringBuilder();

			String serverPath = ServerConfigurationService.getToolUrl();
			//Do not send email on localhost,dev or qa	
			
			if (serverPath.contains("mydev") || serverPath.contains("myqa") || serverPath.contains("localhost")){
				//Get Default Email Address (ADRPH - 1, TRACKING DEFAULT, 40)
				log.info("TrackingAction - sendNotificationToUser - Get Default Email Address from ADRPH");
				String tmpMail = pGateWay.getDefaultEmail(webServiceURL ,webserviceEmailId, "value1");
				if (tmpMail != null && !tmpMail.equals("")){
					userEmail = tmpMail;
					userValue = (new StringBuilder()).append(userValue).append("Test User ("+form.getNovelUserId().toString()+")");
					log.info("TrackingAction - sendNotificationToUser - Default Email Address="+userEmail);
					log.info("TrackingAction - sendNotificationToUser - Default Email Recipient="+userValue.toString());
					log.info("TrackingAction - sendNotificationToUser - **************************************************************");
				}
			}else{
				//Get Person's email address
				if (form.getSaveAddress1() != null && !form.getSaveAddress1().equals("")){
					if (form.getSaveAddress1().toUpperCase().contains("PERSON")){
	        			//log.info("PERSON="+pTrackingForm.getSaveAddress1());
	        			String [] splitPerson  = form.getSaveAddress1().trim().split("|");
		        		if(splitPerson[0] != null && !splitPerson[0].isEmpty()){
		        			toUser = splitPerson[1];
		        			//log.info("PERSON Split="+toUser);
		        		}
					}
				}
				
				if (toUser != null && !toUser.equals("")){
					log.info("TrackingAction - sendNotificationToUser - **************************************************************");
					log.info("TrackingAction - sendNotificationToUser - Get Destination User Details");
					log.info("TrackingAction - sendNotificationToUser - getUserList1 - UserID="+toUser);

					getValues = pGateWay.getUserList1(webServiceURL ,webserviceId, "value1", "value2", "value3", "value4", "value5", "value6", toUser);
						if (!getValues.isEmpty()){
				    		Iterator<KeyValue> it = getValues.iterator();
				    		log.info("TrackingAction - sendNotificationToUser - Iterator");
				    		if (it.hasNext()){
					    		while(it.hasNext()){
					    			log.info("TrackingAction - sendNotificationToUser - Result Iterator Has Next");
									KeyValue test = (KeyValue) it.next();
									log.info("TrackingAction - sendNotificationToUser - Result ToUser StaffNumber="+test.getValue1());
									//userKey = test.getValue().trim().toString();
				
									//Build String in order (Title, Initials, Surname);
									if(test.getValue4() != null &&  !test.getValue4().isEmpty()){
										if(!test.getValue4().equals("null")){
											log.info("TrackingAction - sendNotificationToUser - Result ToUser Title="+test.getValue4());
											userValue = (new StringBuilder()).append(userValue).append(test.getValue4().trim().toString()).append(".");
										}
									}
									if(test.getValue3() != null &&  !test.getValue3().isEmpty()){
										if(!test.getValue3().equals("null")){
											log.info("TrackingAction - sendNotificationToUser - Result ToUser Initials="+test.getValue3());
											userValue = (new StringBuilder()).append(userValue).append(" ").append(test.getValue3().trim().toString());
										}
									}
									if(test.getValue2() != null &&  !test.getValue2().isEmpty()){
										if(!test.getValue2().equals("null")){
											log.info("TrackingAction - sendNotificationToUser - Result ToUser Surname="+test.getValue2());
											userValue = (new StringBuilder()).append(userValue).append(" ").append(test.getValue2().trim().toString());
										}
									}
									if(test.getValue6() != null &&  !test.getValue6().isEmpty()){
										if(!test.getValue6().equals("null")){
											log.info("TrackingAction - sendNotificationToUser - Result ToUser Email="+test.getValue6());
											userEmail = test.getValue2().trim().toString();
										}
									}
					    		}
				    		}
						}
				}else if (form.getAddressType().equalsIgnoreCase("MANUAL")){
					log.info("TrackingAction - sendNotificationToUser - **************************************************************");
					log.info("TrackingAction - sendNotificationToUser - Get Manually Entered Destination User Details");

	    			if (form.getEmailAddress()!=null && !form.getEmailAddress().equals("")){
	    				userEmail = form.getDisplayEmail();
	    				userValue = (new StringBuilder()).append(userValue).append(form.getDisplayAddress1());
	    			}
	    		}
			}
			
			log.info("TrackingAction - sendNotificationToUser - **************************************************************");
			log.info("TrackingAction - sendNotificationToUser - userValue="+userValue.toString());
			log.info("TrackingAction - sendNotificationToUser - userEmail="+userEmail);
			log.info("TrackingAction - sendNotificationToUser - **************************************************************");

			
			if (userValue == null || userValue.equals("")){
				log.info("TrackingAction - sendNotificationToUser - **************************************************************");
				log.info("TrackingAction - sendNotificationToUser - No Destination User. Using NovelUserID");
				userValue = (new StringBuilder()).append(userValue).append(" ").append("(").append(form.getNovelUserId()).append(")");
				log.info("TrackingAction - sendNotificationToUser - Destination User="+userValue.toString());
				log.info("TrackingAction - sendNotificationToUser - **************************************************************");
			}
			
			//Get Sender's Information as we don't want to use the Novel ID
			log.info("TrackingAction - sendNotificationToUser - **************************************************************");
			log.info("TrackingAction - sendNotificationToUser - Get Sending User Details");
			log.info("TrackingAction - sendNotificationToUser - getUserList1 - UserID="+form.getPersNo());

			if (serverPath.contains("mydev") || serverPath.contains("myqa") || serverPath.contains("localhost")){
				//Set Default Email User
				fromUserValue = (new StringBuilder()).append(fromUserValue).append("Test User ("+form.getNovelUserId().toString()+")");				
				log.info("TrackingAction - sendNotificationToUser - Default Email Sender="+userValue.toString());
				log.info("TrackingAction - sendNotificationToUser - **************************************************************");
			}else{
				getValues = pGateWay.getUserList1(webServiceURL ,webserviceId, "value1", "value2", "value3", "value4", "value5", "value6", form.getPersNo());
				if (!getValues.isEmpty()){
		    		Iterator<KeyValue> it = getValues.iterator();
		    		log.info("TrackingAction - sendNotificationToUser - Iterator");
		    		if (it.hasNext()){
			    		while(it.hasNext()){
			    			log.info("TrackingAction - sendNotificationToUser - Result Iterator Has Next");
			    			String userKey = "";
							KeyValue test = (KeyValue) it.next();
							log.info("TrackingAction - sendNotificationToUser - Result FromUser StaffNumber="+test.getValue1());
							//userKey = test.getValue().trim().toString();
		
							//Build String in order (Title, Initials, Surname);
							if(test.getValue4() != null &&  !test.getValue4().isEmpty()){
								if(!test.getValue4().equals("null")){
									log.info("TrackingAction - sendNotificationToUser - Result FromUser Title="+test.getValue4());
									fromUserValue = (new StringBuilder()).append(fromUserValue).append(test.getValue4().trim().toString()).append(".");
								}
							}
							if(test.getValue3() != null &&  !test.getValue3().isEmpty()){
								if(!test.getValue3().equals("null")){
									log.info("TrackingAction - sendNotificationToUser - Result FromUser Initials="+test.getValue3());
									fromUserValue = (new StringBuilder()).append(fromUserValue).append(" ").append(test.getValue3().trim().toString());
								}
							}
							if(test.getValue2() != null &&  !test.getValue2().isEmpty()){
								if(!test.getValue2().equals("null")){
									log.info("TrackingAction - sendNotificationToUser - Result FromUser Surname="+test.getValue2());
									fromUserValue = (new StringBuilder()).append(fromUserValue).append(" ").append(test.getValue2().trim().toString());
								}
							}
							
							//Finally Add Sending User's Personnel number
							fromUserValue = (new StringBuilder()).append(fromUserValue).append(" ").append("Personel Number (").append(test.getValue1().trim().toString()).append(")");
	
			    		}
		    		}
				}
			}
			log.info("TrackingAction - sendNotificationToUser - **************************************************************");
			log.info("TrackingAction - sendNotificationToUser - fromUserValue="+fromUserValue.toString());
			log.info("TrackingAction - sendNotificationToUser - **************************************************************");

			//Only send email if email address is not empty
			if (userEmail != null && !userEmail.equals("")){
				
				String tmpEmailFrom = ServerConfigurationService.getString("noReplyEmailFrom");
				InternetAddress toEmail = new InternetAddress(userEmail);
				InternetAddress iaTo[] = new InternetAddress[1];
				iaTo[0] = toEmail;
				InternetAddress iaHeaderTo[] = new InternetAddress[1];
				iaHeaderTo[0] = toEmail;
				InternetAddress iaReplyTo[] = new InternetAddress[1];
				iaReplyTo[0] = new InternetAddress(tmpEmailFrom);
				emailService = (EmailService) ComponentManager.get(EmailService.class);
				List<String> contentList = new ArrayList<String>();
				contentList.add("Content-Type: text/html"); 
				
				/* Email to student informing him/her of re-assignment*/
				String subject = "Unisa Assignment Hardcopy Tracking ("+form.getDisplayShipListNumber()+")";
				String body = "<html> "+
			    "<body>Dear "+userValue.toString()+"," + "<br/><br/>"+
			    "The Consignment List (<b>"+form.getDisplayShipListNumber()+"</b>) was booked OUT to you on "+form.getDisplayShipListDate()+" for your attention.<br><br>" +
			    "Booked Out by: "+fromUserValue.toString()+".<br/><br/>" +			
				"Please visit myUnisa (https://my.unisa.ac.za) to access the consignment list via Course Admin or function (F856) on the Student System.<br/><br/> " +
			    "Should you have any further queries please contact:<br>" +
				"  The Directorate: Student Assessment Administration (DSAA)<br>" +
			    "  Email: <STRONG><A href='mailto:study-info@unisa.ac.za'>study-info@unisa.ac.za</A></STRONG><br><br>" +
				"Regards<br>"+
				"Student Assessment Administration Team" +
				"</body>"+
				"</html>";

				log.info("TrackingAction - sendNotificationToUser - **************************************************************");
				log.info("TrackingAction - sendNotificationToUser - Sending mail from="+iaReplyTo[0]);
				log.info("TrackingAction - sendNotificationToUser - Sending mail To="+toEmail.toString());
				log.info("TrackingAction - sendNotificationToUser - Sending mail tmpEmailFrom="+tmpEmailFrom);
				log.info("TrackingAction - sendNotificationToUser - Sending mail Subject="+subject);
				log.info("TrackingAction - sendNotificationToUser - Sending mail Body="+body);
				log.info("TrackingAction - sendNotificationToUser - Sending mail ContentList="+contentList);
				log.info("TrackingAction - sendNotificationToUser - **************************************************************");

				log.info("TrackingAction - sendNotificationToUser - Before Sending Email");
				try{
					emailService.sendMail(iaReplyTo[0],iaTo,subject,body,iaHeaderTo,iaReplyTo,contentList);
				}catch (Exception e){
					// Don't fail if email could not be send
					log.info("TrackingAction - sendNotificationToUser - Email failed: "+e+" "+e.getMessage());
	        	}
				log.info("TrackingAction - sendNotificationToUser - After Sending Email");
				log.info("TrackingAction - sendNotificationToUser - **************************************************************");

				//Future Log Email sent
				//EmailLogRecord log = new EmailLogRecord();
				//	log.setRecipient(userValue);
				//	log.setRecipientType("TUTOR");
				//	log.setEmailAddress(toAddress);
				//	log.setProgram("TRACKING:MYUNISA");
				//	log.setSubject(subject);
				//	log.setEmailType("BOOKOUT");
				//	log.setBody(body);
				//insertEmailLog(log);
			}else{
				result = "No email sent as Destination email address could not be found or destination is a Province, College or Building etc";
			}
		} catch (Exception e) {
			result = "Email Failed; "+e+" "+e.getMessage();
		}	
		log.info("TrackingAction - sendNotificationToUser - **************************************************************");
		return result;
	}

	public void resetForm(TrackingForm form, String method) throws Exception {
		
		log.info("TrackingAction - resetForm - *******************************************************");
		log.info("TrackingAction - resetForm - Calling Method="+method);
		log.info("TrackingAction - resetForm - *******************************************************");
		log.info("TrackingAction - resetForm - Local Lists");
		// Clear fields
		masterConsignmentList.clear();
		masterDocketList.clear();
		mapUnqAssignments.clear();
		displayDockets.clear();
		displayDctAssignments.clear();
		displayUniqueNumbers.clear();
		processDocketList.clear();
		processDocketAssignmentList.clear();
		processAssignmentList.clear();
	
		log.info("TrackingAction - resetForm - Form Variables");
		
		form.setDisplayDocketsForConsignment(null);
		form.setDisplayDctAssignmentsForConsignment(null);
		form.setDisplayUniqueNumbersForConsignment(null);
		form.setDisplayShipListNumber("");
		form.setDisplayShipListDate("");
		form.setDisplayAddress1("");
		form.setDisplayAddress2("");
		form.setDisplayAddress3("");
		form.setDisplayAddress4("");
		form.setDisplayPostal("");
		
		form.setConsignmentListNumber("");
		form.setAddressType("");
		form.setSearchString("");
		form.setSearchRadio("");
		
		form.setSaveAddress1("");
		form.setSaveAddress2("");
		form.setSaveAddress3("");
		form.setSaveAddress4("");
		form.setSaveAddress5("");
		form.setSavePostal("");
		log.info("TrackingAction - resetForm - *******************************************************");
	}
	
	public void resetLocal(TrackingForm form, String method) throws Exception {
		
		log.info("TrackingAction - resetLocal - *******************************************************");
		log.info("TrackingAction - resetLocal - Calling Method="+method);
		log.info("TrackingAction - resetLocal - *******************************************************");
		log.info("TrackingAction - resetLocal - Local Lists");
		// Clear fields
		masterConsignmentList.clear();
		masterDocketList.clear();
		//mapUnqAssignments.clear();
		displayDockets.clear();
		displayDctAssignments.clear();
		displayUniqueNumbers.clear();
		processDocketList.clear();
		//processDocketAssignmentList.clear();
		processAssignmentList.clear();
		log.info("TrackingAction - resetLocal - *******************************************************");
	}

	public Boolean isNumberValid(String numVal) {
	    regexPattern = Pattern.compile("^[0-9]*$");
	    regMatcher   = regexPattern.matcher(numVal);
	    //if (regMatcher.find()) {
	    if(regMatcher.matches()){
	        return true;
	    } else {
	    	return false;
	    }
	}
	
	public String validateEmail(TrackingForm form){
     	String emailAdrress=form.getUserEmail();
       if( emailAdrress== null || "".equals(emailAdrress.trim())){
             return  "Recipient E-mail address may not be empty when entering a manual address.";
       }
       EmailValidator  emValidator=new EmailValidator();
       boolean valid = emValidator.validate(emailAdrress);
       if(!valid){			
           return "E-mail address is invalid. Please enter a valid E-mail address.";
       }else{
           form.setUserEmail(emailAdrress);
       }
       return "";
}
 	
	  public void getAllRequestParamaters(HttpServletRequest req, HttpServletResponse res){ 
		    Enumeration<String> parameterNames = req.getParameterNames(); 
		    while (parameterNames.hasMoreElements()) { 
		        String paramName = parameterNames.nextElement(); 
		        System.out.println("param: " + paramName); 

		        String[] paramValues = req.getParameterValues(paramName); 
		        for (int i = 0; i < paramValues.length; i++) { 
		            String paramValue = paramValues[i]; 
		            System.out.println("value: " + paramValue); 
		        } 
		    } 
		}
}
