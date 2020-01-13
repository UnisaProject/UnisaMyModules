package za.ac.unisa.lms.tools.tpustudentplacement.dao;
import java.sql.Types;
import za.ac.unisa.lms.tools.tpustudentplacement.model.modelImpl.ContactUI;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.map.ListOrderedMap;
import org.springframework.jdbc.core.JdbcTemplate;
import org.apache.struts.util.LabelValueBean;
import za.ac.unisa.lms.db.StudentSystemDAO;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.District;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.Province;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.SupervisorAreaRecord;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.SupervisorListRecord;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.Supervisor;
import za.ac.unisa.lms.tools.tpustudentplacement.utils.DateUtil;
import za.ac.unisa.lms.tools.tpustudentplacement.utils.FileExtractorClass;
import za.ac.unisa.lms.tools.tpustudentplacement.utils.PlacementUtilities;
public class SupervisorDAO extends StudentSystemDAO {
             
	                databaseUtils dbutil;
                    DistrictDAO   districtDAO;
                    CountryDAO    countryDAO;
                    SupervisorFileWriter  supervisorFileWriter;
                    FileExtractorClass fileExtractorClass;
            	    public SupervisorDAO(){
                              dbutil=new databaseUtils();
                              districtDAO=new DistrictDAO();
                              countryDAO=new CountryDAO();
                              supervisorFileWriter=new SupervisorFileWriter();
                              fileExtractorClass=new FileExtractorClass();
                    } 
            	    public void writeFile(List<SupervisorListRecord> records,String  fileName,String countryCode){
            	    	                        supervisorFileWriter.writeFile(records,fileName,countryCode);
            	    }
            	    public void  extractFile(HttpServletRequest request,
    			                     HttpServletResponse response,String fileToRead,
    			                     String outputFileName){
            	    	                   fileExtractorClass.extractFile(request,response,fileToRead,outputFileName);
    	            }
            	    public String saveAllocationAllowed(int supervisorCode,int studentsAllowed,int year,int semester)throws Exception {
            	    	              String sql = "select count(*) as total   from tpusuptot  where" +
            	    	                           " tpusuptot.mk_supervisor_code="+supervisorCode+" and tpusuptot.mk_academic_year="+
            	    	                           year+" and  tpusuptot.semester_period="+semester;
            	    	              String errorMsg="SupervisorDAO: Error saving data to tpusuptot";
            	    	              String total=dbutil.querySingleValue(sql,"total",errorMsg);
            	    	              if(total.equals("")||total.equals("0")){
            	    	            	  insertAllocationAllowed(supervisorCode,studentsAllowed,year,semester);
            	    	            	  return " The record has  been added successfully";
            	    	              }else{
            	    	            	  updateAllocationAllowed(supervisorCode,studentsAllowed,year,semester);
            	    	            	  return "The record   has been updated successfully";
            	    	              }
            	 	}
            	    
            	    private void insertAllocationAllowed(int supervisorCode,int studentsAllowed,int year,int semester)throws Exception {
            	    	            String sql="Insert into tpusuptot(mk_academic_year,mk_supervisor_code,semester_period,tot_stud_alloc)values("+
            	    	                       year+","+supervisorCode+","+semester+","+studentsAllowed+")";
            	    	            String errorMsg="SupervisorDAO: Error inserting into tpusuptot";
     	                            dbutil.update(sql,errorMsg);
                   }
            	    private void updateAllocationAllowed(int supervisorCode,int studentsAllowed,int year,int semester)throws Exception {
	    	                           String sql="update  tpusuptot set  tot_stud_alloc="+studentsAllowed+" where mk_academic_year="+year+
	    	                           " and mk_supervisor_code="+supervisorCode+" and semester_period="+semester;
	    	                           String errorMsg="SupervisorDAO: Error updating tpusuptot";
                                      dbutil.update(sql,errorMsg);
                    }
                    public void updateSupervisor(Supervisor supervisor) throws Exception {
		 
		                         if (supervisor.getContract().equalsIgnoreCase("N")){
			                              supervisor.setContractStart("");
			                              supervisor.setContractEnd("");
		                         }
		                         if(!supervisor.getCountryCode().equals(PlacementUtilities.getSaCode())){
			                              supervisor.setPostalCode("0");    
		                         }
		                         Connection connection = null;
			                     try{
				                      JdbcTemplate jdt = new JdbcTemplate(getDataSource());
				                      connection = jdt.getDataSource().getConnection();
				                      connection.setAutoCommit(false);
				                      Statement stmt = connection.createStatement();			
				                      PreparedStatement ps = connection.prepareStatement("update tpusup set " +
						"mk_title = ?," +
						"initials = ?," +
						"surname = ?," +
						"occupation = ?," +
						"contract = ?," +
						"contract_start = to_date(?,'YYYY/MM/DD')," +
						"contract_end = to_date(?,'YYYY/MM/DD')," +
						"mk_country_code = ?" +
						" where code = ?");
				
				ps.setString(1, supervisor.getTitle().toUpperCase().trim());
				ps.setString(2, supervisor.getInitials().toUpperCase().trim());
				ps.setString(3, supervisor.getSurname().toUpperCase().trim());
				ps.setString(4, supervisor.getOccupation().toUpperCase().trim());
				ps.setString(5, supervisor.getContract().toUpperCase().trim());
				ps.setString(6, supervisor.getContractStart().toUpperCase().trim());
				ps.setString(7, supervisor.getContractEnd().toUpperCase().trim());
				ps.setString(8, supervisor.getCountryCode().trim());
				ps.setInt(9, supervisor.getCode());
				ps.executeUpdate();
				//read postal address to determine whether to insert or update the postal address record
				String sql = "select count(*)" +
				  " from adr" +
				  " where reference_no=" + supervisor.getCode() +
				  " and fk_adrcatypfk_adrc=231" +
				  " and fk_adrcatypfk_adrt=1";
				jdt = new JdbcTemplate(getDataSource());
				int rowsReturn = jdt.queryForInt(sql) ;
				if (rowsReturn==0){
					//insert postal address
					if (supervisor.getPostalAddress1()!=null && !supervisor.getPostalAddress1().trim().equalsIgnoreCase("")){
						ps = connection.prepareStatement("insert into adr (reference_no, address_line_1,address_line_2,address_line_3," +
								"address_line_4,address_line_5,address_line_6,postal_code,fk_adrcatypfk_adrc,fk_adrcatypfk_adrt," +
								"error_code,user_code)" +
								"values " +
								"(?,?,?,?,?,?,?,?,231,1,null,null)");
						ps.setInt(1, supervisor.getCode());
						ps.setString(2, supervisor.getPostalAddress1().toUpperCase().trim());
						ps.setString(3, supervisor.getPostalAddress2().toUpperCase().trim());
						ps.setString(4, supervisor.getPostalAddress3().toUpperCase().trim());
						ps.setString(5, supervisor.getPostalAddress4().toUpperCase().trim());
						ps.setString(6, supervisor.getPostalAddress5().toUpperCase().trim());
						ps.setString(7, supervisor.getPostalAddress6().toUpperCase().trim());
						ps.setShort(8, Short.parseShort(supervisor.getPostalCode().trim()));
						ps.executeUpdate();
					}		
				}else{
					//update postal address
					if (supervisor.getPostalAddress1()!=null && !supervisor.getPostalAddress1().trim().equalsIgnoreCase("")){
     					ps = connection.prepareStatement("update adr set " +
								"address_line_1 = ?," +
								"address_line_2 = ?," +
								"address_line_3 = ?," +
								"address_line_4 = ?," +
								"address_line_5 = ?," +
								"address_line_6 = ?," +
								"postal_code = ?" +
								" where reference_no = ?" + 
								" and fk_adrcatypfk_adrc=231" +
								" and fk_adrcatypfk_adrt=1");
						
						ps.setString(1, supervisor.getPostalAddress1().toUpperCase().trim());
						ps.setString(2, supervisor.getPostalAddress2().toUpperCase().trim());
						ps.setString(3, supervisor.getPostalAddress3().toUpperCase().trim());
						ps.setString(4, supervisor.getPostalAddress4().toUpperCase().trim());
						ps.setString(5, supervisor.getPostalAddress5().toUpperCase().trim());
						ps.setString(6, supervisor.getPostalAddress6().toUpperCase().trim());
						ps.setShort(7, Short.parseShort(supervisor.getPostalCode().trim()));
						ps.setInt(8, supervisor.getCode());
						ps.executeUpdate();
					}else{
					//delete postal address	
						stmt.executeUpdate("delete from adr " +
								" where reference_no = " + supervisor.getCode() + 
								" and fk_adrcatypfk_adrc=231" +
								" and fk_adrcatypfk_adrt=1");
					}
					
				}
				
				//read physical address to determine whether to insert or update the physical address record
				sql = "select count(*)" +
				  " from adr" +
				  " where reference_no=" + supervisor.getCode() +
				  " and fk_adrcatypfk_adrc=231" +
				  " and fk_adrcatypfk_adrt=3";
				jdt = new JdbcTemplate(getDataSource());
				rowsReturn = jdt.queryForInt(sql) ;
				Short postalCode=0;
				if (supervisor.getPhysicalPostalCode()!=null && !supervisor.getPhysicalPostalCode().trim().equalsIgnoreCase("")){
					postalCode=Short.parseShort(supervisor.getPhysicalPostalCode());
				}
				if (rowsReturn==0){
					//insert physical address
					if (supervisor.getPhysicalAddress1()!=null && !supervisor.getPhysicalAddress1().trim().equalsIgnoreCase("")){
							ps = connection.prepareStatement("insert into adr (reference_no, address_line_1,address_line_2,address_line_3," +
									"address_line_4,address_line_5,address_line_6,postal_code,fk_adrcatypfk_adrc,fk_adrcatypfk_adrt," +
									"error_code,user_code)" +
									"values " +
									"(?,?,?,?,?,?,?,?,231,3,null,null)");
							ps.setInt(1, supervisor.getCode());
							ps.setString(2, supervisor.getPhysicalAddress1().toUpperCase().trim());
							ps.setString(3, supervisor.getPhysicalAddress2().toUpperCase().trim());
							ps.setString(4, supervisor.getPhysicalAddress3().toUpperCase().trim());
							ps.setString(5, supervisor.getPhysicalAddress4().toUpperCase().trim());
							ps.setString(6, supervisor.getPhysicalAddress5().toUpperCase().trim());
							ps.setString(7, supervisor.getPhysicalAddress6().toUpperCase().trim());
							ps.setShort(8, postalCode);
							ps.executeUpdate();
						}
				}else{
					//update physical address
					if (supervisor.getPhysicalAddress1()!=null && !supervisor.getPhysicalAddress1().trim().equalsIgnoreCase("")){
						ps = connection.prepareStatement("update adr set " +
								"address_line_1 = ?," +
								"address_line_2 = ?," +
								"address_line_3 = ?," +
								"address_line_4 = ?," +
								"address_line_5 = ?," +
								"address_line_6 = ?," +
								"postal_code = ?" +
								" where reference_no = ?" + 
								" and fk_adrcatypfk_adrc=231" +
								" and fk_adrcatypfk_adrt=3");
						
						ps.setString(1, supervisor.getPhysicalAddress1().toUpperCase().trim());
						ps.setString(2, supervisor.getPhysicalAddress2().toUpperCase().trim());
						ps.setString(3, supervisor.getPhysicalAddress3().toUpperCase().trim());
						ps.setString(4, supervisor.getPhysicalAddress4().toUpperCase().trim());
						ps.setString(5, supervisor.getPhysicalAddress5().toUpperCase().trim());
						ps.setString(6, supervisor.getPhysicalAddress6().toUpperCase().trim());
						ps.setShort(7, postalCode);
						ps.setInt(8, supervisor.getCode());
						ps.executeUpdate();
					}else{
							//delete physical address
							stmt.executeUpdate("delete from adr " +
									" where reference_no = " + supervisor.getCode() + 
									" and fk_adrcatypfk_adrc=231" +
									" and fk_adrcatypfk_adrt=3");
					}
				}	
				//read adrph to determine whether to insert or update the adrph record
				sql = "select count(*)" +
				  " from adrph" +
				  " where reference_no=" + supervisor.getCode() +
				  " and fk_adrcatcode=231";
				jdt = new JdbcTemplate(getDataSource());
				rowsReturn = jdt.queryForInt(sql) ;
				if (rowsReturn==0){
					//insert contact details - adrph
					stmt.executeUpdate("insert into adrph (reference_no,home_number,work_number,fax_number," +
							"email_address,fk_adrcatcode,cell_number,courier_contact_no,cell_phone_verifie," +
							"email_verified)" +
							"values " +
							"(" + supervisor.getCode() + "," + 
							"' '," +
							"'" + supervisor.getLandLineNr().trim() + "'," +
							"'" + supervisor.getFaxNr().trim() + "'," +
							"'" + supervisor.getEmailAddress().toUpperCase().trim() + "'," +
							"231," +
							"'" + supervisor.getCellNr().trim() + "'," +
							"' '," +
							"'N'," +
							"'N')");	
				}else{
					//update contact details - adrph
					stmt.executeUpdate("update adrph set " +
							"work_number = '" + supervisor.getLandLineNr().trim() + "'," +
							"fax_number = '" + supervisor.getFaxNr().trim() + "'," +
							"email_address = '" + supervisor.getEmailAddress().toUpperCase().trim() + "'," +
							"cell_number ='" + supervisor.getCellNr().trim() + "'" +
							" where reference_no = " + supervisor.getCode() + 
							" and fk_adrcatcode=231");
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
						throw new Exception("StudentPlacementDao : Error updating supervisor records, records have been rollbacked / " + ex,ex);	
				}
			}		
		}

		public ArrayList getPostalCodeList(String searchType, String searchCriteria, String postalType) throws Exception {
			//Construct list object of postal codes and suburbs
			List postalCodeList = new ArrayList();
			ArrayList postalCodes = new ArrayList();
			String streetOrBox = "B";
			//Pstcod cod = new Pstcod();
			
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());

				if ("suburb".equalsIgnoreCase(searchType)){			
					postalCodeList = jdt.queryForList("select CODE,SUBURB,TOWN from PSTCOD where TYPE = ? and upper(SUBURB) like ?  order by SUBURB",						
							new Object[] { postalType, searchCriteria.toUpperCase()+"%" },
							new int[] { Types.VARCHAR, Types.VARCHAR });
				}
				if ("postal".equalsIgnoreCase(searchType)){
					postalCodeList = jdt.queryForList("select CODE, SUBURB, TOWN from PSTCOD where TYPE = ? and (substr('0000',1,4-length(CODE)) || to_char(CODE)) like ? order by CODE",
							new Object[] { postalType, searchCriteria.toUpperCase()+"%"},
							new int[] { Types.VARCHAR, Types.VARCHAR });
				}
				
				if (postalCodeList.size() > 0) {
					String code = "";
					String descr = "";
					String town = "";
					for ( int i=0; i<postalCodeList.size(); i++ ) { 
						ListOrderedMap test = (ListOrderedMap) postalCodeList.get(i);
						code =  (String) test.get("CODE").toString();
						descr = (String) test.get("SUBURB").toString();
						town = (String) test.get("TOWN").toString();
						if (code.length()==1){
							code = "000" + code;
						}else if (code.length()==2){
							code = "00" + code;
						}else if (code.length()==3){
							code = "0" + code;
						}
						postalCodes.add(new org.apache.struts.util.LabelValueBean(code + " - " + descr+", " + town, code + descr));
					} 
				}
			
			return postalCodes;
		}
		private String getSupervisorDistrict(SupervisorListRecord supervisor,
                               String province,JdbcTemplate jdt) throws Exception{
                               String district = "";
                               List queryListDistrict = new ArrayList();
                if (province!=null){
                        int provCode = Integer.parseInt(province.toString());
                        String sqlDistrict = "select eng_description" +
                                             " from tpusar, ldd" +
                                             " where tpusar.mk_superv_code=" + supervisor.getCode() +
                                             " and tpusar.mk_district_code = ldd.code" +
                                             " and tpusar.mk_prv_code=" + provCode;
                        try{ 
                              queryListDistrict = jdt.queryForList(sqlDistrict);
                              Iterator j = queryListDistrict.iterator();
                              while (j.hasNext()) {
                                     ListOrderedMap dataDistrict = (ListOrderedMap) j.next();
                                     if (district.equalsIgnoreCase("")){
                                          district = dbutil.replaceNull(dataDistrict.get("eng_description"));
                                     }else{
                                           district = district + "; " + dbutil.replaceNull(dataDistrict.get("eng_description"));
                                     }					
                              }
                       }catch (Exception ex) {
                                 throw new Exception("StudentPlacementDAO : : Error reading Postal ADR / " + ex);
                       }
                   }
                   return  district;
          }
		  
		  private void initialiseContactData(Supervisor supervisor){
		                supervisor.setPostalAddress1("");
                        supervisor.setPostalAddress2("");
                        supervisor.setPostalAddress3("");
                        supervisor.setPostalAddress4("");
                        supervisor.setPostalAddress5("");
                        supervisor.setPostalAddress6("");
                        supervisor.setPhysicalAddress1("");
                        supervisor.setPhysicalAddress2("");
                        supervisor.setPhysicalAddress3("");
                        supervisor.setPhysicalAddress4("");
                        supervisor.setPhysicalAddress5("");
                        supervisor.setPhysicalAddress6("");
                        supervisor.setPhysicalPostalCode("");
                        supervisor.setEmailAddress("");
                        supervisor.setCellNr("");
                        supervisor.setLandLineNr("");
          }
          
		  public Supervisor getSupervisor(Integer code) throws Exception {

                      Supervisor supervisor = new Supervisor();
                      String sql = "select code,mk_title,initials,surname,occupation,contract,to_char(contract_start,'YYYY/MM/DD') as startDate , mk_country_code," +
                      " to_char(contract_end,'YYYY/MM/DD') as endDate" +
                      " from tpusup" +
                      " where code =" + code;		
                      try{ 
                            JdbcTemplate jdt = new JdbcTemplate(getDataSource());
                            List queryList = jdt.queryForList(sql);
                            Iterator i = queryList.iterator();
                            while (i.hasNext()) {
	                               ListOrderedMap data = (ListOrderedMap) i.next();
                                   supervisor.setCode(Integer.parseInt(data.get("code").toString()));
                                   String title=dbutil.replaceNull(data.get("mk_title"));
	                               supervisor.setTitle(title);
	                               String initials=dbutil.replaceNull(data.get("initials"));
	                               supervisor.setInitials(initials);
	                               String surname=dbutil.replaceNull(data.get("surname"));
	                               supervisor.setSurname(surname);
	                               supervisor.setName(surname+" "+initials+" "+title);
	                               supervisor.setOccupation(dbutil.replaceNull(data.get("occupation")));
	                               supervisor.setContract(dbutil.replaceNull(data.get("contract")));
	                               supervisor.setContractStart(dbutil.replaceNull(data.get("startDate")));
	                               supervisor.setContractEnd(dbutil.replaceNull(data.get("endDate")));	
	                               supervisor.setCountryCode(dbutil.replaceNull(data.get("mk_country_code")));
	                               initialiseContactData(supervisor);
	                               
	//Get district
	if(supervisor.getCountryCode().equals(PlacementUtilities.getSaCode())){
	     List listArea = new ArrayList<SupervisorAreaRecord>();
	     supervisor.setListArea(listArea);
	
	//Get district supervisor is linked to
	String sqlDistrict = "select a.mk_prv_code as supProv, (select eng_description from prv where prv.code = a.mk_prv_code) as supProvDesc,a.mk_district_code as supDistrictCode,(select eng_description from ldd where ldd.code=a.mk_district_code) as supDistrictDesc" +
		  " from tpusar a" +
		  " where mk_superv_code=" + supervisor.getCode() +
	      " order by supProvDesc, supDistrictDesc";
	try{ 
		queryList = jdt.queryForList(sqlDistrict);
		Iterator j = queryList.iterator();
		while (j.hasNext()) {
			ListOrderedMap dataDistrict = (ListOrderedMap) j.next();						
			District district = new District();
			Province province = new Province();
			if (dbutil.replaceNull(dataDistrict.get("supProv")).equalsIgnoreCase("")){
				//do nothing
			}else{
				province.setCode(Short.parseShort(dataDistrict.get("supProv").toString()));
				province.setDescription(dbutil.replaceNull(dataDistrict.get("supProvDesc")));
				if (dbutil.replaceNull(dataDistrict.get("supDistrictCode")).equalsIgnoreCase("")){
					//then nothing
				}else{															
					district.setCode(Short.parseShort(dataDistrict.get("supDistrictCode").toString()));
					district.setDescription(dbutil.replaceNull(dataDistrict.get("supDistrictDesc")));
					district.setProvince(province);								
				}
				SupervisorAreaRecord supervisorArea = new SupervisorAreaRecord();	
				supervisor.setProvinceCode(Short.parseShort(dataDistrict.get("supProv").toString()));
				supervisorArea.setDistrict(district);
				supervisorArea.setProvince(province);
				supervisor.getListArea().add(supervisorArea);
			}
		}
	  }
	    catch (Exception ex) {
		     throw new Exception("StudentPlacementDAO : : Error reading Postal ADR / " + ex);
	    }
	}
	//Get postal address
	String sqlPostal = "select address_line_1,address_line_2,address_line_3,address_line_4,address_line_5,address_line_6,postal_code" +
		  " from adr" +
		  " where reference_no=" + supervisor.getCode() +
		  " and fk_adrcatypfk_adrc=231" +
		  " and fk_adrcatypfk_adrt=1";
	try{ 
		queryList = jdt.queryForList(sqlPostal);
		Iterator j = queryList.iterator();
		while (j.hasNext()) {
			ListOrderedMap dataPostal = (ListOrderedMap) j.next();
			supervisor.setPostalAddress1(dbutil.replaceNull(dataPostal.get("address_line_1")));
			supervisor.setPostalAddress2(dbutil.replaceNull(dataPostal.get("address_line_2")));
			supervisor.setPostalAddress3(dbutil.replaceNull(dataPostal.get("address_line_3")));
			supervisor.setPostalAddress4(dbutil.replaceNull(dataPostal.get("address_line_4")));
			supervisor.setPostalAddress5(dbutil.replaceNull(dataPostal.get("address_line_5")));
			supervisor.setPostalAddress6(dbutil.replaceNull(dataPostal.get("address_line_6")));
			supervisor.setPostalCode(dbutil.replaceNull(dataPostal.get("postal_code")));
			if(supervisor.getCountryCode().equals(PlacementUtilities.getSaCode())){
			    while (supervisor.getPostalCode().length() < 4){
				   supervisor.setPostalCode("0" + supervisor.getPostalCode());
			    }
			}
		 }
	}
	catch (Exception ex) {
		throw new Exception("StudentPlacementDAO : : Error reading Postal ADR / " + ex);
	}
	//Get physical address
	String sqlPhysical = "select address_line_1,address_line_2,address_line_3,address_line_4,address_line_5,address_line_6,postal_code" +
	  " from adr" +
	  " where reference_no=" + supervisor.getCode() +
	  " and fk_adrcatypfk_adrc=231" +
	  " and fk_adrcatypfk_adrt=3";
	try{ 
		queryList = jdt.queryForList(sqlPhysical);
		Iterator j = queryList.iterator();
		while (j.hasNext()) {
			ListOrderedMap dataPhysical = (ListOrderedMap) j.next();
			supervisor.setPhysicalAddress1(dbutil.replaceNull(dataPhysical.get("address_line_1")));
			supervisor.setPhysicalAddress2(dbutil.replaceNull(dataPhysical.get("address_line_2")));
			supervisor.setPhysicalAddress3(dbutil.replaceNull(dataPhysical.get("address_line_3")));
			supervisor.setPhysicalAddress4(dbutil.replaceNull(dataPhysical.get("address_line_4")));
			supervisor.setPhysicalAddress5(dbutil.replaceNull(dataPhysical.get("address_line_5")));
			supervisor.setPhysicalAddress6(dbutil.replaceNull(dataPhysical.get("address_line_6")));
			supervisor.setPhysicalPostalCode(dbutil.replaceNull(dataPhysical.get("postal_code")));
			if(supervisor.getCountryCode().equals(PlacementUtilities.getSaCode())){
			    while (supervisor.getPhysicalPostalCode().length() < 4){
				    supervisor.setPhysicalPostalCode("0" + supervisor.getPhysicalPostalCode());
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
	  " where reference_no=" + supervisor.getCode() +
	  " and fk_adrcatcode=231";
	try{ 
		queryList = jdt.queryForList(sqlContact);
		Iterator j = queryList.iterator();
		while (j.hasNext()) {
			ListOrderedMap dataContact = (ListOrderedMap) j.next();
			supervisor.setLandLineNr(dbutil.replaceNull(dataContact.get("work_number")));
			supervisor.setEmailAddress(dbutil.replaceNull(dataContact.get("email_address")));
			supervisor.setCellNr(dbutil.replaceNull(dataContact.get("cell_number")));
			supervisor.setFaxNr(dbutil.replaceNull(dataContact.get("fax_number")));
		}
	}
	catch (Exception ex) {
		throw new Exception("StudentPlacementDAO : : Error reading ADRPH / " + ex);
	}			
	break;
}
}
catch (Exception ex) {
throw new Exception("StudentPlacementDao : Error reading table TPUSUP / " + ex,ex);						
}		
return supervisor;		
}

public List getSupervisorAreaList(Integer supervisorCode) throws Exception {
List listArea = new ArrayList<SupervisorAreaRecord>();
List queryList = new ArrayList();

//Get district supervisor is linked to
String sql = "TT6U";

try{ 
JdbcTemplate jdt = new JdbcTemplate(getDataSource());
queryList = jdt.queryForList(sql);
Iterator j = queryList.iterator();
while (j.hasNext()) {
	ListOrderedMap dataDistrict = (ListOrderedMap) j.next();						
	District district = new District();
	Province province = new Province();
	if (dbutil.replaceNull(dataDistrict.get("supProv")).equalsIgnoreCase("")){
		//do nothing
	}else{
		province.setCode(Short.parseShort(dataDistrict.get("supProv").toString()));
		province.setDescription(dbutil.replaceNull(dataDistrict.get("supProvDesc")));
		if (dbutil.replaceNull(dataDistrict.get("supDistrictCode")).equalsIgnoreCase("")){
			//then nothing
		}else{															
			district.setCode(Short.parseShort(dataDistrict.get("supDistrictCode").toString()));
			district.setDescription(dbutil.replaceNull(dataDistrict.get("supDistrictDesc")));
			district.setProvince(province);								
		}
		SupervisorAreaRecord supervisorArea = new SupervisorAreaRecord();
		supervisorArea.setDistrict(district);
		supervisorArea.setProvince(province);
		listArea.add(supervisorArea);
	}
}
}	
catch (Exception ex) {
throw new Exception("StudentPlacementDao : Error reading table TPUSAR / " + ex,ex);
}	


return listArea;	
}


public List getSupervisorListxx(String country,Short province,Short district,String filter) throws Exception {
	
	List listSupervisor  = new ArrayList<SupervisorListRecord>();
	List queryList = new ArrayList();
	
	String sql = "select a.code as supCode," + 
	" a.surname || ' ' || a.initials || ' ' || a.mk_title as supName," +
	" to_char(a.contract_start, 'YYYY/MM/DD') as supContractStart," +
	" to_char(a.contract_end, 'YYYY/MM/DD') as supContractEnd," +
	" Decode(b.mk_prv_code,null,' ',(select prv.eng_description from prv where prv.code=b.mk_prv_code)) as supProv, " +
	" c.eng_description as supCountry" +
	" from tpusup a,tpusar b, lns c" +
	" where a.code = b.mk_superv_code" + 
	" and b.mk_country_code = c.code";
				
	if (country!=null && !country.trim().equalsIgnoreCase("")){
		sql = sql + " and b.mk_country_code = '" + country + "'";
	}
	
	if (province!=null && province.compareTo(Short.parseShort("0"))!=0){
		sql = sql  + " and b.mk_prv_code = " + province; 
	}
	
	if (district!=null && district!=0){
		sql = sql + " and b.mk_district_code = " + district;
	}
	if (filter!=null && !filter.trim().equalsIgnoreCase("")){
		sql = sql + " and a.surname like '" + filter.trim().toUpperCase() + "'";
	}
	sql = sql + " order by a.surname,a.initials";
	   databaseUtils dbutil=new databaseUtils();
	   JdbcTemplate jdt = new JdbcTemplate(getDataSource());
	   queryList = jdt.queryForList(sql);
	   for (int i=0; i<queryList.size();i++){
		 	SupervisorListRecord supervisor = new SupervisorListRecord();	
		    ListOrderedMap data = (ListOrderedMap) queryList.get(i);
		    supervisor.setCode(Integer.parseInt(data.get("supCode").toString()));
		    supervisor.setName(data.get("supName").toString());
		    supervisor.setContractStart(dbutil.replaceNull(data.get("supContractStart")));
		    supervisor.setContractEnd(dbutil.replaceNull(data.get("supContractEnd")));			
		    supervisor.setCountry(data.get("supCountry").toString());
		    supervisor.setProvince(data.get("supProv").toString());
		    listSupervisor.add(supervisor);			
		}
	return listSupervisor;
}

public List getSupervisorList(String country,Short province,Short district,String filter,String contractStatus,int timeLimit) throws Exception {
	                 List listSupervisor  = new ArrayList<SupervisorListRecord>();
	                 if(country.equals(PlacementUtilities.getSaCode())){
	                	     listSupervisor=getNationalSupervisorList(country,province,district,filter,contractStatus,timeLimit);
	                 }else{
	                	     listSupervisor=getInternationalSupervisorList(country,filter,contractStatus,timeLimit);
	                 }
	
	    return listSupervisor;
   }
   public List getLabelValueList(List listSupervisor){
          Iterator i=listSupervisor.iterator();
          List supervList  = new ArrayList<LabelValueBean>();
          while(i.hasNext()){
	         SupervisorListRecord supervListRec=(SupervisorListRecord)i.next();
	         supervList.add(new LabelValueBean(supervListRec.getName(),""+supervListRec.getCode())); 
          }
          return supervList;
   }
   public List getSupervisorList(String country,Short province) throws Exception {
                  List listSupervisor  = new ArrayList<SupervisorListRecord>();
                  if(country.equals(PlacementUtilities.getSaCode())){
                          listSupervisor=getNationalSupervisorList(country,province,null,null,"All",0);
                  }else{
                          listSupervisor=getInternationalSupervisorList(country,null,"All",0);
                  }
                  return getLabelValueList(listSupervisor);
  }
  
  public List getInternationalSupervisorList(String country,String filter,String contractStatus,int timeLimit) throws Exception {
	 
	                String sql = "select distinct a.code as supCode," + 
	                             " a.surname || ' ' || a.initials || ' ' || a.mk_title as supName," +
	                             " (select cell_number from adrph where adrph.reference_no=a.code and adrph.fk_adrcatcode=231) as cellNumber, " +
	                             " to_char(a.contract_start, 'YYYY/MM/DD') as supContractStart," +
	                             " to_char(a.contract_end, 'YYYY/MM/DD') as supContractEnd," +
	                             " c.eng_description as supCountry,a.contract as contract" +
	                             " from tpusup a,lns c" +
	                             " where a.mk_country_code = c.code";
				    if (country!=null && !country.trim().equalsIgnoreCase("")){
		                   sql = sql + " and a.mk_country_code = '" + country + "'";
	                } 
	                if (filter!=null && !filter.trim().equalsIgnoreCase("")){
		                     filter = filter.replaceAll("'", "''");
		                     sql = sql + " and a.surname || ' ' || a.initials || ' ' || a.mk_title like '" + filter.trim().toUpperCase() + "'";
	                }
	                sql = sql + " order by a.surname || ' ' || a.initials || ' ' || a.mk_title";
	                List listSupervisor  = new ArrayList<SupervisorListRecord>();
	                listSupervisor=getSupervisoList(sql,country,contractStatus,timeLimit);
	
		return listSupervisor;
}
public List getNationalSupervisorList(String country,Short province,Short district,String filter,String contractStatus,int timeLimit) throws Exception {

	                        String sql = "select distinct a.code as supCode," + 
	                        " a.surname || ' ' || a.initials || ' ' || a.mk_title as supName," +
	                        " (select cell_number from adrph where adrph.reference_no=a.code and adrph.fk_adrcatcode=231) as cellNumber, " +
	                        " to_char(a.contract_start, 'YYYY/MM/DD') as supContractStart," +
	                        " to_char(a.contract_end, 'YYYY/MM/DD') as supContractEnd," +
	                        " b.mk_prv_code as supProvCode," +
	                        " Decode(b.mk_prv_code,null,' ',(select prv.eng_description from prv where prv.code=b.mk_prv_code)) as supProv, " +
	                        " a.contract as contract,c.eng_description as supCountry" +
	                        " from tpusup a,tpusar b, lns c" +
	                        " where a.code = b.mk_superv_code" + 
	                        " and a.mk_country_code = c.code";
				
	                        if (country!=null && !country.trim().equalsIgnoreCase("")){
		                                  sql = sql + " and a.mk_country_code = '" + country + "'";
	                        }
	                        if (province!=null && province.compareTo(Short.parseShort("0"))!=0){
		                                sql = sql  + " and b.mk_prv_code = " + province; 
	                        }
		                    if (district!=null && district!=0){
		                                    sql = sql + " and b.mk_district_code = " + district;
	                         }
                             if (filter!=null && !filter.trim().equalsIgnoreCase("")){
		                             filter = filter.replaceAll("'", "''");
		                             sql = sql + " and a.surname || ' ' || a.initials || ' ' || a.mk_title like '" + filter.trim().toUpperCase() + "'";
	                         }
	                         sql = sql + " order by a.surname || ' ' || a.initials || ' ' || a.mk_title";
	                         List listSupervisor  = new ArrayList<SupervisorListRecord>();
                             listSupervisor=getSupervisoList(sql,country,contractStatus,timeLimit);

                 return listSupervisor;
     }
     private List getSupervisoList(String sql,String country,String contractStatus,int timeLimit) throws Exception{
	                databaseUtils dbutil=new databaseUtils();
                    List listSupervisor  = new ArrayList<SupervisorListRecord>();
                    List queryList = new ArrayList();
                    JdbcTemplate jdt = new JdbcTemplate(getDataSource());
                    queryList = jdt.queryForList(sql);
                    for (int i=0; i<queryList.size();i++){
			             SupervisorListRecord supervisor = new SupervisorListRecord();	
                         ListOrderedMap data = (ListOrderedMap) queryList.get(i);
                         supervisor.setCode(Integer.parseInt(data.get("supCode").toString()));
                         supervisor.setName(data.get("supName").toString());
                         supervisor.setCellNumber(dbutil.replaceNull(data.get("cellNumber")));
                         supervisor.setContractStart(dbutil.replaceNull(data.get("supContractStart")));
                         supervisor.setContractEnd(dbutil.replaceNull(data.get("supContractEnd")));			
                         supervisor.setCountry(data.get("supCountry").toString());
                         supervisor.setContract(dbutil.replaceNull(data.get("contract")));	
                         if(country.equals(PlacementUtilities.getSaCode())){
            	              String provinceCode=dbutil.replaceNull(data.get("supProvCode").toString());
            	              String province=dbutil.replaceNull(data.get("supProv").toString());
                              supervisor.setProvince(province);
                              String district=getSupervisorDistrict(supervisor,provinceCode,jdt);
                              supervisor.setDistrict(district);		
                         }
                         setAditionalFields(supervisor,supervisor.getCode());
                         addSupervToList(listSupervisor,supervisor,contractStatus,timeLimit);
                       }
                    return listSupervisor;

     }
     public List getSupervProvList(int supervisorCode){
    	          String sql= "select  b.mk_prv_code as supProvCode" +
                              " from tpusup a,tpusar b, lns c" +
                              " where a.code = b.mk_superv_code" + 
                              " and a.mk_country_code = c.code"+
                              " and b.mk_superv_code="+supervisorCode;
    	           List queryList = new ArrayList();
    	           List provList = new ArrayList();
                   JdbcTemplate jdt = new JdbcTemplate(getDataSource());
                   queryList = jdt.queryForList(sql);
                   for (int i=0; i<queryList.size();i++){
	                      ListOrderedMap data = (ListOrderedMap) queryList.get(i);
           	              String provinceCode=dbutil.replaceNull(data.get("supProvCode").toString());
           	              provList.add(provinceCode);
                   }
                   return provList;
    	 
     }
     private void addSupervToList(List listSupervisor,SupervisorListRecord supervisor,String contractStatus,int timeLimit){
    	                if(contractStatus.equals("All")){
    	            	    listSupervisor.add(supervisor);
    	                }else if(contractStatus.equals("Active")){
    	                         addOnlyActiveSuperv(listSupervisor,supervisor);
    	                }else if(contractStatus.equals("Expired")){
    	                	       addOnlyExpiredSuperv(listSupervisor,supervisor);
    	                }else{
    	                	addOnlyExpireWithinTimeFrame(listSupervisor,supervisor,timeLimit);
    	                }
    	 
     }
     public String getSupervisorName(int supervisorCode)throws Exception {
    	           String sql = "select distinct a.surname || ' ' || a.initials || ' ' || a.mk_title as supName," +
                      " a.contract as contract,c.eng_description as supCountry" +
                      " from tpusup a, lns c" +
                      " where a.mk_country_code = c.code and   a.code ="+supervisorCode;
                      
    	           String errorMsg="SupervisorDAO  :Error reading tpusch";
                   String supervisorName=dbutil.querySingleValue(sql,"supName",errorMsg);
                   return supervisorName;
     }
     private void addOnlyExpireWithinTimeFrame(List listSupervisor,SupervisorListRecord supervisor,int timeLimit){
    	                    if(isExpiringWithinTimeFrame(supervisor,timeLimit)){
    	            	             listSupervisor.add(supervisor);
                             }
     }
     public boolean isExpiringWithinTimeFrame(SupervisorListRecord supervisor,int timeLimit){
    	                       DateUtil dateUtil=new DateUtil(); 
                               PlacementUtilities placementUtilities=new PlacementUtilities();
                               boolean expireWithinTimeFrame=false;
                               String endDate=supervisor.getContractEnd().trim();
                               if(!placementUtilities.isStringEmpty(endDate)){
        	                          String nextDate=dateUtil.getNextDate(timeLimit,"/");
        	                          String prevDay=dateUtil.getPrevDate();
        	                          if(dateUtil.isDateGreaterThanSecDate(endDate,prevDay)){
        	                        	  if(!dateUtil.isDateGreaterThanSecDate(endDate,nextDate)){
                                    	       expireWithinTimeFrame=true;
                                           }
        	                          }
                               }
                               return expireWithinTimeFrame;
     }
     private void addOnlyActiveSuperv(List listSupervisor,SupervisorListRecord supervisor){
                         DateUtil dateUtil=new DateUtil(); 
                         String endDate=supervisor.getContractEnd().trim();
                         PlacementUtilities placementUtilities=new PlacementUtilities();
                         if(!placementUtilities.isStringEmpty(endDate)){
                                if(dateUtil.isDateGreaterThanSysDate(endDate)){
                                     listSupervisor.add(supervisor);
                                }
                         }
     }
     private void addOnlyExpiredSuperv(List listSupervisor,SupervisorListRecord supervisor){
    	                    if(isExpiredSupervisor(supervisor)){
                                    listSupervisor.add(supervisor);
                              }
     }
     public boolean isExpiredSupervisor(SupervisorListRecord supervisor){
                           DateUtil dateUtil=new DateUtil(); 
                           boolean expired=false;
                           PlacementUtilities placementUtilities=new PlacementUtilities();
                           String endDate=supervisor.getContractEnd().trim();
                           if(!placementUtilities.isStringEmpty(endDate)){
                                 if(!dateUtil.isDateGreaterThanSysDate(endDate)){
                                	 expired=true;
                                 }
                          }
                           return expired;
   }
     private void setAditionalFields(SupervisorListRecord supervisor,int supervisorCode) throws Exception {
    	               GregorianCalendar calCurrent = new GregorianCalendar();
                       int year=calCurrent.get(Calendar.YEAR);
    	               supervisor.setEmailAddress(getSupervisorEmail(supervisorCode));
    	               supervisor.setStudentsAllowed(getStudentsAllowed(supervisorCode,year));
    	               supervisor.setStudentsAllocated(getStudentsAllocated(supervisorCode,year));
     }
     public   String getSupervisorEmail(int supervisorCode)throws Exception {
                        String query="Select  adrph.email_address as email  from adrph,tpusup where adrph.reference_no="+
                                     " tpusup.code and tpusup.code="+supervisorCode+" and fk_adrcatcode=231";
                        String errorMsg="SupervisorDAO : Error accessing adrph,tpusup";
                        return dbutil.querySingleValue(query,"email",errorMsg);
     }
     public   String getStudentsAllowed(int supervisorCode,int year)throws Exception {
                         String query="Select  tot_stud_alloc as totAllowed from tpusuptot  where mk_supervisor_code="+
                                       supervisorCode+" and semester_period=0  and mk_academic_year="+year;
                         String errorMsg="SupervisorDAO : Error accessing tpusuptot";
                         String totAllowed=dbutil.querySingleValue(query,"totAllowed",errorMsg);
                         if(totAllowed.equals("")){
                        	 return "100";
                         }else{
                        	 return totAllowed;
                         }
     }
     public   String getStudentsAllocated(int supervisorCode,int year)throws Exception {
                        String query="Select  count(distinct mk_student_nr) as totalAllocated  from tpuspl where mk_supervisor_code="+
                                     supervisorCode+" and semester_period=0  and mk_academic_year="+year;
                        String errorMsg="SupervisorDAO : Error accessing tpuspl";
                        String totAllocated=dbutil.querySingleValue(query,"totalAllocated",errorMsg);
                        if(totAllocated.equals("")){
        	                  return "0";
                        }else{
        	                 return totAllocated;
                        }
     }
     public   String getStudentsAllocated(int supervisorCode,int year,int semester)throws Exception {
         String query="Select  count(distinct mk_student_nr) as totalAllocated  from tpuspl where mk_supervisor_code="+
                      supervisorCode+" and semester_period="+semester+"  and mk_academic_year="+year;
         String errorMsg="SupervisorDAO : Error accessing tpuspl";
         String totAllocated=dbutil.querySingleValue(query,"totalAllocated",errorMsg);
         if(totAllocated.equals("")){
               return "0";
         }else{
              return totAllocated;
         }
}
public void insertSupervisor(Supervisor supervisor) throws Exception {

	String sql = "select max(code) + 1 from tpusup";
	JdbcTemplate jdt = new JdbcTemplate(getDataSource());
	int supervisorCode = jdt.queryForInt(sql) ;
	if (supervisorCode==0){
		supervisorCode=1;
	}
	
	if (supervisor.getContract().equalsIgnoreCase("N")){
		supervisor.setContractStart("");
		supervisor.setContractEnd("");
	}
	String saCode="1015";
	if(!supervisor.getCountryCode().equals(saCode)){
		supervisor.setPostalCode("0");
	}
	Connection connection = null;
	try{
		jdt = new JdbcTemplate(getDataSource());
		connection = jdt.getDataSource().getConnection();
		connection.setAutoCommit(false);
		Statement stmt = connection.createStatement();
		
    	PreparedStatement ps = connection.prepareStatement("insert into tpusup (code,mk_title,initials,surname,occupation,contract," +
				"contract_start,contract_end,mk_country_code)" +
				" values" +
				" (?,?,?,?,?,?,to_date(?,'YYYY/MM/DD'),to_date(?,'YYYY/MM/DD'),?)");
		ps.setInt(1, supervisorCode);
		ps.setString(2, supervisor.getTitle().toUpperCase().trim());
		ps.setString(3, supervisor.getInitials().toUpperCase().trim());
		ps.setString(4, supervisor.getSurname().toUpperCase().trim());
		ps.setString(5,supervisor.getOccupation().toUpperCase().trim());
		ps.setString(6, supervisor.getContract().toUpperCase().trim());
		ps.setString(7, supervisor.getContractStart().toUpperCase().trim());
		ps.setString(8, supervisor.getContractEnd().toUpperCase().trim());
		ps.setString(9, supervisor.getCountryCode());	
		ps.executeUpdate();
		if (supervisor.getPostalAddress1()!=null && !supervisor.getPostalAddress1().trim().equalsIgnoreCase("")){

			ps = connection.prepareStatement("insert into adr (reference_no, address_line_1,address_line_2,address_line_3," +
					"address_line_4,address_line_5,address_line_6,postal_code,fk_adrcatypfk_adrc,fk_adrcatypfk_adrt," +
					"error_code,user_code)" +
					"values " +
					"(?,?,?,?,?,?,?,?,231,1,null,null)");
			ps.setInt(1, supervisorCode);
			ps.setString(2, supervisor.getPostalAddress1().toUpperCase().trim());
			ps.setString(3, supervisor.getPostalAddress2().toUpperCase().trim());
			ps.setString(4, supervisor.getPostalAddress3().toUpperCase().trim());
			ps.setString(5, supervisor.getPostalAddress4().toUpperCase().trim());
			ps.setString(6, supervisor.getPostalAddress5().toUpperCase().trim());
			ps.setString(7, supervisor.getPostalAddress6().toUpperCase().trim());
			ps.setShort(8, Short.parseShort(supervisor.getPostalCode().trim()));
			ps.executeUpdate();
		}		
		
		//insert physical address
		if (supervisor.getPhysicalAddress1()!=null && !supervisor.getPhysicalAddress1().trim().equalsIgnoreCase("")){
			Short postalCode=0;
			if (supervisor.getPhysicalPostalCode()!=null && !supervisor.getPhysicalPostalCode().trim().equalsIgnoreCase("")){
				postalCode=Short.parseShort(supervisor.getPhysicalPostalCode());
			}
			ps = connection.prepareStatement("insert into adr (reference_no, address_line_1,address_line_2,address_line_3," +
					"address_line_4,address_line_5,address_line_6,postal_code,fk_adrcatypfk_adrc,fk_adrcatypfk_adrt," +
					"error_code,user_code)" +
					"values " +
					"(?,?,?,?,?,?,?,?,231,3,null,null)");
			ps.setInt(1, supervisorCode);
			ps.setString(2, supervisor.getPhysicalAddress1().toUpperCase().trim());
			ps.setString(3, supervisor.getPhysicalAddress2().toUpperCase().trim());
			ps.setString(4, supervisor.getPhysicalAddress3().toUpperCase().trim());
			ps.setString(5, supervisor.getPhysicalAddress4().toUpperCase().trim());
			ps.setString(6, supervisor.getPhysicalAddress5().toUpperCase().trim());
			ps.setString(7, supervisor.getPhysicalAddress6().toUpperCase().trim());
			ps.setShort(8, postalCode);
			ps.executeUpdate();
		}
		
		//insert contact details - adrph
		stmt.executeUpdate("insert into adrph (reference_no,home_number,work_number,fax_number," +
				"email_address,fk_adrcatcode,cell_number,courier_contact_no,cell_phone_verifie," +
				"email_verified)" +
				"values " +
				"(" + supervisorCode + "," + 
				"' '," +
				"'" + supervisor.getLandLineNr().trim() + "'," +
				"'" + supervisor.getFaxNr().trim() + "'," +
				"'" + supervisor.getEmailAddress().toUpperCase().trim() + "'," +
				"231," +
				"'" + supervisor.getCellNr().trim() + "'," +
				"' '," +
				"'N'," +
				"'N')");	
		//insert supervisor areas
		if(supervisor.getCountryCode().equals(saCode)){
		    for (int i=0; i < supervisor.getListArea().size();i++) {
			   SupervisorAreaRecord area = new SupervisorAreaRecord();
			   area = (SupervisorAreaRecord)supervisor.getListArea().get(i);			
			
			   stmt.executeUpdate("insert into tpusar (mk_prv_code,mk_district_code," +
					"mk_superv_code)" +
					" values" +
					" (" + area.getProvince().getCode() + "," +
					area.getDistrict().getCode() + "," +
					supervisorCode + ")"); 
		    }
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
				throw new Exception("StudentPlacementDao : Error inserting records, records have been rollbacked / " + ex,ex);	
		    }
	     }		
     }
    


public void addSupervisorArea(Supervisor supervisor , SupervisorAreaRecord area)throws Exception {

String sql = "insert into tpusar (mk_prv_code,mk_district_code,mk_superv_code) " +
	"values " +
	"(" +
	area.getProvince().getCode() + "," +
	area.getDistrict().getCode() + "," +
	supervisor.getCode() + ")";
try{ 
	JdbcTemplate jdt = new JdbcTemplate(getDataSource());
	int result = jdt.update(sql);	
}
catch (Exception ex) {
	throw new Exception("StudentPlacementDao : Error inserting TPUSAR / " + ex,ex);
}	
}

public void removeSupervisorArea(Supervisor supervisor , SupervisorAreaRecord area)throws Exception {

	String sql = "delete from tpusar " +
		" where mk_superv_code = " + supervisor.getCode();	
	
	if (area.getDistrict().getCode()!=null){
		sql = sql + " and mk_prv_code= " + area.getDistrict().getProvince().getCode() +
		" and mk_district_code = " + area.getDistrict().getCode();
	}
	
	if (area.getDistrict().getCode()==null && area.getProvince().getCode()!=null){
		sql = sql + " and mk_prv_code= " + area.getProvince().getCode() +
		" and mk_district_code is null";
	}
	
	try{ 
		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		int result = jdt.update(sql);	
	}
	catch (Exception ex) {
		throw new Exception("StudentPlacementDao : Error deleting TPUSAR / " + ex,ex);
	}	
}

  public Supervisor getSup(int code) throws Exception {

      Supervisor supervisor = new Supervisor();
      String sql = "select b.code,b.mk_title,b.initials,b.surname,a.email_address,d.eng_description as supProvDesc,d.code as supProvCode"+
                 " from adrph a,tpusup b,tpusar c,prv d " +
                 " where  reference_no=b.code and  b.code= c.mk_superv_code and  c.mk_prv_code=d.code"+
                 " and b.code=" + code;
      String errorMsg="SupervisorDAO : Error getting supervisor details from tables: adrph ,tpusup ,tpusar ,prv ";         
      List queryList = dbutil.queryForList(sql,errorMsg);
      Iterator i = queryList.iterator();
      while (i.hasNext()) {
            ListOrderedMap data = (ListOrderedMap) i.next();
            supervisor.setCode(Integer.parseInt(data.get("code").toString()));
            supervisor.setTitle(dbutil.replaceNull(data.get("mk_title")));
            supervisor.setInitials(dbutil.replaceNull(data.get("initials")));
            supervisor.setSurname(dbutil.replaceNull(data.get("surname")));
            supervisor.setEmailAddress(dbutil.replaceNull(data.get("email_Address")));
            break;
      }
      return supervisor;
   }
   
}
