<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>

<fmt:setBundle basename="za.ac.unisa.lms.tools.assessmentcriteria.ApplicationResources"/>

<sakai:html>
	<html:form action="/assessmentCriteria" onreset='<%="javascript:resetForm();"%>'>
		<!--<html:hidden property="currentPage" value="mcqMarking"/>-->
		<sakai:messages/>
		<sakai:messages message="true"/>
		<sakai:heading>
			<logic:notEqual  name="assessmentCriteriaForm"  property="assignmentAction" value="view">
				<fmt:message key="heading.mcq.markingDetails"/>
			</logic:notEqual>
			<logic:equal  name="assessmentCriteriaForm"  property="assignmentAction" value="view">
				<fmt:message key="heading.view.mcq.markingDetails"/>
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
		<sakai:group_table>	
		<tr>
			<td><fmt:message key="page.assignment.numberOfQuestions"/>&nbsp;<fmt:message key="page.mandatory"/></td>
			<td>
				<logic:notEqual name="assessmentCriteriaForm" property="assignmentAction" value="view">
					<html:text name="assessmentCriteriaForm" property="assignment.numberQuestions" size="3" maxlength="3"/>
				</logic:notEqual>
				<logic:equal name="assessmentCriteriaForm" property="assignmentAction" value="view">
					<html:text name="assessmentCriteriaForm" property="assignment.numberQuestions" size="3" maxlength="3" disabled="true"/>
				</logic:equal>
			</td>
		</tr>		
		<tr>
			<td><fmt:message key="page.assignment.negativeMarking"/>&nbsp;</td>	
			<td>			
				<sakai:group_table>
					<logic:iterate name="assessmentCriteriaForm" property="listNegativeMarking" id="record" indexId="index" >
						<tr>
							<logic:notEqual name="assessmentCriteriaForm" property="assignmentAction" value="view">
								<td><html:radio property="negativeMarkingYesNoFlag" idName="record" value="code" onclick='<%="javascript:clickedNegativeMarking(this.form,this.name," + index.toString() +");"%>'></html:radio></td>
							</logic:notEqual>
							<logic:equal name="assessmentCriteriaForm" property="assignmentAction" value="view">
								<td><html:radio property="negativeMarkingYesNoFlag" idName="record" value="code" disabled="true"></html:radio></td>
							</logic:equal>
							<td><bean:write name="record" property="engDescription"/></td>
						</tr>
					</logic:iterate>
				</sakai:group_table>	
			</td>					
		</tr>
		<tr>
			<td colspan="2">
				<sakai:instruction>
					<fmt:message key="page.note11"/>&nbsp;
				</sakai:instruction>
			</td>
		</tr>
		<tr>
			<logic:notEqual name="assessmentCriteriaForm" property="assignmentAction" value="view">
				<logic:equal name="assessmentCriteriaForm" property="negativeMarkingYesNoFlag" value="Y">
					<td colspan="2"><fmt:message key="page.assignment.negativeMarkingFactora"/>
						<html:text name="assessmentCriteriaForm" property="assignment.negativeMarkingFactor" size="1" maxlength="1"/>
						<fmt:message key="page.assignment.negativeMarkingFactorb"/>
					</td>
				</logic:equal>
				<logic:equal name="assessmentCriteriaForm" property="negativeMarkingYesNoFlag" value="N">
					<td colspan="2"><fmt:message key="page.assignment.negativeMarkingFactora"/>
						<html:text name="assessmentCriteriaForm" property="assignment.negativeMarkingFactor" size="1" maxlength="1" disabled="true"/>
						<fmt:message key="page.assignment.negativeMarkingFactorb"/>
					</td>
				</logic:equal>
			</logic:notEqual>
			<logic:equal name="assessmentCriteriaForm" property="assignmentAction" value="view">
				<td colspan="2"><fmt:message key="page.assignment.negativeMarkingFactora"/>
					<html:text name="assessmentCriteriaForm" property="assignment.negativeMarkingFactor" size="1" maxlength="1" disabled="true"/>
					<fmt:message key="page.assignment.negativeMarkingFactorb"/>
				</td>
			</logic:equal>
		</tr>
		</sakai:group_table>	
		<sakai:actions>
			<html:submit property="act">
					<fmt:message key="button.continue"/>
			</html:submit>
			<html:submit property="act">
					<fmt:message key="button.back"/>
			</html:submit>
			<html:submit property="act">
					<fmt:message key="button.cancel"/>
			</html:submit>				
		</sakai:actions>
		<script language="javascript">
		function resetForm(){
		  alert("in reset form");
		}
		function clickedNegativeMarking(Form,Radio,index){	
			document.forms["assessmentCriteriaForm"].elements["assignment.negativeMarkingFactor"].disabled=false;	
			for (var i=0;i<Form[Radio].length;i++){
				if (Form[Radio][i].checked){
					if (Form[Radio][i].value=="N"){
						document.forms["assessmentCriteriaForm"].elements["assignment.negativeMarkingFactor"].value="0";
						document.forms["assessmentCriteriaForm"].elements["assignment.negativeMarkingFactor"].disabled=true;					
	                	i=Form[Radio].length; 		    			    			    	
					}	
				}		   	
		    }
		}		
		</script>
	</html:form>
</sakai:html>