//Created by MyEclipse Struts
// XSL source (default): platform:/plugin/com.genuitec.eclipse.cross.easystruts.eclipse_3.9.210/xslt/JavaClass.xsl

package za.ac.unisa.lms.tools.mdapplications.forms;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts.upload.FormFile;
import org.apache.struts.validator.ValidatorActionForm;

public class MdApplicationsForm extends ValidatorActionForm {

	// --------------------------------------------------------- Instance Variables

	private static final long serialVersionUID = 1L;
	private static final String version = "2018003";

	private String applyType = "";
	private String loginType = "";
	private String registrationType ="";// U = undergrad, P = postgrad, S = Short learning programme
	private Student student = new Student();
	private Staff adminStaff = new Staff();
	private Qualification qual = new Qualification();
///putback	private Application studentApplication = new Application();
	//private History prevHistory = new History();
	private List<MdPrev> mdprevList;
	
	private String readmd = "";
	
	// ----- Walkthrough
	private String question1 = "";
	private String question2 = "";

	// Apply for student nr
	private String selectedDisability;
	private String selectedInterestArea;
	private String selectedQual;
	private String selectedSpes;
	private String selectedCountry;
	private String selectedExamCentre;
	private String selectedHomeLanguage;
	private String selectedNationality;
	private String selectedPopulationGroup;
	private String selectedOccupation;
	private String selectedEconomicSector;
	private String selectedOtherUniversity;
	private String SelectedPrevInstitution;
	private String SelectedProvince;
	private String selectedPrevActivity;

	private String agree;
	private String agreeQualInfo;
	private String numberBack;
	private String emailBack;
	private String timeBack;
	private String donesubmit;
	private int spescount=0;
	
	// keep count of number of search attempts for student number
	private int studentNumberSearchAttemp;

	//variable used to test session throughout process
	private String fromPage;
	
	// variable used to indicate that document upload is application specific
	private boolean application = true;
	
	// File upload
	private String addressLink = "";
	private String fileName = "";
	private String fileType = "";
	private FormFile theFile;
	private ArrayList<StudentFile> studentFiles = new ArrayList<StudentFile>();
	private ArrayList<StudentFile> requiredFiles = new ArrayList<StudentFile>();
	
	//Admin Login
	private String webLoginMsg = "";
	private boolean dateWEBMDAPP = false;
	private boolean dateWEBMDDOC = false;
	private boolean dateWEBMDADM = false;
	
	
	public String getVersion() {
		return version;
	}
	
	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}
	
	public Staff getAdminStaff() {
		return adminStaff;
	}

	public void setAdminStaff(Staff adminStaff) {
		this.adminStaff = adminStaff;
	}

	public String getApplyType() {
		return applyType;
	}

	public void setApplyType(String applyType) {
		this.applyType = applyType;
	}

	public String getLoginType() {
		return loginType;
	}

	public void setLoginType(String loginType) {
		this.loginType = loginType;
	}

	public String getQuestion1() {
		return question1;
	}

	public void setQuestion1(String question1) {
		this.question1 = question1;
	}

	public String getQuestion2() {
		return question2;
	}

	public void setQuestion2(String question2) {
		this.question2 = question2;
	}

	public String getRegistrationType() {
		return registrationType;
	}

	public void setRegistrationType(String registrationType) {
		this.registrationType = registrationType;
	}

	public int getStudentNumberSearchAttemp() {
		return studentNumberSearchAttemp;
	}

	public void setStudentNumberSearchAttemp(int studentNumberSearchAttemp) {
		this.studentNumberSearchAttemp = studentNumberSearchAttemp;
	}

	public Qualification getQual() {
		return qual;
	}

	public void setQual(Qualification qual) {
		this.qual = qual;
	}

	public String getSelectedDisability() {
		return selectedDisability;
	}

	public void setSelectedDisability(String selectedDisability) {
		this.selectedDisability = selectedDisability;
	}

	public String getSelectedInterestArea() {
		return selectedInterestArea;
	}

	public void setSelectedInterestArea(String selectedInterestArea) {
		this.selectedInterestArea = selectedInterestArea;
	}
	
	public String getSelectedQual() {
		return selectedQual;
	}

	public void setSelectedQual(String selectedQual) {
		this.selectedQual = selectedQual;
	}

	public String getSelectedCountry() {
		return selectedCountry;
	}

	public void setSelectedCountry(String selectedCountry) {
		this.selectedCountry = selectedCountry;
	}

	public String getSelectedExamCentre() {
		return selectedExamCentre;
	}

	public void setSelectedExamCentre(String selectedExamCentre) {
		this.selectedExamCentre = selectedExamCentre;
	}

	public String getSelectedEconomicSector() {
		return selectedEconomicSector;
	}

	public void setSelectedEconomicSector(String selectedEconomicSector) {
		this.selectedEconomicSector = selectedEconomicSector;
	}

	public String getSelectedHomeLanguage() {
		return selectedHomeLanguage;
	}

	public void setSelectedHomeLanguage(String selectedHomeLanguage) {
		this.selectedHomeLanguage = selectedHomeLanguage;
	}

	public String getSelectedNationality() {
		return selectedNationality;
	}

	public void setSelectedNationality(String selectedNationality) {
		this.selectedNationality = selectedNationality;
	}

	public String getSelectedOccupation() {
		return selectedOccupation;
	}

	public void setSelectedOccupation(String selectedOccupation) {
		this.selectedOccupation = selectedOccupation;
	}

	public String getSelectedOtherUniversity() {
		return selectedOtherUniversity;
	}

	public void setSelectedOtherUniversity(String selectedOtherUniversity) {
		this.selectedOtherUniversity = selectedOtherUniversity;
	}

	public String getSelectedPopulationGroup() {
		return selectedPopulationGroup;
	}

	public void setSelectedPopulationGroup(String selectedPopulationGroup) {
		this.selectedPopulationGroup = selectedPopulationGroup;
	}

	public String getAgree() {
		return agree;
	}

	public void setAgree(String agree) {
		this.agree = agree;
	}

	public String getAgreeQualInfo() {
		return agreeQualInfo;
	}

	public void setAgreeQualInfo(String agreeQualInfo) {
		this.agreeQualInfo = agreeQualInfo;
	}
	
	public String getFromPage() {
		return fromPage;
	}

	public void setFromPage(String fromPage) {
		this.fromPage = fromPage;
	}

	public String getAddressLink() {
		return addressLink;
	}

	public void setAddressLink(String addressLink) {
		this.addressLink = addressLink;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public FormFile getTheFile() {
		return theFile;
	}

	public void setTheFile(FormFile theFile) {
		this.theFile = theFile;
	}

	public ArrayList<StudentFile> getStudentFiles() {
		return studentFiles;
	}

	public void setStudentFiles(ArrayList<StudentFile> studentFiles) {
		this.studentFiles = studentFiles;
	}

	public ArrayList<StudentFile> getRequiredFiles() {
		return requiredFiles;
	}

	public void setRequiredFiles(ArrayList<StudentFile> list) {
		this.requiredFiles = list;
	}

	public boolean isApplication() {
		return application;
	}

	public void setApplication(boolean application) {
		this.application = application;
	}

	public String getSelectedPrevInstitution() {
		return SelectedPrevInstitution;
	}

	public void setSelectedPrevInstitution(String selectedPrevInstitution) {
		SelectedPrevInstitution = selectedPrevInstitution;
	}

	public String getSelectedProvince() {
		return SelectedProvince;
	}

	public void setSelectedProvince(String selectedProvince) {
		SelectedProvince = selectedProvince;
	}

	public String getNumberBack() {
		return numberBack;
	}

	public void setNumberBack(String numberBack) {
		this.numberBack = numberBack;
	}

	public String getDonesubmit() {
		return donesubmit;
	}

	public void setDonesubmit(String donesubmit) {
		this.donesubmit = donesubmit;
	}

	public String getEmailBack() {
		return emailBack;
	}

	public void setEmailBack(String emailBack) {
		this.emailBack = emailBack;
	}

	public String getTimeBack() {
		return timeBack;
	}

	public void setTimeBack(String timeBack) {
		this.timeBack = timeBack;
	}

	public String getSelectedSpes() {
		return selectedSpes;
	}

	public void setSelectedSpes(String selectedSpes) {
		this.selectedSpes = selectedSpes;
	}
	public String getSelectedPrevActivity() {
		return selectedPrevActivity;
	}

	public void setSelectedPrevActivity(String selectedPrevActivity) {
		this.selectedPrevActivity = selectedPrevActivity;
	}
//	public History getPrevHistory() {
//		return prevHistory;
//	}

//	public void setPrevHistory(History prevHistory) {
//		this.prevHistory = prevHistory;
//	}

	public String getReadmd() {
		return readmd;
	}

	public void setReadmd(String readmd) {
		this.readmd = readmd;
	}

	public List<MdPrev> getMdprevList() {
		return mdprevList;
	}

	public void setMdprevList(List<MdPrev> mdprevList) {
		this.mdprevList = mdprevList;
	}

	public void setMdprevlist(MdPrev mdPrev, int index){
		this.mdprevList.set(index, mdPrev);
	}
	
	public MdPrev getMdprevlist(int index){
		 return mdprevList.get(index);
	}

	public int getSpescount() {
		return spescount;
	}

	public void setSpescount(int spescount) {
		this.spescount = spescount;
	}

	public String getWebLoginMsg() {
		return webLoginMsg;
	}

	public void setWebLoginMsg(String webLoginMsg) {
		this.webLoginMsg = webLoginMsg;
	}

	public boolean isDateWEBMDAPP() {
		return dateWEBMDAPP;
	}

	public void setDateWEBMDAPP(boolean dateWEBMDAPP) {
		this.dateWEBMDAPP = dateWEBMDAPP;
	}

	public boolean isDateWEBMDDOC() {
		return dateWEBMDDOC;
	}

	public void setDateWEBMDDOC(boolean dateWEBMDDOC) {
		this.dateWEBMDDOC = dateWEBMDDOC;
	}

	public boolean isDateWEBMDADM() {
		return dateWEBMDADM;
	}

	public void setDateWEBMDADM(boolean dateWEBMDADM) {
		this.dateWEBMDADM = dateWEBMDADM;
	}
}