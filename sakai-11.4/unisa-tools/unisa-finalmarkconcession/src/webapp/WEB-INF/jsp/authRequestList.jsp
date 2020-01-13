<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>

<fmt:setBundle basename="za.ac.unisa.lms.tools.finalmarkconcession.ApplicationResources"/>

<sakai:html>		
	<html:form action="/authorisation">
		<logic:equal name="finalMarkConcessionForm" property="authorisationType" value="AUTHREQCOD">
			<sakai:heading><fmt:message key="heading.altExamOptCodAuthorisation"/></sakai:heading>
		</logic:equal>
		<logic:equal name="finalMarkConcessionForm" property="authorisationType" value="AUTHREQDN">
			<sakai:heading><fmt:message key="heading.altExamOptDeanAuthorisation"/></sakai:heading>
		</logic:equal>	
		<sakai:messages/>
		<sakai:messages message="true"/>
		<sakai:group_table>	
			<tr>
				<td><fmt:message key="prompt.examYear"/><fmt:message key="prompt.required"/></td>
				<td colspan="2"><html:text name="finalMarkConcessionForm" property="authListCriteriaExamYear" size="4" maxlength="4"/></td>				
			</tr>
			<tr>
				<td><fmt:message key="prompt.examPeriod"/><fmt:message key="prompt.required"/></td>
				<td colspan="2">
					<html:select name="finalMarkConcessionForm" property="authListCriteriaExamPeriod">
					<html:optionsCollection name="finalMarkConcessionForm" property="examPeriodCodes" value="code" label="engDescription"/>
					</html:select>
				</td>				
			</tr>
			<tr>
				<td>
					<fmt:message key="prompt.department"/>
				</td>
				<td>
					<html:select name="finalMarkConcessionForm" property="authListCriteriaDepartment">
					<html:optionsCollection name="finalMarkConcessionForm"  property="listAuthDepartments" value="value" label="label"/>
					</html:select><BR>
				</td>
			</tr>
		</sakai:group_table>
		<sakai:actions>
			<html:submit property="act">
					<fmt:message key="button.display"/>
			</html:submit>	
		</sakai:actions>
		<hr/>
		<sakai:group_heading>
			<fmt:message key="prompt.codAuthRequestList"/> 
		</sakai:group_heading>	
		<sakai:instruction>
						<fmt:message key="note.abbrevFields"/>
		</sakai:instruction>			
		<logic:empty name="finalMarkConcessionForm" property="authRequestList">
				<sakai:group_table>
					<tr><td>
						<sakai:instruction>
							<fmt:message key="note.noRequest"/>
						</sakai:instruction>
					</td></tr>
				</sakai:group_table>
			</logic:empty>
		<logic:notEmpty name="finalMarkConcessionForm" property="authRequestList">
			<sakai:group_table>		
				<tr>			
					<th style="white-space: nowrap">&nbsp;</th>
					<th><fmt:message key="request.studyUnit"/>&nbsp;</th>
					<th><fmt:message key="request.studentNumber"/>&nbsp;</th>									
					<th><fmt:message key="request.alternativeAssessment"/>&nbsp;</th>					
					<th><fmt:message key="request.academicSupport"/>&nbsp;</th>
					<th><fmt:message key="request.publishMark"/>&nbsp;</th>
					<th><fmt:message key="request.yearMark"/>&nbsp;</th>
					<th><fmt:message key="request.concessionMark"/>&nbsp;</th>	
					<th><fmt:message key="request.finalAssessMark"/>&nbsp;</th>	
					<th><fmt:message key="request.department"/>&nbsp;</th>					
				</tr>
				<logic:iterate name="finalMarkConcessionForm" property="authRequestList" id="rec" indexId="index">
					<bean:define id="examYear" name="rec" property="examYear"/>
					<bean:define id="examPeriod" name="rec" property="examPeriod"/>
					<bean:define id="studyUnit" name="rec" property="studyUnit"/>
					<bean:define id="studentNumber" name="rec" property="studentNumber"/>
					<% 
						java.util.HashMap authParams = new java.util.HashMap();
						authParams.put("examYear",examYear);
						authParams.put("examPeriod",examPeriod);
						authParams.put("studyUnit",studyUnit);
						authParams.put("studentNumber",studentNumber);
						pageContext.setAttribute("authParams",authParams);
					%>
					
					<tr>
						<td  style="white-space:nowrap;align:left">
							<html:multibox property="indexNrSelectedAuth"><bean:write name="index"/></html:multibox>
							&nbsp;
							<html:submit property='<%="action.view" + index.toString() %>'><fmt:message key="button.view"/>
							</html:submit>
							<!--  <html:link href="authorisation.do?action=viewConcessionForm" scope="page" name="authParams">
								<fmt:message key="link.view"/>
							</html:link>	-->		
						</td>							
						<td><bean:write name="rec" property="studyUnit"/></td>
						<td><bean:write name="rec" property="studentNumber"/></td>						
						<td>
							<span title="<bean:write name="rec" property="fiConcession.alternativeAssessOptDesc"/>"><bean:write name="rec" property="fiConcession.alternativeAssessOpt"/></span>
							&nbsp;
							<span title="<bean:write name="rec" property="fiConcession.alternativeAssessOtherDesc"/>"><bean:write name="rec" property="fiConcession.alternativeAssessOtherShortDesc"/></span>
						</td>	
						<td>
							<span title="<bean:write name="rec" property="fiConcession.academicSupportDesc"/>"><bean:write name="rec" property="fiConcession.academicSupportOpt"/></span>
							&nbsp;
							<span title="<bean:write name="rec" property="fiConcession.academicSupportOtherDesc"/>"><bean:write name="rec" property="fiConcession.academicSupportOtherShortDesc"/></span>
						</td>
						<td>
							<bean:write name="rec" property="result.finalMark"/>
						</td>
						<td>
							<bean:write name="rec" property="result.yearMark"/>
						</td>	
						<td>
							<bean:write name="rec" property="fiConcession.concessionMark"/>
						</td>	
						<td>
							<bean:write name="rec" property="fiConcession.finalMark"/>
						</td>
						<td>
							<bean:write name="rec" property="department.description"/>
						</td>			
					</tr>			 
				</logic:iterate>
			</sakai:group_table>				
			<sakai:actions>		
				<html:submit property="act">
							<fmt:message key="button.selectAll"/>
				</html:submit>	
				<html:submit property="act">
							<fmt:message key="button.deSelectAll"/>
				</html:submit>
				<html:submit property="act">
							<fmt:message key="button.authorise"/>
				</html:submit>	
				<html:submit property="act">
							<fmt:message key="button.reject"/>
				</html:submit>					
				<html:submit property="act">
						<fmt:message key="button.cancel"/>
				</html:submit>	
			</sakai:actions>	
		</logic:notEmpty>
		
		<logic:empty name="finalMarkConcessionForm" property="authRequestList">
			<sakai:actions>
				<html:submit property="act">
							<fmt:message key="button.cancel"/>
				</html:submit>				
			</sakai:actions>
		</logic:empty>						
	</html:form>
</sakai:html>