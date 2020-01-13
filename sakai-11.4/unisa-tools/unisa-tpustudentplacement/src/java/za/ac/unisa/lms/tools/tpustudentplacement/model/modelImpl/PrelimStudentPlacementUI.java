package za.ac.unisa.lms.tools.tpustudentplacement.model.modelImpl;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

import za.ac.unisa.lms.tools.tpustudentplacement.forms.PlacementListRecord;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.StudentPlacement;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.StudentPlacementForm;
import za.ac.unisa.lms.tools.tpustudentplacement.model.modelImpl.studentPlacementImpl.StudentPlacementUI;
import za.ac.unisa.lms.tools.tpustudentplacement.uiLayer.StudentPlacementListRecordUI;
import za.ac.unisa.lms.tools.tpustudentplacement.uiLayer.StudentUI;
import za.ac.unisa.lms.tools.tpustudentplacement.utils.PlacementUtilities;

import org.apache.struts.action.ActionMessages;
public class PrelimStudentPlacementUI{

	public void setPrelimPlacementScreen(StudentPlacementForm studentPlacementForm,HttpServletRequest request)
            throws Exception {
		                        PrelimStudentPlacementImpl  prelimPlacementImpl=new  PrelimStudentPlacementImpl();
		                        List   listOfPlacements=studentPlacementForm.getListPlacement();
		                        int pos=prelimPlacementImpl.getPosOfSelectedPlacement(listOfPlacements,request);
		                        setPrelimPlacementScreen(studentPlacementForm,pos);
                                setNavigationBtnsTrackingValues(studentPlacementForm,pos,listOfPlacements);
                                studentPlacementForm.setPosOfCurrPrelimPlacement(pos);
                                studentPlacementForm.setStudentPlacementAction("editPrelimPlacement");	
                                studentPlacementForm.setCurrentPage("editPrelimPlacement");
    }
	
    public void setNavigationBtnsTrackingValues(StudentPlacementForm studentPlacementForm,int pos,List listOfPlacements){
	                     PrelimStudentPlacementImpl  prelimPlacementImpl=new  PrelimStudentPlacementImpl();
	                     String prevBtnBoundryReached=prelimPlacementImpl.prevBtnBoundryReached(pos);
	                     String nextBtnBoundryReached=prelimPlacementImpl.nextBtnBoundryReached(pos,listOfPlacements);
                         studentPlacementForm.setFirstPlacementReached(prevBtnBoundryReached);
                            studentPlacementForm.setLastPlacementReached(nextBtnBoundryReached);
    }
    public boolean setPrelimPlacementScreen(StudentPlacementForm studentPlacementForm,
                          int pos) throws Exception {//pos is position of current placemnt
                             boolean endReached=false;
                             StudentPlacement stuPlacement=new StudentPlacement();
                             List   listOfPlacements=studentPlacementForm.getListPlacement();
                             if((pos!=-1)&&(pos!=listOfPlacements.size())){
                            	      PlacementListRecord stuPlacementListRec=(PlacementListRecord)listOfPlacements.get(pos);
                            	      StudentPlacementListRecordUI splr=new StudentPlacementListRecordUI();
                                      splr.getStuPlacementFromPlacementListRec(stuPlacement,stuPlacementListRec);
                                      studentPlacementForm.setStudentNr(stuPlacement.getStuNum());
                                      StudentUI studentUI=new StudentUI();
                                      studentUI.setStudent(studentPlacementForm);
                                      studentPlacementForm.setStudentPlacement(stuPlacement);
                                      studentPlacementForm.setOriginalPrelimPlacement(stuPlacementListRec);
                                      endReached=true;
                             }
                             return endReached;
     }
     public ActionMessages setListPrelimPlacementScreen(StudentPlacementForm studentPlacementForm)throws Exception{
	                      PrelimPlacementScreenBuilder screenBuilder=new PrelimPlacementScreenBuilder(this);
	                      return screenBuilder.setListPrelimPlacementScreen(studentPlacementForm);
     }
     public void setPlacementList(StudentPlacementForm studentPlacementForm,Short province) throws Exception {     
                       StudentPlacementUI stuPlacementUI = new StudentPlacementUI();
                       stuPlacementUI .setPlacementList(studentPlacementForm, province);
     }
     public void initForIntCountry(StudentPlacementForm studentPlacementForm){
    	                 StudentPlacementUI stuPlacementUI = new StudentPlacementUI();
    	                 stuPlacementUI.initForIntCountry(studentPlacementForm);
     }
     public ActionMessages buildPrelimPlacementScreen(StudentPlacementForm studentPlacementForm)throws Exception { 
    	               Short province = studentPlacementForm.getPlacementFilterProvince();
                       studentPlacementForm.setListPlacement(new ArrayList());
		               if(!studentPlacementForm.getPlacementFilterCountry().equals(PlacementUtilities.getSaCode())){
		    	                 province=Short.parseShort("0");
		               }
		               DistrictUI districtUI=new DistrictUI();
		               districtUI.setDistrictValue(studentPlacementForm,province);
                       ActionMessages messages =setListPrelimPlacementScreen(studentPlacementForm);
                       return messages;
     }
}