<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>

<fmt:setBundle basename="za.ac.unisa.lms.tools.payments.ApplicationResources"/>

<sakai:html>
		<html:form action="displayFinancialDetails" focus="student.number">
		<sakai:messages/>
		<h3><fmt:message key="function.financialDetails"/></h3>
		<sakai:instruction><fmt:message key="function.input.instruction"/></sakai:instruction>
		<sakai:group_table>
			<tr>
				<td><fmt:message key="function.stno"/>&nbsp;</td>
				<td><html:text property="student.number" size="10" maxlength="8"/></td>
			</tr>
		</sakai:group_table>
		
		<sakai:actions>
			<html:submit property="action"><fmt:message key="button.displayfd"/></html:submit>
		</sakai:actions>
		
		</html:form>
</sakai:html>
