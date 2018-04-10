package za.ac.unisa.lms.tools.discussionforums.dao;

import java.util.List;

public interface ForumDao {
	public void insertForum(Forum forum);
	public void updateForum(Forum forum);
	public boolean deleteForum(final Forum forum, final String activityBy, final String ipAddress);
	public List<ForumRowMapper> getForumList(String siteId,String sortby,String sortorder);
	public Forum getForumContent(Integer forumId);
	public String getForumByName(String forumName,String siteId) throws Exception;
	public String getForumPostCounter(Integer forumId,String siteId) throws Exception;
	public String getForumLastPostUser(Integer forumId,String siteId) throws Exception;
	public String getForumsPerSiteCounter(String siteId) throws Exception ;
    public String makeJDBCCompatible(String convertString);
    public int getForumCount(String forumName,String siteId);

}
