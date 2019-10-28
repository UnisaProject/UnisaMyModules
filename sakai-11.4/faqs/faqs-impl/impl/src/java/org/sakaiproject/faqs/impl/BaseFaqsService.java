/**********************************************************************************
 * $URL$
 * $Id$
 ***********************************************************************************
 *
 * Copyright (c) 2003, 2004, 2005, 2006, 2007, 2008, 2009 The Sakai Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.opensource.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 **********************************************************************************/

package org.sakaiproject.faqs.impl;

import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Vector;
import java.util.Stack;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sakaiproject.component.cover.ComponentManager;
import org.sakaiproject.event.api.EventTrackingService;
import org.sakaiproject.event.api.UsageSession;
import org.sakaiproject.event.api.UsageSessionService;
import org.sakaiproject.faqs.api.FaqsService;
import org.sakaiproject.faqs.dataModel.FaqCategory;
import org.sakaiproject.faqs.dataModel.FaqContent;
import org.sakaiproject.tool.api.Session;
import org.sakaiproject.tool.api.SessionManager;
import org.sakaiproject.entity.api.Entity;
import org.sakaiproject.user.api.UserDirectoryService;
import org.w3c.dom.Element;
import org.sakaiproject.entity.api.ResourceProperties;
import org.w3c.dom.Document;
import org.sakaiproject.entity.api.Reference;
import org.sakaiproject.entity.api.HttpAccess;

/**
 * <p>
 * Implements the FaqsService, all but a Storage model.
 * </p>
 */
public abstract class BaseFaqsService implements FaqsService {
	/** Our log (commons). */
	private static Logger M_log = LoggerFactory.getLogger(BaseFaqsService.class);
	protected Storage m_storage = null;

	protected abstract Storage newStorage();

	/**********************************************************************************************************************************************************************************************************************************************************
	 * Constructors, Dependencies and their setter methods
	 *********************************************************************************************************************************************************************************************************************************************************/

	/** Dependency: SessionManager */
	protected SessionManager m_sessionManager = null;

	/**
	 * Dependency: SessionManager.
	 * 
	 * @param service The SessionManager.
	 */
	public void setSessionManager(SessionManager service) {
		m_sessionManager = service;
	}

	/** Dependency: UsageSessionService */
	protected UsageSessionService m_usageSessionService = null;

	/**
	 * Dependency: UsageSessionService.
	 * 
	 * @param service The UsageSessionService.
	 */
	public void setUsageSessionService(UsageSessionService service) {
		m_usageSessionService = service;
	}

	/** Dependency: UserDirectoryService */
	protected UserDirectoryService m_userDirectoryService = null;

	/**
	 * Dependency: UserDirectoryService.
	 * 
	 * @param service The UserDirectoryService.
	 */
	public void setUserDirectoryService(UserDirectoryService service) {
		m_userDirectoryService = service;
	}

	/** Dependency: EventTrackingService */
	protected EventTrackingService m_eventTrackingService = null;

	/**
	 * Dependency: EventTrackingService.
	 * 
	 * @param service The EventTrackingService.
	 */
	public void setEventTrackingService(EventTrackingService service) {
		m_eventTrackingService = service;
	}


	public List getFaqCategories(String siteId)
	{
		
		List categories = m_storage.getFaqCategories(siteId);

		return categories;

	} 
	
	public List getFaqCategory(int categoryId)
	{
		
		List category = m_storage.getFaqCategory(categoryId);

		return category;

	} 
	

	public List getFaqContents(Integer categoryId)
	{
		
		List content = m_storage.getFaqContents(categoryId);

		return content;

	} 
	
	public  List getFaqContent(int contentId) {
		
		List faqContent = m_storage.getFaqContent(contentId);

		return faqContent;
	}
   public  List getFaqContentWithCategory(int contentId) {
		
		List faqContent = m_storage.getFaqContentWithCategory(contentId);

		return faqContent;
	}

	
	public void insertFaqCategory(String siteId, String categoryDesc)
	{
		 m_storage.insertFaqCategory(siteId,categoryDesc);
	}
	
	public  void insertFaqContent(String question,String answer, String categoryId) {
		
		m_storage.insertFaqContent(question, answer,categoryId);
	}
	public  void updateFaqCategory(String categoryDesc, int categoryId) {
		
		m_storage.updateFaqCategory(categoryDesc, categoryId);
	}

	public  void updateFaqContent(FaqContent faqContent) {
		m_storage.updateFaqContent(faqContent);
	}
	public  void deleteFaqContent(Integer contentId) {
		m_storage.deleteFaqContent(contentId);
	}
	public  void deleteFaqCategory(Integer categoryId){
		m_storage.deleteFaqCategory(categoryId);
	}
 
	public void deleteAllFaqsForSite(String siteId) {
		List fromCategories = getFaqCategories(siteId);
 
		Iterator i = fromCategories.iterator();
		while (i.hasNext()) {
			FaqCategory faqCategory = (FaqCategory) i.next();
			deleteFaqCategory(faqCategory.getCategoryId());
		}
	}
	
 
	public void copy(String fromSiteId, String toSiteId) {
		List fromCategories = getFaqCategories(fromSiteId);

		Iterator i = fromCategories.iterator();
		while(i.hasNext()){
			FaqCategory fromCategory = (FaqCategory) i.next();
			List contents = getFaqContents(fromCategory.getCategoryId());

			fromCategory.setSiteId(toSiteId);
			fromCategory.setCategoryId(null);
			//fromCategory.setModifiedOn(new Timestamp(new Date().getTime()));
			insertFaqCategory(toSiteId,fromCategory.getDescription());

			Iterator cont = contents.iterator();
			while(cont.hasNext()){
				FaqContent fc = (FaqContent) cont.next();
				fc.setCategoryId(fromCategory.getCategoryId());
				fc.setContentId(null);
				//fc.setModifiedOn(new Timestamp(new Date().getTime()));
				insertFaqContent(fc.getQuestion(),fc.getAnswer(),fc.getCategoryId().toString());
 
			}
		}
	}
	
	
	public String[] myToolIds() {
		String[] toolId = {"unisa.faqs"};
		return toolId;
	}

	public void transferCopyEntities(String fromContext, String toContext,
			List ids) {
		transferCopyEntities(fromContext, toContext, ids, false);
	}
	
	public void transferCopyEntities(String fromContext, String toContext,
			List ids, boolean cleanup) {
		
		if (cleanup) {
			deleteAllFaqsForSite(toContext);
		}
		copy(fromContext, toContext);
		
	}

	
	// The following is just dummy code to implement the EntityProducer interface
	// This is needed so that the Site Tool can do Import/Export for this tool
	public String getLabel() {
		return "EmptyLabel";
	}

	public boolean willArchiveMerge() {
		return false;
	}

	public String archive(String siteId, Document doc, Stack stack,
			String archivePath, List attachments) {
		return "Emptyarchive";
	}

	public String merge(String siteId, Element root, String archivePath,
			String fromSiteId, Map attachmentNames, Map userIdTrans,
			Set userListAllowImport) {
		return "EmptyMerge";
	}

	public boolean parseEntityReference(String reference, Reference ref) {
		return false;
	}

	public String getEntityDescription(Reference ref) {
		return "EmptyEntityDescription";
	}

	public ResourceProperties getEntityResourceProperties(Reference ref) {
		//return new BaseResourceProperties();
		return null;
	}

	public Entity getEntity(Reference ref) {
		return new Entity() {
			
			public Element toXml(Document doc, Stack stack) {
				return null;
			}
			
			public String getUrl(String rootProperty) {
				return null;
			}
			
			public String getUrl() {
				return null;
			}
			
			public String getReference(String rootProperty) {
				return null;
			}
			
			public String getReference() {
				return null;
			}
			
			public ResourceProperties getProperties() {
				return null;
			}
			
			public String getId() {
				return null;
			}
		};
	}

	public String getEntityUrl(Reference ref) {
		return "EmptyEntityUrl";
	}

	public Collection getEntityAuthzGroups(Reference ref, String userId) {
		return null;
	}

	public HttpAccess getHttpAccess() {
		return null;
	}
	/**********************************************************************************************************************************************************************************************************************************************************
	 * Init and Destroy
	 *********************************************************************************************************************************************************************************************************************************************************/

	/**
	 * Final initialization, once all dependencies are set.
	 */
	public void init() {
		try {

			m_storage = newStorage();

			M_log.info("init()");

		} catch (Exception t) {
			M_log.warn("init(): ", t);
		}
	}

	/**
	 * Returns to uninitialized state.
	 */
	public void destroy() {

		M_log.info("destroy()");
	}

	/**********************************************************************************************************************************************************************************************************************************************************
	 * Storage
	 *********************************************************************************************************************************************************************************************************************************************************/

	protected interface Storage {
		
		List getFaqCategories(String siteId);
		List getFaqContents(Integer categoryId);
		void insertFaqCategory(String siteId, String categoryDesc);
		List getFaqCategory(int categoryId);
		List getFaqContent(int contentId);
		List getFaqContentWithCategory(int contentId);
	    void updateFaqCategory(String categoryDesc, int categoryId);
	    void updateFaqContent(FaqContent faqContent);
	    void insertFaqContent(String question,String answer, String categoryId);
	    void deleteFaqContent(Integer contentId);
	    void deleteFaqCategory(Integer categoryId);
	}

}
