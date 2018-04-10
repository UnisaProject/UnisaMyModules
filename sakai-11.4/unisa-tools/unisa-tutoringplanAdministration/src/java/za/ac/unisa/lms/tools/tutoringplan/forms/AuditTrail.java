package za.ac.unisa.lms.tools.tutoringplan.forms;
import za.ac.unisa.lms.domain.general.Person;

public class AuditTrail {
	private String action;
	private String comment;
	private String updatedOn;
	private Person user;
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getUpdatedOn() {
		return updatedOn;
	}
	public void setUpdatedOn(String updatedOn) {
		this.updatedOn = updatedOn;
	}
	public Person getUser() {
		return user;
	}
	public void setUser(Person user) {
		this.user = user;
	}
    
}
