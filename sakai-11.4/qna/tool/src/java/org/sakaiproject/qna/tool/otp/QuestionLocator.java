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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.sakaiproject.content.api.FilePickerHelper;
import org.sakaiproject.entity.api.Reference;
import org.sakaiproject.qna.logic.AttachmentLogic;
import org.sakaiproject.qna.logic.ExternalLogic;
import org.sakaiproject.qna.logic.QuestionLogic;
import org.sakaiproject.qna.logic.exceptions.AttachmentException;
import org.sakaiproject.qna.model.QnaQuestion;
import org.sakaiproject.qna.utils.QNAUtils;
import org.sakaiproject.qna.utils.TextUtil;
import org.sakaiproject.tool.api.SessionManager;
import org.sakaiproject.tool.api.ToolSession;
import org.sakaiproject.util.FormattedText;

import uk.org.ponder.beanutil.entity.EntityBeanLocator;
import uk.org.ponder.messageutil.TargettedMessage;
import uk.org.ponder.messageutil.TargettedMessageList;

public class QuestionLocator implements EntityBeanLocator  {
    public static final String NEW_PREFIX = "new ";
    public static final String NEW_1 = NEW_PREFIX + "1";

    public static final String SAVED = "saved";
    public static final String CANCEL = "cancel";
    public static final String DELETE = "delete";
    
    private QuestionLogic questionLogic;
    private ExternalLogic externalLogic;
    private AttachmentLogic attachmentLogic;

	private Map<String, QnaQuestion> delivered = new HashMap<String,QnaQuestion>();

	private TargettedMessageList messages;

	private SessionManager sessionManager;
	
	public void setSessionManager(SessionManager sessionManager) {
		this.sessionManager = sessionManager;
	}
	
	public void setMessages(TargettedMessageList messages) {
		this.messages = messages;
	}
	
	/**
	 * Remove bean
	 */
	public boolean remove(String beanname) {
		try {
			QnaQuestion question = questionLogic.getQuestionById(Long.valueOf(beanname));
			questionLogic.removeQuestion(beanname, externalLogic.getCurrentLocationId());
			delivered.remove(beanname);

			if (messages.size() == 0) {
				if (delivered.size() > 0) {
		            messages.addMessage(
		            		new TargettedMessage("qna.delete-question.delete-multiple-successful",
		    				null,
		                    TargettedMessage.SEVERITY_INFO)
		        		);
				} else {
					messages.addMessage(
							new TargettedMessage("qna.delete-question.delete-successful",
							new Object[] {TextUtil.stripTags(question.getQuestionText())},
							TargettedMessage.SEVERITY_INFO));
				}
			}
			return true;
		} catch (AttachmentException ae) {
			messages.addMessage(new TargettedMessage("qna.delete-question.attachment-error", null, TargettedMessage.SEVERITY_ERROR));
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public void set(String beanname, Object toset) {
		throw new UnsupportedOperationException("Not implemented");
	}
	
	/**
	 * Locate bean
	 * 
	 * @param name bean
	 * @return {@link QnaQuestion}
	 */
	public Object locateBean(String name) {
		QnaQuestion togo = delivered.get(name);
		if (togo == null) {
			 if (name.startsWith(NEW_PREFIX)) {
				 togo = new QnaQuestion();
			 } else {
				 togo = questionLogic.getQuestionById(Long.valueOf(name));
			 }
			 delivered.put(name, togo);
		}
		return togo;
	}
	
	/**
	 * Save all beans
	 * 
	 * @return return key
	 */
	public void saveAll() {
		for (QnaQuestion question : delivered.values()) {
			//question text needs to be escaped
			String escapedQ = FormattedText.processFormattedText(question.getQuestionText(), new StringBuilder());
			escapedQ = QNAUtils.cleanupHtmlPtags(escapedQ);
			question.setQuestionText(escapedQ);
			questionLogic.saveQuestion(question, externalLogic.getCurrentLocationId());
		}
		return;
	}
	
	public String saveQuestions(){
		saveAll();
		return "Saved";
	}
	
	/**
	 * Edit existing bean
	 * 
	 * @return return key
	 */
	@SuppressWarnings("unchecked")
	public String edit() {
		ToolSession session = sessionManager.getCurrentToolSession();
		
		for (String key : delivered.keySet()) {
			if (!key.startsWith(NEW_PREFIX)) {
				QnaQuestion toEdit = delivered.get(key);
				
				if (session.getAttribute(FilePickerHelper.FILE_PICKER_ATTACHMENTS) != null) {
					attachmentLogic.synchAttachmentList(toEdit, (List<Reference>) session.getAttribute(FilePickerHelper.FILE_PICKER_ATTACHMENTS));
				}
				
				questionLogic.saveQuestion(toEdit, externalLogic.getCurrentLocationId());
			}
		}
		messages.addMessage(new TargettedMessage("qna.ask-question.updated-success",null,TargettedMessage.SEVERITY_INFO));
		
	    session.removeAttribute(FilePickerHelper.FILE_PICKER_ATTACHMENTS);
	    session.removeAttribute(FilePickerHelper.FILE_PICKER_CANCEL);
	    
		return SAVED;
	}
	
	/**
	 * Delete beans
	 * 
	 * @return key
	 */
	public String delete() {
		for (QnaQuestion question : delivered.values()) {
			try {
				questionLogic.removeQuestion(question.getId(), externalLogic.getCurrentLocationId());
			} catch (AttachmentException ae) {
				messages.addMessage(new TargettedMessage("qna.delete-question.attachment-error",null,TargettedMessage.SEVERITY_ERROR));
			} finally {
				if (delivered.size() == 1) {
					messages.addMessage(
							new TargettedMessage("qna.delete-question.delete-successful",
							new Object[] {TextUtil.stripTags(question.getQuestionText())},
							TargettedMessage.SEVERITY_INFO));
				}
			}
		}
		
		if (delivered.size() > 1) {
			messages.addMessage(
					new TargettedMessage("qna.delete-question.delete-multiple-successful",
					null,
					TargettedMessage.SEVERITY_INFO));
		}
		
		return DELETE;
	}
	
	/**
	 * Cancel new question of editing of question
	 * Attachments need to be cleared
	 * 
	 * @return return key
	 */
	@SuppressWarnings("unchecked")
	public String cancel() {
		// Clears file attachments from tool session
		ToolSession session = sessionManager.getCurrentToolSession();
		
		if (session.getAttribute(FilePickerHelper.FILE_PICKER_ATTACHMENTS) != null) 
		{
			if (delivered.keySet().contains(NEW_1)) {
	
					List<Reference> refs = (List<Reference>) session.getAttribute(FilePickerHelper.FILE_PICKER_ATTACHMENTS);
					for (Reference ref : refs) {
						try {
							attachmentLogic.deleteAttachment(ref.getId());
						} catch (AttachmentException ae) {
							messages.addMessage(new TargettedMessage("qna.delete-question.attachment-error",null,TargettedMessage.SEVERITY_ERROR));
						}
					}
	
			} else {
				for (QnaQuestion question : delivered.values()) { // Should only be one
					attachmentLogic.synchAttachmentList(question, (List<Reference>) session.getAttribute(FilePickerHelper.FILE_PICKER_ATTACHMENTS));
					questionLogic.saveQuestion(question, externalLogic.getCurrentLocationId());
				}
			}
		}
				
	    session.removeAttribute(FilePickerHelper.FILE_PICKER_ATTACHMENTS);
	    session.removeAttribute(FilePickerHelper.FILE_PICKER_CANCEL);
		
		return CANCEL;
	}

	public void setQuestionLogic(QuestionLogic questionLogic) {
		this.questionLogic = questionLogic;
	}

	public void setExternalLogic(ExternalLogic externalLogic) {
		this.externalLogic = externalLogic;
	}
	
	public void setAttachmentLogic(AttachmentLogic attachmentLogic) {
		this.attachmentLogic = attachmentLogic;
	}

	public Map<String, QnaQuestion> getDeliveredBeans() {
		return delivered;
	}
}
