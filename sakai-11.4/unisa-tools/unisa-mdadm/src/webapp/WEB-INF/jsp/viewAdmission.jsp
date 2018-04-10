<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setBundle basename="za.ac.unisa.lms.tools.mdadm.ApplicationResources"/>

<sakai:html>

	<html:form action="/displaymdadmission">
	<html:hidden property="currentPage" value="viewAdmission"/>

		<sakai:heading><fmt:message key="page.heading.viewAdmission"/></sakai:heading>
		<sakai:messages/>
	<sakai:flat_list>
			<tr>
				<th><fmt:message key="section.heading.studentDetails"/></th>
			</tr>	  
			<tr>
				<sakai:group_table>
					<tr>
						<td><strong><fmt:message key="page.heading.studentnr"/></strong></td>
						<td><bean:write name="mdAdmissionForm" property="student.studentNumber"/></td>
					</tr><tr>
						<td><strong><fmt:message key="page.heading.name"/></strong></td>
						<td colspan="3"><bean:write name="mdAdmissionForm" property="student.printName"/></td>
					</tr><tr>
						<td><strong><fmt:message key="page.heading.qualification"/></strong></td>
						<td><bean:write name="mdAdmissionForm" property="application.qualification.qualCode"/>&nbsp;&nbsp;<bean:write name="mdAdmissionForm" property="application.qualification.qualDesc"/></td>
					</tr><tr>
						<td><strong><fmt:message key="page.heading.speciality"/></strong></td>
						<td><bean:write name="mdAdmissionForm" property="application.qualification.specCode"/></td>
					</tr><tr>
						<td><strong><fmt:message key="page.heading.country"/></strong></td>
						<td><bean:write name="mdAdmissionForm" property="student.country"/></td>
					</tr><tr>
						<td><strong><fmt:message key="page.heading.gender"/></strong></td>
						<td><bean:write name="mdAdmissionForm" property="student.gender"/></td>	
					</tr><tr>
						<td><strong><fmt:message key="page.heading.homenumber"/></strong></td>
						<td><bean:write name="mdAdmissionForm" property="student.contactInfo.homeNumber"/></td>
					</tr><tr>
						<td><strong><fmt:message key="page.heading.cellnumber"/></strong></td>
						<td><bean:write name="mdAdmissionForm" property="student.contactInfo.cellNumber"/></td>
					</tr><tr>
						<td><strong><fmt:message key="page.heading.email"/></strong></td>
						<td><bean:write name="mdAdmissionForm" property="student.contactInfo.emailAddress"/></td>				
					</tr><tr>
						<td><strong><fmt:message key="page.heading.lecturerConsulted"/></strong></td>
						<td><bean:write name="mdAdmissionForm" property="application.lecturerConsulted"/></td>
					</tr>
				</sakai:group_table>
			</tr>	
	</sakai:flat_list>
		
	<sakai:flat_list>
			<tr>
				<th><fmt:message key="section.heading.supportDocs"/></th>
			</tr>	
			<tr>		
				<sakai:group_table>
				  	<logic:notEmpty name="mdAdmissionForm" property="documentList">
					<logic:iterate name="mdAdmissionForm" property="documentList" id="uFile" indexId="i">
					<tr>
					  <td><html:link href="displaymdadmission.do?action=getFile" paramName="uFile" paramProperty="uniqueId" paramId="selectedFile"><bean:write name="uFile" property="displayName"/></html:link></td>
					</tr>
					</logic:iterate>
					</logic:notEmpty>
				</sakai:group_table>
			</tr>	
	</sakai:flat_list>	
	
	<sakai:flat_list>
		<tr>
			<th><fmt:message key="section.heading.prevQuals"/></th>
		</tr>		
		<tr>    
			<sakai:group_table>
			<tr>
				<th align="left"><fmt:message key="table.heading.qualification"/>&nbsp;</th>
				<th align="left"><fmt:message key="table.heading.institution"/>&nbsp;</th>
				<th align="left"><fmt:message key="table.heading.compyear"/>&nbsp;</th>
				<th align="left"><fmt:message key="table.heading.source"/>&nbsp;</th>
			</tr>
			<logic:notEmpty name="mdAdmissionForm" property="application.prevQualification">
				<logic:iterate name="mdAdmissionForm" property="application.prevQualification" id="prevQualRecord" indexId="i">
				<tr>
					<td><bean:write name="prevQualRecord" property="qualification"/>&nbsp;</td>
					<td><bean:write name="prevQualRecord" property="institution"/></td>
					<td><bean:write name="prevQualRecord" property="yearCompleted"/>&nbsp;</td>
					<td><bean:write name="prevQualRecord" property="accreditationSource"/>&nbsp;</td>
				</tr>
				</logic:iterate>
			</logic:notEmpty>
			</sakai:group_table>
		</tr>	
	</sakai:flat_list>		
	
	<sakai:flat_list>
		<tr>
			<th><fmt:message key="section.heading.mdDetails"/></th>
		</tr>
		<tr>
			<sakai:group_table>
				<tr>
					<th><fmt:message key="page.heading.interest"/></th>
				</tr>	
				 <tr>
				   <td><bean:write name="mdAdmissionForm" property="application.areaOfInterest"/></td>
				 </tr>
			</sakai:group_table>
			<sakai:group_table>
				<tr>
					<th><fmt:message key="page.heading.title"/></th>
				</tr>	
				 <tr>
				   <td><bean:write name="mdAdmissionForm" property="application.title"/></td>
				 </tr>
			</sakai:group_table>
			<sakai:group_table>
				<tr>
					<th><fmt:message key="page.heading.advisorComment"/></th>
				</tr>	
				 <tr>
				   <td><bean:write name="mdAdmissionForm" property="application.advisorComment"/></td>
				 </tr>
			</sakai:group_table>
		</tr>
	</sakai:flat_list>	
	
	<sakai:flat_list>
		<tr>
			<th><fmt:message key="section.heading.signOffRoute"/></th>
		</tr>
		<tr>
			<sakai:group_table>
				<tr>
					<th colspan="4"><fmt:message key="subSection.heading.signOffRoute"/></th>
				</tr>	
				<logic:empty name="mdAdmissionForm" property="admSignOffRoute">
					<tr>
						<td colspan="4"><fmt:message key="note.noAdmSignOffRoute"/></td>
					</tr>
				</logic:empty>
				<logic:notEmpty name="mdAdmissionForm" property="admSignOffRoute">						
					<tr>											
						<th><fmt:message key="column.route.level"/></th>
						<th>&nbsp;</th>	
						<th>&nbsp;</th>
						<th>&nbsp;</th>
					</tr>
					<logic:iterate name="mdAdmissionForm" property="admSignOffRoute" id="record" indexId="index">
						<tr>					
							<td><logic:equal name="record" property="signOffLevel.level" value="F"><fmt:message key="levelF"/></logic:equal>
								<logic:notEqual name="record" property="signOffLevel.level" value="F"><bean:write name="record" property="signOffLevel.level"/></logic:notEqual>
							</td>							
							<td><bean:write name="record" property="person.name"/>&nbsp;(<bean:write name="record" property="person.personnelNumber"/>)</td>
							<td><fmt:message key="column.route.tel"/>&nbsp;<bean:write name="record" property="person.contactNumber"/></td>
							<td><fmt:message key="column.route.email"/>&nbsp;<bean:write name="record" property="person.emailAddress"/></td>														
						</tr>
					</logic:iterate>
				</logic:notEmpty>
			</sakai:group_table>
		</tr>	
		<tr>
			<sakai:group_table>
				<tr>
					<th colspan="4"><fmt:message key="subSection.heading.signOffRoute.appeal"/></th>
				</tr>	
				<logic:empty name="mdAdmissionForm" property="appealSignOffRoute">
					<tr>
						<td colspan="4"><fmt:message key="note.noAppealSignOffRoute"/></td>
					</tr>
				</logic:empty>
				<logic:notEmpty name="mdAdmissionForm" property="appealSignOffRoute">						
					<tr>											
						<th><fmt:message key="column.route.level"/></th>
						<th>&nbsp;</th>	
						<th>&nbsp;</th>
						<th>&nbsp;</th>
					</tr>
					<logic:iterate name="mdAdmissionForm" property="appealSignOffRoute" id="record" indexId="index">
						<tr>					
							<td><logic:equal name="record" property="signOffLevel.level" value="E"><fmt:message key="levelE"/></logic:equal>
								<logic:equal name="record" property="signOffLevel.level" value="I"><fmt:message key="levelI"/></logic:equal>								
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
			<th><fmt:message key="section.heading.signOffStatus"/></th>
		</tr>	
		<tr>		
			<sakai:group_table>					
				<logic:notEmpty name="mdAdmissionForm" property="signOffStatusList">						
					<tr>											
						<th><fmt:message key="column.signoffStatus.date"/></th>
						<th><fmt:message key="column.signoffStatus.status"/></th>
						<th><fmt:message key="column.signoffStatus.from"/></th>
						<th><fmt:message key="column.signoffStatus.referTo"/></th>	
						<th><fmt:message key="column.signoffStatus.recommendation"/></th>	
						<th><fmt:message key="column.signoffStatus.recommendationComment"/></th>
						<th><fmt:message key="column.signoffStatus.signOffComment"/></th>	
					</tr>
					<logic:iterate name="mdAdmissionForm" property="signOffStatusList" id="record" indexId="index">
						<tr>
							<td><bean:write name="record" property="date"/></td>
							<td><bean:write name="record" property="status.engDescription"/></td>		
							<td><bean:write name="record" property="currentPerson.name"/>(
								<logic:equal name="record" property="currentLevel" value="A"><fmt:message key="levelA"/></logic:equal>
								<logic:equal name="record" property="currentLevel" value="F"><fmt:message key="levelF"/></logic:equal>								
								<logic:equal name="record" property="currentLevel" value="E"><fmt:message key="levelE"/></logic:equal>
								<logic:equal name="record" property="currentLevel" value="I"><fmt:message key="levelI"/></logic:equal>
									<logic:notEqual name="record" property="currentLevel" value="F">
									<logic:notEqual name="record" property="currentLevel" value="A">
										<logic:notEqual name="record" property="currentLevel" value="E">
											<logic:notEqual name="record" property="currentLevel" value="I">
												level <bean:write name="record" property="currentLevel"/>
											</logic:notEqual>
										</logic:notEqual>		
									</logic:notEqual>	
								</logic:notEqual>)
							</td>		
							<td><bean:write name="record" property="assignToPerson.name"/>(
								<logic:equal name="record" property="assignToLevel" value="A"><fmt:message key="levelA"/></logic:equal>
								<logic:equal name="record" property="assignToLevel" value="F"><fmt:message key="levelF"/></logic:equal>								
								<logic:equal name="record" property="assignToLevel" value="E"><fmt:message key="levelE"/></logic:equal>
								<logic:equal name="record" property="assignToLevel" value="I"><fmt:message key="levelI"/></logic:equal>
								<logic:notEqual name="record" property="assignToLevel" value="F">
									<logic:notEqual name="record" property="assignToLevel" value="A">
										<logic:notEqual name="record" property="assignToLevel" value="E">
											<logic:notEqual name="record" property="assignToLevel" value="I">
												level <bean:write name="record" property="assignToLevel"/>
											</logic:notEqual>
										</logic:notEqual>		
									</logic:notEqual>	
								</logic:notEqual>)
							</td>	
							<td><bean:write name="record" property="recommendation.engDescription"/></td>	
							<td><bean:write name="record" property="recommendationComment"/></td>	
							<td><bean:write name="record" property="signoffComment"/></td>													
						</tr>
					</logic:iterate>
				</logic:notEmpty>	
			</sakai:group_table>
		</tr>
	</sakai:flat_list>
	<sakai:flat_list>	
			<tr>
				<th><fmt:message key="section.heading.applicationStatus"/></th>
			</tr>
			<tr>
				<sakai:group_table>
						<tr>
							<td>
								<logic:equal name="mdAdmissionForm" property="application.status" value="I">Internet application submitted</logic:equal>
								<logic:equal name="mdAdmissionForm" property="application.status" value="O">Application opened by M&D admin section</logic:equal>
								<logic:equal name="mdAdmissionForm" property="application.status" value="R">Admission referred to academics for consideration</logic:equal>
								<logic:equal name="mdAdmissionForm" property="application.status" value="A">Student appealed admission decision</logic:equal>
								<logic:equal name="mdAdmissionForm" property="application.status" value="Y">Finalised. Admission granted</logic:equal>
								<logic:equal name="mdAdmissionForm" property="application.status" value="N">Finalised. Not admitted</logic:equal>
							</td>
						</tr>
				</sakai:group_table>
			</tr>		
	</sakai:flat_list>
	<sakai:flat_list>			
			<tr>
				<th>
					<logic:equal name="mdAdmissionForm" property="application.status" value="R"><fmt:message key="section.heading.recommendation"/></logic:equal>
					<logic:equal name="mdAdmissionForm" property="application.status" value="A">
						<logic:equal name="mdAdmissionForm" property="latestTrackRecord.status.code" value="FS"><fmt:message key="section.heading.recommendation.appeal"/></logic:equal>
					    <logic:notEqual name="mdAdmissionForm" property="latestTrackRecord.status.code" value="FS"><fmt:message key="section.heading.previousRecommendationOnAppeal"/></logic:notEqual>
					 </logic:equal>   
					<logic:equal name="mdAdmissionForm" property="application.status" value="Y"><fmt:message key="section.heading.admissionDecision"/></logic:equal>
				</th>
			</tr>
			<tr>
				<sakai:group_table>
					<tr>
						<th colspan="2"><bean:write name="mdAdmissionForm" property="application.recommendation.engDescription"/></th>
					</tr>
					<tr>
						<td>
							<logic:equal name="mdAdmissionForm" property="application.recommendation.code" value="LT">						
								<bean:write name="mdAdmissionForm" property="application.notAdmittedComment"/>
							</logic:equal>
							<logic:equal name="mdAdmissionForm" property="application.recommendation.code" value="LS">						
								<bean:write name="mdAdmissionForm" property="application.structuredComment"/>
							</logic:equal>
							<logic:equal name="mdAdmissionForm" property="application.recommendation.code" value="LF">
								<br/>
								<sakai:group_table>
									<tr>
										<th>&nbsp;</th>
										<th><fmt:message key="column.contact.name"/></th>
										<th><fmt:message key="column.contact.contactNr"/></th>
										<th><fmt:message key="column.contact.email"/></th>
									</tr>
									<tr>
										<td><fmt:message key="column.contact.person"/></td>
										<td><bean:write name="mdAdmissionForm" property="application.contactPerson.name"/></td>
										<td><bean:write name="mdAdmissionForm" property="application.contactPerson.contactNumber"/></td>
										<td><bean:write name="mdAdmissionForm" property="application.contactPerson.emailAddress"/></td>
									</tr>
									<tr>
										<td><fmt:message key="column.contact.supervisor"/></td>
										<td><bean:write name="mdAdmissionForm" property="application.supervisor.name"/></td>
										<td><bean:write name="mdAdmissionForm" property="application.supervisor.contactNumber"/></td>
										<td><bean:write name="mdAdmissionForm" property="application.supervisor.emailAddress"/></td>
									</tr>
									<tr>
										<td><fmt:message key="column.contact.coSupervisor"/></td>
										<td><bean:write name="mdAdmissionForm" property="application.jointSupervisor.name"/></td>
										<td><bean:write name="mdAdmissionForm" property="application.jointSupervisor.contactNumber"/></td>
										<td><bean:write name="mdAdmissionForm" property="application.jointSupervisor.emailAddress"/></td>
									</tr>
									</sakai:group_table>
								</logic:equal>				
							</td>
						</tr>									
				</sakai:group_table>
			</tr>				
	</sakai:flat_list>		
	<sakai:actions>
		<html:submit property="action"><fmt:message key="button.back"/></html:submit>
	</sakai:actions>
	</html:form>
</sakai:html>