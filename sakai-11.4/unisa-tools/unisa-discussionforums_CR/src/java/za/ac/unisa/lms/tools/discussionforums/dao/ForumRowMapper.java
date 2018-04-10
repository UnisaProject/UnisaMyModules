package za.ac.unisa.lms.tools.discussionforums.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.sakaiproject.component.cover.ComponentManager;
import org.sakaiproject.user.api.User;
import org.sakaiproject.user.api.UserDirectoryService;
import org.springframework.jdbc.core.RowMapper;

import za.ac.unisa.lms.tools.discussionforums.dao.impl.ForumDAOImpl;

public class ForumRowMapper implements RowMapper {
  //  private UserDirectoryService userDirectoryService; 
	public Object mapRow(ResultSet rs,int arg1) throws SQLException {
		//userDirectoryService = (UserDirectoryService) ComponentManager.get(UserDirectoryService.class);
		Forum forum = new Forum();
		ForumDAOImpl forumdao = new ForumDAOImpl();
		TopicDAO topicDAO = new TopicDAO();
		String topicTotals = "";
		String messageTotals = "";
		Integer totalPosts = null;
		String lastPostUser = "";
		forum.setForumId(new Integer(rs.getString("Forum_Id")));
		forum.setForumName(rs.getString("Forum_Name").replaceAll("''", "'"));
		forum.setForumDescription(rs.getString("Description"));
		forum.setUserId(rs.getString("User_id"));
		forum.setCreationDate(rs.getString("Creation_Date"));
		forum.setSiteId(rs.getString("Site_Id"));
		forum.setLastPostDate(rs.getString("Last_Post_Date"));
		lastPostUser = rs.getString("Last_Post_User");
		User user = null;
		try{
			topicTotals = (String) topicDAO.getTopicsCounter(forum.getForumId(),forum.getSiteId());
			messageTotals = (String) forumdao.getForumPostCounter(forum.getForumId(),forum.getSiteId());
			totalPosts = new Integer((Integer.parseInt(messageTotals) - Integer.parseInt(topicTotals)));
			lastPostUser = forumdao.getForumLastPostUser(forum.getForumId(),forum.getSiteId());
			if (lastPostUser.equalsIgnoreCase("")){
				lastPostUser = "No Posting";
			} else if (lastPostUser == null){
				lastPostUser = "No Posting";
			}else {
				if (lastPostUser != null) {
					//user = userDirectoryService.getUserByEid(lastPostUser);
					//lastPostUser = user.getDisplayName();
					OracleDAO dao = new OracleDAO();
					lastPostUser = dao.getUserNames(lastPostUser);
					
				} else {
					lastPostUser = "No Posting";
				}
			}
		}catch (Exception ex) {
			System.out.println("ERROR in ForumRowMapper "+ex);
			lastPostUser = forum.getUserId();
			
			System.out.println("Forum Last Poster "+lastPostUser);
		}

		if (topicTotals.equalsIgnoreCase("0")){
			forum.setForumTopicsCount(Integer.valueOf("0"));
		} else {
			forum.setForumTopicsCount(Integer.valueOf(topicTotals));
		}
		forum.setForumPosts(totalPosts);
		forum.setLastPoster(lastPostUser);
		return forum;
	}
}
