package za.ac.unisa.lms.tools.lmsmis.dao;

public class StatsReport {
	private String studentsJoin;
	private String studentsVisits;
	private String uniqueLogins;
	private String currentYear;
	private String annualTotal;

	public String getAnnualTotal() {
		return annualTotal;
	}

	public void setAnnualTotal(String annualTotal) {
		this.annualTotal = annualTotal;
	}

	public String getCurrentYear() {
		return currentYear;
	}

	public void setCurrentYear(String currentYear) {
		this.currentYear = currentYear;
	}

	public String getStudentsJoin() {
		return studentsJoin;
	}

	public void setStudentsJoin(String studentsJoin) {
		this.studentsJoin = studentsJoin;
	}
	public String getStudentsVisits() {
		return studentsVisits;
	}
	public void setStudentsVisits(String studentsVisits) {
		this.studentsVisits = studentsVisits;
	}
	public String getUniqueLogins() {
		return uniqueLogins;
	}
	public void setUniqueLogins(String uniqueLogins) {
		this.uniqueLogins = uniqueLogins;
	}

}
