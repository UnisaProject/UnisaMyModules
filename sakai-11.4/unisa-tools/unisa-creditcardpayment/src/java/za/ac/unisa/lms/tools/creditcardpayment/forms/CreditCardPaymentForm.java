//Created by MyEclipse Struts
// XSL source (default): platform:/plugin/com.genuitec.eclipse.cross.easystruts.eclipse_3.9.210/xslt/JavaClass.xsl

package za.ac.unisa.lms.tools.creditcardpayment.forms;

import java.util.List;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;

import za.ac.unisa.lms.domain.registration.Qualification;

/** 
 * MyEclipse Struts
 * Creation date: 09-16-2005
 * 
 * XDoclet definition:
 * @struts:form name="CreditCardPaymentForm"
 */
public class CreditCardPaymentForm extends ActionForm {

	// --------------------------------------------------------- Instance Variables

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Student student = new Student();
	private Qualification qual = new Qualification();
    private  String userWorkspaceAddr;
	//credit card
	private String cardNumber;
	private String cardHolder;
	private String cvvNo;
	private String expiryDate;
	private String expiryMonth;
	private String expiryYear;
	private String budgetPeriod;
	private String regStatus;
	private String regStatusDescription;
	private String summaryMessage;
	private boolean errorOccured = false;
	private boolean studentUser = false;
	// library card
	private boolean canChooseLibraryCard=true;  // for non-tp: true = show option on screen, else dont show
	private Double libraryFeeForStudent;// lib fee as determined by server for tp students
	private Double libraryFeeAmount;// lib fee to keep if student decides not to cancel fee after it was origionally cancelled
	private String formattedLibraryFeeForStudent="";// formatted string version of above field
	private String libraryFeeText;  	// fee display on screen
	private double libraryFee;			// fee in R in non-tp payments
	private String payLibraryFee;   	// tick box indicator
	
	//Library fine
	private double libraryFineFee;
	private String libraryFineFeeText;
	private String libraryFineFeeAmountInput;
	private double libraryFineFeeAmount;
	private String libCreditDebitIndicator;
	private String librayFineBalance;
	//3G
	private boolean canChooseThreeGDataBundle=true;  // for non-tp: true = show option on screen, else dont show
	private Double threeGDataBundleFeeForStudent;// 3G Data Bundle fee as determined by server for tp students
	private Double threeGDataBundleFeeAmount;// 3G Data Bundle fee to keep if student decides not to cancel fee after it was origionally cancelled
	private String formattedThreeGDataBundleFeeForStudent="";// formatted string version of above field
	private String threeGDataBundleFeeText;  	// fee display on screen
	private double threeGDataBundleFee;			// fee in R in non-tp payments
	private String payThreeGDataBundleFee;   	// tick box indicator
	// matric fee
	private boolean canChooseMatric=true;  // for non-tp: true = show option on screen, else dont show
	private Double matricFeeForStudent;		// MR fee as determined by server for tp students
	private String formattedMatricFeeForStudent="";// formatted string version of above field
	private double matricFirstAppFee;	    // fee in R for non-tp payments
	private String matricFirstAppFeeText;   // fee display on screen
	private String payMatricFirstAppFee;    // tick box indicator
	private String matricReApp;
	private String matricReAppFee;
	
	//lists
	private List monthList;
	private List yearList;
	private List budgetList;
	
	private double totalAmount = 0.00;
	// use this for credit card total amount to be paid
	private String ccTotalAmountInput = "0.00";
	private double ccTotalAmount = 0;
	private String balance = "";
	private double balanceAmount = 0.00;
	private double matricFee = 0.00;
	private String studyFeeAmountInput = "0.00";
	private double StudyFeeAmount = 0.00;
	private double fullAccount = 0.00;
	private double dueImmediately = 0.00;
	private double minimumForReg = 0.00;
	private String creditDebitIndicator="";
	private double minimumStudyFee = 0;  // used on tp screen to indicate minimum study fee payable
	
	private double applyAmount = 0; // used for student number application fee
	private String applyAmountInput = "0.00";
	
	// field to indicate whether a TP student can cancel his smartcard request
	private boolean smartCardChoice= false;
	private boolean threeGDataBundleChoice= false;
	private String cancelSmartCard="";
	private boolean cancel = false;
	
	public String toString() {
		
		StringBuilder result = new StringBuilder();
	    String NEW_LINE = System.getProperty("line.separator");

	    result.append(NEW_LINE+"{ Credit card payment details" + NEW_LINE);
	    result.append(toStringStudent());
	    result.append(toStringFees());
	    result.append("}");

	    return result.toString();
	}
	
	public String toStringStudent(){
		
		StringBuilder result = new StringBuilder();
	    String NEW_LINE = System.getProperty("line.separator");

	    result.append(NEW_LINE+"Stud Nr: " + student.getStudentNumber() + NEW_LINE);
	    result.append("Qual: " + qual.getQualCode() + NEW_LINE);
	    result.append("Reg status: " + regStatus + NEW_LINE);
	    result.append("Email: " + student.getEmailAddress() + NEW_LINE);

	    return result.toString();	
	}
	public void setUserWorkspaceAddr(String addr){
		this.userWorkspaceAddr=userWorkspaceAddr;
	}
	public String getUserWorkspaceAddr(){
		return userWorkspaceAddr;
	}
	public String toStringFees(){
		StringBuilder result = new StringBuilder();
	    String NEW_LINE = System.getProperty("line.separator");

	    result.append(NEW_LINE+"Balance: " + balance + NEW_LINE);
	    if("on".equalsIgnoreCase(payLibraryFee)){
	    	result.append("Library card: " + libraryFee + NEW_LINE);
	    }else{
	    	result.append("Library card: 0.0 "  + NEW_LINE);
	    }
	    if("on".equalsIgnoreCase(payMatricFirstAppFee)){
	    	result.append("Matric: " + matricFirstAppFee + NEW_LINE);
	    }else{
	    	result.append("Matric: 0.0"+ NEW_LINE);
	    }
	    if("on".equalsIgnoreCase(payThreeGDataBundleFee)){
	    	result.append("3G Data bundle: " + threeGDataBundleFee + NEW_LINE);
	    }else{
	    	result.append("3G Data bundle: 0.0"+ NEW_LINE);
	    }
	    result.append("Study fee: " + studyFeeAmountInput + NEW_LINE );
	    result.append("Total (lib, MR,3G Data bundle study fees): " + totalAmount + NEW_LINE);
	    result.append("Total cc: " + ccTotalAmount + NEW_LINE);
	    result.append("Card holder: " + cardHolder + NEW_LINE);
	    result.append("Budget months: " + budgetPeriod + NEW_LINE);

	    return result.toString();
	}
	
	
	public Student getStudent() {
		return student;
	}
	public void setStudent(Student student) {
		this.student = student;
	}
	public double getMatricFee() {
		return matricFee;
	}
	public void setMatricFee(double matricFee) {
		this.matricFee = matricFee;
	}
	public String getExpiryMonth() {
		return expiryMonth;
	}
	public void setExpiryMonth(String expiryMonth) {
		this.expiryMonth = expiryMonth;
	}
	public String getExpiryYear() {
		return expiryYear;
	}
	public void setExpiryYear(String expiryYear) {
		this.expiryYear = expiryYear;
	}
	public String getBudgetPeriod() {
		return budgetPeriod;
	}
	public void setBudgetPeriod(String budgetPeriod) {
		this.budgetPeriod = budgetPeriod;
	}
	public Qualification getQual() {
		return qual;
	}
	public void setQual(Qualification qual) {
		this.qual = qual;
	}
	public String getCardNumber() {
		return cardNumber;
	}
	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}
	public String getCardHolder() {
		return cardHolder;
	}
	public void setCardHolder(String cardHolder) {
		this.cardHolder = cardHolder;
	}
	public String getCvvNo() {
		return cvvNo;
	}
	public void setCvvNo(String cvvNo) {
		this.cvvNo = cvvNo;
	}
	public String getExpiryDate() {
		return expiryDate;
	}
	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
	}
	public String getRegStatus() {
		return regStatus;
	}
	public void setRegStatus(String regStatus) {
		this.regStatus = regStatus;
	}
	public double getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}
	public double getCcTotalAmount() {
		return ccTotalAmount;
	}
	public void setCcTotalAmount(double ccTotalAmount) {
		this.ccTotalAmount = ccTotalAmount;
	}
	public String getStudyFeeAmountInput() {
		return studyFeeAmountInput;
	}

	public void setStudyFeeAmountInput(String studyFeeAmountInput) {
		this.studyFeeAmountInput = studyFeeAmountInput;
	}

	public double getStudyFeeAmount() {
		return StudyFeeAmount;
	}

	public void setStudyFeeAmount(double studyFeeAmount) {
		StudyFeeAmount = studyFeeAmount;
	}

	public List getMonthList() {
		return monthList;
	}
	public void setMonthList(List monthList) {
		this.monthList = monthList;
	}
	public List getYearList() {
		return yearList;
	}
	public void setYearList(List yearList) {
		this.yearList = yearList;
	}
	public List getBudgetList() {
		return budgetList;
	}
	public void setBudgetList(List budgetList) {
		this.budgetList = budgetList;
	}
	public String getMatricReApp() {
		return matricReApp;
	}
	public void setMatricReApp(String matricReApp) {
		this.matricReApp = matricReApp;
	}
	public double getLibraryFee() {
		return libraryFee;
	}
	public void setLibraryFee(double libraryFee) {
		this.libraryFee = libraryFee;
	}
	public double getMatricFirstAppFee() {
		return matricFirstAppFee;
	}
	public void setMatricFirstAppFee(double matricFirstAppFee) {
		this.matricFirstAppFee = matricFirstAppFee;
	}
	public String getMatricReAppFee() {
		return matricReAppFee;
	}
	public void setMatricReAppFee(String matricReAppFee) {
		this.matricReAppFee = matricReAppFee;
	}
	public double getFullAccount() {
		return fullAccount;
	}
	public void setFullAccount(double fullAccount) {
		this.fullAccount = fullAccount;
	}
	public String getSummaryMessage() {
		return summaryMessage;
	}
	public void setSummaryMessage(String summaryMessage) {
		this.summaryMessage = summaryMessage;
	}
	public String getPayLibraryFee() {
		return payLibraryFee;
	}
	public void setPayLibraryFee(String payLibraryFee) {
		this.payLibraryFee = payLibraryFee;
	}
	public String getPayMatricFirstAppFee() {
		return payMatricFirstAppFee;
	}
	public void setPayMatricFirstAppFee(String payMatricFirstAppFee) {
		this.payMatricFirstAppFee = payMatricFirstAppFee;
	}
	public String getLibraryFeeText() {
		return libraryFeeText;
	}
	public void setLibraryFeeText(String libraryFeeText) {
		this.libraryFeeText = libraryFeeText;
	}
	public String getMatricFirstAppFeeText() {
		return matricFirstAppFeeText;
	}
	public void setMatricFirstAppFeeText(String matricFirstAppFeeText) {
		this.matricFirstAppFeeText = matricFirstAppFeeText;
	}
	public String getRegStatusDescription() {
		return regStatusDescription;
	}
	public void setRegStatusDescription(String regStatusDescription) {
		this.regStatusDescription = regStatusDescription;
	}

	public String getCreditDebitIndicator() {
		return creditDebitIndicator;
	}

	public void setCreditDebitIndicator(String creditDebitIndicator) {
		this.creditDebitIndicator = creditDebitIndicator;
	}

	public String getBalance() {
		return balance;
	}

	public void setBalance(String balance) {
		this.balance = balance;
	}

	
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		payLibraryFee="";
		payMatricFirstAppFee="";	
		// reset checkbox field
		cancel = false;	
		
	}

	public Double getLibraryFeeForStudent() {
		return libraryFeeForStudent;
	}

	public void setLibraryFeeForStudent(Double libraryFeeForStudent) {
		this.libraryFeeForStudent = libraryFeeForStudent;
	}

	public Double getMatricFeeForStudent() {
		return matricFeeForStudent;
	}

	public void setMatricFeeForStudent(Double matricFeeForStudent) {
		this.matricFeeForStudent = matricFeeForStudent;
	}

	public double getDueImmediately() {
		return dueImmediately;
	}

	public void setDueImmediately(double dueImmediately) {
		this.dueImmediately = dueImmediately;
	}

	public String getFormattedMatricFeeForStudent() {
		return formattedMatricFeeForStudent;
	}

	public void setFormattedMatricFeeForStudent(String formattedMatricFeeForStudent) {
		this.formattedMatricFeeForStudent = formattedMatricFeeForStudent;
	}

	public String getFormattedLibraryFeeForStudent() {
		return formattedLibraryFeeForStudent;
	}

	public void setFormattedLibraryFeeForStudent(
			String formattedLibraryFeeForStudent) {
		this.formattedLibraryFeeForStudent = formattedLibraryFeeForStudent;
	}

	public boolean isCanChooseLibraryCard() {
		return canChooseLibraryCard;
	}

	public void setCanChooseLibraryCard(boolean canChooseLibraryCard) {
		this.canChooseLibraryCard = canChooseLibraryCard;
	}

	public double getMinimumStudyFee() {
		return minimumStudyFee;
	}

	public void setMinimumStudyFee(double minimumStudyFee) {
		this.minimumStudyFee = minimumStudyFee;
	}

	public String getCcTotalAmountInput() {
		return ccTotalAmountInput;
	}

	public void setCcTotalAmountInput(String ccTotalAmountInput) {
		this.ccTotalAmountInput = ccTotalAmountInput;
	}

	public boolean isErrorOccured() {
		return errorOccured;
	}

	public void setErrorOccured(boolean errorOccured) {
		this.errorOccured = errorOccured;
	}

	public boolean isCanChooseMatric() {
		return canChooseMatric;
	}

	public void setCanChooseMatric(boolean canChooseMatric) {
		this.canChooseMatric = canChooseMatric;
	}

	public boolean isStudentUser() {
		return studentUser;
	}

	public void setStudentUser(boolean studentUser) {
		this.studentUser = studentUser;
	}

	public double getMinimumForReg() {
		return minimumForReg;
	}

	public void setMinimumForReg(double minimumForReg) {
		this.minimumForReg = minimumForReg;
	}

	public double getApplyAmount() {
		return applyAmount;
	}

	public void setApplyAmount(double applyAmount) {
		this.applyAmount = applyAmount;
	}

	public String getApplyAmountInput() {
		return applyAmountInput;
	}

	public void setApplyAmountInput(String applyAmountInput) {
		this.applyAmountInput = applyAmountInput;
	}

	public boolean isSmartCardChoice() {
		return smartCardChoice;
	}

	public void setSmartCardChoice(boolean smartCardChoice) {
		this.smartCardChoice = smartCardChoice;
	}

	public String getCancelSmartCard() {
		return cancelSmartCard;
	}

	public void setCancelSmartCard(String cancelSmartCard) {
		this.cancelSmartCard = cancelSmartCard;
	}

	public Double getLibraryFeeAmount() {
		return libraryFeeAmount;
	}

	public void setLibraryFeeAmount(Double libraryFeeAmount) {
		this.libraryFeeAmount = libraryFeeAmount;
	}

	public boolean isCancel() {
		return cancel;
	}

	public void setCancel(boolean cancel) {
		this.cancel = cancel;
	}
	public  boolean isCanChooseThreeGDataBundle(){
		return canChooseThreeGDataBundle;
	}
	public void setCanChooseThreeGDataBundle(boolean canChooseThreeGDataBundle){
		  this.canChooseThreeGDataBundle=canChooseThreeGDataBundle;
	}
	
	public Double getThreeGDataBundleFeeForStudent(){
		return threeGDataBundleFeeForStudent;
	}
	public void setThreeGDataBundleForStudent(Double threeGDataBundleFeeForStudent){
		  this.threeGDataBundleFeeForStudent=threeGDataBundleFeeForStudent;
	}
	
	public Double  getThreeGDataBundleFeeAmount(){
		return threeGDataBundleFeeAmount;
	}
	public void setThreeGDataBundleFeeAmount(Double threeGDataBundleFeeAmount){
		  this.threeGDataBundleFeeAmount=threeGDataBundleFeeAmount;
	}
	public String getFormattedThreeGDataBundleFeeForStudent(){
		return formattedThreeGDataBundleFeeForStudent;
	}
	public void setFormattedThreeGDataBundleFeeForStudent(String formattedThreeGDataBundleFeeForStudent){
		this.formattedThreeGDataBundleFeeForStudent=formattedThreeGDataBundleFeeForStudent;
	}
	public String  getThreeGDataBundleFeeText(){
		return threeGDataBundleFeeText;
	}
	public void setThreeGDataBundleFeeText(String threeGDataBundleFeeText){
		this.threeGDataBundleFeeText=threeGDataBundleFeeText;
	}
	public double  getThreeGDataBundleFee(){
		return threeGDataBundleFee;
	}
	public void setThreeGDataBundleFee(double threeGDataBundleFee){
		this.threeGDataBundleFee=threeGDataBundleFee;
	}
	public String  getPayThreeGDataBundleFee(){
		return payThreeGDataBundleFee;
	}
	public void setPayThreeGDataBundleFee(String payThreeGDataBundleFee){
		this.payThreeGDataBundleFee=payThreeGDataBundleFee;
	}

	public double getLibraryFineFee() {
		return libraryFineFee;
	}

	public void setLibraryFineFee(double libraryFineFee) {
		this.libraryFineFee = libraryFineFee;
	}
public String getLibraryFineFeeText() {
		return libraryFineFeeText;
	}

	public void setLibraryFineFeeText(String libraryFineFeeText) {
		this.libraryFineFeeText = libraryFineFeeText;
	}

	public String getLibraryFineFeeAmountInput() {
		return libraryFineFeeAmountInput;
	}

	public void setLibraryFineFeeAmountInput(String libraryFineFeeAmountInput) {
		this.libraryFineFeeAmountInput = libraryFineFeeAmountInput;
	}

	public double getLibraryFineFeeAmount() {
		return libraryFineFeeAmount;
	}

	public void setLibraryFineFeeAmount(double libraryFineFeeAmount) {
		this.libraryFineFeeAmount = libraryFineFeeAmount;
	}

	public String getLibCreditDebitIndicator() {
		return libCreditDebitIndicator;
	}

	public void setLibCreditDebitIndicator(String libCreditDebitIndicator) {
		this.libCreditDebitIndicator = libCreditDebitIndicator;
	}

	public String getLibrayFineBalance() {
		return librayFineBalance;
	}

	public void setLibrayFineBalance(String librayFineBalance) {
		this.librayFineBalance = librayFineBalance;
	}

	public double getBalanceAmount() {
		return balanceAmount;
	}

	public void setBalanceAmount(double balanceAmount) {
		this.balanceAmount = balanceAmount;
	}
	
}

