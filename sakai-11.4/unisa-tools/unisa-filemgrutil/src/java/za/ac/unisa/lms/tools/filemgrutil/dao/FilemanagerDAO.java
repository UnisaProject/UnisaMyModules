package za.ac.unisa.lms.tools.filemgrutil.dao;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Hashtable;
import java.util.Map;
import java.util.TreeMap;
import java.util.ArrayList;

import org.apache.commons.collections.functors.NullIsExceptionPredicate;
import org.apache.commons.collections.map.ListOrderedMap;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;

import za.ac.unisa.lms.db.StudentSystemDAO;
import za.ac.unisa.lms.tools.filemgrutil.forms.UtilDetailsForm;
public class FilemanagerDAO extends StudentSystemDAO{

	public boolean isSubjectCodeValid(String subjectCode) throws Exception{
		String query = " SELECT COUNT(*) as COUNT "+
                       " FROM WEBSTM "+
                       " WHERE REF_NAME = '"+subjectCode +"'";
        	try {
        	    String recCount = this.querySingleValue(query,"COUNT");
        	    int counter =  Integer.parseInt(recCount); 

        		if(counter > 0){
        			return true;
        		}	else {
        			return false;
        		}
        		
        	} catch (Exception ex) {
        		throw new Exception("E-Bookshop myUnisa account enquiry: Error occurred / "+ ex,ex);
        		
        	}
		
    }
	
	public boolean isWebIdValid(Integer webId) throws Exception{
		String query = "SELECT COUNT(*) as COUNT "+
                     "FROM WEBSTM "+
                     "WHERE WEBID = "+webId;
        	try {
        	    String recCount = this.querySingleValue(query,"COUNT");
        	    int counter =  Integer.parseInt(recCount); 

        		if(counter > 0){
        			return true;
        		}	else {
        			return false;
        		}
        		
        	} catch (Exception ex) {
        		throw new Exception("Filemanagerutil isWebIdValid: Error / "+ ex,ex);
            }
    }
	
	public String getDocumentPath(Integer webid)
	{
		      JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			  Hashtable ht = new Hashtable();
			  int count = 0, countrec = 0;
			  List queryList1;
			  List queryList2;
			  List queryList3;
			  String path = "";	
		  
			  try {  
			  		
			  String query1 = "select start_academic_yea, doc_name "+
							"from webstm "+
							"where webid = "+webid;
			
			  queryList1 = jdt.queryForList(query1);
			  
			  //System.out.println("\n\n\n--------------------------------------------------------\n"+queryList1+"\n--------------------------------------------------------\n\n\n");
			  Iterator i = queryList1.iterator();
			  i = queryList1.iterator();
			  while(i.hasNext()){
				  ListOrderedMap data1 = (ListOrderedMap) i.next();
				  ht.put("syear",data1.get("start_academic_yea"));
				  ht.put("docname",data1.get("doc_name"));
			  }		  
			  
			  String query2 = "select * from annstm "+
							"where fk_webid = "+webid+
							" and academic_year = "+ht.get("syear");
			  queryList2 = jdt.queryForList(query2);
			  Iterator j = queryList2.iterator();
			  j = queryList2.iterator();
			  
			  //System.out.println(":::::::::::::-----------------" +j+" -----------------:::::::::::::::::::::::");
			  
			  while(j.hasNext()){
				  ListOrderedMap data2 = (ListOrderedMap) j.next();
				  ht.put("acadprd", data2.get("academic_period"));
				  ht.put("name", data2.get("name"));
				  ht.put("lang", data2.get("din_language"));
				  ht.put("type", data2.get("fk_stmtypcode"));
				  ht.put("nr", data2.get("nr"));		
				  
				  //System.out.println(":::::::::::::-----------------" +data2.get("fk_stmtypcode")+" -----------------:::::::::::::::::::::::");
			  }	
			  
			  String query3 = "select mk_study_unit_code "+
							"from sunstm "+
							"where fk_annstmacadyear = "+ht.get("syear")+
							" and fk_annstmacadprd = "+ht.get("acadprd")+
							" and fk_annstmname ='"+ht.get("name")+"'"+
							" and fk_annstm_stmtyp ='"+ht.get("type")+"'"+
							" and fk_annstmnr = "+ht.get("nr")+
							" and fk_annstm_lang ='"+ht.get("lang")+"'";
			
			  queryList3 = jdt.queryForList(query3);
			  Iterator k = queryList3.iterator();
			  k = queryList3.iterator();
			  // while(k.hasNext()){			  	
			  	count = queryList3.size();
				
			 //}	
			  ht.put("lname", ht.get("name"));
			  if((ht.containsValue("tl")) || (ht.containsValue("TL")) || (ht.containsValue("tw")) || (ht.containsValue("TW")) ||(ht.containsValue("tp")) ||(ht.containsValue("TP"))) {
				  ht.put("dirtype", "tl");
			  } else if ((ht.containsValue("ma")) || (ht.containsValue("MA"))){
				  ht.put("dirtype", "ma");
			  } else if ((ht.containsValue("wb")) || (ht.containsValue("WB"))){
				  ht.put("dirtype", "wb");
			  } else if ((ht.containsValue("qb")) || (ht.containsValue("QB"))){
				  ht.put("dirtype", "qb");
			  } else if ((ht.containsValue("mo")) || (ht.containsValue("MO"))){
				  ht.put("dirtype", "mo");
			  } else if ((ht.containsValue("lb")) || (ht.containsValue("LB"))){
				  ht.put("dirtype", "lb");
			  } else if ((ht.containsValue("bl")) || (ht.containsValue("BL"))){
				  ht.put("dirtype", "bl");
			  } else if ((ht.containsValue("bb")) || (ht.containsValue("BB"))){
				  ht.put("dirtype", "bb");
			  } else if ((ht.containsValue("hl")) || (ht.containsValue("HL"))){
				  ht.put("dirtype", "hl");
			  } else if ((ht.containsValue("re")) || (ht.containsValue("RE"))){
				  ht.put("dirtype", "re");
			  } else if ((ht.containsValue("mg")) || (ht.containsValue("MG"))){
				  ht.put("dirtype", "mg");
			  } else {
				  ht.put("dirtype", "sg");
			  }
			  
			 // System.out.println("-----------------------"+ht.get("dirtype")+"----------------------------------------------------------------------------------");
			  
			if (count > 1) {
				path = "collect/"+ht.get("lname")+"/"+ht.get("dirtype")+"/"+ht.get("docname");
				//return true;
			} else {
				path = ht.get("lname")+"/"+ht.get("dirtype")+"/"+ht.get("docname");
				//return false;
			}
					
		   } catch (Exception ex) {
	           //throw new Exception("MyUnisaContentQueryDAO: getDocumentPath: (webid: "+webid+") Error occurred / "+ ex,ex);
			   System.out.println(ex.getMessage());
			}		
			  
			 // System.out.println("\n\n\nPath--------------------------------------------------------\n"+path+"\n--------------------------------------------------------\n\n\n");
			return path;		
		}
	
	private String querySingleValue(String query, String field){
		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		List queryList;
		String result = "";
		

		queryList = jdt.queryForList(query);
		Iterator i = queryList.iterator();
		if (i.hasNext()) {
           ListOrderedMap data = (ListOrderedMap) i.next();
           if (data.get(field) == null){
           } else {
               result = data.get(field).toString();
               
           }
		}
		return result;
  }
	
	
	
	
	//to get the web record for entered subject code.
	
	public String setWebRecord(String Subcode)throws Exception{
		
		String sql= "SELECT * FROM WEBSTM "+
        "WHERE REF_NAME = '"+Subcode +"'";
		String temp ="" ;
		  ArrayList ids = new ArrayList();
		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		List queryList;
		queryList=jdt.queryForList(sql);
		Iterator i=queryList.iterator();
		UtilDetailsForm details = new UtilDetailsForm();
		while(i.hasNext()){
	
		   ListOrderedMap data = (ListOrderedMap) i.next();
		   String startYear = data.get("START_ACADEMIC_YEA").toString();
		   String endYear = data.get("END_ACADEMIC_YEAR").toString();
		   String webId = data.get("WEBID").toString();
		   int webid = Integer.parseInt(webId);
		   int start= Integer.parseInt(startYear);
		   int end= Integer.parseInt(endYear);
		   

		   
		   
		   String sql1 =   " select * from annstm where fk_webid = "+webid+
		   		           " and ACADEMIC_YEAR= (select max(ACADEMIC_YEAR) from "+ 
		                   " annstm where fk_webid = "+webid+")";
		   
		   String acdamicYear ="";
		   String acdamicPeriod="";
		   String name="";
		   String fkstmtypeCode="";
		   String nr=""; 
		   String dinLanguage="";
		   String pubDescription="";
		   String fkstmbarCode="";
		   String submittingDept="";
		   List queryList1;
	             queryList1=jdt.queryForList(sql1);
	  		     Iterator j=queryList1.iterator(); 
	  		     
	  		
	  		  boolean l = queryList1.isEmpty();
	  		 //  System.out.println("list is epmty===="+l);
	  		  if(l==true){
	  			  temp="norecord";
	  			  ids.add(webId);
	  		
	  			//  System.out.println("list is empty");
	  		  }
	  		
	  		  else{
	  		
			 while(j.hasNext()){
				 ListOrderedMap data1 = (ListOrderedMap) j.next();
				    
				  acdamicYear = data1.get("ACADEMIC_YEAR").toString();
			  	  acdamicPeriod = data1.get("ACADEMIC_PERIOD").toString();
			  	  name = data1.get("NAME").toString();
			  	  fkstmtypeCode = data1.get("FK_STMTYPCODE").toString();
			  	  nr = data1.get("NR").toString();
			  	
			  	  dinLanguage = data1.get("DIN_LANGUAGE").toString();
			  	  try{
			  	  pubDescription = data1.get("PUBLISHING_DESCRIP").toString();
			  	  }catch(NullPointerException ne){
			  		  
			  	  }
			  	  try{
			  	  fkstmbarCode = data1.get("FK_STMBARCODE").toString();
			  	  }catch(NullPointerException ne){
			  		  
			  	  }
			  	  try{
			  	  submittingDept = data1.get("SUBMITTING_DEPT").toString();
			  	  }catch(NullPointerException ne){
			  		  
			  	  }
				 
			 }
		
	         
			 while(start<=end){
			
				// System.out.println("start year"+start);
			    String query = "SELECT ACADEMIC_YEAR FROM ANNSTM "+
		                       "WHERE FK_WEBID = "+webid+
			                    "and ACADEMIC_YEAR="+start;
			    
			    String year=querySingleValue(query,"ACADEMIC_YEAR");
		          
		  		 System.out.println("checing  ----year"+year);
		  		
		  	 if(year==null||year.equals("")){
	         
		  		 int count = forEmpty(name,start,acdamicPeriod,fkstmtypeCode,nr,dinLanguage,submittingDept);
				 
				 if (count>=1){
					 boolean web = checkingWebid(name,start,acdamicPeriod,fkstmtypeCode,nr,dinLanguage,submittingDept);
					 if (web==true){
						 boolean sucess = updateWebid(name,start,acdamicPeriod,fkstmtypeCode,nr,dinLanguage,submittingDept,webid);
						 if(sucess=true){
							 temp="sucess";
						 }
					 }
				 }else{
		  
		  	      
		  			  String query2 =   "insert into annstm (academic_year,academic_period,name,"+
 	                                    "fk_stmtypcode,nr,din_language,PUBLISHING_DESCRIP,fk_stmbarcode,submitting_dept,fk_webid)"+
                                        "values (?,?,?,?,?,?,?,?,?,?)";
		  		
		  		
		  			 try{
		  		
		      		 jdt.update(query2,new Object[] {start,acdamicPeriod,name,fkstmtypeCode,nr,dinLanguage,pubDescription,
		      				                                     fkstmbarCode,submittingDept,webid});
                     
                     temp = "sucess"; 
		  			  }catch(Exception ex){
		  				throw new Exception("FilemanagerDAO.java: getWebrecord"+ex);
		  			  }
		  	 } 
		  		  } 
		  
		 String query3 =   "select * from sunstm"+
		                    " where fk_annstmacadyear = "+start+
		                    " and fk_annstmacadprd ="+acdamicPeriod+
		                    " and ref_name ='"+Subcode+"'"+
		                    " and fk_annstm_stmtyp ='"+fkstmtypeCode+"'"+
		                    " and fk_annstmnr = '"+nr+"'"+
		                    " and fk_annstm_lang = '"+dinLanguage+"'";
		  System.out.println("query"+query3);
		              String mk_study_unit = "";
		              String fk_annstmname = "";
		              String fk_stmtyp_ref = "";
		              String ref_no = "";
		              String ref_language = "";
		              String guide_flag = "";
		              String planned_issue_date = "";
		              String issue_quantity = "";
		              String implementation_date = "";
		              String syear=querySingleValue(query3,"FK_ANNSTMACADYEAR");
		             
		              if(syear==null||syear.equals("")){
		            	  
		            	  int sunyear = start-1;
		       
		            	
		            	  String query5 =   " select * from sunstm"+
		                                    " where fk_annstmacadyear = "+sunyear+
		                                    " and fk_annstmacadprd ="+acdamicPeriod+
		                                    " and ref_name ='"+Subcode+"'"+
		                                    " and fk_annstm_stmtyp ='"+fkstmtypeCode+"'"+
		                                    " and fk_annstmnr = '"+nr+"'"+
		                                    " and fk_annstm_lang = '"+dinLanguage+"'";
		            	  int acc_prd = Integer.parseInt(acdamicPeriod);
		            
		            	  mk_study_unit = querySingleValue(query5,"MK_STUDY_UNIT_CODE");
			              fk_annstmname = querySingleValue(query5,"FK_ANNSTMNAME");
			              fk_stmtyp_ref = querySingleValue(query5,"FK_STMTYP_REF");
			              ref_no = querySingleValue(query5,"REF_NO");
			              ref_language = querySingleValue(query5,"REF_LANGUAGE");
			              guide_flag = querySingleValue(query5,"ONLY_GUIDE_FLAG");
			              planned_issue_date = querySingleValue(query5,"PLANNED_ISSUE_DATE");
			              issue_quantity = querySingleValue(query5,"ISSUE_QUANTITY");
			              implementation_date = querySingleValue(query5,"IMPLEMENTATION_DAT");
			              
			          	  List queryList5;
			              queryList5=jdt.queryForList(query5);
			              int sun = queryList5.size();
			       
			              if(sun!=0){
			                
			                                          
		            	  String query4 = " insert into sunstm (mk_study_unit_code,fk_annstmacadyear,"+
		            			          " fk_annstmacadprd, fk_annstmname,fk_annstm_stmtyp,fk_annstmnr,fk_annstm_lang,"+
		            			          " ref_name,fk_stmtyp_ref,ref_no,ref_language,only_guide_flag,planned_issue_date," +
		            			  		  " issue_quantity,IMPLEMENTATION_DAT) " +
		            			  		  " values (?,?,?,?,?,?,?,?,?,?,?,?,to_TIMESTAMP(?,'YYYY-MM-DD HH24:MI:SS.FF1'),?,to_TIMESTAMP(?,'YYYY-MM-DD HH24:MI:SS.FF1'))";
		            			  		  //" values('"+mk_study_unit+"',"+ start+","+acc_prd+",'"+fk_annstmname+"','"+
		            			  		   ///fkstmtypeCode+"','"+nr+"','"+dinLanguage+"','"+Subcode+"','"+fk_stmtyp_ref+"','"+ref_no+"','"+ref_language+"','"
		            			  		   //+guide_flag+"',to_TIMESTAMP('"+planned_issue_date+"','YYYY-MM-DD HH24:MI:SS.FF1'),1,to_TIMESTAMP('"+implementation_date+"','YYYY-MM-DD HH24:MI:SS.FF1'))";
		            			  		  
		            			  		  
		            			  		try{
		            		  			
		            		      		 jdt.update(query4,new Object[] {mk_study_unit,start,acc_prd,fk_annstmname,fkstmtypeCode,nr,
		            		      				                          dinLanguage,Subcode,fk_stmtyp_ref,ref_no,ref_language,guide_flag,planned_issue_date,
		            		      				                        1,implementation_date});
		            			  		
		            			  		
		            			  			
		                              
		                                 
		                                 temp = "sucess";
		                                 
		            		  			  }catch(Exception ex){
		            		  				throw new Exception("FilemanagerDAO.java: getWebrecord"+ex);
		            		  			  }
		                         
		                        }
			              else{
			            	  
			            	  temp = "nosunrecord";
			              }
		                  
		              }
			
		
		start++;
		}
			
			
	  		  }
	  	
	  		
	  		 
		}
		 details.setWedids(ids);
		return temp;
	}
	
     public boolean isSunstmEmpty(String mk_study_unit,int sunyear,int acc_prd,String fk_annstmname,String fkstmtypeCode,String nr,
    		                  String dinLanguage,String Subcode,String fk_stmtyp_ref,String ref_no,String ref_language,
    		                  String guide_flag )throws Exception{
		
		int count=0;
		boolean sunRecord=false;
		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		String query = " select * from sunstm"+
                       " where mk_study_unit_code = '"+mk_study_unit+"'"+
                       " and fk_annstmacadyear = "+sunyear+
                       " and fk_annstmacadprd ="+acc_prd+
                       " and fk_annstmname = '"+fk_annstmname+"'"+
                       " and fk_annstm_stmtyp = '"+fkstmtypeCode+"'"+
                       " and fk_annstmnr = '"+nr+"'"+
                       " and fk_annstm_lang = '"+dinLanguage+"'"+
                       " and ref_name ='"+Subcode+"'"+
                       " and fk_stmtyp_ref = '"+fk_stmtyp_ref+"'"+
                       " and ref_no = '"+ref_no+"'"+
                       " and ref_language = '"+ref_language+"'"+
                       " and only_guide_flag = '"+guide_flag+"'";
                     
                 
		
		List queryList;
        queryList=jdt.queryForList(query);
		     Iterator i=queryList.iterator(); 
		     count = queryList.size();
			if(count>0){
				sunRecord = true;
			}
		return sunRecord;
	}
	
	
	public int forEmpty(String name,int academicyear,String academicperiod,String fkstmtypcode,String nr,String dinlanguage,String submittingdept )throws Exception{
		
		int count=0;
		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		String query = "select * from annstm where name ='"+name+"'"+
		               " and academic_year = "+academicyear+
		               " and academic_period="+academicperiod+
                       " and fk_stmtypcode='"+fkstmtypcode+"'"+
                       " and nr ='"+nr+"'"+
                       " and din_language = '"+dinlanguage+"'"+
                       " and submitting_dept = "+submittingdept;
		
		List queryList;
        queryList=jdt.queryForList(query);
		     Iterator i=queryList.iterator(); 
			 while(i.hasNext()){
				 ListOrderedMap data = (ListOrderedMap) i.next(); 
				 String acdamicYear = data.get("ACADEMIC_YEAR").toString();
				 
				 count++;
			 }
				System.out.println("count"+count);
		return count;
	}
	
	
	
      public boolean checkingWebid(String name,int academicyear,String academicperiod,String fkstmtypcode,String nr,String dinlanguage,String submittingdept )throws Exception{
		
		boolean check;
		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		String query = " select fk_webid from annstm where name ='"+name+"'"+
		               " and academic_year = "+academicyear+
		               " and academic_period="+academicperiod+
                       " and fk_stmtypcode='"+fkstmtypcode+"'"+
                       " and nr ='"+nr+"'"+
                       " and din_language = '"+dinlanguage+"'"+
                       " and submitting_dept = "+submittingdept;
		  
		  String webid = querySingleValue(query,"fk_webid");
	
		  if(webid.equals("")||webid==null){
			  check =true;
		  }else{
			  check=false;
		  }
		return check;  
	}	
       public boolean updateWebid(String name,int academicyear,String academicperiod,String fkstmtypcode,String nr,String dinlanguage,String submittingdept,int webId )throws Exception{
	              boolean update=false;
	              JdbcTemplate jdt = new JdbcTemplate(getDataSource());
	              String query =  " update annstm  set fk_webid="+webId +
			                  " where name ='"+name+"'"+
                              " and academic_year = "+academicyear+
                              " and academic_period="+academicperiod+
                              " and fk_stmtypcode='"+fkstmtypcode+"'"+
                              " and nr ='"+nr+"'"+
                              " and din_language = '"+dinlanguage+"'"+
                              " and submitting_dept = "+submittingdept;

	           try{     
	
	              jdt.update(query);
	               update=true;
	                 }catch(Exception ex){
		                  throw new Exception("FilemanagerDAO.java: updateWebid"+ex);
	                  }
             return update;
          }

    public ArrayList getAnnstmId(String Subcode)throws Exception{
	
	String sql= " SELECT * FROM WEBSTM "+
                " WHERE REF_NAME = '"+Subcode +"'";
	String temp ="" ;
	  ArrayList ids = new ArrayList();
	JdbcTemplate jdt = new JdbcTemplate(getDataSource());
	List queryList;
	queryList=jdt.queryForList(sql);
	Iterator i=queryList.iterator();
	UtilDetailsForm details = new UtilDetailsForm();
	while(i.hasNext()){
	
	   ListOrderedMap data = (ListOrderedMap) i.next();
	   String startYear = data.get("START_ACADEMIC_YEA").toString();
	   String endYear = data.get("END_ACADEMIC_YEAR").toString();
	   String webId = data.get("WEBID").toString();
	   int webid = Integer.parseInt(webId);
	   
	   String sql1 =   " select * from annstm where fk_webid = "+webid+
	   		           "and ACADEMIC_YEAR= (select max(ACADEMIC_YEAR) from "+ 
	                   "annstm where fk_webid = "+webid+")";
	 
	   List queryList1;
             queryList1=jdt.queryForList(sql1);
  		     Iterator j=queryList1.iterator(); 
  		     
  		
  		  boolean l = queryList1.isEmpty();
  		   System.out.println("list is epmty===="+l);
  		  if(l==true){
  			  temp="norecord";
  			  ids.add(webId);
  		
  			 
  		  }
	}
	
	return ids;
}	
	
}