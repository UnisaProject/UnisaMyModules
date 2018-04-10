<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>

<fmt:setBundle basename="za.ac.unisa.lms.tools.tutorstudentgrouping.ApplicationResources"/>

<sakai:html>
	<html:form action="/tutorStudentGrouping">
		<html:hidden property="currentPage" value="inputAction"/>
		<sakai:messages/>
		<sakai:messages message="true"/>		
		<sakai:heading>
			<fmt:message key="heading.groupManagement"/>
		</sakai:heading>		
		<sakai:instruction>
			<fmt:message key="note.required"/>&nbsp;<fmt:message key="note.mandatory"/>
		</sakai:instruction>
		<sakai:group_heading>
			<fmt:message key="instr.inputAction.enterCourse"/> 
		</sakai:group_heading>	
		<sakai:group_table>	
			<tr>
				<td><fmt:message key="prompt.acadYear"/>&nbsp;<fmt:message key="note.mandatory"/></td>
				<td><html:text name="tutorStudentGroupForm" property="acadYear" size="4" maxlength="4"/></td>
			</tr>
			<tr>
				<td><fmt:message key="prompt.semester"/>&nbsp;<fmt:message key="note.mandatory"/></td>
				<td>
					<sakai:group_table>
						<logic:iterate name="tutorStudentGroupForm" property="listSemester" id="record" indexId="index">
							<tr>
								<td><html:radio property="semester" idName="record" value="code"></html:radio></td>
								<td><bean:write name="record" property="engDescription"/></td>
							</tr>
						</logic:iterate>
					</sakai:group_table>
				</td>				
			</tr>
			<tr>
				<td><fmt:message key="prompt.studyUnit"/>&nbsp;<fmt:message key="note.mandatory"/></td>
				<td><html:text name="tutorStudentGroupForm" property="inputStudyUnit" size="10" maxlength="7"/></td>
			</tr>	
			<tr>
				<td><fmt:message key="prompt.action"/>&nbsp;<fmt:message key="note.mandatory"/></td>
				<td>
					<sakai:group_table>
						<logic:iterate name="tutorStudentGroupForm" property="listAction" id="record" indexId="index">
							<tr>
								<td><html:radio property="selectedAction" idName="record" value="code"></html:radio></td>
								<td><bean:write name="record" property="engDescription"/></td>
							</tr>
						</logic:iterate>
					</sakai:group_table>
				</td>				
			</tr>		
		</sakai:group_table>	
		<sakai:actions>
			<html:submit property="act">
					<fmt:message key="button.continue"/>
			</html:submit>			
		</sakai:actions>		
	</html:form>
</sakai:html>