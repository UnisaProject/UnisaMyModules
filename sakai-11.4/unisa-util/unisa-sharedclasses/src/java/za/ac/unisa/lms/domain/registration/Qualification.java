package za.ac.unisa.lms.domain.registration;

public class Qualification {

	private String qualCode;
	private String qualDesc;
	private String qualType; // U = undergrad, H = honors
	// Qualification Speciality
	private Speciality speciality;

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
	public String getQualType() {
		return qualType;
	}
	public Speciality getSpeciality() {
		return speciality;
	}
	public void setSpeciality(Speciality speciality) {
		this.speciality = speciality;
	}
	public void setQualType(String qualType) {
		this.qualType = qualType;
	}


}
