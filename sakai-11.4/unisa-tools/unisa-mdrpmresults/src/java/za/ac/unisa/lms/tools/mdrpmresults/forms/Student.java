package za.ac.unisa.lms.tools.mdrpmresults.forms;

public class Student {

	private String studentNumber;	
	private String name;
	private Qualification qualification = new Qualification();
	private AddressPH addressPH = new AddressPH();	
	private StudyUnit studyUnit = new StudyUnit();
	private String applSequenceNr;
	public static final String STUDENT_NUMBER = "studentNumber";
	public static final String STUDENT_NAME = "studentName";
	
	public String getStudentNumber() {
		return studentNumber;
	}

	public void setStudentNumber(String studentNumber) {
		this.studentNumber = studentNumber;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Qualification getQualification() {
		return qualification;
	}

	public void setQualification(Qualification qualification) {
		this.qualification = qualification;
	}

	public AddressPH getAddressPH() {
		return addressPH;
	}

	public void setAddressPH(AddressPH addressPH) {
		this.addressPH = addressPH;
	}
	
	public StudyUnit getStudyUnit() {
		return studyUnit;
	}
	public void setStudyUnit(StudyUnit studyUnit) {
		this.studyUnit = studyUnit;
	}
	public String getApplSequenceNr() {
		return applSequenceNr;
	}
	public void setApplSequenceNr(String applSequenceNr) {
		this.applSequenceNr = applSequenceNr;
	}
}


