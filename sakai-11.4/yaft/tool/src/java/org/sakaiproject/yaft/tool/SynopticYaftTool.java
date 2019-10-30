/**
 * Copyright 2009 The Sakai Foundation
 *
 * Licensed under the Educational Community License, Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.sakaiproject.yaft.tool;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import net.sf.json.JSONArray;

import org.apache.log4j.Logger;
import org.sakaiproject.component.api.ComponentManager;
import org.sakaiproject.user.api.User;
import org.sakaiproject.util.ResourceLoader;
import org.sakaiproject.yaft.api.ActiveDiscussion;
import org.sakaiproject.yaft.api.SakaiProxy;
import org.sakaiproject.yaft.api.YaftForumService;

/**
 * This servlet handles all of the REST type stuff. At some point this may all
 * move into an EntityProvider.
 * 
 * @author Adrian Fish (a.fish@lancaster.ac.uk)
 */
public class SynopticYaftTool extends HttpServlet
{
	private Logger logger = Logger.getLogger(SynopticYaftTool.class);

	private YaftForumService yaftForumService = null;

	private SakaiProxy sakaiProxy;

	public void destroy()
	{
		logger.info("destroy");

		super.destroy();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		if (logger.isDebugEnabled()) logger.debug("doGet()");
		
		if(yaftForumService == null || sakaiProxy == null)
			throw new ServletException("yaftForumService and sakaiProxy MUST be initialised.");
		
		User user = sakaiProxy.getCurrentUser();
		
		if(user == null)
		{
			// We are not logged in
			throw new ServletException("getCurrentUser returned null.");
		}
		
		String siteId = sakaiProxy.getCurrentSiteId();
		String placementId = sakaiProxy.getCurrentToolId();
		
		// We need to pass the language code to the JQuery code in the pages.
		Locale locale = (new ResourceLoader(user.getId())).getLocale();
		String languageCode = locale.getLanguage();
		
		String pathInfo = request.getPathInfo();

		String uri = request.getRequestURI();
		
		if (pathInfo == null || pathInfo.length() < 1) {
			// There's no path info, so this is the initial state

			if (uri.contains("/portal/pda/")) {
				// The PDA portal is frameless for redirects don't work. It also
				// means that we can't pass url parameters to the page.We can
				// use a cookie and the JS will pull the initial state from that
				// instead.
				Cookie params = new Cookie("sakai-tool-params", "placementId=" + placementId + "&langage=" + languageCode + "&skin=" + sakaiProxy.getSakaiSkin());
				response.addCookie(params);

				RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/synoptic_yaft.html");
				dispatcher.include(request, response);
				return;
			} else {
				String url = "/yaft-tool/synoptic_yaft.html?placementId=" + placementId + "&language=" + languageCode + "&skin=" + sakaiProxy.getSakaiSkin();
				response.sendRedirect(url);
				return;
			}
		} else {
			String[] parts = pathInfo.substring(1).split("/");

			if (parts.length >= 1) {
				String part1 = parts[0];

				if ("activeDiscussions.json".equals(part1)) {
					List<ActiveDiscussion> activeDiscussions = yaftForumService.getActiveDiscussions();
					JSONArray data = JSONArray.fromObject(activeDiscussions);
					response.setStatus(HttpServletResponse.SC_OK);
					response.setContentType("application/json");
					response.getWriter().write(data.toString());
					return;
				}
			}
		}
	}

	/**
	 * Sets up the YaftForumService and SakaiProxy instances
	 */
	public void init(ServletConfig config) throws ServletException
	{
		super.init(config);

		if (logger.isDebugEnabled()) logger.debug("init");

		try
		{
			ComponentManager componentManager = org.sakaiproject.component.cover.ComponentManager.getInstance();
			yaftForumService = (YaftForumService) componentManager.get(YaftForumService.class);
			sakaiProxy = yaftForumService.getSakaiProxy();
		}
		catch (Throwable t)
		{
			throw new ServletException("Failed to initialise SynopticYaftTool servlet.", t);
		}
	}
}
