package za.ac.unisa.lms.tools.uploadmanager.dao;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.map.ListOrderedMap;
import org.springframework.jdbc.core.JdbcTemplate;

import za.ac.unisa.lms.db.SakaiDAO;

public class AuditDAO  extends SakaiDAO {

	
	public void setAuditReport(String barcode, String filename, String path, int filesize, String userId, String lastUpload)throws Exception{
		
		double m = filesize/1024;
		m=m/1024;
		DecimalFormat dec = new DecimalFormat("0.00");
		String size = dec.format(m).concat("MB");
		
	 String sql = " INSERT INTO UNISA_PDFUPLOAD(BARCODE, FILENAME, PATH, FILESIZE, EID, LAST_UPLOAD)"+
                  " VALUES (?, ?, ?, ?, ?, sysdate)";

                  JdbcTemplate jdt = new JdbcTemplate(getDataSource());
                  try{
                   jdt.update(sql,new Object[] {barcode,filename,path,size,userId.toUpperCase()});

                  }catch(Exception ex){
                  throw new Exception("AuditDAO: setAuditReport"+ex);
                  }
	           }
  


    public void updateAuditReport(String barcode, String path, int filesize, String userId,String lastUpload)throws Exception{
    	
    	double m = filesize/1024;
    	m=m/1024;
		DecimalFormat dec = new DecimalFormat("0.00");
		String size = dec.format(m).concat("MB");
	
         String query = "UPDATE UNISA_PDFUPLOAD SET FILESIZE="+"'"+size+"'"+
                        ", EID ="+"'"+userId.toUpperCase()+"'"+
        		        ",PATH='"+path+"'"+
                        ", LAST_UPLOAD = to_date('"+lastUpload+"','YYYY-MM-DD HH24:mi:SS')"+
                        " WHERE BARCODE="+"'"+barcode+"'";
            JdbcTemplate jdt = new JdbcTemplate(getDataSource());
            try{     
     	    jdt.update(query);
            }catch(Exception ex){
	                  throw new Exception("AuditDAO.java: updateAuditReport"+ex);
           }
           
    }
    public void writeLogs(String studyItemBarcode,int size, 
            String uploadFullPath,String userId,String filename) throws Exception{

                     AuditDAO db= new AuditDAO();
                     DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                     Date date = new Date();	
                     boolean auditRecordExistInDatabase = db.isRecordExist(studyItemBarcode);
                     String timeStamp = dateFormat.format(date);
                       if(auditRecordExistInDatabase==true){
                             db.updateAuditReport(studyItemBarcode,uploadFullPath,size,userId, timeStamp);
                      }else{
                              db.setAuditReport(studyItemBarcode, filename,uploadFullPath,size, userId, timeStamp);
                     }
    }
    public String getDocumentName(String itemBarCode)
	 {
	 			  String sql= "SELECT FILENAME FROM UNISA_PDFUPLOAD WHERE BARCODE ="+"'"+itemBarCode+"'";
	 			  
	 			  String filename = this.querySingleValue(sql,"FILENAME");
				
					  				
				  
		return filename;
	}
    
    public String getPath(String itemBarCode)
	 {
	 			  String sql= "SELECT PATH FROM UNISA_PDFUPLOAD WHERE BARCODE ="+"'"+itemBarCode+"'";
	 			  
	 			  String path = this.querySingleValue(sql,"PATH");
				
					  				
				  
		return path;
	}
    
    public String getUserId(String itemBarCode)
 	 {
 	 			  String sql= "SELECT EID FROM UNISA_PDFUPLOAD WHERE BARCODE ="+"'"+itemBarCode+"'";
 	 			  
 	 			  String userId = this.querySingleValue(sql,"EID");
 				
 					  				
 				  
 		return userId;
 	}
    public String getUpdatedDate(String itemBarCode)
  	 {
  	 			  String sql= "SELECT TO_CHAR(LAST_UPLOAD, 'YYYY-MM-DD') as lastDate FROM UNISA_PDFUPLOAD WHERE BARCODE ="+"'"+itemBarCode+"'";
  	 			  
  	 			  String filename = this.querySingleValue(sql,"lastDate");
  				
  					  				
  				  
  		return filename;
  	}
    
	public boolean isRecordExist(String itemBarCode) throws Exception{
		String query = " SELECT COUNT(*) as COUNT "+
                       " FROM UNISA_PDFUPLOAD "+
                       " WHERE BARCODE ="+"'"+itemBarCode+"'";
            	try {
        	    String recCount = this.querySingleValue(query,"COUNT");
        	    int counter =  Integer.parseInt(recCount); 

        		if(counter > 0){
        			return true;
        		}	else {
        			return false;
        		}
        		
        	} catch (Exception ex) {
        		throw new Exception("isRecordExist: Error occurred / "+ ex,ex);
        		
        	}
	 }
    private String querySingleValue(String query, String field){
  		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
  		List queryList;
  		String result = "";
  		

  		queryList = jdt.queryForList(query);
  		Iterator i = queryList.iterator();
  		if (i.hasNext()) {
             ListOrderedMap data = (ListOrderedMap) i.next();
             if (data.get(field) == null){
             } else {
                 result = data.get(field).toString();
                 
             }
  		}
  		return result;
     }
    
}

