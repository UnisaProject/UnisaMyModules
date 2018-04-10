//Created by MyEclipse Struts
// XSL source (default): platform:/plugin/com.genuitec.eclipse.cross.easystruts.eclipse_3.9.210/xslt/JavaClass.xsl

package za.ac.unisa.lms.tools.changepassword.forms;

import org.apache.struts.validator.ValidatorForm;

/** 
 * MyEclipse Struts
 * Creation date: 01-03-2006
 * 
 * XDoclet definition:
 * @struts:form name="changepasswordForm"
 */
public class ChangePasswordForm extends ValidatorForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String oldpassword;
	private String newpassword;
	private String confirmpassword;
	private String mylifePath;
	
	public String getMylifePath() {
		return mylifePath;
	}
	public void setMylifePath(String mylifePath) {
		this.mylifePath = mylifePath;
	}
	/**
	 * @return Returns the confirmpassword.
	 */
	public String getConfirmpassword() {
		return confirmpassword;
	}
	/**
	 * @param confirmpassword The confirmpassword to set.
	 */
	public void setConfirmpassword(String confirmpassword) {
		this.confirmpassword = confirmpassword;
	}
	/**
	 * @return Returns the newpassword.
	 */
	public String getNewpassword() {
		return newpassword;
	}
	/**
	 * @param newpassword The newpassword to set.
	 */
	public void setNewpassword(String newpassword) {
		this.newpassword = newpassword;
	}
	/**
	 * @return Returns the password.
	 */
	public String getOldpassword() {
		return oldpassword;
	}
	/**
	 * @param password The password to set.
	 */
	public void setOldpassword(String oldpassword) {
		this.oldpassword = oldpassword;
	}

}

