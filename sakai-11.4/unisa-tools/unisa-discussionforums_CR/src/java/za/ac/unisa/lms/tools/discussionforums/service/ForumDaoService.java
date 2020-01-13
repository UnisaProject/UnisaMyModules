package za.ac.unisa.lms.tools.discussionforums.service;

import za.ac.unisa.lms.tools.discussionforums.dao.*;
import za.ac.unisa.lms.tools.discussionforums.dao.impl.ForumDAOImpl;

public class ForumDaoService {

	public  static IForumDao forumDao;

	public static IForumDao getForumDao() {
		if(forumDao == null){
			forumDao = new ForumDAOImpl();
		}
		return forumDao;
	}

	
	
	
}
