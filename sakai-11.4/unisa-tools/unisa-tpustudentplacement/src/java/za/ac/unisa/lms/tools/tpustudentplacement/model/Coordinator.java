package za.ac.unisa.lms.tools.tpustudentplacement.model;
import za.ac.unisa.lms.tools.tpustudentplacement.dao.CoordinatorDAO;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.Country;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.StudentPlacementForm;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.StudentPlacementListRecord;
import za.ac.unisa.lms.tools.tpustudentplacement.model.modelImpl.CoordinatorImpl;
import za.ac.unisa.lms.tools.tpustudentplacement.model.modelImpl.schoolImpl.SchoolUI;
import za.ac.unisa.lms.tools.tpustudentplacement.uiLayer.CommunicationUI;
import za.ac.unisa.lms.tools.tpustudentplacement.uiLayer.ProvinceUI;
import za.ac.unisa.lms.tools.tpustudentplacement.utils.InfoMessagesUtil;

import java.util.List;
import org.apache.struts.action.ActionMessages;

public class Coordinator extends Personnel{
             
               private String sadecInt;//it indicates if coordinator is from sadec or international country
           
               CoordinatorImpl coordinatorImpl;
               CoordinatorDAO dao;
               public Coordinator(){
            	         dao= new CoordinatorDAO();
            	         this.coordinatorImpl=new CoordinatorImpl(this,dao);
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
           	   
           	  public List getCoordinatorList() throws Exception{
           		           return  coordinatorImpl.getCoordinatorList();
              }
              public  String checkCoordinatorForProv(String supervisorCountry,int supervisorProv) throws  Exception{
           		                     return coordinatorImpl.checkCoordinatorForProv(supervisorCountry, supervisorProv);
           	  }
              public Coordinator getCoordinatorForProv(int provinceCode)throws Exception{
	                      return coordinatorImpl.getCoordinatorForProv(provinceCode);
              }
              public String  getPersnoForProvWSC(int provinceCode)throws Exception{
                              return coordinatorImpl.getPersnoForProvWSC(provinceCode);
              }
              public Coordinator getCoordinator(int persno) throws Exception{
	                     return coordinatorImpl.getCoordinator(persno);
              }
              public Coordinator getCoordinatorForSadecInt() throws Exception{
                                     return coordinatorImpl.getCoordinatorForSadecInt();
              }
              public Coordinator getCoordForProvGivenSchoolCode(int schoolCode)throws Exception{
            	                      SchoolUI school=new SchoolUI();
            	                      boolean isSchoolLocal=school.isSchoolLocal(schoolCode);
                                      if(isSchoolLocal){
                                    	       int provCode=school.getSchoolProvCode(schoolCode);
                                    	       return getCoordinatorForProv(provCode);
                                      }else{
                                    	      return  getCoordinatorForSadecInt();
                                      }
	         }
              public String  updateCoordinator(Coordinator coordinator,String currProv)throws Exception{
            	               if((!coordinator.getWorkStationCode().equals(currProv))&&
            	            		   dao.isCoordinatorLinked(coordinator.getPersonnelNumber(),coordinator.getWorkStationCode())){
            	                	return "Coordinator is already linked for the workstation ";
            	                }
            	                return dao.updateCoordinator(coordinator,currProv);
              }
              public String  saveCoordinator(Coordinator coordinator)throws Exception{
            	                  return dao.saveCoordinator(coordinator);
              }
              public Coordinator getProspectiveCoordinator(int persno) throws Exception{
            	                       return dao.getProspectiveCoordinator(persno);
              } 
              private void addNoProvincialCoordMessage(int schoolCode,ActionMessages emailMessages) throws Exception {
            	                    InfoMessagesUtil infoMessagesUtil=new InfoMessagesUtil();
            	                    String message="";
            	                    SchoolUI school=new SchoolUI();
	    	                        String schoolName=school.getSchoolName(schoolCode);
	    	                        if(school.isSchoolLocal(schoolCode)){
	    	                        	    ProvinceUI  prov=new ProvinceUI ();
	            	                        prov.setProvinceData(schoolCode);
		    	                            message+=("Placement is linked to a Dummy Supervisor.The province ( "+ prov.getDescription()+" )  where the school ("+
	    	                        		schoolName+") is situated has no"+
	    	                                " Provincial Workstation coordinator.The email can't be sent till this error is fixed");
	    	                        }else{
	    	                        	  Country  country=new Country();
	    	                        	 message+=("Placement is linked to a Dummy Supervisor.The school("+
	 	    	                        		 schoolName+" ) is situated in a foreign country ( "+country.getSchoolCountry(schoolCode)+" ) and there is no coordinator for  SADEC/International Countries "+
	 	    	                               ".The email can't be sent till this error is fixed");
	    	                        }
	    	                        infoMessagesUtil.addMessages(emailMessages, message);
	    	 }
             public void handleNoProvincialCoord(StudentPlacementForm studentPlacementForm,ActionMessages emailMessages) throws Exception {
            	                        CommunicationUI communicationUI=new CommunicationUI();
                                        List  placementList=communicationUI.getStuPlacementsForSchool(studentPlacementForm);
                                        if(placementList.size()>0){
                                              for(int x=0;x<placementList.size();x++){
                                                       StudentPlacementListRecord  placement=(StudentPlacementListRecord)placementList.get(x);
                                                       if(placement.getSupervisorCode()==283){
                                                    	        int schoolCode=placement.getSchoolCode();
      	                                                        Coordinator coordinator=new Coordinator();
      	                                                        coordinator=getCoordForProvGivenSchoolCode(schoolCode);
      	                                                        if(coordinator==null){
  	                                                                   coordinator=new Coordinator();
  	                                                                   addNoProvincialCoordMessage(schoolCode, emailMessages);
  	                                                                   break;
  	                                                            }
                                                       }
                                             }
                                       }
           }
 }
