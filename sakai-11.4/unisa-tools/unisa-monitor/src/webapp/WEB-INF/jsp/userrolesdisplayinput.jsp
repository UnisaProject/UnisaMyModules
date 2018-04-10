<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ page import="java.util.*" %>

<sakai:html>
	<sakai:tool_bar>
		<html:link action="/default.do">Back</html:link>
	</sakai:tool_bar>

	<sakai:messages/>
	<sakai:messages message="true"/>

	<sakai:heading>Display user roles</sakai:heading>

	<html:form action="/userrolesdisplay">
		<sakai:group_heading>User Information</sakai:group_heading>
		<sakai:group_table>
			<tr>
				<td>Student Number / User ID</td>
				<td><html:text property="userId"/>
			</tr>
		</sakai:group_table>
		<sakai:actions>
			<html:submit/>
		</sakai:actions>
		
	</html:form>

	<logic:present name="results">
		<sakai:group_heading>Results</sakai:group_heading>
		<sakai:flat_list>
		
			<logic:iterate id="result" name="results">
				<logic:present name="result" property="columns">
				<tr>
					<logic:iterate id="column" name="result" property="columns">
						<th><bean:write name="column"/></th>
					</logic:iterate>
				</tr>
				</logic:present>
				<logic:present name="result" property="values">
				<tr>
					<logic:iterate id="value" name="result" property="values">
						<td><logic:present name="value">
						<bean:write name="value"/>
						</logic:present></td>
					</logic:iterate>
				</tr>
				</logic:present>
			</logic:iterate>
		
		</sakai:flat_list>
	</logic:present>

</sakai:html>