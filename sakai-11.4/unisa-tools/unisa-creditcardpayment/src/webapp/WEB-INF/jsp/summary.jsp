<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setBundle basename="za.ac.unisa.lms.tools.creditcardpayment.ApplicationResources"/>


<sakai:html>
	<html:form action="/creditCardPayment.do">
	<html:hidden property="page" value="summary"/>
	<sakai:heading><fmt:message key="page.heading"/></sakai:heading>
	<sakai:messages/>
	<sakai:messages message="true"/>
	<br>
	
	<sakai:instruction>
	<fmt:message key="page.payment.result"/>
	</sakai:instruction>
	<sakai:group_table>
		<tr>
		<logic:equal name="creditCardPaymentForm" property="errorOccured" value="true">
			<th><font color="RED"><bean:write name="creditCardPaymentForm" property="summaryMessage"/></font></th>
		</logic:equal>
		<logic:equal name="creditCardPaymentForm" property="errorOccured" value="false">
			<th><bean:write name="creditCardPaymentForm" property="summaryMessage"/></th>
		</logic:equal>
		</tr>
	</sakai:group_table>
	
	<sakai:actions>
		<sakai:actions>
		<logic:equal name="creditCardPaymentForm" property="studentUser" value="false">
			<html:submit property="action"><fmt:message key="button.payanother"/></html:submit>
		</logic:equal>
		<logic:equal name="creditCardPaymentForm" property="studentUser" value="true">
			<html:submit property="action"><fmt:message key="button.payment"/></html:submit>
		</logic:equal>
		<html:submit property="action"><fmt:message key="button.logout"/></html:submit>
		</sakai:actions>
	</sakai:actions>
	</html:form>
</sakai:html> 