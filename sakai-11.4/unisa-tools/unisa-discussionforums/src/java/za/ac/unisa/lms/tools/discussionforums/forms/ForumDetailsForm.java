//Created by MyEclipse Struts
// XSL source (default): platform:/plugin/com.genuitec.eclipse.cross.easystruts.eclipse_3.9.210/xslt/JavaClass.xsl

package za.ac.unisa.lms.tools.discussionforums.forms;

import java.util.List;

import org.apache.struts.validator.ValidatorForm;

import za.ac.unisa.lms.tools.discussionforums.dao.Forum;

public class ForumDetailsForm extends ValidatorForm {

	private static final long serialVersionUID = 1L;
	private Integer forumId;
	private boolean editForum;
	private Forum forum;
	private List siteForums;
	private String tmpForumName;
	private Integer totalPages;
	private Integer pageNo;
	private boolean addForum;
	private boolean updateOwnForum;
	private boolean updateAnyForum;
	private String sortBy;
	private String sortOrder;
	private String sortIcon;
	private String title;
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSortIcon() {
		return sortIcon;
	}

	public void setSortIcon(String sortIcon) {
		this.sortIcon = sortIcon;
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

	public Integer getForumId() {
		return forumId;
	}

	public void setForumId(Integer forumId) {
		this.forumId = forumId;
	}

	public ForumDetailsForm(){
		super();
		forum = new Forum();
	}

	public Forum getForum() {
		return forum;
	}

	public void setForum(Forum forum) {
		this.forum = forum;
	}

	public List getSiteForums() {
		return siteForums;
	}

	public void setSiteForums(List siteForums) {
		this.siteForums = siteForums;
	}

	public boolean isEditForum() {
		return editForum;
	}

	public void setEditForum(boolean editForum) {
		this.editForum = editForum;
	}

	public String getTmpForumName() {
		return tmpForumName;
	}

	public void setTmpForumName(String tmpForumName) {
		this.tmpForumName = tmpForumName;
	}

	public Integer getPageNo() {
		return pageNo;
	}

	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}

	public Integer getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(Integer totalPages) {
		this.totalPages = totalPages;
	}

	public boolean isAddForum() {
		return addForum;
	}

	public void setAddForum(boolean addForum) {
		this.addForum = addForum;
	}

	public boolean isUpdateAnyForum() {
		return updateAnyForum;
	}

	public void setUpdateAnyForum(boolean updateAnyForum) {
		this.updateAnyForum = updateAnyForum;
	}

	public boolean isUpdateOwnForum() {
		return updateOwnForum;
	}

	public void setUpdateOwnForum(boolean updateOwnForum) {
		this.updateOwnForum = updateOwnForum;
	}


}