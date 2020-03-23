package za.ac.unisa.lms.tools.cronjobs.tpuModel.tpuModelImpl;

import java.util.Iterator;
import java.util.List;

import za.ac.unisa.lms.tools.cronjobs.forms.model.Province;
import za.ac.unisa.lms.tools.cronjobs.forms.model.Supervisor;
import za.ac.unisa.lms.tools.cronjobs.forms.util.DateUtil;
import za.ac.unisa.lms.tools.cronjobs.dao.tpuDAO.SupervisorDAO;

public class SupervisorImpl {
	
	SupervisorDAO dao;
	public SupervisorImpl(){
		  dao=new SupervisorDAO();
	}
	
	 public List getSupervProvList(int supervisorCode)throws Exception {
		  	        return dao.getSupervProvList(supervisorCode);
	 }
	 public   int getStudentsAllocated(int supervisorCode,int year)throws Exception {
		          return dao.getStudentsAllocated(supervisorCode, year);
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
	 public   String getSupervisorEmail(int supervisorCode)throws Exception {
               return dao.getSupervisorEmail(supervisorCode);
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
	 public Supervisor getSupervisor(int code)throws Exception {
		                 Supervisor supervisor= dao.getSupervisor(code);
		                 supervisor.setName(supervisor.getTitle()+" "+supervisor.getInitials()+" "+supervisor.getSurname());
		                 DateUtil dateutil=new DateUtil();
		                 int  totStudentsAllocToSupevisor=dao.getStudentsAllocated(code,dateutil.yearInt());
                         supervisor.setStudentsAllocated(totStudentsAllocToSupevisor);
                         supervisor.setEmailAddress(supervisor.getEmailAddress(code));
                         supervisor.setProvCodesList(dao.getSupervProvList(code));
                   return supervisor;
    }
	 
}
