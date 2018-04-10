package za.ac.unisa.lms.tools.smsquery.SmsQueryDAO;

public class PersonnelClass {
	           private String initial,title,surname;
	           private String email;
	           private String telNumber;
	           private String personnelNumber;
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
	           public String  getInitial(){
	        	     return initial;
	           }
	           public void setInitial(String initial){
	        	         this.initial=initial;   
	           }
	           public String getTitle(){
	        	        return title;
	           }
               public void setTitle(String title){
            	       this.title=title;
               }
               public String getSurname(){
            	       return surname;
               }
               public void setSurname(String surname){
            	        this.surname=surname;
               }
               public String getEmail(){
            	     return email;
               }
               public void setEmail(String email){
            	    this.email=email;
               }
               public String getTelNumber(){
            	     return telNumber;
               }
               public void setTelNumber(String telNumber){
            	     this.telNumber=telNumber;
               }
               public String getPersonnelNumber(){
          	     return personnelNumber;
             }
             public void setPersonnelNumber(String personnelNumber){
          	     this.personnelNumber=personnelNumber;
             }
}
