
package za.ac.unisa.lms.tools.discussionforums.service;

import za.ac.unisa.lms.tools.discussionforums.dao.*;
import za.ac.unisa.lms.tools.discussionforums.dao.impl.*;

public class TopicDaoService {

	public  static TopicDao topicDao;

	public static TopicDao getTopicDao() {
		if(topicDao == null){
			topicDao = new TopicDAOImpl();
		}
		return topicDao;
	}

	
	
	
}
