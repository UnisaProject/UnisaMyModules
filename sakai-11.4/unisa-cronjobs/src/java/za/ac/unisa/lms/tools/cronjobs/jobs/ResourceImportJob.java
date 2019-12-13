package za.ac.unisa.lms.tools.cronjobs.jobs;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.map.ListOrderedMap;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.InterruptableJob;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;
import org.quartz.UnableToInterruptJobException;
import org.sakaiproject.component.cover.ComponentManager;
import org.sakaiproject.component.cover.ServerConfigurationService;
import org.sakaiproject.content.api.ContentCollectionEdit;
import org.sakaiproject.content.api.ContentResource;
import org.sakaiproject.content.api.ContentHostingService;
import org.sakaiproject.entity.api.ResourcePropertiesEdit;
import org.sakaiproject.exception.IdInvalidException;
import org.sakaiproject.exception.IdUnusedException;
import org.sakaiproject.exception.IdUsedException;
import org.sakaiproject.exception.InUseException;
import org.sakaiproject.exception.InconsistentException;
import org.sakaiproject.exception.PermissionException;
import org.sakaiproject.exception.TypeException;
import org.sakaiproject.tool.api.Session;
import org.sakaiproject.tool.api.SessionManager;
import org.sakaiproject.user.api.User;
import org.sakaiproject.user.api.UserDirectoryService;

import za.ac.unisa.lms.tools.cronjobs.dao.CourseImportDAO;
import za.ac.unisa.lms.tools.cronjobs.dao.Resource;
import za.ac.unisa.lms.tools.cronjobs.dao.ResourceImportDAO;
import za.ac.unisa.lms.tools.cronjobs.locks.SingleClusterInstanceJob;

public class ResourceImportJob
	extends SingleClusterInstanceJob
	implements StatefulJob, InterruptableJob, OutputGeneratingJob {
	
	private ContentHostingService contentHostingService;
	private SessionManager sessionManager;
	private UserDirectoryService userDirectoryService;

	public static long runcount = 1L;
	private Log log = null;
	private boolean interrupt = false;
	private ByteArrayOutputStream outputStream;
	private PrintWriter output;
	private String collectionNamePrefix = "/group/";
	private String collectionNameSuffix = "/Official Study Material/";
	protected String CONTENT_TYPE = "text/url";
	protected boolean FIRST_REMOVE = true;
	protected boolean DEEP_COMPARISON = true;

	private void editCollectionDisplayName(
		String collectionName,
		String displayName
	) throws IdUnusedException, TypeException, PermissionException, InUseException {
		
		contentHostingService = (ContentHostingService) ComponentManager.get(ContentHostingService.class);
		
		ContentCollectionEdit newCollection = contentHostingService.editCollection(collectionName);
		ResourcePropertiesEdit collectionPropEdit = newCollection.getPropertiesEdit();

		collectionPropEdit.addProperty(
			ResourcePropertiesEdit.PROP_DISPLAY_NAME,
			displayName
		);
		contentHostingService.commitCollection(newCollection);
	}

	public void createOrUpdateResources(
		String sitename,
		String code,
		String year,
		String period
	) throws Exception {
		
		contentHostingService = (ContentHostingService) ComponentManager.get(ContentHostingService.class);
		
		String collectionName = collectionNamePrefix+sitename + this.collectionNameSuffix;
		String displayName = collectionNameSuffix.substring(1, collectionNameSuffix.length()-1);
		try {
			contentHostingService.getCollection(collectionName);
			editCollectionDisplayName(collectionName, displayName);

		} catch (IdUnusedException ide) {
			log.debug(collectionName+" collection not found, will try create it");
			output.println(collectionName+" collection not found, will try create it");
			try {
				ContentCollectionEdit newCollection = contentHostingService.addCollection(collectionName);
				contentHostingService.commitCollection(newCollection);

				log.info("Successfully created resource collection : "+collectionName);

				editCollectionDisplayName(collectionName, displayName);
				log.debug("Successfully edited resource collection : "+collectionName);
			} catch (Exception e) {
				log.error(e);
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			return;
		}
		log.debug("Preparing to add resources to "+collectionName);
		/*if (FIRST_REMOVE) {
			// Generic: Find existing resources in the collection //
			List resourcesList = contentCollection.getMemberResources();
			// Generic: Prepare resource list iteration //
			Iterator listIterator = resourcesList.iterator();
			// iterate and delete all of type CONTENT_TYPE //
			while (listIterator.hasNext()) {
				Object o = listIterator.next();
				if (o instanceof ContentResource) {
					ContentResource res = (ContentResource)o;
					String resType = res.getProperties().getProperty(ResourceProperties.PROP_CONTENT_TYPE);
					if (resType != null && resType.equals(CONTENT_TYPE)) {
						ContentHostingService.removeResource(res.getId());
					}
				} else if (o instanceof ContentCollection) {
					ContentCollection col = (ContentCollection)o;
					String resType = col.getProperties().getProperty(ResourceProperties.PROP_CONTENT_TYPE);
					if (resType != null && resType.equals(CONTENT_TYPE)) {
						ContentHostingService.removeCollection(col.getId());
					}
				}
			}
		}*/
		addResources(sitename, code, year, period, collectionName);
	}

	/**
	 *
	 * @param sitename
	 * @param code
	 * @param year
	 * @param period
	 * @param collectionName
	 */
	public void addResources(
		String sitename,
		String code,
		String year,
		String period,
		String collectionName
	) {
		contentHostingService = (ContentHostingService) ComponentManager.get(ContentHostingService.class);
		sessionManager = (SessionManager) ComponentManager.get(SessionManager.class);
		
		ListOrderedMap newResourcesMap = prepareResources(code, year, period);
		String newResourceDisplayName = "";
		String newResourceContent = "";
		Set<?> newResourceSet = newResourcesMap.entrySet();
		Iterator<?> newResourceIterator = newResourceSet.iterator();
		while (newResourceIterator.hasNext()) {
			Map.Entry entry = (Map.Entry) newResourceIterator.next();
			Resource resource = (Resource)entry.getValue();
			newResourceDisplayName = (String) entry.getKey();
			newResourceContent = resource.getUrl();
			try {
				String type = resource.getType();
				String resourceName = "";
				if ((type.equalsIgnoreCase("tl")) || (type.equalsIgnoreCase("tw")) || (type.equalsIgnoreCase("tp"))) {
					resourceName = newResourceDisplayName;
				} else {
					resourceName = "Study Guide "+
						resource.getRefNo()+
						" ("+resource.getStartAcademicYear()+
						" - "+resource.getEndAcademicYear()+
						") "+resource.getLanguage();
				}

				ResourcePropertiesEdit newResourceEdit = contentHostingService.newResourceProperties();
				newResourceEdit.addProperty(
					ResourcePropertiesEdit.PROP_DISPLAY_NAME,
					resourceName
				);
				newResourceEdit.addProperty(
					ResourcePropertiesEdit.PROP_DESCRIPTION,
					resource.getDescription()
				);
				newResourceEdit.addProperty(
					ResourcePropertiesEdit.PROP_CREATION_DATE,
					resource.getDateCreated()
				);

				ContentResource newResource = null;

				resourceName = collectionName+resourceName;
				try {
					newResource = contentHostingService.getResource(resourceName);
				} catch (IdUnusedException e) {
					newResource = contentHostingService.addResource(
						resourceName,
						CONTENT_TYPE,
						newResourceContent.getBytes(),
						newResourceEdit,
						1
					);
					if (newResource == null) {
						log.error(sitename+" has failed to received a new resource and no exceptions have been thrown.");
					} else {
						log.info("This site has received the resource: "+newResourceContent+" ("+sitename+")");
					}
				}
			} catch (PermissionException e) {
				log.error(e.getMessage());
				log.error("PermissionException");
				log.error(
					"<br/>Sorry "+
					sessionManager.getCurrentSession().getUserId()+
					", you don't have permission to add this resource."
				);
			} catch (IdUsedException e) {
				log.error(e.getMessage());
				log.error("IdUsedException");
				log.error("<br/>This site already has the resource: "+newResourceContent);
			} catch (IdInvalidException e) {
				log.error(e.getMessage());
				log.error("IdInvalidException");
			} catch (InconsistentException e) {
				log.error(e.getMessage());
				log.error("InconsistentException");
			} catch (Exception e) {
				log.error(e.getMessage());
				log.error("Exception");
			}
		}

	}

	public ListOrderedMap prepareResources(String code, String year, String period) {
		ResourceImportDAO dao = new ResourceImportDAO();
		List<?> material = dao.getMaterial(code, year, period);
		ListOrderedMap hash = new ListOrderedMap();

		Iterator<?> ci = material.iterator();
		while (ci.hasNext()) {
			Resource resource = new Resource();
			ListOrderedMap course = (ListOrderedMap)ci.next();

			boolean collection = dao.isPartOfCollection(
				course.get("ACADEMIC_YEAR").toString(),
				course.get("ACADEMIC_PERIOD").toString(),
				course.get("NAME").toString(),
				course.get("FK_STMTYPCODE").toString(),
				course.get("NR").toString(),
				course.get("DIN_LANGUAGE").toString()
			);

			String name = course.get("REF_NO").toString();
			name += "_"+course.get("START_ACADEMIC_YEA");
			name += "_"+course.get("ACADEMIC_PERIOD");
			name += "_"+course.get("DIN_LANGUAGE");

			String url = ServerConfigurationService.getString("resources.path");
			if (collection) {
				url += "collect/";
			}
			url += course.get("NAME");

			String type = course.get("FK_STMTYPCODE").toString().toLowerCase();
			if ((type.equalsIgnoreCase("tl")) || (type.equalsIgnoreCase("tw")) || (type.equalsIgnoreCase("tp"))) {
				type = "tl";
			} else {
				type = "sg";
			}
			url += "/"+type;
			url += "/"+name.toLowerCase();
			url += "."+course.get("FORMAT").toString().toLowerCase();

			resource.setEndAcademicYear(new Integer(course.get("END_ACADEMIC_YEAR").toString()).intValue());
			resource.setStartAcademicYear(new Integer(course.get("START_ACADEMIC_YEA").toString()).intValue());
			resource.setLanguage(course.get("DIN_LANGUAGE").toString().toUpperCase());
			resource.setRefNo(course.get("REF_NO").toString());
			resource.setCollection(collection);
			resource.setTitle(name);
			resource.setType(course.get("FK_STMTYPCODE").toString().toUpperCase());
			resource.setUrl(url);
			resource.setDateCreated(course.get("LAST_MODIFIED").toString());
			resource.setDescription(
				(course.get("PUBLISHING_DESCRIP") != null)
				?course.get("PUBLISHING_DESCRIP").toString()
				:""
			);

			hash.put(name, resource);
		}
		return hash;
	}

	public String getCourseType(String semester_code) {
		int code = Integer.parseInt(semester_code);
		switch (code) {
		case 0: {
			return "Y1";
		}
		case 1: {
			return "S1";
		}
		case 2: {
			return "S2";
		}
		case 6: {
			return "Y2";
		}
		}
		return "?";
	}

	public void executeLocked(JobExecutionContext context) throws JobExecutionException {
		
		userDirectoryService = (UserDirectoryService) ComponentManager.get(UserDirectoryService.class);
		sessionManager = (SessionManager) ComponentManager.get(SessionManager.class);
		
		log = LogFactory.getLog(this.getClass());
		outputStream = new ByteArrayOutputStream();
		output = new PrintWriter(outputStream);

		runcount++;

		log.debug(runcount + ": Started.");
		try {
			User user = userDirectoryService.authenticate(
				ServerConfigurationService.getString("admin.user"),
				ServerConfigurationService.getString("admin.password")
			);
			if (user != null) {
				log.debug(runcount + ": Authenticated.");
				Session session = sessionManager.startSession();
				if (session == null) throw new JobExecutionException("No session");
				log.debug(runcount + ": Session started: " + session.getId());

				session.setUserEid(user.getId());
				session.setUserId(user.getId());
				sessionManager.setCurrentSession(session);

				CourseImportDAO dao = new CourseImportDAO();

				Calendar cal = Calendar.getInstance();
				cal.setTime(new Date());
				int currentYear = cal.get(Calendar.YEAR);

				List<?> courses = dao.getCourses(currentYear);
				log.info(runcount+": Query returned "+courses.size()+" courses");
				Iterator<?> ci = courses.iterator();
				while (ci.hasNext()) {
					updateLock();
					ListOrderedMap course = (ListOrderedMap)ci.next();
					String siteName = (String)course.get("FK_SUNCODE");
					siteName += "-"+course.get("MK_ACADEMIC_YEAR").toString().substring(2);
					siteName += "-"+getCourseType(course.get("SEMESTER_PERIOD").toString());

					createOrUpdateResources(
						siteName,
						course.get("FK_SUNCODE").toString(),
						course.get("MK_ACADEMIC_YEAR").toString().substring(2),
						course.get("SEMESTER_PERIOD").toString()
					);
					if (interrupt) {
						throw new Exception("Execution interrupted.");
					}
				}
				log.info("ResourceImportJob FINISHED...");
			} else {
				throw new Exception("User not authenticated");
			}

		} catch (Exception e) {
			log.error(runcount+": Exception "+e.getClass().getName()+": "+e.getMessage());
			throw new JobExecutionException(e);
		}
		log.debug(runcount+": Stopped. "+(runcount));
	}

	public void interrupt() throws UnableToInterruptJobException {
		super.interrupt();
		interrupt = true;
	}

	public String getOutput() {
		if (output != null) {
			output.flush();
			return outputStream.toString();
		}
		return null;
	}
}