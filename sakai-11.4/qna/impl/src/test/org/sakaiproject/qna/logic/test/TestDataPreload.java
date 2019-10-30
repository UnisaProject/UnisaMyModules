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

import java.util.Date;

import org.sakaiproject.genericdao.api.GenericDao;
import org.sakaiproject.qna.model.QnaAnswer;
import org.sakaiproject.qna.model.QnaCategory;
import org.sakaiproject.qna.model.QnaCustomEmail;
import org.sakaiproject.qna.model.QnaOptions;
import org.sakaiproject.qna.model.QnaQuestion;
import org.sakaiproject.qna.model.constants.QnaConstants;

public class TestDataPreload {

	/**
	 * current user, access level user in LOCATION_ID1
	 */
	public final static String USER_UPDATE = "user-12345678";
	
	public final static String USER_LOC_3_UPDATE_1 = "user-11112222";
	public final static String USER_LOC_3_UPDATE_1_EMAIL = "user-11112222@qnatest.com";
	
	public final static String USER_LOC_3_UPDATE_2 = "user-22221111";
	public final static String USER_LOC_3_UPDATE_2_EMAIL= "user-22221111@qnatest.com";
	
	public final static String USER_LOC_3_UPDATE_3 = "user-11144422";
	public final static String USER_LOC_3_UPDATE_3_EMAIL = "user-11144422@qnatest.com";	
	
	public final static String USER_LOC_3_NO_UPDATE_1 = "user-77755577";
	public final static String USER_LOC_3_NO_UPDATE_1_EMAIL = "user-77755577@qnatest.com";
	
	public final static String USER_LOC_3_NO_UPDATE_2 = "user-65000011";
	public final static String USER_LOC_3_NO_UPDATE_2_EMAIL = "user-65000011@qnatest.com";
	
	public final static String USER_NO_UPDATE = "user-87654321";
	
	public final static String USER_CUSTOM_EMAIL1 = "user1@qna.com";
	public final static String USER_CUSTOM_EMAIL2 = "user2@qna.com";
	public final static String USER_CUSTOM_EMAIL3 = "user3@qna.com";
	public final static String USER_CUSTOM_EMAIL4 = "user3qna.com";
	
	public final static String ANSWER_TEXT_1 = "This is an answer";
	public final static String ANSWER_TEXT_2 = "This is an another answer";
	public final static String ANSWER_TEXT_3 = "&lt;ANSWER&gt;&nbsp;1<br/>";

	public final static String USER_CUSTOM_EMAIL_INVALID = USER_CUSTOM_EMAIL4
			+ "," + USER_CUSTOM_EMAIL1 + " " + USER_CUSTOM_EMAIL2 + ","
			+ USER_CUSTOM_EMAIL3;
	public final static String USER_CUSTOM_EMAIL_VALID = USER_CUSTOM_EMAIL1
			+ "," + USER_CUSTOM_EMAIL2 + " , " + USER_CUSTOM_EMAIL3;

	/**
	 * current location
	 */
	public final static String LOCATION1_ID = "/site/ref-1111111";
	public final static String LOCATION1_TITLE = "Location 1 title";
	public final static String LOCATION2_ID = "/site/ref-22222222";
	public final static String LOCATION2_TITLE = "Location 2 title";
	public final static String LOCATION3_ID = "/site/ref-33333333";
	public final static String LOCATION3_TITLE = "Location 3 title";
	public final static String LOCATION4_ID = "/site/ref-44444444";
	public final static String LOCATION4_TITLE = "Location 4 title";

	public final static String LOCATION1_CONTACT_NAME = "Site Contact Name";
	public final static String LOCATION1_CONTACT_EMAIL = "sitecontact@site.com";

	public QnaOptions options_location1 = new QnaOptions(USER_UPDATE,
			LOCATION1_ID, new Date(), new Date(), false, false, true,
			QnaConstants.SITE_CONTACT, QnaConstants.CATEGORY_VIEW, true);
	
	public QnaOptions options_location3 = new QnaOptions(USER_LOC_3_UPDATE_1,
			LOCATION3_ID, new Date(), new Date(), true, true, true,
			QnaConstants.UPDATE_RIGHTS, QnaConstants.MOST_POPULAR_VIEW, true);
	
	public QnaOptions options_location4 = new QnaOptions(USER_UPDATE,
			LOCATION4_ID, new Date(), new Date(), false, false, false,
			null, QnaConstants.MOST_POPULAR_VIEW, true);
	

	public QnaCustomEmail customEmail1_location1 = new QnaCustomEmail(
			TestDataPreload.USER_UPDATE, TestDataPreload.USER_CUSTOM_EMAIL1,
			new Date());
	public QnaCustomEmail customEmail2_location1 = new QnaCustomEmail(
			TestDataPreload.USER_UPDATE, TestDataPreload.USER_CUSTOM_EMAIL2,
			new Date());
	public QnaCustomEmail customEmail3_location1 = new QnaCustomEmail(
			TestDataPreload.USER_UPDATE, TestDataPreload.USER_CUSTOM_EMAIL3,
			new Date());
	
	
	public QnaQuestion question1_location1 = new QnaQuestion(null,TestDataPreload.USER_UPDATE, TestDataPreload.LOCATION1_ID,"Test Question1", 0,new Date(),new Date(), 0,false,false, false, null  );
	public QnaCategory category1_location1 = new QnaCategory(TestDataPreload.USER_UPDATE, TestDataPreload.LOCATION1_ID,"Test Category1",0, false);
	public QnaCategory category2_location1 = new QnaCategory(TestDataPreload.USER_UPDATE, TestDataPreload.LOCATION1_ID,"Test Category2",0, false);
	public QnaCategory category3_location1 = new QnaCategory(TestDataPreload.USER_UPDATE, TestDataPreload.LOCATION1_ID,"Test Category3",0, false);
	
	public QnaQuestion question2_location1 = new QnaQuestion(category1_location1,TestDataPreload.USER_UPDATE, TestDataPreload.LOCATION1_ID,"Test Question2", 0,new Date(),new Date(), 0,false,true, false, null);
	public QnaQuestion question3_location1 = new QnaQuestion(category1_location1,TestDataPreload.USER_UPDATE, TestDataPreload.LOCATION1_ID,"Test Question3", 0,new Date(),new Date(), 0,false,true, false, null);
	public QnaQuestion question4_location1 = new QnaQuestion(category1_location1,TestDataPreload.USER_UPDATE, TestDataPreload.LOCATION1_ID,"Test Question4", 76,new Date(),new Date(), 0,false,true, false, null);
	public QnaQuestion question5_location1 = new QnaQuestion(category1_location1,TestDataPreload.USER_UPDATE, TestDataPreload.LOCATION1_ID,"Test Question5", 0,new Date(),new Date(), 0,true,false, false, null);
	
	public QnaCategory category1_location3 = new QnaCategory(TestDataPreload.USER_LOC_3_UPDATE_1, TestDataPreload.LOCATION3_ID,"Test Category location 3",0, false);
	public QnaQuestion question1_location3 = new QnaQuestion(category1_location3,TestDataPreload.USER_LOC_3_UPDATE_1, TestDataPreload.LOCATION3_ID,"Test Question1 location 3", 0,new Date(),new Date(), 0,false,false, false, null);
	
	public QnaAnswer answer1_location1 = new QnaAnswer(question2_location1, USER_UPDATE, ANSWER_TEXT_1, true, false, false);
	public QnaAnswer answer2_location1 = new QnaAnswer(question2_location1, USER_UPDATE, ANSWER_TEXT_2, false, true, false);
	
	public QnaAnswer answer1_location3 = new QnaAnswer(question1_location3, USER_LOC_3_UPDATE_1, ANSWER_TEXT_3, false, false, false);
	
	/**
	 * Preload a bunch of test data into the database
	 * 
	 * @param dao
	 *            a generic dao
	 */
	public void preloadTestData(GenericDao dao) {

		dao.save(options_location1);
	
		options_location1.addCustomEmail(customEmail1_location1);
		options_location1.addCustomEmail(customEmail2_location1);
		options_location1.addCustomEmail(customEmail3_location1);
		
		dao.save(options_location1);
		
		dao.save(options_location3);
		
		dao.save(question1_location1);
		
		dao.save(category1_location1);
		dao.save(category2_location1);
		dao.save(category3_location1);
		
		question2_location1.addAnswer(answer1_location1);
		question2_location1.addAnswer(answer2_location1);
		category1_location1.addQuestion(question2_location1);
		dao.save(question2_location1);
		dao.save(answer1_location1);
		dao.save(answer2_location1);
		
		category1_location1.addQuestion(question3_location1);
		dao.save(question3_location1);
		category1_location1.addQuestion(question4_location1);
		dao.save(question4_location1);
		category1_location1.addQuestion(question5_location1);		
		dao.save(question5_location1);
		dao.save(category1_location1);
		
		dao.save(question3_location1);
		dao.save(question4_location1);
		dao.save(question5_location1);
		
		dao.save(category1_location3);
		
		question1_location3.addAnswer(answer1_location3);
		dao.save(question1_location3);
		dao.save(answer1_location3);
	}

}
