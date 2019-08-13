/**********************************************************************************
 * $URL$
 * $Id$
 ***********************************************************************************
 *
 * Copyright (c) 2003, 2005, 2006, 2007, 2008 The Sakai Foundation
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

package org.sakaiproject.faqs.cover;

import java.util.List;

import org.sakaiproject.component.cover.ComponentManager;
import org.sakaiproject.event.api.UsageSession;
import org.sakaiproject.faqs.dataModel.FaqCategory;
import org.sakaiproject.faqs.dataModel.FaqContent;
import org.sakaiproject.user.api.User;

/**
 * <p>
 * FaqsService is a static Cover for the
 * {@link org.sakaiproject.faqs.api.FaqsService FaqsService}; see that interface
 * for usage details.
 * </p>
 */
public class FaqsService {

	private static org.sakaiproject.faqs.api.FaqsService m_instance = null;

	/**
	 * Access the component instance: special cover only method.
	 * 
	 * @return the component instance.
	 */
	public static org.sakaiproject.faqs.api.FaqsService getInstance() {
		if (ComponentManager.CACHE_COMPONENTS) {
			if (m_instance == null)
				m_instance = (org.sakaiproject.faqs.api.FaqsService) ComponentManager
						.get(org.sakaiproject.faqs.api.FaqsService.class);
			return m_instance;
		} else {
			return (org.sakaiproject.faqs.api.FaqsService) ComponentManager
					.get(org.sakaiproject.faqs.api.FaqsService.class);
		}
	}

	public static List getFaqCategories(String siteId) {
		org.sakaiproject.faqs.api.FaqsService service = getInstance();
		if (service == null)
			return null;

		return service.getFaqCategories(siteId);
	}

	public static List getFaqCategory(int categoryId) {
		org.sakaiproject.faqs.api.FaqsService service = getInstance();
		if (service == null)
			return null;

		return service.getFaqCategory(categoryId);
	}
	
	
	public static List getFaqContent(int contentId) {
		org.sakaiproject.faqs.api.FaqsService service = getInstance();
		if (service == null)
			return null;

		return service.getFaqContent(contentId);
	}

	public static List getFaqContents(Integer categoryId) {
		org.sakaiproject.faqs.api.FaqsService service = getInstance();
		if (service == null)
			return null;

		return service.getFaqContents(categoryId);
	}

	public static void insertFaqCategory(String siteId, String categoryDesc) {
		org.sakaiproject.faqs.api.FaqsService service = getInstance();
		service.insertFaqCategory(siteId, categoryDesc);
	}
	
	public static void updateFaqCategory(String categoryDesc, int categoryId) {
		org.sakaiproject.faqs.api.FaqsService service = getInstance();
		service.updateFaqCategory(categoryDesc, categoryId);
	}
	public static void updateFaqContent(FaqContent faqContent) {
		org.sakaiproject.faqs.api.FaqsService service = getInstance();
		service.updateFaqContent(faqContent);
	}

}
