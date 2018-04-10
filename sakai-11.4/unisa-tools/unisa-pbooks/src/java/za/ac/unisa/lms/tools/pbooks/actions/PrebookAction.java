package za.ac.unisa.lms.tools.pbooks.actions;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.sakaiproject.component.cover.ComponentManager;
import org.sakaiproject.authz.api.SecurityService;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.LookupDispatchAction;
import org.apache.struts.util.LabelValueBean;
import org.sakaiproject.component.cover.ServerConfigurationService;
import org.sakaiproject.email.api.EmailService;
import org.sakaiproject.tool.api.Session;
import org.sakaiproject.tool.api.SessionManager;
import org.sakaiproject.tool.api.ToolManager;
import org.sakaiproject.user.api.User;
import org.sakaiproject.user.api.UserNotDefinedException;
import org.sakaiproject.user.api.UserDirectoryService;

import za.ac.unisa.exceptions.NotACourseSiteException;
import za.ac.unisa.lms.tools.pbooks.dao.AuditTrail;
import za.ac.unisa.lms.tools.pbooks.dao.BookDetails;
import za.ac.unisa.lms.tools.pbooks.dao.CollegeDeptDetails;
import za.ac.unisa.lms.tools.pbooks.dao.CourseDAO;
import za.ac.unisa.lms.tools.pbooks.forms.BookMenuForm;
import za.ac.unisa.utils.CoursePeriod;
import za.ac.unisa.utils.CoursePeriodLookup;
public class PrebookAction extends LookupDispatchAction {
	private SessionManager sessionManager;
	private UserDirectoryService userDirectoryService;
	private EmailService emailService;
		protected Map getKeyMethodMap() {
			Map map = new HashMap();
			map.put("prescribedBookAction","prescribedBookAction");
			map.put("recommendedBookAction","recommendedBookAction");
		    map.put("eRerveBookAction","eRerveBookAction");
			map.put("mainWinSecPage","mainWinSecPage");
			map.put("booklistDisplay","booklistDisplay");
			map.put("showPrebooks", "showPrebooks");
			map.put("viewBookMenu", "viewBookMenu");
			map.put("firstScreen", "firstScreen");
			map.put("button.continue", "prebooksContinue");
			map.put("prebooksContinue","prebooksContinue");
			map.put("processAcadyear","processAcadyear");
			map.put("button.newsearch", "searchBook");
			map.put("searchBook", "searchBook");
			map.put("courseNote", "courseNote");
			map.put("bookReuse", "bookReuse");
			map.put("declareNopBooks", "declareNopBooks");
			map.put("button.noBooks", "noBooks");
			map.put("button.noItems", "noBooks");		
			map.put("findBooks", "findBooks");
			map.put("button.search", "findBooks");
			map.put("button.submit", "submit");
			map.put("button.submitrequestauth", "submit");
			map.put("button.remove", "removeBook");
			map.put("button.delete", "deleteBook");
			map.put("button.copy", "copyBooks");
			map.put("button.addnewbook", "addNewBook");
			map.put("addNewBook","addNewBook");
			map.put("button.save", "updateBookDetails");
			map.put("button.cancel", "viewBookMenu");
			map.put("button.cancel", "cancel");
			map.put("button.back", "cancel");
			//map.put("button.confirm", "confirmBookList");
			map.put("button.confirm", "confirmBookAcademic");
			map.put("button.unconfirm", "unConfirmBookList");
			map.put("button.remindacademic", "remindAcademic");
			map.put("hodApproval", "hodapproval");
			map.put("button.approve", "approve");
			map.put("button.requestedit", "hodRequestEdit");
			//Sifiso Changes: 2017/08/08:LU_MA_BLU_06:Requirement 10:Tutor Booklist: Forward to Tutor Edit page
			map.put("tutorBookAction", "tutorBookAction");
			map.put("button.tutorCount", "submitTutorCount");
			
			return map;
		}
		 private int  yearInt(){
	         SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy");
	         String yearStr=dateFormat.format(new Date());
	         int year=Integer.parseInt(yearStr);
	         return  year;
	 }

	public ActionForward viewBookMenu(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response){
			//SecurityServices ss = new SecurityServices();	
			CourseDAO dao = new CourseDAO();
			BookMenuForm bookMenuForm = (BookMenuForm) form;
			sessionManager = (SessionManager)ComponentManager.get(SessionManager.class);
			Session currentSession = sessionManager.getCurrentSession();
	   		String userID = currentSession.getUserEid();
			bookMenuForm.setNetworkId(userID);
			// 13 May 2013 (SY) add if (bookMenuForm.getAcadyear()==null) otherwise valid year got overwritten with "Select academic year"
			if (bookMenuForm.getAcadyear()==null) {
				bookMenuForm.setAcadyear("Select Academic Year");
			}
			//Sifiso Changes: 2017/08/08:LU_MA_BLU_06:Requirement 10:Tutor Booklist: Submit button action
			//Get Diss Authoriser emails from sakai.properties
			String dissEmails = ServerConfigurationService.getString("booklist.dissAuthEmail");
			//check if user userId (network ID) of logged in user contains any diss email
			if( dissEmails.toLowerCase().contains( userID.toLowerCase() ) )
				bookMenuForm.setIsDiss("true");
			
			ArrayList<LabelValueBean> crs = new ArrayList<LabelValueBean>();
            List courseList = new ArrayList();
    		HttpSession session = request.getSession(true);
            session.setAttribute("yearsList",yearsList());
    		resetLastUpdateDetail(bookMenuForm);
    		resetLastUpdateDetail(bookMenuForm);
    		
        try {    if(!bookMenuForm.getAcadyear().equals("Select Academic Year")){
                  courseList = dao.getCourseList(bookMenuForm.getNetworkId(),bookMenuForm.getAcadyear());
                  String[] temp = null;
                  String [] temp1 = null;
                  for(int i=0; i < courseList.size(); i++){
                        temp = courseList.get(i).toString().split("=");
                        temp1 = temp[1].split("}");
                        crs.add(new LabelValueBean(temp1[0],temp1[0]));
                  }
                 }else{
                	 crs.add(new LabelValueBean("",""));
                 }
        if(!bookMenuForm.getAcadyear().equals("Select Academic Year")){
                  setListsAndStatuses(mapping,form,request,response);
        }
            } catch (Exception e) {
                  e.printStackTrace();
            } 
            if(bookMenuForm.getMainViewTracker().equals("0")){
            	request.setAttribute("crs",new ArrayList());
		      return mapping.findForward("bookmenu");
            }else{
            	request.setAttribute("crs", crs);
            	return mapping.findForward("booklistDisplay");
            }
	}
	public ActionForward prescribedBookAction(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response)throws Exception{
		    BookMenuForm bookMenuForm = (BookMenuForm) form;
		    bookMenuForm.setListStatus(bookMenuForm.getPlistStatus());
		    bookMenuForm.setTypeOfBookList("P");
		    bookMenuForm.setCourseId(bookMenuForm.getSelectedCourse());
		    return doTheConfirming(mapping,form,request,response);
	}
	public ActionForward recommendedBookAction(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response)throws Exception{
		    BookMenuForm bookMenuForm = (BookMenuForm) form;
		    bookMenuForm.setListStatus(bookMenuForm.getRlistStatus());
		    bookMenuForm.setCourseId(bookMenuForm.getSelectedCourse());
		    bookMenuForm.setTypeOfBookList("R");
		    return 	doTheConfirming(mapping,form,request,response);
	}
	public ActionForward eRerveBookAction(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response)throws Exception{
		    BookMenuForm bookMenuForm = (BookMenuForm) form;
		    bookMenuForm.setTypeOfBookList("E");
		    bookMenuForm.setCourseId(bookMenuForm.getSelectedCourse());
		    bookMenuForm.setListStatus(bookMenuForm.getElistStatus());
		     return doTheConfirming(mapping,form,request,response);
	}
	//Sifiso Changes: 2017/08/08:LU_MA_BLU_06:Requirement 10:Tutor Booklist: Forward to Tutor Edit page
	public ActionForward tutorBookAction(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response)throws Exception{

		    BookMenuForm bookMenuForm = (BookMenuForm) form;
		    CourseDAO dao = new CourseDAO();
		    
		    if( !bookMenuForm.getCountUpdateTracker().equals("0") )
		    	bookMenuForm.setCountUpdateTracker("0");			//Reset tutor page tracker from any page
		    
		    bookMenuForm.setTypeOfBookList( "P" );
		    bookMenuForm.setCourseId( bookMenuForm.getSelectedCourse() );
		    bookMenuForm.setListStatus( ""+bookMenuForm.getTutorBookListStatus() ); 		//TutorBookListStatus is int
		    bookMenuForm.setTutorBookListStatus( bookMenuForm.getTutorBookListStatus() ); 	//from TUTOR_STATUS DB Column

		    try {
				bookMenuForm.setBookList( dao.getBookList( bookMenuForm.getCourseId(),bookMenuForm.getAcadyear(),bookMenuForm.getTypeOfBookList() ) );
			} catch (Exception ex){
				ex.printStackTrace();
			}
		    
			return mapping.findForward("editTutorCount");
	}
	public ActionForward submit(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		
			BookMenuForm bookMenuForm = (BookMenuForm) form;
			CourseDAO dao = new CourseDAO();
			String hodComments = bookMenuForm.getHodComments();
			ActionMessages messages = new ActionMessages();
			
			if (bookMenuForm.getSubmitOption().toUpperCase().equals("COURSENOTE")){
				return saveCourseNotes(mapping, form, request, response);
				
			}
			else if (bookMenuForm.getSubmitOption().toUpperCase().equals("NEWBOOK")){
				return saveBookDetails(mapping, form, request, response);
			}
			else if (bookMenuForm.getSubmitOption().toUpperCase().equals("CONFIRMBYACADEMIC")){
				
				if(bookMenuForm.getSelectedPerson()==null || bookMenuForm.getSelectedPerson().length()==0){
								messages.add(ActionMessages.GLOBAL_MESSAGE,
						        new ActionMessage("message.generalmessage", "Please choose COD or Stand-in COD to send authorization request."));
							addErrors(request, messages);
						return mapping.findForward("confirmbyacademicform");
				}	
				dao.updateBookListStatus("1", bookMenuForm.getCourseId(), bookMenuForm.getAcadyear(),bookMenuForm.getNetworkId(),bookMenuForm.getTypeOfBookList());
				
				if(bookMenuForm.getSelectedPerson().equals("COD")){
					bookMenuForm.setSelectedPerson(bookMenuForm.getCodNovelluserId());
				}
				updateAuditTrail(bookMenuForm, "Book list confirmed");
				
				String email=bookMenuForm.getSelectedPerson();
				email += "@unisa.ac.za";
				
				//String email="udcsweb@unisa.ac.za";
				
				User hodDetails = null;
				try {
					userDirectoryService = (UserDirectoryService) ComponentManager.get(UserDirectoryService.class);
					hodDetails = userDirectoryService.getUserByEid(bookMenuForm.getNetworkId());
				} catch (UserNotDefinedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	
				String submittedBy="";
				try{
					hodDetails = userDirectoryService.getUserByEid(bookMenuForm.getNetworkId());
					 submittedBy = hodDetails.getDisplayName();
					} catch (UserNotDefinedException exception){
						submittedBy="User Unknown";
					}		
					String emailSubject;
				String emailMessage;
				if(bookMenuForm.getTypeOfBookList().equals("P")){
			
				 emailSubject = "Request Authorization of Prescribed Booklist for " +bookMenuForm.getCourseId();
				 emailMessage = "NB: This is an automated message - do not reply to this e-mail. <br>"+
						"<p>"+
						""+submittedBy+" request authorization of the " +bookMenuForm.getAcadyear()+ " Prescribed book list for " +(bookMenuForm.getCourseId())+".<br>" +
						"<p>"+
						"To view this book list, go to the myUnisa site at https://my.unisa.ac.za/portal, login and select the Course Admin site.  The Book List Management tool is accessible from the left " +
						"navigation. Click on this tool and select the <b>COD Authorization</b> link.<br>" +
						"<p>"+
						"Regards <br>" +
						"myUnisa Support Team";
				}else if(bookMenuForm.getTypeOfBookList().equals("R")){
					 emailSubject = "Request Authorization of Recommended Booklist for " +bookMenuForm.getCourseId();
					 emailMessage = "NB: This is an automated message - do not reply to this e-mail. <br>"+
							"<p>"+
							""+submittedBy+" request authorization of the " +bookMenuForm.getAcadyear()+ " Recommended book list for " +(bookMenuForm.getCourseId())+".<br>" +
							"<p>"+
							"To view this book list, go to the myUnisa site at https://my.unisa.ac.za/portal, login and select the Course Admin site.  The Book List Management tool is accessible" +
							" from the left navigation.  Click on this tool and select the <b>COD Authorization</b> link.<br>" +
							"<p>"+
							"Regards <br>" +
							"myUnisa Support Team";
				}else{
					 emailSubject = "Request Authorization of EReserves Booklist for " +bookMenuForm.getCourseId();
					 emailMessage = "NB: This is an automated message - do not reply to this e-mail. <br>"+
							"<p>"+
							""+submittedBy+" request authorization of the " +bookMenuForm.getAcadyear()+ " eReserves list for " +(bookMenuForm.getCourseId())+".<br>" +
							"<p>"+
							"To view this book list, go to the myUnisa site at https://my.unisa.ac.za/portal, login and select the Course Admin site.  The Book List Management tool is accessible " +
							"from the left navigation.  Click on this tool and select the <b>COD Authorization</b> link.<br>" +
							"<p>"+
							"Regards <br>" +
							"myUnisa Support Team";
				}
				
				sendEmail(emailSubject, emailMessage, email);	
				

				return confirmBooks(mapping, form, request, response);
				//return confirmBookList(mapping, form, request, response);
			}
			else if (bookMenuForm.getSubmitOption().toUpperCase().equals("UPDATEBOOK")){
				return updateBookDetails(mapping, form, request, response);
			}
			else{
				return confirmBooks(mapping, form, request, response);
			}
	}
	private boolean isStringInArr(String str,String[] cmpArr){
		             for(int x=0;x<cmpArr.length;x++){
		            	 if(str.equals(cmpArr[x])){
		            		  return true;
		            	 }
		             }
		             return false;
	}
	public ActionForward cancel(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response)throws Exception{
		
		BookMenuForm bookMenuForm = (BookMenuForm) form;
		bookMenuForm.setTxtTitle("");
		bookMenuForm.setTxtAuthor("");
		bookMenuForm.setTxtEdition("");
		bookMenuForm.setTxtISBN("");
		bookMenuForm.setTxtPublisher("");
		bookMenuForm.setTxtYear("");
		bookMenuForm.setMainViewTracker("1");
		form = bookMenuForm;
				
		//Sifiso Changes: 2017/08/08:LU_MA_BLU_06:Requirement 10:Tutor Booklist: Track if DISS auth step reached
		//If page reached then re-populate dissAuthNamesList
		if ( bookMenuForm.getCountUpdateTracker().equals("2") ){
			//we going back to page before dissAuth
			bookMenuForm.setCountUpdateTracker("0"); 
	
			//Get Diss Authoriser details from sakai.properties and add to list
			String dissNames = ServerConfigurationService.getString("booklist.dissAuthName");
			String dissEmails = ServerConfigurationService.getString("booklist.dissAuthEmail");
			String [] tmpDissNames = dissNames.split(",");  	
			String [] tmpDissEmails = dissEmails.split(",");
			List tmpNamesList = new ArrayList();		
			List tmpEmailsList = new ArrayList();
			
			for ( int i=0; i<tmpDissNames.length; i++){			
				tmpNamesList.add( tmpDissNames[i].toString().trim() );
			}
			bookMenuForm.setDissAuthNames( tmpNamesList );	   
			
			for ( int i=0; i<tmpDissEmails.length; i++ ){
				tmpEmailsList.add( tmpDissEmails[i].toString().trim() );
			}
			bookMenuForm.setDissAuthEmails( tmpEmailsList );
		}
		
		if (bookMenuForm.getCancelOption() != null) {
			if (bookMenuForm.getCancelOption().toUpperCase().equals("TOMAINVIEW")){
				return viewBookMenu(mapping, form, request, response);	
			}
			else{
				return confirmBooks(mapping, form, request, response);		
			}
		}			
		else{
			return confirmBooks(mapping, form, request, response);		
		}
		
	}
	public ActionForward processAcadyear(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response){
			BookMenuForm bookMenuForm = (BookMenuForm) form;
		    resetLastUpdateDetail(bookMenuForm);
		    request.setAttribute("yearsList",yearsList());
		    bookMenuForm.setSelectedCourse("Select Course Code");
		    
			//Sifiso Changes: 2017/08/08:LU_MA_BLU_06:Requirement 10:Change year in drop-down
			String dissEmails = ServerConfigurationService.getString("booklist.dissAuthEmail");
			//check if user userId (network ID) of logged in user contains any diss email
			if( bookMenuForm.getNetworkId() != null ){
				if( dissEmails.toLowerCase().contains( bookMenuForm.getNetworkId().toLowerCase() ) )
					bookMenuForm.setIsDiss("true");
			}
			
		    if(bookMenuForm.getAcadyear().equals("Select Academic Year")){
			         request.setAttribute("crs",new ArrayList());
			         return mapping.findForward("bookmenu");	
		    }else{
		    	   request.setAttribute("crs",getCourseList(bookMenuForm));
		           return mapping.findForward("mainWinSecPage");
		    }
    }
	public ActionForward	prebooksContinue(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response){
			BookMenuForm bookMenuForm = (BookMenuForm) form;
		    CourseDAO dao = new CourseDAO();
		    resetLastUpdateDetail(bookMenuForm);
		    request.setAttribute("yearsList",yearsList());
		    request.setAttribute("crs", new ArrayList());
		    bookMenuForm.setMainViewTracker("0");
		    bookMenuForm.setListDate(todayString());
		    
			//Sifiso Changes: 2017/08/08:LU_MA_BLU_06:Requirement 10:Change year in drop-down
			String dissEmails = ServerConfigurationService.getString("booklist.dissAuthEmail");
			//check if user userId (network ID) of logged in user contains any diss email
			if( bookMenuForm.getNetworkId() != null ){
				if( dissEmails.toLowerCase().contains( bookMenuForm.getNetworkId().toLowerCase() ) )
					bookMenuForm.setIsDiss("true");
			}
		    
		    if (bookMenuForm.getContinueOption().toUpperCase().equals("SEARCHBOOK")) 
		    {
		    	return addNewBook(mapping, form, request, response);
		    }
		    else if(bookMenuForm.getContinueOption().toUpperCase().equals("MAINVIEWCONTINUE"))
		    {
			         try
			         {         
			        	           	   setListsAndStatuses(mapping,bookMenuForm,request,response);
						               request.setAttribute("crs",getCourseList(bookMenuForm));
						               //System.out.println("course:"+bookMenuForm.getSelectedCourse());
					                   return mapping.findForward("booklistDisplay");
					              			        	      
    		  	    }
			         catch (Exception e)
			         {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}			
			return mapping.findForward("bookmenu");	
		}
		return mapping.findForward("bookmenu");		
	}
	private List getCourseList(BookMenuForm bookMenuForm){
		  ArrayList<LabelValueBean> crs = new ArrayList<LabelValueBean>();
		  try {
		        List courseList = new ArrayList();
              CourseDAO dao = new CourseDAO();
              courseList = dao.getCourseList(bookMenuForm.getNetworkId(),bookMenuForm.getAcadyear());
              String[] temp = null;
              String [] temp1 = null;
              for(int i=0; i < courseList.size(); i++){
                  temp = courseList.get(i).toString().split("=");
                  temp1 = temp[1].split("}");
                  crs.add(new LabelValueBean(temp1[0],temp1[0]));
              }
       
        } catch (Exception e) {
                e.printStackTrace();
        } 
        return crs;
	}
	private List yearsList(){
	 int prevYear=yearInt()-1;
   String[] arr3=new String[4];
   for(int x=0;x<4;x++){
         arr3[x]=""+prevYear;
         prevYear++; 
   }
   ArrayList <LabelValueBean> arrList=new ArrayList <LabelValueBean>();
   for(int x=0;x< arr3.length;x++){
           arrList.add(new org.apache.struts.util.LabelValueBean(arr3[x],arr3[x]));
   }//for
   return arrList;
}
	private String todayString(){
		   GregorianCalendar calCurrent = new GregorianCalendar();
		   String year=Integer.toString(calCurrent.get(Calendar.YEAR));
		   int dayOfMonthInt =calCurrent.get(Calendar.DATE);
		   String dayOfMonth="";
		   String month="";
		   int monthInt=calCurrent.get(Calendar.MONTH)+1;
		   if(monthInt<10){
			    month+="0"+monthInt;
	       }else{
				 month+=monthInt;
	       }
		   if(dayOfMonthInt<10){
			   dayOfMonth="0"+dayOfMonthInt;
	       }else{
	    	   dayOfMonth+=dayOfMonthInt;
	       }
		   return year+"-"+month+"-"+dayOfMonth;
	}
	private void resetLastUpdateDetail(BookMenuForm bookMenuForm){
		bookMenuForm.setDisplayAuditTrailName("");
		bookMenuForm.setLastmodifiedforbook("");
		bookMenuForm.setWhoModifiedPbook("" );
		bookMenuForm.setLastmodifiedforRbook("");
		bookMenuForm.setWhoModifiedRBook("");
		bookMenuForm.setLastmodifiedforEbook("");
		bookMenuForm.setWhoModifiedEBook("");
	}
	private void setListsAndStatuses(ActionMapping mapping,
			    ActionForm form,
			    HttpServletRequest request,
			    HttpServletResponse response) throws Exception{
			    BookMenuForm bookMenuForm = (BookMenuForm) form;
		        CourseDAO dao = new CourseDAO();
		      	String studyUnitCode = bookMenuForm.getSelectedCourse();
		    	setListStatuses(bookMenuForm,studyUnitCode,bookMenuForm.getAcadyear());
			    bookMenuForm.setLastUpdated(dao.getLastUpdated(studyUnitCode,bookMenuForm.getAcadyear(),"P"));
				String lastUpdatedDisplayName="";
				if(!dao.getOpenAndClosingDatesOfblist("P",Integer.parseInt(bookMenuForm.getAcadyear()),studyUnitCode).isEmpty()){
					  bookMenuForm.setDateIsWithinLimits(true);
				}else{
					bookMenuForm.setDateIsWithinLimits(false);
				}
				if(!dao.getOpenAndClosingDatesOfblist("R",Integer.parseInt(bookMenuForm.getAcadyear()),studyUnitCode).isEmpty()){
					bookMenuForm.setDateIsWithinLimitsForR(true);
				}else{
					bookMenuForm.setDateIsWithinLimitsForR(false);
				}
				if(!dao.getOpenAndClosingDatesOfblist("E",Integer.parseInt(bookMenuForm.getAcadyear()),studyUnitCode).isEmpty()){
					bookMenuForm.setDateIsWithinLimitsForE(true);
				}else{
					bookMenuForm.setDateIsWithinLimitsForE(false);
				}
				if (bookMenuForm.getLastUpdated() != null){
					String userId = bookMenuForm.getLastUpdated().getNetworkId();
					userDirectoryService = (UserDirectoryService) ComponentManager.get(UserDirectoryService.class);
					try{
					User lastUpdatedUserDetails = userDirectoryService.getUserByEid(userId);
					 	 lastUpdatedDisplayName = lastUpdatedUserDetails.getDisplayName();
					} catch (UserNotDefinedException exception){
						 lastUpdatedDisplayName="User Unknown";
					}		
					bookMenuForm.setDisplayAuditTrailName(lastUpdatedDisplayName);
					bookMenuForm.setLastmodifiedforbook(bookMenuForm.getLastUpdated().getTransactionTime());
					bookMenuForm.setWhoModifiedPbook(lastUpdatedDisplayName );
					bookMenuForm.setBookList(dao.getBookList(studyUnitCode,bookMenuForm.getAcadyear(),"P"));
					
				}
				bookMenuForm.setRlastUpdated(dao.getLastUpdated(studyUnitCode, bookMenuForm.getAcadyear(),"R"));
				if (bookMenuForm.getRlastUpdated() != null){
					String userId = bookMenuForm.getRlastUpdated().getNetworkId();
					userDirectoryService = (UserDirectoryService) ComponentManager.get(UserDirectoryService.class);
					String lastUpdatedDisplayNameForR="";
					try{
					User lastUpdatedUserDetails = userDirectoryService.getUserByEid(userId);
					 lastUpdatedDisplayNameForR = lastUpdatedUserDetails.getDisplayName();
					}catch (UserNotDefinedException e) {
						lastUpdatedDisplayNameForR="User Unknown";
					}
					bookMenuForm.setDisplayAuditTrailName(lastUpdatedDisplayNameForR);
					bookMenuForm.setLastmodifiedforRbook(bookMenuForm.getRlastUpdated().getTransactionTime());
					bookMenuForm.setWhoModifiedRBook(lastUpdatedDisplayNameForR);
					bookMenuForm.setBookList(dao.getBookList(studyUnitCode,bookMenuForm.getAcadyear(),"R"));
				}
				bookMenuForm.setElastUpdated(dao.getLastUpdated(studyUnitCode, bookMenuForm.getAcadyear(),"E"));
				if (bookMenuForm.getElastUpdated()!= null){
					String userId = bookMenuForm.getElastUpdated().getNetworkId();
					userDirectoryService = (UserDirectoryService) ComponentManager.get(UserDirectoryService.class);
					String lastUpdatedDisplayNameForE="";
					try{
					User lastUpdatedUserDetails = userDirectoryService.getUserByEid(userId);
					lastUpdatedDisplayNameForE = lastUpdatedUserDetails.getDisplayName();
					}catch (UserNotDefinedException e) {
						lastUpdatedDisplayNameForE="User Unknown";
					}
					bookMenuForm.setDisplayAuditTrailName(lastUpdatedDisplayNameForE);
					bookMenuForm.setLastmodifiedforEbook(bookMenuForm.getElastUpdated().getTransactionTime());
					bookMenuForm.setWhoModifiedEBook(lastUpdatedDisplayNameForE);
					bookMenuForm.setBookList(dao.getBookList(studyUnitCode,bookMenuForm.getAcadyear(),"E"));
				}
     }
	private String getBookListStatusFromDAO(String studyUnitCode, String acadYear, String typeOfbook) throws Exception{
		  String bookListStatus = "Booklist open for editing";
		  CourseDAO dao = new CourseDAO();
		  BookDetails bookDetails = new BookDetails();
		  String status = dao.getDBBookListStatus(studyUnitCode, acadYear,typeOfbook).toString();
		  bookDetails.setStatus(status); 
		  if(status.equalsIgnoreCase("0")){
				bookListStatus = "Booklist open for editing";
		   } else if(status.equalsIgnoreCase("3")){
			   bookListStatus = "No Books prescribed for this course";
		   }else if(status.equalsIgnoreCase("1")){
			   bookListStatus = "Booklist submitted for authorization";
		   }else if(status.equalsIgnoreCase("2")){
			   bookListStatus = "Booklist authorized by COD";	
		   }else if(status.equalsIgnoreCase("4")){
			   bookListStatus = "Booklist authorized by School Director";	
		   }else if(status.equalsIgnoreCase("5")){
			   bookListStatus = "Booklist authorized by Dean";	
		   }
		   else if(status.equalsIgnoreCase("7")){
			   bookListStatus = "Booklist open for editing by administrator";	
		   }else if(status.equalsIgnoreCase("6")){
			   bookListStatus = "Book list published by administrator";	
		   }else{
		     bookListStatus = "Booklist open for editing";
		   }
		return bookListStatus;
	}
	private void setListStatuses(BookMenuForm  bmk,String studyUnitCode, String acadYear) throws Exception{
		    String pstatus = getBookListStatusFromDAO(studyUnitCode,acadYear,"P");
		    String rstatus = getBookListStatusFromDAO(studyUnitCode,acadYear,"R");
		    String estatus = getBookListStatusFromDAO(studyUnitCode,acadYear,"E");
			bmk.setBookListStatus(pstatus);
			bmk.setRbookListStatus(rstatus);
			bmk.setEbookListStatus(estatus);
			
			//Sifiso Changes: 2017/08/08:LU_MA_BLU_06:Requirement 4:Tutor Booklist: Set Tutor Status-Prescribed ("P") ONLY 
			int tutorStatus = 0;
			try{
				CourseDAO dao = new CourseDAO();
				tutorStatus = dao.getDBTutorBookListStatus( studyUnitCode, acadYear, "P" ); 
			}catch( Exception ex ){
				throw new Exception("PrebookAction : setListStatuses Error occurred / "+ ex,ex);
			}
			bmk.setTutorBookListStatus( tutorStatus );
    }
	private void sendEmail(String subject, String body, String emailAddress) throws AddressException {
 		  String tmpEmailFrom = ServerConfigurationService.getString("noReplyEmailFrom");
		  InternetAddress toEmail = new InternetAddress(emailAddress);
		  InternetAddress iaTo[] = new InternetAddress[1];
		  iaTo[0] = toEmail;
		  InternetAddress iaHeaderTo[] = new InternetAddress[1];
		  iaHeaderTo[0] = toEmail;
		  InternetAddress iaReplyTo[] = new InternetAddress[1];
		  iaReplyTo[0] = new InternetAddress(tmpEmailFrom);
		  List contentList = new ArrayList();
		  contentList.add("Content-Type: text/html");
		  //contentList.add("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		  emailService = (EmailService) ComponentManager.get(EmailService.class);
		  emailService.sendMail(iaReplyTo[0],iaTo,subject,body,iaHeaderTo,iaReplyTo,contentList);
		  log.info("sending mail from "+iaReplyTo[0]+" to "+toEmail.toString());
	} // end of sendEmail
	public ActionForward hodApproval(
					ActionMapping mapping,
					ActionForm form,
					HttpServletRequest request,
					HttpServletResponse response){
					BookMenuForm bookMenuForm = (BookMenuForm) form;
					CourseDAO dao = new CourseDAO();
					ActionMessages messages = new ActionMessages();
					messages.add(ActionMessages.GLOBAL_MESSAGE,
					        new ActionMessage("message.generalmessage", "Are you sure you want to approve the following Book List information?"));
					addErrors(request, messages);
					try {
						bookMenuForm.setBookList(dao.getBookList(bookMenuForm.getCourseId(),bookMenuForm.getAcadyear(),bookMenuForm.getTypeOfBookList()));
					} catch (Exception ex){
						ex.printStackTrace();
					}
					return mapping.findForward("hodapproval");
	}
	public ActionForward approve(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response)throws Exception{
		BookMenuForm bookMenuForm = (BookMenuForm) form;		
		CourseDAO dao = new CourseDAO();
		ActionMessages messages = new ActionMessages();
		String hodComments = bookMenuForm.getHodComments();
		
/*		dao.updateBookListStatus("2", bookMenuForm.getCourseId(), bookMenuForm.getAcadyear(),bookMenuForm.getNetworkId());
		updateAuditTrail(bookMenuForm, "Book list Approved");*/
		if (hodComments.length() > 2){
		    	messages.add(ActionMessages.GLOBAL_MESSAGE,
				        new ActionMessage("message.generalmessage", "To approve, remove comments."));
				addErrors(request, messages);
				try {
					bookMenuForm.setBookList(dao.getBookList(bookMenuForm.getCourseId(),bookMenuForm.getAcadyear(),bookMenuForm.getTypeOfBookList()));
				} catch (Exception ex){
					ex.printStackTrace();
				}
				return mapping.findForward("hodapproval");
		}else{
		User hodDetails = null;
		try {
			userDirectoryService = (UserDirectoryService) ComponentManager.get(UserDirectoryService.class);
			hodDetails = userDirectoryService.getUserByEid(bookMenuForm.getNetworkId());
		} catch (UserNotDefinedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String hodTitleName = hodDetails.getDisplayName();
		String emailSubject = "HOD Approval of Booklist for " +bookMenuForm.getCourseId();
		String emailMessage = "NB: This is an automated message - do not reply to this e-mail. <br>"+
				"<p>"+
				"The HOD, "+hodTitleName+" has approved the prescribed book list for " +bookMenuForm.getCourseId()+ " for " +(bookMenuForm.getAcadyear())+".<br>" +
				"<p>"+
				"The list has now been sent to the Book List Administrator for verification and release to the Unisa Official Booksellers. <br>" +
				"<p>"+
				"Regards <br>" +
				"myUnisa Support Team on behalf of " +hodTitleName;
		List users = null;
		try {
			users = dao.getUserId(bookMenuForm.getCourseId(),bookMenuForm.getAcadyear());
			
			Iterator i = users.iterator();
			String userId = null;
			while(i.hasNext()){
				userId = i.next().toString();
				String[] emailaddress = userId.split("=");
				String[] temp = emailaddress[1].split("}");
				temp[0]+= "@unisa.ac.za";
				
				System.out.println(temp[0]);
				//sendEmail(emailSubject, emailMessage, temp[0]);
				
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
/*			dao.updateBookListStatus("0", bookMenuForm.getCourseId(), bookMenuForm.getAcadyear(),bookMenuForm.getNetworkId());
		updateAuditTrail(bookMenuForm, "Edit request by HOD");*/
		messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("message.generalmessage","Book list was successfully approved."));
		addMessages(request, messages);
		saveMessages(request, messages);
		return confirmBooks(mapping, form, request, response);
	}	
	}
	public ActionForward remindAcademic(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response)throws Exception{
		BookMenuForm bookMenuForm = (BookMenuForm) form;
		CourseDAO dao = new CourseDAO();
		String hodComments = bookMenuForm.getHodComments();
		ActionMessages messages = new ActionMessages();
		boolean confirmStatus = false;
		Iterator i = bookMenuForm.getBookList().iterator();
		//System.out.println(bookMenuForm.getBookList().iterator());
		while (i.hasNext()){
			BookDetails bookDetails = (BookDetails) i.next();
			if (bookDetails.isRemove()){
				confirmStatus = true;
			}
		}
		if (!confirmStatus){
			User hodDetails = null;
			
			try {
				userDirectoryService = (UserDirectoryService) ComponentManager.get(UserDirectoryService.class);
				hodDetails = userDirectoryService.getUserByEid(bookMenuForm.getNetworkId());
			} catch (UserNotDefinedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String hodTitleName = hodDetails.getDisplayName();
			String emailSubject = "Reminder to Update Booklist for " +bookMenuForm.getCourseId();
			String emailMessage = "NB: This is an automated message - do not reply to this e-mail. <br>"+
					"<p>"+
					"This e-mail serves as a reminder to complete the update of the prescribed book list for " +bookMenuForm.getCourseId()+ " for " +(bookMenuForm.getAcadyear())+".<br>" +
					"<p>"+
					"To update this book list, go to the myUnisa site at https://my.unisa.ac.za/portal, login and select the Course Admin site.  The Book List Management tool is accessible from the left navigation. <br>" +
					"<p>"+
					"Regards <br>" +
					"myUnisa Support Team on behalf of " +hodTitleName;
			List users = null;
			try {
				users = dao.getUserId(bookMenuForm.getCourseId(),bookMenuForm.getAcadyear());
				
				Iterator imail = users.iterator();
				String userId = null;
				while(imail.hasNext()){
					//System.out.println("Testing if e-mail sends");
					userId = imail.next().toString();
					String[] emailaddress = userId.split("=");
					String[] temp = emailaddress[1].split("}");
					temp[0]+= "@unisa.ac.za";
					
					//System.out.println(temp[0]);
					//sendEmail(emailSubject, emailMessage, temp[0]);
					
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			messages.add(ActionMessages.GLOBAL_MESSAGE,
			        new ActionMessage("message.generalmessage", "An e-mail was sent to the no of lecturers linked to this course."));
			addErrors(request, messages);
			return confirmBooks(mapping, form, request, response);
		} else {
			messages.add(ActionMessages.GLOBAL_MESSAGE,
			        new ActionMessage("message.generalmessage", "Cannot remind academic when a book item is checked for Removal."));
			addErrors(request, messages);
			//return mapping.findForward("bookconfirm");
			return confirmBooks(mapping, form, request, response);
		}
	}
	public ActionForward hodRequestEdit(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response){
		BookMenuForm bookMenuForm = (BookMenuForm) form;		
		CourseDAO dao = new CourseDAO();
		
		String hodComments = bookMenuForm.getHodComments();
		
		ActionMessages messages = new ActionMessages();
		
		if (hodComments.length() < 3){
	    	messages.add(ActionMessages.GLOBAL_MESSAGE,
			        new ActionMessage("message.generalmessage", "Comments must be added to inform academic of the reasons for not approving the book list."));
			addErrors(request, messages);
			try {
				bookMenuForm.setBookList(dao.getBookList(bookMenuForm.getCourseId(),bookMenuForm.getAcadyear(),bookMenuForm.getTypeOfBookList()));
			} catch (Exception ex){
				ex.printStackTrace();
			}
			return mapping.findForward("hodapproval");
		} else{
			User hodDetails = null;
			try {
				userDirectoryService = (UserDirectoryService) ComponentManager.get(UserDirectoryService.class);
				hodDetails = userDirectoryService.getUserByEid(bookMenuForm.getNetworkId());
			} catch (UserNotDefinedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String hodTitleName = hodDetails.getDisplayName();
			String emailSubject = "HOD Approval of Booklist for " +bookMenuForm.getCourseId();
			String emailMessage = "NB: This is an automated message - do not reply to this e-mail. <br>"+
					"<p>"+
					"The HOD, "+hodTitleName+" has approved the prescribed book list for " +bookMenuForm.getCourseId()+ " for " +(bookMenuForm.getAcadyear())+"<br>" +
					"<p>"+
					"The list has now been sent to the Book List Administrator for verification and release to the Unisa Official Booksellers. <br>" +
					"<p>"+
					bookMenuForm.getHodComments()+ " from "+hodTitleName+ ".<br>"+
					"<p>"+
					"To edit this book list, go to the myUnisa site at https://my.unisa.ac.za/portal, "+
					"login and select the Course Admin site. The Book List Management tool is accessible from the left navigation. <br>"+
					"<p>"+
					"Regards <br>" +
					"myUnisa Support Team on behalf of " +hodTitleName;
			List users = null;
			try {
				users = dao.getUserId(bookMenuForm.getCourseId(),bookMenuForm.getAcadyear());
				
				Iterator i = users.iterator();
				String userId = null;
				while(i.hasNext()){
					//System.out.println("Testing if e-mail sends");
					userId = i.next().toString();
					String[] emailaddress = userId.split("=");
					String[] temp = emailaddress[1].split("}");
					temp[0]+= "@unisa.ac.za";
					
					//System.out.println(temp[0]);
					//sendEmail(emailSubject, emailMessage, temp[0]);
					
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
/*			dao.updateBookListStatus("0", bookMenuForm.getCourseId(), bookMenuForm.getAcadyear(),bookMenuForm.getNetworkId());
			updateAuditTrail(bookMenuForm, "Edit request by HOD");*/
			return mapping.findForward("bookconfirm");
			//return confirmBooks(mapping, form, request, response);	
		}
		
	}	
	public ActionForward confirmBooks(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception{
			return doTheConfirming(mapping,form,request,response);
					//return mapping.findForward("hodapproval");
	}
	public ActionForward doTheConfirming(ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception,UserNotDefinedException{
	
		CourseDAO dao = new CourseDAO();
	BookMenuForm bookMenuForm = (BookMenuForm) form;
	String studyUnitCode = bookMenuForm.getCourseId();
	String courseNote;
	//Sifiso Changes: 2017/08/08:LU_MA_BLU_06:Requirement 4:Get Tutor Count for viewing
	bookMenuForm.setTutorCount( dao.getTutorCount(bookMenuForm.getCourseId(), bookMenuForm.getAcadyear(),bookMenuForm.getTypeOfBookList()) );
	   
	try {
		String bookListStatus = getBookListStatusFromDAO(bookMenuForm.getCourseId(), bookMenuForm.getAcadyear(),bookMenuForm.getTypeOfBookList());
		if(bookMenuForm.getTypeOfBookList().equals("P")){
			if(bookMenuForm.isDateIsWithinLimits()){
				   bookMenuForm.setBookListStatus(bookListStatus);
				   bookMenuForm.setBookListDatesStatus("");
			}else{
				   bookMenuForm.setBookListDatesStatus("booklist Closed");
			}
		}
		if(bookMenuForm.getTypeOfBookList().equals("R")){
			if(bookMenuForm.isDateIsWithinLimitsForR()){
				   bookMenuForm.setBookListStatus(bookListStatus);
				   bookMenuForm.setBookListDatesStatus("");
			}else{
				bookMenuForm.setBookListDatesStatus("booklist Closed");	
			}
		}
		if(bookMenuForm.getTypeOfBookList().equals("E")){
			if(bookMenuForm.isDateIsWithinLimitsForE()){
				   bookMenuForm.setBookListStatus(bookListStatus);
				   bookMenuForm.setBookListDatesStatus("");
			}else{
				bookMenuForm.setBookListDatesStatus("booklist Closed");	
			}
		}
		courseNote = dao.getCourseNote(bookMenuForm.getCourseId(), bookMenuForm.getAcadyear(),bookMenuForm.getTypeOfBookList());
		bookMenuForm.setCourseNote(courseNote);
		bookMenuForm.setLastUpdated(dao.getLastUpdated(bookMenuForm.getCourseId(), bookMenuForm.getAcadyear(),bookMenuForm.getTypeOfBookList()));
		// After setting the Audit Trail, now resolve the fullName of last updated User.
		if ( bookMenuForm.getLastUpdated() != null) {
			String userId = bookMenuForm.getLastUpdated().getNetworkId();
			userDirectoryService = (UserDirectoryService) ComponentManager.get(UserDirectoryService.class);
			
			String lastUpdatedDisplayName="";
			try{
			User lastUpdatedUserDetails = userDirectoryService.getUserByEid(userId);
			lastUpdatedDisplayName = lastUpdatedUserDetails.getDisplayName();
			}catch (UserNotDefinedException exception) {
				lastUpdatedDisplayName="User Unknown";
			}
			bookMenuForm.setDisplayAuditTrailName(lastUpdatedDisplayName);
		}
		CollegeDeptDetails coldept = new CollegeDeptDetails();
		coldept = dao.getCollegeDeptDetails(studyUnitCode);
		bookMenuForm.setCollege(coldept.getCollege());
		bookMenuForm.setDepartment(coldept.getDepartment());
		bookMenuForm.setCodInitials(coldept.getCodInitials());
		bookMenuForm.setCodSurname(coldept.getCodSurname());
		bookMenuForm.setCodNovelluserId(coldept.getCodNevellUserCode());	
		bookMenuForm.setDeptCode(coldept.getDeptCode());
		if(bookMenuForm.getDeptCode()==null ||bookMenuForm.getDeptCode().length()==0){
			coldept = dao.getStandInDetails(studyUnitCode);
			bookMenuForm.setCollege(coldept.getCollege());
			bookMenuForm.setDepartment(coldept.getDepartment());
			bookMenuForm.setDeptCode(coldept.getDeptCode());
		}		
	    bookMenuForm.setStandInChairs(dao.getActingCod(bookMenuForm.getDeptCode()));
      
     // getActingCodInfo
 /*     bookMenuForm.setCollegeDeptDetails(dao.getActingCodInfo(bookMenuForm.getDeptCode()));
      bookMenuForm.setStandinInitial(bookMenuForm.getCollegeDeptDetails().getStandInName());
      bookMenuForm.setStandinNovellCode(bookMenuForm.getCollegeDeptDetails().getStandInSurname());
      bookMenuForm.setStandinSurname(bookMenuForm.getCollegeDeptDetails().getStandInSurname());
   
      System.out.println("stand in list "+bookMenuForm.getStandInChairs());
      System.out.println("stand in list size "+bookMenuForm.getStandInChairs().size());
      System.out.println("stand in list "+ bookMenuForm.getStandinInitial()+"surname  "+bookMenuForm.getStandinSurname());
*/
		
		/*bookMenuForm.setCollegeChair(coldept.getCollegeChair());
		bookMenuForm.setPersno(coldept.getPersno());*/
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	ActionMessages messages = new ActionMessages();
	if (isCancelled(request)){
		return viewBookMenu(mapping, form, request, response);
	}
	if ((bookMenuForm.getTypeOfBookList() == null) && ((bookMenuForm.getCourseId() == null) || (bookMenuForm.getCourseId().length()==0))){
		messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("message.generalmessage","Please select option and course."));
		addErrors(request, messages);
		
		return viewBookMenu(mapping, form, request, response);
	}
	if (bookMenuForm.getTypeOfBookList() == null){
		messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("message.generalmessage","Please select Update Prescribed Books option."));
		addErrors(request, messages);				
		return viewBookMenu(mapping, form, request, response);
	}
	if ((bookMenuForm.getCourseId() == null) || (bookMenuForm.getCourseId().length()==0)){
		messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("message.generalmessage","Please select course code."));
		addErrors(request, messages);
		return viewBookMenu(mapping, form, request, response);
	}
	
	String bookListStatus;
	try {
		bookListStatus = getBookListStatusFromDAO(bookMenuForm.getCourseId(), bookMenuForm.getAcadyear(),bookMenuForm.getTypeOfBookList());
		if(bookListStatus == "no status"){				
		messages.add(ActionMessages.GLOBAL_MESSAGE,
		        new ActionMessage("message.generalmessage", "No booklist information found for the selected course"));
		addErrors(request, messages);
		
		return viewBookMenu(mapping, form, request, response);
	}
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	try {
		bookMenuForm.setBookList(dao.getBookList(bookMenuForm.getCourseId(),bookMenuForm.getAcadyear(),bookMenuForm.getTypeOfBookList()));
		//bookMenuForm.setCopiedBook(dao.getEditOption(bookMenuForm.getCourseId(),bookMenuForm.getAcadyear(),bookMenuForm.getTypeOfBookList()));
	} catch (Exception ex){
		ex.printStackTrace();
	}
	return mapping.findForward("bookconfirm");
}

	public ActionForward copyBooks(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response)throws Exception{
		BookMenuForm bookMenuForm = (BookMenuForm) form;
		ActionMessages messages = new ActionMessages();
		CourseDAO dao = new CourseDAO();
		Iterator i = bookMenuForm.getBookList().iterator();
		GregorianCalendar calCurrent = new GregorianCalendar();
		boolean copyStatus = false;
		if (bookMenuForm.getCopyExistingBookOption().equalsIgnoreCase("COPYEXISTINGBOOKS")){
			if (bookMenuForm.getCopyBook() == null){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
				        new ActionMessage("message.generalmessage", "Select the book or click cancel button"));
					addErrors(request, messages);
				return mapping.findForward("bookfind");
			} else {
				return copyExistingBook(mapping, form, request, response);
			}
		}
		while (i.hasNext()){
			BookDetails bookDetails = (BookDetails) i.next();
			bookDetails.setTypeOfBookList(bookMenuForm.getTypeOfBookList());
			if (bookDetails.isRemove()){
				copyStatus =  true;
				dao.linkBook(bookDetails,bookMenuForm.getCourseId(),bookMenuForm.getAcadyear());
				updateAuditTrail(bookMenuForm, "Linked booknr "+bookDetails.getBookId());
			}
		}
		if (!copyStatus){
			messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("message.generalmessage","Please tick check box and click copy."));
			addErrors(request, messages);
			return bookReuse(mapping, form, request, response);
		}
		//updateAuditTrail(bookMenuForm, "Linked the books");
		messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("message.generalmessage","You have successfully copied the book(s)"));
		addMessages(request, messages);
		saveMessages(request, messages);
		return confirmBooks(mapping, form, request, response);
	}
	public ActionForward searchBook(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response){
		
		HttpSession session = request.getSession(true);
		BookMenuForm bookMenuForm = (BookMenuForm) form;
		
		if(bookMenuForm.getTypeOfBookList().equals("E")){
			//bookMenuForm.setSearchOption("Journal");
		}
		bookMenuForm.setTxtAuthor("");
		bookMenuForm.setTxtTitle("");
		
		return mapping.findForward("searchform");
	}
		public ActionForward confirmBookAcademic(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		BookMenuForm bookMenuForm = (BookMenuForm) form;
		
		ActionMessages messages = new ActionMessages();
		boolean confirmStatus = false;
		//find the department for the selected course
		
		Iterator i = bookMenuForm.getBookList().iterator();
		while (i.hasNext()){
			BookDetails bookDetails = (BookDetails) i.next();
			if (bookDetails.isRemove()){
				confirmStatus = true;
			}
		}
		if (!confirmStatus){
			if(bookMenuForm.getTypeOfBookList().equals("E")){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
				        new ActionMessage("message.generalmessage", "Are you sure you want to submit the following eReserves List Information and send it for authorization?"));
				addErrors(request, messages);
				return mapping.findForward("confirmbyacademicform");
			}else{
			messages.add(ActionMessages.GLOBAL_MESSAGE,
			        new ActionMessage("message.generalmessage", "Are you sure you want to submit the following Book List Information and send it for authorization?"));
			addErrors(request, messages);
			return mapping.findForward("confirmbyacademicform");
		}
		} else {
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					//Changed 09-04-09 to meet new spec changes, no longer use Confirm but Submit Final Booklist
			        //new ActionMessage("message.generalmessage", "Cannot Confirm when a book item is checked for Removal."));
					new ActionMessage("message.generalmessage", "Cannot Submit Final Booklist when a book item is checked for Removal."));
			addErrors(request, messages);
			//return mapping.findForward("bookconfirm");
			return confirmBooks(mapping, form, request, response);
		}
	}
	
	public ActionForward findBooks(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response){
			BookMenuForm bookMenuForm = (BookMenuForm) form;
			HttpSession session = request.getSession(true);
			
			String bookType = bookMenuForm.getSearchOption();
			session.setAttribute("bookType",bookType);
			
			
			CourseDAO dao = new CourseDAO();
			ActionMessages messages = new ActionMessages();
			bookMenuForm.setBookList(null);
			
			if(bookMenuForm.getSearchOption() == null && bookMenuForm.getTypeOfBookList().equals("E")){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
				        new ActionMessage("message.generalmessage", "Please select the type of eReserve you would like to search for."));
				addErrors(request, messages);
				return mapping.findForward("searchform");
			}
			
			else{
				if (bookMenuForm.getTxtTitle().equalsIgnoreCase("")  && bookMenuForm.getTxtAuthor().equalsIgnoreCase("")){
			    	messages.add(ActionMessages.GLOBAL_MESSAGE,
					        new ActionMessage("message.generalmessage", "Please enter Title or/and Author."));
					addErrors(request, messages);
					return mapping.findForward("searchform");
				} else if ((!bookMenuForm.getTxtTitle().equalsIgnoreCase("") || !bookMenuForm.getTxtAuthor().equalsIgnoreCase(""))){
					if (bookMenuForm.getTxtTitle().length() < 3 && bookMenuForm.getTxtAuthor().length() < 3){
						messages.add(ActionMessages.GLOBAL_MESSAGE,
					        new ActionMessage("message.generalmessage", "Please enter valid data of at least three characters in either Title or Author to continue your search for book information."));
						addErrors(request, messages);
						return mapping.findForward("searchform");
					} 
				}
			}	
			try {

				Iterator linkedBooks = null;
				Vector<BookDetails> tempList = null;
				Iterator searchedBooks = null;
				
				String ereserveType4WhereClause = bookMenuForm.getSearchOption();
				session.setAttribute("ereserveType4WhereClause",ereserveType4WhereClause);
				
				if(bookMenuForm.getSearchOption() != null)
				{
					if(bookMenuForm.getSearchOption().equalsIgnoreCase("Journal") || bookMenuForm.getSearchOption().equalsIgnoreCase("Law Report") || bookMenuForm.getSearchOption().equalsIgnoreCase("Book Chapter"))
					{
						bookMenuForm.setSearchOption("addform");
						linkedBooks = dao.getBookList(bookMenuForm.getCourseId(),bookMenuForm.getAcadyear(),bookMenuForm.getTypeOfBookList()).iterator();
						tempList = new Vector<BookDetails>();
						searchedBooks = dao.getSearchedEreserveBookList(bookMenuForm.getTxtTitle(), bookMenuForm.getTxtAuthor(),new Integer(bookMenuForm.getAcadyear()),bookMenuForm.getCourseId(),bookMenuForm.getTypeOfBookList(),(String)session.getAttribute("ereserveType4WhereClause")).iterator();
						bookMenuForm.seteReserveType((String)session.getAttribute("ereserveType4WhereClause"));
					}
					else
					{
						bookMenuForm.setSearchOption("addform");
						linkedBooks = dao.getBookList(bookMenuForm.getCourseId(),bookMenuForm.getAcadyear(),bookMenuForm.getTypeOfBookList()).iterator();
						tempList = new Vector<BookDetails>();
						searchedBooks = dao.getSearchedBookList(bookMenuForm.getTxtTitle(), bookMenuForm.getTxtAuthor(),new Integer(bookMenuForm.getAcadyear()),bookMenuForm.getCourseId(),bookMenuForm.getTypeOfBookList()).iterator();
					}
				}
				else
				{
					bookMenuForm.setSearchOption("addform");
					linkedBooks = dao.getBookList(bookMenuForm.getCourseId(),bookMenuForm.getAcadyear(),bookMenuForm.getTypeOfBookList()).iterator();
					tempList = new Vector<BookDetails>();
					searchedBooks = dao.getSearchedBookList(bookMenuForm.getTxtTitle(), bookMenuForm.getTxtAuthor(),new Integer(bookMenuForm.getAcadyear()),bookMenuForm.getCourseId(),bookMenuForm.getTypeOfBookList()).iterator();
				}
				
				boolean matchFound = true;

				while (searchedBooks.hasNext()){
					matchFound = true;
					BookDetails bookDetails = (BookDetails) searchedBooks.next();
					
					while(linkedBooks.hasNext() && matchFound)
					{
						BookDetails bDetails = (BookDetails) linkedBooks.next();
						
						if (bDetails.getBookId().equalsIgnoreCase(bookDetails.getBookId()))
						{
							matchFound = false;					
						}
					}
					if (!matchFound){					
					} 
					else 
					{						
						tempList.addElement(bookDetails);
					}
				}
				bookMenuForm.setBookList(tempList);
				if (bookMenuForm.getBookList().size() == 0){
					if(bookMenuForm.getTypeOfBookList().equals("E")){
						bookMenuForm.setEreserveSearchOption("addNew");
						messages.add(ActionMessages.GLOBAL_MESSAGE,
						        new ActionMessage("message.generalmessage", "No entries found that match your search criteria."));
							addErrors(request, messages);
									
					}else{
					bookMenuForm.setEreserveSearchOption("addNew");
					messages.add(ActionMessages.GLOBAL_MESSAGE,
					        new ActionMessage("message.generalmessage", "No book entries found that match your search criteria."));
						addErrors(request, messages);
						
					}
				}			
				
			} catch (Exception e) {
					e.printStackTrace();
			}		
		
		return mapping.findForward("bookfind");
	}
	public ActionForward copyExistingBook(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception{
			BookMenuForm bookMenuForm = (BookMenuForm) form;
			CourseDAO dao = new CourseDAO();
			if (bookMenuForm.getCopyBook().equalsIgnoreCase("") || bookMenuForm.getCopyBook() == null){
				return mapping.findForward("bookfind");
			} else {
				BookDetails bookDetails = new BookDetails();
			//	dao.insertBookLink(bookMenuForm.getCourseId(),bookMenuForm.getCopyBook(),"B",bookMenuForm.getTypeOfBookList(),bookMenuForm.getAcadyear());
				bookDetails.setTypeOfBookList(bookMenuForm.getTypeOfBookList());
				bookDetails.setBookId(bookMenuForm.getCopyBook());
				
				dao.linkBook(bookDetails,bookMenuForm.getCourseId(),bookMenuForm.getAcadyear());
				updateAuditTrail(bookMenuForm, "Linked booknr " +bookMenuForm.getCopyBook());
			}
			return confirmBooks(mapping, form, request, response);
	}	
	
	public ActionForward courseNote(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response){
		BookMenuForm bookMenuForm = (BookMenuForm) form;
		CourseDAO dao = new CourseDAO();
		try {
			dao.getCourseNote(bookMenuForm.getCourseId(), bookMenuForm.getAcadyear(),bookMenuForm.getTypeOfBookList());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward("coursenote");
	}
	
	public ActionForward bookReuse(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response){
		BookMenuForm bookMenuForm = (BookMenuForm) form;
		CourseDAO dao = new CourseDAO();
		//GregorianCalendar calCurrent = new GregorianCalendar();
		try {		
			bookMenuForm.setBookList(dao.getBookCopyList(bookMenuForm.getCourseId(),bookMenuForm.getAcadyear(),bookMenuForm.getTypeOfBookList()));
		} catch (Exception ex){
			ex.printStackTrace();
		}
		
		//System.out.println("Test size"+ bookMenuForm.getBookList().size());
		
		//bookMenuForm.setCopyBook(copyBook)
		if (bookMenuForm.getBookList().size() == 0){
			
			try {
				bookMenuForm.setBookList(dao.getBookList(bookMenuForm.getCourseId(),(Integer.parseInt(bookMenuForm.getAcadyear())-1)+"",bookMenuForm.getTypeOfBookList()));
				bookMenuForm.setAcadyear(bookMenuForm.getAcadyear());
			} catch (Exception ex){
				ex.printStackTrace();
			}
			ActionMessages messages = new ActionMessages();
	    	messages.add(ActionMessages.GLOBAL_MESSAGE,
			        new ActionMessage("message.generalmessage", "You cannot reuse, no books to copy from previous year."));
			addErrors(request, messages);

			return mapping.findForward("bookconfirm");
			
		}else{
			return mapping.findForward("bookreuse");
		}
		
	}
	public ActionForward declareNopBooks(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		BookMenuForm bookMenuForm = (BookMenuForm) form;
		
		ActionMessages messages = new ActionMessages();
		CourseDAO dao = new CourseDAO();
		try {
			bookMenuForm.setBookList(dao.getBookList(bookMenuForm.getCourseId(), bookMenuForm.getAcadyear(),bookMenuForm.getTypeOfBookList()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	if(bookMenuForm.getTypeOfBookList().equals("P")){
		if (bookMenuForm.getBookList().size() == 0){
			
	    	messages.add(ActionMessages.GLOBAL_MESSAGE,
			        new ActionMessage("message.generalmessage", "Are you sure there are no Prescribed Books for this course?"));
			addErrors(request, messages);
		}else if (bookMenuForm.getBookList().size() > 0){
			
	    	messages.add(ActionMessages.GLOBAL_MESSAGE,
			        new ActionMessage("message.generalmessage", "Book items have been added for this course. Please remove all book items from this view before declaring no prescribed books"));
			addErrors(request, messages);
			return confirmBooks(mapping, form, request, response);
		}
	}else if(bookMenuForm.getTypeOfBookList().equals("R")){
	if (bookMenuForm.getBookList().size() == 0){
			
	    	messages.add(ActionMessages.GLOBAL_MESSAGE,
			        new ActionMessage("message.generalmessage", "Are you sure there are no Recommeneded  Books for this course?"));
			addErrors(request, messages);
		}else if (bookMenuForm.getBookList().size() > 0){
			
	    	messages.add(ActionMessages.GLOBAL_MESSAGE,
			        new ActionMessage("message.generalmessage", "Book items have been added for this course.  Please remove all book items from this view before declaring no recommended books."));
			addErrors(request, messages);
			return confirmBooks(mapping, form, request, response);
		}
	}else{
	if (bookMenuForm.getBookList().size() == 0){
			
	    	messages.add(ActionMessages.GLOBAL_MESSAGE,
			        new ActionMessage("message.generalmessage", "Are you sure there are no eReserves for this course?"));
			addErrors(request, messages);
		}else if (bookMenuForm.getBookList().size() > 0){
			
	    	messages.add(ActionMessages.GLOBAL_MESSAGE,
			        new ActionMessage("message.generalmessage", "Items have been added for this course.  Please remove all items from this view before declaring no eReserves."));
			addErrors(request, messages);
			return confirmBooks(mapping, form, request, response);
		}
	}
		return mapping.findForward("declarenopbooks");
	}
	public ActionForward noBooks(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		BookMenuForm bookMenuForm = (BookMenuForm) form;
		ActionMessages messages = new ActionMessages();
		CourseDAO dao = new CourseDAO();

		dao.updateBookListStatus("3", bookMenuForm.getCourseId(), bookMenuForm.getAcadyear(),bookMenuForm.getNetworkId(),bookMenuForm.getTypeOfBookList());
		if(bookMenuForm.getTypeOfBookList().equals("E")){
			updateAuditTrail(bookMenuForm, "No eReserves prescribed for this course");
		}else{
		updateAuditTrail(bookMenuForm, "No books prescribed for this course");
		}
	
			if(bookMenuForm.getTypeOfBookList().equals("E")){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
				        new ActionMessage("message.generalmessage", "Successful declaration of no eReserves. No authorization required."));
				addErrors(request, messages);
			}else{
			messages.add(ActionMessages.GLOBAL_MESSAGE,
			        new ActionMessage("message.generalmessage", "Successful declaration of no books. No authorization required."));
			addErrors(request, messages);	}				
		
		return confirmBooks(mapping, form, request, response);
	}
	public ActionForward addNewBook(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response){
			
			
			HttpSession session = request.getSession(true);
			String bookType="";
			BookMenuForm bookMenuForm = (BookMenuForm) form;
			String searchOption = " ";
			if(bookMenuForm.getTypeOfBookList().equalsIgnoreCase("E")){		
				
				bookType = (String)session.getAttribute("bookType");
				if(bookType == null){
					bookType = bookMenuForm.geteReserveType();
				}
				
				bookMenuForm.setSearchOption(bookType);
				searchOption = request.getParameter("searchOption");	
				
				if(searchOption == null)
				{
					searchOption = " ";
				}
			}
		if((request.getParameter("searchOption") != null && request.getParameter("searchOption").equalsIgnoreCase("edit")) || bookMenuForm.getSearchOption() == "edit")
		{	
			
			CourseDAO dao = new CourseDAO();			
			bookMenuForm.setBookDetails(dao.getBookDetailsInfo(bookMenuForm.getBookId()));	
			
			bookMenuForm.seteReserveType(bookMenuForm.getBookDetails().geteReserveType());
			//System.out.println(bookMenuForm.getBookDetails().geteReserveType());
			bookMenuForm.setSearchOption(bookMenuForm.getBookDetails().geteReserveType());	
			
			
			bookMenuForm.setTxtTitle(bookMenuForm.getBookDetails().getTxtTitle());
			bookMenuForm.setTxtAuthor(bookMenuForm.getBookDetails().getTxtAuthor());
			bookMenuForm.setTxtBookNote(bookMenuForm.getBookDetails().getTxtBookNote());
			
			if(bookMenuForm.getSearchOption() != null)
			{
				if(bookMenuForm.getSearchOption().equalsIgnoreCase("Journal") || bookMenuForm.getSearchOption().equalsIgnoreCase("Law Report") 
				|| bookMenuForm.getSearchOption().equalsIgnoreCase("Book Chapter"))
				{
					bookMenuForm.setCourseLang(" ");
				}
			}
			else
			{
				bookMenuForm.setCourseLang(bookMenuForm.getBookDetails().getCourseLang());
			}
			bookMenuForm.setTxtEdition(bookMenuForm.getBookDetails().getTxtEdition());
			bookMenuForm.setEbook_pages(bookMenuForm.getBookDetails().getEbook_pages());
			bookMenuForm.setEbookVolume(bookMenuForm.getBookDetails().getEbookVolume());
			bookMenuForm.setUrl(bookMenuForm.getBookDetails().getUrl());
			bookMenuForm.setMasterCopy(bookMenuForm.getBookDetails().getMasterCopy());
			bookMenuForm.setMasterCopyFormat(bookMenuForm.getBookDetails().getMasterCopyFormat());
			bookMenuForm.setAvailableAsEbook(bookMenuForm.getBookDetails().getAvailableAsEbook());
			bookMenuForm.setTxtOtherAuthor(bookMenuForm.getBookDetails().getOtherAuthor());
			bookMenuForm.setTxtISBN(bookMenuForm.getBookDetails().getTxtISBN());
			bookMenuForm.setTxtISBN1(bookMenuForm.getBookDetails().getTxtISBN1());
			bookMenuForm.setTxtISBN2(bookMenuForm.getBookDetails().getTxtISBN2());
			bookMenuForm.setTxtISBN3(bookMenuForm.getBookDetails().getTxtISBN3());
			bookMenuForm.setTxtYear(bookMenuForm.getBookDetails().getTxtYear());
			bookMenuForm.setTxtPublisher(bookMenuForm.getBookDetails().getTxtPublisher());
			bookMenuForm.setPublishStatus(bookMenuForm.getBookDetails().getPublishStatus());
			bookMenuForm.setNoteToLibrary(bookMenuForm.getBookDetails().getNoteToLibrary());
			return mapping.findForward("newbookadd");
		}
		
		if(bookMenuForm.getTypeOfBookList().equals("E")){
			if (bookMenuForm.getEreserveSearchOption() != null && bookMenuForm.getEreserveSearchOption().equals("searchform"))
			{
				bookMenuForm.setSearchOption(bookType);
				bookMenuForm.setTxtAuthor("");
				bookMenuForm.setTxtTitle("");
				return mapping.findForward("searchform");
			} 
			else if (bookMenuForm.getEreserveSearchOption() != null && bookMenuForm.getEreserveSearchOption().equals("addNew"))
			{
				bookMenuForm.setSearchOption(bookType);
				bookMenuForm.setTxtTitle("");
				bookMenuForm.setTxtAuthor("");
				bookMenuForm.setTxtBookNote("");
				bookMenuForm.setCourseLang("E");			
				bookMenuForm.setTxtEdition("");
				bookMenuForm.setTxtISBN("0000000000000");
				bookMenuForm.setTxtISBN1("0000000000000");
				bookMenuForm.setTxtISBN2("0000000000000");
				bookMenuForm.setTxtISBN3("0000000000000");
				bookMenuForm.setTxtYear("");
				bookMenuForm.setTxtPublisher("");
				bookMenuForm.setPublishStatus(0);
				bookMenuForm.setTxtOtherAuthor("");
				bookMenuForm.setAvailableAsEbook("unknown");
				bookMenuForm.setEbook_pages("");
				bookMenuForm.setEbookVolume("");
				bookMenuForm.setNoteToLibrary("");
				bookMenuForm.setUrl("");
				bookMenuForm.setMasterCopy("N");
				bookMenuForm.setMasterCopyFormat("Unknown");
				return mapping.findForward("newbookadd");
			}
		}else{
			if (bookMenuForm.getSearchOption() != null && bookMenuForm.getSearchOption().equals("searchform"))
			{
				bookMenuForm.setTxtAuthor("");
				bookMenuForm.setTxtTitle("");
				return mapping.findForward("searchform");
			}else if (bookMenuForm.getSearchOption() != null && bookMenuForm.getSearchOption().equalsIgnoreCase("addform")){

				bookMenuForm.setTxtBookNote("");
				bookMenuForm.setCourseLang("E");			
				bookMenuForm.setTxtEdition("");
				bookMenuForm.setTxtISBN("0000000000000");
				bookMenuForm.setTxtISBN1("0000000000000");
				bookMenuForm.setTxtISBN2("0000000000000");
				bookMenuForm.setTxtISBN3("0000000000000");
				bookMenuForm.setTxtYear("");
				bookMenuForm.setTxtPublisher("");
				bookMenuForm.setPublishStatus(0);
				bookMenuForm.setTxtOtherAuthor("");
				bookMenuForm.setAvailableAsEbook("unknown");
				bookMenuForm.setEbook_pages("");
				bookMenuForm.setEbookVolume("");
				bookMenuForm.setNoteToLibrary("");
				bookMenuForm.setUrl("");
				bookMenuForm.setMasterCopy("N");
				bookMenuForm.setMasterCopyFormat("Unknown");
				bookMenuForm.setSearchOption(bookType);
				return mapping.findForward("newbookadd");
			} 

		}
		
		 if(bookMenuForm.getSearchOption() != null && bookMenuForm.getSearchOption().equals("edit")){
			CourseDAO dao = new CourseDAO();			
			bookMenuForm.setBookDetails(dao.getBookDetailsInfo(bookMenuForm.getBookId()));	
			bookMenuForm.seteReserveType(bookMenuForm.getBookDetails().geteReserveType());
			bookMenuForm.setTxtTitle(bookMenuForm.getBookDetails().getTxtTitle());
			bookMenuForm.setTxtAuthor(bookMenuForm.getBookDetails().getTxtAuthor());
			bookMenuForm.setTxtBookNote(bookMenuForm.getBookDetails().getTxtBookNote());				
			bookMenuForm.setCourseLang(bookMenuForm.getBookDetails().getCourseLang());
			bookMenuForm.setTxtEdition(bookMenuForm.getBookDetails().getTxtEdition());
			bookMenuForm.setEbook_pages(bookMenuForm.getBookDetails().getEbook_pages());
			bookMenuForm.setEbookVolume(bookMenuForm.getBookDetails().getEbookVolume());
			bookMenuForm.setUrl(bookMenuForm.getBookDetails().getUrl());
			bookMenuForm.setMasterCopy(bookMenuForm.getBookDetails().getMasterCopy());
			bookMenuForm.setMasterCopyFormat(bookMenuForm.getBookDetails().getMasterCopyFormat());
			bookMenuForm.setAvailableAsEbook(bookMenuForm.getBookDetails().getAvailableAsEbook());
			bookMenuForm.setTxtOtherAuthor(bookMenuForm.getBookDetails().getOtherAuthor());
			bookMenuForm.setTxtISBN(bookMenuForm.getBookDetails().getTxtISBN());
			bookMenuForm.setTxtISBN1(bookMenuForm.getBookDetails().getTxtISBN1());
			bookMenuForm.setTxtISBN2(bookMenuForm.getBookDetails().getTxtISBN2());
			bookMenuForm.setTxtISBN3(bookMenuForm.getBookDetails().getTxtISBN3());
			bookMenuForm.setTxtYear(bookMenuForm.getBookDetails().getTxtYear());
			bookMenuForm.setTxtPublisher(bookMenuForm.getBookDetails().getTxtPublisher());
			bookMenuForm.setPublishStatus(bookMenuForm.getBookDetails().getPublishStatus());
			bookMenuForm.setNoteToLibrary(bookMenuForm.getBookDetails().getNoteToLibrary());
			return mapping.findForward("newbookadd");
			
		} else {
			bookMenuForm.setTxtTitle("");
			bookMenuForm.setTxtAuthor("");
			bookMenuForm.setTxtBookNote("");
			bookMenuForm.setCourseLang("E");			
			bookMenuForm.setTxtEdition("");
			bookMenuForm.setTxtISBN("0000000000000");
			bookMenuForm.setTxtISBN1("0000000000000");
			bookMenuForm.setTxtISBN2("0000000000000");
			bookMenuForm.setTxtISBN3("0000000000000");
			bookMenuForm.setTxtYear("");
			bookMenuForm.setTxtPublisher("");
			bookMenuForm.setPublishStatus(0);
			bookMenuForm.setTxtOtherAuthor("");
			bookMenuForm.setAvailableAsEbook("unknown");
			bookMenuForm.setEbook_pages("");
			bookMenuForm.setEbookVolume("");
			bookMenuForm.setNoteToLibrary("");
			bookMenuForm.setUrl("");
			bookMenuForm.setMasterCopy("N");
			bookMenuForm.setMasterCopyFormat("Unknown");
			return mapping.findForward("newbookadd");
		}
		 
	
	}
	public ActionForward saveBookDetails(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		
		String bookType="";
		HttpSession session = request.getSession(true);

		BookMenuForm bookMenuForm = (BookMenuForm) form;	

		
		if(bookMenuForm.getTypeOfBookList().equalsIgnoreCase("E"))
		{
			bookType = (String)session.getAttribute("bookType");
		}
		else
		{
			bookType = "notEreserves";
		}
		
		CourseDAO dao = new CourseDAO();
		ActionMessages messages = new ActionMessages();
		BookDetails bookDetails = new BookDetails();


		
		bookMenuForm.setTxtTitle(rtrim(ltrim(bookMenuForm.getTxtTitle())));
		bookMenuForm.setTxtAuthor(rtrim(ltrim(bookMenuForm.getTxtAuthor())));
		
		if(bookType.equals("Book Chapter")){
		bookMenuForm.setTxtEdition(rtrim(ltrim(bookMenuForm.getTxtEdition())));
		}
		if(!bookType.equals("Law Report")){
		bookMenuForm.setTxtYear(rtrim(ltrim(bookMenuForm.getTxtYear())));
		}
		if(!bookType.equals("Law Report")){
		bookMenuForm.setTxtPublisher(rtrim(ltrim(bookMenuForm.getTxtPublisher())));
		}
		if(!bookType.equals("notEreserves")){
		bookMenuForm.setEbook_pages(rtrim(ltrim(bookMenuForm.getEbook_pages())));
		}

		 
			bookMenuForm.setSearchOption(bookType);
			if(bookMenuForm.getTypeOfBookList().equals("P")){
				if(bookMenuForm.getTxtTitle().length() == 0){
					messages.add(ActionMessages.GLOBAL_MESSAGE,
					        new ActionMessage("message.generalmessage", "Title cannot be empty, please enter the Title."));
					addErrors(request, messages);
					return addNewBook(mapping, form, request, response);
				} else if (bookMenuForm.getTxtAuthor().length() == 0){
					messages.add(ActionMessages.GLOBAL_MESSAGE,
					        new ActionMessage("message.generalmessage", "Author cannot be empty, please enter the Author."));
					addErrors(request, messages);
					return addNewBook(mapping, form, request, response);				
				} else if (bookMenuForm.getTxtEdition().length() == 0){
					messages.add(ActionMessages.GLOBAL_MESSAGE,
					        new ActionMessage("message.generalmessage", "Edition cannot be empty, please enter the Edition"));
					addErrors(request, messages);
					return addNewBook(mapping, form, request, response);				
				} else if (bookMenuForm.getTxtYear().length() == 0){
					messages.add(ActionMessages.GLOBAL_MESSAGE,
					        new ActionMessage("message.generalmessage", "Year cannot be empty, please enter the Year"));
					addErrors(request, messages);
					return addNewBook(mapping, form, request, response);
				} else if (isInteger(bookMenuForm.getTxtYear()) == false){
					messages.add(ActionMessages.GLOBAL_MESSAGE,
					        new ActionMessage("message.generalmessage", "Year must be a number."));
					addErrors(request, messages);
					return addNewBook(mapping, form, request, response);
				} else if (bookMenuForm.getTxtYear().length() < 4){
					messages.add(ActionMessages.GLOBAL_MESSAGE,
					        new ActionMessage("message.generalmessage", "Year cannot be less than four characters, please enter the Year"));
					addErrors(request, messages);
					return addNewBook(mapping, form, request, response);
				}
				 else if (bookMenuForm.getTxtBookNote().length()>300){
						messages.add(ActionMessages.GLOBAL_MESSAGE,
						        new ActionMessage("message.generalmessage", "Book note should not exceed 300 characters, including spaces. Please shorten your text."));
						addErrors(request, messages);
						return addNewBook(mapping, form, request, response);	
				 } else if (bookMenuForm.getTxtPublisher().length() == 0){
						messages.add(ActionMessages.GLOBAL_MESSAGE,
						        new ActionMessage("message.generalmessage", "Publisher cannot be empty, please enter the Publisher"));
						addErrors(request, messages);
						return addNewBook(mapping, form, request, response);	
				} else if (bookMenuForm.getTxtISBN().length() == 0){
					messages.add(ActionMessages.GLOBAL_MESSAGE,
					        new ActionMessage("message.generalmessage", "ISBN cannot be empty, please enter an ISBN number or fill field with 13 zeros."));
					addErrors(request, messages);
					return addNewBook(mapping, form, request, response);	
			   } else if (bookMenuForm.getTxtISBN().length() < 10){
					messages.add(ActionMessages.GLOBAL_MESSAGE,
					        new ActionMessage("message.generalmessage", "ISBN cannot be less 10 characters, please enter valid ISBN number or fill field with 13 zeros."));
					addErrors(request, messages);
					return addNewBook(mapping, form, request, response);	
			   } else if (bookMenuForm.getTxtISBN().length() > 13){
					messages.add(ActionMessages.GLOBAL_MESSAGE,
					        new ActionMessage("message.generalmessage", "ISBN cannot be morethan 13 characters, please enter valid ISBN number or fill field with 13 zeros."));
					addErrors(request, messages);
					return addNewBook(mapping, form, request, response);	
			   }else if (isDigit(bookMenuForm.getTxtISBN()) == true){
					messages.add(ActionMessages.GLOBAL_MESSAGE,
					        new ActionMessage("message.generalmessage", "ISBN should only be a combination of numbers, please enter valid ISBN number or fill field with 13 zeros."));
					addErrors(request, messages);
					return addNewBook(mapping, form, request, response);	
			   }else if (bookMenuForm.getPublishStatus().intValue() == 0){
					messages.add(ActionMessages.GLOBAL_MESSAGE,
					        new ActionMessage("message.generalmessage", "The Book is Published option is set to No.  Only published books may be prescribed. Refer to Section 4.7 of the relevant Policy."));
					addErrors(request, messages);
					return addNewBook(mapping, form, request, response);				
				 }else {
					bookDetails.setTxtTitle(bookMenuForm.getTxtTitle());
					bookDetails.setTxtAuthor(bookMenuForm.getTxtAuthor());
					bookDetails.setTxtBookNote(bookMenuForm.getTxtBookNote());
					bookDetails.setTxtEdition(bookMenuForm.getTxtEdition());
					bookDetails.setTxtISBN(bookMenuForm.getTxtISBN());
					bookDetails.setTxtISBN1("");
					bookDetails.setTxtISBN2("");
					bookDetails.setTxtISBN3("");
					bookDetails.setTxtPublisher(bookMenuForm.getTxtPublisher());
					bookDetails.setTxtYear(bookMenuForm.getTxtYear());
					bookDetails.setPublishStatus(bookMenuForm.getPublishStatus());
					bookDetails.setTypeOfBookList(bookMenuForm.getTypeOfBookList());
					bookDetails.setCourse(bookMenuForm.getCourseId());
					bookDetails.setAcadYear(new Integer(bookMenuForm.getAcadyear()));
					bookDetails.setCourseLang(bookMenuForm.getCourseLang());
					bookDetails.setNetworkID(bookMenuForm.getNetworkId());
					bookDetails.setOtherAuthor(bookMenuForm.getTxtOtherAuthor());
					bookDetails.setNoteToLibrary("");
					bookDetails.setAvailableAsEbook("");
					bookDetails.setEbook_pages("");
					bookDetails.setEbookVolume("");
					bookDetails.setUrl("");
					bookDetails.setMasterCopy("");
					bookDetails.setMasterCopyFormat("");
					bookDetails.setBookId(dao.insertBook(bookDetails,"0").toString());
					updateAuditTrail(bookMenuForm, "Added booknr " +bookDetails.getBookId());
					//Confirm field in crsbs1 table:
					//if it is 1, can edit the book else 
				}
			}else if(bookMenuForm.getTypeOfBookList().equals("R")){
				if(bookMenuForm.getTxtTitle().length() == 0){
					messages.add(ActionMessages.GLOBAL_MESSAGE,
					        new ActionMessage("message.generalmessage", "Title cannot be empty, please enter the Title."));
					addErrors(request, messages);
					return addNewBook(mapping, form, request, response);
				} else if (bookMenuForm.getTxtAuthor().length() == 0){
					messages.add(ActionMessages.GLOBAL_MESSAGE,
					        new ActionMessage("message.generalmessage", "Author cannot be empty, please enter the Author."));
					addErrors(request, messages);
					return addNewBook(mapping, form, request, response);				
				} else if (bookMenuForm.getTxtEdition().length() == 0){
					messages.add(ActionMessages.GLOBAL_MESSAGE,
					        new ActionMessage("message.generalmessage", "Edition cannot be empty, please enter the Edition"));
					addErrors(request, messages);
					return addNewBook(mapping, form, request, response);				
				} else if (bookMenuForm.getTxtYear().length() == 0){
					messages.add(ActionMessages.GLOBAL_MESSAGE,
					        new ActionMessage("message.generalmessage", "Year cannot be empty, please enter the Year"));
					addErrors(request, messages);
					return addNewBook(mapping, form, request, response);
				} else if (isInteger(bookMenuForm.getTxtYear()) == false){
					messages.add(ActionMessages.GLOBAL_MESSAGE,
					        new ActionMessage("message.generalmessage", "Year must be a number."));
					addErrors(request, messages);
					return addNewBook(mapping, form, request, response);
				} else if (bookMenuForm.getTxtYear().length() < 4){
					messages.add(ActionMessages.GLOBAL_MESSAGE,
					        new ActionMessage("message.generalmessage", "Year cannot be less than four characters, please enter the Year"));
					addErrors(request, messages);
					return addNewBook(mapping, form, request, response);
				}
				else if (bookMenuForm.getTxtPublisher().length() == 0){
					messages.add(ActionMessages.GLOBAL_MESSAGE,
					        new ActionMessage("message.generalmessage", "Publisher cannot be empty, please enter the Publisher"));
					addErrors(request, messages);
					return addNewBook(mapping, form, request, response);				
				 }else if (bookMenuForm.getTxtISBN().length() == 0){
						messages.add(ActionMessages.GLOBAL_MESSAGE,
						        new ActionMessage("message.generalmessage", "ISBN cannot be empty, please enter an ISBN number or fill field with 13 zeros."));
						addErrors(request, messages);
						return addNewBook(mapping, form, request, response);	
				   } else if (bookMenuForm.getTxtISBN().length() < 10){
						messages.add(ActionMessages.GLOBAL_MESSAGE,
						        new ActionMessage("message.generalmessage", "ISBN cannot be less 10 characters, please enter valid ISBN number or fill field with 13 zeros."));
						addErrors(request, messages);
						return addNewBook(mapping, form, request, response);	
				   }else if (bookMenuForm.getTxtISBN().length() > 13){
						messages.add(ActionMessages.GLOBAL_MESSAGE,
						        new ActionMessage("message.generalmessage", "ISBN cannot be morethan 13 characters, please enter valid ISBN number or fill field with 13 zeros."));
						addErrors(request, messages);
						return addNewBook(mapping, form, request, response);	
				   }else if (isDigit(bookMenuForm.getTxtISBN()) == true){
						messages.add(ActionMessages.GLOBAL_MESSAGE,
						        new ActionMessage("message.generalmessage", "ISBN should only be a combination of numbers, please enter valid ISBN number or fill field with 13 zeros."));
						addErrors(request, messages);
						return addNewBook(mapping, form, request, response);	
				   }else if (bookMenuForm.getPublishStatus().intValue() == 0){
					messages.add(ActionMessages.GLOBAL_MESSAGE,
					        new ActionMessage("message.generalmessage", "The Book is Published option is set to No.  Only published books may be prescribed. Refer to Section 5.3 of the relevant Policy"));
					addErrors(request, messages);
					return addNewBook(mapping, form, request, response);
				}else {
					bookDetails.setTxtTitle(bookMenuForm.getTxtTitle());
					bookDetails.setTxtAuthor(bookMenuForm.getTxtAuthor());
					bookDetails.setTxtBookNote(bookMenuForm.getTxtBookNote());
					bookDetails.setTxtEdition(bookMenuForm.getTxtEdition());
					bookDetails.setTxtISBN(bookMenuForm.getTxtISBN());
					bookDetails.setTxtISBN1(bookMenuForm.getTxtISBN1());
					bookDetails.setTxtISBN2(bookMenuForm.getTxtISBN2());
					bookDetails.setTxtISBN3(bookMenuForm.getTxtISBN3());
					bookDetails.setTxtPublisher(bookMenuForm.getTxtPublisher());
					bookDetails.setTxtYear(bookMenuForm.getTxtYear());
					bookDetails.setPublishStatus(bookMenuForm.getPublishStatus());
					bookDetails.setCourse(bookMenuForm.getCourseId());
					bookDetails.setAcadYear(new Integer(bookMenuForm.getAcadyear()));
					bookDetails.setTypeOfBookList(bookMenuForm.getTypeOfBookList());
					bookDetails.setCourseLang(bookMenuForm.getCourseLang());
					bookDetails.setNetworkID(bookMenuForm.getNetworkId());
					bookDetails.setNoteToLibrary(bookMenuForm.getNoteToLibrary());
					bookDetails.setAvailableAsEbook(bookMenuForm.getAvailableAsEbook());
					bookDetails.setOtherAuthor(bookMenuForm.getTxtOtherAuthor());
					
					bookDetails.setEbook_pages("");
					bookDetails.setEbookVolume("");
					bookDetails.setUrl("");
					bookDetails.setMasterCopy("");
					bookDetails.setMasterCopyFormat("");
					
					bookDetails.setBookId(dao.insertBook(bookDetails,"0").toString());
					updateAuditTrail(bookMenuForm, "Added booknr " +bookDetails.getBookId());
				}
			}else if(bookMenuForm.getTypeOfBookList().equals("E")){
				
				if(!bookType.equalsIgnoreCase("Book Chapter") && bookMenuForm.getTxtTitle().length() == 0)
				{
					messages.add(ActionMessages.GLOBAL_MESSAGE,
					        new ActionMessage("message.generalmessage", "Title cannot be empty, please enter the Title."));
					addErrors(request, messages);
					return addNewBook(mapping, form, request, response);
				}
				else if(!bookType.equalsIgnoreCase("Law Report") && !bookType.equalsIgnoreCase("Book Chapter") && bookMenuForm.getTxtAuthor().length() == 0)
				{
				
						messages.add(ActionMessages.GLOBAL_MESSAGE,
						        new ActionMessage("message.generalmessage", "Author cannot be empty, please enter the Author."));
						addErrors(request, messages);
						return addNewBook(mapping, form, request, response);
	
				}
				else if(bookMenuForm.geteReserveType().equalsIgnoreCase("Book Chapter") && bookMenuForm.getTxtPublisher().length() == 0)
				{				
			
					messages.add(ActionMessages.GLOBAL_MESSAGE,
					        new ActionMessage("message.generalmessage", "Publication cannot be empty, please enter the Publication details"));
					addErrors(request, messages);
					return addNewBook(mapping, form, request, response);
				  
				}
				else if(bookType.equalsIgnoreCase("Law Report") && bookMenuForm.getEbookVolume().length() == 0)
				{
					messages.add(ActionMessages.GLOBAL_MESSAGE,
					        new ActionMessage("message.generalmessage", "Case number cannot be empty, please enter the case number."));
					addErrors(request, messages);
					return addNewBook(mapping, form, request, response);
				} 
				else if(!bookType.equalsIgnoreCase("Law Report") && bookMenuForm.getEbook_pages().length() == 0)
				{
					messages.add(ActionMessages.GLOBAL_MESSAGE,
					        new ActionMessage("message.generalmessage", "Page numbers cannot be empty, please enter the page numbers."));
					addErrors(request, messages);
					return addNewBook(mapping, form, request, response);
				} 
				else if(!bookType.equalsIgnoreCase("Law Report") && bookMenuForm.getTxtYear().length() == 0 )
				{
				
						messages.add(ActionMessages.GLOBAL_MESSAGE,
						        new ActionMessage("message.generalmessage", "Year cannot be empty, please enter the Year"));
						addErrors(request, messages);
						return addNewBook(mapping, form, request, response);
				
				} 
				else if(!bookType.equalsIgnoreCase("Law Report") && !isInteger(bookMenuForm.getTxtYear()))
				{
				
						messages.add(ActionMessages.GLOBAL_MESSAGE,
						        new ActionMessage("message.generalmessage", "Year must be a number."));
						addErrors(request, messages);
						return addNewBook(mapping, form, request, response);
				} 
				else if(!bookType.equalsIgnoreCase("Law Report") && bookMenuForm.getTxtYear().length() < 4)
				{				
				
						messages.add(ActionMessages.GLOBAL_MESSAGE,
						        new ActionMessage("message.generalmessage", "Year cannot be less than four characters, please enter the Year"));
						addErrors(request, messages);
						return addNewBook(mapping, form, request, response);
				 
				}
				else if(bookType.equalsIgnoreCase("Journal") && bookMenuForm.getTxtPublisher().length() == 0)
				{				
			
					messages.add(ActionMessages.GLOBAL_MESSAGE,
					        new ActionMessage("message.generalmessage", "Publication cannot be empty, please enter the Publication details"));
					addErrors(request, messages);
					return addNewBook(mapping, form, request, response);
				  
				}
				else if (bookMenuForm.getNoteToLibrary().length() > 255)
				{
					messages.add(ActionMessages.GLOBAL_MESSAGE,
					        new ActionMessage("message.generalmessage", "Note to the library can not exceed 255 characters. Please shorten your note"));
					addErrors(request, messages);
					return addNewBook(mapping, form, request, response);
				}
				else 
				{
					bookDetails.setTxtTitle(bookMenuForm.getTxtTitle());
					bookDetails.setTxtAuthor(bookMenuForm.getTxtAuthor());
					bookDetails.setTxtBookNote("");
					
					if(bookType.equalsIgnoreCase("Book Chapter"))
					{
						bookDetails.setTxtEdition(bookMenuForm.getTxtEdition());
					}
					else
					{
						bookDetails.setTxtEdition("");
					}

					bookDetails.setTxtISBN("");
					bookDetails.setTxtISBN1("");
					bookDetails.setTxtISBN2("");
					bookDetails.setTxtISBN3("");
					bookDetails.setTxtPublisher(bookMenuForm.getTxtPublisher());
					bookDetails.setTxtYear(bookMenuForm.getTxtYear());
					bookDetails.setPublishStatus(3);
					bookDetails.setAvailableAsEbook("");
					bookDetails.setCourse(bookMenuForm.getCourseId());
					bookDetails.setAcadYear(new Integer(bookMenuForm.getAcadyear()));
					bookDetails.setCourseLang("");
					bookDetails.setNetworkID(bookMenuForm.getNetworkId());
					bookDetails.setTypeOfBookList(bookMenuForm.getTypeOfBookList());
					bookDetails.setEbook_pages(bookMenuForm.getEbook_pages());
					bookDetails.setEbookVolume(bookMenuForm.getEbookVolume());
					bookDetails.setUrl(bookMenuForm.getUrl());
					bookDetails.setMasterCopy(bookMenuForm.getMasterCopy());
					bookDetails.setMasterCopyFormat(bookMenuForm.getMasterCopyFormat());
					bookDetails.setNoteToLibrary(bookMenuForm.getNoteToLibrary());
					bookDetails.setOtherAuthor(bookMenuForm.getTxtOtherAuthor());

					
					bookDetails.setBookId(dao.insertBook(bookDetails,bookType).toString());
					updateAuditTrail(bookMenuForm, "Added booknr " +bookDetails.getBookId());
				}			
			}
			
		return confirmBooks(mapping, form, request, response);		
	}
	public ActionForward updateBookDetails(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response)throws Exception{
		HttpSession session = request.getSession();
		BookMenuForm bookMenuForm = (BookMenuForm) form;		
		CourseDAO dao = new CourseDAO();		
		BookDetails bookDetails = new BookDetails();
		ActionMessages messages = new ActionMessages();
		bookMenuForm.setSearchOption("edit");
		session.setAttribute("bookType", "edit");
		
		bookMenuForm.seteReserveType(bookMenuForm.getBookDetails().geteReserveType());
		
		if(bookMenuForm.getTypeOfBookList().equals("P")){
			
			if(bookMenuForm.getCourseLang() == null){
				bookMenuForm.setCourseLang("");
			}
			
			if(ltrim(rtrim(bookMenuForm.getTxtTitle())).length() == 0){
				
				messages.add(ActionMessages.GLOBAL_MESSAGE,
				        new ActionMessage("message.generalmessage", "Title cannot be empty, please enter the Title."));
				addErrors(request, messages);
				return addNewBook(mapping, form, request, response);
			} else if (ltrim(rtrim(bookMenuForm.getTxtAuthor())).length() == 0){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
				        new ActionMessage("message.generalmessage", "Author cannot be empty, please enter the Author."));
				addErrors(request, messages);
				return addNewBook(mapping, form, request, response);				
			} else if (ltrim(rtrim(bookMenuForm.getTxtEdition())).length() == 0){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
				        new ActionMessage("message.generalmessage", "Edition cannot be empty, please enter the Edition"));
				addErrors(request, messages);
				return addNewBook(mapping, form, request, response);				
			} else if (bookMenuForm.getTxtYear().length() == 0){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
				        new ActionMessage("message.generalmessage", "Year cannot be empty, please enter the Year"));
				addErrors(request, messages);
				return addNewBook(mapping, form, request, response);
			} else if (!isInteger(bookMenuForm.getTxtYear())){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
				        new ActionMessage("message.generalmessage", "Year must be a number."));
				addErrors(request, messages);
				return addNewBook(mapping, form, request, response);
			} else if (bookMenuForm.getTxtYear().length() < 4){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
				        new ActionMessage("message.generalmessage", "Year cannot be less than four characters, please enter the Year"));
				addErrors(request, messages);
				return addNewBook(mapping, form, request,response);
			}
			 else if (bookMenuForm.getTxtBookNote().length()>300){
					messages.add(ActionMessages.GLOBAL_MESSAGE,
					        new ActionMessage("message.generalmessage", "Book note should not exceed 300 characters, including spaces. Please shorten your text."));
					addErrors(request, messages);
					return addNewBook(mapping, form, request, response);	
			 } else if (ltrim(rtrim(bookMenuForm.getTxtPublisher())).length() == 0){
					messages.add(ActionMessages.GLOBAL_MESSAGE,
					        new ActionMessage("message.generalmessage", "Publisher cannot be empty, please enter the Publisher"));
					addErrors(request, messages);
					return addNewBook(mapping, form, request, response);	
			} else if (bookMenuForm.getTxtISBN().length() == 0){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
				        new ActionMessage("message.generalmessage", "ISBN cannot be empty, please enter an ISBN number or fill field with 13 zeros."));
				addErrors(request, messages);
				return addNewBook(mapping, form, request, response);	
		   } else if (bookMenuForm.getTxtISBN().length() < 10){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
				        new ActionMessage("message.generalmessage", "ISBN cannot be less 10 characters, please enter valid ISBN number or fill field with 13 zeros."));
				addErrors(request, messages);
				return addNewBook(mapping, form, request, response);	
		   }else if (bookMenuForm.getTxtISBN().length() > 13){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
				        new ActionMessage("message.generalmessage", "ISBN cannot be morethan 13 characters, please enter valid ISBN number or fill field with 13 zeros."));
				addErrors(request, messages);
				return addNewBook(mapping, form, request, response);	
		   }else if (isDigit(bookMenuForm.getTxtISBN()) == true){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
				        new ActionMessage("message.generalmessage", "ISBN should only be a combination of numbers, please enter valid ISBN number or fill field with 13 zeros."));
				addErrors(request, messages);
				return addNewBook(mapping, form, request, response);	
		   }else if (bookMenuForm.getPublishStatus().intValue() == 0){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
				        new ActionMessage("message.generalmessage", "The Book is Published option is set to No.  Only published books may be prescribed. Refer to Section 4.7 of the relevant Policy."));
				addErrors(request, messages);

				return addNewBook(mapping, form, request, response);				
		   }else {
				    bookDetails.setBookId(bookMenuForm.getBookId());
				    bookDetails.setTypeOfBookList(bookMenuForm.getTypeOfBookList());
					bookDetails.setTxtTitle(bookMenuForm.getTxtTitle());
					bookDetails.setTxtAuthor(bookMenuForm.getTxtAuthor());
					bookDetails.setTxtBookNote(bookMenuForm.getTxtBookNote());
					bookDetails.setTxtEdition(bookMenuForm.getTxtEdition());
					bookDetails.setTxtISBN(bookMenuForm.getTxtISBN());
					bookDetails.setTxtISBN1("");
					bookDetails.setTxtISBN2("");
					bookDetails.setTxtISBN3("");
					bookDetails.setTxtPublisher(bookMenuForm.getTxtPublisher());
					bookDetails.setTxtYear(bookMenuForm.getTxtYear());
					bookDetails.setPublishStatus(bookMenuForm.getPublishStatus());
					bookDetails.setCourse(bookMenuForm.getCourseId());
					bookDetails.setCourseLang(bookMenuForm.getCourseLang());
					bookDetails.setAcadYear(new Integer(bookMenuForm.getAcadyear()));
					bookDetails.setOtherAuthor(bookMenuForm.getTxtOtherAuthor());
					bookDetails.setNoteToLibrary("");
					bookDetails.setAvailableAsEbook("");
					bookDetails.setEbook_pages("");
					bookDetails.setEbookVolume("");
					bookDetails.setUrl("");
					bookDetails.setMasterCopy("");
					bookDetails.setMasterCopyFormat("");
					int booknr = dao.updateBook(bookDetails);
					updateAuditTrail(bookMenuForm, "Updated P booknr " +bookDetails.getBookId()+" Add booknr "+booknr);
				
			}
		}else if(bookMenuForm.getTypeOfBookList().equals("R")){
			
			if(bookMenuForm.getCourseLang() == null){
				bookMenuForm.setCourseLang("");
			}
			if(ltrim(rtrim(bookMenuForm.getTxtTitle())).length() == 0){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
				        new ActionMessage("message.generalmessage", "Title cannot be empty, please enter the Title."));
				addErrors(request, messages);
				return addNewBook(mapping, form, request, response);
			} else if (ltrim(rtrim(bookMenuForm.getTxtAuthor())).length() == 0){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
				        new ActionMessage("message.generalmessage", "Author cannot be empty, please enter the Author."));
				addErrors(request, messages);
				return addNewBook(mapping, form, request, response);				
			} else if (ltrim(rtrim(bookMenuForm.getTxtEdition())).length() == 0){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
				        new ActionMessage("message.generalmessage", "Edition cannot be empty, please enter the Edition"));
				addErrors(request, messages);
				return addNewBook(mapping, form, request, response);				
			} else if (bookMenuForm.getTxtYear().length() == 0){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
				        new ActionMessage("message.generalmessage", "Year cannot be empty, please enter the Year"));
				addErrors(request, messages);
				return addNewBook(mapping, form, request, response);
			} else if (!isInteger(bookMenuForm.getTxtYear())){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
				        new ActionMessage("message.generalmessage", "Year must be a number."));
				addErrors(request, messages);
				return addNewBook(mapping, form, request, response);
			} else if (bookMenuForm.getTxtYear().length() < 4){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
				        new ActionMessage("message.generalmessage", "Year cannot be less than four characters, please enter the Year"));
				addErrors(request, messages);
				return addNewBook(mapping, form, request, response);
			}
			else if (ltrim(rtrim(bookMenuForm.getTxtPublisher())).length() == 0){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
				        new ActionMessage("message.generalmessage", "Publisher cannot be empty, please enter the Publisher"));
				addErrors(request, messages);
				return addNewBook(mapping, form, request, response);				
			 }else if (bookMenuForm.getTxtISBN().length() == 0){
					messages.add(ActionMessages.GLOBAL_MESSAGE,
					        new ActionMessage("message.generalmessage", "ISBN cannot be empty, please enter an ISBN number or fill field with 13 zeros."));
					addErrors(request, messages);
					return addNewBook(mapping, form, request, response);	
			   } else if (bookMenuForm.getTxtISBN().length() < 10){
					messages.add(ActionMessages.GLOBAL_MESSAGE,
					        new ActionMessage("message.generalmessage", "ISBN cannot be less 10 characters, please enter valid ISBN number or fill field with 13 zeros."));
					addErrors(request, messages);
					return addNewBook(mapping, form, request, response);	
			   }else if (bookMenuForm.getTxtISBN().length() > 13){
					messages.add(ActionMessages.GLOBAL_MESSAGE,
					        new ActionMessage("message.generalmessage", "ISBN cannot be morethan 13 characters, please enter valid ISBN number or fill field with 13 zeros."));
					addErrors(request, messages);
					return addNewBook(mapping, form, request, response);	
			   }else if (isDigit(bookMenuForm.getTxtISBN()) == true){
					messages.add(ActionMessages.GLOBAL_MESSAGE,
					        new ActionMessage("message.generalmessage", "ISBN should only be a combination of numbers, please enter valid ISBN number or fill field with 13 zeros."));
					addErrors(request, messages);
					return addNewBook(mapping, form, request, response);	
			   }else if (bookMenuForm.getPublishStatus().intValue() == 0){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
				        new ActionMessage("message.generalmessage", "The Book is Published option is set to No.  Only published books may be prescribed. Refer to Section 5.3 of the relevant Policy."));
				addErrors(request, messages);
				return addNewBook(mapping, form, request, response);
			}else {
				bookDetails.setBookId(bookMenuForm.getBookId());
				bookDetails.setTypeOfBookList(bookMenuForm.getTypeOfBookList());
				bookDetails.setTxtTitle(bookMenuForm.getTxtTitle());
				bookDetails.setTxtAuthor(bookMenuForm.getTxtAuthor());
				bookDetails.setTxtBookNote("");
				bookDetails.setTxtEdition(bookMenuForm.getTxtEdition());
				bookDetails.setTxtISBN(bookMenuForm.getTxtISBN());
				bookDetails.setTxtISBN1(bookMenuForm.getTxtISBN1());
				bookDetails.setTxtISBN2(bookMenuForm.getTxtISBN2());
				bookDetails.setTxtISBN3(bookMenuForm.getTxtISBN3());
				bookDetails.setTxtPublisher(bookMenuForm.getTxtPublisher());
				bookDetails.setTxtYear(bookMenuForm.getTxtYear());
				bookDetails.setPublishStatus(0);
				bookDetails.setCourse(bookMenuForm.getCourseId());
				bookDetails.setCourseLang(bookMenuForm.getCourseLang());
				bookDetails.setAcadYear(new Integer(bookMenuForm.getAcadyear()));
				bookDetails.setNoteToLibrary(bookMenuForm.getNoteToLibrary());
				bookDetails.setAvailableAsEbook(bookMenuForm.getAvailableAsEbook());
				bookDetails.setOtherAuthor(bookMenuForm.getTxtOtherAuthor());
				bookDetails.setEbook_pages("");
				bookDetails.setEbookVolume("");
				bookDetails.setUrl("");
				bookDetails.setMasterCopy("");
				bookDetails.setMasterCopyFormat("");
				int booknr = dao.updateBook(bookDetails);
				updateAuditTrail(bookMenuForm, "Updated R booknr " +bookDetails.getBookId()+" Add booknr "+booknr);
			}
		}else if(bookMenuForm.getTypeOfBookList().equals("E")){
			
			if(!bookMenuForm.geteReserveType().equalsIgnoreCase("Book Chapter") && bookMenuForm.getTxtTitle().length() == 0)
			{
				messages.add(ActionMessages.GLOBAL_MESSAGE,
				        new ActionMessage("message.generalmessage", "Title cannot be empty, please enter the Title." ));
				addErrors(request, messages);
				bookMenuForm.setSearchOption("edit");
				return addNewBook(mapping, form, request, response);
			}
			else if(!bookMenuForm.geteReserveType().equalsIgnoreCase("Law Report") && !bookMenuForm.geteReserveType().equalsIgnoreCase("Book Chapter")  && bookMenuForm.getTxtAuthor().length() == 0)
			{
			
					messages.add(ActionMessages.GLOBAL_MESSAGE,
					        new ActionMessage("message.generalmessage", "Author cannot be empty, please enter the Author."));
					addErrors(request, messages);
					return addNewBook(mapping, form, request, response);

			}
			else if(bookMenuForm.geteReserveType().equalsIgnoreCase("Law Report") && bookMenuForm.getEbookVolume().length() == 0)
			{
				messages.add(ActionMessages.GLOBAL_MESSAGE,
				        new ActionMessage("message.generalmessage", "Case number cannot be empty, please enter the case number."));
				addErrors(request, messages);
				return addNewBook(mapping, form, request, response);
			}
			else if(bookMenuForm.geteReserveType().equalsIgnoreCase("Book Chapter") && bookMenuForm.getTxtPublisher().length() == 0)
			{				
		
				messages.add(ActionMessages.GLOBAL_MESSAGE,
				        new ActionMessage("message.generalmessage", "Publication cannot be empty, please enter the Publication details"));
				addErrors(request, messages);
				return addNewBook(mapping, form, request, response);
			  
			}
			else if(!bookMenuForm.geteReserveType().equalsIgnoreCase("Law Report") && bookMenuForm.getEbook_pages().length() == 0)
			{
				messages.add(ActionMessages.GLOBAL_MESSAGE,
				        new ActionMessage("message.generalmessage", "Page numbers cannot be empty, please enter the page numbers."));
				addErrors(request, messages);
				return addNewBook(mapping, form, request, response);
			} 
			else if(!bookMenuForm.geteReserveType().equalsIgnoreCase("Law Report") && bookMenuForm.getTxtYear().length() == 0 )
			{
			
					messages.add(ActionMessages.GLOBAL_MESSAGE,
					        new ActionMessage("message.generalmessage", "Year cannot be empty, please enter the Year"));
					addErrors(request, messages);
					return addNewBook(mapping, form, request, response);
			
			} 
			else if(!bookMenuForm.geteReserveType().equalsIgnoreCase("Law Report") && isInteger(bookMenuForm.getTxtYear()) == false)
			{
					messages.add(ActionMessages.GLOBAL_MESSAGE,
					        new ActionMessage("message.generalmessage", "Year must be a number."));
					addErrors(request, messages);
					return addNewBook(mapping, form, request, response);
			} 
			else if(!bookMenuForm.geteReserveType().equalsIgnoreCase("Law Report") && bookMenuForm.getTxtYear().length() < 4)
			{				
			
					messages.add(ActionMessages.GLOBAL_MESSAGE,
					        new ActionMessage("message.generalmessage", "Year cannot be less than four characters, please enter the Year"));
					addErrors(request, messages);
					return addNewBook(mapping, form, request, response);
			 
			}
			else if(bookMenuForm.geteReserveType().equalsIgnoreCase("Journal") && bookMenuForm.getTxtPublisher().length() == 0)
			{				
		
				messages.add(ActionMessages.GLOBAL_MESSAGE,
				        new ActionMessage("message.generalmessage", "Publication cannot be empty, please enter the Publication details"));
				addErrors(request, messages);
				return addNewBook(mapping, form, request, response);
			  
			}
			else if (bookMenuForm.getNoteToLibrary().length() > 255)
			{
				messages.add(ActionMessages.GLOBAL_MESSAGE,
				        new ActionMessage("message.generalmessage", "Note to the library can not exceed 255 characters. Please shorten your note"));
				addErrors(request, messages);
				return addNewBook(mapping, form, request, response);
			}else {
				bookDetails.setBookId(bookMenuForm.getBookId());
				bookDetails.setTypeOfBookList(bookMenuForm.getTypeOfBookList());
				bookDetails.setTxtTitle(bookMenuForm.getTxtTitle());
				bookDetails.setTxtAuthor(bookMenuForm.getTxtAuthor());
				bookDetails.setTxtBookNote("");
				bookDetails.setTxtEdition("");
				bookDetails.setTxtISBN("");
				bookDetails.setTxtISBN1("");
				bookDetails.setTxtISBN2("");
				bookDetails.setTxtISBN3("");
				bookDetails.setAvailableAsEbook("");
				bookDetails.setTxtPublisher(bookMenuForm.getTxtPublisher());
				bookDetails.setTxtYear(bookMenuForm.getTxtYear());
				bookDetails.setPublishStatus(3);
				bookDetails.setCourse(bookMenuForm.getCourseId());
				bookDetails.setCourseLang(bookMenuForm.getCourseLang());
				bookDetails.setAcadYear(new Integer(bookMenuForm.getAcadyear()));
				bookDetails.setEbook_pages(bookMenuForm.getEbook_pages());
				bookDetails.setEbookVolume(bookMenuForm.getEbookVolume());
				bookDetails.setUrl(bookMenuForm.getUrl());
				bookDetails.setMasterCopy(bookMenuForm.getMasterCopy());
				bookDetails.setMasterCopyFormat(bookMenuForm.getMasterCopyFormat());
				bookDetails.setNoteToLibrary(bookMenuForm.getNoteToLibrary());
				bookDetails.setOtherAuthor(bookMenuForm.getTxtOtherAuthor());
				bookDetails.seteReserveType(bookMenuForm.geteReserveType());
				
				int booknr = dao.updateBook(bookDetails);
				updateAuditTrail(bookMenuForm, "Updated E booknr " +bookDetails.getBookId()+" Add booknr "+booknr);
			}			
		}
		
		return confirmBooks(mapping, form, request, response);
	}
	
	public ActionForward confirmBookList(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response)throws Exception{
		    BookMenuForm bookMenuForm = (BookMenuForm) form;		
		CourseDAO dao = new CourseDAO();
		
		dao.updateBookListStatus("1", bookMenuForm.getCourseId(), bookMenuForm.getAcadyear(),bookMenuForm.getNetworkId(),bookMenuForm.getTypeOfBookList());
		updateAuditTrail(bookMenuForm, "Book list confirmed");
		
		return confirmBooks(mapping, form, request, response);
	}
	public ActionForward unConfirmBookList(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		BookMenuForm bookMenuForm = (BookMenuForm) form;		
		CourseDAO dao = new CourseDAO();
		
		dao.updateBookListStatus("0", bookMenuForm.getCourseId(), bookMenuForm.getAcadyear(),bookMenuForm.getNetworkId(),bookMenuForm.getTypeOfBookList());
		updateAuditTrail(bookMenuForm, "Booklist unconfirmed");
		return confirmBooks(mapping, form, request, response);
	}	
	
	public ActionForward saveCourseNotes(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		
		BookMenuForm bookMenuForm = (BookMenuForm) form;		
		CourseDAO dao = new CourseDAO();
		BookDetails bookDetails = new BookDetails();
		
		String studyUnit = bookMenuForm.getCourseId();
		String acadYear = bookMenuForm.getAcadyear();
		String courseNote =ltrim(bookMenuForm.getCourseNote());		
		String typeofBooklist = bookMenuForm.getTypeOfBookList();
		String status = bookDetails.getStatus();
			
		ActionMessages messages = new ActionMessages();
		
		if (courseNote.length() == 0 || courseNote==null){
	    	messages.add(ActionMessages.GLOBAL_MESSAGE,
			        new ActionMessage("message.generalmessage", "You cannot save empty course notes, Please enter course notes."));
			addErrors(request, messages);
			return courseNote(mapping, form, request, response);
		}
		else if (courseNote.length() > 1000){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
			        new ActionMessage("message.generalmessage", "Course notes should not be more than 1000 characters, Please enter course notes."));
			addErrors(request, messages);
			return courseNote(mapping, form, request, response);
		}
		else {

			try {
				dao.saveCourseNote(studyUnit, acadYear, courseNote ,status,typeofBooklist);
				updateAuditTrail(bookMenuForm, "Course note updated");
			} catch (Exception e) {
				String errorMessage = e.getMessage();
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("message.generalmessage",errorMessage));
				addErrors(request, messages);
				return courseNote(mapping, form, request, response);
			}
			return confirmBooks(mapping, form, request, response);
		}
}

	public ActionForward removeBook(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response)throws Exception{
		BookMenuForm bookMenuForm = (BookMenuForm) form;
		
		ActionMessages messages = new ActionMessages();
		CourseDAO dao = new CourseDAO();
		boolean deleteStatus = false;
		
		Iterator i = bookMenuForm.getBookList().iterator();
		while (i.hasNext()){
			BookDetails bookDetails = (BookDetails) i.next();
			if (bookDetails.isRemove()){
				deleteStatus = true;
			    bookMenuForm.setLastmodifiedforbook(dao.getLastUpdatedPerBook(bookMenuForm.getCourseId(), bookMenuForm.getAcadyear(), bookMenuForm.getTypeOfBookList(), bookDetails.getBookId()));
			   
			}
		}
		if (deleteStatus){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
			        new ActionMessage("message.generalmessage", "Are you sure you want to delete the following Book List information?"));
			addErrors(request, messages);
			return mapping.findForward("bookremove");
		} else {
			messages.add(ActionMessages.GLOBAL_MESSAGE,
			        new ActionMessage("message.generalmessage", "Please tick from check box and click remove."));
			addErrors(request, messages);
			return confirmBooks(mapping, form, request, response);
		}
	}
	public ActionForward deleteBook(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		BookMenuForm bookMenuForm = (BookMenuForm) form;
		ActionMessages messages = new ActionMessages();
		CourseDAO dao = new CourseDAO();
		Iterator i = bookMenuForm.getBookList().iterator();
		while (i.hasNext()){
			BookDetails bookDetails = (BookDetails) i.next();
			if (bookDetails.isRemove()){
		
				dao.deleteBook(bookDetails, bookMenuForm.getCourseId(),bookMenuForm.getAcadyear());				
				updateAuditTrail(bookMenuForm, "Booknr " +bookDetails.getBookId()+" deleted");
			}
		}
		messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("message.generalmessage","You have successfully deleted the book(s)"));
		addMessages(request, messages);
		saveMessages(request, messages);
		return confirmBooks(mapping, form, request, response);
	}
	private void updateAuditTrail(BookMenuForm bookMenuForm, String updateInfo){
		CourseDAO dao = new CourseDAO();
		AuditTrail auditTrail = new AuditTrail();
		
		auditTrail.setNetworkId(bookMenuForm.getNetworkId());		
		auditTrail.setStudyUnitCode(bookMenuForm.getCourseId());
		auditTrail.setAcadYear(bookMenuForm.getAcadyear());
		auditTrail.setBookStatus(bookMenuForm.getTypeOfBookList());
		auditTrail.setUpdateInfo(updateInfo);
		dao.updateAuditTrail(auditTrail);
		
	}
	//Sifiso Changes: 2017/08/08:LU_MA_BLU_06:Requirement 10:Tutor Booklist: Submit button action
	public ActionForward submitTutorCount(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		BookMenuForm bookMenuForm = (BookMenuForm) form;
		ActionMessages messages = new ActionMessages();
		CourseDAO dao = new CourseDAO();
		
		//Get Diss Authoriser details from sakai.properties
		String dissNames = ServerConfigurationService.getString( "booklist.dissAuthName" );
		String dissEmails = ServerConfigurationService.getString( "booklist.dissAuthEmail" );
		//Split dissNames and dissEmails from sakai.properties by comma
		String [] tmpDissNames = dissNames.split(",");  	
		String [] tmpDissEmails = dissEmails.split(",");
		List tmpNamesList = new ArrayList();		//for adding to bookMenuForm list
		List tmpEmailsList = new ArrayList();
		
		//Add split names to temp list
		for ( int i=0; i<tmpDissNames.length; i++){			
			tmpNamesList.add( tmpDissNames[i].toString().trim() );
		}
		bookMenuForm.setDissAuthNames( tmpNamesList );	    //set names to bookMenuForm list
		
		//Add split emails to temp list
		for ( int i=0; i<tmpDissEmails.length; i++ ){
			tmpEmailsList.add( tmpDissEmails[i].toString().trim() );
		}
		bookMenuForm.setDissAuthEmails( tmpEmailsList );	//set emails to bookMenuForm list
		
		int updateCountResult = 0;
		if( bookMenuForm.getCountUpdateTracker().equals("0") ){			//not chosen authoriser yet
			//Check if input is digit - works in reverse manner - should be named isNotDigit()
			if ( isDigit(bookMenuForm.getTutorCount()) || bookMenuForm.getTutorCount().isEmpty() ||
					bookMenuForm.getTutorCount().equals("0") )
				messages.add( ActionMessages.GLOBAL_MESSAGE,new ActionMessage("errors.tutorcount") );
			else
				bookMenuForm.setCountUpdateTracker( "1" ); 	//proceed to next step of send authorisation
			
			addErrors( request, messages );
			return mapping.findForward( "editTutorCount" );
		}else if( bookMenuForm.getCountUpdateTracker().equals("1") ) {	//authoriser chosen, update and send authorisation
			//Check if input is digit - works in reverse manner - should be named isNotDigit()
			if ( isDigit(bookMenuForm.getTutorCount()) || bookMenuForm.getTutorCount().isEmpty() ||
					bookMenuForm.getTutorCount().equals("0") ){
				messages.add( ActionMessages.GLOBAL_MESSAGE,new ActionMessage("errors.tutorcount") );
				addErrors( request, messages );
				return mapping.findForward( "editTutorCount" );
			}else{
				//Set page tracker to identify that we are at this step
				bookMenuForm.setCountUpdateTracker( "2" );
				try{
					//Update TutorCount in Database
					updateCountResult = dao.updateTutorCount( Integer.parseInt(bookMenuForm.getTutorCount()), 0, 
							bookMenuForm.getCourseId(), bookMenuForm.getAcadyear(), bookMenuForm.getTypeOfBookList() );
				}catch ( Exception ex ){
					messages.add( ActionMessages.GLOBAL_MESSAGE,new ActionMessage("message.generalmessage",
							"Something went wrong! Contact ICT!") );
					ex.printStackTrace();
					throw new Exception( "PrebookAction : submitTutorCount() Error occurred / "+ ex,ex );
				}
				
				if( updateCountResult > 0 ){
					//Get authoriser email using index of name
					String authEmail = "";
					String authName = "";
					int nameIndex = 0;
					authName = bookMenuForm.getSelectedDissName();
					nameIndex = bookMenuForm.getDissAuthNames().indexOf( authName );
					authEmail = bookMenuForm.getDissAuthEmails().get( nameIndex ).toString();
					
					//Send Authoristation email
					if( !authEmail.isEmpty() ){
						//get submitter name from user directory service using novell_Id (network id)
						User submitterDetails = null;
						String submittedBy="";
						try {
							userDirectoryService = (UserDirectoryService) ComponentManager.get( UserDirectoryService.class );
							submitterDetails = userDirectoryService.getUserByEid( bookMenuForm.getNetworkId() );
						} catch (UserNotDefinedException e) {
							e.printStackTrace();
						}	
						try{
							submitterDetails = userDirectoryService.getUserByEid( bookMenuForm.getNetworkId() );
							submittedBy = submitterDetails.getDisplayName();
						} catch ( UserNotDefinedException exception ){
							submittedBy="User Unknown";
						}		
						
						String emailSubject = "Request Authorization of tutor count of prescribed booklist for "+
								bookMenuForm.getCourseId();
						String emailMessage = "NB: This is an automated message - do not reply to this e-mail. <br>"+
								"<p>"+
								""+submittedBy+" request authorization of the "+bookMenuForm.getAcadyear()+ 
								" tutor prescribed booklist for "+bookMenuForm.getCourseId()+".<br>"+
								"<p>"+
								"To view this book list:<br>"+
								" 1.	Access myUnisa site at https://my.unisa.ac.za/portal<br>"+
								" 2.	Login with your network code and password.<br>"+
								" 3.	Select the Course Admin site from the orange horizontal navigation (or in the –more- drop-down list).<br>"+
								" 4.	The Book List Authorization tool is accessible from the left navigation.<br>"+
								" 5.	Click on the 'DISS Authorization' tab on the toolbar to Authorize"+
								"<p>"+
								"Regards <br>" +
								"myUnisa Support Team";
						
						sendEmail( emailSubject, emailMessage, authEmail );
					}
					
					//Write audit trail
					updateAuditTrail( bookMenuForm, "Tutor Count "+bookMenuForm.getTutorCount()+" Added" );
					
					messages.add( ActionMessages.GLOBAL_MESSAGE,new ActionMessage("message.tutorcountsuccess",
							bookMenuForm.getCourseId()) );
					addMessages( request, messages );
				}else{
					messages.add( ActionMessages.GLOBAL_MESSAGE,new ActionMessage("message.generalmessage",
							"Tutor Count not added! Authorisation not sent! Contact ICT!") );
					addErrors( request, messages );
				}
			}
		}

		bookMenuForm.setAcadyear( "Select Academic Year" );	//Reset acad year for drop-down
		return viewBookMenu( mapping, form, request, response );
	}
	
	public boolean isDigit(String isbn) {

		boolean letterFound = false;
		  
		for (char ch : isbn.toCharArray()) 
		{
		  if (Character.isLetter(ch)) 
		   {
		     letterFound = true;
		     break;
		   }  
		 }
		  // if not true after passing through the entire string, return false
		  return letterFound;
	}
	private boolean isInteger(String i)
	{
		try{
			Integer.parseInt(i);
			return true;
		}catch(NumberFormatException nfe)
		{
			return false;
		}
	}	
	/* remove leading whitespace */
    public static String ltrim(String source) {
         return source.replaceAll("^\\s+", "");
    }
    /* remove trailing whitespace */
    public static String rtrim(String source) {
         return source.replaceAll("\\s+$", "");
    }
}
