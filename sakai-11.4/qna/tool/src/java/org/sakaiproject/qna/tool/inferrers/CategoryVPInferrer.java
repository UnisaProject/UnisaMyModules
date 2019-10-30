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
package org.sakaiproject.qna.tool.inferrers;

import org.sakaiproject.entitybroker.IdEntityReference;
import org.sakaiproject.qna.logic.entity.CategoryEntityProvider;
import org.sakaiproject.qna.tool.params.CategoryParams;
import org.sakaiproject.qna.tool.producers.CategoryProducer;
import org.sakaiproject.rsf.entitybroker.EntityViewParamsInferrer;

import uk.org.ponder.rsf.viewstate.ViewParameters;

public class CategoryVPInferrer implements EntityViewParamsInferrer {

	public String[] getHandledPrefixes() {
		return new String[] {CategoryEntityProvider.ENTITY_PREFIX};
	}

	/**
	 * @see EntityViewParamsInferrer#inferDefaultViewParameters(String)
	 */
	public ViewParameters inferDefaultViewParameters(String reference) {
		IdEntityReference ref = new IdEntityReference(reference);
		return new CategoryParams(CategoryProducer.VIEW_ID, "1", null, ref.id);
	}

}
