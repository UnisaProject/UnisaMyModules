package za.ac.unisa.lms.tools.tpustudentplacement.model.modelImpl;
import java.util.Iterator;
import java.util.List;
import za.ac.unisa.lms.tools.tpustudentplacement.utils.DateUtil;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.Province;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.Supervisor;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.SupervisorListRecord;
import za.ac.unisa.lms.tools.tpustudentplacement.dao.SupervisorDAO;
public class SupervisorImpl {
	
	SupervisorDAO dao;
	public SupervisorImpl(){
		  dao=new SupervisorDAO();
	}
	 public Supervisor getSupervisor(Integer code) throws Exception {
		              return dao.getSupervisor(code);
	 }
	 public List getSupervProvList(int supervisorCode){
		  	        return dao.getSupervProvList(supervisorCode);
	 }
	 public   String getStudentsAllocated(int supervisorCode,int year)throws Exception {
		          return dao.getStudentsAllocated(supervisorCode, year);
	 }
	 public String getSurpervisorName(int supervisorCode)throws Exception{
                return dao.getSupervisorName(supervisorCode);
     }
	 public   String getSupervisorEmail(int supervisorCode)throws Exception {
               return dao.getSupervisorEmail(supervisorCode);
     }
	 public String supervisorProvince(List provinces,int supervisorCode){
                     String provinceStr="";
                     if((provinces==null)||(provinces.isEmpty())){
                           return "";
                     }else{
                             Iterator i= provinces.iterator();
                             while (i.hasNext()){
    	                           Province  prov=(Province)i.next();
    	                           provinceStr+=prov.getDescription();
    	                           provinceStr+=(",");
                             }
                             provinceStr=provinceStr.substring(0,(provinceStr.length()-1));
                    }
                    return provinceStr;
     }
     public static int getPosOfSupevisorInList(int supervisorCode,List supervisorList){
                   int pos=-1;
                   if(supervisorList==null){
	                    return pos;
                   }
                   for(int x=0;x<supervisorList.size();x++){
	                    Supervisor  supervisor =(Supervisor)supervisorList.get(x);
                       if(supervisor.getCode()==supervisorCode){
     	                      pos=x; 
    	                      break;
                       }
                   } 
                   return pos;
    }
    public Supervisor getSup(int code)throws Exception {
              Supervisor supervisor= dao.getSupervisor(code);
              DateUtil dateutil=new DateUtil();
              String  totStudentsAllocToSupevisor=dao.getStudentsAllocated(code,dateutil.yearInt());
              supervisor.setStudentsAllocated( totStudentsAllocToSupevisor);
              supervisor.setEmailAddress(supervisor.getEmailAddress(code));
              supervisor.setProvCodesList(dao.getSupervProvList(code));
        return supervisor;
    }
   public String  getStudentsAllocated(int code)throws Exception {
                       DateUtil dateutil=new DateUtil();
                       return  dao.getStudentsAllocated(code,dateutil.yearInt());
    }
    
    public List getSupervisorList(String country,Short province,Short district,String filter,String contractStatus,int timeLimit) throws Exception {
              return dao.getSupervisorList(country,province,district,filter,contractStatus,timeLimit);
    }
    public List getSupervisorList(String country,Short province) throws Exception {
                  return dao.getSupervisorList(country,province);
    }   
    public boolean isExpiredSupervisor(SupervisorListRecord supervisor){
        boolean expired=false;
        SupervisorDAO  dao=new SupervisorDAO();
        if(supervisor.getContract().equals("Y")){
                if(dao.isExpiredSupervisor(supervisor)||dao.isExpiringWithinTimeFrame(supervisor,30)){
              	  expired=true;
                }
	    }
	    	  
        return expired;
    }
    public String getStudentsAllocated(int code,int acadYear, int semester)throws Exception {
    	                return    dao.getStudentsAllocated(code,acadYear, semester);
    }
    public String getStudentsAllowed(int code,int acadYear)throws Exception {
                          return    dao.getStudentsAllowed(code, acadYear);
    }
    
}  
