package za.ac.unisa.lms.tools.tpustudentplacement.dao;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.collections.map.ListOrderedMap;
import org.springframework.jdbc.core.JdbcTemplate;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.Address;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.Contact;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.Supervisor;
import za.ac.unisa.lms.tools.tpustudentplacement.model.modelImpl.ContactUI;
import za.ac.unisa.lms.db.StudentSystemDAO;
public class ContactDAO extends StudentSystemDAO {
	              
	
	              databaseUtils dbutil;
	              public  ContactDAO(){
	            	         dbutil=new databaseUtils();
	              }
	              public Contact getContactDetails(Integer referenceNo,Integer category) throws Exception {
                		
              		                Contact contact = new Contact();
              		           		String sql = "select home_number,work_number,fax_number,email_address,cell_number" +
              		                             " from adrph" +
              		                             " where reference_no=" + referenceNo +
              		                             " and fk_adrcatcode=" + category;
              		                 String  errorMsg="Error reading ADR / ";
              		                 List queryList = dbutil.queryForList(sql,errorMsg);
              	
              			             Iterator i = queryList.iterator();
              			             while (i.hasNext()) {
              				                 ListOrderedMap data = (ListOrderedMap) i.next();
              				                 contact.setHomeNumber(dbutil.replaceNull(data.get("home_number")).trim());
              				                 contact.setWorkNumber(dbutil.replaceNull(data.get("work_number")).trim());
              				                 contact.setFaxNumber(dbutil.replaceNull(data.get("fax_number")).trim());
              				                 contact.setCellNumber(dbutil.replaceNull(data.get("cell_number")).trim());
              				                 contact.setEmailAddress(dbutil.replaceNull(data.get("email_address")).trim());
              		   	            }
              		              return contact;	
              	 }
	              public int countRecords(int reference,int category)throws Exception {
	            	         String  sql = "select count(*)" +
	        				  " from adrph   where reference_no=" + reference +
	        				  " and fk_adrcatcode="+category;
	            	          String  errorMsg="Error reading ADR / ";
		                      databaseUtils dbutil=new databaseUtils();
			                  return dbutil.queryInt(sql,errorMsg);
	              }
	              public void insertContactDetail(Supervisor supervisor)throws Exception {
	            	             String  sql="insert into adrph (reference_no,home_number,work_number,fax_number," +
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
							                 "'N')";	
	            	              String  errorMsg="Error inserting into adrph / ";
		                          databaseUtils dbutil=new databaseUtils();
			                      dbutil.update(sql,errorMsg);
	              }
	              public void updateContactDetail(Supervisor supervisor)throws Exception {
 					            //update contact details - adrph
	            	            String  sql="update adrph set " +
							                "work_number = '" + supervisor.getLandLineNr().trim() + "'," +
							                "fax_number = '" + supervisor.getFaxNr().trim() + "'," +
							                "email_address = '" + supervisor.getEmailAddress().toUpperCase().trim() + "'," +
							                "cell_number ='" + supervisor.getCellNr().trim() + "'" +
							                " where reference_no = " + supervisor.getCode() + 
							                " and fk_adrcatcode=231";
	            	            String  errorMsg="Error updating into adrph / ";
		                        databaseUtils dbutil=new databaseUtils();
			                    dbutil.update(sql,errorMsg);
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
            public Address getAddress(Integer referenceNo,Integer category,Integer type) throws Exception {
              		
            		                 Address address = new Address();
            		       		     String sql = "select address_line_1,address_line_2,address_line_3,address_line_4,address_line_5" +
            		                              " address_line_6,postal_code" +
            		                              " from adr" +
            		                              " where reference_no=" + referenceNo +
            		                              " and fk_adrcatypfk_adrc=" + category +
            		                              " and fk_adrcatypfk_adrt=" + type;
            		
            	  	         	  	String  errorMsg="Error reading ADR / ";
  		                            databaseUtils dbutil=new databaseUtils();
  		                            List queryList = dbutil.queryForList(sql,errorMsg);
            			            Iterator i = queryList.iterator();
            			            while (i.hasNext()) {
            				                ListOrderedMap data = (ListOrderedMap) i.next();
            				                address.setLine1(dbutil.replaceNull(data.get("address_line_1")).trim());
            				                address.setLine2(dbutil.replaceNull(data.get("address_line_2")).trim());
            				                address.setLine3(dbutil.replaceNull(data.get("address_line_3")).trim());
            				                address.setLine4(dbutil.replaceNull(data.get("address_line_4")).trim());
            				                address.setLine5(dbutil.replaceNull(data.get("address_line_5")).trim());
            				                address.setLine6(dbutil.replaceNull(data.get("address_line_6")).trim());
            				                address.setPostalCode(dbutil.replaceNull(data.get("postal_code")).trim());
            			           }
            		              return address;	
            }
            public  int totRecsOfAddress(int supervCode,int addressType)throws Exception {
                String sql = "select count(*)" +
		                        " from adr" +
		                        " where reference_no=" + supervCode+
		                        " and fk_adrcatypfk_adrc=231" +
		                        " and fk_adrcatypfk_adrt="+addressType;
                String errorMsg="Error reading supervisor postal address  in table adr, SupervisorDAO"; 
               return dbutil.queryInt(sql,errorMsg);
            }
            public void insertPostalAddress(Supervisor supervisor)throws Exception {
 	             String sql="insert into adr (reference_no, address_line_1,address_line_2,address_line_3," +
	                           " address_line_4,address_line_5,address_line_6,postal_code,fk_adrcatypfk_adrc,fk_adrcatypfk_adrt," +
	                           " error_code,user_code)" +
	                           " values " +
	                           "("+supervisor.getCode()+",'"+supervisor.getPostalAddress1().toUpperCase().trim()+"','"+
	                           supervisor.getPostalAddress2().toUpperCase().trim()+"','"+
	                           supervisor.getPostalAddress3().toUpperCase().trim()+"','"+
	                           supervisor.getPostalAddress4().toUpperCase().trim()+"','"+
	                           supervisor.getPostalAddress5().toUpperCase().trim()+"','"+
	                           supervisor.getPostalAddress6().toUpperCase().trim()+"',"+
	                           Short.parseShort(supervisor.getPostalCode().trim())+",231,1,null,null)";
                   String errorMsg="Error inserting supervisor postal address  in table adr, SupervisorDAO";
                   dbutil.update(sql,errorMsg);
        }
        public void updatePostalAddress(Supervisor supervisor)throws Exception {
 	             String sql="update adr set " +
								"address_line_1 = " + supervisor.getPostalAddress1().toUpperCase().trim()+
								",address_line_2 = " +supervisor.getPostalAddress2().toUpperCase().trim()+
								",address_line_3 = " +supervisor.getPostalAddress3().toUpperCase().trim()+
								",address_line_4 = " +supervisor.getPostalAddress4().toUpperCase().trim()+
								",address_line_5 = " +supervisor.getPostalAddress5().toUpperCase().trim()+
								",address_line_6 = " + supervisor.getPostalAddress6().toUpperCase().trim()+
								",postal_code = " +Short.parseShort(supervisor.getPostalCode().trim())+
								" where reference_no = " + supervisor.getCode()+
								" and fk_adrcatypfk_adrc=231" +
								" and fk_adrcatypfk_adrt=1";
 	             String errorMsg="Error updating supervisor postal address  in table adr, SupervisorDAO";
 	             dbutil.update(sql,errorMsg);
        }
        public void insertPhysicalAddress(Supervisor supervisor)throws Exception {
                 String sql="insert into adr (reference_no, address_line_1,address_line_2,address_line_3," +
                " address_line_4,address_line_5,address_line_6,postal_code,fk_adrcatypfk_adrc,fk_adrcatypfk_adrt," +
                " error_code,user_code)" +
                " values " +
                "("+supervisor.getCode()+",'"+supervisor.getPhysicalAddress1().toUpperCase().trim()+"','"+
                supervisor.getPhysicalAddress2().trim()+"','"+
                supervisor.getPhysicalAddress3().toUpperCase().trim()+"','"+
                supervisor.getPhysicalAddress4().toUpperCase().trim()+"','"+
                supervisor.getPhysicalAddress5().toUpperCase().trim()+"','"+
                supervisor.getPhysicalAddress6().toUpperCase().trim()+"',"+
                Short.parseShort(supervisor.getPostalCode().trim())+",231,3,null,null)";
                String errorMsg="Error inserting supervisor postal address  in table adr, SupervisorDAO";
                dbutil.update(sql,errorMsg);
        }
        public void updatePhysicalAddress(Supervisor supervisor)throws Exception {
              String sql="update adr set " +
					"address_line_1 = " + supervisor.getPhysicalAddress1().toUpperCase().trim()+
					",address_line_2 = " +supervisor.getPhysicalAddress2().toUpperCase().trim()+
					",address_line_3 = " +supervisor.getPhysicalAddress3().toUpperCase().trim()+
					",address_line_4 = " +supervisor.getPhysicalAddress4().toUpperCase().trim()+
					",address_line_5 = " +supervisor.getPhysicalAddress5().toUpperCase().trim()+
					",address_line_6 = " + supervisor.getPhysicalAddress6().toUpperCase().trim()+
					",postal_code = " +Short.parseShort(supervisor.getPostalCode().trim())+
					" where reference_no = " + supervisor.getCode()+
					" and fk_adrcatypfk_adrc=231" +
					" and fk_adrcatypfk_adrt=3";
                   String errorMsg="Error updating supervisor postal address  in table adr, SupervisorDAO";
            dbutil.update(sql,errorMsg);
       }
        public void deleteAddress(Supervisor supervisor,int addressType)throws Exception {
 	                    String sql="delete from adr " +
			                    " where reference_no = " + supervisor.getCode() + 
			               " and fk_adrcatypfk_adrc=231" +
			               " and fk_adrcatypfk_adrt="+addressType;
 	        String errorMsg="Error deleting supervisor Postal Address  in table adr, SupervisorDAO";
 	        if(addressType==3){
 	        	errorMsg="Error deleting supervisor Physical Address  in table adr, SupervisorDAO";
 	        }
                dbutil.update(sql,errorMsg);
         }
        public void updateSupervisorPostalAddress(Supervisor supervisor)throws Exception {
                        int totAddrressRecords=totRecsOfAddress(supervisor.getCode(),1);
            if (totAddrressRecords==0){
                   if (supervisor.getPostalAddress1()!=null &&
	                     !supervisor.getPostalAddress1().trim().equalsIgnoreCase("")){
                	        insertPostalAddress(supervisor);
                   }
            }else{
      			   if (supervisor.getPostalAddress1()!=null &&
		                    !supervisor.getPostalAddress1().trim().equalsIgnoreCase("")){
      				           updatePostalAddress(supervisor);
                     }else{
                  	           deleteAddress(supervisor,1);
                     }
             }
       }
        public void updateSupervisorPhysicalAddress(Supervisor supervisor)throws Exception {
                        int totAddrressRecords=totRecsOfAddress(supervisor.getCode(),3);
                if (totAddrressRecords==0){
                       if (supervisor.getPhysicalAddress1()!=null &&
		                     !supervisor.getPhysicalAddress1().trim().equalsIgnoreCase("")){
                    	            insertPhysicalAddress(supervisor);
                       }
                }else{
          			   if (supervisor.getPhysicalAddress1()!=null &&
			                    !supervisor.getPhysicalAddress1().trim().equalsIgnoreCase("")){
          				           updatePhysicalAddress(supervisor);
                         }else{
                      	             deleteAddress(supervisor,1);
                         }
                 }
     }
     public void updateContactDetails(Supervisor supervisor)throws Exception {
                       int totContactRecords=countRecords(supervisor.getCode(),231);
                       if (totContactRecords==0){
	                        insertContactDetail(supervisor);
                       }else{
                              updateContactDetail(supervisor);
                       }
    }
            	
}
