<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setBundle basename="za.ac.unisa.lms.tools.payments.ApplicationResources"/>

<sakai:html>
	<sakai:heading><fmt:message key="function.payment"/></sakai:heading>
	<p>
	<fmt:message key="function.download"/>
	<fmt:message key="function.printed"/>
	</p>
	<p>
	<fmt:message key="function.note"/>
	</p>
</sakai:html>