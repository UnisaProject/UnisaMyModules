package za.ac.unisa.lms.tools.telecentre.dao;
public class extendedHourDetails{
	
	private String auditUser;
	private String auditDate;
	private String allocType;
	private String month;
	private String teleId;
	private String studentNr;
	private String hours;
	
	public void setTeleId(String teleId){
	     this.teleId=teleId;	 
	}
	public String getTeleId(){
		  return teleId;
	}
	public String getStudentNr(){
		   return  studentNr;
	}
	public void setStudentNr(String studentNr){
		   this.studentNr=studentNr;
	}
	public String getAuditUser(){
		return auditUser;
	}
	public void setAuditUser(String auditUser){
		this.auditUser=auditUser;
	}
	public String getAuditDate() {
		return auditDate;
	}
	public void setAuditDate(String auditDate) {
		this.auditDate = auditDate;
	}
	public String getAllocType() {
		return allocType;
	}
	public void setAllocType(String allocType) {
		this.allocType = allocType;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public String getHours() {
		return hours;
	}
	public void setHours(String hours) {
		this.hours = hours;
	}
}
