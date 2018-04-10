<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setBundle basename="za.ac.unisa.lms.tools.mdapplications.ApplicationResources"/>

<sakai:html>
<!-- Form -->
	<html:form action="/mdapplications">
	<html:hidden property="page" value="step0"/>
	
		<sakai:messages/>
		<sakai:messages message="true"/>	
		
		<logic:equal name="mdApplicationsForm" property="loginType" value="app">	
			<sakai:heading><fmt:message key="md.heading"/></sakai:heading>
		</logic:equal>
		<logic:equal name="mdApplicationsForm" property="loginType" value="doc">	
			<sakai:heading><fmt:message key="md.heading.doc"/></sakai:heading>
		</logic:equal>
		
		<sakai:instruction>
		<fmt:message key="md.instruction"/><br/>	
		</sakai:instruction>
							
		<sakai:flat_list>
		<tr>
			<td colspan="2">&nbsp;</td>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<td><fmt:message key="md.apply.studnumber"/></td>
			<td colspan="2"><html:text name="mdApplicationsForm" property="student.number" maxlength="8" size="10"/></td>
		</tr>
		<tr>
			<td><fmt:message key="md.apply.studsurname"/></td>
			<td colspan="2"><html:text name="mdApplicationsForm" property="student.surname" maxlength="28" size="40"/></td>
		</tr>	
		<tr>
			<td><fmt:message key="md.apply.studfullname"/></td>
			<td colspan="2"><html:text name="mdApplicationsForm" property="student.firstnames" maxlength="60" size="60"/></td>
		</tr>	
		<tr>
			<td colspan="2"><fmt:message key="md.apply.studbirthdate"/></td>
			<td>Year(ccyy)&nbsp;<html:text name="mdApplicationsForm" property="student.birthYear" maxlength="4" size="6"/>&nbsp;
			&nbsp;Month&nbsp;<html:text name="mdApplicationsForm" property="student.birthMonth" maxlength="2" size="2"/>&nbsp;
			&nbsp;Day&nbsp;<html:text name="mdApplicationsForm" property="student.birthDay" maxlength="2" size="2"/>
			</td>
		</tr>	
		</sakai:flat_list>
		
		<sakai:actions>	
			<html:submit property="action"><fmt:message key="button.submit"/></html:submit>
		</sakai:actions>
	</html:form>
	<BR/>
	<div style="display: none;"><br><bean:write name="mdApplicationsForm" property="version"/></div>
	<BR/>
</sakai:html>