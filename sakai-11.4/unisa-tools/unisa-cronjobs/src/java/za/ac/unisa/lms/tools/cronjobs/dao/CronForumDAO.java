package za.ac.unisa.lms.tools.cronjobs.dao;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;

import za.ac.unisa.lms.db.SakaiDAO;

public class CronForumDAO extends SakaiDAO{
	
	public List<?> getSites() {
		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		String sql = "SELECT distinct Site_Id FROM uforum_forum ";
		List<?> records = jdt.queryForList(sql);
		
		return records;
	}
	
	public List<?> getSiteForum(String siteId) {
		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		String sql = "";
		sql = 	"select forum_id,forum_name " +
				"from UFORUM_FORUM " +
				"where Site_Id = '"+siteId+"'";
		List<?> records = jdt.queryForList(sql);
		
		return records;
	}
	
	public List<?> getSiteForumDetailed(String siteId, String forumId) {
		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		String sql = "";
		sql = 	"select fm.User_Id as User_Id,TO_CHAR(fm.Creation_Date,'YYYY-MM-DD HH24:MI') as Creation_Date,ff.Forum_Id, ff.Site_Id  " +
		"from UFORUM_FORUM ff, UFORUM_TOPIC ft, UFORUM_MESSAGE fm " +
		"where ff.Site_Id = '"+siteId+"' and ff.Forum_Id = ft.Forum_Id " +
		"and ft.Topic_Id = fm.Topic_Id and ft.Hide_Status = 'N' " +
		"and fm.Message_Id != (select min(fm1.Message_Id) " +
		"from UFORUM_FORUM ff1, UFORUM_TOPIC ft1, UFORUM_MESSAGE fm1 " +
		"where ff1.Site_Id = '"+siteId+"' and 	ff1.Forum_Id = ft1.Forum_Id and ff1.Forum_Id = " +forumId+
		" and ft1.Topic_Id = fm1.Topic_Id and ft1.Hide_Status='N') and " +
		"fm.Creation_Date = (select max(fm2.Creation_Date) " +
		"from UFORUM_FORUM ff2, UFORUM_TOPIC ft2, UFORUM_MESSAGE fm2 " +
		"where 	ff2.Site_Id = '"+siteId+"' and ff2.Forum_Id = ft2.Forum_Id " +
		"and ff2.Forum_Id = "+forumId+" and ft2.Topic_Id = fm2.Topic_Id and ft2.Hide_Status = 'N')";
		List<?> records = jdt.queryForList(sql);
		
		return records;
	}
	
	public List<?> getForumTopics(String forumId) {
		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		String sql = "";
	    sql = 	"select Topic_Id  " +
	    		"from UFORUM_TOPIC " +
	    		"where Forum_Id = "+forumId;
		List<?> records = jdt.queryForList(sql);
		
		return records;
	}	
	
	public List<?> getForumTopicDetails(String forumId, String topicId) {
		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		String sql = "";
	    sql = 	"select fm.User_Id as User_Id,TO_CHAR(fm.Creation_Date,'YYYY-MM-DD HH24:MI') as Creation_Date  " +
	    		"from UFORUM_TOPIC ft,UFORUM_MESSAGE fm " +
	    		"where ft.Forum_Id ="+forumId+" " +
	    		"and ft.Topic_Id = fm.Topic_Id and fm.Topic_Id= "+topicId+
	    		" and ft.Hide_Status = 'N' and fm.Creation_Date = " +
	    		"													(select max(fm1.Creation_Date) " +
	    		"													 from UFORUM_TOPIC ft1,UFORUM_MESSAGE fm1 "+ 
	    		"													 where ft1.Forum_Id ="+forumId+" " +
	    		"													 and ft1.Topic_Id = fm1.Topic_Id " +
	    		"													 and fm1.Topic_Id="+topicId+")";
		List<?> records = jdt.queryForList(sql);
		
		return records;
	}		
	
	public boolean updateForum(String lastPostUser, String lastPostDate,String forumId){
		String sql = "UPDATE uforum_forum SET Last_Post_User = '"+lastPostUser+"', Last_Post_Date = TO_DATE('"+lastPostDate+"','YYYY-MM-DD HH24:MI')" +
					 "WHERE Forum_Id = "+forumId;
		boolean updated = true;
		try{
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			jdt.update(sql);
			updated = true;
		} catch (Exception ex) {
			updated = false;
		}
		return updated;
	}
	
	public boolean updateTopic(String lastPostUser, String lastPostDate,String forumId,String topicId){
		String sql = "UPDATE uforum_topic SET Last_Post_User = '"+lastPostUser+"', Last_Post_Date = TO_DATE('"+lastPostDate+"','YYYY-MM-DD HH24:MI')" +
					 "WHERE Forum_Id = "+forumId +
					 "  AND Topic_Id = "+topicId;
		boolean updated = true;
		try{
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			jdt.update(sql);
			updated = true;
		} catch (Exception ex) {
			updated = false;
		}
		return updated;
	}	
}
