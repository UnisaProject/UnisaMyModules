package za.ac.unisa.lms.tools.studentemail.dao;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.mail.internet.InternetAddress;

import org.apache.commons.collections.map.ListOrderedMap;
import org.sakaiproject.component.cover.ComponentManager;
import org.sakaiproject.email.api.EmailService;
import org.sakaiproject.tool.api.Session;
import org.sakaiproject.tool.api.SessionManager;
import org.sakaiproject.tool.api.ToolManager;
import org.sakaiproject.user.api.User;
import org.sakaiproject.user.api.UserDirectoryService;
import org.sakaiproject.component.cover.ServerConfigurationService;
import org.springframework.jdbc.core.JdbcTemplate;

import za.ac.unisa.lms.db.SakaiDAO;
import za.ac.unisa.lms.tools.studentemail.Otherl;
import za.ac.unisa.lms.tools.studentemail.Priml;



public class SakaiQueryDAO extends SakaiDAO{
	
	private EmailService emailService;
	private SessionManager sessionManager;
	private ToolManager toolManager;
	private UserDirectoryService userDirectoryService;
	
	public boolean checkEmail(String site) throws Exception {
		//toolManager = (ToolManager) ComponentManager.get(ToolManager.class);
		String sql = "Select VALUE from SAKAI_SITE_PROPERTY where SITE_ID = '"+site+"'" +
				" and NAME = 'contact-email'";
		boolean found = false;

		try {
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List<?> queryList = jdt.queryForList(sql);
			Iterator<?> k = queryList.iterator();
			if (k.hasNext()){
				ListOrderedMap data = (ListOrderedMap) k.next();
				if((null != data.get("VALUE")) && ("" != data.get("VALUE"))) {
					found = true;
				} else {
					clearNullContactRecord(site);
					found = false;
				}
			}
		}catch (Exception ex) {
		       throw new Exception("SakaiQueryDAO : checkEmail : Error Occurred : / "+ ex,ex);
		}
		return found;
	}
	
	public String getEmail(String site) throws Exception{
		//toolManager = (ToolManager) ComponentManager.get(ToolManager.class);
		String sql = "Select VALUE from SAKAI_SITE_PROPERTY where SITE_ID = '"+site+"'" +
		" and NAME = 'contact-email'";
		String email="";
		try{
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List<?> queryList = jdt.queryForList(sql);
			Iterator<?> i = queryList.iterator();
			while (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				email = data.get("VALUE").toString();
			}
		}  catch (Exception ex) {
			       throw new Exception("SakaiQueryQueryDAO : getEmail : Error Occurred / "+ ex,ex);
		}
		return email;
	}
	
	private void clearNullContactRecord(String site) {
		String sql = "delete from SAKAI_SITE_PROPERTY where SITE_ID = '"+site+"'" +
				" and NAME = 'contact-email' AND VALUE IS NULL";
		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		jdt.update(sql);
	}
	
	public void setEmail(String choice, String emailaddres,String course, int year, short period) throws Exception {
		toolManager = (ToolManager) ComponentManager.get(ToolManager.class);
		sessionManager = (SessionManager) ComponentManager.get(SessionManager.class);
		userDirectoryService = (UserDirectoryService) ComponentManager.get(UserDirectoryService.class);
		emailService = (EmailService) ComponentManager.get(EmailService.class);
		
		String sql ="";
		if ("1".equals(choice)){
			sql = "delete from SAKAI_SITE_PROPERTY where SITE_ID = '"+toolManager.getCurrentPlacement().getContext()+"'" +
			" and NAME = 'contact-email'";
		} else if ("2".equals(choice)){
			boolean found=checkEmail(toolManager.getCurrentPlacement().getContext());
			if (found) {
				sql = "Update SAKAI_SITE_PROPERTY set value = '"+toolManager.getCurrentPlacement().getContext()+"@unisa.ac.za' where SITE_ID = '"+toolManager.getCurrentPlacement().getContext()+"'" +
				" and NAME = 'contact-email'";
			} else {
				sql = "Insert into SAKAI_SITE_PROPERTY(SITE_ID, NAME, VALUE) values('"+toolManager.getCurrentPlacement().getContext()+"','contact-email','"+toolManager.getCurrentPlacement().getContext()+"@unisa.ac.za')";
			}
			// Get user

			Session currentSession = sessionManager.getCurrentSession();
			String userId = currentSession.getUserId();
			User user = null;
			if (userId != null) {
				user = userDirectoryService.getUser(userId);
			}

			StudentQueryDao db = new StudentQueryDao();
			Priml priml = new Priml();
			ArrayList<?> other = new ArrayList();
			try {
				priml = db.getPrimaryLeturer(course,year,period);
				other = db.getOtherLecturers(course,year,period);
			} catch (Exception ex) {
				throw ex;
			}
			String subject ="New Mailbox for :"+ toolManager.getCurrentPlacement().getContext();
			String message = "User :"+currentSession.getUserEid()+"- "+user.getFirstName()+" "+user.getLastName()+"\n" +
					"\n" +
					"Has requested a new mailbox for "+toolManager.getCurrentPlacement().getContext()+"\n" +
					"The email address must be "+toolManager.getCurrentPlacement().getContext()+"@unisa.ac.za\n"+
					"\n\n" +
					"The user who must have proxy for this is :\n" +
					"\n" +
					"Primary Lecturer:\n" +
					"Personnel number\tName\t\t\tWork Tel\tEmail Address\n"+
					priml.getPersno()+"\t\t"+priml.getName()+"\t"+priml.getWtel()+"\t\t"+ priml.getEmail()+"\n\n" +
					"Secondary Lecturer(s):\n";


			for (int i=0;i<other.size();i++){
				Otherl otherl = (Otherl) (other.get(i));
				message +="Personnel number\tName\t\t\tWork Tel\tEmail Address\n";
				message += otherl.getPersno()+"\t\t"+otherl.getName()+"\t"+otherl.getWtel()+"\t\t"+otherl.getEmail()+"\n";
			}

			message+="\n\n Thank you";
			String tmpEmailFrom = user.getEmail();
			InternetAddress toEmail = new InternetAddress(ServerConfigurationService.getString("ict-helpdesk.email"));
			InternetAddress iaTo[] = new InternetAddress[1];
			iaTo[0] = toEmail;
			InternetAddress iaHeaderTo[] = new InternetAddress[1];
			iaHeaderTo[0] = toEmail;
			InternetAddress iaReplyTo[] = new InternetAddress[1];
			iaReplyTo[0] = new InternetAddress(tmpEmailFrom);
			emailService.sendMail(iaReplyTo[0],iaTo,subject,message,iaHeaderTo,iaReplyTo,null);
		} else if ("3".equals(choice)){
			boolean found=checkEmail(toolManager.getCurrentPlacement().getContext());
			if (found) {
				sql = "Update SAKAI_SITE_PROPERTY set VALUE = '"+emailaddres+"' where SITE_ID = '"+toolManager.getCurrentPlacement().getContext()+"'" +
					" and NAME = 'contact-email'";
			} else {
				sql = "Insert into SAKAI_SITE_PROPERTY(SITE_ID, NAME, VALUE) values('"+toolManager.getCurrentPlacement().getContext()+"','contact-email','"+emailaddres+"')";
			}
		}
		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		jdt.update(sql);
	}
}
