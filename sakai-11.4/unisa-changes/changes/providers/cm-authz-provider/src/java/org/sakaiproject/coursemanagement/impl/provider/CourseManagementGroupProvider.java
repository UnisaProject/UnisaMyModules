/**********************************************************************************
 * $URL:$
 * $Id:$
 ***********************************************************************************
 *
 * Copyright (c) 2006, 2007, 2008 The Sakai Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.opensource.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 **********************************************************************************/
package org.sakaiproject.coursemanagement.impl.provider;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.Map.Entry;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sakaiproject.authz.api.GroupProvider;
import org.sakaiproject.component.cover.ComponentManager;
import org.sakaiproject.component.cover.ServerConfigurationService;
import org.sakaiproject.coursemanagement.api.CourseManagementService;
import org.sakaiproject.coursemanagement.api.Section;
import org.sakaiproject.coursemanagement.api.exception.IdNotFoundException;
import org.sakaiproject.user.api.UserDirectoryProvider;

/**
 * A Sakai GroupProvider that utilizes the CourseManagementService and the
 * CmMappingService to supply authz data to Sakai.  This implementation uses
 * a list of RoleResolvers, which can be used to resolve a user's role in a section
 * based on memberships in parent objects such as CourseSets.
 */
public class CourseManagementGroupProvider implements GroupProvider {
	private static final Logger log = LoggerFactory.getLogger(CourseManagementGroupProvider.class);

	// Configuration keys.
	public static final String SITE_ROLE_RESOLUTION_ORDER = "siteRoleResolutionOrder";

	//unisa change: add var userDirectoryProvider
	private UserDirectoryProvider userDirectoryProvider;
	// Handle packing and unpacking safely.
	public static String EID_SEPARATOR = "+";
	public static String QUOTED_SEPARATOR = "/+";
	public static Pattern EID_SEPARATOR_PATTERN = Pattern.compile("(?<!/)\\+");

	/** The course management service */
	CourseManagementService cmService;
			
	/** The role resolvers to use when looking for CM roles in the hierarchy*/
	List<RoleResolver> roleResolvers;
	
	/** The ordered list of role preferences.  Roles earlier in the list are preferred to those later in the list. */
	List<String> rolePreferences;
	
	/** Map to support external service configuration */
	Map<String, Object> configuration;
	
	// GroupProvider methods
	
	/**
	 * This method is not longer in use in Sakai.  It should be removed from the
	 * GroupProvider interface.
	 */
	public String getRole(String id, String user) {
		log.error("\n------------------------------------------------------------------\n");
		log.error("THIS METHOD IS NEVER CALLED IN SAKAI.  WHAT HAPPENED???");
		log.error("\n------------------------------------------------------------------\n");
		return null;
	}
		
	/**
	 * Provides a Map of user IDs to (Sakai) roles for the Course Section EIDs specified
	 * in the input AuthzGroup provider string.
	 */
	public Map<String, String> getUserRolesForGroup(String id) {
		if(log.isDebugEnabled()) log.debug("------------------CMGP.getUserRolesForGroup(" + id + ")");
		Map<String, String> userRoleMap = new HashMap<String, String>();
		
		String[] sectionEids = unpackId(id);
		if(log.isDebugEnabled()) log.debug(id + " is mapped to " + sectionEids.length + " sections");

		for (RoleResolver rr : roleResolvers) {
			for(int i=0; i < sectionEids.length; i++) {
				String sectionEid = sectionEids[i];
				Section section;
				try {
					section = cmService.getSection(sectionEid);
				} catch (IdNotFoundException e) {
					if (log.isWarnEnabled()) log.warn("Unable to find CM section " + sectionEid);
					continue;
				}
				if(log.isDebugEnabled()) log.debug("Looking for roles in section " + sectionEid);
			
				Map<String, String> rrUserRoleMap = rr.getUserRoles(cmService, section);

				for(Iterator<Entry<String, String>> rrRoleIter = rrUserRoleMap.entrySet().iterator(); rrRoleIter.hasNext();) {
					Entry<String, String> entry = rrRoleIter.next();
					String userEid = entry.getKey();
					String existingRole = userRoleMap.get(userEid);
					String rrRole = entry.getValue();

					// The Role Resolver has found no role for this user
					if(rrRole == null) {
						continue;
					}
					
					// Add or replace the role in the map if this is a more preferred role than the previous role
					if(existingRole == null) {
						if(log.isDebugEnabled()) log.debug("Adding "+ userEid + " to userRoleMap with role=" + rrRole);
						userRoleMap.put(userEid, rrRole);
					} else if(preferredRole(existingRole, rrRole).equals(rrRole)){
						if(log.isDebugEnabled()) log.debug("Changing "+ userEid + "'s role in userRoleMap from " + existingRole + " to " + rrRole + " for section " + sectionEid);
						userRoleMap.put(userEid, rrRole);
					}
				}
			}
		}
		if(log.isDebugEnabled()) log.debug("_____________getUserRolesForGroup=" + userRoleMap);
		return userRoleMap;
	}

	/**
	 * Provides a map of Course Section EIDs (which can be used as AuthzGroup provider IDs)
	 * to Sakai roles for a given user.
	 */
	public Map<String, String> getGroupRolesForUser(String userEid) {
		if(log.isDebugEnabled()) log.debug("------------------CMGP.getGroupRolesForUser(" + userEid + ")");
		Map<String, String> groupRoleMap = new HashMap<String, String>();
		
		for(RoleResolver rr : roleResolvers) {
			Map<String, String> rrGroupRoleMap = rr.getGroupRoles(cmService, userEid);
			if(log.isDebugEnabled()) log.debug("Found " + rrGroupRoleMap.size() + " groups for " + userEid + " from resolver " + rr.getClass().getName());

			// Only add the section eids if they aren't already in the map or if the new role has a higher preference.
			for(Iterator<Entry<String, String>> rrRoleIter = rrGroupRoleMap.entrySet().iterator(); rrRoleIter.hasNext();) {
				Entry<String, String> entry = rrRoleIter.next();
				String sectionEid = entry.getKey();
				String existingRole = groupRoleMap.get(sectionEid);
				String rrRole = entry.getValue();

				// The Role Resolver has found no role for this section
				if(rrRole == null) {
					continue;
				}
				
				if(existingRole ==  null) {
					if(log.isDebugEnabled()) log.debug("Adding " + sectionEid + " to groupRoleMap with sakai role" + rrRole + " for user " + userEid);
					groupRoleMap.put(sectionEid, rrRole);
				}  else if(preferredRole(existingRole, rrRole).equals(rrRole)){
					if(log.isDebugEnabled()) log.debug("Changing "+ userEid + "'s role in groupRoleMap from " + existingRole + " to " + rrRole + " for section " + sectionEid);
					groupRoleMap.put(sectionEid, rrRole);
				}
			}
		}
		
		//unisa change: Add myStudents to lecturers and myAdmin to student during Login		
		  if (userEid.matches("[a-zA-Z].*")) {			  
			  userDirectoryProvider = (UserDirectoryProvider) ComponentManager.get(UserDirectoryProvider.class);
			  String checkUserType = userDirectoryProvider.checkUserType(userEid);
			  //if the basepath contains guest meaning it is a guest user. For guest user no default sites
			  if(checkUserType.contains("guest")){				  
			  }else{
				groupRoleMap.put("mystudents", "access");
			  }
		  }else{
			  groupRoleMap.put("myadmin", "access");
			 }
		return groupRoleMap;
	}

	/**
	 * {@inheritDoc}
	 */
	public String packId(String[] ids) {
		if(ids == null || ids.length == 0) {
			return null;
		}
		
		if(ids.length == 1) {
			return ids[0];
		}
		
		StringBuilder sb = new StringBuilder();
		for(int i=0; i<ids.length; i++) {
			// First, escape any embedded separator characters.
			String eid = (ids[i]).replace(EID_SEPARATOR, QUOTED_SEPARATOR);
			
			sb.append(eid);
			if(i < ids.length - 1) {
				sb.append(EID_SEPARATOR);
			}
		}
		return sb.toString();
	}

	public String[] unpackId(String id) {
		if(id == null) {
			return new String[0];
		}
		
		String[] ids = EID_SEPARATOR_PATTERN.split(id);
		
		// Unescape any embedded separator characters.
		for(int i=0; i<ids.length; i++) {
			String eid = (ids[i]).replace(QUOTED_SEPARATOR, EID_SEPARATOR);
			ids[i] = eid;
		}

		return ids;
	}
	

	// Utility methods

	public void init() {
		if(log.isInfoEnabled()) log.info("initializing " + this.getClass().getName());
		
		/**
		 * Use the externally supplied configuration map, if any.
		 */
		if (configuration != null) {
			if (rolePreferences != null) {
				log.warn("Both a provider configuration object and direct role mappings have been defined. " +
					"The configuration object will take precedence.");
			}
			setRolePreferences((List<String>)configuration.get(SITE_ROLE_RESOLUTION_ORDER));
		}
	}
	
	public void destroy() {
		if(log.isInfoEnabled()) log.info("destroying " + this.getClass().getName());
	}
	
	// Dependency injection
	
	public void setCmService(CourseManagementService cmService) {
		this.cmService = cmService;
	}

	public void setRoleResolvers(List<RoleResolver> roleResolvers) {
		this.roleResolvers = roleResolvers;
	}
	
	public String preferredRole(String one, String other) {
		int oneIndex = rolePreferences.indexOf(one);
		int otherIndex = rolePreferences.indexOf(other);
		if(otherIndex == -1) {
			return one;
		}
		if(oneIndex == -1) {
			return other;
		}
		return oneIndex < otherIndex ? one : other;
	}

	public void setRolePreferences(List<String> rolePreferences) {
		this.rolePreferences = rolePreferences;
	}

	public void setConfiguration(Map<String, Object> configuration) {
		this.configuration = configuration;
	}

	/*Unisa change: comment out the following method and add unisa specific method to validate the courses As unisa does not uses course section info*/
/*	public boolean groupExists(String groupId) {
		
		if (cmService.isSectionDefined(groupId)) 
			return true;
		
				
		return false;
	}*/
	
 public boolean groupExists(String arg0) {

		String providerIds = ServerConfigurationService.getString("providerIds");
		StringTokenizer st = new StringTokenizer(providerIds, ",");
		int tokenCount = st.countTokens();
		String[] array = new String[tokenCount];
		boolean test = false;
		for (int i = 0; i < tokenCount; i++) {
			String testRealm = st.nextToken();
		    if(testRealm.equals(arg0)) {
		    	test = true;
		    	i = tokenCount;
		    }
		}
		
		if (!test){
			//System.out.println("Group Exists is not equal to mystudents and myadmin checking for course site or postgraduate site");
			boolean isValidSiteOrGroup = false;
			if (arg0.length() == 8) {
				//System.out.println("Check for a Postgraduate site");
				String col = arg0.substring(0,3);
				String num = arg0.substring(3,4);
				String cat = arg0.substring(4,7);
				String let = arg0.substring(7);
				List<String> numarray = Arrays.asList("1","2","3","4","5","6","7","8","9");
				List<String> letarray = Arrays.asList("U", "H", "M","D");
				
				if (col.equalsIgnoreCase("COL")&&cat.equalsIgnoreCase("CAT")&&numarray.contains(num)&&letarray.contains(let) ) {
					//System.out.println("Found valid posgraduate site");
					isValidSiteOrGroup = true;
				}
			} else if (arg0.length() >= 13) {
				//System.out.println("Group Exist arg has right lenght 13");
				String delimiter = "-";
				String [] temp;
				temp = arg0.split(delimiter);
				for (int i = 0; i < temp.length;i++){
					if ( i == 0){
						if (temp[i].length() == 7){
							//System.out.println("Subject found right length (7) "+ temp[i]);
							isValidSiteOrGroup = true;
						} else {
							isValidSiteOrGroup = false;
						} 
					} else if (i == 1){
						if (temp[i].length() == 2){
							//System.out.println("Year found now check if it is a int");
							try
							{
								Integer.parseInt(temp[i]);
								//System.out.println(temp[i] + " is valid integer number");
								isValidSiteOrGroup = true;
							}
							catch(NumberFormatException nme)
							{
								isValidSiteOrGroup = false;
								//System.out.println(temp[i] + " is not a valid integer number");
							}
						}
					} else if (i == 2){
						if (temp[i].length() == 2){
							//System.out.println("Period found check if valid");
							List<String> periods = Arrays.asList("S1","S2","Y1","Y2");
							if (periods.contains(temp[i])){
								//System.out.println("Found a valid period :"+arg0);
								isValidSiteOrGroup = true;
							} else {
								isValidSiteOrGroup = false;
							}
							
						}
					}
				}
			} else	{
				isValidSiteOrGroup = false;
				//System.out.println("Not a Course Site/Postgraduate returing false");
			}
			if (isValidSiteOrGroup){
				test = true;
			}
		}
		
		
		// TODO Auto-generated method stub
		return test;
	}
}
