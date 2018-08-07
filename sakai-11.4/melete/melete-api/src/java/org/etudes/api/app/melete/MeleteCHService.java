/**********************************************************************************
 *
 * $URL: https://source.sakaiproject.org/contrib/etudes/melete/tags/2.9.1/melete-api/src/java/org/etudes/api/app/melete/MeleteCHService.java $
 * $Id: MeleteCHService.java 77532 2011-11-21 22:57:15Z rashmi@etudes.org $
 ***********************************************************************************
 *
 * Copyright (c) 2008,2009,2010,2011 Etudes, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
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

package org.etudes.api.app.melete;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.etudes.api.app.melete.exception.MeleteException;
import org.sakaiproject.content.api.ContentCollection;
import org.sakaiproject.content.api.ContentResource;
import org.sakaiproject.entity.api.ResourcePropertiesEdit;

/*
 *  This is a basic class to do all content hosting service work through melete
 */
public interface MeleteCHService
{
	public static final String MIME_TYPE_EDITOR = "text/html";
	public static final String MIME_TYPE_LINK = "text/url";
	public static final String MIME_TYPE_LTI = "ims/basiclti";

	/**
	 * Creates module and uploads collection for a site if it doesn't exist.
	 * 
	 * @param meleteItemColl
	 *        Collection Id
	 * @param CollName
	 *        Collection name
	 * @return
	 */
	public String addCollectionToMeleteCollection(String meleteItemColl, String CollName);

	/**
	 * Adds resource to content hosting.
	 * 
	 * @param name
	 *        Resource display name
	 * @param res_mime_type
	 *        Mime type
	 * @param addCollId
	 *        Content Collection Id
	 * @param secContentData
	 *        Resource data
	 * @param res
	 *        ResourceProperties
	 * @return new resource Id
	 * @throws Exception
	 */
	public String addResourceItem(String name, String res_mime_type, String addCollId, byte[] secContentData, ResourcePropertiesEdit res)
			throws Exception;

	/**
	 * Adds resource to melete resource in the database and the association to section if section_id is not null.
	 * 
	 * @param sectionId
	 *        The section id
	 * @param resourceId
	 *        The resource id
	 * @throws Exception
	 */
	public void addToMeleteResource(String sectionId, String resourceId) throws Exception;

	/**
	 * Check if resource exists.
	 * 
	 * @param resourceId
	 *        The resource Id
	 * @throws Exception
	 */
	public void checkResource(String resourceId) throws Exception;

	/**
	 * Copies all members of one collection to other.
	 * 
	 * @param fromColl
	 *        From Collection Id
	 * @param toColl
	 *        To Collection Id
	 * @return
	 */
	public String copyIntoFolder(String fromColl, String toColl);

	/**
	 * Edit an HTML resource.
	 * 
	 * @param resourceId
	 *        The resource Id
	 * @param contentEditor
	 *        HTML data
	 * 
	 * @throws Exception
	 */
	public void editResource(String resourceId, String contentEditor) throws Exception;

	/**
	 * Edit a composed section HTML resource if you have author access to a site.
	 * 
	 * @param courseId
	 *        The Site Id
	 * @param resourceId
	 *        The resource Id
	 * @param contentEditor
	 *        composed section data
	 *         
	 * @throws Exception
	 */
	public void editResource(String courseId, String resourceId, String contentEditor) throws Exception;

	/**
	 * Update the resource properties of a resource.
	 * 
	 * @param selResourceIdFromList
	 *        The resource Id
	 * @param secResourceName
	 *        Resource display name
	 * @param secResourceDescription
	 *        resource description
	 * @return boolean. null if resource is bad, true if contents have changed.       
	 */
	public Boolean editResourceProperties(String selResourceIdFromList, String secResourceName, String secResourceDescription);

	/**
	 * Get ResourceProperties for a resource
	 * 
	 * @param name
	 *        Resource display name
	 * @return
	 */
	public ResourcePropertiesEdit fillEmbeddedImagesResourceProperties(String name);

	/**
	 * Get ResourceProperties for a section html file.
	 * 
	 * @param encodingFlag
	 *        The EncodingFlag
	 * @param secResourceName
	 *        Resource display name
	 * @param secResourceDescription
	 *        resource description
	 * @return
	 */
	public ResourcePropertiesEdit fillInSectionResourceProperties(boolean encodingFlag, String secResourceName, String secResourceDescription);

	/**
	 * Parses the HTML content and gets all embedded media resource Ids
	 * 
	 * @param sec_resid
	 *        The composed section_xxx.html resource id
	 * @return a list of resource ids
	 * @throws Exception
	 */
	public List<String> findAllEmbeddedImages(String sec_resid) throws Exception;

	/**
	 * Parses all embedded media from the composed data and add them to meletedocs collection.
	 * 
	 * @param courseId
	 *        The site id
	 * @param errs
	 *        List of errors encountered while adding resources
	 * @param newEmbeddedResources
	 *        Map keyed with local file names and value is embed resource Id
	 * @param contentEditor
	 *        Composed HTML content
	 * @return the revised HTML data
	 * @throws MeleteException
	 */
	public String findLocalImagesEmbeddedInEditor(String courseId, ArrayList<String> errs, Map<String, String> newEmbeddedResources, String contentEditor)
			throws MeleteException;

	/**
	 * Not in use anymore
	 * 
	 * @param uploadCollId
	 * @return
	 */
	public List<ContentResource> getAllResources(String uploadCollId);

	/**
	 * Get content collection. Note:not in use anymore
	 * 
	 * @param toColl
	 *        The collection id
	 * @return
	 */
	public ContentCollection getCollection(String toColl);

	/**
	 * Get collection based on section's content type for a site. For composed sections, get module's collection and for rest meleteDocs/uploads collection.
	 * 
	 * @param courseId
	 *        The site Id
	 * @param contentType
	 *        Section content type
	 * @param modId
	 *        The module Id
	 * @return
	 */
	public String getCollectionId(String courseId, String contentType, Integer modId);

	/**
	 * Get the module id where the section belongs.
	 * 
	 * @param sectionId
	 *        The section Id
	 * @return
	 */
	public Integer getContainingModule(String sectionId);
	
	/**
	 * Get display name of a resource
	 * 
	 * @param resourceId
	 *        the resource Id
	 * @return
	 */
	public String getDisplayName(String resourceId);

	/**
	 * Get linkType section's content
	 * 
	 * @param resourceId
	 *        The resource id
	 * @return
	 */
	public String getLinkContent(String resourceId);

	/**
	 * Gets all resources of the specified mime type from the site's uploads collection
	 * 
	 * @param collId
	 *        The Collection Id
	 * @param mimeType
	 *        Mime type
	 * @return a list of ContentEntity objects
	 */
	public List<ContentResource> getListFromCollection(String collId, String mimeType);

	/**
	 * Gets all html and media resources of a site.
	 * 
	 * @param collId
	 *        The Collection id
	 * @return a list of ContentEntity objects
	 */
	public List<ContentResource> getListofFilesFromCollection(String collId);

	/**
	 * Get all image resources from a site's uploads collection.
	 * 
	 * @param collId
	 *        The Collection Id
	 * @return a list of ContentEntity objects
	 */
	public List<ContentResource> getListofImagesFromCollection(String collId);

	/**
	 * Get all link resources from a site's uploads collection.Note:not in use anymore
	 * 
	 * @param collId
	 *        The Collection Id
	 * @return a list of ContentEntity objects
	 */
	public List<ContentResource> getListofLinksFromCollection(String collId);

	/**
	 * Get all resources from the collection
	 * 
	 * @param collId
	 *        The Collection Id
	 * @return a list of ContentEntity objects
	 */
	public List<ContentResource> getListofMediaFromCollection(String collId);

	/**
	 * Get all resources from the collection
	 * 
	 * @param collId
	 *        The Collection Id
	 * @return a list of resource names
	 */
	public List<String> getMemberNamesCollection(String collId);

	/**
	 * Get content resource
	 * 
	 * @param resourceId
	 *        The resource Id
	 * @return
	 * @throws Exception
	 */
	public ContentResource getResource(String resourceId) throws Exception;

	/**
	 * Get a resource if you have author access to a site.
	 * 
	 * @param courseId
	 *        The course Id
	 * @param resourceId
	 *        The resource id
	 * @return
	 * @throws Exception
	 */
	public ContentResource getResource(String courseId, String resourceId) throws Exception;

	/**
	 * Get the URL of resource.
	 * 
	 * @param resourceId
	 *        The resource Id
	 * @return
	 */
	public String getResourceUrl(String resourceId);

	/**
	 * Get section resource
	 * 
	 * @param sectionId
	 *        The section Id
	 * @return
	 * @throws Exception
	 */
	public String getSectionResource(String sectionId) throws Exception;

	/**
	 * Get composed section display name.
	 * 
	 * @param sectionId
	 *        The section id
	 * @return
	 */
	public String getTypeEditorSectionName(Integer sectionId);

	/**
	 * Get meleteDocs uploads collection for a site.
	 * 
	 * @param courseId
	 * @return
	 */
	public String getUploadCollectionId(String courseId);

	/**
	 * Move resource to a collection
	 * 
	 * @param resourceId
	 *        The resource Id
	 * @param destinationColl
	 *        module Collection Id
	 * @return
	 * @throws Exception
	 */
	public String moveResource(String resourceId, String destinationColl) throws Exception;

	/**
	 * Delete the entire module's collection.
	 * 
	 * @param delColl_id
	 *        Site Id
	 * @param delSubColl_id
	 *        Module Id
	 * @throws Exception
	 */
	public void removeCollection(String delColl_id, String delSubColl_id) throws Exception;

	/**
	 * Deletes entire site collection from meleteDocs.
	 * 
	 * @param delColl_id
	 *        The site Id
	 * @throws Exception
	 */
	public void removeCourseCollection(String delColl_id) throws Exception;

	/**
	 * Delete resource.
	 * 
	 * @param delRes_id
	 *        The resource id
	 * @throws Exception
	 */
	public void removeResource(String delRes_id) throws Exception;
}
