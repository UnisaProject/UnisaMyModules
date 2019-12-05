package za.ac.unisa.utils;

public class CoursePeriod {
	private int year;
	private short semesterPeriod;
	private String courseCode;
	private String semesterType;
	private String group;
	
	/**
	 * @return Returns the semesterType.
	 */
	public String getGroup() {
		return group;
	}
	/**
	 * @param semeterType The semesterType to set.
	 */
	public void setGroup(String group) {
		this.group = group;
	}
	/**
	 * @return Returns the semesterType.
	 */
	public String getSemesterType() {
		return semesterType;
	}
	/**
	 * @param semeterType The semesterType to set.
	 */
	public void setSemesterType(String semesterType) {
		this.semesterType = semesterType;
	}
	/**
	 * @return Returns the courseCode.
	 */
	public String getCourseCode() {
		return courseCode;
	}
	/**
	 * @param courseCode The courseCode to set.
	 */
	public void setCourseCode(String courseCode) {
		this.courseCode = courseCode;
	}
	/**
	 * @return Returns the semester_period.
	 */
	public short getSemesterPeriod() {
		return semesterPeriod;
	}
	/**
	 * @param semeter_period The semester_period to set.
	 */
	public void setSemesterPeriod(short semesterPeriod) {
		this.semesterPeriod = semesterPeriod;
	}
	/**
	 * @return Returns the year.
	 */
	public int getYear() {
		return year;
	}
	/**
	 * @param year The year to set.
	 */
	public void setYear(int year) {
		this.year = year;
	}
	

}
