<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>

<sakai:html>
	<sakai:tool_bar>
		<html:link action="/taglibs/index">Back</html:link>
		<html:link href="htmlareatag.source" target="htmlareatag">Source</html:link>
	</sakai:tool_bar>
	<sakai:heading>&lt;sakai:html_area&gt; Tag</sakai:heading>
	<p>
		Used to provide the user with a WYSIWIG html editor for a string property on a form.
		The result that gets posted to the form is the actual html code.
	</p>

	

	<html:form action="/taglibs/htmlareatag">
	<sakai:group_heading>Page Information</sakai:group_heading>
	<sakai:group_table>

		<tr>

			<td><label>Page title</label></td>

			<td><html:text property="title"/></td>

		</tr>

		<tr>

			<td><label>Description</label></td>

			<td><html:text property="description"/></td>

		</tr>
		<tr>
			<td><label>Content</label></td>
			<td><sakai:html_area property="content"/>
		</tr>

	</sakai:group_table>
	
	<sakai:actions>
		<html:submit styleClass="active"/>
	</sakai:actions>
	
	<sakai:group_heading>Preview</sakai:group_heading>
	
	<blockquote>
		<h3><bean:write name="htmlAreaTestForm" property="title"/></h3>
		<p><bean:write name="htmlAreaTestForm" property="description"/></p>
		<bean:write name="htmlAreaTestForm" property="content" filter="none"/>
	</blockquote>
	
	</html:form>
</sakai:html>