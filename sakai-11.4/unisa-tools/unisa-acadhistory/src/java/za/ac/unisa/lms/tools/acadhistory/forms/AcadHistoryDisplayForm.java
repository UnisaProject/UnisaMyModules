//Created by MyEclipse Struts
// XSL source (default): platform:/plugin/com.genuitec.eclipse.cross.easystruts.eclipse_3.9.210/xslt/JavaClass.xsl

package za.ac.unisa.lms.tools.acadhistory.forms;

import java.util.Vector;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.ValidatorForm;

import za.ac.unisa.lms.tools.acadhistory.forms.AcadStudyUnitRecord;

/** 
 * MyEclipse Struts
 * Creation date: 08-18-2005
 * 
 * XDoclet definition:
 * @struts:form name="acadHistoryDisplayForm"
 */
public class AcadHistoryDisplayForm extends ValidatorForm {

	// --------------------------------------------------------- Instance Variables

	/** student property */
	private Student student;
	private Vector qualRecords;
	private boolean studentUser;
	private String selectedQualCode;
	private String selectedQualDesc;
	private String creditsOnly;	// Y or N
	private boolean studentNull;
	private String acadRecRequestButtonState;
	private List<AcadStudyUnitRecord> studyUnitList;

	// --------------------------------------------------------- Methods



	/** 
	 * Method reset
	 * @param mapping
	 * @param request
	 */
	public void reset(ActionMapping mapping, HttpServletRequest request) {

		// TODO Auto-generated method stub
	}

	

	public String getAcadRecRequestButtonState() {
		return acadRecRequestButtonState;
	}

	public void setAcadRecRequestButtonState(String acadRecRequestButtonState) {
		this.acadRecRequestButtonState = acadRecRequestButtonState;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public Vector getQualRecords() {
		return qualRecords;
	}

	public void setQualRecords(Vector qualRecords) {
		this.qualRecords = qualRecords;
	}

	public boolean isStudentUser() {
		return studentUser;
	}

	public void setStudentUser(boolean studentUser) {
		this.studentUser = studentUser;
	}

	public String getSelectedQualCode() {
		return selectedQualCode;
	}

	public void setSelectedQualCode(String selectedQualCode) {
		this.selectedQualCode = selectedQualCode;
	}

	public String getCreditsOnly() {
		return creditsOnly;
	}

	public void setCreditsOnly(String creditsOnly) {
		this.creditsOnly = creditsOnly;
	}

	public boolean isStudentNull() {
		return studentNull;
	}

	public void setStudentNull(boolean studentNull) {
		this.studentNull = studentNull;
	}

	public List<AcadStudyUnitRecord> getStudyUnitList() {
		return studyUnitList;
	}

	public void setStudyUnitList(List<AcadStudyUnitRecord> studyUnitList) {
		this.studyUnitList = studyUnitList;
	}

	public String getSelectedQualDesc() {
		return selectedQualDesc;
	}

	public void setSelectedQualDesc(String selectedQualDesc) {
		this.selectedQualDesc = selectedQualDesc;
	}		

}

