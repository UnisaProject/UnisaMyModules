package za.ac.unisa.lms.tools.assmarkerreallocation.forms;

import za.ac.unisa.lms.domain.assessmentCriteria.Assignment;

import java.util.List;

public class AssessmentDetails  extends Assignment {
	private boolean markerExist;
	public boolean getMarkerExist(){
		return markerExist;
	}
	public void setMarkerExist(boolean markerExist){
		this.markerExist=markerExist;
	}
	private String module;
	public   String getModule(){
		return module;
	}
	public void setModule(String module){
		this.module=module;
	}
	public Short getAssNumber(){
		 return  Short.parseShort(getNumber());
	}
	private List makerDetailsList;
	public void setMarkerDetailsList(List makerDetailsList){
		this.makerDetailsList=makerDetailsList;
	}
	public List getMakerDetailsList(){
		return makerDetailsList;
	}
	private String docFormats;
	public void setDocFormats(String docFormats){
		this.docFormats=docFormats;
	}
	public String getDocFormats(){
	    	return docFormats;
	}
	public Integer getUniqueAssNum(){
	     return Integer.parseInt(getUniqueNumber());
   }
	public  int  getUniqueAssignmentNum(List assessmentList, int assemssmentNr){
		             int   uniqueAssNum=-1;
	                 for(Object assignment:assessmentList){
	 	                   Assignment  assessment=(Assignment)assignment;
	 	                   int assignmentNr=Integer.parseInt(assessment.getNumber());
	 	                   if(assignmentNr==assemssmentNr){
	 	                	     uniqueAssNum=Integer.parseInt(assessment.getUniqueNumber());
	 	    	                 break;
	 	                   }
	 	            }
	                 return uniqueAssNum;
	}

}
