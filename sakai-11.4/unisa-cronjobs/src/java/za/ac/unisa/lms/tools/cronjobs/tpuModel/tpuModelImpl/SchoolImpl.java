package za.ac.unisa.lms.tools.cronjobs.tpuModel.tpuModelImpl;

import za.ac.unisa.lms.tools.cronjobs.dao.tpuDAO.SchoolDAO;
import za.ac.unisa.lms.tools.cronjobs.forms.model.School;

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
	
}
