package za.ac.unisa.lms.tools.telecentre.forms;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import za.ac.unisa.lms.tools.telecentre.dao.TelecentreDAO;
import za.ac.unisa.lms.tools.telecentre.dao.TelecentreDetails;
public class TelecentreVisits {
	public void getTelecentreVisits(TelecentreForm telecentreForm){
    	// gets visits by all students to either a particular centre or all centres
    	//if  TelecentreCode is All we get all visits by students to all centres
    	//if the telecentreCode is is for a specific centre we get visits by students to that centre
    	//we get fromDate  and  endDate from the telecentreForm
    	    String fromDate=getStartDateForVisits(telecentreForm);
    	    String endDate=getEndDateForVisits(telecentreForm);
    	    telecentreForm.setFromDate(fromDate);
    	    telecentreForm.setToDate(endDate);
    	    setVisits(fromDate,endDate,telecentreForm);
    }
    public void getTelecentreVisits(String  today,TelecentreForm telecentreForm){
    	String fromDate=getStartDateForVisits(telecentreForm);
	    String endDate=today;
	    telecentreForm.setFromDate(fromDate);
	    telecentreForm.setToDate(endDate);
	    setVisits(fromDate,endDate,telecentreForm);
	}
    public void getTelVisitsForWorkBook(TelecentreForm telecentreForm){
    	// gets visits by all students to either a particular centre or all centres
    	//if  TelecentreCode is All we get all visits by students to all centres
    	//if the telecentreCode is is for a specific centre we get visits by students to that centre
    	//we get fromDate  and  endDate from the telecentreForm
    	    String fromDate=getStartDateForVisits(telecentreForm);
    	    String endDate=getEndDateForVisits(telecentreForm);
    	    telecentreForm.setFromDate(fromDate);
    	    telecentreForm.setToDate(endDate);
    	    setVisitsForWorkBook(fromDate,endDate,telecentreForm);
    }
    private void getTelecentreVisitsForWorkBook(TelecentreForm telecentreForm,TelecentreDAO dao){
                ArrayList telecentres=dao.getTelecentreVisits("","","","");
                removeVisitsInSessionNow(telecentres);
                telecentreForm.setTelecentreInfo(telecentres);
    }
    private void getTelecentreVisitsForWorkBook(TelecentreForm telecentreForm,dateUtil du,TelecentreDAO dao){
        String startDate=du.getStartDateFromString(telecentreForm.getVisitPeriod().trim());
        ArrayList telecentres=dao.getTelecentreVisits("",startDate,du.todayDateStr(),"");
        removeVisitsInSessionNow(telecentres);
        telecentreForm.setTelecentreInfo(telecentres);
    }
    public void getTelVisitsForWorkBook(String  today,TelecentreForm telecentreForm){
    	String fromDate=getStartDateForVisits(telecentreForm);
	    String endDate=today;
	    telecentreForm.setFromDate(fromDate);
	    telecentreForm.setToDate(endDate);
	    setVisitsForWorkBook(fromDate,endDate,telecentreForm);
	}
    public void setVisitsStudent(String fromDate,String endDate,TelecentreForm telecentreForm){
    	//set the telecentre visits in the form(telecentreForm),for a student vists to all centres
    	
    	           ArrayList telecentres=null;
	               TelecentreDAO dao = new TelecentreDAO();
	               String studentNr=telecentreForm.getStudentNr();
	               telecentres=dao.getTelecentreVisits(studentNr,fromDate,endDate,""); 
                   removeVisitsInSessionNow(telecentres);
	               telecentreForm.setTelecentreInfo(telecentres);
    }
    public void setVisits(String fromDate,String endDate,TelecentreForm telecentreForm){
    	//set the telecentre visits in the form(telecentreForm),if studentNr="", it get visits by all students
    	//else it choose visits bby the given student and
    	//if the telentreCode is "All" it get visits to all telecentres else it choose those from a given telentres
    	           ArrayList telecentres=null;
	               TelecentreDAO dao = new TelecentreDAO();
	               String studentNr=telecentreForm.getStudentNr();
	               if(telecentreForm.exportPageReached()==1)
	            	     studentNr="";
	               if(telecentreForm.getCode().equals("All")){
                       telecentres=dao.getTelecentreVisits(studentNr,fromDate,endDate,""); 
                   }else{
                         telecentres=dao.getTelecentreVisits(studentNr,fromDate,endDate,telecentreForm.getCode());
                   }
	               removeVisitsInSessionNow(telecentres);
	               telecentreForm.setTelecentreInfo(telecentres);
    }
    public void setVisitsForWorkBook(String fromDate,String endDate,TelecentreForm telecentreForm){
    	//gets all visits for the given dates
    	      ArrayList telecentres=null;
              TelecentreDAO dao = new TelecentreDAO();
              telecentres=dao.getTelecentreVisits("",fromDate,endDate,""); 
              removeVisitsInSessionNow(telecentres);
              telecentreForm.setTelecentreInfo(telecentres);
    }
    public String getStartDateForVisits(TelecentreForm telecentreForm){
    	// uses the variables set for fromDay,fromMonth and fromYear to form a date string 
    	//and then make a date out of the resultant string
	      dateUtil du=new dateUtil();
          String fromDateStr=telecentreForm.getFromYear()+"-"+telecentreForm.getFromMonth()
                             +"-"+telecentreForm.getFromDay();
          return fromDateStr; 
    }
    public String getEndDateForVisits(TelecentreForm telecentreForm){
      	  // uses the variables set for endDay,toMonth and toYear in the telecentreForm to form a date string 
      	  //and then make a date out of the resultant string
	      dateUtil du=new dateUtil();
          String toDateStr=telecentreForm.getToYear()+"-"+telecentreForm.getToMonth()
                           +"-"+telecentreForm.getEndDay();
        return toDateStr; 
    }
    protected List getWorkBookData(TelecentreForm telecentreForm,dateUtil du){
    	                      setDisplayDataForWorkBook(telecentreForm);
    				return  telecentreForm.getTelecentreInfo();
	}
    protected void getWorkBookDataGivenMonthAndYear(TelecentreForm telecentreForm,dateUtil du,TelecentreDAO dao){
    	           String toMonth=telecentreForm.getToMonth();
	               String toYear =telecentreForm.getToYear();
	               String fromDate=telecentreForm.getFromYear()+"-"+telecentreForm.getFromMonth()+"-01";
	  		       String endDate=toYear+"-"+toMonth+"-"+du.getEndDayForMonth(Integer.parseInt(toMonth),Integer.parseInt(toYear));
	  		       ArrayList telecentres=dao.getTelecentreVisits("",fromDate,endDate,"");
                   removeVisitsInSessionNow(telecentres);
                   telecentreForm.setTelecentreInfo(telecentres);
    }
    public void removeVisitsInSessionNow(ArrayList telecentres){
    	dateUtil dateutil=new dateUtil();
		if(telecentres!=null){
			ArrayList telecentresDet=new ArrayList();
	    	for(int x=0;x<telecentres.size();x++){
			   TelecentreDetails telDet= (TelecentreDetails)telecentres.get(x);
			   String endTimeStr=telDet.getTimeOut().trim();
			 if(dateutil.dateOnly().equals(telDet.getTodate().trim())){
			      if(!dateutil.givenTimeGreaterSystime(endTimeStr)){
				       telecentresDet.add(telDet);
			     }//if
			 }else{
				 telecentresDet.add(telDet);
			 }
		    }//for
	    	telecentres.clear();
	    	telecentres.addAll(telecentresDet);
		   }
		
	}
    public void setDisplayData(TelecentreForm telecentreForm){
    	//gets telecentre visits for displaying for all students
    	        dateUtil du =new dateUtil();
    	        telecentreForm.setStudentNr("");
                if(telecentreForm.getVisitPeriod().trim().equals("All")){
                       	setVisits("","",telecentreForm);
                }else if(telecentreForm.getVisitPeriod().trim().equals("Last 7 Days")||
                		telecentreForm.getVisitPeriod().trim().equals("Last 30 Days")){
                           	String startDate=du.getStartDateFromString(telecentreForm.getVisitPeriod().trim());
                        	setVisits(startDate,du.todayDateStr(),telecentreForm);
                        }else{
                              getTelecentreVisits(telecentreForm);
                        }
                
    }
    public void setDisplayDataForWorkBook(TelecentreForm telecentreForm){
    	    //gets telecentre visits for displaying for all students
    	        TelecentreDAO dao=new TelecentreDAO();
    	        dateUtil du =new dateUtil();
    	        if(telecentreForm.getVisitPeriod().trim().equals("All")){
    	        	       getTelecentreVisitsForWorkBook(telecentreForm,dao); 
                }else{
                        if(telecentreForm.getCustomvisittracker()==0){
                        	getTelecentreVisitsForWorkBook(telecentreForm,du,dao);
                        	
                        }else if(telecentreForm.getCustomvisittracker()==1){
                        	     getTelVisitsForWorkBook(telecentreForm);
                        }else{
                           	  getWorkBookDataGivenMonthAndYear(telecentreForm,du,dao);
                        }
                }
    }
    public void setDisplayDataForStu(TelecentreForm telecentreForm){
    	//gets telecentre visits for dispaying for a particular student
    	              dateUtil du =new dateUtil();
    	              String year=telecentreForm.getFromYear();
    	              String month=telecentreForm.getFromMonth();
    	              String fromDate=year+"-"+month+"-01";
    	              String endDate=year+"-"+month+"-"+du.getEndDayForMonth(Integer.parseInt(month),Integer.parseInt(year));
   	  		          setVisitsStudent(fromDate,endDate,telecentreForm);
     }
   
}
