<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>

<fmt:setBundle basename="za.ac.unisa.lms.tools.tutorstudentgrouping.ApplicationResources"/>

<sakai:html>
	<html:form action="/tutorStudentGrouping">
		<html:hidden property="currentPage" value="confirmUploadGroup"/>
		<sakai:messages/>
		<sakai:messages message="true"/>		
		<sakai:heading>
			<fmt:message key="heading.uploadGroup"/>
		</sakai:heading>	
		<sakai:group_heading>
			<fmt:message key="instr.confirmGroup.tutorGroupInfo"/> 
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
			<tr>
				<td><fmt:message key="prompt.groupNumber"/>&nbsp;</td>			
				<td><bean:write name="newGroupNr"/></td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td><fmt:message key="prompt.numberStudentsInGroup"/>&nbsp;</td>			
				<td><bean:write name="tutorStudentGroupForm" property="validRecords"/></td>
				<td>&nbsp;</td>
			</tr>
		</sakai:group_table>	
		<sakai:actions>		
			<html:submit property="act">
					<fmt:message key="button.cancel"/>
			</html:submit>			
		</sakai:actions>		
	</html:form>
</sakai:html>