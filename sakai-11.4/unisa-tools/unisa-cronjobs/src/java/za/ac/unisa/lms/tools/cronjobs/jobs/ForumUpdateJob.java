package za.ac.unisa.lms.tools.cronjobs.jobs;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.map.ListOrderedMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;
import org.sakaiproject.component.cover.ComponentManager;
import org.sakaiproject.user.api.User;
import org.sakaiproject.user.api.UserNotDefinedException;
import org.sakaiproject.user.api.UserDirectoryService;

import za.ac.unisa.lms.tools.cronjobs.dao.CronForumDAO;
import za.ac.unisa.lms.tools.cronjobs.locks.SingleClusterInstanceJob;

public class ForumUpdateJob extends SingleClusterInstanceJob implements StatefulJob, OutputGeneratingJob{
	
	private UserDirectoryService userDirectoryService;
	
	private ByteArrayOutputStream outputStream;
	private PrintWriter output;
	CronForumDAO forumdao = new CronForumDAO();
	
	public void executeLocked(JobExecutionContext context)throws JobExecutionException 
	{
		userDirectoryService = (UserDirectoryService) ComponentManager.get(UserDirectoryService.class);
		List<?> sites = forumdao.getSites();
        String[] temp = null;
        String [] temp1 = null;
        for(int i=0; i < sites.size(); i++){        	
            temp = sites.get(i).toString().split("=");
            temp1 = temp[1].split("}");
            List<?> siteForums = forumdao.getSiteForum(temp1[0]);
            Iterator<?> sf = siteForums.iterator();
            while(sf.hasNext()){
            	ListOrderedMap forums = (ListOrderedMap) sf.next();
            	List<?> forumDetails = forumdao.getSiteForumDetailed(temp1[0], forums.get("Forum_Id").toString());
            	Iterator<?> fd = forumDetails.iterator();
            	User user = null;
            	String lastPostUserName = "";
            	String lastPostDate = "";
            	if (fd.hasNext()){            		
            		ListOrderedMap forumUSer = (ListOrderedMap) fd.next();
            		try {
						user = userDirectoryService.getUserByEid(forumUSer.get("User_Id").toString());
						lastPostUserName = user.getDisplayName();
						lastPostDate = forumUSer.get("Creation_Date").toString();
					} catch (UserNotDefinedException e) {
						lastPostUserName = "";
						//e.printStackTrace();
					}
					if (forumdao.updateForum(lastPostUserName, lastPostDate,forums.get("Forum_Id").toString())){
						System.out.println("SUCCESS"); 
					} else {
						System.out.println("FAILURE");
					}
            	}
            	List<?> topics = forumdao.getForumTopics(forums.get("Forum_Id").toString());
            	Iterator<?> tpc = topics.iterator();

            	while (tpc.hasNext()){
            		ListOrderedMap topicid = (ListOrderedMap) tpc.next();
            		List<?> topicDetails = forumdao.getForumTopicDetails(forums.get("Forum_Id").toString(), topicid.get("Topic_Id").toString());
            		Iterator<?> tpcd = topicDetails.iterator();
                 	String topicLastPostUserName = "";
                	String topicLastPostDate = "";
            		if (tpcd.hasNext()){
            			ListOrderedMap tpcdetails = (ListOrderedMap) tpcd.next();
            			try {
    						user = userDirectoryService.getUserByEid(tpcdetails.get("User_Id").toString());
    						topicLastPostUserName = user.getDisplayName();
    						topicLastPostDate = tpcdetails.get("Creation_Date").toString();
    					} catch (UserNotDefinedException e) {
    						topicLastPostDate = "";
    						//e.printStackTrace();
    					}
    					
    					if (forumdao.updateTopic(topicLastPostUserName, topicLastPostDate, forums.get("Forum_Id").toString(), topicid.get("Topic_Id").toString())){
    						System.out.println("TOPIC SUCCESS"); 
    					} else {
    						System.out.println("TOPIC FAILURE");
    					}
            		}
            	}
            }                         
        }

	}
	
	public String getOutput() {
		if (output != null) {
			output.flush();
			return outputStream.toString();
		}
		return null;
	}	

}
