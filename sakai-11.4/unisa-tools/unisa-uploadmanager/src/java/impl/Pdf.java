package impl;

import java.io.File;
import org.sakaiproject.component.cover.ServerConfigurationService;
import utils.FileUploadUtils;
import utils.InfoMessagesUtil;

public class Pdf {
	private  String pathToMaterialOnServer=ServerConfigurationService.getString("materialPath");
	
       public String getReloadDirPath(String itembarcode,String module) throws Exception{
                     
    	             AuditTrail  auditTrail=new AuditTrail();
                     
    	             String currpathInLogs=auditTrail.getPath(itembarcode);
                     
    	             if (currpathInLogs.indexOf("collect")!=-1){
  	                 	     String[]  uploadpaths=currpathInLogs.split("^");
                    	     for(int x=0;x<uploadpaths.length;x++){
                    	    	 currpathInLogs="unknown path";
                    	    	 if(uploadpaths[x].indexOf(module)!=-1){
                    	    		 currpathInLogs=uploadpaths[x];
                    	    		 break;
                    	    	}
                    	     }
                    }
    	             
                    return currpathInLogs;
     }
     public String getUploadDirPathForSingle(String type,String module){
    	                      return  pathToMaterialOnServer+module+"/"+type.toLowerCase()+"/";
     }
     public String getUploadDirPathForCollection(String type,String module){ 
    	                 return pathToMaterialOnServer+"collect/"+module+"/"+type.toLowerCase()+"/";
     }
     
     public String  copyFileToCollection(String inputFilePath,StudyMaterial studyMaterial,AuditTrail  auditTrail) throws Exception{
         String errorMsg="";
         Pdf pdf=new Pdf();
         String uploadpath=pdf.getUploadDirPathForCollection(studyMaterial.getType(), studyMaterial.getModule());
            errorMsg= copyFileToSingle(inputFilePath,uploadpath,studyMaterial.getFilename());
           if(!errorMsg.equals("")){
	             return errorMsg;
        }
         String pathInLog=auditTrail.getPath(studyMaterial.getItembarcode());
         if(pathInLog.indexOf("collect")==-1){
         	String moduleInLog=FileUploadUtils.getModuleCodeFromFilePath(auditTrail.getPath(studyMaterial.getItembarcode()));
         	uploadpath=pdf.getUploadDirPathForCollection(studyMaterial.getType(), moduleInLog);
         	errorMsg= copyFileToSingle(inputFilePath,uploadpath,studyMaterial.getFilename());
         }
         studyMaterial.setFilepath(uploadpath);
        
          return errorMsg;
     }
     
     public String  copyFileToSingle(String inputFilePath,String uploadpath,String filename ) throws Exception {
    	    
    	   File uploadOrgFilePath = new File(inputFilePath);
    	   File copyToFilePath = new File(uploadpath);
           String  uploadErrorMsg=InfoMessagesUtil.fileCopyErrorMsg();
           FileUploadUtils.copyFile(inputFilePath,uploadpath,filename);
           File inputfilePath = new File(inputFilePath);
           
           if (inputfilePath.exists()) {
               boolean deleteFlag = inputfilePath.delete();
           }
           uploadErrorMsg="";
           return uploadErrorMsg;
     }       
     


     
}
