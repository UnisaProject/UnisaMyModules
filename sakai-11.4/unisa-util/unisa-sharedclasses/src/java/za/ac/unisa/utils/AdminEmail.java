package za.ac.unisa.utils;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.sakaiproject.component.cover.ServerConfigurationService;
import org.sakaiproject.email.cover.EmailService;
import org.sakaiproject.user.cover.UserDirectoryService;
import org.sakaiproject.user.api.User;

public class AdminEmail {

	public static Log log = LogFactory.getLog(AdminEmail.class);

	public static void sendAdminEmail(String subject, String message) {

		try {

			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			PrintWriter pw = new PrintWriter(baos);

			User admin = UserDirectoryService.getUser("admin");
			String serverName = ServerConfigurationService.getServerName();
			String from = "sakai@"+serverName;

			pw.println();

			pw.println("Server ID: "+ServerConfigurationService.getServerId());
			pw.println("Server URL: "+ServerConfigurationService.getServerUrl());

			pw.flush();
			message += baos.toString();

			EmailService.send(from, admin.getEmail(), subject, message, null, null, null);
			log.info("Sent a mail to "+admin.getEmail()+" from "+from+" with the subject: "+subject);
		} catch (Exception e) {
			log.error("Unhandled exception: "+e+" "+e.getMessage());
			e.printStackTrace();
		}
	}

}
