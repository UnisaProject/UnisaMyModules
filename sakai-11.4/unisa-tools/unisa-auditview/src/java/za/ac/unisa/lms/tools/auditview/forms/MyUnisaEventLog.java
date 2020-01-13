package za.ac.unisa.lms.tools.auditview.forms;

public class MyUnisaEventLog {
	
	private String sessionId;
	private String sessionServer;
	private String sessionUser;
	private String sessionIp;
	private String sessionUserAgent;
	private String sessionStart;
	private String sessionEnd;
	private String eventId;
	private String eventDate;
	private String event;
	private String ref;
	private String eventCode;
	
	public MyUnisaEventLog() {
		
	}
	
	public String getEvent() {
		return event;
	}
	
	public void setEvent(String event) {
		if(event == null) {
			this.event = "";
		} else {
			this.event = event;
		}
	}
	
	public String getEventCode() {
		return eventCode;
	}
	
	public void setEventCode(String eventCode) {
		if(eventCode == null) {
			
		} else {
			this.eventCode = eventCode;
		}
	}
	
	public String getEventDate() {
		return eventDate;
	}
	
	public void setEventDate(String eventDate) {
		if(eventDate == null) {
			
		} else {
			this.eventDate = eventDate;
		}
	}
	
	public String getEventId() {
		return eventId;
	}
	
	public void setEventId(String eventId) {
		if(eventId == null) {
			
		} else {
			this.eventId = eventId;
		}
	}
	
	public String getRef() {
		return ref;
	}
	
	public void setRef(String ref) {
		if(ref == null) {
			
		} else {
			this.ref = ref;
		}
	}
	
	public String getSessionEnd() {
		return sessionEnd;
	}
	
	public void setSessionEnd(String sessionEnd) {
		if(sessionEnd == null) {
			
		} else {
			this.sessionEnd = sessionEnd;
		}
	}
	
	public String getSessionId() {
		return sessionId;
	}
	
	public void setSessionId(String sessionId) {
		if(sessionId == null) {
			
		} else {
			this.sessionId = sessionId;
		}
	}
	
	public String getSessionIp() {
		return sessionIp;
	}
	
	public void setSessionIp(String sessionIp) {
		if(sessionIp == null) {
			
		} else {
			this.sessionIp = sessionIp;
		}
	}
	
	public String getSessionServer() {
		return sessionServer;
	}
	
	public void setSessionServer(String sessionServer) {
		if(sessionServer == null) {
			
		} else {
			this.sessionServer = sessionServer;
		}
	}
	
	public String getSessionStart() {
		return sessionStart;
	}
	
	public void setSessionStart(String sessionStart) {
		if(sessionStart == null) {
			
		} else {
			this.sessionStart = sessionStart;
		}
	}
	
	public String getSessionUser() {
		return sessionUser;
	}
	
	public void setSessionUser(String sessionUser) {
		if(sessionUser == null) {
			
		} else {
			this.sessionUser = sessionUser;
		}
	}
	
	public String getSessionUserAgent() {
		return sessionUserAgent;
	}
	
	public void setSessionUserAgent(String sessionUserAgent) {
		if(sessionUserAgent == null) {
			
		} else {
			this.sessionUserAgent = sessionUserAgent;
		}
	}
	
}