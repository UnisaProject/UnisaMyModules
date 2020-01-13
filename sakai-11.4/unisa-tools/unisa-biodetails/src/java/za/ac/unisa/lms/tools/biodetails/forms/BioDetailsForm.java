
package za.ac.unisa.lms.tools.biodetails.forms;

import java.util.Vector;

import org.apache.struts.validator.ValidatorActionForm;


/** 
 * MyEclipse Struts
 * Creation date: 09-14-2005
 * 
 * XDoclet definition:
 * @struts:form name="BioDetailsForm"
 */
@SuppressWarnings("rawtypes")
public class BioDetailsForm extends ValidatorActionForm {

	// --------------------------------------------------------- Instance Variables

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String version = "2018001";
	
	/** personal details */
	private String number;
	private String title;
	private String firstNames;
	private String initials;
	private String correspondLang;
	private String disability;
	private String ethnicGroup;
	private String prevSurname;
	private String citySearch;
	private String examCentre;
	private String examCentreCode;
	private String countryCode;
	private String addressCountryCode;
	private String countryDescription;
	private String gender;
	private String nationality;
	private String birthDate;
	private String regRegion;
	private String homeLang;
	private String surname;
	private String identityNr;
	private String foreign;
	private String addressForeign;
	private boolean emailUpdatableFromWeb = false;// student have myLife email address
		
	/* Qualification */
	
	private Qualification qual;
	
	/** contact details */
	private String cellNr;
	private String oldCellNr;
	private String workNr;
	private String faxNr;
	private String oldFaxNr;
	private String contactNr;
	private String advisorContactNr;
	private String oldWorkNr;
	private String homeNr;
	private String oldHomeNr;
	private String email;
	private String oldEmail;
	private String nonMyLifeEmail;
	private String emailStatus;

	/** address */
	private Vector postal;
	private Vector physical;
	private Vector courier;
	private String postalType;
	private String postalAddressType;
	private String postalCode;
	private String postalStreetCode;
	private String physicalPostalCode;
	private String courierPostalCode;
	private String postalSuburb;
	private String postalStreetSuburb;
	private String physicalSuburb;
	private String courierSuburb;
	private String physicalTown;
	private String courierTown;
	private String addressType;
	private String postalLine1;
	private String postalLine2;
	private String postalLine3;
	private String postalLine4;
	private String postalLine5;
	private String postalLine6;
	private String postalStreetLine1;
	private String postalStreetLine2;
	private String postalStreetLine3;
	private String postalStreetLine4;
	private String postalStreetLine5;
	private String postalStreetLine6;
	private String advisorPostalLine1;
	private String advisorPostalLine2;
	private String advisorPostalLine3;
	private String advisorPostalLine4;
	private String advisorPostalLine5;
	private String advisorPostalLine6;	
	private String physicalLine1;
	private String physicalLine2;
	private String physicalLine3;
	private String physicalLine4;
	private String physicalLine5;
	private String physicalLine6;
	private String advisorPhysicalLine1;
	private String advisorPhysicalLine2;
	private String advisorPhysicalLine3;
	private String advisorPhysicalLine4;
	private String advisorPhysicalLine5;
	private String advisorPhysicalLine6;
	private String courierLine1;
	private String courierLine2;
	private String courierLine3;
	private String courierLine4;
	private String courierLine5;
	private String courierLine6;
	private String advisorCourierLine1;
	private String advisorCourierLine2;
	private String advisorCourierLine3;
	private String advisorCourierLine4;
	private String advisorCourierLine5;
	private String advisorCourierLine6;	
	private String searchType;
	private String searchSuburb;
	private String postalCodeSearch;
	private String searchPostalCode;
	private String advisorPostalCode;
	private String advisorPhysicalPostalCode;
	private String advisorCourierPostalCode;
	private String advisorPostalSuburb;
	private String advisorPhysicalSuburb;
	private String advisorCourierSuburb;
	private String searchResult;
	private String adrYear;
	private String adrMonth;
	private String adrDay;
	private String disclaimer;
	
	/** exam centre */
	private String examCity;
	private String selectedExamCentre;
	
	/** sms options */
	private String regOption;
	private String assignOption;
	private String blockExamResults;
	
	/** Stores the tool id that this current too is called from*/
	private String originatedFrom;
	
	/** Validating Courier Address **/
	private boolean checkCourier = false;
	private boolean courierAddressValid = false;
	private boolean physicalAddressValid = false;
	
     
	public String getVersion() {
		return version;
	}
	
	public void setNonMyLifeEmail(String nonMyLifeEmail){
		this.nonMyLifeEmail=nonMyLifeEmail;
	}
	public String getNonMyLifeEmail(){
		return nonMyLifeEmail;
	}
	public String getSelectedExamCentre() {
		return selectedExamCentre;
	}

	public void setSelectedExamCentre(String selectedExamCentre) {
		this.selectedExamCentre = selectedExamCentre;
	}
	public String getPostalLine1() {
		return postalLine1;
	}

	public void setPostalLine1(String postalLine1) {
		this.postalLine1 = postalLine1;
	}

	public String getPostalLine2() {
		return postalLine2;
	}

	public void setPostalLine2(String postalLine2) {
		this.postalLine2 = postalLine2;
	}

	public String getPostalLine3() {
		return postalLine3;
	}

	public void setPostalLine3(String postalLine3) {
		this.postalLine3 = postalLine3;
	}
	public String getPostalLine4() {
		return postalLine4;
	}

	public void setPostalLine4(String postalLine4) {
		this.postalLine4 = postalLine4;
	}

	public String getPostalLine5() {
		return postalLine5;
	}

	public void setPostalLine5(String postalLine5) {
		this.postalLine5 = postalLine5;
	}

	public String getPostalLine6() {
		return postalLine6;
	}

	public void setPostalLine6(String postalLine6) {
		this.postalLine6 = postalLine6;
	}
	public String getPostalStreetLine1() {
		return postalStreetLine1;
	}

	public void setPostalStreetLine1(String postalStreetLine1) {
		this.postalStreetLine1 = postalStreetLine1;
	}

	public String getPostalStreetLine2() {
		return postalStreetLine2;
	}

	public void setPostalStreetLine2(String postalStreetLine2) {
		this.postalStreetLine2 = postalStreetLine2;
	}

	public String getPostalStreetLine3() {
		return postalStreetLine3;
	}

	public void setPostalStreetLine3(String postalStreetLine3) {
		this.postalStreetLine3 = postalStreetLine3;
	}
	public String getPostalStreetLine4() {
		return postalStreetLine4;
	}

	public void setPostalStreetLine4(String postalStreetLine4) {
		this.postalStreetLine4 = postalStreetLine4;
	}

	public String getPostalStreetLine5() {
		return postalStreetLine5;
	}

	public void setPostalStreetLine5(String postalStreetLine5) {
		this.postalStreetLine5 = postalStreetLine5;
	}

	public String getPostalStreetLine6() {
		return postalStreetLine6;
	}

	public void setPostalStreetLine6(String postalStreetLine6) {
		this.postalStreetLine6 = postalStreetLine6;
	}
	public String getSearchPostalCode() {
		return searchPostalCode;
	}

	public void setSearchPostalCode(String searchPostalCode) {
		this.searchPostalCode = searchPostalCode;
	}

	public String getSearchSuburb() {
		return searchSuburb;
	}

	public void setSearchSuburb(String searchSuburb) {
		this.searchSuburb = searchSuburb;
	}

	public String getSearchType() {
		return searchType;
	}

	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}

	public String getBirthDate() {
		return birthDate;
	}
	
	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
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

	public String getCorrespondLang() {
		return correspondLang;
	}

	public void setCorrespondLang(String correspondLang) {
		this.correspondLang = correspondLang;
	}

	public String getDisability() {
		return disability;
	}

	public void setDisability(String disability) {
		this.disability = disability;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getExamCentre() {
		return examCentre;
	}

	public void setExamCentre(String examCentre) {
		this.examCentre = examCentre;
	}

	public String getFaxNr() {
		return faxNr;
	}

	public void setFaxNr(String faxNr) {
		this.faxNr = faxNr;
	}

	public String getFirstNames() {
		return firstNames;
	}

	public void setFirstNames(String firstNames) {
		this.firstNames = firstNames;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getHomeLang() {
		return homeLang;
	}

	public void setHomeLang(String homeLang) {
		this.homeLang = homeLang;
	}

	public String getHomeNr() {
		return homeNr;
	}

	public void setHomeNr(String homeNr) {
		this.homeNr = homeNr;
	}

	public String getIdentityNr() {
		return identityNr;
	}

	public void setIdentityNr(String identityNr) {
		this.identityNr = identityNr;
	}

	public String getInitials() {
		return initials;
	}

	public void setInitials(String initials) {
		this.initials = initials;
	}

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public Vector getPhysical() {
		return physical;
	}

	public void setPhysical(Vector physical) {
		this.physical = physical;
	}
	
	public Vector getPostal() {
		return postal;
	}

	public void setPostal(Vector postal) {
		this.postal = postal;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getPostalStreetCode() {
		return postalStreetCode;
	}

	public void setPostalStreetCode(String postalStreetCode) {
		this.postalStreetCode = postalStreetCode;
	}

	public String getPrevSurname() {
		return prevSurname;
	}

	public void setPrevSurname(String prevSurname) {
		this.prevSurname = prevSurname;
	}

	public String getEthnicGroup() {
		return ethnicGroup;
	}

	public void setEthnicGroup(String ethnicGroup) {
		this.ethnicGroup = ethnicGroup;
	}

	public String getRegRegion() {
		return regRegion;
	}

	public void setRegRegion(String regRegion) {
		this.regRegion = regRegion;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getWorkNr() {
		return workNr;
	}

	public void setWorkNr(String workNr) {
		this.workNr = workNr;
	}

	public String getOldFaxNr() {
		return oldFaxNr;
	}

	public void setOldFaxNr(String oldFaxNr) {
		this.oldFaxNr = oldFaxNr;
	}

	public String getOldWorkNr() {
		return oldWorkNr;
	}

	public void setOldWorkNr(String oldWorkNr) {
		this.oldWorkNr = oldWorkNr;
	}

	public String getOldHomeNr() {
		return oldHomeNr;
	}

	public void setOldHomeNr(String oldHomeNr) {
		this.oldHomeNr = oldHomeNr;
	}

	public String getOldEmail() {
		return oldEmail;
	}

	public void setOldEmail(String oldEmail) {
		this.oldEmail = oldEmail;
	}

	public String getOldCellNr() {
		return oldCellNr;
	}

	public void setOldCellNr(String oldCellNr) {
		this.oldCellNr = oldCellNr;
	}

	public String getPostalType() {
		return postalType;
	}

	public void setPostalType(String postalType) {
		this.postalType = postalType;
	}

	public String getPostalAddressType() {
		return postalAddressType;
	}

	public void setPostalAddressType(String postalAddressType) {
		this.postalAddressType = postalAddressType;
	}
	
	public String getPostalSuburb() {
		return postalSuburb;
	}

	public void setPostalSuburb(String postalSuburb) {
		this.postalSuburb = postalSuburb;
	}
	public String getPostalStreetSuburb() {
		return postalStreetSuburb;
	}

	public void setPostalStreetSuburb(String postalStreetSuburb) {
		this.postalStreetSuburb = postalStreetSuburb;
	}

	public String getPhysicalSuburb() {
		return physicalSuburb;
	}

	public void setPhysicalSuburb(String physicalSuburb) {
		this.physicalSuburb = physicalSuburb;
	}
	public String getCourierSuburb() {
		return courierSuburb;
	}

	public void setCourierSuburb(String courierSuburb) {
		this.courierSuburb = courierSuburb;
	}
	public String getPhysicalTown() {
		return physicalTown;
	}

	public void setPhysicalTown(String physicalTown) {
		this.physicalTown = physicalTown;
	}
	public String getCourierTown() {
		return courierTown;
	}

	public void setCourierTown(String courierTown) {
		this.courierTown = courierTown;
	}
	public String getAddressType() {
		return addressType;
	}

	public void setAddressType(String addressType) {
		this.addressType = addressType;
	}

	public String getCitySearch() {
		return citySearch;
	}

	public void setCitySearch(String citySearch) {
		this.citySearch = citySearch;
	}

	public String getPostalCodeSearch() {
		return postalCodeSearch;
	}

	public void setPostalCodeSearch(String postalCodeSearch) {
		this.postalCodeSearch = postalCodeSearch;
	}

	public String getAdvisorPostalCode() {
		return advisorPostalCode;
	}

	public void setAdvisorPostalCode(String advisorPostalCode) {
		this.advisorPostalCode = advisorPostalCode;
	}

	public String getSearchResult() {
		return searchResult;
	}

	public void setSearchResult(String searchResult) {
		this.searchResult = searchResult;
	}

	public String getAdvisorPostalSuburb() {
		return advisorPostalSuburb;
	}

	public void setAdvisorPostalSuburb(String advisorPostalSuburb) {
		this.advisorPostalSuburb = advisorPostalSuburb;
	}

	public String getAdvisorPhysicalSuburb() {
		return advisorPhysicalSuburb;
	}

	public void setAdvisorPhysicalSuburb(String advisorPhysicalSuburb) {
		this.advisorPhysicalSuburb = advisorPhysicalSuburb;
	}
	
	public String getAdvisorCourierSuburb() {
		return advisorCourierSuburb;
	}

	public void setAdvisorCourierSuburb(String advisorCourierSuburb) {
		this.advisorCourierSuburb = advisorCourierSuburb;
	}
	
	public String getExamCentreCode() {
		return examCentreCode;
	}

	public void setExamCentreCode(String examCentreCode) {
		this.examCentreCode = examCentreCode;
	}

	public String getExamCity() {
		return examCity;
	}

	public void setExamCity(String examCity) {
		this.examCity = examCity;
	}

	public String getAssignOption() {
		return assignOption;
	}

	public void setAssignOption(String assignOption) {
		this.assignOption = assignOption;
	}

	public String getRegOption() {
		return regOption;
	}

	public void setRegOption(String regOption) {
		this.regOption = regOption;
	}

	public String getAdrDay() {
		return adrDay;
	}

	public void setAdrDay(String adrDay) {
		this.adrDay = adrDay;
	}

	public String getAdrMonth() {
		return adrMonth;
	}

	public void setAdrMonth(String adrMonth) {
		this.adrMonth = adrMonth;
	}

	public String getAdrYear() {
		return adrYear;
	}

	public void setAdrYear(String adrYear) {
		this.adrYear = adrYear;
	}

	public String getAdvisorPostalLine1() {
		return advisorPostalLine1;
	}

	public void setAdvisorPostalLine1(String advisorPostalLine1) {
		this.advisorPostalLine1 = advisorPostalLine1;
	}

	public String getAdvisorPostalLine2() {
		return advisorPostalLine2;
	}

	public void setAdvisorPostalLine2(String advisorPostalLine2) {
		this.advisorPostalLine2 = advisorPostalLine2;
	}

	public String getAdvisorPostalLine3() {
		return advisorPostalLine3;
	}

	public void setAdvisorPostalLine3(String advisorPostalLine3) {
		this.advisorPostalLine3 = advisorPostalLine3;
	}

	public String getAdvisorPostalLine4() {
		return advisorPostalLine4;
	}

	public void setAdvisorPostalLine4(String advisorPostalLine4) {
		this.advisorPostalLine4 = advisorPostalLine4;
	}

	public String getAdvisorPostalLine5() {
		return advisorPostalLine5;
	}

	public void setAdvisorPostalLine5(String advisorPostalLine5) {
		this.advisorPostalLine5 = advisorPostalLine5;
	}

	public String getAdvisorPostalLine6() {
		return advisorPostalLine6;
	}

	public void setAdvisorPostalLine6(String advisorPostalLine6) {
		this.advisorPostalLine6 = advisorPostalLine6;
	}
	public String getPhysicalLine1() {
		return physicalLine1;
	}

	public void setPhysicalLine1(String physicalLine1) {
		this.physicalLine1 = physicalLine1;
	}

	public String getPhysicalLine2() {
		return physicalLine2;
	}

	public void setPhysicalLine2(String physicalLine2) {
		this.physicalLine2 = physicalLine2;
	}

	public String getPhysicalLine3() {
		return physicalLine3;
	}

	public void setPhysicalLine3(String physicalLine3) {
		this.physicalLine3 = physicalLine3;
	}
	public String getPhysicalLine4() {
		return physicalLine4;
	}

	public void setPhysicalLine4(String physicalLine4) {
		this.physicalLine4 = physicalLine4;
	}

	public String getPhysicalLine5() {
		return physicalLine5;
	}

	public void setPhysicalLine5(String physicalLine5) {
		this.physicalLine5 = physicalLine5;
	}

	public String getPhysicalLine6() {
		return physicalLine6;
	}

	public void setPhysicalLine6(String physicalLine6) {
		this.physicalLine6 = physicalLine6;
	}

	public String getAdvisorPhysicalLine1() {
		return advisorPhysicalLine1;
	}

	public void setAdvisorPhysicalLine1(String advisorPhysicalLine1) {
		this.advisorPhysicalLine1 = advisorPhysicalLine1;
	}

	public String getAdvisorPhysicalLine2() {
		return advisorPhysicalLine2;
	}

	public void setAdvisorPhysicalLine2(String advisorPhysicalLine2) {
		this.advisorPhysicalLine2 = advisorPhysicalLine2;
	}

	public String getAdvisorPhysicalLine3() {
		return advisorPhysicalLine3;
	}

	public void setAdvisorPhysicalLine3(String advisorPhysicalLine3) {
		this.advisorPhysicalLine3 = advisorPhysicalLine3;
	}

	public String getAdvisorPhysicalLine4() {
		return advisorPhysicalLine4;
	}

	public void setAdvisorPhysicalLine4(String advisorPhysicalLine4) {
		this.advisorPhysicalLine4 = advisorPhysicalLine4;
	}

	public String getAdvisorPhysicalLine5() {
		return advisorPhysicalLine5;
	}

	public void setAdvisorPhysicalLine5(String advisorPhysicalLine5) {
		this.advisorPhysicalLine5 = advisorPhysicalLine5;
	}

	public String getAdvisorPhysicalLine6() {
		return advisorPhysicalLine6;
	}

	public void setAdvisorPhysicalLine6(String advisorPhysicalLine6) {
		this.advisorPhysicalLine6 = advisorPhysicalLine6;
	}

	public String getAdvisorContactNr() {
		return advisorContactNr;
	}

	public void setAdvisorContactNr(String advisorContactNr) {
		this.advisorContactNr = advisorContactNr;
	}

	public String getEmailStatus() {
		return emailStatus;
	}

	public void setEmailStatus(String emailStatus) {
		this.emailStatus = emailStatus;
	}

	public String getDisclaimer() {
		return disclaimer;
	}

	public void setDisclaimer(String disclaimer) {
		this.disclaimer = disclaimer;
	}

	public String getForeign() {
		return foreign;
	}

	public void setForeign(String foreign) {
		this.foreign = foreign;
	}

	public Qualification getQual() {
		return qual;
	}

	public void setQual(Qualification qual) {
		this.qual = qual;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getCountryDescription() {
		return countryDescription;
	}

	public void setCountryDescription(String countryDescription) {
		this.countryDescription = countryDescription;
	}

	public boolean isEmailUpdatableFromWeb() {
		return emailUpdatableFromWeb;
	}

	public void setEmailUpdatableFromWeb(boolean emailUpdatableFromWeb) {
		this.emailUpdatableFromWeb = emailUpdatableFromWeb;
	}

	public String getPhysicalPostalCode() {
		return physicalPostalCode;
	}

	public void setPhysicalPostalCode(String physicalPostalCode) {
		this.physicalPostalCode = physicalPostalCode;
	}

	public String getCourierPostalCode() {
		return courierPostalCode;
	}

	public void setCourierPostalCode(String courierPostalCode) {
		this.courierPostalCode = courierPostalCode;
	}

	public Vector getCourier() {
		return courier;
	}

	public void setCourier(Vector courier) {
		this.courier = courier;
	}
	public String getCourierLine1() {
		return courierLine1;
	}

	public void setCourierLine1(String courierLine1) {
		this.courierLine1 = courierLine1;
	}

	public String getCourierLine2() {
		return courierLine2;
	}

	public void setCourierLine2(String courierLine2) {
		this.courierLine2 = courierLine2;
	}

	public String getCourierLine3() {
		return courierLine3;
	}

	public void setCourierLine3(String courierLine3) {
		this.courierLine3 = courierLine3;
	}
	public String getCourierLine4() {
		return courierLine4;
	}

	public void setCourierLine4(String courierLine4) {
		this.courierLine4 = courierLine4;
	}

	public String getCourierLine5() {
		return courierLine5;
	}

	public void setCourierLine5(String courierLine5) {
		this.courierLine5 = courierLine5;
	}

	public String getCourierLine6() {
		return courierLine6;
	}

	public void setCourierLine6(String courierLine6) {
		this.courierLine6 = courierLine6;
	}
	public String getAdvisorCourierLine1() {
		return advisorCourierLine1;
	}

	public void setAdvisorCourierLine1(String advisorCourierLine1) {
		this.advisorCourierLine1 = advisorCourierLine1;
	}

	public String getAdvisorCourierLine2() {
		return advisorCourierLine2;
	}

	public void setAdvisorCourierLine2(String advisorCourierLine2) {
		this.advisorCourierLine2 = advisorCourierLine2;
	}

	public String getAdvisorCourierLine3() {
		return advisorCourierLine3;
	}

	public void setAdvisorCourierLine3(String advisorCourierLine3) {
		this.advisorCourierLine3 = advisorCourierLine3;
	}

	public String getAdvisorCourierLine4() {
		return advisorCourierLine4;
	}

	public void setAdvisorCourierLine4(String advisorCourierLine4) {
		this.advisorCourierLine4 = advisorCourierLine4;
	}

	public String getAdvisorCourierLine5() {
		return advisorCourierLine5;
	}

	public void setAdvisorCourierLine5(String advisorCourierLine5) {
		this.advisorCourierLine5 = advisorCourierLine5;
	}

	public String getAdvisorCourierLine6() {
		return advisorCourierLine6;
	}

	public void setAdvisorCourierLine6(String advisorCourierLine6) {
		this.advisorCourierLine6 = advisorCourierLine6;
	}

	public String getAdvisorPhysicalPostalCode() {
		return advisorPhysicalPostalCode;
	}

	public void setAdvisorPhysicalPostalCode(String advisorPhysicalPostalCode) {
		this.advisorPhysicalPostalCode = advisorPhysicalPostalCode;
	}

	public String getAdvisorCourierPostalCode() {
		return advisorCourierPostalCode;
	}

	public void setAdvisorCourierPostalCode(String advisorCourierPostalCode) {
		this.advisorCourierPostalCode = advisorCourierPostalCode;
	}

	public String getBlockExamResults() {
		return blockExamResults;
	}

	public void setBlockExamResults(String blockExamResults) {
		this.blockExamResults = blockExamResults;
	}

	public String getOriginatedFrom() {
		return originatedFrom;
	}

	public void setOriginatedFrom(String originatedFrom) {
		this.originatedFrom = originatedFrom;
	}

	public boolean isCheckCourier() {
		return checkCourier;
	}

	public void setCheckCourier(boolean checkCourier) {
		this.checkCourier = checkCourier;
	}
	
	public boolean isCourierValid() {
		return courierAddressValid;
	}

	public void setCourierValid(boolean courierAddressValid) {
		this.courierAddressValid = courierAddressValid;
		
	}
	public boolean isPhysicalValid() {
		return physicalAddressValid;
	}

	public void setPhysicalValid(boolean physicalAddressValid) {
		this.physicalAddressValid = physicalAddressValid;
		
	}

	public String getAddressCountryCode() {
		return addressCountryCode;
	}

	public void setAddressCountryCode(String addressCountryCode) {
		this.addressCountryCode = addressCountryCode;
	}


	public String getAddressForeign() {
		return addressForeign;
	}

	public void setAddressForeign(String addressForeign) {
		this.addressForeign = addressForeign;
	}
	
}

