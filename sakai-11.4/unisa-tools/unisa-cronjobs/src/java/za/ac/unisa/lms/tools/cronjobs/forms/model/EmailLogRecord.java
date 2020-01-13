package za.ac.unisa.lms.tools.cronjobs.forms.model;

import za.ac.unisa.lms.tools.cronjobs.tpuModel.tpuModelImpl.EmailLogRecordImpl;

import java.util.Iterator;
import java.util.List;

public class EmailLogRecord {
	private String emailAddress;
	private String dateSent;
	private String recipient;
	private String recipientType;
	private String program;
	private String emailType;
	private String subject;
	private String body;
	public String getEmailAddress() {
		return emailAddress;
	}
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	public String getDateSent() {
		return dateSent;
	}
	public void setDateSent(String dateSent) {
		this.dateSent = dateSent;
	}
	public String getRecipient() {
		return recipient;
	}
	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}
	public String getRecipientType() {
		return recipientType;
	}
	public void setRecipientType(String recipientType) {
		this.recipientType = recipientType;
	}
	public String getProgram() {
		return program;
	}
	public void setProgram(String program) {
		this.program = program;
	}
	public String getEmailType() {
		return emailType;
	}
	public void setEmailType(String emailType) {
		this.emailType = emailType;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	
    public void setEmailLog() throws Exception{
    	           EmailLogRecordImpl  emlgimp=new EmailLogRecordImpl();
                   emlgimp.setEmailLog(this);
    }
    public List getEmailLogs(String date) throws Exception{
                  EmailLogRecordImpl  emlgimp=new EmailLogRecordImpl();
                 return emlgimp.getEmailLogs(date);
    } 
 }
