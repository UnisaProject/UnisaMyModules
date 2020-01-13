package za.ac.unisa.lms.tools.tpustudentplacement.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.map.ListOrderedMap;
import org.apache.struts.util.LabelValueBean;
import org.springframework.jdbc.core.JdbcTemplate;

import za.ac.unisa.lms.db.StudentSystemDAO;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.District;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.Province;

public class DistrictDAO extends StudentSystemDAO {
	                  
	                   databaseUtils dbutil;
  	                   public DistrictDAO(){
                                  dbutil=new databaseUtils();
                       }   
     
	                   public void updateDistrict(District district) throws Exception {
		 		                      String sql = "update tpudis set description='" + district.getDescription().toUpperCase().trim() + "'," +
			                                       " mk_prv_code=" + district.getProvince().getCode() + "," + 
			                                       " in_use_flag='" + district.getInUse().toUpperCase().trim() + "'" +
			                                       " where code=" + district.getCode();
		 		                      String errorMsg="StudentPlacementDao : Error updating TPUDIS / ";
		 		                 dbutil.update(sql,errorMsg);	
		              }

	                  public District getDistrict(Short code, String description) throws Exception {
		
		                                  District district = new District();
		                                  String sql = "select code,eng_description,fk_prvcode,in_use_flag from ldd";
		          		                  if (code!=null && code!=0){
			                                    sql = sql + " where code=" + code;
		                                  }else if (description!=null && !description.equalsIgnoreCase("")){
			                                        sql = sql + " where eng_description ='" + description.toUpperCase().trim() + "'";
		                                  }		
		          		                  String errorMsg="StudentPlacementDao : Error reading table TPUDIS / "; 
		          		                  
 		               		              List queryList = dbutil.queryForList(sql,errorMsg);
					                      Iterator i = queryList.iterator();
			                              while (i.hasNext()) {
				                                   ListOrderedMap data = (ListOrderedMap) i.next();
				                                   Province province = new Province();
								                   district.setCode(Short.parseShort(data.get("code").toString()));
				                                   district.setDescription(dbutil.replaceNull(data.get("eng_description")));
				                                   province.setCode(Short.parseShort(data.get("fk_prvcode").toString()));
				                                   district.setInUse(dbutil.replaceNull(data.get("in_use_flag")));
				                                   district.setProvince(province);		
				                                   break;
			                             }
               		   		             return district;		
	                 }
	
	public District xxgetDistrict(Integer code, String description) throws Exception {
		
		District district = new District();
		
		String sql = "select code,description,mk_prv_code,in_use_flag from tpudis";
		
		if (code!=null && code!=0){
			sql = sql + " where code=" + code;
		}else if (description!=null && !description.equalsIgnoreCase("")){
			sql = sql + " where description ='" + description.toUpperCase().trim() + "'";
		}		
		
		try{ 
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(sql);
		
			Iterator i = queryList.iterator();
			while (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				Province province = new Province();
				
				district.setCode(Short.parseShort(data.get("code").toString()));
				district.setDescription(dbutil.replaceNull(data.get("description")));
				province.setCode(Short.parseShort(data.get("mk_prv_code").toString()));
				district.setInUse(dbutil.replaceNull(data.get("in_use_flag")));
				district.setProvince(province);		
				break;
			}
		}
		catch (Exception ex) {
			throw new Exception("StudentPlacementDao : Error reading table TPUDIS / " + ex,ex);						
		}		
		return district;		
	}
	public List getDistrictList(String inUseFlag, Short provCode, String filter) throws Exception {
		List listDistrict  = new ArrayList<District>();
		List queryList = new ArrayList();
		
		District district = new District();	
		
		String sql = "select a.code as districtCode, a.eng_description as districtDesc," +
		" a.fk_prvcode as provCode,a.in_use_flag as districtInUse, b.eng_description as provDesc" +
		" from ldd a,prv b" +
		" where a.fk_prvcode = b.code";
		
		if (inUseFlag != null && inUseFlag.equalsIgnoreCase("Y")){
			sql = sql + " and a.in_use_flag='Y'";
		}
		
		if(provCode!=null && provCode!=0){
			sql = sql + " and a.fk_prvcode=" + provCode;					
		}
		
		if (filter!=null && !filter.trim().equalsIgnoreCase("")){
			filter = filter.replaceAll("'", "''");
			sql = sql + " and a.eng_description like '" + filter.trim().toUpperCase() + "'";
		}
		
		sql = sql + " order by a.eng_description";
			
		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		queryList = jdt.queryForList(sql);
		for (int i=0; i<queryList.size();i++){
			
			district = new District();	
			Province province = new Province();	
			ListOrderedMap data = (ListOrderedMap) queryList.get(i);
			district.setCode(Short.parseShort(data.get("districtCode").toString()));
			district.setDescription(data.get("districtDesc").toString());
			district.setInUse(data.get("districtInUse").toString());
			province.setCode(Short.parseShort(data.get("provCode").toString()));
			province.setDescription(data.get("provDesc").toString());
			district.setProvince(province);
			listDistrict.add(district);						
			}
		return listDistrict;			
	}
	
	public List getDistrictList2(String inUseFlag, Short provCode, String filter) throws Exception {
		List listDistrict  = new ArrayList<District>();
		List queryList = new ArrayList();
				
		String sql = "select a.code as districtCode, a.eng_description as districtDesc," +
		" a.fk_prvcode as provCode,a.in_use_flag as districtInUse, b.eng_description as provDesc" +
		" from ldd a,prv b" +
		" where a.fk_prvcode = b.code";
		
		if (inUseFlag != null && inUseFlag.equalsIgnoreCase("Y")){
			sql = sql + " and a.in_use_flag='Y'";
		}
		
		if(provCode!=null && provCode!=0){
			sql = sql + " and a.fk_prvcode=" + provCode;					
		}
		
		if (filter!=null && !filter.trim().equalsIgnoreCase("")){
			filter = filter.replaceAll("'", "''");
			sql = sql + " and a.eng_description like '" + filter.trim().toUpperCase() + "'";
		}
		
		sql = sql + " order by a.eng_description";
			
		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		queryList = jdt.queryForList(sql);
		String value="";
		String label="";
		String districtCode="";
		String districtDesc="";
		String provinceCode="";
		String provinceDesc="";
		for (int i=0; i<queryList.size();i++){			
			ListOrderedMap data = (ListOrderedMap) queryList.get(i);
			districtCode = data.get("districtCode").toString();
			districtDesc = data.get("districtDesc").toString();
			provinceCode = data.get("provCode").toString();
			provinceDesc = data.get("provDesc").toString();
			label = districtDesc;
			if(provCode==null || provCode==0){
				label = label + "---" + provinceDesc;
			}			
			value = districtCode;
			value=value + "-" + provinceCode;
			listDistrict.add(new LabelValueBean(label, value));
			}
		return listDistrict;			
	}
	
	public List getDistrictListxx(String inUseFlag, Short provCode) throws Exception {
		List listDistrict  = new ArrayList<District>();
		List queryList = new ArrayList();
		
		District district = new District();	
		
		String sql = "select a.code as districtCode, a.description as districtDesc," +
		" a.mk_prv_code as provCode,a.in_use_flag as districtInUse, b.eng_description as provDesc" +
		" from tpudis a,prv b" +
		" where a.mk_prv_code = b.code";
		
		if (inUseFlag != null && inUseFlag.equalsIgnoreCase("Y")){
			sql = sql + " and a.in_use_flag='Y'";
		}
		
		if(provCode!=null && provCode!=0){
			sql = sql + " and a.mk_prv_code=" + provCode;					
		}
		
		sql = sql + " order by a.description";
			
		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		queryList = jdt.queryForList(sql);
		for (int i=0; i<queryList.size();i++){
			
			district = new District();	
			Province province = new Province();	
			ListOrderedMap data = (ListOrderedMap) queryList.get(i);
			district.setCode(Short.parseShort(data.get("districtCode").toString()));
			district.setDescription(data.get("districtDesc").toString());
			district.setInUse(data.get("districtInUse").toString());
			province.setCode(Short.parseShort(data.get("provCode").toString()));
			province.setDescription(data.get("provDesc").toString());
			district.setProvince(province);
			listDistrict.add(district);						
			}
		return listDistrict;			
	}
	
	public void insertDistrict(District district) throws Exception {
		
		String sql = "insert into tpudis (code,description,mk_prv_code,in_use_flag) " +
			"values " +
			"((select max(code) + 1 from tpudis)," + 
			"'" + district.getDescription().toUpperCase().trim() + "'," +
			district.getProvince().getCode()+ "," +
			"'" + district.getInUse().toUpperCase().trim() + "')";
		try{ 
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			int result = jdt.update(sql);	
		}
		catch (Exception ex) {
			throw new Exception("StudentPlacementDao : Error inserting TPUDIS / " + ex,ex);
		}	
	}
	

}
