package za.ac.unisa.lms.tools.mdregistrations.forms;

import java.util.ArrayList;

public class StudyUnit {

	private String code;
	private String desc;
	private String regPeriod;
	private boolean regPeriod1 = false;
	private boolean regPeriod2 = false;
	private boolean regPeriod0 = false;
	private boolean regPeriod6 = false;
	// use this to determine whether display should be static
	private boolean regPeriodStatic = false;
	private boolean languageStatic = false;
	private String examPeriod;
	private String ndp; //non degree purposes
	private String language;
	private String languageDesc;
	private String status;
	private String statusDesc;
	/* Indicates which status description should be shown
	 * TP : temporary registration, stuann=RG, awaiting payment
	 * TN : temporary registration, stuann=TN, awaiting payment and/or doc's
	 * A : application for
	 */
	private String statusIndicator;
	private boolean wflIndicator = false;;
	 // Keeps track of all exam dates for a specific module
	private ArrayList examDates;
	private String supplCode;
	// keep track of study unit status for sruaf01c
	// I = insert, R = remove, M = modify
	private String action="";
	private boolean odl= false;
	private boolean wil=false;
	private String odlAnswer="";
	private String wilAnswer="";
	// workflow status
	private String wflDescription;
	private String wflTimestamp;
	

	public String getSupplCode() {
		return supplCode;
	}
	public void setSupplCode(String supplCode) {
		this.supplCode = supplCode;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getLanguageDesc() {
		return languageDesc;
	}
	public void setLanguageDesc(String languageDesc) {
		this.languageDesc = languageDesc;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getNdp() {
		return ndp;
	}
	public void setNdp(String ndp) {
		this.ndp = ndp;
	}
	public String getRegPeriod() {
		return regPeriod;
	}
	public void setRegPeriod(String regPeriod) {
		this.regPeriod = regPeriod;
	}
	public ArrayList getExamDates() {
		return examDates;
	}
	public void setExamDates(ArrayList examDates) {
		this.examDates = examDates;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getStatusDesc() {
		return statusDesc;
	}
	public void setStatusDesc(String statusDesc) {
		this.statusDesc = statusDesc;
	}
	public String toString() {
		return "Study unit: " + code + ", lang=" +language;

	}
	public String getExamPeriod() {
		return examPeriod;
	}
	public void setExamPeriod(String examPeriod) {
		this.examPeriod = examPeriod;
	}
	public String getStatusIndicator() {
		return statusIndicator;
	}
	public void setStatusIndicator(String statusIndicator) {
		this.statusIndicator = statusIndicator;
	}
	public boolean isRegPeriod0() {
		return regPeriod0;
	}
	public void setRegPeriod0(boolean regPeriod0) {
		this.regPeriod0 = regPeriod0;
	}
	public boolean isRegPeriod1() {
		return regPeriod1;
	}
	public void setRegPeriod1(boolean regPeriod1) {
		this.regPeriod1 = regPeriod1;
	}
	public boolean isRegPeriod2() {
		return regPeriod2;
	}
	public void setRegPeriod2(boolean regPeriod2) {
		this.regPeriod2 = regPeriod2;
	}
	public boolean isRegPeriod6() {
		return regPeriod6;
	}
	public void setRegPeriod6(boolean regPeriod6) {
		this.regPeriod6 = regPeriod6;
	}
	public boolean isLanguageStatic() {
		return languageStatic;
	}
	public void setLanguageStatic(boolean languageStatic) {
		this.languageStatic = languageStatic;
	}
	public boolean isRegPeriodStatic() {
		return regPeriodStatic;
	}
	public void setRegPeriodStatic(boolean regPeriodStatic) {
		this.regPeriodStatic = regPeriodStatic;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public boolean isOdl() {
		return odl;
	}
	public void setOdl(boolean odl) {
		this.odl = odl;
	}
	public String getOdlAnswer() {
		return odlAnswer;
	}
	public void setOdlAnswer(String odlAnswer) {
		this.odlAnswer = odlAnswer;
	}
	public boolean isWil() {
		return wil;
	}
	public void setWil(boolean wil) {
		this.wil = wil;
	}
	public String getWilAnswer() {
		return wilAnswer;
	}
	public void setWilAnswer(String wilAnswer) {
		this.wilAnswer = wilAnswer;
	}
	public String getWflDescription() {
		return wflDescription;
	}
	public void setWflDescription(String wflDescription) {
		this.wflDescription = wflDescription;
	}
	public String getWflTimestamp() {
		return wflTimestamp;
	}
	public void setWflTimestamp(String wflTimestamp) {
		this.wflTimestamp = wflTimestamp;
	}
	public boolean isWflIndicator() {
		return wflIndicator;
	}
	public void setWflIndicator(boolean wflIndicator) {
		this.wflIndicator = wflIndicator;
	}

}
