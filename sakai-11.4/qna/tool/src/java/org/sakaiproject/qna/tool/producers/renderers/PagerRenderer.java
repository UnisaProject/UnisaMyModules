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
package org.sakaiproject.qna.tool.producers.renderers;


import uk.org.ponder.messageutil.MessageLocator;
import uk.org.ponder.rsf.components.UIBoundList;
import uk.org.ponder.rsf.components.UIContainer;
import uk.org.ponder.rsf.components.UIForm;
import uk.org.ponder.rsf.components.UIInput;
import uk.org.ponder.rsf.components.UIJointContainer;
import uk.org.ponder.rsf.components.UIMessage;
import uk.org.ponder.rsf.components.UISelect;
import uk.org.ponder.rsf.components.decorators.DecoratorList;
import uk.org.ponder.rsf.components.decorators.UIFreeAttributeDecorator;
import uk.org.ponder.rsf.viewstate.ViewParameters;
import java.util.HashMap;
import java.util.Map;

import org.sakaiproject.qna.logic.ExternalLogic;
import org.sakaiproject.qna.tool.params.SortPagerViewParams;
import org.sakaiproject.qna.tool.producers.QuestionsListProducer;

public class PagerRenderer {

	private MessageLocator messageLocator;
	private ExternalLogic externalLogic;
	
	public static final int DEFAULT_START_COUNT = 20;
	
	public Integer currentStart = 0;
	public Integer currentCount = DEFAULT_START_COUNT;		//actually set in the pager view parameters :-)
	public Integer totalCount = 16;
	
	/**
	 * Creates the pager
	 * 
	 * @param tofill			{@link UIContainer} to fill
	 * @param divID				ID of div
	 * @param currentViewID		Current ID being viewed
	 * @param viewparams		View Parameters
	 * @param totalCount		Total count
	 */
	@SuppressWarnings("unchecked")
	public void makePager(UIContainer tofill, String divID, String currentViewID, ViewParameters viewparams, Integer totalCount) {
    	
		SortPagerViewParams pagerparams = (SortPagerViewParams) viewparams;

    	//set vars
    	this.currentCount = pagerparams.current_count;
    	this.currentStart = pagerparams.current_start;
    	this.totalCount = totalCount;
    	
        UIJointContainer joint = new UIJointContainer(tofill, divID, "pagerDivContainer:", ""+1);
        
        //Currently Viewing
        UIMessage.make(joint, "pager_viewing", "qna.pager.viewing", 
        		new Object[] {this.getViewingStart(), this.getViewingEnd(), this.getViewingTotal()} );
        
        //Form
        UIForm form = UIForm.make(joint, "pager_form");
                
		//Paging Buttons
		////////////////////
		String href_params = "";
		//If we are on the Question list page... add in the view params to keep the sorting accurate
		if (QuestionsListProducer.VIEW_ID.equals(currentViewID)){
			href_params = "sortDir=" + pagerparams.sortDir + "&sortBy=" + pagerparams.sortBy + "&";
		}		
		//build url
		String url = externalLogic.getQuestionViewUrl(currentViewID);

		//Select Box
        UISelect select_box = UISelect.make(form, "pager_select_box");
        UIInput selection = new UIInput();
        selection.setValue(this.getCurrentSelect());
        select_box.selection = selection;
        UIBoundList comboValues = new UIBoundList();
        comboValues.setValue(new String[] {"5","10","20","50","100","200"});
        select_box.optionlist = comboValues;
		UIBoundList comboNames = new UIBoundList();
		comboNames.setValue(new String[] {messageLocator.getMessage("qna.pager.5-items"),
										  messageLocator.getMessage("qna.pager.10-items"),
										  messageLocator.getMessage("qna.pager.20-items"),
										  messageLocator.getMessage("qna.pager.50-items"),
										  messageLocator.getMessage("qna.pager.100-items"),
										  messageLocator.getMessage("qna.pager.200-items")});
		select_box.optionnames = comboNames;
		Map attrmap = new HashMap(); 
		attrmap.put("onchange", "location.href=\"" + url + "?" + href_params + "current_start=" + 
				currentStart.toString() + "&current_count=\" + jQuery(this).val()");
		select_box.decorators = new DecoratorList(new UIFreeAttributeDecorator(attrmap)); 
		
		
		//first page
		UIInput first_page = UIInput.make(form, "pager_first_page", null, messageLocator.getMessage("qna.pager.pager_first_page"));
		attrmap = new HashMap();
		attrmap.put("onclick", "location.href=\"" + url + "?" + href_params + "current_start=" + 
					this.goToFirstPage() + "&current_count=" + this.getCurrentSelect() + "\"");
		first_page.decorators = new DecoratorList(new UIFreeAttributeDecorator(attrmap));
				
		//previous page
		UIInput prev_page = UIInput.make(form, "pager_prev_page", null, messageLocator.getMessage("qna.pager.pager_prev_page"));
		attrmap = new HashMap();
		attrmap.put("onclick", "location.href=\"" + url + "?" + href_params + "current_start=" + 
				this.goToPrevPage() + "&current_count=" + this.getCurrentSelect() + "\"");
		prev_page.decorators = new DecoratorList(new UIFreeAttributeDecorator(attrmap));

		//next page
		UIInput next_page = UIInput.make(form, "pager_next_page", null, messageLocator.getMessage("qna.pager.pager_next_page"));
		attrmap = new HashMap();
		attrmap.put("onclick", "location.href=\"" + url + "?" + href_params + "current_start=" + 
				this.goToNextPage() + "&current_count=" + this.getCurrentSelect() + "\"");
		next_page.decorators = new DecoratorList(new UIFreeAttributeDecorator(attrmap));
		
		//last button
		UIInput last_page = UIInput.make(form, "pager_last_page", null, messageLocator.getMessage("qna.pager.pager_last_page"));
		attrmap = new HashMap();
		attrmap.put("onclick", "location.href=\"" + url + "?" + href_params + "current_start=" + 
				this.goToLastPage() + "&current_count=" + this.getCurrentSelect() + "\"");
		last_page.decorators = new DecoratorList(new UIFreeAttributeDecorator(attrmap));
		
		
		//Disable buttons not in use
		Map disabledAttr = new HashMap();
		disabledAttr.put("disabled", "disabled");
		DecoratorList disabledDecoratorList = new DecoratorList(new UIFreeAttributeDecorator(disabledAttr));
		
		if (currentStart == 0){
			//disable if on first page
			first_page.decorators = disabledDecoratorList;
			prev_page.decorators = disabledDecoratorList;
		}
		if ((currentStart + currentCount) >= totalCount){
			//disable if on last page
			next_page.decorators = disabledDecoratorList;
			last_page.decorators = disabledDecoratorList;
		}
        
    }
    
    public void setMessageLocator(MessageLocator messageLocator) {
        this.messageLocator = messageLocator;
    }
    
    public void setExternalLogic(ExternalLogic externalLogic) {
    	this.externalLogic = externalLogic;
    }
    
    
    //Private Methods
	private String getViewingStart(){
		return Integer.toString(currentStart + 1);
	}
	
	private String getViewingEnd(){
		return Integer.toString((totalCount < (currentStart + currentCount)) ? totalCount : (currentStart + currentCount));
	}
	
	private String getViewingTotal(){
		return totalCount.toString();
	}
	
	private String getCurrentSelect(){
		return currentCount.toString();
	}
	
	private String goToFirstPage(){
		Integer newCurrentStart = 0;
		return newCurrentStart.toString();
	}
	
	private String goToPrevPage(){
		Integer newCurrentStart = currentStart - currentCount;
		if (newCurrentStart < 0) return this.goToFirstPage();
		return newCurrentStart.toString();
	}
	
	private String goToNextPage(){
		Integer newCurrentStart = currentStart + currentCount;
		if (newCurrentStart > totalCount) return this.goToLastPage();
		return newCurrentStart.toString();
	}
	
	private String goToLastPage(){
		Integer newCurrentStart = 0;
		if (totalCount > currentCount){
			newCurrentStart = totalCount - (totalCount % currentCount);
		} else {
			newCurrentStart = 0;
		}
		return newCurrentStart.toString(); 
	}
}
