package za.ac.unisa.lms.tools.tpustudentplacement.model.modelImpl;
import java.util.ArrayList;
import java.util.List;
import org.apache.struts.util.LabelValueBean;
import za.ac.unisa.lms.tools.tpustudentplacement.dao.DistrictDAO;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.District;
public class DistrictImpl {
	
	 public List getAllDistricts(Short province,Short district)throws Exception{
	      	       List listDistrict = new ArrayList();
	               DistrictDAO dao = new DistrictDAO();
	               listDistrict = dao.getDistrictList2("Y", province,""+district);
	               String label="ALL";
	               String value="0-" + province.toString();
	               listDistrict.add(0,new LabelValueBean(label, value));
	             return listDistrict;
	   }

	   DistrictDAO dao;
       public DistrictImpl(){
        	    dao = new DistrictDAO();
       }   
       public void updateDistrict(District district) throws Exception {
                      	dao.updateDistrict(district);
       }
       public District getDistrict(Short code, String description) throws Exception {
   		             return 	dao.getDistrict(code, description);		
       }

       public List getDistrictList(String inUseFlag, Short provCode, String filter) throws Exception {
                       return dao.getDistrictList(inUseFlag, provCode, filter);	
       }
       public List getDistrictList2(String inUseFlag, Short provCode, String filter) throws Exception {
        	           return dao.getDistrictList2(inUseFlag, provCode, filter);			
        }
        public void insertDistrict(District district) throws Exception {
        	  dao.insertDistrict(district);
        }
        public District getDistrict(Short districtCode)throws Exception {
                           return dao.getDistrict(districtCode,null);
       }               
	
}
