package za.ac.unisa.lms.domain.assessmentCriteria;

public class AssLog {
	Short year;
	Short period;
	String studyUnit;
	String type;
	String action;
	String comments;
	String returnEmailAddr;
	String updatedBy;
	String updatedOn;
	String requestActionFrom;
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public String getReturnEmailAddr() {
		return returnEmailAddr;
	}
	public void setReturnEmailAddr(String returnEmailAddr) {
		this.returnEmailAddr = returnEmailAddr;
	}
	public String getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}	
	public String getUpdatedOn() {
		return updatedOn;
	}
	public void setUpdatedOn(String updatedOn) {
		this.updatedOn = updatedOn;
	}
	public String getRequestActionFrom() {
		return requestActionFrom;
	}
	public void setRequestActionFrom(String requestActionFrom) {
		this.requestActionFrom = requestActionFrom;
	}
	public String getStudyUnit() {
		return studyUnit;
	}
	public void setStudyUnit(String studyUnit) {
		this.studyUnit = studyUnit;
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
}

