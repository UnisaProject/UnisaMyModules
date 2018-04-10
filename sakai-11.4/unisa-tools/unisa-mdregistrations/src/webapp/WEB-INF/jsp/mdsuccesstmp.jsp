<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setBundle basename="za.ac.unisa.lms.tools.mdregistrations.ApplicationResources"/>

<sakai:html>

<!-- Form -->
	<html:form action="/mdregistrations">
	<html:hidden property="page" value="tmpsuccess"/>

		<sakai:messages/>
		<sakai:messages message="true"/>
		<sakai:group_heading><fmt:message key="md.heading.p2"/></sakai:group_heading>

		<sakai:instruction>
		<p>
			<strong><fmt:message key="md.page7.success"/></strong><br/><br/>
			<fmt:message key="md.page7.date"/>&nbsp;
			<bean:write name="apptime"/><br/><br/> 
			<fmt:message key="md.page7.info1"/><br/></br>
			<strong><fmt:message key="md.page7.fees"/></strong><br/><br/>
			<fmt:message key="md.page7.amount"/><br>
			<bean:write name="amount"/><br/><br/> 
			<fmt:message key="md.page7.info2"/><br/></br>
			<fmt:message key="md.page7.info3"/><br/>
		</p>
		</sakai:instruction>
	
		<sakai:actions>
			<html:submit property="action"><fmt:message key="button.continue"/></html:submit>
		</sakai:actions>
	</html:form>
</sakai:html>