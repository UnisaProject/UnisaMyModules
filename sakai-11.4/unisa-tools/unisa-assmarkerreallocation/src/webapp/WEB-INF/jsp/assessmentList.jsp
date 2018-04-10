<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>

<fmt:setBundle basename="za.ac.unisa.lms.tools.assmarkerreallocation.ApplicationResources"/>

<sakai:html>
	<html:form action="/assMarkerReallocation">
		<html:hidden property="currentPage" value="assDetail"/>
		<sakai:messages/>
		<sakai:messages message="true"/>
		<sakai:heading>
			<fmt:message key="heading.detail"/>
		</sakai:heading>	
		<sakai:instruction>
			<fmt:message key="note.assessment.clicktoselect"/>
		</sakai:instruction>
		<sakai:group_table>	
			<tr>
				<td><fmt:message key="prompt.studyUnit"/>&nbsp;</td>
				<td><bean:write name="assMarkerReallocationForm" property="studyUnitRecord.code"/></td>
				<td><bean:write name="assMarkerReallocationForm" property="studyUnitRecord.engLongDesc"/></td>
			</tr>
			<tr>
				<td><fmt:message key="prompt.yearSemester"/>&nbsp;</td>			
				<td><bean:write name="assMarkerReallocationForm" property="acadYear"/>/<bean:write name="assMarkerReallocationForm" property="semesterType"/></td>
				<td>&nbsp;</td>
			</tr>
		</sakai:group_table>	
		<sakai:instruction>
			<fmt:message key="instruction.input3"/>
		</sakai:instruction>
		<sakai:flat_list>
			<tr>
				<th style="text-align:left"><fmt:message key="prompt.assessment.tableHeading.assNumber"/></th>
				<th style="text-align:left"><fmt:message key="prompt.assessment.tableHeading.group"/></th>
				<th style="text-align:left"><fmt:message key="prompt.assessment.tableHeading.format"/></th>	
				<th style="text-align:left"><fmt:message key="prompt.assessment.tableHeading.uniqueNumber"/></th>	
				<th style="text-align:left"><fmt:message key="prompt.assessment.tableHeading.dueDate"/></th>	
				<th style="text-align:left"><fmt:message key="prompt.assessment.tableHeading.closingDate"/></th>			
			 </tr>
			<logic:iterate name="assMarkerReallocationForm" property="assessmentList" id="record" indexId="index">
				<tr>
					<td style="text-align:left">
					     <bean:write name="record" property="number"/>
					 <logic:equal name="record" property="markerExist" value="true">
						 <logic:equal name="assMarkerReallocationForm" property="updateAllowed" value="true">
								<html:link href="assMarkerReallocation.do?action=editAssMarkerScreen" paramName="assMarkerReallocationForm" 
								          paramProperty='<%= "assessmentList[" + index.toString() + "].number"%>' paramId="assignmentNr">                                   							
								<fmt:message key="prompt.linkEditText"/>
							</html:link>	
						 </logic:equal>
						 <logic:equal name="assMarkerReallocationForm" property="updateAllowed" value="false">
						           <html:link href="assMarkerReallocation.do?action=viewAssessmentStats" paramName="assMarkerReallocationForm"
								             paramProperty='<%= "assessmentList[" + index.toString() + "].number"%>' paramId="assignmentNr">                                   							
								            <fmt:message key="prompt.linkViewText"/>
							         </html:link>
					     </logic:equal>
					  </logic:equal>
					  <logic:equal name="record" property="markerExist" value="false">
							           <fmt:message key="prompt.nomarker"/>
									
					  </logic:equal>
			   	   </td>
					<td style="text-align:left"><bean:write name="record" property="type"/></td>	
					<td style="text-align:left"><bean:write name="record" property="format"/></td>					
					<td style="text-align:left"><bean:write name="record" property="uniqueNumber"/></td>
					<td style="text-align:left"><bean:write name="record" property="dueDate"/></td>
					<td style="text-align:left"><bean:write name="record" property="finalMarkingDate"/></td>
				</tr>
			</logic:iterate>
		</sakai:flat_list>
		<sakai:actions>
			<html:submit property="action">
					<fmt:message key="button.cancel"/>
			</html:submit>			
		</sakai:actions>				
	</html:form>
</sakai:html>	