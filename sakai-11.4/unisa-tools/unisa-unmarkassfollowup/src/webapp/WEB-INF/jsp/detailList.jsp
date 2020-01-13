<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>

<fmt:setBundle basename="za.ac.unisa.lms.tools.unmarkassfollowup.ApplicationResources"/>


<sakai:html>
	<html:form action="/unmarkAssFollowUp">	
		<html:hidden property="currentPage" value="detail"/>
		<sakai:messages/>
		<sakai:messages message="true"/>
		<sakai:heading>
			<fmt:message key="heading.detail"/>
		</sakai:heading>
		<sakai:instruction>
			<fmt:message key="note.required"/>&nbsp;<fmt:message key="prompt.mandatory"/>
		</sakai:instruction>
		<sakai:group_table>
			<tr>
				<td><fmt:message key="prompt.acadYear"/>&nbsp;</td>
				<td><bean:write name="unmarkAssFollowUpForm" property="acadYear" /></td>
			</tr>
			<tr>
				<td><fmt:message key="prompt.semester"/>&nbsp;</td>
				<td><bean:write name="unmarkAssFollowUpForm" property="semesterType" /></td>				
			</tr>
			<tr>			
				<td><fmt:message key="prompt.breakDownLevel"/>&nbsp;</td>
				<logic:equal name="unmarkAssFollowUpForm" property="selectedDetailBreakdownLevel" value="COLLEGE">
					<td><fmt:message key="prompt.college"/>&nbsp;:&nbsp;<bean:write name="unmarkAssFollowUpForm" property="college.description" /></td>
				</logic:equal>
				<logic:equal name="unmarkAssFollowUpForm" property="selectedDetailBreakdownLevel" value="SCHOOL">
					<td><fmt:message key="prompt.school"/>&nbsp;:&nbsp;<bean:write name="unmarkAssFollowUpForm" property="school.description" /></td>
				</logic:equal>
				<logic:equal name="unmarkAssFollowUpForm" property="selectedDetailBreakdownLevel" value="DEPARTMENT">
					<td><fmt:message key="prompt.department"/>&nbsp;:&nbsp;<bean:write name="unmarkAssFollowUpForm" property="detailDepartment.description" />
					<logic:notEmpty name="unmarkAssFollowUpForm" property="selectedDetailSubBreakMarkerName">(Marker : <bean:write name="unmarkAssFollowUpForm" property="selectedDetailSubBreakMarkerName" />)</logic:notEmpty></td>
				</logic:equal>
				<logic:equal name="unmarkAssFollowUpForm" property="selectedDetailBreakdownLevel" value="STUDYUNIT">
					<td><fmt:message key="prompt.studyUnit"/>&nbsp;:&nbsp;<bean:write name="unmarkAssFollowUpForm" property="detailStudyUnit" />
					<logic:notEmpty name="unmarkAssFollowUpForm" property="selectedDetailSubBreakMarkerName">(Marker : <bean:write name="unmarkAssFollowUpForm" property="selectedDetailSubBreakMarkerName" />)</logic:notEmpty></td>
				</logic:equal>
				<logic:equal name="unmarkAssFollowUpForm" property="selectedDetailBreakdownLevel" value="MARKER">
					<td><fmt:message key="prompt.marker"/>&nbsp;:&nbsp;<bean:write name="unmarkAssFollowUpForm" property="detailMarker.name" /></td>
				</logic:equal>
				<logic:equal name="unmarkAssFollowUpForm" property="selectedDetailBreakdownLevel" value="ASSIGNMENT">
					<td><fmt:message key="prompt.assignment"/>&nbsp;:&nbsp;<bean:write name="unmarkAssFollowUpForm" property="detailAssignment.studyUnit" />&nbsp;<bean:write name="unmarkAssFollowUpForm" property="assignment.assNumber" />
					<logic:notEmpty name="unmarkAssFollowUpForm" property="selectedDetailSubBreakMarkerName">(Marker : <bean:write name="unmarkAssFollowUpForm" property="selectedDetailSubBreakMarkerName" />)</logic:notEmpty></td>
				</logic:equal>
			</tr>
			<tr>
				<td colspan="2">Include all assignments more than <bean:write name="unmarkAssFollowUpForm" property="detailDaysOutstanding" /> days outstanding</td>
			</tr>
			<tr>
				<td><fmt:message key="prompt.detailSortOption"/>&nbsp;</td>
				<td><html:radio name="unmarkAssFollowUpForm" property="selectedDetailSortOn" value="daysFirst"></html:radio>
					<fmt:message key="prompt.detailSortDaysFirst"/>
					<html:radio name="unmarkAssFollowUpForm" property="selectedDetailSortOn" value="daysLast"></html:radio>
					<fmt:message key="prompt.detailSortDaysLast"/>
				</td>
			</tr>
		</sakai:group_table>
		<sakai:actions>
			<html:submit property="action">
					<fmt:message key="button.display"/>
			</html:submit>	
			<logic:notEqual name="unmarkAssFollowUpForm" property="fromSystem" value="email">			
				<html:submit property="action">
						<fmt:message key="button.cancel"/>
				</html:submit>	
				<html:submit property="action">
					<fmt:message key="button.back"/>
			</html:submit>	
			</logic:notEqual>			
			<logic:equal name="unmarkAssFollowUpForm" property="fromSystem" value="email">
				<logic:notEqual name="unmarkAssFollowUpForm" property="fromPage" value="email">
					<html:submit property="action">
						<fmt:message key="button.back"/>
					</html:submit>	
				</logic:notEqual>	
			</logic:equal>		
		</sakai:actions>		
		<hr/>					
		<sakai:flat_list>	
			<tr><td colspan="12" align="right">
					<html:link href="unmarkAssFollowUp.do?action=extractFile">
						<fmt:message key="link.extractFile"/>						
					</html:link>	
				</td>			
			</tr>
			<logic:notEqual name="unmarkAssFollowUpForm" property="fromSystem" value="email">
				<tr><td colspan="12" align="right">
						<html:link href="unmarkAssFollowUp.do?action=emailList">
							<fmt:message key="link.emailDetailList"/>						
						</html:link>	
					</td>			
				</tr>
			</logic:notEqual>
			<tr>
				<th align="left"><fmt:message key="detail.days"/></th>
				<th align="left"><fmt:message key="detail.department"/></th>
				<th align="left"><fmt:message key="detail.studyUnit"/></th>
				<th align="left"><fmt:message key="detail.assNr"/></th>
				<th align="left"><fmt:message key="detail.studentNr"/></th>
				<th align="left"><fmt:message key="detail.received"/></th>
				<th align="left"><fmt:message key="detail.assType"/></th>
				<th align="left"><fmt:message key="detail.subMode"/></th>
				<th align="left"><fmt:message key="detail.markingMode"/></th>
				<th align="left"><fmt:message key="detail.primL"/></th>		
				<th align="left"><fmt:message key="detail.marker"/></th>	
				<th align="left"><fmt:message key="detail.docketNr"/></th>			
			</tr>
			<logic:notEmpty name="detailList">
				<logic:iterate name="detailList" id="record">
					<tr>
						<td><bean:write name="record" property="daysOutstanding"/></td>
						<td><bean:write name="record" property="dptDesc"/></td>
						<td><bean:write name="record" property="studyUnit"/></td>
						<td><bean:write name="record" property="assignmentNr"/></td>
						<td><bean:write name="record" property="studentNr"/></td>
						<td><bean:write name="record" property="dateReceived"/></td>
						<td><bean:write name="record" property="assignmentType"/></td>
						<td><bean:write name="record" property="submissionMode"/></td>
						<td><bean:write name="record" property="markingMode"/></td>
						<td><bean:write name="record" property="primLecturer"/></td>
						<td><span title="<bean:write name="record" property="markerPersno"/>"><bean:write name="record" property="marker"/></span></td>
						<td><bean:write name="record" property="docketNr"/></td>					
					</tr>
				</logic:iterate>
			</logic:notEmpty>
		</sakai:flat_list>
		<sakai:actions>		
			<logic:notEqual name="unmarkAssFollowUpForm" property="fromSystem" value="email">	
				<html:submit property="action">
						<fmt:message key="button.cancel"/>
				</html:submit>
				<html:submit property="action">
					<fmt:message key="button.back"/>
				</html:submit>	
			</logic:notEqual>
			<logic:equal name="unmarkAssFollowUpForm" property="fromSystem" value="email">
				<logic:notEqual name="unmarkAssFollowUpForm" property="fromPage" value="email">
					<html:submit property="action">
						<fmt:message key="button.back"/>
					</html:submit>	
				</logic:notEqual>	
			</logic:equal>						
		</sakai:actions>			
	</html:form>
</sakai:html>