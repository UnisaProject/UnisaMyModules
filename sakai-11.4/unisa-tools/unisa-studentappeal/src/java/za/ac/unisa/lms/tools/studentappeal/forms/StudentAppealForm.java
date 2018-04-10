//Created by MyEclipse Struts
// XSL source (default): platform:/plugin/com.genuitec.eclipse.cross.easystruts.eclipse_3.9.210/xslt/JavaClass.xsl

package za.ac.unisa.lms.tools.studentappeal.forms;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;
import org.apache.struts.validator.ValidatorActionForm;

import za.ac.unisa.lms.tools.studentappeal.actions.StudentAppealAction;

public class StudentAppealForm extends ValidatorActionForm {

	// --------------------------------------------------------- Instance Variables

	private static final long serialVersionUID = 1L;
	private static final String version = "2018001b";
	public static Log log = LogFactory.getLog(StudentAppealAction.class);
	
	private String applyType = "";
	private String registrationType ="";// U = undergrad, P = postgrad, S = Short learning programme
	private boolean allowLogin = true;
	private Student student = new Student();
	private Qualification qual = new Qualification();
	private Qualification qualtwo = new Qualification();
	private Application studentApplication = new Application();
	private boolean qualSTAAE01 = false;
	private int Spescount=0;
	private String regqualtype="";

	private String agree;
	private String numberBack;
	private String timeBack;
	private String donesubmit;
	private String String500back;

	//variable used to test session throughout process
	private String fromPage;

	private String webLoginMsg = "";
	private String webUploadMsg = "";
	private String selectReset = "";
	
	private String qualStatus1 = "";
	private String qualStatus2 = "";
	private String qualStatusCode1 = "";
	private String qualStatusCode2 = "";
	private String qualStatus1Reason = "";
	private String qualStatus2Reason = "";
	
	private String selQualCode1;
	private String selQualCode2;

	private String selSpecCode1;
	private String selSpecCode2;
	
    //Appeal Process
    private String appealSelect1 = "";
    private String appealSelect2 = "";
    private String appealText = "";
	private ArrayList<String> appealSourceFiles = new ArrayList<String>();
	private ArrayList<String> appealTypeFiles = new ArrayList<String>();
	private ArrayList<String> appealWorkflowFiles = new ArrayList<String>();
	
	//File Upload
	private String fileName = "";
	private String fileNewName = "";
	private String fileType = "";
	private int fileCount = 0;
	private FormFile file;
	private FormFile theFile;
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileNewName() {
		return fileNewName;
	}

	public void setFileNewName(String fileNewName) {
		this.fileNewName = fileNewName;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
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

	public FormFile getTheFile() {
		return theFile;
	}

	public void setTheFile(FormFile theFile) {
		this.theFile = theFile;
	}

	public String getFileSelected() {
		return fileSelected;
	}

	public void setFileSelected(String fileSelected) {
		this.fileSelected = fileSelected;
	}

	private String fileSelected = "";

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

	public boolean isAllowLogin() {
		return allowLogin;
	}

	public void setAllowLogin(boolean allowLogin) {
		this.allowLogin = allowLogin;
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

	public Qualification getQualtwo() {
		return qualtwo;
	}

	public void setQualtwo(Qualification qualtwo) {
		this.qualtwo = qualtwo;
	}

	public int getSpescount() {
		return Spescount;
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

	public Application getStudentApplication() {
		return studentApplication;
	}

	public void setStudentApplication(Application studentApplication) {
		this.studentApplication = studentApplication;
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

	public String getTimeBack() {
		return timeBack;
	}

	public void setTimeBack(String timeBack) {
		this.timeBack = timeBack;
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

	public String getWebLoginMsg() {
		return webLoginMsg;
	}

	public void setWebLoginMsg(String webLoginMsg) {
		this.webLoginMsg = webLoginMsg;
	}

	public String getSelectReset() {
		return selectReset;
	}

	public void setSelectReset(String selectReset) {
		this.selectReset = selectReset;
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

	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		
		ActionErrors errors = new ActionErrors();
		//log.debug("StudentAppealForm - Validate................");
		return errors;
	}

	public String getWebUploadMsg() {
		return webUploadMsg;
	}

	public void setWebUploadMsg(String webUploadMsg) {
		this.webUploadMsg = webUploadMsg;
	}

	public String getSelQualCode1() {
		return selQualCode1;
	}

	public void setSelQualCode1(String selQualCode1) {
		this.selQualCode1 = selQualCode1;
	}

	public String getSelQualCode2() {
		return selQualCode2;
	}

	public void setSelQualCode2(String selQualCode2) {
		this.selQualCode2 = selQualCode2;
	}

	public String getSelSpecCode1() {
		return selSpecCode1;
	}

	public void setSelSpecCode1(String selSpecCode1) {
		this.selSpecCode1 = selSpecCode1;
	}

	public String getSelSpecCode2() {
		return selSpecCode2;
	}

	public void setSelSpecCode2(String selSpecCode2) {
		this.selSpecCode2 = selSpecCode2;
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

	public static Log getLog() {
		return log;
	}

	public static void setLog(Log log) {
		StudentAppealForm.log = log;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public void resetFormFieldsOLD() {
		applyType = "";
		registrationType = "";
		allowLogin = true;
		student = new Student();
		qual = new Qualification();
		qualtwo = new Qualification();
		studentApplication = new Application();

		// Apply for student nr
		numberBack = null;
		timeBack = null;
		donesubmit = null;
		String500back = null;

		//variable used to test session throughout process
		fromPage = null;

		webLoginMsg = "";
		webUploadMsg = "";
		selectReset = "";
		
		student.setNumber("");
		student.setSurname("");
		student.setFirstnames("");
		student.setBirthYear("");
		student.setBirthMonth("");
		student.setBirthDay("");

		qualSTAAE01 = false;
		
		selQualCode1 = "";
		selQualCode2 = "";

		selSpecCode1 = "";
		selSpecCode2 = "";
		
		qualStatus1 = "";
		qualStatus2 = "";
		qualStatusCode1 = "";
		qualStatusCode2 = "";
		qualStatus1Reason = "";
		qualStatus2Reason = "";
		
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
		allowLogin = true;
		student = new Student();
		qual = new Qualification();
		qualtwo = new Qualification();
		studentApplication = new Application();

		donesubmit = null;
		String500back = null;

		//variable used to test session throughout process
		fromPage = null;

		webLoginMsg = "";
		webUploadMsg = "";
		selectReset = "";
		
		student.setNumber("");
		student.setSurname("");
		student.setFirstnames("");
		student.setBirthYear("");
		student.setBirthMonth("");
		student.setBirthDay("");

		qualSTAAE01 = false;

		selQualCode1 = "";
		selQualCode2 = "";

		selSpecCode1 = "";
		selSpecCode2 = "";
	
		qualStatus1 = "";
		qualStatus2 = "";
		qualStatusCode1 = "";
		qualStatusCode2 = "";
		qualStatus1Reason = "";
		qualStatus2Reason = "";
		
	    //Appeal Process
	    appealSelect1 = "";
	    appealSelect2 = "";
	    appealText = "";
		appealSourceFiles = new ArrayList<String>();
		appealTypeFiles = new ArrayList<String>();
		appealWorkflowFiles = new ArrayList<String>();
		
		// File upload
		fileCount = 0;
		fileName = "";
		fileNewName = "";
		fileType = "";
		theFile = null;

	}
}

