<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>

<fmt:setBundle basename="za.ac.unisa.lms.tools.assessmentcriteria.ApplicationResources"/>

<sakai:html>
<!-- Toolbar -->
	<sakai:tool_bar>
		<logic:notEqual  name="assessmentCriteriaForm"  property="assignmentAction" value="view">
			<html:link href="assessmentCriteria.do?act=displayRequestAuthorisation">
				<fmt:message key="link.requestAuthorization"/>
			</html:link>
		</logic:notEqual>
		<logic:equal  name="assessmentCriteriaForm"  property="status" value="AUTHREQ">
			<html:link href="assessmentCriteria.do?act=displayCancelAuthorisationRequest">
				<fmt:message key="link.cancelAuthorizationRequest"/>
			</html:link>
		</logic:equal>
	</sakai:tool_bar>
	<html:form action="/assessmentCriteria">
		<html:hidden property="currentPage" value="assessmentCriteria"/>
		<sakai:messages/>
		<sakai:messages message="true"/>
		<sakai:heading>
			<logic:notEqual  name="assessmentCriteriaForm"  property="assignmentAction" value="view">
				<fmt:message key="heading.assessmentCriteria"/>
			</logic:notEqual>
			<logic:equal  name="assessmentCriteriaForm"  property="assignmentAction" value="view">
				<fmt:message key="heading.view.assessmentCriteria"/>
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
			<fmt:message key="page.note2"/>
		</sakai:instruction>
		<sakai:heading>
			<fmt:message key="heading.assessmentCriteria.sectionA"/>
		</sakai:heading>
		<sakai:instruction>
			<fmt:message key="page.finalMarkComp.Note1"/>
		</sakai:instruction>
		<sakai:group_table>	
		<tr>
			<th><fmt:message key="page.component"/>&nbsp;</th>
			<th><fmt:message key="page.percentage"/>&nbsp;</th>
		</tr>
		<tr>
			<td><fmt:message key="page.yearMark"/>&nbsp;</td>
			
			<td>
				<logic:equal  name="assessmentCriteriaForm"  property="assignmentAction" value="view">
					<bean:write name="assessmentCriteriaForm" property="finalMarkComp.yearMarkComponent"/>
				</logic:equal>
				<logic:notEqual  name="assessmentCriteriaForm"  property="assignmentAction" value="view">
					<html:text name="assessmentCriteriaForm" property="finalMarkComp.yearMarkComponent" size="3" maxlength="3"/>
				</logic:notEqual>
			</td>
			<td>
				<logic:notEmpty name="assessmentCriteriaForm" property="finalMarkComp.yearMarkSubminimum">
					<logic:notEqual name="assessmentCriteriaForm" property="finalMarkComp.yearMarkSubminimum" value="0">
						<fmt:message key="page.subMinimum"/>
						<bean:write name="assessmentCriteriaForm" property="finalMarkComp.yearMarkSubminimum"/>%
					</logic:notEqual>&nbsp;
				</logic:notEmpty>
			</td>
		</tr>
		<tr>
			<td><fmt:message key="page.portfolio"/>&nbsp;</td>
			<td>
				<logic:equal  name="assessmentCriteriaForm"  property="assignmentAction" value="view">
					<bean:write name="assessmentCriteriaForm" property="finalMarkComp.portfolioComponent"/>
				</logic:equal>
				<logic:notEqual  name="assessmentCriteriaForm"  property="assignmentAction" value="view">
					<html:text name="assessmentCriteriaForm" property="finalMarkComp.portfolioComponent" size="3" maxlength="3"/>
				</logic:notEqual>
			</td>
			<td>
				<logic:notEmpty name="assessmentCriteriaForm" property="finalMarkComp.portfolioSubminimum">
					<logic:notEqual name="assessmentCriteriaForm" property="finalMarkComp.portfolioSubminimum" value="0">
						<fmt:message key="page.subMinimum"/>
						<bean:write name="assessmentCriteriaForm" property="finalMarkComp.portfolioSubminimum"/>%
					</logic:notEqual>&nbsp;
				</logic:notEmpty>
			</td>
		</tr>
		<tr>
			<td><fmt:message key="page.examination"/>&nbsp;</td>
			<td>
				<logic:equal  name="assessmentCriteriaForm"  property="assignmentAction" value="view">
					<bean:write name="assessmentCriteriaForm" property="finalMarkComp.examComponent"/>
				</logic:equal>	
				<logic:notEqual  name="assessmentCriteriaForm"  property="assignmentAction" value="view">
					<html:text name="assessmentCriteriaForm" property="finalMarkComp.examComponent" size="3" maxlength="3"/>
				</logic:notEqual>
			</td>
			<td>
				<logic:notEmpty name="assessmentCriteriaForm" property="finalMarkComp.examSubminimum">
					<logic:notEqual name="assessmentCriteriaForm" property="finalMarkComp.examSubminimum" value="0">
						<fmt:message key="page.subMinimum"/>
						<bean:write name="assessmentCriteriaForm" property="finalMarkComp.examSubminimum"/>%
					</logic:notEqual>&nbsp;
				</logic:notEmpty>
			</td>
		</tr>
		</sakai:group_table>
		<sakai:heading>
			<fmt:message key="heading.assessmentCriteria.sectionB"/>
		</sakai:heading>
		<sakai:group_table>
			<tr>
				<th colspan="3"><fmt:message key="page.examAdmission"/></th>
			</tr>
			<tr>
				<logic:equal name="assessmentCriteriaForm" property="studyUnit.autoExamAdmission" value="Y">
					<tr>
						<td><html:radio property="finalMarkComp.examAdmissionMethod" value="A1"></html:radio></td>
						<td>Auto examination admission</td>
					</tr>		
				</logic:equal>	
				<logic:notEqual name="assessmentCriteriaForm" property="studyUnit.autoExamAdmission" value="Y">	
					<logic:iterate name="assessmentCriteriaForm" property="listExamAdmissionMethod" id="record" indexId="index">
						<tr>
							<td><html:radio property="finalMarkComp.examAdmissionMethod" idName="record" value="code"></html:radio></td>
							<td><bean:write name="record" property="engDescription"/>									
								<logic:equal name="record" property="code" value="AM">
									<br/>										
									<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									<fmt:message key="page.examAdmission.NrAss"/>
									<html:text name="assessmentCriteriaForm" property="finalMarkComp.examAdmissionNrAss" size="3" maxlength="2"/>
								</logic:equal>
								<logic:equal name="record" property="code" value="YM">
									<br/>
									<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									<fmt:message key="page.examAdmission.YearMarkSubmin"/>										
									<html:text name="assessmentCriteriaForm" property="finalMarkComp.examAdmissionYearMarkSubMin" size="3" maxlength="2"/>
								</logic:equal>
							</td>								
							<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
						</tr>
					</logic:iterate>
				</logic:notEqual>							
			</tr>
		</sakai:group_table>		
		<sakai:actions>
			<logic:notEqual  name="assessmentCriteriaForm"  property="assignmentAction" value="view">
				<html:submit property="act">
						<fmt:message key="button.save"/>
				</html:submit>	
			</logic:notEqual>		
		</sakai:actions>
		<hr/>
		<sakai:heading>
			<fmt:message key="heading.assessmentCriteria.sectionC"/>
		</sakai:heading>
		<logic:notEmpty name="assessmentCriteriaForm" property="listAssignment">
			<logic:equal name="assessmentCriteriaForm"  property="assignmentAction" value="edit">
				<logic:equal name="assessmentCriteriaForm"  property="academicYear" value="2009">
					<sakai:instruction>
						<fmt:message key="page.note12"/>
					</sakai:instruction>
				</logic:equal>
				<sakai:instruction>
					<fmt:message key="page.note9"/>
				</sakai:instruction>
			</logic:equal>
			<logic:equal  name="assessmentCriteriaForm"  property="assignmentAction" value="view">
				<sakai:instruction>
					<fmt:message key="page.note10"/>
				</sakai:instruction>			
			</logic:equal>
		</logic:notEmpty>
		<sakai:flat_list>
			<tr>
				<th colspan="6"><fmt:message key="page.assignment.tableHeading.general"/></th>
				<th colspan="4"><fmt:message key="page.assignment.tableHeading.yearMark"/></th>
				<logic:equal name="assessmentCriteriaForm"  property="enableDelete" value="true">
					<th rowspan="2">&nbsp;<BR><fmt:message key="page.assignment.tableHeading.remove"/></th>	
				</logic:equal>
			</tr>
			<tr>
				<th><fmt:message key="page.assignment.tableHeading.number"/></th>
				<th><fmt:message key="page.assignment.tableHeading.group"/></th>
				<th><fmt:message key="page.assignment.tableHeading.format"/></th>
				<th><fmt:message key="page.assignment.tableHeading.uniqueNumber"/></th>
				<th><fmt:message key="page.assignment.tableHeading.dueDate"/></th>
				<!--<th><fmt:message key="page.assignment.tableHeading.subsidyAssignment"/></th> -->
				<th><fmt:message key="page.assignment.tableHeading.type"/></th>
				<th><fmt:message key="page.assignment.tableHeading.optionality"/></th>
				<th><fmt:message key="page.assignment.tableHeading.normal"/></th>
				<th><fmt:message key="page.assignment.tableHeading.repeat"/></th>
				<th><fmt:message key="page.assignment.tableHeading.aegrotat"/></th>
			</tr>
			<logic:iterate name="assessmentCriteriaForm" property="listAssignment" id="record" indexId="index">
				<tr>
					<td><bean:write name="record" property="number"/>
					<logic:equal  name="assessmentCriteriaForm"  property="assignmentAction" value="view">
						<html:link href="assessmentCriteria.do?act=displayAssignment" paramName="record" paramProperty="number" paramId="selectAssignment">                                   							
							<fmt:message key="page.linkViewText"/>
						</html:link>
					</logic:equal>
					<logic:notEqual  name="assessmentCriteriaForm"  property="assignmentAction" value="view">
						<html:link href="assessmentCriteria.do?act=displayAssignment" paramName="record" paramProperty="number" paramId="selectAssignment">                                   							
							<fmt:message key="page.linkEditText"/>
						</html:link>
					</logic:notEqual>
					</td>
					<td>
						<logic:equal name="record" property="group" value="F">
							<fmt:message key="page.assignment.record.group.formative"/>
						</logic:equal>
						<logic:equal name="record" property="group" value="S">
							<fmt:message key="page.assignment.record.group.summative"/>
						</logic:equal>
					</td>
					<td>
						<logic:equal name="record" property="format" value="A">
							<fmt:message key="page.assignment.record.format.mcq"/>
						</logic:equal>
						<logic:equal name="record" property="format" value="H">
							<fmt:message key="page.assignment.record.format.written"/>
						</logic:equal>
						<logic:equal name="record" property="format" value="BL">
							<fmt:message key="page.assignment.record.format.blog"/>
						</logic:equal>
						<logic:equal name="record" property="format" value="DF">
							<fmt:message key="page.assignment.record.format.forum"/>
						</logic:equal>
						<logic:equal name="record" property="format" value="SA">
							<fmt:message key="page.assignment.record.format.samigo"/>
						</logic:equal>
						<logic:equal name="record" property="format" value="XA">
							<fmt:message key="page.assignment.record.format.external"/>
						</logic:equal>
						<logic:equal name="record" property="format" value="MS">
							<fmt:message key="page.assignment.record.format.myunisa"/>
						</logic:equal>
						<logic:equal name="record" property="format" value="OR">
							<fmt:message key="page.assignment.record.format.oral"/>
						</logic:equal>
					</td>	
					<td>
						<bean:write name="record" property="uniqueNumber"/>						
					</td>
					<td><bean:write name="record" property="dueDate"/></td>					
					<!-- <td><bean:write name="record" property="type"/></td> -->
					<td>
						<logic:equal name="record" property="type" value="I">Individual</logic:equal>
						<logic:equal name="record" property="type" value="G">Group</logic:equal>
						<logic:equal name="record" property="type" value="PF">Portfolio</logic:equal>
						<logic:equal name="record" property="type" value="PJ">Project</logic:equal>
						<logic:equal name="record" property="type" value="PC">Practical</logic:equal>
						<logic:equal name="record" property="type" value="R">Peer Report</logic:equal>
						<logic:equal name="record" property="type" value="S">Peer</logic:equal>
						<logic:equal name="record" property="type" value="T">Test</logic:equal>
						<logic:equal name="record" property="type" value="O">Online Examination</logic:equal>
					</td>
					<logic:lessThan name="record" property="number" value="91">
						<td><bean:write name="record" property="optionality"/></td>
						<td><bean:write name="record" property="normalWeight"/></td>
						<td><bean:write name="record" property="repeatWeight"/></td>
						<td><bean:write name="record" property="aegrotatWeight"/></td>
					</logic:lessThan>
					<logic:greaterEqual name="record" property="number" value="91">
						<logic:lessEqual name="record" property="number" value="95">
							<td>n.a.</td>
							<td>n.a.</td>
							<td>n.a.</td>
							<td>n.a.</td>
						</logic:lessEqual>
					</logic:greaterEqual>
					<logic:greaterThan name="record" property="number" value="95">
						<td><bean:write name="record" property="optionality"/></td>
						<td><bean:write name="record" property="normalWeight"/></td>
						<td><bean:write name="record" property="repeatWeight"/></td>
						<td><bean:write name="record" property="aegrotatWeight"/></td>
					</logic:greaterThan>
					<logic:notEqual  name="assessmentCriteriaForm"  property="assignmentAction" value="view">
						<logic:equal  name="record"  property="captureOnStudentSystem" value="false">
							<td><html:multibox property="indexSelectedRemoveAssignment"><bean:write name="index"/></html:multibox></td>
						</logic:equal>
					</logic:notEqual>
				</tr>
			</logic:iterate>
		</sakai:flat_list>
		
		<sakai:actions>
			<logic:notEqual  name="assessmentCriteriaForm"  property="assignmentAction" value="view">
				<html:submit property="act">
						<fmt:message key="button.add"/>
				</html:submit>	
				<html:submit property="act">
						<fmt:message key="button.add.survey"/>
				</html:submit>	
				<logic:equal name="assessmentCriteriaForm"  property="enableDelete" value="true">
					<html:submit property="act">
							<fmt:message key="button.remove"/>
					</html:submit>
				</logic:equal>
			</logic:notEqual>		
		</sakai:actions>
		<sakai:instruction>
			<fmt:message key="page.note15"/>
		</sakai:instruction>
		<hr/>
		<sakai:actions>
			<logic:equal  name="assessmentCriteriaForm"  property="siteId" value="Course Admin">
				<html:submit property="act">
						<fmt:message key="button.cancel"/>
				</html:submit>		
			</logic:equal>	
		</sakai:actions>	
		<script language="javascript">
		function submitForm(){	
			document.forms["assessmentCriteriaForm"].submit();			
		}			
		</script>		
	</html:form>
</sakai:html>