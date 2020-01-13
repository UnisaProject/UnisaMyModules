<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>

<fmt:setBundle basename="za.ac.unisa.lms.tools.tutorstudentgrouping.ApplicationResources"/>

<sakai:html>
	<html:form action="/tutorStudentGrouping">
		<html:hidden property="currentPage" value="selectNewTutorReplaceTutor"/>
		<sakai:messages/>
		<sakai:messages message="true"/>		
		<sakai:heading>
			<fmt:message key="heading.replaceTutor"/>
		</sakai:heading>
		<sakai:instruction>
			<b><fmt:message key="instr.replaceTutor.completeDetails"/></b>
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
					<td><b><fmt:message key="prompt.tutorReplaceDetails"/></b>&nbsp;</td>
					<td><bean:write name="tutorStudentGroupForm" property="tutor.person.personnelNumber"/>&nbsp;&nbsp;&nbsp;
						<bean:write name="tutorStudentGroupForm" property="tutor.person.name"/>
					</td>			
				</tr>			
			</sakai:group_table>
		<sakai:group_heading>
			<fmt:message key="note.selectNewTutorReplaceTutor.currentGroupDetails"/>&nbsp;<bean:write name="tutorStudentGroupForm" property="tutor.person.personnelNumber"/>
		</sakai:group_heading>	
		<sakai:instruction>
			<b><fmt:message key="instr.selectNewTutorReplaceTutor.selectGroupsToAssign"/></b>
		</sakai:instruction>			
		<sakai:group_table>
				<tr>
					<th><fmt:message key="column.tutorGroup.replace"/></th>					
					<th><fmt:message key="column.tutorGroup.groupNr"/></th>
					<th><fmt:message key="column.tutorGroup.totalStudents"/></th>
					<th><fmt:message key="column.tutorGroup.tutoringMode"/></th>
					<th><fmt:message key="column.tutorGroup.allocationCriteria"/></th>
				</tr>
				<logic:iterate name="tutorStudentGroupForm" property="listTutorStudentGroup" id="record" indexId="index">
					<tr>
						<td><html:multibox property="indexNrSelectedGroups"><bean:write name="index"/></html:multibox></td>
						<td><bean:write name="record" property="groupNumber"/></td>
						<td><bean:write name="record" property="totalStudentsAllocated"/></td>
						<td><bean:write name="record" property="tutoringMode.tutorModeDesc"/></td>
						<td><bean:write name="record" property="tutoringMode.allocationCriteria"/></td>
					</tr>
				</logic:iterate>
		</sakai:group_table>	
		<sakai:group_heading>
			<fmt:message key="note.selectNewTutorReplaceTutor.availableTutors"/> 
		</sakai:group_heading>	
		<sakai:instruction>
			<b><fmt:message key="instr.selectNewTutorReplaceTutor.selectTutor"/></b>
		</sakai:instruction>
		<sakai:group_table>
			<tr>
				<th><fmt:message key="column.tutor.select"/></th>
				<th><fmt:message key="column.tutor.persno"/></th>
				<th><fmt:message key="column.tutor.name"/></th>	
				<th><fmt:message key="column.tutor.emailAddress"/></th>			
				<th><fmt:message key="column.tutor.role"/></th>
				<th><fmt:message key="column.tutor.groups"/></th>
				<th><fmt:message key="column.tutor.totalStudents"/></th>
				<th><fmt:message key="column.tutor.tutoringMode"/></th>
			</tr>
			<logic:iterate name="tutorStudentGroupForm" property="listTutor" id="record" indexId="index">
				<tr>
					<td><html:radio property="selectedTutor" idName="record" value="person.personnelNumber"></html:radio></td>
					<td><bean:write name="record" property="person.personnelNumber"/></td>
					<td><bean:write name="record" property="person.name"/></td>		
					<td><bean:write name="record" property="person.emailAddress"/></td>			
					<td><bean:write name="record" property="role"/></td>
					<td><bean:write name="record" property="numberGroupsAllocated"/></td>
					<td><bean:write name="record" property="totalStudentsAllocated"/></td>
					<td><bean:write name="record" property="tutorModeDesc"/></td>
				</tr>
			</logic:iterate>
		</sakai:group_table>
		<sakai:actions>
			<html:submit property="act">
					<fmt:message key="button.replace"/>
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