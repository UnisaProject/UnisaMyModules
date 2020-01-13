package za.ac.unisa.lms.tools.tpustudentplacement.model.Email;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.StudentPlacementListRecord;
import za.ac.unisa.lms.tools.tpustudentplacement.utils.DateUtil;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.School;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.Student;
import za.ac.unisa.lms.domain.general.Person;
import java.util.List;
public class SchoolEmailData {
	
	public String getMailBody(Person user,School school,Student student, List listPlacements){
	                   String message=getSalutation(school);
	                          message+=writePlacementData(student,listPlacements,school.getCode());
                              message+=getTeachPracInfoToPrincipal();
	                          message+=writeEpilogue(user);
	                      return message;
	}
	private String getSalutation(School school){
		              DateUtil  dateutil=new DateUtil();
                      String message="";
                      message = "<html> "+
		                        "<body><blockquote><blockquote><blockquote><blockquote><blockquote><blockquote>"+
                    		    "<blockquote><blockquote><img src='https://www2.unisa.ac.za/aol/image/college of education logo.jpg'"+
		                        "width='217' height='47'/></blockquote></blockquote></blockquote></blockquote></blockquote></blockquote>"+
                                "</blockquote></blockquote>"+
		                        "<p/><p/>" +
		                        "<p><b>The Principal <br>" +
		                        "" + school.getName() + "<br>"+
		                        "" + dateutil.dateDDMMMMYYYY() + "<br>"+
		                        "</b></p>";
                      message+=("<p><b>Dear Sir/Madam</b></p>");
                      message+=("<p>Thank you for considering this student to be placed at your school to fulfil his/ her teaching practical component of the qualification.</p>");  
                      message+=("<p>The Teaching Practice Office confirms the placement details of the following student at your school:</br>");
		        return message ;
		}
	    private String getTeachPracInfoToPrincipal(){
	                          String message= "<p>During the teaching practice period, the student might be visited by a UNISA Supervisor"+
	                              "to support and assess one lesson, in addition<BR> to the assessment and support provided by"+
                                  "the Mentor Teacher<br></p>"+
                               "<p>Please note that students will remain under your jurisdiction for the full period of the"+
                                  "teaching practice and have to abide by the<br> school Code of Conduct and policies. Students are"+
                                "expected to execute all tasks that you and Mentor Teachers give them.<br> For example, if the school"+
                                  "starts at 07H30, then the student should be at school on time until end of the school day.</p>"+ 
                               "<p>Please take note of the following Teaching Practice Office requirements:</p>"+                   
                               " <OL>"+               
                               "<LI>Teaching should at all times occur under the supervision of a qualified teacher (Mentor) allocated by the school."+
                                 "The student is also<br> required to participate in all school-related activities for the full duration of the Teaching Practice period.</LI>"+
                                 "<LI>Standing in for an absent Teacher should be kept to a bare minimum, as this could interfere with the Teaching Practice programme.</LI>"+
                               "<LI> Schools are not obligated to pay/remunerate the student teacher for the services rendered during the teaching practicals.</LI>"+
                                "<LI>The student may not change schools after placement has been finalised without the approval of the Teaching Practice Office.</LI>"+
                                 "<LI>The student should report at the school on a daily basis for the full duration of the Teaching Practice period.</LI>"+
                                 "<LI>The student may not take leave from the school.</LI>"+
                                 "<LI>The student may not be excused for private engagements during school time. In the case of illness or any other official matter, <br>  "+
                                "the school and the Teaching Practice Office should be notified immediately. A medical certificate must be submitted on<br> return to the school"+
                                "in case of illness.</LI>"+
                                 "<LI>Should the student be absent from the Teaching Practice programme for any reason whatsoever, s(h)e will be required to make up for"+
                                "the lost time.</LI>"+
                                "<LI>Should the student require permission to write examinations, arrangements with the school and the Teaching Practice Office"+
                                 " <br>should be made well in advance. The student is also required to supply the Principal with an official examination timetable.</LI>"+
                                "<LI>The student should at all times adhere to the safety regulations of the school.</LI>"+
                               "</OL>";
	                       return message;
	}
	private String writePlacementData(Student student,List listPlacements,int schoolCode){
		               	String  message="<table>" +
				                        "<tr><th align=left><b>Name of Student</b></th>" +
				                        "<td   align=left><b>&nbsp;&nbsp;&nbsp;&nbsp;"+ student.getPrintName() + "</b></td></tr>"+
				                        "<tr><th  align=left><b>Student Number</b></th>" +
				                        "<td  align=left><b>&nbsp;&nbsp;&nbsp;&nbsp;"+ student.getNumber() + "</b></td></tr>"+
			                            "<tr><th  align=left><b>Programme</b></th>" +
			                            "<td  align=left><b>&nbsp;&nbsp;&nbsp;&nbsp;" + student.getQual().getShortDesc() + "</b></td></tr>"+
			                            "<tr><th  align=left><b>Teaching Practice Module</b></th>" +
			                            "<td  align=left><b>&nbsp;&nbsp;&nbsp;&nbsp;"+ getModule(listPlacements,schoolCode)+"</b></td></tr>"+
			                            "<tr><th  align=left><b>Duration</b></th>" +
			                            "<td  align=left><b>&nbsp;&nbsp;&nbsp;&nbsp;"+ getDuration(listPlacements,schoolCode)+"</b></td></tr>"+
		                                 "<tr><th  align=left><b>Subjects</b></th><td><b>&nbsp;&nbsp;&nbsp;&nbsp;The student will provide the information</b></td></tr>" +
		                               "</table>";
		                     return message;
		                                      
	}
	private String  getModule(List listPlacements,int schoolCode){ 
                         String module="";
                         for(int x=0;x<listPlacements.size();x++){
                                 StudentPlacementListRecord  splr=(StudentPlacementListRecord)listPlacements.get(x);
                                 if(splr.getSchoolCode().equals(schoolCode)){
               	                         module=splr.getModule();
                                 }
                                 break;
                         }
                  return module;
    }
	private String  getDuration(List listPlacements,int schoolCode){ 
        String duration="";
        for(int x=0;x<listPlacements.size();x++){
                StudentPlacementListRecord  splr=(StudentPlacementListRecord)listPlacements.get(x);
                if(splr.getSchoolCode().equals(schoolCode)){
	                        duration=  splr.getStartDate() + "&nbsp;-&nbsp;" + splr.getEndDate() ; 
                break;
                }
        }
      return duration;
    }
	private String writeEpilogue(Person user){
		              String message="<p>Thank you for your contribution to the training of teachers.</p>" +
				                     "<p>For any placement queries, please contact Teaching Practice office</p>" +
				                     "<p/><p>Teaching Practice Workstation Coordinator<br>" + 	
				                     "" + user.getName() + "<br>" +
				                     "Tel. no.:&nbsp;" + user.getContactNumber() + "<br> " +
				                     "Email:&nbsp;" + user.getEmailAddress() + " </p>"+
				                     "<p>Yours Faithfully<br><br>UNISA Teaching Practice Office<br><br>Teaching Practice Manager<br>Prof MW Mndawe</p>"+
				                     "</body></html>";
				return message;
		
	}
}
