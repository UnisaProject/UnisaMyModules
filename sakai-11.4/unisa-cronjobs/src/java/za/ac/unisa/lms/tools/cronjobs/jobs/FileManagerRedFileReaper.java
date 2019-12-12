package za.ac.unisa.lms.tools.cronjobs.jobs;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

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
import org.sakaiproject.tool.api.Session;
import org.sakaiproject.tool.api.SessionManager;
import org.sakaiproject.user.api.User;
import org.sakaiproject.user.api.UserDirectoryService;

import za.ac.unisa.lms.tools.cronjobs.dao.CourseImportDAO;
import za.ac.unisa.lms.tools.cronjobs.dao.FileManagerRedFileReaperDAO;
import za.ac.unisa.lms.tools.cronjobs.dao.Resource;
import za.ac.unisa.lms.tools.cronjobs.locks.SingleClusterInstanceJob;

public class FileManagerRedFileReaper
	extends SingleClusterInstanceJob
	implements StatefulJob, InterruptableJob, OutputGeneratingJob {

	private SessionManager sessionManager;
	private UserDirectoryService userDirectoryService;
	
	public static long runcount = 1L;
	private Log log = null;
	private boolean interrupt = false;
	private ByteArrayOutputStream outputStream;
	private PrintWriter output;

	public ListOrderedMap getResources(String code, String year) {
		FileManagerRedFileReaperDAO dao = new FileManagerRedFileReaperDAO();
		List<?> material = dao.getMaterial(code, year);
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

			resource.setWebid(new Integer(course.get("WEBID").toString()).intValue());
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

	/**
	 *
	 * @param code
	 * @param year
	 */
	public void checkResources(
		String code,
		String year
	) {
		File file = new File(System.getProperty("sakai.home")+"/fm_missing_files.dat");
		FileOutputStream writer = null;
		try {
			writer = new FileOutputStream(file, true);
			ListOrderedMap newResourcesMap = getResources(code, year);
			//String resourceDisplayName = "";
			String resourceURL = "";
			Set<?> newResourceSet = newResourcesMap.entrySet();
			Iterator<?> newResourceIterator = newResourceSet.iterator();
			while (newResourceIterator.hasNext()) {
				Map.Entry entry = (Map.Entry) newResourceIterator.next();
				Resource resource = (Resource)entry.getValue();
				//resourceDisplayName = (String)entry.getKey();
				resourceURL = resource.getUrl();
				URL url = new URL(resourceURL);
				// Create a trust manager that does not validate certificate chains
			    TrustManager[] trustAllCerts = new TrustManager[]{
			        new X509TrustManager() {
			            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
			                return null;
			            }
			            public void checkClientTrusted(
			            		java.security.cert.X509Certificate[] certs, String authType
		            		) {}
			            public void checkServerTrusted(
			            		java.security.cert.X509Certificate[] certs, String authType
			            ) {}
			        }
			    };
				HttpsURLConnection connection = (HttpsURLConnection)url.openConnection();
				SSLContext sc = SSLContext.getInstance("SSL");
		        sc.init(null, trustAllCerts, new java.security.SecureRandom());
		        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
				connection.connect();
				int response = connection.getResponseCode();
				if (response == 404) {
					writer.write((resource.getWebid()+"\t"+code+"\t  ("+resourceURL+")").getBytes());
					writer.write("\n\r".getBytes());
					writer.flush();
					log.error("Resource : "+code+" ("+resourceURL+") is invalid !!! ("+response+")");
				} else {
					log.info("Resource : "+code+" ("+resourceURL+") found. ("+response+")");
				}
				writer.flush();
				connection.disconnect();
			}
		} catch (MalformedURLException e) {
			log.error(e);
		} catch (IOException e) {
			log.error(e);
		} catch (Exception e) {
			log.error(e);
		} finally {
			try {
				writer.flush();
				writer.close();
				writer = null;
			} catch (IOException e) {
				log.error(e);
			}
		}
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

					checkResources(
						course.get("FK_SUNCODE").toString(),
						course.get("MK_ACADEMIC_YEAR").toString().substring(2)
					);
					if (interrupt) {
						throw new Exception("Execution interrupted.");
					}
				}
				log.info("FileManagerRedFileReaper FINISHED...");
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