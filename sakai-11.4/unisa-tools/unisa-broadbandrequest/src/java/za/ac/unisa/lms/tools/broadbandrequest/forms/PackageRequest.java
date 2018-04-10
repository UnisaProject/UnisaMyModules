package za.ac.unisa.lms.tools.broadbandrequest.forms;

import za.ac.unisa.lms.dao.Gencod;

public class PackageRequest {
	private int year;
	private int sequenceNr;
	private String requestDate;
	private ServiceProviderCost serviceProviderCost;
	private Gencod regionalCentre;
	private String status;
	private String statusDisplayed;
	private String paymentReference;
	private String paymentDate;
	private String infoToSpDate;
	private String activationDate;
	private String terminationDate;
	private String cancellationDate;
	private String modemFlag;
	private String termsConditionFlag;
	private String transferFundsFlag;
	private String contactDetialFlag;
	private String cardMobileNr;	
	private String paymentType;	
	private int bundleNr;
	private int documentSeqNr;
	private String refundReference;
	private String refundDate;
	private String link;
	
	public String getRefundReference() {
		return refundReference;
	}
	public void setRefundReference(String refundReference) {
		this.refundReference = refundReference;
	}
	public String getRefundDate() {
		return refundDate;
	}
	public void setRefundDate(String refundDate) {
		this.refundDate = refundDate;
	}
	public String getContactDetialFlag() {
		return contactDetialFlag;
	}
	public void setContactDetialFlag(String contactDetialFlag) {
		this.contactDetialFlag = contactDetialFlag;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public int getSequenceNr() {
		return sequenceNr;
	}
	public void setSequenceNr(int sequenceNr) {
		this.sequenceNr = sequenceNr;
	}
	public String getRequestDate() {
		return requestDate;
	}
	public void setRequestDate(String requestDate) {
		this.requestDate = requestDate;
	}	
	public Gencod getRegionalCentre() {
		return regionalCentre;
	}
	public void setRegionalCentre(Gencod regionalCentre) {
		this.regionalCentre = regionalCentre;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getStatusDisplayed() {
		return statusDisplayed;
	}
	public void setStatusDisplayed(String statusDisplayed) {
		this.statusDisplayed = statusDisplayed;
	}
	public String getPaymentReference() {
		return paymentReference;
	}
	public void setPaymentReference(String paymentReference) {
		this.paymentReference = paymentReference;
	}
	public String getPaymentDate() {
		return paymentDate;
	}
	public void setPaymentDate(String paymentDate) {
		this.paymentDate = paymentDate;
	}
	public String getInfoToSpDate() {
		return infoToSpDate;
	}
	public void setInfoToSpDate(String infoToSpDate) {
		this.infoToSpDate = infoToSpDate;
	}
	public String getActivationDate() {
		return activationDate;
	}
	public void setActivationDate(String activationDate) {
		this.activationDate = activationDate;
	}
	public String getTerminationDate() {
		return terminationDate;
	}
	public void setTerminationDate(String terminationDate) {
		this.terminationDate = terminationDate;
	}
	public String getCancellationDate() {
		return cancellationDate;
	}
	public void setCancellationDate(String cancellationDate) {
		this.cancellationDate = cancellationDate;
	}
	public String getModemFlag() {
		return modemFlag;
	}
	public void setModemFlag(String modemFlag) {
		this.modemFlag = modemFlag;
	}
	public String getTermsConditionFlag() {
		return termsConditionFlag;
	}
	public void setTermsConditionFlag(String termsConditionFlag) {
		this.termsConditionFlag = termsConditionFlag;
	}
	public String getTransferFundsFlag() {
		return transferFundsFlag;
	}
	public void setTransferFundsFlag(String transferFundsFlag) {
		this.transferFundsFlag = transferFundsFlag;
	}
	public String getCardMobileNr() {
		return cardMobileNr;
	}
	public void setCardMobileNr(String cardMobileNr) {
		this.cardMobileNr = cardMobileNr;
	}
	public String getPaymentType() {
		return paymentType;
	}
	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}		
	public int getBundleNr() {
		return bundleNr;
	}
	public void setBundleNr(int bundleNr) {
		this.bundleNr = bundleNr;
	}
	public int getDocumentSeqNr() {
		return documentSeqNr;
	}
	public void setDocumentSeqNr(int documentSeqNr) {
		this.documentSeqNr = documentSeqNr;
	}
	public ServiceProviderCost getServiceProviderCost() {
		return serviceProviderCost;
	}
	public void setServiceProviderCost(ServiceProviderCost serviceProviderCost) {
		this.serviceProviderCost = serviceProviderCost;
	}
	
}
