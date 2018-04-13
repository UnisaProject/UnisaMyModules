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
import org.sakaiproject.qna.logic.exceptions.AttachmentException;
import org.sakaiproject.qna.logic.exceptions.QnaConfigurationException;
import org.sakaiproject.qna.logic.impl.CategoryLogicImpl;
import org.sakaiproject.qna.logic.impl.OptionsLogicImpl;
import org.sakaiproject.qna.logic.impl.PermissionLogicImpl;
import org.sakaiproject.qna.logic.impl.QuestionLogicImpl;
import org.sakaiproject.qna.logic.test.stubs.DeveloperHelperServiceStub;
import org.sakaiproject.qna.logic.test.stubs.ExternalEventLogicStub;
import org.sakaiproject.qna.logic.test.stubs.ExternalLogicStub;
import org.sakaiproject.qna.logic.test.stubs.NotificationLogicStub;
import org.sakaiproject.qna.model.QnaQuestion;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

@DirtiesContext
@ContextConfiguration(locations={
		"/hibernate-test.xml",
		"/spring-hibernate.xml"})
public class QuestionLogicImplTest extends AbstractTransactionalJUnit4SpringContextTests {

	QuestionLogicImpl questionLogic;
	CategoryLogicImpl categoryLogic;
	OptionsLogicImpl optionsLogic;
	PermissionLogicImpl permissionLogic;
	
	private static Log log = LogFactory.getLog(OptionsLogicImplTest.class);

	private final ExternalLogicStub externalLogicStub = new ExternalLogicStub();
	private final NotificationLogicStub notificationLogicStub = new NotificationLogicStub();
	private final ExternalEventLogicStub externalEventLogicStub = new ExternalEventLogicStub();
	private final DeveloperHelperServiceStub developerHelperServiceStub = new DeveloperHelperServiceStub();
	
	private final TestDataPreload tdp = new TestDataPreload();

	// run this before each test starts
	@Before
	public void onSetUpBeforeTransaction() throws Exception {
		// load the spring created dao class bean from the Spring Application
		// Context
		QnaDao dao = (QnaDao) applicationContext.getBean("org.sakaiproject.qna.dao.impl.QnaDaoTarget");
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
		questionLogic.setNotificationLogic(notificationLogicStub);
		questionLogic.setExternalEventLogic(externalEventLogicStub);
		questionLogic.setDeveloperHelperService(developerHelperServiceStub);
		
		// preload testData
		tdp.preloadTestData(dao);
	}
	
	/**
	 * Test retrieval of question by id
	 */
	@Test
	public void testGetQuestionById() {
		QnaQuestion question = questionLogic.getQuestionById(tdp.question1_location1.getId());
		Assert.assertNotNull(question);
		Assert.assertEquals(question.getQuestionText(),tdp.question1_location1.getQuestionText());
		Assert.assertEquals(question.getId(), tdp.question1_location1.getId());
	}

	/**
	 * Test retrieval of published questions list
	 */
	@Test
	public void testGetPublishedQuestions() {
		List<QnaQuestion> questions = questionLogic.getPublishedQuestions(LOCATION1_ID);
		Assert.assertEquals(questions.size(), 3);
		
		Assert.assertTrue(questions.contains(tdp.question2_location1));
		Assert.assertTrue(questions.contains(tdp.question3_location1));
		Assert.assertTrue(questions.contains(tdp.question4_location1));
	}

	/**
	 * Test retrieval of unpublished questions list
	 */
	@Test
	public void testGetNewQuestions() {
		List<QnaQuestion> questions = questionLogic.getNewQuestions(LOCATION1_ID);
		Assert.assertEquals(questions.size(), 2);
		
		Assert.assertTrue(questions.contains(tdp.question1_location1));
		Assert.assertTrue(questions.contains(tdp.question5_location1));
	}

	/**
	 * Test modify question
	 * Test modified date updated
	 */
	@Test
	public void testModifyQuestion() {
		QnaQuestion question = questionLogic.getQuestionById(tdp.question1_location1.getId());

		question.setQuestionText("Testing update");

		// Test with invalid permissions
		try {
			externalLogicStub.currentUserId = USER_NO_UPDATE;
			questionLogic.saveQuestion(question,LOCATION1_ID);
			Assert.fail("Should have thrown exception");
		} catch (SecurityException e) {
			Assert.assertNotNull(e);
		} 

		// Test with valid permission
		try {
			externalLogicStub.currentUserId = USER_UPDATE;
			questionLogic.saveQuestion(question,LOCATION1_ID);
			QnaQuestion changedQuestion = questionLogic.getQuestionById(tdp.question1_location1.getId());
			Assert.assertEquals(changedQuestion.getQuestionText(), "Testing update");
		} catch (Exception e) {
			Assert.fail("Should not have thrown exception");
		}
	}

	/**
	 * Test saving new question in moderated location
	 */
	@Test
	public void testSaveNewQuestionModerated() {
		Assert.assertTrue(optionsLogic.getOptionsForLocation(LOCATION3_ID).isModerated());

		QnaQuestion question = new QnaQuestion();
		question.setQuestionText("blah blah blah");
		question.setAnonymous(false);
		question.setNotify(false);
		
		// Test with invalid
		externalLogicStub.currentUserId = USER_LOC_3_NO_UPDATE_1;
		try {
			questionLogic.saveQuestion(question, LOCATION3_ID);
			Assert.fail("Should have thrown exception");
		} catch (SecurityException se) {
			Assert.assertNotNull(se);
		}

		
		// Test with valid
		externalLogicStub.currentUserId = USER_LOC_3_UPDATE_1;
		try {
			questionLogic.saveQuestion(question, LOCATION3_ID);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("Should not have thrown exception");
		}

		Assert.assertEquals(question.getOwnerId(), USER_LOC_3_UPDATE_1);
		Assert.assertEquals(question.getLocation(), LOCATION3_ID);
		Assert.assertEquals(question.getViews(), Integer.valueOf(0));
		Assert.assertFalse(question.isPublished());
		Assert.assertNull(question.getCategory());

		Assert.assertTrue(questionLogic.existsQuestion(question.getId()));
	}

	/**
	 * Test saving new question in unmoderated location
	 */
	@Test
	public void testSaveNewQuestionUnmoderated() {
		Assert.assertFalse(optionsLogic.getOptionsForLocation(LOCATION1_ID).isModerated());
		externalLogicStub.currentUserId = USER_UPDATE;
		
		
		QnaQuestion question = new QnaQuestion();
		question.setQuestionText("blah blah blah");
		question.setAnonymous(false);
		question.setNotify(false);
		try {
			questionLogic.saveQuestion(question, LOCATION1_ID);
		} catch (Exception e) {
			Assert.fail("Should not have thrown exception");
		}

		Assert.assertEquals(question.getOwnerId(), USER_UPDATE);
		Assert.assertEquals(question.getLocation(), LOCATION1_ID);
		Assert.assertEquals(question.getViews(), Integer.valueOf(0));
		Assert.assertTrue(question.isPublished());
		Assert.assertTrue(questionLogic.existsQuestion(question.getId()));
	}

	@Test
	public void testSaveQuestionSpecifyUser() {
		QnaQuestion question = new QnaQuestion();
		question.setQuestionText("blah blah blah");
		try {
			questionLogic.saveQuestion(question, LOCATION1_ID, USER_UPDATE);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("Should not have thrown exception");
		}
		
		Assert.assertEquals(USER_UPDATE, question.getOwnerId());
		Assert.assertTrue(questionLogic.existsQuestion(question.getId()));
	}
	
	/**
	 * Test publishing of question
	 */
	@Test
	public void testPublishQuestion() {
		
		// try publish with invalid user (no update rights)
		QnaQuestion question = questionLogic.getQuestionById(tdp.question1_location3.getId());
		Assert.assertFalse(question.isPublished());
		externalLogicStub.currentUserId = USER_LOC_3_NO_UPDATE_1;
		try {
			questionLogic.publishQuestion(question.getId(),LOCATION3_ID);
			Assert.fail("Should have thrown exception");
		} catch(SecurityException se){
			Assert.assertNotNull(se);
		}
		question = questionLogic.getQuestionById(tdp.question1_location3.getId());
		Assert.assertFalse(question.isPublished());
		Assert.assertNotNull(question.getCategory());
		
		// try publish with valid user (update rights)
		externalLogicStub.currentUserId = USER_LOC_3_UPDATE_1;
		try {
			questionLogic.publishQuestion(question.getId(),LOCATION3_ID);
			question = questionLogic.getQuestionById(tdp.question1_location3.getId());
			Assert.assertTrue(question.isPublished());
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("Should not have thrown Exception");
		}
	}
	
	/**
	 * Test publishing a question with no category
	 */
	@Test
	public void testPublishQuestionWithNoCategory() {
		externalLogicStub.currentUserId = USER_LOC_3_UPDATE_1;
		
		QnaQuestion question = questionLogic.getQuestionById(tdp.question1_location3.getId());
		question.setCategory(null);
		questionLogic.saveQuestion(question, LOCATION3_ID);
		Assert.assertFalse(question.isPublished());
		Assert.assertNull(question.getCategory());

		try {
			questionLogic.publishQuestion(question.getId(), LOCATION3_ID);
			Assert.fail("Should have thrown QnaConfigurationException");
		} catch (QnaConfigurationException qne) {
			Assert.assertNotNull(qne);
		}
	}
	
	/**
	 * Test publishing existing anonymous question into site that doesn't allow anonymous questions
	 */
	@Test
	public void testPublishAnonymousInNonAnonymousLocation() {
		externalLogicStub.currentUserId = USER_UPDATE;
		Assert.assertFalse(optionsLogic.getOptionsForLocation(LOCATION1_ID).getAnonymousAllowed());
		
		QnaQuestion question = questionLogic.getQuestionById(tdp.question5_location1.getId());
		Assert.assertTrue(question.isAnonymous());
		Assert.assertFalse(question.isPublished());
		
		questionLogic.publishQuestion(question.getId(), LOCATION1_ID);
	}

	/**
	 * Test removal of question
	 */
	@Test
	public void testRemoveQuestion() {
		// Test with invalid permissions
		try {
			externalLogicStub.currentUserId = USER_NO_UPDATE;
			questionLogic.removeQuestion(tdp.question1_location1.getId(), LOCATION1_ID);
			Assert.fail("Should have thrown exception");
		} catch (SecurityException e) {
			Assert.assertNotNull(e);
		} catch (AttachmentException e) {
			Assert.fail("Should throw SecurityException");
		}

		// Test with valid permission
		try {
			externalLogicStub.currentUserId = USER_UPDATE;
			questionLogic.removeQuestion(tdp.question1_location1.getId(), LOCATION1_ID);
			Assert.assertFalse(questionLogic.existsQuestion(tdp.question1_location1.getId()));
		} catch (SecurityException e) {
			Assert.fail("Should not have thrown exception");
		} catch (AttachmentException e) {
			Assert.fail("Should not have thrown exception");
		}
		
		Assert.assertNull(questionLogic.getQuestionById(tdp.question1_location1.getId()));
	}

	/**
	 * Test view increment of question
	 */
	@Test
	public void testViewsIncrement() {
		externalLogicStub.currentUserId = USER_NO_UPDATE;
		QnaQuestion question = questionLogic.getQuestionById(tdp.question4_location1.getId());
		Assert.assertEquals(question.getViews(), Integer.valueOf(76));
		questionLogic.incrementView(tdp.question4_location1.getId());
		Assert.assertEquals(question.getViews(), Integer.valueOf(77));
		
		externalLogicStub.currentUserId = USER_UPDATE;
		questionLogic.incrementView(tdp.question4_location1.getId());
		Assert.assertEquals(question.getViews(), Integer.valueOf(77));
	}

	/**
	 * Test anonymous question
	 */
	@Test
	public void testAnonymous() {
		Assert.assertFalse(optionsLogic.getOptionsForLocation(LOCATION1_ID).getAnonymousAllowed());
		Assert.assertTrue(optionsLogic.getOptionsForLocation(LOCATION3_ID).getAnonymousAllowed());

		QnaQuestion question = new QnaQuestion();
		question.setQuestionText("xxxx");
		question.setAnonymous(true);
		question.setNotify(false);
		
		externalLogicStub.currentUserId = USER_UPDATE;
		// Should get exception when trying to save a question anonymously when anonymous option isn't true
		try	{
			questionLogic.saveQuestion(question, LOCATION1_ID);
			Assert.fail("This should have thrown an exception");
		} catch (QnaConfigurationException e) {
			Assert.assertNotNull(e);
			Assert.assertNull(question.getId());
		}
		
		externalLogicStub.currentUserId = USER_LOC_3_UPDATE_1;
		// Should not get exception when saving where anonymous is allowed
		try	{
			questionLogic.saveQuestion(question, LOCATION3_ID);
			Assert.assertNotNull(question.getId());
		} catch (Exception e) {
			Assert.fail("Should not have thrown exception");
		}
	}
	
	/**
	 * Test editing existing anonymous question in location that doesn't allow anonymous
	 */
	@Test
	public void testExistingAnonymousQuestion() {
		externalLogicStub.currentUserId = USER_UPDATE;
		Assert.assertFalse(optionsLogic.getOptionsForLocation(LOCATION1_ID).getAnonymousAllowed());
				
		QnaQuestion question = questionLogic.getQuestionById(tdp.question5_location1.getId());
		Assert.assertTrue(question.isAnonymous());
		
		question.setQuestionText("something new");
		
		// Saving a existing anonymous question in non-anonymous location should not throw exception
		try	{
			questionLogic.saveQuestion(question, LOCATION1_ID);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("Should not have thrown exception");
		}
	}
	
	/**
	 * Test add question to category
	 */
	@Test
	public void testAddQuestionToCategory() {
		// Invalid user id
		externalLogicStub.currentUserId = USER_NO_UPDATE;
		try {
			questionLogic.addQuestionToCategory( tdp.question1_location1.getId() , tdp.category1_location1.getId(), LOCATION1_ID);
			Assert.fail("Should throw SecurityException");
		} catch (SecurityException se) {
			Assert.assertNotNull(se);
		} 
		
		// Valid user id
		externalLogicStub.currentUserId = USER_UPDATE;
		try {
			questionLogic.addQuestionToCategory(tdp.question1_location1.getId() , tdp.category1_location1.getId(), LOCATION1_ID);
		} catch (Exception se) {
			Assert.fail("Should not throw Exception");
		}
		Assert.assertTrue(tdp.question1_location1.getCategory().getId().equals(tdp.category1_location1.getId()));
	}
	
	/**
	 * Test get questions with private replies
	 */
	@Test
	public void testGetQuestionsWithPrivateReplies() {
		List<QnaQuestion> questions = questionLogic.getQuestionsWithPrivateReplies(LOCATION1_ID);
		Assert.assertEquals(1, questions.size());
		Assert.assertTrue(questions.contains(tdp.question2_location1));
	}
	
	/**
	 * Test get all questions
	 */
	@Test
	public void testGetAllQuestions() {
		List<QnaQuestion> questions = questionLogic.getAllQuestions(LOCATION1_ID);
		Assert.assertEquals(5, questions.size());
		Assert.assertTrue(questions.contains(tdp.question1_location1));
		Assert.assertTrue(questions.contains(tdp.question2_location1));
		Assert.assertTrue(questions.contains(tdp.question3_location1));
		Assert.assertTrue(questions.contains(tdp.question4_location1));
		Assert.assertTrue(questions.contains(tdp.question5_location1));
	}
	
}
