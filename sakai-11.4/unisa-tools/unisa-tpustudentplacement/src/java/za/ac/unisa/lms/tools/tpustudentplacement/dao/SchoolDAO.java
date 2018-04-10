package za.ac.unisa.lms.tools.tpustudentplacement.dao;

import java.sql.Types;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;

import org.apache.commons.collections.map.ListOrderedMap;
import org.apache.struts.util.LabelValueBean;
import org.springframework.jdbc.core.JdbcTemplate;

import za.ac.unisa.lms.db.StudentSystemDAO;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.Contact;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.School;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.District;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.SchoolListRecord;

public class SchoolDAO extends StudentSystemDAO {
	                    databaseUtils dbutil;
	                    DistrictDAO   districtDAO;
	                    CountryDAO    countryDAO;
                      	public SchoolDAO(DistrictDAO districtDAO,CountryDAO countryDAO){
                                  dbutil=new databaseUtils();
                                  this.districtDAO=districtDAO;
                                  this.countryDAO=countryDAO;
                        }
                      	public SchoolDAO(){
                      		 dbutil=new databaseUtils();
                             districtDAO=new DistrictDAO();
                             countryDAO=new CountryDAO();
                        } 
                        public List getSchoolList(String type,String category,String country,Short province,Short district,
			                                      String filter) throws Exception {
		    	                                   String sql = "select a.code as schCode, a.name as schName, a.in_use_flag as schInUse, " +
		                                                        " d.eng_description as schType, e.eng_description as schCategory," +
		                                                        " b.code as schProvCode, b.eng_description as schProv, c.eng_description as schCountry, " +
		                                                        " f.code as schDistrictCode, f.eng_description as schDistrict" +
		                                                        " from tpusch a, prv b, lns c, gencod d, gencod e, ldd f" +
		                                                        " where  a.mk_country_code = c.code" +
		    	                                                " and  a.mk_prv_code = b.code  and a.mk_district_code = f.code"+
		    	                                                " and a.type_gc148 = d.code" +
		                                                        " and d.fk_gencatcode=148" +
		                                                        " and a.category_gc149 = e.code" +
		                            " and e.fk_gencatcode=149";
		
		                       if (type!=null && !type.trim().equalsIgnoreCase("") && !type.trim().equalsIgnoreCase("All")){
			                         sql = sql + " and d.code = '" + type + "'";
		                       }
		                	   if (category!=null && !category.trim().equalsIgnoreCase("") && !category.trim().equalsIgnoreCase("All")){
			                          sql = sql + " and e.code = '" + category + "'";
		                       }
		          		       if (country!=null && !country.trim().equalsIgnoreCase("")){
			                          sql = sql + " and c.code = '" + country + "'";
		                      }
		          		      if (province!=null && province.compareTo(Short.parseShort("0"))!=0){
			                               sql = sql  + " and b.code = " + province; 
		                             }
			                         if (district!=null && district!=0){
		    	                          sql = sql + " and f.code=" + district;
		                       }
		                       if (filter!=null && !filter.trim().equalsIgnoreCase("")){
			                        filter = filter.replaceAll("'", "''");
			                        sql = sql + " and a.name like '" + filter.trim().toUpperCase() + "'";
		                       }
		                       sql = sql + " order by a.name";
		                       List listSchool=getSchoolListFromDatabase(sql,country);
		                    return listSchool;		
	                    }
                        public List getSchoolList(String country,Short province,Short district) throws Exception {
                                 String sql = " select a.code as schCode a.name as schoolName, f.eng_description as dist, b.eng_description as prov"+
                                              " from tpusch a, prv b, lns c, gencod d, gencod e, ldd f" +
                                              " where  a.mk_country_code = c.code" +
                                              " and  a.mk_prv_code = b.code  and a.mk_district_code = f.code"+
                                              " and a.type_gc148 = d.code" +
                                              " and d.fk_gencatcode=148" +
                                              " and a.category_gc149 = e.code" +
                                              " and e.fk_gencatcode=149";
                                             if (country!=null && !country.trim().equalsIgnoreCase("")){
                                                  sql = sql + " and c.code = '" + country + "'";
                                             }
                                             if(country.equals(dbutil.saCode)){
		                                           if (province!=null && province.compareTo(Short.parseShort("0"))!=0){
                                                            sql = sql  + " and b.code = " + province; 
                                                   }
                                                   if (district!=null && district!=0){
                                                       sql = sql + " and f.code=" + district;
                                                   }
                                             }
                                             List listSchool=getSchoolListFromDatabase(sql);
                                             return listSchool;		
                        }
                        public List getSchoolCodeList(String country,Short province,Short district) throws Exception {
                            String sql = " select a.code as schCode "+
                                         " from tpusch a, prv b, lns c, gencod d, gencod e, ldd f" +
                                         " where  a.mk_country_code = c.code" +
                                         " and  a.mk_prv_code = b.code  and a.mk_district_code = f.code"+
                                         " and a.type_gc148 = d.code" +
                                         " and d.fk_gencatcode=148" +
                                         " and a.category_gc149 = e.code" +
                                         " and e.fk_gencatcode=149";
                                        if (country!=null && !country.trim().equalsIgnoreCase("")){
                                             sql = sql + " and c.code = '" + country + "'";
                                        }
                                        if(country.equals(dbutil.saCode)){
	                                           if (province!=null && province.compareTo(Short.parseShort("0"))!=0){
                                                       sql = sql  + " and b.code = " + province; 
                                              }
                                              if (district!=null && district!=0){
                                                  sql = sql + " and f.code=" + district;
                                              }
                                        }
                                        List listSchool=getSchoolCodeListFromDatabase(sql);
                                        return listSchool;		
                   }
                   
                        private List getSchoolListFromDatabase(String sql){
             		                       databaseUtils dbutil=new databaseUtils();
             		                       List listSchool  = new ArrayList<SchoolListRecord>();
             		                       List queryList = new ArrayList();
             		                       JdbcTemplate jdt = new JdbcTemplate(getDataSource());
             		                       queryList = jdt.queryForList(sql);
             		                       for (int i=0; i<queryList.size();i++){
             			                          ListOrderedMap data = (ListOrderedMap) queryList.get(i);
             			                          SchoolListRecord  schoolListRecord=new  SchoolListRecord();
             			                          schoolListRecord.setCode(Integer.parseInt(dbutil.replaceNull(data.get("schCode").toString())));
             			                          schoolListRecord.setDistrict(dbutil.replaceNull(data.get("dist").toString()));
             			                          schoolListRecord.setProvince(dbutil.replaceNull(data.get("prov").toString()));
             			                          schoolListRecord.setName(dbutil.replaceNull(data.get("schoolName").toString()));
             			                          listSchool.add(schoolListRecord);	
             			                   }
             		                       return listSchool; 
             	        }
                        private List getSchoolCodeListFromDatabase(String sql){
		                       databaseUtils dbutil=new databaseUtils();
		                       List listSchool  = new ArrayList<SchoolListRecord>();
		                       List queryList = new ArrayList();
		                       JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		                       queryList = jdt.queryForList(sql);
		                       for (int i=0; i<queryList.size();i++){
			                          ListOrderedMap data = (ListOrderedMap) queryList.get(i);
			                          SchoolListRecord  schoolListRecord=new  SchoolListRecord();
			                          listSchool.add( dbutil.replaceNull(data.get("schCode").toString()));	
			                   }
		                       return listSchool; 
	                    }
                        public   int  getSchCountryCode(int schoolCode) throws Exception {
                        	                String sql ="select mk_country_code from tpusch  where code="+schoolCode;
                        	                JdbcTemplate jdt = new JdbcTemplate(getDataSource());
                        	    			List queryList = jdt.queryForList(sql);
                        	    			
                        	    			Iterator i = queryList.iterator();
                        	    			int countryCode=-1;
                        	    			while (i.hasNext()) {
                        	    				   ListOrderedMap data = (ListOrderedMap) i.next();
                        	    				   countryCode=Integer.parseInt(dbutil.replaceNull(data.get("mk_country_code")));
                        	    				   break;
                        	    			}
                        	    			return countryCode;
                        }
                        public   String   getSchoolCountryName(int countryCode) throws Exception {
        	                String sql ="select eng_description from lns  where code="+countryCode;
        	                JdbcTemplate jdt = new JdbcTemplate(getDataSource());
        	    			List queryList = jdt.queryForList(sql);
        	    			
        	    			Iterator i = queryList.iterator();
        	    			String  countryName="";
        	    			while (i.hasNext()) {
        	    				   ListOrderedMap data = (ListOrderedMap) i.next();
        	    				   countryName=dbutil.replaceNull(data.get("eng_description"));
        	    				   break;
        	    			}
        	    			return countryName;
                      }
   public List getSchoolList(String type,String category,String country,String filter) throws Exception {
		    	  String sql ="select a.code as schCode, a.name as schName, a.in_use_flag as schInUse,"+ 
                              " d.eng_description as schType, e.eng_description as schCategory,"+
                              " c.eng_description as schCountry,a.mk_prv_code as schProvCode,"+ 
                              " a.town as town"+
                              " from tpusch a,lns c, gencod d, gencod e"+
                              " where  a.mk_country_code = c.code"+
                              " and a.mk_country_code ='"+country+"'"+
	                          " and a.type_gc148 = d.code"+
                              " and d.fk_gencatcode=148"+
                              " and a.category_gc149 = e.code"+
                              " and e.fk_gencatcode=149";
		                       if (type!=null && !type.trim().equalsIgnoreCase("") && !type.trim().equalsIgnoreCase("All")){
			                         sql = sql + " and d.code = '" + type + "'";
		                       }
		                	   if (category!=null && !category.trim().equalsIgnoreCase("") && !category.trim().equalsIgnoreCase("All")){
			                          sql = sql + " and e.code = '" + category + "'";
		                       }
		                	   if (filter!=null && !filter.trim().equalsIgnoreCase("")){
			                        filter = filter.replaceAll("'", "''");
			                        sql = sql + " and a.name like '" + filter.trim().toUpperCase() + "'";
		                       }
		          		       sql = sql + " order by a.name";
		                     List listSchool=getSchoolListFromDatabase(sql,country);
		                    return listSchool;		
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
	public String getTown(Integer code) throws Exception {
		            String sql= "select town from tpusch where code="+code;
		            String  errorMsg="SchoolDAO: Error accesing tpusch";
		       return dbutil.querySingleValue(sql, "town", errorMsg);
	}
	public String getSchoolDistrictCode(Integer code) throws Exception {
		                 if(!getSchoolCountryCode(code).equals(dbutil.saCode)){
		                	  return "";
		                 }
		                  String sql ="select mk_district_code" +
                                      " from tpusch  where code=" + code+ " and mk_country_code="+dbutil.saCode ;
		                   String  errorMsg="SchoolDAO: Error accesing tpusch";
		                   return dbutil.querySingleValue(sql, "mk_district_code", errorMsg);
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
				String surburb=dbutil.replaceNull(data.get("suburb"));
				school.setSuburb(surburb);
				school.setTown(dbutil.replaceNull(data.get("town")));
				if(!surburb.trim().equals("")){
					  school.setTown(surburb);
				}
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
				}
				school.setDistrict(district);
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
	
	public void insertSchool(School school) throws Exception {
		           String saCode="1015";
		           if(!school.getCountryCode().toUpperCase().trim().equals(saCode)){
		        	   school.setPostalCode("0"); 
		           }
		           String sql = "select max(code) + 1 from tpusch";
		           JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		           int schoolCode = jdt.queryForInt(sql) ;
		
		           Connection connection = null;
		           try{
			            jdt = new JdbcTemplate(getDataSource());
			            connection = jdt.getDataSource().getConnection();
			            connection.setAutoCommit(false);
			            Statement stmt = connection.createStatement();
			            PreparedStatement ps = connection.prepareStatement("insert into tpusch (code,name,type_gc148,category_gc149,agreement_flag,mk_country_code," +
					                            "mk_prv_code,mk_district_code,contact_name,suburb,town,in_use_flag)" +
					                             "values (?,?,?,?,?,?,?,?,?,?,?,?)");
			ps.setInt(1, schoolCode);
			ps.setString(2, school.getName().toUpperCase().trim());
			ps.setString(3, school.getType().toUpperCase().trim());
			ps.setString(4, school.getCategory().toUpperCase().trim());
			ps.setString(5, school.getAgreement().toUpperCase().trim());
			ps.setString(6, school.getCountryCode().toUpperCase().trim());
			if(school.getCountryCode().toUpperCase().trim().equals(saCode)){
			    ps.setShort(7, school.getDistrict().getProvince().getCode());
			    ps.setShort(8,school.getDistrict().getCode());
		    }else{
		    	ps.setShort(7,Short.parseShort("0"));
			    ps.setShort(8,Short.parseShort("0"));
		    }
			ps.setString(9, school.getContactName().toUpperCase().trim());
			ps.setString(10, school.getSuburb().toUpperCase().trim());
			ps.setString(11, school.getTown().toUpperCase().trim());
			ps.setString(12, school.getInUse().toUpperCase().trim());	
			ps.executeUpdate();
			//insert postal address
			if (school.getPostalAddress1()!=null && !school.getPostalAddress1().trim().equalsIgnoreCase("")){
    			ps = connection.prepareStatement("insert into adr (reference_no, address_line_1,address_line_2,address_line_3," +
						"address_line_4,address_line_5,address_line_6,postal_code,fk_adrcatypfk_adrc,fk_adrcatypfk_adrt," +
						"error_code,user_code)" +
						"values " +
						"(?,?,?,?,?,?,?,?,230,1,null,null)");
				ps.setInt(1, schoolCode);
				ps.setString(2, school.getPostalAddress1().toUpperCase().trim());
				ps.setString(3, school.getPostalAddress2().toUpperCase().trim());
				ps.setString(4, school.getPostalAddress3().toUpperCase().trim());
				ps.setString(5, school.getPostalAddress4().toUpperCase().trim());
				ps.setString(6, school.getPostalAddress5().toUpperCase().trim());
				ps.setString(7, school.getPostalAddress6().toUpperCase().trim());
				ps.setShort(8, Short.parseShort(school.getPostalCode().trim()));
				ps.executeUpdate();
				
			}			
			//insert physical address
			if (school.getPhysicalAddress1()!=null && !school.getPhysicalAddress1().trim().equalsIgnoreCase("")){
				Short postalCode=0;
				if (school.getPhysicalPostalCode()!=null && !school.getPhysicalPostalCode().trim().equalsIgnoreCase("")){
					postalCode=Short.parseShort(school.getPhysicalPostalCode());
				}

				ps = connection.prepareStatement("insert into adr (reference_no, address_line_1,address_line_2,address_line_3," +
						"address_line_4,address_line_5,address_line_6,postal_code,fk_adrcatypfk_adrc,fk_adrcatypfk_adrt," +
						"error_code,user_code)" +
						"values " +
						"(?,?,?,?,?,?,?,?,230,3,null,null)");
				ps.setInt(1, schoolCode);
				ps.setString(2, school.getPhysicalAddress1().toUpperCase().trim());
				ps.setString(3, school.getPhysicalAddress2().toUpperCase().trim());
				ps.setString(4, school.getPhysicalAddress3().toUpperCase().trim());
				ps.setString(5, school.getPhysicalAddress4().toUpperCase().trim());
				ps.setString(6, school.getPhysicalAddress5().toUpperCase().trim());
				ps.setString(7, school.getPhysicalAddress6().toUpperCase().trim());
				ps.setShort(8, Short.parseShort(school.getPostalCode().trim()));
				ps.executeUpdate();
			}			
			//insert contact details - adrph
			stmt.executeUpdate("insert into adrph (reference_no,home_number,work_number,fax_number," +
					"email_address,fk_adrcatcode,cell_number,courier_contact_no,cell_phone_verifie," +
					"email_verified)" +
					"values " +
					"(" + schoolCode + "," + 
					"' '," +
					"'" + school.getLandLineNr().trim() + "'," +
					"'" + school.getFaxNr().trim() + "'," +
					"'" + school.getEmailAddress().trim().toUpperCase() + "'," +
					"230," +
					"'" + school.getCellNr().trim() + "'," +
					"' '," +
					"'N'," +
					"'N')");	
			connection.commit();
			connection.setAutoCommit(true);	
			connection.close();
		}
		catch (Exception ex) {
			if (connection!=null){				
					connection.rollback();	
					connection.setAutoCommit(true);
					connection.close();
					throw new Exception("StudentPlacementDao : Error inserting records, records have been rollbacked / " + ex,ex);	
			}
		}		
	}
	public void updateSchool(School school) throws Exception {
		            String saCode="1015";
		            if(!school.getCountryCode().toUpperCase().trim().equals(saCode)){
	        	          school.setPostalCode("0"); 
	                }
		            Connection connection = null;
		            try{
			             JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			             connection = jdt.getDataSource().getConnection();
			             connection.setAutoCommit(false);
			             Statement stmt = connection.createStatement();			
			             PreparedStatement ps = connection.prepareStatement("update tpusch set " +
					             "name = ?,type_gc148 = ?,category_gc149 = ?,agreement_flag = ?,mk_country_code = ?," +
					             "mk_prv_code = ?,mk_district_code = ?,contact_name = ?,suburb = ?,town = ?,in_use_flag = ?" +
					             " where code = ?");
						ps.setString(1, school.getName().toUpperCase().trim());
			ps.setString(2, school.getType().toUpperCase().trim());
			ps.setString(3, school.getCategory().toUpperCase().trim());
			ps.setString(4, school.getAgreement().toUpperCase().trim());
			ps.setString(5, school.getCountryCode().toUpperCase().trim());
			if(school.getCountryCode().toUpperCase().trim().equals(saCode)){
			    ps.setShort(6, school.getDistrict().getProvince().getCode());
			    ps.setShort(7,school.getDistrict().getCode());
		    }else{
		    	ps.setShort(6,Short.parseShort("0"));
			    ps.setShort(7,Short.parseShort("0"));
		    }
			ps.setString(8, school.getContactName().toUpperCase().trim());
			ps.setString(9, school.getSuburb().toUpperCase().trim());
			ps.setString(10, school.getTown().toUpperCase().trim());
			ps.setString(11, school.getInUse().toUpperCase().trim());	
			ps.setInt(12, school.getCode());
			ps.executeUpdate();
			//read postal address to determine whether to insert or update the postal address record
			String sql = "select count(*)" +
			  " from adr" +
			  " where reference_no=" + school.getCode() +
			  " and fk_adrcatypfk_adrc=230" +
			  " and fk_adrcatypfk_adrt=1";
			jdt = new JdbcTemplate(getDataSource());
			int rowsReturn = jdt.queryForInt(sql) ;
			if (rowsReturn==0){
				//insert postal address
				if (school.getPostalAddress1()!=null && !school.getPostalAddress1().trim().equalsIgnoreCase("")){
					ps = connection.prepareStatement("insert into adr (reference_no, address_line_1,address_line_2,address_line_3," +
							"address_line_4,address_line_5,address_line_6,postal_code,fk_adrcatypfk_adrc,fk_adrcatypfk_adrt," +
							"error_code,user_code)" +
							"values " +
							"(?,?,?,?,?,?,?,?,230,1,null,null)");
					ps.setInt(1, school.getCode());
					ps.setString(2, school.getPostalAddress1().toUpperCase().trim());
					ps.setString(3, school.getPostalAddress2().toUpperCase().trim());
					ps.setString(4, school.getPostalAddress3().toUpperCase().trim());
					ps.setString(5, school.getPostalAddress4().toUpperCase().trim());
					ps.setString(6, school.getPostalAddress5().toUpperCase().trim());
					ps.setString(7, school.getPostalAddress6().toUpperCase().trim());
					ps.setShort(8, Short.parseShort(school.getPostalCode().trim()));
					ps.executeUpdate();
				}	
			}else{
				//update postal address
				ps = connection.prepareStatement("update adr set " +
						"address_line_1 = ?," +
						"address_line_2 = ?," +
						"address_line_3 = ?," +
						"address_line_4 = ?," +
						"address_line_5 = ?," +
						"address_line_6 = ?," +
						"postal_code = ?" +
						" where reference_no = ?" + 
						" and fk_adrcatypfk_adrc=230" +
						" and fk_adrcatypfk_adrt=1");
				
				ps.setString(1, school.getPostalAddress1().toUpperCase().trim());
				ps.setString(2, school.getPostalAddress2().toUpperCase().trim());
				ps.setString(3, school.getPostalAddress3().toUpperCase().trim());
				ps.setString(4, school.getPostalAddress4().toUpperCase().trim());
				ps.setString(5, school.getPostalAddress5().toUpperCase().trim());
				ps.setString(6, school.getPostalAddress6().toUpperCase().trim());
				ps.setShort(7, Short.parseShort(school.getPostalCode().trim()));
				ps.setInt(8, school.getCode());
				ps.executeUpdate();
			}		
			//read physical address to determine whether to insert or update the postal address record
			sql = "select count(*)" +
			  " from adr" +
			  " where reference_no=" + school.getCode() +
			  " and fk_adrcatypfk_adrc=230" +
			  " and fk_adrcatypfk_adrt=3";
			jdt = new JdbcTemplate(getDataSource());
			rowsReturn = jdt.queryForInt(sql) ;
			Short postalCode=0;
			if (school.getPhysicalPostalCode()!=null && !school.getPhysicalPostalCode().trim().equalsIgnoreCase("")){
				postalCode=Short.parseShort(school.getPhysicalPostalCode());
			}
			if (rowsReturn==0){
				//insert physical address
				if (school.getPhysicalAddress1()!=null && !school.getPhysicalAddress1().trim().equalsIgnoreCase("")){					
					ps = connection.prepareStatement("insert into adr (reference_no, address_line_1,address_line_2,address_line_3," +
							"address_line_4,address_line_5,address_line_6,postal_code,fk_adrcatypfk_adrc,fk_adrcatypfk_adrt," +
							"error_code,user_code)" +
							"values " +
							"(?,?,?,?,?,?,?,?,230,3,null,null)");
					ps.setInt(1, school.getCode());
					ps.setString(2, school.getPhysicalAddress1().toUpperCase().trim());
					ps.setString(3, school.getPhysicalAddress2().toUpperCase().trim());
					ps.setString(4, school.getPhysicalAddress3().toUpperCase().trim());
					ps.setString(5, school.getPhysicalAddress4().toUpperCase().trim());
					ps.setString(6, school.getPhysicalAddress5().toUpperCase().trim());
					ps.setString(7, school.getPhysicalAddress6().toUpperCase().trim());
					ps.setShort(8, Short.parseShort(school.getPostalCode().trim()));
					ps.executeUpdate();
				}	
			}else{
				//update physical address
				ps = connection.prepareStatement("update adr set " +
						"address_line_1 = ?," +
						"address_line_2 = ?," +
						"address_line_3 = ?," +
						"address_line_4 = ?," +
						"address_line_5 = ?," +
						"address_line_6 = ?," +
						"postal_code = ?" +
						" where reference_no = ?" + 
						" and fk_adrcatypfk_adrc=230" +
						" and fk_adrcatypfk_adrt=3");
				
				ps.setString(1, school.getPhysicalAddress1().toUpperCase().trim());
				ps.setString(2, school.getPhysicalAddress2().toUpperCase().trim());
				ps.setString(3, school.getPhysicalAddress3().toUpperCase().trim());
				ps.setString(4, school.getPhysicalAddress4().toUpperCase().trim());
				ps.setString(5, school.getPhysicalAddress5().toUpperCase().trim());
				ps.setString(6, school.getPhysicalAddress6().toUpperCase().trim());
	            ps.setShort(7, Short.parseShort(school.getPostalCode().trim()));
				ps.setShort(8,Short.parseShort(""+school.getCode()));
				ps.executeUpdate();
			}			
			//read adrph to determine whether to insert or update the adrph record
			sql = "select count(*)" +
			  " from adrph" +
			  " where reference_no=" + school.getCode() +
			  " and fk_adrcatcode=230";
			jdt = new JdbcTemplate(getDataSource());
			rowsReturn = jdt.queryForInt(sql) ;
			if (rowsReturn==0){
				//insert contact details - adrph
				stmt.executeUpdate("insert into adrph (reference_no,home_number,work_number,fax_number," +
						"email_address,fk_adrcatcode,cell_number,courier_contact_no,cell_phone_verifie," +
						"email_verified)" +
						"values " +
						"(" + school.getCode() + "," + 
						"' '," +
						"'" + school.getLandLineNr().trim() + "'," +
						"'" + school.getFaxNr().trim() + "'," +
						"'" + school.getEmailAddress().toUpperCase().trim() + "'," +
						"230," +
						"'" + school.getCellNr().trim() + "'," +
						"' '," +
						"'N'," +
						"'N')");	
			}else{
				   //update contact details - adrph
				   stmt.executeUpdate("update adrph set " +
						"work_number = '" + school.getLandLineNr().trim() + "'," +
						"fax_number = '" + school.getFaxNr().trim() + "'," +
						"email_address = '" + school.getEmailAddress().toUpperCase().trim() + "'," +
						"cell_number ='" + school.getCellNr().trim() + "'" +
						" where reference_no = " + school.getCode() + 
						" and fk_adrcatcode=230");
			}			
			connection.commit();
			connection.setAutoCommit(true);	
			connection.close();
		}
		catch (Exception ex) {
			if (connection!=null){				
					connection.rollback();	
					connection.setAutoCommit(true);
					connection.close();
					throw new Exception("StudentPlacementDao : Error updating school records, records have been rollbacked / " + ex,ex);	
			}
		}		
	}
	private List getSchoolListFromDatabase(String sql,String country){
		       databaseUtils dbutil=new databaseUtils();
		       List listSchool  = new ArrayList<SchoolListRecord>();
		       List queryList = new ArrayList();
		       JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		       queryList = jdt.queryForList(sql);
		       for (int i=0; i<queryList.size();i++){
			         SchoolListRecord school = new SchoolListRecord();	
			         ListOrderedMap data = (ListOrderedMap) queryList.get(i);
			         school.setCode(Integer.parseInt(data.get("schCode").toString()));
			         school.setName(data.get("schName").toString());
			         school.setType(data.get("schType").toString());
			         school.setCategory(data.get("schCategory").toString());
			         school.setCountry(data.get("schCountry").toString());
			         if(country.equals(dbutil.saCode)){
			             school.setProvinceCode(Short.parseShort(data.get("schProvCode").toString()));
				         school.setProvince(data.get("schProv").toString());
			             school.setDistrictCode(Short.parseShort(data.get("schDistrictCode").toString()));
					     school.setDistrict(data.get("schDistrict").toString());
			         }else{
			        	    school.setProvince("");
			                school.setDistrict("");
			                school.setTown(data.get("town").toString());
			         }
					 school.setInUse(data.get("schInUse").toString());
			         listSchool.add(school);						
			   }
		       return listSchool; 
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
