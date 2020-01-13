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
package org.sakaiproject.qna.tool.producers;

import java.util.ArrayList;
import java.util.List;

import org.sakaiproject.qna.logic.ExternalLogic;
import org.sakaiproject.qna.logic.OptionsLogic;
import org.sakaiproject.qna.logic.QuestionLogic;
import org.sakaiproject.qna.model.QnaAnswer;
import org.sakaiproject.qna.model.QnaQuestion;
import org.sakaiproject.qna.tool.params.QuestionParams;
import org.sakaiproject.qna.tool.producers.renderers.NavBarRenderer;
import org.sakaiproject.qna.tool.utils.DateUtil;
import org.sakaiproject.qna.utils.TextUtil;

import uk.org.ponder.rsf.components.UIBranchContainer;
import uk.org.ponder.rsf.components.UICommand;
import uk.org.ponder.rsf.components.UIContainer;
import uk.org.ponder.rsf.components.UIDeletionBinding;
import uk.org.ponder.rsf.components.UIForm;
import uk.org.ponder.rsf.components.UIJointContainer;
import uk.org.ponder.rsf.components.UIMessage;
import uk.org.ponder.rsf.components.UIOutput;
import uk.org.ponder.rsf.components.decorators.UIRowsDecorator;
import uk.org.ponder.rsf.flow.ARIResult;
import uk.org.ponder.rsf.flow.ActionResultInterceptor;
import uk.org.ponder.rsf.flow.jsfnav.NavigationCase;
import uk.org.ponder.rsf.flow.jsfnav.NavigationCaseReporter;
import uk.org.ponder.rsf.view.ComponentChecker;
import uk.org.ponder.rsf.view.ComponentProducer;
import uk.org.ponder.rsf.view.ViewComponentProducer;
import uk.org.ponder.rsf.viewstate.SimpleViewParameters;
import uk.org.ponder.rsf.viewstate.ViewParameters;
import uk.org.ponder.rsf.viewstate.ViewParamsReporter;

public class DeleteQuestionsProducer implements ViewComponentProducer, NavigationCaseReporter, ViewParamsReporter, ActionResultInterceptor {

	public static final String VIEW_ID = "delete_questions";
	private NavBarRenderer navBarRenderer;
	private OptionsLogic optionsLogic;
	private ExternalLogic externalLogic;
	private QuestionLogic questionLogic;

	public String getViewID() {
		return VIEW_ID;
	}

	public void setNavBarRenderer(NavBarRenderer navBarRenderer) {
		this.navBarRenderer = navBarRenderer;
	}
	public void setOptionsLogic(OptionsLogic optionsLogic) {
		this.optionsLogic = optionsLogic;
	}
	public void setExternalLogic(ExternalLogic externalLogic) {
		this.externalLogic = externalLogic;
	}
	public void setQuestionLogic(QuestionLogic questionLogic) {
		this.questionLogic = questionLogic;
	}

    /**
     * @see ComponentProducer#fillComponents(UIContainer, ViewParameters, ComponentChecker)
     */
	public void fillComponents(UIContainer tofill, ViewParameters viewparams, ComponentChecker checker) {
		QuestionParams params = (QuestionParams)viewparams;

		if ((params.questionids == null) || (params.questionids.length == 0)) {
			UIMessage.make(tofill, "error-message", "" +
					"qna.warning.no-questions-selected");
			//we can't render this page without a list of questions
			return;
		}

		navBarRenderer.makeNavBar(tofill, "navIntraTool:", VIEW_ID);

		String locationId = externalLogic.getCurrentLocationId();
		String questionLocator = "QuestionLocator";

		UIForm form = UIForm.make(tofill, "delete-questions-form");

		UIJointContainer listTable = new UIJointContainer(form, "question-list-table", "question-list-table:");

		UIBranchContainer questionHeadings = UIBranchContainer.make(listTable, "question-headings:");

		UIMessage.make(questionHeadings, "dqs-name", "qna.delete-question.name-title");
		UIMessage.make(questionHeadings, "dqs-category", "qna.delete-question.category-title");
		UIMessage.make(questionHeadings, "dqs-answers", "qna.delete-question.answers-title");
		UIMessage.make(questionHeadings, "dqs-views", "qna.view-questions.views");
		UIMessage.make(questionHeadings, "dqs-modified", "qna.delete-question.modified-title");

		// Generate the page title
		UIMessage.make(tofill, "page-title", "qna.general.delete-confirmation");
		
		// Generate confirmation warning for the delete action
		UIMessage.make(tofill, "error-message3", "qna.warning.delete-confirmation-note");
		
		for (int k=0; k<params.questionids.length; k++) {

			UIBranchContainer questionContainer = UIBranchContainer.make(listTable, "question-entry:");

			String questionOTP = questionLocator+"."+params.questionids[k];

			form.parameters.add(new UIDeletionBinding(questionOTP));

			QnaQuestion question = questionLogic.getQuestionById(Long.valueOf(params.questionids[k]));
			List<QnaAnswer> answerList = question.getAnswers();
			boolean hasPrivateReply = false;

			for (QnaAnswer answer : answerList) {
				if (answer.isPrivateReply()) {
					hasPrivateReply = true;
				}
			}
			
			//we need a row object for the allerts
			UIBranchContainer alertRow = UIBranchContainer.make(tofill, "alertsrow:"); 
			
			// Generate the warning if the question has not been answered
			if (optionsLogic.isModerationOn(locationId) && (!hasPrivateReply) && (answerList.size() == 0)) {
				UIMessage.make(alertRow, "error-message1", "qna.warning.questions-not-answered");
			}

			// Generate warning for associated answers
			if ((question.isPublished()) && (answerList.size() > 0)) {
				UIMessage.make(alertRow, "error-message2", "qna.warning.questions-with-answers");
			}

			



			UIOutput.make(questionContainer, "name", TextUtil.stripTags(question.getQuestionText()));
			UIOutput.make(questionContainer, "category", (question.getCategory() != null) ? question.getCategory().getCategoryText() : "");
			UIOutput.make(questionContainer, "answers", answerList.size()+"");
			UIOutput.make(questionContainer, "views", question.getViews()+"");
			UIOutput.make(questionContainer, "modified", DateUtil.getSimpleDate(question.getDateLastModified()));
		}

		UICommand.make(form, "delete-button", UIMessage.make("qna.general.delete")).setReturn("delete");
		UICommand.make(form, "cancel-button", UIMessage.make("qna.general.cancel")).setReturn("cancel");
	}

	public List<NavigationCase> reportNavigationCases() {
		List<NavigationCase> list = new ArrayList<NavigationCase>();
		list.add(new NavigationCase("delete", new QuestionParams(QuestionsListProducer.VIEW_ID)));
		list.add(new NavigationCase("cancel", new SimpleViewParameters(QuestionsListProducer.VIEW_ID)));
		return list;
	}

	public ViewParameters getViewParameters() {
		return new QuestionParams();
	}

	public void interceptActionResult(ARIResult result, ViewParameters incoming, Object actionReturn) {
		// TODO Auto-generated method stub

	}
}