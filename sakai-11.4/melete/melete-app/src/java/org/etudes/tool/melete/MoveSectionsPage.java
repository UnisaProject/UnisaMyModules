/**********************************************************************************
 *
 * $URL: https://source.sakaiproject.org/contrib/etudes/melete/tags/2.9.1/melete-app/src/java/org/etudes/tool/melete/MoveSectionsPage.java $
 *
 ***********************************************************************************
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
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;
import javax.faces.model.SelectItem;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.etudes.api.app.melete.ModuleObjService;
import org.etudes.api.app.melete.ModuleService;
import org.etudes.api.app.melete.ModuleDateBeanService;
import org.etudes.component.app.melete.SectionBean;
import org.sakaiproject.util.ResourceLoader;

public class MoveSectionsPage implements Serializable
{

	private ArrayList<SelectItem> availableModules;

	private String courseId;

	private List<ModuleDateBeanService> moduleDateBeans;

	private ModuleService moduleService;

	private boolean nomodsFlag;

	private List<SectionBean> sectionBeans;

	/** identifier field */

	private ModuleObjService selectedModule;

	private Integer selectId;

	private String userId;

	/** Dependency: The logging service. */
	protected Log logger = LogFactory.getLog(MoveSectionsPage.class);

	public MoveSectionsPage()
	{

	}

	/**
	 * Reset values on list auth page and redirect
	 * 
	 * @return list_auth_modules
	 */
	public String cancel()
	{
		FacesContext ctx = FacesContext.getCurrentInstance();
		ValueBinding binding = Util.getBinding("#{listAuthModulesPage}");
		ListAuthModulesPage lPage = (ListAuthModulesPage) binding.getValue(ctx);
		lPage.resetValues();
		return "list_auth_modules";
	}

	/**
	 * Get list of modules in a list of select items
	 * 
	 * @return list of select items
	 */
	public List<SelectItem> getAvailableModules()
	{
		FacesContext context = FacesContext.getCurrentInstance();
		ResourceLoader bundle = new ResourceLoader("org.etudes.tool.melete.bundle.Messages");

		try
		{
			if (availableModules == null)
			{
				availableModules = new ArrayList<SelectItem>(0);
				ModuleService moduleService = getModuleService();
				moduleDateBeans = moduleService.getModuleDateBeans(getUserId(), getCourseId());
				if (moduleDateBeans == null || moduleDateBeans.size() == 0)
				{
					nomodsFlag = true;
					return availableModules;
				}
				nomodsFlag = false;
				for (ModuleDateBeanService md : moduleDateBeans)
				{
					availableModules.add(new SelectItem(new Integer(md.getModuleId()), md.getModule().getTitle()));
				}
				// selectId = new Integer(moduleDateBeans.get(0).getModuleId());
			}
		}
		catch (Exception ex)
		{
			String msg = bundle.getString("move_section_fail");
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "move_section_fail", msg));
		}
		return availableModules;
	}

	/**
	 * @return Returns the ModuleService.
	 */
	public ModuleService getModuleService()
	{
		return moduleService;
	}
	
	/**
	 * Get the list of to sections to move
	 * 
	 * @return
	 */
	public List<SectionBean> getSectionBeans()
	{
		return this.sectionBeans;
	}	

	/**
	 * @return the selectId
	 */
	public Integer getSelectId()
	{
		return this.selectId;
	}

	/**
	 * @return the nomodsFlag
	 */
	public boolean isNomodsFlag()
	{
		return this.nomodsFlag;
	}

	/**
	 * Move sections from one module to another
	 * 
	 * @return list_auth_modules if there are no errors
	 */
	public String move()
	{
		logger.debug("move called");
		FacesContext ctx = FacesContext.getCurrentInstance();
		ResourceLoader bundle = new ResourceLoader("org.etudes.tool.melete.bundle.Messages");
		try
		{
			if (selectId != null && selectId != 0) selectedModule = moduleService.getModule(new Integer(selectId).intValue());

			if (selectedModule == null)
			{
				String msg = bundle.getString("select_one_move_section");
				ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "select_one_move_section", msg));
				return "move_section";
			}
			moduleService.moveSections(getCourseId(),sectionBeans, selectedModule);
		}
		catch (Exception e)
		{
			String msg = bundle.getString("move_section_fail");
			ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "move_section_fail", msg));
		}

		ValueBinding binding = Util.getBinding("#{listAuthModulesPage}");
		ListAuthModulesPage lPage = (ListAuthModulesPage) binding.getValue(ctx);
		lPage.resetValues();
		return "list_auth_modules";
	}

	/**
	 * Reset values for move sections page
	 * 
	 */
	public void resetValues()
	{
		moduleDateBeans = null;
		availableModules = null;
		selectedModule = null;
		sectionBeans = null;
		selectId = null;
	}

	/**
	 * @param moduleService
	 *        The moduleService to set.
	 */
	public void setModuleService(ModuleService moduleService)
	{
		this.moduleService = moduleService;
	}

	/**
	 * @param sectionBeans
	 *        the sectionBeans to set
	 */
	public void setSectionBeans(List<SectionBean> sectionBeans)
	{
		this.sectionBeans = sectionBeans;
	}

	/**
	 * @param selectId
	 *        the selectId to set
	 */
	public void setSelectId(Integer selectId)
	{
		this.selectId = selectId;
	}

	/**
	 * @return The course id
	 */
	private String getCourseId()
	{
		if (courseId == null)
		{
			FacesContext context = FacesContext.getCurrentInstance();
			ValueBinding binding = Util.getBinding("#{meleteSiteAndUserInfo}");
			MeleteSiteAndUserInfo mPage = (MeleteSiteAndUserInfo) binding.getValue(context);
			courseId = mPage.getCurrentSiteId();
		}
		return courseId;
	}

	/**
	 * @return The user id
	 */
	private String getUserId()
	{
		if (userId == null)
		{
			FacesContext context = FacesContext.getCurrentInstance();
			ValueBinding binding = Util.getBinding("#{meleteSiteAndUserInfo}");
			MeleteSiteAndUserInfo mPage = (MeleteSiteAndUserInfo) binding.getValue(context);
			userId = mPage.getCurrentUser().getId();
		}
		return userId;
	}
}
