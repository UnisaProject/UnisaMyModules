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
		<html:hidden property="currentPage" value="batchCodAuthReq"/>
		<sakai:messages/>
		<sakai:messages message="true"/>
		<sakai:heading>		
			<fmt:message key="heading.requestBatchCodAuthorisation"/>	
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
		<sakai:group_heading>
			<fmt:message key="prompt.batchCodAuthRequestList"/> 
		</sakai:group_heading>						
		<sakai:flat_list>	
			<tr>				
				<th align="left" rowspan="2"><fmt:message key="import.select"/></th> 
				<th align="left" rowspan="2"><fmt:message key="import.studentNumber"/></th>
				<th align="left" rowspan="2"><fmt:message key="import.studentName"/></th>
				<th align="left" rowspan="2"><fmt:message key="import.reg"/></th>
				<th align="left" rowspan="2"><fmt:message key="import.finalMark"/></th>
				<th align="left" rowspan="2"><fmt:message key="import.yearMark"/></th>
				<th align="left" rowspan="2"><fmt:message key="import.primL"/></th>				
				<th align="left" rowspan="2"><fmt:message key="import.status"/></th>
				<th align="left" colspan="3" align="center"><fmt:message key="import.concession"/></th>
				<th align="left" rowspan="2"><fmt:message key="import.revisedFinalMark"/></th>
			</tr>			
			<tr>							
				<th align="left" ><fmt:message key="import.ass"/></th>
				<th align="left" ><fmt:message key="import.acSup"/></th>
				<th align="left"><fmt:message key="import.mark"/></th>
			</tr>		
			<logic:notEmpty name="finalMarkConcessionForm" property="listBatchCodAuthRecords">
				<logic:iterate name="finalMarkConcessionForm" property="listBatchCodAuthRecords" id="record" indexId="index">	
				<tr>
					<td  style="white-space:nowrap;align:left">
						<html:multibox property="indexNrSelectedAuth"><bean:write name="index"/></html:multibox>
					</td>				
					<td><bean:write name="record" property="studentNumber"/></td>
					<td><bean:write name="record" property="studentName"/></td>	
					<td><bean:write name="record" property="result.academicYear"/>/<bean:write name="record" property="result.semester"/></td>	
					<td><bean:write name="record" property="result.finalMark"/></td>
					<td><bean:write name="record" property="result.yearMark"/></td>
					<td><bean:write name="record" property="altExamOpt.responsibleLecturer.name"/></td> 
					<td><bean:write name="record" property="altExamOpt.status"/></td> 
					<td><span title="<bean:write name="record" property="altExamOpt.alternativeAssessOptDesc"/>"><bean:write name="record" property="altExamOpt.alternativeAssessOpt"/></span></td> 
					<td><span title="<bean:write name="record" property="altExamOpt.academicSupportDesc"/>"><bean:write name="record" property="altExamOpt.academicSupportOpt"/></span></td>
					<td><bean:write name="record" property="altExamOpt.concessionMark"/></td>
					<td><bean:write name="record" property="altExamOpt.finalMark"/></td>				
				</tr>	
				</logic:iterate>
			</logic:notEmpty>	
		</sakai:flat_list>
		<sakai:actions>	
			<html:submit property="act">
				<fmt:message key="button.selectAll"/>
			</html:submit>	
			<html:submit property="act">
				<fmt:message key="button.deSelectAll"/>
			</html:submit>
			<html:submit property="act">
				<fmt:message key="button.requestBatchAuth"/>
			</html:submit>
			<html:submit property="act">
				<fmt:message key="button.cancel"/>
			</html:submit>		
		</sakai:actions>		
	</html:form>
</sakai:html>