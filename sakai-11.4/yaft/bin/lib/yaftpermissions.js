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
function YaftPermissions(data) {

	for(var i=0,j=data.length;i<j;i++) {
		if('yaft.discussion.create' === data[i])
			this.discussionCreate = true;
		else if('yaft.discussion.deleteAny' === data[i])
			this.discussionDeleteAny = true;
		else if('yaft.discussion.deleteOwn' === data[i])
			this.discussionDeleteOwn = true;
		else if('yaft.forum.create' === data[i])
			this.forumCreate = true;
		else if('yaft.forum.deleteAny' === data[i])
			this.forumDeleteAny = true;
		else if('yaft.forum.deleteOwn' === data[i])
			this.forumDeleteOwn = true;
		else if('yaft.forum.viewGroups' === data[i])
			this.forumViewGroups = true;
		else if('yaft.message.create' === data[i])
			this.messageCreate = true;
		else if('yaft.message.deleteAny' === data[i])
			this.messageDeleteAny = true;
		else if('yaft.message.deleteOwn' === data[i])
			this.messageDeleteOwn = true;
		else if('yaft.message.updateOwn' === data[i])
			this.messageUpdateOwn = true;
		else if('yaft.message.updateAny' === data[i])
			this.messageUpdateAny = true;
		else if('yaft.message.read' === data[i])
			this.messageRead = true;
		else if('yaft.modify.permissions' === data[i])
			this.modifyPermissions = true;
		else if('yaft.view.invisible' === data[i])
			this.viewInvisible = true;
		else if('yaft.sendAlerts' === data[i])
			this.sendAlerts = true;
		else if('gradebook.gradeAll' === data[i])
			this.gradeAll = true;
	}
}
