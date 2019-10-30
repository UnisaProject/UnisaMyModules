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

package org.sakaiproject.template.cover;

import org.sakaiproject.component.cover.ComponentManager;
import org.sakaiproject.event.api.UsageSession;
import org.sakaiproject.user.api.User;

/**
 * <p>
 * TemplateService is a static Cover for the
 * {@link org.sakaiproject.template.api.TemplateService TemplateService}; see
 * that interface for usage details.
 * </p>
 */
public class TemplateService{

private static org.sakaiproject.template.api.TemplateService m_instance = null;


	/**
	 * Access the component instance: special cover only method.
	 * 
	 * @return the component instance.
	 */
	public static org.sakaiproject.template.api.TemplateService getInstance() {
		if (ComponentManager.CACHE_COMPONENTS) {
			if (m_instance == null)
				m_instance = (org.sakaiproject.template.api.TemplateService) ComponentManager
						.get(org.sakaiproject.template.api.TemplateService.class);
			return m_instance;
		} else {
			return (org.sakaiproject.template.api.TemplateService) ComponentManager
					.get(org.sakaiproject.template.api.TemplateService.class);
		}
	}

	public static java.lang.String getCurrentUserName() {
		org.sakaiproject.template.api.TemplateService service = getInstance();
		if (service == null)
			return null;

		return service.getCurrentUserName();
	}

	public static java.lang.String getSakaiUserId(String eid) {
		org.sakaiproject.template.api.TemplateService service = getInstance();
		if (service == null)
			return null;

		return service.getSakaiUserId(eid);
	}

}
