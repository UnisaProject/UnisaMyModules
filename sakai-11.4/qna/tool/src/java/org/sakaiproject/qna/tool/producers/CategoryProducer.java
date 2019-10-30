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
import org.sakaiproject.qna.logic.PermissionLogic;
import org.sakaiproject.qna.tool.otp.CategoryLocator;
import org.sakaiproject.qna.tool.params.CategoryParams;
import org.sakaiproject.qna.tool.params.SimpleReturnToParams;
import org.sakaiproject.qna.tool.producers.renderers.NavBarRenderer;
import org.sakaiproject.qna.tool.producers.renderers.SearchBarRenderer;

import uk.org.ponder.messageutil.TargettedMessageList;
import uk.org.ponder.rsf.components.UICommand;
import uk.org.ponder.rsf.components.UIContainer;
import uk.org.ponder.rsf.components.UIForm;
import uk.org.ponder.rsf.components.UIInput;
import uk.org.ponder.rsf.components.UIMessage;
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

public class CategoryProducer implements ViewComponentProducer, NavigationCaseReporter, ViewParamsReporter, ActionResultInterceptor {

    public static final String VIEW_ID = "categories";
    private NavBarRenderer navBarRenderer;
    private SearchBarRenderer searchBarRenderer;
    private PermissionLogic permissionLogic;
    private ExternalLogic externalLogic;
    private TargettedMessageList messages;
    
	public String getViewID() {
        return VIEW_ID;
    }

    public void setNavBarRenderer(NavBarRenderer navBarRenderer) {
		this.navBarRenderer = navBarRenderer;
	}

    public void setSearchBarRenderer(SearchBarRenderer searchBarRenderer) {
    	this.searchBarRenderer = searchBarRenderer;
    }

    public void setPermissionLogic(PermissionLogic permissionLogic) {
    	this.permissionLogic = permissionLogic;
    }

    public void setExternalLogic(ExternalLogic externalLogic) {
    	this.externalLogic = externalLogic;
    }

    /**
     * @see ComponentProducer#fillComponents(UIContainer, ViewParameters, ComponentChecker)
     */
	public void fillComponents(UIContainer tofill, ViewParameters viewparams, ComponentChecker checker) {
		String categoryLocator = "CategoryLocator";
		String categoryOTP = null;

		CategoryParams params = (CategoryParams)viewparams;

		boolean newentry = false;
        if (params.id == null) {
        	categoryOTP = categoryLocator+"."+CategoryLocator.NEW_1;
            newentry = true;
        } else {
        	categoryOTP = categoryLocator+"."+params.id;
        }

		// Front-end customization regarding permissions/options will come here
        navBarRenderer.makeNavBar(tofill, "navIntraTool:", VIEW_ID);
        searchBarRenderer.makeSearchBar(tofill, "searchTool:", VIEW_ID);

        if (newentry) {
        	UIMessage.make(tofill, "page-title", "qna.category.create-title");
        	UIMessage.make(tofill, "category-note", "qna.category.create-note");
        } else {
        	UIMessage.make(tofill, "page-title", "qna.category.edit-title");
        	UIMessage.make(tofill, "category-note", "qna.category.edit-note");
        }

        UIForm form = UIForm.make(tofill, "category-form");
        UIMessage.make(form, "category-label", "qna.category.category");

        UIInput.make(form, "category-name", categoryOTP+".categoryText");
        if (newentry) {
        	UICommand.make(form, "save-button", UIMessage.make("qna.general.save"), categoryLocator+".save");
        } else if (permissionLogic.canUpdate(externalLogic.getCurrentLocationId(), externalLogic.getCurrentUserId())) {
        	UICommand.make(form, "save-button", UIMessage.make("qna.general.save"), categoryLocator+".edit");
        	UICommand.make(form, "remove-button", UIMessage.make("qna.general.delete"), categoryLocator+".remove");
        }
        UICommand.make(form,"cancel-button",UIMessage.make("qna.general.cancel")).setReturn("cancel");

	}

	public List<NavigationCase> reportNavigationCases() {
		  List<NavigationCase> l = new ArrayList<NavigationCase>();
		  l.add(new NavigationCase("cancel", new SimpleViewParameters(QuestionsListProducer.VIEW_ID)));
		  l.add(new NavigationCase("saved", new SimpleViewParameters(QuestionsListProducer.VIEW_ID)));
		  l.add(new NavigationCase("edited", new SimpleViewParameters(QuestionsListProducer.VIEW_ID)));
		  l.add(new NavigationCase("removed", new SimpleViewParameters(QuestionsListProducer.VIEW_ID)));
		  return l;
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

	public void setMessages(TargettedMessageList messages) {
		this.messages = messages;
	}
}
