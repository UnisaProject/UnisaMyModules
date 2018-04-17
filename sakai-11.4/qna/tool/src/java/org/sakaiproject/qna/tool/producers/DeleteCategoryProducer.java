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
import org.sakaiproject.qna.model.QnaCategory;
import org.sakaiproject.qna.model.QnaQuestion;
import org.sakaiproject.qna.tool.params.CategoryParams;
import org.sakaiproject.qna.tool.params.SimpleReturnToParams;
import org.sakaiproject.qna.tool.producers.renderers.NavBarRenderer;
import org.sakaiproject.qna.tool.utils.DateUtil;
import org.sakaiproject.qna.utils.TextUtil;

import uk.org.ponder.rsf.components.UIBranchContainer;
import uk.org.ponder.rsf.components.UICommand;
import uk.org.ponder.rsf.components.UIContainer;
import uk.org.ponder.rsf.components.UIELBinding;
import uk.org.ponder.rsf.components.UIForm;
import uk.org.ponder.rsf.components.UIJointContainer;
import uk.org.ponder.rsf.components.UIMessage;
import uk.org.ponder.rsf.components.UIOutput;
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

public class DeleteCategoryProducer implements ViewComponentProducer, NavigationCaseReporter, ViewParamsReporter, ActionResultInterceptor {

	public static final String VIEW_ID = "delete_category";
	private NavBarRenderer navBarRenderer;
	private CategoryLogic categoryLogic;

	public String getViewID() {
		return VIEW_ID;
	}

	public void setNavBarRenderer(NavBarRenderer navBarRenderer) {
		this.navBarRenderer = navBarRenderer;
	}
	public void setCategoryLogic(CategoryLogic categoryLogic) {
		this.categoryLogic = categoryLogic;
	}

    /**
     * @see ComponentProducer#fillComponents(UIContainer, ViewParameters, ComponentChecker)
     */
	public void fillComponents(UIContainer tofill, ViewParameters viewparams, ComponentChecker checker) {
		CategoryParams params = (CategoryParams)viewparams;

		navBarRenderer.makeNavBar(tofill, "navIntraTool:", VIEW_ID);

		String categoryLocator = "CategoryLocator";

		UIForm form = UIForm.make(tofill, "delete-categories-form");

		UIJointContainer listTable = new UIJointContainer(form, "category-list-table", "category-list-table:");

		UIBranchContainer categoryHeadings = UIBranchContainer.make(listTable, "category-headings:");

		UIMessage.make(categoryHeadings, "dcs-name", "qna.delete-category.name-title");
		UIMessage.make(categoryHeadings, "dcs-questions", "qna.delete-category.question-title");
		UIMessage.make(categoryHeadings, "dcs-answers", "qna.delete-category.answers-title");
		UIMessage.make(categoryHeadings, "dcs-modified", "qna.delete-category.modified-title");

		UIBranchContainer categoryContainer = UIBranchContainer.make(listTable, "category-entry:");

		String categoryOTP = categoryLocator+"."+params.id;

//		form.parameters.add(new UIDeletionBinding(categoryOTP));

		QnaCategory category = categoryLogic.getCategoryById(params.id);
		List<QnaQuestion> questionList = category.getQuestions();


		// Generate warning for associated answers
		if (questionList.size() > 0) {
			UIMessage.make(tofill, "error-message1", "qna.warning.qna-associated");
		}

		int answerTotal = 0;
		// Count the total number of answers
		for (QnaQuestion question: questionList) {
			answerTotal += question.getAnswers().size();
		}

		// Generate confirmation warning for the delete action
		UIMessage.make(tofill, "error-message3", "qna.warning.delete-confirmation-note");

		// Generate the page title
		UIMessage.make(tofill, "page-title", "qna.general.delete-confirmation");

		UIOutput.make(categoryContainer, "name", TextUtil.stripTags(category.getCategoryText()));
		UIOutput.make(categoryContainer, "questions", questionList.size()+"");
		UIOutput.make(categoryContainer, "answers", answerTotal+"");
		UIOutput.make(categoryContainer, "modified", DateUtil.getSimpleDate(category.getDateLastModified()));

		UICommand delete = UICommand.make(form, "delete-button", UIMessage.make("qna.general.delete"), categoryLocator + ".delete");
		delete.addParameter(new UIELBinding(categoryOTP+".id", category.getId()));
		UICommand.make(form, "cancel-button", UIMessage.make("qna.general.cancel")).setReturn("cancel");
	}

	public List<NavigationCase> reportNavigationCases() {
		List<NavigationCase> list = new ArrayList<NavigationCase>();
		list.add(new NavigationCase("delete", new CategoryParams(QuestionsListProducer.VIEW_ID)));
		list.add(new NavigationCase("cancel", new SimpleViewParameters(QuestionsListProducer.VIEW_ID)));
		return list;
	}

	public ViewParameters getViewParameters() {
		return new CategoryParams();
	}
	
	public void interceptActionResult(ARIResult result,
			ViewParameters incoming, Object actionReturn) {
		if (incoming instanceof SimpleReturnToParams) {
			if (((SimpleReturnToParams)incoming).returnToViewID != null) {
				((SimpleViewParameters)result.resultingView).viewID = ((SimpleReturnToParams)incoming).returnToViewID;
			}
		}
	}
	
}