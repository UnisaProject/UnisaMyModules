package za.ac.unisa.lms.tools.exampaperonline.forms;

import java.util.List;

public class LinkedPaper {
	
	String inputStudyUnitStatus;
	String motherCode;	
	List linkedPapers;	
	String linkedDesc;
	String shortLinkedDesc;
	
	public String getInputStudyUnitStatus() {
		return inputStudyUnitStatus;
	}
	public void setInputStudyUnitStatus(String inputStudyUnitStatus) {
		this.inputStudyUnitStatus = inputStudyUnitStatus;
	}
	public String getMotherCode() {
		return motherCode;
	}
	public void setMotherCode(String motherCode) {
		this.motherCode = motherCode;
	}
	public List getLinkedPapers() {
		return linkedPapers;
	}
	public void setLinkedPapers(List linkedPapers) {
		this.linkedPapers = linkedPapers;
	}
	public String getLinkedDesc() {
		return linkedDesc;
	}
	public void setLinkedDesc(String linkedDesc) {
		this.linkedDesc = linkedDesc;
	}
	public String getShortLinkedDesc() {
		if (this.linkedDesc!=null && this.linkedDesc.length() > 15){
			shortLinkedDesc = this.linkedDesc.substring(0,15) + "   ...more";
		}else{
			shortLinkedDesc = this.linkedDesc;
		}		
		return shortLinkedDesc;
	}
	public void setShortLinkedDesc(String shortLinkedDesc) {
		this.shortLinkedDesc = shortLinkedDesc;
	}	

}
