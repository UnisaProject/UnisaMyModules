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
package org.sakaiproject.qna.logic.impl.sms;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.sakaiproject.qna.logic.CategoryLogic;
import org.sakaiproject.qna.logic.ExternalLogic;
import org.sakaiproject.qna.logic.OptionsLogic;
import org.sakaiproject.qna.logic.PermissionLogic;
import org.sakaiproject.qna.logic.QnaBundleLogic;
import org.sakaiproject.qna.logic.QuestionLogic;
import org.sakaiproject.qna.model.QnaOptions;
import org.sakaiproject.qna.model.QnaQuestion;
import org.sakaiproject.sms.logic.incoming.ParsedMessage;
import org.sakaiproject.sms.logic.incoming.ShortMessageCommand;

/**
 * Post a question to a specific site Usage: QUESTION <site> <question text>
 * 
 * @author wilhelm@psybergate.co.za
 * 
 */
public class QuestionSmsCommand implements ShortMessageCommand {

	private static Log log = LogFactory.getLog(QuestionSmsCommand.class);
	private static final String QUESTION = "QUESTION";
	private static final String QUESTION_ALIAS = "Q";

	private QuestionLogic questionLogic;
	private QnaBundleLogic qnaBundleLogic;
	private OptionsLogic optionsLogic;
	private CategoryLogic categoryLogic;
	private ExternalLogic externalLogic;
	private PermissionLogic permissionLogic;
	
	public PermissionLogic getPermissionLogic() {
		return permissionLogic;
	}

	public void setPermissionLogic(PermissionLogic permissionLogic) {
		this.permissionLogic = permissionLogic;
	}

	public void setExternalLogic(ExternalLogic externalLogic) {
		this.externalLogic = externalLogic;
	}

	public void setQuestionLogic(QuestionLogic questionLogic) {
		this.questionLogic = questionLogic;
	}

	public void setOptionsLogic(OptionsLogic optionsLogic) {
		this.optionsLogic = optionsLogic;
	}

	public void setQnaBundleLogic(QnaBundleLogic qnaBundleLogic) {
		this.qnaBundleLogic = qnaBundleLogic;
	}

	public void setCategoryLogic(CategoryLogic categoryLogic) {
		this.categoryLogic = categoryLogic;
	}

	public String execute(ParsedMessage message, String messageType, String mobileNr) {
		
		String siteId = message.getSite();
		String userId = message.getIncomingUserId();
		String body[] = message.getBodyParameters();
		String serviceName = externalLogic.getServiceName();
		
		log.debug(getCommandKey() + " command called with parameters: " + message);

		if (body == null || body.length == 0 || body[0] == null || "".equals(body[0].trim())) {
			return qnaBundleLogic.getString("qna.sms.no-question-text");
		} else {
			String siteRef = "/site/" + siteId;
			String siteTitle = message.getSiteTitle();

			QnaQuestion question = new QnaQuestion();
			question.setQuestionText(body[0]);
			question.setOwnerMobileNr(mobileNr);
			
			// Always default
			question.setCategory(categoryLogic.getDefaultCategory(siteRef));
			QnaOptions options = optionsLogic.getOptionsForLocation(siteRef);

			// If we have a userid but the user would not have permission to post
			// but anonymous posting is allowed, anonymize the user
			
			if (options.getAllowUnknownMobile() && !permissionLogic.canAddNewQuestion(siteRef, userId)) {
					userId = null;
			}
			
			try {
				if (userId != null) {
					questionLogic.saveQuestion(question, siteRef, userId);
				} else {
					if (options.getAllowUnknownMobile()) {
						questionLogic.saveQuestion(question, siteRef, null);
					} else {
						return qnaBundleLogic
								.getFormattedMessage("qna.sms.anonymous-not-allowed",
										new Object[] { siteTitle, serviceName }	);
					}
				}
				
			} catch (SecurityException se) {
				return qnaBundleLogic.getFormattedMessage(
						"qna.sms.save-question-denied", new Object[] { siteTitle });
			}

			String smsNumber = externalLogic.getSmsNumber();
			
			if (options.getSmsNotification()) {
				return qnaBundleLogic.getFormattedMessage(
					"qna.sms.question-posted.replies-sent",
					new Object[] { siteTitle, question.getId().toString() });
			} else {
				return qnaBundleLogic.getFormattedMessage(
						"qna.sms.question-posted.no-replies",
						new Object[] { siteTitle, question.getId().toString(), smsNumber });
			}
		}
	}

	public String[] getAliases() {
		return new String[] { QUESTION_ALIAS };
	}

	public String getCommandKey() {
		return QUESTION;
	}

	public String getHelpMessage(String messageType) {
		return qnaBundleLogic.getString("qna.sms.question-help");
	}

	public int getBodyParameterCount() {
		return 1;
	}

	public boolean isEnabled() {
		return true;
	}

	public boolean isVisible() {
		return true;
	}

	public boolean requiresSiteId() {
		return true;
	}

	public boolean requiresUserId() {
		return false;
	}
	
	public boolean canExecute(ParsedMessage arg0) {
		return true;
	}

}
