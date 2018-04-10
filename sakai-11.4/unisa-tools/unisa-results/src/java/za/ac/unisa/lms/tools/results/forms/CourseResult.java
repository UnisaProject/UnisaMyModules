/* $Header: /data/cvs/javaweb/src/za/ac/unisa/struts/forms/results/CourseResult.java,v 1.4 2005/02/09 07:15:34 udcsweb3 Exp $ */
package za.ac.unisa.lms.tools.results.forms;

public class CourseResult {
	private String course;
	private String finalMark;
	private String description;
	private double yearMark;
	private double examMark;
	private double portfolioMark;
	private int yearMarkWeight;
	private int examMarkWeight;
	private int portfolioWeight;
	private double paper1Mark;
	private int paper1Weight;
	private double paper2Mark;
	private int paper2Weight;
	private String message;
	private String showDetails;
	
	/**
	 * @return Returns the course.
	 */
	public String getCourse() {
		return course;
	}
	/**
	 * @param course The course to set.
	 */
	public void setCourse(String course) {
		this.course = course;
	}
	/**
	 * @return Returns the description.
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description The description to set.
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * @return Returns the finalMark.
	 */
	public String getFinalMark() {
		return finalMark;
	}
	/**
	 * @param finalMark The finalMark to set.
	 */
	public void setFinalMark(String finalMark) {
		this.finalMark = finalMark;
	}
	public double getYearMark() {
		return yearMark;
	}
	public void setYearMark(double yearMark) {
		this.yearMark = yearMark;
	}
	public double getExamMark() {
		return examMark;
	}
	public void setExamMark(double examMark) {
		this.examMark = examMark;
	}	
	public double getPortfolioMark() {
		return portfolioMark;
	}
	public void setPortfolioMark(double portfolioMark) {
		this.portfolioMark = portfolioMark;
	}
	public String getShowDetails() {
		return showDetails;
	}
	public void setShowDetails(String showDetails) {
		this.showDetails = showDetails;
	}	
	public int getYearMarkWeight() {
		return yearMarkWeight;
	}
	public void setYearMarkWeight(int yearMarkWeight) {
		this.yearMarkWeight = yearMarkWeight;
	}
	public int getExamMarkWeight() {
		return examMarkWeight;
	}
	public void setExamMarkWeight(int examMarkWeight) {
		this.examMarkWeight = examMarkWeight;
	}
	public int getPortfolioWeight() {
		return portfolioWeight;
	}
	public void setPortfolioWeight(int portfolioWeight) {
		this.portfolioWeight = portfolioWeight;
	}
	public int getPaper1Weight() {
		return paper1Weight;
	}
	public void setPaper1Weight(int paper1Weight) {
		this.paper1Weight = paper1Weight;
	}
	public int getPaper2Weight() {
		return paper2Weight;
	}
	public void setPaper2Weight(int paper2Weight) {
		this.paper2Weight = paper2Weight;
	}
	public double getPaper1Mark() {
		return paper1Mark;
	}
	public void setPaper1Mark(double paper1Mark) {
		this.paper1Mark = paper1Mark;
	}
	public double getPaper2Mark() {
		return paper2Mark;
	}
	public void setPaper2Mark(double paper2Mark) {
		this.paper2Mark = paper2Mark;
	}	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
}
