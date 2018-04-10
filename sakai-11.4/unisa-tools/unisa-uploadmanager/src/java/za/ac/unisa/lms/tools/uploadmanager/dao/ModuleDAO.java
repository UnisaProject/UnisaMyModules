package za.ac.unisa.lms.tools.uploadmanager.dao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.map.ListOrderedMap;
import org.springframework.jdbc.core.JdbcTemplate;

import za.ac.unisa.lms.db.StudentSystemDAO;

public class ModuleDAO extends StudentSystemDAO{
	  public ArrayList getAllModuleCodes(String part)
	    {
		   String coursePart="";
		   ArrayList results = new ArrayList();
		   try{
		   coursePart = part.toUpperCase();
		   }catch(NullPointerException e){}
		   if (coursePart.length()==0){
			   return results;
		   }else{
		   
	                       String sql= "select code from sun where in_use_flag = 'Y' " +
	                       		       " and code like "+"'"+coursePart+"%"+"'"+
	                       		       " order by code";
	                     
	                       JdbcTemplate jdt = new JdbcTemplate(getDataSource());
	                       List queryList;
	                       queryList = jdt.queryForList(sql);
	                       Iterator i = queryList .iterator();
	                       while(i.hasNext()) {
	                           ListOrderedMap data = (ListOrderedMap) i.next();
	                           String code= data.get("code").toString();
	                                         
	                           results.add(new org.apache.struts.util.LabelValueBean(code,code));
	                                               
	                       }
		   
	         return results;
		   }
	   }
	  public 	List  getModules(String networkCode)  {
          GregorianCalendar calCurrent = new GregorianCalendar();
          int currYear=calCurrent.get(Calendar.YEAR);
          int threeYearPrevious = currYear-3;
         String sql=    "Select mk_study_unit_code  as module  from usrsun a, staff b  where a.system_type='L'  AND  "+
                         "a.mk_academic_year > "+threeYearPrevious +
                          " and UPPER(b.novell_user_id)=UPPER(a.novell_user_id) and UPPER(b.novell_user_id)='"+networkCode.toUpperCase()+"'";
         /*         
      	String sql = "select distinct mk_study_unit_code from usrsun "+
      			"where system_type = 'L' "+
      			"and novell_user_id ='"+userID.toUpperCase()+"' "+
      			"order by mk_study_unit_code";*/
      	
      /*	String sql = "select distinct mk_study_unit_code from usrsun where "+
      			"system_type = 'L' and novell_user_id = '"+networkCode.toUpperCase()+"'"+
      			"and MK_ACADEMIC_YEAR > ("+threeYearPrevious+") order by mk_study_unit_code";
      	
     */     List moduleList=new ArrayList();
          try{
          JdbcTemplate jdt = new JdbcTemplate(getDataSource());
          
          List queryList = jdt.queryForList(sql);
          databaseUtils dbutil=new databaseUtils();
          for (int i=0; i<queryList.size();i++){
                ListOrderedMap data = (ListOrderedMap) queryList.get(i);
                moduleList.add(dbutil.replaceNull(data.get("module")));
          }
          }catch(Exception ex){
        	  
          }
          
          Set<String> hs = new HashSet<String>();
          hs.addAll(moduleList);
          moduleList.clear();
          moduleList.addAll(hs);
          Collections.sort(moduleList);
          return moduleList;
}

}
