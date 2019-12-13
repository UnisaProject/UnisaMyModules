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
import org.apache.commons.logging.Log;			//Sifiso Changes:2017/03/10:SR63678
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



public class TelecentreCodesJob extends SingleClusterInstanceJob implements
		StatefulJob, InterruptableJob, OutputGeneratingJob {
	
	private EmailService emailService;
	/**
	 *
	 * @param path
	 * @param filename
	 */
	public void teleCodesGenerator()
	      throws Exception{
		TelecentreDAO db = new TelecentreDAO();
		Log log = LogFactory.getLog(TelecentreCodesJob.class);		//Sifiso Changes:2017/03/10:SR63678
		
			
	     try{
	    	 String str1="0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	         StringBuilder sb=new StringBuilder(6);
	         Random r = new Random();

	         //int n = db.getTotalCount();	//Sifiso Changes:2017/03/10:SR63678:Does not reflect true number of TELE_ID values-Con:some records not updated
	         int n = db.getMaxTeleId();		//Sifiso Changes:2017/03/10:SR63678:Get max number of TELE_ID values and use that as record count-Ensures all records updated
	         int tele_id =db.getTeleId();
	         int updateResult = 0;			//Sifiso Changes:2017/03/10:SR63678:To check update status
	         for(int i=0;i<n;i++)
	         {
	             for(int j=0;j<6;j++)
	             {
	                 sb.append(str1.charAt(r.nextInt(str1.length())));
	             }
	             String teleCode = sb.toString();
	             //db.updateTelecode(teleCode, tele_id);					//Sifiso Changes:2017/03/10:SR63678:Removed
	             updateResult=0;											//Sifiso Changes:2017/03/10:SR63678:Reset updateResult each time the loop runs
	             if(db.isTelecentreIDExist(tele_id))						//Sifiso Changes:2017/03/10:SR63678:Update only if TELE_ID exists-reduces DB update time
	            	 updateResult = db.updateTelecode(teleCode, tele_id);	
	             
	             //Sifiso Changes:2017/03/10:SR63678:Added logic to log update results
	             if (updateResult>0)
	            	log.info(this+": TELE_CODE for TELE_ID '"+tele_id+"' updated to '"+teleCode+"'");
	             else{
	            	 if((updateResult==0) && db.isTelecentreIDExist(tele_id))	//Sifiso Changes:2017/03/10:SR63678:Prevent unnecessary logging
	            		 log.info(this+": TELE_CODE for TELE_ID '"+tele_id+"' was not updated!");
	             }
	            	 
	             
	             sb.delete(0,6);
	             tele_id++;
	         }
	    	 
	    	 
	    	 
	     }catch (Exception e){
	    	    	//Catch exception if any
	    	 		e.printStackTrace();
	    	    }
	    	    
		
		
	}

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
		String[] emailList ={"udcsweb@unisa.ac.za","commtelecentre@unisa.ac.za", "lebelsg@unisa.ac.za", "Mashiql@unisa.ac.za", "Rachijl@unisa.ac.za", "magagjs@unisa.ac.za"};	//Sifiso Changes:2016/08/19-Prod Implementation
		//String[] emailList ={"udcsweb@unisa.ac.za","magagjs@unisa.ac.za", "lebelsg@unisa.ac.za"};	//Sifiso Changes:2016/08/19 //Uncomment for QA After Prod Implementation on 8 September 2016
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

		//log.info("sending mail from "+iaReplyTo[0]+" to "+toEmail.toString());
	}

	
	@Override
	public void executeLocked(JobExecutionContext arg0)
			throws JobExecutionException {
		// TODO Auto-generated method stub
		try {
			teleCodesGenerator();
			//sendTelecodes();		//Sifiso Changes:2017/03/10:SR63678: sendTelecodes() and teleCodesGenerator() must be seperated into two cronjobs
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}

	public String getOutput() {
		// TODO Auto-generated method stub
		return null;
	}
	    public boolean isInteger( String input )  
	    {  
	       try  
	       {  
	          Integer.parseInt( input );  
	         return true;  
	       }  
	       catch(NumberFormatException e)  
	      {  
	         return false;  
	      }  
	   }  
	    
}
