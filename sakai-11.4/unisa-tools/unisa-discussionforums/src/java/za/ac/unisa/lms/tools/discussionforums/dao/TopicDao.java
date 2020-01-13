package za.ac.unisa.lms.tools.discussionforums.dao;

import java.util.List;

public interface TopicDao {

	
	public boolean insertTopic(final ForumTopicDetails forumTopicDetails);
	public boolean deleteTopic(final ForumTopicDetails forumTopicDetails,final String activityBy, final String ipAddress); 
	public void editTopicName(String topicName, int topicId); 
	public String getTopicName(Integer topicId) throws Exception;
	public String getTopicsCounter(Integer forumId, String siteId)throws Exception;
	public String getTopicLastPostUser(Integer forumId, Integer topicId,String type) throws Exception;
	public List<TopicRowMapper> getTopics(Integer forumId, String sortby, String sortorder);

}
