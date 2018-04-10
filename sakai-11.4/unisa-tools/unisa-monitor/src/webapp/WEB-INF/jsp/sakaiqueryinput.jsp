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

	<sakai:heading>Query the Sakai Database</sakai:heading>

	<html:form action="/sakaiquery">
		<sakai:group_heading>Query Information</sakai:group_heading>
		<sakai:group_table>
			<tr>
				<td>SQL Query Text</td>
				<td><html:textarea cols="60" rows="10" property="query"></html:textarea>
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

	<br/>

	<logic:present name="ds">
	<sakai:group_heading>Connection Information</sakai:group_heading>
	<sakai:group_table>
		<tr>
			<td>Auto Commit</td>
			<td><bean:write name="ds" property="defaultAutoCommit"/></td>
		</tr>
		<tr>
			<td>Active Connections</td>
			<td><bean:write name="ds" property="numActive"/></td>
		</tr>
		<tr>
			<td>Idle Connections</td>
			<td><bean:write name="ds" property="numIdle"/></td>
		</tr>
		<tr>
			<td>Maximum Active Connections</td>
			<td><bean:write name="ds" property="maxActive"/></td>
		</tr>
		<tr>
			<td>Maximum Idle Connections</td>
			<td><bean:write name="ds" property="maxIdle"/></td>
		</tr>
		<tr>
			<td>Maximum Time (ms) to Wait for a Connection</td>
			<td><bean:write name="ds" property="maxWait"/></td>
		</tr>
	</sakai:group_table>
	</logic:present>
		
</sakai:html>