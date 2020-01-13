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

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.validator.EmailValidator;
import org.sakaiproject.component.api.ServerConfigurationService;
import org.sakaiproject.genericdao.api.finders.ByPropsFinder;
import org.sakaiproject.qna.dao.QnaDao;
import org.sakaiproject.qna.logic.ExternalEventLogic;
import org.sakaiproject.qna.logic.ExternalLogic;
import org.sakaiproject.qna.logic.OptionsLogic;
import org.sakaiproject.qna.logic.PermissionLogic;
import org.sakaiproject.qna.model.QnaCustomEmail;
import org.sakaiproject.qna.model.QnaOptions;
import org.sakaiproject.qna.model.constants.QnaConstants;

public class OptionsLogicImpl implements OptionsLogic {

	private static Log log = LogFactory.getLog(OptionsLogicImpl.class);

	private PermissionLogic permissionLogic;
	private QnaDao dao;
	private ExternalLogic externalLogic;
	private ExternalEventLogic externalEventLogic;
	private ServerConfigurationService serverConfigurationService;
	
	public void setPermissionLogic(PermissionLogic permissionLogic) {
		this.permissionLogic = permissionLogic;
	}

	public void setDao(QnaDao dao) {
		this.dao = dao;
	}

	public void setExternalLogic(ExternalLogic externalLogic) {
		this.externalLogic = externalLogic;
	}
	
	public void setExternalEventLogic(ExternalEventLogic externalEventLogic) {
		this.externalEventLogic = externalEventLogic;
	}
	
	public void setServerConfigurationService(ServerConfigurationService serverConfigurationService) {
		this.serverConfigurationService = serverConfigurationService;
	}
	
	/**
	 * @see OptionsLogic#createDefaultOptions(String)
	 */
	public QnaOptions createDefaultOptions(String locationId) {

		QnaOptions newOptions = new QnaOptions();

		newOptions.setLocation(locationId);
		newOptions.setOwnerId("default");

		newOptions.setAnonymousAllowed(serverConfigurationService.getBoolean("qna.default.anonymous", false));
		
		newOptions.setModerated(serverConfigurationService.getBoolean("qna.default.moderated", true));
		
		newOptions.setAllowUnknownMobile(serverConfigurationService.getBoolean("qna.default.allow-unknown-mobile", false));
		
		newOptions.setSmsNotification(serverConfigurationService.getBoolean("qna.default.sms-notification", false));
		
		newOptions.setMobileAnswersNr(serverConfigurationService.getInt("qna.default.mobile-answers-nr", 1));
		
		String notificationProperty = serverConfigurationService.getString("qna.default.notification", "none");
		
		if (notificationProperty.equalsIgnoreCase(QnaConstants.SITE_CONTACT)) {
			newOptions.setEmailNotification(true);
			newOptions.setEmailNotificationType(QnaConstants.SITE_CONTACT);
		} else if (notificationProperty.equalsIgnoreCase(QnaConstants.UPDATE_RIGHTS)) {
			newOptions.setEmailNotification(true);
			newOptions.setEmailNotificationType(QnaConstants.UPDATE_RIGHTS);
		} else if (notificationProperty.equalsIgnoreCase("none")) {
			newOptions.setEmailNotification(false);
			// Make site contact the default to notify but set notification false as default
			newOptions.setEmailNotificationType(QnaConstants.SITE_CONTACT);
		} else { // If custom mail list or invalid option given
			processCustomMailList(notificationProperty,newOptions,newOptions.getOwnerId());
			if (newOptions.getCustomEmails().size() > 0) {
				newOptions.setEmailNotification(true);
				newOptions.setEmailNotificationType(QnaConstants.CUSTOM_LIST);
			} else {
				newOptions.setEmailNotification(false);
				newOptions.setEmailNotificationType(QnaConstants.SITE_CONTACT);
			}
		}
		
		String defaultViewProperty = serverConfigurationService.getString("qna.default.view",QnaConstants.CATEGORY_VIEW);
		
		if (QnaConstants.isValidView(defaultViewProperty)) {
			newOptions.setDefaultStudentView(defaultViewProperty);
		} else {
			newOptions.setDefaultStudentView(QnaConstants.CATEGORY_VIEW);
		}

		Date now = new Date();
		newOptions.setDateCreated(now);
		newOptions.setDateLastModified(now);

		dao.save(newOptions);
		externalEventLogic.postEvent(ExternalEventLogic.EVENT_OPTIONS_CREATE, newOptions);
		return newOptions;
	}

	/**
	 * @see OptionsLogic#getNotificationSet(String)
	 */
	public Set<String> getNotificationSet(String locationId) {
		QnaOptions options = getOptionsForLocation(locationId);
		Set<String> notificationSet = new HashSet<String>();
		
		try {
			if (options.getEmailNotification()) {
				if (options.getEmailNotificationType().equals(QnaConstants.SITE_CONTACT)) {
					notificationSet.add(externalLogic.getSiteContactEmail(locationId));
				} else if (options.getEmailNotificationType().equals(QnaConstants.UPDATE_RIGHTS)) {
					Set<String> users = externalLogic.getSiteUsersWithPermission(locationId,ExternalLogic.QNA_UPDATE);
					for (String userId : users) {
						notificationSet.add(externalLogic.getUserEmail(userId));
					}
				} else if (options.getEmailNotificationType().equals(QnaConstants.CUSTOM_LIST)) {
					Set<QnaCustomEmail> customMails = options.getCustomEmails();
					for (QnaCustomEmail qnaCustomEmail : customMails) {
						notificationSet.add(qnaCustomEmail.getEmail());
					}
				}
			}	
		} catch (SecurityException se) {
			// This might happen if anon does not have access to site or site isn't public, log it for now
			log.error(se.getMessage(), se);
		}
		
		return notificationSet;
	}
	
	/**
	 * @see OptionsLogic#getOptionsForLocation(String)
	 */
	public QnaOptions getOptionsForLocation(String locationId) {
		List l = dao.findByProperties(QnaOptions.class,
				new String[] { "location" }, new Object[] { locationId },
				new int[] { ByPropsFinder.EQUALS }, 0, 1);
		if (l.size() > 0) {
			return (QnaOptions) l.get(0);
		} else {
			// Create options for location
			QnaOptions newOptions = createDefaultOptions(locationId);
			return newOptions;
		}
	}
	
	/**
	 * @see OptionsLogic#isModerationOn(String)
	 */
	public boolean isModerationOn(String locationId) {
		List l = dao.findByProperties(
			QnaOptions.class,
			new String[] { "location" },
			new Object[] { locationId },
			new int[] { ByPropsFinder.EQUALS },
			0,
			1
		);
		if (l.size() > 0) {
			return ((QnaOptions)l.get(0)).isModerated();
		} else {
			return false;
		}
	}
	
	/**
	 * @see OptionsLogic#saveOptions(QnaOptions, String)
	 */
	public void saveOptions(QnaOptions options, String locationId) {
		String userId = externalLogic.getCurrentUserId();

		if (!options.getLocation().equals(locationId)) {
			throw new SecurityException("Current location ("+locationId+") does not match options location ("+options.getLocation()+")");
		}

		if (permissionLogic.canUpdate(options.getLocation(), userId)) {
			options.setDateLastModified(new Date());
			options.setOwnerId(userId);
			dao.save(options);
			externalEventLogic.postEvent(ExternalEventLogic.EVENT_OPTIONS_UPDATE, options);
		} else {
			throw new SecurityException("Current user cannot save options for "
					+ options.getLocation()
					+ " because they do not have permission");
		}

	}
	
	/**
	 * @see OptionsLogic#setCustomMailList(String, String)
	 */
	public boolean setCustomMailList(String locationId, String mailList) {
		String userId = externalLogic.getCurrentUserId();

		QnaOptions options = getOptionsForLocation(locationId);

		for (QnaCustomEmail mail : options.getCustomEmails()) {
			dao.delete(mail);
		}
		options.getCustomEmails().clear();

		boolean invalidEmail = processCustomMailList(mailList, options, userId);
		saveOptions(options, locationId);

		return invalidEmail;

	}
	
	/**
	 * Processes a comma separated list of emails and adds it to a {@link QnaOptions} object
	 * 
	 * @param mailList String of comma separated values of emails
	 * @param options The {@link QnaOptions} object custom mails must be added to
	 * @param userId Sakai user id to use
	 * @return boolean if there were any invalid mails
	 */
	private boolean processCustomMailList(String mailList, QnaOptions options, String userId) {
		EmailValidator emailValidator = EmailValidator.getInstance();
		boolean invalidEmail = false;
		
		if (mailList != null && !mailList.trim().equals("")) {
			String[] emails = mailList.split(",");
			for (int i = 0; i < emails.length; i++) {
				if (!emailValidator.isValid(emails[i].trim())) {
					invalidEmail = true;
				} else {
					QnaCustomEmail customEmail = new QnaCustomEmail(userId, emails[i].trim(), new Date());
					options.addCustomEmail(customEmail);
				}
			}
		}
		return invalidEmail;
	}
	
	/**
	 * @see OptionsLogic#getOptionsById(String)
	 */
	public QnaOptions getOptionsById(String id) {
		return dao.findById(QnaOptions.class, id);
	}

}
