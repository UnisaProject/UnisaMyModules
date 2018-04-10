//Created by MyEclipse Struts
// XSL source (default): platform:/plugin/com.genuitec.eclipse.cross.easystruts.eclipse_3.9.210/xslt/JavaClass.xsl

package za.ac.unisa.lms.tools.regdetails.forms;

import java.util.ArrayList;
import java.util.Map;
import java.util.Vector;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionServlet;
import org.apache.struts.action.ActionServletWrapper;
import org.apache.struts.upload.MultipartRequestHandler;
import org.apache.struts.validator.ValidatorActionForm;

/**
 * MyEclipse Struts
 * Creation date: 10-14-2005
 *
 * XDoclet definition:
 * @struts:form name="regDetailsForm"
 */
public class RegDetailsForm extends ValidatorActionForm {

	// --------------------------------------------------------- Instance Variables

	private static final long serialVersionUID = 1L;
	private static final String version = "2018001";
	private String listType; // A=all study units, S=specific, T=type in
	private String orderType; //order by code or description
	private int numberOfUnits; // number of study units to be added

	private String completeQual;
	private String email;
//	 Current registration list
	private ArrayList studyUnits;
	// sruaf01 studyUnit list
	private ArrayList<StudyUnit> studyUnitsFromSruaf01 = new ArrayList<StudyUnit>();
//	 Application for registration list
	private ArrayList applyForStudyUnits;
//	 Supplement/aegrotat/special registration list
	private ArrayList supplementStudyUnits;
	private String studentNr;
	private String studentName;
	private Qualification qual;	// student current qual
	private String amendQual="0"; // amend qual
	private Qualification newQual;
	private String selectedQual;
	private String selectedSpec;
	private String valueReg0;
	private String valueReg1;
	private String valueReg2;
	private String valueReg6;
	private ArrayList listStudyUnits;
	private ArrayList additionalStudyUnits;
	private ArrayList selectedAdditionalStudyUnits;
	private ArrayList cancelStudyUnits;
	private ArrayList exchangeStudyUnits;
	private ArrayList pickList;
	private String paymentType;
	private String[] selectedItems;
	private String exSem1;
	private String exSem2;
	private String acadYear;
	private String acadPeriod;
	private String regPeriodOpen0;
	private String regPeriodOpen1;
	private String regPeriodOpen2;
	private String regPeriodOpen6;
	private boolean showQuote = false;
	private boolean nonFormal=false;
	private boolean regAnnual=false;
	private String disclaimer;
	private String submissionTimeStamp;
	private boolean selfhelp=true; //selfhelp to F139 always possible by default
	private String selfHelpReason = "";
	private String deliveryType;
	private String qualStatus;
    private String selfhelpErrorMsg="";
    // used to indicate that these questions has to be asked
    private boolean wil = false;
    private boolean odl= false;
    private int creditTotal = 0;

	// workflow information
	private String workflowDesc="";
	private String workfowTimestamp="";
	private String workflowReasonCode="";
	
	// final year
	private ArrayList<StudyUnit> finalYearStudyUnits;
	private String su1="";
	private String su2="";
	private String moduleCount="";
	private String moduleAverage="";
	
	/** Address */
	private Vector postal;
	private Vector physical;
	private Vector courier;
	private Address postalAddress = new Address();
	private Address physicalAddress = new Address();
	private Address courierAddress = new Address();

	/** Contact details */
	private String cellNumber;
	private String workNumber;
	private String faxNumber;
	private String contactNumber;
	private String homeNumber;
	private String emailAddress;
	
	/** Phased Out Qualification */
	private String phasedOutDeclare="";
	
	
	private boolean checkPostal = false;
	private boolean checkPhysical = false;
	private boolean checkCourier = false;
	private boolean postalAddressValid = false;
	private boolean physicalAddressValid = false;
	private boolean courierAddressValid = false;
	private boolean postalSubCodeValid = false;
	private boolean physicalSubCodeValid = false;
	private boolean courierSubCodeValid = false;
	
	private boolean crocDate = false;
	private String crocMyLife = "";
	private boolean crocLetterRequest = false;
	
	// --------------------------------------------------------- Methods

	public String getVersion() {
		return version;
	}
	
	public String getModuleCount() {
		return moduleCount;
	}

	public void setModuleCount(String moduleCount) {
		this.moduleCount = moduleCount;
	}

	public String getModuleAverage() {
		return moduleAverage;
	}

	public void setModuleAverage(String moduleAverage) {
		this.moduleAverage = moduleAverage;
	}

	public String getSu1() {
		return su1;
	}

	public void setSu1(String su1) {
		this.su1 = su1;
	}

	public String getSu2() {
		return su2;
	}

	public void setSu2(String su2) {
		this.su2 = su2;
	}

	public StudyUnit getFinalYearStudyUnits(int index) {
		return (StudyUnit)finalYearStudyUnits.get(index);
	}

	public ArrayList<StudyUnit> getFinalYearStudyUnits() {
		return finalYearStudyUnits;
	}

	public void setFinalYearStudyUnits(ArrayList<StudyUnit> finalYearStudyUnits) {
		this.finalYearStudyUnits = finalYearStudyUnits;
	}

	public void setFinalYearStudyUnits(StudyUnit studyUnit, int index) {
		this.finalYearStudyUnits.set(index,studyUnit);
	}


	public String getWorkflowDesc() {
		return workflowDesc;
	}

	public void setWorkflowDesc(String workflowDesc) {
		this.workflowDesc = workflowDesc;
	}

	public String getWorkflowReasonCode() {
		return workflowReasonCode;
	}

	public void setWorkflowReasonCode(String workflowReasonCode) {
		this.workflowReasonCode = workflowReasonCode;
	}

	public String getWorkfowTimestamp() {
		return workfowTimestamp;
	}

	public void setWorkfowTimestamp(String workfowTimestamp) {
		this.workfowTimestamp = workfowTimestamp;
	}

	public String getSelfhelpErrorMsg() {
		return selfhelpErrorMsg;
	}

	public void setSelfhelpErrorMsg(String selfhelpErrorMsg) {
		this.selfhelpErrorMsg = selfhelpErrorMsg;
	}

	public String getDeliveryType() {
		return deliveryType;
	}

	public void setDeliveryType(String deliveryType) {
		this.deliveryType = deliveryType;
	}

	public String getSubmissionTimeStamp() {
		return submissionTimeStamp;
	}

	public void setSubmissionTimeStamp(String submissionTimeStamp) {
		this.submissionTimeStamp = submissionTimeStamp;
	}

	public String getDisclaimer() {
		return disclaimer;
	}

	public void setDisclaimer(String disclaimer) {
		this.disclaimer = disclaimer;
	}

	public String getAcadYear() {
		return acadYear;
	}

	public void setAcadYear(String acadYear) {
		this.acadYear = acadYear;
	}
	
	public String getAcadPeriod() {
		return acadPeriod;
	}

	public void setAcadPeriod(String acadPeriod) {
		this.acadPeriod = acadPeriod;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public StudyUnit getCancelStudyUnits(int index) {
		return (StudyUnit)cancelStudyUnits.get(index);
	}

	public void setCancelStudyUnits(StudyUnit studyUnit, int index) {
		this.cancelStudyUnits.set(index,studyUnit);
	}

	public ArrayList getCancelStudyUnits() {
		return cancelStudyUnits;
	}

	public void setCancelStudyUnits(ArrayList cancelStudyUnits) {
		this.cancelStudyUnits = cancelStudyUnits;
	}
	public StudyUnit getAdditionalStudyUnits(int index) {
		return (StudyUnit)additionalStudyUnits.get(index);
	}

	public void setAdditionalStudyUnits(StudyUnit studyUnit, int index) {
		this.additionalStudyUnits.set(index,studyUnit);
	}

	public ArrayList getAdditionalStudyUnits() {
		return additionalStudyUnits;
	}

	public void setAdditionalStudyUnits(ArrayList additionalStudyUnits) {
		this.additionalStudyUnits = additionalStudyUnits;
	}
	public String getSelectedQual() {
		return selectedQual;
	}

	public void setSelectedQual(String selectedQual) {
		this.selectedQual = selectedQual;
	}

	/**
	 * Returns the listType.
	 * @return String
	 */
	public String getListType() {
		return listType;
	}

	/**
	 * Set the listType.
	 * @param listType The listType to set
	 */
	public void setListType(String listType) {
		this.listType = listType;
	}

	/**
	 * Returns the completeQual.
	 * @return String
	 */
	public String getCompleteQual() {
		return completeQual;
	}

	/**
	 * Set the completeQual.
	 * @param completeQual The completeQual to set
	 */
	public void setCompleteQual(String completeQual) {
		this.completeQual = completeQual;
	}

	/**
	 * Returns the email.
	 * @return String
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Set the email.
	 * @param email The email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Returns the studyUnits.
	 * @return Vector
	 */
	public ArrayList getStudyUnits() {
		return studyUnits;
	}

	/**
	 * Set the studyUnits.
	 * @param studyUnits The studyUnits to set
	 */
	public void setStudyUnits(ArrayList studyUnits) {
		this.studyUnits = studyUnits;
	}

	/**
	 * Returns the studentNr.
	 * @return String
	 */
	public String getStudentNr() {
		return studentNr;
	}

	/**
	 * Set the studentNr.
	 * @param studentNr The studentNr to set
	 */
	public void setStudentNr(String studentNr) {
		this.studentNr = studentNr;
	}

	public String getAmendQual() {
		return amendQual;
	}

	public void setAmendQual(String amendQual) {
		this.amendQual = amendQual;
	}

	public Qualification getNewQual() {
		return newQual;
	}

	public void setNewQual(Qualification newQual) {
		this.newQual = newQual;
	}

	public void setQual(Qualification qual) {
		this.qual = qual;
	}

	public Qualification getQual() {
		return qual;
	}

	public String getSelectedSpec() {
		return selectedSpec;
	}

	public void setSelectedSpec(String selectedSpec) {
		this.selectedSpec = selectedSpec;
	}

	public String getValueReg0() {
		return valueReg0;
	}

	public void setValueReg0(String valueReg0) {
		this.valueReg0 = valueReg0;
	}

	public String getValueReg1() {
		return valueReg1;
	}

	public void setValueReg1(String valueReg1) {
		this.valueReg1 = valueReg1;
	}

	public String getValueReg2() {
		return valueReg2;
	}

	public void setValueReg2(String valueReg2) {
		this.valueReg2 = valueReg2;
	}

	public String getValueReg6() {
		return valueReg6;
	}

	public void setValueReg6(String valueReg6) {
		this.valueReg6 = valueReg6;
	}

	public String[] getSelectedItems() {
		return selectedItems;
	}

	public void setSelectedItems(String[] selectedItems) {
		this.selectedItems = selectedItems;
	}

	public ArrayList getExchangeStudyUnits() {
		return exchangeStudyUnits;
	}

	public void setExchangeStudyUnits(ArrayList exchangeStudyUnits) {
		this.exchangeStudyUnits = exchangeStudyUnits;
	}

	public String getExSem1() {
		return exSem1;
	}

	public void setExSem1(String exSem1) {
		this.exSem1 = exSem1;
	}

	public String getExSem2() {
		return exSem2;
	}

	public void setExSem2(String exSem2) {
		this.exSem2 = exSem2;
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public ArrayList getPickList() {
		return pickList;
	}

	public void setPickList(ArrayList pickList) {
		this.pickList = pickList;
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

	public boolean isNonFormal() {
		return nonFormal;
	}

	public void setNonFormal(boolean nonFormal) {
		this.nonFormal = nonFormal;
	}

	public boolean isShowQuote() {
		return showQuote;
	}

	public void setShowQuote(boolean showQuote) {
		this.showQuote = showQuote;
	}

	public boolean isRegAnnual() {
		return regAnnual;
	}

	public void setRegAnnual(boolean regAnnual) {
		this.regAnnual = regAnnual;
	}

	public ArrayList getApplyForStudyUnits() {
		return applyForStudyUnits;
	}

	public void setApplyForStudyUnits(ArrayList applyForStudyUnits) {
		this.applyForStudyUnits = applyForStudyUnits;
	}

	public ArrayList getSupplementStudyUnits() {
		return supplementStudyUnits;
	}

	public void setSupplementStudyUnits(ArrayList supplementStudyUnits) {
		this.supplementStudyUnits = supplementStudyUnits;
	}

	public int getNumberOfUnits() {
		return numberOfUnits;
	}

	public void setNumberOfUnits(int numberOfUnits) {
		this.numberOfUnits = numberOfUnits;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public ArrayList getListStudyUnits() {
		return listStudyUnits;
	}

	public void setListStudyUnits(ArrayList listStudyUnits) {
		this.listStudyUnits = listStudyUnits;
	}

	public boolean isCourierAddressValid() {
		return courierAddressValid;
	}

	public void setCourierAddressValid(boolean courierAddressValid) {
		this.courierAddressValid = courierAddressValid;
	}

	public ArrayList getSelectedAdditionalStudyUnits() {
		return selectedAdditionalStudyUnits;
	}

	public void setSelectedAdditionalStudyUnits(ArrayList selectedAdditionalStudyUnits) {
		this.selectedAdditionalStudyUnits = selectedAdditionalStudyUnits;
	}

	public ArrayList<StudyUnit> getStudyUnitsFromSruaf01() {
		return studyUnitsFromSruaf01;
	}

	public void setStudyUnitsFromSruaf01(ArrayList<StudyUnit> studyUnitsFromSruaf01) {
		this.studyUnitsFromSruaf01 = studyUnitsFromSruaf01;
	}

	public boolean isSelfhelp() {
		return selfhelp;
	}

	public void setSelfhelp(boolean selfhelp) {
		this.selfhelp = selfhelp;
	}

	public String getSelfHelpReason() {
		return selfHelpReason;
	}

	public void setSelfHelpReason(String selfHelpReason) {
		this.selfHelpReason = selfHelpReason;
	}

	public String getQualStatus() {
		return qualStatus;
	}

	public void setQualStatus(String qualStatus) {
		this.qualStatus = qualStatus;
	}

	public boolean isOdl() {
		return odl;
	}

	public void setOdl(boolean odl) {
		this.odl = odl;
	}

	public boolean isWil() {
		return wil;
	}

	public void setWil(boolean wil) {
		this.wil = wil;
	}

	public int getCreditTotal() {
		return creditTotal;
	}

	public void setCreditTotal(int creditTotal) {
		this.creditTotal = creditTotal;
	}

	public Vector getPostal() {
		return postal;
	}

	public void setPostal(Vector postal) {
		this.postal = postal;
	}

	public Vector getPhysical() {
		return physical;
	}

	public void setPhysical(Vector physical) {
		this.physical = physical;
	}

	public Vector getCourier() {
		return courier;
	}

	public void setCourier(Vector courier) {
		this.courier = courier;
	}

	public Address getPostalAddress() {
		return postalAddress;
	}

	public void setPostalAddress(Address postalAddress) {
		this.postalAddress = postalAddress;
	}

	public Address getPhysicalAddress() {
		return physicalAddress;
	}

	public void setPhysicalAddress(Address physicalAddress) {
		this.physicalAddress = physicalAddress;
	}

	public Address getCourierAddress() {
		return courierAddress;
	}

	public void setCourierAddress(Address courierAddress) {
		this.courierAddress = courierAddress;
	}

	public String getCellNumber() {
		return cellNumber;
	}

	public void setCellNumber(String cellNumber) {
		this.cellNumber = cellNumber;
	}

	public String getWorkNumber() {
		return workNumber;
	}

	public void setWorkNumber(String workNumber) {
		this.workNumber = workNumber;
	}

	public String getFaxNumber() {
		return faxNumber;
	}

	public void setFaxNumber(String faxNumber) {
		this.faxNumber = faxNumber;
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	public String getHomeNumber() {
		return homeNumber;
	}

	public void setHomeNumber(String homeNumber) {
		this.homeNumber = homeNumber;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getPhasedOutDeclare() {
		return phasedOutDeclare;
	}

	public void setPhasedOutDeclare(String phasedOutDeclare) {
		this.phasedOutDeclare = phasedOutDeclare;
	}

	public boolean isCheckPostal() {
		return checkPostal;
	}

	public void setCheckPostal(boolean checkPostal) {
		this.checkPostal = checkPostal;
	}
	
	public boolean isCheckPhysical() {
		return checkPhysical;
	}

	public void setCheckPhysical(boolean checkPhysical) {
		this.checkPhysical = checkPhysical;
	}
	
	public boolean isCheckCourier() {
		return checkCourier;
	}

	public void setCheckCourier(boolean checkCourier) {
		this.checkCourier = checkCourier;
	}
	
	public boolean isCourierValid() {
		return courierAddressValid;
	}

	public void setCourierValid(boolean courierAddressValid) {
		this.courierAddressValid = courierAddressValid;
		
	}
	
	public boolean isCourierSubCodeValid() {
		return courierSubCodeValid;
	}

	public void setCourierSubCodeValid(boolean courierSubCodeValid) {
		this.courierSubCodeValid = courierSubCodeValid;
		
	}

	public boolean isPostalAddressValid() {
		return postalAddressValid;
	}

	public void setPostalAddressValid(boolean postalAddressValid) {
		this.postalAddressValid = postalAddressValid;
	}

	public boolean isPhysicalAddressValid() {
		return physicalAddressValid;
	}

	public void setPhysicalAddressValid(boolean physicalAddressValid) {
		this.physicalAddressValid = physicalAddressValid;
	}

	public boolean isPostalSubCodeValid() {
		return postalSubCodeValid;
	}

	public void setPostalSubCodeValid(boolean postalSubCodeValid) {
		this.postalSubCodeValid = postalSubCodeValid;
	}

	public boolean isPhysicalSubCodeValid() {
		return physicalSubCodeValid;
	}

	public void setPhysicalSubCodeValid(boolean physicalSubCodeValid) {
		this.physicalSubCodeValid = physicalSubCodeValid;
	}

	public boolean isCrocDate() {
		return crocDate;
	}

	public void setCrocDate(boolean crocDate) {
		this.crocDate = crocDate;
	}

	public String getCrocMyLife() {
		return crocMyLife;
	}

	public void setCrocMyLife(String crocMyLife) {
		this.crocMyLife = crocMyLife;
	}

	public boolean isCrocLetterRequest() {
		return crocLetterRequest;
	}

	public void setCrocLetterRequest(boolean crocLetterRequest) {
		this.crocLetterRequest = crocLetterRequest;
	}

}

