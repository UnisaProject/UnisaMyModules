<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setBundle basename="za.ac.unisa.lms.tools.mdregistrations.ApplicationResources"/>

<sakai:html>

<!-- Form -->
	<html:form action="/mdregistrations">
	<html:hidden property="page" value="successno"/>

		<sakai:messages/>
		<sakai:messages message="true"/>
		<sakai:group_heading><fmt:message key="md.heading.p2"/></sakai:group_heading>

		<sakai:instruction>
		<p><strong>
			<fmt:message key="md.page5.notsuccess"/><br/><br/>
			<fmt:message key="md.page5.error"/></strong><br/><br/>
			<bean:write name="errormessage"/><br/><br/><br/>
			<fmt:message key="md.page5.info"/><br/></br> 
		</p>
		</sakai:instruction>
	
		<sakai:actions>
			<html:submit property="action"><fmt:message key="button.backto"/></html:submit>
			<html:submit property="action"><fmt:message key="button.submitadv"/></html:submit>
		</sakai:actions>
	</html:form>
</sakai:html>