<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>

<fmt:setBundle basename="za.ac.unisa.lms.tools.tutorstudentgrouping.ApplicationResources"/>

<sakai:html>
	<html:form action="/tutorStudentGrouping">
		<html:hidden property="currentPage" value="inputSwitchTutor"/>
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
		<sakai:group_heading>
			<fmt:message key="instr.inputSwitchTutor.enterTutorDetails"/> 
		</sakai:group_heading>	
		<sakai:group_table>				
			<tr>
				<td><b><fmt:message key="prompt.staffNumber"/></b>&nbsp;<fmt:message key="note.mandatory"/><br/></td>
				<td><html:text name="tutorStudentGroupForm" property="tutorStaffNumber" size="10" maxlength="8"></html:text></td>
		
			</tr>	
			<tr>
				<td><b><fmt:message key="prompt.networkCode"/></b>&nbsp;<fmt:message key="note.mandatory"/><br/></td>
				<td><html:text name="tutorStudentGroupForm" property="tutorNetworkCode" size="20" maxlength="20"></html:text></td>
		
			</tr>				
		</sakai:group_table>	
		<sakai:actions>
			<html:submit property="act">
					<fmt:message key="button.tutorGroups"/>
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