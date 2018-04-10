package za.ac.unisa.lms.tools.tpustudentplacement.model.modelImpl.schoolImpl;

import za.ac.unisa.lms.tools.tpustudentplacement.forms.SchoolListRecord;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.StudentPlacement;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.StudentPlacementForm;
import za.ac.unisa.lms.tools.tpustudentplacement.model.Mentor;
import za.ac.unisa.lms.tools.tpustudentplacement.utils.PlacementUtilities;

public class SchoolScreenBuilder {

	  public  SchoolListRecord getSelectedSchool(StudentPlacementForm studentPlacementForm){
	           SchoolListRecord school = new SchoolListRecord();
	           String[] indexArr=studentPlacementForm.getIndexNrSelected();
	           for (int i=0; i <indexArr.length; i++) {			
		                school = (SchoolListRecord)studentPlacementForm.getListSchool().get(Integer.parseInt(indexArr[i]));			
		                i=studentPlacementForm.getIndexNrSelected().length;
	           }
	           return school;
	   }
	   public void setSchoolDetailsForEditPlacement(StudentPlacement studentPlacement,int schoolCode,
	           String schoolName,String pageCalledFrom){
              if (pageCalledFrom.equalsIgnoreCase("editStudentPlacement")||
             		  pageCalledFrom.equalsIgnoreCase("editPrelimPlacement")  
             		  ){
             	          studentPlacement.setSchoolCode(schoolCode);
                           studentPlacement.setSchoolDesc(schoolName);
	              }
       }
	   public void setSchoolDetailsForInputPlacement(StudentPlacementForm studentPlacementForm,SchoolListRecord school){
           if (studentPlacementForm.getSchoolCalledFrom().equalsIgnoreCase("inputPlacement") || 
	                 studentPlacementForm.getSchoolCalledFrom().equalsIgnoreCase("listPlacement")||
	                   studentPlacementForm.getSchoolCalledFrom().equalsIgnoreCase("listPrelimPlacement")){ 
	                       studentPlacementForm.setPlacementFilterSchoolDesc(school.getName());
	                       studentPlacementForm.setPlacementFilterSchool(school.getCode());
	                       if(studentPlacementForm.getPlacementFilterCountry().equals(PlacementUtilities.getSaCode())){
	                               studentPlacementForm.setPlacementFilterProvince(school.getProvinceCode());
	                               studentPlacementForm.setPlacementFilterDistrictValue(school.getDistrictCode().toString() + "-" + school.getProvinceCode());
	                               studentPlacementForm.setListFilterPlacementDistrict(studentPlacementForm.getListFilterSchoolDistrict());
	                       }
	                      /* if(studentPlacementForm.getSchoolCalledFrom().equalsIgnoreCase("listPlacement")){ 
	                             studentPlacementForm.setSchoolCalledFrom("inputPlacement");
	                       }*/
            }
}
}
