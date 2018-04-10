package za.ac.unisa.lms.tools.tpustudentplacement.model.Email;
import java.util.List;
import java.util.Set;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.StudentPlacementListRecord;
import za.ac.unisa.lms.tools.tpustudentplacement.model.modelImpl.schoolImpl.SchoolUI;
import za.ac.unisa.lms.tools.tpustudentplacement.model.modelImpl.studentPlacementImpl.StuPlacementReader;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.School;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.StudentPlacement;
public class StudentEmailData {
	
	           public String getMailBody(String studentPrintName,List listPlacements,int schoolCode){
        		                  String message="<html><body>";
		                          message+=writePrologue(studentPrintName);
		                          message+=writeAsStrRecordsOfAllPlacementsInList(listPlacements,schoolCode);
		                          message+=writePlacementRequirements();
		                          message+=writeEpilogue();
		                          message+="</body></html>";	
				            return message;
	           }

                private String writeSchoolAddressInBodyOfMsg(School school){
    	                         String schoolAddress=  "<table><tr><td>School:&nbsp;</td><td>" + school.getName() + "</td></tr>";
			   			         if (school.getPhysicalAddress1()!=null && !school.getPhysicalAddress1().trim().equalsIgnoreCase("")){
			   			            	schoolAddress+= "<tr><td>School Address:&nbsp;</td><td>" + school.getPhysicalAddress1() + "</td></tr>"; 
				                        if (school.getPhysicalAddress2()!=null && !school.getPhysicalAddress2().trim().equalsIgnoreCase("")){
				                         	 schoolAddress+= "<tr><td>&nbsp;</td><td>" + school.getPhysicalAddress2() + "</td></tr>"; 
				                        }
				                        if (school.getPhysicalAddress3()!=null && !school.getPhysicalAddress3().trim().equalsIgnoreCase("")){
				                    	        schoolAddress+= "<tr><td>&nbsp;</td><td>" + school.getPhysicalAddress3() + "</td></tr>"; 
				                        }				
				                  }
				                  schoolAddress+="</table>";
				                  return schoolAddress;
			
               }
               private String writePrologue(String studentPrintName){
         	                 String prologue= "<blockquote><blockquote><blockquote><blockquote><blockquote><blockquote>"+
          	                              "<blockquote><blockquote><img src='https://www2.unisa.ac.za/aol/image/college of education logo.jpg'"+
      			                          "width='217' height='47'/></blockquote></blockquote></blockquote></blockquote></blockquote></blockquote>"+
          	                              "</blockquote></blockquote>" +
           			                      "<p/><p/>" +
           			                      "Dear&nbsp;" + studentPrintName +  "<br/><br/>" +
           			                      "PLACEMENT INFORMATION<br/><br/>" +
           			                      "This serves:<br/><br/>" +
           			                      "<table>" +
           				                  "<tr valign=top align=left><td>a)</td><td>To confirm your placement at the school/ECD centre for the duration of the Teaching Practice period as stipulated below.</td></tr>" +
                            			  "<tr valign=top align=left><td>b)</td><td>To advise and draw your attention to the Teaching Practice requirements which must be adhered to for the duration of your Teaching Practice period.</td></tr>" +
           			                      "<tr valign=top align=left><td>c)</td><td>To formally inform and agree with the school about your placement (relevant documents will be sent to the school separately).</td></tr>" +
           			                      "</table><br/>" +
           				                  "<b>1.  DETAILS</b><br/><br/>";
         	                 return prologue;
               }
               private String writePlacementsForSchool(List listPlacements,int schoolCode) throws Exception{
        	                               String message="";
        	                               message+="<table><tr valign=top align=left><th>Teaching Practice Module:&nbsp;</th>"+
        	                                           "<th>Duration  of Teaching Practice:&nbsp;</th>"+
			                                           "<th valign=top align=left>Date of Placement:&nbsp;</th>"+
			                                           "<th valign=top align=left>Supervisor:&nbsp;</th></tr>";
        	                               message+=writePlacementsListForSchool(listPlacements,schoolCode);
        	                               message+="</table>";
			           	             return message;
              }
               private String writeAsStrRecordsOfAllPlacementsInList(List listPlacements,int schoolCode){
                                            String message="";
                                            SchoolUI schoolUI=new SchoolUI();
                                	        String tempMessage="";
                                	         try{
                                	        	 School school=schoolUI.getSchool(schoolCode,null);
                		                          tempMessage+=writeSchoolAddressInBodyOfMsg(school);
                		                          tempMessage+= writePlacementsForSchool(listPlacements,schoolCode);
                		                          tempMessage+="<br>";
                                	         }catch(Exception ex){
                                	    	           tempMessage="";
                                	         }
                                	         message+=tempMessage;
                                
	                          return message;
             }
             private String writeAplacement(StudentPlacementListRecord placement) throws Exception{
            	                             StudentPlacement stuPlacement=new StudentPlacement();
            	                             stuPlacement.replaceDummySupervWithCoordForProv(placement);
            	                             String message= "<tr><td>" + placement.getModule() + "</td>" +
                                                "<td>" + placement.getNumberOfWeeks() + " weeks</td>" +
                                                "<td>From:&nbsp;&nbsp;" + placement.getStartDate() +
                                                "&nbsp;&nbsp;To:&nbsp;&nbsp;" + placement.getEndDate() + "</td>" +
                                                "<td>" +placement.getSupervisorName();
  	                                            if (placement.getSupervisorContactNumber()!=null &&
  	                    		                      !placement.getSupervisorContactNumber().trim().equalsIgnoreCase("")){
  	                    	                               message += " (" + placement.getSupervisorContactNumber() + ")";
                                                }
  	                                     message+="</td>";
  	                             return message;
             }
             private String  writePlacementsListForSchool(List listPlacements,int schoolCode) throws Exception{
              	                   String message="";
                                   for(int x=0;x<listPlacements.size();x++){
     	      	                          StudentPlacementListRecord  splr=(StudentPlacementListRecord)listPlacements.get(x);
              	                          if(splr.getSchoolCode().equals(schoolCode)){
              	                        	    message+=writeAplacement(splr);	
              	                          }
                                  }
                               return message;
             }
             private String writePlacementRequirements(){
            	        String message= "<table><b>2.  TEACHING PRACTICE REQUIREMENTS</b><br/><br/>" +
         				"The College of Education&#39s Teaching Practice Unit requires all students to comply with the under-mentioned requirements:<br/><br/>" +
         				"<table>" +
         				"<tr><td valign=top align=left>2.1.</td><td>Teaching should at all times occur under the supervision of a qualified teacher (mentor) allocated by the school. The student is also required to participate in all school-related activities for the full duration of the Teaching Practice period.</td></tr>" +
         				"<tr><td valign=top align=left>2.2.</td><td>Standing in for an absent teacher should be kept to a bare minimum, as this could interfere with the Teaching Practice programme.</td></tr>" +
         				"<tr><td valign=top align=left>2.3.</td><td>There should be no payment/remuneration for the services rendered at the placement school.</td></tr>" +
         				"<tr><td valign=top align=left>2.4.</td><td>The student may not change schools after placement has been finalised without the approval of the Teaching Practice Unit.</td></tr>" +
         				"<tr><td valign=top align=left>2.5.</td><td>The student should report at the school on a daily basis for the full duration of the Teaching Practice period.</td></tr>" +
         				"<tr><td valign=top align=left>2.6.</td><td>The student may not be excused for private engagements during school time. In the case of illness or any other official matter, the school and the Teaching Practice Unit should be notified immediately. A medical certificate must be submitted on return to the school in case of illness.</td></tr>" +
         				"<tr><td valign=top align=left>2.7.</td><td>Should the student be absent from the Teaching Practice programme for any reason whatsoever, s(h)e will be required to make up for the lost time.</td></tr>" +
         				"<tr><td valign=top align=left>2.8.</td><td>The student may not take leave from the school.</td></tr>" +
         				"<tr><td valign=top align=left>2.9.</td><td>Should the student require permission to write examinations, arrangements with the school and the Teaching Practice Unit should be made well in advance. The student is also required to supply the principal with an official examination time-table.</td></tr>" +
         				"<tr><td valign=top align=left>2.10.</td><td>The student should at all times adhere to the safety regulations of the school.</td></tr>" +
         				"</table><br/><br/>";
            	        return message;
             }
             private String writeEpilogue(){
            	               String message= "Yours faithfully<br/><br/>" +
         				                       "Unisa Teaching Practice Unit<br/><br/>" +
         				                        "College of Education<br/>" +
         				                        "Tel. No.: (012) 429 4200<br/>" +
         				                        "E-mail address: teachprac@unisa.ac.za";
            	               return message;
         					
             }
            
}
