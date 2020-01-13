package za.ac.unisa.lms.tools.lmsmis.forms;

import java.text.NumberFormat;
import java.util.ArrayList;

import org.apache.struts.validator.ValidatorForm;

public class misForm extends ValidatorForm{
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private String view;
	private String action;
	private String currentYear;
	private String previousYear;
	private String year;
	private String unisaDate;
	private ArrayList studentsJoin = new ArrayList();
	private ArrayList studentsLogin = new ArrayList();
	private ArrayList studentsVisits = new ArrayList();
	private ArrayList lecturerVisits = new ArrayList();
	private ArrayList lecturerLogins = new ArrayList();
	private ArrayList passwordChanges = new ArrayList();
	private ArrayList registrationDetials = new ArrayList();
	private ArrayList addTransactions = new ArrayList();
	private ArrayList cancelTransactions = new ArrayList();
	private ArrayList semesterChanges =new ArrayList();
	private ArrayList emailsLecturer =new ArrayList();
	private ArrayList classUpdates =new ArrayList();
	private ArrayList academicRecord =new ArrayList();
	private ArrayList bioDetials =new ArrayList();
	private ArrayList directAddress =new ArrayList();
	private ArrayList indirectAddress =new ArrayList();
	private ArrayList contactChanges =new ArrayList();
	private ArrayList optionChanges =new ArrayList();
	private ArrayList centreChanges =new ArrayList();
	private ArrayList cardPayments =new ArrayList();
	private ArrayList currentFinancial =new ArrayList();
	private ArrayList historicFinancial =new ArrayList();
	private ArrayList feeQuotation =new ArrayList();
	private ArrayList parcelInformation = new ArrayList();
	private ArrayList ebookShop =new ArrayList();
	private ArrayList ebookshopAdditions =new ArrayList();
	private ArrayList shopEdits =new ArrayList();
	private ArrayList shopDeleted =new ArrayList();
	private ArrayList messagePosted = new ArrayList();
	private ArrayList itemsAdded = new ArrayList();
	private ArrayList annouceAdded = new ArrayList();
	private ArrayList emailsSent = new ArrayList();
	private ArrayList calendarItems = new ArrayList();
	private ArrayList listCompiled = new ArrayList();
	private ArrayList listsDownloaded = new ArrayList();
	private ArrayList messageUpdated = new ArrayList();
	private ArrayList fileAdded = new ArrayList();
	private ArrayList websitesAdded = new ArrayList();
	private ArrayList resourceRead = new ArrayList();
	private ArrayList statsList;
	private String total;
	private String selectedYear;
	private ArrayList activeStudents;
	private ArrayList activeLectures;
	private ArrayList toasterWrites =new ArrayList();
	private ArrayList toasterNoWrites =new ArrayList();
	private ArrayList toasterCancels =new ArrayList();


	public ArrayList getToasterWrites() {
		return toasterWrites;
	}

	public void setToasterWrites(ArrayList toasterWrites) {
		this.toasterWrites = toasterWrites;
	}

	public ArrayList getToasterNoWrites() {
		return toasterNoWrites;
	}

	public void setToasterNoWrites(ArrayList toasterNoWrites) {
		this.toasterNoWrites = toasterNoWrites;
	}

	public ArrayList getToasterCancels() {
		return toasterCancels;
	}

	public void setToasterCancels(ArrayList toasterCancels) {
		this.toasterCancels = toasterCancels;
	}

	public ArrayList getActiveLectures() {
		return activeLectures;
	}

	public void setActiveLectures(ArrayList activeLectures) {
		this.activeLectures = activeLectures;
	}

	public ArrayList getActiveStudents() {
		return activeStudents;
	}

	public void setActiveStudents(ArrayList activeStudents) {
		this.activeStudents = activeStudents;
	}

	public String getSelectedYear() {
		return selectedYear;
	}

	public void setSelectedYear(String selectedYear) {
		this.selectedYear = selectedYear;
	}

	public ArrayList getLecturerLogins() {
		return lecturerLogins;
	}

	public void setLecturerLogins(ArrayList lecturerLogins) {
		this.lecturerLogins = lecturerLogins;
	}

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	public String getView() {
		return view;
	}

	public void setView(String view) {
		this.view = view;
	}

	public String getCurrentYear() {
		return currentYear;
	}

	public void setCurrentYear(String currentYear) {
		this.currentYear = currentYear;
	}

	public String getPreviousYear() {
		return previousYear;
	}

	public void setPreviousYear(String previousYear) {
		this.previousYear = previousYear;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public ArrayList getStudentsJoin() {
		return studentsJoin;
	}

	public void setStudentsJoin(ArrayList studentsJoin) {
		this.studentsJoin = studentsJoin;
	}

	public ArrayList getStatsList() {
		return statsList;
	}

	public void setStatsList(ArrayList statsList) {
		this.statsList = statsList;
	}

	public ArrayList getStudentsLogin() {
		return studentsLogin;
	}

	public void setStudentsVisits(ArrayList studentsVisits) {
		this.studentsVisits = studentsVisits;
	}

	public ArrayList getStudentsVisits() {
		//NumberFormat form;
		//applyPattern
		return studentsVisits;
	}

	public void setStudentsLogin(ArrayList studentsLogin) {
		this.studentsLogin = studentsLogin;
	}

	public ArrayList getLecturerVisits() {
		return lecturerVisits;
	}

	public void setLecturerVisits(ArrayList lecturerVisits) {
		this.lecturerVisits = lecturerVisits;
	}

	public ArrayList getPasswordChanges() {
		return passwordChanges;
	}

	public void setPasswordChanges(ArrayList passwordChanges) {
		this.passwordChanges = passwordChanges;
	}

	public ArrayList getRegistrationDetails() {
		return registrationDetials;
	}

	public void setRegistrationDetails(ArrayList registrationDetials) {
		this.registrationDetials = registrationDetials;
	}

	public ArrayList getAddTransactions() {
		return addTransactions;
	}

	public void setAddTransactions(ArrayList addTransactions) {
		this.addTransactions = addTransactions;
	}

	public ArrayList getCancelTransactions() {
		return cancelTransactions;
	}

	public void setCancelTransactions(ArrayList cancelTransactions) {
		this.cancelTransactions = cancelTransactions;
	}

	public ArrayList getAcademicRecord() {
		return academicRecord;
	}

	public void setAcademicRecord(ArrayList academicRecord) {
		this.academicRecord = academicRecord;
	}

	public ArrayList getBioDetails() {
		return bioDetials;
	}

	public void setBioDetails(ArrayList bioDetials) {
		this.bioDetials = bioDetials;
	}

	public ArrayList getCardPayments() {
		return cardPayments;
	}

	public void setCardPayments(ArrayList cardPayments) {
		this.cardPayments = cardPayments;
	}

	public ArrayList getCentreChanges() {
		return centreChanges;
	}

	public void setCentreChanges(ArrayList centreChanges) {
		this.centreChanges = centreChanges;
	}

	public ArrayList getClassUpdates() {
		return classUpdates;
	}

	public void setClassUpdates(ArrayList classUpdates) {
		this.classUpdates = classUpdates;
	}

	public ArrayList getContactChanges() {
		return contactChanges;
	}

	public void setContactChanges(ArrayList contactChanges) {
		this.contactChanges = contactChanges;
	}

	public ArrayList getCurrentFinancial() {
		return currentFinancial;
	}

	public void setCurrentFinancial(ArrayList currentFinancial) {
		this.currentFinancial = currentFinancial;
	}

	public ArrayList getDirectAddress() {
		return directAddress;
	}

	public void setDirectAddress(ArrayList directAddress) {
		this.directAddress = directAddress;
	}

	public ArrayList getEbookShop() {
		return ebookShop;
	}

	public void setEbookShop(ArrayList ebookShop) {
		this.ebookShop = ebookShop;
	}

	public ArrayList getEbookshopAdditions() {
		return ebookshopAdditions;
	}

	public void setEbookshopAdditions(ArrayList ebookshopAdditions) {
		this.ebookshopAdditions = ebookshopAdditions;
	}

	public ArrayList getEmailsLecturer() {
		return emailsLecturer;
	}

	public void setEmailsLecturer(ArrayList emailsLecturer) {
		this.emailsLecturer = emailsLecturer;
	}

	public ArrayList getFeeQuotation() {
		return feeQuotation;
	}

	public void setFeeQuotation(ArrayList feeQuotation) {
		this.feeQuotation = feeQuotation;
	}

	public ArrayList getHistoricFinancial() {
		return historicFinancial;
	}

	public void setHistoricFinancial(ArrayList historicFinancial) {
		this.historicFinancial = historicFinancial;
	}

	public ArrayList getIndirectAddress() {
		return indirectAddress;
	}

	public void setIndirectAddress(ArrayList indirectAddress) {
		this.indirectAddress = indirectAddress;
	}

	public ArrayList getOptionChanges() {
		return optionChanges;
	}

	public void setOptionChanges(ArrayList optionChanges) {
		this.optionChanges = optionChanges;
	}


	public ArrayList getSemesterChanges() {
		return semesterChanges;
	}

	public void setSemesterChanges(ArrayList semesterChanges) {
		this.semesterChanges = semesterChanges;
	}

	public ArrayList getShopDeleted() {
		return shopDeleted;
	}

	public void setShopDeleted(ArrayList shopDeleted) {
		this.shopDeleted = shopDeleted;
	}

	public ArrayList getShopEdits() {
		return shopEdits;
	}

	public void setShopEdits(ArrayList shopEdits) {
		this.shopEdits = shopEdits;
	}

	public ArrayList getAnnouceAdded() {
		return annouceAdded;
	}

	public void setAnnouceAdded(ArrayList annouceAdded) {
		this.annouceAdded = annouceAdded;
	}

	public ArrayList getCalendarItems() {
		return calendarItems;
	}

	public void setCalendarItems(ArrayList calendarItems) {
		this.calendarItems = calendarItems;
	}

	public ArrayList getEmailsSent() {
		return emailsSent;
	}

	public void setEmailsSent(ArrayList emailsSent) {
		this.emailsSent = emailsSent;
	}

	public ArrayList getFileAdded() {
		return fileAdded;
	}

	public void setFileAdded(ArrayList fileAdded) {
		this.fileAdded = fileAdded;
	}

	public ArrayList getItemsAdded() {
		return itemsAdded;
	}

	public void setItemsAdded(ArrayList itemsAdded) {
		this.itemsAdded = itemsAdded;
	}

	public ArrayList getListCompiled() {
		return listCompiled;
	}

	public void setListCompiled(ArrayList listCompiled) {
		this.listCompiled = listCompiled;
	}

	public ArrayList getListsDownloaded() {
		return listsDownloaded;
	}

	public void setListsDownloaded(ArrayList listsDownloaded) {
		this.listsDownloaded = listsDownloaded;
	}

	public ArrayList getMessagePosted() {
		return messagePosted;
	}

	public void setMessagePosted(ArrayList messagePosted) {
		this.messagePosted = messagePosted;
	}

	public ArrayList getMessageUpdated() {
		return messageUpdated;
	}

	public void setMessageUpdated(ArrayList messageUpdated) {
		this.messageUpdated = messageUpdated;
	}

	public ArrayList getRegistrationDetials() {
		return registrationDetials;
	}

	public void setRegistrationDetials(ArrayList registrationDetials) {
		this.registrationDetials = registrationDetials;
	}

	public ArrayList getResourceRead() {
		return resourceRead;
	}

	public void setResourceRead(ArrayList resourceRead) {
		this.resourceRead = resourceRead;
	}

	public ArrayList getWebsitesAdded() {
		return websitesAdded;
	}

	public void setWebsitesAdded(ArrayList websitesAdded) {
		this.websitesAdded = websitesAdded;
	}

	public ArrayList getBioDetials() {
		return bioDetials;
	}

	public void setBioDetials(ArrayList bioDetials) {
		this.bioDetials = bioDetials;
	}

	public ArrayList getParcelInformation() {
		return parcelInformation;
	}

	public void setParcelInformation(ArrayList parcelInformation) {
		this.parcelInformation = parcelInformation;
	}

	public String getUnisaDate() {
		return unisaDate;
	}

	public void setUnisaDate(String unisaDate) {
		this.unisaDate = unisaDate;
	}


}
