/**********************************************************************************
 *
 * $URL: https://source.sakaiproject.org/contrib/etudes/melete/tags/2.9.1/melete-app/src/java/org/etudes/tool/melete/DeleteResourcePage.java $
 * $Id: DeleteResourcePage.java 77082 2011-10-24 18:38:10Z rashmi@etudes.org $
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
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;
import javax.faces.event.ActionEvent;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.etudes.api.app.melete.MeleteCHService;
import org.etudes.api.app.melete.SectionService;
import org.sakaiproject.content.api.ContentResource;
import org.sakaiproject.entity.api.ResourceProperties;
import org.sakaiproject.util.ResourceLoader;

public class DeleteResourcePage implements Serializable
{

	/** Dependency: The logging service. */
	protected Log logger = LogFactory.getLog(DeleteResourcePage.class);
	private SectionService sectionService;
	private MeleteCHService meleteCHService;

	private String fromPage;
	private boolean warningFlag;
	private String delResourceName;
	private String delResourceId;
	private String sectionId;

	/**
	 * Default constructor
	 */
	public DeleteResourcePage()
	{
		warningFlag = false;
		fromPage = "";
		delResourceId = null;
		delResourceName = null;
		sectionId="";
	}

	/**
	 * Get the from Page
	 * @return
	 */
	public String getFromPage()
	{
		return fromPage;
	}

	/**
	 * Set the start page.
	 * 
	 * @param fromPage
	 */
	public void setFromPage(String fromPage)
	{
		this.fromPage = fromPage;
	}

	/**
	 * Checks the selected resource is used in the other sections. If yes, warns the user.
	 * 
	 * @param delResourceId
	 *        The Resource Id
	 * @param courseId
	 *        The Site Id
	 */
	public void processDeletion(String delResourceId, String courseId)
	{
		this.delResourceId = delResourceId;
		List<?> res_in_use = sectionService.findResourceInUse(delResourceId, courseId);
		if (res_in_use != null) logger.debug("res_in_use size " + res_in_use.size());
		if (res_in_use != null && res_in_use.size() > 0) warningFlag = true;
	}

	/**
	 * Set resource name
	 * 
	 * @param title
	 */
	public void setResourceName(String title)
	{
		delResourceName = title;
	}

	/**
	 * Refresh the resources list.
	 */
	private void refreshCurrSiteResourcesList()
	{
		FacesContext ctx = FacesContext.getCurrentInstance();
		ValueBinding binding = Util.getBinding("#{listResourcesPage}");
		ListResourcesPage listResPage = (ListResourcesPage) binding.getValue(ctx);
		listResPage.setFromPage(this.fromPage);
		listResPage.setSectionId(sectionId);
		listResPage.refreshCurrSiteResourcesList();
	}

	/**
	 * Delete a resource even if shared by other sections. Delete from content hosting.
	 * 
	 * @return navigate back to start page
	 */
	public void deleteResource(ActionEvent evt)
	{
		FacesContext context = FacesContext.getCurrentInstance();
		fromPage = (String) evt.getComponent().getAttributes().get("fromPage");
		sectionId = (String) evt.getComponent().getAttributes().get("sectionId");
		try
		{
			if (delResourceId != null)
			{
				// delete from content resource
				sectionService.deleteResourceInUse(this.delResourceId);
				meleteCHService.removeResource(this.delResourceId);
			}
			context.getExternalContext().redirect(fromPage + "?fromPage=" + fromPage + "&sectionId=" + sectionId);
		}
		catch (Exception e)
		{
			// e.printStackTrace();
			logger.debug("error in delete resource" + e.toString());
			ResourceLoader bundle = new ResourceLoader("org.etudes.tool.melete.bundle.Messages");
			String errMsg = bundle.getString("delete_resource_fail");
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), errMsg));
		}
	}

	/**
	 * Cancel the action. Navigate back to start page.
	 * 
	 * @return
	 */
	public void cancelDeleteResource(ActionEvent evt)
	{
		try
		{
			FacesContext context = FacesContext.getCurrentInstance();
			fromPage = (String) evt.getComponent().getAttributes().get("fromPage");
			sectionId = (String) evt.getComponent().getAttributes().get("sectionId");
			context.getExternalContext().redirect(fromPage + "?fromPage=" + fromPage + "&sectionId=" + sectionId);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * @return Returns the SectionService.
	 */
	public SectionService getSectionService()
	{
		return sectionService;
	}

	/**
	 * @param SectionService
	 *        The SectionService to set.
	 */
	public void setSectionService(SectionService sectionService)
	{
		this.sectionService = sectionService;
	}

	/**
	 * @param meleteCHService
	 *        the meleteCHService to set
	 */
	public void setMeleteCHService(MeleteCHService meleteCHService)
	{
		this.meleteCHService = meleteCHService;
	}

	/**
	 * @return the warningFlag
	 */
	public boolean isWarningFlag()
	{
		return this.warningFlag;
	}

	/**
	 * @param warningFlag
	 *        the warningFlag to set
	 */
	public void setWarningFlag(boolean warningFlag)
	{
		this.warningFlag = warningFlag;
	}

	/**
	 * @return the delResourceName
	 */
	public String getDelResourceName()
	{
		if (this.delResourceName == null || this.delResourceName.length() == 0 && delResourceId != null)
			this.delResourceName = meleteCHService.getDisplayName(delResourceId);
		return this.delResourceName;
	}

	/**
	 * Get the resourceId of the resource being deleted
	 * @return
	 */
	public String getDelResourceId()
	{
		return delResourceId;
	}

	/**
	 * Set the resourceId to delete
	 * @param delResourceId
	 */
	public void setDelResourceId(String delResourceId)
	{
	    logger.debug("setDelResourceId :" + delResourceId);
		this.delResourceId = delResourceId;
		try
		{
		if (delResourceId != null && delResourceId.length() != 0)
		{
			ContentResource cr = meleteCHService.getResource(delResourceId);
			delResourceName = cr.getProperties().getProperty(ResourceProperties.PROP_DISPLAY_NAME);
			String course_id = getCurrentCourseId();
			processDeletion(delResourceId, course_id);
		}
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	/*
	 * get the current course id Pass it to getuploads collection method
	 */
	private String getCurrentCourseId()
	{
		String currId = "";
		FacesContext context = FacesContext.getCurrentInstance();
		Map<?, ?> sessionMap = context.getExternalContext().getSessionMap();
		currId = (String) sessionMap.get("course_id");
		if (currId == null || currId.length() == 0)
		{
			ValueBinding binding = Util.getBinding("#{meleteSiteAndUserInfo}");
			MeleteSiteAndUserInfo info = (MeleteSiteAndUserInfo) binding.getValue(context);
			currId = info.getCourse_id();
		}
		return currId;
	}

	/**
	 * Get the sectionId if the parameter is being passed around for edit section pages.
	 * @return
	 */
	public String getSectionId()
	{
		return sectionId;
	}

	/**
	 * Set the sectionid. This is to pass the parameter back to edit section pages
	 * 
	 * @param sectionId
	 */
	public void setSectionId(String sectionId)
	{
		logger.debug("setSectionId in delete resource:" + sectionId);
		this.sectionId = sectionId;
	}

}
