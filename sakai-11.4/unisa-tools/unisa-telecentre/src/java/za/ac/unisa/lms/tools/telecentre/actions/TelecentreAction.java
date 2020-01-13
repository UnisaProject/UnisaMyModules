package za.ac.unisa.lms.tools.telecentre.actions;
import java.beans.PropertyVetoException;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date; 				//Sifiso Changes:To prevent 'class Date' cannot find symbol exception 
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;		   //Sifiso Changes:Added:2016/08/01:For generating random Telecentre code

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.ServletOutputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.LookupDispatchAction;
import org.apache.struts.actions.MappingDispatchAction;
import org.apache.struts.util.LabelValueBean;
import org.sakaiproject.component.cover.ServerConfigurationService;
import org.sakaiproject.email.api.EmailService;
import org.sakaiproject.tool.api.Session;
import org.sakaiproject.tool.api.SessionManager;
import org.sakaiproject.user.api.UserDirectoryService;
import org.sakaiproject.component.cover.ComponentManager;

import Saaal40j.Abean.Saaal40jGetStudentCourses;
import Srcds01h.Abean.Srcds01sMntStudContactDetail;
import za.ac.unisa.lms.tools.telecentre.dao.*;
import za.ac.unisa.lms.tools.telecentre.forms.*;
import za.ac.unisa.lms.tools.telecentre.validate.ValidateTele;
import za.ac.unisa.utils.CodeProfiler;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.map.ListOrderedMap;
import org.springframework.jdbc.core.JdbcTemplate;
public class TelecentreAction extends LookupDispatchAction {
	private SessionManager sessionManager;
	private UserDirectoryService userDirectoryService;
	private Log log = LogFactory.getLog(this.getClass());
	public static final int stuHrs = 24;
	public static final int centreMonthlyHrs = 6;
	public static final int centreHrs = 1000;
	public static final int SECONDS_IN_HOUR = 3600;	//Sifiso Changes:Added:2016/07/01:For two way conversion between seconds and hours
	public static final int SECONDS_IN_MINUTE = 60;	//Sifiso Changes:Added:2016/07/01:For two way conversion between seconds and minutes
	public static final int MINUTES_IN_HOUR = 60;	//Sifiso Changes:Added:2016/07/01:For two way conversion between minutes and hours/seconds
	public String timeDurationUnit="Hours";			//Sifiso Changes:Added:2016/07/06:For determining the time duration in secs, mins or hours. Default to Hours
	//Sifiso Changes:Added:2016/08/24:Email addresses of super tool administrators
	//CHANGE THE BELOW OR ADD ANOTHER STATEMENT WHENEVER A NEW ADMINISTRATOR CHANGES OR IS ADDED
	//SEARCH AND FIND 'sendEmail' METHOD AND ADD CHANGES THERE TOO
	public static final String emailTelecentreGroup="commtelecentre@unisa.ac.za";	//Sifiso Changes:Added:2016/08/24
	protected Map getKeyMethodMap(){
		       Map map = new HashMap();
		       map.put("myTelecentre","myTelecentre");
		       map.put("handleExportListBoxAction","handleExportListBoxAction");
		       map.put("handleDisplayListBoxAction","handleDisplayListBoxAction");
		       map.put("audit","audit");
		       map.put("telecentre.extend.records","audit");
		       map.put("telecentre.download","download");
		       map.put("telecentre.submit.hrs","submitHours");
		       map.put("telecentre.extend.hrs","extendStudentHours");
		       map.put("performTask","performTask");
		       map.put("telecentre.extend.centre.hrs","extendTelecentreHours");
		       map.put("extendHours","extendhrs");
		       map.put("telecentre.start","performTask");
		       map.put("telecentre.end","endTask");
		       map.put("displayStartingPage","displayStartingPage");
		       map.put("exportVisit","exportVisits");
		       map.put("telecentre.cancel","performCancel");
		       map.put("telecentre.main","back");
	           map.put("telecentre.back","back");
	           map.put("telecentre.display","myTelecentre");
		       map.put("telecentre.secondback","secondBack");
		       map.put("telecentre.displayVisitDetails","displayVisitDetails");
		       map.put("display.visit.details","displayTelecenters");
		       map.put("displayvisitdetails","displayVisitDetails");
		       map.put("exportvisitdetails","exportVisitDetails");
		       map.put("displayTelecenters","displayTelecenters");
		       map.put("visitsdataexcelsheet","visitsDataExcelSheet");
		       map.put("createCentres","createCentresLinkBtn");		 	 //Sifiso Changes:Added:2016/07/26:Mapping action for 'Create Profile' toolbar link
		       map.put("profile.create.submitBtn","createSubmit");   	 //Sifiso Changes:Added:2016/07/29:Mapping action for 'Create' button
		       map.put("createConfirmation","createConfirm");		 	 //Sifiso Changes:Added:2016/06/29:Mapping action for confirm page of create profile
		       map.put("updateCentres","updateCentresLinkBtn");		 	 //Sifiso Changes:Added:2016/07/26:Mapping action for 'Update Profile' toolbar link
		       map.put("removeCentres","removeCentresLinkBtn");		 	 //Sifiso Changes:Added:2016/07/26:Mapping action for 'Remove Telecentres' toolbar link
		       map.put("activateCentres","activateCentresLinkBtn");  	 //Sifiso Changes:Added:2016/07/26:Mapping action for 'Activate/Deactivate Profile' toolbar link
		       map.put("manageAdmins","manageAdminsLinkBtn");		 	 //Sifiso Changes:Added:2016/11/28:Mapping action for 'manageAdmins' toolbar link
		       map.put("profile.manage.admin.viewBtn","viewAdminBtn"); 		   //Sifiso Changes:Added:2016/11/28:Mapping action for 'View Admins' button
		       map.put("profile.manage.admin.addBtn","addAdminBtn");  		   //Sifiso Changes:Added:2016/11/28:Mapping action for 'Add Admin' button
		       map.put("profile.manage.admin.add.submitBtn","addAdminSubmit"); //Sifiso Changes:Added:2016/11/28:Mapping action for 'Submit' button in 'Add Admin' page
		       map.put("profile.manage.admin.removeBtn","removeAdminBtn"); 	   //Sifiso Changes:Added:2016/12/14:Mapping action for 'Remove Admin' button in 'Manage Admins' page
		       map.put("handleAdminRemoveListBoxAction","handleAdminRemoveListBoxAction"); 	   //Sifiso Changes:Added:2016/08/14:Mapping action of drop-down in 'Remove Admins' jsp
		       map.put("profile.create.confirm.submitBtn","addDbProfile");		  	   //Sifiso Changes:Added:2016/08/01:Mapping action for 'Create Telecentre' button on create confirmation page
		       map.put("profile.manage.admin.remove.submitBtn","removeAdminSubmit");   //Sifiso Changes:Added:2016/12/21:Mapping action for 'Submit' button in 'Remove Admin' page
		       map.put("handleUpdateListBoxAction","handleUpdateListBoxAction");  	   //Sifiso Changes:Added:2016/08/04:Mapping action of drop-down in 'Update Profile' jsp
		       map.put("profile.update.submitBtn","updateSubmit");					   //Sifiso Changes:Added:2016/08/10:Mapping action for 'Update' button
		       map.put("createConfirmation","updateConfirm");						   //Sifiso Changes:Added:2016/08/10:Mapping action for confirm page of update profile
		       map.put("profile.update.confirm.submitBtn","updateDbProfile"); 	 	   //Sifiso Changes:Added:2016/08/12:Mapping action for 'Update Telecentre' button on update confirmation page
		       map.put("handleRemoveListBoxAction","handleRemoveListBoxAction"); 	   //Sifiso Changes:Added:2016/08/14:Mapping action of drop-down in 'Remove Telecentre' jsp
		       map.put("profile.remove.submitBtn","removeCentresBtn");			 	   //Sifiso Changes:Added:2016/08/14:Mapping action for 'Remove' button in 'Remove Telecentre' jsp
		       map.put("profile.remove.confirm.submitBtn","removeDbCentre");	 	   //Sifiso Changes:Added:2016/08/14:Mapping action for 'Remove Telecentre' button on remove confirmation page
		       map.put("handleActivateListBoxAction","handleActivateListBoxAction");   //Sifiso Changes:Added:2016/08/14:Mapping action of drop-down in 'Activate...' jsp
		       map.put("profile.activate.activateBtn","activateCentresBtn");		   //Sifiso Changes:Added:2016/08/14:Mapping action for 'Activate' button on activate page
		       map.put("profile.activate.deactivateBtn","deactivateCentresBtn");	   //Sifiso Changes:Added:2016/08/14:Mapping action for 'Deactivate' button on activate page
		       map.put("profile.activate.confirm.activateBtn","activateDbCentre");	   //Sifiso Changed:Added:2016/08/14:Mapping action for 'Activate Telecentre' button on activate confirmation page
		       map.put("profile.activate.confirm.deactivateBtn","deactivateDbCentre"); //Sifiso Changed:Added:2016/08/14:Mapping action for 'Deactivate Telecentre' button on activate confirmation page
			return map;
	}
	public ActionForward execute(HttpServletRequest request,HttpServletResponse response,
            ActionMapping mapping,ActionForm form) throws Exception {
		
		     if (request.getParameter("action") == null) {
		    	 return displayStartingPage(mapping, form, request, response);
		     }
		     
		    return super.execute(mapping, form, request, response);
    }
	//Sifiso Changes:Changed:2016/08/19:Added statements to check for regional admin users
	private String getPermissions(TelecentreForm telecentreForm,
			Session currentSession,TelecentreDAO dao) throws Exception{
	              String userID = currentSession.getUserEid();
	              telecentreForm.setUserId(userID);
	              String  username=dao.selectUserId(userID);
	              telecentreForm.setUserId(userID);
	              telecentreForm.setExtConfirmPageReached(0);
	              String userID_other = currentSession.getUserId();
	              String  permission=dao.getUserRights(username,userID_other);
	              if(permission.toUpperCase().equals("ACCESS") ){
		              //STUDENTS 
	            	  telecentreForm.setIsAdmin(0);
	              }else{
		              //Staff Admin  
	            	  telecentreForm.setIsAdmin(1);
		              //Sifiso Changes:Added:2016/12/15:Condition to check if regional admin or not
	            	  boolean adminIsRegional=false;
            		  if(dao.isRegionalAdmin(userID)){	 //userID (AD login)
            			  adminIsRegional=true;			 //IS regional staff
	            	  }
	            	  if(adminIsRegional){						 
	            		  telecentreForm.setIsRegionalStaff(1); 
	            	  }else if (!adminIsRegional){
	            		  telecentreForm.setIsRegionalStaff(0); 
	            	  }
	              }
	              return permission.toUpperCase();
	}
	public ActionForward displayStartingPage(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		    ActionMessages messages = new ActionMessages();
		    sessionManager = (SessionManager) ComponentManager.get(SessionManager.class);
			TelecentreForm telecentreForm = (TelecentreForm) form;
		    TelecentreDAO dao = new TelecentreDAO();
		    HttpSession session = request.getSession(true);
			Session currentSession = sessionManager.getCurrentSession();
			String userID = currentSession.getUserEid();
			telecentreForm.setUserId(userID);
			telecentreForm.setExtConfirmPageReached(0);
			dateUtil du=new dateUtil();
			String topagestr="";
			refreshIntervalVars(telecentreForm,du);
			TelecentreLogOutJob tlj=new TelecentreLogOutJob();
			tlj.checkVisitsStatusForPrev();
			getPermissions(telecentreForm,currentSession,dao);
			//Sifiso Changes:2016/08/16:Do not allow login between 21:h00 and 07h00
			Calendar timeLimit=Calendar.getInstance();
			timeLimit.set(Calendar.HOUR_OF_DAY,21);				//set the hour limit (24 Hour format) to 21h00
			Calendar timeStart=Calendar.getInstance();
			timeStart.set(Calendar.HOUR_OF_DAY,06);				//set the hour start (24 Hour format) to 07h00
			timeStart.set(Calendar.MINUTE,59);					//set the minute start
			timeStart.set(Calendar.SECOND,59);					//set the second start
			Calendar timeNow=Calendar.getInstance();
			String currMinAppend = ""+timeNow.get(Calendar.MINUTE);		//Sifiso Changes:2017/03/14:SR62915:To 0 append to Minute Calendar object	
			//Sifiso Changes:2017/03/14:SR62915:To append 0 to Minute Calendar object-MINUTE is absolute(e.g. 04 = 4)
			if(timeNow.get(Calendar.MINUTE)<10)
				  currMinAppend = "0"+timeNow.get(Calendar.MINUTE);
			
			//Sifiso Changes:2016/08/19:Added '|| telecentreForm.getIsRegionalStaff()==1'
			//condition makes sure that regional staff can have their view when they have maintain (admin) access
			if(telecentreForm.getIsAdmin()==1 || telecentreForm.getIsRegionalStaff()==1){
				displayVisitsScreen(session,telecentreForm,dao);
				return mapping.findForward("displayvisitdetails");
			}
			setDate(telecentreForm); 
			topagestr=checkIfStudentActive(telecentreForm,messages,request);
			
			//Sifiso Changes:2016/11/18: Do checks for student daily hours
			/*if((topagestr.equals("startpage"))){
				//Check daily hours ONLY when student is active (topagestr==startpage):used for redirect below
				topagestr=checkDailyHours(telecentreForm,messages,request);		//Sifiso Changes:2016/11/04
			}
			//Sifiso Changes:2016/11/18: Do checks for student weekly hours if daily hours are not used up
			if((topagestr.equals("startpage"))){
				//Check weekly hours ONLY if daily hours are not used up (topagestr==startpage):used for redirect below
				topagestr=(telecentreForm,messages,request);	//Sifiso Changes:2016/11/16
			}*/
			
			if(!topagestr.equals("empty")){
			       session.setAttribute("telecentres",dao.getTelecentres());
			       String latestTime = dao.getLatestTime(telecentreForm.getUserId());
		  	       telecentreForm.setLatestStartTime(latestTime);
		  	       String checkSessionStr=checkSessions(dao,messages,request,Integer.parseInt(telecentreForm.getUserId()));
		  	       telecentreForm.setCheck(false);
			       if(checkSessionStr.equals("endpage")){
		    	       DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			           Date date1 = new Date();
			           String dateTime1 = dateFormat1.format(date1);
				       telecentreForm.setToday(dateTime1);
				       telecentreForm.setCheck(true);
				       telecentreForm.setTelecentreName(dao.getcentreName(telecentreForm.getUserId()));
				       topagestr="endpage";
		          }
			}
			//Sifiso Changes:2016/08/16:Do not allow login after 21:h00
			 if(timeNow.get(Calendar.HOUR_OF_DAY)>=timeLimit.get(Calendar.HOUR_OF_DAY)){
				  messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("errors.message.notLoginTime"));
				  addErrors(request, messages); 
				  telecentreForm.setAboutTele("N");
				  log.info(this+": displayStartingPage(): Student '"+userID+"' accessed tool at "+timeNow.get(Calendar.HOUR_OF_DAY)+":"+
						  currMinAppend+", after 21:h00. ACCESS DENIED!"); 		//Sifiso Changes:2017/03/10:SR62915
				  
				  return mapping.findForward("empty");
			 }else{
				  //Sifiso Changes:2016/08/16:Do not allow login before 07h00: Below is login before 06:59:59
				  if((timeNow.get(Calendar.HOUR_OF_DAY)<=timeStart.get(Calendar.HOUR_OF_DAY)) &&
						  (timeNow.get(Calendar.MINUTE)<=timeStart.get(Calendar.MINUTE)) &&
						  (timeNow.get(Calendar.SECOND)<=timeStart.get(Calendar.SECOND))){
					  messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("errors.message.notLoginTime"));
					  addErrors(request, messages); 
					  telecentreForm.setAboutTele("N");
					  log.info(this+": displayStartingPage(): Student '"+userID+"' accessed tool at "+timeNow.get(Calendar.HOUR_OF_DAY)+":"+
							  currMinAppend+", before 07:h00. ACCESS DENIED!"); 	//Sifiso Changes:2017/03/10:SR62915
					  
					  return mapping.findForward("empty");
				  }else{
					  log.info(this+": displayStartingPage(): Student '"+userID+"' accessed tool at "+timeNow.get(Calendar.HOUR_OF_DAY)+":"+
							  currMinAppend+". ACCESS GRANTED!"); 				   //Sifiso Changes:2017/03/14:SR62915
					  
					  return mapping.findForward(topagestr);
				  }
			}
			//return mapping.findForward(topagestr);		//Sifiso Changes:2016/08/19
   	    }
	    private void setDate(TelecentreForm  telecentreForm){
	    	    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		        Date date = new Date();
		        String dateTime = dateFormat.format(date);
		        DateFormat df = new SimpleDateFormat("yyyy");
		        String currentYear = df.format(date);
		        telecentreForm.setCurrentYear(currentYear);
		        telecentreForm.setToday(dateTime);

	    }
	    //Sifiso Changes:Added: 2016/11/04:CR1176 -Determine Daily Hours used prior to student Telecentre usage
	    private String checkDailyHours(TelecentreForm telecentreForm,
	    		 ActionMessages messages,HttpServletRequest request) throws Exception{
	    	
	    	TelecentreDAO dao = new TelecentreDAO();
	    	String stuNum = telecentreForm.getUserId();
	    	DateFormat dateFormatYMD = new SimpleDateFormat("yyyy-MM-dd");
	    	Date currentDate = new Date();
	    	String todayDate = dateFormatYMD.format(currentDate);
	    	double dailyHoursUsed=dao.getStudentDailyUsedHours(stuNum,todayDate); //Sifiso Changes:2017/03/10:SR62915:Run time optimization for log
	    	
	    	
	    	if (dailyHoursUsed>=2){
	    		telecentreForm.setAboutTele("N");
	    		log.info(this+": checkDailyHours(): Student '"+stuNum+"' daily hour limit reached. Used daily hours are: "+
	    				dailyHoursUsed);	//Sifiso Changes:2017/03/10:SR62915
	    		
				return "empty";
	    	}else{
			       return "startpage";	
		    }
	    }
	    
	    //Sifiso Changes:Added: 2016/11/16:CR1176 -Determine Weekly Hours used prior to student Telecentre usage
	    private String checkWeeklyHours(TelecentreForm telecentreForm,
	    		 ActionMessages messages,HttpServletRequest request) throws Exception{
	    	
	    	TelecentreDAO dao = new TelecentreDAO();
	    	String stuNum = telecentreForm.getUserId();
	    	DateFormat dateFormatYMD = new SimpleDateFormat("yyyy-MM-dd");
	    	Date currentDate = new Date();
	    	String todayDate = dateFormatYMD.format(currentDate);
	    	double weeklyHoursUsed=dao.getStudentWeeklyUsedHours(stuNum,todayDate); //Sifiso Changes:2017/03/10:SR62915:Run time optimization for log
	    	
	    	
	    	if (weeklyHoursUsed>=6){
	    		telecentreForm.setAboutTele("N");
	    		log.info(this+": checkWeeklyHours(): Student '"+stuNum+"' weekly hour limit reached. Used weekly hours are: "+
	    				weeklyHoursUsed);	//Sifiso Changes:2017/03/10:SR62915
	    		
				return "empty";
	    	}else{
			       return "startpage";	
		    }
	    }
	    
	    private String checkIfStudentActive(TelecentreForm telecentreForm,
	    		 ActionMessages messages,HttpServletRequest request) throws Exception{
	    	   
	    	  boolean passed = true;
	    	  boolean studentActive =false;   
	         	try {
	         		
	         		String studentNo = telecentreForm.getUserId();
	         		
	         		passed = 	ValidateTele.validateInteger(studentNo);
	        	               		
	         	
	         		if(passed)
	         		{
	                    int x = Integer.parseInt(telecentreForm.getUserId());
	                    
	         		}   
	                    
	         		
	           
	            }catch(NumberFormatException nFE) {
	                    messages.add(ActionMessages.GLOBAL_MESSAGE,
	                    new ActionMessage(""));
	                    addErrors(request, messages); 
	                    telecentreForm.setAboutTele("N");
	              	    return "empty";
	             }
	         	
	         	if(passed)
	         	{
	         		
	         	studentActive = activeStudent(telecentreForm.getUserId());
	         	}
	         	
			     if (studentActive==false) {
				        messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("errors.message.not.active"));
				        addErrors(request, messages); 
				        telecentreForm.setAboutTele("N");
				        log.info(this+": checkIfStudentActive(): Student '"+telecentreForm.getUserId()+"' is not an active myUnisa Student. "+
				        		"Student not registered for Current year."); 	//Sifiso Changes:2017/03/14:SR62915
				        
				        return "empty";
				}else{
				       return "startpage";	
			    }
	    }
		private void displayVisitsScreen(HttpSession session,TelecentreForm telecentreForm,TelecentreDAO dao){
               DateFormat dateFormat4 = new SimpleDateFormat("yyyy-MM-dd ");
               Date date4 = new Date();
               String dateTime4 = dateFormat4.format(date4);
               telecentreForm.setToday(dateTime4);
               dateUtil dateutil=new dateUtil();
               dateutil.generatePeriodList();
               dateutil.generateMonthList();
               dateUtil du=new dateUtil();
               telecentreForm.setShowTelecentreListTracker(0);
               telecentreForm.setTelecenters(dao.getCentres());
               telecentreForm.setFromDay("01");
     		   telecentreForm.setEndDay("01");
     		   telecentreForm.setFromMonth(du.getMonthIntStr());
     		   telecentreForm.setToMonth(du.getMonthIntStr());
     		   telecentreForm.setFromYear(du.getYearIntStr());
     		   telecentreForm.setToYear(du.getYearIntStr());
               telecentreForm.setPeriodList(dateutil.generatePeriodList());
               session.setAttribute("telecentres", telecentreForm.getTelecenters());
               session.setAttribute("periodList",telecentreForm.getPeriodList());
               telecentreForm.setExtConfirmPageReached(0);
               telecentreForm.setCustomvisittracker(0);
               telecentreForm.setExtendPageReached(0);
               telecentreForm.setExportPageReached(0);
               telecentreForm.setDisplayPageReached(1);
      }
	public ActionForward extendStudentHours(ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception{
	    	ActionMessages messages = new ActionMessages();
            TelecentreForm telecentreForm = (TelecentreForm) form;
            String studentNum=telecentreForm.getStudentNr();
            
            boolean passed = false;
             
            passed = ValidateTele.validateInteger(studentNum);
            
            
            //November 2014
              if(passed){
            	  
		    		     boolean check = activeStudent(studentNum);
				          if(check){
				        	  if(telecentreForm.getExtensionHours()==0 || telecentreForm.getExtensionHours() < 0  ){
		        		    	    messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("telecentre.ext.zeroerror"));
		        	                addErrors(request, messages); 
		        	                return mapping.findForward("extendhours"); 
		        		       }
				        	   
				   		       if(!isStudentHrsAssignedForMonth(telecentreForm,studentNum )){
				   			           messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("error.hrsnotassigned"));
				   	                   addErrors(request, messages); 
				   	                   return mapping.findForward("extendhours"); 
				   	           }
				        	   if(processActiveStudent(request,telecentreForm)){
				        	    	     return mapping.findForward("extensionconfirmation");
				        	   }else{
				        		        messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("stunum.error"));
				        		        addErrors(request, messages); 
				        		        return mapping.findForward("extendhours"); 
				        	      }//else
				        	      
				          }else{
					            messages.add(ActionMessages.GLOBAL_MESSAGE,
								new ActionMessage("errors.message.not.active.stu"));
					            addErrors(request, messages); 
					            return mapping.findForward("extendhours");
				         }	
		    	}else{
		    		 messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("stunum.error"));
		    		 addErrors(request, messages); 
		    		 return mapping.findForward("extendhours");		
		    	}
	}
	private boolean isStudentHrsAssignedForMonth(TelecentreForm telecentreForm, String studentNum ){
		                    TelecentreDAO  dao= new TelecentreDAO();
	                        dateUtil dateutil=new dateUtil();
	                       	String monthIntStr=dateutil.monthToIntStr(telecentreForm.getMonth());
	                        int monthInt=Integer.parseInt(monthIntStr);
	                        int year=Integer.parseInt(telecentreForm.getYear());
	                        if(dao.isStudentHoursAssigned(studentNum,monthInt,year)){
	                        	return true;
	                        }else{
	                        	return false;
	                        }
	}
private boolean processActiveStudent(HttpServletRequest request,TelecentreForm telecentreForm ){
	 TelecentreDAO dao = new TelecentreDAO();
	 stuDAO  sdao= new stuDAO();
     ActionMessages messages = new ActionMessages();
	 if((telecentreForm.getStudentNr().length()==7)||(telecentreForm.getStudentNr().length()==8)){
		   String month=telecentreForm.getMonth();
		   String yearStr=telecentreForm.getYear();
		   int year=Integer.parseInt(yearStr);
		   //Sifiso Changes:Changed below line:2016/06/30-To change the type from int to double
		   //int stuAvailHours=getStudentAvailableHours(dao,telecentreForm.getStudentNr(),month,year);
		   double stuAvailHours=getStudentAvailableHours(dao,telecentreForm.getStudentNr(),month,year); //Sifiso Changes
		   telecentreForm.setExtStu(1);
		   telecentreForm.setHoursSubmitted(0);
		   //Sifiso Changes:Changed line below:2016/06/30-To Cast 'stuAvailHours' to int
           //int extendedHours=stuAvailHours+telecentreForm.getExtensionHours();
		   int extendedHours=(int)stuAvailHours+telecentreForm.getExtensionHours();	//Sifiso Changes
           telecentreForm.setStudentName(sdao.stuDetails(telecentreForm.getStudentNr()));
           telecentreForm.setNewHourLimit(extendedHours);
           //Sifiso Changes:Changed line below:2016/06/30-To Cast 'stuAvailHours' to int
           //telecentreForm.setPreviousHourLimit(stuAvailHours);
           telecentreForm.setPreviousHourLimit((int)stuAvailHours);		//Sifiso Changes
           telecentreForm.setMonthYearString(month+"/"+year);
           messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("error.areusureforstu"));
           addErrors(request, messages); 
           telecentreForm.setExtConfirmPageReached(1);
          return  true;
	}else{
		  return false;
	}
  }
  //Sifiso Changes:Changed below line:2016/06/30-To change the return type from int to double
  //private int getStudentAvailableHours(TelecentreDAO dao,String studentNr,String month,int year){
  private double getStudentAvailableHours(TelecentreDAO dao,String studentNr,String month,int year){
               dateUtil dateutil=new dateUtil();
               //Sifiso Changes:Changed below line:2016/06/30-To change the type from int to double
               //int stuAvailHours=dao.getStudentAvailableHours(studentNr,dateutil.monthToInt(month),year);
               double stuAvailHours=dao.getStudentAvailableHours(studentNr,dateutil.monthToInt(month),year);	//Sifiso Changes
               if(stuAvailHours<0){
            	     dao.assignStudentHours(studentNr,dateutil.monthToInt(month),stuHrs,year);
            	     dao.allocateStudentTelecentreHrs("System",(new Date()),"Normal","",studentNr,stuHrs);
            	     stuAvailHours=dao.getStudentAvailableHours(studentNr,dateutil.monthToInt(month),year);
	                 return stuAvailHours;
               }else{
            	   return stuAvailHours;
               }
   }
   //Sifiso Changes:Changed below line:2016/06/30-To change the type from int to double
   //private int getCentreAvailableHours(TelecentreDAO dao,String telecode,String month,int year){
   private double getCentreAvailableHours(TelecentreDAO dao,String telecode,String month,int year){  //Sifiso Changes
	                  dateUtil dateutil=new dateUtil();
	                  //Sifiso Changes:Changed below 2 lines:2016/06/30-To change the type from int to double
	                  //int cenAvailHours=dao.getTelecentreAvailableHours(telecode,
     		          //           dateutil.monthToInt(month),year);
	                  double cenAvailHours=dao.getTelecentreAvailableHours(telecode,	//Sifiso Changes
  		                     dateutil.monthToInt(month),year);							//Sifiso Changes
	                  if(cenAvailHours<0){
	                	  dao.assignCentreHours(telecode,dateutil.monthToInt(month),centreHrs,year);
	                	  dao.allocateStudentTelecentreHrs("System",(new Date()),"Normal",telecode,"",centreHrs);
	                	  cenAvailHours=dao.getTelecentreAvailableHours(telecode,
	     		                     dateutil.monthToInt(month),year);
	                	    return cenAvailHours;
			    	  }else{
			    			  return cenAvailHours;
			    	  }
	}
   public ActionForward audit(ActionMapping mapping,
           ActionForm form,
           HttpServletRequest request,
           HttpServletResponse response) throws Exception{
	       setSessionAttributes(request,form);
	       TelecentreForm telecentreForm = (TelecentreForm) form;
	       telecentreForm.setAuditPageReached(1);
           return   mapping.findForward("extendedhoursdownload");
   }
   private void setSessionAttributes(HttpServletRequest request,ActionForm form){
	              dateUtil du=new dateUtil();
	              TelecentreForm telecentreForm = (TelecentreForm) form;
                  HttpSession session = request.getSession(true);
                  telecentreForm.setYearsList(du.getYearsList());
	              telecentreForm.setVisitPeriod("Choose  Period");
	              telecentreForm.setMonthList(du.generateMonthsList());
	              telecentreForm.setPeriodList(du.generatePeriodList());
	              session.setAttribute("monthList",du.generateMonthsList());
                  session.setAttribute("daysList",du.generateDayList());
                  session.setAttribute("endDaysList",du.generateDayList());
                  session.setAttribute("toYearsList",du.getYearsList());
                  session.setAttribute("monthList",du.generateMonthsList());
                  session.setAttribute("yearsList",du.getYearsList());
                  session.setAttribute("toMonthsList",du.generateMonthsList());
                  session.setAttribute("toYearsList",du.getYearsList());
	              session.setAttribute("periodList",telecentreForm.getPeriodList());
    }
   public ActionForward extendTelecentreHours(ActionMapping mapping,
			             ActionForm form,
			             HttpServletRequest request,
			             HttpServletResponse response) throws Exception{
		                 ActionMessages messages = new ActionMessages();
		                 TelecentreForm telecentreForm = (TelecentreForm) form;
		                 TelecentreDAO dao = new TelecentreDAO();
		                 String month=telecentreForm.getMonthForTelecenter();
		      			 String yearStr=telecentreForm.getYearForTelecenter();
		      			 int year=Integer.parseInt(yearStr);
		      			 String telecode=telecentreForm.getTelecentreCode();
		       		     String centreName=dao.getCentreName(telecode);
			             boolean isTeleCodeValid = dao.isTelecentreCodeValid(centreName);
			            
			             if(isTeleCodeValid){
			            	       if(telecentreForm.getExtensionHoursForCentre()==0  || telecentreForm.getExtensionHoursForCentre() <0){
			            	    	   messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("telecentre.ext.zeroerror"));
				                        addErrors(request, messages); 
				                        return mapping.findForward("extendhours"); 
			            	       }
			            	       if(!isTelecentreHrsAssignedForMonth(telecentreForm,telecode)){
			            	    	      messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("telecentre.ext.hrsnotassigned"));
				                          addErrors(request, messages); 
				                          return mapping.findForward("extendhours"); 
			            	       }
			            	      
			            	      //Sifiso Changes:Changed below line:2016/06/30-To change the type from int to double
			                      //int centerAvailHours=getCentreAvailableHours(dao,telecode,month,year); 
			            	      double centerAvailHours=getCentreAvailableHours(dao,telecode,month,year); 	//Sifiso Changes
			            	      //Sifiso Changes:Changed below line:2016/06/30-To cast 'centerAvailHours' to int
						          //telecentreForm.setNewHourLimit(centerAvailHours+telecentreForm.getExtensionHoursForCentre());
			            	      telecentreForm.setNewHourLimit((int)centerAvailHours+telecentreForm.getExtensionHoursForCentre()); //Sifiso Changes
			            	      //Sifiso Changes:Changed below line:2016/06/30-To cast 'centerAvailHours' to int
						          //telecentreForm.setPreviousHourLimit(centerAvailHours);
			            	      telecentreForm.setPreviousHourLimit((int)centerAvailHours);  //Sifiso Changes
						          telecentreForm.setTelecentreName(centreName);
						          telecentreForm.setMonthYearString(month+"/"+year);
						          telecentreForm.setExtStu(0);
						          telecentreForm.setHoursSubmitted(0);
						          messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("error.areusurefortel"));
			                      addErrors(request, messages);
			                      telecentreForm.setExtConfirmPageReached(1);
				                  return mapping.findForward("extensionconfirmation");
				                     
			               }else{
				                        messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("telecentre.ext.error"));
				                        addErrors(request, messages); 
				                        return mapping.findForward("extendhours");
			              }//else
	}
    private boolean isTelecentreHrsAssignedForMonth(TelecentreForm telecentreForm, String telecode ){
            TelecentreDAO  dao= new TelecentreDAO();
            dateUtil dateutil=new dateUtil();
      	    String monthIntStr=dateutil.monthToIntStr(telecentreForm.getMonth());
            int monthInt=Integer.parseInt(monthIntStr);
            int year=Integer.parseInt(telecentreForm.getYearForTelecenter());
            if(dao.isTelecentreHoursAssigned(telecode, monthInt, year)){
         	     return true;
            }else{
       	         return false;
           }
    }
    private void setStartTimesInFormBean(TelecentreForm  telecentreForm){
    	      dateUtil dateutil=new dateUtil(); 
    	      DateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
              int startHour=dateutil.getCurrHourInt();
              int startMin=dateutil.getCurrMin();
              int startDay=dateutil.getDayOfMonthInt();
              int month=dateutil.getMonthInt();
		      int yearInt=dateutil.getYearInt();
		      telecentreForm.setStartMin(startMin);
              telecentreForm.setStartDay(startDay);
              telecentreForm.setStartHour(startHour);
              String dateTime2 = dateFormat2.format(new Date());
              telecentreForm.setToday(dateTime2);
              telecentreForm.setStartTimeStamp(dateTime2);
    }
    public ActionForward performTask(ActionMapping mapping,ActionForm form,
                       HttpServletRequest request,HttpServletResponse response)
                       throws Exception{
	                    TelecentreForm telecentreForm = (TelecentreForm) form;
                        ActionMessages messages = new ActionMessages();
                        TelecentreDAO dao = new TelecentreDAO();
                        dateUtil dateutil=new dateUtil(); 
                        CodeProfiler profiler = new CodeProfiler();
                        String teleCode = telecentreForm.getCode();
                        String centreName = dao.getCentreName(teleCode); //Sifiso Changes:2017/03/10:SR62915:Run time Optimization
                        telecentreForm.setTelecentreName(centreName);
                        String teleLoginCode = telecentreForm.getTelecentreCode();	 //Sifiso Changes:2017/03/10:SR62915: Login Telecentre Code for Log
                        DateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        int startHour=dateutil.getCurrHourInt();
                        int startMin=dateutil.getCurrMin();
                        int startDay=dateutil.getDayOfMonthInt();
                        int month=dateutil.getMonthInt();
		                int yearInt=dateutil.getYearInt();
		                setStartTimesInFormBean(telecentreForm);
		                String dateTime2 = dateFormat2.format(new Date());
                        int stuNum=Integer.parseInt(telecentreForm.getUserId());
                        
                        //Sifiso Changes:2016/08/16:Do not allow login between 21:h00 and 07h00
            			Calendar timeLimit=Calendar.getInstance();
            			timeLimit.set(Calendar.HOUR_OF_DAY,21);				//set the hour limit (24 Hour format) to 21h00
            			Calendar timeStart=Calendar.getInstance();
            			timeStart.set(Calendar.HOUR_OF_DAY,06);				//set the hour start (24 Hour format) to 07h00
            			timeStart.set(Calendar.MINUTE,59);					//set the minute start
            			timeStart.set(Calendar.SECOND,59);					//set the second start
            			Calendar timeNow=Calendar.getInstance();
            			String currMinAppend = ""+timeNow.get(Calendar.MINUTE);		//Sifiso Changes:2017/03/14:SR62915:To append 0 to Minute Calendar object	
            			//Sifiso Changes:2017/03/14:SR62915:To append 0 to Minute Calendar object-MINUTE is absolute(e.g. 04 = 4)
            			if(timeNow.get(Calendar.MINUTE)<10)
            				  currMinAppend = "0"+timeNow.get(Calendar.MINUTE);
            			
            			//Sifiso Changes:2016/08/16:Do not allow login after 21:h00
            			if(timeNow.get(Calendar.HOUR_OF_DAY)>=timeLimit.get(Calendar.HOUR_OF_DAY)){
            				  messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("errors.message.notLoginTime"));
            				  addErrors(request, messages); 
            				  telecentreForm.setAboutTele("N");
            				  log.info(this+": performTask(): Student '"+stuNum+"' login to Telecentre '"+centreName+"("+teleCode+")' at "+
            						  timeNow.get(Calendar.HOUR_OF_DAY)+":"+currMinAppend+", after 21:h00. LOGIN DENIED!"); 	//Sifiso Changes:2017/03/10:SR62915
            				  
            				  return mapping.findForward("empty");
            			 }else{
            				  //Sifiso Changes:2016/08/16:Do not allow login before 07h00: Below is login before 06:59:59
            				  if((timeNow.get(Calendar.HOUR_OF_DAY)<=timeStart.get(Calendar.HOUR_OF_DAY)) &&
            						  (timeNow.get(Calendar.MINUTE)<=timeStart.get(Calendar.MINUTE)) &&
            						  (timeNow.get(Calendar.SECOND)<=timeStart.get(Calendar.SECOND))){
            					  messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("errors.message.notLoginTime"));
            					  addErrors(request, messages); 
            					  telecentreForm.setAboutTele("N");
            					  log.info(this+": performTask(): Student '"+stuNum+"' login to Telecentre '"+centreName+"("+teleCode+")' at "+
                						  timeNow.get(Calendar.HOUR_OF_DAY)+":"+currMinAppend+", before 07:h00. LOGIN DENIED!"); //Sifiso Changes:2017/03/10:SR62915
            					  
            					  return mapping.findForward("empty");
            				  }else{
						            //Sifiso Changes:2016/11/18: Do checks for student daily and weekly hours and redirect accordingly
						            String toPageStrDaily=checkDailyHours(telecentreForm,messages,request); 	//Check daily hours
						            String toPageStrWeekly=checkWeeklyHours(telecentreForm,messages,request);   //Check weekly hours
			                        
			                        String returnStr=checkSessions(dao,messages,request,stuNum);
					                if(!returnStr.equals("endpage")){
						                  if(!telecentreCodeValid(dao,teleCode,messages,request,telecentreForm)){
						                	  log.info(this+": performTask(): Student '"+stuNum+"' login to Telecentre '"+centreName+"("+teleCode+")' at "+
			                						  timeNow.get(Calendar.HOUR_OF_DAY)+":"+currMinAppend+". Telecode "+telecentreForm.getTelecentreCode()+
			                						  " invalid. LOGIN DENIED!");		//Sifiso Changes:2017/03/14:SR62915
						                	  
						    	               return mapping.findForward("startpage");
						                  }
						                  assignStuHours(dao,""+stuNum,month,yearInt);
			                              assignTeleHours(dao,teleCode,month,yearInt);
			                              boolean stuHoursLessThanZero=stuHrsLessThanZero(dao,""+stuNum,month,yearInt,request);
			                              boolean telHoursLessThanZero=telHrsLessThanZero(dao,teleCode,month,yearInt,request);
			                              if(stuHoursLessThanZero||telHoursLessThanZero){
			                            	  if(stuHoursLessThanZero){
			                            		  log.info(this+": performTask(): Student '"+stuNum+"' login to Telecentre '"+centreName+"("+teleCode+")' at "+
				                						  timeNow.get(Calendar.HOUR_OF_DAY)+":"+currMinAppend+". LOGIN DENIED! "+
				                						  "Student Monthly hour limit reached."); 		//Sifiso Changes:2017/03/10:SR62915
			                            	  }else if(telHoursLessThanZero){
			                            		  log.info(this+": performTask(): Student '"+stuNum+"' login to Telecentre '"+centreName+"("+teleCode+")' at "+
				                						  timeNow.get(Calendar.HOUR_OF_DAY)+":"+currMinAppend+". LOGIN DENIED! "+
				                						  "Telecentre Monthly hour limit reached."); 	//Sifiso Changes:2017/03/10:SR62915
			                            	  }
			                            	  
			                	               return mapping.findForward("startpage");
					                      }
								          //Sifiso Changes:2016/11/18: Do checks for student weekly hours and daily hours and
							    		  //redirect to permission denied page if daily OR weekly hours are used up
							    		  if(toPageStrWeekly.equals("empty")){
							    			  messages.add(ActionMessages.GLOBAL_MESSAGE,
											  new ActionMessage("errors.message.limitWeeklyHours"));
											  addErrors(request, messages);
											  log.info(this+": performTask(): Student '"+stuNum+"' login to Telecentre '"+centreName+"("+teleCode+")' at "+
			                						  timeNow.get(Calendar.HOUR_OF_DAY)+":"+currMinAppend+". LOGIN DENIED! "+
			                						  "Student Weekly hour limit reached."); 	//Sifiso Changes:2017/03/14:SR62915
											  
											  return mapping.findForward("empty");
							    		  }else if(toPageStrDaily.equals("empty")){
							    			  messages.add(ActionMessages.GLOBAL_MESSAGE,
											  new ActionMessage("errors.message.limitDailyHours"));
											  addErrors(request, messages);
											  log.info(this+": performTask(): Student '"+stuNum+"' login to Telecentre '"+centreName+"("+teleCode+")' at "+
			                						  timeNow.get(Calendar.HOUR_OF_DAY)+":"+currMinAppend+". LOGIN DENIED! "+
			                						  "Student Daily hour limit reached."); 	//Sifiso Changes:2017/03/14:SR62915
											  
							    			  return mapping.findForward("empty");
							    		  }
					                      dao.setTimeStamps(teleCode,telecentreForm.getUserId(),dateTime2);
					                      //Sifiso Changes:2017/03/23:SR62915-Added parameter dateTime2 to record exact time session was started instead of time email was sent
				                          //sendStartEmail(form,request);			//Sifiso Changes:2017/03/23:SR62915:Removed
					                      sendStartEmail(form,request,dateTime2);	//Sifiso Changes:2017/03/23:SR62915:Added
					                }
					                log.info(this+": performTask(): Student '"+stuNum+"' login to Telecentre '"+centreName+"("+teleCode+")' at "+
	                						  timeNow.get(Calendar.HOUR_OF_DAY)+":"+currMinAppend+". Telecentre Code '"+teleLoginCode+
					                		  "' used. LOGIN GRANTED! ");  //Sifiso Changes:2017/03/14:SR62915
					                
				                    return mapping.findForward("endpage");
            				  }
            			 }
    }
    private boolean stuHrsLessThanZero(TelecentreDAO dao,String stuNum,
    		          int month,int yearInt,HttpServletRequest request){
    				  //Sifiso Changes:Changed below line:2016/06/30-To change the type from int to double
    	              //int stuAvailHrs = dao.getStudentAvailableHours(stuNum,month,yearInt);
    				  double stuAvailHrs = dao.getStudentAvailableHours(stuNum,month,yearInt);	//Sifiso Changes
    	              if(stuAvailHrs<=0){
    	        	       addErrorMessage("errors.message.nohrs",request);
    	        	       log.info(this+": stuHrsLessThanZero(): Student '"+stuNum+"' Student Monthly hour limit reached. "+
         						  "Available monthly hours are: "+stuAvailHrs); 		//Sifiso Changes:2017/03/10:SR62915
    	        	       
 	 	                   return true;
    	              }else{
    	        	      return false;
                      }
    }
    private boolean telHrsLessThanZero(TelecentreDAO dao,String teleCode,
	          int month,int yearInt,HttpServletRequest request){
    		//Sifiso Changes:Changed below line:2016/06/30-To change the type from int to double
    	    //int centreAvailHrs = dao.getTelecentreAvailableHours(teleCode,month,yearInt);
    		double centreAvailHrs = dao.getTelecentreAvailableHours(teleCode,month,yearInt);	//Sifiso Changes
    	    if(centreAvailHrs<=0){
    	       String centreName = dao.getCentreName(teleCode); 			//Sifiso Changes:2017/03/14:SR62915:For use in log
      	       addErrorMessage("errors.message.nohrsforcentre",request);
      	       log.info(this+": telHrsLessThanZero(): Telecentre '"+centreName+"' Telecentre Monthly hour limit reached. "+
					  "Available monthly hours are: "+centreAvailHrs); 		//Sifiso Changes:2017/03/14:SR62915
      	       
                   return true;
            }else{
      	      return false;
            }
    }
    private void addErrorMessage(String errorMsg,HttpServletRequest request){
    	           ActionMessages messages = new ActionMessages();
                   messages.add(ActionMessages.GLOBAL_MESSAGE,
	                new ActionMessage(errorMsg));
	              addErrors(request, messages);
    }
	private String checkSessions(TelecentreDAO dao,ActionMessages messages,
			          HttpServletRequest request,int stuNum ){
		// the methos checks if there is a session at present 
		//and return string 'endpage if there is a session else it returns empty string
                       	TelecentreLogInTracker  tloj=new TelecentreLogInTracker();
		                boolean sessionIsOn=tloj.checkVisitsStatus(""+stuNum);
		                int totActiveSessions=dao.activeSessions(stuNum);
		                if((totActiveSessions>0)||(sessionIsOn)){
		                	sessionIsOn=true;
		                }else{
		                	sessionIsOn=false;
		                }
                        if(sessionIsOn){
                        	 messages.add(ActionMessages.GLOBAL_MESSAGE,
        				             new ActionMessage("errors.message.alreadylogged"));
        		                addErrors(request, messages);
        		                //dao.getcentreName(""+stuNum,dao.getLatestTime(""+stuNum)); 								 //Sifiso Changes:2017/03/15:SR62915:Removed
        		                
        		                String telName = dao.getcentreName(""+stuNum,dao.getLatestTime(""+stuNum));					 //Sifiso Changes:2017/03/15:SR62915
        		                log.info(this+": checkSessions(): Student '"+stuNum+"' already logged in at '"+telName+"'"); //Sifiso Changes:2017/03/15:SR62915
        		  										
        		                return "endpage";
		                }else{
		                	return "";
		                }
	 }
	 private boolean telecentreCodeValid(TelecentreDAO dao,TelecentreForm telecentreForm,
	          ActionMessages messages, HttpServletRequest request )throws Exception{
             boolean telecentreCodeValid=true;
             boolean codeValid=dao.isTelecentreCodeValid(telecentreForm.getTelecentreCode(),
            		           telecentreForm.getTelecentreName());
             
             if (!codeValid){
                    messages.add(ActionMessages.GLOBAL_MESSAGE,
                    new ActionMessage("error.telecode"));
                    addErrors(request, messages); 
                    
                    telecentreCodeValid=false;
             }
            return telecentreCodeValid;
    }
    private boolean telecentreNameValid(TelecentreDAO dao,String teleCode,
                     ActionMessages messages, HttpServletRequest request ){
    	             boolean telecentreCodeValid=true;
    	             if (teleCode.equals("-1")||teleCode.equals("")) {
                         messages.add(ActionMessages.GLOBAL_MESSAGE,
                         new ActionMessage("error.select.telecentrename"));
                         telecentreCodeValid=false;
                         addErrors(request, messages);
                         
                     }
                     return telecentreCodeValid;
    }
    private boolean teleCodeEmpty(TelecentreForm telecentreForm,
	             ActionMessages messages, HttpServletRequest request ){
    	         boolean telecentreCodeValid=false; 
	             String telecentreCode=telecentreForm.getTelecentreCode();
                 if(telecentreCode == null||telecentreCode.equals("0")||telecentreCode.equals("")){
                     messages.add(ActionMessages.GLOBAL_MESSAGE,
                     new ActionMessage("enter.telecentre.code"));
                     addErrors(request, messages); 
                     telecentreCodeValid=true;
                  }
                return telecentreCodeValid;
    }
    private boolean telecentreCodeValid(TelecentreDAO dao,String teleCode,
                      ActionMessages messages, HttpServletRequest request,
                      TelecentreForm telecentreForm)throws Exception{
    	              boolean nameValid=telecentreNameValid(dao,teleCode,messages,request);
    	              boolean empty=teleCodeEmpty(telecentreForm,messages,request);
    	              boolean valid=telecentreCodeValid(dao,telecentreForm,messages,request);
    	              if(!nameValid||empty||!valid){
    	            	 return false;
    	              }else{
    	            	  return true;
    	              }
    }
    private void  assignTeleHours(TelecentreDAO dao,String teleCode,int month,int yearInt){
        if(!dao.isTelecentreHoursAssigned(teleCode,month,yearInt)){
            dao.assignCentreHours(teleCode,month,centreHrs,yearInt);
            dao.allocateStudentTelecentreHrs("System",(new Date()),"Normal",teleCode,"",centreHrs);
        }
   }
    private void  assignStuHours(TelecentreDAO dao,String stuNum,int month,int yearInt){
              if(!dao.isStudentHoursAssigned(stuNum,month,yearInt)){
	              dao.assignStudentHours(stuNum,month,stuHrs,yearInt);
                  dao.allocateStudentTelecentreHrs("System",(new Date()),"Normal","",stuNum,stuHrs);
              }
    }
    //Sifiso Changes:This is the action that sends the update of the total hours to the database
    public ActionForward endTask(
            ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
                    HttpServletResponse response)throws Exception{
                    TelecentreForm telecentreForm = (TelecentreForm) form;
                    ActionMessages messages = new ActionMessages();
                    TelecentreDAO dao = new TelecentreDAO();
                    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String dateTime3 = dateFormat.format(new Date());
                    String studentNumber=telecentreForm.getUserId();
                    String startTimeStamp=dao.getStartTimeStamp(studentNumber);
                    String latestEndTimeStamp=dao.getLatestEndTimeStamp(studentNumber);	 //Sifiso Changes:Added:2016/09/08:SR - SR8071
            	    Date dStartTimeStamp = dateFormat.parse(startTimeStamp);
                	telecentreForm.setStartTimeStamp(startTimeStamp);
            	    DateFormat df= new SimpleDateFormat("dd");
                    DateFormat dfH= new SimpleDateFormat("HH");
                    DateFormat dfm= new SimpleDateFormat("mm");
                    DateFormat dfs=new SimpleDateFormat("ss");		//Sifiso Changes:Added:2016/06/22-Date format in seconds for use in setStartSec() below
                    String teleCode = dao.getTelecentreId(startTimeStamp,studentNumber);
                    telecentreForm.setTelecentreName(dao.getCentreName(teleCode));
            	    telecentreForm.setCode(teleCode);
            	    telecentreForm.setStartDay(Integer.parseInt(df.format(dStartTimeStamp)));
            	    telecentreForm.setStartHour(Integer.parseInt(dfH.format(dStartTimeStamp)));
            	    telecentreForm.setStartMin(Integer.parseInt(dfm.format(dStartTimeStamp)));
            	    telecentreForm.setStartSec(Integer.parseInt(dfs.format(dStartTimeStamp)));			//Sifiso Changes:Added:2016/06/23-setStartSec method
            	    double totHrs=dao.getTotalHrsBeforeUpdate(teleCode,studentNumber,startTimeStamp);
            	    if(totHrs==0){
            	    	
            	    		//Sifiso Changes:Added:2016/07/01-We are returning the totalTime and setting the time duration unit
            	    		//Sifiso Changes:2017/03/23:SR62915: Added dateTime3 argument to ensure current logout times are constant across all calculations
	     	                double totalTime =getCurrDuration(telecentreForm,dateTime3);
	     	                double dbTotalTime=convertToHours(totalTime,timeDurationUnit);		//Sifiso Changes:Added:2016/07/06-Convert the time returned to hours for DB storage
	     	                telecentreForm.setDurationUnit(timeDurationUnit);					//Sifiso Changes:Added:2016/07/06-Time duration unit to be displayed to user
	     	                log.info(this+": endTask(): Student '"+studentNumber+"' total session time is '"+totalTime+"' "+timeDurationUnit);
	     	                
	     	                //if(totalTime==1){ //jeremia changed 1 to 7 						//Sifiso Changes:Changed:2016/06/29 and changed below
	     	                
	     	                //Sifiso Changes:SR37433 & SR62915:compare startTimestamp with current timestamp (datetime3) to check if 2 hours have been used for session
	     	                //method calculateSessionHours() must adjust dbTotalTime as per update in dao.updateTotalHrs() method
	     	                //if no adjustments are required, method returns same value returned by convertToHours() as above
	     	                dbTotalTime = calculateSessionHours(dateTime3,startTimeStamp,teleCode,studentNumber,dbTotalTime,dao,latestEndTimeStamp);
	     	             
	      	                telecentreForm.setCurrentVisistDuration(""+roundOfDuration(totalTime));		//Total time without conversion to hours, will be displayed with relevant unit
	                        updateStuAvailHours(telecentreForm,dbTotalTime,dao);						//Sifiso ChangesChanged:2016/07/01-To use the 'dbTotalTime' in hours
	                        updateTeleAvailHours(telecentreForm,dbTotalTime,dao);						//Sifiso ChangesChanged:2016/07/01-To use the 'dbTotalTime' in hours
	                        //Sifiso Changes:2017/03/23:SR62915-Added parameter dateTime3 to record exact time session was ended instead of time email was sent
          	                //sendConclusionEmail(form,request);	
	                        sendConclusionEmail(form,request,dateTime3);
              	    	    //Sifiso Changes:Added:2016/08/14:Begin procedure to send email about available hours
            	    	    if(telecentreForm.getCentreAvailHrs()<=100){
            	    	    	String subjectEmail="Telecentre Hours Pre-set Limit Reached";
            	    	    	String bodyEmail="The Telecentre available hours for Telecentre "+telecentreForm.getTelecentreName()+
            	    	    			" have reached the pre-set limit. The available hours are: "+telecentreForm.getCentreAvailHrs();
            	    	    	sendEmail(subjectEmail,bodyEmail,emailTelecentreGroup);
            	    	    	
            	    	    	log.info(this+": endTask(): Telecentre '"+telecentreForm.getTelecentreName()+"' has "+
            	    	    			telecentreForm.getCentreAvailHrs()+" available hours. Pre-set Limit Reached! Email sent to '"+
            	    	    			emailTelecentreGroup+"'");		    //Sifiso Changes:2017/03/17:SR62915:Log
            	    	    }
            	    	    if(telecentreForm.getStuAvailHrs()<=6){
            	    	    	String subjectEmail="Student Hours Pre-set Limit Reached";
            	    	    	String bodyEmail="The Student Hours for Student with Student Number "+telecentreForm.getUserId()+
            	    	    			" have reached the pre-set limit. The available hours are: "+telecentreForm.getStuAvailHrs();
            	    	    	sendEmail(subjectEmail,bodyEmail,emailTelecentreGroup);
            	    	    	
            	    	    	log.info(this+": endTask(): Student '"+telecentreForm.getUserId()+"' has "+
            	    	    			telecentreForm.getStuAvailHrs()+" available hours. Pre-set Limit Reached! Email sent to '"+
            	    	    			emailTelecentreGroup+"'");		    //Sifiso Changes:2017/03/17:SR62915:Log
            	    	    }
            	    }else{
            	    		telecentreForm.setDurationUnit(timeDurationUnit);			//Sifiso Changes:Added:2016/07/06-Time duration unit to be displayed to user
            	        	telecentreForm.setCurrentVisistDuration(""+2);		
            	    	    setStuAvailHours(telecentreForm,dao);
            	    	    setTeleAvailHours(telecentreForm,dao);
            	    	    //Sifiso Changes:Added:2016/08/14:Begin procedure to send email about available hours
            	    	    if(telecentreForm.getCentreAvailHrs()<=100){
            	    	    	String subjectEmail="Telecentre Hours Pre-set Limit Reached";
            	    	    	String bodyEmail="The Telecentre available hours for Telecentre "+telecentreForm.getTelecentreName()+
            	    	    			" have reached the pre-set limit. The available hours are: "+telecentreForm.getCentreAvailHrs();
            	    	    	sendEmail(subjectEmail,bodyEmail,emailTelecentreGroup);
            	    	    	
            	    	    	log.info(this+": endTask(): Telecentre '"+telecentreForm.getTelecentreName()+"' has "+
            	    	    			telecentreForm.getCentreAvailHrs()+" available hours. Pre-set Limit Reached! Email sent to '"+
            	    	    			emailTelecentreGroup+"'");		    //Sifiso Changes:2017/03/17:SR62915:Log
            	    	    }
            	    	    if(telecentreForm.getStuAvailHrs()<=6){
            	    	    	String subjectEmail="Student Hours Pre-set Limit Reached";
            	    	    	String bodyEmail="The Student Hours for Student with Student Number "+telecentreForm.getUserId()+
            	    	    			" have reached the pre-set limit. The available hours are: "+telecentreForm.getStuAvailHrs();
            	    	    	sendEmail(subjectEmail,bodyEmail,emailTelecentreGroup);
            	    	    	
            	    	    	log.info(this+": endTask(): Student '"+telecentreForm.getUserId()+"' has "+
            	    	    			telecentreForm.getStuAvailHrs()+" available hours. Pre-set Limit Reached! Email sent to '"+
            	    	    			emailTelecentreGroup+"'");		    //Sifiso Changes:2017/03/17:SR62915:Log
            	    	    }
            	    }
                    telecentreForm.setActiveSessions(0);
            return mapping.findForward("lastpage");
    }
    
    //Sifiso Changes:this is where we set the total time rules: THIS MUST BE MODIFIED:2016/06/22
    //Sifiso Changes:Modified:2016/07/01-We are returning the time duration VALUE and setting the time duration UNIT
    //calculations will be performed on VALUE based on UNIT in method 'convertToHours()'
    //results will be converted back to hours to be stored in DB in method 'convertToHours'
    //Sifiso Changes:2017/03/23:SR62915: Added currentTimeStamp parameter to ensure current logout times are constant across all calculations
    //function was getting most current time which could be different from dateTime3 timestamp recorded when user clicks the 'End Telecentre' button
    private double getCurrDuration(TelecentreForm  telecentreForm, String currentTimeStamp){

    	     int endHour=Integer.parseInt(currentTimeStamp.substring(11,13));	  //exact Hour the user ended session
    	     int endDay=Integer.parseInt(currentTimeStamp.substring(8,10));	  	  //exact Day the user ended session
    	     int endMin=Integer.parseInt(currentTimeStamp.substring(14,16));	  //exact Minute the user ended session
    	     int endSec=Integer.parseInt(currentTimeStamp.substring(17,19));	  //exact Second the user ended session

 	         //int  currVisitDration=0;
    	     double currVisitDration=0;		//Sifiso Changes:We want time in decimal so that even hours are reflected as decimal
 	         if(telecentreForm.getStartDay()==endDay){					
 	    	    	//currVisitDration=1;					 //Sifiso Changes:Changed:2016/06/22-Set total hours to 1 hour:THIS MUST BE CHANGED
 	        	 	//Sifiso Changes:Added:2016/07/07-Conditions to assign time duration UNIT to be used in jsp page to display 'currentVisistDuration'
	    			//and to set the time duration VALUE which will later be converted to hours
 	        	 	int secDiff=0;
 	        	 	int minDiff=0;
 	        	 	int hourDiff=0;
 	        	 	boolean inSameMin=false;
 	        	 	boolean inSameHour=false;
 	        	 	//We are in the same minute of the same hour
 	        	 	if((endHour==telecentreForm.getStartHour())&&(endMin==telecentreForm.getStartMin())){
 	        	 		inSameMin=true;
 	        	 	}
 	        	 	//We are in the same hour of the same day (endDay=...StartDay())
 	        	 	if(endHour==telecentreForm.getStartHour()){
 	        	 		inSameHour=true;
 	        	 	}
 	        	 	//We are still in current minute so perform calculations in seconds
 	        	 	if(inSameMin){		
 	        	 		secDiff=endSec-telecentreForm.getStartSec();	//the seconds used in current minute
 	        	 	}
 	        	 	else{	//If we are already in the next minute since we started
        	 				int previousSec=SECONDS_IN_MINUTE-telecentreForm.getStartSec();	//get the seconds used in previous minute
 	 	        	 		secDiff=endSec+previousSec;										//add the seconds used in previous minute to seconds used in current minute
 	        	 	}
 	        	 	
 	        	 	//We are still in current hour so perform calculations in minutes
 	        	 	if((inSameMin!=true)&&inSameHour){
 	        	 		minDiff=endMin-telecentreForm.getStartMin();		//the minutes used in current hour
 	        	 	}
 	        	 	else{	//If we have passed the starting hour since we started
 	        	 			if((inSameMin!=true)&&(inSameHour!=true)){	//We are in the next hour (staring hour+1, only 1 hour has passed)
 	        	 				hourDiff=endHour-telecentreForm.getStartHour();		//the hours passed since we started
		 	        	 		if(hourDiff==1){
			 	        	 		int previousMin=MINUTES_IN_HOUR-telecentreForm.getStartMin(); //get the minutes used in previous hour
			 	        	 		minDiff=endMin+previousMin;									  //add the minutes used in previous hour to minutes used in next current hour
		 	        	 		}else{
		 	        	 			if(hourDiff>1){
		 	        	 				int previousMin=(MINUTES_IN_HOUR*(hourDiff))-telecentreForm.getStartMin(); //get the minutes used in previous hour
				 	        	 		minDiff=endMin+previousMin;									  //add the minutes used in previous hour to minutes used in next current hour
		 	        	 			}
		 	        	 		}
 	        	 			}
 	        	 	}
 	        	 	
 	        	 	//If the seconds passed are less than 60, we have used seconds
	 	        	if((secDiff<SECONDS_IN_MINUTE)&&(minDiff<=1)){		//The minDiff is less than 1 minute and secDiff is less than 60 seconds
		    			if(secDiff<0)					//Sifiso Changes:2017/03/23:SR62915:Remove negative total_hours
		    				currVisitDration=0;
		    			else if(secDiff>=0)
		    				currVisitDration=secDiff;
		    			if(secDiff>1){
		    				timeDurationUnit="Seconds";
		    			}else{
		    				timeDurationUnit="Second";
		    			}
		    		}
	 	        	else{
	 	        		//If the minutes passed are less than 60, we have used minutes
	 	        		if(minDiff<MINUTES_IN_HOUR){
	 	    	    		currVisitDration=minDiff;
	 	    	    		if(minDiff>1){
			    				timeDurationUnit="Minutes";
			    			}else{
			    				timeDurationUnit="Minute";
			    			}	
	 	    	    	}
	 	    	    	else{
	 	    	    		//If the minutes passed are more than 60, we have used hours
	 	    	    		if (minDiff>=MINUTES_IN_HOUR){	
	 	    	    			//the below conversion will get the hours in absolute form without the decimals,
	 	    	    			//thus we will lose the minutes used but only get the hours used (e.g. 1 hour instead of 1.5 hours)
 	    	    				//currVisitDration=(endHour-telecentreForm.getStartHour());
	 	    	    			currVisitDration=(double)minDiff/(double)MINUTES_IN_HOUR;	//convert the minutes used to hours(e.g. 90 mins = 90/60-->1.5 hours)
 		 	    	    		if(currVisitDration>1){
 				    				timeDurationUnit="Hours";
 				    			}else{
 				    				timeDurationUnit="Hour";
 				    			}
 	    	    			}
	 	    	    	}			
	 	        	}
 	    	    	 /* Sifiso Changed:BEGIN-2016/06/22:THIS MUST BE CHANGED! WE WANT THE EXACT TIME SPENT!
 	    	    	  if(endMin>telecentreForm.getStartMin()){
	    	    		  currVisitDration++;
	    	    	  }
 	    	    	  if(currVisitDration>2){
 	    	    		currVisitDration=2;
 	    	          }Sifiso Changes: END*/
 	    	 }/*else{ Sifiso Changes: BEGIN: THIS MUST BE CHANGED:Dont record 2 hours is no time was used today!
 	    	     	currVisitDration+=2; //Sifiso Changes:THIS MUST BE CHANGED:We want the exact time used TODAY and must not increment if no time was used TODAY
 	    	 } Sifiso Changes: END*/
 	      return  currVisitDration;
   }
    
   //Sifiso Changes:Added:2016/06/29:Updated:2017/03-Duration must be present in order to update timestamps
   //SR37433 & SR62915:compare startTime with current timestamp (currentTime) to check if 2 hours have been used for session
   //if duration is 0, the user is still logged in, but if greater than 0 the user session is complete and the end & start times must be updated	
   private double calculateSessionHours(String currentTime,String startTime, String teleCode,String studentNumber,
 			 double sessionDuration, TelecentreDAO dao, String latestEndTimeStamp) throws Exception{
	   
	   //session duration to update Student and Telecentre Available Hours tables as per TOTAL_HRS column in STUDENT_TELECENTRE table
	   //this must be returned with the calculateSessionHours() method. 
	   //If no adjustments are made, it must return same value as sessionDuration (dbTotalTime) argument
	   double dbTotalTimeAdjust = sessionDuration;	
	   
	   if(sessionDuration>0){			//use this condition for updating timestamps (i.e. dao.updateTimeStamps())
        	 double sessionHours = 0;												  //hours used in actual duration when session ends
      	     double sessionMins = 0;												  //minutes in duration time when session ends
      	     double sessionSec = 0;													  //seconds in duration time when session ends
      	     int currentEndHour = Integer.parseInt(currentTime.substring(11,13));	  //actual hour the user ended session
      	     int sessionStartHour = Integer.parseInt(startTime.substring(11,13));     //actual hour the user started session
      	     int currentEndMin = Integer.parseInt(currentTime.substring(14,16));		  
      	     int sessionStartMin = Integer.parseInt(startTime.substring(14,16));
      	     int currentEndSec = Integer.parseInt(currentTime.substring(17,19));
      	     int sessionStartSec = Integer.parseInt(startTime.substring(17,19));
      	     
      	     int dbEndHour = Integer.parseInt(latestEndTimeStamp.substring(11,13));	  //hour in database for end of 2 hour session
      	     int dbEndMin = Integer.parseInt(latestEndTimeStamp.substring(14,16));	  //minute in database for end of 2 hour session
      	     int dbEndSec = Integer.parseInt(latestEndTimeStamp.substring(17,19));	  //second in database for end of 2 hour session
      	     
    	 	 boolean inSameMin=false;
    	 	 boolean inSameHour=false;
    	 	 if((currentEndHour==sessionStartHour) && (currentEndMin==sessionStartMin)){
    	 		 inSameMin=true;					//we are in the same minute of the same hour
    	 	 }
    	 	 if(currentEndHour==sessionStartHour){
    	 		 inSameHour=true;					//we are in the same hour of the same day (endDay=StartDay)
    	 	 }
    	 	 
    	 	 //calcuate HOURS used in session
    	 	 sessionHours = sessionDuration;	   //Actual duration as calculated in getCurrDuration() method
    	 	 if(sessionHours<0)		//Check for negative HOURS [occurs when session ends quickly OR same time as end Hour]
    	 		sessionHours=0;
    	 	
    	 	 //calcuate SECONDS used in session 
    	 	 if(inSameMin)												//we are in CURRENT MINUTE
    	 		 sessionSec=currentEndSec-sessionStartSec;				//the SECONDS in current minute
    	 	 else if(!inSameMin && inSameHour){							//we are in CURRENT HOUR
    	 		 int minsInCurrHour=currentEndMin-sessionStartMin;		
 		 		 if(minsInCurrHour>0){						    	
 		 			 int previousSec=(SECONDS_IN_MINUTE*minsInCurrHour)-sessionStartSec;
 		 			 sessionSec=currentEndSec+previousSec;				//the SECONDS in all minutes of session
 		 		 }
	 		 }else if(!inSameMin && !inSameHour){					    //we are NOT IN CURRENT HOUR
 		 		if(sessionHours>0){
	 				double previousSec=(SECONDS_IN_HOUR*sessionHours)-sessionStartSec;
	 				sessionSec=currentEndSec+previousSec;				//the SECONDS in all hours of session			 
	 			}
    	 	 }
    	 	 if(sessionSec<0)	//Check for negative SECONDS [occurs when session ends quickly OR same time as end Sec]
				sessionSec=0;
    	 	 
    	 	 //calcuate MINUTES used in session
    	 	 if(!inSameMin && inSameHour){					//we are in CURRENT HOUR
    	 		sessionMins=currentEndMin-sessionStartMin;	//the MINUTES in current hour
    	 	 }
    	 	 else if(!inSameMin && !inSameHour){  			//we are NOT in CURRENT HOUR
    	 		 if(sessionHours>0){
	 				double previousMin=(MINUTES_IN_HOUR*(sessionHours))-sessionStartMin;
	 				sessionMins=currentEndMin+previousMin;	//the MINUTES in all hours of session						 
	 			 }
    	 	 }
    	 	 if(sessionMins<0)	//Check for negative MINUTES [occurs when session ends quickly OR same time as end Min]
    	 		 sessionMins=0;
    	 	
      	     //SR62915: if 2 hours have NOT been used - update timestamps with exact end timestamp(currentTime) recorded when session was ended
      		 if((sessionHours>=0) && (sessionHours<2))						   						 //less than 2 hours used-we are still in 2 hour limit
      			 dao.updateTimeStamps(teleCode,studentNumber,startTime,currentTime);	
      		 if(sessionHours<0)																		 //less than 1 hour used-we are still in 2 hour limit
      			 dao.updateTimeStamps(teleCode,studentNumber,startTime,currentTime);					 
      		 else if((sessionHours==2) && (currentEndHour<=dbEndHour) && (currentEndMin<=dbEndMin)){  //2 hrs & less Mins than DB latest Min-still in 2 hour limit
      			 if(currentEndMin<dbEndMin)							 								  //just update if current minutes are less than db end minutes
      				 dao.updateTimeStamps(teleCode,studentNumber,startTime,currentTime); 	
      			 else if((currentEndMin==dbEndMin) && (currentEndSec<=dbEndSec))					  //check seconds if minutes are equal							 
      				 dao.updateTimeStamps(teleCode,studentNumber,startTime,currentTime); 
      		 }

      		 //Sifiso Changes:2017/03/17:SR62915:Logs
      		 if((sessionHours>0) && (sessionHours<=2) && (sessionMins>=MINUTES_IN_HOUR) && (sessionMins<=MINUTES_IN_HOUR*2)){
      			 log.info(this+": calculateSessionHours(): Student '"+studentNumber+"' ended session BEFORE 2 hour timeout! "+
      					 "Student used LESS THAN 2 HOURS IN SESSION! START TIMESTAMP is '"+ startTime+"'. END TIMESTAMP is '"+
      					 currentTime+"'. Actual duration is: "+sessionDuration+ " Hour(s)");
      	     }else if((sessionHours>=0) && (sessionHours<=2) && (sessionMins>0) && (sessionMins<=MINUTES_IN_HOUR)){		//in same hour (end Hour and start Hour as equal)
      			 log.info(this+": calculateSessionHours: Student '"+studentNumber+"' ended session BEFORE 2 hour timeout! "+
      					"Student used LESS THAN 2 HOURS IN SESSION! START TIMESTAMP is '"+ startTime+"'. END TIMESTAMP is '"
      					 +currentTime+"'. Actual duration is: "+sessionMins+ " Minute(s)");
      	     }else if((sessionHours>=0) && (sessionHours<=2) && (sessionMins<=1) && (sessionSec>=0)){  					//in same Min & hour (end Min and start Min as equal)
      	    	 log.info(this+": calculateSessionHours: Student '"+studentNumber+"' ended session BEFORE 2 hour timeout! "+
      	    			"Student used LESS THAN 2 HOURS IN SESSION! START TIMESTAMP is '"+startTime+"'. END TIMESTAMP is '"+
      	    			 currentTime+"'. Actual duration is: "+sessionSec+ " Second(s)");
      	     }
       }
	   
       //Sifiso Changes: SR37433 & SR62915: Max time always 2 hours
       if(sessionDuration>2){			//use this condition for updating total_hours (i.e. dao.updateTotalHrs)
    	   log.info(this+": calculateSessionHours(): Student '"+studentNumber+"' ended session AFTER 2 hour time limit! "+
    			   "Student used MORE THAN 2 HOURS IN SESSION! START TIMESTAMP is '"+ startTime+"'. END TIMESTAMP is '"+currentTime+"'. "+
    			   "Actual duration is: "+sessionDuration+ " Hour(s)");	
    	   
	       //Sifiso Changes:SR37433 & SR62915:Logic to check SUM of total hours in database less than 2 hours and adjust
	       //Do this just for DB storage but in webpage display session use time exactly as it is for session
	       double totalHoursUsedToday = 0.0;
	       totalHoursUsedToday = dao.getStudentDailyUsedHours(studentNumber,currentTime.substring(0,10));
	       String tempTimeDurationUnit = timeDurationUnit;	//current time unit as calculated in convertToHours()
	        	
	       if(totalHoursUsedToday<=2)				        //today's total hours in database is 2 hours or less, must ke kept at maximum 2 hours
	    	   sessionDuration = 2-totalHoursUsedToday;		//get difference to makeup 2 hours maximum for total hours used today
	       else if(totalHoursUsedToday>=2){
	    	   sessionDuration=1/SECONDS_IN_HOUR;	 		//record 1 second for current session hours for DB storage-no effect on business rules
	           timeDurationUnit = "Second";					//record time unit as 'Second'-for DB storage
	       }
	
	       dao.updateTotalHrs(teleCode,studentNumber,startTime,""+sessionDuration,timeDurationUnit);
	       dbTotalTimeAdjust = sessionDuration;	 //update tables used in updateStuAvailHours() and updateTeleAvailHours() methods with same sessionDuration value
	        	
	       log.info(this+": calculateSessionHours(): Student '"+studentNumber+"'. Previous Daily Session hours are "+totalHoursUsedToday+
	    		   ". Current Session Hours adjusted to: "+sessionDuration);
	       log.info(this+": calculateSessionHours(): Student '"+studentNumber+"'. Database updated with "+sessionDuration+" in TOTAL_HRS column and '"+
    			   timeDurationUnit+"' in TIME_UNIT column!");		
	       
	       timeDurationUnit = tempTimeDurationUnit;	 //restore time unit to what it was-for web page display
       }else{
    	   //Sifiso Changes:SR37433:Logic to check SUM of daily total hours in database less than 2 hours and adjust
	       double totalHoursUsedToday = 0.0;
	       double allDbTotalTime = 0.0;			       					//Hold current session time + total used hours today
	       totalHoursUsedToday = dao.getStudentDailyUsedHours(studentNumber,currentTime.substring(0,10));
	       String tempTimeDurationUnit = timeDurationUnit;				//current time unit as calculated in convertToHours()
	          	
	       if((totalHoursUsedToday>0) && (totalHoursUsedToday<=2)){
	    	   allDbTotalTime = sessionDuration + totalHoursUsedToday;	//Add sessionDuration(current session time) to already used time today
	            if(allDbTotalTime>2)									//current session time + total used hours today are more than 2 hour limit
	            	sessionDuration=2-totalHoursUsedToday;				//difference of 2 hours -(minus) total used hours today
	       }
           else if(totalHoursUsedToday>=2){
        	   	sessionDuration=1/SECONDS_IN_HOUR;	 					//record 1 second for current session hours for DB storage-no effect on business rules
          		timeDurationUnit = "Second";							//record time unit as 'Second'-for DB storage
           }
	          	
	       dao.updateTotalHrs(teleCode,studentNumber,startTime,""+sessionDuration,timeDurationUnit);
	       dbTotalTimeAdjust = sessionDuration;	 //update tables used in updateStuAvailHours() and updateTeleAvailHours() methods with same sessionDuration value
	          	
	       log.info(this+": calculateSessionHours(): Student '"+studentNumber+"'. Previous Daily Session hours are "+totalHoursUsedToday+
	    		   ". Current Session Hours adjusted to: "+sessionDuration);
	       log.info(this+": calculateSessionHours(): Student '"+studentNumber+"'. Database updated with "+sessionDuration+" in TOTAL_HRS column and '"+
    			   timeDurationUnit+"' in TIME_UNIT column!");	
	       
	       timeDurationUnit = tempTimeDurationUnit;	 //restore time unit to what it was-for web page display	       
       }
       return dbTotalTimeAdjust;  //update tables used in updateStuAvailHours() and updateTeleAvailHours() methods with same value as dao.updateTotalHrs()
   }

   //Sifiso Changes:Changed below line:2016/06/30-To change the 'int totHours' to double type
   private void updateStuAvailHours(TelecentreForm telecentreForm,double totHours,TelecentreDAO dao){
	               dateUtil  dateutil=new dateUtil();
	               //Sifiso Changes:Changed line below:2016/06/30-To change the type from int to double
	               double stuAvailHrs=dao.getStudentAvailableHours(telecentreForm.getUserId(),dateutil.getMonthInt(),dateutil.yearInt()); //Sifiso Changes
	               log.info(this+": updateStuAvailHours(): UPDATING STUDENT AVAILABLE HOURS for '"+telecentreForm.getUserId()+
                		   "'! Student currently has '"+stuAvailHrs+"' available hours! "+
	            		   totHours+" Hours must be Subtracted!"); //Sifiso Changes:2017/03/17:SR62915:Log
	               
	               double stuAvailHrsRounded;	//Sifiso Changes:Added:2016/07/06-Round off the hours to three decimal place
                   stuAvailHrs-=totHours;
                   if(stuAvailHrs<0){
      	             stuAvailHrs=0;
                   }
                   
                   dao.updateStudentHours(telecentreForm.getUserId(),dateutil.getMonthInt(),stuAvailHrs,dateutil.yearInt());
                   log.info(this+": updateStuAvailHours(): UPDATING STUDENT AVAILABLE HOURS for '"+telecentreForm.getUserId()+
                		   "'! Student available hours UPDATED to '"+stuAvailHrs+"'! "+
                		   totHours+" Hours Subtracted!"); 		   //Sifiso Changes:2017/03/17:SR62915:Log
                   
                   //Sifiso Changes:Added:2016/06/06-Method to round off the decimals
                   stuAvailHrsRounded=roundOfDuration(stuAvailHrs);
                   //Sifiso Changes:Changed line below:2016/07/06-To add the rounded off variable
                   //telecentreForm.setStuAvailHrs(stuAvailHrs);
                   telecentreForm.setStuAvailHrs(stuAvailHrsRounded);	//Sifiso Changes
   }
   private void setStuAvailHours(TelecentreForm telecentreForm,TelecentreDAO dao){
	              dateUtil  dateutil=new dateUtil();
	              //Sifiso Changes:Changed line below:2016/06/30-To change the type from int to double
                  //int stuAvailHrs=dao.getStudentAvailableHours(telecentreForm.getUserId(),dateutil.getMonthInt(),dateutil.yearInt());
	              double stuAvailHrs=dao.getStudentAvailableHours(telecentreForm.getUserId(),dateutil.getMonthInt(),dateutil.yearInt()); //Sifiso Changes
	              double stuAvailHrsRounded;	//Sifiso Changes:Added:2016/07/06-Round off the hours to three decimal place
	              
	              //Sifiso Changes:Added:2016/06/06-Method to round off the decimals
                  stuAvailHrsRounded=roundOfDuration(stuAvailHrs);
	              //Sifiso Changes:Changed line below:2016/07/06-To add the rounded off variable
                  //telecentreForm.setStuAvailHrs(stuAvailHrs);
                  telecentreForm.setStuAvailHrs(stuAvailHrsRounded);		//Sifiso Changes
   }
   //Sifiso Changes:Changed below line:2016/06/30-To change the 'int totHours' to double type
   //private void updateTeleAvailHours(TelecentreForm telecentreForm,int totHours,TelecentreDAO dao){
   //Sifiso Changes:Changed below line:2016/06/30-To change the 'int totHours' to double type
   private void updateTeleAvailHours(TelecentreForm telecentreForm,double totHours,TelecentreDAO dao){
	                   dateUtil  dateutil=new dateUtil();
	                   String teleCode=telecentreForm.getCode();
	                   //Sifiso Changes:Changed line below:2016/06/30-To change the type from int to double
	                   //int centreAvailHrs=dao.getTelecentreAvailableHours(teleCode,dateutil.getMonthInt(),dateutil.yearInt());
	                   double centreAvailHrs=dao.getTelecentreAvailableHours(teleCode,dateutil.getMonthInt(),dateutil.yearInt()); //Sifiso Changes
	                   log.info(this+": updateTeleAvailHours(): UPDATING TELECENTRE AVAILABLE HOURS for '"+teleCode+
	                		   "'! Telecentre currently has '"+centreAvailHrs+"' available hours! "+
	                		   totHours+" Hours must be Subtracted!"); //Sifiso Changes:2017/03/17:SR62915:Log
	                   
	                   double centreAvailHrsRounded;	//Sifiso Changes:Added:2016/07/06:Round off the decimals
                       centreAvailHrs-=totHours;
                       if(centreAvailHrs<0){
       	                  centreAvailHrs=0;
                       }
                       dao.updateCentreHours(teleCode,dateutil.getMonthInt(),centreAvailHrs,dateutil.yearInt());
                       log.info(this+": updateTeleAvailHours(): UPDATING TELECENTRE AVAILABLE HOURS for '"+teleCode+
                    		   "'! Telecentre available hours UPDATED to '"+centreAvailHrs+"'! "+
                    		   totHours+" Hours Subtracted!"); 		   //Sifiso Changes:2017/03/17:SR62915:Log
                       
                       //Sifiso Changes:Added:2016/07/06:Round off the decimals for display to end user on jsp
                       centreAvailHrsRounded=roundOfDuration(centreAvailHrs);
                       //Sifiso Changes:Changed line below:2016/07/06-To add the rounded off variable
                       //telecentreForm.setCentreAvailHrs(centreAvailHrs);
                       telecentreForm.setCentreAvailHrs(centreAvailHrsRounded);		//Sifiso Changes
   }
   private void setTeleAvailHours(TelecentreForm telecentreForm,TelecentreDAO dao){
                   dateUtil  dateutil=new dateUtil();
                   String teleCode=telecentreForm.getCode();
                   //Sifiso Changes:Changed line below:2016/06/30-To change the type from int to double
                   //int centreAvailHrs=dao.getTelecentreAvailableHours(teleCode,dateutil.getMonthInt(),dateutil.yearInt());
                   double centreAvailHrs=dao.getTelecentreAvailableHours(teleCode,dateutil.getMonthInt(),dateutil.yearInt()); //Sifiso Changes
                   double centreAvailHrsRounded;	//Sifiso Changes:Added:2016/07/06:Round off the decimals
                   dao.updateCentreHours(teleCode,dateutil.getMonthInt(),centreAvailHrs,dateutil.yearInt());
                   
                   //Sifiso Changes:Added:2016/07/06:Round off the decimals for display to end user on jsp
                   centreAvailHrsRounded=roundOfDuration(centreAvailHrs);
                   //Sifiso Changes:Changed line below:2016/07/06-To add the rounded off variable
                   //telecentreForm.setCentreAvailHrs(centreAvailHrs);
                   telecentreForm.setCentreAvailHrs(centreAvailHrsRounded);		//Sifiso Changes
  }
   
  //Sifiso Changes:2017/03/23:SR62915-Added parameter currentTimeStamp to record exact time session was ended instead of time email was sent
  //private void  sendConclusionEmail(ActionForm form,HttpServletRequest request) throws Exception{
  private void  sendConclusionEmail(ActionForm form,HttpServletRequest request,String currentTimeStamp) throws Exception{
                  //String onlyDate,onlyTime;												  //Sifiso Changes:2017/03/23:SR62915:Removed												
				  String currentEndHour = currentTimeStamp.substring(11,13);  	//Sifiso Changes:2017/03/23:SR62915:Added
				  String currentEndMin = currentTimeStamp.substring(14,16);	  	//Sifiso Changes:2017/03/23:SR62915:Added
				  String currentEndSec = currentTimeStamp.substring(17,19);	  	//Sifiso Changes:2017/03/23:SR62915:Added
				  String currentDate = currentTimeStamp.substring(0,10);	 	//Sifiso Changes:2017/03/23:SR62915:Added
				  				  
                  TelecentreForm telecentreForm = (TelecentreForm) form;
                  //dateUtil dateutil=new dateUtil();						 	//Sifiso Changes:2017/03/23:SR62915:Removed	
                  String subject = "Telecentre visit for Student "+telecentreForm.getUserId()+" concluded";
	              //String body=getBodyOfClosingEmail(telecentreForm,dateutil.dateOnly(),dateutil.timeOnly());				//Sifiso Changes:2017/03/23:SR62915:Removed	
                  String body=getBodyOfClosingEmail(telecentreForm,currentDate,currentEndHour,currentEndMin,currentEndSec);	//Sifiso Changes:2017/03/23:SR62915:Added
	              int stuNum=Integer.parseInt(telecentreForm.getUserId());
	              sendEmail(stuNum,telecentreForm,request,subject,body);
	              
	              //Sifiso Changes:2017/03/15:SR62915:Added Log
                  log.info(this+": sendConclusionEmail(): Telecentre Conclusion Visit Mail for Student '"+stuNum+"' sent at "+
                		  currentDate +" "+currentEndHour+":"+currentEndMin+":"+currentEndSec);
   }
  
   //Sifiso Changes:2017/03/23:SR62915-Added parameter currentTimeStamp to record exact time session was started instead of time email was sent
   //private void  sendStartEmail(ActionForm form,HttpServletRequest request) throws Exception{	  						//Sifiso Changes:2017/03/23:SR62915:Removed
   private void  sendStartEmail(ActionForm form,HttpServletRequest request,String currentTimeStamp) throws Exception{	//Sifiso Changes:2017/03/23:SR62915:Added
                    //String onlyDate,onlyTime;									 	 //Sifiso Changes:2017/03/23:SR62915:Removed
                    String currentStartHour = currentTimeStamp.substring(11,13);  	 //Sifiso Changes:2017/03/23:SR62915:Added
  				  	String currentStartMin = currentTimeStamp.substring(14,16);	   	 //Sifiso Changes:2017/03/23:SR62915:Added
  				  	String currentStartSec = currentTimeStamp.substring(17,19);	  	 //Sifiso Changes:2017/03/23:SR62915:Added
  				  	String currentDate = currentTimeStamp.substring(0,10);		  	 //Sifiso Changes:2017/03/23:SR62915:Added
  				  	
                    TelecentreForm telecentreForm = (TelecentreForm) form;
                    //dateUtil dateutil=new dateUtil();							  	 //Sifiso Changes:2017/03/23:SR62915:Removed
                    String subject = "Telecentre visit started for Student "+telecentreForm.getUserId();
                    //String body= bodyOfStartingEmail(telecentreForm,dateutil.dateOnly(),dateutil.timeOnly());	     				//Sifiso Changes:2017/03/23:SR62915:Removed
                    String body= bodyOfStartingEmail(telecentreForm,currentDate,currentStartHour,currentStartMin,currentStartSec);	//Sifiso Changes:2017/03/23:SR62915:Added
                    int stuNum=Integer.parseInt(telecentreForm.getUserId());
                    sendEmail(stuNum,telecentreForm,request,subject,body);
                    
                    //Sifiso Changes:2017/03/15:SR62915:Added Log
                    log.info(this+": sendStartEmail(): Telecentre Start Visit Mail for Student '"+stuNum+"' sent at "+
                    		currentDate +" "+currentStartHour+":"+currentStartMin+":"+currentStartSec);
   }
   public ActionForward performCancel(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		    ActionMessages messages = new ActionMessages();
		    CodeProfiler profiler = new CodeProfiler();
		    TelecentreForm telecentreForm = (TelecentreForm) form;
		    TelecentreDAO dao = new TelecentreDAO();
		    HttpSession session = request.getSession(true);
		    sessionManager = (SessionManager) ComponentManager.get(SessionManager.class);
			Session currentSession = sessionManager.getCurrentSession();
			String userID = currentSession.getUserEid();
			telecentreForm.setUserId(userID);
			DateFormat dateFormat5 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date5 = new Date();
	        String dateTime5 = dateFormat5.format(date5);
	        telecentreForm.setToday(dateTime5);
			telecentreForm.setCode("-1");
			telecentreForm.setTelecentreCode("");
			telecentreForm.setStartTimeStamp(""); 
			session.setAttribute("telecentres",dao.getTelecentres());
			telecentreForm.setActiveSessions(0);
			
            log.info(this+": performCancel(): Student '"+userID+"' clicked 'Cancel' Telecentre session at '"+
            		dateTime5+"'");		//Sifiso Changes:2017/03/17:SR62915
            
			return mapping.findForward("startpage");
	}
    private void sendEmail(int stuNum,TelecentreForm telecentreForm,HttpServletRequest request,String subject,String body) throws Exception{
		                 CodeProfiler profiler = new CodeProfiler();
		                 TelecentreDAO dao = new TelecentreDAO();
		                 HttpSession session = request.getSession(true);
		                 sessionManager = (SessionManager) ComponentManager.get(SessionManager.class);
			             Session currentSession = sessionManager.getCurrentSession();
			             String userID = currentSession.getUserEid();
			             telecentreForm.setUserId(userID);
			             String teleId=dao.getTeleId(stuNum);
			             if(!teleId.equals("")){
			                  String emailAddress = dao.getEmail(teleId);
				              profiler.start(request);
		                      sendEmail(subject,body,emailAddress);
		                      profiler.stop(request, "unisa.join myunisajoinaction confirm sendemail");
		                      
		                      //Sifiso Changes:2017/03/15:SR62915:Log for email messages sent to Telecentre contacts
		                      log.info(this+": sendEmail(): \r\nToEmailAddress:'"+emailAddress+"'\r\nSubject:'"+subject+"'\r\nBody:'"+body+"'");
			             }
   }
    
  //Sifiso Changes:2017/03/23:SR62915-Added parameters currentDate, currentHour, currentMin & currentSec to record exact time session was ended instead of time email was sent
  // private  String getBodyOfClosingEmail(TelecentreForm telecentreForm,String onlyDate,String onlyTime ){ 	
  private  String getBodyOfClosingEmail(TelecentreForm telecentreForm,String currentDate,String currentHour,String currentMin,String currentSec){ 
                  
	  			  String timeOnly = ""+currentHour+":"+currentMin+":"+currentSec;		//Sifiso Changes:2017/03/23:SR62915:Added
		          String body = "<html> "+
					  "<body> "+
					  "UNISA has recorded the conclusion of a Telecentre visit at "+telecentreForm.getTelecentreName()+" for Student "+telecentreForm.getUserId()+"<br><br>"+
		              //"Date: "+onlyDate+"<br>"+				//Sifiso Changes:2017/03/23:SR62915:Removed
		              "Date: "+currentDate+"<br>"+				//Sifiso Changes:2017/03/23:SR62915:Added
		              //"Time endded: "+onlyTime+"<br><br>"+	//Sifiso Changes:2017/03/23:SR62915:Removed
		              "Time endded: "+timeOnly+"<br><br>"+	    //Sifiso Changes:2017/03/23:SR62915:Added
		              "Regards  <br>"+
		  			  "UNISA ICT"+
		              "</body>"+
		              "</html>";
		          return body;
   }
  
   //Sifiso Changes:2017/03/23:SR62915-Added parameters currentDate, currentHour, currentMin & currentSec to record exact time session was ended instead of time email was sent
   //private String bodyOfStartingEmail(TelecentreForm telecentreForm,String onlyDate,String onlyTime ){
   private String bodyOfStartingEmail(TelecentreForm telecentreForm,String currentDate,String currentHour,String currentMin,String currentSec ){
	   
	   			String timeOnly = ""+currentHour+":"+currentMin+":"+currentSec;		//Sifiso Changes:2017/03/23:SR62915:Added
	   			
	            String body = "<html> "+
                  "<body> "+
                  "UNISA has recorded the start of a Telecentre visit at "+telecentreForm.getTelecentreName()+" for Student "+telecentreForm.getUserId()+"<br><br>"+
                  "NB: This is an automated response - do not reply to this e-mail. <br> "+
                  //"Date: "+onlyDate+"<br>"+					//Sifiso Changes:2017/03/23:SR62915:Removed
                  "Date: "+currentDate+"<br>"+					//Sifiso Changes:2017/03/23:SR62915:Added
                  //"Time started: "+onlyTime+"<br><br>"+		//Sifiso Changes:2017/03/23:SR62915:Removed
                  "Time started: "+timeOnly+"<br><br>"+			//Sifiso Changes:2017/03/23:SR62915:Added
                  "Regards  <br>"+
                  "UNISA ICT"+
                  "</body>"+
                  "</html>";
	             return body;
   }
	public ActionForward myTelecentre(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		    ActionMessages messages = new ActionMessages();
		    TelecentreForm telecentreForm = (TelecentreForm) form;
		    HttpSession session = request.getSession(true);
		    sessionManager = (SessionManager) ComponentManager.get(SessionManager.class);
			Session currentSession = sessionManager.getCurrentSession();
			if(telecentreForm.getIsAdmin()==0){
			      String userID = currentSession.getUserEid();
			      telecentreForm.setUserId(userID);
			      telecentreForm.setAboutTele(request.getParameter("type"));
			      dateUtil dateutil=new dateUtil();
				  telecentreForm.setMonths(dateutil.monthsArr());
		          String month="";
		          if(!telecentreForm.isVisited()){
		        	   telecentreForm.setVisited(true);
		        	   telecentreForm.setMonth(dateutil.getCurrMonth());
		          }
		          month=telecentreForm.getMonth();
		          String monthNumStr=dateutil.monthToIntStr(month);
		          telecentreForm.setStudentNr(userID);
		          telecentreForm.setFromMonth(monthNumStr);
		          telecentreForm.setFromYear(""+dateutil.yearInt());
			      TelecentreVisits tlcv=new TelecentreVisits();
			      tlcv.setDisplayDataForStu(telecentreForm);
			      if(request.getParameter("type")==null){
			    	  telecentreForm.setAboutTele("M");
			    	  return mapping.findForward("telelist"); 
			      }
			      if(request.getParameter("type")=="M"||request.getParameter("type").equals("M")||request.getParameter("type")=="K"||request.getParameter("type").equals("K")){
			            	return mapping.findForward("telelist");
			      }else{
			                return mapping.findForward("empty");
			      }
			}else{
				   return displayTel(mapping,form,request,response);
			}
	}
	/*
	 * get the telecentre visits.
	 */
	
	public ActionForward displayTelecenters(// displayed after  'Display' button is clicked
			ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		    ActionMessages messages = new ActionMessages();
		    TelecentreForm telecentreForm = (TelecentreForm) form;
		    HttpSession session = request.getSession(true);
	        telecentreForm.setTelecentreInfo((new ArrayList()));
	        telecentreForm.setExtConfirmPageReached(0);
	        telecentreForm.setShowTelecentreListTracker(1);
	        telecentreForm.setDisplayPageReached(0);
	        TelecentreVisits tlcv=new TelecentreVisits(); 
	        if(telecentreForm.getVisitPeriod().indexOf("Choose")!=-1){
    	          return mapping.findForward("displayvisitdetails");
            }
	        tlcv.setDisplayData(telecentreForm);
	       	session.setAttribute("telecentreInfo",telecentreForm.getTelecentreInfo());
	        telecentreForm.setCustomvisittracker(0);
	        if((telecentreForm.getTelecentreInfo()==null)||(telecentreForm.getTelecentreInfo().isEmpty())){
	                  messages.add(ActionMessages.GLOBAL_MESSAGE,
		                           new ActionMessage("telecetre.novisit"));
              }//if
	         addErrors(request, messages); 
	         return mapping.findForward("displayvisitdetails");
    }
	private ActionForward displayTel(// displayed after  'Display' button is clicked
			ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		    ActionMessages messages = new ActionMessages();
		    TelecentreForm telecentreForm = (TelecentreForm) form;
		    HttpSession session = request.getSession(true);
	        telecentreForm.setTelecentreInfo((new ArrayList()));
	        telecentreForm.setExtConfirmPageReached(0);
	        telecentreForm.setShowTelecentreListTracker(1);
	        telecentreForm.setDisplayPageReached(0);
	        telecentreForm.setExportPageReached(0);
	        TelecentreVisits tlcv=new TelecentreVisits(); 
	        if(telecentreForm.getVisitPeriod().indexOf("Choose")!=-1){
	        	
	        	 messages.add(ActionMessages.GLOBAL_MESSAGE,
                         new ActionMessage("telecetre.choosePeriod "));
	        	 addErrors(request, messages);
    	          return mapping.findForward("displayvisitdetails");
            }
	       	
	        tlcv.setDisplayData(telecentreForm);
	       	session.setAttribute("telecentreInfo",telecentreForm.getTelecentreInfo());
	        telecentreForm.setCustomvisittracker(0);
	        if((telecentreForm.getTelecentreInfo()==null)||(telecentreForm.getTelecentreInfo().isEmpty())){
	                  messages.add(ActionMessages.GLOBAL_MESSAGE,
		                           new ActionMessage("telecetre.choosePeriod"));
              }//if
	         addErrors(request, messages); 
	         return mapping.findForward("displayvisitdetails");
    }
		/*
	 * back
	 */
	public ActionForward back(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		    ActionMessages messages = new ActionMessages();
		    TelecentreForm telecentreForm = (TelecentreForm) form;
		    TelecentreDAO dao = new TelecentreDAO();
		    HttpSession session = request.getSession(true);
		    sessionManager = (SessionManager) ComponentManager.get(SessionManager.class);
			Session currentSession = sessionManager.getCurrentSession();
			String userID = currentSession.getUserEid();
			telecentreForm.setUserId(userID);
		    DateFormat dateFormat4 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		    Date date4 = new Date();
		    String dateTime4 = dateFormat4.format(date4);
			telecentreForm.setToday(dateTime4);
			telecentreForm.setVisitPeriod("Choose Custom Period");
			telecentreForm.setTelecentreName("All Centres");
			telecentreForm.setTelecentreInfo((new ArrayList()));
			if(telecentreForm.getIsAdmin()==1){
				 if(telecentreForm.extendPageReached()==1){
					 if((telecentreForm.getExtConfirmPageReached()==1)||(telecentreForm.auditPageReached()==1)){
						    extendHoursScreen(telecentreForm,session);
						    return mapping.findForward("extendhours");
					 }
				 }
				 if(telecentreForm.exportPageReached()==1){
					 if(telecentreForm.getCustomvisittracker()==1){
				            setExportScreen(telecentreForm,request,form);
				            telecentreForm.setCustomvisittracker(0);
		                    return mapping.findForward("exportvisitdetails");
			        }
				 }
				 //Sifiso Changes:Added:2016/08/01:Return to 'Create Profile' page if it was reached already
				 if(telecentreForm.getTelCreatePageReached()==1){
					telecentreForm.setTelCreatePageReached(0);
					return mapping.findForward("createCentres");
				 }
				 //Sifiso Changes:Added:2016/08/04:Return to 'Update Profile' page if it was reached already
				 if(telecentreForm.getTelUpdatePageReached()==1){
					 telecentreForm.setTelUpdatePageReached(0);
					 telecentreForm.setTelUpdateListTracker(0);			//reset the selection chosen on drop-down list to return to drop-down options
					 telecentreForm.setTelecentreCurrentName("Select a Telecentre to update");
					 telecentreForm.setTelecentreName("Select a Telecentre to update");
					 telecentreForm.setTelUpdateNameCheckBox(false);	//reset checkboxes
					 telecentreForm.setTelUpdateEmailCheckBox(false);
					 telecentreForm.setTelUpdateProvinceCheckBox(false);
					 telecentreForm.setTelUpdateActiveCheckBox(false);
					 return mapping.findForward("updateCentres");
				 }else{
					 if(telecentreForm.getTelUpdatePageReached()==2){	
						 telecentreForm.setTelUpdatePageReached(0);
						 telecentreForm.setTelUpdateListTracker(0);		 	//reset the selection chosen on drop-down list to return to drop-down options
						 telecentreForm.setTelecentreCurrentName("Select a Telecentre to update");
						 telecentreForm.setTelecentreName("Select a Telecentre to update");
						 telecentreForm.setTelUpdateNameCheckBox(false); 	//reset checkboxes
						 telecentreForm.setTelUpdateEmailCheckBox(false);
						 telecentreForm.setTelUpdateProvinceCheckBox(false);
						 telecentreForm.setTelUpdateActiveCheckBox(false);
						 return mapping.findForward("updateCentres");
					 }else{
						 if(telecentreForm.getTelUpdatePageReached()==3){
							 populateTelecentreInfo(telecentreForm,dao,telecentreForm.getTelecentreCurrentName()); //get all other current detials
							 telecentreForm.setTelUpdatePageReached(2);
							 telecentreForm.setTelUpdateListTracker(1);		//return to telecentre edit options page
							 return mapping.findForward("updateCentres");
						 }
					 }
				 }
				 //Sifiso Changes:Added:2016/08/14:Return to 'Remove Telecentre' home page if it was reached already
				 if(telecentreForm.getTelRemovePageReached()==1){
					 telecentreForm.setTelRemovePageReached(0);
					 telecentreForm.setTelRemoveListTracker(0);
					 return mapping.findForward("removeCentres");
				 }else{
					 if(telecentreForm.getTelRemovePageReached()==2){	//return to remove telcentre edit page
						 telecentreForm.setTelRemovePageReached(1);
						 telecentreForm.setTelRemoveListTracker(1);
						 populateTelecentreInfo(telecentreForm,dao,telecentreForm.getTelecentreRemoveName());  //re-populate telecentreInfo to re-display details
						 return mapping.findForward("removeCentres");
					 }
				 }
				 if(telecentreForm.getTelActivatePageReached()==1){
					 telecentreForm.setTelActivatePageReached(0);					 
					 telecentreForm.setTelActivateListTracker(0);
					 return mapping.findForward("activateCentres");
				 }else{
					 if(telecentreForm.getTelActivatePageReached()==2){
						 telecentreForm.setTelActivatePageReached(1);
						 telecentreForm.setTelActivateListTracker(1);
						 populateTelecentreInfo(telecentreForm,dao,telecentreForm.getTelecentreActivateName());  //re-populate telecentreInfo to re-display details
						 return mapping.findForward("activateCentres");
					 }else{
						 if(telecentreForm.getTelActivatePageReached()==3){
							 telecentreForm.setTelActivatePageReached(1);
							 telecentreForm.setTelActivateListTracker(1);
							 populateTelecentreInfo(telecentreForm,dao,telecentreForm.getTelecentreActivateName());  //re-populate telecentreInfo to re-display details
							 return mapping.findForward("activateCentres");
						 }
					 }
				 }
				 
				 //Sifiso Changes:Added:2016/08/14:Return to 'Manage Admins' home page if it was reached already
				 if(telecentreForm.getTelManageAdminsReached()==1){
					 if(telecentreForm.getTelAddAdminsReached()==1){
						telecentreForm.setTelAddAdminsReached(0);
						return mapping.findForward("manageAdmins");
					 }else if(telecentreForm.getTelViewAdminsReached()==1){
							telecentreForm.setTelViewAdminsReached(0);
							return mapping.findForward("manageAdmins");
					 }else if(telecentreForm.getTeleAdminRemoveName().equals("Select an Admin to Remove")){
						 telecentreForm.setTelRemoveAdminsReached(0);
						 return mapping.findForward("removeAdmins");
					 }else if(telecentreForm.getTelRemoveAdminsReached()==2){
						 telecentreForm.setTeleAdminRemoveName("-1");	//Drop-Down value for "Select an Admin to Remove" option: logic for jsp
						 telecentreForm.setTelRemoveAdminsReached(1);
						 return mapping.findForward("removeAdmins");
					 }else if(telecentreForm.getTelRemoveAdminsReached()==1){
						 telecentreForm.setTelRemoveAdminsReached(0);
						 telecentreForm.setAdminRemoveListTracker(0);
						 return mapping.findForward("manageAdmins");
					 }
				 }
				 
				 displayVisitsScreen(session,telecentreForm,dao);
                 return mapping.findForward("displayvisitdetails");
		     }
	   	   return mapping.findForward("startpage");
	}
	public ActionForward secondBack(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		    ActionMessages messages = new ActionMessages();
		    TelecentreForm telecentreForm = (TelecentreForm) form;
		    TelecentreDAO dao = new TelecentreDAO();
		    HttpSession session = request.getSession(true);
		    sessionManager = (SessionManager) ComponentManager.get(SessionManager.class);
			Session currentSession = sessionManager.getCurrentSession();
			String userID = currentSession.getUserEid();
			telecentreForm.setUserId(userID);
			  String teleCode = telecentreForm.getCode();
			   DateFormat dateFormat4 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		        Date date4 = new Date();
		        String dateTime4 = dateFormat4.format(date4);
			telecentreForm.setToday(dateTime4);
			
			  if(telecentreForm.isCheck()==false){
				 
				   telecentreForm.setTelecentreName(dao.getCentreName(teleCode));
			  }else{
				  telecentreForm.setTelecentreName(dao.getcentreName(telecentreForm.getUserId(), telecentreForm.getLatestStartTime()));
			  }
			 return mapping.findForward("endpage");
	}
	
	
	/*
	 * Send the email 
	 */
	
	public void sendEmail(String subject, String body, String emailAddress) throws AddressException {

		String tmpEmailFrom = ServerConfigurationService.getString("noReplyEmailFrom");

		InternetAddress toEmail = new InternetAddress(emailAddress);
		InternetAddress iaTo[] = new InternetAddress[1];
		iaTo[0] = toEmail;
		InternetAddress iaHeaderTo[] = new InternetAddress[1];
		iaHeaderTo[0] = toEmail;
		InternetAddress iaReplyTo[] = new InternetAddress[1];
		iaReplyTo[0] = new InternetAddress(tmpEmailFrom);
		List contentList = new ArrayList();
		contentList.add("Content-Type: text/html");
		//contentList.add("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		EmailService emailService=(EmailService)ComponentManager.get(EmailService.class);
		emailService.sendMail(iaReplyTo[0],iaTo,subject,body,iaHeaderTo,iaReplyTo,contentList);
		log.info("sending mail from "+iaReplyTo[0]+" to "+toEmail.toString());
	} 
	public boolean activeStudent(String StudentNumber) throws Exception{
	
		boolean studentActive=false;
	    String courses = "";
	    Srcds01sMntStudContactDetail op = new Srcds01sMntStudContactDetail();
	    operListener opl = new operListener();
	    op.addExceptionListener(opl);
		op.clear();

	    Saaal40jGetStudentCourses op2 = new Saaal40jGetStudentCourses();
	    operListener op3 = new operListener();
	    op2.addExceptionListener(op3);
	    op2.clear();
	    op2.setAsStringInWsStudentAnnualRecordMkStudentNr(StudentNumber);
	    op2.execute();
	    String errMessage = op2.getExitStateMsg();
	    if (errMessage.equals("") || errMessage == null){
	    	errMessage = "Proxy returned an error but the error message is null";
	    }
	    if (opl.getException() != null) throw opl.getException();
	    try{
	    	if (op2.getExitStateType() < 3) throw new Exception(errMessage);
	    	}
	    catch (Exception e){
	    	if (op2.getExitStateMsg().equals(""))
	    		log.info("DisplayResults: Exit state message is empty.");
	    	else
	    		log.info("DisplayResults: " + op2.getExitStateMsg());
	       	}
	    if (op2.getOutStusunCount()>0) {
	    	studentActive=true;
	    } else {
	    	studentActive=false;
	    }
	    studentActive=true;	
	    return studentActive;
	}
	public ActionForward unspecified(ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response){

			log.info("DisplayResults: unspecified method call -no value for parameter in request");

			return mapping.findForward("startpage");
	}
	// --------------------------------------------------------- Methods
	private class operListener implements java.awt.event.ActionListener {
		private Exception exception = null;

		operListener() {
			exception = null;
		}

		public Exception getException() {
			return exception;
		}

		public void actionPerformed(java.awt.event.ActionEvent aEvent) {
			exception = new Exception(aEvent.getActionCommand());
		}
	}
		public ActionForward download(//writes a spreadsheet to file 
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		    ActionMessages messages = new ActionMessages();
		    TelecentreForm telecentreForm = (TelecentreForm) form;
		    TelecentreDAO dao = new TelecentreDAO();
		    dateUtil dateutil=new dateUtil();
		    HttpSession session = request.getSession(true);
		    if(telecentreForm.downloadExtendedHrs()==0){
		          if(telecentreForm.getVisitPeriod().indexOf("Choose")!=-1){
           	          return mapping.findForward("exportvisitdetails");
                  }
		    }
		    excelReport excelreport=new excelReport();
		    List telecentres;
		    if(telecentreForm.downloadExtendedHrs()==0){
		            telecentres=excelreport.generateExcelWorkBookData(telecentreForm);
		    }else{
		    	 telecentres=excelreport.getExtendeHoursData(telecentreForm);
		    }
		    if((telecentres==null)||(telecentres.isEmpty())){
		    	   if(telecentreForm.downloadExtendedHrs()==0){
		    	        messages.add(ActionMessages.GLOBAL_MESSAGE,
					    new ActionMessage("telecetre.novisit"));
		    	   }else{
		    		   messages.add(ActionMessages.GLOBAL_MESSAGE,
		    				   new ActionMessage("telecetre.noextendedhours"));
		    	   }
		    	    addErrors(request, messages);
		    }else{
		    	    telecentreForm.setVisitPeriod("Choose Period");
		         	String filename =  "hrsExtension.xls";     
		         	if(telecentreForm.downloadExtendedHrs()==0){
		         		filename =  "visits.xls";
		         	}
				    String path = getServlet().getServletContext().getInitParameter("mypath");
				    File file2 = new File(path + filename);
				    file2.delete();
				    if(telecentreForm.downloadExtendedHrs()==0){
				    	excelreport.saveWorkBook(path,filename,telecentres);	
				    }else{
				    	excelreport.saveHoursExtensionRecords(path,filename,telecentres);	
				    }
					File file = new File(path + filename);
					DataInputStream in = new DataInputStream(new FileInputStream(file));
					ServletOutputStream out = response.getOutputStream();
					response.setDateHeader("Expires", 0);
					response.setHeader( "Pragma", "public" );
					response.setContentType("application/ms-excel");
					response.setContentLength((int)file.length());
					response.addHeader("Content-Disposition", "attachment;filename=" + filename );
					excelreport.saveToClient(in, out);
			}
		    // Sifiso Changes:2018/18/30:Remove redirect to prevent JavaIllegalStateException since
		    // redirect is not even required and user will remain in correct page, method just downloads file
		    /*if(telecentreForm.downloadExtendedHrs()==0){
		         telecentreForm.setCustomvisittracker(2);
		         return mapping.findForward("exportvisitdetails");
		    }else{
		    	return mapping.findForward("extendedhoursdownload");
		    }*/
		    
		    // Sifiso Changes:2018/18/30:Keep above tracker setting but dont redirect
		    if(telecentreForm.downloadExtendedHrs()==0)
		         telecentreForm.setCustomvisittracker(2);
		         
		    return null;	// Sifiso Changes:2018/18/30: We are no longer redirecting so return null
		  
    }
    public ActionForward handleDisplayListBoxAction(// to handle the onchange event of the period listbox in the displayVisitDetails.jsp
    		        ActionMapping mapping,
	                ActionForm form,
	                HttpServletRequest request,
	                HttpServletResponse response) throws Exception{
                    ActionMessages messages = new ActionMessages();
                    TelecentreForm telecentreForm = (TelecentreForm) form;
                    if(telecentreForm.getVisitPeriod().indexOf("Choose")!=-1){
                    	return mapping.findForward("displayvisitdetails");
                    }
                    if(telecentreForm.getVisitPeriod().equals("Custom")){
                    	  telecentreForm.setVisitPeriod("");
                    	  telecentreForm.setCustomvisittracker(1);
                    }
                    if(telecentreForm.getVisitPeriod().equals("-1")){
                    	  telecentreForm.setShowTelecentreListTracker(0);
                    }
                    List telecentres=new ArrayList();
                    telecentreForm.setDisplayPageReached(0);;
                    telecentreForm.setTelecentreInfo(telecentres);
                    HttpSession session = request.getSession(true);
                    dateUtil du=new dateUtil();
                    session.setAttribute("telecentreInfo",telecentreForm.getTelecentreInfo());
                    session.setAttribute("monthList",du.generateMonthsList());
                    session.setAttribute("daysList",du.generateDayList());
                    session.setAttribute("yearsList",du.getYearsList());
                    session.setAttribute("toMonthsList",du.generateMonthsList());
                    session.setAttribute("endDaysList",du.generateDayList());
                    session.setAttribute("toYearsList",du.getYearsList());
                    return mapping.findForward("displayvisitdetails");
     }
     public ActionForward  handleExportListBoxAction(//to handle the onchange event of the period listbox in the exportVisitDetails.jsp
	        ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception{
            ActionMessages messages = new ActionMessages();
            TelecentreForm telecentreForm = (TelecentreForm) form;
            dateUtil du=new dateUtil();
            if(telecentreForm.getVisitPeriod().trim().indexOf("Choose")!=-1){
            	 telecentreForm.setCustomvisittracker(2);
            	 return mapping.findForward("exportvisitdetails");
             }
            if(telecentreForm.getVisitPeriod().trim().equals("Custom")){
            	  refreshIntervalVars(telecentreForm,du);
            	  telecentreForm.setCustomvisittracker(1);//indicates that the custom date option was selected
            }else{
            	   telecentreForm.setCustomvisittracker(0);//indicates that either 'All' or 'last 7 days' or 'Last 30 days'  option was selected
             }
            return mapping.findForward("exportvisitdetails");
     }
      public ActionForward extendhrs(
 			ActionMapping mapping,
 			ActionForm form,
 			HttpServletRequest request,
 			HttpServletResponse response) throws Exception{
 		    HttpSession session = request.getSession(true);
 		    TelecentreForm telecentreForm = (TelecentreForm)form;
 		    extendHoursScreen(telecentreForm,session);
 		     return mapping.findForward("extendhours");
 	}
     private void extendHoursScreen(TelecentreForm telecentreForm,HttpSession session){
    	     DateFormat dateFormat = new SimpleDateFormat("yyyy");
    	     Calendar cal=Calendar.getInstance();
	         String yearStr=dateFormat.format(cal.getTime());
	         int  yearInt=Integer.parseInt(yearStr);
		     yearStr=""+yearInt;
		     dateUtil dateutil=new dateUtil();
		     ArrayList <LabelValueBean> arrList=new ArrayList <LabelValueBean>();
		     arrList.add(new org.apache.struts.util.LabelValueBean(yearStr,yearStr));
		     telecentreForm.setYearsList(arrList);
		     telecentreForm.setMonthList(dateutil.generateMonthList2());
		     telecentreForm.setNewHourLimit(0);
		     telecentreForm.setPreviousHourLimit(0);
		     telecentreForm.setExtensionHours(0);
		     telecentreForm.setExtensionHoursForCentre(0);
		     session.setAttribute("monthList", telecentreForm.getMonthList());
		     session.setAttribute("yearsList",telecentreForm.getYearsList());
		     session.setAttribute("telecentres", telecentreForm.getTelecenters());
		     telecentreForm.setExtConfirmPageReached(0);
		     telecentreForm.setExportPageReached(0);
		     telecentreForm.setDisplayPageReached(0);
		     telecentreForm.setExtendPageReached(1);
		     telecentreForm.setAuditPageReached(0);
	 }
     public ActionForward exportVisits(
 			      ActionMapping mapping,
 			      ActionForm form,
 			      HttpServletRequest request,
 			      HttpServletResponse response) throws Exception{
    		      TelecentreForm telecentreForm = (TelecentreForm) form;
    		      setExportScreen(telecentreForm,request,form);
 		          return mapping.findForward("exportvisitdetails");
 	}
     private void setExportScreen(TelecentreForm telecentreForm,HttpServletRequest request,
    		        ActionForm form){
    	            setSessionAttributes(request,form);
    	            dateUtil du=new dateUtil();
		            refreshIntervalVars(telecentreForm,du);
		            telecentreForm.setTrackerForExport(0);
		            telecentreForm.setDisplayPageReached(0);
		            telecentreForm.setExportPageReached(1);
            		telecentreForm.setExtendPageReached(0);
     }
    private void refreshIntervalVars(TelecentreForm telecentreForm,dateUtil du){
    	    telecentreForm.setFromDay("01");
		    telecentreForm.setEndDay(""+du.getEndDayForMonth(du.getMonthInt(),du.yearInt()));
		    telecentreForm.setFromMonth(du.getMonthIntStr());
		    telecentreForm.setToMonth(du.getMonthIntStr());
		    telecentreForm.setFromYear(du.getYearIntStr());
		    telecentreForm.setToYear(du.getYearIntStr());
	}
 	public ActionForward displayVisitDetails(
			ActionMapping mapping,
			TelecentreForm telecentreForm,
			String userID,HttpSession session
			)throws Exception{
            String date="";
            TelecentreDAO dao=new TelecentreDAO();
            displayVisitsScreen(session,telecentreForm,dao);
          return mapping.findForward("displayvisitdetails");
    }
    public boolean updateTelecentreHours(TelecentreForm telecentreForm,
                               HttpServletRequest request,TelecentreDAO dao)throws Exception{
                               ActionMessages messages = new ActionMessages();
                               dateUtil dateutil=new dateUtil();
                               String username=telecentreForm.getUserId();
                               String month=telecentreForm.getMonthForTelecenter();
                               dao.updateCentreHours(telecentreForm.getTelecentreCode(),dateutil.monthToInt(month),
                               telecentreForm.getNewHourLimit(),Integer.parseInt(telecentreForm.getYear()));
                               dao.allocateStudentTelecentreHrs(username,(new Date()),"Extend",telecentreForm.getTelecentreCode(),""
                               ,telecentreForm.getExtensionHoursForCentre());
                               messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("stu.ext.completion"));
                               addErrors(request, messages); 
                               return true;
   }
   private  void updateStudentHours(HttpServletRequest request,TelecentreForm telecentreForm,dateUtil dateutil){
	                     ActionMessages messages = new ActionMessages();
	                     TelecentreDAO dao=new TelecentreDAO();
	                     String month=telecentreForm.getMonth();
	                     dao.updateStudentHours(telecentreForm.getStudentNr(),dateutil.monthToInt(month),
      		             telecentreForm.getNewHourLimit(),Integer.parseInt(telecentreForm.getYear()));
		                 String username=telecentreForm.getUserId();
                         dao.allocateStudentTelecentreHrs(username,(new Date()),"Extend","",
      		             telecentreForm.getStudentNr(),telecentreForm.getExtensionHours());
                         telecentreForm.setExtensionHours(0);
                         messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("stu.ext.completion"));
                         addErrors(request, messages); 
   }
  public ActionForward submitHours(ActionMapping mapping,
                         ActionForm form,
                         HttpServletRequest request,
                         HttpServletResponse response)throws Exception{
                         TelecentreForm telecentreForm = (TelecentreForm) form;
                         TelecentreDAO dao = new TelecentreDAO();
                         ActionMessages messages = new ActionMessages();
                         sessionManager = (SessionManager) ComponentManager.get(SessionManager.class);
                         Session currentSession = sessionManager.getCurrentSession();
                         Date date3 = new Date();
                         dateUtil dateutil=new dateUtil();
                         DateFormat dateFormat4 = new SimpleDateFormat("dd-MM-yyyy");
                         String dateOnly=dateFormat4.format(date3);
                           	telecentreForm.setHoursSubmitted(1);
             	        	if((telecentreForm.getExtStu()==1)){
             	        	         updateStudentHours(request,telecentreForm,dateutil);
             	        	          return mapping.findForward("extensionconfirmation");
                  	        }else{
             	        		      	updateTelecentreHours(telecentreForm,request,dao);
             	        			    return mapping.findForward("extensionconfirmation");
             	            }
    }
  	
  	//Sifiso Changes:Added:2016/07/26:Create Profile page action mapping method for toolbar link
  	public ActionForward createCentresLinkBtn(
  			ActionMapping mapping,
		    ActionForm form,
		    HttpServletRequest request,
		    HttpServletResponse response) throws Exception{
		ActionMessages messages = new ActionMessages();
		TelecentreForm telecentreForm = (TelecentreForm) form;
		HttpSession session = request.getSession(true);
		telecentreForm.setProvinceList(generateProvinceList());					//Sifiso Changes:Added:2016/07/27:Generate a list of provinces for a drop-down menu
        session.setAttribute("provinceList",telecentreForm.getProvinceList());	//Sifiso Changed:Added:2016/07/28:List must be available throughout the session
        telecentreForm.setActiveStatusList(generateCentreActive());
        session.setAttribute("activeStatusList",telecentreForm.getActiveStatusList());
        telecentreForm.setTelCreatePageReached(0);		//Indicate that the 'Create Profile' page is reached(1) or not(0), set to 0 to enable going back to home page
        return mapping.findForward("createCentres");
  	}
  	
  	String dbTelecode=generateTeleCode();	//Sifiso Changes:Added:2016/08/02:Call method to generate telecente code for display on confirm page and addition to DB	
  	//Sifiso Changes:Added:2016/07/29:Create Profile page action mapping method for Create button
  	public ActionForward createSubmit(
  			ActionMapping mapping,
  			ActionForm form,
  			HttpServletRequest request,
  			HttpServletResponse response) throws Exception{
		ActionMessages messages=new ActionMessages();
		TelecentreForm telecentreForm = (TelecentreForm) form;
		telecentreForm.setTelecode(dbTelecode);
		telecentreForm.setTelCreatePageReached(1);				//Set to 1 to enable going back to create profile page
		boolean nameValid=true,provinceValid=true,emailValid=true,emailFormatValidFlag=false;
		boolean nameExists=false;	//Sifiso Changes:2016/08/26-User agreed that it is possible for a Telecentre to have same name, email and province 
		
		if (emailFormatValid(telecentreForm.getTelecentreEmail())){
			emailFormatValidFlag=true;							//Determine format of entered Telecentre email (someone@domain.domainExt)
		}	
		if(telecentreForm.getTelecentreName().equals("")){		//Telecentre Name has not been entered
			nameValid=false;
			messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("errors.message.create.name"));
		}
		if(telecentreForm.getProvince().equals("-1")){			//Telecentre Province has not been entered
			provinceValid=false;
			messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("errors.message.create.province"));
		}
		if(telecentreForm.getTelecentreEmail().equals("")){		//Telecentre Email has not been entered
			emailValid=false;
			messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("errors.message.create.email"));
		}
		if(emailFormatValidFlag==false){						//Format of Telecentre email is invalid
			messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("errors.message.create.emailInvalid"));
		}
		//Sifiso Changes:Commented out:2016/08/26-User agreed that it is possible for a Telecentre to have same name, email and province
		/*if(teleNameExists(telecentreForm.getTelecentreName(),telecentreForm.getProvince(),
						  telecentreForm.getTelecentreEmail())){
			nameExists=true;									//Telecentre name already exists in the same province and the same contact email
			messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("errors.message.create.name.exists"));
		}*/	
		addErrors(request, messages);											//Add all errors added by 'messages.add' for display
		if(nameValid && emailValid && emailFormatValidFlag && provinceValid &&
		   nameExists==false){													//All telecentre details have been entered correctly
			return mapping.findForward("createConfirmation");					//Forward to create profile confirmation page
		}else{
			return mapping.findForward("createCentres");						//Remain in 'Create Profile' page and display errors
		}
  	}
  	
  	//Sifiso Changes:Added:2016/08/01:Method to add a Telecentre to the database
  	public ActionForward addDbProfile(
  			ActionMapping mapping,
  			ActionForm form,
  			HttpServletRequest request,
  			HttpServletResponse response) throws Exception{
  		ActionMessages messages=new ActionMessages();
  		TelecentreForm telecentreForm = (TelecentreForm)form;
  		telecentreForm.setTelCreatePageReached(1);						//set to 1 to enable going back to create profile page
  		TelecentreDAO dao = new TelecentreDAO();
  		//if insertion to database is successfull, 'result' must be >0
  		int result=dao.addCentreProfile(telecentreForm.getProvince(),telecentreForm.getTelecentreName(),
  						 telecentreForm.getTelecentreEmail(),centreMonthlyHrs,telecentreForm.getTelecentreStatus(),
  						 dbTelecode);
  		if(result<0){
  			messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("errors.message.create.fail"));		//failure
  		}else{
  			if(result>0){
  				messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("profile.create.completion.message"));	//success
  			}
  		}
  		addErrors(request, messages);				//add the message of success for display
  		return mapping.findForward("createCentres");
  	}
  	
  	//Sifiso Changes:Added:2016/07/26:Update Profile page action mapping method for toolbar link
  	public ActionForward updateCentresLinkBtn(
  			ActionMapping mapping,
		    ActionForm form,
		    HttpServletRequest request,
		    HttpServletResponse response) throws Exception{
		TelecentreForm telecentreForm = (TelecentreForm) form;
		TelecentreDAO dao=new TelecentreDAO();
		HttpSession session=request.getSession(true);
		telecentreForm.setTelecenters(dao.getAllCentres());		//set all centres for drop-down for user selection
		session.setAttribute("centreNames", telecentreForm.getTelecenters());
		telecentreForm.setTelecentreCurrentName("Select a Telecentre to update");
		telecentreForm.setTelecentreName("Select a Telecentre to update");
		telecentreForm.setProvinceList(generateProvinceList());					//Sifiso Changes:Added:2016/08/05:Generate a list of provinces for a drop-down menu
        session.setAttribute("provinceList",telecentreForm.getProvinceList());	//Sifiso Changed:Added:2016/08/05:List must be available throughout the session
        telecentreForm.setActiveStatusList(generateCentreActive());
        session.setAttribute("activeStatusList",telecentreForm.getActiveStatusList());
		telecentreForm.setTelUpdatePageReached(0);				//Indicate that the 'Update Profile' page is reached (1) or not(0)
		telecentreForm.setTelUpdateListTracker(0);				//Indicate that the drop-down on this page is not selected yet
		telecentreForm.setTelUpdateNameCheckBox(false);
		telecentreForm.setTelUpdateEmailCheckBox(false);
		telecentreForm.setTelUpdateProvinceCheckBox(false);
		telecentreForm.setTelUpdateActiveCheckBox(false);
		return mapping.findForward("updateCentres");
  	}
  	
  	//Sifiso Changes:Added:2016/08/05:Handles the onchange event of the drop-down list for Telecentre selection in the 'Update Profile'
  	public ActionForward handleUpdateListBoxAction(
  			ActionMapping mapping,
  			ActionForm form,
  			HttpServletRequest request,
  			HttpServletResponse response) throws Exception{
  		TelecentreForm telecentreForm = (TelecentreForm) form;
  		TelecentreDAO dao=new TelecentreDAO();
  		
  		if(telecentreForm.getTelecentreCurrentName().trim().indexOf("Select")==-1){	//no occurance of 'Select'==>a centre WAS selected from drop-down
	  		telecentreForm.setTelUpdateListTracker(1);
	  		telecentreForm.setTelUpdatePageReached(2);
	  		if(telecentreForm.getTelUpdateListTracker()==1){
	  			populateTelecentreInfo(telecentreForm,dao,telecentreForm.getTelecentreCurrentName());
	  		}
	  		return mapping.findForward("updateCentres");
  		}else{
	  			telecentreForm.setTelUpdateListTracker(0);				//a centre WAS NOT selected
	  			telecentreForm.setTelUpdateNameCheckBox(false);
	  			telecentreForm.setTelUpdateEmailCheckBox(false);
	  			telecentreForm.setTelUpdateProvinceCheckBox(false);
	  			telecentreForm.setTelUpdateActiveCheckBox(false);
  		}
  		return mapping.findForward("updateCentres");
  	}  	
  	
	//Sifiso Changes:Added:2016/08/10:Update Profile page action mapping method for Update button
  	public ActionForward updateSubmit(
  			ActionMapping mapping,
  			ActionForm form,
  			HttpServletRequest request,
  			HttpServletResponse response) throws Exception{
		ActionMessages messages=new ActionMessages();
		TelecentreForm telecentreForm = (TelecentreForm) form;
		TelecentreDAO dao = new TelecentreDAO();
		telecentreForm.setTelecode(dbTelecode);
		telecentreForm.setTelUpdatePageReached(3);					//Set to 3 to enable going back to edit update profile page
		boolean emailFormatValidFlag=false,detailsError=false;
		
		if (checkboxValue(telecentreForm.getTelUpdateEmailCheckBox()) && 
				emailFormatValid(telecentreForm.getTelecentreEmail())){
			emailFormatValidFlag=true;								//Determine format of entered Telecentre email (someone@domain.domainExt)
		}	
		if(checkboxValue(telecentreForm.getTelUpdateNameCheckBox()) && 
				(telecentreForm.getTelecentreName().equals(""))){	//Telecentre Name has not been entered
			detailsError=true;
			messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("errors.message.create.name"));
		}
		if(checkboxValue(telecentreForm.getTelUpdateNameCheckBox()) && 
				   telecentreForm.getTelecentreName().equals(telecentreForm.getTelecentreCurrentName())){
					detailsError=true;								//Telecentre name is the same as current
					messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("errors.message.update.name.exists"));
		}
		//Sifiso Changes:Commented out:2016/08/26:User agreed that it is possible for the same name,email and province to be used 
		/*if(checkboxValue(telecentreForm.getTelUpdateNameCheckBox()) && 
				teleNameExists(telecentreForm.getTelecentreName())){
					detailsError=true;								//Telecentre name already exists in database
					messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("errors.message.update.name.exists.nameOnly"));
		}*/
		if(checkboxValue(telecentreForm.getTelUpdateProvinceCheckBox()) &&
				telecentreForm.getProvince().equals("-1")){			//Telecentre Province has not been entered
			detailsError=true;
			messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("errors.message.create.province"));
		}
		if(checkboxValue(telecentreForm.getTelUpdateProvinceCheckBox()) &&
				telecentreForm.getProvince().equals(telecentreForm.getTelecentreCurrentProvince())){	
			detailsError=true;										//Telecentre Province is the same as current
			messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("errors.message.update.province"));
		}
		if(checkboxValue(telecentreForm.getTelUpdateEmailCheckBox()) && 
				telecentreForm.getTelecentreEmail().equals("")){	//Telecentre Email has not been entered
			detailsError=true;
			messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("errors.message.create.email"));
		}
		if(checkboxValue(telecentreForm.getTelUpdateEmailCheckBox()) && 
				 telecentreForm.getTelecentreEmail().equals(telecentreForm.getTelecentreCurrentEmail())){		
			detailsError=true;										//Telecentre Email is the same as current
			messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("errors.message.update.email"));
		}
		if(checkboxValue(telecentreForm.getTelUpdateEmailCheckBox()) && 
				emailFormatValidFlag==false){						//Format of Telecentre email is invalid
			detailsError=true;
			messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("errors.message.create.emailInvalid"));
		}
		if(checkboxValue(telecentreForm.getTelUpdateActiveCheckBox()) && 
				 telecentreForm.getTelecentreStatus().equals(telecentreForm.getTelecentreCurrentStatus())){		
			detailsError=true;										//Telecentre Status is the same as current	
			messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("errors.message.update.status"));
		}
		addErrors(request, messages);														//Add all errors added by 'messages.add' for display
		if(!detailsError && (checkboxValue(telecentreForm.getTelUpdateNameCheckBox()) || 	//if any checkbox has BEEN ticked
				checkboxValue(telecentreForm.getTelUpdateEmailCheckBox()) ||
				checkboxValue(telecentreForm.getTelUpdateProvinceCheckBox()) ||
				checkboxValue(telecentreForm.getTelUpdateActiveCheckBox()))){	 //Telecentre details have been entered correctly
			return mapping.findForward("updateConfirmation");					 //Forward to update profile confirmation page
		}else{
			if(!(checkboxValue(telecentreForm.getTelUpdateNameCheckBox()) || 	 //if any checkbox has NOT BEEN ticked
					checkboxValue(telecentreForm.getTelUpdateEmailCheckBox()) ||
					checkboxValue(telecentreForm.getTelUpdateProvinceCheckBox()) ||
					checkboxValue(telecentreForm.getTelUpdateActiveCheckBox()))){
				messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("errors.message.update.noCheck"));
				addErrors(request, messages);
			}
			return mapping.findForward("updateCentres");						//Remain in 'Update Profile' page and display errors
		}
  	}
  	
  	//Sifiso Changes:Added:2016/08/12:Method to update a Telecentre in the database
  	//method is called when the 'Update Telecentre' button is clicked in the Update Confirmation page
  	public ActionForward updateDbProfile(
  			ActionMapping mapping,
  			ActionForm form,
  			HttpServletRequest request,
  			HttpServletResponse response) throws Exception{
  		ActionMessages messages=new ActionMessages();
  		TelecentreForm telecentreForm = (TelecentreForm)form;
  		telecentreForm.setTelUpdatePageReached(3);		
  		TelecentreDAO dao = new TelecentreDAO();
  		int resultName=-1,resultEmail=-1,resultProvince=-1,resultStatus=-1,				//variables for update DB result
  			resultNameEmail=-1,resultNameProvince=-1,resultNameStatus=-1,
  			resultEmailProvince=-1,resultEmailStatus=-1,resultProvinceStatus=-1,
  			resultNameEmailProvince=-1,resultNameEmailStatus=-1,resultNameProvinceStatus=-1,
  			resultEmailProvinceStatus=-1,allUpdate=-1;
  		String updateFields="";															//names of fields to update
  		boolean nameOnly=false,emailOnly=false,provinceOnly=false,statusOnly=false,		//variables to validate checkboxes
  				nameEmail=false,nameProvince=false,nameStatus=false,nameEmailProvince=false,
  		  		nameEmailStatus=false,nameProvinceStatus=false,emailProvince=false,
  				emailStatus=false,emailProvinceStatus=false,provinceStatus=false,
  				allCheck=false;
  		
  		if(checkboxValue(telecentreForm.getTelUpdateNameCheckBox()) &&
  				!checkboxValue(telecentreForm.getTelUpdateEmailCheckBox()) &&
  				!checkboxValue(telecentreForm.getTelUpdateProvinceCheckBox()) &&
  				!checkboxValue(telecentreForm.getTelUpdateActiveCheckBox())){
  			nameOnly=true;			//only the telecentre name checkbox was checked
  		}
  		if(checkboxValue(telecentreForm.getTelUpdateEmailCheckBox()) &&
  				!checkboxValue(telecentreForm.getTelUpdateNameCheckBox()) &&
  				!checkboxValue(telecentreForm.getTelUpdateProvinceCheckBox()) &&
  				!checkboxValue(telecentreForm.getTelUpdateActiveCheckBox())){
  			emailOnly=true;			//only the telecentre email checkbox was checked
  		}
  		if(checkboxValue(telecentreForm.getTelUpdateProvinceCheckBox()) &&
  				!checkboxValue(telecentreForm.getTelUpdateNameCheckBox()) &&
  				!checkboxValue(telecentreForm.getTelUpdateEmailCheckBox()) &&
  				!checkboxValue(telecentreForm.getTelUpdateActiveCheckBox())){
  			provinceOnly=true;  	//only the telecentre province checkbox was checked
  		}
  		if(checkboxValue(telecentreForm.getTelUpdateActiveCheckBox()) &&
  				!checkboxValue(telecentreForm.getTelUpdateEmailCheckBox()) &&
  				!checkboxValue(telecentreForm.getTelUpdateNameCheckBox()) &&
  				!checkboxValue(telecentreForm.getTelUpdateProvinceCheckBox())){
  			statusOnly=true;		//only the telecentre status checkbox was checked
  		}
  		if(checkboxValue(telecentreForm.getTelUpdateNameCheckBox()) &&
  				checkboxValue(telecentreForm.getTelUpdateEmailCheckBox()) &&
  				!checkboxValue(telecentreForm.getTelUpdateProvinceCheckBox()) &&
  				!checkboxValue(telecentreForm.getTelUpdateActiveCheckBox())){
  			nameEmail=true;			//only the telecentre name AND email checkbox was checked
  		}
  		if(checkboxValue(telecentreForm.getTelUpdateNameCheckBox()) &&
  				checkboxValue(telecentreForm.getTelUpdateProvinceCheckBox()) &&
  				!checkboxValue(telecentreForm.getTelUpdateEmailCheckBox()) &&
  				!checkboxValue(telecentreForm.getTelUpdateActiveCheckBox())){
  			nameProvince=true;		//only the telecentre name AND province checkbox was checked
  		}
  		if(checkboxValue(telecentreForm.getTelUpdateNameCheckBox()) &&
  				checkboxValue(telecentreForm.getTelUpdateActiveCheckBox()) &&
  				!checkboxValue(telecentreForm.getTelUpdateEmailCheckBox()) &&
  				!checkboxValue(telecentreForm.getTelUpdateProvinceCheckBox())){
  			nameStatus=true;		//only the telecentre name AND status checkbox was checked
  		}
  		if(checkboxValue(telecentreForm.getTelUpdateEmailCheckBox()) &&
  				checkboxValue(telecentreForm.getTelUpdateProvinceCheckBox()) &&
  				!checkboxValue(telecentreForm.getTelUpdateNameCheckBox()) &&
  				!checkboxValue(telecentreForm.getTelUpdateActiveCheckBox())){
  			emailProvince=true;		//only the telecentre email AND province checkbox was checked
  		}
  		if(checkboxValue(telecentreForm.getTelUpdateEmailCheckBox()) &&
  				checkboxValue(telecentreForm.getTelUpdateActiveCheckBox()) &&
  				!checkboxValue(telecentreForm.getTelUpdateNameCheckBox()) &&
  				!checkboxValue(telecentreForm.getTelUpdateProvinceCheckBox())){
  			emailStatus=true;		//only the telecentre email AND status checkbox was checked
  		}
  		if(checkboxValue(telecentreForm.getTelUpdateProvinceCheckBox()) &&
  				checkboxValue(telecentreForm.getTelUpdateActiveCheckBox()) &&
  				!checkboxValue(telecentreForm.getTelUpdateNameCheckBox()) &&
  				!checkboxValue(telecentreForm.getTelUpdateEmailCheckBox())){
  			provinceStatus=true; 	//only the telecentre province AND status checkbox was checked
  		}
  		if(checkboxValue(telecentreForm.getTelUpdateNameCheckBox()) &&
  				checkboxValue(telecentreForm.getTelUpdateEmailCheckBox()) &&
  				checkboxValue(telecentreForm.getTelUpdateProvinceCheckBox()) &&
  				!checkboxValue(telecentreForm.getTelUpdateActiveCheckBox())){
  			nameEmailProvince=true; //only the telecentre name AND email AND province checkboxes were checked
  		}
  		if(checkboxValue(telecentreForm.getTelUpdateNameCheckBox()) &&
  				checkboxValue(telecentreForm.getTelUpdateEmailCheckBox()) &&
  				checkboxValue(telecentreForm.getTelUpdateActiveCheckBox()) &&
  				!checkboxValue(telecentreForm.getTelUpdateProvinceCheckBox())){
  			nameEmailStatus=true;	//only the telecentre name AND email AND status checkboxes were checked
  		}
  		if(checkboxValue(telecentreForm.getTelUpdateNameCheckBox()) &&
  				checkboxValue(telecentreForm.getTelUpdateProvinceCheckBox()) &&
  				checkboxValue(telecentreForm.getTelUpdateActiveCheckBox()) &&
  				!checkboxValue(telecentreForm.getTelUpdateEmailCheckBox())){
  			nameProvinceStatus=true; //only the telecentre name AND province AND status checkboxes were checked
  		}
  		if(checkboxValue(telecentreForm.getTelUpdateEmailCheckBox()) &&
  				checkboxValue(telecentreForm.getTelUpdateProvinceCheckBox()) &&
  				checkboxValue(telecentreForm.getTelUpdateActiveCheckBox()) &&
  				!checkboxValue(telecentreForm.getTelUpdateNameCheckBox())){
  			emailProvinceStatus=true; //only the telecentre email AND province AND status checkboxes were checked
  		}
  		if(checkboxValue(telecentreForm.getTelUpdateProvinceCheckBox()) &&
  				checkboxValue(telecentreForm.getTelUpdateActiveCheckBox()) &&
  				checkboxValue(telecentreForm.getTelUpdateNameCheckBox()) &&
  				checkboxValue(telecentreForm.getTelUpdateEmailCheckBox())){
  			allCheck=true;			//all checkboxes were checked
  		}

  		//update the name
  		if(nameOnly){
  			updateFields="name";
	  		//if update in database is successfull, 'result' must be >0
  			resultName=dao.updateCentreDb(telecentreForm.getTelecentreName(),"","","",
						 telecentreForm.getTelecentreCurrentName(),telecentreForm.getTelecentreCurrentCode(),
						 telecentreForm.getTelecentreCurrentProvince(),telecentreForm.getTelecentreCurrentEmail(),
						 updateFields);
	  		if(resultName<0){
	  			messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("errors.message.update.nameFail"));	//failure
	  		}else{
	  			if(resultName>0){
	  				messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("profile.update.completion.name"));	//success
	  			}
	  		}
  		}
  		//update the email
  		if(emailOnly){
  			updateFields="email";
  			resultEmail=dao.updateCentreDb("",telecentreForm.getTelecentreEmail(),"","",
						 telecentreForm.getTelecentreCurrentName(),telecentreForm.getTelecentreCurrentCode(),
						 telecentreForm.getTelecentreCurrentProvince(),telecentreForm.getTelecentreCurrentEmail(),
						 updateFields);
	  		if(resultEmail<0){
	  			messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("errors.message.update.emailFail"));		
	  		}else{
	  			if(resultEmail>0){
	  				messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("profile.update.completion.email"));	
	  			}
	  		}
  		}
  		//update the province
  		if(provinceOnly){
  			updateFields="province";
  			resultProvince=dao.updateCentreDb("","",telecentreForm.getProvince(),"",
						 telecentreForm.getTelecentreCurrentName(),telecentreForm.getTelecentreCurrentCode(),
						 telecentreForm.getTelecentreCurrentProvince(),telecentreForm.getTelecentreCurrentEmail(),
						 updateFields);
	  		if(resultProvince<0){
	  			messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("errors.message.update.provinceFail"));		
	  		}else{
	  			if(resultProvince>0){
	  				messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("profile.update.completion.province"));	
	  			}
	  		}
  		}
  		//update the status
  		if(statusOnly){
  			updateFields="status";
  			resultStatus=dao.updateCentreDb("","","",telecentreForm.getTelecentreStatus(),
						 telecentreForm.getTelecentreCurrentName(),telecentreForm.getTelecentreCurrentCode(),
						 telecentreForm.getTelecentreCurrentProvince(),telecentreForm.getTelecentreCurrentEmail(),
						 updateFields);
	  		
	  		if(resultStatus<0){
	  			messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("errors.message.update.statusFail"));		
	  		}else{
	  			if(resultStatus>0){
	  				messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("profile.update.completion.status"));
	  			}
	  		}
  		}
  		//update the name and Email 
  		if(nameEmail){
  			updateFields="nameEmail";
  			resultNameEmail=dao.updateCentreDb(telecentreForm.getTelecentreName(),telecentreForm.getTelecentreEmail(),"","",
						 telecentreForm.getTelecentreCurrentName(),telecentreForm.getTelecentreCurrentCode(),
						 telecentreForm.getTelecentreCurrentProvince(),telecentreForm.getTelecentreCurrentEmail(),
						 updateFields);
	  		
	  		if(resultNameEmail<0){
	  			messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("errors.message.update.nameFail"));
	  			messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("errors.message.update.emailFail"));
	  		}else{
	  			if(resultNameEmail>0){
	  				messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("profile.update.completion.name"));
	  				messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("profile.update.completion.email"));
	  			}
	  		}
  		}
  		//update the name and Province 
  		if(nameProvince){
  			updateFields="nameProvince";
  			resultNameProvince=dao.updateCentreDb(telecentreForm.getTelecentreName(),"",telecentreForm.getProvince(),"",
						 telecentreForm.getTelecentreCurrentName(),telecentreForm.getTelecentreCurrentCode(),
						 telecentreForm.getTelecentreCurrentProvince(),telecentreForm.getTelecentreCurrentEmail(),
						 updateFields);
	  		
	  		if(resultNameProvince<0){
	  			messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("errors.message.update.nameFail"));
	  			messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("errors.message.update.provinceFail"));		
	  		}else{
	  			if(resultNameProvince>0){
	  				messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("profile.update.completion.name"));
	  				messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("profile.update.completion.province"));
	  			}
	  		}
  		}
  		//update the name and Status 
  		if(nameStatus){
  			updateFields="nameStatus";
  			resultNameStatus=dao.updateCentreDb(telecentreForm.getTelecentreName(),"","",telecentreForm.getTelecentreStatus(),
						 telecentreForm.getTelecentreCurrentName(),telecentreForm.getTelecentreCurrentCode(),
						 telecentreForm.getTelecentreCurrentProvince(),telecentreForm.getTelecentreCurrentEmail(),
						 updateFields);
	  		
	  		if(resultNameStatus<0){
	  			messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("errors.message.update.nameFail"));
	  			messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("errors.message.update.statusFail"));		
	  		}else{
	  			if(resultNameStatus>0){
	  				messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("profile.update.completion.name"));
	  				messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("profile.update.completion.status"));
	  			}
	  		}
  		}
  		//update the email and province 
  		if(emailProvince){
  			updateFields="emailProvince";
  			resultEmailProvince=dao.updateCentreDb("",telecentreForm.getTelecentreEmail(),telecentreForm.getProvince(),"",
						 telecentreForm.getTelecentreCurrentName(),telecentreForm.getTelecentreCurrentCode(),
						 telecentreForm.getTelecentreCurrentProvince(),telecentreForm.getTelecentreCurrentEmail(),
						 updateFields);
	  		
	  		if(resultEmailProvince<0){
	  			messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("errors.message.update.emailFail"));
	  			messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("errors.message.update.provinceFail"));		
	  		}else{
	  			if(resultEmailProvince>0){
	  				messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("profile.update.completion.email"));
	  				messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("profile.update.completion.province"));
	  			}
	  		}
  		}
  		//update the email and status 
  		if(emailStatus){
  			updateFields="emailStatus";
  			resultEmailStatus=dao.updateCentreDb("",telecentreForm.getTelecentreEmail(),"",telecentreForm.getTelecentreStatus(),
						 telecentreForm.getTelecentreCurrentName(),telecentreForm.getTelecentreCurrentCode(),
						 telecentreForm.getTelecentreCurrentProvince(),telecentreForm.getTelecentreCurrentEmail(),
						 updateFields);
	  		
	  		if(resultEmailStatus<0){
	  			messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("errors.message.update.emailFail"));
	  			messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("errors.message.update.statusFail"));		
	  		}else{
	  			if(resultEmailStatus>0){
	  				messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("profile.update.completion.email"));
	  				messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("profile.update.completion.status"));
	  			}
	  		}
  		}
  		//update the province and status 
  		if(provinceStatus){
  			updateFields="provinceStatus";
  			resultProvinceStatus=dao.updateCentreDb("","",telecentreForm.getProvince(),telecentreForm.getTelecentreStatus(),
						 telecentreForm.getTelecentreCurrentName(),telecentreForm.getTelecentreCurrentCode(),
						 telecentreForm.getTelecentreCurrentProvince(),telecentreForm.getTelecentreCurrentEmail(),
						 updateFields);
	  		
	  		if(resultProvinceStatus<0){
	  			messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("errors.message.update.provinceFail"));
	  			messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("errors.message.update.statusFail"));		
	  		}else{
	  			if(resultProvinceStatus>0){
	  				messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("profile.update.completion.province"));
	  				messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("profile.update.completion.status"));
	  			}
	  		}
  		}
  		//update the name and email and province 
  		if(nameEmailProvince){
  			updateFields="nameEmailProvince";
  			resultNameEmailProvince=dao.updateCentreDb(telecentreForm.getTelecentreName(),telecentreForm.getTelecentreEmail(),
  						 telecentreForm.getProvince(),"",telecentreForm.getTelecentreCurrentName(),
  						 telecentreForm.getTelecentreCurrentCode(),telecentreForm.getTelecentreCurrentProvince(),
  						 telecentreForm.getTelecentreCurrentEmail(),updateFields);
	  		
	  		if(resultNameEmailProvince<0){
	  			messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("errors.message.update.nameFail"));
	  			messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("errors.message.update.emailFail"));
	  			messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("errors.message.update.provinceFail"));
	  		}else{
	  			if(resultNameEmailProvince>0){
	  				messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("profile.update.completion.name"));
	  				messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("profile.update.completion.email"));
	  				messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("profile.update.completion.province"));
	  			}
	  		}
  		}
  		//update the name and email and status 
  		if(nameEmailStatus){
  			updateFields="nameEmailStatus";
  			resultNameEmailStatus=dao.updateCentreDb(telecentreForm.getTelecentreName(),telecentreForm.getTelecentreEmail(),"",
  						 telecentreForm.getTelecentreStatus(),telecentreForm.getTelecentreCurrentName(),
  						 telecentreForm.getTelecentreCurrentCode(),telecentreForm.getTelecentreCurrentProvince(),
  						 telecentreForm.getTelecentreCurrentEmail(),updateFields);
	  		
	  		if(resultNameEmailStatus<0){
	  			messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("errors.message.update.nameFail"));
	  			messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("errors.message.update.emailFail"));
	  			messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("errors.message.update.statusFail"));
	  		}else{
	  			if(resultNameEmailStatus>0){
	  				messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("profile.update.completion.name"));
	  				messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("profile.update.completion.email"));
	  				messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("profile.update.completion.status"));
	  			}
	  		}
  		}
  		//update the name and province and status 
  		if(nameProvinceStatus){
  			updateFields="nameProvinceStatus";
  			resultNameProvinceStatus=dao.updateCentreDb(telecentreForm.getTelecentreName(),"",telecentreForm.getProvince(),
  						 telecentreForm.getTelecentreStatus(),telecentreForm.getTelecentreCurrentName(),
  						 telecentreForm.getTelecentreCurrentCode(),telecentreForm.getTelecentreCurrentProvince(),
  						 telecentreForm.getTelecentreCurrentEmail(),updateFields);
	  		
	  		if(resultNameProvinceStatus<0){
	  			messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("errors.message.update.nameFail"));
	  			messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("errors.message.update.provinceFail"));
	  			messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("errors.message.update.statusFail"));
	  		}else{
	  			if(resultNameProvinceStatus>0){
	  				messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("profile.update.completion.name"));
	  				messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("profile.update.completion.province"));
	  				messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("profile.update.completion.status"));
	  			}
	  		}
  		}
  		//update the email and province and status 
  		if(emailProvinceStatus){
  			updateFields="emailProvinceStatus";
  			resultEmailProvinceStatus=dao.updateCentreDb("",telecentreForm.getTelecentreEmail(),telecentreForm.getProvince(),
  						 telecentreForm.getTelecentreStatus(),telecentreForm.getTelecentreCurrentName(),
  						 telecentreForm.getTelecentreCurrentCode(),telecentreForm.getTelecentreCurrentProvince(),
  						 telecentreForm.getTelecentreCurrentEmail(),updateFields);
	  		
	  		if(resultEmailProvinceStatus<0){
	  			messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("errors.message.update.emailFail"));
	  			messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("errors.message.update.provinceFail"));
	  			messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("errors.message.update.statusFail"));
	  		}else{
	  			if(resultEmailProvinceStatus>0){
	  				messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("profile.update.completion.email"));
	  				messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("profile.update.completion.province"));
	  				messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("profile.update.completion.status"));
	  			}
	  		}
  		}
  		//update all fields 
  		if(allCheck){
  			updateFields="allCheck";
  			allUpdate=dao.updateCentreDb(telecentreForm.getTelecentreName(),telecentreForm.getTelecentreEmail(),telecentreForm.getProvince(),
  					telecentreForm.getTelecentreStatus(),telecentreForm.getTelecentreCurrentName(),telecentreForm.getTelecentreCurrentCode(),
					telecentreForm.getTelecentreCurrentProvince(),telecentreForm.getTelecentreCurrentEmail(),
					updateFields);
	  		
	  		if(allUpdate<0){
	  			messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("errors.message.update.nameFail"));
	  			messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("errors.message.update.emailFail"));
	  			messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("errors.message.update.provinceFail"));
	  			messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("errors.message.update.statusFail"));	
	  		}else{
	  			if(allUpdate>0){
	  				messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("profile.update.completion.name"));
	  				messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("profile.update.completion.email"));
	  				messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("profile.update.completion.province"));
	  				messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("profile.update.completion.status"));
	  			}
	  		}
  		}
  		addErrors(request, messages);				//add the message of success/failure for display
  		//reset everything
		telecentreForm.setTelecentreCurrentName("Select a Telecentre to update");
		telecentreForm.setTelecentreName("Select a Telecentre to update");
		telecentreForm.setTelUpdatePageReached(0);				
		telecentreForm.setTelUpdateListTracker(0);				
		telecentreForm.setTelUpdateNameCheckBox(false);
		telecentreForm.setTelUpdateEmailCheckBox(false);
		telecentreForm.setTelUpdateProvinceCheckBox(false);
		telecentreForm.setTelUpdateActiveCheckBox(false);
  		return mapping.findForward("updateCentres");
  	}
  	
  	//Sifiso Changes:Added:2016/07/26:Remove Telecentres page action mapping method
  	public ActionForward removeCentresLinkBtn(
  			ActionMapping mapping,
		    ActionForm form,
		    HttpServletRequest request,
		    HttpServletResponse response) throws Exception{
		TelecentreForm telecentreForm = (TelecentreForm) form;
		TelecentreDAO dao=new TelecentreDAO();
		HttpSession session=request.getSession(true);
		telecentreForm.setTelecenters(dao.getAllCentres());		//set all centres for drop-down for user selection
		session.setAttribute("centreNames", telecentreForm.getTelecenters());
		telecentreForm.setTelecentreRemoveName("Select a Telecentre to remove");
		//telecentreForm.setTelecentreName("Select a Telecentre to update");
		telecentreForm.setTelRemovePageReached(0);				//Indicate that the 'Remove Profile' page is reached (1) or not(0)
		telecentreForm.setTelRemoveListTracker(0);				//Indicate that the drop-down on this page is not selected yet
		telecentreForm.setTelecentreRemoveMsg("");
		return mapping.findForward("removeCentres");
  	}
	//Sifiso Changes:Added:2016/08/14:Handles the onchange event of the drop-down list for Telecentre selection in the 'Remove Telecentre' jsp
  	public ActionForward handleRemoveListBoxAction(
  			ActionMapping mapping,
  			ActionForm form,
  			HttpServletRequest request,
  			HttpServletResponse response) throws Exception{
  		TelecentreForm telecentreForm = (TelecentreForm) form;
  		TelecentreDAO dao=new TelecentreDAO();
  		
  		if(telecentreForm.getTelecentreRemoveName().trim().indexOf("Select")==-1){	//no occurance of 'Select'==>a centre WAS selected from drop-down
	  		telecentreForm.setTelRemoveListTracker(1);
	  		telecentreForm.setTelRemovePageReached(1);
	  		if(telecentreForm.getTelRemoveListTracker()==1){
	  			populateTelecentreInfo(telecentreForm,dao,telecentreForm.getTelecentreRemoveName());
	  		}
	  		return mapping.findForward("removeCentres");
  		}else{
	  			telecentreForm.setTelRemoveListTracker(0);				//a centre WAS NOT selected
  		}
  		return mapping.findForward("removeCentres");
  	}  
	//Sifiso Changes:Added:2016/08/14:Remove Telecentres button page action mapping method
  	public ActionForward removeCentresBtn(
  			ActionMapping mapping,
		    ActionForm form,
		    HttpServletRequest request,
		    HttpServletResponse response) throws Exception{
		TelecentreForm telecentreForm = (TelecentreForm) form;
  		TelecentreDAO dao=new TelecentreDAO();
		telecentreForm.setTelRemovePageReached(2);				
		telecentreForm.setTelRemoveListTracker(1);		//Indicate that the drop-down on this page is already selected yet
		populateTelecentreInfo(telecentreForm,dao,telecentreForm.getTelecentreRemoveName());
		return mapping.findForward("removeConfirmation");
  	}
	//Sifiso Changes:Added:2016/08/14:Confirm Remove Telecentres button page action mapping method
  	//Method to delete the records from the database using the tele ID of the selected Telecentre
  	public ActionForward removeDbCentre(
  			ActionMapping mapping,
		    ActionForm form,
		    HttpServletRequest request,
		    HttpServletResponse response) throws Exception{
		TelecentreForm telecentreForm = (TelecentreForm) form;
  		TelecentreDAO dao=new TelecentreDAO();
  		ActionMessages messages=new ActionMessages();
  		String dbResultMessage="";
  		dbResultMessage=dao.updateCentreDb(telecentreForm.getTelecentreRemoveName());
  		telecentreForm.setTelecentreRemoveMsg(dbResultMessage);
  		if(dbResultMessage.indexOf("successfull!")!=-1){
  			messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("profile.remove.success.message"));
  		}else{
  			if(dbResultMessage.indexOf("failed!")==-1){
  	  			messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("profile.remove.failure.message"));
  			}
  		}
		telecentreForm.setTelRemovePageReached(0);				
		telecentreForm.setTelRemoveListTracker(0);		//Indicate that the drop-down on this page is already selected yet
		addErrors(request, messages);					//add the message of success/failure for display
		return mapping.findForward("removeCentres");
  	}
  	
  	//Sifiso Changes:Added:2016/07/26:Activate/Deactivate Telecentre monthly code page action mapping method
  	public ActionForward activateCentresLinkBtn(
  			ActionMapping mapping,
		    ActionForm form,
		    HttpServletRequest request,
		    HttpServletResponse response) throws Exception{
		TelecentreForm telecentreForm = (TelecentreForm) form;
		TelecentreDAO dao=new TelecentreDAO();
		HttpSession session=request.getSession(true);
		telecentreForm.setTelecenters(dao.getAllCentres());		//set all centres for drop-down for user selection
		session.setAttribute("centreNames", telecentreForm.getTelecenters());
		telecentreForm.setTelActivatePageReached(0);			
		telecentreForm.setTelActivateListTracker(0);
		telecentreForm.setTelecentreActivateName("Select a Telecentre to Activate/Deactivate");
		return mapping.findForward("activateCentres");
  	}
	//Sifiso Changes:Added:2016/08/14:Handles the onchange event of the drop-down list for Telecentre selection in the 'Remove Telecentre' jsp
  	public ActionForward handleActivateListBoxAction(
  			ActionMapping mapping,
  			ActionForm form,
  			HttpServletRequest request,
  			HttpServletResponse response) throws Exception{
  		TelecentreForm telecentreForm = (TelecentreForm) form;
  		TelecentreDAO dao=new TelecentreDAO();
  		if(telecentreForm.getTelecentreActivateName().trim().indexOf("Select")==-1){	//no occurance of 'Select'==>a centre WAS selected from drop-down
	  		telecentreForm.setTelActivatePageReached(1);
	  		telecentreForm.setTelActivateListTracker(1);
	  		if(telecentreForm.getTelActivateListTracker()==1){
	  			populateTelecentreInfo(telecentreForm,dao,telecentreForm.getTelecentreActivateName());
	  		}
	  		return mapping.findForward("activateCentres");
  		}else{
	  			telecentreForm.setTelActivateListTracker(0);				//a centre WAS NOT selected
  		}
  		return mapping.findForward("activateCentres");
  	} 
	//Sifiso Changes:Added:2016/08/14:Activate Telecentres button page action mapping method
    //method redirects to the Activate confirmation page
  	public ActionForward activateCentresBtn(
  			ActionMapping mapping,
		    ActionForm form,
		    HttpServletRequest request,
		    HttpServletResponse response) throws Exception{
		TelecentreForm telecentreForm = (TelecentreForm) form;
  		TelecentreDAO dao=new TelecentreDAO();
		telecentreForm.setTelActivatePageReached(2);				
		telecentreForm.setTelActivateListTracker(1);		//Indicate that the drop-down on this page is already selected yet
		populateTelecentreInfo(telecentreForm,dao,telecentreForm.getTelecentreActivateName());
		return mapping.findForward("activateConfirmation");
  	}
	//Sifiso Changes:Added:2016/08/14:Deactivate Telecentres button page action mapping method
  	//method redirects to the Deactivate confirmation page
  	public ActionForward deactivateCentresBtn(
  			ActionMapping mapping,
		    ActionForm form,
		    HttpServletRequest request,
		    HttpServletResponse response) throws Exception{
		TelecentreForm telecentreForm = (TelecentreForm) form;
  		TelecentreDAO dao=new TelecentreDAO();
		telecentreForm.setTelActivatePageReached(3);				
		telecentreForm.setTelActivateListTracker(1);		//Indicate that the drop-down on this page is already selected yet
		populateTelecentreInfo(telecentreForm,dao,telecentreForm.getTelecentreActivateName());
		return mapping.findForward("activateConfirmation");
  	}
  	
  	//Sifiso Changes:Added:2016/11/28:Manage Admins Telecentre page action mapping method
  	public ActionForward manageAdminsLinkBtn(
  			ActionMapping mapping,
		    ActionForm form,
		    HttpServletRequest request,
		    HttpServletResponse response) throws Exception{
		TelecentreForm telecentreForm = (TelecentreForm) form;
		TelecentreDAO dao=new TelecentreDAO();
		HttpSession session=request.getSession(true);
		telecentreForm.setActiveStatusList(generateCentreActive());
        session.setAttribute("activeStatusList",telecentreForm.getActiveStatusList());
		telecentreForm.setTelManageAdminsReached(1);
		return mapping.findForward("manageAdmins");
  	}
  	
	//Sifiso Changes:Added:2016/08/14:Confirm Activate Telecentres button page action mapping method
  	//method to update the database to change the Active field to value 'Y' for the record (Telecentre)
  	public ActionForward activateDbCentre(
  			ActionMapping mapping,
		    ActionForm form,
		    HttpServletRequest request,
		    HttpServletResponse response) throws Exception{
		TelecentreForm telecentreForm = (TelecentreForm) form;
  		TelecentreDAO dao=new TelecentreDAO();
  		ActionMessages messages=new ActionMessages();
  		String activeStatus="Y";
  		int updateResult=dao.updateStatusDB(telecentreForm.getTelecentreActivateName(),activeStatus);
  		if(updateResult>0){
  			messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("profile.activate.success.activate.message"));
  		}else{
  			messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("profile.activate.failure.activate.message"));
  		}
		telecentreForm.setTelActivatePageReached(0);				
		telecentreForm.setTelActivateListTracker(0);		//Indicate that the drop-down on this page is already selected yet
		addErrors(request, messages);						//add the message of success/failure for display
		return mapping.findForward("activateCentres");
  	}
	//Sifiso Changes:Added:2016/08/14:Confirm Deactivate Telecentres button page action mapping method
  	//method to update the database to change the Active field to value 'N' for the record (Telecentre)
  	public ActionForward deactivateDbCentre(
  			ActionMapping mapping,
		    ActionForm form,
		    HttpServletRequest request,
		    HttpServletResponse response) throws Exception{
		TelecentreForm telecentreForm = (TelecentreForm) form;
  		TelecentreDAO dao=new TelecentreDAO();
  		ActionMessages messages=new ActionMessages();
  		String activeStatus="N";
  		int updateResult=dao.updateStatusDB(telecentreForm.getTelecentreActivateName(),activeStatus);
  		if(updateResult>0){
  			messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("profile.activate.success.deactivate.message"));
  		}else{
  			messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("profile.activate.failure.deactivate.message"));
  		}
		telecentreForm.setTelActivatePageReached(0);				
		telecentreForm.setTelActivateListTracker(0);		//Indicate that the drop-down on this page is already selected yet
		addErrors(request, messages);						//add the message of success/failure for display
		return mapping.findForward("activateCentres");
  	}
  	
  	public ActionForward viewAdminBtn(
  			ActionMapping mapping,
  			ActionForm form,
  			HttpServletRequest request,
  			HttpServletResponse response) throws Exception{
		ActionMessages messages=new ActionMessages();
		TelecentreForm telecentreForm = (TelecentreForm) form;
		HttpSession session = request.getSession(true);
		TelecentreDAO dao = new TelecentreDAO();
		telecentreForm.setTelViewAdminsReached(1);				//Set to 1 to enable going back to 'Manage Admins' page
		ArrayList adminInfo = dao.getAdminInfo();
		telecentreForm.setAdminInfo(adminInfo);
		session.setAttribute("adminInfo",telecentreForm.getAdminInfo());
		return mapping.findForward("viewAdmins");					
  	}   
  
  	public ActionForward addAdminBtn(
  			ActionMapping mapping,
  			ActionForm form,
  			HttpServletRequest request,
  			HttpServletResponse response) throws Exception{
		ActionMessages messages=new ActionMessages();
		TelecentreForm telecentreForm = (TelecentreForm) form;
		telecentreForm.setTelAddAdminsReached(1);				//Set to 1 to enable going back to 'Manage Admins' page
		return mapping.findForward("addAdmins");					
  	} 
  	
  	//Sifiso Changes:Added:2016/11/30:Add Admin page action mapping method for 'Submit' button
  	public ActionForward addAdminSubmit(
  			ActionMapping mapping,
  			ActionForm form,
  			HttpServletRequest request,
  			HttpServletResponse response) throws Exception{ 
		ActionMessages messages=new ActionMessages();
		TelecentreForm telecentreForm = (TelecentreForm) form;
		TelecentreDAO dao = new TelecentreDAO(); 
		telecentreForm.setTelAddAdminsReached(1);				
		boolean emailValid=true,emailFormatValidFlag=false,usernameValid=true,userNameExists=false,nameValid=true;
		
		//perform validations for user input - assign appropriate error messages for wrong input
		if(telecentreForm.getTeleAdminStaffName().equals("")){		//Admin staff name has not been entered
			nameValid=false;
		}
		if(telecentreForm.getTeleAdminEmail().equals("")){			//Admin AD username has not been entered
			emailValid=false;
		}
		if (emailValid && emailFormatValid(telecentreForm.getTeleAdminEmail())){
			emailFormatValidFlag=true;								//Determine format of entered admin email (someone@domain.domainExt)
		}	
		if(emailValid && !emailFormatValidFlag){					//Format of admin email is invalid
			messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("errors.message.create.emailInvalid"));
		}
		if(telecentreForm.getTeleAdminAdUserName().equals("")){		//Admin user name has not been entered
			usernameValid=false;
			messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("errors.message.manage.admin.username")); 
		}else{
			if(dao.checkTelecentreAdminExists(telecentreForm.getTeleAdminAdUserName())){
				userNameExists=true;		
			}
		}
		addErrors(request, messages);		//Add all validation errors when forwarding
		
		if(usernameValid && !userNameExists){						//Admin AD username has been entered correctly and does not already exist		
			//email format is invalid so dont proceed further
			if(emailValid && !emailFormatValidFlag){				//Email can be empty. If not empty format MUST be valid
				telecentreForm.setTeleAdminEmail("");				//reset values for correct error message display
				telecentreForm.setTeleAdminAdUserName("");			
				telecentreForm.setTeleAdminRegional("");			
				return mapping.findForward("addAdmins");			//Remain in 'Add Admins' page and display errors
			}
			try{													//try to insert all details input by user to DB
				int addAdminDBResult = dao.addTelecentreAdmin(telecentreForm.getTeleAdminAdUserName(),telecentreForm.getTeleAdminStaffName(),
						telecentreForm.getTeleAdminEmail(),telecentreForm.getTeleAdminRegional());
				if(addAdminDBResult>0){
					messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("profile.manage.admin.add.success"));
				}else{
					messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("profile.manage.admin.add.failure"));
				}
				addErrors(request, messages);
				return mapping.findForward("manageAdmins");			    //Forward to 'Manage Admins' home page and display success/failure
			}catch(Exception ex){										//catch any DB errors for insert and display appropriate error message for user
				messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("profile.manage.admin.add.failure"));
				addErrors(request, messages);
				return mapping.findForward("manageAdmins");			    
			}
		}else{
			//username exists and valid but email format is invalid so dont proceed further
			if(usernameValid && userNameExists && emailValid && !emailFormatValidFlag){				
				telecentreForm.setTeleAdminEmail("");				
				telecentreForm.setTeleAdminAdUserName("");			
				telecentreForm.setTeleAdminRegional("");			
				return mapping.findForward("addAdmins");			
			}
			//try and catch DB errors so that an appropriate error message is displayed on Sakai instead of DB/Java stacktrace 
			try{
				String updateAll="",updateName="",updateEmail="",updateRegion="",updateNameEmail="",updateNameRegion="",updateEmailRegion="";
				boolean updateAllFlag=false,updateNameFlag=false,updateEmailFlag=false,updateRegionFlag=false,updateNameEmailFlag=false, 
						updateNameRegionFlag=false,updateEmailRegionFlag=false,sameStaffName=false,sameStaffEmail=false,sameRegional=false;
				if(nameValid){
					sameStaffName = dao.queryTelAdminDetails(telecentreForm.getTeleAdminAdUserName(),  //check if staff name in DB is identical to user input
							telecentreForm.getTeleAdminStaffName(), "", "", "name");
				}
				if(emailValid){
					sameStaffEmail = dao.queryTelAdminDetails(telecentreForm.getTeleAdminAdUserName(), //check if staff email in DB is identical to user input
							"", telecentreForm.getTeleAdminEmail(), "", "email");
				}
				sameRegional = dao.queryTelAdminDetails(telecentreForm.getTeleAdminAdUserName(),   	   //check if regional flag in DB is identical to MANDATORY user input
						"", "", telecentreForm.getTeleAdminRegional(), "region");		

				//negation: conditions negate identical values to determine DB update field - NOTE: 'sameRegional' is MANDATORY INPUT
				//name and email input received - no input identical - update all
				if((nameValid && emailValid) && !sameStaffName && !sameStaffEmail && !sameRegional){  	
					updateAll="all";
					updateAllFlag=true;
				}
				//name and email input received - name and email input not identical to DB value, region identical - then update name and email
				if((nameValid && emailValid) && !sameStaffName && !sameStaffEmail && sameRegional){
					updateNameEmail="nameEmail";
					updateNameEmailFlag=true;
				}
				//name and email input received - name not identical to DB value, email and region identical - then update name
				if((nameValid && emailValid) && !sameStaffName && sameStaffEmail && sameRegional){
					updateName="name";
					updateNameFlag=true;
				}
				//name input received, email input not receieved - name not identical to DB value, region identical - then update name
				if((nameValid && !emailValid) && !sameStaffName && sameRegional){  
					updateName="name";
					updateNameFlag=true;
				}
				//name and email input received - email input not identical to DB value, name and region identical - then update email
				if((emailValid && nameValid) && sameStaffName && !sameStaffEmail && sameRegional){
					updateEmail="email";
					updateEmailFlag=true;
				}
				//email input received, name input not receieved - email input not identical to DB value, region identical - then update email
				if((emailValid && !nameValid) && !sameStaffEmail && sameRegional){
					updateEmail="email";
					updateEmailFlag=true;
				}
				//name and email input not received, region not identical to DB value - then update region
				if((!nameValid && !emailValid) && !sameRegional){	 
					updateRegion="region";
					updateRegionFlag=true;
				}
				//name and email input received and are identical to DB values, region not identical - then update region
				if((nameValid && emailValid) && sameStaffName && sameStaffEmail && !sameRegional){	 
					updateRegion="region";
					updateRegionFlag=true;
				}
				//name input not received, email received and identical to DB value, region not identical - then update region
				if((!nameValid && emailValid) && sameStaffEmail && !sameRegional){	 
					updateRegion="region";
					updateRegionFlag=true;
				}
				//email input not received, name received and identical to DB value, region not identical - then update region
				if((nameValid && !emailValid) && sameStaffName && !sameRegional){	 
					updateRegion="region";
					updateRegionFlag=true;
				}
				//name input received, email not received - name and region not identical to DB value - then update name and region
				if((nameValid && !emailValid) && !sameStaffName && !sameRegional){
					updateNameRegion="nameRegion";
					updateNameRegionFlag=true;
				}
				//name and email input received - email identical to DB value, name and region not identical - then update name and region
				if((nameValid && emailValid) && !sameStaffName && sameStaffEmail && !sameRegional){
					updateNameRegion="nameRegion";
					updateNameRegionFlag=true;
				}
				//email input received, name input not receieved - email and region input not identical to DB value - then update email and region
				if((!nameValid && emailValid) && !sameStaffEmail && !sameRegional){
					updateEmailRegion="emailRegion";
					updateEmailRegionFlag=true;
				}
				//name and email input received, name input identical to DB value, email and region not identical - then update email and region
				if((emailValid && nameValid) && sameStaffName && !sameStaffEmail && !sameRegional){
					updateEmailRegion="emailRegion";
					updateEmailRegionFlag=true;
				}

				//perform UPDATE if user input for username is identical to DB value but any other input is not identical to DB values
				if(usernameValid && userNameExists){
					if(updateAllFlag){				//ALL user input fields are not identical to DB values: UPDATE ALL DB fields (except username)
						int dbResult=dao.updateTelAdminDetails(telecentreForm.getTeleAdminAdUserName(),telecentreForm.getTeleAdminStaffName(),
								telecentreForm.getTeleAdminEmail(),telecentreForm.getTeleAdminRegional(),updateAll);
						if(dbResult>0){
							messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("profile.manage.admin.add.updateSuccess.all"));
						}else{
							messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("profile.manage.admin.add.updateFailure.all"));
						}
					}else if(updateNameFlag){  		//user input for staff name is not identical to DB value: UPDATE staff NAME ONLY
						int dbResult=dao.updateTelAdminDetails(telecentreForm.getTeleAdminAdUserName(),telecentreForm.getTeleAdminStaffName(),"","",updateName);
						if(dbResult>0){
							messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("profile.manage.admin.add.updateSuccess.staffName"));
						}else{
							messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("profile.manage.admin.add.updateFailure.staffName"));
						}
					}else if(updateEmailFlag){		//user input for staff email is not identical to DB value: UPDATE staff EMAIL ONLY
						int dbResult=dao.updateTelAdminDetails(telecentreForm.getTeleAdminAdUserName(),"",telecentreForm.getTeleAdminEmail(),"",updateEmail);
						if(dbResult>0){
							messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("profile.manage.admin.add.updateSuccess.staffEmail"));
						}else{
							messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("profile.manage.admin.add.updateFailure.staffEmail"));
						}	    
					}else if(updateRegionFlag){		//user input for regional flag is not identical to DB value: UPDATE REGIONAL ONLY
						int dbResult=dao.updateTelAdminDetails(telecentreForm.getTeleAdminAdUserName(),"","",telecentreForm.getTeleAdminRegional(),updateRegion);
						if(dbResult>0){
							messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("profile.manage.admin.add.updateSuccess.regional"));
						}else{
							messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("profile.manage.admin.add.updateFailure.regional"));
						}
					}else if(updateNameEmailFlag){		//user input for name and email flag is not identical to DB value: UPDATE NAME AND EMAIL ONLY
						int dbResult=dao.updateTelAdminDetails(telecentreForm.getTeleAdminAdUserName(),telecentreForm.getTeleAdminStaffName(),
								telecentreForm.getTeleAdminEmail(),"",updateNameEmail);
						if(dbResult>0){
							messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("profile.manage.admin.add.updateSuccess.nameEmail"));
						}else{
							messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("profile.manage.admin.add.updateFailure.nameEmail"));
						}
					}else if(updateNameRegionFlag){	 //user input for name and regional flag is not identical to DB value: UPDATE NAME AND REGIONAL ONLY
							int dbResult=dao.updateTelAdminDetails(telecentreForm.getTeleAdminAdUserName(),telecentreForm.getTeleAdminStaffName(),"",
									telecentreForm.getTeleAdminRegional(),updateNameRegion);
							if(dbResult>0){
								messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("profile.manage.admin.add.updateSuccess.nameRegion"));
							}else{
								messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("profile.manage.admin.add.updateFailure.nameRegion"));
							}
					}else if(updateEmailRegionFlag){  //user input for name and regional flag is not identical to DB value: UPDATE EMAIL AND REGIONAL ONLY
							int dbResult=dao.updateTelAdminDetails(telecentreForm.getTeleAdminAdUserName(),"",telecentreForm.getTeleAdminEmail(),
									telecentreForm.getTeleAdminRegional(),updateEmailRegion);
							if(dbResult>0){
								messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("profile.manage.admin.add.updateSuccess.emailRegion"));
							}else{
								messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("profile.manage.admin.add.updateFailure.emailRegion"));
							}
					}
					addErrors(request, messages);
					return mapping.findForward("manageAdmins"); //Forward to 'Manage Admins' home page and display success/failure
				}else{											//no insertion or update could be performed, remain in 'Add Admins' page and display errors		
					telecentreForm.setTeleAdminEmail("");		//reset values for correct error message display	
					telecentreForm.setTeleAdminAdUserName("");				
					telecentreForm.setTeleAdminRegional("");				
					return mapping.findForward("addAdmins");	//Remain in 'Add Admins' page and display errors
				}					
			}catch(Exception ex){		//catch any DB errors for update and display appropriate error message for user
				messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("profile.manage.admin.add.updateAll.failure"));
				addErrors(request, messages);
				return mapping.findForward("manageAdmins");	    
			}		
		}
  	}
  	
  	public ActionForward removeAdminBtn(
  			ActionMapping mapping,
  			ActionForm form,
  			HttpServletRequest request,
  			HttpServletResponse response) throws Exception{
		ActionMessages messages=new ActionMessages();
		TelecentreForm telecentreForm = (TelecentreForm) form;
		TelecentreDAO dao= new TelecentreDAO();
		HttpSession session = request.getSession(true);
		telecentreForm.setTelRemoveAdminsReached(1);				//Set to 1 to enable going back to 'Manage Admins' page
		telecentreForm.setTeleAdminRemoveName("Select an Admin to Remove");
		telecentreForm.setTeleAdminNames(dao.getAdminNameLabelPairs());
		session.setAttribute("adminNames", telecentreForm.getTeleAdminNames());
		return mapping.findForward("removeAdmins");					
  	} 
  	
	//Sifiso Changes:Added:2016/12/14:Handles the onchange event of the drop-down list for Admin selection in the 'Remove Admins' jsp
  	public ActionForward handleAdminRemoveListBoxAction(
  			ActionMapping mapping,
  			ActionForm form,
  			HttpServletRequest request,
  			HttpServletResponse response) throws Exception{
  		TelecentreForm telecentreForm = (TelecentreForm) form;
  		TelecentreDAO dao=new TelecentreDAO();
  		if(telecentreForm.getTeleAdminRemoveName().trim().indexOf("Select")==-1){	//no occurance of 'Select'==>an admin WAS selected from drop-down
		  	telecentreForm.setAdminRemoveListTracker(1);
		  	telecentreForm.setTelRemoveAdminsReached(2);							//Set to 2 to enable going back to default drop-down
		  	String sakaiAdminUsrID=dao.getAdminSakaiID(telecentreForm.getTeleAdminRemoveName()); 
		  	if(telecentreForm.getAdminRemoveListTracker()==1){
		  		populateAdminInfo(telecentreForm,dao,sakaiAdminUsrID);
		  	}
	  		return mapping.findForward("removeAdmins");
  		}else{
	  			telecentreForm.setAdminRemoveListTracker(0);				//an admin WAS NOT selected
  		}
  		return mapping.findForward("removeAdmins");
  	} 
  	
  	//Sifiso Changes:Added:2016/12/21:Remove Admin page action mapping method for 'Submit' button
  	public ActionForward removeAdminSubmit(
  			ActionMapping mapping,
  			ActionForm form,
  			HttpServletRequest request,
  			HttpServletResponse response) throws Exception{ 
		ActionMessages messages=new ActionMessages();
		TelecentreForm telecentreForm = (TelecentreForm) form;
		TelecentreDAO dao = new TelecentreDAO(); 
		HttpSession session = request.getSession(true);
		telecentreForm.setTelAddAdminsReached(0);	
		
		String sakaiAdminUsrID=dao.getAdminSakaiID(telecentreForm.getTeleAdminRemoveName()); //get SakaiUserID of user to be removed
		int dbResult=dao.removeTelecentreAdmin(sakaiAdminUsrID);							 //remove user
		if(dbResult>0){
			messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("profile.manage.admin.remove.success")); 
			telecentreForm.setAdminRemoveListTracker(1);
			telecentreForm.setTelRemoveAdminsReached(2);				//Set to 2 to enable going back to default drop-down
			telecentreForm.setTeleAdminRemoveName("-1");
			telecentreForm.setTeleAdminNames(dao.getAdminNameLabelPairs());
			session.setAttribute("adminNames", telecentreForm.getTeleAdminNames());				
		}else{
			messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("profile.manage.admin.remove.failure"));
			telecentreForm.setAdminRemoveListTracker(1);
			telecentreForm.setTelRemoveAdminsReached(2);				
			telecentreForm.setTeleAdminRemoveName("-1");
			telecentreForm.setTeleAdminNames(dao.getAdminNameLabelPairs());
			session.setAttribute("adminNames", telecentreForm.getTeleAdminNames());	
		}		
		addErrors(request, messages);		//Add all success/failure messages when forwarding
		return mapping.findForward("manageAdmins");	
  	}
  	
	private boolean validateNumericInput(String intstr){
		      boolean isIntVal=true;
		      for(int x=0;x<intstr.length();x++){
		    	    if(!Character.isDigit((intstr.charAt(x)))){
		    	    	isIntVal=false;
		    	    }
		      }//for
		      return isIntVal;
	}
	
	//Sifiso Changes:Added:2016/07/04-Method to convert time to hours for insertion into database
	//we must only store time as hours in database so we convert whatever time spent by user to hours
	private double convertToHours(double time, String timeUnit){
		double timeHrs=0;
		if((timeUnit.equals("Seconds")) || (timeUnit.equals("Second"))){
			timeHrs=time/SECONDS_IN_HOUR;		//Convert seconds to hours
		}else{
			if((timeUnit.equals("Minutes")) || (timeUnit.equals("Minute"))){
				timeHrs=time/MINUTES_IN_HOUR;	//Convert Minutes to hours
			}else{
				if((timeUnit.equals("Hours")) || (timeUnit.equals("Hour"))){
					timeHrs=time;				
				}
			}
		}
		return timeHrs;
	}
	
	//Sifiso Changes:Added:2016/07/06-Method to round off time duration to two decimal places
	//we must display the time duration for telecentre session in three decimal places
	private double roundOfDuration(double decimalTime){
		double roundedTime=0.00;
		String roundStr=String.format("%.3f",decimalTime);		//Format the time to three decimal places as a String type
		roundedTime=Double.valueOf(roundStr);					//Convert the formatted string back to double type
		
		return roundedTime;
	}
	
	//Sifiso Changes:2016/07/28:Added method to list all 9 provinces for a <html:options/> drop-down list 
    public ArrayList generateProvinceList(){
    	String [] provinceArr={"Eastern Cape","Free State","Gauteng","KZN",
    						"Limpopo","Mpumalanga","N. Cape","North West","W. Cape"};
    	ArrayList<LabelValueBean> provinceList = new ArrayList<LabelValueBean>();
    	for(int i=0; i<provinceArr.length; i++){
    		provinceList.add(new org.apache.struts.util.LabelValueBean(provinceArr[i].toString(),provinceArr[i].toString()));
    	}
    	return provinceList;
    }
    
    //Sifiso Changes:2016/07/28:Added method to list 'Yes(Y)' or 'No(N)' selection options for a <html:options/> drop-down list
    public ArrayList generateCentreActive(){
    	ArrayList activeList = new ArrayList();
    	activeList.add(new org.apache.struts.util.LabelValueBean("Y","Y"));
    	activeList.add(new org.apache.struts.util.LabelValueBean("N","N"));
    	return activeList;
    }
    
    //Sifiso Changes:2016/07/28:Added method to check if an email format is valid
    public boolean emailFormatValid(String emailAddr){
    	if((emailAddr.contains("@"))&&(emailAddr.contains("."))){
    		if(emailAddr.lastIndexOf('.')>emailAddr.lastIndexOf('@')){  //'@' appears before the last '.' such that email format is some.one@domain.domainExt
    			return true;
    		}else{
    			return false;
    		}
    	}else{
    		return false;
    	}
    	
    }
    
    //Sifiso Changes:Added:2016/08/2:Added method to generate a random number telecentre code
    public String generateTeleCode(){
    	String codeSample="0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    	StringBuilder sb=new StringBuilder(6);
    	Random ran=new Random();
    	for(int i=0; i<6; i++){
    		sb.append(codeSample.charAt(ran.nextInt(codeSample.length())));
    	}
    	String telecode=sb.toString();		//telecentre code generated randomly
    	TelecentreDAO dao=new TelecentreDAO();
    	ArrayList compareTeleCodes = dao.getTeleCodes();
    	for(int i=0, j=compareTeleCodes.size(); i<j; i++){		
    		if(compareTeleCodes.get(i).toString().trim().equals(telecode)){	//compare the telecodes in DB with the one in 'telecode' string
    			StringBuilder sb2=new StringBuilder(6);			
    			for(int k=0; k<telecode.length(); k++){			
    				sb2.append(telecode.charAt(k));				//build new string equal to 'telecode' if match found
    			}
				sb2.reverse();  								//reverse characters of new string
				telecode=sb2.toString();						//new string in reverse
    		}
    	}
    	return telecode;
    }
    
    //Sifiso Changes:Added:2016/08/02:Method to check if Telecentre Name exists in database with the same province and same email as user input
    public boolean teleNameExists(String teleName, String province, String email){
    	boolean teleNameExists=false;
    	TelecentreDAO dao=new TelecentreDAO();
    	ArrayList compareTeleNames=dao.getTeleNames();
    	for(int i=0, j=compareTeleNames.size(); i<j; i++){
    		if(compareTeleNames.get(i).toString().trim().equals(teleName.trim())){ //search database to find centre name with similar name as user input
    			//check if the centre name found in database has the same province and the same email as user input
    			if(dao.teleNameExists(province, email)){
    				teleNameExists=true;
    			}
    		}
    	}
    	return teleNameExists;
    }
    
    //Sifiso Changes:Added:2016/08/12:Method to check if Telecentre Name exists in database with the name as user input
    public boolean teleNameExists(String teleName){
    	boolean teleNameExists=false;
    	TelecentreDAO dao=new TelecentreDAO();
    	ArrayList compareTeleNames=dao.getTeleNames();
    	for(int i=0, j=compareTeleNames.size(); i<j; i++){
    		if(compareTeleNames.get(i).toString().trim().equals(teleName.trim())){ //search database to find centre name with similar name as user input
    			teleNameExists=true;
    		}
    	}
    	return teleNameExists;
    }
    
    public boolean checkboxValue(boolean formCheckboxes){
    	boolean checkboxFlag;
    	if(formCheckboxes){
    		checkboxFlag=true;
    	}else{
    		checkboxFlag=false;
    	}
    	return checkboxFlag;
    }
    
    //Sifiso Changes:2016/08/12:Method gets all the current telecentre details as is in the database in order to display on update profile page
    public void populateTelecentreInfo(TelecentreForm form, TelecentreDAO dao, String currentUpdateName){
		ArrayList telecentreUpdateInfo=null;
		String centreNameExtract=currentUpdateName.trim();					//get user-selected centre name with leading and trailing spaces removed
		telecentreUpdateInfo=dao.getCentreUpdateInfo(centreNameExtract);	//get centre info from DB for display for user-selected centre name
		form.setTelecentreInfo(telecentreUpdateInfo);						//set the telecentre update info on TelecentreForm for use in jsp
		
		List tellInfo = form.getTelecentreInfo();							//get the list that contains the TelecentreDetails object
			TelecentreDetails telecentreDetails=new TelecentreDetails();	
			for(int i=0; i<tellInfo.size(); i++){
				//a safetrigger in case our telecentreInfo list is no longer size 1
				if(tellInfo.size()==1){
					telecentreDetails=(TelecentreDetails)tellInfo.get(i);	//cast the telecentreInfo list to telecentre details so we get the object
				}
			}
			form.setTelecentreCurrentName(telecentreDetails.getCentre()); 			//get the name from the object
			form.setTelecentreCurrentEmail(telecentreDetails.getEmail()); 			//get the email from the object
			form.setTelecentreCurrentProvince(telecentreDetails.getProvince()); 	//get the province from the object
			form.setTelecentreCurrentStatus(telecentreDetails.getStatus()); 		//get the active status from the object
			form.setTelecentreCurrentCode(telecentreDetails.getCode()); 			//get the code from the object
    }
    
    //Sifiso Changes:2016/12/14:Get all the current telecentre admin details as is in the database to display on 'Remove Admin' jsp
    public void populateAdminInfo(TelecentreForm form, TelecentreDAO dao, String sakaiUsrID){
		ArrayList teleAdminInfo=null;
		String sakaiIDExtract=sakaiUsrID.trim();							//get user-selected admin sakai user ID with leading and trailing spaces removed
		teleAdminInfo=dao.getAdminInfo(sakaiIDExtract);						//get admin info from DB for display for user-selected admin name
		form.setAdminInfo(teleAdminInfo);									//set the admin info on TelecentreForm for use in jsp
		
		List tellInfo = form.getAdminInfo();								//get the list that contains the TelecentreDetails object
			TelecentreDetails telecentreDetails=new TelecentreDetails();	
			for(int i=0; i<tellInfo.size(); i++){
				//a safetrigger in case our adminInfo list is no longer size 1
				if(tellInfo.size()==1){
					telecentreDetails=(TelecentreDetails)tellInfo.get(i);	//cast the telecentreInfo list to telecentre details so we get the object
				}
			}
			//form.setAdminCurrentUsrName(telecentreDetails.getStatus()); 	//get the username from the object
			//form.setAdminCurrentName(telecentreDetails.getCentre()); 		//get the name from the object
			//form.setAdminCurrentEmail(telecentreDetails.getEmail()); 		//get the email from the object
			//form.setAdminCurrentRegional(telecentreDetails.getProvince()); 	//get the regional flag from the object
    }
    
}
//nedbank personal loan :8001263414601