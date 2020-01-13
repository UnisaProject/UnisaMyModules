<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setBundle basename="za.ac.unisa.lms.tools.regdetails.ApplicationResources"/>

<sakai:html>

<!-- Form -->
	<html:form action="/cancel">
	<html:hidden property="goto" value="2"/>

		<sakai:messages/>
		<sakai:messages message="true"/>
		<sakai:heading><fmt:message key="page.heading.cancel"/></sakai:heading>

		<sakai:instruction>
			<fmt:message key="page.cancel.confirm.instruction1"/>
			<br/>
		</sakai:instruction>

		<sakai:group_heading><fmt:message key="page.cancel.heading.step2"/></sakai:group_heading>
		<sakai:group_table>
		<tr><td><fmt:message key="page.email"/></td>
			<td><bean:write name="regDetailsForm" property="email"/></td>
			<td><strong><fmt:message key="page.step1.instruction1"/></strong></td>
		</tr>
		<logic:notEmpty name="regDetailsForm" property="cancelStudyUnits">
		<tr>
			<td><fmt:message key="table.heading.code"/></td>
			<td><fmt:message key="table.heading.codeDesc"/></td>
			<td><fmt:message key="table.heading.semester"/></td>
			<td>&nbsp;</td>
		</tr>
		<logic:iterate name="regDetailsForm" property="cancelStudyUnits" id="cancelStudyUnits" indexId="index">
			 <logic:present name="cancelStudyUnits" property="code">
			 	<tr>
			 		<td><bean:write name="cancelStudyUnits" property="code"/>&nbsp;</td>
			 		<td><bean:write name="cancelStudyUnits" property="desc"/>&nbsp;</td>
			 		<td><bean:write name="cancelStudyUnits" property="regPeriod"/>&nbsp;</td>
			 		<td>&nbsp;</td>
			 	</tr>
			</logic:present>
		</logic:iterate>
	</logic:notEmpty>
		</sakai:group_table>

		<sakai:actions>
			<html:submit property="action"><fmt:message key="button.save"/></html:submit></td>
			<html:submit property="action"><fmt:message key="button.back"/></html:submit>
			<html:submit property="action"><fmt:message key="button.cancel"/></html:submit>
		</sakai:actions>
	</html:form>

</sakai:html>