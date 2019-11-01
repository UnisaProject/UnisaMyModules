<%@ page import="org.sakaiproject.tool.api.Session"%>
<%@ page import="org.sakaiproject.tool.cover.SessionManager"%>

<%
// mark the session as active
Session s = SessionManager.getCurrentSession();
if (s != null) s.setActive();
out.print("//");
out.flush();
%>