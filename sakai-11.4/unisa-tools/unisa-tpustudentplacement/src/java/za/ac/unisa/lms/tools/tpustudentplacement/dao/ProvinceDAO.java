package za.ac.unisa.lms.tools.tpustudentplacement.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.map.ListOrderedMap;
import org.apache.struts.util.LabelValueBean;
import org.springframework.jdbc.core.JdbcTemplate;

import za.ac.unisa.lms.db.StudentSystemDAO;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.Province;

public class ProvinceDAO extends StudentSystemDAO {

	public List getProvinceList() throws Exception {
		List listProvince  = new ArrayList<Province>();
		List queryList = new ArrayList();
		
		Province province = new Province();	
		
		String sql = "select code, eng_description, in_use_flag" +
		" from prv where in_use_flag = 'Y'" 
		+ " order by eng_description";
			
		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		queryList = jdt.queryForList(sql);
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
	public List getProvinceLabelValueList(String defaultValue,String defaultString) throws Exception {
		List listProvince  = new ArrayList<LabelValueBean>();
		List queryList = new ArrayList();
		String value="";
		String label="";
		Province province = new Province();	
		
		String sql = "select code, eng_description, in_use_flag" +
		" from prv where in_use_flag = 'Y'" 
		+ " order by eng_description";
			
		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		queryList = jdt.queryForList(sql);
		for (int i=0; i<queryList.size();i++){
			province = new Province();				
				ListOrderedMap data = (ListOrderedMap) queryList.get(i);
				province.setCode(Short.parseShort((String)data.get("code").toString()));
				province.setDescription(data.get("eng_description").toString());
				label=province.getDescription();
				value=""+province.getCode();
				listProvince.add(new LabelValueBean(label, value));
		}
		listProvince.add(0,new LabelValueBean(defaultString,defaultValue));
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
