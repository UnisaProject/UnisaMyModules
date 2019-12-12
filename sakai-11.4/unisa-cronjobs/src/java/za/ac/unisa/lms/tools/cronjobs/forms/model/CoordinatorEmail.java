package za.ac.unisa.lms.tools.cronjobs.forms.model;
import za.ac.unisa.lms.tools.cronjobs.forms.model.Email;
import java.util.List;
import java.util.Set;
import java.util.ArrayList;
import java.util.Iterator;
import org.sakaiproject.component.cover.ServerConfigurationService;
public class CoordinatorEmail extends Email{
	 
	       public  void sendEmailToCoordiators(Set supervCodeList){
	    	             try{
    	                 StudentPlacement stupl=new StudentPlacement();
    	                 List supervisorList=getSupervisorsList(supervCodeList);
    	                 CoordinatorMailBody  smb=new CoordinatorMailBody();
    	                 Province prov=new Province();
    	                 Set provCodesList=prov.getProvinceCodeList();
    	                 Iterator i=provCodesList.iterator();
    	                 while(i.hasNext()){
    	                	 try{
    	                	      int provCode=Integer.parseInt(i.next().toString());
    	                	      List supervListForCoordinator= makeSupervListForCoordinator(supervisorList,provCode);
    	                	      if((supervListForCoordinator!=null)&&(!supervListForCoordinator.isEmpty())){ 
    	                		        sendEmailToCoord(supervListForCoordinator,smb);
    	                   	      }
    	                	 }catch(Exception ex){
   	    	            	         continue;
   	    	                 }
    	                 }
	    	        }catch(Exception ex){
	    	        	return;
	    	        }
          }
	     
	      private List makeSupervListForCoordinator(List supervList,int provCode) throws Exception{
	    	                Iterator i=supervList.iterator();
	    	                List supervListForCoord=new ArrayList();
	    	                while(i.hasNext()){
	    	                	try{
	    	        	             Supervisor sup=(Supervisor)i.next();
	    	        	             short  supProvCode=sup.getProvinceCode();
	    	        	             if(supProvCode==provCode){
	    	        	        	       supervListForCoord.add(sup);
		    	        	         }
	    	                	}catch(Exception ex){
	    	                            continue;
	    	                    }
	    	        	    }
	    	                return supervListForCoord;
	      }
	      
	     private List getSupervisorsList(Set supervCodesList) throws Exception{
                              Iterator i=supervCodesList.iterator();
                              List supervisorList=new ArrayList();
                              while(i.hasNext()){
                            	  try{
                            	         int supCode=Integer.parseInt(i.next().toString());
                            	         addSupervisorToList(supCode,supervisorList);
                                   }catch(Exception ex){
                            	         continue;
  	                               }
     		              	  }
     	                 return supervisorList;
         }
	     private void addSupervisorToList(int supCode,List supervisorList){
	    	              Supervisor sup=new Supervisor();
	    	              try{
                                sup=sup.getSupervisor(supCode);
	    	                    List provCodesList=sup.getProvCodesList();
	    	                    if((provCodesList==null)||(provCodesList.size()==0)){
	    	                    	   provCodesList=new ArrayList();
	    	                    	   provCodesList.add("21");
	    	                    }
                                Iterator i=provCodesList.iterator();
                                int provCode=1;
                                while(i.hasNext()){
                        	            provCode= Integer.parseInt(i.next().toString());
                        	            Supervisor supervisor=new Supervisor();
                        	            supervisor=supervisor.getSupervisor(supCode);
                        	            supervisor.setProvinceCode((short)provCode);
                        	            supervisorList.add(supervisor);
                        	    }
	    	              }catch(Exception ex){
	    	            	  return;
	    	              }
         }
	     private int totEmailSentToCoord=0;
	     public int getTotEmailSentToCoord(){
	    	 return totEmailSentToCoord;
	     }
	     public void setTotEmailSentToCoord(int totEmailSentToCoord){
	    	           this.totEmailSentToCoord=totEmailSentToCoord;
	     }
	     private void   sendEmailToCoord(List supervList,CoordinatorMailBody smb) throws Exception{
	    	                  Supervisor sup=(Supervisor)supervList.get(0);
	                          int provinceCode=sup.getProvinceCode();
	                          Coordinator coordinator=new Coordinator();
	                          List<Coordinator> coordList;
	                          coordList=coordinator.getCoordinatorListForProvince(provinceCode);
	                          Iterator i=coordList.iterator();
                              while(i.hasNext()){
                            	   try{
                            	         Coordinator  coord=(Coordinator)i.next();
                                         if((supervList!=null)&&(!supervList.isEmpty())){
  	                                           body=smb.createEmailBody(coord.getName(),supervList);
                                               String emailAddr=coord.getEmailAddress();
                                               prepAdressing(emailAddr);
      	                                       if((toEmail==null)||(toEmail.equals(""))){
      	                                    	        toEmail="teachprac@unisa.ac.za";
                                               }
      	                                       subject="Unisa Teaching Practice Placement Information  to Coordinator";
                                               sendEmail();
                                               totEmailSentToCoord++;
                                   }
                            	   }catch(Exception ex){
                                  	       continue;
                                   }
                            	  
                              } 
	     } 
	    
      
      
}