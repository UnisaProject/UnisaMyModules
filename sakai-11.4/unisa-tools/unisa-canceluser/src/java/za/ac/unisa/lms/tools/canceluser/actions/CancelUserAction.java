package za.ac.unisa.lms.tools.canceluser.actions;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.LookupDispatchAction;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.sakaiproject.authz.api.AuthzGroup;
import org.sakaiproject.authz.api.AuthzGroupService;
import org.sakaiproject.authz.api.AuthzPermissionException;
import org.sakaiproject.authz.api.GroupNotDefinedException;
import org.sakaiproject.component.cover.ComponentManager;
import org.sakaiproject.component.cover.ServerConfigurationService;
import org.sakaiproject.event.api.UsageSession;
import org.sakaiproject.event.api.EventTrackingService;
import org.sakaiproject.event.api.UsageSessionService;
import org.sakaiproject.tool.api.Session;
import org.sakaiproject.tool.api.SessionManager;
import org.sakaiproject.tool.api.ToolManager;
import org.sakaiproject.user.api.User;
import org.sakaiproject.user.api.UserDirectoryService;
import za.ac.unisa.lms.constants.EventTrackingTypes;
import za.ac.unisa.lms.tools.canceluser.dao.CancelUserSakaiQueryDAO;
import za.ac.unisa.lms.tools.canceluser.dao.CancelUserStudentQueryDAO;
import za.ac.unisa.lms.tools.canceluser.dao.StudentInfo;
import za.ac.unisa.lms.tools.canceluser.dao.UserCleanupSakaiDAO;
import za.ac.unisa.lms.tools.canceluser.forms.CancelUserForm;
import za.ac.unisa.lms.tools.join.dao.MyUnisaJoinSakaiDAO;
public class CancelUserAction extends LookupDispatchAction {
	private AuthzGroupService authzGroupService;
	private boolean  myLifeActive=false;
	private boolean stuNumExist=false;
	private EventTrackingService eventTrackingService;
	private UsageSessionService usageSessionService;
    private SessionManager sessionManager;
    private UserDirectoryService userDirectoryService;
    private ToolManager toolManager;
	
	//code for removing the white space problem 
		public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
			if (request.getParameter("action") == null) return submit(mapping, form, request, response);
		return super.execute(mapping, form, request, response);
		}
		protected Map getKeyMethodMap() {
			Map map = new HashMap();
			map.put("button.continue", "submit");
			map.put("button.clear", "clear");
			map.put("button.back", "input");
			map.put("button.cancel", "save");
			map.put("input","input");
			return map;
		}


	/**
	 * Method execute
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward input(
		  ActionMapping mapping,
		  ActionForm form,
		  HttpServletRequest request,
		  HttpServletResponse response) {
		  return mapping.findForward("input");
	}

	/**
	 * Method execute
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward submit(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) {
			ActionMessages messages = new ActionMessages();
			//create a instance of the form linked to the action (as in struts-config.)
			eventTrackingService = (EventTrackingService) ComponentManager.get(EventTrackingService.class);
			userDirectoryService= (UserDirectoryService) ComponentManager.get(UserDirectoryService.class);
			toolManager= (ToolManager) ComponentManager.get(ToolManager.class);
			Log log = LogFactory.getLog(CancelUserAction.class);
			CancelUserForm cancelUserForm = (CancelUserForm)form;
			//do validations using DAO
			//create a instance of CancelUserStudentQueryDAO
			CancelUserStudentQueryDAO db= new CancelUserStudentQueryDAO();
			CancelUserSakaiQueryDAO db1 = new CancelUserSakaiQueryDAO();
			
			String studentNumber =ltrim(cancelUserForm.getStudentNr());
			studentNumber = rtrim(studentNumber);
			String blockedStatusStr="";
			   
			// use validator.xml to validate that stno must be a number and is mandatory
			messages =(ActionMessages) cancelUserForm.validate(mapping,request);
			
			if (!messages.isEmpty()){
				addErrors(request,messages);
				return mapping.findForward("input");
			} else {
				
				
				StudentInfo studentinfo = new StudentInfo();
				studentinfo = db.getStudentDetails(studentNumber);
	
				//	validate student number
				boolean validStudent= false;
				try{
					validStudent= db.getvalidStudent(studentNumber);

					if (validStudent == false){

						messages.add(ActionMessages.GLOBAL_MESSAGE,
								new ActionMessage("message.generalmessage", "Please enter a valid Unisa student number"));
						addErrors(request, messages);

						return mapping.findForward("input");
					}
				}catch (Exception e){
					log.error("action: cancelUserAction method=submit: (e) student="+cancelUserForm.getStudentNr()+" e="+e);
					eventTrackingService.post(
							eventTrackingService.newEvent(EventTrackingTypes.EVENT_CANCELUSER_ERROR, "JavaProxy error: "+e, false));

				    messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage", "The student myUnisa account was not cancelled, unexpected error occured."));
					addErrors(request, messages);

					return mapping.findForward("input");
				}
				
				//verifying the student record in Join_activation table
				boolean validStuRecord= false;
				try{
					validStuRecord= db1.getvalidStudentRecord(studentNumber);

					if (validStuRecord == false){
						
						log.debug(this+": "+cancelUserForm.getStudentNr()+" is not found.");

						messages.add(ActionMessages.GLOBAL_MESSAGE,
								new ActionMessage("message.generalmessage", "Student does not have a myUnisa account and cannot be cancelled"));
						addErrors(request, messages);

						return mapping.findForward("input");
					}
				}catch (Exception e){
					log.error("action: cancelUserAction method=submit: (e) student="+cancelUserForm.getStudentNr()+" e="+e);
					eventTrackingService.post(
							eventTrackingService.newEvent(EventTrackingTypes.EVENT_CANCELUSER_ERROR, "JavaProxy error: "+e, false));

				    messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage", "The student myUnisa account was not cancelled, unexpected error occured."));
					addErrors(request, messages);

					return mapping.findForward("input");
				}
							
				//	validate if student have active myUnisa account
				MyUnisaJoinSakaiDAO db2 = new MyUnisaJoinSakaiDAO();			
				try {
					boolean isActivated = db2.myUnisaAccountExist(studentNumber);
					myLifeActive=isActivated;
					if (isActivated == false) {
						  if(!cancelUserForm.getCancellationOption().equals("myLife")){
						     messages.add(ActionMessages.GLOBAL_MESSAGE,
								new ActionMessage("message.generalmessage", "Student's myUnisa account is not active and cannot be cancelled"));
						      addErrors(request, messages);
						     return mapping.findForward("input");
					      }//if
					}
				} catch (Exception e) {
					log.debug("action: cancelUserAction method=submit: (e) student="+cancelUserForm.getStudentNr()+" e="+e);
					eventTrackingService.post(
							eventTrackingService.newEvent(EventTrackingTypes.EVENT_CANCELUSER_ERROR, "JavaProxy error: "+e, false));

				    messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage", "The student myUnisa account was not cancelled, unexpected error occured."));
					addErrors(request, messages);

					return mapping.findForward("input");
				}
				String tempReason = ltrim(cancelUserForm.getReasonForCans());
				tempReason = rtrim(tempReason);
				if(tempReason.equals("")){
		
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage", "Please enter the reason for cancellation ."));
					addErrors(request, messages);
					return mapping.findForward("input");
					
				}
				//initial and first letter of the name should be Upper and remaining lower case.
				
				String tempInitial = studentinfo.getStudentName().toUpperCase();
				char c = studentinfo.getSurname().charAt(0);
				String t = Character.toString(c).toUpperCase();
				String tempSurName = t.concat(studentinfo.getSurname().substring(1).toLowerCase());
				String tempName =tempInitial+tempSurName;
		    	cancelUserForm.setStudentName(tempName);
		    	if(cancelUserForm.isBlockCheck()){
					cancelUserForm.setBlocked("Yes");
					if(cancelUserForm.getCancellationOption().equals("myLife")){
					     messages.add(ActionMessages.GLOBAL_MESSAGE,
    				     new ActionMessage("message.generalmessage", "Student myUnisa account is active, cannot block only  myLife for the student."));
    				     addErrors(request, messages);
    				     return mapping.findForward("input");
					}//if
			    }else{
					cancelUserForm.setBlocked("No");
				}
				//setting the join date
		    	if(!cancelUserForm.getCancellationOption().equals("myLife")){
				     try{
					       cancelUserForm.setJoinDate(db1.getJoinDate(studentNumber).substring(0,16));
					  }catch (Exception e){
						       log.error("action: cancelUserAction method=submit: (e) student="+cancelUserForm.getStudentNr()+" e="+e);
						       eventTrackingService.post(
						    		   eventTrackingService.newEvent(EventTrackingTypes.EVENT_CANCELUSER_ERROR, "JavaProxy error: "+e, false));
        					   messages.add(ActionMessages.GLOBAL_MESSAGE,
							   new ActionMessage("message.generalmessage", "The student myUnisa account was not cancelled, unexpected error occured."));
						       addErrors(request, messages);
 						       return mapping.findForward("input");
				 	  }//catch
		    	}//if
				
			} // END if (!messages.isEmpty()) else
			// use validator.xml to validate that stno must be a number and is mandatory
			String studentNr=cancelUserForm.getStudentNr();
        	blockedStatusStr=db1.getBlockedStatus(studentNr);
        	int tracker=0;
        	if(cancelUserForm.getCancellationOption().equals("myLife")&&(!blockedStatusStr.equalsIgnoreCase("nc"))){
           	      log.error("action: cancelUserAction method=save: (e) student="+cancelUserForm.getStudentNr()+" is already a member");
	              messages.add(ActionMessages.GLOBAL_MESSAGE,
				  new ActionMessage("message.generalmessage", "Student  has an active  myUnisa account. Use either  the myUnisa and myLife or myUnisa option for cancellation"));
		          addErrors(request, messages);
		          tracker=1;
		    }
			if(cancelUserForm.getCancellationOption().equals("myLife")&&blockedStatusStr.equalsIgnoreCase("nc")){
				    messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Are you sure you want to cancel the following user myLife account ? "));
					addErrors(request, messages);
			}//if
			if(!cancelUserForm.getCancellationOption().equals("myLife")){
			       messages.add(ActionMessages.GLOBAL_MESSAGE,
				   new ActionMessage("message.generalmessage", "Are you sure you want to cancel the following user account on myUnisa? "));
			       addErrors(request, messages);
			}//if
			if(tracker==1){
			      return mapping.findForward("input");	
			}else{
				return mapping.findForward("submit");
			}
			
		}
	public ActionForward save(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws JobExecutionException {
			ActionMessages messages = new ActionMessages();//class that encapsulates messages.
			CancelUserForm cancelUserForm = (CancelUserForm)form ;
			boolean deleteStatus = false;

			CancelUserSakaiQueryDAO db1= new CancelUserSakaiQueryDAO();
			CancelUserStudentQueryDAO db= new CancelUserStudentQueryDAO();
			eventTrackingService = (EventTrackingService) ComponentManager.get(EventTrackingService.class);
			userDirectoryService = (UserDirectoryService) ComponentManager.get(UserDirectoryService.class);
			sessionManager = (SessionManager) ComponentManager.get(SessionManager.class);
			usageSessionService = (UsageSessionService) ComponentManager.get(UsageSessionService.class);
			toolManager = (ToolManager) ComponentManager.get(ToolManager.class);
						
			//if no validation errors do cancellation
			if(cancelUserForm.getBlocked().equals("Yes")){
				if(cancelUserForm.getCancellationOption().equals("both")||cancelUserForm.getCancellationOption().equals("one")||cancelUserForm.getCancellationOption().equals("")){
				try {
					db.setUserInactiveSOLACT(cancelUserForm.getStudentNr()); //if check box is selected
						} catch (Exception e) {
							eventTrackingService.post(
									eventTrackingService.newEvent(EventTrackingTypes.EVENT_CANCELUSER_ERROR, "JavaProxy error: "+e, false));

						    messages.add(ActionMessages.GLOBAL_MESSAGE,
									new ActionMessage("message.generalmessage", "The student myUnisa account was not cancelled, unexpected error occured."));
							addErrors(request, messages);

							return mapping.findForward("input");
						}//catch

						try {
						db1.setUserInactiveJOINACTIVATIONBlocked(cancelUserForm.getStudentNr());
						} catch (Exception e) {
							eventTrackingService.post(
									eventTrackingService.newEvent(EventTrackingTypes.EVENT_CANCELUSER_ERROR, "JavaProxy error: "+e, false));

						    messages.add(ActionMessages.GLOBAL_MESSAGE,
									new ActionMessage("message.generalmessage", "The student myUnisa account was not cancelled, unexpected error occured."));
							addErrors(request, messages);

							return mapping.findForward("input");
						}//catch
						try {
						  	   db.setUserInactiveSTUANN(cancelUserForm.getStudentNr()); //if check box is selected
						} catch (Exception e) {
									log.error("action: cancelUserAction method=save: (e) student="+cancelUserForm.getStudentNr()+" e="+e);
									eventTrackingService.post(
											eventTrackingService.newEvent(EventTrackingTypes.EVENT_CANCELUSER_ERROR, "JavaProxy error: "+e, false));
								    messages.add(ActionMessages.GLOBAL_MESSAGE,
											new ActionMessage("message.generalmessage", "The student myUnisa account was not cancelled, unexpected error occured."));
									addErrors(request, messages);

									return mapping.findForward("input");
						}//catch
						
               					messages.add(ActionMessages.GLOBAL_MESSAGE,
								new ActionMessage("message.generalmessage", "myUnisa user account ("+cancelUserForm.getStudentNr()+") was successfully cancelled and blocked."));
						        addMessages(request, messages);
						        eventTrackingService.post(
  					            		eventTrackingService.newEvent(EventTrackingTypes.EVENT_CANCELUSER_SUCCESS, cancelUserForm.getReasonForCans(), false));
							
					 	}
			        	if(cancelUserForm.getCancellationOption().equals("myLife")){
				                   messages.add(ActionMessages.GLOBAL_MESSAGE,
							       new ActionMessage("message.generalmessage", "The student myUnisa account is not active,cannot block the student."));
					               addErrors(request, messages);
			            }
				        	}else{
				        		if(cancelUserForm.getCancellationOption().equals("myLife")){
						        	String studentNr=cancelUserForm.getStudentNr();
						        	String blockedStatusStr=db1.getBlockedStatus(studentNr);
						        	if(blockedStatusStr.equalsIgnoreCase("nc")){
						           	          try {
										  	          db1.setMyLifeUserInactiveSOLACT(studentNr); //if check box is selected
										  	          messages.add(ActionMessages.GLOBAL_MESSAGE,
														     new ActionMessage("message.generalmessage", "! myLife user account "+studentNr+" was succesfully cancelled"));
												      addErrors(request, messages);
												      log.info("action: cancelUserAction method=save: (e) student="+studentNr+" was succesfully removed");
										       }catch (Exception e) {
										    	   eventTrackingService.post(
										    			   eventTrackingService.newEvent(EventTrackingTypes.EVENT_CANCELUSER_ERROR, "JavaProxy error: "+e, false));
												    messages.add(ActionMessages.GLOBAL_MESSAGE,
															new ActionMessage("message.generalmessage", "The student myUnisa account was not cancelled, unexpected error occured."));
													addErrors(request, messages);
													return mapping.findForward("input");
										      }//catch
						            }//if
						     }//if

		
		if(cancelUserForm.getCancellationOption().equals("both")){
	    	try{
	    	db1.setUserInactiveJOINACTIVATIONBoth(cancelUserForm.getStudentNr());
	    	}catch(Exception e){
	    		eventTrackingService.post(
	    				eventTrackingService.newEvent(EventTrackingTypes.EVENT_CANCELUSER_ERROR, "JavaProxy error: "+e, false));

			    messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "The student myUnisa account was not cancelled, unexpected error occured."));
				addErrors(request, messages);
				return mapping.findForward("input");
  		
	    	}
	    	try {
				db.setUserInactiveSOLACT(cancelUserForm.getStudentNr());
	    			} catch (Exception e) {
	    				eventTrackingService.post(
	    						eventTrackingService.newEvent(EventTrackingTypes.EVENT_CANCELUSER_ERROR, "JavaProxy error: "+e, false));

	    			    messages.add(ActionMessages.GLOBAL_MESSAGE,
	    						new ActionMessage("message.generalmessage", "The student myUnisa account was not cancelled, unexpected error occured."));
	    				addErrors(request, messages);

	    				return mapping.findForward("input");
	    			}

	    			try {
	    			db1.setUserInactiveJOINACTIVATION(cancelUserForm.getStudentNr());
	    			} catch (Exception e) {
	    				log.error("action: cancelUserAction method=save: (e) student="+cancelUserForm.getStudentNr()+" e="+e);
	    				eventTrackingService.post(
	    						eventTrackingService.newEvent(EventTrackingTypes.EVENT_CANCELUSER_ERROR, "JavaProxy error: "+e, false));

	    			    messages.add(ActionMessages.GLOBAL_MESSAGE,
	    						new ActionMessage("message.generalmessage", "The student myUnisa account was not cancelled, unexpected error occured."));
	    				addErrors(request, messages);

	    				return mapping.findForward("input");
	    			}			
	    			try {
	    				db.setUserInactiveSTUANN(cancelUserForm.getStudentNr()); //if check box is selected
	    					} catch (Exception e) {
	    						log.error("action: cancelUserAction method=save: (e) student="+cancelUserForm.getStudentNr()+" e="+e);
	    						
	    						eventTrackingService.post(
	    								eventTrackingService.newEvent(EventTrackingTypes.EVENT_CANCELUSER_ERROR, "JavaProxy error: "+e, false));

	    					    messages.add(ActionMessages.GLOBAL_MESSAGE,
	    								new ActionMessage("message.generalmessage", "The student myUnisa account was not cancelled, unexpected error occured."));
	    						addErrors(request, messages);

	    						return mapping.findForward("input");
	    					}
	    			
	    			messages.add(ActionMessages.GLOBAL_MESSAGE,
	    					new ActionMessage("message.generalmessage", "myUnisa and myLife user account ("+cancelUserForm.getStudentNr()+") was successfully cancelled."));
	    			addMessages(request, messages);

	    			eventTrackingService.post(
	    					eventTrackingService.newEvent(EventTrackingTypes.EVENT_CANCELUSER_SUCCESS, cancelUserForm.getReasonForCans(), false));
		}
		
		if(cancelUserForm.getCancellationOption().equals("one")){
	    	try{
	    	db1.setUserInactiveJOINACTIVATION(cancelUserForm.getStudentNr());
	    	}catch(Exception e){
	    		eventTrackingService.post(
	    				eventTrackingService.newEvent(EventTrackingTypes.EVENT_CANCELUSER_ERROR, "JavaProxy error: "+e, false));

			    messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "The student myUnisa account was not cancelled, unexpected error occured."));
				addErrors(request, messages);

				return mapping.findForward("input");
	    		
	    	}
	    	try {
					db.setUserInactiveSOLACT(cancelUserForm.getStudentNr());
	    			} catch (Exception e) {
	    				eventTrackingService.post(
	    						eventTrackingService.newEvent(EventTrackingTypes.EVENT_CANCELUSER_ERROR, "JavaProxy error: "+e, false));

	    			    messages.add(ActionMessages.GLOBAL_MESSAGE,
	    						new ActionMessage("message.generalmessage", "The student myUnisa account was not cancelled, unexpected error occured."));
	    				addErrors(request, messages);

	    				return mapping.findForward("input");
	    			}

	    			try {
	    			db1.setUserInactiveJOINACTIVATION(cancelUserForm.getStudentNr());
	    			} catch (Exception e) {
	    				log.error("action: cancelUserAction method=save: (e) student="+cancelUserForm.getStudentNr()+" e="+e);
	    				eventTrackingService.post(
	    						eventTrackingService.newEvent(EventTrackingTypes.EVENT_CANCELUSER_ERROR, "JavaProxy error: "+e, false));

	    			    messages.add(ActionMessages.GLOBAL_MESSAGE,
	    						new ActionMessage("message.generalmessage", "The student myUnisa account was not cancelled, unexpected error occured."));
	    				addErrors(request, messages);

	    				return mapping.findForward("input");
	    			}			
	    			try {
	    				db.setUserInactiveSTUANN(cancelUserForm.getStudentNr()); //if check box is selected
	    					} catch (Exception e) {
	    						log.error("action: cancelUserAction method=save: (e) student="+cancelUserForm.getStudentNr()+" e="+e);
	    						
	    						eventTrackingService.post(
	    								eventTrackingService.newEvent(EventTrackingTypes.EVENT_CANCELUSER_ERROR, "JavaProxy error: "+e, false));

	    					    messages.add(ActionMessages.GLOBAL_MESSAGE,
	    								new ActionMessage("message.generalmessage", "The student myUnisa account was not cancelled, unexpected error occured."));
	    						addErrors(request, messages);

	    						return mapping.findForward("input");
	    					}
	    			
	    			messages.add(ActionMessages.GLOBAL_MESSAGE,
	    					new ActionMessage("message.generalmessage", "myUnisa user account ("+cancelUserForm.getStudentNr()+") was successfully cancelled."));
	    			addMessages(request, messages);

	    			eventTrackingService.post(
	    					eventTrackingService.newEvent(EventTrackingTypes.EVENT_CANCELUSER_SUCCESS, cancelUserForm.getReasonForCans(), false));
	    }
			
			}
		  /*  try {
			db.insertAuditLog(cancelUserForm.getStudentNr());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	
			UserCleanupSakaiDAO db2=new UserCleanupSakaiDAO();
			String userId = "";
			String studentNr=cancelUserForm.getStudentNr();
			
			try {
				userId = db2.getUserId(studentNr);
			//	System.out.println("user Id  "+userId);
			} catch (Exception e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			authzGroupService = org.sakaiproject.authz.cover.AuthzGroupService.getInstance();
			
			//authzGroupService.getAuthzGroupsIsAllowed: the Set (String) of AuthzGroup ids in which this user is allowed to perform this function
	 		java.util.Set authzGroups = authzGroupService.getAuthzGroupsIsAllowed(userId , "site.visit", null); // (1)
			java.util.Iterator it = authzGroups.iterator(); // (2)
			User user = userDirectoryService.authenticate(
					ServerConfigurationService.getString("admin.user"),
					ServerConfigurationService.getString("admin.password"));
			
			if (user != null) {
		
				Session session = sessionManager.startSession();
			if (session == null) throw new JobExecutionException("No session");
	
				session.setUserEid(user.getId());
				//System.out.println("session user Eid "+session.getUserEid());
				session.setUserId(user.getId());
				//System.out.println("session user Id "+session.getUserId());
				sessionManager.setCurrentSession(session);

				while (it.hasNext()) {
					AuthzGroup group;
					try {
						group = authzGroupService.getAuthzGroup((String) it.next());
						group.removeMember(userId);
						authzGroupService.save(group);
												
					} catch (GroupNotDefinedException e1) {
						// TODO Auto-generated catch block
						System.out.println("error occured: "+e1);
						
						e1.printStackTrace();
					}
					//AuthzGroup group = authzGroups.get(i);
					catch (AuthzPermissionException e) {
						// TODO Auto-generated catch block
						System.out.println("error occured: "+e);
						e.printStackTrace();
					}
					
				}
		}*/
		    UsageSession usageSession = usageSessionService.startSession(cancelUserForm.getStudentNr(), request.getRemoteAddr(), request.getHeader("user-agent"));
		    request.getSession().setAttribute("UsageSession", usageSession);

		    eventTrackingService.post(
					eventTrackingService.newEvent(EventTrackingTypes.EVENT_CANCELUSER_SUCCESS, toolManager.getCurrentPlacement().getContext(), false), usageSession);
		    
		    cancelUserForm.setStudentNr("");
		    cancelUserForm.setReasonForCans("");
		    
			return mapping.findForward("input");
		}

	public ActionForward clear(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response){

		    CancelUserForm cancelUserForm1 = (CancelUserForm)form;
		    cancelUserForm1.setStudentNr("");

			return mapping.findForward("input");
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

