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
import java.util.Collections;
import java.util.List;

import org.sakaiproject.qna.comparators.CategoriesSortOrderComparator;
import org.sakaiproject.qna.comparators.QuestionsSortOrderComparator;
import org.sakaiproject.qna.logic.CategoryLogic;
import org.sakaiproject.qna.logic.ExternalLogic;
import org.sakaiproject.qna.logic.PermissionLogic;
import org.sakaiproject.qna.logic.QuestionLogic;
import org.sakaiproject.qna.model.QnaCategory;
import org.sakaiproject.qna.model.QnaQuestion;
import org.sakaiproject.qna.tool.params.CategoryParams;
import org.sakaiproject.qna.tool.params.OrganiseParams;
import org.sakaiproject.qna.tool.params.QuestionParams;
import org.sakaiproject.qna.tool.producers.renderers.NavBarRenderer;
import org.sakaiproject.qna.utils.TextUtil;

import uk.org.ponder.messageutil.MessageLocator;
import uk.org.ponder.messageutil.TargettedMessage;
import uk.org.ponder.messageutil.TargettedMessageList;
import uk.org.ponder.rsf.components.UIBranchContainer;
import uk.org.ponder.rsf.components.UICommand;
import uk.org.ponder.rsf.components.UIContainer;
import uk.org.ponder.rsf.components.UIForm;
import uk.org.ponder.rsf.components.UIInitBlock;
import uk.org.ponder.rsf.components.UIInternalLink;
import uk.org.ponder.rsf.components.UILink;
import uk.org.ponder.rsf.components.UIMessage;
import uk.org.ponder.rsf.components.UIOutput;
import uk.org.ponder.rsf.components.UISelect;
import uk.org.ponder.rsf.components.UISelectChoice;
import uk.org.ponder.rsf.components.decorators.DecoratorList;
import uk.org.ponder.rsf.components.decorators.UITooltipDecorator;
import uk.org.ponder.rsf.flow.jsfnav.NavigationCase;
import uk.org.ponder.rsf.flow.jsfnav.NavigationCaseReporter;
import uk.org.ponder.rsf.view.ComponentChecker;
import uk.org.ponder.rsf.view.ComponentProducer;
import uk.org.ponder.rsf.view.ViewComponentProducer;
import uk.org.ponder.rsf.viewstate.SimpleViewParameters;
import uk.org.ponder.rsf.viewstate.ViewParameters;
import uk.org.ponder.rsf.viewstate.ViewParamsReporter;
import uk.org.ponder.stringutil.StringList;

public class OrganiseListProducer implements ViewComponentProducer, NavigationCaseReporter, ViewParamsReporter {
	
	public static final String VIEW_ID = "organise_list";
	
	private static final String EDIT_ICON_URL = "/library/image/silk/page_white_edit.png";
	private static final String LIGHTBULB_ON_ICON_URL = "/library/image/silk/lightbulb.png";
	private static final String LIGHTBULB_OFF_ICON_URL = "/library/image/silk/lightbulb_off.png";
	private static final String DELETE_ICON_URL = "/library/image/silk/cross.png";
	
	private NavBarRenderer navBarRenderer;
	private CategoryLogic categoryLogic;
	private ExternalLogic externalLogic;
	private PermissionLogic permissionLogic;
	private QuestionLogic questionLogic;
    private TargettedMessageList messages;
	private MessageLocator messageLocator;
    
	public void setNavBarRenderer(NavBarRenderer navBarRenderer) {
		this.navBarRenderer = navBarRenderer;
	}
    	
	public String getViewID() {
		return VIEW_ID;
	}
	
	public void setCategoryLogic(CategoryLogic categoryLogic) {
		this.categoryLogic = categoryLogic;
	}
	
	public void setExternalLogic(ExternalLogic externalLogic) {
		this.externalLogic = externalLogic;
	}
	
	public void setPermissionLogic(PermissionLogic permissionLogic) {
		this.permissionLogic = permissionLogic;
	}

	public void setQuestionLogic(QuestionLogic questionLogic) {
		this.questionLogic = questionLogic;
	}
	
	public void setMessages(TargettedMessageList messages) {
		this.messages = messages;
	}
	
	public void setMessageLocator(MessageLocator messageLocator) {
        this.messageLocator = messageLocator;
    }
	
    /**
     * @see ComponentProducer#fillComponents(UIContainer, ViewParameters, ComponentChecker)
     */
	public void fillComponents(UIContainer tofill, ViewParameters viewparams, ComponentChecker checker) {
		navBarRenderer.makeNavBar(tofill, "navIntraTool:", VIEW_ID);
		
		UIInitBlock.make(tofill, "init-organiser", "initOrganiser", new Object[]{});
		
		OrganiseParams params = (OrganiseParams)viewparams;

		if (params.id != null) {
			if (params.type.equalsIgnoreCase("cat")) {
				QnaCategory qnaCategory = categoryLogic.getCategoryById(params.id);
				qnaCategory.setHidden(Boolean.valueOf(!params.visible));
				categoryLogic.saveCategory(qnaCategory, externalLogic.getCurrentLocationId());
				if (params.visible) {
					messages.addMessage(new TargettedMessage("qna.organise.category-visible", null, TargettedMessage.SEVERITY_INFO));
				} else {
					messages.addMessage(new TargettedMessage("qna.organise.category-hidden", null, TargettedMessage.SEVERITY_INFO));
				}
			} else if (params.type.equalsIgnoreCase("que")) {
				QnaQuestion qnaQuestion = questionLogic.getQuestionById(Long.valueOf(params.id));
				qnaQuestion.setHidden(Boolean.valueOf(!params.visible));
				questionLogic.saveQuestion(qnaQuestion, externalLogic.getCurrentLocationId());
				if (params.visible) {
					messages.addMessage(new TargettedMessage("qna.organise.question-visible", null, TargettedMessage.SEVERITY_INFO));
				} else {
					messages.addMessage(new TargettedMessage("qna.organise.question-hidden", null, TargettedMessage.SEVERITY_INFO));
				}
			}
		}
		
		UIMessage.make(tofill, "page-title", "qna.organise.page-title");
		UIMessage.make(tofill, "page-message1", "qna.organise.page-message1");
		UIMessage.make(tofill, "page-message2", "qna.organise.page-message2");
		
		UIForm form = UIForm.make(tofill, "organise-form");

		UISelect catorder = UISelect.makeMultiple(form, "category-sort-order", null, "#{OrganiserHelper.catorder}", null);
		UISelect queorder = UISelect.makeMultiple(form, "question-sort-order", null, "#{OrganiserHelper.queorder}", null);
		UISelect questionCategoryOrder = UISelect.makeMultiple(form, "question-category-order", null, "#{OrganiserHelper.questionCategoryOrder}", null);
		
		List<QnaCategory> categories = categoryLogic.getCategoriesForLocation(externalLogic.getCurrentLocationId());
		Collections.sort(categories, new CategoriesSortOrderComparator());
		
		int categoryCount = 0;
		int questionCount = 0;

		StringList catorderlist = new StringList();
		StringList queorderlist = new StringList();
		StringList questionCategoryOrderList = new StringList();
		
		for (QnaCategory qnaCategory : categories) {
			UIBranchContainer categoryContainer = UIBranchContainer.make(form, "category-entry:",Integer.toString(categoryCount));
			categoryCount++;
			UIOutput.make(categoryContainer, "category-name", TextUtil.stripTags(qnaCategory.getCategoryText()));
			
			if (permissionLogic.canUpdate(externalLogic.getCurrentLocationId(), externalLogic.getCurrentUserId())) {
				UILink.make(categoryContainer, "edit-category-icon", EDIT_ICON_URL);
				UIInternalLink editCategory = UIInternalLink.make(categoryContainer, "edit-category-link", new CategoryParams(CategoryProducer.VIEW_ID, "1", qnaCategory.getCategoryText(), qnaCategory.getId(),VIEW_ID));
				editCategory.decorators =  new DecoratorList(new UITooltipDecorator(messageLocator.getMessage("qna.organise.edit-category-tooltip")));
				
				if (qnaCategory.getHidden()) {
					UILink.make(categoryContainer, "hide-category-icon", LIGHTBULB_OFF_ICON_URL);
					UIInternalLink showCategory = UIInternalLink.make(categoryContainer, "hide-category-link", new OrganiseParams(OrganiseListProducer.VIEW_ID, "cat", qnaCategory.getId(), true));
					showCategory.decorators = new DecoratorList(new UITooltipDecorator(messageLocator.getMessage("qna.organise.show-category-tooltip")));
				} else {
					UILink.make(categoryContainer, "hide-category-icon", LIGHTBULB_ON_ICON_URL);
					UIInternalLink hideCategory = UIInternalLink.make(categoryContainer, "hide-category-link", new OrganiseParams(OrganiseListProducer.VIEW_ID, "cat", qnaCategory.getId(), false));
					hideCategory.decorators = new DecoratorList(new UITooltipDecorator(messageLocator.getMessage("qna.organise.hide-category-tooltip")));
				}
				
				if (categories.size() != 1) { // If there is only one category it should not be deletable
					UILink.make(categoryContainer, "delete-category-icon", DELETE_ICON_URL);
					UIInternalLink deleteCategory = UIInternalLink.make(categoryContainer, "delete-category-link", new CategoryParams(DeleteCategoryProducer.VIEW_ID, "1", qnaCategory.getCategoryText(), qnaCategory.getId(),VIEW_ID));
					deleteCategory.decorators =	new DecoratorList(new UITooltipDecorator(messageLocator.getMessage("qna.organise.delete-category-tooltip")));
				}
			}
			
			UISelectChoice.make(categoryContainer, "category-sort-order-checkbox", catorder.getFullID(), catorderlist.size());
			catorderlist.add(qnaCategory.getId());
			
			List<QnaQuestion> questions = qnaCategory.getQuestions();
			Collections.sort(questions, new QuestionsSortOrderComparator());
				
			for (QnaQuestion qnaQuestion : questions) {
				if (qnaQuestion.isPublished()) {
					UIBranchContainer questionContainer = UIBranchContainer.make(categoryContainer, "question-entry:",Integer.toString(questionCount));
					questionCount++;
					UIOutput.make(questionContainer, "question-text", TextUtil.stripTags(qnaQuestion.getQuestionText()));
				
					if (permissionLogic.canUpdate(externalLogic.getCurrentLocationId(), externalLogic.getCurrentUserId())) {
						UILink.make(questionContainer, "edit-question-icon", EDIT_ICON_URL);
						UIInternalLink editQuestion = UIInternalLink.make(questionContainer, "edit-question-link", new QuestionParams(EditPublishedQuestionProducer.VIEW_ID, qnaQuestion.getId().toString(),VIEW_ID));
						editQuestion.decorators = new DecoratorList(new UITooltipDecorator(messageLocator.getMessage("qna.organise.edit-question-tooltip")));
							
						if (qnaQuestion.getHidden()) {
							UILink.make(questionContainer, "hide-question-icon", LIGHTBULB_OFF_ICON_URL);
							UIInternalLink showQuestion = UIInternalLink.make(questionContainer, "hide-question-link", new OrganiseParams(OrganiseListProducer.VIEW_ID, "que", qnaQuestion.getId().toString(), true));
							showQuestion.decorators = new DecoratorList(new UITooltipDecorator(messageLocator.getMessage("qna.organise.show-question-tooltip")));
						} else {
							UILink.make(questionContainer, "hide-question-icon", LIGHTBULB_ON_ICON_URL);
							UIInternalLink hideQuestion = UIInternalLink.make(questionContainer, "hide-question-link", new OrganiseParams(OrganiseListProducer.VIEW_ID, "que", qnaQuestion.getId().toString(), false));
							hideQuestion.decorators = new DecoratorList(new UITooltipDecorator(messageLocator.getMessage("qna.organise.hide-question-tooltip")));
						}

						UILink.make(questionContainer, "delete-question-icon", DELETE_ICON_URL);
						UIInternalLink deleteQuestion = UIInternalLink.make(questionContainer, "delete-question-link", new QuestionParams(DeleteQuestionProducer.VIEW_ID, qnaQuestion.getId().toString(),VIEW_ID));
						deleteQuestion.decorators =  new DecoratorList(new UITooltipDecorator(messageLocator.getMessage("qna.organise.delete-question-tooltip")));
					}
					
					UISelectChoice.make(questionContainer, "question-sort-order-checkbox", queorder.getFullID(), queorderlist.size());
					queorderlist.add(qnaQuestion.getId().toString());
					UISelectChoice.make(questionContainer, "question-category-order-checkbox", questionCategoryOrder.getFullID(), questionCategoryOrderList.size());
					questionCategoryOrderList.add(qnaQuestion.getCategory().getId());
				}
			}
		}
		
		catorder.optionlist.setValue(catorderlist.toStringArray());
		queorder.optionlist.setValue(queorderlist.toStringArray());
		questionCategoryOrder.optionlist.setValue(questionCategoryOrderList.toStringArray());

		UICommand.make(form, "save-button", UIMessage.make("qna.general.save"), "#{OrganiserHelper.saveOrder}");
		UICommand.make(form, "cancel-button", UIMessage.make("qna.general.cancel")).setReturn("cancel");
		
	}

	public List<NavigationCase> reportNavigationCases() {
		List<NavigationCase> togo = new ArrayList<NavigationCase>();
		togo.add(new NavigationCase("cancel", new SimpleViewParameters(QuestionsListProducer.VIEW_ID)));
		togo.add(new NavigationCase("saved", new SimpleViewParameters(QuestionsListProducer.VIEW_ID)));
		return togo;
	}

	public ViewParameters getViewParameters() {
		return new OrganiseParams();
	}

}
