/**********************************************************************************
 *
 * $URL: https://source.sakaiproject.org/contrib/etudes/melete/tags/2.9.1/melete-impl/src/java/org/etudes/component/app/melete/MeleteImportfromSiteServiceImpl.java $
 * $Id: MeleteImportfromSiteServiceImpl.java 80350 2012-06-15 21:00:14Z rashmi@etudes.org $
 ***********************************************************************************
 *
 * Copyright (c) 2008,2009,2010,2011, 2012 Etudes, Inc.
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
package org.etudes.component.app.melete;

import java.net.MalformedURLException;
import java.net.URLDecoder;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.etudes.api.app.melete.MeleteCHService;
import org.etudes.api.app.melete.MeleteImportfromSiteService;
import org.etudes.api.app.melete.MeleteSitePreferenceService;
import org.etudes.api.app.melete.ModuleObjService;
import org.etudes.api.app.melete.SectionObjService;
import org.etudes.api.app.melete.exception.MeleteException;
import org.sakaiproject.content.api.ContentResource;
import org.sakaiproject.entity.api.ResourceProperties;
import org.sakaiproject.entity.api.ResourcePropertiesEdit;
import org.sakaiproject.exception.IdUnusedException;
import org.sakaiproject.user.api.User;
import org.sakaiproject.user.cover.UserDirectoryService;
import org.sakaiproject.util.Validator;


public class MeleteImportfromSiteServiceImpl extends MeleteImportBaseImpl implements MeleteImportfromSiteService
{
	/*******************************************************************************
	 * Dependencies
	 *******************************************************************************/
	/** Dependency: The logging service. */
	protected Log logger = LogFactory.getLog(MeleteImportfromSiteServiceImpl.class);

	/**
	 * Imports composed section files and for other content types, imports the resource if it not exists in site
	 * 
	 * @param module
	 * @param section
	 * @param meleteResource
	 * @param hrefVal
	 * @param courseId
	 * @param oldCourseId
	 * @throws MalformedURLException
	 * @throws UnknownHostException
	 * @throws MeleteException
	 * @throws Exception
	 */
	private void createContentResource(Module module, Section section, MeleteResource meleteResource, String hrefVal, String courseId,
			String oldCourseId) throws MalformedURLException, UnknownHostException, MeleteException, Exception
	{
		String newResourceId = "";

		if (logger.isDebugEnabled()) logger.debug("Entering createSection...");

		// html file
		if (section.getContentType().equals("typeEditor"))
		{
			String addCollId = getMeleteCHService().getCollectionId(courseId, section.getContentType(), module.getModuleId());
			String sectionResourceName = getMeleteCHService().getTypeEditorSectionName(section.getSectionId());
			newResourceId = processEmbedDatafromHTML(hrefVal, sectionResourceName, courseId, addCollId);
			meleteResource.setResourceId(newResourceId);
			sectionDB.insertMeleteResource((Section) section, (MeleteResource) meleteResource);
			return;
		}
		// for any other section upload/link/LTI check the resource exists and associate with it
		String rdata = checkAndAddResource(hrefVal, courseId, true, oldCourseId);

		if (rdata != null && rdata.length() != 0)
		{
			meleteResource.setResourceId(rdata);
			sectionDB.insertMeleteResource((Section) section, (MeleteResource) meleteResource);
		}

		if (logger.isDebugEnabled()) logger.debug("Exiting createSection...");
	}

	/**
	 * Check if resource exists in the meletedocs collection else add it and record it for skipped files
	 * 
	 * @param hrefVal
	 * @param toSiteId
	 * @param addDB
	 * @param oldCourseId
	 * @return
	 * @throws Exception
	 */
	private String checkAndAddResource(String hrefVal, String toSiteId, boolean addDB, String oldCourseId) throws Exception
	{
		ArrayList<String> rdata = new ArrayList<String>();
		ContentResource cr = null;
		String melResourceName = null;
		String melResourceDescription = null;
		logger.debug("check and add resource for " + hrefVal);
		try
		{
			cr = getMeleteCHService().getResource(hrefVal);
			if (cr == null) return null;
			melResourceDescription = cr.getProperties().getProperty(ResourceProperties.PROP_DESCRIPTION);
			melResourceName = cr.getProperties().getProperty(ResourceProperties.PROP_DISPLAY_NAME);

			// check if the item has already been imported to this site (in uploads collection)
			String checkResourceId = null;
			checkResourceId = "/private/meleteDocs/" + toSiteId + "/uploads/" + Validator.escapeResourceName(melResourceName);
			getMeleteCHService().checkResource(checkResourceId);
			return checkResourceId;
		}
		catch (IdUnusedException ex)
		{
			// if not found in meleteDocs collection include it
			String uploadCollId = getMeleteCHService().getUploadCollectionId(toSiteId);
			String newResourceId = null;

			if (cr.getContentType().equals(MeleteCHService.MIME_TYPE_LINK))
			{
				String linkData = new String(cr.getContent());

				// LINKS POINTING TO RESOURCES AND MELETEDOCS COLLECTION
				if (linkData.indexOf("/access/content/group") != -1 || linkData.indexOf("/access/meleteDocs/content") != -1)
				{
					rdata = copyResource(linkData, oldCourseId, toSiteId, uploadCollId);
					if (rdata == null) return null;
					String firstReferId = rdata.get(1);
					if (addDB && rdata.get(0).equals("new"))
					{
						MeleteResource firstResource = new MeleteResource();
						firstResource.setResourceId(firstReferId);
						sectionDB.insertResource(firstResource);
					}
					// link item should point to new address
					byte[] melContentData = (getMeleteCHService().getResourceUrl(firstReferId)).getBytes();
					ResourcePropertiesEdit res = getMeleteCHService().fillInSectionResourceProperties(true, melResourceName, melResourceDescription);
					newResourceId = getMeleteCHService().addResourceItem(melResourceName, MeleteCHService.MIME_TYPE_LINK, uploadCollId,
							melContentData, res);
				}
				// external urls or my workspace etc
				else
					newResourceId = getMeleteCHService().copyIntoFolder(cr.getId(), uploadCollId);
			}
			// LTI resource
			else if (cr.getContentType().equals(MeleteCHService.MIME_TYPE_LTI))
			{
				ResourcePropertiesEdit res = getMeleteCHService().fillInSectionResourceProperties(true, melResourceName, melResourceDescription);
				newResourceId = getMeleteCHService().addResourceItem(melResourceName, MeleteCHService.MIME_TYPE_LTI, uploadCollId,
						cr.getContent(), res);
			}
			else
			{
				// upload resource
				rdata = copyResource(getMeleteCHService().getResourceUrl(cr.getId()), oldCourseId, toSiteId, uploadCollId);
				return rdata.get(1);
			}

			return newResourceId;
		} // catch end
	}

	/**
	 * Finds the source of resource item - meletedocs or group collection. If resource is html then call processEmbed method otherwise create a new resource.
	 * 
	 * @param Data
	 * @param oldCourseId
	 * @param toSiteId
	 * @param uploadCollId
	 * @return
	 * @throws Exception
	 */
	private ArrayList<String> copyResource(String Data, String oldCourseId, String toSiteId, String uploadCollId) throws Exception
	{
		ArrayList<String> checkResource = new ArrayList<String>();
		ArrayList<String> r = meleteUtil.findResourceSource(Data, oldCourseId, toSiteId, true);
		if (r == null || r.size() == 0) return null;
		String ref_id = r.get(0);
		String checkReferenceId = r.get(1);
		try
		{
			getMeleteCHService().checkResource(checkReferenceId);
			checkResource.add("exists");
			checkResource.add(checkReferenceId);
		}
		catch (IdUnusedException iue)
		{
			String firstReferId = null;
			String extn = ref_id.substring(ref_id.lastIndexOf(".") + 1);
			if (extn.equals("htm") || extn.equals("html"))
			{
				firstReferId = processEmbedDatafromHTML(ref_id, null, toSiteId, uploadCollId);
				checkResource.add("exists");
				checkResource.add(firstReferId);
			}
			else
			{
				ContentResource cr1 = getMeleteCHService().getResource(ref_id);
				String fileResourceName = cr1.getProperties().getProperty(ResourceProperties.PROP_DISPLAY_NAME);
				ResourcePropertiesEdit res = getMeleteCHService().fillInSectionResourceProperties(false, fileResourceName,
						cr1.getProperties().getProperty(ResourceProperties.PROP_DESCRIPTION));
				firstReferId = getMeleteCHService().addResourceItem(fileResourceName, cr1.getContentType(), uploadCollId, cr1.getContent(), res);

				checkResource.add("new");
				checkResource.add(firstReferId);
			}
		}
		return checkResource;
	}

	/**
	 * Process HTML resources.
	 * 
	 * @param ref_id
	 * @param fileResourceName
	 * @param toSiteId
	 * @param uploadCollId
	 * @return
	 * @throws Exception
	 */
	private String processEmbedDatafromHTML(String ref_id, String fileResourceName, String toSiteId, String uploadCollId) throws Exception
	{
		ContentResource cr1 = getMeleteCHService().getResource(ref_id);
		if (fileResourceName == null) fileResourceName = cr1.getProperties().getProperty(ResourceProperties.PROP_DISPLAY_NAME);
		String contentEditor = new String(cr1.getContent());
		String parentStr = meleteUtil.findParentReference(cr1.getId());
		ArrayList<?> content = createHTMLFile(contentEditor, toSiteId, new HashSet<String>(), parentStr);
		contentEditor = (String) content.get(0);

		ResourcePropertiesEdit res = getMeleteCHService().fillInSectionResourceProperties(true, fileResourceName,
				cr1.getProperties().getProperty(ResourceProperties.PROP_DESCRIPTION));
		return getMeleteCHService().addResourceItem(fileResourceName, cr1.getContentType(), uploadCollId, contentEditor.getBytes(), res);
	}

	/**
	 * {@inheritDoc}
	 */
	protected byte[] readData(String unZippedDirPath, String hrefVal) throws Exception
	{
		ContentResource cr = getMeleteCHService().getResource(hrefVal);
		return cr.getContent();
	}

	/**
	 * Parse HTML files and create resources for embedded data. If embedded data is an html file then calls itself again to process. Set is used to record the html files processed in one recursion cycle to avoid circular references.
	 * 
	 * @param contentEditor
	 * @param courseId
	 * @param checkEmbedHTMLResources
	 * @param parentRef
	 * @return
	 * @throws Exception
	 */
	protected ArrayList<?> createHTMLFile(String contentEditor, String courseId, Set<String> checkEmbedHTMLResources, String parentRef) throws Exception
	{
		// save uploaded img inside content editor to destination directory
		String checkforimgs = contentEditor;
		int imgindex = -1;
		String imgSrcPath;

		int startSrc = 0;
		int endSrc = 0;
		String checkLink = null;

		logger.debug("parentStr inside createHTML" + parentRef);
		while (checkforimgs != null)
		{
			ArrayList<?> embedData = meleteUtil.findEmbedItemPattern(checkforimgs);
			checkforimgs = (String) embedData.get(0);
			if (embedData.size() > 1)
			{
				startSrc = ((Integer) embedData.get(1)).intValue();
				endSrc = ((Integer) embedData.get(2)).intValue();
				checkLink = (String) embedData.get(3);
			}
			if (endSrc <= 0) break;

			imgSrcPath = checkforimgs.substring(startSrc, endSrc);

			// This part executed by import from site
			String imgActualPath = "";

			// make it full url
			if (imgSrcPath.indexOf("://") == -1 && imgSrcPath.indexOf("/") == -1)
			{
				logger.debug("parentRef on checking rel path " + parentRef);
				if (parentRef != null && parentRef.length() > 0)
				{
					contentEditor = meleteUtil.replace(contentEditor, imgSrcPath, parentRef + imgSrcPath.trim());
					imgSrcPath = parentRef + imgSrcPath.trim();
				}
			}
			logger.debug("imgSrcpath in createHTML of import from site:" + imgSrcPath);
			// if img src is in library or any other inside sakai path then don't process
			if (imgSrcPath.indexOf("/access") != -1)
			{
				checkforimgs = checkforimgs.substring(endSrc);
				String findResourcePath = imgSrcPath.trim();
				// harvest links with anchors
				if (checkLink != null && checkLink.equals("link") && findResourcePath.indexOf("#") != -1)
					findResourcePath = findResourcePath.substring(0, findResourcePath.indexOf("#"));

				ArrayList<String> r = meleteUtil.findResourceSource(findResourcePath, null, courseId, false);
				if (r == null || r.size() == 0)
				{
					imgindex = -1;
					startSrc = 0;
					endSrc = 0;
					checkLink = null;
					continue;
				}

				imgActualPath = r.get(0);

				String importResName = imgActualPath.substring(imgActualPath.lastIndexOf('/') + 1);

				if (imgActualPath.endsWith(".htm") || imgActualPath.endsWith(".html"))
				{
					// if not processed yet then add to the set
					if (checkEmbedHTMLResources.contains(imgActualPath))
					{
						imgindex = -1;
						startSrc = 0;
						endSrc = 0;
						checkLink = null;
						String replacementStr = "/access/meleteDocs/content/private/meleteDocs/" + courseId + "/uploads/"
								+ imgSrcPath.substring(imgSrcPath.lastIndexOf('/') + 1).trim();
						String patternStr = imgSrcPath;
						contentEditor = meleteUtil.replace(contentEditor, patternStr, replacementStr);
						continue;
					}
					checkEmbedHTMLResources.add(imgActualPath);
					// look for its embedded data
					ContentResource embedResource = getMeleteCHService().getResource(imgActualPath);
					if (embedResource == null)
					{
						checkforimgs = checkforimgs.substring(endSrc);
						imgindex = -1;
						startSrc = 0;
						endSrc = 0;
						checkLink = null;
						continue;
					}
					logger.debug("embed data found at createHTML:" + embedResource.getId());
					if (embedResource.getContentLength() > 0)
					{
						String moreContentData = new String(embedResource.getContent());
						String parentStr = meleteUtil.findParentReference(imgActualPath);
						String filename = imgActualPath.substring(imgActualPath.lastIndexOf('/') + 1);
						String res_id = embedResource.getId();
						res_id = res_id.substring(res_id.lastIndexOf('/') + 1);
						ArrayList<?> contentData = createHTMLFile(moreContentData, courseId, checkEmbedHTMLResources, parentStr);
						moreContentData = (String) contentData.get(0);
						checkEmbedHTMLResources = (Set<String>) contentData.get(1);
						try
						{
							String checkResourceId = meleteCHService.getUploadCollectionId(courseId) + Validator.escapeResourceName(res_id);
							getMeleteCHService().checkResource(checkResourceId);
						}
						catch (IdUnusedException ex)
						{
							addResource(filename, embedResource.getContentType(), moreContentData.getBytes(), courseId);
						}
						catch (Exception e)
						{
							logger.debug("error adding a resource on import from site" + e.toString());
						}
					}
				}
				String anchorString = null;
				if (checkLink != null && checkLink.equals("link") && imgSrcPath.indexOf("#") != -1)
					anchorString = imgSrcPath.substring(imgSrcPath.indexOf("#"));

				contentEditor = ReplaceEmbedMediaWithResourceURL(contentEditor, imgSrcPath, imgActualPath, courseId, null, anchorString);

			} // if access check end
			// for other inside sakai paths
			else
				checkforimgs = checkforimgs.substring(endSrc);

			imgindex = -1;
			startSrc = 0;
			endSrc = 0;
			checkLink = null;
		}// End while
		ArrayList<Object> returnData = new ArrayList<Object>();
		returnData.add(contentEditor);
		returnData.add(checkEmbedHTMLResources);
		return returnData;
	}

	/**
	 * {@inheritDoc}
	 */
	protected String uploadSectionDependentFile(String imgActualPath, String courseId, String unZippedDirPath)
	{
		try
		{
			ContentResource embedResource = getMeleteCHService().getResource(imgActualPath);
			if (embedResource != null && embedResource.getContentLength() > 0)
			{
				String filename = embedResource.getProperties().getProperty(ResourceProperties.PROP_DISPLAY_NAME);
				String res_id = embedResource.getId();
				res_id = res_id.substring(res_id.lastIndexOf('/') + 1);
				try
				{
					String checkResourceId = meleteCHService.getUploadCollectionId(courseId) + Validator.escapeResourceName(res_id);
					getMeleteCHService().checkResource(checkResourceId);
					// found it so return it
					return getMeleteCHService().getResourceUrl(checkResourceId);
				}
				catch (IdUnusedException ex)
				{
					try
					{
						return addResource(filename, embedResource.getContentType(), embedResource.getContent(), courseId);
					}
					catch (Exception ex1)
					{
						logger.debug("error adding embedded resource on import from site" + ex1.toString());
					}
				}
				catch (Exception e)
				{
					logger.debug("error adding a resource on import from site" + e.toString());
				}
			}
		}
		catch (Exception getResourceEx)
		{
			// do nothing
		}
		return "";
	}

	/**
	 * {@inheritDoc}
	 */
	public void copyModules(String fromContext, String toContext)
	{
		// Copy the uploads collection
		buildModules(fromContext, toContext);
		transferManageItems(fromContext, toContext);
		transferMeleteSitePreference(fromContext, toContext);
	}

	/**
	 * Converts a list of Modules --> Set of ImportModules.
	 */
	private Set<ImportModule> convertToImportModules(List<? extends ModuleObjService> aModuleList)
	{
		Set<ImportModule> aModuleSet = new LinkedHashSet<ImportModule>();
		if (aModuleList == null) return aModuleSet;
		for (ModuleObjService m :  aModuleList)
		{
			ImportModule im = new ImportModule((Module)m);
			aModuleSet.add(im);
		}
		return aModuleSet;
	}

	/**
	 * Imports active and archived modules.
	 * 
	 * @param fromContext
	 * @param toContext
	 */
	private void buildModules(String fromContext, String toContext)
	{
		// Get modules in site A
		Map<Integer,? extends SectionObjService> sectionList = null;
		MeleteResource toMres = null;
		int fromSecId, toSecId;

		List<? extends ModuleObjService> fromModuleList = moduleDB.getActivenArchiveModules(fromContext);

		// Iterate through all modules in site A
		if (fromModuleList == null || fromModuleList.size() <= 0) return;

		// Get TO SITE modules
		Set<ImportModule> toSiteModules = new LinkedHashSet<ImportModule>();
		List<? extends ModuleObjService> toModuleList = moduleDB.getActivenArchiveModules(toContext);
		if (toModuleList != null && toModuleList.size() > 0)
		{
			// create a set of toSite modules
			toSiteModules = convertToImportModules(toModuleList);
		}
		User importer = UserDirectoryService.getCurrentUser();
		
		for (ListIterator<?> i = fromModuleList.listIterator(); i.hasNext();)
		{
			Module fromMod = (Module) i.next();
			String fromModSeqXml = fromMod.getSeqXml();

			// if fromModule exists in toSiteModulesList then skip
			ImportModule check = new ImportModule(fromMod);
			if (toSiteModules != null && toSiteModules.contains(check))
			{
				logger.debug("already there SO DON'T IMPORT OVER" + fromMod.getTitle());
				continue;
			}

			// Copy module properties and insert, seqXml is null for now
			Module toMod = new Module(fromMod.getTitle(), fromMod.getDescription(), fromMod.getKeywords(), importer.getId(), importer.getId(),
					fromMod.getWhatsNext(), new java.util.Date(), new java.util.Date(), null);
			ModuleShdates fromShDates = (ModuleShdates) fromMod.getModuleshdate();
			ModuleShdates toModshdate = new ModuleShdates(fromShDates.getStartDate(), fromShDates.getEndDate(), fromShDates.getAllowUntilDate(),
					fromShDates.getAddtoSchedule());
			if (fromMod.getCoursemodule().isArchvFlag() == false)
			{
				try
				{
					moduleDB.addModule(toMod, toModshdate, importer.getId(), toContext);
					if ((toModshdate.getAddtoSchedule().booleanValue() == true)
							&& ((toModshdate.getStartDate() != null) || (toModshdate.getEndDate() != null)))
					{
						moduleDB.updateCalendar(toMod, toModshdate, toContext);
					}
				}
				catch (Exception ex3)
				{
					// logger.debug("error importing module");
				}
			}
			else
			{
				CourseModule toCmod = new CourseModule(toContext, -1, true, fromMod.getCoursemodule().getDateArchived(), false, toMod);
				try
				{
					moduleDB.addArchivedModule(toMod, toModshdate, importer.getId(), toContext, toCmod);
				}
				catch (Exception ex3)
				{
					// logger.debug("error importing archived module");
				}

			}

			sectionList = fromMod.getSections();
			// Iterate throug sections of a module
			if (sectionList != null)
			{
				int mapSize = sectionList.size();
				if (mapSize > 0)
				{
					Iterator<?> keyValuePairs = sectionList.entrySet().iterator();
					while (keyValuePairs.hasNext())
					{
						Map.Entry entry = (Map.Entry) keyValuePairs.next();
						Section fromSec = (Section) entry.getValue();
						fromSecId = fromSec.getSectionId().intValue();

						Section toSec = new Section(fromSec.getTitle(), importer.getId(), importer.getId(), fromSec.getInstr(),
								fromSec.getContentType(), fromSec.isAudioContent(), fromSec.isVideoContent(), fromSec.isTextualContent(),
								fromSec.isOpenWindow(), fromSec.isDeleteFlag(), new java.util.Date(), new java.util.Date());
						// logger.debug("copied section open window value" + toSec.getTitle()+"," + toSec.isOpenWindow() );
						try
						{
							// Insert into the SECTION table
							sectionDB.addSection(toMod, toSec, false, importer.getId());
							toSecId = toSec.getSectionId().intValue();
							// Replace old references of sections to new references in SEQ xml
							// TODO : Move the update seqxml lower down so sequence does not update
							// if exception is thrown
							if (!fromSec.getContentType().equals("notype") && fromSec.getSectionResource() != null
									&& fromSec.getSectionResource().getResource() != null)
							{
								toMres = new MeleteResource((MeleteResource) fromSec.getSectionResource().getResource());
								toMres.setResourceId(null);
								createContentResource(toMod, toSec, toMres, fromSec.getSectionResource().getResource().getResourceId(), toContext,
										fromContext);
							}
							if (fromModSeqXml != null) fromModSeqXml = fromModSeqXml.replace(Integer.toString(fromSecId), Integer.toString(toSecId));

						}
						catch (Exception ex)
						{
							if (logger.isDebugEnabled())
							{
								// logger.debug("error in inserting section "+ ex.toString());
								ex.printStackTrace();
							}
							// rollback and delete section
							try
							{
								sectionDB.deleteSection(toSec, toContext, null);
							}
							catch (Exception ex2)
							{
								logger.debug("Error in deleting section " + ex2.toString());
							}
						}

					}

					// Finally, update the seqXml for the module

					try
					{
						Module secModule = moduleDB.getModule(toMod.getModuleId().intValue());
						secModule.setSeqXml(fromModSeqXml);
						moduleDB.updateModule(secModule);
					}
					catch (Exception ex)
					{
						logger.debug("error in updating module");
					}

				}
			}
			if (toSiteModules != null && toMod != null) toSiteModules.add(new ImportModule(toMod));
		}
	}

	/**
	 * Import of unreferred meletedocs items
	 * 
	 * @param fromContext
	 * @param toContext
	 */
	private void transferManageItems(String fromContext, String toContext)
	{
		long totalstart = System.currentTimeMillis();
		String fromUploadsColl = meleteCHService.getUploadCollectionId(fromContext);
		String toUploadsColl = meleteCHService.getUploadCollectionId(toContext);

		List<String> fromContextList = meleteCHService.getMemberNamesCollection(fromUploadsColl);

		List<String> toContextList = meleteCHService.getMemberNamesCollection(toUploadsColl);

		if ((fromContextList != null) && (toContextList != null))
		{
			ListIterator<String> memIt = fromContextList.listIterator();
			List<String> replaceContextList = new ArrayList<String>();
			while (memIt != null && memIt.hasNext())
			{
				String resId = (String) memIt.next();
				resId = resId.replaceFirst(fromContext, toContext);
				replaceContextList.add(resId);
			}
			if (replaceContextList != null && replaceContextList.size() > 0)
			{
				replaceContextList.removeAll(toContextList);
				if (replaceContextList.size() > 0)
				{
					ListIterator<?> repIt = replaceContextList.listIterator();
					while (repIt != null && repIt.hasNext())
					{
						String resId = (String) repIt.next();
						resId = resId.replaceFirst(toContext, fromContext);
						byte[] melContentData;
						String res_mime_type, melResourceName;
						try
						{
							ContentResource cr = getMeleteCHService().getResource(resId);
							melContentData = cr.getContent();
							res_mime_type = cr.getContentType();
							melResourceName = cr.getProperties().getProperty(ResourceProperties.PROP_DISPLAY_NAME);
							try
							{
								// check if the item has already been imported to this site (in uploads collection)
								String find = Validator.escapeResourceName(melResourceName);
								// for + sign characters
								try
								{
									find = URLDecoder.decode(find, "UTF-8");
								}
								catch (Exception decodex)
								{
								}
								String checkResourceId = toUploadsColl + find;
								getMeleteCHService().checkResource(checkResourceId);
							}
							catch (IdUnusedException ex)
							{
								ResourcePropertiesEdit res = getMeleteCHService().fillInSectionResourceProperties(false, melResourceName, "");
								try
								{
									String newResourceId = getMeleteCHService().addResourceItem(melResourceName, res_mime_type, toUploadsColl,
											melContentData, res);
								}
								catch (Exception e)
								{
									logger.debug("Error thrown in exporting of manage resources" + e.toString());
								}
							}
						}
						catch (IdUnusedException unuse)
						{
							// if file not found exception or content is missing continue working
							logger.debug("error in reading resource content in exporting manage resources");
						}
						catch (Exception e)
						{
							logger.error("error in reading resource in export manage resource");
						}

					}// End while repIt
				}// End if
			}
		}

		long totalend = System.currentTimeMillis();

		logger.debug("TRANSFER took " + (totalend - totalstart) + " millisecs");
	}

	/**
	 * Transfers melete site preferences like printable etc
	 * @param fromContext
	 * @param toContext
	 */
	private void transferMeleteSitePreference(String fromContext, String toContext)
	{
		try
		{
			MeleteSitePreference fromMsp = meleteUserPrefDB.getSitePreferences(fromContext);
			MeleteSitePreference toMsp = meleteUserPrefDB.getSitePreferences(toContext);
			if (fromMsp == null) return;
			if (toMsp == null)
			{
				toMsp = new MeleteSitePreference(fromMsp);
				toMsp.setPrefSiteId(toContext);
			}
			else
			{
				toMsp.setAutonumber(fromMsp.isAutonumber());
				toMsp.setPrintable(fromMsp.isPrintable());
			}
			meleteUserPrefDB.setSitePreferences(toMsp);
		}
		catch (Exception e)
		{
			logger.warn("Error on importing melete site preferences" + e.getMessage());
			e.printStackTrace();
		}
	}
	
	/**
	 * Class to check if module should be imported to next site. If module with same title, no of sections, and all sections with same title and 
	 * content type exists in next site then don't import over.
	 */
	class ImportModule
	{
		Module importMod;

		public ImportModule(Module importMod)
		{
			this.importMod = importMod;
		}

		public Module getImportMod()
		{
			return importMod;
		}

		public void setImportMod(Module importMod)
		{
			this.importMod = importMod;
		}

		@Override
		public int hashCode()
		{
			int hash = 1;
			// module title
			hash = hash * 31 + importMod.getTitle().hashCode();

			// sections size
			Map<Integer, ? extends SectionObjService> secs = importMod.getSections();
			hash = hash * 31 + (secs == null ? 0 : secs.size());

			// sections title and content type
			if (secs != null)
			{
				Iterator<?> keyValuePairs = secs.keySet().iterator();
				while (keyValuePairs.hasNext())
				{
					Section s = (Section) secs.get(keyValuePairs.next());
					hash = hash * 31 + s.getTitle().hashCode();
					hash = hash * 31 + (s.getContentType() == null ? 0 : s.getContentType().hashCode());
				}
			}
			return hash;
		}

		@Override
		public boolean equals(Object obj)
		{
			if (this == obj) return true;
			if (obj == null) return false;
			if (getClass() != obj.getClass()) return false;
			ImportModule other = (ImportModule) obj;
			boolean result = false;
			// module title
			if (this.getImportMod().getTitle().equals(other.getImportMod().getTitle()))
				result = true;
			else
				return false;

			// compare sections
			Map<Integer, ? extends SectionObjService> secs = this.getImportMod().getSections();
			Map<Integer, ? extends SectionObjService> otherSecs = other.getImportMod().getSections();

			if (secs != null && otherSecs != null)
			{
				if (secs.size() == otherSecs.size())
					result = true;
				else
					return false;

				Iterator<?> keyValuePairs = secs.keySet().iterator();
				Iterator<?> otherKeyValuePairs = otherSecs.keySet().iterator();
				while (keyValuePairs.hasNext() && otherKeyValuePairs.hasNext())
				{
					Section s = (Section) secs.get(keyValuePairs.next());
					Section sOther = (Section) otherSecs.get(otherKeyValuePairs.next());
					if (s.getTitle().equals(sOther.getTitle()) && s.getContentType().equals(sOther.getContentType()))
						result = true;
					else
						return false;
				}
			}
			else if (secs == null && otherSecs == null)
				result = true;
			else
				return false;

			return result;
		}
	}
}
