/**********************************************************************************
 *
 * $URL: https://source.etudes.org/svn/apps/melete/tags/2.9.1forSakai/melete-app/src/java/org/etudes/tool/melete/ListResourcesPage.java $
 * $Id: ListResourcesPage.java 3647 2012-12-02 22:30:41Z ggolden $
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
 **********************************************************************************/
package org.etudes.tool.melete;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIData;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.etudes.api.app.melete.MeleteAuthorPrefService;
import org.etudes.api.app.melete.MeleteCHService;
import org.etudes.api.app.melete.MeleteResourceService;
import org.etudes.api.app.melete.SectionObjService;
import org.etudes.api.app.melete.SectionService;
import org.etudes.component.app.melete.MeleteResource;
import org.etudes.component.app.melete.MeleteUserPreference;
import org.sakaiproject.component.cover.ServerConfigurationService;
import org.sakaiproject.content.api.ContentResource;
import org.sakaiproject.content.cover.ContentTypeImageService;
import org.sakaiproject.entity.api.ResourceProperties;
import org.sakaiproject.entity.api.ResourcePropertiesEdit;
import org.sakaiproject.util.ResourceLoader;

public class ListResourcesPage
{
	/*
	 * 
	 * inner class to set required content resource values for display
	 */
	public class DisplaySecResources implements Comparable<DisplaySecResources>
	{
		String resource_gif;
		String resource_id="";
		String resource_title;
		String resource_url;
		boolean typeLink;
		boolean typeLTI;

		/**
		 * constructor
		 * 
		 * @param resource_title
		 * @param resource_id
		 * @param resource_url
		 * @param isTypeLink
		 * @param resource_gif
		 * @param typeLTI
		 */
		public DisplaySecResources(String resource_title, String resource_id, String resource_url, boolean isTypeLink, String resource_gif,
				boolean typeLTI)
		{
			this.resource_title = resource_title;
			this.resource_id = resource_id;
			this.resource_url = resource_url;
			this.typeLink = isTypeLink;
			this.resource_gif = resource_gif;
			this.typeLTI = typeLTI;
		}

		/**
		 * @return result of comparison
		 */
		public int compareTo(DisplaySecResources n)
		{
			int res = 0;
			if (this.typeLTI) res = 1;
			// both are link or upload than equal
			if (this.typeLink == n.isTypeLink() && this.typeLTI == n.isTypeLTI())
				res = this.resource_title.compareToIgnoreCase(n.getResource_title());

			// this is link and n is upload
			if (this.typeLink && (!n.isTypeLink() || n.isTypeLTI())) res = -1;

			// this is upload and n is link
			if (!this.typeLink && n.isTypeLink()) res = 1;

			if (!this.typeLink && n.isTypeLTI()) res = -1;

			return res;
		}

		/**
		 * @return the resource_gif
		 */
		public String getResource_gif()
		{
			return this.resource_gif;
		}

		/**
		 * @return Returns the resource_id.
		 */
		public String getResource_id()
		{
			return resource_id;
		}

		/**
		 * @return Returns the resource_title.
		 */
		public String getResource_title()
		{
			return resource_title;
		}

		/**
		 * @return Returns the resource_url.
		 */
		public String getResource_url()
		{
			return resource_url;
		}

		/**
		 * @return value of typeLink
		 */
		public boolean isTypeLink()
		{
			return this.typeLink;
		}

		/**
		 * @return value of typeLTI
		 */
		public boolean isTypeLTI()
		{
			return typeLTI;
		}

		/**
		 * @param resource_gif
		 *        the resource_gif to set
		 */
		public void setResource_gif(String resource_gif)
		{
			this.resource_gif = resource_gif;
		}

		/**
		 * @param resource_id
		 *        The resource_id to set.
		 */
		public void setResource_id(String resource_id)
		{
			this.resource_id = resource_id;
		}

		/**
		 * @param resource_title
		 *        The resource_title to set.
		 */
		public void setResource_title(String resource_title)
		{
			this.resource_title = resource_title;
		}

		/**
		 * @param resource_url
		 *        The resource_url to set.
		 */
		public void setResource_url(String resource_url)
		{
			this.resource_url = resource_url;
		}

		/**
		 * @param typeLink
		 *        value to set
		 */
		public void setTypeLink(boolean isTypeLink)
		{
			this.typeLink = isTypeLink;
		}

		/**
		 * @param typeLTI
		 *        typeLTI value to set
		 */
		public void setTypeLTI(boolean typeLTI)
		{
			this.typeLTI = typeLTI;
		}

		/*
		 * @return value of resource_title
		 */
		public String toString()
		{
			return resource_title;
		}
	}

	/**
	 * The comparator to sort list ascending or descending
	 */
	static Comparator<DisplaySecResources> SectionResourcesComparatorDesc = new Comparator<DisplaySecResources>()
	{
		public int compare(DisplaySecResources o1, DisplaySecResources o2)
		{
			return -1 * (o1.compareTo(o2));
		}
	};
	public String fromPage;
	private boolean callFromSection = true;
	private List<DisplaySecResources> displayResourcesList;
//	private RemoteFilesListingNav listNav;
	private boolean renderSelectedResource = false;
	private String selResourceIdFromList="";
	private boolean sortAscFlag = true;
	protected List<DisplaySecResources> currSiteResourcesList;
	/** Dependency: The logging service. */
	protected Log logger = LogFactory.getLog(ListResourcesPage.class);
	protected MeleteCHService meleteCHService;
	protected SectionService sectionService;
	protected MeleteAuthorPrefService authorPrefService;
	protected ServerConfigurationService serverConfigurationService;

	private String sectionId="";
	private String newLinkUrl;
	private String newURLTitle;
	private boolean openWindow = false;
	
	private int fromIndex=0;
	private int toIndex=0;
	private int totalSize=0;
	private String chunkSize="30";
	private boolean prevListingFlag = true;
	private boolean nextListingFlag = true;
	private UIData table;
	private String fromIndexParam;
	/**
	 * Default constructor
	 */
	public ListResourcesPage()
	{
		logger.debug("ListResourcesPage constructor");
		displayResourcesList = new ArrayList<DisplaySecResources>();
	}

	/**
	 * Lists resources by fetching the appropriate ones(links/upload/lti/all) depending on page that this method is invoked from
	 * 
	 * @return Returns the currSiteResourcesList.
	 */
	public void getCurrSiteResourcesList()
	{
		try
		{
			FacesContext ctx = FacesContext.getCurrentInstance();
			if (currSiteResourcesList == null || currSiteResourcesList.size() == 0)
			{
				ValueBinding binding = Util.getBinding("#{meleteSiteAndUserInfo}");
				MeleteSiteAndUserInfo mPage = (MeleteSiteAndUserInfo) binding.getValue(ctx);
				String uploadCollId = getMeleteCHService().getUploadCollectionId(mPage.getCurrentSiteId());

				// get list of all resources for upload type for the current site
				currSiteResourcesList = new ArrayList<DisplaySecResources>();

				List<?> allmembers = null;
				if (this.fromPage == null) return;
				// to create list of resource whose type is typeUpload
				if (this.fromPage.equals("ContentUploadServerView") || this.fromPage.equals("editContentUploadServerView"))
				{
					allmembers = getMeleteCHService().getListofFilesFromCollection(uploadCollId);
				}

				if (this.fromPage.equals("ContentLinkServerView") || this.fromPage.equals("editContentLinkServerView"))
				{
					allmembers = getMeleteCHService().getListofLinksFromCollection(uploadCollId);
				}

				if (this.fromPage.equals("ContentLTIServerView") || this.fromPage.equals("editContentLTIServerView"))
				{
					allmembers = getMeleteCHService().getListFromCollection(uploadCollId, MeleteCHService.MIME_TYPE_LTI);
				}

				if (this.fromPage.equals("manage_content"))
				{
					allmembers = getMeleteCHService().getListofMediaFromCollection(uploadCollId);
				}

				if (allmembers == null)
				{					
					return;					
				}
				Iterator<?> allmembers_iter = allmembers.iterator();
				String serverUrl = ServerConfigurationService.getServerUrl();
				while (allmembers_iter != null && allmembers_iter.hasNext())
				{
					ContentResource cr = (ContentResource) allmembers_iter.next();
					String displayName = cr.getProperties().getProperty(ResourceProperties.PROP_DISPLAY_NAME);
					if (displayName.length() > 50) displayName = displayName.substring(0, 50) + "...";
					String rUrl = getMeleteCHService().getResourceUrl(cr.getId());
					boolean rType = cr.getContentType().equals(MeleteCHService.MIME_TYPE_LINK);
					boolean rTypeLTI = cr.getContentType().equals(MeleteCHService.MIME_TYPE_LTI);
					String rgif = serverUrl + "/library/image/sakai/url.gif";
					if (!rType && !rTypeLTI)
					{
						String contentextension = cr.getContentType();
						rgif = ContentTypeImageService.getContentTypeImage(contentextension);
		
						if (rgif.startsWith("sakai"))
							rgif = rgif.replace("sakai", (serverUrl + "/library/image/sakai"));
						else if (rgif.startsWith("/sakai")) rgif = rgif.replace("/sakai", (serverUrl + "/library/image/sakai"));
					}
					else if (rTypeLTI)
					{
						rgif = "images/web_service.png";
					}
					currSiteResourcesList.add(new DisplaySecResources(displayName, cr.getId(), rUrl, rType, rgif, rTypeLTI));
				}				
			}
			
			if (ctx.getExternalContext().getRequestParameterMap().get("fromIndex") != null)
			{
				String t = (String) ctx.getExternalContext().getRequestParameterMap().get("fromIndex");
				fromIndex = Integer.parseInt(t);
			}
			if (ctx.getExternalContext().getRequestParameterMap().get("chunkSize") != null)
			{
				this.chunkSize = (String) ctx.getExternalContext().getRequestParameterMap().get("chunkSize");
			}
			if (ctx.getExternalContext().getRequestParameterMap().get("sortAscFlag") != null)
			{
				String s = (String) ctx.getExternalContext().getRequestParameterMap().get("sortAscFlag");
				sortAscFlag = new Boolean(s).booleanValue();
			}
			
			totalSize = currSiteResourcesList.size() + 1;
			int c = Integer.parseInt(chunkSize);
			if (c == -1) c = totalSize -1;
			toIndex = fromIndex + c;
			if (toIndex >= (totalSize - 1)) toIndex = totalSize - 1;
			sortList();
		//	currSiteResourcesList = currSiteResourcesList.subList(fromIndex, toIndex);
			for(int i=fromIndex; i <toIndex; i++)
			{
				displayResourcesList.add(currSiteResourcesList.get(i));
			}
	
		}
		catch (Exception e)
		{
			logger.debug("error in creating list for server residing files" + e.toString());
			e.printStackTrace();
		}
		return;
	}

	/**
	 * Responsible for displaying listing in chunks
	 * 
	 * @return resource list in chunks
	 */
	public List<DisplaySecResources> getDisplayResourcesList()
	{
		return displayResourcesList;
	}

	/**
	 * @return page that the user is on(such as manage_content or section listing pages)
	 */
	public String getFromPage()
	{
		return fromPage;
	}

	/**
	 * @return Returns the listNav(30 resources on a page).
	 */
	/*public RemoteFilesListingNav getListNav()
	{
		if (listNav == null)
		{
			String chunkSize="30";
			if (FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("chunkSize") != null)
			{
				chunkSize = (String)FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("chunkSize");
			}
			
			listNav = new RemoteFilesListingNav(0, 0, Integer.parseInt(chunkSize));
		}

		return listNav;
	}*/

	/**
	 * @return Returns the meleteCHService.
	 */
	public MeleteCHService getMeleteCHService()
	{
		return meleteCHService;
	}

	/**
	 * @return Returns the selResourceIdFromList.
	 */
	public String getSelResourceIdFromList()
	{
		return selResourceIdFromList;
	}

	/**
	 * @return value of sort flag
	 */
	public boolean getSortAscFlag()
	{
		return this.sortAscFlag;
	}

	/**
	 * @return false if fromPage is manage_content, true otherwise
	 */
	public boolean isCallFromSection()
	{
		if (this.fromPage != null)
		{
			if (this.fromPage.equals("manage_content"))
			{
				callFromSection = false;
			}
			else
			{
				callFromSection = true;
			}
		}
		else
		{
			callFromSection = true;
		}
		return callFromSection;
	}

	/**
	 * @return value of renderSelectedResource
	 */
	public boolean isRenderSelectedResource()
	{
		return renderSelectedResource;
	}

	/**
	 * @return delete_resource page
	 */
	public String redirectDeleteLink()
	{
		return "delete_resource";
	}

	/**
	 * Refresh the list.
	 */
	public void refreshCurrSiteResourcesList()
	{
		currSiteResourcesList = null;
		getCurrSiteResourcesList();
		return;
	}

	/**
	 * Method that executes when a resource is clicked on values of selected resource are set
	 * 
	 * @param evt
	 *        ActionEvent object
	 */
	public String link2meAction()
	{
		FacesContext ctx = FacesContext.getCurrentInstance();
		try
		{
			selResourceIdFromList = getSelResourceIdFromList();
			logger.debug("res id selected:" + selResourceIdFromList);
			if (sectionId != null && sectionId.length() != 0)
			{
				SectionObjService sec = sectionService.getSection(Integer.parseInt(sectionId));
				MeleteResourceService mres = sectionService.getMeleteResource(getSelResourceIdFromList());
				sectionService.editSection(sec, mres, getCurrUserId(), true);
			}

			FacesContext.getCurrentInstance().getExternalContext().redirect("editmodulesections.jsf?sectionId=" + sectionId);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return fromPage;
	}
	
	/**
	 * Sets delete resource page with selected resource values
	 * 
	 * @param evt
	 *        ActionEvent object
	 */
	public String selectedResourceDeleteAction()
	{
		try
		{
		FacesContext ctx = FacesContext.getCurrentInstance();
		String delRes_id = getSelResourceIdFromList();
	
		ctx.getExternalContext().redirect("delete_resource.jsf?fromPage=" + fromPage + "&delResourceId="+ delRes_id + "&sectionId=" + sectionId);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return fromPage;
	}

	/**
	 * @param callFromSection
	 *        The callFromSection to set.
	 */
	public void setCallFromSection(boolean callFromSection)
	{
		this.callFromSection = callFromSection;
	}

	/**
	 * @param fromPage
	 *        value of page that user is on
	 */
	public void setFromPage(String fromPage)
	{
		this.fromPage = fromPage;
	}

	/**
	 * @param meleteCHService
	 *        meleteChService to set
	 */
	public void setMeleteCHService(MeleteCHService meleteCHService)
	{
		this.meleteCHService = meleteCHService;
	}

	/**
	 * @param renderSelectedResource
	 *        boolean value that determines if selected resource is rendered
	 */
	public void setRenderSelectedResource(boolean renderSelectedResource)
	{
		this.renderSelectedResource = renderSelectedResource;
	}

	/**
	 * Get the section Id to associate the selected resource
	 * 
	 * @return
	 */
	public String getSectionId()
	{
		return sectionId;
	}

	/**
	 * Set the section id
	 * 
	 * @param sectionId
	 */
	public void setSectionId(String sectionId)
	{
		this.sectionId = sectionId;
	}

	/**
	 * @param selResourceIdFromList
	 *        string value to set
	 */
	public void setSelResourceIdFromList(String selResourceIdFromList)
	{
		this.selResourceIdFromList = selResourceIdFromList;
	}

	/**
	 * @param sectionService
	 *        The sectionService to set.
	 */
	public void setSectionService(SectionService sectionService)
	{
		this.sectionService = sectionService;
	}
	
	public void setAuthorPrefService(MeleteAuthorPrefService authorPrefService)
	{
		this.authorPrefService = authorPrefService;
	}

	/**
	 * @param serverConfigurationService
	 *        The ServerConfigurationService to set.
	 */
	public void setServerConfigurationService(ServerConfigurationService serverConfigurationService)
	{
		this.serverConfigurationService = serverConfigurationService;
	}

	/**
	 * @param sortAscFlag
	 *        value of sort flag
	 */
	public void setSortAscFlag(boolean sortAscFlag)
	{
		this.sortAscFlag = sortAscFlag;
	}

	/**
	 * Reset flag and index and sort list
	 * 
	 * @return the list resources page
	 */
	public void sortResourcesAsc(ActionEvent evt)
	{
		sortAscFlag = false;
		try
		{
		FacesContext.getCurrentInstance().getExternalContext().redirect(fromPage +"?fromPage=" + fromPage + "&sectionId=" + sectionId + "&chunkSize=" + chunkSize + "&sortAscFlag=" + sortAscFlag);
		} catch (Exception e) {e.getMessage();}
	}

	/**
	 * Reset flag and index and sort list
	 * 
	 * @return the list resources page
	 */
	public void sortResourcesDesc(ActionEvent evt)
	{
		sortAscFlag = true;
		try
		{
		FacesContext.getCurrentInstance().getExternalContext().redirect(fromPage +"?fromPage=" + fromPage + "&sectionId=" + sectionId + "&chunkSize=" + chunkSize + "&sortAscFlag=" + sortAscFlag);
		} catch (Exception e) {e.getMessage();}
	}

	/**
	 * Sort resources list depending on value of sortAscFlag
	 * 
	 */
	private void sortList()
	{
		if (sortAscFlag)
			java.util.Collections.sort(currSiteResourcesList);
		else
			Collections.sort(currSiteResourcesList, SectionResourcesComparatorDesc);
	}

	/**
	 * Reset all values of page
	 * 
	 */
	void resetValues()
	{
		setRenderSelectedResource(false);
		setSelResourceIdFromList(null);
//		setSelectedResourceName(null);
		setSortAscFlag(true);
	//	setListNav(null);
		refreshCurrSiteResourcesList();
	}

	/**
	 * 
	 * @param newResource
	 * @return
	 */
	private MeleteResourceService setDefaultLicense(MeleteResourceService newResource)
	{
		try
		{
			MeleteUserPreference mup = (MeleteUserPreference) authorPrefService.getUserChoice(getCurrUserId());
			if (mup == null || mup.getLicenseCode() == null)
			{
				newResource.setLicenseCode(0);
				return newResource;
			}
			newResource.setLicenseCode(mup.getLicenseCode());
			newResource.setAllowCmrcl(mup.isAllowCmrcl());
			newResource.setAllowMod(mup.getAllowMod());
			newResource.setCcLicenseUrl(mup.getCcLicenseUrl());
			newResource.setCopyrightOwner(mup.getCopyrightOwner());
			newResource.setCopyrightYear(mup.getCopyrightYear());
			newResource.setReqAttr(mup.isReqAttr());

			return newResource;
		}
		catch (Exception e)
		{
			newResource.setLicenseCode(0);
			return newResource;
		}
	}
	
	/**
	 * Add the newly provided local file
	 * 
	 * @param evt
	 */
	public void addNewFile(ActionEvent evt)
	{
		try
		{
			FacesContext context = FacesContext.getCurrentInstance();
			org.apache.commons.fileupload.FileItem fi = (org.apache.commons.fileupload.FileItem) context.getExternalContext().getRequestMap().get(
					"file1");

			if (fi != null && fi.getName() != null && fi.getName().length() != 0)
			{

				Util.validateUploadFileName(fi.getName());
				// filename on the client
				String secResourceName = fi.getName();
				if (secResourceName.indexOf("/") != -1)
				{
					secResourceName = secResourceName.substring(secResourceName.lastIndexOf("/") + 1);
				}
				if (secResourceName.indexOf("\\") != -1)
				{
					secResourceName = secResourceName.substring(secResourceName.lastIndexOf("\\") + 1);
				}

				byte[] secContentData = new byte[(int) fi.getSize()];
				InputStream is = fi.getInputStream();
				is.read(secContentData);

				String secContentMimeType = fi.getContentType();

				if (secContentMimeType != null)
				{
					ResourcePropertiesEdit res = getMeleteCHService().fillInSectionResourceProperties(false, secResourceName, "");
					String containCollectionId = getMeleteCHService().getUploadCollectionId(getCurrentCourseId());
					String newResourceId = getMeleteCHService().addResourceItem(secResourceName, secContentMimeType, containCollectionId,
							secContentData, res);
					MeleteResourceService newResource = new MeleteResource();
					newResource.setResourceId(newResourceId);
					//set default license information
					newResource = setDefaultLicense(newResource);
					SectionObjService section = sectionService.getSection(Integer.parseInt(sectionId));

					section.setOpenWindow(openWindow);
				//	sectionService.insertMeleteResource(section, newResource);
					sectionService.editSection(section, newResource, getCurrUserId(), true);
				}
			}
	//		 String secId = (String) evt.getComponent().getAttributes().get("sectionId");
			 FacesContext.getCurrentInstance().getExternalContext().redirect("editmodulesections.jsf?sectionId="+sectionId);
		}
		catch (Exception e)
		{
			logger.debug("file upload FAILED" + e.toString());
			e.printStackTrace();
		}
	}

	/**
	 * set selected file.
	 * 
	 * @return
	 */
	public String setServerFile()
	{
		return "editmodulesections";
	}

	/**
	 * Cancel selected file.
	 * 
	 * @return
	 */
	public void cancelServerFile(ActionEvent evt)
	{
		try
		{
	//		String secId = (String) evt.getComponent().getAttributes().get("sectionId");
			FacesContext.getCurrentInstance().getExternalContext().redirect("editmodulesections.jsf?sectionId=" + sectionId);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Add newly provided URL item
	 * 
	 * @param evt
	 */
	public void addNewUrl(ActionEvent evt)
	{
		try
		{
			// if title is not provided and url is provided then title is same as url.
			if (newLinkUrl != null && newLinkUrl.length() != 0)
			{
				if (newURLTitle == null || newURLTitle.length() == 0) newURLTitle = newLinkUrl;
			}
			if (newLinkUrl != null && newURLTitle != null && newLinkUrl.length() != 0 && newURLTitle.length() != 0)
			{
				if (newURLTitle.trim().length() > SectionService.MAX_URL_LENGTH)
					newURLTitle = newURLTitle.substring(0, SectionService.MAX_URL_LENGTH);
				byte[] secContentData = new byte[newLinkUrl.length()];
				secContentData = newLinkUrl.getBytes();
				String res_mime_type = MeleteCHService.MIME_TYPE_LINK;
				ResourcePropertiesEdit res = getMeleteCHService().fillInSectionResourceProperties(false, newURLTitle, "");
				String containCollectionId = getMeleteCHService().getUploadCollectionId(getCurrentCourseId());
				String newResourceId = getMeleteCHService().addResourceItem(newURLTitle, res_mime_type, containCollectionId, secContentData, res);

				MeleteResourceService newResource = new MeleteResource();
				newResource.setResourceId(newResourceId);
				// set default license information
				newResource = setDefaultLicense(newResource);
				SectionObjService section = sectionService.getSection(Integer.parseInt(sectionId));

				section.setOpenWindow(openWindow);
				sectionService.editSection(section, newResource, getCurrUserId(), true);
			}
			FacesContext.getCurrentInstance().getExternalContext().redirect("editmodulesections.jsf?sectionId=" + sectionId);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Get the current user Id
	 * 
	 * @return
	 */
	public String getCurrUserId()
	{
		String currUserId ="";
		FacesContext context = FacesContext.getCurrentInstance();
		Map<?, ?> sessionMap = context.getExternalContext().getSessionMap();
		if (sessionMap != null && sessionMap.containsKey("userId"))
			currUserId = (String) sessionMap.get("userId");
		else
		{
			ValueBinding binding = Util.getBinding("#{meleteSiteAndUserInfo}");
			MeleteSiteAndUserInfo meleteSiteAndUser = (MeleteSiteAndUserInfo) binding.getValue(context);
			currUserId = meleteSiteAndUser.getCurrentUser().getId();
		}
		return currUserId;
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
	 * get the max uploads size allowed for the course.
	 */
	public int getMaxUploadSize()
	{

		FacesContext context = FacesContext.getCurrentInstance();
		ValueBinding binding = Util.getBinding("#{meleteSiteAndUserInfo}");
		MeleteSiteAndUserInfo mPage = (MeleteSiteAndUserInfo) binding.getValue(context);
		int sz = mPage.getMaxUploadSize();

		return sz;
	}

	/**
	 * Cancel selected file.
	 * 
	 * @return
	 */
	public String cancelServerUrl()
	{
		return "editmodulesections";
	}

	/**
	 * the new provided URL
	 * 
	 * @return
	 */
	public String getNewLinkUrl()
	{
		return newLinkUrl;
	}

	/**
	 * Set the URL
	 * 
	 * @param newLinkUrl
	 */
	public void setNewLinkUrl(String newLinkUrl)
	{
		this.newLinkUrl = newLinkUrl;
	}

	/**
	 * Get the new URL Title
	 * 
	 * @return
	 */
	public String getNewURLTitle()
	{
		return newURLTitle;
	}

	/**
	 * Set the new URL title
	 * 
	 * @param newURLTitle
	 */
	public void setNewURLTitle(String newURLTitle)
	{
		this.newURLTitle = newURLTitle;
	}

	/**
	 * Get the provided open window status
	 * 
	 * @return
	 */
	public boolean isOpenWindow()
	{
		FacesContext facesContext = FacesContext.getCurrentInstance();
		if (facesContext.getExternalContext().getRequestParameterMap().get("showMessage") != null)
		{
			String show = (String) facesContext.getExternalContext().getRequestParameterMap().get("showMessage");

			if (show == null || show.length() == 0) return openWindow;

			ResourceLoader bundle = new ResourceLoader("org.etudes.tool.melete.bundle.Messages");
			String errMsg = bundle.getString("file_too_large");
			facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "file_too_large", errMsg));
		}
		return openWindow;
	}

	/**
	 * Set the open window status
	 * 
	 * @param openWindow
	 */
	public void setOpenWindow(boolean openWindow)
	{
		this.openWindow = openWindow;
	}

	/**
	 * Get the listing start index
	 * @return
	 */
	public int getFromIndex()
	{
		return fromIndex;
	}

	/**
	 * Set the listing start index
	 * @param fromIndex
	 */
	public void setFromIndex(int fromIndex)
	{
		this.fromIndex = fromIndex;
	}

	public String getFromIndexParam()
	{
		fromIndexParam = Integer.toString(fromIndex);
		return fromIndexParam;
	}

	public void setFromIndexParam(String fromIndexParam)
	{
		fromIndex = Integer.parseInt(fromIndexParam);
		this.fromIndexParam = fromIndexParam;
	}

	/**
	 * Get the listing end index. set according to the chunk size
	 * @return
	 */
	public int getToIndex()
	{
		return toIndex;
	}

	/**
	 * Set listing end index
	 * @param toIndex
	 */
	public void setToIndex(int toIndex)
	{
		this.toIndex = toIndex;
	}

	/**
	 * Get the total listing size
	 * @return
	 */
	public int getTotalSize()
	{
		return totalSize;
	}

	/**
	 * Set the listing size
	 * @param totalSize
	 */
	public void setTotalSize(int totalSize)
	{
		this.totalSize = totalSize;
	}

	/**
	 * Get the chunk size for pagination
	 * @return
	 */
	public String getChunkSize()
	{
		if (Integer.parseInt(chunkSize) == (totalSize -1)) return "-1";
		return chunkSize;
	}

	/**
	 * Set the chunk size for pagination
	 * @param chunkSize
	 */
	public void setChunkSize(String chunkSize)
	{
		this.chunkSize = chunkSize;
	}

	/**
	 * Change the pagination size. Refreshes the page and starts with new chunk size.
	 * 
	 * @param event
	 * @throws AbortProcessingException
	 */
	public void changeChunkSize(ValueChangeEvent event) throws AbortProcessingException
	{
		UIInput chunkSelect = (UIInput) event.getComponent();

		this.chunkSize = (String) chunkSelect.getValue();
		// -1 implies all resources need to be displayed
		if (this.chunkSize.equals("-1")) this.chunkSize = Integer.toString(totalSize - 1);
		int c = Integer.parseInt(chunkSize);
		if (c == -1) c = totalSize - 1;
		toIndex = fromIndex + c;
		if (toIndex > (totalSize - 1)) toIndex = totalSize - 1;
		// FacesContext.getCurrentInstance().renderResponse();
		try
		{
			FacesContext.getCurrentInstance().getExternalContext().redirect(
					fromPage + "?fromPage=" + fromPage + "&sectionId=" + sectionId + "&chunkSize=" + chunkSize);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Process start and end indices for the previous page
	 * 
	 * @return the start page
	 */
	public void goPrev(ActionEvent evt)
	{	
		int c = Integer.parseInt(chunkSize);
		if (c == -1) c = totalSize -1;
		fromIndex = fromIndex - c;
		if (fromIndex < 0) fromIndex = 0;

		try
		{
		FacesContext.getCurrentInstance().getExternalContext().redirect(fromPage +"?fromPage=" + fromPage + "&sectionId=" + sectionId + "&chunkSize=" + chunkSize + "&fromIndex=" + fromIndex);
		} catch (Exception e) {e.getMessage();}
	}

	/**
	 * Process start and end indices for the next page
	 * 
	 * @return the start page
	 */
	public void goNext(ActionEvent evt)
	{
		int c = Integer.parseInt(chunkSize);
		if (c == -1) c = totalSize -1;
		fromIndex = fromIndex + c;
		try
		{
		FacesContext.getCurrentInstance().getExternalContext().redirect(fromPage +"?fromPage=" + fromPage + "&sectionId=" + sectionId + "&chunkSize=" + chunkSize + "&fromIndex=" + fromIndex);
		} catch (Exception e) {e.getMessage();}
	}
	
	/**
	 * Show navigation prev button
	 * @return true if shown listing is from middle
	 */
	public boolean isPrevListingFlag()
	{	
		if (currSiteResourcesList == null) getCurrSiteResourcesList();
		if (fromIndex == 0)	prevListingFlag = false;
		else prevListingFlag = true;
		return prevListingFlag;
	}

	/**
	 * Show navigation next button
	 * @return true if more listing is to show
	 */
	public boolean isNextListingFlag()
	{
		if (toIndex < (totalSize - 1))nextListingFlag = true;
		else nextListingFlag = false;
		return nextListingFlag;
	}

	/**
	 * Get the display start index
	 * @return
	 */
	public String getDisplayFromIndex()
	{
		if (currSiteResourcesList == null) getCurrSiteResourcesList();
		return new Integer(fromIndex + 1).toString();
	}

	/**
	 * Get the display end index
	 * @return
	 */
	public String getDisplayEndIndex()
	{
		return new Integer(toIndex).toString();
	}
	
	/**
	 * @return value of datatable (in which resources are rendered)
	 */
	public UIData getTable()
	{
		return table;
	}

	/**
	 * @param table module datatable to set
	 */
	public void setTable(UIData table)
	{
		this.table = table;
	}
}
