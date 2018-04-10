<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page import="java.util.*" %>
<fmt:setBundle basename="za.ac.unisa.lms.tools.graduationceremony.ApplicationResources"/>

<sakai:html>
<!-- Form -->
	<html:form action="/graduationceremony">
	
		<sakai:messages/>
		<sakai:messages message="true"/>		
		<sakai:heading><fmt:message key="ceremstu.heading"/></sakai:heading>
		
		<!--<sakai:instruction>
		<fmt:message key="ceremstu.instruction"/><br/>	
		</sakai:instruction>-->
							
		<sakai:flat_list>
		<tr>
			<td colspan="2">&nbsp;</td>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<td><fmt:message key="ceremstu.apply.studnumber"/></td>
			<td colspan="2"><html:text name="graduationCeremonyForm" property="student.studentnumber" maxlength="8" size="10"/></td>
		</tr>
		<tr>
			<td><fmt:message key="ceremstu.apply.studsurname"/></td>
			<td colspan="2"><html:text name="graduationCeremonyForm" property="student.surname" maxlength="28" size="40"/></td>
		</tr>	
		<tr>
			<td><fmt:message key="ceremstu.apply.studfullname"/></td>
			<td colspan="2"><html:text name="graduationCeremonyForm" property="student.firstnames" maxlength="60" size="60"/></td>
		</tr>	
		<tr>
			<td colspan="2"><fmt:message key="ceremstu.apply.studbirthdate"/></td>
			<td>Day&nbsp;<html:text name="graduationCeremonyForm" property="student.birthDay" maxlength="2" size="2"/>&nbsp;
			&nbsp;Month&nbsp;<html:text name="graduationCeremonyForm" property="student.birthMonth" maxlength="2" size="2"/>&nbsp;
			&nbsp;Year(ccyy)&nbsp;<html:text name="graduationCeremonyForm" property="student.birthYear" maxlength="4" size="6"/>
			</td>
		</tr>	
		</sakai:flat_list>
		
		<sakai:actions>	
			<html:submit property="action"><fmt:message key="button.continue"/></html:submit>
			<html:submit property="action"><fmt:message key="button.reset"/></html:submit>
		</sakai:actions>
	</html:form>
	
</sakai:html>