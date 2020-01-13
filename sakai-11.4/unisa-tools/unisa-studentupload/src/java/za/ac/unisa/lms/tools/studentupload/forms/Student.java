//Created by MyEclipse Struts
// XSL source (default): platform:/plugin/com.genuitec.eclipse.cross.easystruts.eclipse_3.9.210/xslt/JavaClass.xsl

package za.ac.unisa.lms.tools.studentupload.forms;

public class Student {

	// --------------------------------------------------------- Instance Variables

	//Login Variables for new Applicant
	private String number;
	private String surname;
	private String firstnames;
	private String birthYear;
	private String birthMonth;
	private String birthDay;
	
	private String stuTestNo;
	private boolean stuAPP = false;
	private boolean stuSLP = false;
	private boolean stuSBL = false;
	private boolean stuHON = false;
	private boolean stuPG = false;
	private boolean stuMD = false;
	private boolean stuNEW = false;
	private boolean stuRET = false;
	private boolean stuapq = false;
	
	private String initials;
	private String maidenName;
	private String title;
	private String gender;

	private String academicYear;
	private String academicPeriod;
	
	private String cellNr;
	private String emailAddress;
	
	private String matrix="";
	
	private String appTime;
	private String webOpenDate;
	private String webOpenADM;
	private String targetFrame;

	private boolean dateWAPSTAT = false;
	
	//Workstation Info
    private String stuIPAddress = "";
    private String stuBrowser = "";
    private String stuBrowserAgent = "";
    private String stuWksOS = "";
    
    private boolean stuExist = false;
    private boolean stuapqExist = false;
    
	private String retQualPrevFinal = "";
	private String retSpecPrevFinal = "";
	
	private String retQualOneFinal = "";
	private String retQualTwoFinal = "";
	private String retSpecOneFinal = "";
	private String retSpecTwoFinal = "";
	
	private String newQualOneFinal = "";
	private String newQualTwoFinal = "";
	private String newSpecOneFinal = "";
	private String newSpecTwoFinal = "";
	
	private String newMDQualFinal;
	private String newMDSpecFinal;
	
	// --------------------------------------------------------- Methods

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
	public String getFirstnames() {
		return firstnames;
	}
	public void setFirstnames(String firstnames) {
		this.firstnames = firstnames;
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
	public String getStuTestNo() {
		return stuTestNo;
	}
	public void setStuTestNo(String stuTestNo) {
		this.stuTestNo = stuTestNo;
	}
	public String getInitials() {
		return initials;
	}
	public void setInitials(String initials) {
		this.initials = initials;
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
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getAcademicYear() {
		return academicYear;
	}
	public void setAcademicYear(String academicYear) {
		this.academicYear = academicYear;
	}
	public String getAcademicPeriod() {
		return academicPeriod;
	}
	public void setAcademicPeriod(String academicPeriod) {
		this.academicPeriod = academicPeriod;
	}
	public String getCellNr() {
		return cellNr;
	}
	public void setCellNr(String cellNr) {
		this.cellNr = cellNr;
	}
	public String getEmailAddress() {
		return emailAddress;
	}
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	public String getMatrix() {
		return matrix;
	}
	public void setMatrix(String matrix) {
		this.matrix = matrix;
	}
	public String getAppTime() {
		return appTime;
	}
	public void setAppTime(String appTime) {
		this.appTime = appTime;
	}
	public String getWebOpenDate() {
		return webOpenDate;
	}
	public void setWebOpenDate(String webOpenDate) {
		this.webOpenDate = webOpenDate;
	}
	public String getWebOpenADM() {
		return webOpenADM;
	}
	public void setWebOpenADM(String webOpenADM) {
		this.webOpenADM = webOpenADM;
	}
	public String getTargetFrame() {
		return targetFrame;
	}
	public void setTargetFrame(String targetFrame) {
		this.targetFrame = targetFrame;
	}
	public boolean isDateWAPSTAT() {
		return dateWAPSTAT;
	}
	public void setDateWAPSTAT(boolean dateWAPSTAT) {
		this.dateWAPSTAT = dateWAPSTAT;
	}
	public String getStuIPAddress() {
		return stuIPAddress;
	}
	public void setStuIPAddress(String stuIPAddress) {
		this.stuIPAddress = stuIPAddress;
	}
	public String getStuBrowser() {
		return stuBrowser;
	}
	public void setStuBrowser(String stuBrowser) {
		this.stuBrowser = stuBrowser;
	}
	public String getStuBrowserAgent() {
		return stuBrowserAgent;
	}
	public void setStuBrowserAgent(String stuBrowserAgent) {
		this.stuBrowserAgent = stuBrowserAgent;
	}
	public String getStuWksOS() {
		return stuWksOS;
	}
	public void setStuWksOS(String stuWksOS) {
		this.stuWksOS = stuWksOS;
	}
	public boolean isStuExist() {
		return stuExist;
	}
	public void setStuExist(boolean stuExist) {
		this.stuExist = stuExist;
	}
	public boolean isStuapqExist() {
		return stuapqExist;
	}
	public void setStuapqExist(boolean stuapqExist) {
		this.stuapqExist = stuapqExist;
	}
	public String getRetQualPrevFinal() throws Exception {
		return retQualPrevFinal;
	}
	public void setRetQualPrevFinal(String retQualPrevFinal) {
		this.retQualPrevFinal = retQualPrevFinal;
	}
	
	public String getRetSpecPrevFinal() throws Exception {
		return retSpecPrevFinal;
	}
	public void setRetSpecPrevFinal(String retSpecPrevFinal) {
		this.retSpecPrevFinal = retSpecPrevFinal;
	}
	
	public String getRetQualOneFinal() throws Exception {
		return retQualOneFinal;
	}
	public void setRetQualOneFinal(String retQualOneFinal) {
		this.retQualOneFinal = retQualOneFinal;
	}

	public String getRetQualTwoFinal() throws Exception {
		return retQualTwoFinal;
	}
	public void setRetQualTwoFinal(String retQualTwoFinal) {
		this.retQualTwoFinal = retQualTwoFinal;
	}

	public String getRetSpecOneFinal() throws Exception {
		return retSpecOneFinal;
	}
	public void setRetSpecOneFinal(String retSpecOneFinal) {
		this.retSpecOneFinal = retSpecOneFinal;
	}

	public String getRetSpecTwoFinal() throws Exception {
		return retSpecTwoFinal;
	}
	public void setRetSpecTwoFinal(String retSpecTwoFinal) {
		this.retSpecTwoFinal = retSpecTwoFinal;
	}
	
	public String getNewQualOneFinal() throws Exception {
		return newQualOneFinal;
	}
	public void setNewQualOneFinal(String newQualOneFinal) {
		this.newQualOneFinal = newQualOneFinal;
	}

	public String getNewQualTwoFinal() throws Exception {
		return newQualTwoFinal;
	}
	public void setNewQualTwoFinal(String newQualTwoFinal) {
		this.newQualTwoFinal = newQualTwoFinal;
	}

	public String getNewSpecOneFinal() throws Exception {
		return newSpecOneFinal;
	}
	public void setNewSpecOneFinal(String newSpecOneFinal) {
		this.newSpecOneFinal = newSpecOneFinal;
	}

	public String getNewSpecTwoFinal() throws Exception {
		return newSpecTwoFinal;
	}
	public void setNewSpecTwoFinal(String newSpecTwoFinal) {
		this.newSpecTwoFinal = newSpecTwoFinal;
	}
	
	public String getNewMDQualFinal() throws Exception {
		return newMDQualFinal;
	}
	public void setNewMDQualFinal(String newMDQualFinal) {
		this.newMDQualFinal = newMDQualFinal;
	}

	public String getNewMDSpecFinal() throws Exception {
		return newMDSpecFinal;
	}
	public void setNewMDSpecFinal(String newMDSpecFinal) {
		this.newMDSpecFinal = newMDSpecFinal;
	}


	public boolean isStuAPP() {
		return stuAPP;
	}
	public void setStuAPP(boolean stuAPP) {
		this.stuAPP = stuAPP;
	}
	public boolean isStuSLP() {
		return stuSLP;
	}

	public void setStuSLP(boolean stuSLP) {
		this.stuSLP = stuSLP;
	}

	public boolean isStuSBL() {
		return stuSBL;
	}
	public void setStuSBL(boolean stuSBL) {
		this.stuSBL = stuSBL;
	}
	public boolean isStuHON() {
		return stuHON;
	}
	public void setStuHON(boolean stuHON) {
		this.stuHON = stuHON;
	}
	public boolean isStuPG() {
		return stuPG;
	}
	public void setStuPG(boolean stuPG) {
		this.stuPG = stuPG;
	}
	public boolean isStuapq() {
		return stuapq;
	}

	public void setStuapq(boolean stuapq) {
		this.stuapq = stuapq;
	}
	public boolean isStuMD() {
		return stuMD;
	}
	public void setStuMD(boolean stuMD) {
		this.stuMD = stuMD;
	}
	public boolean isStuNEW() {
		return stuNEW;
	}
	public void setStuNEW(boolean stuNEW) {
		this.stuNEW = stuNEW;
	}
	public boolean isStuRET() {
		return stuRET;
	}
	public void setStuRET(boolean stuRET) {
		this.stuRET = stuRET;
	}

}