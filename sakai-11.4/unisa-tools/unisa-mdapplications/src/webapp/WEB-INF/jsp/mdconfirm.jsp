<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setBundle basename="za.ac.unisa.lms.tools.mdapplications.ApplicationResources"/>

<sakai:html>

<!-- Form -->
	<html:form action="/mdapplications">
	<html:hidden property="page" value="confirmation"/>

		<sakai:messages/>
		<sakai:messages message="true"/>
		<sakai:heading><fmt:message key="md.heading"/></sakai:heading>

		<sakai:instruction>
		<p><strong>
			<fmt:message key="md.confirmation1"/></strong><br/><br/>
			<fmt:message key="md.confirmation2"/><br/>
			<fmt:message key="md.confirmation3"/>
			<bean:write name="apptime"/><br/><br/> 
		</p>
		</sakai:instruction>
	
		<sakai:actions>
			<html:submit property="action"><fmt:message key="button.backto"/></html:submit>
			<html:submit property="action"><fmt:message key="button.submitdoc"/></html:submit>
		</sakai:actions>
	</html:form>
</sakai:html>