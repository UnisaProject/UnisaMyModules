package za.ac.unisa.lms.tools.cronjobs.jobs;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.InterruptableJob;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;
import org.quartz.UnableToInterruptJobException;
import org.sakaiproject.tool.api.Session;
import org.sakaiproject.tool.api.SessionManager;
import org.sakaiproject.exception.IdInvalidException;
import org.sakaiproject.exception.IdUnusedException;
import org.sakaiproject.exception.IdUsedException;
import org.sakaiproject.exception.InconsistentException;
import org.sakaiproject.exception.PermissionException;
import org.sakaiproject.component.cover.ServerConfigurationService;
import org.sakaiproject.content.api.ContentCollection;
import org.sakaiproject.content.api.ContentResource;
import org.sakaiproject.content.api.ContentHostingService;
import org.sakaiproject.entity.api.ResourceProperties;
import org.sakaiproject.entity.api.ResourcePropertiesEdit;
import org.sakaiproject.user.api.User;
import org.sakaiproject.user.api.UserDirectoryService;
import org.sakaiproject.component.cover.ComponentManager;

import za.ac.unisa.lms.tools.cronjobs.locks.SingleClusterInstanceJob;
import za.ac.unisa.utils.ValidCourse;

public class CoolResourceImportJob
	extends SingleClusterInstanceJob
	implements StatefulJob, InterruptableJob, OutputGeneratingJob {
	
	private SessionManager sessionManager;
	private ContentHostingService contentHostingService;
	private UserDirectoryService userDirectoryService;

	public static long runcount = 1L;
	private Log log = null;
	private boolean interrupt = false;
	private ByteArrayOutputStream outputStream;
	private PrintWriter output;
	private ContentCollection contentCollection = null;
	private String collectionNamePrefix = "/group/";
	private String collectionNameSuffix = "/";
	private String collectionReference = "";
	//private String coolResourceDir = "/home/riaans/cool/";
	private String coolResourceDir = "/data/coolresources/";
	private String loghome = System.getProperty("sakai.home");
	protected String CONTENT_TYPE = "application/octet-stream";
	protected boolean FIRST_REMOVE = false;
	protected boolean NEW_COLLECTION = true;
	protected boolean DEEP_COMPARISON = true;

	/**
	 *
	 * @param sitename
	 * @param code
	 * @param year
	 * @param period
	 * @throws Exception
	 */
	public void createOrUpdateResources(
		String coursesite,
		String subdir,
		String path,
		String fullname,
		String filename,
		String description
	) throws Exception {
		contentHostingService = (ContentHostingService) ComponentManager.get(ContentHostingService.class);
		
		String collectionName = collectionNamePrefix+coursesite+collectionNameSuffix+subdir+"/";
		/* Generic: Create a relevant content collection if it doesn't exist. */
		try {
			NEW_COLLECTION = false;
			contentCollection = (ContentCollection)contentHostingService.getCollection(collectionName);
			collectionReference = contentCollection.getReference();
			/*
			 * At this point, the collection exists. The question is whether to
			 * remove all resources and re-populating resources of type
			 * CONTENT_TYPE or possibly removing resources of CONTENT_TYPE only
			 * and then re-populating them. The latter is the current
			 * implementation.
			 */
		} catch (IdUnusedException ide) {
			NEW_COLLECTION = true;
			log.debug(collectionName+" collection not found, will try create it");
			output.println(collectionName+" collection not found, will try create it");

			contentCollection = contentHostingService.addCollection(collectionName);
			ResourceProperties properties = contentCollection.getProperties();
			properties.addProperty(
				ResourcePropertiesEdit.PROP_DISPLAY_NAME,
				subdir
			);
			//ContentHostingService.commitCollection(contentCollectionEdit);
			//collectionReference = contentCollectionEdit.getReference();
		} catch (Exception e) {
			log.error(e.getMessage());
			return;
		}
		log.debug("Preparing to add resources to " + collectionReference);

		/*if ((FIRST_REMOVE) && (!NEW_COLLECTION)) {
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
		addResources(collectionName, path, fullname, filename, description);
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
		String collectionName,
		String path,
		String fullname,
		String filename,
		String description
	) {
		sessionManager = (SessionManager) ComponentManager.get(SessionManager.class);
		contentHostingService = (ContentHostingService) ComponentManager.get(ContentHostingService.class);
		InputStream is;
		byte[] bytes;
		File file = null;
		try {
			ResourcePropertiesEdit newResourceEdit = contentHostingService.newResourceProperties();
			newResourceEdit.addProperty(
				ResourcePropertiesEdit.PROP_DISPLAY_NAME,
				filename
			);
			newResourceEdit.addProperty(
				ResourcePropertiesEdit.PROP_DESCRIPTION,
				description
			);

			file = new File(fullname);
			is = new FileInputStream(file);
			long length = file.length();

			if (length > 6291456) {
				File tooBig = new File(loghome+"/cool_too_big.log");
				FileOutputStream out = new FileOutputStream(tooBig, true);
				out.write((file.getAbsolutePath()+"\r\n").getBytes());
				out.flush();
				out.close();
			} else {

				bytes = new byte[(int)length];

				int offset = 0;
		        int numRead = 0;
		        while ((offset < bytes.length) && ((numRead=is.read(bytes, offset, bytes.length-offset)) >= 0)) {
		            offset += numRead;
		        }

		        // Ensure all the bytes have been read in
		        if (offset < bytes.length) {
		            throw new IOException("Could not completely read file " + file.getName());
		        }

		        is.close();

				ContentResource newResource = contentHostingService.addResource(
					collectionName+filename,
					CONTENT_TYPE,
					bytes,
					newResourceEdit,
					1
				);

				if (newResource == null) {
					log.debug(collectionName+" has failed to received a new resource and no exceptions have been thrown.");
				} else {
					log.info("This site has received the resource: "+filename);
					try {
						if (!file.delete()) {
							File tooBig = new File(loghome+"/cool_not_deleted.log");
							FileOutputStream out = new FileOutputStream(tooBig, true);
							out.write((file.getAbsolutePath()+"\r\n").getBytes());
							out.flush();
							out.close();
							throw new IOException("Could not delete : "+file.getAbsolutePath());
						} else {
							log.debug(file.getAbsolutePath()+" deleted successfully.");
						}
					} catch (Exception e) {
						log.error(e);
					}
				}
				newResource = null;
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
			try {
				if (!file.delete()) {
					File tooBig = new File(loghome+"/cool_not_deleted.log");
					FileOutputStream out = new FileOutputStream(tooBig, true);
					out.write((file.getAbsolutePath()+"\r\n").getBytes());
					out.flush();
					out.close();
					throw new IOException("Could not delete : "+file.getAbsolutePath());
				} else {
					log.debug(file.getAbsolutePath()+" deleted successfully. (FROM :IdUsedException)");
				}
			} catch (Exception ex) {
				log.error(ex);
			}
		} catch (IdInvalidException e) {
			log.error(e.getMessage());
			log.error("IdInvalidException");
		} catch (InconsistentException e) {
			log.error(e.getMessage());
			log.error("InconsistentException");
		} catch (Exception e) {
			log.error(e.getMessage());
			log.error("Exception");
		} finally {
			bytes = null;
			file = null;
			is = null;
			System.gc();
		}
	}

	private boolean validCourse(String coursesite) {
		String course = coursesite.substring(0, coursesite.indexOf("-"));
		log.debug("Course : "+course);
		ValidCourse vc = new ValidCourse();
		return vc.isValid(course);
	}

	public void executeLocked(JobExecutionContext context) throws JobExecutionException {
		
		sessionManager = (SessionManager) ComponentManager.get(SessionManager.class);
		userDirectoryService = (UserDirectoryService) ComponentManager.get(UserDirectoryService.class);

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

				File level1file = new File(coolResourceDir);
				String[] level1list = level1file.list();

				for (int k=0; k<level1list.length; k++) {
					System.gc();
					if (validCourse(level1list[k])) {
						File level2file = new File(level1file.getPath()+"/"+level1list[k]);
						String[] level2list = level2file.list();
						for (int z=0; z<level2list.length; z++) {
							System.gc();
							File level3file = new File(level2file.getPath()+"/"+level2list[z]);
							File[] level3list = level3file.listFiles();
							for (int x=0; x<level3list.length; x++) {
								updateLock();
								System.gc();
								String token = level3list[x].getName();
								String filename = token.substring(0, token.lastIndexOf("-~-"));
								String description = token.substring(token.lastIndexOf("-~-")+3, token.length());
								//StringTokenizer st = new StringTokenizer(level3list[x].getName(), "-\\~-");
								//String filename = st.nextToken();
								//String description = st.nextToken().trim();

								log.debug("FileName : "+filename);
								log.debug("Description : "+description);

								String coursesite = level1list[k];
								String period = coursesite.substring(coursesite.length()-2, coursesite.length());
								if (period.equalsIgnoreCase("01")) {
									period = "Y1";
								} else if (period.equalsIgnoreCase("02")) {
									period = "Y2";
								}
								coursesite = coursesite.substring(0, coursesite.length()-2)+period;

								createOrUpdateResources(coursesite, level2list[z], level3file.getPath(), level3list[x].getPath(), filename, description);
							}
						}
					} else {
						File inValid = new File(loghome+"/cool_invalid_course.log");
						FileOutputStream out = new FileOutputStream(inValid, true);
						out.write((level1list[k]+"\r\n").getBytes());
						out.flush();
						out.close();
					}
				}
				log.info("CoolResourceImportJob FINISHED...");
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
		log.debug("interrupt() : "+interrupt);
	}

	public String getOutput() {
		if (output != null) {
			output.flush();
			return outputStream.toString();
		}
		return null;
	}
}