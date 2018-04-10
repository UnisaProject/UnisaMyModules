//Created by MyEclipse Struts
// XSL source (default): platform:/plugin/com.genuitec.eclipse.cross.easystruts.eclipse_3.9.210/xslt/JavaClass.xsl

package za.ac.unisa.lms.tools.satbook.actions;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
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
import org.sakaiproject.event.api.UsageSessionService;
import org.sakaiproject.event.api.EventTrackingService;
import org.sakaiproject.tool.api.Session;
import org.sakaiproject.tool.api.SessionManager;
import org.sakaiproject.tool.api.ToolManager;

import za.ac.unisa.lms.constants.EventTrackingTypes;
import za.ac.unisa.lms.tools.satbook.dao.SatbookBookingDAO;
import za.ac.unisa.lms.tools.satbook.dao.SatbookDAO;
import za.ac.unisa.lms.tools.satbook.dao.SatbookOracleDAO;
import za.ac.unisa.lms.tools.satbook.forms.AssistantRecord;
import za.ac.unisa.lms.tools.satbook.forms.AssistantTypeRecord;
import za.ac.unisa.lms.tools.satbook.forms.BookingForm;
import za.ac.unisa.lms.tools.satbook.forms.LecturerForm;
import za.ac.unisa.lms.tools.satbook.forms.SatbookBkngMaterialRecord;
import za.ac.unisa.lms.tools.satbook.forms.SatbookDailyForm;
import za.ac.unisa.lms.tools.satbook.forms.SatbookMonthlyForm;

/**
 * MyEclipse Struts
 * Creation date: 09-11-2006
 *
 * XDoclet definition:
 * @struts:action parameter="action" validate="true"
 */
public class SatbookBookingAction extends LookupDispatchAction {

	// --------------------------------------------------------- Instance Variables
	String noEmailErrorMsg = "No e-mail address for this user so e-mail will not be send. " +
			"Login to http://staff.unisa.ac.za and please log a request on ICT Self Service " +
			"to add users unisa e-mail address.";

	private SessionManager sessionManager;
	private UsageSessionService usageSessionService;
	private EventTrackingService eventTrackingService;
	private ToolManager toolManager;
	private EmailService emailService;

	// --------------------------------------------------------- Methods

	protected Map getKeyMethodMap() {
		Map map = new HashMap();

	    map.put("button.continue","goToMethod");
	    map.put("monthlyview","monthlyView");
	    map.put("bookingstep1","bookingStep1");
	    map.put("viewbooking","viewBooking");
	    map.put("editbooking","editBooking");
	    map.put("button.bookingstep1.continue","continueStep1");
	    map.put("button.bookingstep2.continue2","continueStep2");
	    map.put("button.bookingstep3.continue3","continueStep3");
	    map.put("button.bookingstep4.continue4","continueStep4");
	    map.put("button.bookingstep5.continue5","continueStep5");
	    map.put("button.bookingstep6.continue6","saveBooking");
	    map.put("button.booking.backstep1","bookingStep1");
	    map.put("button.booking.backstep2","backToStep2");
	    map.put("button.booking.backstep3","backToStep3");
	    map.put("button.booking.backstep4","backToStep4");
	    map.put("button.booking.backstep5","backToStep5");
	    map.put("button.booking.dialyview","dailyview");
	    map.put("button.back","goBackTo");
	    map.put("button.cancel","cancel");
	    map.put("button.booking.editheading","editHeading");
	    map.put("button.booking.editsubjects","editSubjects");
	    map.put("button.booking.editmaterials","editMaterials");
	    map.put("button.booking.editvisitors","editVisitors");
	    map.put("button.booking.editclassrooms","editClassrooms");
	    map.put("button.booking.editadmin","editAdmin");
	    map.put("button.booking.save","saveAdmin");
	    map.put("bookingstep5.button.all","selectAllClassrooms");
	    map.put("bookingstep5.button.deselect","deselectAllClassrooms");
	    map.put("print","bookingPrint");

	    return map;
	}

	/**
	 * Method gotoMethod
	 * 		to decide what is the next method to be executed
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward goToMethod(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response)throws Exception {

		String gotoStepValidate;
		if ("1".equalsIgnoreCase(request.getParameter("atstep"))){
			// if at step 1 go to validations of step1
			gotoStepValidate = continueStep1(mapping,form,request, response);
		} else if ("2".equalsIgnoreCase(request.getParameter("atstep"))){
			gotoStepValidate = continueStep2(mapping,form,request, response);
		} else if ("3".equalsIgnoreCase(request.getParameter("atstep"))){
			gotoStepValidate = continueStep3(mapping,form,request, response);
		} else if ("4".equalsIgnoreCase(request.getParameter("atstep"))){
			gotoStepValidate = continueStep4(mapping,form,request, response);
		} else if ("5".equalsIgnoreCase(request.getParameter("atstep"))){
			gotoStepValidate = continueStep5(mapping,form,request, response);
		} else if ("6".equalsIgnoreCase(request.getParameter("atstep"))){
			gotoStepValidate = saveBooking(mapping,form,request, response);
		} else {
			// default go to step 1
			return mapping.findForward("step1");
		}

		return mapping.findForward(gotoStepValidate);
	}


	public ActionForward bookingStep1(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) {
		
		sessionManager=(SessionManager) ComponentManager.get(SessionManager.class);
		
		BookingForm bookingForm = (BookingForm)form;
		SatbookDAO db1 = new SatbookDAO();
		SatbookMonthlyForm monthlyForm = (SatbookMonthlyForm)request.getSession().getAttribute("monthlyForm");
		Session currentSession =sessionManager.getCurrentSession();

		// Select the systemID 1=satbook; 2=venbook
		bookingForm.setSystemID(monthlyForm.getSystemID());
		
		String userID = currentSession.getUserEid();
		bookingForm.setCreatedBy(userID);
		
		// get the user permissions
		try {
			bookingForm.setUserPermission(db1.getUserRights(currentSession.getUserId()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		bookingForm.setPageStatus("ADD");
		try {
			bookingForm.setBkngTypeList(db1.selectBkngTypeList(bookingForm.getSystemID(),"label"));
		} catch (Exception e) {
			e.printStackTrace();
		}

		// set lists
		request.setAttribute("monthList", bookingForm.getMonthList());
		request.setAttribute("yearList", bookingForm.getYearList());
		request.setAttribute("registrationPeriodList",bookingForm.getRegistrationPeriodList());
		request.setAttribute("pageStatus",bookingForm.getPageStatus());
		request.setAttribute("bkngTypeList",bookingForm.getBkngTypeList());
		
		// to remove 0 before month (02=feb)
		int startMonthInt;
		if ((bookingForm.getStartMonth() != null)&&(!bookingForm.getStartMonth().equals(""))) {
			startMonthInt = Integer.parseInt(bookingForm.getStartMonth());
			bookingForm.setStartMonth(Integer.toString(startMonthInt));
		}
				
		return mapping.findForward("bookingstep1");
	}

	public String continueStep1(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		
			sessionManager=(SessionManager) ComponentManager.get(SessionManager.class);
			usageSessionService = (UsageSessionService) ComponentManager.get(UsageSessionService.class);
			eventTrackingService = (EventTrackingService) ComponentManager.get(EventTrackingService.class);

		// *****  Variables
		BookingForm bookingForm = (BookingForm)form;
		System.out.println("system: "+bookingForm.getSystemID());
		ActionMessages messages = new ActionMessages();
		SatbookBookingDAO db2 = new SatbookBookingDAO();
		SatbookDAO db = new SatbookDAO();
		
		// SATBOOK set variables for bookingForm bean	
		if (bookingForm.getSystemID().equals(bookingForm.getSatbook())) {
			if (bookingForm.getPageStatus().equals("ADD")) {
				bookingForm.setHeading(request.getParameter("heading").replace('\'',' '));
				//bookingForm.setLecturerNovellId(request.getParameter("lecturerNovellId").replace('\'',' '));
				bookingForm.setStartDay(request.getParameter("startDay"));
				bookingForm.setStartMonth(request.getParameter("startMonth"));
				bookingForm.setStartYear(request.getParameter("startYear"));
				bookingForm.setEndDay(request.getParameter("startDay"));
				bookingForm.setEndMonth(request.getParameter("startMonth"));
				bookingForm.setEndYear(request.getParameter("startYear"));
	
			} else { // for edit mode
				bookingForm.setEndDay(bookingForm.getStartDay());
				bookingForm.setEndMonth(bookingForm.getStartMonth());
				bookingForm.setEndYear(bookingForm.getStartYear());		
			}
			
			if (null != request.getParameter("rebroadcast")) {	
				if (request.getParameter("rebroadcast").equals("true")){
		            bookingForm.setRebroadcast("true");
		            bookingForm.setRebroadDay(bookingForm.getRebroadDay());
		            bookingForm.setRebroadMonth(bookingForm.getRebroadMonth());
		            bookingForm.setRebroadYear(bookingForm.getRebroadYear());
		            
				}else if (request.getParameter("rebroadcast").equals("false")) {
					bookingForm.setRebroadcast("false");
				}
			}
		} // end of set variables for SATBOOK

		// VENBOOK set variables for bookingForm bean
		if (bookingForm.getSystemID().equals(bookingForm.getVenbook())) {
			if (bookingForm.getPageStatus().equals("ADD")) {
				bookingForm.setHeading(request.getParameter("heading").replace('\'',' '));
				bookingForm.setStartDay(request.getParameter("startDay"));
				bookingForm.setStartMonth(request.getParameter("startMonth"));
				bookingForm.setStartYear(request.getParameter("startYear"));
				bookingForm.setEndDay(request.getParameter("startDay"));
				bookingForm.setEndMonth(request.getParameter("startMonth"));
				bookingForm.setEndYear(request.getParameter("startYear"));
				bookingForm.setBkngType(request.getParameter("bkngType"));
				
							
			} else {
				bookingForm.setEndDay(bookingForm.getStartDay());
				bookingForm.setEndMonth(bookingForm.getStartMonth());
				bookingForm.setEndYear(bookingForm.getStartYear());
				bookingForm.setBkngType(request.getParameter("bkngType"));
				
			}
		} // end of set variables for VENBOOK

		// set list for if there is errors and have to return to step 1
		request.setAttribute("monthList", bookingForm.getMonthList());
		request.setAttribute("yearList", bookingForm.getYearList());
		request.setAttribute("registrationPeriodList",bookingForm.getRegistrationPeriodList());
		request.setAttribute("pageStatus",bookingForm.getPageStatus());
		request.setAttribute("bkngTypeList",bookingForm.getBkngTypeList());
		
		
		/** ***** HEADING VALIDATIONS *****
		 *  heading is mandatory
		 */
		if ((bookingForm.getHeading()==null)||(bookingForm.getHeading().length()<=0)||
				bookingForm.getHeading().equals("0")) {

			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Please enter the heading for the booking."));
			addErrors(request, messages);

			request.setAttribute("bookingForm", bookingForm);
			return "bookingstep1";
		}

		/** *****  SATBOOK: LECTURER VALIDATIONS *****
		 * network id is mandatory
		 * validate lecturer id against database
		 */
		if (bookingForm.getSystemID().equals(bookingForm.getSatbook())) {

			// lecturer network id is mandatory
			if ((bookingForm.getLecturerNovellId()==null)||(bookingForm.getLecturerNovellId().length()<=0)||
					bookingForm.getLecturerNovellId().equals("0")) {
	
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "Please enter the Lecturer's network code."));
				addErrors(request, messages);
	
				request.setAttribute("bookingForm", bookingForm);
				return "bookingstep1";
			}
	
			// validate lecturer against database
			String lecturerDetail = validateLecturer(bookingForm.getLecturerNovellId());
			if ((lecturerDetail==null)||(lecturerDetail.length() == 0)) {
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "Lecturer does not exist, please check the network code that was entered.."));
				addErrors(request, messages);
	
				request.setAttribute("bookingForm", bookingForm);
				return "bookingstep1";
			}else {
				// set lecturer detail in formbean
				SatbookOracleDAO db1 = new SatbookOracleDAO();
				String lectureContact = db1.getlecturerContactDetails(bookingForm.getLecturerNovellId());
				String[] temp = null;
				temp = lectureContact.split("#");
				bookingForm.setLecturerName(temp[0]);
				bookingForm.setLecturerNum1(temp[1]);
				bookingForm.setEmail(temp[2]);
				
			}
		} else { // end lecturer validations for SATBOOK
            // set lecturer detail in formbean
                  SatbookOracleDAO db1 = new SatbookOracleDAO();
                  String lectureContact = db1.getlecturerContactDetails(bookingForm.getCreatedBy());
                  if(lectureContact.length()==0){
                        messages.add(ActionMessages.GLOBAL_MESSAGE,
                                    new ActionMessage("message.generalmessage", "Unrecognized user details"));
                        addErrors(request, messages);

                        request.setAttribute("bookingForm", bookingForm);
                        return "bookingstep1";
                  }else{
                  String[] temp = null;
                  temp = lectureContact.split("#");
                  bookingForm.setLecturerName(temp[0]);
                  bookingForm.setLecturerNum1(temp[1]);
                  bookingForm.setEmail(temp[2]);
      }
      }


		
		/** ***** CURRENT DATE VARIABLES *****
		 */
		// current date
		GregorianCalendar calCurrent = new GregorianCalendar();
		int currentDay = calCurrent.get(Calendar.DAY_OF_MONTH);
		int currentMonth = calCurrent.get(Calendar.MONTH) + 1;
		int currentYear = calCurrent.get(Calendar.YEAR);
		int currentHH = calCurrent.get(Calendar.HOUR);
		int currentMM = calCurrent.get(Calendar.MINUTE);

		String currentDayStr = "";
		String currentMonthStr = "";
		String currentHHStr = "";
		String currentMMStr = "";
		if (currentDay <= 9) {
			currentDayStr = "0"+currentDay;
		}else {
			currentDayStr = Integer.toString(currentDay);
		}
		if (currentMonth <= 9) {
			currentMonthStr = "0"+currentMonth;
		}else {
			currentMonthStr = Integer.toString(currentMonth);
		}
		if (currentHH <= 9) {
			currentHHStr = "0"+currentHH;
		} else {
			currentHHStr = Integer.toString(currentHH);
		}
		if (currentMM <= 9) {
			currentMMStr = "0"+currentMM;
		} else {
			currentMMStr = Integer.toString(currentMM);
		}
		String currentDate = currentDayStr+"-"+currentMonthStr+"-"+currentYear;


		/** ****** START DATE VALIDATIONS *****
		 * validate start date is mandatory
		 * validate start date (valid month, year, day)
		 * validate start date is in future
		 */
		// validate start date is mandatory
		int tmp;

		boolean startDateEntered = true;
		if ((bookingForm.getStartDay()==null)||(bookingForm.getStartDay().length()<=0)||
				bookingForm.getStartDay().equals("0")) {
			startDateEntered = false;
		}
		try {
			tmp = Integer.parseInt(bookingForm.getStartDay());
		} catch (NumberFormatException e) {
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Start date day must be number."));
			addErrors(request, messages);

			request.setAttribute("bookingForm", bookingForm);
			return "bookingstep1";
		}

		if ((bookingForm.getStartMonth()==null)||(bookingForm.getStartMonth().length()<=0)||
				bookingForm.getStartMonth().equals("0")) {
			startDateEntered = false;
		}

		if ((bookingForm.getStartYear()==null)||(bookingForm.getStartYear().length()<=0)||
				bookingForm.getStartYear().equals("0")||(bookingForm.getStartYear().length()>4)) {
			startDateEntered = false;
		}
		try {
			tmp = Integer.parseInt(bookingForm.getStartYear());
		} catch (NumberFormatException e) {
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Start date year must be number."));
			addErrors(request, messages);

			request.setAttribute("bookingForm", bookingForm);
			return "bookingstep1";
		}

		if (startDateEntered == false) {
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Please enter the start date."));
			addErrors(request, messages);

			request.setAttribute("bookingForm", bookingForm);
			return "bookingstep1";
		}

		// validate start date (valid month, year, day)
		int startDayInt = Integer.parseInt(bookingForm.getStartDay());
		int startYearInt = Integer.parseInt(bookingForm.getStartYear());
		int startMonthInt = Integer.parseInt(bookingForm.getStartMonth());
		if (bookingForm.getStartMonth().equals("1")||bookingForm.getStartMonth().equals("3")
				||bookingForm.getStartMonth().equals("5")||bookingForm.getStartMonth().equals("7")
				||bookingForm.getStartMonth().equals("8")||bookingForm.getStartMonth().equals("3")
				||bookingForm.getStartMonth().equals("10")||bookingForm.getStartMonth().equals("12")) {
			if (startDayInt > 31) {
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "Invalid day entered for start date."));
				addErrors(request, messages);

				bookingForm.setStartMonth(Integer.toString(startMonthInt));

				request.setAttribute("bookingForm", bookingForm);
				return "bookingstep1";
			}
		} else if (bookingForm.getStartMonth().equals("4")||bookingForm.getStartMonth().equals("6")
				||bookingForm.getStartMonth().equals("9")||bookingForm.getStartMonth().equals("11")) {
			if (startDayInt > 30) {
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "Invalid day entered for start date."));
				addErrors(request, messages);

				bookingForm.setStartMonth(Integer.toString(startMonthInt));
				request.setAttribute("bookingForm", bookingForm);
				return "bookingstep1";
			}
		} else if (bookingForm.getStartMonth().equals("2")) {
			if (startYearInt % 4 == 0) {
				if (startDayInt > 29) {
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage", "Invalid day entered for start date."));
					addErrors(request, messages);

					bookingForm.setStartMonth(Integer.toString(startMonthInt));
					request.setAttribute("bookingForm", bookingForm);
					return "bookingstep1";
				}
			} else {
				if (startDayInt > 28) {
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage", "Invalid day entered for start date."));
					addErrors(request, messages);

					bookingForm.setStartMonth(Integer.toString(startMonthInt));
					request.setAttribute("bookingForm", bookingForm);
					return "bookingstep1";
				}
			}
		} // end validate start date


		// set start date:
		if (startDayInt <= 9) {
			bookingForm.setStartDay("0"+startDayInt);
		}
		if (startMonthInt <= 9) {
			bookingForm.setStartMonth("0"+startMonthInt);
		}
		bookingForm.setStartDate(bookingForm.getStartYear()+"-"+bookingForm.getStartMonth()+"-"+bookingForm.getStartDay());
		String tmpDate = bookingForm.getStartYear()+"-"+bookingForm.getStartMonth()+"-"+bookingForm.getStartDay();
		
		if (bookingForm.getSystemID().equals(bookingForm.getSatbook())) {
			// may user add a booking to this date (is date linked to his institution)
			String institution = null;
			try {
				institution = db2.selectInstitution(tmpDate);
			} catch (Exception e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			if ((institution == null)||(institution.length()==0)) {
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "No institution was linked to this date."));
				addErrors(request, messages);
	
				bookingForm.setStartMonth(Integer.toString(startMonthInt));
				request.setAttribute("bookingForm", bookingForm);
				return "bookingstep1";
			}
			if (!(institution.equals("UNISA"))) {
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "UNISA may not place a booking on this date"));
				addErrors(request, messages);
	
				bookingForm.setStartMonth(Integer.toString(startMonthInt));
				request.setAttribute("bookingForm", bookingForm);
				return "bookingstep1";
			}
		} //if (bookingForm.getSystemID().equals(bookingForm.getSatbook())) {

		// validate that start date is in future
		//@return int of either a -1, 0, 1 - which determines if the date is before, equal to or after
		//int result = currentDate.compareTo(bookingForm.getStartDate());
		String tmpCurrentDate = currentYear+"-"+currentMonthStr+"-"+currentDayStr;
		String tmpStartDate = bookingForm.getStartYear()+"-"+bookingForm.getStartMonth()+"-"+bookingForm.getStartDay();
		//int result = bookingForm.getStartDate().compareTo(currentDate);
		int result = tmpStartDate.compareTo(tmpCurrentDate);
		if (result <= -1) {
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Invalid start date, booking can not be placed in the past."));
			addErrors(request, messages);

			bookingForm.setStartMonth(Integer.toString(startMonthInt));
			request.setAttribute("bookingForm", bookingForm);
			return "bookingstep1";
		}
		
		//validate end time slot of bookings for during the week & weekends is correct 
		/** 
		 setting Calender day of week:January = 0 , Sunday = 1 & Saturday = 7
		 System.out.println(" Year "+startYearInt+ " Month "+ startMonthInt+" Day "+startDayInt);
		**/
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR,startYearInt);
		calendar.set(Calendar.MONTH,startMonthInt-1);
		calendar.set(Calendar.DATE,startDayInt);
		
		// limits on after hours is only on satbook and not on venbook.
		if (bookingForm.getSystemID().equals(bookingForm.getSatbook())) {
		    int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
			if (dayOfWeek ==1){
				//No bookings on sunday
					
						messages.add(ActionMessages.GLOBAL_MESSAGE,
								new ActionMessage("message.generalmessage", "Bookings may not be placed on Sundays." ));
						addErrors(request, messages);
			
						bookingForm.setStartMonth(Integer.toString(startMonthInt));
						request.setAttribute("bookingForm", bookingForm);
						return "bookingstep1";	
					
				}
			if (dayOfWeek < 7){
			//No bookings after 4 pm during the week.
				
			
				
				if ((Integer.parseInt(bookingForm.getStartHH())<8)||(Integer.parseInt(bookingForm.getEndHH())>18)) {
					
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage", "Bookings may only be placed between 8:00 and 18:30 on weekdays"));
					addErrors(request, messages);
		
					bookingForm.setStartMonth(Integer.toString(startMonthInt));
					request.setAttribute("bookingForm", bookingForm);
					return "bookingstep1";	
					
				} if (Integer.parseInt(bookingForm.getEndHH())==18) {
					if(Integer.parseInt(bookingForm.getEndMM())>30){
							
							messages.add(ActionMessages.GLOBAL_MESSAGE,
									new ActionMessage("message.generalmessage", "Bookings may only be placed between 8:00 and 18:30 on weekdays"));
							addErrors(request, messages);
				
							bookingForm.setStartMonth(Integer.toString(startMonthInt));
							request.setAttribute("bookingForm", bookingForm);
							return "bookingstep1";	
							}
						}
					}
			
	
			
			// No Saturday bookings after 12 pm
			if (dayOfWeek == 7){ 
				if((Integer.parseInt(bookingForm.getStartHH())<8)||(Integer.parseInt(bookingForm.getEndHH())>17 )){
					
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage", "Bookings may only be placed between 8:00 and 17:00 on Saturdays"));
					addErrors(request, messages);
	
					bookingForm.setStartMonth(Integer.toString(startMonthInt));
					request.setAttribute("bookingForm", bookingForm);
					return "bookingstep1";
				}
				if (Integer.parseInt(bookingForm.getEndHH())==17) {
					if(Integer.parseInt(bookingForm.getEndMM())>0){
							
							messages.add(ActionMessages.GLOBAL_MESSAGE,
									new ActionMessage("message.generalmessage", "Bookings may only be placed between 8:00 and 17:00 on Saturdays"));
							addErrors(request, messages);
				
							bookingForm.setStartMonth(Integer.toString(startMonthInt));
							request.setAttribute("bookingForm", bookingForm);
							return "bookingstep1";	
							}
						}
			} // end dayOfWeek == 7
		} // end of time limits for satbook
		
		// VC Bookings - bookings may not be placed on Sundays.
		if (bookingForm.getSystemID().equals(bookingForm.getVenbook())) {
		    int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
			if (dayOfWeek ==1){
			//No bookings on sunday
				
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage", "Bookings may not be placed on Sundays." ));
					addErrors(request, messages);
		
					bookingForm.setStartMonth(Integer.toString(startMonthInt));
					request.setAttribute("bookingForm", bookingForm);
					return "bookingstep1";	
				
			}
		}
		
		/** ****** VALIDATE IF BOOKING DATE IS 7 DAYS IN-ADVANCE *****/
		boolean inAdvance = true;
		//if(bookingForm.getUserPermission().equals("ACCESS")){
		try {
			inAdvance = db2.validateAdvanceBooking(bookingForm.getStartDate());
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if ((inAdvance == false)&& (bookingForm.getUserPermission().equals("ACCESS"))) {
		
			 messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Booking must be placed at least 7 days in advance."));
			addErrors(request, messages);

			bookingForm.setStartMonth(Integer.toString(startMonthInt));
			request.setAttribute("bookingForm", bookingForm);
			return "bookingstep1";
		} // end of if (inAdvance==false
		
		
		/** ****** START TIME VALIDATIONS *****
		 * validate start time (valid hours and minutes)
		 * validate that start time is not in the passed if start date is current date
		 */
		if ((bookingForm.getStartHH()==null)||(bookingForm.getStartMM()==null)||
				(bookingForm.getStartHH().equals(""))||(bookingForm.getStartMM().equals(""))) {
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Please enter the start time HH:MM"));
			addErrors(request, messages);

			bookingForm.setStartMonth(Integer.toString(startMonthInt));
			request.setAttribute("bookingForm", bookingForm);
			return "bookingstep1";
		}
		
		try {
			tmp = Integer.parseInt(bookingForm.getStartHH());
		} catch (NumberFormatException e) {
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Start time (HH) must be number."));
			addErrors(request, messages);

			bookingForm.setStartMonth(Integer.toString(startMonthInt));
			request.setAttribute("bookingForm", bookingForm);
			return "bookingstep1";
		}
		try {
			tmp = Integer.parseInt(bookingForm.getStartHH());
		} catch (NumberFormatException e) {
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Start time (MM) must be number."));
			addErrors(request, messages);

			bookingForm.setStartMonth(Integer.toString(startMonthInt));
			request.setAttribute("bookingForm", bookingForm);
			return "bookingstep1";
		}

		int startHHInt = Integer.parseInt(bookingForm.getStartHH());
		int startMMInt = Integer.parseInt(bookingForm.getStartMM());
		// set start time
		if (startHHInt <= 9) {
			bookingForm.setStartHH("0"+startHHInt);
		}
		if (startMMInt <= 9) {
			bookingForm.setStartMM("0"+startMMInt);
		}
		// validate start time (valid hours and minutes)
		if ((startHHInt<=0)||(startHHInt>23)||(startMMInt<0)||(startMMInt>59)) {
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Invalid start time."));
			addErrors(request, messages);

			bookingForm.setStartMonth(Integer.toString(startMonthInt));
			request.setAttribute("bookingForm", bookingForm);
			return "bookingstep1";
		}

		// validate that start time is not in the passed if start date is current date
		if (result == 0) {
			if ((startHHInt < currentHH)&&(startMMInt < currentMM)) {
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "Invalid start time, time can not be in the past."));
				addErrors(request, messages);

				bookingForm.setStartMonth(Integer.toString(startMonthInt));
				request.setAttribute("bookingForm", bookingForm);
				return "bookingstep1";
			}
			if ((startHHInt == currentHH)&&(startMMInt < currentMM)) {
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "Invalid start time, time can not be in the past."));
				addErrors(request, messages);

				bookingForm.setStartMonth(Integer.toString(startMonthInt));
				request.setAttribute("bookingForm", bookingForm);
				return "bookingstep1";
			}
		} // end of if (result==0)
		
		
		// validate that previous end time is not within 30mnts of start time of current booking
		boolean validStartTime=false;
		try{
			validStartTime=db2.validateStartTime(bookingForm.getStartHH() + ":" + bookingForm.getStartMM() ,bookingForm.getStartDate(),bookingForm.getSystemID() );
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
		if (bookingForm.getSystemID().equals(bookingForm.getSatbook())) {
			if (validStartTime== false){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "Your booking must start at least 30 minutes after the previous booking"));
				addErrors(request, messages);
				bookingForm.setStartMonth(Integer.toString(startMonthInt));
				request.setAttribute("bookingForm", bookingForm);
				return "bookingstep1";	
			}
		
	        // validate that end time of new booking is at least 30mnts before start time of new booking	
		    boolean validEndTime=false;
			try{
				validEndTime=db2.validateEndTime(bookingForm.getEndHH() + ":" + bookingForm.getEndMM() ,bookingForm.getStartDate(),bookingForm.getSystemID() );
			}catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (validEndTime== false){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "Your booking must end at least 30 minutes before start time of the next booking"));
				addErrors(request, messages);
	
				bookingForm.setStartMonth(Integer.toString(startMonthInt));
				request.setAttribute("bookingForm", bookingForm);
				return "bookingstep1";	
			}
		} // end if (bookingForm.getSystemID()) { for 30minute validations
				
		/** ****** END DATE VALIDATIONS *****
		 * validate end date is mandatory
		 * validate end date (valid month, year, day)
		 * validate end date is in future
		 */
		// validate end date is mandatory

		// validate end date (valid month, year, day)
		int endDayInt = Integer.parseInt(bookingForm.getStartDay());
		int endYearInt = Integer.parseInt(bookingForm.getStartYear());
		int endMonthInt = Integer.parseInt(bookingForm.getStartMonth());

		// set end date:
		if (endDayInt <= 9) {
			bookingForm.setEndDay("0"+endDayInt);
		}
		if (endMonthInt <= 9) {
			bookingForm.setEndMonth("0"+endMonthInt);
		}
		bookingForm.setEndDate(bookingForm.getStartYear()+"-"+bookingForm.getStartMonth()+"-"+bookingForm.getStartDay());

		// validate that end date is in future
		//@return int of either a -1, 0, 1 - which determines if the date is before, equal to or after
		//int result2 = currentDate.compareTo(bookingForm.getEndDate());
		int result2 = bookingForm.getEndDate().compareTo(currentDate);

		/** ****** END TIME VALIDATIONS *****
		 * validate end time (valid hours and minutes)
		 * validate that end time is not in the passed if end date is current date
		 * validate that end time is not before end time
		 */
		if ((bookingForm.getEndHH()==null)||(bookingForm.getEndMM()==null)||
				(bookingForm.getEndHH().equals(""))||(bookingForm.getEndMM().equals(""))) {
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Please enter the end time HH:MM"));
			addErrors(request, messages);

			bookingForm.setStartMonth(Integer.toString(startMonthInt));
			request.setAttribute("bookingForm", bookingForm);
			return "bookingstep1";
		}

		try {
			tmp = Integer.parseInt(bookingForm.getEndHH());
		} catch (NumberFormatException e) {
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "End time (HH) must be number."));
			addErrors(request, messages);

			bookingForm.setStartMonth(Integer.toString(startMonthInt));
			request.setAttribute("bookingForm", bookingForm);
			return "bookingstep1";
		}
		try {
			tmp = Integer.parseInt(bookingForm.getEndMM());
		} catch (NumberFormatException e) {
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "End time (MM) must be number."));
			addErrors(request, messages);

			bookingForm.setStartMonth(Integer.toString(startMonthInt));
			request.setAttribute("bookingForm", bookingForm);
			return "bookingstep1";
		}

		int endHHInt = Integer.parseInt(bookingForm.getEndHH());
		int endMMInt = Integer.parseInt(bookingForm.getEndMM());
		// set start time
		if (endHHInt <= 9) {
			bookingForm.setEndHH("0"+endHHInt);
		}
		if (endMMInt <= 9) {
			bookingForm.setEndMM("0"+endMMInt);
		}
 		if ((endHHInt<=0)||(endHHInt>23)||(endMMInt<0)||(endMMInt>59)) {
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Invalid end time."));
			addErrors(request, messages);

			bookingForm.setStartMonth(Integer.toString(startMonthInt));
			request.setAttribute("bookingForm", bookingForm);
			return "bookingstep1";
		}
		if (result2 == 0) {
			if ((endHHInt < currentHH)&&(endMMInt < currentMM)) {
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "Invalid end time, time can not be in the past."));
				addErrors(request, messages);

				bookingForm.setStartMonth(Integer.toString(startMonthInt));
				request.setAttribute("bookingForm", bookingForm);
				return "bookingstep1";
			}
			if ((endHHInt == currentHH)&&(endMMInt < currentMM)) {
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "Invalid end time, time can not be in the past."));
				addErrors(request, messages);

				bookingForm.setStartMonth(Integer.toString(startMonthInt));
				request.setAttribute("bookingForm", bookingForm);
				return"bookingstep1";
			}
		} // end if (result2 == 0) {

		// validate that end time is not before start time
		if (bookingForm.getStartDate().equals(bookingForm.getEndDate())) {
			if ((endHHInt < startHHInt)||
					((endHHInt == startHHInt)&&(endMMInt <= startMMInt))) {
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "Invalid end time, end time can not be before start time."));
				addErrors(request, messages);

				bookingForm.setStartMonth(Integer.toString(startMonthInt));
				request.setAttribute("bookingForm", bookingForm);
				return "bookingstep1";
			}
		} //if (bookingForm.getStartDate().equals(bookingForm.getEndDate())) {
		
		/*
		 * SATBOOK: Rebroadcast validations
		 */
		int startRebroadDayInt = 0;
		int startRebroadYearInt = 0;
		int startRebroadMonthInt = 0;
		if (bookingForm.getSystemID().equals(bookingForm.getSatbook())) {
			
			if (null == bookingForm.getRebroadcast()) {
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "Please choose broadcast type"));
				addErrors(request, messages);
				bookingForm.setStartMonth(Integer.toString(startMonthInt));
				request.setAttribute("bookingForm", bookingForm);
				return "bookingstep1";
			}				

			//validations of radio buttons value
			if (bookingForm.getRebroadcast().equals("true")){
				//validations for day mandatory
				if ((bookingForm.getRebroadDay()==null)||(bookingForm.getRebroadDay().length()<=0)||
						bookingForm.getRebroadDay().equals("0")){
							
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage", "Please enter day of date that must be rebroadcasted"));
					addErrors(request, messages);
					bookingForm.setStartMonth(Integer.toString(startMonthInt));
					request.setAttribute("bookingForm", bookingForm);
					return "bookingstep1";
				}
				//validations for year mandatory
				if ((bookingForm.getRebroadYear()==null)||(bookingForm.getRebroadYear().length()<=0)||
						bookingForm.getRebroadYear().equals("0") ||bookingForm.getRebroadYear().length() >4){
							
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage", "Please enter year of date that must be rebroadcasted"));
					addErrors(request, messages);
					bookingForm.setStartMonth(Integer.toString(startMonthInt));
					request.setAttribute("bookingForm", bookingForm);
					return "bookingstep1";
				}
				//validate if year is a  valid number
				try {
					tmp = Integer.parseInt(bookingForm.getRebroadYear());
				} catch (NumberFormatException e) {
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage", "rebroadcast year must be number."));
					addErrors(request, messages);
	
					request.setAttribute("bookingForm", bookingForm);
					bookingForm.setStartMonth(Integer.toString(startMonthInt));
					return "bookingstep1";
				}
				//validate month is mandatory 
				if ((bookingForm.getRebroadMonth()==null)||(bookingForm.getRebroadMonth().length()<=0)||
						bookingForm.getRebroadMonth().equals("0")){
							
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage", "Please enter month of date that must be rebroadcasted"));
					addErrors(request, messages);
					bookingForm.setStartMonth(Integer.toString(startMonthInt));
					request.setAttribute("bookingForm", bookingForm);
					return "bookingstep1";
				}
				//validate if month is a  valid number
				try {
					tmp = Integer.parseInt(bookingForm.getRebroadMonth());
				} catch (NumberFormatException e) {
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage", "rebroadcast month  must be number."));
					addErrors(request, messages);
					bookingForm.setStartMonth(Integer.toString(startMonthInt));
					request.setAttribute("bookingForm", bookingForm);
					return "bookingstep1";
				}
			
				// validate Rebroadcast valid day  
				startRebroadDayInt = Integer.parseInt(bookingForm.getRebroadDay());
				startRebroadYearInt = Integer.parseInt(bookingForm.getRebroadYear());
				startRebroadMonthInt = Integer.parseInt(bookingForm.getRebroadMonth());
				if (bookingForm.getRebroadMonth().equals("1")||bookingForm.getRebroadMonth().equals("3")
						||bookingForm.getRebroadMonth().equals("5")||bookingForm.getRebroadMonth().equals("7")
						||bookingForm.getRebroadMonth().equals("8")||bookingForm.getRebroadMonth().equals("3")
						||bookingForm.getRebroadMonth().equals("10")||bookingForm.getRebroadMonth().equals("12")) {
					if (startRebroadDayInt > 31) {
						messages.add(ActionMessages.GLOBAL_MESSAGE,
								new ActionMessage("message.generalmessage", "Invalid day entered for date that must be rebroadcasted."));
						addErrors(request, messages);
		
						bookingForm.setRebroadMonth(Integer.toString(startRebroadMonthInt));
						bookingForm.setStartMonth(Integer.toString(startMonthInt));
		
						request.setAttribute("bookingForm", bookingForm);
						return "bookingstep1";
					}
				} else if (bookingForm.getRebroadMonth().equals("4")||bookingForm.getRebroadMonth().equals("6")
						||bookingForm.getRebroadMonth().equals("9")||bookingForm.getRebroadMonth().equals("11")) {
					if (startRebroadDayInt > 30) {
						messages.add(ActionMessages.GLOBAL_MESSAGE,
								new ActionMessage("message.generalmessage", "Invalid day entered for date that must be rebroadcasted"));
						addErrors(request, messages);
		
						bookingForm.setRebroadMonth(Integer.toString(startRebroadMonthInt));
						bookingForm.setStartMonth(Integer.toString(startMonthInt));
						request.setAttribute("bookingForm", bookingForm);
						return "bookingstep1";
					}
				} else if (bookingForm.getRebroadMonth().equals("2")) {
					if (startRebroadYearInt % 4 == 0) {
						if (startRebroadDayInt > 29) {
							messages.add(ActionMessages.GLOBAL_MESSAGE,
									new ActionMessage("message.generalmessage", "Invalid day entered for date that must be rebroadcasted."));
							addErrors(request, messages);
		
							bookingForm.setRebroadMonth(Integer.toString(startRebroadMonthInt));
							bookingForm.setStartMonth(Integer.toString(startMonthInt));
							request.setAttribute("bookingForm", bookingForm);
							return "bookingstep1";
						}
					} else {
						if (startRebroadDayInt > 28) {
							messages.add(ActionMessages.GLOBAL_MESSAGE,
									new ActionMessage("message.generalmessage", "Invalid day entered for date that must be rebroadcasted."));
							addErrors(request, messages);
		
							bookingForm.setRebroadMonth(Integer.toString(startRebroadMonthInt));
							bookingForm.setStartMonth(Integer.toString(startMonthInt));
							request.setAttribute("bookingForm", bookingForm);
							return "bookingstep1";
						}
					}
				} // end validate rebroadcast day
			
				
				// set rebroadcast date:
				if (startRebroadDayInt <= 9) {
					bookingForm.setRebroadDay("0"+startRebroadDayInt);
				}
				if (startRebroadMonthInt <= 9) {
					bookingForm.setRebroadMonth("0"+startRebroadMonthInt);
				}
				
				
				//set rebroadcast date
				bookingForm.setRebroadDate(bookingForm.getRebroadDay()+"-"+bookingForm.getRebroadMonth()+"-"+bookingForm.getRebroadYear());
				//verify if a booking exist on rebroadcast date entered
				String rebroad=db2.verifyRebroadcast(bookingForm.getRebroadDate(),bookingForm.getSystemID());
				if (rebroad.equals("")){
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage", "Booking does not exist on this date for a rebroadcast."));
					addErrors(request, messages);
					bookingForm.setStartMonth(Integer.toString(startMonthInt));
					bookingForm.setRebroadMonth(Integer.toString(startRebroadMonthInt));
					request.setAttribute("bookingForm", bookingForm);
					return "bookingstep1";			
				}
			}
		} // end of rebroadcast SATBOOK validations


		/** SATBOOK: validate for double bookings, registration year, registration period */
		if (bookingForm.getSystemID().equals(bookingForm.getSatbook())) {
			/** ****** VALIDATE FOR DOUBLE BOOKINGS *****/
			String doubleBooking = "false";
			String sDate = bookingForm.getStartDate()+" "+bookingForm.getStartHH()+":"+bookingForm.getStartMM();
			String eDate = bookingForm.getEndDate()+" "+bookingForm.getEndHH()+":"+bookingForm.getEndMM();
			try {
				doubleBooking = db2.validateDoubleBookings(sDate,eDate,bookingForm.getBkngId(),bookingForm.getClassrooms(),bookingForm.getSystemID());
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			if (doubleBooking.equals("true")) {
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "A booking was already scheduled for these dates."));
				addErrors(request, messages);

				bookingForm.setStartMonth(Integer.toString(startMonthInt));
				bookingForm.setRebroadMonth(Integer.toString(startRebroadMonthInt));
				request.setAttribute("bookingForm", bookingForm);
				return "bookingstep1";
			}
				
			/** ****** VALIDATE REGISTRATION PERIOD *****/
			if ((bookingForm.getRegistrationPeriod().equals("."))||(bookingForm.getRegistrationPeriod()==null)) {
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "Please choose the registration period."));
				addErrors(request, messages);
	
				bookingForm.setStartMonth(Integer.toString(startMonthInt));
				bookingForm.setRebroadMonth(Integer.toString(startRebroadMonthInt));
				request.setAttribute("bookingForm", bookingForm);
				return "bookingstep1";
			}
	
			/** ****** VALIDATE REGISTRATION YEAR *****/
			if ((bookingForm.getRegistrationYear().equals("0"))||(bookingForm.getRegistrationYear()==null)) {
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "Please choose the registration year."));
				addErrors(request, messages);
	
				bookingForm.setStartMonth(Integer.toString(startMonthInt));
				bookingForm.setRebroadMonth(Integer.toString(startRebroadMonthInt));
				request.setAttribute("bookingForm", bookingForm);
				return "bookingstep1";
			}
		} // end of SATBOOK specific validations
		
		/** VC Booking: Validate for double bookings */
		if (bookingForm.getPageStatus().equals("EDIT")) {
			if (bookingForm.getSystemID().equals(bookingForm.getVenbook())) {
				/** ************************************************ */
					String doubleBooking = "false";
					String sDate = bookingForm.getStartYear()+"-"+bookingForm.getStartMonth()+"-"+bookingForm.getStartDay()+" "+bookingForm.getStartHH()+":"+bookingForm.getStartMM();
					String eDate = bookingForm.getEndYear()+"-"+bookingForm.getEndMonth()+"-"+bookingForm.getEndDay()+" "+bookingForm.getEndHH()+":"+bookingForm.getEndMM();
					try {
						doubleBooking = db2.validateDoubleBookings(sDate, eDate, bookingForm.getBkngId(), bookingForm.getSelectedClassroomsAL(), bookingForm.getSystemID());
						if (!doubleBooking.equals("false")) {
							messages.add(ActionMessages.GLOBAL_MESSAGE,
									new ActionMessage("message.generalmessage", "The new enterred times overlap existing times for venue(s) ("+doubleBooking+") please choose another venue or time."));
							addErrors(request, messages);
	
							bookingForm.setStartMonth(Integer.toString(startMonthInt));
							bookingForm.setRebroadMonth(Integer.toString(startRebroadMonthInt));
							request.setAttribute("bookingForm", bookingForm);
							
							return "bookingstep1";
						}
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
			}
		}
		
		/** VENBOOK: booking type validations */
		if (bookingForm.getSystemID().equals(bookingForm.getVenbook())) {
			/* booking type is mandatory */
			if ((bookingForm.getBkngType() == null)||(bookingForm.getBkngType().length() == 0)||(bookingForm.getBkngType().equals(".."))) {
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "Please choose the booking type"));
				addErrors(request, messages);
				bookingForm.setStartMonth(Integer.toString(startMonthInt));
				bookingForm.setRebroadMonth(Integer.toString(startRebroadMonthInt));
				request.setAttribute("bookingForm", bookingForm);
				
				return "bookingstep1";
			} 
		} // end of VENBOOK specific validations

		/** Description is mandatory + remove ' */
		if ((bookingForm.getDescription() == null)||(bookingForm.getDescription().length() == 0)) {
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Please enter the description of the booking"));
			addErrors(request, messages);
			bookingForm.setStartMonth(Integer.toString(startMonthInt));
			bookingForm.setRebroadMonth(Integer.toString(startRebroadMonthInt));
			request.setAttribute("bookingForm", bookingForm);
			
			return "bookingstep1";
		} // descsription is mandatory

		bookingForm.setDescription(bookingForm.getDescription().replace('\'',' '));
		if (bookingForm.getDescription().length() > 250) {
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Description may not be more that 250 characters."));
			addErrors(request, messages);

			bookingForm.setStartMonth(Integer.toString(startMonthInt));
			bookingForm.setRebroadMonth(Integer.toString(startRebroadMonthInt));
			request.setAttribute("bookingForm", bookingForm);
			return "bookingstep1";
		}

		request.setAttribute("bookingForm", bookingForm);

		/** Satbook - subject list for lecturer, registration year and period */
		if (bookingForm.getSystemID().equals(bookingForm.getSatbook())) {
			// set subject list for lecturer, registration year and period
			SatbookOracleDAO db1 = new SatbookOracleDAO();
			try {
				bookingForm.setSubjects(db1.selectSubjects(bookingForm.getLecturerNovellId(),
						bookingForm.getRegistrationYear(),
						bookingForm.getRegistrationPeriod()));
				if ((bookingForm.getSubjects() == null)||(bookingForm.getSubjects().isEmpty())) {
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage", "No subjects linked to lecturer for this year and period."));
					addErrors(request, messages);
	
					bookingForm.setStartMonth(Integer.toString(startMonthInt));
					bookingForm.setRebroadMonth(Integer.toString(startRebroadMonthInt));
					request.setAttribute("bookingForm", bookingForm);
					return "bookingstep1";
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if (bookingForm.getPageStatus().equals("EDIT")) {
			// save changes
			try {
				String startDate = bookingForm.getStartYear()+"-"+bookingForm.getStartMonth()+"-"+bookingForm.getStartDay()+
                					" "+bookingForm.getStartHH()+":"+bookingForm.getStartMM();
				String endDate = bookingForm.getStartYear()+"-"+bookingForm.getStartMonth()+"-"+bookingForm.getStartDay()+
									" "+bookingForm.getEndHH()+":"+bookingForm.getEndMM();
				
				//bookingForm.setRebroadDate(bookingForm.getRebroadYear()+"-"+bookingForm.getRebroadMonth()+"-"+bookingForm.getRebroadDay());
				String rebroadDate = bookingForm.getRebroadDay()+"-"+bookingForm.getRebroadMonth()+"-"+bookingForm.getRebroadYear();
				Session currentSession =sessionManager.getCurrentSession();
				String userEID = currentSession.getUserEid();
				bookingForm.setCreatedBy(userEID);
				
				db2.saveBookingHeading("EDIT",bookingForm.getBkngId(),bookingForm.getHeading(),bookingForm.getLecturerNovellId(),
						bookingForm.getLecturerNum2(),startDate,endDate,bookingForm.getDescription(), bookingForm.getRebroadcast(),rebroadDate,
						bookingForm.getCreatedBy(),bookingForm.getBkngType(),bookingForm.getSystemID());
				
				
				// save log
				UsageSession usageSession = usageSessionService.getSession();
				eventTrackingService.post(
						eventTrackingService.newEvent(EventTrackingTypes.EVENT_SATBOOK_EDIT_HEADING, " booking id= "+bookingForm.getBkngId(), false), usageSession);

				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// return to edit screen
			return "viewbooking";
		} else {
			if (bookingForm.getSystemID().equals(bookingForm.getVenbook())) {
				// set materials list
				try {
					bookingForm.setMaterials(db.selectMaterialList(false,bookingForm.getSystemID()));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return "bookingstep3"; //materials step	
			} else {
				return "bookingstep2"; //subjects step
			}
		}
	}

	public String continueStep2(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) {
		
			usageSessionService = (UsageSessionService) ComponentManager.get(UsageSessionService.class);
			eventTrackingService = (EventTrackingService) ComponentManager.get(EventTrackingService.class);

		BookingForm bookingForm = (BookingForm)form;
		ActionMessages messages = new ActionMessages();
		SatbookDAO db = new SatbookDAO();
		SatbookBookingDAO db1 = new SatbookBookingDAO();

		String[] list = bookingForm.getSelectedSubjects();
		ArrayList subjectList = new ArrayList();

		if ((list == null)||(list.length==0)) {
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Please select 1 or more subjects."));
			addErrors(request, messages);

			request.setAttribute("bookingForm", bookingForm);
			return "bookingstep2";
		}

		for (int i = 0; i < list.length; i++) {
			if (list[i]!= null) {
				subjectList.add(bookingForm.getSubjects().get(Integer.parseInt(list[i])));
			}
		}
		bookingForm.setSelectedSubjectsAL(subjectList);

		// set materials list
		try {
			bookingForm.setMaterials(db.selectMaterialList(false,bookingForm.getSystemID()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		request.setAttribute("bookingForm", bookingForm);
		request.setAttribute("pageStatus",bookingForm.getPageStatus());

		if (bookingForm.getPageStatus().equals("EDIT")) {
			request.setAttribute("pageStatus",bookingForm.getPageStatus());
			// save subject changes
			try {
				db1.saveBookingSubjects("ADD",bookingForm.getBkngId(),bookingForm.getSelectedSubjectsAL(),bookingForm.getRegistrationPeriod(),bookingForm.getRegistrationYear());
				// save log
				UsageSession usageSession = usageSessionService.getSession();
				eventTrackingService.post(
						eventTrackingService.newEvent(EventTrackingTypes.EVENT_SATBOOK_EDIT_SUBJECTS, " booking id= "+bookingForm.getBkngId(), false), usageSession);

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return "viewbooking";
		} else {
			return "bookingstep3";
		}
	}

	public String continueStep3(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) {
		
		usageSessionService = (UsageSessionService) ComponentManager.get(UsageSessionService.class);
		eventTrackingService = (EventTrackingService) ComponentManager.get(EventTrackingService.class);

		BookingForm bookingForm = (BookingForm)form;
		ActionMessages messages = new ActionMessages();
		SatbookBookingDAO db = new SatbookBookingDAO();
		SatbookDAO db1 = new SatbookDAO();

		//bookingForm.reset(mapping, request);
		String[] list = bookingForm.getSelectedMaterials();
		ArrayList materialList = new ArrayList();

		/* read currently registered study units into cancellation list */
		if (list != null) {
			for (int i = 0; i < list.length; i++) {
				if (list[i]!= null) {
					materialList.add(bookingForm.getMaterials().get(Integer.parseInt(list[i])));
				}
			}
		}
		bookingForm.setSelectedMaterialsAL(materialList);

		request.setAttribute("bookingForm", bookingForm);
		request.setAttribute("pageStatus",bookingForm.getPageStatus());

		if (bookingForm.getPageStatus().equals("EDIT")) {
			request.setAttribute("pageStatus",bookingForm.getPageStatus());
			// save material changes
			try {
				db.saveBookingMaterials("ADD",bookingForm.getBkngId(),bookingForm.getSelectedMaterialsAL());
				// save log
				UsageSession usageSession = usageSessionService.getSession();
				eventTrackingService.post(
						eventTrackingService.newEvent(EventTrackingTypes.EVENT_SATBOOK_EDIT_MATERIALS, " booking id= "+bookingForm.getBkngId(), false), usageSession);

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return "viewbooking";
		} else {
			if (bookingForm.getSystemID().equals(bookingForm.getVenbook())) {
				// set classroom list
				try {
					bookingForm.setClassrooms(db1.selectClassrooms(false,bookingForm.getSystemID()));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return "bookingstep5"; // venues step	
			} else {
				return "bookingstep4";	//visitors step
			}
		}
	}

	public String continueStep4(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) {
		
		usageSessionService = (UsageSessionService) ComponentManager.get(UsageSessionService.class);
		eventTrackingService = (EventTrackingService) ComponentManager.get(EventTrackingService.class);

		BookingForm bookingForm = (BookingForm)form;
		SatbookDAO db = new SatbookDAO();
		SatbookBookingDAO db1 = new SatbookBookingDAO();

		request.setAttribute("bookingForm", bookingForm);
		request.setAttribute("pageStatus",bookingForm.getPageStatus());

		ArrayList visitorList = new ArrayList();
		if ((bookingForm.getVisitor1() != null)&&(bookingForm.getVisitor1().length() > 0)) {
			visitorList.add(new org.apache.struts.util.LabelValueBean(bookingForm.getVisitor1(),"1"));
		}
		if ((bookingForm.getVisitor2() != null)&&(bookingForm.getVisitor2().length() > 0)) {
			visitorList.add(new org.apache.struts.util.LabelValueBean(bookingForm.getVisitor2(),"2"));
		}
		if ((bookingForm.getVisitor3() != null)&&(bookingForm.getVisitor3().length() > 0)) {
			visitorList.add(new org.apache.struts.util.LabelValueBean(bookingForm.getVisitor3(),"3"));
		}
		if ((bookingForm.getVisitor4() != null)&&(bookingForm.getVisitor4().length() > 0)) {
			visitorList.add(new org.apache.struts.util.LabelValueBean(bookingForm.getVisitor4(),"4"));
		}
		if (visitorList != null) {
			bookingForm.setVisitors(visitorList);
		}

		// set classroom list

		try {
			bookingForm.setClassrooms(db.selectClassrooms(false, bookingForm.getSystemID()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (bookingForm.getPageStatus().equals("EDIT")) {
			request.setAttribute("pageStatus",bookingForm.getPageStatus());
			// save visitor changes
			try {
				db1.saveBookingVisitors("ADD",bookingForm.getBkngId(),bookingForm.getVisitors());
				// save log
				UsageSession usageSession = usageSessionService.getSession();
				eventTrackingService.post(
						eventTrackingService.newEvent(EventTrackingTypes.EVENT_SATBOOK_EDIT_VISITORS, " booking id= "+bookingForm.getBkngId(), false), usageSession);

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return "viewbooking";
		} else {
			return "bookingstep5";
		}
	}

	public String continueStep5(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) {
		
		usageSessionService = (UsageSessionService) ComponentManager.get(UsageSessionService.class);
		eventTrackingService = (EventTrackingService) ComponentManager.get(EventTrackingService.class);

		BookingForm bookingForm = (BookingForm)form;
		ActionMessages messages = new ActionMessages();
		SatbookDAO db = new SatbookDAO();
		SatbookBookingDAO db1 = new SatbookBookingDAO();

		String[] list = bookingForm.getSelectedClassrooms();
		ArrayList classroomList = new ArrayList();

		if ((list == null)||(list.length==0)) {
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Please select 1 or more venues."));
			addErrors(request, messages);

			// set classroom list
			try {
				bookingForm.setClassrooms(db.selectClassrooms(false,bookingForm.getSystemID()));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			request.setAttribute("bookingForm", bookingForm);
			return "bookingstep5";
		}

		for (int i = 0; i < list.length; i++) {
			classroomList.add(bookingForm.getClassrooms().get(Integer.parseInt(list[i])));
		}
		bookingForm.setSelectedClassroomsAL(classroomList);

		/** ************************************************ */
		// if VENBOOK check for double bookings per venue.
		if (bookingForm.getSystemID().equals(bookingForm.getVenbook())) {
			String doubleBooking = "false";
			String sDate = bookingForm.getStartDate()+" "+bookingForm.getStartHH()+":"+bookingForm.getStartMM();
			String eDate = bookingForm.getEndDate()+" "+bookingForm.getEndHH()+":"+bookingForm.getEndMM();
			//String sDate = bookingForm.getStartYear()+"-"+bookingForm.getStartMonth()+"-"+bookingForm.getStartDay()+" "+bookingForm.getStartHH()+":"+bookingForm.getStartMM();
			//String eDate = bookingForm.getEndYear()+"-"+bookingForm.getEndMonth()+"-"+bookingForm.getEndDay()+" "+bookingForm.getEndHH()+":"+bookingForm.getEndMM();
			try {
				doubleBooking = db1.validateDoubleBookings(sDate, eDate, bookingForm.getBkngId(), bookingForm.getSelectedClassroomsAL(), bookingForm.getSystemID());
				if (!doubleBooking.equals("false")) {
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage", "The following venue(s) ("+doubleBooking+") are already in used please choose another venue."));
					addErrors(request, messages);

					// set classroom list
					try {
						bookingForm.setClassrooms(db.selectClassrooms(false,bookingForm.getSystemID()));
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					request.setAttribute("bookingForm", bookingForm);
					return "bookingstep5";
				}
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
		// set duration
		try {
			bookingForm.setDuration(db1.calculateBookingDuration(bookingForm.getStartHH(),bookingForm.getEndHH(),
					bookingForm.getStartMM(),bookingForm.getEndMM()));
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		request.setAttribute("bookingForm", bookingForm);
		request.setAttribute("pageStatus",bookingForm.getPageStatus());

		if (bookingForm.getPageStatus().equals("EDIT")) {
			request.setAttribute("pageStatus",bookingForm.getPageStatus());
			// save classroom changes
			try {
				db1.saveBookingClassrooms("ADD",bookingForm.getBkngId(),bookingForm.getSelectedClassroomsAL());
				// save log
				UsageSession usageSession = usageSessionService.getSession();
				eventTrackingService.post(
						eventTrackingService.newEvent(EventTrackingTypes.EVENT_SATBOOK_EDIT_CLASSROOMS, " booking id= "+bookingForm.getBkngId(), false), usageSession);

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return "viewbooking";
		} else {
			return "bookingstep6";
		}
	}

	public String saveBooking(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) {
		
		sessionManager=(SessionManager) ComponentManager.get(SessionManager.class);
		usageSessionService = (UsageSessionService) ComponentManager.get(UsageSessionService.class);
		eventTrackingService = (EventTrackingService) ComponentManager.get(EventTrackingService.class);

		BookingForm bookingForm = (BookingForm)form;
		ActionMessages messages = new ActionMessages();
		SatbookBookingDAO db1 = new SatbookBookingDAO();
		SatbookDAO db2 = new SatbookDAO();
		SatbookOracleDAO db3 = new SatbookOracleDAO();
		String bkngId = "";

		// 1. Save heading information and get unique reference number
		// date 0000-00-00 00:00:00
		//30-11-2006 20:00:00
		String startDate = bookingForm.getStartYear()+"-"+bookingForm.getStartMonth()+"-"+bookingForm.getStartDay()+
		                   " "+bookingForm.getStartHH()+":"+bookingForm.getStartMM();
		String endDate = bookingForm.getStartYear()+"-"+bookingForm.getStartMonth()+"-"+bookingForm.getStartDay()+
		                 " "+bookingForm.getEndHH()+":"+bookingForm.getEndMM();
		bookingForm.setRebroadDate(bookingForm.getRebroadDay()+"-"+bookingForm.getRebroadMonth()+"-"+bookingForm.getRebroadYear());
		String rebroadDate = bookingForm.getRebroadDay()+"-"+bookingForm.getRebroadMonth()+"-"+bookingForm.getRebroadYear();
		Session currentSession =sessionManager.getCurrentSession();
		String userEID = currentSession.getUserEid();
		bookingForm.setCreatedBy(userEID);
		
		// set list for if there is errors and have to return to step 1
		request.setAttribute("monthList", bookingForm.getMonthList());
		request.setAttribute("yearList", bookingForm.getYearList());
		request.setAttribute("registrationPeriodList",bookingForm.getRegistrationPeriodList());
		request.setAttribute("pageStatus",bookingForm.getPageStatus());
		request.setAttribute("bkngTypeList",bookingForm.getBkngTypeList());
		
		
		/** *****/
		// do double booking validations again (in case another booking was added simultaneously.
		// if VENBOOK check for double bookings per venue.
		if (bookingForm.getSystemID().equals(bookingForm.getVenbook())) {
			String doubleBooking = "false";
			String sDate = bookingForm.getStartDate()+" "+bookingForm.getStartHH()+":"+bookingForm.getStartMM();
			String eDate = bookingForm.getEndDate()+" "+bookingForm.getEndHH()+":"+bookingForm.getEndMM();
			//String sDate = bookingForm.getStartYear()+"-"+bookingForm.getStartMonth()+"-"+bookingForm.getStartDay()+" "+bookingForm.getStartHH()+":"+bookingForm.getStartMM();
			//String eDate = bookingForm.getEndYear()+"-"+bookingForm.getEndMonth()+"-"+bookingForm.getEndDay()+" "+bookingForm.getEndHH()+":"+bookingForm.getEndMM();
			try {
				doubleBooking = db1.validateDoubleBookings(sDate, eDate, bookingForm.getBkngId(), bookingForm.getSelectedClassroomsAL(), bookingForm.getSystemID());
				if (!doubleBooking.equals("false")) {
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage", "The following venue(s) ("+doubleBooking+") are already in used please choose another venue."));
					addErrors(request, messages);

					// set classroom list
					try {
						bookingForm.setClassrooms(db2.selectClassrooms(false,bookingForm.getSystemID()));
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					request.setAttribute("bookingForm", bookingForm);
					return "bookingstep5";
				}
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		/** *****/
		
		/** *****/
		// do double booking validations again (in case another booking was added simultaneously.
		// if SATBOOK check for double bookings.
		if (bookingForm.getSystemID().equals(bookingForm.getSatbook())) {
			// validate that previous end time is not within 30mnts of start time of current booking
			boolean validStartTime=false;
			try{
				validStartTime=db1.validateStartTime(bookingForm.getStartHH() + ":" + bookingForm.getStartMM() ,bookingForm.getStartDate(),bookingForm.getSystemID() );
			}catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
					
			if (validStartTime== false){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "Your booking must start at least 30 minutes after the previous booking"));
				addErrors(request, messages);
				//bookingForm.setStartMonth(Integer.toString(startMonthInt));
				request.setAttribute("bookingForm", bookingForm);
				return "bookingstep1";	
			}
			
	        // validate that end time of new booking is at least 30mnts before start time of new booking	
		    boolean validEndTime=false;
			try{
				validEndTime=db1.validateEndTime(bookingForm.getEndHH() + ":" + bookingForm.getEndMM() ,bookingForm.getStartDate(),bookingForm.getSystemID() );
			}catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (validEndTime== false){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "Your booking must end at least 30 minutes before start time of the next booking"));
				addErrors(request, messages);
	
				//bookingForm.setStartMonth(Integer.toString(startMonthInt));
				request.setAttribute("bookingForm", bookingForm);
				return "bookingstep1";	
			}
			
			/** ****** VALIDATE FOR DOUBLE BOOKINGS *****/
			String doubleBooking = "false";
			String sDate = bookingForm.getStartDate()+" "+bookingForm.getStartHH()+":"+bookingForm.getStartMM();
			String eDate = bookingForm.getEndDate()+" "+bookingForm.getEndHH()+":"+bookingForm.getEndMM();
			try {
				doubleBooking = db1.validateDoubleBookings(sDate,eDate,bookingForm.getBkngId(),bookingForm.getClassrooms(),bookingForm.getSystemID());
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			if (doubleBooking.equals("true")) {
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "A booking was already scheduled for these dates ("+sDate+" - "+eDate+"."));
				addErrors(request, messages);

				//bookingForm.setStartMonth(Integer.toString(startMonthInt));
				//bookingForm.setRebroadMonth(Integer.toString(startRebroadMonthInt));
				request.setAttribute("bookingForm", bookingForm);
				return "bookingstep1";
			}
		}
		/** *****/
		
		try {
			bkngId = db1.saveBookingHeading("ADD","",bookingForm.getHeading(),bookingForm.getLecturerNovellId(),
					bookingForm.getLecturerNum2(),startDate,endDate,bookingForm.getDescription(),bookingForm.getRebroadcast(),rebroadDate,
					bookingForm.getCreatedBy(),bookingForm.getBkngType(),bookingForm.getSystemID());			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// 2. Save broadcast subjects and there linked subjects
		if (bookingForm.getSystemID().equals(bookingForm.getSatbook())) {
			try {
				db1.saveBookingSubjects("ADD",bkngId,bookingForm.getSelectedSubjectsAL(),bookingForm.getRegistrationPeriod(),bookingForm.getRegistrationYear());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		// 3. Save broadcast material
		try {
			db1.saveBookingMaterials("ADD",bkngId,bookingForm.getSelectedMaterialsAL());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// 4. Save broadcast visitors
		if (bookingForm.getSystemID().equals(bookingForm.getSatbook())) {
			try {
				db1.saveBookingVisitors("ADD",bkngId,bookingForm.getVisitors());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		// 5. Save broadcast classrooms
		try {
			db1.saveBookingClassrooms("ADD",bkngId,bookingForm.getSelectedClassroomsAL());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// 6. Send email to administrator
		String tmpIsRebroadcast = "";
		if (bookingForm.getSystemID().equals(bookingForm.getSatbook())) {
			if (bookingForm.getRebroadcast().equals("true")) {
				tmpIsRebroadcast = "Yes";
			} else {
				tmpIsRebroadcast = "No";
			}
		}
		
		String subject = "New broadcast booking was placed";
		String body = "";
		if (bookingForm.getSystemID().equals(bookingForm.getSatbook())) {
			body = "<html>"+
				      "<body>"+
					  "A new Satellite broadcast booking was placed <br>"+
        			  "Heading: "+bookingForm.getHeading()+" <br>"+
		              "by lecturer: "+bookingForm.getLecturerName()+": "+bookingForm.getLecturerNum1()+" <br>"+
		              "Start date: "+startDate+" <br>"+
		              "End date: "+endDate+" <br>"+
		              "Registration period: "+bookingForm.getRegistrationPeriodDesc()+" <br>"+
		              "Registration year: "+bookingForm.getRegistrationYear()+" <br>"+
		              "Description: "+bookingForm.getDescription()+" <br>"+
		              "rebroadcast: "+tmpIsRebroadcast+" <br>"+
		              "<br> <br>";
		}
		if (bookingForm.getSystemID().equals(bookingForm.getVenbook())) {
            try {
            	String lectureContact = db3.getlecturerContactDetails(bookingForm.getCreatedBy());
            	String[] temp = null;
	            temp = lectureContact.split("#");
	            bookingForm.setLecturerName(temp[0]);
	            bookingForm.setLecturerNum1(temp[1]);
	            bookingForm.setEmail(temp[2]);
            } catch (Exception e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
			body = "<html>"+
				      "<body>"+
					  "A new Venue booking was placed <br>"+
					  "Booking created by: "+bookingForm.getCreatedBy()+" - "+bookingForm.getLecturerName()+"<br>"+
					  "Contact details: "+bookingForm.getLecturerNum1()+"; "+bookingForm.getEmail()+" <br>"+
        			  "Heading: "+bookingForm.getHeading()+" <br>"+
		              "Start date: "+startDate+" <br>"+
		              "End date: "+endDate+" <br>"+
		              "Description: "+bookingForm.getDescription()+" <br>"+
		              "<br> <br>";
		}
		if (bookingForm.getSystemID().equals(bookingForm.getSatbook())) {
			body = body+"SUBJECTS: <br>"+
		         "_________<br>";

			ArrayList subjectList = bookingForm.getSelectedSubjectsAL();
			for (int i=0; i < subjectList.size(); i++) {
				LabelValueBean tmpSubjCode = (LabelValueBean) subjectList.get(i);
				body = body+tmpSubjCode.getValue()+" <br>";
			}
		}

		body = body + "<br> <br> MATERIALS: <br> __________<br>";
		ArrayList materialList = bookingForm.getSelectedMaterialsAL();
		for (int i=0; i< materialList.size();i++) {
			LabelValueBean tmpMatCode = (LabelValueBean) materialList.get(i);
			body = body+tmpMatCode.getValue()+" <br>";
		}
		
		if (bookingForm.getSystemID().equals(bookingForm.getSatbook())) {
	
			body = body + "<br> <br> VISITORS: <br> _________ <br>";
			ArrayList visitorList = bookingForm.getVisitors();
			for (int i=0; i< visitorList.size();i++) {
				LabelValueBean tmpVisCode = (LabelValueBean) visitorList.get(i);
				body = body+tmpVisCode.getLabel()+" <br>";
			}
		}
		
		body = body + "<br> <br> VENUES: <br> ___________ <br>";
		ArrayList classroomList = bookingForm.getSelectedClassroomsAL();
		for (int i=0; i< classroomList.size();i++) {
			LabelValueBean tmpClassCode = (LabelValueBean) classroomList.get(i);
			body = body+tmpClassCode.getValue()+" <br>";
		}

		body = body+"</body> </html>";

		// select lecturer email address
		String lecturerEmailAddress = "";
		try {
			if (bookingForm.getSystemID().equals(bookingForm.getSatbook())) {
				lecturerEmailAddress = db3.getLecturerEmailAddress(bookingForm.getLecturerNovellId());
			} else {
				lecturerEmailAddress = db3.getLecturerEmailAddress(bookingForm.getCreatedBy());
			}
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		// send email to user stating that his booking was created.
		if (!lecturerEmailAddress.equals(" ")) {
			try {
				sendEmail(subject,body,lecturerEmailAddress);
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
		
		// send e-mails to administrators for satbook
		if (bookingForm.getSystemID().equals(bookingForm.getSatbook())) {
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
						sendEmail(subject,body,assistant.getAssistantEmail());
					} catch (AddressException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}

		// for video conferencing send email to vcbookingEmail
		if (bookingForm.getSystemID().equals(bookingForm.getVenbook())) {
			String vcBookingEmail = ServerConfigurationService.getString("vcbookingEmail");
			if (null != vcBookingEmail) {
				try {
					sendEmail(subject,body,vcBookingEmail);
				} catch (AddressException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			// send mail to venue facilitators
			for (int i=0; i< classroomList.size();i++) {
				LabelValueBean tmpClassCode = (LabelValueBean) classroomList.get(i);
				String email = "";
				// select e-mail address
				try {
					email =  db2.selectVenuesContact2(bookingForm.getSystemID(),tmpClassCode.getLabel());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					sendEmail(subject,body,email);
				} catch (AddressException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		// save log
		UsageSession usageSession = usageSessionService.getSession();
		eventTrackingService.post(
				eventTrackingService.newEvent(EventTrackingTypes.EVENT_SATBOOK_BKNG_ADD, "Booking added for lecturer="+bookingForm.getLecturerNovellId()+" booking id= "+bkngId, false), usageSession);

		// set start date to be the date daily view
		SatbookDailyForm satbookDailyForm = new SatbookDailyForm();
		satbookDailyForm.setViewDate(startDate);

		bookingForm.resetFormbean(mapping,request);

		return "dailyview";
	}
/* Remove 31/8/2010
	public ActionForward backToStep2(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) {

		BookingForm bookingForm = (BookingForm)form;

		request.setAttribute("bookingForm", bookingForm);

		// set subject list for lecturer, registration year and period
		SatbookOracleDAO db1 = new SatbookOracleDAO();
		try {
			bookingForm.setSubjects(db1.selectSubjects(bookingForm.getLecturerNovellId(),
					bookingForm.getRegistrationYear(),
					bookingForm.getRegistrationPeriod()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return mapping.findForward("bookingstep2");
	}

	public ActionForward backToStep3(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) {

		BookingForm bookingForm = (BookingForm)form;

		request.setAttribute("bookingForm", bookingForm);

		// set materials list
		SatbookDAO db = new SatbookDAO();
		try {
			bookingForm.setMaterials(db.selectMaterialList(false,bookingForm.getSystemID()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return mapping.findForward("bookingstep3");
	}

	public ActionForward backToStep4(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) {

		BookingForm bookingForm = (BookingForm)form;

		request.setAttribute("bookingForm", bookingForm);

		return mapping.findForward("bookingstep4");
	}

	public ActionForward backToStep5(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) {

		BookingForm bookingForm = (BookingForm)form;

		request.setAttribute("bookingForm", bookingForm);

		// set classroom list
		SatbookDAO db = new SatbookDAO();
		try {
			bookingForm.setClassrooms(db.selectClassrooms(false,bookingForm.getSystemID()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return mapping.findForward("bookingstep5");
	}
	*/ 
	


	public ActionForward viewBooking(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) {
		
		sessionManager=(SessionManager) ComponentManager.get(SessionManager.class);
		toolManager = (ToolManager) ComponentManager.get(ToolManager.class);

		BookingForm bookingForm = (BookingForm)form;
		SatbookMonthlyForm monthlyForm = (SatbookMonthlyForm)request.getSession().getAttribute("monthlyForm");

		// Select the systemID 1=satbook; 2=venbook
		bookingForm.setSystemID(monthlyForm.getSystemID());
		bookingForm.setBkngId(request.getAttribute("bookingid").toString());
		
		SatbookBookingDAO db1 = new SatbookBookingDAO();
		SatbookDAO db2 = new SatbookDAO();
		SatbookOracleDAO satbookOracleDao=new SatbookOracleDAO();

		String lectureContact = "";
		// set url for print
		String serverPath = ServerConfigurationService.getToolUrl();
		String toolId = toolManager.getCurrentPlacement().getId();
		String url = ServerConfigurationService.getServerUrl()+"/unisa-satbook/satbookBooking.do?action=viewbooking&bookingid="+bookingForm.getBkngId();
		url = serverPath.trim() + "/" + toolId.trim() + "/satbookBooking.do?action=print";
		bookingForm.setUrl(url);
		request.setAttribute("url",url);

		try {
			db1.selectAllBkngDetail(bookingForm);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       
		// set duration
		try {
			bookingForm.setDuration(db1.calculateBookingDuration(bookingForm.getStartHH(),bookingForm.getEndHH(),
					bookingForm.getStartMM(),bookingForm.getEndMM()));
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		//select lecture detail
		try {
			lectureContact = satbookOracleDao.getlecturerContactDetails(bookingForm.getLecturerNovellId());
			String[] temp = null;
            temp = lectureContact.split("#");
            bookingForm.setLecturerName(temp[0]);
            bookingForm.setLecturerNum1(temp[1]);
            bookingForm.setEmail(temp[2]);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Session currentSession =sessionManager.getCurrentSession();
		String userID = currentSession.getUserId();
		try {
			bookingForm.setUserPermission(db2.getUserRights(userID));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		bookingForm.setPageStatus("VIEW");
		try {
			bookingForm.setDuration(db1.calculateBookingDuration(bookingForm.getStartHH(),bookingForm.getEndHH(),
					bookingForm.getStartMM(),bookingForm.getEndMM()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		request.setAttribute("bookingForm", bookingForm);

		return mapping.findForward("viewbooking");
	}

	public ActionForward editBooking(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) {
		
		sessionManager=(SessionManager) ComponentManager.get(SessionManager.class);

		BookingForm bookingForm = (BookingForm)form;
		ActionMessages messages = new ActionMessages();
		SatbookMonthlyForm monthlyForm = (SatbookMonthlyForm)request.getSession().getAttribute("monthlyForm");

		// Select the systemID 1=satbook; 2=venbook
		bookingForm.setSystemID(monthlyForm.getSystemID());
				
		bookingForm.setBkngId(request.getAttribute("bookingid").toString());
		SatbookBookingDAO db1 = new SatbookBookingDAO();
		SatbookDAO db2 = new SatbookDAO();

		try {
			db1.selectAllBkngDetail(bookingForm);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		Session currentSession = sessionManager.getCurrentSession();
		String userID = currentSession.getUserId();
		try {
			bookingForm.setUserPermission(db2.getUserRights(userID));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String mayUpdate = "FALSE";
		try {
			mayUpdate = db2.mayBookingBeUpdated(bookingForm.getBkngId());
			bookingForm.setHeadingMayUpdate(mayUpdate);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if ((mayUpdate.equals("TRUE"))&&(bookingForm.getUserPermission().equals("ACCESS"))) {
			// check if lecturer may update this booking
			if (bookingForm.getLecturerNovellId().toUpperCase().equals(currentSession.getUserEid().toUpperCase())) {
				mayUpdate = "TRUE";
			} else {
				mayUpdate = "FALSE";
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "This booking can only be updated by the lecturer that loaded this booking."));
				addErrors(request, messages);

				SatbookDailyForm satbookDailyForm = new SatbookDailyForm();
				satbookDailyForm.setViewDate(bookingForm.getStartDate());
				return mapping.findForward("dailyview");
			}
			//bookingForm.resetFormbean(mapping,request);
			//return mapping.findForward("dailyview");		
		}
		
		if (bookingForm.getUserPermission().equals("MAINTAIN")) {
			mayUpdate = "TRUE";
		}

		bookingForm.setMayUpdate(mayUpdate);

		try {
			bookingForm.setDuration(db1.calculateBookingDuration(bookingForm.getStartHH(),bookingForm.getEndHH(),
					bookingForm.getStartMM(),bookingForm.getEndMM()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		bookingForm.setPageStatus("EDIT");
		request.setAttribute("bookingForm", bookingForm);

		return mapping.findForward("viewbooking");
	}

	public ActionForward editHeading(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) {
		
		sessionManager=(SessionManager) ComponentManager.get(SessionManager.class);

		BookingForm bookingForm = (BookingForm) form;
		System.out.println(">>>>>>>>>>>>>>>>>>>systemID "+bookingForm.getSystemID());
		SatbookOracleDAO db1 = new SatbookOracleDAO();
		SatbookDAO db2 = new SatbookDAO();
		Session currentSession = sessionManager.getCurrentSession();
		String userID = currentSession.getUserId();
		try {
			bookingForm.setUserPermission(db2.getUserRights(userID));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (bookingForm.getUserPermission().equals("ACCESS")) {
			bookingForm.setLecturerNovellId(currentSession.getUserEid());
		}

		String tmp[] = bookingForm.getStartDate().split("-");
		bookingForm.setStartYear(tmp[0]);
		//bookingForm.setStartMM(tmp[1]);
		bookingForm.setStartDay(tmp[2]);
		bookingForm.setStartMonth(tmp[1]);
	
		if (bookingForm.getSystemID().equals(bookingForm.getSatbook())) {
       		String rb[] = null;
        	if (bookingForm.getRebroadcast().equals("true")){
        		if (bookingForm.getRebroadDate().equals(" ") || bookingForm.getRebroadDate().equals("") || bookingForm.getRebroadDate() == null) {
        			bookingForm.setRebroadDay("");
        			bookingForm.setRebroadMonth("");
        			bookingForm.setRebroadYear("");
                } else {
               		rb=bookingForm.getRebroadDate().split("-");
                	bookingForm.setRebroadDay(rb[0]);
	                if (rb[1].startsWith("0")){
                        bookingForm.setRebroadMonth(rb[1].substring(1));
    	            } else {
                        bookingForm.setRebroadMonth(rb[1]);
        	        }
            	    bookingForm.setRebroadYear(rb[2]);
                }
        	}
		}
		
		// set lists
		try {
			bookingForm.setBkngTypeList(db2.selectBkngTypeList(bookingForm.getSystemID(),"label"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		request.setAttribute("monthList", bookingForm.getMonthList());
		request.setAttribute("yearList", bookingForm.getYearList());
		request.setAttribute("registrationPeriodList",bookingForm.getRegistrationPeriodList());
		request.setAttribute("pageStatus",bookingForm.getPageStatus());
		request.setAttribute("bkngTypeList",bookingForm.getBkngTypeList());

		bookingForm.setPageStatus("EDIT");

		return mapping.findForward("bookingstep1");
	}

	public ActionForward editSubjects(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) {

		BookingForm bookingForm = (BookingForm) form;
		SatbookOracleDAO db1 = new SatbookOracleDAO();

		// set subject list for lecturer, registration year and period
		try {
			bookingForm.setSubjects(db1.selectSubjects(bookingForm.getLecturerNovellId(),
					bookingForm.getRegistrationYear(),
					bookingForm.getRegistrationPeriod()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String[] list1 = new String[bookingForm.getSubjects().size()];

		for (int j=0; j<= bookingForm.getSubjects().size()-1; j++) {
			LabelValueBean tmpSubjCode = (LabelValueBean) bookingForm.getSubjects().get(j);
			for (int k=0; k <= bookingForm.getSelectedSubjectsAL().size()-1; k++) {
				LabelValueBean tmpSubjCodeAL = (LabelValueBean) bookingForm.getSelectedSubjectsAL().get(k);
				if (tmpSubjCode.getValue().equals(tmpSubjCodeAL.getValue())) {
					list1[j] = Integer.toString(j);
				}
			}
		}

		bookingForm.setSelectedSubjects(list1);

		bookingForm.setPageStatus("EDIT");

		return mapping.findForward("bookingstep2");
	}

	public ActionForward editMaterials(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) {

		BookingForm bookingForm = (BookingForm) form;
		SatbookOracleDAO db1 = new SatbookOracleDAO();
		SatbookDAO db = new SatbookDAO();

		// set subject list for lecturer, registration year and period
		try {
			bookingForm.setMaterials(db.selectMaterialList(false,bookingForm.getSystemID()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String[] list1 = new String[bookingForm.getMaterials().size()];

		for (int j=0; j<= bookingForm.getMaterials().size()-1; j++) {
			LabelValueBean tmpMatCode = (LabelValueBean) bookingForm.getMaterials().get(j);
			for (int k=0; k <= bookingForm.getSelectedMaterialsAL().size()-1; k++) {
				LabelValueBean tmpMatCodeAL = (LabelValueBean) bookingForm.getSelectedMaterialsAL().get(k);
				if (tmpMatCode.getValue().equals(tmpMatCodeAL.getValue())) {
					list1[j] = Integer.toString(j);
				}
			}
		}

		bookingForm.setSelectedMaterials(list1);

		bookingForm.setPageStatus("EDIT");

		return mapping.findForward("bookingstep3");
	}

	public ActionForward editVisitors(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) {

		BookingForm bookingForm = (BookingForm) form;

		if (bookingForm.getVisitors().size() >= 1) {
			LabelValueBean tmpVisitor1 = (LabelValueBean) bookingForm.getVisitors().get(0);
			bookingForm.setVisitor1(tmpVisitor1.getLabel());
		}

		if (bookingForm.getVisitors().size() >= 2) {
			LabelValueBean tmpVisitor2 = (LabelValueBean) bookingForm.getVisitors().get(1);
			bookingForm.setVisitor2(tmpVisitor2.getLabel());
		}

		if (bookingForm.getVisitors().size() >= 3) {
			LabelValueBean tmpVisitor3 = (LabelValueBean) bookingForm.getVisitors().get(2);
			bookingForm.setVisitor3(tmpVisitor3.getLabel());
		}

		if (bookingForm.getVisitors().size() >= 4) {
			LabelValueBean tmpVisitor4 = (LabelValueBean) bookingForm.getVisitors().get(3);
			bookingForm.setVisitor4(tmpVisitor4.getLabel());
		}

		bookingForm.setPageStatus("EDIT");

		return mapping.findForward("bookingstep4");
	}

	public ActionForward editClassrooms(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) {

		BookingForm bookingForm = (BookingForm) form;
		SatbookDAO db = new SatbookDAO();

		// set subject list for lecturer, registration year and period
		try {
			bookingForm.setClassrooms(db.selectClassrooms(false,bookingForm.getSystemID()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String[] list1 = new String[bookingForm.getClassrooms().size()];

		for (int j=0; j<= bookingForm.getClassrooms().size()-1; j++) {
			LabelValueBean tmpClassroomCode = (LabelValueBean) bookingForm.getClassrooms().get(j);
			for (int k=0; k <= bookingForm.getSelectedClassroomsAL().size()-1; k++) {
				LabelValueBean tmpClassroomCodeAL = (LabelValueBean) bookingForm.getSelectedClassroomsAL().get(k);
				if (tmpClassroomCode.getValue().equals(tmpClassroomCodeAL.getValue())) {
					list1[j] = Integer.toString(j);
				}
			}
		}

		bookingForm.setSelectedClassrooms(list1);

		bookingForm.setPageStatus("EDIT");

		return mapping.findForward("bookingstep5");
	}

	public ActionForward selectAllClassrooms(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) {

		BookingForm bookingForm = (BookingForm) form;
		SatbookDAO db = new SatbookDAO();

		try {
			bookingForm.setClassrooms(db.selectClassrooms(false,bookingForm.getSystemID()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			bookingForm.setSelectedClassroomsAL(db.selectClassrooms(false,bookingForm.getSystemID()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String[] list1 = new String[bookingForm.getClassrooms().size()];

		for (int j=0; j<= bookingForm.getClassrooms().size()-1; j++) {
			LabelValueBean tmpClassroomCode = (LabelValueBean) bookingForm.getClassrooms().get(j);
			for (int k=0; k <= bookingForm.getSelectedClassroomsAL().size()-1; k++) {
				LabelValueBean tmpClassroomCodeAL = (LabelValueBean) bookingForm.getSelectedClassroomsAL().get(k);
				if (tmpClassroomCode.getValue().equals(tmpClassroomCodeAL.getValue())) {
					list1[j] = Integer.toString(j);
				}
			}
		}

		bookingForm.setSelectedClassrooms(list1);

		if (bookingForm.getPageStatus().equals("EDIT")) {
			bookingForm.setPageStatus("EDIT");
		} else {
			bookingForm.setPageStatus("ADD");
		}	

		return mapping.findForward("bookingstep5");
	}

	public ActionForward deselectAllClassrooms(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) {

		BookingForm bookingForm = (BookingForm) form;
		SatbookDAO db = new SatbookDAO();

		try {
			bookingForm.setClassrooms(db.selectClassrooms(false,bookingForm.getSystemID()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			bookingForm.setSelectedClassroomsAL(null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String[] list1 = new String[bookingForm.getClassrooms().size()];

		for (int j=0; j<= bookingForm.getClassrooms().size()-1; j++) {
			LabelValueBean tmpClassroomCode = (LabelValueBean) bookingForm.getClassrooms().get(j);
			if (null != bookingForm.getSelectedClassroomsAL()) {
				for (int k=0; k <= bookingForm.getSelectedClassroomsAL().size()-1; k++) {
					LabelValueBean tmpClassroomCodeAL = (LabelValueBean) bookingForm.getSelectedClassroomsAL().get(k);
					if (tmpClassroomCode.getValue().equals(tmpClassroomCodeAL.getValue())) {
						list1[j] = Integer.toString(j);
					}
				}
			}
		}

		bookingForm.setSelectedClassrooms(list1);

		if (bookingForm.getPageStatus().equals("EDIT")) {
			bookingForm.setPageStatus("EDIT");
		} else {
			bookingForm.setPageStatus("ADD");
		}

		return mapping.findForward("bookingstep5");
	}


	public ActionForward editAdmin(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) {

		BookingForm bookingForm = (BookingForm) form;
		SatbookDAO db1 = new SatbookDAO();
		SatbookBookingDAO db2 = new SatbookBookingDAO();
		ArrayList savedAssistants = new ArrayList();

		// get list of assistant types
		try {
			bookingForm.setAssistantTypeList(db1.selectAssistantTypeList(true, bookingForm.getSystemID()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// get assistants already saved for booking
		try {
			savedAssistants = db2.selectBookingAssistants(bookingForm.getBkngId());
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		// get all assistants for the types
		for (int i=0; i < bookingForm.getAssistantTypeList().size(); i++) {
			AssistantTypeRecord assistantTypeRecord = (AssistantTypeRecord) bookingForm.getAssistantTypeList().get(i);
			try {
				assistantTypeRecord.setAssistantsList(db1.selectAssistantList(assistantTypeRecord.getAssTypeId(),false));

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// check if an assistant was selected for this type
			for (int j=0; j < savedAssistants.size(); j++) {
				LabelValueBean tmpAss = (LabelValueBean) savedAssistants.get(j);
				String tmpAssistant = tmpAss.getLabel().toString();
				String tmpType = tmpAss.getValue().toString();
				if (tmpType.equals(assistantTypeRecord.getAssTypeId())) {
					AssistantRecord ass = new AssistantRecord();
					try {
						ass = db1.selectAssistant(tmpAssistant);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					assistantTypeRecord.setSelectedAssistant(tmpAssistant);
					assistantTypeRecord.setSelectedAssName(ass.getAssistantName());
				}
			}
		}

		// temp set of assistants list as receiving error (bean not found for assistantList when trying to do it
		// per assistant type
		try {
			request.setAttribute("assistantsList",db1.selectAssistantList("0",false));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		request.setAttribute("assistantTypeList",bookingForm.getAssistantTypeList());
		request.setAttribute("adminMaterials",bookingForm.getAdminMaterials());

		return mapping.findForward("bookingadmin");
	}

	public ActionForward saveAdmin(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) {

		BookingForm bookingForm = (BookingForm) form;
		SatbookBookingDAO db1 = new SatbookBookingDAO();

		// store material detail
		ArrayList materialList = bookingForm.getAdminMaterials();
		for (int i=0; i < materialList.size(); i++) {
			SatbookBkngMaterialRecord materialRecord = (SatbookBkngMaterialRecord) materialList.get(i);
			materialRecord.setTimeIn(bookingForm.getStartDate()+" "+materialRecord.getTimeInHH()+":"+materialRecord.getTimeInMM()+":"+materialRecord.getTimeInSS());
			materialRecord.setTimeOut(bookingForm.getStartDate()+" "+materialRecord.getTimeOutHH()+":"+materialRecord.getTimeOutMM()+":"+materialRecord.getTimeOutSS());
			materialRecord.setDuration(bookingForm.getStartDate()+" "+materialRecord.getDurationHH()+":"+materialRecord.getDurationMM()+":"+materialRecord.getDurationSS());

			if (materialRecord.getTimeInFF() == null) {
				materialRecord.setTimeInFF("00");
			}
			if (materialRecord.getTimeOutFF() == null) {
				materialRecord.setTimeOutFF("00");
			}
			if (materialRecord.getDurationFF() == null) {
				materialRecord.setDurationFF("00");
			}
			if (materialRecord.getDuplication() == null) {
				materialRecord.setDuplication("0");
			}

			try {
				db1.updateSatbookBkngMaterialAdmin(materialRecord);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		// save technical error detail
		try {
			db1.saveBookingHeadingTechError(bookingForm.getBkngId(),bookingForm.getTechnicalError());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// save assistant detail
		try {
			db1.saveBookingAssistants("EDIT",bookingForm.getBkngId(),bookingForm.getAssistantTypeList());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		bookingForm.setPageStatus("EDIT");
		request.setAttribute("bookingForm", bookingForm);

		return mapping.findForward("viewbooking");
	}


	public String validateLecturer(String lecturerNovellId) {
		String lecturerDetail = "";

		// 1. Does lecturer exist in table STAFF?
		SatbookOracleDAO db1 = new SatbookOracleDAO();
		SatbookDAO db2 = new SatbookDAO();

		try {
			lecturerDetail = db1.lecturerExistStaff(lecturerNovellId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// 2. if not in STAFF does lecturer exist in table USR
		if ((lecturerDetail == null)||(lecturerDetail.length() == 0)) {
			try {
				lecturerDetail = db1.lecturerExistUsr(lecturerNovellId);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		if ((lecturerDetail != null)&&(lecturerDetail.length() > 0)) {
			// 		3. select lecturer alternative contact num
			LecturerForm lect = new LecturerForm();
			lect = db2.selectLecturer(lecturerNovellId);

			if (lect != null) {
				lecturerDetail = lecturerDetail+"#"+lect.getTelNumber();
			} else {
				lecturerDetail = lecturerDetail+"# ";
			}
		}

		return lecturerDetail;
	}

	public ActionForward goBackTo(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) {
		// set start date to be the date daily view

		BookingForm bookingForm = (BookingForm) form;
		request.setAttribute("bookingForm", bookingForm);
			
		if (request.getParameter("atstep").equals("bookingadminpage")) {
			bookingForm.setPageStatus("VIEW");
			return mapping.findForward("viewbooking");
		} else if (request.getParameter("atstep").equals("2")) {
			// set lists
			request.setAttribute("monthList", bookingForm.getMonthList());
			request.setAttribute("yearList", bookingForm.getYearList());
			request.setAttribute("registrationPeriodList",bookingForm.getRegistrationPeriodList());
			request.setAttribute("pageStatus",bookingForm.getPageStatus());
			request.setAttribute("bkngTypeList",bookingForm.getBkngTypeList());
			if (Integer.parseInt(bookingForm.getStartMonth()) <= 9) {
				bookingForm.setStartMonth(bookingForm.getStartMonth().substring(1,2));
			} 
			
			return mapping.findForward("bookingstep1");
			
		} else if (request.getParameter("atstep").equals("3")) {

			if (bookingForm.getSystemID().equals(bookingForm.getSatbook())) {
				// set subject list for lecturer, registration year and period
				SatbookOracleDAO db1 = new SatbookOracleDAO();
				try {
					bookingForm.setSubjects(db1.selectSubjects(bookingForm.getLecturerNovellId(),
							bookingForm.getRegistrationYear(),
							bookingForm.getRegistrationPeriod()));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				return mapping.findForward("bookingstep2");
			} else { // if systemId is venbook
				// set lists
				request.setAttribute("monthList", bookingForm.getMonthList());
				request.setAttribute("yearList", bookingForm.getYearList());
				request.setAttribute("registrationPeriodList",bookingForm.getRegistrationPeriodList());
				request.setAttribute("pageStatus",bookingForm.getPageStatus());
				request.setAttribute("bkngTypeList",bookingForm.getBkngTypeList());
				if (Integer.parseInt(bookingForm.getStartMonth()) >= 9) {
					bookingForm.setStartMonth(bookingForm.getStartMonth().substring(1,2));
				} 
				return mapping.findForward("bookingstep1");
			}
		} else if (request.getParameter("atstep").equals("4")) {

			// set materials list
			SatbookDAO db = new SatbookDAO();
			try {
				bookingForm.setMaterials(db.selectMaterialList(false,bookingForm.getSystemID()));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return mapping.findForward("bookingstep3");
		} else if (request.getParameter("atstep").equals("5")) {

			if (bookingForm.getSystemID().equals(bookingForm.getSatbook())) {
				return mapping.findForward("bookingstep4");
			} else {
				// set materials list
				SatbookDAO db = new SatbookDAO();
				try {
					bookingForm.setMaterials(db.selectMaterialList(false,bookingForm.getSystemID()));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				return mapping.findForward("bookingstep3");	
			}
		} else if (request.getParameter("atstep").equals("6")) {

			// set classroom list
			SatbookDAO db = new SatbookDAO();
			try {
				bookingForm.setClassrooms(db.selectClassrooms(false,bookingForm.getSystemID()));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return mapping.findForward("bookingstep5");
		} else {
			
			bookingForm.resetFormbean(mapping,request);
			SatbookDailyForm satbookDailyForm = new SatbookDailyForm();
			satbookDailyForm.setViewDate(bookingForm.getStartDate());

			
			return mapping.findForward("dailyview");
		}
		
	}
	

	public ActionForward cancel(ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response){

		BookingForm bookingForm = (BookingForm)form;

		bookingForm.resetFormbean(mapping,request);
		SatbookDailyForm satbookDailyForm = new SatbookDailyForm();
		satbookDailyForm.setViewDate(bookingForm.getStartDate());

		return mapping.findForward("dailyview");
	}

	public ActionForward bookingPrint(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		BookingForm bookingForm = (BookingForm)form;
		bookingForm.setViewStatus("print");

		request.setAttribute("bookingForm", bookingForm);

			return mapping.findForward("viewbooking");

			}

	public void sendEmail(String subject, String body, String emailAddress) throws AddressException {
		
		emailService=(EmailService) ComponentManager.get(EmailService.class);

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

		emailService.sendMail(iaReplyTo[0],iaTo,subject,body,iaHeaderTo,iaReplyTo,contentList);
		log.info("sending mail from "+iaReplyTo[0]+" to "+toEmail.toString());
	} // end of sendEmail
}