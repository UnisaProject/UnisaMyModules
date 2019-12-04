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

package org.sakaiproject.prescribedbooks.cover;

import org.sakaiproject.component.cover.ComponentManager;
import org.sakaiproject.event.api.UsageSession;
import org.sakaiproject.user.api.User;

/**
 * <p>
 * prescribedbooksService is a static Cover for the
 * {@link org.sakaiproject.prescribedbooks.api.prescribedbooksService prescribedbooksService}; see
 * that interface for usage details.
 * </p>
 */
public class PrescribedbooksService{

private static org.sakaiproject.prescribedbooks.api.PrescribedbooksService m_instance = null;


	/**
	 * Access the component instance: special cover only method.
	 * 
	 * @return the component instance.
	 */
	public static org.sakaiproject.prescribedbooks.api.PrescribedbooksService getInstance() {
		if (ComponentManager.CACHE_COMPONENTS) {
			if (m_instance == null)
				m_instance = (org.sakaiproject.prescribedbooks.api.PrescribedbooksService) ComponentManager
						.get(org.sakaiproject.prescribedbooks.api.PrescribedbooksService.class);
			return m_instance;
		} else {
			return (org.sakaiproject.prescribedbooks.api.PrescribedbooksService) ComponentManager
					.get(org.sakaiproject.prescribedbooks.api.PrescribedbooksService.class);
		}
	}

	public static java.lang.String getCurrentUserName() {
		org.sakaiproject.prescribedbooks.api.PrescribedbooksService service = getInstance();
		if (service == null)
			return null;

		return service.getCurrentUserName();
	}

	public static java.lang.String getPrescribedbooksContent(String siteId) {
		org.sakaiproject.prescribedbooks.api.PrescribedbooksService service = getInstance();
		if (service == null)
			return null;

		return service.getPrescribedbooksContent(siteId);
	}
	
	
}
