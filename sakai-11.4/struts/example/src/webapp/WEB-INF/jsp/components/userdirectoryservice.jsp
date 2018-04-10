<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ page import="org.sakaiproject.tool.api.Session"%>
<%@ page import="org.sakaiproject.tool.cover.SessionManager"%>
<%@ page import="org.sakaiproject.component.api.ComponentManager"%>
<%@ page import="org.sakaiproject.user.api.User"%>
<%@ page import="org.sakaiproject.user.cover.UserDirectoryService"%>



<sakai:html>

	<sakai:tool_bar>
		<html:link action="/components/componentmanager">Back</html:link>
		<html:link href="userdirectoryservice.source" target="userdirectoryservicesource">Source</html:link>
	</sakai:tool_bar>
	
	<h3>UserDirectoryService Component</h3>

	<p>You need to be logged on to see the table below!</p>

	<%
		String userId = null;
	
		ComponentManager sakaiCompMgr = (ComponentManager) org.sakaiproject.component.cover.ComponentManager.getInstance();
		SessionManager sakaiSessionManager = (SessionManager) sakaiCompMgr.get("org.sakaiproject.tool.cover.SessionManager");
		UserDirectoryService userDirService = (UserDirectoryService) sakaiCompMgr.get("org.sakaiproject.user.cover.UserDirectoryService");
		Session currentSession = sakaiSessionManager.getCurrentSession();	
		userId = currentSession.getUserId();
		User user = null;
		if (userId != null) {
			user = userDirService.getUser(userId);
			request.setAttribute("user",user);
		}
	%>
	
	<logic:present name="user">
	<sakai:flat_list>
		<tr>
			<th>Username</th>
			<th>Display Name</th>
			<th>Type</th>
		</tr>
		<tr>
			<td><bean:write name="user" property="id"/></td>
			<td><bean:write name="user" property="displayName"/></td>
			<td><bean:write name="user" property="type"/></td>
		</tr>
	</sakai:flat_list>
	</logic:present>
	
</sakai:html>