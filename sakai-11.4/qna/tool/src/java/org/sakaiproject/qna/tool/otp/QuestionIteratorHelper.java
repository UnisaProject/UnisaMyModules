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
package org.sakaiproject.qna.tool.otp;


import java.util.List;

import org.sakaiproject.qna.logic.ExternalLogic;
import org.sakaiproject.qna.logic.PermissionLogic;
import org.sakaiproject.qna.model.QnaQuestion;
import org.sakaiproject.qna.tool.constants.SortByConstants;
import org.sakaiproject.qna.tool.constants.ViewTypeConstants;
import org.sakaiproject.qna.tool.utils.QuestionsSorter;
import org.sakaiproject.qna.tool.utils.ViewHelper;

public class QuestionIteratorHelper {
	
	private ExternalLogic externalLogic;
	private PermissionLogic permissionLogic;
	private QuestionsSorter questionsSorter; 
	private QnaQuestion current;
	private List<QnaQuestion> questionList;
    private ViewHelper viewHelper;
	
    /**
     * Set question currently viewed
     * @param current {@link QnaQuestion} currently being viewed
     */
	public void setCurrentQuestion(QnaQuestion current) {
		if (current == null || current.getId() == null) {
			throw new IllegalArgumentException("Question provided must be valid");
		}
		
		this.current = current;
		questionList = getCurrentList();
	}
	
	/**
	 * Get previous question
	 * @return {@link QnaQuestion}
	 */
	public QnaQuestion getPrevious() {
		checkSetup();
		if (!isFirst()) {
			return questionList.get(questionList.indexOf(current) - 1);
		} else {
			return null;
		}
	}
	
	/**
	 * Get next question
	 * @return {@link QnaQuestion}
	 */
	public QnaQuestion getNext() {
		checkSetup();
		if (!isLast()) {
			return questionList.get(questionList.indexOf(current) + 1);
		} else {
			return null;
		}
	}
	
	/**
	 * Check if question is first in list
	 * @return true if question is first, false otherwise
	 */
	public boolean isFirst() {
		checkSetup();
		if (questionList.indexOf(current) == 0) {
			return true;
		}
		return false;
	}

	/**
	 * Check if question is last in list
	 * @return true if question is last, false otherwise
	 */
	public boolean isLast() {
		checkSetup();
		if (questionList.indexOf(current) == (questionList.size()-1)) {
			return true;
		}
		return false;
	}
	
	/**
	 * Retrieve message key for list type
	 * @return key
	 */
	public String getListTypeMessageKey() {
		checkSetup();
		
		String viewType = viewHelper.getViewType();
		String sortBy = viewHelper.getSortBy();
		
		if (ViewTypeConstants.CATEGORIES.equals(viewType)) {
			return "qna.question-iterator.category";
		} else {
			if (SortByConstants.VIEWS.equals(sortBy)) {
				return "qna.question-iterator.most-popular";
			} else if (SortByConstants.ANSWERS.equals(sortBy)) {
				return "qna.question-iterator.answers";
			} else if (SortByConstants.CATEGORY.equals(sortBy)) {
				return "qna.question-iterator.alphabetic-category";
			} else if (SortByConstants.CREATED.equals(sortBy)) {
				return "qna.question-iterator.recent-questions";
			} else if (SortByConstants.MODIFIED.equals(sortBy)) {
				return "qna.question-iterator.recent-changes";
			} else if (SortByConstants.QUESTIONS.equals(sortBy)) {
				return "qna.question-iterator.alphabetic-questions";
			}
		}
		return "qna.general.blank";
	}
	
	/**
	 * Retrieve current list
	 * @return {@link List} of sorted {@link QnaQuestion}
	 */
	private List<QnaQuestion> getCurrentList() {
		checkSetup();
		
		String viewType = viewHelper.getViewType();
		String sortBy = viewHelper.getSortBy();
		String sortDir = viewHelper.getSortDir();
		
		String location = externalLogic.getCurrentLocationId();
		List<QnaQuestion> questions = questionsSorter.getSortedQuestionList(location, viewType, sortBy, permissionLogic.canUpdate(location, externalLogic.getCurrentUserId()),SortByConstants.SORT_DIR_DESC.equals(sortDir));
		
		return questions;
	}
	
	public void setExternalLogic(ExternalLogic externalLogic) {
		this.externalLogic = externalLogic;
	}
	
	public void setPermissionLogic(PermissionLogic permissionLogic) {
		this.permissionLogic = permissionLogic;
	}
	
	public void setQuestionsSorter(QuestionsSorter questionsSorter) {
		this.questionsSorter = questionsSorter;
	}
	
    public void setViewHelper(ViewHelper viewHelper) {
    	this.viewHelper = viewHelper;
    }
	
    /**
     * Current question must be set
     */
	public void checkSetup() {
		if (current == null) {
			throw new IllegalStateException("Current selected question must be set");
		}
	}
	
}
