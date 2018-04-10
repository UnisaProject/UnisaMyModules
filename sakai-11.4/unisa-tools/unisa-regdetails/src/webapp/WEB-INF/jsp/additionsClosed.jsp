
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<fmt:setBundle basename="za.ac.unisa.lms.tools.regdetails.ApplicationResources"/>

<sakai:html>

<html:form action="/additions" >
	<html:hidden property="goto" value="home"/>
	
	<sakai:heading><fmt:message key="page.heading.additions"/></sakai:heading>
	
	<p><fmt:message key="page.additionsClosed"/>
	</p>
	
	  <sakai:actions>		
		<html:submit property="action"><fmt:message key="button.regdetails"/></html:submit>
 	</sakai:actions>
 
</html:form>

</sakai:html>
