<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setBundle basename="za.ac.unisa.lms.tools.payments.ApplicationResources"/>

<sakai:html>
	<sakai:tool_bar>
		<html:link href="CreditCardPayment.do?action=input">
			<fmt:message key="function.headingCredit"/>
		</html:link>
		<html:link href="CreditCardPayment.do?action=other">
			<fmt:message key="function.headingOther"/>
		</html:link>
	</sakai:tool_bar>
</sakai:html>