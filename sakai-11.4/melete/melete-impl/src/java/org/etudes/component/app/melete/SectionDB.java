/**********************************************************************************
 *
 * $URL: https://source.sakaiproject.org/contrib/etudes/melete/tags/2.9.9/melete-impl/src/java/org/etudes/component/app/melete/SectionDB.java $
 * $Id: SectionDB.java 87125 2014-10-16 19:48:52Z mallika@etudes.org $
 ***********************************************************************************
 *
 * Copyright (c) 2008, 2009, 2010, 2011, 2012 Etudes, Inc.
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
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.ListIterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Element;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.StaleObjectStateException;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.type.StandardBasicTypes;
import org.etudes.api.app.melete.MeleteCHService;
import org.etudes.api.app.melete.MeleteSecurityService;
import org.etudes.api.app.melete.ModuleObjService;
import org.etudes.api.app.melete.SectionObjService;
import org.etudes.api.app.melete.SectionTrackViewObjService;
import org.etudes.api.app.melete.exception.MeleteException;
import org.sakaiproject.user.api.User;
import org.sakaiproject.user.cover.UserDirectoryService;
import org.sakaiproject.entity.cover.EntityManager;
import org.sakaiproject.entity.api.Reference;
import org.sakaiproject.util.StringUtil;
import org.sakaiproject.util.Validator;
import org.sakaiproject.component.cover.ServerConfigurationService;

public class SectionDB implements Serializable
{
	private HibernateUtil hibernateUtil;
	private ModuleDB moduleDB;
	private MeleteCHService meleteCHService;
	private MeleteSecurityService meleteSecurityService;

	public static final int MELETE_RESOURCE_ONLY = 0;
	public static final int SECTION_RESOURCE_ONLY = 1;
	public static final int MELETE_RESOURCE_SECTION_RESOURCE = 2;
	public static final int NONE_TO_DELETE = 3;

	/** Dependency: a logger component. */
	private Log logger = LogFactory.getLog(SectionDB.class);
	
	private MeleteUtil meleteUtil = new MeleteUtil();


	public SectionDB()
	{
		hibernateUtil = getHibernateUtil();
	}

	/**
	 * Add section sets the not-null values not been populated yet and then inserts the section into section table. update module witht his association to new added section If error in committing transaction, it rollbacks the transaction.
	 */
	public Integer addSection(Module module, Section section, boolean fromImport, String userId) throws MeleteException
	{
		try
		{
			Session session = hibernateUtil.currentSession();
			if (session != null) session.clear();
			Transaction tx = null;
			try
			{
				// set default values for not-null fields
				section.setCreationDate(new java.util.Date());
				section.setModificationDate(new java.util.Date());
				section.setModuleId(module.getModuleId().intValue());
				section.setDeleteFlag(false);
				section.setUserId(userId);
				section.setModifyUserId(userId);
				// save object
				if (!session.isOpen())
				{
					session = hibernateUtil.currentSession();
				}
				tx = session.beginTransaction();
				session.save(section);

				if (!fromImport)
				{
					Query query = session.createQuery("from Module mod where mod.moduleId=:moduleId");
					query.setParameter("moduleId", module.getModuleId());
					List secModules = query.list();
					if (secModules != null)
					{
						Module secModule = (Module) secModules.get(0);
						// set xml structure for sequencing and placement of sections
						String sectionsSeqXML = secModule.getSeqXml();
						SubSectionUtilImpl SectionUtil = new SubSectionUtilImpl();
						logger.debug("adding section id to the xmllist" + section.getSectionId().toString());
						SectionUtil.addSectiontoList(sectionsSeqXML, section.getSectionId().toString());
						sectionsSeqXML = SectionUtil.storeSubSections();
						secModule.setSeqXml(sectionsSeqXML);
						session.saveOrUpdate(secModule);
					}
					else
						throw new MeleteException("add_section_fail");
				}

				tx.commit();

				if (logger.isDebugEnabled())
					logger.debug("commiting transaction and new added section id:" + section.getSectionId() + "," + section.getTitle());
				return section.getSectionId();
			}
			catch (StaleObjectStateException sose)
			{
				logger.error("add section stale object exception" + sose.toString());
				if (tx != null) tx.rollback();
				throw sose;
			}
			catch (ConstraintViolationException cve)
			{
				logger.error("constraint voilation exception" + cve.getConstraintName());
				throw cve;
			}
			catch (HibernateException he)
			{
				if (tx != null) tx.rollback();
				logger.error("add section HE exception" + he.toString());
				throw he;
			}
			finally
			{
				hibernateUtil.closeSession();
			}
		}
		catch (Exception ex)
		{
			// Throw application specific error
			ex.printStackTrace();
			throw new MeleteException("add_section_fail");
		}

	}

	/**
	 * Compare 2 section objects.
	 * 
	 * @param obj1
	 *        Object 1
	 * @param obj2
	 *        Object 2
	 * @return true if objects are same
	 */
	private boolean shouldUpdateSectionObject(Section obj1, Section obj2)
	{
		if (obj1 == null || obj2 == null) return false;
		if (obj1.getClass() != obj2.getClass()) return false;

		if (obj1.isAudioContent() != obj2.isAudioContent()) return false;
		if (obj1.getContentType() == null)
		{
			if (obj2.getContentType() != null) return false;
		}
		else if (!obj1.getContentType().equals(obj2.getContentType())) return false;
		if (obj1.isDeleteFlag() != obj2.isDeleteFlag()) return false;
		if (obj1.getInstr() == null || obj1.getInstr().length() == 0)
		{
			if (obj2.getInstr() != null && obj2.getInstr().trim().length() != 0) return false;
		}
		else if (!obj1.getInstr().equals(obj2.getInstr())) return false;
		if (obj1.getModuleId() != obj2.getModuleId()) return false;
		if (obj1.isOpenWindow() != obj2.isOpenWindow()) return false;
		if (obj1.isTextualContent() != obj2.isTextualContent()) return false;
		if (obj1.getTitle() == null)
		{
			if (obj2.getTitle() != null) return false;
		}
		else if (!obj1.getTitle().equals(obj2.getTitle())) return false;
		if (obj1.isVideoContent() != obj2.isVideoContent()) return false;
		return true;
	}
	
	/**
	 * Compare 2 Melete Resource objects
	 * 
	 * @param obj1
	 *        Object 1
	 * @param obj2
	 *        Object 2
	 * @return true if same
	 */
	private boolean shouldUpdateMeleteResourceObject(MeleteResource obj1, MeleteResource obj2)
	{
		if (obj1 == null || obj2 == null) return false;
		if (obj1.getClass() != obj2.getClass()) return false;

		if (obj1.isAllowCmrcl() != obj2.isAllowCmrcl()) return false;
		if (obj1.getAllowMod() != obj2.getAllowMod()) return false;
		if (obj1.getCcLicenseUrl() == null)
		{
			if (obj2.getCcLicenseUrl() != null) return false;
		}
		else if (!obj1.getCcLicenseUrl().equals(obj2.getCcLicenseUrl())) return false;
		if (obj1.getCopyrightOwner() == null)
		{
			if (obj2.getCopyrightOwner() != null) return false;
		}
		else if (!obj1.getCopyrightOwner().equals(obj2.getCopyrightOwner())) return false;
		if (obj1.getCopyrightYear() == null)
		{
			if (obj2.getCopyrightYear() != null) return false;
		}
		else if (!obj1.getCopyrightYear().equals(obj2.getCopyrightYear())) return false;
		if (obj1.getLicenseCode() != obj2.getLicenseCode()) return false;
		if (obj1.isReqAttr() != obj2.isReqAttr()) return false;
		if (obj1.getResourceId() == null)
		{
			if (obj2.getResourceId() != null) return false;
		}
		else if (!obj1.getResourceId().equals(obj2.getResourceId())) return false;
		return true;
	}
	
	
	/**
	 * Update section.
	 * 
	 * @param section
	 *        The section
	 * @return
	 * @throws Exception
	 *         "edit_section_multiple_users" and "add_section_fail" MeleteException
	 */
	public Integer editSection(Section section, String userId) throws Exception
	{
		try
		{
			Session session = hibernateUtil.currentSession();
			Transaction tx = null;
			try
			{
				// refresh section object
				String queryString = "select section from Section as section where section.sectionId = :sectionId";
				Query query = session.createQuery(queryString);
				query.setParameter("sectionId", section.getSectionId());
				Section findSection = (Section) query.uniqueResult();
			
				//back-filling data with blank				
				if (findSection.getUserId() == null) findSection.setUserId("");
				if (findSection.getModifyUserId() == null) findSection.setModifyUserId("");
				
				//compare before saving
				if(shouldUpdateSectionObject(findSection, section))
				{
					logger.debug("2 sections are equal...no modification");			
				}
				else
				{
					// update modification details only when there is modification
					findSection.setModificationDate(new java.util.Date());
					findSection.setModifyUserId(userId);
				}
				findSection.setAudioContent(section.isAudioContent());
				findSection.setContentType(section.getContentType());
				findSection.setInstr(section.getInstr());				
				findSection.setOpenWindow(section.isOpenWindow());
				findSection.setTextualContent(section.isTextualContent());
				findSection.setTitle(section.getTitle());
				findSection.setVideoContent(section.isVideoContent());			

				// save object
				if (!session.isOpen()) session = hibernateUtil.currentSession();
				tx = session.beginTransaction();
				session.saveOrUpdate(findSection);
				session.flush();
				tx.commit();

				if (logger.isDebugEnabled())
					logger.debug("commiting transaction and new added section id:" + section.getSectionId() + "," + section.getTitle());
				return section.getSectionId();

			}
			catch (StaleObjectStateException sose)
			{
				if (tx != null) tx.rollback();
				logger.error("edit section stale object exception" + sose.toString());
				throw new MeleteException("edit_section_multiple_users");
			}
			catch (ConstraintViolationException cve)
			{
				if (tx != null) tx.rollback();
				logger.error("constraint voilation exception" + cve.getConstraintName());
				throw new MeleteException("add_section_fail");
			}
			catch (HibernateException he)
			{
				if (tx != null) tx.rollback();
				logger.error("edit section stale object exception" + he.toString());
				throw new MeleteException("add_section_fail");
			}
			finally
			{
				hibernateUtil.closeSession();
			}
		}
		catch (Exception ex)
		{
			// Throw application specific error
			throw ex;
		}

	}

	/**
	 * Updates section and the resource.
	 * 
	 * @param section
	 *        Section
	 * @param melResource
	 *        MeleteResource
	 * @param  userId
	 * 		The user Id      
	 * @throws Exception
	 *         "edit_section_multiple_users" and "add_section_fail" MeleteException
	 */

	public void editSection(Section section, MeleteResource melResource, String userId, Boolean modifyCR) throws Exception
	{
		try
		{
			SectionResource secResource = getSectionResourcebyId(section.getSectionId().toString());
			Session session = hibernateUtil.currentSession();
			Transaction tx = null;
			try
			{
				// fetch from database now
				Section findSection = null;
				SectionResource findSecResource = null;
				MeleteResource findMelResource = null;
				boolean melResExists = false, secResExists = false;
				String queryString = "from Section section where section.sectionId=:sectionId";
				List result_list = session.createQuery(queryString).setParameter("sectionId", section.getSectionId()).list();
				if (result_list != null && result_list.size() > 0) findSection = (Section) result_list.get(0);

				queryString = "from SectionResource sectionresource where sectionresource.sectionId=:sectionId";
				result_list = session.createQuery(queryString).setParameter("sectionId", section.getSectionId()).list();
				if (result_list != null && result_list.size() > 0) findSecResource = (SectionResource) result_list.get(0);

				if (findSecResource != null) findMelResource = (MeleteResource) findSecResource.getResource();
				if (findMelResource != null)
				{
					queryString = "from MeleteResource meleteresource where meleteresource.resourceId=:resourceId";
					result_list = session.createQuery(queryString).setParameter("resourceId", findMelResource.getResourceId()).list();
					if (result_list != null && result_list.size() > 0) findMelResource = (MeleteResource) result_list.get(0);
				}

				//back-filling data with blank
				if (findSection.getUserId() == null) findSection.setUserId("");
				if (findSection.getModifyUserId() == null) findSection.setModifyUserId("");
				
				// compare
				if (!shouldUpdateSectionObject(section, findSection) || (melResource != null && !shouldUpdateMeleteResourceObject(melResource, findMelResource)) || modifyCR)
				{
					// modification details
					findSection.setModificationDate(new java.util.Date());		
					findSection.setModifyUserId(userId);
				}

				// if new associated resource is different than old one, fetch from db
				if (melResource != null)
				{
					queryString = "from MeleteResource meleteresource where meleteresource.resourceId=:resourceId";
					result_list = session.createQuery(queryString).setParameter("resourceId", melResource.getResourceId()).list();
					if (result_list != null && result_list.size() > 0)
						findMelResource = (MeleteResource) result_list.get(0);
					else
						findMelResource = null;
				}

				// save data
				if (melResource != null && findMelResource == null)
				{
					melResExists = true;
					findMelResource = new MeleteResource();
					findMelResource.setResourceId(melResource.getResourceId());
				}
				tx = session.beginTransaction();
				if (melResource != null)
				{
					findMelResource.setLicenseCode(melResource.getLicenseCode());
					findMelResource.setCcLicenseUrl(melResource.getCcLicenseUrl());
					findMelResource.setReqAttr(melResource.isReqAttr());
					findMelResource.setAllowCmrcl(melResource.isAllowCmrcl());
					findMelResource.setAllowMod(melResource.getAllowMod());
					findMelResource.setCopyrightYear(melResource.getCopyrightYear());
					findMelResource.setCopyrightOwner(melResource.getCopyrightOwner());
					if (melResExists)session.save(findMelResource);
					else session.update(findMelResource);
				}
				else findMelResource = null;
				
				// set default values for not-null fields
				if (findSecResource == null)
				{
					secResExists = true;
					findSecResource = new SectionResource();
				}

				findSecResource.setSection(findSection);
				findSecResource.setResource(findMelResource);
				if (secResExists) session.save(findSecResource);
				else session.update(findSecResource);

				// section object
				findSection.setAudioContent(section.isAudioContent());
				findSection.setContentType(section.getContentType());
				findSection.setInstr(section.getInstr());				
				findSection.setOpenWindow(section.isOpenWindow());
				findSection.setTextualContent(section.isTextualContent());
				findSection.setTitle(section.getTitle());
				findSection.setVideoContent(section.isVideoContent());				
				findSection.setSectionResource(findSecResource);
	
				session.saveOrUpdate(findSection);
				session.flush();
				tx.commit();

				if (logger.isDebugEnabled())
					logger.debug("commit transaction and edit section :" + section.getSectionId() + "," + section.getTitle());
				// updateExisitingResource(secResource);
				return;
			}
			catch (StaleObjectStateException sose)
			{
				if (tx != null) tx.rollback();
				logger.error("edit section stale object exception" + sose.toString());
				throw new MeleteException("edit_section_multiple_users");
			}
			catch (ConstraintViolationException cve)
			{
				if (tx != null) tx.rollback();
				logger.error("constraint voilation exception" + cve.getConstraintName());
				throw new MeleteException("add_section_fail");
			}
			catch (HibernateException he)
			{
				if (tx != null) tx.rollback();
				logger.error("edit section HE exception" + he.toString());
				he.printStackTrace();
				throw new MeleteException("add_section_fail");
			}
			finally
			{
				hibernateUtil.closeSession();
			}
		}
		catch (Exception ex)
		{
			// Throw application specific error
			throw ex;
		}
	}

	/**
	 * 
	 */
	public Date getLastModifiedDate(Integer sectionId)
	{
		Date secModifyDate = null;
		try
		{
			Session session = hibernateUtil.currentSession();
			String queryString = "select s.modificationDate from Section s where s.sectionId = :sectionId";
			Query query = session.createQuery(queryString);
			query.setParameter("sectionId", sectionId);
			Object res = query.uniqueResult();
			if (res != null) secModifyDate = (Date) res;
		}
		catch (HibernateException he)
		{
			logger.error(he.toString());
		}
		finally
		{
			try
			{
				hibernateUtil.closeSession();
			}
			catch (HibernateException he)
			{
				logger.error(he.toString());
			}
		}
		return secModifyDate;
	}
	
	/**
	 * Depending on the deleteFrom parameter, this method cleans out the section from various MELETE tables
	 * 
	 * @param sec
	 *        Section
	 * @param userId
	 *        The user Id
	 * @param deleteFrom
	 *        NONE_TO_DELETE, MELETE_RESOURCE_ONLY , MELETE_RESOURCE_SECTION_RESOURCE, SECTION_RESOURCE_ONLY
	 * @param embedResourceId
	 *        The resource Id
	 * @throws MeleteException
	 */
	private void deleteFromMeleteTables(Section sec, String userId, int deleteFrom, String embedResourceId) throws MeleteException
	{
		SectionResource secRes = null;
		int affectedEntities = 0;

		// These are the queries
		String updSectionResourceStr = "update SectionResource sr set sr.resource = null where sr.sectionId=:sectionId";
		String delMeleteResourceStr = "delete MeleteResource mr where mr.resourceId=:resourceId";
		String delSectionResourceStr = "delete SectionResource sr where sr.sectionId=:sectionId";
		String delBookmarksStr = "delete Bookmark bm where bm.sectionId=:sectionId";
		String delSectionViewStr = "delete SectionTrackView stv where stv.sectionId=:sectionId";
		String delSectionStr = "delete Section sec where sec.sectionId=:sectionId";
		String selModuleStr = "select mod.seqXml from Module mod where mod.moduleId=:moduleId";
		String updModuleStr = "update Module mod set mod.seqXml=:seqXml where mod.moduleId=:moduleId";

		try
		{
			Transaction tx = null;
			Session session = hibernateUtil.currentSession();

			try
			{
				tx = session.beginTransaction();

				secRes = (SectionResource) sec.getSectionResource();

				if (deleteFrom != NONE_TO_DELETE)
				{
					if ((deleteFrom == MELETE_RESOURCE_ONLY) || (deleteFrom == MELETE_RESOURCE_SECTION_RESOURCE))
					{
						// Delete from MELETE_RESOURCE table
						if (deleteFrom == MELETE_RESOURCE_ONLY)
						{
							if (embedResourceId != null)
							{
								affectedEntities = session.createQuery(delMeleteResourceStr).setString("resourceId", embedResourceId).executeUpdate();
								// logger.debug(affectedEntities+" row was deleted from MELETE_RESOURCE");
							}
						}
						if (deleteFrom == MELETE_RESOURCE_SECTION_RESOURCE)
						{
							if (secRes.getSectionId() != null)
							{
								affectedEntities = session.createQuery(updSectionResourceStr).setInteger("sectionId", secRes.getSectionId())
								.executeUpdate();
							}
							if (secRes.getResource().getResourceId() != null)
							{
								affectedEntities = session.createQuery(delMeleteResourceStr).setString("resourceId",
										secRes.getResource().getResourceId()).executeUpdate();
							}
							// logger.debug(affectedEntities+" row was deleted from MELETE_RESOURCE");
						}
					}

					if ((deleteFrom == SECTION_RESOURCE_ONLY) || (deleteFrom == MELETE_RESOURCE_SECTION_RESOURCE))
					{
						// Delete from SECTION_RESOURCE table
						if (secRes.getSectionId() != null)
						{
							affectedEntities = session.createQuery(delSectionResourceStr).setInteger("sectionId", secRes.getSectionId())
							.executeUpdate();
							// logger.debug(affectedEntities+" row was deleted from SECTION_RESOURCE");
						}
					}
				}
				if (deleteFrom != MELETE_RESOURCE_ONLY)
				{
					Module module = (Module) sec.getModule();
					Integer sectionId = sec.getSectionId();
					logger.debug("checking module element");
					if (module != null)
					{
						// String sectionsSeqXML = module.getSeqXml();
						Query q = session.createQuery(selModuleStr);
						q.setParameter("moduleId", module.getModuleId());
						String sectionsSeqXML = (String) q.uniqueResult();

						logger.debug("module is not null so changing seq" + sectionsSeqXML);
						SubSectionUtilImpl SectionUtil = new SubSectionUtilImpl();
						logger.debug("deleting section id from xmllist" + sectionId.toString());
						if (sectionsSeqXML != null) sectionsSeqXML = SectionUtil.deleteSection(sectionsSeqXML, sectionId.toString());
						// logger.debug("New sectionsseqxml is "+sectionsSeqXML);
						affectedEntities = session.createQuery(updModuleStr).setInteger("moduleId", module.getModuleId()).setString("seqXml",
								sectionsSeqXML).executeUpdate();
						// logger.debug(affectedEntities+" row was updated in MELETE_MODULE");
					}

					// Delete bookmarks for this section
					if (sectionId != null)
					{
						affectedEntities = session.createQuery(delBookmarksStr).setInteger("sectionId", sectionId).executeUpdate();
						logger.debug(affectedEntities + " row was deleted from MELETE_BOOKMARK");
					}

					// Delete tracking for this section
					if (sectionId != null)
					{
						affectedEntities = session.createQuery(delSectionViewStr).setInteger("sectionId", sectionId).executeUpdate();
						logger.debug(affectedEntities + " row was deleted from MELETE_SECTION_TRACK_VIEW");
					}

					// Delete section
					if (sectionId != null)
					{
						if (secRes != null)
						{
							affectedEntities = session.createQuery(delSectionResourceStr).setInteger("sectionId", secRes.getSectionId())
							.executeUpdate();
							// logger.debug(affectedEntities+" row was deleted from SECTION_RESOURCE");
						}
						affectedEntities = session.createQuery(delSectionStr).setInteger("sectionId", sectionId).executeUpdate();
						// logger.debug(affectedEntities+" row was deleted from MELETE_SECTION");
					}

				}

				tx.commit();
				logger.debug("Deleted section from everywhere");

			}
			catch (HibernateException he)
			{
				if (tx != null) tx.rollback();
				logger.error(he.toString());
				throw he;
			}
			catch (Exception e)
			{
				if (tx != null) tx.rollback();
				logger.error(e.toString());
				throw e;
			}
			finally
			{
				hibernateUtil.closeSession();
			}

		}
		catch (Exception ex)
		{
			logger.error(ex.toString());
			ex.printStackTrace();
			throw new MeleteException("delete_module_fail");
		}
	}

	/**
	 * Delete section and if its resource is not shared by other sections then deletes the resource.
	 * 
	 * @param sec
	 *        Section
	 * @param courseId
	 *        The site Id
	 * @param userId
	 *        The user Id
	 * @throws "delete_module_fail" MeleteException
	 */
	public void deleteSection(Section sec, String courseId, String userId) throws MeleteException
	{
		logger.debug("deleteSection begin");

		// find in embedded data
		long starttime = System.currentTimeMillis();
		if (sec.getContentType().equals("notype"))
		{
			deleteFromMeleteTables(sec, userId, NONE_TO_DELETE, null);
		}

		if ((sec.getContentType().equals("typeLink")) || (sec.getContentType().equals("typeUpload")) || (sec.getContentType().equals("typeLTI")))
		{
			boolean resourceInUse = false;
			String resourceId = null;
			if (sec.getSectionResource() != null)
			{
				if (sec.getSectionResource().getResource() != null)
				{
					resourceId = sec.getSectionResource().getResource().getResourceId();
					// Check in SECTION_RESOURCE table
					List srUseList = checkInSectionResources(resourceId);
					if (srUseList != null)
					{
						// This means there is the reference for this section
						// as well as another section
						if (srUseList.size() > 1)
						{
							// Resource being used elsewhere
							resourceInUse = true;
						}
						else
						{

							// Checks all typeEditor sections for embedded media references
							// to this resource
							List resourceUseList = findResourceInUse(resourceId, courseId);
							// This means there is atleast one typeEditor section
							// with media embedded reference to this resource
							if ((resourceUseList != null) && (resourceUseList.size() > 0))
							{
								resourceInUse = true;
							}
						}
						// If resource is being referenced from elsewhere,
						// only delete from MELETE_SECTION and SECTION_RESOURCE tables
						if (resourceInUse == true)
						{
							deleteFromMeleteTables(sec, userId, SECTION_RESOURCE_ONLY, null);
						}
						// If resource is not being referenced from anywhere else,
						// remove from CH, delete from MELETE_SECTION, SECTION_RESOURCE, and MELETE_RESOURCE tables
						if (resourceInUse == false)
						{
							deleteFromMeleteTables(sec, userId, MELETE_RESOURCE_SECTION_RESOURCE, null);
							try
							{
								meleteCHService.removeResource(resourceId);
							}
							catch (Exception e)
							{
								e.printStackTrace();
								logger.error("SectionDB -- deleteSection -- error in delete resource" + e.toString());
								throw new MeleteException("delete_module_fail");
							}

						}
					}
				}
				else
					// resource_id is usually null if the resource has been deleted via Manage
				{
					// Delete from MELETE_SECTION_RESOURCE and MELETE_SECTION
					deleteFromMeleteTables(sec, userId, SECTION_RESOURCE_ONLY, null);
				}
			}// End if section.getSectionResource != null
			else
				// Ideally this condition should never arise, it is a safety feature
			{
				deleteFromMeleteTables(sec, userId, NONE_TO_DELETE, null);
			}
		}// End typeLink and typeUpload

		if (sec.getContentType().equals("typeEditor"))
		{
			List<String> secEmbed = null;
			if ((sec.getSectionResource() == null) || (sec.getSectionResource().getResource() == null))
			{
				deleteFromMeleteTables(sec, userId, NONE_TO_DELETE, null);
				long endtime = System.currentTimeMillis();
				logger.debug("delete section end " + (endtime - starttime));
				return;
			}
			String resourceId = sec.getSectionResource().getResource().getResourceId();
			// Store all embedded media references in a list
			try
			{
				// This method returns references only in meleteDocs
				secEmbed = meleteCHService.findAllEmbeddedImages(resourceId);
			}
			catch (Exception e)
			{
				e.printStackTrace();
				logger.error("SectionDB -- deleteSection - findAllEmbeddedImages failed");
			}
			// Remove main section.html resource from CH
			// Delete references to the main section.html section in MELETE_SECTION, SECTION_RESOURCE
			// and MELETE_RESOURCE
			deleteFromMeleteTables(sec, userId, MELETE_RESOURCE_SECTION_RESOURCE, null);

			try
			{
				meleteCHService.removeResource(resourceId);
			}
			catch (Exception e)
			{
				e.printStackTrace();
				logger.error("SectionDB -- deleteSection -- error in delete resource" + e.toString());
				throw new MeleteException("delete_module_fail");
			}

			// Media reference processing
			if ((secEmbed != null) && (secEmbed.size() > 0))
			{
				List newSecEmbed = new ArrayList();
				for (ListIterator<String> i = secEmbed.listIterator(); i.hasNext();)
				{
					resourceId = (String) i.next();

					// Check to see if this media is also associated with a typeLink
					// or typeUpload section
					List srUseList = checkInSectionResources(resourceId);
					// If it is, continue to the next media reference
					if ((srUseList != null) && (srUseList.size() > 0))
					{
						continue;
					}
					else
					{
						// This is the list of embedded media that needs to be checked
						// in other files
						newSecEmbed.add(resourceId);
					}
				}// End for loop for checkInSectionResources
				if ((newSecEmbed != null) && (newSecEmbed.size() > 0))
				{
					List resourceDeleteList = findResourcesToDelete(newSecEmbed, courseId);
					if ((resourceDeleteList != null) && (resourceDeleteList.size() > 0))
					{
						for (ListIterator<String> k = resourceDeleteList.listIterator(); k.hasNext();)
						{
							resourceId = (String) k.next();
							// Remove from CH and then remove from MELETE_RESOURCE
							deleteFromMeleteTables(sec, userId, MELETE_RESOURCE_ONLY, resourceId);
							try
							{
								meleteCHService.removeResource(resourceId);
							}
							catch (Exception e)
							{
								e.printStackTrace();
								logger.error("SectionDB -- deleteSection -- error in delete resource" + e.toString());
								throw new MeleteException("delete_module_fail");
							}

						}
					}
				}// End if newSecEmbed != null
			}// End if secEmbed != null
		}// end typeEditor
		long endtime = System.currentTimeMillis();

		logger.debug("delete section end " + (endtime - starttime));

	}

	/**
	 * Get the section.
	 * 
	 * @param sectionId
	 *        The section Id
	 * @return
	 * @throws HibernateException
	 */
	public Section getSection(int sectionId) throws HibernateException
	{
		Section sec = new Section();
		try
		{

			Session session = hibernateUtil.currentSession();

			String queryString = "select section from Section as section where section.sectionId = :sectionId";
			Query query = session.createQuery(queryString);
			query.setParameter("sectionId", new Integer(sectionId));
			sec = (Section) query.uniqueResult();
		}
		catch (HibernateException he)
		{
			logger.error(he.toString());
		}
		finally
		{
			try
			{
				hibernateUtil.closeSession();
			}
			catch (HibernateException he)
			{
				logger.error(he.toString());
			}
		}
		return sec;
	}

	/**
	 * Get the section title.
	 * 
	 * @param sectionId
	 *        The section Id
	 * @return
	 * @throws HibernateException
	 */
	public String getSectionTitle(int sectionId) throws HibernateException
	{
		String secTitle = null;
		try
		{

			Session session = hibernateUtil.currentSession();

			String queryString = "select title from Section  where sectionId = :sectionId";
			Query query = session.createQuery(queryString);
			query.setParameter("sectionId", new Integer(sectionId));
			secTitle = (String) query.uniqueResult();
		}
		catch (HibernateException he)
		{
			logger.error(he.toString());
		}
		finally
		{
			try
			{
				hibernateUtil.closeSession();
			}
			catch (HibernateException he)
			{
				logger.error(he.toString());
			}
		}
		return secTitle;
	}

	/**
	 * This method returns all typeEditor sections in a course.
	 * 
	 * @param courseId
	 *        The site Id
	 * @return a list of Section Objects.
	 * 
	 * @throws HibernateException
	 */
	private List<Section> getEditorSections(String courseId) throws HibernateException
	{
		List<Section> secList = new ArrayList<Section>();

		try
		{
			Session session = hibernateUtil.currentSession();

			// String queryString = "from Section section  where section.module.coursemodule.courseId = :courseId  and section.module.coursemodule.archvFlag = 0 and section.module.coursemodule.deleteFlag = 0 and section.contentType='typeEditor'";
			String queryString = "from Section section  where section.module.coursemodule.courseId = :courseId  and section.module.coursemodule.deleteFlag = 0 and section.contentType='typeEditor'";
			Query query = session.createQuery(queryString);
			query.setParameter("courseId", courseId);

			secList = query.list();

		}
		catch (HibernateException he)
		{
			logger.error(he.toString());
		}
		finally
		{
			try
			{
				hibernateUtil.closeSession();
			}
			catch (HibernateException he)
			{
				logger.error(he.toString());
			}
		}
		return secList;

	}

	/**
	 * Insert /update tracking information for all sections of a module.
	 * @param moduleId
	 * @param userId
	 * @throws Exception
	 */
	public void insertSectionTrackforAModule(int moduleId, String userId) throws Exception
	{
		Transaction tx = null;
		try
		{
			Session session = hibernateUtil.currentSession();
			Query query = session.createQuery("from Module mod where mod.moduleId=:moduleId");
			query.setParameter("moduleId", moduleId);
			List secModules = query.list();

			if (secModules != null)
			{
				Module m = (Module) secModules.get(0);
				Map sectionMap = m.getSections();
				if (sectionMap == null || sectionMap.size() == 0) return;
				tx = session.beginTransaction();	
				Iterator it = sectionMap.entrySet().iterator();
				while (it.hasNext())
				{
					Map.Entry pairs = (Map.Entry) it.next();
					Integer secId = (Integer) pairs.getKey();			
					insertSectionTrack(session, secId, userId);
				}
				tx.commit();
			}
		}
		catch (Exception e)
		{
			if (tx != null) tx.rollback();
			logger.error("error in tracking a module:" + e.getMessage());
			e.printStackTrace();
		}
		finally
		{
			try
			{
				hibernateUtil.closeSession();
			}
			catch (HibernateException he)
			{
				logger.error(he.toString());
				throw he;
			}
		}
	}
	
	/**
	 * Insert new section tracking info or update section tracking info if it exists already
	 * Before updating, store current tracking info to return and display
	 * 
	 * @param sectionId
	 *        Section id
	 * @param userId
	 *        User id
	 * @return SectionTrackView object, if found or null
	 * @throws Exception
	 */
	public SectionTrackView insertSectionTrack(int sectionId, String userId) throws Exception
	{
		Transaction tx = null;
		SectionTrackView returnStv = null;
		try
		{
			Session session = hibernateUtil.currentSession();
			tx = session.beginTransaction();
			insertSectionTrack(session, sectionId, userId);
			tx.commit();
		}
		catch (StaleObjectStateException sose)
		{
			if (tx != null) tx.rollback();
			logger.error("stale object exception" + sose.toString());
			throw sose;
		}
		catch (HibernateException he)
		{
			logger.error(he.toString());
			he.printStackTrace();
			throw he;
		}
		catch (Exception e)
		{
			if (tx != null) tx.rollback();
			logger.error(e.toString());
			throw e;
		}
		finally
		{
			try
			{
				hibernateUtil.closeSession();
			}
			catch (HibernateException he)
			{
				logger.error(he.toString());
				throw he;
			}
		}
		return returnStv;
	}

	/**
	 * common method for tracking section
	 */
	private SectionTrackView insertSectionTrack(Session session, int sectionId, String userId) throws Exception
	{
		Query q = session.createQuery("select sv from SectionTrackView as sv where sv.sectionId = :sectionId and sv.userId =:userId");
		q.setParameter("sectionId", sectionId);
		q.setParameter("userId", userId);
		SectionTrackView find_sv = (SectionTrackView) q.uniqueResult();
		SectionTrackView returnStv = null;
		if (find_sv == null)
		{
			// If it doesn't exist, insert it
			find_sv = new SectionTrackView();
			find_sv.setSectionId(sectionId);
			find_sv.setUserId(userId);
			find_sv.setViewDate(new java.util.Date());
			find_sv.setFirstViewDate(new java.util.Date());
			session.save(find_sv);
		}
		else
		{
			// If it exists, retrieve current tracking info, store it in returnStv object
			// Then update tracking info with new date
			
			returnStv = new SectionTrackView();
			returnStv.setSectionId(find_sv.getSectionId());
			returnStv.setUserId(find_sv.getUserId());
			returnStv.setViewDate(find_sv.getViewDate());
			returnStv.setFirstViewDate(find_sv.getFirstViewDate());
			find_sv.setViewDate(new java.util.Date());
			session.update(find_sv);
		}
		return returnStv;
	}
	
	/**
	 * add resource.
	 * 
	 * @param melResource
	 *        MeleteResource
	 * @throws "add_section_fail" MeleteException
	 */
	public void insertResource(MeleteResource melResource) throws Exception
	{
		try
		{
			Session session = hibernateUtil.currentSession();
			Transaction tx = null;
			if (melResource == null || melResource.getResourceId() == null || melResource.getResourceId().length() == 0) return;

			try
			{
				String queryString = "from MeleteResource meleteresource where meleteresource.resourceId=:resourceId";
				Query query = session.createQuery(queryString);
				query.setParameter("resourceId", melResource.getResourceId());
				List result_list = query.list();
				if (result_list != null && result_list.size() != 0) return;
				tx = session.beginTransaction();
				// save resource
				session.save(melResource);
				// complete transaction
				tx.commit();

				if (logger.isDebugEnabled()) logger.debug(" resource is added");

			}
			catch (StaleObjectStateException sose)
			{
				logger.error("stale object exception" + sose.toString());
			}
			catch (HibernateException he)
			{
				if (tx != null) tx.rollback();
				logger.error(he.toString());
				he.printStackTrace();
				throw he;
			}
			finally
			{
				hibernateUtil.closeSession();
			}
		}
		catch (Exception ex)
		{
			// Throw application specific error
			ex.printStackTrace();
			throw new MeleteException("add_section_fail");
		}
	}

	/**
	 * update resource
	 * 
	 * @param melResource
	 *        MeleteResource
	 * 
	 * @throws "add_section_fail" MeleteException
	 */
	public void updateResource(MeleteResource melResource) throws Exception
	{
		try
		{
			// to avoid stale object exception
			MeleteResource mr = findMeleteResource(melResource.getResourceId());
			mr.setAllowCmrcl(melResource.isAllowCmrcl());
			mr.setAllowMod(melResource.getAllowMod());
			mr.setCcLicenseUrl(melResource.getCcLicenseUrl());
			mr.setCopyrightOwner(melResource.getCopyrightOwner());
			mr.setCopyrightYear(melResource.getCopyrightYear());
			mr.setLicenseCode(melResource.getLicenseCode());
			mr.setReqAttr(melResource.isReqAttr());

			Session session = hibernateUtil.currentSession();
			Transaction tx = null;
			try
			{
				tx = session.beginTransaction();
				// save resource
				session.saveOrUpdate(mr);
				// complete transaction
				tx.commit();

				if (logger.isDebugEnabled()) logger.debug(" resource is updated");

			}
			catch (StaleObjectStateException sose)
			{
				logger.error("stale object exception" + sose.toString());
			}
			catch (HibernateException he)
			{
				if (tx != null) tx.rollback();
				logger.error(he.toString());
				he.printStackTrace();
				throw he;
			}
			finally
			{
				hibernateUtil.closeSession();
			}
		}
		catch (Exception ex)
		{
			// Throw application specific error
			ex.printStackTrace();
			throw new MeleteException("add_section_fail");
		}
	}

	/**
	 * Delete a resource.
	 * 
	 * @param melResource
	 *        MeleteResource
	 * 
	 * @throws "add_section_fail" MeleteException
	 */
	public void deleteResource(MeleteResource melResource) throws Exception
	{
		try
		{
			Session session = hibernateUtil.currentSession();
			Transaction tx = null;
			try
			{
				tx = session.beginTransaction();
				// delete resource
				session.delete(melResource);
				// complete transaction
				tx.commit();

				if (logger.isDebugEnabled()) logger.debug(" resource deleted");

			}
			catch (StaleObjectStateException sose)
			{
				logger.error("stale object exception" + sose.toString());
			}
			catch (HibernateException he)
			{
				if (tx != null) tx.rollback();
				logger.error(he.toString());
				he.printStackTrace();
				throw he;
			}
			finally
			{
				hibernateUtil.closeSession();
			}
		}
		catch (Exception ex)
		{
			// Throw application specific error
			ex.printStackTrace();
			throw new MeleteException("add_section_fail");
		}
	}

	/**
	 * add resource associated with section
	 * 
	 * @param section
	 *        The Section
	 * @param melResource
	 *        The MeleteResource
	 * @throws "add_section_fail" MeleteException
	 */
	public void insertMeleteResource(Section section, MeleteResource melResource) throws Exception
	{
		try
		{
			boolean secResExists = false;
			boolean melResExists = false;
			MeleteResource findResource = findMeleteResource(melResource.getResourceId());
			if (findResource != null) melResExists = true;

			Session session = hibernateUtil.currentSession();
			Transaction tx = null;
			try
			{
				SectionResource secResource = (SectionResource) section.getSectionResource();
				if (secResource == null)
				{
					secResource = new SectionResource();
				}
				else
					secResExists = true;
				// set secResource fields
				secResource.setSection(section);
				secResource.setSectionId(section.getSectionId());
				if (melResExists)
					secResource.setResource(findResource);
				else
					secResource.setResource(melResource);

				// update Section
				tx = session.beginTransaction();
				// save resource
				logger.debug("inserting mel resource" + melResource.toString());
				if (!melResExists) session.save(melResource);

				// save sectionResource
				if (secResExists)
					session.update(secResource);
				else
					session.save(secResource);
				section.setSectionResource(secResource);
				session.saveOrUpdate(section);

				// complete transaction
				tx.commit();

				if (logger.isDebugEnabled()) logger.debug("section resource association and resource is added");
			}
			catch (StaleObjectStateException sose)
			{
				logger.error("stale object exception" + sose.toString());
			}
			catch (HibernateException he)
			{
				if (tx != null) tx.rollback();
				logger.error(he.toString());
				// he.printStackTrace();
				throw he;
			}
			finally
			{
				hibernateUtil.closeSession();
			}
		}
		catch (Exception ex)
		{
			// Throw application specific error
			// ex.printStackTrace();
			throw new MeleteException("add_section_fail");
		}
	}

	/**
	 * The Sferyx composed section html for the add section are associated with the section. add the intermediate association to save new section_xxx.html file added by save.jsp
	 * 
	 * @param sectionId
	 *        The section Id
	 * @param melResourceId
	 *        The composed section resource Id
	 * @throws Exception
	 */
	public void insertSectionResource(String sectionId, String melResourceId) throws Exception
	{
		try
		{
			boolean existFlag = true;
			MeleteResource melResource = null;
			if (melResourceId != null) melResource = getMeleteResource(melResourceId);

			// refresh section object
			Section section = getSection(new Integer(sectionId).intValue());
			SectionResource secResource = getSectionResourcebyId(sectionId);

			Session session = hibernateUtil.currentSession();
			Transaction tx = null;
			try
			{

				if (secResource == null)
				{
					secResource = new SectionResource();
					existFlag = false;
				}
				// set secResource fields
				secResource.setSection(section);
				secResource.setSectionId(new Integer(sectionId));
				// update Section
				tx = session.beginTransaction();
				if (melResource != null && melResource.getResourceId() != null)
				{
					secResource.setResource(melResource);
					// save sectionResource
					if (existFlag)
						session.update(secResource);
					else
						session.save(secResource);
					// update Section
					section.setSectionResource(secResource);
				}
				session.saveOrUpdate(section);

				// complete transaction
				tx.commit();

				if (logger.isDebugEnabled()) logger.debug("section resource is added");
			}
			catch (StaleObjectStateException sose)
			{
				logger.error("stale object exception" + sose.toString());
			}
			catch (HibernateException he)
			{
				if (tx != null) tx.rollback();
				logger.error(he.toString());
				he.printStackTrace();
				throw he;
			}
			finally
			{
				hibernateUtil.closeSession();
			}
		}
		catch (Exception ex)
		{
			// Throw application specific error
			ex.printStackTrace();
			throw new MeleteException("add_section_fail");
		}
	}

	/**
	 * add resource associated with section
	 * 
	 * @param section
	 *        Section
	 * @param melResource
	 *        Melete Resource
	 * 
	 * @throws "add_section_fail" MeleteException
	 */
	public void insertSectionResource(Section section, MeleteResource melResource) throws Exception
	{
		try
		{
			boolean existFlag = true;
			if (melResource != null && melResource.getResourceId() != null) melResource = getMeleteResource(melResource.getResourceId());

			// refresh section object
			Section findSection = getSection(section.getSectionId().intValue());
			findSection.setAudioContent(section.isAudioContent());
			findSection.setContentType(section.getContentType());
			findSection.setInstr(section.getInstr());
			findSection.setOpenWindow(section.isOpenWindow());
			findSection.setTextualContent(section.isTextualContent());
			findSection.setTitle(section.getTitle());
			findSection.setVideoContent(section.isVideoContent());
	//		findSection.setModificationDate(section.getModificationDate());

			SectionResource secResource = getSectionResourcebyId(section.getSectionId().toString());

			Session session = hibernateUtil.currentSession();
			Transaction tx = null;
			try
			{

				// SectionResource secResource = (SectionResource)section.getSectionResource();
				if (secResource == null)
				{
					secResource = new SectionResource();
					existFlag = false;
				}
				// set secResource fields
				secResource.setSection(findSection);
				secResource.setSectionId(section.getSectionId());
				// update Section
				tx = session.beginTransaction();
				if (melResource != null && melResource.getResourceId() != null)
				{
					secResource.setResource(melResource);
					// save sectionResource
					if (existFlag)
						session.update(secResource);
					else
						session.save(secResource);
					// update Section
					findSection.setSectionResource(secResource);
				}
				session.saveOrUpdate(findSection);

				// complete transaction
				tx.commit();

				if (logger.isDebugEnabled()) logger.debug("section resource is added");
			}
			catch (StaleObjectStateException sose)
			{
				logger.error("stale object exception" + sose.toString());
			}
			catch (HibernateException he)
			{
				if (tx != null) tx.rollback();
				logger.error(he.toString());
				he.printStackTrace();
				throw he;
			}
			finally
			{
				hibernateUtil.closeSession();
			}
		}
		catch (Exception ex)
		{
			// Throw application specific error
			ex.printStackTrace();
			throw new MeleteException("add_section_fail");
		}
	}

	/**
	 * Get the melete resource based on resource id. If its not there add it mainly for embedded medias.
	 * 
	 * @param selResourceId
	 *        The resource Id
	 * 
	 * @return
	 */
	public MeleteResource getMeleteResource(String selResourceId)
	{
		Session session = null;
		if (selResourceId == null || (selResourceId = selResourceId.trim()).length() == 0) return null;
		try
		{
			session = hibernateUtil.currentSession();
			String queryString = "from MeleteResource meleteresource where meleteresource.resourceId=:resourceId";
			Query query = session.createQuery(queryString);
			query.setParameter("resourceId", selResourceId);
			List result_list = query.list();
			if (result_list != null && result_list.size() != 0)
				return (MeleteResource) result_list.get(0);
			else
			{
				// insert missing ones
				MeleteResource mr = new MeleteResource();
				mr.setResourceId(selResourceId);
				mr.setLicenseCode(0);
				insertResource(mr);
				return mr;
			}
		}
		catch (Exception ex)
		{
			logger.error(ex.toString());
			return null;
		}
		finally
		{
			hibernateUtil.closeSession();
		}
	}

	/**
	 * find melete resource is to just look for it. If it doesn't exist then return null
	 * 
	 * @param selResourceId
	 *        The resource Id
	 * @return
	 */
	private MeleteResource findMeleteResource(String selResourceId)
	{
		Session session = null;
		try
		{
			session = hibernateUtil.currentSession();
			String queryString = "from MeleteResource meleteresource where meleteresource.resourceId=:resourceId";
			Query query = session.createQuery(queryString);
			query.setParameter("resourceId", selResourceId);
			List result_list = query.list();
			if (result_list != null && result_list.size() != 0)
				return (MeleteResource) result_list.get(0);
			else
				return null;
		}
		catch (Exception ex)
		{
			logger.error(ex.toString());
			return null;
		}
		finally
		{
			hibernateUtil.closeSession();
		}
	}

	/**
	 * Gets all melete resources including section_xx.html files of the course.
	 * 
	 * @param courseId
	 *        The site Id
	 * @return
	 */
	public List getAllMeleteResourcesOfCourse(String courseId)
	{
		try
		{
			Session session = hibernateUtil.currentSession();
			String queryString = "select meleteresource.resourceId from MeleteResource meleteresource where meleteresource.resourceId like '%"
				+ courseId + "%'";
			Query query = session.createQuery(queryString);
			List result_list = query.list();
			return result_list;
		}
		catch (Exception ex)
		{
			logger.error(ex.toString());
			return null;
		}
	}

	/**
	 * Gets all melete embedded media and uploaded files in the site .
	 * 
	 * @param courseId
	 *        The course Id
	 * @return
	 */
	public List<String> getUploadMeleteResourcesOfCourse(String courseId)
	{
		try
		{
			Session session = hibernateUtil.currentSession();
			String queryString = "select meleteresource.resourceId from MeleteResource meleteresource where meleteresource.resourceId like '%"
				+ courseId + "/uploads%'";
			Query query = session.createQuery(queryString);
			List<String> result_list = query.list();
			return result_list;
		}
		catch (Exception ex)
		{
			logger.error(ex.toString());
			return null;
		}
	}

	/**
	 * Get the section resource based on resource id.
	 * 
	 * @param secResourceId
	 *        The resource Id
	 * @return
	 */
	public SectionResource getSectionResource(String secResourceId)
	{
		try
		{
			Session session = hibernateUtil.currentSession();
			String queryString = "from SectionResource sectionresource where sectionresource.resourceId=:resourceId";
			Query query = session.createQuery(queryString);
			query.setParameter("resourceId", secResourceId);
			List result_list = query.list();
			if (result_list != null)
				return (SectionResource) result_list.get(0);
			else
				return null;
		}
		catch (Exception ex)
		{
			logger.error(ex.toString());
			return null;
		}

	}

	/**
	 * Get the section resource based on section id.Used mainly for sferyx composed sections.
	 * 
	 * @param sectionId
	 *        The section Id
	 * @return
	 */
	public SectionResource getSectionResourcebyId(String sectionId)
	{
		try
		{
			if (sectionId == null || sectionId.length() == 0) return null;
			Session session = hibernateUtil.currentSession();
			String queryString = "from SectionResource sectionresource where sectionresource.sectionId=:sectionId";
			Query query = session.createQuery(queryString);
			query.setParameter("sectionId", new Integer(sectionId));
			List result_list = query.list();
			if (result_list != null && result_list.size() > 0)
				return (SectionResource) result_list.get(0);
			else
				return null;
		}
		catch (Exception ex)
		{
	//		logger.error(ex.toString());
			ex.printStackTrace();
			return null;
		}
		finally
		{
			hibernateUtil.closeSession();
		}
	}

	/**
	 * Delete section resource. When section type changes to no type or associated with no file, then delete the association.
	 * 
	 * @param sectionId
	 *        The section Id
	 */
	public void deleteSectionResourcebyId(String sectionId)
	{
		Transaction tx = null;
		try
		{
			Session session = hibernateUtil.currentSession();
			tx = session.beginTransaction();
			String queryString = "from SectionResource sectionresource where sectionresource.sectionId=:sectionId";
			Query query = session.createQuery(queryString);
			query.setParameter("sectionId", new Integer(sectionId));
			List result_list = query.list();
			if (result_list != null && result_list.size() > 0)
			{
				SectionResource sr = (SectionResource) result_list.get(0);
				session.delete(sr);
			}
			tx.commit();
		}
		catch (Exception ex)
		{
			tx.rollback();
			logger.error(ex.toString());
			ex.printStackTrace();
		}
		finally
		{
			hibernateUtil.closeSession();
		}
	}

	/**
	 * Note: not in use anymore
	 * 
	 * @param section
	 * @param secResource
	 * @throws Exception
	 */
	public void deassociateSectionResource(Section section, SectionResource secResource) throws Exception
	{
		try
		{
			Session session = hibernateUtil.currentSession();
			Transaction tx = null;
			try
			{

				// delete SectionResource
				tx = session.beginTransaction();
				section.setSectionResource(null);
				session.saveOrUpdate(section);
				// session.delete(secResource);
				session.saveOrUpdate(secResource);
				// complete transaction
				tx.commit();

				if (logger.isDebugEnabled()) logger.debug("section resource is deassociated");
			}
			catch (StaleObjectStateException sose)
			{
				logger.error("stale object exception" + sose.toString());
			}
			catch (HibernateException he)
			{
				if (tx != null) tx.rollback();
				logger.error(he.toString());
				he.printStackTrace();
				throw he;
			}
			finally
			{
				hibernateUtil.closeSession();
			}
		}
		catch (Exception ex)
		{
			// Throw application specific error
			ex.printStackTrace();
			throw new MeleteException("add_section_fail");
		}
	}

	/**
	 * Updates the association.
	 * 
	 * @param section
	 *        Section
	 * @param secResource
	 *        SectionResource
	 * @throws "add_section_fail" MeleteException
	 */
	public void updateSectionResource(Section section, SectionResource secResource) throws Exception
	{
		try
		{
			Session session = hibernateUtil.currentSession();
			Transaction tx = null;
			try
			{
				// set secResource fields
				secResource.setSection(section);
				secResource.setSectionId(section.getSectionId());
				// update Section
				tx = session.beginTransaction();
				// save sectionResource
				session.saveOrUpdate(secResource);
				section.setSectionResource(secResource);
				session.saveOrUpdate(section);

				// complete transaction
				tx.commit();

				if (logger.isDebugEnabled()) logger.debug("section resource is updated");
				// find existing resources with same resource_id and change their properties
				// updateExisitingResource(secResource);
			}
			catch (StaleObjectStateException sose)
			{
				logger.error("stale object exception" + sose.toString());
			}
			catch (HibernateException he)
			{
				if (tx != null) tx.rollback();
				logger.error(he.toString());
				he.printStackTrace();
				throw he;
			}
			finally
			{
				hibernateUtil.closeSession();
			}
		}
		catch (Exception ex)
		{
			// Throw application specific error
			ex.printStackTrace();
			throw new MeleteException("add_section_fail");
		}
	}

	/**
	 * Checks if a resource is shared by other typeUpload/typeLink sections. Its a part of checking in use resource. Before parsing html files for reference check in section resource table.
	 * 
	 * @param selResourceId
	 *        The resource Id
	 * 
	 * @return a list of Object[] with obj[0] as section and obj[1] as sectionresource
	 */
	public List<Object[]> checkInSectionResources(String selResourceId)
	{

		try
		{
			Session session = hibernateUtil.currentSession();

			String queryString = "Select a from Section a, SectionResource b where a.sectionId = b.sectionId "
				+ " AND a.deleteFlag=0 AND b.resource.resourceId=:resourceId";

			Query query = session.createQuery(queryString);
			query.setParameter("resourceId", selResourceId);
			List<Object[]> result_list = query.list();
			if (result_list == null) return null;
			return result_list;
			/*
			 * List foundResources = new ArrayList<String> (0); for(Iterator<Section> itr=result_list.listIterator(); itr.hasNext();) { Section sec = itr.next(); String foundAt = sec.getModule().getTitle() +" >> " + sec.getTitle();
			 * foundResources.add(foundAt); } return foundResources;
			 */
		}
		catch (Exception ex)
		{
			logger.error(ex.toString());
			return null;
		}

	}

	/**
	 * parse all section html files and check if resource is shared by composed sections.
	 * 
	 * @param selResourceId
	 *        The resource id
	 * @param courseId
	 *        The site Id
	 * @return a list section objects
	 */
	public List<SectionObjService> findResourceInUse(String selResourceId, String courseId)
	{
		try
		{
			List<SectionObjService> resourceUseList = null;

			logger.debug("now looking in embed data as section resources don't have it");
			// String lookingFor = "/access/meleteDocs/content" + selResourceId;
			String lookingFor = selResourceId;
			// find in embedded data
			long starttime = System.currentTimeMillis();
			resourceUseList = new ArrayList<SectionObjService>(0);
			List<Section> secList = getEditorSections(courseId);
			if ((secList != null) && (secList.size() > 0))
			{
				for (Iterator<Section> itr = secList.iterator(); itr.hasNext();)
				{
					Section sec = (Section) itr.next();
					List<String> secEmbed = meleteCHService.findAllEmbeddedImages(sec.getSectionResource().getResource().getResourceId());

					if (secEmbed != null && secEmbed.contains(lookingFor))
					{
						// String foundAt = sec.getModule().getTitle() + " >> " + sec.getTitle();
						// resourceUseList.add(foundAt);
						resourceUseList.add((SectionObjService)sec);
						long endtime = System.currentTimeMillis();
						logger.debug("found in " + (endtime - starttime));
						return resourceUseList;
					}
				}
			}

			long endtime = System.currentTimeMillis();

			logger.debug("time to process all files to get all embedded data" + (endtime - starttime));
			return null;

		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			logger.error("SectionServiceImpl --find resource in use failed");
			return null;
		}
	}
	
	/**
	 * import from site failed to translate path for embedded files with (x) names.
	 * Fix paths for such files. 
	 * 
	 * @param data
	 * @param courseId
	 * @return
	 */
	public String fixXrefs(String data, String courseId)
	{
		if (data == null) return data;

		Pattern p = Pattern.compile("(src|href)[\\s]*=[\\s]*\"([^#\"]*)([#\"])", Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE);
		Matcher m = p.matcher(data);
		StringBuffer sb = new StringBuffer();

		// process each "harvested" string (avoiding like strings that are not in src= or href= patterns)
		while (m.find())
		{
			if (m.groupCount() == 3)
			{
				String ref = m.group(2);
				String terminator = m.group(3);

				if (ref != null) ref = ref.trim();
				
				// check if ref belongs to melete or resources tool			
				boolean groupItem = ref.startsWith("/access/content/group/") ? true : false;
				boolean meleteDocsItem = ref.startsWith("/access/meleteDocs/content/private/meleteDocs") ? true : false;
				if (!groupItem && !meleteDocsItem) continue;
						
				// old site Id
				String[] parts = StringUtil.split(ref, "/");				
				String embedOldSiteId = null;
				if (meleteDocsItem && parts.length > 7)	embedOldSiteId = parts[6];					
				else if (groupItem && parts.length > 4) embedOldSiteId = parts[4];
								
				if (embedOldSiteId == null || courseId.equals(embedOldSiteId)) continue;
				
				// melete adds resource with escape names. ckeditor doesn't
				String name = parts[parts.length - 1];
				name = decodeName(name);
				name = Validator.escapeResourceName(name);
				String translatedRef = "/private/meleteDocs/" + courseId + "/uploads/" + name;
				String translated = meleteCHService.getResourceUrl(translatedRef);
				translated = translated.replace(ServerConfigurationService.getServerUrl(), "");
				
				m.appendReplacement(sb, Matcher.quoteReplacement(m.group(1) + "=\"" + translated + terminator));
			}
		}
		m.appendTail(sb);
		return sb.toString();
	}
	
	/**
	 * convert from url form to resource id format.
	 * 
	 * @param id
	 * @return
	 */
	private String decodeName(String id)
	{
		try
		{
			String processed = id.replaceAll("&amp;", "&");
			processed = processed.replaceAll("&lt;", "<");
			processed = processed.replaceAll("&gt;", ">");
			processed = processed.replaceAll("&quot;", "\"");

			// if a browser sees a plus, it sends a plus (URLDecoder will change it to a space)
			processed = processed.replaceAll("\\+", "%2b");

			// and the rest of the works, including %20 and + handling
			String decoded = URLDecoder.decode(processed, "UTF-8");

			return decoded;
		}
		catch (Exception decodeEx)
		{
			logger.debug("get resource fails while decoding " + id);
		}
		return id;
	}
	

	/**
	 * From a list of resources, removes the resources shared by other sections.
	 * 
	 * @param resourceIdList
	 *        The list of resource Ids
	 * 
	 * @param courseId
	 *        The course Id
	 * @return a list of unused resource ids.
	 */
	private List findResourcesToDelete(List resourceIdList, String courseId)
	{
		try
		{
			logger.debug("findResourcesToDelete method beginning");

			// find in embedded data
			long starttime = System.currentTimeMillis();
			// resourceUseList = new ArrayList<String>(0);
			List<Section> secList = getEditorSections(courseId);

			if ((secList != null) && (secList.size() > 0))
			{
				for (Iterator<Section> itr = secList.iterator(); itr.hasNext();)
				{
					Section sec = (Section) itr.next();
					List<String> secEmbed = meleteCHService.findAllEmbeddedImages(sec.getSectionResource().getResource().getResourceId());

					if (secEmbed != null & secEmbed.size() > 0)
					{
						for (Iterator<String> i = secEmbed.iterator(); i.hasNext();)
						{
							String secEmbedStr = (String) i.next();
							if (resourceIdList.size() > 0)
							{
								if (resourceIdList.contains(secEmbedStr))
								{
									resourceIdList.remove(resourceIdList.indexOf(secEmbedStr));
								}
							}
							if (resourceIdList.size() == 0) return null;
						}// End for loop for secEmbed
					}// End if for secEmbed
				}// End for loop for secList
			}// End if for secList

			long endtime = System.currentTimeMillis();

			logger.debug("findResourcesToDelete" + (endtime - starttime));
			return resourceIdList;

		}
		catch (Exception ex)
		{
			logger.error("SectionServiceImpl --find resource in use failed");
			return null;
		}
	}

	/**
	 * Delete resource even if shared by other sections.
	 * 
	 * @param delResourceId
	 *        The resource id
	 * @throws "delete_resource_fail" MeleteException
	 */
	public void deleteResourceInUse(String delResourceId) throws MeleteException
	{
		try
		{
			Session session = hibernateUtil.currentSession();
			Transaction tx = null;
			try
			{
				String queryString = "update SectionResource secResource set secResource.resource.resourceId = null where secResource.resource.resourceId=:resourceId";

				tx = session.beginTransaction();

				int delResources = session.createQuery(queryString).setString("resourceId", delResourceId).executeUpdate();

				logger.debug(delResources + "section resources are set to null");

				MeleteResource melRes = (MeleteResource) session.get(org.etudes.component.app.melete.MeleteResource.class, delResourceId);
				if (melRes != null) session.delete(melRes);

				// complete transaction
				tx.commit();
			}
			catch (StaleObjectStateException sose)
			{
				logger.error("stale object exception" + sose.toString());
			}
			catch (HibernateException he)
			{
				if (tx != null) tx.rollback();
				logger.error(he.toString());
				he.printStackTrace();
				throw he;
			}
			finally
			{
				hibernateUtil.closeSession();
			}
		}
		catch (Exception ex)
		{
			logger.error(ex.toString());
			ex.printStackTrace();
			throw new MeleteException("delete_resource_fail");
		}

	}

	/**
	 * Clean up sections marked for delete. Part of melete admin tool.
	 * 
	 * @return the count of deleted sections
	 * 
	 * @throws "cleanup_module_fail" MeleteException
	 */
	public int cleanUpDeletedSections(String userId) throws Exception
	{
		if (!meleteSecurityService.isSuperUser(userId)) throw new MeleteException("admin_allow_cleanup");

		int delCount = 0;
		long totalStart = System.currentTimeMillis();
		try
		{
			Session session = hibernateUtil.currentSession();
			Transaction tx = null;
			try
			{
				// get deleted modules group by course id
				String queryString = "select cmod.courseId,sec.sectionId from CourseModule cmod,Section sec where cmod.moduleId=sec.moduleId and sec.deleteFlag=1 order by sec.moduleId";
				Query query = session.createQuery(queryString);
				List res = query.list();

				Map deletedSections = new HashMap<String, ArrayList<Section>>();

				for (Iterator itr = res.listIterator(); itr.hasNext();)
				{
					Object pair[] = (Object[]) itr.next();
					String courseId = (String) pair[0];
					Integer sectionId = (Integer) pair[1];

					String keyStr = courseId;
					if (deletedSections.containsKey(keyStr))
					{
						ArrayList delsections = (ArrayList) deletedSections.get(keyStr);
						delsections.add(sectionId);
						deletedSections.put(keyStr, delsections);
					}
					else
					{
						ArrayList delSection = new ArrayList();
						delSection.add(sectionId);
						deletedSections.put(keyStr, delSection);
					}
				}
				logger.info("Process deleted sections from active modules of " + deletedSections.size() + " sites");
				delCount = deletedSections.size();
				int i = 0;
				// for each course id
				Set alldelSecCourses = deletedSections.keySet();
				for (Iterator iter = alldelSecCourses.iterator(); iter.hasNext();)
				{
					// for that course id get all melete resources from melete_resource
					long starttime = System.currentTimeMillis();
					String toDelSecCourseId = (String) iter.next();
					logger.info("processing " + i++ + " course with id " + toDelSecCourseId);
					List<? extends ModuleObjService> activenArchModules = moduleDB.getActivenArchiveModules(toDelSecCourseId);
					// parse and list all names which are in use
					List<String> activeResources = moduleDB.getActiveResourcesFromList(activenArchModules);
					List<String> allCourseResources = moduleDB.getAllMeleteResourcesOfCourse(toDelSecCourseId);
					int allresourcesz = 0;
					int delresourcesz = 0;
					if (allCourseResources != null) allresourcesz = allCourseResources.size();

					// compare the lists and not in use resources are
					if (!session.isOpen()) session = hibernateUtil.currentSession();
					tx = session.beginTransaction();
					if (allCourseResources != null && activeResources != null)
					{
						// logger.debug("active list and all" + activeResources.size() + " ; " + allCourseResources.size());
						allCourseResources.removeAll(activeResources);
					}
					// delete sections marked for delete
					List<Integer> delSections = (ArrayList) deletedSections.get(toDelSecCourseId);
					String allSecIds = getAllDeleteSectionIds(delSections);
					// logger.debug("all SecIds in sectionscleanup" + allSecIds);
					String selectResourceStr = "select sr.resource.resourceId from SectionResource sr where sr.section.contentType ='typeEditor' and sr.section in "
						+ allSecIds;
					String updSectionResourceStr = "update SectionResource sr set sr.resource = null where sr.section in " + allSecIds;
					String delSectionResourceStr = "delete SectionResource sr where sr.section in " + allSecIds;
					String delSectionStr = "delete Section s where s.sectionId in " + allSecIds;

					List<String> delSectionResources = session.createQuery(selectResourceStr).list();
					int deletedEntities = session.createQuery(updSectionResourceStr).executeUpdate();
					deletedEntities = session.createQuery(delSectionResourceStr).executeUpdate();
					deletedEntities = session.createQuery(delSectionStr).executeUpdate();

					if (delSectionResources != null && delSectionResources.size() > 0)
					{
						deleteResources(session, delSectionResources, true);
					}

					// delete melete resource and from content resource
					if ((allCourseResources != null) && (allCourseResources.size() > 0))
					{
						deleteResources(session, allCourseResources, true);
					}

					tx.commit();
					long endtime = System.currentTimeMillis();
					logger.info("to cleanup course with " + allresourcesz + " resources and del sections " + delSections.size()
							+ " and del resources" + delresourcesz + ", it took " + (endtime - starttime) + "ms");
				} // for end
				long totalend = System.currentTimeMillis();
				logger.info("to cleanup " + deletedSections.size() + "courses it took " + (totalend - totalStart) + "ms");
			}
			catch (HibernateException he)
			{
				if (tx != null) tx.rollback();
				logger.error(he.toString());
				throw he;
			}
			finally
			{
				hibernateUtil.closeSession();
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new MeleteException("cleanup_module_fail");
		}
		return delCount;
	}

	/**
	 * Get all section ids in a comma separated string format.
	 * 
	 * @param delSections
	 *        List of Section ids
	 * @return
	 */
	private String getAllDeleteSectionIds(List<Integer> delSections)
	{
		StringBuffer allIds = new StringBuffer("( ");
		String a = null;
		for (Integer s : delSections)
		{
			allIds.append(Integer.toString(s) + ",");
		}
		if (allIds.lastIndexOf(",") != -1) a = allIds.substring(0, allIds.lastIndexOf(",")) + " )";
		return a;
	}

	/**
	 * Collect resource ids for deleted sections
	 * 
	 * @param delSections
	 *        List of section objects
	 * @return a list of resource ids
	 */
	private List<String> getAllDeleteSectionMeleteResourceIds(List<Section> delSections)
	{
		List<String> a = new ArrayList<String>(0);
		for (Section s : delSections)
		{
			if (s.getSectionResource() != null && s.getSectionResource().getResource() != null)
				a.add(s.getSectionResource().getResource().getResourceId());
		}
		return a;
	}

	/**
	 * Deletes a list of melete resource.
	 * 
	 * @param session
	 *        Hibernate session
	 * @param delResources
	 *        List of resource ids
	 * @param removeResourceFlag
	 *        Deletes resource from content resource if set
	 */
	public void deleteResources(Session session, List<String> delResources, boolean removeResourceFlag)
	{
		StringBuffer delResourceIds = new StringBuffer("(");
		// delete melete resource
		for (String delRes : delResources)
		{
			if (delRes == null) continue;
			delResourceIds.append("'" + meleteUtil.escapeQuoted(delRes) + "',");
			if (removeResourceFlag == true)
			{
				try
				{
					meleteCHService.removeResource(delRes);
				}
				catch (Exception e)
				{
					logger.warn("unable to delete resource.its still asociated with section." + delRes);
				}
			}
		}

		if (delResourceIds.lastIndexOf(",") != -1)
			delResourceIds = new StringBuffer(delResourceIds.substring(0, delResourceIds.lastIndexOf(",")) + " )");

		String delMeleteResourceStr = "delete MeleteResource mr where mr.resourceId in " + delResourceIds;
		int deletedEntities = session.createQuery(delMeleteResourceStr).executeUpdate();
	}

	/**
	 * Sets all section licenses to the user preferred one.
	 * 
	 * @param courseId
	 *        The course Id
	 * @param mup
	 *        MeleteUserPreference object
	 * @return the count of licenses changed
	 * 
	 * @throws "all_license_change_fail" MeleteException
	 * 
	 */
	public int changeLicenseForAll(String courseId, MeleteUserPreference mup) throws Exception
	{
		try
		{
			Session session = hibernateUtil.currentSession();
			Transaction tx = null;
			try
			{
				// get all active section melete resource objects for this site
				String queryString = "Select sr.resource.resourceId from SectionResource sr " + "join sr.section s " + "join s.module m "
				+ "join m.coursemodule cmod "
				+ "where cmod.courseId=:courseId and cmod.archvFlag = 0 and cmod.deleteFlag = 0 and sr.resource != null";
				Query query = session.createQuery(queryString);
				query.setParameter("courseId", courseId);
				List result_list = query.list();

				// to bulk change, create a string with all resource ids
				if (result_list != null && result_list.size() != 0)
				{
					String res_ids = new String("(");
					for (int i = 0; i < result_list.size(); i++)
					{
						res_ids = res_ids.concat("\'" + (String) result_list.get(i) + "\',");
					}

					if (res_ids.lastIndexOf(",") != -1) res_ids = res_ids.substring(0, res_ids.lastIndexOf(","));
					res_ids = res_ids.concat(")");

					// bulk update
					tx = session.beginTransaction();
					String updMeleteResourceStr = "update MeleteResource mr1 set mr1.licenseCode=:lcode, mr1.ccLicenseUrl=:lurl,"
						+ " mr1.reqAttr=:reqAttr, mr1.allowCmrcl=:allowCmrcl, mr1.allowMod=:allowMod, mr1.copyrightOwner=:copyrightOwner, "
						+ "mr1.copyrightYear=:copyrightYear where mr1.resourceId in " + res_ids;
					int updatedEntities = 0;
					Query query1 = session.createQuery(updMeleteResourceStr);
					query1.setParameter("lcode", mup.getLicenseCode());
					query1.setParameter("lurl", mup.getCcLicenseUrl());
					query1.setParameter("reqAttr", mup.isReqAttr());
					query1.setParameter("allowCmrcl", mup.isAllowCmrcl());
					query1.setParameter("allowMod", mup.getAllowMod());
					query1.setParameter("copyrightOwner", mup.getCopyrightOwner());
					query1.setParameter("copyrightYear", mup.getCopyrightYear());

					updatedEntities = query1.executeUpdate();
					tx.commit();
					logger.debug("license updated for " + updatedEntities + "resources");
					return updatedEntities;
				}
				else
					return 0;
			}
			catch (HibernateException he)
			{
				if (tx != null) tx.rollback();
				logger.error(he.toString());
				throw he;
			}
			finally
			{
				hibernateUtil.closeSession();
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new MeleteException("all_license_change_fail");
		}
	}

	/**
	 * Get all users who have viewed a section.
	 * 
	 * @param sectionId
	 *        The section id
	 * @return a Map<userId, ViewDate> for a section
	 */
	public Map<String, Date> getSectionUsersFirstViewDate(int sectionId)
	{
		Map<String, Date> all = new HashMap<String, Date>();
		Session session = hibernateUtil.currentSession();
		Query q = session.getNamedQuery("trackSectionItem");
		q.setParameter("sectionId", sectionId, StandardBasicTypes.INTEGER);

		List<SectionTrackView> sectionUsers = q.list();
		if (sectionUsers == null || sectionUsers.size() <= 0) return all;
		for (SectionTrackView s : sectionUsers)
		{
			if (s.getFirstViewDate() != null)
			{
				all.put(s.getUserId(), s.getFirstViewDate());
			}
		}
		hibernateUtil.closeSession();
		return all;
	}
	
	/**
	 * Get all users who have viewed a section.
	 * 
	 * @param sectionId
	 *        The section id
	 * @return a Map<userId, ViewDate> for a section
	 */
	public Map<String, Date> getSectionUsersViewDate(int sectionId)
	{
		Map<String, Date> all = new HashMap<String, Date>();
		Session session = hibernateUtil.currentSession();
		Query q = session.getNamedQuery("trackSectionItem");
		q.setParameter("sectionId", sectionId, StandardBasicTypes.INTEGER);

		List<SectionTrackView> sectionUsers = q.list();
		if (sectionUsers == null || sectionUsers.size() <= 0) return all;
		for (SectionTrackView s : sectionUsers)
		{
			if (s.getViewDate() != null)
			{
				all.put(s.getUserId(), s.getViewDate());
			}
		}
		hibernateUtil.closeSession();
		return all;
	}

	/**
	 * Count of users view the section
	 * 
	 * @param sectionId
	 *        The section id
	 * @return the count
	 */
	public Integer getSectionViewersCount(int sectionId)
	{
		Integer count = 0;
		try
		{
			Session session = hibernateUtil.currentSession();
			Query q = session.getNamedQuery("trackSectionCount");
			q.setParameter("sectionId", sectionId, StandardBasicTypes.INTEGER);
			List results = q.list();
			if (results != null) count = (Integer) results.get(0);

		}
		catch (Exception e)
		{
			logger.debug("exception at getting track count " + e.getMessage());
			count = 0;
		}
		hibernateUtil.closeSession();
		return count;
	}

	/**
	 * Get the total number of active sections of a site.
	 * 
	 * @param course_id
	 *        The site Id
	 * @return the count
	 */
	public int getAllActiveSectionsCount(String courseId)
	{
		int count = 0;
		if (courseId == null) return count;

		Session session = hibernateUtil.currentSession();
		try
		{
			String queryString = "select count(sec.sectionId) from CourseModule cmod,Section sec where cmod.moduleId=sec.moduleId and cmod.courseId=:courseId and cmod.archvFlag=0";
			Query query = session.createQuery(queryString);
			query.setParameter("courseId", courseId);
			List res = query.list();
			if (res != null) count = ((Integer) res.get(0)).intValue();
		}
		catch (Exception e)
		{
			logger.debug("exception at getting all sections count " + e.getMessage());
			count = 0;
		}
		hibernateUtil.closeSession();
		return count;
	}

	/**
	 *all viewed sections of a course
	 * 
	 * @param courseId
	 *        The site Id
	 * @return a Map<section id, List of viewed user ids>
	 * 
	 */
	public Map<Integer, List<String>> getAllViewedSectionsCount(String courseId)
	{
		if (courseId == null) return null;
		Map<Integer, List<String>> allViewedSections = new HashMap<Integer, List<String>>();
		Session session = hibernateUtil.currentSession();
		try
		{
			String queryString = "select secTrack from CourseModule cmod,Section sec, SectionTrackView secTrack where cmod.moduleId=sec.moduleId and sec.sectionId = secTrack.sectionId and cmod.archvFlag=0 and cmod.courseId =:courseId order by secTrack.sectionId";
			Query query = session.createQuery(queryString);
			query.setParameter("courseId", courseId);
			List<SectionTrackView> res = query.list();
			if (res != null)
			{
				for (SectionTrackView track : res)
				{
					if (allViewedSections.containsKey(track.getSectionId()))
					{
						List<String> users = allViewedSections.get(track.getSectionId());
						users.add(track.getUserId());
						allViewedSections.put(track.getSectionId(), users);
					}
					else
					{
						List<String> users = new ArrayList<String>();
						users.add(track.getUserId());
						allViewedSections.put(track.getSectionId(), users);
					}
				} // for end
			}
		}
		catch (Exception e)
		{
			logger.debug("exception at getting viewed sections count " + e.getMessage());
		}
		hibernateUtil.closeSession();
		return allViewedSections;
	}

	/**
	 * number of sections viewed by users of a site
	 * 
	 * @param courseId
	 *        The site Id
	 * @return a map keyed by user ids and the value is no of viewed sections by the user
	 */
	public Map<String, Integer> getNumberOfSectionViewedByUserId(String courseId)
	{
		if (courseId == null) return null;
		Map<String, Integer> allViewedSections = new HashMap<String, Integer>();
		Session session = hibernateUtil.currentSession();
		try
		{
			String queryString = "select secTrack from CourseModule cmod,Section sec, SectionTrackView secTrack where cmod.moduleId=sec.moduleId and sec.sectionId = secTrack.sectionId and cmod.archvFlag=0 and cmod.courseId =:courseId order by secTrack.userId";
			Query query = session.createQuery(queryString);
			query.setParameter("courseId", courseId);
			List<SectionTrackView> res = query.list();
			if (res != null)
			{
				for (SectionTrackView track : res)
				{
					if (allViewedSections.containsKey(track.getUserId()))
					{
						Integer count = allViewedSections.get(track.getUserId());
						count++;
						allViewedSections.put(track.getUserId(), count);
					}
					else
					{
						allViewedSections.put(track.getUserId(), 1);
					}
				} // for end
			}
		}
		catch (Exception e)
		{
			logger.debug("exception at getting viewed sections count " + e.getMessage());
		}
		hibernateUtil.closeSession();
		return allViewedSections;
	}

	/**
	 * @return Returns the hibernateUtil.
	 */
	public HibernateUtil getHibernateUtil()
	{
		return hibernateUtil;
	}

	/**
	 * @param hibernateUtil
	 *        The hibernateUtil to set.
	 */
	public void setHibernateUtil(HibernateUtil hibernateUtil)
	{
		this.hibernateUtil = hibernateUtil;
	}

	public void setMeleteCHService(MeleteCHService meleteCHService)
	{
		this.meleteCHService = meleteCHService;
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
	 * @param moduleDB
	 *        the moduleDB to set
	 */
	public void setModuleDB(ModuleDB moduleDB)
	{
		this.moduleDB = moduleDB;
	}
	
	public String getSectionContentFile(String resourceId)
	{
		
		String SectionContentFile = null;

		Session session = hibernateUtil.currentSession();
		try
		{
			String queryString = "select filePath from ContentResource where resourceId = :resourceId";
			Query query = session.createQuery(queryString);
			query.setParameter("resourceId", resourceId);
			SectionContentFile = (String) query.uniqueResult();
		}
		catch (Exception e)
		{
		
		}
		hibernateUtil.closeSession();
		return SectionContentFile;
		
	}

}
