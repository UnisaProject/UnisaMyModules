//Created by MyEclipse Struts
// XSL source (default): platform:/plugin/com.genuitec.eclipse.cross.easystruts.eclipse_3.9.210/xslt/JavaClass.xsl

package za.ac.unisa.lms.tools.satbook.actions;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Date;
import java.util.Calendar;
import java.util.StringTokenizer;
import java.text.DateFormat;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.LookupDispatchAction;
import org.apache.struts.util.LabelValueBean;
import org.sakaiproject.component.cover.ComponentManager;
import org.sakaiproject.component.cover.ServerConfigurationService;
import org.sakaiproject.email.api.EmailService;
import org.sakaiproject.event.api.UsageSession;
import org.sakaiproject.event.api.EventTrackingService;
import org.sakaiproject.event.api.UsageSessionService;
import org.sakaiproject.tool.api.Session;
import org.sakaiproject.tool.api.SessionManager;
import org.sakaiproject.tool.api.ToolManager;
import org.sakaiproject.user.api.UserDirectoryService;

import za.ac.unisa.lms.constants.EventTrackingTypes;
import za.ac.unisa.lms.tools.satbook.dao.SatbookBookingDAO;
import za.ac.unisa.lms.tools.satbook.dao.SatbookOracleDAO;
import za.ac.unisa.lms.tools.satbook.dao.SatbookDAO;
import za.ac.unisa.lms.tools.satbook.forms.AssistantRecord;
import za.ac.unisa.lms.tools.satbook.forms.BookingForm;
import za.ac.unisa.lms.tools.satbook.forms.ClassroomForm;
import za.ac.unisa.lms.tools.satbook.forms.SatbookDailyForm;
import za.ac.unisa.lms.tools.satbook.forms.SatbookMonthlyForm;

/**
 * MyEclipse Struts
 * Creation date: 09-11-2006
 *
 * XDoclet definition:
 * @struts:action parameter="action" validate="true"
 */
public class SatbookDailyAction extends LookupDispatchAction {

	// --------------------------------------------------------- Instance Variables
	String noEmailErrorMsg = "No e-mail address for this user so e-mail will not be send. " +
			"Login to http://staff.unisa.ac.za and please log a request on ICT Self Service " +
			"to add users unisa e-mail address.";
	
	private SessionManager sessionManager;
	private UserDirectoryService userDirectoryService;
	private UsageSessionService usageSessionService;
	private EventTrackingService eventTrackingService;
	private ToolManager toolManager;
	private EmailService emailService;
		
	// --------------------------------------------------------- Methods

	protected Map getKeyMethodMap() {
		Map map = new HashMap();

	    map.put("dailyview","dailyView");
	    map.put("button.dailyview.addbooking","addBooking");
	    map.put("dailyview.button.view","viewBooking");
	    map.put("dailyview.button.edit","editBooking");
	    map.put("button.dailyview.delete","deleteBookingConfirm");
	    map.put("dailyview.button.confirm","confirmBooking");
	    map.put("bookingsreport","bookingsReportInput");
	    map.put("booking.button.get","bookingsReportOutput");
	    map.put("booking.button.export","bookingsReportExport");
	    map.put("dailyview.button.unconfirm","unconfirmBooking");
	    map.put("dailyview.button.confirmdelete","deleteBooking");
	    map.put("dailyview.button.cancel", "cancelDelete");

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
	public ActionForward dailyView(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response) {
		
		sessionManager=(SessionManager) ComponentManager.get(SessionManager.class);

		SatbookDailyForm dailyForm = (SatbookDailyForm)form;
		ArrayList bookingList = new ArrayList();
		SatbookBookingDAO db1 = new SatbookBookingDAO();
		SatbookDAO db2 = new SatbookDAO();
		//BookingForm bookingForm = (BookingForm)request.getSession().getAttribute("bookingForm");
		SatbookMonthlyForm monthlyForm = (SatbookMonthlyForm)request.getSession().getAttribute("monthlyForm");
		dailyForm.setSystemID(monthlyForm.getSystemID());

		Session currentSession = sessionManager.getCurrentSession();
		String userID = currentSession.getUserId();
		try {
			dailyForm.setUserPermission(db2.getUserRights(userID));
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//if ((bookingForm == null)||(null == bookingForm.getStartDate())) {
			dailyForm.setViewDate(monthlyForm.getSelectedDate());
			String[] tmp = dailyForm.getViewDate().split("-");
			String tmpDay = tmp[0];
			String tmpMonth = tmp[1];
			String tmpYear = tmp[2];
			if (tmpDay.length() == 1) {
				tmpDay = "0"+tmpDay;
			}
			if (tmpMonth.length() == 1) {
				tmpMonth = "0"+tmpMonth;
			}
			dailyForm.setViewDate(tmpDay+"-"+tmpMonth+"-"+tmpYear);
		//} else {
			//dailyForm.setViewDate(bookingForm.getStartDate());
		//}

		// set the bookinglist

		String[] temp = null;
		String tmpDate;
		temp = dailyForm.getViewDate().split("-");
		tmpDate = temp[2]+"-"+temp[1]+"-"+temp[0];
		try {
			bookingList = db1.selectBookingList(tmpDate,null,"",dailyForm.getSystemID());
			dailyForm.setBookingList(bookingList);
			System.out.println("permission: "+dailyForm.getUserPermission());
			if(dailyForm.getUserPermission().equals("ACCESS")){
		 	    dailyForm.setBookingDisable(db1.validateAdvanceBooking(tmpDate));
			}else if(dailyForm.getUserPermission().equals("MAINTAIN")){
				dailyForm.setBookingDisable(true);
			}
			dailyForm.setDeleteDisable(db1.validateAdvanceBooking(tmpDate));
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// selectInstitution
		try {
			dailyForm.setInstitution(db1.selectInstitution(tmpDate));
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		request.setAttribute("dailyForm", dailyForm);
		request.setAttribute("bookingList",dailyForm.getBookingList());

		System.out.println("BookingDisable: "+dailyForm.getBookingDisable());


		return mapping.findForward("dailyview");
	}

	/**
	 * Method execute
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward viewBooking(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response) {
		
		sessionManager=(SessionManager) ComponentManager.get(SessionManager.class);

		SatbookDailyForm dailyForm = (SatbookDailyForm)form;
		ActionMessages messages = new ActionMessages();
		SatbookBookingDAO db1 = new SatbookBookingDAO();
		ArrayList bookingList = new ArrayList();
		
		if ((dailyForm.getSelectedBooking() == null)||
				(dailyForm.getSelectedBooking().length() == 0)) {
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Please choose a booking to view."));
			addErrors(request, messages);

			SatbookMonthlyForm monthlyForm = (SatbookMonthlyForm)request.getSession().getAttribute("monthlyForm");
			dailyForm.setViewDate(monthlyForm.getSelectedDate());
			

			SatbookDAO db2 = new SatbookDAO();

			dailyForm.setViewDate(monthlyForm.getSelectedDate());
			String[] tmp = dailyForm.getViewDate().split("-");
			String tmpDay = tmp[0];
			String tmpMonth = tmp[1];
			String tmpYear = tmp[2];
			if (tmpDay.length() == 1) {
				tmpDay = "0"+tmpDay;
			}
			if (tmpMonth.length() == 1) {
				tmpMonth = "0"+tmpMonth;
			}
			dailyForm.setViewDate(tmpDay+"-"+tmpMonth+"-"+tmpYear);

			// set the bookinglist

			String[] temp = null;
			String tmpDate;
			temp = dailyForm.getViewDate().split("-");
			tmpDate = temp[2]+"-"+temp[1]+"-"+temp[0];
			try {
				bookingList = db1.selectBookingList(tmpDate,null,"",dailyForm.getSystemID());
				dailyForm.setBookingList(bookingList);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// selectInstitution
			try {
				dailyForm.setInstitution(db1.selectInstitution(tmpDate));
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			/*
			//set rebroadcastdate
			String rb[] = dailyForm.getRebroadDate().split("-");
			dailyForm.setRebroadDay(rb[0]);
			dailyForm.setRebroadMonth(rb[1]);
			dailyForm.setRebroadYear(rb[2]);
			*/
			request.setAttribute("dailyForm", dailyForm);
			request.setAttribute("bookingList",dailyForm.getBookingList());


			Session currentSession = sessionManager.getCurrentSession();
			String userID = currentSession.getUserId();
			try {
				dailyForm.setUserPermission(db2.getUserRights(userID));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return mapping.findForward("dailyview");
		}

		request.setAttribute("bookingid",dailyForm.getSelectedBooking());

		return mapping.findForward("viewbooking");
	}

	/**
	 * Method execute
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward editBooking(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response) {
				
		sessionManager=(SessionManager) ComponentManager.get(SessionManager.class);
		
		SatbookDailyForm dailyForm = (SatbookDailyForm)form;
		ActionMessages messages = new ActionMessages();
		SatbookBookingDAO db1 = new SatbookBookingDAO();
		ArrayList bookingList = new ArrayList();

		if ((dailyForm.getSelectedBooking() == null)||
				(dailyForm.getSelectedBooking().length() == 0)) {
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Please choose a booking to edit."));
			addErrors(request, messages);

			SatbookMonthlyForm monthlyForm = (SatbookMonthlyForm)request.getSession().getAttribute("monthlyForm");
			dailyForm.setViewDate(monthlyForm.getSelectedDate());

			SatbookDAO db2 = new SatbookDAO();

			dailyForm.setViewDate(monthlyForm.getSelectedDate());
			String[] tmp = dailyForm.getViewDate().split("-");
			String tmpDay = tmp[0];
			String tmpMonth = tmp[1];
			String tmpYear = tmp[2];
			if (tmpDay.length() == 1) {
				tmpDay = "0"+tmpDay;
			}
			if (tmpMonth.length() == 1) {
				tmpMonth = "0"+tmpMonth;
			}
			dailyForm.setViewDate(tmpDay+"-"+tmpMonth+"-"+tmpYear);

			// set the bookinglist
			String[] temp = null;
			String tmpDate;
			temp = dailyForm.getViewDate().split("-");
			tmpDate = temp[2]+"-"+temp[1]+"-"+temp[0];
			try {
				bookingList = db1.selectBookingList(tmpDate,null,"",dailyForm.getSystemID());
				dailyForm.setBookingList(bookingList);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// selectInstitution
			try {
				dailyForm.setInstitution(db1.selectInstitution(tmpDate));
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			request.setAttribute("dailyForm", dailyForm);
			request.setAttribute("bookingList",dailyForm.getBookingList());


			Session currentSession = sessionManager.getCurrentSession();
			String userID = currentSession.getUserId();
			try {
				dailyForm.setUserPermission(db2.getUserRights(userID));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return mapping.findForward("dailyview");
		}

		request.setAttribute("bookingid",dailyForm.getSelectedBooking());

		return mapping.findForward("editbooking");
	}

	public ActionForward addBooking(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) {
		
			return mapping.findForward("bookingstep1");
	}

	/**
	 * Method execute
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward deleteBookingConfirm(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response) {
		
		sessionManager=(SessionManager) ComponentManager.get(SessionManager.class);
		
		SatbookDailyForm dailyForm = (SatbookDailyForm)form; // instance/reference of dailyviewform
		ActionMessages messages = new ActionMessages();
		SatbookBookingDAO db1 = new SatbookBookingDAO();
		SatbookDAO db2 = new SatbookDAO();
		
		BookingForm bookingForm = new BookingForm(); // creating a new object
		bookingForm.setBkngId(dailyForm.getSelectedBooking());
		
		String mayUpdate = "FALSE";
		//System.out.println("BOOKING ID "+dailyForm.getSelectedBooking());
		try {
			mayUpdate = db2.mayBookingBeUpdated(dailyForm.getSelectedBooking());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		request.setAttribute("dailyForm", dailyForm);
		request.setAttribute("bookingList",dailyForm.getBookingList());
		
		 	if ((dailyForm.getSelectedBooking() == null)||
				(dailyForm.getSelectedBooking().length() == 0)) {
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Please choose a booking to delete."));
			addErrors(request, messages);
		
			return mapping.findForward("dailyview");
	 	}
		 	
		try {
			db1.selectBkngMainDetail(bookingForm);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		 /* may booking be deleted by this users?
		  * user may only delete a booking that was created by himself and only if the booking was not confirmed 
		  */
		Session currentSession = sessionManager.getCurrentSession();
		String userID = currentSession.getUserId();
		String userEID = currentSession.getUserEid();
		try {
			dailyForm.setUserPermission(db2.getUserRights(userID));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if (dailyForm.getUserPermission().equals("ACCESS")) {
			if (!bookingForm.getCreatedBy().equals(userEID)) {
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "You may only delete your own bookings."));
				addErrors(request, messages);
			
				return mapping.findForward("dailyview");	
			}
			if (bookingForm.getBkngConfirmed().equals("Y")) {
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "Confirmed bookings may only be deleted by an administrator"));
				addErrors(request, messages);
			
				return mapping.findForward("dailyview");	
			}
		}

		
		String tmpBkngHeading = bookingForm.getHeading();
		dailyForm.setBkngHeading(bookingForm.getHeading());
		request.setAttribute("bkngHeading",tmpBkngHeading);
		
		return mapping.findForward("confirmbkngdelete");
		
		}
	
		
	
	//Cancel the delete 
	public ActionForward cancelDelete(ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response){
		
			SatbookDailyForm dailyForm = (SatbookDailyForm)form;	
			ActionMessages messages = new ActionMessages();
			request.setAttribute("dailyForm", dailyForm);
		    request.setAttribute("bookingList",dailyForm.getBookingList());
		    		    		
		    return mapping.findForward("dailyview");

			
	}
	
	
	
	/**
	 * Method execute
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward deleteBooking(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response) {
		
		sessionManager=(SessionManager) ComponentManager.get(SessionManager.class);
		eventTrackingService = (EventTrackingService) ComponentManager.get(EventTrackingService.class);
		usageSessionService = (UsageSessionService) ComponentManager.get(UsageSessionService.class);

		SatbookDailyForm dailyForm = (SatbookDailyForm)form;
		ActionMessages messages = new ActionMessages();
		SatbookBookingDAO db1 = new SatbookBookingDAO();
		SatbookDAO db2 = new SatbookDAO();
		SatbookOracleDAO db3 = new SatbookOracleDAO();
		
		ArrayList bookingList = new ArrayList();

		SatbookMonthlyForm monthlyForm = (SatbookMonthlyForm)request.getSession().getAttribute("monthlyForm");
		dailyForm.setViewDate(monthlyForm.getSelectedDate());

		Session currentSession = sessionManager.getCurrentSession();
		String userID = currentSession.getUserId();
		
		try {
			dailyForm.setUserPermission(db2.getUserRights(userID));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String mayUpdate = "FALSE";
		//System.out.println("BOOKING ID "+dailyForm.getSelectedBooking());
		try {
			mayUpdate = db2.mayBookingBeUpdated(dailyForm.getSelectedBooking());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		request.setAttribute("dailyForm", dailyForm);
		request.setAttribute("bookingList",dailyForm.getBookingList());

		 	if ((dailyForm.getSelectedBooking() == null)||
				(dailyForm.getSelectedBooking().length() == 0)) {
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Please choose a booking to delete."));
			addErrors(request, messages);

			dailyForm.setViewDate(monthlyForm.getSelectedDate());
			String[] tmp = dailyForm.getViewDate().split("-");
			String tmpDay = tmp[0];
			String tmpMonth = tmp[1];
			String tmpYear = tmp[2];
			if (tmpDay.length() == 1) {
				tmpDay = "0"+tmpDay;
			}
			if (tmpMonth.length() == 1) {
				tmpMonth = "0"+tmpMonth;
			}
			dailyForm.setViewDate(tmpDay+"-"+tmpMonth+"-"+tmpYear);

			// set the bookinglist

			String[] temp = null;
			String tmpDate;
			temp = dailyForm.getViewDate().split("-");
			tmpDate = temp[2]+"-"+temp[1]+"-"+temp[0];
			try {
				bookingList = db1.selectBookingList(tmpDate,null,"",dailyForm.getSystemID());
				dailyForm.setBookingList(bookingList);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// selectInstitution
			try {
				dailyForm.setInstitution(db1.selectInstitution(tmpDate));
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			request.setAttribute("dailyForm", dailyForm);
			request.setAttribute("bookingList",dailyForm.getBookingList());

			return mapping.findForward("dailyview");
				
				//return dailyView(mapping,form,request,response);
		}

		if ((mayUpdate.equals("FALSE"))&&(dailyForm.getUserPermission().equals("ACCESS"))) {
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Booking may not be deleted, it was already confirmed."));
			addErrors(request, messages);

			request.setAttribute("dailyForm", dailyForm);
			request.setAttribute("bookingList",dailyForm.getBookingList());

			return mapping.findForward("dailyview");
		}

		BookingForm bookingForm = new BookingForm();
		bookingForm.setBkngId(dailyForm.getSelectedBooking());
		try {
			db1.selectAllBkngDetail(bookingForm);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String tmpBkngHeading = bookingForm.getHeading();
		String tmpBkngStart = bookingForm.getStartDate();
		String tmpBkngTime = bookingForm.getStartHH()+":"+bookingForm.getStartMM()+" - "+
							bookingForm.getEndHH()+":"+bookingForm.getEndMM();
		String tmpLecturer = bookingForm.getLecturerNovellId();
		
		ArrayList subjectList = bookingForm.getSelectedSubjectsAL();
		
		String tmpSubjects = "";
		for (int i=0; i < subjectList.size(); i++) {
			LabelValueBean tmpSubjCode = (LabelValueBean) subjectList.get(i);
			tmpSubjects = tmpSubjects+tmpSubjCode.getValue()+"; ";
	
		}
		
		// delete the booking
		try {
			UsageSession usageSession = usageSessionService.getSession();
			eventTrackingService.post(
					eventTrackingService.newEvent(EventTrackingTypes.EVENT_SATBOOK_BKNG_DELETE, " booking id= "+dailyForm.getSelectedBooking(), false), usageSession);
			System.out.println("deleteBooking");
			db1.deleteBooking(dailyForm.getSelectedBooking());

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		messages.add(ActionMessages.GLOBAL_MESSAGE,
				new ActionMessage("message.generalmessage", "Booking was deleted successfully."));
		addMessages(request, messages);
	
		// send email to lecturer stating that his booking was deleted.
		String emailHeading = "Booking was cancelled.";
		String emailMessage = "<html><form>Your booking was cancelled for the following booking: "+
							"<b> Heading: "+tmpBkngHeading+" </b><br> "+
							//"<b> Subjects: "+tmpSubjects+"</b><br>"+
							"<b> Date: </b> "+tmpBkngStart+" <br> "+
							"<b> Time: </b> "+tmpBkngTime+" <br>"+
							"</html></form>";

		String lecturerEmailAddress = "";
		// select lecturer email address
		try {
			lecturerEmailAddress = db3.getLecturerEmailAddress(tmpLecturer);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		if (!lecturerEmailAddress.equals(" ")) {
			try {
				sendEmail(emailHeading,emailMessage,lecturerEmailAddress);
			} catch (AddressException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			// if lecturerEmailAddress == null
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", noEmailErrorMsg+" (user: "+bookingForm.getLecturerNovellId()+")"));
			addErrors(request, messages);
			messages.clear();
		}
		
		// select administrator email address.
		ArrayList emailList = new ArrayList();
		try {
			emailList = db2.selectAssistantList("1",true);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for (int k=0; k< emailList.size(); k++) {
			AssistantRecord assistant = (AssistantRecord) emailList.get(k);
			if (null != assistant.getAssistantEmail()) {
				try {
					sendEmail(emailHeading,emailMessage,assistant.getAssistantEmail());
				} catch (AddressException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		// send email to vc booking email group
		if (dailyForm.getSystemID().equals(dailyForm.getVenbook())) {
			String vcBookingEmail = ServerConfigurationService.getString("vcbookingEmail");
			if (null != vcBookingEmail) {
				try {
					sendEmail(emailHeading,emailMessage,vcBookingEmail);
				} catch (AddressException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		dailyForm.setViewDate(monthlyForm.getSelectedDate());
		String[] tmp = dailyForm.getViewDate().split("-");
		String tmpDay = tmp[0];
		String tmpMonth = tmp[1];
		String tmpYear = tmp[2];
		if (tmpDay.length() == 1) {
			tmpDay = "0"+tmpDay;
		}
		if (tmpMonth.length() == 1) {
			tmpMonth = "0"+tmpMonth;
		}
		dailyForm.setViewDate(tmpDay+"-"+tmpMonth+"-"+tmpYear);

		// set the bookinglist

		String[] temp = null;
		String tmpDate;
		temp = dailyForm.getViewDate().split("-");
		tmpDate = temp[2]+"-"+temp[1]+"-"+temp[0];
		try {
			bookingList = db1.selectBookingList(tmpDate,null,"",dailyForm.getSystemID());
			dailyForm.setBookingList(bookingList);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// selectInstitution
		try {
			dailyForm.setInstitution(db1.selectInstitution(tmpDate));
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		request.setAttribute("dailyForm", dailyForm);
		//request.setAttribute("bookingList",dailyForm.getBookingList());

		return mapping.findForward("dailyview");
	}
	
	
	
	
	/**
	 * Method execute
	 * @param confirmBooking
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward confirmBooking(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response) {
		
		eventTrackingService = (EventTrackingService) ComponentManager.get(EventTrackingService.class);
		sessionManager=(SessionManager) ComponentManager.get(SessionManager.class);

		SatbookDailyForm dailyForm = (SatbookDailyForm)form;
		ActionMessages messages = new ActionMessages();
		SatbookBookingDAO db1 = new SatbookBookingDAO();
		SatbookDAO db2 = new SatbookDAO();
		ArrayList bookingList = new ArrayList();
		String lecturerEmailAddress = "";
		
		SatbookMonthlyForm monthlyForm = (SatbookMonthlyForm)request.getSession().getAttribute("monthlyForm");
		dailyForm.setSystemID(monthlyForm.getSystemID());
		dailyForm.setViewDate(monthlyForm.getSelectedDate());

		request.setAttribute("dailyForm", dailyForm);
		request.setAttribute("bookingList",dailyForm.getBookingList());

		if ((dailyForm.getSelectedBooking() == null)||
				(dailyForm.getSelectedBooking().length() == 0)) {
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Please choose a booking to confirm."));
			addErrors(request, messages);

			dailyForm.setViewDate(monthlyForm.getSelectedDate());
			String[] tmp = dailyForm.getViewDate().split("-");
			String tmpDay = tmp[0];
			String tmpMonth = tmp[1];
			String tmpYear = tmp[2];
			if (tmpDay.length() == 1) {
				tmpDay = "0"+tmpDay;
			}
			if (tmpMonth.length() == 1) {
				tmpMonth = "0"+tmpMonth;
			}
			dailyForm.setViewDate(tmpDay+"-"+tmpMonth+"-"+tmpYear);
			// set the bookinglist

			String[] temp = null;
			String tmpDate;
			temp = dailyForm.getViewDate().split("-");
			tmpDate = temp[2]+"-"+temp[1]+"-"+temp[0];
			try {
				bookingList = db1.selectBookingList(tmpDate,null,"",dailyForm.getSystemID());
				dailyForm.setBookingList(bookingList);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// selectInstitution
			try {
				dailyForm.setInstitution(db1.selectInstitution(tmpDate));
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			request.setAttribute("dailyForm", dailyForm);
			request.setAttribute("bookingList",dailyForm.getBookingList());

			Session currentSession = sessionManager.getCurrentSession();
			String userID = currentSession.getUserId();
			try {
				dailyForm.setUserPermission(db2.getUserRights(userID));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return mapping.findForward("dailyview");
		}

		// confirm the booking
		try {
			db1.confirmBooking(dailyForm.getSelectedBooking(),"Y");
			// save log
			UsageSession usageSession = usageSessionService.getSession();
			eventTrackingService.post(
					eventTrackingService.newEvent(EventTrackingTypes.EVENT_SATBOOK_CONFIRM, " booking id= "+dailyForm.getSelectedBooking(), false), usageSession);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// select lecturer email address
		try {
			lecturerEmailAddress = db1.selectLecturerEmail(dailyForm.getSelectedBooking());
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		// get booking detail
		BookingForm bookingForm = new BookingForm();
		bookingForm.setBkngId(dailyForm.getSelectedBooking());
		try {
			db1.selectAllBkngDetail(bookingForm);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// email information 
		String emailHeading = "Your booking was confirmed.";
		String emailMessage = "<html><form>Your booking was confirmed for the following booking: "+
							"<b> Heading: "+bookingForm.getHeading()+" </b><br> "+
							"<b> Date: </b> "+bookingForm.getStartDate()+" <br> "+
							"<b> Time: </b> "+bookingForm.getStartHH()+":"+bookingForm.getStartMM()+" - "+
							bookingForm.getEndHH()+":"+bookingForm.getEndMM()+" <br>"+
							"</html></form>";
		
		// send email to lecturer stating that his booking was confirmed.
		if (!lecturerEmailAddress.equals(" ")) {
			try {
				sendEmail(emailHeading,emailMessage,lecturerEmailAddress);
			} catch (AddressException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			// if lecturerEmailAddress == null
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", noEmailErrorMsg+" (user: "+bookingForm.getLecturerNovellId()+")"));
			addErrors(request, messages);
			messages.clear();
		}
		
		if (dailyForm.getSystemID().equals(dailyForm.getSatbook())){
		      String emailHeadingForIctHelpDesk = "New Satellite Broadcast booking was confirmed.";
              String emailMessageForIctHelpDesk = "<html><form>Satellite Broadcast Booking was confirmed for the following booking: "+
                                      "<b> Heading: "+bookingForm.getHeading()+" </b><br> "+
                                      "<b> Date: </b> "+bookingForm.getStartDate()+" <br> "+
                                      "<b> Time: </b> "+bookingForm.getStartHH()+":"+bookingForm.getStartMM()+" - "+
                                      bookingForm.getEndHH()+":"+bookingForm.getEndMM()+" <br>"+
                                      "</html></form>";
		      String ictHelpdeskEmail = ServerConfigurationService.getString("ictHelpdeskEmail");
		      /** Remove SY 10 Mar 2011		
		      if (null != ictHelpdeskEmail) {
			       try {
		   		       sendEmail(emailHeadingForIctHelpDesk,emailMessageForIctHelpDesk,ictHelpdeskEmail);
			       } catch (AddressException e) {
   				      // TODO Auto-generated catch block
				      e.printStackTrace();
			       }//catch
		      } else {
		          	// if lecturerEmailAddress == null
			        messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "The ICT HelpDesk email was not found"));
			        addErrors(request, messages);
			        messages.clear();
		     }//else
		     */
		
			// select administrator email address.
			ArrayList emailList = new ArrayList();
			try {
				emailList = db2.selectAssistantList("1",true);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			for (int k=0; k< emailList.size(); k++) {
				AssistantRecord assistant = (AssistantRecord) emailList.get(k);
				if (null != assistant.getAssistantEmail()) {
					try {
						sendEmail(emailHeading,emailMessage,assistant.getAssistantEmail());
					} catch (AddressException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}	
		}//if satbook
		
		// for video conferencing send email to vcbookingEmail
		if (dailyForm.getSystemID().equals(dailyForm.getVenbook())) {
			emailHeading="New Video Conference booking Confirmed";
			emailMessage = "<html><form>New Video conference booking was placed and confirmed: <br><br>"+
				"<b> Heading: "+bookingForm.getHeading()+" </b><br> "+
				"<b> Booking placed by: "+bookingForm.getLecturerName()+" (tel: "+bookingForm.getLecturerNum1()+"; email: "+bookingForm.getEmail()+")<br>"+
				"<b> Date: </b> "+bookingForm.getStartDate()+" <br> "+
				"<b> Time: </b> "+bookingForm.getStartHH()+":"+bookingForm.getStartMM()+" - "+
				bookingForm.getEndHH()+":"+bookingForm.getEndMM()+" <br>"+
				"<b> Venues: </b> "+bookingForm.getClassroomStr()+" <br>"+
				"</html></form>";
			
			/** Remove SY 10 Mar 2011
			// send e-mail to ICT Helpdesk
			String ictHelpdeskEmail = ServerConfigurationService.getString("ictHelpdeskEmail");
		      if (null != ictHelpdeskEmail) {
			       try {
		   		       sendEmail(emailHeading,emailMessage,ictHelpdeskEmail);
			       } catch (AddressException e) {
 				      // TODO Auto-generated catch block
				      e.printStackTrace();
			       }//catch
		      } else {
		          	// if lecturerEmailAddress == null
			        messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "The ICT HelpDesk email was not found"));
			        addErrors(request, messages);
			        messages.clear();
		     }//else
		     */
		      
		    /** send e-mail to facilitators of venues */
				// send mail to venue facilitators
		        ArrayList classroomList = bookingForm.getSelectedClassroomsAL();
				for (int i=0; i< classroomList.size();i++) {
					LabelValueBean tmpClassCode = (LabelValueBean) classroomList.get(i);
					String email = "";
					// select e-mail address
					try {
						email =  db2.selectVenuesContact2(dailyForm.getSystemID(),tmpClassCode.getLabel());
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					try {
						if (!email.equals(" ")) {
							sendEmail(emailHeading,emailMessage,email);
						}
					} catch (AddressException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
		    
		}
		
		messages.add(ActionMessages.GLOBAL_MESSAGE,
				new ActionMessage("message.generalmessage", "Booking was confirmed."));
		addMessages(request, messages);

		dailyForm.setViewDate(monthlyForm.getSelectedDate());
		String[] tmp = dailyForm.getViewDate().split("-");
		String tmpDay = tmp[0];
		String tmpMonth = tmp[1];
		String tmpYear = tmp[2];
		if (tmpDay.length() == 1) {
			tmpDay = "0"+tmpDay;
		}
		if (tmpMonth.length() == 1) {
			tmpMonth = "0"+tmpMonth;
		}
		dailyForm.setViewDate(tmpDay+"-"+tmpMonth+"-"+tmpYear);

		// set the bookinglist

		String[] temp = null;
		String tmpDate;
		temp = dailyForm.getViewDate().split("-");
		tmpDate = temp[2]+"-"+temp[1]+"-"+temp[0];
		try {
			bookingList = db1.selectBookingList(tmpDate,null,"",dailyForm.getSystemID());
			dailyForm.setBookingList(bookingList);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// selectInstitution
		try {
			dailyForm.setInstitution(db1.selectInstitution(tmpDate));
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		request.setAttribute("dailyForm", dailyForm);
		request.setAttribute("bookingList",dailyForm.getBookingList());

		Session currentSession = sessionManager.getCurrentSession();
		String userID = currentSession.getUserId();
		
		try {
			dailyForm.setUserPermission(db2.getUserRights(userID));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return mapping.findForward("dailyview");
	}

	/**
	 * Method execute
	 * @param confirmBooking
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward unconfirmBooking(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response) {
		
		sessionManager=(SessionManager) ComponentManager.get(SessionManager.class);
		usageSessionService = (UsageSessionService) ComponentManager.get(UsageSessionService.class);
		eventTrackingService = (EventTrackingService) ComponentManager.get(EventTrackingService.class);

		SatbookDailyForm dailyForm = (SatbookDailyForm)form;
		ActionMessages messages = new ActionMessages();
		SatbookBookingDAO db1 = new SatbookBookingDAO();
		SatbookDAO db2 = new SatbookDAO();
		ArrayList bookingList = new ArrayList();
		String lecturerEmailAddress = "";

		SatbookMonthlyForm monthlyForm = (SatbookMonthlyForm)request.getSession().getAttribute("monthlyForm");
		dailyForm.setViewDate(monthlyForm.getSelectedDate());

		request.setAttribute("dailyForm", dailyForm);
		request.setAttribute("bookingList",dailyForm.getBookingList());

		if ((dailyForm.getSelectedBooking() == null)||
				(dailyForm.getSelectedBooking().length() == 0)) {
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Please choose a booking to unconfirm."));
			addErrors(request, messages);

				dailyForm.setViewDate(monthlyForm.getSelectedDate());
				String[] tmp = dailyForm.getViewDate().split("-");
				String tmpDay = tmp[0];
				String tmpMonth = tmp[1];
				String tmpYear = tmp[2];
				if (tmpDay.length() == 1) {
					tmpDay = "0"+tmpDay;
				}
				if (tmpMonth.length() == 1) {
					tmpMonth = "0"+tmpMonth;
				}
				dailyForm.setViewDate(tmpDay+"-"+tmpMonth+"-"+tmpYear);

				// set the bookinglist

				String[] temp = null;
				String tmpDate;
				temp = dailyForm.getViewDate().split("-");
				tmpDate = temp[2]+"-"+temp[1]+"-"+temp[0];
				try {
					bookingList = db1.selectBookingList(tmpDate,null,"",dailyForm.getSystemID());
					dailyForm.setBookingList(bookingList);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				// selectInstitution
				try {
					dailyForm.setInstitution(db1.selectInstitution(tmpDate));
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				request.setAttribute("dailyForm", dailyForm);
				request.setAttribute("bookingList",dailyForm.getBookingList());

				Session currentSession = sessionManager.getCurrentSession();
				String userID = currentSession.getUserId();
				try {
					dailyForm.setUserPermission(db2.getUserRights(userID));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			return mapping.findForward("dailyview");
		}

		boolean mayUpdate = false;
		try {
			mayUpdate = db1.mayUpdateBkng(dailyForm.getSelectedBooking());
		} catch (Exception e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		
		if (mayUpdate == true) {
			// confirm the booking
			try {
				db1.confirmBooking(dailyForm.getSelectedBooking(),"N");
				// save log
				UsageSession usageSession = usageSessionService.getSession();
				eventTrackingService.post(
						eventTrackingService.newEvent(EventTrackingTypes.EVENT_SATBOOK_UNCONFIRM, " booking id= "+dailyForm.getSelectedBooking(), false), usageSession);
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "Booking confirmation was cancelled."));
				addMessages(request, messages);
	
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	
			// select lecturer email address
			try {
				lecturerEmailAddress = db1.selectLecturerEmail(dailyForm.getSelectedBooking());
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} else {
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Booking may not be unconfirmed."));
			addErrors(request, messages);
		}
	
		
		// get booking detail
		BookingForm bookingForm = new BookingForm();
		bookingForm.setBkngId(dailyForm.getSelectedBooking());
		try {
			db1.selectAllBkngDetail(bookingForm);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		String emailHeading = "Your booking confirmation was cancelled.";
		String emailMessage = "<html><form>Your booking confirmation was cancelled for the following booking: "+
							"<b> Heading: "+bookingForm.getHeading()+" </b><br> "+
							"<b> Date: </b> "+bookingForm.getStartDate()+" <br> "+
							"<b> Time: </b> "+bookingForm.getStartHH()+":"+bookingForm.getStartMM()+" - "+
							bookingForm.getEndHH()+":"+bookingForm.getEndMM()+" <br>"+
							"</html></form>";
		
		// send email to lecturer stating that his booking was confirmed.
		if (!lecturerEmailAddress.equals(" ")) {
			try {
				sendEmail(emailHeading,emailMessage,lecturerEmailAddress);
			} catch (AddressException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			// if lecturerEmailAddress == null
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", noEmailErrorMsg+" (user: "+bookingForm.getLecturerNovellId()+")"));
			addErrors(request, messages);
			messages.clear();
		}
		
		String ictHelpdeskEmail = ServerConfigurationService.getString("ictHelpdeskEmail");
		if (ictHelpdeskEmail != null) {
			try {
				 sendEmail(emailHeading,emailMessage,ictHelpdeskEmail);
			} catch (AddressException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else {
			// if lecturerEmailAddress == null
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Could not find the e-mail of the ICT Help Desk"));
			addErrors(request, messages);
			messages.clear();
		}

		// select administrator email address.
		ArrayList emailList = new ArrayList();
		try {
			emailList = db2.selectAssistantList("1",true);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for (int k=0; k< emailList.size(); k++) {
			AssistantRecord assistant = (AssistantRecord) emailList.get(k);
			if (null != assistant.getAssistantEmail()) {
				try {
					sendEmail(emailHeading,emailMessage,assistant.getAssistantEmail());
				} catch (AddressException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}	
		dailyForm.setViewDate(monthlyForm.getSelectedDate());
		String[] tmp = dailyForm.getViewDate().split("-");
		String tmpDay = tmp[0];
		String tmpMonth = tmp[1];
		String tmpYear = tmp[2];
		if (tmpDay.length() == 1) {
			tmpDay = "0"+tmpDay;
		}
		if (tmpMonth.length() == 1) {
			tmpMonth = "0"+tmpMonth;
		}
		dailyForm.setViewDate(tmpDay+"-"+tmpMonth+"-"+tmpYear);

		// set the bookinglist

		String[] temp = null;
		String tmpDate;
		temp = dailyForm.getViewDate().split("-");
		tmpDate = temp[2]+"-"+temp[1]+"-"+temp[0];
		try {
			bookingList = db1.selectBookingList(tmpDate,null,"",dailyForm.getSystemID());
			dailyForm.setBookingList(bookingList);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// selectInstitution
		try {
			dailyForm.setInstitution(db1.selectInstitution(tmpDate));
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		request.setAttribute("dailyForm", dailyForm);
		request.setAttribute("bookingList",dailyForm.getBookingList());

		Session currentSession = sessionManager.getCurrentSession();
		String userID = currentSession.getUserId();
		
		try {
			dailyForm.setUserPermission(db2.getUserRights(userID));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return mapping.findForward("dailyview");
	}
	
	
	public void sendEmail(String subject, String body, String emailAddress) throws AddressException {
		
		emailService = (EmailService) ComponentManager.get(EmailService.class);

		//String tmpEmailFrom = ServerConfigurationService.getString("noReplyEmailFrom");
		String tmpEmailFrom = ServerConfigurationService.getString("vcbookingEmail");

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
		
		System.out.println("sending mail from "+iaReplyTo[0]+" to "+toEmail.toString());
		emailService.sendMail(iaReplyTo[0],iaTo,subject,body,iaHeaderTo,iaReplyTo,contentList);
		log.info("sending mail from "+iaReplyTo[0]+" to "+toEmail.toString());
	} // end of sendEmail

	/**
	 * Method execute
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward bookingsReportInput(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response) {

		SatbookDailyForm dailyForm = (SatbookDailyForm)form;
		ActionMessages messages = new ActionMessages();
		SatbookMonthlyForm monthlyForm = (SatbookMonthlyForm)request.getSession().getAttribute("monthlyForm");
		dailyForm.setSystemID(monthlyForm.getSystemID());
		System.out.println("DailyView: "+dailyForm.getSystemID());
		dailyForm.setBookingList(null);

		request.setAttribute("dailyForm", dailyForm);
		request.setAttribute("possibleReports",dailyForm.getPossibleReports());
		request.setAttribute("reportTypeList",dailyForm.getReportTypeList());

		return mapping.findForward("bookingsreport");
	}

	/**
	 * Method execute
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward bookingsReportOutput(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response) {

		SatbookDailyForm dailyForm = (SatbookDailyForm)form;
		ActionMessages messages = new ActionMessages();
		SatbookBookingDAO db1 = new SatbookBookingDAO();
		SatbookOracleDAO db2 = new SatbookOracleDAO();

		request.setAttribute("reportTypeList",dailyForm.getReportTypeList());
		request.setAttribute("possibleReports",dailyForm.getPossibleReports());

		/** REPORT TYPE
		 * Was type of report selected, for which bookings, confirmed/not confirmed/both
		 */
		if ((null == dailyForm.getSelectedReportType())||(dailyForm.getSelectedReportType().length() == 0)) {
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Please choose which bookings must be displayed from drop down list."));
			addMessages(request, messages);

			request.setAttribute("dailyForm", dailyForm);
			request.setAttribute("reportTypeList",dailyForm.getReportTypeList());

			return mapping.findForward("bookingsreport");
		}

		/** VALIDATE FROM DATE */
		// from date is mandatory
		if ((dailyForm.getFromDateDD() == null)||(dailyForm.getFromDateDD().length() == 0)||
				(dailyForm.getFromDateMM() == null)||(dailyForm.getFromDateMM().length() == 0)||
				(dailyForm.getFromDateYYYY() == null)||(dailyForm.getFromDateYYYY().length() == 0)) {
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Please enter the from date for the report."));
			addErrors(request, messages);

			request.setAttribute("dailyForm", dailyForm);

			return mapping.findForward("bookingsreport");
		}

		// validate start year
		if ((dailyForm.getFromDateYYYY() == null)||(dailyForm.getFromDateYYYY().length() < 4)) {
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Invalid year entered for the from date (must be 4 characters)."));
			addErrors(request, messages);

			request.setAttribute("dailyForm", dailyForm);
			return mapping.findForward("bookingsreport");
		}

		// validate start date (valid month, year, day)
		int startDayInt = Integer.parseInt(dailyForm.getFromDateDD());
		int startYearInt = Integer.parseInt(dailyForm.getFromDateYYYY());
		int startMonthInt = Integer.parseInt(dailyForm.getFromDateMM());

		// validate to month
		if (startMonthInt > 12) {
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Invalid month entered for the from date."));
			addErrors(request, messages);

			request.setAttribute("dailyForm", dailyForm);
			return mapping.findForward("bookingsreport");
		}

		if ((startMonthInt == 1)||(startMonthInt == 3)
				||(startMonthInt == 5)||(startMonthInt == 7)
				||(startMonthInt == 8)||(startMonthInt == 10)
				||(startMonthInt == 12)) {
			if (startDayInt > 31) {
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "Invalid day entered for from date."));
				addErrors(request, messages);

				request.setAttribute("dailyForm", dailyForm);
				return mapping.findForward("bookingsreport");
			}
		} else if ((startMonthInt == 4)||(startMonthInt == 6)
				||(startMonthInt == 9)||(startMonthInt == 11)) {
			if (startDayInt > 30) {
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "Invalid day entered for from date."));
				addErrors(request, messages);

				request.setAttribute("dailyForm", dailyForm);
				return mapping.findForward("bookingsreport");
			}
		} else if ((startMonthInt == 2)) {
			if (startYearInt % 4 == 0) {
				if (startDayInt > 29) {
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage", "Invalid day entered for from date."));
					addErrors(request, messages);

					request.setAttribute("dailyForm", dailyForm);
					return mapping.findForward("bookingsreport");
				}
			} else {
				if (startDayInt > 28) {
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage", "Invalid day entered for start date."));
					addErrors(request, messages);

					request.setAttribute("dailyForm", dailyForm);
					return mapping.findForward("bookingsreport");
				}
			}
		}

		// set from date:
		if (startDayInt <= 9) {
			dailyForm.setFromDateDD("0"+startDayInt);
		}
		if (startMonthInt <= 9) {
			dailyForm.setFromDateMM("0"+startMonthInt);
		}
		dailyForm.setFromDate(dailyForm.getFromDateYYYY()+"-"+dailyForm.getFromDateMM()+"-"+dailyForm.getFromDateDD());

		/** VALIDATE TO DATE */
		// to date is mandatory
		if ((dailyForm.getToDateDD() == null)||(dailyForm.getToDateDD().length() == 0)||
				(dailyForm.getToDateMM() == null)||(dailyForm.getToDateMM().length() == 0)||
				(dailyForm.getToDateYYYY() == null)||(dailyForm.getToDateYYYY().length() == 0)) {
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Please enter the to date for the report."));
			addErrors(request, messages);

			request.setAttribute("dailyForm", dailyForm);
			return mapping.findForward("bookingsreport");
		}

		// validate to year
		if ((dailyForm.getToDateYYYY() == null)||(dailyForm.getToDateYYYY().length() < 4)) {
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Invalid year entered for the to date (must be 4 characters)."));
			addErrors(request, messages);

			request.setAttribute("dailyForm", dailyForm);
			return mapping.findForward("bookingsreport");
		}

		// validate to date (valid month, year, day)
		int toDayInt = Integer.parseInt(dailyForm.getToDateDD());
		int toYearInt = Integer.parseInt(dailyForm.getToDateYYYY());
		int toMonthInt = Integer.parseInt(dailyForm.getToDateMM());

		// validate to month
		if (toMonthInt > 12) {
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Invalid month entered for the to date."));
			addErrors(request, messages);

			request.setAttribute("dailyForm", dailyForm);
			return mapping.findForward("bookingsreport");
		}

		if ((toMonthInt == 1)||(startMonthInt == 3)
				||(toMonthInt == 5)||(toMonthInt == 7)
				||(toMonthInt == 8)||(toMonthInt == 10)
				||(toMonthInt == 12)) {
			if (toDayInt > 31) {
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "Invalid day entered for to date."));
				addErrors(request, messages);

				request.setAttribute("dailyForm", dailyForm);
				return mapping.findForward("bookingsreport");
			}
		} else if ((toMonthInt == 4)||(toMonthInt == 6)
				||(toMonthInt == 9)||(toMonthInt == 11)) {
			if (toDayInt > 30) {
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "Invalid day entered for to date."));
				addErrors(request, messages);

				request.setAttribute("dailyForm", dailyForm);
				return mapping.findForward("bookingsreport");
			}
		} else if ((toMonthInt == 2)) {
			if (toYearInt % 4 == 0) {
				if (toDayInt > 29) {
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage", "Invalid day entered for to date."));
					addErrors(request, messages);

					request.setAttribute("dailyForm", dailyForm);
					return mapping.findForward("bookingsreport");
				}
			} else {
				if (toDayInt > 28) {
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage", "Invalid day entered for to date."));
					addErrors(request, messages);

					request.setAttribute("dailyForm", dailyForm);
					return mapping.findForward("bookingsreport");
				}
			}
		}

		// set to date:
		if (toDayInt <= 9) {
			dailyForm.setToDateDD("0"+toDayInt);
		}
		if (toMonthInt <= 9) {
			dailyForm.setToDateMM("0"+toMonthInt);
		}
		dailyForm.setToDate(dailyForm.getToDateYYYY()+"-"+dailyForm.getToDateMM()+"-"+dailyForm.getToDateDD());

		if (dailyForm.getSelectedReport().equals("DetailedReport")) {
			/** SELECT BOOKINGS */
			/** ===========  select bookings between from and to dates */
			try {
				dailyForm.setBookingList(db1.selectBookingList(dailyForm.getFromDate(),dailyForm.getToDate(),dailyForm.getSelectedReportType(),dailyForm.getSystemID()));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
	
			if (dailyForm.getSystemID().equals(dailyForm.getSatbook())) {
				for (int i=0; i < dailyForm.getBookingList().size(); i++) {
					// get booking subjects
					BookingForm booking = (BookingForm) dailyForm.getBookingList().get(i);
					try {
						db1.selectBkngSubjectDetial(booking);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					try {
						db2.selectSelectedSubject(booking);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} //for (int i=0; i < dailyForm.getBookingList().size(); i++) {
			} //if (dailyForm.getSystemID().equals(dailyForm.getSatbook())) {
		} else if (dailyForm.getSelectedReport().equals("DaysReport")) {
			//** SELECT BOOKINGS *//*
			//** ===========  select bookings between from and to dates *//*
			try {
				dailyForm.setVenueList(db1.selectBookingListXtypePerVenue(dailyForm.getFromDate(),dailyForm.getToDate(),dailyForm.getSelectedReportType(),dailyForm.getSystemID(),"DAYS"));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (dailyForm.getSelectedReport().equals("HoursReport")) {
			//** SELECT BOOKINGS *//*
			//** ===========  select bookings between from and to dates *//*
			try {
				dailyForm.setVenueList(db1.selectBookingListXtypePerVenue(dailyForm.getFromDate(),dailyForm.getToDate(),dailyForm.getSelectedReportType(),dailyForm.getSystemID(),"HOURS"));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		request.setAttribute("dailyForm", dailyForm);
		request.setAttribute("bookingList",dailyForm.getBookingList());
		request.setAttribute("venueList",dailyForm.getVenueList());
	 
		return mapping.findForward("bookingsreport");
	}


	
	public ActionForward bookingsReportExport(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) {
		
			//String path = "C:\\var\\"; // to test on localhost create var directory on c:drive
			String path = getServlet().getServletContext().getInitParameter("mypath");
			String filename = "";

			SatbookDailyForm dailyForm = (SatbookDailyForm)form;
			ActionMessages messages = new ActionMessages();
			SatbookBookingDAO db1 = new SatbookBookingDAO();
			SatbookOracleDAO db2 = new SatbookOracleDAO();
			
			request.setAttribute("reportTypeList",dailyForm.getReportTypeList());
			request.setAttribute("possibleReports",dailyForm.getPossibleReports());
			String fileHeading = "";

			//** REPORT TYPE
			// Was type of report selected, for which bookings, confirmed/not confirmed/both
			//*
			if ((null == dailyForm.getSelectedReportType())||(dailyForm.getSelectedReportType().length() == 0)) {
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "Please choose which bookings must be displayed from drop down list."));
				addMessages(request, messages);

				request.setAttribute("dailyForm", dailyForm);
				request.setAttribute("reportTypeList",dailyForm.getReportTypeList());

				return mapping.findForward("bookingsreport");
			}

			//** VALIDATE FROM DATE *//*
			// from date is mandatory
			if ((dailyForm.getFromDateDD() == null)||(dailyForm.getFromDateDD().length() == 0)||
					(dailyForm.getFromDateMM() == null)||(dailyForm.getFromDateMM().length() == 0)||
					(dailyForm.getFromDateYYYY() == null)||(dailyForm.getFromDateYYYY().length() == 0)) {
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "Please enter the from date for the report."));
				addErrors(request, messages);

				request.setAttribute("dailyForm", dailyForm);

				return mapping.findForward("bookingsreport");
			}

			// validate start year
			if ((dailyForm.getFromDateYYYY() == null)||(dailyForm.getFromDateYYYY().length() < 4)) {
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "Invalid year entered for the from date (must be 4 characters)."));
				addErrors(request, messages);

				request.setAttribute("dailyForm", dailyForm);
				return mapping.findForward("bookingsreport");
			}

			// validate start date (valid month, year, day)
			int startDayInt = Integer.parseInt(dailyForm.getFromDateDD());
			int startYearInt = Integer.parseInt(dailyForm.getFromDateYYYY());
			int startMonthInt = Integer.parseInt(dailyForm.getFromDateMM());
			

			// validate to month
			if (startMonthInt > 12) {
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "Invalid month entered for the from date."));
				addErrors(request, messages);

				request.setAttribute("dailyForm", dailyForm);
				return mapping.findForward("bookingsreport");
			}

			if ((startMonthInt == 1)||(startMonthInt == 3)
					||(startMonthInt == 5)||(startMonthInt == 7)
					||(startMonthInt == 8)||(startMonthInt == 10)
					||(startMonthInt == 12)) {
				if (startDayInt > 31) {
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage", "Invalid day entered for from date."));
					addErrors(request, messages);

					request.setAttribute("dailyForm", dailyForm);
					return mapping.findForward("bookingsreport");
				}
			} else if ((startMonthInt == 4)||(startMonthInt == 6)
					||(startMonthInt == 9)||(startMonthInt == 11)) {
				if (startDayInt > 30) {
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage", "Invalid day entered for from date."));
					addErrors(request, messages);

					request.setAttribute("dailyForm", dailyForm);
					return mapping.findForward("bookingsreport");
				}
			} else if ((startMonthInt == 2)) {
				if (startYearInt % 4 == 0) {
					if (startDayInt > 29) {
						messages.add(ActionMessages.GLOBAL_MESSAGE,
								new ActionMessage("message.generalmessage", "Invalid day entered for from date."));
						addErrors(request, messages);

						request.setAttribute("dailyForm", dailyForm);
						return mapping.findForward("bookingsreport");
					}
				} else {
					if (startDayInt > 28) {
						messages.add(ActionMessages.GLOBAL_MESSAGE,
								new ActionMessage("message.generalmessage", "Invalid day entered for start date."));
						addErrors(request, messages);

						request.setAttribute("dailyForm", dailyForm);
						return mapping.findForward("bookingsreport");
					}
				}
			}

			// set from date:
			if (startDayInt <= 9) {
				dailyForm.setFromDateDD("0"+startDayInt);
			}
			if (startMonthInt <= 9) {
				dailyForm.setFromDateMM("0"+startMonthInt);
			}
			dailyForm.setFromDate(dailyForm.getFromDateYYYY()+"-"+dailyForm.getFromDateMM()+"-"+dailyForm.getFromDateDD());

			//** VALIDATE TO DATE *//*
			// to date is mandatory
			if ((dailyForm.getToDateDD() == null)||(dailyForm.getToDateDD().length() == 0)||
					(dailyForm.getToDateMM() == null)||(dailyForm.getToDateMM().length() == 0)||
					(dailyForm.getToDateYYYY() == null)||(dailyForm.getToDateYYYY().length() == 0)) {
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "Please enter the to date for the report."));
				addErrors(request, messages);

				request.setAttribute("dailyForm", dailyForm);
				return mapping.findForward("bookingsreport");
			}

			// validate to year
			if ((dailyForm.getToDateYYYY() == null)||(dailyForm.getToDateYYYY().length() < 4)) {
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "Invalid year entered for the to date (must be 4 characters)."));
				addErrors(request, messages);

				request.setAttribute("dailyForm", dailyForm);
				return mapping.findForward("bookingsreport");
			}

			// validate to date (valid month, year, day)
			int toDayInt = Integer.parseInt(dailyForm.getToDateDD());
			int toYearInt = Integer.parseInt(dailyForm.getToDateYYYY());
			int toMonthInt = Integer.parseInt(dailyForm.getToDateMM());

			// validate to month
			if (toMonthInt > 12) {
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "Invalid month entered for the to date."));
				addErrors(request, messages);

				request.setAttribute("dailyForm", dailyForm);
				return mapping.findForward("bookingsreport");
			}

			if ((toMonthInt == 1)||(startMonthInt == 3)
					||(toMonthInt == 5)||(toMonthInt == 7)
					||(toMonthInt == 8)||(toMonthInt == 10)
					||(toMonthInt == 12)) {
				if (toDayInt > 31) {
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage", "Invalid day entered for to date."));
					addErrors(request, messages);

					request.setAttribute("dailyForm", dailyForm);
					return mapping.findForward("bookingsreport");
				}
			} else if ((toMonthInt == 4)||(toMonthInt == 6)
					||(toMonthInt == 9)||(toMonthInt == 11)) {
				if (toDayInt > 30) {
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage", "Invalid day entered for to date."));
					addErrors(request, messages);

					request.setAttribute("dailyForm", dailyForm);
					return mapping.findForward("bookingsreport");
				}
			} else if ((toMonthInt == 2)) {
				if (toYearInt % 4 == 0) {
					if (toDayInt > 29) {
						messages.add(ActionMessages.GLOBAL_MESSAGE,
								new ActionMessage("message.generalmessage", "Invalid day entered for to date."));
						addErrors(request, messages);

						request.setAttribute("dailyForm", dailyForm);
						return mapping.findForward("bookingsreport");
					}
				} else {
					if (toDayInt > 28) {
						messages.add(ActionMessages.GLOBAL_MESSAGE,
								new ActionMessage("message.generalmessage", "Invalid day entered for to date."));
						addErrors(request, messages);

						request.setAttribute("dailyForm", dailyForm);
						return mapping.findForward("bookingsreport");
					}
				}
			}
			
			// set to date:
			if (toDayInt <= 9) {
				dailyForm.setToDateDD("0"+toDayInt);
			}
			if (toMonthInt <= 9) {
				dailyForm.setToDateMM("0"+toMonthInt);
			}
			dailyForm.setToDate(dailyForm.getToDateYYYY()+"-"+dailyForm.getToDateMM()+"-"+dailyForm.getToDateDD());

			if (dailyForm.getSelectedReport().equals("DetailedReport")) {
				//** SELECT BOOKINGS *//*
				//** ===========  select bookings between from and to dates *//*
				try {
					dailyForm.setBookingList(db1.selectBookingList(dailyForm.getFromDate(),dailyForm.getToDate(),dailyForm.getSelectedReportType(),dailyForm.getSystemID()));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	
				if (dailyForm.getSystemID().equals(dailyForm.getSatbook())) {
					for (int i=0; i < dailyForm.getBookingList().size(); i++) {
						// get booking subjects
		
						BookingForm booking = (BookingForm) dailyForm.getBookingList().get(i);
						try {
							db1.selectBkngSubjectDetial(booking);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						try {
							db2.selectSelectedSubject(booking);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			} else if (dailyForm.getSelectedReport().equals("DaysReport")) {
				//** SELECT BOOKINGS *//*
				//** ===========  select bookings between from and to dates *//*
				try {
					dailyForm.setVenueList(db1.selectBookingListXtypePerVenue(dailyForm.getFromDate(),dailyForm.getToDate(),dailyForm.getSelectedReportType(),dailyForm.getSystemID(),"DAYS"));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else if (dailyForm.getSelectedReport().equals("HoursReport")) {
				//** SELECT BOOKINGS *//*
				//** ===========  select bookings between from and to dates *//*
				try {
					dailyForm.setVenueList(db1.selectBookingListXtypePerVenue(dailyForm.getFromDate(),dailyForm.getToDate(),dailyForm.getSelectedReportType(),dailyForm.getSystemID(),"HOURS"));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
  
        
			request.setAttribute("dailyForm", dailyForm);
			request.setAttribute("bookingList",dailyForm.getBookingList());
			request.setAttribute("venueList",dailyForm.getVenueList());




	if(dailyForm.getToDate() != null) {
		filename = "booking report "+dailyForm.getToDate() + ".csv";
	
	}else {
		filename =  "satbook_list.csv";
	}

	
	try {
		saveToServer(path,filename,request,dailyForm);
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	File file = new File(path + filename);
	DataInputStream in = null;
	try {
		in = new DataInputStream(new FileInputStream(file));
	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	ServletOutputStream out = null;
	try {
		out = response.getOutputStream();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	response.setDateHeader("Expires", 0);
	response.setHeader( "Pragma", "public" );
	response.setContentType("application/octet-stream");
	response.setContentLength((int)file.length());
	response.addHeader("Content-Disposition", "attachment;filename=" + filename );

	try {
		saveToClient(in, out);
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

	
	
	return null;
}
	
public void saveToClient(DataInputStream in, ServletOutputStream out) throws Exception {

		try{
			int w = in.read();
		

		while (w != -1) {
			out.write(w);
			w = in.read();
		}
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		out.flush();
		out.close();
		in.close();
	}

public void saveToServer(String path, String filename, HttpServletRequest request, SatbookDailyForm dailyForm) throws IOException {
	SatbookOracleDAO db2 = new SatbookOracleDAO();
	SatbookBookingDAO db1 = new SatbookBookingDAO();
	
	   File fileobject = new File(path + filename);
		FileWriter fw = new FileWriter(fileobject);
		
		List satbooklist;
		if (dailyForm.getSelectedReport().equals("DetailedReport")) {
			satbooklist = (List) request.getAttribute("bookingList");
		} else {
			satbooklist = (List) request.getAttribute("venueList");
		}
		
		String seperatewith = ",";
		BookingForm booking = new BookingForm();
		//for heading in xl sheet
		if (dailyForm.getSelectedReport().equals("DetailedReport")) {
			if (dailyForm.getSystemID().equals(dailyForm.getVenbook())) {
				fw.write("Venue Booking Report - Detailed Report");
			} else {
				fw.write("Satellite Booking Report ");
			}
		}
		if ((dailyForm.getSystemID().equals(dailyForm.getVenbook()))&&(dailyForm.getSelectedReport().equals("DaysReport"))) {
			fw.write("Venue Booking Report - Days per Venue per Month");
		}
		if ((dailyForm.getSystemID().equals(dailyForm.getVenbook()))&&(dailyForm.getSelectedReport().equals("HoursReport"))) {
			fw.write("Venue Booking Report - Hours per Venue per Month");
		}
		fw.write(" "+((char)13)+((char)10)); //to get the records in the next line
		fw.write("Report date: "+db1.getDateTime());
		fw.write(" "+((char)13)+((char)10)); //to get the records in the next line
		fw.write("Between dates: "+dailyForm.getFromDateDD()+"-"+dailyForm.getFromDateMM()+"-"+dailyForm.getFromDateYYYY()+
			    " and "+dailyForm.getToDateDD()+"-"+dailyForm.getToDateMM()+"-"+dailyForm.getToDateYYYY());
		fw.write(" "+((char)13)+((char)10)); //to get the records in the next line
		if (dailyForm.getSelectedReportType().equals("Y")) {
			fw.write("Bookings confirmed");
		} else if (dailyForm.getSelectedReportType().equals("N")) {
			fw.write("Bookings not confirmed");
		} else {
			fw.write("Bookings confirmed and not confirmed");
		}
		fw.write(" "+((char)13)+((char)10)); //to get the records in the next line
				
		if (dailyForm.getSystemID().equals(dailyForm.getSatbook())) {
			fw.write( ("  Date MM/DD/YYYY")+ seperatewith+("   Day")+ seperatewith +("   Time ")+ seperatewith +("   Lecturer")+ seperatewith +("   Subject Code")+ seperatewith +("   Subject Name")+ seperatewith 
					+("   Registration ")+ seperatewith+("   Region ")+ seperatewith);
		} else {
			if (dailyForm.getSelectedReport().equals("DetailedReport")) {
				fw.write( ("  Date MM/DD/YYYY")+ seperatewith+("   Day")+ seperatewith +("   Time ")+ seperatewith +("   User")+ seperatewith +("   Department")+ seperatewith +("   Heading")+ seperatewith +("   Confirmed")+ seperatewith +("   Type of Booking")+ seperatewith+("   Venues")+ seperatewith);
			} else if (dailyForm.getSelectedReport().equals("DaysReport")) {
				fw.write( ("  Month")+ seperatewith+("   Venue")+ seperatewith +("   Number of Days ")+ seperatewith);
			} else if (dailyForm.getSelectedReport().equals("HoursReport")) {
				fw.write( ("  Month")+ seperatewith+("   Venue")+ seperatewith +("   Number of Hours ")+ seperatewith);
			}
		}
		
		fw.write(" "+((char)13)+((char)10)); //to get the records in the next line
	
		/** **********************************************************/
		/** SATBOOK + VENBOOK Detailed Report */
		/** **********************************************************/
		if (dailyForm.getSelectedReport().equals("DetailedReport")) {
			for(int i=0; i < satbooklist.size(); i++) {
				
				fw.write(((BookingForm)satbooklist.get(i)).getStartDate() + seperatewith);
			
				String date =((BookingForm)satbooklist.get(i)).getStartDate();
			
				String weekday="";
				try{	
					DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	        
					String date1 = formatter.parse(date).toString(); 
					weekday=date1.substring(0,3);
					// System.out.println("Today is " +weekday);
	            
				} catch (Exception e) {
					System.out.println("Exception :"+e);   
				}   
	     
				fw.write((weekday) + seperatewith);
			    
		
				fw.write(((BookingForm)satbooklist.get(i)).getStartHH()+":");
				fw.write(((BookingForm)satbooklist.get(i)).getStartMM() + "-");
				fw.write(((BookingForm)satbooklist.get(i)).getEndHH() + ":");
				fw.write(((BookingForm)satbooklist.get(i)).getEndMM()+seperatewith);
				
				fw.write(((BookingForm)satbooklist.get(i)).getLecturerName() + seperatewith);
				if (dailyForm.getSystemID().equals(dailyForm.getVenbook())) {
					fw.write(((BookingForm)satbooklist.get(i)).getDpt() + seperatewith);
				}
			
				if (dailyForm.getSystemID().equals(dailyForm.getSatbook())) {
					//to find selected subject codes
					ArrayList selectedSubjects=((BookingForm)satbooklist.get(i)).getSelectedSubjectsAL();
					String tmpSubjectCode = "";
					for(int j=0;j<selectedSubjects.size();j++){
						
						LabelValueBean tmpSubjCode = (LabelValueBean) selectedSubjects.get(j);
						tmpSubjectCode = tmpSubjectCode+tmpSubjCode.getValue()+"; ";
									
					}
					fw.write(tmpSubjectCode +seperatewith);
					//the code to get corresponding subject name
					ArrayList subjectNamesList=((BookingForm)satbooklist.get(i)).getSelectedSubjectNamesAL();
					String tmpSubjectName = "";
				    String 	tmpString = "";
					for(int k=0;k<subjectNamesList.size();k++){
					
						LabelValueBean tmpSubjName = (LabelValueBean) subjectNamesList.get(k);
						tmpSubjectName =tmpSubjectName+tmpSubjName.getValue()+"; ";
						
						//System.out.println("tmpSubjectName  "+tmpSubjectName);
						  tmpString = tmpSubjectName.replace(',',' ');
									
					}
					fw.write((tmpString)+seperatewith);
					
					fw.write(((BookingForm)satbooklist.get(i)).getRegistrationPeriodDesc() + seperatewith);
	
					fw.write("ALL" + seperatewith);
				}
				
				if (dailyForm.getSystemID().equals(dailyForm.getVenbook())) {
					fw.write(((BookingForm)satbooklist.get(i)).getHeading()+seperatewith);
					fw.write(((BookingForm)satbooklist.get(i)).getBkngConfirmed()+seperatewith);
					fw.write(((BookingForm)satbooklist.get(i)).getBkngTypeName()+seperatewith);
					fw.write(((BookingForm)satbooklist.get(i)).getClassroomStr()+seperatewith);
				}
				fw.write(" "+((char)13)+((char)10)); //to get the records in the next line
			} //end for
		}
		
		/** **********************************************************/
		/** VENBOOK DAYS PER VENUE */
		/** **********************************************************/
		if ((dailyForm.getSystemID().equals(dailyForm.getVenbook()))&&(dailyForm.getSelectedReport().equals("DaysReport"))) {
			for(int i=0; i < satbooklist.size(); i++) {
				
				fw.write(((ClassroomForm)satbooklist.get(i)).getMonth() + seperatewith);
				fw.write(((ClassroomForm)satbooklist.get(i)).getRegionDesc() + seperatewith);
				fw.write(((ClassroomForm)satbooklist.get(i)).getNumberOfDays() + seperatewith);
				fw.write(" "+((char)13)+((char)10)); //to get the records in the next line
			}
		}
	
		/** **********************************************************/
		/** VENBOOK HOURS PER VENUE */
		/** **********************************************************/
		if ((dailyForm.getSystemID().equals(dailyForm.getVenbook()))&&(dailyForm.getSelectedReport().equals("HoursReport"))) {
			for(int i=0; i < satbooklist.size(); i++) {
				
				fw.write(((ClassroomForm)satbooklist.get(i)).getMonth() + seperatewith);
				fw.write(((ClassroomForm)satbooklist.get(i)).getRegionDesc() + seperatewith);
				fw.write(((ClassroomForm)satbooklist.get(i)).getNumberOfDays() + seperatewith);
				fw.write(" "+((char)13)+((char)10)); //to get the records in the next line
			}
		}
		
		fw.flush();
		fw.close();
	}
  
}
