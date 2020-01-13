//Created by MyEclipse Struts
// XSL source (default): platform:/plugin/com.genuitec.eclipse.cross.easystruts.eclipse_3.9.210/xslt/JavaClass.xsl

package za.ac.unisa.lms.tools.mdregistrations.forms;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts.validator.ValidatorActionForm;


public class MdRegistrationsForm extends ValidatorActionForm {

	// --------------------------------------------------------- Instance Variables

	private static final long serialVersionUID = 1L;

	private static final String version = "2018001";
	private String applyType = "";
	private String registrationType ="";// U = undergrad, P = postgrad, S = Short learning programme
	private Student student = new Student();
	private Qualification qual = new Qualification();
	private String listType; // A=all study units, S=specific, T=type in
	
	private String readmd = "";
	
	// ----- Walkthrough
	private String question1 = "";
	private String question2 = "";
	private String errorAdvisor="";
//	 Current registration list

	private ArrayList studyUnits;
	private ArrayList additionalStudyUnits;
	private ArrayList regStudyUnits;
	private ArrayList selectedAdditionalStudyUnits;
	private List<StudyUnit> newStudyUnits;
	private List<StudyUnit> currentRegStudyUnitsList =  new ArrayList<StudyUnit>();
	
	private String regPeriodOpen0;
	private String regPeriodOpen1;
	private String regPeriodOpen2;

	private String regPeriodOpen6;
	///QSPRUL table
	private ArrayList qualifRules;
	
	// Apply for student nr
	private String selectedDisability;
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
	private String numberBack;
	private String emailBack;
	private String timeBack;
	private String donesubmit;
	private String completeQual;
	private String payment;
	private String tempreg;

	// keep count of number of search attempts for student number
	private int studentNumberSearchAttemp;
	// number of study units to be added
	private int numberOfUnits; 

	//variable used to test session throughout process
	private String fromPage;
	
	// variable used to indicate that document upload is application specific
	private boolean application = true;
	
	// File upload
	private String addressLink = "";
	private String fileName = "";
	private String fileType = "";

	private boolean studyUnitAddition = false;
	
	//Courier Search
	private String selectedPostalCode;
	private int searchListIdentifier = 0;
	private String searchSuburb = "";
	private String searchTown = "";
	private String searchResult = "";

	// variable used to indicate that specific error message
	private boolean noStuNo = false;
	
	public String getVersion() {
		return version;
	}
	
	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public String getApplyType() {
		return applyType;
	}

	public void setApplyType(String applyType) {
		this.applyType = applyType;
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

	public String getReadmd() {
		return readmd;
	}

	public void setReadmd(String readmd) {
		this.readmd = readmd;
	}

	public String getCompleteQual() {
		return completeQual;
	}

	public void setCompleteQual(String completeQual) {
		this.completeQual = completeQual;
	}

	public int getNumberOfUnits() {
		return numberOfUnits;
	}

	public void setNumberOfUnits(int numberOfUnits) {
		this.numberOfUnits = numberOfUnits;
	}
	
	public ArrayList getStudyUnits() {
		return studyUnits;
	}

	public void setStudyUnits(ArrayList studyUnits) {
		this.studyUnits = studyUnits;
	}

	public ArrayList getAdditionalStudyUnits() {
		return additionalStudyUnits;
	}

	public void setAdditionalStudyUnits(ArrayList additionalStudyUnits) {
		this.additionalStudyUnits = additionalStudyUnits;
	}

	public ArrayList getSelectedAdditionalStudyUnits() {
		return selectedAdditionalStudyUnits;
	}

	public void setSelectedAdditionalStudyUnits(ArrayList selectedAdditionalStudyUnits) {
		this.selectedAdditionalStudyUnits = selectedAdditionalStudyUnits;
	}

	public String getListType() {
		return listType;
	}

	public void setListType(String listType) {
		this.listType = listType;
	}

	public ArrayList getQualifRules() {
		return qualifRules;
	}

	public void setQualifRules(ArrayList qualifRules) {
		this.qualifRules = qualifRules;
	}
	
	public ArrayList getRegStudyUnits() {
		return regStudyUnits;
	}

	public void setRegStudyUnits(ArrayList regStudyUnits) {
		this.regStudyUnits = regStudyUnits;
	}

	public List<StudyUnit> getNewStudyUnits() {
		return newStudyUnits;
	}

	public void setNewStudyUnits(List<StudyUnit> newStudyUnits) {
		this.newStudyUnits = newStudyUnits;
	}

	public List<StudyUnit> getCurrentRegStudyUnitsList() {
		return currentRegStudyUnitsList;
	}

	public void setCurrentRegStudyUnitsList(List<StudyUnit> currentRegStudyUnitsList) {
		this.currentRegStudyUnitsList = currentRegStudyUnitsList;
	}

	public String getRegPeriodOpen0() {
		return regPeriodOpen0;
	}

	public void setRegPeriodOpen0(String regPeriodOpen0) {
		this.regPeriodOpen0 = regPeriodOpen0;
	}

	public String getRegPeriodOpen1() {
		return regPeriodOpen1;
	}

	public void setRegPeriodOpen1(String regPeriodOpen1) {
		this.regPeriodOpen1 = regPeriodOpen1;
	}

	public String getRegPeriodOpen2() {
		return regPeriodOpen2;
	}

	public void setRegPeriodOpen2(String regPeriodOpen2) {
		this.regPeriodOpen2 = regPeriodOpen2;
	}

	public String getRegPeriodOpen6() {
		return regPeriodOpen6;
	}

	public void setRegPeriodOpen6(String regPeriodOpen6) {
		this.regPeriodOpen6 = regPeriodOpen6;
	}

	public String getPayment() {
		return payment;
	}

	public void setPayment(String payment) {
		this.payment = payment;
	}

	public String getTempreg() {
		return tempreg;
	}

	public void setTempreg(String tempreg) {
		this.tempreg = tempreg;
	}

	public String getErrorAdvisor() {
		return errorAdvisor;
	}

	public void setErrorAdvisor(String errorAdvisor) {
		this.errorAdvisor = errorAdvisor;
	}

	public boolean isStudyUnitAddition() {
		return studyUnitAddition;
	}

	public void setStudyUnitAddition(boolean studyUnitAddition) {
		this.studyUnitAddition = studyUnitAddition;
	}

	public String getSelectedPostalCode() {
		return selectedPostalCode;
	}

	public void setSelectedPostalCode(String selectedPostalCode) {
		this.selectedPostalCode = selectedPostalCode;
	}

	public int getSearchListIdentifier() {
		return searchListIdentifier;
	}

	public void setSearchListIdentifier(int searchListIdentifier) {
		this.searchListIdentifier = searchListIdentifier;
	}

	public String getSearchSuburb() {
		return searchSuburb;
	}

	public void setSearchSuburb(String searchSuburb) {
		this.searchSuburb = searchSuburb;
	}

	public String getSearchTown() {
		return searchTown;
	}

	public void setSearchTown(String searchTown) {
		this.searchTown = searchTown;
	}

	public String getSearchResult() {
		return searchResult;
	}

	public void setSearchResult(String searchResult) {
		this.searchResult = searchResult;
	}
	
	public boolean isNoStuNo() {
		return noStuNo;
	}

	public void setNoStuNo(boolean noStuNo) {
		this.noStuNo = noStuNo;
	}
}