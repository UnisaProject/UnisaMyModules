package za.ac.unisa.lms.domain.assessmentCriteria;

public class FinalMarkComposition {
	private Short examYear;
	private Short examPeriod;
	private String studyUnit;
	private String yearMarkComponent;
	private String portfolioComponent;
	private String examComponent;
	private String yearMarkSubminimum;
	private String portfolioSubminimum;
	private String examSubminimum;
	private String examAdmissionMethod;
	private String examAdmissionNrAss;
	private String examAdmissionYearMarkSubMin;
	
	public String getExamAdmissionMethod() {
		return examAdmissionMethod;
	}
	public void setExamAdmissionMethod(String examAdmissionMethod) {
		this.examAdmissionMethod = examAdmissionMethod;
	}
	public String getExamAdmissionNrAss() {
		return examAdmissionNrAss;
	}
	public void setExamAdmissionNrAss(String examAdmissionNrAss) {
		this.examAdmissionNrAss = examAdmissionNrAss;
	}
	public String getExamAdmissionYearMarkSubMin() {
		return examAdmissionYearMarkSubMin;
	}
	public void setExamAdmissionYearMarkSubMin(String examAdmissionYearMarkSubMin) {
		this.examAdmissionYearMarkSubMin = examAdmissionYearMarkSubMin;
	}
	public String getYearMarkComponent() {
		return yearMarkComponent;
	}
	public void setYearMarkComponent(String yearMarkComponent) {
		this.yearMarkComponent = yearMarkComponent;
	}
	public String getPortfolioComponent() {
		return portfolioComponent;
	}
	public void setPortfolioComponent(String portfolioComponent) {
		this.portfolioComponent = portfolioComponent;
	}
	public String getExamComponent() {
		return examComponent;
	}
	public void setExamComponent(String examComponent) {
		this.examComponent = examComponent;
	}
	public String getYearMarkSubminimum() {
		return yearMarkSubminimum;
	}
	public void setYearMarkSubminimum(String yearMarkSubminimum) {
		this.yearMarkSubminimum = yearMarkSubminimum;
	}
	public String getPortfolioSubminimum() {
		return portfolioSubminimum;
	}
	public void setPortfolioSubminimum(String portfolioSubminimum) {
		this.portfolioSubminimum = portfolioSubminimum;
	}
	public String getExamSubminimum() {
		return examSubminimum;
	}
	public void setExamSubminimum(String examSubminimum) {
		this.examSubminimum = examSubminimum;
	}	
	public Short getExamYear() {
		return examYear;
	}
	public void setExamYear(Short examYear) {
		this.examYear = examYear;
	}
	public Short getExamPeriod() {
		return examPeriod;
	}
	public void setExamPeriod(Short examPeriod) {
		this.examPeriod = examPeriod;
	}
	public String getStudyUnit() {
		return studyUnit;
	}
	public void setStudyUnit(String studyUnit) {
		this.studyUnit = studyUnit;
	}
}
