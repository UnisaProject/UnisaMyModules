package za.ac.unisa.lms.tools.cronjobs.dao.tpuDAO;

import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import  org.apache.commons.collections.map.ListOrderedMap;
import za.ac.unisa.lms.tools.cronjobs.forms.model.Supervisor;
public class SupervisorDAO  {
             
	                databaseUtils dbutil;
                    DistrictDAO   districtDAO;
                    CountryDAO    countryDAO;
                     
            	    public SupervisorDAO(){
                              dbutil=new databaseUtils();
                              districtDAO=new DistrictDAO();
                              countryDAO=new CountryDAO();
                            
                    } 

     public List getSupervProvList(int supervisorCode) throws Exception{
    	          String sql= "select distinct  b.mk_prv_code as supProvCode" +
                              " from tpusup a,tpusar b, lns c" +
                              " where a.code = b.mk_superv_code" + 
                              " and a.mk_country_code = c.code"+
                              " and b.mk_superv_code="+supervisorCode;
    	           List provList = new ArrayList();
                   String errorMessage="SupervisorDAO:Error getting province list for supervisor: tables tpusup,tpusarc";
                   List queryList = dbutil.queryForList(sql,errorMessage);
                   for (int i=0; i<queryList.size();i++){
	                      ListOrderedMap data = (ListOrderedMap) queryList.get(i);
           	              String provinceCode=dbutil.replaceNull(data.get("supProvCode").toString());
           	              provList.add(provinceCode);
                   }
                   return provList;
    	 
     }
     public String getSupervProvDesc(int supervisorCode) throws Exception{
                      String sql= "select  b.eng_description as supProvDesc" +
                                  " from tpusup a,tpusar b, prv c" +
                                  " where a.code = b.mk_superv_code" + 
                                  " and b.mk_prv_code = c.code"+
                                  " and b.mk_superv_code="+supervisorCode;
          
                      String errorMessage="SupervisorDAO:Error getting province description: tables tpusup,tpusar,prv";
                      List queryList = dbutil.queryForList(sql,errorMessage);
                      String provinceCode="";
                      Iterator i=queryList.iterator();
                      while(i.hasNext()){
                            ListOrderedMap data = (ListOrderedMap) i.next();
  	                        provinceCode=dbutil.replaceNull(data.get("supProvDesc").toString());
  	                  }
                    return provinceCode;
     }
    
    
     public   String getSupervisorEmail(int supervisorCode)throws Exception {
                        String query="Select  adrph.email_address as email  from adrph,tpusup where adrph.reference_no="+
                                     " tpusup.code and tpusup.code="+supervisorCode+" and fk_adrcatcode=231";
                        String errorMsg="SupervisorDAO : Error accessing adrph,tpusup";
                        return dbutil.querySingleValue(query,"email",errorMsg);
     }
      public   int getStudentsAllocated(int supervisorCode,int year)throws Exception {
                        String query="Select  count(distinct mk_student_nr) as totalAllocated  from tpuspl where mk_supervisor_code="+
                                     supervisorCode+" and semester_period=0  and mk_academic_year="+year;
                        String errorMsg="SupervisorDAO : Error accessing tpuspl";
                        String totAllocated=dbutil.querySingleValue(query,"totalAllocated",errorMsg);
                        if(totAllocated.equals("")){
        	                  return 0;
                        }else{
        	                 return Integer.parseInt(totAllocated);
                        }
     }
     public   int getStudentsAllocated(int supervisorCode,int year,int semester)throws Exception {
                       String query="Select  count(distinct mk_student_nr) as totalAllocated  from tpuspl where mk_supervisor_code="+
                       supervisorCode+" and semester_period="+semester+"  and mk_academic_year="+year;
                       String errorMsg="SupervisorDAO : Error accessing tpuspl";
                       String totAllocated=dbutil.querySingleValue(query,"totalAllocated",errorMsg);
                       if(totAllocated.equals("")){
                            return 0;
                       }else{
                    	   return Integer.parseInt(totAllocated);
                       }
     }
     public Supervisor getSupervisor(Integer code) throws Exception {

                          Supervisor supervisor = new Supervisor();
                          String sql = "select b.code,b.mk_title,b.initials,b.surname,a.email_address,d.eng_description as supProvDesc,d.code as supProvCode"+
                                       " from adrph a,tpusup b,tpusar c,prv d " +
                                       " where  reference_no=b.code and  b.code= c.mk_superv_code and  c.mk_prv_code=d.code"+
                                       " and b.code=" + code+" and a.fk_adrcatcode=231";
                          String errorMsg="SupervisorDAO : Error getting supervisor details from tables: adrph ,tpusup ,tpusar ,prv ";         
                          List queryList = dbutil.queryForList(sql,errorMsg);
                          Iterator i = queryList.iterator();
                          while (i.hasNext()) {
                                  ListOrderedMap data = (ListOrderedMap) i.next();
                                  supervisor.setCode(Integer.parseInt(data.get("code").toString()));
                                  supervisor.setTitle(dbutil.replaceNull(data.get("mk_title")));
                                  supervisor.setInitials(dbutil.replaceNull(data.get("initials")));
                                  supervisor.setSurname(dbutil.replaceNull(data.get("surname")));
                                  supervisor.setEmailAddress(getSupervisorEmail(code));
                                  break;
                          }
                       return supervisor;
     }
                      
                     
}
