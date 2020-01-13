<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setBundle basename="za.ac.unisa.lms.tools.regdetails.ApplicationResources"/>
 
<sakai:html>
 
<!-- Form -->
<html:form action="/croc">
	<html:hidden property="page" value="CrocStep1" />

	<sakai:messages />
	<sakai:messages message="true" />
	<sakai:heading><fmt:message key="page.croc.heading" /></sakai:heading>

	<sakai:instruction>
		<fmt:message key="page.croc.step1.instruction1"/><br/>
		<br/>
	</sakai:instruction>

	<sakai:group_heading>
		<fmt:message key="page.croc.step1.instruction2" />
	</sakai:group_heading>

	<sakai:actions>	
			<html:submit property="action"><fmt:message key="button.request"/></html:submit>
			<html:submit property="action"><fmt:message key="button.cancel"/></html:submit>	
	</sakai:actions>
</html:form>

</sakai:html
>