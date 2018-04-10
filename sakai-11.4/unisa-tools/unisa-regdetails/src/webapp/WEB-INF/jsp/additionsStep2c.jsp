<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setBundle basename="za.ac.unisa.lms.tools.regdetails.ApplicationResources"/>



<sakai:html>
<head>

	<SCRIPT type='text/javascript'>

	function newWindow(newContent) {
	  	winContent = window.open(newContent, 'nextWin', 'toolbar=no,width=800,height=600,directories=no,scrollbars=yes,resizable=yes,menubar=no');         
	}
	
  </script>  
</head>

<!-- Form -->
	<html:form action="/additions">
	<html:hidden property="page" value="2c"/>
	<html:hidden property="goto" value="2c"/>

		<sakai:messages/>
		<sakai:messages message="true"/>
		<sakai:heading><fmt:message key="page.heading.step2c"/></sakai:heading>
		<sakai:instruction>
			<fmt:message key="page.step2c.instruction1"/>
		</sakai:instruction>
		<sakai:instruction>
			<fmt:message key="page.step2c.instruction2"/>
		</sakai:instruction>
		<html:radio property="phasedOutDeclare" value="true" /><fmt:message key="page.step2c.declare"/>
		<sakai:actions>
			<html:submit property="action"><fmt:message key="button.continue"/></html:submit>
			<html:submit property="action"><fmt:message key="button.back"/></html:submit>
			<html:submit property="action"><fmt:message key="button.cancel"/></html:submit>
		</sakai:actions>
	</html:form>

</sakai:html>