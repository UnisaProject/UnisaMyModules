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
var SAKAIUTILS = (function ($) {

    var my = {};

	my.getProfileMarkup = function (userId) {
		var profile = '';

		jQuery.ajax( {
	       	url : "/direct/profile/" + userId + "/formatted",
	       	dataType : "html",
	       	async : false,
			cache: false,
		   	success : function(p) {
				profile = p;
			},
			error : function(xmlHttpRequest,stat,error) {
				alert("Failed to get profile markup. Status: " + stat + ". Error: " + error);
			}
	   	});

		return profile;
	};

	my.renderTrimpathTemplate = function (templateName,contextObject,output) {
		var templateNode = document.getElementById(templateName);
		var firstNode = templateNode.firstChild;
		var template = null;

		if ( firstNode && ( firstNode.nodeType === 8 || firstNode.nodeType === 4))
  			template = templateNode.firstChild.data.toString();
		else
   			template = templateNode.innerHTML.toString();

		var trimpathTemplate = TrimPath.parseTemplate(template,templateName);

   		var render = trimpathTemplate.process(contextObject);

		if (output)
			document.getElementById(output).innerHTML = render;

		return render;
	};

	my.setupFCKEditor = function (textarea_id,width,height,toolbarSet) {
	
		sakai.editor.launch(textarea_id,{},width,height);
		
        if(window.frameElement) {
            setMainFrameHeight(window.frameElement.id);
        }
	};
	
	my.setupCKEditor = function(textarea_id,width,height,toolbarSet) {
	var instance = CKEDITOR.instances[textarea_id];
        if(typeof instance !== 'undefined') {
        	delete CKEDITOR.instances[textarea_id];
        }

		sakai.editor.launch(textarea_id,{},width,height);
		
		CKEDITOR.instances[textarea_id].on('instanceReady',function (e) {
            if(window.frameElement) {
                setMainFrameHeight(window.frameElement.id);
            }
        });
	}
	
	my.setupWysiwygEditor = function(editorId,textarea_id,width,height,toolbarSet) {
		if ('FCKeditor' === editorId) {
			this.setupFCKEditor(textarea_id,width,height,toolbarSet);
		} else if ('ckeditor' === editorId) {
			this.setupCKEditor(textarea_id,width,height,toolbarSet);
		}
	}
	
	my.getWysiwygEditor = function(editorId,textarea_id) {
		if ('FCKeditor' === editorId) {
			return FCKeditorAPI.GetInstance(textarea_id);
		} else if ('ckeditor' === editorId) {
			return CKEDITOR.instances[textarea_id];
		}
	}
	
	my.getEditorData = function(editorId,textarea_id) {
		if ('FCKeditor' === editorId) {
			return this.getWysiwygEditor(editorId,textarea_id).GetData();
		} else if ('ckeditor' === editorId) {
			return this.getWysiwygEditor(editorId,textarea_id).getData();
		} else {
            return $('#' + textarea_id).val();
        }
	}

	my.updateEditorElement = function(editorId,textarea_id) {
		if ('FCKeditor' === editorId) {
			return this.getWysiwygEditor(editorId,textarea_id).UpdateLinkedField();
		} else if ('ckeditor' === editorId) {
			return this.getWysiwygEditor(editorId,textarea_id).updateElement();
		}
	}

    return my;

}(jQuery));
