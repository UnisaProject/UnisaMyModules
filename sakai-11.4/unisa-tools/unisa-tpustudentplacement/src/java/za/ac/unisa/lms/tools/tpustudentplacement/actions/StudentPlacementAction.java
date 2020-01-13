package za.ac.unisa.lms.tools.tpustudentplacement.actions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.LookupDispatchAction;
import org.apache.struts.util.LabelValueBean;
import org.sakaiproject.component.cover.ComponentManager;
import org.sakaiproject.component.cover.ServerConfigurationService;
import org.sakaiproject.tool.api.Session;
import org.sakaiproject.tool.api.SessionManager;
import org.sakaiproject.user.api.User;
import org.sakaiproject.user.api.UserDirectoryService;

import za.ac.unisa.lms.dao.Gencod;
import za.ac.unisa.lms.dao.StudentSystemGeneralDAO;
import za.ac.unisa.lms.dao.general.UserDAO;
import za.ac.unisa.lms.domain.general.Person;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.*;
import za.ac.unisa.lms.tools.tpustudentplacement.model.*;
import za.ac.unisa.lms.tools.tpustudentplacement.model.Email.TeachPracticeEmailUI;
import za.ac.unisa.lms.tools.tpustudentplacement.model.modelImpl.PrelimStudentPlacementUI;
import za.ac.unisa.lms.tools.tpustudentplacement.model.modelImpl.schoolImpl.SchoolUI;
import za.ac.unisa.lms.tools.tpustudentplacement.model.modelImpl.studentPlacementImpl.StuPlacementEditWinBuilder;
import za.ac.unisa.lms.tools.tpustudentplacement.model.modelImpl.studentPlacementImpl.StudentPlacementLogUI;
import za.ac.unisa.lms.tools.tpustudentplacement.model.modelImpl.studentPlacementImpl.StudentPlacementUI;
import za.ac.unisa.lms.tools.tpustudentplacement.uiLayer.CommunicationUI;
import za.ac.unisa.lms.tools.tpustudentplacement.uiLayer.StudentUI;
import za.ac.unisa.lms.tools.tpustudentplacement.model.modelImpl.LetterUI;
import za.ac.unisa.lms.tools.tpustudentplacement.utils.*;
public class StudentPlacementAction extends LookupDispatchAction{
	
	               private SessionManager sessionManager;
	               private UserDirectoryService userDirectoryService;
	             	            	protected Map getKeyMethodMap() {
		// TODO Auto-generated method stub
		Map map = new HashMap();
		map.put("initial", "initial");	
		map.put("button.continue", "nextPage");
		map.put("button.placeModule", "addStudentPlacement");
		map.put("button.save", "saveStudentPlacement");
		map.put("button.edit", "editStudentPlacement");
		map.put("button.remove", "removeStudentPlacement");
		map.put("button.contactDetails", "studentContactDetails");
		map.put("button.back","prevPage");
		map.put("button.searchSupervisor", "searchSupervisor");
		map.put("button.searchSchool", "searchSchool");
		map.put("inputCorrespondence","inputCorrespondence");
		map.put("button.correspondence","inputCorrespondence");
		map.put("button.sendEmail", "sendEmail");
		map.put("button.getEmailAdress", "getEmailAddress");
		map.put("button.getCellNumber", "getCellNumber");
		map.put("button.viewLetter", "viewLetter");
		map.put("button.SendSMS", "sendSMS");
		map.put("button.getContactDetails", "getContactDetails");
		map.put("button.listLogs","displayLogList");
		map.put("button.previous","prevPrelimPlacement");
		map.put("button.next","nextPrelimPlacement");
		map.put("button.searchMentor","searchMentor");
		map.put("handleEndDate","handleEndDate");
		map.put("handleStartDate","handleStartDate");
		map.put("editPrelimPlacements","editPrelimPlacements");
		map.put("supervForProvOnchangeAction","supervForProvOnchangeAction");
		return map;
	  }
	   public ActionForward searchMentor(ActionMapping mapping, ActionForm form,
                        HttpServletRequest request, HttpServletResponse response)
                        throws Exception {
		                                    StudentPlacementForm studentPlacementForm = (StudentPlacementForm) form; 
		                                    studentPlacementForm.setPlacementFilterDistrict(Short.parseShort("0"));
		                                    studentPlacementForm.setPlacementFilterProvince(Short.parseShort("0"));
		                                    if((studentPlacementForm.getMentor()==null)||(studentPlacementForm.getMentorFilterData()==null)){
		                                              Mentor mentor=new Mentor();
		   	                                          studentPlacementForm.setMentor(mentor);
		   	                                          mentor.setMentorTrainedList(studentPlacementForm);
		   	   			                              studentPlacementForm.setMentorFilterData(new MentorFilteringData());
		   	   			                              studentPlacementForm.setMentorModel(new MentorModel());
		                                    }
		                                    if(studentPlacementForm.getStudentPlacement()!=null){
		                        	                 Integer schoolCode=studentPlacementForm.getStudentPlacement().getSchoolCode();
		                        	                 SchoolUI schoolUI=new SchoolUI();
                        		                     int countryCode=schoolUI.getSchCountryCode(schoolCode);
                        		                     studentPlacementForm.setPlacementFilterCountry(""+countryCode);
                        		                     if(schoolCode!=0){
                        		                    	     if((""+countryCode).equals("1025")){
		                        		            	             short provCode=Short.parseShort(""+schoolUI.getSchoolProvCode(schoolCode));
		                        		                             short distCode=Short.parseShort(schoolUI.getSchoolDistrictCode(schoolCode));
		                        		                             studentPlacementForm.setPlacementFilterDistrict(distCode);
		                		                                     studentPlacementForm.setPlacementFilterProvince(provCode);
		                		                             }
		                        		                     studentPlacementForm.getMentorFilterData().setMentorFilterSchoolCode(schoolCode);
		                        		                     String schoolName=schoolUI.getSchoolName(schoolCode);
		                        		                     studentPlacementForm.getMentorFilterData().setMentorFilterSchoolValue(schoolName);
		                        	                  }
		                                 }
		                                 studentPlacementForm.getMentorFilterData().setMentorFilterDistrict(studentPlacementForm.getPlacementFilterDistrict());
		                                 studentPlacementForm.getMentorFilterData().setMentorFilterCountry(studentPlacementForm.getPlacementFilterCountry());
                                         studentPlacementForm.getMentorFilterData().setMentorFilterProvince(studentPlacementForm.getPlacementFilterProvince());
		                                 studentPlacementForm.setMentorCalledFrom("editStudentPlacement");
			                     return mapping.findForward("listMentor");	
      }
	      public ActionForward editPrelimPlacements(ActionMapping mapping, ActionForm form,
              HttpServletRequest request, HttpServletResponse response)
              throws Exception {
                       StudentPlacementForm studentPlacementForm = (StudentPlacementForm) form; 
                       Supervisor superv=new Supervisor();
                       String country=studentPlacementForm.getPlacementFilterCountry();
                       short province=studentPlacementForm.getPlacementFilterProvince();
                       studentPlacementForm.setListProvSupervisor(superv.getSupervisorList(country, province));
                       PrelimStudentPlacementUI prelimPlacementUI=new PrelimStudentPlacementUI();
                       prelimPlacementUI.setPrelimPlacementScreen(studentPlacementForm,request);
                       studentPlacementForm.setCanSaveEdits(1);
                       request.setAttribute("startDate",studentPlacementForm.getStudentPlacement().getStartDate());
                       request.setAttribute("endDate",studentPlacementForm.getStudentPlacement().getEndDate());
                      
                      return mapping.findForward("editPrelimPlacement");	
    }
	 public ActionForward nextPrelimPlacement(ActionMapping mapping, ActionForm form,
                             HttpServletRequest request, HttpServletResponse response)
                             throws Exception {
                                  StudentPlacementForm studentPlacementForm = (StudentPlacementForm) form; 
                                  int pos=studentPlacementForm.getPosOfCurrPrelimPlacement();
                                  if(studentPlacementForm.getListPlacement().size()>0){
                                       if(pos<studentPlacementForm.getListPlacement().size()){
                                            pos++;
	                                   }
                                  }
                                  PrelimStudentPlacementUI prelimPlacementUI=new PrelimStudentPlacementUI();
 		                         prelimPlacementUI.setNavigationBtnsTrackingValues(studentPlacementForm,pos,
                                		   studentPlacementForm.getListPlacement());
 		                        prelimPlacementUI.setPrelimPlacementScreen(studentPlacementForm,pos);
                                  studentPlacementForm.setPosOfCurrPrelimPlacement(pos);
                                  studentPlacementForm.setCanSaveEdits(1);
                                  request.setAttribute("startDate",studentPlacementForm.getStudentPlacement().getStartDate());
                                  request.setAttribute("endDate",studentPlacementForm.getStudentPlacement().getEndDate());
                                 
                                  return mapping.findForward("editPrelimPlacement");
      }
	  public ActionForward prevPrelimPlacement(ActionMapping mapping, ActionForm form,
                                      HttpServletRequest request, HttpServletResponse response)
                                      throws Exception {
                                                        StudentPlacementForm studentPlacementForm = (StudentPlacementForm) form; 
                                                        int pos=studentPlacementForm.getPosOfCurrPrelimPlacement();
                                                        if(pos>0){
                                                           pos--;
                                                        }
                                                        PrelimStudentPlacementUI prelimPlacementUI=new PrelimStudentPlacementUI();
                        		                         prelimPlacementUI.setNavigationBtnsTrackingValues(studentPlacementForm,pos,
                                                        		               studentPlacementForm.getListPlacement());
                        		                         prelimPlacementUI.setPrelimPlacementScreen(studentPlacementForm,pos);
                                                        studentPlacementForm.setPosOfCurrPrelimPlacement(pos);
                                                        studentPlacementForm.setCanSaveEdits(1);
                                                        request.setAttribute("startDate",studentPlacementForm.getStudentPlacement().getStartDate());
                                                        request.setAttribute("endDate",studentPlacementForm.getStudentPlacement().getEndDate());
                                                       
                                                        return mapping.findForward("editPrelimPlacement");
      }
	  public ActionForward viewLetter(
			                          ActionMapping mapping,
			                          ActionForm form,
 			                          HttpServletRequest request,
			                          HttpServletResponse response) throws Exception {
		
		                                          StudentPlacementForm studentPlacementForm = (StudentPlacementForm) form;	
		                                          ActionMessages messages = new ActionMessages();	
		                                          if(studentPlacementForm.getCommunicationTo().equals("Student")){
		                        	                         Coordinator coordinator=new Coordinator();
		                        	                         coordinator.handleNoProvincialCoord(studentPlacementForm,messages);
     	                                                     if (!messages.isEmpty()) {
     	                            	                            addErrors(request,messages);
                                                                   return mapping.findForward("inputCorrespondence");
                                                             }
				            	                	         StudentPlacement studenPlacement=new StudentPlacement();
					                        	             studenPlacement.replaceDummySupervWithCoordForProv(studentPlacementForm);
				            	                 }
		                                         LetterUI letterUI=new  LetterUI();
		                                         letterUI.getLetter(studentPlacementForm,messages);
		                                         addErrors(request,messages);
		                                     return mapping.findForward(studentPlacementForm.getCurrentPage());
	 }
	 private ActionMessages validateSendSmsData(StudentPlacementForm studentPlacementForm){
		                                 StuPlacementActionValidator stuPlacementActionValidator=new StuPlacementActionValidator();
                                         Student student=studentPlacementForm.getStudent();
                                         String cellNumber=studentPlacementForm.getCommunicationCellNumber();
                                         String communicationTo=studentPlacementForm.getCommunicationTo();
                                         return stuPlacementActionValidator.validateSmsCommunicData(student,cellNumber,communicationTo);
	}
	public ActionForward sendSMS(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		            StudentPlacementForm studentPlacementForm = (StudentPlacementForm) form;	
		            ActionMessages messages=validateSendSmsData(studentPlacementForm);
		            ActionMessages infoMessages=new ActionMessages();
		            boolean sendSuccessfuly=false;
		            StudentPlacementSmsClass smsClass=new StudentPlacementSmsClass();
			        if (messages.isEmpty()) {
			        	      sendSuccessfuly=smsClass.sendSmsNotifyingOfEmail(studentPlacementForm, request, infoMessages, infoMessages);
			        }
			        if(sendSuccessfuly){
			        	   addMessages(request,infoMessages);
			        }else{
			        	   addErrors(request,messages);
			        }
			        studentPlacementForm.setCurrentPage("inputCorrespondence");
		    return mapping.findForward("inputCorrespondence");
	}
    private ActionMessages validateSendEmailData(StudentPlacementForm studentPlacementForm){
                              StuPlacementActionValidator stuPlacementActionValidator=new StuPlacementActionValidator();
                              String emailAddress=studentPlacementForm.getCommunicationEmailAddress().trim();
                              String cellNumber=studentPlacementForm.getCommunicationCellNumber();
                              String communicationTo=studentPlacementForm.getCommunicationTo();
                  return stuPlacementActionValidator.validatEmailCommunicData(emailAddress,cellNumber,communicationTo);
    }
	public ActionForward sendEmail(
			                       ActionMapping mapping,
			                       ActionForm form,
			                       HttpServletRequest request,
			                       HttpServletResponse response) throws Exception {
		
		                                  StudentPlacementForm studentPlacementForm = (StudentPlacementForm) form;	
		 		                          ActionMessages infoMessages = new ActionMessages();
		 		                          ActionMessages emailMessages = new ActionMessages();
		 		                          ActionMessages smsLogMessages = new ActionMessages();
		 		                          boolean delivered=false;
		 		                          boolean sendSuccessfully=false;
		 		                          ActionMessages messages=validateSendEmailData(studentPlacementForm);
			                              studentPlacementForm.setCurrentPage("inputCorrespondence");
	            	                      if (messages.isEmpty()) {
		            	                             setStudent(studentPlacementForm);
		            	                             if(studentPlacementForm.getCommunicationTo().equals("Student")){
				                        	                 Coordinator coordinator=new Coordinator();
		            	                                     coordinator.handleNoProvincialCoord(studentPlacementForm,emailMessages);
	                                                         if (!emailMessages.isEmpty()) {
		            	                                            handleEmailErrors(request,emailMessages,delivered);
                                                                    return mapping.findForward("inputCorrespondence");
                                                             }
		            	                             }
		            	                             TeachPracticeEmailUI tpEmailUI=new TeachPracticeEmailUI();
		                                             delivered=tpEmailUI.sendEmail(studentPlacementForm,emailMessages);
		                                             String fromEmail="teachprac@unisa.ac.za";
		                                             String toEmail=studentPlacementForm.getCommunicationEmailAddress().trim();
		    	                                     log.info("sending mail from "+fromEmail+" to "+toEmail);
		                                             StudentPlacementSmsClass smsClass=new StudentPlacementSmsClass();
                                                     sendSuccessfully=smsClass.sendSmsNotifyingOfEmail(studentPlacementForm,request,infoMessages,messages);
		                                 }
		                                 handleSmsErrors(request,infoMessages,messages,sendSuccessfully);
                                         handleEmailErrors(request,emailMessages,delivered);
                                         handleSmsLogErrors(request,smsLogMessages);
                                         return mapping.findForward("inputCorrespondence");	
	}	
	private void  handleSmsErrors(HttpServletRequest request,
			                 ActionMessages infoMessages,ActionMessages messages,boolean sendSuccessfully)throws Exception {
	                                if(sendSuccessfully){
	                                	  addMessages(request,infoMessages);
	                                }else{
	                                       addErrors(request,messages);
	                                }
	}
	private void  handleSmsLogErrors(HttpServletRequest request,ActionMessages messages)throws Exception {
                   if(!messages.isEmpty()){
                   	            addErrors(request,messages);
                   }
    }
	private void  handleEmailErrors(HttpServletRequest request,ActionMessages messages,
			         boolean delivered)throws Exception {
                             if(delivered){
                   	                 addMessages(request,messages);
                             }else{
                                     addErrors(request,messages);
                             }
    }
	public ActionForward getEmailAddress(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		                 StudentPlacementForm studentPlacementForm = (StudentPlacementForm) form;	
		                 ActionMessages messages = new ActionMessages();	
		                 CommunicationDataValidator cv=new CommunicationDataValidator();
		                 String communicationTo=studentPlacementForm.getCommunicationTo();
		                 cv.validateCommunicationTo(communicationTo,messages,"Recipient required to get Email Address.");
		                 if (messages.isEmpty()){
		                	     SchoolUI school=new SchoolUI();
		                	     school.setSchoolEmailToFormBean(studentPlacementForm);
		                	     setStudentEmail(studentPlacementForm);
			             }
		         		if (!messages.isEmpty()) {
			                    addErrors(request,messages);		
		                }
		                studentPlacementForm.setCurrentPage("inputCorrespondence");
		            return mapping.findForward("inputCorrespondence");	
	 }	
	 private void  setStudentCommunicationDataToFormBean(StudentPlacementForm studentPlacementForm )throws Exception {
                        if (studentPlacementForm .getCommunicationTo().equalsIgnoreCase("Student")){
       	                        StudentUI  studentUI  =new StudentUI();
       	                        studentUI.setStudentEmailToFormBean(studentPlacementForm);
       	                        studentUI.setStudentCellNrToFormBean(studentPlacementForm);
                        }
     }
	 private void  setStudentEmail(StudentPlacementForm studentPlacementForm )throws Exception {
                          if (studentPlacementForm .getCommunicationTo().equalsIgnoreCase("Student")){
                        	       StudentUI  studentUI  =new StudentUI  ();
                        	       studentUI.setStudentEmailToFormBean(studentPlacementForm);
                          }
     }
	 public ActionForward getCellNumber(
			               ActionMapping mapping,
			               ActionForm form,
			               HttpServletRequest request,
			               HttpServletResponse response) throws Exception {
		
                    		          StudentPlacementForm studentPlacementForm = (StudentPlacementForm) form;	
		                              ActionMessages messages = new ActionMessages();
		                              CommunicationUI communicationUI=new CommunicationUI();
		                              communicationUI.getCellNr(studentPlacementForm, messages);
		                              if (!messages.isEmpty()) {
			                                 addErrors(request,messages);		
		                              }
				        return mapping.findForward("inputCorrespondence");	
	}	
	public ActionForward getContactDetails(
			               ActionMapping mapping,
			               ActionForm form,
			               HttpServletRequest request,
			               HttpServletResponse response) throws Exception {
		
		                        StudentPlacementForm studentPlacementForm = (StudentPlacementForm) form;
		                        ValidateString validateString=new ValidateString();
		                        ActionMessages messages = new ActionMessages();
		                        String communicationTo=studentPlacementForm.getCommunicationTo();
		                        String msg="Recipient required to get Contact Details.";
		                        validateString.validateStr(communicationTo,messages,msg);
		                        if(messages.isEmpty()){
		                        	     SchoolUI school=new SchoolUI();
		                        	     school.setSchoolCommunicDataToFormBean(studentPlacementForm);
						                 setStudentCommunicationDataToFormBean(studentPlacementForm );
		                                 String cellNumErrorMsg="If an email is sent without a cell phone number specified the notification SMS would not be sent.";
		                                 String communicCellNum=studentPlacementForm.getCommunicationCellNumber();
		                                 validateString.validateStr(communicCellNum,messages,cellNumErrorMsg);
		                        }
		                        if(!messages.isEmpty()) {
			                          addErrors(request,messages);
                         		}
		                        studentPlacementForm.setCurrentPage("inputCorrespondence");
		                   return mapping.findForward("inputCorrespondence");	
	}	
	public ActionForward inputCorrespondence(
			                 ActionMapping mapping,
			                 ActionForm form,
			                 HttpServletRequest request,
			                 HttpServletResponse response) throws Exception {
		                  
		                      StudentPlacementForm studentPlacementForm = (StudentPlacementForm) form;	
		                      CommunicationUI communicationUI=new CommunicationUI();
		                      ActionMessages messages=new ActionMessages(); 
		                      communicationUI.buildCommunicationScreen(request, studentPlacementForm, messages);
		                      if(!messages.isEmpty()){
		                    	  addErrors(request,messages);
		                    	  studentPlacementForm.setCurrentPage(studentPlacementForm.getPreviousPage());
		                      }
		           return mapping.findForward(studentPlacementForm.getCurrentPage());	
	}
	public ActionForward searchSupervisor(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		         StudentPlacementForm studentPlacementForm = (StudentPlacementForm) form;
		         return mapping.findForward("searchSupervisor");	
	}
	public ActionForward searchSchool(
			                  ActionMapping mapping,
			                  ActionForm form,
			                  HttpServletRequest request,
			                  HttpServletResponse response) throws Exception {
		                            StudentPlacementForm studentPlacementForm = (StudentPlacementForm) form;	
		                                 return mapping.findForward("searchSchool");	
	}
	public ActionForward nextPage(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		               StudentPlacementForm studentPlacementForm = (StudentPlacementForm) form;	
		String nextPage="initial";
		
		if (studentPlacementForm.getCurrentPage().equalsIgnoreCase("inputStudentPlacement")){
			   nextPage = listStudentPlacement(mapping,form,request,response);
		}	
		
		studentPlacementForm.setPreviousPage(studentPlacementForm.getCurrentPage());
		studentPlacementForm.setCurrentPage(nextPage);
		return mapping.findForward(nextPage);	
	}
	private void setStudent(StudentPlacementForm studentPlacementForm)throws Exception {
		            StudentUI studentUI=new StudentUI();
		             studentUI.setStudent(studentPlacementForm);
	}
	public ActionForward prevPage(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		           StudentPlacementForm studentPlacementForm = (StudentPlacementForm) form;	
		 		   String nextPage="initial";
		 		   if (studentPlacementForm.getCurrentPage().equalsIgnoreCase("inputCorrespondence")){
			                     nextPage=handlePrevForWhenCurrPageInputCorr(studentPlacementForm);
		           }else if (studentPlacementForm.getCurrentPage().equalsIgnoreCase("editPrelimPlacement")){
		             	        studentPlacementForm.setCurrentPage("listPrelimPlacement");  
			                    nextPage="listPrelimPlacement";
		           }else if (studentPlacementForm.getCurrentPage().equalsIgnoreCase("listStudentPlacement")){
			                      nextPage="inputStudentPlacement";
		           }else if (studentPlacementForm.getCurrentPage().equalsIgnoreCase("editStudentPlacement")){
			                      nextPage="listStudentPlacement";
		           }else if (studentPlacementForm.getCurrentPage().equalsIgnoreCase("studentLetter")){
			                      nextPage="inputCorrespondence";
		           }else if (studentPlacementForm.getCurrentPage().equalsIgnoreCase("schoolLetter")){
			                      nextPage="inputCorrespondence";
		           }else if (studentPlacementForm.getCurrentPage().equalsIgnoreCase("studentContactDetails")){	
		        	             if(studentPlacementForm.getStudentPlacement()!=null){
		        	            	 studentPlacementForm.getStudentPlacement().setDatesToRequest(request);
		        	             }
			                     nextPage=studentPlacementForm.getPreviousPage(); 			
		           }else if(studentPlacementForm.getCurrentPage().equalsIgnoreCase("listLogs")){
				              if(studentPlacementForm.getLogButtonTracker().equals("PassedFromLogBtn")){
					                  nextPage=listStudentPlacement(mapping,form, request,response);
				              }else{
					                   nextPage="inputStudentPlacement";	
				              }
				              studentPlacementForm.setLogButtonTracker("");
		          }
   		          //studentPlacementForm.setPreviousPage(studentPlacementForm.getCurrentPage());
		          studentPlacementForm.setCurrentPage(nextPage);
		return mapping.findForward(nextPage);	
	}
	private String handlePrevForWhenCurrPageInputCorr(StudentPlacementForm studentPlacementForm)throws Exception {
		              String nextPage="initial";
		              StudentPlacementLogUI studentPlacementLogUI=new StudentPlacementLogUI();
	                  if (studentPlacementForm.getPreviousPage().equalsIgnoreCase("editPrelimPlacement")){
                              setStudent(studentPlacementForm);
                              nextPage="editPrelimPlacement";
                      }else if(studentPlacementForm.getLogButtonTracker().equals("")){
                                   setStudent(studentPlacementForm);
                                   nextPage="listStudentPlacement";
                      }else{
	                               studentPlacementLogUI.getSelectedLogs(studentPlacementForm);
                                   nextPage="listLogs";
                      }
	                  return nextPage;
	}
	public ActionForward studentContactDetails(
			                  ActionMapping mapping,
			                  ActionForm form,
			                  HttpServletRequest request,
			                  HttpServletResponse response) throws Exception {
		
		                                StudentPlacementForm studentPlacementForm = (StudentPlacementForm) form;			
		                         		studentPlacementForm.setPreviousPage(studentPlacementForm.getCurrentPage());
		                                if(studentPlacementForm.getCurrentPage().equals("editPrelimPlacement")){
		                                	setStudent(studentPlacementForm);
		                                }
		                                studentPlacementForm.setCurrentPage("studentContactDetails");	
		               return mapping.findForward("studentContactDetails");	
	}
    public String listStudentPlacement(ActionMapping mapping, ActionForm form,
			     HttpServletRequest request, HttpServletResponse response)
			          throws Exception {
		                     StudentPlacementUI spUI=new StudentPlacementUI(); 
  		                     ActionMessages messages=new ActionMessages();
  		                    String nextPage=spUI.listStudentPlacement(mapping, form, request, response,messages);
  		                    if (!messages.isEmpty()) {
		                      	     addErrors(request,messages);
		                    } 
		            return nextPage;
	}
	public ActionForward initial(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		             StudentPlacementForm studentPlacementForm = (StudentPlacementForm) form;	
				     //Get user details
		             studentPlacementForm.setUserId(null);
		             User user = null;
		             sessionManager = (SessionManager) ComponentManager.get(SessionManager.class);
	                 userDirectoryService = (UserDirectoryService) ComponentManager.get(UserDirectoryService.class);
	                 String serverpath = ServerConfigurationService.getServerUrl();
		                    	  
		             Session currentSession = sessionManager.getCurrentSession();
		             String userID  = currentSession.getUserId();
		             studentPlacementForm.setLogButtonTracker("");
		             String storedNetWorkCode="";
		             if (userID != null) {
			                 user = userDirectoryService.getUser(userID);
			                 studentPlacementForm.setUserId(user.getEid().toUpperCase().trim());
			                 String networkCode=studentPlacementForm.getUserId();
			                 if ((studentPlacementForm.getUserId()==null)||studentPlacementForm.getUserId().equals("")){
		                            return mapping.findForward("userUnknown");
	                         }
	                         Coordinator coordinator=new Coordinator();
			                 if((networkCode!=null)&&(!networkCode.equals(""))){
				                    studentPlacementForm.setCoordinatorActive(coordinator.isCoordinator(networkCode));
			                 }else{
				                     studentPlacementForm.setCoordinatorActive("N");
				                     return mapping.findForward("userUnknown");
			                 }
	   	                     Personnel personnel=new Personnel(); 
	   	                     String personnelNum=personnel.getPersonnelNumber(networkCode);
	   	                     if ((personnelNum!=null)&&(!personnelNum.equals(""))){
	   	                	        studentPlacementForm.setPersonnelNumber(personnelNum);
	   	 		             }else{
	   	                              return mapping.findForward("userUnknown");
                             }
	   	                     Mentor mentor=new Mentor();
	   	                     studentPlacementForm.setMentor(mentor);
	   	                     mentor.setMentorTrainedList(studentPlacementForm);
	   	   			         studentPlacementForm.setMentorFilterData(new MentorFilteringData());
	   	   			         studentPlacementForm.setMentorModel(new MentorModel());
	   	   			         mentor.refresh(studentPlacementForm);
  	   			        
	   	   			}else{
		            	      return mapping.findForward("userUnknown");
		             }
	   	                    
		studentPlacementForm.setJustEnteredPlacementLogs("no");
		Person person = new Person();
		UserDAO userdao = new UserDAO();
		person = userdao.getPerson(studentPlacementForm.getUserId());			
		studentPlacementForm.setUser(person);
		//initialise input values
		studentPlacementForm.setAcadYear("");
		studentPlacementForm.setSemester("");
		
		//Get semester list
		List list = new ArrayList();
		StudentSystemGeneralDAO dao = new StudentSystemGeneralDAO();
		for (int i=0; i < dao.getGenCodes((short)54,1).size(); i++) {
			list.add(i, (Gencod)(dao.getGenCodes((short)54,1).get(i)));
		}
		studentPlacementForm.setListSemester(list);		
		
		//Get school type list
		//sort alphabetic on eng-desc
		Gencod gencod = new Gencod();
		gencod.setCode("");
		gencod.setEngDescription("All");
		List listFilter = new ArrayList();
		list = new ArrayList();
		listFilter.add(0,gencod);
		
		int index = 0;
		for (int i=0; i < dao.getGenCodes((short)148,1).size(); i++) {
			index = index + 1;
			listFilter.add(index, (Gencod)(dao.getGenCodes((short)148,1).get(i)));
			list.add(i, (Gencod)(dao.getGenCodes((short)148,1).get(i)));
		}
		studentPlacementForm.setListSchoolType(list);	
		studentPlacementForm.setListFilterSchoolType(listFilter);
		
		//Get school category list
		//sort alphabetic on afr-desc
		listFilter = new ArrayList();
		list = new ArrayList();
		listFilter.add(0,gencod);
		
		index = 0;
		for (int i=0; i < dao.getGenCodes((short)149,3).size(); i++) {
			index = index + 1;
			listFilter.add(index, (Gencod)(dao.getGenCodes((short)149,3).get(i)));
			list.add(i, (Gencod)(dao.getGenCodes((short)149,3).get(i)));
		}
		studentPlacementForm.setListFilterSchoolCategory(listFilter);
		studentPlacementForm.setListSchoolCategory(list);	
		
		//Get country list
		Country country=new Country();
		studentPlacementForm.setListCountry(country.listAllCountries());
		
		//Get Province list
		list = new ArrayList<Province>();
		Province  province=new Province();
		list = province.getProvinceList();
		studentPlacementForm.setListProvince(list);
		listFilter = new ArrayList<Province>();
		province.setCode(Short.parseShort("0"));
		province.setDescription("ALL");
		province.setIn_use("Y");
		listFilter.addAll(list);
		listFilter.add(0, province);
		studentPlacementForm.setListFilterProvince(listFilter);		
		
		//Initialise values
		PlacementUtilities util = new PlacementUtilities();
		if (studentPlacementForm.getSemester()==null || studentPlacementForm.getSemester().equalsIgnoreCase("")){
			studentPlacementForm.setSemester("0");
		}
		if (studentPlacementForm.getAcadYear()==null || studentPlacementForm.getAcadYear().equalsIgnoreCase("")){
			studentPlacementForm.setAcadYear(util.getDefaultAcadYear().toString());
		}
		studentPlacementForm.setCurrentPage("inputStudentPlacement");
		return mapping.findForward("inputStudentPlacement");	
	}
	public ActionForward addStudentPlacement(
			   ActionMapping mapping,
			   ActionForm form,
			   HttpServletRequest request,
			   HttpServletResponse response) throws Exception {
		
		StudentPlacementForm studentPlacementForm = (StudentPlacementForm) form;
		
		
		StudentPlacement placement = new StudentPlacement();
		placement.setSchoolCode(-1);
		//initialise values
		if (studentPlacementForm.getListStudentPlacement().size()>0){
			for (int i=0; i < studentPlacementForm.getListStudentPlacement().size(); i++){
				         StudentPlacementListRecord record = new StudentPlacementListRecord();
				         record=(StudentPlacementListRecord)studentPlacementForm.getListStudentPlacement().get(i);
				         placement.setSchoolCode(record.getSchoolCode());
				         placement.setSchoolDesc(record.getSchoolDesc());
				         placement.setSupervisorCode(record.getSupervisorCode());
				         placement.setSupervisorName(record.getSupervisorName());
				         placement.setStartDate(record.getStartDate());
				         placement.setEndDate(record.getEndDate());
				         placement.setNumberOfWeeks(record.getNumberOfWeeks());
				
              }
		}
		placement.setDatesToRequest(request);
		Integer schoolCode=placement.getSchoolCode();
		if((schoolCode!=null)&&(schoolCode!=-1)){
			       SchoolUI schoolUI=new  SchoolUI();
			       String town=schoolUI.getTown(schoolCode);
			       placement.setTown(town);
		}
		studentPlacementForm.setStudentPlacement(placement);
		studentPlacementForm.setStudentPlacementAction("add");	
		studentPlacementForm.setSchoolCalledFrom("editStudentPlacement");
		studentPlacementForm.setPreviousPage(studentPlacementForm.getCurrentPage());
		studentPlacementForm.setCurrentPage("editStudentPlacement");
		return mapping.findForward("editStudentPlacement");	
	}
	
	public ActionForward editStudentPlacement(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		           StudentPlacementForm studentPlacementForm = (StudentPlacementForm) form;
		           ActionMessages messages = new ActionMessages();
		           StuPlacementEditWinBuilder editWinBuilder=new StuPlacementEditWinBuilder();
		           editWinBuilder.buildEditWin(studentPlacementForm, messages);
		           request.setAttribute("startDate",studentPlacementForm.getStudentPlacement().getStartDate());
                   request.setAttribute("endDate",studentPlacementForm.getStudentPlacement().getEndDate());
                   if (!messages.isEmpty()) {
			              addErrors(request,messages);
			              studentPlacementForm.setCurrentPage("listStudentPlacement");
		            }
		            studentPlacementForm.setCurrentPage("editStudentPlacement");
		   		 return mapping.findForward(studentPlacementForm.getCurrentPage());
		}
	
	public ActionForward removeStudentPlacement(
			                  ActionMapping mapping,
			                  ActionForm form,
			                  HttpServletRequest request,
			                  HttpServletResponse response) throws Exception {
		        		         StudentPlacementForm studentPlacementForm = (StudentPlacementForm) form;
		        		         StudentPlacementUI stuPlacementUI=new StudentPlacementUI();
		        		         ActionMessages messages=new ActionMessages();
		        		         String nextPage=stuPlacementUI.removeStudentPlacement(mapping, request, response, studentPlacementForm, messages);
		                         if (!messages.isEmpty()) {
		                         	    addErrors(request,messages);
		                         	    studentPlacementForm.setCurrentPage(nextPage);
		                 	     }
		                         return mapping.findForward(nextPage); 
    }
	public ActionForward saveStudentPlacement(
			                  ActionMapping mapping,
			                  ActionForm form,
			                  HttpServletRequest request,
			                  HttpServletResponse response) throws Exception {
		
		                             StudentPlacementForm studentPlacementForm = (StudentPlacementForm) form;
                                     StudentPlacementUI stuPlacementUI=new StudentPlacementUI(); 
                                     studentPlacementForm.getStudentPlacement().setStartDate(request.getParameter("startDate").toString());
                                     studentPlacementForm.getStudentPlacement().setEndDate(request.getParameter("endDate").toString());
                                     studentPlacementForm.setCommunicationSchool(studentPlacementForm.getStudentPlacement().getSchoolCode());
		                             ActionMessages messages=stuPlacementUI.saveStuPlacement(studentPlacementForm);
		                             request.setAttribute("startDate",studentPlacementForm.getStudentPlacement().getStartDate());
	                                  request.setAttribute("endDate",studentPlacementForm.getStudentPlacement().getEndDate());
	                                  if(!messages.isEmpty()){
		                            	   addErrors(request,messages);
		                            	   return mapping.findForward(studentPlacementForm.getCurrentPage());
		                             }
		                    return mapping.findForward(listStudentPlacement(mapping,form,request,response));	
	}
	public ActionForward addPlacement(ActionMapping mapping,
			                  ActionForm form,
			                  HttpServletRequest request,
			                  HttpServletResponse response) {
		                            ActionMessages messages=new ActionMessages();
		                            StudentPlacementForm studentPlacementForm = (StudentPlacementForm) form;
		 	         	            try{
		                                   StudentPlacement placement=studentPlacementForm.getStudentPlacement();
		 	         	                   StudentPlacementUI  studentPlacementUI=new StudentPlacementUI();
		 	         	                   messages=studentPlacementUI.addPlacement(studentPlacementForm);
		 	         	                   if(messages.isEmpty()){
		 	         	            	         studentPlacementForm.setCommunicationSchool(placement.getSchoolCode());
		 	         	                   }
		 	         	                   if(!messages.isEmpty()){
	 	         	               	            handleAddPlacementErrors(messages,request,studentPlacementForm);
	 	 			                            return mapping.findForward("editStudentPlacement");	
		                                   }
	 	 		                           return mapping.findForward(listStudentPlacement(mapping,form,request,response));
		 	         	            }catch(Exception ex){
		 	         	            	                   handleAddPlacementErrors(messages,request,studentPlacementForm);
	 			                                           return mapping.findForward("editStudentPlacement");
		 	         	            }
	}
	private void handleAddPlacementErrors(ActionMessages messages,HttpServletRequest request,
	                     StudentPlacementForm studentPlacementForm){
                         addErrors(request,messages);
                         studentPlacementForm.setPreviousPage(studentPlacementForm.getCurrentPage());
                         studentPlacementForm.setCurrentPage("editStudentPlacement");
   }
   public ActionForward displayLogList(// it lists all the logs  for Placement Log button
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		           StudentPlacementForm studentPlacementForm = (StudentPlacementForm) form;
		           StudentPlacementLogUI studentPlacementLogUI=new StudentPlacementLogUI();
		           studentPlacementLogUI.setLogList(studentPlacementForm);
		    return mapping.findForward("listLogs");	
	}
    public ActionForward supervForProvOnchangeAction(
		                    	ActionMapping mapping,
			                    ActionForm form,
			                    HttpServletRequest request,
			                    HttpServletResponse response) throws Exception {
		                                StudentPlacementForm studentPlacementForm = (StudentPlacementForm) form;
		                                StudentPlacement stuplcmt=studentPlacementForm.getStudentPlacement();
		                                Supervisor supervisor=new Supervisor();
		                                int supervCode=stuplcmt.getSupervisorCode();
		                                if(supervCode!=-1){
		                                       supervisor=supervisor.getSupervisor(supervCode);
		                                       String supervisorFullName= supervisor.getSurname()+" "+supervisor.getInitials()+
		                                    		                      " "+supervisor.getTitle();
		                                       stuplcmt.setSupervisorName(supervisorFullName);
		                                       studentPlacementForm.setStudentPlacement(stuplcmt);
		                                }
		                                request.setAttribute("startDate",studentPlacementForm.getStudentPlacement().getStartDate());
		                                  request.setAttribute("endDate",studentPlacementForm.getStudentPlacement().getEndDate());
		    return mapping.findForward("editPrelimPlacement");
    }
	
}
