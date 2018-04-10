<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setBundle basename="za.ac.unisa.lms.tools.mdregistrations.ApplicationResources"/>

<sakai:html>

<!-- Form -->
	<html:form action="/mdregistrations">
	<html:hidden property="page" value="step1"/>

		<sakai:messages/>
		<sakai:messages message="true"/>
		<sakai:group_heading><fmt:message key="md.heading.p1"/></sakai:group_heading>

<!--		<sakai:heading><fmt:message key="md.subheading1"/></sakai:heading>  -->

		<sakai:group_table>
		<tr>
			<td colspan="2"><fmt:message key="md.page1.instruction"/></td>
		</tr><tr>
			<td colspan="2"><fmt:message key="md.page1.instruct2"/></td>
		</tr>
		<tr>
			<td><fmt:message key="md.page1.studnumber"/></td>
			<td><bean:write name="mdRegistrationsForm" property="student.number"/></td>
		</tr><tr>
			<td><fmt:message key="md.page1.acadyear"/>&nbsp;</td>
			<td><bean:write name="mdRegistrationsForm" property="student.academicYear"/></td>
		</tr><tr>
			<td><fmt:message key="md.page1.proqual"/>&nbsp;</td>
			<td><bean:write name="mdRegistrationsForm" property="qual.qualCode"/></td>
		</tr><tr>
			<td><fmt:message key="md.page1.prospes"/>&nbsp;</td>
			<td><bean:write name="mdRegistrationsForm" property="qual.specCode"/></td>
		</tr><tr>
			<td><fmt:message key="md.page1.surname"/>&nbsp;<sakai:required/></td>
			<td><bean:write name="mdRegistrationsForm" property="student.surname" /></td>
		</tr><tr>
			<td><fmt:message key="md.page1.initials"/>&nbsp;<sakai:required/></td>
			<td><bean:write name="mdRegistrationsForm" property="student.initials" /></td>
		</tr><tr>
			<td><fmt:message key="md.page1.title"/>&nbsp;<sakai:required/></td>
			<td><bean:write name="mdRegistrationsForm" property="student.title" /></td> 
		</tr><tr>
			<td><fmt:message key="md.page1.firstnames"/>&nbsp;<sakai:required/></td>
			<td><bean:write name="mdRegistrationsForm" property="student.firstnames" /></td>
		</tr><tr>
			<td><fmt:message key="md.page1.maiden"/>&nbsp;<sakai:required/></td>
			<td><bean:write name="mdRegistrationsForm" property="student.maidenName" /></td>
		</tr><tr>
			<td><fmt:message key="md.page1.birthdate"/>&nbsp;<sakai:required/></td>
			<td><bean:write name="mdRegistrationsForm" property="student.birthYear" />
			<bean:write name="mdRegistrationsForm" property="student.birthMonth" />
			<bean:write name="mdRegistrationsForm" property="student.birthDay" /></td>
		</tr><tr>
			<td><fmt:message key="md.page1.id"/>&nbsp;<sakai:required/></td>
			<td><bean:write name="mdRegistrationsForm" property="student.idNumber" /></td>
		</tr><tr>
			<td><fmt:message key="md.page1.foreign"/>&nbsp;<sakai:required/></td>
			<td><bean:write name="mdRegistrationsForm" property="student.passportNumber" /></td>
		</tr><tr>
			<td><fmt:message key="md.page1.gender"/>&nbsp;</td>
			<td><bean:write name="mdRegistrationsForm" property="student.gender" /></td>
		</tr><tr>
			<td><fmt:message key="md.page1.language"/>&nbsp;</td>
			<td><bean:write name="mdRegistrationsForm" property="student.language" /></td> 
		</tr><tr>
			<td><fmt:message key="md.page1.disability"/></td>
			<td><bean:write name="mdRegistrationsForm" property="student.disability.code" /></td>
		</tr>
		
		<logic:equal name="mdRegistrationsForm" property="studyUnitAddition" value="false">
		<tr>
			<td><fmt:message key="md.page1.examcentre"/></td>
			<td>
				<html:select property="selectedExamCentre">
 					<html:options collection="examlist" property="value" labelProperty="label"/>
				</html:select>
			</td>
		</tr>
		<tr>
			<td colspan="2"><fmt:message key="md.page1.examcentre.info"/></td>
		</tr>
		</logic:equal>
		
		<tr>
			<td><fmt:message key="md.page1.sucount"/>&nbsp;</td>
			<td>
				<html:select property="numberOfUnits">
 					<html:options collection="numbers" property="value" labelProperty="label"/>
				</html:select>
			</td>
		</tr>
		</sakai:group_table>

		<sakai:actions>
			<html:submit property="action"><fmt:message key="button.continue"/></html:submit>
			<html:submit property="action"><fmt:message key="button.cancel"/></html:submit>
		</sakai:actions>
	</html:form>
</sakai:html>