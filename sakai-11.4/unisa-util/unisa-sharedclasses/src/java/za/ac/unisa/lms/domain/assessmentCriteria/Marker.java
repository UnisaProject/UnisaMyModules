package za.ac.unisa.lms.domain.assessmentCriteria;

import za.ac.unisa.lms.domain.general.Person;
import java.util.List;

public class Marker {
	
	private Person person;
	private String markPercentage;
	private String status;
	private String departmentDesc;
	private List listMarkLanguages;
	private String[] languageSelection;
	
	public String[] getLanguageSelection() {
		return languageSelection;
	}
	public void setLanguageSelection(String[] languageSelection) {
		this.languageSelection = languageSelection;
	}	
	public List getListMarkLanguages() {
		return listMarkLanguages;
	}
	public void setListMarkLanguages(List listMarkLanguages) {
		this.listMarkLanguages = listMarkLanguages;
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
	public Person getPerson() {
		return person;
	}
	public void setPerson(Person person) {
		this.person = person;
	}
}

