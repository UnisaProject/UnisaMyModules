package za.ac.unisa.lms.tools.tpstudymaterial.util;

import org.sakaiproject.component.cover.ServerConfigurationService;

public class ServerConfig {
	
	public static String serverUrl = null;
	public static String studyMaterialPath = null;
	
	static {
		serverUrl = ServerConfigurationService.getServerUrl();
		studyMaterialPath = ServerConfigurationService.getString("materialPath");
	}

    public static String currSCMURL(){
	        
    	String server="";
	        
	    if (serverUrl.contains("myqa")){
	      	server="https://lmkn-ealb01sv.int.unisa.ac.za/sharedservices/courseMaterial";
	     }else if(serverUrl.contains("localhost")){
	            server="http://appsdev.int.unisa.ac.za/sharedservices/courseMaterial";
	    		//server="https://lmkn-ealb01sv.int.unisa.ac.za/sharedservices/courseMaterial";
	     }else if(serverUrl.contains("mydev")){
	        	
	    		//server="https://lmkn-ealb01sv.int.unisa.ac.za/sharedservices/courseMaterial";
	    		 server="http://appsdev.int.unisa.ac.za/sharedservices/courseMaterial";
	     }else{
	      	  server="https://apps.unisa.ac.za/sharedservices/courseMaterial";  
	      }
	     return    server;   
	 }
    
    
     public static boolean isTestEnvironment(){
	      
    	 serverUrl = ServerConfigurationService.getServerUrl();
	     
    	 if ((serverUrl.contains("localhost"))||
    			 (serverUrl.contains("mydev"))||
    			 (serverUrl.contains("myqa"))){
	                return true;
	      } else {
	              return false;
	        }
	  }
	  
       public  static boolean isPreProd(){
		 
    	   serverUrl = ServerConfigurationService.getServerUrl();
	                     
    	   if (serverUrl.contains("myqa")){
	  		   return true;
	       } else {
	           return false;
	         }
		}
       
   	    public  static String getPathToMaterialOnServer(){
                return ServerConfigurationService.getString("materialPath");
        }


}
