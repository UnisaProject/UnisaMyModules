package za.ac.unisa.lms.tools.discussionforums.dao;

public class Forum {
	private Integer forumId;
	private String forumName;
	private String forumDescription;
	private Integer forumTopicsCount;
	private Integer forumPosts;
	private String lastPostDate;
	private String creationDate;
	private String lastPoster;
	private String userId;
	private String siteId;
	private String userType;
	private String hideStatus;

	public String getForumDescription() {
		return forumDescription;
	}
	public void setForumDescription(String forumDescription) {
		this.forumDescription = forumDescription;
	}
	public Integer getForumId() {
		return forumId;
	}
	public void setForumId(Integer forumId) {
		this.forumId = forumId;
	}
	public String getForumName() {
		return forumName;
	}
	public void setForumName(String forumName) {
		this.forumName = forumName;
	}
	public Integer getForumPosts() {
		return forumPosts;
	}
	public void setForumPosts(Integer forumPosts) {
		this.forumPosts = forumPosts;
	}
	public Integer getForumTopicsCount() {
		return forumTopicsCount;
	}
	public void setForumTopicsCount(Integer forumTopicsCount) {
		this.forumTopicsCount = forumTopicsCount;
	}
	public String getLastPostDate() {
		return lastPostDate;
	}
	public void setLastPostDate(String lastPostDate) {
		this.lastPostDate = lastPostDate;
	}
	public String getLastPoster() {
		return lastPoster;
	}
	public void setLastPoster(String lastPoster) {
		this.lastPoster = lastPoster;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getSiteId() {
		return siteId;
	}
	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}
	public String getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	public String getHideStatus() {
		return hideStatus;
	}
	public void setHideStatus(String hideStatus) {
		this.hideStatus = hideStatus;
	}
}
