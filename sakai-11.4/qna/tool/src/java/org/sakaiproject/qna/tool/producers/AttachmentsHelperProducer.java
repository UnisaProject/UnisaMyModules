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
package org.sakaiproject.qna.tool.producers;

import java.util.ArrayList;
import java.util.List;

import org.sakaiproject.qna.tool.params.AttachmentsHelperParams;
import org.sakaiproject.qna.tool.params.QuestionTextParams;
import org.sakaiproject.rsf.helper.HelperViewParameters;
import org.springframework.web.multipart.MultipartResolver;

import uk.org.ponder.rsf.components.UICommand;
import uk.org.ponder.rsf.components.UIContainer;
import uk.org.ponder.rsf.components.UIOutput;
import uk.org.ponder.rsf.flow.ARIResult;
import uk.org.ponder.rsf.flow.ActionResultInterceptor;
import uk.org.ponder.rsf.flow.jsfnav.NavigationCase;
import uk.org.ponder.rsf.flow.jsfnav.NavigationCaseReporter;
import uk.org.ponder.rsf.view.ComponentChecker;
import uk.org.ponder.rsf.view.ComponentProducer;
import uk.org.ponder.rsf.view.ViewComponentProducer;
import uk.org.ponder.rsf.viewstate.ViewParameters;
import uk.org.ponder.rsf.viewstate.ViewParamsReporter;

public class AttachmentsHelperProducer implements ViewComponentProducer, ViewParamsReporter, 
													ActionResultInterceptor, NavigationCaseReporter {
	//removed 
	//	DynamicNavigationCaseReporter,

	public static final String VIEW_ID = "add_attachment";
	public MultipartResolver multipartResolver;
	
	public String getViewID() {
		return VIEW_ID;
	}

    /**
     * @see ComponentProducer#fillComponents(UIContainer, ViewParameters, ComponentChecker)
     */
	public void fillComponents(UIContainer tofill, ViewParameters viewparams,
			ComponentChecker checker) {
	    //AttachmentsHelperParams params = (AttachmentsHelperParams) viewparams;
	    
	    UIOutput.make(tofill, HelperViewParameters.HELPER_ID, "sakai.filepicker");
	    UICommand.make(tofill, HelperViewParameters.POST_HELPER_BINDING).setReturn("processed");
	}

	public ViewParameters getViewParameters() {
		return new AttachmentsHelperParams();
	}

	@SuppressWarnings("unchecked")
	public List reportNavigationCases() {
	    List l = new ArrayList();
	    l.add(new NavigationCase("processed", new QuestionTextParams(AskQuestionProducer.VIEW_ID, null)));
	    return l;
	}

	/**
	 * @see ActionResultInterceptor#interceptActionResult(ARIResult, ViewParameters, Object)
	 */
	public void interceptActionResult(ARIResult result,
			ViewParameters incoming, Object actionReturn) {
		if (result.resultingView instanceof QuestionTextParams) {
			QuestionTextParams resultParams = ((QuestionTextParams)result.resultingView);
			AttachmentsHelperParams incomingParams = ((AttachmentsHelperParams)incoming);
			resultParams.questionText = incomingParams.questionText;
			
			if (incomingParams.returnToViewID != null) {
				resultParams.viewID = incomingParams.returnToViewID;
			}
			if (incomingParams.questionid != null) {
				resultParams.questionid = incomingParams.questionid;
			}
		}
	}

}
