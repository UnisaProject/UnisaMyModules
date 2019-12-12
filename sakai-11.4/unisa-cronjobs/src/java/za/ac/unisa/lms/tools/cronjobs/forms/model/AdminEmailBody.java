/**
 * 
 */
package za.ac.unisa.lms.tools.cronjobs.forms.model;

import java.util.Iterator;
import java.util.Set;

/**
 * @author BALOYAR
 *
 */
public class AdminEmailBody {
	
	private int totEmailsSentToCoord=0;
	 public int getTotEmailsSentToCoord(){
		          return totEmailsSentToCoord;
	 }
	 public void  setTotEmailsSentToCoord(int totEmailsSentToCoord){
                   this.totEmailsSentToCoord=totEmailsSentToCoord;
    }
	 private int totEmailsSentToSup=0;
	 public int getTotEmailsSentToSup(){
		          return totEmailsSentToSup;
	 }
	 public void  setTotEmailsSentToSup(int totEmailsSentToSup){
                   this.totEmailsSentToSup=totEmailsSentToSup;
    }
	 private int numOfSupsInPlacements=0;
	 public int getNumOfSupsInPlacements(){
		          return numOfSupsInPlacements;
	 }
	 public void setNumOfSupsInPlacements(int numOfSupsInPlacements){
              this.numOfSupsInPlacements=numOfSupsInPlacements;
    }
	 private int numOfSupMailSend=0;
	 public int getNumOfSupMailSend(){
		          return numOfSupMailSend;
	 }
	 public void setNumOfSupMailSend(int numOfSupMailSend){
              this.numOfSupMailSend=numOfSupMailSend;
    }
	 private int numOfSupMailNotSend=0;
	 public int getNumOfSupMailNotSend(){
		          return numOfSupMailNotSend;
	 }
	 public void setNumOfSupMailNotSend(int numOfSupMailNotSend){
                  this.numOfSupMailNotSend=numOfSupMailNotSend;
    } 
	 private int totPlacementsBeforeRun;
	 public int getTotPlacementsBeforeRun(){
		          return totPlacementsBeforeRun;
	 }
	 public void setTotPlacementsBeforeRun(int totPlacementsBeforeRun){
                  this.totPlacementsBeforeRun=totPlacementsBeforeRun;
    }
	 private boolean summaryDataCompiled=false;
	 public void setSummaryDataCompiled(boolean summaryDataCompiled){
		             this.summaryDataCompiled=summaryDataCompiled;
	 }
	 public boolean isSummaryDataCompiled(){
		       return summaryDataCompiled;
	 }
	 
	public String  msgForSuccessfulRun(){
                        try{
                        	 return createMsgForSuccessRun();
                        	
                        }catch(Exception ex){
     	                          return "<b>Dear Administrator</b><br><br>The batch program ran and  completed successfully"+
                                         "but there was an error compiling summary data,check the error report below<br>"+
     	        		                 "<b>Error Message</b><br><br>"+ex.getMessage();
                       }
    }
	public String msgForSuccesfulRunNoSummary(){
              return  "<b>Dear Administrator</b><br><br>The batch program ran and  completed successfully"+
                      "but there was an error compiling summary data<br>";
    }
	public String  msgForFailedRun(String exceptionMessage){
            String  message="";
            message="<b>Dear Administrator</b><br><br>The batch program did not run successfully, there was an error <br>"+
                    "<br>Error Message</b><br><br>"+exceptionMessage; 
            return message;
   }
    private String createMsgForSuccessRun() throws Exception{
    	          StudentPlacement  stuplacemnt=new StudentPlacement();
    	          String message="<b>Dear Administrator</b><br><br>"+"" +
                                  "The batch program ran and  completed successfully<br>"+
                                  "<b>Summary data<\b><br><br>"+
                                  "<table border=1><tr><td>Total Emails sent   </td><td>"+(totEmailsSentToSup+
	                              totEmailsSentToCoord)+"</td></tr>"+
                                  "<tr><td>Total Emails sent to coodinators  </td><td>"+totEmailsSentToCoord+"</td></tr>"+
                                  "<tr><td>Total  placements accessed               </td><td>"+totPlacementsBeforeRun+"</td></tr>"+
                                  "<tr><td>Total placements in emails sent          </td><td>"+
                                  stuplacemnt.getTotPlacmtEmailSend()+"</td></tr>"+
                                  "<tr><td>Total placements where emails not sent   </td><td>"+
                                  stuplacemnt.getTotPlacmtEmailNotSend()+"</td></tr>"+
                                  "<tr><td>Total Emails sent to supervisors  </td><td>"+totEmailsSentToSup+"</td></tr>"+
                                  "<tr><td>Number of supervisors whose emails were not sent </td><td>"+numOfSupMailNotSend+"</td></tr>"+
                                 "<tr><td>Number of supervisors  who should have been sent emails</td><td>" +
                                 numOfSupsInPlacements+"</td></tr></table>"+writeSupsInErrorList();
    	          summaryDataCompiled=true;
    	          return message;
                 
    }
    private Set supervErrorList;
	public void setSupervErrorList(Set supervErrorList ){
		    this.supervErrorList=supervErrorList;
	}
	private String writeSupsInErrorList(){
		               Iterator i=supervErrorList.iterator();
		               String errorSupsListStr="<br>There was an error accessing the data for the following supervisors";
		               while (i.hasNext()){
		            	   errorSupsListStr+=(i.next().toString()+"<br>");
		               }
		               return errorSupsListStr;
   }

}
