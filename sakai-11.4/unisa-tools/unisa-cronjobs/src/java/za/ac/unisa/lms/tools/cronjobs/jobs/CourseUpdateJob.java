package za.ac.unisa.lms.tools.cronjobs.jobs;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.InterruptableJob;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;
import org.quartz.UnableToInterruptJobException;
import org.sakaiproject.authz.api.Role;
import org.sakaiproject.authz.api.RoleAlreadyDefinedException;
import org.sakaiproject.component.cover.ComponentManager;
import org.sakaiproject.component.cover.ServerConfigurationService;
import org.sakaiproject.exception.InUseException;
import org.sakaiproject.site.api.Site;
import org.sakaiproject.site.api.SitePage;
import org.sakaiproject.site.api.ToolConfiguration;
import org.sakaiproject.site.api.SiteService;
import org.sakaiproject.tool.api.Session;
import org.sakaiproject.tool.api.SessionManager;
import org.sakaiproject.user.api.User;
import org.sakaiproject.user.api.UserDirectoryService;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import za.ac.unisa.lms.tools.cronjobs.dao.SakaiDAO;
import za.ac.unisa.lms.tools.cronjobs.locks.SingleClusterInstanceJob;
import za.ac.unisa.lms.tools.cronjobs.util.SiteManagement;

public class CourseUpdateJob extends SingleClusterInstanceJob implements StatefulJob, InterruptableJob, OutputGeneratingJob {

	private SiteService siteService;
	private SessionManager sessionManager;
	private UserDirectoryService userDirectoryService;
	
	public static long runcount = 1L;
	private Log log = null;
	private boolean interrupt = false;
	private ByteArrayOutputStream outputStream;
	private PrintWriter output;

	private String getRequiredAttribute(Element element, String attribute) throws Exception {
		String value = element.getAttribute(attribute);
		if (value == null) {
			throw new Exception("Attribute \""+attribute+"\" of element \""+element.getTagName()+"\" is required");
		}
		value = value.trim();
		if (value.equals("")) {
			throw new Exception("Attribute \""+attribute+"\" of element \""+element.getTagName()+"\" must have a non-whitespace value");
		}

		return value;
	}

	public void updateSiteFromTemplate(String courseSiteRef, Site siteTemplate) throws Exception {
		
		siteService = (SiteService) ComponentManager.get(SiteService.class);
		// get the target courseSite
		Site courseSite = siteService.getSite(courseSiteRef);
		// prepare a coursePageMap for the target course pages
		HashMap<String, SitePage> coursePageMap = new HashMap<String, SitePage>();
		List<SitePage> coursePageList = courseSite.getOrderedPages();
		Iterator<SitePage> coursePageIter = coursePageList.iterator();
		// populate the coursePageMap of course pages
		while(coursePageIter.hasNext()) {
			SitePage sitePage = (SitePage)coursePageIter.next();
			coursePageMap.put(sitePage.getTitle(), sitePage);
			log.info("your course has page ("+sitePage.getTitle()+") - saving in hashmap");
		}
		Iterator<?> templatePageIter = siteTemplate.getOrderedPages().iterator();
		// for each page in the template
		//SitePage tempSitePage = null;
		while(templatePageIter.hasNext()) {
			SitePage tempSitePage = (SitePage)templatePageIter.next();
			Iterator<?> toolIter = tempSitePage.getTools().iterator();
			//SitePage coursePage = courseSite.getPage(tempSitePage.getTitle());
			if(tempSitePage.getTitle().startsWith("REMOVE_PAGE_")) {
				SitePage coursePageToRemove = (SitePage)coursePageMap.get(tempSitePage.getTitle().substring(12));
				if(coursePageToRemove != null) {
					coursePageList.remove(coursePageToRemove);
					coursePageMap.remove(tempSitePage.getTitle().substring(12));
					log.info("removed old page ("+tempSitePage.getTitle().substring(12)+")");
				}
				SitePage removePageToRemove = (SitePage)coursePageMap.get(tempSitePage.getTitle());
				if(removePageToRemove != null) {
					coursePageList.remove(removePageToRemove);
					coursePageMap.remove(tempSitePage.getTitle());
					log.info("removed old page ("+tempSitePage.getTitle()+")");
				}
			} else {
				SitePage coursePage = (SitePage)coursePageMap.get(tempSitePage.getTitle());
				// if the courseSite doesn't have the page, add it
				if(coursePage == null) {
					//log.debug("coursePage is null - add it as new ("+tempSitePage.getTitle()+")");
					SitePage newSitePage = courseSite.addPage();
					newSitePage.setTitle(tempSitePage.getTitle());
					while(toolIter.hasNext()) {
						ToolConfiguration templateToolConfig = (ToolConfiguration)toolIter.next();
						ToolConfiguration newToolConfig = newSitePage.addTool();
						newToolConfig.setTool(templateToolConfig.getTitle(), templateToolConfig.getTool());
						Properties toolProperties = templateToolConfig.getPlacementConfig();
						Iterator<?> propertyIter = toolProperties.keySet().iterator();
						String property = "";
						while(propertyIter.hasNext()) {
							property = (String)propertyIter.next();
							newToolConfig.getPlacementConfig().setProperty(property,toolProperties.getProperty(property));
						}
						//don't save so soon, allow full save of entire site at the end.
						//newToolConfig.save();
						log.info("added new tool ("+templateToolConfig.getTitle()+")");
					}
					// otherwise check order, then remove it from the map
				} else {
					log.info("course site already has this page: "+coursePage.getTitle());
					while(toolIter.hasNext()) {
						ToolConfiguration templateToolConfig = (ToolConfiguration)toolIter.next();
						ToolConfiguration courseToolConfig = null;
						List<?> toolsList = coursePage.getTools();
						Iterator<?> toolsListIter = toolsList.iterator();
						log.info("looking for templateConfig.getToolId(): "+templateToolConfig.getToolId());
						boolean coursePageLacksTool = true;
						while(toolsListIter.hasNext()) {
							courseToolConfig = (ToolConfiguration)toolsListIter.next();
							if(templateToolConfig.getToolId().equalsIgnoreCase(courseToolConfig.getToolId())) {
								coursePageLacksTool = false;
								log.info("this page has a tool with getToolId() :"+templateToolConfig.getToolId());
								break;
							}
						}
						if(coursePageLacksTool) {
							courseToolConfig = coursePage.addTool();
							courseToolConfig.setTool(templateToolConfig.getTitle(), templateToolConfig.getTool());
							log.info("added new tool ("+templateToolConfig.getTitle()+")");
						}
						Properties toolProperties = templateToolConfig.getPlacementConfig();
						Iterator<?> propertyIter = toolProperties.keySet().iterator();
						String property = "";
						while(propertyIter.hasNext()) {
							property = (String)propertyIter.next();
							courseToolConfig.getPlacementConfig().setProperty(property,toolProperties.getProperty(property));
						}
						courseToolConfig.save();
					}
					coursePageList.remove(coursePage);
					coursePageList.add(coursePage);
				}
			}
			coursePageMap.remove(tempSitePage.getTitle());
		}

		if(!coursePageMap.isEmpty()) {
			// do something with orphaned pages in courseSite. Perhaps remove unwanted ones.
			// remember however that optional pages must be left alone.
			Set<String> orphanPageSet = coursePageMap.keySet();
			Iterator<String> orphanPageIter = orphanPageSet.iterator();
			while(orphanPageIter.hasNext()) {
				SitePage orphanPage = (SitePage)coursePageMap.get(orphanPageIter.next());
				coursePageList.remove(orphanPage);
				coursePageList.add(orphanPage);
			}

		}

		updateRoles(courseSite, siteTemplate);
		courseSite.setType("course");
		log.debug(courseSite.getId() +":Course type updated to course");
		siteService.save(courseSite);
		log.debug("course site updated from template: "+courseSite.getId());
	}

	public void updateRoles(Site courseSite, Site siteTemplate) {
		Set<?> roleSet = siteTemplate.getRoles();
		Iterator<?> roleSetIter = roleSet.iterator();
		while(roleSetIter.hasNext()) {
			Role templateRole = (Role)roleSetIter.next();
			try {
				courseSite.addRole(templateRole.getId(), templateRole);
			} catch(RoleAlreadyDefinedException rade) {
				Role courseSiteRole = courseSite.getRole(templateRole.getId());
				/*
				Use this to remove all existing permissions before adding template permissions.
				This effectively allows one to revoke permissions from a role.
				Use this only when absolutely necessary.
				 */
				//courseSiteRole.disallowAll();
				courseSiteRole.allowFunctions(templateRole.getAllowedFunctions());
			}
		}
		courseSite.setMaintainRole(siteTemplate.getMaintainRole());
	}

	public void updateSiteFromXML(String sitename, Element siteLayoutElement) throws Exception {
		
		siteService = (SiteService) ComponentManager.get(SiteService.class);

		SiteManagement sm = new SiteManagement(output);

		Site siteEdit = sm.getSite(sitename);
		siteEdit.setTitle(sitename);
		siteEdit.setDescription(sitename);
		siteEdit.setJoinable(false);
		siteEdit.setPublished(true);
		siteEdit.setShortDescription(sitename);
		siteEdit.setType("Course");
		siteEdit.setJoinerRole("access");
		if(siteEdit == null) {
			log.error(this+": says siteEdit is null - SiteManagement not returning valid site obj");
			return;
		}

		//siteEdit.getPages();

		NodeList nodes = siteLayoutElement.getChildNodes();
		Role roleToAdd = null;

		for (int i = 0; i < nodes.getLength(); i++) {
			try {
			if (nodes.item(i).getNodeType() != Node.ELEMENT_NODE) continue;
			Element actionElement = (Element) nodes.item(i);
			String action = actionElement.getTagName();
			if (action.equalsIgnoreCase("addpage")) {
				log.info("addpage "+getRequiredAttribute(actionElement, "title")+ " to "+sitename);

				SitePage pageEdit = sm.getPage(siteEdit, getRequiredAttribute(actionElement, "title"));
				ToolConfiguration toolconfig = null;

				NodeList subnodes = actionElement.getChildNodes();
				for (int x = 0; x < subnodes.getLength(); x++) {
					try {
					if (subnodes.item(x).getNodeType() != Node.ELEMENT_NODE) continue;
					Element subActionElement = (Element) subnodes.item(x);
					String subAction = subActionElement.getTagName();
					if (subAction.equalsIgnoreCase("addtool")) {
						log.debug("add "+getRequiredAttribute(subActionElement, "title")+getRequiredAttribute(subActionElement, "toolId")+" from "+sitename);
						toolconfig = sm.getTool(pageEdit, getRequiredAttribute(subActionElement, "title"),
								getRequiredAttribute(subActionElement, "toolId"));

						NodeList properties = subActionElement.getChildNodes();
						for (int p = 0; p < properties.getLength(); p++) {
							if (properties.item(p).getNodeType() != Node.ELEMENT_NODE) continue;
							Element propertyElement = (Element) properties.item(p);
							if (propertyElement.getTagName().equals("property")) {
								String name = getRequiredAttribute(propertyElement, "name");
								String value = getRequiredAttribute(propertyElement, "value");
								if (value.startsWith("server:")) {
									value = ServerConfigurationService.getString(value.replaceFirst("server:", ""));
								}
								toolconfig.getPlacementConfig().setProperty(name, value);
							}
						}

					} else if (subAction.equalsIgnoreCase("removetool")) {
						try {
						sm.removeTool(pageEdit, getRequiredAttribute(subActionElement, "title"),
								getRequiredAttribute(subActionElement, "toolId"));

						} catch(NullPointerException npe) {
							continue;
						}
					}
					} catch(Exception e) {
						log.error(this+": tool update "+sitename+e+e.getMessage());
						e.printStackTrace(System.out);
					}
				}

				sm.orderPage(siteEdit, getRequiredAttribute(actionElement, "title"));

			} else if (action.equalsIgnoreCase("removepage")) {
				try {
				sm.removePage(siteEdit, getRequiredAttribute(actionElement, "title"));
				} catch(NullPointerException npe) {
					continue;
				}
			} else if(action.equalsIgnoreCase("addRole")) {
				try {
					roleToAdd = siteEdit.getRole(getRequiredAttribute(actionElement, "roleId"));
					if(null == roleToAdd) {
						roleToAdd = siteEdit.addRole(getRequiredAttribute(actionElement, "roleId"));
					}
					NodeList subnodes = actionElement.getChildNodes();
					for (int x = 0; x < subnodes.getLength(); x++) {
						try {
							if (subnodes.item(x).getNodeType() != Node.ELEMENT_NODE) continue;
							Element subActionElement = (Element) subnodes.item(x);
							String subAction = subActionElement.getTagName();
							if (subAction.equalsIgnoreCase("grantPermission")) {
								roleToAdd.allowFunction(getRequiredAttribute(subActionElement, "permission"));
							} else if (subAction.equalsIgnoreCase("revokePermission")) {
								roleToAdd.disallowFunction(getRequiredAttribute(subActionElement, "permission"));
							}
						} catch(Exception e) {
							log.error(this+": role update "+sitename+e+e.getMessage());
							e.printStackTrace(System.out);
						}
					}
				} catch(Exception e) {
					roleToAdd = null;
				}


			} else {
				log.error("Unknown action '"+action+"'");
			}
			} catch(Exception e) {
				log.error(this+": page update "+sitename+e+e.getMessage());
				e.printStackTrace(System.out);
			}
		}

		try {
			if(null == siteEdit) {
				log.error(this+": says siteEdit is null ");
			} else {
				siteService.save(siteEdit);
			}
			log.info("Site "+siteEdit.getId()+sitename+" committed.");
		} catch(Exception e) {
			log.error(this+": Site commit "+siteEdit.getId()+e+e.getMessage());
			e.printStackTrace(System.out);
		}
	}

	public void executeLocked(JobExecutionContext context)
			throws JobExecutionException {
		
		siteService = (SiteService) ComponentManager.get(SiteService.class);
		sessionManager = (SessionManager) ComponentManager.get(SessionManager.class);
		userDirectoryService = (UserDirectoryService) ComponentManager.get(UserDirectoryService.class);

		log = LogFactory.getLog(this.getClass());
		outputStream = new ByteArrayOutputStream();
		output = new PrintWriter(outputStream);

		runcount++;

		log.info(runcount + ": Started.");

		try {

			User user = userDirectoryService.authenticate(
					ServerConfigurationService.getString("admin.user"),
					ServerConfigurationService.getString("admin.password"));
			if (user != null) {
				log.debug(runcount + ": Authenticated.");
				Session session = sessionManager.startSession();
				if (session == null) throw new JobExecutionException("No session");
				log.debug(runcount + ": Session started: "+session.getId());

				session.setUserEid(user.getId());
				session.setUserId(user.getId());
				sessionManager.setCurrentSession(session);

//				CourseImportDAO dao = new CourseImportDAO();
				SakaiDAO sdao = new SakaiDAO();
				sdao.removeStaleSakaiLocks();

				InputStream is = this.getClass().getResourceAsStream("/site-layouts.xml");
				DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
				DocumentBuilder docBuilder = dbf.newDocumentBuilder();
				InputSource inputSource = new InputSource(is);
				Document doc = docBuilder.parse(inputSource);

				Element root = doc.getDocumentElement();
				NodeList nodes = root.getChildNodes();


				for (int i = 0; i < nodes.getLength(); i++) {
					if (nodes.item(i).getNodeType() != Node.ELEMENT_NODE) continue;
					Element siteLayoutElement = (Element) nodes.item(i);
					int year = Integer.parseInt(getRequiredAttribute(siteLayoutElement, "year"));
					String yearStr = new Integer(year).toString();
					List<?> courses = siteService.getSites(org.sakaiproject.site.api.SiteService.SelectionType.ANY, "Course", "-"+yearStr.substring(2)+"-", null, org.sakaiproject.site.api.SiteService.SortType.TITLE_ASC, null);
					log.debug(runcount + ": Query for "+yearStr+" returned "+courses.size()+" courses");

					Iterator<?> ci = courses.iterator();
					while (ci.hasNext()) {
						updateLock();
						Site course = (Site) ci.next();
						String siteName = (String) course.getId();
						try {

							Site siteTemplate = siteService.getSite("!site.template.course."+yearStr);
							//updateSite(siteName, siteLayoutElement);
							updateSiteFromTemplate(course.getId(), siteTemplate);
							log.debug(this+": Performed update of "+siteName);

						} catch (InUseException iue) {
							log.error(siteName+" is in use - cannot CourseUpdate(Job)"+iue.getMessage());
						} catch (Exception e) {
							log.error(siteName+" - cannot CourseUpdate(Job)"+e.getMessage());
						}
						if (interrupt) {
							throw new Exception("Execution interrupted.");
						}
					}
				}

			} else {
				throw new Exception("User not authenticated");
			}

		} catch (Exception e) {
			log.error(runcount + ": Exception "+e.getClass().getName()+": "+e.getMessage());
			throw new JobExecutionException(e);
		}

		log.debug(runcount + ": Stopped. "+(runcount));

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
