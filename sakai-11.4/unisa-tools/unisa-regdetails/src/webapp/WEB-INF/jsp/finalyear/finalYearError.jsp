<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setBundle basename="za.ac.unisa.lms.tools.regdetails.ApplicationResources"/>

<sakai:html>
	<html:form action="/finalYearStudent.do">
	
	<sakai:heading><fmt:message key="page.heading.finalyear"/></sakai:heading>
	<sakai:messages/>
	<sakai:messages message="true"/>
	<br>

	<sakai:group_table>
		<tr>
			<td><strong><fmt:message key="page.finalyear.requirementerror"/></strong></td>
		</tr>
	</sakai:group_table>
	
	<sakai:actions>
			<html:submit property="action"><fmt:message key="button.cancel"/></html:submit>
	</sakai:actions>
	
	</html:form>
</sakai:html> 