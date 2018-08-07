/**********************************************************************************
 *
 * $URL: https://source.sakaiproject.org/contrib/etudes/melete/tags/2.9.1/melete-app/src/java/org/etudes/tool/melete/SectionPage.java $
 * $$
 * $Id: SectionPage.java 83082 2013-03-15 20:30:37Z mallika@etudes.org $
 ************************************************************************************
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

import java.io.File;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.component.UIParameter;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.etudes.api.app.melete.MeleteCHService;
import org.etudes.api.app.melete.ModuleObjService;
import org.etudes.api.app.melete.ModuleService;
import org.etudes.api.app.melete.SectionObjService;
import org.etudes.api.app.melete.SectionResourceService;
import org.etudes.api.app.melete.SectionService;
import org.etudes.api.app.melete.exception.MeleteException;
import org.etudes.api.app.melete.exception.UserErrorException;
import org.etudes.component.app.melete.MeleteResource;
import org.etudes.component.app.melete.MeleteUserPreference;
import org.etudes.component.app.melete.Module;
import org.etudes.component.app.melete.SectionResource;
import org.etudes.util.HtmlHelper;
import org.imsglobal.basiclti.BasicLTIUtil;
import org.sakaiproject.authz.api.SecurityAdvisor;
import org.sakaiproject.component.api.ServerConfigurationService;
import org.sakaiproject.content.api.ContentHostingService;
import org.sakaiproject.content.api.ContentResource;
import org.sakaiproject.entity.api.ResourceProperties;
import org.sakaiproject.entity.api.ResourcePropertiesEdit;
import org.sakaiproject.tool.cover.SessionManager;
import org.sakaiproject.tool.cover.ToolManager;
import org.sakaiproject.util.ResourceLoader;

/**
 * 
 * This class is the backing bean for AddModuleSections.jsp and EditModuleSections.jsp page.
 */

public abstract class SectionPage implements Serializable
{

	private StringBuffer author;
	protected String contentEditor;
	protected String previewContentData;
	protected String checkUploadChange;
	private boolean success = false;
	private boolean renderInstr = false;

	public String serverLocation;
	public String formName;
	
	// rendering flags
	protected boolean shouldRenderEditor = true;
	protected boolean shouldRenderLink = true;
	protected boolean shouldRenderLTI = true;
	protected boolean shouldRenderUpload = true;
	protected boolean shouldRenderNotype = true;

	protected boolean shouldLTIDisplayAdvanced = false;

	/** Dependency: The logging service. */
	protected Log logger = LogFactory.getLog(SectionPage.class);
	protected SectionService sectionService;
	protected ModuleService moduleService;
	protected ServerConfigurationService serverConfigurationService;
	protected ModuleObjService module;
	protected SectionObjService section;
	protected MeleteCHService meleteCHService;
	protected ContentHostingService contentservice;

	// rashmi - 3.0 added variables
	protected String access;
	protected SectionResourceService secResource;

	private String linkUrl;
	private String ltiDescriptor;
	private byte[] secContentData;
	protected String selResourceIdFromList;
	private String nullString = null;
	protected String secResourceName;
	protected String secResourceDescription;
	protected MeleteResource meleteResource;
	protected String FCK_CollId;

	protected String currLinkUrl;
	protected String currLTIDescriptor;
	protected String currLTIKey;
	protected String currLTIPassword;
	protected String currLTIUrl;
	protected String displayCurrLink;
	protected String newURLTitle;
	protected String newLTIDescriptor;
	protected ArrayList<SelectItem> allContentTypes;

	protected String selectedResourceName;

	protected String selectedResourceDescription;

	protected MeleteResource selectedResource;

	protected Boolean contentWithHtml;

	protected String oldType;
	private String currUserId;
	
	private Boolean isComposeDataEdited = false;
	
	/**
	 * default constructor
	 */
	public SectionPage()
	{
		module = null;
		section = null;
		contentEditor = null;
		checkUploadChange = null;
		serverLocation = "http://localhost:8080";
		secResourceName = null;
		secResourceDescription = "";
		selResourceIdFromList = null;
		secResource = null;
		meleteResource = null;
		allContentTypes = null;
		contentWithHtml = null;
		shouldRenderEditor = true;
		shouldRenderLink = true;
		shouldRenderLTI = true;
		shouldRenderUpload = true;
		shouldRenderNotype = true;
		shouldLTIDisplayAdvanced = false;

	}

	/**
	 * @return Returns the ModuleService.
	 */
	public ModuleService getModuleService()
	{
		return moduleService;
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
	 * @return success render sucess message if this flag is true
	 */
	public boolean getSuccess()
	{
		return this.success;
	}

	/**
	 * @param success
	 *        to set sucess to true if section save is successful.
	 */
	public void setSuccess(boolean success)
	{
		this.success = success;
	}

	/**
	 * Set flag to render editors
	 * 
	 * @return
	 */
	public boolean getShouldRenderEditor()
	{
		if (this.section != null && this.section.getContentType() != null)
		{

			shouldRenderEditor = this.section.getContentType().equals("typeEditor");
		}
		return shouldRenderEditor;
	}

	/**
	 * Set flag to render select/replace links
	 * 
	 * @return
	 */
	public boolean getShouldRenderLink()
	{
		shouldRenderLink = false;
		if (this.section != null && this.section.getContentType() != null)
		{
			shouldRenderLink = this.section.getContentType().equals("typeLink");
		}
		return shouldRenderLink;
	}

	/**
	 * Set flag to render select/replace LTI's
	 * 
	 * @return
	 */
	public boolean getShouldRenderLTI()
	{
		shouldRenderLTI = false;
		if (this.section != null && this.section.getContentType() != null)
		{
			shouldRenderLTI = this.section.getContentType().equals("typeLTI");
		}
		return shouldRenderLTI;
	}

	/**
	 * Set flag to render select/replace Uploads
	 * 
	 * @return
	 */
	public boolean getShouldRenderUpload()
	{
		if (this.section != null && this.section.getContentType() != null)
		{
			shouldRenderUpload = this.section.getContentType().equals("typeUpload");
		}
		return shouldRenderUpload;
	}

	/**
	 * @return Returns the shouldRenderNotype.
	 */
	public boolean isShouldRenderNotype()
	{
		if (this.section != null && this.section.getContentType() != null)
		{
			shouldRenderNotype = this.section.getContentType().equals("notype");
		}
		return shouldRenderNotype;
	}

	/**
	 * Check if the composed html has head/form tags.
	 * 
	 * @return
	 */
	public Boolean getContentWithHtml()
	{
		return contentWithHtml;
	}

	/**
	 * @return module if module is not set, get the module from session.
	 * 
	 */
	public ModuleObjService getModule()
	{
		if (module == null && section != null && section.getModule() != null)
		{
			module = (Module) section.getModule();
		}

		// fetch from db if unable to get in the previous statement
		if (module == null && section != null)
		{
			module = moduleService.getModule(section.getModuleId());
		}
		if (module == null) logger.info("Edit Section Page : get Module method fails to get module for the section" + section);
		
		return module;
	}

	public void setModule(ModuleObjService module)
	{
		this.module = module;
	}

	/**
	 * @return section. create a new instance of section and assign default values.
	 * 
	 */
	public SectionObjService getSection()
	{
		FacesContext context = FacesContext.getCurrentInstance();
	
		if (null == this.section)
		{
			try
			{
		/*	if (logger.isDebugEnabled()) logger.info("get section is null in edit page");
			ResourceLoader bundle = new ResourceLoader("org.etudes.tool.melete.bundle.Messages");
			String errMsg = bundle.getString("add_section_load_error");
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "add_section_load_error", errMsg));
			context.getExternalContext().redirect("list_auth_modules.jsf");	*/
			}
			catch (Exception e)
			{
				logger.debug("get section is null in edit page" + e.getMessage());
			}
		}

		return this.section;
	}

	/**
	 * set section.
	 * 
	 * @param sec
	 *        SectionObjService
	 */
	public void setSection(SectionObjService sec)
	{
		try
		{
			this.section = null;
			if (sec != null)
			{
				if (logger.isDebugEnabled()) logger.debug("setSection called and section is not null" + sec.getSectionId());
				this.module = (Module) sec.getModule();
				this.section = sec;
			}
		}
		catch (Exception ex)
		{
			logger.debug(ex.toString());
		}
	}

	/**
	 * added to set module null.
	 */
	public void setModuleNull()
	{
		this.module = null;
	}

	/**
	 * @param contentEditor
	 * 
	 */
	public void setContentEditor(String contentEditor)
	{
		this.contentEditor = contentEditor;
	}

	/**
	 * Set license information.
	 */
	protected void setLicenseInfo()
	{
		FacesContext context = FacesContext.getCurrentInstance();

		ValueBinding licBinding = Util.getBinding("#{licensePage}");
		LicensePage lPage = (LicensePage) licBinding.getValue(context);
		lPage.setFormName(this.formName);
		lPage.resetValues();

		ValueBinding authBinding = Util.getBinding("#{authorPreferences}");
		AuthorPreferencePage preferencePage = (AuthorPreferencePage) authBinding.getValue(context);
		MeleteUserPreference mup = preferencePage.getMup();
		lPage.setInitialValues(this.formName, mup);

		// The code below is required because the setter for the license code kicks in by default
		// and we need to actually set the component with the values determined above.(ME-1071)
		UIComponent licComp = context.getViewRoot().findComponent("EditSectionForm");
		if (licComp != null && licComp.findComponent("ResourcePropertiesPanel") != null
				&& licComp.findComponent("ResourcePropertiesPanel").findComponent("LicenseForm") != null
				&& licComp.findComponent("ResourcePropertiesPanel").findComponent("LicenseForm").findComponent("SectionView") != null)
		{
			licComp = licComp.findComponent("ResourcePropertiesPanel").findComponent("LicenseForm").findComponent("SectionView");
			UIInput uiInp = (UIInput) licComp.findComponent("licenseCodes");
			uiInp.setValue(lPage.getLicenseCodes());
		}
	}

	/**
	 * @param event
	 * @throws AbortProcessingException
	 *         Changes the LTI view from basic to advanced.
	 */
	public void toggleLTIDisplay(ValueChangeEvent event) throws AbortProcessingException
	{
		// Nothing to do - because the setter handles it all
		UIInput LTITypeRadio = (UIInput) event.getComponent();
		shouldLTIDisplayAdvanced = LTITypeRadio.getValue().equals("Advanced");
	}

	/**
	 * 
	 * @return
	 */
	public String getLTIDisplay()
	{
		if (shouldLTIDisplayAdvanced) return "Advanced";
		return "Basic";
	}

	/**
	 * 
	 * @param newDisplay
	 */
	public void setLTIDisplay(String newDisplay)
	{
		shouldLTIDisplayAdvanced = "Advanced".equals(newDisplay);
	}

	/**
	 * Get LTI content location URL
	 * 
	 * @return
	 */
	public String getLTIUrl()
	{
		return currLTIUrl;
	}

	/**
	 * Set LTI content location URL
	 * 
	 * @param LTIUrl
	 */
	public void setLTIUrl(String LTIUrl)
	{
		currLTIUrl = LTIUrl;
		fixDescriptor();
	}

	/**
	 * get LTI key
	 * 
	 * @return
	 */
	public String getLTIKey()
	{
		return currLTIKey;
	}

	/**
	 * Set LTI key
	 * 
	 * @param LTIKey
	 */
	public void setLTIKey(String LTIKey)
	{
		currLTIKey = LTIKey;
		fixDescriptor();
	}

	/**
	 * get LTI passowrd
	 * 
	 * @return
	 */
	public String getLTIPassword()
	{
		return currLTIPassword;
	}

	/**
	 * Set LTI content password
	 * 
	 * @param LTIPassword
	 */
	public void setLTIPassword(String LTIPassword)
	{
		currLTIPassword = LTIPassword;
		fixDescriptor();
	}

	/**
	 * Produce a basic descriptor from the URL and Password
	 */
	private void fixDescriptor()
	{
		if (currLTIUrl == null) return;
		String desc = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" + "<basic_lti_link\n"
		+ "     xmlns=\"http://www.imsglobal.org/xsd/imsbasiclti_v1p0\"\n"
		+ "     xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n" + "  <melete-basic>true</melete-basic> \n" + "  <launch_url>"
		+ currLTIUrl + "</launch_url> \n" + "  <x-secure>\n";
		if (currLTIKey != null && currLTIKey.trim().length() > 0)
		{
			desc = desc + "    <launch_key>" + currLTIKey + "</launch_key> \n";
		}
		if (currLTIPassword != null && currLTIPassword.trim().length() > 0)
		{
			desc = desc + "    <launch_secret>" + currLTIPassword + "</launch_secret> \n";
		}
		desc = desc + "  </x-secure>\n";
		desc = desc + "</basic_lti_link>\n";
		setLTIDescriptor(desc);
	}

	/**
	 * 
	 * @return
	 */
	public boolean getShouldLTIDisplayAdvanced()
	{
		return shouldLTIDisplayAdvanced;
	}

	/**
	 * 
	 * @return
	 */
	public boolean getShouldLTIDisplayBasic()
	{
		return !shouldLTIDisplayAdvanced;
	}

	/**
	 * modality is required. check if one is selected or not
	 */
	protected boolean validateModality()
	{
		if (!(this.section.isAudioContent() || this.section.isTextualContent() || this.section.isVideoContent())) return false;
		return true;
	}

	/**
	 * adds resource to specified melete module or uploads collection.
	 * 
	 * @param addCollId
	 *        Collection Id
	 */
	public String addResourceToMeleteCollection(String addCollId) throws UserErrorException, MeleteException
	{
		try
		{
			String res_mime_type = MeleteCHService.MIME_TYPE_EDITOR;
			boolean encodingFlag = false;

			if (section.getContentType().equals("typeEditor"))
			{
				contentEditor = getMeleteCHService().findLocalImagesEmbeddedInEditor(ToolManager.getCurrentPlacement().getContext(),
						new ArrayList<String>(), null, contentEditor);

				res_mime_type = MeleteCHService.MIME_TYPE_EDITOR;
				secContentData = new byte[contentEditor.length()];
				secContentData = contentEditor.getBytes();
				encodingFlag = true;
				secResourceName = getMeleteCHService().getTypeEditorSectionName(section.getSectionId());
				secResourceDescription = "compose content";
			}

			if (section.getContentType().equals("typeLink"))
			{
				res_mime_type = MeleteCHService.MIME_TYPE_LINK;
				Util.validateLink(getLinkUrl());
				if ((secResourceName == null) || (secResourceName.trim().length() == 0)) throw new MeleteException("URL_title_reqd");
				if (secResourceName.trim().length() > SectionService.MAX_URL_LENGTH)
					secResourceName = secResourceName.substring(0, SectionService.MAX_URL_LENGTH);
				secContentData = new byte[linkUrl.length()];
				secContentData = linkUrl.getBytes();
			}
			if (section.getContentType().equals("typeLTI"))
			{
				if (getLTIUrl() != null) Util.validateLink(getLTIUrl());

				if (ltiDescriptor == null || ltiDescriptor.trim().length() == 0)
				{
					throw new MeleteException("add_section_empty_lti");
				}
				if (BasicLTIUtil.validateDescriptor(ltiDescriptor) != null)
				{
					throw new MeleteException("add_section_bad_lti");
				}
				res_mime_type = MeleteCHService.MIME_TYPE_LTI;
				secContentData = new byte[ltiDescriptor.length()];
				secContentData = ltiDescriptor.getBytes();
			}
			ResourcePropertiesEdit res = getMeleteCHService().fillInSectionResourceProperties(encodingFlag, secResourceName, secResourceDescription);

			String newResourceId = getMeleteCHService().addResourceItem(secResourceName, res_mime_type, addCollId, secContentData, res);
			return newResourceId;
		}
		catch (UserErrorException uex)
		{
			throw uex;
		}
		catch (MeleteException me)
		{
			logger.debug("error in creating resource for section content" + me.toString());
			throw me;
		}
		catch (Exception e)
		{
			if (logger.isDebugEnabled())
			{
				logger.debug("error in creating resource for section content" + e.toString());
				// e.printStackTrace();
			}
			throw new MeleteException("add_section_fail");
		}
	}

	/**
	 * adds resource to specified melete module or uploads collection. For sferyx, refreshes the editor contents by reading from sectionxxx.html file.
	 * 
	 * @param resourceId
	 *        The Resource Id
	 */
	public Boolean editMeleteCollectionResource(String resourceId) throws MeleteException
	{
		// if (logger.isDebugEnabled()) logger.debug("edit resource function");
		Boolean modify = null;
		try
		{
			// if (logger.isDebugEnabled()) logger.debug("editing properties for " + resourceId);

			FacesContext context = FacesContext.getCurrentInstance();
			ValueBinding binding = Util.getBinding("#{authorPreferences}");
			AuthorPreferencePage authPage = (AuthorPreferencePage) binding.getValue(context);
			authPage.setEditorFlags();
			// for fck editor normal processing
			if (section.getContentType().equals("typeEditor") && authPage.isShouldRenderFCK())
			{
				try
				{
					String contentData = getMeleteCHService().findLocalImagesEmbeddedInEditor(ToolManager.getCurrentPlacement().getContext(),
							new ArrayList<String>(), null, contentEditor);
					if (contentData != null) contentEditor = contentData;
				}
				catch (MeleteException me)
				{
					// uncomment if we want to save section contents before throwing exception
					// getMeleteCHService().editResource(resourceId, contentEditor);
					throw me;
				}
				getMeleteCHService().editResource(resourceId, contentEditor);
				modify = isComposeDataEdited;
			}
			// sferyx saves thru save.jsp
			else if (section.getContentType().equals("typeEditor") && authPage.isShouldRenderSferyx())
			{
				if (resourceId == null) throw new MeleteException("resource_null");
				ContentResource cr = getMeleteCHService().getResource(resourceId);
				if (cr != null) 
				{
					this.contentEditor = HtmlHelper.clean(new String(cr.getContent()), false);
				}
			}

			if (resourceId != null
					&& (section.getContentType().equals("typeLink") || section.getContentType().equals("typeUpload") || section.getContentType()
							.equals("typeLTI")))
			{				
				modify = getMeleteCHService().editResourceProperties(resourceId, secResourceName, secResourceDescription);
				logger.debug("modify :" + modify);
			}
		}
		catch (MeleteException me)
		{
			logger.debug("error in editing resource for section content" + me.toString());
			throw me;
		}
		catch (Exception e)
		{
			if (logger.isDebugEnabled())
			{
				logger.debug("error in editing resource for section content" + e.toString());
				e.printStackTrace();
			}
			throw new MeleteException("add_section_fail");
		}

		return modify;
	}

	/**
	 * Abstract class. Add /edit section calls their own save methods.
	 * 
	 * @return
	 */
	public abstract String saveHere();

	/**
	 * listener to set action for save button for setting data from html editor
	 */
	public void saveListener(ActionEvent event)
	{
	
	}

	/**
	 * Render instructions on preview page
	 */
	public boolean getRenderInstr()
	{
		if (this.section == null || this.section.getInstr() == null || this.section.getInstr().length() == 0)
			renderInstr = false;
		else
			renderInstr = true;
		return renderInstr;
	}

	/**
	 * Reset Values.
	 */
	public void resetSectionValues()
	{
		this.section = null;
		contentEditor = null;
		setSuccess(false);
	
		checkUploadChange = null;
	
		secResource = null;
		secResourceName = null;
		secResourceDescription = null;
		secContentData = null;
		selResourceIdFromList = null;
		meleteResource = null;
		setLicenseCodes(null);
		linkUrl = null;
		ltiDescriptor = null;
		FCK_CollId = null;
		currLinkUrl = null;
		currLTIDescriptor = null;
		currLTIUrl = null;
		currLTIKey = null;
		currLTIPassword = null;
		displayCurrLink = null;
		FacesContext ctx = FacesContext.getCurrentInstance();
		ValueBinding binding = Util.getBinding("#{remoteBrowserFile}");
		RemoteBrowserFile rbPage = (RemoteBrowserFile) binding.getValue(ctx);
		if (rbPage != null)
		{
			rbPage.setRemoteFiles(null);
			rbPage.setRemoteLinkFiles(null);
		}

		binding = Util.getBinding("#{authorPreferences}");
		AuthorPreferencePage preferencePage = (AuthorPreferencePage) binding.getValue(ctx);
		preferencePage.setDisplaySferyx(false);
		shouldRenderEditor = false;
		shouldRenderLink = false;
		shouldRenderLTI = false;
		shouldRenderUpload = false;
		shouldRenderNotype = false;
		allContentTypes = null;
		oldType = null;
		contentWithHtml = null;
	
	}

	/**
	 * reset resource values and license from java cache when its deleted and associated with current section
	 */
	public void resetMeleteResourceValues()
	{
		currLinkUrl = null;
		currLTIDescriptor = null;
		currLTIUrl = null;
		currLTIKey = null;
		currLTIPassword = null;
		displayCurrLink = null;
		secResourceName = null;
		secResourceDescription = null;
		setLicenseCodes(null);
	}

	/**
	 * Process the selected resource.
	 * 
	 * @param selResourceIdFromList
	 *        The user selected resource Id
	 */
	protected void processSelectedResource(String selResourceIdFromList)
	{
		FacesContext ctx = FacesContext.getCurrentInstance();
		try
		{
			currLinkUrl = getLinkContent(selResourceIdFromList);
			ContentResource cr = getMeleteCHService().getResource(selResourceIdFromList);
			this.secResourceName = cr.getProperties().getProperty(ResourceProperties.PROP_DISPLAY_NAME);
			this.secResourceDescription = cr.getProperties().getProperty(ResourceProperties.PROP_DESCRIPTION);

			// get resource object
			selectedResource = (MeleteResource) sectionService.getMeleteResource(selResourceIdFromList);
			// just take resource properties from this object as its assoc with another section
			if (selectedResource != null)
			{
				meleteResource = selectedResource;
				ValueBinding binding = Util.getBinding("#{licensePage}");

				LicensePage lPage = (LicensePage) binding.getValue(ctx);
				lPage.setInitialValues(formName, section.getSectionId().toString(), selectedResource);

				// render selected file name
				selectedResourceName = secResourceName;
				// if (logger.isDebugEnabled()) logger.debug("values changed in resource action for res name and desc" + secResourceName + secResourceDescription);
			}
		}
		catch (Exception ex)
		{
			logger.debug("error while accessing content resource" + ex.toString());
		}
	}

	/**
	 * @return remove values from session before closing
	 */
	public String cancel()
	{
		FacesContext context = FacesContext.getCurrentInstance();
		Map<?, ?> sessionMap = context.getExternalContext().getSessionMap();
		if (sessionMap.containsKey("currModule"))
		{
			sessionMap.remove("currModule");
		}
		resetSectionValues();

		ValueBinding binding = Util.getBinding("#{listAuthModulesPage}");
		ListAuthModulesPage lPage = (ListAuthModulesPage) binding.getValue(context);
		lPage.resetValues();
		return "list_auth_modules";
	}

	/**
	 * @return uploaded file or link name for preview page
	 */
	public String getContentLink()
	{
		// if (logger.isDebugEnabled()) logger.debug("getContentLink fn is called");
		return "#";
	}

	/**
	 * Get the form name
	 * 
	 * @return
	 */
	public String getFormName()
	{
		return formName;
	}

	/**
	 * @param formName
	 */
	public void setFormName(String formName)
	{
		this.formName = formName;
	}

	/**
	 * @param access
	 *        The access to set.
	 */
	public void setAccess(String access)
	{
		this.access = access;
	}

	/**
	 * @return Returns the access.
	 */
	public String getAccess()
	{
		if (this.access == null) this.access = "site";
		return access;
	}

	/**
	 * Set the license code
	 * 
	 * @param licenseCodes
	 */
	public void setLicenseCodes(String licenseCodes)
	{
		FacesContext context = FacesContext.getCurrentInstance();
		ValueBinding binding = Util.getBinding("#{licensePage}");
		LicensePage lPage = (LicensePage) binding.getValue(context);
		lPage.setFormName(formName);
		lPage.setLicenseCodes(licenseCodes);
	}

	/**
	 * @return Returns the linkUrl.
	 */
	public String getLinkUrl()
	{
		if (linkUrl == null) linkUrl = "http://";
		return linkUrl;
	}

	/**
	 * @param linkUrl
	 *        The linkUrl to set. as from section table we will remove link,contentpath and uploadpath fields. This will get stored in resources.
	 */
	public void setLinkUrl(String linkUrl)
	{
		this.linkUrl = linkUrl;
	}

	/**
	 * @return Returns the ltiDescriptor.
	 */
	public String getLTIDescriptor()
	{
	//	if (ltiDescriptor == null) ltiDescriptor = "";
		return ltiDescriptor;
	}

	/**
	 * @param ltiDescriptor
	 *        The ltiDescriptor to set. as from section table we will remove link,contentpath and uploadpath fields. This will get stored in resources.
	 */
	public void setLTIDescriptor(String ltiDescriptor)
	{
		this.ltiDescriptor = ltiDescriptor;
	}

	/**
	 * get material from the new provided link
	 */
	public void createLinkUrl()
	{
		if (secResourceName == null || secResourceName.length() == 0) secResourceName = linkUrl;
		secContentData = new byte[getLinkUrl().length()];
		secContentData = getLinkUrl().getBytes();
	}

	/**
	 * get material from the new provided Descriptor
	 */
	public void createLTIDescriptor()
	{
		if (secResourceName == null || secResourceName.length() == 0) secResourceName = "create name in createLTIDescriptor";
		secContentData = new byte[getLTIDescriptor().length()];
		secContentData = getLTIDescriptor().getBytes();
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
	 * @param serverConfigurationService
	 *        The ServerConfigurationService to set.
	 */
	public void setServerConfigurationService(ServerConfigurationService serverConfigurationService)
	{
		this.serverConfigurationService = serverConfigurationService;
	}

	/**
	 * @return Returns the secResource.
	 */
	public SectionResourceService getSecResource()
	{
		if (null == this.secResource || (this.section != null && this.section.getSectionResource() == null))
		{
			this.secResource = new SectionResource();
		}
		else
			this.secResource = this.section.getSectionResource();
		return secResource;
	}

	/**
	 * @param secResource
	 *        The secResource to set.
	 */
	public void setSecResource(SectionResourceService secResource)
	{
		this.secResource = secResource;
	}

	/**
	 * @return Returns the nullString.
	 */
	public String getNullString()
	{
		return nullString;
	}

	/**
	 * @return Returns the secResourceDescription.
	 */
	public String getSecResourceDescription()
	{
		try
		{
			if (meleteResource != null && meleteResource.getResourceId() != null)
			{
				ContentResource cr = getMeleteCHService().getResource(meleteResource.getResourceId());
				secResourceDescription = (String) cr.getProperties().get(ResourceProperties.PROP_DESCRIPTION);
			}
		}
		catch (Exception e)
		{

		}
		return secResourceDescription;
	}

	/**
	 * @param secResourceDescription
	 *        The secResourceDescription to set.
	 */
	public void setSecResourceDescription(String secResourceDescription)
	{
		this.secResourceDescription = secResourceDescription;
	}

	/**
	 * @return Returns the secResourceName.
	 */
	public String getSecResourceName()
	{
		try
		{
			if (meleteResource != null && meleteResource.getResourceId() != null)
			{
				ContentResource cr = meleteCHService.getResource(meleteResource.getResourceId());
				secResourceName = cr.getProperties().getProperty(ResourceProperties.PROP_DISPLAY_NAME);
			}
		}
		catch (Exception e)
		{

		}
		return secResourceName;
	}

	/**
	 * @param secResourceName
	 *        The secResourceName to set.
	 */
	public void setSecResourceName(String secResourceName)
	{
		this.secResourceName = secResourceName;
	}

	/**
	 * @return Returns the secContentData.
	 */
	public byte[] getSecContentData()
	{
		return secContentData;
	}

	/**
	 * Get preview data
	 * 
	 * @return
	 */
	public String getPreviewContentData()
	{		
		try
		{
		contentWithHtml = false;
		previewContentData = "";
		
		if (meleteResource != null && meleteResource.getResourceId() != null)
		   {
			   if (this.section.getContentType().equals("typeEditor"))
			   {
				   this.previewContentData = contentEditor;
				   if(Util.FindNestedHTMLTags(contentEditor))
				   {
					   this.previewContentData = getMeleteCHService().getResourceUrl(meleteResource.getResourceId());
					   contentWithHtml = true;
				   }
			   }
			   else this.previewContentData = getMeleteCHService().getResourceUrl(meleteResource.getResourceId());
		   }
		} catch(Exception e)
		{
			e.printStackTrace();
		}
		return previewContentData;
	}

	/**
	 * @return Returns the meleteCHService.
	 */
	public MeleteCHService getMeleteCHService()
	{
		return meleteCHService;
	}

	/**
	 * Set MeleteCHService service
	 * 
	 * @param meleteCHService
	 */
	public void setMeleteCHService(MeleteCHService meleteCHService)
	{
		this.meleteCHService = meleteCHService;
	}

	/**
	 * @return Returns the contentservice.
	 */
	public ContentHostingService getContentservice()
	{
		return contentservice;
	}

	/**
	 * @param contentservice
	 *        The contentservice to set.
	 */
	public void setContentservice(ContentHostingService contentservice)
	{
		this.contentservice = contentservice;
	}
	
	/**
	 * @param meleteResource
	 *        The meleteResource to set.
	 */
	public void setMeleteResource(MeleteResource meleteResource)
	{
		this.meleteResource = meleteResource;
	}

	/**
	 * Set the base collection for FCK Editor. It shows all resources of the site's meletedocs uploads collection.
	 */
	public void setFCKCollectionAttrib()
	{
		FCK_CollId = getMeleteCHService().getUploadCollectionId(getCurrentCourseId());
		String attrb = "fck.security.advisor." + FCK_CollId;
		
		SessionManager.getCurrentSession().setAttribute("ck.collectionId",FCK_CollId);
		SessionManager.getCurrentSession().setAttribute(attrb, new SecurityAdvisor()
		{
			public SecurityAdvice isAllowed(String userId, String function, String reference)
			{
				try
				{
					Collection<?> meleteGrpAllow = org.sakaiproject.authz.cover.AuthzGroupService.getAuthzGroupsIsAllowed(userId, "melete.author",
							null);

					String anotherRef = new String(reference);
					anotherRef = anotherRef.replace("/content/private/meleteDocs", "/site");
					org.sakaiproject.entity.api.Reference ref1 = org.sakaiproject.entity.cover.EntityManager.newReference(anotherRef);

					if (meleteGrpAllow.contains("/site/" + ref1.getContainer()))
					{
						return SecurityAdvice.ALLOWED;
					}
				}
				catch (Exception e)
				{
					logger.warn("exception in setting security advice for FCK collection" + e.toString());
					return SecurityAdvice.NOT_ALLOWED;
				}
				return SecurityAdvice.NOT_ALLOWED;
			}
		});
	}

	/**
	 * @return Returns the fCK_CollId.
	 */
	public String getFCK_CollId()
	{
		return FCK_CollId;
	}

	/**
	 * @return Returns the selResourceIdFromList.
	 */
	public String getSelResourceIdFromList()
	{
		FacesContext ctx = FacesContext.getCurrentInstance();
		ValueBinding binding = Util.getBinding("#{listResourcesPage}");
		ListResourcesPage listResPage = (ListResourcesPage) binding.getValue(ctx);
		selResourceIdFromList = listResPage.getSelResourceIdFromList();
		return selResourceIdFromList;
	}

	/**
	 * Get the display link url. It adds ... after 50 characters.
	 * 
	 * @return
	 */
	public String getDisplayCurrLink()
	{
		String retval = currLinkUrl;

		if (retval != null && retval.length() > 50)
			displayCurrLink = retval.substring(0, 50) + "...";
		else
			displayCurrLink = retval;

		return displayCurrLink;
	}

	/**
	 * Get the display LTI url. It adds ... after 50 characters.
	 * 
	 * @return
	 */
	public String getDisplayCurrLTI()
	{
		String retval = secResourceName;

		if (retval != null && retval.length() > 50)
			displayCurrLink = retval.substring(0, 50) + "...";
		else
			displayCurrLink = retval;

		return displayCurrLink;
	}

	/**
	 * @return Returns the currLinkUrl.
	 */
	public String getCurrLinkUrl()
	{
		if (!(getLinkUrl().equals("http://") || getLinkUrl().equals("https://"))) currLinkUrl = getLinkUrl();
		return currLinkUrl;
	}

	/**
	 * Get the LTI URL.
	 * 
	 * @return
	 */
	public String getCurrLTIUrl()
	{
		if (meleteResource != null && meleteResource.getResourceId() != null)
		{
			try
			{
				String rUrl = getMeleteCHService().getResourceUrl(meleteResource.getResourceId());
				return rUrl;
			}
			catch (Exception e)
			{
				return "about:blank";
			}
		}
		return "about:blank";
	}

	/**
	 * @param currLinkUrl
	 *        The currLinkUrl to set.
	 */
	public void setCurrLinkUrl(String currLinkUrl)
	{
		this.currLinkUrl = currLinkUrl;
	}

	/**
	 * @return Returns the currLTIDescriptor.
	 */
	public String getCurrLTIDescriptor()
	{
		if (!(getLTIDescriptor().equals("http://") || getLTIDescriptor().equals("https://"))) currLTIDescriptor = getLTIDescriptor();
		return currLTIDescriptor;
	}

	/**
	 * @param currLTIDescriptor
	 *        The currLTIDescriptor to set.
	 */
	public void setCurrLTIDescriptor(String currLTIDescriptor)
	{
		this.currLTIDescriptor = currLTIDescriptor;
	}

	public void setShouldLTIDisplayAdvanced(boolean shouldLTIDisplayAdvanced)
	{
		this.shouldLTIDisplayAdvanced = shouldLTIDisplayAdvanced;
	}

	/**
	 * @return Returns the selectedResource.
	 */
	public MeleteResource getSelectedResource()
	{
		if (selectedResource == null) selectedResource = new MeleteResource();
		return selectedResource;
	}

	/**
	 * @param selectedResource
	 *        The selectedResource to set.
	 */
	public void setSelectedResource(MeleteResource selectedResource)
	{
		this.selectedResource = selectedResource;
	}

	/**
	 * @return Returns the selectedResourceDescription.
	 */
	public String getSelectedResourceDescription()
	{
		return selectedResourceDescription;
	}

	/**
	 * @param selectedResourceDescription
	 *        The selectedResourceDescription to set.
	 */
	public void setSelectedResourceDescription(String selectedResourceDescription)
	{
		this.selectedResourceDescription = selectedResourceDescription;
	}

	/**
	 * @return Returns the selectedResourceName.
	 */
	public String getSelectedResourceName()
	{
		return selectedResourceName;
	}

	/**
	 * @param selectedResourceName
	 *        The selectedResourceName to set.
	 */
	public void setSelectedResourceName(String selectedResourceName)
	{
		this.selectedResourceName = selectedResourceName;
	}

	/**
	 * @return the newURLTitle
	 */
	public String getNewURLTitle()
	{
		return this.newURLTitle;
	}

	/**
	 * @param newURLTitle
	 *        the newURLTitle to set
	 */
	public void setNewURLTitle(String newURLTitle)
	{
		this.newURLTitle = newURLTitle;
	}

	/**
	 * @return the newLTIDescriptor
	 */
	public String getNewLTIDescriptor()
	{
		return this.newLTIDescriptor;
	}

	/**
	 * @return the newLTIDescriptor to set
	 */
	public void setNewLTIDescriptor(String newLTIDescriptor)
	{
		this.newLTIDescriptor = newLTIDescriptor;
	}

	/**
	 * Get all section content types. Includes LTI only if user requested it in Preferences.
	 * 
	 * @return
	 */
	public List<SelectItem> getAllContentTypes()
	{
		FacesContext context = FacesContext.getCurrentInstance();
		ResourceLoader bundle = new ResourceLoader("org.etudes.tool.melete.bundle.Messages");
		if (allContentTypes == null)
		{
			String userId = getCurrUserId();

			ValueBinding binding = Util.getBinding("#{authorPreferences}");
			AuthorPreferencePage preferencePage = (AuthorPreferencePage) binding.getValue(context);
			boolean userLTIChoice = preferencePage.getUserLTIChoice(userId);

			allContentTypes = new ArrayList<SelectItem>(0);
			String notypemsg = bundle.getString("addmodulesections_choose_one");
			allContentTypes.add(new SelectItem("notype", notypemsg));
			String typeEditormsg = bundle.getString("addmodulesections_compose");
			allContentTypes.add(new SelectItem("typeEditor", typeEditormsg));
			String typeUploadmsg = bundle.getString("addmodulesections_upload_local");
			allContentTypes.add(new SelectItem("typeUpload", typeUploadmsg));
			String typeLinkmsg = bundle.getString("addmodulesections_link_url");
			allContentTypes.add(new SelectItem("typeLink", typeLinkmsg));
			if (userLTIChoice)
			{
				String typeLTImsg = bundle.getString("addmodulesections_lti");
				allContentTypes.add(new SelectItem("typeLTI", typeLTImsg));
			}
		}
		return allContentTypes;
	}

	/**
	 * Get the resource's display name
	 * 
	 * @param resourceId
	 * @return
	 */
	protected String getDisplayName(String resourceId)
	{
		return getMeleteCHService().getDisplayName(resourceId);
	}

	/**
	 * Get the link type's resource url/content
	 * 
	 * @param resourceId
	 * @return
	 */
	protected String getLinkContent(String resourceId)
	{
		return getMeleteCHService().getLinkContent(resourceId);
	}

	/**
	 * Get the current user Id
	 * 
	 * @return
	 */
	public String getCurrUserId()
	{
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

	/**
	 * Set the user Id
	 * 
	 * @param currUserId
	 */
	public void setCurrUserId(String currUserId)
	{
		this.currUserId = currUserId;
	}

	/*
	 * get the current course id
	 * Pass it to getuploads collection method
	 */
	protected String getCurrentCourseId()
	{
		String currId = "";
		FacesContext context = FacesContext.getCurrentInstance();
		Map<?,?> sessionMap = context.getExternalContext().getSessionMap();
		currId = (String)sessionMap.get("course_id");
		if(currId == null || currId.length() == 0)
		{
			ValueBinding binding = Util.getBinding("#{meleteSiteAndUserInfo}");
			MeleteSiteAndUserInfo info = (MeleteSiteAndUserInfo) binding.getValue(context);
			currId = info.getCourse_id();
		}
		return currId;
	}
	
	/*
	 * 
	 * 
	 */
	public Boolean getIsComposeDataEdited()
	{
		return isComposeDataEdited;
	}

	/**
	 * 
	 * @param isEdited
	 */
	public void setIsComposeDataEdited(Boolean isComposeDataEdited)
	{
		this.isComposeDataEdited = isComposeDataEdited;
	}
}
