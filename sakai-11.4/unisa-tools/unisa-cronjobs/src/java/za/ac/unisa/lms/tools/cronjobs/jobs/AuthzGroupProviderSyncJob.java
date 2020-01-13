package za.ac.unisa.lms.tools.cronjobs.jobs;

import org.quartz.InterruptableJob;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;

import za.ac.unisa.lms.tools.cronjobs.dao.DropboxEventsUpdateDAO;
import za.ac.unisa.lms.tools.cronjobs.locks.SingleClusterInstanceJob;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;
import org.sakaiproject.authz.api.AuthzGroup;
import org.sakaiproject.authz.api.AuthzPermissionException;
import org.sakaiproject.authz.api.GroupNotDefinedException;
import org.sakaiproject.authz.api.AuthzGroupService;
import org.sakaiproject.javax.PagingPosition;
import org.sakaiproject.tool.api.Session;
import org.sakaiproject.tool.api.SessionManager;
import org.sakaiproject.component.cover.ComponentManager;

public class AuthzGroupProviderSyncJob extends SingleClusterInstanceJob implements
StatefulJob, InterruptableJob, OutputGeneratingJob {
	
	
	
private static final Log log = LogFactory.getLog(AuthzGroupProviderSyncJob.class);
	
	// If it's been modified in the last hour ignore it.
	private long refreshAge = 3600000;
	
	private int batchSize = 200;
	
	private SessionManager sessionManager;
	private AuthzGroupService authzGroupService;
	

	/**
	 * Get an iterator for the authzgroups and load them in chunks.
	 * It is possible that we miss one if the order changes while we are doing this
	 * but as we are just syncing stuff it's not too crucial.
	 */
	private Iterator<AuthzGroup> getAuthzGroups() {
		return new Iterator<AuthzGroup>() {
			
			private int current = 1;
			private int size = batchSize;
			private boolean tryGetMore = true;
			private Iterator<AuthzGroup> internalIt = Collections.EMPTY_LIST.iterator(); 
			
			private boolean checkOrLoadNext() {
				if (internalIt.hasNext()) {
					return true;
				}
				if (tryGetMore) {
					List groups = authzGroupService.getAuthzGroups(null, new PagingPosition(current, current+size));
					if (groups.size() < size) {
						tryGetMore = false;
					}
					current += groups.size();
					internalIt = groups.iterator();
				}
				return internalIt.hasNext();
			}

		public boolean hasNext() {
				return checkOrLoadNext();
			}

			public AuthzGroup next() {
				if (checkOrLoadNext()) {
					return internalIt.next();
				}
				throw new NoSuchElementException();
			}

			public void remove() {
				throw new UnsupportedOperationException();
			}
		};
	}

public void setRefreshAge(long refreshAge) {
		this.refreshAge = refreshAge;
	}
	
	public void setBatchSize(int batchSize) {
		this.batchSize = batchSize;
	}

	/*public void setSessionManager(SessionManager sessionManager) {
		this.sessionManager = sessionManager;
	}
*/
	public void setAuthzGroupService(AuthzGroupService authzGroupService) {
		this.authzGroupService = authzGroupService;
	}
	
	
	public String getOutput() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void executeLocked(JobExecutionContext arg0)
			throws JobExecutionException { 
		
		  sessionManager = (SessionManager) ComponentManager.get(SessionManager.class);
		  authzGroupService = (AuthzGroupService) ComponentManager.get(AuthzGroupService.class);
		  Session session = sessionManager.getCurrentSession();
			session.setUserEid("admin");
			session.setUserId("admin");
			int groupsTotal = 0, groupsProcessed = 0, groupsUpdated = 0, groupsNoProvider = 0, groupsTooNew = 0;
			long start = System.currentTimeMillis();
			try {
				groupsTotal = authzGroupService.countAuthzGroups(null);
				Iterator<AuthzGroup> groupsIt = getAuthzGroups();
				while (groupsIt.hasNext()) {
					AuthzGroup group = groupsIt.next();
					groupsProcessed++;
					if (group.getProviderGroupId() != null && group.getProviderGroupId().length() > 0) {
						if (System.currentTimeMillis() - group.getModifiedTime().getTime() > refreshAge) {
							try {
								// Need to load the group before we can save it.
								AuthzGroup groupToRefresh = authzGroupService.getAuthzGroup(group.getId());
								authzGroupService.save(groupToRefresh);
								groupsUpdated++;
							} catch (GroupNotDefinedException e) {
								log.warn("Failed to update group ("+ group.getReference()+ "), maybe deleted while processing");
							} catch (AuthzPermissionException e) {
								log.error("Lack of permission to update group: "+ group.getReference());
								throw new JobExecutionException(e);
							}
						} else {
							groupsTooNew++;
							if (log.isDebugEnabled()) {
								log.debug("Ignored group as it has been updated too recently: "+ group.getReference());
							}
						}
					} else {
						groupsNoProvider++;
						if (log.isDebugEnabled()) {
							log.debug("Ignored group as it doesn't have any provided groups: "+ group.getReference());
						}
					}
				}
			} finally {
				long duration = System.currentTimeMillis() - start;
				log.info("Summary (duration: "+ duration+ ") -"+
						" Total: "+ groupsTotal+
						" Processed: "+ groupsProcessed+
						" Updated: "+ groupsUpdated+
						" No Provider: "+ groupsNoProvider+
						" Too New: "+ groupsTooNew
						);
				session.invalidate();
			}
		}
}
