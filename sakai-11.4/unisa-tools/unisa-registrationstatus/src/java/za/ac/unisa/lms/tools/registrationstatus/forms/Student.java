//Created by MyEclipse Struts
// XSL source (default): platform:/plugin/com.genuitec.eclipse.cross.easystruts.eclipse_3.9.210/xslt/JavaClass.xsl

package za.ac.unisa.lms.tools.registrationstatus.forms;

public class Student {

	// --------------------------------------------------------- Instance Variables

	private String number;
	private String surname;
	private String maidenName;
	private String title;
	private String firstnames;
	private String initials;
	private String birthYear;
	private String birthMonth;
	private String birthDay;
	private String gender;
	private String language;
	private GeneralItem disability = new GeneralItem();
	private GeneralItem country = new GeneralItem();
	// contact details
	private String homePhone;
	private String workPhone;
	private String cellNr;
	private String faxNr;
	private String contactNr;
	private String emailAddress;

	//other details
	private String shareContactDetails;
	private String lastInstitution;
	private String lastRegYear;
	private String lastStatus;
	private String underPostGrad; // U = undergrad, P = postgrad
	private GeneralItem homeLanguage = new GeneralItem();
	private GeneralItem nationalty = new GeneralItem();
	private GeneralItem populationGroup = new GeneralItem();
	private GeneralItem occupation = new GeneralItem();
	private GeneralItem economicSector = new GeneralItem();
	private GeneralItem otherUniversity = new GeneralItem();

	private String idType; // R = rsa idnr, F = foreign idnr, P = passport
	private String idNumber;
	private String passportNumber;
	private String foreignIdNumber;

	// used in search for student nr on walkthrough
	private String lastPostalAddress;

	// --------------------------------------------------------- Methods


	/**
	 * Returns the studentNumber.
	 * @return String
	 */
	public String getNumber() {
		return number;
	}

	/**
	 * Set the number.
	 * @param number The number to set
	 */
	public void setNumber(String number) {
		this.number = number;
	}

	/**
	 * Returns the studentTitle.
	 * @return String
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Set the title.
	 * @param title The title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	public String getFirstnames() {
		return firstnames;
	}

	public void setFirstnames(String firstnames) {
		this.firstnames = firstnames;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	/**
	 * Returns the initials.
	 * @return String
	 */
	public String getInitials() {
		return initials;
	}

	/**
	 * Set the initials.
	 * @param initials The initials to set
	 */
	public void setInitials(String initials) {
		this.initials = initials;
	}

	public String getBirthDay() {
		return birthDay;
	}

	public void setBirthDay(String birthDay) {
		this.birthDay = birthDay;
	}

	public String getBirthMonth() {
		return birthMonth;
	}

	public void setBirthMonth(String birthMonth) {
		this.birthMonth = birthMonth;
	}

	public String getBirthYear() {
		return birthYear;
	}

	public void setBirthYear(String birthYear) {
		this.birthYear = birthYear;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	public String getLastPostalAddress() {
		return lastPostalAddress;
	}

	public void setLastPostalAddress(String lastPostalAddress) {
		this.lastPostalAddress = lastPostalAddress;
	}

	public String getPassportNumber() {
		return passportNumber;
	}

	public void setPassportNumber(String passportNumber) {
		this.passportNumber = passportNumber;
	}

	public String getMaidenName() {
		return maidenName;
	}

	public void setMaidenName(String maidenName) {
		this.maidenName = maidenName;
	}

	public String getForeignIdNumber() {
		return foreignIdNumber;
	}

	public void setForeignIdNumber(String foreignIdNumber) {
		this.foreignIdNumber = foreignIdNumber;
	}

	public String getIdType() {
		return idType;
	}

	public void setIdType(String idType) {
		this.idType = idType;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public GeneralItem getDisability() {
		return disability;
	}

	public void setDisability(GeneralItem disability) {
		this.disability = disability;
	}

	public String getCellNr() {
		return cellNr;
	}

	public void setCellNr(String cellNr) {
		this.cellNr = cellNr;
	}

	public String getContactNr() {
		return contactNr;
	}

	public void setContactNr(String contactNr) {
		this.contactNr = contactNr;
	}

	public GeneralItem getCountry() {
		return country;
	}

	public String getFaxNr() {
		return faxNr;
	}

	public void setFaxNr(String faxNr) {
		this.faxNr = faxNr;
	}

	public String getHomePhone() {
		return homePhone;
	}

	public void setHomePhone(String homePhone) {
		this.homePhone = homePhone;
	}

	public String getWorkPhone() {
		return workPhone;
	}

	public void setWorkPhone(String workPhone) {
		this.workPhone = workPhone;
	}

	public void setCountry(GeneralItem country) {
		this.country = country;
	}

	public GeneralItem getEconomicSector() {
		return economicSector;
	}

	public void setEconomicSector(GeneralItem economicSector) {
		this.economicSector = economicSector;
	}

	public GeneralItem getHomeLanguage() {
		return homeLanguage;
	}

	public void setHomeLanguage(GeneralItem homeLanguage) {
		this.homeLanguage = homeLanguage;
	}

	public String getLastInstitution() {
		return lastInstitution;
	}

	public void setLastInstitution(String lastInstitution) {
		this.lastInstitution = lastInstitution;
	}

	public String getLastRegYear() {
		return lastRegYear;
	}

	public void setLastRegYear(String lastRegYear) {
		this.lastRegYear = lastRegYear;
	}

	public GeneralItem getNationalty() {
		return nationalty;
	}

	public void setNationalty(GeneralItem nationalty) {
		this.nationalty = nationalty;
	}

	public GeneralItem getOccupation() {
		return occupation;
	}

	public void setOccupation(GeneralItem occupation) {
		this.occupation = occupation;
	}

	public GeneralItem getOtherUniversity() {
		return otherUniversity;
	}

	public void setOtherUniversity(GeneralItem otherUniversity) {
		this.otherUniversity = otherUniversity;
	}

	public GeneralItem getPopulationGroup() {
		return populationGroup;
	}

	public void setPopulationGroup(GeneralItem populationGroup) {
		this.populationGroup = populationGroup;
	}

	public String getShareContactDetails() {
		return shareContactDetails;
	}

	public void setShareContactDetails(String shareContactDetails) {
		this.shareContactDetails = shareContactDetails;
	}

	public String getUnderPostGrad() {
		return underPostGrad;
	}

	public void setUnderPostGrad(String underPostGrad) {
		this.underPostGrad = underPostGrad;
	}

	public String getLastStatus() {
		return lastStatus;
	}

	public void setLastStatus(String lastStatus) {
		this.lastStatus = lastStatus;
	}

	public String toString() {
		String result = "";

		StringBuffer student = new StringBuffer();
		student.append(getNumber()+",");
		student.append(getSurname()+ ",");
		student.append(getFirstnames()+ ",");
		student.append(getBirthYear()+ "-");
		student.append(getBirthMonth()+ "-");
		student.append(getBirthDay()+ ",");

		student.toString();

		return result;

	}

}

