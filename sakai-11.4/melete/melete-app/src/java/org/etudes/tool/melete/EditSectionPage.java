/**********************************************************************************
 *
 * $URL: https://source.sakaiproject.org/contrib/etudes/melete/tags/2.9.9/melete-app/src/java/org/etudes/tool/melete/EditSectionPage.java $
 * $Id: EditSectionPage.java 87125 2014-10-16 19:48:52Z mallika@etudes.org $
 ***********************************************************************************
 * Copyright (c) 2008,2009, 2010, 2011, 2012, 2014 Etudes, Inc.
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

import java.util.*;
import java.io.Serializable;
import javax.faces.context.FacesContext;
import javax.faces.application.FacesMessage;
import javax.faces.component.*;
import javax.faces.el.ValueBinding;
import javax.faces.event.*;

import org.etudes.component.app.melete.MeleteResource;
import org.etudes.component.app.melete.MeleteUserPreference;
import org.etudes.component.app.melete.Section;
import org.etudes.component.app.melete.SectionResource;
import org.etudes.util.HtmlHelper;

import org.etudes.api.app.melete.MeleteCHService;
import org.etudes.api.app.melete.MeleteResourceService;
import org.etudes.api.app.melete.ModuleService;
import org.etudes.api.app.melete.SectionObjService;
import org.etudes.api.app.melete.SectionService;
import org.etudes.api.app.melete.exception.MeleteException;
import org.etudes.api.app.melete.exception.UserErrorException;
import org.imsglobal.basiclti.BasicLTIUtil;

import org.sakaiproject.component.cover.ServerConfigurationService;
import org.sakaiproject.content.api.ContentResource;
import org.sakaiproject.content.cover.ContentTypeImageService;

import org.sakaiproject.entity.api.ResourceProperties;
import org.sakaiproject.entity.api.ResourcePropertiesEdit;
import org.sakaiproject.tool.cover.ToolManager;
import org.sakaiproject.user.api.User;
import org.sakaiproject.user.cover.UserDirectoryService;
import org.sakaiproject.util.ResourceLoader;

import org.sakaiproject.event.cover.EventTrackingService;

import java.nio.file.Files; 
import java.nio.file.Paths;

public class EditSectionPage extends SectionPage implements Serializable
{
	private boolean sizeWarning = false;

	private ModuleService moduleService;

	private boolean shouldRenderContentTypeSelect = false;

	// picking from server
	private String editSelection;

	private String containCollectionId;

	private boolean shouldRenderServerResources = false;

	private boolean shouldRenderLocalUpload = false;

	private Boolean hasNext = true;

	private Boolean hasPrev = true;

	private String editId;
	
	private long lastSavedAt;

	private String createdByAuthor;
	
	private String modifiedByAuthor;
	
	private Boolean httpAddressAlert;
	
	private String rgif;
	
	private String emptyString = "";
	
	private String activeCheckUrl = "";
	
	private String resourceSectionFile = "";

	
	/**
	 * Default constructor
	 */
	public EditSectionPage()
	{
		logger.debug("EditSectionPage CONSTRUCTOR CALLED");
		setFormName("EditSectionForm");
		createdByAuthor = "";
		modifiedByAuthor = "";
	}

	/**
	 * initializes values.
	 * 
	 * @param section1
	 *        SectionObjService
	 * @return
	 */
	public String setEditInfo(SectionObjService section1)
	{
		FacesContext context = FacesContext.getCurrentInstance();

		// resetSectionValues();
		try
		{
			setFormName("EditSectionForm");
			if (editId == null) editId = section1.getSectionId().toString();
			setSection(sectionService.getSection(section1.getSectionId()));
			lastSavedAt = ((Date) this.section.getModificationDate()).getTime();
			setModule(moduleService.getModule(section1.getModuleId()));
			// setSecResource(this.section.getSectionResource());
			setSecResource(sectionService.getSectionResourcebyId(this.section.getSectionId().toString()));
			resourceSectionFile = "Section_" + editId + ".html";
		}
		catch (Exception e)
		{
			try
			{
				FacesContext.getCurrentInstance().getExternalContext().redirect("list_auth_modules");
			}
			catch (Exception ex)
			{
			}
		}

		ValueBinding binding = Util.getBinding("#{licensePage}");
		LicensePage lPage = (LicensePage) binding.getValue(context);

		if (this.secResource != null && this.secResource.getResource() != null)
		{
			// setMeleteResource((MeleteResource) this.secResource.getResource());
			setMeleteResource((MeleteResource) sectionService.getMeleteResource(secResource.getResource().getResourceId()));
			if (this.meleteResource != null && this.meleteResource.getResourceId() != null && this.meleteResource.getResourceId().length() != 0)
				setContentResourceData(this.meleteResource.getResourceId());

			lPage.setInitialValues(this.formName, section1.getSectionId().toString(), getMeleteResource());

			// for incomplete add action and resource file is saved through intermediate save
			try
			{
				if (("notype").equals(this.section.getContentType()) && this.meleteResource != null && this.meleteResource.getResourceId() != null)
				{
					if (this.meleteResource.getResourceId().indexOf("Section_") != -1)
						this.section.setContentType("typeEditor");
					else
						this.section.setContentType("typeUpload");
					sectionService.insertSectionResource(this.section, this.meleteResource);
				}
			}
			catch (Exception ex)
			{
			}
		}
		else
		{
			setMeleteResource(null);
			setLicenseCodes(null);

			ValueBinding authBinding = Util.getBinding("#{authorPreferences}");
			AuthorPreferencePage preferencePage = (AuthorPreferencePage) authBinding.getValue(context);
			MeleteUserPreference mup = preferencePage.getMup(getCurrUserId());
			lPage.setInitialValues(this.formName, mup);
			shouldRenderContentTypeSelect = true;
		}
		setSuccess(false);
		try
		{
			hasPrev = sectionService.getPrevSection(section.getSectionId().toString(), module.getSeqXml()) != null ? true : false;
			hasNext = sectionService.getNextSection(section.getSectionId().toString(), module.getSeqXml()) != null ? true : false;
		}
		catch (Exception e)
		{

		}
		binding = Util.getBinding("#{authorPreferences}");
		AuthorPreferencePage preferencePage = (AuthorPreferencePage) binding.getValue(context);
		preferencePage.setEditorFlags();

		// if fck editor then push advisors and other config values
		if (this.section.getContentType().equals("typeEditor") && preferencePage.isShouldRenderFCK())
		{
			setFCKCollectionAttrib();
		}
		if (this.section.getContentType().equals("typeEditor") && preferencePage.isShouldRenderSferyx()) preferencePage.setDisplaySferyx(true);

		return "success";
	}

	/**
	 * returns the page to keep the session active
	 * @return
	 */
	public String getActiveCheckUrl()
	{
		activeCheckUrl = ServerConfigurationService.getServerUrl() + "/portal/tool/" + ToolManager.getCurrentPlacement().getId() + "/melete/activeUserCheck.jsp";
		return activeCheckUrl;
	}

	/**
	 * 
	 * @param activeCheckUrl
	 */
	public void setActiveCheckUrl(String activeCheckUrl)
	{
		this.activeCheckUrl = activeCheckUrl;
	}
	
	/**
	 * For checking the empty string
	 * @return
	 */
	public String getEmptyString()
	{
		return emptyString;
	}

	/**
	 * Get the gif file according to the file type
	 * @return
	 */
	public String getRgif()
	{
		return rgif;
	}

	/**
	 * @return sizeWarning render sizeWarning message if this flag is true
	 */
	public boolean getSizeWarning()
	{
		return this.sizeWarning;
	}

	/**
	 * @param sizeWarning
	 *        to set sizeWarning to true if section save is successful.
	 */
	public void setSizeWarning(boolean sizeWarning)
	{
		this.sizeWarning = sizeWarning;
	}

	/**
	 * set resource data.
	 * 
	 * @param resourceId
	 *        The resource Id
	 */
	private void setContentResourceData(String resourceId)
	{
	   try
		{
			if (resourceId != null)
			{
				ContentResource cr = getMeleteCHService().getResource(resourceId);
				if (cr == null) return;

				String serverUrl = ServerConfigurationService.getServerUrl();
				boolean rTypeLink = cr.getContentType().equals(MeleteCHService.MIME_TYPE_LINK);
				boolean rTypeLTI = cr.getContentType().equals(MeleteCHService.MIME_TYPE_LTI);
				rgif = serverUrl + "/library/image/sakai/url.gif";

				if (!rTypeLink && !rTypeLTI)
				{
					String contentextension = cr.getContentType();
					rgif = ContentTypeImageService.getContentTypeImage(contentextension);

					if (rgif.startsWith("sakai"))
						rgif = rgif.replace("sakai", (serverUrl + "/library/image/sakai"));
					else if (rgif.startsWith("/sakai")) rgif = rgif.replace("/sakai", (serverUrl + "/library/image/sakai"));
				}

				if (cr.getContentType().equals(MeleteCHService.MIME_TYPE_EDITOR))
				{
					try
					{
						this.contentEditor = HtmlHelper.clean(new String(cr.getContent()), false);
						this.contentEditor = getSectionService().fixXrefs(contentEditor, getCurrentCourseId());
						//mphahsm added this line to fix the failing of html rendering
						if (contentEditor.indexOf("<body>") >= 0)
                        {
							this.contentEditor = contentEditor.substring(contentEditor.indexOf("<body>") + 6, contentEditor.indexOf("</body>"));
                            System.out.println("setContentResourceData: With BODY elements but after removing them");
                        }
						System.out.println("setContentResourceData: Without BODY elements");
					}
					catch (Exception ex)
					{
						String failResourceSectionFile = sectionService.getSectionContentFile(resourceId);
						/*
						This is for local host
						failResourceSectionFile = "C:\\data\\sakai\\content" + failResourceSectionFile;
						failResourceSectionFile = failResourceSectionFile.replace("/", "\\");
						*/
						
						//This is for the server
						failResourceSectionFile = "/data/sakai/content" + failResourceSectionFile;
						
						String contents = new String(Files.readAllBytes(Paths.get(failResourceSectionFile)));
						if (contents.indexOf("<body>") >= 0)
						{
							String contentsCleaup = contents.substring(contents.indexOf("<body>") + 6, contents.indexOf("</body>"));
							this.contentEditor = HtmlHelper.clean(contentsCleaup, false);
							this.contentEditor = getSectionService().fixXrefs(contentEditor, getCurrentCourseId());
							System.out.println("setContentResourceData: EditSectionPage: After content exception: Completed successfully");
						}
					}
					
				}
				else if (rTypeLink)
				{
					setCurrLinkUrl(new String(cr.getContent()));
					setLinkUrl(new String(cr.getContent()));
				}
				else if (rTypeLTI && cr.getContent() != null)
				{
					String descriptor = new String(cr.getContent());
					Properties ltiProps = BasicLTIUtil.parseDescriptor(descriptor);
					this.currLTIUrl = (ltiProps.containsKey("launch_url")) ? ltiProps.getProperty("launch_url") : "";
					this.currLTIKey = (ltiProps.containsKey("key")) ? ltiProps.getProperty("key") : "";
					this.currLTIPassword = (ltiProps.containsKey("secret")) ? ltiProps.getProperty("secret") : "";

					StringBuffer customParams = new StringBuffer("");
					Set keys = ltiProps.keySet();
					for (Object k : keys)
					{
						String key = (String) k;
						if (key.startsWith("custom_"))
						{
							String value = ltiProps.getProperty(key);
							key = key.replaceAll("custom_", "");
							customParams.append(key + "=" + value+"\n");
						}
					}
					if (customParams.length() > 0) customParameters = customParams.toString();
				}

				this.secResourceName = cr.getProperties().getProperty(ResourceProperties.PROP_DISPLAY_NAME);
				this.secResourceDescription = cr.getProperties().getProperty(ResourceProperties.PROP_DESCRIPTION);
				containCollectionId = cr.getContainingCollection().getId();
			}
		}
		catch (Exception e)
		{
			logger.debug("error in reading resource properties in edit section" + e);
		}
	}

	/**
	 * Get composed section content. return content editor
	 */
	public String getContentEditor()
	{
		return this.contentEditor;
	}

	/**
	 * Checks if section has a content type.If not show the dropdown menu to pick a content type.
	 * @return
	 */
	public boolean isShouldRenderContentTypeSelect()
	{
		// shouldRenderContentTypeSelect = false;
		if (shouldRenderContentTypeSelect == false && this.section != null && this.section.getContentType().equals("notype"))
		{
			shouldRenderContentTypeSelect = true;
			shouldRenderUpload = false;
			shouldRenderEditor = false;
			shouldRenderLink = false;
			shouldRenderNotype = true;
		}
		return shouldRenderContentTypeSelect;
	}

	/**
	 * Save the section.Validates modality and license required fields. Track section edit event.
	 */
	public String saveHere()
	{
		FacesContext context = FacesContext.getCurrentInstance();
		ResourceLoader bundle = new ResourceLoader("org.etudes.tool.melete.bundle.Messages");
		if( section.getTitle() == null || section.getTitle().trim().length() == 0)
		{			
			String errMsg = bundle.getString("title_reqd");
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "title_reqd", errMsg));
			return "failure";
		}
		if( section.getTitle() != null && section.getTitle().trim().length() > SectionService.MAX_TITLE_INSTR_LENGTH)
		{			
			String errMsg = bundle.getString("invalid_title_len");
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "invalid_title_len", errMsg));
			return "failure";
		}	
		if( section.getInstr() != null && section.getInstr().trim().length() > SectionService.MAX_TITLE_INSTR_LENGTH)
		{
			String errMsg = bundle.getString("invalid_instructions_len");
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "invalid_instructions_len", errMsg));
			return "failure";
		}	
		
		// validation 1a: modality is required.
		if (!validateModality())
		{
			String errMsg = bundle.getString("add_section_modality_reqd");
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "add_section_modality_reqd", errMsg));
			return "failure";
		}
		
		ValueBinding binding = Util.getBinding("#{authorPreferences}");
		AuthorPreferencePage preferencePage = (AuthorPreferencePage) binding.getValue(context);
		
		boolean contentEmpty = false;
		//If editor is sferyx, grab content empty flag value and set section to notype
		if (section.getContentType().equals("typeEditor") && preferencePage.isShouldRenderSferyx())
		{
			binding = Util.getBinding("#{addResourcesPage}");
			AddResourcesPage resourcesPage = (AddResourcesPage) binding.getValue(context);
			HashMap<String, ArrayList<String>> save_err = resourcesPage.getHm_msgs();
			logger.debug("hashmap in editsectionpage is " + save_err);
			String errKey = "content_empty";
			if (save_err != null && !save_err.isEmpty() && save_err.containsKey(errKey))
			{
				ArrayList<String> errs = save_err.get(errKey);
				for (String err : errs)
				{
					//String errMsg = resourcesPage.getMessageText(err);
					if (err.equals("true")) 
					{
						section.setContentType("notype");
						contentEmpty = true;
					}
				}
				resourcesPage.removeFromHm_Msgs(errKey);
			}
			
		}
		
		//If editor is FCK, check contentEditor and set section to notype if blank
		if (section.getContentType().equals("typeEditor") && preferencePage.isShouldRenderFCK())
		{
			if ((contentEditor == null) || (contentEditor.trim().length() == 0))
			{
				section.setContentType("notype");
				contentEmpty = true;
			}
		}	
		
		Boolean modifyContentResource = false;
		binding =  Util.getBinding("#{licensePage}");
		 LicensePage lPage = (LicensePage)binding.getValue(context);
		 lPage.setFormName(formName);
		try
		{
			// validation 2a: if content is provided then check for license and year lengths
			if (!section.getContentType().equals("notype") && !lPage.getLicenseCodes().equals(LicensePage.NO_CODE))
			{
				lPage.validateLicenseLengths();
			}
			
			// validation 3: if upload a new file check fileName format -- move to uploadSectionContent()
			// validation 3-1: if typeEditor and saved by sferyx then check for error messages
			Date checkLastWork = sectionService.getLastModifiedDate(section.getSectionId());
			
			if (section.getContentType().equals("typeEditor") && preferencePage.isShouldRenderSferyx())
			{
				binding = Util.getBinding("#{addResourcesPage}");
				AddResourcesPage resourcesPage = (AddResourcesPage) binding.getValue(context);
				HashMap<String, ArrayList<String>> save_err = resourcesPage.getHm_msgs();
				logger.debug("hashmap in editsectionpage is " + save_err);
				String errKey = section.getSectionId().toString() + "-" + getCurrUserId();
				if (save_err != null && !save_err.isEmpty() && save_err.containsKey(errKey))
				{
					ArrayList<String> errs = save_err.get(errKey);
					for (String err : errs)
					{
						String errMsg = resourcesPage.getMessageText(err);
						context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, err, errMsg));
					}
					resourcesPage.removeFromHm_Msgs(errKey);
				}
				if (context.getMessages().hasNext()) return "failure";
				logger.debug("CHK IN edit page:" + checkLastWork + ", compare:"+(checkLastWork.getTime() > lastSavedAt));
				modifyContentResource = getIsComposeDataEdited();
			}
			
			// save section
			if (logger.isDebugEnabled()) logger.debug("EditSectionpage:save section" + section.getContentType());
		
			if (checkLastWork != null && checkLastWork.getTime() <= lastSavedAt)
			{
				if (section.getContentType().equals("notype"))
				{
					//This means content in editor is empty
					if (contentEmpty)
					{
						meleteResource = null;
						//Clear out Melete resource table and the resource entry in CH
						if (section.getSectionResource() != null && section.getSectionResource().getResource() != null)
						{
							String delResourceId = section.getSectionResource().getResource().getResourceId();
							sectionService.deleteResourceInUse(delResourceId);
							meleteCHService.removeResource(delResourceId);
						}
						// delete existing record from section_resource table
						sectionService.deleteSectionResourcebyId(section.getSectionId().toString());
						// insert just into section table
						sectionService.editSection(section, getCurrUserId());
					}
					else
					{
						meleteResource = null;
						sectionService.editSection(section, getCurrUserId());
					}
				}
				else
				{
					// step 1: check if new resource content or existing resource is edited										
					try
					{	
						if (shouldRenderContentTypeSelect) throw new MeleteException("new_resource");
						shouldRenderContentTypeSelect = false;
			
						if ("typeEditor".equals(section.getContentType()) && preferencePage.isShouldRenderSferyx())
						{
							modifyContentResource = getIsComposeDataEdited();
							// get secResource object
							secResource = sectionService.getSectionResourcebyId(section.getSectionId().toString());

							meleteResource.setResourceId(secResource.getResource().getResourceId());
							section.setSectionResource(secResource);						
						}
						else if ("typeEditor".equals(section.getContentType()) && preferencePage.isShouldRenderFCK())
						{
							// type change from type upload or typeLink to compose then create section xxx.html file
							if (meleteResource != null && meleteResource.getResourceId() != null && meleteResource.getResourceId().length() != 0
									&& meleteResource.getResourceId().indexOf("/private/meleteDocs/") != -1
									&& meleteResource.getResourceId().indexOf("/uploads/") != -1)
							{
								throw new MeleteException("section_html_null");
							}
							// no type to editor
							if (meleteResource != null && meleteResource.getResourceId() != null && meleteResource.getResourceId().length() == 0)
							{
								throw new MeleteException("section_html_null");
							}
							getMeleteCHService().checkResource(meleteResource.getResourceId());
							modifyContentResource = editMeleteCollectionResource(meleteResource.getResourceId());
						}
						// The condition below was put in to handle ME-639
						else
						{
							if (meleteResource != null && meleteResource.getResourceId() != null
									&& meleteResource.getResourceId().trim().length() != 0)
							{
								// validation 4a: check link url title
								if (section.getContentType().equals("typeLink") && (secResourceName == null || secResourceName.trim().length() == 0))
									throw new UserErrorException("URL_title_reqd");
								// validation 4b: check link url title length
								if (section.getContentType().equals("typeLink")
										&& (secResourceName != null && secResourceName.trim().length() > SectionService.MAX_URL_LENGTH))
									secResourceName = secResourceName.substring(0, SectionService.MAX_URL_LENGTH);
								getMeleteCHService().checkResource(meleteResource.getResourceId());
								modifyContentResource = editMeleteCollectionResource(meleteResource.getResourceId());
							}
							else
							{
								// resource is removed
								if (logger.isDebugEnabled()) logger.debug("Resource ID is null i.e resource is removed");
								editMeleteCollectionResource(null);
								meleteResource = null;

								// delete existing record from section_resource table
								sectionService.deleteSectionResourcebyId(section.getSectionId().toString());
								// change section type to notype
								section.setContentType("notype");
								// insert just into section table
								sectionService.editSection(section, getCurrUserId());
								// refresh section
								this.section = sectionService.getSection(section.getSectionId());
								// refresh date
								lastSavedAt = ((Date)section.getModificationDate()).getTime();
								// Track the event
								EventTrackingService.post(EventTrackingService.newEvent("melete.section.edit", ToolManager.getCurrentPlacement().getContext(), true));
								return "success";
							}
						}

					}
					catch (Exception e)
					{
						// resource is not there when no content type is choosen
						if (logger.isDebugEnabled()) logger.debug("resource is new i.e. coming from notype content");

						String addCollId = null;
						if ("typeEditor".equals(section.getContentType()))
							addCollId = getMeleteCHService().getCollectionId(getCurrentCourseId(), section.getContentType(), module.getModuleId());
						else
							addCollId = getMeleteCHService().getUploadCollectionId(getCurrentCourseId());

						String newResourceId = addResourceToMeleteCollection(addCollId);
						if (newResourceId != null) meleteResource.setResourceId(newResourceId);					
					}

					// step 3: edit license information
					if (meleteResource != null && meleteResource.getResourceId() != null && meleteResource.getResourceId().length() != 0)
					{
						meleteResource = lPage.processLicenseInformation(meleteResource);
						sectionService.editSection(section, meleteResource, getCurrUserId(), modifyContentResource);
					}
					else sectionService.editSection(section, getCurrUserId());
				}
			}

			// refresh section
			this.section = sectionService.getSection(section.getSectionId());
			if (section.getContentType().equals("typeEditor"))
			{
				String refresh_resourceId = getMeleteCHService().getSectionResource(section.getSectionId().toString());
				if (refresh_resourceId != null && refresh_resourceId.length() != 0)
				{
					ContentResource cr = getMeleteCHService().getResource(refresh_resourceId);
					contentEditor = HtmlHelper.clean(new String(cr.getContent()), false);
				}
			}
			lastSavedAt = ((Date)section.getModificationDate()).getTime();
			//Track the event
			EventTrackingService.post(EventTrackingService.newEvent("melete.section.edit", ToolManager.getCurrentPlacement().getContext(), true));

			return "success";
		}
		catch (UserErrorException uex)
		{
			String errMsg = bundle.getString(uex.getMessage());
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, uex.getMessage(), errMsg));
			return "failure";
		}
		catch (MeleteException mex)
		{
			logger.debug("error in updating section " + mex.toString());
			String errMsg = bundle.getString(mex.getMessage());
			// uncomment it after sferyx brings uploadfile limit param
			/*if(mex.getMessage().equals("embed_image_size_exceed"))
			{
				errMsg = errMsg.concat(ServerConfigurationService.getString("content.upload.max", "0"));
				errMsg = errMsg.concat(bundle.getString("embed_image_size_exceed1"));
			}*/
			if(mex.getMessage().equals("embed_image_size_exceed2"))
			{
				errMsg = errMsg.concat(ServerConfigurationService.getString("content.upload.max", "0"));
				errMsg = errMsg.concat(bundle.getString("embed_image_size_exceed2-1"));
			}
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, mex.getMessage(), errMsg));
			return "failure";
		}
		catch (Exception ex)
		{
			logger.debug("error in updating section " + ex.toString());
			String errMsg = bundle.getString("add_section_fail");
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "add_section_fail", errMsg));
			return "failure";
		}

	}

	/**
	 * instantiates saving of a section. if file needs to be uploaded, upload file. Validate content. save section. If sucess set success flag and
	 * show the success message. if any error, display error message to the user.
	 */
	public void save(ActionEvent evt)
	{
		if (saveHere().equals("failure"))
		{
			return;	
		}
//		 editId = (String) evt.getComponent().getAttributes().get("sectionId");
		 setEditIdParam(editId);
	}
	
	/**
	 * show hides the input boxes to specify the uploaded file name, link or writing new content. based on the user radio button selection.
	 * 
	 * @param event
	 * @throws AbortProcessingException
	 */
	public void showHideContent(ValueChangeEvent event) throws AbortProcessingException
	{
		FacesContext context = FacesContext.getCurrentInstance();

		UIInput contentTypeRadio = (UIInput) event.getComponent();
	/*	 editId = (String) event.getComponent().getAttributes().get("sectionId");
		 */
		shouldRenderEditor = contentTypeRadio.getValue().equals("typeEditor");
		shouldRenderLink = contentTypeRadio.getValue().equals("typeLink");
		shouldRenderUpload = contentTypeRadio.getValue().equals("typeUpload");
		shouldRenderNotype = contentTypeRadio.getValue().equals("notype");
		shouldRenderLTI = contentTypeRadio.getValue().equals("typeLTI");
		shouldRenderContentTypeSelect = true;
		
		selResourceIdFromList = null;
		secResourceName = null;
		secResourceDescription = null;
		setMeleteResource(null);
		oldType = section.getContentType();

		ValueBinding binding = Util.getBinding("#{authorPreferences}");
		AuthorPreferencePage preferencePage = (AuthorPreferencePage) binding.getValue(context);
		preferencePage.setEditorFlags();
		preferencePage.setDisplaySferyx(false);

		if (contentTypeRadio.findComponent(getFormName()).findComponent("uploadPath") != null)
		{
			contentTypeRadio.findComponent(getFormName()).findComponent("uploadPath").setRendered(shouldRenderUpload);
			contentTypeRadio.findComponent(getFormName()).findComponent("BrowsePath").setRendered(shouldRenderUpload);
		}

		if (contentTypeRadio.findComponent(getFormName()).findComponent("link") != null)
		{
			contentTypeRadio.findComponent(getFormName()).findComponent("link").setRendered(shouldRenderLink);
		}

		if (contentTypeRadio.findComponent(getFormName()).findComponent("ContentLTIView") != null)
		{
			contentTypeRadio.findComponent(getFormName()).findComponent("ContentLTIView").setRendered(shouldRenderLTI);
		}

		this.contentEditor = new String("Compose content here");
		if (contentTypeRadio.findComponent(getFormName()).findComponent("othereditor:otherMeletecontentEditor") != null)
		{
			setFCKCollectionAttrib();
			contentTypeRadio.findComponent(getFormName()).findComponent("othereditor:otherMeletecontentEditor").setRendered(
					shouldRenderEditor && preferencePage.isShouldRenderFCK());
		}

		if (contentTypeRadio.findComponent(getFormName()).findComponent("contentEditorView") != null)
		{
			preferencePage.setDisplaySferyx(shouldRenderEditor && preferencePage.isShouldRenderSferyx());
			contentTypeRadio.findComponent(getFormName()).findComponent("contentEditorView").setRendered(
					shouldRenderEditor && preferencePage.isShouldRenderSferyx());
		}

		if (contentTypeRadio.findComponent(getFormName()).findComponent("ResourceListingForm") != null)
		{
			section.setContentType((String) contentTypeRadio.getValue());
			contentTypeRadio.findComponent(getFormName()).findComponent("ResourceListingForm").setRendered(false);
		}

		setLicenseInfo();
	  //  setEditIdParam(editId);
	}
	
	/**
     * For top mode bar clicks, auto save section
     * Returns # if save is success else stay on same page to correct error
     */
	public String autoSave()
	{
		setSuccess(false);
		if (!saveHere().equals("failure"))
		{
			setSuccess(true);
			// clear return url
			FacesContext context = FacesContext.getCurrentInstance();
			ValueBinding binding = Util.getBinding("#{meleteSiteAndUserInfo}");
			MeleteSiteAndUserInfo mPage = (MeleteSiteAndUserInfo) binding.getValue(context);
			mPage.setNavigateCM(null);
			return "#";
		}
		return "editmodulesections";
	}

	/**
	 * save the section and create next section.
	 */
	public void saveAndAddAnotherSection(ActionEvent evt)
	{
		try
		{
		if(saveHere().equals("failure"))
		{
			return;
		}

		// create new instance of section model
		setSection(null);
		resetSectionValues();
		setSizeWarning(false);

		FacesContext context = FacesContext.getCurrentInstance();
		Map sessionMap = context.getExternalContext().getSessionMap();
		sessionMap.put("currModule", module);

		ValueBinding binding = Util.getBinding("#{authorPreferences}");
		AuthorPreferencePage authPage = (AuthorPreferencePage) binding.getValue(context);
		authPage.setEditorFlags();

		// create new section
		addBlankSection();		
		FacesContext.getCurrentInstance().getExternalContext().redirect("editmodulesections.jsf?sectionId="+editId);
	}
	catch (Exception e)		
	{
		logger.debug("error in saving and add another" + e.getMessage());
	}
	}

	/**
	 * Save the section and navigate to author list page.
	 */
	public String Finish()
	{
		setSuccess(false);

			if(!saveHere().equals("failure"))
			{
			setSuccess(true);
			} else return "editmodulesections";

		FacesContext context = FacesContext.getCurrentInstance();
		ValueBinding binding = Util.getBinding("#{meleteSiteAndUserInfo}");

		MeleteSiteAndUserInfo mPage = (MeleteSiteAndUserInfo) binding.getValue(context);
		if (mPage.getNavigateCM() != null)
		{
			return "coursemap";
		}
		// un-comment to show success message again.
		/*FacesContext context = FacesContext.getCurrentInstance();
		ResourceLoader bundle = new ResourceLoader("org.etudes.tool.melete.bundle.Messages");
		String successMsg = bundle.getString("edit_section_confirm");
		FacesMessage msg = new FacesMessage("Info message", successMsg);
		msg.setSeverity(FacesMessage.SEVERITY_INFO);
		context.addMessage(null, msg);*/

		binding =Util.getBinding("#{listAuthModulesPage}");
		ListAuthModulesPage listPage = (ListAuthModulesPage) binding.getValue(context);
        listPage.resetValues();
		return "list_auth_modules";

	}

	/**
	 * @return Returns the meleteResource.
	 */
	public MeleteResource getMeleteResource()
	{

		try
		{
			if (this.meleteResource == null)
			{
				if (section == null && editId != null && editId.length() != 0)
				{
					SectionObjService s = sectionService.getSection(Integer.parseInt(editId));
					setEditInfo(s);
				}
				setSecResource(sectionService.getSectionResourcebyId(this.section.getSectionId().toString()));
				if (secResource != null && secResource.getResource() != null)
					setMeleteResource((MeleteResource) sectionService.getMeleteResource(secResource.getResource().getResourceId()));
				if (this.meleteResource == null)
				{
					this.meleteResource = new MeleteResource();
					this.meleteResource.setResourceId("");
				}
			}
		}
		catch (Exception e)
		{
			try
			{
				FacesContext.getCurrentInstance().getExternalContext().redirect("list_auth_modules");
			}
			catch (Exception ex)
			{
			}
		}
		return this.meleteResource;
	}

	/**
	 *  preview Listener
	 * @return
	 */
	 public void getPreviewPageListener(ActionEvent event)
	{
		try
		{
			if(saveHere().equals("failure"))
			{
				return;
			}
		//	String edit = (String) event.getComponent().getAttributes().get("sectionId");
			FacesContext.getCurrentInstance().getExternalContext().redirect("editpreview.jsf?sectionId=" + editId);
		}
		catch (Exception e)
		{
			logger.debug("preview exception:" + e.getMessage());
		}
	}
	 
	/**
	 * Preview the content. Save section.
	 */
	public String getPreviewPage()
	{
		return "editpreview";
	}

	/**
	 * Navigate back to edit page from preview.
	 * @return
	 */
	 public void returnBack(ActionEvent event) {
		 try
			{
			//	editId = (String) event.getComponent().getAttributes().get("sectionId");
				FacesContext.getCurrentInstance().getExternalContext().redirect("editmodulesections.jsf?sectionId=" + editId);
			}
			catch (Exception e)
			{
				logger.debug("preview return exception:" + e.getMessage());
			}	 
	    }
	
	
	/**
	 * reset values
	 */
	public void resetSectionValues()
	{
		shouldRenderContentTypeSelect = false;
		currLinkUrl = null;
		editSelection = null;
		//m_selected_license = null;
		selectedResourceName = null;
		selectedResourceDescription = null;
		selectedResource = null;
		hasNext = null;
		hasPrev = null;
		super.resetSectionValues();
	}

	/**
	 * Navigate to edit page
	 * @return
	 */
	public String redirectLinktoEdit()
	{
		return "editmodulesections";
		// return "#";
	}

	/**
	 * on clicking link 2 me the page navigates back
	 */
	public String redirectLink()
	{
		return "editContentUploadServerView";
		// return "#";
	}

	/**
	 * Navigate page to delete a resource
	 * @return
	 */
	public String redirectDeleteLink()
	{
		 return "delete_resource";
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
	 * @return Returns the editSelection.
	 */
	public String getEditSelection()
	{
		// if(editSelection == null) editSelection = "none";
		return editSelection;
	}

	/**
	 * @param editSelection
	 *        The editSelection to set.
	 */
	public void setEditSelection(String editSelection)
	{
		this.editSelection = editSelection;
	}

	/**
	 * @return Returns the m_selected_license.
	 */
	/*public SectionResourceLicenseSelector getM_selected_license()
	{
		if (m_selected_license == null)
		{
			m_selected_license = new SectionResourceLicenseSelector();
			m_selected_license.setInitialValues(this.formName, sectionService, getSelectedResource());
		}
		return m_selected_license;
	}*/

	/**
	 * @param m_selected_license
	 *        The m_selected_license to set.
	 */
	/*public void setM_selected_license(SectionResourceLicenseSelector m_selected_license)
	{
		this.m_selected_license = m_selected_license;
	}*/

	public String getResourceSectionFile()
	{
		return resourceSectionFile;
	}

	public void setResourceSectionFile(String resourceSectionFile)
	{
		this.resourceSectionFile = resourceSectionFile;
	}

	/**
	 * 
	 * @param evt
	 */
	public void setServerFileListener(ActionEvent evt)
	{
		try
		{
			if (meleteResource.getResourceId() == null || meleteResource.getResourceId().length() == 0)
				sectionService.editSection(section, getCurrUserId());
			else saveHere();
			FacesContext.getCurrentInstance().getExternalContext().redirect(
					"editContentUploadServerView.jsf?fromPage=editContentUploadServerView&sectionId=" + editId);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}	

	/**
	 * 
	 * @param evt
	 */
	public void setServerLTIListener(ActionEvent evt)
	{
		try
		{
			if (meleteResource.getResourceId() == null || meleteResource.getResourceId().length() == 0)
				sectionService.editSection(section, getCurrUserId());
			else saveHere();
			
			FacesContext.getCurrentInstance().getExternalContext().redirect(
					"editContentLTIServerView.jsf?fromPage=editContentLTIServerView&sectionId=" + editId);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @param evt
	 */
	public void setServerUrlListener(ActionEvent evt)
	{
		try
		{
			if (meleteResource.getResourceId() == null || meleteResource.getResourceId().length() == 0)
				sectionService.editSection(section, getCurrUserId());
			else saveHere();
			
			FacesContext.getCurrentInstance().getExternalContext().redirect(
					"editContentLinkServerView.jsf?fromPage=editContentLinkServerView&sectionId=" + editId);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @return
	 */
	public String setServerUrl()
	{
		return "editmodulesections";
	}
	
	/**
	 * Returns true if the alert message needs to be shown to the instructor.
	 * @return
	 */
	public Boolean getHttpAddressAlert()
	{
		httpAddressAlert = null;
		try
		{
			if (section == null) return httpAddressAlert;
			String checkUrl = "";
			if (section.getContentType() != null && section.getContentType().equals("typeLink") )
				checkUrl = currLinkUrl;
			
			if (section.getContentType() != null && section.getContentType().equals("typeLTI") )
				checkUrl = currLTIUrl;
			
			if (checkUrl == null || checkUrl.length() == 0) return httpAddressAlert;
				if (!section.isOpenWindow())
				{
					if (checkUrl.startsWith("http://"))	httpAddressAlert = new Boolean(true);
					else if (checkUrl.startsWith("https://")) httpAddressAlert = new Boolean(false);
				}			
		}
		catch (Exception e)
		{
			httpAddressAlert = null;
		}
		return httpAddressAlert;
	}

	/**
	 * Associate section with LTI resource selected.
	 * @return
	 */
	public void setServerLTI(ActionEvent evt)
	{
		FacesContext ctx = FacesContext.getCurrentInstance();
		ResourceLoader bundle = new ResourceLoader("org.etudes.tool.melete.bundle.Messages");
		String errMsg = null;
		selResourceIdFromList = getSelResourceIdFromList();

		try
		{
			// new link provided
			if (getLTIDescriptor() != null && getLTIDescriptor().length() != 0 )
			{
				if (getLTIDescriptor().equals("http://") || getLTIDescriptor().equals("https://"))
				{
					errMsg = bundle.getString("select_or_cancel");
					ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "select_or_cancel", errMsg));
					return;
				}
				setLicenseInfo();
				if(newURLTitle == null || newURLTitle.length() == 0)
				{
					newURLTitle = getLTIDescriptor();
				}
				if(newURLTitle != null && newURLTitle.length() > SectionService.MAX_URL_LENGTH)
				{
					newURLTitle = newURLTitle.substring(0,SectionService.MAX_URL_LENGTH );
				}
				secResourceName = newURLTitle;
				createLTIDescriptor();
				String res_mime_type = MeleteCHService.MIME_TYPE_LTI;
				ResourcePropertiesEdit res = getMeleteCHService().fillInSectionResourceProperties(false, secResourceName, secResourceDescription);

				containCollectionId = getMeleteCHService().getUploadCollectionId(getCurrentCourseId());
				String newResourceId = getMeleteCHService().addResourceItem(secResourceName, res_mime_type, containCollectionId, getSecContentData(),
						res);
								
				MeleteResourceService newResource = new MeleteResource();
				newResource.setResourceId(newResourceId);
				
				SectionObjService section = sectionService.getSection(Integer.parseInt(editId));
				sectionService.editSection(section, newResource, getCurrUserId(), true);
				ctx.getExternalContext().redirect("editmodulesections.jsf?sectionId=" + editId);
			}
			
		}
		catch (Exception e)
		{
			logger.debug("error in set server url for edit section content" + errMsg);
		//	e.printStackTrace();
			if (e.getMessage() != null)
			{
			  errMsg = bundle.getString(e.getMessage());
			  ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), errMsg));
			}
			return;
		}		
	}

	/**
	 * Cancel LTI selected file.
	 * 
	 * @return
	 */
	public void cancelServerFile(ActionEvent evt)
	{
		try
		{
	//		String secId = (String) evt.getComponent().getAttributes().get("sectionId");
			FacesContext.getCurrentInstance().getExternalContext().redirect("editmodulesections.jsf?sectionId=" + editId);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * @return Returns the shouldRenderServerResources.
	 */
	public boolean isShouldRenderServerResources()
	{
		return shouldRenderServerResources;
	}

	/**
	 * @param shouldRenderServerResources
	 *        The shouldRenderServerResources to set.
	 */
	public void setShouldRenderServerResources(boolean shouldRenderServerResources)
	{
		this.shouldRenderServerResources = shouldRenderServerResources;
	}

	/**
	 * @return Returns the shouldRenderLocalUpload.
	 */
	public boolean isShouldRenderLocalUpload()
	{
		return shouldRenderLocalUpload;
	}

	/**
	 * @param shouldRenderLocalUpload
	 *        The shouldRenderLocalUpload to set.
	 */
	public void setShouldRenderLocalUpload(boolean shouldRenderLocalUpload)
	{
		this.shouldRenderLocalUpload = shouldRenderLocalUpload;
	}

	/**
	 * 
	 * @return
	 */
	public boolean isHasNext()
	{
		SectionObjService nextSection = null;
		if (hasNext == null)
		{
			hasNext = false;
			try
			{
				nextSection = sectionService.getNextSection(section.getSectionId().toString(), module.getSeqXml());

				if (nextSection != null) hasNext = true;
			}
			catch (Exception e)
			{
			}
		}
		return hasNext.booleanValue();
	}

	/**
	 * 
	 * @param evt
	 */
	public void editNextSectionListener(ActionEvent evt)
	{
		if(saveHere().equals("failure"))
		{
			return;
		}	
		
	//	 editId = (String) evt.getComponent().getAttributes().get("otherId");
		 SectionObjService sec = sectionService.getSection(Integer.parseInt(editId));
		// find Next Section/subsection
		SectionObjService nextSection = null;
		try
		{
			nextSection = sectionService.getNextSection(sec.getSectionId().toString(), sec.getModule().getSeqXml());
		
		// reset section model to refresh and set to next
		if(nextSection != null)
		{
			setEditInfo(nextSection);	
			setEditId(nextSection.getSectionId().toString());
		}
		FacesContext.getCurrentInstance().getExternalContext().redirect("editmodulesections.jsf?sectionId="+editId);
		}
		catch (Exception e)		
		{
			logger.debug("error in finding next so probably this is the last one" + e.getMessage());
		}
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean isHasPrev()
	{
		SectionObjService prevSection = null;
		if (hasPrev == null)
		{
			hasPrev = false;
			try
			{
				prevSection = sectionService.getPrevSection(section.getSectionId().toString(), module.getSeqXml());
				if(prevSection != null)hasPrev = true;
			}
			catch (Exception e)
			{
			}
		}
		return hasPrev.booleanValue();
	}

	/**
	 * 
	 * @param evt
	 */
	public void editPrevSectionListener(ActionEvent evt)
	{
		if (saveHere().equals("failure"))
		{
			return;
		}

//		editId = (String) evt.getComponent().getAttributes().get("otherId");
		SectionObjService sec = sectionService.getSection(Integer.parseInt(editId));
		// find Next Section/subsection
		SectionObjService prevSection = null;
		try
		{
			prevSection = sectionService.getPrevSection(sec.getSectionId().toString(), sec.getModule().getSeqXml());

			// reset section model to refresh and set to next
			if (prevSection != null)
			{
				setEditInfo(prevSection);
				setEditId(prevSection.getSectionId().toString());
			}
			FacesContext.getCurrentInstance().getExternalContext().redirect("editmodulesections.jsf?sectionId=" + editId);
		}
		catch (Exception e)
		{
			logger.debug("error in finding prev so probably this is the first one" + e.getMessage());
		}
	}

	/**
	 * 
	 * @return
	 */
	public String goTOC()
	{
		setSuccess(false);
		if (!saveHere().equals("failure"))
		{
			setSuccess(true);
		}
		else
			return "editmodulesections";

		//clear return url
		FacesContext context = FacesContext.getCurrentInstance();
		ValueBinding binding = Util.getBinding("#{meleteSiteAndUserInfo}");
		MeleteSiteAndUserInfo mPage = (MeleteSiteAndUserInfo) binding.getValue(context);
		mPage.setNavigateCM(null);
		
		// reset section model to refresh
		setSection(null);
		resetSectionValues();
		setSizeWarning(false);
		return cancel();
	}

	public String getDataUrl()
	{

		String rUrl = null;
		if (this.section == null) return null;
		SectionResource secRes = (SectionResource)this.section.getSectionResource();
		String resourceId = null;
		if (secRes != null && (secRes.getResource() != null))
		{
			resourceId = secRes.getResource().getResourceId();
		}

		if (resourceId != null)
		{
			try
			{
					rUrl = getMeleteCHService().getResourceUrl(resourceId);
			}catch(Exception e) {
				// do nothing
			}
		}
	  return rUrl;
	}

	public String gotoMyBookmarks()
	{

		setSuccess(false);
		if(!saveHere().equals("failure"))
		{
			setSuccess(true);
		}
		else return "editmodulesections";

		FacesContext context = FacesContext.getCurrentInstance();
		ValueBinding binding = Util.getBinding("#{bookmarkPage}");
		BookmarkPage bmrkPage = (BookmarkPage) binding.getValue(context);

		return bmrkPage.gotoMyBookmarks("editmodulesections", module.getModuleId(), editId);
	}

	public String saveAndAddBookmark()
	{

		setSuccess(false);
		if(!saveHere().equals("failure"))
		{
			setSuccess(true);
			FacesContext context = FacesContext.getCurrentInstance();
			ValueBinding binding = Util.getBinding("#{bookmarkPage}");
			BookmarkPage bmrkPage = (BookmarkPage) binding.getValue(context);
			bmrkPage.setSectionId(this.section.getSectionId().toString());
		}
		return "editmodulesections";

	}

	/*
	 * click of add creates a blank section
	 */
	public Integer addBlankSection()
	{
		FacesContext context = FacesContext.getCurrentInstance();
		Map sessionMap = context.getExternalContext().getSessionMap();
		ValueBinding binding = Util.getBinding("#{meleteSiteAndUserInfo}");
		MeleteSiteAndUserInfo info = (MeleteSiteAndUserInfo) binding.getValue(context);
		Integer newSectionId = null;
		try
		{
			Section s = new Section();
			s.setContentType("notype");
			s.setTextualContent(true);

			// reset flags
			shouldRenderEditor = false;
			shouldRenderLink = false;
			shouldRenderLTI = false;
			shouldRenderUpload = false;
			shouldRenderNotype = true;
			int mId = module.getModuleId().intValue();

			newSectionId = sectionService.insertSection(module, s, info.getCurrentUser().getId());
			s.setSectionId(newSectionId);

			// refresh module and refresh seqxml. It has newly added section id
			this.module = moduleService.getModule(mId);
			sessionMap.put("currModule", module);
			s.setModule(module);

			// set edit page for this section
			setEditId(newSectionId.toString());
			setEditInfo(s);
		}
		catch (Exception ex)
		{
			// do nothing
		}
		return newSectionId;
	}	
	
	public long getLastSavedAt() {
		return lastSavedAt;
	}

	public void setLastSavedAt(long lastSavedAt) {
		this.lastSavedAt = lastSavedAt;
	}

	public String getEditId()
	{
		return editId;
	}

	public void setEditId(String editId)
	{
		this.editId = editId;
	}

	public void setEditIdParam(String editIdParam)
	{
		logger.debug("SETTING EDITID PARAM :" + editIdParam);
		if (editIdParam != null && editIdParam.length() > 0 && !editIdParam.equals("null"))
		{
			setEditId(editIdParam);
			SectionObjService sec = sectionService.getSection(Integer.parseInt(editIdParam));
			setEditInfo(sec);
		}

	}
	
	/**
	 * Get the creator name. If user Id is specified get the current name otherwise from our stored fields
	 * @return
	 */
	public String getCreatedByAuthor()
	{
		try
		{
			if (section.getUserId() != null && section.getUserId().length() > 0)
			{
				User user = UserDirectoryService.getUser(section.getUserId());
				createdByAuthor = user.getFirstName();
				createdByAuthor = createdByAuthor.concat(" " + user.getLastName());
			}		
		}
		catch (Exception e)
		{
			createdByAuthor = "";
		}
		return createdByAuthor;
	}

	/**
	 * Get the last modified author name.
	 * @return
	 */
	public String getModifiedByAuthor()
	{
		try
		{
			if (section.getModifyUserId() != null && section.getModifyUserId().length() > 0)
			{
				User user = UserDirectoryService.getUser(section.getModifyUserId());
				modifiedByAuthor = user.getFirstName();
				modifiedByAuthor = modifiedByAuthor.concat(" " + user.getLastName());
			}	
		}
		catch (Exception e)
		{
			modifiedByAuthor = "";
		}
		return modifiedByAuthor;
	}
	
}
