package org.sakaiproject.discussionforums.api.model;

import java.util.List;
import java.util.Vector;
import org.sakaiproject.discussionforums.api.model.Forum;
import org.sakaiproject.discussionforums.api.model.ForumTopicDetails;
import org.sakaiproject.discussionforums.api.model.ForumMessage;

import lombok.Getter;
import lombok.Setter;


public class ForumTopicsForm {
	
	private static final long serialVersionUID = 1L;
	private List topics;
	private ForumTopicDetails forumTopicDetails;
	private ForumMessage forumMessage;
	private String topicForumName;
	private String topicForumDescription;
	private Integer totalPages;
	private Integer pageNo;
	private Integer forumId;
	private Long topicId;
	private boolean topicAddable;
	private boolean topicDeletable;
	private String records;
	private int start;
	private int end;
	private int numberOfItems;
	private List allTopics;
	private String sortBy;
	private String sortOrder;
	private String sortIcon;
	private int editTopic;
	private String topicname;
	public String getTopicname() {
		return topicname;
	}
	public void setTopicname(String topicname) {
		this.topicname = topicname;
	}
	public int getEditTopic() {
		return editTopic;
	}
	public void setEditTopic(int editTopic) {
		this.editTopic = editTopic;
	}
	public Long getTopicId() {
		return topicId;
	}
	public void setTopicId(Long topicId) {
		this.topicId = topicId;
	}
	public Integer getForumId() {
		return forumId;
	}
	public void setForumId(Integer forumId) {
		this.forumId = forumId;
	}
	public ForumTopicsForm(){
		super();
		forumTopicDetails = new ForumTopicDetails();
	}
	public List getTopics() {
		return topics;
	}

	public void setTopics(List topics) {
		this.topics = topics;
	}
	public ForumTopicDetails getForumTopicDetails() {
		return forumTopicDetails;
	}
	public void setForumTopicDetails(ForumTopicDetails forumTopicDetails) {
		this.forumTopicDetails = forumTopicDetails;
	}
	
	public ForumMessage getForumMessage() {
		return forumMessage;
	}
	public void setForumMessage(ForumMessage forumMessage) {
		this.forumMessage = forumMessage;
	}
	
	public String getTopicForumName() {
		return topicForumName;
	}
	public void setTopicForumName(String topicForumName) {
		this.topicForumName = topicForumName;
	}
	public Integer getTotalPages() {
		return totalPages;
	}
	public void setTotalPages(Integer totalPages) {
		this.totalPages = totalPages;
	}
	public Integer getPageNo() {
		return pageNo;
	}
	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}
	public String getTopicForumDescription() {
		return topicForumDescription;
	}
	public void setTopicForumDesciption(String topicForumDescription) {
		this.topicForumDescription = topicForumDescription;
	}
	public boolean isTopicAddable() {
		return topicAddable;
	}
	public void setTopicAddable(boolean topicAddable) {
		this.topicAddable = topicAddable;
	}
	public boolean isTopicDeletable() {
		return topicDeletable;
	}
	public void setTopicDeletable(boolean topicDeletable) {
		this.topicDeletable = topicDeletable;
	}
	public String getRecords() {
		return records;
	}

	public void setRecords(String records) {
		this.records = records;
	}
	public int getStart() {
		return start;
	}
	public void setStart(int start) {
		this.start = start;
	}
	public int getEnd() {
		return end;
	}
	public void setEnd(int end) {
		this.end = end;
	}
	public int getNumberOfItems() {
		return numberOfItems;
	}
	public void setNumberOfItems(int numberOfItems) {
		this.numberOfItems = numberOfItems;
	}
	public List getAllTopics() {
		return allTopics;
	}
	public void setAllTopics(List allTopics) {
		this.allTopics = allTopics;
	}
	public String getSortBy() {
		return sortBy;
	}
	public void setSortBy(String sortBy) {
		this.sortBy = sortBy;
	}
	public String getSortOrder() {
		return sortOrder;
	}
	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}
	public String getSortIcon() {
		return sortIcon;
	}
	public void setSortIcon(String sortIcon) {
		this.sortIcon = sortIcon;
	}
}
