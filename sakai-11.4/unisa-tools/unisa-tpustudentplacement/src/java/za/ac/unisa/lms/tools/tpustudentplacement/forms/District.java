package za.ac.unisa.lms.tools.tpustudentplacement.forms;

public class District {
	private Short code;
	private String description;
	private String inUse;
	private Province province;
	
	public Short getCode() {
		return code;
	}
	public void setCode(Short code) {
		this.code = code;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}	
	public String getInUse() {
		return inUse;
	}
	public void setInUse(String inUse) {
		this.inUse = inUse;
	}
	public Province getProvince() {
		return province;
	}
	public void setProvince(Province province) {
		this.province = province;
	}
}
