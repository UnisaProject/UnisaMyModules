package za.ac.unisa.lms.tools.tpustudentplacement.uiLayer;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.util.LabelValueBean;
import za.ac.unisa.lms.tools.tpustudentplacement.model.modelImpl.schoolImpl.SchoolUI;
import za.ac.unisa.lms.tools.tpustudentplacement.model.modelImpl.studentPlacementImpl.StudentPlacementUI;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.StudentPlacementForm;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.StudentPlacementListRecord;
import za.ac.unisa.lms.tools.tpustudentplacement.utils.CommunicationDataValidator;
public class CommunicationUI {
             
	StudentPlacementUI studentPlacementUI;
	public  CommunicationUI(){
		          studentPlacementUI=new StudentPlacementUI();
	}
	            public void buildCommunicationScreen(HttpServletRequest request,StudentPlacementForm studentPlacementForm,
                                         ActionMessages messages)throws Exception{
	            	                        CorrespScreenBuilder  correspScreenBuilder=new CorrespScreenBuilder();
	            	                        correspScreenBuilder.buildCommunicationScreen(request, studentPlacementForm, studentPlacementUI, 
	            	            		             this, messages);
		   
	            }
	            public void setCellNrToFormBean(StudentPlacementForm studentPlacementForm)throws Exception{
		                            setStudentCellNrToFormBean(studentPlacementForm);
		                          setSchoolCellNrToBean(studentPlacementForm);
		               
	            }
                public void setStudentCellNrToFormBean(StudentPlacementForm studentPlacementForm)throws Exception{
                              if (studentPlacementForm.getCommunicationTo().equalsIgnoreCase("Student")){
 	                                      StudentUI studentUI=new StudentUI();
 	                                      studentUI.setStudentCellNrToFormBean(studentPlacementForm);
                              }
                }
	            public void setSchoolCellNrToBean(StudentPlacementForm studentPlacementForm)throws Exception{
                                   if (studentPlacementForm.getCommunicationTo().equalsIgnoreCase("School")){
       	                                  SchoolUI schoolUI=new SchoolUI();
       	                                  schoolUI.setSchoolCellNrToFormBean(studentPlacementForm);
       	                                  schoolUI.setSchoolToFormBean(studentPlacementForm);
                                   }
                }
	            public void getCellNr(StudentPlacementForm studentPlacementForm,ActionMessages messages)throws Exception{
	            	               CommunicationDataValidator  cdv=new CommunicationDataValidator();
	            	               String communicationTo=studentPlacementForm.getCommunicationTo();
	            	               String msg="Recipient required to get Cell Number.";
	            	               cdv.validateCommunicationTo(communicationTo,messages,msg);
	            	               if(messages.isEmpty()){
 	            	            	        CommunicationUI communicationUI=new CommunicationUI();
      	      			                    communicationUI.setCellNrToFormBean(studentPlacementForm);
	                 	      		}
	            	               studentPlacementForm.setCurrentPage("inputCorrespondence");
	            }
	            public List  getListOfSchoolsInStuPlacementList(StudentPlacementForm studentPlacementForm){
	            	               List list = new ArrayList<LabelValueBean>();
	                               for (int i=0; i < studentPlacementForm.getListStudentPlacement().size(); i++){
	                                  StudentPlacementListRecord record = new StudentPlacementListRecord();
	                                  studentPlacementForm.setStudentPlacementListRecord(record);
	                                  String value="";
	                                  String label="";			
	                                  record=(StudentPlacementListRecord)studentPlacementForm.getListStudentPlacement().get(i);
	                                  label=record.getSchoolDesc();
	                                  value=record.getSchoolCode().toString();
	                                  boolean found=false;
	                                  for (int j=0; j<list.size(); j++){
	                                         if (value.equalsIgnoreCase(((LabelValueBean)list.get(j)).getValue())){
	                                              found=true;
	                                              j=list.size();
	                                         }
	                                  }
	                                  if (!found){
	                                          list.add(new LabelValueBean(label,value));
	                                 }			
	                             }
	                         return list; 
	            	}
	                public StudentPlacementListRecord getPlacementForCorresp(StudentPlacementForm studentPlacementForm){
                                                      List placementList= studentPlacementForm.getListStudentPlacement();
                                                      StudentPlacementListRecord placement = new StudentPlacementListRecord();
                                                      for (int i=0; i < placementList.size(); i++){
                                                             placement = (StudentPlacementListRecord)placementList.get(i);
                                                             checkCommunicationSchcool(placement,studentPlacementForm);
                                                             if (placement.getSchoolCode().compareTo(studentPlacementForm.getCommunicationSchool())==0){
                                                                      studentPlacementForm.setStudentPlacementListRecord(placement);  
                                                                      break;
                                                              }
                                                     }
                                                     return placement;
                  }
	              public List getStuPlacementsForSchool(StudentPlacementForm studentPlacementForm){
                                        List placementList= studentPlacementForm.getListStudentPlacement();
                                        StudentPlacementListRecord placement = new StudentPlacementListRecord();
                                        List placementsAtAschool=new ArrayList();
                                        for (int i=0; i < placementList.size(); i++){
                                                placement = (StudentPlacementListRecord)placementList.get(i);
                                                if(i==0){
                                                    checkCommunicationSchcool(placement,studentPlacementForm);
                                                }
                                                if (placement.getSchoolCode().compareTo(studentPlacementForm.getCommunicationSchool())==0){
                            	                       placementsAtAschool.add(placement); 
                                                }
                                       }
                                       return placementsAtAschool;
                  }
                  public void checkCommunicationSchcool(StudentPlacementListRecord placement,StudentPlacementForm studentPlacementForm){
                                   if(studentPlacementForm.getCommunicationSchool()==null){
                                                  studentPlacementForm.setCommunicationSchool(placement.getSchoolCode());
                                   }

                  }
}
