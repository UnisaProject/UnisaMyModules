package za.ac.unisa.lms.tools.proxy;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.htmlparser.util.ParserException;

public class ConnectionMonitor implements org.htmlparser.http.ConnectionMonitor {

	private static final Log log = LogFactory.getLog(ConnectionMonitor.class);
	private ServletInputStream sis = null;
	private HttpServletRequest request = null;
	private String requestMethod = "";
	private String sessionCookies = "";
	private String otherCookies = "";
	private String queryString = "";
	
	public ConnectionMonitor() {
		super();
	}
	
	public ConnectionMonitor(String method) {
		super();
		requestMethod = method;
	}
	
	public void setServletInputStream(ServletInputStream sis) {
		this.sis = sis;
	}
	
	private void setSessionCookie(HttpURLConnection connection) throws Exception {
        if ((sessionCookies == null) || (sessionCookies.equals(""))) return;
        String cookies = connection.getRequestProperty("Cookie");
        if (cookies != null) {
        	cookies += " ";
        } else {
        	cookies = "";
        }
        cookies += sessionCookies;
        connection.setRequestProperty("Cookie", cookies);
	}
	
	private void setOtherCookies(HttpURLConnection connection) throws Exception {
        if ((otherCookies == null) || (otherCookies.equals(""))) return;
        String cookies = connection.getRequestProperty("Cookie");
        if (cookies != null) {
        	cookies += " ";
        } else {
        	cookies = "";
        }
        cookies += otherCookies;
        connection.setRequestProperty("Cookie", cookies);
	}
	
	private void doPOSTParameters(HttpURLConnection connection) throws Exception {
		OutputStream dos = null;
		try {
			dos = connection.getOutputStream();
			int b = sis.read();
	        if (b != -1) {
		        while (b != -1) {
		            dos.write(b);
		            b = sis.read();
		        }
	        } else {
	        	OutputStreamWriter writer = new OutputStreamWriter(dos);
	        	writer.write(queryString);
	        	writer.flush();
	        }
	        dos.flush();
	        dos.close();
		} catch (Exception e) {
			throw e;
		}
        
	}
	
	public void postConnect(HttpURLConnection connection) throws ParserException {
	}

	public void preConnect(HttpURLConnection connection) throws ParserException {
		try {
			connection.setRequestMethod(requestMethod.toUpperCase());
			connection.setUseCaches(false);
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setDefaultUseCaches(false);
			connection.setAllowUserInteraction(true);
			connection.setInstanceFollowRedirects(false);
			
			setSessionCookie(connection);
			setOtherCookies(connection);
			if ((request.getContentType() != null) && (request.getContentType().startsWith("multipart"))) {
				connection.setRequestProperty("Content-Type", request.getContentType());
			}

			if (requestMethod.equalsIgnoreCase("POST")) {
				doPOSTParameters(connection);
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new ParserException(e);
		}
	}

	public String getOtherCookies() {
		return otherCookies;
	}

	public void setOtherCookies(String otherCookies) {
		this.otherCookies = otherCookies;
	}

	public String getSessionCookies() {
		return sessionCookies;
	}

	public void setSessionCookies(String sessionCookies) {
		this.sessionCookies = sessionCookies;
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public String getQueryString() {
		return queryString;
	}

	public void setQueryString(String queryString) {
		this.queryString = queryString;
	}
}