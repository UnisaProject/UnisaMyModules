/*
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
/**
 * Expands text and textarea elements while new characters are typed to the a miximum width
 *
 * @name Expander
 * @description Expands text and textarea elements while new characters are typed to the a miximum width
 * @param Mixed limit integer if only expands in width, array if expands in width and height
 * @type jQuery
 * @cat Plugins/Interface
 * @author Stefan Petre
 */

jQuery.iExpander =
{
	helper : null,
	expand : function()
	{
		
		text = this.value;
		if (!text)
			return;
		style = {
			fontFamily: jQuery(this).css('fontFamily')||'',
			fontSize: jQuery(this).css('fontSize')||'',
			fontWeight: jQuery(this).css('fontWeight')||'',
			fontStyle: jQuery(this).css('fontStyle')||'',
			fontStretch: jQuery(this).css('fontStretch')||'',
			fontVariant: jQuery(this).css('fontVariant')||'',
			letterSpacing: jQuery(this).css('letterSpacing')||'',
			wordSpacing: jQuery(this).css('wordSpacing')||''
		};
		jQuery.iExpander.helper.css(style);
		html = jQuery.iExpander.htmlEntities(text);
		html = html.replace(new RegExp( "\\n", "g" ), "<br />");
		jQuery.iExpander.helper.html('pW');
		spacer = jQuery.iExpander.helper.get(0).offsetWidth;
		jQuery.iExpander.helper.html(html);
		width = jQuery.iExpander.helper.get(0).offsetWidth + spacer;
		if (this.Expander.limit && width > this.Expander.limit[0]) {
			width = this.Expander.limit[0];
		}
		this.style.width = width + 'px';
		if (this.tagName == 'TEXTAREA') {
			height = jQuery.iExpander.helper.get(0).offsetHeight + spacer;
			if (this.Expander.limit && height > this.Expander.limit[1]) {
				height = this.Expander.limit[1];
			}
			this.style.height = height + 'px';
		}
	},
	htmlEntities : function(text)
	{ 
		entities = {
			'&':'&amp;',
			'<':'&lt;',
			'>':'&gt;',
			'"':'&quot;'
		};
		for(i in entities) {
			text = text.replace(new RegExp(i,'g'),entities[i]);
		}
		return text;
	},
	build : function(limit)
	{
		if (jQuery.iExpander.helper == null) {
			jQuery('body', document).append('<div id="expanderHelper" style="position: absolute; top: 0; left: 0; visibility: hidden;"></div>');
			jQuery.iExpander.helper = jQuery('#expanderHelper');
		}
		return this.each(
			function()
			{
				if (/TEXTAREA|INPUT/.test(this.tagName)) {
					if (this.tagName == 'INPUT') {
						elType = this.getAttribute('type');
						if (!/text|password/.test(elType)) {
							return;
						}
					}
					if (limit && (limit.constructor == Number || (limit.constructor == Array && limit.length == 2))) {
						if (limit.constructor == Number)
							limit = [limit, limit];
						else {
							limit[0] = parseInt(limit[0])||400;
							limit[1] = parseInt(limit[1])||400;
						}
						this.Expander = {
							limit : limit
						};
					}
					jQuery(this)
						.blur(jQuery.iExpander.expand)
						.keyup(jQuery.iExpander.expand)
						.keypress(jQuery.iExpander.expand);
					jQuery.iExpander.expand.apply(this);
				}
			}
		);			
	}
};

jQuery.fn.Autoexpand = jQuery.iExpander.build;