package za.ac.unisa.lms.tools.studentstatus.forms;

import java.util.ArrayList;

/**
 * @author Krugegj
 *
 */
public class Application {
	private String appnumber;
	private String appDate;
	private String staffCurrent;
	private String staffDeceased;
	private String prisoner;
	private String radioPrev;
	private String radioNDP;
	private String radioRPL;
	private String qualNDP;
	private String radioOfferQual1;
	private String radioOfferQual2;
	private boolean ndpSub = false;
	private String finaidEduloan;
	private String finaidNsfas;
	private String careerCounsel;
	private String docformForm;
	private String docformSchool;
	private String docformAcadrec;
	private String docformIdentity;
	private String docformMarriage;
	private String docformToefl;
	private String feePaid;
	private String lateApplication;
	private String matricCertificate;
	private String heAdmission;
	private String matricExamnr;
	private String matricExamyear;
	private String applyExemptions;
	private String licensee;
	private String previnstStudnr;
	private String completeQual;
	private String completeText;
	private String matricAutoverified;
	private String matricCertOther;
	private String internetApplication;
	private String certificateDesc; 
	private GeneralItem matricProvince = new GeneralItem();
	private GeneralItem matricSchool = new GeneralItem();
	
	//NDP Study Modules
	ArrayList<String> ndpRegList = new ArrayList<String>();
	
	private String ndpRegSu1 = "";
	private String ndpRegSu2 = "";
	private String ndpRegSu3 = "";
	private String ndpRegSu4 = "";
	private String ndpRegSu5 = "";
	private String ndpRegSu6 = "";
	private String ndpRegSu7 = "";
	private String ndpRegSu8 = "";
	private String ndpRegSu9 = "";
	private String ndpRegSu10 = "";
	
	
	public String getAppnumber() {
		return appnumber;
	}
	public void setAppnumber(String appnumber) {
		this.appnumber = appnumber;
	}
	public String getAppDate() {
		return appDate;
	}
	public void setAppDate(String appDate) {
		this.appDate = appDate;
	}
	public String getStaffCurrent() {
		return staffCurrent;
	}
	public void setStaffCurrent(String staffCurrent) {
		this.staffCurrent = staffCurrent;
	}
	public String getStaffDeceased() {
		return staffDeceased;
	}
	public void setStaffDeceased(String staffDeceased) {
		this.staffDeceased = staffDeceased;
	}
	public String getPrisoner() {
		return prisoner;
	}
	public void setPrisoner(String prisoner) {
		this.prisoner = prisoner;
	}
	public String getFinaidEduloan() {
		return finaidEduloan;
	}
	public void setFinaidEduloan(String finaidEduloan) {
		this.finaidEduloan = finaidEduloan;
	}
	public String getFinaidNsfas() {
		return finaidNsfas;
	}
	public void setFinaidNsfas(String finaidNsfas) {
		this.finaidNsfas = finaidNsfas;
	}
	public String getCareerCounsel() {
		return careerCounsel;
	}
	public void setCareerCounsel(String careerCounsel) {
		this.careerCounsel = careerCounsel;
	}
	public String getDocformForm() {
		return docformForm;
	}
	public void setDocformForm(String docformForm) {
		this.docformForm = docformForm;
	}
	public String getDocformSchool() {
		return docformSchool;
	}
	public void setDocformSchool(String docformSchool) {
		this.docformSchool = docformSchool;
	}
	public String getDocformAcadrec() {
		return docformAcadrec;
	}
	public void setDocformAcadrec(String docformAcadrec) {
		this.docformAcadrec = docformAcadrec;
	}
	public String getDocformIdentity() {
		return docformIdentity;
	}
	public void setDocformIdentity(String docformIdentity) {
		this.docformIdentity = docformIdentity;
	}
	public String getDocformMarriage() {
		return docformMarriage;
	}
	public void setDocformMarriage(String docformMarriage) {
		this.docformMarriage = docformMarriage;
	}
	public String getFeePaid() {
		return feePaid;
	}
	public void setFeePaid(String feePaid) {
		this.feePaid = feePaid;
	}
	public String getLateApplication() {
		return lateApplication;
	}
	public void setLateApplication(String lateApplication) {
		this.lateApplication = lateApplication;
	}
	public String getMatricCertificate() {
		return matricCertificate;
	}
	public void setMatricCertificate(String matricCertificate) {
		this.matricCertificate = matricCertificate;
	}
	public String getHeAdmission() {
		return heAdmission;
	}
	public void setHeAdmission(String heAdmission) {
		this.heAdmission = heAdmission;
	}
	public String getMatricExamnr() {
		return matricExamnr;
	}
	public void setMatricExamnr(String matricExamnr) {
		this.matricExamnr = matricExamnr;
	}
	public String getMatricExamyear() {
		return matricExamyear;
	}
	public void setMatricExamyear(String matricExamyear) {
		this.matricExamyear = matricExamyear;
	}
	public String getApplyExemptions() {
		return applyExemptions;
	}
	public void setApplyExemptions(String applyExemptions) {
		this.applyExemptions = applyExemptions;
	}
	public String getPrevinstStudnr() {
		return previnstStudnr;
	}
	public void setPrevinstStudnr(String previnstStudnr) {
		this.previnstStudnr = previnstStudnr;
	}
	public String getCompleteQual() {
		return completeQual;
	}
	public void setCompleteQual(String completeQual) {
		this.completeQual = completeQual;
	}
	public String getCompleteText() {
		return completeText;
	}
	public void setCompleteText(String completeText) {
		this.completeText = completeText;
	}
	public String getMatricAutoverified() {
		return matricAutoverified;
	}
	public void setMatricAutoverified(String matricAutoverified) {
		this.matricAutoverified = matricAutoverified;
	}
	public GeneralItem getMatricProvince() {
		return matricProvince;
	}
	public void setMatricProvince(GeneralItem matricProvince) {
		this.matricProvince = matricProvince;
	}
	public GeneralItem getMatricSchool() {
		return matricSchool;
	}
	public void setMatricSchool(GeneralItem matricSchool) {
		this.matricSchool = matricSchool;
	}
	public String getLicensee() {
		return licensee;
	}
	public void setLicensee(String licensee) {
		this.licensee = licensee;
	}
	public String getMatricCertOther() {
		return matricCertOther;
	}
	public void setMatricCertOther(String matricCertOther) {
		this.matricCertOther = matricCertOther;
	}
	public String getDocformToefl() {
		return docformToefl;
	}
	public void setDocformToefl(String docformToefl) {
		this.docformToefl = docformToefl;
	}
	public String getInternetApplication() {
		return internetApplication;
	}
	public void setInternetApplication(String internetApplication) {
		this.internetApplication = internetApplication;
	}		
	public String getCertificateDesc() {
		return certificateDesc;
	}
	public void setCertificateDesc(String certificateDesc) {
		this.certificateDesc = certificateDesc;
	}
	public String getRadioPrev() {
		return radioPrev;
	}
	public void setRadioPrev(String radioPrev) {
		this.radioPrev = radioPrev;
	}
	public String getRadioNDP() {
		return radioNDP;
	}
	public void setRadioNDP(String radioNDP) {
		this.radioNDP = radioNDP;
	}
	public String getRadioRPL() {
		return radioRPL;
	}
	public void setRadioRPL(String radioRPL) {
		this.radioRPL = radioRPL;
	}
	public String getRadioOfferQual1() {
		return radioOfferQual1;
	}
	public void setRadioOfferQual1(String radioOfferQual1) {
		this.radioOfferQual1 = radioOfferQual1;
	}
	public String getRadioOfferQual2() {
		return radioOfferQual2;
	}
	public void setRadioOfferQual2(String radioOfferQual2) {
		this.radioOfferQual2 = radioOfferQual2;
	}
	public String getQualNDP() {
		return qualNDP;
	}
	public void setQualNDP(String qualNDP) {
		this.qualNDP = qualNDP;
	}
	public boolean isNdpSub() {
		return ndpSub;
	}
	public void setNdpSub(boolean ndpSub) {
		this.ndpSub = ndpSub;
	}
	public ArrayList<String> getNdpRegList() {
		return ndpRegList;
	}
	public void setNdpRegList(ArrayList<String> ndpRegList) {
		this.ndpRegList = ndpRegList;
	}
	public String getNdpRegSu1() {
		return ndpRegSu1;
	}
	public void setNdpRegSu1(String ndpRegSu1) {
		this.ndpRegSu1 = ndpRegSu1;
	}
	public String getNdpRegSu2() {
		return ndpRegSu2;
	}
	public void setNdpRegSu2(String ndpRegSu2) {
		this.ndpRegSu2 = ndpRegSu2;
	}
	public String getNdpRegSu3() {
		return ndpRegSu3;
	}
	public void setNdpRegSu3(String ndpRegSu3) {
		this.ndpRegSu3 = ndpRegSu3;
	}
	public String getNdpRegSu4() {
		return ndpRegSu4;
	}
	public void setNdpRegSu4(String ndpRegSu4) {
		this.ndpRegSu4 = ndpRegSu4;
	}
	public String getNdpRegSu5() {
		return ndpRegSu5;
	}
	public void setNdpRegSu5(String ndpRegSu5) {
		this.ndpRegSu5 = ndpRegSu5;
	}
	public String getNdpRegSu6() {
		return ndpRegSu6;
	}
	public void setNdpRegSu6(String ndpRegSu6) {
		this.ndpRegSu6 = ndpRegSu6;
	}
	public String getNdpRegSu7() {
		return ndpRegSu7;
	}
	public void setNdpRegSu7(String ndpRegSu7) {
		this.ndpRegSu7 = ndpRegSu7;
	}
	public String getNdpRegSu8() {
		return ndpRegSu8;
	}
	public void setNdpRegSu8(String ndpRegSu8) {
		this.ndpRegSu8 = ndpRegSu8;
	}
	public String getNdpRegSu9() {
		return ndpRegSu9;
	}
	public void setNdpRegSu9(String ndpRegSu9) {
		this.ndpRegSu9 = ndpRegSu9;
	}
	public String getNdpRegSu10() {
		return ndpRegSu10;
	}
	public void setNdpRegSu10(String ndpRegSu10) {
		this.ndpRegSu10 = ndpRegSu10;
	}
		
}
