//Created by MyEclipse Struts
// XSL source (default): platform:/plugin/com.genuitec.eclipse.cross.easystruts.eclipse_3.9.210/xslt/JavaClass.xsl

package za.ac.unisa.lms.tools.password.forms;

import org.apache.struts.validator.ValidatorForm;
import org.sakaiproject.component.cover.ServerConfigurationService;

/**
 * MyEclipse Struts
 * Creation date: 12-28-2005
 *
 * XDoclet definition:
 * @struts:form name="forgetPasswordForm"
 */
public class ForgetPasswordForm extends ValidatorForm {

	// --------------------------------------------------------- Instance Variables

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private String email;
	private String studentNr;
	private String rvn;
	private String serverPath;
	private String studentStatus;
	private String selectAccount;
	private String surname;
	private String fullNames;
	private String birthYear;
	private String birthMonth;
	private String birthDay;
	private String id_nr;
	private String passport_nr; 
	private String dateOfBirth;
	private String password;
	private String deliveryOption;
	private String cellNum;
	private String emailButtonStr;
	private String smsButtonStr;
	private int mylifeInitPageTracker=0;
	private String myLifemail;
	private String mylifePath;
	private String selectRequest;
	private String message;
	private String altContactDetails;
	private String confirm;
	private String myLifeActiveStatus;
	private String personalEmail;//jeremia
	private String foreignChoiceDelivery;//jeremia
	private boolean passwordChnaged;
	private boolean isEmail;//jeremia
	private boolean foreign;
	
	
	public boolean isPasswordChnaged() {
		return passwordChnaged;
	}
	public void setPasswordChnaged(boolean passwordChnaged) {
		this.passwordChnaged = passwordChnaged;
	}
	public String getMyLifeActiveStatus() {
		return myLifeActiveStatus;
	}
	public void setMyLifeActiveStatus(String myLifeActiveStatus) {
		this.myLifeActiveStatus = myLifeActiveStatus;
	}
	public String getConfirm() {
		return confirm;
	}
	public void setConfirm(String confirm) {
		this.confirm = confirm;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getAltContactDetails() {
		return altContactDetails;
	}
	public void setAltContactDetails(String altContactDetails) {
		this.altContactDetails = altContactDetails;
	}
	public String getSelectRequest() {
		return selectRequest;
	}
	public void setSelectRequest(String selectRequest) {
		this.selectRequest = selectRequest;
	}
	
	
	public String getMylifePath() {
		return mylifePath;
	}
	public void setMylifePath(String mylifePath) {
		this.mylifePath = mylifePath;
	}
	public String getMyUnisaPath() {
		return myUnisaPath;
	}
	public void setMyUnisaPath(String myUnisaPath) {
		this.myUnisaPath = myUnisaPath;
	}

	private String myUnisaPath;

	public String getMyLifemail() {
		return myLifemail;
	}
	public void setMyLifemail(String myLifemail) {
		this.myLifemail = myLifemail;
	}
	public void seMylifeInitPageTracker(int mylifeInitPageTracker){
		  this.mylifeInitPageTracker=mylifeInitPageTracker;
	}
	public int getMylifeInitPageTracker(){
		return mylifeInitPageTracker;
	}
	public String  getEmailButtonStr(){
			return emailButtonStr;
	}
	public void setCellNum(String cellNum){
		 this.cellNum=cellNum;
	}
	public String getCellNum(){
		return cellNum;
	}
	public void setEmailButtonStr(String email){
		emailButtonStr="(UNISA password will be send to "+email+")"; 
	}
	public String  getSmsButtonStr(){
				return smsButtonStr;
	}
	public void setSmsButtonStr(String cellNum){
		smsButtonStr= "(UNISA password will be send to "+cellNum+")";
	}
	public String getDeliveryOption(){
		return  deliveryOption;
	}
	public void setDeliveryOption(String deliveryOption){
		  this.deliveryOption=deliveryOption;
	}
	public String getPassword(){
		  return password;
	}
	public void setPassword(String password){
		 this.password=password;
	}
	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getId_nr() {
		return id_nr;
	}

	public void setId_nr(String idNr) {
		id_nr = idNr;
	}

	public String getPassport_nr() {
		return passport_nr;
	}

	public void setPassport_nr(String passportNr) {
		passport_nr = passportNr;
	}
	public String getSelectAccount() {
		return selectAccount;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getFullNames() {
		return fullNames;
	}

	public void setFullNames(String fullNames) {
		this.fullNames = fullNames;
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

	public void setSelectAccount(String selectAccount) {
		this.selectAccount = selectAccount;
	}
	/**
	 * Returns the email.
	 * @return String
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Set the email.
	 * @param email The email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Returns the rvn.
	 * @return String
	 */
	public String getRvn() {
		return rvn;
	}

	/**
	 * Set the rvn.
	 * @param rvn The rvn to set
	 */
	public void setRvn(String rvn) {
		this.rvn = rvn;
	}

	/**
	 * Returns the studentNr.
	 * @return String
	 */
	public String getStudentNr() {
		return studentNr;
	}

	/**
	 * Set the studentNr.
	 * @param studentNr The studentNr to set
	 */
	public void setStudentNr(String studentNr) {
		if (!studentNr.equals("")) {
			if (studentNr.substring(0,1).equals("0")) {
				studentNr = studentNr.substring(1);
			}
		}
		this.studentNr = studentNr;
	}

	public String getServerPath() {
		return serverPath;
	}

	public void setServerPath(String serverPath) {
		this.serverPath = serverPath;
	}

	public String getStudentStatus() {
		return studentStatus;
	}

	public void setStudentStatus(String studentStatus) {
		this.studentStatus = studentStatus;
	}
    
	public String getPersonalEmail()
	{
		return personalEmail;//jeremia
	}
	
	public void setPersonalEmail(String personalEmail)
	{
		this.personalEmail = personalEmail;//jeremia
	}
	
	public String getForeignChoiceDelivery()
	{
		return foreignChoiceDelivery;
	}
	 
	public void setForeignChoiceDelivery(String foreignChoiceDelivery)
	{
		this.foreignChoiceDelivery = foreignChoiceDelivery;
	}
	
	public boolean getIsEmail()
	{
		return isEmail;
	}
	
	public void setIsEmail(boolean isEmail)
	{
		this.isEmail = isEmail;
	}
	
	public boolean getIsForeign()
	{
		return foreign;
	}
	
	public void setForeign(boolean foreign)
	{
		this.foreign = foreign;
	}
	
	
}

