package za.ac.unisa.lms.tools.tpustudentplacement.model.Email;

import java.util.List;

public class EmailLogUI {
	
	            EmailLogRecordImpl  emlogImp;
	            public  EmailLogUI(){
		                    emlogImp=new EmailLogRecordImpl();
	            }
	            public List getEmailLog(int supervisorCode,int year) throws Exception {
                                  return emlogImp.getEmailLog(supervisorCode,year);
                }
                public void writeEmailLog(int supervisorCode,String toEmail,String body,String subject)throws Exception{
                	         emlogImp.writeEmailLog(supervisorCode, toEmail, body, subject);
                }
	            public void writeEmailLog(String toEmail,String recipientType,String schoolCode,String stuName)throws Exception{
	            	         emlogImp.writeEmailLog(toEmail, recipientType, schoolCode, stuName);
                }

}
