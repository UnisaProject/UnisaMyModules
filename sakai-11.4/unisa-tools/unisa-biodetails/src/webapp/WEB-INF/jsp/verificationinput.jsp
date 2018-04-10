<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ page import="java.util.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setBundle basename="za.ac.unisa.lms.tools.join.ApplicationResources"/>

<html:html>
<html:body>
<html:form action="/verifyemail">
	<h3>E-mail Address Verification</h3>
	
	<p>
	<table>
	<tr>
		<td>Student number</td>
		<td><html:text property="studentno" size="10"/></td>
	</tr>
	<tr>
		<td>Verification code</td>
		<td><html:text property="code" size="20"/></td>
	</tr>
	</table>
		<html:hidden property="action" value="verify"/>
	<html:submit>Continue</html:submit>	
	
</html:form>
</html:body>
</html:html>