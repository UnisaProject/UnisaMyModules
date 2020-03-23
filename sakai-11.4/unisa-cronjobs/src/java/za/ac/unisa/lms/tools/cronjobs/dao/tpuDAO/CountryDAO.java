package za.ac.unisa.lms.tools.cronjobs.dao.tpuDAO;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.map.ListOrderedMap;
import org.springframework.jdbc.core.JdbcTemplate;

import za.ac.unisa.lms.db.StudentSystemDAO;
import za.ac.unisa.lms.tools.cronjobs.forms.model.Country;
public class CountryDAO extends StudentSystemDAO {
	               databaseUtils dbutil;
                   public CountryDAO(){
                            dbutil=new databaseUtils();
                   }    
                   public String getSchoolCountry(int schoolCode)throws Exception {
     	                            String sql ="select c.eng_description as schCountry"+ 
                                    " from tpusch a,lns c"+
                                    " where  a.mk_country_code = c.code"+
                                    " and a.code="+schoolCode;
     	                            String errorMsg="CountryDAO:Error reading tpusch ,lns";
                                    return dbutil.querySingleValue(sql,"schCountry",errorMsg);

                   }
                   public String getSchoolCountryCode(int schoolCode)throws Exception {
                        String sql ="select a.mk_country_code as schCountry"+ 
                       " from tpusch a,lns c"+
                       " where  a.mk_country_code = c.code"+
                       " and a.code="+schoolCode;
                        String errorMsg="CountryDAO:Error reading tpusch ,lns";
                       return dbutil.querySingleValue(sql,"schCountry",errorMsg);

                  }
                   public List getCountryList() throws Exception {
               		List listCountry  = new ArrayList<Country>();
               		List queryList = new ArrayList();
               		
               		Country country = new Country();	
               		
               		String sql = "select code, eng_description" +
               		" from lns where in_use_flag = 'Y'" 
               		+ " order by eng_description";
               			
               		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
               		queryList = jdt.queryForList(sql);
               		for (int i=0; i<queryList.size();i++){
               			country = new Country();				
               				ListOrderedMap data = (ListOrderedMap) queryList.get(i);
               				country.setCode(data.get("code").toString());
               				country.setDescription(data.get("eng_description").toString());
               				listCountry.add(country);						
               			}
               		return listCountry;	
               		
               	}
}
