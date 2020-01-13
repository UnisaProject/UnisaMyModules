<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ page import="java.util.*" %>

<sakai:html>
	<sakai:tool_bar>
		<html:link action="/default.do">Back</html:link>
	</sakai:tool_bar>
	<sakai:heading>Components Available in the Class Loader</sakai:heading>
	<sakai:flat_list>
		<tr>
			<th>Bean ID</th>
		</tr>
		<logic:iterate id="component" name="components">
		<tr>
			<td><bean:write name="component"/></td>
		</tr>
		</logic:iterate>
	</sakai:flat_list>
</sakai:html>