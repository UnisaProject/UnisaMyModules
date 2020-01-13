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
		document.tutorStudentGroupForm.action = 'tutorStudentGrouping.do?act=uploadGroup';  
		document.tutorStudentGroupForm.submit();
	}
</script>
	<html:form action="/tutorStudentGrouping">
		<html:hidden property="currentPage" value="verifyUploadGroup"/>
		<sakai:messages/>
		<sakai:messages message="true"/>		
		<sakai:heading>
			<fmt:message key="heading.uploadGroup"/>
		</sakai:heading>
		<sakai:instruction>
			<b><fmt:message key="instr.verifyGroup.verifyDetails"/></b>
		</sakai:instruction>		
		<sakai:instruction>
			<fmt:message key="note.required"/>&nbsp;<fmt:message key="note.mandatory"/>
		</sakai:instruction>		
		<sakai:group_heading>
			<fmt:message key="instr.verifyGroup.verifyModule"/> 
		</sakai:group_heading>
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
			<tr>
				<td><fmt:message key="prompt.tutoringMode"/>&nbsp;</td>			
				<td><bean:write name="tutorStudentGroupForm" property="tutoringMode.tutorModeDesc"/></td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td><fmt:message key="prompt.tutorName"/>&nbsp;</td>			
				<td><bean:write name="tutorStudentGroupForm" property="tutor.person.name"/></td>
				<td>&nbsp;</td>
			</tr>
		</sakai:group_table>
		<sakai:group_heading>
			<fmt:message key="instr.verifyGroup.verifyFile"/> 			
		</sakai:group_heading>
		<sakai:group_table>	
			<tr>
				<td><fmt:message key="prompt.fileName"/>&nbsp;</td>
				<td><bean:write name="tutorStudentGroupForm" property="fileName"/></td>
			</tr>
			<tr>
				<td><fmt:message key="prompt.fileSize"/>&nbsp;</td>			
				<td><bean:write name="tutorStudentGroupForm" property="fileSize"/></td>			
			</tr>
			<tr>
				<td><fmt:message key="prompt.numberOfRecs"/>&nbsp;</td>			
				<td><bean:write name="tutorStudentGroupForm" property="validRecords"/></td>			
			</tr>
			<tr>
				<td><fmt:message key="prompt.numberOfInvalidRecs"/>&nbsp;</td>			
				<td><bean:write name="tutorStudentGroupForm" property="invalidRecords"/></td>			
			</tr>
		</sakai:group_table>
		<sakai:actions>
			<html:button property="act" onclick="javascript:disabled=true;doAction();">
					<fmt:message key="button.uploadGroup"/>
			</html:button>	
			<html:submit property="act">
					<fmt:message key="button.viewInvalidRecords"/>
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