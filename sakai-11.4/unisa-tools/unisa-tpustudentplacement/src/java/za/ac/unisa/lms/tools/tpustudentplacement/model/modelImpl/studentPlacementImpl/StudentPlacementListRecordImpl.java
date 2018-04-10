package za.ac.unisa.lms.tools.tpustudentplacement.model.modelImpl.studentPlacementImpl;

import za.ac.unisa.lms.tools.tpustudentplacement.forms.PlacementListRecord;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.StudentPlacementListRecord;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.StudentPlacement;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.StudentPlacementForm;

public class StudentPlacementListRecordImpl {
	
	public void getStuPlacementFromPlacementListRec(StudentPlacement stuPlacement,PlacementListRecord stuPlacementRec){           
         stuPlacement.setName(stuPlacementRec.getStudentName());
         stuPlacement.setModule(stuPlacementRec.getModule());
         stuPlacement.setStuNum(""+stuPlacementRec.getStudentNumber());
         stuPlacement.setSchoolContactNumber(stuPlacementRec.getSchoolContactNumber());
         stuPlacement.setStudentContactNumber(stuPlacementRec.getStudentContactNumber());
         stuPlacement.setDistrictDescr(stuPlacementRec.getDistrictDesc());
         stuPlacement.setDistrictCode(stuPlacementRec.getDistrictCode());
         stuPlacement.setProvinceCode(stuPlacementRec.getProvinceCode());
         stuPlacement.setCountryCode(stuPlacementRec.getCountryCode());
         stuPlacement.setSchoolDesc(stuPlacementRec.getSchoolDesc());
         stuPlacement.setSchoolCode(stuPlacementRec.getSchoolCode());
         stuPlacement.setEndDate(stuPlacementRec.getEndDate());
         stuPlacement.setStartDate(stuPlacementRec.getStartDate());
         stuPlacement.setSupervisorName(stuPlacementRec.getSupervisorName());
         stuPlacement.setSupervisorCode(stuPlacementRec.getSupervisorCode());
         stuPlacement.setEvaluationMark(stuPlacementRec.getEvaluationMark());
         stuPlacement.setNumberOfWeeks(stuPlacementRec.getNumberOfWeeks());
         stuPlacement.setTown(stuPlacementRec.getTown());
         stuPlacement.setMentorCode(stuPlacementRec.getMentorCode());
         stuPlacement.setMentorName(stuPlacementRec.getMentorName());
    }
	public StudentPlacementListRecord getStuPlacementListRecord(StudentPlacementForm studentPlacementForm){
		         StudentPlacementListRecord studentPlacementListRecord=new StudentPlacementListRecord();
	             for (int i=0; i <studentPlacementForm.getIndexNrPlacementSelected().length; i++) {
			              String array[] = studentPlacementForm.getIndexNrPlacementSelected();
			              studentPlacementListRecord = (StudentPlacementListRecord)studentPlacementForm.getListStudentPlacement().get(Integer.parseInt(array[i]));
		         }
	             return studentPlacementListRecord;
	}
		
		

}
