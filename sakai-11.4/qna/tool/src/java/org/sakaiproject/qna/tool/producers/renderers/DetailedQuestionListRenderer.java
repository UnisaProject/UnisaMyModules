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

import org.sakaiproject.qna.logic.ExternalLogic;
import org.sakaiproject.qna.model.QnaQuestion;
import org.sakaiproject.qna.tool.constants.SortByConstants;
import org.sakaiproject.qna.tool.params.QuestionParams;
import org.sakaiproject.qna.tool.params.SortPagerViewParams;
import org.sakaiproject.qna.tool.producers.QueuedQuestionProducer;
import org.sakaiproject.qna.tool.producers.ViewPrivateReplyProducer;
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
import uk.org.ponder.rsf.components.UISelect;
import uk.org.ponder.rsf.components.UISelectChoice;
import uk.org.ponder.rsf.components.UIVerbatim;
import uk.org.ponder.stringutil.StringList;

public class DetailedQuestionListRenderer implements QuestionListRenderer {

	private ExternalLogic externalLogic;
	private PagerRenderer pagerRenderer;
	private SortHeaderRenderer sortHeaderRenderer;
	private QuestionsSorter questionsSorter;

	public static final String DEFAULT_SORT_DIR = SortByConstants.SORT_DIR_DESC;
	public static final String DEFAULT_SORT_BY = SortByConstants.VIEWS;

	private String current_sort_by = DEFAULT_SORT_BY;
    private String current_sort_dir = DEFAULT_SORT_DIR;

    public void setPagerRenderer(PagerRenderer pagerRenderer) {
		this.pagerRenderer = pagerRenderer;
	}

    public void setSortHeaderRenderer(SortHeaderRenderer sortHeaderRenderer) {
    	this.sortHeaderRenderer = sortHeaderRenderer;
    }

	public void setExternalLogic(ExternalLogic externalLogic) {
		this.externalLogic = externalLogic;
	}

	public void setQuestionsSorter(QuestionsSorter questionsSorter) {
		this.questionsSorter = questionsSorter;
	}
	
	/**
	 * @see QuestionListRenderer#makeQuestionList(UIContainer, String, SortPagerViewParams, UIForm)
	 */
	public void makeQuestionList(UIContainer tofill, String divID, SortPagerViewParams params, UIForm form) {

		UIMessage.make(tofill,"sort-message","qna.view-questions.sort-message");

		UIJointContainer listTable = new UIJointContainer(tofill,divID,"question-list-table:");

		if (params.sortBy == null) params.sortBy = DEFAULT_SORT_BY;
    	if (params.sortDir == null) params.sortDir = DEFAULT_SORT_DIR;

    	current_sort_by = params.sortBy;
    	current_sort_dir = params.sortDir;

		//get paging data
        List<QnaQuestion> questionsAll = questionsSorter.getSortedQuestionList(externalLogic.getCurrentLocationId(), params.viewtype, current_sort_by, true, current_sort_dir.equals(SortByConstants.SORT_DIR_DESC));
        List<QnaQuestion> questions = questionsSorter.filterQuestions(questionsAll, params.current_start, params.current_count);
        int total_count = questionsAll != null ? questionsAll.size() : 0;

    	pagerRenderer.makePager(listTable, "pagerDiv:", params.viewID, params, total_count);

    	//table headers and sorting links
        sortHeaderRenderer.makeSortingLink(listTable, "tableheader.question", params,
        		SortByConstants.QUESTIONS, "qna.view-questions.questions");
        sortHeaderRenderer.makeSortingLink(listTable, "tableheader.views", params,
        		SortByConstants.VIEWS, "qna.view-questions.views");
        sortHeaderRenderer.makeSortingLink(listTable, "tableheader.answers", params,
        		SortByConstants.ANSWERS, "qna.view-questions.answers");
        sortHeaderRenderer.makeSortingLink(listTable, "tableheader.created", params,
        		SortByConstants.CREATED, "qna.view-questions.created");
        sortHeaderRenderer.makeSortingLink(listTable, "tableheader.modified", params,
        		SortByConstants.MODIFIED, "qna.view-questions.modified");
        sortHeaderRenderer.makeSortingLink(listTable, "tableheader.category", params,
        		SortByConstants.CATEGORY, "qna.view-questions.category");        
        UIMessage.make(listTable, "tableheader.remove:", "qna.view-questions.remove");

        StringList deletable = new StringList();
        UISelect questionDeleteSelect = UISelect.makeMultiple(form, "remove-question-cell", null, "#{DeleteMultiplesHelper.questionids}", null);

        //Fill out Table
        for (QnaQuestion question : questions) {

        	UIBranchContainer row = UIBranchContainer.make(listTable, "question-entry:");

        	if (question.isPublished()) {
        		UIInternalLink.make(row,"question-link", (String)null , new QuestionParams(ViewQuestionProducer.VIEW_ID,question.getId().toString()));
        	} else {
        		if (question.hasPrivateReplies()) {
        			UIInternalLink.make(row,"question-link", (String)null, new QuestionParams(ViewPrivateReplyProducer.VIEW_ID,question.getId().toString()));
        		} else {
        			UIInternalLink.make(row,"question-link", (String)null, new QuestionParams(QueuedQuestionProducer.VIEW_ID,question.getId().toString()));
        		}
        	}

        	UIVerbatim.make(row,"question-link-text",TextUtil.stripTags(question.getQuestionText()));
        	UIOutput.make(row, "question_row_views", question.getViews() + "");
        	UIOutput.make(row, "question_row_answers", question.getAnswers().size() + "");
        	UIOutput.make(row, "question_row_created", DateUtil.getSimpleDate(question.getDateCreated()));
        	UIOutput.make(row, "question_row_modified", DateUtil.getSimpleDate(question.getDateLastModified()));
        	if (question.getCategory() != null) {
        		UIOutput.make(row,"question_row_category",question.getCategory().getCategoryText());
        	} else {
        		UIOutput.make(row,"question_row_category","");
        	}
        	UISelectChoice.make(row, "remove-question-checkbox", questionDeleteSelect.getFullID(), deletable.size());
        	deletable.add(question.getId().toString());
        }

        questionDeleteSelect.optionlist.setValue(deletable.toStringArray());
	}
}