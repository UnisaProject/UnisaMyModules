package za.ac.unisa.lms.tools.tpustudentplacement.model;
import java.util.List;

import za.ac.unisa.lms.tools.tpustudentplacement.forms.StudentPlacement;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.StudentPlacementListRecord;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.StudentPlacementForm;
import za.ac.unisa.lms.tools.tpustudentplacement.model.modelImpl.PlacementImage;
import za.ac.unisa.lms.tools.tpustudentplacement.model.modelImpl.studentPlacementImpl.StudentPlacementLogImpl;

public class StudentPlacementLog extends StudentPlacement{
	 private String year;
	 private String semester;
	 private String action;
	 private String correspondenceTo;
	 private String  beforeImage="";
	 private String  afterImage="";
	 private String cellNum;
	 private String emailAddress;
	 private String updatedBy;//networkCode
	 private String updatedOn;
	 private String sortOrder;
	 private Personnel personnel;
	 private String  imageTracker="0";
	 StudentPlacement placement;
	 
	 public void setPlacement(StudentPlacement placement){
		 this.placement=placement;
	 }
	 public StudentPlacement getPlacement(){
		       return placement;
	 }
	 
	 private PlacementListRecordDataCopier placemntListRecCopier;
	 
	 StudentPlacementLogImpl stpi;
	 public StudentPlacementLog(){
		       stpi=new StudentPlacementLogImpl(this);
		       personnel=new Personnel();
	           placemntListRecCopier=new PlacementListRecordDataCopier();
	 }
	 
	 public String getImageTracker(){
   	  return  imageTracker;
     }
     public void setImageTracker(String  imageTracker){
   	  this.imageTracker= imageTracker;
     }
	 public Personnel getPersonnel(){
		   return  personnel;
	 }
	 public String  getPersonnelNumber()throws Exception{
		    return  personnel.getPersonnelNumber(updatedBy);
	 }
	 public StudentPlacementLog getStoredLog(String action,String  updatedby,String  updatedOn)  throws Exception{
         return stpi.getStoredLog(action,updatedby,updatedOn);
     }
	 public void getStoredData(String action,String  updatedby,String  updatedOn)  throws Exception{
		    //it updates it's data members with data from the data storage
                    stpi.getStoredData(action,updatedby,updatedOn);
     }
	 public String getSortOrder(){
		 return sortOrder;
	 }
	 public void setSortOrder(String sortOrder){
		    this.sortOrder=sortOrder;
	 }
	  public String getYear(){
		 return year;
	 }
	 public void setYear(String year){
		 this.year=year;
	 }
	 public String getSemester(){
		 return semester;
	 }
	 public void setSemester(String semester){
		      this.semester=semester;
	 }
	 public String getAction(){
		 return action;
	 }
	 public void setAction(String action){
		 this.action=action;
	 }
	 public String getCorrespondenceTo(){
		 return correspondenceTo;
	 }
	 public void setCorrespondenceTo(String correspondenceTo){
		  this.correspondenceTo=correspondenceTo;
	 }
     public void disassemblePlacementImage(String  placementImage){
    	     stpi.disassembleImage(placementImage);
     }
     public void disassembleAfterImage(){
	          stpi.disassembleImage(afterImage);
     }
     public String getBeforeImage(){
    	 return beforeImage;
     }
     public void setBeforeImage(String beforeImage){
    	          this.beforeImage=beforeImage;
     }
     public String getAfterImage(){
    	 return afterImage;
     }
     public void setAfterImage(String afterImage){
   	       this.afterImage=afterImage;
    }
     public String getCellNum(){
    	 return cellNum;
     }
     public void setCellNum(String cellNum){
    	 this.cellNum=cellNum;
    }
    public void setEmailAddress(String emailAddress){
    	 this.emailAddress=emailAddress;
    }
	public String getEmailAddress(){
		 	 return emailAddress;
    }
	 public void setUpdatedBy(String updatedBy){
    	 this.updatedBy=updatedBy;
    }
	public String getUpdatedBy(){
		 return updatedBy;
    }
	public void setUpdatedOn(String updatedOn){
    	 this.updatedOn=updatedOn;
    }
	public String getUpdatedOn(){
		 return updatedOn;
    }
	
   public List getListOfAllLogs()throws Exception{
	            	   return stpi.getListOfAllLogs();
   }
   public List getListOfSelectedLogs()throws Exception{
          	   return stpi.getListOfSelectedLogs();
   }
   public String getStoredPlacementImage(String action,String updatedOn,String updatedBy)throws Exception{
	          return stpi.getStoredPlacementImage(action,updatedOn,updatedBy);
   }
   
   
}
