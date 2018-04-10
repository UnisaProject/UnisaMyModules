package za.ac.unisa.lms.tools.broadbandrequest.forms;

import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.ValidatorActionForm;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import za.ac.unisa.lms.dao.Gencod;

public class BroadbandRequestForm extends ValidatorActionForm {
	private static final long serialVersionUID = 1L;
	private int studentNr;
	private Student student;
	private PackageRequest request;
	private List<ServiceProviderCost> listServiceProviderCost;
	private List<PackageRequest> listPackageRequest;
	private List<Gencod> listRegionalCentre;
	private List<Gencod> cellRanges;
	private String userAccess; 
	private String currentPage;
	private String studentAccountBalanceStr;
	private Double studentAccountBalanceDbl;
	private String amountDueStr;
	private Double amountDueDbl;
	private String selectedModemFlag;
	private String selectedServiceProviderCode;	
	private String tcsUrl;
	private String cancelMessage;
	private String spOfferModem;
	private int submitCounter;
	private boolean resetArraysStep1;
	private boolean resetArraysStep2;
	private String confirmActivationMobileNr;
	
	
	public List<Gencod> getCellRanges() {
		return cellRanges;
	}

	public void setCellRanges(List<Gencod> cellRanges) {
		this.cellRanges = cellRanges;
	}

	public String getConfirmActivationMobileNr() {
		return confirmActivationMobileNr;
	}

	public void setConfirmActivationMobileNr(String confirmActivationMobileNr) {
		this.confirmActivationMobileNr = confirmActivationMobileNr;
	}
	public int getSubmitCounter() {
		return submitCounter;
	}

	public void setSubmitCounter(int submitCounter) {
		this.submitCounter = submitCounter;
	}

	public String getSpOfferModem() {
		return spOfferModem;
	}

	public void setSpOfferModem(String spOfferModem) {
		this.spOfferModem = spOfferModem;
	}

	public String getTcsUrl() {
		return tcsUrl;
	}

	public void setTcsUrl(String tcsUrl) {
		this.tcsUrl = tcsUrl;
	}

	public String getCancelMessage() {
		return cancelMessage;
	}

	public void setCancelMessage(String cancelMessage) {
		this.cancelMessage = cancelMessage;
	}

	public void reset (ActionMapping mapping, HttpServletRequest request){
		if (this.isResetArraysStep1()){
			this.selectedModemFlag = new String();	
			this.resetArraysStep1=false;
		}
		if (this.isResetArraysStep2()){
			this.getRequest().setTermsConditionFlag(new String());
			this.getRequest().setContactDetialFlag(new String());
			this.getRequest().setTransferFundsFlag(new String());
			this.resetArraysStep2=false;
		}
	}
	
	public boolean isResetArraysStep1() {
		return resetArraysStep1;
	}
	public void setResetArraysStep1(boolean resetArraysStep1) {
		this.resetArraysStep1 = resetArraysStep1;
	}
	public boolean isResetArraysStep2() {
		return resetArraysStep2;
	}
	public void setResetArraysStep2(boolean resetArraysStep2) {
		this.resetArraysStep2 = resetArraysStep2;
	}	
	public String getSelectedModemFlag() {
		return selectedModemFlag;
	}

	public void setSelectedModemFlag(String selectedModemFlag) {
		this.selectedModemFlag = selectedModemFlag;
	}

	public String getSelectedServiceProviderCode() {
		return selectedServiceProviderCode;
	}

	public void setSelectedServiceProviderCode(String selectedServiceProviderCode) {
		this.selectedServiceProviderCode = selectedServiceProviderCode;
	}

	public String getAmountDueStr() {
		return amountDueStr;
	}
	public void setAmountDueStr(String amountDueStr) {
		this.amountDueStr = amountDueStr;
	}
	public Double getAmountDueDbl() {
		return amountDueDbl;
	}
	public void setAmountDueDbl(Double amountDueDbl) {
		this.amountDueDbl = amountDueDbl;
	}
	public String getStudentAccountBalanceStr() {
		return studentAccountBalanceStr;
	}
	public void setStudentAccountBalanceStr(String studentAccountBalanceStr) {
		this.studentAccountBalanceStr = studentAccountBalanceStr;
	}
	public Double getStudentAccountBalanceDbl() {
		return studentAccountBalanceDbl;
	}
	public void setStudentAccountBalanceDbl(Double studentAccountBalanceDbl) {
		this.studentAccountBalanceDbl = studentAccountBalanceDbl;
	}	
	public List<ServiceProviderCost> getListServiceProviderCost() {
		return listServiceProviderCost;
	}
	public void setListServiceProviderCost(
			List<ServiceProviderCost> listServiceProviderCost) {
		this.listServiceProviderCost = listServiceProviderCost;
	}
	public List<PackageRequest> getListPackageRequest() {
		return listPackageRequest;
	}
	public void setListPackageRequest(List<PackageRequest> listPackageRequest) {
		this.listPackageRequest = listPackageRequest;
	}
	public List<Gencod> getListRegionalCentre() {
		return listRegionalCentre;
	}
	public void setListRegionalCentre(List<Gencod> listRegionalCentre) {
		this.listRegionalCentre = listRegionalCentre;
	}
	public String getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(String currentPage) {
		this.currentPage = currentPage;
	}	
	public String getUserAccess() {
		return userAccess;
	}
	public void setUserAccess(String userAccess) {
		this.userAccess = userAccess;
	}
	public PackageRequest getRequest() {
		return request;
	}
	public void setRequest(PackageRequest request) {
		this.request = request;
	}	
	public int getStudentNr() {
		return studentNr;
	}
	public void setStudentNr(int studentNr) {
		this.studentNr = studentNr;
	}	
	public Student getStudent() {
		return student;
	}
	public void setStudent(Student student) {
		this.student = student;
	}
	

}
