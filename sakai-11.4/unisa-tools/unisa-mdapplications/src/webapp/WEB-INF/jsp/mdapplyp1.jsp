<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setBundle basename="za.ac.unisa.lms.tools.mdapplications.ApplicationResources"/>

<sakai:html>

<!-- Form -->
	<html:form action="/mdapplications">
	<html:hidden property="page" value="step1"/>

		<sakai:messages/>
		<sakai:messages message="true"/>
		<sakai:heading><fmt:message key="md.heading.p1"/></sakai:heading>

		<sakai:group_heading><fmt:message key="md.subheading1"/></sakai:group_heading>

		<sakai:group_table>
		<tr>
			<td colspan="3"><fmt:message key="md.page1.instruction"/>&nbsp;<sakai:required/></td>
		</tr><tr>
			<td colspan="3"><strong><fmt:message key="md.page1.instruct2"/>&nbsp;</strong><sakai:required/></td>
		</tr><tr>
			<td><fmt:message key="md.page1.studnumber"/></td>
			<td><bean:write name="mdApplicationsForm" property="student.number"/></td>
		</tr><tr>
			<td><fmt:message key="md.page1.proqual"/>&nbsp;<sakai:required/></td>
			<td colspan="2">
				<html:select property="selectedQual">
 					<html:options collection="quallist" property="value" labelProperty="label"/>
				</html:select>
			</td>
		</tr><tr>
			<td><fmt:message key="md.page1.acadyear"/>&nbsp;</td>
			<td><bean:write name="mdApplicationsForm" property="student.academicYear"/></td>
		</tr><tr>
			<td><fmt:message key="md.page1.surname"/>&nbsp;<sakai:required/></td>
			<td><bean:write name="mdApplicationsForm" property="student.surname" /></td>
		</tr><tr>
			<td><fmt:message key="md.page1.initials"/>&nbsp;<sakai:required/></td>
			<td><bean:write name="mdApplicationsForm" property="student.initials" /></td>
		</tr><tr>
			<td><fmt:message key="md.page1.title"/>&nbsp;<sakai:required/></td>
<!--			<td><bean:write name="mdApplicationsForm" property="student.title" /></td>  -->
			<td colspan="2">
				<html:select property="student.title">
 					<html:options collection="titlelist" property="value" labelProperty="label"/>
				</html:select>
			</td>
		</tr><tr>
			<td><fmt:message key="md.page1.firstnames"/>&nbsp;<sakai:required/></td>
			<td><bean:write name="mdApplicationsForm" property="student.firstnames" /></td>
		</tr><tr>
			<td><fmt:message key="md.page1.maiden"/></td>
			<td><bean:write name="mdApplicationsForm" property="student.maidenName" /></td>
		</tr><tr>
			<td><fmt:message key="md.page1.birthdate"/>&nbsp;<sakai:required/></td>
			<td><bean:write name="mdApplicationsForm" property="student.birthYear" />
			<bean:write name="mdApplicationsForm" property="student.birthMonth" />
			<bean:write name="mdApplicationsForm" property="student.birthDay" /></td>
<!--			<td colspan="2">
				<html:text name="mdApplicationsForm" property="student.birthYear" maxlength="4" size="5"/>&nbsp;
				/&nbsp;<html:text name="mdApplicationsForm" property="student.birthMonth" maxlength="2" size="3"/>&nbsp;
				/&nbsp;<html:text name="mdApplicationsForm" property="student.birthDay" maxlength="2" size="3"/>&nbsp;
			</td>   -->
		</tr><tr>
			<td><fmt:message key="md.page1.enter"/>&nbsp;<sakai:required/></td>
			<td><html:radio property="student.idType" value="R"/><fmt:message key="md.page1.id"/></td>
			<td><bean:write name="mdApplicationsForm" property="student.idNumber" /></td>
		</tr><tr>
			<td>&nbsp;</td>
			<td><html:radio property="student.idType" value="P"/><fmt:message key="md.page1.foreign"/></td>
			<td><bean:write name="mdApplicationsForm" property="student.passportNumber" /></td>
		</tr><tr>
			<td><fmt:message key="md.page1.gender"/>&nbsp;<sakai:required/></td>
			<td><html:radio property="student.gender" value="F"/><fmt:message key="md.page1.female"/></td>
			<td><html:radio property="student.gender" value="M"/><fmt:message key="md.page1.male"/></td>
		</tr><tr>
			<td><fmt:message key="md.page1.language"/>&nbsp;<sakai:required/></td>
			<td><html:radio property="student.language" value="E"/><fmt:message key="md.page1.eng"/></td>
			<td><html:radio property="student.language" value="A"/><fmt:message key="md.page1.afr"/>
		</tr><tr>
			<td><fmt:message key="md.page1.disability"/></td>
			<td colspan="2">
				<html:select property="selectedDisability">
 					<html:options collection="disabilitylist" property="value" labelProperty="label"/>
				</html:select>
			</td>
		</tr><tr>
			<td colspan="2">
				<html:radio  property ="agreeQualInfo" value="Y"/>&nbsp;<fmt:message key="md.page1.agree"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			</td>
		</tr>
		</sakai:group_table>

		<sakai:actions>
			<html:submit property="action"><fmt:message key="button.continue"/></html:submit>
			<html:submit property="action"><fmt:message key="button.cancel"/></html:submit>
		</sakai:actions>
	</html:form>
</sakai:html>