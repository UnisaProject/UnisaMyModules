package za.ac.unisa.lms.tools.cronjobs.forms.model;

import java.util.List;
import za.ac.unisa.lms.tools.cronjobs.forms.model.PlacementListRecord;
import za.ac.unisa.lms.tools.cronjobs.forms.model.School;
import za.ac.unisa.lms.tools.cronjobs.forms.model.Supervisor;
import za.ac.unisa.lms.tools.cronjobs.forms.model.Coordinator;
public class SupervisorMailBody {
	          
	private String firstPartOfEmailMessage(String supName){
        String emailMessage="Dear  "+supName+"<br><br>"+
        "The   Teaching Practice Unit  would like to thank you for the support you are giving to our "+ 
                   "students, and also  for your dedication and hard work in this project: Teaching Practice Supervision."+
                "Please find the list of students attached.<br><br>"+
                   "Please take note of the following:<br>"+
		  "<ul><li>	Contact your students prior to the visit at the school to confirm dates and times suitable for both of you.</li>"+
		  "<li>Contact Teaching Practice Coordinator in case you have to travel more than 80 Km.</li>"+
		  "<li>	Please plan and geographically locate your route to ensure that you visit at least 2 or more students per day.</li>"+
		  "<li>Always find out how many students are practicing at the school or area you will be visiting, so that you may arrange to visit them all if possible.</li>"+
		  "<li>	Write the correct kilometres when completing your claim forms  as the Teaching Practice Coordinator will check them before payment is made.</li>"+
		  "<li>	Contact the Teaching Practice Coordinator if you have  problems with any student/s.</li>"+
		  "<li>	Please ensure that you have signed your claim forms where applicable  before submission.</li>"+
		  "<li>	Please ensure clear and neat hand writing in all forms to be  submitted.</li>"+
		  "<li>	For your claims to be timeously paid,<b> (processed by the 7th  of the next month)</b> please insure that the claim forms are recieved by the TP Office  on or before the <b>25th</b> of current month.</li>"+
		  "<li>	Inform the Teaching Practice office in time if you need assessment forms.</li>"+
		  "<li>	Please inform the Teaching Practice Coordinator on time if you will not be able to assess all the students due to unforeseen circumstances  so that we can re-allocate them to other supervisors.</li>"+
		  "<br><b>Below is a list of students allocated to you for school visits.</b>";
        return emailMessage;
   }
   private String getStudentsList(int supervisorCode)throws Exception{
	                 String  lastPartOfEmailBody="";
                          lastPartOfEmailBody="<b>* New allocations since our previous communication</b><br><br>";
                            String  startTableTag="<table cellspacing='0' border='1'>";  
                            String headerRow="<tr><th>PROVINCE</th><th>DISTRICT</th><th>STUDENT NAME</th><th>STUDENT NUMBER</th><th>CONTACT</th><th>MODULE</th><th>PERIOD</th>"+
                                     "<th>NAME OF SCHOOL</th><th>SCHOOL CONTACT</th></tr>";
                            StudentPlacement  sp=new StudentPlacement();
                            List stuPlacementsList=sp.getPlacementListForSuperv(supervisorCode);
                            String placedStu=makeStudentList(stuPlacementsList);
                            lastPartOfEmailBody+=makeStudentsTable(placedStu,startTableTag,headerRow);
                    return lastPartOfEmailBody;
   }
   private String makeStudentsTable(String rowsOfStu,String startTableTag,String headerRow){
	                 String lastPartOfEmailBody="";
  	                 if(!rowsOfStu.trim().equals("")){
  	                       lastPartOfEmailBody+=startTableTag;
  	                       lastPartOfEmailBody+=headerRow;
  	                       lastPartOfEmailBody+=rowsOfStu;
  	                       lastPartOfEmailBody+="</table>";
  	                       lastPartOfEmailBody+="<br>";
  	                       lastPartOfEmailBody+="<b>* New allocations since our previous communication</b>";
  	                       lastPartOfEmailBody+="<br><br>";
                     }
                     return lastPartOfEmailBody;
   }
   private String makeStudentList(List placementsList)throws Exception{
	                    String row="";
                        	for(int x=0;x<placementsList.size();x++){
                                PlacementListRecord plr= (PlacementListRecord)placementsList.get(x);
  	                            row+=getStudentPlacementData(plr);
                            }
                      return row;
   }
   private String getStudentPlacementData(PlacementListRecord plr)throws Exception{
	                 String row="";
                    if(!plr.getCountryDescr().trim().equals("SOUTH AFRICA")){
                    	     plr.setProvinceDescr(plr.getCountryDescr());
                    	     School school=new School();
                    	     school=school.getSchool(plr.getSchoolCode(),null);
                    	     plr.setDistrictDesc(school.getTown());
                      }
                      if(plr.getEmailSendDate().equals("")){
                    	  row+="<tr><b><td>*"+plr.getProvinceDescr()+"</td><td>"+plr.getDistrictDesc()+"</td><td>"+
	                              plr.getStudentName()+"</td>"+
                                  "<td>"+plr.getStudentNumber()+"</td><td>"+plr.getStudentContactNumber()+"</td><td>"+
    		                      plr.getModule()+"</td><td>"+plr.getStartDate()+"-"+plr.getEndDate()+"</td><td>"+plr.getSchoolDesc()+
    		                      "</td><td>"+plr.getSchoolContactNumber()+
    		                      "</td></b></tr>";
                      }else{
                    	  row+="<tr><td>"+plr.getProvinceDescr()+"</td><td>"+plr.getDistrictDesc()+"</td><td>"+
	                              plr.getStudentName()+"</td>"+
                                  "<td>"+plr.getStudentNumber()+"</td><td>"+plr.getStudentContactNumber()+"</td><td>"+
    		                      plr.getModule()+"</td><td>"+plr.getStartDate()+"-"+plr.getEndDate()+"</td><td>"+plr.getSchoolDesc()+
    		                      "</td><td>"+plr.getSchoolContactNumber()+
    		                      "</td></tr>";
                     }
                  return row;
   }

    public String createEmailBody(int supervisorCode)throws Exception{
    	             String emailBody="";
    	             Supervisor supervisor=new Supervisor();
                     supervisor=supervisor.getSupervisor(supervisorCode);
                     List provCodeList=supervisor.getProvCodesList();
                     if((provCodeList==null)||(provCodeList.size()==0)){
                    	   provCodeList=new java.util.ArrayList();
                    	   provCodeList.add("21");
                     }
                     supervisor.setProvinceCode((Short.parseShort(provCodeList.get(0).toString())));
                     Coordinator  coordinator=new Coordinator();
                     coordinator=coordinator.getCoordinatorForProvince(supervisor.getProvinceCode());
                     emailBody= firstPartOfEmailMessage(supervisor.getName())+
                          "<br><br>Number of Students Allocated:"+supervisor.getStudentsAllocated()+
                          "<br><br>"+getStudentsList(supervisorCode)+"<br><br>"+
                          "We hope you find this in order and feel free to contact our office for any enquiries.<br><br>"+
                          "Kind Regards<br><br>Teaching Practice Coordinator:<br><b>"+coordinator.getName()+"</b><br><br>";
                      if(!coordinator.getEmailAddress().equals("")){
                    	  emailBody+="Email Address:"+coordinator.getEmailAddress()+"<br>";
                      }
                      if(!coordinator.getContactNumber().equals("")){
                    	  emailBody+="Tel:"+coordinator.getContactNumber();
                      }
    	            return emailBody;
    }
    public String createIncompleteEmailBody(int supervisorCode)throws Exception{
    	              String emailBody="";
    	                   Supervisor supervisor=new Supervisor();
                           supervisor=supervisor.getSupervisor(supervisorCode);
                           emailBody= firstPartOfEmailMessage(supervisor.getName())+
                                        "<br><br>Number of Students Allocated:"+supervisor.getStudentsAllocated()+
                                        "<br><br>"+getStudentsList(supervisorCode)+"<br><br>"+
                                        "We hope you find this in order and feel free to contact our office for any enquiries.<br><br>"+
                                         "Kind Regards<br><br>Teaching Practice Coordinator:<br><b>";
    	               return emailBody;
   }
    
   public String appendCoordDetails(String emailBody, Coordinator  coordinator){ 
	                 emailBody+=coordinator.getName()+"</b><br><br>";
                     if(!coordinator.getEmailAddress().equals("")){
  	                            emailBody+="Email Address:"+coordinator.getEmailAddress()+"<br>";
                     }
                     if(!coordinator.getContactNumber().equals("")){
  	                          emailBody+="Tel:"+coordinator.getContactNumber();
                     }
                     return emailBody;
  }
}
