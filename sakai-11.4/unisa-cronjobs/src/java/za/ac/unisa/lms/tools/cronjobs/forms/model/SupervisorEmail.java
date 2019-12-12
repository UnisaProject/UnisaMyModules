package za.ac.unisa.lms.tools.cronjobs.forms.model;
import za.ac.unisa.lms.tools.cronjobs.forms.model.Email;
import  za.ac.unisa.lms.tools.cronjobs.forms.model.Supervisor;
import  za.ac.unisa.lms.tools.cronjobs.forms.model.StudentPlacement;
import  za.ac.unisa.lms.tools.cronjobs.forms.model.EmailLogRecord;
import za.ac.unisa.lms.tools.cronjobs.forms.model.SupervisorMailBody;
import java.util.Iterator;
import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
public class SupervisorEmail extends Email{

	 
	       AdminEmail adminEmail;
	       CoordinatorEmail coordinatorEmail;
	       int  totEmailsSentToSup=0;
	       private List supervCodeList;
	       private Set supervListEmailSend,supervErrorList;
	       public  SupervisorEmail(){
		               adminEmail=new AdminEmail();
		               coordinatorEmail=new CoordinatorEmail();
		               supervListEmailSend=new HashSet();
	  	               supervErrorList =new HashSet();
	      }
	      private void getPlacedSupervisorsCodeList(){
	    	              try{
	                            StudentPlacement  sp=new StudentPlacement();
	                            supervCodeList=sp.getSupervCodeListEmailNotSend();
	    	              }catch(Exception ex){
	    	            	        supervCodeList=new ArrayList();
	    	              }
	      }
	      public void sendEmailToSupervisors() throws Exception{
		               try{
		        	           getPlacedSupervisorsCodeList();
    	                       adminEmail.setSummaryDataBeforeRun(supervCodeList.size());
    	                       sendEmailToSupsInList(supervCodeList);
    	                       adminEmail.setSupervErrorList(supervErrorList);
    	                       coordinatorEmail.sendEmailToCoordiators(supervListEmailSend);
    	                       sendSuccessfulRunEmailToAdmin(adminEmail, coordinatorEmail.getTotEmailSentToCoord());
    	                }catch(Exception ex){
    	            	         sendErrorReportEmailToAdmin(adminEmail,ex.getMessage());
			                   
			          }
	    }
	    private void sendEmailToSupsInList(List supervCodeList){
	    	             try{
		                        Iterator i=supervCodeList.iterator();
		                        int supervisorCode;
		                        while(i.hasNext()){
    	                             try{
	                                        String supervCodeStr=i.next().toString().trim();
	                                        supervisorCode=Integer.parseInt(supervCodeStr);
	                                        sendEmailToSupervisor(supervisorCode,supervListEmailSend,supervErrorList);
       	                             }catch(Exception  ex){
    		                                 continue;
    	                             }
	                           }
	    	             }catch(Exception ex){}
	   }
	 private void sendSuccessfulRunEmailToAdmin(AdminEmail adminEmail,
			          int totEmailsSentToCoord)throws Exception{
		                        adminEmail.setSuccessfulRun(true);
		                        getPlacedSupervisorsCodeList();
		                        adminEmail.setSummaryDataAfterRun(supervCodeList.size(), totEmailsSentToCoord, totEmailsSentToSup);
                                adminEmail.sendEmailForSuccessfulRun();
	 }
	 private void sendErrorReportEmailToAdmin(AdminEmail adminEmail,String errorMsg){
		                try{
		                        if(!adminEmail.isSuccessfulRun()){
                                       adminEmail.sendEmailForFailedRun(errorMsg); 
                                }else{
        	                           adminEmail.sendEmailForSuccessfulRun();	
                                }
	                     }catch(Exception ex2){}
	 }
     public void sendEmailToSupervisor(int supervisorCode,Set supervListEmailSend,Set supervErrorList) {
    	             try{
                            SupervisorMailBody  smb=new SupervisorMailBody();
                            Supervisor supervisor=new Supervisor();
                            supervisor=supervisor.getSupervisor(supervisorCode);
                            String emailAddress=supervisor.getEmailAddress();
                            subject="Unisa Teaching Practice Placement Information";
                            if((emailAddress!=null)&&(!emailAddress.trim().equals(""))){
                    	           body=smb.createEmailBody(supervisorCode);
                     	           prepAdressing(emailAddress);
                	               sendEmail();
                	               updateEmailSentFieldOnStuPlacement(supervisorCode);
                                   writeEmailLog(supervisorCode,emailAddress,
                                		   "Students allocated :"+supervisor.getStudentsAllocated(),subject);
                                   totEmailsSentToSup++;
                            }
                            supervListEmailSend.add(""+supervisorCode);
                      }catch(Exception ex){
                    	       supervErrorList.add(""+supervisorCode);
    	             }
     }
     private void updateEmailSentFieldOnStuPlacement(int supervisorCode){
    	                 try{
    	                            StudentPlacement  sp=new StudentPlacement();
    	                            sp.updateEmailToSupField(supervisorCode);
                         }catch(Exception ex){
	                                 
                        }
     }
     private void writeEmailLog(int supervisorCode,String toEmail,String body,String subject){
    	                try{
                             EmailLogRecord seml=new EmailLogRecord();
                             seml.setEmailAddress(toEmail);
                             seml.setProgram("TPU_BATCH");
                             seml.setRecipientType("SUPERVISOR");
                             seml.setRecipient(""+supervisorCode);
                             seml.setEmailType("STUALLOC");
                             seml.setSubject(subject);
                             seml.setBody(body);
                             seml.setEmailLog();
                       }catch(Exception ex){}
     }
 }