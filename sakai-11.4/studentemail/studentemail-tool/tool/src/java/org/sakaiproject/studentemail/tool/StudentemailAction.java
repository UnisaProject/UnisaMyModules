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

package org.sakaiproject.studentemail.tool;

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
import org.sakaiproject.studentemail.cover.StudentemailService;
import org.sakaiproject.util.ResourceLoader;
import org.sakaiproject.tool.api.Placement;
import org.sakaiproject.tool.api.Session;
import org.sakaiproject.tool.cover.ToolManager;
import org.sakaiproject.user.api.User;
import org.sakaiproject.site.api.Site;
import org.sakaiproject.site.cover.SiteService;
import org.sakaiproject.exception.PermissionException;
import org.sakaiproject.event.api.EventTrackingService;
import org.sakaiproject.tool.cover.SessionManager;
import org.sakaiproject.user.cover.UserDirectoryService;
import org.sakaiproject.user.api.UserNotDefinedException;

@SuppressWarnings("deprecation")
public class StudentemailAction extends VelocityPortletPaneledAction {
	private static final long serialVersionUID = 1L;
	private static Logger M_log = LoggerFactory.getLogger(StudentemailAction.class);
	@Getter
	@Setter
	private boolean maintainUser;
	private String siteId = null;
	private String siteReference = null;
	private EventTrackingService eventTrackingService = null;

	/** Resource bundle using current language locale */
	private static ResourceLoader rb = new ResourceLoader("admin");
	protected static final String STATE_DISPLAY_MODE = "display_mode";
	protected static final String MODE_EDIT = "edit";

	/**
	 * @param portlet
	 * @param context
	 * @param rundata
	 * @param state
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String buildMainPanelContext(VelocityPortlet portlet, Context context, RunData rundata, SessionState state)
			throws Exception {
		context.put("tlang", rb);
		String siteReference = null;
		siteId = ToolManager.getCurrentPlacement().getContext();
		// put $action into context for menus, forms and links
		context.put(Menu.CONTEXT_ACTION, state.getAttribute(STATE_ACTION));
		String template = (String) getContext(rundata).get("template");

		User user = null;

		String userId = SessionManager.getCurrentSessionUserId();
		if (userId != null) {
			try {
				user = UserDirectoryService.getUser(userId);
			} catch (UserNotDefinedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		String studentNr = user.getEid();
		if ("Student".equalsIgnoreCase(user.getType())) {
			Boolean contactEmail = StudentemailService.checkEmail(siteId);
			context.put("userType", "Student");
			context.put("studentNr", studentNr);
			context.put("studentName", user.getDisplayName());
			context.put("studentEmail", studentNr + "@mylife.unisa.ac.za");
			context.put("contactEmail", contactEmail);
			return template + "displaystudent";

		} else if ("Instructor".equalsIgnoreCase(user.getType()) || "admin".equalsIgnoreCase(user.getType())) {
			context.put("userType", "Instructor");
			try {
				if (StudentemailService.checkEmail(siteId)) {
					try {
						String tempemail = StudentemailService.getEmail(siteId);
						if ((siteId + "@unisa.ac.za").equals(tempemail)) {
							context.put("choice", "2");
						} else {
							context.put("choice", "3");
							context.put("coursecontactEmail", tempemail);
						}
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				} else {
					context.put("choice", "1");
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			return template + "displayLecturer";
		} else {
			throw new Exception("Payment : User type unknown");
		}

	}

}
