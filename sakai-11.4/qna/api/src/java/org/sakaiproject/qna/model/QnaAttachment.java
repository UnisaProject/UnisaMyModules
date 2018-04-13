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
package org.sakaiproject.qna.model;

/**
 *	QnaAttachment entity 
 *
 */
public class QnaAttachment {
	
	// Internal id in database
	private String id; 
	
	// Question attachment is linked to
	private QnaQuestion question;
	
	// ID of attachment in content hosting
	private String attachmentId;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public QnaQuestion getQuestion() {
		return question;
	}
	
	public void setQuestion(QnaQuestion question) {
		this.question = question;
	}
	
	public String getAttachmentId() {
		return attachmentId;
	}
	
	public void setAttachmentId(String attachmentId) {
		this.attachmentId = attachmentId;
	}
}
