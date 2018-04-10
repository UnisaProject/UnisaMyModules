<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ page import="java.util.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setBundle basename="za.ac.unisa.lms.tools.password.ApplicationResources"/>

<sakai:html>
	<html:form action="/forgotPassword.do" >
	<html:hidden property="atstep" value="personneldetailstep"/>

	<fmt:message key="forget.pers.info1"/>


	<p>
	<sakai:group_heading> <fmt:message key="forget.headingstep2"/><bean:write name="forgetPasswordForm" property="studentNr"/> </sakai:group_heading>

	<sakai:messages/>
	<sakai:messages message="true"/>

	<sakai:group_table>
		<tr>
			<td colspan="2">
				<fmt:message key="forget.pers.info2"/>
				<fmt:message key="forget.pers.info3"/>
				<fmt:message key="forget.pers.info4"/>
			</td>
		<tr>
		<tr>
			<td colspan="2">
				<fmt:message key="forgotpassword.required"/> <sakai:required/>
			</td>
		</tr>
		
		<tr>
			<td> <fmt:message key="forget.pers.surname"/> <sakai:required/></td>
			<td align="left"> <html:text property="surname" size="30" maxlength="28"></html:text></td>
		</tr>
		<tr>
			<td> <fmt:message key="forget.pers.fullnames"/> <sakai:required/></td>
			<td> <html:text property="fullNames" size="30" maxlength="60"></html:text></td>
		</tr>
		<tr>
			<td> <fmt:message key="forget.pers.birthdate"/> <sakai:required/></td>
			<td>
				<html:text property="birthYear" size="4" maxlength="4"></html:text> /
				<html:text property="birthMonth" size="2" maxlength="2"></html:text> /
				<html:text property="birthDay" size="2" maxlength="2"></html:text>
			</td>
		</tr>
		<tr>
			<td> <fmt:message key="forget.pers.idnumber"/> <sakai:required/></td>
			<td> <html:text property="id_nr" size="30" maxlength="60"></html:text></td>
		</tr>
		<tr>
			<td> <fmt:message key="forget.pers.or"/></td>
			<td></td>
		</tr>
		<tr>
			<td> <fmt:message key="forget.pers.passport"/> <sakai:required/></td>
			<td> <html:text property="passport_nr" size="30" maxlength="60"></html:text></td>
		</tr>
			
	</sakai:group_table>
	<sakai:actions>
	    <html:hidden property="atstep" value="personneldetailstep"/>
		<html:submit property="action">
			<fmt:message key="button.continue"/>
		</html:submit>
		<html:submit property="action">
			<fmt:message key="button.clear"/>
		</html:submit>
	</sakai:actions>
	</html:form>
</sakai:html>