package za.ac.unisa.lms.tools.cronjobs.tpuModel.tpuModelImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import za.ac.unisa.lms.tools.cronjobs.dao.tpuDAO.CoordinatorDAO;
import za.ac.unisa.lms.tools.cronjobs.forms.model.Coordinator;
import za.ac.unisa.lms.tools.cronjobs.forms.model.Supervisor;
import za.ac.unisa.lms.tools.cronjobs.forms.util.PlacementUtilities;

public class CoordinatorImpl {
	       
	         CoordinatorDAO dao;
	         Coordinator coordinator;
	         public CoordinatorImpl(Coordinator coordinator){
	        	       dao= new CoordinatorDAO();
	        	       this.coordinator=coordinator;
	         }
	        
	         public String getPersonnelNumber(String networkCode) throws Exception{
                 return dao.getPersonnelNumber(networkCode);
             }
	         public String  getCoordinatorForProvince(int provinceCode)throws Exception{
	        	              return dao.getCoordinatorForProvince(provinceCode);
	         }
	         public Coordinator getCoordinator(int persno) throws Exception{
	        	                  return dao.getCoordinator(persno);
	         }
             private boolean checkForProvince(int provCode) throws Exception{
                              List coordinatorProvList=dao.getCoordinatorList();
                              ListIterator i= coordinatorProvList.listIterator();
                              boolean found=false;
                              while(i.hasNext()){
                                     Coordinator   coordinator =(Coordinator)i.next();
                                     int coordProvCode=Integer.parseInt(coordinator.getWorkStationCode());
                                     if(provCode==coordProvCode){
                                           found=true;
                                           break;
                                     }
                              }
                             return found;
            }
             private boolean checkSadecInt()throws Exception{
                 List coordinatorProvList=dao.getCoordinatorList(coordinator.getPersonnelNumber());
                 ListIterator i= coordinatorProvList.listIterator();
                 boolean found=false;
                 while(i.hasNext()){
                     Coordinator   coord =(Coordinator)i.next();
                     String sadecInt=coord.getSadecInt();
                     if(sadecInt.equals("Y")){
                         found=true;
                         break;
                     }
                }
                return found;
}
            public  String checkCoordinatorForProv(String supervisorCountry, int supervisorProv) throws  Exception{
                                    boolean found=false;
                                    if(supervisorCountry.equals(PlacementUtilities.getSaCode())){
                                    	      found=checkForProvince(supervisorProv);
                                    }else{
                                    	      found=checkSadecInt();
                                    }
                                    if(found){
                                    	  return   "Y"; 
 	                                 }else{
 	            	                      return  "N";             
 	                                }
	                              
            }
            public List getCoordinatorList() throws Exception{
            	           return dao.getCoordinatorList();
            }
            public String isCoordinator( String  networkCode)throws Exception{
                return dao.getCoordinatorActive(networkCode);
            }
            public static int getPosOfCoordinatorInList(int personnelNum,List coordinatorList){
	             int pos=-1;
	             for(int x=0;x<coordinatorList.size();x++){
	            	    Coordinator  coordinator =(Coordinator)coordinatorList.get(x);
	                    if(Integer.parseInt(coordinator.getPersonnelNumber())==personnelNum){
	                    	  pos=x; 
	                   	      break;
	                    }
	              }
	             return pos;
           }
           public List<Coordinator> getCoordinatorListForProvince(int prov)throws Exception{
                                          return dao.getCoordinatorListForProvince(prov);
           }

}