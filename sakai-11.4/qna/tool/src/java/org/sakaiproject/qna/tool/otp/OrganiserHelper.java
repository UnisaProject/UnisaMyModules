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
package org.sakaiproject.qna.tool.otp;

import org.sakaiproject.qna.logic.CategoryLogic;
import org.sakaiproject.qna.logic.ExternalLogic;
import org.sakaiproject.qna.logic.QuestionLogic;
import org.sakaiproject.qna.model.QnaCategory;
import org.sakaiproject.qna.model.QnaQuestion;

import uk.org.ponder.messageutil.TargettedMessage;
import uk.org.ponder.messageutil.TargettedMessageList;

/**
 *	Used in Organiser 
 *
 */
public class OrganiserHelper {

	// Instansiate to empty arrays
	public String[] catorder = new String[0];
	public String[] queorder = new String[0];
	public String[] questionCategoryOrder = new String[0];
	
	private CategoryLogic categoryLogic;
	private ExternalLogic externalLogic;
	private QuestionLogic questionLogic;
	private TargettedMessageList messages;
	 
	public String[] getQueorder() {
		return queorder;
	}
	public void setQueorder(String[] queorder) {
		this.queorder = queorder;
	}
	public String[] getCatorder() {
		return catorder;
	}
	public void setCatorder(String[] catorder) {
		this.catorder = catorder;
	}
	
	public String[] getQuestionCategoryOrder() {
		return questionCategoryOrder;
	}
	
	public void setQuestionCategoryOrder(String[] questionCategoryOrder) {
		this.questionCategoryOrder = questionCategoryOrder;
	}
	
	public void setCategoryLogic(CategoryLogic categoryLogic) {
		this.categoryLogic = categoryLogic;
	}
	
	public void setExternalLogic(ExternalLogic externalLogic) {
		this.externalLogic = externalLogic;
	}
	
	public void setQuestionLogic(QuestionLogic questionLogic) {
		this.questionLogic = questionLogic;
	}

	public void setMessages(TargettedMessageList messages) {
		this.messages = messages;
	}
	
	/**
	 * Saves order
	 * @return return key
	 */
	public String saveOrder() {
		for (int k=0; k<catorder.length; k++) {
			String id = catorder[k];
			QnaCategory category = categoryLogic.getCategoryById(id);
			
			if (category != null) {
				category.setSortOrder(Integer.valueOf(k));
				categoryLogic.saveCategory(category, externalLogic.getCurrentLocationId());
			}
		}

		int nr = 0;
		String tmpcatid = "";
		for (int k=0; k<queorder.length; k++) {
			String id = queorder[k];
			QnaQuestion question = questionLogic.getQuestionById(Long.valueOf(id));
			
			if (question != null) {
				String catid = questionCategoryOrder[k];
				if (tmpcatid.equals(catid)) {
					nr++;
				} else {
					nr = 0;
				}
				tmpcatid = catid;
	
				question.setSortOrder(Integer.valueOf(nr));
							
				if (!question.getCategory().getId().equals(questionCategoryOrder[k])) {
					question.setCategory(categoryLogic.getCategoryById(questionCategoryOrder[k]));
				}
				
				questionLogic.saveQuestion(question, externalLogic.getCurrentLocationId());
			}
		}
		messages.addMessage(new TargettedMessage("qna.organise.successful-save",null,TargettedMessage.SEVERITY_INFO));
		return "saved";
	}
}
