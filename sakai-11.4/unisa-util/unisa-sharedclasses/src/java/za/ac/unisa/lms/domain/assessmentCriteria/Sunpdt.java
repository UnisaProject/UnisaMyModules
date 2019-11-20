package za.ac.unisa.lms.domain.assessmentCriteria;

public class Sunpdt {
	
	private Examination firstExam;
	private String onlineMethod;
	private String formativeAssessInd;
	private String SummativeAssessInd;
	private String nonVenueBaseExam;
	
	public String getNonVenueBaseExam() {
		return nonVenueBaseExam;
	}
	public void setNonVenueBaseExam(String nonVenueBaseExam) {
		this.nonVenueBaseExam = nonVenueBaseExam;
	}
	public String getFormativeAssessInd() {
		return formativeAssessInd;
	}
	public void setFormativeAssessInd(String formativeAssessInd) {
		this.formativeAssessInd = formativeAssessInd;
	}
	public String getSummativeAssessInd() {
		return SummativeAssessInd;
	}
	public void setSummativeAssessInd(String summativeAssessInd) {
		SummativeAssessInd = summativeAssessInd;
	}
	public Examination getFirstExam() {
		return firstExam;
	}
	public void setFirstExam(Examination firstExam) {
		this.firstExam = firstExam;
	}
	public String getOnlineMethod() {
		return onlineMethod;
	}
	public void setOnlineMethod(String onlineMethod) {
		this.onlineMethod = onlineMethod;
	}
	
	

}
