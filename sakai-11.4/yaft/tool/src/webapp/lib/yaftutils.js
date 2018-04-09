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
var YAFTUTILS = (function($) {
    var my = {};
		
	my.setCurrentForums = function (render) {
		jQuery.ajax( {
	   		url : yaftBaseDataUrl + "forums.json",
	   		dataType : "json",
	   		async : false,
			cache: false,
	  		success : function(forums,textStatus) {
				yaftCurrentForums = forums;

				markReadMessagesInFora();

				addFormattedDatesToFora();

                if(render) {
                    YAFTUTILS.renderCurrentForums();
                }
			},
			error : function(xhr,textStatus,errorThrown) {
				alert("Failed to set current forums. Reason: " + errorThrown);
			}
		});
	};
		
	my.saveForum = function() {
	
		var title = $('#yaft_title_field').val();
		
		if(title === '') {
			alert(yaft_missing_title_message);
			return;
		}
		
		var description = $('#yaft_description_field').val();
		
		if(description.length > 255) {
			alert(yaft_description_too_long_message);
			return;
		}

		var startDate = (+$('#yaft_start_date_millis').val());
		var startHours = (+$('#yaft_start_hours').val());
		var startMinutes = (+$('#yaft_start_minutes').val());
		startDate += (startHours * 3600000) + (startMinutes * 60000);

		var endDate = (+$('#yaft_end_date_millis').val());
		var endHours = (+$('#yaft_end_hours').val());
		var endMinutes = (+$('#yaft_end_minutes').val());
		endDate += (endHours * 3600000) + (endMinutes * 60000);

		// Get any selected groups
		var groupBoxes = $('.yaft_group_checkbox:checked');

		var groups = '';

		for(var i=0,j=groupBoxes.length;i<j;i++) {
			groups += groupBoxes[i].id;
			if(i<j) groups += ',';
		}

	   	var forum = {
	   		'siteId':startupArgs.siteId,
			'id':$('#yaft_id_field').val(),
			'startDate':startDate,
			'endDate':endDate,
			'title':title,
			'description':description,
			'sendEmail':$('#yaft_send_email_checkbox').attr('checked'),
			'lockWriting':$('#yaft_lock_writing_checkbox').attr('checked'),
			'lockReading':$('#yaft_lock_reading_checkbox').attr('checked'),
			'discussions': [],
			'groups': groups
		};
	   		
		jQuery.ajax( {
	   		url : "/portal/tool/" + startupArgs.placementId + "/forums",
	   		dataType : "text",
	   		type: 'POST',
	   		'data': forum,
	       	async : false,
			cache: false,
		   	success : function(id,textStatus) {
				if('' === forum.id) {
					// New forum. Enter it.
					yaftCurrentForum = forum;
					yaftCurrentForum.id = id;
					switchState('forum');
				}
				else {
					switchState('forums');
				}
			},
			error : function(xhr,textStatus,errorThrown) {
				var message = $('#yaft_feedback_message');
				message.html('Failed to save forum');
				message.show();
			}
	   	});
	};
	
	my.showDeleted = function() {
		$(".yaft_deleted_message").show();
	  	$(document).ready(function() {
			if(window.frameElement)
				setMainFrameHeight(window.frameElement.id);
		});
	  	yaftShowingDeleted = true;
		$('#yaft_show_deleted_link').hide();
		$('#yaft_hide_deleted_link').show();
	};

	my.clearDates = function() {
		$('#yaft_start_date').val('');
		$('#yaft_end_date').val('');
		$('#yaft_start_hours').get(0).selectedIndex = 0;
		$('#yaft_start_minutes').get(0).selectedIndex = 0;
		$('#yaft_end_hours').get(0).selectedIndex = 0;
		$('#yaft_end_minutes').get(0).selectedIndex = 0;
		$('#yaft_start_date_millis').val('');
		$('#yaft_end_date_millis').val('');
	};
	
	my.getGroupsForCurrentSite = function() {
        var groups = null;

        $.ajax( {
            url : "/portal/tool/" + startupArgs.placementId + "/siteGroups",
            dataType : "json",
            cache: false,
            async : false,
            success : function(data,textStatus) {
                groups = data;
            },
            error : function(xhr,textStatus,errorThrown) {
            }
        });

        return groups;
    };
	
	my.clearActiveDiscussionsForCurrentUser = function() {
		$.ajax( {
	   		url : "/portal/tool/" + startupArgs.placementId + "/activeDiscussions/clear",
			dataType : "text",
			cache: false,
			async : false,
	  		success : function(text,status) {
				$('.yaft_active_discussion_row').hide();
			},
            error : function(xhr,textStatus,errorThrown) {
				alert('failed');
			}
		});
	};

	my.hideDeleted = function() {
		$(".yaft_deleted_message").hide();
	   	$(document).ready(function() {
			if(window.frameElement)
				setMainFrameHeight(window.frameElement.id);
		});
	  	yaftShowingDeleted = false;
		$('#yaft_show_deleted_link').show();
		$('#yaft_hide_deleted_link').hide();
	};

	my.renderCurrentForumContent = function() {
		SAKAIUTILS.renderTrimpathTemplate('yaft_forum_content_template',yaftCurrentForum,'yaft_content');
			
        $(document).ready(function() {
        
            // If there are no discussions in the current forum, hide the check all checkbox
            if(!yaftCurrentForum || yaftCurrentForum.discussions.length <= 0) {
                $('#yaft_all_discussions_checkbox').hide();
            }

            $('#yaft_bulk_discussions_delete_button').click(function () {
                YAFTUTILS.deleteSelectedDiscussions();
            });
            $('.yaft_bulk_option_candidate').click(function () {
                if($(this).attr('checked')) {
                    $('#yaft_bulk_discussions_delete_button').removeAttr('disabled');
                    $('#yaft_bulk_discussions_delete_button').addClass('enabled');
                } else {
                    // Only enable the delete button if no other discussion is checked
                    if($('.yaft_bulk_option_candidate:checked').length <= 0) {
                        $('#yaft_bulk_discussions_delete_button').attr('disabled','disabled');
                        $('#yaft_bulk_discussions_delete_button').removeClass('enabled');
                    }
                }
            });

            $('#yaft_all_discussions_checkbox').click(function () {
                if($(this).attr('checked')) {
                    $('.yaft_bulk_option_candidate').attr('checked','true');
                    $('#yaft_bulk_discussions_delete_button').removeAttr('disabled');
                    $('#yaft_bulk_discussions_delete_button').addClass('enabled');
                } else {
                    $('.yaft_bulk_option_candidate').removeAttr('checked');
                    $('#yaft_bulk_discussions_delete_button').attr('disabled','disabled');
                    $('#yaft_bulk_discussions_delete_button').removeClass('enabled');
                }
            });
									
            // We need to check this as tablesorter complains at empty tbody
            if(yaftCurrentForum.discussions.length > 0) {
			    $("#yaft_discussion_table").tablesorter({
	 							cssHeader:'yaftSortableTableHeader',
	 							cssAsc:'yaftSortableTableHeaderSortUp',
	 							cssDesc:'yaftSortableTableHeaderSortDown',
	 							headers:
	 								{
	 									4:{sorter: "isoDate"},
	 									5:{sorter: false},
	 									6:{sorter: false}
	 								},
                                sortList: [[0,0]],
	 							widgets: ['zebra']
	 						});

	            YAFTUTILS.attachProfilePopup();
            }

			if(window.frameElement)
		        setMainFrameHeight(window.frameElement.id);
	    });
	};
	
	my.attachProfilePopup = function() {
		$('a.profile').cluetip({
			width: '620px',
			cluetipClass: 'yaft',
			sticky: true,
 			dropShadow: false,
			arrows: true,
			mouseOutClose: true,
			closeText: '<img src="/library/image/silk/cross.png" alt="close" />',
			closePosition: 'top',
			showTitle: false,
			hoverIntent: true,
			ajaxSettings: {type: 'GET'}
		});
	};

    my.deleteSelectedFora  = function() {
    
		if(!confirm(yaft_delete_selected_fora_message)) {
			return;
		}
		
        var candidates = $('.yaft_bulk_option_candidate:checked');

        var forumIds = [];

        for(var i=0,j=candidates.length;i<j;i++) {
            forumIds.push(candidates[i].id);
        }

    	jQuery.ajax( {
			url : yaftBaseDataUrl + "forums/delete",
			type : 'POST',
        	dataType : "text",
        	async : false,
			cache: false,
        	data : { 'forumIds[]': forumIds },
        	success : function(results,textStatus) {
        		YAFTUTILS.setCurrentForums(/*render=*/true);
        	},
        	error : function(xhr,textStatus,errorThrown) {
				alert("Failed to delete forums. Status: " + textStatus + ". Error: " + errorThrown);
			}
		});
    };
	
    my.deleteSelectedDiscussions  = function() {
    
		if(!confirm(yaft_delete_selected_discussions_message)) {
			return;
		}
		
        var candidates = $('.yaft_bulk_option_candidate:checked');

        var discussionIds = [];

        for(var i=0,j=candidates.length;i<j;i++) {
            discussionIds.push(candidates[i].id);
        }

    	jQuery.ajax( {
			url : yaftBaseDataUrl + "discussions/delete",
			type : 'POST',
        	dataType : "text",
        	async : false,
			cache: false,
        	data : { 'discussionIds[]': discussionIds },
        	success : function(results,textStatus) {
        		switchState('forum',{'forumId':yaftCurrentForum.id});
        	},
        	error : function(xhr,textStatus,errorThrown) {
				alert("Failed to delete discussions. Status: " + textStatus + ". Error: " + errorThrown);
			}
		});
    };
    
	my.renderCurrentForums = function() {
		SAKAIUTILS.renderTrimpathTemplate('yaft_forums_content_template',{'items':yaftCurrentForums},'yaft_content');
	 	$(document).ready(function() {

            // If there are no fora, hide the check all checkbox
            if(!yaftCurrentForums || yaftCurrentForums.length <= 0) {
                $('#yaft_all_fora_checkbox').hide();
            }

            $('#yaft_bulk_forum_delete_button').click(function () {
                YAFTUTILS.deleteSelectedFora();
            });
            $('.yaft_bulk_option_candidate').click(function () {
                if($(this).attr('checked')) {
                    $('#yaft_bulk_forum_delete_button').removeAttr('disabled');
                    $('#yaft_bulk_forum_delete_button').addClass('enabled');
                } else {
                    // Only enable the delete button if no other forum is checked
                    if($('.yaft_bulk_option_candidate:checked').length <= 0) {
                        $('#yaft_bulk_forum_delete_button').attr('disabled','disabled');
                        $('#yaft_bulk_forum_delete_button').removeClass('enabled');
                    }
                }
            });

            $('#yaft_all_fora_checkbox').click(function () {
                if($(this).attr('checked')) {
                    $('.yaft_bulk_option_candidate').attr('checked','true');
                    $('#yaft_bulk_forum_delete_button').removeAttr('disabled');
                    $('#yaft_bulk_forum_delete_button').addClass('enabled');
                } else {
                    $('.yaft_bulk_option_candidate').removeAttr('checked');
                    $('#yaft_bulk_forum_delete_button').attr('disabled','disabled');
                    $('#yaft_bulk_forum_delete_button').removeClass('enabled');
                }
            });
	 			
            if(yaftCurrentForums.length > 0) {
	 		    $("#yaft_forum_table").tablesorter({
	 			    cssHeader:'yaftSortableTableHeader',
	 			    cssAsc:'yaftSortableTableHeaderSortUp',
	 			    cssDesc:'yaftSortableTableHeaderSortDown',
	 			    headers:
	 				    {
	 					    4: {sorter: "isoDate"},
	 					    5: {sorter: false},
	 					    6: {sorter: false}
	 				    },
                    sortList: [[0,0]],
	 			    widgets: ['zebra']
	 		    });
            }
            
			if(window.frameElement)
				setMainFrameHeight(window.frameElement.id);
	 	});
	};
	
	my.validateMessageSubmission = function(originalSubject) {
		originalSubject = unescape(originalSubject);

		var subject = $("#yaft_message_subject_field").val();

		if(subject)
			return true;
		else {
			if(confirm(yaft_empty_message_subject_message)) {
				if(originalSubject.match(/^Re: /) == null)
					$("#yaft_message_subject_field").val('Re: ' + originalSubject);
				else
					$("#yaft_message_subject_field").val(originalSubject);

				return true;
			}
			else
				return false;
		}
	};
	
	my.getForum = function(forumId,state) {
		var forumDataUrl = yaftBaseDataUrl + "forums/" + forumId + ".json";
		if(state != null) forumDataUrl += "?state=" + state;
	
		var currentForum = null;
		
		$.ajax( {
	   		url : forumDataUrl,
			dataType : "json",
			cache: false,
			async : false,
	  		success : function(data) {
	  			currentForum = data.forum;

			    for(var i=0,j=currentForum.discussions.length;i<j;i++) {
			        var discussion = currentForum.discussions[i];
				    var count = data.counts[discussion.id];
				    if(count) {
				        discussion['unread'] = discussion.messageCount - count;
                    } else {
					    discussion['unread'] = discussion.messageCount;
                    }

				    if(discussion['unread'] < 0) {
                        discussion['unread'] = 0;
                    }
			    }
	        },
            error : function(xhr,textStatus,errorThrown) {
			}
		});
		
		return currentForum;
	};
	
	my.getDiscussion = function(discussionId) {
		var discussion = null;
		
		jQuery.ajax( {
	   		url : yaftBaseDataUrl + "discussions/" + discussionId + ".json",
			dataType : "json",
			cache: false,
			async : false,
	  		success : function(d) {
				discussion = d;
	  		},
            error : function(xhr,textStatus,errorThrown) {
				alert("Discussions connection expired. Please refresh the page and try again "+xhr.statusText);
	 		}
		});
		
		return discussion;
	};

	my.getDiscussionContainingMessage = function(messageId) {
		var discussion = null;
		
		jQuery.ajax( {
	   		url : "/portal/tool/" + startupArgs.placementId + "/discussions/discussionContainingMessage?messageId=" + messageId,
			dataType : "json",
			cache: false,
			async : false,
	  		success : function(d) {
				discussion = d;
	  		},
            error : function(xhr,textStatus,errorThrown) {
				alert("Failed to get discussion. Reason: " + xhr.statusText);
	 		}
		});
		
		return discussion;
	};

	my.getForumContainingMessage = function(messageId) {
		var forum = null;
		
		jQuery.ajax( {
	   		url : "/portal/tool/" + startupArgs.placementId + "/forumContainingMessage?messageId=" + messageId,
			dataType : "json",
			cache: false,
			async : false,
	  		success : function(d) {
				forum = d;
	  		},
            error : function(xhr,textStatus,errorThrown) {
				alert("Failed to get forum. Reason: " + xhr.statusText);
	 		}
		});
		
		return forum;
	};

	my.likeAuthor = function(authorId) {
		jQuery.ajax( {
	   		url : "/direct/likeservice/" + authorId + "/addLike",
			dataType : "text",
			cache: false,
	  		success : function(response) {
	  		},
            error : function(xhr,textStatus,errorThrown) {
				alert("Failed to like author. Reason: " + xhr.statusText);
	 		}
		});
	};
	
	/* START MESSAGE OPERATIONS */
	
	my.deleteMessage = function(messageId) {
		if(!confirm(yaft_delete_message_message))
			return false;
		
		jQuery.ajax( {
	   		url : "/portal/tool/" + startupArgs.placementId + "/messages/" + messageId + "/delete",
	   		dataType : "text",
	   		async : false,
			cache: false,
		   	success : function(text,textStatus) {
				var message = YAFTUTILS.findMessage(messageId);
				message.status = 'DELETED';
				if(message.id == yaftCurrentDiscussion.firstMessage.id)
					message['isFirstMessage'] = true;
				else
					message['isFirstMessage'] = false;
				SAKAIUTILS.renderTrimpathTemplate('yaft_message_template',message,message.id);
				if(yaftViewMode === 'minimal') {
					$('#' + message.id + '_link').hide();
					$('#' + message.id).hide();
				}

				$(document).ready(function() {
					attachProfilePopup();
			    });
			},
			error : function(xhr,textStatus,errorThrown) {
				alert("Failed to delete message. Reason: " + textStatus);
			}
		});
		
		return false;
	};

	my.findForum = function(id) {
		for(var i=0,j=yaftCurrentForums.length;i<j;i++) {
			if(yaftCurrentForums[i].id == id)
				return yaftCurrentForums[i];
		}

		return null;
	};

	my.findDiscussion = function(id) {
		var discussions = yaftCurrentForum.discussions;

		for(var i=0,j=discussions.length;i<j;i++) {
			if(discussions[i].id == id)
				return discussions[i];
		}

		return null;
	};
	
	my.undeleteMessage = function(messageId) {
		if(!confirm(yaft_undelete_message_message))
			return false;
		
		jQuery.ajax( {
	   		url : "/portal/tool/" + startupArgs.placementId + "/messages/" + messageId + "/undelete",
	   		dataType : "text",
	   		async : false,
			cache: false,
		   	success : function(text,textStatus) {
				var message = YAFTUTILS.findMessage(messageId);
				message.status = 'READY';
				if(message.id == yaftCurrentDiscussion.firstMessage.id)
					message['isFirstMessage'] = true;
				else
					message['isFirstMessage'] = false;
				SAKAIUTILS.renderTrimpathTemplate('yaft_message_template',message,message.id);
				$(document).ready(function() {
					attachProfilePopup();
			    });
			},
			error : function(xhr,textStatus,errorThrown) {
				alert("Failed to un-delete message. Reason: " + textStatus);
			}
		});
		
		return false;
	};
    
    my.toggleMessage = function(e,messageId) {
		var message = YAFTUTILS.findMessage(messageId);

    	var descendantIds = new Array();
    	getDescendantIds(messageId,message,descendantIds);

		if(message.collapsed) {
    		for(var i=0,j=descendantIds.length;i<j;i++) {
    			$("#" + descendantIds[i] + ' .yaft_collapse_expand_link').html(yaft_collapse_label);
    			$("#" + descendantIds[i]).show();
		        var descendant = YAFTUTILS.findMessage(descendantIds[i]);
                descendant.collapsed = false;
           	}

			e.innerHTML = yaft_collapse_label;
			message.collapsed = false;
		} else {
    		for(var i=0,j=descendantIds.length;i<j;i++)
    			$("#" + descendantIds[i]).hide();

			e.innerHTML = yaft_expand_label + ' (' + descendantIds.length + ')';
			message.collapsed = true;
		}
    };

    my.markCurrentDiscussionRead = function(read) {
		jQuery.ajax( {
	 		url : "/portal/tool/" + startupArgs.placementId + "/discussions/" + yaftCurrentDiscussion.id + "/markRead",
	   		dataType : "text",
	   		async : false,
			cache: false,
		   	success : function(text,status) {
				for(var i=0,j=yaftCurrentForum.discussions.length;i<j;i++) {
					var discussion = yaftCurrentForum.discussions[i];
					if(discussion.id == yaftCurrentDiscussion.id)
						discussion['unread'] = 0;
				}
				
				switchState('forum');
			},
			error : function(xhr,status,error) {
			}
	  	});

		return false;
	};
    
    my.markMessageRead = function(message,read) {
		var message;
		
		if(message["read"] && read == message["read"]) return;
		
		var func = 'markRead';
		if(!read) func = 'markUnRead';
		
		jQuery.ajax( {
	 		url : "/portal/tool/" + startupArgs.placementId + "/messages/" + message.id + "/" + func,
	   		dataType : "text",
	   		async : false,
			cache: false,
		   	success : function(text,status) {
				message["read"] = read;

				if('minimal' === yaftViewMode) {
					if(read)
						$("#" + message.id + "_read").show();
					else
						$("#" + message.id + "_read").hide();
				}

				SAKAIUTILS.renderTrimpathTemplate('yaft_message_template',message,message.id);
					
			},
			error : function(xhr,status,error) {
				alert("Failed to mark message as read. Reason: " + status);
			}
	  	});

		return false;
	};
	
	/* END MESSAGE OPERATIONS */
	
	my.deleteForum = function(forumId,forumTitle) {
		if(!confirm(yaft_delete_forum_message_one + "'" + forumTitle + "'" + yaft_delete_forum_message_two))
			return false;
		
		jQuery.ajax( {
	   		url : "/portal/tool/" + startupArgs.placementId + "/forums/" + forumId + "/delete",
	   		dataType : "text",
	   		async : false,
			cache: false,
		   	success : function(text,textStatus) {
				switchState('forums');
			},
			error : function(xhr,textStatus,errorThrown) {
				alert("Failed to delete forum. Reason: " + textStatus);
			}
		});
		
		return false;
	};
	
	my.deleteDiscussion = function(discussionId,discussionTitle) {
		if(!confirm(yaft_delete_discussion_message_one + "'" + discussionTitle + "'" + yaft_delete_discussion_message_two))
			return false;
		
		jQuery.ajax( {
	   		url : "/portal/tool/" + startupArgs.placementId + "/discussions/" + discussionId + "/delete",
	   		dataType : "text",
	   		async : false,
			cache: false,
		   	success : function(text,textStatus) {
				switchState('forum',{'forumId':yaftCurrentForum.id});
			},
			error : function(xhr,textStatus,errorThrown) {
				alert("Failed to delete discussion. Reason: " + textStatus);
			}
		});
		
		return false;
	};

	my.clearDiscussion = function(discussionId,discussionTitle) {
		if(!confirm(yaft_clear_discussion_message_one + "'" + discussionTitle + "'" + yaft_clear_discussion_message_two))
			return false;
		
		jQuery.ajax( {
	   		url : "/portal/tool/" + startupArgs.placementId + "/discussions/" + discussionId + "/clear",
	   		dataType : "json",
	   		async : false,
			cache: false,
		   	success : function(data,textStatus) {
		   		// Switch the updated discussion into the current forum
		   		for(var i=0,j=yaftCurrentForum.discussions.length;i<j;i++) {
		   			if(yaftCurrentForum.discussions[i].id === data.id) {
		   			    yaftCurrentForum.discussions[i] = data;
		   			}
		   		}
				switchState('forum',{'forumId':yaftCurrentForum.id});
			},
			error : function(xhr,textStatus,errorThrown) {
				alert("Failed to clear discussion. Reason: " + textStatus);
			}
		});
		
		return false;
	};
	
    function getDescendantIds(messageId,testMessage,descendantIds) {
    	for(var i=0,j=testMessage.children.length;i<j;i++) {
    		descendantIds.push(testMessage.children[i].id);
    		getDescendantIds(messageId,testMessage.children[i],descendantIds);
    	}
    }
	
	my.removeAttachment = function(attachmentId,messageId,elementId) {
		if(!confirm(yaft_delete_attachment_message))
			return;

		jQuery.ajax( {
	 		url : "/portal/tool/" + startupArgs.placementId + "/messages/" + messageId + "/attachments/" + attachmentId + "/delete",
	   		dataType : "text",
	   		async : false,
			cache: false,
		   	success : function(text,textStatus) {
				//if("success".equals(text))
					jQuery("#" + elementId).remove();
			},
			error : function(xhr,textStatus,errorThrown) {
				alert("Failed to delete attachment. Reason: " + errorThrown);
			}
	  	});
	};

	my.showSearchResults = function(searchTerms) {
    	jQuery.ajax( {
			url : "/portal/tool/" + startupArgs.placementId + "/search",
			type : 'POST',
        	dataType : "json",
        	async : false,
			cache: false,
        	data : {'searchTerms':searchTerms},
        	success : function(results,textStatus) {
        		var hits = results;
				$('#yaft_breadcrumb').html('');
				SAKAIUTILS.renderTrimpathTemplate('yaft_search_results_content_template',{'results':hits},'yaft_content');
	 			$(document).ready(function() {
					if(window.frameElement)
						setMainFrameHeight(window.frameElement.id);
				});
        	},
        	error : function(xhr,textStatus,errorThrown) {
				alert("Failed to search. Status: " + textStatus + ". Error: " + errorThrown);
			}
		});

		$("#yaft_add_discussion_link").hide();
		$("#yaft_add_forum_link").hide();
		$("#yaft_hide_deleted_link").hide();
		$("#yaft_minimal_link").hide();
		$("#yaft_full_link").hide();
		$("#yaft_permissions_link").hide();
	}
	
	function contains(list,test) {
		for(var i=0,j=list.length;i<j;i++) {
			if(test == list[i]) return true;
		}
		
		return false;
	}
	
	function markReadMessagesInFora() {
		var readMessages = null;
			
		jQuery.ajax( {
	 		url : yaftBaseDataUrl + "forums/allReadMessages.json",
			async : false,
   			dataType: "json",
			cache: false,
			success : function(read,textStatus) {
				readMessages = read;
			},
			error : function(xhr,textStatus,errorThrown) {
				alert("Failed to get read messages. Reason: " + errorThrown);
			}
		});
			
		if(readMessages != null) {
			for(var i=0,j=yaftCurrentForums.length;i<j;i++) {
				var forum = yaftCurrentForums[i];
				var count = readMessages[forum.id];
				if(count)
					forum['unread'] = forum.messageCount - count;
				else
					forum['unread'] = forum.messageCount;

				if(forum['unread'] < 0) forum['unread'] = 0;
			}
		}
	}

    my.addFormattedDatesToDiscussionsInCurrentForum = function () {
		for(var i=0,j=yaftCurrentForum.discussions.length;i<j;i++) {
			var discussion = yaftCurrentForum.discussions[i];
            if(discussion.start > -1) {
                var d = new Date(discussion.start);
                var hours = d.getHours();
		        if(hours < 10)  hours = '0' + hours;
                var minutes = d.getMinutes();
		        if(minutes < 10)  minutes = '0' + minutes;
                var formattedStartDate = d.getDate() + " " + yaft_month_names[d.getMonth()] + " " + d.getFullYear() + " @ " + hours + ":" + minutes;
                discussion.formattedStartDate = formattedStartDate;
            }
            if(discussion.end > -1) {
                var d = new Date(discussion.end);
                var hours = d.getHours();
		        if(hours < 10)  hours = '0' + hours;
                var minutes = d.getMinutes();
		        if(minutes < 10)  minutes = '0' + minutes;
                var formattedEndDate = d.getDate() + " " + yaft_month_names[d.getMonth()] + " " + d.getFullYear() + " @ " + hours + ":" + minutes;
                discussion.formattedEndDate = formattedEndDate;
            }
            if(discussion.lastMessageDate > -1) {
                var d = new Date(discussion.lastMessageDate);
                var hours = d.getHours();
		        if(hours < 10)  hours = '0' + hours;
                var minutes = d.getMinutes();
		        if(minutes < 10)  minutes = '0' + minutes;
                discussion.formattedLastMessageDate = d.getDate() + " " + yaft_month_names[d.getMonth()] + " " + d.getFullYear() + " @ " + hours + ":" + minutes;
            } else {
            	discussion.formattedLastMessageDate = 'n/a';
            }
		}
    };

    function addFormattedDatesToFora() {
		for(var i=0,j=yaftCurrentForums.length;i<j;i++) {
			var forum = yaftCurrentForums[i];
            if(forum.start > -1) {
                var d = new Date(forum.start);
                var hours = d.getHours();
		        if(hours < 10)  hours = '0' + hours;
                var minutes = d.getMinutes();
		        if(minutes < 10)  minutes = '0' + minutes;
                forum.formattedStartDate = d.getDate() + " " + yaft_month_names[d.getMonth()] + " " + d.getFullYear() + " @ " + hours + ":" + minutes;
            }
            if(forum.end > -1) {
                var d = new Date(forum.end);
                var hours = d.getHours();
		        if(hours < 10)  hours = '0' + hours;
                var minutes = d.getMinutes();
		        if(minutes < 10)  minutes = '0' + minutes;
                forum.formattedEndDate = d.getDate() + " " + yaft_month_names[d.getMonth()] + " " + d.getFullYear() + " @ " + hours + ":" + minutes;
            }
            if(forum.lastMessageDate > -1) {
                var d = new Date(forum.lastMessageDate);
                var hours = d.getHours();
		        if(hours < 10)  hours = '0' + hours;
                var minutes = d.getMinutes();
		        if(minutes < 10)  minutes = '0' + minutes;
                forum.formattedLastMessageDate = d.getDate() + " " + yaft_month_names[d.getMonth()] + " " + d.getFullYear() + " @ " + hours + ":" + minutes;
            } else {
            	forum.formattedLastMessageDate = 'n/a';
            }
		}
    };
	
	my.markReadMessagesInCurrentDiscussion = function() {
		var readMessages = [];
			
		jQuery.ajax( {
	 		url : yaftBaseDataUrl + "discussions/" + yaftCurrentDiscussion.id + "/readMessages.json",
			async : false,
   			dataType: "json",
			cache: false,
			success : function(read,textStatus) {
				var ids = read;
				for(var i=0,j=ids.length;i<j;i++) {
					//readMessages.push(ids[i].data);
					readMessages.push(ids[i]);
				}
			},
			error : function(xhr,textStatus,errorThrown) {
				// 404 can be thrown when there are no read messages
				if(404 != xhr.status) {
					alert("Failed to get read messages. Reason: " + errorThrown);
				}
			}
		});
			
		if(readMessages != null) {
			var firstMessage = yaftCurrentDiscussion.firstMessage;
		
			if(contains(readMessages,firstMessage.id) || firstMessage.creatorId == startupArgs.userId)
				firstMessage['read'] = true;
			else
				firstMessage['read'] = false;
		
			markReadMessages(firstMessage.children,readMessages);
		}
	};

	function markReadMessages(messages,readMessages) {
		for(var i=0,j=messages.length;i<j;i++) {
            if(contains(readMessages,messages[i].id) || messages[i].creatorId == startupArgs.userId)
				messages[i]["read"] = true;
			else
				messages[i]["read"] = false;

			markReadMessages(messages[i].children,readMessages);
        }
	}
	
	my.findMessage = function(wantedId) {
		if(yaftCurrentDiscussion) {
			var firstMessage = yaftCurrentDiscussion.firstMessage;
		
			if(firstMessage.id == wantedId) {
				firstMessage["isFirstMessage"] = true;
				return firstMessage;
			}
			else
				return recursiveFindMessage(firstMessage.children,wantedId);
		} else {
		}
	};
	
	function recursiveFindMessage(messages,wantedId) {
		for(var i=0,j=messages.length;i<j;i++) {
			var message = messages[i];
            if(message.id == wantedId) {
				message["isFirstMessage"] = false;
                return message;
           	}
          	else {
          		if(message.children.length > 0) {
          			var test = recursiveFindMessage(message.children,wantedId);
          			if(test == null)
          				continue;
          			else {
						test["isFirstMessage"] = false;
          				return test;
          			}
          		}
          		else {
          		}
          	}
        }
        
        return null;
	}

	my.getCurrentUserData = function() {
		var userData = null;
		jQuery.ajax( {
	 		url : yaftBaseDataUrl + "userData.json",
	   		dataType : "json",
	   		timeout: 5000,
	   		async : false,
	   		cache : false,
		   	success : function(data,textStatus) {
                userData = data;
			},
			error : function(xhr,textStatus,errorThrown) {
				alert("Discussions connection expired. Please close ALL your open internet browser windows and login to myUnisa again");
			}
	  	});
	  	
	  	return userData;
	};
	
	my.getSitePermissionMatrix = function() {
        var perms = [];

        jQuery.ajax( {
            url : yaftBaseDataUrl + "perms.json",
            dataType : "json",
            async : false,
            cache: false,
            success : function(p,textStatus) {
                for(role in p) {
                    var permSet = {'role':role};

                    for(var i=0,j=p[role].length;i<j;i++) {
                        var perm = p[role][i].replace(/\./g,"_");
                        eval("permSet." + perm + " = true");
                    }

                    perms.push(permSet);
                }
            },
            error : function(xhr,textStatus,errorThrown) {
                alert("Failed to get permissions. Status: " + textStatus + ". Error: " + errorThrown);
            }
        });

        return perms;
    };
    
    my.savePermissions = function() {
        var boxes = $('.yaft_permission_checkbox');
        var myData = {};
        for(var i=0,j=boxes.length;i<j;i++) {
            var box = boxes[i];
            if(box.checked)
                myData[box.id] = 'true';
            else
                myData[box.id] = 'false';
        }

        jQuery.ajax( {
            url : yaftBaseDataUrl + "setPerms.json",
            type : 'POST',
            data : myData,
            timeout: 30000,
            async : false,
            dataType: 'text',
            success : function(result,textStatus) {
                switchState('forums');
            },
            error : function(xhr,textStatus,errorThrown) {
                alert("Failed to save permissions. Status: " + textStatus + '. Error: ' + errorThrown);
            }
        });

        return false;
    };

    my.getAuthors = function() {
        this.setCurrentAuthors();
	    SAKAIUTILS.renderTrimpathTemplate('yaft_discussion_authors_breadcrumb_template',yaftCurrentDiscussion,'yaft_breadcrumb');
        SAKAIUTILS.renderTrimpathTemplate('yaft_authors_template',{'authors': yaftCurrentAuthors},'yaft_content');
	    $(document).ready(function() {
	        YAFTUTILS.attachProfilePopup();
		    $("#yaft_author_table").tablesorter({
	 				cssHeader:'yaftSortableTableHeader',
	 				cssAsc:'yaftSortableTableHeaderSortUp',
	 				cssDesc:'yaftSortableTableHeaderSortDown',
	 				widgets: ['zebra']
	 		});
	        if(window.frameElement)
		        setMainFrameHeight(window.frameElement.id);
        });
    };

    my.setCurrentAuthors = function() {
		jQuery.ajax( {
	 		url : "/portal/tool/" + startupArgs.placementId + "/discussions/" + yaftCurrentDiscussion.id + "/authors",
	   		dataType : "json",
	   		cache : false,
            async: false,
		   	success : function(data,textStatus) {
                yaftCurrentAuthors = data;
                yaftCurrentAuthors.sort(function(a,b) {
                    if(a.displayName < b.displayName) return -1;
                    else if(a.displayName > b.displayName) return 1;
                    else return 0;
                });
			},
			error : function(xhr,textStatus,errorThrown) {
				alert("Failed to get the current set of authors. Status: " + textStatus + ". Error: " + errorThrown);
			}
	  	});
    };


    my.showAuthorPosts = function(authorId,nextAuthor,prevAuthor) {
        var author = null;                   

        if(yaftCurrentAuthors == null) {
            this.setCurrentAuthors();
        }

        for(var i=0,j=yaftCurrentAuthors.length;i<j;i++) {
            if(yaftCurrentAuthors[i].id === authorId) {
            	if(i==0){
            	prevAuthor='';	
                }
            	else if(i==(j - 1)){
                nextAuthor='';
                } 
            	if(yaftCurrentAuthors.length==1){
            		nextAuthor='';
            		prevAuthor='';
            	}
                author = yaftCurrentAuthors[i];
            }
        }
        
		jQuery.ajax( {
	 		url : "/portal/tool/" + startupArgs.placementId + "/discussions/" + yaftCurrentDiscussion.id + "/authors/" + authorId + "/messages",
	   		dataType : "json",
	   		timeout: 15000,
	   		cache : false,
		   	success : function(data,textStatus) {
                var stuff = {'messages': data,'assignment':yaftCurrentDiscussion.assignment,'displayName':author.displayName,'nextAuthor':nextAuthor,'prevAuthor':prevAuthor};

                if(author.grade) {
                    stuff.grade = author.grade.grade;
                }

                SAKAIUTILS.renderTrimpathTemplate('yaft_author_messages_template',stuff,'yaft_content');

	  	        $(document).ready(function() {
			        YAFTUTILS.attachProfilePopup();
                    $('#yaft_grade_button').click(function () {

                        var gradePoints = $('#yaft_grade_field').val();

                        jQuery.ajax( {
	 		                url : "/portal/tool/" + startupArgs.placementId + "/assignments/" + yaftCurrentDiscussion.assignment.id + "/authors/" + authorId + "/grade/" + gradePoints,
	   		                dataType : "text",
	   		                cache : false,
		   	                success : function(data,textStatus) {
                                $('.yaft_grade_field').val(gradePoints);

                                $('.yaft_grade_success_message').show();

                                window.setTimeout(function () { $('.yaft_grade_success_message').fadeOut(); },2000);
                            },
			                error : function(xhr,textStatus,errorThrown) {
			                }
                        });
                    });
                   
                    $('#yaft_previous_author_button').click(function () {
                        for(var i=0,j=yaftCurrentAuthors.length;i<j;i++) {
                            if(yaftCurrentAuthors[i].id === authorId) {                            	
                             	if(i > 0) {  
                                	nextAuthor='valid';
                                	if(i==1){
                                		prevAuthor='';
                                	}
                                    YAFTUTILS.showAuthorPosts(yaftCurrentAuthors[i - 1].id,nextAuthor,prevAuthor);
                                }
                            }
                        }
                    });

                    $('#yaft_next_author_button').click(function () {
                        for(var i=0,j=yaftCurrentAuthors.length;i<j;i++) {
                          if(yaftCurrentAuthors[i].id === authorId) {
                        	  prevAuthor='valid';
                                if(i < (j - 1)) {
                                   	 if((i==j-2)){
                                		 nextAuthor='';
                                	 }
                                	YAFTUTILS.showAuthorPosts(yaftCurrentAuthors[i + 1].id,nextAuthor,prevAuthor);
                                }                                	
                            }
                        }
                    });
   
			        if(window.frameElement) {
				        setMainFrameHeight(window.frameElement.id);
                    }
		        });
			},
			error : function(xhr,textStatus,errorThrown) {
				alert("Failed to get posts for author. Status: " + textStatus + ". Error: " + errorThrown);
			}
	  	});
    };
    
    my.addFormattedDatesToCurrentDiscussion = function () {
    	var d = new Date(yaftCurrentDiscussion.firstMessage.createdDate);
        var minutes = d.getMinutes();
        if(minutes < 10) minutes = '0' + minutes;
        var formattedCreatedDate = d.getDate() + " " + yaft_month_names[d.getMonth()] + " " + d.getFullYear() + " @ " + d.getHours() + ":" + minutes;
    	yaftCurrentDiscussion.firstMessage.formattedDate = formattedCreatedDate;
    	
    	this.recursivelyAddFormattedDatesToChildMessages(yaftCurrentDiscussion.firstMessage);
    };
    
   	my.recursivelyAddFormattedDatesToChildMessages = function(parent) {
   		for(var i=0,j=parent.children.length;i<j;i++) {
   			var child = parent.children[i];
    		var d = new Date(child.createdDate);
        	var minutes = d.getMinutes();
        	if(minutes < 10) minutes = '0' + minutes;
        	var formattedCreatedDate = d.getDate() + " " + yaft_month_names[d.getMonth()] + " " + d.getFullYear() + " @ " + d.getHours() + ":" + minutes;
    		child.formattedDate = formattedCreatedDate;
    		
   			this.recursivelyAddFormattedDatesToChildMessages(child);
   		}
   	};

    return my;

}(jQuery));

