<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setBundle basename="za.ac.unisa.lms.tools.regdetails.ApplicationResources"/>

<sakai:html>
	<html:form action="/finalYearStudent">
	<html:hidden property="goto" value="2"/>
	
	<sakai:heading><fmt:message key="page.heading.finalyear"/></sakai:heading>
	<sakai:messages/>
	<sakai:messages message="true"/>
	<br>
	
	<sakai:instruction>
	<fmt:message key="page.finalyear.step1.instruction"/><br/>
	</sakai:instruction>

	<sakai:group_table>
		<tr>
			<td><strong><fmt:message key="page.twomodules"/></strong></td>
		</tr>
		<tr>
			<td><fmt:message key="page.heading.typeincode"/></td>
		</tr>
		<tr>
			 <td><html:text property='su1' size="7" maxlength="7"/></td>
		</tr><tr>
			 <td><html:text property='su2' size="7" maxlength="7"/></td>
		</tr>
	</sakai:group_table>
	
	<sakai:actions>
			<html:submit property="action"><fmt:message key="button.continue"/></html:submit>
	</sakai:actions>
	
	</html:form>
</sakai:html> 