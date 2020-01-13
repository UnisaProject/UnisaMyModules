package za.ac.unisa.lms.tools.registrationstatus.forms;

import java.util.ArrayList;

public class StudyUnit {

	private String code;
	private String desc;
	private String regPeriod;
	private boolean regPeriod1 = false;
	private boolean regPeriod2 = false;
	private boolean regPeriod0 = false;
	private boolean regPeriod6 = false;
	// use this to determine wether display should be static
	private boolean regPeriodStatic = false;
	private boolean languageStatic = false;
	private String examPeriod;
	private String ndp; //non degree purposes
	private String language;
	private String status;
	/* Indicates which status description should be shown
	 * TP : temporary registration, stuann=RG, awaiting payment
	 * TN : temporary registration, stuann=TN, awaiting payment and/or doc's
	 * A : application for
	 */
	private String statusIndicator;
	 // Keeps track of all exam dates for a specific module
	private ArrayList examDates;
	private String supplCode;

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

}
