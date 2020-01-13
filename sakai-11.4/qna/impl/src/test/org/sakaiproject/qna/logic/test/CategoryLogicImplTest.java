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
import static org.sakaiproject.qna.logic.test.TestDataPreload.LOCATION4_ID;
import static org.sakaiproject.qna.logic.test.TestDataPreload.USER_NO_UPDATE;
import static org.sakaiproject.qna.logic.test.TestDataPreload.USER_UPDATE;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.sakaiproject.qna.dao.QnaDao;
import org.sakaiproject.qna.logic.QnaBundleLogic;
import org.sakaiproject.qna.logic.impl.CategoryLogicImpl;
import org.sakaiproject.qna.logic.impl.OptionsLogicImpl;
import org.sakaiproject.qna.logic.impl.PermissionLogicImpl;
import org.sakaiproject.qna.logic.impl.QuestionLogicImpl;
import org.sakaiproject.qna.logic.test.stubs.ExternalEventLogicStub;
import org.sakaiproject.qna.logic.test.stubs.ExternalLogicStub;
import org.sakaiproject.qna.logic.test.stubs.QnaBundleLogicStub;
import org.sakaiproject.qna.model.QnaCategory;
import org.sakaiproject.qna.model.QnaQuestion;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
@DirtiesContext
@ContextConfiguration(locations={
		"/hibernate-test.xml",
		"/spring-hibernate.xml"})
public class CategoryLogicImplTest extends AbstractTransactionalJUnit4SpringContextTests {

	OptionsLogicImpl optionsLogic;
	CategoryLogicImpl categoryLogic;
	QuestionLogicImpl questionLogic;
	PermissionLogicImpl permissionLogic;

	private static Log log = LogFactory.getLog(CategoryLogicImplTest.class);
	
	private ExternalLogicStub externalLogicStub = new ExternalLogicStub();
	private ExternalEventLogicStub externalEventLogicStub = new ExternalEventLogicStub();
	private QnaBundleLogic bundleLogicStub = new QnaBundleLogicStub();
	
	private TestDataPreload tdp = new TestDataPreload();

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

		// create and setup options
		optionsLogic = new OptionsLogicImpl();
		optionsLogic.setDao(dao);
		optionsLogic.setPermissionLogic(permissionLogic);
		optionsLogic.setExternalLogic(externalLogicStub);
		optionsLogic.setExternalEventLogic(externalEventLogicStub);
		
		// create and setup the question logic
		questionLogic = new QuestionLogicImpl();
		questionLogic.setDao(dao);
		questionLogic.setPermissionLogic(permissionLogic);
		questionLogic.setOptionsLogic(optionsLogic);
		questionLogic.setExternalLogic(externalLogicStub);
		questionLogic.setExternalEventLogic(externalEventLogicStub);
		
		// create and setup the category logic
		categoryLogic = new CategoryLogicImpl();
		categoryLogic.setDao(dao);
		categoryLogic.setPermissionLogic(permissionLogic);
		categoryLogic.setExternalLogic(externalLogicStub);
		categoryLogic.setExternalEventLogic(externalEventLogicStub);
		categoryLogic.setQnaBundleLogic(bundleLogicStub);
		
		// preload testData
		tdp.preloadTestData(dao);
	}
	
	/**
	 * Test retrieval of category by id
	 */
	@Test
	public void testGetCategoryById() {
		QnaCategory category = categoryLogic.getCategoryById(tdp.category1_location1.getId());
		Assert.assertNotNull(category);
		Assert.assertEquals(category.getCategoryText(),tdp.category1_location1.getCategoryText());
		Assert.assertEquals(category.getId(), tdp.category1_location1.getId());
	}

	/**
	 * Test create of new category
	 */
	@Test
	public void testCreateCategory() {
		
		QnaCategory category = new QnaCategory();
		Assert.assertNotNull(category);
		category.setCategoryText("New Category test");

		// With invalid permissions
		externalLogicStub.currentUserId  = USER_NO_UPDATE;
		try {
			categoryLogic.saveCategory(category, LOCATION1_ID );
			Assert.fail("Should have thrown exception");
		} catch (SecurityException se) {
			Assert.assertNotNull(se);
		}

		// With valid permissions
		externalLogicStub.currentUserId  = USER_UPDATE;
		try {
			categoryLogic.saveCategory(category, LOCATION1_ID );
			Assert.assertNotNull(category.getId());
			
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("Should not have thrown exception");
		}
	}

	/**
	 *	Test editing of existing category
	 */
	@Test
	public void testEditCategory() {
		QnaCategory category = categoryLogic.getCategoryById(tdp.category1_location1.getId());
		Assert.assertNotNull(category);

		category.setCategoryText("New text to be saved");
		
		// Invalid permission
		externalLogicStub.currentUserId = USER_NO_UPDATE;
		try {
			categoryLogic.saveCategory(category, LOCATION1_ID);
			Assert.fail("Should have thrown exception");
		} catch (SecurityException se) {
			Assert.assertNotNull(se);
		}
		
		// Valid permission
		externalLogicStub.currentUserId = USER_UPDATE;
		try {
			categoryLogic.saveCategory(category, LOCATION1_ID);
		} catch (Exception e) {
			Assert.fail("Should not have thrown exception");
		}
	}

	/**
	 * Test removal of category
	 */
	@Test
	public void testRemoveCategory() {
		// With invalid permissions
		externalLogicStub.currentUserId = USER_NO_UPDATE;
		try {
			categoryLogic.removeCategory(tdp.category1_location1.getId(), LOCATION1_ID);
			Assert.fail("Should have thrown exception");
		} catch (SecurityException se) {
			Assert.assertNotNull(se);
		}
		
		// With valid permissions
		externalLogicStub.currentUserId = USER_UPDATE;
		try {
			categoryLogic.removeCategory(tdp.category1_location1.getId(), LOCATION1_ID);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("Should not have thrown exception");
		}
		Assert.assertNull(categoryLogic.getCategoryById(tdp.category1_location1.getId()));
		
		Assert.assertEquals(0,questionLogic.getPublishedQuestions(LOCATION1_ID).size());
		Assert.assertEquals(1,questionLogic.getNewQuestions(LOCATION1_ID).size());
	}

	/**
	 * Test get questions for a category
	 */
	@Test
	public void testGetQuestionsForCategory() {
		List<QnaQuestion> questions = categoryLogic.getQuestionsForCategory(tdp.category1_location1.getId());
		Assert.assertEquals(4,  questions.size());	
		Assert.assertEquals(tdp.question2_location1.getQuestionText(), questions.get(0).getQuestionText());
		Assert.assertEquals(tdp.question3_location1.getQuestionText(), questions.get(1).getQuestionText());
		Assert.assertEquals(tdp.question4_location1.getQuestionText(), questions.get(2).getQuestionText());
		Assert.assertEquals(tdp.question5_location1.getQuestionText(), questions.get(3).getQuestionText());
	}
	
	/**
	 * Test get published question for category
	 */
	@Test
	public void testGetPublishedQuestionsForCategory() {
		QnaCategory category = categoryLogic.getCategoryById(tdp.category1_location1.getId());
		List<QnaQuestion> questions = category.getPublishedQuestions();
		Assert.assertEquals(3, questions.size());
		Assert.assertTrue(questions.contains(tdp.question2_location1));
		Assert.assertTrue(questions.contains(tdp.question3_location1));
		Assert.assertTrue(questions.contains(tdp.question4_location1));
	}
	
	/**
	 * Test get categories for location
	 */
	@Test
	public void testGetCategoriesForLocation() {
		List<QnaCategory> categories = categoryLogic.getCategoriesForLocation(LOCATION1_ID);
		Assert.assertEquals(3, categories.size());
	}
	
	/**
	 * Test saving of defaults
	 */
	@Test
	public void testSaveDefaults() {
		try {
			categoryLogic.setNewCategoryDefaults(tdp.category1_location1, LOCATION1_ID, USER_UPDATE);
			Assert.fail("Should throw exception");
		} catch (Exception expected) {
			Assert.assertNotNull(expected);
		}
		
		QnaCategory category = new QnaCategory();
		categoryLogic.setNewCategoryDefaults(category, LOCATION1_ID, USER_UPDATE);
		Assert.assertEquals(category.getLocation(),LOCATION1_ID);
		Assert.assertEquals(category.getOwnerId(),USER_UPDATE);
	}
	
	/**
	 * Test create general category
	 */
	@Test
	public void testCreateGeneralCategory() {
		List<QnaCategory> categories = categoryLogic.getCategoriesForLocation(LOCATION4_ID);
		Assert.assertEquals(1, categories.size());
		Assert.assertEquals(bundleLogicStub.getString("qna.default-category.text"), categories.get(0).getCategoryText());
		
		// Confire it doesn't get created more than once
		categories = categoryLogic.getCategoriesForLocation(LOCATION4_ID);
		Assert.assertEquals(1, categories.size());
	}

	@Test
	public void testGetDefaultCategory() {
		Assert.assertEquals(3, categoryLogic.getCategoriesForLocation(LOCATION1_ID).size());
		Assert.assertEquals(categoryLogic.getCategoriesForLocation(LOCATION1_ID).get(0).getId(), categoryLogic.getDefaultCategory(LOCATION1_ID).getId());
	}

}
