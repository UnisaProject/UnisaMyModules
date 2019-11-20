package za.ac.unisa.lms.domain.general;

import java.util.List;
import za.ac.unisa.lms.domain.general.Person;

public class Department {
	private String code;
	private String description;
	private Person cod;
	private List actingCodList;
	private String college;
	private String school;
	private Person dean;
	private List deputyDeanList;
	private List deputyDirectorList;
	private Person schoolDirector;
	
	public Person getSchoolDirector() {
		return schoolDirector;
	}
	public void setSchoolDirector(Person schoolDirector) {
		this.schoolDirector = schoolDirector;
	}
	public List getDeputyDirectorList() {
		return deputyDirectorList;
	}
	public void setDeputyDirectorList(List deputyDirectorList) {
		this.deputyDirectorList = deputyDirectorList;
	}
	public Person getDean() {
		return dean;
	}
	public void setDean(Person dean) {
		this.dean = dean;
	}	
	public List getDeputyDeanList() {
		return deputyDeanList;
	}
	public void setDeputyDeanList(List deputyDeanList) {
		this.deputyDeanList = deputyDeanList;
	}
	public String getCollege() {
		return college;
	}
	public void setCollege(String college) {
		this.college = college;
	}
	public String getSchool() {
		return school;
	}
	public void setSchool(String school) {
		this.school = school;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public List getActingCodList() {
		return actingCodList;
	}
	public void setActingCodList(List actingCodList) {
		this.actingCodList = actingCodList;
	}
	public Person getCod() {
		return cod;
	}
	public void setCod(Person cod) {
		this.cod = cod;
	}
}

