/**
 * Copyright (c) 2007-2009 The Sakai Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *             http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.sakaiproject.qna.model.constants;

public class QnaConstants {
	
	/**
	 * Default student view: category
	 */
	public static final String CATEGORY_VIEW = "category";
	
	/**
	 * Default student view: most popular
	 */
	public static final String MOST_POPULAR_VIEW = "most_popular";
	
	/**
	 * Notification type: site contact
	 */
	public static final String SITE_CONTACT = "site_contact";
	
	/**
	 * Notification type: specified list
	 */
	public static final String CUSTOM_LIST = "custom";

	/**
	 * Notification type: everyone with update rights
	 */
	public static final String UPDATE_RIGHTS = "update";
	
	/**
	 * Check if valid view type
	 *  
	 * @param viewType {@link String} view type to check
	 * @return true if valid, false otherwise
	 */
	public static boolean isValidView(String viewType) {
		if (viewType.equalsIgnoreCase(CATEGORY_VIEW) || viewType.equalsIgnoreCase(MOST_POPULAR_VIEW)) {
			return true;
		} else {
			return false;
		}
	}
}

