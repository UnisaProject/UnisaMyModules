<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>

<fmt:setBundle basename="za.ac.unisa.lms.tools.assessmentcriteria.ApplicationResources"/>
<script language="javascript">
	function doAction() {
		document.assessmentCriteriaForm.action = 'assessmentCriteria.do?act=saveData';  
		document.assessmentCriteriaForm.submit();
	}
</script>
<sakai:html>
	<html:form action="/assessmentCriteria">
		<!--<html:hidden property="currentPage" value="mcqMemorandum"/>-->
		<sakai:messages/>
		<sakai:messages message="true"/>
		<sakai:heading>
			<logic:notEqual  name="assessmentCriteriaForm"  property="assignmentAction" value="view">
				<fmt:message key="heading.mcq.memorandum"/>
			</logic:notEqual>
			<logic:equal  name="assessmentCriteriaForm"  property="assignmentAction" value="view">
				<fmt:message key="heading.view.mcq.memorandum"/>
			</logic:equal>					
		</sakai:heading>
		<sakai:instruction>
			<fmt:message key="page.note1"/>&nbsp;<fmt:message key="page.mandatory"/>
		</sakai:instruction>
		<sakai:group_table>	
			<tr>
				<td><fmt:message key="page.studyUnit"/>&nbsp;</td>
				<td><bean:write name="assessmentCriteriaForm" property="studyUnit.code"/></td>
				<td><bean:write name="assessmentCriteriaForm" property="studyUnit.description"/></td>
			</tr>
			<tr>
				<td><fmt:message key="page.yearSemester"/>&nbsp;</td>			
				<td><bean:write name="assessmentCriteriaForm" property="academicYear"/>/<bean:write name="assessmentCriteriaForm" property="semesterType"/></td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td><fmt:message key="page.assignment"/>&nbsp;</td>	
				<td colspan="2"><bean:write name="assessmentCriteriaForm" property="assignment.number"/>&nbsp;&nbsp;(<fmt:message key="page.assignment.dueDate"/>:&nbsp;<bean:write name="assessmentCriteriaForm" property="assignment.dueDate"/>)</td>	
			</tr>
			<tr>
				<td><fmt:message key="page.status"/>&nbsp;</td>			
				<td><bean:write name="assessmentCriteriaForm" property="statusDesc"/></td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td><fmt:message key="page.onlineMethod"/>&nbsp;</td>	
				<td colspan="2"><bean:write name="assessmentCriteriaForm" property="onlineMethod"/></td>
			</tr>
		</sakai:group_table>	
		<hr/>
		<logic:equal name="assessmentCriteriaForm" property="survey" value="false">
			<sakai:group_table>	
			<tr>
				<td colspan="2">
					<sakai:instruction>
						<fmt:message key="page.note4a"/><BR>
						<fmt:message key="page.note4b"/><BR>
						<fmt:message key="page.note4c"/>
					</sakai:instruction>
				</td>
			</tr>
			<tr>
				<td><fmt:message key="page.assignment.prelimMarkingDate"/>&nbsp;</td>
				<td><bean:write name="assessmentCriteriaForm" property="assignment.prelimMarkingDate"/></td>
			</tr>
			<tr>
				<td><fmt:message key="page.assignment.finalMarkingDate"/>&nbsp;</td>
				<td><bean:write name="assessmentCriteriaForm" property="assignment.finalMarkingDate"/></td>
			</tr>
			</sakai:group_table>
		</logic:equal>
		<logic:equal name="assessmentCriteriaForm" property="survey" value="true">
				<sakai:instruction>
						<fmt:message key="page.note16"/>
				</sakai:instruction>		
		</logic:equal>
		<sakai:group_table>			
			<tr>
				<th><fmt:message key="page.question"/></th>
				<th><fmt:message key="page.answer"/></th>
				<th colspan="10" align="center"><fmt:message key="page.answerNote"/></th>
			</tr>
			<logic:iterate name="assessmentCriteriaForm" property="assignment.listAnswer" id="quesRecord"  indexId="index" >
				<tr>
					<td><bean:write name="quesRecord" property="question"/>.</td>
					<td>[..<html:text name="assessmentCriteriaForm" property='<%="listDisplayAnswer[" + index.toString() + "]"%>' disabled="true" size="1" maxlength="1"/>..]</td>
					<logic:iterate name="assessmentCriteriaForm" property="listPossibleMCQAnswers" id="answerRecord">
						<logic:notEqual name="assessmentCriteriaForm" property="assignmentAction" value="view">
							<td><html:radio name="assessmentCriteriaForm" property='<%="assignment.listAnswer[" + index.toString() + "].answer"%>' idName="answerRecord" value="code" onclick='<%="javascript:clicked(this.form,this.name," + index.toString() +");"%>'></html:radio></td>
						</logic:notEqual>
						<logic:equal name="assessmentCriteriaForm" property="assignmentAction" value="view">
							<td><html:radio name="assessmentCriteriaForm" property='<%="assignment.listAnswer[" + index.toString() + "].answer"%>' idName="answerRecord" value="code" disabled="true"></html:radio></td>
						</logic:equal>						
						<td><bean:write name="answerRecord" property="engDescription"/></td>
					</logic:iterate>
				</tr>
			</logic:iterate>
		</sakai:group_table>	
		<sakai:actions>
			<logic:notEqual name="assessmentCriteriaForm" property="assignmentAction" value="view">
				<html:submit property="act" onclick="javascript:disabled=true;doAction();">
						<fmt:message key="button.save"/>
				</html:submit>
			</logic:notEqual>
			<html:submit property="act">
					<fmt:message key="button.back"/>
			</html:submit>
			<html:submit property="act">
					<fmt:message key="button.cancel"/>
			</html:submit>				
		</sakai:actions>
		<script language="javascript">
		function clicked(Form,Radio,index){			
			for (var i=0;i<Form[Radio].length;i++){
				if (Form[Radio][i].checked){
					document.forms["assessmentCriteriaForm"].elements["listDisplayAnswer["+index+"]"].value=Form[Radio][i].value;	   				     
	                i=Form[Radio].length; 		    			    			    	
				}			   	
		    }
		}		
	</script>
	</html:form>
</sakai:html>