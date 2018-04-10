package impl;
import za.ac.unisa.lms.tools.uploadmanager.dao.AuditDAO;
public class AuditTrail {
	
	     public void writeLogs(String studyItemBarcode,int size,
			          String uploadFullPath,String userId,String filename) throws Exception{
			                 AuditDAO  auditTrailDAO=new AuditDAO();
	                         auditTrailDAO.writeLogs(studyItemBarcode,size, uploadFullPath,userId, filename);
         }
	     public boolean studyMaterialExistInLogs(String studyItemBarcode) throws Exception{
		        		AuditDAO  auditTrailDAO=new AuditDAO();
		                return auditTrailDAO.isRecordExist(studyItemBarcode);
	     }
	     public String getPath(String itemBarCode) throws Exception{
	 		
		        AuditDAO  auditTrailDAO=new AuditDAO();
		        return auditTrailDAO.getPath(itemBarCode);
	     }
	     public boolean isRecordExist(String itemBarCode) throws Exception{
	    	 AuditDAO  auditTrailDAO=new AuditDAO();
		        return auditTrailDAO.isRecordExist(itemBarCode);
	 	 }
	
	     public String getDocumentName(String itemBarCode) throws Exception{
	                    AuditDAO  auditTrailDAO=new AuditDAO();
	                     return auditTrailDAO.getDocumentName(itemBarCode);
         }
	     public String getAllFilepathForCollect(String filepathForLatestUpload ,String itemBarCode) throws Exception {   
	    	             String[] filepaths=filepathForLatestUpload.split("^");
         		         String paths=filepaths[0].toString();
                         if(filepaths.length>1){
            	                paths+=("^"+filepaths[1]);
			             }else{
                 				   paths+=("^"+getPath(itemBarCode));
			             }
			             return paths;
      	}
	     public String getUpdater(String studyMaterialBarcode){
                           AuditDAO db= new AuditDAO();
                         return db.getUserId(studyMaterialBarcode);
             
         }
	     
}
