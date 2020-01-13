//Created by MyEclipse Struts
// XSL source (default): platform:/plugin/com.genuitec.eclipse.cross.easystruts.eclipse_3.9.210/xslt/JavaClass.xsl

package za.ac.unisa.lms.tools.studentlist.forms;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.ValidatorForm;

/**
 * MyEclipse Struts
 * Creation date: 11-16-2005
 *
 * XDoclet definition:
 * @struts:form name="studentlistform"
 */
public class StudentListForm extends ValidatorForm {

	// --------------------------------------------------------- Instance Variables

	private ArrayList sites;
	private ArrayList students;
	private ArrayList cStudents;
	private ArrayList disabilityList;
	private ArrayList homeLanguages;
	//private ArrayList correspondenceLanguage;
	private ArrayList countries;
	private ArrayList provinces;
	private ArrayList races;
	private ArrayList regions;
	private ArrayList resRegions;
	private ArrayList districts;
	private String[] indexNrOfSelectedSite;
	private static final long serialVersionUID = 1L;

	//holding the list descision for the user.
	private String format = "weblist";
	private String listOutput;
	private String hod;
	private String hodLoggedIn;
	 private String selectView;

	//The novell user id will be placed on the form.
	private String userName;

	//Step 3 of 4: The search criteria. Will be checked for on the jsp as well as in the action when the user wants to write to file
	private boolean studentNumber;
	private boolean homePhoneNumber;
	private boolean title;
	private boolean workPhoneNumber;
	private boolean initials;
	private boolean cellularNumber;
	private boolean firstNames;
	private boolean faxNumber;
	private boolean lastName;
	private boolean emailAddress;
	private boolean postalAddress;
	private boolean gender;
	private boolean postalCode;
	private boolean correspondenceLanguage;
	private boolean homeLanguage;
	private boolean region;
	private boolean resRegion;
	private boolean registrationStatus;
	private boolean moduleRegDate;
	private boolean tutGroupNr;
	
	//Added field for disability
	private boolean disabilityType;

	//Step 4 of 4: Confining the search	

	private String confineStudentList;
	
	private String confineGender;
	private String confineEmailAddress;
	private String confineOnlineUser;
	private String confineHomeLanguage;
	private String confineCorrespondenceLanguage;
	private String confineCountry;
	private String confineProvince;
	private String confineRace;
	private String confineStudentsregion;
	private String confineResidentialRegion;
	private String confineDistrict;
	private String course;
	private int registedStudents;	
	private int completedStudents;
	private boolean noSitesExists;
	private String disability;
	private String displayOption;
	private String groupOption;
	private String confineGroupedStudent;

	// --------------------------------------------------------- Methods
	

	public String getConfineGroupedStudent() {
		return confineGroupedStudent;
	}

	public void setConfineGroupedStudent(String confineGroupedStudent) {
		this.confineGroupedStudent = confineGroupedStudent;
	}

	public String getGroupOption() {
		return groupOption;
	}

	public void setGroupOption(String groupOption) {
		this.groupOption = groupOption;
	}

	public boolean isModuleRegDate() {
		return moduleRegDate;
	}

	public void setModuleRegDate(boolean moduleRegDate) {
		this.moduleRegDate = moduleRegDate;
	}

	public boolean isTutGroupNr() {
		return tutGroupNr;
	}

	public void setTutGroupNr(boolean tutGroupNr) {
		this.tutGroupNr = tutGroupNr;
	}


	public boolean isDisabilityType() {
		return disabilityType;
	}

	public void setDisabilityType(boolean disabilityType) {
		this.disabilityType = disabilityType;
	}

	public String getDisplayOption() {
		return displayOption;
	}

	public void setDisplayOption(String displayOption) {
		this.displayOption = displayOption;
	}

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
	 * Returns the format.
	 * @return String
	 */
	public String getFormat() {
		return format;
	}

	/**
	 * Set the format.
	 * @param format The format to set
	 */
	public void setFormat(String format) {
		this.format = format;
	}

	public String getListOutput() {
		return listOutput;
	}

	public void setListOutput(String listOutput) {
		this.listOutput = listOutput;
	}

	public String getConfineGender() {
		return confineGender;
	}

	public void setConfineGender(String confineGender) {
		this.confineGender = confineGender;
	}

	public String getConfineEmailAddress() {
		return confineEmailAddress;
	}

	public void setConfineEmailAddress(String confineEmailAddress) {
		this.confineEmailAddress = confineEmailAddress;
	}

	public String getConfineOnlineUser() {
		return confineOnlineUser;
	}

	public void setConfineOnlineUser(String confineOnlineUser) {
		this.confineOnlineUser = confineOnlineUser;
	}

	public boolean isCellularNumber() {
		return cellularNumber;
	}

	public void setCellularNumber(boolean cellularNumber) {
		this.cellularNumber = cellularNumber;
	}

	public boolean isCorrespondenceLanguage() {
		return correspondenceLanguage;
	}

	public void setCorrespondenceLanguage(boolean correspondenceLanguage) {
		this.correspondenceLanguage = correspondenceLanguage;
	}

	public boolean isEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(boolean emailAddress) {
		this.emailAddress = emailAddress;
	}

	public boolean isFaxNumber() {
		return faxNumber;
	}

	public void setFaxNumber(boolean faxNumber) {
		this.faxNumber = faxNumber;
	}

	public boolean isFirstNames() {
		return firstNames;
	}

	public void setFirstNames(boolean firstNames) {
		this.firstNames = firstNames;
	}

	public boolean isGender() {
		return gender;
	}

	public void setGender(boolean gender) {
		this.gender = gender;
	}

	public boolean isHomePhoneNumber() {
		return homePhoneNumber;
	}

	public void setHomePhoneNumber(boolean homePhoneNumber) {
		this.homePhoneNumber = homePhoneNumber;
	}

	public boolean isInitials() {
		return initials;
	}

	public void setInitials(boolean initials) {
		this.initials = initials;
	}

	public boolean isLastName() {
		return lastName;
	}

	public void setLastName(boolean lastName) {
		this.lastName = lastName;
	}

	public boolean isPostalAddress() {
		return postalAddress;
	}

	public void setPostalAddress(boolean postalAddress) {
		this.postalAddress = postalAddress;
	}

	public boolean isPostalCode() {
		return postalCode;
	}

	public void setPostalCode(boolean postalCode) {
		this.postalCode = postalCode;
	}

	public boolean isRegion() {
		return region;
	}

	public void setRegion(boolean region) {
		this.region = region;
	}

	public boolean isRegistrationStatus() {
		return registrationStatus;
	}

	public void setRegistrationStatus(boolean registrationStatus) {
		this.registrationStatus = registrationStatus;
	}

	public boolean isStudentNumber() {
		return studentNumber;
	}

	public void setStudentNumber(boolean studentNumber) {
		this.studentNumber = studentNumber;
	}

	public boolean isTitle() {
		return title;
	}

	public void setTitle(boolean title) {
		this.title = title;
	}

	public boolean isWorkPhoneNumber() {
		return workPhoneNumber;
	}

	public void setWorkPhoneNumber(boolean workPhoneNumber) {
		this.workPhoneNumber = workPhoneNumber;
	}

	public ArrayList getSites() {
		return sites;
	}

	public void setSites(ArrayList sites) {
		this.sites = sites;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public ArrayList getStudents() {
		return students;
	}

	public void setStudents(ArrayList students) {
		this.students = students;
	}

	public ArrayList getHomeLanguages() {
		return homeLanguages;
	}

	public void setHomeLanguages(ArrayList homeLanguages) {
		this.homeLanguages = homeLanguages;
	}

	public String getConfineHomeLanguage() {
		return confineHomeLanguage;
	}

	public void setConfineHomeLanguage(String confineHomeLanguage) {
		this.confineHomeLanguage = confineHomeLanguage;
	}

	public ArrayList getCountries() {
		return countries;
	}

	public void setCountries(ArrayList countries) {
		this.countries = countries;
	}



	public ArrayList getProvinces() {
		return provinces;
	}

	public void setProvinces(ArrayList provinces) {
		this.provinces = provinces;
	}

	public ArrayList getRaces() {
		return races;
	}

	public void setRaces(ArrayList races) {
		this.races = races;
	}

	public ArrayList getRegions() {
		return regions;
	}

	public void setRegions(ArrayList regions) {
		this.regions = regions;
	}

	public String getConfineCountry() {
		return confineCountry;
	}

	public void setConfineCountry(String confineCountry) {
		this.confineCountry = confineCountry;
	}

	public String getConfineProvince() {
		return confineProvince;
	}

	public void setConfineProvince(String confineProvince) {
		this.confineProvince = confineProvince;
	}

	public String getConfineRace() {
		return confineRace;
	}

	public void setConfineRace(String confineRace) {
		this.confineRace = confineRace;
	}

	public String getConfineStudentsregion() {
		return confineStudentsregion;
	}

	public void setConfineStudentsregion(String confineStudentsregion) {
		this.confineStudentsregion = confineStudentsregion;
	}

	public String getCourses() {
		return course;
	}

	public void setCourses(String course) {
		this.course = course;
	}

	public String getConfineDistrict() {
		return confineDistrict;
	}

	public void setConfineDistrict(String confineDistrict) {
		this.confineDistrict = confineDistrict;
	}

	public ArrayList getDistricts() {
		return districts;
	}

	public void setDistricts(ArrayList districts) {
		this.districts = districts;
	}

	public String[] getIndexNrOfSelectedSite() {
		return indexNrOfSelectedSite;
	}

	public void setIndexNrOfSelectedSite(String[] indexNrOfSelectedSite) {
		this.indexNrOfSelectedSite = indexNrOfSelectedSite;
	}

	public boolean isNoSitesExists() {
		return noSitesExists;
	}

	public void setNoSitesExists(boolean noSitesExists) {
		this.noSitesExists = noSitesExists;
	}

	public String getConfineResidentialRegion() {
		return confineResidentialRegion;
	}

	public void setConfineResidentialRegion(String confineResidentialRegion) {
		this.confineResidentialRegion = confineResidentialRegion;
	}

	public boolean isResRegion() {
		return resRegion;
	}

	public void setResRegion(boolean resRegion) {
		this.resRegion = resRegion;
	}

	public ArrayList getResRegions() {
		return resRegions;
	}

	public void setResRegions(ArrayList resRegions) {
		this.resRegions = resRegions;
	}

	public String getConfineCorrespondenceLanguage() {
		return confineCorrespondenceLanguage;
	}

	public void setConfineCorrespondenceLanguage(String confineCorrespondenceLanguage) {
		this.confineCorrespondenceLanguage = confineCorrespondenceLanguage;
	}

	public int getRegistedStudents() {
		return registedStudents;
	}

	public void setRegistedStudents(int registedStudents) {
		this.registedStudents = registedStudents;
	}

	public boolean isHomeLanguage() {
		return homeLanguage;
	}

	public void setHomeLanguage(boolean homeLanguage) {
		this.homeLanguage = homeLanguage;
	}

	public String getConfineStudentList() {
		return confineStudentList;
	}

	public void setConfineStudentList(String confineStudentList) {
		this.confineStudentList = confineStudentList;
	}

	public int getCompletedStudents() {
		return completedStudents;
	}

	public void setCompletedStudents(int completedStudents) {
		this.completedStudents = completedStudents;
	}

	public ArrayList getCStudents() {
		return cStudents;
	}

	public void setCStudents(ArrayList students) {
		cStudents = students;
	}

	public String getHod() {
		return hod;
	}

	public void setHod(String hod) {
		this.hod = hod;
	}

	public String getHodLoggedIn() {
		return hodLoggedIn;
	}

	public void setHodLoggedIn(String hodLoggedIn) {
		this.hodLoggedIn = hodLoggedIn;
	}

	public String getSelectView() {
		return selectView;
	}

	public void setSelectView(String selectView) {
		this.selectView = selectView;
	}

	public ArrayList getDisabilityList() {
		return disabilityList;
	}

	public void setDisabilityList(ArrayList disabilityList) {
		this.disabilityList = disabilityList;
	}

	public String getDisability() {
		return disability;
	}

	public void setDisability(String disability) {
		this.disability = disability;
	}

	

}

