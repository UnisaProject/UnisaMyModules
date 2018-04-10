<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>

<fmt:setBundle basename="za.ac.unisa.lms.tools.assessmentcriteria.ApplicationResources"/>

<sakai:html>
	<html:form action="/assessmentCriteria">
		<html:hidden property="currentPage" value="requestAuthorisation"/>
		<sakai:messages/>
		<sakai:messages message="true"/>
		<sakai:heading>
			<fmt:message key="heading.requestAuthorisation"/>
		</sakai:heading>		
		<sakai:instruction>
			<fmt:message key="page.note1"/>&nbsp;<fmt:message key="page.mandatory"/>
		</sakai:instruction>
		<sakai:group_table>	
			<tr>
				<td><fmt:message key="page.studyUnit"/>&nbsp;</td>
				<td><bean:write name="assessmentCriteriaForm" property="studyUnit.code"/></td>
				<td><bean:write name="assessmentCriteriaForm" property="studyUnit.description"/></td>
			</tr>
			<tr>
				<td><fmt:message key="page.yearSemester"/>&nbsp;</td>			
				<td><bean:write name="assessmentCriteriaForm" property="academicYear"/>/<bean:write name="assessmentCriteriaForm" property="semesterType"/></td>
				<td>&nbsp;</td>
			</tr>			
			<tr>
				<td><fmt:message key="page.status"/>&nbsp;</td>			
				<td><bean:write name="assessmentCriteriaForm" property="statusDesc"/></td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td><fmt:message key="page.onlineMethod"/>&nbsp;</td>	
				<td colspan="2"><bean:write name="assessmentCriteriaForm" property="onlineMethod"/></td>
			</tr>
		</sakai:group_table>	
		<hr/>
		<sakai:group_table>
			<tr>
				<td><fmt:message key="page.department"/></td>
				<td><bean:write name="assessmentCriteriaForm" property="department.code"/>-
				<bean:write name="assessmentCriteriaForm" property="department.description"/></td>
			</tr>
		</sakai:group_table>
		<sakai:group_heading>
			<fmt:message key="page.instruction2"/> 
		</sakai:group_heading>
		<sakai:instruction>
			<fmt:message key="page.note6a"/>
		</sakai:instruction>
		<sakai:instruction>
			<fmt:message key="page.note6b"/>
		</sakai:instruction>
		<sakai:group_table>		
			<tr>
				<td colspan="2"><fmt:message key="page.cod"/></td>
			<tr>
			<logic:iterate name="codList" id="record">
				<tr>
					<td><html:radio property="selectedAuthoriser" idName="record" value="personnelNumber"></html:radio></td>
					<td><bean:write name="record" property="name"/></td>
					<td><bean:write name="record" property="emailAddress"/></td>
				</tr>
			</logic:iterate>
			<tr>
				<td colspan="2"><fmt:message key="page.actingCods"/></td>
			<tr>
			<logic:iterate name="assessmentCriteriaForm" property="department.actingCodList" id="record">
				<tr>
					<td><html:radio property="selectedAuthoriser" idName="record" value="personnelNumber"></html:radio></td>
					<td><bean:write name="record" property="name"/></td>
					<td><bean:write name="record" property="emailAddress"/></td>
				</tr>
			</logic:iterate>
		</sakai:group_table>	
		<sakai:instruction>
			<fmt:message key="page.note13"/>
		</sakai:instruction>
		<sakai:group_table>
			<tr>
				<td><fmt:message key="page.emailResponseAddress"/><fmt:message key="page.mandatory"/></td>
				<td><td><html:text name="assessmentCriteriaForm" property="responseEmailAddress" size="40" maxlength="60"/></td></td>
			</tr>
		</sakai:group_table>	
		<sakai:actions>			
			<html:submit property="act">
					<fmt:message key="button.request"/>
			</html:submit>
			<html:submit property="act">
					<fmt:message key="button.cancel"/>
			</html:submit>				
		</sakai:actions>
	</html:form>
</sakai:html>