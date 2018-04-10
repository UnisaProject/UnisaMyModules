<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>

<sakai:html>
	<sakai:tool_bar>
		<html:link action="/taglibs/index">Back</html:link>
		<html:link href="requiredtag.source" target="requiredtag">Source</html:link>
	</sakai:tool_bar>
	<sakai:heading>&lt;sakai:required&gt; Tag</sakai:heading>
	<p>Used to indicate to the user that a field should be required.  Can be used anyware in code.</p>

	

	<sakai:group_heading>User Information</sakai:group_heading>

	<sakai:group_table>

		<tr>

			<td><label>Username <sakai:required/></label></td>

			<td><input/></td>

		</tr>

		<tr>

			<td><label>Password <sakai:required/></label></td>

			<td><input type="password"/></td>

		</tr>

		<tr>

			<td><label>Password again <sakai:required/></label></td>

			<td><input type="password"/></td>

		</tr>

		<tr>

			<td><label>Description</label></td>

			<td><input/></td>

		</tr>

	</sakai:group_table>
</sakai:html>