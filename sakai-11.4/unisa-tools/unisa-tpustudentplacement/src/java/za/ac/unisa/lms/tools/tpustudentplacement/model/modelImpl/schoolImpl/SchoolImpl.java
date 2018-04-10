package za.ac.unisa.lms.tools.tpustudentplacement.model.modelImpl.schoolImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.map.ListOrderedMap;
import org.springframework.jdbc.core.JdbcTemplate;

import za.ac.unisa.lms.tools.tpustudentplacement.dao.ContactDAO;
import za.ac.unisa.lms.tools.tpustudentplacement.dao.SchoolDAO;
import za.ac.unisa.lms.tools.tpustudentplacement.dao.databaseUtils;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.Contact;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.District;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.School;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.SchoolListRecord;

public class SchoolImpl {
	
	  SchoolDAO dao;
	  public SchoolImpl(){
		       dao=new SchoolDAO(); 
	  }
	  public String getEmailAddress(int code) throws Exception{
		return  dao.getEmailAddress(code);
  	 }
	 public String getSchoolCountryCode(int schoolCode)throws Exception {
         return dao.getSchoolCountryCode(schoolCode);

     }
	 public School getSchool(Integer code, String name,String countryCode) throws Exception {
         return dao. getSchool(code,name,countryCode);
	 }
	 public  String getSchoolContactNumber(int schoolCode) throws Exception{
		 return dao.getSchoolContactNumber(schoolCode); 
	 }
	 public String getTown(Integer code) throws Exception {
		              return dao.getTown(code);
	 }
	 public School getSchool(Integer code, String name) throws Exception {
                       return getSchool(code,name, getSchoolCountryCode(code));
     }
	 public   int  getSchCountryCode(int schoolCode) throws Exception {
		             return dao.getSchCountryCode(schoolCode);
     }
	 public   boolean   isSchoolLocal(int schoolCode) throws Exception {
                            if(getSchCountryCode(schoolCode)==1015){
        	                       return true;
                            }else{
        	                       return false;
                           }
     }
	 public   String   getSchoolCountryName(int countryCode) throws Exception {
		                 return dao.getSchoolCountryName(countryCode);
     }
	 public List getSchoolList(String type,String category,String country,Short province,Short district,
              String filter) throws Exception {
              return dao.getSchoolList(type, category, country, province, district, filter);
    }
	public List getSchoolList(String country,Short province,Short district) throws Exception {
              return dao.getSchoolList(country, province, district);
   }
   public List getSchoolList(String type,String category,String country,String filter) throws Exception {
                  return dao.getSchoolList(type, category, country, filter);	
   }
   public String getSchoolCountry(int schoolCode)  throws Exception{
                      return dao.getSchoolCountry(schoolCode);
   }
   public void insertSchool(School school) throws Exception {
                  dao.insertSchool(school);
   }
   public void updateSchool(School school) throws Exception {
         dao.updateSchool(school);
   }
   public String getSchoolDistrictCode(Integer code) throws Exception {
	                return dao.getSchoolDistrictCode(code);
   }
   public List getSchoolCodeList(String country,Short province,Short district) throws Exception {
                          return dao.getSchoolCodeList(country, province, district);		
   }
   
}
