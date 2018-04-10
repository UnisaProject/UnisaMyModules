<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>

<fmt:setBundle basename="za.ac.unisa.lms.tools.honsadmission.ApplicationResources"/>

<sakai:html>
	<html:form action="/honsAdmission">
		<html:hidden property="currentPage" value="notAuthorised"/>
		<sakai:messages/>
		<sakai:messages message="true"/>
		<sakai:heading>
		<fmt:message key="page.notAuthorised.heading" />
	</sakai:heading>
	</html:form>
</sakai:html>