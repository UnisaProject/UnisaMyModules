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
package org.sakaiproject.qna.tool.producers.renderers;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.sakaiproject.qna.logic.ExternalLogic;
import org.sakaiproject.qna.model.QnaQuestion;
import org.sakaiproject.qna.tool.constants.SortByConstants;
import org.sakaiproject.qna.tool.params.QuestionParams;
import org.sakaiproject.qna.tool.params.SortPagerViewParams;
import org.sakaiproject.qna.tool.producers.ViewQuestionProducer;
import org.sakaiproject.qna.tool.utils.DateUtil;
import org.sakaiproject.qna.tool.utils.QuestionsSorter;
import org.sakaiproject.qna.utils.TextUtil;

import uk.org.ponder.rsf.components.UIBranchContainer;
import uk.org.ponder.rsf.components.UIContainer;
import uk.org.ponder.rsf.components.UIForm;
import uk.org.ponder.rsf.components.UIInternalLink;
import uk.org.ponder.rsf.components.UIJointContainer;
import uk.org.ponder.rsf.components.UIMessage;
import uk.org.ponder.rsf.components.UIOutput;

/**
 * Standard question list
 * Only shows published questions
 */
public class StandardQuestionListRenderer implements QuestionListRenderer {

	private static Log log = LogFactory.getLog(StandardQuestionListRenderer.class);
	private QuestionsSorter questionsSorter;
	private ExternalLogic externalLogic;
	private PagerRenderer pagerRenderer;

	public void setQuestionsSorter(QuestionsSorter questionsSorter) {
		this.questionsSorter = questionsSorter;
	}

	public void setExternalLogic(ExternalLogic externalLogic) {
		this.externalLogic = externalLogic;
	}

    public void setPagerRenderer(PagerRenderer pagerRenderer) {
		this.pagerRenderer = pagerRenderer;
	}
    
    /**
     * @see QuestionListRenderer#makeQuestionList(UIContainer, String, SortPagerViewParams, UIForm)
     */
	public void makeQuestionList(UIContainer tofill, String divID, SortPagerViewParams params, UIForm form) {
		
		UIJointContainer listTable = new UIJointContainer(tofill,divID,"question-list-table:");

		UIMessage.make(listTable,"rank-title","qna.view-questions.rank");
		UIMessage.make(listTable,"question-title","qna.view-questions.questions");

		if (params.sortBy.equals(SortByConstants.VIEWS)) {
			UIMessage.make(listTable,"ordered-by-title","qna.view-questions.views");
		} else if (params.sortBy.equals(SortByConstants.MODIFIED)) {
			UIMessage.make(listTable,"ordered-by-title","qna.view-questions.modified");
		} else if (params.sortBy.equals(SortByConstants.CREATED)) {
			UIMessage.make(listTable,"ordered-by-title","qna.view-questions.created");
		}
		log.debug("Sorted by:" + params.sortBy + " order: " + params.sortDir);
		boolean reverseSort = false;
		//if null find a default
		if (params.sortDir == null && SortByConstants.CREATED.equals(params.sortBy)) {
			params.sortDir = SortByConstants.SORT_DIR_DESC;
		}
		
		
		if (SortByConstants.SORT_DIR_DESC.equals(params.sortDir)) {
			log.debug("we are rever sorting this list");
			reverseSort = true;
		} 
		
		List<QnaQuestion> questionsAll = questionsSorter.getSortedQuestionList(externalLogic.getCurrentLocationId(), params.viewtype, params.sortBy, false, reverseSort);
		List<QnaQuestion> questions = questionsSorter.filterQuestions(questionsAll, params.current_start, params.current_count);

        int total_count = questionsAll != null ? questionsAll.size() : 0;
    	pagerRenderer.makePager(listTable, "pagerDiv:", params.viewID, params, total_count);
    	
		int rank = 1;
		for (QnaQuestion qnaQuestion : questions) {
			if (displayQuestion(qnaQuestion)) {
				UIBranchContainer entry = UIBranchContainer.make(listTable, "question-entry:");
				UIOutput.make(entry,"rank-nr",rank + "");
				UIInternalLink.make(entry,"question-link",TextUtil.stripTags(qnaQuestion.getQuestionText()),new QuestionParams(ViewQuestionProducer.VIEW_ID,qnaQuestion.getId().toString()));
				if (params.sortBy.equals(SortByConstants.VIEWS)) {
					UIOutput.make(entry,"ordered-by",qnaQuestion.getViews() + "");
				} else if (params.sortBy.equals(SortByConstants.MODIFIED)) {
					UIOutput.make(entry,"ordered-by",DateUtil.getSimpleDate(qnaQuestion.getDateLastModified()));
				} else if (params.sortBy.equals(SortByConstants.CREATED)) {
					UIOutput.make(entry,"ordered-by",DateUtil.getSimpleDate(qnaQuestion.getDateCreated()));
				}
				rank++;
			}
		}
	}
	
	/**
	 * Should question be displayed or not
	 * 
	 * @param question {@link QnaQuestion}
	 * @return true if should be displayed, false if not
	 */
	private boolean displayQuestion(QnaQuestion question) {
		if (question.getHidden() != null && question.getHidden()) {
			return false;
		}
		if (question.getCategory() != null) {
			if (question.getCategory().getHidden()) {
				return false;
			}
		}
		return true;
	}
}
