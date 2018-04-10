<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>

<fmt:setBundle basename="za.ac.unisa.lms.tools.tutorstudentgrouping.ApplicationResources"/>
<script language="javascript">
	function doAction() {
		document.tutorStudentGroupForm.action = 'tutorStudentGrouping.do?act=reassignStudent';  
		document.tutorStudentGroupForm.submit();
	}
</script>
<sakai:html>
	<html:form action="/tutorStudentGrouping">
		<html:hidden property="currentPage" value="selectNewGroupReassignStudent"/>
		<sakai:messages/>
		<sakai:messages message="true"/>		
		<sakai:heading>
			<fmt:message key="heading.reassignStudent"/>
		</sakai:heading>
		<sakai:instruction>
			<fmt:message key="instr.selectNewGroupReassignStudent.completeDetails"/>
		</sakai:instruction>			
		<sakai:instruction>
			<fmt:message key="note.required"/>&nbsp;<fmt:message key="note.mandatory"/>
		</sakai:instruction>		
		<sakai:group_table>	
			<tr>
				<td><fmt:message key="prompt.studyUnit"/>&nbsp;</td>
				<td><bean:write name="tutorStudentGroupForm" property="studyUnit.code"/></td>
				<td><bean:write name="tutorStudentGroupForm" property="studyUnit.engLongDesc"/></td>
			</tr>
			<tr>
				<td><fmt:message key="prompt.yearSemester"/>&nbsp;</td>			
				<td><bean:write name="tutorStudentGroupForm" property="acadYear"/>/<bean:write name="tutorStudentGroupForm" property="semesterType"/></td>
				<td>&nbsp;</td>
			</tr>
		</sakai:group_table>		
		<hr/>
		<sakai:group_table>				
				<tr>
					<td><b><fmt:message key="prompt.studentDetials"/></b>&nbsp;</td>
					<td><bean:write name="tutorStudentGroupForm" property="student.number"/>&nbsp;&nbsp;&nbsp;
						<bean:write name="tutorStudentGroupForm" property="student.printName"/>&nbsp;
						(Birth date:&nbsp;<bean:write name="tutorStudentGroupForm" property="student.birthDate"/>)<br/>
						<bean:write name="studentAddress"/>
					</td>			
				</tr>			
			</sakai:group_table>
		<sakai:group_heading>
			<fmt:message key="note.selectNewGroupReassignStudent.currentGroupDetails"/> 
		</sakai:group_heading>	
		<logic:notEqual name="tutorStudentGroupForm" property="studentGroup.groupNumber" value="0">
			<sakai:group_table>				
				<tr>
					<td><b><fmt:message key="prompt.tutoringMode"/></b>&nbsp;</td>
					<td><bean:write name="tutorStudentGroupForm" property="studentGroup.tutoringMode.tutorModeDesc"/></td>
				</tr>	
				<tr>
					<td><b><fmt:message key="prompt.groupNumber"/></b>&nbsp;</td>
					<td><bean:write name="tutorStudentGroupForm" property="studentGroup.groupNumber"/></td>
				</tr>	
				<tr>
					<td><b><fmt:message key="prompt.tutor"/></b>&nbsp;</td>
					<td><bean:write name="tutorStudentGroupForm" property="studentGroup.tutor.name"/></td>
				</tr>	
				<tr>
					<td><b><fmt:message key="prompt.tutorStudentRatio"/></b>&nbsp;</td>
					<td><bean:write name="tutorStudentGroupForm" property="studentGroup.tutoringMode.tutorStuRatio"/></td>
				</tr>			
			</sakai:group_table>
		</logic:notEqual>	
		<logic:equal name="tutorStudentGroupForm" property="studentGroup.groupNumber" value="0">
			<sakai:group_table>		
				<tr>
						<td><fmt:message key="prompt.studentNotAssignToGroup"/>&nbsp;</td>
				</tr>
			</sakai:group_table>	
		</logic:equal>
		<sakai:group_heading>
			<fmt:message key="note.selectNewGroupReassignStudent.availableGroups"/> 
		</sakai:group_heading>		
		<sakai:instruction>
			<fmt:message key="instr.selectNewGroupReassignStudent.markGroup"/>
		</sakai:instruction>
			<sakai:group_table>
				<tr>					
					<th><fmt:message key="column.tutorGroup.select"/></th>
					<th><fmt:message key="column.tutorGroup.tutoringMode"/></th>
					<th><fmt:message key="column.tutorGroup.groupNr"/></th>
					<th><fmt:message key="column.tutorGroup.persno"/></th>
					<th><fmt:message key="column.tutorGroup.name"/></th>
					<th><fmt:message key="column.tutorGroup.role"/></th>
					<th><fmt:message key="column.tutorGroup.totalStudents"/></th>
					
				</tr>
				<logic:iterate name="tutorStudentGroupForm" property="listAvailableTutorGroup" id="record" indexId="index">
					<tr>
						<td><html:radio property="selectedGroup" idName="record" value="groupNumber"></html:radio></td>
						<td><bean:write name="record" property="tutoringMode.tutorModeDesc"/></td>
						<td><bean:write name="record" property="groupNumber"/></td>
						<td><bean:write name="record" property="tutor.personnelNumber"/></td>
						<td><bean:write name="record" property="tutor.name"/></td>
						<td><bean:write name="record" property="role"/></td>
						<td><bean:write name="record" property="totalStudentsAllocated"/></td>
						
					</tr>
				</logic:iterate>
			</sakai:group_table>
		<sakai:actions>
			<html:button property="act" onclick="javascript:disabled=true;doAction();">
					<fmt:message key="button.reassignStudent"/>
			</html:button>
			<html:submit property="act">
					<fmt:message key="button.back"/>
			</html:submit>		
			<html:submit property="act">
					<fmt:message key="button.cancel"/>
			</html:submit>			
		</sakai:actions>		
	</html:form>
</sakai:html>