package za.ac.unisa.lms.tools.telecentre.forms;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;

import org.apache.struts.action.ActionForm;
import org.apache.struts.util.LabelValueBean;
import org.apache.struts.validator.ValidatorForm;

public class TelecentreForm extends ActionForm {
	  
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String today;
	private String telecentreName;
	private String telecentreCode;
	private String telecentreEmail;			      //Sifiso Changes:Added:2016/07/28-set/get the telecentre email address from create page
	private String telecentreStatus;		      //Sifiso Changes:Added:2016/07/28-set/get 'YES/NO' values for 'Active' status
	private String telecentreCurrentName="";	  //Sifiso Changes:Added:2016/08/12:current Telecentre details on update profile page
	private String telecentreCurrentEmail="";	  //Sifiso Changes:Added:2016/08/12:current Telecentre details on update profile page
	private String telecentreCurrentProvince="";  //Sifiso Changes:Added:2016/08/12:current Telecentre details on update profile page
	private String telecentreCurrentStatus="";	  //Sifiso Changes:Added:2016/08/12:current Telecentre details on update profile page
	private String telecentreCurrentCode="";	  //Sifiso Changes:Added:2016/08/12:current Telecentre details on update profile page
	private String telecentreRemoveName="";	  //Sifiso Changes:Added:2016/08/14:telecentre on drop-down list in 'Remove Telecentres' jsp
	private String telecentreRemoveMsg=""; 	  //Sifiso Changes:Added:2016/08/14:message to confirm indicate removal success or failure
	private String telecentreActivateName=""; //Sifiso Changes:Added:2016/08/14:telecentre on drop-down list in 'Activate/Deactivate Telecentre' jsp
	private String teleAdminAdUserName="";	  //Sifiso Changes:Added:2016/11/30:Admin username for textbox in 'Manage Admins' jsp
	private String teleAdminStaffName="";	  //Sifiso Changes:Added:2016/11/30:Staff Name for textbox in 'Manage Admins' jsp
	private String teleAdminEmail="";		  //Sifiso Changes:Added:2016/11/30:Staff Email for textbox in 'Manage Admins' jsp
	private String teleAdminRegional="";	  //Sifiso Changes:Added:2016/11/30:Regional Indicator for drop-down in 'Manage Admins' jsp
	private String teleAdminRemoveName="";	  //Sifiso Changes:Added:2016/12/14:Admin name on drop-down in 'Remove Admins' jsp
	private String code;
	private String fromDate,toDate;
	private String aboutTele;
	private List monthList,yearsList,periodList,toMonthsList,toYearsList,endDaysList,daysList;
	private String startTimeStamp;
	private String currentTotalTime;
	private String currentVisistDuration,visitPeriod;
	private int availableHrs,extensionHours,extensionHoursForCentre,availableHoursForTelecenter,startDay,startMin,startSec; //Sifiso Changes: added startSec variable: 2016/06/23
	private String latestStartTime;
	private String latestEndTime,monthYearString;
	private boolean check;
	private String month,monthForTelecenter,year,yearForTelecenter;
	private String[] months;
	private List telecentreInfo,telecenters,adminInfo,teleAdminNames;	//Sifiso Changes:Added:2016/12/07 and 2016/12/14:adminInfo and teleAdminNames
	private String currentYear;
	private String studentNr,studentName;
	private int newHourLimit,previousHourLimit,extStu;
	private boolean displayTeleList=false,visited=false;
	private int isAdmin=0,customvisittracker=0,trackerForExport=0,mainPageTracker=0,ecustomvisittracker=0,extConfirmPageReached=0,auditPageReached=0,
                 ecustomvisittracker2=0,backPointer=1,hoursSubmitted=0,
                 startHour=0,showTelecentreListTracker=0,			   
                 downloadExtendedHrs=0,displayPageReached=1,exportPageReached=0,extendPageReached=0,activeSessions=0,
                 isRegionalStaff=0;		//Sifiso Changes:2016/08/19:Added 'isRegionalStaff'
	private double stuAvailHrs=0,centreAvailHrs=0;			//Sifiso Changes:Changed:2016/06/30:Changed from int to double from above line 39
	private String province,centre,fromMonth,toMonth,fromYear,toYear,customDate="",endDay="",fromDay="";
	private String durationUnit;							//Sifiso Changes:Added:2016/06/28-Indicate whether duration is in minutes, hours or seconds
	//Sifiso Changes:Added below:2016/07/26-Below variables indicate whether any of the pages to manage telecentre details are reached
	private int telCreatePageReached=0,telUpdatePageReached=0,telRemovePageReached=0,telActivatePageReached=0, telUpdateListTracker=0,
			telRemoveListTracker=0,	telActivateListTracker=0, telManageAdminsReached=0, telAddAdminsReached=0, telRemoveAdminsReached=0,
			telViewAdminsReached=0, adminRemoveListTracker=0; 		
	private ArrayList provinceList;			//Sifiso Changes:Added:2016/07/28-set/get a list of 9 provinces for HTML drop-down;
	private ArrayList activeStatusList;		//Sifiso Changes:Added:2016/07/28-set/get list of selected 'YES/NO' values for 'Active' status
	private String telecode;				//Sifiso Changes:Added:2016/07/28-set/get the telecentre code auto-generated by the system
	//Sifiso Changes:Added below:2016/08/08-set/get the 'checked' status of the update checkboxes in the 'Update Profile' page
	private boolean telUpdateNameCheckBox=false,telUpdateEmailCheckBox=false,telUpdateProvinceCheckBox=false,telUpdateActiveCheckBox=false;
	private String currentUpdateEmail;		//Sifiso Changes:2016/08/14: Update Current Email
		
	public int getActiveSessions(){
	     return activeSessions;
    }
    public void setActiveSessions(int activeSessions){
	          this.activeSessions=activeSessions;
    }
	public int downloadExtendedHrs(){
		     return downloadExtendedHrs;
	}
	public void setDownloadExtendedHrs(int downloadExtendedHrs){
		          this.downloadExtendedHrs=downloadExtendedHrs;
	}
	public int exportPageReached(){
		return exportPageReached;
	}
	public void setExportPageReached(int exportPageReached){
		           this.exportPageReached=exportPageReached;
	}
	public int auditPageReached(){
		return auditPageReached;
	}
	public void setAuditPageReached(int auditPageReached){
		           this.auditPageReached=auditPageReached;
	}
	public int extendPageReached(){
		return extendPageReached;
	}
	public void setExtendPageReached(int extendPageReached){
		   this.extendPageReached=extendPageReached;
	}
	public int getStartMin(){
		 return startMin;
	}
	public void setStartMin(int startMin){
		 this.startMin=startMin;
	}
	//Sifiso Changes:Added:2016/06/23-Created methods to set and get the start seconds
	public int getStartSec(){				//Sifiso Changes: get the start seconds: used in 'TelecentreAction'
		 return startSec;
	}
	public void setStartSec(int startSec){	//Sifiso Changes: Set start seconds: used in 'TelecentreAction'
		 this.startSec=startSec;	
	}
	public void setStartDay(int startDay){
		    this.startDay=startDay;
	}
	public int getStartDay(){
		    return startDay;
	}
	public int getDisplayPageReached(){
	    	return  displayPageReached; 
	}
	public void setDisplayPageReached(int displayPageReached){
		  this.displayPageReached=displayPageReached;
	}
	public boolean isVisited(){
		 return visited;
	}
	public void setVisited(boolean visited){
		  this.visited=visited;
	}
	public void setStartHour(int startHour){
		   this.startHour=startHour;
	}
	public int getStartHour(){
		   return startHour;
	}
	public void setShowTelecentreListTracker(int showTelecentreListTracker){
		   this.showTelecentreListTracker=showTelecentreListTracker;
	}
	public int getShowTelecentreListTracker(){
		   return showTelecentreListTracker;
	}
	//Sifiso Changes:Changed below line:2016/06/30-Changed paramater type from int to double
	//public void setStuAvailHrs(int stuAvailHrs){
	public void setStuAvailHrs(double stuAvailHrs){		//Sifiso Changes
		this.stuAvailHrs=stuAvailHrs;
	}
	//Sifiso Changes:Changed below line:2016/06/30-Changed return type from int to double
	//public int getStuAvailHrs(){
	public double getStuAvailHrs(){						//Sifiso Changes
		     return stuAvailHrs;
	}
	//Sifiso Changes:Changed below line:2016/06/30-Changed paramater type from int to double
    //public void setCentreAvailHrs(int centreAvailHrs){
	public void setCentreAvailHrs(double centreAvailHrs){	//Sifiso Changes
    	    this.centreAvailHrs=centreAvailHrs;
    }
	//Sifiso Changes:Changed below line:2016/06/30-Changed return type from int to double
    //public int  getCentreAvailHrs(){
	public double  getCentreAvailHrs(){						//Sifiso Changes
	      return centreAvailHrs;
}
	public void setExtConfirmPageReached(int extConfirmPageReached){
		  this.extConfirmPageReached=extConfirmPageReached;
	}
	public int  getExtConfirmPageReached(){
		  return  extConfirmPageReached;
	}
	public int  getHoursSubmitted(){
		   return hoursSubmitted;
	}
	public void  setHoursSubmitted(int hoursSubmitted ){
		   this.hoursSubmitted=hoursSubmitted;
	}
	public void setStudentName(String studentName){
		 this.studentName=studentName;
	}
	public String getStudentName(){
		 return studentName;
	}
	public String  getCustomDate(){
		  return customDate;
	}
	public  void  setCustomDate(String  customDate){
		  this.customDate=customDate;
	}
	public String getFromMonth(){
		 return fromMonth;
	}
	public void setFromMonth(String fromMonth){
		   this.fromMonth=fromMonth;
	}
	public String getToMonth(){
		 return toMonth;
	}
	public void setToMonth(String toMonth){
		   this.toMonth=toMonth;
	}
	public String getFromYear(){
		 return fromYear;
	}
	public void setFromYear(String fromYear){
		   this.fromYear=fromYear;
	}
	public String getToYear(){
		 return toYear;
	}
	public void setToYear(String toYear){
		   this.toYear=toYear;
	}
	public int getMainPageTracker(){
		return mainPageTracker;
	}
	public void setMainPageTracker(int mainPageTracker){
		this.mainPageTracker=mainPageTracker;
	}
	public String getMonthYearString(){
		 return monthYearString;
	}
	public void setMonthYearString(String monthYearString){
		 this.monthYearString=monthYearString;
	}
	public void setTrackerForExport(int trackerForExport){
		  this.trackerForExport=trackerForExport;
	}
	public int getTrackerForExport(){
		return trackerForExport;
	}
	//Sifiso Comments:Commented:2016/07/28-Getter method to return the value selected by user from drop-down
	public String getProvince(){
		 return province;
	}
	//Sifiso Changes:Added:2016/07/29-Getter method to set the value selected by user from drop-down[THIS WAS NEVER ADDED BEFORE]
	public void setProvince(String province){
		 this.province=province;
	}
	public String getCentre(){
		   return this.centre;
	}
	public int  getCustomvisittracker(){
		  return customvisittracker;
	}
	public void setCustomvisittracker(int customvisittracker){
            this.customvisittracker=customvisittracker;
	}
	public int  getEcustomvisittracker(){
		  return ecustomvisittracker;
	}
	public void setEcustomvisittracker2(int ecustomvisittracker2){
        this.ecustomvisittracker2=ecustomvisittracker2;
	}
	public int  getEcustomvisittracker2(){
		  return ecustomvisittracker2;
	}
	public  int getBackPointer(){
		return backPointer;
	}
	public void setBackPointer(int backPointer){
		    this.backPointer=backPointer;
	}
	public void setEcustomvisittracker(int ecustomvisittracker){
          this.ecustomvisittracker=ecustomvisittracker;
	}
	
	public void setPeriodList(ArrayList periodList){
		          this.periodList=periodList;
	}
	public List getPeriodList(){
	      return periodList;
}
	public void  setIsAdmin(int isAdmin){
		   this.isAdmin=isAdmin;
	}
	public int getIsAdmin(){
		return isAdmin;
	}
	public void setDisplayTeleList(boolean displayTeleList){
		     this.displayTeleList=displayTeleList;
	}
	public boolean  getDisplayTeleList(){
	     return displayTeleList;
    }
	public void setTelecenters(ArrayList telecenters){
		      this.telecenters=telecenters;
	}
	public List getTelecenters(){
	      return telecenters;
    }
	public void setVisitPeriod(String visitPeriod){
		    this.visitPeriod = visitPeriod;
	}
	public String getVisitPeriod(){
		    return visitPeriod;
	}
	public String getFromDate(){
		  return fromDate;
	}
	public void setFromDate(String fromDate){
		     this.fromDate=fromDate;
	}
	public String getToDate(){
		  return toDate;
	}
	public void setToDate(String toDate){
		     this.toDate=toDate;
	}
   	public List getToMonthList(){
			 return toMonthsList;
	}
	public void setToMonthList(List toMonthList){
	       this.toMonthsList=toMonthList;
    }
    public List getToYearsList(){
        	    return toYearsList;
    }
    public void setToYearsList(ArrayList toYearsList){
      this.toYearsList=toYearsList;
    }
    public void setEndDaysList(ArrayList endDaysList){
		this.endDaysList=endDaysList;
	}
	public List getEndDaysList(){
		 return endDaysList;
	}
	public void setDaysList(ArrayList daysList){
		this.daysList=daysList;
	}
	public List getDaysList(){
		 return daysList;
	}
	 public List getMonthList(){
		 return monthList;
	 }
	 public void setMonthList(List monthList){
		 this.monthList=monthList;
	 }
	 public List getYearsList(){
	      	    return yearsList;
	 }
	public void setYearsList(ArrayList yearsList){
	    this.yearsList=yearsList;
	}
	public void setMonthYearString(){
		   monthYearString=""+month+"/"+year;
	}
    public int getPreviousHourLimit(){
    	 return previousHourLimit;
    }
    public void setPreviousHourLimit(int previousHourLimit){
    	   this.previousHourLimit=previousHourLimit;
    }
    public int getNewHourLimit(){
   	   return newHourLimit;
    }
    public void setExtStu(int extStu){
    	this.extStu=extStu;
    }
    public int getExtStu(){
    	return extStu;
    }
   public void setNewHourLimit(int newHourLimit){
   	   this.newHourLimit=newHourLimit;
   }
	public String getCurrentYear() {
		return currentYear;
	}
	public String getStudentNr(){
		 return studentNr;
	}
	public void setStudentNr(String studentNr){
		   this.studentNr=studentNr;
	}
   
    public void setExtensionHours(int extensionHours ){
    	this.extensionHours=extensionHours;
    }
    public int getExtensionHours(){
	       return extensionHours;
    }
    public void setExtensionHoursForCentre(int extensionHoursForCentre ){
    	this.extensionHoursForCentre=extensionHoursForCentre;
    }
    public int getExtensionHoursForCentre(){
	       return extensionHoursForCentre;
    }
    public void setCurrentYear(String currentYear) {
		this.currentYear = currentYear;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year){
		this.year = year;
	}
	public String getYearForTelecenter() {
		return yearForTelecenter;
	}
	public void setYearForTelecenter(String yearForTelecenter) {
		this.yearForTelecenter = yearForTelecenter;
	}
	public List getTelecentreInfo() {
		return telecentreInfo;
	}

	public void setTelecentreInfo(List telecentreInfo) {
		this.telecentreInfo = telecentreInfo;
	}
 
	public String[] getMonths() {
		return months;
	}
	public void setMonthsList(ArrayList monthList) {
		this.monthList = monthList;
	}
    public String getMonth() {
		return month;
	}
    public void setMonth(String month) {
		this.month = month;
	}
	public String getMonthForTelecenter() {
		return monthForTelecenter;
	}
	public void setMonthForTelecenter(String monthForTelecenter) {
		this.monthForTelecenter = monthForTelecenter;
	}
	public void setMonths(String[] months) {
		this.months = months;
	}

	public String getAboutTele() {
		return aboutTele;
	}
	public void setAboutTele(String aboutTele) {
		this.aboutTele = aboutTele;
	}
	public boolean isCheck() {
		return check;
	}

	public void setCheck(boolean check) {
		this.check = check;
	}

	public String getLatestStartTime() {
		return latestStartTime;
	}

	public void setLatestStartTime(String latestStartTime) {
		this.latestStartTime = latestStartTime;
	}

	public String getLatestEndTime() {
		return latestEndTime;
	}

	public void setLatestEndTime(String latestEndTime) {
		this.latestEndTime = latestEndTime;
	}

	public int getAvailableHrs() {
		return availableHrs;
	}

	public void setAvailableHrs(int availableHrs) {
		this.availableHrs = availableHrs;
	}
	public String getCurrentVisistDuration() {
		return currentVisistDuration;
	}

	public void setCurrentVisistDuration(String currentVisistDuration) {
		this.currentVisistDuration = currentVisistDuration;
	}

	public String getStartTimeStamp() {
		return startTimeStamp;
	}

	public String getCurrentTotalTime() {
		return currentTotalTime;
	}

	public void setCurrentTotalTime(String currentTotalTime) {
		this.currentTotalTime = currentTotalTime;
	}

	public void setStartTimeStamp(String startTimeStamp) {
		this.startTimeStamp = startTimeStamp;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	private String userId;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getTelecentreName() {
		return telecentreName;
	}

	public void setTelecentreName(String telecentreName) {
		this.telecentreName = telecentreName;
	}

	public String getTelecentreCode() {
		return telecentreCode;
	}

	public void setTelecentreCode(String telecentreCode) {
		this.telecentreCode = telecentreCode;
	}

	public String getToday() {
		return today;
	}

	public void setToday(String today) {
		this.today = today;
	}
	 public String getFromDay(){
	    return fromDay;
    }
    public void setFromDay(String fromDay){
	    this.fromDay=fromDay;
    }    
    public String getEndDay(){
	    return endDay;
    }
    public void setEndDay(String endDay){
    	   this.endDay=endDay;
    }
    //Sifiso Changes:Added:2016/06/28-Setter method for durationUnit
    public void setDurationUnit(String durationUnit){
    	this.durationUnit = durationUnit;
    }
    //Sifiso Changes:Added:2016/06/28-Getter method for durationUnit
    public String getDurationUnit(){
    	return durationUnit;
    }
    //Sifiso Changes:Added:2016/07/26-Setter method for telCreatePageReached to indicate if the 'Create Profile' page is reached
    public void setTelCreatePageReached(int telCreatePageReached){
    	this.telCreatePageReached=telCreatePageReached;
    }
    //Sifiso Changes:Added:2016/07/26-Getter method for telCreatePageReached to return when the 'Create Profile' page is reached
    public int getTelCreatePageReached(){
    	return telCreatePageReached;
    }
    //Sifiso Changes:Added:2016/07/26-Setter method for telUpdatePageReached to indicate if the 'Update Profile' page is reached
    public void setTelUpdatePageReached(int telUpdatePageReached){
    	this.telUpdatePageReached=telUpdatePageReached;
    }
    //Sifiso Changes:Added:2016/07/26-Getter method for telUpdatePageReached to return when the 'Update Profile' page is reached
    public int getTelUpdatePageReached(){
    	return telUpdatePageReached;
    }
    //Sifiso Changes:Added:2016/07/26-Setter method for telRemovePageReached to indicate if the 'Remove Telecentre' page is reached
    public void setTelRemovePageReached(int telRemovePageReached){
    	this.telRemovePageReached=telRemovePageReached;
    }
    //Sifiso Changes:Added:2016/07/26-Getter method for telRemovePageReached to return when the 'Remove Telecentre' page is reached
    public int getTelRemovePageReached(){
    	return telRemovePageReached;
    }
    //Sifiso Changes:Added:2016/07/26-Setter method for telActivatePageReached to indicate if the 'Activate/Deactivate Code' page is reached
    public void setTelActivatePageReached(int telActivatePageReached){
    	this.telActivatePageReached=telActivatePageReached;
    }
    //Sifiso Changes:Added:2016/07/26-Getter method for telActivatePageReached to return when the 'Activate/Deactivate Code' page is reached
    public int getTelActivatePageReached(){
    	return telActivatePageReached;
    }
    //Sifiso Changes:Added:2016/07/27-Setter method to populate provinceList 9 provinces in drop-down
    public void setProvinceList(ArrayList provinceList){
    	this.provinceList=provinceList;
    }
    //Sifiso Changes:Added:2016/07/27-Getter method to return and display 9 provinces in drop-down
    public ArrayList getProvinceList(){
    	return provinceList;
    }
    //Sifiso Changes:Added:2016/07/28-Setter method to populate Y or N for drop-down
    public void setActiveStatusList(ArrayList activeStatusList){
    	this.activeStatusList=activeStatusList;
    }
   //Sifiso Changes:Added:2016/07/28-Getter method to return Y or N from drop-down display
    public ArrayList getActiveStatusList(){
    	return activeStatusList;
    }
    //Sifiso Changes:Added:2016/07/28-Setter method to set the value selected by user (Y or N) on drop-down
    public void setTelecentreStatus(String telecentreStatus){
    	this.telecentreStatus=telecentreStatus;
    }
    //Sifiso Changes:Added:2016/07/28-Getter method to return the value selected by user (Y or N) from drop-down
    public String getTelecentreStatus(){
    	return telecentreStatus;
    }

    //Sifiso Changes:Added:2016/07/28-Setter method to set the telecentre email from create telecentre page
    public void setTelecentreEmail(String telecentreEmail){
    	this.telecentreEmail=telecentreEmail;
    }
   //Sifiso Changes:Added:2016/07/28-Getter method to return the telecentre email entered in create telecentre page
    public String getTelecentreEmail(){
    	return telecentreEmail;
    }
    //Sifiso Changes:Added:2016/07/28-Setter method to set the telecentre code auto-generated by the system
	public void setTelecode(String telecode){
		this.telecode=telecode;
	}
	//Sifiso Changes:Added:2016/07/28-Getter method to return the telecentre code auto-generated by the system
	public String getTelecode(){
		return telecode;
	}
	//Sifiso Changes:2016/08/04:
	public void setTelUpdateListTracker(int telUpdateListTracker){
		this.telUpdateListTracker=telUpdateListTracker;
	}
	//Sifiso Changes:2016/08/04:
	public int getTelUpdateListTracker(){
		return telUpdateListTracker;
	}
	//Sifiso Changes:2016/08/08:
	public void setTelUpdateNameCheckBox(boolean telUpdateNameCheckBox){
		this.telUpdateNameCheckBox=telUpdateNameCheckBox;
	}
	//Sifiso Changes:2016/08/08:
	public boolean getTelUpdateNameCheckBox(){
		return telUpdateNameCheckBox;
	}
	//Sifiso Changes:2016/08/08:
	public void setTelUpdateEmailCheckBox(boolean telUpdateEmailCheckBox){
		this.telUpdateEmailCheckBox=telUpdateEmailCheckBox;
	}
	//Sifiso Changes:2016/08/08:
	public boolean getTelUpdateEmailCheckBox(){
		return telUpdateEmailCheckBox;
	}
	//Sifiso Changes:2016/08/08:
	public void setTelUpdateProvinceCheckBox(boolean telUpdateProvinceCheckBox){
		this.telUpdateProvinceCheckBox=telUpdateProvinceCheckBox;
	}
	//Sifiso Changes:2016/08/08:
	public boolean getTelUpdateProvinceCheckBox(){
		return telUpdateProvinceCheckBox;
	}
	//Sifiso Changes:2016/08/08:
	public void setTelUpdateActiveCheckBox(boolean telUpdateActiveCheckBox){
		this.telUpdateActiveCheckBox=telUpdateActiveCheckBox;
	}
	//Sifiso Changes:2016/08/08:
	public boolean getTelUpdateActiveCheckBox(){
		return telUpdateActiveCheckBox;
	}
	//Sifiso Changes:2016/08/12:
	public void setTelecentreCurrentName(String telecentreCurrentName){
		this.telecentreCurrentName=telecentreCurrentName;
	}
	//Sifiso Changes:2016/08/12:
	public String getTelecentreCurrentName(){
		return telecentreCurrentName;
	}
	//Sifiso Changes:2016/08/12:
	public void setTelecentreCurrentEmail(String telecentreCurrentEmail){
		this.telecentreCurrentEmail=telecentreCurrentEmail;
	}
	//Sifiso Changes:2016/08/12:
	public String getTelecentreCurrentEmail(){
		return telecentreCurrentEmail;
	}
	//Sifiso Changes:2016/08/12:
	public void setTelecentreCurrentProvince(String telecentreCurrentProvince){
		this.telecentreCurrentProvince=telecentreCurrentProvince;
	}
	//Sifiso Changes:2016/08/12:
	public String getTelecentreCurrentProvince(){
		return telecentreCurrentProvince;
	}
	//Sifiso Changes:2016/08/12:
	public void setTelecentreCurrentStatus(String telecentreCurrentStatus){
		this.telecentreCurrentStatus=telecentreCurrentStatus;
	}
	//Sifiso Changes:2016/08/12:
	public String getTelecentreCurrentStatus(){
		return telecentreCurrentStatus;
	}
	//Sifiso Changes:2016/08/12:
	public void setTelecentreCurrentCode(String telecentreCurrentCode){
		this.telecentreCurrentCode=telecentreCurrentCode;
	}
	//Sifiso Changes:2016/08/12:
	public String getTelecentreCurrentCode(){
		return telecentreCurrentCode;
	}
	//Sifiso Changes:2016/08/14:
	public void setTelRemoveListTracker(int telRemoveListTracker){
		this.telRemoveListTracker=telRemoveListTracker;
	}
	//Sifiso Changes:2016/08/14:
	public int getTelRemoveListTracker(){
		return telRemoveListTracker;
	}
	//Sifiso Changes:2016/08/14:
	public void setTelecentreRemoveName(String telecentreRemoveName){
		this.telecentreRemoveName=telecentreRemoveName;
	}
	//Sifiso Changes:2016/08/14:
	public String getTelecentreRemoveName(){
		return telecentreRemoveName;
	}
	//Sifiso Changes:2016/08/14:
	public void setTelecentreRemoveMsg(String telecentreRemoveMsg){
		this.telecentreRemoveMsg=telecentreRemoveMsg;
	}
	//Sifiso Changes:2016/08/14:
	public String getTelecentreRemoveMsg(){
		return telecentreRemoveMsg;
	}
	//Sifiso Changes:2016/08/14:
	public void setTelecentreActivateName(String telecentreActivateName){
		this.telecentreActivateName=telecentreActivateName;
	}
	//Sifiso Changes:2016/08/14:
	public String getTelecentreActivateName(){
		return telecentreActivateName;
	}
	//Sifiso Changes:2016/08/14:
	public void setTelActivateListTracker(int telActivateListTracker){
		this.telActivateListTracker=telActivateListTracker;
	}
	//Sifiso Changes:2016/08/14:
	public int getTelActivateListTracker(){
		return telActivateListTracker;
	}
	//Sifiso Changes:2016/08/14:
	public void setIsRegionalStaff(int isRegionalStaff){
		this.isRegionalStaff=isRegionalStaff;
	}
	//Sifiso Changes:2016/08/14:
	public int getIsRegionalStaff(){
		return isRegionalStaff;
	}
	//Sifiso Changes:2016/08/14:
    public String getCurrentUpdateEmail(){
	    return currentUpdateEmail;
    }
    public void setCurrentUpdateEmail(String currentUpdateEmail){
	    this.currentUpdateEmail=currentUpdateEmail;
    }
	
    //Sifiso Changes:Added:2016/11/30-Setter method for telManageAdminsReached to indicate if the 'Manage Admins' page is reached
    public void setTelManageAdminsReached(int telManageAdminsReached){
    	this.telManageAdminsReached=telManageAdminsReached;
    }
    //Sifiso Changes:Added:2016/11/30-Getter method for telManageAdminsReached to return when the 'Manage Admins' page is reached
    public int getTelManageAdminsReached(){
    	return telManageAdminsReached;
    }
    
    //Sifiso Changes:Added:2016/12/06-Setter method for telViewAdminsReached to indicate if the 'View Admins' page is reached
    public void setTelViewAdminsReached(int telViewAdminsReached){
    	this.telViewAdminsReached=telViewAdminsReached;
    }
    //Sifiso Changes:Added:2016/12/06-Getter method for telViewAdminsReached to return when the 'View Admins' page is reached
    public int getTelViewAdminsReached(){
    	return telViewAdminsReached;
    }
    
    //Sifiso Changes:Added:2016/11/30-Setter method for teleAdminAdUserName
    public void setTeleAdminAdUserName(String teleAdminAdUserName){
    	this.teleAdminAdUserName=teleAdminAdUserName;
    }
    //Sifiso Changes:Added:2016/11/30-Getter method for teleAdminAdUserName
    public String getTeleAdminAdUserName(){
    	return teleAdminAdUserName;
    }
    
    //Sifiso Changes:Added:2016/11/30-Setter method for teleAdminStaffName
    public void setTeleAdminStaffName(String teleAdminStaffName){
    	this.teleAdminStaffName=teleAdminStaffName;
    }
    //Sifiso Changes:Added:2016/11/30-Getter method for teleAdminStaffName
    public String getTeleAdminStaffName(){
    	return teleAdminStaffName;
    }
    
    //Sifiso Changes:Added:2016/11/30-Setter method for teleAdminEmail
    public void setTeleAdminEmail(String teleAdminEmail){
    	this.teleAdminEmail=teleAdminEmail;
    }
    //Sifiso Changes:Added:2016/11/30-Getter method for teleAdminEmail
    public String getTeleAdminEmail(){
    	return teleAdminEmail;
    }
    
    //Sifiso Changes:Added:2016/11/30-Setter method for teleAdminRegional
    public void setTeleAdminRegional(String teleAdminRegional){
    	this.teleAdminRegional=teleAdminRegional;
    }
    //Sifiso Changes:Added:2016/11/30-Getter method for teleAdminRegional
    public String getTeleAdminRegional(){
    	return teleAdminRegional;
    }
    
    //Sifiso Changes:Added:2016/11/30-Setter method for telAddAdminsReached to indicate if the 'Add Admins' page is reached
    public void setTelAddAdminsReached(int telAddAdminsReached){
    	this.telAddAdminsReached=telAddAdminsReached;
    }
    //Sifiso Changes:Added:2016/11/30-Getter method for telAddAdminsReached to return when the 'Add Admins' page is reached
    public int getTelAddAdminsReached(){
    	return telAddAdminsReached;
    }
    
    //Sifiso Changes:Added:2016/11/30-Setter method for telRemoveAdminsReached to indicate if the 'Remove Admins' page is reached
    public void setTelRemoveAdminsReached(int telRemoveAdminsReached){
    	this.telRemoveAdminsReached=telRemoveAdminsReached;
    }
    //Sifiso Changes:Added:2016/11/30-Getter method for telRemoveAdminsReached to return when the 'Remove Admins' page is reached
    public int getTelRemoveAdminsReached(){
    	return telRemoveAdminsReached;
    }
        
	public List getAdminInfo() {
		return adminInfo;
	}

	public void setAdminInfo(List adminInfo) {
		this.adminInfo = adminInfo;
	}
	
	//for use on 'Remove Admins' jsp
	public void setAdminRemoveListTracker(int adminRemoveListTracker){
		this.adminRemoveListTracker=adminRemoveListTracker;
	}
	//for use on 'Remove Admins' jsp
	public int getAdminRemoveListTracker(){
		return adminRemoveListTracker;
	}
	//Sifiso Changes:2016/12/14://for use on 'Remove Admins' jsp
	public void setTeleAdminRemoveName(String teleAdminRemoveName){
		this.teleAdminRemoveName=teleAdminRemoveName;
	}
	//Sifiso Changes:2016/12/14://for use on 'Remove Admins' jsp
	public String getTeleAdminRemoveName(){
		return teleAdminRemoveName;
	} 
	//Sifiso Changes:2016/12/14://for use on 'Remove Admins' jsp
	public void setTeleAdminNames(ArrayList teleAdminNames){
	      this.teleAdminNames=teleAdminNames;
	}
	//Sifiso Changes:2016/12/14://for use on 'Remove Admins' jsp
	public List getTeleAdminNames(){
	    return teleAdminNames;
	}
	
}
