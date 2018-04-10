package za.ac.unisa.lms.tools.tpustudentplacement.model;

import za.ac.unisa.lms.tools.tpustudentplacement.model.modelImpl.DistrictUI;

public class MentorFilteringData {
	    private String mentorFilterCountry;
		private Short mentorFilterProvince;
		private Short mentorFilterDistrict;
		private String mentorFilterDistrictValue;
		private Integer mentorFilterSchoolCode;
		private String mentorFilterSchoolValue;
		private String mentorDistrictFilter;
		private String mentorSchoolFilter;
		private String mentorFilterProvinceValue;
		private String mentorFilter;
		private String mentorIsTrained;
		private String mentorFilterLongDistrictValue;
	
		public Short getMentorFilterProvince() {
			return mentorFilterProvince;
		}
		public void setMentorFilterProvince(Short mentorFilterProvince) {
			this.mentorFilterProvince = mentorFilterProvince;
		}
		public String getMentorFilterDistrictValue() {
			return mentorFilterDistrictValue;
		}
		public void setMentorFilterDistrictValue(String mentorFilterDistrictValue) {
			this.mentorFilterDistrictValue = mentorFilterDistrictValue;
		}
		public Short getMentorFilterDistrict() {
			              return mentorFilterDistrict;
		}
		public void setMentorFilterDistrict(Short mentorFilterDistrict) {
			this.mentorFilterDistrict = mentorFilterDistrict;
		}
		public String getMentorFilterCountry() {
			return mentorFilterCountry;
		}
		public void setMentorFilterCountry(String mentorlFilterCountry) {
			this.mentorFilterCountry = mentorlFilterCountry;
		}
		public String getMentorIsTrained() {
			return mentorIsTrained;
		}
		public void setMentorIsTrained(String mentorIsTrained) {
			this.mentorIsTrained = mentorIsTrained;
		}
		public Integer getMentorFilterSchoolCode() {
			return mentorFilterSchoolCode;
		}
		public void setMentorFilterSchoolCode(Integer mentorFilterSchoolCode) {
			this.mentorFilterSchoolCode = mentorFilterSchoolCode;
		}
		public String getMentorFilter() {
			return mentorFilter;
		}
		public void setMentorFilter(String mentorFilter) {
			this.mentorFilter = mentorFilter;
		}
		public String getMentorSchoolFilter() {
			return mentorSchoolFilter;
		}
		public void setMentorSchoolFilter(String mentorSchoolFilter) {
			this.mentorSchoolFilter = mentorSchoolFilter;
		}
		public String getMentorDistrictFilter() {
			return mentorDistrictFilter;
		}
		public void setMentorDistrictFilter(String mentorDistrictFilter) {
			this.mentorDistrictFilter = mentorDistrictFilter;
		}
		public String getMentorFilterSchoolValue() {
			return mentorFilterSchoolValue;
		}
		public void setMentorFilterSchoolValue(String mentorFilterSchoolValue) {
			this.mentorFilterSchoolValue = mentorFilterSchoolValue;
		}
		public String getMentorFilterLongDistrictValue() {
			return mentorFilterLongDistrictValue;
		}
		public void setMentorFilterLongDistrictValue(String mentorFilterLongDistrictValue) throws Exception {
			this.mentorFilterLongDistrictValue = mentorFilterLongDistrictValue;
			 if (mentorFilterLongDistrictValue!=null && !mentorFilterLongDistrictValue.equalsIgnoreCase("")){
                   int index = mentorFilterLongDistrictValue.indexOf("-");
                   String district = mentorFilterLongDistrictValue.trim().substring(0, index);
                   mentorFilterDistrict=Short.parseShort(district);
                   DistrictUI districtUI =new DistrictUI (); 
                   mentorFilterDistrictValue=districtUI.getDistrict(mentorFilterDistrict).getDescription();
                   }
      }
		public String getMentorFilterProvinceValue() {
			return mentorFilterProvinceValue;
		}
		public void setMentorFilterProvinceValue(String mentorFilterProvinceValue) {
			this.mentorFilterProvinceValue = mentorFilterProvinceValue;
		}
		
}
