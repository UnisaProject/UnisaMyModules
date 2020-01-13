package za.ac.unisa.lms.tools.cronjobs.forms.model;
import za.ac.unisa.lms.tools.cronjobs.tpuModel.tpuModelImpl.CoordinatorImpl;

import java.util.List;
import java.util.ArrayList;

public class Coordinator extends Personnel{
             
               private String sadecInt;//it indicates if coordinator is from sadec or international country
               private List supervisorsList;
               CoordinatorImpl coordinatorImpl;
               public Coordinator(){
            	       this.coordinatorImpl=new CoordinatorImpl(this);
            	       supervisorsList=new ArrayList();
        	   }
               public String isCoordinator( String  networkCode)throws Exception{
                               return coordinatorImpl.isCoordinator(networkCode);
               }
    		   public void setSadecInt(String sadecInt){
           		   this.sadecInt=sadecInt;
           	   }
           	   public String getSadecInt(){
           		   return sadecInt;
           	   }
           	   public List getSupervisorsList(){
           		   return supervisorsList;
           	   }
           	   public void setSupervisorsList(List supervisorsList){
           		   this.supervisorsList=supervisorsList;
           	   }
           	   public List getCoordinatorList() throws Exception{
           		           return  coordinatorImpl.getCoordinatorList();
               }
               public  String checkCoordinatorForProv(String supervisorCountry,int supervisorProv) throws  Exception{
           		                     return coordinatorImpl.checkCoordinatorForProv(supervisorCountry, supervisorProv);
           	   }
               public String  getPersnoForProvCoordinator(int provinceCode)throws Exception{
	                      return coordinatorImpl.getCoordinatorForProvince(provinceCode);
               }
               public Coordinator getCoordinator(int persno) throws Exception{
	                     return coordinatorImpl.getCoordinator(persno);
               }
               public static int getPosOfCoordinatorInList(int personnelNum,List coordinatorList){
            	        return  CoordinatorImpl.getPosOfCoordinatorInList(personnelNum, coordinatorList);
               }
               public  Coordinator getCoordinatorForProvince(int provinceCode)throws Exception{
                                     String coordinatorPersonnelNum= getPersnoForProvCoordinator(provinceCode);
                                     if( coordinatorPersonnelNum.equals("")){
                                    	 return new Coordinator();
                                     }
                                     return getCoordinator(Integer.parseInt(coordinatorPersonnelNum));
  	                   
               }
               public List<Coordinator> getCoordinatorListForProvince(int provinceCode)throws Exception{
                                            return coordinatorImpl.getCoordinatorListForProvince(provinceCode);
               }
       
              
}