/**********************************************************************************
 *
 * $URL: https://source.sakaiproject.org/contrib/etudes/melete/tags/2.9.1/melete-impl/src/java/org/etudes/component/app/melete/MeleteImportServiceImpl.java $
 * $Id: MeleteImportServiceImpl.java 80314 2012-06-12 22:15:39Z rashmi@etudes.org $
 ***********************************************************************************
 *
 * Copyright (c) 2008,2009,2010,2011 Etudes, Inc.
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

import java.io.File;
import java.net.MalformedURLException;
import java.net.URLDecoder;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.XPath;

import org.etudes.api.app.melete.MeleteCHService;
import org.etudes.api.app.melete.MeleteImportService;
import org.etudes.api.app.melete.exception.MeleteException;

import org.sakaiproject.component.cover.ServerConfigurationService;
import org.sakaiproject.content.cover.ContentTypeImageService;
import org.sakaiproject.entity.api.ResourcePropertiesEdit;
import org.sakaiproject.exception.IdUnusedException;
import org.sakaiproject.thread_local.api.ThreadLocalManager;
import org.sakaiproject.user.cover.UserDirectoryService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.sakaiproject.util.Validator;

/**
 * MeleteImportServiceImpl is the implementation of MeleteImportService that provides the methods for importing IMS packages
 */
public class MeleteImportServiceImpl extends MeleteImportBaseImpl implements MeleteImportService
{
	/*******************************************************************************
	 * Dependencies
	 *******************************************************************************/
	/** Dependency: The logging service. */
	protected Log logger = LogFactory.getLog(MeleteImportServiceImpl.class);
	protected ThreadLocalManager importThreadLocal = org.sakaiproject.thread_local.cover.ThreadLocalManager.getInstance();

	/**
	 * Gets module title. "Untitled Module" if title is not specified.
	 * 
	 * @param titleEle
	 * @param module
	 */
	private void buildModuleTitle(Element titleEle, Module module)
	{
		boolean moduleTitleFlag = false;
		if (titleEle != null)
		{
			String title = titleEle.getTextTrim();
			if (title != null && title.length() != 0)
			{
				module.setTitle(title);
				moduleTitleFlag = true;
			}
		}
		if (!moduleTitleFlag) module.setTitle("Untitled Module");
		return;
	}

	/**
	 * Gets module description.
	 * 
	 * @param descEle
	 * @param module
	 * @return
	 */
	private boolean buildModuleDescription(Element descEle, Module module)
	{
		boolean descr = false;
		if (descEle != null && descEle.element("imsmd:langstring") != null)
		{
			String desc = descEle.element("imsmd:langstring").getText();
			module.setDescription(desc.trim());
			descr = true;
		}
		return descr;
	}

	/**
	 * Gets module keyword.
	 * 
	 * @param keywordEle
	 * @param module
	 * @return
	 */
	private boolean buildModuleKeyword(Element keywordEle, Module module)
	{
		boolean keywords = false;

		if (keywordEle != null && keywordEle.element("imsmd:langstring") != null)
		{
			String modkeyword = keywordEle.element("imsmd:langstring").getText();
			module.setKeywords(modkeyword.trim());
			keywords = true;
		}
		return keywords;
	}

	/**
	 * {@inheritDoc}
	 */
	public int mergeAndBuildModules(Document ArchiveDoc, String unZippedDirPath, String fromSiteId, String userId) throws Exception
	{
		if (logger.isDebugEnabled()) logger.debug("Entering mergeAndBuildModules");

		int count = 0;
		try
		{
			Element rootEle = ArchiveDoc.getRootElement();

			Map<String,String> uris = new HashMap<String,String>();
			uris.put("imscp", DEFAULT_NAMESPACE_URI);
			uris.put("imsmd", IMSMD_NAMESPACE_URI);

			// organizations
			List<?> elements = rootEle.selectNodes("//organization/item");
			logger.debug("sz of elements is" + elements.size());
			count = elements.size();
			for (Iterator<?> iter = elements.iterator(); iter.hasNext();)
			{
				Element element = (Element) iter.next();

				// build module
				Module module = new Module();
				boolean keywords = false;
				boolean descr = false;
				for (Iterator<?> iter1 = element.elementIterator(); iter1.hasNext();)
				{
					Element childele = (Element) iter1.next();

					if (childele.getName().equals("title")) buildModuleTitle(childele, module);
					if (childele.getName().equals("imsmd:lom"))
					{
						List<?> modulegeneralList = childele.elements();
						List<?> moduleMetadataList = ((Element)modulegeneralList.get(0)).elements();

						for (Iterator<?> iter2 = moduleMetadataList.iterator(); iter2.hasNext();)
						{
							Element metaElement = (Element) iter2.next();

							if (metaElement.getName().equals("imsmd:description")) descr = buildModuleDescription(metaElement, module);
							if (!descr) module.setDescription("    ");
							if (metaElement.getName().equals("imsmd:keyword")) keywords = buildModuleKeyword(metaElement, module);
							if (!keywords) module.setKeywords(module.getTitle());
						}
					}

				}
				createModule(module, fromSiteId,userId);
				// build sections

				sectionUtil = new SubSectionUtilImpl();
				Document seqDocument = sectionUtil.createSubSection4jDOM();

				for (Iterator<?> iter3 = element.elementIterator("item"); iter3.hasNext();)
				{
					Element itemelement = (Element) iter3.next();

					if (itemelement.attributeValue("identifier").startsWith("NEXTSTEPS"))
						mergeWhatsNext(itemelement, ArchiveDoc, module, unZippedDirPath);
					else
						mergeSection(itemelement, ArchiveDoc, module, addBlankSection(null, seqDocument), unZippedDirPath, seqDocument, fromSiteId, userId);
				}

				// update module seqXml
				logger.debug("checking seqXML now at the end of buildModule process" + seqDocument.asXML());
				module.setSeqXml(seqDocument.asXML());
				moduleDB.updateModule(module);

			}
		}
		catch (Exception e)
		{
			// no organization tag so create one flat module
			// e.printStackTrace();
		}

		if (logger.isDebugEnabled()) logger.debug("Exiting mergeAndBuildModules");
		return count;
	}

	/**
	 * Imports un-referred file tag items.
	 * 
	 * @param rootEle
	 * @param unZippedDirPath
	 * @param fromSiteId
	 * @param document
	 * @throws Exception
	 */
	private void processManageItems(Element rootEle, String unZippedDirPath, String fromSiteId, Document document) throws Exception
	{
		List<?> elements = rootEle.elements();
		for (Iterator<?> iter = elements.iterator(); iter.hasNext();)
		{
			Element element = (Element) iter.next();
			// for next steps file don't bring in as unreferrrenced material
			if (element.attributeValue("identifier").startsWith("NEXTSTEPS")) continue;

			Attribute resHrefAttr = element.attribute("href");

			if (resHrefAttr != null)
			{
				String hrefVal = resHrefAttr.getValue();
				hrefVal = hrefVal.trim();

				// for old melete packages which don't have identifier starting with NEXTSTEPS
				if (hrefVal != null && hrefVal.indexOf("module_") != -1 && hrefVal.endsWith("nextsteps.html")) continue;

				// for files already imported
				Set<String> recordFiles = (Set) importThreadLocal.get("MeleteIMSImportFiles");
				if (recordFiles != null && recordFiles.contains(hrefVal)) continue;

				String melResourceName = null;
				String melResourceDescription = null;
				String contentType = "typeUpload";
				List<Element> resElements = element.elements();

				// typeLink resource
				if (hrefVal.startsWith("http://") || hrefVal.startsWith("https://") || hrefVal.startsWith("mailto:"))
				{
					melResourceName = readTitlefromElement(resElements, true, hrefVal);
					contentType = "typeLink";
				}
				else
				{
					melResourceName = hrefVal.substring(hrefVal.lastIndexOf("/") + 1);
					// if starts with Section then ignore it
					if (hrefVal.substring(hrefVal.lastIndexOf('/') + 1).startsWith("Section_")) continue;
					// LTI RESOURCE
					// Load up the resource to look at it
					// String fileName = ((Element)resElements.get(0)).attributeValue("href");
					String fileName = getFileNamefromElement(resElements, melResourceName);
					byte[] data = readData(unZippedDirPath, fileName);
					if (data == null) continue;
					String contentEditor = new String(data);
					if (isLTIDocument(contentEditor)) contentType = "typeLTI";

					// else uploaded file
					String fileTitle = readTitlefromElement(resElements, false, null);
					if (fileTitle != null) melResourceName = fileTitle;
					melResourceDescription = readDescriptionFromElement(resElements);
				}
				checkAndAddResource(hrefVal, contentType, fromSiteId, resElements, unZippedDirPath, false);
			}
		} // for end
	}

	/**
	 * Get resource title. For links, if not provided then last part of url becomes title.
	 * 
	 * @param resElements
	 * @param link
	 * @param hrefVal
	 * @return
	 */
	private String readTitlefromElement(List<Element> resElements, boolean link, String hrefVal)
	{
		String urlTitle = null;
		if (resElements != null)
		{
			for (int i = 0; i < resElements.size(); i++)
			{
				Element urlTitleElement = (Element) resElements.get(i);
				if (urlTitleElement.getQualifiedName().equalsIgnoreCase("imsmd:title"))
				{
					urlTitle = urlTitleElement.selectSingleNode(".//imsmd:langstring").getText();
					break;
				}
			}
		}

		// make last part of link as title
		if (link && urlTitle == null)
		{
			urlTitle = hrefVal.substring(hrefVal.lastIndexOf("/") + 1);
			if (urlTitle == null || urlTitle.length() == 0)
			{
				urlTitle = hrefVal.substring(0, hrefVal.lastIndexOf("/"));
				urlTitle = urlTitle.substring(urlTitle.lastIndexOf("/") + 1);
			}
		}
		return urlTitle;
	}

	/**
	 * Get Resource description
	 * 
	 * @param resElements
	 * @return
	 */
	private String readDescriptionFromElement(List<Element> resElements)
	{
		String melResourceDescription = null;
		if (resElements != null)
		{
			for (int i = 0; i < resElements.size(); i++)
			{
				Element resDescElement = (Element) resElements.get(i);
				if (resDescElement.getQualifiedName().equalsIgnoreCase("imsmd:description"))
				{
					melResourceDescription = resDescElement.selectSingleNode(".//imsmd:langstring").getText();
					break;
				}
			}
		}
		return melResourceDescription;
	}

	/**
	 * Test if resource is already in meletedocs collection. If not, read it from package file and add it.
	 * 
	 * @param hrefVal
	 * @param contentType
	 * @param courseId
	 * @param resElements
	 * @param unZippedDirPath
	 * @param addDB
	 * @return
	 * @throws Exception
	 */
	private String checkAndAddResource(String hrefVal, String contentType, String courseId, List<Element> resElements, String unZippedDirPath, boolean addDB)
	throws Exception
	{
		ArrayList<String> checkResource = null;
		String melResourceName = readTitlefromElement(resElements, true, hrefVal);
		String melResourceDescription = readDescriptionFromElement(resElements);

		// Everything here is going to uploads collection
		try
		{
			// check if the item has already been imported to this site (in uploads collection)
			String checkResourceId = "/private/meleteDocs/" + courseId + "/uploads/" + Validator.escapeResourceName(melResourceName);
			getMeleteCHService().checkResource(checkResourceId);
			return checkResourceId;
		}
		catch (IdUnusedException ex)
		{
			// actual insert
			String uploadCollId = getMeleteCHService().getUploadCollectionId(courseId);
			byte[] melContentData = null;
			String res_mime_type = null;
			String newResourceId = null;

			if (contentType.equals("typeLink"))
			{
				if (hrefVal.indexOf("/access/content/group") != -1 || hrefVal.indexOf("/access/meleteDocs") != -1)
				{
					checkResource = copyResource(hrefVal, courseId, uploadCollId, resElements, unZippedDirPath);
					if (checkResource == null || checkResource.size() == 0) return null;
					String firstReferId = (String) checkResource.get(1);
					if (firstReferId == null) return null;
					if (addDB && ((String) checkResource.get(0)).equals("new"))
					{
						MeleteResource firstResource = new MeleteResource();
						firstResource.setResourceId(firstReferId);
						sectionDB.insertResource(firstResource);
					}
					// this section points to the link location of added resource item
					String secondResName = getMeleteCHService().getResourceUrl(firstReferId);
					melContentData = secondResName.getBytes();
					res_mime_type = MeleteCHService.MIME_TYPE_LINK;
				}
				else
				{
					// for external url
					// DNS name for workspace files
					if (hrefVal.indexOf("/access/") != -1)
						hrefVal = ServerConfigurationService.getServerUrl() + hrefVal.substring(hrefVal.indexOf("/access/"));
					melContentData = hrefVal.getBytes();
					res_mime_type = MeleteCHService.MIME_TYPE_LINK;
				}
			}
			else if (contentType.equals("typeLTI"))
			{
				res_mime_type = MeleteCHService.MIME_TYPE_LTI;
				String fileName = ((Element) resElements.get(0)).attributeValue("href");
				melContentData = readData(unZippedDirPath, fileName);
			}
			else
			{
				// uploaded file
				checkResource = copyResource(hrefVal, courseId, uploadCollId, resElements, unZippedDirPath);
				return checkResource.get(1);
			}
			if (!contentType.equals("typeUpload"))
			{
				ResourcePropertiesEdit res = getMeleteCHService().fillInSectionResourceProperties(true, melResourceName, melResourceDescription);
				newResourceId = getMeleteCHService().addResourceItem(melResourceName, res_mime_type, uploadCollId, melContentData, res);
			}
			return newResourceId;
		} // catch end

	}

	/**
	 * For embedded links, if they refer to a file item then get it
	 * 
	 * @param hrefVal
	 * @param toSiteId
	 * @param uploadCollId
	 * @param resElements
	 * @param unZippedDirPath
	 * @return
	 * @throws Exception
	 */
	private ArrayList<String> copyResource(String hrefVal, String toSiteId, String uploadCollId, List<Element> resElements, String unZippedDirPath) throws Exception
	{
		String firstReferId = null;
		ArrayList<String> checkResource = new ArrayList<String>();
		String fileResourceName = hrefVal.substring(hrefVal.lastIndexOf("/") + 1);
		String checkReferenceId = "/private/meleteDocs/" + toSiteId + "/uploads/" + fileResourceName;
		try
		{
			getMeleteCHService().checkResource(checkReferenceId);
			checkResource.add("exists");
			checkResource.add(checkReferenceId);
			return checkResource;
		}
		catch (IdUnusedException iue)
		{
			// NOTE: the decode is absolutely reqd otherwise display name of site resources links pointing to files with funky characters loses those characters
			// ex:- student$guide.pdf becomes student%24guide.pdf
			try
			{
				fileResourceName = URLDecoder.decode(fileResourceName, "UTF-8");
			}
			catch (Exception e)
			{
				// if decode fails then just use the fileResourceName as is
			}
			String extn = fileResourceName.substring(fileResourceName.lastIndexOf(".") + 1);
			String fileName = getFileNamefromElement(resElements, fileResourceName);
			if (extn.equals("htm") || extn.equals("html"))
			{
				firstReferId = processEmbedDatafromHTML(fileName, fileResourceName, toSiteId, uploadCollId, resElements, unZippedDirPath);
				checkResource.add("exists");
				checkResource.add(firstReferId);
			}
			else
			{
				String fileResourceDescription = readDescriptionFromElement(resElements);
				String res_mime_type = fileResourceName.substring(fileResourceName.lastIndexOf(".") + 1);
				res_mime_type = ContentTypeImageService.getContentType(res_mime_type);
				byte[] contentData = readData(unZippedDirPath, fileName);
				if (contentData == null) return null;
				ResourcePropertiesEdit res = getMeleteCHService().fillInSectionResourceProperties(false, fileResourceName, fileResourceDescription);
				firstReferId = getMeleteCHService().addResourceItem(fileResourceName, res_mime_type, uploadCollId, contentData, res);
				checkResource.add("new");
				checkResource.add(firstReferId);
			}
		}
		return checkResource;
	}

	/**
	 * find File element from manifest file which tells the exact location of file in the package.
	 * 
	 * @param resElements
	 * @param fileResourceName
	 * @return
	 * @throws Exception
	 */
	private String getFileNamefromElement(List<Element> resElements, String fileResourceName) throws Exception
	{
		String actualFileLocation = null;
		// NOTE:this decoder is reqd. It helps in reading the right file.ex:-href says student%24guide.pdf and the file is
		// under resources as student$guide.pdf
		try
		{
			fileResourceName = URLDecoder.decode(fileResourceName, "UTF-8");
		}
		catch (Exception e)
		{
			// do nothing
		}
		// for chars like [] which decode passes but is converted for xml parser
		fileResourceName = meleteUtil.escapeFileforExportPackage(fileResourceName);
		String checkFileName = fileResourceName;
		// to look for exact filename ex:- menu.jpg and applemenu.jpg. menu.jpg search shouldnot pick applemenu.jpg
		if (checkFileName.indexOf("/") == -1) checkFileName = "/" + checkFileName;

		for (int i = 0; i < resElements.size(); i++)
		{
			Element FileEle = (Element) resElements.get(i);
			actualFileLocation = FileEle.attributeValue("href");
			if (actualFileLocation != null && actualFileLocation.indexOf(checkFileName) != -1) return actualFileLocation;
		}
		// if not found as /menu.jpg then look for menu.jpg for eXe packages
		for (int i = 0; i < resElements.size(); i++)
		{
			Element FileEle = (Element) resElements.get(i);
			actualFileLocation = FileEle.attributeValue("href");
			if (actualFileLocation != null && actualFileLocation.equals(fileResourceName)) return actualFileLocation;
		}

		// for references not in file tag and are just embedded references
		if (fileResourceName.startsWith("../"))
		{
			fileResourceName = fileResourceName.replace("../", "");
		}
			
		return fileResourceName;
	//	return null;
	}

	/**
	 * Abstract method implementation to read data from the zip file
	 */
	protected byte[] readData(String unZippedDirPath, String hrefVal) throws Exception
	{
		File fileUploadResource = new File(unZippedDirPath + File.separator + hrefVal);
		if (fileUploadResource.exists() && fileUploadResource.isFile())
			return meleteUtil.readFromFile(fileUploadResource);
		else
			return null;
	}

	/**
	 * Parse HTML file and harvest embed data on IMS import
	 * 
	 * @param contentEditor
	 * @param resElements
	 * @param unZippedDirPath
	 * @param courseId
	 * @param checkEmbedHTMLResources
	 * @param parentRef
	 * @return
	 * @throws Exception
	 */
	private ArrayList<?> createHTMLFile(String contentEditor, List<Element> resElements, String unZippedDirPath, String courseId,
			Set<String> checkEmbedHTMLResources, String parentRef) throws Exception
			{
		// save uploaded img inside content editor to destination directory
		String checkforimgs = contentEditor;
		int imgindex = -1;
		String imgSrcPath;

		int startSrc = 0;
		int endSrc = 0;
		String checkLink = null;

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
			if (endSrc <= 0 || endSrc > checkforimgs.length()) break;

			imgSrcPath = checkforimgs.substring(startSrc, endSrc);
			if (imgSrcPath == null || imgSrcPath.length() == 0)
			{
				checkforimgs = checkforimgs.substring(endSrc);
				continue;
			}
			String patternStr = imgSrcPath;
			String anchorString = null;

			// changed on 10/16/06 - add https condition too
			if (resElements != null)
			{
				if (!(imgSrcPath.startsWith("http://") || imgSrcPath.startsWith("https://")))
				{
					// if img src is in library or any other inside sakai path then don't process
					if (!imgSrcPath.startsWith("/"))
					{
						checkforimgs = checkforimgs.substring(endSrc);
						if (checkLink != null && checkLink.equals("link") && imgSrcPath.indexOf("#") != -1)
						{
							anchorString = imgSrcPath.substring(imgSrcPath.indexOf("#"));
							imgSrcPath = imgSrcPath.substring(0, imgSrcPath.indexOf("#"));
						}
						String imgActualPath = "";
						imgActualPath = getFileNamefromElement(resElements, imgSrcPath);

						if (imgActualPath == null || imgActualPath.length() == 0)
						{
							imgindex  = -1;
							startSrc = 0;
							endSrc = 0;
							checkLink = null;
							continue;
						}

						// look for embedded data within resources html file
						if (imgSrcPath.endsWith(".htm") || imgSrcPath.endsWith(".html"))
						{
							if (!checkEmbedHTMLResources.contains(imgActualPath))
							{
								checkEmbedHTMLResources.add(imgActualPath);
								String embedContentData = null;
								byte[] embedByteData = readData(unZippedDirPath, imgActualPath);
								if (embedByteData != null && (embedContentData = new String(embedByteData)) != null)
								{
									ArrayList<?> contentData = createHTMLFile(embedContentData, resElements, unZippedDirPath, courseId,
											checkEmbedHTMLResources, null);
									embedContentData = (String) contentData.get(0);
									checkEmbedHTMLResources = (Set) contentData.get(1);
									String filename = imgActualPath.substring(imgActualPath.lastIndexOf('/') + 1);
									try
									{
										String checkResourceId = getMeleteCHService().getUploadCollectionId(courseId) + filename;
										getMeleteCHService().checkResource(checkResourceId);
									}
									catch (IdUnusedException ex)
									{
										addResource(filename, null, embedContentData.getBytes(), courseId);
									}
									catch (Exception e)
									{
										logger.debug("error adding a secondary html resource on ims import");
									}
								}
							}
							else
							{
								// logger.debug("already processed file" + imgActualPath);
								imgindex = -1;
								startSrc = 0;
								endSrc = 0;
								checkLink = null;
								String replacementStr = "/access/meleteDocs/content/private/meleteDocs/" + courseId + "/uploads/"
								+ imgSrcPath.substring(imgSrcPath.lastIndexOf('/') + 1);
								contentEditor = meleteUtil.replace(contentEditor, patternStr, replacementStr);
								continue;
							}
						}
						contentEditor = ReplaceEmbedMediaWithResourceURL(contentEditor, patternStr, imgActualPath, courseId, unZippedDirPath,
								anchorString);
					} // if check for images
				} // if http check end
			}// IMS import (original code) ends here

			imgindex = -1;
			startSrc = 0;
			endSrc = 0;
			checkLink = null;
		}
		ArrayList<Object> returnData = new ArrayList<Object>();
		returnData.add(contentEditor);
		returnData.add(checkEmbedHTMLResources);
		return returnData;
			}

	/**
	 * abstract method implementation to process embed media found in html files
	 * 
	 * @param hrefVal
	 *        embedded resource url
	 * @param courseId
	 *        The site Id
	 * @param unZippedDirPath
	 *        package unzipped location
	 */
	protected String uploadSectionDependentFile(String hrefVal, String courseId, String unZippedDirPath)
	{
		try
		{
			String filename = hrefVal;
			byte[] melContentData = null;

			if (hrefVal.lastIndexOf('/') != -1) filename = hrefVal.substring(hrefVal.lastIndexOf('/') + 1);
			// filename read is PostcardStPatricksDay (1).jpg
			if (filename != null && filename.trim().length() > 0)
			{
				try
				{
					String checkResourceId = getMeleteCHService().getUploadCollectionId(courseId) + Validator.escapeResourceName(filename);
					getMeleteCHService().checkResource(checkResourceId);

					// found it so return it
					return getMeleteCHService().getResourceUrl(checkResourceId);
				}
				catch (IdUnusedException ex)
				{
					melContentData = readData(unZippedDirPath, hrefVal);
					return addResource(filename, null, melContentData, courseId);
				}
				catch (Exception e)
				{
					// logger.debug(e.toString());
				}
			}
		}
		catch (Exception e)
		{
			// do nothing
		}
		return "";
	}

	/**
	 * If item tag for module is missing then add it. used for moodle packages.
	 * 
	 * @param eleItem
	 * @param eleParentOrganization
	 * @return
	 * @throws Exception
	 */
	private org.dom4j.Element checkModuleItem(org.dom4j.Element eleItem, org.dom4j.Element eleParentOrganization) throws Exception
	{
		org.dom4j.Attribute identifierref = eleItem.attribute("identifierref");
		if (identifierref == null) return null;
		List<?> elements = eleParentOrganization.elements();
		org.dom4j.Element newModuleElement = eleParentOrganization.addElement("item");
		for (Iterator<?> iter = elements.iterator(); iter.hasNext();)
		{
			org.dom4j.Element e = (org.dom4j.Element) iter.next();
			newModuleElement.add(e.createCopy());
			eleParentOrganization.remove(e);
		}
		return newModuleElement;
	}

	/**
	 * {@inheritDoc}
	 */
	public void parseAndBuildModules(String courseId, Document document, String unZippedDirPath, String userId) throws Exception
	{
		if (logger.isDebugEnabled()) logger.debug("Entering parseAndBuildModules");

		Map<String,String> uris = new HashMap<String,String>();
		uris.put("imscp", DEFAULT_NAMESPACE_URI);
		uris.put("imsmd", IMSMD_NAMESPACE_URI);

		// organizations
		XPath xpath = document.createXPath("/imscp:manifest/imscp:organizations/imscp:organization");
		xpath.setNamespaceURIs(uris);

		Element eleOrg = (Element) xpath.selectSingleNode(document);

		// build module
		// loop thru organization elements - item elements
		if (eleOrg != null)
		{
			importThreadLocal.set("MeleteIMSImportFiles", new HashSet<String>());

			for (Iterator<?> iter = eleOrg.elementIterator("item"); iter.hasNext();)
			{
				Element element = (Element) iter.next();
				// for moodle packages which don't have parent item tag for modules.
				Element blankElement = checkModuleItem(element, eleOrg);
				if (blankElement != null)
				{
					buildModule(blankElement, document, unZippedDirPath, courseId, userId);
					break;
				}
				else
					buildModule(element, document, unZippedDirPath, courseId, userId);
			}
		}

		xpath = document.createXPath("/imscp:manifest/imscp:resources");
		xpath.setNamespaceURIs(uris);

		Element eleResource = (Element) xpath.selectSingleNode(document);
		if (eleResource != null) processManageItems(eleResource, unZippedDirPath, courseId, document);

		if (logger.isDebugEnabled()) logger.debug("Exiting parseAndBuildModules");
	}

	/**
	 * Builds the module for each Item element under organization
	 * 
	 * @param eleItem
	 *        item element
	 * @exception throws exception
	 */
	private void buildModule(Element eleItem, Document document, String unZippedDirPath, String courseId, String userId) throws Exception
	{

		if (logger.isDebugEnabled()) logger.debug("Entering buildModule...");

		// create module object
		Module module = new Module();
		boolean moduleTitleFlag = false;
		if (eleItem.attribute("isvisible") != null)
		{
			if (((Attribute) eleItem.attribute("isvisible")).getValue().equals("false"))
			{
				CourseModule cmod = new CourseModule(courseId, -1, true, null, false, module);
				module.setCoursemodule(cmod);
			}
		}
		if (eleItem.elements("title") != null && eleItem.elements("title").size() != 0)
		{
			Element titleEle = (Element) eleItem.elements("title").get(0);
			if (titleEle != null)
			{
				String title = titleEle.getTextTrim();
				if (title != null && title.length() != 0)
				{
					module.setTitle(title);
					moduleTitleFlag = true;
				}
			}
		}
		if (!moduleTitleFlag) module.setTitle("Untitled Module");

		boolean keywords = false;
		boolean descr = false;
		if (eleItem.selectNodes("./imsmd:lom/imsmd:general") != null && eleItem.selectNodes("./imsmd:lom/imsmd:general").size() != 0)
		{
			Element generalElement = (Element) eleItem.selectNodes("./imsmd:lom/imsmd:general").get(0);
			List<?> moduleMetadataList = generalElement.elements();
			for (Iterator<?> iter = moduleMetadataList.iterator(); iter.hasNext();)
			{
				Element metaElement = (Element) iter.next();

				if (metaElement.getName().equals("description"))
				{
					String desc = metaElement.selectSingleNode(".//imsmd:langstring").getText();
					module.setDescription(desc.trim());
					descr = true;
				}

				if (metaElement.getName().equals("keyword"))
				{
					String modkeyword = metaElement.selectSingleNode(".//imsmd:langstring").getText();
					if (modkeyword != null)
					{
						module.setKeywords(modkeyword.trim());
						keywords = true;
					}
				}
			}
		}
		if (!keywords) module.setKeywords(module.getTitle());
		if (!descr) module.setDescription("    ");
		createModule(module, courseId, userId);

		// build sections
		try
		{
			sectionUtil = new SubSectionUtilImpl();
			Document seqDocument = sectionUtil.createSubSection4jDOM();

			for (Iterator<?> iter = eleItem.elementIterator("item"); iter.hasNext();)
			{
				Element element = (Element) iter.next();

				if (element.attributeValue("identifier").startsWith("NEXTSTEPS"))
					buildWhatsNext(element, document, module, unZippedDirPath);
				else
					buildSection(element, document, module, addBlankSection(null, seqDocument), unZippedDirPath, seqDocument, courseId, userId);
			}

			// update module seqXml
			// logger.debug("checking seqXML now at the end of buildModule process" + seqDocument.asXML());
			module = moduleDB.getModule(module.getModuleId());
			module.setSeqXml(seqDocument.asXML());
			moduleDB.updateModule(module);
		}
		catch (Exception e)
		{
			// e.printStackTrace();
			throw e;
		}
		if (logger.isDebugEnabled()) logger.debug("Exiting buildModule...");
	}

	/**
	 * 
	 * @param parentElement
	 * @param seqDocument
	 * @return
	 */
	private Element addBlankSection(Element parentElement, Document seqDocument)
	{
		if (parentElement == null) parentElement = seqDocument.getRootElement();
		Element newSectionElement = parentElement.addElement("section");
		return newSectionElement;
	}

	/**
	 * Get whats next information
	 * 
	 * @param eleItem
	 * @param document
	 * @param module
	 * @param unZippedDirPath
	 * @throws Exception
	 */
	private void buildWhatsNext(Element eleItem, Document document, Module module, String unZippedDirPath) throws Exception
	{
		Attribute identifierref = eleItem.attribute("identifierref");
		Element eleRes;

		if (identifierref != null)
		{
			eleRes = getResource(identifierref.getValue(), document);
			String hrefVal = eleRes.attributeValue("href");
			String nextsteps = new String(readData(unZippedDirPath, hrefVal));
			module.setWhatsNext(nextsteps);
			moduleDB.updateModuleNextSteps(module.getModuleId(), nextsteps);
		}

	}

	/**
	 * 
	 * @param eleItem
	 * @param document
	 * @param module
	 * @param unZippedDirPath
	 * @throws Exception
	 */
	private void mergeWhatsNext(Element eleItem, Document document, Module module, String unZippedDirPath) throws Exception
	{
		Attribute identifierref = eleItem.attribute("identifierref");
		Element eleRes;

		if (identifierref != null)
		{
			eleRes = getMergeResource(identifierref.getValue(), document);
			String hrefVal = eleRes.attributeValue("href");
			String nextsteps = new String(readData(unZippedDirPath, hrefVal));
			module.setWhatsNext(nextsteps);
			moduleDB.updateModule(module);
		}

	}

	/**
	 * creates the module
	 * 
	 * @param module
	 * @param courseId
	 * @throws Exception
	 */
	private void createModule(Module module, String courseId, String userId) throws Exception
	{
		if (logger.isDebugEnabled()) logger.debug("Entering createModule...");

		String firstName = UserDirectoryService.getUser(userId).getFirstName();
		String lastName = UserDirectoryService.getUser(userId).getLastName();

		module.setUserId(userId);
		module.setModuleshdate(getModuleShdates());
		if (module.getCoursemodule() != null)
		{
			if (module.getCoursemodule().isArchvFlag() == true)
			{
				moduleDB.addArchivedModule(module, getModuleShdates(), userId, courseId, (CourseModule) module.getCoursemodule());
			}
		}
		else
		{
			moduleDB.addModule(module, getModuleShdates(), userId, courseId);
		}
		if (logger.isDebugEnabled()) logger.debug("Exiting createModule...");
	}

	/**
	 * Builds section for each item under module item
	 * 
	 * @param eleItem
	 *        item element
	 * @param document
	 *        document
	 * @param module
	 *        Module
	 * @throws Exception
	 */
	private void buildSection(Element eleItem, Document document, Module module, Element seqElement, String unZippedDirPath, Document seqDocument,
			String courseId, String userId) throws Exception
			{
		if (logger.isDebugEnabled()) logger.debug("Entering buildSection...");

		Attribute identifier = eleItem.attribute("identifier");
		// logger.debug("importing ITEM " + identifier.getValue());

		Attribute identifierref = eleItem.attribute("identifierref");
		Element eleRes;

		Section section = new Section();
		MeleteResource meleteResource = new MeleteResource();
		boolean sectionTitleFlag = false;
		boolean sectionCopyrightFlag = false;

		List<?> elements = eleItem.elements();
		for (Iterator<?> iter = elements.iterator(); iter.hasNext();)
		{
			Element element = (Element) iter.next();

			// title
			if (element.getQualifiedName().equalsIgnoreCase("title"))
			{
				section.setTitle(element.getTextTrim());
				sectionTitleFlag = true;
			}
			// item
			else if (element.getQualifiedName().equalsIgnoreCase("item"))
			{
				// call recursive here
				buildSection(element, document, module, addBlankSection(seqElement, seqDocument), unZippedDirPath, seqDocument, courseId, userId);
			}
			// metadata
			else if (element.getQualifiedName().equalsIgnoreCase("imsmd:lom"))
			{
				// section instructions
				Element DescElement = null;
				if (eleItem.selectNodes("./imsmd:lom/imsmd:general/imsmd:description") != null
						&& (eleItem.selectNodes("./imsmd:lom/imsmd:general/imsmd:description").size() != 0))
					DescElement = (Element) eleItem.selectNodes("./imsmd:lom/imsmd:general/imsmd:description").get(0);

				if (DescElement != null)
				{
					String instr = DescElement.selectSingleNode(".//imsmd:langstring").getText();
					section.setInstr(instr.trim());
				}

				// read license information
				Element rightsElement = null;
				if (eleItem.selectNodes("./imsmd:lom/imsmd:rights") != null && (eleItem.selectNodes("./imsmd:lom/imsmd:rights").size() != 0))
					rightsElement = (Element) eleItem.selectNodes("./imsmd:lom/imsmd:rights").get(0);

				if (rightsElement != null)
				{
					Element licenseElement = rightsElement.element("description");
					String licenseUrl = licenseElement.selectSingleNode(".//imsmd:langstring").getText();
					if (licenseUrl != null) buildLicenseInformation(meleteResource, licenseUrl);
					sectionCopyrightFlag = true;
				}
			}
			// license end
		}
		// other attributes
		// logger.debug("setting section attribs");
		String firstName = UserDirectoryService.getUser(userId).getFirstName();
		String lastName = UserDirectoryService.getUser(userId).getLastName();

		section.setTextualContent(true);
		section.setContentType("notype");

		if (!sectionTitleFlag) section.setTitle("Untitled Section");

		// default to no license
		if (!sectionCopyrightFlag)
		{
			meleteResource.setLicenseCode(RESOURCE_LICENSE_CODE);
			meleteResource.setCcLicenseUrl(RESOURCE_LICENSE_URL);
		}
		// save section object
		Integer new_section_id = sectionDB.addSection(module, section, true, userId);
		section.setSectionId(new_section_id);
		seqElement.addAttribute("id", new_section_id.toString());

		// now melete resource object
		if (identifierref != null)
		{
			eleRes = getResource(identifierref.getValue(), document);
			if (eleRes != null)
			{
				Attribute resHrefAttr = eleRes.attribute("href");

				if (resHrefAttr != null)
				{
					String hrefVal = resHrefAttr.getValue();
					// logger.debug("hrefVal:" + hrefVal);
					// check if file is missing
					if (hrefVal != null && hrefVal.length() != 0
							&& !(hrefVal.startsWith("http://") || hrefVal.startsWith("https://") || hrefVal.startsWith("mailto:")))
					{
						hrefVal = getFileNamefromElement(eleRes.elements(), hrefVal);
						if (!meleteUtil.checkFileExists(unZippedDirPath + File.separator + hrefVal))
						{
							logger.info("content file for section is missing so move ON");
							return;
						}
					}
					// end missing file check

					// create meleteResourceObject
					List<Element> resElements = eleRes.elements();
					createContentResource(module, section, meleteResource, hrefVal, resElements, unZippedDirPath, courseId);

				} // resHrefAttr check end
			}
		}

		if (logger.isDebugEnabled()) logger.debug("Exiting buildSection...");
			}

	/**
	 * Builds section for each item under module item
	 * 
	 * @param eleItem
	 *        item element
	 * @param document
	 *        document
	 * @param module
	 *        Module
	 * @throws Exception
	 */
	private void mergeSection(Element eleItem, Document document, Module module, Element seqElement, String unZippedDirPath, Document seqDocument,
			String courseId, String userId) throws Exception
			{
		if (logger.isDebugEnabled()) logger.debug("Entering mergeSection...");

		Attribute identifier = eleItem.attribute("identifier");
		// // logger.debug("importing ITEM " + identifier.getValue());

		Attribute identifierref = eleItem.attribute("identifierref");
		Element eleRes;

		Section section = new Section();
		MeleteResource meleteResource = new MeleteResource();
		boolean sectionTitleFlag = false;
		boolean sectionCopyrightFlag = false;

		List<?> elements = eleItem.elements();
		for (Iterator<?> iter = elements.iterator(); iter.hasNext();)
		{
			Element element = (Element) iter.next();

			// title
			if (element.getQualifiedName().equalsIgnoreCase("title"))
			{
				section.setTitle(element.getTextTrim());
				sectionTitleFlag = true;
			}
			// item
			else if (element.getQualifiedName().equalsIgnoreCase("item"))
			{
				// call recursive here
				buildSection(element, document, module, addBlankSection(seqElement, seqDocument), unZippedDirPath, seqDocument, courseId, userId);
			}
			// metadata
			else if (element.getName().equalsIgnoreCase("imsmd:lom"))
			{
				// section instructions
				List<?> modulegeneralList = element.elements();
				List<?> moduleMetadataList = ((Element)modulegeneralList.get(0)).elements();

				for (Iterator<?> iter2 = moduleMetadataList.iterator(); iter2.hasNext();)
				{
					Element metaElement = (Element) iter2.next();

					if (metaElement.getName().equals("imsmd:description") && metaElement.element("imsmd:langstring") != null)
					{
						String instr = metaElement.element("imsmd:langstring").getText();
						section.setInstr(instr.trim());
					}
				}

				// read license information
				if (modulegeneralList.size() > 1)
				{
					List<?> rightList = ((Element)modulegeneralList.get(1)).elements();
					for (Iterator<?> iter3 = rightList.iterator(); iter3.hasNext();)
					{
						Element rightsElement = (Element) iter3.next();

						if (rightsElement.getName().equals("imsmd:description") && rightsElement.element("imsmd:langstring") != null)
						{
							String licenseUrl = rightsElement.element("imsmd:langstring").getText();
							if (licenseUrl != null)
							{
								buildLicenseInformation(meleteResource, licenseUrl);
								sectionCopyrightFlag = true;
							}
						}
					}
				}
			}
			// license end
		}
		// other attributes
		// logger.debug("setting section attribs");
		String firstName = UserDirectoryService.getUser(userId).getFirstName();
		String lastName = UserDirectoryService.getUser(userId).getLastName();

		section.setTextualContent(true);
		section.setContentType("notype");

		if (!sectionTitleFlag) section.setTitle("Untitled Section");

		// default to no license
		if (!sectionCopyrightFlag)
		{
			meleteResource.setLicenseCode(RESOURCE_LICENSE_CODE);
			meleteResource.setCcLicenseUrl(RESOURCE_LICENSE_URL);
		}
		// save section object
		Integer new_section_id = sectionDB.addSection(module, section, true, userId);
		section.setSectionId(new_section_id);
		seqElement.addAttribute("id", new_section_id.toString());

		// now melete resource object
		if (identifierref != null)
		{
			eleRes = getMergeResource(identifierref.getValue(), document);
			if (eleRes != null)
			{
				Attribute resHrefAttr = eleRes.attribute("href");

				if (resHrefAttr != null)
				{
					String hrefVal = resHrefAttr.getValue();

					// check if file is missing
					if (hrefVal != null && hrefVal.length() != 0
							&& !(hrefVal.startsWith("http://") || hrefVal.startsWith("https://") || hrefVal.startsWith("mailto:")))
					{
						if (!meleteUtil.checkFileExists(unZippedDirPath + File.separator + hrefVal))
						{
							logger.info("content file for section is missing so move ON");
							return;
						}
					}
					// end missing file check

					// create meleteResourceObject
					List<Element> resElements = eleRes.elements();
					createContentResource(module, section, meleteResource, hrefVal, resElements, unZippedDirPath, courseId);

				} // resHrefAttr check end
			}
		}

		if (logger.isDebugEnabled()) logger.debug("Exiting mergeSection...");
			}

	/**
	 * Adds Embedded media as a melete Resource.
	 * 
	 * @param hrefVal
	 *        the actual filename like <file href="xxxx.xxx"/>
	 * @param fileResourceName
	 * @param courseId
	 * @param uploadCollId
	 * @param resElements
	 * @param unZippedDirPath
	 * @return
	 * @throws Exception
	 */
	private String processEmbedDatafromHTML(String hrefVal, String fileResourceName, String courseId, String uploadCollId, List<Element> resElements,
			String unZippedDirPath) throws Exception
			{
		byte[] contentData = readData(unZippedDirPath, hrefVal);

		if (contentData != null)
		{
			String contentEditor = new String(contentData);
			ArrayList<?> content = createHTMLFile(contentEditor, resElements, unZippedDirPath, courseId, new HashSet<String>(), null);
			if (fileResourceName == null) fileResourceName = readTitlefromElement(resElements, true, hrefVal);
			String melResourceDescription = readDescriptionFromElement(resElements);
			contentEditor = (String) content.get(0);
			ResourcePropertiesEdit res = getMeleteCHService().fillInSectionResourceProperties(true, fileResourceName, melResourceDescription);
			return getMeleteCHService().addResourceItem(fileResourceName, MeleteCHService.MIME_TYPE_EDITOR, uploadCollId,
					contentEditor.getBytes(), res);
		}
		return null;
			}

	/**
	 * creates the section
	 * 
	 * @param module
	 *        Module
	 * @param section
	 *        Section
	 * @param hrefVal
	 *        href value of the item
	 * @return
	 * @throws MalformedURLException
	 * @throws UnknownHostException
	 * @throws MeleteException
	 * @throws Exception
	 */
	private void createContentResource(Module module, Section section, MeleteResource meleteResource, String hrefVal, List<Element> resElements,
			String unZippedDirPath, String courseId) throws MalformedURLException, UnknownHostException, MeleteException, Exception
			{
		String newResourceId = "";

		if (logger.isDebugEnabled()) logger.debug("Entering createSection...");

		Set<String> recordFiles = (Set) importThreadLocal.get("MeleteIMSImportFiles");
		if (recordFiles != null) recordFiles.add(hrefVal);
		// html file
		if (!(hrefVal.startsWith("http://") || hrefVal.startsWith("https://") || hrefVal.startsWith("mailto:"))
				&& (hrefVal.lastIndexOf('.') != -1 && (hrefVal.substring(hrefVal.lastIndexOf('.') + 1).equalsIgnoreCase("html") || hrefVal.substring(
						hrefVal.lastIndexOf('.') + 1).equalsIgnoreCase("htm"))))
		{
			// This is for typeEditor sections
			section.setContentType("typeEditor");
			String addCollId = getMeleteCHService().getCollectionId(courseId, section.getContentType(), module.getModuleId());
			String sectionResourceName = getMeleteCHService().getTypeEditorSectionName(section.getSectionId());
			newResourceId = processEmbedDatafromHTML(hrefVal, sectionResourceName, courseId, addCollId, resElements, unZippedDirPath);
			meleteResource.setResourceId(newResourceId);
			sectionDB.insertMeleteResource((Section) section, (MeleteResource) meleteResource);
			return;
		}

		if (hrefVal.startsWith("http://") || hrefVal.startsWith("https://") || hrefVal.startsWith("mailto:"))
		{
			// link
			section.setContentType("typeLink");
			section.setOpenWindow(true);
		}
		else
		{
			// Load up the resource to look at it
			// String fileName = ((Element)resElements.get(0)).attributeValue("href");
			String fileName = getFileNamefromElement(resElements, hrefVal);
			String contentEditor = new String(readData(unZippedDirPath, fileName));
			if (contentEditor == null) return;
			if (isLTIDocument(contentEditor))
				section.setContentType("typeLTI");
			else
				section.setContentType("typeUpload");
		}

		String rdata = checkAndAddResource(hrefVal, section.getContentType(), courseId, resElements, unZippedDirPath, true);
		if (rdata != null && rdata.length() != 0)
		{
			meleteResource.setResourceId(rdata);
			sectionDB.insertMeleteResource((Section) section, (MeleteResource) meleteResource);
		}

		if (logger.isDebugEnabled()) logger.debug("Exiting createSection...");
			}

	/**
	 * Get Resource element from the manifest file.
	 * 
	 * @param resName
	 * @param document
	 * @return
	 * @throws Exception
	 */
	private Element getResource(String resName, Document document) throws Exception
	{
		if (logger.isDebugEnabled()) logger.debug("Entering getResource...");

		Map<String, String> uris = new HashMap<String, String> ();
		uris.put("imscp", DEFAULT_NAMESPACE_URI);
		uris.put("imsmd", IMSMD_NAMESPACE_URI);

		// resource
		XPath xpath = document.createXPath("/imscp:manifest/imscp:resources/imscp:resource[@identifier = '" + resName + "']");
		xpath.setNamespaceURIs(uris);

		Element eleRes = (Element) xpath.selectSingleNode(document);

		if (logger.isDebugEnabled()) logger.debug("Exiting getResource...");

		return eleRes;
	}

	/**
	 * Get resource from the sakai archive document
	 * 
	 * @param resName
	 * @param document
	 * @return
	 * @throws Exception
	 */
	private Element getMergeResource(String resName, Document document) throws Exception
	{
		if (logger.isDebugEnabled()) logger.debug("Entering getResource...");

		Map<String, String>  uris = new HashMap<String, String> ();
		uris.put("imscp", DEFAULT_NAMESPACE_URI);
		uris.put("imsmd", IMSMD_NAMESPACE_URI);

		// resource
		XPath xpath = document.createXPath("//resource[@identifier = '" + resName + "']");
		xpath.setNamespaceURIs(uris);

		Element eleRes = (Element) xpath.selectSingleNode(document);

		if (logger.isDebugEnabled()) logger.debug("Exiting getResource...");

		return eleRes;
	}

	/**
	 * {@inheritDoc}
	 */
	public String getContentSourceInfo(Document document)
	{
		Map<String, String>  uris = new HashMap<String, String> ();
		uris.put("imscp", DEFAULT_NAMESPACE_URI);
		uris.put("imsmd", IMSMD_NAMESPACE_URI);

		try
		{
			// description
			XPath xpath = document.createXPath("/imscp:manifest/imscp:metadata/imsmd:lom/imsmd:rights/imsmd:description");
			xpath.setNamespaceURIs(uris);

			Element eleOrg = (Element) xpath.selectSingleNode(document);
			if (eleOrg != null)
			{
				// logger.debug("got desc element" + eleOrg.toString());
				return eleOrg.selectSingleNode(".//imsmd:langstring").getText();
			}
			else
				return null;
		}
		catch (Exception e)
		{
			logger.debug("error in reading other contact info" + e.toString());
			return null;
		}
	}

	/**
	 * Parse license information and populate melete resource object
	 * 
	 * @param meleteResource
	 * @param licenseUrl
	 */
	protected void buildLicenseInformation(MeleteResource meleteResource, String licenseUrl)
	{
		int lcode = RESOURCE_LICENSE_CODE;

		if (licenseUrl.startsWith("Copyright (c)"))
		{
			lcode = RESOURCE_LICENSE_COPYRIGHT_CODE;
			// remove copyright(c) phrase
			String otherInfo = licenseUrl.substring(13);
			otherInfo = otherInfo.trim();
			int commaPos = otherInfo.indexOf(",");
			if (commaPos != -1)
			{
				processLicenseStr(otherInfo, meleteResource);
			}

		}
		else if (licenseUrl.startsWith("Public Domain"))
		{
			lcode = RESOURCE_LICENSE_PD_CODE;
			int nameIdx = licenseUrl.indexOf(",");
			String licensename = licenseUrl.trim();
			if (nameIdx != -1)
			{
				licensename = licenseUrl.substring(0, nameIdx);
				String otherInfo = licenseUrl.substring(nameIdx + 1);
				otherInfo = otherInfo.trim();
				if (otherInfo != null)
				{
					int commaPos = otherInfo.indexOf(",");
					if (commaPos != -1)
					{
						processLicenseStr(otherInfo, meleteResource);
					}
					else
					{
						meleteResource.setCopyrightOwner(otherInfo);
					}

				}
			}
			CcLicense ccl = meleteLicenseDB.fetchCcLicenseUrl(licensename);
			licenseUrl = ccl.getUrl();

		}
		else if (licenseUrl.startsWith("Creative Commons"))
		{
			lcode = RESOURCE_LICENSE_CC_CODE;
			// remove "creative commons" phrase from the name
			licenseUrl = licenseUrl.substring(17);
			int nameIdx = licenseUrl.indexOf(",");
			String licensename = licenseUrl.trim();
			if (nameIdx != -1)
			{
				licensename = licenseUrl.substring(0, nameIdx);
				String otherInfo = licenseUrl.substring(nameIdx + 1);
				otherInfo = otherInfo.trim();
				if (otherInfo != null)
				{
					int commaPos = otherInfo.indexOf(",");
					if (commaPos != -1)
					{
						processLicenseStr(otherInfo, meleteResource);
					}
					else
					{
						meleteResource.setCopyrightOwner(otherInfo);
					}

				}
			}
			CcLicense ccl = meleteLicenseDB.fetchCcLicenseUrl(licensename);
			licenseUrl = ccl.getUrl();
			meleteResource.setReqAttr(true);
			meleteResource.setAllowCmrcl(ccl.isAllowCmrcl());
			meleteResource.setAllowMod(ccl.getAllowMod());

		}
		else if (licenseUrl.startsWith("Copyrighted Material"))
		{
			lcode = RESOURCE_LICENSE_FAIRUSE_CODE;
			int nameIdx = licenseUrl.indexOf(",");
			String licensename = licenseUrl.trim();
			if (nameIdx != -1)
			{
				licensename = licenseUrl.substring(0, nameIdx);
				String otherInfo = licenseUrl.substring(nameIdx + 1);
				otherInfo = otherInfo.trim();
				if (otherInfo != null)
				{
					int commaPos = otherInfo.indexOf(",");
					if (commaPos != -1)
					{
						processLicenseStr(otherInfo, meleteResource);
					}
					else
					{
						meleteResource.setCopyrightOwner(otherInfo);
					}

				}
			}
			licenseUrl = licensename;
		}

		meleteResource.setLicenseCode(lcode);
		meleteResource.setCcLicenseUrl(licenseUrl);
	}

	/**
	 * Parse and populate copyright owner and year information.
	 * 
	 * @param otherInfo
	 * @param meleteResource
	 */
	protected void processLicenseStr(String otherInfo, MeleteResource meleteResource)
	{
		String firstStr = null, secondStr = null;
		int commaPos = otherInfo.indexOf(",");
		while (commaPos != -1)
		{
			firstStr = otherInfo.substring(0, commaPos).trim();
			secondStr = otherInfo.substring(commaPos + 1).trim();
			if (!(Character.isDigit(firstStr.charAt(firstStr.length() - 1))) && (Character.isDigit(secondStr.charAt(0))))
			{
				break;
			}
			else
			{
				commaPos = otherInfo.indexOf(",", commaPos + 1);
			}

		}
		meleteResource.setCopyrightOwner(firstStr);
		meleteResource.setCopyrightYear(secondStr);
	}

	/**
	 * create an instance of moduleshdates with open dates
	 * 
	 */
	protected ModuleShdates getModuleShdates()
	{
		if (moduleShdates == null)
		{
			moduleShdates = new ModuleShdates();
		}
		return moduleShdates;
	}
}
