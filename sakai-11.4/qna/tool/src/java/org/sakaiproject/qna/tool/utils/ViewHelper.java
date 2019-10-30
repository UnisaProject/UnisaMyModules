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
package org.sakaiproject.qna.tool.utils;

import org.sakaiproject.qna.logic.ExternalLogic;
import org.sakaiproject.qna.logic.OptionsLogic;
import org.sakaiproject.qna.logic.PermissionLogic;
import org.sakaiproject.qna.model.constants.QnaConstants;
import org.sakaiproject.qna.tool.constants.SortByConstants;
import org.sakaiproject.qna.tool.constants.ViewTypeConstants;
import org.sakaiproject.tool.api.SessionManager;
import org.sakaiproject.tool.api.ToolSession;

public class ViewHelper {
    
	public static final String VIEW_TYPE_ATTR = "view-type-attr";  
	public static final String SORT_BY_ATTR =  "sort-by-attr";
	public static final String SORT_DIR_ATTR= "sort-dir-attr";
	
	private OptionsLogic optionsLogic;
    private SessionManager sessionManager;
	private ExternalLogic externalLogic;
	private PermissionLogic permissionLogic;
    
    public void setSessionManager(SessionManager sessionManager) {
    	  this.sessionManager = sessionManager;
      }
    
    public void setOptionsLogic(OptionsLogic optionsLogic) {
    	this.optionsLogic = optionsLogic;
    }
    
    public void setExternalLogic(ExternalLogic externalLogic) {
    	this.externalLogic = externalLogic;
    }

    public void setPermissionLogic(PermissionLogic permissionLogic) {
    	this.permissionLogic = permissionLogic;
    }
    
    public String getViewType() {
		ToolSession toolSession = sessionManager.getCurrentToolSession();
    	if (toolSession.getAttribute(VIEW_TYPE_ATTR) != null) {
    		return (String)toolSession.getAttribute(VIEW_TYPE_ATTR);
    	} else {
    		String defaultView = optionsLogic.getOptionsForLocation(externalLogic.getCurrentLocationId()).getDefaultStudentView();
    		String returnView = ViewTypeConstants.CATEGORIES;
    		
    		if (defaultView.equals(QnaConstants.CATEGORY_VIEW)) {
				returnView = ViewTypeConstants.CATEGORIES;
			} else if (defaultView.equals(QnaConstants.MOST_POPULAR_VIEW)){
				if (permissionLogic.canUpdate(externalLogic.getCurrentLocationId(), externalLogic.getCurrentUserId())) {
					returnView = ViewTypeConstants.ALL_DETAILS; // Admin users see all details
				} else {
					returnView = ViewTypeConstants.STANDARD;
				}
			}
    		toolSession.setAttribute(VIEW_TYPE_ATTR, returnView);
    		return returnView;
    	}
    }
    
    public String getSortBy() {
		ToolSession toolSession = sessionManager.getCurrentToolSession();
		if (toolSession.getAttribute(SORT_BY_ATTR) != null) {
			return (String)toolSession.getAttribute(SORT_BY_ATTR);
		} else {
    		String defaultView = optionsLogic.getOptionsForLocation(externalLogic.getCurrentLocationId()).getDefaultStudentView();
    		if (QnaConstants.CATEGORY_VIEW.equals(defaultView)) {
    			return null;
    		} else {
    			if (externalLogic.isUserAllowedInLocation(externalLogic.getCurrentUserId(), "qna.update", externalLogic.getCurrentLocationId())) {
    				toolSession.setAttribute(SORT_BY_ATTR, SortByConstants.CREATED);
    				return SortByConstants.CREATED; 
				} else {
					toolSession.setAttribute(SORT_BY_ATTR, SortByConstants.VIEWS);
					return SortByConstants.VIEWS;
				}
    			
    		}
		}
    }
    
    public String getSortDir() {
    	ToolSession toolSession = sessionManager.getCurrentToolSession();
		
    	if (getViewType().equals(ViewTypeConstants.CATEGORIES)) {
    		return SortByConstants.SORT_DIR_ASC;
    	}
    	
    	if (toolSession.getAttribute(SORT_DIR_ATTR) != null) {
			return (String)toolSession.getAttribute(SORT_DIR_ATTR);
		} else {
			if (toolSession.getAttribute(SORT_BY_ATTR) != null && SortByConstants.CREATED.equals(toolSession.getAttribute(SORT_BY_ATTR))) {
				toolSession.setAttribute(SORT_DIR_ATTR, SortByConstants.SORT_DIR_DESC);
				return  SortByConstants.SORT_DIR_DESC;
			} else if (toolSession.getAttribute(SORT_BY_ATTR) != null && SortByConstants.VIEWS.equals(toolSession.getAttribute(SORT_BY_ATTR))) {
				toolSession.setAttribute(SORT_DIR_ATTR, SortByConstants.SORT_DIR_DESC);
				return  SortByConstants.SORT_DIR_DESC;
			}
			
			
			toolSession.setAttribute(SORT_DIR_ATTR, SortByConstants.SORT_DIR_ASC);
			return  SortByConstants.SORT_DIR_ASC;
		}
    }
    
    public void setupSession(String viewType, String sortBy, String sortDir) {
		ToolSession toolSession = sessionManager.getCurrentToolSession();
		if (viewType != null) {
			toolSession.setAttribute(VIEW_TYPE_ATTR, viewType);
		}

		if (sortBy != null) {
			toolSession.setAttribute(SORT_BY_ATTR, sortBy);
		}

		if (sortDir != null) {
			toolSession.setAttribute(SORT_DIR_ATTR, sortDir);
		}
    }
}
