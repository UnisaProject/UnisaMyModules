package org.sakaiproject.studymaterial.utils;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.sakaiproject.component.cover.ServerConfigurationService;

public class Utilities {

	public static String getMD5(byte[] source) {
		
		String s = null;
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'a', 'b', 'c', 'd', 'e', 'f' };
		try {
			java.security.MessageDigest md = java.security.MessageDigest
					.getInstance("MD5");
			md.update(source);
			byte tmp[] = md.digest();
			char str[] = new char[16 * 2];
			int k = 0;
			for (int i = 0; i < 16; i++) {
				byte byte0 = tmp[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			s = new String(str);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return s;
	}
	
	public static String scmServerUrl(){
        String serverpath = ServerConfigurationService.getServerUrl();
        if (serverpath.contains("localhost")||serverpath.contains("mydev")){
      	  serverpath="http://appsdev.int.unisa.ac.za/sharedservices/courseMaterial";
        	//serverpath="https://lmkn-ealb01sv.int.unisa.ac.za/sharedservices/courseMaterial"; //new
      	  //serverpath="https://lmkn-ealb01sv.int.unisa.ac.za/sharedservices/courseMaterial";
         }else if (serverpath.contains("myqa")) {
      	   serverpath="https://lmkn-ealb01sv.int.unisa.ac.za/sharedservices/courseMaterial";
      	  
         } else {
      	   serverpath="https://apps.unisa.ac.za/sharedservices/courseMaterial"; 
      	   
         }
      return  serverpath;   
    }
	
	
	public static String currServer() {
        String serverpath = ServerConfigurationService.getServerUrl();
        
        if(serverpath.contains("localhost")||serverpath.contains("mydev")){
              serverpath="https://myqa.int.unisa.ac.za";
        }
        return    serverpath;   
    }
    
	public static boolean isTestEnvironment(){
        String serverpath = ServerConfigurationService.getServerUrl();
               
        if ((serverpath.contains("localhost"))||(serverpath.contains("mydev"))
        		         ||(serverpath.contains("myqa"))) {
             return true;
         }else {
             return false;
          }
	}
	
	public  static boolean isPreProd(){
		 String serverpath = ServerConfigurationService.getServerUrl();
         
		 if (serverpath.contains("myqa")){
  		       return true;
         } else {
               return false;
          }
	}
	
	public  static String getPathToMaterialOnServer(){
		  return ServerConfigurationService.getString("materialPath");
	}
	
	public  static long getFileLength(String pdffullPath){
	      File file = new File(pdffullPath);
	      int size=0;
	      if(file.exists()){
	            size = (int)file.length();
	      }
	  return size;
	}
	
	public static String getFilepath(String filename,String module,String docType){
		
		String documentType = getStudyMaterialTypeDirectoryName(docType);
		String filePath = Utilities.getPathToMaterialOnServer()+module+"/"+documentType+"/"+filename;
		File pdfFile = new File(filePath);
        if (!pdfFile.exists()) {
            filePath=Utilities.getPathToMaterialOnServer()+"collect"+"/"+module+"/"+documentType+"/"+filename;
        }

      return filePath;
    }
	
	public static String getFilepathInserver(String filename,String module,String docType){
        
		String documentType = getStudyMaterialTypeDirectoryName(docType);
		
		String filePathOrginal = Utilities.getPathToMaterialOnServer()+module+"/"+documentType+"/"+filename;
		String filePath=module+"/"+documentType+"/"+filename;
        File pdfFile = new File(filePathOrginal);
        
        if(!pdfFile.exists()){
                 filePath="collect"+"/"+module+"/"+documentType+"/"+filename;
        }
        
         return filePath;
   }
	
   public  static  void closeStream(InputStream stream){
     
	   try {
	       if (stream != null) {
		       stream.close();
		   }
		}catch(IOException e){
			e.printStackTrace();
		}
	}
   
	public static String getStudyMaterialTypeDirectoryName(String annType) {

		String dirType = null;

		if ((annType.equalsIgnoreCase("TL"))
				|| (annType.equalsIgnoreCase("TW"))
				|| (annType.equalsIgnoreCase("TP"))) {
			dirType = "tl";
		} else if ((annType.equalsIgnoreCase("GD"))
				|| (annType.equalsIgnoreCase("SW"))
				|| (annType.equalsIgnoreCase("SP"))) {
			dirType = "sg";
		} else if (annType.equalsIgnoreCase("MA")) {
			dirType = "ma";
		} else if (annType.equalsIgnoreCase("WB")) {
			dirType = "wb";
		} else if (annType.equalsIgnoreCase("QB")) {
			dirType = "qb";
		} else if (annType.equalsIgnoreCase("MO")) {
			dirType = "mo";
		} else if (annType.equalsIgnoreCase("LB")) {
			dirType = "lb";
		} else if (annType.equalsIgnoreCase("BL")) {
			dirType = "bl";
		} else if (annType.equalsIgnoreCase("BB")) {
			dirType = "bb";
		} else if (annType.equalsIgnoreCase("HL")) {
			dirType = "hl";
		} else if (annType.equalsIgnoreCase("RE")) {
			dirType = "re";
		} else if (annType.equalsIgnoreCase("MG")) {
			dirType = "mg";
		}

		return dirType;
	}
	
}