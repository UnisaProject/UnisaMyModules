<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>

<sakai:html>
	<sakai:tool_bar>
		<html:link action="/taglibs/index">Back</html:link>
		<html:link href="newhtmlareatag.source" target="newhtmlareatag">Source</html:link>
	</sakai:tool_bar>
	<sakai:heading>&lt;sakai:new_html_area&gt; Tag</sakai:heading>
	<p>
		Used to provide the user with a WYSIWIG html editor for a string property on a form.
		The result that gets posted to the form is the actual html code.
	</p>
	<p>
		Its the same as the sakai:html_area tag but the editor is smaller. We use this tag for example in our Home tool for a course
		Site where there is 3 tools and 2 of them are next to each other. The editor is small enoght to fit into the "tool space" provided.
	</p>

	

	<html:form action="/taglibs/newhtmlareatag">
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
			<td><sakai:new_html_area property="content"/>
		</tr>

	</sakai:group_table>
	
	<sakai:actions>
		<html:submit styleClass="active"/>
	</sakai:actions>
	
	<sakai:group_heading>Preview</sakai:group_heading>
	
	<blockquote>
		<h3><bean:write name="newHtmlAreaTestForm" property="title"/></h3>
		<p><bean:write name="newHtmlAreaTestForm" property="description"/></p>
		<bean:write name="newHtmlAreaTestForm" property="content" filter="none"/>
	</blockquote>
	
	</html:form>
</sakai:html>