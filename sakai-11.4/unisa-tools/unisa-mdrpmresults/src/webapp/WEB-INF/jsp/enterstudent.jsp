<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<fmt:setBundle
	basename="za.ac.unisa.lms.tools.mdrpmresults.ApplicationResources" />


<sakai:html>
<html:form action="/displaymdrpmresults">
	<html:hidden property="action" value="display" />

	<sakai:messages />
	<sakai:messages message="true" />
	<sakai:heading>
		<fmt:message key="page1.main.title" />
	</sakai:heading>

	<sakai:group_table>
		<tr>
			<td><sakai:instruction>
					<fmt:message key="page.instruction.enterstudent" />
				</sakai:instruction></td>
		</tr>
		<tr>
			<td><strong><fmt:message key="page.label.studentnumber" /></strong></td>
			<td><html:text name="mdRPMResultsForm"
					property="student.studentNumber" size="10" maxlength="8" /></td>
		</tr>
	</sakai:group_table>

	<sakai:actions>
		<html:submit property="action">
			<fmt:message key="button.continue" />
		</html:submit>
	</sakai:actions>

</html:form>
</sakai:html>
