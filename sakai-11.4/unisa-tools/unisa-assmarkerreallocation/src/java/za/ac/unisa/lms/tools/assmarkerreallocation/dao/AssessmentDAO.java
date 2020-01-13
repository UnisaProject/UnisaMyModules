package za.ac.unisa.lms.tools.assmarkerreallocation.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.map.ListOrderedMap;
import org.springframework.jdbc.core.JdbcTemplate;

import za.ac.unisa.lms.dao.Gencod;
import za.ac.unisa.lms.dao.StudentSystemGeneralDAO;
import za.ac.unisa.lms.db.StudentSystemDAO;
import za.ac.unisa.lms.domain.assessmentCriteria.Assignment;
import za.ac.unisa.lms.tools.assmarkerreallocation.actions.Utilities;
import za.ac.unisa.lms.tools.assmarkerreallocation.forms.AssessmentDetails;
public class AssessmentDAO extends MarkingLanguageDAO{
	 private String sqlForGettingAssessmentList(short acadYear,short semester,String studyUnit){
		                  String sql="select unique_nr,closing_date,assignment_nr,final_submit_date ,assess_group_gc230,"+
	                                 " type,online_type_gc176  from   unqass  where   year="+acadYear+"  and period="+semester+" and "+
		                		     " mk_study_unit_code='"+studyUnit+"'";
		                  return  sql;
	 }
	 public String getAssessmentFormat(int uniqueNum){
		             String sql="select unique_nr,closing_date,assignment_nr,final_submit_date ,assess_group_gc230,"+
                               " type,online_type_gc176 from   unqass  where  unique_nr="+uniqueNum;
		             JdbcTemplate jdt = new JdbcTemplate(getDataSource());
                     List queryList = jdt.queryForList(sql);
                     Iterator i = queryList.iterator();
                     String  format="";
                     while (i.hasNext()) {
                           ListOrderedMap data = (ListOrderedMap) i.next();
                           format=Utilities.replaceNull(data.get("online_type_gc176"));
                           if(!format.equals("")){
                        	    	  format=decodeGenCode(176,format);
                           }else{
                        	      format="Written";
                           }
                     }
                     return  format;
	 }
	 public String getAssSubTypFromYrmk(String uniqueNum)throws Exception{
         String sql="select sub_type_gc206 from   yrmrk  where  fk_unique_nr="+uniqueNum;
         JdbcTemplate jdt = new JdbcTemplate(getDataSource());
         List queryList = jdt.queryForList(sql);
         Iterator i = queryList.iterator();
         String  format="";
         while (i.hasNext()) {
               ListOrderedMap data = (ListOrderedMap) i.next();
               format=Utilities.replaceNull(data.get("sub_type_gc206"));
         }
         return  format;
    }
	public String getAssTypFromYrmk(String uniqueNum)throws Exception{
         String sql="select type from   yrmrk  where  fk_unique_nr="+uniqueNum;
         JdbcTemplate jdt = new JdbcTemplate(getDataSource());
         List queryList = jdt.queryForList(sql);
         Iterator i = queryList.iterator();
         String  format="";
         while (i.hasNext()) {
               ListOrderedMap data = (ListOrderedMap) i.next();
               format=Utilities.replaceNull(data.get("type"));
         }
         return  format;
     }
	 private String decodeGenCode(int getCategoryCode,String type){
		               StudentSystemGeneralDAO dao = new StudentSystemGeneralDAO();
			           Gencod gencod = new Gencod();
			           gencod = dao.getGenCode(""+getCategoryCode,type);
			           if (gencod!=null && gencod.getEngDescription()!=null){
				               return gencod.getEngDescription();
			           }				
		               return "";
	 }
	 public List<AssessmentDetails>  getAssessmentList (short acadYear,short semester,String studyUnit) throws Exception{
		                         String sql=sqlForGettingAssessmentList(acadYear,semester,studyUnit);
	                             JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		                         List queryList = jdt.queryForList(sql);
                                 Iterator i = queryList.iterator();
                                 List<AssessmentDetails>  assessmentsList=new ArrayList<AssessmentDetails>();
		                         while (i.hasNext()) {
			                           ListOrderedMap data = (ListOrderedMap) i.next();
			                           AssessmentDetails assessmentModule=new AssessmentDetails();
			                           setAssessmentModeldata(assessmentModule,data);
			                           assessmentsList.add(assessmentModule);
		                         }
		                         return  assessmentsList;
    }
	private void setAssessmentModeldata(AssessmentDetails assessmentModule,ListOrderedMap data)throws Exception{
		               assessmentModule.setNumber(Utilities.replaceNull(data.get("assignment_nr")));
                       assessmentModule.setUniqueNumber(Utilities.replaceNull(data.get("unique_nr")));
                       String closingDate=Utilities.replaceNull(data.get("closing_date"));
                       if(!closingDate.equals("")){
                    	   closingDate=closingDate.substring(0,10);
                       }
                       assessmentModule.setDueDate(closingDate);
                       if(!closingDate.equals("")){
                    	   closingDate=closingDate.substring(0,10);
                    	   if(closingDate.trim().equals("0001-01-01")){
                    		   closingDate="";
                    	   }
                       }
                       String submitDate=Utilities.replaceNull(data.get("final_submit_date"));
                       if(!submitDate.equals("")){
                    	   submitDate=submitDate.substring(0,10);
                    	   if(submitDate.trim().equals("0001-01-01")){
                    		   submitDate="";
                    	   }
                       }
                       assessmentModule.setFinalMarkingDate(submitDate);
                       String group=Utilities.replaceNull(data.get("assess_group_gc230"));
                       if(group.equals("")){
                    	       assessmentModule.setType(group);
                       }else{
                    	      String assessmentGroup=decodeGenCode(230,group);
                              assessmentModule.setType(assessmentGroup+"("+group+")");
                       }
                        String type=Utilities.replaceNull(data.get("type"));
                       String onlinetype=Utilities.replaceNull(data.get("online_type_gc176"));
                       assessmentModule.setMarkerExist(markerExist(type.trim(),onlinetype.trim()));
                       String assessFormat=getFormat(type.trim(),onlinetype.trim(), assessmentModule.getUniqueNumber());
                       assessmentModule.setFormat(assessFormat);
     }
     private boolean markerExist(String  type,String onlinetype){
    	                   boolean markerExist=true;
    	                   if(type.equals("A")){
    	                	   markerExist=false; 
 	                        }else if(type.equals("H")){
 	                        	   if(onlinetype.equals("BL")||onlinetype.equals("SA")||
 	                            		  onlinetype.equals("DF")||onlinetype.equals("SA")||
 	                            		  onlinetype.equals("BL")||onlinetype.equals("XA")||
 	                            		  onlinetype.equals("OR")){
 	                            	             markerExist=false;
 	                                }
 	                        }
    	                   return markerExist;
 	 }
     private String  getFormat(String  type,String onlinetype,String uniqueNum)throws Exception{
                         String format="";
                         if(type.equals("A")){
                        	 format=decodeGenCode(93,type);
                         }else if(type.equals("H")){
                        	        format=getWrittenFormats(onlinetype,uniqueNum);
           	             }
                      return format;
     }
     private String  getWrittenFormats(String onlinetype,String uniqueNum)throws Exception{
	                       String format="";
	                        if(onlinetype.equals("MS")){
                            	     format=getMyUnisaFormats(onlinetype,uniqueNum);
                            }else if(onlinetype.equals("")){
                                       format=getWrittenFormatsWithNullOnlineType(onlinetype,uniqueNum);
                            }else{
                                     format=getFormatsFromGen176Codes(onlinetype);
                            }
	                 return  format;
     }
     private String getFormatsFromGen176Codes(String onlinetype){
    	 if(onlinetype.equals("BL")){
    		 return  "Blog";
    	 }else  if(onlinetype.equals("SA")){
    		 return "SAmigo";
    	 }
    	 else if(onlinetype.equals("DF")){
    		   return "Forum";
    	 }else if(onlinetype.equals("XA")){
    		 return   "External";
    	 }else{
        		return "Oral";	
    	 }
     }
     private String  getWrittenFormatsWithNullOnlineType(String onlinetype,String uniqueNum)throws Exception{
                           String format="";
                           String subType=getAssSubTypFromYrmk(uniqueNum);
                           if(subType.trim().equals("")){
                                   format="Written";
                           }else{
                        	   if(subType.trim().equals("PORTFOLIO")){
                            	   format= "Portfolio";
                               }else if(subType.trim().equals("PRACTICAL")){
                            	   format ="Practical";
                               }else{
                            	   format ="Project";
                               }
                           }
                        return  format;
     }
     private String  getMyUnisaFormats(String onlinetype,String uniqueNum)throws Exception{
                         String format="";
                         String typeFromYrmrk=getAssTypFromYrmk(uniqueNum);
                         if(typeFromYrmrk.trim().equals("I")||typeFromYrmrk.trim().equals("G")||typeFromYrmrk.trim().equals("O")){
                               format="myUnisa";
                         }else if(typeFromYrmrk.trim().equals("P")){
                                    String subType=getAssSubTypFromYrmk(uniqueNum);
                                   if(subType.trim().equals("PORTFOLIO")){
                                	   format= "Portfolio";
                                   }else if(subType.trim().equals("PRACTICAL")){
                                	   format ="Practical";
                                   }else{
                                	   format ="Project";
                                   }
                         }else if(typeFromYrmrk.trim().equals("R")){
                        	   format="Peer Report";
                         }else if(typeFromYrmrk.trim().equals("S")){
                        	 format="Peer";
                         }else{
                        	 format="myUnisa";
                         }
                      return  format;
     }
	 public boolean isAssPlanAuthorised(String studyUnit,Short year,Short period) {
			String sql = "select count(*) " +
			             "from asslog " +
			             "where year = " + year +
			             " and period = " + period +
			             " and mk_study_unit_code = '" + studyUnit.toUpperCase().trim() + "'" +
			             " and TYPE_GC52 = 'ASSESSCRIT'" +
			             " and ACTION_GC53 ='AUTHORISED'";
			           
			 JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			int result = jdt.queryForInt(sql) ;
			if (result == 0) {
				return false;
			} else {
				return true;
			}
		}
	 public List getFileFormats(String studyUnit, int unique_nr) throws Exception{
	 		
	 		ArrayList list = new ArrayList();
	 		
	 		String sql = "select extention from onasfm " +
	 		" where mk_study_unit_code='" + studyUnit.toUpperCase().trim() + "'" +
	 		" and mk_unique_nr=" + unique_nr;		
	 		
	 		try{ 
	 			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
	 			List queryList = jdt.queryForList(sql);
	 			String fileExtention="";
	 			Iterator i = queryList.iterator();
	 			while (i.hasNext()) {
	 				ListOrderedMap data = (ListOrderedMap) i.next();
	 				fileExtention = data.get("extention").toString();
	 				list.add(fileExtention);				
	 			}
	 		}
	 		catch (Exception ex) {
	 			throw new Exception("AssMarkerReallocationDao : Error reading table onasfm/ " + ex);
	 		}		
	 		return list;	
	 	}
	 public boolean periodDetailExist(String studyUnit,short year,short period) {
			               String sql = "select count(*) FROM SUNPDT  where mk_academic_year="+year+"  and semester_period="+period+" and FK_suncode='"+studyUnit+"'";
			               JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			               int result = jdt.queryForInt(sql) ;
			               if (result == 0) {
				                  return false;
			               } else {
				                    return true;
			               }
		}
	 public String  getAssFormat(String gencode) throws Exception{
	                     String sql= "Select eng_description  from gencod where fk_gencatcode=176 and in_use_flag='Y'  and code='"+gencode+"'";
	                     JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		                 List queryList = jdt.queryForList(sql);
		                 Iterator i = queryList.iterator();
		                 String   assFormat="";
		                 while (i.hasNext()) {
			                   ListOrderedMap data = (ListOrderedMap) i.next();
			                   assFormat=data.get("eng_description").toString();
	                    }
		             return assFormat;		
   }
}
