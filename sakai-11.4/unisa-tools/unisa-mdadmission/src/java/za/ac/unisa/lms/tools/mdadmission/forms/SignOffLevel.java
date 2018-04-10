package za.ac.unisa.lms.tools.mdadmission.forms;

public class SignOffLevel {
	
	private String qualCode;
	private String specCode;
	private String finalFlag="";
	
	public String getQualCode() {
		return qualCode;
	}
	public void setQualCode(String qualCode) {
		this.qualCode = qualCode;
	}
	public String getSpecCode() {
		return specCode;
	}
	public void setSpecCode(String specCode) {
		this.specCode = specCode;
	}
	public String getFinalFlag() {
		return finalFlag;
	}
	public void setFinalFlag(String finalFlag) {
		this.finalFlag = finalFlag;
	}
}
