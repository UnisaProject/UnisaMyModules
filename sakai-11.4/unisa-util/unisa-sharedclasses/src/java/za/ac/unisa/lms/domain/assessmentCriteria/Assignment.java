package za.ac.unisa.lms.domain.assessmentCriteria;

import java.util.List;

import za.ac.unisa.lms.dao.Gencod;

public class Assignment {
	
	private Short year;
	private Short period;
	private String number;
	private String uniqueNumber;
	private String format;
	private String dueDate;
	private String subsidyAssignment;
	private String type;
	private String normalWeight;
	private String repeatWeight;
	private String aegrotatWeight;
	private String optionality;
	private String numberQuestions;
	private String negativeMarkingFactor;
	private String prelimMarkingDate;
	private String finalMarkingDate;
	private String creditSystem;
	private String fileReleaseDate;
	private String studentSpecifyLang;
	private String onscreenMarkFlag;
	private String subType;
	private boolean captureOnStudentSystem;
	private List listFormat;
	private List listLanguage;
	private List listMarkers;
	private List listAnswer;
	private String pfOpenDate;
	private String releaseFlag;	
	private String finalSubmissionDate;
	private String maxFileSize;
	private String group;
	
	public String getGroup() {
		return group;
	}
	public void setGroup(String group) {
		this.group = group;
	}
	public String getFinalSubmissionDate() {
		return finalSubmissionDate;
	}
	public void setFinalSubmissionDate(String finalSubmissionDate) {
		this.finalSubmissionDate = finalSubmissionDate;
	}
	public String getMaxFileSize() {
		return maxFileSize;
	}
	public void setMaxFileSize(String maxFileSize) {
		this.maxFileSize = maxFileSize;
	}
	public String getReleaseFlag() {
		return releaseFlag;
	}
	public void setReleaseFlag(String releaseFlag) {
		this.releaseFlag = releaseFlag;
	}
	public String getPfOpenDate() {
		return pfOpenDate;
	}
	public void setPfOpenDate(String pfOpenDate) {
		this.pfOpenDate = pfOpenDate;
	}
	public String getSubType() {
		return subType;
	}
	public void setSubType(String subType) {
		this.subType = subType;
	}
	public String getOnscreenMarkFlag() {
		return onscreenMarkFlag;
	}
	public void setOnscreenMarkFlag(String onscreenMarkFlag) {
		this.onscreenMarkFlag = onscreenMarkFlag;
	}
	public String getFileReleaseDate() {
		return fileReleaseDate;
	}
	public void setFileReleaseDate(String fileReleaseDate) {
		this.fileReleaseDate = fileReleaseDate;
	}
	public String getStudentSpecifyLang() {
		return studentSpecifyLang;
	}
	public void setStudentSpecifyLang(String studentSpecifyLang) {
		this.studentSpecifyLang = studentSpecifyLang;
	}	
	public List getListFormat() {
		return listFormat;
	}
	public void setListFormat(List listFormat) {
		this.listFormat = listFormat;
	}
	public List getListLanguage() {
		return listLanguage;
	}
	public void setListLanguage(List listLanguage) {
		this.listLanguage = listLanguage;
	}
	public List getListAnswer() {
		return listAnswer;
	}
	public void setListAnswer(List listAnswer) {
		this.listAnswer = listAnswer;
	}
	public List getListMarkers() {
		return listMarkers;
	}
	public void setListMarkers(List listMarkers) {
		this.listMarkers = listMarkers;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getFormat() {
		return format;
	}
	public void setFormat(String format) {
		this.format = format;
	}
	public String getDueDate() {
		return dueDate;
	}
	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getNormalWeight() {
		return normalWeight;
	}
	public void setNormalWeight(String normalWeight) {
		this.normalWeight = normalWeight;
	}
	public String getRepeatWeight() {
		return repeatWeight;
	}
	public void setRepeatWeight(String repeatWeight) {
		this.repeatWeight = repeatWeight;
	}
	public String getAegrotatWeight() {
		return aegrotatWeight;
	}
	public void setAegrotatWeight(String aegrotatWeight) {
		this.aegrotatWeight = aegrotatWeight;
	}
	public String getOptionality() {
		return optionality;
	}
	public void setOptionality(String optionality) {
		this.optionality = optionality;
	}
	public String getNumberQuestions() {
		return numberQuestions;
	}
	public void setNumberQuestions(String numberQuestions) {
		this.numberQuestions = numberQuestions;
	}
	public String getNegativeMarkingFactor() {
		return negativeMarkingFactor;
	}
	public void setNegativeMarkingFactor(String negativeMarkingFactor) {
		this.negativeMarkingFactor = negativeMarkingFactor;
	}
	public String getPrelimMarkingDate() {
		return prelimMarkingDate;
	}
	public void setPrelimMarkingDate(String prelimMarkingDate) {
		this.prelimMarkingDate = prelimMarkingDate;
	}
	public String getFinalMarkingDate() {
		return finalMarkingDate;
	}
	public void setFinalMarkingDate(String finalMarkingDate) {
		this.finalMarkingDate = finalMarkingDate;
	}
	public String getUniqueNumber() {
		return uniqueNumber;
	}
	public void setUniqueNumber(String uniqueNumber) {
		this.uniqueNumber = uniqueNumber;
	}
	public String getSubsidyAssignment() {
		return subsidyAssignment;
	}
	public void setSubsidyAssignment(String subsidyAssignment) {
		this.subsidyAssignment = subsidyAssignment;
	}
	public Short getYear() {
		return year;
	}
	public void setYear(Short year) {
		this.year = year;
	}
	public Short getPeriod() {
		return period;
	}
	public void setPeriod(Short period) {
		this.period = period;
	}
	public String getCreditSystem() {
		return creditSystem;
	}
	public void setCreditSystem(String creditSystem) {
		this.creditSystem = creditSystem;
	}
	public boolean isCaptureOnStudentSystem() {
		return captureOnStudentSystem;
	}
	public void setCaptureOnStudentSystem(boolean captureOnStudentSystem) {
		this.captureOnStudentSystem = captureOnStudentSystem;
	}
}
