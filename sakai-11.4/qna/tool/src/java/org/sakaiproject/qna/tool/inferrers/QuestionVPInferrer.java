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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.sakaiproject.entitybroker.EntityReference;
import org.sakaiproject.qna.logic.entity.QuestionEntityProvider;
import org.sakaiproject.qna.tool.params.QuestionParams;
import org.sakaiproject.qna.tool.producers.ViewQuestionProducer;
import org.sakaiproject.rsf.entitybroker.EntityViewParamsInferrer;

import uk.org.ponder.rsf.viewstate.ViewParameters;

public class QuestionVPInferrer implements EntityViewParamsInferrer {
	private static Log log = LogFactory.getLog(QuestionVPInferrer.class);
	public String[] getHandledPrefixes() {
		return new String[] {QuestionEntityProvider.ENTITY_PREFIX};
	}

	/**
	 * @see EntityViewParamsInferrer#inferDefaultViewParameters(String)
	 */
	public ViewParameters inferDefaultViewParameters(String reference) {
		 EntityReference ref = new EntityReference(reference);
		 log.debug(reference.toString());
		 return new QuestionParams(ViewQuestionProducer.VIEW_ID, ref.getId(), true);
	}

}
