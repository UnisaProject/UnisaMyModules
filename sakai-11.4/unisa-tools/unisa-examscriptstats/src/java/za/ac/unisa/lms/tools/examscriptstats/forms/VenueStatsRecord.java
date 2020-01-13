package za.ac.unisa.lms.tools.examscriptstats.forms;

import java.util.List;

public class VenueStatsRecord {
	private String venueCode;
	private String venueDescription;
	private Integer totalScriptsExpected;
	private Integer totalScriptsReceived;
	private Integer	totalScriptsOutstanding;
	private Integer totalStudentsAbsent;
	private Integer totalMCQReceived;
	private Integer totalMCQOutstanding;
		
	public String getVenueCode() {
		return venueCode;
	}
	public void setVenueCode(String venueCode) {
		this.venueCode = venueCode;
	}
	public String getVenueDescription() {
		return venueDescription;
	}
	public void setVenueDescription(String venueDescription) {
		this.venueDescription = venueDescription;
	}
	public Integer getTotalScriptsExpected() {
		return totalScriptsExpected;
	}
	public void setTotalScriptsExpected(Integer totalScriptsExpected) {
		this.totalScriptsExpected = totalScriptsExpected;
	}
	public Integer getTotalScriptsReceived() {
		return totalScriptsReceived;
	}
	public void setTotalScriptsReceived(Integer totalScriptsReceived) {
		this.totalScriptsReceived = totalScriptsReceived;
	}
	public Integer getTotalScriptsOutstanding() {
		return totalScriptsOutstanding;
	}
	public void setTotalScriptsOutstanding(Integer totalScriptsOutstanding) {
		this.totalScriptsOutstanding = totalScriptsOutstanding;
	}
	public Integer getTotalStudentsAbsent() {
		return totalStudentsAbsent;
	}
	public void setTotalStudentsAbsent(Integer totalStudentsAbsent) {
		this.totalStudentsAbsent = totalStudentsAbsent;
	}
	public Integer getTotalMCQReceived() {
		return totalMCQReceived;
	}
	public void setTotalMCQReceived(Integer totalMCQReceived) {
		this.totalMCQReceived = totalMCQReceived;
	}
	public Integer getTotalMCQOutstanding() {
		return totalMCQOutstanding;
	}
	public void setTotalMCQOutstanding(Integer totalMCQOutstanding) {
		this.totalMCQOutstanding = totalMCQOutstanding;
	}	

}
