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

import org.sakaiproject.qna.tool.params.AnswerParams;
import org.sakaiproject.qna.tool.params.QuestionParams;
import org.sakaiproject.qna.tool.producers.renderers.NavBarRenderer;

import uk.org.ponder.rsf.components.UICommand;
import uk.org.ponder.rsf.components.UIContainer;
import uk.org.ponder.rsf.components.UIForm;
import uk.org.ponder.rsf.components.UIInput;
import uk.org.ponder.rsf.components.UIMessage;
import uk.org.ponder.rsf.evolvers.TextInputEvolver;
import uk.org.ponder.rsf.flow.ARIResult;
import uk.org.ponder.rsf.flow.ActionResultInterceptor;
import uk.org.ponder.rsf.flow.jsfnav.NavigationCase;
import uk.org.ponder.rsf.flow.jsfnav.NavigationCaseReporter;
import uk.org.ponder.rsf.view.ComponentChecker;
import uk.org.ponder.rsf.view.ComponentProducer;
import uk.org.ponder.rsf.view.ViewComponentProducer;
import uk.org.ponder.rsf.viewstate.ViewParameters;
import uk.org.ponder.rsf.viewstate.ViewParamsReporter;

public class EditPublishedAnswerProducer implements ViewComponentProducer, NavigationCaseReporter, ViewParamsReporter, ActionResultInterceptor {

	public static final String VIEW_ID = "edit_published_answer";
	public String getViewID() {
		return VIEW_ID;
	}


	private TextInputEvolver richTextEvolver;
	public void setRichTextEvolver(TextInputEvolver richTextEvolver) {
        this.richTextEvolver = richTextEvolver;
    }

	private NavBarRenderer navBarRenderer;
	public void setNavBarRenderer(NavBarRenderer navBarRenderer) {
		this.navBarRenderer = navBarRenderer;
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

		// Generate the warning if the answers were already viewed
		UIMessage.make(tofill, "error-message", "qna.warning.answer-already-viewed");

		// Generate the page title
		UIMessage.make(tofill, "page-title", "qna.edit-published-answer.title");

		// Generate the answer title
		UIMessage.make(tofill, "answer-title", "qna.edit-published-answer.answer-title");

		// Put in the form
		UIForm form = UIForm.make(tofill,"edit-published-answer-form");


//		Generate the answer input box
		UIInput answertext = UIInput.make(form, "answer-input:",answerOTP + ".answerText");
        richTextEvolver.evolveTextInput(answertext);

		// Generate the different buttons
		UICommand.make(form, "update-button", UIMessage.make("qna.general.update"),answerLocator + ".saveAll");
		UICommand.make(form, "cancel-button",UIMessage.make("qna.general.cancel") ).setReturn("cancel");

	}

	public List<NavigationCase> reportNavigationCases() {
		List<NavigationCase> list = new ArrayList<NavigationCase>();
		list.add(new NavigationCase(null,new QuestionParams(ViewQuestionProducer.VIEW_ID)));
		list.add(new NavigationCase("cancel",new QuestionParams(ViewQuestionProducer.VIEW_ID)));
		return list;
	}

	public ViewParameters getViewParameters() {
		return new AnswerParams();
	}

	public void interceptActionResult(ARIResult result, ViewParameters incoming, Object actionReturn) {
		if (result.resultingView instanceof QuestionParams) {
			QuestionParams params = (QuestionParams)result.resultingView;
			params.questionid = ((AnswerParams)incoming).questionid;
		}
	}

}
