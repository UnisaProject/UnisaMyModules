package za.ac.unisa.lms.tools.tpustudentplacement.forms;

public class SupervisorListRecord {
	private Integer code;
	private String name;
	private String cellNumber;
	private String contractStart;
	private String contractEnd;
	private String contract;
	private String country;
	private String province;
	private String district;
	private String shortDistrict;
	private String studentsAllocated;
	private String studentsAllowed;
	private String emailAddress;
	private String surname;
	
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public String getEmailAddress() {
		return emailAddress;
	}
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	public String getStudentsAllocated(){
		return studentsAllocated;
	}
	public void  setStudentsAllocated(String studentsAllocated){
		     this.studentsAllocated=studentsAllocated;
	}
	public String getStudentsAllowed(){
		return studentsAllowed;
	}
	public void setStudentsAllowed(String studentsAllowed){
		    this.studentsAllowed=studentsAllowed;
	}
	public String getShortDistrict() {		
		if (this.district!=null && this.district.length() > 40){
			shortDistrict = this.district.substring(0,40) + "   ...more";
		}else{
			shortDistrict = this.district;
		}		
		return shortDistrict;
	}
	public void setShortDistrict(String shortDistrict) {
		this.shortDistrict = shortDistrict;
	}
	public String getDistrict() {
		return district;
	}
	public void setDistrict(String district) {
		this.district = district;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getContractStart() {
		return contractStart;
	}
	public void setContractStart(String contractStart) {
		this.contractStart = contractStart;
	}
	public String getContractEnd() {
		return contractEnd;
	}
	public void setContract(String contract) {
		this.contract = contract;
	}
	public String getContract() {
		return contract;
	}
	public void setContractEnd(String contractEnd) {
		this.contractEnd = contractEnd;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCellNumber() {
		return cellNumber;
	}
	public void setCellNumber(String cellNumber) {
		this.cellNumber = cellNumber;
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
	
	

}
