package za.ac.unisa.lms.tools.mdrpm.forms;

public class Qualification {

	private String code;
	private String description;
	private Speciality speciality = new Speciality();

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

	public Speciality getSpeciality() {
		return speciality;
	}

	public void setSpeciality(Speciality speciality) {
		this.speciality = speciality;
	}

	

}
