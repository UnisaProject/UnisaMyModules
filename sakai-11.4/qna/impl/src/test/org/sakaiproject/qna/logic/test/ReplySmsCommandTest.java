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
import org.sakaiproject.qna.logic.impl.AnswerLogicImpl;
import org.sakaiproject.qna.logic.impl.OptionsLogicImpl;
import org.sakaiproject.qna.logic.impl.PermissionLogicImpl;
import org.sakaiproject.qna.logic.impl.QuestionLogicImpl;
import org.sakaiproject.qna.logic.impl.sms.ReplySmsCommand;
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
public class ReplySmsCommandTest extends
	AbstractTransactionalJUnit4SpringContextTests {

	ReplySmsCommand replySmsCommand;
	OptionsLogicImpl optionsLogic;
	QuestionLogicImpl questionLogic;
	AnswerLogicImpl answerLogic;
	PermissionLogicImpl permissionLogic;
	
	private final ExternalLogicStub externalLogicStub = new ExternalLogicStub();
	private final ExternalEventLogicStub externalEventLogicStub = new ExternalEventLogicStub();
	private final QnaBundleLogicStub bundleLogicStub = new QnaBundleLogicStub();
	private final ServerConfigurationServiceStub serverConfigurationServiceStub = new ServerConfigurationServiceStub();	
	private final DeveloperHelperServiceStub developerHelperServiceStub = new DeveloperHelperServiceStub();
	private final NotificationLogicStub notificationLogicStub = new NotificationLogicStub();
	
	private final TestDataPreload tdp = new TestDataPreload();

	private static Log log = LogFactory.getLog(ReplySmsCommandTest.class);
	
	private static String CMD = "REPLY";

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
		
		// create and setup the question logic
		questionLogic = new QuestionLogicImpl();
		questionLogic.setDao(dao);
		questionLogic.setPermissionLogic(permissionLogic);
		questionLogic.setOptionsLogic(optionsLogic);
		questionLogic.setExternalLogic(externalLogicStub);
		questionLogic.setExternalEventLogic(externalEventLogicStub);
		questionLogic.setDeveloperHelperService(developerHelperServiceStub);
		questionLogic.setNotificationLogic(notificationLogicStub);
		
		answerLogic = new AnswerLogicImpl();
		answerLogic.setDao(dao);
		answerLogic.setExternalEventLogic(externalEventLogicStub);
		answerLogic.setExternalLogic(externalLogicStub);
		answerLogic.setOptionsLogic(optionsLogic);
		answerLogic.setPermissionLogic(permissionLogic);
		answerLogic.setQuestionLogic(questionLogic);
		answerLogic.setNotificationLogic(notificationLogicStub);
		
		replySmsCommand = new ReplySmsCommand();
		replySmsCommand.setAnswerLogic(answerLogic);
		replySmsCommand.setQnaBundleLogic(bundleLogicStub);
		replySmsCommand.setQuestionLogic(questionLogic);
		replySmsCommand.setOptionsLogic(optionsLogic);
		replySmsCommand.setExternalLogic(externalLogicStub);
		replySmsCommand.setPermissionLogic(permissionLogic);

		// preload testData
		tdp.preloadTestData(dao);
	}
	 
	/**
	 * Test empty or null body
	 */
	@Test
	public void testEmptyBody() {
		Assert.assertEquals("qna.sms.no-question-id", replySmsCommand.execute(
				new ParsedMessage(USER_UPDATE, CMD, null, "", 1), ShortMessageCommand.MESSAGE_TYPE_SMS,"1234"));
		Assert.assertEquals("qna.sms.no-question-id", replySmsCommand.execute(
				new ParsedMessage(USER_UPDATE, CMD, null, null, 0), ShortMessageCommand.MESSAGE_TYPE_SMS,"1234"));
	}
	
	/**
	 * Test no answer ID supplied
	 */
	@Test
	public void testNoAnswerText() {
		QnaQuestion question = questionLogic.getAllQuestions(LOCATION1_ID).get(1);
		Assert.assertEquals(2, question.getAnswers().size());
		
		Assert.assertEquals("qna.sms.no-answer-text", replySmsCommand.execute(
				new ParsedMessage(USER_UPDATE, CMD, null, question.getId().toString(), 1), 
				ShortMessageCommand.MESSAGE_TYPE_SMS,"1234"));
	}

	/**
	 * Test no invalid question id supplied
	 */
	@Test
	public void testInvalidId() {
		QnaQuestion question = questionLogic.getQuestionById(Long.valueOf("53"));
		Assert.assertNull(question);
		Assert.assertEquals("qna.sms.invalid-question-id", replySmsCommand.execute(
				new ParsedMessage(USER_UPDATE, CMD, null, "53 reply text", 2), 
				ShortMessageCommand.MESSAGE_TYPE_SMS,"1234"));
	}

	/**
	 * Test save of answer
	 */
	@Test
	public void testSaveAnswer() {
		QnaQuestion question = questionLogic.getAllQuestions(LOCATION1_ID).get(0);
		Assert.assertEquals("qna.sms.reply-posted", replySmsCommand.execute(
				new ParsedMessage(USER_UPDATE, CMD, null, question.getId().toString() + " text", 2), 
				ShortMessageCommand.MESSAGE_TYPE_SMS,"1234"));
		Assert.assertEquals("text", question.getAnswers().get(0).getAnswerText());
	}

	/**
	 * Test save of answer (null userId)
	 */
	@Test
	public void testSaveAnswerNullUserId() {
		externalLogicStub.currentUserId = USER_UPDATE;
		QnaOptions options = optionsLogic.getOptionsForLocation(LOCATION1_ID);
		options.setAllowUnknownMobile(true);
		optionsLogic.saveOptions(options, LOCATION1_ID);
		Assert.assertTrue(options.getAllowUnknownMobile());
		
		QnaQuestion question = questionLogic.getAllQuestions(LOCATION1_ID).get(0);
		Assert.assertEquals("qna.sms.reply-posted", replySmsCommand.execute(
				new ParsedMessage(null, CMD, null, question.getId().toString() + " text", 2),
				ShortMessageCommand.MESSAGE_TYPE_SMS,"1234"));
		Assert.assertEquals("text", question.getAnswers().get(0).getAnswerText());
		Assert.assertEquals(null, question.getAnswers().get(0).getOwnerId());
		Assert.assertEquals("1234", question.getAnswers().get(0).getOwnerMobileNr());
	}
	
	/**
	 * Test save answer with null user id where anon mobile postings not allowed
	 */
	@Test
	public void testSaveAnswerNullUserIdAnonMobileNotAllowed() {
		externalLogicStub.currentUserId = USER_UPDATE;
		QnaOptions options = optionsLogic.getOptionsForLocation(LOCATION1_ID);
		options.setAllowUnknownMobile(false);
		optionsLogic.saveOptions(options, LOCATION1_ID);
		Assert.assertFalse(options.getAllowUnknownMobile());
		
		QnaQuestion question = questionLogic.getAllQuestions(LOCATION1_ID).get(0);
		Assert.assertEquals(0, question.getAnswers().size());
		Assert.assertEquals("qna.sms.anonymous-not-allowed", replySmsCommand.execute(
				new ParsedMessage(null, CMD, null, question.getId().toString() + " new question", 2),
				ShortMessageCommand.MESSAGE_TYPE_SMS,"1234"));
		Assert.assertEquals(0, questionLogic.getAllQuestions(LOCATION1_ID).get(0).getAnswers().size()); 
	}
}
