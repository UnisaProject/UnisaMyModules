<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setBundle basename="za.ac.unisa.lms.tools.regdetails.ApplicationResources"/>
 
<sakai:html>
 
<!-- Form -->
<html:form action="/croc">
	<html:hidden property="page" value="CrocStep2" />
	<html:hidden name="regDetailsForm" property="crocLetterRequest" />

	<sakai:messages />
	<sakai:messages message="true" />
	<sakai:heading><fmt:message key="page.croc.heading" /></sakai:heading>

	<logic:equal name="regDetailsForm" property="crocLetterRequest" value="true">
		
		<sakai:instruction>
			<fmt:message key="page.croc.step2.instruction1"/><br/>
			<br/>
		</sakai:instruction>
			&nbsp;<fmt:message key="page.croc.step2.email"/>&nbsp;&nbsp;&nbsp;<bean:write name="regDetailsForm" property="crocMyLife"/>
	</logic:equal>
	
	<logic:notEqual name="regDetailsForm" property="crocLetterRequest" value="true">
		<sakai:instruction>
			<br/>
			&nbsp;<fmt:message key="page.croc.step2.error1"/><br/><br/>
			&nbsp;<fmt:message key="page.croc.step2.error2"/><br/><br/>
			&nbsp;<fmt:message key="page.croc.step2.error3"/><br/>
			<br/>
		</sakai:instruction>
	</logic:notEqual>

	<sakai:actions>	
		<html:submit property="action"><fmt:message key="button.cancel"/></html:submit>	
	</sakai:actions>
</html:form>

</sakai:html>