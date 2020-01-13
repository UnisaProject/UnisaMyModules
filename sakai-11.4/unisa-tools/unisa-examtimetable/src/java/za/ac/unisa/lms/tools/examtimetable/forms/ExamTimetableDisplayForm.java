//Created by MyEclipse Struts
// XSL source (default): platform:/plugin/com.genuitec.eclipse.cross.easystruts.eclipse_3.9.210/xslt/JavaClass.xsl

package za.ac.unisa.lms.tools.examtimetable.forms;

import java.util.Vector;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.ValidatorForm;

/**
 * MyEclipse Struts
 * Creation date: 04-03-2006
 *
 * XDoclet definition:
 * @struts:form name="examTimetableDisplayForm"
 */
public class ExamTimetableDisplayForm extends ValidatorForm {

	// --------------------------------------------------------- Instance Variables

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private boolean studentUser;
	private String practicalType;
	private short examYear;
	private short examPeriod;
	private String timetableStatus;
	private String timetableStatusDesc;
	private String venueCode;
	private String venueDesc;
	private String venueAddress;
	private String latitude;
	private String longitude;
	private String currentPage;
	private Student student;
	private Vector examTimetableRecord;
	private List parText4130;
	private List parText4136;
	private List parText4137;
	private List parText4138;
	private List parText4125;
	private String admissionFlag;
	private String noAdmissionFlag;
	private String fcFlag;
	private List admissionList;
	private List noAdmissionList;
	private List fcList;
	private List admParTextList;
	private String calcTextFlag;
	private String invigTelephone;
	private String pageUrl;
	private boolean databaseError;
	public String getPageUrl() {
		return pageUrl;
	}

	public void setPageUrl(String pageUrl) {
		this.pageUrl = pageUrl;
	}

	// --------------------------------------------------------- Methods
	public String getVenueAddress() {
		return venueAddress;
	}

	public void setVenueAddress(String venueAddress) {
		this.venueAddress = venueAddress;
	}

	public String getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(String currentPage) {
		this.currentPage = currentPage;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getInvigTelephone() {
		return invigTelephone;
	}

	public void setInvigTelephone(String invigTelephone) {
		this.invigTelephone = invigTelephone;
	}

	public String getCalcTextFlag() {
		return calcTextFlag;
	}

	public void setCalcTextFlag(String calcTextFlag) {
		this.calcTextFlag = calcTextFlag;
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
	 * Returns the studentUser.
	 * @return boolean
	 */
	public boolean getStudentUser() {
		return studentUser;
	}

	/**
	 * Set the studentUser.
	 * @param studentUser The studentUser to set
	 */
	public void setStudentUser(boolean studentUser) {
		this.studentUser = studentUser;
	}

	/**
	 * Returns the practicalType.
	 * @return String
	 */
	public String getPracticalType() {
		return practicalType;
	}

	/**
	 * Set the practicalType.
	 * @param practicalType The practicalType to set
	 */
	public void setPracticalType(String practicalType) {
		this.practicalType = practicalType;
	}

	public String getTimetableStatus() {
		return timetableStatus;
	}

	public void setTimetableStatus(String timetableStatus) {
		this.timetableStatus = timetableStatus;
	}

	public String getTimetableStatusDesc() {
		return timetableStatusDesc;
	}

	public void setTimetableStatusDesc(String timetableStatusDesc) {
		this.timetableStatusDesc = timetableStatusDesc;
	}

	public String getVenueCode() {
		return venueCode;
	}

	public void setVenueCode(String venueCode) {
		this.venueCode = venueCode;
	}

	public String getVenueDesc() {
		return venueDesc;
	}

	public void setVenueDesc(String venueDesc) {
		this.venueDesc = venueDesc;
	}

	/**
	 * Returns the examYear.
	 * @return short
	 */
	public short getExamYear() {
		return examYear;
	}

	/**
	 * Set the examYear.
	 * @param examYear The examYear to set
	 */
	public void setExamYear(short examYear) {
		this.examYear = examYear;
	}

	/**
	 * Returns the examPeriod.
	 * @return short
	 */
	public short getExamPeriod() {
		return examPeriod;
	}

	/**
	 * Set the examPeriod.
	 * @param examPeriod The examPeriod to set
	 */
	public void setExamPeriod(short examPeriod) {
		this.examPeriod = examPeriod;
	}

	/**
	 * Returns the student.
	 * @return student
	 */
	public Student getStudent() {
		return student;
	}

	/**
	 * Set the student.
	 * @param student The student to set
	 */
	public void setStudent(Student student) {
		this.student = student;
	}

	/**
	 * Returns the examTimetableRecord.
	 * @return vector
	 */
	public Vector getExamTimetableRecord() {
		return examTimetableRecord;
	}

	/**
	 * Set the examTimetableRecord.
	 * @param examTimetableRecord The examTimetableRecord to set
	 */
	public void setExamTimetableRecord(Vector examTimetableRecord) {
		this.examTimetableRecord = examTimetableRecord;
	}

	public List getParText4130() {
		return parText4130;
	}

	public void setParText4130(List parText4130) {
		this.parText4130 = parText4130;
	}

	public List getParText4136() {
		return parText4136;
	}

	public void setParText4136(List parText4136) {
		this.parText4136 = parText4136;
	}

	public List getParText4137() {
		return parText4137;
	}

	public void setParText4137(List parText4137) {
		this.parText4137 = parText4137;
	}

	public List getParText4138() {
		return parText4138;
	}

	public void setParText4138(List parText4138) {
		this.parText4138 = parText4138;
	}

	public String getAdmissionFlag() {
		return admissionFlag;
	}

	public void setAdmissionFlag(String admissionFlag) {
		this.admissionFlag = admissionFlag;
	}

	public String getNoAdmissionFlag() {
		return noAdmissionFlag;
	}

	public void setNoAdmissionFlag(String noAdmissionFlag) {
		this.noAdmissionFlag = noAdmissionFlag;
	}

	public String getFcFlag() {
		return fcFlag;
	}

	public void setFcFlag(String fcFlag) {
		this.fcFlag = fcFlag;
	}

	public List getAdmissionList() {
		return admissionList;
	}

	public void setAdmissionList(List admissionList) {
		this.admissionList = admissionList;
	}

	public List getNoAdmissionList() {
		return noAdmissionList;
	}

	public void setNoAdmissionList(List noAdmissionList) {
		this.noAdmissionList = noAdmissionList;
	}

	public List getFcList() {
		return fcList;
	}

	public void setFcList(List fcList) {
		this.fcList = fcList;
	}

	public List getAdmParTextList() {
		return admParTextList;
	}

	public void setAdmParTextList(List admParTextList) {
		this.admParTextList = admParTextList;
	}

	public List getParText4125() {
		return parText4125;
	}

	public void setParText4125(List parText4125) {
		this.parText4125 = parText4125;
	}

	public boolean isDatabaseError() {
		return databaseError;
	}

	public void setDatabaseError(boolean databaseError) {
		this.databaseError = databaseError;
	}

}

