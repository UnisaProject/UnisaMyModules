//Created by MyEclipse Struts
// XSL source (default): platform:/plugin/com.genuitec.eclipse.cross.easystruts.eclipse_3.9.210/xslt/JavaClass.xsl

package za.ac.unisa.lms.tools.acadhistory.forms;


import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

/** 
 * MyEclipse Struts
 * Creation date: 08-18-2005
 * 
 * XDoclet definition:
 * @struts:form name="acadQualRecord"
 */
public class AcadQualRecord {

	// --------------------------------------------------------- Instance Variables

	/** firstRegistrationDate property */
	private String firstRegistrationDate;

	/** qualShortDescription property */
	private String qualShortDescription;

	/** status property */
	private String status;

	/** registrationYear property */
	private short lastRegistrationYear;

	/** qualCode property */
	private String qualCode;
	
	private String graduationCeremonyDate;
	
	private String auditFlag;

	// --------------------------------------------------------- Methods

	/** 
	 * Method reset
	 * @param mapping
	 * @param request
	 */
	public void reset(ActionMapping mapping, HttpServletRequest request) {

		// TODO Auto-generated method stub
	}

	/** 
	 * Returns the firstRegistrationDate.
	 * @return String
	 */
	public String getFirstRegistrationDate() {
		return firstRegistrationDate;
	}

	/** 
	 * Set the firstRegistrationDate.
	 * @param firstRegistrationDate The firstRegistrationDate to set
	 */
	public void setFirstRegistrationDate(String firstRegistrationDate) {
		this.firstRegistrationDate = firstRegistrationDate;
	}

	/** 
	 * Returns the qualShortDescription.
	 * @return String
	 */
	public String getQualShortDescription() {
		return qualShortDescription;
	}

	/** 
	 * Set the qualShortDescription.
	 * @param qualShortDescription The qualShortDescription to set
	 */
	public void setQualShortDescription(String qualShortDescription) {
		this.qualShortDescription = qualShortDescription;
	}

	/** 
	 * Returns the status.
	 * @return String
	 */
	public String getStatus() {
		return status;
	}

	/** 
	 * Set the status.
	 * @param status The status to set
	 */
	public void setStatus(String status) {
		//GAAN lees gencod en gencat
			this.status= status;
	}

	/** 
	 * Returns the registrationYear.
	 * @return String
	 */
	public short getLastRegistrationYear() {
		return lastRegistrationYear;
	}

	/** 
	 * Set the registrationYear.
	 * @param registrationYear The registrationYear to set
	 */
	public void setLastRegistrationYear(short lastRegistrationYear) {
		this.lastRegistrationYear = lastRegistrationYear;
	}

	/** 
	 * Returns the qualCode.
	 * @return String
	 */
	public String getQualCode() {
		return qualCode;
	}

	/** 
	 * Set the qualCode.
	 * @param qualCode The qualCode to set
	 */
	public void setQualCode(String qualCode) {
		this.qualCode = qualCode;
	}

	public String getGraduationCeremonyDate() {
		return graduationCeremonyDate;
	}

	public void setGraduationCeremonyDate(String graduationCeremonyDate) {
		this.graduationCeremonyDate = graduationCeremonyDate;
	}

	public String getAuditFlag() {
		return auditFlag;
	}

	public void setAuditFlag(String auditFlag) {
		this.auditFlag = auditFlag;
	}
	
	

}

