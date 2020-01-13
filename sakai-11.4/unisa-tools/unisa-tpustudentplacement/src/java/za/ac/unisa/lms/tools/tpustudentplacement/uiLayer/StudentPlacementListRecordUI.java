package za.ac.unisa.lms.tools.tpustudentplacement.uiLayer;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.StudentPlacement;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.PlacementListRecord;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.StudentPlacementForm;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.StudentPlacementListRecord;
import za.ac.unisa.lms.tools.tpustudentplacement.model.modelImpl.studentPlacementImpl.StudentPlacementListRecordImpl;

public class StudentPlacementListRecordUI {
	
         public void getStuPlacementFromPlacementListRec(StudentPlacement stuPlacement, PlacementListRecord stuPlacementRec){
                        StudentPlacementListRecordImpl  stuPlacListRec=new StudentPlacementListRecordImpl();
                        stuPlacListRec.getStuPlacementFromPlacementListRec(stuPlacement, stuPlacementRec);
         }
         public StudentPlacementListRecord getSelectedPlacemenRecord(StudentPlacementForm studentPlacementForm){
                        StudentPlacementListRecord placementListRecord = new StudentPlacementListRecord();
        			    for (int i=0; i <studentPlacementForm.getIndexNrPlacementSelected().length; i++) {
		                     String array[] = studentPlacementForm.getIndexNrPlacementSelected();
		                     int index=Integer.parseInt(array[i]);
		                     placementListRecord = (StudentPlacementListRecord)studentPlacementForm.getListStudentPlacement().get(index);
	                    }
	                    return placementListRecord;
        }
}
