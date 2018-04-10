package za.ac.unisa.lms.tools.discussionforums.dao;

import java.util.List;

public interface MessageDao {
	public boolean insertMessage(final ForumMessage forumMessage);
	public ForumMessage getTopicPosting(Integer topicId);
	public boolean deleteMessage(final ForumMessage forumMessage, final String activityBy, final String ipAddress) throws Exception;
	public String getInitialMessage(int topicId) throws Exception;
	public ForumMessage getMessageDetail(Integer messageId);
	public List<MessageRowMapper> getMessageList(Integer topicId);
	public String initailMessage(ForumMessage forumMessage) throws Exception;
}
