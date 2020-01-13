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
package org.sakaiproject.qna.logic.test;

import static org.sakaiproject.qna.logic.test.TestDataPreload.LOCATION1_CONTACT_EMAIL;
import static org.sakaiproject.qna.logic.test.TestDataPreload.LOCATION1_ID;
import static org.sakaiproject.qna.logic.test.TestDataPreload.LOCATION2_ID;
import static org.sakaiproject.qna.logic.test.TestDataPreload.LOCATION3_ID;
import static org.sakaiproject.qna.logic.test.TestDataPreload.LOCATION4_ID;
import static org.sakaiproject.qna.logic.test.TestDataPreload.USER_CUSTOM_EMAIL1;
import static org.sakaiproject.qna.logic.test.TestDataPreload.USER_CUSTOM_EMAIL2;
import static org.sakaiproject.qna.logic.test.TestDataPreload.USER_CUSTOM_EMAIL3;
import static org.sakaiproject.qna.logic.test.TestDataPreload.USER_CUSTOM_EMAIL_INVALID;
import static org.sakaiproject.qna.logic.test.TestDataPreload.USER_CUSTOM_EMAIL_VALID;
import static org.sakaiproject.qna.logic.test.TestDataPreload.USER_LOC_3_UPDATE_1_EMAIL;
import static org.sakaiproject.qna.logic.test.TestDataPreload.USER_LOC_3_UPDATE_2_EMAIL;
import static org.sakaiproject.qna.logic.test.TestDataPreload.USER_LOC_3_UPDATE_3_EMAIL;
import static org.sakaiproject.qna.logic.test.TestDataPreload.USER_NO_UPDATE;
import static org.sakaiproject.qna.logic.test.TestDataPreload.USER_UPDATE;

import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.sakaiproject.qna.dao.QnaDao;
import org.sakaiproject.qna.logic.impl.OptionsLogicImpl;
import org.sakaiproject.qna.logic.impl.PermissionLogicImpl;
import org.sakaiproject.qna.logic.test.stubs.ExternalEventLogicStub;
import org.sakaiproject.qna.logic.test.stubs.ExternalLogicStub;
import org.sakaiproject.qna.logic.test.stubs.ServerConfigurationServiceStub;
import org.sakaiproject.qna.model.QnaCustomEmail;
import org.sakaiproject.qna.model.QnaOptions;
import org.sakaiproject.qna.model.constants.QnaConstants;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

@DirtiesContext
@ContextConfiguration(locations={
		"/hibernate-test.xml",
		"/spring-hibernate.xml"})
public class OptionsLogicImplTest extends
	AbstractTransactionalJUnit4SpringContextTests {

	OptionsLogicImpl optionsLogic;
	PermissionLogicImpl permissionLogic;
	QnaDao dao;

	private static Log log = LogFactory.getLog(OptionsLogicImplTest.class);

	private final ExternalLogicStub externalLogicStub = new ExternalLogicStub();
	private final ExternalEventLogicStub externalEventLogicStub = new ExternalEventLogicStub();
	private final ServerConfigurationServiceStub serverConfigurationServiceStub = new ServerConfigurationServiceStub();	
	
	private final TestDataPreload tdp = new TestDataPreload();

	// run this before each test starts
	@Before
	public void onSetUpBeforeTransaction() throws Exception {
		// load the spring created dao class bean from the Spring Application
		// Context
		dao = (QnaDao) applicationContext.getBean("org.sakaiproject.qna.dao.impl.QnaDaoTarget");
		if (dao == null) {
			log.error("onSetUpBeforeTransaction: DAO could not be retrieved from spring context");
		}
		
		permissionLogic = new PermissionLogicImpl();
		permissionLogic.setExternalLogic(externalLogicStub);

		// create and setup the object to be tested
		optionsLogic = new OptionsLogicImpl();
		optionsLogic.setDao(dao);
		optionsLogic.setPermissionLogic(permissionLogic);
		optionsLogic.setExternalLogic(externalLogicStub);
		optionsLogic.setExternalEventLogic(externalEventLogicStub);
		optionsLogic.setServerConfigurationService(serverConfigurationServiceStub);
		
		// preload testData
		tdp.preloadTestData(dao);
	}

	/**
	 * Test to retrieve options
	 */
	@Test
	public void testGetOptionsByLocation() {
		QnaOptions options = optionsLogic
				.getOptionsForLocation(LOCATION1_ID);
		Assert.assertNotNull(options);
		Assert.assertTrue(options.getLocation().equals(LOCATION1_ID));

		Assert.assertEquals(options.isModerated(), tdp.options_location1
				.isModerated());
		Assert.assertEquals(options.getAnonymousAllowed(), tdp.options_location1
				.getAnonymousAllowed());

		Assert.assertEquals(options.getEmailNotification(), tdp.options_location1
				.getEmailNotification());
		Assert.assertEquals(options.getEmailNotificationType(), tdp.options_location1
				.getEmailNotificationType());

		Assert.assertEquals(options.getDefaultStudentView(), tdp.options_location1
				.getDefaultStudentView());
	}

	/**
	 * Test to modify options
	 */
	@Test
	public void testModifyOptions() {
		QnaOptions options = optionsLogic
				.getOptionsForLocation(LOCATION1_ID);
		Assert.assertNotNull(options);
		options.setAnonymousAllowed(true);
		options.setModerated(false);
		options.setEmailNotification(false);
		options.setDefaultStudentView(QnaConstants.MOST_POPULAR_VIEW);
		
		// Set user here without permissions

		// Test with invalid permissions
		try {
			externalLogicStub.currentUserId = USER_NO_UPDATE;
			optionsLogic.saveOptions(options, LOCATION1_ID);

			Assert.fail("Should have thrown exception");
		} catch (SecurityException e) {
			Assert.assertNotNull(e);
		}
		
		// Set user here with permissions
		try {
			externalLogicStub.currentUserId = USER_UPDATE;
			optionsLogic.saveOptions(options, LOCATION1_ID);
		} catch (SecurityException e) {
			Assert.fail("Should have thrown exception");
		}

		QnaOptions modifiedOptions = optionsLogic
				.getOptionsForLocation(LOCATION1_ID);

		Assert.assertNotNull(modifiedOptions);
		Assert.assertEquals(options.getAnonymousAllowed(), modifiedOptions
				.getAnonymousAllowed());
		Assert.assertEquals(options.isModerated(), modifiedOptions
				.isModerated());
		Assert.assertEquals(options.getEmailNotification(), modifiedOptions
				.getEmailNotification());
		Assert.assertEquals(options.getDefaultStudentView(), modifiedOptions
				.getDefaultStudentView());
		
	}

	/**
	 * Test creating new options for location without options saved yet
	 */
	@Test
	public void testNewOptions() {
		// Get new location id without options set
		String locationId = LOCATION2_ID;

		QnaOptions options = optionsLogic.getOptionsForLocation(locationId);

		Assert.assertNotNull(options);
	}
	
	/**
	 * Test that default view can only be set as valid values
	 */
	@Test
	public void testSetDefaultView() {
		QnaOptions options = optionsLogic
				.getOptionsForLocation(LOCATION1_ID);

		Assert.assertEquals(options.getDefaultStudentView(),
				QnaConstants.CATEGORY_VIEW);

		try {
			options.setDefaultStudentView("silly_string");
			Assert.fail("Should throw exception");
		} catch (IllegalArgumentException e) {
			Assert.assertNotNull(e);
		}

		Assert.assertEquals(options.getDefaultStudentView(),
				QnaConstants.CATEGORY_VIEW);
		options.setDefaultStudentView(QnaConstants.MOST_POPULAR_VIEW);
		Assert.assertEquals(options.getDefaultStudentView(),
				QnaConstants.MOST_POPULAR_VIEW);
	}

	/**
	 * Test that only valid email notification types can be selected Test that
	 * notification type is null when emailNotification is false
	 */
	@Test
	public void testMailNotificationType() {
		QnaOptions options = optionsLogic
				.getOptionsForLocation(LOCATION1_ID);

		Assert.assertEquals(options.getEmailNotification(), Boolean.valueOf(true));
		Assert.assertEquals(options.getEmailNotificationType(),
				QnaConstants.SITE_CONTACT);

		try {
			options.setEmailNotificationType("silly_string");
			Assert.fail("Should throw exception");
		} catch (IllegalArgumentException e) {
			Assert.assertNotNull(e);
		}

		Assert.assertFalse(options.getEmailNotificationType().equals("silly_string"));
		Assert.assertEquals(options.getEmailNotificationType(),
				QnaConstants.SITE_CONTACT);
	}

	/**
	 * Test get/set of custom mail list
	 */
	@Test
	public void testCustomMailList() {
		externalLogicStub.currentUserId = USER_UPDATE;
		QnaOptions options = optionsLogic
				.getOptionsForLocation(LOCATION1_ID);
		Set<QnaCustomEmail> customEmails = options.getCustomEmails();
		
	
		Assert.assertEquals(3, customEmails.size());
		
		Assert.assertTrue(customEmails.contains(tdp.customEmail1_location1));
		Assert.assertTrue(customEmails.contains(tdp.customEmail2_location1));
		Assert.assertTrue(customEmails.contains(tdp.customEmail3_location1));
		
		for (QnaCustomEmail qnaCustomEmail2 : customEmails) {
			Assert.assertNotNull(qnaCustomEmail2.getId());
		}
		
		boolean errorOccurred = optionsLogic.setCustomMailList(
				LOCATION1_ID,
				USER_CUSTOM_EMAIL_INVALID);
		Assert.assertTrue(errorOccurred);

		options = optionsLogic.getOptionsForLocation(LOCATION1_ID);
		customEmails = options.getCustomEmails();

		Assert.assertEquals(customEmails.size(), 1);
		
		boolean contains = false;
		for (QnaCustomEmail qnaCustomEmail : customEmails) {
			if(qnaCustomEmail.getEmail().equals(USER_CUSTOM_EMAIL3)){
				contains = true;
			}
		}
		Assert.assertTrue(contains);

		errorOccurred = optionsLogic.setCustomMailList(
				LOCATION1_ID,
				USER_CUSTOM_EMAIL_VALID);
		Assert.assertFalse(errorOccurred);
		
		options = optionsLogic.getOptionsForLocation(LOCATION1_ID);
		customEmails = options.getCustomEmails();
		

		Assert.assertEquals(customEmails.size(), 3);
		
		boolean foundEmail1 = false;
		boolean foundEmail2 = false;
		boolean foundEmail3 = false;
		
		for (QnaCustomEmail qnaCustomEmail : customEmails) {
			Assert.assertNotNull(qnaCustomEmail.getId());
			if(qnaCustomEmail.getEmail().equals(USER_CUSTOM_EMAIL1)) {
				foundEmail1 = true;
			} else if (qnaCustomEmail.getEmail().equals(USER_CUSTOM_EMAIL2)) {
				foundEmail2 = true;
			} else if (qnaCustomEmail.getEmail().equals(USER_CUSTOM_EMAIL3)) {
				foundEmail3 = true;
			}
		}
		Assert.assertTrue(foundEmail1);
		Assert.assertTrue(foundEmail2);
		Assert.assertTrue(foundEmail3);
		
		// Add it again
		errorOccurred = optionsLogic.setCustomMailList(
				LOCATION1_ID,
				"clown@college.org");
		Assert.assertFalse(errorOccurred);
		options = optionsLogic.getOptionsForLocation(LOCATION1_ID);
		customEmails = options.getCustomEmails();
		
		Assert.assertEquals(customEmails.size(), 1);
	}

	/**
	 * Test get to get correct mail addresses based on options Test location set
	 * as site contact
	 */
	@Test
	public void testGetMailAddressesSiteContact() {
		QnaOptions options = optionsLogic.getOptionsForLocation(LOCATION1_ID);
		Assert.assertEquals(options.getEmailNotificationType(),
				QnaConstants.SITE_CONTACT);
		Set<String> notificationList = optionsLogic.getNotificationSet(LOCATION1_ID);
		Assert.assertEquals(notificationList.size(), 1);
		Assert.assertTrue(notificationList.contains(LOCATION1_CONTACT_EMAIL));
	}

	/**
	 * Test get to get correct mail addresses based on options Test location set
	 * as update rights
	 */
	@Test
	public void testGetMailAddressesUpdateRights() {
		QnaOptions options = optionsLogic.getOptionsForLocation(LOCATION3_ID);
		Assert.assertEquals(options.getEmailNotificationType(),
				QnaConstants.UPDATE_RIGHTS);
		Set<String> notificationSet = optionsLogic.getNotificationSet(LOCATION3_ID);
		Assert.assertEquals(notificationSet.size(), 3);

		Assert.assertTrue(notificationSet.contains(USER_LOC_3_UPDATE_1_EMAIL));
		Assert.assertTrue(notificationSet.contains(USER_LOC_3_UPDATE_2_EMAIL));
		Assert.assertTrue(notificationSet.contains(USER_LOC_3_UPDATE_3_EMAIL));
	}

	/**
	 * Test get to get correct mail addresses based on options Test location set
	 * as custom mail list
	 */
	@Test
	public void testGetMailAddressesCustomList() {
		externalLogicStub.currentUserId = USER_UPDATE;
		QnaOptions options = optionsLogic.getOptionsForLocation(LOCATION1_ID);
		try {
			options.setEmailNotificationType(QnaConstants.CUSTOM_LIST);
			optionsLogic.saveOptions(options, LOCATION1_ID);
		} catch (Exception e) {
			Assert.fail("Should not throw exception");
		}
		
		Assert.assertEquals(options.getEmailNotificationType(),QnaConstants.CUSTOM_LIST);
		Assert.assertEquals(options.getCustomEmails().size(),3);
		Set<String> notificationSet = optionsLogic.getNotificationSet(LOCATION1_ID);
		Set<QnaCustomEmail> customMails = options.getCustomEmails();
		Assert.assertEquals(notificationSet.size(), notificationSet.size());

		for (QnaCustomEmail qnaCustomEmail : customMails) {
			Assert.assertTrue(notificationSet.contains(qnaCustomEmail.getEmail()));
		}
	}

	/**
	 * Test to get zero mail addresses when notification is false for location
	 */
	@Test
	public void testGetMailAddressesNoNotification() {
		QnaOptions options = optionsLogic.getOptionsForLocation(LOCATION4_ID);
		Assert.assertEquals(options.getEmailNotification(), Boolean.valueOf(false));
		Assert.assertEquals(optionsLogic.getNotificationSet(LOCATION4_ID).size(),0);
	}
	
	/**
	 * Test getOptionsById
	 */
	@Test
	public void testGetById() {
		QnaOptions optionsByLoc = optionsLogic.getOptionsForLocation(LOCATION1_ID);
		QnaOptions optionsById = optionsLogic.getOptionsById(optionsByLoc.getId());
		Assert.assertEquals(optionsByLoc.getId(), optionsById.getId());
		Assert.assertEquals(LOCATION1_ID, optionsById.getLocation());
	}
	
	/**
	 * Test default options with no properties set
	 */
	@Test
	public void testDefaultNoProperties() {
		String locationId = LOCATION2_ID;
		QnaOptions options = optionsLogic.getOptionsForLocation(locationId);
		Assert.assertFalse(options.getAnonymousAllowed());
		Assert.assertTrue(options.isModerated());
		Assert.assertEquals(QnaConstants.SITE_CONTACT,options.getEmailNotificationType());
		Assert.assertFalse(options.getEmailNotification());
		Assert.assertEquals(QnaConstants.CATEGORY_VIEW, options.getDefaultStudentView());
		Assert.assertFalse(options.getAllowUnknownMobile());
		Assert.assertEquals(Integer.valueOf(1), options.getMobileAnswersNr());
	}
	
	/**
	 * Test default options with moderated property set to false 
	 */
	@Test
	public void testDefaultNotModerated() {
		String locationId = LOCATION2_ID;
		serverConfigurationServiceStub.setProperty("qna.default.moderated", false);
		QnaOptions options = optionsLogic.getOptionsForLocation(locationId);
		Assert.assertFalse(options.isModerated());
	}

	/**
	 * Test default options with moderated property set to true 
	 */
	@Test
	public void testDefaultModerated() {
		String locationId = LOCATION2_ID;
		serverConfigurationServiceStub.setProperty("qna.default.moderated", true);
		QnaOptions options = optionsLogic.getOptionsForLocation(locationId);
		Assert.assertTrue(options.isModerated());
	}
	
	/**
	 * Test default options with anonymous property set to true 
	 */
	@Test
	public void testDefaultAnonymousAllowed() {
		String locationId = LOCATION2_ID;
		serverConfigurationServiceStub.setProperty("qna.default.anonymous", true);
		QnaOptions options = optionsLogic.getOptionsForLocation(locationId);
		Assert.assertTrue(options.getAnonymousAllowed());
	}

	/**
	 * Test default options with anonymous property set to false 
	 */
	@Test
	public void testDefaultAnonymousNotAllowed() {
		String locationId = LOCATION2_ID;
		serverConfigurationServiceStub.setProperty("qna.default.anonymous", false);
		QnaOptions options = optionsLogic.getOptionsForLocation(locationId);
		Assert.assertFalse(options.getAnonymousAllowed());
	}
	
	/**
	 * Test default options with allow unknown mobile number as true
	 */
	@Test
	public void testDefaultAllowUnknownMobile() {
		String locationId = LOCATION2_ID;
		serverConfigurationServiceStub.setProperty("qna.default.allow-unknown-mobile", true);
		QnaOptions options = optionsLogic.getOptionsForLocation(locationId);
		Assert.assertTrue(options.getAllowUnknownMobile());
	}
	
	/**
	 * Test default option for number of answers returned by mobile command
	 */
	@Test
	public void testDefaultMobileAnswerNr() {
		String locationId = LOCATION2_ID;
		serverConfigurationServiceStub.setProperty("qna.default.mobile-answers-nr", 3);
		QnaOptions options = optionsLogic.getOptionsForLocation(locationId);
		Assert.assertEquals(Integer.valueOf(3), options.getMobileAnswersNr());
	}
	
	/**
	 * Test default options with allow unknown mobile number as false
	 */
	@Test
	public void testDefaultNotAllowUnknownMobile() {
		String locationId = LOCATION2_ID;
		serverConfigurationServiceStub.setProperty("qna.default.allow-unknown-mobile", false);
		QnaOptions options = optionsLogic.getOptionsForLocation(locationId);
		Assert.assertFalse(options.getAllowUnknownMobile());
	}
	
	/**
	 * Test default options with student view property set to most_popular 
	 */
	@Test
	public void testDefaultMostPopularView() {
		String locationId = LOCATION2_ID;
		serverConfigurationServiceStub.setProperty("qna.default.view", QnaConstants.MOST_POPULAR_VIEW);
		QnaOptions options = optionsLogic.getOptionsForLocation(locationId);
		Assert.assertEquals(QnaConstants.MOST_POPULAR_VIEW, options.getDefaultStudentView());
	}

	/**
	 * Test default options with student view property set to category 
	 */
	@Test
	public void testDefaultCategoryView() {
		String locationId = LOCATION2_ID;
		serverConfigurationServiceStub.setProperty("qna.default.view", QnaConstants.CATEGORY_VIEW);
		QnaOptions options = optionsLogic.getOptionsForLocation(locationId);
		Assert.assertEquals(QnaConstants.CATEGORY_VIEW, options.getDefaultStudentView());
	}
	
	/**
	 * Test default options with student view property set to invalid
	 */
	@Test
	public void testDefaultInvalidView() {
		String locationId = LOCATION2_ID;
		serverConfigurationServiceStub.setProperty("qna.default.view", "invalid");
		QnaOptions options = optionsLogic.getOptionsForLocation(locationId);
		Assert.assertEquals(QnaConstants.CATEGORY_VIEW, options.getDefaultStudentView());
	}
	
	/**
	 * Test default options with notification property set to none 
	 */
	@Test
	public void testDefaultNoNotification() {
		String locationId = LOCATION2_ID;
		serverConfigurationServiceStub.setProperty("qna.default.notification", "none");
		QnaOptions options = optionsLogic.getOptionsForLocation(locationId);
		Assert.assertFalse(options.getEmailNotification());
		Assert.assertEquals(QnaConstants.SITE_CONTACT, options.getEmailNotificationType());
	}

	/**
	 * Test default options with notification property set to invalid 
	 */
	@Test
	public void testDefaultInvalidNotification() {
		String locationId = LOCATION2_ID;
		serverConfigurationServiceStub.setProperty("qna.default.notification", "invalid");
		QnaOptions options = optionsLogic.getOptionsForLocation(locationId);
		Assert.assertFalse(options.getEmailNotification());
		Assert.assertEquals(QnaConstants.SITE_CONTACT, options.getEmailNotificationType());
	}
	
	/**
	 * Test default options with notification property set to site_contact 
	 */
	@Test
	public void testDefaultSiteContactNotification() {
		String locationId = LOCATION2_ID;
		serverConfigurationServiceStub.setProperty("qna.default.notification", QnaConstants.SITE_CONTACT);
		QnaOptions options = optionsLogic.getOptionsForLocation(locationId);
		Assert.assertTrue(options.getEmailNotification());
		Assert.assertEquals(QnaConstants.SITE_CONTACT, options.getEmailNotificationType());
	}
	
	/**
	 * Test default options with notification property set to update 
	 */
	@Test
	public void testDefaultUpdateNotification() {
		String locationId = LOCATION2_ID;
		serverConfigurationServiceStub.setProperty("qna.default.notification", QnaConstants.UPDATE_RIGHTS);
		QnaOptions options = optionsLogic.getOptionsForLocation(locationId);
		Assert.assertTrue(options.getEmailNotification());
		Assert.assertEquals(QnaConstants.UPDATE_RIGHTS, options.getEmailNotificationType());		
	}
	
	/**
	 * Test default options with notification property set to valid custom mail list 
	 */
	@Test
	public void testDefaultValidCustomNotification() {
		String locationId = LOCATION2_ID;
		serverConfigurationServiceStub.setProperty("qna.default.notification",USER_CUSTOM_EMAIL_VALID);
		QnaOptions options = optionsLogic.getOptionsForLocation(locationId);
		Assert.assertTrue(options.getEmailNotification());
		
		Assert.assertEquals(QnaConstants.CUSTOM_LIST, options.getEmailNotificationType());
		
		Set<QnaCustomEmail> customEmails = options.getCustomEmails();
		Assert.assertEquals(customEmails.size(), 3);
		
		boolean foundEmail1 = false;
		boolean foundEmail2 = false;
		boolean foundEmail3 = false;
		
		for (QnaCustomEmail qnaCustomEmail : customEmails) {
			Assert.assertNotNull(qnaCustomEmail.getId());
			if(qnaCustomEmail.getEmail().equals(USER_CUSTOM_EMAIL1)) {
				foundEmail1 = true;
			} else if (qnaCustomEmail.getEmail().equals(USER_CUSTOM_EMAIL2)) {
				foundEmail2 = true;
			} else if (qnaCustomEmail.getEmail().equals(USER_CUSTOM_EMAIL3)) {
				foundEmail3 = true;
			}
		}
		Assert.assertTrue(foundEmail1);
		Assert.assertTrue(foundEmail2);
		Assert.assertTrue(foundEmail3);
	}
	
	/**
	 * Test default options with notification property set to custom mail list with some valid emails 
	 */
	@Test
	public void testDefaultSomeValidCustomNotification() {
		String locationId = LOCATION2_ID;
		serverConfigurationServiceStub.setProperty("qna.default.notification",USER_CUSTOM_EMAIL_INVALID);
		QnaOptions options = optionsLogic.getOptionsForLocation(locationId);
		Assert.assertTrue(options.getEmailNotification());
		Assert.assertEquals(QnaConstants.CUSTOM_LIST, options.getEmailNotificationType());
		
		Set<QnaCustomEmail> customEmails = options.getCustomEmails();
		Assert.assertEquals(customEmails.size(), 1);
		boolean contains = false;
		for (QnaCustomEmail qnaCustomEmail : customEmails) {
			if(qnaCustomEmail.getEmail().equals(USER_CUSTOM_EMAIL3)){
				contains = true;
			}
		}
		Assert.assertTrue(contains);
	}
	
	/**
	 * Test that null mobile answers number return zero
	 */
	@Test
	public void testNullMobileAnswersNrReturnZero() {
		QnaOptions options = optionsLogic.getOptionsForLocation(LOCATION1_ID);
		options.setMobileAnswersNr(null);
		try {
			externalLogicStub.currentUserId = USER_UPDATE;
			optionsLogic.saveOptions(options, LOCATION1_ID);
		} catch (SecurityException e) {
			Assert.fail("Should have thrown exception");
		}
		
		Assert.assertEquals(Integer.valueOf(0), options.getMobileAnswersNr());
	}
}
