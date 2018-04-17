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
import org.sakaiproject.qna.tool.params.SimpleReturnToParams;
import org.sakaiproject.qna.tool.producers.renderers.NavBarRenderer;
import org.sakaiproject.qna.tool.utils.DateUtil;
import org.sakaiproject.qna.utils.TextUtil;

import uk.org.ponder.rsf.components.UICommand;
import uk.org.ponder.rsf.components.UIContainer;
import uk.org.ponder.rsf.components.UIELBinding;
import uk.org.ponder.rsf.components.UIForm;
import uk.org.ponder.rsf.components.UIMessage;
import uk.org.ponder.rsf.components.UIOutput;
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

public class DeleteQuestionProducer implements ViewComponentProducer, NavigationCaseReporter, ViewParamsReporter,ActionResultInterceptor {

	public static final String VIEW_ID = "delete_question";
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

		String questionLocator = "QuestionLocator";
		String questionOTP = questionLocator+"."+params.questionid;

		navBarRenderer.makeNavBar(tofill, "navIntraTool:", VIEW_ID);

		String locationId = externalLogic.getCurrentLocationId();

		QnaQuestion question = questionLogic.getQuestionById(Long.valueOf(params.questionid));
		List<QnaAnswer> answerList = question.getAnswers();
		boolean hasPrivateReply = false;

		for (QnaAnswer answer : answerList) {
			if (answer.isPrivateReply()) {
				hasPrivateReply = true;
			}
		}

		// Generate the warning if the question has not been answered
		if (optionsLogic.isModerationOn(locationId) && (hasPrivateReply)) {
			UIMessage.make(tofill, "error-message1", "qna.warning.question-not-answered");
		}

		// Generate warning for associated answers
		if ((question.isPublished()) && (answerList.size() > 0)) {
			UIMessage.make(tofill, "error-message2", "qna.warning.answer-associated");
		} else {
			// Generate confirmation warning for the delete action
			UIMessage.make(tofill, "error-message3", "qna.warning.delete-confirmation-note");
		}

		// Generate the page title
		UIMessage.make(tofill, "page-title", "qna.general.delete-confirmation");

		// Put in the form
		UIForm form = UIForm.make(tofill, "delete-question-form");

		UIMessage.make(form, "name-title", "qna.delete-question.name-title");
		UIMessage.make(form, "category-title", "qna.delete-question.category-title");
		UIMessage.make(form, "answers-title", "qna.delete-question.answers-title");
		UIMessage.make(form, "modified-title", "qna.delete-question.modified-title");

		UIOutput.make(form, "name", TextUtil.stripTags(question.getQuestionText()));
		UIOutput.make(form, "category", (question.getCategory() != null) ? question.getCategory().getCategoryText() : "");
		UIOutput.make(form, "answers", answerList.size()+"");
		UIOutput.make(form, "modified", DateUtil.getSimpleDate(question.getDateLastModified()));

		// Generate the different buttons
		UICommand delete = UICommand.make(form, "delete-button", UIMessage.make("qna.general.delete"), questionLocator+".delete");
		delete.addParameter(new UIELBinding(questionOTP+".id", question.getId()));
		UICommand.make(form, "cancel-button", UIMessage.make("qna.general.cancel")).setReturn("cancel");

	}

	public List<NavigationCase> reportNavigationCases() {
		List<NavigationCase> list = new ArrayList<NavigationCase>();
		list.add(new NavigationCase("delete", new SimpleViewParameters(QuestionsListProducer.VIEW_ID)));
		list.add(new NavigationCase("cancel", new SimpleViewParameters(QuestionsListProducer.VIEW_ID)));
		return list;
	}

	public ViewParameters getViewParameters() {
		return new QuestionParams();
	}

	public void interceptActionResult(ARIResult result,
			ViewParameters incoming, Object actionReturn) {
		if (incoming instanceof SimpleReturnToParams) {
			if (((SimpleReturnToParams)incoming).returnToViewID != null) {
				((SimpleViewParameters)result.resultingView).viewID = ((SimpleReturnToParams)incoming).returnToViewID;
			}
		}

	}
}