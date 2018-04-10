package za.ac.unisa.lms.tools.honsadmission.forms;

public class SignOff {
	private Qualification qualification;	
	private String recommendation;
	private String recommendationDesc;
	private String recommendationComment;
	private String comment;
	private String date;
	private String user;
	private String level;
	private String referType;
	
	public String getRecommendationDesc() {
		return recommendationDesc;
	}
	public void setRecommendationDesc(String recommendationDesc) {
		this.recommendationDesc = recommendationDesc;
	}
	public Qualification getQualification() {
		return qualification;
	}
	public void setQualification(Qualification qualification) {
		this.qualification = qualification;
	}	
	public String getReferType() {
		return referType;
	}
	public void setReferType(String referType) {
		this.referType = referType;
	}
	public String getRecommendation() {
		return recommendation;
	}
	public void setRecommendation(String recommendation) {
		this.recommendation = recommendation;
	}
	public String getRecommendationComment() {
		return recommendationComment;
	}
	public void setRecommendationComment(String recommendationComment) {
		this.recommendationComment = recommendationComment;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	
	
	
}
