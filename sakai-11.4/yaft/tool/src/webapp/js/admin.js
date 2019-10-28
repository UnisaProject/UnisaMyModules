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
(function()
{
	var arg = YaftUtils.getParameters();
	
	YaftUtils.render('yaft_home_link_template',arg,'yaft_home_link');
	
	jQuery.ajax(
	{
	   	url : "/portal/tool/" + arg.placementId + "/sites",
	   	dataType : "json",
	   	async : false,
		cache: false,
	  	success : function(sites)
		{
			for(var i = 0;i<sites.length;i++)
			{
				var c = sites[i].title[0].toUpperCase();
				if(c in yaftSiteTable)
				{
					yaftSiteTable[c].push(sites[i]);
				}
				else
				{
					yaftSiteTable[c] = [sites[i]];
				}
			}
			var data = {"t":yaftSiteTable};
			data["placementId"] = arg.placementId;

			YaftUtils.render('yaft_admin_template',data,'yaft_admin');
			YaftUtils.render('yaft_sites_list_template',data,'yaft_sites_list');
	 		$(document).ready(function() {setMainFrameHeight(window.frameElement.id);});
		},
		error : function(reqObject,status)
		{
			alert("Failed to get sites. Reason: " + status);
		}
	});
})();

function yaftShowSites(letter)
{
	$(".yaftSites").hide();
	$("#" + letter + "_sites").show();
	$(document).ready(function() {setMainFrameHeight(window.frameElement.id);});
}
