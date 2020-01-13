<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setBundle basename="za.ac.unisa.lms.tools.mdregistrations.ApplicationResources"/>

<sakai:html>

<!-- Form -->
	<html:form action="/mdregistrations">
	<html:hidden property="page" value="success"/>

		<sakai:messages/>
		<sakai:messages message="true"/>
		<sakai:heading><fmt:message key="md.heading.p2"/></sakai:heading>

		<sakai:instruction>
		<p><strong>
			<fmt:message key="md.page6.success"/></strong><br/><br/>
			<fmt:message key="md.page6.date"/>&nbsp;
			<bean:write name="apptime"/><br/><br/> 
			<fmt:message key="md.page6.amount"/>&nbsp;
			<bean:write name="amount"/><br/><br/> 
		</p>
		</sakai:instruction>
	
		<sakai:actions>
			<html:submit property="action"><fmt:message key="button.continue"/></html:submit>
		</sakai:actions>
	</html:form>
</sakai:html>