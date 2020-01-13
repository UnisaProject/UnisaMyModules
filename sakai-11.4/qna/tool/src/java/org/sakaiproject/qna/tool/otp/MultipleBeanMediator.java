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

import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.sakaiproject.content.api.FilePickerHelper;
import org.sakaiproject.entity.api.Reference;
import org.sakaiproject.qna.logic.CategoryLogic;
import org.sakaiproject.qna.logic.ExternalLogic;
import org.sakaiproject.qna.logic.QuestionLogic;
import org.sakaiproject.qna.logic.exceptions.QnaConfigurationException;
import org.sakaiproject.qna.model.QnaAnswer;
import org.sakaiproject.qna.model.QnaAttachment;
import org.sakaiproject.qna.model.QnaCategory;
import org.sakaiproject.qna.model.QnaQuestion;
import org.sakaiproject.qna.utils.TextUtil;
import org.sakaiproject.tool.api.SessionManager;
import org.sakaiproject.tool.api.ToolSession;

import uk.org.ponder.messageutil.MessageLocator;
import uk.org.ponder.messageutil.TargettedMessage;
import uk.org.ponder.messageutil.TargettedMessageList;

/**
 * Mediator for working with multiple beans
 *
 */
public class MultipleBeanMediator {

    public static final String NEW_PREFIX = "new ";
    public static final String NEW_1 = NEW_PREFIX + "1";

    private QuestionLocator questionLocator;
	private CategoryLocator categoryLocator;
	private AnswerLocator answerLocator;

    private QuestionLogic questionLogic;
    private CategoryLogic categoryLogic;
    private ExternalLogic externalLogic;

	private TargettedMessageList messages;
	private SessionManager sessionManager;
	private MessageLocator messageLocator;
	
	private static Log log = LogFactory.getLog(MultipleBeanMediator.class);
		
	public void setSessionManager(SessionManager sessionManager) {
		this.sessionManager = sessionManager;
	}
	
	/**
	 * Moves question to other category and save
	 * 
	 * @return return key
	 */
	public String moveQuestionSave() {
		QnaCategory categoryToLink = null;
		Set<String> keys = questionLocator.getDeliveredBeans().keySet();
		String questionId = keys.iterator().next();

		QnaQuestion question = (QnaQuestion)questionLocator.getDeliveredBeans().get(questionId);

		if (TextUtil.isEmptyWithoutTags(((QnaCategory)categoryLocator.locateBean(NEW_1)).getCategoryText())) {
			if (question.getCategoryId() != null) {
				categoryToLink = (QnaCategory)categoryLocator.locateBean(question.getCategoryId());
			}
		} else {
			categoryLocator.save();
			categoryToLink = (QnaCategory)categoryLocator.locateBean(NEW_1);
		}

		if (categoryToLink == null) {
			//not sure how this could happen but we can't continue
			return null;
		}
		String oldCategory = question.getCategory() != null ? TextUtil.stripTags(question.getCategory().getCategoryText()) : null;

		question.setCategory(categoryToLink);
		question.setSortOrder(Integer.valueOf(categoryToLink.getPublishedQuestions().size()));
		questionLogic.saveQuestion(question, externalLogic.getCurrentLocationId());
		
		messages.clear();
		if (oldCategory != null) {
			messages.addMessage(
					new TargettedMessage("qna.move-question.moved-successfully",
					new Object[] { oldCategory, TextUtil.stripTags(categoryToLink.getCategoryText()) },
					TargettedMessage.SEVERITY_INFO)
				);
		} else {
			messages.addMessage(
					new TargettedMessage("qna.move-question.moved-successfully-no-previous",
					new Object[] {TextUtil.stripTags(categoryToLink.getCategoryText()) },
					TargettedMessage.SEVERITY_INFO)
				);
		}

		
		return "saved";
	}


	/**
	 * Saving a new question
	 * @return return key
	 */
    // TODO: When time permits: combine the two calls + try to remove categoryId string field from model
    public String saveNew() {
    	log.debug("saveNew()");
    	QnaCategory categoryToLink=null;

		QnaQuestion newQuestion = (QnaQuestion)questionLocator.locateBean(NEW_1);
		addAttachments(newQuestion);
		
		log.debug("got question " + newQuestion.getId() + " in category " + newQuestion.getCategoryId());
		
		if (TextUtil.isEmptyWithoutTags(newQuestion.getQuestionText())) {
			messages.addMessage(new TargettedMessage("qna.ask-question.save-failure-empty", new Object[]{}, TargettedMessage.SEVERITY_ERROR));
			log.debug("attempted to save empty question");
			return "error";
		}

		if ( newQuestion.getCategoryId() == null ) {
			//this should go in the default category
			String location = externalLogic.getCurrentLocationId();
			QnaCategory cat = categoryLogic.getDefaultCategory(location);
			newQuestion.setCategoryId(cat.getId());
		}
		
		if (TextUtil.isEmptyWithoutTags(((QnaCategory)categoryLocator.locateBean(NEW_1)).getCategoryText())) {
			if (newQuestion.getCategoryId() != null) {
				categoryToLink = (QnaCategory)categoryLocator.locateBean(newQuestion.getCategoryId());
			}
		} else {
			categoryLocator.save();
			categoryToLink = (QnaCategory)categoryLocator.locateBean(NEW_1);
		}

		newQuestion.setCategory(categoryToLink);
		messages.clear();
		questionLocator.saveAll();
		
		if (newQuestion.isPublished()) {
			//log.debug("setting message qna.ask-question.save-success with text: " + TextUtil.stripTags(newQuestion.getCategory().getCategoryText()) + "and severity " + TargettedMessage.SEVERITY_INFO);
			//in the case of moderated questions the Category text can be null
			log.debug("new question is: " + newQuestion.getQuestionText() + " in " + newQuestion.getCategoryId());
			if (newQuestion.getCategoryId() != null) {
				messages.addMessage(
						new TargettedMessage("qna.ask-question.save-success",
								new Object[] { TextUtil.stripTags(newQuestion.getCategory().getCategoryText()) },
								TargettedMessage.SEVERITY_INFO)
				);
			} else {
				//if moderated it gets saved in general questions
				messages.addMessage(
						new TargettedMessage("qna.ask-question.save-success",
								new Object[] {messageLocator.getMessage("qna.default-category.text")},
								TargettedMessage.SEVERITY_INFO)
				);
				
			}
		} else {
			//log.debug("setting message qna.ask-question.save-success-unpublished  with text: and severity " + TextUtil.stripTags(newQuestion.getCategory().getCategoryText()));
			messages.addMessage(
					new TargettedMessage("qna.ask-question.save-success-unpublished",new Object[]{},TargettedMessage.SEVERITY_INFO)
				);
		}
		log.debug("question saved: returning");
    	return "saved";
    }
    
    /**
     * Add attachments on session to {@link QnaQuestion}
     * 
     * @param question {@link QnaQuestion} to add attachments to
     */
    @SuppressWarnings("unchecked")
	private void addAttachments(QnaQuestion question) {
		ToolSession session = sessionManager.getCurrentToolSession();
		
		if (session.getAttribute(FilePickerHelper.FILE_PICKER_ATTACHMENTS) != null) 
		{
			List<Reference> refs = (List<Reference>) session.getAttribute(FilePickerHelper.FILE_PICKER_ATTACHMENTS);
			for (Reference ref : refs) {
				if (question != null) {
					QnaAttachment attachment = new QnaAttachment();
					attachment.setAttachmentId(ref.getId());
					question.addAttachment(attachment);
				}
			}
		}

	    session.removeAttribute(FilePickerHelper.FILE_PICKER_ATTACHMENTS);
	    session.removeAttribute(FilePickerHelper.FILE_PICKER_CANCEL);
    }
    
    /**
     * Saves all beans
     * @return return key
     */
    public String saveAll() {
    	// If a new category was created. Check that category text is not empty.
		if (!TextUtil.isEmptyWithoutTags(((QnaCategory) categoryLocator.locateBean(NEW_1)).getCategoryText())) {
			categoryLocator.save();
			QnaCategory categoryToLink = (QnaCategory) categoryLocator.locateBean(NEW_1);

			for (QnaQuestion question : questionLocator.getDeliveredBeans().values()) {
				question.setCategory(categoryToLink);
			}
			questionLocator.saveAll();
		} else {
			if (questionLocator.getDeliveredBeans().size() == 1) { // Should only be 1
				for (QnaQuestion question : questionLocator.getDeliveredBeans().values()) {
					if (question.getCategoryId() != null) {
						question.setCategory((QnaCategory)categoryLocator.locateBean(question.getCategoryId()));
					}
				}
			}

			questionLocator.saveAll();
		}

		// If answer was added
		if (answerLocator.getDeliveredBeans().values().size() > 0) {

			if (!answerLocator.getDeliveredBeans().containsKey(NEW_1)) {
				answerLocator.saveAll();
			} else if (!TextUtil.isEmptyWithoutTags(((QnaAnswer)answerLocator.locateBean(NEW_1)).getAnswerText())) {
				answerLocator.saveAll();
			}
		}
		return "saved";
    }

	/**
	 * Publishing a question
	 * 
	 * @return return key
	 */
	public String publish() {
		saveAll();
		messages.clear();
		for (QnaQuestion question : questionLocator.getDeliveredBeans().values()) {
			try
			{
				questionLogic.publishQuestion(question.getId(), externalLogic.getCurrentLocationId());
				messages.addMessage(new TargettedMessage("qna.publish-queued-question.publish-success",
			                new Object[] { TextUtil.stripTags(question.getQuestionText()) },
			                TargettedMessage.SEVERITY_INFO));
			} catch (QnaConfigurationException qne) {
				log.info("Error received when publishing question: " + qne.getMessage());
				messages.addMessage(new TargettedMessage("qna.publish-queued-question.publish-failure",
		                new Object[] {},
		                TargettedMessage.SEVERITY_ERROR));
			}
		}
		return "saved-published";
	}

	public String deleteQuestions() {
		return "deletedQuestions";
	}
	
	/**
	 * Special method binding when questions and categories are deleted
	 * Used for giving correct message
	 * @return return key
	 */
	public String deleteMultiple() {
		messages.clear();
		messages.addMessage(new TargettedMessage("qna.multiple-delete.delete-success",
               new Object[]{},
               TargettedMessage.SEVERITY_INFO));
		return "delete";
	}
	
	public void setQuestionLocator(QuestionLocator questionLocator) {
		this.questionLocator = questionLocator;
	}

	public void setCategoryLocator(CategoryLocator categoryLocator) {
		this.categoryLocator = categoryLocator;
	}

	public void setAnswerLocator(AnswerLocator answerLocator) {
		this.answerLocator = answerLocator;
	}

	public void setQuestionLogic(QuestionLogic questionLogic) {
		this.questionLogic = questionLogic;
	}

	public void setExternalLogic(ExternalLogic externalLogic) {
		this.externalLogic = externalLogic;
	}

	public void setMessages(TargettedMessageList messages) {
		this.messages = messages;
	}

	public void setMessageLocator(MessageLocator messageLocator) {
		this.messageLocator = messageLocator;
	}

	public void setCategoryLogic(CategoryLogic cl) {
		categoryLogic = cl;
	}
}
