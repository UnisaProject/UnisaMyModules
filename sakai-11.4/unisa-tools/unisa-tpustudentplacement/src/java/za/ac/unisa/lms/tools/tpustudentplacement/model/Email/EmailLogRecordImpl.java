package za.ac.unisa.lms.tools.tpustudentplacement.model.Email;
import java.util.List;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.EmailLogRecord;
import za.ac.unisa.lms.tools.tpustudentplacement.model.modelImpl.PlacementEmailLogImpl;
import za.ac.unisa.lms.tools.tpustudentplacement.model.modelImpl.SupervisorEmailLogImpl;
import za.ac.unisa.lms.tools.tpustudentplacement.dao.EmailLogDAO;
public class EmailLogRecordImpl{ 
	
	
	        private void setEmailLog(EmailLogRecord log) throws Exception{
                          EmailLogDAO emailLogDAO =new EmailLogDAO(); 
                          emailLogDAO.insertEmailLog(log);	
                        
            }
	  	    public List getEmailLog(int supervisorCode,int year) throws Exception {
				             EmailLogDAO emailLogDAO =new EmailLogDAO(); 
			                return emailLogDAO.getEmailLog(supervisorCode,year);   
			 }
			 public void writeEmailLog(int supervisorCode,String toEmail,String body,String subject)throws Exception{
	                           SupervisorEmailLogImpl supervisorEmailLogImpl=new SupervisorEmailLogImpl();
	                           EmailLogRecord  log=supervisorEmailLogImpl.createLogObject(supervisorCode, toEmail, body, subject);
	                           setEmailLog(log);
			 }
			 public void writeEmailLog(String toEmail,String recipientType,String schoolCode,String stuName)throws Exception{
				               PlacementEmailLogImpl placementEmailLogImpl=new PlacementEmailLogImpl();
                               EmailLogRecord  log=placementEmailLogImpl.createLogObject(toEmail, recipientType, schoolCode, stuName);
                               setEmailLog(log);
             }
			 
}
