package za.ac.unisa.lms.tools.discussionforums.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.sakaiproject.component.cover.ComponentManager;
import org.sakaiproject.user.api.User;
import org.sakaiproject.user.api.UserDirectoryService;
import org.springframework.jdbc.core.RowMapper;

public class MessageRowMapper implements RowMapper{
	private UserDirectoryService userDirectoryService;
	public Object mapRow(ResultSet rs,int arg1) throws SQLException {
		//userDirectoryService = (UserDirectoryService) ComponentManager.get(UserDirectoryService.class);
		ForumMessage forumMessage = new ForumMessage();
		OracleDAO dao = new OracleDAO();
		User user = null;
		String userId = "";
		forumMessage.setMessageId(new Integer(rs.getString("Message_Id")));
		forumMessage.setTopicId(new Integer(rs.getString("Topic_Id")));
		forumMessage.setMessage(rs.getString("Content"));
		forumMessage.setMessageDate(rs.getString("Creation_Date"));
		userId =rs.getString("User_Id");
		
	
		forumMessage.setUserId(userId);
		forumMessage.setUserType(rs.getString("User_Identifier"));
		forumMessage.setAttachment(rs.getString("Msg_Url"));
		forumMessage.setFileType(rs.getString("File_Type"));
		try{
			
			if (userId.equalsIgnoreCase("")){
				userId = "NotAvailable";
			} else if (userId.equalsIgnoreCase("admin")){
				userId = "myUnisa Administrator";
			} else if (!(userId.equalsIgnoreCase("NotAvailable")) && !(userId.equalsIgnoreCase("admin"))){
				if (userId != null) {
					//user = userDirectoryService.getUserByEid(userId);
					//userId = user.getDisplayName();
					userId = dao.getUserNames(userId);
				} else {
					userId = "NotAvailable";
				}
			}
		} catch (Exception ex){
			userId = "NotAvailable";
		}
		forumMessage.setAuthor(userId);
		return forumMessage;
	}
}
