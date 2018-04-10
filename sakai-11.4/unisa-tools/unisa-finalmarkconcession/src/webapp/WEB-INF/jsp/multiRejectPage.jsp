<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>

<fmt:setBundle basename="za.ac.unisa.lms.tools.finalmarkconcession.ApplicationResources"/>
<script language="javascript">
	function doAction() {
		document.finalMarkConcessionForm.action = 'authorisation.do?act=confirm';  
		document.finalMarkConcessionForm.submit();
	}
</script>
<sakai:html>
	<html:form action="/authorisation">
		<!--<html:hidden property="currentPage" value="multiRejectPage"/>-->
		<sakai:messages/>
		<sakai:messages message="true"/>
		<sakai:heading>
			<fmt:message key="heading.rejectAuthorisation"/>
		</sakai:heading>		
		<sakai:instruction>
			<fmt:message key="note.required"/>&nbsp;<fmt:message key="prompt.required"/>
		</sakai:instruction>
		<sakai:group_heading>
					<fmt:message key="heading.confirmReject"/> 
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
		<sakai:group_heading>
			<fmt:message key="heading.reject"/> 
		</sakai:group_heading>
		<sakai:group_table>
			<tr>
				<td colspan="2">
					<sakai:instruction>
						<fmt:message key="instruction.authComment"/>
					</sakai:instruction>
				</td>
			</tr>
			<tr>
				<td><fmt:message key="prompt.comment"/><fmt:message key="prompt.required"/></td>
				<td><html:textarea name="finalMarkConcessionForm" property="authComment" cols="50" rows="5"/></td>
			</tr>
		</sakai:group_table>	
		<sakai:actions>
			<html:submit property="act" onclick="javascript:disabled=true;doAction();">
				<fmt:message key="button.confirm"/>
			</html:submit>
			<html:submit property="act">
				<fmt:message key="button.cancel"/>
			</html:submit>
		</sakai:actions>
	</html:form>
</sakai:html>