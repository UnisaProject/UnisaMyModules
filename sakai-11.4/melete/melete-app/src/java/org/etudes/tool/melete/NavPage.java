/**********************************************************************************
 *
 * $URL: https://source.sakaiproject.org/contrib/etudes/melete/tags/2.9.9/melete-app/src/java/org/etudes/tool/melete/NavPage.java $
 * $Id: NavPage.java 80438 2012-06-20 06:21:16Z mallika@etudes.org $
 ***********************************************************************************
 *
 * Copyright (c) 2008, 2009, 2010, 2011, 2012 Etudes, Inc.
 *
 * Portions completed before September 1, 2008 Copyright (c) 2004, 2005, 2006, 2007, 2008 Foothill College, ETUDES Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied. See the License for the specific language governing
 * permissions and limitations under the License.
 *
 **********************************************************************************/

package org.etudes.tool.melete;

import java.io.Serializable;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.faces.application.FacesMessage;
import javax.faces.el.ValueBinding;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.sakaiproject.component.cover.ServerConfigurationService;
import org.sakaiproject.thread_local.api.ThreadLocalManager;
import org.sakaiproject.tool.cover.ToolManager;
import org.sakaiproject.util.ResourceLoader;
import org.etudes.api.app.melete.MeleteSecurityService;

public class NavPage implements Serializable
{

	private boolean isInstructor;
	private boolean shouldRenderAuthor;
	private boolean shouldRenderManage;
	private boolean shouldRenderPreferences = false;
	private boolean shouldRenderView;
	/** Dependency: The logging service. */
	protected Log logger = LogFactory.getLog(NavPage.class);

	/** Dependency: The Melete Security service. */
	protected MeleteSecurityService meleteSecurityService;
	protected ThreadLocalManager threadLocalManager = org.sakaiproject.thread_local.cover.ThreadLocalManager.getInstance();

	public NavPage()
	{
	}

	/**
	 * Resets values on list auth page and redirects
	 * 
	 * @return list_auth_modules page
	 */
	public String authAction()
	{
		FacesContext ctx = FacesContext.getCurrentInstance();
		String goToPage = checkCallFrom();
		if ("#".equals(goToPage))
		{
			ValueBinding binding = Util.getBinding("#{listAuthModulesPage}");
			ListAuthModulesPage lamPage = (ListAuthModulesPage) binding.getValue(ctx);
			lamPage.resetValues();
			return "list_auth_modules";
		}
		else
			return goToPage;
	}

	/**
	 * Check if the current user has permission as author.
	 * 
	 * @return true if the current user has permission to perform this action, false if not.
	 */
	public boolean getIsInstructor()
	{
		FacesContext context = FacesContext.getCurrentInstance();
		ResourceLoader bundle = new ResourceLoader("org.etudes.tool.melete.bundle.Messages");

		try
		{
			return meleteSecurityService.allowAuthor(ToolManager.getCurrentPlacement().getContext());
		}
		catch (Exception e)
		{
			String errMsg = bundle.getString("auth_failed");
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "auth_failed", errMsg));
			logger.warn(e.toString());
		}
		return false;
	}

	/**
	 * @return true if author mode, false otherwise
	 */
	public boolean isShouldRenderAuthor()
	{
		FacesContext ctx = FacesContext.getCurrentInstance();
		String usrMode = (String) ctx.getExternalContext().getRequestParameterMap().get("myMode");

		shouldRenderAuthor = !("Author".equals(usrMode));
		return shouldRenderAuthor;
	}

	/**
	 * @return true if manage mode, false otherwise
	 */
	public boolean isShouldRenderManage()
	{
		FacesContext ctx = FacesContext.getCurrentInstance();
		String usrMode = (String) ctx.getExternalContext().getRequestParameterMap().get("myMode");

		shouldRenderManage = !("Manage".equals(usrMode));
		return shouldRenderManage;
	}

	/**
	 * @return Returns the shouldRenderPreferences.
	 */
	public boolean isShouldRenderPreferences()
	{
		FacesContext ctx = FacesContext.getCurrentInstance();
		String usrMode = (String) ctx.getExternalContext().getRequestParameterMap().get("myMode");

		shouldRenderPreferences = !("Preferences".equals(usrMode));
		return shouldRenderPreferences;
	}

	/**
	 * @return true if view mode, false otherwise
	 */
	public boolean isShouldRenderView()
	{
		FacesContext ctx = FacesContext.getCurrentInstance();
		String usrMode = (String) ctx.getExternalContext().getRequestParameterMap().get("myMode");

		shouldRenderView = !("View".equals(usrMode));
		return shouldRenderView;
	}

	/**
	 * Check if the current user has permission as student.
	 * 
	 * @return true if the current user has permission to perform this action, false if not.
	 */
	public boolean isUserStudent()
	{
		FacesContext context = FacesContext.getCurrentInstance();
		ResourceLoader bundle = new ResourceLoader("org.etudes.tool.melete.bundle.Messages");

		try
		{
			return meleteSecurityService.allowStudent(ToolManager.getCurrentPlacement().getContext());
		}
		catch (Exception e)
		{
			String errMsg = bundle.getString("auth_failed");
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "auth_failed", errMsg));
			logger.warn(e.toString());
		}
		return false;
	}

	/**
	 * Resets values on manage modules page and redirects
	 * 
	 * @return modules_author_manage page
	 */
	public String manageAction()
	{
		FacesContext ctx = FacesContext.getCurrentInstance();
		String goToPage = checkCallFrom();
		if ("#".equals(goToPage))
		{
			ValueBinding binding = Util.getBinding("#{manageModulesPage}");
			ManageModulesPage mPage = (ManageModulesPage) binding.getValue(ctx);
			mPage.resetValues();
			return "manage_content";
		}
		else
			return goToPage;
	}

	/**
	 * @return list_auth_modules
	 */
	public String prefAction()
	{
		return "list_auth_modules";
	}

	/**
	 * Resets values on author preference page and redirects
	 * 
	 * @return author_preference or student_preference page
	 */
	public String PreferenceAction()
	{
		FacesContext ctx = FacesContext.getCurrentInstance();
		Map sessionMap = ctx.getExternalContext().getSessionMap();
		String goToPage = checkCallFrom();
		cancelCMReturn();
		if ("#".equals(goToPage))
		{
			if (getIsInstructor())
			{
				ValueBinding binding = Util.getBinding("#{licensePage}");
				LicensePage lPage = (LicensePage) binding.getValue(ctx);
				lPage.resetValues();
				ValueBinding authBinding = Util.getBinding("#{authorPreferences}");
				AuthorPreferencePage aPage = (AuthorPreferencePage) authBinding.getValue(ctx);
				aPage.setFormName("UserPreferenceForm");
				aPage.resetValues();
				return "author_preference";
			}
			else
				return "student_preference";
		}
		else
			return goToPage;
	}

	/**
	 * @param meleteSecurityService
	 *        The meleteSecurityService to set.
	 */
	public void setMeleteSecurityService(MeleteSecurityService meleteSecurityService)
	{
		this.meleteSecurityService = meleteSecurityService;
	}

	/**
	 * @param shouldRenderPreferences
	 *        The shouldRenderPreferences to set.
	 */
	public void setShouldRenderPreferences(boolean shouldRenderPreferences)
	{
		this.shouldRenderPreferences = shouldRenderPreferences;
	}

	/**
	 * Resets values on listModulesPage and redirects
	 * 
	 * @return list_modules_student or list_modules_inst page
	 */
	public String viewAction()
	{
		FacesContext ctx = FacesContext.getCurrentInstance();
		Map sessionMap = ctx.getExternalContext().getSessionMap();
		String goToPage = checkCallFrom();
		cancelCMReturn();
		if ("#".equals(goToPage))
		{
			ValueBinding binding = Util.getBinding("#{listModulesPage}");
			ListModulesPage lmPage = (ListModulesPage) binding.getValue(ctx);
			lmPage.resetValues();
			lmPage.setViewModuleBeans(null);
			return lmPage.listViewAction();
		}
		else
			return goToPage;
	}

	private void cancelCMReturn()
	{
		FacesContext context = FacesContext.getCurrentInstance();
		ValueBinding binding = Util.getBinding("#{meleteSiteAndUserInfo}");
		MeleteSiteAndUserInfo mPage = (MeleteSiteAndUserInfo) binding.getValue(context);
		mPage.setNavigateCM(null);
	}
	
	/**
	 * On Click of any mode bar actions, auto save module and section changes. If open screen is add/edit module or add/edit sections auto save the changes.
	 * 
	 * @return editmodulesections or add_module or edit_module or confirm_addmodule
	 */
	private String checkCallFrom()
	{
		FacesContext ctx = FacesContext.getCurrentInstance();
		String callFrom = (String) threadLocalManager.get("MELETE_SAVE_FROM");

		if ("Section".equals(callFrom))
		{
			ValueBinding binding = Util.getBinding("#{editSectionPage}");
			EditSectionPage ePage = (EditSectionPage) binding.getValue(ctx);
			return ePage.autoSave();
		}
		else if ("editModule".equals(callFrom))
		{
			ValueBinding binding = Util.getBinding("#{editModulePage}");
			EditModulePage ePage = (EditModulePage) binding.getValue(ctx);
			return ePage.autoSave();
		}
		else if ("addModule".equals(callFrom))
		{
			ValueBinding binding = Util.getBinding("#{addModulePage}");
			AddModulePage aPage = (AddModulePage) binding.getValue(ctx);
			return aPage.autoSave();
		}
		else if ("listAuth".equals(callFrom))
		{
			ValueBinding binding = Util.getBinding("#{listAuthModulesPage}");
			ListAuthModulesPage lamPage = (ListAuthModulesPage) binding.getValue(ctx);
			return lamPage.saveChanges();
		}
		else if ("authPref".equals(callFrom))
		{
			ValueBinding binding = Util.getBinding("#{authorPreferences}");
			AuthorPreferencePage apPage = (AuthorPreferencePage) binding.getValue(ctx);
			return apPage.autoSave();
		}
		threadLocalManager.set("MELETE_SAVE_FROM", "");
		return "#";
	}
}
