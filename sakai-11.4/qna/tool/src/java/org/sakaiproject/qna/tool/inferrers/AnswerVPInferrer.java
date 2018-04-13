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

import org.sakaiproject.entitybroker.EntityReference;
import org.sakaiproject.qna.logic.AnswerLogic;
import org.sakaiproject.qna.logic.entity.AnswerEntityProvider;
import org.sakaiproject.qna.model.QnaAnswer;
import org.sakaiproject.qna.tool.params.QuestionParams;
import org.sakaiproject.qna.tool.producers.ViewQuestionProducer;
import org.sakaiproject.rsf.entitybroker.EntityViewParamsInferrer;

import uk.org.ponder.rsf.viewstate.ViewParameters;

public class AnswerVPInferrer implements EntityViewParamsInferrer {

	private AnswerLogic answerLogic;
	
	public void setAnswerLogic(AnswerLogic answerLogic) {
		this.answerLogic = answerLogic;
	}
	
	public String[] getHandledPrefixes() {
		return new String[] {AnswerEntityProvider.ENTITY_PREFIX};
	}
	
	/**
	 * @see EntityViewParamsInferrer#inferDefaultViewParameters(String)
	 */
	public ViewParameters inferDefaultViewParameters(String reference) {
		EntityReference ref = new EntityReference(reference);
		QnaAnswer answer = answerLogic.getAnswerById(Long.valueOf(ref.getId()));
		return new QuestionParams(ViewQuestionProducer.VIEW_ID, answer.getQuestion().getId().toString(), true);
	}
}
