//Created by MyEclipse Struts
// XSL source (default): platform:/plugin/com.genuitec.eclipse.cross.easystruts.eclipse_3.9.210/xslt/JavaClass.xsl

package za.ac.unisa.lms.tools.examtimetable.forms;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;


/** 
 * MyEclipse Struts
 * Creation date: 04-03-2006
 * 
 * XDoclet definition:
 * @struts:form name="examTimetableRecord"
 */
public class ExamTimetableRecord {

	// --------------------------------------------------------- Instance Variables

	/** studyUnit property */
	private String studyUnit;
	private String studyUnitDesc;
	
	/** paperNo property */
	private String paperNo;

	/** examDate property */
	private String examDate;
	
	/** calculator permissible */
	private String calcString;

		// --------------------------------------------------------- Methods

	
	public String getCalcString() {
		return calcString;
	}

	public void setCalcString(String calcString) {
		this.calcString = calcString;
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
	 * Returns the studyUnit.
	 * @return String
	 */
	public String getStudyUnit() {
		return studyUnit;
	}

	/** 
	 * Set the studyUnit.
	 * @param studyUnit The studyUnit to set
	 */
	public void setStudyUnit(String studyUnit) {
		this.studyUnit = studyUnit;
	}

	/** 
	 * Returns the paperNo.
	 * @return short
	 */
	public String getPaperNo() {
		return paperNo;
	}

	/** 
	 * Set the paperNo.
	 * @param paperNo The paperNo to set
	 */
	public void setPaperNo(String paperNo) {
		this.paperNo = paperNo;
	}

	/** 
	 * Returns the examDate.
	 * @return String
	 */
	public String getExamDate() {
		return examDate;
	}

	/** 
	 * Set the examDate.
	 * @param examDate The examDate to set
	 */
	public void setExamDate(String examDate) {
		this.examDate = examDate;
	}

	public String getStudyUnitDesc() {
		return studyUnitDesc;
	}

	public void setStudyUnitDesc(String studyUnitDesc) {
		this.studyUnitDesc = studyUnitDesc;
	}
}

