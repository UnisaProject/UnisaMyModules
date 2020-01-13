//Created by MyEclipse Struts
// XSL source (default): platform:/plugin/com.genuitec.eclipse.cross.easystruts.eclipse_3.9.210/xslt/JavaClass.xsl

package za.ac.unisa.lms.tools.studentregistration.forms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;
import org.apache.struts.validator.ValidatorActionForm;

import za.ac.unisa.lms.tools.studentregistration.actions.ApplyForStudentNumberAction;
import za.ac.unisa.lms.tools.studentregistration.bo.Categories;
import za.ac.unisa.lms.tools.studentregistration.bo.Doc;
import za.ac.unisa.lms.tools.studentregistration.bo.Qualifications;
import za.ac.unisa.lms.tools.studentregistration.bo.Specializations;
import za.ac.unisa.lms.tools.studentregistration.bo.Status;
import za.ac.unisa.lms.tools.studentregistration.bo.StudySelected;
import za.ac.unisa.lms.tools.studentregistration.dao.ApplyForStudentNumberQueryDAO;
import za.ac.unisa.lms.tools.studentregistration.dao.DocDao;
import za.ac.unisa.lms.tools.studentregistration.dao.SavedDocDao;

public class StudentRegistrationForm extends ValidatorActionForm {

	// --------------------------------------------------------- Instance Variables

	private static final long serialVersionUID = 1L;
	private static final String version = "2019001b";
	public static Log log = LogFactory.getLog(ApplyForStudentNumberAction.class);
	
	private String applyType = "";
	private int applySEQUENCE = 0;
	private String registrationType ="";// U = undergrad, P = postgrad, S = Short learning programme
	private boolean allowLogin = true;
	private Student student = new Student();
	private Qualification qual = new Qualification();
	private Qualification qualtwo = new Qualification();
	private Qualification qualAPS = new Qualification();
	private Application studentApplication = new Application();
	private Staff adminStaff = new Staff();
    private HistoryOther qualOther = new HistoryOther();
    private Status status = new Status();

	private MatricM30 matric = new MatricM30();
	private boolean qualSTAAE01 = false;
	private int Spescount=0;
	private String regqualtype="";

	// ----- Walkthrough
	private String question1 = "";
	private String question2 = "";

	// Apply for student nr
	private String selectedDisability;
	private String selectedQual;
	private String selectedQualtwo;
	private String selectedSpes;
	private String selectedSpestwo;
	private String selectedCountry;
	private String selectedExamCentre;
	private String selectedExamCenprov;
	private String selectedHomeLanguage;
	private String selectedNationality;
	private String selectedPopulationGroup;
	private String selectedOccupation;
	private String selectedEconomicSector;
	private String selectedOtherUniversity;
	private String SelectedPrevInstitution;
	private String SelectedProvince;

	private String SelectedSchool;
	private String schoolCode = "";
	private String schoolDesc = "";
	
	private String selectedPrevActivity;

	private String savedCategory;
	private String savedCategory1;
	private String savedCategory2;
	private String savedQual;
	private String savedQual1;
	private String savedQual2;
	private String savedSpec;
	private String savedSpec1;
	private String savedSpec2;
	
	private String agree;
	private String numberBack;
	private String emailBack;
	private String timeBack;
	private boolean doneSubmit;
	private String String500back;

	//variable used to test session throughout process
	private String fromPage;

	// variable used to indicate that document upload is application specific
	private boolean application = true;

	// File upload
	private String addressLink = "";
	private String fileName = "";
	private String fileNewName = "";
	private String fileType = "";
	private int fileCount = 0;
	private FormFile file;
	private FormFile theFile;
	private String fileSelected = "";
	private ArrayList<StudentFile> studentFiles = new ArrayList<StudentFile>();
	private String selectedPostalCode;
	private String searchListIdentifier = "Postal";
	private String searchSuburb = "";
	private String searchTown = "";
	private String searchResult = "";
	private String addressSubResultPos = "";
	private String addressSubResultPhys = "";
	private String addressSubResultCour = "";

	private boolean hiddenButton = false;
	private boolean hiddenQualButton ;

	// 2014 New Login Select
	private String selected = "1";
	
	private String loginSelectAPS = "";
	private String loginSelectMain = "";
	private String loginSelectDocPay = "";
	private String loginSelectYesNo = "";
	private String selectSLP = "";
	private String webLoginMsg = "";
	private String webLoginMsg2 = "";
	private String webUploadMsg = "";
	private String selectReset = "";
	
	private String selCategoryCode;
	private String selCategoryCode1;
	private String selCategoryCode2;
	
	private String selQualCode;
	private String selQualCode1;
	private String selQualCode2;
	
	private String selQualCodeDesc = "";
	private String selQualCode1Desc = "";
	private String selQualCode2Desc = "";
	
	private String selSpecCode;
	private String selSpecCode1;
	private String selSpecCode2;
	
	private String selSpecCodeDesc = "";
	private String selSpecCode1Desc = "";
	private String selSpecCode2Desc = "";
	
	private String existQual = "";
	private String existSpec = "";
	private String existQualDesc = "";
	private String existSpecDesc = "";
	
	private String selQualPrevCode;

	ArrayList<String> categoryCodeList = new ArrayList<String>();
	ArrayList<String> categoryCodeList1 = new ArrayList<String>();
	ArrayList<String> categoryCodeList2 = new ArrayList<String>();

	ArrayList<String> categoryDescList = new ArrayList<String>();
	ArrayList<String> categoryDescList1 = new ArrayList<String>();
	ArrayList<String> categoryDescList2 = new ArrayList<String>();
	
	ArrayList<String> qualCodeList = new ArrayList<String>();
	ArrayList<String> qualCodeList1 = new ArrayList<String>();
	ArrayList<String> qualCodeList2 = new ArrayList<String>();
	
	ArrayList<String> qualCodeDescList = new ArrayList<String>();
	ArrayList<String> qualCodeDescList1 = new ArrayList<String>();
	ArrayList<String> qualCodeDescList2 = new ArrayList<String>();
	
	ArrayList<String> qualDescList = new ArrayList<String>();
	ArrayList<String> qualDescList1 = new ArrayList<String>();
	ArrayList<String> qualDescList2 = new ArrayList<String>();
	
	ArrayList<String> specCodeList = new ArrayList<String>();
	ArrayList<String> specCodeList1 = new ArrayList<String>();
	ArrayList<String> specCodeList2 = new ArrayList<String>();
	
	ArrayList<String> specCodeDescList = new ArrayList<String>();
	ArrayList<String> specCodeDescList1 = new ArrayList<String>();
	ArrayList<String> specCodeDescList2 = new ArrayList<String>();
	
	ArrayList<String> specDescList = new ArrayList<String>();
	ArrayList<String> specDescList1 = new ArrayList<String>();
	ArrayList<String> specDescList2 = new ArrayList<String>();

	ArrayList<String> qualPrevCodeList = new ArrayList<String>();
	ArrayList<String> qualPrevDescList = new ArrayList<String>();
	ArrayList<String> qualPrevCodeDescList = new ArrayList<String>();
	
	private String qualStatus1 = "";
	private String qualStatus2 = "";
	private String qualStatusCode1 = "";
	private String qualStatusCode2 = "";
	private String qualStatus1Reason = "";
	private String qualStatus2Reason = "";
	
	private String offerQual1 = "";
	private String offerQual2 = "";
	private String offerSpec1 = "";
	private String offerSpec2 = "";
	
	private String offerRadio = "";
	
	//FileUpload
	//for showing optional docs

	private List<String> desc = new ArrayList<String>();
	private Map<String, List<String>> map = new HashMap<String, List<String>>();
		

	private List<Doc> requiredDocs;
	private List<Doc> optionalDocs;

	private List<String> optionLabels = new ArrayList<String>();
	private List<String> optionCodes = new ArrayList<String>();

	private FileBean[] requiredFileBeans;
	private FileBean optionFileBean;

	private String docMsg = "";

	private boolean hiddenButtonUpload ;
	
	//Matric Results for APS Calculator
    private String selectHEMain = "";
    private String selectMatric = "";
    private int apsScore = 0;
    private int apsSize = 0;
    private List<Subject> subjects;
    
    private ArrayList qualUnisa;
    private ArrayList qualArray;
    
    private boolean qualUnisaFound = false;
    private boolean qualIDMatch = false;
    private boolean qualAPSMatch = false;
    
    private String apsSubjectName1  = "";
    private String apsSubjectName2  = "";
    private String apsSubjectName3  = "";
    private String apsSubjectName4  = "";
    private String apsSubjectName5  = "";
    private String apsSubjectName6  = "";
    private String apsSubjectName7  = "";
    private String apsSubjectName8  = "";
    private String apsSubjectName9  = "";
    private String apsSubjectName10 = "";
    private String apsSubjectName11 = "";
    private String apsSubjectName12 = "";
    
    private String apsSubjectGrade1  = "";
    private String apsSubjectGrade2  = "";
    private String apsSubjectGrade3  = "";
    private String apsSubjectGrade4  = "";
    private String apsSubjectGrade5  = "";
    private String apsSubjectGrade6  = "";
    private String apsSubjectGrade7  = "";
    private String apsSubjectGrade8  = "";
    private String apsSubjectGrade9  = "";
    private String apsSubjectGrade10 = "";
    private String apsSubjectGrade11 = "";
    private String apsSubjectGrade12 = "";
    
    private String apsSubjectSymbol1  = "";
    private String apsSubjectSymbol2  = "";
    private String apsSubjectSymbol3  = "";
    private String apsSubjectSymbol4  = "";
    private String apsSubjectSymbol5  = "";
    private String apsSubjectSymbol6  = "";
    private String apsSubjectSymbol7  = "";
    private String apsSubjectSymbol8  = "";
    private String apsSubjectSymbol9  = "";
    private String apsSubjectSymbol10 = "";
    private String apsSubjectSymbol11 = "";
    private String apsSubjectSymbol12 = "";
    
    private String apsSubjectResult1  = "";
    private String apsSubjectResult2  = "";
    private String apsSubjectResult3  = "";
    private String apsSubjectResult4  = "";
    private String apsSubjectResult5  = "";
    private String apsSubjectResult6  = "";
    private String apsSubjectResult7  = "";
    private String apsSubjectResult8  = "";
    private String apsSubjectResult9  = "";
    private String apsSubjectResult10 = "";
    private String apsSubjectResult11 = "";
    private String apsSubjectResult12 = "";
    
    //Appeal Process
    private String appealSelect1 = "";
    private String appealSelect2 = "";
    private String appealText = "";
	private ArrayList<String> appealSourceFiles = new ArrayList<String>();
	private ArrayList<String> appealTypeFiles = new ArrayList<String>();
	private ArrayList<String> appealWorkflowFiles = new ArrayList<String>();
	
	//M&D Admission Process
	private List<MdPrev> mdprevList;
	private String readmd = "";

	
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

	public int getApplySEQUENCE() {
		return applySEQUENCE;
	}

	public void setApplySEQUENCE(int applySEQUENCE) {
		this.applySEQUENCE = applySEQUENCE;
	}

	public boolean isAllowLogin() {
		return allowLogin;
	}

	public void setAllowLogin(boolean allowLogin) {
		this.allowLogin = allowLogin;
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

	public Qualification getQual() {
		return qual;
	}

	public void setQual(Qualification qual) {
		this.qual = qual;
	}

	public String getSelectedQual() {
		return selectedQual;
	}

	public void setSelectedQual(String selectedQual) {
		this.selectedQual = selectedQual;
	}

	public String getSelectedQualtwo() {
		return selectedQualtwo;
	}

	public void setSelectedQualtwo(String selectedQualtwo) {
		this.selectedQualtwo = selectedQualtwo;
	}

	public Qualification getQualtwo() {
		return qualtwo;
	}

	public void setQualtwo(Qualification qualtwo) {
		this.qualtwo = qualtwo;
	}
	public Qualification getQualAPS() {
		return qualAPS;
	}

	public void setQualAPS(Qualification qualAPS) {
		this.qualAPS = qualAPS;
	}

	public int getSpescount() {
		return Spescount;
	}

	public String getSelectedDisability() {
		return selectedDisability;
	}

	public void setSelectedDisability(String selectedDisability) {
		this.selectedDisability = selectedDisability;
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

	public String getNewFileName() {
		return fileNewName;
	}

	public void setNewFileName(String fileNewName) {
		this.fileNewName = fileNewName;
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

	public int getFileCount() {
		return fileCount;
	}

	public void setFileCount(int fileCount) {
		this.fileCount = fileCount;
	}
	
	public FormFile getFile() {
		return file;
	}

	public void setFile(FormFile file) {
		this.file = file;
	}

	public String getFileSelected() {
		return fileSelected;
	}

	public void setFileSelected(String fileSelected) {
		this.fileSelected = fileSelected;
	}

	public ArrayList<StudentFile> getStudentFiles() {
		return studentFiles;
	}
	
	public void setStudentFiles(ArrayList<StudentFile> studentFiles) {
		this.studentFiles = studentFiles;
	}
	
	public String getAppealSelect1() {
		return appealSelect1;
	}

	public void setAppealSelect1(String appealSelect1) {
		this.appealSelect1 = appealSelect1;
	}

	public String getAppealSelect2() {
		return appealSelect2;
	}

	public void setAppealSelect2(String appealSelect2) {
		this.appealSelect2 = appealSelect2;
	}

	public String getAppealText() {
		return appealText;
	}

	public void setAppealText(String appealText) {
		this.appealText = appealText;
	}

	public ArrayList<String> getAppealSourceFiles() {
		return appealSourceFiles;
	}

	public void setAppealSourceFiles(ArrayList<String> appealSourceFiles) {
		this.appealSourceFiles = appealSourceFiles;
	}

	public ArrayList<String> getAppealTypeFiles() {
		return appealTypeFiles;
	}

	public void setAppealTypeFiles(ArrayList<String> appealTypeFiles) {
		this.appealTypeFiles = appealTypeFiles;
	}

	public ArrayList<String> getAppealWorkflowFiles() {
		return appealWorkflowFiles;
	}

	public void setAppealWorkflowFiles(ArrayList<String> appealWorkflowFiles) {
		this.appealWorkflowFiles = appealWorkflowFiles;
	}

	public boolean isApplication() {
		return application;
	}

	public void setApplication(boolean application) {
		this.application = application;
	}

	public Application getStudentApplication() {
		return studentApplication;
	}

	public void setStudentApplication(Application studentApplication) {
		this.studentApplication = studentApplication;
	}
	
	public Staff getAdminStaff() {
		return adminStaff;
	}

	public void setAdminStaff(Staff adminStaff) {
		this.adminStaff = adminStaff;
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

	public String getSelectedSchool() {
		return SelectedSchool;
	}

	public void setSelectedSchool(String selectedSchool) {
		SelectedSchool = selectedSchool;
	}
	public String getSchoolCode() {
		return schoolCode;
	}
	public void SchoolCode(String schoolCode) {
		this.schoolCode = schoolCode;
	}
	public String getSchoolDesc() {
		return schoolDesc;
	}
	public void setSchoolDesc(String schoolDesc) {
		this.schoolDesc = schoolDesc;
	}
	
	public String getNumberBack() {
		return numberBack;
	}

	public void setNumberBack(String numberBack) {
		this.numberBack = numberBack;
	}

	public boolean isDoneSubmit() {
		return doneSubmit;
	}

	public void setDoneSubmit(boolean doneSubmit) {
		this.doneSubmit = doneSubmit;
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

	public String getSelectedSpestwo() {
		return selectedSpestwo;
	}

	public void setSelectedSpestwo(String selectedSpestwo) {
		this.selectedSpestwo = selectedSpestwo;
	}

	public String getSelectedPrevActivity() {
		return selectedPrevActivity;
	}

	public void setSelectedPrevActivity(String selectedPrevActivity) {
		this.selectedPrevActivity = selectedPrevActivity;
	}
	public MatricM30 getMatric() {
		return matric;
	}

	public void setMatric(MatricM30 matric) {
		this.matric = matric;
	}

	public boolean isQualSTAAE01() {
		return qualSTAAE01;
	}

	public void setQualSTAAE01(boolean qualSTAAE01) {
		this.qualSTAAE01 = qualSTAAE01;
	}

	public void setSpescount(int spescount) {
		Spescount = spescount;
	}

	public String getString500back() {
		return String500back;
	}

	public void setString500back(String string500back) {
		String500back = string500back;
	}

	public String getRegqualtype() {
		return regqualtype;
	}

	public void setRegqualtype(String regqualtype) {
		this.regqualtype = regqualtype;
	}

	public String getSelectedExamCenprov() {
		return selectedExamCenprov;
	}

	public void setSelectedExamCenprov(String selectedExamCenprov) {
		this.selectedExamCenprov = selectedExamCenprov;
	}

	public String getSelectedPostalCode() {
		return selectedPostalCode;
	}

	public void setSelectedPostalCode(String selectedPostalCode) {
		this.selectedPostalCode = selectedPostalCode;
	}

	public String getSearchListIdentifier() {
		return searchListIdentifier;
	}

	public void setSearchListIdentifier(String searchListIdentifier) {
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

	public String getAddressSubResultPos() {
		return addressSubResultPos;
	}

	public void setAddressSubResultPos(String addressSubResultPos) {
		this.addressSubResultPos = addressSubResultPos;
	}
	
	public String getAddressSubResultPhys() {
		return addressSubResultPhys;
	}

	public void setAddressSubResultPhys(String addressSubResultPhys) {
		this.addressSubResultPhys = addressSubResultPhys;
	}
	
	public String getAddressSubResultCour() {
		return addressSubResultCour;
	}

	public void setAddressSubResultCour(String addressSubResultCour) {
		this.addressSubResultCour = addressSubResultCour;
	}
	
	public String getLoginSelectAPS() {
		return loginSelectAPS;
	}

	public void setLoginSelectAPS(String loginSelectAPS) {
		this.loginSelectAPS = loginSelectAPS;
	}

	// New 2014 Login Select
	public String getLoginSelectMain() {
		return loginSelectMain;
	}

	public void setLoginSelectMain(String loginSelectMain) {
		this.loginSelectMain = loginSelectMain;
	}
	
	public String getSelectSLP() {
		return selectSLP;
	}

	public void setSelectSLP(String selectSLP) {
		this.selectSLP = selectSLP;
	}

	public String getLoginSelectDocPay() {
		return loginSelectDocPay;
	}

	public void setLoginSelectDocPay(String loginSelectDocPay) {
		this.loginSelectDocPay = loginSelectDocPay;
	}
	
	public String getLoginSelectYesNo() {
		return loginSelectYesNo;
	}

	public void setLoginSelectYesNo(String loginSelectYesNo) {
		this.loginSelectYesNo = loginSelectYesNo;
	}
	
	public String getWebLoginMsg2() {
		return webLoginMsg2;
	}

	public void setWebLoginMsg2(String webLoginMsg2) {
		this.webLoginMsg2 = webLoginMsg2;
	}

	public List<MdPrev> getMdprevList() {
		return mdprevList;
	}

	public void setMdprevList(List<MdPrev> mdprevList) {
		this.mdprevList = mdprevList;
	}

	public String getReadmd() {
		return readmd;
	}

	public void setReadmd(String readmd) {
		this.readmd = readmd;
	}

	public String getWebLoginMsg() {
		return webLoginMsg;
	}

	public void setWebLoginMsg(String webLoginMsg) {
		this.webLoginMsg = webLoginMsg;
	}

	public String getWebUploadMsg() {
		return webUploadMsg;
	}

	public void setWebUploadMsg(String webUploadMsg) {
		this.webUploadMsg = webUploadMsg;
	}
	
	public String getSelectReset() {
		return selectReset;
	}

	public void setSelectReset(String selectReset) {
		this.selectReset = selectReset;
	}
	

	public boolean isHiddenButton() {
		  try {
			  if("newStu".equalsIgnoreCase(getStudent().getStuExist()) && ("".equals(getStudent().getNumber()) || getStudent().getNumber() == null)){
				  //log.debug("no Student Number - New Student - Disable Document button: " + getStudent().getStuExist());
				  return true;
			  }
			  return false;
		  } catch (Exception e) {
		   e.printStackTrace();
		  }
	  return hiddenButton;
	}

	public void setHiddenButton(boolean hiddenButton) {
		this.hiddenButton = hiddenButton;
	}

	public boolean isHiddenQualButton() {
		  try {
			  if("None".equals(getStudent().getStuapqExist())){
				  //log.debug("Has No STUAPQ - Enable Apply button: " + getStudent().getStuapqExist());
				  return false;
			  }
			  return true;
		  } catch (Exception e) {
		   e.printStackTrace();
		  }
	  return hiddenQualButton;
	}

	public void setHiddenQualButton(boolean hiddenQualButton) {
		this.hiddenQualButton = hiddenQualButton;
	}
	public ArrayList<String> getCategoryCodeList() throws Exception {
		ApplyForStudentNumberQueryDAO dao = new ApplyForStudentNumberQueryDAO();
		Categories categories = dao.getCategories(getStudent().getAcademicYear(), getLoginSelectMain(), adminStaff.isAdmin(),getStudent().isDateWAPU(),getStudent().isDateWAPRU(),getStudent().isDateWAPADMU(),getStudent().isDateWAPH(),getStudent().isDateWAPRH(),getStudent().isDateWAPADMH(),getStudent().isDateWAPD(),getStudent().isDateWAPADMD(),getStudent().isDateWAPM(),getStudent().isDateWAPADMM(),getStudent().isDateWAPS(),getStudent().isDateWAPADMS());
		//log.debug("StudentRegistrationForm - getCategoryCodeList");
		return categories.getCatCodes();
	}

	public ArrayList<String> getCategoryDescList() throws Exception {

		ApplyForStudentNumberQueryDAO dao = new ApplyForStudentNumberQueryDAO();
		Categories categories = dao.getCategories(getStudent().getAcademicYear(), getLoginSelectMain(), adminStaff.isAdmin(),getStudent().isDateWAPU(),getStudent().isDateWAPRU(),getStudent().isDateWAPADMU(),getStudent().isDateWAPH(),getStudent().isDateWAPRH(),getStudent().isDateWAPADMH(),getStudent().isDateWAPD(),getStudent().isDateWAPADMD(),getStudent().isDateWAPM(),getStudent().isDateWAPADMM(),getStudent().isDateWAPS(),getStudent().isDateWAPADMS());
		//log.debug("StudentRegistrationForm - getCategoryDescList");
		return categories.getCatDescs();
	}
	public ArrayList<String> getCategoryCodeList1() throws Exception {
		ApplyForStudentNumberQueryDAO dao = new ApplyForStudentNumberQueryDAO();
		Categories categories = dao.getCategories(getStudent().getAcademicYear(), getLoginSelectMain(), adminStaff.isAdmin(),getStudent().isDateWAPU(),getStudent().isDateWAPRU(),getStudent().isDateWAPADMU(),getStudent().isDateWAPH(),getStudent().isDateWAPRH(),getStudent().isDateWAPADMH(),getStudent().isDateWAPD(),getStudent().isDateWAPADMD(),getStudent().isDateWAPM(),getStudent().isDateWAPADMM(),getStudent().isDateWAPS(),getStudent().isDateWAPADMS());
		return categories.getCatCodes();

	}
	public void setCategoryCodeList1(ArrayList<String> categoryCodeList1) {
		this.categoryCodeList1 = categoryCodeList1;
	}
	public ArrayList<String> getCategoryDescList1() throws Exception {
		ApplyForStudentNumberQueryDAO dao = new ApplyForStudentNumberQueryDAO();
		Categories categories = dao.getCategories(getStudent().getAcademicYear(), getLoginSelectMain(), adminStaff.isAdmin(),getStudent().isDateWAPU(),getStudent().isDateWAPRU(),getStudent().isDateWAPADMU(),getStudent().isDateWAPH(),getStudent().isDateWAPRH(),getStudent().isDateWAPADMH(),getStudent().isDateWAPD(),getStudent().isDateWAPADMD(),getStudent().isDateWAPM(),getStudent().isDateWAPADMM(),getStudent().isDateWAPS(),getStudent().isDateWAPADMS());
		return categories.getCatDescs();
	}
	public void setCategoryDescList1(ArrayList<String> categoryDescList1) {
		this.categoryDescList1 = categoryDescList1;
	}
	public ArrayList<String> getQualCodeList1() throws Exception {

		ApplyForStudentNumberQueryDAO dao = new ApplyForStudentNumberQueryDAO();
		
		//log.debug("StudentRegistrationForm - queryStudySelectedNew: " +getStudent().getNumber()+"~"+getStudent().getAcademicYear()+"~"+getStudent().getAcademicPeriod());
		StudySelected selected = dao.queryStudySelected(getStudent().getNumber(),getStudent().getAcademicYear(),getStudent().getAcademicPeriod());
		if(selected !=null ){
			Qualifications quals = dao.getQualifications(getStudent().getNumber(), getStudent().getStuExist(), getStudent().getAcademicYear(), getStudent().getAcademicPeriod(), selected.getCategory1(), getLoginSelectMain(), getAdminStaff().isAdmin(),getStudent().isDateWAPU(),getStudent().isDateWAPRU(),getStudent().isDateWAPADMU(),getStudent().isDateWAPH(),getStudent().isDateWAPRH(),getStudent().isDateWAPADMH(),getStudent().isDateWAPD(),getStudent().isDateWAPADMD(),getStudent().isDateWAPM(),getStudent().isDateWAPADMM(),getStudent().isDateWAPS(),getStudent().isDateWAPADMS());
			return quals.getQualCodes();
		}
		 return qualCodeList1;
	}
	public void setQualCodeList1(ArrayList<String> qualCodeList1) {
		this.qualCodeList1 = qualCodeList1;
	}
	public ArrayList<String> getQualDescList1() throws Exception {

		ApplyForStudentNumberQueryDAO dao = new ApplyForStudentNumberQueryDAO();

		StudySelected selected = dao.queryStudySelected(getStudent().getNumber(),getStudent().getAcademicYear(),getStudent().getAcademicPeriod());

		if(selected !=null ){
			Qualifications quals = dao.getQualifications(getStudent().getNumber(), getStudent().getStuExist(), getStudent().getAcademicYear(), getStudent().getAcademicPeriod(), selected.getCategory1(), getLoginSelectMain(), getAdminStaff().isAdmin(),getStudent().isDateWAPU(),getStudent().isDateWAPRU(),getStudent().isDateWAPADMU(),getStudent().isDateWAPH(),getStudent().isDateWAPRH(),getStudent().isDateWAPADMH(),getStudent().isDateWAPD(),getStudent().isDateWAPADMD(),getStudent().isDateWAPM(),getStudent().isDateWAPADMM(),getStudent().isDateWAPS(),getStudent().isDateWAPADMS());
			return quals.getQualDescs();
		}

		return qualDescList1;
	}
	public void setQualDescList1(ArrayList<String> qualDescList1) {
		this.qualDescList1 = qualDescList1;
	}
	public ArrayList<String> getSpecCodeList1() throws Exception {

		ApplyForStudentNumberQueryDAO dao = new ApplyForStudentNumberQueryDAO();
		
		StudySelected selected = dao.queryStudySelected(getStudent().getNumber(),getStudent().getAcademicYear(),getStudent().getAcademicPeriod());
		if(selected !=null ){
			Specializations quals = dao.getSpecializations(selQualCode1,getStudent().getAcademicYear(),getStudent().getAcademicPeriod());
			return quals.getSpecCodes();
		}

		return specCodeList1;
	}

	public ArrayList<String> getCategoryCodeList2() throws Exception {
		ApplyForStudentNumberQueryDAO dao = new ApplyForStudentNumberQueryDAO();
		Categories categories = dao.getCategories(getStudent().getAcademicYear(), getLoginSelectMain(), adminStaff.isAdmin(),getStudent().isDateWAPU(),getStudent().isDateWAPRU(),getStudent().isDateWAPADMU(),getStudent().isDateWAPH(),getStudent().isDateWAPRH(),getStudent().isDateWAPADMH(),getStudent().isDateWAPD(),getStudent().isDateWAPADMD(),getStudent().isDateWAPM(),getStudent().isDateWAPADMM(),getStudent().isDateWAPS(),getStudent().isDateWAPADMS());
		return categories.getCatCodes();
	}
	public void setCategoryCodeList2(ArrayList<String> categoryCodeList2) {
		this.categoryCodeList2 = categoryCodeList2;
	}
	public ArrayList<String> getCategoryDescList2() throws Exception {
		ApplyForStudentNumberQueryDAO dao = new ApplyForStudentNumberQueryDAO();
		Categories categories = dao.getCategories(getStudent().getAcademicYear(), getLoginSelectMain(), adminStaff.isAdmin(),getStudent().isDateWAPU(),getStudent().isDateWAPRU(),getStudent().isDateWAPADMU(),getStudent().isDateWAPH(),getStudent().isDateWAPRH(),getStudent().isDateWAPADMH(),getStudent().isDateWAPD(),getStudent().isDateWAPADMD(),getStudent().isDateWAPM(),getStudent().isDateWAPADMM(),getStudent().isDateWAPS(),getStudent().isDateWAPADMS());
		return categories.getCatDescs();
	}
	public void setCategoryDescList2(ArrayList<String> categoryDescList2) {
		this.categoryDescList2 = categoryDescList2;
	}
	public ArrayList<String> getQualCodeList() throws Exception {
		ApplyForStudentNumberQueryDAO dao = new ApplyForStudentNumberQueryDAO();
		StudySelected selected = dao.queryStudySelected(getStudent().getNumber(),getStudent().getAcademicYear(),getStudent().getAcademicPeriod());

		if(selected !=null ){
			Qualifications quals = dao.getQualifications(getStudent().getNumber(), getStudent().getStuExist(), getStudent().getAcademicYear(), getStudent().getAcademicPeriod(), selected.getCategory(), getLoginSelectMain(), getAdminStaff().isAdmin(),getStudent().isDateWAPU(),getStudent().isDateWAPRU(),getStudent().isDateWAPADMU(),getStudent().isDateWAPH(),getStudent().isDateWAPRH(),getStudent().isDateWAPADMH(),getStudent().isDateWAPD(),getStudent().isDateWAPADMD(),getStudent().isDateWAPM(),getStudent().isDateWAPADMM(),getStudent().isDateWAPS(),getStudent().isDateWAPADMS());
			this.qualCodeList = quals.getQualCodes();
			//return quals.getQualCodes();
			return this.qualCodeList;
		}
		//log.debug("StudentRegistrationForm - getQualCodeList");
		return qualCodeList;
	}

	public ArrayList<String> getQualDescList() throws Exception {
		ApplyForStudentNumberQueryDAO dao = new ApplyForStudentNumberQueryDAO();
		StudySelected selected = dao.queryStudySelected(getStudent().getNumber(),getStudent().getAcademicYear(),getStudent().getAcademicPeriod());

		if(selected !=null ){
			Qualifications quals = dao.getQualifications(getStudent().getNumber(), getStudent().getStuExist(), getStudent().getAcademicYear(), getStudent().getAcademicPeriod(), selected.getCategory(), getLoginSelectMain(), getAdminStaff().isAdmin(),getStudent().isDateWAPU(),getStudent().isDateWAPRU(),getStudent().isDateWAPADMU(),getStudent().isDateWAPH(),getStudent().isDateWAPRH(),getStudent().isDateWAPADMH(),getStudent().isDateWAPD(),getStudent().isDateWAPADMD(),getStudent().isDateWAPM(),getStudent().isDateWAPADMM(),getStudent().isDateWAPS(),getStudent().isDateWAPADMS());
			this.qualDescList = quals.getQualDescs();
			//return quals.getQualDescs();
			return this.qualDescList;
		}

		//log.debug("StudentRegistrationForm - getQualDescList");
		return qualDescList;
	}
	public ArrayList<String> getQualCodeDescList() throws Exception {
		  qualCodeDescList = new ArrayList<String>();
		  //log.debug("size :"+qualCodeDescList.size() );
		  ArrayList<String> codes = this.qualCodeList==null||this.qualCodeList.size()==0  ? getQualCodeList() : this.qualCodeList;
		  ArrayList<String> descs = this.qualDescList==null||this.qualDescList.size()==0  ? getQualDescList() : this.qualDescList;
		  
		  for(int i =0;i<codes.size()&&i<descs.size();i++){
		   qualCodeDescList.add(codes.get(i)+"-"+descs.get(i));
		   //log.debug(getQualCodeList().get(i)+"-"+getQualDescList().get(i));
		  }
		  //log.debug("size :"+qualCodeDescList.size() );
		  //log.debug("StudentRegistrationForm - getQualCodeDescList");
		  return qualCodeDescList;
	}
	public void setQualCodeDescList(ArrayList<String> qualCodeDescList) throws Exception {
		  this.qualCodeDescList = qualCodeDescList;
	}

	public ArrayList<String> getQualCodeList2() throws Exception {
		
		ApplyForStudentNumberQueryDAO dao = new ApplyForStudentNumberQueryDAO();
		
		StudySelected selected = dao.queryStudySelected(getStudent().getNumber(),getStudent().getAcademicYear(),getStudent().getAcademicPeriod());
		if(selected !=null ){
			Qualifications quals = dao.getQualifications(getStudent().getNumber(), getStudent().getStuExist(), getStudent().getAcademicYear(), getStudent().getAcademicPeriod(), selCategoryCode2, getLoginSelectMain(), getAdminStaff().isAdmin(),getStudent().isDateWAPU(),getStudent().isDateWAPRU(),getStudent().isDateWAPADMU(),getStudent().isDateWAPH(),getStudent().isDateWAPRH(),getStudent().isDateWAPADMH(),getStudent().isDateWAPD(),getStudent().isDateWAPADMD(),getStudent().isDateWAPM(),getStudent().isDateWAPADMM(),getStudent().isDateWAPS(),getStudent().isDateWAPADMS());
			return quals.getQualCodes();
		}

		return qualCodeList2;
	}
	public void setQualCodeList2(ArrayList<String> qualCodeList2) {
		this.qualCodeList2 = qualCodeList2;
	}
	public ArrayList<String> getQualDescList2() throws Exception {
		
		ApplyForStudentNumberQueryDAO dao = new ApplyForStudentNumberQueryDAO();

		StudySelected selected = dao.queryStudySelected(getStudent().getNumber(),getStudent().getAcademicYear(),getStudent().getAcademicPeriod());
		if(selected !=null ){
			Qualifications quals = dao.getQualifications(getStudent().getNumber(), getStudent().getStuExist(), getStudent().getAcademicYear(), getStudent().getAcademicPeriod(), selCategoryCode2, getLoginSelectMain(), getAdminStaff().isAdmin(),getStudent().isDateWAPU(),getStudent().isDateWAPRU(),getStudent().isDateWAPADMU(),getStudent().isDateWAPH(),getStudent().isDateWAPRH(),getStudent().isDateWAPADMH(),getStudent().isDateWAPD(),getStudent().isDateWAPADMD(),getStudent().isDateWAPM(),getStudent().isDateWAPADMM(),getStudent().isDateWAPS(),getStudent().isDateWAPADMS());
			return quals.getQualDescs();
		}
		return qualDescList2;
	}
	public void setQualDescList2(ArrayList<String> qualDescList2) {
		this.qualDescList2 = qualDescList2;
	}
	public ArrayList<String> getSpecCodeList2() throws Exception {
		
		ApplyForStudentNumberQueryDAO dao = new ApplyForStudentNumberQueryDAO();

		StudySelected selected = dao.queryStudySelected(getStudent().getNumber(),getStudent().getAcademicYear(),getStudent().getAcademicPeriod());
		if(selected !=null ){
			Specializations quals = dao.getSpecializations(selQualCode2,getStudent().getAcademicYear(),getStudent().getAcademicPeriod());
			return quals.getSpecCodes();
		}
		return specCodeList2;
	}
	public ArrayList<String> getSpecCodeDescList() throws Exception {
		  specCodeDescList = new ArrayList<String>();
		  
		  ArrayList<String> codes = this.specCodeList==null || this.specCodeList.size()==0 ? getSpecCodeList() : this.specCodeList;
		  ArrayList<String> descs = this.specDescList==null || this.specDescList.size()==0 ? getSpecDescList() : this.specDescList;
		  
		  for(int i =0;i<codes.size()&&i<descs.size();i++){
		   specCodeDescList.add(codes.get(i)+"-"+descs.get(i));
		  }
		  //log.debug("StudentRegistrationForm - getSpecCodeDescList");
		  return specCodeDescList;
	}
	public void setSpecCodeDescList(ArrayList<String> specCodeDescList) throws Exception {
		  this.specCodeDescList = specCodeDescList;
	}
	public void setSpecCodeList1(ArrayList<String> specCodeList1) {
		this.specCodeList1 = specCodeList1;
	}
	public ArrayList<String> getSpecDescList1() throws Exception {
		
		ApplyForStudentNumberQueryDAO dao = new ApplyForStudentNumberQueryDAO();

		StudySelected selected = dao.queryStudySelected(getStudent().getNumber(),getStudent().getAcademicYear(),getStudent().getAcademicPeriod());
		if(selected !=null ){
			Specializations quals = dao.getSpecializations(selQualCode1,getStudent().getAcademicYear(),getStudent().getAcademicPeriod());
			return quals.getSpecDescs();
		}
		return specDescList1;
	}
	public void setSpecDescList1(ArrayList<String> specDescList1) {
		this.specDescList1 = specDescList1;
	}
	public void setSpecCodeList2(ArrayList<String> specCodeList2) {
		this.specCodeList2 = specCodeList2;
	}
	public ArrayList<String> getSpecDescList2() throws Exception {
		
		ApplyForStudentNumberQueryDAO dao = new ApplyForStudentNumberQueryDAO();

		StudySelected selected = dao.queryStudySelected(getStudent().getNumber(),getStudent().getAcademicYear(),getStudent().getAcademicPeriod());
		if(selected !=null ){
			Specializations quals = dao.getSpecializations(selQualCode2,getStudent().getAcademicYear(),getStudent().getAcademicPeriod());
			return quals.getSpecDescs();
		}
		return specDescList2;
	}
	public void setSpecDescList2(ArrayList<String> specDescList2) {
		this.specDescList2 = specDescList2;
	}
	public String getSelQualCode() {
		return selQualCode;
	}

	public void setSelQualCode(String selQualCode) {
		this.selQualCode = selQualCode;
	}

	public String getSelQualPrevCode() {
		return selQualPrevCode;
	}

	public void setSelQualPrevCode(String selQualPrevCode) {
		this.selQualPrevCode = selQualPrevCode;
	}
	public void setSpecDescList(ArrayList<String> specDescList) {
		this.specDescList = specDescList;
	}

	public void setCategoryCodeList(ArrayList<String> categoryCodeList) {
		this.categoryCodeList = categoryCodeList;
	}

	public void setCategoryDescList(ArrayList<String> categoryDescList) {
		this.categoryDescList = categoryDescList;
	}

	public void setQualCodeList(ArrayList<String> qualCodeList) {
		this.qualCodeList = qualCodeList;
	}

	public void setQualDescList(ArrayList<String> qualDescList) {
		this.qualDescList = qualDescList;
	}
	
	public String getSelected() {
		return selected;
	}

	public void setSelected(String selected) {
		this.selected = selected;
	}

	public String getSelCategoryCode() {
		return selCategoryCode;
	}
	public void setSelCategoryCode(String selCategoryCode) {
		this.selCategoryCode = selCategoryCode;
	}
	public String getSelCategoryCode1() {
		return selCategoryCode1;
	}
	public void setSelCategoryCode1(String selCategoryCode1) {
		this.selCategoryCode1 = selCategoryCode1;
	}
	public String getSelQualCode1() {
		return selQualCode1;
	}
	public void setSelQualCode1(String selQualCode1) {
		this.selQualCode1 = selQualCode1;
	}
	public String getSelSpecCode1() {
		return selSpecCode1;
	}
	public void setSelSpecCode1(String selSpecCode1) {
		this.selSpecCode1 = selSpecCode1;
	}
	public String getSelCategoryCode2() {
		return selCategoryCode2;
	}
	public void setSelCategoryCode2(String selCategoryCode2) {
		this.selCategoryCode2 = selCategoryCode2;
	}
	public String getSelQualCode2() {
		return selQualCode2;
	}
	public void setSelQualCode2(String selQualCode2) {
		this.selQualCode2 = selQualCode2;
	}
	public String getSelSpecCode2() {
		return selSpecCode2;
	}
	public void setSelSpecCode2(String selSpecCode2) {
		this.selSpecCode2 = selSpecCode2;
	}
	public ArrayList<String> getQualCodeDescList1() throws Exception {
		  qualCodeDescList1 = new ArrayList<String>();
		  //log.debug("size :"+qualCodeDescList1.size() );
		  ArrayList<String> codes = this.qualCodeList1==null||this.qualCodeList1.size()==0  ? getQualCodeList1() : this.qualCodeList1;
		  ArrayList<String> descs = this.qualDescList1==null||this.qualDescList1.size()==0  ? getQualDescList1() : this.qualDescList1;
		  
		  for(int i =0;i<codes.size()&&i<descs.size();i++){
		   qualCodeDescList1.add(codes.get(i)+"-"+descs.get(i));
		    
		  }
		  //log.debug("size :"+qualCodeDescList1.size() );
		  return qualCodeDescList1;
	}
	public void setQualCodeDescList1(ArrayList<String> qualCodeDescList1) throws Exception {
		  this.qualCodeDescList1 = qualCodeDescList1;
	}
	public ArrayList<String> getQualCodeDescList2() throws Exception {
		  qualCodeDescList2 = new ArrayList<String>();
		  //log.debug("size :"+qualCodeDescList2.size() );
		  ArrayList<String> codes = this.qualCodeList2==null||this.qualCodeList2.size()==0  ? getQualCodeList2() : this.qualCodeList2;
		  ArrayList<String> descs = this.qualDescList2==null||this.qualDescList2.size()==0  ? getQualDescList2() : this.qualDescList2;
		  
		  for(int i =0;i<codes.size()&&i<descs.size();i++){
		   qualCodeDescList2.add(codes.get(i)+"-"+descs.get(i));
		    
		  }
		  //log.debug("size :"+qualCodeDescList2.size() );
		  return qualCodeDescList2;
	}

	public void setQualCodeDescList2(ArrayList<String> qualCodeDescList2) throws Exception {
		  this.qualCodeDescList2 = qualCodeDescList2;
	}
	public String getSelSpecCode() {
		return selSpecCode;
	}

	public void setSelSpecCode(String selSpecCode) {
		this.selSpecCode = selSpecCode;
	}

	public ArrayList<String> getSpecCodeList() throws Exception {
		ApplyForStudentNumberQueryDAO dao = new ApplyForStudentNumberQueryDAO();
		StudySelected selected = dao.queryStudySelected(getStudent().getNumber(),getStudent().getAcademicYear(),getStudent().getAcademicPeriod());

		if(selected !=null ){
			Specializations quals = dao.getSpecializations(selQualCode,getStudent().getAcademicYear(),getStudent().getAcademicPeriod());
			this.specCodeList = quals.getSpecCodes();
			//return quals.getSpecCodes();
			return this.specCodeList;
		}
		//log.debug("StudentRegistrationForm - getSpecCodeList");
		return specCodeList;
	}

	public void setSpecCodeList(ArrayList<String> specCodeList) {
		this.specCodeList = specCodeList;
	}

	public ArrayList<String> getSpecDescList() throws Exception {
		ApplyForStudentNumberQueryDAO dao = new ApplyForStudentNumberQueryDAO();
		StudySelected selected = dao.queryStudySelected(getStudent().getNumber(),getStudent().getAcademicYear(),getStudent().getAcademicPeriod());

		if(selected !=null ){
			Specializations quals = dao.getSpecializations(selQualCode,getStudent().getAcademicYear(),getStudent().getAcademicPeriod());
			this.specDescList = quals.getSpecDescs();
			//return quals.getSpecDescs();
			return this.specDescList;
		}
		//log.debug("StudentRegistrationForm - getSpecDescList");
		return specDescList;
	}
	public ArrayList<String> getSpecCodeDescList1() throws Exception {
		  specCodeDescList1 = new ArrayList<String>();
		  
		  ArrayList<String> codes = this.specCodeList1==null || this.specCodeList1.size()==0 ? getSpecCodeList1() : this.specCodeList1;
		  ArrayList<String> descs = this.specDescList1==null || this.specDescList1.size()==0 ? getSpecDescList1() : this.specDescList1;
		  
		  for(int i =0;i<codes.size()&&i<descs.size();i++){
		   specCodeDescList1.add(codes.get(i)+"-"+descs.get(i));
		  }
		  return specCodeDescList1;
	}

	public void setSpecCodeDescList1(ArrayList<String> specCodeDescList1) throws Exception {
		  this.specCodeDescList1 = specCodeDescList1;
	}
	public ArrayList<String> getSpecCodeDescList2() throws Exception {
		  specCodeDescList2 = new ArrayList<String>();
		  
		  ArrayList<String> codes = this.specCodeList2==null || this.specCodeList2.size()==0 ? getSpecCodeList2() : this.specCodeList2;
		  ArrayList<String> descs = this.specDescList2==null || this.specDescList2.size()==0 ? getSpecDescList2() : this.specDescList2;
		  
		  for(int i =0;i<codes.size()&&i<descs.size();i++){
		   specCodeDescList2.add(codes.get(i)+"-"+descs.get(i));
		  }
		  return specCodeDescList2;
	}

	public void setSpecCodeDescList2(ArrayList<String> specCodeDescList2) throws Exception {
		  this.specCodeDescList2 = specCodeDescList2;
	}
	public String getExistQual() throws Exception {
		ApplyForStudentNumberQueryDAO dao = new ApplyForStudentNumberQueryDAO();
		String selected = dao.getExistQual(getStudent().getNumber());
		if(selected !=null){
			existQual = dao.getExistQual(getStudent().getNumber());

		}else{
			existQual = "";
		}
		return existQual;
	}
	public String getExistSpec() throws Exception {
		ApplyForStudentNumberQueryDAO dao = new ApplyForStudentNumberQueryDAO();
		String selected = dao.getExistSpec(getStudent().getNumber(),getStudent().getAcademicYear(),getStudent().getAcademicPeriod());
		if(selected !=null ){
			existSpec = dao.getExistSpec(getStudent().getNumber(),getStudent().getAcademicYear(),getStudent().getAcademicPeriod());

		}else{
			existSpec = "";
		}
		return existSpec;
	}

	public void loadData(){
		//log.debug("StudentRegistrationForm - Upload - LoadData");
		try {
			//log.debug("StudentRegistrationForm - Upload - getAllRequiredDocs - Try: " + getStudent().getNumber() + " : " + getStudent().getAcademicYear() + " : " + getStudent().getAcademicPeriod() + " : Y : " + getStudent().getMatrix());
			DocDao docDao = new DocDao();
			requiredDocs = docDao.getAllRequiredDocs(getStudent().getNumber(),getStudent().getAcademicYear(),getStudent().getAcademicPeriod(),"Y",getStudent().getMatrix(),getStudent().getStuExist(), getLoginSelectMain());
		} catch (Exception e1) {
			//log.debug("StudentRegistrationForm - Upload - getAllRequiredDocs - Catch: " + getStudent().getNumber() + " : " + getStudent().getAcademicYear() + " : " + getStudent().getAcademicPeriod() + " : Y : " + getStudent().getMatrix());
			e1.printStackTrace();
		}
		try {
			//log.debug("StudentRegistrationForm - Upload - getAllOptionalDocs - Try: " + getStudent().getNumber() + " : " + getStudent().getAcademicYear() + " : " + getStudent().getAcademicPeriod() + " : Y : " + getStudent().getMatrix());
			DocDao docDao = new DocDao();
			optionalDocs = docDao.getAllOptionalDocs(getStudent().getNumber(),getStudent().getAcademicYear(),getStudent().getAcademicPeriod(),getStudent().getMatrix(),getStudent().getStuExist());
		} catch (Exception e1) {
			//log.debug("StudentRegistrationForm - Upload - getAllOptionalDocs - Catch: " + getStudent().getNumber() + " : " + getStudent().getAcademicYear() + " : " + getStudent().getAcademicPeriod() + " : " + getStudent());
			e1.printStackTrace();
		}

		//log.debug("StudentRegistrationForm - Upload - Checking RequiredDocs............");
		if (requiredDocs != null) {
			//log.debug("StudentRegistrationForm - Upload - requiredDocs <> null............");
			int fileSize = requiredDocs.size();
			//log.debug("StudentRegistrationForm - Upload - Instantiate Optional File Bean");
			requiredFileBeans = new FileBean[fileSize];
			
			int index = 0;
			for (Doc doc : requiredDocs) {
				FileBean fb = new FileBean();
				fb.setDoc(doc);
				requiredFileBeans[index++] = fb;
				try {
					SavedDocDao savedDocDao = new SavedDocDao();
					fb.setUploaded(savedDocDao.getSavedDocByDoc(doc.getDocCode(),getStudent().getNumber(),getStudent().getAcademicYear(),getStudent().getAcademicPeriod()));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		//log.debug("StudentRegistrationForm - Upload - Checking RequiredDocs............");
		if (optionalDocs != null) {
			//log.debug("StudentRegistrationForm - Upload - optionalDocs <> null............");

			optionCodes.clear();
			for (Doc d : optionalDocs) {
				//log.debug("StudentRegistrationForm - Upload - Getting optionalDoc Codes="+d.getDocCode());
				optionCodes.add(d.getDocCode());
			}
		}
		if (optionalDocs != null) {
			optionLabels.clear();
			for (Doc d : optionalDocs) {
				//log.debug("StudentRegistrationForm - Upload - Getting optionalDoc Labels="+d.getDocDescription());
				optionLabels.add(d.getDocDescription());
			}
		}
		
		//log.debug("StudentRegistrationForm - Upload - Instantiate Optional File Bean");
		optionFileBean = new FileBean();
		//optional docs showing
   		   	desc.clear();
		    map.clear();
		    //log.debug("StudentRegistrationForm - Upload - Map Cleared");
		   try {
			   SavedDocDao savedDocDao = new SavedDocDao();
			   //log.debug("StudentRegistrationForm - Upload - Getting SavedDocDAOInfo");
			   savedDocDao.getAllNonRequiredDocInfo(desc, map, getStudent().getNumber(),getStudent().getAcademicYear(),getStudent().getAcademicPeriod(),getStudent().getStuExist(),getStudent().getMatrix());
		   } catch (Exception e) {
			   e.printStackTrace();
		   }
		 //log.debug("StudentRegistrationForm - Upload - Done");
	}

	public void reLoad() throws Exception{
		desc.clear();
		map.clear();
		SavedDocDao savedDocDao = new SavedDocDao();
		//log.debug("StudentRegistrationForm - reLoad - Reloading uploaded docs..");
		savedDocDao.getAllNonRequiredDocInfo(desc, map, getStudent().getNumber(),getStudent().getAcademicYear(),getStudent().getAcademicPeriod(),getStudent().getStuExist(),getStudent().getMatrix());
		for(FileBean fb : requiredFileBeans){
			//log.debug("StudentRegistrationForm - reLoad - Settigng FileBean Uploaded..");
			fb.setUploaded(savedDocDao.getSavedDocByDoc(fb.getDoc().getDocCode(),getStudent().getNumber(),getStudent().getAcademicYear(),getStudent().getAcademicPeriod()));
		}
		//log.debug("StudentRegistrationForm - reLoad - Done");
	}

	public boolean isHiddenButtonUpload() {
		//log.debug("StudentRegistrationForm - isHiddenButtonUpload - Start");
		  try {
			  for(FileBean fb : requiredFileBeans) {
				  if(fb.getUploaded() == null ||fb.getUploaded().size() == 0 || fb.getUploaded().isEmpty()){
					  //log.debug("StudentRegistrationForm - isHiddenButtonUpload - All Files not uploaded, hide button");
					  return true;
				  }
			 }
			  return false;
		  } catch (Exception e) {
			  e.printStackTrace();
		  }
		  //log.debug("StudentRegistrationForm - isHiddenButtonUpload - Done");
	  return hiddenButtonUpload;
	}

	public void setHiddenButtonUpload(boolean hiddenButtonUpload) {
		//log.debug("StudentRegistrationForm - SetHiddenButtonUpload");
		  this.hiddenButtonUpload = hiddenButtonUpload;
	}

	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		
		ActionErrors errors = new ActionErrors();
		//log.debug("StudentRegistrationForm - Validate................");
		return errors;
	}

	public void setFileBeans(FileBean[] requiredFileBeans) {
		//log.debug("StudentRegistrationForm - setFileBeans");
		this.requiredFileBeans = requiredFileBeans;
		//log.debug("StudentRegistrationForm - setFileBeans - Done");
	}

	public FileBean[] getFileBeans() {
		//log.debug("StudentRegistrationForm - getFileBeans");
		return requiredFileBeans;
	}

	public List<Doc> getRequiredDocs() {
		//log.debug("StudentRegistrationForm - getRequiredDocs");
		return requiredDocs;
	}

	public void setRequiredDocs(List<Doc> requiredDocs) {
		//log.debug("StudentRegistrationForm - setRequiredDocs");
		this.requiredDocs = requiredDocs;
	}

	public List<Doc> getOptionalDocs() {
		//log.debug("StudentRegistrationForm - getOptionalDocs");
		return optionalDocs;
	}

	public void setOptionalDocs(List<Doc> optionalDocs) {
		//log.debug("StudentRegistrationForm - setOptionalDocs");
		this.optionalDocs = optionalDocs;
	}

	public List<String> getOptionLabels() {
		//log.debug("StudentRegistrationForm - getOptionLabels");
		return optionLabels;
	}

	public void setOptionLabels(List<String> optionLabels) {
		//log.debug("StudentRegistrationForm - setOptionLabels");
		this.optionLabels = optionLabels;
	}

	public List<String> getOptionCodes() {
		//log.debug("StudentRegistrationForm - getOptionCodes");
		return optionCodes;
	}

	public void setOptionCodes(List<String> optionValues) {
		//log.debug("StudentRegistrationForm - setOptionCodes");
		this.optionCodes = optionValues;
	}

	public FileBean getOptionFileBean() {
		//log.debug("StudentRegistrationForm - getOptionFileBean");
		return optionFileBean;
	}

	public void setOptionFileBean(FileBean optionFileBean) {
		//log.debug("StudentRegistrationForm - setOptionFileBean");
		this.optionFileBean = optionFileBean;
	}

	public List<String> getDesc() {
		//log.debug("StudentRegistrationForm - getDesc");
		return desc;
	}

	public void setDesc(List<String> desc) {
		//log.debug("StudentRegistrationForm - setDesc");
		this.desc = desc;
	}

	public Map<String, List<String>> getMap() {
		//log.debug("StudentRegistrationForm - getMap");
		return map;
	}

	public void setMap(Map<String, List<String>> map) {
		//log.debug("StudentRegistrationForm - setMap");
		this.map = map;
	}
	public String getDocMsg() {
		return docMsg;
	}

	public void setDocMsg(String docMsg) {
		this.docMsg = docMsg;
	}
	
/*
	public String getSavedCategory() {
		ApplyForStudentNumberQueryDAO dao = new ApplyForStudentNumberQueryDAO();

		try {
			savedCategory = dao.getXMLSelected("catCode2", "2",getStudent().getNumber(),getStudent().getAcademicYear(),getStudent().getAcademicPeriod(), "getSavedCategory");
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		//log.debug("StudentRegistrationForm - getSavedCategory: " + savedCategory);
		return savedCategory;
	}

	public void setSavedCategory(String savedCategory) {
		this.savedCategory = savedCategory;
	}
*/
	
	public String getSavedCategory1() {
		ApplyForStudentNumberQueryDAO dao = new ApplyForStudentNumberQueryDAO();

		try {
			if (getStudent().isStuSLP() && getStudent().isStuapq() ){
				savedCategory1 = dao.getSLPQual(getStudent().getNumber(),getStudent().getAcademicYear(),getStudent().getAcademicPeriod(), "1", "CAT", "getSavedCategory1");
			}else{
				savedCategory1 = dao.getXMLSelected("catCode1", "1",getStudent().getNumber(),getStudent().getAcademicYear(),getStudent().getAcademicPeriod(), "getSavedCategory1");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		//log.debug("StudentRegistrationForm - getSavedCategory1: " + savedCategory1);
		return savedCategory1;
	}

	public void setSavedCategory1(String savedCategory1) {
		this.savedCategory1 = savedCategory1;
	}
	
	public String getSavedCategory2() {
		ApplyForStudentNumberQueryDAO dao = new ApplyForStudentNumberQueryDAO();

		try {
			savedCategory2 = dao.getXMLSelected("catCode2", "2",getStudent().getNumber(),getStudent().getAcademicYear(),getStudent().getAcademicPeriod(), "getSavedCategory2");
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		//log.debug("StudentRegistrationForm - getSavedCategory2: " + savedCategory2);
		return savedCategory2;
	}

	public void setSavedCategory2(String savedCategory2) {
		this.savedCategory2 = savedCategory2;
	}

/*
	public String getSavedQual() {
		ApplyForStudentNumberQueryDAO dao = new ApplyForStudentNumberQueryDAO();

		try {
			savedQual = dao.getXMLSelected("qualCode2", "2",getStudent().getNumber(),getStudent().getAcademicYear(),getStudent().getAcademicPeriod(), "getSavedQual");
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		//log.debug("StudentRegistrationForm - getSavedQual: " + savedQual);
		return savedQual;
	}

	public void setSavedQual(String savedQual) {
		this.savedQual = savedQual;
	}
*/
	public String getSavedQual1() {
		ApplyForStudentNumberQueryDAO dao = new ApplyForStudentNumberQueryDAO();

		try {
			if (getStudent().isStuSLP() && getStudent().isStuapq() ){
				savedQual1 = dao.getSLPQual(getStudent().getNumber(),getStudent().getAcademicYear(),getStudent().getAcademicPeriod(), "1", "QUAL", "getSavedQual1");
			}else{
				savedQual1 = dao.getXMLSelected("qualCode1", "1",getStudent().getNumber(),getStudent().getAcademicYear(),getStudent().getAcademicPeriod(), "getSavedQual1");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		//log.debug("StudentRegistrationForm - getSavedQual1: " + savedQual1);
		return savedQual1;
	}

	public void setSavedQual1(String savedQual1) {
		this.savedQual1 = savedQual1;
	}

	public String getSavedQual2() {
		ApplyForStudentNumberQueryDAO dao = new ApplyForStudentNumberQueryDAO();

		try {
			savedQual2 = dao.getXMLSelected("qualCode2", "2",getStudent().getNumber(),getStudent().getAcademicYear(),getStudent().getAcademicPeriod(), "getSavedQual2");
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		//log.debug("StudentRegistrationForm - getSavedQual2: " + savedQual2);
		return savedQual2;
	}

	public void setSavedQual2(String savedQual2) {
		this.savedQual2 = savedQual2;
	}
/*
	public String getSavedSpec() {
		ApplyForStudentNumberQueryDAO dao = new ApplyForStudentNumberQueryDAO();

		try {
			savedSpec = dao.getXMLSelected("specCode2", "2",getStudent().getNumber(),getStudent().getAcademicYear(),getStudent().getAcademicPeriod(), "getSavedSpec");
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		//log.debug("StudentRegistrationForm - getSavedSpec: " + savedSpec);
		return savedSpec;
	}

	public void setSavedSpec(String savedSpec) {
		this.savedSpec = savedSpec;
	}
*/
	public String getSavedSpec1() {
		ApplyForStudentNumberQueryDAO dao = new ApplyForStudentNumberQueryDAO();

		try {
			if (getStudent().isStuSLP() && getStudent().isStuapq() ){
				savedSpec1 = dao.getSLPQual(getStudent().getNumber(),getStudent().getAcademicYear(),getStudent().getAcademicPeriod(), "1", "SPEC", "getSavedSpec1");
			}else{
				savedSpec1 = dao.getXMLSelected("specCode1", "1",getStudent().getNumber(),getStudent().getAcademicYear(),getStudent().getAcademicPeriod(), "getSavedSpec1");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		//log.debug("StudentRegistrationForm - getSavedSpec1: " + savedSpec1);
		return savedSpec1;
	}

	public void setSavedSpec1(String savedSpec1) {
		this.savedSpec1 = savedSpec1;
	}

	public String getSavedSpec2() {
		ApplyForStudentNumberQueryDAO dao = new ApplyForStudentNumberQueryDAO();

		try {
			savedSpec2 = dao.getXMLSelected("specCode2", "2",getStudent().getNumber(),getStudent().getAcademicYear(),getStudent().getAcademicPeriod(), "getSavedSpec2");
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		//log.debug("StudentRegistrationForm - getSavedSpec2: " + savedSpec2);
		return savedSpec2;
	}

	public void setSavedSpec2(String savedSpec2) {
		this.savedSpec2 = savedSpec2;
	}

	public String getFileNewName() {
		return fileNewName;
	}

	public void setFileNewName(String fileNewName) {
		this.fileNewName = fileNewName;
	}

	public String getSelQualCodeDesc() {
		return selQualCodeDesc;
	}

	public void setSelQualCodeDesc(String selQualCodeDesc) {
		this.selQualCodeDesc = selQualCodeDesc;
	}

	public String getSelQualCode1Desc() {
		return selQualCode1Desc;
	}

	public void setSelQualCode1Desc(String selQualCode1Desc) {
		this.selQualCode1Desc = selQualCode1Desc;
	}

	public String getSelQualCode2Desc() {
		return selQualCode2Desc;
	}

	public void setSelQualCode2Desc(String selQualCode2Desc) {
		this.selQualCode2Desc = selQualCode2Desc;
	}

	public String getSelSpecCodeDesc() {
		return selSpecCodeDesc;
	}

	public void setSelSpecCodeDesc(String selSpecCodeDesc) {
		this.selSpecCodeDesc = selSpecCodeDesc;
	}

	public String getSelSpecCode1Desc() {
		return selSpecCode1Desc;
	}

	public void setSelSpecCode1Desc(String selSpecCode1Desc) {
		this.selSpecCode1Desc = selSpecCode1Desc;
	}

	public String getSelSpecCode2Desc() {
		return selSpecCode2Desc;
	}

	public void setSelSpecCode2Desc(String selSpecCode2Desc) {
		this.selSpecCode2Desc = selSpecCode2Desc;
	}

	public FileBean[] getRequiredFileBeans() {
		return requiredFileBeans;
	}

	public void setRequiredFileBeans(FileBean[] requiredFileBeans) {
		this.requiredFileBeans = requiredFileBeans;
	}

	public void setSchoolCode(String schoolCode) {
		this.schoolCode = schoolCode;
	}

	public void setExistQual(String existQual) {
		this.existQual = existQual;
	}

	public void setExistSpec(String existSpec) {
		this.existSpec = existSpec;
	}

	public void setQualPrevCodeDescList(ArrayList<String> qualPrevCodeDescList) {
		this.qualPrevCodeDescList = qualPrevCodeDescList;
	}

	public String getQualStatus1() {
		return qualStatus1;
	}

	public void setQualStatus1(String qualStatus1) {
		this.qualStatus1 = qualStatus1;
	}

	public String getQualStatus2() {
		return qualStatus2;
	}

	public void setQualStatus2(String qualStatus2) {
		this.qualStatus2 = qualStatus2;
	}
	
	public String getQualStatusCode1() {
		return qualStatusCode1;
	}
	
	public void setQualStatusCode1(String qualStatusCode1) {
		this.qualStatusCode1 = qualStatusCode1;
	}
	
	public String getQualStatusCode2() {
		return qualStatusCode2;
	}

	public void setQualStatusCode2(String qualStatusCode2) {
		this.qualStatusCode2 = qualStatusCode2;
	}

	public String getQualStatus1Reason() {
		return qualStatus1Reason;
	}

	public void setQualStatus1Reason(String qualStatus1Reason) {
		this.qualStatus1Reason = qualStatus1Reason;
	}

	public String getQualStatus2Reason() {
		return qualStatus2Reason;
	}

	public void setQualStatus2Reason(String qualStatus2Reason) {
		this.qualStatus2Reason = qualStatus2Reason;
	}

	public String getOfferRadio() {
		return offerRadio;
	}

	public void setOfferRadio(String offerRadio) {
		this.offerRadio = offerRadio;
	}

	public String getExistQualDesc() {
		ApplyForStudentNumberQueryDAO dao = new ApplyForStudentNumberQueryDAO();
		String selected;
		try {
			selected = dao.getQualDesc(existQual);
			if(selected !=null){
				existQualDesc = selected;
			}else{
				existQualDesc = "";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return existQualDesc;
	}

	public String getOfferQual1() {
		return offerQual1;
	}

	public void setOfferQual1(String offerQual1) {
		this.offerQual1 = offerQual1;
	}

	public String getOfferQual2() {
		return offerQual2;
	}

	public void setOfferQual2(String offerQual2) {
		this.offerQual2 = offerQual2;
	}

	public String getOfferSpec1() {
		return offerSpec1;
	}

	public void setOfferSpec1(String offerSpec1) {
		this.offerSpec1 = offerSpec1;
	}

	public String getOfferSpec2() {
		return offerSpec2;
	}

	public void setOfferSpec2(String offerSpec2) {
		this.offerSpec2 = offerSpec2;
	}

	public void setExistQualDesc(String existQualDesc) {
		this.existQualDesc = existQualDesc;
	}

	public String getExistSpecDesc() {
		ApplyForStudentNumberQueryDAO dao = new ApplyForStudentNumberQueryDAO();
		String selected;
		try {
			selected = dao.getSpecDesc(existQual,existSpec);
			if(selected !=null ){
				existSpecDesc = selected;
			}else{
				existSpecDesc = "";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return existSpecDesc;
	}

	public void setExistSpecDesc(String existSpecDesc) {
		this.existSpecDesc = existSpecDesc;
	}

	public String getSelectHEMain() {
		return selectHEMain;
	}

	public void setSelectHEMain(String selectHEMain) {
		this.selectHEMain = selectHEMain;
	}

	public String getSelectMatric() {
		return selectMatric;
	}

	public void setSelectMatric(String selectMatric) {
		this.selectMatric = selectMatric;
	}

    public int getApsScore() {
		return apsScore;
	}

	public void setApsScore(int apsScore) {
		this.apsScore = apsScore;
	}

	public int getApsSize() {
		return apsSize;
	}

	public void setApsSize(int apsSize) {
		this.apsSize = apsSize;
	}

	public List<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<Subject> subjects) {
        this.subjects = subjects;
    }
    
	/**
	 * Returns the completed Unisa Qualifications.
	 */
	public ArrayList getQualUnisa() {
		return qualUnisa;
	}

	/**
	 * Set the completed Unisa Qualifications.
	 * @param Qualifications, The Qualifications to set
	 */
	public void setQualUnisa(ArrayList qualUnisa) {
		this.qualUnisa = qualUnisa;
	}
	public ArrayList getQualArray() {
		return qualArray;
	}

	public void setQualArray(ArrayList qualArray) {
		this.qualArray = qualArray;
	}

	/**
	 * Returns the completed Qualifications.
	 */
	public HistoryOther getQualOther() {
		return qualOther;
	}

	/**
	 * Set the completed Qualifications.
	 * @param Qualifications, The Qualifications to set
	 */
	public void setQualOther(HistoryOther qualOther) {
		this.qualOther = qualOther;
	}
	
    
    public boolean isQualUnisaFound() {
		return qualUnisaFound;
	}

	public void setQualUnisaFound(boolean qualUnisaFound) {
		this.qualUnisaFound = qualUnisaFound;
	}

	public boolean isQualIDMatch() {
		return qualIDMatch;
	}

	public void setQualIDMatch(boolean qualIDMatch) {
		this.qualIDMatch = qualIDMatch;
	}

	public boolean isQualAPSMatch() {
		return qualAPSMatch;
	}

	public void setQualAPSMatch(boolean qualAPSMatch) {
		this.qualAPSMatch = qualAPSMatch;
	}

	public String getApsSubjectName1() {
		return apsSubjectName1;
	}

	public void setApsSubjectName1(String apsSubjectName1) {
		this.apsSubjectName1 = apsSubjectName1;
	}

	public String getApsSubjectName2() {
		return apsSubjectName2;
	}

	public void setApsSubjectName2(String apsSubjectName2) {
		this.apsSubjectName2 = apsSubjectName2;
	}

	public String getApsSubjectName3() {
		return apsSubjectName3;
	}

	public void setApsSubjectName3(String apsSubjectName3) {
		this.apsSubjectName3 = apsSubjectName3;
	}

	public String getApsSubjectName4() {
		return apsSubjectName4;
	}

	public void setApsSubjectName4(String apsSubjectName4) {
		this.apsSubjectName4 = apsSubjectName4;
	}

	public String getApsSubjectName5() {
		return apsSubjectName5;
	}

	public void setApsSubjectName5(String apsSubjectName5) {
		this.apsSubjectName5 = apsSubjectName5;
	}

	public String getApsSubjectName6() {
		return apsSubjectName6;
	}

	public void setApsSubjectName6(String apsSubjectName6) {
		this.apsSubjectName6 = apsSubjectName6;
	}

	public String getApsSubjectName7() {
		return apsSubjectName7;
	}

	public void setApsSubjectName7(String apsSubjectName7) {
		this.apsSubjectName7 = apsSubjectName7;
	}

	public String getApsSubjectName8() {
		return apsSubjectName8;
	}

	public void setApsSubjectName8(String apsSubjectName8) {
		this.apsSubjectName8 = apsSubjectName8;
	}

	public String getApsSubjectName9() {
		return apsSubjectName9;
	}

	public void setApsSubjectName9(String apsSubjectName9) {
		this.apsSubjectName9 = apsSubjectName9;
	}

	public String getApsSubjectName10() {
		return apsSubjectName10;
	}

	public void setApsSubjectName10(String apsSubjectName10) {
		this.apsSubjectName10 = apsSubjectName10;
	}

	public String getApsSubjectName11() {
		return apsSubjectName11;
	}

	public void setApsSubjectName11(String apsSubjectName11) {
		this.apsSubjectName11 = apsSubjectName11;
	}

	public String getApsSubjectName12() {
		return apsSubjectName12;
	}

	public void setApsSubjectName12(String apsSubjectName12) {
		this.apsSubjectName12 = apsSubjectName12;
	}

	public String getApsSubjectGrade1() {
		return apsSubjectGrade1;
	}

	public void setApsSubjectGrade1(String apsSubjectGrade1) {
		this.apsSubjectGrade1 = apsSubjectGrade1;
	}

	public String getApsSubjectGrade2() {
		return apsSubjectGrade2;
	}

	public void setApsSubjectGrade2(String apsSubjectGrade2) {
		this.apsSubjectGrade2 = apsSubjectGrade2;
	}

	public String getApsSubjectGrade3() {
		return apsSubjectGrade3;
	}

	public void setApsSubjectGrade3(String apsSubjectGrade3) {
		this.apsSubjectGrade3 = apsSubjectGrade3;
	}

	public String getApsSubjectGrade4() {
		return apsSubjectGrade4;
	}

	public void setApsSubjectGrade4(String apsSubjectGrade4) {
		this.apsSubjectGrade4 = apsSubjectGrade4;
	}

	public String getApsSubjectGrade5() {
		return apsSubjectGrade5;
	}

	public void setApsSubjectGrade5(String apsSubjectGrade5) {
		this.apsSubjectGrade5 = apsSubjectGrade5;
	}

	public String getApsSubjectGrade6() {
		return apsSubjectGrade6;
	}

	public void setApsSubjectGrade6(String apsSubjectGrade6) {
		this.apsSubjectGrade6 = apsSubjectGrade6;
	}

	public String getApsSubjectGrade7() {
		return apsSubjectGrade7;
	}

	public void setApsSubjectGrade7(String apsSubjectGrade7) {
		this.apsSubjectGrade7 = apsSubjectGrade7;
	}

	public String getApsSubjectGrade8() {
		return apsSubjectGrade8;
	}

	public void setApsSubjectGrade8(String apsSubjectGrade8) {
		this.apsSubjectGrade8 = apsSubjectGrade8;
	}

	public String getApsSubjectGrade9() {
		return apsSubjectGrade9;
	}

	public void setApsSubjectGrade9(String apsSubjectGrade9) {
		this.apsSubjectGrade9 = apsSubjectGrade9;
	}

	public String getApsSubjectGrade10() {
		return apsSubjectGrade10;
	}

	public void setApsSubjectGrade10(String apsSubjectGrade10) {
		this.apsSubjectGrade10 = apsSubjectGrade10;
	}

	public String getApsSubjectGrade11() {
		return apsSubjectGrade11;
	}

	public void setApsSubjectGrade11(String apsSubjectGrade11) {
		this.apsSubjectGrade11 = apsSubjectGrade11;
	}

	public String getApsSubjectGrade12() {
		return apsSubjectGrade12;
	}

	public void setApsSubjectGrade12(String apsSubjectGrade12) {
		this.apsSubjectGrade12 = apsSubjectGrade12;
	}

	public String getApsSubjectSymbol1() {
		return apsSubjectSymbol1;
	}

	public void setApsSubjectSymbol1(String apsSubjectSymbol1) {
		this.apsSubjectSymbol1 = apsSubjectSymbol1;
	}

	public String getApsSubjectSymbol2() {
		return apsSubjectSymbol2;
	}

	public void setApsSubjectSymbol2(String apsSubjectSymbol2) {
		this.apsSubjectSymbol2 = apsSubjectSymbol2;
	}

	public String getApsSubjectSymbol3() {
		return apsSubjectSymbol3;
	}

	public void setApsSubjectSymbol3(String apsSubjectSymbol3) {
		this.apsSubjectSymbol3 = apsSubjectSymbol3;
	}

	public String getApsSubjectSymbol4() {
		return apsSubjectSymbol4;
	}

	public void setApsSubjectSymbol4(String apsSubjectSymbol4) {
		this.apsSubjectSymbol4 = apsSubjectSymbol4;
	}

	public String getApsSubjectSymbol5() {
		return apsSubjectSymbol5;
	}

	public void setApsSubjectSymbol5(String apsSubjectSymbol5) {
		this.apsSubjectSymbol5 = apsSubjectSymbol5;
	}

	public String getApsSubjectSymbol6() {
		return apsSubjectSymbol6;
	}

	public void setApsSubjectSymbol6(String apsSubjectSymbol6) {
		this.apsSubjectSymbol6 = apsSubjectSymbol6;
	}

	public String getApsSubjectSymbol7() {
		return apsSubjectSymbol7;
	}

	public void setApsSubjectSymbol7(String apsSubjectSymbol7) {
		this.apsSubjectSymbol7 = apsSubjectSymbol7;
	}

	public String getApsSubjectSymbol8() {
		return apsSubjectSymbol8;
	}

	public void setApsSubjectSymbol8(String apsSubjectSymbol8) {
		this.apsSubjectSymbol8 = apsSubjectSymbol8;
	}

	public String getApsSubjectSymbol9() {
		return apsSubjectSymbol9;
	}

	public void setApsSubjectSymbol9(String apsSubjectSymbol9) {
		this.apsSubjectSymbol9 = apsSubjectSymbol9;
	}

	public String getApsSubjectSymbol10() {
		return apsSubjectSymbol10;
	}

	public void setApsSubjectSymbol10(String apsSubjectSymbol10) {
		this.apsSubjectSymbol10 = apsSubjectSymbol10;
	}

	public String getApsSubjectSymbol11() {
		return apsSubjectSymbol11;
	}

	public void setApsSubjectSymbol11(String apsSubjectSymbol11) {
		this.apsSubjectSymbol11 = apsSubjectSymbol11;
	}

	public String getApsSubjectSymbol12() {
		return apsSubjectSymbol12;
	}

	public void setApsSubjectSymbol12(String apsSubjectSymbol12) {
		this.apsSubjectSymbol12 = apsSubjectSymbol12;
	}

	public String getApsSubjectResult1() {
		return apsSubjectResult1;
	}

	public void setApsSubjectResult1(String apsSubjectResult1) {
		this.apsSubjectResult1 = apsSubjectResult1;
	}

	public String getApsSubjectResult2() {
		return apsSubjectResult2;
	}

	public void setApsSubjectResult2(String apsSubjectResult2) {
		this.apsSubjectResult2 = apsSubjectResult2;
	}

	public String getApsSubjectResult3() {
		return apsSubjectResult3;
	}

	public void setApsSubjectResult3(String apsSubjectResult3) {
		this.apsSubjectResult3 = apsSubjectResult3;
	}

	public String getApsSubjectResult4() {
		return apsSubjectResult4;
	}

	public void setApsSubjectResult4(String apsSubjectResult4) {
		this.apsSubjectResult4 = apsSubjectResult4;
	}

	public String getApsSubjectResult5() {
		return apsSubjectResult5;
	}

	public void setApsSubjectResult5(String apsSubjectResult5) {
		this.apsSubjectResult5 = apsSubjectResult5;
	}

	public String getApsSubjectResult6() {
		return apsSubjectResult6;
	}

	public void setApsSubjectResult6(String apsSubjectResult6) {
		this.apsSubjectResult6 = apsSubjectResult6;
	}

	public String getApsSubjectResult7() {
		return apsSubjectResult7;
	}

	public void setApsSubjectResult7(String apsSubjectResult7) {
		this.apsSubjectResult7 = apsSubjectResult7;
	}

	public String getApsSubjectResult8() {
		return apsSubjectResult8;
	}

	public void setApsSubjectResult8(String apsSubjectResult8) {
		this.apsSubjectResult8 = apsSubjectResult8;
	}

	public String getApsSubjectResult9() {
		return apsSubjectResult9;
	}

	public void setApsSubjectResult9(String apsSubjectResult9) {
		this.apsSubjectResult9 = apsSubjectResult9;
	}

	public String getApsSubjectResult10() {
		return apsSubjectResult10;
	}

	public void setApsSubjectResult10(String apsSubjectResult10) {
		this.apsSubjectResult10 = apsSubjectResult10;
	}

	public String getApsSubjectResult11() {
		return apsSubjectResult11;
	}

	public void setApsSubjectResult11(String apsSubjectResult11) {
		this.apsSubjectResult11 = apsSubjectResult11;
	}

	public String getApsSubjectResult12() {
		return apsSubjectResult12;
	}

	public void setApsSubjectResult12(String apsSubjectResult12) {
		this.apsSubjectResult12 = apsSubjectResult12;
	}

	public static Log getLog() {
		return log;
	}

	public static void setLog(Log log) {
		StudentRegistrationForm.log = log;
	}

	public ArrayList<String> getQualPrevCodeList() {
		return qualPrevCodeList;
	}

	public void setQualPrevCodeList(ArrayList<String> qualPrevCodeList) {
		this.qualPrevCodeList = qualPrevCodeList;
	}

	public ArrayList<String> getQualPrevDescList() {
		return qualPrevDescList;
	}

	public void setQualPrevDescList(ArrayList<String> qualPrevDescList) {
		this.qualPrevDescList = qualPrevDescList;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public ArrayList<String> getQualPrevCodeDescList() {
		return qualPrevCodeDescList;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public String getSavedCategory() {
		return savedCategory;
	}

	public void setSavedCategory(String savedCategory) {
		this.savedCategory = savedCategory;
	}

	public String getSavedQual() {
		return savedQual;
	}

	public void setSavedQual(String savedQual) {
		this.savedQual = savedQual;
	}

	public String getSavedSpec() {
		return savedSpec;
	}

	public void setSavedSpec(String savedSpec) {
		this.savedSpec = savedSpec;
	}

	public void resetFormFieldsOLD() {
		applyType = "";
		registrationType = "";
		allowLogin = true;
		student = new Student();
		qual = new Qualification();
		qualtwo = new Qualification();
		studentApplication = new Application();
		adminStaff = new Staff();
	    qualOther = new HistoryOther();

		// History (MasterDoctor)

		
		matric = new MatricM30();
		Spescount=0;
		regqualtype="";
		student.setIdNumber("");

		// ----- Walkthrough
		question1 = "";
		question2 = "";

		// Apply for student nr
		selectedDisability = null;
		selectedQual = null;
		selectedQualtwo = null;
		selectedSpes = null;
		selectedSpestwo = null;
		selectedCountry = null;
		selectedExamCentre = null;
		selectedExamCenprov = null;
		selectedHomeLanguage = null;
		selectedNationality = null;
		selectedPopulationGroup = null;
		selectedOccupation = null;
		selectedEconomicSector = null;
		selectedOtherUniversity = null;
		SelectedPrevInstitution = null;
		SelectedProvince = null;

		SelectedSchool = null;
		schoolCode = "";
		schoolDesc = "";
		
		selectedPrevActivity = null;

		savedCategory = null;
		savedCategory1 = null;
		savedCategory2 = null;
		savedQual = null;
		savedQual1 = null;
		savedQual2 = null;
		savedSpec = null;
		savedSpec1 = null;
		savedSpec2 = null;
		
		agree = null;
		numberBack = null;
		emailBack = null;
		timeBack = null;
		doneSubmit = false;
		String500back = null;

		//variable used to test session throughout process
		fromPage = null;

		// variable used to indicate that document upload is application specific
		application = true;

		// File upload
		addressLink = "";
		fileCount = 0;
		fileName = "";
		fileNewName = "";
		fileType = "";
		theFile = null;
		studentFiles = new ArrayList<StudentFile>();
		appealSourceFiles = new ArrayList<String>();
		appealWorkflowFiles = new ArrayList<String>();
		selectedPostalCode = null;
		searchListIdentifier = "Postal";
		searchSuburb = "";
		searchTown = "";
		searchResult = "";

		hiddenButton = false;
		hiddenQualButton = false;

		// 2014 New Login Select
		selected = "1";
		
		loginSelectMain = "";
		loginSelectDocPay = "";
		loginSelectYesNo = "";
		webLoginMsg = "";
		webUploadMsg = "";
		selectReset = "";
		
		selCategoryCode = null;
		selCategoryCode1 = null;
		selCategoryCode2 = null;
		
		selQualCode = null;
		selQualCode1 = null;
		selQualCode2 = null;
		
		selSpecCode = null;
		selSpecCode1 = null;
		selSpecCode2 = null;
		
		existQual = "";
		existSpec = "";
		
		selQualPrevCode  = null;

		categoryCodeList = new ArrayList<String>();
		categoryCodeList1 = new ArrayList<String>();
		categoryCodeList2 = new ArrayList<String>();

		categoryDescList = new ArrayList<String>();
		categoryDescList1 = new ArrayList<String>();
		categoryDescList2 = new ArrayList<String>();
		
		qualCodeList = new ArrayList<String>();
		qualCodeList1 = new ArrayList<String>();
		qualCodeList2 = new ArrayList<String>();
		
		qualCodeDescList = new ArrayList<String>();
		qualCodeDescList1 = new ArrayList<String>();
		qualCodeDescList2 = new ArrayList<String>();
		
		qualDescList = new ArrayList<String>();
		qualDescList1 = new ArrayList<String>();
		qualDescList2 = new ArrayList<String>();
		
		specCodeList = new ArrayList<String>();
		specCodeList1 = new ArrayList<String>();
		specCodeList2 = new ArrayList<String>();
		
		specCodeDescList = new ArrayList<String>();
		specCodeDescList1 = new ArrayList<String>();
		specCodeDescList2 = new ArrayList<String>();
		
		specDescList = new ArrayList<String>();
		specDescList1 = new ArrayList<String>();
		specDescList2 = new ArrayList<String>();

		qualPrevCodeList = new ArrayList<String>();
		qualPrevDescList = new ArrayList<String>();
		qualPrevCodeDescList = new ArrayList<String>();
		
		//FileUpload
		//for showing optional docs

		desc = new ArrayList<String>();
		map = new HashMap<String, List<String>>();
			
		requiredDocs  = null;
		optionalDocs  = null;

		optionLabels = new ArrayList<String>();
		optionCodes = new ArrayList<String>();

		requiredFileBeans = null;
		optionFileBean = null;

		docMsg = "";

		hiddenButtonUpload = false;
		
		adminStaff.setAdmin(false);
		adminStaff.setNumber("");
		adminStaff.setPassword("");
		adminStaff.setAdminAdmission("");
		adminStaff.setAdmission("");
		adminStaff.setFirstnames("");
		adminStaff.setSurname("");
		adminStaff.setStudentType("");
		
		//Matric Results for APS Calculator
		selectHEMain = "";
		
	    selectMatric = "";
	    apsScore = 0;
	    apsSize = 0;
	    subjects = null ;
	    qualUnisa = null;
	    qualArray = null;

	    apsSubjectName1  = "";
	    apsSubjectName2  = "";
	    apsSubjectName3  = "";
	    apsSubjectName4  = "";
	    apsSubjectName5  = "";
	    apsSubjectName6  = "";
	    apsSubjectName7  = "";
	    apsSubjectName8  = "";
	    apsSubjectName9  = "";
	    apsSubjectName10 = "";
	    apsSubjectName11 = "";
	    apsSubjectName12 = "";
	    
	    apsSubjectGrade1  = "";
	    apsSubjectGrade2  = "";
	    apsSubjectGrade3  = "";
	    apsSubjectGrade4  = "";
	    apsSubjectGrade5  = "";
	    apsSubjectGrade6  = "";
	    apsSubjectGrade7  = "";
	    apsSubjectGrade8  = "";
	    apsSubjectGrade9  = "";
	    apsSubjectGrade10 = "";
	    apsSubjectGrade11 = "";
	    apsSubjectGrade12 = "";
	    
	    apsSubjectSymbol1  = "";
	    apsSubjectSymbol2  = "";
	    apsSubjectSymbol3  = "";
	    apsSubjectSymbol4  = "";
	    apsSubjectSymbol5  = "";
	    apsSubjectSymbol6  = "";
	    apsSubjectSymbol7  = "";
	    apsSubjectSymbol8  = "";
	    apsSubjectSymbol9  = "";
	    apsSubjectSymbol10 = "";
	    apsSubjectSymbol11 = "";
	    apsSubjectSymbol12 = "";
	    
	    apsSubjectResult1  = "";
	    apsSubjectResult2  = "";
	    apsSubjectResult3  = "";
	    apsSubjectResult4  = "";
	    apsSubjectResult5  = "";
	    apsSubjectResult6  = "";
	    apsSubjectResult7  = "";
	    apsSubjectResult8  = "";
	    apsSubjectResult9  = "";
	    apsSubjectResult10 = "";
	    apsSubjectResult11 = "";
	    apsSubjectResult12 = "";
	    
	    //Appeal Process
	    appealSelect1 = "";
	    appealSelect2 = "";
	    appealText = "";
		appealSourceFiles = new ArrayList<String>();
		appealTypeFiles = new ArrayList<String>();
		appealWorkflowFiles = new ArrayList<String>();

	}

	public void resetFormFields(){

		applyType = "";
		applySEQUENCE = 0;
		registrationType = "";
		allowLogin = true;
		student = new Student();
		qual = new Qualification();
		qualtwo = new Qualification();
		qualAPS = new Qualification();
		studentApplication = new Application();
		adminStaff = new Staff();
		qualOther = new HistoryOther();
		status = new Status();

		// History (MasterDoctor)

		
		matric = new MatricM30();
		Spescount=0;
		regqualtype="";
		student.setIdNumber("");

		// ----- Walkthrough
		question1 = "";
		question2 = "";

		// Apply for student nr
		selectedDisability = null;
		selectedQual = null;
		selectedQualtwo = null;
		selectedSpes = null;
		selectedSpestwo = null;
		selectedCountry = null;
		selectedExamCentre = null;
		selectedExamCenprov = null;
		selectedHomeLanguage = null;
		selectedNationality = null;
		selectedPopulationGroup = null;
		selectedOccupation = null;
		selectedEconomicSector = null;
		selectedOtherUniversity = null;
		SelectedPrevInstitution = null;
		SelectedProvince = null;

		SelectedSchool = null;
		schoolCode = "";
		schoolDesc = "";
		
		selectedPrevActivity = null;

		savedCategory = null;
		savedCategory1 = null;
		savedCategory2 = null;
		savedQual = null;
		savedQual1 = null;
		savedQual2 = null;
		savedSpec = null;
		savedSpec1 = null;
		savedSpec2 = null;
		
		agree = null;
		numberBack = null;
		emailBack = null;
		timeBack = null;
		doneSubmit = false;
		String500back = null;

		//variable used to test session throughout process
		fromPage = null;

		// variable used to indicate that document upload is application specific
		application = true;

		// File upload
		addressLink = "";
		fileCount = 0;
		fileName = "";
		fileNewName = "";
		fileType = "";
		theFile = null;
		studentFiles = new ArrayList<StudentFile>();
		appealSourceFiles = new ArrayList<String>();
		appealWorkflowFiles = new ArrayList<String>();
		selectedPostalCode = null;
		searchListIdentifier = "Postal";
		searchSuburb = "";
		searchTown = "";
		searchResult = "";

		addressSubResultPos = "";
		addressSubResultPhys = "";
		addressSubResultCour = "";
	
		hiddenButton = false;
		hiddenQualButton = false;

		// 2014 New Login Select
		selected = "1";
		
		loginSelectMain = "";
		loginSelectDocPay = "";
		loginSelectYesNo = "";
		webLoginMsg = "";
		webLoginMsg2 = "";
		webUploadMsg = "";
		selectReset = "";
		
		selCategoryCode = null;
		selCategoryCode1 = null;
		selCategoryCode2 = null;
		
		selQualCode = null;
		selQualCode1 = null;
		selQualCode2 = null;
		
		selQualCodeDesc = "";
		selQualCode1Desc = "";
		selQualCode2Desc = "";
		
		selSpecCode = null;
		selSpecCode1 = null;
		selSpecCode2 = null;
		
		selSpecCodeDesc = "";
		selSpecCode1Desc = "";
		selSpecCode2Desc = "";
		
		existQual = "";
		existSpec = "";

		existQualDesc = "";
		existSpecDesc = "";
		
		selQualPrevCode  = null;

		categoryCodeList = new ArrayList<String>();
		categoryCodeList1 = new ArrayList<String>();
		categoryCodeList2 = new ArrayList<String>();

		categoryDescList = new ArrayList<String>();
		categoryDescList1 = new ArrayList<String>();
		categoryDescList2 = new ArrayList<String>();
		
		qualCodeList = new ArrayList<String>();
		qualCodeList1 = new ArrayList<String>();
		qualCodeList2 = new ArrayList<String>();
		
		qualCodeDescList = new ArrayList<String>();
		qualCodeDescList1 = new ArrayList<String>();
		qualCodeDescList2 = new ArrayList<String>();
		
		qualDescList = new ArrayList<String>();
		qualDescList1 = new ArrayList<String>();
		qualDescList2 = new ArrayList<String>();
		
		specCodeList = new ArrayList<String>();
		specCodeList1 = new ArrayList<String>();
		specCodeList2 = new ArrayList<String>();
		
		specCodeDescList = new ArrayList<String>();
		specCodeDescList1 = new ArrayList<String>();
		specCodeDescList2 = new ArrayList<String>();
		
		specDescList = new ArrayList<String>();
		specDescList1 = new ArrayList<String>();
		specDescList2 = new ArrayList<String>();

		qualPrevCodeList = new ArrayList<String>();
		qualPrevDescList = new ArrayList<String>();
		qualPrevCodeDescList = new ArrayList<String>();
		
		//FileUpload
		//for showing optional docs

		desc = new ArrayList<String>();
		map = new HashMap<String, List<String>>();
			
		requiredDocs  = null;
		optionalDocs  = null;

		optionLabels = new ArrayList<String>();
		optionCodes = new ArrayList<String>();

		requiredFileBeans = null;
		optionFileBean = null;

		docMsg = "";

		hiddenButtonUpload = false;
		
	
		//Matric Results for APS Calculator
		selectHEMain = "";
		
		selectMatric = "";
		apsScore = 0;
		apsSize = 0;
		subjects = null ;
		qualUnisa = null;
		qualArray = null;

		apsSubjectName1  = "";
		apsSubjectName2  = "";
		apsSubjectName3  = "";
		apsSubjectName4  = "";
		apsSubjectName5  = "";
		apsSubjectName6  = "";
		apsSubjectName7  = "";
		apsSubjectName8  = "";
		apsSubjectName9  = "";
		apsSubjectName10 = "";
		apsSubjectName11 = "";
		apsSubjectName12 = "";
		
		apsSubjectGrade1  = "";
		apsSubjectGrade2  = "";
		apsSubjectGrade3  = "";
		apsSubjectGrade4  = "";
		apsSubjectGrade5  = "";
		apsSubjectGrade6  = "";
		apsSubjectGrade7  = "";
		apsSubjectGrade8  = "";
		apsSubjectGrade9  = "";
		apsSubjectGrade10 = "";
		apsSubjectGrade11 = "";
		apsSubjectGrade12 = "";
		
		apsSubjectSymbol1  = "";
		apsSubjectSymbol2  = "";
		apsSubjectSymbol3  = "";
		apsSubjectSymbol4  = "";
		apsSubjectSymbol5  = "";
		apsSubjectSymbol6  = "";
		apsSubjectSymbol7  = "";
		apsSubjectSymbol8  = "";
		apsSubjectSymbol9  = "";
		apsSubjectSymbol10 = "";
		apsSubjectSymbol11 = "";
		apsSubjectSymbol12 = "";
		
		apsSubjectResult1  = "";
		apsSubjectResult2  = "";
		apsSubjectResult3  = "";
		apsSubjectResult4  = "";
		apsSubjectResult5  = "";
		apsSubjectResult6  = "";
		apsSubjectResult7  = "";
		apsSubjectResult8  = "";
		apsSubjectResult9  = "";
		apsSubjectResult10 = "";
		apsSubjectResult11 = "";
		apsSubjectResult12 = "";
		
		//Appeal Process
		appealSelect1 = "";
		appealSelect2 = "";
		appealText = "";
		appealSourceFiles = new ArrayList<String>();
		appealTypeFiles = new ArrayList<String>();
		appealWorkflowFiles = new ArrayList<String>();
		
		//M&D Admission Process
		mdprevList = null;
		readmd = "";

		student.setNumber("");
		student.setNumberTmp("");
		student.setSurname("");
		student.setFirstnames("");
		student.setBirthYear("");
		student.setBirthMonth("");
		student.setBirthDay("");
		student.setIdNumber("");

		qualSTAAE01 = false;

		selectedPrevActivity = "";

		loginSelectAPS = "";

		selQualCodeDesc = "";
		selQualCode1Desc = "";
		selQualCode2Desc = "";

		selSpecCodeDesc = "";
		selSpecCode1Desc = "";
		selSpecCode2Desc = "";

		existQualDesc = "";
		existSpecDesc = "";

		qualStatus1 = "";
		qualStatus2 = "";
		qualStatusCode1 = "";
		qualStatusCode2 = "";
		qualStatus1Reason = "";
		qualStatus2Reason = "";

		offerQual1 = "";
		offerQual2 = "";
		offerSpec1 = "";
		offerSpec2 = "";
		
		offerRadio = "";

		qualUnisaFound = false;
		qualIDMatch = false;
		qualAPSMatch = false;
			
		appealSelect1 = "";
		appealSelect2 = "";
		appealText = "";

		appealSourceFiles = new ArrayList<String>();
		appealTypeFiles = new ArrayList<String>();
		appealWorkflowFiles = new ArrayList<String>();

		adminStaff.setAdmin(false);
		adminStaff.setNumber("");
		adminStaff.setPassword("");
		adminStaff.setAdminAdmission("");
		adminStaff.setAdmission("");
		adminStaff.setFirstnames("");
		adminStaff.setSurname("");
		adminStaff.setStudentType("");
		
	}
}

