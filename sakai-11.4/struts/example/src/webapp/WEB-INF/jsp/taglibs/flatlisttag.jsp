<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%> 
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>

<sakai:html>
	<sakai:tool_bar>
		<html:link action="/taglibs/index">Back</html:link>
		<html:link href="flatlisttag.source" target="flatlisttagsource">Source</html:link>
	</sakai:tool_bar>
	<sakai:heading>&lt;sakai:flat_list&gt; Tag</sakai:heading>

	<sakai:flat_list>
		<tr>
			<th>Name</th>
			<th>Surname</th>
		</tr>
		<logic:iterate name="students" id="student">
		<tr>
			<td><bean:write name="student" property="name"/></td>
			<td><bean:write name="student" property="surname"/></td>
		</tr>
		</logic:iterate>
	</sakai:flat_list>

	

</sakai:html>