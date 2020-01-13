package za.ac.unisa.lms.tools.cronjobs.dao.tpuDAO;

import java.util.ArrayList;
import java.util.Set;
import java.util.Iterator;
import java.util.List;
import java.util.HashSet;
import org.apache.commons.collections.map.ListOrderedMap;
import za.ac.unisa.lms.tools.cronjobs.forms.model.PlacementListRecord;
import za.ac.unisa.lms.tools.cronjobs.forms.util.DateUtil;
import za.ac.unisa.lms.tools.cronjobs.dao.tpuDAO.databaseUtils;
import za.ac.unisa.lms.tools.cronjobs.dao.tpuDAO.ContactDAO;
import za.ac.unisa.lms.tools.cronjobs.forms.model.Contact;
import za.ac.unisa.lms.tools.cronjobs.forms.model.School;
import za.ac.unisa.lms.tools.cronjobs.forms.model.Student;
public class StudentPlacementDAO{
    
             
	 public List getSupervisorCodeListEmailNotSent() { 
		 try{
		              DateUtil dateutil=new DateUtil();
    	              String sql="select  distinct (mk_supervisor_code) as supCode "+
	                		   " from tpuspl "+
                               " where mk_academic_year=" + dateutil.yearInt()+
                               " and semester_period= 0" +
                               " and mk_supervisor_code <> 283"+
                               " and email_to_sup is null order by mk_supervisor_code"; 
    	              databaseUtils dbutil=new databaseUtils();    
    	              List queryList = dbutil.queryForList(sql,"StudentPlacementDao : Error getting supervisorCodes List, reading TPUSPL / ");
    	              Iterator i=queryList.iterator();
    	              List supCodeList=new ArrayList();
    	              while(i.hasNext()){
    	            	     ListOrderedMap data = (ListOrderedMap) i.next();
    	            	     supCodeList.add(data.get("supCode"));
    	              }
  	                  return supCodeList;
		 }catch(Exception ex){
			 return new ArrayList();
		 }
     }
	public String  getSqlForTotPlacmtEmailSend() throws Exception{
                        DateUtil dateutil=new DateUtil();
                        String sql="select  *"+
                                   " from tpuspl "+
                                   " where mk_academic_year=" + dateutil.yearInt()+
                                   " and semester_period= 0" +
                                   " and mk_supervisor_code <> 283"+
                                   " and email_to_sup is not null and"+
                                   " to_char(email_to_sup,'YYYY-MM-DD')='"+dateutil.dateOnly()+"'";
                       return sql;
     }
	 public int  getTotPlacmtEmailNotSend() throws Exception{
		                  DateUtil dateutil=new DateUtil();
                          String sql="select  *"+
       		                         " from tpuspl "+
                                     " where mk_academic_year=" + dateutil.yearInt()+
                                     " and semester_period= 0" +
                                     " and mk_supervisor_code <> 283"+
                                     " and email_to_sup is null "; 
                          databaseUtils dbutil=new databaseUtils();    
                          List queryList = dbutil.queryForList(sql,"StudentPlacementDao : Error  reading TPUSPL / ");
                          if(queryList==null){
                        	  return 0;
                          }
                          return queryList.size();
    }
	public int  getTotPlacmtEmailSend() throws Exception{
                       String sql=getSqlForTotPlacmtEmailSend();
                       databaseUtils dbutil=new databaseUtils();    
                       List queryList = dbutil.queryForList(sql,"StudentPlacementDao : Error  reading TPUSPL / ");
                       if(queryList==null){
       	                    return 0;
                       }
                       return queryList.size();
    }
	private String  getLocalPlacementListSql(int supervisorCode,int saCode){
		               DateUtil dateutil=new DateUtil();
		               String sql="select a.mk_student_nr as stuNumber,(d.mk_title || ' ' ||  d.initials || ' ' || d.surname ) as stuName,"+
                       " a.mk_school_code as schCode, b.name as schName, a.mk_study_unit_code as module,"+
                       " a.mk_supervisor_code as supCode, (c.surname || ' ' || c.initials || ' ' || c.mk_title) as supName,"+
                      "  to_char(a.start_date,'YYYY/MM/DD') as startDate,to_char(a.end_date,'YYYY/MM/DD') as endDate,"+
                      " a.number_of_weeks as numWeeks,a.evaluation_mark as evalMark,a.mk_academic_year  year, a.semester_period"+ 
                      "  semester,f.eng_description as prov,"+
                      "  a.email_to_sup as dateSent ,e.code as disCode,e.eng_description as disName from tpuspl a,"+ 
                      "  tpusch b, tpusup c, stu d, ldd e, prv f"+
                      " where a.mk_academic_year="+ dateutil.yearInt()+
                      "  and a.semester_period=0"+
                      " and a.mk_school_code=b.code"+
                      " and a.mk_supervisor_code=c.code"+
                      " and a.mk_supervisor_code="+supervisorCode+
                      " and a.mk_student_nr=d.nr"+
                      " and b.mk_prv_code=f.code"+
                      " and b.mk_country_code="+saCode+
                      " and b.mk_district_code=e.code"+
                      " order by prov,e.eng_description,c.surname";
                      
              return sql;
     }
	 private String  getInternationalPlacementListSql(int supervisorCode,int saCode){
         DateUtil dateutil=new DateUtil();
         String sql="select a.mk_student_nr as stuNumber,(d.mk_title || ' ' ||  d.initials || ' ' || d.surname ) as stuName,"+
         " a.mk_school_code as schCode, b.name as schName, a.mk_study_unit_code as module,"+
         " a.mk_supervisor_code as supCode, (c.surname || ' ' || c.initials || ' ' || c.mk_title) as supName,"+
        "  to_char(a.start_date,'YYYY/MM/DD') as startDate,to_char(a.end_date,'YYYY/MM/DD') as endDate,"+
        " a.number_of_weeks as numWeeks,a.evaluation_mark as evalMark,a.mk_academic_year  year, a.semester_period"+ 
        "  semester,e.eng_description,"+
        "  a.email_to_sup as dateSent  from tpuspl a, tpusch b, tpusup c, stu d,lns e"+
        " where a.mk_academic_year="+ dateutil.yearInt()+
        "  and a.semester_period=0"+
        " and a.mk_school_code=b.code"+
        " and a.mk_supervisor_code=c.code"+
        " and a.mk_supervisor_code="+supervisorCode+
        " and a.mk_student_nr=d.nr"+
        " and b.mk_country_code<>"+saCode+
        " and b.mk_country_code=e.code"+
        " order by e.eng_description,c.surname";
        
        return sql;
    }
	public List  getPlacementListForSuperv(int supervisorCode) throws Exception {
    	              int saCode=1015;
    	              String localPlacementsSql=getLocalPlacementListSql(supervisorCode,saCode);
                      String internationalPlacementsSql=getInternationalPlacementListSql(supervisorCode,saCode);
                      List supervisorPlacementList= getLocalPlacementList(localPlacementsSql);	
                      List internationList=getInternationalPlacementList(internationalPlacementsSql);
                      Iterator i=internationList.iterator();
                      while(i.hasNext()){
                    	  supervisorPlacementList.add(i.next());
                      }
                      return supervisorPlacementList;
    }
    private List getLocalPlacementList(String sql) throws Exception{
        databaseUtils dbutil=new databaseUtils();
        List placementList = new ArrayList<PlacementListRecord>();
           	 List queryList = dbutil.queryForList(sql,"StudentPlacementDao : Error getting placementList, reading TPUSPL / ");
    		 Iterator i = queryList.iterator();
    		 ContactDAO contactDAO=new ContactDAO();
    		 Contact contact=new Contact();
    		 School school=new School();
		     Student student=new  Student(); 
             while (i.hasNext()) {
                 ListOrderedMap data = (ListOrderedMap) i.next();
                 PlacementListRecord placement = new PlacementListRecord();
                 setPlacementInfo(placement,data,dbutil);
                 setPlacementContactInfo(placement);
                 setSchoolRegionInfo(placement,data,dbutil);
	             placementList.add(placement);
           }
       
        return placementList;
   }
    private List getInternationalPlacementList(String sql) throws Exception{
                     databaseUtils dbutil=new databaseUtils();
                     List placementList = new ArrayList<PlacementListRecord>();
                     List queryList = dbutil.queryForList(sql,"StudentPlacementDao : Error getting placementList, reading TPUSPL / ");
    		         Iterator i = queryList.iterator();
    		         while (i.hasNext()) {
                           ListOrderedMap data = (ListOrderedMap) i.next();
                           PlacementListRecord placement = new PlacementListRecord();
                           setPlacementInfo(placement,data,dbutil);
                           setSchoolInternationalCountryInfo(placement,data,dbutil);
                           setPlacementContactInfo(placement);
	                       placementList.add(placement);
                     }
                     return placementList;
   }   
    private void setSchoolRegionInfo(PlacementListRecord placement,ListOrderedMap data,databaseUtils dbutil){
    	              placement.setDistrictCode(Short.parseShort(data.get("disCode").toString()));
                      placement.setDistrictDesc(data.get("disName").toString());
                      placement.setProvinceDescr(dbutil.replaceNull(data.get("prov")));
                      placement.setCountryDescr("SOUTH AFRICA");
    }
    private void setSchoolInternationalCountryInfo(PlacementListRecord placement,ListOrderedMap data,databaseUtils dbutil){
                    placement.setCountryDescr(dbutil.replaceNull(data.get("eng_description").toString()));
                   
    }
    private void setPlacementInfo(PlacementListRecord placement,ListOrderedMap data,databaseUtils dbutil){
    	               placement.setStudentName(data.get("stuName").toString());
                       placement.setStudentNumber(Integer.parseInt(data.get("stuNumber").toString()));
                       placement.setSchoolCode(Integer.parseInt(data.get("schCode").toString()));
                       placement.setSchoolDesc(data.get("schName").toString());
                       placement.setSupervisorCode(Integer.parseInt(data.get("supCode").toString()));
                       placement.setSupervisorName(data.get("supName").toString());
                       placement.setModule(data.get("module").toString());
                       placement.setStartDate(data.get("startDate").toString());
                       placement.setEndDate(data.get("endDate").toString());
                       placement.setNumberOfWeeks(dbutil.replaceNull(data.get("numWeeks")));	
                       placement.setEvaluationMark(dbutil.replaceNull(data.get("evalMark")));	
                       placement.setEmailSendDate(dbutil.replaceNull(data.get("dateSent")));
    }
    private void setPlacementContactInfo(PlacementListRecord placement )throws Exception{
    	             ContactDAO contactDAO=new ContactDAO();
		             Contact contact=new Contact();
		             School school=new School();
	                 Student student=new  Student();
	                 placement.setStudentContactNumber(student.getStudentContactNumber(placement.getStudentNumber()));
                     placement.setSchoolContactNumber(school.getSchoolContactNumber(placement.getSchoolCode()));
    }
     
    public void updateEmailToSupField(int supervisorCode) throws Exception{
                    DateUtil dateutil=new DateUtil(); 
                    String sql="update  tpuspl set  email_to_sup=sysdate where mk_academic_year="+dateutil.yearInt()+" and semester_period=0"+
		                       " and email_to_sup is null   and mk_supervisor_code <> 283   and mk_supervisor_code="+supervisorCode;
                    databaseUtils dbutil=new databaseUtils();
                    dbutil.update(sql,"StudentPlacementDAO: Error updating tpuspl ");
   }
   
}
