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
package org.sakaiproject.qna.tool.entity;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.sakaiproject.entitybroker.entityprovider.CoreEntityProvider;
import org.sakaiproject.entitybroker.util.AbstractEntityProvider;
import org.sakaiproject.qna.logic.CategoryLogic;
import org.sakaiproject.qna.model.QnaCategory;

/**
 * Entity provider for categories
 */
public class CategoryEntityProvider extends AbstractEntityProvider implements CoreEntityProvider {
	public final static String ENTITY_PREFIX = "qna-category";
	private static Log log = LogFactory.getLog(CategoryEntityProvider.class);
	
	/**
	 * Injected services
	 */
	private CategoryLogic categoryLogic;
	
	
	public void setCategoryLogic(CategoryLogic categoryLogic) {
		this.categoryLogic = categoryLogic;
	}

	public String getEntityPrefix() {
		return ENTITY_PREFIX;
	}

	public boolean entityExists(String id) {
		log.debug("exists: " + id);
		if (id == null) {
            return false;
        }
        if ("".equals(id)) {
            return true;
        }
        
        QnaCategory question = categoryLogic.getCategoryById(id);
        boolean exists = (question != null);
        return exists;
	}
}
