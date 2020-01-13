package za.ac.unisa.lms.tools.assmarkerreallocation.actions;

import java.util.List;
import za.ac.unisa.lms.tools.assmarkerreallocation.forms.MarkerDetailRecord;
import za.ac.unisa.lms.tools.assmarkerreallocation.forms.MarkerModel;
public class MarkingDetailValidator {
	private  String  validateMarkerAllocation(List markingDetailsList){
		         String  errorMsg="";
		          for (int i=0; i < markingDetailsList.size(); i++){
	                     MarkerDetailRecord markingDetail = (MarkerDetailRecord)markingDetailsList.get(i);	
	                     String markingPerc=markingDetail.getMarker().getMarkPercentage();
	                     if (markingPerc==null || markingPerc.equalsIgnoreCase("")){
	        	                errorMsg= "To Mark % is required for each marker.";
	        	                break;
	                     } else {
		                            if (!Utilities.isInteger(markingPerc)){
		            	                  errorMsg= "To Mark % must be numeric.";
		            	                  break;
		                            }
	                     }
                  }	
		          return errorMsg;
	 }
	 private   String  validateMarkerStatus(List markingDetailsList){
                           String  errorMsg="";
                           boolean activeMarkerFound=false,inactiveMarkerFound=false;;
                           for (int i=0; i < markingDetailsList.size(); i++){
                                  MarkerDetailRecord markingDetail = (MarkerDetailRecord)markingDetailsList.get(i);	
                                  MarkerModel  marker=markingDetail.getMarker();
                                  String markingPerc=markingDetail.getMarker().getMarkPercentage();
                                  String status=marker.getStatus();
                                  if(status.equalsIgnoreCase("Inactive")&&  Integer.parseInt(markingPerc)>0){
                	                   errorMsg="A marker with no role or a person that have resigned may not have a 'To Mark %' > 0.";
                	                   break;
                                  }
                          }
                         return errorMsg;
    }	
	 
	private  String  validateTotPercentage(List markingDetailsList){
		                  String  errorMsg="";
		                   int totalMarkPerc=0;
                          for (int i=0; i < markingDetailsList.size(); i++){
                                MarkerDetailRecord markingDetail = (MarkerDetailRecord)markingDetailsList.get(i);	
                                MarkerModel  marker=markingDetail.getMarker();
                                String markingPerc=markingDetail.getMarker().getMarkPercentage();
                                totalMarkPerc = totalMarkPerc + Integer.parseInt(markingPerc);
	                      }	
                          if (totalMarkPerc != 100){
                  			       errorMsg="The To Mark % of all markers must accumulate to a 100.";
                  		  }
                          return errorMsg;
	}
	
	 public   String  validate(List markingDetailsList,boolean markingLnagExist){
		                  String errorMsg=validateMarkerAllocation(markingDetailsList);
		                  if(!errorMsg.equals("")){
		                        return   errorMsg;	
		                  }
		                  errorMsg=validateMarkerStatus(markingDetailsList);
		                  if(!errorMsg.equals("")){
		                        return   errorMsg;	
		                  }
		                  errorMsg=validateTotPercentage(markingDetailsList);
		                  if(!errorMsg.equals("")){
		                        return   errorMsg;	
		                  }
		                  errorMsg=validateMarkingRecords(markingDetailsList,markingLnagExist);
		                  return errorMsg;
	 }
	 public String validateMarkingRecords(List markingDetailList,boolean markingLnagExist){
         String   erroMsg="";
         for (int i=0; i < markingDetailList.size(); i++){
                MarkerDetailRecord markerDetail = (MarkerDetailRecord)markingDetailList.get(i);
                MarkerModel markerModel=markerDetail.getMarker();
                String status=markerModel.getStatus();
                if(!status.equalsIgnoreCase("Inactive")){
                	if(!markerModel.getPrevMarkPercentage().trim().equals(markerModel.getMarkPercentage().trim())){
                	     if(markingLnagExist)
                	    		 if((markerModel.getChosenMarkingLanguageList()==null)||(markerModel.getChosenMarkingLanguageList().length==0)){
                     	        erroMsg="Please select atleast one  language for markers whose marking percentage has been edited";
                     	   break;
                        }
                	}
                }
         }
         return  erroMsg;
   }
}
