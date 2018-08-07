/**********************************************************************************
 *
 * $Header:https://source.sakaiproject.org/contrib/etudes/melete/trunk/melete-impl/src/java/org/sakaiproject/component/app/melete/SectionServiceImpl.java,v 1.13 2007/06/25 17:03:13 rashmim Exp $
 * $Id: SectionServiceImpl.java 80446 2012-06-20 19:24:31Z rashmi@etudes.org $
 ***********************************************************************************
 *
 * Copyright (c) 2008, 2009, 2010, 2011 Etudes, Inc.
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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.etudes.api.app.melete.MeleteCHService;
import org.etudes.api.app.melete.MeleteLicenseService;
import org.etudes.api.app.melete.MeleteResourceService;
import org.etudes.api.app.melete.MeleteUserPreferenceService;
import org.etudes.api.app.melete.ModuleObjService;
import org.etudes.api.app.melete.SectionBeanService;
import org.etudes.api.app.melete.SectionObjService;
import org.etudes.api.app.melete.SectionResourceService;
import org.etudes.api.app.melete.SectionService;
import org.etudes.api.app.melete.SectionTrackViewObjService;
import org.etudes.api.app.melete.exception.MeleteException;

public class SectionServiceImpl implements Serializable, SectionService
{
	private Log logger = LogFactory.getLog(SectionServiceImpl.class);

	private MeleteCHService meleteCHService;
	
	private MeleteLicenseDB meleteLicenseDB;
	
	private SectionDB sectiondb;

	public SectionServiceImpl()
	{
		sectiondb = getSectiondb();
	}

	/**
	 * {@inheritDoc}
	 */
	public int changeLicenseForAll(String courseId, MeleteUserPreferenceService mup) throws Exception
	{
		try
		{
			return sectiondb.changeLicenseForAll(courseId, (MeleteUserPreference) mup);
		}
		catch (Exception e)
		{
			throw new MeleteException("all_license_change_fail");
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public int cleanUpDeletedSections(String userId) throws Exception
	{
		int noOfDeleted = sectiondb.cleanUpDeletedSections(userId);
		return noOfDeleted;
	}

	/**
	 * {@inheritDoc}
	 */
	public void deassociateSectionResource(SectionObjService section, SectionResourceService secResource) throws Exception
	{
		try
		{
			sectiondb.deassociateSectionResource((Section) section, (SectionResource) secResource);
		}
		catch (Exception ex)
		{
			logger.debug("EditSectionPage --deassociateSectionResource failed");
			throw new MeleteException(ex.toString());
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public void deleteResource(MeleteResourceService melResource) throws Exception
	{
		try
		{
			sectiondb.deleteResource((MeleteResource) melResource);
		}
		catch (Exception ex)
		{
			logger.debug("AddSectionPage --delete resource failed");
			throw new MeleteException(ex.toString());
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public void deleteResourceInUse(String delResourceId) throws Exception
	{
		sectiondb.deleteResourceInUse(delResourceId);
	}

	/**
	 * {@inheritDoc}
	 */
	public void deleteSection(SectionObjService sec, String courseId, String userId) throws MeleteException
	{
		try
		{
			sectiondb.deleteSection((Section) sec, courseId, userId);
		}
		catch (Exception ex)
		{
			throw new MeleteException("delete_module_fail");
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public void deleteSectionResourcebyId(String sectionId)
	{
		try
		{
			sectiondb.deleteSectionResourcebyId(sectionId);
		}
		catch (Exception ex)
		{
			logger.debug("editSectionPage --delete section resource failed");
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public void deleteSections(List<SectionBeanService> sectionBeans, String courseId, String userId) throws MeleteException
	{
	
		for (ListIterator<SectionBeanService> i = sectionBeans.listIterator(); i.hasNext();)
		{
			SectionBeanService secbean = (SectionBeanService) i.next();

			Section sec = (Section) secbean.getSection();
			deleteSection(sec, courseId, userId);
		}
	}

	public void destroy()
	{
		logger.debug(this + ".destroy()");
	}

	/**
	 * {@inheritDoc}
	 */
	public void editSection(SectionObjService section, String userId) throws Exception
	{
		try
		{
			// edit Section
			sectiondb.editSection((Section) section, userId);
		}
		catch (MeleteException mex)
		{
			throw mex;
		}
		catch (Exception ex)
		{
			throw new MeleteException("add_section_fail");
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public void editSection(SectionObjService section, MeleteResourceService melResource, String userId, Boolean modifyCR) throws MeleteException
	{
		try
		{
			// edit Section
			sectiondb.editSection((Section) section, (MeleteResource) melResource, userId, modifyCR);
		}
		catch (MeleteException mex)
		{
			throw mex;
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			throw new MeleteException("add_section_fail");
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public List<?> findResourceInUse(String selResourceId, String courseId)
	{
		List<?> resourceUseList = null;
		// found in section resources so break
		resourceUseList = sectiondb.checkInSectionResources(selResourceId);
		if (resourceUseList != null && resourceUseList.size() > 0)
		{
			return resourceUseList;
		}
		else
		{
			resourceUseList = sectiondb.findResourceInUse(selResourceId, courseId);
		}
		return resourceUseList;
	}

	/**
	 * {@inheritDoc}
	 */
	public String[] getCCLicenseURL(boolean reqAttr, boolean allowCmrcl, int allowMod)
	{
		try
		{
			return getMeleteLicenseDB().fetchCcLicenseURL(new Boolean(reqAttr), new Boolean(allowCmrcl), new Integer(allowMod));
		}
		catch (Exception ex)
		{
			// need to work on it
			return null;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public Date getLastModifiedDate(Integer sectionId)
	{
		Date last = null;
		try
		{
			last = sectiondb.getLastModifiedDate(sectionId);
		}
		catch (Exception ex)
		{
			logger.debug("failed to get last modified date" + ex.getMessage());
		}
		return last;
	}
	
	/**
	 * @return Returns the meleteLicenseDB.
	 */
	public MeleteLicenseDB getMeleteLicenseDB()
	{
		return meleteLicenseDB;
	}

	/**
	 * {@inheritDoc}
	 */
	public List<MeleteLicenseService> getMeleteLicenses()
	{
		try
		{
			List<MeleteLicenseService> licenses = new ArrayList<MeleteLicenseService>();
			licenses = getMeleteLicenseDB().getLicenseTypes();

			return licenses;
		}
		catch (Exception ex)
		{
			// need to work on it
			return null;
		}

	}

	/**
	 * {@inheritDoc}
	 */
	public MeleteResourceService getMeleteResource(String selResourceId)
	{
		MeleteResource mr = null;
		try
		{
			mr = sectiondb.getMeleteResource(selResourceId);
		}
		catch (Exception ex)
		{
			logger.debug("AddSectionPage --add section resource failed");
		}
		return mr;
	}

	/**
	 * {@inheritDoc}
	 */
	public SectionObjService getNextSection(String curr_id, String seqXML) throws Exception
	{
		SubSectionUtilImpl SectionUtil = new SubSectionUtilImpl();
		org.w3c.dom.Document sectionDocument = SectionUtil.getSubSectionW3CDOM(seqXML);
		org.w3c.dom.Element currItem = sectionDocument.getElementById(curr_id);
		org.w3c.dom.Element nextItem = SectionUtil.getNextSection(currItem);
		if (nextItem != null)
		{
			SectionObjService nextSection = getSection(Integer.parseInt(nextItem.getAttribute("id")));
			return nextSection;
		}
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	public int getNumberOfActiveSections(String course_id)
	{
		try
		{
			return sectiondb.getAllActiveSectionsCount(course_id);
		}
		catch (Exception e)
		{
			return 0;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public Map<String, Integer> getNumberOfSectionViewedByUserId(String courseId)
	{
		try
		{
			return sectiondb.getNumberOfSectionViewedByUserId(courseId);
		}
		catch (Exception e)
		{
			return null;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public Map<Integer, List<String>> getNumberOfViewedSections(String course_id)
	{
		try
		{
			return sectiondb.getAllViewedSectionsCount(course_id);
		}
		catch (Exception e)
		{
			return null;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public SectionObjService getPrevSection(String curr_id, String seqXML) throws Exception
	{
		SubSectionUtilImpl SectionUtil = new SubSectionUtilImpl();
		org.w3c.dom.Document sectionDocument = SectionUtil.getSubSectionW3CDOM(seqXML);
		org.w3c.dom.Element currItem = sectionDocument.getElementById(curr_id);
		org.w3c.dom.Element prevItem = SectionUtil.getPrevSection(sectionDocument, currItem);
		if (prevItem != null)
		{
			SectionObjService prevSection = getSection(Integer.parseInt(prevItem.getAttribute("id")));
			return prevSection;
		}
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	public SectionObjService getSection(int sectionId)
	{
		Section section = new Section();
		try
		{
			section = sectiondb.getSection(sectionId);
		}
		catch (Exception e)
		{
			logger.debug(e.toString());
		}
		return section;
	}

	/**
	 * @return Returns the sectiondb.
	 */
	public SectionDB getSectiondb()
	{
		return sectiondb;
	}

	/**
	 * {@inheritDoc}
	 */
	public String getSectionDisplaySequence(SectionObjService section)
	{
		try
		{
			if (section == null)
			{
				return null;
			}
			;
			ModuleObjService module = section.getModule();
			Map sections = module.getSections();
			List sectionsList = null;
			SubSectionUtilImpl sutil = new SubSectionUtilImpl();
			String startDispSeq = new Integer(module.getCoursemodule().getSeqNo()).toString();
			sutil.traverseDom(module.getSeqXml(), startDispSeq);
			List xmlSecList = sutil.getXmlSecList();
			if (xmlSecList != null)
			{
				sectionsList = new ArrayList();
				for (ListIterator k = xmlSecList.listIterator(); k.hasNext();)
				{
					SecLevelObj slObj = (SecLevelObj) k.next();
					if (section.getSectionId().equals(slObj.getSectionId()))
					{
						return slObj.getDispSeq();
					}
					;

				}
			}
		}
		catch (Exception e)
		{
			logger.debug(e.toString());
		}
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	public SectionResourceService getSectionResource(String secResourceId)
	{
		SectionResource sr = null;
		try
		{
			sr = sectiondb.getSectionResource(secResourceId);
		}
		catch (Exception ex)
		{
			logger.debug("AddSectionPage --add section resource failed");
		}
		return sr;
	}

	/**
	 * {@inheritDoc}
	 */
	public SectionResourceService getSectionResourcebyId(String sectionId)
	{
		SectionResource sr = null;
		try
		{
			sr = sectiondb.getSectionResourcebyId(sectionId);
		}
		catch (Exception ex)
		{
			logger.debug("AddSectionPage --add section resource failed");
		}
		return sr;
	}

	/**
	 * {@inheritDoc}
	 */
	public String getSectionTitle(int sectionId)
	{
		String secTitle = null;
		try
		{
			secTitle = sectiondb.getSectionTitle(sectionId);
		}
		catch (Exception e)
		{
			logger.debug(e.toString());
		}
		return secTitle;
	}
	
	/**
	 * {@inheritDoc}
	 */
	public Map<String, Date> getSectionFirstViewDates(String sectionId) throws Exception
	{
		if (sectionId == null) return null;
		return sectiondb.getSectionUsersFirstViewDate(new Integer(sectionId).intValue());
	}

	/**
	 * {@inheritDoc}
	 */
	public Map<String, Date> getSectionViewDates(String sectionId) throws Exception
	{
		if (sectionId == null) return null;
		return sectiondb.getSectionUsersViewDate(new Integer(sectionId).intValue());
	}

	/**
	 * {@inheritDoc}
	 */
	public Integer getSectionViewersCount(String sectionId)
	{
		if (sectionId == null) return 0;
		return sectiondb.getSectionViewersCount(new Integer(sectionId).intValue());
	}

	/**
	 * {@inheritDoc}
	 */
	public List getSortSections(ModuleObjService module)
	{
		try
		{
			Map sections = module.getSections();
			List sectionsList = null;
			SubSectionUtilImpl sutil = new SubSectionUtilImpl();
			String startDispSeq = new Integer(module.getCoursemodule().getSeqNo()).toString();
			sutil.traverseDom(module.getSeqXml(), startDispSeq);
			List xmlSecList = sutil.getXmlSecList();
			if (xmlSecList != null)
			{
				sectionsList = new ArrayList();
				for (ListIterator k = xmlSecList.listIterator(); k.hasNext();)
				{
					SecLevelObj slObj = (SecLevelObj) k.next();
					if (slObj != null)
					{
						Section sec = (Section) sections.get(new Integer(slObj.getSectionId()));
						String addBefore = "| - ";
						for (int i = 0; i < slObj.getLevel(); i++)
							addBefore = addBefore + "- ";
						SectionBeanService secBean = new SectionBean(sec);
						secBean.setDisplaySequence(addBefore + slObj.getDispSeq());
						sectionsList.add(secBean);
					}
				}
			}
			return sectionsList;
		}
		catch (Exception e)
		{
			logger.debug(e.toString());
		}
		return null;
	}

	/**
	 * Get sections in order
	 */
	public List<SectionObjService> getSections(ModuleObjService module)
	{
		try
		{
			Map<Integer,SectionObjService> sections = module.getSections();
			List<SectionObjService> sectionsList = null;
			SubSectionUtilImpl sutil = new SubSectionUtilImpl();
			String startDispSeq = new Integer(module.getCoursemodule().getSeqNo()).toString();
			sutil.traverseDom(module.getSeqXml(), startDispSeq);
			List xmlSecList = sutil.getXmlSecList();
			if (xmlSecList != null)
			{
				sectionsList = new ArrayList<SectionObjService>();
				for (ListIterator k = xmlSecList.listIterator(); k.hasNext();)
				{
					SecLevelObj slObj = (SecLevelObj) k.next();
					if (slObj != null)
					{
						SectionObjService sec = sections.get(new Integer(slObj.getSectionId()));
						sectionsList.add(sec);
					}
				}
			}
			return sectionsList;
		}
		catch (Exception e)
		{
			logger.debug(e.toString());
		}
		return null;
	}
	
	/**
	 * Final initialization, once all dependencies are set.
	 */
	public void init()
	{
		if (logger.isDebugEnabled()) logger.debug(this + ".init()");

		List ccLicenseList = meleteLicenseDB.getCcLicense();
		if (ccLicenseList != null && ccLicenseList.size() == 0)
		{
			ArrayList ccLicenses = new ArrayList();
			ccLicenses.add(new CcLicense(false, false, 0, "http://creativecommons.org/licenses/publicdomain/", "Public Domain Dedication"));
			ccLicenses.add(new CcLicense(true, false, 0, "http://creativecommons.org/licenses/by-nc-nd/2.0/", "Attribution-NonCommercial-NoDerivs"));
			ccLicenses
			.add(new CcLicense(true, false, 1, "http://creativecommons.org/licenses/by-nc-sa/2.0/", "Attribution-NonCommercial-ShareAlike"));
			ccLicenses.add(new CcLicense(true, false, 2, "http://creativecommons.org/licenses/by-nc/2.0/", "Attribution-NonCommercial"));
			ccLicenses.add(new CcLicense(true, true, 0, "http://creativecommons.org/licenses/by-nd/2.0/", "Attribution-NoDerivs"));
			ccLicenses.add(new CcLicense(true, true, 1, "http://creativecommons.org/licenses/by-sa/2.0/", "Attribution-ShareAlike"));
			ccLicenses.add(new CcLicense(true, true, 2, "http://creativecommons.org/licenses/by/2.0/", "Attribution"));
			meleteLicenseDB.createCcLicense(ccLicenses);
		}

		List moduleLicenseList = meleteLicenseDB.getLicenseTypes();
		if (moduleLicenseList != null && moduleLicenseList.size() == 0)
		{
			ArrayList moduleLicenses = new ArrayList();
			moduleLicenses.add(new MeleteLicense(new Integer(0), "I have not determined copyright yet"));
			moduleLicenses.add(new MeleteLicense(new Integer(1), "Copyright of Author"));
			moduleLicenses.add(new MeleteLicense(new Integer(2), "Public Domain"));
			moduleLicenses.add(new MeleteLicense(new Integer(3), "Creative Commons License"));
			moduleLicenses.add(new MeleteLicense(new Integer(4), "Fair Use Exception"));
			meleteLicenseDB.createMeleteLicense(moduleLicenses);
		}

		logger.info(this + ".init() completed successfully");
	}

	/**
	 * {@inheritDoc}
	 */
	public void insertMeleteResource(SectionObjService section, MeleteResourceService melResource) throws Exception
	{
		try
		{
			sectiondb.insertMeleteResource((Section) section, (MeleteResource) melResource);
		}
		catch (Exception ex)
		{
			logger.debug("AddSectionPage --add section resource failed");
			throw new MeleteException(ex.toString());
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public void insertResource(MeleteResourceService melResource) throws Exception
	{
		try
		{
			sectiondb.insertResource((MeleteResource) melResource);
		}
		catch (Exception ex)
		{
			logger.debug("AddSectionPage --add resource failed");
			throw new MeleteException(ex.toString());
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public Integer insertSection(ModuleObjService module, SectionObjService section, String userId) throws MeleteException
	{
		try
		{
			// insert new Section
			logger.debug("dtd in insersection - impl layer");
			Integer newsectionId = sectiondb.addSection((Module) module, (Section) section, false, userId);
			return newsectionId;

		}
		catch (Exception ex)
		{
			logger.debug("section business --add section failed");
			throw new MeleteException("add_section_fail");
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public void insertSectionResource(SectionObjService section, MeleteResourceService melResource) throws Exception
	{
		try
		{
			sectiondb.insertSectionResource((Section) section, (MeleteResource) melResource);
		}
		catch (Exception ex)
		{
			logger.debug("AddSectionPage --add section resource failed");
			throw new MeleteException(ex.toString());
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public void insertSectionTrackforAModule(int moduleId, String userId)
	{		
		try
		{
			sectiondb.insertSectionTrackforAModule(moduleId, userId);
		}
		catch (Exception ex)
		{
			
		}		
	}
	
	/**
	 * {@inheritDoc}
	 */
	public SectionTrackView insertSectionTrack(int sectionId, String userId)
	{
		SectionTrackView returnStv = null;
		try
		{
			returnStv = sectiondb.insertSectionTrack(sectionId, userId);
		}
		catch (Exception ex)
		{
			logger.error("ViewSectionPage --add/update section track failed for user " + userId + " for section " + sectionId);
		}
		return returnStv;
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
	 * @param meleteLicenseDB
	 *        The meleteLicenseDB to set.
	 */
	public void setMeleteLicenseDB(MeleteLicenseDB meleteLicenseDB)
	{
		this.meleteLicenseDB = meleteLicenseDB;
	}

	/**
	 * @param sectiondb
	 *        The sectiondb to set.
	 */
	public void setSectiondb(SectionDB sectiondb)
	{
		this.sectiondb = sectiondb;
	}

	/**
	 * {@inheritDoc}
	 */
	public void updateResource(MeleteResourceService melResource) throws Exception
	{
		try
		{
			sectiondb.updateResource((MeleteResource) melResource);
		}
		catch (Exception ex)
		{
			logger.debug("EditSectionPage --update resource failed");
			throw new MeleteException(ex.toString());
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public void updateSectionResource(SectionObjService section, SectionResourceService secResource) throws Exception
	{
		try
		{
			sectiondb.updateSectionResource((Section) section, (SectionResource) secResource);
		}
		catch (Exception ex)
		{
			logger.debug("EditSectionPage --updateSectionResource failed");
			throw new MeleteException(ex.toString());
		}
	}
}
