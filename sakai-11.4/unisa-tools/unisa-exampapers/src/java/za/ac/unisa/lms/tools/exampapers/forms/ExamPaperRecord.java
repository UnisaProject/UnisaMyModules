package za.ac.unisa.lms.tools.exampapers.forms;

import java.util.List;

public class ExamPaperRecord {
	private String studyUnit;
	private String studyUnitDesc;
	private String examYear;
	private Short examPeriod;
	private String examPeriodDesc;
	private String paperNo;
	private String uniqueNo;
	private List combinedPapers;
	private List firstExaminers;
	private List secondExaminers;
	private List externalExaminers;
	private String durationHours;
	private String durationMin;
	private String durationDays;
	private String markReadingCode;
	private String writtenTotal;
	private String mcqTotal;
	private String paperTotal;
	private String totalPages;
	private String rackSpacePages;
	private List specialMaterials;
	private List answerBooks;
	private List specialInstructions;
	private List calcSpecialInstructions;
	private List inUseSpecialInstructions;
	private List languages;
	private List additionalInstructions;
	private String examDate;
	private String examTime;
	private String letterheadLink;
	private String examinerPrt;
	private Short nrOfDocumentsSubmitted;
			
	public Short getNrOfDocumentsSubmitted() {
		return nrOfDocumentsSubmitted;
	}
	public void setNrOfDocumentsSubmitted(Short nrOfDocumentsSubmitted) {
		this.nrOfDocumentsSubmitted = nrOfDocumentsSubmitted;
	}
	public String getExaminerPrt() {
		return examinerPrt;
	}
	public void setExaminerPrt(String examinerPrt) {
		this.examinerPrt = examinerPrt;
	}
	public String getLetterheadLink() {
		return letterheadLink;
	}
	public void setLetterheadLink(String letterheadLink) {
		this.letterheadLink = letterheadLink;
	}
	public String getMarkReadingCode() {
		return markReadingCode;
	}
	public void setMarkReadingCode(String markReadingCode) {
		this.markReadingCode = markReadingCode;
	}
	public Short getExamPeriod() {
		return examPeriod;
	}
	public void setExamPeriod(Short examPeriod) {
		this.examPeriod = examPeriod;
	}
	public String getExamYear() {
		return examYear;
	}
	public void setExamYear(String examYear) {
		this.examYear = examYear;
	}
	public String getPaperNo() {
		return paperNo;
	}
	public void setPaperNo(String paperNo) {
		this.paperNo = paperNo;
	}
	public String getStudyUnit() {
		return studyUnit;
	}
	public void setStudyUnit(String studyUnit) {
		this.studyUnit = studyUnit;
	}
	public List getCombinedPapers() {
		return combinedPapers;
	}
	public void setCombinedPapers(List combinedPapers) {
		this.combinedPapers = combinedPapers;
	}
	public String getDurationHours() {
		return durationHours;
	}
	public void setDurationHours(String durationHours) {
		this.durationHours = durationHours;
	}
	public String getDurationMin() {
		return durationMin;
	}
	public void setDurationMin(String durationMin) {
		this.durationMin = durationMin;
	}
	public List getExternalExaminers() {
		return externalExaminers;
	}
	public void setExternalExaminers(List externalExaminers) {
		this.externalExaminers = externalExaminers;
	}
	public List getFirstExaminers() {
		return firstExaminers;
	}
	public void setFirstExaminers(List firstExaminers) {
		this.firstExaminers = firstExaminers;
	}
	public List getSecondExaminers() {
		return secondExaminers;
	}
	public void setSecondExaminers(List secondExaminers) {
		this.secondExaminers = secondExaminers;
	}
	public String getStudyUnitDesc() {
		return studyUnitDesc;
	}
	public void setStudyUnitDesc(String studyUnitDesc) {
		this.studyUnitDesc = studyUnitDesc;
	}
	public String getMcqTotal() {
		return mcqTotal;
	}
	public void setMcqTotal(String mcqTotal) {
		this.mcqTotal = mcqTotal;
	}
	public String getPaperTotal() {
		return paperTotal;
	}
	public void setPaperTotal(String paperTotal) {
		this.paperTotal = paperTotal;
	}
	public String getWrittenTotal() {
		return writtenTotal;
	}
	public void setWrittenTotal(String writtenTotal) {
		this.writtenTotal = writtenTotal;
	}
	public List getSpecialMaterials() {
		return specialMaterials;
	}
	public void setSpecialMaterials(List specialMaterials) {
		this.specialMaterials = specialMaterials;
	}
	public List getAnswerBooks() {
		return answerBooks;
	}
	public void setAnswerBooks(List answerBooks) {
		this.answerBooks = answerBooks;
	}
	public List getSpecialInstructions() {
		return specialInstructions;
	}
	public void setSpecialInstructions(List specialInstructions) {
		this.specialInstructions = specialInstructions;
	}
	public List getLanguages() {
		return languages;
	}
	public void setLanguages(List languages) {
		this.languages = languages;
	}
	public String getTotalPages() {
		return totalPages;
	}
	public void setTotalPages(String totalPages) {
		this.totalPages = totalPages;
	}
	public String getExamDate() {
		return examDate;
	}
	public void setExamDate(String examDate) {
		this.examDate = examDate;
	}
	public String getExamTime() {
		return examTime;
	}
	public void setExamTime(String examTime) {
		this.examTime = examTime;
	}
	public List getAdditionalInstructions() {
		return additionalInstructions;
	}
	public void setAdditionalInstructions(List additionalInstructions) {
		this.additionalInstructions = additionalInstructions;
	}
	public String getExamPeriodDesc() {
		return examPeriodDesc;
	}
	public void setExamPeriodDesc(String examPeriodDesc) {
		this.examPeriodDesc = examPeriodDesc;
	}
	public String getUniqueNo() {
		return uniqueNo;
	}
	public void setUniqueNo(String uniqueNo) {
		this.uniqueNo = uniqueNo;
	}
	public List getCalcSpecialInstructions() {
		return calcSpecialInstructions;
	}
	public void setCalcSpecialInstructions(List calcSpecialInstructions) {
		this.calcSpecialInstructions = calcSpecialInstructions;
	}
	public List getInUseSpecialInstructions() {
		return inUseSpecialInstructions;
	}
	public void setInUseSpecialInstructions(List inUseSpecialInstructions) {
		this.inUseSpecialInstructions = inUseSpecialInstructions;
	}
	public String getRackSpacePages() {
		return rackSpacePages;
	}
	public void setRackSpacePages(String rackSpacePages) {
		this.rackSpacePages = rackSpacePages;
	}
	public String getDurationDays() {
		return durationDays;
	}
	public void setDurationDays(String durationDays) {
		this.durationDays = durationDays;
	}	
	
}
