package za.ac.unisa.lms.tools.tpustudentplacement.model.Email;
import za.ac.unisa.lms.tools.tpustudentplacement.utils.InfoMessagesUtil;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.StudentPlacementForm;
import za.ac.unisa.lms.tools.tpustudentplacement.model.modelImpl.studentPlacementImpl.StudentPlacementLogUI;
import org.apache.struts.action.ActionMessages;
public class TeachPracticeEmailUI {
		
	        //TeachPracticeEmail teachPracticeEmail;
	        EmailImpl emailImpl;
	        TeachPracMailBodyClass teachMailBodyClass;
	        public  TeachPracticeEmailUI(){
	    	              //teachPracticeEmail=new TeachPracticeEmail();
	        	          emailImpl=new EmailImpl();
	    	              teachMailBodyClass=new TeachPracMailBodyClass();
	    	 }
	        public boolean sendEmail(StudentPlacementForm studentPlacementForm,ActionMessages infoMessages) {
	        	                String erroMessage="Email was not sent successfully";
	        	                 try{
	        	                      setEmailData(studentPlacementForm);
	        	                      emailImpl.sendEmail();
	        	                      addEmailLog(studentPlacementForm);
	        	                   }catch(Exception ex){
	        	                	   return false;
	        	                   }
	        	                   erroMessage="Email sent successfully";
	        	                   addErrorMessages( infoMessages,erroMessage);
	        	                   return true;
	       }
	        public void setEmailData(StudentPlacementForm studentPlacementForm){
	   		                  String toEmail=studentPlacementForm.getCommunicationEmailAddress().trim();
	   		                  String body=teachMailBodyClass.getMailBody(studentPlacementForm);
	   		                  emailImpl.setBody(body);
	   		                  emailImpl.setFromEmail("teachprac@unisa.ac.za");
	   		                  emailImpl.setToEmail(toEmail);
	   		                  emailImpl.setSubject("PLACEMENT LETTER");
	   		}
		    private void addErrorMessages(ActionMessages infoMessages,String erroMessage){
		    	  	           InfoMessagesUtil infoMessagesUtil=new InfoMessagesUtil();
                               infoMessagesUtil.addMessages(infoMessages,erroMessage);
		    }
		    private void addEmailLog(StudentPlacementForm studentPlacementForm){
		    	                  	try{
		    	               	        StudentPlacementLogUI  studentPlacementLogUI=new StudentPlacementLogUI();
		    	              		    if(studentPlacementForm.getCommunicationTo().equalsIgnoreCase("School")){
		    	              		            studentPlacementLogUI.setLogOnEmailToSchool(studentPlacementForm);
		    	              		    }else{
		    	              		    	     studentPlacementLogUI.setLogOnEmailToStu(studentPlacementForm);
		    	              		    }
		    	            	}catch(Exception ex){
		    	            		
		    	            	}
		    	           
		    }
}