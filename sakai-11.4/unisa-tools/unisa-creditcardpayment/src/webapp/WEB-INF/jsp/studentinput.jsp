<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setBundle basename="za.ac.unisa.lms.tools.creditcardpayment.ApplicationResources"/>

<sakai:html>
	<html:form action="/creditCardPayment.do">
	<html:hidden property="page" value="studentinput"/>
	
	<sakai:heading><fmt:message key="page.heading"/></sakai:heading>
	<sakai:messages/>
	<sakai:messages message="true"/>
	<br>
	
	<sakai:instruction>
	<fmt:message key="page.input.info1"/><br/>
	<fmt:message key="page.input.info2"/>
	</sakai:instruction>

	<sakai:group_table>
		<tr>
			<td><strong><fmt:message key="page.studentApplyNr"/></strong></td>
			<td><html:text property="student.studentNumber" size="8" maxlength="8"/></td>
		</tr>
	</sakai:group_table>
	
	<sakai:actions>
			<html:submit property="action"><fmt:message key="button.continue"/></html:submit>
	</sakai:actions>
	
	</html:form>
</sakai:html> 