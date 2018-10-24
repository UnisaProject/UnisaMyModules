//Created by MyEclipse Struts
// XSL source (default): platform:/plugin/com.genuitec.eclipse.cross.easystruts.eclipse_3.9.210/xslt/JavaClass.xsl

package za.ac.unisa.lms.tools.studentstatus.forms;

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

import za.ac.unisa.lms.tools.studentstatus.actions.StudentStatusAction;
import za.ac.unisa.lms.tools.studentstatus.bo.Status;

public class StudentStatusForm extends ValidatorActionForm {

	// --------------------------------------------------------- Instance Variables

	private static final long serialVersionUID = 1L;
	private static final String version = "2019001a";
	public static Log log = LogFactory.getLog(StudentStatusAction.class);
	
	private String applyType = "";
	private boolean allowLogin = true;
	private Student student = new Student();
	private Status status = new Status();

	private String agree;
	private String donesubmit;
	private String String500back;

	//variable used to test session throughout process
	private String fromPage;
	
	private String loginSelectMain = "";
	private String webLoginMsg = "";
	private String selectReset = "";

	private String selCategoryCode1;
	private String selCategoryCode2;

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
	
	private String qualStatus1 = "";
	private String qualStatus2 = "";
	private String qualStatusCode1 = "";
	private String qualStatusCode2 = "";
	private String qualStatus1Reason = "";
	private String qualStatus2Reason = "";
	
	private boolean screeningSitting = false;
	private String screeningSittingQual1 ="";
	private String screeningSittingQual2 ="";
	private ScreeningVenue screeningVenue = new ScreeningVenue();	
	private String originatedFrom = "";
	
	public String getOriginatedFrom() {
		return originatedFrom;
	}

	public void setOriginatedFrom(String originatedFrom) {
		this.originatedFrom = originatedFrom;
	}

	public boolean isScreeningSitting() {
		return screeningSitting;
	}

	public void setScreeningSitting(boolean screeningSitting) {
		this.screeningSitting = screeningSitting;
	}

	public ScreeningVenue getScreeningVenue() {
		return screeningVenue;
	}

	public void setScreeningVenue(ScreeningVenue screeningVenue) {
		this.screeningVenue = screeningVenue;
	}

	public String getScreeningSittingQual1() {
		return screeningSittingQual1;
	}

	public void setScreeningSittingQual1(String screeningSittingQual1) {
		this.screeningSittingQual1 = screeningSittingQual1;
	}

	public String getScreeningSittingQual2() {
		return screeningSittingQual2;
	}

	public void setScreeningSittingQual2(String screeningSittingQual2) {
		this.screeningSittingQual2 = screeningSittingQual2;
	}

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
	
	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public String getDonesubmit() {
		return donesubmit;
	}

	public void setDonesubmit(String donesubmit) {
		this.donesubmit = donesubmit;
	}

	public String getString500back() {
		return String500back;
	}

	public void setString500back(String string500back) {
		String500back = string500back;
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

	public String getSelSpecCode() {
		return selSpecCode;
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

	public static Log getLog() {
		return log;
	}

	public static void setLog(Log log) {
		StudentStatusForm.log = log;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	public void resetFormFieldsOLD() {
		applyType = "";
		allowLogin = true;
		student = new Student();
		donesubmit = null;
		String500back = null;

		//variable used to test session throughout process
		fromPage = null;
		loginSelectMain = "";
		webLoginMsg = "";
		selectReset = "";
		
		selCategoryCode1 = null;
		selCategoryCode2 = null;
		
		selQualCode1 = null;
		selQualCode2 = null;
		
		selSpecCode1 = null;
		selSpecCode2 = null;

	}

	public void resetFormFields(){
		originatedFrom = "";
		applyType = "";
		allowLogin = true;
		student = new Student();
		screeningVenue = new ScreeningVenue();
		donesubmit = null;
		String500back = null;

		//variable used to test session throughout process
		fromPage = null;
		webLoginMsg = "";
		selectReset = "";
		
		selQualCode1 = null;
		selQualCode2 = null;
		
		selSpecCode1 = null;
		selSpecCode2 = null;
		
		student.setNumber("");
		student.setSurname("");
		student.setFirstnames("");
		student.setBirthYear("");
		student.setBirthMonth("");
		student.setBirthDay("");

		qualStatus1 = "";
		qualStatus2 = "";
		qualStatusCode1 = "";
		qualStatusCode2 = "";
		qualStatus1Reason = "";
		qualStatus2Reason = "";
		screeningSitting = false;
		screeningSittingQual1 ="";
		screeningSittingQual2 ="";
	
	}
}

