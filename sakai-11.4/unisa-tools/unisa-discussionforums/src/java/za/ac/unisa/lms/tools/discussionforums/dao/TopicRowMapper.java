package za.ac.unisa.lms.tools.discussionforums.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.sakaiproject.component.cover.ComponentManager;
import org.sakaiproject.user.api.User;
import org.sakaiproject.user.api.UserDirectoryService;
import org.springframework.jdbc.core.RowMapper;

import za.ac.unisa.lms.tools.discussionforums.service.TopicDaoService;
public class TopicRowMapper implements RowMapper{
	
	private UserDirectoryService userDirectoryService;
	public Object mapRow(ResultSet rs,int arg1) throws SQLException {
		//userDirectoryService = (UserDirectoryService) ComponentManager.get(UserDirectoryService.class);
		ForumTopicDetails forumTopicDetails = new ForumTopicDetails();
		OracleDAO dao = new OracleDAO();
		 TopicDao topicDAO = TopicDaoService.getTopicDao();
		StaffMssqlDao staffMsssqldao = new StaffMssqlDao("PERSDB");
		PosterDetails posterDetails = new PosterDetails();
		User user = null;
		String replyCount = "";
		String userId = "";
		String authorId = "";
		String posterId = "";
		forumTopicDetails.setTopicId(new Integer(rs.getString("Topic_Id")));
		forumTopicDetails.setForumId(new Integer(rs.getString("Forum_Id")));
		forumTopicDetails.setTopicTitle(rs.getString("Topic_Subject"));
		forumTopicDetails.setCreationDate(rs.getTimestamp("Creation_Date"));
		forumTopicDetails.setModificationDate(rs.getTimestamp("Modification_Date"));
		forumTopicDetails.setViewCounter(new Integer(rs.getString("View_Count")));
		forumTopicDetails.setLastPostDate(rs.getString("Last_Post_Date"));
		try{
			userId = topicDAO.getTopicLastPostUser(forumTopicDetails.getForumId(),forumTopicDetails.getTopicId(),"creator");
			if (userId.equalsIgnoreCase("admin")){
				authorId = "MyUnisa Administrator";
			} else if (userId.equalsIgnoreCase("NotAvailable")){
				authorId = "Not Available";
			} else {
				if (userId != null){
						//user = userDirectoryService.getUserByEid(userId);
					
						authorId = dao.getUserNames(userId);
					if (authorId != null){
						//authorId = user.getDisplayName();
					} else {
						authorId = "Unknown";
					}
				} else {
					authorId = "Not Available";
				}
			}
			forumTopicDetails.setTopicAuthor(authorId);
		//	replyCount = forumdao.getReplyCounter(forumTopicDetails.getForumId(),forumTopicDetails.getTopicId());
			replyCount= rs.getString("replycount");
			//forumTopicDetails.setLastPostDate(forumdao.getTopicLastPostDate(forumTopicDetails.getForumId(),forumTopicDetails.getTopicId()));
			posterId = topicDAO.getTopicLastPostUser(forumTopicDetails.getForumId(),forumTopicDetails.getTopicId(),"poster");
			forumTopicDetails.setUserId(userId);
			if (posterId.equalsIgnoreCase("admin")){
				authorId = "MyUnisa Administrator";
			} else if (posterId.equalsIgnoreCase("NotAvailable")){
				authorId = "Not Available";
			} else {
				if (posterId != null){
					    posterId = dao.getUserNames(posterId);
					//	user = userDirectoryService.getUserByEid(posterId);
					if (posterId != null){
						//posterId = user.getDisplayName();
					} else {
						posterId = "NotAvailable";
					}
				} else {
					posterId = "Not Available";
				}
			}
			forumTopicDetails.setLastPostUser(posterId);
		}catch (Exception ex) {
			userId = "NotAvailable";
			forumTopicDetails.setLastPostUser(userId);
			forumTopicDetails.setTopicAuthor(userId);
		}
		if((replyCount == null) || (replyCount.equals(""))) {
			replyCount = "0";
		}
		forumTopicDetails.setReplies(Integer.valueOf(replyCount));
		return forumTopicDetails;
	}

}