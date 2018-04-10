package za.ac.unisa.lms.tools.discussionforums.dao;

import java.sql.Timestamp;

public class ForumTopicDetails {
	private Integer topicId;
	private Integer forumId;
	private String siteId;
	private String topicTitle;
	private Integer replies;
	private String lastPostDate;
	private Timestamp creationDate;
	private Timestamp modificationDate;
	private String lastPostUser;
	private String topicAuthor;
	private Integer viewCounter;
	private String topicMessage;
	private String hideStatus;
	private String userId;
	private String authorId;

	public String getTopicMessage() {
		return topicMessage;
	}
	public void setTopicMessage(String topicMessage) {
		this.topicMessage = topicMessage;
	}
	public Integer getTopicId() {
		return topicId;
	}
	public void setTopicId(Integer topicId) {
		this.topicId = topicId;
	}

	public String getTopicTitle() {
		return topicTitle;
	}
	public void setTopicTitle(String topicTitle) {
		this.topicTitle = topicTitle;
	}
	public Integer getReplies() {
		return replies;
	}
	public void setReplies(Integer replies) {
		this.replies = replies;
	}
	public Integer getViewCounter() {
		return viewCounter;
	}
	public void setViewCounter(Integer viewCounter) {
		this.viewCounter = viewCounter;
	}
	public String getLastPostDate() {
		return lastPostDate;
	}
	public void setLastPostDate(String lastPostDate) {
		this.lastPostDate = lastPostDate;
	}
	public String getLastPostUser() {
		return lastPostUser;
	}
	public void setLastPostUser(String lastPostUser) {
		this.lastPostUser = lastPostUser;
	}
	public String getTopicAuthor() {
		return topicAuthor;
	}
	public void setTopicAuthor(String topicAuthor) {
		this.topicAuthor = topicAuthor;
	}
	public Timestamp getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Timestamp creationDate) {
		this.creationDate = creationDate;
	}
	public Timestamp getModificationDate() {
		return modificationDate;
	}
	public void setModificationDate(Timestamp modificationDate) {
		this.modificationDate = modificationDate;
	}
	public Integer getForumId() {
		return forumId;
	}
	public void setForumId(Integer forumId) {
		this.forumId = forumId;
	}
	public String getSiteId() {
		return siteId;
	}
	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}
	public String getHideStatus() {
		return hideStatus;
	}
	public void setHideStatus(String hideStatus) {
		this.hideStatus = hideStatus;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getAuthorId() {
		return authorId;
	}
	public void setAuthorId(String authorId) {
		this.authorId = authorId;
	}
}
