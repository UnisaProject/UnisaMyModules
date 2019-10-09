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

package org.sakaiproject.welcome.tool;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lombok.Getter;
import lombok.Setter;

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
import org.sakaiproject.exception.IdUnusedException;
import org.sakaiproject.welcome.cover.WelcomeService;
import org.sakaiproject.util.ResourceLoader;
import org.sakaiproject.tool.api.Placement;
import org.sakaiproject.tool.cover.ToolManager;
import org.sakaiproject.site.api.Site;
import org.sakaiproject.site.cover.SiteService;
import org.sakaiproject.exception.PermissionException;

@SuppressWarnings("deprecation")
public class WelcomeAction extends VelocityPortletPaneledAction {
	private static final long serialVersionUID = 1L;
	private static Logger M_log = LoggerFactory.getLogger(WelcomeAction.class);
	@Getter
	@Setter
	private boolean maintainUser;
	private String siteId = null;
	private String siteReference = null;

	/** Resource bundle using current language locale */
	private static ResourceLoader rb = new ResourceLoader("admin");
	protected static final String STATE_DISPLAY_MODE = "display_mode";
	protected static final String MODE_EDIT = "edit";

	/**
	 * Populate the state object, if needed.
	 */
/*	@SuppressWarnings("deprecation")
	protected void initState(SessionState state, VelocityPortlet portlet, JetspeedRunData rundata) {
		// get the current tool placement
		siteId = ToolManager.getCurrentPlacement().getContext();
	}*/

	/**
	 * @param portlet
	 * @param context
	 * @param rundata
	 * @param state
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String buildMainPanelContext(VelocityPortlet portlet, Context context, RunData rundata, SessionState state)
			throws PermissionException {
		context.put("tlang", rb);
		String siteReference = null;
		siteId = ToolManager.getCurrentPlacement().getContext();
		// put $action into context for menus, forms and links
		context.put(Menu.CONTEXT_ACTION, state.getAttribute(STATE_ACTION)); 

		try {
			siteReference = getSiteReference(siteId);
		} catch (IdUnusedException e) {
			context.put("nopermission", rb.getString("welcome.permissionerror"));
			return (String) getContext(rundata).get("template") + "_noaccess";
		}
		maintainUser = SecurityService.unlock("welcome.update", siteReference);
		context.put("maintainUser", maintainUser);

		String template = (String) getContext(rundata).get("template");
		String welcomeContent = WelcomeService.getWelcomeContent(siteId);
		context.put("welcomeContent", welcomeContent);
		Menu bar = new MenuImpl();
		bar.add(new MenuEntry(rb.getString("welcome.edit"), "editContent"));
		context.put(Menu.CONTEXT_MENU, bar);

		if (MODE_EDIT.equals(state.getAttribute(STATE_DISPLAY_MODE))) {
			template = template + "_edit";
		}

		return template;

	}

	public void editContent(RunData data, Context context) {

		String peid = ((JetspeedRunData) data).getJs_peid();
		SessionState state = ((JetspeedRunData) data).getPortletSessionState(peid);
		state.setAttribute(STATE_DISPLAY_MODE, MODE_EDIT);
	}

	public void doSubmit(RunData data, Context context) {
		String insertContent = null;
		siteId = ToolManager.getCurrentPlacement().getContext();
		SessionState state = ((JetspeedRunData) data).getPortletSessionState(((JetspeedRunData) data).getJs_peid());
		String content = data.getParameters().getString("content");
		
		int charcount = checkWelcomeContent(content);
		if (charcount > 0) {
			insertContent = WelcomeService.saveWelcomeContent(siteId, content);
			M_log.info(this + " Welcome message save: success for the site  " + siteId);
		} else {
			addAlert(state, rb.getString("welcome.alert.nocontent"));
			state.setAttribute(STATE_DISPLAY_MODE, MODE_EDIT);
			return;
		}
		if(insertContent==null) {
			addAlert(state, rb.getString("welcome.alert.insertfail"));
			M_log.error(this + " Welcome message save: failed for the site  " + siteId);
			state.setAttribute(STATE_DISPLAY_MODE, MODE_EDIT);
			return;
		}
		state.setAttribute(STATE_DISPLAY_MODE, null);
	}

	public void doCancel(RunData data, Context context) {
		SessionState state = ((JetspeedRunData) data).getPortletSessionState(((JetspeedRunData) data).getJs_peid());
		state.setAttribute(STATE_DISPLAY_MODE, null);
	}

	public void doRevert(RunData data, Context context) {
		siteId = ToolManager.getCurrentPlacement().getContext();
		SessionState state = ((JetspeedRunData) data).getPortletSessionState(((JetspeedRunData) data).getJs_peid());
		String revertContent = WelcomeService.revertWelcomeContent(siteId);
		M_log.info(this + " Welcome message revert: success for the site  " + siteId);
		state.setAttribute(STATE_DISPLAY_MODE, null);
	}

	public static final String getSiteReference(String siteId) throws IdUnusedException {	   
		return SiteService.getSite(siteId).getReference(); //returns /site/site_id
	}
	
	public int checkWelcomeContent(String welcomeContent) {
	int charcount = 0;
	String regex = "<[^>]*>";
	Pattern p2 = Pattern.compile(regex);
	Matcher m2 = p2.matcher(welcomeContent);
	//M_log.info(this + " String before sub :*" + welcomeContent + "*");
	welcomeContent = m2.replaceAll("");
	//M_log.info(this + " String after sub :*" + welcomeContent + "*");
	regex = "([A-Za-z])";
	Pattern p = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
	Matcher m = p.matcher(welcomeContent);
	while (m.find()) {
		charcount++;
	}
	return charcount;
	}

}
