package za.ac.unisa.lms.tools.maintainstaff.DAO;

public class MaintainLogDetail {
	private int count;
	private String audDate;
	private String novellUserId;
	private String audLog;
	
	public String getAudDate() {
		return audDate;
	}
	public void setAudDate(String audDate) {
		this.audDate = audDate;
	}
	public String getNovellUserId() {
		return novellUserId;
	}
	public void setNovellUserId(String novellUserId) {
		this.novellUserId = novellUserId;
	}
	public String getAudLog() {
		return audLog;
	}
	public void setAudLog(String audLog) {
		this.audLog = audLog;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}

}
