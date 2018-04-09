/*
 * Copyright 2009 The Sakai Foundation
 *
 * Licensed under the Educational Community License, Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
/* Stuff that we always expect to be setup */
var yaftPlacementId = null;

(function()
{
	var arg = SAKAIUTILS.getParameters();
	
	if(!arg || !arg.placementId)
	{
		alert('The placement id MUST be supplied as a page parameter');
		return;
	}
	
	if(arg['language']) {
    	$.localise('yaft-translations',{language:arg['language'],loadBase: true});
    } else {
   		$.localise('yaft-translations');
   	}
	
	// Stuff that we always expect to be setup
	yaftPlacementId = arg.placementId;
	
	var activeDiscussions = null;
		
	$.ajax(
	{
	  	url : "/portal/tool/" + yaftPlacementId + "/activeDiscussions.json",
		dataType : "json",
		cache: false,
		async : false,
		success : function(ads)
		{
			activeDiscussions = ads;
		},
		error : function(xmlHttpRequest,status)
		{
		}
	});
			
	SAKAIUTILS.renderTrimpathTemplate('synoptic_yaft_content_template',{'discussions':activeDiscussions},'synoptic_yaft_content');
	
	$(document).ready(function() {
			$("#yaft_active_discussion_table").tablesorter({
	 							cssHeader:'yaftSortableTableHeader',
	 							cssAsc:'yaftSortableTableHeaderSortUp',
	 							cssDesc:'yaftSortableTableHeaderSortDown',
	 							headers:
	 								{
	 									2:{sorter: "isoDate"}
	 								},
	 							widgets: ['zebra']
	 						});

			setMainFrameHeight(window.frameElement.id);
		});
})();
