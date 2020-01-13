package za.ac.unisa.lms.tools.signoff.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.collections.map.ListOrderedMap;
import org.springframework.jdbc.core.JdbcTemplate;

import za.ac.unisa.lms.db.StudentSystemDAO;
import za.ac.unisa.lms.tools.signoff.forms.SignoffForm;
import za.ac.unisa.lms.tools.signoff.forms.StaffRecord;
public class SignoffDAO extends StudentSystemDAO {
	
	public ArrayList getStructureName()
	 {
	 			  String sql= "SELECT Code, SCHOOL_CODE, COLLEGE_CODE, Eng_Description FROM DPT WHERE IN_USE_FLAG = 'Y' ORDER BY Eng_Description";
	 			  
	 			 ArrayList results = new ArrayList();
				  JdbcTemplate jdt = new JdbcTemplate(getDataSource());
				  List queryList;
				  queryList = jdt.queryForList(sql);
				  Iterator i = queryList .iterator();
				  while(i.hasNext()) {
					ListOrderedMap data = (ListOrderedMap) i.next();
			
					String code = data.get("Code").toString()+"."+data.get("SCHOOL_CODE").toString()+"."+data.get("COLLEGE_CODE").toString();
					String description = data.get("Eng_Description").toString();
					results.add(new org.apache.struts.util.LabelValueBean(description,code));
				
									
				  }
		return results;
	}
	
    /*
	 * get Dean, Director of school, Chair of department and deputy dean's personel number's
	 */
	   public ArrayList getPersonelNumbers(int code)
	    {
	                   /*    String sql= " select NVL(DPT.head_of_dept,0) AS COD, NVL(COLLEG.DEAN,0) AS DEAN, NVL(SCHOOL.HEAD_OF_SCHOOL,0) AS HOS,"+
	                                   " Nvl(COLLEG.DEPUTY_DEAN1,0) AS SID1," +
	                                   " colleg.code as clgCode ,school.code as schCode"+
	                                   " FROM  DPT, COLLEG, SCHOOL"+
	                                   " WHERE DPT.IN_USE_FLAG='Y'"+
	                                   " AND DPT.CODE ="+code+
	                                   " AND DPT.COLLEGE_CODE = COLLEG.CODE"+
	                                   " AND DPT.SCHOOL_CODE = SCHOOL.CODE"+
	                                   " AND SCHOOL.COLLEGE_CODE = COLLEG.CODE"+
	                                   " AND NVL(SCHOOL.IN_USE_FLAG,'Y') = 'Y'";*/
		   
		   String sql = "select NVL(DPT.head_of_dept,0) AS COD, NVL(COLLEG.DEAN,0) AS DEAN, NVL(COLLEG.DEPUTY_DEAN1,0) AS SID1, colleg.code as clgCode"+ 
		                " FROM  DPT, COLLEG"+
		                " WHERE DPT.IN_USE_FLAG='Y'"+
		                " AND DPT.CODE ="+code+
		                " AND DPT.COLLEGE_CODE = COLLEG.CODE";
		   System.out.println("Sql: "+sql);
	                       
	                       
	                       ArrayList results = new ArrayList();
	                       JdbcTemplate jdt = new JdbcTemplate(getDataSource());
	                       List queryList;
	                       queryList = jdt.queryForList(sql);
	                       Iterator i = queryList .iterator();
	                       while(i.hasNext()) {
	                    	   ListOrderedMap data = (ListOrderedMap) i.next();
	                    	   String cod= data.get("COD").toString();
	                    	   String dean= data.get("DEAN").toString();
	                    	   String dydean= data.get("SID1").toString();
	                    	   String clgCode= data.get("clgCode").toString();
	                    	   results.add(cod);
	                    	   results.add(dean);
	                    	   results.add(dydean);
	                    	   results.add(clgCode);
	                       }
	            return results;
	   }
	   
	   /*
		 * get Dean, Director of school, Chair of department and deputy dean's personel number's
		 */
		   public ArrayList getPersonelNumbers1(int code, int colCode)
		    {
			   String schCode = "";
		               		   
			   String sql1 = "select NVL(DPT.head_of_dept,0) AS COD, school_code as schCode"+
               				" FROM  DPT"+
               				" WHERE DPT.IN_USE_FLAG='Y'"+
               				" AND DPT.CODE ="+code;
			   

			   /** replaced by Sonette Yzelle - split select into two selects
			    * String sql = "select NVL(DPT.head_of_dept,0) AS COD,  NVL(SCHOOL.HEAD_OF_SCHOOL,0) AS HOS,school.code as schCode"+
			    
			                " FROM  DPT, SCHOOL"+
			                " WHERE DPT.IN_USE_FLAG='Y'"+
			                " AND DPT.CODE ="+code+
			                " and dpt.code = school.mk_department_code"+
			                " and school.college_code = "+colCode+
			                " AND DPT.SCHOOL_CODE = SCHOOL.CODE"+
			                " AND NVL(SCHOOL.IN_USE_FLAG,'Y') = 'Y'";*/
			System.out.println("sql1--   "+sql1);
			
			ArrayList results = new ArrayList();
		    JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		    List queryList;
		    queryList = jdt.queryForList(sql1);
		    Iterator i = queryList .iterator();
		    while(i.hasNext()) {
		    	ListOrderedMap data = (ListOrderedMap) i.next();
		    	schCode= data.get("schCode").toString();
	
		    }
		                       
		    String sql2 = "SELECT NVL(SCHOOL.HEAD_OF_SCHOOL,0) AS HOS"+
						 " FROM   SCHOOL" +
				         " WHERE school.college_code = "+colCode+
				         " AND   CODE = "+schCode+
				         " AND   NVL(SCHOOL.IN_USE_FLAG,'Y') = 'Y'";

		    System.out.println("sql2--   "+sql2);
		    ArrayList results2 = new ArrayList();
		    JdbcTemplate jdt2 = new JdbcTemplate(getDataSource());
		    List queryList2;
		    queryList = jdt2.queryForList(sql2);
		    Iterator j = queryList.iterator();
		    while(j.hasNext()) {
		    	ListOrderedMap data = (ListOrderedMap) j.next();
		    	String dos= data.get("HOS").toString();
		    	results.add(dos);
		    	results.add(schCode);
		    }
		    
		    return results;
		 }
	   
	   public ArrayList getStandinCOD(int code)
	    {
	                       String sql= " select NOVELL_USER_CODE,PERSONNEL_NO FROM USRDPT "+
	                    	           " where MK_DEPARTMENT_CODE="+code+
	                                   " and type = 'DPT'";
	                       ArrayList results = new ArrayList();
	                       JdbcTemplate jdt = new JdbcTemplate(getDataSource());
	                       List queryList;
	                       queryList = jdt.queryForList(sql);
	                       Iterator i = queryList .iterator();
	                       while(i.hasNext()) {
	                    	   ListOrderedMap data = (ListOrderedMap) i.next();
	                    	   SignoffDetails signoffDetails = new SignoffDetails();
	                    	   String perno= data.get("PERSONNEL_NO").toString();
	                    	   signoffDetails.setStandinCod(perno);
	                    	   results.add(signoffDetails);
	                       }
	            return results;
	   }
	   
	   public ArrayList getStandinDydean(int code)
	    {
	                       String sql= " select NOVELL_USER_CODE,PERSONNEL_NO FROM USRDPT "+
	                    	           " where COLLEGE_CODE="+code+
	                                   " and type = 'COLLEGE'"+
	                                   " order by display_order";
	                       ArrayList results = new ArrayList();
	                       JdbcTemplate jdt = new JdbcTemplate(getDataSource());
	                       List queryList;
	                       queryList = jdt.queryForList(sql);
	                       Iterator i = queryList .iterator();
	                       while(i.hasNext()) {
	                    	   ListOrderedMap data = (ListOrderedMap) i.next();
	                    	   SignoffDetails signoffDetails = new SignoffDetails();
	                    	   String perno= data.get("PERSONNEL_NO").toString();
	                    	   signoffDetails.setStandinDydean(perno);
	                    	   results.add(signoffDetails);
	                       }
	            return results;
	   }
	   
	   public ArrayList getStandinSOD(int code, int clg_code)
	    {
	                       String sql= " select NOVELL_USER_CODE,PERSONNEL_NO FROM USRDPT "+
	                    	           " where SCHOOL_CODE="+code+
	                    	           " and   college_code ="+clg_code+
	                                   " and type = 'SCHOOL'"+
	                                   " Order by display_order";
	                       
	                       ArrayList results = new ArrayList();
	                       JdbcTemplate jdt = new JdbcTemplate(getDataSource());
	                       List queryList;
	                       queryList = jdt.queryForList(sql);
	                       Iterator i = queryList .iterator();
	                       while(i.hasNext()) {
	                    	   ListOrderedMap data = (ListOrderedMap) i.next();
	                    	   SignoffDetails signoffDetails = new SignoffDetails();
	                    	   String perno= data.get("PERSONNEL_NO").toString();
	                    	   signoffDetails.setStandinSch(perno);
	                    	   results.add(signoffDetails);
	                       }
	            return results;
	   }
	   
	/*
	 * check staff is active or not
	 */
	
	   public String getName(String perno){
		   
				
				String select = "SELECT USER_CODE_OWNER FROM USR WHERE PERSONNEL_NO="+"'"+perno+"'";
				
		            JdbcTemplate jdt = new JdbcTemplate(super.getDataSource());
		            String name= querySingleValue(select,"USER_CODE_OWNER");
		            return name;
			
	   }
	   public String getStatus(String perno) throws ParseException{
		   String status;
			String select = "SELECT  to_char(APPMNT.to_date,'YYYY-MM-DD') as TO_DATE"+ 
                            " FROM USR, APPMNT WHERE USR.PERSONNEL_NO="+"'"+perno+"'"+
                            " and usr.nr = appmnt.nr";
                
               
			    GregorianCalendar calCurrent = new GregorianCalendar();
	            JdbcTemplate jdt = new JdbcTemplate(super.getDataSource());
	            String resignDate= querySingleValue(select,"TO_DATE");
	            SimpleDateFormat format = new SimpleDateFormat("YYYY-MM-DD");
	            Date date = format.parse(resignDate);
	            GregorianCalendar resignDate1 = new GregorianCalendar();
	            resignDate1.setTime(date);
	             int result = 0;
	    		 if (resignDate!= null) {
	    		    result = resignDate1.compareTo(calCurrent);
	    		 }
	    		 if (result <= -1) {
	    		    status = "Inactive";
	    		 } else {
	    		   status = "Active";
	    		 }
	            return status;
		
  }
	   
	   /*
		* delete record from USRDPT
	    */
   public void deleteUsrdpt(int code, int sch_code, int clg_code, String type, String personnel_no) throws Exception{

	String query= "Delete from USRDPT where ";
	              if(type.equals("DPT")){
	            	  query=query+" mk_department_code ="+code;
                  }else if(type.equals("SCHOOL")){
                	  query= query+" SCHOOL_CODE="+sch_code+
                	  " and COLLEGE_CODE="+clg_code;
                  }else if(type.equals("COLLEGE")){
                	  query= query+" COLLEGE_CODE="+clg_code;
                  }query= query+" and   type="+"'"+type+"'"+
                  " and   personnel_no ="+personnel_no;
                  
                  JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		try{
		 jdt.update(query);
		}catch(Exception ex){
	    throw new Exception("SignoffDAO: deleteUsrdpt"+ex);
		}				
	}
	   
   /*
    * update order
    */
    public void updateUserdpt(String personnelNr, int orderNr, String type, String dptCode) throws Exception{

          String query= "Update usrdpt set display_order ="+orderNr+
                        " where personnel_no ="+"'"+personnelNr+"'"+
                        " and mk_department_code ="+dptCode+
                        " and type ="+"'"+type+"'";

          
             JdbcTemplate jdt = new JdbcTemplate(getDataSource());
	         try{
	           jdt.update(query);
        	}catch(Exception ex){
            throw new Exception("SignoffDAO: updateUserdpt"+ex);
	   }				
     }
   
   public ArrayList getCod(){
                      String sql="SELECT Code, Eng_Description, COLLEGE_CODE, SCHOOL_CODE FROM DPT WHERE IN_USE_FLAG = 'Y' ORDER BY Eng_Description";
                      ArrayList results = new ArrayList();
                      JdbcTemplate jdt = new JdbcTemplate(getDataSource());
                      List queryList;
                      queryList = jdt.queryForList(sql);
                      Iterator i = queryList .iterator();
                      while(i.hasNext()) {
                          ListOrderedMap data = (ListOrderedMap) i.next();
                          String code= data.get("Code").toString()+"."+data.get("SCHOOL_CODE").toString()+"."+data.get("COLLEGE_CODE").toString();;
                          String description   = data.get("Eng_Description").toString();                           
                          results.add(new org.apache.struts.util.LabelValueBean(description,code));                     
                      }
        return results;
  }
   public ArrayList getSch(){
       String sql="SELECT Code, COLLEGE_CODE, Eng_Description FROM SCHOOL WHERE IN_USE_FLAG = 'Y' ORDER BY Eng_Description";
       ArrayList results = new ArrayList();
       JdbcTemplate jdt = new JdbcTemplate(getDataSource());
       List queryList;
       queryList = jdt.queryForList(sql);
       Iterator i = queryList .iterator();
       while(i.hasNext()) {
           ListOrderedMap data = (ListOrderedMap) i.next();
           String code= data.get("Code").toString()+"."+data.get("COLLEGE_CODE").toString();
           String description   = data.get("Eng_Description").toString();                           
           results.add(new org.apache.struts.util.LabelValueBean(description,code));                     
       }
   return results;
   }
   
   public ArrayList getDyDean(){
       String sql="SELECT Code, Eng_Description FROM COLLEG ORDER BY Eng_Description";
       ArrayList results = new ArrayList();
       JdbcTemplate jdt = new JdbcTemplate(getDataSource());
       List queryList;
       queryList = jdt.queryForList(sql);
       Iterator i = queryList .iterator();
       while(i.hasNext()) {
           ListOrderedMap data = (ListOrderedMap) i.next();
           String code= data.get("Code").toString();
           String description   = data.get("Eng_Description").toString();                           
           results.add(new org.apache.struts.util.LabelValueBean(description,code));                     
       }
    return results;
   }

   public ArrayList getOrderList(int dptCode, String type){
       String sql="Select novell_user_code, personnel_no from USRDPT"+
                  " Where mk_department_code ="+dptCode+
                  " And    type ="+"'"+type+"'"+
                  " Order by display_order";
       ArrayList results = new ArrayList();
       JdbcTemplate jdt = new JdbcTemplate(getDataSource());
       List queryList;
       queryList = jdt.queryForList(sql);
       Iterator i = queryList .iterator();
       while(i.hasNext()) {
           ListOrderedMap data = (ListOrderedMap) i.next();
           StaffRecord staffRecord= new StaffRecord();
           String code= data.get("novell_user_code").toString();
           String perNum   = data.get("personnel_no").toString();  
           staffRecord.setNovelUsrCode(code);
           staffRecord.setPersonelNum(perNum);
           results.add(staffRecord);
       }
    return results;
   }
     public ArrayList getOderDisplyDptList(String type){
    	 
    	 String sql="";
    	 ArrayList results = new ArrayList();
        if(type.equals("clg")){
             sql="SELECT Code, Eng_Description FROM COLLEG ORDER BY Eng_Description";
        }else if(type.equals("sch")){
        	 sql="SELECT Code, Eng_Description FROM SCHOOL WHERE IN_USE_FLAG = 'Y' ORDER BY Eng_Description";
        }else if(type.equals("dpt")){
        	 sql="SELECT Code, Eng_Description FROM DPT WHERE IN_USE_FLAG = 'Y' ORDER BY Eng_Description";
        }else if(type.equals("-1")){
        	return results;
        }
        
       JdbcTemplate jdt = new JdbcTemplate(getDataSource());
       List queryList;
       queryList = jdt.queryForList(sql);
       Iterator i = queryList .iterator();
       while(i.hasNext()) {
           ListOrderedMap data = (ListOrderedMap) i.next();
           String code= data.get("Code").toString();
           String description   = data.get("Eng_Description").toString();                           
           results.add(new org.apache.struts.util.LabelValueBean(description,code));                     
       }
    return results;
     }
   
     public String getDepartmentName(String type, int code, int colCode){
    	 
         String sql="SELECT Eng_Description FROM ";
         if(type.equals("DPT")){
        	 sql= sql+" DPT WHERE IN_USE_FLAG = 'Y' and code="+code;
         }else if(type.equals("SCHOOL")){
        	 sql= sql+" SCHOOL WHERE IN_USE_FLAG = 'Y' and code="+code+
        	          " and college_code="+colCode;
         }else if(type.equals("COLLEGE")){
        	 sql= sql+" COLLEG WHERE code="+code;
         }
         String results = this.querySingleValue(sql,"Eng_Description");
        
    return results;
    }
     
	public boolean isStaffNumber(String networkCode, String perNum, int dptCode, int schCode, int colCode, String type) throws Exception{
		
	
		
		String query = "SELECT count(*) as COUNT from usrdpt where novell_user_code ="+"'"+networkCode+"'"+
                       " and personnel_no ="+perNum;
		String sql="";
		
		if(type.equals("DPT")){
			sql = "select count(*) as count from dpt where  HEAD_OF_DEPT="+perNum+
			      "and code="+dptCode;
		  query=query+ " and mk_department_code ="+dptCode+
                       " and type ="+"'"+type+"'";
		}else if(type.equals("SCHOOL")){
			sql ="select count(*) as count from school where HEAD_OF_SCHOOL="+perNum+
			     " and code="+schCode+
			     " and college_code="+colCode;
	     query=query+" and college_code ="+colCode+
                     " and school_code ="+schCode+
                     " and type ="+"'"+type+"'";
			
		}else if(type.equals("COLLEGE")){
			sql ="select count(*) as count from colleg where DEAN="+perNum+
			     " and code="+colCode;
			  query=query+" and mk_department_code =0"+
                          " and school_code =0"+
        	              " and college_code ="+colCode+
                          " and type ="+"'"+type+"'";
			//Removed by Sonette 12 Sept " or DEPUTY_DEAN1="+perNum+
		}
		

        	try {
        	    String recCount = this.querySingleValue(query,"COUNT");
        	    String recCount1 = this.querySingleValue(sql,"COUNT");
        	    int counter =  Integer.parseInt(recCount); 
        	    int counter1 =  Integer.parseInt(recCount1); 
        		if(counter > 0){
        			return true;
        		}else if(counter1 > 0) {
        			return true;
        		} else{
        			return false;
        		}
        		
        		
        	} catch (Exception ex) {
        		throw new Exception("staff / "+ ex,ex);
        		
        	}
        	
		
    }
   
	public void setUserDpt(String networkCode, String perNum, int dptCode, int schCode, int colCode, String type) throws Exception{
		
		  String query= "Insert into USRDPT(novell_user_code, personnel_no, mk_department_code, COLLEGE_CODE, SCHOOL_CODE, type) " +
		  		        " values (?,?,?,?,?,?)";

	    
		  
	         JdbcTemplate jdt = new JdbcTemplate(getDataSource());

	  		try{
			
    		 jdt.update(query,new Object[] {networkCode, perNum, dptCode, colCode, schCode, type});
	  		
			  }catch(Exception ex){
				throw new Exception("signoffdao: setusrdpt"+ex);
			  }
							
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
		
}
			

