package za.ac.unisa.lms.tools.cronjobs.tpuModel.tpuModelImpl;
import za.ac.unisa.lms.tools.cronjobs.dao.tpuDAO.ProvinceDAO;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.HashSet;

public class ProvinceImpl {
	
	
	public String provinceListAsString(List provinceCodes)throws Exception {
               String provinceStr="";
               ProvinceDAO  dao=new ProvinceDAO();
         if((provinceCodes==null)||(provinceCodes.isEmpty())){
      	   return "";
         }else{
        	 Set  setOfProvinceCodes=new  HashSet(provinceCodes);
      	     Iterator i= setOfProvinceCodes.iterator();
      	      while (i.hasNext()){
      	    	 String  provCode=i.next().toString();
      	    	 String provDescr= dao.getProvinceDescription(Integer.parseInt(provCode));
      	    	 provinceStr+=provDescr;
      	    	 provinceStr+=(",");
      	     }
      	     provinceStr=provinceStr.substring(0,(provinceStr.length()-1));
         }
         return provinceStr;
    }
	public Set getProvinceCodeList() throws Exception {
		          ProvinceDAO  dao=new ProvinceDAO();
		          return dao.getProvinceCodeList();
	}

}
