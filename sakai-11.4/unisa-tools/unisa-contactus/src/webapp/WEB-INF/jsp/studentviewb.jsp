<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>


<fmt:setBundle basename="za.ac.unisa.lms.tools.contactus.ApplicationResources"/>


<sakai:html>
	<sakai:messages/>
	<sakai:messages message="true"/>
	<html:errors/>
	<sakai:heading><fmt:message key="page.studentviewb.heading"/></sakai:heading>
	<sakai:instruction>
		<br/><fmt:message key="page.studentviewb.note"/><br/><br/>
		<fmt:message key="page.studentviewb.select"/>
	</sakai:instruction>
	
	<html:form action="contactusaction.do?action=myUnisaCategorySelected">
		<sakai:group_table>
			<tr>
				<td><html:radio property="myUnisaCategorySelected" value="myUnisa_joining"/></td>
				<td><fmt:message key="page.studentviewb.joining"/></td>
			</tr>	
			<tr>
				<td><html:radio property="myUnisaCategorySelected" value="myUnisa_accountactivation"/></td>
				<td><fmt:message key="page.studentviewb.accountactivation"/></td>
			</tr>	
			<tr>
				<td><html:radio property="myUnisaCategorySelected" value="myUnisa_login"/></td>
				<td><fmt:message key="page.studentviewb.login"/></td>
			</tr>	
			<tr>
				<td><html:radio property="myUnisaCategorySelected" value="myUnisa_currentregistration"/></td>
				<td><fmt:message key="page.studentviewb.currentregistration"/></td>
			</tr>	
			<tr>
				<td><html:radio property="myUnisaCategorySelected" value="myUnisa_accountpayment"/></td>
				<td><fmt:message key="page.studentviewb.accountpayment"/></td>
			</tr>	
			<tr>
				<td><html:radio property="myUnisaCategorySelected" value="myUnisa_assignments"/></td>
				<td><fmt:message key="page.studentviewb.assignments"/></td>
			</tr>	
			<tr>
				<td><html:radio property="myUnisaCategorySelected" value="myUnisa_examinations"/></td>
				<td><fmt:message key="page.studentviewb.examinations"/></td>
			</tr>	
			<tr>
				<td><html:radio property="myUnisaCategorySelected" value="myUnisa_prescribedbooks"/></td>
				<td><fmt:message key="page.studentviewb.prescribedbooks"/></td>
			</tr>	
			<tr>
				<td><html:radio property="myUnisaCategorySelected" value="myUnisa_resources"/></td>
				<td><fmt:message key="page.studentviewb.resources"/></td>
			</tr>	
			<tr>
				<td><html:radio property="myUnisaCategorySelected" value="myUnisa_suggestions"/></td>
				<td><fmt:message key="page.studentviewb.suggestions"/></td>
			</tr>	
			<tr>
				<td><html:radio property="myUnisaCategorySelected" value="myUnisa_other"/></td>
				<td><fmt:message key="page.studentviewb.other"/></td>
			</tr>	
			<tr>
				<sakai:actions>
					<td><html:submit property="button" value="Continue"/></td>
					<td><html:submit property="button" value="Back"/></td>
				</sakai:actions>
			</tr>
		</sakai:group_table>
	</html:form>
	
</sakai:html>
