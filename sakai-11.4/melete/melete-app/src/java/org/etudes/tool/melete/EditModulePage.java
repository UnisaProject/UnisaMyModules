/**********************************************************************************
 *
 * $URL: https://source.sakaiproject.org/contrib/etudes/melete/tags/2.9.9/melete-app/src/java/org/etudes/tool/melete/EditModulePage.java $
 * $Id: EditModulePage.java 80314 2012-06-12 22:15:39Z rashmi@etudes.org $
 ***********************************************************************************
 *
 * Copyright (c) 2008, 2009, 2010, 2011 Etudes, Inc.
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
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;
import javax.faces.event.ActionEvent;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.etudes.api.app.melete.ModuleObjService;
import org.etudes.api.app.melete.SectionBeanService;
import org.etudes.api.app.melete.SectionObjService;
import org.etudes.api.app.melete.exception.MeleteException;
import org.etudes.component.app.melete.Module;
import org.etudes.component.app.melete.ModuleDateBean;
import org.etudes.component.app.melete.ModuleShdates;
import org.sakaiproject.event.cover.EventTrackingService;
import org.sakaiproject.thread_local.api.ThreadLocalManager;
import org.sakaiproject.tool.cover.ToolManager;
import org.sakaiproject.user.api.User;
import org.sakaiproject.user.cover.UserDirectoryService;
import org.sakaiproject.util.ResourceLoader;

/**
 * 
 * Edit Module Page is the backing bean for the page edit_module.jsp.
 */

public class EditModulePage extends ModulePage implements Serializable/* , ToolBean */
{

	/** Dependency: The logging service. */
	protected Log logger = LogFactory.getLog(EditModulePage.class);
	private boolean callFromAddContent = false;
	private boolean showLicenseFlag = true;
	private boolean hasSections = true;
	private SectionObjService firstSection = null;
	private String createdAuthor;
	private String modifiedAuthor;
	protected ThreadLocalManager threadLocalManager = org.sakaiproject.thread_local.cover.ThreadLocalManager.getInstance();

	/**
	 * constructor
	 */
	public EditModulePage()
	{
		setFormName("EditModuleForm");
		setSuccess(false);
		createdAuthor = "";
		modifiedAuthor = "";
	}

	/**
	 * Set modification details.
	 */
	public void setModification()
	{
		FacesContext context = FacesContext.getCurrentInstance();
		ValueBinding binding = Util.getBinding("#{meleteSiteAndUserInfo}");
		MeleteSiteAndUserInfo mPage = (MeleteSiteAndUserInfo) binding.getValue(context);
		module.setModificationDate(new Date());
	}

	/**
	 * Note: not in use anymore
	 * 
	 * @return
	 */
	public boolean getShowLicenseFlag()
	{
		return showLicenseFlag;
	}

	/**
	 * Note: not in use anymore
	 * 
	 * @param showLicenseFlag
	 */
	public void setShowLicenseFlag(boolean showLicenseFlag)
	{
		this.showLicenseFlag = showLicenseFlag;
	}

	/**
	 * Update module. Validates the dates.Tracks module edit event.
	 * 
	 * @return
	 */
	public String savehere()
	{
		String errMsg = "";
		setSuccess(false);
		if (moduleService == null) moduleService = getModuleService();

		FacesContext context = FacesContext.getCurrentInstance();
		ResourceLoader bundle = new ResourceLoader("org.etudes.tool.melete.bundle.Messages");
		Map sessionMap = context.getExternalContext().getSessionMap();
		ValueBinding binding = Util.getBinding("#{meleteSiteAndUserInfo}");
		MeleteSiteAndUserInfo mPage = (MeleteSiteAndUserInfo) binding.getValue(context);

		// rashmi added validations start
		// validation
		module.setTitle(module.getTitle().trim());

		// rashmi added validations end
		// actual update
		try
		{
		//	setModification();
			if (module.getKeywords() != null)
			{
				module.setKeywords(module.getKeywords().trim());
			}
			if (module.getKeywords() == null || (module.getKeywords().length() == 0))
			{
				module.setKeywords(module.getTitle());
			}
			ModuleDateBean mdbean = new ModuleDateBean();
			mdbean.setModuleId(getModule().getModuleId().intValue());
			mdbean.setModule((Module) getModule());
			mdbean.setModuleShdate((ModuleShdates) getModuleShdates());
			ArrayList<ModuleDateBean> mdbeanList = new ArrayList<ModuleDateBean>();
			mdbeanList.add(mdbean);
			moduleService.updateProperties(mdbeanList, mPage.getCurrentSiteId(),mPage.getCurrentUser().getId());

			// add module to session
			sessionMap.put("currModule", module);

			// Track the event
			EventTrackingService.post(EventTrackingService.newEvent("melete.module.edit", ToolManager.getCurrentPlacement().getContext(), true));

		}
		catch (MeleteException me)
		{
			logger.debug("show error message for" + me.toString() + me.getMessage() + ",");
			errMsg = bundle.getString(me.getMessage());
			addMessage(context, "Error Message", errMsg, FacesMessage.SEVERITY_ERROR);
			return "failure";
		}
		catch (Exception ex)
		{
			errMsg = bundle.getString("edit_module_fail");
			addMessage(context, "Error Message", errMsg, FacesMessage.SEVERITY_ERROR);
			return "failure";
		}
		/*
		 * if (callFromAddContent == false) { String msg=""; msg = bundle.getString("edit_module_success"); addMessage(context, "Info Message", msg, FacesMessage.SEVERITY_INFO); }
		 */
		setSuccess(true);
		return "success";
	}

	/**
	 * Abstract method implementation for edit module.
	 */
	public String save()
	{
		if (!savehere().equals("failure"))
		{
			callFromAddContent = false;
			setSuccess(true);
		}
		else
		{
			callFromAddContent = false;
		}
		// refresh module and sh dates object
		module = moduleService.getModule(module.getModuleId());
		setModuleShdates(module.getModuleshdate());
		return "edit_module";

	}

	/**
	 * For top mode bar clicks, auto save edit module.
	 * Returns # if save is success else stay on same page to correct error
	 * If page is invoked from outside melete, then clear return url.
	 */
	public String autoSave()
	{
		callFromAddContent = false;
		if (!savehere().equals("failure"))
		{
			setSuccess(true);
			// clear back url as user clicked on top mode bar
			FacesContext ctx = FacesContext.getCurrentInstance();
			ValueBinding binding = Util.getBinding("#{meleteSiteAndUserInfo}");
			MeleteSiteAndUserInfo mPage = (MeleteSiteAndUserInfo) binding.getValue(ctx);
			mPage.setNavigateCM(null);
			return "#";
		}
		return "edit_module";
	}

	/**
	 * Creates a new section.
	 */
	public void addContentSections(ActionEvent evt)
	{
		if (!getSuccess())
		{
			callFromAddContent = true;
			if (!savehere().equals("failure"))
			{
				callFromAddContent = false;
				setSuccess(true);
			}
			else
			{
				callFromAddContent = false;
				return;
			}
		}
		// Revision -- 12/20 - to remove retaintion of values
		FacesContext context = FacesContext.getCurrentInstance();
		Map sessionMap = context.getExternalContext().getSessionMap();
		sessionMap.put("currModule", module);

		ValueBinding binding = Util.getBinding("#{editSectionPage}");
		EditSectionPage editPage = (EditSectionPage) binding.getValue(context);
		editPage.setModule(module);
		Integer newSecId = editPage.addBlankSection();
		try
		{
			if (newSecId != null) context.getExternalContext().redirect("editmodulesections.jsf?sectionId=" + newSecId.toString());
		}
		catch (Exception e)
		{
			return;
		}
		
		return;
	}

	/**
	 * Go to Table of contents.
	 * If invoked from CM and user clicks on TOC then no need to return back. 
	 * 
	 * @return
	 */
	public String gotoTOC()
	{
		if (!getSuccess())
		{
			callFromAddContent = true;
			if (!savehere().equals("failure"))
			{
				callFromAddContent = false;
				setSuccess(true);
			}
			else
			{
				callFromAddContent = false;
				return "edit_module";
			}
		}
		//clear back url
		FacesContext context = FacesContext.getCurrentInstance();
		ValueBinding binding = Util.getBinding("#{meleteSiteAndUserInfo}");
		MeleteSiteAndUserInfo mPage = (MeleteSiteAndUserInfo) binding.getValue(context);
		mPage.setNavigateCM(null);
		
		//go to list page
		binding = Util.getBinding("#{listAuthModulesPage}");
		ListAuthModulesPage listPage = (ListAuthModulesPage) binding.getValue(context);
		listPage.resetValues();
		listPage.setModuleDateBeans(null);
		return "list_auth_modules";
	}

	/**
	 * cancel from edit module Screen. If this is invoked from coursemap, then return back.
	 */
	public String cancel()
	{
		FacesContext context = FacesContext.getCurrentInstance();
		// reset values
		resetModuleValues();
		this.mdBean = null;
		this.firstSection = null;
		this.module = null;
		this.hasSections = false;
		setModuleShdates(null);

		// Go back to coursemap if invoked from there
		ValueBinding binding = Util.getBinding("#{meleteSiteAndUserInfo}");
		MeleteSiteAndUserInfo mPage = (MeleteSiteAndUserInfo) binding.getValue(context);
		if (mPage.getNavigateCM() != null)
		{
			return "coursemap";
		}

		binding = Util.getBinding("#{listAuthModulesPage}");
		ListAuthModulesPage listPage = (ListAuthModulesPage) binding.getValue(context);
		listPage.resetValues();
		listPage.setModuleDateBeans(null);
		return "list_auth_modules";
	}

	public String goDone()
	{
		if (!getSuccess())
		{
			callFromAddContent = true;
			if (!savehere().equals("failure"))
			{
				callFromAddContent = false;
				setSuccess(true);
			}
			else
			{
				callFromAddContent = false;
				return "edit_module";
			}
		}

		//return back to CM if invoked from there
		FacesContext context = FacesContext.getCurrentInstance();
		ValueBinding binding = Util.getBinding("#{meleteSiteAndUserInfo}");
		MeleteSiteAndUserInfo mPage = (MeleteSiteAndUserInfo) binding.getValue(context);
		if (mPage.getNavigateCM() != null)
		{
			return "coursemap";
		}

		binding = Util.getBinding("#{listAuthModulesPage}");
		ListAuthModulesPage listPage = (ListAuthModulesPage) binding.getValue(context);
		listPage.resetValues();
		listPage.setModuleDateBeans(null);
		return "list_auth_modules";
	}
	
	/**
	 * Initialize values for editing a module.
	 * 
	 * @param mdbean
	 */
	public void setEditInfo(ModuleDateBean mdbean)
	{
		resetModuleValues();
		this.mdBean = null;
		this.firstSection = null;
		this.module = null;
		this.hasSections = false;
		setModuleShdates(null);
	
		setModuleDateBean(mdbean);
		setModule(mdbean.getModule());
		setModuleShdates(mdbean.getModuleShdate());

		if ((mdbean.getSectionBeans() == null) || (mdbean.getSectionBeans().isEmpty()))
		{
			setFirstSection(null);
			setHasSections(false);
		}
		else
		{
			setHasSections(true);
			setFirstSection(((SectionBeanService) mdbean.getSectionBeans().get(0)).getSection());
		}

	}

	/**
	 * Checks if module has sections.
	 * 
	 * @return
	 */
	public boolean isHasSections()
	{
		return hasSections;
	}

	/**
	 * @param hasSections
	 *        the hasSections to set
	 */
	public void setHasSections(boolean hasSections)
	{
		this.hasSections = hasSections;
	}

	/**
	 * Edit the section. Takes user to the first section.
	 * If edit module is invoked from CM, then pass the return address to section page.
	 * @return
	 */
	public void editSection(ActionEvent evt)
	{
		callFromAddContent = false;
		if (!getSuccess())
		{
			if (!savehere().equals("failure"))
				setSuccess(true);
			else
				return ;
		}

		FacesContext context = FacesContext.getCurrentInstance();
		Map sessionMap = context.getExternalContext().getSessionMap();
		sessionMap.put("currModule", module);
		
		try
		{
			context.getExternalContext().redirect("editmodulesections.jsf?sectionId=" + firstSection.getSectionId().toString());
		}
		catch (Exception e)
		{
			return;
		}
	}

	public SectionObjService getFirstSection()
	{
		return this.firstSection;
	}
	
	/**
	 * @param firstSection
	 *        the firstSection to set
	 */
	public void setFirstSection(SectionObjService firstSection)
	{
		this.firstSection = firstSection;
	}

	/**
	 * Get Module. Edit can be invoked from coursemap and from author list page. For coursemap, it reads from the threadlocal MELETE_MODULE_ID variable.
	 */
	public ModuleObjService getModule()
	{

		FacesContext ctx = FacesContext.getCurrentInstance();
		ValueBinding binding = Util.getBinding("#{meleteSiteAndUserInfo}");

		MeleteSiteAndUserInfo mPage = (MeleteSiteAndUserInfo) binding.getValue(ctx);
		String courseId = mPage.getCurrentSiteId();
		String userId = mPage.getCurrentUser().getId();

		String moduleIdfromOutside = (String) threadLocalManager.get("MELETE_MODULE_ID");
		if (moduleIdfromOutside != null)
		{
			logger.debug("reading module id from thread local :" + moduleIdfromOutside);
			this.mdBean = (ModuleDateBean) moduleService.getModuleDateBean(userId, courseId, new Integer(moduleIdfromOutside).intValue());
			setEditInfo(this.mdBean);
			String backToOutside = (String) threadLocalManager.get("MELETE_NAVIGATE_BACK");
			mPage.populateMeleteSession();
			mPage.setNavigateCM(backToOutside);
			threadLocalManager.set("MELETE_MODULE_ID", null);
			threadLocalManager.set("MELETE_NAVIGATE_BACK", null);
		}
		if (ctx.getExternalContext().getRequestParameterMap().get("editmodid") != null)
		{
			String selectedModId = (String)ctx.getExternalContext().getRequestParameterMap().get("editmodid");
			this.mdBean = (ModuleDateBean) moduleService.getModuleDateBean(userId, courseId, Integer.parseInt(selectedModId));
			setEditInfo(this.mdBean);
		}
		return super.getModule();
	}

	/**
	 * Set the module date bean.
	 * 
	 * @param userId
	 *        The user Id
	 * @param siteId
	 *        The site Id
	 * @param id
	 *        module Id
	 */
	public void setMdBean(String userId, String siteId, String id)
	{
		if (id == null) return;
		this.mdBean = (ModuleDateBean) moduleService.getModuleDateBean(userId, siteId, new Integer(id).intValue());
		setEditInfo(mdBean);
	}

	/**
	 * Get the creator name. If user Id is specified get the current name otherwise from our stored fields
	 * @return
	 */
	public String getCreatedAuthor()
	{
		try
		{
			if (module.getUserId() != null && module.getUserId().length() > 0)
			{
				User user = UserDirectoryService.getUser(module.getUserId());
				createdAuthor = user.getFirstName();
				createdAuthor = createdAuthor.concat(" " + user.getLastName());
			}
		}
		catch (Exception e)
		{
			createdAuthor = "";
		}
		return createdAuthor;
	}

	/**
	 * Get the last modified author name.
	 * @return
	 */
	public String getModifiedAuthor()
	{
		try
		{
			if (module.getModifyUserId() != null && module.getModifyUserId().length() > 0)
			{
				User user = UserDirectoryService.getUser(module.getModifyUserId());
				modifiedAuthor = user.getFirstName();
				modifiedAuthor = modifiedAuthor.concat(" " + user.getLastName());
			}			
		}
		catch (Exception e)
		{
			modifiedAuthor ="";
		}
		return modifiedAuthor;
	}

}
