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
import org.sakaiproject.qna.tool.params.QuestionParams;
import org.sakaiproject.qna.tool.producers.renderers.AttachmentsViewRenderer;
import org.sakaiproject.qna.tool.producers.renderers.NavBarRenderer;
import org.sakaiproject.qna.tool.producers.renderers.QuestionIteratorRenderer;
import org.sakaiproject.qna.tool.producers.renderers.SearchBarRenderer;
import org.sakaiproject.qna.tool.utils.DateUtil;

import uk.org.ponder.messageutil.TargettedMessage;
import uk.org.ponder.messageutil.TargettedMessageList;
import uk.org.ponder.rsf.components.UICommand;
import uk.org.ponder.rsf.components.UIContainer;
import uk.org.ponder.rsf.components.UIForm;
import uk.org.ponder.rsf.components.UIMessage;
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

public class QueuedQuestionProducer implements ViewComponentProducer,NavigationCaseReporter,ViewParamsReporter,ActionResultInterceptor {

	public static final String VIEW_ID = "queued_question";
	public String getViewID() {
		return VIEW_ID;
	}

	private NavBarRenderer navBarRenderer;
	private SearchBarRenderer searchBarRenderer;
	private QuestionLogic questionLogic;
	private ExternalLogic externalLogic;
    private AttachmentsViewRenderer attachmentsViewRenderer;
	private QuestionIteratorRenderer questionIteratorRenderer;
	
	public void setNavBarRenderer(NavBarRenderer navBarRenderer) {
		this.navBarRenderer = navBarRenderer;
	}

	public void setSearchBarRenderer(SearchBarRenderer searchBarRenderer) {
		this.searchBarRenderer = searchBarRenderer;
	}

	public void setQuestionLogic(QuestionLogic questionLogic) {
		this.questionLogic = questionLogic;
	}

	public void setExternalLogic(ExternalLogic externalLogic) {
		this.externalLogic = externalLogic;
	}
	
	public void setAttachmentsViewRenderer(AttachmentsViewRenderer attachmentsViewRenderer) {
		this.attachmentsViewRenderer = attachmentsViewRenderer;
	}
	
	public void setQuestionIteratorRenderer(QuestionIteratorRenderer questionIteratorRenderer) {
		this.questionIteratorRenderer = questionIteratorRenderer;
	}
	
	private TargettedMessageList targettedMessageList;	
	public void setTargettedMessageList(TargettedMessageList targettedMessageList) {
		this.targettedMessageList = targettedMessageList;
	}

	/**
	 * @see ComponentProducer#fillComponents(UIContainer, ViewParameters, ComponentChecker)
	 */
	public void fillComponents(UIContainer tofill, ViewParameters viewparams, ComponentChecker checker) {

		QuestionParams questionParams = (QuestionParams) viewparams;
		QnaQuestion question = questionLogic.getQuestionById(Long.valueOf(questionParams.questionid));
		
		navBarRenderer.makeNavBar(tofill, "navIntraTool:", VIEW_ID);
		searchBarRenderer.makeSearchBar(tofill, "searchTool", VIEW_ID);
		//QNA-179 its possible the question doesn't exist
		if (question == null) {
			targettedMessageList.addMessage(new TargettedMessage("qna.warning.no-such-question", new Object[]{questionParams.questionid}, TargettedMessage.SEVERITY_ERROR));
			return;
		}
		
		questionIteratorRenderer.makeQuestionIterator(tofill, "iterator1:",question);
		// Generate the page title and the page sub title
		UIMessage.make(tofill, "page-title", "qna.queued-question.title");
		UIMessage.make(tofill, "sub-title", "qna.queued-question.subtitle");

		// Put in the form
		UIForm form = UIForm.make(tofill, "queued-question-form");

		UIVerbatim.make(form,"queued-question", question.getQuestionText());
		if (question.getAttachments().size() > 0) {
			attachmentsViewRenderer.makeAttachmentsView(tofill, "attachmentsViewTool:", question); }
		
		UIMessage.make(tofill,"queued-question-id","qna.view-question.id", new Object[] { question.getId() + ""});

		// If anonymous remove name
		if (question.isAnonymous()) {
			UIMessage.make(tofill,"queued-question-submitter","qna.queued-question.submitter-detail-anonymous", new Object[] {DateUtil.getSimpleDateTime(question.getDateLastModified()),question.getViews()});
		} else {
			UIMessage.make(tofill,"queued-question-submitter","qna.queued-question.submitter-detail", new Object[] {externalLogic.getUserDisplayName(question.getOwnerId()),DateUtil.getSimpleDateTime(question.getDateLastModified()),question.getViews()});
		}
		
		// Generate the different buttons
		if (question.getOwnerId() != null) { // Can't private reply if it is totally anonymous
			UICommand.make(form, "queued-question-reply", UIMessage.make("qna.queued-question.reply")).setReturn("private_reply");	
		}
		UICommand.make(form, "queued-question-publish", UIMessage.make("qna.queued-question.publish")).setReturn("publish");
		UICommand.make(form, "queued-question-delete", UIMessage.make("qna.general.delete")).setReturn("delete");
		UICommand.make(form, "queued-question-cancel",UIMessage.make("qna.general.cancel") ).setReturn("cancel");
		
		questionLogic.incrementView(question.getId());
	}

	public List<NavigationCase> reportNavigationCases() {
		List<NavigationCase> list = new ArrayList<NavigationCase>();
		list.add(new NavigationCase("private_reply",new QuestionParams(ReplyPrivatelyProducer.VIEW_ID)));
		list.add(new NavigationCase("cancel",new SimpleViewParameters(QuestionsListProducer.VIEW_ID)));
		list.add(new NavigationCase("publish",new QuestionParams(PublishQueuedQuestionProducer.VIEW_ID)));
		list.add(new NavigationCase("delete",new QuestionParams(DeleteQuestionProducer.VIEW_ID)));
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
