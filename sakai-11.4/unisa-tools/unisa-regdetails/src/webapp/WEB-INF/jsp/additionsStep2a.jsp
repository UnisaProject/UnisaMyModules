<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setBundle basename="za.ac.unisa.lms.tools.regdetails.ApplicationResources"/>

<sakai:html>

<!-- Form -->
	<html:form action="/additions">
	<html:hidden property="goto" value="speciality"/>

		<sakai:messages/>
		<sakai:messages message="true"/>
		<sakai:heading><fmt:message key="page.heading.additions"/></sakai:heading>
<br/>
		<sakai:group_heading><fmt:message key="page.additions.option2"/></sakai:group_heading>

		<sakai:group_table>
		<tr>
			<td colspan="2"><fmt:message key="page.heading.step2a"/></td>
		</tr><tr>
			<td><fmt:message key="page.qualList"/></td>
			<td><html:select property="selectedQual">
				<html:options collection="qualList" property="value" labelProperty="label"/>
			</html:select></td>
		</tr>
		</sakai:group_table>

		<sakai:actions>
			<html:submit property="action"><fmt:message key="button.continue"/></html:submit>
			<html:submit property="action"><fmt:message key="button.back"/></html:submit>
			<html:submit property="action"><fmt:message key="button.cancel"/></html:submit>
		</sakai:actions>
	</html:form>

</sakai:html>