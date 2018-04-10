package za.ac.unisa.lms.tools.mdrpm.forms;

import java.util.List;

public class SupervisorHistoryRecord {
	private String updatedOn;
	private String updatedBy;
	private List<Promotor> promotorList;
	private String comment;
	public String getUpdatedOn() {
		return updatedOn;
	}
	public void setUpdatedOn(String updatedOn) {
		this.updatedOn = updatedOn;
	}
	public String getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
	public List<Promotor> getPromotorList() {
		return promotorList;
	}
	public void setPromotorList(List<Promotor> promotorList) {
		this.promotorList = promotorList;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	
	
}
