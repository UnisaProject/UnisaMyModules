/**********************************************************************************
 *
 * $URL: https://source.sakaiproject.org/contrib/etudes/melete/tags/2.9.9/melete-app/src/java/org/etudes/tool/melete/ManageModulesPage.java $
 * $Id: ManageModulesPage.java 77082 2011-10-24 18:38:10Z rashmi@etudes.org $
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
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIInput;
import javax.faces.component.html.HtmlPanelGrid;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ValueChangeEvent;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.etudes.api.app.melete.CourseModuleService;
import org.etudes.api.app.melete.ModuleService;
import org.etudes.api.app.melete.SectionService;
import org.etudes.component.app.melete.CourseModule;
import org.etudes.component.app.melete.Module;
import org.sakaiproject.util.ResourceLoader;


public class ManageModulesPage implements Serializable/* ,ToolBean */
{

	private List<CourseModuleService> archiveModulesList;
	
	private Map archiveModulesMap = null;

	private List<CourseModuleService> restoreModulesList;
	
	/** Dependency: The logging service. */
	protected Log logger = LogFactory.getLog(ManageModulesPage.class);

	protected ModuleService moduleService;
	protected SectionService sectionService;

	// attributes
	// restore modules
	int count;
	int listSize;
	boolean selectAllFlag;

	/**
	 * constructor
	 */
	public ManageModulesPage()
	{
		count = 0;
		restoreModulesList = new ArrayList<CourseModuleService>();
		archiveModulesList = null;
		archiveModulesMap = null;
		selectAllFlag = false;
	}

	/**
	 * Upon cancel, redirect
	 * 
	 * @return modules_author_manage page
	 */
	public String cancel()
	{
		return "list_auth_modules";
	}

	/**
	 * Clear archive and restore lists upon cancel and redirect
	 * 
	 * @return modules_author_manage
	 */
	public String cancelRestoreModules()
	{
		archiveModulesList = null;
		archiveModulesMap = null;
		restoreModulesList = null;
		count = 0;
		return "list_auth_modules";
	}

	/**
	 * Delete selected modules
	 * 
	 * @return restore_modules or delete_module page
	 */
	public String deleteModules()
	{
		List<Module> delModules = new ArrayList<Module>(0);

		FacesContext context = FacesContext.getCurrentInstance();
		ResourceLoader bundle = new ResourceLoader("org.etudes.tool.melete.bundle.Messages");

		if (count <= 0)
		{
			String msg = bundle.getString("no_module_delete_selected");
			addMessage(context, "Select  One", msg, FacesMessage.SEVERITY_ERROR);
			return "restore_modules";
		}

		for (ListIterator<?> i = restoreModulesList.listIterator(); i.hasNext();)
		{
			CourseModule cmod = (CourseModule) i.next();
			delModules.add((Module) cmod.getModule());
		}

		ValueBinding binding = Util.getBinding("#{deleteModulePage}");
		DeleteModulePage dmPage = (DeleteModulePage) binding.getValue(context);
		dmPage.setModules(delModules);
		dmPage.setModuleSelected(true);
		dmPage.setFromPage("restore");
		count = 0;

		// 2. clear archivelist
		archiveModulesList = null;
		archiveModulesMap = null;

		return "delete_module";
	}

	/**
	 * @return list of archived modules
	 */
	public List<? extends CourseModuleService> getArchiveModulesList()
	{
		try
		{
			if (archiveModulesList == null)
			{
				restoreModulesList = null;
				FacesContext context = FacesContext.getCurrentInstance();
				ValueBinding binding = Util.getBinding("#{meleteSiteAndUserInfo}");
				MeleteSiteAndUserInfo mPage = (MeleteSiteAndUserInfo) binding.getValue(context);

				archiveModulesList = getModuleService().getArchiveModules(mPage.getCurrentSiteId());
				
				if ((archiveModulesList != null)&&(archiveModulesList.size() > 0))
				{
					archiveModulesMap = getArchiveModulesMap(archiveModulesList);
				}
			}
		}
		catch (Exception e)
		{
			logger.debug("getting archived modules list " + e.toString());
		}
		listSize = archiveModulesList.size();
		return archiveModulesList;
	}
	
	protected Map getArchiveModulesMap(List archiveModulesList)
	{
		if ((archiveModulesList == null)||(archiveModulesList.size() == 0)) return null;
		Map archiveModulesMap = new LinkedHashMap<Integer, CourseModuleService>();

		for (Iterator itr = archiveModulesList.listIterator(); itr.hasNext();)
		{
			CourseModuleService cm = (CourseModuleService) itr.next();
		    archiveModulesMap.put(cm.getModuleId(), cm);
		}	
		return archiveModulesMap;
	}	

	/**
	 * Returns boolean false to not render restore commandlink if modules are not present
	 * 
	 * @return
	 */
	public boolean getFalseBool()
	{
		return false;
	}

	/**
	 * @return size of list
	 */
	public int getListSize()
	{
		return listSize;
	}

	/**
	 * @return Returns the ModuleService.
	 */
	public ModuleService getModuleService()
	{
		return moduleService;
	}

	/**
	 * @return list of restored modules
	 */
	public List<? extends CourseModuleService> getRestoreModulesList()
	{
		return restoreModulesList;
	}

	/**
	 * @return Returns the sectionService.
	 */
	public SectionService getSectionService()
	{
		return sectionService;
	}

	/**
	 * @return boolean value of selectAllFlag
	 */
	public boolean getSelectAllFlag()
	{
		return selectAllFlag;
	}

	/**
	 * NOT in use
	 * 
	 * @return
	 */
	public HtmlPanelGrid getSeqTable()
	{
		return null;
	}

	/**
	 * @return true if archiveModulesList is null, false otherwise
	 */
	public boolean getShouldRenderEmptyList()
	{
		if (archiveModulesList == null || archiveModulesList.size() == 0)
		{
			return true;
		}
		else
			return false;
	}

	/**
	 * Reset values on manage resources page and redirect
	 * 
	 * @return manage_content
	 */
	public String goToManageContent()
	{
		return "manage_content";
	}

	/**
	 * @return restore_modules page
	 */
	public String goToRestoreModules()
	{
		return "restore_modules";
	}

	/**
	 * Reset values on export modules page and redirect
	 * 
	 * @return importexportmodules page
	 */
	public String importExportModules()
	{
		return "importexportmodules";
	}

	/**
	 * Reset all values, archive and restore modules, count and select flag
	 */
	public void resetValues()
	{
		archiveModulesList = null;
		archiveModulesMap = null;
		restoreModulesList = null;
		count = 0;
		selectAllFlag = false;
		// commented as reset is done from delete page on deleting archived modules
		/*
		 * FacesContext ctx = FacesContext.getCurrentInstance(); ValueBinding binding = Util.getBinding("#{deleteModulePage}"); DeleteModulePage dmPage = (DeleteModulePage) binding.getValue(ctx); dmPage.setMdbean(null); dmPage.setModuleSelected(false);
		 * dmPage.setSection(null); dmPage.setSectionSelected(false); dmPage.setModules(null); dmPage.setSectionBeans(null);
		 */
	}

	/**
	 * Restore modules
	 * 
	 * @return restore_modules or list_auth_modules page
	 */
	public String restoreModules()
	{
		FacesContext context = FacesContext.getCurrentInstance();
		ResourceLoader bundle = new ResourceLoader("org.etudes.tool.melete.bundle.Messages");
		ValueBinding binding = Util.getBinding("#{meleteSiteAndUserInfo}");
		MeleteSiteAndUserInfo mPage = (MeleteSiteAndUserInfo) binding.getValue(context);

		if (count <= 0)
		{
			String msg = bundle.getString("no_module_selected");
			addMessage(context, "Select  One", msg, FacesMessage.SEVERITY_ERROR);
			return "restore_modules";
		}
		// 1. restore modules
		try
		{
			String courseId = mPage.getCurrentSiteId();
			String userId = mPage.getCurrentUser().getId();
			getModuleService().restoreModules(restoreModulesList, courseId, userId);
			count = 0;
		}
		catch (Exception me)
		{

			String errMsg = bundle.getString("restore_module_fail");
			context.addMessage(null, new FacesMessage(errMsg));
			return "restore_modules";
		}

		// 2. clear archivelist
		archiveModulesList = null;
		archiveModulesMap = null;

		String confMsg = bundle.getString("restore_modules_msg");
		addMessage(context, "Info", confMsg, FacesMessage.SEVERITY_INFO);

		binding = Util.getBinding("#{listAuthModulesPage}");
		ListAuthModulesPage lPage = (ListAuthModulesPage) binding.getValue(context);
		lPage.resetValues();
		return "list_auth_modules";
	}

	/**
	 * Event that triggers when archived module is selected to restore
	 * 
	 * @param event
	 *        ValueChangeEvent
	 * @throws AbortProcessingException
	 */
	public void selectedRestoreModule(ValueChangeEvent event) throws AbortProcessingException
	{
		FacesContext context = FacesContext.getCurrentInstance();
		UIInput mod_Selected = (UIInput) event.getComponent();

		if (((Boolean) mod_Selected.getValue()).booleanValue() == true)
		{
			String title = (String) mod_Selected.getAttributes().get("title");
			if (title != null) {
				int selectedModId = Integer.parseInt(title);
			
			if (restoreModulesList == null) restoreModulesList = new ArrayList<CourseModuleService>();

			CourseModuleService cm = (CourseModuleService) archiveModulesMap.get(selectedModId);
			restoreModulesList.add(cm);
			count++;
			}

		}
		return;
	}

	/**
	 * @param listSize
	 *        size of list
	 */
	public void setListSize(int listSize)
	{
		this.listSize = listSize;
	}

	/**
	 * @param ModuleService
	 *        The ModuleService to set.
	 */
	public void setModuleService(ModuleService moduleService)
	{
		this.moduleService = moduleService;
	}

	/**
	 * @param sectionService
	 *        The sectionService to set.
	 */
	public void setSectionService(SectionService sectionService)
	{
		this.sectionService = sectionService;
	}

	/**
	 * @param selectAllFlag
	 *        boolean value of selectAllFlag
	 */
	public void setSelectAllFlag(boolean selectAllFlag)
	{
		this.selectAllFlag = selectAllFlag;
	}

	/**
	 * Sort list in ascending order of archived date
	 * 
	 * @return redirect to restore_modules with sorted list
	 */
	public String sortOnDate()
	{
		if (archiveModulesList == null)
		{
			return "restore_modules";
		}
		Collections.sort(archiveModulesList, new MeleteDateComparator());
		return "restore_modules";
	}

	/**
	 * Adds message to faces context
	 * 
	 * @param ctx
	 *        FacesContext object
	 * @param msgName
	 *        Message name
	 * @param msgDetail
	 *        Message detail
	 * @param severity
	 *        Severity of message
	 */
	private void addMessage(FacesContext ctx, String msgName, String msgDetail, FacesMessage.Severity severity)
	{
		FacesMessage msg = new FacesMessage(msgName, msgDetail);
		msg.setSeverity(severity);
		ctx.addMessage(null, msg);
	}
}
