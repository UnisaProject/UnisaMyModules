package za.ac.unisa.lms.tools.tpustudentplacement.uiLayer;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.StudentPlacementForm;
import za.ac.unisa.lms.tools.tpustudentplacement.model.RegPeriodForStu;;


public class RegPeriodForStuUI {
	
	              RegPeriodForStu  regPeriodForStu;
	              public  RegPeriodForStuUI(){
	            	         regPeriodForStu=new RegPeriodForStu();
	             }
	             public void etRegPeriodForStuFromFormBean(StudentPlacementForm studentPlacementForm){
	            	                            regPeriodForStu=getRegPeriodForFromFormBean(studentPlacementForm);
	            	                            regPeriodForStu.setSemester(Short.parseShort(studentPlacementForm.getSemester()));
	             }
	             public void getRegPeriodForStuFromFormBean(String stuNum,StudentPlacementForm studentPlacementForm){
                                             regPeriodForStu=new RegPeriodForStu();
                                             regPeriodForStu.setStuNum(Integer.parseInt(stuNum));
                 }
	             private RegPeriodForStu getRegPeriodForFromFormBean(StudentPlacementForm studentPlacementForm){
	            	                           RegPeriodForStu regPeriodForStu=getRegPeriodForFromFormBean(studentPlacementForm);
	            	                           regPeriodForStu.setAcadYear(Short.parseShort(studentPlacementForm.getAcadYear()));
	            	                           regPeriodForStu.setSemester(Short.parseShort(studentPlacementForm.getSemester()));
	            	                           return regPeriodForStu;
	             }
	             public int getStuNum(){
	            	       return regPeriodForStu.getStuNum();
	             }
	             public short getAcadYear(){
	            	             return regPeriodForStu.getAcadYear();
	             }
	             public short getSemester(){
	            	             return regPeriodForStu.getSemester();
	             }
	             
	             
}
