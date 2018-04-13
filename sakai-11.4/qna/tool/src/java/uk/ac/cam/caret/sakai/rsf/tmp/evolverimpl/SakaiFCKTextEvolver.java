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
/*
 * There's a bug in the SakaiFCKTextEvolver, this is the trunk version and can be refactored
 * out when it's fixed upstream
 */
package uk.ac.cam.caret.sakai.rsf.tmp.evolverimpl;

import org.sakaiproject.content.api.ContentHostingService;

import uk.org.ponder.htmlutil.HTMLUtil;
import uk.org.ponder.rsf.components.UIInput;
import uk.org.ponder.rsf.components.UIJointContainer;
import uk.org.ponder.rsf.components.UIVerbatim;
import uk.org.ponder.rsf.evolvers.TextInputEvolver;

public class SakaiFCKTextEvolver implements TextInputEvolver {
	public static final String COMPONENT_ID = "sakai-FCKEditor:";

	private String context;

	private ContentHostingService contentHostingService;

	public void setContext(String context) {
		this.context = context;
	}

	public void setContentHostingService(ContentHostingService contentHostingService) {
		this.contentHostingService = contentHostingService;
	}
	
	/**
	 * Make input be FCK text editor
	 */
	public UIJointContainer evolveTextInput(UIInput toevolve) {
		toevolve.parent.remove(toevolve);
		UIJointContainer joint = new UIJointContainer(toevolve.parent, toevolve.ID, COMPONENT_ID);

		toevolve.ID = SEED_ID; // must change ID while unattached
		joint.addComponent(toevolve);
		String collectionID = "".equals(context) ? "" : contentHostingService.getSiteCollection(context);
		String js = HTMLUtil.emitJavascriptCall(
			"SakaiProject.fckeditor.initializeEditor",
			new String[] {
				toevolve.getFullID(),
				collectionID
			}
		);
		UIVerbatim.make(joint, "textarea-js", js);
		return joint;
	}

}
