<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page import="java.util.*" %>
<fmt:setBundle basename="za.ac.unisa.lms.tools.graduationceremony.ApplicationResources"/>

<sakai:html>
<!-- Form -->
	<html:form action="/graduationceremony">
	
		<sakai:messages/>
		<sakai:messages message="true"/>		
		<sakai:heading><fmt:message key="ceremstu.heading"/></sakai:heading>
		
		<sakai:instruction>
		<fmt:message key="ceremstu.not.line1"/><br/>	
		<fmt:message key="ceremstu.not.line2"/><br/>
		</sakai:instruction>

		<sakai:group_table>
		<tr>
			<td><fmt:message key="ceremstu.not.line3"/></td>	
		</tr>
		<tr>
			<td><fmt:message key="ceremstu.not.line4"/></td>
		</tr>
		<tr>
			<td><fmt:message key="ceremstu.not.line5"/></td>	
		</tr>
		<tr>
			<td><fmt:message key="ceremstu.not.line6"/></td>
		</tr>
		</sakai:group_table>
		
		<sakai:instruction>
		<fmt:message key="ceremstu.not.line7"/><br/>	
		<fmt:message key="ceremstu.not.line8"/><br/>
		</sakai:instruction>
							

		<sakai:actions>
			<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
			<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>	
			<html:submit property="action"><fmt:message key="button.back"/></html:submit>
		</sakai:actions>
	</html:form>
	
</sakai:html>