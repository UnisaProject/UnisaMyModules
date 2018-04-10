<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ page import="java.util.*" %>

<fmt:setBundle basename="za.ac.unisa.lms.tools.tutorstudentgrouping.ApplicationResources"/>

<sakai:html>
	<html:form action="/tutorStudentGrouping" enctype="multipart/form-data">
		<html:hidden property="currentPage" value="inputUploadGroup"/>
		<sakai:messages/>
		<sakai:messages message="true"/>		
		<sakai:heading>
			<fmt:message key="heading.uploadGroup"/>
		</sakai:heading>
		<sakai:instruction>
			<b><fmt:message key="instr.inputGroup.completeDetails"/></b>
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
				<td><b><fmt:message key="prompt.upload.tutorMode"/></b>&nbsp;<fmt:message key="note.mandatory"/><br/>
				    <sakai:instruction><fmt:message key="note.uploadGroup.tutorMode"/></sakai:instruction>
					<sakai:group_table>
						<logic:iterate name="tutorStudentGroupForm" property="listAvailableTutorMode" id="record" indexId="index">
							<tr>
								<td><html:radio property="selectedTutoringMode" idName="record" value="tutorMode"></html:radio></td>
								<td><bean:write name="record" property="tutorModeDesc"/></td>
							</tr>
						</logic:iterate>
					</sakai:group_table>
				</td>				
			</tr>
			<tr>
				<td><b><fmt:message key="prompt.upload.tutorList"/></b>&nbsp;<fmt:message key="note.mandatory"/><br/>
				    <!--<sakai:instruction><fmt:message key="note.uploadGroup.tutorList"/></sakai:instruction>-->
					<sakai:flat_list>
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
					</sakai:flat_list>
				</td>				
			</tr>
			<tr>
				<td><b><fmt:message key="note.uploadGroup.file1"/></b>&nbsp;<fmt:message key="note.mandatory"/><br/>
				 	<sakai:instruction><fmt:message key="note.uploadGroup.file2"/></sakai:instruction><br/>
					<fmt:message key="prompt.upload.file"/>&nbsp;&nbsp;<html:file name="tutorStudentGroupForm"  property="theFile"/><br/>
				</td>
			</tr>			
		</sakai:group_table>	
		<sakai:actions>
			<html:submit property="act">
					<fmt:message key="button.uploadFile"/>
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