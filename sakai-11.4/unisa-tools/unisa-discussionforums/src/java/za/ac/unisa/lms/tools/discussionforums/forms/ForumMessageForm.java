//Created by MyEclipse Struts
// XSL source (default): platform:/plugin/com.genuitec.eclipse.cross.easystruts.eclipse_3.9.210/xslt/JavaClass.xsl

package za.ac.unisa.lms.tools.discussionforums.forms;

import java.util.List;

import org.apache.struts.upload.FormFile;
import org.apache.struts.validator.ValidatorForm;

import za.ac.unisa.lms.tools.discussionforums.dao.ForumMessage;

/**
 * MyEclipse Struts
 * Creation date: 06-26-2006
 *
 * XDoclet definition:
 * @struts:form name="forumMessageForm"
 */
public class ForumMessageForm extends ValidatorForm {

	private static final long serialVersionUID = 1L;
	private List topicMessages;
	private ForumMessage forumMessage;
	private Integer totalPages;
	private Integer pageNo;
	private String messageTopic;
	private Integer topicId;
	private boolean topicViewed;
	private String forumName;
	private String hidden;
	private boolean replyAddable;
	private boolean anyReplyDeledable;
	private String msgRecords;
	private int start;
	private int end;
	private int numberOfItems;
	private List allMessages;
	private String sortBy;
	private String sortOrder;
	private String sortIcon;
	private String addressLink;
	private String uploadPath;
	private String uploadList;
	private String upload;
	private FormFile theFile;
	public String uploadFileStr = "";
	public String fileData;
	private String filename;
	private String cancel;
	private String forumId;
	private String forumDesc;
	private boolean buttonFinish;
	private String firstTopicMessage;
	
	public String getFirstTopicMessage() {
		return firstTopicMessage;
	}
	public void setFirstTopicMessage(String firstTopicMessage) {
		this.firstTopicMessage = firstTopicMessage;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public boolean isButtonFinish() {
		return buttonFinish;
	}
	public void setButtonFinish(boolean buttonFinish) {
		this.buttonFinish = buttonFinish;
	}
	public Integer getTopicId() {
		return topicId;
	}
	public void setTopicId(Integer topicId) {
		this.topicId = topicId;
	}
	public ForumMessageForm(){
		super();
		forumMessage = new ForumMessage();
	}
	public ForumMessage getForumMessage() {
		return forumMessage;
	}
	public void setForumMessage(ForumMessage forumMessage) {
		this.forumMessage = forumMessage;
	}
	public List getTopicMessages() {
		return topicMessages;
	}
	public void setTopicMessages(List topicMessages) {
		this.topicMessages = topicMessages;
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
	public String getMessageTopic() {
		return messageTopic;
	}
	public void setMessageTopic(String messageTopic) {
		this.messageTopic = messageTopic;
	}
	public boolean isTopicViewed() {
		return topicViewed;
	}
	public void setTopicViewed(boolean topicViewed) {
		this.topicViewed = topicViewed;
	}
	public String getForumName() {
		return forumName;
	}
	public void setForumName(String forumName) {
		this.forumName = forumName;
	}
	public String getHidden() {
		return hidden;
	}
	public void setHidden(String hidden) {
		this.hidden = hidden;
	}
	public boolean isAnyReplyDeledable() {
		return anyReplyDeledable;
	}
	public void setAnyReplyDeledable(boolean anyReplyDeledable) {
		this.anyReplyDeledable = anyReplyDeledable;
	}
	public boolean isReplyAddable() {
		return replyAddable;
	}
	public void setReplyAddable(boolean replyAddable) {
		this.replyAddable = replyAddable;
	}
	public String getMsgRecords() {
		return msgRecords;
	}
	public void setMsgRecords(String msgRecords) {
		this.msgRecords = msgRecords;
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
	public List getAllMessages() {
		return allMessages;
	}
	public void setallMessages(List allMessages) {
		this.allMessages = allMessages;
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
	public String getAddressLink() {
		return addressLink;
	}
	public void setAddressLink(String addressLink) {
		this.addressLink = addressLink;
	}
	public void setAllMessages(List allMessages) {
		this.allMessages = allMessages;
	}
	public String getUploadPath() {
		return uploadPath;
	}
	public void setUploadPath(String uploadPath) {
		this.uploadPath = uploadPath;
	}
	public String getUpload() {
		return upload;
	}
	public void setUpload(String upload) {
		this.upload = upload;
	}
	public String getUploadList() {
		return uploadList;
	}
	public void setUploadList(String uploadList) {
		this.uploadList = uploadList;
	}
	public FormFile getTheFile() {
		return theFile;
	}
	public void setTheFile(FormFile theFile) {
		this.theFile = theFile;
	}
	public String getUploadFileStr() {
		return uploadFileStr;
	}
	public void setUploadFileStr(String uploadFileStr) {
		this.uploadFileStr = uploadFileStr;
	}
	public String getFileData() {
		return fileData;
	}
	public void setFileData(String fileData) {
		this.fileData = fileData;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public String getCancel() {
		return cancel;
	}
	public void setCancel(String cancel) {
		this.cancel = cancel;
	}
	public String getForumId() {
		return forumId;
	}
	public void setForumId(String forumId) {
		this.forumId = forumId;
	}	

	public String getForumDesc() {
		return forumDesc;
	}
	public void setForumDesc(String forumDesc) {
		this.forumDesc = forumDesc;
	}	
}

