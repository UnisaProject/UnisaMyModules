<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ page import="org.sakaiproject.tool.api.Session"%>
<%@ page import="org.sakaiproject.tool.cover.SessionManager"%>
<%@ page import="org.sakaiproject.component.api.ComponentManager"%>

<sakai:html>

	<sakai:tool_bar>
		<html:link action="/components/componentmanager">Back</html:link>
		<html:link href="sessionmanager.source" target="sessionmanagersource">Source</html:link>
	</sakai:tool_bar>
	
	<h3>SessionManager Component</h3>

	<%
		String userId = null;
	    String userEId = null;
	
	
		ComponentManager sakaiCompMgr = (ComponentManager) org.sakaiproject.component.cover.ComponentManager.getInstance();
		SessionManager sakaiSessionManager = (SessionManager) sakaiCompMgr.get("org.sakaiproject.tool.cover.SessionManager");
		Session currentSession = sakaiSessionManager.getCurrentSession();
		//ComponentManager sakaiCompMgr = org.sakaiproject.service.framework.component.cover.ComponentManager.getInstance();
		//SessionManager sakaiSessionManager = (SessionManager) sakaiCompMgr.get("org.sakaiproject.api.kernel.session.SessionManager");
		//Session currentSession = sakaiSessionManager.getCurrentSession();	
		userId = currentSession.getUserId();
		userEId = currentSession.getUserEid();
	%>
	
	<p>The currently logged on user's id is <%= userId %></p>
	<p>The currently logged on user's eid is <%= userEId %></p>
	
</sakai:html>