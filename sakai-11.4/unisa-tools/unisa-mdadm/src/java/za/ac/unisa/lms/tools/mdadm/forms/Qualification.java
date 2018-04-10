package za.ac.unisa.lms.tools.mdadm.forms;

public class Qualification {
	
	private String qualCode;
	private String qualDesc;
	private String specCode;
	private String specDesc;
	public static final String QUAL_CODE = "qualCode";
	public static final String QUAL_DESC = "qualDesc";
	
	public String getQualCode() {
		return qualCode;
	}
	public void setQualCode(String qualCode) {
		this.qualCode = qualCode;
	}
	public String getQualDesc() {
		return qualDesc;
	}
	public void setQualDesc(String qualDesc) {
		this.qualDesc = qualDesc;
	}
	public String getSpecCode() {
		return specCode;
	}
	public void setSpecCode(String specCode) {
		this.specCode = specCode;
	}
	public String getSpecDesc() {
		return specDesc;
	}
	public void setSpecDesc(String specDesc) {
		this.specDesc = specDesc;
	}
	
}
