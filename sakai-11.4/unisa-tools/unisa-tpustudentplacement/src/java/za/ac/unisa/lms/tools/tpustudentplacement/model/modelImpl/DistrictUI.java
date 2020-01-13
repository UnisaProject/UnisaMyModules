package za.ac.unisa.lms.tools.tpustudentplacement.model.modelImpl;
import java.util.List;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.StudentPlacementForm;

public class DistrictUI extends DistrictImpl{
	
	
	
	public void setDistrictValue(StudentPlacementForm formBean,Short province){
                   if (formBean.getPlacementFilterDistrictValue()!=null &&
                           !formBean.getPlacementFilterDistrictValue().trim().equalsIgnoreCase("")){
                            int index = formBean.getPlacementFilterDistrictValue().indexOf("-");
                            String district = formBean.getPlacementFilterDistrictValue().trim().substring(0, index);
                            formBean.setPlacementFilterDistrict(Short.parseShort(district));
                   }
                           
      }
    private List getAllDsitrict(short prov,short district)throws Exception{
    	               DistrictImpl districtImpl=new DistrictImpl();
    	               return districtImpl.getAllDistricts(prov, district);
    }
    private void allowAllDsitrictSelection(StudentPlacementForm studentPlacementForm,String district)throws Exception{
    	              short prov=studentPlacementForm.getPlacementFilterProvince();
	                  studentPlacementForm.setPlacementFilterDistrict(Short.parseShort(district));
	                  short districtValue=studentPlacementForm.getPlacementFilterDistrict();
    	              List districtList=getAllDsitrict(prov,districtValue);
                      studentPlacementForm.setListFilterPlacementDistrict(districtList);
   }

}
