package za.ac.unisa.lms.tools.tpustudentplacement.model.modelImpl;

import za.ac.unisa.lms.tools.tpustudentplacement.forms.EmailLogRecord;
import za.ac.unisa.lms.tools.tpustudentplacement.utils.DateUtil;

public class PlacementEmailLogImpl {
	
	           private String getCurrTimeForLog(){
                      DateUtil  dateutil=new DateUtil();
                      return dateutil.getTimeStampOfCurrTime();
               }
               private void  setRecipient(EmailLogRecord log,String recipientType,String schoolCode,String stuName){
                                if(recipientType.equalsIgnoreCase("School")){
                                        log.setRecipient(schoolCode);
                                }else{
                                        log.setRecipient(stuName);
                              }
               }  
               public EmailLogRecord  createLogObject(String toEmail,String recipientType,String schoolCode,String stuName){             
            	                EmailLogRecord log=new EmailLogRecord();
            	                log.setEmailAddress(toEmail);
                                log.setProgram("TEACHINGPRACTICEUNIT");
                                log.setEmailType("PLACEMENT");
                                log.setSubject("Unisa Teaching Practice Placement Information");
                                log.setDateSent(getCurrTimeForLog());
                                String logEmailBody = "Placement Information";
                                log.setBody(logEmailBody);
                                setRecipient(log,recipientType,schoolCode,stuName);
                                return log;
               }

}
