package za.ac.unisa.lms.tools.tpustudentplacement.model;
import java.util.List;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.StudentPlacementListRecord;
public class PlacementListRecordDataCopier{
	public  void copyPlacementListRecord(StudentPlacementLog studentPlacementLog,
	         StudentPlacementListRecord placementListRecord) {
	            studentPlacementLog.setEndDate(placementListRecord.getEndDate());
	            studentPlacementLog.setStartDate(placementListRecord.getStartDate());
	            studentPlacementLog.setEvaluationMark(studentPlacementLog.getEvaluationMark());
	            studentPlacementLog.setModule(placementListRecord.getModule());
	            studentPlacementLog.setNumberOfWeeks(placementListRecord.getNumberOfWeeks());
	            studentPlacementLog.setSchoolCode(placementListRecord.getSchoolCode());
	            studentPlacementLog.setSupervisorCode(placementListRecord.getSupervisorCode());
	}
 
}
