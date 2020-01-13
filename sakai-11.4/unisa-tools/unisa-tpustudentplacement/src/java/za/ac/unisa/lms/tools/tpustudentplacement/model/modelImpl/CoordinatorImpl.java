package za.ac.unisa.lms.tools.tpustudentplacement.model.modelImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import org.apache.commons.collections.map.ListOrderedMap;

import za.ac.unisa.lms.tools.tpustudentplacement.dao.CoordinatorDAO;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.StudentPlacementListRecord;
import za.ac.unisa.lms.tools.tpustudentplacement.model.Coordinator;
import za.ac.unisa.lms.tools.tpustudentplacement.utils.PlacementUtilities;

public class CoordinatorImpl {
	       
	         CoordinatorDAO dao;
	         Coordinator coordinator;
	         public CoordinatorImpl(Coordinator coordinator,CoordinatorDAO dao){
	        	       this.dao=dao;
	        	       this.coordinator=coordinator;
	         }
	        
	          public Coordinator  getCoordinatorForProv(int provinceCode)throws Exception{
	        	              return dao.getCoordinatorForProv(provinceCode);
	         }
	         public Coordinator getCoordinator(int persno) throws Exception{
	        	                  return dao.getCoordinator(persno);
	         }
	         public String  getPersnoForProvWSC(int provinceCode)throws Exception{
	        	 return dao.getPersnoForProvWSC(provinceCode);
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
                              List coordinatorProvList=dao.getCoordinatorList();
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
                             return dao.isCoordinatorActive(networkCode);
            }
            public Coordinator getCoordinatorForSadecInt() throws Exception{
                                 return dao.getCoordinatorForSadecInt();
            }


}