package za.ac.unisa.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.sakaiproject.component.cover.ServerConfigurationService;


/* THIS CLASS WILL CHECK IF A STUDENY IS ALLOWED TO GET ACCESS TO STUDYMATERIAL */
public class NoStudyMaterialIssue {

	
	public static boolean getBlockedStatus(String acadyear, String coursePeriod){
		boolean allow = false;
		try {
			/* SET UP VALUES FOR STUDYMATERIAL CHECK */
			String nostmiss_s= ServerConfigurationService.getString("nostmiss_s");
			String nostmiss_year = ServerConfigurationService.getString("nostmiss_year");
			SimpleDateFormat f = new SimpleDateFormat("dd-MMM-yyyy");
			/* AlTHOUGH WE HAVE THE FOLLOWING LINE WE ARE NOT USING THIS AT THIS MOMENT */
			Date nostmiss_blockFrom = f.parse(ServerConfigurationService.getString("nostmiss_blockFrom"));
			Date nostmiss_blockTo = f.parse(ServerConfigurationService.getString("nostmiss_blockTo"));
			Date Sysdate = new Date();
			
/*			
			System.out.println("acayear="+acadyear+"coursePeriod"+coursePeriod);
			System.out.println("nostmiss_s="+nostmiss_s+",nostmiss_year="+nostmiss_year+"nostmiss_blockFrom"+ServerConfigurationService.getString("nostmiss_blockFrom")+"nostmiss_blockTo="+ServerConfigurationService.getString("nostmiss_blockTo"));
			
			if ((Integer.parseInt(acadyear)==Integer.parseInt(nostmiss_year))){
				System.out.println("acadyear==nostmissYear");
			}
			if (nostmiss_s.equalsIgnoreCase(coursePeriod)) {
				System.out.println("nostmiss_s=coursePeriod");
			}
			if (Sysdate.before(nostmiss_blockTo)){
				System.out.println(Sysdate.toString() + " is Before nostmiss_blockTo");
			}
			
*/	
			/* DO CHECK IF STUDENT IS ALLOWD TO HAVE ACCESS TO STUDY MATERIAL AND LMS SYSTEMS */
			if ((Integer.parseInt(acadyear)==Integer.parseInt(nostmiss_year)) && (nostmiss_s.equalsIgnoreCase(coursePeriod)) && (Sysdate.before(nostmiss_blockTo))) {
				allow=false;
			}else{		
				allow=true;
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return allow;
	}
	public static boolean getInactiveDateStatus(String date){
		Date currentDate = new Date();
		boolean allowSite = false;
	try{
		if(date != null){
		SimpleDateFormat  formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date inactiveDate = formatter.parse(date);
		if(currentDate.before(inactiveDate)){
			allowSite = true;
		}else{
			allowSite = false;
		}
	}else{
			allowSite = true;
		}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
		return allowSite;
	}

}
