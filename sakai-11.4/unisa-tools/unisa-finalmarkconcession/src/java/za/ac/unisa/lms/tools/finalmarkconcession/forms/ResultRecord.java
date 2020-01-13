package za.ac.unisa.lms.tools.finalmarkconcession.forms;

public class ResultRecord {
	
	private Short academicYear;
	private Short semester;
	private Short finalMark;
	private double yearMark;
	private short resultCode;	
	
	public double getYearMark() {
		return yearMark;
	}
	public void setYearMark(double yearMark) {
		this.yearMark = yearMark;
	}
	public Short getAcademicYear() {
		return academicYear;
	}
	public void setAcademicYear(Short academicYear) {
		this.academicYear = academicYear;
	}
	public Short getSemester() {
		return semester;
	}
	public void setSemester(Short semester) {
		this.semester = semester;
	}
	public Short getFinalMark() {
		return finalMark;
	}
	public void setFinalMark(Short finalMark) {
		this.finalMark = finalMark;
	}
	public short getResultCode() {
		return resultCode;
	}
	public void setResultCode(short resultCode) {
		this.resultCode = resultCode;
	}
	
	

}
