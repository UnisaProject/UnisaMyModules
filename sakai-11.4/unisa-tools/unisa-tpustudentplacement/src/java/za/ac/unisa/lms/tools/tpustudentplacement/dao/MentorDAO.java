package za.ac.unisa.lms.tools.tpustudentplacement.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.map.ListOrderedMap;
import org.springframework.jdbc.core.JdbcTemplate;

import za.ac.unisa.lms.tools.tpustudentplacement.model.MentorFilteringData;
import za.ac.unisa.lms.tools.tpustudentplacement.model.MentorModel;
import za.ac.unisa.lms.tools.tpustudentplacement.utils.FileExtractorClass;
import za.ac.unisa.lms.tools.tpustudentplacement.utils.PlacementUtilities;
public class MentorDAO extends MentorModel{
	
	public void  extractFile(HttpServletRequest request,
            HttpServletResponse response,String fileToRead,
            String outputFileName){
                  fileExtractorClass.extractFile(request,response,fileToRead,outputFileName);
    }
	 FileExtractorClass fileExtractorClass;
	    public MentorDAO(){
               dbutil=new databaseUtils();
                fileExtractorClass=new FileExtractorClass();
     } 
	  
	   databaseUtils dbutil;
	   public boolean mentorExists(int mentorcode)throws Exception{
		                   String query="select count(*) from tpumen where code="+mentorcode; 
                           String errorMsg="MentorDAO:Error reading tpumen";
                           short tot=(short)dbutil.queryInt(query,errorMsg);
                           if(tot>0){
                        	    return true;
                           }
                           return false;
       }
	     
	   private List  getMentors(String sql) throws Exception{
           String errorMsg="MentorDAO:Error reading tpumen";
           List queryList=dbutil.queryForList(sql,errorMsg);
           List<MentorModel> mentorList=new ArrayList<MentorModel>();
for (int i=0; i<queryList.size();i++){
    ListOrderedMap data = (ListOrderedMap) queryList.get(i);
    MentorModel mentorModelInList=new MentorModel();
    mentorModelInList.setMentorCode(Integer.parseInt(dbutil.replaceNull(data.get("code"))));
    mentorModelInList.setSchoolCode(Integer.parseInt(dbutil.replaceNull(data.get("mk_school_code"))));
    if(mentorModelInList.getSchoolCode()!=0){
        mentorModelInList.setSchoolName(dbutil.replaceNull(data.get("schoolName")));
    }
    mentorModelInList.setTrained(dbutil.replaceNull(data.get("trained")));
    mentorModelInList.setTitle(dbutil.replaceNull(data.get("mk_title")));
    mentorModelInList.setInitials(dbutil.replaceNull(data.get("initials")));
    mentorModelInList.setSurname(dbutil.replaceNull(data.get("surname")));
    mentorModelInList.setName(mentorModelInList.getTitle()+" "+mentorModelInList.getInitials()+" "+mentorModelInList.getSurname());
    mentorModelInList.setCountryCode(dbutil.replaceNull(data.get("mk_country_code")));
    mentorModelInList.setOccupation(dbutil.replaceNull(data.get("occupation")));
    mentorModelInList.setInUseFlag(dbutil.replaceNull(data.get("in_use_flag")));
    mentorModelInList.setEmailAddress(dbutil.replaceNull(data.get("email_Address")));
    mentorModelInList.setCellNumber(dbutil.replaceNull(data.get("cell_number")));
    mentorModelInList.setPhoneNumber(dbutil.replaceNull(data.get("work_number")));
    mentorModelInList.setFaxNumber(dbutil.replaceNull(data.get("fax_number")));
    if(mentorModelInList.getSchoolCode()!=0){
         mentorModelInList.setDistrictName(dbutil.replaceNull(data.get("districtName")));
         mentorModelInList.setProvinceName(dbutil.replaceNull(data.get("provinceName")));
    }
    mentorList.add(mentorModelInList);
}
return mentorList;
}
	      public List  getMentors(MentorFilteringData filteringData) throws Exception{
	    	                List listOfLinkedMentors= getMentorsLinkedToSch(filteringData);
	    	                if((filteringData.getMentorFilterSchoolCode()==0)&&(filteringData.getMentorFilterProvince()==0)&&(filteringData.getMentorFilterDistrict()==0)){
	    	                          String filter=filteringData.getMentorFilter();
	    	                          String trained=filteringData.getMentorIsTrained();
	    	                          String countrycode=filteringData.getMentorFilterCountry();
	                                  List listOfNonLinkedMentors= getMentorsNotLinkedToSch(filter,trained,countrycode);
	                                  Iterator iter=listOfNonLinkedMentors.iterator();
	                                  while(iter.hasNext()){
	                                        listOfLinkedMentors.add(iter.next());
	                                  }
	    	                }
	                        return listOfLinkedMentors;
	       }
	 	 public List  getMentorsLinkedToSch(MentorFilteringData filteringData) throws Exception{
		   String countrycode=filteringData.getMentorFilterCountry();
		   short district=filteringData.getMentorFilterDistrict();
		   short prov=filteringData.getMentorFilterProvince();
		   int schoolCode=filteringData.getMentorFilterSchoolCode();
		   String filter=filteringData.getMentorFilter();
		   String  isTrained=filteringData.getMentorIsTrained();
           String  sql="select a.code as code,a.mk_school_code as mk_school_code,f.name as schoolName,trained,mk_title,initials,surname,a.mk_country_code as mk_country_code,"+
		               " occupation, a.in_use_flag as in_use_flag,email_address,cell_number,work_number,fax_number " ;
          
           		 if(countrycode.equalsIgnoreCase(PlacementUtilities.getSaCode())){
        	                  sql+=(" ,d.eng_description as provinceName ");
      	              	   sql+=(", e.eng_description as districtName ");
        	    }
           		sql+=(" from tpumen a,adrph b,tpusch f ");
           if(countrycode.equalsIgnoreCase(PlacementUtilities.getSaCode())){
        	                  sql+=(" ,prv d");
      	                	   sql+=(",ldd e");
           }
           sql+=("  where a.in_use_flag='Y' and a.mk_school_code<>0 and a.code=b.reference_no and a.MK_SCHOOL_CODE=f.code and f.mk_country_code=a.MK_COUNTRY_CODE and fk_adrcatcode=232"); 
           if((filter!=null)&&(!filter.trim().equals(""))){
           	  sql+=(" AND surname like '"+filter.trim()+"' ");
             }
           //if(!isTrained.trim().equalsIgnoreCase("All")){
                   if((isTrained!=null)&&(!isTrained.trim().equals(""))){
           	        sql+=(" AND trained='"+isTrained+"'");
                   }
          // }
           if(schoolCode!=0){
           	      sql+=(" and  a.MK_SCHOOL_CODE="+schoolCode);
           }
           if(countrycode.equalsIgnoreCase(PlacementUtilities.getSaCode())){
        	  	sql+=(" and f.mk_prv_code=d.code ");
               	sql+=(" and f.mk_district_code= e.code ");
            }
           if(countrycode.equalsIgnoreCase(PlacementUtilities.getSaCode())){
       	    
               if(prov!=0){
               	sql+=(" and d.code ="+prov);
               }
               if(district!=0){
               	sql+=("  and  e.code="+district);
               }
           }
           if(!countrycode.trim().equals("0")){
        		sql+=("  and a.mk_country_code='"+countrycode+"'");
           }
            return getMentors(sql);
    }
	     public List  getMentorsNotLinkedToSch( String filter, String  isTrained,String countryCode) throws Exception{
			    String  sql="select a.code as code,a.mk_school_code as mk_school_code,trained,mk_title,initials,surname,a.mk_country_code as mk_country_code,"+
			               " occupation, a.in_use_flag as in_use_flag,email_address,cell_number,work_number,fax_number from tpumen a,adrph b " ;
	           sql+=("  where  a.code=b.reference_no  and fk_adrcatcode=232   and mk_school_code=0 and in_use_flag='Y'"); 
	           if((filter!=null)&&(!filter.trim().equals(""))){
	           	  sql+=(" AND surname like '"+filter.trim()+"' ");
	             }
	          // if(!isTrained.trim().equalsIgnoreCase("All")){
	                   if((isTrained!=null)&&(!isTrained.trim().equals(""))){
	           	        sql+=(" AND trained='"+isTrained+"'");
	                   }
	           //}
	           if(!countryCode.trim().equals("0")){
	        		sql+=("  and a.mk_country_code='"+countryCode+"'");
	           }
	           
	           return getMentors(sql);
	    }

     public void  getMentor(int mentorcode)throws Exception{
    	                  String query="select * from tpumen a,adrph b,tpusch f where a.code=b.reference_no  and  a.MK_SCHOOL_CODE=f.code and a.code="+mentorcode+
    	            		     "and fk_adrcatcode=232"; 
    	                  String errorMsg="MentorDAO:Error reading tpumen";
                          List queryList=dbutil.queryForList(query,errorMsg);
                       for (int i=0; i<queryList.size();i++){
                          ListOrderedMap data = (ListOrderedMap) queryList.get(i);
                          setMentorCode(Integer.parseInt(dbutil.replaceNull(data.get("code"))));
                          setSchoolCode(Integer.parseInt(dbutil.replaceNull(data.get("mk_school_code"))));
                          setSchoolName(dbutil.replaceNull(data.get("name")));
                          setTrained(dbutil.replaceNull(data.get("trained")));
                          setTitle(dbutil.replaceNull(data.get("mk_title")));
                          setInitials(dbutil.replaceNull(data.get("initials")));
                          setSurname(dbutil.replaceNull(data.get("surname")));
                          setName(getTitle()+" "+getInitials()+" "+getSurname());
                          setCountryCode(dbutil.replaceNull(data.get("mk_country_code")));
                          setOccupation(dbutil.replaceNull(data.get("occupation")));
                          setInUseFlag(dbutil.replaceNull(data.get("in_use_flag")));
                          setEmailAddress(dbutil.replaceNull(data.get("email_Address")));
                          setCellNumber(dbutil.replaceNull(data.get("cell_number")));
                          setPhoneNumber(dbutil.replaceNull(data.get("work_number")));
                          setFaxNumber(dbutil.replaceNull(data.get("fax_number")));
	    			 }
    }
    public void saveMentor()throws Exception{
    	           String query="insert into tpumen(CODE,MK_SCHOOL_CODE,TRAINED,SURNAME,MK_TITLE,INITIALS,MK_COUNTRY_CODE,OCCUPATION,IN_USE_FLAG)values("
    	        		   +"?,?,?,?,?,?,?,?,?)";
    	           Connection connection = null;
    	          

	                 try{
	                	 String sql = "select max(code) + 1 from tpumen";
	         	       	JdbcTemplate jdt = dbutil.getdbcTemplate();
	         	       	int mentorCode = jdt.queryForInt(sql) ;
	         	       	if (mentorCode==0){
	         	       		mentorCode=1;
	         	       	}
	         	       setMentorCode(mentorCode);
		                connection = jdt.getDataSource().getConnection();
		                connection.setAutoCommit(false);
		                Statement stmt = connection.createStatement();
		        		PreparedStatement ps = connection.prepareStatement(query);
		                ps.setInt(1, getMentorCode());
		  	            ps.setInt(2, getSchoolCode());
         	       	    ps.setString(3,getTrained());
		                ps.setString(4,getSurname());
		                ps.setString(5,getTitle());
		                ps.setString(6, getInitials());
		                ps.setString(7, getCountryCode());
		                ps.setString(8, getOccupation());
		                ps.setString(9,"Y");
		    		    ps.executeUpdate();
		    		    
		    		    int totRecsInAadrph=jdt.queryForInt("select count(*) from adrph where reference_no="+getMentorCode()+"and  fk_adrcatcode=232 ") ;
		    		    if(totRecsInAadrph==0){
		    		    stmt.executeUpdate("insert into adrph (reference_no,home_number,work_number,fax_number," +
		    					"email_address,fk_adrcatcode,cell_number,courier_contact_no,cell_phone_verifie," +
		    					"email_verified)" +
		    					"values " +
		    					"(" + getMentorCode() + "," +"'  '" +
		    					",'" + getPhoneNumber().trim() + "'," +
		    					"'" +getFaxNumber().trim() + "'," +
		    					"'" + getEmailAddress().toUpperCase().trim() + "'," +
		    					"232," +
		    					"'" + getCellNumber().trim() + "',"  +"'  '" +
		    				 					",'N'," +
		    					"'N')");	
		    		    }
		    			 connection.commit();
			    		 connection.setAutoCommit(true);	
			    		 connection.close();
	               }catch (Exception ex) {
		    			    if (connection!=null){				
		    					connection.rollback();	
		    					connection.setAutoCommit(true);
		    					connection.close();
		    					throw  new Exception("MentorDao : Error saving  a  record, records have been rollbacked / " + ex,ex);	
		    				}
		    		     }		
	}
    public void updateMentor()throws Exception{
    	 String query="update tpumen set MK_SCHOOL_CODE=?,TRAINED=?,SURNAME=?,MK_TITLE=?,INITIALS=?,MK_COUNTRY_CODE=?,OCCUPATION=?,IN_USE_FLAG=? "+
                       " where  code=?";
      	 Connection connection = null;
           try{
                 JdbcTemplate jdt =dbutil.getdbcTemplate();
                 connection = jdt.getDataSource().getConnection();
                 Statement stmt = connection.createStatement();
	        	 connection.setAutoCommit(false);
                 PreparedStatement ps = connection.prepareStatement(query);
                    ps.setInt(1, getSchoolCode());
	                ps.setString(2,getTrained());
	                ps.setString(3,getSurname());
	                ps.setString(4,getTitle());
	                ps.setString(5, getInitials());
	                ps.setString(6, getCountryCode());
	                ps.setString(7, getOccupation());
	                ps.setString(8, "Y");
	                ps.setInt(9,getMentorCode());
 	          ps.executeUpdate();
 	         stmt.executeUpdate("update  adrph  set  work_number='"+getPhoneNumber().trim()+"',fax_number='" +getFaxNumber().trim()+"'"+
 					",email_address='"+getEmailAddress().toUpperCase().trim()+"',cell_number='"+getCellNumber().trim()+"' where reference_no="+ getMentorCode()+" and fk_adrcatcode=232");	
 			 connection.commit();
  			 connection.setAutoCommit(true);	
  			 connection.close();
         }catch (Exception ex) {
  			    if (connection!=null){				
  					connection.rollback();	
  					connection.setAutoCommit(true);
  					connection.close();
  					throw  new Exception("MentorDao : Error updating  records, records have been rollbacked / " + ex,ex);	
  				}
          }
    }
     public void deleteMentor(int mentorCode)throws Exception{
    	             String sql="update tpumen set in_use_flag='N' where code= "+mentorCode;
    	             Connection connection = null;
		               try{
			                JdbcTemplate jdt =dbutil.getdbcTemplate();
			                connection = jdt.getDataSource().getConnection();
			                connection.setAutoCommit(false);
			                Statement stmt = connection.createStatement();	
			                stmt.executeUpdate(sql);
			                connection.commit();
			    			connection.setAutoCommit(true);	
			    			connection.close();
		               }catch (Exception ex) {
			    			    if (connection!=null){				
			    					connection.rollback();	
			    					connection.setAutoCommit(true);
			    					connection.close();
			    					throw new Exception("MentorDao : Error deleting records, records have been rollbacked / " + ex,ex);	
			    			    }
			    	 }	
     }
     public void removeFromSchool(int mentorCode)throws Exception{
    	               String query="update tpumen set MK_SCHOOL_CODE=0 "+
                       " where  code="+mentorCode;
      	 Connection connection = null;
           try{
                    JdbcTemplate jdt =dbutil.getdbcTemplate();
                    connection = jdt.getDataSource().getConnection();
                    connection.setAutoCommit(false);
	                Statement stmt = connection.createStatement();	
	                stmt.executeUpdate(query);
	                connection.commit();
  			        connection.setAutoCommit(true);	
  			       connection.close();
         }catch (Exception ex) {
  			    if (connection!=null){				
  					connection.rollback();	
  					connection.setAutoCommit(true);
  					connection.close();
  					throw  new Exception("MentorDao : Error updating  records, records have been rollbacked / " + ex,ex);	
  				}
          }
    }
     public void linkMentorToSchool(int schoolCode,int mentorCode)throws Exception{
                       String query="update tpumen set MK_SCHOOL_CODE= "+schoolCode+
                               " where  code="+mentorCode;
                       Connection connection = null;
                       try{
                             JdbcTemplate jdt =dbutil.getdbcTemplate();
                             connection = jdt.getDataSource().getConnection();
                             connection.setAutoCommit(false);
                             Statement stmt = connection.createStatement();	
                             stmt.executeUpdate(query);
                             connection.commit();
                             connection.setAutoCommit(true);	
                             connection.close();
                     }catch (Exception ex) {
                             if (connection!=null){				
		                          connection.rollback();	
		                          connection.setAutoCommit(true);
		                          connection.close();
		                           throw  new Exception("MentorDao : Error updating  records, records have been rollbacked / " + ex,ex);	
	                          }
                     }
     }
}
