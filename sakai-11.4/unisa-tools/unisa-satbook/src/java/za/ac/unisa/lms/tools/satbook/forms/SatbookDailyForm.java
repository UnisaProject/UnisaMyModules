//Created by MyEclipse Struts
// XSL source (default): platform:/plugin/com.genuitec.eclipse.cross.easystruts.eclipse_3.9.210/xslt/JavaClass.xsl

package za.ac.unisa.lms.tools.satbook.forms;

import java.util.ArrayList;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.ValidatorForm;

/**
 * MyEclipse Struts
 * Creation date: 09-11-2006
 *
 * XDoclet definition:
 * @struts:form name="satbookDailyForm"
 */
public class SatbookDailyForm extends ValidatorForm {

	// --------------------------------------------------------- Instance Variables

	private String userPermission;
	/** booking property */
	private Vector booking = new Vector();

	private ArrayList bookingList = new ArrayList();
	private ArrayList venueList = new ArrayList();
	private String selectedBooking;
	private String bkngHeading;
	private String userName;
	

	/** viewDate property */
	private String viewDate;

	private String institution;

	/** For booking report */
	private ArrayList reportTypeList = new ArrayList();
	private String selectedReportType;
	private String fromDate;
	private String fromDateDD;
	private String fromDateMM;
	private String fromDateYYYY;
	private String toDate;
	private String toDateDD;
	private String toDateMM;
	private String toDateYYYY;
	private ArrayList subName;
	
	private String weekDay;
	private boolean bookingDisable;
	private boolean deleteDisable;
	private String rebroadDate;
	private String rebroadDay;
	private String rebroadMonth;
	private String rebroadYear;
	
	private ArrayList possibleReports;
	private String selectedReport;
	

	//setting the system id "1" for sat_book & for "2" for venue booking
	private String systemID;
	
	private final String satbook="1";
	public String getSatbook() {
		return satbook;
	}



	private final String venbook="2";
	public String getVenbook() {
		return venbook;
	}

	// --------------------------------------------------------- Methods

	public String getSystemID() {
		return systemID;
	}

	public void setSystemID(String systemID) {
		this.systemID = systemID;
	}

	public String getRebroadDate() {
		return rebroadDate;
	}

	public void setRebroadDate(String rebroadDate) {
		this.rebroadDate = rebroadDate;
	}

	public String getRebroadDay() {
		return rebroadDay;
	}

	public void setRebroadDay(String rebroadDay) {
		this.rebroadDay = rebroadDay;
	}

	public String getRebroadMonth() {
		return rebroadMonth;
	}

	public void setRebroadMonth(String rebroadMonth) {
		this.rebroadMonth = rebroadMonth;
	}

	public String getRebroadYear() {
		return rebroadYear;
	}

	public void setRebroadYear(String rebroadYear) {
		this.rebroadYear = rebroadYear;
	}

	public String getWeekDay() {
		return weekDay;
	}

	public void setWeekDay(String weekDay) {
		this.weekDay = weekDay;
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
	 * Returns the booking.
	 * @return Vector
	 */
	public Vector getBooking() {
		return booking;
	}

	/**
	 * Set the booking.
	 * @param booking The booking to set
	 */
	public void setBooking(Vector booking) {
		this.booking = booking;
	}

	/**
	 * Returns the bookingList.
	 * @return ArrayList
	 */
	public ArrayList getBookingList() {
		return bookingList;
	}

	/**
	 * Set the bookingList.
	 * @param bookingList The bookingList to set
	 */
	public void setBookingList(ArrayList bookingList) {
		this.bookingList = bookingList;
	}

	/**
	 * Returns the viewDate.
	 * @return String
	 */
	public String getViewDate() {
		return viewDate;
	}

	/**
	 * Set the viewDate.
	 * @param viewDate The viewDate to set
	 */
	public void setViewDate(String viewDate) {
		this.viewDate = viewDate;
	}

	public String getSelectedBooking() {
		return selectedBooking;
	}

	public void setSelectedBooking(String selectedBooking) {
		this.selectedBooking = selectedBooking;
	}

	public String getUserPermission() {
		return userPermission;
	}

	public void setUserPermission(String userPermission) {
		this.userPermission = userPermission;
	}

	public String getInstitution() {
		return institution;
	}

	public void setInstitution(String institution) {
		this.institution = institution;
	}

	public ArrayList getReportTypeList() {

		reportTypeList = new ArrayList();
		reportTypeList.add(new org.apache.struts.util.LabelValueBean("..","0"));
		reportTypeList.add(new org.apache.struts.util.LabelValueBean("Confirmed","Y"));
		reportTypeList.add(new org.apache.struts.util.LabelValueBean("Not Confirmed","N"));
		reportTypeList.add(new org.apache.struts.util.LabelValueBean("Both","B"));

		return reportTypeList;
	}

	public void setReportTypeList(ArrayList reportTypeList) {
		this.reportTypeList = reportTypeList;
	}

	public String getSelectedReportType() {
		return selectedReportType;
	}

	public void setSelectedReportType(String selectedReportType) {
		this.selectedReportType = selectedReportType;
	}

	public String getFromDate() {
		return fromDate;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	public String getFromDateDD() {
		return fromDateDD;
	}

	public void setFromDateDD(String fromDateDD) {
		this.fromDateDD = fromDateDD;
	}

	public String getFromDateMM() {
		return fromDateMM;
	}

	public void setFromDateMM(String fromDateMM) {
		this.fromDateMM = fromDateMM;
	}

	public String getFromDateYYYY() {
		return fromDateYYYY;
	}

	public void setFromDateYYYY(String fromDateYYYY) {
		this.fromDateYYYY = fromDateYYYY;
	}

	public String getToDate() {
		return toDate;
	}

	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	public String getToDateDD() {
		return toDateDD;
	}

	public void setToDateDD(String toDateDD) {
		this.toDateDD = toDateDD;
	}

	public String getToDateMM() {
		return toDateMM;
	}

	public void setToDateMM(String toDateMM) {
		this.toDateMM = toDateMM;
	}

	public String getToDateYYYY() {
		return toDateYYYY;
	}

	public void setToDateYYYY(String toDateYYYY) {
		this.toDateYYYY = toDateYYYY;
	}


	public String getBkngHeading() {
		return bkngHeading;
	}

	public void setBkngHeading(String bkngHeading) {
		this.bkngHeading = bkngHeading;
	}
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public ArrayList getSubName() {
		return subName;
	}

	public void setSubName(ArrayList subName) {
		this.subName = subName;
	}

	public boolean getBookingDisable() {
		return bookingDisable;
	}

	public void setBookingDisable(boolean bookingDisable) {
		this.bookingDisable = bookingDisable;
	}
	
	public boolean getdeleteDisable() {
		return deleteDisable;
	}

	public void setDeleteDisable(boolean deleteDisable) {
		this.deleteDisable = deleteDisable;
	}
	
	public ArrayList getPossibleReports() {
	
		possibleReports = new ArrayList();
		possibleReports.add(new org.apache.struts.util.LabelValueBean("Choose a report..","0"));
		possibleReports.add(new org.apache.struts.util.LabelValueBean("Detailed Report","DetailedReport"));
		possibleReports.add(new org.apache.struts.util.LabelValueBean("Hours per month and venue Report","HoursReport"));
		possibleReports.add(new org.apache.struts.util.LabelValueBean("Days per month and venue Report","DaysReport"));
		
		return possibleReports;
	}

	public void setPossibleReports(ArrayList possibleReports) {
		this.possibleReports = possibleReports;

	}
	
	public String getSelectedReport() {
		return selectedReport;
	}

	public void setSelectedReport(String selectedReport) {
		this.selectedReport = selectedReport;
	}
	
	public ArrayList getVenueList() {
		return venueList;
	}

	public void setVenueList(ArrayList venueList) {
		this.venueList = venueList;
	} 
	
}


