package za.ac.unisa.lms.tools.proxy;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.TreeMap;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.htmlparser.Parser;
import org.htmlparser.PrototypicalNodeFactory;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.http.ConnectionManager;
import org.htmlparser.util.NodeList;
import org.sakaiproject.component.cover.ComponentManager;
import org.sakaiproject.tool.api.Session;
import org.sakaiproject.tool.api.ToolManager;
import org.sakaiproject.tool.api.SessionManager;
import org.sakaiproject.user.api.User;
import org.sakaiproject.user.api.UserDirectoryService;

import za.ac.unisa.exceptions.NotACourseSiteException;
import za.ac.unisa.lms.tools.proxy.tags.SakaiBodyTag;
import za.ac.unisa.lms.tools.proxy.tags.SakaiFormTag;
import za.ac.unisa.lms.tools.proxy.tags.SakaiFrameTag;
import za.ac.unisa.lms.tools.proxy.tags.SakaiHeadTag;
import za.ac.unisa.lms.tools.proxy.tags.SakaiHtmlTag;
import za.ac.unisa.lms.tools.proxy.tags.SakaiImageTag;
import za.ac.unisa.lms.tools.proxy.tags.SakaiInputTag;
import za.ac.unisa.lms.tools.proxy.tags.SakaiLinkTag;
import za.ac.unisa.lms.tools.proxy.tags.SakaiScriptTag;
import za.ac.unisa.lms.tools.proxy.utils.MissingParameters;
import za.ac.unisa.utils.CoursePeriod;
import za.ac.unisa.utils.CoursePeriodLookup;

/**
 * @author University of South Africa
 *
 */
public class HttpProxyTool extends HttpServlet {
	
	private ToolManager toolManager;
	private SessionManager sessionManager;
	private UserDirectoryService userDirectoryService;

	private static final long serialVersionUID = 1L;

	private static final Log log = LogFactory.getLog(HttpProxyTool.class);

	public void init(ServletConfig config) throws ServletException {
		System.setProperty("http.nonProxyHosts", "163.200.*|*.unisa.ac.za");
		super.init();
	}

	/**
	 * Set the required cookies for the endpoint to function correctly
	 * @param monitor The connection monitor that handles cookie sending
	 * @param instanceProperties The instance properties obtained from getInstanceProperties
	 */
	private void setCookies(ConnectionMonitor monitor,
			Properties instanceProperties) {
		userDirectoryService = (UserDirectoryService) ComponentManager.get(UserDirectoryService.class);
		String cookies = "";
		User user = userDirectoryService.getCurrentUser();
		if (!instanceProperties.getProperty("cookieUserID").equals("")) {
			cookies += instanceProperties.getProperty("cookieUserID") + "="
					+ user.getEid() + "; ";
		}
		if (!instanceProperties.getProperty("cookieEmail").equals("")) {
			cookies += instanceProperties.getProperty("cookieEmail") + "="
					+ user.getEmail() + "; ";
		}
		monitor.setOtherCookies(cookies);
	}

	//FIXME I don't think this method is required
	private String getQueryString(HttpServletRequest req) {
		String queryString = "";

		try {
			Map<?, ?> map = req.getParameterMap();
			TreeMap<?, ?> tree = new TreeMap(map);
			Set<?> set = tree.keySet();
			Iterator<?> i = set.iterator();

			while (i.hasNext()) {
				String element = i.next().toString();
				queryString += (URLEncoder.encode(element, "UTF-8") + "="
						+ URLEncoder.encode(req.getParameter(element), "UTF-8") + "&");
			}
		} catch (UnsupportedEncodingException uee) {
		}
		if (!queryString.equals("")) {
			queryString = queryString
					.substring(0, queryString.lastIndexOf("&"));
		}
		return queryString;
	}

	private String getUniqueName(String contextPath) {
		contextPath = contextPath.replaceAll("/", ".");
		return contextPath;
	}

	/**
	 * @return A java.util.Properties that contains all the placement parameters for this instance
	 * @throws IllegalArgumentException
	 */
	private Properties getInstanceProperties() throws IllegalArgumentException {
		toolManager = (ToolManager) ComponentManager.get(ToolManager.class);
		Properties instanceProperties = new Properties();
		Properties properties = toolManager.getCurrentPlacement()
				.getPlacementConfig();
		String base = properties.getProperty("base");
		String url = properties.getProperty("url");
		String parms = properties.getProperty("parms");
		String permParm = properties.getProperty("permParm");
		String cookieUserID = properties.getProperty("cookieUserID");
		String cookieEmail = properties.getProperty("cookieEmail");

		String reqParms = "";

		/* Required parameters */
		if (base == null || base.equals(""))
			reqParms += "base, ";
		if (url == null || url.equals(""))
			reqParms += "url, ";

		if (reqParms.length() > 0) {
			throw new IllegalArgumentException("Required parameters missing: ("
					+ reqParms.substring(0, reqParms.length() - 2) + ")");
		}

		/* Optional parameters */
		if (parms == null)
			parms = "";
		if (permParm == null)
			permParm = "";
		if (cookieUserID == null)
			cookieUserID = "";
		if (cookieEmail == null)
			cookieEmail = "";

		instanceProperties.setProperty("base", base);
		instanceProperties.setProperty("url", url);
		instanceProperties.setProperty("parms", parms);
		instanceProperties.setProperty("permParm", permParm);
		instanceProperties.setProperty("cookieUserID", cookieUserID);
		instanceProperties.setProperty("cookieEmail", cookieEmail);

		return instanceProperties;
	}

	//TODO move this to a provider so that anyone can specify custom variables

	/**
	 * @param vars The string of url parameters with embedded variables
	 * @return The replaced string
	 * @throws Exception If variables are required, but not available
	 */
	private String replaceVars(String vars) throws Exception {
		
         	userDirectoryService = (UserDirectoryService) ComponentManager.get(UserDirectoryService.class);
		sessionManager = (SessionManager) ComponentManager.get(SessionManager.class);

		CoursePeriod c = null;
		String result = vars;

		if ((vars.indexOf("{coursecode}") >= 0)
				|| (vars.indexOf("{acadyear}") >= 0)
				|| (vars.indexOf("{period}") >= 0)) {
			try {
				c = CoursePeriodLookup.getCoursePeriod();
				result = result.replaceAll("\\{coursecode\\}", c
						.getCourseCode());
				result = result.replaceAll("\\{acadyear\\}", new Integer(c
						.getYear()).toString());
				result = result.replaceAll("\\{period\\}", new Short(c
						.getSemesterPeriod()).toString());
			} catch (NotACourseSiteException nac) {
				throw new Exception(
						"The proxy is configured to replace variables with the current course, but this is not a course site",
						nac);
			}
		}

		if ((vars.indexOf("{novellid}") >= 0)
				|| (vars.indexOf("{system}") >= 0)) {
			User user = userDirectoryService.getCurrentUser();
			String userEid = user.getEid();
			String userId = "";
			if ((userEid == null) || (userEid.equals(""))) {
				try {
					userId = sessionManager.getCurrentSessionUserId();
					userEid = userDirectoryService.getUserEid(userId);
				} catch(Exception e) {
					throw new Exception(
					"The proxy is configured to replace variables with the current user, but cannot determine current user (id="+userId+",eid="+userEid);
				}

			}

			if((user.getType() != null) && (!user.getType().equals(""))) {
				result = result.replaceAll("\\{novellid\\}", userEid);
				if (user.getType().equalsIgnoreCase("Instructor")) {
					result = result.replaceAll("\\{system\\}", "LOL");
				} else if (user.getType().equalsIgnoreCase("Student")) {
					result = result.replaceAll("\\{system\\}", "SOL");
				} else {
					throw new Exception(
							"The proxy required a system type as variable, but the current user is neither a Instructor or a student - user (id="+userId+",eid="+userEid);
				}
			} else {
				throw new Exception(
						"The proxy is configured to replace variables with the current user, but can't determine the user type for user (id="+userId+",eid="+userEid);
			}
		}

		return result;
	}

	private void doProcess(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		sessionManager = (SessionManager) ComponentManager.get(SessionManager.class);

		Session session = sessionManager.getCurrentSession();
		String contextPath = request.getContextPath();//portal/tool/e69f4d58-0ba3-4bd1-0047-8f98da3bbff7
		String uniqueName = getUniqueName(contextPath);//.portal.tool.e69f4d58-0ba3-4bd1-0047-8f98da3bbff7
		String requestUri = request.getRequestURI();///portal/tool/e69f4d58-0ba3-4bd1-0047-8f98da3bbff7

		String head = request.getAttribute("sakai.html.head").toString();
		String bodyOnLoad = request.getAttribute("sakai.html.body.onload")
				.toString();
		long startTime = new Date().getTime();
		Properties instanceProperties = null;

		try {
			instanceProperties = getInstanceProperties();
		} catch (IllegalArgumentException iae) {
			MissingParameters.printMissingParameters(request, response, iae
					.getMessage());
			return;
		}

		try {
			instanceProperties.setProperty("parms",
					replaceVars(instanceProperties.getProperty("parms")));
			instanceProperties.setProperty("permParm",
					replaceVars(instanceProperties.getProperty("permParm")));
		} catch (Exception e) {
			throw new ServletException("Unable to replace parameters: "
					+ e.getMessage(), e);
		}

		String target = requestUri.substring(contextPath.length(), requestUri
				.length());

		InputStream is = null;
		ServletInputStream sis = null;
		ServletOutputStream out = null;
		HttpURLConnection uc = null;

		try {

			if ((target == null) || (target.equals("/") || (target.equals("")))) {
				target = contextPath + instanceProperties.getProperty("url");
				target = target.replaceAll("//", "/");

				if (instanceProperties.getProperty("parms").length() > 0) {
					target += ((target.indexOf("?") == -1) ? "?" : "&")
							+ instanceProperties.getProperty("parms");
				}
				if (instanceProperties.getProperty("permParm").length() > 0) {
					target += ((target.indexOf("?") == -1) ? "?" : "&")
							+ instanceProperties.getProperty("permParm");
				}

				log
						.debug("No target on the pathinfo found. Redirecting with default target: "
								+ target);
				response.sendRedirect(target);
				return;
			} else if (target.indexOf("://") >= 0) {
				throw new ServletException("Malformed pathInfo: " + target);
			}

			log.debug("pathinfo: " + target);

			target = instanceProperties.getProperty("base")
					+ ((target.startsWith("/")) ? "" : "/") + target;

			if (instanceProperties.getProperty("permParm").length() > 0) {
				target += "?" + instanceProperties.getProperty("permParm");
			}

			if (request.getQueryString() != null) {
				target += "?" + request.getQueryString();
			}

			target = target.replaceAll("\\?", "\\&");
			target = target.replaceFirst("\\&", "\\?");
			target = target.replaceAll(" ", "%20");

			sis = request.getInputStream();

			log.debug("target: " + target);
			URL url = new URL(target);

			ConnectionManager manager = new ConnectionManager();
			ConnectionMonitor monitor = new ConnectionMonitor(request
					.getMethod());

			manager.setCookieProcessingEnabled(true);
			setCookies(monitor, instanceProperties);

			if (session.getAttribute(uniqueName + "-cookies") != null) {
				HashMap<?, ?> cookies = (HashMap<?, ?>) session.getAttribute(uniqueName
						+ "-cookies");
				String sessionCookies = "";
				Iterator<?> i = cookies.keySet().iterator();
				while (i.hasNext()) {
					String key = (String) i.next();
					String value = (String) cookies.get(key);
					if (!sessionCookies.equals("")) {
						sessionCookies += " ";
					}
					sessionCookies += key + "=" + value + ";";
				}
				monitor.setSessionCookies(sessionCookies);
			}
			monitor.setServletInputStream(sis);
			monitor.setRequest(request);
			monitor.setQueryString(getQueryString(request));
			manager.setMonitor(monitor);

			HttpURLConnection.setFollowRedirects(false);
			

			uc = (HttpURLConnection) manager.openConnection(url);
			uc.connect();
			log.debug("Proxy?" +uc.usingProxy());

			Map<?, ?> map = uc.getHeaderFields();

			List<?> list = null;
		

			HashMap<String, String> cookies = (HashMap) session.getAttribute(uniqueName
					+ "-cookies");
				
			if (cookies == null)
				cookies = new HashMap<String, String>();
			if ((map != null) && (map.containsKey("Set-Cookie"))) {
				list = (List<?>) map.get("Set-Cookie");
				Iterator<?> li = list.iterator();
				while (li.hasNext()) {
					String cookie = (String) li.next();
					cookie = cookie.substring(0, cookie.indexOf(";"));
					String[] cookiedata = cookie.split("=");
					if (cookiedata.length == 2) {
						cookies.put(cookiedata[0], cookiedata[1]);
						Cookie cookie1 = new Cookie(cookiedata[0],cookiedata[1]);
						response.addCookie(cookie1);
					} else if (cookiedata.length == 1) {
						cookies.put(cookiedata[0], "");
						Cookie cookie1 = new Cookie(cookiedata[0],"");
						response.addCookie(cookie1);
					}
				}
			}
			
			//vijay change: Add cookieUserID and cookieEmail to resopnse 
			userDirectoryService = (UserDirectoryService) ComponentManager.get(UserDirectoryService.class);			
			User user = userDirectoryService.getCurrentUser();
			
			if (!instanceProperties.getProperty("cookieUserID").equals("")) {
				Cookie cookie2 = new Cookie(instanceProperties.getProperty("cookieUserID"), user.getEid());
				cookie2.setDomain("unisa.ac.za");
				response.addCookie(cookie2);
			}
			if (!instanceProperties.getProperty("cookieEmail").equals("")) {
				Cookie cookie3 = new Cookie(instanceProperties.getProperty("cookieEmail"), user.getEmail());
				cookie3.setDomain("unisa.ac.za");
				response.addCookie(cookie3);
			}
			
			
			session.setAttribute(uniqueName + "-cookies", cookies);
		

			String contentType = uc.getContentType();
			response.setContentType(contentType);

			if (map.containsKey("content-disposition")) {
				String disposition = map.get("content-disposition").toString();
				if (disposition.startsWith("[")) {
					disposition = disposition
							.substring(1, disposition.length());
				}
				if (disposition.endsWith("]")) {
					disposition = disposition.substring(0,
							disposition.length() - 1);
				}
				if (map.containsKey("content-disposition")) {
					response.addHeader("content-disposition", disposition);
				}
			}

			if ((uc.getResponseCode() >= 200) && (uc.getResponseCode() < 300)) {
				if (contentType.indexOf("text/html") == -1) {
					InputStream in = uc.getInputStream();
					int w = (char) in.read();
					if (out == null) out = response.getOutputStream();
					while (w != -1) {
						out.write((char) w);
						w = in.read();
					}
				} else {
					Parser parser = new Parser(uc);

					PrototypicalNodeFactory factory = new PrototypicalNodeFactory();
					factory.registerTag(new SakaiLinkTag(instanceProperties
							.getProperty("base"), contextPath,
							instanceProperties.getProperty("permParam")));
					factory.registerTag(new SakaiInputTag(instanceProperties
							.getProperty("base"), contextPath));
					factory.registerTag(new SakaiHtmlTag(factory));
					factory.registerTag(new SakaiHeadTag(head));
					factory.registerTag(new SakaiBodyTag(bodyOnLoad));
					factory.registerTag(new SakaiImageTag(instanceProperties
							.getProperty("base"), contextPath));
					factory.registerTag(new SakaiFormTag(contextPath,
							instanceProperties.getProperty("base")));
					factory.registerTag(new SakaiScriptTag(instanceProperties
							.getProperty("base")));
					factory.registerTag(new SakaiFrameTag());

					parser.setNodeFactory(factory);

					NodeList nodeList = parser.parse(null);
					NodeList htmlNodesList = nodeList.extractAllNodesThatMatch(
							new TagNameFilter("html"), true);
					if (out == null) out = response.getOutputStream();
					out.write(htmlNodesList.toHtml().getBytes("UTF-8"));
				}
			} else if ((uc.getResponseCode() >= 300)
					&& (uc.getResponseCode() < 400)) {

				String location = uc.getHeaderField("Location");
				log.debug("redirect request for: " + location);

				// Redirect is relative to the root of the server
				if (location.startsWith("/")) {
					location = request.getContextPath()
							+ location.substring(1, location.length());
					location = location.replaceAll("//", "/");
				} else {
					// See if redirect contains the base and replace it with the
					// proxy tool's current url
					if(location.indexOf("?strictURL") != -1) {
						
					} else if (location.startsWith(instanceProperties
							.getProperty("base"))) {
						location = location.substring(instanceProperties
								.getProperty("base").length(), location
								.length());
						location = request.getContextPath() + location;
						location = location.replaceAll("//", "/");
					}
				}

				//location = location.replaceAll("//", "/");

				log.debug("redirecting to: " + location);
				response.sendRedirect(response.encodeRedirectURL(location));
				return;

			} else {
				log.error("Proxy?" +uc.usingProxy());
				log.error("Proxy Received Error Response : Response Code = "
						+ uc.getResponseCode() + " Target = " + target);
				

				InputStream in = uc.getErrorStream();
				int w = in.read();
				if (out == null) out = response.getOutputStream();
				while (w != -1) {
					out.write((char) w);
					w = in.read();
				}
			}
		} catch (Exception e) {
			long endTime = new Date().getTime();
			e.printStackTrace();
			String readTimeout = System.getProperty("sun.net.client.defaultReadTimeout");
			String connectTimeout = System.getProperty("sun.net.client.defaultConnectTimeout");
			throw new ServletException(e.getMessage() + "\nduration: "
					+ new Long(endTime - startTime).toString() + "ms\n" +
							"read timeout limit = " + readTimeout + "ms\n" +
							"connect timeout limit = "+connectTimeout + "ms\n", e);
		} finally {
			if (is != null) {
				is.close();
			}
			if (out != null) {
				out.flush();
				out.close();
			}
		}
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}

	public void destroy() {
		log.debug("Destroy");
		super.destroy();
	}

	public String getServletInfo() {
		return "Http Proxy Tool Servlet";
	}

}