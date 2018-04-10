package za.ac.unisa.lms.tools.tpustudentplacement.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.collections.map.ListOrderedMap;
import org.springframework.jdbc.core.JdbcTemplate;
import za.ac.unisa.lms.tools.tpustudentplacement.model.Coordinator;
public class CoordinatorDAO {
                        
	                    databaseUtils dbutil;
                        public CoordinatorDAO(){
                                    dbutil=new databaseUtils();
                        }
                        public   String isCoordinatorActive(String username) throws Exception{
                        	               return isCoordinator(username);
	                    }
                        public String  isCoordinator(String username) throws Exception{
	                            String sql="Select count(*) as tot from STAFF a,TPUWSC b, prv c"+
	           		                       " where a.persno = b.mk_persno and a.novell_user_id='"+username+"' and b.mk_prv_code=c.code ";
	                            String errorMsg="CoordinatorDAO:Error reading tpuwsc";
	                            String totalLinks=dbutil.querySingleValue(sql,"tot",errorMsg);
	                         if((totalLinks==null)||(totalLinks.equals(""))||totalLinks.equals("0")){
                         	   return "N";
                            }else{
                         	   return "Y";
                            }
                        }
	                    public String getPersonnelNumber(String username)throws Exception{
	                    	            String query="select   persno from  staff where NOVELL_USER_ID is not null and "+
	                                                 " (resign_date is null or resign_date>sysdate) and novell_user_id='"+username+"'";
	                    	            String errorMsg="CoordinatorDAO:Error reading staff";
            	                        List queryList=dbutil.queryForList(query,errorMsg);
            	                        String personnelNum="";
            	                        for (int i=0; i<queryList.size();i++){
       			                                Coordinator   coordinator = new Coordinator();	
                                                ListOrderedMap data = (ListOrderedMap) queryList.get(i);
                                                coordinator.setPersonnelNumber(dbutil.replaceNull(data.get("persno")));
                                               break;
                                        }
                                        return personnelNum;
            	        }
	                   public   String  getSadecInt(String personnelNumber)throws Exception{
	                    	                String query="Select  mk_sadec_int _ind  from tpuwsc where mk_persno="+personnelNumber;
	                    	                String errorMsg="CoordinatorDAO:Error reading tpuwsc";
	                    	                String sadecIn="N";
	                    	                List queryList=dbutil.queryForList(query,errorMsg);
	                    	                for (int i=0; i<queryList.size();i++){
	                    	                	 ListOrderedMap data = (ListOrderedMap) queryList.get(i);
	                    	                	 sadecIn=dbutil.replaceNull(data.get("mk_sadec_int _ind"));
                                                 break;
                                           }
                                           return sadecIn;
	                    }
	                    public   boolean isCoordinatorLinked(String personnelNum,String workstation)throws Exception{
   	                                 String query="Select  count(*)   as tot from  tpuwsc where mk_persno="+personnelNum+" and mk_prv_code='"+workstation+"'";
   	                                 String errorMsg="CoordinatorDAO:Error reading tpuwsc";
   	                               String totalLinks=dbutil.querySingleValue(query,"tot",errorMsg);
   	                               if((totalLinks==null)||(totalLinks.equals(""))||totalLinks.equals("0")){
   	                            	   return false;
   	                               }else{
   	                            	   return true;
   	                               }
   	             
                        }
	                    public   boolean coordinatorsExit()throws Exception{
           	                              String query="Select  mk_persno from tpuwsc";
           	                              String errorMsg="CoordinatorDAO:Error reading tpuwsc";
           	                            	String persno=dbutil.querySingleValue(query,"mk_persno",errorMsg);
 	                    	             if(persno.equals("")){
 	                    	            	 return false;
 	                    	             }else{
 	                    	            	 return true;
 	                    	             }
	                    }
           	            public Coordinator getCoordinator(int persno) throws Exception{
           	            	                   String sql=  "Select a.title || ' ' || a.initials  || ' ' ||  a.surname as coordName," +
                 	           		                      " a.novell_user_id  networkcode,a.persno as personnelNumber,b.sadec_int_ind as sadecIndicator," +
                 	           		                      " c.eng_description workstationDescr,c.code as workstationCode,b.email_Address,b.telephone_number as contactNumber  from STAFF a,TPUWSC b, prv c"+
                 	           		                      " where  a.NOVELL_USER_ID is not null and  (a.resign_date is null or a.resign_date>sysdate) and "+
                 	           		                      " a.persno = b.mk_persno and a.persno="+persno+" and b.mk_prv_code=c.code  order by workstationDescr,networkcode";
 	                                           String errorMsg="CoordinatorDAO:Error reading STAFF ,TPUWSC , prv ";
                                              List queryList = dbutil.queryForList(sql,errorMsg);
                                              Coordinator   coordinator = new Coordinator();
                                              if(!queryList.isEmpty()){
                                	                    ListOrderedMap data = (ListOrderedMap) queryList.get(0);
                                                        coordinator.setName(dbutil.replaceNull(data.get("coordName")));
                                                        coordinator.setNetworkCode(dbutil.replaceNull(data.get("networkcode")));
                                                        coordinator.setPersonnelNumber(dbutil.replaceNull(data.get("personnelNumber")));
                                                        coordinator.setWorkStationDescr(dbutil.replaceNull(data.get("workstationDescr")));
                                                        coordinator.setWorkStationCode(dbutil.replaceNull(data.get("workstationCode")));
                                                        coordinator.setSadecInt(dbutil.replaceNull(data.get("sadecIndicator")));
                                                        coordinator.setEmailAddress(dbutil.replaceNull(data.get("email_Address")));
                                                        coordinator.setContactNumber(dbutil.replaceNull(data.get("contactNumber")));
                                             }
                                             return coordinator;
                     }
           	         public Coordinator getProspectiveCoordinator(int persno) throws Exception{
           	                                String sql="Select title || ' ' || initials  || ' ' ||  surname as coordName," +
           	           		                           " novell_user_id  networkcode,persno as personnelNumber,email_Address,CONTACT_TELNO as contactNumber  from STAFF "+
           	           		                           " where persno ="+persno;
                          List coordinatorList  = new ArrayList<Coordinator>();
                          String errorMsg="CoordinatorDAO:Error reading STAFF";
                          List queryList = dbutil.queryForList(sql,errorMsg);
                          Coordinator   coordinator = new Coordinator();
                          if(!queryList.isEmpty()){
                          	    ListOrderedMap data = (ListOrderedMap) queryList.get(0);
                                coordinator.setName(dbutil.replaceNull(data.get("coordName")));
                                coordinator.setNetworkCode(dbutil.replaceNull(data.get("networkcode")));
                                coordinator.setPersonnelNumber(dbutil.replaceNull(data.get("personnelNumber")));
                                coordinator.setEmailAddress(dbutil.replaceNull(data.get("email_Address")));
                                coordinator.setContactNumber(dbutil.replaceNull(data.get("contactNumber")));
                                coordinator.setWorkStationCode("1");
                                coordinator.setSadecInt("N");
                         }
                         return coordinator;
                 }
           	     public Coordinator getCoordinatorForProv(int provinceCode) throws Exception{
           	        	                   Coordinator  coordinator=null;
           	        	                   String persno=getPersnoForProvWSC(provinceCode);
           	        	                   if(persno!=null){
           	        	                	     if(!persno.trim().equals("")){
           	        	                	          int personnelNum=Integer.parseInt(persno);
           	        	                              coordinator=getCoordinator(personnelNum);
           	        	                	    }
           	                               }
                                           return coordinator;
                 }
           	  public Coordinator getCoordinatorForSadecInt() throws Exception{
	                                  Coordinator  coordinator=null;
	                                  String persno=getPersnoForSadecInt();
	                                  if(persno!=null){
	                	                    if(!persno.trim().equals("")){
	                	                          int personnelNum=Integer.parseInt(persno);
	                                               coordinator=getCoordinator(personnelNum);
	                	                    }
                                      }
                                    return coordinator;
             }
           	
           	     public List getCoordinatorList() throws Exception{
	                    	           String sql="Select a.title || ' ' || a.initials  || ' ' ||  a.surname as coordName," +
	                    	           		      " a.novell_user_id  networkcode,a.persno as personnelNumber,b.sadec_int_ind as sadecIndicator," +
	                    	           		      " c.eng_description workstationDescr,c.code as workstationCode,b.email_Address,b.telephone_number as contactNumber"+
	                    	           		      " from STAFF a,TPUWSC b, prv c"+
	                    	           		      " where   a.NOVELL_USER_ID is not null and "+
	                                              " (a.resign_date is null or a.resign_date>sysdate)  and a.persno = b.mk_persno"+
	                    	           		      "  and b.mk_prv_code=c.code order by workstationDescr,networkcode";
	    	                           List coordinatorList  = new ArrayList<Coordinator>();
	    	                           String errorMsg="CoordinatorDAO:Error reading STAFF ,TPUWSC , prv ";
	                                   List queryList = dbutil.queryForList(sql,errorMsg);
	                                   for (int i=0; i<queryList.size();i++){
	    			                         Coordinator   coordinator = new Coordinator();	
	                                         ListOrderedMap data = (ListOrderedMap) queryList.get(i);
	                                         coordinator.setName(dbutil.replaceNull(data.get("coordName")));
	                                         coordinator.setNetworkCode(dbutil.replaceNull(data.get("networkcode")));
	                                         coordinator.setPersonnelNumber(dbutil.replaceNull(data.get("personnelNumber")));
	                                         coordinator.setWorkStationDescr(dbutil.replaceNull(data.get("workstationDescr")));
	                                         coordinator.setWorkStationCode(dbutil.replaceNull(data.get("workstationCode")));
	                                         coordinator.setSadecInt(dbutil.replaceNull(data.get("sadecIndicator")));
	                                         coordinator.setEmailAddress(dbutil.replaceNull(data.get("email_Address")));
	                                         coordinator.setContactNumber(dbutil.replaceNull(data.get("contactNumber")));
	                                         coordinatorList.add(coordinator);			
	                                  }
	                                  return coordinatorList;
	                    }
	                          public List getCoordinatorProvinceList(int personnelNumber)throws Exception{
	                    	         String sql="Select mk_persno as personnelNumber,mk_prv_code as workstationCode from TPUWSC"+
   	           		                            " where mk_persno ="+personnelNumber;
                                     List coordinatorList  = new ArrayList<Coordinator>();
                                     String errorMsg="CoordinatorDAO:Error reading TPUWSC  ";
                                     List queryList = dbutil.queryForList(sql,errorMsg);
                                     for (int i=0; i<queryList.size();i++){
                                            Coordinator   coordinator = new Coordinator();	
                                            ListOrderedMap data = (ListOrderedMap) queryList.get(i);
                                            coordinator.setPersonnelNumber(dbutil.replaceNull(data.get("personnelNumber")));
                                            coordinator.setWorkStationCode(dbutil.replaceNull(data.get("workstationCode")));
                                            coordinatorList.add(coordinator);			
                                    }
                                    return coordinatorList;
	                    }
	                    public String  getPersnoForProvWSC(int provinceCode)throws Exception{
               	                           String sql="Select mk_persno as personnelNumber from TPUWSC a,staff b  where  a.mk_persno=b.persno "+
               	                        		      "  and  b.NOVELL_USER_ID is not null and  (b.resign_date is null or b.resign_date>sysdate) and a.mk_prv_code="+provinceCode;
         		                           String errorMsg="CoordinatorDAO:Error reading TPUWSC  ";
                                           List queryList = dbutil.queryForList(sql,errorMsg);
                                           if(queryList.isEmpty()){
                                        	   return "";
                                           }else{
                                               ListOrderedMap data = (ListOrderedMap) queryList.get(0);
                                               return dbutil.replaceNull(data.get("personnelNumber"));
                                           }
                      }
	                  public String  getPersnoForSadecInt()throws Exception{
	                                          String sql="Select mk_persno as personnelNumber from TPUWSC a,staff b   where a.mk_persno=b.persno and a.sadec_int_ind='Y'"+
	                        		                    " and b.NOVELL_USER_ID is not null and  (b.resign_date is null or b.resign_date>sysdate)";
                                              String errorMsg="CoordinatorDAO:Error reading TPUWSC  ";
                                              List queryList = dbutil.queryForList(sql,errorMsg);
                                              if(queryList.isEmpty()){
                         	                           return "";
                                              }else{
                                                       ListOrderedMap data = (ListOrderedMap) queryList.get(0);
                                                       return dbutil.replaceNull(data.get("personnelNumber"));
                                              }
                      }
	                  public void deleteCoordinator(String personnelNumber,String provinceCode)throws Exception{
                                           Connection connection = null;
		        			               try{
		        				                JdbcTemplate jdt =dbutil.getdbcTemplate();
		        				                connection = jdt.getDataSource().getConnection();
		        				                connection.setAutoCommit(false);
		        				                Statement stmt = connection.createStatement();	
		        				                stmt.executeUpdate("delete from TPUWSC where mk_persno ="+
                    	            		            ""+personnelNumber+" and mk_prv_code="+provinceCode);
		        				                connection.commit();
		        				    			connection.setAutoCommit(true);	
		        				    			connection.close();
		        			               }catch (Exception ex) {
		        				    			    if (connection!=null){				
		        				    					connection.rollback();	
		        				    					connection.setAutoCommit(true);
		        				    					connection.close();
		        				    					throw new Exception("CoordinatorDao : Error inserting records, records have been rollbacked / " + ex,ex);	
		        				    			    }
		        				    		     }		
		        	   }
	                   public String getCoordinatorName(String personnelNumber)throws Exception{
	                    	        String sql="Select title || ' ' || initials  || ' ' ||  surname as coordName" +
	                    			           " from STAFF a where persno = "+personnelNumber;
	                    	        String errorMsg="CoordinatorDAO:Error reading STAFF ";
	                    	        return   dbutil.querySingleValue(sql,"coordName",errorMsg);
	                    }
	                    public String getNetworkCode(String personnelNumber)throws Exception{
                	                   String sql="Select novell_user_id  networkcode" +
                			                      " from STAFF  where persno = "+personnelNumber;
                	                   String errorMsg="CoordinatorDAO:Error reading STAFF ";
                	        return   dbutil.querySingleValue(sql,"networkcode",errorMsg);
                        }
	                    public String  saveCoordinator(Coordinator coordinator)throws Exception{
	                    	             String workstation=coordinator.getWorkStationCode();
	                    	             boolean isLinked=isCoordinatorLinked(coordinator.getPersonnelNumber().trim(),workstation);
	                    	             if(isLinked){
	                    	                	return "The coordinator has already been linked  to  the selected workstation";
	                    	             }
	                    	             String returnStr="";
	                                     if(coordinator.getSadecInt().trim().equals("Y")){
             	        	                     if(!checkIfSadecIntRowExists(workstation)){
             	        		                        insertSadecIntCode(workstation);
             	        	                     }
             	                         }
	                	                 Connection connection = null;
	        			                 try{
	        				                JdbcTemplate jdt =dbutil.getdbcTemplate();
	        				                connection = jdt.getDataSource().getConnection();
	        				                connection.setAutoCommit(false);
	        				                PreparedStatement ps = connection.prepareStatement("Insert into tpuwsc(mk_persno,sadec_int_ind,mk_prv_code,telephone_number,"+
	        				                "email_address)values(?,?,?,?,?)");
	        				                ps.setString(1,coordinator.getPersonnelNumber().trim());
               				                ps.setString(2, coordinator.getSadecInt().trim());
	        				                ps.setString(3, coordinator.getWorkStationCode().trim());
                                            ps.setString(4, coordinator.getContactNumber().trim());
	        				                ps.setString(5, coordinator.getEmailAddress().trim());
	        				    		    
	        				                ps.executeUpdate();
	        				    		    connection.commit();
	        				    			connection.setAutoCommit(true);	
	        				    			connection.close();
	        			               }catch (Exception ex) {
	        				    			    if (connection!=null){				
	        				    					connection.rollback();	
	        				    					connection.setAutoCommit(true);
	        				    					connection.close();
	        				    					returnStr= "Database error, records have not been saved ";	
	        				    			    }
	        				    		     }		
	        			               return returnStr;
	        		    }
	                    public String  updateCoordinator(Coordinator coordinator,String currProv)throws Exception{
           	                               String workstation=coordinator.getWorkStationCode();
           	                               String returnStr="";
                                            if(coordinator.getSadecInt().trim().equals("Y")){
	        	                                  if(!checkIfSadecIntRowExists(workstation)){
	        		                                  insertSadecIntCode(workstation);
	        	                                  }
	                                        }
       	                                    Connection connection = null;
			                               try{
				                                  JdbcTemplate jdt =dbutil.getdbcTemplate();
				                                  connection = jdt.getDataSource().getConnection();
				                                  connection.setAutoCommit(false);
				                                   PreparedStatement ps = connection.prepareStatement("update  tpuwsc set telephone_number=?,email_address=?"+
				                                                        "  where mk_persno=?  and  mk_prv_code=?");
								                   ps.setString(1, coordinator.getContactNumber().trim());
				                                   ps.setString(2, coordinator.getEmailAddress().trim());
				                                   ps.setString(3,coordinator.getPersonnelNumber().trim() );
				                                   ps.setString(4,coordinator.getWorkStationCode().trim());
					                               ps.executeUpdate();
				    		                       connection.commit();
				    			                   connection.setAutoCommit(true);	
				    			                   connection.close();
			                             }catch (Exception ex) {
				    			                      if (connection!=null){				
				    					                     connection.rollback();	
				    					                     connection.setAutoCommit(true);
				    					                     connection.close();
				    					                     returnStr= "Database error, records have not been updated ";	
				    			                     }
				    		            }		
			                        return returnStr;
		                }
	        			private boolean checkIfSadecIntRowExists(String sadecIntCode)throws Exception{
                        	              String query="Select  code  from prv    where code="+sadecIntCode;
                        	              String errorMsg="CoordinatorDAO:Error reading prv ";
	                                      String code=dbutil.querySingleValue(query,"code",errorMsg);
	                                      if(code.equals("")){
	            	                            return false;
	                                      }else{
	            	                            return true;
	                                      }
                       }
                       public void insertSadecIntCode(String sadecIntCode )throws Exception{//Insert SADEC and International in the tables of provinces
              	                      String sql="Insert into prv(code,eng_description,in_use_flag)values("+
              	                              sadecIntCode+",'SADEC and International','N')";
              	                     String errorMsg="CoordinatorDAO:Error inserting into prv ";
              	                      dbutil.update(sql,errorMsg);
                       }
}
