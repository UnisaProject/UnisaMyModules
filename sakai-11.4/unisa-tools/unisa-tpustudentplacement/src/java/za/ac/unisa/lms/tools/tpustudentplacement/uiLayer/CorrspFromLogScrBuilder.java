package za.ac.unisa.lms.tools.tpustudentplacement.uiLayer;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMessages;
import org.apache.struts.util.LabelValueBean;

import za.ac.unisa.lms.tools.tpustudentplacement.forms.School;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.StudentPlacementForm;
import za.ac.unisa.lms.tools.tpustudentplacement.model.modelImpl.schoolImpl.SchoolUI;


public class CorrspFromLogScrBuilder {
	public void setCorrespondeceDetailsFromLog(HttpServletRequest request,
	        StudentPlacementForm studentPlacementForm,ActionMessages messages)throws Exception{
                       setSchoolDetFromLogToFormBean(request,studentPlacementForm);
                       setRegistrationPeriodFromLog(request,studentPlacementForm);
                       setStudentDetFromLog(request,studentPlacementForm, messages);
                       List list=setSchoolListFromLog(studentPlacementForm.getSchool());
	                   studentPlacementForm.setCommunicationList(list);
	                   studentPlacementForm.setPreviousPage("listLogs");
}

private void setRegistrationPeriodFromLog(HttpServletRequest request,
	                  StudentPlacementForm studentPlacementForm){
                           studentPlacementForm.setSemester(request.getParameter("semester")) ;
                           studentPlacementForm.setAcadYear(request.getParameter("year"));
                           
}
private List setSchoolListFromLog(School school){
                 List list = new ArrayList<LabelValueBean>();
                 list.add(new LabelValueBean(school.getName(),""+school.getCode()));
                return list;
}
private School  getSchoolDetFromLog(HttpServletRequest request)throws Exception{
                int schoolCode=Integer.parseInt(request.getParameter("schoolCode"));
                SchoolUI schoolUI =new SchoolUI();
	            return schoolUI.getSchool(schoolCode,null);

}
private void  setSchoolDetFromLogToFormBean(HttpServletRequest request,StudentPlacementForm studentPlacementForm)throws Exception{
                  School school=getSchoolDetFromLog(request);
                  SchoolUI schoolUI=new SchoolUI();
                  studentPlacementForm.setSchool(school);
                  studentPlacementForm.setCommunicationSchool(school.getCode());
                  schoolUI.setSchoolCommunicDataToFormBean(studentPlacementForm);
                  String correspondenceTo=request.getParameter("correspondenceTo");
                  if(correspondenceTo.equalsIgnoreCase("SCHOOL")){
                         studentPlacementForm.setCommunicationTo("School");
                  }
}
private void  setStudentDetFromLog(HttpServletRequest request,
	                StudentPlacementForm studentPlacementForm,ActionMessages messages)throws Exception{
                         studentPlacementForm.setStudentNr(request.getParameter("stuNum"));
                         StudentUI studentUI=new StudentUI();
                         studentUI.setStudent(studentPlacementForm);
                         studentUI.setStudentEmailToFormBean(studentPlacementForm);
                         studentUI.setStudentCellNrToFormBean(studentPlacementForm);
                         String correspondenceTo=request.getParameter("correspondenceTo");
                         if(!correspondenceTo.equalsIgnoreCase("SCHOOL")){
                                studentPlacementForm.setCommunicationTo("Student");
                         }
}

}
