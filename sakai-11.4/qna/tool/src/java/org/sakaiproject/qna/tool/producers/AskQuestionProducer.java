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

import org.sakaiproject.content.api.FilePickerHelper;
import org.sakaiproject.entity.api.Reference;
import org.sakaiproject.qna.logic.CategoryLogic;
import org.sakaiproject.qna.logic.ExternalLogic;
import org.sakaiproject.qna.logic.OptionsLogic;
import org.sakaiproject.qna.logic.PermissionLogic;
import org.sakaiproject.qna.model.QnaCategory;
import org.sakaiproject.qna.model.QnaOptions;
import org.sakaiproject.qna.tool.otp.CategoryLocator;
import org.sakaiproject.qna.tool.otp.QuestionLocator;
import org.sakaiproject.qna.tool.params.AttachmentsHelperParams;
import org.sakaiproject.qna.tool.params.QuestionTextParams;
import org.sakaiproject.qna.tool.producers.renderers.AttachmentsViewRenderer;
import org.sakaiproject.qna.tool.producers.renderers.NavBarRenderer;
import org.sakaiproject.qna.tool.producers.renderers.SearchBarRenderer;
import org.sakaiproject.tool.api.SessionManager;
import org.sakaiproject.tool.api.ToolSession;

import uk.org.ponder.beanutil.BeanGetter;
import uk.org.ponder.rsf.components.UIBoundBoolean;
import uk.org.ponder.rsf.components.UICommand;
import uk.org.ponder.rsf.components.UIContainer;
import uk.org.ponder.rsf.components.UIELBinding;
import uk.org.ponder.rsf.components.UIForm;
import uk.org.ponder.rsf.components.UIInput;
import uk.org.ponder.rsf.components.UIMessage;
import uk.org.ponder.rsf.components.UISelect;
import uk.org.ponder.rsf.components.decorators.DecoratorList;
import uk.org.ponder.rsf.components.decorators.UIDisabledDecorator;
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

public class AskQuestionProducer implements ViewComponentProducer, NavigationCaseReporter, ActionResultInterceptor, ViewParamsReporter {

	public static final String VIEW_ID = "ask_question";
	
	public String getViewID() {
		return VIEW_ID;
	}

	private NavBarRenderer navBarRenderer;
	private SearchBarRenderer searchBarRenderer;
	private TextInputEvolver richTextEvolver;
	private OptionsLogic optionsLogic;
	private PermissionLogic permissionLogic;
	private ExternalLogic externalLogic;
	private CategoryLogic categoryLogic;
	private SessionManager sessionManager;
	private AttachmentsViewRenderer attachmentsViewRenderer;
	private BeanGetter ELEvaluator;
	
	public void setNavBarRenderer(NavBarRenderer navBarRenderer) {
		this.navBarRenderer = navBarRenderer;
	}

	public void setSearchBarRenderer(SearchBarRenderer searchBarRenderer) {
		this.searchBarRenderer = searchBarRenderer;
	}

	public void setRichTextEvolver(TextInputEvolver richTextEvolver) {
		this.richTextEvolver = richTextEvolver;
	}

	public void setOptionsLogic(OptionsLogic optionsLogic) {
		this.optionsLogic = optionsLogic;
	}

	public void setPermissionLogic(PermissionLogic permissionLogic) {
		this.permissionLogic = permissionLogic;
	}

	public void setExternalLogic(ExternalLogic externalLogic) {
		this.externalLogic = externalLogic;
	}

	public void setCategoryLogic(CategoryLogic categoryLogic) {
		this.categoryLogic = categoryLogic;
	}
	
	public void setAttachmentsViewRenderer(AttachmentsViewRenderer attachmentsViewRenderer) {
		this.attachmentsViewRenderer = attachmentsViewRenderer;
	}
	
	public void setSessionManager(SessionManager sessionManager) {
		this.sessionManager = sessionManager;
	}
	
	
    public void setELEvaluator(BeanGetter ELEvaluator) {
        this.ELEvaluator = ELEvaluator;
    }
    
    /**
     * @see ComponentProducer#fillComponents(UIContainer, ViewParameters, ComponentChecker)
     */
	@SuppressWarnings("unchecked")
	public void fillComponents(UIContainer tofill, ViewParameters viewparams, ComponentChecker checker) {
		QnaOptions options = optionsLogic.getOptionsForLocation(externalLogic.getCurrentLocationId());

		String multipleBeanMediator = "MultipleBeanMediator";
		String questionLocator = "QuestionLocator";
		String questionOTP = questionLocator + "." + QuestionLocator.NEW_1;
		String categoryLocator = "CategoryLocator";
		String categoryOTP = categoryLocator + "." + CategoryLocator.NEW_1;

		navBarRenderer.makeNavBar(tofill, "navIntraTool:", VIEW_ID);
		searchBarRenderer.makeSearchBar(tofill, "searchTool:", VIEW_ID);

		UIMessage.make(tofill, "page-title", "qna.ask-question.title");


		if (options.getAnonymousAllowed()) {
			UIMessage.make(tofill,"anonymous-note","qna.ask-question.anonymous-note");
		}

		UIMessage.make(tofill, "question-title", "qna.ask-question.question");

		UIForm form = UIForm.make(tofill, "ask-question-form");

		UIInput questiontext = UIInput.make(form, "question-input:",questionOTP +".questionText");
        
		if (((QuestionTextParams)viewparams).questionText != null) {
			form.addParameter(new UIELBinding(questionOTP +".questionText",((QuestionTextParams)viewparams).questionText));
			questiontext.setValue(((QuestionTextParams)viewparams).questionText);
		}
		richTextEvolver.evolveTextInput(questiontext);

        UIBoundBoolean.make(form,"answer-notify",questionOTP + ".notify",true);
        UIMessage.make(form,"answer-notify-label","qna.ask-question.notify-on-answer");

        List<QnaCategory> categories = categoryLogic.getCategoriesForLocation(externalLogic.getCurrentLocationId());
        
        if ((!options.isModerated() && categories.size() > 0) || 
        	(permissionLogic.canUpdate(externalLogic.getCurrentLocationId(), externalLogic.getCurrentUserId()) && categories.size() > 0) || 
        	permissionLogic.canAddNewCategory(externalLogic.getCurrentLocationId(), externalLogic.getCurrentUserId())) {
	        UIMessage.make(form, "category-text", "qna.ask-question-select-category");
        }
     
        
        String[] categoriesIds = new String[categories.size()];
        String[] categoriesText = new String[categories.size()];

        for (int i=0;i<categories.size();i++) {
        	QnaCategory category = categories.get(i);
        	categoriesIds[i] = category.getId();
        	categoriesText[i] = category.getCategoryText();
        }
        
        boolean displayOr = false;
        if ((permissionLogic.canUpdate(externalLogic.getCurrentLocationId(), externalLogic.getCurrentUserId()) || !options.isModerated()) && (categories.size() > 0)) {
        	UIMessage.make(form, "category-title", "qna.ask-question.category");
        	UISelect dropDown = UISelect.make(form, "category-select", categoriesIds, categoriesText, questionOTP + ".categoryId" ); 
        	displayOr = true;
        	   //if there is only 1 category disable the select
            if (categories.size() ==1 ) {
            	dropDown.decorators = new DecoratorList(new UIDisabledDecorator(true));
            }
        }

        if (permissionLogic.canUpdate(externalLogic.getCurrentLocationId(), externalLogic.getCurrentUserId()) ||
        	(permissionLogic.canAddNewCategory(externalLogic.getCurrentLocationId(), externalLogic.getCurrentUserId()) 
        		&& !options.isModerated())) {
        	if (displayOr) {
        		UIMessage.make(form,"or","qna.general.or"); }
        	UIMessage.make(form,"new-category-label","qna.ask-question.create-category");
        	UIInput.make(form, "new-category-name", categoryOTP + ".categoryText");
        }

        UIMessage.make(form,"attachments-title","qna.ask-question.attachments");
        
        ToolSession session = sessionManager.getCurrentToolSession();
		if (session.getAttribute(FilePickerHelper.FILE_PICKER_ATTACHMENTS) != null && (((List<Reference>)session.getAttribute(FilePickerHelper.FILE_PICKER_ATTACHMENTS)).size() > 0)) 
		{
        	attachmentsViewRenderer.makeAttachmentsView(form, "attachmentsViewTool:");
        	UICommand.make(form, "add-attachment-button", UIMessage.make("qna.ask-question.add-remove-attachment")).setReturn("attach");
        } else {
        	UIMessage.make(form,"no-attachments-msg","qna.ask-question.no-attachments");
        	UICommand.make(form, "add-attachment-button", UIMessage.make("qna.ask-question.add-attachment")).setReturn("attach");
        }
		
        if (!permissionLogic.canUpdate(externalLogic.getCurrentLocationId(), externalLogic.getCurrentUserId()) &&
        	options.isModerated()) {
        	UIMessage.make(form,"moderated-note","qna.ask-question.moderated-note");
        }

        UICommand.make(form,"add-question-button",UIMessage.make("qna.ask-question.add-question"),multipleBeanMediator + ".saveNew");
        UICommand.make(form,"cancel-button",UIMessage.make("qna.general.cancel"), questionLocator + ".cancel");
	}

	public List<NavigationCase> reportNavigationCases() {
		List<NavigationCase> list = new ArrayList<NavigationCase>();
		list.add(new NavigationCase("cancel", new SimpleViewParameters(QuestionsListProducer.VIEW_ID)));
		list.add(new NavigationCase("saved", new SimpleViewParameters(QuestionsListProducer.VIEW_ID)));
		list.add(new NavigationCase("attach",new AttachmentsHelperParams(AttachmentsHelperProducer.VIEW_ID)));
		return list;
	}
	
	public void interceptActionResult(ARIResult result, ViewParameters incoming, Object actionReturn) {
		if (result.resultingView instanceof AttachmentsHelperParams) {
			((AttachmentsHelperParams)result.resultingView).questionText = (String)ELEvaluator.getBean("QuestionLocator." + QuestionLocator.NEW_1 + ".questionText");
		}
	}
	
	public ViewParameters getViewParameters() {
		return new QuestionTextParams();
	}
}
