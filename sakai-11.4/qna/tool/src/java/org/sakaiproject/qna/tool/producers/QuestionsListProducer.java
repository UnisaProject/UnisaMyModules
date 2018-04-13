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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.sakaiproject.qna.logic.ExternalLogic;
import org.sakaiproject.qna.logic.OptionsLogic;
import org.sakaiproject.qna.logic.PermissionLogic;
import org.sakaiproject.qna.tool.constants.SortByConstants;
import org.sakaiproject.qna.tool.constants.ViewTypeConstants;
import org.sakaiproject.qna.tool.otp.DeleteMultiplesHelper;
import org.sakaiproject.qna.tool.params.CategoryParams;
import org.sakaiproject.qna.tool.params.MultipleDeletesParams;
import org.sakaiproject.qna.tool.params.QuestionParams;
import org.sakaiproject.qna.tool.params.SortPagerViewParams;
import org.sakaiproject.qna.tool.producers.renderers.CategoryQuestionListRenderer;
import org.sakaiproject.qna.tool.producers.renderers.DetailedQuestionListRenderer;
import org.sakaiproject.qna.tool.producers.renderers.NavBarRenderer;
import org.sakaiproject.qna.tool.producers.renderers.QuestionListRenderer;
import org.sakaiproject.qna.tool.producers.renderers.SearchBarRenderer;
import org.sakaiproject.qna.tool.producers.renderers.StandardQuestionListRenderer;
import org.sakaiproject.qna.tool.utils.ViewHelper;

import uk.org.ponder.beanutil.BeanGetter;
import uk.org.ponder.messageutil.MessageLocator;
import uk.org.ponder.messageutil.TargettedMessage;
import uk.org.ponder.messageutil.TargettedMessageList;
import uk.org.ponder.rsf.components.UICommand;
import uk.org.ponder.rsf.components.UIContainer;
import uk.org.ponder.rsf.components.UIForm;
import uk.org.ponder.rsf.components.UIInitBlock;
import uk.org.ponder.rsf.components.UIInternalLink;
import uk.org.ponder.rsf.components.UILink;
import uk.org.ponder.rsf.components.UIMessage;
import uk.org.ponder.rsf.components.UIOutput;
import uk.org.ponder.rsf.components.UISelect;
import uk.org.ponder.rsf.flow.ARIResult;
import uk.org.ponder.rsf.flow.ActionResultInterceptor;
import uk.org.ponder.rsf.flow.jsfnav.NavigationCase;
import uk.org.ponder.rsf.flow.jsfnav.NavigationCaseReporter;
import uk.org.ponder.rsf.view.ComponentChecker;
import uk.org.ponder.rsf.view.ComponentProducer;
import uk.org.ponder.rsf.view.DefaultView;
import uk.org.ponder.rsf.view.ViewComponentProducer;
import uk.org.ponder.rsf.viewstate.SimpleViewParameters;
import uk.org.ponder.rsf.viewstate.ViewParameters;
import uk.org.ponder.rsf.viewstate.ViewParamsReporter;

public class QuestionsListProducer implements DefaultView, ViewComponentProducer, NavigationCaseReporter, ViewParamsReporter, ActionResultInterceptor {

    public static final String VIEW_ID = "questions_list";
    
    private static final String ADD_ICON_URL = "/library/image/silk/add.png";
    
    private static Log log = LogFactory.getLog(QuestionsListProducer.class);
    
    private NavBarRenderer navBarRenderer;
    private SearchBarRenderer searchBarRenderer;
    private CategoryQuestionListRenderer categoryQuestionListRenderer;
    private DetailedQuestionListRenderer detailedQuestionListRenderer;
    private StandardQuestionListRenderer standardQuestionListRenderer;
    private MessageLocator messageLocator;
    private ExternalLogic externalLogic;
    private PermissionLogic permissionLogic;
    private OptionsLogic optionsLogic;
    private BeanGetter ELEvaluator;
    private ViewHelper viewHelper;
   

    public String getViewID() {
        return VIEW_ID;
    }

	public void setMessageLocator(MessageLocator messageLocator) {
		this.messageLocator = messageLocator;
	}

	public void setNavBarRenderer(NavBarRenderer navBarRenderer) {
		this.navBarRenderer = navBarRenderer;
	}
	public void setSearchBarRenderer(SearchBarRenderer searchBarRenderer) {
		this.searchBarRenderer = searchBarRenderer;
	}

	public void setCategoryQuestionListRenderer(CategoryQuestionListRenderer categoryQuestionListRenderer) {
		this.categoryQuestionListRenderer = categoryQuestionListRenderer;
	}

	public void setDetailedQuestionListRenderer(
			DetailedQuestionListRenderer detailedQuestionListRenderer) {
		this.detailedQuestionListRenderer = detailedQuestionListRenderer;
	}

	public void setStandardQuestionListRenderer(
			StandardQuestionListRenderer standardQuestionListRenderer) {
		this.standardQuestionListRenderer = standardQuestionListRenderer;
	}

    public void setExternalLogic(ExternalLogic externalLogic) {
        this.externalLogic = externalLogic;
    }

    public void setPermissionLogic(PermissionLogic permissionLogic) {
		this.permissionLogic = permissionLogic;
    }

    public void setOptionsLogic(OptionsLogic optionsLogic) {
    	this.optionsLogic = optionsLogic;
    }

    public void setELEvaluator(BeanGetter ELEvaluator) {
        this.ELEvaluator = ELEvaluator;
    }

    public void setViewHelper(ViewHelper viewHelper) {
    	this.viewHelper = viewHelper;
    }
    
    private TargettedMessageList targettedMessageList;	
	public void setTargettedMessageList(TargettedMessageList targettedMessageList) {
		this.targettedMessageList = targettedMessageList;
	}
    
     /**
     * @see ComponentProducer#fillComponents(UIContainer, ViewParameters, ComponentChecker)
     */
	public void fillComponents(UIContainer tofill, ViewParameters viewparams, ComponentChecker checker) {
		log.debug("fillComponents");
		navBarRenderer.makeNavBar(tofill, "navIntraTool:", VIEW_ID);
		searchBarRenderer.makeSearchBar(tofill, "searchTool", VIEW_ID);

		// Depending on default or one selected view type, send through parameter

		SortPagerViewParams params = (SortPagerViewParams) viewparams;
		QuestionListRenderer renderer = getRenderer(params);

		UIMessage.make(tofill, "page-title", "qna.view-questions.title");

		UIForm form = UIForm.make(tofill, "view-questions-form");
		UIMessage.make(form, "view-title", "qna.view-questions.view-title");

		String[] options;
		String[] labels;

		if (permissionLogic.canUpdate(externalLogic.getCurrentLocationId(), externalLogic.getCurrentUserId())) {
			// For users with update permissions
			options = new String[] {ViewTypeConstants.CATEGORIES,
									ViewTypeConstants.ALL_DETAILS};
			labels  = new String[] {messageLocator.getMessage("qna.view-questions.categories"),
			    	   			    messageLocator.getMessage("qna.view-questions.all-details")};

			if (!params.viewtype.equals(ViewTypeConstants.ALL_DETAILS)) {
				UICommand.make(form, "update-button", UIMessage.make("qna.general.update")).setReturn("deletes");
			} else {
				UICommand.make(form, "questions-delete-button", UIMessage.make("qna.view-question.delete-questions")).setReturn("deleteQ");
			}
			//UICommand.make(form, "update-button", "#{QuestionLocator.deleteQuestions}");

		} else {
			options = new String[] {ViewTypeConstants.CATEGORIES,
									SortByConstants.VIEWS,
									SortByConstants.MODIFIED,
									SortByConstants.CREATED};
			labels  = new String[] {messageLocator.getMessage("qna.view-questions.categories"),
									messageLocator.getMessage("qna.view-questions.most-popular"),
						 		   	messageLocator.getMessage("qna.view-questions.recent-changes"),
						 		   	messageLocator.getMessage("qna.view-questions.recent-questions")};
		}
		
		
		boolean addSpacing = false;
		if (permissionLogic.canAddNewQuestion(externalLogic.getCurrentLocationId(), externalLogic.getCurrentUserId())) {
			UIOutput.make(tofill,"ask-question");
			UILink.make(tofill, "ask-question-icon", ADD_ICON_URL);

			if (optionsLogic.getOptionsForLocation(externalLogic.getCurrentLocationId()).getAnonymousAllowed()) {
				UIInternalLink.make(tofill, "ask-question-link", UIMessage.make("qna.view-questions.ask-question-anonymously"), new SimpleViewParameters(AskQuestionProducer.VIEW_ID));
			} else {
				UIInternalLink.make(tofill, "ask-question-link", UIMessage.make("qna.view-questions.ask-question"), new SimpleViewParameters(AskQuestionProducer.VIEW_ID));
			}
			addSpacing = true;
		}
		
		if (permissionLogic.canAddNewCategory(externalLogic.getCurrentLocationId(), externalLogic.getCurrentUserId()) &&
			permissionLogic.canUpdate(externalLogic.getCurrentLocationId(), externalLogic.getCurrentUserId())) {
			UIOutput.make(tofill,"add-category");
			UILink.make(tofill, "add-category-icon", ADD_ICON_URL);
			UIInternalLink.make(tofill, "add-category-link", UIMessage.make("qna.view-questions.add-category"), new SimpleViewParameters(CategoryProducer.VIEW_ID));
			addSpacing = true;
		}
		
		if (addSpacing) {
			UIOutput.make(tofill,"spacing");
		}

		// Init value must be either default or specified
		String currentSelected;
		if (params.viewtype.equals(ViewTypeConstants.CATEGORIES) || params.viewtype.equals(ViewTypeConstants.ALL_DETAILS)) {
			currentSelected = params.viewtype;
		} else {
			currentSelected = params.sortBy;
		}

		UISelect select = UISelect.make(form, "view-select", options, labels, null, currentSelected);
		UIInitBlock.make(form, "view-select-init", "init_view_select", new Object[] {(select.getFullID() + "-selection"),form,options.length,currentSelected});
		
		//check if the user has permission
		if (!permissionLogic.canRead(externalLogic.getCurrentLocationId(), externalLogic.getCurrentUserId())) {
			targettedMessageList.addMessage(new TargettedMessage("qna.warning.no-permission", new Object[]{}, TargettedMessage.SEVERITY_ERROR));
			return;
		}
		
		renderer.makeQuestionList(tofill, "questionListTool:", params, form);
    }

	public List<NavigationCase> reportNavigationCases() {
		List<NavigationCase> list = new ArrayList<NavigationCase>();
		list.add(new NavigationCase("update", new SimpleViewParameters(QuestionsListProducer.VIEW_ID)));
		list.add(new NavigationCase("deleteQ", new QuestionParams(DeleteQuestionsProducer.VIEW_ID)));
		list.add(new NavigationCase("deleteC", new CategoryParams(DeleteCategoriesProducer.VIEW_ID)));
		list.add(new NavigationCase("deletes", new MultipleDeletesParams(MultipleDeletesProducer.VIEW_ID)));
		return list;
	}

	public ViewParameters getViewParameters() {
		return new SortPagerViewParams();
	}

	public void interceptActionResult(ARIResult result, ViewParameters incoming, Object actionReturn) {
		DeleteMultiplesHelper dmh = (DeleteMultiplesHelper)ELEvaluator.getBean("DeleteMultiplesHelper");

		if ((dmh.getQuestionids() == null) && (dmh.getCategoryids() == null)) {
			result.resultingView = new SimpleViewParameters(QuestionsListProducer.VIEW_ID);
		} else if (result.resultingView instanceof QuestionParams) {
			if (dmh.getQuestionids() == null) {
				result.resultingView = new SimpleViewParameters(QuestionsListProducer.VIEW_ID);
			} else {
				QuestionParams params = (QuestionParams)result.resultingView;
				params.questionids = dmh.getQuestionids();
			}
		} else if (result.resultingView instanceof CategoryParams) {
			if (dmh.getCategoryids() == null) {
				result.resultingView = new SimpleViewParameters(QuestionsListProducer.VIEW_ID);
			} else {
				CategoryParams params = (CategoryParams)result.resultingView;
				params.categoryids = dmh.getCategoryids();
			}
		} else if (result.resultingView instanceof MultipleDeletesParams) {
			MultipleDeletesParams params = (MultipleDeletesParams)result.resultingView;
			params.categoryids = dmh.getCategoryids();
			params.questionids = dmh.getQuestionids();
		}

	}

	/**
	 * Determine which renderer to use (as well as update the view type / session)
	 * @param params View parameters
	 * @return {@link QuestionListRenderer} to use
	 */
	private QuestionListRenderer getRenderer(SortPagerViewParams params) {
		QuestionListRenderer renderer = null;

		if (params.viewtype != null) { // Type has been selected
			if (params.viewtype.equals(ViewTypeConstants.CATEGORIES)) {
				renderer = categoryQuestionListRenderer;
			} else if (params.viewtype.equals(ViewTypeConstants.ALL_DETAILS)) {
				renderer = detailedQuestionListRenderer;
				if (params.sortBy == null) {
					params.sortBy = SortByConstants.VIEWS; // default
				}
				if (params.sortDir == null) {
					params.sortDir = SortByConstants.SORT_DIR_ASC; // default
				}
			} else {
				renderer = standardQuestionListRenderer; // Just make default standard list for now
			}
			viewHelper.setupSession(params.viewtype,params.sortBy, params.sortDir);
		} else {
			String view = viewHelper.getViewType();
			String sortBy = viewHelper.getSortBy();
			String sortDir = viewHelper.getSortDir();

			renderer = getRendererForViewType(view);
			params.viewtype = view;
			params.sortBy = sortBy;
			params.sortDir = sortDir;
		}

		return renderer;
	}

	/**
	 * Get renderer for view type specified 
	 * 
	 * @param view type of view {@link ViewTypeConstants}
	 * @return {@link QuestionListRenderer} to use
	 */
	private QuestionListRenderer getRendererForViewType(String view) {
		if (ViewTypeConstants.isValid(view)) {
			if (view.equals(ViewTypeConstants.ALL_DETAILS)) {
				return detailedQuestionListRenderer;
			} else if (view.equals(ViewTypeConstants.CATEGORIES)) {
				return categoryQuestionListRenderer;
			} else {
				return standardQuestionListRenderer;
			}
		}
		return null;
	}

}
