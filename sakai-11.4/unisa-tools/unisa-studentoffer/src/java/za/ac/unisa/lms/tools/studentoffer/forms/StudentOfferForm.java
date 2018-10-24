//Created by MyEclipse Struts
// XSL source (default): platform:/plugin/com.genuitec.eclipse.cross.easystruts.eclipse_3.9.210/xslt/JavaClass.xsl

package za.ac.unisa.lms.tools.studentoffer.forms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.ValidatorActionForm;

import za.ac.unisa.lms.tools.studentoffer.actions.StudentOfferAction;

public class StudentOfferForm extends ValidatorActionForm {

	// --------------------------------------------------------- Instance Variables

	private static final long serialVersionUID = 1L;
	private static final String version = "2018002";
	public static Log log = LogFactory.getLog(StudentOfferAction.class);
	
	private String applyType = "";
	private String registrationType ="";// U = undergrad, P = postgrad, S = Short learning programme
	private boolean allowLogin = true;
	private Student student = new Student();
	private Application studentApplication = new Application();
	private boolean qualSTAAE01 = false;
	private String regqualtype="";


	private String agree;
	private String numberBack;
	private String timeBack;
	private String donesubmit;
	private String String500back;

	//variable used to test session throughout process
	private String fromPage;

	// 2014 New Login Select
	private String selected = "1";
	
	private String loginSelectMain = "";
	private String webLoginMsg = "";
	private String selectReset = "";
	
	private String qualStatus1 = "";
	private String qualStatus2 = "";
	private String qualStatusCode1 = "";
	private String qualStatusCode2 = "";
	private String qualPeriodCode1 = "";
	private String qualPeriodCode2 = "";
	private String qualStatus1Reason = "";
	private String qualStatus2Reason = "";
	
	private String offerQual1 = "";
	private String offerQual2 = "";
	private String offerSpec1 = "";
	private String offerSpec2 = "";
	
	private String pendingQual1 = "";
	private String pendingQual2 = "";
	private String pendingSpec1 = "";
	private String pendingSpec2 = "";
	
	private String offerRadio = "";	
	
	//FileUpload
	//for showing optional docs

	private List<String> desc = new ArrayList<String>();
	private Map<String, List<String>> map = new HashMap<String, List<String>>();
	
	//Johanet add variable pendingApp
	boolean pendingApp = false;
	
	public boolean isPendingApp() {
		return pendingApp;
	}

	public void setPendingApp(boolean pendingApp) {
		this.pendingApp = pendingApp;
	}

	public String getPendingQual1() {
		return pendingQual1;
	}

	public void setPendingQual1(String pendingQual1) {
		this.pendingQual1 = pendingQual1;
	}

	public String getPendingQual2() {
		return pendingQual2;
	}

	public void setPendingQual2(String pendingQual2) {
		this.pendingQual2 = pendingQual2;
	}

	public String getPendingSpec1() {
		return pendingSpec1;
	}

	public void setPendingSpec1(String pendingSpec1) {
		this.pendingSpec1 = pendingSpec1;
	}

	public String getPendingSpec2() {
		return pendingSpec2;
	}

	public void setPendingSpec2(String pendingSpec2) {
		this.pendingSpec2 = pendingSpec2;
	}

	private String docMsg = "";

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

	// New 2014 Login Select
	public String getLoginSelectMain() {
		return loginSelectMain;
	}

	public void setLoginSelectMain(String loginSelectMain) {
		this.loginSelectMain = loginSelectMain;
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


	public String getSelected() {
		return selected;
	}

	public void setSelected(String selected) {
		this.selected = selected;
	}


	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		
		ActionErrors errors = new ActionErrors();
		//log.debug("StudentOfferForm - Validate................");
		return errors;
	}

	public List<String> getDesc() {
		//log.debug("StudentOfferForm - getDesc");
		return desc;
	}

	public void setDesc(List<String> desc) {
		//log.debug("StudentOfferForm - setDesc");
		this.desc = desc;
	}

	public Map<String, List<String>> getMap() {
		//log.debug("StudentOfferForm - getMap");
		return map;
	}

	public void setMap(Map<String, List<String>> map) {
		//log.debug("StudentOfferForm - setMap");
		this.map = map;
	}
	public String getDocMsg() {
		return docMsg;
	}

	public void setDocMsg(String docMsg) {
		this.docMsg = docMsg;
	}

	public String getOfferRadio() {
		return offerRadio;
	}

	public void setOfferRadio(String offerRadio) {
		this.offerRadio = offerRadio;
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

	public String getQualPeriodCode1() {
		return qualPeriodCode1;
	}

	public void setQualPeriodCode1(String qualPeriodCode1) {
		this.qualPeriodCode1 = qualPeriodCode1;
	}

	public String getQualPeriodCode2() {
		return qualPeriodCode2;
	}

	public void setQualPeriodCode2(String qualPeriodCode2) {
		this.qualPeriodCode2 = qualPeriodCode2;
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

	public static Log getLog() {
		return log;
	}

	public static void setLog(Log log) {
		StudentOfferForm.log = log;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public void resetFormFieldsOLD() {
		applyType = "";
		registrationType = "";
		allowLogin = true;
		student = new Student();
		studentApplication = new Application();

		// Apply for student nr
		numberBack = null;
		timeBack = null;
		donesubmit = null;
		String500back = null;

		//variable used to test session throughout process
		fromPage = null;

		// 2014 New Login Select
		selected = "1";
		
		loginSelectMain = "";
		webLoginMsg = "";
		selectReset = "";
		
		qualStatus1 = "";
		qualStatus2 = "";
		qualStatusCode1 = "";
		qualStatusCode2 = "";
		qualPeriodCode1 = "";
		qualPeriodCode2 = "";
		qualStatus1Reason = "";
		qualStatus2Reason = "";
		
		offerQual1 = "";
		offerQual2 = "";
		offerSpec1 = "";
		offerSpec2 = "";
		
		offerRadio = "";
		
		//FileUpload
		//for showing optional docs

		desc = new ArrayList<String>();
		map = new HashMap<String, List<String>>();
			
		docMsg = "";


	}

	public void resetFormFields(){
		applyType = "";
		allowLogin = true;
		student = new Student();
		studentApplication = new Application();

		donesubmit = null;
		String500back = null;

		//variable used to test session throughout process
		fromPage = null;

		// 2014 New Login Select
		selected = "1";
		
		webLoginMsg = "";
		selectReset = "";
		
		student.setNumber("");
		student.setSurname("");
		student.setFirstnames("");
		student.setBirthYear("");
		student.setBirthMonth("");
		student.setBirthDay("");

		qualSTAAE01 = false;

		qualStatus1 = "";
		qualStatus2 = "";
		qualStatusCode1 = "";
		qualStatusCode2 = "";
		qualPeriodCode1 = "";
		qualPeriodCode2 = "";
		qualStatus1Reason = "";
		qualStatus2Reason = "";
		
		offerQual1 = "";
		offerQual2 = "";
		offerSpec1 = "";
		offerSpec2 = "";
		
		pendingQual1 = "";
		pendingQual2 = "";
		pendingSpec1 = "";
		pendingSpec2 = "";
		
		pendingApp = false;
		
		offerRadio = "";
	
	}
}

