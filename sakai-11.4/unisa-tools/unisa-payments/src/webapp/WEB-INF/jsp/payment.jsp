<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setBundle basename="za.ac.unisa.lms.tools.payments.ApplicationResources"/>

<sakai:html>
	<sakai:heading><fmt:message key="function.payment"/></sakai:heading>
	<fmt:message key="function.dear"/> 
	<bean:write name="creditCardPaymentForm" property="title"/>
	<bean:write name="creditCardPaymentForm" property="surname"/>
	<p>
	<fmt:message key="function.confirmmessage"/>
	</p>
	<p>
	<fmt:message key="function.yours"/>
	</p>
	<p>
	<fmt:message key="function.deptfinance"/> <br>
	<fmt:message key="function.unisa"/> <br>
	<fmt:message key="function.financeemail"/> <br>
	</p>
</sakai:html>