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
package org.sakaiproject.qna.tool.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.sakaiproject.qna.comparators.CategoriesSortOrderComparator;
import org.sakaiproject.qna.comparators.QuestionsSortOrderComparator;
import org.sakaiproject.qna.logic.CategoryLogic;
import org.sakaiproject.qna.logic.QuestionLogic;
import org.sakaiproject.qna.model.QnaCategory;
import org.sakaiproject.qna.model.QnaQuestion;
import org.sakaiproject.qna.tool.constants.SortByConstants;
import org.sakaiproject.qna.tool.constants.ViewTypeConstants;

public class QuestionsSorter {

	private QuestionLogic questionLogic;
	private CategoryLogic categoryLogic;
	
	public void setQuestionLogic(QuestionLogic questionLogic) {
		this.questionLogic = questionLogic;
	}
	
	public void setCategoryLogic(CategoryLogic categoryLogic) {
		this.categoryLogic = categoryLogic;
	}

	/**
	 * Get list of questions and sorts it based on parameters
	 * 
	 * @param location Location of questions
	 * @param viewType {@link ViewTypeConstants}
	 * @param sortBy {@link {@link SortByConstants}	
	 * @param includeAll Must private replies and queued questions be included as well
	 * @param sortDir Should the list be reversed
	 * @return List of sorted QnaQuestions
	 */
	public List<QnaQuestion> getSortedQuestionList(String location, String viewType, String sortBy, boolean includeAll, boolean reverse) {
		
		
		List<QnaQuestion> questions;
		
		if (viewType.equals(ViewTypeConstants.CATEGORIES)) {
			questions = new ArrayList<QnaQuestion>();
			
			List<QnaCategory> categories = categoryLogic.getCategoriesForLocation(location);
			Collections.sort(categories, new CategoriesSortOrderComparator());
			
			for (QnaCategory qnaCategory : categories) {
				if (qnaCategory.getPublishedQuestions().size() > 0) {
					List<QnaQuestion> publishedQuestion = qnaCategory.getPublishedQuestions();
					Collections.sort(publishedQuestion, new QuestionsSortOrderComparator());
					questions.addAll(publishedQuestion);
				}
			}
			
			filterHiddenQuestions(questions, false);
			if (includeAll) {
				questions.addAll(questionLogic.getNewQuestions(location));
				questions.addAll(questionLogic.getQuestionsWithPrivateReplies(location));
			}
		} else {
			if (includeAll) {
				questions = questionLogic.getAllQuestions(location);
			} else {
				questions = questionLogic.getPublishedQuestions(location);
			}
			Comparator<QnaQuestion> comparator = ComparatorUtil.getComparator(viewType, sortBy);
			Collections.sort(questions, comparator);
			filterHiddenQuestions(questions, true);
		}
		
		if (reverse) {
			Collections.reverse(questions);
		}

		return questions;
	}
	
	/**
	 * Filters out hidden questions or questions with hidden category from list of questions
	 * @param questions List of questions to filter out
	 * @param includeNewAndPrivate boolean if new and questions with private replies must not be filtered
	 */
	public void filterHiddenQuestions(List<QnaQuestion> questions, boolean includeNewAndPrivate) {
		List<QnaQuestion> questionsToRemove = new ArrayList<QnaQuestion>();
		
		for (int i=0;i<questions.size();i++) {
			QnaQuestion question = questions.get(i);
						
			if (question.getHidden()) {
					if (includeNewAndPrivate) {
						if (question.isPublished() && !question.hasPrivateReplies()) {
							questionsToRemove.add(question);
						}
					} else {
						questionsToRemove.add(question);
					}
			} else if (question.getCategory() != null) {
				if (question.getCategory().getHidden()) {
					if (includeNewAndPrivate) {
						if (question.isPublished() && !question.hasPrivateReplies()) {
							questionsToRemove.add(question);
						}
					} else {
						questionsToRemove.add(question);
					}					
				}
			}
		}
		
		for (QnaQuestion questionToRemove : questionsToRemove) {
			questions.remove(questionToRemove);
		}
	}
	
	/**
	 * Filter questions for sorter
	 * 
	 * @param questions {@link List} of {@link QnaQuestion}
	 * @param fromIndex begin index
	 * @param amount	amount to retrieve 
	 * @return	sub list of questions based on parameters
	 */
	public List<QnaQuestion> filterQuestions(List<QnaQuestion> questions,int fromIndex, int amount) {
		if ((amount + fromIndex) > questions.size()) {
			return questions.subList(fromIndex, questions.size());
		} else {
			return questions.subList(fromIndex, fromIndex + amount);
		}
	}
}
