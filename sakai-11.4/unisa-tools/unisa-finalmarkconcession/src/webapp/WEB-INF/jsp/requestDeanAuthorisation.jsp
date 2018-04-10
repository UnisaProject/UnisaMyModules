<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>

<fmt:setBundle basename="za.ac.unisa.lms.tools.finalmarkconcession.ApplicationResources"/>

<sakai:html>
	<html:form action="/authorisation">
		<html:hidden property="currentPage" value="requestDeanAuthorisation"/>
		<sakai:messages/>
		<sakai:messages message="true"/>
		<sakai:heading>
			<fmt:message key="heading.requestAuthorisation"/>
		</sakai:heading>		
		<sakai:instruction>
			<fmt:message key="note.required"/>&nbsp;<fmt:message key="prompt.required"/>
		</sakai:instruction>
		<sakai:group_table>
			<tr>
				<td><fmt:message key="prompt.student"/></td>
				<td><bean:write name="finalMarkConcessionForm" property="recordSelected.studentNumber"/></td>
				<td><bean:write name="finalMarkConcessionForm" property="recordSelected.name"/></td>
			</tr>
			<tr>
			     <td><fmt:message key="prompt.student.module"/></td>
				 <td><bean:write name="finalMarkConcessionForm" property="recordSelected.studyUnit"/></td>	
				 <td><bean:write name="finalMarkConcessionForm" property="recordSelected.academicYear"/>/<bean:write name="finalMarkConcessionForm" property="recordSelected.semesterType"/></td>
			</tr>
			<tr>
			     <td><fmt:message key="prompt.student.result"/></td>
				 <td><bean:write name="finalMarkConcessionForm" property="recordSelected.finalMark"/></td>	
				 <td><bean:write name="finalMarkConcessionForm" property="recordSelected.resultDesc"/></td>
			</tr>
			<tr>
				<td><fmt:message key="prompt.student.qual"/></td>
				<td><bean:write name="finalMarkConcessionForm" property="recordSelected.qualification.qualCode"/></td>
				<td><bean:write name="finalMarkConcessionForm" property="recordSelected.qualification.qualDesc"/></td>
			</tr>
			<tr>
			     <td><fmt:message key="prompt.student.status"/></td>
				 <td><bean:write name="finalMarkConcessionForm" property="alternativeExamOpportunity.status"/></td>	
				 <td>
			</tr>
		</sakai:group_table>
		<hr>
		<sakai:group_table>
			<tr>
				<td><fmt:message key="prompt.department"/></td>
				<td><bean:write name="finalMarkConcessionForm" property="authDepartment.code"/>-
				<bean:write name="finalMarkConcessionForm" property="authDepartment.description"/></td>
			</tr>
		</sakai:group_table>
		<sakai:group_heading>
			<fmt:message key="note.requestAuthorisation.1"/> 
		</sakai:group_heading>
		<sakai:instruction>
			<fmt:message key="note.requestAuthorisation.2"/>
		</sakai:instruction>
		<sakai:instruction>
			<fmt:message key="note.requestAuthorisation.3"/>
		</sakai:instruction>
		<sakai:group_table>		
			<tr>
				<td colspan="2"><fmt:message key="prompt.dean"/></td>
			<tr>
			<logic:iterate name="deanList" id="record">
				<tr>
					<td><html:radio property="selectedAuthoriser" idName="record" value="personnelNumber"></html:radio></td>
					<td><bean:write name="record" property="name"/></td>
					<td><bean:write name="record" property="emailAddress"/></td>
				</tr>
			</logic:iterate>
			<tr>
				<td colspan="2"><fmt:message key="prompt.deputyDean"/></td>
			<tr>
			<logic:iterate name="finalMarkConcessionForm" property="authDepartment.deputyDeanList" id="record">
				<tr>
					<td><html:radio property="selectedAuthoriser" idName="record" value="personnelNumber"></html:radio></td>
					<td><bean:write name="record" property="name"/></td>
					<td><bean:write name="record" property="emailAddress"/></td>
				</tr>
			</logic:iterate>
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