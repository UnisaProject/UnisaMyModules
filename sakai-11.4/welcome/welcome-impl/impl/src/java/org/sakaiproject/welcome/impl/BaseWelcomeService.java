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

package org.sakaiproject.welcome.impl;

import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.Vector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sakaiproject.component.cover.ComponentManager;
import org.sakaiproject.entity.api.Entity;
import org.sakaiproject.entity.api.EntityProducer;
import org.sakaiproject.entity.api.EntityTransferrer;
import org.sakaiproject.entity.api.HttpAccess;
import org.sakaiproject.entity.api.Reference;
import org.sakaiproject.entity.api.ResourceProperties;
import org.sakaiproject.event.api.EventTrackingService;
import org.sakaiproject.event.api.UsageSession;
import org.sakaiproject.event.api.UsageSessionService;
import org.sakaiproject.welcome.api.WelcomeService;
import org.sakaiproject.tool.api.Session; 
import org.sakaiproject.tool.api.SessionManager;
import org.sakaiproject.user.api.UserDirectoryService;
import org.sakaiproject.entity.api.ResourceProperties;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * <p>
 * Implements the TemplateService, all but a Storage model.
 * </p>
 */
public abstract class BaseWelcomeService implements WelcomeService ,EntityProducer, EntityTransferrer
{
	/** Our log (commons). */
	private static Logger M_log = LoggerFactory.getLogger(BaseWelcomeService.class);
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
	 * @param service
	 *        The SessionManager.
	 */
	public void setSessionManager(SessionManager service)
	{
		m_sessionManager = service;
	}

	/** Dependency: UsageSessionService */
	protected UsageSessionService m_usageSessionService = null;

	/**
	 * Dependency: UsageSessionService.
	 * 
	 * @param service
	 *        The UsageSessionService.
	 */
	public void setUsageSessionService(UsageSessionService service)
	{
		m_usageSessionService = service;
	}

	/** Dependency: UserDirectoryService */
	protected UserDirectoryService m_userDirectoryService = null;

	/**
	 * Dependency: UserDirectoryService.
	 * 
	 * @param service
	 *        The UserDirectoryService.
	 */
	public void setUserDirectoryService(UserDirectoryService service)
	{	
		m_userDirectoryService = service;
	}

	/** Dependency: EventTrackingService */
	protected EventTrackingService m_eventTrackingService = null;

	/**
	 * Dependency: EventTrackingService.
	 * 
	 * @param service
	 *        The EventTrackingService.
	 */
	public void setEventTrackingService(EventTrackingService service)
	{
		m_eventTrackingService = service;
	}


	public String getCurrentUserName() {
		
		return m_userDirectoryService.getCurrentUser().getDisplayName();

	}

	


	public String getWelcomeContent(String siteId)
	{
		
		String content = m_storage.getWelcomeContent(siteId);

		return content;

	} 

	public String saveWelcomeContent(String siteId, String content)
	{
		
		String output = m_storage.saveWelcomeContent(siteId,content);

		return output;

	} 
	

	public void transferCopyEntities(String fromContext, String toContext,
			List ids) {
		transferCopyEntities(fromContext, toContext, ids, false);
	}
	
	public void transferCopyEntities(String fromContext, String toContext,
			List ids, boolean cleanup) {
		try {
			copy(fromContext, toContext);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	private void copy(String fromContext, String toContext) throws Exception {
		String fromContent = m_storage.getWelcomeContent(fromContext);
		m_storage.saveWelcomeContent(toContext, fromContent);
	}
	
	
	public String  revertWelcomeContent(String siteId)
	{
		
		String output = m_storage.revertWelcomeContent(siteId);

		return output;

	} 
	
	public String[] myToolIds() {
		String[] toolId = {"unisa.welcome"};
		return toolId;
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
			return "Welcome Message Entity";
		}

		public ResourceProperties getEntityResourceProperties(Reference ref) {
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
	public void init()
	{
		try
		{
        
			m_storage = newStorage();

			M_log.info("init()");
			
		


		}
		catch (Exception t)
		{
			M_log.warn("init(): ", t);
		}
	}

	/**
	 * Returns to uninitialized state.
	 */
	public void destroy()
	{
	
		M_log.info("destroy()");
	}

	
	
	/**********************************************************************************************************************************************************************************************************************************************************
	 * Storage
	 *********************************************************************************************************************************************************************************************************************************************************/

	protected interface Storage
	{
		String getWelcomeContent(String userId);
		String saveWelcomeContent(String siteId, String content);
		String revertWelcomeContent(String siteId);
	}
	

}
