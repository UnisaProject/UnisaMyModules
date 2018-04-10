package za.ac.unisa.lms.tools.tpustudentplacement.uiLayer;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMessages;
import org.apache.struts.util.LabelValueBean;

import za.ac.unisa.lms.tools.tpustudentplacement.forms.School;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.StudentPlacement;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.StudentPlacementForm;
import za.ac.unisa.lms.tools.tpustudentplacement.model.modelImpl.schoolImpl.SchoolUI;

public class CorrFromEditDummyScrBuilder {
	
	public  void setCorrespDetForEditDummyPlacement(HttpServletRequest request,
			              StudentPlacementForm studentPlacementForm, ActionMessages messages)throws Exception{
                            StudentPlacement stuPlacement=studentPlacementForm.getStudentPlacement();
                            String schoolDesc=stuPlacement.getSchoolDesc();
                            int schoolCode=stuPlacement.getSchoolCode();
                            studentPlacementForm.setCommunicationEmailAddress("");
                            studentPlacementForm.setCommunicationCellNumber("");
                            List list = new ArrayList<LabelValueBean>();
                            list.add(new LabelValueBean(schoolDesc,""+schoolCode));
                            studentPlacementForm.setCommunicationSchool(schoolCode);
                            SchoolUI schoolUI =new SchoolUI();
                            studentPlacementForm.setSchool(schoolUI.getSchool(schoolCode,null));
                            studentPlacementForm.setCommunicationList(list);
                            StudentUI studentUI=new StudentUI();
                            studentUI.setStudent(studentPlacementForm);
                            studentPlacementForm.setPreviousPage("editPrelimPlacement");
	}
}