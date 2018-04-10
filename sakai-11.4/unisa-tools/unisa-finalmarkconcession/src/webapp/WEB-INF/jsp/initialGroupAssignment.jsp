<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>

<fmt:setBundle basename="za.ac.unisa.lms.tools.finalmarkconcession.ApplicationResources"/>
<script language="javascript">
	function doAction() {
		document.finalMarkConcessionForm.action = 'finalMarkConcession.do?act=submitGroupAssignment';  
		document.finalMarkConcessionForm.submit();
	}
</script>
<sakai:html>	

	<html:form action="/finalMarkConcession">
		<sakai:heading><fmt:message key="heading.initialGroupAssignment"/>
		</sakai:heading>
		<html:hidden name="finalMarkConcessionForm" property="currentPage" value="initialGroupAssignment"/>
		<sakai:messages/>
		<sakai:messages message="true"/>
		<sakai:instruction>
			<fmt:message key="note.required"/><fmt:message key="prompt.required"/><BR>
		</sakai:instruction>
		<sakai:group_table>	
			<tr>
				<td><fmt:message key="prompt.examYear"/></td>
				<td colspan="2"><bean:write name="finalMarkConcessionForm" property="examYear"/></td>				
			</tr>
			<tr>
				<td><fmt:message key="prompt.examPeriod"/></td>
				<td colspan="2">
					<bean:write name="finalMarkConcessionForm" property="examPeriodDesc"/>&nbsp;
				</td>				
			</tr>
			<tr>
				<td><fmt:message key="prompt.studyUnit"/></td>				
				<td colspan="2"><bean:write name="finalMarkConcessionForm" property="studyUnitSearchCriteria"/></td>				
			</tr>	
		</sakai:group_table>
		<hr/>	
		<sakai:group_heading>
			<fmt:message key="heading.initialGroupAssignment.CurrentIndicators"/>
		</sakai:group_heading>
		<br/>
		<sakai:instruction>			
			<fmt:message key="prompt.totalRecords"/>&nbsp;<bean:write name="finalMarkConcessionForm" property="totalRecords"/><BR/>
			<fmt:message key="prompt.totalIdentifiedRecords"/>&nbsp;<bean:write name="finalMarkConcessionForm" property="totalStatusRecords"/>
		</sakai:instruction>
		<sakai:group_heading>
			<fmt:message key="heading.initialGroupAssignment.Info"/>
		</sakai:group_heading>
		<br/>
		<sakai:instruction>
			<fmt:message key="note.groupAssignment"/>
		</sakai:instruction>
		<br/>	
		<sakai:group_table>
			<tr>
				<td><fmt:message key="prompt.alternativeAssess"/><fmt:message key="prompt.required"/></td>
				<td>
					<html:select name="finalMarkConcessionForm" property="batchAlternativeAssessmentOpportunity.alternativeAssessOpt">
					        <html:optionsCollection name="finalMarkConcessionForm" property="listAlternativeAssess" value="code" label="engDescription"/>
				</html:select>
				</td>
			</tr>
			<logic:equal name="finalMarkConcessionForm" property="removeAssessOptOther" value="false">                                                           
				<tr>
					<td colspan="2"><sakai:instruction><fmt:message key="note.alternativeAssessOther"/></sakai:instruction>
				</tr>
				<tr>					
					<td colspan="2"><html:textarea name="finalMarkConcessionForm" property="batchAlternativeAssessmentOpportunity.alternativeAssessOtherDesc" cols="50" rows="5"/></td>
				</tr>
			</logic:equal>  
			<tr>
				<td><fmt:message key="prompt.academicSupport"/><fmt:message key="prompt.required"/></td>
				<td>
					<html:select name="finalMarkConcessionForm" property="batchAlternativeAssessmentOpportunity.academicSupportOpt">
					<html:optionsCollection name="finalMarkConcessionForm" property="listAcademicSupport" value="code" label="engDescription"/>
					</html:select>
				</td>
			</tr>
			<tr>
				<td colspan="2"><sakai:instruction><fmt:message key="note.alternativeAssessOther"/></sakai:instruction>
			</tr>
			<tr>				
				<td colspan="2"><html:textarea name="finalMarkConcessionForm" property="batchAlternativeAssessmentOpportunity.academicSupportOtherDesc" cols="50" rows="5"/></td>
			</tr>
				<tr>
				<td><fmt:message key="prompt.responsibleLecturer"/><fmt:message key="prompt.required"/></td>
				<td>
					<html:select name="finalMarkConcessionForm" property="batchAlternativeAssessmentOpportunity.responsibleLecturer.personnelNumber">
					<html:optionsCollection name="finalMarkConcessionForm" property="listLecturer" value="personnelNumber" label="name"/>
					</html:select>
				</td>
			</tr>		
		</sakai:group_table>		
		<sakai:actions>			
			<logic:notEqual name="finalMarkConcessionForm" property="totalStatusRecords" value="0">		
				<html:submit  property="act" onclick="javascript:disabled=true;doAction();">
					<fmt:message key="button.submit"/>
				</html:submit>	
			</logic:notEqual>	
			<logic:equal name="finalMarkConcessionForm" property="totalStatusRecords" value="0">		
				<html:submit  property="act" disabled="true">
					<fmt:message key="button.submit"/>
				</html:submit>	
			</logic:equal>	
			<html:submit property="act">
				<fmt:message key="button.cancel"/>
			</html:submit>		
		</sakai:actions>		
	</html:form>
</sakai:html>
