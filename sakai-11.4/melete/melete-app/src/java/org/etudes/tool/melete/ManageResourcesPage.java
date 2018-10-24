/**********************************************************************************
 *
 * $URL: https://source.sakaiproject.org/contrib/etudes/melete/tags/2.9.9/melete-app/src/java/org/etudes/tool/melete/ManageResourcesPage.java $
 * $Id: ManageResourcesPage.java 77082 2011-10-24 18:38:10Z rashmi@etudes.org $
 ***********************************************************************************
 *
 * Copyright (c) 2008, 2009, 2011 Etudes, Inc.
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

import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.etudes.api.app.melete.MeleteCHService;

public class ManageResourcesPage
{
	private String fileType;
	private MeleteCHService meleteCHService;
	private String numberItems;
	/** Dependency: The logging service. */
	protected Log logger = LogFactory.getLog(ManageResourcesPage.class);

	/**
	 * default constructor
	 */
	public ManageResourcesPage()
	{

	}

	/**
	 * Redirects to add a file or link page depending on choice
	 * 
	 * @return file_upload_view or link_upload_view or manage_content
	 */
	public String addItems()
	{
		FacesContext ctx = FacesContext.getCurrentInstance();
		ValueBinding binding = Util.getBinding("#{addResourcesPage}");
		AddResourcesPage arPage = (AddResourcesPage) binding.getValue(ctx);
		arPage.resetValues();
		arPage.setNumberItems(this.numberItems);
		if (this.fileType.equals("upload"))
		{
			arPage.setFileType("upload");
			return "file_upload_view";
		}
		if (this.fileType.equals("link"))
		{
			arPage.setFileType("link");
			return "link_upload_view";
		}
		return "manage_content";
	}

	/**
	 * Redirect to modules_author_manage upon cancel
	 * 
	 * @return modules_author_manage
	 */
	public String cancel()
	{
		return "modules_author_manage";
	}

	/**
	 * Return upload or link choice
	 * 
	 * @return fileType string value
	 */
	public String getFileType()
	{
		if (fileType == null || fileType.length() == 0) resetValues();
		return this.fileType;
	}

	/**
	 * @return the meleteCHService
	 */
	public MeleteCHService getMeleteCHService()
	{
		return this.meleteCHService;
	}

	/**
	 * @return number of items to upload or link
	 */
	public String getNumberItems()
	{
		return this.numberItems;
	}

	/**
	 * @return delete_resource page
	 */
	public String redirectDeleteLink()
	{
		return "delete_resource";
	}

	/**
	 * Reset values of list resource page Set from page to manage_content
	 */
	public void resetValues()
	{
		this.fileType = "upload";
		this.numberItems = "1";
		FacesContext ctx = FacesContext.getCurrentInstance();
		ValueBinding binding =Util.getBinding("#{listResourcesPage}");
		ListResourcesPage listResPage = (ListResourcesPage) binding.getValue(ctx);
		listResPage.setFromPage("manage_content");
		listResPage.setSectionId("");
	}

	/**
	 * Set upload or link choice
	 * 
	 * @param fileType
	 *        values are upload or link
	 */
	public void setFileType(String fileType)
	{
		this.fileType = fileType;
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
	 * @param numberItems
	 *        number of items to upload or link
	 */
	public void setNumberItems(String numberItems)
	{
		this.numberItems = numberItems;
	}

}
