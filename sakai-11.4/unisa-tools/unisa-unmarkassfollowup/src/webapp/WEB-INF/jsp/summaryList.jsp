<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>

<fmt:setBundle basename="za.ac.unisa.lms.tools.unmarkassfollowup.ApplicationResources"/>


<sakai:html>
	<html:form action="/unmarkAssFollowUp">	
		<html:hidden property="currentPage" value="summary"/>		
		<sakai:messages/>
		<sakai:messages message="true"/>
		<sakai:heading>
			<fmt:message key="heading.summary"/>
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
				<logic:equal name="unmarkAssFollowUpForm" property="selectedSummaryBreakdownLevel" value="COLLEGE">
					<td><fmt:message key="prompt.college"/>&nbsp;:&nbsp;<bean:write name="unmarkAssFollowUpForm" property="college.description" /></td>
				</logic:equal>
				<logic:equal name="unmarkAssFollowUpForm" property="selectedSummaryBreakdownLevel" value="SCHOOL">
					<td><fmt:message key="prompt.school"/>&nbsp;:&nbsp;<bean:write name="unmarkAssFollowUpForm" property="school.description" /></td>
				</logic:equal>
				<logic:equal name="unmarkAssFollowUpForm" property="selectedSummaryBreakdownLevel" value="DEPARTMENT">
					<td><fmt:message key="prompt.department"/>&nbsp;:&nbsp;<bean:write name="unmarkAssFollowUpForm" property="summaryDepartment.description" /></td>
				</logic:equal>
				<logic:equal name="unmarkAssFollowUpForm" property="selectedSummaryBreakdownLevel" value="STUDYUNIT">
					<td><fmt:message key="prompt.studyUnit"/>&nbsp;:&nbsp;<bean:write name="unmarkAssFollowUpForm" property="summaryStudyUnit" /></td>
				</logic:equal>
				<logic:equal name="unmarkAssFollowUpForm" property="selectedSummaryBreakdownLevel" value="MARKER">
					<td><fmt:message key="prompt.marker"/>&nbsp;:&nbsp;<bean:write name="unmarkAssFollowUpForm" property="summaryMarker.name" /></td>
				</logic:equal>
				<logic:equal name="unmarkAssFollowUpForm" property="selectedSummaryBreakdownLevel" value="ASSIGNMENT">
					<td><fmt:message key="prompt.assignment"/>&nbsp;:&nbsp;<bean:write name="unmarkAssFollowUpForm" property="summaryAssignment.studyUnit" />&nbsp;<bean:write name="unmarkAssFollowUpForm" property="summaryAssignment.assNumber" /></td>
				</logic:equal>
			</tr>
			<tr>
				<td colspan="2">Include all assignments more than <bean:write name="unmarkAssFollowUpForm" property="summaryDaysOutstanding" /> days outstanding</td>
			</tr>
			<tr>
				<td><fmt:message key="prompt.summariseOption"/>&nbsp;</td>
				<td><logic:notEqual name="unmarkAssFollowUpForm" property="selectedSummariseOn" value="teaching">
						<html:radio name="unmarkAssFollowUpForm" property="selectedSummariseOn" value="DEPARTMENT"></html:radio>
						<fmt:message key="prompt.summariseDepartment"/>
					</logic:notEqual>
					<html:radio name="unmarkAssFollowUpForm" property="selectedSummariseOn" value="STUDYUNIT"></html:radio>
					<fmt:message key="prompt.summariseStudyUnit"/>
					<html:radio name="unmarkAssFollowUpForm" property="selectedSummariseOn" value="ASSIGNMENT"></html:radio>
					<fmt:message key="prompt.summariseAssignment"/>
				</td>
			</tr>
			<tr>
				<td><fmt:message key="prompt.summarySortOption"/>&nbsp;</td>
				<td><html:radio name="unmarkAssFollowUpForm" property="selectedSummarySortOn" value="daysFirst"></html:radio>
					<fmt:message key="prompt.summarySortDaysFirst"/>
					<html:radio name="unmarkAssFollowUpForm" property="selectedSummarySortOn" value="daysLast"></html:radio>
					<fmt:message key="prompt.summarySortDaysLast"/>
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
		</sakai:actions>		
		<hr/>					
		<sakai:flat_list>	
			<tr>
				<td colspan="6" align="left">
					<fmt:message key="note.detailLink"/>	
				</td>
				<td colspan="6" align="right">
					<html:link href="unmarkAssFollowUp.do?action=extractFile">
						<fmt:message key="link.extractFile"/>						
					</html:link>	
				</td>			
			</tr>
			<logic:notEqual name="unmarkAssFollowUpForm" property="fromSystem" value="email">
				<tr><td colspan="12" align="right">
						<html:link href="unmarkAssFollowUp.do?action=emailList">
							<fmt:message key="link.emailSummaryList"/>						
						</html:link>	
					</td>			
				</tr>
			</logic:notEqual>
			<tr>
				<th align="left"><fmt:message key="summary.daysOutstanding"/></th>
				<th align="left"><fmt:message key="summary.accumTotal"/></th>
				<th align="left"><fmt:message key="summary.rangeOutstanding"/></th>
				<th align="left"><fmt:message key="summary.total"/></th>				
				<th align="left"><fmt:message key="summary.college"/></th>
				<th align="left"><fmt:message key="summary.school"/></th>
				<th align="left"><fmt:message key="summary.department"/></th>
				<th align="left"><fmt:message key="summary.studyUnit"/></th>
				<th align="left"><fmt:message key="summary.primLec"/></th>
				<th align="left"><fmt:message key="summary.assNr"/></th>		
			</tr>
			<logic:notEmpty name="unmarkAssFollowUpForm" property="listSummary">
				<logic:iterate name="unmarkAssFollowUpForm" property="listSummary" id="record">
					<bean:define id="dptCode" name="record" property="department.code"/>
					<bean:define id="studyUnit" name="record" property="studyUnit"/>
					<bean:define id="assNr" name="record" property="assignmentNr"/>
					<bean:define id="firstValue" name="record" property="firstValueInRange"/>
					<bean:define id="lastValue" name="record" property="lastValueInRange"/>	
					<% 
						java.util.HashMap params = new java.util.HashMap();
						params.put("dptCode",dptCode);
						params.put("studyUnit",studyUnit);
						params.put("assNr",assNr);
						params.put("firstValue",firstValue);
						params.put("lastValue",lastValue);	
					%>
					<logic:equal name="unmarkAssFollowUpForm" property="selectedSummaryBreakdownLevel" value="STUDYUNIT">
						<bean:define id="breakDownStudyUnit" name="unmarkAssFollowUpForm" property="summaryStudyUnit" />
						<%
						params.put("studyUnit",breakDownStudyUnit);
						%>
					</logic:equal>
					<logic:equal name="unmarkAssFollowUpForm" property="selectedSummaryBreakdownLevel" value="ASSIGNMENT">
						<bean:define id="breakDownStudyUnit" name="unmarkAssFollowUpForm" property="summaryAssignment.studyUnit" />
						<bean:define id="breakDownAssNr" name="unmarkAssFollowUpForm" property="summaryAssignment.assNumber" />
						<%
						params.put("studyUnit",breakDownStudyUnit);
						params.put("assNr",breakDownAssNr);						
						%>
					</logic:equal>
					<logic:equal name="unmarkAssFollowUpForm" property="selectedSummaryBreakdownLevel" value="MARKER">
						<bean:define id="markerPersno" name="unmarkAssFollowUpForm" property="summaryMarkerPersno" />
						<%
						params.put("markerPersno",markerPersno);		
						%>
					</logic:equal>
					<%
					pageContext.setAttribute("params",params);
					%>
				<tr>
					<td><html:link href="unmarkAssFollowUp.do?action=drilDetailList" scope="page" name="params">   
							<bean:write name="record" property="daysOutstanding"/>
						</html:link>
					</td>
					<td><bean:write name="record" property="accumalate"/></td>
					<td><html:link href="unmarkAssFollowUp.do?action=drilRangeList" scope="page" name="params">
						<bean:write name="record" property="daysOutstandingRange"/>
						</html:link>
					</td>
					<td><bean:write name="record" property="total"/></td>						
					<td><bean:write name="record" property="collegeAbbreviation"/></td>
					<td><bean:write name="record" property="schoolAbbreviation"/></td>
					<td><bean:write name="record" property="department.description"/></td>
					<td><bean:write name="record" property="studyUnit"/></td>	
					<td><bean:write name="record" property="primLecturer.name"/></td>	
					<td><bean:write name="record" property="assignmentNr"/></td>														
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
		</sakai:actions>			
	</html:form>
</sakai:html>