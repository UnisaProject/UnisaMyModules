package za.ac.unisa.lms.tools.tpustudentplacement.model.Email;

public class EmailModul{
	
	protected String toEmail="",fromEmail="";
	protected String subject="",body="";
	
	public String getToEmail(){
		return toEmail;
	}
	public void setToEmail(String toEmail){
		      this.toEmail=toEmail;
	}
	public void setFromEmail(String fromEmail){
		      this.fromEmail=fromEmail;
	}
	public String getFromEmail(){
		return fromEmail;
	}
	public void setBody(String body){
	      this.body=body;
    }
    public String getBody(){
	   return body;
    }
    public String getSubject(){
       return subject;
    }
    public void setSubject(String subject){
    	  this.subject=subject;
    }
	
		
}
