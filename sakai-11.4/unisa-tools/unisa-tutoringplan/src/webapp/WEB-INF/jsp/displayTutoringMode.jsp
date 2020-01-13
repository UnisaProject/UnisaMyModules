<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>

<fmt:setBundle basename="za.ac.unisa.lms.tools.tutoringplan.ApplicationResources"/>

<sakai:html>
	<html:form action="/tutoringPlan">
		<html:hidden property="currentPage" value="displayTutoringMode"/>
		<sakai:messages/>
		<sakai:messages message="true"/>		
		<sakai:heading>
			<fmt:message key="heading.captureTutoringInfo"/>
		</sakai:heading>		
		<sakai:instruction>
			<fmt:message key="instr.displayTutoringMode.completeDetails"/>&nbsp;<fmt:message key="note.mandatory"/>
		</sakai:instruction>		
		<sakai:instruction>
			<fmt:message key="note.required"/>&nbsp;<fmt:message key="note.mandatory"/>
		</sakai:instruction>		
		<sakai:group_table>	
			<tr>
				<td><fmt:message key="prompt.studyUnit"/>&nbsp;</td>
				<td><bean:write name="tutoringPlanForm" property="studyUnit.code"/></td>
				<td><bean:write name="tutoringPlanForm" property="studyUnit.engLongDesc"/></td>
			</tr>
			<tr>
				<td><fmt:message key="prompt.yearSemester"/>&nbsp;</td>			
				<td><bean:write name="tutoringPlanForm" property="acadYear"/>/<bean:write name="tutoringPlanForm" property="semesterType"/></td>
				<td>&nbsp;</td>
			</tr>
		</sakai:group_table>		
		<hr/>	
		<b><fmt:message key="prompt.tutoringMode"/>&nbsp;<fmt:message key="note.mandatory"/></b><br/>
		<sakai:instruction>
			<fmt:message key="note.displayTutoringMode.tutoringMode"/>&nbsp;<fmt:message key="note.mandatory"/>
		</sakai:instruction>	
		<sakai:group_table>
			<logic:iterate name="tutoringPlanForm" property="listTutoringMode" id="record" indexId="index">
				<tr>
					<logic:equal name="tutoringPlanForm" property="editMode" value="edit">
						<td><html:radio property="tutoringMode.tutorMode" idName="record" value="code" disabled="true"></html:radio></td>
						<td style="color:grey"><bean:write name="record" property="engDescription"/></td>
					</logic:equal>
					<logic:notEqual name="tutoringPlanForm" property="editMode" value="edit">
						<td><html:radio property="tutoringMode.tutorMode" idName="record" value="code"></html:radio></td>
						<td><bean:write name="record" property="engDescription"/></td>
					</logic:notEqual>
				</tr>
			</logic:iterate>
		</sakai:group_table>
		<b><fmt:message key="prompt.groupingMethod"/>&nbsp;<fmt:message key="note.mandatory"/></b><br/>
		<sakai:instruction>
			<fmt:message key="note.displayTutoringMode.groupingMethod"/>&nbsp;<fmt:message key="note.mandatory"/>
		</sakai:instruction>	
		<sakai:group_table>
			<logic:iterate name="tutoringPlanForm" property="listGroupingMethod" id="record" indexId="index">
				<tr>
					<td><html:radio property="tutoringMode.groupChoice" idName="record" value="code"></html:radio></td>
					<td><bean:write name="record" property="engDescription"/></td>
				</tr>
			</logic:iterate>
		</sakai:group_table>
		<b><fmt:message key="prompt.tutStuRatio"/>&nbsp;<fmt:message key="note.mandatory"/></b>&nbsp;&nbsp;<html:text  name="tutoringPlanForm" property="tutoringMode.tutorStuRatio" size="6" maxlength="4"></html:text><br/><br/>
		<b><fmt:message key="prompt.maxGroupsPerTutor"/>&nbsp;<fmt:message key="note.mandatory"/></b>&nbsp;&nbsp;<html:text  name="tutoringPlanForm" property="tutoringMode.maxGroupsPerTutor" size="6" maxlength="4"></html:text><br/><br/>
		<b><fmt:message key="prompt.inclLectStaffAsTutor"/>&nbsp;<fmt:message key="note.mandatory"/></b><br/>
		<sakai:instruction>
			<fmt:message key="note.displayTutoringMode.inclLectStaffAsTutor"/>&nbsp;<fmt:message key="note.mandatory"/>
		</sakai:instruction>			
		<sakai:group_table>
			<tr>
				<td>
					<logic:iterate name="tutoringPlanForm" property="listYesNo" id="record" indexId="index">
						<html:radio property="tutoringMode.includeLectAsTutors" idName="record" value="code"></html:radio>
						<bean:write name="record" property="engDescription"/>				
					</logic:iterate>	
				</td>
			</tr>		
		</sakai:group_table>
		<b><fmt:message key="prompt.indicateTutorContactDetails"/>&nbsp;<fmt:message key="note.mandatory"/></b><br/>			
		<sakai:group_table>
			<logic:iterate name="tutoringPlanForm" property="listContactDetail" id="record" indexId="index">
				<tr>
					<logic:equal name="record" property="code" value="CELLNR">
						<td><html:radio property="tutoringMode.tutorContact" idName="record" value="code" disabled="true"></html:radio></td>
						<td style="color:grey"><bean:write name="record" property="engDescription"/></td>
					</logic:equal>	
					<logic:notEqual name="record" property="code" value="CELLNR">
						<td><html:radio property="tutoringMode.tutorContact" idName="record" value="code"></html:radio></td>
						<td><bean:write name="record" property="engDescription"/></td>
					</logic:notEqual>	
				</tr>
			</logic:iterate>
		</sakai:group_table>
		<b><fmt:message key="prompt.allocationCriteria"/>&nbsp;<fmt:message key="note.mandatory"/></b><br/>			
		<sakai:group_table>
			<logic:iterate name="tutoringPlanForm" property="listAllocCriteria" id="record" indexId="index">
				<tr>				
						<logic:equal name="record" property="code" value="GEOGRAPH">
							<td><html:radio property="tutoringMode.allocationCriteria" idName="record" value="code" disabled="true"></html:radio></td>
							<td style="color:grey"><bean:write name="record" property="engDescription"/></td>
						</logic:equal>
						<logic:notEqual name="record" property="code" value="GEOGRAPH">
							<td><html:radio property="tutoringMode.allocationCriteria" idName="record" value="code"></html:radio></td>
							<td><bean:write name="record" property="engDescription"/></td>
						</logic:notEqual>
				</tr>
			</logic:iterate>
		</sakai:group_table>
		<sakai:actions>
			<logic:equal name="tutoringPlanForm" property="updateAllowed" value="true">
				<html:submit property="action">
						<fmt:message key="button.submit"/>
				</html:submit>
			</logic:equal>
			<html:submit property="action">
					<fmt:message key="button.back"/>
			</html:submit>		
			<html:submit property="action">
					<fmt:message key="button.cancel"/>
			</html:submit>			
		</sakai:actions>		
	</html:form>
</sakai:html>