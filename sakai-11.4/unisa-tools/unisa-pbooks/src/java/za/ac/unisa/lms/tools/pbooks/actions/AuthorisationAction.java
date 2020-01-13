package za.ac.unisa.lms.tools.pbooks.actions;
import java.awt.print.Book;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.sakaiproject.component.cover.ComponentManager;
import org.apache.commons.collections.map.ListOrderedMap;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.LookupDispatchAction;
import org.apache.struts.util.LabelValueBean;
import org.sakaiproject.component.cover.ServerConfigurationService;
import org.sakaiproject.email.api.EmailService;
import org.sakaiproject.user.api.User;
import org.sakaiproject.user.api.UserDirectoryService;
import org.sakaiproject.user.api.UserNotDefinedException;

import za.ac.unisa.lms.tools.pbooks.dao.AuditTrail;
import za.ac.unisa.lms.tools.pbooks.dao.AuthorisationDAO;
import za.ac.unisa.lms.tools.pbooks.dao.CollegeDeptDetails;
import za.ac.unisa.lms.tools.pbooks.dao.CourseDAO;
import za.ac.unisa.lms.tools.pbooks.dao.DeanForm;
import za.ac.unisa.lms.tools.pbooks.forms.BookMenuForm;


public class AuthorisationAction extends LookupDispatchAction{	
	private UserDirectoryService userDirectoryService;
	private EmailService emailService;
	protected Map getKeyMethodMap() {
		Map map = new HashMap();
		map.put("codAuthRequestList", "codAuthRequestList");
		map.put("authRequestListContinue","authRequestListContinue");
		map.put("schoolDirectorAuthRequestList", "schoolDirectorAuthRequestList"); 
		map.put("deanAuthRequestList", "deanAuthRequestList");	
		map.put("codAuthContinue","codAuthContinue");
		map.put("button.continuecodauthorize", "codAuthContinue");
		map.put("button.continuetocodscreen", "authRequestListContinue");
		map.put("button.continue","codAuthContinue");
		map.put("button.display", "display");
		map.put("button.backtomain", "cancel");
		map.put("button.back", "cancel");
		map.put("button.authorize", "authorize");
		map.put("button.cancel", "cancel");
		map.put("button.sendback", "sendback");
		map.put("button.unAuthorize", "unAuthorize");		
		map.put("displayoptions", "displayoptions");	
		map.put("authorizepermodule", "authorizepermodule"); 
		map.put("allmoduleslist", "allmoduleslist");
		map.put("sites.display", "allmoduleslist");		
		//Sifiso Changes: 2017/08/16:LU_MA_BLU_06:Requirement 54:Tutor Booklist: DISS Authorisation
		map.put("dissAuthRequestList", "dissAuthRequestList");
		return map;
	}
	public ActionForward codAuthRequestList(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		    BookMenuForm bookMenuForm=(BookMenuForm) form;
		    ActionMessages messages = new ActionMessages();
		    bookMenuForm.setSchDirector("COD");
            bookMenuForm.setSelectedCourse("");
            bookMenuForm.setAcadyear("Select Academic Year");
            bookMenuForm.setCancelOption("TOMAINVIEW");
            String forward="codselectacadyear";
            if(!authenticateUser(bookMenuForm,"COD")){
            	  addErrorMessage(messages,request);
            	  forward="viewBookMenu";
            }else{
            	    
            }
            return mapping.findForward(forward);
	}
	public ActionForward authRequestListContinue(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		    BookMenuForm bookMenuForm=(BookMenuForm) form;	
		    ActionMessages messages = new ActionMessages();
	        if(!bookMenuForm.getAcadyear().equals("Select Academic Year")){
			      String forward="";
			      if(bookMenuForm.getSchDirector().equals("COD")){
			           forward = getCodAuthRequestList(mapping,form,request,response);
			      }else{
			    	   forward = getSdAuthRequestList(mapping,form,request,response);
			      }
			      return mapping.findForward(forward);
	        }else{
	        	messages.add(ActionMessages.GLOBAL_MESSAGE,
     			        new ActionMessage("message.generalmessage",
     						"Please select Academic Year."));
     	               addErrors(request,messages);
	        	  return mapping.findForward("codselectacadyear");
	        }
	}
	private String getCodAuthRequestList(ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		    String head="",actingCod=""; 
		    BookMenuForm bookMenuForm=(BookMenuForm) form;	
		    ActionMessages messages = new ActionMessages();
			AuthorisationDAO authDao = new AuthorisationDAO();
			String persno=authDao.getPersonnelNr(bookMenuForm.getNetworkId().toUpperCase());
	        bookMenuForm.setPresCourseList(authDao.getCodCourseList(bookMenuForm.getAcadyear(), "P",1,persno));
	        bookMenuForm.setRecCourseList(authDao.getCodCourseList(bookMenuForm.getAcadyear(), "R",1,persno));
	        bookMenuForm.setEresCourseList(authDao.getCodCourseList(bookMenuForm.getAcadyear(), "E",1,persno));
            if(bookMenuForm.getPresCourseList().size()==0 && bookMenuForm.getRecCourseList().size()==0 && bookMenuForm.getEresCourseList().size()==0){
	                messages.add(ActionMessages.GLOBAL_MESSAGE,
			        new ActionMessage("message.generalmessage",
						"There are no booklists to authorize."));
	               addErrors(request,messages);
	               bookMenuForm.setCancelOption("TOMAINVIEW");
	         }
            return "authRequestList";
    }
	public ActionForward schoolDirectorAuthRequestList(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
			  BookMenuForm bookMenuForm=(BookMenuForm) form;	
			  ActionMessages messages = new ActionMessages();
		      bookMenuForm.setContinueOption("");
		      bookMenuForm.setSelectedCourse("");
		      bookMenuForm.setCancelOption("TOMAINVIEW");
		      bookMenuForm.setSchDirector("SD");
		      bookMenuForm.setDeanNetworkId("");
		      bookMenuForm.setDeputydean1NetworkId("");		
		      bookMenuForm.setDeputydean2NetworkId("");			
		      bookMenuForm.setDeputydean3NetworkId("");			
		      bookMenuForm.setDeputydean4NetworkId("");
		      bookMenuForm.setTypeOfBookList("");
		      String forward="codselectacadyear";
	          if(!authenticateUser(bookMenuForm,"SD")){
	            	  addErrorMessage(messages,request);
	            	  forward="viewBookMenu";
	          }
	          return mapping.findForward(forward);
	}
	public String getSdAuthRequestList(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
			BookMenuForm bookMenuForm=(BookMenuForm) form;		
		    ActionMessages messages = new ActionMessages();	
			AuthorisationDAO authDao = new AuthorisationDAO();
		    String head="";
		    String actingCod="";
		    List actingDeptList = new ArrayList();
			String persno=authDao.getPersonnelNr(bookMenuForm.getNetworkId().toUpperCase());		
		    bookMenuForm.setPersonnelNr(persno);
			bookMenuForm.setUserName(authDao.getUserCode(persno));
			head=authDao.getSchoolDirector(Integer.parseInt(persno));		
			return "schdirmainview";	
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
		 if(bookMenuForm.getContinueOption().equals("")){
		 if(bookMenuForm.getTypeOfBookList().equals("")){
		 messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage",
								"Please select book list type"));
			addErrors(request,messages);
		 }
		 return mapping.findForward("schdirmainview");
		 }
		 else  if(bookMenuForm.getContinueOption().equals("searchmodule")){
			   bookMenuForm.setCourseId(rtrim(ltrim(bookMenuForm.getCourseId().toUpperCase())));				
				if (bookMenuForm.getCourseId().equalsIgnoreCase("")){
		    	messages.add(ActionMessages.GLOBAL_MESSAGE,
				        new ActionMessage("message.generalmessage", "Please enter module code and click on Display again"));
				addErrors(request, messages);
				return mapping.findForward("searchcourse");
			}else if (bookMenuForm.getCourseId().length()<7){
		    	messages.add(ActionMessages.GLOBAL_MESSAGE,
				        new ActionMessage("message.generalmessage", "Module Code must be minimum of 7 characters"));
				addErrors(request, messages);
				return mapping.findForward("searchcourse");
			}else				
				courseValid =authDao.courseValid(bookMenuForm.getCourseId());
				if (courseValid.equalsIgnoreCase("")){
			    	messages.add(ActionMessages.GLOBAL_MESSAGE,
					        new ActionMessage("message.generalmessage", "Please enter valid course code"));
					addErrors(request, messages);
					return mapping.findForward("searchcourse");
				}else					
					status=authDao.getStatus(bookMenuForm.getCourseId(), bookMenuForm.getAcadyear(), bookMenuForm.getTypeOfBookList());
				     verifyCourse=authDao.verifyModuleSubmitted(bookMenuForm.getAcadyear(), bookMenuForm.getTypeOfBookList(), 2, bookMenuForm.getPersonnelNr(), bookMenuForm.getCourseId());
					if(status.equals("3")){
			 		 messages.add(ActionMessages.GLOBAL_MESSAGE,
						        new ActionMessage("message.generalmessage", "A status of No books for this module has been declared."));
						addErrors(request, messages);
						return mapping.findForward("searchcourse");					  
			      }
					else if(verifyCourse.length()==0){
						 messages.add(ActionMessages.GLOBAL_MESSAGE,
							        new ActionMessage("message.generalmessage", "You cannot authorize this module.It may not be ready for authorization or do not belong to your School"));
							addErrors(request, messages);
							return mapping.findForward("searchcourse");	
					}else{			    	  
			    	bookMenuForm.setLastUpdated(dao.getLastUpdated(bookMenuForm.getCourseId(), bookMenuForm.getAcadyear(),bookMenuForm.getTypeOfBookList()));
					CollegeDeptDetails coldept = new CollegeDeptDetails();
					bookMenuForm.setBookListStatus("Booklist submitted for authorization");
					coldept = dao.getCollegeDeptDetails(bookMenuForm.getCourseId());
					bookMenuForm.setCollege(coldept.getCollege());
					bookMenuForm.setDepartment(coldept.getDepartment());	
					
					DeanForm deanform=new DeanForm();
					deanform = authDao.getDean(bookMenuForm.getCourseId(), bookMenuForm.getSchDirector());
					bookMenuForm.setDeanNetworkId(deanform.getDeanNetworkId());
					bookMenuForm.setDeanSurname(deanform.getDeanSurname());
					//get deputy deans list selectDeputyDeans
					List deputyDeanList = authDao.selectDeputyDeans(bookMenuForm.getCourseId());
					bookMenuForm.setDeputyDeanList(deputyDeanList); 
				
		/*		
					deanform=authDao.selectDean(bookMenuForm.getCourseId(),bookMenuForm.getSchDirector());		
					bookMenuForm.setSchDirSurname(deanform.getSchDirSurname());
					bookMenuForm.setSchDirNetworkId(deanform.getSchDirNetworkId());
					bookMenuForm.setDeanNetworkId(deanform.getDeanNetworkId());
					bookMenuForm.setDeanSurname(deanform.getDeanSurname());
					bookMenuForm.setDeputydean1NetworkId(deanform.getDeputydean1NetworkId());
					bookMenuForm.setDeputydean1Surname(deanform.getDeputydean1Surname());
					bookMenuForm.setDeputydean2NetworkId(deanform.getDeputydean2NetworkId());
					bookMenuForm.setDeputydean2Surname(deanform.getDeputydean2Surname());
					bookMenuForm.setDeputydean3NetworkId(deanform.getDeputydean3NetworkId());
					bookMenuForm.setDeputydean3Surname(deanform.getDeputydean3Surname());
					bookMenuForm.setDeputydean4NetworkId(deanform.getDeputydean4NetworkId());
					bookMenuForm.setDeputydean4Surname(deanform.getDeputydean4Surname()); 	*/				
					
					// After setting the Audit Trail, now resolve the fullName of last updated User.
					if ( bookMenuForm.getLastUpdated() != null) {
						String userId = bookMenuForm.getLastUpdated().getNetworkId();
						String lastUpdatedDisplayName="";
						userDirectoryService = (UserDirectoryService) ComponentManager.get(UserDirectoryService.class);
						try{
						User lastUpdatedUserDetails = userDirectoryService.getUserByEid(userId);
						 lastUpdatedDisplayName = lastUpdatedUserDetails.getDisplayName();
						}catch (UserNotDefinedException e) {
							lastUpdatedDisplayName="User Unknown";
						}
						bookMenuForm.setDisplayAuditTrailName(lastUpdatedDisplayName);
					}				
					bookMenuForm.setCourseNote(dao.getCourseNote(bookMenuForm.getCourseId(), bookMenuForm.getAcadyear(),bookMenuForm.getTypeOfBookList()));
					try {
						bookMenuForm.setBookList(dao.getBookList(bookMenuForm.getCourseId(),bookMenuForm.getAcadyear(),bookMenuForm.getTypeOfBookList()));
					
					} catch (Exception ex){
						ex.printStackTrace();
					}		   
					    
					    return mapping.findForward("codAuthorize");
			      }
		 }
		 return mapping.findForward("schdirmainview");
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
		List deptList = new ArrayList();
		deptList = authDao.getSchDirDepts(Integer.parseInt(bookMenuForm.getPersonnelNr()));
		session.setAttribute("schdirdepts", deptList);
		
		if(bookMenuForm.getSearchOption().length()==0){			
			return mapping.findForward("sddisplayoptions");
		}
		if(!(bookMenuForm.getSearchOption().length()==0)||bookMenuForm.getSearchOption().equals("")){
		if(bookMenuForm.getDepartment().equals("")||bookMenuForm.getDepartment().equals("-1")){
			bookMenuForm.setSearchOption("");
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage",
								"Please select a department"));
			addErrors(request,messages);				
		}else{
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
						return mapping.findForward("sddisplayoptions");
						}
				 return mapping.findForward("courselist");	
			}else if(bookMenuForm.getSearchOption().equals("allmodules")){
				bookMenuForm.setStatus("List of all modules (codes only) with status by department");
				courseList=authDao.getAllModulesforDept(bookMenuForm.getDepartment(), bookMenuForm.getAcadyear(),bookMenuForm.getTypeOfBookList());
				 bookMenuForm.setCourselist(courseList);
				 return mapping.findForward("courselist");	
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
						return mapping.findForward("sddisplayoptions");
						}
				 return mapping.findForward("courselist");	
			}
		}
		}
		    return mapping.findForward("sddisplayoptions");
	}
	public ActionForward authorizepermodule(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		BookMenuForm bookMenuForm=(BookMenuForm) form;	
		ActionMessages messages = new ActionMessages();	
		bookMenuForm.setCourseId("");
		 
	 return mapping.findForward("searchcourse");
	}
	
	public ActionForward allmoduleslist(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		BookMenuForm bookMenuForm=(BookMenuForm) form;	
		ActionMessages messages = new ActionMessages();	
		AuthorisationDAO authDao = new AuthorisationDAO();
		CourseDAO dao = new CourseDAO();
		HttpSession session = request.getSession(true);
		bookMenuForm.getTypeOfBookList();

		String persno=authDao.getPersonnelNr(bookMenuForm.getNetworkId().toUpperCase());		
		Boolean num=verityNumber(persno);
		bookMenuForm.setPersonnelNr(persno);
	/*	String head="";
		head=authDao.getSchoolDirector(Integer.parseInt(persno));	
	    if(head.equals("schoolDir")){
			bookMenuForm.setPresCourseList(authDao.getSChDirCourseList(bookMenuForm.getAcadyear(), "P",2,persno));
			bookMenuForm.setRecCourseList(authDao.getSChDirCourseList(bookMenuForm.getAcadyear(), "R",2,persno));
			bookMenuForm.setEresCourseList(authDao.getSChDirCourseList(bookMenuForm.getAcadyear(), "E",2,persno));		
		}else{
			bookMenuForm.setPresCourseList(authDao.getStandInSchoolDirList(bookMenuForm.getAcadyear(), "P",2,persno));
			bookMenuForm.setRecCourseList(authDao.getStandInSchoolDirList(bookMenuForm.getAcadyear(), "R",2,persno));
			bookMenuForm.setEresCourseList(authDao.getStandInSchoolDirList(bookMenuForm.getAcadyear(), "E",2,persno));		
		}*/
		bookMenuForm.setPresCourseList(authDao.getSChDirCourseList(bookMenuForm.getAcadyear(), "P",2,persno));
		bookMenuForm.setRecCourseList(authDao.getSChDirCourseList(bookMenuForm.getAcadyear(), "R",2,persno));
		bookMenuForm.setEresCourseList(authDao.getSChDirCourseList(bookMenuForm.getAcadyear(), "E",2,persno));	
		
		if(bookMenuForm.getPresCourseList().size()==0 && bookMenuForm.getRecCourseList().size()==0 && bookMenuForm.getEresCourseList().size()==0){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage",
								"There are no booklists to authorize."));
			addErrors(request,messages);
			return mapping.findForward("authRequestList");
		}
		return mapping.findForward("authRequestList");
	}
	
	
	
/*	status and meaning
	0-Booklist open for editing
	1-sumitted for authorization
	2-authorized by COD
	3-No books
	4-authorised by school director
	5-authorized by Dean*/
	private boolean authenticateUser(BookMenuForm bookMenuForm,String user) throws Exception {
			    ActionMessages messages = new ActionMessages();	
			    AuthorisationDAO authDao = new AuthorisationDAO();
		        String personelNo=authDao.getPersonnelNr(bookMenuForm.getNetworkId().toUpperCase());		
		        Boolean num=verityNumber(personelNo);
		        bookMenuForm.setPersonnelNr(personelNo);
		        boolean authenticated=false;
		        if (num){
				   bookMenuForm.setUserName(authDao.getUserCode(personelNo));
				   if(user.equals("COD")){
					     authenticated=validateCod(personelNo,authDao);
				   }else{
					     authenticated=validateSd(personelNo,authDao);
				   }
		        }
		        return authenticated;
	}
	private void addErrorMessage(ActionMessages messages,HttpServletRequest request){
		           messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage",
								"You are not authorised to use this tool"));
			       addErrors(request,messages);
	}
	private boolean validateCod(String personelNo,AuthorisationDAO authDao){
		              String cod="",actingCod="";
		              boolean authorised=true;
		        	   try {
		   				    cod = authDao.getCODPersno(Integer.parseInt(personelNo));	
		   				    actingCod =authDao.getActingCODDept(Integer.parseInt(personelNo));		
		   			  }catch (Exception ex) {}
		   			  if (cod.length()==0 && actingCod.length()==0){
		   				   authorised=false;
		   			  }
		   		      return  authorised;  
		   			   
	}
	private boolean validateSd(String personelNo,AuthorisationDAO authDao){
               String sd="";
               boolean authorised=true;
  	           try {
				    sd =authDao.getSchoolDirector(Integer.parseInt(personelNo));		
			   }catch (Exception ex) {}
			   if (sd.length()==0){
				   authorised=false;
			   }
		       return  authorised;  
    }
	public String getAuthRequestList(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		BookMenuForm bookMenuForm=(BookMenuForm) form;		
		ActionMessages messages = new ActionMessages();	
		
		AuthorisationDAO authDao = new AuthorisationDAO();
		String head="";
		String actingCod="";
		List actingDeptList = new ArrayList();
		
		String persno=authDao.getPersonnelNr(bookMenuForm.getNetworkId().toUpperCase());		
		Boolean num=verityNumber(persno);
		bookMenuForm.setPersonnelNr(persno);
		if (num.equals(false)){				
			messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage",
									"You are not authorised to use this tool"));
				addErrors(request,messages);
				return "viewBookMenu";
			}
		bookMenuForm.setUserName(authDao.getUserCode(persno));
		 
		
		if(bookMenuForm.getSchDirector().equals("SD")){
			//verify the login person is school director or deputy dean
			head=authDao.getSchoolDirector(Integer.parseInt(persno));		
			if (head.length()==0){				
				messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage",
										"You are not authorised to use this tool"));
					addErrors(request,messages);
					return "viewBookMenu";
				}

			return "schdirmainview";	
			
		}else{
			try {
				head = authDao.getCODPersno(Integer.parseInt(persno));	
				actingCod =authDao.getActingCODDept(Integer.parseInt(persno));		
			}catch (NumberFormatException ex) {
				
			}
			if (head.length()==0 && actingCod.length()==0){
				
				messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage",
										"You are not authorised to use this tool."));
					addErrors(request,messages);
					return "viewBookMenu";
				}
			
			bookMenuForm.setPresCourseList(authDao.getCodCourseList(bookMenuForm.getAcadyear(), "P",1,persno));
			bookMenuForm.setRecCourseList(authDao.getCodCourseList(bookMenuForm.getAcadyear(), "R",1,persno));
			bookMenuForm.setEresCourseList(authDao.getCodCourseList(bookMenuForm.getAcadyear(), "E",1,persno));
		}		
		if(bookMenuForm.getPresCourseList().size()==0 && bookMenuForm.getRecCourseList().size()==0 && bookMenuForm.getEresCourseList().size()==0){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage",
								"There are no booklists to authorize."));
			addErrors(request,messages);
			return "authRequestList";
		}
		return "authRequestList";
	}
	
	public boolean verityNumber(String persno){
		try {
		Integer.parseInt(persno);
		return true;
		} catch (NumberFormatException nfe){
		return false;
		}
		} 
	public ActionForward codAuthContinue(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		BookMenuForm bookMenuForm=(BookMenuForm) form;
		ActionMessages messages = new ActionMessages();	
		AuthorisationDAO authDao = new AuthorisationDAO();
		CourseDAO dao = new CourseDAO();
		bookMenuForm.setAuthComment("");
		bookMenuForm.setDeanNetworkId("");			
		bookMenuForm.setDeputydean1NetworkId("");		
		bookMenuForm.setDeputydean2NetworkId("");			
		bookMenuForm.setDeputydean3NetworkId("");			
		bookMenuForm.setDeputydean4NetworkId("");	
		bookMenuForm.setSelectedPerson("");
		
		if(bookMenuForm.getPresCourseList().size()==0 && bookMenuForm.getRecCourseList().size()==0 && bookMenuForm.getEresCourseList().size()==0){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage",
								"There are no booklists to authorize."));
			addErrors(request,messages);
			return mapping.findForward("authRequestList");
		}
		
		
		String selectedCourse=bookMenuForm.getSelectedCourse();				
		if(bookMenuForm.getSelectedCourse().equals("")){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage",
								"Please select a module code for authorization."));
			addErrors(request,messages);
			  return mapping.findForward("authRequestList");
		}
		if(bookMenuForm.getSchDirector().equals("SD")){
			bookMenuForm.setBookListStatus("Booklist submitted for authorization");
		}else{
			bookMenuForm.setBookListStatus("Booklist submitted for authorization");
		}

		bookMenuForm.setCourseId(selectedCourse.substring(1));
		bookMenuForm.setTypeOfBookList(selectedCourse.substring(0,1));
		bookMenuForm.setLastUpdated(dao.getLastUpdated(bookMenuForm.getCourseId(), bookMenuForm.getAcadyear(),bookMenuForm.getTypeOfBookList()));
		
		
		CollegeDeptDetails coldept = new CollegeDeptDetails();
		coldept = dao.getCollegeDeptDetails(bookMenuForm.getCourseId());
		bookMenuForm.setCollege(coldept.getCollege());
		bookMenuForm.setDepartment(coldept.getDepartment());
		
		DeanForm deanform=new DeanForm();
		if(bookMenuForm.getSchDirector().equals("COD")){
		//get the school director details
		deanform=authDao.selectSchDir(bookMenuForm.getCourseId(),bookMenuForm.getSchDirector());
		bookMenuForm.setSchDirSurname(deanform.getSchDirSurname());
		bookMenuForm.setSchDirNetworkId(deanform.getSchDirNetworkId());
		//get the standin school directors list
		List standSchDirList = authDao.selectStandinSchDir(bookMenuForm.getCourseId());
		bookMenuForm.setStandInSchools(standSchDirList); 
		}
		
		if(bookMenuForm.getSchDirector().equals("SD")){
			//get the dean details
			deanform = authDao.getDean(bookMenuForm.getCourseId(), bookMenuForm.getSchDirector());
			bookMenuForm.setDeanNetworkId(deanform.getDeanNetworkId());
			bookMenuForm.setDeanSurname(deanform.getDeanSurname());
			//get deputy deans list selectDeputyDeans
			List deputyDeanList = authDao.selectDeputyDeans(bookMenuForm.getCourseId());
			bookMenuForm.setDeputyDeanList(deputyDeanList); 
						
/*			deanform = authDao.selectDean(bookMenuForm.getCourseId(), bookMenuForm.getSchDirector());
		bookMenuForm.setSchDirSurname(deanform.getSchDirSurname());
		bookMenuForm.setSchDirNetworkId(deanform.getSchDirNetworkId());
		
		bookMenuForm.setDeanNetworkId(deanform.getDeanNetworkId());
		bookMenuForm.setDeanSurname(deanform.getDeanSurname());
		bookMenuForm.setDeputydean1NetworkId(deanform.getDeputydean1NetworkId());
		bookMenuForm.setDeputydean1Surname(deanform.getDeputydean1Surname());
		bookMenuForm.setDeputydean2NetworkId(deanform.getDeputydean2NetworkId());
		bookMenuForm.setDeputydean2Surname(deanform.getDeputydean2Surname());
		bookMenuForm.setDeputydean3NetworkId(deanform.getDeputydean3NetworkId());
		bookMenuForm.setDeputydean3Surname(deanform.getDeputydean3Surname());
		bookMenuForm.setDeputydean4NetworkId(deanform.getDeputydean4NetworkId());
		bookMenuForm.setDeputydean4Surname(deanform.getDeputydean4Surname());  */         
		}
		
		
		// After setting the Audit Trail, now resolve the fullName of last updated User.
		if ( bookMenuForm.getLastUpdated() != null) {
			String userId = bookMenuForm.getLastUpdated().getNetworkId();
			String lastUpdatedDisplayName="";
			userDirectoryService = (UserDirectoryService) ComponentManager.get(UserDirectoryService.class);
			try{
			User lastUpdatedUserDetails = userDirectoryService.getUserByEid(userId);
			 lastUpdatedDisplayName = lastUpdatedUserDetails.getDisplayName();
			}catch (UserNotDefinedException e) {
				lastUpdatedDisplayName="User Unknown";
			}
			bookMenuForm.setDisplayAuditTrailName(lastUpdatedDisplayName);
		}
	
		bookMenuForm.setCourseNote(dao.getCourseNote(bookMenuForm.getCourseId(), bookMenuForm.getAcadyear(),bookMenuForm.getTypeOfBookList()));
	
		try {
			bookMenuForm.setBookList(dao.getBookList(bookMenuForm.getCourseId(),bookMenuForm.getAcadyear(),bookMenuForm.getTypeOfBookList()));
		
		} catch (Exception ex){
			ex.printStackTrace();
		}
		   
		    
		    return mapping.findForward("codAuthorize");
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
		
		//Sifiso Changes: 2017/08/16:LU_MA_BLU_06:Requirement 54:Tutor Booklist
		//redirect to DISS authorization page
		if( bookMenuForm.getDissAuthTracker() == 1 )
			return dissAuthorizeBtn( mapping, form, request, response);
		
		//Found errors in Authorization pages-handle them
		if( bookMenuForm.getDissAuthTracker() == -1  )
			return mapping.findForward("viewBookMenu");
		
		if(bookMenuForm.getAuthComment().length()>200){
		messages.add(ActionMessages.GLOBAL_MESSAGE,
				new ActionMessage("message.generalmessage",
							"Comment should not be more than 200 characters."));
		addErrors(request,messages);
		return mapping.findForward("codAuthorize");
		}
		
		if(bookMenuForm.getSchDirector().equals("COD")){
			if(bookMenuForm.getSelectedPerson()==null || bookMenuForm.getSelectedPerson().length()==0){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
			        new ActionMessage("message.generalmessage", "Please select School Director or Stand in School Director to send authorization request."));
				addErrors(request, messages);
			    return mapping.findForward("codAuthorize");
			    }			
		}else if(bookMenuForm.getSchDirector().equals("SD")){
			if(bookMenuForm.getSelectedPerson()==null || bookMenuForm.getSelectedPerson().length()==0){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
		        new ActionMessage("message.generalmessage", "Please select Dean or Deputy Dean to send authorization request."));
			addErrors(request, messages);
		    return mapping.findForward("codAuthorize");
		    }        }			
		
		String tempemail="@unisa.ac.za";
		String email2="";
		
		//get dean or deputy dean network id
		if (bookMenuForm.getSchDirector().equals("SD")) {
			if (bookMenuForm.getSelectedPerson().equals("Dean")) {
				email2 = bookMenuForm.getDeanNetworkId() + tempemail;
			} else {
				String deputyDean = bookMenuForm.getSelectedPerson();
				email2 = deputyDean + tempemail;
			}
		}
		 //get the school director or stand in school director network id
		if (bookMenuForm.getSchDirector().equals("COD")) {
			if (bookMenuForm.getSelectedPerson().equals("SchDirector")) {
				String sdUserCode = authDao.getSchDirectorUserCode(bookMenuForm
						.getCourseId());
				email2 = sdUserCode + tempemail;
			} else {
				String standinschDir = bookMenuForm.getSelectedPerson();
				email2 = standinschDir + tempemail;
			}

		}	
		  if(bookMenuForm.getSchDirector().equals("COD")){
			
			String novellUserCode=dao.getRequestedPerson(bookMenuForm.getCourseId(), bookMenuForm.getAcadyear(),bookMenuForm.getTypeOfBookList(),"Book list confirmed");				
			dao.updateBookListStatus("2", bookMenuForm.getCourseId(), bookMenuForm.getAcadyear(),bookMenuForm.getNetworkId(),bookMenuForm.getTypeOfBookList());				
			String email=novellUserCode;
			email += "@unisa.ac.za";			
		
			updateAuditTrail(bookMenuForm, "Booklist authorized by COD"+ bookMenuForm.getAuthComment());
			
			//send an email to requested person for authorization			
			String emailSubject;
			String emailMessage;
			if(bookMenuForm.getTypeOfBookList().equals("P")){
				
			 emailSubject = "CoD Approval of Prescribed Booklist for "+bookMenuForm.getCourseId()+" for "+bookMenuForm.getAcadyear();
			 emailMessage = "NB: This is an automated message - do not reply to this e-mail. <br>"+
					"<p>"+
					"The Prescribed book list for "+bookMenuForm.getCourseId()+" for "+bookMenuForm.getAcadyear()+" has been approved. <br>"+				
					"<p>"+
					"The list has now been sent for further verification by the School Director/Deputy Dean in the College." +
					"<p>"+
					"Comment from COD: "+bookMenuForm.getAuthComment()+
					"<p>"+
					"Regards <br>" +
					"myUnisa Support Team on behalf of College Tuition Committee";
			}else if(bookMenuForm.getTypeOfBookList().equals("R")){
				 emailSubject = "CoD Approval of Recommended Booklist for "+bookMenuForm.getCourseId()+" for "+bookMenuForm.getAcadyear();
				 emailMessage = "NB: This is an automated message - do not reply to this e-mail. <br>"+
						"<p>"+
						"The Recommended book list for "+bookMenuForm.getCourseId()+" for "+bookMenuForm.getAcadyear()+" has been approved. <br>"+				
						"<p>"+
						"The list has now been sent for further verification by the School Director/Deputy Dean in the College." +
						"<p>"+
						"Comment from COD: "+bookMenuForm.getAuthComment()+
						"<p>"+
						"Regards <br>" +
						"myUnisa Support Team on behalf of College Tuition Committee";
			}else{
				 emailSubject = "CoD Approval of eReserves List for "+bookMenuForm.getCourseId()+" for "+bookMenuForm.getAcadyear();
				 emailMessage = "NB: This is an automated message - do not reply to this e-mail. <br>"+
						"<p>"+
						"The eReserves list for "+bookMenuForm.getCourseId()+" for "+bookMenuForm.getAcadyear()+" has been approved. <br>"+				
						"<p>"+
						"The list has now been sent for further verification by the School Director/Deputy Dean in the College." +
						"<p>"+
						"Comment from COD: "+bookMenuForm.getAuthComment()+
						"<p>"+
						"Regards <br>" +
						"myUnisa Support Team on behalf of College Tuition Committee";
			}
			
			sendEmail(emailSubject, emailMessage, email);	
			
			//send an email to School director 
			String emailSubject2;
			String emailMessage2;
			if(bookMenuForm.getTypeOfBookList().equals("P")){				
					 emailSubject2 = "Request Authorization of Prescribed Booklist for "+bookMenuForm.getCourseId()+" for "+bookMenuForm.getAcadyear();
					 emailMessage2 = "NB: This is an automated message - do not reply to this e-mail. <br>"+
							"<p>"+
							bookMenuForm.getUserName()+" request authorization of the "+bookMenuForm.getAcadyear()+" prescribed book list for "+bookMenuForm.getCourseId()+".<br>"+				
							"<p>"+
							"To view this book list, go to the myUnisa site at https://my.unisa.ac.za/portal, login and select the <b>Course Admin</b> site. " +
							"The Book List Management tool is accessible from the left navigation. Click on this tool and then select the <b>School Director Authorization</b> link." +
							"<p>"+
							"Regards <br>" +
							"myUnisa Support Team on behalf of College Tuition Committee";
					}else if(bookMenuForm.getTypeOfBookList().equals("R")){
						emailSubject2 = "Request Authorization of Recommended Booklist for "+bookMenuForm.getCourseId()+" for "+bookMenuForm.getAcadyear();
						 emailMessage2 = "NB: This is an automated message - do not reply to this e-mail. <br>"+
								"<p>"+
								bookMenuForm.getUserName()+" request authorization of the "+bookMenuForm.getAcadyear()+" Recommended book list for "+bookMenuForm.getCourseId()+".<br>"+				
								"<p>"+
								"To view this book list, go to the myUnisa site at https://my.unisa.ac.za/portal, login and select the <b>Course Admin</b> site. " +
								"The Book List Management tool is accessible from the left navigation. Click on this tool and then select the <b>School Director Authorization</b> link." +
								"<p>"+
								"Regards <br>" +
								"myUnisa Support Team on behalf of College Tuition Committee";
					}else{
						emailSubject2 = "Request Authorization of eReserves List for "+bookMenuForm.getCourseId()+" for "+bookMenuForm.getAcadyear();
						 emailMessage2 = "NB: This is an automated message - do not reply to this e-mail. <br>"+
								"<p>"+
								bookMenuForm.getUserName()+" request authorization of the "+bookMenuForm.getAcadyear()+" eReserves list for "+bookMenuForm.getCourseId()+".<br>"+				
								"<p>"+
								"To view this book list, go to the myUnisa site at https://my.unisa.ac.za/portal, login and select the <b>Course Admin</b> site. " +
								"The Book List Management tool is accessible from the left navigation. Click on this tool and then select the <b>School Director Authorization</b> link." +
								"<p>"+
								"Regards <br>" +
								"myUnisa Support Team on behalf of College Tuition Committee";
					}
			  sendEmail(emailSubject2, emailMessage2, email2);
			bookMenuForm.setSelectedCourse("-1");
			bookMenuForm.setTypeOfBookList("");
			bookMenuForm.setAcadyear("Select Academic Year");
			return mapping.findForward("viewBookMenu");			
			
		}else if(bookMenuForm.getSchDirector().equals("SD")){
						
			dao.updateBookListStatus("4", bookMenuForm.getCourseId(), bookMenuForm.getAcadyear(),bookMenuForm.getNetworkId(),bookMenuForm.getTypeOfBookList());
			String novellUserCode=dao.getRequestedPerson(bookMenuForm.getCourseId(), bookMenuForm.getAcadyear(),bookMenuForm.getTypeOfBookList(),"Booklist authorized by COD");
			String email=novellUserCode;
			email += "@unisa.ac.za";
			updateAuditTrail(bookMenuForm, "Booklist authorized by School Director"+bookMenuForm.getAuthComment());
			
			//send an email to requested person for authorization			
			String emailSubject;
			String emailMessage;
			if(bookMenuForm.getTypeOfBookList().equals("P")){
				
			 emailSubject = "School Director Approval of Prescribed Booklist for "+bookMenuForm.getCourseId()+" for "+bookMenuForm.getAcadyear();
			 emailMessage = "NB: This is an automated message - do not reply to this e-mail. <br>"+
					"<p>"+
					"The Prescribed book list for "+bookMenuForm.getCourseId()+" for "+bookMenuForm.getAcadyear()+" has been approved. <br>"+				
					"<p>"+
					"The list has now been sent for further verification by the Deputy Dean in the College." +
					"<p>"+
					"Comment from School Director: "+bookMenuForm.getAuthComment()+
					"<p>"+
					"Regards <br>" +
					"myUnisa Support Team on behalf of College Tuition Committee";
			}else if(bookMenuForm.getTypeOfBookList().equals("R")){
				 emailSubject = "School Director Approval of Recommended Booklist for "+bookMenuForm.getCourseId()+" for "+bookMenuForm.getAcadyear();
				 emailMessage = "NB: This is an automated message - do not reply to this e-mail. <br>"+
						"<p>"+
						"The Recommended book list for "+bookMenuForm.getCourseId()+" for "+bookMenuForm.getAcadyear()+" has been approved. <br>"+				
						"<p>"+
						"The list has now been sent for further verification by the Deputy Dean in the College." +
						"<p>"+
						"Comment from School Director: "+bookMenuForm.getAuthComment()+
						"<p>"+
						"Regards <br>" +
						"myUnisa Support Team on behalf of College Tuition Committee";
			}else{
				 emailSubject = "School Director Approval of eReserves List for "+bookMenuForm.getCourseId()+" for "+bookMenuForm.getAcadyear();
				 emailMessage = "NB: This is an automated message - do not reply to this e-mail. <br>"+
						"<p>"+
						"The eReserves list for "+bookMenuForm.getCourseId()+" for "+bookMenuForm.getAcadyear()+" has been approved. <br>"+				
						"<p>"+
						"The list has now been sent for further verification by the Deputy Dean in the College." +
						"<p>"+
						"Comment from School Director: "+bookMenuForm.getAuthComment()+
						"<p>"+
						"Regards <br>" +
						"myUnisa Support Team on behalf of College Tuition Committee";
			}
			
			sendEmail(emailSubject, emailMessage, email);	
			
			//send an email to Dean
			String emailSubject2;
			String emailMessage2;
			if(bookMenuForm.getTypeOfBookList().equals("P")){				
					 emailSubject2 = "Request Authorization of Prescribed Booklist for "+bookMenuForm.getCourseId()+" for "+bookMenuForm.getAcadyear();
					 emailMessage2 = "NB: This is an automated message - do not reply to this e-mail. <br>"+
							"<p>"+
							bookMenuForm.getUserName()+" request authorization of the "+bookMenuForm.getAcadyear()+" prescribed book list for "+bookMenuForm.getCourseId()+".<br>"+				
							"<p>"+
							"To view this book list, go to the myUnisa site at https://my.unisa.ac.za/portal, login and select the <b>Course Admin</b> site. " +
							"The Book List Management tool is accessible from the left navigation. Click on this tool and then select the <b>Dean Authorization</b> link." +
							"<p>"+
							"Regards <br>" +
							"myUnisa Support Team on behalf of College Tuition Committee";
					}else if(bookMenuForm.getTypeOfBookList().equals("R")){
						emailSubject2 = "Request Authorization of Recommended Booklist for "+bookMenuForm.getCourseId()+" for "+bookMenuForm.getAcadyear();
						 emailMessage2 = "NB: This is an automated message - do not reply to this e-mail. <br>"+
								"<p>"+
								bookMenuForm.getUserName()+" request authorization of the "+bookMenuForm.getAcadyear()+" Recommended book list for "+bookMenuForm.getCourseId()+".<br>"+				
								"<p>"+
								"To view this book list, go to the myUnisa site at https://my.unisa.ac.za/portal, login and select the <b>Course Admin</b> site. " +
								"The Book List Management tool is accessible from the left navigation. Click on this tool and then select the <b>Dean Authorization</b> link." +
								"<p>"+
								"Regards <br>" +
								"myUnisa Support Team on behalf of College Tuition Committee";
					}else{
						emailSubject2 = "Request Authorization of eReserves List for "+bookMenuForm.getCourseId()+" for "+bookMenuForm.getAcadyear();
						 emailMessage2 = "NB: This is an automated message - do not reply to this e-mail. <br>"+
								"<p>"+
								bookMenuForm.getUserName()+" request authorization of the "+bookMenuForm.getAcadyear()+" eReserves list for "+bookMenuForm.getCourseId()+".<br>"+				
								"<p>"+
								"To view this book list, go to the myUnisa site at https://my.unisa.ac.za/portal, login and select the <b>Course Admin</b> site. " +
								"The Book List Management tool is accessible from the left navigation. Click on this tool and then select the <b>Dean Authorization</b> link." +
								"<p>"+
								"Regards <br>" +
								"myUnisa Support Team on behalf of College Tuition Committee";
					}
			   sendEmail(emailSubject2, emailMessage2, email2);
			   bookMenuForm.setAcadyear("Select Academic Year");
			   bookMenuForm.setSelectedCourse("-1");
			   bookMenuForm.setTypeOfBookList("");
			return mapping.findForward("viewBookMenu");			
					
		}
		
		
		return mapping.findForward("viewBookMenu");
	}

	public ActionForward unAuthorize(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		BookMenuForm bookMenuForm=(BookMenuForm) form;
		ActionMessages messages = new ActionMessages();	
		CourseDAO dao = new CourseDAO();
		if(bookMenuForm.getSchDirector().equals("SD")){
		dao.updateBookListStatus("0", bookMenuForm.getCourseId(), bookMenuForm.getAcadyear(),bookMenuForm.getNetworkId(),bookMenuForm.getTypeOfBookList());
		updateAuditTrail(bookMenuForm, "Booklist Rejected by School Director "+bookMenuForm.getAuthComment());
		String novellUserCode=dao.getRequestedPerson(bookMenuForm.getCourseId(), bookMenuForm.getAcadyear(),bookMenuForm.getTypeOfBookList(),"Booklist authorized by COD");		
		String email=novellUserCode;
		email += "@unisa.ac.za";
		//send an email to requested person for authorization			
		String emailSubject;
		String emailMessage;
		if(bookMenuForm.getTypeOfBookList().equals("P")){
			
		 emailSubject = "Return of Prescribed Booklist for "+bookMenuForm.getCourseId()+" for "+bookMenuForm.getAcadyear();
		 emailMessage = "NB: This is an automated message - do not reply to this e-mail. <br>"+
				"<p>"+
				"The Prescribed book list for "+bookMenuForm.getCourseId()+" for "+bookMenuForm.getAcadyear()+" has been returned to you for further editing. <br>"+				
				"<p>"+				
				"Comment from COD: "+bookMenuForm.getAuthComment()+
				"<p>"+
				"Regards <br>" +
				"myUnisa Support Team on behalf of College Tuition Committee";
		}else if(bookMenuForm.getTypeOfBookList().equals("R")){
			 emailSubject = "Return of Recommended Booklist for "+bookMenuForm.getCourseId()+" for "+bookMenuForm.getAcadyear();
			 emailMessage = "NB: This is an automated message - do not reply to this e-mail. <br>"+
					"<p>"+
					"The Recommended book list for "+bookMenuForm.getCourseId()+" for "+bookMenuForm.getAcadyear()+" has been returned to you for further editing. <br>"+				
					"<p>"+
					"Comment from School Director: "+bookMenuForm.getAuthComment()+
					"<p>"+
					"Regards <br>" +
					"myUnisa Support Team on behalf of College Tuition Committee";
		}else{
			 emailSubject = "Return of eReserves List for "+bookMenuForm.getCourseId()+" for "+bookMenuForm.getAcadyear();
			 emailMessage = "NB: This is an automated message - do not reply to this e-mail. <br>"+
					"<p>"+
					"The eReserves list for "+bookMenuForm.getCourseId()+" for "+bookMenuForm.getAcadyear()+" has been returned to you for further editing. <br>"+				
					"<p>"+
					"Comment from School Director: "+bookMenuForm.getAuthComment()+
					"<p>"+
					"Regards <br>" +
					"myUnisa Support Team on behalf of College Tuition Committee";
		}
		
		sendEmail(emailSubject, emailMessage, email);	
		

		return mapping.findForward("viewBookMenu");		
		
		}else if(bookMenuForm.getSchDirector().equals("COD")){
		dao.updateBookListStatus("0", bookMenuForm.getCourseId(), bookMenuForm.getAcadyear(),bookMenuForm.getNetworkId(),bookMenuForm.getTypeOfBookList());
     	String novellUserCode=dao.getRequestedPerson(bookMenuForm.getCourseId(), bookMenuForm.getAcadyear(),bookMenuForm.getTypeOfBookList(),"Book list confirmed");
		String email=novellUserCode;
		email += "@unisa.ac.za";
		
		updateAuditTrail(bookMenuForm, "Booklist rejected by COD"+ bookMenuForm.getAuthComment());
		
		//send an email to requested person for authorization			
		String emailSubject;
		String emailMessage;
		if(bookMenuForm.getTypeOfBookList().equals("P")){
			
		 emailSubject = "Return of Prescribed Booklist for "+bookMenuForm.getCourseId()+" for "+bookMenuForm.getAcadyear();
		 emailMessage = "NB: This is an automated message - do not reply to this e-mail. <br>"+
				"<p>"+
				"The Prescribed book list for "+bookMenuForm.getCourseId()+" for "+bookMenuForm.getAcadyear()+" has been returned to you for further editing. <br>"+				
				"<p>"+
				"Comment from COD: "+bookMenuForm.getAuthComment()+
				"<p>"+
				"Regards <br>" +
				"myUnisa Support Team on behalf of College Tuition Committee";
		}else if(bookMenuForm.getTypeOfBookList().equals("R")){
			 emailSubject = "Return of Recommended Booklist for "+bookMenuForm.getCourseId()+" for "+bookMenuForm.getAcadyear();
			 emailMessage = "NB: This is an automated message - do not reply to this e-mail. <br>"+
					"<p>"+
					"The Recommended book list for "+bookMenuForm.getCourseId()+" for "+bookMenuForm.getAcadyear()+" has been returned to you for further editing. <br>"+				
					"<p>"+
					"Comment from COD: "+bookMenuForm.getAuthComment()+
					"<p>"+
					"Regards <br>" +
					"myUnisa Support Team on behalf of College Tuition Committee";
		}else{
			 emailSubject = "Return of eReserves List for "+bookMenuForm.getCourseId()+" for "+bookMenuForm.getAcadyear();
			 emailMessage = "NB: This is an automated message - do not reply to this e-mail. <br>"+
					"<p>"+
					"The eReserves list for "+bookMenuForm.getCourseId()+" for "+bookMenuForm.getAcadyear()+" has been returned to you for further editing.<br>"+				
					"<p>"+
					"Comment from COD: "+bookMenuForm.getAuthComment()+
					"<p>"+
					"Regards <br>" +
					"myUnisa Support Team on behalf of College Tuition Committee";
		}
		
		sendEmail(emailSubject, emailMessage, email);	
		return mapping.findForward("viewBookMenu");	
		}
		return mapping.findForward("viewBookMenu");			
	}

	
	public ActionForward sendback(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		BookMenuForm bookMenuForm=(BookMenuForm) form;
		ActionMessages messages = new ActionMessages();	
		
		//Sifiso Changes: 2017/08/16:LU_MA_BLU_06:Requirement 54:Tutor Booklist
		//redirect to DISS send back page
		if( bookMenuForm.getDissAuthTracker() == 1 )
			return dissSendbackBtn( mapping, form, request, response);
		
		//Found errors in Authorization pages-handle them
		if( bookMenuForm.getDissAuthTracker() == -1  )
			return mapping.findForward("viewBookMenu");
		
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
		
		return mapping.findForward("confirmSendBack");
	}
	public ActionForward cancel (
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response){
		    	BookMenuForm bookMenuForm=(BookMenuForm) form;
		        bookMenuForm.setDeanNetworkId("");
		        bookMenuForm.setDeputydean1NetworkId("");		
		        bookMenuForm.setDeputydean2NetworkId("");			
		        bookMenuForm.setDeputydean3NetworkId("");			
		        bookMenuForm.setDeputydean4NetworkId("");	
		if(bookMenuForm.getCancelOption().equals("TOMAINVIEW")){
			    bookMenuForm.setSelectedCourse("-1");
			    bookMenuForm.setTypeOfBookList("");
			    bookMenuForm.setAcadyear("Select Academic Year");
			    return mapping.findForward("viewBookMenu");
		}else if(bookMenuForm.getCancelOption().equals("requestedList")){
		        	return mapping.findForward("authRequestList");
		}else if(bookMenuForm.getCancelOption().equals("prevstep")){
		        	bookMenuForm.setDepartment("");
			        bookMenuForm.setSearchOption("");
			        return mapping.findForward("sddisplayoptions");
		}else if(bookMenuForm.getCancelOption().equals("codSelectAcadyear")){
			       bookMenuForm.setDepartment("");
			       bookMenuForm.setSearchOption("");
			       return mapping.findForward("codselectacadyear");
		}else if(bookMenuForm.getCancelOption().equals("mainview")){
			        if(bookMenuForm.getSchDirector().equals("SD")){
			             bookMenuForm.setContinueOption("");
			             return mapping.findForward("schdirmainview");
			        }else{
				           bookMenuForm.setSelectedCourse("-1");
				           bookMenuForm.setAcadyear("Select Academic Year");
				           return mapping.findForward("viewBookMenu");
			        }
		}
		bookMenuForm.setAcadyear("Select Academic Year");
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
	
	//Sifiso Changes: 2017/08/16:LU_MA_BLU_06:Requirement 54:Tutor Booklist: DISS Authorization-Landing page		
	public ActionForward dissAuthRequestList(ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		    BookMenuForm bookMenuForm=(BookMenuForm) form;	
		    ActionMessages messages = new ActionMessages();
			AuthorisationDAO authDao = new AuthorisationDAO();
			CourseDAO courseDao = new CourseDAO();
			String persno=authDao.getPersonnelNr( bookMenuForm.getNetworkId().toUpperCase() );	
			
			bookMenuForm.setDissAuthTracker(1);	//set-we are going to authorization page
			
	       if( !authDao.tutorCountsExist( "P",0,0 ) ){
	               messages.add( ActionMessages.GLOBAL_MESSAGE,new ActionMessage("message.generalmessage",
					"There are no Tutor booklists to authorize.") );
	              addErrors( request,messages );
	              bookMenuForm.setCancelOption( "TOMAINVIEW" );
	              return mapping.findForward("viewBookMenu");
	        }
	       try{
		         //get all details of tutor booklist: P = Prescribed; 0 = not authorized yet
	 			List<String> tutorBookList = authDao.getTutorBookList("P",0);
	 			
	 			if ( tutorBookList.size() == 0 ){
	 				messages.add( ActionMessages.GLOBAL_MESSAGE,new ActionMessage("message.generalmessage",
							"There are no Tutor booklists to authorize.") );
		               addErrors( request,messages );
		               bookMenuForm.setCancelOption( "TOMAINVIEW" );
		               return mapping.findForward("viewBookMenu");
	 			}
	 			
	 			Iterator tbIterator = tutorBookList.iterator();	
	 			int tutorBooklistSize = tutorBookList.size();
	 			int iterationCount = 0;
	 			
	 			//Just get next element (first row from DB) from iterator-DON'T iterate through everything
	 			//Next time this part runs, it will be second row from DB being iterated and so forth
	 			while ( tutorBooklistSize != 0 && tbIterator.hasNext() ){
	 				iterationCount += 1;
					ListOrderedMap details = (ListOrderedMap) tbIterator.next();
					String courseId = details.get("mk_study_unit_code").toString();
					String acadYear = details.get("mk_academic_year").toString();
					String bookType = details.get("book_status").toString();
					String tutorCount = details.get("tutor_count").toString();
					String bookStatus = details.get("status").toString();
					
					bookMenuForm.setCourseId( courseId );
					bookMenuForm.setAcadyear( acadYear );
					bookMenuForm.setTypeOfBookList( bookType );
					bookMenuForm.setTutorCount( tutorCount );
					bookMenuForm.setStatus( bookStatus );
					//set last updated transaction time from auditTrail
		 			bookMenuForm.setLastUpdated( courseDao.getLastUpdated(courseId, acadYear,bookType) );
	 			
		 			//determine if authorization is required for rejected items
	 				//If there are no more items in list, exit loop
		 			String lastUpdateNote = bookMenuForm.getLastUpdated().getUpdateInfo();
		 			if( lastUpdateNote.startsWith("(Tutor count rejected by DIIS)") && ((tutorBooklistSize-iterationCount)==0) ){
		 				tutorBooklistSize = 0;
		 				//we already rejected and no new tutor count was submitted so
		 				//no authorization required for this one
		 				messages.add( ActionMessages.GLOBAL_MESSAGE,new ActionMessage("message.generalmessage",
								"There are no Tutor booklists to authorize.") );
			               addErrors( request,messages );
			              
			               bookMenuForm.setCancelOption( "TOMAINVIEW" );
			               bookMenuForm.setAcadyear("Select Academic Year");
						   bookMenuForm.setSelectedCourse("-1");
						   bookMenuForm.setTypeOfBookList("");
			               return mapping.findForward("viewBookMenu");
		 			}else if( lastUpdateNote.startsWith("Tutor Count") ){
		 				tutorBooklistSize = 0;
		 			}
		 			
		 			tutorBooklistSize -= 1;
	 			}
	 			
	 			//we rejected this one, no need to go back to it!
	 			String lastUpdateNote = bookMenuForm.getLastUpdated().getUpdateInfo();
	 			if( lastUpdateNote.startsWith("(Tutor count rejected by DIIS)") ){
	 				messages.add( ActionMessages.GLOBAL_MESSAGE,new ActionMessage("message.generalmessage",
							"There are no Tutor booklists to authorize.") );
		               addErrors( request,messages );
		              
		               bookMenuForm.setCancelOption( "TOMAINVIEW" );
		               bookMenuForm.setAcadyear("Select Academic Year");
					   bookMenuForm.setSelectedCourse("-1");
					   bookMenuForm.setTypeOfBookList("");
		               return mapping.findForward("viewBookMenu");
	 			}
	 			
				// After setting the Audit Trail, now resolve the fullName of last updated User.
				if ( bookMenuForm.getLastUpdated() != null) {
					String userId = bookMenuForm.getLastUpdated().getNetworkId();
					String lastUpdatedDisplayName="";
					userDirectoryService = (UserDirectoryService) ComponentManager.get(UserDirectoryService.class);
					try{
					User lastUpdatedUserDetails = userDirectoryService.getUserByEid(userId);
					 lastUpdatedDisplayName = lastUpdatedUserDetails.getDisplayName();
					}catch (UserNotDefinedException e) {
						lastUpdatedDisplayName="User Unknown";
					}
					bookMenuForm.setDisplayAuditTrailName(lastUpdatedDisplayName);
				}	
	       }catch ( Exception ex ) {
				messages.add( ActionMessages.GLOBAL_MESSAGE,new ActionMessage("message.generalmessage",
						"Something went wrong! Please contact ICT!") );
				addErrors( request,messages );
				ex.printStackTrace();
				
				bookMenuForm.setAcadyear("Select Academic Year");
			    bookMenuForm.setSelectedCourse("-1");
			    bookMenuForm.setTypeOfBookList("");
				return mapping.findForward("viewBookMenu");
	       }
           return mapping.findForward( "dissAuthorize" );
    }
	
	//Sifiso Changes: 2017/08/16:LU_MA_BLU_06:Requirement 54:Tutor Booklist: DISS Authorization: Authorize button
	public ActionForward dissAuthorizeBtn(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		BookMenuForm bookMenuForm=(BookMenuForm) form;
		ActionMessages messages = new ActionMessages();	
		CourseDAO dao = new CourseDAO();
		
		bookMenuForm.setDissAuthTracker(0);	//reset-we have reached authorization page
		
		if( bookMenuForm.getAuthComment().length()>200 ){
			messages.add( ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage",
								"Comment should not be more than 200 characters.") );
			addErrors( request,messages );
			return mapping.findForward( "dissAuthorize" );
		}
		
		try{
			//update tutor_status
			int updateResult = 0;
			updateResult = dao.updateTutorStatus( 1, bookMenuForm.getCourseId(), bookMenuForm.getAcadyear(), 
					bookMenuForm.getTypeOfBookList(), Integer.parseInt( bookMenuForm.getStatus() ), 
					Integer.parseInt(bookMenuForm.getTutorCount()) );
			
			if( updateResult > 0 ){
				String novellUserCode=dao.getRequestedPerson( bookMenuForm.getCourseId(), bookMenuForm.getAcadyear(),
						bookMenuForm.getTypeOfBookList(),"Tutor Count "+bookMenuForm.getTutorCount()+" Added" );			
				String email = novellUserCode;		
				
				//update audit trail for authorization
				String auditUpdateInfo = bookMenuForm.getAuthComment();
				auditUpdateInfo = auditUpdateInfo.replace("'", "");
				auditUpdateInfo = auditUpdateInfo.replace("\"", "");
				
				updateAuditTrail( bookMenuForm, "(Tutor count authorized by DIIS) "+ auditUpdateInfo );
				
				//send an email to requested person for authorization			
				String emailSubject;
				String emailMessage;				
				emailSubject = "DISS Approval of Prescribed Booklist Tutor Count for "+bookMenuForm.getCourseId()+" for "+
						bookMenuForm.getAcadyear();
				emailMessage = "NB: This is an automated message - do not reply to this e-mail. <br>"+
						"<p>"+
						"The Prescribed book list Tutor Count for "+bookMenuForm.getCourseId()+" for "+bookMenuForm.getAcadyear()+
						" has been approved. <br>"+				
						"<p>"+
						"Comment from DISS: "+bookMenuForm.getAuthComment()+
						"<p>"+
						"Regards <br>" +
						"myUnisa Support Team";
				
				if( email.isEmpty() ){
					messages.add( ActionMessages.GLOBAL_MESSAGE,new ActionMessage("message.generalmessage","No email address available for requestor!") );
					addErrors( request,messages );
				}else{
					email += "@unisa.ac.za";	
					sendEmail(emailSubject, emailMessage, email);
					
					messages.add( ActionMessages.GLOBAL_MESSAGE,new ActionMessage("message.dissauthsuccess",
							bookMenuForm.getCourseId(),bookMenuForm.getAcadyear()) );
					addMessages(request,messages);
				}
			}else{
				messages.add( ActionMessages.GLOBAL_MESSAGE,new ActionMessage("message.generalmessage",
						"Authorization Failed! Please contact ICT!") );
				addErrors( request,messages ) ;
			}
		}catch ( Exception ex ) {
			messages.add( ActionMessages.GLOBAL_MESSAGE,new ActionMessage("message.generalmessage",
					"Authorization Failed! Please contact ICT!") );
			addErrors( request,messages );
			bookMenuForm.setDissAuthTracker(-1);		//set-we found error in authorization page
			ex.printStackTrace();
			return mapping.findForward( "dissAuthorize" );
		}
		
		bookMenuForm.setAcadyear("Select Academic Year");
	    bookMenuForm.setSelectedCourse("-1");
	    bookMenuForm.setTypeOfBookList("");
		return mapping.findForward("viewBookMenu");
	}
	
	//Sifiso Changes: 2017/08/16:LU_MA_BLU_06:Requirement 54:Tutor Booklist: DISS Authorization: Send Back button
	public ActionForward dissSendbackBtn(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		BookMenuForm bookMenuForm=(BookMenuForm) form;
		ActionMessages messages = new ActionMessages();
		CourseDAO dao = new CourseDAO();
		
		bookMenuForm.setDissAuthTracker(0);	//reset-we have reached authorization page
		
		if( bookMenuForm.getAuthComment().length()>200 ){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage",
						"Comment should not be more than 200 characters."));
			addErrors(request,messages);
			return mapping.findForward("dissAuthorize");
		}
		
		try{
			String novellUserCode = dao.getRequestedPerson( bookMenuForm.getCourseId(), bookMenuForm.getAcadyear(),
					bookMenuForm.getTypeOfBookList(),"Tutor Count "+bookMenuForm.getTutorCount()+" Added" );			
			String email = novellUserCode;	
			
			//update audit trail for rejection
			String auditUpdateInfo = bookMenuForm.getAuthComment();
			auditUpdateInfo = auditUpdateInfo.replace("'", "");
			auditUpdateInfo = auditUpdateInfo.replace("\"", "");
			
			updateAuditTrail( bookMenuForm, "(Tutor count rejected by DIIS) "+ auditUpdateInfo );		
		
			//send an email to requested person for rejection			
			String emailSubject;
			String emailMessage;				
			emailSubject = "DISS Rejection of Prescribed Booklist Tutor Count for "+bookMenuForm.getCourseId()+" for "+bookMenuForm.getAcadyear();
			emailMessage = "NB: This is an automated message - do not reply to this e-mail. <br>"+
					"<p>"+
					"The Prescribed book list Tutor Count for "+bookMenuForm.getCourseId()+" for "+bookMenuForm.getAcadyear()+" has been rejected. <br>"+				
					"<p>"+
					"Comment from DISS: "+bookMenuForm.getAuthComment()+
					"<p>"+
					"Regards <br>" +
					"myUnisa Support Team";
			if( email.isEmpty() ){
				messages.add( ActionMessages.GLOBAL_MESSAGE,new ActionMessage("message.generalmessage","No email address available for requestor!") );
				addErrors( request,messages );
			}else{
				email += "@unisa.ac.za";
				sendEmail( emailSubject, emailMessage, email );
				
				messages.add( ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.dissrejectsuccess",
								bookMenuForm.getCourseId(),bookMenuForm.getAcadyear()) );
				addMessages( request, messages );
			}
		}catch ( Exception ex ) {
			messages.add( ActionMessages.GLOBAL_MESSAGE,new ActionMessage("message.generalmessage","Rejection Failed! Please contact ICT!") );
			addErrors( request,messages );
			bookMenuForm.setDissAuthTracker(-1);	//set-we found error in authorization page
			ex.printStackTrace();
			return mapping.findForward("dissAuthorize");
		}
		
		bookMenuForm.setAcadyear("Select Academic Year");
	    bookMenuForm.setSelectedCourse("-1");
	    bookMenuForm.setTypeOfBookList("");
		return mapping.findForward( "viewBookMenu" );
	}
            
	/* remove leading whitespace */
    public static String ltrim(String source) {
        return source.replaceAll("^\\s+", "");
    }

    /* remove trailing whitespace */
    public static String rtrim(String source) {	
        return source.replaceAll("\\s+$", "");
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
	
}
