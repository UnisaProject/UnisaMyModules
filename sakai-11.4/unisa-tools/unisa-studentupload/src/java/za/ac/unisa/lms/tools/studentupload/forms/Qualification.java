package za.ac.unisa.lms.tools.studentupload.forms;

public class Qualification {

	private String qualCode;
	private String qualDesc;
	private String specCode;
	private String specDesc;
	private String qualType; 		// F=formal S=non-formal
	private String underpostGrad1;	//U = undergrad, H = honors
	private String underpostGrad2;	//U = undergrad, H = honors
	private String qualKat;
	private String qualQualify;		//OK, CF, SF
	private String qualKATCODE;		//45

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
	public String getQualType() {
		return qualType;
	}
	public void setQualType(String qualType) {
		this.qualType = qualType;
	}
	public String getQualKat() {
		return qualKat;
	}
	public void setQualKat(String qualKat) {
		this.qualKat = qualKat;
	}
	public String getUnderpostGrad1() {
		return underpostGrad1;
	}
	public void setUnderpostGrad1(String underpostGrad1) {
		this.underpostGrad1 = underpostGrad1;
	}
	public String getUnderpostGrad2() {
		return underpostGrad2;
	}
	public void setUnderpostGrad2(String underpostGrad2) {
		this.underpostGrad2 = underpostGrad2;
	}
	public String getQualQualify() {
		return qualQualify;
	}
	public void setQualQualify(String qualQualify) {
		this.qualQualify = qualQualify;
	}
	public String getQualKATCODE() {
		return qualKATCODE;
	}
	public void setQualKATCODE(String qualKATCODE) {
		this.qualKATCODE = qualKATCODE;
	}
}
