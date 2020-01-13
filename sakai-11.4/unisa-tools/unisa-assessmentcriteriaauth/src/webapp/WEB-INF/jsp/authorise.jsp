<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>

<fmt:setBundle basename="za.ac.unisa.lms.tools.assessmentcriteriaauth.ApplicationResources"/>

<sakai:html>
	<html:form action="/assessmentCriteriaAuth">
		<html:hidden property="currentPage" value="authorise"/>
		<sakai:messages/>
		<sakai:messages message="true"/>
		<sakai:heading>
			<fmt:message key="heading.assessmentCriteriaAuth"/>
		</sakai:heading>
		<sakai:instruction>
			<fmt:message key="page.note1"/>&nbsp;<fmt:message key="page.mandatory"/>
		</sakai:instruction>
		<sakai:group_table>	
			<tr>
				<td><fmt:message key="page.studyUnit"/>&nbsp;</td>
				<td><bean:write name="assessmentCriteriaAuthForm" property="selectedCourse.courseCode"/></td>
				<td><bean:write name="assessmentCriteriaAuthForm" property="studyUnit.description"/></td>
			</tr>
			<tr>
				<td><fmt:message key="page.yearSemester"/>&nbsp;</td>			
				<td><bean:write name="assessmentCriteriaAuthForm" property="selectedCourse.year"/>/<bean:write name="assessmentCriteriaAuthForm" property="selectedCourse.semesterType"/></td>
				<td>&nbsp;</td>
			</tr>			
		</sakai:group_table>	
		<hr/>
		<sakai:heading>
			<fmt:message key="par.heading.finalMarkComp"/>
		</sakai:heading>
		<sakai:group_table>
			<tr>
				<th><fmt:message key="page.component"/>&nbsp;</th>
				<th><fmt:message key="page.percentage"/>&nbsp;</th>
			</tr>
			<logic:notEqual name="assessmentCriteriaAuthForm" property="finalMarkComp.yearMarkComponent" value="0">
				<tr>
					<td><fmt:message key="page.yearMark"/>&nbsp;</td>			
					<td><bean:write name="assessmentCriteriaAuthForm" property="finalMarkComp.yearMarkComponent"/>
						<logic:notEmpty name="assessmentCriteriaAuthForm" property="finalMarkComp.yearMarkSubminimum">
							<logic:notEqual name="assessmentCriteriaAuthForm" property="finalMarkComp.yearMarkSubminimum" value="0">
								(<fmt:message key="page.subMinimum"/>
								<bean:write name="assessmentCriteriaAuthForm" property="finalMarkComp.yearMarkSubminimum"/>%)
							</logic:notEqual>
						</logic:notEmpty>
					</td>
				</tr>
			</logic:notEqual>
			<logic:notEmpty name="assessmentCriteriaAuthForm" property="finalMarkComp.portfolioComponent">
				<logic:notEqual name="assessmentCriteriaAuthForm" property="finalMarkComp.portfolioComponent" value="0">
					<tr>
						<td><fmt:message key="page.portfolio"/>&nbsp;</td>			
						<td><bean:write name="assessmentCriteriaAuthForm" property="finalMarkComp.portfolioComponent"/>
							<logic:notEmpty name="assessmentCriteriaAuthForm" property="finalMarkComp.portfolioSubminimum">
								<logic:notEqual name="assessmentCriteriaAuthForm" property="finalMarkComp.portfolioSubminimum" value="0">
									(<fmt:message key="page.subMinimum"/>
									<bean:write name="assessmentCriteriaAuthForm" property="finalMarkComp.portfolioSubminimum"/>%)
								</logic:notEqual>
							</logic:notEmpty>
						</td>
					</tr>
				</logic:notEqual>
			</logic:notEmpty>
			<logic:notEqual name="assessmentCriteriaAuthForm" property="finalMarkComp.examComponent" value="0">
				<tr>
					<td><fmt:message key="page.examination"/>&nbsp;</td>	
					<td><bean:write name="assessmentCriteriaAuthForm" property="finalMarkComp.examComponent"/>
						<logic:notEmpty name="assessmentCriteriaAuthForm" property="finalMarkComp.examSubminimum">
							<logic:notEqual name="assessmentCriteriaAuthForm" property="finalMarkComp.examSubminimum" value="0">
								(<fmt:message key="page.subMinimum"/>
								<bean:write name="assessmentCriteriaAuthForm" property="finalMarkComp.examSubminimum"/>%)
							</logic:notEqual>
					</logic:notEmpty>
					</td>					
				</tr>
			</logic:notEqual>				
		</sakai:group_table>			
		<hr/>
		<sakai:heading>
			<fmt:message key="par.heading.examAdmission"/>
		</sakai:heading>
		<sakai:group_table>
			<tr>
				<td colspan="3">
					<bean:write name="assessmentCriteriaAuthForm" property="admissionMethod"/>
				</td>
			</tr>
			<logic:equal name="assessmentCriteriaAuthForm" property="finalMarkComp.examAdmissionMethod" value="AM">	
				<tr>
					<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
					<td><fmt:message key="page.admissionMethod.nrOfAssign"/></td>
					<td><bean:write name="assessmentCriteriaAuthForm" property="finalMarkComp.examAdmissionNrAss"/></td>
				</tr>	
			</logic:equal>	
			<logic:equal name="assessmentCriteriaAuthForm" property="finalMarkComp.examAdmissionMethod" value="YM">	
				<tr>	
					<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
					<td><fmt:message key="page.admissionMethod.yrmrkSubmin"/></td>
					<td><bean:write name="assessmentCriteriaAuthForm" property="finalMarkComp.examAdmissionYearMarkSubMin"/></td>
				</tr>	
			</logic:equal>					
		</sakai:group_table>	
		<hr/>
		<sakai:heading>
			<fmt:message key="par.heading.assignment"/>
		</sakai:heading>
		<sakai:instruction>
			<fmt:message key="page.note2"/>
		</sakai:instruction>
		<sakai:flat_list>
			<tr>
				<th colspan="6"><fmt:message key="page.assignment.tableHeading.general"/></th>
				<th colspan="4"><fmt:message key="page.assignment.tableHeading.yearMark"/></th>				
			</tr>
			<tr>
				<th><fmt:message key="page.assignment.tableHeading.number"/></th>
				<th><fmt:message key="page.assignment.tableHeading.group"/></th>
				<th><fmt:message key="page.assignment.tableHeading.format"/></th>
				<th><fmt:message key="page.assignment.tableHeading.uniqueNumber"/></th>
				<th><fmt:message key="page.assignment.tableHeading.dueDate"/></th>
				<!--<th><fmt:message key="page.assignment.tableHeading.subsidyAssignment"/></th>-->
				<th><fmt:message key="page.assignment.tableHeading.type"/></th>
				<th><fmt:message key="page.assignment.tableHeading.optionality"/></th>
				<th><fmt:message key="page.assignment.tableHeading.normal"/></th>
				<th><fmt:message key="page.assignment.tableHeading.repeat"/></th>
				<th><fmt:message key="page.assignment.tableHeading.aegrotat"/></th>
			</tr>
			<logic:iterate name="assessmentCriteriaAuthForm" property="listAssignment" id="record" indexId="index">
				<tr>
					<td><bean:write name="record" property="number"/>
						<html:link href="assessmentCriteriaAuth.do?action=displayAssignment" paramName="record" paramProperty="number" paramId="selectAssignment" onclick="javascript:submitForm();">                                   							
							<fmt:message key="page.linkViewText"/>
						</html:link>
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
					<!-- 
					<td> 
						<logic:equal name="record" property="subsidyAssignment" value="Y">
							<fmt:message key="page.assignment.record.subsidyAssignment.yes"/>
						</logic:equal>
						<logic:notEqual name="record" property="subsidyAssignment" value="Y">
							<fmt:message key="page.assignment.record.subsidyAssignment.no"/>
						</logic:notEqual>
					</td>
					 -->
					<!--<td><bean:write name="record" property="type"/></td>-->
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
					<td><bean:write name="record" property="optionality"/></td>
					<td><bean:write name="record" property="normalWeight"/></td>
					<td><bean:write name="record" property="repeatWeight"/></td>
					<td><bean:write name="record" property="aegrotatWeight"/></td>
				</tr>
			</logic:iterate>
		</sakai:flat_list>
		<sakai:group_table>
			<tr>
				<td colspan="2">
					<sakai:instruction>
						<fmt:message key="page.note3"/>
					</sakai:instruction>
				</td>
			</tr>
			<tr>
				<td><fmt:message key="page.comment"/></td>
				<td><html:textarea name="assessmentCriteriaAuthForm" property="comment" cols="50" rows="5"/></td>
			</tr>
		</sakai:group_table>		
		<sakai:actions>
			<html:submit property="action">
				<fmt:message key="button.authorise"/>
			</html:submit>	
			<html:submit property="action">
				<fmt:message key="button.sendBack"/>
			</html:submit>
			<html:submit property="action">
				<fmt:message key="button.cancel"/>
			</html:submit>
		</sakai:actions>
		<script language="javascript">
		function submitForm(){	
			document.forms["assessmentCriteriaAuthForm"].submit();			
		}			
		</script>		
	</html:form>
</sakai:html>