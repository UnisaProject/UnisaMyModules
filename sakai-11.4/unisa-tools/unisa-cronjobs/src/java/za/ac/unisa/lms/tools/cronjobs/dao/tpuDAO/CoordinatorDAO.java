package za.ac.unisa.lms.tools.cronjobs.dao.tpuDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.map.ListOrderedMap;
import org.springframework.jdbc.core.JdbcTemplate;

import za.ac.unisa.lms.tools.cronjobs.forms.model.Coordinator;
import za.ac.unisa.lms.tools.cronjobs.forms.util.DateUtil;

public class CoordinatorDAO {
                        
	                    databaseUtils dbutil;
                        public CoordinatorDAO(){
                                    dbutil=new databaseUtils();
                        }
                        public   String getCoordinatorActive(String username) throws Exception{
                        	               String persno="";
	                    	               String persnoFromStaff=getPersonnelNumber(username);
	                    	               String errorMsg="CoordinatorDAO:Error reading tpuwsc";
	                    	               if(!persnoFromStaff.equals("")){
	                    	            	       String query="Select  mk_persno  from tpuwsc where mk_persno="+persnoFromStaff;
                                                   persno= dbutil.querySingleValue(query,"mk_persno",errorMsg);
	                    	               }
	                    	               if(persno.equals("")){
	                    	            	     return "N";
	                    	               }else{
	                    	            	      return "Y";
	                    	               }
	                    }
	                    public String getPersonnelNumber(String username)throws Exception{
	                    	            String query="select   persno, to_char(resign_date,'YYYY-MM-DD') as resignDate from  staff where novell_user_id='"+username+"'";
	                    	            String errorMsg="CoordinatorDAO:Error reading staff";
            	                        List queryList=dbutil.queryForList(query,errorMsg);
            	                        String personnelNum="";
            	                        for (int i=0; i<queryList.size();i++){
       			                                Coordinator   coordinator = new Coordinator();	
                                                ListOrderedMap data = (ListOrderedMap) queryList.get(i);
                                                coordinator.setPersonnelNumber(dbutil.replaceNull(data.get("persno")));
                                                coordinator.setContractExpiryDate(dbutil.replaceNull(data.get("resignDate")));
                                                DateUtil  dateutil=new DateUtil();
                                                String date=coordinator.getContractExpiryDate().trim();
                                                if(date.equals("")||dateutil.isDateGreaterThanSysDate(date)){
                                                	  personnelNum=coordinator.getPersonnelNumber();
                                                      break;
            	                                }
                                        }
                                        return personnelNum;
            	        }
	                    public   String  getSadecInt(String personnelNumber)throws Exception{
	                    	                String query="Select  mk_sadec_int _ind  from tpuwsc where mk_persno="+personnelNumber;
	                    	                String errorMsg="CoordinatorDAO:Error reading tpuwsc";
                                            return dbutil.querySingleValue(query,"mk_sadec_int _ind",errorMsg);
	                    }
	                    public   boolean isCoordinatorLinked(String personnelNum,String workstation)throws Exception{
   	                                 String query="Select  count(*)   as tot from  tpuwsc where mk_persno="+personnelNum+" and mk_prv_code='"+workstation+"'";
   	                                 String errorMsg="CoordinatorDAO:Error reading tpuwsc";
   	                               String persno=dbutil.querySingleValue(query,"tot",errorMsg);
   	                               if(persno.equals("")||persno.equals("0")){
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
                 	           String sql="Select a.title || ' ' || a.name  || ' ' ||  a.surname as coordName," +
                 	           		      " a.novell_user_id  networkcode,b.mk_persno as personnelNumber,b.sadec_int_ind as sadecIndicator," +
                 	           		      " c.eng_description workstationDescr,c.code as workstationCode, a.email_address as emailAddress,a.contact_telno as"+
                 	           		      " tel from STAFF a,TPUWSC b, prv c where a.persno = b.mk_persno and a.persno="+persno+
                 	           		      " and b.mk_prv_code=c.code  order by workstationDescr,networkcode";
 	                           List coordinatorList  = new ArrayList<Coordinator>();
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
                                      coordinator.setEmailAddress(dbutil.replaceNull(data.get("emailAddress")));
                                      coordinator.setContactNumber(dbutil.replaceNull(data.get("contactNumber")));
                                      coordinator.setEmailAddress(dbutil.replaceNull(data.get("emailAddress")));
                                      coordinator.setContactNumber(dbutil.replaceNull(data.get("tel")));
                                    
                                }
                               return coordinator;
                 }
           	     public List getCoordinatorList() throws Exception{
	                    	           String sql="Select a.title || ' ' || a.name  || ' ' ||  a.surname as coordName," +
	                    	           		      " a.novell_user_id  networkcode,a.persno as personnelNumber,b.sadec_int_ind as sadecIndicator," +
	                    	           		      " c.eng_description workstationDescr,c.code as workstationCode  from STAFF a,TPUWSC b, prv c"+
	                    	           		      " where a.persno = b.mk_persno and b.mk_prv_code=c.code order by workstationDescr,networkcode";
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
	                                        
	                                         coordinatorList.add(coordinator);			
	                                  }
	                                  return coordinatorList;
	                    }
	                    public List getCoordinatorList(String personnelNumber) throws Exception{
             	                      String sql="Select a.title || ' ' || a.name  || ' ' ||  a.surname as coordName," +
             	           		                  " a.novell_user_id  networkcode,a.persno as personnelNumber,b.sadec_int_ind as sadecIndicator," +
             	           		                  " c.eng_description workstationDescr,c.code as workstationCode  from STAFF a,TPUWSC b, prv c"+
             	           		                  " where a.persno = b.mk_persno and b.mk_prv_code=c.code order by workstationDescr,networkcode";
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
	                    public String  getCoordinatorForProvince(int provinceCode)throws Exception{
               	                           String sql="Select mk_persno as personnelNumber from TPUWSC  where mk_prv_code="+provinceCode;
         		                           String errorMsg="CoordinatorDAO:Error reading TPUWSC  ";
                                           List queryList = dbutil.queryForList(sql,errorMsg);
                                           if(!queryList.isEmpty()){
                                               ListOrderedMap data = (ListOrderedMap) queryList.get(0);
                                               return dbutil.replaceNull(data.get("personnelNumber"));
                                           }else{
                                        	   return "";
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
	                    	        String sql="Select title || ' ' || name  || ' ' ||  surname as coordName" +
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
	                    public List<Coordinator> getCoordinatorListForProvince(int prov)throws Exception{
	                    	                         String sql="Select mk_persno as personnelNumber from TPUWSC"+
       		                                                   " where mk_prv_code ="+prov;
                                                     List coordinatorList  = new ArrayList<Coordinator>();
                                                     String errorMsg="CoordinatorDAO:Error reading TPUWSC  ";
                                                     List queryList = dbutil.queryForList(sql,errorMsg);
                                                     for (int i=0; i<queryList.size();i++){
                                                           Coordinator   coordinator = new Coordinator();	
                                                           ListOrderedMap data = (ListOrderedMap) queryList.get(i);
                                                           String personnelNum=data.get("personnelNumber").toString();
                                                           coordinator=coordinator.getCoordinator(Integer.parseInt(personnelNum));
                                                           coordinatorList.add(coordinator);			
                                                    }
                                                    return coordinatorList;
                       }	                   
}
