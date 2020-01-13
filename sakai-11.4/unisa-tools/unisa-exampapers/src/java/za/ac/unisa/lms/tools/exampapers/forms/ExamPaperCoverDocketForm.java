package za.ac.unisa.lms.tools.exampapers.forms;

import java.util.List;

import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.*;
import javax.servlet.http.HttpServletRequest;
import za.ac.unisa.lms.domain.general.Person;

public class ExamPaperCoverDocketForm extends ValidatorActionForm {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List exampapers;
	private String novellUserid;	
	private ExamPaperRecord exampaper;
	private String selectedExampaper;
	private String currentPage;
	private String[] indexNrSelectedMaterial;
	private String[] indexNrSelectedBook;	
	private String[] indexNrSelectedLanguage;
	private String[] specialInstructionSelection;
	private String[] calcInstructionSelection;
	private String[] inUseInstructionSelection;
	private String contactPerson;
	private String requestToProofRead;
	private String status;
	private boolean docketExists;
	private boolean noBookRequired;
	private boolean specialFrontPage;
	private boolean attendanceRegister;
	private boolean mcqInstructionSheet;
	private String fillInPaper;
	private String openBook;
	private String declaration;
	private String annexurePages;
	private String keepPaper;
	private String calcPermit;
	private String memoIncluded;
	private List markReadingGenCodes;
	private List fullPartialNoneCodes;
	private List openBookCodes;
	private List yesNoCodes;
	private List examPeriodCodes;
	private PersonnelRecord contact;
	private String lastUpdatedBy;
	private String lastUpdatedOn;
	private boolean educationFaculty = false;
	private boolean productionUser = false;
	private boolean typistUser = false;
	private boolean lecturerUser = true;
	private boolean updateRights = false;
	private boolean updateStatusClosedRights= false;
	private boolean updateStatusOpenRights = false;
	private boolean updateNrOfPagesRights = false;
	private boolean displayLetterHeadRights = false;
	private String examPaperLetterHead;
	private String logAction;
	private String displayMedia;
	private String printFriendlyUrl;
	private String openInstructionUrl;
	private String paperColour;
	private String sblFlag;
	private Boolean xpaperLogExists;
	private String acadLevel;
	private String tuitionType;
	private String semesterType;
	//management information
	private CollegeRecord college;
	private DepartmentRecord department;
	private String year;
	private Short period;
	private Short selectedDepartment;
	private String selectedReport;
	private Person user;
	private String reportMessage;
	private String fromSystem;
	private boolean disableKeepPaper=true;
	
	
	public boolean isDisableKeepPaper() {
		return disableKeepPaper;
	}
	public void setDisableKeepPaper(boolean disableKeepPaper) {
		this.disableKeepPaper = disableKeepPaper;
	}
	public String getAcadLevel() {
		return acadLevel;
	}
	public void setAcadLevel(String acadLevel) {
		this.acadLevel = acadLevel;
	}
	public String getTuitionType() {
		return tuitionType;
	}
	public void setTuitionType(String tuitionType) {
		this.tuitionType = tuitionType;
	}
	public String getSemesterType() {
		return semesterType;
	}
	public void setSemesterType(String semesterType) {
		this.semesterType = semesterType;
	}
	public Boolean getXpaperLogExists() {
		return xpaperLogExists;
	}


	public void setXpaperLogExists(Boolean xpaperLogExists) {
		this.xpaperLogExists = xpaperLogExists;
	}


	public List getOpenBookCodes() {
		return openBookCodes;
	}


	public void setOpenBookCodes(List openBookCodes) {
		this.openBookCodes = openBookCodes;
	}


	public String getFromSystem() {
		return fromSystem;
	}


	public void setFromSystem(String fromSystem) {
		this.fromSystem = fromSystem;
	}


	public String getReportMessage() {
		return reportMessage;
	}


	public void setReportMessage(String reportMessage) {
		this.reportMessage = reportMessage;
	}


	public String getYear() {
		return year;
	}


	public void setYear(String year) {
		this.year = year;
	}

	public Short getPeriod() {
		return period;
	}


	public void setPeriod(Short period) {
		this.period = period;
	}


	public String getSelectedReport() {
		return selectedReport;
	}


	public void setSelectedReport(String selectedReport) {
		this.selectedReport = selectedReport;
	}


	public String getDisplayMedia() {
		return displayMedia;
	}


	public void setDisplayMedia(String displayMedia) {
		this.displayMedia = displayMedia;
	}


	public String getLogAction() {
		return logAction;
	}


	public void setLogAction(String logAction) {
		this.logAction = logAction;
	}


	public String getExamPaperLetterHead() {
		return examPaperLetterHead;
	}


	public void setExamPaperLetterHead(String examPaperLetterHead) {
		this.examPaperLetterHead = examPaperLetterHead;
	}


	public boolean isUpdateNrOfPagesRights() {
		return updateNrOfPagesRights;
	}


	public void setUpdateNrOfPagesRights(boolean updateNrOfPagesRights) {
		this.updateNrOfPagesRights = updateNrOfPagesRights;
	}


	public boolean isUpdateRights() {
		return updateRights;
	}


	public void setUpdateRights(boolean updateRights) {
		this.updateRights = updateRights;
	}


	public boolean isUpdateStatusClosedRights() {
		return updateStatusClosedRights;
	}


	public void setUpdateStatusClosedRights(boolean updateStatusClosedRights) {
		this.updateStatusClosedRights = updateStatusClosedRights;
	}


	public boolean isUpdateStatusOpenRights() {
		return updateStatusOpenRights;
	}


	public void setUpdateStatusOpenRights(boolean updateStatusOpenRights) {
		this.updateStatusOpenRights = updateStatusOpenRights;
	}


	public boolean isLecturerUser() {
		return lecturerUser;
	}


	public void setLecturerUser(boolean lecturerUser) {
		this.lecturerUser = lecturerUser;
	}


	public boolean isProductionUser() {
		return productionUser;
	}


	public void setProductionUser(boolean productionUser) {
		this.productionUser = productionUser;
	}


	public boolean isTypistUser() {
		return typistUser;
	}


	public void setTypistUser(boolean typistUser) {
		this.typistUser = typistUser;
	}


	public String getLastUpdatedBy() {
		return lastUpdatedBy;
	}


	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}


	public String getLastUpdatedOn() {
		return lastUpdatedOn;
	}


	public void setLastUpdatedOn(String lastUpdatedOn) {
		this.lastUpdatedOn = lastUpdatedOn;
	}


	public void reset (ActionMapping mapping, HttpServletRequest request) {
		//If you deselect all items in a multibox and submit the form
		//nothing happens: the previous selected items are still selected.
		//The Form is therefore not updated. To correct this the control arrays 
		//are set to empty arrays.
		//This must be done after display of step2 but before step2 is submitted.
		//Only do this when currentpage is equal to step1 or step2
		//The reset method gets called each time a request is made.
		//if(this.currentPage == null){
		//	setCurrentPage("input");
		//}
		if (this.currentPage != null && this.currentPage.equalsIgnoreCase("step2")){
				this.indexNrSelectedLanguage = new String[0];
				this.indexNrSelectedMaterial = new String[0];
				this.indexNrSelectedBook = new String[0];
				this.specialInstructionSelection = new String[0];	
				this.calcInstructionSelection = new String[0];
				this.inUseInstructionSelection = new String[0];				
				this.noBookRequired = false;
				this.specialFrontPage = false;
				this.attendanceRegister = false;	
				this.declaration = "";
				this.annexurePages="";
		}			
		if (this.currentPage != null && this.currentPage.equalsIgnoreCase("step3")){
			this.contact = new PersonnelRecord();
		}
	}
		
	
	public boolean getAttendanceRegister() {
		return attendanceRegister;
	}

	public void setAttendanceRegister(boolean attendanceRegister) {
		this.attendanceRegister = attendanceRegister;
	}

	public boolean getNoBookRequired() {
		return noBookRequired;
	}

	public void setNoBookRequired(boolean bookRequired) {
		this.noBookRequired = bookRequired;
	}

	public boolean getSpecialFrontPage() {
		return specialFrontPage;
	}

	public void setSpecialFrontPage(boolean specialFrontPage) {
		this.specialFrontPage = specialFrontPage;
	}

	public String[] getSpecialInstructionSelection() {
		return specialInstructionSelection;
	}

	public void setSpecialInstructionSelection(String[] specialInstructionSelection) {
		this.specialInstructionSelection = specialInstructionSelection;
	}

	public List getExampapers() {
		return exampapers;
	}

	public void setExampapers(List exampapers) {
		this.exampapers = exampapers;
	}

	public String getNovellUserid() {
		return novellUserid;
	}

	public void setNovellUserid(String novellUserid) {
		this.novellUserid = novellUserid;
	}

	public ExamPaperRecord getExampaper() {
		return exampaper;
	}

	public void setExampaper(ExamPaperRecord exampaper) {
		this.exampaper = exampaper;
	}

	public String getSelectedExampaper() {
		return selectedExampaper;
	}

	public void setSelectedExampaper(String selectedExampaper) {
		this.selectedExampaper = selectedExampaper;
	}

	public String getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(String currentPage) {
		this.currentPage = currentPage;
	}

	public String[] getIndexNrSelectedMaterial() {
		return indexNrSelectedMaterial;
	}

	public void setIndexNrSelectedMaterial(String[] indexNrSelectedMaterial) {
		this.indexNrSelectedMaterial = indexNrSelectedMaterial;
	}

	public String[] getIndexNrSelectedBook() {
		return indexNrSelectedBook;
	}

	public void setIndexNrSelectedBook(String[] indexNrSelectedBook) {
		this.indexNrSelectedBook = indexNrSelectedBook;
	}

	public String getContactPerson() {
		return contactPerson;
	}

	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}

	public String getRequestToProofRead() {
		return requestToProofRead;
	}

	public void setRequestToProofRead(String requestToProofRead) {
		this.requestToProofRead = requestToProofRead;
	}

	public String[] getIndexNrSelectedLanguage() {
		return indexNrSelectedLanguage;
	}

	public void setIndexNrSelectedLanguage(String[] indexNrSelectedLanguage) {
		this.indexNrSelectedLanguage = indexNrSelectedLanguage;
	}

	public List getMarkReadingGenCodes() {
		return markReadingGenCodes;
	}

	public void setMarkReadingGenCodes(List markReadingGenCodes) {
		this.markReadingGenCodes = markReadingGenCodes;
	}


	public PersonnelRecord getContact() {
		return contact;
	}


	public void setContact(PersonnelRecord contact) {
		this.contact = contact;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public boolean getDocketExists() {
		return docketExists;
	}


	public void setDocketExists(boolean docketExists) {
		this.docketExists = docketExists;
	}


	public boolean isDisplayLetterHeadRights() {
		return displayLetterHeadRights;
	}


	public void setDisplayLetterHeadRights(boolean displayLetterHeadRights) {
		this.displayLetterHeadRights = displayLetterHeadRights;
	}


	public String getAnnexurePages() {
		return annexurePages;
	}


	public void setAnnexurePages(String annexurePages) {
		this.annexurePages = annexurePages;
	}


	public String getCalcPermit() {
		return calcPermit;
	}


	public void setCalcPermit(String calcPermit) {
		this.calcPermit = calcPermit;
	}


	public String getDeclaration() {
		return declaration;
	}


	public void setDeclaration(String declaration) {
		this.declaration = declaration;
	}


	public String getFillInPaper() {
		return fillInPaper;
	}


	public void setFillInPaper(String fillInPaper) {
		this.fillInPaper = fillInPaper;
	}


	public String getKeepPaper() {
		return keepPaper;
	}


	public void setKeepPaper(String keepPaper) {
		this.keepPaper = keepPaper;
	}


	public String getMemoIncluded() {
		return memoIncluded;
	}


	public void setMemoIncluded(String memoIncluded) {
		this.memoIncluded = memoIncluded;
	}


	public String getOpenBook() {
		return openBook;
	}


	public void setOpenBook(String openBook) {
		this.openBook = openBook;
	}


	public List getFullPartialNoneCodes() {
		return fullPartialNoneCodes;
	}


	public void setFullPartialNoneCodes(List finalPartialNoneCodes) {
		this.fullPartialNoneCodes = finalPartialNoneCodes;
	}


	public List getYesNoCodes() {
		return yesNoCodes;
	}


	public void setYesNoCodes(List yesNoCodes) {
		this.yesNoCodes = yesNoCodes;
	}


	public String[] getCalcInstructionSelection() {
		return calcInstructionSelection;
	}


	public void setCalcInstructionSelection(String[] calcInstructionSelection) {
		this.calcInstructionSelection = calcInstructionSelection;
	}


	public String[] getInUseInstructionSelection() {
		return inUseInstructionSelection;
	}


	public void setInUseInstructionSelection(String[] inUseInstructionSelection) {
		this.inUseInstructionSelection = inUseInstructionSelection;
	}


	public boolean isMcqInstructionSheet() {
		return mcqInstructionSheet;
	}


	public void setMcqInstructionSheet(boolean mcqInstructionSheet) {
		this.mcqInstructionSheet = mcqInstructionSheet;
	}


	public String getPrintFriendlyUrl() {
		return printFriendlyUrl;
	}


	public void setPrintFriendlyUrl(String printFriendlyUrl) {
		this.printFriendlyUrl = printFriendlyUrl;
	}


	public boolean isEducationFaculty() {
		return educationFaculty;
	}


	public void setEducationFaculty(boolean educationFaculty) {
		this.educationFaculty = educationFaculty;
	}


	public List getExamPeriodCodes() {
		return examPeriodCodes;
	}


	public void setExamPeriodCodes(List examPeriodCodes) {
		this.examPeriodCodes = examPeriodCodes;
	}


	public String getPaperColour() {
		return paperColour;
	}


	public void setPaperColour(String paperColour) {
		this.paperColour = paperColour;
	}


	public CollegeRecord getCollege() {
		return college;
	}


	public void setCollege(CollegeRecord college) {
		this.college = college;
	}


	public DepartmentRecord getDepartment() {
		return department;
	}


	public void setDepartment(DepartmentRecord department) {
		this.department = department;
	}


	public Short getSelectedDepartment() {
		return selectedDepartment;
	}


	public void setSelectedDepartment(Short selectedDepartment) {
		this.selectedDepartment = selectedDepartment;
	}


	public Person getUser() {
		return user;
	}


	public void setUser(Person user) {
		this.user = user;
	}


	public String getOpenInstructionUrl() {
		return openInstructionUrl;
	}


	public void setOpenInstructionUrl(String openInstructionUrl) {
		this.openInstructionUrl = openInstructionUrl;
	}


	public String getSblFlag() {
		return sblFlag;
	}


	public void setSblFlag(String sblFlag) {
		this.sblFlag = sblFlag;
	}

}
