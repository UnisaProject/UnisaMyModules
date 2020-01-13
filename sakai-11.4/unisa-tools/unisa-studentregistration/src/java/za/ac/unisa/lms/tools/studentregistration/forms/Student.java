//Created by MyEclipse Struts
// XSL source (default): platform:/plugin/com.genuitec.eclipse.cross.easystruts.eclipse_3.9.210/xslt/JavaClass.xsl

package za.ac.unisa.lms.tools.studentregistration.forms;

import za.ac.unisa.lms.tools.studentregistration.dao.ApplyForStudentNumberQueryDAO;

public class Student {

	// --------------------------------------------------------- Instance Variables

	//Login Variables for new Applicant
	private String number;
	private String numberTmp;
	private String surname;
	private String firstnames;
	private String birthYear;
	private String birthMonth;
	private String birthDay;
	
	private String stuTestNo;
	private boolean stuSLP = false;
	private boolean stuapq = false;
	
	private String initials;
	private String maidenName;
	private String title;
	private String gender;
	private String language;
	private String academicYear;
	private String academicPeriod;
	private GeneralItem disability = new GeneralItem();
	private GeneralItem country = new GeneralItem();
	// contact details
	private String homePhone;
	private String workPhone;
	private String faxNr;
	private String contactNr;
	
	private String cellNr;
	private String cellNr2;
	private String emailAddress;
	private String emailAddress2;
	private boolean emailAddressGood;
	
	// Address
	private Address postalAddress = new Address();;
	private Address physicalAddress = new Address();
	private Address courierAddress = new Address();
	// Exam details
	private Exam exam = new Exam();
	//private GeneralItem examCentre = new GeneralItem();

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

	private String idType; // R = rsa idnr, F = foreign idnr, P = passport
	private String idNumber;
	private String passportNumber;
	private String foreignIdNumber;

	// used in search for student number on walkthrough
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
	
	private String category1;
	private String qual1;
	private String spec1;
	private String category2;
	private String qual2;
	private String spec2;
	
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
	
	private String qualUnderpost;
	private String qual1Underpost;
	private String qual2Underpost;
	
	private String stuExist;
	private boolean stuapqExist;
	private String matrix;
	private String nextStep;
	private String apptime;
	
	// M & D information
	private String moreinfo;
	private String researchtopic;
	
	private String webOpenDate;
	private String webOpenADM;
	private String targetFrame;

	//Open/Closed Categories and Dates
	private boolean dateWAPU = false;
	private boolean dateWAPRU = false;
	private boolean dateWAPH = false;
	private boolean dateWAPRH = false;
	private boolean dateWAPS = false;
	private boolean dateWAPM = false;
	private boolean dateWAPD = false;
	
	//Open/Closed ADMIN
	private boolean dateWAPADMU = false;
	private boolean dateWAPADMH = false;
	private boolean dateWAPADMS= false;
	private boolean dateWAPADMD = false;
	private boolean dateWAPADMM = false;
	private boolean dateWAPADMNEW = false;
	private boolean dateWAPADMRET = false;
	
	private boolean dateWAPDOC = false;
	private boolean dateWAPPAY = false;
	private boolean dateWAPSTAT = false;
	private boolean dateWAPAPL = false;
	private boolean dateWAPOFFER = false;
	private boolean dateWAPOFFICE = false;
	
	//Workstation Info
    private String stuIPAddress = "";
    private String stuBrowser = "";
    private String stuBrowserAgent = "";
    private String stuWksOS = "";
	
    //M&D Process
	// M & D information
	private String researchTopic;
	private String interestArea;
	private String seqnr = "0";
	private String admstatus;
	private String applYear;
	private String passedndp;
	private String appliedmd;
	private String appliedqual;
	private String lecturer;


	// --------------------------------------------------------- Methods

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

	public String getNumberTmp() {
		return numberTmp;
	}

	public void setNumberTmp(String numberTmp) {
		this.numberTmp = numberTmp;
	}

	/**
	 * Returns the studentNumber as a backup to verify before saving data.
	 * @return String
	 */
	public String getNumberCheck() {
		return number;
	}

	/**
	 * Set the number.
	 * @param number The number to set
	 */
	public void setNumberCheck(String number) {
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
		firstnames = firstnames.replaceAll("'","`");
		this.firstnames = firstnames;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		surname = surname.replaceAll("'","`");
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

	public String getCellNr2() {
		return cellNr2;
	}
	public void setCellNr2(String cellNr2) {
		this.cellNr2 = cellNr2;
	}

	public String getEmailAddress() {
		return emailAddress;
	}
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getEmailAddress2() {
		return emailAddress2;
	}
	public void setEmailAddress2(String emailAddress2) {
		this.emailAddress2 = emailAddress2;
	}

	public boolean getEmailAddressGood() {
		return emailAddressGood;
	}
	public void setEmailAddressGood(boolean emailAddressGood) {
		this.emailAddressGood = emailAddressGood;
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

	public Address getPostalAddress() {
		return postalAddress;
	}

	public void setPostalAddress(Address postalAddress) {
		this.postalAddress = postalAddress;
	}

	public String getWorkPhone() {
		return workPhone;
	}

	public void setWorkPhone(String workPhone) {
		this.workPhone = workPhone;
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
	
	public Exam getExam() {
		return exam;
	}

	public void setExam(Exam exam) {
		this.exam = exam;
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

	public String getMediaAccess() {
		return mediaAccess;
	}

	public void setMediaAccess(String mediaAccess) {
		this.mediaAccess = mediaAccess;
	}

	public String getOrientationFormat() {
		return orientationFormat;
	}

	public void setOrientationFormat(String orientationFormat) {
		this.orientationFormat = orientationFormat;
	}

	public GeneralItem getPrevInstitution() {
		return prevInstitution;
	}

	public void setPrevInstitution(GeneralItem prevInstitution) {
		this.prevInstitution = prevInstitution;
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

	public String getMoreinfo() {
		return moreinfo;
	}

	public void setMoreinfo(String moreinfo) {
		this.moreinfo = moreinfo;
	}

	public String getResearchtopic() {
		return researchtopic;
	}

	public void setResearchtopic(String researchtopic) {
		this.researchtopic = researchtopic;
	}

	public GeneralItem getPrevActivity() {
		return prevActivity;
	}

	public void setPrevActivity(GeneralItem prevActivity) {
		this.prevActivity = prevActivity;
	}
	public String getTwinflag() {
		return twinflag;
	}

	public void setTwinflag(String twinflag) {
		this.twinflag = twinflag;
	}

	public String getCategory1() {
		return category1;
	}
	public void setCategory1(String category1) {
		this.category1 = category1;
	}
	public String getQual1() {
		return qual1;
	}
	public void setQual1(String qual1) {
		this.qual1 = qual1;
	}
	public String getSpec1() {
		return spec1;
	}
	public void setSpec1(String spec1) {
		this.spec1 = spec1;
	}
	public String getCategory2() {
		return category2;
	}
	public void setCategory2(String category2) {
		this.category2 = category2;
	}
	public String getQual2() {
		return qual2;
	}
	public void setQual2(String qual2) {
		this.qual2 = qual2;
	}
	public String getSpec2() {
		return spec2;
	}
	public  void setSpec2(String spec2) {
		this.spec2 = spec2;
	}
	
	public String getQualUnderpost() {
		return qualUnderpost;
	}
	public void setQualUnderpost(String qualUnderpost) {
		this.qualUnderpost = qualUnderpost;
	}
	public String getQual1Underpost() {
		return qual1Underpost;
	}
	public void setQual1Underpost(String qual1Underpost) {
		this.qual1Underpost = qual1Underpost;
	}
	public String getQual2Underpost() {
		return qual2Underpost;
	}
	public void setQual2Underpost(String qual2Underpost) {
		this.qual2Underpost = qual2Underpost;
	}
	public String getStuExist() {
		return stuExist;
	}
	public void setStuExist(String stuExist) {
		this.stuExist = stuExist;
	}
	public boolean getStuapqExist() {
		return stuapqExist;
	}
	public void setStuapqExist(boolean stuapqExist) {
		this.stuapqExist = stuapqExist;
	}

	public String getNextStep() {
		return nextStep;
	}
	public void setNextStep(String nextStep) {
		this.nextStep = nextStep;
	}
	public String getAppTime() {
		return apptime;
	}
	public void setAppTime(String apptime) {
		this.apptime = apptime;
	}
	public String getMatrix() {
		return matrix;
	}
	public void setMatrix(String matrix) {
		this.matrix = matrix;
	}

	public String getRetQualPrevFinal() throws Exception {
		ApplyForStudentNumberQueryDAO dao = new ApplyForStudentNumberQueryDAO();
		try{
			retQualPrevFinal = dao.vrfyPrevQual("Qual","1",number,academicYear,academicPeriod);
		} catch (Exception ex) {
			throw new Exception(
					"Student - ApplyForNewStudentNumberDAO : Error getting vrfyPrevQual / " + ex);
		}
		return retQualPrevFinal;
	}
	public void setRetQualPrevFinal(String retQualPrevFinal) {
		this.retQualPrevFinal = retQualPrevFinal;
	}
	
	public String getRetSpecPrevFinal() throws Exception {
		ApplyForStudentNumberQueryDAO dao = new ApplyForStudentNumberQueryDAO();
		try{
			retSpecPrevFinal = dao.vrfyPrevQual("Spec","1",number,academicYear,academicPeriod);
		} catch (Exception ex) {
			throw new Exception(
					"Student - ApplyForNewStudentNumberDAO : Error getting vrfyPrevQual / " + ex);
		}
		return retSpecPrevFinal;
	}
	public void setRetSpecPrevFinal(String retSpecPrevFinal) {
		this.retSpecPrevFinal = retSpecPrevFinal;
	}
	
	public String getRetQualOneFinal() throws Exception {
		ApplyForStudentNumberQueryDAO dao = new ApplyForStudentNumberQueryDAO();
		try{
			retQualOneFinal = dao.vrfyNewQual("Qual","1",number,academicYear,academicPeriod);
		} catch (Exception ex) {
			throw new Exception(
					"Student - ApplyForNewStudentNumberDAO : Error getting vrfyNewQual / " + ex);
		}
		return retQualOneFinal;
	}
	public void setRetQualOneFinal(String retQualOneFinal) {
		this.retQualOneFinal = retQualOneFinal;
	}

	public String getRetQualTwoFinal() throws Exception {
		ApplyForStudentNumberQueryDAO dao = new ApplyForStudentNumberQueryDAO();
		try{
			retQualTwoFinal = dao.vrfyNewQual("Qual","2",number,academicYear,academicPeriod);
		} catch (Exception ex) {
			throw new Exception(
					"Student - ApplyForNewStudentNumberDAO : Error getting vrfyNewQual / " + ex);
		}

		return retQualTwoFinal;
	}
	public void setRetQualTwoFinal(String retQualTwoFinal) {
		this.retQualTwoFinal = retQualTwoFinal;
	}

	public String getRetSpecOneFinal() throws Exception {
		ApplyForStudentNumberQueryDAO dao = new ApplyForStudentNumberQueryDAO();
		try{
			retSpecOneFinal = dao.vrfyNewQual("Spec","1",number,academicYear,academicPeriod);
		} catch (Exception ex) {
			throw new Exception(
					"Student - ApplyForNewStudentNumberDAO : Error getting vrfyPrevQual / " + ex);
		}
		return retSpecOneFinal;
	}
	public void setRetSpecOneFinal(String retSpecOneFinal) {
		this.retSpecOneFinal = retSpecOneFinal;
	}

	public String getRetSpecTwoFinal() throws Exception {
		ApplyForStudentNumberQueryDAO dao = new ApplyForStudentNumberQueryDAO();
		try{
			retSpecTwoFinal = dao.vrfyNewQual("Spec","2",number,academicYear,academicPeriod);
		} catch (Exception ex) {
			throw new Exception(
					"Student - ApplyForNewStudentNumberDAO : Error getting vrfyNewQual / " + ex);
		}

		return retSpecTwoFinal;
	}
	public void setRetSpecTwoFinal(String retSpecTwoFinal) {
		this.retSpecTwoFinal = retSpecTwoFinal;
	}
	
	public String getNewQualOneFinal() throws Exception {
		ApplyForStudentNumberQueryDAO dao = new ApplyForStudentNumberQueryDAO();
		try{
			newQualOneFinal = dao.vrfyNewQual("Qual","1",number,academicYear,academicPeriod);
		} catch (Exception ex) {
			throw new Exception(
					"Student - ApplyForNewStudentNumberDAO : Error getting vrfyNewQual / " + ex);
		}
		return newQualOneFinal;
	}
	public void setNewQualOneFinal(String newQualOneFinal) {
		this.newQualOneFinal = newQualOneFinal;
	}

	public String getNewQualTwoFinal() throws Exception {
		ApplyForStudentNumberQueryDAO dao = new ApplyForStudentNumberQueryDAO();
		try{
			newQualTwoFinal = dao.vrfyNewQual("Qual","2",number,academicYear,academicPeriod);
		} catch (Exception ex) {
			throw new Exception(
					"Student - ApplyForNewStudentNumberDAO : Error getting vrfyNewQual / " + ex);
		}

		return newQualTwoFinal;
	}
	public void setNewQualTwoFinal(String newQualTwoFinal) {
		this.newQualTwoFinal = newQualTwoFinal;
	}

	public String getNewSpecOneFinal() throws Exception {
		ApplyForStudentNumberQueryDAO dao = new ApplyForStudentNumberQueryDAO();
		try{
			newSpecOneFinal = dao.vrfyNewQual("Spec","1",number,academicYear,academicPeriod);
		} catch (Exception ex) {
			throw new Exception(
					"Student - ApplyForNewStudentNumberDAO : Error getting vrfyNewQual / " + ex);
		}
		return newSpecOneFinal;
	}
	public void setNewSpecOneFinal(String newSpecOneFinal) {
		this.newSpecOneFinal = newSpecOneFinal;
	}

	public String getNewSpecTwoFinal() throws Exception {
		ApplyForStudentNumberQueryDAO dao = new ApplyForStudentNumberQueryDAO();
		try{
			newSpecTwoFinal = dao.vrfyNewQual("Spec","2",number,academicYear,academicPeriod);
		} catch (Exception ex) {
			throw new Exception(
					"Student - ApplyForNewStudentNumberDAO : Error getting vrfyNewQual / " + ex);
		}

		return newSpecTwoFinal;
	}
	public void setNewSpecTwoFinal(String newSpecTwoFinal) {
		this.newSpecTwoFinal = newSpecTwoFinal;
	}
	
	public String getNewMDQualFinal() throws Exception {
		ApplyForStudentNumberQueryDAO dao = new ApplyForStudentNumberQueryDAO();
		try{
			newMDQualFinal = dao.vrfyNewMDQual("Qual",number,academicYear,academicPeriod);
		} catch (Exception ex) {
			throw new Exception(
					"Student - ApplyForNewStudentNumberDAO : Error getting vrfyNewMDQual / " + ex);
		}
		return newMDQualFinal;
	}
	public void setNewMDQualFinal(String newMDQualFinal) {
		this.newMDQualFinal = newMDQualFinal;
	}

	public String getNewMDSpecFinal() throws Exception {
		ApplyForStudentNumberQueryDAO dao = new ApplyForStudentNumberQueryDAO();
		try{
			newMDSpecFinal = dao.vrfyNewMDQual("Spec",number,academicYear,academicPeriod);
		} catch (Exception ex) {
			throw new Exception(
					"Student - ApplyForNewStudentNumberDAO : Error getting vrfyNewMDSpec / " + ex);
		}
		return newMDSpecFinal;
	}
	public void setNewMDSpecFinal(String newMDSpecFinal) {
		this.newMDSpecFinal = newMDSpecFinal;
	}

	public String getTargetFrame() {
		return targetFrame;
	}

	public void setTargetFrame(String targetFrame) {
		this.targetFrame = targetFrame;
	}

	public String getStuTestNo() {
		return stuTestNo;
	}

	public void setStuTestNo(String stuTestNo) {
		this.stuTestNo = stuTestNo;
	}

	public boolean isStuSLP() {
		return stuSLP;
	}

	public void setStuSLP(boolean stuSLP) {
		this.stuSLP = stuSLP;
	}

	public boolean isStuapq() {
		return stuapq;
	}

	public void setStuapq(boolean stuapq) {
		this.stuapq = stuapq;
	}

	public boolean isDateWAPU() {
		return dateWAPU;
	}

	public void setDateWAPU(boolean dateWAPU) {
		this.dateWAPU = dateWAPU;
	}

	public boolean isDateWAPRU() {
		return dateWAPRU;
	}

	public void setDateWAPRU(boolean dateWAPRU) {
		this.dateWAPRU = dateWAPRU;
	}

	public boolean isDateWAPH() {
		return dateWAPH;
	}

	public void setDateWAPH(boolean dateWAPH) {
		this.dateWAPH = dateWAPH;
	}

	public boolean isDateWAPRH() {
		return dateWAPRH;
	}

	public void setDateWAPRH(boolean dateWAPRH) {
		this.dateWAPRH = dateWAPRH;
	}

	public boolean isDateWAPS() {
		return dateWAPS;
	}

	public void setDateWAPS(boolean dateWAPS) {
		this.dateWAPS = dateWAPS;
	}

	public boolean isDateWAPADMS() {
		return dateWAPADMS;
	}

	public void setDateWAPADMS(boolean dateWAPADMS) {
		this.dateWAPADMS = dateWAPADMS;
	}

	public boolean isDateWAPM() {
		return dateWAPM;
	}

	public void setDateWAPM(boolean dateWAPM) {
		this.dateWAPM = dateWAPM;
	}

	public boolean isDateWAPD() {
		return dateWAPD;
	}

	public void setDateWAPD(boolean dateWAPD) {
		this.dateWAPD = dateWAPD;
	}

	public boolean isDateWAPADMU() {
		return dateWAPADMU;
	}

	public void setDateWAPADMU(boolean dateWAPADMU) {
		this.dateWAPADMU = dateWAPADMU;
	}

	public boolean isDateWAPADMH() {
		return dateWAPADMH;
	}

	public void setDateWAPADMH(boolean dateWAPADMH) {
		this.dateWAPADMH = dateWAPADMH;
	}

	public boolean isDateWAPADMD() {
		return dateWAPADMD;
	}

	public void setDateWAPADMD(boolean dateWAPADMD) {
		this.dateWAPADMD = dateWAPADMD;
	}

	public boolean isDateWAPADMM() {
		return dateWAPADMM;
	}

	public void setDateWAPADMM(boolean dateWAPADMM) {
		this.dateWAPADMM = dateWAPADMM;
	}

	public boolean isDateWAPADMNEW() {
		return dateWAPADMNEW;
	}

	public void setDateWAPADMNEW(boolean dateWAPADMNEW) {
		this.dateWAPADMNEW = dateWAPADMNEW;
	}

	public boolean isDateWAPADMRET() {
		return dateWAPADMRET;
	}

	public void setDateWAPADMRET(boolean dateWAPADMRET) {
		this.dateWAPADMRET = dateWAPADMRET;
	}	
	
   //Add Workstation IP and Browser for Logging
    
    public String getApptime() {
		return apptime;
	}

	public void setApptime(String apptime) {
		this.apptime = apptime;
	}

	public boolean isDateWAPDOC() {
		return dateWAPDOC;
	}

	public void setDateWAPDOC(boolean dateWAPDOC) {
		this.dateWAPDOC = dateWAPDOC;
	}

	public boolean isDateWAPSTAT() {
		return dateWAPSTAT;
	}

	public void setDateWAPSTAT(boolean dateWAPSTAT) {
		this.dateWAPSTAT = dateWAPSTAT;
	}

	public boolean isDateWAPAPL() {
		return dateWAPAPL;
	}

	public void setDateWAPAPL(boolean dateWAPAPL) {
		this.dateWAPAPL = dateWAPAPL;
	}

	public boolean isDateWAPOFFER() {
		return dateWAPOFFER;
	}

	public void setDateWAPOFFER(boolean dateWAPOFFER) {
		this.dateWAPOFFER = dateWAPOFFER;
	}

	public boolean isDateWAPPAY() {
		return dateWAPPAY;
	}

	public void setDateWAPPAY(boolean dateWAPPAY) {
		this.dateWAPPAY = dateWAPPAY;
	}

	public boolean isDateWAPOFFICE() {
		return dateWAPOFFICE;
	}

	public void setDateWAPOFFICE(boolean dateWAPOFFICE) {
		this.dateWAPOFFICE = dateWAPOFFICE;
	}

	public void setStuIPAddress(String stuIPAddress){
    	this.stuIPAddress = stuIPAddress;
    }     
    public String getStuIPAddress(){
        return stuIPAddress;
    }
    
    public void setStuBrowser(String stuBrowser){
    	this.stuBrowser = stuBrowser;
    }     
    public String getStuBrowser(){
        return stuBrowser;
    }
    
    public void setStuBrowserAgent(String stuBrowserAgent){
    	this.stuBrowserAgent = stuBrowserAgent;
    }     
    public String getStuBrowserAgent(){
        return stuBrowserAgent;
    }
    
    public void setStuWksOS(String stuWksOS){
    	this.stuWksOS = stuWksOS;
    }     
    public String getStuWksOS(){
        return stuWksOS;
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