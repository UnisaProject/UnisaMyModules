package za.ac.unisa.lms.tools.cronjobs.jobs;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.InterruptableJob;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;
import org.quartz.UnableToInterruptJobException;
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

import za.ac.unisa.lms.tools.cronjobs.dao.OptionalCourseToolsDAO;
import za.ac.unisa.lms.tools.cronjobs.dao.SakaiDAO;
import za.ac.unisa.lms.tools.cronjobs.locks.SingleClusterInstanceJob;
import za.ac.unisa.lms.tools.cronjobs.util.SiteManagement;

public class OptionalCourseToolsOnceoff extends SingleClusterInstanceJob implements StatefulJob, InterruptableJob, OutputGeneratingJob{
	
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

	public void optionaltoolsupdate(String sitename, Element siteLayoutElement) throws Exception {
		
		siteService = (SiteService) ComponentManager.get(SiteService.class);
		
		SiteManagement sm = new SiteManagement(output);

		Site siteEdit = sm.getSite(sitename);
		siteEdit.setTitle(sitename);
		siteEdit.setDescription(sitename);
		siteEdit.setJoinable(false);
		siteEdit.setPublished(true);
		siteEdit.setShortDescription(sitename);
		/*siteEdit.setType("Course");
		siteEdit.setJoinerRole("access");*/
		if(siteEdit == null) {
			log.error(this+": says siteEdit is null - SiteManagement not returning valid site obj");
			return;
		}
		NodeList nodes = siteLayoutElement.getChildNodes();
		OptionalCourseToolsDAO OCTDAO = new OptionalCourseToolsDAO();

		for (int i = 0; i < nodes.getLength(); i++) {
			try {
			if (nodes.item(i).getNodeType() != Node.ELEMENT_NODE) continue;
			Element actionElement = (Element) nodes.item(i);
			String action = actionElement.getTagName();
			log.info("Get optional-pages: " + action.equalsIgnoreCase("optional-page"));
			if (action.equalsIgnoreCase("optional-page")) {
				log.info("Title = "+getRequiredAttribute(actionElement, "title"));
				if ("Home".equals(getRequiredAttribute(actionElement, "title"))){
					log.info("Optional tool "+getRequiredAttribute(actionElement, "title")+ " to "+sitename);
					SitePage hPageEdit = sm.getPage(siteEdit, getRequiredAttribute(actionElement, "title"));
					ToolConfiguration hToolconfig = null;
					NodeList hSubnodes = actionElement.getChildNodes();
					for (int x = 0; x < hSubnodes.getLength(); x++) {
						try {
							if (hSubnodes.item(x).getNodeType() != Node.ELEMENT_NODE) continue;
							Element subActionElement = (Element) hSubnodes.item(x);
							String subAction = subActionElement.getTagName();
							if (subAction.equalsIgnoreCase("optional-tool")) {
								log.info("Optional tool add "+getRequiredAttribute(subActionElement, "title") +" "+ getRequiredAttribute(subActionElement, "toolId")+" from "+sitename);
								hToolconfig = sm.getTool(hPageEdit, getRequiredAttribute(subActionElement, "title"),getRequiredAttribute(subActionElement, "toolId"));
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
										if (!name.equals("layoutHints")){
											hToolconfig.getPlacementConfig().setProperty(name, value);
										} else {
											hToolconfig.setLayoutHints(value);
										}
									}
								}

							}
						} catch(Exception e) {
							log.error(this+": tool update "+sitename+e+e.getMessage());
							e.printStackTrace(System.out);
						}
					}
					
					
					hPageEdit.setPosition(0);
					hPageEdit.setLayout(1);
		/*			sm.orderPage(siteEdit, getRequiredAttribute(actionElement, "title"));*/
				}

				
				if ("Official Study Material".equals(getRequiredAttribute(actionElement, "title"))){
					log.info("Optional tool "+getRequiredAttribute(actionElement, "title")+ " to "+sitename);
					SitePage oPageEdit = sm.getPage(siteEdit, getRequiredAttribute(actionElement, "title"));
					ToolConfiguration oToolconfig = null;
					NodeList oSubnodes = actionElement.getChildNodes();
					for (int x = 0; x < oSubnodes.getLength(); x++) {
						try {
							if (oSubnodes.item(x).getNodeType() != Node.ELEMENT_NODE) continue;
							Element subActionElement = (Element) oSubnodes.item(x);
							String subAction = subActionElement.getTagName();
							if (subAction.equalsIgnoreCase("optional-tool")) {
								log.info("Optional tool add "+getRequiredAttribute(subActionElement, "title")+getRequiredAttribute(subActionElement, "toolId")+" from "+sitename);
								oToolconfig = sm.getTool(oPageEdit, getRequiredAttribute(subActionElement, "title"),getRequiredAttribute(subActionElement, "toolId"));
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
										oToolconfig.getPlacementConfig().setProperty(name, value);
									}
								}

							}
						} catch(Exception e) {
							log.error(this+": tool update "+sitename+e+e.getMessage());
							e.printStackTrace(System.out);
						}
					}
					
					SitePage DiscussionPageEdit = sm.getPage(siteEdit, "Discussion Forum");
					int pos = DiscussionPageEdit.getPosition() +1;
					oPageEdit.setPosition(pos);
		/*			sm.orderPage(siteEdit, getRequiredAttribute(actionElement, "title"));*/
				}
				if ("Site Stats".equals(getRequiredAttribute(actionElement, "title"))){
					log.info("Optional tool "+getRequiredAttribute(actionElement, "title")+ " to "+sitename);
					SitePage oPageEdit = sm.getPage(siteEdit, getRequiredAttribute(actionElement, "title"));
					ToolConfiguration oToolconfig = null;
					NodeList oSubnodes = actionElement.getChildNodes();
					for (int x = 0; x < oSubnodes.getLength(); x++) {
						try {
							if (oSubnodes.item(x).getNodeType() != Node.ELEMENT_NODE) continue;
							Element subActionElement = (Element) oSubnodes.item(x);
							String subAction = subActionElement.getTagName();
							if (subAction.equalsIgnoreCase("optional-tool")) {
								log.info("Optional tool add "+getRequiredAttribute(subActionElement, "title")+getRequiredAttribute(subActionElement, "toolId")+" from "+sitename);
								oToolconfig = sm.getTool(oPageEdit, getRequiredAttribute(subActionElement, "title"),getRequiredAttribute(subActionElement, "toolId"));
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
										oToolconfig.getPlacementConfig().setProperty(name, value);
									}
								}

							}
						} catch(Exception e) {
							log.error(this+": tool update "+sitename+e+e.getMessage());
							e.printStackTrace(System.out);
						}
					}
					
					SitePage PrescribedBooksPageEdit = sm.getPage(siteEdit, "Prescribed Books");
					int pos = PrescribedBooksPageEdit.getPosition() +1;
					oPageEdit.setPosition(pos);
		/*			sm.orderPage(siteEdit, getRequiredAttribute(actionElement, "title"));*/
				}
				if ("Additional Resources".equals(getRequiredAttribute(actionElement, "title"))){
					log.info("Optional tool "+getRequiredAttribute(actionElement, "title")+ " to "+sitename);
					SitePage aPageEdit = sm.getPage(siteEdit, getRequiredAttribute(actionElement, "title"));
					ToolConfiguration aToolconfig = null;
					NodeList aSubnodes = actionElement.getChildNodes();
					for (int x = 0; x < aSubnodes.getLength(); x++) {
						try {
							if (aSubnodes.item(x).getNodeType() != Node.ELEMENT_NODE) continue;
							Element subActionElement = (Element) aSubnodes.item(x);
							String subAction = subActionElement.getTagName();
							if (subAction.equalsIgnoreCase("optional-tool")) {
								log.info("Optional tool add "+getRequiredAttribute(subActionElement, "title")+getRequiredAttribute(subActionElement, "toolId")+" from "+sitename);
								aToolconfig = sm.getTool(aPageEdit, getRequiredAttribute(subActionElement, "title"),getRequiredAttribute(subActionElement, "toolId"));
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
										aToolconfig.getPlacementConfig().setProperty(name, value);
									}
								}

							}
						} catch(Exception e) {
							log.error(this+": tool update "+sitename+e+e.getMessage());
							e.printStackTrace(System.out);
						}
					}
					SitePage OfficialPageEdit = sm.getPage(siteEdit, "Official Study Material");
					int pos = OfficialPageEdit.getPosition() +1;
					aPageEdit.setPosition(pos);
					aPageEdit.setLayout(2);
					/*sm.orderPage(siteEdit, getRequiredAttribute(actionElement, "title"));*/
				}

				
				if ("Course Contact".equals(getRequiredAttribute(actionElement, "title"))){
					boolean addemailtool=false;
					log.info("Check if Course Contact has to be added");
					addemailtool=OCTDAO.checkEmailTool(sitename);
					log.info("Add Course Contact :"+addemailtool);
					if (addemailtool){
						log.info("Optional tool "+getRequiredAttribute(actionElement, "title")+ " to "+sitename);
						SitePage cPageEdit = sm.getPage(siteEdit, getRequiredAttribute(actionElement, "title"));
						ToolConfiguration cToolconfig = null;
						NodeList cSubnodes = actionElement.getChildNodes();
						for (int x = 0; x < cSubnodes.getLength(); x++) {
							try {
								if (cSubnodes.item(x).getNodeType() != Node.ELEMENT_NODE) continue;
								Element subActionElement = (Element) cSubnodes.item(x);
								String subAction = subActionElement.getTagName();
								if (subAction.equalsIgnoreCase("optional-tool")) {
									log.info("Optional tool add "+getRequiredAttribute(subActionElement, "title")+getRequiredAttribute(subActionElement, "toolId")+" from "+sitename);
									cToolconfig = sm.getTool(cPageEdit, getRequiredAttribute(subActionElement, "title"),getRequiredAttribute(subActionElement, "toolId"));
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
											cToolconfig.getPlacementConfig().setProperty(name, value);
										}
									}

								}
							} catch(Exception e) {
								log.error(this+": tool update "+sitename+e+e.getMessage());
								e.printStackTrace(System.out);
							}
						}
						sm.orderPage(siteEdit, getRequiredAttribute(actionElement, "title"));
					} else {
						try {
							sm.removePage(siteEdit, getRequiredAttribute(actionElement, "title"));
						} catch(NullPointerException npe) {
							continue;
						}
					} 
				}
				if ("FAQs".equals(getRequiredAttribute(actionElement, "title"))){
					boolean addfaqtool=false;
					if (addfaqtool){
						log.info("Optional tool "+getRequiredAttribute(actionElement, "title")+ " to "+sitename);
						SitePage pageEdit = sm.getPage(siteEdit, getRequiredAttribute(actionElement, "title"));
						ToolConfiguration toolconfig = null;
						NodeList subnodes = actionElement.getChildNodes();
						for (int x = 0; x < subnodes.getLength(); x++) {
							try {
								if (subnodes.item(x).getNodeType() != Node.ELEMENT_NODE) continue;
								Element subActionElement = (Element) subnodes.item(x);
								String subAction = subActionElement.getTagName();
								if (subAction.equalsIgnoreCase("optional-tool")) {
									log.info("Optional tool add "+getRequiredAttribute(subActionElement, "title")+getRequiredAttribute(subActionElement, "toolId")+" from "+sitename);
									toolconfig = sm.getTool(pageEdit, getRequiredAttribute(subActionElement, "title"),getRequiredAttribute(subActionElement, "toolId"));
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

								}
							} catch(Exception e) {
								log.error(this+": tool update "+sitename+e+e.getMessage());
								e.printStackTrace(System.out);
							}
						}
						sm.orderPage(siteEdit, getRequiredAttribute(actionElement, "title"));
					} else {
						try {
							sm.removePage(siteEdit, getRequiredAttribute(actionElement, "title"));
						} catch(NullPointerException npe) {
							continue;
						}
					} 
				}
				if ("Assignments".equals(getRequiredAttribute(actionElement, "title"))){
					log.info("Optional tool "+getRequiredAttribute(actionElement, "title")+ " to "+sitename);
					SitePage assignPageEdit = sm.getPage(siteEdit, getRequiredAttribute(actionElement, "title"));
					ToolConfiguration assignToolconfig = null;
					NodeList aSubnodes = actionElement.getChildNodes();
					for (int x = 0; x < aSubnodes.getLength(); x++) {
						try {
							if (aSubnodes.item(x).getNodeType() != Node.ELEMENT_NODE) continue;
							Element subActionElement = (Element) aSubnodes.item(x);
							String subAction = subActionElement.getTagName();
							if (subAction.equalsIgnoreCase("optional-tool")) {
								log.info("Optional tool add "+getRequiredAttribute(subActionElement, "title")+" "+getRequiredAttribute(subActionElement, "toolId")+" from "+sitename);
								assignToolconfig = sm.getTool(assignPageEdit, getRequiredAttribute(subActionElement, "title"),getRequiredAttribute(subActionElement, "toolId"));
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
										assignToolconfig.getPlacementConfig().setProperty(name, value);
									}
								}

							}
						} catch(Exception e) {
							log.error(this+": tool update "+sitename+e+e.getMessage());
							e.printStackTrace(System.out);
						}
					}
					SitePage HomePageEdit = sm.getPage(siteEdit, "Home");
					int pos = HomePageEdit.getPosition() +1;
					assignPageEdit.setPosition(pos);
					/*assignPageEdit.setLayout(2);*/
					/*sm.orderPage(siteEdit, getRequiredAttribute(actionElement, "title"));*/
				}

			} else if (action.equalsIgnoreCase("optional-remove")){
				try {
					sm.removePage(siteEdit, getRequiredAttribute(actionElement, "title"));
					} catch(NullPointerException npe) {
						continue;
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
				log.info("Site "+siteEdit.getId()+" "+sitename+" committed.");
			}
			
		} catch(Exception e) {
			log.error(this+": Site commit "+siteEdit.getId()+e+e.getMessage());
			e.printStackTrace(System.out);
		}
	}



	public void executeLocked(JobExecutionContext context) throws JobExecutionException {
		
		userDirectoryService = (UserDirectoryService) ComponentManager.get(UserDirectoryService.class);
		sessionManager = (SessionManager) ComponentManager.get(SessionManager.class);
		siteService = (SiteService) ComponentManager.get(SiteService.class);
		
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
				log.info(runcount + ": Authenticated.");
				Session session = sessionManager.startSession();
				if (session == null) throw new JobExecutionException("No session");
				log.info(runcount + ": Session started: "+session.getId());

				session.setUserEid(user.getId());
				session.setUserId(user.getId());
				sessionManager.setCurrentSession(session);

//				CourseImportDAO dao = new CourseImportDAO();
				SakaiDAO sdao = new SakaiDAO();
				sdao.removeStaleSakaiLocks();

				InputStream is = this.getClass().getResourceAsStream("/optional-coursesite-tools.xml");
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
					List<?> courses = siteService.getSites(org.sakaiproject.site.api.SiteService.SelectionType.ANY, "course", "-"+yearStr.substring(2)+"-", null, org.sakaiproject.site.api.SiteService.SortType.TITLE_ASC, null);
					log.info(runcount + ": Query for "+yearStr+" returned "+courses.size()+" courses");

					Iterator<?> ci = courses.iterator();
					while (ci.hasNext()) {
						updateLock();
						Site course = (Site) ci.next();
						String siteName = (String) course.getId();
						try {

							optionaltoolsupdate(siteName, siteLayoutElement);
							log.info(this+": Performed update of "+siteName);

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

		log.info(runcount + ": Stopped. "+(runcount));


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
