package za.ac.unisa.lms.tools.tpustudentplacement.forms;
import za.ac.unisa.lms.tools.tpustudentplacement.dao.CountryDAO;
import java.util.List;

public class Country {
	private String code;
	private String description;
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public List listAllCountries()throws Exception{ 
		            CountryDAO dao=new CountryDAO();
		       return dao.getCountryList();
	}
	
	public String getSchoolCountry(int schoolCode)throws Exception {
		               CountryDAO dao=new CountryDAO();
		               return dao.getSchoolCountry(schoolCode);
    }
    public String getSchoolCountryCode(int schoolCode)throws Exception {
    	               CountryDAO dao=new CountryDAO();
                       return dao.getSchoolCountryCode(schoolCode);
    }
    public List getCountryList() throws Exception {
    	           CountryDAO dao=new CountryDAO();
                   return dao.getCountryList();
   }
	
}
