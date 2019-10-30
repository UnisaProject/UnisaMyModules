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
package org.sakaiproject.qna.tool.constants;

/**
 * Constants for the view types 
 *
 */
public class ViewTypeConstants {
	public static final String CATEGORIES 		= "CATEGORIES";
	public static final String ALL_DETAILS 		= "ALL_DETAILS";
	public static final String STANDARD 		= "STANDARD";

	/**
	 * Check if string is valid view type
	 * 
	 * @param str to check
	 * @return true of valid, false if no
	 */
	public static boolean isValid(String str) {
		if (str.equals(CATEGORIES) || 
			str.equals(ALL_DETAILS) ||
			str.equals(STANDARD)) {
			return true;
		} else {
			return false;
		}
	}
}
