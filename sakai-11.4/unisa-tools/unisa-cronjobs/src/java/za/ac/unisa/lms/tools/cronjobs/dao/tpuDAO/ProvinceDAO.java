package za.ac.unisa.lms.tools.cronjobs.dao.tpuDAO;

import java.util.ArrayList;
import java.util.List;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.collections.map.ListOrderedMap;

import org.springframework.jdbc.core.JdbcTemplate;

import za.ac.unisa.lms.db.StudentSystemDAO;
import za.ac.unisa.lms.tools.cronjobs.forms.model.Province;

public class ProvinceDAO extends StudentSystemDAO {

	public List getProvinceList() throws Exception {
		             List listProvince  = new ArrayList<Province>();
		             List queryList = new ArrayList();
		             Province province = new Province();	
		
		             String sql = "select code, eng_description, in_use_flag" +
		                    " from prv where in_use_flag = 'Y'" +
		                    " order by eng_description";
		              databaseUtils dutils=new databaseUtils();	
		              queryList=dutils.queryForList(sql,"Error: reading prv table, in class: provinceDAO, mthod: getProvinceList()");
		              for (int i=0; i<queryList.size();i++){
			                province = new Province();				
				            ListOrderedMap data = (ListOrderedMap) queryList.get(i);
				            province.setCode(Short.parseShort((String)data.get("code").toString()));
				            province.setDescription(data.get("eng_description").toString());
				            province.setIn_use(data.get("in_use_flag").toString());
				            listProvince.add(province);						
			          }
		              return listProvince;	
	}
	public Set getProvinceCodeList() throws Exception {
		              List queryList = new ArrayList();
		              Set listProvince  = new HashSet<String>();	
		 		      String sql = "select code" +
		                           " from prv where in_use_flag = 'Y'"+ 
		                           " order by eng_description";
			
		 		      databaseUtils dutils=new databaseUtils();	
		              queryList=dutils.queryForList(sql,"Error: reading prv table, in class: provinceDAO, mthod: getProvinceList()");
		              for (int i=0; i<queryList.size();i++){
			                   ListOrderedMap data = (ListOrderedMap) queryList.get(i);
				               listProvince.add(data.get("code").toString());
			          }
		              return listProvince;	
	}
	public String getProvinceDescription(int code)throws Exception {
        String sql = "select eng_description, in_use_flag" +
	               " from prv where code="+code;
        JdbcTemplate jdt = new JdbcTemplate(getDataSource());
        databaseUtils  databaseutils=new databaseUtils();
        String errorMsg="ProvinceDAO:error reading prv table";
        return databaseutils.querySingleValue(sql,"eng_description", errorMsg);
   }
	
}
