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

import org.sakaiproject.qna.model.QnaQuestion;
import org.sakaiproject.qna.tool.params.AttachmentsHelperParams;
import org.sakaiproject.qna.tool.params.QuestionParams;
import org.sakaiproject.qna.tool.producers.renderers.AttachmentsViewRenderer;
import org.sakaiproject.qna.tool.producers.renderers.NavBarRenderer;

import uk.org.ponder.beanutil.BeanGetter;
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

public class EditPublishedQuestionProducer implements ViewComponentProducer,NavigationCaseReporter, ViewParamsReporter, ActionResultInterceptor {

	public static final String VIEW_ID = "edit_published_question";
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

	private BeanGetter ELEvaluator;
    public void setELEvaluator(BeanGetter ELEvaluator) {
        this.ELEvaluator = ELEvaluator;
    }
    
    private AttachmentsViewRenderer attachmentsViewRenderer;
	public void setAttachmentsViewRenderer(AttachmentsViewRenderer attachmentsViewRenderer) {
		this.attachmentsViewRenderer = attachmentsViewRenderer;
	}
	
    /**
     * @see ComponentProducer#fillComponents(UIContainer, ViewParameters, ComponentChecker)
     */
	public void fillComponents(UIContainer tofill, ViewParameters viewparams, ComponentChecker checker) {

		navBarRenderer.makeNavBar(tofill, "navIntraTool:", VIEW_ID);

		QuestionParams params = (QuestionParams) viewparams;

		String questionLocator = "QuestionLocator";
		String questionOTP = questionLocator + "." + params.questionid;
		QnaQuestion question = (QnaQuestion)ELEvaluator.getBean(questionOTP);
		
		if (question.getAnswers().size() > 0) {
			// Generate the warning if there is answers present
			UIMessage.make(tofill, "error-message", "qna.warning.question-has-answers");
		}

		// Generate the page title
		UIMessage.make(tofill, "page-title", "qna.edit-published-question.title");

		// Generate the question title
		UIMessage.make(tofill, "question-title", "qna.publish-queued-question.question-title");

		// Put in the form
		UIForm form = UIForm.make(tofill,"edit-published-question-form");

//		Generate the question input box
		UIInput questiontext = UIInput.make(form, "question-input:",questionOTP + ".questionText");
        richTextEvolver.evolveTextInput(questiontext);
        
		// Render attachments
        attachmentsViewRenderer.setupFilepickerSession(question);
		attachmentsViewRenderer.makeAttachmentsView(tofill, "attachmentsViewTool:");
		UICommand.make(form, "add-attachment-button", UIMessage.make("qna.ask-question.add-remove-attachment")).setReturn("attach");
        
		// Generate the different buttons
		UICommand.make(form, "update-button", UIMessage.make("qna.general.update"),questionLocator + ".edit");
		UICommand.make(form, "cancel-button",UIMessage.make("qna.general.cancel"),questionLocator + ".cancel" );

	}

	public List<NavigationCase> reportNavigationCases() {
		List<NavigationCase> list = new ArrayList<NavigationCase>();
		list.add(new NavigationCase("saved",new QuestionParams(ViewQuestionProducer.VIEW_ID)));
		list.add(new NavigationCase("cancel",new QuestionParams(ViewQuestionProducer.VIEW_ID)));
		list.add(new NavigationCase("attach",new AttachmentsHelperParams(AttachmentsHelperProducer.VIEW_ID)));
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
			if (((QuestionParams)incoming).returnToViewID != null) {
				params.viewID = ((QuestionParams)incoming).returnToViewID;
			}
		}
		if (result.resultingView instanceof AttachmentsHelperParams) {
			AttachmentsHelperParams resultParams = (AttachmentsHelperParams)result.resultingView;
			resultParams.returnToViewID = EditPublishedQuestionProducer.VIEW_ID;
			resultParams.questionid = ((QuestionParams)incoming).questionid;
		}
		
	}
}
