package za.ac.unisa.lms.tools.assmarkerreallocation.forms;

import java.util.List;

import za.ac.unisa.lms.domain.general.Person;

	public class MarkerModel {
		
		private Person person;
		private String markPercentage;
		private String prevMarkPercentage;
		private String status;
		private String departmentDesc;
		private String role;
		private List  markingLanguageList;
		
		public  List  getMarkingLanguageList(){
			return markingLanguageList;
		}
		public void setMarkingLanguageList(List markingLanguageList){
		                this.markingLanguageList=markingLanguageList;
		}
        private String[] chosenMarkingLanguageList;
		
		public String[]  getChosenMarkingLanguageList(){
			return chosenMarkingLanguageList;
		}
		public void setChosenMarkingLanguageList(String[] chosenMarkingLanguageList){
		                this.chosenMarkingLanguageList=chosenMarkingLanguageList;
		}
		public String getRole() {
			return role;
		}
		public void setRole(String role) {
			this.role = role;
		}
		public String getDepartmentDesc() {
			return departmentDesc;
		}
		public void setDepartmentDesc(String department) {
			this.departmentDesc = department;
		}
		public String getStatus() {
			return status;
		}
		public void setStatus(String status) {
			this.status = status;
		}
		public String getMarkPercentage() {
			return markPercentage;
		}
		public void setMarkPercentage(String markPercentage) {
			this.markPercentage = markPercentage;
		}
		public String getPrevMarkPercentage() {
			return prevMarkPercentage;
		}
		public void setPrevMarkPercentage(String prevMarkPercentage) {
			this.prevMarkPercentage = prevMarkPercentage;
		}
		public Person getPerson() {
			return person;
		}
		public void setPerson(Person person) {
			this.person = person;
		}
}
