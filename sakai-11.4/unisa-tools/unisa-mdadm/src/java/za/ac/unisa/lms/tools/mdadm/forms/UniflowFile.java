package za.ac.unisa.lms.tools.mdadm.forms;

import org.datacontract.schemas._2004._07.UniflowFindAndGetService.DocumentLocation;

public class UniflowFile {
	
	private String displayName="";
	private String shortDesc = "";
	private String uniqueId = "";
	private DocumentLocation documentLocation;
	
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	public String getUniqueId() {
		return uniqueId;
	}
	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}
	public DocumentLocation getDocumentLocation() {
		return documentLocation;
	}
	public void setDocumentLocation(DocumentLocation documentLocation) {
		this.documentLocation = documentLocation;
	}
	public String getShortDesc() {
		return shortDesc;
	}
	public void setShortDesc(String shortDesc) {
		this.shortDesc = shortDesc;
	}
	@Override
	public String toString() {
		StringBuffer result = new StringBuffer();
		result.append("\r\nfile display name= "+ this.displayName);
		result.append("\r\n     short desc= "+ this.shortDesc);
		result.append("\r\n     display uniqueId= "+ this.uniqueId);
		result.append("\r\n     location name= "+ this.documentLocation.getName());
		result.append("\r\n     location type= "+ this.documentLocation.getType().toString());
		
		return result.toString();
	}
	
}
