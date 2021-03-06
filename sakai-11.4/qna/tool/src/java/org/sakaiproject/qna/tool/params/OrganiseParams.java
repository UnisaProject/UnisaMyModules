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

import uk.org.ponder.rsf.viewstate.SimpleViewParameters;

public class OrganiseParams extends SimpleViewParameters {

	public boolean visible;
	public String id;
	public String type;

	public OrganiseParams() {
	}

	public OrganiseParams(String viewID) {
		this.viewID = viewID;
	}

	public OrganiseParams(String viewID, String type, String id, boolean visible) {
		this.viewID = viewID;
		this.type = type;
		this.id = id;
		this.visible = visible;
	}

}