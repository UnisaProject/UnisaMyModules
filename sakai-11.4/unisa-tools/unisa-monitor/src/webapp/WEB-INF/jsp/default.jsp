<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ page import="java.util.*" %>

<sakai:html>
	<sakai:heading>Unisa Monitor</sakai:heading>

	<ul>
		<li><html:link action="/displayspringcomponents.do">Display Registered Spring Components</html:link></li>
		<li><html:link action="/studentsystemquery.do">Query the Student System</html:link></li>
		<li><html:link action="/sakaiquery.do">Query the Sakai Database</html:link></li>
		<li><html:link action="/userrolesdisplay.do">Display User Roles</html:link></li>
		<li><html:link action="/exceptiontest.do">Throw a few exceptions...</html:link></li>
		<li><html:link action="/activedirectoryssl.do">Check SSL Certificate for Active Directory</html:link></li>
	</ul>

</sakai:html>