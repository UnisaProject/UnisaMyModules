<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
 
<sakai:html>
	<sakai:heading>Current Cron Jobs</sakai:heading> 

	<sakai:instruction>
		<logic:present name="service">
		<!-- bean:write name="service" property="name"/ -->
		</logic:present>
	</sakai:instruction>

	<sakai:flat_list>
		<tr>
			<th>Cron Job Name</th>
			<th>Last Executed On</th>
			<th>Last Status</th>
		</tr>
		<logic:iterate id="cls" name="factory">
		<tr>
			<td><bean:write name="cls"/></td>
			<td>.</td>
			<td>.</td>
		</tr>
		</logic:iterate>
	</sakai:flat_list>
	
</sakai:html>