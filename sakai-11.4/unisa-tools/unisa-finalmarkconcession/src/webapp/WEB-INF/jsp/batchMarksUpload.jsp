<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>

<fmt:setBundle basename="za.ac.unisa.lms.tools.finalmarkconcession.ApplicationResources"/>
<script language="javascript">
	function doAction() {
		document.finalMarkConcessionForm.action = 'finalMarkConcession.do?act=uploadMarks';  
		document.finalMarkConcessionForm.submit();
	}
</script>

<sakai:html>
	<html:form action="/finalMarkConcession">
		<html:hidden property="currentPage" value="batchMarksUpload"/>
		<sakai:messages/>
		<sakai:messages message="true"/>
		<sakai:heading>		
			<fmt:message key="heading.import"/>	
		</sakai:heading>		
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
		
		<sakai:flat_list>		
			<tr>				
				<th align="left"><fmt:message key="batch.studentNumber"/></th>
				<th align="left"><fmt:message key="batch.studentName"/></th>
				<th align="left"><fmt:message key="batch.gradebookPercentage"/></th>
				<th align="left"><fmt:message key="batch.uploadStatus"/></th>	
				<th align="left"><fmt:message key="batch.yearMark"/></th>	
				<th align="left"><fmt:message key="batch.concessionMark"/></th>	
				<th align="left"><fmt:message key="batch.revisedFinalMark"/></th>	
				<th align="left"><fmt:message key="batch.fiStatus"/></th>			
			</tr>			
			<logic:notEmpty name="finalMarkConcessionForm" property="listBatchVerifiedRecords">
				<logic:iterate name="finalMarkConcessionForm" property="listBatchVerifiedRecords" id="record">	
				<tr>					
					<td><bean:write name="record" property="studentNumber"/></td>
					<td><bean:write name="record" property="studentName"/></td>		
					<td><bean:write name="record" property="gradebookPercentage"/></td>		
					<td><bean:write name="record" property="status"/></td>	
					<td>
						<logic:empty name="record" property="result">&nbsp;</logic:empty>
						<logic:notEmpty name="record" property="result">
							<logic:notEmpty name="record" property="result.yearMark">
								<bean:write name="record" property="result.yearMark"/>
							</logic:notEmpty>&nbsp;
						</logic:notEmpty>	
					</td>	
					<logic:empty name="record" property="altExamOpt"><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td></logic:empty>	
					<logic:notEmpty name="record" property="altExamOpt">			
						<td><logic:notEmpty name="record" property="altExamOpt.concessionMark">
							<bean:write name="record" property="altExamOpt.concessionMark"/>
						</logic:notEmpty>&nbsp;</td>
						<td><logic:notEmpty name="record" property="altExamOpt.finalMark">
							<bean:write name="record" property="altExamOpt.finalMark"/>
						</logic:notEmpty>&nbsp;</td>
						<td><logic:notEmpty name="record" property="altExamOpt.status">
							<bean:write name="record" property="altExamOpt.status"/>
						</logic:notEmpty>&nbsp;</td>
					</logic:notEmpty>	
				</tr>	
				</logic:iterate>
			</logic:notEmpty>	
		</sakai:flat_list>	
		<logic:greaterThan name="finalMarkConcessionForm" property="totalRecords" value="0">
			<sakai:instruction><bean:write name="finalMarkConcessionForm" property="totalStatusRecords" /> out of <bean:write name="finalMarkConcessionForm" property="totalRecords" /> records ready to be imported</sakai:instruction>
		</logic:greaterThan>
		<sakai:actions>	
			<logic:greaterThan name="finalMarkConcessionForm" property="totalStatusRecords" value="0">
				<html:submit  property="act" onclick="javascript:disabled=true;doAction();">
					<fmt:message key="button.upload"/>
				</html:submit>	
			</logic:greaterThan>	
			<logic:lessEqual name="finalMarkConcessionForm" property="totalStatusRecords" value="0">
				<html:submit  property="act" disabled="true">
					<fmt:message key="button.upload"/>
				</html:submit>	
			</logic:lessEqual>		
			<html:submit property="act">
				<fmt:message key="button.back"/>
			</html:submit>		
			<html:submit property="act">
				<fmt:message key="button.cancel"/>
			</html:submit>		
		</sakai:actions>		
	</html:form>
</sakai:html>