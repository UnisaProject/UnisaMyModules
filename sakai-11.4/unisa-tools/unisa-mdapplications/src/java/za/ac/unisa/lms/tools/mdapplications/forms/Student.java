package za.ac.unisa.lms.tools.mdapplications.forms;

import za.ac.unisa.lms.tools.mdapplications.forms.Address;
import za.ac.unisa.lms.tools.mdapplications.forms.GeneralItem;

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
	private String academicYear;
	private String despatchMethod;
	private String registrationMethod;
	private String regdeliveryMethod;
	private String commMethod;
	private String tutorialMethod;
	private String statusCode;
	private String prevPostgrad;
	private String prevUnisapg;
	private String spesCharacter;
	private GeneralItem disability = new GeneralItem();
	private GeneralItem country = new GeneralItem();
	// contact details
	private String homePhone;
	private String workPhone;
	private String cellNr;
	private String faxNr;
	private String contactNr;
	private String emailAddress;
	private boolean emailAddressGood;
	private String addressAudit;
	// Address
	private Address postalAddress = new Address();;
	private Address physicalAddress = new Address();
	private Address courierAddress = new Address();

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
	private GeneralItem prevInstitution = new GeneralItem();
	private GeneralItem prevActivity = new GeneralItem();
	private GeneralItem exam = new GeneralItem();

	private String idType; // R = rsa idnr, F = foreign idnr, P = passport
	private String idNumber;
	private String passportNumber;
	private String foreignIdNumber;
	private String lastPostalAddress;

	// additional for application stored on STUANN
	private String serviceOffice;
	private String tutorialSupport;
	private String orientationFormat;
	private String mediaAccess;
	private String mediaAccess1;
	private String mediaAccess2;
	private String mediaAccess3;
	private String mediaAccess4;
	private String mediaAccess5;
	private String computerTraining;
	private String twinflag;

	// M & D information
	private String moreinfo;
	private String researchTopic;
	private String interestArea;
	private String seqnr = "1";
	private String admstatus;
	private String applYear;
	private String passedndp;
	private String appliedmd;
	private String appliedqual;
	private String lecturer;

	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public String getMaidenName() {
		return maidenName;
	}
	public void setMaidenName(String maidenName) {
		this.maidenName = maidenName;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getFirstnames() {
		return firstnames;
	}
	public void setFirstnames(String firstnames) {
		this.firstnames = firstnames;
	}
	public String getInitials() {
		return initials;
	}
	public void setInitials(String initials) {
		this.initials = initials;
	}
	public String getBirthYear() {
		return birthYear;
	}
	public void setBirthYear(String birthYear) {
		this.birthYear = birthYear;
	}
	public String getBirthMonth() {
		return birthMonth;
	}
	public void setBirthMonth(String birthMonth) {
		this.birthMonth = birthMonth;
	}
	public String getBirthDay() {
		return birthDay;
	}
	public void setBirthDay(String birthDay) {
		this.birthDay = birthDay;
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
	public String getAcademicYear() {
		return academicYear;
	}
	public void setAcademicYear(String academicYear) {
		this.academicYear = academicYear;
	}
	public GeneralItem getDisability() {
		return disability;
	}
	public void setDisability(GeneralItem disability) {
		this.disability = disability;
	}
	public GeneralItem getCountry() {
		return country;
	}
	public void setCountry(GeneralItem country) {
		this.country = country;
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
	public String getCellNr() {
		return cellNr;
	}
	public void setCellNr(String cellNr) {
		this.cellNr = cellNr;
	}
	public String getFaxNr() {
		return faxNr;
	}
	public void setFaxNr(String faxNr) {
		this.faxNr = faxNr;
	}
	public String getContactNr() {
		return contactNr;
	}
	public void setContactNr(String contactNr) {
		this.contactNr = contactNr;
	}
	public String getEmailAddress() {
		return emailAddress;
	}
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	public boolean getEmailAddressGood() {
		return emailAddressGood;
	}
	public void setEmailAddressGood(boolean emailAddressGood) {
		this.emailAddressGood = emailAddressGood;
	}
	public Address getPostalAddress() {
		return postalAddress;
	}
	public void setPostalAddress(Address postalAddress) {
		this.postalAddress = postalAddress;
	}
	public Address getPhysicalAddress() {
		return physicalAddress;
	}
	public void setPhysicalAddress(Address physicalAddress) {
		this.physicalAddress = physicalAddress;
	}
	public Address getCourierAddress() {
		return courierAddress;
	}
	public void setCourierAddress(Address courierAddress) {
		this.courierAddress = courierAddress;
	}
	public String getShareContactDetails() {
		return shareContactDetails;
	}
	public void setShareContactDetails(String shareContactDetails) {
		this.shareContactDetails = shareContactDetails;
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
	public String getLastStatus() {
		return lastStatus;
	}
	public void setLastStatus(String lastStatus) {
		this.lastStatus = lastStatus;
	}
	public String getUnderPostGrad() {
		return underPostGrad;
	}
	public void setUnderPostGrad(String underPostGrad) {
		this.underPostGrad = underPostGrad;
	}
	public GeneralItem getHomeLanguage() {
		return homeLanguage;
	}
	public void setHomeLanguage(GeneralItem homeLanguage) {
		this.homeLanguage = homeLanguage;
	}
	public GeneralItem getNationalty() {
		return nationalty;
	}
	public void setNationalty(GeneralItem nationalty) {
		this.nationalty = nationalty;
	}
	public GeneralItem getPopulationGroup() {
		return populationGroup;
	}
	public void setPopulationGroup(GeneralItem populationGroup) {
		this.populationGroup = populationGroup;
	}
	public GeneralItem getOccupation() {
		return occupation;
	}
	public void setOccupation(GeneralItem occupation) {
		this.occupation = occupation;
	}
	public GeneralItem getEconomicSector() {
		return economicSector;
	}
	public void setEconomicSector(GeneralItem economicSector) {
		this.economicSector = economicSector;
	}
	public GeneralItem getOtherUniversity() {
		return otherUniversity;
	}
	public void setOtherUniversity(GeneralItem otherUniversity) {
		this.otherUniversity = otherUniversity;
	}
	public GeneralItem getPrevInstitution() {
		return prevInstitution;
	}
	public void setPrevInstitution(GeneralItem prevInstitution) {
		this.prevInstitution = prevInstitution;
	}
	public GeneralItem getPrevActivity() {
		return prevActivity;
	}
	public void setPrevActivity(GeneralItem prevActivity) {
		this.prevActivity = prevActivity;
	}
	public GeneralItem getExam() {
		return exam;
	}
	public void setExam(GeneralItem exam) {
		this.exam = exam;
	}
	public String getIdType() {
		return idType;
	}
	public void setIdType(String idType) {
		this.idType = idType;
	}
	public String getIdNumber() {
		return idNumber;
	}
	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}
	public String getPassportNumber() {
		return passportNumber;
	}
	public void setPassportNumber(String passportNumber) {
		this.passportNumber = passportNumber;
	}
	public String getForeignIdNumber() {
		return foreignIdNumber;
	}
	public void setForeignIdNumber(String foreignIdNumber) {
		this.foreignIdNumber = foreignIdNumber;
	}
	public String getLastPostalAddress() {
		return lastPostalAddress;
	}
	public void setLastPostalAddress(String lastPostalAddress) {
		this.lastPostalAddress = lastPostalAddress;
	}
	public String getServiceOffice() {
		return serviceOffice;
	}
	public void setServiceOffice(String serviceOffice) {
		this.serviceOffice = serviceOffice;
	}
	public String getTutorialSupport() {
		return tutorialSupport;
	}
	public void setTutorialSupport(String tutorialSupport) {
		this.tutorialSupport = tutorialSupport;
	}
	public String getOrientationFormat() {
		return orientationFormat;
	}
	public void setOrientationFormat(String orientationFormat) {
		this.orientationFormat = orientationFormat;
	}
	public String getMediaAccess() {
		return mediaAccess;
	}
	public void setMediaAccess(String mediaAccess) {
		this.mediaAccess = mediaAccess;
	}
	public String getMediaAccess1() {
		return mediaAccess1;
	}
	public void setMediaAccess1(String mediaAccess1) {
		this.mediaAccess1 = mediaAccess1;
	}
	public String getMediaAccess2() {
		return mediaAccess2;
	}
	public void setMediaAccess2(String mediaAccess2) {
		this.mediaAccess2 = mediaAccess2;
	}
	public String getMediaAccess3() {
		return mediaAccess3;
	}
	public void setMediaAccess3(String mediaAccess3) {
		this.mediaAccess3 = mediaAccess3;
	}
	public String getMediaAccess4() {
		return mediaAccess4;
	}
	public void setMediaAccess4(String mediaAccess4) {
		this.mediaAccess4 = mediaAccess4;
	}
	public String getMediaAccess5() {
		return mediaAccess5;
	}
	public void setMediaAccess5(String mediaAccess5) {
		this.mediaAccess5 = mediaAccess5;
	}
	public String getComputerTraining() {
		return computerTraining;
	}
	public void setComputerTraining(String computerTraining) {
		this.computerTraining = computerTraining;
	}
	public String getTwinflag() {
		return twinflag;
	}
	public void setTwinflag(String twinflag) {
		this.twinflag = twinflag;
	}
	public String getMoreinfo() {
		return moreinfo;
	}
	public void setMoreinfo(String moreinfo) {
		this.moreinfo = moreinfo;
	}
	public String getDespatchMethod() {
		return despatchMethod;
	}
	public void setDespatchMethod(String despatchMethod) {
		this.despatchMethod = despatchMethod;
	}
	public String getRegistrationMethod() {
		return registrationMethod;
	}
	public void setRegistrationMethod(String registrationMethod) {
		this.registrationMethod = registrationMethod;
	}
	public String getRegdeliveryMethod() {
		return regdeliveryMethod;
	}
	public void setRegdeliveryMethod(String regdeliveryMethod) {
		this.regdeliveryMethod = regdeliveryMethod;
	}
	public String getPrevPostgrad() {
		return prevPostgrad;
	}
	public void setPrevPostgrad(String prevPostgrad) {
		this.prevPostgrad = prevPostgrad;
	}
	public String getSpesCharacter() {
		return spesCharacter;
	}
	public void setSpesCharacter(String spesCharacter) {
		this.spesCharacter = spesCharacter;
	}
	public String getAddressAudit() {
		return addressAudit;
	}
	public void setAddressAudit(String addressAudit) {
		this.addressAudit = addressAudit;
	}
	public String getPrevUnisapg() {
		return prevUnisapg;
	}
	public void setPrevUnisapg(String prevUnisapg) {
		this.prevUnisapg = prevUnisapg;
	}
	public String getCommMethod() {
		return commMethod;
	}
	public void setCommMethod(String commMethod) {
		this.commMethod = commMethod;
	}
	public String getTutorialMethod() {
		return tutorialMethod;
	}
	public void setTutorialMethod(String tutorialMethod) {
		this.tutorialMethod = tutorialMethod;
	}
	public String getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}
	public String getResearchTopic() {
		return researchTopic;
	}
	public void setResearchTopic(String researchTopic) {
		this.researchTopic = researchTopic;
	}
	public String getInterestArea() {
		return interestArea;
	}
	public void setInterestArea(String interestArea) {
		this.interestArea = interestArea;
	}
	public String getSeqnr() {
		return seqnr;
	}
	public void setSeqnr(String seqnr) {
		this.seqnr = seqnr;
	}
	public String getAdmstatus() {
		return admstatus;
	}
	public void setAdmstatus(String admstatus) {
		this.admstatus = admstatus;
	}
	public String getApplYear() {
		return applYear;
	}
	public void setApplYear(String applYear) {
		this.applYear = applYear;
	}
	public String getPassedndp() {
		return passedndp;
	}
	public void setPassedndp(String passedndp) {
		this.passedndp = passedndp;
	}
	public String getAppliedmd() {
		return appliedmd;
	}
	public void setAppliedmd(String appliedmd) {
		this.appliedmd = appliedmd;
	}
	public String getAppliedqual() {
		return appliedqual;
	}
	public void setAppliedqual(String appliedqual) {
		this.appliedqual = appliedqual;
	}
	public String getLecturer() {
		return lecturer;
	}
	public void setLecturer(String lecturer) {
		this.lecturer = lecturer;
	}

}