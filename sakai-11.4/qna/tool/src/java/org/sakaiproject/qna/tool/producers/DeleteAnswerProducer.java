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

import org.sakaiproject.qna.model.QnaAnswer;
import org.sakaiproject.qna.model.QnaQuestion;
import org.sakaiproject.qna.tool.params.AnswerParams;
import org.sakaiproject.qna.tool.params.QuestionParams;
import org.sakaiproject.qna.tool.producers.renderers.NavBarRenderer;
import org.sakaiproject.qna.tool.utils.DateUtil;
import org.sakaiproject.qna.utils.TextUtil;

import uk.org.ponder.beanutil.BeanGetter;
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
import uk.org.ponder.rsf.viewstate.ViewParameters;
import uk.org.ponder.rsf.viewstate.ViewParamsReporter;

public class DeleteAnswerProducer implements ViewComponentProducer, NavigationCaseReporter, ViewParamsReporter, ActionResultInterceptor {

	public static final String VIEW_ID = "delete_answer";
	public String getViewID() {
		return VIEW_ID;
	}

	private NavBarRenderer navBarRenderer;
	public void setNavBarRenderer(NavBarRenderer navBarRenderer) {
		this.navBarRenderer = navBarRenderer;
	}

	private BeanGetter ELEvaluator;
    public void setELEvaluator(BeanGetter ELEvaluator) {
        this.ELEvaluator = ELEvaluator;
    }

    /**
     * @see ComponentProducer#fillComponents(UIContainer, ViewParameters, ComponentChecker)
     */
	public void fillComponents(UIContainer tofill, ViewParameters viewparams,
			ComponentChecker checker) {

		navBarRenderer.makeNavBar(tofill, "navIntraTool:", VIEW_ID);

		AnswerParams params = (AnswerParams) viewparams;
		String answerLocator = "AnswerLocator";
		String answerOTP = answerLocator + "." + params.answerid;
		String questionLocator = "QuestionLocator";
		String questionOTP = questionLocator + "." + params.questionid;

		// Generate confirmation warning for the delete action
		UIMessage.make(tofill, "error-message", "qna.warning.delete-confirmation-note");

		// Generate the page title
		UIMessage.make(tofill, "page-title", "qna.general.delete-confirmation");

		// Put in the form
		UIForm form = UIForm.make(tofill,"delete-answer-form");

		UIMessage.make(form, "name-title", "qna.delete-answer.name-title");
		UIMessage.make(form, "category-title", "qna.delete-answer.category-title");
		UIMessage.make(form, "question-title", "qna.delete-answer.question-title");
		UIMessage.make(form, "modified-title", "qna.delete-answer.modified-title");

		QnaAnswer answer = (QnaAnswer)ELEvaluator.getBean(answerOTP);
		QnaQuestion question = (QnaQuestion)ELEvaluator.getBean(questionOTP);

		UIOutput.make(form, "answer-text", TextUtil.stripTags(answer.getAnswerText()));
		UIOutput.make(form, "category", question.getCategory() != null ? question.getCategory().getCategoryText() : "");
		UIOutput.make(form, "question-text", TextUtil.stripTags(question.getQuestionText()));
		UIOutput.make(form, "modified-date", DateUtil.getSimpleDate(answer.getDateLastModified()));

		// Generate the different buttons
		UICommand.make(form, "delete-button", UIMessage.make("qna.general.delete"), answerLocator + ".delete");
		UICommand.make(form, "cancel-button",UIMessage.make("qna.general.cancel") ).setReturn("cancel");
		// just to bind bean
		form.addParameter(new UIELBinding(answerOTP + ".id", answer.getId()));
	}

	public List<NavigationCase> reportNavigationCases() {
		List<NavigationCase> list = new ArrayList<NavigationCase>();
		list.add(new NavigationCase("deleted", new QuestionParams(ViewQuestionProducer.VIEW_ID)));
		list.add(new NavigationCase("cancel", new QuestionParams(ViewQuestionProducer.VIEW_ID)));
		return list;
	}

	public ViewParameters getViewParameters() {
		return new AnswerParams();
	}

	public void interceptActionResult(ARIResult result,	ViewParameters incoming, Object actionReturn) {
		if (result.resultingView instanceof QuestionParams) {
			QuestionParams params = (QuestionParams)result.resultingView;
			params.questionid = ((AnswerParams)incoming).questionid;
		}
	}

}
