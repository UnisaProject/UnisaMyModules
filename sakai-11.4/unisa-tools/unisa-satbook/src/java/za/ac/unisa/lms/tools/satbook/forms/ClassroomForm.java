package za.ac.unisa.lms.tools.satbook.forms;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.ValidatorForm;

/**
 * MyEclipse Struts
 * Creation date: 09-14-2006
 *
 * XDoclet definition:
 * @struts:form name="lecturerForm"
 */
public class ClassroomForm extends ValidatorForm {

	// --------------------------------------------------------- Instance Variables
	private String regionCode;
	private String regionDesc;
	private String contactPerson;
	private String contactNum1;
	private String contactNum2;
	private String regionAddress1;
	private String regionAddress2;
	private String regionAddress3;
	private String regionAddress4;
	private String regionAddressPcode;
	private String systemId;
	
	// variables for report per region
	private String month;
	private String numberOfDays;

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getNumberOfDays() {
		return numberOfDays;
	}

	public void setNumberOfDays(String numberOfDays) {
		this.numberOfDays = numberOfDays;
	}

	private boolean regionActive;

	/**
	 * Returns the regionCode.
	 * @return String
	 */
	public String getRegionCode() {
		return regionCode;
	}

	/**
	 * Set the regionCode.
	 * @param regionCode The regionCode to set
	 */
	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
	}

	/**
	 * Returns the regionDesc.
	 * @return String
	 */
	public String getRegionDesc() {
		return regionDesc;
	}

	/**
	 * Set the regionDesc.
	 * @param regionDesc The regionDesc to set
	 */
	public void setRegionDesc(String regionDesc) {
		this.regionDesc = regionDesc;
	}

	/**
	 * Returns the contactPerson.
	 * @return String
	 */
	public String getContactPerson() {
		return contactPerson;
	}

	/**
	 * Set the contactPerson.
	 * @param contactPerson The contactPerson to set
	 */
	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}

	/**
	 * Returns the contactNum1.
	 * @return String
	 */
	public String getContactNum1() {
		return contactNum1;
	}

	/**
	 * Set the contactNum1.
	 * @param contactNum1 The contactNum1 to set
	 */
	public void setContactNum1(String contactNum1) {
		this.contactNum1 = contactNum1;
	}

	/**
	 * Returns the contactNum2.
	 * @return String
	 */
	public String getContactNum2() {
		return contactNum2;
	}

	/**
	 * Set the contactNum2.
	 * @param contactNum2 The contactNum2 to set
	 */
	public void setContactNum2(String contactNum2) {
		this.contactNum2 = contactNum2;
	}

	/**
	 * Returns the regionAddress1.
	 * @return String
	 */
	public String getRegionAddress1() {
		return regionAddress1;
	}

	/**
	 * Set the regionAddress1.
	 * @param regionAddress1 The regionAddress1 to set
	 */
	public void setRegionAddress1(String regionAddress1) {
		this.regionAddress1 = regionAddress1;
	}

	/**
	 * Returns the regionAddress2.
	 * @return String
	 */
	public String getRegionAddress2() {
		return regionAddress2;
	}

	/**
	 * Set the regionAddress2.
	 * @param regionAddress2 The regionAddress2 to set
	 */
	public void setRegionAddress2(String regionAddress2) {
		this.regionAddress2 = regionAddress2;
	}

	/**
	 * Returns the regionAddress3.
	 * @return String
	 */
	public String getRegionAddress3() {
		return regionAddress3;
	}

	/**
	 * Set the regionAddress3.
	 * @param regionAddress3 The regionAddress3 to set
	 */
	public void setRegionAddress3(String regionAddress3) {
		this.regionAddress3 = regionAddress3;
	}

	/**
	 * Returns the regionAddress4.
	 * @return String
	 */
	public String getRegionAddress4() {
		return regionAddress4;
	}

	/**
	 * Set the regionAddress4.
	 * @param regionAddress4 The regionAddress4 to set
	 */
	public void setRegionAddress4(String regionAddress4) {
		this.regionAddress4 = regionAddress4;
	}

	/**
	 * Returns the regionAddressPcode.
	 * @return String
	 */
	public String getRegionAddressPcode() {
		return regionAddressPcode;
	}

	/**
	 * Set the regionAddressPcode.
	 * @param regionAddressPcode The regionAddressPcode to set
	 */
	public void setRegionAddressPcode(String regionAddressPcode) {
		this.regionAddressPcode = regionAddressPcode;
	}

	/**
	 * Returns the regionActive.
	 * @return boolean
	 */
	public boolean getRegionActive() {
		return regionActive;
	}

	/**
	 * Set the regionActive.
	 * @param regionActive The regionActive to set
	 */
	public void setRegionActive(boolean regionActive) {
		this.regionActive = regionActive;
	}

	public String getSystemId() {
		return systemId;
	}

	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}

}
