package za.ac.unisa.lms.tools.discussionforums.service;

import za.ac.unisa.lms.tools.discussionforums.dao.*;
import za.ac.unisa.lms.tools.discussionforums.dao.impl.*;

public class MessageDaoService {

	public  static MessageDao messageDao;

	public static MessageDao getMessageDao() {
		if(messageDao == null){
			messageDao = new MessageDAOImpl();
		}
		return messageDao;
	}

	
	
	
}
