/**********************************************************************************
 *
 * $URL: https://source.sakaiproject.org/contrib/etudes/melete/tags/2.9.9/melete-impl/src/java/org/etudes/component/app/melete/MeleteCHServiceImpl.java $
 * $Id: MeleteCHServiceImpl.java 86862 2014-08-08 20:37:38Z mallika@etudes.org $
 ***********************************************************************************
 *
 * Copyright (c) 2008,2009,2010,2011,2014 Etudes, Inc.
 *
 * Portions completed before September 1, 2008 Copyright (c) 2004, 2005, 2006, 2007, 2008 Foothill College, ETUDES Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
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
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.etudes.api.app.melete.MeleteCHService;
import org.etudes.api.app.melete.MeleteSecurityService;
import org.etudes.api.app.melete.exception.MeleteException;
import org.etudes.util.HtmlHelper;
import org.sakaiproject.component.cover.ServerConfigurationService;
import org.sakaiproject.content.api.ContentCollection;
import org.sakaiproject.content.api.ContentCollectionEdit;
import org.sakaiproject.content.api.ContentEntity;
import org.sakaiproject.content.api.ContentHostingService;
import org.sakaiproject.content.api.ContentResource;
import org.sakaiproject.content.api.ContentResourceEdit;
import org.sakaiproject.content.api.ResourceType;
import org.sakaiproject.entity.api.Entity;
import org.sakaiproject.entity.api.Reference;
import org.sakaiproject.entity.api.ResourceProperties;
import org.sakaiproject.entity.api.ResourcePropertiesEdit;
import org.sakaiproject.entity.cover.EntityManager;
import org.sakaiproject.exception.IdInvalidException;
import org.sakaiproject.exception.IdLengthException;
import org.sakaiproject.exception.IdUniquenessException;
import org.sakaiproject.exception.IdUnusedException;
import org.sakaiproject.exception.IdUsedException;
import org.sakaiproject.exception.InUseException;
import org.sakaiproject.exception.InconsistentException;
import org.sakaiproject.exception.OverQuotaException;
import org.sakaiproject.exception.PermissionException;
import org.sakaiproject.exception.ServerOverloadException;
import org.sakaiproject.exception.TypeException;
import org.sakaiproject.util.Validator;

public class MeleteCHServiceImpl implements MeleteCHService
{
	/** Dependency: The logging service. */
	public Log logger = LogFactory.getLog(MeleteCHServiceImpl.class);
	private ContentHostingService contentservice;
	private static final int MAXIMUM_ATTEMPTS_FOR_UNIQUENESS = 100;
	private SectionDB sectiondb;
	/** Dependency: The Melete Security service. */
	private MeleteSecurityService meleteSecurityService;
	protected MeleteUtil meleteUtil = new MeleteUtil();

	/** This string starts the references to resources in this service. */
	static final String REFERENCE_ROOT = Entity.SEPARATOR + "meleteDocs";

	/**
	 * Check if the current user has permission as author to a site.
	 * 
	 * @return true if the current user has permission to perform this action, false if not.
	 */
	public boolean isUserAuthor(String reference) throws Exception
	{

		try
		{
			return meleteSecurityService.allowAuthor(reference);
		}
		catch (Exception e)
		{
			throw e;
		}
	}

	/**
	 * Check if the current user has permission as student to a site.
	 * 
	 * @return true if the current user has permission to perform this action, false if not.
	 */
	public boolean isUserStudent(String reference) throws Exception
	{

		try
		{
			return meleteSecurityService.allowStudent(reference);
		}
		catch (Exception e)
		{
			throw e;
		}
	}

	/**
	 * Parse to get the site id.
	 * 
	 * @param inputStr
	 *        resource id or collection id
	 * @return
	 */
	private String getCourseId(String inputStr)
	{
		String courseId = null;
		String meleteCollectionRef = Entity.SEPARATOR + "private" + REFERENCE_ROOT;
		String groupCollectionRef = Entity.SEPARATOR + "group";
		org.sakaiproject.entity.api.Reference ref1 = null;

		if ((inputStr != null) && (inputStr.length() > 0))
		{
			if (inputStr.startsWith(meleteCollectionRef))
			{
				inputStr = inputStr.replace(meleteCollectionRef, "/site");
				ref1 = org.sakaiproject.entity.cover.EntityManager.newReference(inputStr);
				courseId = ref1.getContainer();
			}
			else
			{
				if (inputStr.startsWith(groupCollectionRef))
				{
					inputStr = inputStr.replace(groupCollectionRef, "/site");
					ref1 = org.sakaiproject.entity.cover.EntityManager.newReference(inputStr);
					courseId = ref1.getContainer();
				}
			}
		}
		return courseId;
	}

	/**
	 * Creates melete root collection if it doesn't exist.
	 * 
	 * @param rootCollectionRef
	 *        /private/meleteDocs reference
	 * @param collectionName
	 *        collection name
	 * @param description
	 *        collection description
	 * @return
	 */
	private String addMeleteRootCollection(String rootCollectionRef, String collectionName, String description)
	{
		try
		{
			// Check to see if meleteDocs exists
			ContentCollection collection = getContentservice().getCollection(rootCollectionRef);
			return collection.getId();
		}
		catch (IdUnusedException e)
		{
			// if not, create it
			if (logger.isDebugEnabled()) logger.debug("creating melete root collection " + rootCollectionRef);
			ContentCollectionEdit edit = null;
			try
			{
				edit = getContentservice().addCollection(rootCollectionRef);
				ResourcePropertiesEdit props = edit.getPropertiesEdit();
				props.addProperty(ResourceProperties.PROP_DISPLAY_NAME, collectionName);
				props.addProperty(ResourceProperties.PROP_DESCRIPTION, description);
				props.addProperty(ContentHostingService.PROP_ALTERNATE_REFERENCE, REFERENCE_ROOT);
				getContentservice().commitCollection(edit);
				return edit.getId();
			}
			catch (Exception e2)
			{
				if (edit != null) getContentservice().cancelCollection(edit);
				logger.warn("creating melete root collection: " + e2.toString());
			}
		}
		catch (Exception e)
		{
			logger.warn("checking melete root collection: " + e.toString());
		}
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	public String addCollectionToMeleteCollection(String meleteItemColl, String CollName)
	{
		String courseId = meleteItemColl.substring(0, meleteItemColl.indexOf(Entity.SEPARATOR));
		try
		{
			if (!isUserAuthor(courseId))
			{
				logger.info("User is not authorized to access meleteDocs collection");
				return null;
			}
			// setup a security advisor
			meleteSecurityService.pushAdvisor();
			try
			{
				// check if the root collection is available
				String rootCollectionRef = Entity.SEPARATOR + "private" + REFERENCE_ROOT + Entity.SEPARATOR;
				// Check to see if meleteDocs exists
				String rootMeleteCollId = addMeleteRootCollection(rootCollectionRef, "meleteDocs", "root collection");
				// now sub collection for course
				String meleteItemDirCollId = rootMeleteCollId + meleteItemColl + Entity.SEPARATOR;
				String subMeleteCollId = addMeleteRootCollection(meleteItemDirCollId, CollName, "module collection");
				return subMeleteCollId;
			}
			catch (Exception ex)
			{
				logger.error("error while creating modules collection" + ex.toString());
			}
		}
		catch (Throwable t)
		{
			logger.warn("init(): ", t);
		}
		finally
		{
			// clear the security advisor
			meleteSecurityService.popAdvisor();
		}

		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	public String getCollectionId(String courseId, String contentType, Integer modId)
	{
		String addToCollection = "";
		String collName = "";
		if (contentType.equals("typeEditor"))
		{
			addToCollection = courseId + Entity.SEPARATOR + "module_" + modId;
			collName = "module_" + modId;
		}
		else
		{
			addToCollection = courseId + Entity.SEPARATOR + "uploads";
			collName = "uploads";
		}
		// check if collection exists otherwise create it
		String addCollId = addCollectionToMeleteCollection(addToCollection, collName);
		return addCollId;
	}

	/**
	 * {@inheritDoc}
	 */
	public Integer getContainingModule(String sectionId)
	{
		if (sectionId == null) return null;
		Section s = sectiondb.getSection(new Integer(sectionId).intValue());
		if (s != null) return s.getModuleId();
		return null;
	}
	
	/**
	 * {@inheritDoc}
	 */
	public String getUploadCollectionId(String courseId)
	{
		try
		{
			String uploadCollectionId = courseId + Entity.SEPARATOR + "uploads";
			String collName = "uploads";
			// check if collection exists
			// read meletDocs dir name from web.xml
			String uploadCollId = addCollectionToMeleteCollection(uploadCollectionId, collName);
			return uploadCollId;
		}
		catch (Exception e)
		{
			logger.info("error accessing uploads directory");
			return null;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public List<ContentResource> getListofImagesFromCollection(String collId)
	{
		try
		{
			// setup a security advisor
			meleteSecurityService.pushAdvisor();

			long starttime = System.currentTimeMillis();
			logger.debug("time to get all collectionMap" + starttime);
			List<ContentResource> mem = getContentservice().getAllResources(collId);
			if (mem == null) return null;

			ListIterator<?> memIt = mem.listIterator();
			while (memIt != null && memIt.hasNext())
			{
				ContentEntity ce = (ContentEntity) memIt.next();
				if (ce.isResource())
				{
					String contentextension = ((ContentResource) ce).getProperties().getProperty(ce.getProperties().getNamePropContentType());

					if (!contentextension.startsWith("image"))
					{
						memIt.remove();
					}
				}
				else
					memIt.remove();
			}
			long endtime = System.currentTimeMillis();
			logger.debug("end time to get all collectionMap" + (endtime - starttime));
			return mem;
		}
		catch (Exception e)
		{
			logger.error(e.toString());
		}
		finally
		{
			// clear the security advisor
			meleteSecurityService.popAdvisor();
		}

		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	public List<ContentResource> getListofFilesFromCollection(String collId)
	{
		try
		{
			// setup a security advisor
			meleteSecurityService.pushAdvisor();

			long starttime = System.currentTimeMillis();
			logger.debug("time to get all collectionMap" + starttime);
			List<ContentResource> mem = getContentservice().getAllResources(collId);
			if (mem == null) return null;

			ListIterator<?> memIt = mem.listIterator();
			while (memIt != null && memIt.hasNext())
			{
				ContentEntity ce = (ContentEntity) memIt.next();
				if (ce.isResource())
				{
					String contentextension = ((ContentResource) ce).getContentType();
					if (contentextension.equals(MIME_TYPE_LINK) || contentextension.equals(MIME_TYPE_LTI))
					{
						memIt.remove();
					}
				}
				else
					memIt.remove();
			}
			long endtime = System.currentTimeMillis();
			logger.debug("end time to get all collectionMap" + (endtime - starttime));
			return mem;
		}
		catch (Exception e)
		{
			logger.error(e.toString());
		}
		finally
		{
			// clear the security advisor
			meleteSecurityService.popAdvisor();
		}

		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	public List<ContentResource> getListofLinksFromCollection(String collId)
	{
		try
		{
			if (!isUserAuthor(getCourseId(collId)))
			{
				logger.info("User is not authorized to access meleteDocs collection");
				return null;
			}
			// setup a security advisor
			meleteSecurityService.pushAdvisor();
			List<ContentResource> mem = getContentservice().getAllResources(collId);
			if (mem == null) return null;

			ListIterator<?> memIt = mem.listIterator();
			while (memIt != null && memIt.hasNext())
			{
				ContentEntity ce = (ContentEntity) memIt.next();
				if (ce.isResource())
				{
					String contentextension = ((ContentResource) ce).getContentType();
					if (!contentextension.equals(MIME_TYPE_LINK)) memIt.remove();
				}
				else
					memIt.remove();
			}

			return mem;
		}
		catch (Exception e)
		{
			logger.error(e.toString());
		}
		finally
		{
			// clear the security advisor
			meleteSecurityService.popAdvisor();
		}
		return null;
	}

	// TODO: Remove getListofLinksFromCollection
	/**
	 * {@inheritDoc}
	 */
	public List<ContentResource> getListFromCollection(String collId, String mimeType)
	{
		try
		{
			if (!isUserAuthor(getCourseId(collId)))
			{
				logger.info("User is not authorized to access meleteDocs collection");
			}
			// setup a security advisor
			meleteSecurityService.pushAdvisor();
			List<ContentResource> mem = getContentservice().getAllResources(collId);
			if (mem == null) return null;

			ListIterator<?> memIt = mem.listIterator();
			while (memIt != null && memIt.hasNext())
			{
				ContentEntity ce = (ContentEntity) memIt.next();
				if (ce.isResource())
				{
					String contentextension = ((ContentResource) ce).getContentType();
					if (!contentextension.equals(mimeType)) memIt.remove();
				}
				else
				{
					memIt.remove();
				}
			}

			return mem;
		}
		catch (Exception e)
		{
			logger.error(e.toString());
		}
		finally
		{
			// clear the security advisor
			meleteSecurityService.popAdvisor();
		}
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	public List<ContentResource> getListofMediaFromCollection(String collId)
	{
		try
		{
			if (!isUserAuthor(getCourseId(collId)))
			{
				logger.info("User is not authorized to access meleteDocs collection");
				return null;
			}
			// setup a security advisor
			meleteSecurityService.pushAdvisor();
			List<ContentResource> mem = getContentservice().getAllResources(collId);

			return mem;
		}
		catch (Exception e)
		{
			logger.error(e.toString());
		}
		finally
		{
			// clear the security advisor
			meleteSecurityService.popAdvisor();
		}
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	public List<String> getMemberNamesCollection(String collId)
	{
		try
		{
			if (!isUserAuthor(getCourseId(collId)))
			{
				logger.info("User is not authorized to access meleteDocs collection");
				return null;
			}
			// setup a security advisor
			meleteSecurityService.pushAdvisor();
			ContentCollection c = getContentservice().getCollection(collId);
			List<String> mem = c.getMembers();
			if (mem == null) return null;

			return mem;
		}
		catch (Exception e)
		{
			logger.error(e.toString());
		}
		finally
		{
			// clear the security advisor
			meleteSecurityService.popAdvisor();
		}
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	public ResourcePropertiesEdit fillInSectionResourceProperties(boolean encodingFlag, String secResourceName, String secResourceDescription)
	{
		ResourcePropertiesEdit resProperties = getContentservice().newResourceProperties();
		try
		{
			secResourceName = URLDecoder.decode(secResourceName, "UTF-8");
		}
		catch (Exception e)
		{
			logger.debug("error with decode in fillInSectionResourceProperties");
		}
		// resProperties.addProperty (ResourceProperties.PROP_COPYRIGHT,);
		// resourceProperties.addProperty(ResourceProperties.PROP_COPYRIGHT_CHOICE,);
		// Glenn said to not set the two properties below
		// resProperties.addProperty(ResourceProperties.PROP_COPYRIGHT_ALERT,Boolean.TRUE.toString());
		// resProperties.addProperty(ResourceProperties.PROP_IS_COLLECTION,Boolean.FALSE.toString());
		resProperties.addProperty(ResourceProperties.PROP_DISPLAY_NAME, secResourceName);
		resProperties.addProperty(ResourceProperties.PROP_DESCRIPTION, secResourceDescription);
		resProperties.addProperty(ContentHostingService.PROP_ALTERNATE_REFERENCE, REFERENCE_ROOT);

		// Glenn said the property below may not need to be set either, will leave it in
		// unless it creates problems
		if (encodingFlag) resProperties.addProperty(ResourceProperties.PROP_CONTENT_ENCODING, "UTF-8");
		return resProperties;

	}

	/**
	 * {@inheritDoc}
	 */
	public ResourcePropertiesEdit fillEmbeddedImagesResourceProperties(String name)
	{
		try
		{
			name = URLDecoder.decode(name, "UTF-8");
		}
		catch (Exception e)
		{
			logger.debug("error with decode in fillEmbeddedImagesResourceProperties");
		}
		ResourcePropertiesEdit resProperties = getContentservice().newResourceProperties();

		// Glenn said to not set the two properties below
		// resProperties.addProperty(ResourceProperties.PROP_COPYRIGHT_ALERT,Boolean.TRUE.toString());
		// resProperties.addProperty(ResourceProperties.PROP_IS_COLLECTION,Boolean.FALSE.toString());
		resProperties.addProperty(ResourceProperties.PROP_DISPLAY_NAME, name);
		resProperties.addProperty(ResourceProperties.PROP_DESCRIPTION, "embedded image");
		resProperties.addProperty(ContentHostingService.PROP_ALTERNATE_REFERENCE, REFERENCE_ROOT);
		return resProperties;

	}

	/**
	 * {@inheritDoc}
	 */
	public Boolean editResourceProperties(String selResourceIdFromList, String secResourceName, String secResourceDescription, byte[] data)
	{
		Boolean modify = false;
		if (selResourceIdFromList == null || selResourceIdFromList.length() == 0) return modify;
		ContentResourceEdit edit = null;
		try
		{
			if (!isUserAuthor(getCourseId(selResourceIdFromList)))
			{
				logger.info("User is not authorized to access meleteDocs collection");
				return modify;
			}
			// setup a security advisor
			meleteSecurityService.pushAdvisor();
			try
			{
				selResourceIdFromList = beforeDecode(selResourceIdFromList);
				selResourceIdFromList = URLDecoder.decode(selResourceIdFromList, "UTF-8");
			}
			catch (Exception decodex)
			{
			}
			edit = getContentservice().editResource(selResourceIdFromList);
			byte[] editData = edit.getContent();
			ResourcePropertiesEdit rp = edit.getPropertiesEdit();
			if (rp.getProperty(ResourceProperties.PROP_DISPLAY_NAME) != null)
				modify = (rp.getProperty(ResourceProperties.PROP_DISPLAY_NAME).compareTo(secResourceName) == 0) ? false : true;
			if (rp.getProperty(ResourceProperties.PROP_DESCRIPTION) != null)
				modify = modify ||( (rp.getProperty(ResourceProperties.PROP_DESCRIPTION).compareTo(secResourceDescription) == 0) ? false : true);
			if (editData != null) modify = modify || ((Arrays.equals(editData, data)) ? false : true);
			
			rp.clear();
			rp.addProperty(ResourceProperties.PROP_DISPLAY_NAME, secResourceName);
			rp.addProperty(ResourceProperties.PROP_DESCRIPTION, secResourceDescription);
			rp.addProperty(ContentHostingService.PROP_ALTERNATE_REFERENCE, REFERENCE_ROOT);
			if (data != null) edit.setContent(data);
			getContentservice().commitResource(edit);
			edit = null;
		}
		catch (Exception e)
		{
			logger.error("edit res properties:" + e.toString());
			e.printStackTrace();
		}
		finally
		{
			if (edit != null) getContentservice().cancelResource(edit);
			// clear the security advisor
			meleteSecurityService.popAdvisor();
		}
		return modify;
	}

	/**
	 * {@inheritDoc}
	 */
	public String addResourceItem(String name, String res_mime_type, String addCollId, byte[] secContentData, ResourcePropertiesEdit res)
			throws Exception
	{
		ContentResource resource = null;
		// Variable displayName is to preserve the user provided name
		String displayName = name;
		try
		{
			displayName = URLDecoder.decode(displayName, "UTF-8");
		}
		catch (Exception e)
		{
			// if decode fails then use name as is
			// comment line below otherwise on uploading bad file name again through Manage display name changes to cat_-1.gif instead of cat%-1.gif
			// and is moved out for name variable for creating resource url
			// name = Validator.escapeResourceName(name);
		}
		String courseId = getCourseId(addCollId);

		// need to add notify logic here and set the arg6 accordingly.
		try
		{
			if (!isUserAuthor(courseId))
			{
				logger.info("User is not authorized to add resource");
				return null;
			}
			// setup a security advisor
			meleteSecurityService.pushAdvisor();
			try
			{
				if (res_mime_type == MIME_TYPE_LINK)
				{
					String checkURL = new String(secContentData);
					if (checkURL.indexOf("/access/") != -1) checkURL = checkURL.substring(checkURL.indexOf("/access/"));
					secContentData = checkURL.getBytes();
				}
				// name is escaped to create resource url
				name = Validator.escapeResourceName(displayName);
				String finalName = addCollId + name;
				if (finalName.length() > ContentHostingService.MAXIMUM_RESOURCE_ID_LENGTH)
				{
					// leaving room for CHS inserted duplicate filenames -1 -2 etc
					int extraChars = finalName.length() - ContentHostingService.MAXIMUM_RESOURCE_ID_LENGTH + 3;
					name = name.substring(0, name.length() - extraChars);
				}
				resource = getContentservice().addResource(name, addCollId, MAXIMUM_ATTEMPTS_FOR_UNIQUENESS, res_mime_type, secContentData, res, 0);
				if (resource != null && (res_mime_type == MIME_TYPE_LINK))
				{
					try
					{
						ContentResourceEdit cre = getContentservice().editResource(resource.getId());
						cre.setResourceType(ResourceType.TYPE_URL);
						getContentservice().commitResource(cre);
					}
					catch (Exception e1)
					{
						logger.debug("melete tried to set Resource Type of web link and failed", e1);
					}
				}

				if (logger.isDebugEnabled())
					logger.debug("IN addResourceItem " + res.getProperty(ResourceProperties.PROP_DISPLAY_NAME) + " resourceId " + resource.getId());

				// check if its duplicate file and edit the resource name if it is
				String checkDup = resource.getUrl().substring(resource.getUrl().lastIndexOf("/") + 1);
				String numberStr = null;
				if (addCollId.endsWith("uploads/") && (!checkDup.equals(Validator.escapeResourceName(name))))
				{
					int lastDashIndex = checkDup.lastIndexOf("-");
					int lastDotIndex = checkDup.lastIndexOf(".");

					// for titles with no file extension
					if (lastDotIndex == -1) lastDotIndex = checkDup.length();
					if ((lastDashIndex != -1) && (lastDotIndex != -1))
					{
						numberStr = checkDup.substring(lastDashIndex, lastDotIndex);
						checkDup = checkDup.substring(0, lastDotIndex);
					}
				}
				ContentResourceEdit edit = null;
				try
				{
					if (numberStr != null && !checkDup.equals(Validator.escapeResourceName(name)))
					{
						edit = getContentservice().editResource(resource.getId());
						ResourcePropertiesEdit rp = edit.getPropertiesEdit();
						String desc = rp.getProperty(ResourceProperties.PROP_DESCRIPTION);
						rp.clear();
						displayName = createDuplicateName(displayName, numberStr);
						rp.addProperty(ResourceProperties.PROP_DISPLAY_NAME, displayName);
						rp.addProperty(ResourceProperties.PROP_DESCRIPTION, desc);
						rp.addProperty(ContentHostingService.PROP_ALTERNATE_REFERENCE, REFERENCE_ROOT);
						getContentservice().commitResource(edit);
						edit = null;
					}
				}
				catch (Exception ex)
				{
					throw ex;
				}
				finally
				{
					if (edit != null) getContentservice().cancelResource(edit);
				}
			}
			catch (PermissionException e)
			{
				logger.debug("permission is denied");
			}
			catch (IdInvalidException e)
			{
				logger.debug("title" + " " + e.getMessage());
				throw new MeleteException("failed");
			}
			catch (IdLengthException e)
			{
				logger.debug("The name is too long" + " " + e.getMessage());
				throw new MeleteException("failed");
			}
			catch (IdUniquenessException e)
			{
				logger.debug("Could not add this resource item to melete collection");
				throw new MeleteException("failed");
			}
			catch (InconsistentException e)
			{
				logger.debug("Invalid characters in collection title");

				throw new MeleteException("failed");
			}
			catch (OverQuotaException e)
			{
				logger.debug("Adding this resource would place this account over quota.To add this resource, some resources may need to be deleted.");
				throw new MeleteException("failed");
			}
			catch (ServerOverloadException e)
			{
				logger.debug("failed - internal error");
				throw new MeleteException("failed");
			}
			catch (RuntimeException e)
			{
				logger.debug("SectionPage.addResourcetoMeleteCollection ***** Unknown Exception ***** " + e.getMessage());
				e.printStackTrace();
				throw new MeleteException("failed");
			}
		}
		catch (Exception e)
		{
			logger.error(e.toString());
		}
		finally
		{
			meleteSecurityService.popAdvisor();
		}
		return resource.getId();
	}

	/**
	 * create a new display name with -1, -2 etc for duplicate resources
	 * 
	 * @param name
	 *        file name
	 * @param numberStr
	 *        the number
	 * @return
	 */
	private String createDuplicateName(String name, String numberStr)
	{
		String dupName = null;
		int index = name.lastIndexOf(".");

		// for files with no extension
		if (index == -1) index = name.length();
		String base = null;
		String ext = null;
		if (index > 0)
		{
			base = name.substring(0, index);
			ext = name.substring(index);
			if (ext == null) ext = "";
		}
		dupName = base + numberStr + ext;
		return dupName;
	}

	/**
	 * {@inheritDoc}
	 */
	public String getLinkContent(String resourceId)
	{
		ContentResourceEdit cr = null;
		try
		{
			// setup a security advisor
			meleteSecurityService.pushAdvisor();
			cr = getContentservice().editResource(resourceId);
			String linkData = new String(cr.getContent());
			if (cr.getContentType().equals(MIME_TYPE_LINK) && cr.getContent() != null)
			{
				String check = new String(cr.getContent());
				if ((!check.startsWith("/access/")) && check.indexOf("/access/") != -1)
				{
					check = check.substring(check.indexOf("/access/"));
					byte[] data = check.getBytes();
					cr.setContent(data);
					// cr.setContentLength((long)data.length);
					linkData = check;
					getContentservice().commitResource(cr);
					cr = null;
				}
			}
			if (cr != null) getContentservice().cancelResource(cr);
			return linkData;
		}
		catch (Exception e)
		{
			logger.debug("error in reading link content in edit section" + e);
		}
		finally
		{
			meleteSecurityService.popAdvisor();
		}
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	public String getDisplayName(String resourceId)
	{
		try
		{
			// setup a security advisor
			meleteSecurityService.pushAdvisor();
			ContentResource cr = getContentservice().getResource(resourceId);
			logger.debug("prop name" + cr.getProperties().getProperty(ResourceProperties.PROP_DISPLAY_NAME));
			String dispName = cr.getProperties().getProperty(ResourceProperties.PROP_DISPLAY_NAME);

			cr = null;
			return dispName;
		}
		catch (Exception e)
		{
			logger.debug("error in reading resource properties in edit section" + e);
			e.printStackTrace();
		}
		finally
		{
			meleteSecurityService.popAdvisor();
		}
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	public List<ContentResource> getAllResources(String uploadCollId)
	{
		try
		{
			if (!isUserAuthor(getCourseId(uploadCollId)))
			{
				logger.info("User is not authorized to access meleteDocs collection");
				return null;
			}
			// setup a security advisor
			meleteSecurityService.pushAdvisor();
			List<ContentResource> all = null;
			all = getContentservice().getAllResources(uploadCollId);
			return all;

		}
		catch (Exception e)
		{
			logger.error(e.toString());
		}
		finally
		{
			// clear the security advisor
			meleteSecurityService.popAdvisor();
		}
		return null;
	}

	/**
	 * Replaces few special characters for browser to convert
	 * 
	 * @param id
	 * @return
	 */
	private String beforeDecode(String id)
	{
		// these the browser will convert when it's making the URL to send
		String processed = id.replaceAll("&amp;", "&");
		processed = processed.replaceAll("&lt;", "<");
		processed = processed.replaceAll("&gt;", ">");
		processed = processed.replaceAll("&quot;", "\"");

		// if a browser sees a plus, it sends a plus (URLDecoder will change it to a space)
		processed = processed.replaceAll("\\+", "%2b");
		return processed;
	}

	/**
	 * {@inheritDoc}
	 */
	public ContentResource getResource(String courseId, String resourceId) throws Exception
	{
		if (resourceId == null || resourceId.length() == 0) return null;

		try
		{
			if (!isUserAuthor(courseId) && !isUserStudent(courseId))
			{
				logger.info("User is not authorized to access meleteDocs collection");
				return null;
			}
			String originalResourceId = resourceId;
			// setup a security advisor
			meleteSecurityService.pushAdvisor();
			// absolute reqd otherwise import from site creates desert Landscape.jpg and desert%20Landscape-1.jpg
			try
			{
				resourceId = beforeDecode(resourceId);
				resourceId = URLDecoder.decode(resourceId, "UTF-8");
			}
			catch (Exception decodeEx)
			{
				logger.debug("get resource fails while decoding " + resourceId);
			}

			try
			{
				return (getContentservice().getResource(resourceId));
			}
			catch (Exception getResourceEx)
			{
				return (getContentservice().getResource(originalResourceId));
			}
		}
		catch (Exception e)
		{
			logger.error("getResource error:" + e.toString());
			throw e;
		}
		finally
		{
			// clear the security advisor
			meleteSecurityService.popAdvisor();
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public ContentResource getResource(String resourceId) throws Exception
	{
		if (resourceId == null || resourceId.length() == 0) return null;
		String courseId = getCourseId(resourceId);
		try
		{
			if (!isUserAuthor(courseId) && !isUserStudent(courseId))
			{
				logger.info("User is not authorized to access meleteDocs collection");
				return null;
			}
			String originalResourceId = resourceId;
			// setup a security advisor
			meleteSecurityService.pushAdvisor();
			// absolute reqd otherwise import from site creates desert Landscape.jpg and desert%20Landscape-1.jpg
			try
			{
				resourceId = beforeDecode(resourceId);
				resourceId = URLDecoder.decode(resourceId, "UTF-8");
			}
			catch (Exception decodeEx)
			{
				logger.debug("get resource fails while decoding " + resourceId);
			}

			try
			{
				return (getContentservice().getResource(resourceId));
			}
			catch (Exception getResourceEx)
			{
				return (getContentservice().getResource(originalResourceId));
			}
		}
		catch (Exception e)
		{
			logger.error("getResource error:" + e.toString());
			throw e;
		}
		finally
		{
			// clear the security advisor
			meleteSecurityService.popAdvisor();
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public void checkResource(String resourceId) throws Exception
	{
		if (resourceId == null || resourceId.length() == 0) return;
		String courseId = getCourseId(resourceId);
		try
		{
			if (!isUserAuthor(courseId))
			{
				logger.info("User is not authorized to access meleteDocs collection");
				return;
			}
			// setup a security advisor
			meleteSecurityService.pushAdvisor();
			try
			{
				// edit save for + sign files on upload type
				resourceId = beforeDecode(resourceId);
				resourceId = URLDecoder.decode(resourceId, "UTF-8");
			}
			catch (Exception decodeEx)
			{
				logger.debug("get resource fails while decoding " + resourceId);
			}
			getContentservice().checkResource(resourceId);
			// for manage items which are just in CH and NOT in Melete Resource then insert them
			sectiondb.getMeleteResource(resourceId);
			return;
		}
		catch (IdUnusedException ex)
		{
			logger.debug("resource is not available so create one" + resourceId);
			throw ex;
		}
		catch (Exception e)
		{
			logger.debug("checkResource error " + e.toString());
		}
		finally
		{
			// clear the security advisor
			meleteSecurityService.popAdvisor();
		}
		return;
	}

	/**
	 * {@inheritDoc}
	 */
	public void editResource(String resourceId, String contentEditor) throws Exception
	{
		ContentResourceEdit edit = null;
		Boolean modify = false;
		try
		{
			if (!isUserAuthor(getCourseId(resourceId)))
			{
				logger.info("User is not authorized to access meleteDocs collection");
				return;
			}
			// setup a security advisor
			meleteSecurityService.pushAdvisor();
			if (resourceId != null)
			{
				try
				{
					resourceId = beforeDecode(resourceId);
					resourceId = URLDecoder.decode(resourceId, "UTF-8");
				}
				catch (Exception decodex)
				{
				}
				edit = getContentservice().editResource(resourceId);
				byte[] data = contentEditor.getBytes();

				edit.setContent(data);
				// edit.setContentLength((long)data.length);
				getContentservice().commitResource(edit);
				edit = null;
			}
			return;
		}
		catch (Exception e)
		{
			logger.error("error saving editor content " + e.toString());
			throw e;
		}
		finally
		{
			if (edit != null) getContentservice().cancelResource(edit);
			// clear the security advisor
			meleteSecurityService.popAdvisor();
		}
	}

	/**
	 * {@inheritDoc}
	 */

	public void editResource(String courseId, String resourceId, String contentEditor) throws Exception
	{
		ContentResourceEdit edit = null;

		try
		{
			if (!isUserAuthor(courseId))
			{
				logger.info("User is not authorized to access meleteDocs collection");
				return;
			}
			// setup a security advisor
			meleteSecurityService.pushAdvisor();
			if (resourceId != null)
			{
				try
				{
					resourceId = beforeDecode(resourceId);
					resourceId = URLDecoder.decode(resourceId, "UTF-8");
				}
				catch (Exception decodex)
				{
				}
				edit = getContentservice().editResource(resourceId);
				byte[] data = contentEditor.getBytes();

				// save user provided data
				edit.setContent(data);
				// edit.setContentLength((long)data.length);
				getContentservice().commitResource(edit);
				edit = null;
			}
			return;
		}
		catch (Exception e)
		{
			logger.error("error saving editor content " + e.toString());
			throw e;
		}
		finally
		{
			if (edit != null) getContentservice().cancelResource(edit);
			// clear the security advisor
			meleteSecurityService.popAdvisor();
		}
	}

	/**
	 * 
	 * @param content1
	 * @return
	 */
	private String checkPlainText(String content1) 
	{	
		try
		{
			if (content1 == null || content1.length() == 0) return content1;
			Pattern p1 = Pattern.compile("<.*?>", Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE | Pattern.DOTALL);
			
			Matcher m = p1.matcher(content1);
			StringBuffer sb = new StringBuffer();
			while (m.find())
			{				
				m.appendReplacement(sb, "");
			}
			m.appendTail(sb);
			return sb.toString();
		}
		catch (Exception e)
		{
			logger.debug("error in checking text");
			e.printStackTrace();
		}
		return content1;
	}
	
	
	/**
	 * Parses the data as text and a list of embedded media.
	 * @param content1
	 * The html content
	 * @return
	 * a vector with first element as list of embedded media.
	 */
	private Vector<String> checkEmbedModified(String content1) 
	{
		Vector<String> v = new Vector<String>(0);
		try
		{
			if (content1 == null || content1.length() == 0) return v;
			Pattern p1 = Pattern.compile("<(img|a|embed|script|applet|object)\\s+.*?/*>", Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE
					| Pattern.DOTALL);
			Pattern p2 = Pattern.compile("(src|href|data|archive)[\\s]*=[\\s]*\"([^#\"]*)([#\"])", Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE);

			Matcher m = p1.matcher(content1);
			StringBuffer sb = new StringBuffer();
			List<String> embed = new ArrayList<String>(0);
			while (m.find())
			{
				Matcher m2 = p2.matcher(m.group(0));
				if (m2.find() && m2.groupCount() == 3) embed.add(m2.group(2));
				m.appendReplacement(sb, "");
			}
			m.appendTail(sb);

	//		v.add(sb.toString());
			v.add(embed.toString());

			return v;
		}
		catch (Exception e)
		{
			logger.debug("error in checking text");
			e.printStackTrace();
		}
		return v;
	}
	/**
	 * {@inheritDoc}
	 */
	public void addToMeleteResource(String sectionId, String resourceId) throws Exception
	{
		// add in melete resource database table
		MeleteResource meleteResource = new MeleteResource();
		meleteResource.setResourceId(resourceId);
		// set default license info to "I have not determined copyright yet" option
		meleteResource.setLicenseCode(0);
		sectiondb.insertResource(meleteResource);
		if (sectionId != null)
		{
			sectiondb.insertSectionResource(sectionId, resourceId);
		}

	}

	/**
	 * {@inheritDoc}
	 */
	public String getSectionResource(String sectionId) throws Exception
	{
		SectionResource sr = sectiondb.getSectionResourcebyId(sectionId);
		if (sr != null && sr.getResource() != null)
			return sr.getResource().getResourceId();
		else
			return null;
	}

	/**
	 * {@inheritDoc}
	 */
	public String findLocalImagesEmbeddedInEditor(String courseId, ArrayList<String> errs, Map<String, String> newEmbeddedResources,
			String contentEditor) throws MeleteException
	{
		String checkforimgs = contentEditor;
		// get collection id where the embedded files will go
		String UploadCollId = getUploadCollectionId(courseId);
		String fileName;
		int startSrc = 0;
		int endSrc = 0;
		String foundLink = null;
		// look for local embedded images
		try
		{
			if (!isUserAuthor(courseId))
			{
				logger.info("User is not authorized to access meleteDocs");
				return null;
			}
			// remove MSword comments
			/*int wordcommentIdx = -1;
			while (checkforimgs != null && (wordcommentIdx = checkforimgs.indexOf("<!--[if gte vml 1]>")) != -1)
			{
				String pre = checkforimgs.substring(0, wordcommentIdx);
				checkforimgs = checkforimgs.substring(wordcommentIdx + 19);
				int endcommentIdx = checkforimgs.indexOf("<![endif]-->");
				checkforimgs = checkforimgs.substring(endcommentIdx + 12);
				checkforimgs = pre + checkforimgs;
				wordcommentIdx = -1;
			}
			// for FCK editor inserted comments
			wordcommentIdx = -1;
			while (checkforimgs != null && (wordcommentIdx = checkforimgs.indexOf("<!--[if !vml]-->")) != -1)
			{
				String pre = checkforimgs.substring(0, wordcommentIdx);
				checkforimgs = checkforimgs.substring(wordcommentIdx);
				int endcommentIdx = checkforimgs.indexOf("<!--[endif]-->");
				checkforimgs = checkforimgs.substring(endcommentIdx + 14);
				checkforimgs = pre + checkforimgs;
				wordcommentIdx = -1;
			}*/
			// strip MS comments and bogus links
			// strip bad link and meta tags
			// and does more cleaning
			checkforimgs = HtmlHelper.clean(checkforimgs, false);

			contentEditor = checkforimgs;
			// remove word comments code end

			// check for form tag and remove it
			// checkforimgs = meleteUtil.findFormPattern(checkforimgs);
			// logger.debug("after find form pattern "+ endSrc);
			// contentEditor = checkforimgs;

			while (checkforimgs != null)
			{
				// look for a href and img tag
				ArrayList<?> embedData = meleteUtil.findEmbedItemPattern(checkforimgs);

				checkforimgs = (String) embedData.get(0);
				if (embedData.size() > 1)
				{
					startSrc = ((Integer) embedData.get(1)).intValue();
					endSrc = ((Integer) embedData.get(2)).intValue();
					logger.debug("reading embeddata " + endSrc);
					foundLink = (String) embedData.get(3);
				}
				if (endSrc <= 0) break;
				// find filename
				fileName = checkforimgs.substring(startSrc, endSrc);
				String patternStr = fileName;

				// word paste
				fileName = meleteUtil.replace(fileName, "\\", "/");
				logger.debug("processing embed src" + fileName + endSrc);
				String key = null;
				if (fileName.lastIndexOf("/") != -1)
				{
					key = fileName.substring(fileName.lastIndexOf("/") + 1);

				}
				logger.debug("key is:" + key);
				// process for local uploaded files for sferyx

				// bad character files are not uploaded so send error message
				if (fileName != null && fileName.trim().length() > 0 && !fileName.equals(File.separator) && fileName.startsWith("file:"))
				{
					// if filename contains pound char then throw error
					if (fileName.indexOf("#") != -1)
					{
						errs.add("embed_img_bad_filename");
					}

					// if filename contains percentage sign then throw error
					if (fileName.indexOf("%") != -1)
					{
						try
						{
							java.net.URLDecoder.decode(fileName, "UTF-8");
						}
						catch (Exception decodex)
						{
							errs.add("embed_img_bad_filename1");
						}
					}
				}
				if (fileName != null && fileName.trim().length() > 0 && (!(fileName.equals(File.separator))) && key != null
						&& newEmbeddedResources != null && newEmbeddedResources.containsKey(key))
				{

					String newEmbedResourceId = (String) newEmbeddedResources.get(key);

					// in content editor replace the file found with resource reference url
					String replaceStr = getResourceUrl(newEmbedResourceId);
					replaceStr = meleteUtil.replace(replaceStr, ServerConfigurationService.getServerUrl(), "");
					logger.debug("repl;acestr" + patternStr + " in embedimage processing is " + replaceStr);

					// Rashmi's change to fix infinite loop on uploading images
					contentEditor = meleteUtil.replaceaPath(contentEditor, patternStr, replaceStr);
					checkforimgs = meleteUtil.replaceaPath(checkforimgs, patternStr, replaceStr);

				}
				// for internal links to make it relative
				else
				{
					if (fileName.startsWith(ServerConfigurationService.getServerUrl()))
					{
						if (fileName.indexOf("/meleteDocs") != -1)
						{
							String findEntity = fileName.substring(fileName.indexOf("/access") + 7);
							Reference ref = EntityManager.newReference(findEntity);
							logger.debug("ref properties" + ref.getType() + "," + ref.getId());
							String newEmbedResourceId = ref.getId();
							newEmbedResourceId = newEmbedResourceId.replaceFirst("/content", "");
							sectiondb.getMeleteResource(newEmbedResourceId);
						}
						String replaceStr = meleteUtil.replace(fileName, ServerConfigurationService.getServerUrl(), "");
						contentEditor = meleteUtil.replacePath(contentEditor, patternStr, replaceStr);
						checkforimgs = meleteUtil.replacePath(checkforimgs, patternStr, replaceStr);
					}
					// process links and append http:// protocol if not provided
					/*else if (!(fileName.startsWith("/") || fileName.startsWith("./") || fileName.startsWith("../") || fileName.startsWith("#"))
							&& foundLink != null && foundLink.equals("link")
							&& !(fileName.startsWith("http://") || fileName.startsWith("https://") || fileName.startsWith("mailto:")))
					{
						logger.debug("processing embed link src for appending protocol");
						String replaceLinkStr = "http://" + fileName;
						contentEditor = meleteUtil.replacePath(contentEditor, fileName, replaceLinkStr);
						checkforimgs = meleteUtil.replacePath(checkforimgs, fileName, replaceLinkStr);
					}
					// FCK editor word paste puts bad ../../..'s in the src so remove them
					else if (fileName.startsWith("../") && fileName.indexOf("/access/") != -1)
					{
						logger.debug("paste in FCK puts extra ../..");
						String replaceStr = fileName.substring(fileName.indexOf("/access/"));
						contentEditor = meleteUtil.replacePath(contentEditor, fileName, replaceStr);
						checkforimgs = meleteUtil.replacePath(checkforimgs, fileName, replaceStr);
					}*/
					// convert relative paths to full urls
					else if (fileName.indexOf("://") == -1 && fileName.indexOf("/") == -1)
					{
						String replaceStr = getResourceUrl(UploadCollId + fileName);
						replaceStr = meleteUtil.replace(replaceStr, ServerConfigurationService.getServerUrl(), "");
						contentEditor = meleteUtil.replacePath(contentEditor, fileName, replaceStr);
						checkforimgs = meleteUtil.replacePath(checkforimgs, fileName, replaceStr);
					}
				}
				// iterate next
				if (endSrc > 0 && endSrc <= checkforimgs.length())
					checkforimgs = checkforimgs.substring(endSrc);
				else
					checkforimgs = null;
				startSrc = 0;
				endSrc = 0;
				foundLink = null;
			}
		}
		catch (MeleteException me)
		{
			throw me;
		}
		catch (Exception e)
		{
			if (logger.isDebugEnabled())
			{
				logger.debug(e.toString());
				e.printStackTrace();
			}
		}	
		// add target attribute if missing
		contentEditor = addTargetAttributeIfMissing(contentEditor);
		return contentEditor;
	}

	/**
	 * 
	 * @param s1
	 * @return
	 */
	private String addTargetAttributeIfMissing(String s1)
	{
		Pattern pa = Pattern.compile("<a\\s+.*?/*>", Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE | Pattern.DOTALL);
		Pattern p_target = Pattern.compile("\\s[tT][aA][rR][gG][eE][tT]\\s*=");
		Pattern p_targetBlank = Pattern.compile("\\s[tT][aA][rR][gG][eE][tT]\\s*=\\s*[\"]*_blank\\s*[\"]*");
		
		Matcher m = pa.matcher(s1);
		StringBuffer sb = new StringBuffer();
		while (m.find())
		{
			String a_content = m.group(0);
			Matcher m_target = p_target.matcher(m.group(0));

			if (m_target.find())
			{
				// check if target is _blank with or without and some FCK composed data has 2 targets so replace all with blank 
				Matcher m_blank = p_targetBlank.matcher(a_content);
				StringBuffer sb_blank = new StringBuffer();
				while (m_blank.find())
				{
					m_blank.appendReplacement(sb_blank, "");
				}
				m_blank.appendTail(sb_blank);
				a_content = sb_blank.toString();
			}
			// add it
			a_content = a_content.replace("<a ", "<a target=\"_blank\" ");

			m.appendReplacement(sb,Matcher.quoteReplacement(a_content));
		}
		m.appendTail(sb);
		return sb.toString();
	}
	
	
	public List<String> findAllEmbeddedImages(String sec_resId) throws Exception
	{
		try
		{
			ContentResource cr = getResource(sec_resId);
			String checkforImgs = new String(cr.getContent());
			List<String> secEmbedData = new ArrayList<String>(0);
			int startSrc = 0, endSrc = 0;
			if (checkforImgs == null || checkforImgs.length() == 0) return null;
			while (checkforImgs != null)
			{
				// look for a href and img tag
				ArrayList<?> embedData = meleteUtil.findEmbedItemPattern(checkforImgs);
				checkforImgs = (String) embedData.get(0);
				if (embedData.size() > 1)
				{
					startSrc = ((Integer) embedData.get(1)).intValue();
					endSrc = ((Integer) embedData.get(2)).intValue();
				}
				if (endSrc <= 0) break;
				// find filename and add just the ones which belongs to meleteDocs
				String fileName = checkforImgs.substring(startSrc, endSrc);
				if (fileName.startsWith("/access/meleteDocs/content"))
				{
					fileName = fileName.replace("/access/meleteDocs/content", "");
					try
					{
						fileName = URLDecoder.decode(fileName, "UTF-8");
					}
					catch (Exception decodex)
					{
					}
					secEmbedData.add(fileName);
				}
				// iterate next
				checkforImgs = checkforImgs.substring(endSrc);
				startSrc = 0;
				endSrc = 0;
			}
			return secEmbedData;
		}
		catch (Exception e)
		{
			logger.debug("can't read section file" + sec_resId);
		}
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	public String getResourceUrl(String newResourceId)
	{
		try
		{
			meleteSecurityService.pushAdvisor();
			// first try with original id for resources like b%20day.jpg
			// and for other resources with &amp; or + sign decode it
			try
			{
				return (getContentservice().getUrl(newResourceId));
			}
			catch (Exception getResourceEx)
			{
				try
				{
					newResourceId = beforeDecode(newResourceId);
					newResourceId = URLDecoder.decode(newResourceId, "UTF-8");
				}
				catch (Exception decodeEx)
				{
					logger.debug("get resource fails while decoding " + newResourceId);
				}
				return (getContentservice().getUrl(newResourceId));
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return "";
		}
		finally
		{
			meleteSecurityService.popAdvisor();
		}

	}

	/**
	 * {@inheritDoc}
	 */
	public String copyIntoFolder(String fromColl, String toColl)
	{
		try
		{
			if (!isUserAuthor(getCourseId(toColl)))
			{
				logger.info("User is not authorized to perform the copyIntoFolder function");
				return "";
			}
			// setup a security advisor
			meleteSecurityService.pushAdvisor();
			try
			{
				return getContentservice().copyIntoFolder(fromColl, toColl);
			}
			catch (InconsistentException e)
			{
				logger.debug("Inconsistent exception thrown");
			}
			catch (IdLengthException e)
			{
				logger.debug("IdLength exception thrown");
			}
			catch (IdUniquenessException e)
			{
				logger.debug("IdUniqueness exception thrown");
			}
			catch (PermissionException e)
			{
				logger.debug("Permission to copy uploads collection is denied");
			}
			catch (IdUnusedException e)
			{
				logger.debug("Failed to create uploads collection in second site");
			}
			catch (TypeException e)
			{
				logger.debug("TypeException thrown: " + e.getMessage());
			}
			catch (InUseException e)
			{
				logger.debug("InUseException thrown: " + e.getMessage());
			}
			catch (IdUsedException e)
			{
				logger.debug("IdUsedException thrown");
			}
			catch (OverQuotaException e)
			{
				logger.debug("Copying this collection would place this account over quota.");
			}
			catch (ServerOverloadException e)
			{
				logger.debug("Server overload exception");
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			meleteSecurityService.popAdvisor();
		}
		return "";
	}

	/**
	 * {@inheritDoc}
	 */
	public ContentCollection getCollection(String toColl)
	{
		ContentCollection toCollection = null;
		try
		{
			if (!isUserAuthor(getCourseId(toColl)))
			{
				logger.info("User is not authorized to perform the copyIntoFolder function");
				return null;
			}
			// setup a security advisor
			meleteSecurityService.pushAdvisor();
			try
			{
				toCollection = getContentservice().getCollection(toColl);
			}
			catch (IdUnusedException e1)
			{
				logger.debug("IdUnusedException thrown: " + e1.getMessage());
			}
			catch (TypeException e1)
			{
				logger.debug("TypeException thrown: " + e1.getMessage());
			}
			catch (PermissionException e1)
			{
				logger.debug("Permission to get uploads collection is denied");
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			meleteSecurityService.popAdvisor();
		}
		return toCollection;
	}

	/**
	 * {@inheritDoc}
	 */
	public void removeResource(String delRes_id) throws Exception
	{
		if (!isUserAuthor(getCourseId(delRes_id)))
		{
			logger.info("User is not authorized to perform del resource function");
			return;
		}
		// setup a security advisor
		meleteSecurityService.pushAdvisor();
		ContentResourceEdit edit = null;
		try
		{
			edit = getContentservice().editResource(delRes_id);
			if (edit != null) getContentservice().removeResource(edit);
			edit = null;
		}
		catch (IdUnusedException e1)
		{
			logger.debug("IdUnusedException thrown: " + e1.getMessage() + delRes_id);
		}
		catch (TypeException e1)
		{
			logger.debug("TypeException thrown: " + e1.getMessage());
		}
		catch (PermissionException e1)
		{
			logger.debug("Permission to get uploads collection is denied");
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new MeleteException("delete_resource_fail");
		}
		finally
		{
			if (edit != null) getContentservice().cancelResource(edit);
			meleteSecurityService.popAdvisor();
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public void removeCollection(String delColl_id, String delSubColl_id) throws Exception
	{
		if (!isUserAuthor(delColl_id))
		{
			logger.info("User is not authorized to perform del collection function");
			return;
		}
		// setup a security advisor
		meleteSecurityService.pushAdvisor();
		delColl_id = Entity.SEPARATOR + "private" + REFERENCE_ROOT + Entity.SEPARATOR + delColl_id + Entity.SEPARATOR;
		if (delSubColl_id != null) delColl_id = delColl_id.concat(delSubColl_id + Entity.SEPARATOR);
		// logger.debug("checking coll before delte" + delColl_id);

		try
		{
			getContentservice().checkCollection(delColl_id);
			getContentservice().removeCollection(delColl_id);
		}
		catch (IdUnusedException e1)
		{
			logger.debug("IdUnusedException thrown: " + e1.getMessage());
		}
		catch (TypeException e1)
		{
			logger.debug("TypeException thrown: " + e1.getMessage());
		}
		catch (PermissionException e1)
		{
			logger.debug("Permission to get uploads collection is denied");
		}
		catch (Exception e)
		{
			logger.error("error deleting melete collection:" + e.getMessage());
			throw new MeleteException("delete_resource_fail");
		}
		finally
		{
			meleteSecurityService.popAdvisor();
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public void removeCourseCollection(String delColl_id) throws Exception
	{
		if (!isUserAuthor(getCourseId(delColl_id)))
		{
			logger.info("User is not authorized to perform del resource function");
			return;
		}
		// setup a security advisor
		meleteSecurityService.pushAdvisor();
		try
		{
			delColl_id = Entity.SEPARATOR + "private" + REFERENCE_ROOT + Entity.SEPARATOR + delColl_id + Entity.SEPARATOR;
			// logger.debug("checking course coll before delete: " + delColl_id);
			getContentservice().checkCollection(delColl_id);

			// if uploads directory remains and all modules are deleted
			logger.debug("collection sz" + getContentservice().getCollectionSize(delColl_id));
			if (getContentservice().getCollectionSize(delColl_id) == 1)
			{
				List<?> allEnt = getContentservice().getAllEntities(delColl_id);

				for (Iterator<?> i = allEnt.iterator(); i.hasNext();)
				{
					ContentEntity ce = (ContentEntity) i.next();
					if (ce.isCollection() && (ce.getId().indexOf("module_") == -1)) getContentservice().removeCollection(delColl_id);
				}

			}
		}
		catch (IdUnusedException e1)
		{
			logger.debug("IdUnusedException thrown from remove Course Collection: " + e1.getMessage());
		}
		catch (TypeException e1)
		{
			logger.debug("TypeException thrown: " + e1.getMessage());
		}
		catch (PermissionException e1)
		{
			logger.debug("Permission to get uploads collection is denied");
		}
		catch (Exception e)
		{
			throw new MeleteException("delete_resource_fail");
		}
		finally
		{
			meleteSecurityService.popAdvisor();
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public String moveResource(String resourceId, String destinationColl) throws Exception
	{
		if (!isUserAuthor(getCourseId(destinationColl)))
		{
			logger.info("User is not authorized to perform del resource function");
			return null;
		}
		// setup a security advisor
		meleteSecurityService.pushAdvisor();
		try
		{
			getContentservice().checkResource(resourceId);
			String newResId = getContentservice().moveIntoFolder(resourceId, destinationColl);
			return newResId;
		}
		catch (IdUnusedException e1)
		{
			logger.debug("IdUnusedException thrown from moveResource: " + e1.getMessage());
		}
		catch (Exception e)
		{
			throw new MeleteException("move_resource_fail");
		}
		finally
		{
			meleteSecurityService.popAdvisor();
		}
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	public String getTypeEditorSectionName(Integer sectionId)
	{
		if (sectionId != null)
		{
			String sectionIdStr = sectionId.toString();
			if (sectionIdStr.trim().length() == 0)
				return null;
			else
			{
				return "Section_" + sectionIdStr + ".html";
			}
		}
		else
		{
			return null;
		}
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
	 * @return meleteSecurityService
	 */
	public MeleteSecurityService getMeleteSecurityService()
	{
		return meleteSecurityService;
	}

	/**
	 * @param meleteSecurityService
	 *        The meleteSecurityService to set.
	 */
	public void setMeleteSecurityService(MeleteSecurityService meleteSecurityService)
	{
		this.meleteSecurityService = meleteSecurityService;
	}

	/**
	 * @return Returns the sectiondb.
	 */
	public SectionDB getSectiondb()
	{
		return sectiondb;
	}

	/**
	 * @param sectiondb
	 *        The sectiondb to set.
	 */
	public void setSectiondb(SectionDB sectiondb)
	{
		this.sectiondb = sectiondb;
	}
}
