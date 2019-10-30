/**
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
package org.sakaiproject.yaft.api;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.sakaiproject.api.app.profile.Profile;
import org.sakaiproject.authz.api.SecurityAdvisor;
import org.sakaiproject.entity.api.EntityProducer;
import org.sakaiproject.search.api.SearchResult;
import org.sakaiproject.service.gradebook.shared.Assignment;
import org.sakaiproject.service.gradebook.shared.GradeDefinition;
import org.sakaiproject.site.api.Site;
import org.sakaiproject.site.api.ToolConfiguration;
import org.sakaiproject.user.api.User;

/**
 * All Sakai API calls go in here. If Sakai changes all we have to do if mod
 * this file.
 * 
 * @author Adrian Fish (a.fish@lancaster.ac.uk)
 */
public interface SakaiProxy
{
	public boolean isAutoDDL();
	
	public String getDbVendor();
	
	public String getCurrentSiteId();
	
	public Site getCurrentSite();
	
	public Connection borrowConnection() throws SQLException;
	
	public void returnConnection(Connection connection);

	public String getDisplayNameForUser(String creatorId);

	public String getSakaiHomePath();

	public Profile getProfile(String userId);

	public void registerFunction(String yaftForumCreate);
	
	public boolean addCalendarEntry(String title,String description, String type, long startDate,long endDate);
	
	public boolean removeCalendarEntry(String title,String description);

	public User getCurrentUser();
	//unisa change 
	public String getUserIdFromSession();

	public String getPortalUrl();
	
	public String getServerUrl();

	public String getCurrentPageId();

	public String getCurrentToolId();
	
	public String getYaftPageId(String siteId);
	
	public String getYaftToolId(String siteId);

	public String getDirectUrl(String siteId, String string);
	
	public String saveFile(String siteId, String creatorId,String name,String mimeType, byte[] fileData) throws Exception;
	
	public void getAttachment(String siteId, Attachment attachment);

	public void deleteFile(String resourceId) throws Exception;

	public String getUserBio(String id);

	public List<Site> getAllSites();

	public ToolConfiguration getFirstInstanceOfTool(String siteId, String string);

	public String[] getSiteIds();
	
	public void registerEntityProducer(EntityProducer entityProducer);
	
	public boolean userHasFunctionInCurrentSite(String userId, String function);
	
	public void postEvent(String event,String reference,boolean modify);

	public Site getSite(String siteId);

	public List<User> getUsers(Collection<String> userIds);

	public Set<String> getPermissionsForCurrentUserAndSite();

	public Map<String,Set<String>> getPermsForCurrentSite();

	public boolean setPermsForCurrentSite(Map<String,String[]> parameterMap);

	public List<SearchResult> searchInCurrentSite(String searchTerms);

	public boolean isCurrentUserMemberOfAnyOfTheseGroups(List<Group> groups);

	public List<User> getGroupUsers(List<Group> groups);

	public List<Group> getCurrentSiteGroups();

	public boolean currentUserHasFunction(String function);
	
	public boolean scoreAssignment(int assignmentId, String studentId,String score);

	public String getSakaiSkin();

	//public void createGradebookAssignment(String subject);

	/** Gets the list of assignments for the current worksite*/
	public List<YaftGBAssignment> getGradebookAssignments();

	public YaftGBAssignment getGradebookAssignment(int gradebookAssignmentId);

	public GradeDefinition getAssignmentGrade(String userId, long assignmentId);

	public List<User> getCurrentSiteMaintainers();

	public boolean getIncludeMessageBodyInEmailSetting();

	public String getWysiwygEditor();

	public boolean isCurrentUserMemberOfSite(String siteId);

	public void pushSecurityAdvisor(SecurityAdvisor advisor);

	public boolean isCurrentUserSuperUser();
	
	public String getString(String property, String defaultValue);

	public boolean canCurrentUserSendAlerts();
}
