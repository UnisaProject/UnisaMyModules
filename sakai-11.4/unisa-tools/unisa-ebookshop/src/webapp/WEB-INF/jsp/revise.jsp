<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<fmt:setBundle basename="za.ac.unisa.lms.tools.ebookshop.ApplicationResources"/>

<sakai:html>
	<sakai:messages/>
	<sakai:messages message="true"/>
	<sakai:heading>
		<fmt:message key="eshop.reviseadvert.title"/>
	</sakai:heading>
	<sakai:instruction>
		<fmt:message key="eshop.reviseadvert.instruction"/>
	</sakai:instruction>

	<html:form action="eshop.do?action=reviseActionTaken">
	<sakai:group_table>
		<tr>
			<td>
				<fmt:message key="eshop.reviseadvert.studentnumber"/>
			</td>
			<td>
				<html:text property="student.studentNumber" size="20"/>
			</td>
		</tr>
		<tr>
			<td>
				<fmt:message key="eshop.reviseadvert.password"/>
			</td>
			<td>
				<html:password property="student.password" size="20"/>
			</td>
		</tr>
	</sakai:group_table>


	<sakai:actions>
		<html:submit property="buttonClicked"><fmt:message key="eshop.button.editadd"/></html:submit>
		<html:submit property="buttonClicked"><fmt:message key="eshop.button.deleteadd"/></html:submit>
		<html:submit property="buttonClicked"><fmt:message key="eshop.button.cancel"/></html:submit>
	</sakai:actions>

	</html:form>
</sakai:html>