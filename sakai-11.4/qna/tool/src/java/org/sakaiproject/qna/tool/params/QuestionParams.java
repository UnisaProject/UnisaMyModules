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
package org.sakaiproject.qna.tool.params;


public class QuestionParams extends SimpleReturnToParams {

	public String questionid;
	public String[] questionids;
	public boolean direct = false; // If this is direct reference
	
	public QuestionParams() {}

	public QuestionParams(String viewid) {
		this.viewID = viewid;
	}

	public QuestionParams(String viewid, String questionid) {
		this.viewID = viewid;
		this.questionid = questionid;
	}
	
	public QuestionParams(String viewid, String questionid, String returnToViewID) {
		this(viewid,questionid);
		this.returnToViewID = returnToViewID;
	}
	
	public QuestionParams(String viewid, String questionid, boolean direct) {
		this.viewID = viewid;
		this.questionid = questionid;
		this.direct = direct;
	}

	public QuestionParams(String viewid, String[] questionids) {
		this.viewID = viewid;
		this.questionids = questionids;
	}
}
