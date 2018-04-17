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


public class CategoryParams extends SimpleReturnToParams {

	public String id;
	public String number;
	public String categoryText;
	public String[] categoryids;

	public CategoryParams() {
		number = "1";
	}

	public CategoryParams(String viewID) {
		this.viewID = viewID;
	}

	public CategoryParams(String viewID, String number) {
		this.viewID = viewID;
		this.number = number;
	}

	public CategoryParams(String viewID, String number, String categoryText) {
		this.viewID = viewID;
		this.number = number;
		this.categoryText = categoryText;
	}

	public CategoryParams(String viewID, String number, String categoryText, String id) {
		this.viewID = viewID;
		this.number = number;
		this.categoryText = categoryText;
		this.id = id;
	}
	
	public CategoryParams(String viewID, String number, String categoryText, String id, String returnToViewID) {
		this(viewID,number,categoryText,id);
		this.returnToViewID = returnToViewID;
	}
}