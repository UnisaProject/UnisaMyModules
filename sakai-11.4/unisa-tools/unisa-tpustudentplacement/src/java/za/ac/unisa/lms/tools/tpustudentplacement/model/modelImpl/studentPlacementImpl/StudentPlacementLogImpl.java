package za.ac.unisa.lms.tools.tpustudentplacement.model.modelImpl.studentPlacementImpl;
import za.ac.unisa.lms.tools.tpustudentplacement.model.StudentPlacementLog;
import za.ac.unisa.lms.tools.tpustudentplacement.model.modelImpl.PlacementImage;
import za.ac.unisa.lms.tools.tpustudentplacement.model.modelImpl.PlacementLogAdderClass;
import za.ac.unisa.lms.tools.tpustudentplacement.dao.StudentPlacementLogDAO;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.StudentPlacementForm;

import java.util.List;
public class StudentPlacementLogImpl {
	             StudentPlacementLogDAO dao;
	             PlacementImage placementImage;
	             StudentPlacementLog spl;
	             PlacementLogAdderClass logAdder;
	             public StudentPlacementLogImpl(StudentPlacementLog spl){
	            	         this.spl=spl;
	            	         placementImage=new PlacementImage(spl);
	            	         dao=new StudentPlacementLogDAO();
	            	         logAdder=new PlacementLogAdderClass();
	             }
	            
                public List getListOfSelectedLogs()throws Exception{
                	      return dao.getListOfSelectedLogs(spl);
                 }
                 public List getListOfAllLogs()throws Exception{
           	                  return dao.getListOfAllLogs();
                 }
                 public String getStoredPlacementImage(String action,String updatedOn,String updatedBy)throws Exception{
                	 return dao.getPlacementImage(action,updatedOn,updatedBy);
                 }
                 public void  disassembleImage(String  placementImageStr){
                	             placementImage.disassembleImage(placementImageStr);
                 }
                 public StudentPlacementLog getStoredLog(String action,String  updatedby,String  updatedOn)  throws Exception{
                	               dao.getStoredLog(spl,action,updatedby,updatedOn);
                	               return spl;
                 }
                 public void getStoredData(String action,String  updatedby,String  updatedOn)  throws Exception{
                	             dao.getStoredLog(spl,action,updatedby,updatedOn);
                 }
                 public void setLogOnNewPlacement(StudentPlacementForm studentPlacementForm)throws Exception{
                	               logAdder.setLogOnNewPlacement(studentPlacementForm);
                 }
                 public void setLogOnDeletePlacement(StudentPlacementForm studentPlacementForm)throws Exception{
                	               logAdder.setLogOnDeletePlacement(studentPlacementForm);
                 }
                 public void setLogOnEmailToStu(StudentPlacementForm studentPlacementForm)throws Exception{
    	                          logAdder.setLogOnEmailToStu(studentPlacementForm);
                 }
                 public void setLogOnSmsToStu(StudentPlacementForm studentPlacementForm)throws Exception{
    	                          logAdder.setLogOnSmsToStu(studentPlacementForm);
                 }
                 public void setLogOnEmailToSchool(StudentPlacementForm studentPlacementForm)throws Exception{
                	              logAdder.setLogOnEmailToSchool(studentPlacementForm);
                 }
   
                 
}
