<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setBundle basename="za.ac.unisa.lms.tools.payments.ApplicationResources"/>

<sakai:html>
	<html:form action="/CreditCardPayment.do">
	<sakai:heading><fmt:message key="function.headingCredit"/></sakai:heading>
	<sakai:messages/>
	<sakai:messages message="true"/>
	
	<sakai:instruction>
		<fmt:message key="function.info5"/>
	</sakai:instruction>
	</html:form>
</sakai:html>