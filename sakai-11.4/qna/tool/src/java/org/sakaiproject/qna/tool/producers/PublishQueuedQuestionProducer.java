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

import org.sakaiproject.qna.logic.CategoryLogic;
import org.sakaiproject.qna.logic.ExternalLogic;
import org.sakaiproject.qna.logic.PermissionLogic;
import org.sakaiproject.qna.logic.QuestionLogic;
import org.sakaiproject.qna.model.QnaCategory;
import org.sakaiproject.qna.model.QnaQuestion;
import org.sakaiproject.qna.tool.otp.AnswerLocator;
import org.sakaiproject.qna.tool.otp.CategoryLocator;
import org.sakaiproject.qna.tool.params.QuestionParams;
import org.sakaiproject.qna.tool.producers.renderers.NavBarRenderer;

import uk.org.ponder.rsf.components.ELReference;
import uk.org.ponder.rsf.components.UICommand;
import uk.org.ponder.rsf.components.UIContainer;
import uk.org.ponder.rsf.components.UIELBinding;
import uk.org.ponder.rsf.components.UIForm;
import uk.org.ponder.rsf.components.UIInput;
import uk.org.ponder.rsf.components.UIMessage;
import uk.org.ponder.rsf.components.UIOutput;
import uk.org.ponder.rsf.components.UISelect;
import uk.org.ponder.rsf.components.UIVerbatim;
import uk.org.ponder.rsf.evolvers.TextInputEvolver;
import uk.org.ponder.rsf.flow.jsfnav.NavigationCase;
import uk.org.ponder.rsf.flow.jsfnav.NavigationCaseReporter;
import uk.org.ponder.rsf.view.ComponentChecker;
import uk.org.ponder.rsf.view.ComponentProducer;
import uk.org.ponder.rsf.view.ViewComponentProducer;
import uk.org.ponder.rsf.viewstate.SimpleViewParameters;
import uk.org.ponder.rsf.viewstate.ViewParameters;
import uk.org.ponder.rsf.viewstate.ViewParamsReporter;

public class PublishQueuedQuestionProducer implements ViewComponentProducer,NavigationCaseReporter,ViewParamsReporter {

	public static final String VIEW_ID = "publish_queued_question";
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
	
	private QuestionLogic questionLogic;
	public void setQuestionLogic(QuestionLogic questionLogic) {
		this.questionLogic = questionLogic;
	}
	
	private CategoryLogic categoryLogic;
	public void setCategoryLogic(CategoryLogic categoryLogic) {
		this.categoryLogic = categoryLogic;
	}
	
	private ExternalLogic externalLogic;
	public void setExternalLogic(ExternalLogic externalLogic) {
		this.externalLogic = externalLogic;
	}
	
	private PermissionLogic permissionLogic;
	public void setPermissionLogic(PermissionLogic permissionLogic) {
		this.permissionLogic = permissionLogic;
	}
	
    /**
     * @see ComponentProducer#fillComponents(UIContainer, ViewParameters, ComponentChecker)
     */
	public void fillComponents(UIContainer tofill, ViewParameters viewparams,
			ComponentChecker checker) {
		
		String questionLocator = "QuestionLocator";
		String categoryLocator = "CategoryLocator";
		String categoryOTP = categoryLocator + "." + CategoryLocator.NEW_1;
		String answerLocator = "AnswerLocator";
		String answerOTP = answerLocator + ".";
		
		String multipleBeanMediator = "MultipleBeanMediator";
		
		QuestionParams questionParams = (QuestionParams) viewparams;
		navBarRenderer.makeNavBar(tofill, "navIntraTool:", VIEW_ID);
		QnaQuestion question = questionLogic.getQuestionById(Long.valueOf(questionParams.questionid));
		
		String questionOTP = questionLocator + "." + question.getId();
		
		if (question.getAnswers().size() > 0) { // Does this question have answers (will be private)
			answerOTP += question.getAnswers().get(0).getId(); // Only take first one
		} else {
			answerOTP += AnswerLocator.NEW_1;
		}
		
		// Generate the page title
		UIMessage.make(tofill, "page-title", "qna.publish-queued-question.title");
		
		// Put in the form
		UIForm form = UIForm.make(tofill,"publish-queued-question-form");
		
		// Generate the question title
		UIMessage.make(form, "question-title", "qna.publish-queued-question.question-title");
				
		UIVerbatim.make(form, "unpublished-question", question.getQuestionText());
		
		UIOutput.make(form,"edit-span");
		
		UIInput editQuestionText = UIInput.make(form, "unpublished-question-edit:", questionOTP  +".questionText");
		richTextEvolver.evolveTextInput(editQuestionText);
				
	    List<QnaCategory> categories = categoryLogic.getCategoriesForLocation(externalLogic.getCurrentLocationId());

		// Generate the category title
	    if (categories.size() > 0 || permissionLogic.canAddNewCategory(externalLogic.getCurrentLocationId(), externalLogic.getCurrentUserId())) {
	    	UIMessage.make(form, "category-title", "qna.publish-queued-question.category-title");
	    }
	    
		String[] categoriesIds = new String[categories.size()];
		String[] categoriesText = new String[categories.size()];

		for (int i = 0; i < categories.size(); i++) {
			QnaCategory category = categories.get(i);
			categoriesIds[i] = category.getId();
			categoriesText[i] = category.getCategoryText();
		}
		
		boolean displayOr = false;
		if (categories.size() > 0) {
			// Generate the category note
			UIMessage.make(form, "category-note", "qna.publish-queued-question.category-note");
			
			if (question.getCategory() == null) {
				UISelect.make(form, "category-select", categoriesIds, categoriesText, questionOTP + ".categoryId" );	
			} else {
				UISelect.make(form, "category-select", categoriesIds, categoriesText, questionOTP + ".category.id");
			}
			displayOr = true;
		}
		
		if (permissionLogic.canAddNewCategory(externalLogic.getCurrentLocationId(), externalLogic.getCurrentUserId())) {
	        if (displayOr) {
	        	UIMessage.make(form,"or","qna.general.or");
	        }
			
	        UIMessage.make(form,"new-category-label","qna.publish-queued-question.category-label");
	        UIInput.make(form, "new-category-name", categoryOTP + ".categoryText");
		}
        
     // Generate the answer title
		UIMessage.make(form, "answer-title", "qna.publish-queued-question.answer-title");
		
		// Generate the answer note
		UIMessage.make(form, "answer-note", "qna.publish-queued-question.answer-note");
        
//		Generate the answer input box
		UIInput answertext = UIInput.make(form, "reply-input:", answerOTP  +".answerText");
	
		form.addParameter(new UIELBinding(answerOTP + ".question",new ELReference(questionLocator + "." + question.getId())));
		form.addParameter(new UIELBinding(answerOTP + ".privateReply",false)); // make it public if it isn't
		richTextEvolver.evolveTextInput(answertext);
        
		// Generate the different buttons
		UICommand.make(form, "published-button", UIMessage.make("qna.general.publish"), multipleBeanMediator +".publish");
		UICommand.make(form, "cancel-button",UIMessage.make("qna.general.cancel") ).setReturn("cancel");


	}

	public List<NavigationCase> reportNavigationCases() {
		List<NavigationCase> list = new ArrayList<NavigationCase>();
		list.add(new NavigationCase("saved-published",new SimpleViewParameters(QuestionsListProducer.VIEW_ID)));
		list.add(new NavigationCase("cancel",new SimpleViewParameters(QuestionsListProducer.VIEW_ID)));
		return list;
	}
	
	public ViewParameters getViewParameters() {
		return new QuestionParams();
	}

}
