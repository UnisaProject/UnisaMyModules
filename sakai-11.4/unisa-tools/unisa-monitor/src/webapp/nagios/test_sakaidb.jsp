<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ page import="za.ac.unisa.lms.tools.monitor.dao.SakaiQueryDAO" %>
<%@ page import="java.util.Vector" %>

<%
	SakaiQueryDAO dao = new SakaiQueryDAO();
	Vector results = dao.doQuery("select * from SAKAI_USER where user_id = 'admin'");
	request.setAttribute("results", results);
%>

<html>
<body>

	<table>
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
	</table>

	<p>NAGIOS_TEST_SUCCEEDED</p>
	
</body>
</html>

