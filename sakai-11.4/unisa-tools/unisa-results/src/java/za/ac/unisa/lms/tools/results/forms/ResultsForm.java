/* $Header: /data/cvs/javaweb/src/za/ac/unisa/struts/forms/results/ResultsForm.java,v 1.4 2005/02/09 07:15:34 udcsweb3 Exp $ */

package za.ac.unisa.lms.tools.results.forms;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.ValidatorForm;

import za.ac.unisa.lms.tools.results.dao.Xamprd;
import java.util.List;

/**
 * MyEclipse Struts
 * Creation date: 09-17-2004
 *
 * XDoclet definition:
 * @struts:form name="results"
 */
public class ResultsForm extends ValidatorForm {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * @return Returns the examPeriod.
	 */
	public Short getExamPeriod() {
		return examPeriod;
	}
	/**
	 * @param examPeriod The examPeriod to set.
	 */
	public void setExamPeriod(Short examPeriod) {
		this.examPeriod = examPeriod;
	}
	// --------------------------------------------------------- Instance Variables

	/** examYear property */
	private String examYear;
	/** studentNr property */
	private String studentNr;
	private Short examPeriod;
	private Xamprd xamprd;
	private boolean studentUser = true;
	private String textLine1;
	private String textLine2;
	private String textLine3;
	private String textLine4;
	private String textLine5;
	private boolean lecturerUser = false;
	private boolean staffOnlineUser = false;
	private String currentPage;
	private List listResults;
	private List exampPeriods;
	private String selectCourse;
	private String queryDate;
	private CourseResult selectResult;
		

	public List getExampPeriods(){
		return exampPeriods;
	}
	public void setExampPeriods(List exampPeriods){
		this.exampPeriods=exampPeriods;
	}
	public CourseResult getSelectResult() {
		return selectResult;
	}
	public void setSelectResult(CourseResult selectResult) {
		this.selectResult = selectResult;
	}
	public List getListResults() {
		return listResults;
	}
	public void setListResults(List listResults) {
		this.listResults = listResults;
	}
	public boolean isStudentUser() {
		return studentUser;
	}
	public void setStudentUser(boolean studentUser) {
		this.studentUser = studentUser;
	}
	/**
	 * @return Returns the xamprd.
	 */
	public Xamprd getXamprd() {
		return xamprd;
	}
	/**
	 * @param xamprd The xamprd to set.
	 */
	public void setXamprd(Xamprd xamprd) {
		this.xamprd = xamprd;
	}
	// --------------------------------------------------------- Methods

	/**
	 * Method reset
	 * @param mapping
	 * @param request
	 */
	public void reset(ActionMapping mapping, HttpServletRequest request) {
	}

	/**
	 * Returns the examYear.
	 * @return String
	 */
	public String getExamYear() {
		return examYear;
	}

	/**
	 * Set the examYear.
	 * @param examYear The examYear to set
	 */
	public void setExamYear(String examYear) {
		this.examYear = examYear;
	}

	/**
	 * Returns the studentNr.
	 * @return String
	 */
	public String getStudentNr() {
		return studentNr;
	}

	/**
	 * Set the studentNr.
	 * @param studentNr The studentNr to set
	 */
	public void setStudentNr(String studentNr) {
		this.studentNr = studentNr;
	}
	public String getTextLine1() {
		return textLine1;
	}
	public void setTextLine1(String textLine1) {
		this.textLine1 = textLine1;
	}
	public String getTextLine2() {
		return textLine2;
	}
	public void setTextLine2(String textLine2) {
		this.textLine2 = textLine2;
	}
	public String getTextLine3() {
		return textLine3;
	}
	public void setTextLine3(String textLine3) {
		this.textLine3 = textLine3;
	}
	public String getTextLine4() {
		return textLine4;
	}
	public void setTextLine4(String textLine4) {
		this.textLine4 = textLine4;
	}
	public String getTextLine5() {
		return textLine5;
	}
	public void setTextLine5(String textLine5) {
		this.textLine5 = textLine5;
	}
	public boolean isLecturerUser() {
		return lecturerUser;
	}
	public void setLecturerUser(boolean lecturerUser) {
		this.lecturerUser = lecturerUser;
	}
	public String getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(String currentPage) {
		this.currentPage = currentPage;
	}
	public String getSelectCourse() {
		return selectCourse;
	}
	public void setSelectCourse(String selectCourse) {
		this.selectCourse = selectCourse;
	}
	public boolean isStaffOnlineUser() {
		return staffOnlineUser;
	}
	public void setStaffOnlineUser(boolean staffOnlineUser) {
		this.staffOnlineUser = staffOnlineUser;
	}
	public String getQueryDate() {
		return queryDate;
	}
	public void setQueryDate(String queryDate) {
		this.queryDate = queryDate;
	}

}