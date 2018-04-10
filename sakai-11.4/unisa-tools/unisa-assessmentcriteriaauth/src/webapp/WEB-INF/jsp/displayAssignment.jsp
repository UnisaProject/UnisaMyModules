<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>

<fmt:setBundle basename="za.ac.unisa.lms.tools.assessmentcriteriaauth.ApplicationResources"/>

<sakai:html>
	<html:form action="/assessmentCriteriaAuth">
		<html:hidden property="currentPage" value="assignment"/>
		<sakai:messages/>
		<sakai:messages message="true"/>
		<sakai:heading>
			<fmt:message key="heading.assignment"/>
		</sakai:heading>
		<sakai:group_table>	
			<tr>
				<td><fmt:message key="page.studyUnit"/>&nbsp;</td>
				<td><bean:write name="assessmentCriteriaAuthForm" property="selectedCourse.courseCode"/></td>
				<td><bean:write name="assessmentCriteriaAuthForm" property="studyUnit.description"/></td>
			</tr>
			<tr>
				<td><fmt:message key="page.yearSemester"/>&nbsp;</td>			
				<td><bean:write name="assessmentCriteriaAuthForm" property="selectedCourse.year"/>/<bean:write name="assessmentCriteriaAuthForm" property="selectedCourse.semesterType"/></td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td><fmt:message key="page.assignmentNumber"/>&nbsp;</td>
				<td><bean:write name="assessmentCriteriaAuthForm" property="assignment.number"/></td>
				<td>&nbsp;</td>
			</tr>			
		</sakai:group_table>	
		<hr/>
		<sakai:group_table>
			<tr>
				<th colspan="2">
					<sakai:group_heading>
						<fmt:message key="par.heading.assignmentSectionA"/>
					</sakai:group_heading>
				</th>
			</tr>
			<!-- <tr>
				<th><fmt:message key="page.subsidyAssignment"/>&nbsp;</th>
				<td><bean:write name="assessmentCriteriaAuthForm" property="assignment.subsidyAssignment"/></td>
			</tr> -->
			<logic:notEmpty name="assessmentCriteriaAuthForm" property="assignment.group">
				<tr>
					<th><fmt:message key="page.assignmentGroup"/>&nbsp;</th>
					<td><bean:write name="assessmentCriteriaAuthForm" property="assignment.group"/></td>
				</tr>
			</logic:notEmpty>
			<tr>
				<th><fmt:message key="page.assignmentFormat"/>&nbsp;</th>
				<td><bean:write name="assessmentCriteriaAuthForm" property="assignment.format"/></td>
			</tr>
			<tr>
					<th><fmt:message key="page.uniqueNumber"/>&nbsp;</th>
					<td><bean:write name="assessmentCriteriaAuthForm" property="assignment.uniqueNumber"/></td>
			</tr>					
			<tr>
				<th><fmt:message key="page.dueDate"/>&nbsp;</th>
				<td><bean:write name="assessmentCriteriaAuthForm" property="assignment.dueDate"/></td>
			</tr>			
			<tr>
				<th><fmt:message key="page.assignmentType"/>&nbsp;</th>
				<td><bean:write name="assessmentCriteriaAuthForm" property="assignment.type"/></td>
			</tr>
			<logic:equal name="assessmentCriteriaAuthForm" property="assFormatCode" value="A">
				<tr>
					<th><fmt:message key="page.numberOfQuestions"/>&nbsp;</th>
					<td><bean:write name="assessmentCriteriaAuthForm" property="assignment.numberQuestions"/></td>
				</tr>
				<tr>
					<th><fmt:message key="page.negativeMarkingFactor"/>&nbsp;</th>
					<logic:equal  name="assessmentCriteriaAuthForm" property="assignment.negativeMarkingFactor" value="0">
						<td><fmt:message key="page.no"/>&nbsp;</td>
					</logic:equal>
					<logic:notEqual  name="assessmentCriteriaAuthForm" property="assignment.negativeMarkingFactor" value="0">
						<td><fmt:message key="page.negativeMarkingFactora"/>
							<bean:write name="assessmentCriteriaAuthForm" property="assignment.negativeMarkingFactor"/>
							<fmt:message key="page.negativeMarkingFactorb"/>
						</td>
					</logic:notEqual>
				</tr>
			</logic:equal>	
	</sakai:group_table>
	<sakai:group_table>			
		<tr>
			<th colspan="2">
				<sakai:group_heading>
					<fmt:message key="par.heading.assignmentSectionB"/>
				</sakai:group_heading>
			</th>
		</tr>
		<tr>
			<th><fmt:message key="page.assignmentWeight"/>&nbsp;</th>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<td><fmt:message key="page.normalWeight"/>&nbsp;</td>
			<td><bean:write name="assessmentCriteriaAuthForm" property="assignment.normalWeight"/></td>
		</tr>
		<tr>
			<td><fmt:message key="page.repeatWeight"/>&nbsp;</td>
			<td><bean:write name="assessmentCriteriaAuthForm" property="assignment.repeatWeight"/></td>
		</tr>
		<tr>
			<td><fmt:message key="page.aegrotatWeight"/>&nbsp;</td>
			<td><bean:write name="assessmentCriteriaAuthForm" property="assignment.aegrotatWeight"/></td>
		</tr>
		<tr>
			<th><fmt:message key="page.assignmentOptionality"/>&nbsp;</th>
			<td><bean:write name="assessmentCriteriaAuthForm" property="assignment.optionality"/></td>
		</tr>
	</sakai:group_table>				
	<logic:equal name="assessmentCriteriaAuthForm" property="assignment.onscreenMarkFlag" value="Y">
		<sakai:group_table>
			<tr>
				<th colspan="2">
					<sakai:group_heading>
						<fmt:message key="par.heading.assignmentSectionC"/>
					</sakai:group_heading>
				</th>
			</tr>
			<tr>
				<th><fmt:message key="page.assignment.onscreenmarking.pfOpenDate"/>&nbsp;</th>
				<td><bean:write name="assessmentCriteriaAuthForm" property="assignment.pfOpenDate"/></td>
			</tr>
			<logic:notEmpty name="assessmentCriteriaAuthForm" property="assignment.finalSubmissionDate">
				<tr>
					<th><fmt:message key="page.assignment.onscreenmarking.finalSubmissionDate"/>&nbsp;</th>
					<td><bean:write name="assessmentCriteriaAuthForm" property="assignment.finalSubmissionDate"/></td>
				</tr>
			</logic:notEmpty>	
			<bean:define id="type" name="assessmentCriteriaAuthForm" property="assTypeCode"/>	
				<%
				String assType = type.toString().trim();
				if (assType.equalsIgnoreCase("PF") || assType.equalsIgnoreCase("PJ") || assType.equalsIgnoreCase("PC")){%>
				<tr>
					<th><fmt:message key="page.assignment.onscreenmarking.pfReleaseFlag"/>&nbsp;</th>				
					<td>
						<logic:equal name="assessmentCriteriaAuthForm" property="assignment.releaseFlag" value="Y">Yes</logic:equal>
						<logic:notEqual name="assessmentCriteriaAuthForm" property="assignment.releaseFlag" value="Y">No</logic:notEqual>
					</td>
				</tr>
				<%} %>
			<logic:notEqual name="assessmentCriteriaAuthForm" property="assignment.releaseFlag" value="N">
				<tr>
					<th><fmt:message key="page.assignment.onscreenmarking.fileReleaseDate"/>&nbsp;</th>
					<td><bean:write name="assessmentCriteriaAuthForm" property="assignment.fileReleaseDate"/></td>
				</tr>
			</logic:notEqual>	
			<tr>
				<th><fmt:message key="page.assignment.onscreenmarking.fileFormats"/>&nbsp;</th>
				<td>
					<logic:iterate name="assessmentCriteriaAuthForm" property="assignment.listFormat" id="record" indexId="index">
						<bean:write name="record" property="description"/> (<bean:write name="record" property="extention"/>)<br/>														
					</logic:iterate>
				</td>
			</tr>
			<tr>
				<th><fmt:message key="page.assignment.onscreenmarking.maxFileSize"/>&nbsp;</th>
				<td><bean:write name="assessmentCriteriaAuthForm" property="assignment.maxFileSize"/></td>
			</tr>
			<tr>
				<th><fmt:message key="page.assignment.onscreenmarking.stuSpecifyLang"/>&nbsp;</th>				
				<td>
					<logic:equal name="assessmentCriteriaAuthForm" property="assignment.studentSpecifyLang" value="Y">Yes</logic:equal>
					<logic:notEqual name="assessmentCriteriaAuthForm" property="assignment.studentSpecifyLang" value="Y">No</logic:notEqual>
				</td>				
			</tr>
			<logic:equal name="assessmentCriteriaAuthForm" property="assignment.studentSpecifyLang" value="Y">
				<tr>
					<th><fmt:message key="page.assignment.onscreenmarking.Languages"/>&nbsp;</th>
					<td>
						<logic:iterate name="assessmentCriteriaAuthForm" property="assignment.listLanguage" id="record" indexId="index">
							<bean:write name="record"/><br/>														
						</logic:iterate>
					</td>
				</tr>
			</logic:equal>	
		</sakai:group_table>
		<logic:notEmpty name="assessmentCriteriaAuthForm" property="assignment.listMarkers">
				<sakai:group_table>
				<tr>
					<th colspan="2">
						<sakai:group_heading>
							<fmt:message key="par.heading.assignmentSectionD"/>
						</sakai:group_heading>
					</th>
				</tr>
				</sakai:group_table>			
					<sakai:flat_list>
						<tr>
							<th><fmt:message key="page.marker.tableHeading.personnelNr"/></th>
							<th><fmt:message key="page.marker.tableHeading.name"/></th>
							<th><fmt:message key="page.marker.tableHeading.department"/></th>
							<th><fmt:message key="page.marker.tableHeading.percentageToMark"/></th>
							<th><fmt:message key="page.marker.tableHeading.status"/></th>	
							<logic:equal name="assessmentCriteriaAuthForm" property="assignment.studentSpecifyLang" value="Y">
								<th><fmt:message key="page.marker.tableHeading.markingLanguage"/></th>
							</logic:equal>	
										
						</tr>
						<logic:iterate name="assessmentCriteriaAuthForm" property="assignment.listMarkers" id="record" indexId="index">
							<tr>						
								<td><bean:write name="record" property="person.personnelNumber"/></td>
								<td><bean:write name="record" property="person.name"/></td>
								<td><bean:write name="record" property="departmentDesc"/></td>
								<td><bean:write name="record" property="markPercentage"/></td>
								<td><bean:write name="record" property="status"/></td>
								<logic:equal name="assessmentCriteriaAuthForm" property="assignment.studentSpecifyLang" value="Y">
									<td>
										<logic:iterate name="assessmentCriteriaAuthForm" property='<%="assignment.listMarkers[" + index.toString() + "].listMarkLanguages"%>' id="rec" indexId="ind">
											<bean:write name="rec"/><br/>
										</logic:iterate>
									</td>
								</logic:equal>
							</tr>
						</logic:iterate>	
					</sakai:flat_list>	
			</logic:notEmpty>					
	</logic:equal>			
		<sakai:actions>
			<html:submit property="action">
				<fmt:message key="button.back"/>
			</html:submit>
		</sakai:actions>		
	</html:form>
</sakai:html>