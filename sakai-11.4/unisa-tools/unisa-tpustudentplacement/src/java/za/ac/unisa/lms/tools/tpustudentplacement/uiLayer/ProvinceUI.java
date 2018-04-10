package za.ac.unisa.lms.tools.tpustudentplacement.uiLayer;
import java.util.List;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.Province;
import za.ac.unisa.lms.tools.tpustudentplacement.model.modelImpl.ProvinceImpl;
import za.ac.unisa.lms.tools.tpustudentplacement.model.modelImpl.schoolImpl.SchoolUI;

public class ProvinceUI extends Province{
		       public void setProvinceData(int schoolCode)throws Exception {
                              SchoolUI school=new SchoolUI();
                              int provCode=school.getSchoolProvCode(schoolCode);
                              setCode((short)provCode);
                              setDescription(getProvinceDescription(provCode));
               }
		       public String provinceListAsString(List provinceCodes)throws Exception {
                               ProvinceImpl probImpl=new ProvinceImpl();
                               return probImpl.provinceListAsString(provinceCodes);
               }
	           public List getProvinceList()throws Exception {
	        	                 ProvinceImpl probImpl=new ProvinceImpl();
	        	                 return probImpl.getProvinceList();
	           }
	           public String getProvinceDescription(int code)throws Exception {
	        	                ProvinceImpl probImpl=new ProvinceImpl();
	                            return probImpl.getProvinceDescription(code);
               }
}
