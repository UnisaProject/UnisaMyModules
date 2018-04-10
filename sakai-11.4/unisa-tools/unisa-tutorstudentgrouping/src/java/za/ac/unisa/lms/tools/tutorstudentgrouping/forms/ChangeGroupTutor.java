package za.ac.unisa.lms.tools.tutorstudentgrouping.forms;

public class ChangeGroupTutor {
	private Tutor NewTutor;
	private TutorStudentGroup tutorStudentGroup;
	public Tutor getNewTutor() {
		return NewTutor;
	}
	public void setNewTutor(Tutor newTutor) {
		NewTutor = newTutor;
	}
	public TutorStudentGroup getTutorStudentGroup() {
		return tutorStudentGroup;
	}
	public void setTutorStudentGroup(TutorStudentGroup tutorStudentGroup) {
		this.tutorStudentGroup = tutorStudentGroup;
	}
}
