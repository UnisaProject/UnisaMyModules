/**********************************************************************************
 * $URL$
 * $Id$
 ***********************************************************************************
 *
 * Copyright (c) 2003, 2004, 2005, 2006, 2007, 2008 The Sakai Foundation
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

package org.sakaiproject.faqs.api;

import java.sql.Types;
import java.util.List;

import org.sakaiproject.faqs.dataModel.FaqCategory;
import org.sakaiproject.faqs.dataModel.FaqContent;
 

public interface FaqsService
{
	/** This string starts the references to resources in this service. */
	static final String REFERENCE_ROOT = "/faqs";

	List getFaqCategories(String siteId);	
	List getFaqContents(Integer categoryId);
	void insertFaqCategory(String siteId, String categoryDesc);
	void insertFaqContent(String question,String answer, String categoryId);
	List getFaqCategory(int categoryId);
	List getFaqContent(int contentId);
	List getFaqContentWithCategory(int contentId);
	void updateFaqCategory(String categoryDesc, int categoryId);	
	void updateFaqContent(FaqContent faqContent);
	void deleteFaqContent(Integer contentId);
	void deleteFaqCategory(Integer categoryId);
 
}
