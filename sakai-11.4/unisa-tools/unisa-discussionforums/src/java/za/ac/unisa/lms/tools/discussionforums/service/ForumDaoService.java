package za.ac.unisa.lms.tools.discussionforums.service;

import za.ac.unisa.lms.tools.discussionforums.dao.*;
import za.ac.unisa.lms.tools.discussionforums.dao.impl.*;

public class ForumDaoService {

	public  static ForumDao forumDao;

	public static ForumDao getForumDao() {
		if(forumDao == null){
			forumDao = new ForumDAOImpl();
		}
		return forumDao;
	}

	
	
	
}
