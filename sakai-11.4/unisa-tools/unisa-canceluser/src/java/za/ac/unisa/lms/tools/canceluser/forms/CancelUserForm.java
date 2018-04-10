//Created by MyEclipse Struts
// XSL source (default): platform:/plugin/com.genuitec.eclipse.cross.easystruts.eclipse_3.9.210/xslt/JavaClass.xsl

package za.ac.unisa.lms.tools.canceluser.forms;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.ValidatorForm;

/**
 * MyEclipse Struts
 * Creation date: 04-10-2006
 *
 * XDoclet definition:
 * @struts:form name="canceluserForm"
 */
public class CancelUserForm extends ValidatorForm {

	/**
	 * Eclipse Generated.
	 */
	private static final long serialVersionUID = -7875111824636614975L;
	/** studentnumber property */
	private String studentNr;
	private String reasonForCans;
	private String studentName;
	private String joinDate;
	private boolean blockCheck;
	private String blocked;
	private String cancellationOption ;


	public String getBlocked() {
		return blocked;
	}

	public void setBlocked(String blocked) {
		this.blocked = blocked;
	}

	/**
	 * Method reset
	 * @param mapping
	 * @param request
	 */
	public void reset(ActionMapping mapping, HttpServletRequest request) {

		// TODO Auto-generated method stub
		blockCheck=false;
	}

	/**
	 * Returns the studentNr.
	 * @return String
	 */
	public String getStudentNr() {
		return studentNr;
	}

	/**
	 * Set the studentnumber.
	 * @param studentnumber The studentnNr to set
	 */
	public void setStudentNr(String studentNr) {
		this.studentNr = studentNr;
	}

	public String getJoinDate() {
		return joinDate;
	}

	public void setJoinDate(String joinDate) {
		this.joinDate = joinDate;
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public boolean isBlockCheck() {
		return blockCheck;
	}

	public void setBlockCheck(boolean blockCheck) {
		this.blockCheck = blockCheck;
	}

	public String getReasonForCans() {
		return reasonForCans;
	}

	public void setReasonForCans(String reasonForCans) {
		this.reasonForCans = reasonForCans;
	}

	public String getCancellationOption() {
		return cancellationOption;
	}

	public void setCancellationOption(String cancellationOption) {
		this.cancellationOption = cancellationOption;
	}
}

