<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setBundle basename="za.ac.unisa.lms.tools.payments.ApplicationResources"/>

<sakai:html>
	<html:form action="/CreditCardPayment.do">
	<sakai:heading><fmt:message key="function.confirmpayment"/></sakai:heading>

	<br/>
	<sakai:group_heading> <fmt:message key="function.confirm"/> </sakai:group_heading>
	<sakai:group_table>
	<tr>	
		<td><fmt:message key="function.matric"/></td>
		<td align="right">
			<fmt:message key="function.rand"/>
			<bean:write name="creditCardPaymentForm" property="matricExempFees" format="0.00"/> 
		</td>
	</tr>
	<tr>
		<td><fmt:message key="function.studyfees"/> </td>
		<td align="right">
			<fmt:message key="function.rand"/>
		    <bean:write name="creditCardPaymentForm" property="studyFees" format="0.00"/>	
		</td>
	</tr>
	<tr>
		<td><fmt:message key="function.total"/></td>
		<td align="right">
			<fmt:message key="function.rand"/>
			<bean:write name="creditCardPaymentForm" property="total" format="0.00"/>
		</td>
	</tr>
	</sakai:group_table>

	<sakai:actions>
		<html:submit property="action">
			<fmt:message key="button.save"/>
		</html:submit>
 		<html:submit property="action">	
 			<fmt:message key="button.back"/>
		</html:submit>
	</sakai:actions>
	</html:form>
</sakai:html>