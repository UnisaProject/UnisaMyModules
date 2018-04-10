
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<fmt:setBundle basename="za.ac.unisa.lms.tools.regdetails.ApplicationResources"/>

<sakai:html>

<html:form action="/additions" >
	<html:hidden property="goto" value="home"/>
	
	<sakai:heading><fmt:message key="page.heading.additions"/></sakai:heading>
	
	<p><fmt:message key="page.oneSubmission1"/>
		<fmt:message key="page.oneSubmission2"/>
		<a HREF='http://www.unisa.ac.za/default.asp?cmd=ViewContent&ContentID=17554' onClick="window.open('http://www.unisa.ac.za/default.asp?cmd=ViewContent&ContentID=17554', 'nextPage', 'height=640,width=630,directories=no,scrollbars=yes,resizable=yes,menubar=no'); return false;" target="_blank">
		<fmt:message key="page.general.contact"/></a>
		<fmt:message key="page.general.unisa"/>
	</p>
	
	  <sakai:actions>		
		<html:submit property="action"><fmt:message key="button.regdetails"/></html:submit>
 	</sakai:actions>
 
</html:form>

</sakai:html>
