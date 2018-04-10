//Created by MyEclipse Struts
// XSL source (default): platform:/plugin/com.genuitec.eclipse.cross.easystruts.eclipse_3.9.210/xslt/JavaClass.xsl

package za.ac.unisa.lms.tools.satbook.forms;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.ValidatorForm;

/**
 * MyEclipse Struts
 * Creation date: 09-11-2006
 *
 * XDoclet definition:
 * @struts:form name="satbookMonthlyForm"
 */
public class SatbookMonthlyForm extends ValidatorForm {

	// --------------------------------------------------------- Instance Variables

	private String userPermission;
	/** viewYear property */
	private String viewYear;

	/** viewMonth property */
	private String viewMonth;

	/** viewDate property */
	private ArrayList viewDate = new ArrayList();

	private String selectMonth;
	private String selectYear;
	private String monthName;
	private String yearName;
	private String selectedDay;

	private String selectedDate;

	private String[] unisaDays;
	
	//setting the system id "1" for sat_book & for "2" for venue booking
	private String systemID;
	private final String satbook="1";
	private final String venbook="2";
	
	// --------------------------------------------------------- Methods

	public String getSatbook() {
		return satbook;
	}

	public String getVenbook() {
		return venbook;
	}

	
	public String getSystemID() {
		return systemID;
	}

	public void setSystemID(String systemID) {
		this.systemID = systemID;
	}

	/**
	 * Method validate
	 * @param mapping
	 * @param request
	 * @return ActionErrors
	 */
	public ActionErrors validate(
		ActionMapping mapping,
		HttpServletRequest request) {

		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Method reset
	 * @param mapping
	 * @param request
	 */
	public void reset(ActionMapping mapping, HttpServletRequest request) {

		// TODO Auto-generated method stub
	}

	/**
	 * Returns the viewYear.
	 * @return String
	 */
	public String getViewYear() {
		return viewYear;
	}

	/**
	 * Set the viewYear.
	 * @param viewYear The viewYear to set
	 */
	public void setViewYear(String viewYear) {
		this.viewYear = viewYear;
	}

	/**
	 * Returns the viewMonth.
	 * @return String
	 */
	public String getViewMonth() {
		return viewMonth;
	}

	/**
	 * Set the viewMonth.
	 * @param viewMonth The viewMonth to set
	 */
	public void setViewMonth(String viewMonth) {
		this.viewMonth = viewMonth;
	}

	/**
	 * Returns the viewDate.
	 * @return String
	 */
	public ArrayList getViewDate() {
		return viewDate;
	}

	/**
	 * Set the viewDate.
	 * @param viewDate The viewDate to set
	 */
	public void setViewDate(ArrayList viewDate) {
		this.viewDate = viewDate;
	}

	public String getSelectMonth() {
		return selectMonth;
	}

	public void setSelectMonth(String selectMonth) {
		this.selectMonth = selectMonth;
	}

	public String getSelectYear() {
		return selectYear;
	}

	public void setSelectYear(String selectYear) {
		this.selectYear = selectYear;
	}

	public String getMonthName() {
		return monthName;
	}

	public void setMonthName(String monthName) {
		this.monthName = monthName;
	}

	public String getYearName() {
		return yearName;
	}

	public void setYearName(String yearName) {
		this.yearName = yearName;
	}

	public String getSelectedDay() {
		return selectedDay;
	}

	public void setSelectedDay(String selectedDay) {
		this.selectedDay = selectedDay;
	}

	public String getSelectedDate() {
		return selectedDate;
	}

	public void setSelectedDate(String selectedDate) {
		this.selectedDate = selectedDate;
	}

	public String getUserPermission() {
		return userPermission;
	}

	public void setUserPermission(String userPermission) {
		this.userPermission = userPermission;
	}
}

