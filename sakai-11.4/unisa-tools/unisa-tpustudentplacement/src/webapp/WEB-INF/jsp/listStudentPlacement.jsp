<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>

<fmt:setBundle basename="za.ac.unisa.lms.tools.tpustudentplacement.ApplicationResources"/>

<sakai:html>	
	<html:form action="/studentPlacement">
		<sakai:messages/>
		<sakai:messages message="true"/>			
		<sakai:group_heading>
			<fmt:message key="heading.studentPlacement.studentInfo"/> 
		</sakai:group_heading>
		<sakai:group_table>	
			<tr>
				<td><fmt:message key="prompt.acadYear"/>/<fmt:message key="prompt.semester"/></td>
				<td><bean:write name="studentPlacementForm" property="acadYear"/></td>
			</tr>
			<tr>
				<td><fmt:message key="prompt.studentNr"/></td>
				<td><bean:write name="studentPlacementForm" property="student.number"/></td>
			</tr>	
			<tr>
				<td><fmt:message key="prompt.studentName"/></td>
				<td><bean:write name="studentPlacementForm" property="student.name"/>
					<html:submit property="action">
						<fmt:message key="button.contactDetails"/>
					</html:submit>	
				</td>
			</tr>			
			<tr>
				<td><fmt:message key="prompt.qualification"/></td>
				<td><bean:write name="studentPlacementForm" property="student.qual.description"/></td>
			</tr>
			<tr>
				<td><fmt:message key="prompt.practicalModules"/></td>
				<td><bean:write name="studentPlacementForm" property="student.practicalModules"/></td>
			</tr>					
		</sakai:group_table>
		<hr/>	
		<sakai:group_heading>
			<fmt:message key="heading.studentPlacment.modulePlacements"/> 
		</sakai:group_heading>	
		<sakai:flat_list>
			<tr >
				<th style="white-space:nowrap;align:left" rowspan="2"><fmt:message key="prompt.column.module"/></th>	
				<th style="white-space:nowrap;align:left" rowspan="2"><fmt:message key="prompt.column.school"/></th>					
				<th style="white-space:nowrap;align:center" colspan="2"><fmt:message key="prompt.column.supervisor"/></th>
				<th style="white-space:nowrap;align:center" colspan="2"><fmt:message key="prompt.column.duration"/></th>	
				<th style="white-space:nowrap;align:left" rowspan="2" ><fmt:message key="prompt.fulltimeStu"/></th>
				<th style="white-space:nowrap;align:left" rowspan="2"><fmt:message key="prompt.column.mentor"/></th>
				<th style="white-space:nowrap;align:left" rowspan="2"><fmt:message key="prompt.column.weeks"/></th>
				<th style="white-space:nowrap;align:left" rowspan="2"><fmt:message key="prompt.column.evalMark"/></th>
			</tr>
			<tr >	
				<th style="white-space:nowrap;align:left"><fmt:message key="prompt.column.name"/></th>
				<th style="white-space:nowrap;align:left"><fmt:message key="prompt.column.contactNumber"/></th>	
				<th style="white-space:nowrap;align:left"><fmt:message key="prompt.column.startDate"/></th>
				<th style="white-space:nowrap;align:left"><fmt:message key="prompt.column.endDate"/></th>	
			</tr>
			<logic:iterate name="studentPlacementForm" property="listStudentPlacement" id="rec" indexId="index">
				<tr>
					<td>	
						<html:multibox property="indexNrPlacementSelected"><bean:write name="index"/></html:multibox>				
						<bean:write name="rec" property="module"/>
					</td>
					<td><bean:write name="rec" property="schoolDesc"/></td>									
					<td><bean:write name="rec" property="supervisorName"/></td>
					<td><bean:write name="rec" property="supervisorContactNumber"/></td>
					<td><bean:write name="rec" property="startDate"/></td>
					<td><bean:write name="rec" property="endDate"/></td>
					<td><bean:write name="rec" property="stuFullTime"/>
					<td><bean:write name="rec" property="mentorName"/></td>
					<td><bean:write name="rec" property="numberOfWeeks"/></td>
					<td><bean:write name="rec" property="evaluationMark"/></td>						
				</tr>
			</logic:iterate>
		</sakai:flat_list>	
		<sakai:actions>
			<html:submit property="action">
					<fmt:message key="button.placeModule"/>
			</html:submit>
			<logic:notEmpty name="studentPlacementForm" property="listStudentPlacement">	
				<html:submit property="action">
						<fmt:message key="button.edit"/>
				</html:submit>	
				<html:submit property="action">
						<fmt:message key="button.remove"/>
				</html:submit>							
				<html:submit property="action">
						<fmt:message key="button.correspondence"/>
				</html:submit>	
			</logic:notEmpty>
			<html:submit property="action">
					<fmt:message key="button.listLogs"/>
			</html:submit>	
			<html:submit property="action">
					<fmt:message key="button.back"/>
			</html:submit>			
		</sakai:actions>				
	</html:form>
</sakai:html>