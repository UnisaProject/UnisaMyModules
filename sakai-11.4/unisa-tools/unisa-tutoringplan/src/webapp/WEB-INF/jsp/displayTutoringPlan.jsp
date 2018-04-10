<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>

<fmt:setBundle basename="za.ac.unisa.lms.tools.tutoringplan.ApplicationResources"/>

<sakai:html>
	<html:form action="/tutoringPlan">
		<html:hidden property="currentPage" value="displayTutoringPlan"/>
		<sakai:messages/>
		<sakai:messages message="true"/>		
		<sakai:heading>
			<fmt:message key="heading.captureTutoringInfo"/>
		</sakai:heading>				
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
		<sakai:heading>
			<fmt:message key="heading.displayTutoringPlan.tutoringModeList"/>
		</sakai:heading>	
		<sakai:instruction>
			<fmt:message key="instr.displayTutoringPlan.tutoringModeList"/>&nbsp;<fmt:message key="note.mandatory"/>
		</sakai:instruction>
		<sakai:flat_list>
			<tr>
				<th colspan="6" align="center"><fmt:message key="column.tutorMode.general"/></th>
				<logic:equal name="tutoringPlanForm" property="updateAllowed"  value="true">
					<th rowspan="2"><fmt:message key="column.tutorMode.remove"/></th>
				</logic:equal>
			</tr>
			<tr>
				<th><fmt:message key="column.tutorMode.tutoringMode"/></th>
				<th><fmt:message key="column.tutorMode.groupingMethod"/></th>
				<th><fmt:message key="column.tutorMode.tutorStudentRatio"/></th>				
				<th><fmt:message key="column.tutorMode.maxGroups"/></th>
				<th><fmt:message key="column.tutorMode.includeLect"/></th>
				<th><fmt:message key="column.tutorMode.allocationCriteria"/></th>
				
			</tr>
			<logic:iterate name="tutoringPlanForm" property="tutoringPlan.listTutoringMode" id="record" indexId="index">
				<tr>
					<td><logic:equal  name="tutoringPlanForm"  property="updateAllowed" value="true">
							<html:link href="tutoringPlan.do?action=editTutoringMode" paramName="record" paramProperty="tutorMode" paramId="selectTutoringMode">                                   							
								<fmt:message key="prompt.linkEditText"/>
							</html:link>					
						</logic:equal>
						<logic:notEqual  name="tutoringPlanForm"  property="updateAllowed" value="true">
							<html:link href="tutoringPlan.do?action=displayTutoringMode" paramName="record" paramProperty="tutorMode" paramId="selectTutoringMode">                                   							
								<fmt:message key="prompt.linkViewText"/>
							</html:link>
						</logic:notEqual>
						<bean:write name="record" property="tutorModeDesc"/>	
					</td>
					<td><bean:write name="record" property="groupChoice"/></td>					
					<td><bean:write name="record" property="tutorStuRatio"/></td>
					<td><bean:write name="record" property="maxGroupsPerTutor"/></td>
					<td><bean:write name="record" property="includeLectAsTutors"/></td>
					<td><bean:write name="record" property="allocationCriteria"/></td>
					<logic:equal name="tutoringPlanForm"  property="updateAllowed"  value="true">						
						<td><html:multibox property="indexSelectedRemoveTutorMode"><bean:write name="index"/></html:multibox></td>
					</logic:equal>
				</tr>
			</logic:iterate>
		</sakai:flat_list>	
		<sakai:actions>
			<logic:equal name="tutoringPlanForm" property="updateAllowed" value="true">
				<html:submit property="action">
						<fmt:message key="button.addTutorMode"/>
				</html:submit>
				<html:submit property="action">
						<fmt:message key="button.removeTutorMode"/>
				</html:submit>
			</logic:equal>
			<html:submit property="action">
					<fmt:message key="button.auditTrail"/>
			</html:submit>
			<html:submit property="action">
					<fmt:message key="button.back"/>
			</html:submit>		
			<html:submit property="action">
					<fmt:message key="button.cancel"/>
			</html:submit>			
		</sakai:actions>		
	</html:form>
</sakai:html>