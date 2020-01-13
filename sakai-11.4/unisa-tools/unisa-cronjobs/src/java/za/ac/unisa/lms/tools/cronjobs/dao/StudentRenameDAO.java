package za.ac.unisa.lms.tools.cronjobs.dao;

import org.springframework.jdbc.core.JdbcTemplate;

public class StudentRenameDAO extends SakaiDAO {

	public void renameUser(String oldName, String newName) {
		JdbcTemplate jt = new JdbcTemplate(getDataSource());

		String sql1 = "Update JOIN_ACTIVATION SET USER_ID = SUBSTRING(USER_ID,2) where USER_ID = '" + oldName + "'";
		String sql2 = "Update SAKAI_SESSION SET SESSION_USER = SUBSTRING(SESSION_USER,2) where SESSION_USER = '" + oldName + "'";
		String sql3 = "Update SAKAI_SITE SET SITE_ID = CONCAT(\"~\", SUBSTRING(SITE_ID,3)) where SUBSTRING(SITE_ID,2) = '" + oldName + "'";
		String sql4 = "Update SAKAI_SITE_PAGE SET SITE_ID = CONCAT(\"~\", SUBSTRING(SITE_ID,3)) where SUBSTRING(SITE_ID,2) = '" + oldName + "'";
		String sql5 = "Update SAKAI_SITE_PAGE_PROPERTY SET SITE_ID = CONCAT(\"~\", SUBSTRING(SITE_ID,3)) where SUBSTRING(SITE_ID,2) = '" + oldName + "'";
		String sql6 = "Update SAKAI_SITE_PROPERTY SET SITE_ID = CONCAT(\"~\", SUBSTRING(SITE_ID,3)) where SUBSTRING(SITE_ID,2) = '" + oldName + "'";
		String sql7 = "Update SAKAI_SITE_TOOL SET SITE_ID = CONCAT(\"~\", SUBSTRING(SITE_ID,3)) where SUBSTRING(SITE_ID,2) = '" + oldName + "'";
		String sql8 = "Update SAKAI_SITE_TOOL_PROPERTY SET SITE_ID = CONCAT(\"~\", SUBSTRING(SITE_ID,3)) where SUBSTRING(SITE_ID,2) = '" + oldName + "'";
		String sql9 = "Update SAKAI_SITE_USER SET USER_ID = SUBSTRING(USER_ID,2) WHERE USER_ID = '" + oldName + "'";
		String sql10 = "Update SAKAI_SITE_USER SET SITE_ID = CONCAT(\"~\", SUBSTRING(SITE_ID,3)) where SUBSTRING(SITE_ID,2) = '" + oldName + "'";
		String sql11 = "Delete FROM CALENDAR_CALENDAR WHERE CALENDAR_ID like '%"+oldName+"%'";
		String sql12 = "Update CALENDAR_EVENT SET CALENDAR_ID = CONCAT(\"/calendar/calendar/~\", "+ newName +" ,\"/main\") where CALENDAR_ID like '%"+oldName+"%'";
		String sql13 = "Update CLASSLIST_SETTINGS SET USER_ID = SUBSTRING(USER_ID,2) WHERE USER_ID = '" + oldName + "'";
		jt.update(sql1);
		jt.update(sql2);
		jt.update(sql3);
		jt.update(sql4);
		jt.update(sql5);
		jt.update(sql6);
		jt.update(sql7);
		jt.update(sql8);
		jt.update(sql9);
		jt.update(sql10);
		jt.execute(sql11);
		jt.update(sql12);
		jt.update(sql13);
	}

	public void removeOldUser(String oldName) {
		JdbcTemplate jt = new JdbcTemplate(getDataSource());

		String sql1 = "delete from JOIN_ACTIVATION where USER_ID = '" + oldName + "'";
		String sql2 = "delete from SAKAI_SESSION where SESSION_USER = '" + oldName + "'";
		String sql3 = "delete from SAKAI_SITE where SUBSTRING(SITE_ID,2) = '" + oldName + "'";
		String sql4 = "delete from SAKAI_SITE_PAGE where SUBSTRING(SITE_ID,2) = '" + oldName + "'";
		String sql5 = "delete from SAKAI_SITE_PAGE_PROPERTY where SUBSTRING(SITE_ID,2) = '" + oldName + "'";
		String sql6 = "delete from SAKAI_SITE_PROPERTY where SUBSTRING(SITE_ID,2) = '" + oldName + "'";
		String sql7 = "delete from SAKAI_SITE_TOOL where SUBSTRING(SITE_ID,2) = '" + oldName + "'";
		String sql8 = "delete from SAKAI_SITE_TOOL_PROPERTY where SUBSTRING(SITE_ID,2) = '" + oldName + "'";
		String sql9 = "delete from SAKAI_SITE_USER where USER_ID = '" + oldName + "'";
		String sql10 = "delete from SAKAI_SITE_USER where SUBSTRING(SITE_ID,2) = '" + oldName + "'";
		String sql11 = "delete FROM CALENDAR_CALENDAR WHERE CALENDAR_ID like '%"+oldName+"%'";
		String sql12 = "delete from CALENDAR_EVENT where CALENDAR_ID like '%"+oldName+"%'";
		String sql13 = "delete from CLASSLIST_SETTINGS where USER_ID = '" + oldName + "'";
		jt.update(sql1);
		jt.update(sql2);
		jt.update(sql3);
		jt.update(sql4);
		jt.update(sql5);
		jt.update(sql6);
		jt.update(sql7);
		jt.update(sql8);
		jt.update(sql9);
		jt.update(sql10);
		jt.execute(sql11);
		jt.update(sql12);
		jt.update(sql13);
	}

	/**
	 * This method executes a query and returns a String value as result.
	 * An empty String value is returned if the query returns no results.
	 *
	 * @param query       The query to be executed on the database
	 * @param field		  The field that is queried on the database
	 * method written by: E Penzhorn
	*/
	/*private String querySingleValue(String query, String field){

		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
    	List queryList;
    	String result = "";

 	   queryList = jdt.queryForList(query);
 	   Iterator i = queryList.iterator();
 	   i = queryList.iterator();
 	   if (i.hasNext()) {
			 HashMap data = (HashMap) i.next();
			 if (data.get(field) == null){
			 } else {
				 result = data.get(field).toString();
			 }
 	   }
 	   return result;
	}	*/

}
