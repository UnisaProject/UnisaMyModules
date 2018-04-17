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
import static org.sakaiproject.qna.logic.test.TestDataPreload.LOCATION3_ID;
import static org.sakaiproject.qna.logic.test.TestDataPreload.USER_LOC_3_NO_UPDATE_1;
import static org.sakaiproject.qna.logic.test.TestDataPreload.USER_LOC_3_UPDATE_1;
import static org.sakaiproject.qna.logic.test.TestDataPreload.USER_NO_UPDATE;
import static org.sakaiproject.qna.logic.test.TestDataPreload.USER_UPDATE;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.sakaiproject.qna.dao.QnaDao;
import org.sakaiproject.qna.logic.exceptions.QnaConfigurationException;
import org.sakaiproject.qna.logic.impl.AnswerLogicImpl;
import org.sakaiproject.qna.logic.impl.CategoryLogicImpl;
import org.sakaiproject.qna.logic.impl.OptionsLogicImpl;
import org.sakaiproject.qna.logic.impl.PermissionLogicImpl;
import org.sakaiproject.qna.logic.impl.QuestionLogicImpl;
import org.sakaiproject.qna.logic.test.stubs.DeveloperHelperServiceStub;
import org.sakaiproject.qna.logic.test.stubs.ExternalEventLogicStub;
import org.sakaiproject.qna.logic.test.stubs.ExternalLogicStub;
import org.sakaiproject.qna.logic.test.stubs.NotificationLogicStub;
import org.sakaiproject.qna.model.QnaAnswer;
import org.sakaiproject.qna.model.QnaQuestion;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

@DirtiesContext
@ContextConfiguration(locations={
		"/hibernate-test.xml",
		"/spring-hibernate.xml"})	
public class AnswerLogicImplTest extends
	AbstractTransactionalJUnit4SpringContextTests {

	AnswerLogicImpl answerLogic;
	PermissionLogicImpl permissionLogic;
	QuestionLogicImpl questionLogic;
	OptionsLogicImpl optionsLogic;
	CategoryLogicImpl categoryLogic;
	
	QnaDao dao;

	private static Log log = LogFactory.getLog(AnswerLogicImplTest.class);

	private ExternalLogicStub externalLogicStub = new ExternalLogicStub();
	private ExternalEventLogicStub externalEventLogicStub = new ExternalEventLogicStub();
	
	private NotificationLogicStub notificationLogicStub = new NotificationLogicStub();
	private DeveloperHelperServiceStub developerHelperServiceStub = new DeveloperHelperServiceStub();
	
	private TestDataPreload tdp = new TestDataPreload();

	// run this before each test starts
	@Before
	public void onSetUpBeforeTransaction() throws Exception {
		// load the spring created dao class bean from the Spring Application
		// Context
		dao = (QnaDao) applicationContext.getBean("org.sakaiproject.qna.dao.impl.QnaDaoTarget");
		if (dao == null) {
			log.error("onSetUpBeforeTransaction: DAO could not be retrieved from spring context");
			return;
		}

		permissionLogic = new PermissionLogicImpl();
		permissionLogic.setExternalLogic(externalLogicStub);

		// create and setup OptionsLogic
		optionsLogic = new OptionsLogicImpl();
		optionsLogic.setDao(dao);
		optionsLogic.setPermissionLogic(permissionLogic);
		optionsLogic.setExternalLogic(externalLogicStub);
		optionsLogic.setExternalEventLogic(externalEventLogicStub);

		// create and setup CategoryLogic
		categoryLogic = new CategoryLogicImpl();
		categoryLogic.setDao(dao);
		categoryLogic.setExternalLogic(externalLogicStub);
		categoryLogic.setPermissionLogic(permissionLogic);
		categoryLogic.setExternalEventLogic(externalEventLogicStub);
		
		// create and setup the object to be tested
		questionLogic = new QuestionLogicImpl();
		questionLogic.setDao(dao);
		questionLogic.setPermissionLogic(permissionLogic);
		questionLogic.setOptionsLogic(optionsLogic);
		questionLogic.setExternalLogic(externalLogicStub);
		questionLogic.setCategoryLogic(categoryLogic);
		questionLogic.setExternalEventLogic(externalEventLogicStub);
		questionLogic.setDeveloperHelperService(developerHelperServiceStub);
		
		// create and setup answer object
		answerLogic = new AnswerLogicImpl();
		answerLogic.setDao(dao);
		answerLogic.setExternalLogic(externalLogicStub);
		answerLogic.setPermissionLogic(permissionLogic);
		answerLogic.setQuestionLogic(questionLogic);
		answerLogic.setOptionsLogic(optionsLogic);
		answerLogic.setNotificationLogic(notificationLogicStub);
		answerLogic.setExternalEventLogic(externalEventLogicStub);
		
		// preload testData
		tdp.preloadTestData(dao);
	}

	/**
	 * Test retrieval of answer by id
	 */
	@Test
	public void testGetAnswerById() {
		QnaAnswer answer = answerLogic.getAnswerById(questionLogic
				.getQuestionById(tdp.question2_location1.getId()).getAnswers()
				.get(0).getId());

		Assert.assertNotNull(answer);
		Assert.assertEquals(answer.getAnswerText(), tdp.answer1_location1
				.getAnswerText());
		Assert.assertEquals(answer.getId(), tdp.answer1_location1.getId());
	}

	/**
	 * Test retrieval of all answers linked to a question
	 */
	@Test
	public void testGetAnswersToQuestion() {
		QnaQuestion question = questionLogic
				.getQuestionById(tdp.question2_location1.getId());
		Assert.assertNotNull(question);

		List<QnaAnswer> answers = question.getAnswers();
		Assert.assertEquals(answers.size(), 2);

		answers.contains(tdp.answer1_location1);
		answers.contains(tdp.answer2_location1);
	}

	/**
	 * Test add answer to question
	 */
	@Test
	public void testSaveAnswer() {

		String answerText = "Test add answer text";

		// Add answer with an invalid userid
		try {
			externalLogicStub.currentUserId = USER_LOC_3_NO_UPDATE_1;
			QnaAnswer answer = new QnaAnswer();
			answer.setAnswerText(answerText);
			answer.setQuestion(tdp.question3_location1);
			answer.setAnonymous(true);
			answer.setPrivateReply(false);
			answerLogic.saveAnswer(answer, LOCATION1_ID);
			Assert.fail("Should have thrown exception");
		} catch (SecurityException e) {
			Assert.assertNotNull(e);
		}

		// Add answer with an invalid configuration (anonymous reply not
		// allowed)
		try {
			externalLogicStub.currentUserId = USER_UPDATE;
			QnaAnswer answer = new QnaAnswer();
			answer.setAnswerText(answerText);
			answer.setQuestion(tdp.question3_location1);
			answer.setAnonymous(true);
			answer.setPrivateReply(false);
			answerLogic.saveAnswer(answer, LOCATION1_ID);;
			Assert.fail("Should have caught the exception");
		} catch (QnaConfigurationException e) {
			Assert.assertNotNull(e);
		}

		// Add answer with valid configuration
		try {
			externalLogicStub.currentUserId = USER_LOC_3_UPDATE_1;
					
			QnaAnswer answer = new QnaAnswer();
			answer.setAnswerText(answerText);
			answer.setQuestion(tdp.question1_location3);
			answer.setAnonymous(true);
			answer.setPrivateReply(false);
			answerLogic.saveAnswer(answer, LOCATION3_ID);
			
			QnaQuestion question = questionLogic
					.getQuestionById(tdp.question1_location3.getId());

			Assert.assertEquals(2, question.getAnswers().size());
			boolean found = false;
			for (QnaAnswer answer1 : question.getAnswers()) {
				Assert.assertNotNull(answer1.getId());
				if (answer1.getAnswerText().equals(answerText))
					found = true;
			}
			Assert.assertTrue(found);

		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("Should not have caught exception");
		}
	}

	/**
	 * Test add private reply
	 */
	@Test
	public void testAddPrivateReply() {
		try {
			externalLogicStub.currentUserId = USER_UPDATE;
			String answerText = "Private Reply";
			
			QnaAnswer answer = new QnaAnswer();
			answer.setAnswerText(answerText);
			answer.setQuestion(tdp.question1_location1);
			answer.setAnonymous(false);
			answer.setPrivateReply(true);
			answerLogic.saveAnswer(answer, LOCATION1_ID);
			

			List<QnaAnswer> answersPrivateReply = tdp.question1_location1
					.getAnswers();

			boolean found = false;
			for (QnaAnswer qnaAnswer : answersPrivateReply) {
				if (qnaAnswer.getAnswerText().equals(answerText)) {
					Assert.assertNotNull(qnaAnswer.getId());
					Assert.assertFalse(qnaAnswer.isAnonymous());
					Assert.assertTrue(qnaAnswer.isPrivateReply());
					Assert.assertEquals(qnaAnswer.getOwnerId(), USER_UPDATE);
					found = true;
				}
			}
			Assert.assertTrue(found);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("This should not have thrown this exception");
		}
	}

	/**
	 * Test removal of answer from question
	 */
	@Test
	public void testRemoveAnswerFromQuestion() {
		QnaQuestion questionRemove = questionLogic
				.getQuestionById(tdp.question2_location1.getId());
		List<QnaAnswer> answersBeforeRemove = questionRemove.getAnswers();

		Assert.assertEquals(answersBeforeRemove.size(), 2);

		// invalid permissions
		externalLogicStub.currentUserId = USER_NO_UPDATE;

		try {
			answerLogic.removeAnswerFromQuestion(tdp.answer1_location1.getId(),
					questionRemove.getId(), LOCATION1_ID);
			answerLogic.removeAnswerFromQuestion(tdp.answer2_location1.getId(),
					questionRemove.getId(), LOCATION1_ID);
		} catch (SecurityException expected) {
			Assert.assertNotNull(expected);
		}

		Assert.assertEquals(answersBeforeRemove.size(), 2);

		// valid permissions
		
		externalLogicStub.currentUserId = USER_UPDATE;
		
		answerLogic.removeAnswerFromQuestion(tdp.answer1_location1.getId(),questionRemove.getId(),LOCATION1_ID);
		answerLogic.removeAnswerFromQuestion(tdp.answer2_location1.getId(),questionRemove.getId(),LOCATION1_ID);
		
		Assert.assertNull(answerLogic.getAnswerById(tdp.answer1_location1.getId()));
		Assert.assertNull(answerLogic.getAnswerById(tdp.answer2_location1.getId()));
	}

	/**
	 * Test approval of answer
	 */
	@Test
	public void testApproveAnswer() {
		QnaAnswer answerToBeApproved = answerLogic.getAnswerById(tdp.answer2_location1.getId());
		Assert.assertFalse(answerToBeApproved.isApproved());
		
		// Test with invalid
		externalLogicStub.currentUserId = USER_NO_UPDATE;
		try {	
			answerLogic.approveAnswer(tdp.answer2_location1.getId(), LOCATION1_ID);
			Assert.fail("Should throw SecurityException");
		}
		catch (SecurityException expected) {
			Assert.assertNotNull(expected);
		}
		
		// Test with valid
		externalLogicStub.currentUserId = USER_UPDATE;
		try {	
			answerLogic.approveAnswer(tdp.answer2_location1.getId(), LOCATION1_ID);
		}
		catch (Exception notExpected) {
			Assert.fail("Should not throw Exception");
		}

		QnaAnswer answerApproved = answerLogic.getAnswerById(tdp.answer2_location1.getId());
		Assert.assertTrue(answerApproved.isApproved());
	}

	/**
	 * Test withdrawal of approval
	 */
	@Test
	public void testWithdrawApproval() {
		QnaAnswer answerToWithdrawApproval = answerLogic.getAnswerById(tdp.answer1_location1.getId());
		Assert.assertTrue(answerToWithdrawApproval.isApproved());
		
		// Test with invalid
		externalLogicStub.currentUserId = USER_NO_UPDATE;
		try {	
			answerLogic.withdrawApprovalAnswer(tdp.answer1_location1.getId(), LOCATION1_ID);
			Assert.fail("Should throw SecurityException");
		}
		catch (SecurityException expected) {
			Assert.assertNotNull(expected);
		}
		
		// Test with valid
		externalLogicStub.currentUserId = USER_UPDATE;
		try {	
			answerLogic.withdrawApprovalAnswer(tdp.answer1_location1.getId(), LOCATION1_ID);
		}
		catch (Exception notExpected) {
			Assert.fail("Should not throw Exception");
		}

		QnaAnswer answerApproved = answerLogic.getAnswerById(tdp.answer1_location1.getId());
		Assert.assertFalse(answerApproved.isApproved());

	}
}
