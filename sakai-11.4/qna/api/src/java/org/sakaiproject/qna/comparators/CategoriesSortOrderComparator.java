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
package org.sakaiproject.qna.comparators;

import java.util.Comparator;

import org.sakaiproject.qna.model.QnaCategory;

/**
 * Sorts a collection of QnaCategory alphabetically by name
 */
public class CategoriesSortOrderComparator implements Comparator<QnaCategory> {
	
	/**
	 * @see Comparator#compare(Object, Object)	
	 */
	public int compare(QnaCategory c1, QnaCategory c2) {
		return c1.getSortOrder().compareTo(c2.getSortOrder());
	}
}
