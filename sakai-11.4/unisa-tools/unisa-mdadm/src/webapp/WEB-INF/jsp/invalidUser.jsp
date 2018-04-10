
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>

<fmt:setBundle basename="za.ac.unisa.lms.tools.mdadm.ApplicationResources"/>

<sakai:html>
	<html:form action="/displaymdadmission">
		<html:hidden property="currentPage" value="invalidUser"/>
		<sakai:messages/>
		<sakai:messages message="true"/>
		<p><fmt:message key="page.lecturersOnly"/></p>
	</html:form>
</sakai:html>
