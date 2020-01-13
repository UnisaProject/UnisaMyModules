<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>

<fmt:setBundle basename="za.ac.unisa.lms.tools.mdrpm.ApplicationResources"/>

<sakai:html>
	<html:form action="/mdRpm">
	<html:hidden property="currentPage" value="reviewReferBack"/>
		<sakai:messages/>
		<sakai:messages message="true"/>
		<sakai:heading>
			<fmt:message key="heading.reviewReferBack"/>
		</sakai:heading>
		<sakai:flat_list>
			<tr>
				<th><fmt:message key="heading.general.rpm.student"/></th>
			</tr>
			<tr>			
				<sakai:group_table>
					<tr>
						<td><fmt:message key="page.prompt.studentNr"/></td>
						<td><bean:write name="mdRpmForm" property="rpm.student.number"/></td>
					</tr>
					<tr>
						<td><fmt:message key="page.prompt.studentName"/></td>
						<td><bean:write name="mdRpmForm" property="rpm.student.printName"/></td>
					</tr>
					<tr>
						<td><fmt:message key="page.prompt.qualification"/></td>
						<td><bean:write name="mdRpmForm" property="rpm.qualification.code"/>&nbsp;(<bean:write name="mdRpmForm" property="rpm.qualification.description"/>)</td>
					</tr>
					<tr>
						<td><fmt:message key="page.prompt.speciality"/></td>
						<td><bean:write name="mdRpmForm" property="rpm.qualification.speciality.code"/>&nbsp;
							<logic:notEmpty name="mdRpmForm" property="rpm.qualification.speciality.description">(<bean:write name="mdRpmForm" property="rpm.qualification.speciality.description"/>)</logic:notEmpty>
						</td>
					</tr>
					<tr>
						<td><fmt:message key="page.prompt.studyUnit"/></td>
						<td><bean:write name="mdRpmForm" property="rpm.studyunit.code"/></td>
					</tr>
					<tr>
						<td><fmt:message key="page.prompt.cellphoneNumper"/></td>
						<td><bean:write name="mdRpmForm" property="rpm.student.contactInfo.cellNumber"/></td>
					</tr>
					<tr>
						<td><fmt:message key="page.prompt.emailAddress"/></td>
						<td><bean:write name="mdRpmForm" property="rpm.student.contactInfo.emailAddress"/></td>
					</tr>
				</sakai:group_table>
			</tr>	
		</sakai:flat_list>	
		<sakai:flat_list>
			<bean:define id="currentPage" value="reviewReferBack"/>
			<tr>
				<th><fmt:message key="heading.general.rpm.details"/></th>
			</tr>
			<tr>
				<sakai:group_table>		
					<tr>
						<th><fmt:message key="heading.table.title"/></th>
					</tr>	
					<tr>		
						<td>
							<logic:empty name="mdRpmForm" property="rpm.title">
								<html:link href="mdRpm.do?act=titleMnt" paramId="calledFrom" paramName="currentPage"><fmt:message key="link.addTitle"/></html:link>
							</logic:empty>
							<logic:notEmpty name="mdRpmForm" property="rpm.title">
								<html:link href="mdRpm.do?act=titleMnt" paramId="calledFrom" paramName="currentPage"><bean:write name="mdRpmForm" property="rpm.title"/></html:link>
							</logic:notEmpty>		
						</td>		
						<!-- <td><bean:write name="mdRpmForm" property="rpm.title"/></td> -->
					</tr>
				</sakai:group_table>	
			</tr>	
			<tr>				
				<sakai:group_table>
					<tr>
						<th colspan="4"><fmt:message key="heading.table.promotor"/></th>
					</tr>					
					<logic:iterate name="mdRpmForm" property="rpm.promotorList" id="record" indexId="index">
						<tr>							
							<td><logic:equal name="record" property="isSupervisor" value="true">Supervisor</logic:equal>
								<logic:equal name="record" property="isSupervisor" value="false">Assistant</logic:equal>
							</td>
							<td><html:link href="mdRpm.do?act=supervisorMnt" paramId="calledFrom" paramName="currentPage"><bean:write name="record" property="person.name"/>&nbsp;(<bean:write name="record" property="person.personnelNumber"/>)</html:link></td>	
							<td><fmt:message key="column.promotor.tel"/>&nbsp;<bean:write name="record" property="person.contactNumber"/></td>
							<td><fmt:message key="column.promotor.email"/>&nbsp;<bean:write name="record" property="person.emailAddress"/></td>								
						</tr>
					</logic:iterate>					
				</sakai:group_table>	
			<tr>
				<sakai:group_table>
					<tr>
						<th colspan="2"><fmt:message key="heading.table.proposedResult"/></th>
					</tr>
					<td>
						<bean:write name="mdRpmForm" property="currentResult.engDescription"/>
					</td>
				</sakai:group_table>
			</tr>				
		</sakai:flat_list>
		<sakai:flat_list>
			<tr>
				<th><fmt:message key="heading.general.rpm.signoffRoute"/></th>
			</tr>
			<tr>
				<sakai:group_table>
					<logic:empty name="mdRpmForm" property="rpm.routingList">
						<tr>
							<td colspan="4"><fmt:message key="note.noRoutingList"/></td>
						</tr>
					</logic:empty>
					<logic:notEmpty name="mdRpmForm" property="rpm.routingList">
						<tr>
							<th colspan="4"><fmt:message key="instruction.route"/></th>
						</tr>
						<tr>											
							<th><fmt:message key="column.route.level"/></th>
							<th>&nbsp;</th>	
							<th>&nbsp;</th>
							<th>&nbsp;</th>
						</tr>
						<logic:iterate name="mdRpmForm" property="rpm.routingList" id="record" indexId="index">
						<tr>					
							<td><logic:equal name="record" property="level" value="F">Final</logic:equal>
								<logic:notEqual name="record" property="level" value="F"><bean:write name="record" property="level"/></logic:notEqual>
							</td>							
							<td><bean:write name="record" property="person.name"/>&nbsp;(<bean:write name="record" property="person.personnelNumber"/>)</td>
							<td><fmt:message key="column.route.tel"/>&nbsp;<bean:write name="record" property="person.contactNumber"/></td>
							<td><fmt:message key="column.route.email"/>&nbsp;<bean:write name="record" property="person.emailAddress"/></td>														
						</tr>
					</logic:iterate>
					</logic:notEmpty>
				</sakai:group_table>
			</tr>
		</sakai:flat_list>	
		<sakai:flat_list>
			<tr>
				<th><fmt:message key="heading.general.rpm.signoffRouteLog"/></th>
			</tr>
			<logic:notEmpty  name="mdRpmForm" property="rpm.trackingList">
				<tr>
					<sakai:group_table>				
						<tr>
							<th>&nbsp;</th>						
							<th><fmt:message key="column.tracking.date"/></th>
							<th><fmt:message key="column.tracking.staff"/></th>
							<th><fmt:message key="column.tracking.result"/></th>
							<th><fmt:message key="column.tracking.referTo"/></th>
							<th><fmt:message key="column.tracking.comment"/></th>
						</tr>
						<logic:iterate name="mdRpmForm" property="rpm.trackingList" id="record" indexId="index">
							<tr>
								<th>&nbsp;</th>										
								<td><bean:write name="record" property="date"/> </td>
								<td><logic:notEmpty name="record" property="currentPerson.name"><bean:write name="record" property="currentPerson.name"/>&nbsp;(<bean:write name="record" property="currentPerson.personnelNumber"/>)</logic:notEmpty></td>
								<td><bean:write name="record" property="proposedResult.engDescription"/> </td>
								<td><logic:notEmpty name="record" property="assignedToPerson.name"><bean:write name="record" property="assignedToPerson.name"/>&nbsp;(<bean:write name="record" property="assignedToPerson.personnelNumber"/>)</logic:notEmpty></td>
								<td><bean:write name="record" property="comment"/></td>	
							</tr>
						</logic:iterate>
					</sakai:group_table>
				</tr>
			</logic:notEmpty>		
		</sakai:flat_list>	
		<sakai:flat_list>
			<tr>
				<th><fmt:message key="heading.general.rpm.reviewExistingResult"/></th>
			</tr>
			<tr>
				<sakai:group_table>
					<tr>
						<th colspan="2"><fmt:message key="instruction.proposedResult"/></th>
					</tr>
					<logic:iterate name="mdRpmForm" property="resultTypeList" id="record" indexId="index">
						<tr>							
							<td><html:radio property="tracking.proposedResult.code" idName="record" value="code"></html:radio></td>								
							<td><bean:write name="record" property="engDescription"/></td>
						</tr>
					</logic:iterate>
				</sakai:group_table>
			</tr>
			<tr>
				<sakai:group_table>
					<tr>
						<th><fmt:message key="page.prompt.comment"/></th>
					</tr>
					<tr>
						<td><html:textarea name="mdRpmForm" property="tracking.comment" cols="100" rows="5" ></html:textarea></td>
					</tr>
				</sakai:group_table>		
			</tr>
		</sakai:flat_list>		
		<sakai:actions>		
		<html:submit property="act">
				<fmt:message key="button.continue"/>
		</html:submit>	
		<html:submit property="act">
				<fmt:message key="button.back"/>
		</html:submit>	
		</sakai:actions>	
	</html:form>
</sakai:html>