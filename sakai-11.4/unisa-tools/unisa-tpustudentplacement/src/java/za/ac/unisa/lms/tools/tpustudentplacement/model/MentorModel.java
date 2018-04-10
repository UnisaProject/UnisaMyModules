package za.ac.unisa.lms.tools.tpustudentplacement.model;

import za.ac.unisa.lms.tools.tpustudentplacement.dao.MentorDAO;

public class MentorModel {
	              private int provcode,districtcode, schoolCode;
	              private String districtName,schoolName,ProvinceName;
	              private int mentorCode;
	              private String title;
	              private String name;
	              private String surname;
	              private String initials;
	              private String countryCode;
	              private String occupation,inUseFlag;
	              private String trained;
	              private String emailAddress;
	              private String cellNumber;
	              private  String contactNumber;
	              private String phoneNumber;
	              private String faxNumber;
	              
				public String getCountryCode() {
					return countryCode;
				}
				public void setCountryCode(String countryCode) {
					this.countryCode = countryCode;
				}
				public int getProvcode() {
					return provcode;
				}
				public void setProvcode(int provcode) {
					this.provcode = provcode;
				}
				public int getDistrictcode() {
					return districtcode;
				}
				public void setDistrictcode(int districtcode) {
					this.districtcode = districtcode;
				}
				public int getSchoolCode() {
					return schoolCode;
				}
				public void setSchoolCode(int schoolCode) {
					this.schoolCode = schoolCode;
				}
				public String getName() {
					return name;
				}
				public void setName(String name) {
					this.name = name;
				}
				public String getSurname() {
					return surname;
				}
				public void setSurname(String surname) {
					this.surname = surname;
				}
				public String getInitials() {
					return initials;
				}
				public void setInitials(String initials) {
					this.initials = initials;
				}
				public String getTrained() {
					return trained;
				}
				public void setTrained(String trained) {
					this.trained = trained;
				}
				public int getMentorCode() {
					return mentorCode;
				}
				public void setMentorCode(int mentorCode) {
					this.mentorCode = mentorCode;
				}
				public String getOccupation() {
					return occupation;
				}
				public void setOccupation(String occupation) {
					this.occupation = occupation;
				}
				public String getInUseFlag() {
					return inUseFlag;
				}
				public void setInUseFlag(String inUseFlag) {
					this.inUseFlag = inUseFlag;
				}
				public String getTitle() {
					return title;
				}
				public void setTitle(String title) {
					this.title = title;
				}
				public String getEmailAddress() {
					return emailAddress;
				}
				public void setEmailAddress(String emailAddress) {
					this.emailAddress = emailAddress;
				}
				public String getCellNumber() {
					return cellNumber;
				}
				public void setCellNumber(String cellNumber) {
					this.cellNumber = cellNumber;
				}
				public String getContactNumber() {
					return contactNumber;
				}
				public void setContactNumber(String contactNumber) {
					this.contactNumber = contactNumber;
				}
				public String getPhoneNumber() {
					return phoneNumber;
				}
				public void setPhoneNumber(String phoneNumber) {
					this.phoneNumber = phoneNumber;
				}
				public String getFaxNumber() {
					return faxNumber;
				}
				public void setFaxNumber(String faxNumber) {
					this.faxNumber = faxNumber;
				}
				public String getDistrictName() {
					return districtName;
				}
				public void setDistrictName(String districtName) {
					this.districtName = districtName;
				}
				public String getSchoolName() {
					return schoolName;
				}
				public void setSchoolName(String schoolName) {
					this.schoolName = schoolName;
				}
				public String getProvinceName() {
					return ProvinceName;
				}
				public void setProvinceName(String provinceName) {
					ProvinceName = provinceName;
				}
				public void setData(MentorModel module){
   	                           setMentorCode(module.getMentorCode());
   	                           setCountryCode(module.getCountryCode());
                               setInitials(module.getInitials());
                               setInUseFlag(module.getInUseFlag());
                               setTitle(module.getTitle());
                               setName(module.getName());
                               setOccupation(module.getOccupation());
                               setProvcode(module.getProvcode());
                               setSchoolCode(module.getSchoolCode());
                               setSchoolName(module.getSchoolName());
                               setSurname(module.getSurname());
                               setTrained(module.getTrained());
                               setCellNumber(module.getCellNumber());
                               setPhoneNumber(module.getPhoneNumber());
                               setFaxNumber(module.getFaxNumber());
                               setEmailAddress(module.getEmailAddress());
             }
}
