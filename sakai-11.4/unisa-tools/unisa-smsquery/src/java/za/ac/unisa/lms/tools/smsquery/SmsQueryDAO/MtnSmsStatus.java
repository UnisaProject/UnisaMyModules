package za.ac.unisa.lms.tools.smsquery.SmsQueryDAO;

public class MtnSmsStatus{

	
	    private String messageStatus;
	     private String description;
	   	public void setMessageStatus(String messageStatus) {
		     this.messageStatus = messageStatus;
	    }
	    public String getMessageStatus() {
		      return messageStatus;
	    }
	    public String getDescription() {
		        return description;
	    }
	    public void setDescription(String description) {
		      this.description = description;
	    }

}
