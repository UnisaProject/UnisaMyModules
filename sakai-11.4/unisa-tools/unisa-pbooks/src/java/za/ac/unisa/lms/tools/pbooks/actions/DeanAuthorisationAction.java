package za.ac.unisa.lms.tools.pbooks.actions;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.LookupDispatchAction;
import org.sakaiproject.component.cover.ComponentManager;
import org.sakaiproject.component.cover.ServerConfigurationService;
import org.sakaiproject.email.api.EmailService;
import org.sakaiproject.user.api.User;
import org.sakaiproject.user.api.UserDirectoryService;
import org.sakaiproject.user.api.UserNotDefinedException;

import za.ac.unisa.lms.tools.pbooks.dao.AuditTrail;
import za.ac.unisa.lms.tools.pbooks.dao.AuthorisationDAO;
import za.ac.unisa.lms.tools.pbooks.dao.BookDetails;
import za.ac.unisa.lms.tools.pbooks.dao.CollegeDeptDetails;
import za.ac.unisa.lms.tools.pbooks.dao.CourseDAO;
import za.ac.unisa.lms.tools.pbooks.dao.DeanForm;
import za.ac.unisa.lms.tools.pbooks.forms.BookMenuForm;


public class DeanAuthorisationAction  extends LookupDispatchAction{	
	private UserDirectoryService userDirectoryService;
	private EmailService emailService;

	protected Map getKeyMethodMap() {
		Map map = new HashMap();
		map.put("deanview", "deanview");
		map.put("deanmainview", "deanmainview");
		map.put("deanviewContinue","deanviewContinue");
		map.put("button.continuetodeanscreen","deanviewContinue");
		map.put("button.displayoptions", "display"); 
		map.put("displayStudyUnits", "displayStudyUnits"); 
		map.put("button.display", "displayStudyUnits"); 
		map.put("displayoptions", "displayoptions");	
		map.put("button.continue","deanAuthorizeList");
		map.put("button.continuetodean","contiueTodeanstep1");
		map.put("button.backtodeanscreen","deanview");
		map.put("button.back", "cancel");
		map.put("button.confirmAuthorize", "confirmAuthorize");
		map.put("button.authorize", "authorize");
		map.put("button.sendback", "sendback");
		map.put("button.unAuthorize", "unAuthorize");		
		map.put("button.cancel", "cancel");
		map.put("authorizepermodule", "authorizepermodule"); 		
		return map;
				
	}
	public ActionForward deanview(
			  ActionMapping mapping,
			  ActionForm form,
			  HttpServletRequest request,
			  
			  HttpServletResponse response) throws Exception {
		
		BookMenuForm bookMenuForm=(BookMenuForm) form;	
		ActionMessages messages = new ActionMessages();	
		HttpSession session = request.getSession(true);
		AuthorisationDAO authDao = new AuthorisationDAO();	
		bookMenuForm.setSelectedCourse("");
	    bookMenuForm.setAcadyear("Select Academic Year");
		Calendar calen = Calendar.getInstance();
		Integer current=new Integer(calen.get(Calendar.YEAR));
		String now=current.toString();
		bookMenuForm.setContinueOption("");
		bookMenuForm.setTypeOfBookList("");

		String persno=authDao.getPersonnelNr(bookMenuForm.getNetworkId().toUpperCase());
		bookMenuForm.setPersonnelNr(persno);
		//verify the personnel number is numeric or not in Usr table. If not, he does not have access to use the tool
		Boolean num=verityNumber(persno);
		if (num.equals(false)){				
			messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage",
									"You are not authorised to use this tool"));
				addErrors(request,messages);
				return mapping.findForward("viewBookMenu");		
			}		
		String college=authDao.getDeanCollege(persno);	   

		if(college.length()==0){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage",
								"You are not authorised to use this tool"));
				addErrors(request,messages);
				return mapping.findForward("viewBookMenu");		
		}
		bookMenuForm.setCancelOption("TOMAINVIEW");
		return mapping.findForward("deanacadyearselect");
		
	}
	public ActionForward deanviewContinue(
			  ActionMapping mapping,
			  ActionForm form,
			  HttpServletRequest request,
			  
			  HttpServletResponse response) throws Exception {
		
	           return mapping.findForward("deanviewstep1");
	}
	public ActionForward display(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		BookMenuForm bookMenuForm=(BookMenuForm) form;	
		ActionMessages messages = new ActionMessages();
		AuthorisationDAO authDao = new AuthorisationDAO();
		CourseDAO dao = new CourseDAO();
		String courseValid="";
		String verifyCourse="";
		bookMenuForm.getTypeOfBookList();
		 bookMenuForm.setDepartment("");
		 bookMenuForm.setSearchOption("");
		String status;
		userDirectoryService = (UserDirectoryService) ComponentManager.get(UserDirectoryService.class);
		 if(bookMenuForm.getContinueOption().equals("")){
		 if(bookMenuForm.getTypeOfBookList().equals("")){
		 messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage",
								"Please select book list type"));
			addErrors(request,messages);
		 }
		 return mapping.findForward("deanviewstep1");
		 }
		else  if(bookMenuForm.getContinueOption().equals("searchmodule")){
			   bookMenuForm.setCourseId(rtrim(ltrim(bookMenuForm.getCourseId().toUpperCase())));		
				if (bookMenuForm.getCourseId().equalsIgnoreCase("")){
		    	messages.add(ActionMessages.GLOBAL_MESSAGE,
				        new ActionMessage("message.generalmessage", "Please enter module code and click on Display again"));
				addErrors(request, messages);
				return mapping.findForward("deansearchcourse");
			}else if (bookMenuForm.getCourseId().length()<7){
		    	messages.add(ActionMessages.GLOBAL_MESSAGE,
				        new ActionMessage("message.generalmessage", "Module Code must be minimum of 7 characters"));
				addErrors(request, messages);
				return mapping.findForward("deansearchcourse");
			}else				
				courseValid =authDao.courseValid(bookMenuForm.getCourseId());
				if (courseValid.equalsIgnoreCase("")){
			    	messages.add(ActionMessages.GLOBAL_MESSAGE,
					        new ActionMessage("message.generalmessage", "Please enter valid course code"));
					addErrors(request, messages);
					return mapping.findForward("deansearchcourse");
				}else		
				
					status=authDao.getStatus(bookMenuForm.getCourseId(), bookMenuForm.getAcadyear(), bookMenuForm.getTypeOfBookList());
				     verifyCourse=authDao.verifyModulefordean(bookMenuForm.getAcadyear(), bookMenuForm.getTypeOfBookList(),4, bookMenuForm.getPersonnelNr(), bookMenuForm.getCourseId());
				     if(status.equals("3")){
			 		 messages.add(ActionMessages.GLOBAL_MESSAGE,
						        new ActionMessage("message.generalmessage", "A status of No books for this module has been declared."));
						addErrors(request, messages);
						return mapping.findForward("deansearchcourse");					  
			      }
					else if(verifyCourse.length()==0){
						 messages.add(ActionMessages.GLOBAL_MESSAGE,
							        new ActionMessage("message.generalmessage", "You cannot authorize this module.It may not be ready for authorization or do not belong to your College"));
							addErrors(request, messages);
							return mapping.findForward("deansearchcourse");	
					}else{	 		    	  
						bookMenuForm.setLastUpdated(dao.getLastUpdated(bookMenuForm.getCourseId(), bookMenuForm.getAcadyear(),bookMenuForm.getTypeOfBookList()));
						bookMenuForm.setCourseNote(dao.getCourseNote(bookMenuForm.getCourseId(), bookMenuForm.getAcadyear(),bookMenuForm.getTypeOfBookList()));
						bookMenuForm.setBookListStatus("Booklist submitted for authorization");						
						// After setting the Audit Trail, now resolve the fullName of last updated User.
						if ( bookMenuForm.getLastUpdated() != null) {
							String userId = bookMenuForm.getLastUpdated().getNetworkId();
							String lastUpdatedDisplayName="";
							try{
							User lastUpdatedUserDetails = userDirectoryService.getUserByEid(userId);
							lastUpdatedDisplayName = lastUpdatedUserDetails.getDisplayName();
							}catch (Exception e) {
								lastUpdatedDisplayName="User Unknown";
							}
							bookMenuForm.setDisplayAuditTrailName(lastUpdatedDisplayName);
						}
						try {
							bookMenuForm.setBookList(dao.getBookList(bookMenuForm.getCourseId(),bookMenuForm.getAcadyear(),bookMenuForm.getTypeOfBookList()));
						
						} catch (Exception ex){
							ex.printStackTrace();
						}
					    
						return mapping.findForward("deanAuthorize");
			      }
		 }
		 return mapping.findForward("deanviewstep1");
	}
	public ActionForward displayoptions(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		BookMenuForm bookMenuForm=(BookMenuForm) form;	
		AuthorisationDAO authDao = new AuthorisationDAO();
		HttpSession session = request.getSession(true);
		ActionMessages messages = new ActionMessages();	
		GregorianCalendar calCurrent = new GregorianCalendar();
		String year=Integer.toString(calCurrent.get(Calendar.YEAR));
		int dayOfMonthInt =calCurrent.get(Calendar.DATE);
		String dayOfMonth="";
		if(dayOfMonthInt<10){
		   dayOfMonth+="0"+dayOfMonthInt;
		}else{
			dayOfMonth+=dayOfMonthInt;
		}
		String month="";
		int monthInt=calCurrent.get(Calendar.MONTH)+1;
		if(monthInt<10){
			   month+="0"+monthInt;
	     }else{
				month+=monthInt;
	    }
		bookMenuForm.setListDate(year+"-"+month+"-"+dayOfMonth);
		List deptList = new ArrayList();
	     deptList = authDao.getDeanAllDeptList(Integer.parseInt(bookMenuForm.getPersonnelNr()));		
		session.setAttribute("deanDepartments", deptList);
		
		if(bookMenuForm.getSearchOption().length()==0){			
			return mapping.findForward("deandisplayoptions");
		}
		if(!(bookMenuForm.getSearchOption().length()==0)||bookMenuForm.getSearchOption().equals("")){
		if(bookMenuForm.getDepartment().equals("")||bookMenuForm.getDepartment().equals("-1")){
			bookMenuForm.setSearchOption("");
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage",
								"Please select a department"));
			addErrors(request,messages);				
		}else{
			
			List courseList=null;
	    	String[] temp = null;
	    	temp=bookMenuForm.getDepartment().split("-");
	    	bookMenuForm.setDepartment(temp[0]);
			bookMenuForm.setDeptName(temp[1]);
			if(bookMenuForm.getSearchOption().equals("notsubmitted")){				
				bookMenuForm.setStatus("Modules outstanding (not started by academic)");
				 courseList=authDao.getNoActionCourseList(bookMenuForm.getDepartment(), bookMenuForm.getAcadyear(),bookMenuForm.getTypeOfBookList());
				 bookMenuForm.setCourselist(courseList);
				 if(courseList.size()==0){
					 messages.add(ActionMessages.GLOBAL_MESSAGE,
								new ActionMessage("message.generalmessage",
											"No modules for your selection"));
						addErrors(request,messages);	
						bookMenuForm.setDepartment("");
						bookMenuForm.setSearchOption("");
						return mapping.findForward("deandisplayoptions");
						}
				 return mapping.findForward("deancourselist");	
			}else if(bookMenuForm.getSearchOption().equals("allmodules")){
				bookMenuForm.setStatus("List of all modules (codes only) with status by department");
				courseList=authDao.getAllModulesforDept(bookMenuForm.getDepartment(), bookMenuForm.getAcadyear(),bookMenuForm.getTypeOfBookList());
				 bookMenuForm.setCourselist(courseList);
				 session.setAttribute("courseList",courseList);
				 return mapping.findForward("deancourselist");	
			}else{
				String statusselected=bookMenuForm.getSearchOption();
				if(statusselected.equals("0")){
					bookMenuForm.setStatus("Booklist started but not submitted");
				}else if(statusselected.equals("1")){
					bookMenuForm.setStatus("Booklist submitted but not authorized");
				}else if(statusselected.equals("2")){
					bookMenuForm.setStatus("Booklist authorized by COD");
				}else if(statusselected.equals("3")){
					bookMenuForm.setStatus("Modules with no books declaration");
				}else if(statusselected.equals("4")){
					bookMenuForm.setStatus("Booklist authorized by School Director");
				}else if(statusselected.equals("5")){
					bookMenuForm.setStatus("Book list authorized by Dean");			
				}			
				 courseList=authDao.getCourseListperdept(bookMenuForm.getDepartment(), bookMenuForm.getAcadyear(),bookMenuForm.getSearchOption(),bookMenuForm.getTypeOfBookList());
				 bookMenuForm.setCourselist(courseList);
				 if(courseList.size()==0){
					 messages.add(ActionMessages.GLOBAL_MESSAGE,
								new ActionMessage("message.generalmessage",
											"No modules for your selection"));
						addErrors(request,messages);	
						bookMenuForm.setDepartment("");
						bookMenuForm.setSearchOption("");
						return mapping.findForward("deandisplayoptions");
						}
				
				 
				 return mapping.findForward("deancourselist");	
			}
		}
		}
		    return mapping.findForward("deandisplayoptions");
	}
	public ActionForward deanmainview(
			  ActionMapping mapping,
			  ActionForm form,
			  HttpServletRequest request,
			  
			  HttpServletResponse response) throws Exception {
		
		BookMenuForm bookMenuForm=(BookMenuForm) form;	
		ActionMessages messages = new ActionMessages();	
		HttpSession session = request.getSession(true);
		AuthorisationDAO authDao = new AuthorisationDAO();	
		bookMenuForm.setSelectedCourse("");
		Calendar calen = Calendar.getInstance();
		 Integer current=new Integer(calen.get(Calendar.YEAR));
		 String now=current.toString();


		List deptList = new ArrayList();
		String persno=authDao.getPersonnelNr(bookMenuForm.getNetworkId().toUpperCase());
		deptList = authDao.getDeanDeptList(Integer.parseInt(persno),bookMenuForm.getAcadyear());
		
		session.setAttribute("deanDepartments", deptList);
		bookMenuForm.setCollege(authDao.getDeanCollege(persno));
		if (deptList.size()==0){
					messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage",
								"There are no booklists to authorize for the selected book list type "));
				addErrors(request,messages);
				return mapping.findForward("deanmainview");		
		}
					
		return mapping.findForward("deanmainview");
	}
	public ActionForward authorizepermodule(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		BookMenuForm bookMenuForm=(BookMenuForm) form;	
		ActionMessages messages = new ActionMessages();	
		bookMenuForm.setCourseId("");
		 
	 return mapping.findForward("deansearchcourse");
	}
	public boolean verityNumber(String persno){
		try {
		Integer.parseInt(persno);
		return true;
		} catch (NumberFormatException nfe){
		return false;
		}
		} 
	public ActionForward displayStudyUnits(
			  ActionMapping mapping,
			  ActionForm form,
			  HttpServletRequest request,
			  HttpServletResponse response) throws Exception {
		        BookMenuForm bookMenuForm=(BookMenuForm) form;	
		        AuthorisationDAO authDao = new AuthorisationDAO();
		        ActionMessages messages = new ActionMessages();	
		        String persno=bookMenuForm.getPersonnelNr();
		        if(bookMenuForm.getDepartment().equals("-1")){
			          addErrorMessage(request,"Please select a value from the drop-down list.");
					  return mapping.findForward("deanmainview");	
		        }
		        setBookLists(bookMenuForm,authDao);
		        if(bookMenuForm.getPresCourseList().size()==0 && bookMenuForm.getRecCourseList().size()==0 && bookMenuForm.getEresCourseList().size()==0){
			          addErrorMessage(request,"There are no booklists to authorize. for this booklist type");
			   	      return mapping.findForward("deanmainview");	
		        }
		        bookMenuForm.setDeptName(authDao.getDeptCode(bookMenuForm.getDepartment())); 	
		        bookMenuForm.setDeanViewStep1Reached("reached");
		        return mapping.findForward("deanRequestList");
   }
   private void  addErrorMessage(HttpServletRequest request,String errorMessage){
		           ActionMessages messages = new ActionMessages();	
		           messages.add(ActionMessages.GLOBAL_MESSAGE,
				                 new ActionMessage("message.generalmessage",errorMessage));
		           addErrors(request,messages);
	}
	private void setBookLists(BookMenuForm bookMenuForm,AuthorisationDAO authDao) throws Exception{
  		         String persno=bookMenuForm.getPersonnelNr();
		         bookMenuForm.setPresCourseList(
		    		 authDao.getDeanCourseList(bookMenuForm.getAcadyear(), "P",4,persno,bookMenuForm.getDepartment()));
		         bookMenuForm.setRecCourseList(
		    		 authDao.getDeanCourseList(bookMenuForm.getAcadyear(), "R",4,persno,bookMenuForm.getDepartment()));
		         bookMenuForm.setEresCourseList(
		    		 authDao.getDeanCourseList(bookMenuForm.getAcadyear(), "E",4,persno,bookMenuForm.getDepartment()));
		
	}
	
	 public ActionForward contiueTodeanstep1(
			  ActionMapping mapping,
			  ActionForm form,
			  HttpServletRequest request,
			  HttpServletResponse response) throws Exception {
	                 ActionMessages messages = new ActionMessages();	
		             BookMenuForm bookMenuForm=(BookMenuForm) form;
		           	 bookMenuForm.setDeanViewStep1Reached("reached");
		        	 if(bookMenuForm.getAcadyear().equals("Select Academic Year")){
			               messages.add(ActionMessages.GLOBAL_MESSAGE,
			               new ActionMessage("message.generalmessage",
						   "Please select Academic Year."));
	                       addErrors(request,messages);
			               return mapping.findForward("deanacadyearselect");
		             }else{
		            	         return mapping.findForward("deanviewstep1");
		             }
	}
   public ActionForward deanAuthorizeList(
			  ActionMapping mapping,
			  ActionForm form,
			  HttpServletRequest request,
			  
			      HttpServletResponse response) throws Exception {
	              ActionMessages messages = new ActionMessages();	
		          BookMenuForm bookMenuForm=(BookMenuForm) form;
		          if(!bookMenuForm.deanViewStep1Reached().equals("reached")){
		        	   bookMenuForm.setDeanViewStep1Reached("reached");
		        	   if(bookMenuForm.getAcadyear().equals("Select Academic Year")){
			               messages.add(ActionMessages.GLOBAL_MESSAGE,
			               new ActionMessage("message.generalmessage",
						   "Please select Academic Year."));
	                       addErrors(request,messages);
			               return mapping.findForward("deanacadyearselect");
		               }else{
		            	         return mapping.findForward("deanviewstep1");
		               }
		          }
		          if(bookMenuForm.getPresCourseList().size()==0 && bookMenuForm.getRecCourseList().size()==0 && bookMenuForm.getEresCourseList().size()==0){
		        	  messages.add(ActionMessages.GLOBAL_MESSAGE,
				               new ActionMessage("message.generalmessage",
							   "There are no booklists to authorize for the selected booklist type."));
		                       addErrors(request,messages);
		        	    bookMenuForm.setSelectedCourse("-1");
		           	    return mapping.findForward("viewBookMenu");
		           	}
		BookDetails pbooks=new BookDetails();
		CourseDAO dao = new CourseDAO();
		String selectedCourse=bookMenuForm.getSelectedCourse();	
		bookMenuForm.setAuthComment("");		
		if(bookMenuForm.getSelectedCourse().equals("")){
			 
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage",
								"Please select a module code for authorization."));
			addErrors(request,messages);
			  return mapping.findForward("deanRequestList");
		}
		bookMenuForm.setCourseId(selectedCourse.substring(1));
		bookMenuForm.setTypeOfBookList(selectedCourse.substring(0,1));
		bookMenuForm.setLastUpdated(dao.getLastUpdated(bookMenuForm.getCourseId(), bookMenuForm.getAcadyear(),bookMenuForm.getTypeOfBookList()));
		bookMenuForm.setCourseNote(dao.getCourseNote(bookMenuForm.getCourseId(), bookMenuForm.getAcadyear(),bookMenuForm.getTypeOfBookList()));
		bookMenuForm.setBookListStatus("Booklist submitted for authorization");
		
		userDirectoryService = (UserDirectoryService) ComponentManager.get(UserDirectoryService.class);
		// After setting the Audit Trail, now resolve the fullName of last updated User.
		if ( bookMenuForm.getLastUpdated() != null) {
			String userId = bookMenuForm.getLastUpdated().getNetworkId();
			String lastUpdatedDisplayName="";
			try{
			User lastUpdatedUserDetails = userDirectoryService.getUserByEid(userId);
			lastUpdatedDisplayName = lastUpdatedUserDetails.getDisplayName();
			}catch (UserNotDefinedException e) {
				lastUpdatedDisplayName="User Unknown";
			}
			bookMenuForm.setDisplayAuditTrailName(lastUpdatedDisplayName);
		}
		try {
			bookMenuForm.setBookList(dao.getBookList(bookMenuForm.getCourseId(),bookMenuForm.getAcadyear(),bookMenuForm.getTypeOfBookList()));
		
		} catch (Exception ex){
			ex.printStackTrace();
		}
			return mapping.findForward("deanAuthorize");
	
	}
	
	public ActionForward cancel (
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response){
		    
		    BookMenuForm bookMenuForm=(BookMenuForm) form;
		    bookMenuForm.setDeanViewStep1Reached("notReached");
		    if(bookMenuForm.getCancelOption().equals("requestedList")){
			      return mapping.findForward("deanAuthorize");
		    }else if(bookMenuForm.getCancelOption().equals("TOMAINVIEW")){
		    	     bookMenuForm.setAcadyear("Select Academic Year");
			         return mapping.findForward("viewBookMenu");
		    }else if(bookMenuForm.getCancelOption().equals("deanmainview")){
			          return mapping.findForward("deanmainview");
		    }else if(bookMenuForm.getCancelOption().equals("prevstep")){
			           bookMenuForm.setDepartment("");
			           bookMenuForm.setSearchOption("");
			           return mapping.findForward("deandisplayoptions");
		     }else if(bookMenuForm.getCancelOption().equals("mainview")){
			           	bookMenuForm.setContinueOption("");
			           	      return mapping.findForward("deanviewstep1");
			        
			 }else if(bookMenuForm.getCancelOption().equals("deanacadyearselect")){
		           	  bookMenuForm.setContinueOption("");
		           	  bookMenuForm.setCancelOption("TOMAINVIEW");
		           	  bookMenuForm.setAcadyear("Select Academic Year");
	           	      return mapping.findForward("deanacadyearselect");
			 }
		     return mapping.findForward("deanmainview");
	}
	public ActionForward sendback(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		    BookMenuForm bookMenuForm=(BookMenuForm) form;
		    ActionMessages messages = new ActionMessages();	
		    bookMenuForm.setDeanViewStep1Reached("notReached");
		    if(bookMenuForm.getAuthComment().length()>200){
			      messages.add(ActionMessages.GLOBAL_MESSAGE,
					    new ActionMessage("message.generalmessage",
								"Comment should not be more than 200 characters."));
			      addErrors(request,messages);
			      return mapping.findForward("codAuthorize");
			}
		    messages.add(ActionMessages.GLOBAL_MESSAGE,
				new ActionMessage("message.generalmessage",
							"Are you sure you want to reject the following Book List Information?"));
		    addErrors(request,messages);			
		   return mapping.findForward("deanconfirmSendBack");
	}
	public ActionForward confirmAuthorize(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		BookMenuForm bookMenuForm=(BookMenuForm) form;
		ActionMessages messages = new ActionMessages();	
		
		if(bookMenuForm.getAuthComment().length()>200){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage",
								"Comment should not be more than 200 characters."));
			addErrors(request,messages);
			return mapping.findForward("codAuthorize");
			}
		messages.add(ActionMessages.GLOBAL_MESSAGE,
				new ActionMessage("message.generalmessage",
							"Are you sure you want to authorize the following Book List Information?"));
		addErrors(request,messages);			
		
		return mapping.findForward("deanconfirmAuthorize");
	}
	
	public ActionForward authorize(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		BookMenuForm bookMenuForm=(BookMenuForm) form;
		ActionMessages messages = new ActionMessages();	
		CourseDAO dao = new CourseDAO();
		AuthorisationDAO authDao = new AuthorisationDAO();
		dao.updateBookListStatus("5", bookMenuForm.getCourseId(), bookMenuForm.getAcadyear(),bookMenuForm.getNetworkId(),bookMenuForm.getTypeOfBookList());			
		String novellUserCode=dao.getRequestedPerson(bookMenuForm.getCourseId(), bookMenuForm.getAcadyear(),bookMenuForm.getTypeOfBookList(),"Book list confirmed");		
		String sdUserCode = authDao.getSchDirectorUserCode(bookMenuForm.getCourseId());
		String email=novellUserCode;
		email += "@unisa.ac.za";		
		updateAuditTrail(bookMenuForm, "Booklist authorized by Dean/Deputy Dean"+ bookMenuForm.getAuthComment());
		
		//send an email to requested person for authorization			
		String emailSubject;
		String emailMessage;
		if(bookMenuForm.getTypeOfBookList().equals("P")){
			
		 emailSubject = "Dean/Deputy Dean Approval of Prescribed Booklist for "+bookMenuForm.getCourseId()+" for "+bookMenuForm.getAcadyear();
		 emailMessage = "NB: This is an automated message - do not reply to this e-mail. <br>"+
				"<p>"+
				"The Prescribed book list for "+bookMenuForm.getCourseId()+" for "+bookMenuForm.getAcadyear()+" has been approved. <br>"+				
				"<p>"+
				"The list has now been sent to the Book List Administrator for verification and release to the Unisa Official Booksellers." +
				"<p>"+
				"Comment from Dean/Deputy Dean: "+bookMenuForm.getAuthComment()+
				"<p>"+
				"Regards <br>" +
				"myUnisa Support Team on behalf of College Management";
		}else if(bookMenuForm.getTypeOfBookList().equals("R")){
			 emailSubject = "Dean/Deputy Dean Approval of Recommended Booklist for "+bookMenuForm.getCourseId()+" for "+bookMenuForm.getAcadyear();
			 emailMessage = "NB: This is an automated message - do not reply to this e-mail. <br>"+
					"<p>"+
					"The Recommended book list for "+bookMenuForm.getCourseId()+" for "+bookMenuForm.getAcadyear()+" has been approved. <br>"+				
					"<p>"+
					"The list has now been sent to the Book List Administrator for verification and release to the Unisa Official Booksellers." +
					"<p>"+
					"Comment from Dean/Deputy Dean: "+bookMenuForm.getAuthComment()+
					"<p>"+
					"Regards <br>" +
					"myUnisa Support Team on behalf of College Management";
		}else{
			 emailSubject = "Dean/Deputy Dean Approval of eReserves List for "+bookMenuForm.getCourseId()+" for "+bookMenuForm.getAcadyear();
			 emailMessage = "NB: This is an automated message - do not reply to this e-mail. <br>"+
					"<p>"+
					"The eReserves list for "+bookMenuForm.getCourseId()+" for "+bookMenuForm.getAcadyear()+" has been approved. <br>"+				
					"<p>"+
					"The list has now been sent to the Book List Administrator for verification and release to the Unisa Official Booksellers." +
					"<p>"+
					"Comment from Dean/Deputy Dean: "+bookMenuForm.getAuthComment()+
					"<p>"+
					"Regards <br>" +
					"myUnisa Support Team on behalf of College Management";
		}
		sendEmail(emailSubject, emailMessage, email);	
		bookMenuForm.setSelectedCourse("-1");
		bookMenuForm.setTypeOfBookList("");
		bookMenuForm.setCourselist(new ArrayList());
		bookMenuForm.setPresCourseList(new ArrayList());
		bookMenuForm.setRecCourseList(new ArrayList());
		bookMenuForm.setEresCourseList(new ArrayList());
        String persno=bookMenuForm.getPersonnelNr();
        bookMenuForm.setContinueOption("deanacadyearselect");
		bookMenuForm.setPresCourseList(authDao.getDeanCourseList(bookMenuForm.getAcadyear(), "P",4,persno,bookMenuForm.getDepartment()));
		bookMenuForm.setRecCourseList(authDao.getDeanCourseList(bookMenuForm.getAcadyear(), "R",4,persno,bookMenuForm.getDepartment()));
		bookMenuForm.setEresCourseList(authDao.getDeanCourseList(bookMenuForm.getAcadyear(), "E",4,persno,bookMenuForm.getDepartment()));
		if(bookMenuForm.getPresCourseList().size()==0 && bookMenuForm.getRecCourseList().size()==0 && bookMenuForm.getEresCourseList().size()==0){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage",
								"There are no booklists to authorize."));
			addErrors(request,messages);
			return mapping.findForward("deanmainview");	
		}
        bookMenuForm.setDeptName(authDao.getDeptCode(bookMenuForm.getDepartment())); 	
        refreshLists(bookMenuForm);
        setBookLists(bookMenuForm,authDao);
		return mapping.findForward("deanRequestList");
	}
		
		private void refreshLists(BookMenuForm bookMenuForm){
			bookMenuForm.setSelectedCourse("-1");
			bookMenuForm.setTypeOfBookList("");
			bookMenuForm.setCourselist(new ArrayList());
			bookMenuForm.setPresCourseList(new ArrayList());
			bookMenuForm.setRecCourseList(new ArrayList());
			bookMenuForm.setEresCourseList(new ArrayList());
  			bookMenuForm.setContinueOption("deanacadyearselect");
		}
		
	public ActionForward unAuthorize(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		BookMenuForm bookMenuForm=(BookMenuForm) form;
		ActionMessages messages = new ActionMessages();	
		CourseDAO dao = new CourseDAO();
		
		dao.updateBookListStatus("0", bookMenuForm.getCourseId(), bookMenuForm.getAcadyear(),bookMenuForm.getNetworkId(),bookMenuForm.getTypeOfBookList());
		updateAuditTrail(bookMenuForm, "Booklist Rejected by Dean/Deputy Dean "+bookMenuForm.getAuthComment());
		String novellUserCode=dao.getRequestedPerson(bookMenuForm.getCourseId(), bookMenuForm.getAcadyear(),bookMenuForm.getTypeOfBookList(),"Book list confirmed");		
		String email=novellUserCode;
		email += "@unisa.ac.za";
		//send an email to requested person for authorization			
		String emailSubject;
		String emailMessage;
		System.out.println("email "+email);
		if(bookMenuForm.getTypeOfBookList().equals("P")){
			
		 emailSubject = "Return of Prescribed Booklist for "+bookMenuForm.getCourseId()+" for "+bookMenuForm.getAcadyear();
		 emailMessage = "NB: This is an automated message - do not reply to this e-mail. <br>"+
				"<p>"+
				"The Prescribed book list for "+bookMenuForm.getCourseId()+" for "+bookMenuForm.getAcadyear()+" has been returned to you for further editing. <br>"+				
				"<p>"+				
				"Comment from Dean/Deputy Dean: "+bookMenuForm.getAuthComment()+
				"<p>"+
				"Regards <br>" +
				"myUnisa Support Team on behalf of College Tuition Committee";
		}else if(bookMenuForm.getTypeOfBookList().equals("R")){
			 emailSubject = "Return of Recommended Booklist for "+bookMenuForm.getCourseId()+" for "+bookMenuForm.getAcadyear();
			 emailMessage = "NB: This is an automated message - do not reply to this e-mail. <br>"+
					"<p>"+
					"The Recommended book list for "+bookMenuForm.getCourseId()+" for "+bookMenuForm.getAcadyear()+" has been returned to you for further editing. <br>"+				
					"<p>"+
					"Comment from Dean/Deputy Dean: "+bookMenuForm.getAuthComment()+
					"<p>"+
					"Regards <br>" +
					"myUnisa Support Team on behalf of College Tuition Committee";
		}else{
			 emailSubject = "Return of eReserves List for "+bookMenuForm.getCourseId()+" for "+bookMenuForm.getAcadyear();
			 emailMessage = "NB: This is an automated message - do not reply to this e-mail. <br>"+
					"<p>"+
					"The eReserves list for "+bookMenuForm.getCourseId()+" for "+bookMenuForm.getAcadyear()+" has been returned to you for further editing. <br>"+				
					"<p>"+
					"Comment from Dean/Deputy Dean: "+bookMenuForm.getAuthComment()+
					"<p>"+
					"Regards <br>" +
					"myUnisa Support Team on behalf of College Tuition Committee";
		}
		
		sendEmail(emailSubject, emailMessage, email);	
		
	
		return mapping.findForward("viewBookMenu");		
		
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
		/* remove leading whitespace */
	    public static String ltrim(String source) {
	        return source.replaceAll("^\\s+", "");
	    }

	    /* remove trailing whitespace */
	    public static String rtrim(String source) {	
	        return source.replaceAll("\\s+$", "");
	    }
		
}
