package za.ac.unisa.lms.tools.tpustudentplacement.forms;

public class SchoolListRecord {
	private Integer code;
	private String name;
	private String type;
	private String category;
	private String inUse;
	private String country;
	private String province;
	private String town;
	private Short provinceCode;
	private String district;
	private Short districtCode;
	
	
	public String getTown(){
		return town;
	}
	public void setTown(String town){
		this.town=town;
	}
	public Short getProvinceCode() {
		return provinceCode;
	}
	public void setProvinceCode(Short provinceCode) {
		this.provinceCode = provinceCode;
	}
	public Short getDistrictCode() {
		return districtCode;
	}
	public void setDistrictCode(Short districtCode) {
		this.districtCode = districtCode;
	}
	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getInUse() {
		return inUse;
	}
	public void setInUse(String inUse) {
		this.inUse = inUse;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getDistrict() {
		return district;
	}
	public void setDistrict(String district) {
		this.district = district;
	}
	
	
}
