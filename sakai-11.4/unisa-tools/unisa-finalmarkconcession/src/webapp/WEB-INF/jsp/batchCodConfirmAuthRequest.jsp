<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>

<fmt:setBundle basename="za.ac.unisa.lms.tools.finalmarkconcession.ApplicationResources"/>
<script language="javascript">
	function doAction() {
		document.finalMarkConcessionForm.action = 'finalMarkConcession.do?act=submitBatchAuthRequest';  
		document.finalMarkConcessionForm.submit();
	}
</script>
<sakai:html>
	<html:form action="/finalMarkConcession">
		<html:hidden property="currentPage" value="batchCodConfirmAuthRequest"/>
		<sakai:messages/>
		<sakai:messages message="true"/>
		<sakai:instruction>
			<fmt:message key="note.required"/><fmt:message key="prompt.required"/>
		</sakai:instruction>
		<sakai:heading>
			<fmt:message key="heading.requestBatchCodAuthorisation"/>
		</sakai:heading>		
		<sakai:group_heading>
			<fmt:message key="note.confirmBatchCodAuthoriseRequest"/> 
		</sakai:group_heading>
		<sakai:group_table>	
			<tr>
				<th align="left" rowspan="2"><fmt:message key="import.studentNumber"/></th>
				<th align="left" rowspan="2"><fmt:message key="import.studentName"/></th>
				<th align="left" rowspan="2"><fmt:message key="import.reg"/></th>
				<th align="left" rowspan="2"><fmt:message key="import.finalMark"/></th>
				<th align="left" rowspan="2"><fmt:message key="import.yearMark"/></th>
				<!-- <th align="left" rowspan="2"><fmt:message key="import.primL"/></th>				
				<th align="left" rowspan="2"><fmt:message key="import.status"/></th> -->
				<th align="left" colspan="3" align="center"><fmt:message key="import.concession"/></th>
				<th align="left" rowspan="2"><fmt:message key="import.revisedFinalMark"/></th>
			</tr>			
			<tr>							
				<th align="left" ><fmt:message key="import.ass"/></th>
				<th align="left" ><fmt:message key="import.acSup"/></th>
				<th align="left"><fmt:message key="import.mark"/></th>
			</tr>			
			<logic:iterate name="confirmList" id="record" indexId="index">	
				<tr>			
					<td><bean:write name="record" property="studentNumber"/></td>
					<td><bean:write name="record" property="studentName"/></td>	
					<td><bean:write name="record" property="result.academicYear"/>/<bean:write name="record" property="result.semester"/></td>	
					<td><bean:write name="record" property="result.finalMark"/></td>
					<td><bean:write name="record" property="result.yearMark"/></td>
					<!-- <td><bean:write name="record" property="altExamOpt.responsibleLecturer.name"/></td> 
					<td><bean:write name="record" property="altExamOpt.status"/></td> --> 
					<td><span title="<bean:write name="record" property="altExamOpt.alternativeAssessOptDesc"/>"><bean:write name="record" property="altExamOpt.alternativeAssessOpt"/></span></td> 
					<td><span title="<bean:write name="record" property="altExamOpt.academicSupportDesc"/>"><bean:write name="record" property="altExamOpt.academicSupportOpt"/></span></td>
					<td><bean:write name="record" property="altExamOpt.concessionMark"/></td>
					<td><bean:write name="record" property="altExamOpt.finalMark"/></td>				
				</tr>	
			</logic:iterate>
		</sakai:group_table>
		<hr>
		<sakai:group_table>
			<tr>
				<td><fmt:message key="prompt.department"/></td>
				<td><bean:write name="finalMarkConcessionForm" property="authDepartment.code"/>-
				<bean:write name="finalMarkConcessionForm" property="authDepartment.description"/></td>
			</tr>
		</sakai:group_table>
		<sakai:group_heading>
			<fmt:message key="note.requestAuthorisation.1"/> 
		</sakai:group_heading>
		<sakai:instruction>
			<fmt:message key="note.requestAuthorisation.2"/>
		</sakai:instruction>
		<sakai:instruction>
			<fmt:message key="note.requestAuthorisation.3"/>
		</sakai:instruction>
		<sakai:group_table>		
			<tr>
				<td colspan="2"><fmt:message key="prompt.cod"/></td>
			<tr>
			<logic:iterate name="codList" id="record">
				<tr>
					<td><html:radio property="selectedAuthoriser" idName="record" value="personnelNumber"></html:radio></td>
					<td><bean:write name="record" property="name"/></td>
					<td><bean:write name="record" property="emailAddress"/></td>
				</tr>
			</logic:iterate>
			<tr>
				<td colspan="2"><fmt:message key="prompt.actingCods"/></td>
			<tr>
			<logic:iterate name="finalMarkConcessionForm" property="authDepartment.actingCodList" id="record">
				<tr>
					<td><html:radio property="selectedAuthoriser" idName="record" value="personnelNumber"></html:radio></td>
					<td><bean:write name="record" property="name"/></td>
					<td><bean:write name="record" property="emailAddress"/></td>
				</tr>
			</logic:iterate>
		</sakai:group_table>	
		<sakai:instruction>
			<fmt:message key="note.requestAuthorisation.4"/>
		</sakai:instruction>
		<sakai:group_table>
			<tr>
				<td><fmt:message key="prompt.emailResponseAddress"/><fmt:message key="prompt.required"/></td>
				<td><html:text name="finalMarkConcessionForm" property="responseEmailAddress" size="40" maxlength="60"/></td>
			</tr>
		</sakai:group_table>	
		<sakai:actions>			
			<html:submit property="act" onclick="javascript:disabled=true;doAction();">
					<fmt:message key="button.batchRequest"/>
			</html:submit>
			<html:submit property="act">
					<fmt:message key="button.back"/>
			</html:submit>				
		</sakai:actions>
	</html:form>
</sakai:html>