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
package org.sakaiproject.qna.tool.producers.renderers;

import java.util.Collections;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.sakaiproject.qna.comparators.CategoriesSortOrderComparator;
import org.sakaiproject.qna.comparators.QuestionsSortOrderComparator;
import org.sakaiproject.qna.logic.CategoryLogic;
import org.sakaiproject.qna.logic.ExternalLogic;
import org.sakaiproject.qna.logic.PermissionLogic;
import org.sakaiproject.qna.logic.QuestionLogic;
import org.sakaiproject.qna.model.QnaCategory;
import org.sakaiproject.qna.model.QnaQuestion;
import org.sakaiproject.qna.tool.params.CategoryParams;
import org.sakaiproject.qna.tool.params.QuestionParams;
import org.sakaiproject.qna.tool.params.SortPagerViewParams;
import org.sakaiproject.qna.tool.producers.CategoryProducer;
import org.sakaiproject.qna.tool.producers.QueuedQuestionProducer;
import org.sakaiproject.qna.tool.producers.ViewPrivateReplyProducer;
import org.sakaiproject.qna.tool.producers.ViewQuestionProducer;
import org.sakaiproject.qna.tool.utils.DateUtil;
import org.sakaiproject.qna.utils.TextUtil;

import uk.org.ponder.rsf.components.UIBranchContainer;
import uk.org.ponder.rsf.components.UIContainer;
import uk.org.ponder.rsf.components.UIForm;
import uk.org.ponder.rsf.components.UIInitBlock;
import uk.org.ponder.rsf.components.UIInternalLink;
import uk.org.ponder.rsf.components.UIJointContainer;
import uk.org.ponder.rsf.components.UILink;
import uk.org.ponder.rsf.components.UIMessage;
import uk.org.ponder.rsf.components.UIOutput;
import uk.org.ponder.rsf.components.UISelect;
import uk.org.ponder.rsf.components.UISelectChoice;
import uk.org.ponder.rsf.components.UIVerbatim;
import uk.org.ponder.rsf.components.decorators.UIAlternativeTextDecorator;
import uk.org.ponder.stringutil.StringList;

public class CategoryQuestionListRenderer implements QuestionListRenderer {
	private static final String EXPAND_ICON_URL = "/library/image/sakai/expand.gif";
	private static final String COLLAPSE_ICON_URL = "/library/image/sakai/collapse.gif";
	
	 private static Log log = LogFactory.getLog(CategoryQuestionListRenderer.class);
	
	private ExternalLogic externalLogic;
	private PermissionLogic permissionLogic;
	private CategoryLogic categoryLogic;
	private QuestionLogic questionLogic;
	
    public void setExternalLogic(ExternalLogic externalLogic) {
		this.externalLogic = externalLogic;
	}

	public void setPermissionLogic(PermissionLogic permissionLogic) {
		this.permissionLogic = permissionLogic;
	}

	public void setCategoryLogic(CategoryLogic categoryLogic) {
		this.categoryLogic = categoryLogic;
	}

	public void setQuestionLogic(QuestionLogic questionLogic) {
		this.questionLogic = questionLogic;
	}

	/**
	 * @see QuestionListRenderer#makeQuestionList(UIContainer, String, SortPagerViewParams, UIForm)	
	 */
	public void makeQuestionList(UIContainer tofill, String divID, SortPagerViewParams sortParams, UIForm form) {
		UIJointContainer listTable = new UIJointContainer(tofill,divID,"question-list-table:");
		UIMessage.make(listTable, "categories-title", "qna.view-questions.categories");
		UIMessage.make(listTable, "answers-title", "qna.view-questions.answers");
		UIMessage.make(listTable,"views-title","qna.view-questions.views");

		// Creates remove heading for users with update rights
		if (permissionLogic.canUpdate(externalLogic.getCurrentLocationId(), externalLogic.getCurrentUserId())) {
			UIMessage.make(listTable,"modified-title","qna.view-questions.modified");
			UIMessage.make(listTable, "remove-title", "qna.view-questions.remove");
		} else { // To remove irritating scrollbar rendered
			UIMessage.make(listTable,"modified-title-longer","qna.view-questions.modified");
		}

		List<QnaCategory> categories = categoryLogic.getCategoriesForLocation(externalLogic.getCurrentLocationId());
		Collections.sort(categories, new CategoriesSortOrderComparator());

		UISelect questionDeleteSelect = UISelect.makeMultiple(form, "remove-question-cell", null, "#{DeleteMultiplesHelper.questionids}", null);
		UISelect categoryDeleteSelect = UISelect.makeMultiple(form, "remove-category-cell", null, "#{DeleteMultiplesHelper.categoryids}", null);

		StringList deletable = new StringList();
		// List of published questions by category
		for (QnaCategory qnaCategory : categories) {
			if (qnaCategory.getPublishedQuestions().size() > 0 || permissionLogic.canUpdate(externalLogic.getCurrentLocationId(), externalLogic.getCurrentUserId())) {
				if (!qnaCategory.getHidden()) {
					UIBranchContainer entry = UIBranchContainer.make(listTable, "table-entry:");
					UIBranchContainer category = UIBranchContainer.make(entry,"category-entry:");
					
					if (qnaCategory.getPublishedQuestions().size() > 0) {
						initViewToggle(entry, category);
					}
						
					UIOutput.make(category,"category-name",qnaCategory.getCategoryText());

					// Only users with update permission can edit category
					if (permissionLogic.canUpdate(externalLogic.getCurrentLocationId(), externalLogic.getCurrentUserId())) {
						UIInternalLink.make(category, "category-edit", UIMessage.make("qna.general.edit"), new CategoryParams(CategoryProducer.VIEW_ID, "1", qnaCategory.getCategoryText(), qnaCategory.getId()));
					}

					UIOutput.make(category,"modified-date",DateUtil.getSimpleDate(qnaCategory.getDateLastModified()));

					if (permissionLogic.canUpdate(externalLogic.getCurrentLocationId(), externalLogic.getCurrentUserId())) {
						//UIOutput.make(category,"remove-category-cell");
						//UIBoundBoolean.make(category, "remove-checkbox", false);						
						UIBranchContainer catContainer = UIBranchContainer.make(category, "remove-category-checkbox-td:");
						UISelectChoice.make(catContainer, "remove-category-checkbox", categoryDeleteSelect.getFullID(), deletable.size());
						deletable.add(qnaCategory.getId());
					}
					
					log.debug("getting questions for category: " + qnaCategory.getId() + " title: " + qnaCategory.getCategoryText());
					List<QnaQuestion> publishedQuestions = qnaCategory.getPublishedQuestions();
					renderQuestions(entry, publishedQuestions, ViewQuestionProducer.VIEW_ID, questionDeleteSelect);
				}
			}

		}
		categoryDeleteSelect.optionlist.setValue(deletable.toStringArray());

		// Only users with update permissions can view new questions + private replies
		if (permissionLogic.canUpdate(externalLogic.getCurrentLocationId(), externalLogic.getCurrentUserId())) {

			// All new questions (questions not published)
			List<QnaQuestion> newQuestions = questionLogic.getNewQuestions(externalLogic.getCurrentLocationId());

			if (newQuestions.size() > 0) {
				UIBranchContainer entry = UIBranchContainer.make(listTable, "table-entry:");
				UIBranchContainer category = UIBranchContainer.make(entry,"category-entry:");

				if (showFlagIcon(newQuestions)) {
					UILink ul = UILink.make(category,"new-question-icon","/library/image/silk/flag_yellow.png");
					ul.decorate(new UIAlternativeTextDecorator(UIMessage.make("qna.view-questions.unread-questions")));
				}

				initViewToggle(entry, category);
				UIMessage.make(category,"category-name","qna.view-questions.new-questions");
				UIOutput.make(category,"modified-date","");
				UIOutput.make(category,"remove-category-cell","");
				renderQuestions(entry, newQuestions, QueuedQuestionProducer.VIEW_ID, questionDeleteSelect);
			}

			// All questions with Private Replies
			List<QnaQuestion> questionsWithPrivateReplies = questionLogic.getQuestionsWithPrivateReplies(externalLogic.getCurrentLocationId());
			if (questionsWithPrivateReplies.size() > 0) {
				UIBranchContainer entry = UIBranchContainer.make(listTable, "table-entry:");
				UIBranchContainer category = UIBranchContainer.make(entry,"category-entry:");

				initViewToggle(entry, category);
				UIMessage.make(category,"category-name","qna.view-questions.questions-with-private-replies");
				UIOutput.make(category,"modified-date","");
				UIOutput.make(category,"remove-category-cell","");
				renderQuestions(entry, questionsWithPrivateReplies, ViewPrivateReplyProducer.VIEW_ID, questionDeleteSelect);
			}
		}
	}

	/**
	 *	Initiate javascript to show/hide by category
	 */
	private void initViewToggle(UIBranchContainer entry, UIBranchContainer category) {
		UILink expandIcon = UILink.make(category, "expand-icon", EXPAND_ICON_URL);
		UILink collapseIcon = UILink.make(category, "collapse-icon", COLLAPSE_ICON_URL);

		UIInitBlock.make(category,"onclick-init","init_questions_toggle", new Object[]{expandIcon,collapseIcon,entry});
	}

	/**
	 * Renders list of questions
	 */
	private void renderQuestions(UIBranchContainer entry, List<QnaQuestion> questions, String viewIdForLink, UISelect select) {
		StringList deletable = new StringList();

		Collections.sort(questions, new QuestionsSortOrderComparator());

		for (int k=0; k<questions.size(); k++) {
			QnaQuestion qnaQuestion = questions.get(k);
			if (!qnaQuestion.getHidden()) {
				UIBranchContainer question = UIBranchContainer.make(entry, "question-entry:");
				UIInternalLink.make(question,"question-link", (String)null, new QuestionParams(viewIdForLink,qnaQuestion.getId().toString()));
				UIVerbatim.make(question,"question-link-text",TextUtil.stripTags(qnaQuestion.getQuestionText()));
				UIOutput.make(question,"answers-nr",qnaQuestion.getAnswers().size() +"");
				UIOutput.make(question,"views-nr",qnaQuestion.getViews().toString());
				UIOutput.make(question,"question-modified-date",DateUtil.getSimpleDate(qnaQuestion.getDateLastModified()));

				if (permissionLogic.canUpdate(externalLogic.getCurrentLocationId(), externalLogic.getCurrentUserId())) {
					//UIOutput.make(question,"remove-question-cell");
					//UIBoundBoolean.make(question, "remove-checkbox", false);										
					UIBranchContainer questionContainer = UIBranchContainer.make(question, "remove-question-checkbox-td:");					
					UISelectChoice.make(questionContainer, "remove-question-checkbox", select.getFullID(), deletable.size());
					deletable.add(qnaQuestion.getId().toString());
				}
			}
		}
		StringList tmpIds = deletable;
		deletable = new StringList();
		deletable.append(select.optionlist.getValue());
		deletable.append(tmpIds);
		select.optionlist.setValue(deletable.toStringArray());
	}

	/**
	 * Check if flag icon must be shown (if there are new questions which have not been viewed)
	 */
	private boolean showFlagIcon(List<QnaQuestion> newQuestions) {
		for (QnaQuestion qnaQuestion : newQuestions) {
			if (qnaQuestion.getViews() == 0) {
				return true;
			}
		}
		return false;
	}

}
