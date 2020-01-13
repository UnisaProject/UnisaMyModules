/**
 * Copyright (c) 2007-2009 The Sakai Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *             http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.sakaiproject.qna.logic.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.sakaiproject.authz.api.FunctionManager;
import org.sakaiproject.authz.api.SecurityAdvisor;
import org.sakaiproject.authz.api.SecurityService;
import org.sakaiproject.component.cover.ServerConfigurationService;
import org.sakaiproject.email.api.EmailService;
import org.sakaiproject.entity.api.Entity;
import org.sakaiproject.entitybroker.EntityBroker;
import org.sakaiproject.entitybroker.EntityReference;
import org.sakaiproject.exception.IdUnusedException;
import org.sakaiproject.qna.logic.ExternalLogic;
import org.sakaiproject.qna.utils.QNAUtils;
import org.sakaiproject.site.api.Site;
import org.sakaiproject.site.api.SiteService;
import org.sakaiproject.sms.logic.smpp.SmsService;
import org.sakaiproject.tool.api.SessionManager;
import org.sakaiproject.tool.api.ToolManager;
import org.sakaiproject.user.api.User;
import org.sakaiproject.user.api.UserDirectoryService;
import org.sakaiproject.user.api.UserNotDefinedException;

public class ExternalLogicImpl implements ExternalLogic {

	private final static String PROP_SITE_CONTACT_EMAIL = "contact-email";

	private final static String PROP_SITE_CONTACT_NAME = "contact-name";

	private static Log log = LogFactory.getLog(ExternalLogicImpl.class);

	private FunctionManager functionManager;

	public void setFunctionManager(FunctionManager functionManager) {
		this.functionManager = functionManager;
	}

	private SessionManager sessionManager;

	public void setSessionManager(SessionManager sessionManager) {
		this.sessionManager = sessionManager;
	}

	private ToolManager toolManager;

	public void setToolManager(ToolManager toolManager) {
		this.toolManager = toolManager;
	}

	private SecurityService securityService;

	public void setSecurityService(SecurityService securityService) {
		this.securityService = securityService;
	}

	private SiteService siteService;

	public void setSiteService(SiteService siteService) {
		this.siteService = siteService;
	}

	private UserDirectoryService userDirectoryService;

	public void setUserDirectoryService(
			UserDirectoryService userDirectoryService) {
		this.userDirectoryService = userDirectoryService;
	}

	private EntityBroker entityBroker;

	public void setEntityBroker(EntityBroker entityBroker) {
		this.entityBroker = entityBroker;
	}

	private EmailService emailService;

	public void setEmailService(EmailService emailService) {
		this.emailService = emailService;
	}

	private SmsService smsService;

	public void setSmsService(SmsService smsService) {
		this.smsService = smsService;
	}

	public void init() {
		log.debug("init");
		// register Sakai permissions for this tool
		functionManager.registerFunction(QNA_READ);
		functionManager.registerFunction(QNA_NEW_QUESTION);
		functionManager.registerFunction(QNA_NEW_ANSWER);
		functionManager.registerFunction(QNA_NEW_CATEGORY);
		functionManager.registerFunction(QNA_UPDATE);
	}

	/**
	 * @see ExternalLogic#getCurrentLocationId()
	 */
	public String getCurrentLocationId() {
		try {

			if (toolManager.getCurrentPlacement() == null) {
				return NO_LOCATION;
			}

			Site s = siteService.getSite(toolManager.getCurrentPlacement()
					.getContext());
			return s.getReference();
		} catch (IdUnusedException e) {
			return NO_LOCATION;
		}
	}

	/**
	 * @see ExternalLogic#getQuestionViewUrl(String)
	 */
	public String getQuestionViewUrl(String viewId) {		
		return ServerConfigurationService.getPortalUrl() + getCurrentLocationId() + "/tool" + Entity.SEPARATOR
				+ toolManager.getCurrentPlacement().getId() + Entity.SEPARATOR
				+ viewId;
	}

	/**
	 * @see ExternalLogic#getLocationTitle(String)
	 */
	public String getLocationTitle(String locationId) {
		
		// We always want this to succeed for some cases like anonymous answer requests,
		// so use a securityadvisor.
		
		String title =  "----------";
		
		try {
			securityService.pushAdvisor(new SecurityAdvisor() {
				public SecurityAdvice isAllowed(String userId, String function, String reference) {
					if (SiteService.SITE_VISIT.equals(function)) {
						return SecurityAdvice.ALLOWED;
					}
					return SecurityAdvice.PASS;
				}
			});

			Site site = (Site) entityBroker.fetchEntity(locationId);
			title = site.getTitle();
			
		} catch (Exception e) {
			log.debug("Invalid site reference: " + locationId);
		} finally {
			securityService.popAdvisor();
		}

		return title;
	}

	/**
	 * @see ExternalLogic#getSiteIdFromRef(String)
	 */
	public String getSiteIdFromRef(String reference) {
		return EntityReference.getIdFromRefByKey(reference, "site");
	}

	/**
	 * @see ExternalLogic#getCurrentUserId()
	 */
	public String getCurrentUserId() {
		return sessionManager.getCurrentSessionUserId();
	}

	/**
	 * @see ExternalLogic#getUserDisplayName(String)
	 */
	public String getUserDisplayName(String userId) {
		try {
			return userDirectoryService.getUser(userId).getDisplayName();
		} catch (UserNotDefinedException e) {
			log.warn("Cannot get user displayname for id: " + userId);
			return "--------";
		}
	}

	/**
	 * @see ExternalLogic#isUserAdmin(String)
	 */
	public boolean isUserAdmin(String userId) {
		return securityService.isSuperUser(userId);
	}

	/**
	 * @see ExternalLogic#hasMaintainRole(String)
	 */
	public boolean hasMaintainRole(String userId) {
		try {

			if (toolManager.getCurrentPlacement() == null) {
				return false;
			}

			Site site = siteService.getSite(toolManager.getCurrentPlacement()
					.getContext());
			return site.getMaintainRole().equals(
					site.getMember(userId).getRole().getId());
		} catch (IdUnusedException e) {
			return false;
		}
	}

	/**
	 * @see ExternalLogic#isUserAllowedInLocation(String, String, String)
	 */
	public boolean isUserAllowedInLocation(String userId, String permission,
			String locationId) {
		log.debug("isUserAllowedInLocation(" + userId + ", " + permission + ","
				+ locationId + ")");
		Boolean allowed = false;
		if (userId != null)
			allowed = securityService.unlock(userId, permission, locationId);
		else
			allowed = false;

		log.debug("allowed: " + allowed);
		return allowed;
	}

	/**
	 * @see ExternalLogic#getSiteContactEmail(String)
	 */
	public String getSiteContactEmail(String locationId) {
		Site site = (Site) entityBroker.fetchEntity(locationId);
		if (site.getProperties().getProperty(PROP_SITE_CONTACT_EMAIL) != null) {
			return site.getProperties().getProperty(PROP_SITE_CONTACT_EMAIL);
		} else {
			return site.getCreatedBy().getEmail();
		}
	}

	/**
	 * @see ExternalLogic#getSiteContactName(String)
	 */
	public String getSiteContactName(String locationId) {
		Site site = (Site) entityBroker.fetchEntity(locationId);
		if (site.getProperties().getProperty(PROP_SITE_CONTACT_NAME) != null) {
			return site.getProperties().getProperty(PROP_SITE_CONTACT_NAME);
		} else {
			return site.getCreatedBy().getDisplayName();
		}
	}

	/**
	 * @see ExternalLogic#getSiteUsersWithPermission(String, String)
	 */
	@SuppressWarnings("unchecked")
	public Set<String> getSiteUsersWithPermission(String locationId,
			String permission) {
		Site site = (Site) entityBroker.fetchEntity(locationId);
		Set<String> users = site.getUsers();

		Set<String> usersWithPermission = new HashSet<String>();
		for (String userid : users) {
			if (isUserAllowedInLocation(userid, permission, locationId)) {
				usersWithPermission.add(userid);
			}
		}
		return usersWithPermission;
	}

	/**
	 * @see ExternalLogic#getUserEmail(String)
	 */
	public String getUserEmail(String userId) {
		try {
			return userDirectoryService.getUser(userId).getEmail();
		} catch (UserNotDefinedException unde) {
			log.error("Invalid user: Cannot find user object by id: " + userId);
			return null;
		}
	}

	/**
	 * @see ExternalLogic#getCurrentToolDisplayName()
	 */
	public String getCurrentToolDisplayName() {
		return toolManager.getCurrentPlacement().getTool().getTitle();
	}

	/**
	 * @see ExternalLogic#getCurrentToolId()
	 */
	public String getCurrentToolId() {
		if (toolManager.getCurrentTool() == null) {
			return "sakai.qna";
		} else {
			return toolManager.getCurrentTool().getId();
		}
	}

	/**
	 * @see ExternalLogic#sendEmailsToUsers(String, String[], String, String)
	 */
	public String[] sendEmailsToUsers(String from, String[] toUserIds,
			String subject, String message) {
		InternetAddress fromAddress;
		try {
			fromAddress = new InternetAddress(from);
		} catch (AddressException e) {
			// cannot recover from this failure
			throw new IllegalArgumentException("Invalid from address: " + from,
					e);
		}

		List<User> l = new ArrayList<User>(); // fill this with users
		for (int i = 0; i < toUserIds.length; i++) {
			User user = null;
			try {
				user = userDirectoryService.getUser(toUserIds[i]);
			} catch (UserNotDefinedException e) {
				log.debug("Cannot find user object by id:" + toUserIds[i]);
				try {
					user = userDirectoryService.getUserByEid(toUserIds[i]);
				} catch (UserNotDefinedException e1) {
					log.error(
							"Invalid user: Cannot find user object by id or eid:"
									+ toUserIds[i], e1);
				}
			}
			if (user != null)
				l.add(user);
		}

		// email address validity is checked at entry but value can be null
		List<String> toEmails = new ArrayList<String>();
		for (ListIterator<User> iterator = l.listIterator(); iterator.hasNext();) {
			User u = iterator.next();
			if (u.getEmail() == null || "".equals(u.getEmail())) {
				iterator.remove();
				log.warn("sendEmails: Could not get an email address for "
						+ u.getDisplayName() + " (" + u.getId() + ")");
			} else {
				toEmails.add(u.getEmail());
			}
		}

		if (l == null || l.size() <= 0) {
			log
					.warn("No users with email addresses found in the provided userIds cannot send email so exiting");
			return new String[] {};
		}

		return sendEmails(fromAddress, toEmails, subject, message);
	}

	/**
	 * @see ExternalLogic#sendEmails(String, String[], String, String)
	 */
	public String[] sendEmails(String from, String[] emails, String subject,
			String message) {
		InternetAddress fromAddress;
		try {
			fromAddress = new InternetAddress(from);
		} catch (AddressException e) {
			// cannot recover from this failure
			throw new IllegalArgumentException("Invalid from address: " + from,
					e);
		}
		List<String> toEmails = new ArrayList<String>();
		for (String email : emails) {
			if (email != null && !"".equals(email)) {
				toEmails.add(email);
			}
		}

		return sendEmails(fromAddress, toEmails, subject, message);
	}

	/**
	 * Actual sending of e-mail
	 */
	private String[] sendEmails(InternetAddress fromAddress,
			Collection<String> toEmails, String subject, String message) {
		InternetAddress[] replyTo = new InternetAddress[1];
		List<InternetAddress> listAddresses = new ArrayList<InternetAddress>();

		for (Iterator<String> it = toEmails.iterator(); it.hasNext();) {
			String email = it.next();
			try {
				if (QNAUtils.isValidEmail(email)) {
					InternetAddress toAddress = new InternetAddress(email);
					listAddresses.add(toAddress);
				}
			} catch (AddressException e) {
				log.error("Invalid to address: " + email
						+ ", cannot send email", e);
			}
		}

		replyTo[0] = fromAddress;
		InternetAddress[] toAddresses = listAddresses
				.toArray(new InternetAddress[listAddresses.size()]);
		emailService.sendMail(fromAddress, toAddresses, subject, message, null,
				null, null);

		// now we send back the list of people who the email was sent to
		String[] addresses = new String[toAddresses.length];
		for (int i = 0; i < toAddresses.length; i++) {
			addresses[i] = toAddresses[i].getAddress();
		}
		return addresses;
	}

	public String[] sendSms(String[] mobileNrs, String fromId, String siteId,
			String toolId, String message) {
		if (smsService == null) {
			log.warn("SMS service is not enabled for Q&A");
			return null;
		}
		return smsService.sendSmsToMobileNumbers(mobileNrs, fromId, siteId, toolId, message);
	}

	public String getSmsNumber() {
		return ServerConfigurationService.getString("sms.shortcode", null);
	}

	public String getServiceName() {
		return ServerConfigurationService.getString("ui.service", "Sakai");
	}
}
