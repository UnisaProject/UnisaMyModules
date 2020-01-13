package za.ac.unisa.lms.tools.cronjobs.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.text.html.HTMLDocument.HTMLReader.IsindexAction;

import org.apache.commons.collections.map.ListOrderedMap;
import org.springframework.jdbc.core.JdbcTemplate;
import za.ac.unisa.lms.db.SakaiDAO;
import org.sakaiproject.component.cover.ServerConfigurationService;



public class BlogUserTitleUpdateDao extends SakaiDAO{
	public void blogUpdate() throws Exception {	
		
		
		
		String sql = "select ID,OWNERID,TITLE from blogwow_blog where TITLE ='myUnisa User'";
		//defines student system database schema in sakai properties e.g stu.dba.schema=dev in order for dev tables to point to production tables
		String dbschema=ServerConfigurationService.getString("stu.dba.schema");
		
		ArrayList results = new ArrayList();
		try{
			JdbcTemplate jdt = new JdbcTemplate(super.getDataSource());
			List<?> queryList = jdt.queryForList(sql);
			Iterator<?> i = queryList.iterator();
			while (i.hasNext()) {
				String surname=" ";
				String name=" ";
				String initial=" ";
				String eId;
				boolean eid=false;
				ListOrderedMap data = (ListOrderedMap) i.next();
				String blogId=data.get("ID").toString(); 
				String title=data.get("TITLE").toString(); //title is the user initial and surname
				String userid = data.get("OWNERID").toString();                
				String sql_eid="select EID from sakai_user_id_map where user_id = '"+userid+"'";
				try{
					eId = querySingleValue(sql_eid,"EID");
				} catch (Exception ex) {
					throw new Exception("BlogUserTitleUpdateDao : blogUpdate Error occurred / "+ ex,ex);
				}
				try {
					int eID = Integer.parseInt(eId);                                                               
				}catch(NumberFormatException nFE) {
					eid=true;
				}
				if(eid==false){      
					//select from stu@dev {for students} : for instance  sakaidev is linked to studentsystem, no need to import student system
					String sql1 = "select Initials,SURNAME from stu@"+dbschema +
					"     where nr="+eId;
					try{
						initial = querySingleValue(sql1,"Initials");
						surname = querySingleValue(sql1,"SURNAME");
						name=initial+" "+surname;
					} catch (Exception ex) {
						throw new Exception("BlogUserTitleUpdateDao : blogUpdate Error occurred / "+ ex,ex);
					}
				}else{
					eId=eId.toUpperCase();
				    	if(name.length()<=1 || name.equals("")){
				    	//select from staff@dev {for staff} : for instance  sakaidev is linked to studentsystem, no need to import student system	
						String sql2 = "select Initials,surname  from staff@"+dbschema +
						" where novell_user_id ='"+eId+"'" +
						" and (resign_date > sysdate" +
						" or resign_date is null)" +
						" and (persno < 52000000 or persno > 52999999)"; 
						try{
							initial = querySingleValue(sql2,"Initials");
							surname = querySingleValue(sql2,"surname");
							name=initial+" "+surname;
							} catch (Exception ex) {
							throw new Exception("BlogUserIdUpdateDao : updateBlogUserID Error occurred / "+ ex,ex);
						}
					}
					if(name.length()<=1){
						//select from usr@dev {for contractors}: for instance  sakaidev is linked to studentsystem, no need to import student system
						String sql3 = "select nvl(NAME,' ') NAME  from usr@"+dbschema +
						" where novell_user_code ='"+eId+"'";
						try{
							name = querySingleValue(sql3,"NAME");
						} catch (Exception ex) {
							throw new Exception("BlogUserTitleUpdateDao : blogUpdate Error occurred / "+ ex,ex);
						}
					}
					if(name.length()<=1){
						name="myUnisa User";	
					}
				} 
				String blogUpdate = "update  blogwow_blog set TITLE='"+name+"'" +
				" where id='"+blogId+"' and TITLE ='myUnisa User' and OWNERID='"+userid+"'";              
				try{
					JdbcTemplate jdt1 = new JdbcTemplate(super.getDataSource());
					jdt1.update(blogUpdate);
				} catch (Exception ex) {
					throw new Exception(" BlogUserTitleUpdateDao: blogUpdate  error occurred"+ ex,ex);                
				}
			}
		} catch (Exception ex) {
			//errorMessage = "MyUnisaStatsDAO: getUserLoginDaily: Error occurred / "+ ex;
            throw new Exception("BlogUserTitleUpdate: Error occurred / "+ ex,ex);
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

