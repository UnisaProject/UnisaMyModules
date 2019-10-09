/**********************************************************************************
 * $URL$
 * $Id$
 ***********************************************************************************
 *
 * Copyright (c) 2003, 2004, 2005, 2006, 2008 The Sakai Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.opensource.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 **********************************************************************************/

package org.sakaiproject.template.tool;

import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sakaiproject.authz.cover.SecurityService;
import org.sakaiproject.cheftool.Context;
import org.sakaiproject.cheftool.JetspeedRunData;
import org.sakaiproject.cheftool.RunData;
import org.sakaiproject.cheftool.VelocityPortlet;
import org.sakaiproject.cheftool.VelocityPortletPaneledAction;
import org.sakaiproject.cheftool.api.Menu;
import org.sakaiproject.cheftool.menu.MenuDivider;
import org.sakaiproject.cheftool.menu.MenuEntry;
import org.sakaiproject.cheftool.menu.MenuImpl;
import org.sakaiproject.component.cover.ComponentManager;
import org.sakaiproject.event.api.SessionState;
import org.sakaiproject.event.api.UsageSession;
import org.sakaiproject.event.cover.UsageSessionService;
import org.sakaiproject.template.cover.TemplateService;
import org.sakaiproject.util.ResourceLoader;

/**
 * <p>
 * TemplateToolAction is the template display tool
 * </p>
 */
public class TemplateToolAction extends VelocityPortletPaneledAction {
	private static final long serialVersionUID = 1L;
	private static Logger M_log = LoggerFactory.getLogger(TemplateToolAction.class);

	/** Resource bundle using current language locale */
	private static ResourceLoader rb = new ResourceLoader("admin");

	/**
	 * Populate the state object, if needed.
	 */
	protected void initState(SessionState state, VelocityPortlet portlet, JetspeedRunData rundata) {

	}

	/**
	 * @param portlet
	 * @param context
	 * @param rundata
	 * @param state
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String buildMainPanelContext(VelocityPortlet portlet, Context context, RunData rundata, SessionState state) {
		context.put("tlang", rb);

	/*	// if not logged in as the super user, we won't do anything
		if (!SecurityService.isSuperUser()) {
			return (String) getContext(rundata).get("template") + "_noaccess";
		}
    */
		String template = (String) getContext(rundata).get("template");
		M_log.info(this + "template name " + template);

		String name = TemplateService.getCurrentUserName();
		M_log.info(this + " getCurrentUserName name " + name);
		System.out.println("current user name " + name);
		context.put("name", name);
		String eid = null;
		String userId = TemplateService.getSakaiUserId(eid);
		M_log.info(this + " getSakaiUserId userId " + userId);
		System.out.println("current userId " + userId);
		context.put("userId", userId);

		return template;

	}

}
