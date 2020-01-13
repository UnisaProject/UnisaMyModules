package za.ac.unisa.lms.tools.cronjobs.jobs;


import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.servlet.ServletOutputStream;

import org.apache.struts.action.ActionMessages;
import org.apache.commons.logging.Log;			
import org.apache.commons.logging.LogFactory;	//Sifiso Changes:2017/03/10:SR63678
import org.quartz.InterruptableJob;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;
import org.sakaiproject.component.cover.ComponentManager;
import org.sakaiproject.component.cover.ServerConfigurationService;
import org.sakaiproject.email.api.Attachment;
import org.sakaiproject.email.api.EmailService;
import org.sakaiproject.email.api.RecipientType;

import za.ac.unisa.lms.tools.cronjobs.dao.TelecentreDAO;
import za.ac.unisa.lms.tools.cronjobs.dao.TelecentreCodes;
import za.ac.unisa.lms.tools.cronjobs.locks.SingleClusterInstanceJob;


//Sifiso Changes:2017/03/10:SR63678 (CR1583): Job to send email to all Unisa Telecentre admin using the email group 'commtelecentre@unisa.ac.za'
//Job was seperated from 'TelecentreCodeJob' due to the job sending emails before the Telecentre code database updates are completed
//resulting in wrong Telecentre codes being sent to administrators. -SR63678 and CR1583
public class TelecentreCodesEmailJob extends SingleClusterInstanceJob implements
		StatefulJob, InterruptableJob, OutputGeneratingJob {
	
	private EmailService emailService;
	/**
	 *
	 * @param path
	 * @param filename
	 */

	public void sendTelecodes()throws Exception{
		TelecentreDAO db = new TelecentreDAO();
		// String path = getServlet().getServletContext().getInitParameter("mypath");
	    // String path ="c://drd//";
	    String path ="/tmp/";
	    String filename = "Telecentre.xls";
	    String pathName= path+filename;
		File file = new File(path+filename);
		
		File fileObject = new File(path+filename);
		FileWriter fw = new FileWriter(fileObject);
		String seperatewith = "";	
	    List list = db.getTelecentreCodesList();
	    seperatewith = "\t";
	    List siteInfo = (List)list;	
		fw.write("TELE_ID"+seperatewith+" CENTRE_PROVINCE "+seperatewith+" CENTRE_NAME "+seperatewith+" EMAIL_ADRESS "+seperatewith+" MONTHLY_HOURS "+seperatewith+" ACTIVE "+seperatewith+" TELE_CODE "+seperatewith+" \n");
		
		for(int i=0; i < siteInfo.size(); i++) {
		    fw.write(((TelecentreCodes)siteInfo.get(i)).getTeleId()+seperatewith);		
			fw.write(((TelecentreCodes)siteInfo.get(i)).getCentreProvince()+seperatewith);
			fw.write(((TelecentreCodes)siteInfo.get(i)).getCentreName()+seperatewith);
			fw.write(((TelecentreCodes)siteInfo.get(i)).getEmailId()+seperatewith);
			fw.write(((TelecentreCodes)siteInfo.get(i)).getMonthlyHrs()+seperatewith);
			fw.write(((TelecentreCodes)siteInfo.get(i)).getActive()+seperatewith);
			fw.write(((TelecentreCodes)siteInfo.get(i)).getTeleCode()+seperatewith+"\n");
		}
		fw.flush();
		fw.close();
		
		DataInputStream in = null;
		try {
			in = new DataInputStream(new FileInputStream(fileObject));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
			
		List<Attachment> attachmentList = new ArrayList<Attachment>();
		Attachment A = new Attachment(fileObject, filename);
		attachmentList.add(A);
			
		String body = "<html> "+
	    "<body> "+
	    "Good day Colleagues<br><br>"+
	    "Please find attached the latest version of the Telecentre Login codes. These are applicable immediately. Please distribute to the Telecentre contractors as soon as possible.<br><br>"+
	    "Regards  <br>"+
	    "UNISA ICT"+
	    "</body>"+
	    "</html>";
		   
		String[] months = {"January", "February",
				"March", "April", "May", "June", "July",
		        "August", "September", "October", "November",
		        "December"};
	    Calendar cal = Calendar.getInstance(); 
		String month = months[cal.get(Calendar.MONTH)];
		int year = cal.get(Calendar.YEAR);
		String subject="Monthly Telecentre Login Update: ["+month+ "/" +year+"]";
		//String email = "tguthanr@unisa.ac.za";
		//String[] emailList ={"tguthanr@unisa.ac.za", "khunohb@unisa.ac.za", "Marebgt@unisa.ac.za", "mohalc@unisa.ac.za", "FMyburgh@unisa.ac.za"};
			
		//Sifiso Changes:2017/03/13:SR63678 (CR1583)-Removed "Mashiql@unisa.ac.za" and "Rachijl@unisa.ac.za" from emailList array
		String[] emailList ={"udcsweb@unisa.ac.za","commtelecentre@unisa.ac.za", "lebelsg@unisa.ac.za", "magagjs@unisa.ac.za"};
		for (int i=0; i<emailList.length; i++){
			sendEmail(subject,body,emailList[i], attachmentList);
		}
	}
	
	
	public void sendEmail(String subject, String body, String emailAddress, List<Attachment> attachmentList) throws AddressException {
		
		//Create a hash map
		HashMap hm= new HashMap();
		
		emailService = (EmailService) ComponentManager.get(EmailService.class);
		String tmpEmailFrom = ServerConfigurationService.getString("noReplyEmailFrom");
		
		InternetAddress toEmail = null;
	    try  {
	        toEmail = new InternetAddress(emailAddress);
	    } catch (AddressException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	    }

		InternetAddress iaTo[] = new InternetAddress[1];
		iaTo[0] = toEmail;
		InternetAddress iaHeaderTo[] = new InternetAddress[1];
		iaHeaderTo[0] = toEmail;
		InternetAddress iaReplyTo[] = new InternetAddress[1];
		iaReplyTo[0] = new InternetAddress(tmpEmailFrom);
		List contentList = new ArrayList();
		contentList.add("Content-Type: text/html");
		//put elements to the map
		hm.put(RecipientType.TO,toEmail);
		
		emailService.sendMail(iaReplyTo[0], iaTo, subject, body, hm, iaReplyTo, contentList, attachmentList);
		
		Log log = LogFactory.getLog(TelecentreCodesEmailJob.class);								   //Sifiso Changes:2017/03/10:SR63678 (CR1583):Log
		log.info(this+": sendEmail(): sending mail from "+iaReplyTo[0]+" to "+toEmail.toString()); //Sifiso Changes:2017/03/10:SR63678 (CR1583):Log
	}

	
	@Override
	public void executeLocked(JobExecutionContext arg0)
			throws JobExecutionException {
		// TODO Auto-generated method stub
		try {
			sendTelecodes();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}

	public String getOutput() {
		// TODO Auto-generated method stub
		return null;
	} 
	
	public boolean isInteger( String input ){
	   try{  
	      Integer.parseInt( input );  
	     return true;  
	   }  
	   catch(NumberFormatException e){  
	     return false;  
	  }  
   }  
	    
}
