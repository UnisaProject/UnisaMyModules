<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setBundle basename="za.ac.unisa.lms.tools.mdregistrations.ApplicationResources"/>

<sakai:html>
<!-- Form -->
	<html:form action="/mdregistrations">
	<html:hidden property="page" value="step0"/>
	
		<sakai:messages/>
		<sakai:messages message="true"/>		
		<sakai:heading><fmt:message key="md.heading"/></sakai:heading>
		
		<sakai:instruction><strong>
		<fmt:message key="md.instruction"/><br/></strong>	
		</sakai:instruction>
							
		<sakai:flat_list>
		<tr>
			<td colspan="2">&nbsp;</td>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<td><fmt:message key="md.regis.studnumber"/></td>
			<td colspan="2"><html:text name="mdRegistrationsForm" property="student.number" maxlength="8" size="10"/></td>
		</tr>
		<tr>
			<td><fmt:message key="md.regis.studsurname"/></td>
			<td colspan="2"><html:text name="mdRegistrationsForm" property="student.surname" maxlength="28" size="40"/></td>
		</tr>	
		<tr>
			<td><fmt:message key="md.regis.studfullname"/></td>
			<td colspan="2"><html:text name="mdRegistrationsForm" property="student.firstnames" maxlength="60" size="60"/></td>
		</tr>	
		<tr>
			<td colspan="2"><fmt:message key="md.regis.studbirthdate"/></td>
			<td>Year(ccyy)&nbsp;<html:text name="mdRegistrationsForm" property="student.birthYear" maxlength="4" size="6"/>&nbsp;
			&nbsp;Month&nbsp;<html:text name="mdRegistrationsForm" property="student.birthMonth" maxlength="2" size="2"/>&nbsp;
			&nbsp;Day&nbsp;<html:text name="mdRegistrationsForm" property="student.birthDay" maxlength="2" size="2"/>
			</td>
		</tr>	
		</sakai:flat_list>
		
		<sakai:actions>	
			<html:submit property="action"><fmt:message key="button.submit"/></html:submit>
			<logic:equal name="mdRegistrationsForm" property="errorAdvisor" value="Y">
			<html:submit property="action"><fmt:message key="button.submitadv"/></html:submit>
			</logic:equal>
		</sakai:actions>
		
		<div style="display: none;"><br><center><bean:write name="mdRegistrationsForm" property="version"/></center></div>
	</html:form>
	
</sakai:html>