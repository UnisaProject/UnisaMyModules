<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>

<fmt:setBundle basename="za.ac.unisa.lms.tools.tutorstudentgrouping.ApplicationResources"/>

<sakai:html>
<script language="javascript">
	function doAction() {
		document.tutorStudentGroupForm.action = 'tutorStudentGrouping.do?act=replaceTutor';  
		document.tutorStudentGroupForm.submit();
	}
</script>
	<html:form action="/tutorStudentGrouping">
		<html:hidden property="currentPage" value="confirmReplaceTutor"/>
		<sakai:messages/>
		<sakai:messages message="true"/>		
		<sakai:heading>
			<fmt:message key="heading.confirmReplaceTutor"/>
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
				<th><fmt:message key="column.changeTutor.groupNr"/></th>
				<th><fmt:message key="column.changeTutor.currentTutor"/></th>				
				<th><fmt:message key="column.changeTutor.newTutor"/></th>
				<th><fmt:message key="column.changeTutor.totalStudents"/></th>
			</tr>
			<logic:iterate name="tutorStudentGroupForm" property="listChangeGroupTutor" id="record" indexId="index">
				<tr>
					<td><bean:write name="record" property="tutorStudentGroup.groupNumber"/></td>
					<td><bean:write name="record" property="tutorStudentGroup.tutor.name"/></td>					
					<td><bean:write name="record" property="newTutor.person.name"/></td>
					<td><bean:write name="record" property="tutorStudentGroup.totalStudentsAllocated"/></td>					
				</tr>
			</logic:iterate>
		</sakai:group_table>
		<sakai:actions>
			<html:button property="act" onclick="javascript:disabled=true;doAction();">
					<fmt:message key="button.replaceTutor"/>
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