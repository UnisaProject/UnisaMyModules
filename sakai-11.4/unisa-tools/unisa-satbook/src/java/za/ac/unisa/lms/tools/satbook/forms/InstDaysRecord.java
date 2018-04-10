//Created by MyEclipse Struts
// XSL source (default): platform:/plugin/com.genuitec.eclipse.cross.easystruts.eclipse_3.9.210/xslt/JavaClass.xsl

package za.ac.unisa.lms.tools.satbook.forms;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.ValidatorForm;

/**
 * MyEclipse Struts
 * Creation date: 09-22-2006
 *
 * XDoclet definition:
 * @struts:form name="instDaysRecord"
 */
public class InstDaysRecord extends ValidatorForm {

	// --------------------------------------------------------- Instance Variables

	private String instDate;
	private String instWeekday;
	private String instDay;
	private String instId;
	private String instDesc;
	private boolean instUpdateable;

	// --------------------------------------------------------- Methods

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
	 * Returns the instDate.
	 * @return String
	 */
	public String getInstDate() {
		return instDate;
	}

	/**
	 * Set the instDate.
	 * @param instDate The instDate to set
	 */
	public void setInstDate(String instDate) {
		this.instDate = instDate;
	}

	/**
	 * Returns the instWeekday.
	 * @return String
	 */
	public String getInstWeekday() {
		return instWeekday;
	}

	/**
	 * Set the instWeekday.
	 * @param instWeekday The instWeekday to set
	 */
	public void setInstWeekday(String instWeekday) {
		this.instWeekday = instWeekday;
	}

	/**
	 * Returns the instId.
	 * @return String
	 */
	public String getInstId() {
		return instId;
	}

	/**
	 * Set the instId.
	 * @param instId The instId to set
	 */
	public void setInstId(String instId) {
		this.instId = instId;
	}

	/**
	 * Returns the instDesc.
	 * @return String
	 */
	public String getInstDesc() {
		return instDesc;
	}

	/**
	 * Set the instDesc.
	 * @param instDesc The instDesc to set
	 */
	public void setInstDesc(String instDesc) {
		this.instDesc = instDesc;
	}

	/**
	 * Returns the instDay.
	 * @return String
	 */
	public String getInstDay() {

		instDay = instDate.substring(0,2);
		return instDay;
	}

	/**
	 * Set the instDay.
	 * @param instDay The instDay to set
	 */
	public void setInstDay(String instDay) {
		this.instDay = instDay;
	}


	public boolean getInstUpdateable() {

		return instUpdateable;
	}

	public void setInstUpdateable(boolean instUpdateable) {
		this.instUpdateable = instUpdateable;
	}
}

