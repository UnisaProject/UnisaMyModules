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
package org.sakaiproject.qna.logic.test.stubs;

import static org.sakaiproject.qna.logic.test.TestDataPreload.LOCATION1_CONTACT_EMAIL;
import static org.sakaiproject.qna.logic.test.TestDataPreload.LOCATION1_CONTACT_NAME;
import static org.sakaiproject.qna.logic.test.TestDataPreload.LOCATION1_ID;
import static org.sakaiproject.qna.logic.test.TestDataPreload.LOCATION3_ID;
import static org.sakaiproject.qna.logic.test.TestDataPreload.USER_LOC_3_UPDATE_1;
import static org.sakaiproject.qna.logic.test.TestDataPreload.USER_LOC_3_UPDATE_2;
import static org.sakaiproject.qna.logic.test.TestDataPreload.USER_LOC_3_UPDATE_3;
import static org.sakaiproject.qna.logic.test.TestDataPreload.USER_NO_UPDATE;
import static org.sakaiproject.qna.logic.test.TestDataPreload.USER_UPDATE;

import java.util.HashSet;
import java.util.Set;

import org.sakaiproject.qna.logic.ExternalLogic;

public class ExternalLogicStub implements ExternalLogic {

	public String currentUserId;

	public String getCurrentLocationId() {
		// default is LOCATION1
		return LOCATION1_ID;
	}

	public String getCurrentUserId() {
		return currentUserId;
	}

	public String getLocationTitle(String locationId) {
		// TODO Auto-generated method stub
		return null;
	}

	public String getUserDisplayName(String userId) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isUserAdmin(String userId) {
		if ("admin".equals(userId))
			return true;

		return false;
	}

	public boolean isUserAllowedInLocation(String userId, String permission,
			String locationId) {
		if (userId == null) {
			return false;
		}
		
		if (userId.equals(USER_NO_UPDATE)) {
			if (locationId.equals(LOCATION1_ID)) {
				if (permission.equals(QNA_UPDATE)) {
					return false;
				}
			}
		} else if (userId.equals(USER_UPDATE)) {
			if (locationId.equals(LOCATION1_ID)) {
				if (permission.equals(QNA_UPDATE)
						|| permission.equals(QNA_NEW_QUESTION)
						|| permission.equals(QNA_NEW_CATEGORY)
						|| permission.equals(QNA_NEW_ANSWER)) {
					return true;
				}
			}
		} else if (userId.equals(USER_LOC_3_UPDATE_1)
				|| userId.equals(USER_LOC_3_UPDATE_2)
				|| userId.equals(USER_LOC_3_UPDATE_3)) {
			if (locationId.equals(LOCATION3_ID)) {
				if (permission.equals(QNA_UPDATE)
						|| permission.equals(QNA_NEW_QUESTION)
						|| permission.equals(QNA_NEW_CATEGORY)
						|| permission.equals(QNA_NEW_ANSWER)) {
					return true;
				}
			}
		}

		if (permission.equals(QNA_READ)) {
			return true;
		}

		return false;

	}

	public String getSiteContactEmail(String locationId) {
		if (locationId.equals(LOCATION1_ID)) {
			return LOCATION1_CONTACT_EMAIL;
		} else {
			return null;
		}
	}

	public String getSiteContactName(String locationId) {
		if (locationId.equals(LOCATION1_ID)) {
			return LOCATION1_CONTACT_NAME;
		} else {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public Set<String> getSiteUsersWithPermission(String locationId,
			String permission) {
		if (locationId.equals(LOCATION3_ID) && permission.equals(QNA_UPDATE)) {
			Set users = new HashSet<String>();
			users.add(USER_LOC_3_UPDATE_1);
			users.add(USER_LOC_3_UPDATE_2);
			users.add(USER_LOC_3_UPDATE_3);
			return users;
		} else {
			return new HashSet<String>();
		}
	}

	public String[] sendEmailsToUsers(String from, String[] toUserIds,
			String subject, String message) {
		// TODO Auto-generated method stub
		return null;
	}

	public String getCurrentToolDisplayName() {
		return "Questions and Answers";
	}

	public String getQuestionViewUrl(String viewId) {
		// TODO Auto-generated method stub
		return null;
	}

	public String[] sendEmails(String from, String[] toEmails, String subject,
			String message) {
		// TODO Auto-generated method stub
		return null;
	}

	public String getUserEmail(String userId) {
		return (new FakeUser(userId)).getEmail();
	}

	public boolean hasMaintainRole(String userId) {
		// TODO Auto-generated method stub
		return false;
	}

	public String getCurrentToolId() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getSiteIdFromRef(String reference) {
		// TODO Auto-generated method stub
		return "ref-1111111";
	}

	public String[] sendSms(String[] mobileNrs, String fromId, String siteId,
			String toolId, String message) {
		// TODO Auto-generated method stub
		return null;
	}

	public String getSmsNumber() {
		return "12345";
	}

	public String getServiceName() {
		return "Sakai";
	}
}
