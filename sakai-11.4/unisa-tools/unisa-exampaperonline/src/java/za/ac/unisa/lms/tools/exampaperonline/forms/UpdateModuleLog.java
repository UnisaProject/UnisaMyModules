package za.ac.unisa.lms.tools.exampaperonline.forms;

public class UpdateModuleLog {
	private String studyUnit;
	private Integer seqNr;
	private String type;
	private String statusAction;
	private String xtlogAction;
	private String xtlogeAction;
	
	public String getXtlogAction() {
		return xtlogAction;
	}
	public void setXtlogAction(String xtlogAction) {
		this.xtlogAction = xtlogAction;
	}
	public String getXtlogeAction() {
		return xtlogeAction;
	}
	public void setXtlogeAction(String xtlogeAction) {
		this.xtlogeAction = xtlogeAction;
	}
	public String getStatusAction() {
		return statusAction;
	}
	public void setStatusAction(String statusAction) {
		this.statusAction = statusAction;
	}
	public String getStudyUnit() {
		return studyUnit;
	}
	public void setStudyUnit(String studyUnit) {
		this.studyUnit = studyUnit;
	}
	public Integer getSeqNr() {
		return seqNr;
	}
	public void setSeqNr(Integer seqNr) {
		this.seqNr = seqNr;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	
	
}
