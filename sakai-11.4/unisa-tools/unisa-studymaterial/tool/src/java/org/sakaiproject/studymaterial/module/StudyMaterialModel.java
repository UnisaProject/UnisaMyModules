package org.sakaiproject.studymaterial.module;
import org.sakaiproject.studymaterial.impl.PdfCreater;
import org.sakaiproject.studymaterial.impl.StudyMaterialCodesConverter;
import org.sakaiproject.studymaterial.utils.MetaDataUtils;
import org.sakaiproject.studymaterial.utils.StudyMaterialDataFilteringUtil;
import org.sakaiproject.studymaterial.utils.Utilities;

public class StudyMaterialModel {
	     private  String itembarcode;
	     private  String itemShortDesc;
	     private  String itemFullDesc;
	     private  String unitNumber;
	     private  String documentType;
	     private  String language;
	     private  String module;
	     private  String period;
	     private  String year;
	     private String dateAvailable;
	     private  String  fileSize;
	     private  String lecturer;
	     private String category;
	     private String filePath;
	     private String itemDisplayName;
	     private  String filename;
	     public String getFilename(){
	    	 return filename;
	     }
	    public String getFilePath(){
	    	        return filePath;
         }
	     public void setFilePath(){
	    	            filePath= Utilities.getFilepath(filename, module,documentType);
	      }
	     public void setFilePath(String filePath){
	           this.filePath=filePath;
         }
         public String getItemBarcode(){
	    	 return itembarcode;
	     }
	     public void setItemBarcode(String itembarcode){
	    	 this.itembarcode=itembarcode;
	     }
	     public String getItemShortDesc(){
	    	 return itemShortDesc;
	     }
	     public void setItemShortDesc(String itemShortDesc){
	    	    	 this.itemShortDesc=itemShortDesc;
	    	    	 setDocumentType();
	    	    	 setLanguage();
	    	    	// setFilePath();
	     }
	     public String getItemFullDesc(){
	    	 return itemFullDesc;
	     }
	     public void setItemFullDesc(String itemFullDesc){
	    	 this.itemFullDesc=itemFullDesc;
	     }
	     public String getUnitNumber(){
	    	 return unitNumber;
	     }
	     public void setUnitNumber(String unitNumber){
	    	 this.unitNumber=unitNumber;
	     }
	     public String getDocumentType(){
	    	 return documentType;
	     }
	     public void setDocumentType(String documentType){
	    	 this.documentType=documentType;
	     }
	     public void setDocumentType(){
	    	   this.documentType=MetaDataUtils.getType(itemShortDesc);
	     }
	     public String getLanguage(){
	    	 return language;
	     }
	     public void setLanguage(String language){
	    	        this.language=language;
	     }
	     public void setLanguage(){
 	        this.language=MetaDataUtils.getlanguage(itemShortDesc);
         }
	     public String getModule(){
	    	 return module;
	     }
	     public void setModule(String module){
	    	 this.module=module;
	     }
	     public String getPeriod(){
	    	 return period;
	     }
	     public void setPeriod(String period){
	    	 this.period=period;
	     }
	     public String getYear(){
	    	 return  year;
	     }
	     public void setYear(String year){
	    	 this. year= year;
	     }
	     public String getDateAvailable(){
	    	 return  dateAvailable;
	     }
	     public void setDateAvailable(String dateAvailable){
	    	    this.dateAvailable= dateAvailable;
	     }
	     public void setFileSize(){
	    	               //fileSize=PdfCreater.getFileSize(filename, module, documentType);
	    	 fileSize= "xx";
	    }
	     public String displayFileSize(){
	           	 return  ""+fileSize+"  ";
         }
	     public String  getFileSize(){
	                	 return  fileSize;
         }
	     public void setFileSize(String fileSize){
	    	  this.fileSize= fileSize;
	     }
	    public String getLecturer(){
	    	 return  lecturer;
	     }
	     public void setLecturer(String lecturer){
	    	    this.lecturer= lecturer;
	     }
	     public String getCategory(){
	    	 return   category;
	     }
	     public void setCategory(String  category){
	    	    this.category=  category;
	     }
	     public String setItemDisplayName(){
                          StudyMaterialCodesConverter codesConverter=new StudyMaterialCodesConverter();
                          itemDisplayName=codesConverter.convertCode(documentType)+"  "+unitNumber+" ("+
		                             codesConverter.convertCode(language)+")"+ " for "+module;
             return itemDisplayName;
         }
	     public String getItemDisplayName(){
             return itemDisplayName;
         }
	     public boolean isStudyMaterialValid(){
	    	               return StudyMaterialDataFilteringUtil.isStudyMaterialValid(documentType, fileSize,dateAvailable);
	     }
	     public void setFilename(){
	     	        this.filename=MetaDataUtils.getFileName(itemShortDesc);
	     }
}
