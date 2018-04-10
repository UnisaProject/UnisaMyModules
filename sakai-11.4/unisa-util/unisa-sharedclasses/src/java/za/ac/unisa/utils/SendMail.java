package za.ac.unisa.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.sakaiproject.email.cover.EmailService;

public class SendMail {
	private String to;
	private String from;
	private String subject;
	private String body;

	public SendMail(String to, String from, String subject, String body) {
		this.to = to;
		this.from = from;
		this.subject = subject;
		this.body = body;
	}

	public void sendMail() {
		Log log = LogFactory.getLog(this.getClass());
		try {
			EmailService.send(from, to, subject, body, null, null, null);
		} catch (Exception e) {
			log.error("Could not send mail: "+e+": "+e.getMessage());
			e.printStackTrace();
		}
	}
}
