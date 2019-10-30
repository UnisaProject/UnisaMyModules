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
	// Toggle visibility of an element
    function toggle_visibility(id) {
       if (document.getElementById(id).style.display == '')
       	document.getElementById(id).style.display = 'none';
       else
       	document.getElementById(id).style.display = '';
	}

	// Toggle visibility of questions of a certain entry id
	function toggle_questions(entry_id,expand_icon,collapse_icon) {
    	var element = document.getElementById(entry_id);
 		var rows = document.getElementsByTagName("tr");

		var expr = new RegExp(element.id +"question-entry:([0-9]*):$");

		for (i=0;i<rows.length;i++) {
			if (!(rows[i].id == "")) {
				if(rows[i].id.match(expr)) {
					toggle_visibility(rows[i].id);
				}
			}
		}

		// Toggle Icon
		toggle_visibility(expand_icon.id);
		toggle_visibility(collapse_icon.id);

		resizeToolFrame();
    }

    // Used in questions list
    function init_questions_toggle(expand_icon_id,collapse_icon_id,entry_id) {
    	var expand_icon = document.getElementById(expand_icon_id);
    	var collapse_icon = document.getElementById(collapse_icon_id);
    	expand_icon.onclick = function() { toggle_questions(entry_id,expand_icon,collapse_icon);};
    	collapse_icon.onclick = function() { toggle_questions(entry_id,expand_icon,collapse_icon);};


    	var mySpan = document.getElementById(entry_id + "category-entry::category-name");
    	mySpan.onclick = function() { toggle_questions(entry_id,expand_icon,collapse_icon);};
    }

    function change_view(select,form,current_selected) {
    	if ((select.options[select.selectedIndex].value == "CATEGORIES") || (select.options[select.selectedIndex].value == "ALL_DETAILS")) {
			document.location=form.action+"?viewtype="+select.options[select.selectedIndex].value;
    	} else {
    		document.location=form.action+"?viewtype=STANDARD&sortBy="+select.options[select.selectedIndex].value;
    	}
    }

    // View select on question list screen
    function init_view_select(select_id, form_id, options_size, current_selected) {
    	var select = document.getElementById(select_id);
    	var form = document.getElementById(form_id);
		select.onchange = function () { change_view(select,form,current_selected);};
    }

    function toggle_add_answer(link_id,icon_id,div_id) {
	   	toggle_visibility(link_id);
    	toggle_visibility(icon_id);
    	toggle_visibility(div_id);

    	resizeToolFrame();
    }

    // Add an answer in answers screen
    function init_add_answer_toggle(link_id,icon_id,div_id) {
    	var link = document.getElementById(link_id);
    	link.href= "#";
    	link.onclick = function() { toggle_add_answer(link_id,icon_id,div_id);};
    	var icon = document.getElementById(icon_id);
    	icon.onclick = function() { toggle_add_answer(link_id,icon_id,div_id);};
    	icon.style.cursor="pointer";
    }

    function toggle_disabled(element) {
    	if (element.disabled) {
    		element.disabled = false;
    	} else {
    		element.disabled = true;
    	}
    }

    function toggle_mail_notifications_view(site_option,custom_option,update_option,custom_mail_input) {
    	var stay_disabled = document.getElementById("site-contact-stay-disabled");

    	if (!stay_disabled) {
			toggle_disabled(site_option);
    	}
		toggle_disabled(custom_option);
		toggle_disabled(update_option);
		toggle_disabled(custom_mail_input);
    }

    function init_mail_notifications(notification_id,site_option_id,custom_option_id,update_option_id,custom_mail_input_id) {
    	var notification = document.getElementById(notification_id);
		var site_option = document.getElementById(site_option_id);
		var custom_option = document.getElementById(custom_option_id);
		var update_option = document.getElementById(update_option_id);
		var custom_mail_input = document.getElementById(custom_mail_input_id);

		notification.onclick = function() {toggle_mail_notifications_view(site_option,custom_option,update_option,custom_mail_input);};
    	if (!notification.checked) {
    		site_option.disabled = true;
    		custom_option.disabled = true;
    		update_option.disabled = true;
    		custom_mail_input.disabled = true;
    	}
    }

    // Used to make links perform like submit button
    function make_link_call_command(link_id,command_id) {
    	var link = document.getElementById(link_id);
    	var command = document.getElementById(command_id);
    	link.onclick = function() {command.click(); return false;};
    }

    function cancelDelete(formid, bindings) {
		var form = document.getElementById(formid);
		var bindings = document.getElementsByName(bindings);

		var cLength = form.childNodes.length;

		for (var k=0; k<cLength; k++) {
			if (form.childNodes[k].name == 'deletion-binding') {
				var cNode = form.childNodes[k];
				cNode.value = '';
				form.removeChild(cNode);
			}
		}
	}

	function addCategoryInput(div_id, index_value_id, div_to_copy) {
		var div = document.getElementById(div_id);
		var index = document.getElementById(index_value_id);
		var newInput = document.getElementById(div_to_copy).cloneNode(true);
		var cLength  = newInput.childNodes.length-1;
		var indexInt = parseInt(index.value) + 1;
		index.value = indexInt;

		newInput.id = ':'+(indexInt+1)+':categoryInput';

		for (var i=0; i<=cLength; i++)	{
		    if (newInput.childNodes[i].id == 'category-name') {
			    var cNode = newInput.childNodes[i];
			    cNode.value = '';
			    cNode.id = ':'+(indexInt+1)+':category-name';
			    cNode.name = ':'+(indexInt+1)+':category-name';
			}
			if (newInput.childNodes[i].name == 'category-name-fossil') {
				var cNode = newInput.childNodes[i];
				cNode.name = ':'+(indexInt+1)+':category-name-fossil';
				cNode.value = 'istring#{CategoryLocator.new '+(indexInt+1)+'.categoryText}';
			}
			if (newInput.childNodes[i].id == 'remove-cat') {
				var cNode = newInput.childNodes[i];
				cNode.id = ':'+(indexInt+1)+':remove-cat';
				cNode.style.display = '';
			}
	    }

	    if (index.value > 0) {
	    	document.getElementById('remove-cat').style.display = '';
	    }
	    
		div.appendChild(newInput);
		resizeToolFrame();
	}

	function removeCategoryInput(div_id, index_value_id, div_to_remove) {
		var div = document.getElementById(div_id);
		var index = document.getElementById(index_value_id);
		var number = parseInt(div_to_remove.id.charAt(1));
		var cat2Remove;
		if (isNaN(number)) {
			cat2Remove = document.getElementById('categoryInput');
		} else {
			cat2Remove = document.getElementById(':'+div_to_remove.id.charAt(1)+':categoryInput');
		}
		var indexInt = parseInt(index.value) - 1;
		index.value = indexInt;
		div.removeChild(cat2Remove);

		var cLength  = div.childNodes.length-1;
		var catNr = 0;
		for (var i=0; i<=cLength; i++) {
			if (div.childNodes[i].id) {
				var cnID = div.childNodes[i].id;
				if (cnID.substring(cnID.length-13, cnID.length) == 'categoryInput') {
					var grandChildren = document.getElementById(div.childNodes[i].id);
					if (catNr == 0) {
						div.childNodes[i].id = 'categoryInput';
					} else {
						div.childNodes[i].id = ':'+catNr+':categoryInput';
					}

					var ccLength = grandChildren.childNodes.length - 1;
					for (var k=0; k<=ccLength; k++) {
						if (grandChildren.childNodes[k].id) {
							var childNodeID = grandChildren.childNodes[k].id;
							if (childNodeID.substring(childNodeID.length-13, childNodeID.length) == 'category-name') {
							    var cNode = grandChildren.childNodes[k];
							    if (catNr==0) {
								    cNode.id = 'category-name';
								    cNode.name = 'category-name';
							    } else {
							    	cNode.id = ':'+catNr+':category-name';
								    cNode.name = ':'+catNr+':category-name';
								}
							}
							if (childNodeID.substring(childNodeID.length-10, childNodeID.length) == 'remove-cat') {
								var cNode = grandChildren.childNodes[k];
								if (catNr==0) {
									cNode.id = 'remove-cat';
								} else {
									cNode.id = ':'+catNr+':remove-cat';
								}
							}
						}
						if (grandChildren.childNodes[k].name) {
							var childNodeName = grandChildren.childNodes[k].name;
							if (childNodeName.substring(childNodeName.length-20, childNodeName.length) == 'category-name-fossil') {
								var cNode = grandChildren.childNodes[k];
								if (catNr==0) {
									cNode.name = 'category-name-fossil';
								} else {
									cNode.name = ':'+catNr+':category-name-fossil';
								}
								cNode.value = 'istring#{CategoryLocator.new '+(catNr+1)+'.categoryText}';
							}
						}
					}
					catNr += 1;
				}
			}
		}
		
		if (index.value == 0) {
	    	document.getElementById('remove-cat').style.display = 'none';
	    }
		
		resizeToolFrame();
	}

	function initOrganiser() {
		 jQuery( function($) {
            $('#nested-sortable').NestedSortable(
              {
                accept: 'sortable-element-class',
				noNestingClass: 'no-nesting-class',
				autoScroll : true,
				fx:400,
				revert: true,
				onChange : function(serialized) {
				 			var expr = new RegExp("category-entry:([0-9]*):$");

							$('div.sortable-element-class').each(
								function() {
									if (this.id.match(expr)) {
										var categoryId = $(this).children('input:checkbox[name=\"category-sort-order-selection\"]')[0].value;

										$(this).children('span.page-list').children('div.sortable-element-class').children('input:checkbox[name=\"question-category-order-selection\"]').each (
											function() {
												this.value = categoryId;
											}
										)
									}
								}
							)

							// For some insane reason IE decides to uncheck a checkbox if you move it.
							// This is to make sure all checkboxes are ticked on the organise page
							$('input:checkbox').each( function() {
								if (this.checked == false) {
									this.checked = true;
								}
							});
				 }

              }
            );
        });

		$(document).ready(function() {
			$('input:checkbox').each( function() {
				if (this.checked == false) {
					this.checked = true;
				}
			});
		});
	}

	// Makes a button call a link
	function make_button_call_link(button_id, link_id) {
		var button = document.getElementById(button_id);
		var link = document.getElementById(link_id);
		button.onclick = function() { window.location.href = link.href;};
	}

	function resizeToolFrame() {
		setMainFrameHeight(window.frameElement.id);
	}

