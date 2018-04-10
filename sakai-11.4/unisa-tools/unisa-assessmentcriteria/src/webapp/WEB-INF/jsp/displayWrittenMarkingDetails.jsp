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
		<!--<html:hidden property="currentPage" value="writtenMarking"/>-->
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
		<sakai:instruction>
			<fmt:message key="page.note7"/>
		</sakai:instruction>
		<sakai:instruction>
			<fmt:message key="page.note8"/>
			<html:link href="https://my.unisa.ac.za/portal/tool/3a98f3d0-2c1d-420c-8054-0dad3fb84dba?pageName=%2Fsite%2FHelp+and+Support%2Fstaff+members+able+to+link+courses&action=view&panel=Main&realm=%2Fsite%2FHelp+and+Support" target="_blank">
				<fmt:message key="link.clickHere"/>
			</html:link>.
		</sakai:instruction>
		<sakai:heading>
			<fmt:message key="page.marker.tableHeading"/>
		</sakai:heading>
		<sakai:flat_list>
			<tr>
				<th><fmt:message key="page.marker.tableHeading.mark%"/></th>
				<th><fmt:message key="page.marker.tableHeading.personnelNr"/></th>
				<th><fmt:message key="page.marker.tableHeading.name"/></th>
				<th><fmt:message key="page.marker.tableHeading.contactNr"/></th>
				<th><fmt:message key="page.marker.tableHeading.department"/></th>
				<th><fmt:message key="page.marker.tableHeading.status"/></th>
				<logic:equal name="assessmentCriteriaForm" property="assignment.studentSpecifyLang" value="Y">
					<th><fmt:message key="page.marker.tableHeading.language"/></th>
				</logic:equal>
			</tr>
			<logic:iterate name="assessmentCriteriaForm" property="assignment.listMarkers" id="record" indexId="index">
				<tr>
					<td>
						<logic:notEqual name="assessmentCriteriaForm" property="assignmentAction" value="view">
							<html:text name="assessmentCriteriaForm" property='<%= "assignment.listMarkers[" + index.toString() + "].markPercentage"%>' size="4" maxlength="3"/>
						</logic:notEqual>
						<logic:equal name="assessmentCriteriaForm" property="assignmentAction" value="view">
							<html:text name="assessmentCriteriaForm" property='<%= "assignment.listMarkers[" + index.toString() + "].markPercentage"%>' size="4" maxlength="3" disabled="true"/>
						</logic:equal>
					</td>	
					<td><bean:write name="record" property="person.personnelNumber"/></td>
					<td><bean:write name="record" property="person.name"/></td>
					<td><bean:write name="record" property="person.contactNumber"/></td>
					<td><bean:write name="record" property="departmentDesc"/></td>
					<td><bean:write name="record" property="status"/></td>
					<td>
						<logic:equal name="assessmentCriteriaForm" property="assignment.studentSpecifyLang" value="Y">
							<logic:notEqual name="assessmentCriteriaForm" property="assignmentAction" value="view">
								<html:select name="assessmentCriteriaForm" property='<%="assignment.listMarkers[" + index.toString() + "].languageSelection"%>' multiple="true" size="3">
									<html:optionsCollection name="assessmentCriteriaForm" property="assignment.listLanguage" value="otherLangDesc" label="otherLangDesc"/>
								</html:select> 
							</logic:notEqual> 						
							<logic:equal name="assessmentCriteriaForm" property="assignmentAction" value="view">
								<logic:iterate name="assessmentCriteriaForm" property='<%="assignment.listMarkers[" + index.toString() + "].listMarkLanguages"%>' id="rec" indexId="ind">
									<bean:write name="rec"/><br/>
								</logic:iterate>
							</logic:equal> 					
						</logic:equal>
					</td>
			</tr>
			</logic:iterate>
		</sakai:flat_list>
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
	</html:form>
</sakai:html>