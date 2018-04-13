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
import org.sakaiproject.qna.model.QnaAnswer;
import org.sakaiproject.qna.model.QnaQuestion;
import org.sakaiproject.qna.tool.params.QuestionParams;
import org.sakaiproject.qna.tool.producers.renderers.QuestionIteratorRenderer;
import org.sakaiproject.qna.tool.producers.renderers.NavBarRenderer;
import org.sakaiproject.qna.tool.utils.DateUtil;

import uk.org.ponder.rsf.components.UIBranchContainer;
import uk.org.ponder.rsf.components.UICommand;
import uk.org.ponder.rsf.components.UIContainer;
import uk.org.ponder.rsf.components.UIForm;
import uk.org.ponder.rsf.components.UIMessage;
import uk.org.ponder.rsf.components.UIOutput;
import uk.org.ponder.rsf.components.UIVerbatim;
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

public class ViewPrivateReplyProducer implements ViewComponentProducer, NavigationCaseReporter,ViewParamsReporter,ActionResultInterceptor {

	public static final String VIEW_ID = "view_private_reply";
	public String getViewID() {
		return VIEW_ID;
	}

	private QuestionIteratorRenderer questionIteratorRenderer;
	public void setQuestionIteratorRenderer(QuestionIteratorRenderer questionIteratorRenderer) {
		this.questionIteratorRenderer = questionIteratorRenderer;
	}

	private NavBarRenderer navBarRenderer;
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

		navBarRenderer.makeNavBar(tofill, "navIntraTool:", VIEW_ID);
		questionIteratorRenderer.makeQuestionIterator(tofill, "iterator1:", question);

		UIMessage.make(tofill, "page-title", "qna.view-private-reply.title");
		UIMessage.make(tofill, "sub-title", "qna.view-private-reply.subtitle");

		UIVerbatim.make(tofill, "unpublished-question",question.getQuestionText());
		
		// If anonymous remove name
		String dateToDisplay = DateUtil.getSimpleDateTime(question.getDateLastModified());
		if (question.isAnonymous()) {
			UIMessage.make(tofill,"unpublished-question-submitter","qna.view-private-reply.submitter-detail-anonymous", new Object[] {dateToDisplay});
		} else {
			UIMessage.make(tofill,"unpublished-question-submitter","qna.view-private-reply.submitter-detail", new Object[] {externalLogic.getUserDisplayName(question.getOwnerId()),dateToDisplay,});
		}

		UIMessage.make(tofill,"answer-title","qna.view-private-reply.answer");

		List<QnaAnswer> answers = question.getAnswers();

		for (QnaAnswer qnaAnswer : answers) {
			if (qnaAnswer.isPrivateReply()) {
				UIBranchContainer privateReply = UIBranchContainer.make(tofill, "private-reply:");
				UIVerbatim.make(privateReply,"private-reply-text", qnaAnswer.getAnswerText());
				UIOutput.make(privateReply,"private-reply-timestamp", DateUtil.getSimpleDateTime(qnaAnswer.getDateLastModified()));
			}
		}

		questionIteratorRenderer.makeQuestionIterator(tofill, "iterator2:", question);

		UIForm form = UIForm.make(tofill, "private-reply-form");
		UICommand.make(form,"publish-question-button",UIMessage.make("qna.view-private-reply.publish")).setReturn("publish");
		UICommand.make(form,"delete-button",UIMessage.make("qna.general.delete")).setReturn("delete");
		UICommand.make(form,"cancel-button",UIMessage.make("qna.general.cancel")).setReturn("cancel");
	}

	public List<NavigationCase> reportNavigationCases() {
		List<NavigationCase> list = new ArrayList<NavigationCase>();
		list.add(new NavigationCase("cancel",new SimpleViewParameters(QuestionsListProducer.VIEW_ID)));
		list.add(new NavigationCase("publish",new QuestionParams(PublishQueuedQuestionProducer.VIEW_ID)));
		list.add(new NavigationCase("delete",new QuestionParams(DeleteQuestionProducer.VIEW_ID)));
		return list;
	}

	public ViewParameters getViewParameters() {
		return new QuestionParams();
	}

	public void interceptActionResult(ARIResult result,ViewParameters incoming, Object actionReturn) {
		if (result.resultingView instanceof QuestionParams) {
			QuestionParams params = (QuestionParams)result.resultingView;
			params.questionid = ((QuestionParams)incoming).questionid;
		}
	}

}
