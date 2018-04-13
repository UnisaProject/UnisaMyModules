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
/**
 * Shamelessly stolen from Blog WOW
 */
package org.sakaiproject.qna.tool.producers.renderers;

import org.sakaiproject.qna.logic.ExternalLogic;
import org.sakaiproject.qna.logic.PermissionLogic;
import org.sakaiproject.qna.tool.producers.OptionsProducer;
import org.sakaiproject.qna.tool.producers.OrganiseListProducer;
import org.sakaiproject.qna.tool.producers.PermissionsProducer;
import org.sakaiproject.site.api.SiteService;

import uk.org.ponder.rsf.components.UIBranchContainer;
import uk.org.ponder.rsf.components.UIContainer;
import uk.org.ponder.rsf.components.UIInternalLink;
import uk.org.ponder.rsf.components.UIJointContainer;
import uk.org.ponder.rsf.components.UIMessage;
import uk.org.ponder.rsf.components.decorators.UIStyleDecorator;
import uk.org.ponder.rsf.viewstate.SimpleViewParameters;


public class NavBarRenderer {

	private PermissionLogic permissionLogic;
	private ExternalLogic externalLogic;

	public void setPermissionLogic(PermissionLogic permissionLogic) {
		this.permissionLogic = permissionLogic;
	}
	public void setExternalLogic(ExternalLogic externalLogic) {
		this.externalLogic = externalLogic;
	}
	
	/**
	 * Renders navigation bar
	 * 
	 * @param tofill		{@link UIContainer} to fill
	 * @param divID  		ID of div
	 * @param currentViewID	View ID currently being viewed
	 */
	public void makeNavBar(UIContainer tofill, String divID, String currentViewID) {
    	// Front-end customization for navigation bar regarding permissions will come here
		
		// Only display navbar for users with update rights
		if (permissionLogic.canUpdate(externalLogic.getCurrentLocationId(), externalLogic.getCurrentUserId())) {
	    	UIJointContainer joint = new UIJointContainer(tofill,divID,"qna-navigation:");

	    	if (permissionLogic.canUpdate(externalLogic.getCurrentLocationId(), externalLogic.getCurrentUserId())) {
		    	renderLink(joint, "4", "qna.navbar.organise", OrganiseListProducer.VIEW_ID, currentViewID);
		    	renderLink(joint, "5", "qna.navbar.options", OptionsProducer.VIEW_ID, currentViewID);
	    	}

	    	if (externalLogic.isUserAllowedInLocation(externalLogic.getCurrentUserId(), SiteService.SECURE_UPDATE_SITE, externalLogic.getCurrentLocationId())) {
		    	renderLink(joint, "6", "qna.navbar.permissions", PermissionsProducer.VIEW_ID, currentViewID);
	    	}	
		}
    }
	
	/**
	 * Renders a Nav Link
	 * 
	 * @param joint
	 * @param localId
	 * @param messageKey
	 * @param linkViewID
	 * @param currentViewID
	 */
	private void renderLink(UIJointContainer joint, String localId, String messageKey, String linkViewID, String currentViewID) {

		UIBranchContainer cell = UIBranchContainer.make(joint, "navigation-cell:", localId);
		UIInternalLink link = UIInternalLink.make(cell, "item-link", UIMessage.make(messageKey), new SimpleViewParameters(linkViewID));

		if (currentViewID != null && currentViewID.equals(linkViewID)) {
			link.decorate( new UIStyleDecorator("inactive"));
		}
	}
}
