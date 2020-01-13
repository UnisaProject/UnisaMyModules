<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ page import="java.util.*" %>

<sakai:html>
	<sakai:heading>Default JSP</sakai:heading>
	<sakai:instruction>
		This JSP should never be seen. Please create a forward
		for your initial action, and change DefaultAction.java to forward to
		your action rather than this JSP.
	</sakai:instruction>
	
	<sakai:group_heading>Some info about the current user:</sakai:group_heading>
	
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