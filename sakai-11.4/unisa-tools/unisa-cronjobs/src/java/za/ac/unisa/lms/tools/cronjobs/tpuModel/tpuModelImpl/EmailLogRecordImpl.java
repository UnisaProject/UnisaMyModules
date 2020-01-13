package za.ac.unisa.lms.tools.cronjobs.tpuModel.tpuModelImpl;
import java.util.Iterator;
import java.util.List;
import za.ac.unisa.lms.tools.cronjobs.forms.model.EmailLogRecord;
import za.ac.unisa.lms.tools.cronjobs.dao.tpuDAO.EmailLogDAO;
public class EmailLogRecordImpl{ 
				
			public void setEmailLog(EmailLogRecord log) throws Exception{
                          EmailLogDAO emailLogDAO =new EmailLogDAO(); 
                          emailLogDAO.insertEmailLog(log);	
                        
            }
			public List getEmailLogs(String todayDate) throws Exception {
				           EmailLogDAO emailLogDAO =new EmailLogDAO();
                        return emailLogDAO.getEmailLogs(todayDate);
            }
			
}
