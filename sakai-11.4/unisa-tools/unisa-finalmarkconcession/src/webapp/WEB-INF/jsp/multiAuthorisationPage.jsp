<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>

<fmt:setBundle basename="za.ac.unisa.lms.tools.finalmarkconcession.ApplicationResources"/>
<script language="javascript">
	function doActionDean() {
		document.finalMarkConcessionForm.action = 'authorisation.do?act=confirm';  
		document.finalMarkConcessionForm.submit();
	}
	function doActionCod() {
		document.finalMarkConcessionForm.action = 'authorisation.do?act=confirm';  
		document.finalMarkConcessionForm.submit();
	}
</script>

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
		<sakai:group_heading>
					<fmt:message key="heading.confirmAuthorise"/> 
		</sakai:group_heading>
		<sakai:instruction>
						<fmt:message key="note.abbrevFields"/>
		</sakai:instruction>				
		<sakai:group_table>		
			<tr>			
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
			<logic:iterate name="confirmList" id="rec">
				<tr>
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
			
			<logic:equal name="finalMarkConcessionForm" property="authorisationType" value="AUTHREQCOD">
				<sakai:group_heading>
					<fmt:message key="heading.authorise"/> 
				</sakai:group_heading>
				<sakai:instruction>
					<fmt:message key="note.requestAuthorisation.2"/>
				</sakai:instruction>
				<sakai:instruction>
					<fmt:message key="note.requestAuthorisation.3"/>
				</sakai:instruction>
				<sakai:group_table>		
					<tr>
						<td colspan="2"><fmt:message key="prompt.dean"/></td>
					</tr>
					<logic:iterate name="finalMarkConcessionForm" property="listDeans" id="record">
						<tr>
							<td><html:radio property="selectedAuthoriser" idName="record" value="personnelNumber"></html:radio></td>
							<td><bean:write name="record" property="name"/></td>
							<td><bean:write name="record" property="emailAddress"/></td>
						</tr>
					</logic:iterate>
					<tr>
						<td colspan="2"><fmt:message key="prompt.deputyDean"/></td>
					</tr>
					<logic:iterate name="finalMarkConcessionForm" property="authDepartment.deputyDeanList" id="record">
						<tr>
							<td><html:radio property="selectedAuthoriser" idName="record" value="personnelNumber"></html:radio></td>
							<td><bean:write name="record" property="name"/></td>
							<td><bean:write name="record" property="emailAddress"/></td>
						</tr>
					</logic:iterate>
					<tr>
						<td colspan="2"><fmt:message key="prompt.schoolDirector"/></td>
					</tr>
					<logic:iterate name="finalMarkConcessionForm" property="listDirectors" id="record">
						<tr>
							<td><html:radio property="selectedAuthoriser" idName="record" value="personnelNumber"></html:radio></td>
							<td><bean:write name="record" property="name"/></td>
							<td><bean:write name="record" property="emailAddress"/></td>
						</tr>
					</logic:iterate>
					<tr>
						<td colspan="2"><fmt:message key="prompt.deputyDirector"/></td>
					</tr>
					<logic:iterate name="finalMarkConcessionForm" property="authDepartment.deputyDirectorList" id="record">
						<tr>
							<td><html:radio property="selectedAuthoriser" idName="record" value="personnelNumber"></html:radio></td>
							<td><bean:write name="record" property="name"/></td>
							<td><bean:write name="record" property="emailAddress"/></td>
						</tr>
					</logic:iterate>					
				</sakai:group_table>	
			<sakai:actions>
				<html:submit property="act" onclick="javascript:disabled=true;doActionCod();">
					<fmt:message key="button.requestAuthDean"/>
				</html:submit>
				<html:submit property="act">
					<fmt:message key="button.cancel"/>
				</html:submit>
			</sakai:actions>
		</logic:equal>	
		<sakai:actions>			
			<logic:equal name="finalMarkConcessionForm" property="authorisationType" value="AUTHREQDN">
				<html:submit property="act" onclick="javascript:disabled=true;doActionDean();">
					<fmt:message key="button.confirm"/>
				</html:submit>
				<html:submit property="act">
					<fmt:message key="button.cancel"/>
				</html:submit>
			</logic:equal>
		</sakai:actions>
				
	</html:form>
</sakai:html>