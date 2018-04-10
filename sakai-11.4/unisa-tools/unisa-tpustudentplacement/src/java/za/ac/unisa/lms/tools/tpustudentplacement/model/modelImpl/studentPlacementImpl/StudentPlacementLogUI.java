package za.ac.unisa.lms.tools.tpustudentplacement.model.modelImpl.studentPlacementImpl;

import java.util.ArrayList;
import java.util.List;

import za.ac.unisa.lms.tools.tpustudentplacement.dao.StudentPlacementLogDAO;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.Student;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.StudentPlacement;
import za.ac.unisa.lms.tools.tpustudentplacement.model.StudentPlacementLog;
import za.ac.unisa.lms.tools.tpustudentplacement.model.modelImpl.PlacementLogAdderClass;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.StudentPlacementForm;

public class StudentPlacementLogUI extends PlacementLogAdderClass{
	
	public void  setLogList(StudentPlacementForm studentPlacementForm)throws Exception{
	                  StudentPlacementLog spl=new StudentPlacementLog();
                      Student student=studentPlacementForm.getStudent();
                      short year= Short.parseShort(studentPlacementForm.getAcadYear());
                      short semester=Short.parseShort(studentPlacementForm.getSemester());
                      String moduleStr="";
                      spl.setStuNum(student.getNumber().toString());
                      spl.setYear(""+year);
                      spl.setSemester(""+semester);
                      spl.setModule("");
                      spl.setAction("All");
                      spl.setCorrespondenceTo("");
                      spl.setEndDate("");
                      spl.setStartDate("");
                      spl.setUpdatedBy(studentPlacementForm.getUserId().trim());
                      spl.setUpdatedOn("");
                      spl.setSortOrder("first");
                      studentPlacementForm.setStudentPlacementLog(spl);
                      List logList=spl.getListOfSelectedLogs();
	                  setLogList(studentPlacementForm,logList);
	                  studentPlacementForm.setLogButtonTracker("FromPlacementLogBtn");
	}
    private void setLogList(StudentPlacementForm studentPlacementForm,List list) throws Exception{
        
                     studentPlacementForm.setCurrentPage("listLogs");
                     if(list==null){
      	                    list=new ArrayList();
                     }
                     studentPlacementForm.setListLogs(list);
    }
    public void getSelectedLogs(StudentPlacementForm studentPlacementForm)throws Exception{
    	              StudentPlacementLog spl=studentPlacementForm.getStudentPlacementLog();
                      List list=spl.getListOfSelectedLogs();
                      setLogList(studentPlacementForm,list);
    }
  
}
