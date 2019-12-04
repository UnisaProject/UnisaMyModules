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

package org.sakaiproject.prescribedbooks.tool;

import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.sakaiproject.prescribedbooks.dao.PrescribedbooksDAO;

import org.sakaiproject.cheftool.Context;
import org.sakaiproject.cheftool.JetspeedRunData;
import org.sakaiproject.cheftool.RunData;
import org.sakaiproject.cheftool.VelocityPortlet;
import org.sakaiproject.cheftool.VelocityPortletPaneledAction;
import org.sakaiproject.component.cover.ServerConfigurationService;
import org.sakaiproject.event.api.SessionState;
import org.sakaiproject.tool.cover.ToolManager;
import org.sakaiproject.tool.cover.SessionManager;
import org.sakaiproject.util.ResourceLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import za.ac.unisa.exceptions.NotACourseSiteException;
import za.ac.unisa.utils.CoursePeriod;
import za.ac.unisa.utils.CoursePeriodLookup;

/**
 * <p>
 * TemplateToolAction is the template display tool
 * </p>
 */
public class PrescribedBooksToolAction extends VelocityPortletPaneledAction {
	private static final long serialVersionUID = 1L;
	private static Logger M_log = LoggerFactory.getLogger(PrescribedBooksToolAction.class);
	private boolean preBooklistExist=false;
	private boolean recBooklistExist=false;
	private boolean eResBooklistExist=false;

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
		String template = (String) getContext(rundata).get("template");

		String site = ToolManager.getCurrentPlacement().getContext();
		context.put("siteTitle", site);
		String siteId = null;

		String userId = SessionManager.getCurrentSessionUserId();
		CoursePeriod coursePeriod;
		String subject = null;
		Integer year = null;
		try {
			coursePeriod = CoursePeriodLookup.getCoursePeriod();
			year = new Integer(coursePeriod.getYear());
 			siteId = ToolManager.getCurrentPlacement().getContext();
			subject = coursePeriod.getCourseCode();
		} catch (NotACourseSiteException e) {
			e.getStackTrace();
		}
		M_log.info(this + "View student view of presrcibed books for site " + subject);

		PrescribedbooksDAO dao = new PrescribedbooksDAO();
		try {
			// dao.getPrebooksList("2019", subject);
			List prebooksList = dao.getPrebooksList(year.toString(), subject);
			List recommendedBooksList = dao.getRecommendedBooksList(year.toString(), subject);
			List ereserveBooksList = dao.getEreserveBooksList(year.toString(), subject);
			if(prebooksList.size()>0) {
				preBooklistExist=true;
				context.put("preBooklistExist", preBooklistExist);
			}
			if(recommendedBooksList.size()>0) {
				recBooklistExist=true;
				context.put("recBooklistExist", recBooklistExist);
			}
			if(ereserveBooksList.size()>0) {
				eResBooklistExist=true;
				context.put("eResBooklistExist", eResBooklistExist);
			}
			context.put("prebooklist",  prebooksList); 
			context.put("recommendedBooksList",  recommendedBooksList); 
			context.put("ereserveBooksList",  ereserveBooksList); 
		} catch (Exception e) {
			// TODO Auto-generated catch block
			M_log.error(this + "Error on student view of presrcibed books for site on getting oracle select statements results for the subject" + subject);
			e.printStackTrace();
		}

		return template;

	}

}
