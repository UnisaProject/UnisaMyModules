package za.ac.unisa.lms.tools.cronjobs.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.map.ListOrderedMap;
import org.springframework.jdbc.core.JdbcTemplate;

import za.ac.unisa.lms.db.StudentSystemDAO;

public class SiteStatsDao extends StudentSystemDAO {

	public void updateUserIdEvents() throws Exception {
		
		//String sql = "select  user_id userid from sst_events@SAKHDEV where length(user_id)<10 and length(user_id)>2 ";
		String sql = "select  user_id userid from sst_events@SAKHPROD where length(user_id)<10 and length(user_id)>2 ";
		ArrayList results = new ArrayList();
		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		List queryList;
		queryList = jdt.queryForList(sql);
		Iterator i = queryList.iterator();
		while (i.hasNext()) {
			ListOrderedMap data = (ListOrderedMap) i.next();
			String userid = data.get("userid").toString(); 
			/*String sql1 = "update sst_events@SAKHDEV set  user_id =(select USER_ID "
					+ "from sakai_user_id_map@sakaidev where eid= '"+userid+"') where  user_id='" + userid + "'";*/
			String sql2="select user_id AS userId from sakai_user_id_map@sakaiprd where eid= '"+userid+"'";	
			try{
			String user = this.querySingleValue(sql2,"userId");
			if(user==null ||(user.length()==0)){
				//System.out.println("in null ");
			}else{
			String sql1 = "update sst_events@SAKHPROD set  user_id =(select user_id "
				+ " from sakai_user_id_map@sakaiprd where eid= '"+userid+"') where  user_id='" + userid + "'";
			try {
				JdbcTemplate jdt1 = new JdbcTemplate(getDataSource());
				jdt1.update(sql1);
			} catch (Exception ex) {
				throw new Exception(
						"SiteStatsDao: updateUserId (update): Error occurred /"+ ex, ex);
			}
			}
			}catch (Exception ex) {
				throw new Exception(
						"SiteStatsDao: updateUserId (update): Error occurred /"+ ex, ex);
			}
		
		}
	}
	public void updateUserIdResources() throws Exception {
		
		//String sql = "select  user_id userid from sst_events@SAKHDEV where length(user_id)<10 and length(user_id)>2 ";
		String sql = "select  user_id userid from sst_resources@SAKHPROD where length(user_id)<10 and length(user_id)>2 ";
		ArrayList results = new ArrayList();
		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		List queryList;
		queryList = jdt.queryForList(sql);
		Iterator i = queryList.iterator();
		while (i.hasNext()) {
			ListOrderedMap data = (ListOrderedMap) i.next();
			String userid = data.get("userid").toString(); 
			/*String sql1 = "update sst_events@SAKHDEV set  user_id =(select USER_ID "
					+ "from sakai_user_id_map@sakaidev where eid= '"+userid+"') where  user_id='" + userid + "'";*/
			String sql2="select user_id AS userId from sakai_user_id_map@sakaiprd where eid= '"+userid+"'";	
			try{
			String user = this.querySingleValue(sql2,"userId");
			if(user==null ||(user.length()==0)){
				//System.out.println("in null ");
			}else{
			String sql1 = "update sst_resources@SAKHPROD set  user_id =(select user_id "
				+ " from sakai_user_id_map@sakaiprd where eid= '"+userid+"') where  user_id='" + userid + "'";
			try {
				JdbcTemplate jdt1 = new JdbcTemplate(getDataSource());
				jdt1.update(sql1);
			} catch (Exception ex) {
				throw new Exception(
						"SiteStatsDao: updateUserIdResources (update): Error occurred /"+ ex, ex);
			}
			}
			}catch (Exception ex) {
				throw new Exception(
						"SiteStatsDao: updateUserIdResources (update): Error occurred /"+ ex, ex);
			}
		
		}
	}
	private String querySingleValue(String query, String field){

		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
    	List queryList;
    	String result = "";

 	   queryList = jdt.queryForList(query);
 	   Iterator i = queryList.iterator();
 	   i = queryList.iterator();
 	   if (i.hasNext()) {
			 ListOrderedMap data = (ListOrderedMap) i.next();
			 if (data.get(field) == null){
			 } else {
				 result = data.get(field).toString();
			 }
 	   }
 	   return result;
	}

}
