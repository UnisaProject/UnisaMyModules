//Created by MyEclipse Struts
// XSL source (default): platform:/plugin/com.genuitec.eclipse.cross.easystruts.eclipse_3.9.210/xslt/JavaClass.xsl

package za.ac.unisa.lms.tools.trackandtrace.actions;


import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.LookupDispatchAction;
import org.sakaiproject.event.api.EventTrackingService;
import org.sakaiproject.tool.api.Session;
import org.sakaiproject.tool.api.SessionManager;
import org.sakaiproject.tool.api.ToolManager;
import org.sakaiproject.user.api.User;
import org.sakaiproject.user.api.UserDirectoryService;

import za.ac.unisa.lms.constants.EventTrackingTypes;
import za.ac.unisa.lms.tools.trackandtrace.Student;
import za.ac.unisa.lms.tools.trackandtrace.forms.ParcelTrackingDisplayForm;
import za.ac.unisa.lms.tools.trackandtrace.forms.TrackAndTraceRecord;
import Smsij01h.Abean.Smsij01sMntGenDespatchInfo;
import org.sakaiproject.component.cover.ComponentManager;

/**
 * MyEclipse Struts
 * Creation date: 11-28-2005
 *
 * XDoclet definition:
 * @struts:action parameter="action" validate="true"
 */
public class DisplayParcelTrackingAction extends LookupDispatchAction {
	
	private SessionManager sessionManager;
	private UserDirectoryService userDirectoryService;
	private ToolManager toolManager;
	private EventTrackingService eventTrackingService;
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		if (request.getParameter("action") == null) return confirm(mapping, form, request, response);
		return super.execute(mapping, form, request, response);
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

	// --------------------------------------------------------- Instance Variables

	// --------------------------------------------------------- Methods


	protected Map getKeyMethodMap() {
	      Map map = new HashMap();
	      map.put("button.display", "confirm");
	      map.put("button.displayfd", "confirm");
	      map.put("button.clear","clear");
	      map.put("button.clearstno","clearInput");
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
	 * @throws Exception
	 */
	public ActionForward confirm(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response) {
         try{
		ParcelTrackingDisplayForm parcelTrackingDisplayForm = (ParcelTrackingDisplayForm) form;
		ActionMessages messages = new ActionMessages();
		
		messages = (ActionMessages) parcelTrackingDisplayForm.validate(mapping, request);
		
		eventTrackingService = (EventTrackingService) ComponentManager.get(EventTrackingService.class);
		toolManager = (ToolManager) ComponentManager.get(ToolManager.class);
		
		int tmpStudentNr = 0;
		
		if (!messages.isEmpty()) {
			addErrors(request, messages);
			return mapping.findForward("TrackandTraceInput");
		}
			tmpStudentNr = Integer.parseInt(parcelTrackingDisplayForm.getStudent().getNumber());
			/** Current Year */
			

				short currentYear = new Integer("0").shortValue();
				
				if (Calendar.getInstance().get(Calendar.MONTH) < 11) {
					 currentYear = new Integer(Calendar.getInstance().get(Calendar.YEAR)).shortValue();
					
					 //System.out.println( "year  "+currentYear);
				} else {
					 currentYear = new Integer(Calendar.getInstance().get(Calendar.YEAR) ).shortValue();
					 //System.out.println(currentYear);
				}

				Smsij01sMntGenDespatchInfo op = new Smsij01sMntGenDespatchInfo();
				
				operListener opl = new operListener();
				op.addExceptionListener(opl);
				op.clear();

				op.setInCsfClientServerCommunicationsClientVersionNumber((short)3);
				op.setInCsfClientServerCommunicationsClientRevisionNumber((short)1);
				op.setInCsfClientServerCommunicationsAction("D");
				op.setInCsfClientServerCommunicationsClientDevelopmentPhase("C");
				op.setInStudentAnnualRecordMkStudentNr(tmpStudentNr);
				op.setInStudentAnnualRecordMkAcademicYear(currentYear);
				op.setInSecurityWsPrinterCode("MYUNISA");
				
				//System.out.println(tmpStudentNr);
				op.execute();
				String Errormsg = op.getOutCsfStringsString500();
				if ((Errormsg != null) && (!Errormsg.equals(""))) {
					messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
							"error.coolgenerror", Errormsg));
					addErrors(request, messages);
					return mapping.findForward("TrackandTraceInput");
				} else {
					Student student = new Student();
					student.setNumber(String.valueOf(op.getOutStudentAnnualRecordMkStudentNr()));
					student.setName((String.valueOf(op.getOutWsStudentMkTitle())+ " " + String.valueOf(op.getOutWsStudentFirstNames()+" "+String.valueOf(op.getOutWsStudentSurname()))));
					parcelTrackingDisplayForm.setStudent(student);

					int count = op.getOutLuGroupCount();//count gives total no. of records belongs to that student
					Vector records = new Vector();
					
				

					for (int i = 0; i < count; i++) {
					

						
						if (!("".equals(op.getOutGWsTrackAndTraceNumber(i)))) {
							//DateFormat strDate = new SimpleDateFormat("dd-MM-yyyy");
							Calendar tmpDate = op.getOutGWsTrackAndTraceDate(i);
						
							int day = tmpDate.get(Calendar.DAY_OF_MONTH);
												
							int month = tmpDate.get(Calendar.MONTH)+1;
							
							 String tempmonth;//converting month to string to change the month format
							 String tempday;  //converting day to string to convert the day format.
							 
							if (month < 10){
								 tempmonth = "0".concat(Integer.toString(month));
							 
							}else {
								tempmonth=Integer.toString(month);
								
							}
							if(day<10){
								
								tempday = "0".concat(Integer.toString(day));
								
							}else{
								tempday=Integer.toString(day);
							}
					
							
							int year = tmpDate.get(Calendar.YEAR);
							if (currentYear == (new Integer(year).shortValue())||
									((currentYear-1) == (new Integer(year).shortValue()) && month>10 )){
								//set date in array list
								TrackAndTraceRecord trackAndTraceRecord = new TrackAndTraceRecord();
								if (op.getOutGWsTrackAndTraceNumber(i).substring(0,1).equals("C") ||
									op.getOutGWsTrackAndTraceNumber(i).substring(0,2).equals("RA") ||
									op.getOutGWsTrackAndTraceNumber(i).equalsIgnoreCase("PARCEL POSTED") ||
									isInteger(op.getOutGWsTrackAndTraceNumber(i).substring(0,1))){
									trackAndTraceRecord.setTrackTraceAgent("SAPO Safemail");
									trackAndTraceRecord.setTrackTraceNumber(op.getOutGWsTrackAndTraceNumber(i));
									if (!op.getOutGWsTrackAndTraceNumber(i).equalsIgnoreCase("PARCEL POSTED")){
										   trackAndTraceRecord.setTrackTraceNumber(op.getOutGWsTrackAndTraceNumber(i));
									}	
								
								}else{
									if (!op.getOutGWsTrackAndTraceNumber(i).substring(0,1).equals("S")){
										    trackAndTraceRecord.setTrackTraceAgent("Track n Trace");
									        trackAndTraceRecord.setTrackTraceNumber(op.getOutGWsTrackAndTraceNumber(i));
									        trackAndTraceRecord.setTrackTraceDate(year+"-"+tempmonth+"-"+tempday);
									}
								}
								records.add(trackAndTraceRecord);							
							}
						}
					}
					request.setAttribute("records", records);
					//parcelTrackingDisplayForm1.setTrackRecords(records);
				    eventTrackingService.post(
							eventTrackingService.newEvent(EventTrackingTypes.EVENT_TRACKANDTRACE_VIEW, toolManager.getCurrentPlacement().getContext(), false));

					return mapping.findForward("displayforward");
				}
         }catch(Exception ex){
        	      ActionMessages messages = new ActionMessages();
        	      messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"errors.exceptionhappened", "An unepxected Error has happened, please close this tool and log on again" ));
        	     return mapping.findForward("displayforward");
         }

		}
	

	/**
	 * Method input Determine if user is lecturer or student A student will go
	 * directly to display screen A lecter will go to input screen
	 */
	public ActionForward input(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			 {
       try{
		ParcelTrackingDisplayForm parcelTrackingDisplayForm = (ParcelTrackingDisplayForm) form;

		// Get user
		
		sessionManager = (SessionManager) ComponentManager.get(SessionManager.class);
		userDirectoryService = (UserDirectoryService) ComponentManager.get(UserDirectoryService.class);


		Session currentSession = sessionManager.getCurrentSession();
		String userId = currentSession.getUserId();
		
		User user = null;
		if (userId != null) {
			user = userDirectoryService.getUser(userId);
			request.setAttribute("user", user);
		}

		Student student = new Student();
		if ("Student".equalsIgnoreCase(user.getType())) {
			/*
			 * Student
			 */
			student.setNumber(currentSession.getUserEid());
			parcelTrackingDisplayForm.setStudent(student);
			parcelTrackingDisplayForm.setStudentuser(true);
			return confirm(mapping, form, request, response);
			
		} else if ("Instructor".equalsIgnoreCase(user.getType())) {
			/*
			 * Lecturer Get student nr input
			 */
			parcelTrackingDisplayForm.setStudent(student);
			parcelTrackingDisplayForm.setStudentuser(false);
			return mapping.findForward("TrackandTraceInput");
		} else {
			return mapping.findForward("unknownuser");
		}
	 }catch(Exception ex){
	            ActionMessages messages = new ActionMessages();
	            messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
				"errors.exceptionhappened", "An unepxected Error has happened, please enter you details again" ));
	            clearInput(mapping,form,request,response);
	           return mapping.findForward("TrackandTraceInput");
       }
	}
	public ActionForward clearInput(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		      try{
		            ParcelTrackingDisplayForm parcelTrackingDisplayForm = (ParcelTrackingDisplayForm) form;
		            Student student = new Student();
		            student.setNumber(String.valueOf(0));
		            parcelTrackingDisplayForm.setStudent(student);
		            return mapping.findForward("TrackandTraceInput");
	             }catch(Exception ex){
	                      ActionMessages messages = new ActionMessages();
	                      messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
				          "errors.exceptionhappened", "An unepxected Error has happened, please close this tool and log on again" ));
	                      return mapping.findForward("TrackandTraceInput");
               }

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
}


