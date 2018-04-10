<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setBundle basename="za.ac.unisa.lms.tools.regdetails.ApplicationResources"/>

<sakai:html>

<!-- Form -->
	<html:form action="/additions">
	<html:hidden property="page" value="4a"/>
	<html:hidden property="goto" value="4a"/>

		<sakai:messages/>
		<sakai:messages message="true"/>
		<sakai:heading><fmt:message key="page.heading.step4a"/></sakai:heading>
		<sakai:instruction>
			<fmt:message key="page.step4a.instruction1"/>
		</sakai:instruction>
		<sakai:actions>
			<html:submit property="action"><fmt:message key="button.continue"/></html:submit>
		</sakai:actions>
	</html:form>

</sakai:html>