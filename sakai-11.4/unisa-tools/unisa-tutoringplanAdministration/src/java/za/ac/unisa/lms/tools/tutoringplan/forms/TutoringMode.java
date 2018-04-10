package za.ac.unisa.lms.tools.tutoringplan.forms;

public class TutoringMode {
	private String tutorMode;
	private String tutorModeDesc;
	private String optionality;
	private String groupChoice;
	private String tutorStuRatio;
	private String maxGroupsPerTutor;
	private String includeLectAsTutors;
	private String tutorContact;
	private String allocationCriteria;
	private  String groupingIndicator;
	
	public void setGroupingIndicator(String groupingIndicator){
        this.groupingIndicator=groupingIndicator;	
    }
    public String getGroupingIndicator(){
              return groupingIndicator;	
    }
	public String getTutorMode() {
		return tutorMode;
	}
	public void setTutorMode(String tutorMode) {
		this.tutorMode = tutorMode;
	}
	public String getTutorModeDesc() {
		return tutorModeDesc;
	}
	public void setTutorModeDesc(String tutorModeDesc) {
		this.tutorModeDesc = tutorModeDesc;
	}
	public String getOptionality() {
		return optionality;
	}
	public void setOptionality(String optionality) {
		this.optionality = optionality;
	}
	public String getGroupChoice() {
		return groupChoice;
	}
	public void setGroupChoice(String groupChoice) {
		this.groupChoice = groupChoice;
	}	
	public String getTutorStuRatio() {
		return tutorStuRatio;
	}
	public void setTutorStuRatio(String tutorStuRatio) {
		this.tutorStuRatio = tutorStuRatio;
	}
	public String getMaxGroupsPerTutor() {
		return maxGroupsPerTutor;
	}
	public void setMaxGroupsPerTutor(String maxGroupsPerTutor) {
		this.maxGroupsPerTutor = maxGroupsPerTutor;
	}
	public String getIncludeLectAsTutors() {
		return includeLectAsTutors;
	}
	public void setIncludeLectAsTutors(String includeLectAsTutors) {
		this.includeLectAsTutors = includeLectAsTutors;
	}
	public String getTutorContact() {
		return tutorContact;
	}
	public void setTutorContact(String tutorContact) {
		this.tutorContact = tutorContact;
	}
	public String getAllocationCriteria() {
		return allocationCriteria;
	}
	public void setAllocationCriteria(String allocationCriteria) {
		this.allocationCriteria = allocationCriteria;
	}
}
