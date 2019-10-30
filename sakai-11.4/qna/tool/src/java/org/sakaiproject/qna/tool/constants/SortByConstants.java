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
 * Constants used for sorting of questions 
 *
 */
public class SortByConstants {
	public static final String QUESTIONS = "questions";
	public static final String VIEWS 	= "views";
	public static final String ANSWERS 	= "answers";
	public static final String CREATED 	= "created";
	public static final String MODIFIED = "modified";
	public static final String CATEGORY = "category";
	
	public static final String SORT_DIR_ASC = "asc";
	public static final String SORT_DIR_DESC = "desc";
	
	/**
	 * Check if string is valid sort by value
	 * 
	 * @param str to check
	 * @return true of valid, false if not
	 */
	public static boolean isValid(String str) {
		if (str.equals(QUESTIONS) || 
			str.equals(VIEWS) ||
			str.equals(ANSWERS) ||
			str.equals(CREATED) ||
			str.equals(MODIFIED) ||
			str.equals(CATEGORY)) {
			return true;
		} else {
			return false;
		}
	}
}
