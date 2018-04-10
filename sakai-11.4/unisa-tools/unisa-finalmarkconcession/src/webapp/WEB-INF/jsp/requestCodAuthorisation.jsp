<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>

<fmt:setBundle basename="za.ac.unisa.lms.tools.finalmarkconcession.ApplicationResources"/>
<script language="javascript">
	function doAction() {
		document.finalMarkConcessionForm.action = 'finalMarkConcession.do?act=submitAuthRequest';  
		document.finalMarkConcessionForm.submit();
	}
</script>
<sakai:html>
	<html:form action="/finalMarkConcession">
		<html:hidden property="currentPage" value="requestCodAuthorisation"/>
		<sakai:messages/>
		<sakai:messages message="true"/>
		<sakai:heading>
			<fmt:message key="heading.requestCodAuthorisation"/>
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
				 <td><bean:write name="finalMarkConcessionForm" property="alternativeExamOpportunity.statusDesc"/></td>	
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
				<td colspan="2"><fmt:message key="prompt.cod"/></td>
			<tr>
			<logic:iterate name="codList" id="record">
				<tr>
					<td><html:radio property="selectedAuthoriser" idName="record" value="personnelNumber"></html:radio></td>
					<td><bean:write name="record" property="name"/></td>
					<td><bean:write name="record" property="emailAddress"/></td>
				</tr>
			</logic:iterate>
			<tr>
				<td colspan="2"><fmt:message key="prompt.actingCods"/></td>
			<tr>
			<logic:iterate name="finalMarkConcessionForm" property="authDepartment.actingCodList" id="record">
				<tr>
					<td><html:radio property="selectedAuthoriser" idName="record" value="personnelNumber"></html:radio></td>
					<td><bean:write name="record" property="name"/></td>
					<td><bean:write name="record" property="emailAddress"/></td>
				</tr>
			</logic:iterate>
		</sakai:group_table>	
		<sakai:instruction>
			<fmt:message key="note.requestAuthorisation.4"/>
		</sakai:instruction>
		<sakai:group_table>
			<tr>
				<td><fmt:message key="prompt.emailResponseAddress"/><fmt:message key="prompt.required"/></td>
				<td><html:text name="finalMarkConcessionForm" property="responseEmailAddress" size="40" maxlength="60"/></td>
			</tr>
		</sakai:group_table>	
		<sakai:actions>			
			<html:submit property="act" onclick="javascript:disabled=true;doAction();">
					<fmt:message key="button.request"/>
			</html:submit>
			<html:submit property="act">
					<fmt:message key="button.back"/>
			</html:submit>				
		</sakai:actions>
	</html:form>
</sakai:html>