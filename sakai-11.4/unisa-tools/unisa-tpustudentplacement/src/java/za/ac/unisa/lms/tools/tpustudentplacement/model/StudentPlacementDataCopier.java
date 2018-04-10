package za.ac.unisa.lms.tools.tpustudentplacement.model;
import java.util.List;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.StudentPlacement;

public class StudentPlacementDataCopier{
	
	     public  void copyPlacementRecord(StudentPlacementLog studentPlacementLog,StudentPlacement stuPlacement){
	                    studentPlacementLog.setEndDate(stuPlacement.getEndDate());
                        studentPlacementLog.setStartDate(stuPlacement.getStartDate());
                        studentPlacementLog.setEvaluationMark(stuPlacement.getEvaluationMark());
                        studentPlacementLog.setModule(stuPlacement.getModule());
                        studentPlacementLog.setNumberOfWeeks(stuPlacement.getNumberOfWeeks());
                        studentPlacementLog.setSchoolCode(stuPlacement.getSchoolCode());
                        studentPlacementLog.setSupervisorCode(stuPlacement.getSupervisorCode());
	    }
 
}
