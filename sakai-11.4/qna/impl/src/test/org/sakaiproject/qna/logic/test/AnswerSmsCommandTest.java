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
import org.sakaiproject.qna.logic.impl.OptionsLogicImpl;
import org.sakaiproject.qna.logic.impl.PermissionLogicImpl;
import org.sakaiproject.qna.logic.impl.QuestionLogicImpl;
import org.sakaiproject.qna.logic.impl.sms.AnswerSmsCommand;
import org.sakaiproject.qna.logic.test.stubs.DeveloperHelperServiceStub;
import org.sakaiproject.qna.logic.test.stubs.ExternalEventLogicStub;
import org.sakaiproject.qna.logic.test.stubs.ExternalLogicStub;
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
public class AnswerSmsCommandTest extends
	AbstractTransactionalJUnit4SpringContextTests {

	AnswerSmsCommand answerSmsCommand;
	OptionsLogicImpl optionsLogic;
	QuestionLogicImpl questionLogic;
	PermissionLogicImpl permissionLogic;
	
	private final ExternalLogicStub externalLogicStub = new ExternalLogicStub();
	private final ExternalEventLogicStub externalEventLogicStub = new ExternalEventLogicStub();
	private final QnaBundleLogicStub bundleLogicStub = new QnaBundleLogicStub();
	private final ServerConfigurationServiceStub serverConfigurationServiceStub = new ServerConfigurationServiceStub();	
	private final DeveloperHelperServiceStub developerHelperServiceStub = new DeveloperHelperServiceStub();
	
	private final TestDataPreload tdp = new TestDataPreload();

	private static Log log = LogFactory.getLog(AnswerSmsCommandTest.class);
	
	private static String CMD = "ANSWER";

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
		
		answerSmsCommand = new AnswerSmsCommand();
		answerSmsCommand.setOptionsLogic(optionsLogic);
		answerSmsCommand.setPermissionLogic(permissionLogic);
		answerSmsCommand.setQnaBundleLogic(bundleLogicStub);
		answerSmsCommand.setQuestionLogic(questionLogic);
		answerSmsCommand.setExternalLogic(externalLogicStub);
		
		// preload testData
		tdp.preloadTestData(dao);
	}
	
	/**
	 * Test empty or null body
	 */
	@Test
	public void testEmptyBody() {
		Assert.assertEquals("qna.sms.no-question-id", answerSmsCommand.execute(
				new ParsedMessage(USER_UPDATE, CMD, null, "", 1), ShortMessageCommand.MESSAGE_TYPE_SMS, "1234"));
		String nullString = null;
		Assert.assertEquals("qna.sms.no-question-id", answerSmsCommand.execute(
				new ParsedMessage(USER_UPDATE, CMD, null, nullString, 1), ShortMessageCommand.MESSAGE_TYPE_SMS,"1234"));
		Assert.assertEquals(5, questionLogic.getAllQuestions(LOCATION1_ID).size()); 
	}
	
	/**
	 * Test invalid question id supplied
	 */
	public void testInvalidQuestion() {
		Assert.assertEquals(5, questionLogic.getAllQuestions(LOCATION1_ID).size()); 
		Assert.assertEquals("qna.sms.invalid-question-id", answerSmsCommand.execute(
				new ParsedMessage(USER_UPDATE, CMD, null, "0", 1), ShortMessageCommand.MESSAGE_TYPE_SMS,"1234"));
	}
	
	/**
	 * Test get single answer
	 */
	@Test
	public void testGetAnswer() {
		QnaOptions options = optionsLogic.getOptionsForLocation(LOCATION1_ID);
		Assert.assertEquals(Integer.valueOf(1), options.getMobileAnswersNr()); 
		
		QnaQuestion question1 = questionLogic.getAllQuestions(LOCATION1_ID).get(1);
		Assert.assertEquals("qna.sms.answer.one", answerSmsCommand.execute(
				new ParsedMessage(USER_UPDATE, CMD, null, question1.getId().toString(), 1), ShortMessageCommand.MESSAGE_TYPE_SMS, "1234" ));
	}

	/**
	 * Test get multiple answer
	 */
	@Test
	public void testGetMultipleAnswer() {
		QnaOptions options = optionsLogic.getOptionsForLocation(LOCATION1_ID);
		options.setMobileAnswersNr(2);
		externalLogicStub.currentUserId = USER_UPDATE;
		optionsLogic.saveOptions(options, LOCATION1_ID);
		
		Assert.assertEquals(Integer.valueOf(2), options.getMobileAnswersNr()); 
		
		QnaQuestion question1 = questionLogic.getAllQuestions(LOCATION1_ID).get(1);
		
		String answers = answerSmsCommand.execute(
				new ParsedMessage(USER_UPDATE, CMD, null, question1.getId().toString(), 1), 
				ShortMessageCommand.MESSAGE_TYPE_SMS, "1234");
		Assert.assertEquals("qna.sms.answer.many", answers);
	}
	

	/**
	 * Test with no mobile answers
	 */
	@Test
	public void testGetNoMobileAnswers() {
		QnaOptions options = optionsLogic.getOptionsForLocation(LOCATION1_ID);
		options.setMobileAnswersNr(0);
		externalLogicStub.currentUserId = USER_UPDATE;
		optionsLogic.saveOptions(options, LOCATION1_ID);
		
		Assert.assertEquals(Integer.valueOf(0), options.getMobileAnswersNr()); 
		
		QnaQuestion question1 = questionLogic.getAllQuestions(LOCATION1_ID).get(1);
		Assert.assertEquals("qna.sms.no-mobile-answers", answerSmsCommand.execute(
				new ParsedMessage(USER_UPDATE, CMD, null, question1.getId().toString(), 1),
				ShortMessageCommand.MESSAGE_TYPE_SMS, "1234"));
	}
	
	/**
	 * Test with no answers for question
	 */
	@Test
	public void testGetNoAnswers() {
	
		Assert.assertEquals(0, tdp.question3_location1.getAnswers().size()); 
		Assert.assertEquals("qna.sms.no-answers-found", answerSmsCommand.execute(
				new ParsedMessage(USER_UPDATE, CMD, null, tdp.question3_location1.getId().toString(), 1),
				ShortMessageCommand.MESSAGE_TYPE_SMS, "1234" ));
	}
	
	/**
	 * Test that html tags are stripped from answers for answer SMS command 
	 * (cannot test this at present)
	 */
	/*
	public void testGetAnswersStripTags() {
		QnaOptions options = optionsLogic.getOptionsForLocation(LOCATION3_ID);
		options.setMobileAnswersNr(2);
		externalLogicStub.currentUserId = USER_LOC_3_UPDATE_1;
		optionsLogic.saveOptions(options, LOCATION3_ID);
		QnaQuestion question = questionLogic.getAllQuestions(LOCATION3_ID).get(0);
		assertEquals(ANSWER_TEXT_3, question.getAnswers().get(0).getAnswerText());
		assertEquals("<ANSWER> 1", answerSmsCommand.execute(
				new ParsedMessage(USER_LOC_3_UPDATE_1, CMD, null, question.getId().toString(), 1), 
				ShortMessageCommand.MESSAGE_TYPE_SMS, "1234" ));
	}
	*/
}
