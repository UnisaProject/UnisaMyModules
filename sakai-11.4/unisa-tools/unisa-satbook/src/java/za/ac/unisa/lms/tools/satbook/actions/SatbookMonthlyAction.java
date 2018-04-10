//Created by MyEclipse Struts
// XSL source (default): platform:/plugin/com.genuitec.eclipse.cross.easystruts.eclipse_3.9.210/xslt/JavaClass.xsl

package za.ac.unisa.lms.tools.satbook.actions;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.LookupDispatchAction;
import org.sakaiproject.component.cover.ComponentManager;
import org.sakaiproject.email.api.EmailService;
import org.sakaiproject.event.api.EventTrackingService;
import org.sakaiproject.event.api.UsageSessionService;
import org.sakaiproject.tool.api.Session;
import org.sakaiproject.tool.api.SessionManager;
import org.sakaiproject.tool.api.ToolManager;
import org.sakaiproject.user.api.UserDirectoryService;

import za.ac.unisa.lms.tools.satbook.dao.SatbookDAO;
import za.ac.unisa.lms.tools.satbook.forms.AdminForm;
import za.ac.unisa.lms.tools.satbook.forms.AdminLinkForm;
import za.ac.unisa.lms.tools.satbook.forms.SatbookMonthlyForm;

public class SatbookMonthlyAction extends LookupDispatchAction {
	
	private SessionManager sessionManager;

	protected Map getKeyMethodMap() {
		Map map = new HashMap();

	    map.put("monthlyview","monthlyView");
	    map.put("button.monthlyview.buildcalendar", "monthlyView");
	    map.put("button.monthlyview.adminview", "adminView");
	    map.put("adminview","adminView");
	    map.put("adminLink","adminLink");
	    map.put("link.day", "linkDay");

	    return map;
	}

	public ActionForward monthlyView(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response) {
		
		sessionManager=(SessionManager) ComponentManager.get(SessionManager.class);

		SatbookMonthlyForm monthlyForm = (SatbookMonthlyForm)form;
		getYearList(request, monthlyForm);
		getChosenYearAndMonth(request, monthlyForm);
		SatbookDAO db1 = new SatbookDAO();
		if (monthlyForm.getSystemID()== null) {
			monthlyForm.setSystemID(request.getAttribute("systemID").toString());
		}

		if(null == monthlyForm.getSelectYear() | null == monthlyForm.getSelectMonth()) {
			Date d = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
			SimpleDateFormat sdf2 = new SimpleDateFormat("MM");
			monthlyForm.setSelectYear(sdf.format(d));
			monthlyForm.setSelectMonth(sdf2.format(d));
		}
		request.setAttribute("firstDayOfTheMonth", getFirstDayOfMonthAsString(monthlyForm.getSelectYear(), monthlyForm.getSelectMonth()));
		request.setAttribute("numberOfDaysInTheMonth", new Integer(getNumberOfDaysForCurrentMonth(monthlyForm.getSelectYear(),monthlyForm.getSelectMonth())));
		request.setAttribute("weeknumber", getWeekNumber(monthlyForm.getSelectYear(),monthlyForm.getSelectMonth()));
		String[] unisaDays = new String[31];
		try {
			unisaDays = db1.selectDaysPerInstitution("1",monthlyForm.getSelectYear(),monthlyForm.getSelectMonth());
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		request.setAttribute("unisaDays", unisaDays);
		request.setAttribute("noOfDays",Integer.toString(unisaDays.length));
		request.setAttribute("systemId",monthlyForm.getSystemID());
		request.setAttribute("satbook",monthlyForm.getSatbook());
		request.setAttribute("venbook",monthlyForm.getVenbook());

		Session currentSession = sessionManager.getCurrentSession();
		String userID = currentSession.getUserId();
		try {
			monthlyForm.setUserPermission(db1.getUserRights(userID));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		request.getSession().setAttribute("monthlyForm",monthlyForm);
		return mapping.findForward("monthlyview");
	}

	public ActionForward adminLink(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) {

			SatbookMonthlyForm monthlyForm = (SatbookMonthlyForm)form;
			// set the monthlyForm as a session attribute so that other actions/forms can access the MonhtlyForm formbean
			//request.getSession().setAttribute("monthlyForm",monthlyForm);
			return mapping.findForward("adminsystemlink");
		}
	public ActionForward adminView(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) {
			SatbookMonthlyForm monthlyForm = (SatbookMonthlyForm)form;
						
			System.out.println("adminSystemID  "+monthlyForm.getSystemID());	
			//monthlyForm.setSystemID(request.getAttribute("systemID").toString());
		
		
		
		return mapping.findForward("adminsystemlink");
	}

	public ActionForward linkDay(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response) {


		SatbookMonthlyForm monthlyForm = (SatbookMonthlyForm)form;
		monthlyForm.setSelectedDay(request.getParameter("day_selected"));

		String viewDate = ""; //30-12-2006
		viewDate = monthlyForm.getSelectedDay()+"-"+monthlyForm.getSelectMonth()+"-"+monthlyForm.getSelectYear();
		monthlyForm.setSelectedDate(viewDate);

		//request.setAttribute("monthlyForm", monthlyForm);
		//request.getSession().setAttribute("monthlyForm",monthlyForm);

		return mapping.findForward("dailyview");

	}

	private void getYearList(HttpServletRequest request, SatbookMonthlyForm monthlyForm) {
		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");

		String stringYear = sdf.format(d);

		Integer i = new Integer(stringYear);
		int currentYear = i.intValue();
		int prevYear = currentYear -1;
		int nextYear = currentYear + 1;

		request.setAttribute("currentYear", new Integer(currentYear));
		request.setAttribute("prevYear", new Integer(prevYear));
		request.setAttribute("nextYear", new Integer(nextYear));

	}

	private void getChosenYearAndMonth(HttpServletRequest request, SatbookMonthlyForm monthlyForm) {
		//default year and month resolves to the current year and month
		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		String stringYear = sdf.format(d);

		Integer i = new Integer(stringYear);
		int currentYear = i.intValue();
		//display month
		if(null == monthlyForm.getSelectMonth()) {
			sdf = new SimpleDateFormat("MMMM");
			monthlyForm.setMonthName(sdf.format(d));
		}else {
			//getSelectMonth() returns a integer as a string (3 --> March)
			monthlyForm.setMonthName(getMonthName(monthlyForm.getSelectMonth()));
		}
		//display year
		if(null == monthlyForm.getSelectYear()) {
			monthlyForm.setYearName("" + new Integer(currentYear));
		}else {
			monthlyForm.setYearName(monthlyForm.getSelectYear());
		}

		request.setAttribute("monthlyForm", monthlyForm);
	}

	private int getNumberOfDaysForCurrentMonth(String stringYear, String stringMonth) {
		GregorianCalendar cal = new GregorianCalendar(Integer.parseInt(stringYear), Integer.parseInt(stringMonth) -1, 01);
		int count = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		return count;
	}

	private String getWeekNumber(String year, String month) {
		Calendar weeknumber = Calendar.getInstance();
		int number = 0;
		weeknumber.set(Integer.parseInt(year),Integer.parseInt(month) -1 ,01);
		weeknumber.setFirstDayOfWeek(Calendar.SUNDAY);
		number = weeknumber.get(Calendar.WEEK_OF_YEAR);
		return "" + number;
	}

	private String getFirstDayOfMonthAsString(String year, String month) {
		Calendar beginningOfTheMonth = Calendar.getInstance();
		beginningOfTheMonth.set(Integer.parseInt(year),(Integer.parseInt(month))-1,01);
		SimpleDateFormat sdf = new SimpleDateFormat("EEE");

		if(sdf.format(beginningOfTheMonth.getTime()).equalsIgnoreCase("Sun")) {
			return "0";
		}

		if(sdf.format(beginningOfTheMonth.getTime()).equalsIgnoreCase("Mon")) {
			return "1";
		}

		if(sdf.format(beginningOfTheMonth.getTime()).equalsIgnoreCase("Tue")) {
			return "2";
		}

		if(sdf.format(beginningOfTheMonth.getTime()).equalsIgnoreCase("Wed")) {
			return "3";
		}

		if(sdf.format(beginningOfTheMonth.getTime()).equalsIgnoreCase("Thu")) {
			return "4";
		}

		if(sdf.format(beginningOfTheMonth.getTime()).equalsIgnoreCase("Fri")) {
			return "5";
		}

		if(sdf.format(beginningOfTheMonth.getTime()).equalsIgnoreCase("Sat")) {
			return "6";
		}

		return sdf.format(beginningOfTheMonth.getTime());
	}

	private String getMonthName(String value) {
		if(value.equals("01")) return "January";

		if(value.equals("02")) return "February";

		if(value.equals("03")) return "March";

		if(value.equals("04")) return "April";

		if(value.equals("05")) return "May";

		if(value.equals("06")) return "June";

		if(value.equals("07")) return "July";

		if(value.equals("08")) return "August";

		if(value.equals("09")) return "September";

		if(value.equals("10")) return "October";

		if(value.equals("11")) return "November";

		if(value.equals("12")) return "Desember";

		return null;

	}
}
