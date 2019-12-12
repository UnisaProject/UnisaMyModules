package za.ac.unisa.lms.tools.cronjobs.dao.tpuDAO;


import java.util.Iterator;
import java.util.List;
import org.apache.commons.collections.map.ListOrderedMap;
import org.springframework.jdbc.core.JdbcTemplate;
import za.ac.unisa.lms.db.StudentSystemDAO;
import za.ac.unisa.lms.tools.cronjobs.forms.model.Contact;
import za.ac.unisa.lms.tools.cronjobs.forms.model.School;
import za.ac.unisa.lms.tools.cronjobs.forms.model.District;

public class SchoolDAO extends StudentSystemDAO {
	                    databaseUtils dbutil;
	                    DistrictDAO   districtDAO;
	                    CountryDAO    countryDAO;
                      	public SchoolDAO(){
                      		 dbutil=new databaseUtils();
                             districtDAO=new DistrictDAO();
                             countryDAO=new CountryDAO();
                        } 
 
	public String getSchoolCountry(int schoolCode)  throws Exception{
                       return countryDAO.getSchoolCountry(schoolCode);
	}
	 public String getSchoolCountryCode(int schoolCode)throws Exception {
		             return countryDAO.getSchoolCountryCode(schoolCode);

   }
	public String  getEmailAddress(int code) throws Exception {
		             String email="";
		             String sql= "select work_number,fax_number,email_address,cell_number" +
		                         " from adrph" +
		                         " where reference_no=" + code +
		                         " and fk_adrcatcode=230";
		             String errorMsg="SchoolDAO of StudentPlacement : Error reading ADRPH / ";
			         email= dbutil.querySingleValue(sql,"email_address",errorMsg);
		return email;
	}
	public School getSchool(Integer code, String name,String countryCode) throws Exception {
    	            
		
		School school = new School();
		
		String sql ="";
		    if(countryCode.equals("1015")){
		       sql= "select code,name,type_gc148,category_gc149,agreement_flag," +
			        " mk_country_code,mk_prv_code,mk_district_code,contact_name,suburb,town,in_use_flag" +
			        " from tpusch";
		    }else{
		    	sql= "select code,name,type_gc148,category_gc149,agreement_flag," +
		             " mk_country_code,contact_name,suburb,town,in_use_flag" +
		             " from tpusch";
		    }
		
		if (code!=null && code!=0){
			sql = sql + " where code=" + code;
		}else if (name!=null && !name.equalsIgnoreCase("")){
			name = name.replaceAll("'", "''");
			sql = sql + " where name ='" + name.toUpperCase().trim() + "'";
		}		
		
		try{ 
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(sql);
		
			Iterator i = queryList.iterator();
			while (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				
				school.setCode(Integer.parseInt(data.get("code").toString()));
				school.setName(dbutil.replaceNull(data.get("name")));
				school.setType(dbutil.replaceNull(data.get("type_gc148")));
				school.setCategory(dbutil.replaceNull(data.get("category_gc149")));
				school.setAgreement(dbutil.replaceNull(data.get("agreement_flag")));			
				school.setCountryCode(dbutil.replaceNull(data.get("mk_country_code")));				
				school.setContactName(dbutil.replaceNull(data.get("contact_name")));
				school.setSuburb(dbutil.replaceNull(data.get("suburb")));
				school.setTown(dbutil.replaceNull(data.get("town")));
				school.setInUse(dbutil.replaceNull(data.get("in_use_flag")));
				school.setPostalAddress1("");
				school.setPostalAddress2("");
				school.setPostalAddress3("");
				school.setPostalAddress4("");
				school.setPostalAddress5("");
				school.setPostalAddress6("");
				school.setPhysicalAddress1("");
				school.setPhysicalAddress2("");
				school.setPhysicalAddress3("");
				school.setPhysicalAddress4("");
				school.setPhysicalAddress5("");
				school.setPhysicalAddress6("");
				school.setPhysicalPostalCode("");
				school.setEmailAddress("");
				school.setCellNr("");
				school.setLandLineNr("");
				//Get district
				District district = new District();
				if(countryCode.equals(dbutil.saCode)){
				      district = districtDAO.getDistrict(Short.parseShort(data.get("mk_district_code").toString()), null);
				      school.setDistrict(district);
				}else{
					  school.setDistrict(district);
				}
				//Get postal address
				String postalCodeStr="";
				if(countryCode.equals("1015")){
					postalCodeStr=",postal_code";
				}
				String sqlPostal = "select address_line_1,address_line_2,address_line_3,address_line_4,address_line_5,address_line_6" +postalCodeStr+
				 		           " from adr" +
					               " where reference_no=" + school.getCode() +
					               " and fk_adrcatypfk_adrc=230" +
					               " and fk_adrcatypfk_adrt=1";
				try{ 
					queryList = jdt.queryForList(sqlPostal);
					Iterator j = queryList.iterator();
					while (j.hasNext()) {
						ListOrderedMap dataPostal = (ListOrderedMap) j.next();
						school.setPostalAddress1(dbutil.replaceNull(dataPostal.get("address_line_1")));
						school.setPostalAddress2(dbutil.replaceNull(dataPostal.get("address_line_2")));
						school.setPostalAddress3(dbutil.replaceNull(dataPostal.get("address_line_3")));
						school.setPostalAddress4(dbutil.replaceNull(dataPostal.get("address_line_4")));
						school.setPostalAddress5(dbutil.replaceNull(dataPostal.get("address_line_5")));
						school.setPostalAddress6(dbutil.replaceNull(dataPostal.get("address_line_6")));
						if(countryCode.equals(dbutil.saCode)){
						     school.setPostalCode(dbutil.replaceNull(dataPostal.get("postal_code")));
						  	 while (school.getPostalCode().length() < 4){
							      school.setPostalCode("0" + school.getPostalCode());
						     }
						}
					}
				}
				catch (Exception ex) {
					throw new Exception("StudentPlacementDAO : : Error reading Postal ADR / " + ex);
				}
				//Get physical address
				String sqlPhysical = "select address_line_1,address_line_2,address_line_3,address_line_4,address_line_5,address_line_6"+ postalCodeStr+
				                     " from adr" +
				                     " where reference_no=" + school.getCode() +
				                     " and fk_adrcatypfk_adrc=230" +
				                     " and fk_adrcatypfk_adrt=3";
				try{ 
					queryList = jdt.queryForList(sqlPhysical);
					Iterator j = queryList.iterator();
					while (j.hasNext()) {
						ListOrderedMap dataPhysical = (ListOrderedMap) j.next();
						school.setPhysicalAddress1(dbutil.replaceNull(dataPhysical.get("address_line_1")));
						school.setPhysicalAddress2(dbutil.replaceNull(dataPhysical.get("address_line_2")));
						school.setPhysicalAddress3(dbutil.replaceNull(dataPhysical.get("address_line_3")));
						school.setPhysicalAddress4(dbutil.replaceNull(dataPhysical.get("address_line_4")));
						school.setPhysicalAddress5(dbutil.replaceNull(dataPhysical.get("address_line_5")));
						school.setPhysicalAddress6(dbutil.replaceNull(dataPhysical.get("address_line_6")));
						if(countryCode.equals("1015")){
						        school.setPhysicalPostalCode(dbutil.replaceNull(dataPhysical.get("postal_code")));
						        while (school.getPhysicalPostalCode().length() < 4){
							             school.setPhysicalPostalCode("0" + school.getPhysicalPostalCode());
						        }
						}
					}
				}
				catch (Exception ex) {
					throw new Exception("StudentPlacementDAO : : Error reading Physical ADR / " + ex);
				}
				//Get contact data
				String sqlContact = "select work_number,fax_number,email_address,cell_number" +
				  " from adrph" +
				  " where reference_no=" + school.getCode() +
				  " and fk_adrcatcode=230";
				try{ 
					queryList = jdt.queryForList(sqlContact);
					Iterator j = queryList.iterator();
					while (j.hasNext()) {
						ListOrderedMap dataContact = (ListOrderedMap) j.next();
						school.setLandLineNr(dbutil.replaceNull(dataContact.get("work_number")));
						school.setEmailAddress(dbutil.replaceNull(dataContact.get("email_address")));
						school.setCellNr(dbutil.replaceNull(dataContact.get("cell_number")));
						school.setFaxNr(dbutil.replaceNull(dataContact.get("fax_number")));
					}
				}
				catch (Exception ex) {
					throw new Exception("StudentPlacementDAO : : Error reading ADRPH / " + ex);
				}			
				break;
			}
		}
		catch (Exception ex) {
			throw new Exception("StudentPlacementDao : Error reading table TPUSCH / " + ex,ex);						
		}		
		return school;		
	}
	
	

	 public  String getSchoolContactNumber(int schoolCode) throws Exception{
		 ContactDAO contactDAO=new ContactDAO();
		 Contact contact  = contactDAO.getContactDetails(schoolCode,230);
         String contactNum="";
         if (contact.getCellNumber()!=null && !contact.getCellNumber().trim().equalsIgnoreCase("")){
                  contactNum=contact.getCellNumber();
         }else if (contact.getWorkNumber()!=null && !contact.getWorkNumber().trim().equalsIgnoreCase("")){
                     contactNum=contact.getWorkNumber();
         }
         if (contactNum==null){
                  contactNum="";
         }
  return contactNum;
}

	

}
