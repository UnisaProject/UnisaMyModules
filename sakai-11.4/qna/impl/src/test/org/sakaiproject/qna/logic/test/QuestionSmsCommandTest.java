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

import static org.sakaiproject.qna.logic.test.TestDataPreload.LOCATION1_ID;
import static org.sakaiproject.qna.logic.test.TestDataPreload.USER_UPDATE;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.sakaiproject.qna.dao.QnaDao;
import org.sakaiproject.qna.logic.impl.CategoryLogicImpl;
import org.sakaiproject.qna.logic.impl.OptionsLogicImpl;
import org.sakaiproject.qna.logic.impl.PermissionLogicImpl;
import org.sakaiproject.qna.logic.impl.QuestionLogicImpl;
import org.sakaiproject.qna.logic.impl.sms.QuestionSmsCommand;
import org.sakaiproject.qna.logic.test.stubs.DeveloperHelperServiceStub;
import org.sakaiproject.qna.logic.test.stubs.ExternalEventLogicStub;
import org.sakaiproject.qna.logic.test.stubs.ExternalLogicStub;
import org.sakaiproject.qna.logic.test.stubs.NotificationLogicStub;
import org.sakaiproject.qna.logic.test.stubs.QnaBundleLogicStub;
import org.sakaiproject.qna.logic.test.stubs.ServerConfigurationServiceStub;
import org.sakaiproject.qna.model.QnaOptions;
import org.sakaiproject.qna.model.QnaQuestion;
import org.sakaiproject.sms.logic.incoming.ParsedMessage;
import org.sakaiproject.sms.logic.incoming.ShortMessageCommand;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

@DirtiesContext
@ContextConfiguration(locations={
		"/hibernate-test.xml",
		"/spring-hibernate.xml"})
public class QuestionSmsCommandTest extends
	AbstractTransactionalJUnit4SpringContextTests {

	QuestionSmsCommand questionSmsCommand;
	OptionsLogicImpl optionsLogic;
	QuestionLogicImpl questionLogic;
	PermissionLogicImpl permissionLogic;
	CategoryLogicImpl categoryLogic;
	
	private final ExternalLogicStub externalLogicStub = new ExternalLogicStub();
	private final ExternalEventLogicStub externalEventLogicStub = new ExternalEventLogicStub();
	private final QnaBundleLogicStub bundleLogicStub = new QnaBundleLogicStub();
	private final ServerConfigurationServiceStub serverConfigurationServiceStub = new ServerConfigurationServiceStub();	
	private final DeveloperHelperServiceStub developerHelperServiceStub = new DeveloperHelperServiceStub();
	private final NotificationLogicStub notificationLogicStub = new NotificationLogicStub();
	
	@SuppressWarnings("unused")
	private final TestDataPreload tdp = new TestDataPreload();

	private static Log log = LogFactory.getLog(QuestionSmsCommandTest.class);
	private static String CMD = "QUESTION";
	private static String SITE = "ref-1111111";

	// run this before each test starts
	@Before
	public void onSetUpBeforeTransaction() throws Exception {
		// load the spring created dao class bean from the Spring Application
		// Context
		QnaDao dao = (QnaDao) applicationContext.getBean("org.sakaiproject.qna.dao.impl.QnaDaoTarget");
		if (dao == null) {
			log.error("onSetUpBeforeTransaction: DAO could not be retrieved from spring context");
		}
			
		permissionLogic = new PermissionLogicImpl();
		permissionLogic.setExternalLogic(externalLogicStub);
		
		// create and setup options
		optionsLogic = new OptionsLogicImpl();
		optionsLogic.setDao(dao);
		optionsLogic.setPermissionLogic(permissionLogic);
		optionsLogic.setExternalLogic(externalLogicStub);
		optionsLogic.setExternalEventLogic(externalEventLogicStub);
		optionsLogic.setServerConfigurationService(serverConfigurationServiceStub);
		
		// create and setup category logic
		categoryLogic = new CategoryLogicImpl();
		categoryLogic.setDao(dao);
		categoryLogic.setExternalEventLogic(externalEventLogicStub);
		categoryLogic.setExternalLogic(externalLogicStub);
		categoryLogic.setPermissionLogic(permissionLogic);
		categoryLogic.setQnaBundleLogic(bundleLogicStub);
		
		// create and setup the question logic
		questionLogic = new QuestionLogicImpl();
		questionLogic.setDao(dao);
		questionLogic.setPermissionLogic(permissionLogic);
		questionLogic.setOptionsLogic(optionsLogic);
		questionLogic.setExternalLogic(externalLogicStub);
		questionLogic.setExternalEventLogic(externalEventLogicStub);
		questionLogic.setDeveloperHelperService(developerHelperServiceStub);
		questionLogic.setNotificationLogic(notificationLogicStub);
		questionLogic.setCategoryLogic(categoryLogic);
		
		questionSmsCommand = new QuestionSmsCommand();
		questionSmsCommand.setOptionsLogic(optionsLogic);
		questionSmsCommand.setQnaBundleLogic(bundleLogicStub);
		questionSmsCommand.setQuestionLogic(questionLogic);
		questionSmsCommand.setCategoryLogic(categoryLogic);
		questionSmsCommand.setExternalLogic(externalLogicStub);
		questionSmsCommand.setPermissionLogic(permissionLogic);

	}
	
	/**
	 * Test empty or null body
	 */
	@Test
	public void testEmptyBody() {
		Assert.assertEquals("qna.sms.no-question-text", questionSmsCommand.execute(
				new ParsedMessage(USER_UPDATE, CMD, SITE, "", 1), ShortMessageCommand.MESSAGE_TYPE_SMS, "1234"));
		String nullString = null;
		Assert.assertEquals("qna.sms.no-question-text", questionSmsCommand.execute(
				new ParsedMessage(USER_UPDATE, CMD, SITE, nullString, 0), ShortMessageCommand.MESSAGE_TYPE_SMS,"1234"));
		Assert.assertEquals(0, questionLogic.getAllQuestions(LOCATION1_ID).size()); 
	}
	
	/**
	 * Test saving of question
	 */
	@Test
	public void testSaveQuestion() {
		Assert.assertEquals("qna.sms.question-posted.no-replies", questionSmsCommand.execute(
				new ParsedMessage(USER_UPDATE, CMD, SITE, "new question", 1), ShortMessageCommand.MESSAGE_TYPE_SMS,"1234" ));
		String id = bundleLogicStub.getLastParameters()[1].toString();
		QnaQuestion question = questionLogic.getQuestionById(Long.valueOf(id));
		Assert.assertEquals("new question", question.getQuestionText());
		Assert.assertEquals(USER_UPDATE, question.getOwnerId());
		Assert.assertEquals("1234", question.getOwnerMobileNr());
		Assert.assertEquals(1, questionLogic.getAllQuestions(LOCATION1_ID).size()); 
	}

	@Test
	public void testSaveQuestionNullUserId() {
		externalLogicStub.currentUserId = USER_UPDATE;
		QnaOptions options = optionsLogic.getOptionsForLocation(LOCATION1_ID);
		options.setAllowUnknownMobile(true);
		options.setSmsNotification(false);
		optionsLogic.saveOptions(options, LOCATION1_ID);
		Assert.assertTrue(options.getAllowUnknownMobile());
		Assert.assertEquals("qna.sms.question-posted.no-replies", questionSmsCommand.execute(
				new ParsedMessage(null, CMD, SITE, "new question", 1), ShortMessageCommand.MESSAGE_TYPE_SMS,"1234" ));
		
		String id = bundleLogicStub.getLastParameters()[1].toString();
		QnaQuestion question = questionLogic.getQuestionById(Long.valueOf(id));
		Assert.assertEquals("new question", question.getQuestionText());
		Assert.assertEquals(null, question.getOwnerId());
		Assert.assertEquals("1234", question.getOwnerMobileNr());
		Assert.assertEquals(1, questionLogic.getAllQuestions(LOCATION1_ID).size()); 
	}

	@Test
	public void testSaveQuestionNullUserIdAnonMobileNotAllowed() {
		QnaOptions options = optionsLogic.getOptionsForLocation(LOCATION1_ID);
		Assert.assertFalse(options.getAllowUnknownMobile());
		Assert.assertEquals("qna.sms.anonymous-not-allowed", questionSmsCommand.execute(
				new ParsedMessage(null, CMD, SITE, "new question", 1), ShortMessageCommand.MESSAGE_TYPE_SMS,"1234" ));
		Assert.assertEquals(0, questionLogic.getAllQuestions(LOCATION1_ID).size()); 
	}

	@Test
	public void testSaveQuestionNullUserIdNotModerated() {
		externalLogicStub.currentUserId = USER_UPDATE;
		QnaOptions options = optionsLogic.getOptionsForLocation(LOCATION1_ID);
		options.setAllowUnknownMobile(true);
		options.setModerated(false);
		options.setSmsNotification(false);
		optionsLogic.saveOptions(options, LOCATION1_ID);
		Assert.assertTrue(options.getAllowUnknownMobile());
		Assert.assertEquals("qna.sms.question-posted.no-replies", questionSmsCommand.execute(
				new ParsedMessage(null, CMD, SITE, "new question", 1), ShortMessageCommand.MESSAGE_TYPE_SMS,"1234" ));
		String id = bundleLogicStub.getLastParameters()[1].toString();
		QnaQuestion question = questionLogic.getQuestionById(Long.valueOf(id));
		Assert.assertEquals("new question", question.getQuestionText());
		Assert.assertEquals(null, question.getOwnerId());
		Assert.assertEquals("1234", question.getOwnerMobileNr());
		Assert.assertEquals(1, questionLogic.getAllQuestions(LOCATION1_ID).size()); 
		Assert.assertNotNull(question.getCategory());
	}
	
}
