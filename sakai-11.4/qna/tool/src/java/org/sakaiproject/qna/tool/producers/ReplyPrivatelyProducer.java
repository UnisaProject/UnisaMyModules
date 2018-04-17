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
import org.sakaiproject.qna.logic.QuestionLogic;
import org.sakaiproject.qna.model.QnaQuestion;
import org.sakaiproject.qna.tool.otp.AnswerLocator;
import org.sakaiproject.qna.tool.params.QuestionParams;
import org.sakaiproject.qna.tool.producers.renderers.NavBarRenderer;
import org.sakaiproject.qna.tool.utils.DateUtil;

import uk.org.ponder.rsf.components.ELReference;
import uk.org.ponder.rsf.components.UICommand;
import uk.org.ponder.rsf.components.UIContainer;
import uk.org.ponder.rsf.components.UIELBinding;
import uk.org.ponder.rsf.components.UIForm;
import uk.org.ponder.rsf.components.UIInput;
import uk.org.ponder.rsf.components.UIMessage;
import uk.org.ponder.rsf.components.UIVerbatim;
import uk.org.ponder.rsf.evolvers.TextInputEvolver;
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

public class ReplyPrivatelyProducer implements ViewComponentProducer, NavigationCaseReporter, ViewParamsReporter, ActionResultInterceptor{

	public static final String VIEW_ID = "reply_privately";
	public String getViewID() {
		return VIEW_ID;
	}

	private NavBarRenderer navBarRenderer;
	private TextInputEvolver richTextEvolver;

    public void setRichTextEvolver(TextInputEvolver richTextEvolver) {
        this.richTextEvolver = richTextEvolver;
    }

	public void setNavBarRenderer(NavBarRenderer navBarRenderer) {
		this.navBarRenderer = navBarRenderer;
	}

	private QuestionLogic questionLogic;
	public void setQuestionLogic(QuestionLogic questionLogic) {
		this.questionLogic = questionLogic;
	}

	private ExternalLogic externalLogic;
	public void setExternalLogic(ExternalLogic externalLogic) {
		this.externalLogic = externalLogic;
	}

	/**
	 * @see ComponentProducer#fillComponents(UIContainer, ViewParameters, ComponentChecker)
	 */
	public void fillComponents(UIContainer tofill, ViewParameters viewparams,
			ComponentChecker checker) {

		QuestionParams questionParams = (QuestionParams) viewparams;
		QnaQuestion question = questionLogic.getQuestionById(Long.valueOf(questionParams.questionid));

		String answerLocator = "AnswerLocator";
		String answerOTP = answerLocator + "." + AnswerLocator.NEW_1;
		String questionLocator = "QuestionLocator";

		navBarRenderer.makeNavBar(tofill, "navIntraTool:", VIEW_ID);
		UIMessage.make(tofill, "page-title", "qna.reply-privately.title");
		UIMessage.make(tofill, "sub-title", "qna.reply-privately.subtitle");

		UIForm form = UIForm.make(tofill,"reply-privately-form");

		UIVerbatim.make(form, "unpublished-question", question.getQuestionText());
		
		String dateToDisplay = DateUtil.getSimpleDateTime(question.getDateLastModified());
		if (question.isAnonymous()) {
			UIMessage.make(tofill,"unpublished-question-submitter","qna.queued-question.submitter-detail-anonymous", new Object[] {dateToDisplay,question.getViews()});
		} else {
			UIMessage.make(tofill,"unpublished-question-submitter","qna.queued-question.submitter-detail", new Object[] {externalLogic.getUserDisplayName(question.getOwnerId()),dateToDisplay,question.getViews()});
		}

		UIMessage.make(form,"answer-title","qna.reply-privately.answer");
		UIInput answertext = UIInput.make(form, "reply-input:",answerOTP + ".answerText"); // last parameter is value binding

		form.addParameter(new UIELBinding(answerOTP + ".privateReply", true));
		form.parameters.add(new UIELBinding(answerOTP + ".question", new ELReference(questionLocator + "." + question.getId())));
	    richTextEvolver.evolveTextInput(answertext);

        UICommand.make(form,"send-button",UIMessage.make("qna.reply-privately.send"),answerLocator + ".saveAll");
        UICommand.make(form,"cancel-button",UIMessage.make("qna.general.cancel")).setReturn("cancel");
	}

	public List<NavigationCase> reportNavigationCases() {
		List<NavigationCase> list = new ArrayList<NavigationCase>();
		list.add(new NavigationCase("cancel",new QuestionParams(QueuedQuestionProducer.VIEW_ID)));
		list.add(new NavigationCase("saved", new SimpleViewParameters(QuestionsListProducer.VIEW_ID)));
		return list;
	}

	public ViewParameters getViewParameters() {
		return new QuestionParams();
	}

	public void interceptActionResult(ARIResult result,
			ViewParameters incoming, Object actionReturn) {
		if (result.resultingView instanceof QuestionParams) {
			QuestionParams params = (QuestionParams)result.resultingView;
			params.questionid = ((QuestionParams)incoming).questionid;
		}
	}


}
