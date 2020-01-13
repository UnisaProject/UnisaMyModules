package org.sakaiproject.james;

import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Vector;

import javax.mail.Address;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.avalon.cornerstone.services.threads.ThreadManager;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.mailet.Mail;
import org.apache.mailet.MailAddress;
import org.sakaiproject.authz.api.AuthzGroupService;
import org.sakaiproject.authz.api.Role;
import org.sakaiproject.component.cover.ComponentManager;
import org.sakaiproject.component.cover.ServerConfigurationService;
import org.sakaiproject.entity.api.EntityManager;
import org.sakaiproject.event.api.UsageSessionService;
import org.sakaiproject.exception.IdUnusedException;
import org.sakaiproject.james.dao.UnisaEmailLogDAO;
import org.sakaiproject.mailarchive.cover.MailArchiveService;

import org.sakaiproject.site.api.SiteService;
import org.sakaiproject.thread_local.api.ThreadLocalManager;
import org.sakaiproject.time.api.TimeService;
import org.sakaiproject.user.api.User;
import org.sakaiproject.user.api.UserDirectoryService;
import org.sakaiproject.user.api.UserNotDefinedException;

import org.sakaiproject.util.StringUtil;

import za.ac.unisa.lms.providers.api.AdProvisioner;

public class UnisaMailet extends SakaiMailet {
	
	private UserDirectoryService userDirectoryService;
	private AuthzGroupService authzGroupService;
	private EntityManager entityManager;
	private UsageSessionService usageSessionService;
	private SiteService siteService;
	private ThreadLocalManager threadLocalManager;
	private TimeService timeService;
	
	

	private static Log M_log = LogFactory.getLog(UnisaMailet.class);
	private UnisaEmailLogDAO elogger = new UnisaEmailLogDAO();

	public UnisaMailet() {
		super();
		// TODO Auto-generated constructor stub
	}

	// Condition:The site doesn't have an email archive turned on
	public final String EMAILOFF = "Your message cannot be delivered because the site you are emailing"
			+ " does not have the email feature turned on. Please contact the site"
			+ " owner to ask about enabling this feature on the site." + "\n\n"
			+ "If you have further questions about this feature, please email "
			+ ServerConfigurationService.getString("mail.support", "");

	// Condition:You don't have a Sakai account - you aren't in the system at all.
	public final String SENDERUNKNOWN = "Your message cannot be delivered because "
			+ ServerConfigurationService.getString("ui.service", "Sakai")
			+ " does not recognize your email address. If you are "
			+ "a site participant, be sure you are using the email address associated with your account" + " on the system. "
			+ "\n\n" + "If you believe your email should be accepted, please login to "+ ServerConfigurationService.getString("ui.service", "Sakai")
			+" and update your email address. If you have "
			+ "further questions about this feature, please contact " + ServerConfigurationService.getString("mail.support", "");

	public final String SENDERFORBIDDEN = "Your message cannot be delivered because "
		+ ServerConfigurationService.getString("ui.service", "Sakai")
		+ " does not recognize your email address as authorized to send this email. If you believe your email should be accepted,"
		+ " please contact " + ServerConfigurationService.getString("mail.support", "");

	public final String NOTHREAD = "Your message cannot be delivered because "
		+ ServerConfigurationService.getString("ui.service", "Sakai")
		+ " only allows replies to student-initiated messages. It appears as though you are attempting to initiate a new message to a student.";

	public final String SUBJECTUNKNOWN = "Your message cannot be delivered because "
		+ " you have not specified the subject/site name. If you are sending to a student, you must specify the subject/site name. "
		+ " By logging onto my.unisa.ac.za you will be able to retrieve the correct format of the recipient's email address."
		+ " For assistance lease contact " + ServerConfigurationService.getString("mail.support", "");

	// Condition: The site is not existing.
	public final String errorMsg_III = "Your message cannot be delivered because the address you are trying to send to is unknown. " + "\n\n"
			+ "If you have further questions about this feature, please email "
			+ ServerConfigurationService.getString("mail.support", "");

	// Condition: The sender doesn't have permission to send to the system
	// (you are not a participant in the site, or you are a participant w/o the right permission)
	public final String NOTMEMBEROFSITE = "Your message cannot be delivered because you are not recognized as a member of this site. "
		+ " If you are registered for courses from 2009 onwards, you should be using your free myLife email account to send to this site. "
		+ " By using your own myLife email account, the risk of someone assuming your identity is reduced considerably. "
		+ " To find out more about myLife at Unisa, visit http://mylife.unisa.ac.za "
		+ " If you have further questions about this feature, please contact "
		+ ServerConfigurationService.getString("mail.support", "");

	public final String RCPTNOTEXIST = "Your message cannot be delivered because the recipient's email address is invalid. "
		+ "If you have further questions about this feature, please contact "
		+ ServerConfigurationService.getString("mail.support", "");

	public final String SITENOTEXIST = "Your message cannot be delivered because the recipient's email address refers to a site(course) that does not exist. "
		+ "If you have further questions about this feature, please contact "
		+ ServerConfigurationService.getString("mail.support", "");

	public final String NOCONTACT = "Your message cannot be delivered because the no contact email address has been determined for this subject. "
		+ "If you have further questions about this feature, please contact "
		+ ServerConfigurationService.getString("mail.support", "");

	public final String errorMsg_VII = "Your message cannot be delivered because you have not specified the site/subject you wish to send from. "
		+ "Please include as the start of your email subject field, for example: [COS101S] "
		+ "If you have further questions about this feature, please contact "
		+ ServerConfigurationService.getString("mail.support", "");

	public final String STUDENTHIDDEN = "Your message cannot be delivered because the student's myUnisa profile is set to not receive email. "
		+ "If you have further questions about this feature, please contact "
		+ ServerConfigurationService.getString("mail.support", "");

	public final String BLOCKPMASTER = "Administrators have block postmaster from initiating messages to users at this domain. Your message cannot be delivered. "
		+ ServerConfigurationService.getString("mail.support", "");

	public static final int RECIPIENT = 1;
	public static final int SITE_NAME = 2;
	public static final int THREAD_ID = 3;

	public void init() throws MessagingException
	{
		M_log.info(this+".init()");
		M_log.info(this+": comms email set to "+ServerConfigurationService.getString("noReplyEmailFrom"));
		M_log.info(this+": server-admin email set to "+ServerConfigurationService.getString("server-admin.email"));
	}

	/**
	 * Called when leaving.
	 */
	public void destroy()
	{
		M_log.info(this + ".destroy()");

		super.destroy();

	} // destroy

	public boolean isStudentAcceptMail(String id) {
		return true;
	}

	/**
	 * Process incoming mail.
	 *
	 * @param mail
	 *        ...
	 */
	@SuppressWarnings("deprecation")
	public void service(Mail mail) throws MessagingException
	{
		userDirectoryService = (UserDirectoryService) ComponentManager.get(UserDirectoryService.class);
		authzGroupService = (AuthzGroupService) ComponentManager.get(AuthzGroupService.class);
		entityManager = (EntityManager) ComponentManager.get(EntityManager.class);
		usageSessionService = (UsageSessionService) ComponentManager.get(UsageSessionService.class);
		siteService = (SiteService) ComponentManager.get(SiteService.class);
		threadLocalManager = (ThreadLocalManager) ComponentManager.get(ThreadLocalManager.class);
		timeService = (TimeService) ComponentManager.get(TimeService.class);
		// get the postmaster user
		User postmaster = null;
		try
		{
			postmaster = userDirectoryService.getUserByEid(POSTMASTER);
		}
		catch (UserNotDefinedException e)
		{
			M_log.error(this + ".service: no postmaster retrieved from UserDirectoryService. Can't start session. Not sending email.");
			//mail.setState(Mail.GHOST);
			return;
		}

		try
		{
			usageSessionService.startSession(postmaster.getEid(), "127.0.0.1", "smtp");
			M_log.info(this+": startSession for postmaster");

			MimeMessage msg = mail.getMessage();
			String id = msg.getMessageID();
			boolean isDeliveryNotification = false;
			// If the content type refers to a "report" of some kind, treat it as a delivery report/notification.
			if(msg.getContentType().indexOf("report") >= 0) {
				isDeliveryNotification = true;
			}

			Address[] fromAddresses = msg.getFrom();
			String from = null;
			String fromAddr = null;
			if ((fromAddresses != null) && (fromAddresses.length == 1))
			{
				from = fromAddresses[0].toString();
				if (fromAddresses[0] instanceof InternetAddress)
				{
					fromAddr = ((InternetAddress) (fromAddresses[0])).getAddress();
				}
			}
			else
			{
				from = mail.getSender().toString();
				fromAddr = mail.getSender().toInternetAddress().getAddress();
			}

			Collection to = mail.getRecipients();

			Date sent = msg.getSentDate();

			String subject = StringUtil.trimToNull(msg.getSubject());
			if (subject == null) subject = "<no subject>";

			String headerStr = "";
			Enumeration headers = msg.getAllHeaderLines();
			List mailHeaders = new Vector();
			while (headers.hasMoreElements())
			{
				String line = (String) headers.nextElement();
				headerStr += line + "\n";
				if (line.startsWith("Content-Type: ")) {
					mailHeaders.add(line.replaceAll("Content-Type", MailArchiveService.HEADER_OUTER_CONTENT_TYPE));
				} else {
				//if (line.startsWith("From: ")) {
					//hide original sender address from mailarchive header listing
					//mailHeaders.add("From: "+POSTMASTER+"@"+ServerConfigurationService.getServerName().toString());
				//} else {
					mailHeaders.add(line);
				}
			}

			// we will parse the body and attachments later, when we know we need to.
			String body = null;
			List attachments = null;

				M_log.info(id + " : mail: from:" + from + " sent: "
						+ timeService.newTime(sent.getTime()).toStringLocalFull() + " subject: " + subject);

			// process for each recipient
			Iterator it = to.iterator();
			boolean rejection = false;
			while (it.hasNext())
			{
				String siteId = null;
				String mailId = null;
				String threadId = "";
				User fromUser = null;
				User toUser = null;
				boolean toSite = false;
				boolean toStudent = false;
				boolean fromPostmaster = false;
				boolean toPostmaster = false;
				boolean ccMailList = false;

				// in case lecturer is sending to 32408600.COS101S@my.unisa.ac.za, prepare a fromSubject
				String fromSubject = null;

				try {
					MailAddress recipient = (MailAddress) it.next();
					M_log.debug(this+": preparing to send email to " + recipient + "(from " + from + ")");
					// the recipient's mail id
					mailId = recipient.getUser();
					if(mailId.toLowerCase().equals(POSTMASTER)) {
						M_log.debug("email destined for postmaster - will try postmaster's configured address");
						toPostmaster = true;
					} else if(recipient.toString().toLowerCase().equals(ServerConfigurationService.getString("noReplyEmailFrom").toLowerCase())) {
						M_log.debug(this+": someone ("+fromAddr+") is trying to send to "+recipient.toString()+ " which is the no reply address");
						// Changed to send all email to bounce email and not just delivery notifications (11/11/2008) and just using javax.mail
						//drop or handle bounce
						//M_log.info("Nobody should reply/send to sakai.properties.noReplyEmailFrom ("+ServerConfigurationService.getString("noReplyEmailFrom").toLowerCase()+") - recipient received as "+recipient.toString().toLowerCase()+": DROP");
						//bounces handled - sent to sakai.properties.server-admin.email
						//if(isDeliveryNotification) {
						//InternetAddress toAdmin[] = new InternetAddress[1];
						//toAdmin[0] = new InternetAddress(ServerConfigurationService.getString("sitemailbounces.email"));
						//	InternetAddress replyTo[] = new InternetAddress[1];
						//	replyTo[0] = new InternetAddress("root@localhost");
						//	EmailService.sendMail(new InternetAddress(fromAddr),toAdmin,"Delivery Notification sent to "+recipient.toString(), (String)mail.getMessage().getContent(), toAdmin, replyTo, null);
						//}
						//replies to sakai.properties.noReplyEmailFrom are dropped into a deep dark black hole. for now.
						//toAdmin = new InternetAddress(ServerConfigurationService.getString("sitemailbounces.email"));
						Properties props = System.getProperties();

//						 Setup mail server
						props.put("mail.smtp.host", ServerConfigurationService.getString("smtp@org.sakaiproject.email.api.EmailService"));

//						 Get session
						Session session = Session.getDefaultInstance(props, null);

//						 Redefine message
						MimeMessage message = new MimeMessage(session);
						message.setFrom(new InternetAddress(fromAddr));
						message.setRecipient(javax.mail.internet.MimeMessage.RecipientType.TO,new InternetAddress(ServerConfigurationService.getString("sitemailbounces.email")));
						message.setSubject(subject);
						message.setFlags(msg.getFlags(),true);
						message.setContent(msg.getContent(),msg.getContentType());
						message.setContentID(msg.getContentID());
						message.setContentMD5(msg.getContentMD5());
						//message.setDataHandler(msg.getDataHandler());
						message.setDescription(msg.getDescription());
						message.setDisposition(msg.getDisposition());

						// NO LONGER use Email Service. Use standard javax mail transport to simply forward the message as is.
						Transport.send(message);
						mail.setState(Mail.GHOST);
						
						
						return;
					} else if(from.toLowerCase().startsWith(POSTMASTER) && from.toLowerCase().endsWith(ServerConfigurationService.getServerName())) {
						M_log.debug("sending from postmaster to recipient "+recipient.toString());
						fromPostmaster = true;
					} else {
						// first, assume the mailId is a site id
						try	{
							// Getting siteid here
							siteService.getSite(getMailIdParts(mailId,RECIPIENT).toUpperCase());
							toSite = true;
							siteId=getMailIdParts(mailId,RECIPIENT).toUpperCase();
						}
						catch (IdUnusedException notsite)	{
							M_log.debug("no channel - IdUnusedException " +
							"can't get channelRef, student?");
							toSite = false;
							try {
								toUser = userDirectoryService.getUserByEid(getMailIdParts(mailId,RECIPIENT));
								if(toUser.getType().toLowerCase().equals("student")) {
									M_log.debug("sending to student");
									if(getMailIdParts(mailId,SITE_NAME) != "") {
										fromSubject = getMailIdParts(mailId,SITE_NAME);
										try {
											siteService.getSite(fromSubject);
										} catch (IdUnusedException notsitestudent) {
											M_log.error(this + ": " + id + " : mail rejected: site non-existent: " + mailId);
											throw new Exception(SITENOTEXIST);
										}
									}
									toStudent = true;
								} else {
									M_log.error(this + ": got user but not a student, continuing...");
									mail.setErrorMessage(RCPTNOTEXIST);
									return;
								}
							} catch (IdUnusedException notstudent) {
								M_log.error(this + ":  " + id + " no such student: " + mailId);
								mail.setErrorMessage(RCPTNOTEXIST);
								return;
							} catch (Exception e) {
								M_log.error(this + ": " + id + " : mail rejected: caught basic Exception: " + mailId);
								mail.setErrorMessage(e.getMessage());
								return;
							}
						}
					}
					// prepare to rewrite the FROM address into a user@mydev format
					//String aliasFromAddress = POSTMASTER + "@" + ServerConfigurationService.getServerName();
					String aliasFromAddress = fromAddr;
					InternetAddress toEmail = null;
					InternetAddress iaTo[] = new InternetAddress[1];
					String toNameStr = "";
					if (toSite) {
						M_log.info("preparing mail toSite");
						if(!isDeliveryNotification) {
							// find the possible senders with this email address (presumably a student)
								M_log.info("trying to findUsersByEmail("+fromAddr+")");
								Collection usersByEmail = userDirectoryService.findUsersByEmail(fromAddr);
								Iterator usersByEmailIter = usersByEmail.iterator();
								String realmId = "/site/"+getMailIdParts(mailId,RECIPIENT);
								Role userRole = null;
								boolean userHasRole = false;
								if(usersByEmailIter.hasNext()) {
									while(usersByEmailIter.hasNext()) {
										fromUser = (User)usersByEmailIter.next();
										M_log.info(this+": got user object from list - checking roles in site="+realmId+" for user id="+fromUser.getId()+", eid="+fromUser.getEid());
										userRole = authzGroupService.getAuthzGroup(realmId).getUserRole(fromUser.getEid());
										if(userRole != null) {
											userHasRole = true;
											M_log.info(this+": "+fromUser.getEid()+" has a role="+userRole.getId());
											break;
										} else {
											userRole = authzGroupService.getAuthzGroup(realmId).getUserRole(fromUser.getId());
											if(userRole != null) {
												userHasRole = true;
												M_log.info(this+": "+fromUser.getEid()+" has a role="+userRole.getId());
												break;
											} else {
												M_log.info(this+": "+fromUser.getEid()+" not allowed - no role");
											}
										}
									}
								} else {
									// it's not coming from a student, maybe a lecturer?
									rejection = true;
									M_log.error(this + ": " + id + " : mail rejected: unknown from: " + fromAddr);
									mail.setErrorMessage(SENDERUNKNOWN);
									continue;
								}
								M_log.debug("found user using findUserByEmail("+fromAddr+")");
							// so it IS a student. Is this student a participant in this realm?
							if(!userHasRole) {
								M_log.info("first user found as "+fromUser.getEid()+" has no role defined for "+realmId);
								AdProvisioner adProvisioner = (AdProvisioner)ComponentManager.get("org.sakaiproject.user.api.UserDirectoryProvider");
								M_log.info("got ADDirectoryProvider to get more users with this email address");
								User newFromUser = null;
								try {
									newFromUser = adProvisioner.getUserInRealm(fromAddr,realmId);
									if(newFromUser != null) {
										userHasRole = true;
										//userRole = AuthzGroupService.getAuthzGroup(realmId).getUserRole(newFromUser.getEid());
										fromUser = newFromUser;
									}
								} catch(NullPointerException e) {
									M_log.error("can't get adProvisioner.getUserInRealm(fromAddr,realmId)");

									//userRole = null;
								}
								//M_log.debug("possible user returned has this id: ");
								//send a new id string for the matchRoleByEmail to set as the correct userid
							}
							if(!userHasRole) {
								// user HAS NO role in this realm
								// user can't send to this site
								M_log.error(this + ": " + id + " : mail rejected: non-participant: " + fromUser.getEid());
								//send rejection notice to sender. this line is needed when sent via course contact:
								toEmail = new InternetAddress(fromAddr);
								rejection = true;
								threadId = "0";
								mail.setErrorMessage(NOTMEMBEROFSITE);
								continue;
							} else {
								if(getMailIdParts(mailId,2) == "") {
									M_log.debug("getting next thread id from database");
									threadId = elogger.getNextThreadId();
								} else {
									M_log.debug("thread exists");
									threadId = getMailIdParts(mailId,2);
								}
								aliasFromAddress = fromUser.getEid() + "." + getMailIdParts(mailId,RECIPIENT) + "." + threadId +"@" + ServerConfigurationService.getServerName();
								M_log.debug("trying to get contact-email for "+getMailIdParts(mailId,RECIPIENT));
								String toEmailStr = getContactEmail(getMailIdParts(mailId,RECIPIENT));
								M_log.debug("aliasFromAddress "+aliasFromAddress);
								if(toEmailStr == null) {
									M_log.error(this + ": " + id + " Can't get site contact email");
									mail.setErrorMessage(NOCONTACT);
									continue;
								} else {
									toEmail = new InternetAddress(toEmailStr);
								}
								toNameStr = getContactName(getMailIdParts(mailId,RECIPIENT));
								M_log.debug("subject: ["+mailId+"]: "+subject);
								subject = "["+mailId+"]: "+subject;
							}
						} else {
							M_log.debug("delivery notification");
							M_log.debug("trying to get contact-email for "+mailId);
							String toEmailStr = getContactEmail(mailId);
							M_log.debug("aliasFromAddress "+aliasFromAddress);
							if(toEmailStr == null) {
								M_log.error(this + ": " + id + " Can't get site contact email");
								mail.setErrorMessage(NOCONTACT);
								continue;
							} else {
								toEmail = new InternetAddress(toEmailStr);
							}
							toNameStr = getContactName(mailId);
							M_log.debug("subject: ["+mailId+"]: "+subject);
							subject = "["+mailId+"]: "+subject;
						}
					} else if(toStudent) {
						if(!isStudentAcceptMail(toUser.getId())) {
							mail.setErrorMessage(STUDENTHIDDEN);
							continue;
						}
						boolean threadExists = false;
						M_log.debug("preparing mail toStudent from "+mailId);
						threadId = getMailIdParts(mailId,THREAD_ID);
						if((threadId != null) && (threadId != "")) {
							if(elogger.isValidThread(threadId))	threadExists = true;
						}
						if(!isDeliveryNotification) {
							if(threadExists) {
								toEmail = new InternetAddress(toUser.getEmail());
							} else {
								mail.setErrorMessage(NOTHREAD);
								continue;
							}
							aliasFromAddress = fromSubject+"."+threadId+"@"+ServerConfigurationService.getServerName().toString();
						} else {
							// this is a delivery notifcation. send it regardless
							toEmail = new InternetAddress(toUser.getEmail());
							aliasFromAddress = POSTMASTER+"@"+ServerConfigurationService.getServerName();
							M_log.info("delivery notification to "+toUser.getEmail()+" from "+aliasFromAddress);
							ccMailList = true;						}
					} else if(fromPostmaster) {
						toEmail = new InternetAddress(recipient.toString());
						aliasFromAddress = from;
						mail.setErrorMessage(BLOCKPMASTER);
						continue;

						//						mail.setState(Mail.GHOST);
					} else if(toPostmaster) {
						M_log.info("sending to postmaster: "+postmaster.getEmail());
						toEmail = new InternetAddress(postmaster.getEmail());
						aliasFromAddress = from;
					} else {
						M_log.info("not sending to anyone");
						continue;
					}

					// prepare the message if it has not yet been prepared - we need it.%%%
					// on the other hand, it was suggested that we simply pass on the message as is
					if (body == null)
					{
						body = "";
						attachments = entityManager.newReferenceList();
						try
						{
							StringBuilder bodyBuf[] = new StringBuilder[2];
							bodyBuf[0] = new StringBuilder();
							bodyBuf[1] = new StringBuilder();
							StringBuilder bodyContentType = new StringBuilder();
							Integer embedCount = parseParts(siteId,msg, id, bodyBuf, bodyContentType, attachments, new Integer(-1));
							// treat the message exactly as-is - as plaintext. Stuff like "<b>" will appear
							// to the users as "<b>", NOT as bolded text. Since the message service
							// treats messages as formatted text, plaintext must be converted to formatted text encoding.
							//body = FormattedText.convertPlaintextToFormattedText(body);
							body = bodyBuf.toString();
							if (bodyContentType.length() > 0)
							{
								// save the content type of the message body - which may be different from the
								// overall MIME type of the message (multipart, etc)
								mailHeaders.add(MailArchiveService.HEADER_INNER_CONTENT_TYPE + ": " + bodyContentType);
							}
						}
						catch (MessagingException e)
						{
							M_log.error("Messaging Exception: "+e+": "+e.getMessage());
							M_log.warn(this + ".service(): msg.getContent() threw: " + e);
						}
						catch (IOException e)
						{
							M_log.error("IO Exception: "+e+": "+e.getMessage());
							M_log.warn(this + ".service(): msg.getContent() threw: " + e);
						}
						catch (Exception e) {
							M_log.error("Unhandled exception: "+e+": "+e.getMessage());
						}
					}

					if(toEmail != null) {
						iaTo[0] = toEmail;
						InternetAddress iaHeaderTo[] = new InternetAddress[1];
						iaHeaderTo[0] = toEmail;
						InternetAddress iaReplyTo[] = new InternetAddress[1];
						iaReplyTo[0] = new InternetAddress(aliasFromAddress);
						Properties props = System.getProperties();

//						 Setup mail server
						props.put("mail.smtp.host", ServerConfigurationService.getString("smtp@org.sakaiproject.email.api.EmailService"));

//						 Get session
						Session session = Session.getDefaultInstance(props, null);

//						 Redefine message
						MimeMessage message = new MimeMessage(session);
						message.setFrom(new InternetAddress(aliasFromAddress));
						message.setRecipient(javax.mail.internet.MimeMessage.RecipientType.TO,toEmail);
						if (ccMailList){
							message.addRecipient(javax.mail.internet.MimeMessage.RecipientType.BCC,new InternetAddress(ServerConfigurationService.getString("sitemailbounces.email")));
						}
						message.setSubject(subject);
						message.setFlags(msg.getFlags(),true);
						message.setContent(msg.getContent(),msg.getContentType());
						message.setContentID(msg.getContentID());
						message.setContentMD5(msg.getContentMD5());
						//message.setDataHandler(msg.getDataHandler());
						message.setDescription(msg.getDescription());
						message.setDisposition(msg.getDisposition());

						// NO LONGER use Email Service. Use standard javax mail transport to simply forward the message as is.
						Transport.send(message);
						if ((threadId == null) || (threadId.equals(""))){
							threadId = "0";
						}
						if(!rejection || !(toEmail.toString().equalsIgnoreCase(ServerConfigurationService.getString("server-admin.email"))) || (aliasFromAddress.indexOf("MAILER-DAEMON") < 0)) {
							elogger.insertEmailLog(toEmail.toString(),aliasFromAddress,subject,threadId);
						}
						M_log.info("sending mail from "+aliasFromAddress+" to "+toEmail.toString());

						M_log.info(this + ": " + id + " : delivered to:" + mailId);

						// all is happy - ghost the message to stop further processing
					}
					mail.setState(Mail.GHOST);
				}
				catch (IdUnusedException goOn)
				{
					M_log.error(this + ": " + id + " : mail rejected: " + goOn.toString());
					if (from.startsWith(POSTMASTER + "@"))
					{
						mail.setState(Mail.GHOST);
					}
					else
					{
						mail.setErrorMessage(errorMsg_III);
					}
				}
			}
		} catch(Exception e) {
			M_log.error("Unhandled exception: "+e+": "+e.getMessage());
			e.printStackTrace();
		}
		finally
		{
			// clear out any current current bindings
			threadLocalManager.clear();
		}
	}

	protected String getMailIdParts(String mailId, int part) {
		switch (part) {
			case RECIPIENT:
				if(mailId.indexOf('.') > 0) {
					M_log.debug("returning RECIPIENT as "+mailId.substring(0,mailId.indexOf('.')));
					return mailId.substring(0,mailId.indexOf('.'));
				} else {
					M_log.debug("returning RECIPIENT as "+mailId);
					return mailId;
				}
			case SITE_NAME:
				// assuming recipient contains studentnr.site_name@...
				if(mailId.indexOf('.') > 0) {
					// at least 2 parts
					// check 2nd part
					M_log.debug("mailId has at least 2 parts: "+mailId);
					String part2 = mailId.substring(mailId.indexOf('.')+1,mailId.length());
					if(part2.indexOf('.') > 0) {
						M_log.debug("mailId has 3 parts: "+mailId);
						// 3 parts ie includes thread id
						// return 2nd part
						String sid = part2.substring(0,part2.indexOf('.'));
						M_log.debug("returning SITE_NAME as sid "+sid);
						return sid;
					} else {
						// return 2nd part
						M_log.debug("returning SITE_NAME as "+part2);
						return part2;
					}
				} else {
					return "";
				}
			case THREAD_ID:
				// assuming recipient contains studentnr.site_name.thread122@...
				if(mailId.indexOf('.') > 0) {
					// at least 2 parts
					// check 2nd part
					M_log.debug("mailId has at least 2 parts");
					String part2 = mailId.substring(mailId.indexOf('.')+1,mailId.length());
					if(part2.indexOf('.') > 0) {
						M_log.debug("mailId has 3 parts");
						// 3 parts ie includes thread id
						// return 2nd part
						String part3 = part2.substring(part2.indexOf('.')+1,part2.length());
						M_log.debug("returning SITE_NAME as "+part3);
						return part3;
					} else {
						return "";
					}
				} else {
					return "";
				}
			default:
				return mailId;
		}
	}

	protected String getContactEmail(String siteName) {
		siteService = (SiteService) ComponentManager.get(SiteService.class);
		try {
			return siteService.getSite(siteName).getProperties().getProperty("contact-email").toLowerCase();
		} catch (IdUnusedException ide) {
			M_log.error(ide+": no such site - "+siteName+", can't get contact-email");
			return null;
		}
	}

	protected String getContactName(String siteName) {
		siteService = (SiteService) ComponentManager.get(SiteService.class);
		try {
			return siteService.getSite(siteName).getProperties().getProperty("contact-name");
		} catch (IdUnusedException ide) {
			return "";
		}
	}


}
