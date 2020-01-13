//Created by MyEclipse Struts
// XSL source (default): platform:/plugin/com.genuitec.eclipse.cross.easystruts.eclipse_3.9.210/xslt/JavaClass.xsl

package za.ac.unisa.lms.tools.mdactivity.forms;

import java.util.Calendar;


/**
 * MyEclipse Struts
 * Creation date: 08-18-2005
 *
 * XDoclet definition:
 */
public class ActivityRecord {

	private int userCode;
	private String userName;
	private String activityCode;
	private String activityDescr;
	private String activityDate;
	private Calendar entryTimestamp;
	private String feedbackDate;
	private String comments;
	// This field is to be used in edit process where old comments may never be deleted
	// This field will hold the new comments
	private String extraComments;

	public String getActivityCode() {
		return activityCode;
	}
	public void setActivityCode(String activityCode) {
		this.activityCode = activityCode;
	}
	public String getActivityDate() {
		return activityDate;
	}
	public void setActivityDate(String activityDate) {
		this.activityDate = activityDate;
	}
	public String getActivityDescr() {
		return activityDescr;
	}
	public void setActivityDescr(String activityDescr) {
		this.activityDescr = activityDescr;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public String getFeedbackDate() {
		return feedbackDate;
	}
	public void setFeedbackDate(String feedbackDate) {
		this.feedbackDate = feedbackDate;
	}
	public int getUserCode() {
		return userCode;
	}
	public void setUserCode(int userCode) {
		this.userCode = userCode;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getExtraComments() {
		return extraComments;
	}
	public void setExtraComments(String extraComments) {
		this.extraComments = extraComments;
	}
	public Calendar getEntryTimestamp() {
		return entryTimestamp;
	}
	public void setEntryTimestamp(Calendar entryTimestamp) {
		this.entryTimestamp = entryTimestamp;
	}


}