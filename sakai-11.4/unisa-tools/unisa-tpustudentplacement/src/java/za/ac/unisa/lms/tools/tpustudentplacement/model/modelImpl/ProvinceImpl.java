package za.ac.unisa.lms.tools.tpustudentplacement.model.modelImpl;
import za.ac.unisa.lms.tools.tpustudentplacement.dao.ProvinceDAO;
import za.ac.unisa.lms.tools.tpustudentplacement.dao.databaseUtils;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.jdbc.core.JdbcTemplate;

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
	        public List getProvinceList()throws Exception {
	        	           ProvinceDAO  dao=new ProvinceDAO();
                           List list = dao.getProvinceList();
                    return list;
	        }
	        public String getProvinceDescription(int code)throws Exception {
	        	              ProvinceDAO  dao=new ProvinceDAO();
                              return dao.getProvinceDescription(code);
            }
}
