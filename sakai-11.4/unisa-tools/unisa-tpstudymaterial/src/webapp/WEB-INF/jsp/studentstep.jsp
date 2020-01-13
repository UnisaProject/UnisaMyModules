<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ page import="java.util.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setBundle basename="za.ac.unisa.lms.tools.tpstudymaterial.ApplicationResources"/>
<sakai:html>
<html:form action="/tpStudyMaterial.do">
	<sakai:messages />
	<sakai:messages message="true" />
			<fmt:message key="tpstudymaterial.stno.heading"/><br><br>
	<fmt:message key="tpstudymaterial.stno.info1" />
	<br>
	<br>
	<fmt:message key="tpstudymaterial.stno.info2" /><br><br>
	<sakai:group_table>
			<tr><td><fmt:message key="tpstudymaterial.stno.info3"/></td></tr>
		<tr><td><fmt:message key="tpstudymaterial.stno.info4" /></td></tr>
		<tr><td><fmt:message key="tpstudymaterial.stno.info5" /></td></tr>		
		<tr><td><fmt:message key="tpstudymaterial.stno.info6" /></td></tr>
	</sakai:group_table>
	
	<sakai:group_heading>
		<fmt:message key="tpstudymaterial.stno.heading2" />
	</sakai:group_heading>
	<sakai:group_table>
		<tr>
			<td><fmt:message key="tpstudymaterial.stno" /> <sakai:required /></td>
			<td><html:text property="studentNr" size="10" maxlength="8"></html:text></td>
		</tr>
		<tr>
			<td><fmt:message key="tpstudymaterial.surname" /> <sakai:required /></td>
			<td><html:text property="surname" size="30" maxlength="60"></html:text></td>
		</tr>
		<tr>
			<td><fmt:message key="tpstudymaterial.fullnames" /> <sakai:required /></td>
			<td><html:text property="fullNames" size="30" maxlength="60"></html:text></td>
		</tr>
		<tr>
			<td> <fmt:message key="tpstudymaterial.birthdate"/> <sakai:required/></td>
			<td>
				<html:text property="birthYear" size="4" maxlength="4"></html:text> /
				<html:text property="birthMonth" size="2" maxlength="2"></html:text> /
				<html:text property="birthDay" size="2" maxlength="2"></html:text>
			</td>
		</tr>
	</sakai:group_table>
	<sakai:actions>
		<html:submit property="action">
			<fmt:message key="button.continue"/>
		</html:submit>
	</sakai:actions>

</html:form>
</sakai:html>