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
package org.sakaiproject.qna.logic.impl.entity;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.sakaiproject.entity.api.Entity;
import org.sakaiproject.entity.api.EntityManager;
import org.sakaiproject.entity.api.EntityProducer;
import org.sakaiproject.entity.api.EntityTransferrer;
import org.sakaiproject.entity.api.HttpAccess;
import org.sakaiproject.entity.api.Reference;
import org.sakaiproject.entity.api.ResourceProperties;
import org.sakaiproject.exception.IdUnusedException;
import org.sakaiproject.qna.dao.QnaDao;
import org.sakaiproject.qna.logic.AttachmentLogic;
import org.sakaiproject.qna.logic.CategoryLogic;
import org.sakaiproject.qna.logic.ExternalLogic;
import org.sakaiproject.qna.logic.OptionsLogic;
import org.sakaiproject.qna.model.QnaAnswer;
import org.sakaiproject.qna.model.QnaAttachment;
import org.sakaiproject.qna.model.QnaCategory;
import org.sakaiproject.qna.model.QnaCustomEmail;
import org.sakaiproject.qna.model.QnaOptions;
import org.sakaiproject.qna.model.QnaQuestion;
import org.sakaiproject.site.api.SiteService;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class QnaEntityProducer implements EntityProducer, EntityTransferrer
{
	
	public static final String REFERENCE_ROOT = Entity.SEPARATOR + "qna";
	
	private static Log log = LogFactory.getLog(QnaEntityProducer.class);
	private EntityManager entityManager;
	private SiteService siteService;
	private QnaDao dao;
	private CategoryLogic categoryLogic;
	private OptionsLogic optionsLogic;
	private AttachmentLogic attachmentLogic;
	
	public void setEntityManager(EntityManager em) {
		entityManager = em;
	}
	
	public void setSiteService(SiteService siteService) {
		this.siteService = siteService;
	}
	
	public void init() {
		try {
			entityManager.registerEntityProducer(this, REFERENCE_ROOT);
		} catch (Throwable t) {
			log.warn("init(): ", t);
		}
	}
	
	public String[] myToolIds() {
		String[] toolIds = { ExternalLogic.QNA_TOOL_ID };
		return toolIds;
	}
	
	/**
	 * Transfers entities from location to location
	 */
	public void transferCopyEntities(String fromContext, String toContext,
			List ids) {
		try {
			String fromLocation = siteService.getSite(fromContext).getReference();
			String toLocation = siteService.getSite(toContext).getReference();
			
			List<QnaCategory> categories = categoryLogic.getCategoriesForLocation(fromLocation);
			// Categories
			for (QnaCategory category : categories) {
				QnaCategory newCategory = new QnaCategory();
				newCategory.setCategoryText(category.getCategoryText());
				newCategory.setDateCreated(category.getDateCreated());
				newCategory.setDateLastModified(category.getDateLastModified());
				newCategory.setHidden(category.getHidden());
				newCategory.setLocation(toLocation);
				newCategory.setOwnerId(category.getOwnerId());
				newCategory.setSortOrder(category.getSortOrder());
								
				dao.save(newCategory);
				List<QnaQuestion> questions = category.getQuestions();
				
				// Questions
				for (QnaQuestion question : questions) {
					QnaQuestion newQuestion = new QnaQuestion();
					newQuestion.setAnonymous(question.isAnonymous());
					newQuestion.setCategory(newCategory);
					newQuestion.setDateCreated(question.getDateCreated());
					newQuestion.setDateLastModified(question.getDateLastModified());
					newQuestion.setHidden(question.getHidden());
					newQuestion.setLastModifierId(question.getLastModifierId());
					newQuestion.setLocation(toLocation);
					newQuestion.setNotify(question.getNotify());
					newQuestion.setOwnerId(question.getOwnerId());
					newQuestion.setPublished(question.isPublished());
					newQuestion.setQuestionText(question.getQuestionText());
					newQuestion.setSortOrder(question.getSortOrder());
					newQuestion.setViews(question.getViews());
					
					// copy attachments
					for (QnaAttachment attachment : question.getAttachments()) {
						QnaAttachment newAttachment = new QnaAttachment();
						newAttachment.setAttachmentId(attachment.getAttachmentId());
						newQuestion.addAttachment(newAttachment);
					}
					
					dao.save(newQuestion);
					
					List<QnaAnswer> answers = question.getAnswers();
					// Answers
					for (QnaAnswer answer : answers) {
						QnaAnswer newAnswer = new QnaAnswer();
						newAnswer.setAnonymous(answer.isAnonymous());
						newAnswer.setAnswerText(answer.getAnswerText());
						newAnswer.setApproved(answer.isApproved());
						newAnswer.setDateCreated(answer.getDateCreated());
						newAnswer.setDateLastModified(answer.getDateLastModified());
						newAnswer.setLastModifierId(answer.getLastModifierId());
						newAnswer.setOwnerId(answer.getOwnerId());
						newAnswer.setPrivateReply(answer.isPrivateReply());
						newAnswer.setQuestion(newQuestion);
						
						dao.save(newAnswer);
					}
				}
			}
			
			// Options
			QnaOptions options = optionsLogic.getOptionsForLocation(fromLocation);
			QnaOptions newOptions =optionsLogic.getOptionsForLocation(toLocation);
			newOptions.setAnonymousAllowed(options.getAnonymousAllowed());
			newOptions.setDateCreated(options.getDateCreated());
			newOptions.setDateLastModified(options.getDateLastModified());
			newOptions.setDefaultStudentView(options.getDefaultStudentView());
			newOptions.setEmailNotification(options.getEmailNotification());
			newOptions.setEmailNotificationType(options.getEmailNotificationType());
			newOptions.setLocation(toLocation);
			newOptions.setModerated(options.isModerated());
			newOptions.setOwnerId(options.getOwnerId());
			
			// Custom emails
			Set<QnaCustomEmail> customMailsOld = options.getCustomEmails();
			
			
			for (QnaCustomEmail qnaCustomEmail : customMailsOld) {
				QnaCustomEmail email = new QnaCustomEmail(qnaCustomEmail.getOwnerId(),qnaCustomEmail.getEmail(),qnaCustomEmail.getDateCreated());
				newOptions.addCustomEmail(email);
			}
			dao.save(newOptions);
		} catch (IdUnusedException e) {
			log.warn("Error importing entities", e);
		}
	}

	public String getLabel() {
		return "qna";
	}

	public boolean willArchiveMerge() {
		return false;
	}

	public void setDao(QnaDao dao) {
		this.dao = dao;
	}

	public void setCategoryLogic(CategoryLogic categoryLogic) {
		this.categoryLogic = categoryLogic;
	}
	
	public void setOptionsLogic(OptionsLogic optionsLogic) {
		this.optionsLogic = optionsLogic;
	}
	
	public void setAttachmentLogic(AttachmentLogic attachmentLogic) {
		this.attachmentLogic = attachmentLogic;
	}
	
	// NOT IMPLEMENTED
	
	public String getEntityPrefix() {
		return null;
	}
	
	public String merge(String siteId, Element root, String archivePath,
			String fromSiteId, Map attachmentNames, Map userIdTrans,
			Set userListAllowImport) {
		return null;
	}

	public boolean parseEntityReference(String reference, Reference ref) {
		return false;
	}
	
	public String archive(String siteId, Document doc, Stack stack,
			String archivePath, List attachments) {
		return null;
	}

	public Entity getEntity(Reference ref) {
		return null;
	}

	public Collection<String> getEntityAuthzGroups(Reference ref, String userId) {
		return null;
	}

	public String getEntityDescription(Reference ref) {
		return null;
	}

	public ResourceProperties getEntityResourceProperties(Reference ref) {
		return null;
	}

	public String getEntityUrl(Reference ref) {
		return null;
	}

	public HttpAccess getHttpAccess() {
		return null;
	}

	public void transferCopyEntities(String fromContext, String toContext,
			List ids, boolean cleanup) {
		if (cleanup) {
			// Delete all old categories from toContext
			String toLocation = "/site/" + toContext;
			List<QnaCategory> categories = categoryLogic.getCategoriesForLocation(toLocation);
			for (QnaCategory category : categories) {
				categoryLogic.removeCategory(category.getId(), toLocation);
			}
		}
		
		// Copy everything from fromContext to toContext
		transferCopyEntities(fromContext, toContext, ids);		
	}

}
