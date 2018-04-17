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
package org.sakaiproject.qna.tool.producers.renderers;

import org.sakaiproject.qna.tool.params.SearchParams;
import org.sakaiproject.qna.tool.producers.SearchResultsProducer;

import uk.org.ponder.rsf.components.UIBranchContainer;
import uk.org.ponder.rsf.components.UICommand;
import uk.org.ponder.rsf.components.UIContainer;
import uk.org.ponder.rsf.components.UIForm;
import uk.org.ponder.rsf.components.UIInput;
import uk.org.ponder.rsf.components.UIJointContainer;
import uk.org.ponder.rsf.components.UILink;
import uk.org.ponder.rsf.components.UIMessage;
import uk.org.ponder.rsf.components.UIOutput;


public class SearchBarRenderer {

	/**
	 * Renders search bar
	 * 
	 * @param tofill			{@link UIContainer} to fill
	 * @param divID				id of div
	 * @param currentViewID		current view id
	 */
    public void makeSearchBar(UIContainer tofill, String divID, String currentViewID) {

    	UIJointContainer joint = new UIJointContainer(tofill, divID, "qna-search:");

    	UIBranchContainer cell1 = UIBranchContainer.make(joint, "search-cell:", "1");

    	UIForm form = UIForm.make(cell1, "search-form", new SearchParams(SearchResultsProducer.VIEW_ID));

		UIInput.make(form, "search-value", "#{search}");
		
		//UIInternalLink.make(form, "search-link", UIMessage.make("qna.general.search"), new SimpleViewParameters(SearchResultsProducer.VIEW_ID));
		UICommand.make(form, "search-button", UIMessage.make("qna.general.search"));

		UIOutput.make(cell1, "item-separator");

        UILink.make(cell1, "print-icon", "/library/image/silk/printer.png");
    }
}
