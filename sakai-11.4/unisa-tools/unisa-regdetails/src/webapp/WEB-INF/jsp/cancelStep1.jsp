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
			<!--<fmt:message key="page.cancel.step1.instruction1"/>
			<fmt:message key="page.general.step1.instruction2"/>
			<a HREF='http://www.unisa.ac.za/contents/studyinfo/docs/process_docs/cancel.html' onClick="window.open('http://www.unisa.ac.za/contents/studyinfo/docs/process_docs/cancel.html', 'nextPage', 'height=640,width=630,directories=no,scrollbars=yes,resizable=yes,menubar=no'); return false;" target="_blank">
			<fmt:message key="page.general.clickhere"/></a>
			<fmt:message key="page.general.todo"/>-->
		</sakai:instruction>

		<sakai:group_heading><fmt:message key="page.cancel.heading.step1"/></sakai:group_heading><sakai:group_table>
		<tr>
			<td><fmt:message key="page.email"/></td>
			<td><bean:write name="regDetailsForm" property="email"/></td>
			<td colspan ="3"><strong><fmt:message key="page.step1.instruction1"/></strong></td>
		</tr>
		<logic:notEmpty name="regDetailsForm" property="studyUnits">
		<tr>
			<td><fmt:message key="table.heading.code"/></td>
			<td><fmt:message key="table.heading.codeDesc"/></td>
			<td><fmt:message key="table.heading.semester.year"/></td>
			<td><fmt:message key="table.heading.semester"/></td>
			<td><fmt:message key="table.heading.cancel"/></td>
		</tr>
		<logic:iterate name="regDetailsForm" property="pickList" id="studyUnit" indexId="index">
			 <logic:present name="studyUnit" property="code">
			 	<tr>
			 		<td><bean:write name="studyUnit" property="code"/>&nbsp;</td>
			 		<td><bean:write name="studyUnit" property="desc"/>&nbsp;</td>
			 		<td><bean:write name="regDetailsForm" property="acadYear" />&nbsp;</td>
			 		<td><bean:write name="studyUnit" property="regPeriod"/>&nbsp;</td>
			 		<td><html:multibox property="selectedItems"><bean:write name="index"/></html:multibox></td>
				</tr>
			</logic:present>
		</logic:iterate>
	</logic:notEmpty>
	<tr>
	  <td colspan="5"><html:radio property="disclaimer" value="1"/><fmt:message key="page.cancel.disclaimer1"/>
			<!-- &nbsp;<a HREF='http://www.unisa.ac.za/contents/studyinfo/docs/process_docs/cancel.html' onClick="window.open('http://www.unisa.ac.za/contents/studyinfo/docs/process_docs/cancel.html', 'nextPage', 'height=640,width=630,directories=no,scrollbars=yes,resizable=yes,menubar=no'); return false;" target="_blank">
			<fmt:message key="page.cancel.disclaimer2"/></a>-->
			<fmt:message key="page.cancel.disclaimer2"/>
			<fmt:message key="page.cancel.disclaimer3"/></td>
	</tr>
		</sakai:group_table>

		<sakai:actions>
			<html:submit property="action"><fmt:message key="button.continue"/></html:submit></td>
			<html:submit property="action"><fmt:message key="button.cancel"/></html:submit>
		</sakai:actions>
	</html:form>

</sakai:html>