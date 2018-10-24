/**********************************************************************************
 *
 * $URL: https://source.sakaiproject.org/contrib/etudes/melete/tags/2.9.9/melete-api/src/java/org/etudes/api/app/melete/SectionService.java $
 * $Id: SectionService.java 87125 2014-10-16 19:48:52Z mallika@etudes.org $  
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
package org.etudes.api.app.melete;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.etudes.api.app.melete.SectionTrackViewObjService;

public interface SectionService
{
	public static final int MAX_URL_LENGTH = 150;
	public static final int MAX_TITLE_INSTR_LENGTH = 250;
	
	/**
	 * Change license for all sections with the preferred one.
	 * 
	 * @param courseId
	 *        The course id
	 * @param mup
	 *        The Preferred license
	 * @return count of affected sections
	 * @throws Exception
	 */
	public int changeLicenseForAll(String courseId, MeleteUserPreferenceService mup) throws Exception;

	/**
	 * Clean up database. Removes the orphaned deleted sections. Part of the melete admin tool to cleanup deleted modules and sections before deep delete.
	 * 
	 * @param userId
	 *        The user Id
	 * @return
	 * @throws Exception
	 */
	public int cleanUpDeletedSections(String userId) throws Exception;

	/**
	 * Note: not in use anymore.
	 * 
	 * @param section
	 * @param secResource
	 * @throws Exception
	 */
	public void deassociateSectionResource(SectionObjService section, SectionResourceService secResource) throws Exception;

	/**
	 * Deletes the resource from database.
	 * 
	 * @param melResource
	 *        MeleteResourceService object
	 * 
	 * @throws Exception
	 *         "add_section_fail" MeleteException
	 */
	public void deleteResource(MeleteResourceService melResource) throws Exception;

	/**
	 * Delete the resource which is in use by other sections
	 * 
	 * @param delResourceId
	 *        The resource id
	 * @throws Exception
	 */
	public void deleteResourceInUse(String delResourceId) throws Exception;

	/**
	 * Delete section and its associated resource if not shared by other sections.
	 * 
	 * @param sec
	 *        The section
	 * @param courseId
	 *        The site Id
	 * @param userId
	 *        The user Id
	 * @throws Exception
	 *         "delete_module_fail" MeleteException
	 */
	public void deleteSection(SectionObjService sec, String courseId, String userId) throws Exception;

	/**
	 * Delete the section - resource association.
	 * 
	 * @param sectionId
	 *        The section Id
	 */
	public void deleteSectionResourcebyId(String sectionId);

	/**
	 * Delete a list of sections.
	 * 
	 * @param sectionBeans
	 *        List of SectionBean objects
	 * @param courseId
	 *        The site Id
	 * @param userId
	 *        The user Id
	 * @throws Exception
	 *         "delete_module_fail" MeleteException
	 */
	public void deleteSections(List<SectionBeanService> sectionBeans, String courseId, String userId) throws Exception;

	/**
	 * Updates the section.
	 * 
	 * @param section
	 *        The Section
	 * @param userId
	 * 		The user Id       
	 * @throws Exception
	 */
	public void editSection(SectionObjService section, String userId) throws Exception;

	/**
	 * Updates the section and the resource.
	 * 
	 * @param section
	 *        The Section
	 * @param melResource
	 *        The Melete Resource
	 * @param userId
	 * 		The user Id
	 * @param modifyCR
	 * 		Composed content has been modified or not
	 * @throws Exception
	 *         "add_section_fail" and "edit_section_multiple_users" MeleteException
	 */
	public void editSection(SectionObjService section, MeleteResourceService melResource, String userId, Boolean modifyCR) throws Exception;

	/**
	 * Find all sections where the resource is used.
	 * 
	 * @param selResourceId
	 *        The resource id
	 * @param courseId
	 *        The site Id
	 * @return a list of Object[] with 0 index as SectionObjService and 1 as SectionResource if resource found as uploaded/linked media
	 * and a list of SectionObjService if found as embedded media.
	 * 
	 */
	public List<?> findResourceInUse(String selResourceId, String courseId);

	/**
	 * Get the license URL. For all combinations there is a distinct creative commons license URL.
	 * 
	 * @param reqAttr
	 *        require attribution part of license
	 * @param allowCmrcl
	 *        allow commercial use part of license
	 * @param allowMod
	 *        allow Modifications part of license
	 * @return
	 */
	public String[] getCCLicenseURL(boolean reqAttr, boolean allowCmrcl, int allowMod);

	/**
	 * Get the last modified date of the section.
	 * 
	 * @param sectionId
	 * @return
	 */
	public Date getLastModifiedDate(Integer sectionId);
	
	/**
	 * Get all available licenses
	 * 
	 * @return list of MeleteLicense objects
	 */
	public List<MeleteLicenseService> getMeleteLicenses();

	/**
	 * Get Melete Resource.
	 * 
	 * @param selResourceId
	 *        The resource Id
	 * @return
	 */
	public MeleteResourceService getMeleteResource(String selResourceId);

	/**
	 * Get the next section. Reads section sequence xml and returns the next section.
	 * 
	 * @param curr_id
	 *        The current section id
	 * @param seqXML
	 *        The sequence XML
	 * @return next section or null if its the last section of a module.
	 * @throws Exception
	 */
	public SectionObjService getNextSection(String curr_id, String seqXML) throws Exception;

	/**
	 * Get the total number of active sections in a site. This doesn't count sections of the archived module.
	 * 
	 * @param courseId
	 *        the site Id
	 * @return count
	 */
	public int getNumberOfActiveSections(String courseId);

	/**
	 * Get number of sections viewed per user of a site.
	 * 
	 * @param courseId
	 *        the site Id
	 * @return Map <userId, count of sections> of all viewed sections
	 */
	public Map<String, Integer> getNumberOfSectionViewedByUserId(String courseId);

	/**
	 * Get all sections viewed so far in the site.
	 * 
	 * @param courseId
	 *        the site Id
	 * @return Map <sectionId, List of user ids> of all viewed sections
	 */
	public Map<Integer, List<String>> getNumberOfViewedSections(String courseId);

	/**
	 * Get the previous section. Reads the section sequence xml and returns the previous section.
	 * 
	 * @param curr_id
	 *        The current section id
	 * @param seqXML
	 *        The sequence XML
	 * @return previous section or null if its the first section of a module.
	 * @throws Exception
	 */
	public SectionObjService getPrevSection(String curr_id, String seqXML) throws Exception;

	/**
	 * Get the section.
	 * 
	 * @param sectionId
	 *        The section Id
	 * @return
	 */
	public SectionObjService getSection(int sectionId);

	/**
	 * Get the display sequence of a section like "1.1 section 1", where section 1 is the first section of the first module.
	 * 
	 * @param section
	 *        SectionObjService object
	 * @return
	 */
	public String getSectionDisplaySequence(SectionObjService section);

	/**
	 * Get section Resource.
	 * 
	 * @param secResourceId
	 *        The resource Id
	 * @return
	 */
	public SectionResourceService getSectionResource(String secResourceId);

	/**
	 * Get section resource
	 * 
	 * @param sectionId
	 *        The section id
	 * @return
	 */
	public SectionResourceService getSectionResourcebyId(String sectionId);

	/**
	 * Get the section title
	 * 
	 * @param sectionId
	 *        The section Id
	 * @return
	 */
	public String getSectionTitle(int sectionId);

	/**
	 * Get all users first view date information for a section.
	 * 
	 * @param sectionId
	 *        the section id
	 * @return a Map<userId, FirstViewDate> information for a section.
	 * 
	 * @throws Exception
	 * 
	 */
	public Map<String, Date> getSectionFirstViewDates(String sectionId) throws Exception;
	
	/**
	 * Get all users view date information for a section.
	 * 
	 * @param sectionId
	 *        the section id
	 * @return a Map<userId, ViewDate> information for a section.
	 * 
	 * @throws Exception
	 * 
	 */
	public Map<String, Date> getSectionViewDates(String sectionId) throws Exception;

	/**
	 * Get the list of sectionBeans. The display sequence shows the top section/subsection level by adding -'s like |- section 1
	 * 
	 * @param module
	 *        The Module
	 * @return a list of secBean objects
	 */
	public List<SectionBeanService> getSortSections(ModuleObjService module);

	/**
	 * Get the sections in the order specified in module xml sequence.
	 * 
	 * @param module
	 *        Module Object
	 * @return a list of sectionObjService objects
	 */
	public List<SectionObjService> getSections(ModuleObjService module);
	
	/**
	 * Inserts the association and inserts a new melete resource
	 * 
	 * @param section
	 * @param melResource
	 * @throws Exception
	 */
	public void insertMeleteResource(SectionObjService section, MeleteResourceService melResource) throws Exception;

	/**
	 * Adds a melete resource.
	 * 
	 * @param melResource
	 *        MeleteResourceService Object
	 * 
	 * @throws Exception
	 *         "add_section_fail" MeleteException
	 */
	public void insertResource(MeleteResourceService melResource) throws Exception;

	/**
	 * Adds a section in the database and updates module's sequence XML.
	 * 
	 * @param module
	 *        The module
	 * @param section
	 *        The section
	 * @param userId
	 * 		Id of author creating the section       
	 * @return
	 * @throws Exception
	 *         "add_section_fail" MeleteException
	 */
	public Integer insertSection(ModuleObjService module, SectionObjService section, String userId) throws Exception;

	/**
	 * this method inserts the association in between section and resource and updates melete resource object
	 * 
	 * @param section
	 * @param melResource
	 * @throws Exception
	 */
	public void insertSectionResource(SectionObjService section, MeleteResourceService melResource) throws Exception;

	/**
	 * Insert new section tracking info or update section tracking info if it exists already for all sections of a module
	 *
	 * @param moduleId
	 *        Module id
	 * @param userId
	 *        User id
	 */
	public void insertSectionTrackforAModule(int moduleId, String userId);
	
	/**
	 * Insert new section tracking info or update section tracking info if it exists already
	 * Before updating, store current tracking info to return and display
	 * 
	 * @param sectionId
	 *        Section id
	 * @param userId
	 *        User id
	 * @return SectionTrackViewObjService object, if found or null
	 */
	public SectionTrackViewObjService insertSectionTrack(int sectionId, String userId);
	
	/**
	 * Updates the melete resource.
	 * 
	 * @param melResource
	 *        MeleteResourceService object
	 * 
	 * @throws Exception
	 *         "add_section_fail" MeleteException
	 */
	public void updateResource(MeleteResourceService melResource) throws Exception;

	/**
	 * Update sectionResource and change the resource associated with a section
	 * 
	 * @param section
	 *        The section
	 * @param secResource
	 *        The section resource
	 * @throws Exception
	 */
	public void updateSectionResource(SectionObjService section, SectionResourceService secResource) throws Exception;

	/**
	 * Fix xrefs to refer to current site resources. 
	 * 
	 * @param contents
	 * @param courseId
	 * @return
	 */
	public String fixXrefs(String contents, String courseId);
}
