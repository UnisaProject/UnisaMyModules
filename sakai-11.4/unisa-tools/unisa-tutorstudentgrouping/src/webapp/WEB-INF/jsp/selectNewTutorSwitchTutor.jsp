<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>

<fmt:setBundle basename="za.ac.unisa.lms.tools.tutorstudentgrouping.ApplicationResources"/>

<sakai:html>
	<html:form action="/tutorStudentGrouping">
		<html:hidden property="currentPage" value="selectNewTutorSwitchTutor"/>
		<sakai:messages/>
		<sakai:messages message="true"/>		
		<sakai:heading>
			<fmt:message key="heading.switchTutor"/>
		</sakai:heading>
		<sakai:instruction>
			<b><fmt:message key="instr.switchTutor.completeDetails"/></b>
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
					<td><b><fmt:message key="prompt.tutorSwitchDetails"/></b>&nbsp;</td>
					<td><bean:write name="tutorStudentGroupForm" property="tutor.person.personnelNumber"/>&nbsp;&nbsp;&nbsp;
						<bean:write name="tutorStudentGroupForm" property="tutor.person.name"/>
					</td>			
				</tr>			
			</sakai:group_table>
		<sakai:group_heading>
			<fmt:message key="note.selectNewTutorSwitchTutor.currentGroupDetails"/>&nbsp;<bean:write name="tutorStudentGroupForm" property="tutor.person.personnelNumber"/> 
		</sakai:group_heading>	
		<sakai:instruction>
			<b><fmt:message key="instr.selectNewTutorSwitchTutor.selectGroupToSwitch"/></b>
		</sakai:instruction>			
		<sakai:group_table>
				<tr>
					<th><fmt:message key="column.tutorGroup.selectGroup"/></th>					
					<th><fmt:message key="column.tutorGroup.groupNr"/></th>
					<th><fmt:message key="column.tutorGroup.totalStudents"/></th>
					<th><fmt:message key="column.tutorGroup.tutoringMode"/></th>
					<th><fmt:message key="column.tutorGroup.allocationCriteria"/></th>
				</tr>
				<logic:iterate name="tutorStudentGroupForm" property="listTutorStudentGroup" id="record" indexId="index">
					<tr>
						<td><html:radio property="indexNrselectedTutorGroupSwitch1" value="<%=index.toString()%>"></html:radio></td>
						<td><bean:write name="record" property="groupNumber"/></td>
						<td><bean:write name="record" property="totalStudentsAllocated"/></td>
						<td><bean:write name="record" property="tutoringMode.tutorModeDesc"/></td>
						<td><bean:write name="record" property="tutoringMode.allocationCriteria"/></td>
					</tr>
				</logic:iterate>
		</sakai:group_table>	
			<sakai:group_heading>
			<fmt:message key="note.selectNewTutorSwitchTutor.availableTutorGroups"/> 
		</sakai:group_heading>	
		<sakai:instruction>
			<b><fmt:message key="instr.selectNewTutorSwitchTutor.selectTutorGroup"/></b>
		</sakai:instruction>
		<sakai:group_table>
				<tr>
					<th><fmt:message key="column.tutorGroup.selectGroup"/></th>	
					<th><fmt:message key="column.tutorGroup.persno"/></th>		
					<th><fmt:message key="column.tutorGroup.name"/></th>	
					<th><fmt:message key="column.tutorGroup.role"/></th>			
					<th><fmt:message key="column.tutorGroup.groupNr"/></th>
					<th><fmt:message key="column.tutorGroup.totalStudents"/></th>
					<th><fmt:message key="column.tutorGroup.tutoringMode"/></th>
					<th><fmt:message key="column.tutorGroup.allocationCriteria"/></th>
				</tr>
				<logic:iterate name="tutorStudentGroupForm" property="listAvailableTutorGroup" id="record" indexId="index">
					<tr>
						<td><html:radio property="indexNrselectedTutorGroupSwitch2" value="<%=index.toString()%>"></html:radio></td>
						<td><bean:write name="record" property="tutor.personnelNumber"/></td>
						<td><bean:write name="record" property="tutor.name"/></td>
						<td><bean:write name="record" property="role"/></td>
						<td><bean:write name="record" property="groupNumber"/></td>
						<td><bean:write name="record" property="totalStudentsAllocated"/></td>
						<td><bean:write name="record" property="tutoringMode.tutorModeDesc"/></td>
						<td><bean:write name="record" property="tutoringMode.allocationCriteria"/></td>
					</tr>
				</logic:iterate>
		</sakai:group_table>		
		<sakai:actions>
			<html:submit property="act">
					<fmt:message key="button.switch"/>
			</html:submit>
			<html:submit property="act">
					<fmt:message key="button.back"/>
			</html:submit>		
			<html:submit property="act">
					<fmt:message key="button.cancel"/>
			</html:submit>			
		</sakai:actions>			
	</html:form>
</sakai:html>