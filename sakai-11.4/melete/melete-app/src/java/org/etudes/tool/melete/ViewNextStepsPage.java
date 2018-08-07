/**********************************************************************************
 *
 * $URL: https://source.sakaiproject.org/contrib/etudes/melete/tags/2.9.1/melete-app/src/java/org/etudes/tool/melete/ViewNextStepsPage.java $
 * $Id: ViewNextStepsPage.java 77082 2011-10-24 18:38:10Z rashmi@etudes.org $  
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
import java.util.List;

import javax.faces.application.Application;
import javax.faces.component.UIParameter;
import javax.faces.component.html.HtmlCommandLink;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.component.html.HtmlPanelGroup;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;
import javax.faces.event.ActionEvent;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.etudes.api.app.melete.ModuleObjService;
import org.etudes.api.app.melete.ModuleService;
import org.sakaiproject.util.ResourceLoader;

public class ViewNextStepsPage implements Serializable
{

	public HtmlPanelGroup secpgroup;
	private ModuleObjService module;
	private int moduleSeqNo;
	private ModuleService moduleService;
	private int nextSeqNo;
	private int prevModId;

	/** identifier field */
	private int prevSecId;
	/** Dependency: The logging service. */
	protected Log logger = LogFactory.getLog(ViewNextStepsPage.class);
	String courseId;

	String userId;

	public ViewNextStepsPage()
	{
		courseId = null;
		userId = null;
	}

	/**
	 * Get module and set seq number
	 * 
	 * @return module
	 */
	public ModuleObjService getModule()
	{
		if (this.module == null)
		{
			this.module = getModuleService().getModule(this.prevModId);
			if (this.module != null)
			{
				if (this.module.getCoursemodule() != null)
				{
					this.moduleSeqNo = this.module.getCoursemodule().getSeqNo();
				}
			}
		}
		return this.module;
	}

	/**
	 * @return module sequence number
	 */
	public int getModuleSeqNo()
	{
		return this.moduleSeqNo;
	}

	/**
	 * @return Returns the ModuleService.
	 */
	public ModuleService getModuleService()
	{
		return moduleService;
	}

	/**
	 * @return next seq no
	 */
	public int getNextSeqNo()
	{
		return nextSeqNo;
	}

	/**
	 * @return prev module id
	 */
	public int getPrevModId()
	{
		return prevModId;
	}

	/**
	 * @return prev sec id
	 */
	public int getPrevSecId()
	{
		return prevSecId;
	}

	/**
	 * Get breadcrumbs
	 * 
	 * @return html panel group
	 */
	public HtmlPanelGroup getSecpgroup()
	{
		return null;
	}

	/**
	 * Get next module
	 * 
	 * 
	 */
	public void goNextModule(ActionEvent evt)
	{
		FacesContext context = FacesContext.getCurrentInstance();
		// this.module = null;
		int nextSeqNo = new Integer(((String) context.getExternalContext().getRequestParameterMap().get("modseqno"))).intValue();
		this.module = null;
		/*ValueBinding binding = Util.getBinding("#{viewModulesPage}");
		ViewModulesPage vmPage = (ViewModulesPage) binding.getValue(context);
		// vmPage.setModuleId(nextMdBean.getModuleId());
		vmPage.setModuleId(0);
		vmPage.setViewMbean(null);
		vmPage.setPrevMbean(null);
		vmPage.setModuleSeqNo(nextSeqNo);
		vmPage.setPrintable(null);
		vmPage.setAutonumber(null);
		vmPage.getViewMbean();*/
		try
		{
			context.getExternalContext().redirect("view_module.jsf?modSeqNo="+nextSeqNo);
		}
		catch (Exception e)
		{
			return;
		}
		return;
	}

	/**
	 * Get item(module or section) before this what's next entry
	 * 
	 * 
	 */
	public void goPrevItem(ActionEvent evt)
	{
		FacesContext context = FacesContext.getCurrentInstance();
		courseId = getCourseId();
		int prevSeqNo, modSeqNo;
		prevSeqNo = getModuleService().getPrevSeqNo(getUserId(), courseId, this.nextSeqNo);

		if (this.prevSecId == 0)
		{
			/*ValueBinding binding = Util.getBinding("#{viewModulesPage}");
			ViewModulesPage vmPage = (ViewModulesPage) binding.getValue(context);
			vmPage.setModuleId(this.prevModId);
			vmPage.setPrintable(null);
			vmPage.setViewMbean(null);
			vmPage.setAutonumber(null);*/
			if (this.nextSeqNo > 1)
			{
				//vmPage.setModuleSeqNo(prevSeqNo);
			}
			/*vmPage.setPrevMbean(null);
			vmPage.getViewMbean();*/
			try
			{
				context.getExternalContext().redirect("view_module.jsf?modId="+this.prevModId+"&modSeqNo="+prevSeqNo);
			}
			catch (Exception e)
			{
				return;
			}
			return;

		}
		else
		{
			/*ValueBinding binding = Util.getBinding("#{viewSectionsPage}");

			ViewSectionsPage vsPage = (ViewSectionsPage) binding.getValue(context);

			vsPage.setSectionId(this.prevSecId);
			vsPage.setModuleId(this.prevModId);*/
			// This condition occurs when whats next is after
			// the last section of the last module
			if (prevSeqNo == -1)
			{
				modSeqNo = this.moduleSeqNo;
			}
			else
			{
				modSeqNo = prevSeqNo;
			}
			/*vsPage.setModuleSeqNo(moduleSeqNo);
			vsPage.setSection(null);
			// added by rashmi on 6/14/05
			vsPage.setModule(null);
			vsPage.setAutonumber(null);*/
			try
			{
				context.getExternalContext().redirect("view_section.jsf?moduleId="+this.prevModId+"&sectionId="+this.prevSecId+"&moduleSeqNo="+modSeqNo);
			}
			catch (Exception e)
			{
				return;
			}
		}
	}

	/**
	 * Go to table of contents
	 * 
	 * @return list_modules_student or list_modules_inst page
	 */
	public String goTOC()
	{
		FacesContext context = FacesContext.getCurrentInstance();
		ValueBinding binding = Util.getBinding("#{meleteSiteAndUserInfo}");
		MeleteSiteAndUserInfo mPage = (MeleteSiteAndUserInfo) binding.getValue(context);
		mPage.setNavigateCM(null);
		binding = Util.getBinding("#{listModulesPage}");
		ListModulesPage listPage = (ListModulesPage) binding.getValue(context);
		listPage.setViewModuleBeans(null);
		listPage.setAutonumberMaterial(null);
		return listPage.listViewAction();
	}

	/**
	 * @return list_bookmarks
	 */
	public String gotoMyBookmarks()
	{
		FacesContext context = FacesContext.getCurrentInstance();
		ValueBinding binding = Util.getBinding("#{bookmarkPage}");
		BookmarkPage bmPage = (BookmarkPage) binding.getValue(context);
		bmPage.resetValues();
		return "list_bookmarks";
	}

	/**
	 * @param module
	 *        module object
	 */
	public void setModule(ModuleObjService module)
	{
		this.module = module;
	}

	/**
	 * @param moduleSeqNo
	 *        module sequence number
	 */
	public void setModuleSeqNo(int moduleSeqNo)
	{
		this.moduleSeqNo = moduleSeqNo;
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
	 * @param nextSeqNo
	 *        next seq no
	 */
	public void setNextSeqNo(int nextSeqNo)
	{
		this.nextSeqNo = nextSeqNo;
	}

	/**
	 * @param prevModId
	 *        prev module id
	 */
	public void setPrevModId(int prevModId)
	{
		this.prevModId = prevModId;
	}

	/**
	 * @param prevSecId
	 *        prev sec id
	 */
	public void setPrevSecId(int prevSecId)
	{
		this.prevSecId = prevSecId;
	}

	/**
	 * Creates breadcrumbs for this whats next entry in the format module title>>whats next
	 * 
	 * @param secpgroup
	 *        html panel group to populate
	 */
	public void setSecpgroup(HtmlPanelGroup secpgroup)
	{

		FacesContext context = FacesContext.getCurrentInstance();
		ResourceLoader bundle = new ResourceLoader("org.etudes.tool.melete.bundle.Messages");
		Application app = context.getApplication();

		List list = secpgroup.getChildren();
		list.clear();

		// 1. add module as commandlink and it takes to view module page
		Class[] param = new Class[1];
		HtmlCommandLink modLink = new HtmlCommandLink();
		param[0] = new ActionEvent(modLink).getClass();
		modLink.setId("modlink");
		modLink.setActionListener(app.createMethodBinding("#{viewModulesPage.viewModule}", param));
		// 1a . add outputtext to display module title
		HtmlOutputText outModule = new HtmlOutputText();
		outModule.setId("modtext");
		if (this.module == null) getModule();
		if (this.module != null) outModule.setValue(this.module.getTitle());
		// 1b. param to set module id
		UIParameter modidParam = new UIParameter();
		modidParam.setName("modId");
		if (this.module != null) 
		{
			modidParam.setValue(this.module.getModuleId());
		}
		modLink.getChildren().add(outModule);
		modLink.getChildren().add(modidParam);
		list.add(modLink);

		// 2. add >>
		HtmlOutputText seperatorText = new HtmlOutputText();
		seperatorText.setId("sep1");
		seperatorText.setTitle(" " + (char) 187 + " ");
		seperatorText.setValue(" " + (char) 187 + " ");
		list.add(seperatorText);

		// note: when subsections are in place then find all parents of subsection
		// and in a while or for loop create commandlink with action/action listener as viewSection

		// 3. add current section title
		HtmlOutputText currSectionText = new HtmlOutputText();
		currSectionText.setId("currsectext");
		currSectionText.setValue(bundle.getString("view_whats_next_whats_next"));

		list.add(currSectionText);

		this.secpgroup = secpgroup;
	}

	/**
	 * @return course id
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
	 * @return user id
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
