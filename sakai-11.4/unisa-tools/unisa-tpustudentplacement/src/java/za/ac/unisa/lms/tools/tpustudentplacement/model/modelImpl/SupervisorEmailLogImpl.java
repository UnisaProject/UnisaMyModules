package za.ac.unisa.lms.tools.tpustudentplacement.model.modelImpl;

import za.ac.unisa.lms.tools.tpustudentplacement.forms.EmailLogRecord;

public class SupervisorEmailLogImpl {
	
	
		public  EmailLogRecord createLogObject(int supervisorCode,String toEmail,String body,String subject)throws Exception{
			            EmailLogRecord seml=new EmailLogRecord();
                        seml.setEmailAddress(toEmail);
                        seml.setProgram("TEACHINGPRACTICEUNIT");
                        seml.setRecipientType("SUPERVISOR");
                        seml.setRecipient(""+supervisorCode);
                        seml.setEmailType("STUALLOC");
                        seml.setSubject(subject);
                        seml.setBody(body);
                        return seml;
         }

}
