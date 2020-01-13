//Created by MyEclipse Struts
// XSL source (default): platform:/plugin/com.genuitec.eclipse.cross.easystruts.eclipse_4.1.1/xslt/JavaClass.xsl

package za.ac.unisa.lms.tools.publicexamtimetable.forms;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts.action.ActionForm;

import za.ac.unisa.lms.tools.publicexamtimetable.dao.ExamTimetableDetails;

public class ExamTimeTableForm extends ActionForm {

	private static final long serialVersionUID = 1L;

	private String listDate;
	private String unitCode2;
	private String unitCode4;
	private String unitCode3;
	private String examPeriod;
	private String unitCode1;
	private String unitCode5;
	private String timeTableStatus;
	private String examPeriodDesc;
	private String academicYear;
	private ExamTimetableDetails examTimetableDetails;
	private List timetableList;
	private String printable;
	private List periods;


	public String getPrintable() {
		return printable;
	}

	public void setPrintable(String printable) {
		this.printable = printable;
	}

	public String getListDate() {
		return listDate;
	}

	public void setListDate(String listDate) {
		this.listDate = listDate;
	}

	public String getUnitCode2() {
		return unitCode2;
	}

	public void setUnitCode2(String unitCode2) {
		this.unitCode2 = unitCode2;
	}

	public String getUnitCode4() {
		return unitCode4;
	}

	public void setUnitCode4(String unitCode4) {
		this.unitCode4 = unitCode4;
	}

	public String getUnitCode3() {
		return unitCode3;
	}

	public void setUnitCode3(String unitCode3) {
		this.unitCode3 = unitCode3;
	}

	public String getExamPeriod() {
		return examPeriod;
	}

	public void setExamPeriod(String examPeriod) {
		this.examPeriod = examPeriod;
	}

	public String getUnitCode1() {
		return unitCode1;
	}

	public void setUnitCode1(String unitCode1) {
		this.unitCode1 = unitCode1;
	}

	public String getUnitCode5() {
		return unitCode5;
	}

	public void setUnitCode5(String unitCode5) {
		this.unitCode5 = unitCode5;
	}

	public String getTimeTableStatus() {
		return timeTableStatus;
	}

	public void setTimeTableStatus(String timeTableStatus) {
		this.timeTableStatus = timeTableStatus;
	}

	public String getExamPeriodDesc() {
		return examPeriodDesc;
	}

	public void setExamPeriodDesc(String examPeriodDesc) {
		this.examPeriodDesc = examPeriodDesc;
	}

	public ExamTimetableDetails getExamTimetableDetails() {
		return examTimetableDetails;
	}

	public void setExamTimetableDetails(ExamTimetableDetails examTimetableDetails) {
		this.examTimetableDetails = examTimetableDetails;
	}

	public String getAcademicYear() {
		return academicYear;
	}

	public void setAcademicYear(String academicYear) {
		this.academicYear = academicYear;
	}

	public List getTimetableList() {
		return timetableList;
	}

	public void setTimetableList(List timetableList) {
		this.timetableList = timetableList;
	}

	public List getPeriods() {
		return periods;
	}

	public void setPeriods(List periods) {
		this.periods = periods;
	}

}

