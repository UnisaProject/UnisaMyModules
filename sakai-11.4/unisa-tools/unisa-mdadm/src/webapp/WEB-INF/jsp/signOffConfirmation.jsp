<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setBundle basename="za.ac.unisa.lms.tools.mdadm.ApplicationResources"/>

<sakai:html>

	<html:form action="/displaymdadmission">
	<html:hidden property="currentPage" value="signOffConfirmation"/>

		<sakai:heading><fmt:message key="page.heading.signoff.confirmation"/></sakai:heading>
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
	<logic:notEqual name="mdAdmissionForm" property="newTrackRecord.recommendation.code" value="LN">
		<sakai:flat_list>
				<tr>
					<th><fmt:message key="section.heading.recommendation"/></th>
				</tr>
				<tr>
					<logic:equal name="mdAdmissionForm" property="newTrackRecord.currentLevel" value="1">	
						<sakai:group_table>
							<tr>
								<th><bean:write name="mdAdmissionForm" property="newTrackRecord.recommendation.engDescription"/></th>
							</tr>
							<tr>
								<td><logic:equal name="mdAdmissionForm" property="newTrackRecord.recommendation.code" value="LT">
										<fmt:message key="page.prompt.recommendationComment.notAdmitted"/>
										<br/>
										<html:textarea name="mdAdmissionForm" property="newTrackRecord.recommendationComment" cols="100" rows="5" ></html:textarea>
									</logic:equal>
									<logic:equal name="mdAdmissionForm" property="newTrackRecord.recommendation.code" value="LE">
										<fmt:message key="page.prompt.recommendationComment.additionalRequirements"/>
										<br/>
										<html:textarea name="mdAdmissionForm" property="newTrackRecord.recommendationComment" cols="100" rows="5" ></html:textarea>
									</logic:equal>	
									<logic:equal name="mdAdmissionForm" property="newTrackRecord.recommendation.code" value="LF">
										<fmt:message key="page.prompt.studentContacts"/>
										<br/>
										<sakai:group_table>
											<tr>
												<th>&nbsp;</th>
												<th><fmt:message key="column.contact.name"/></th>
												<th><fmt:message key="column.contact.contactNr"/></th>
												<th><fmt:message key="column.contact.email"/></th>
												<th>&nbsp;</th>
											</tr>
											<tr>
												<td><fmt:message key="column.contact.person"/></td>
												<td><bean:write name="mdAdmissionForm" property="application.contactPerson.name"/></td>
												<td><bean:write name="mdAdmissionForm" property="application.contactPerson.contactNumber"/></td>
												<td><bean:write name="mdAdmissionForm" property="application.contactPerson.emailAddress"/></td>
												<td><html:submit property="action.search1"><fmt:message key="button.searchStaff"/></html:submit>&nbsp;<html:submit property="action.clear1"><fmt:message key="button.clear"/></html:submit></td>
											</tr>
											<tr>
												<td><fmt:message key="column.contact.supervisor"/></td>
												<td><bean:write name="mdAdmissionForm" property="application.supervisor.name"/></td>
												<td><bean:write name="mdAdmissionForm" property="application.supervisor.contactNumber"/></td>
												<td><bean:write name="mdAdmissionForm" property="application.supervisor.emailAddress"/></td>
												<td><html:submit property="action.search2"><fmt:message key="button.searchStaff"/></html:submit>&nbsp;<html:submit property="action.clear2"><fmt:message key="button.clear"/></html:submit></td>
											</tr>
											<tr>
												<td><fmt:message key="column.contact.coSupervisor"/></td>
												<td><bean:write name="mdAdmissionForm" property="application.jointSupervisor.name"/></td>
												<td><bean:write name="mdAdmissionForm" property="application.jointSupervisor.contactNumber"/></td>
												<td><bean:write name="mdAdmissionForm" property="application.jointSupervisor.emailAddress"/></td>
												<td><html:submit property="action.search3"><fmt:message key="button.searchStaff"/></html:submit>&nbsp;<html:submit property="action.clear3"><fmt:message key="button.clear"/></html:submit></td>
											</tr>
										</sakai:group_table>
									</logic:equal>															
								</td>
							</tr>	
						</sakai:group_table>
					</logic:equal>	
					<logic:notEqual name="mdAdmissionForm" property="newTrackRecord.currentLevel" value="1">	
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
					</logic:notEqual>	
				</tr>	
		</sakai:flat_list>
	</logic:notEqual>	
	<sakai:flat_list>			
			<tr>
				<logic:equal name="mdAdmissionForm" property="newTrackRecord.currentLevel" value="F">
					<logic:equal name="mdAdmissionForm" property="selectedResponse" value="AGREE">
						<th><fmt:message key="section.heading.confirmation.agreed.final"/></th>
					</logic:equal>
					<logic:equal name="mdAdmissionForm" property="selectedResponse" value="DISAGREE">
						<th><fmt:message key="section.heading.confirmation.disAgree"/></th>
					</logic:equal>
				</logic:equal>
				<logic:equal name="mdAdmissionForm" property="newTrackRecord.currentLevel" value="1">
					<logic:equal name="mdAdmissionForm" property="newTrackRecord.recommendation.code" value="LN">
						<th><fmt:message key="section.heading.confirmation.referBackAdmin"/></th>
					</logic:equal>
					<logic:notEqual name="mdAdmissionForm" property="newTrackRecord.recommendation.code" value="LN">
						<th><fmt:message key="section.heading.confirmation.agreed"/></th>
					</logic:notEqual>
				</logic:equal>
				<logic:notEqual name="mdAdmissionForm" property="newTrackRecord.currentLevel" value="F">
					<logic:notEqual name="mdAdmissionForm" property="newTrackRecord.currentLevel" value="1">
						<logic:equal name="mdAdmissionForm" property="selectedResponse" value="AGREE">
							<th><fmt:message key="section.heading.confirmation.agreed"/></th>
						</logic:equal>
						<logic:equal name="mdAdmissionForm" property="selectedResponse" value="DISAGREE">
							<th><fmt:message key="section.heading.confirmation.disAgree"/></th>
						</logic:equal>		
					</logic:notEqual>			
				</logic:notEqual>	
			</tr>	
			<tr>
				<sakai:group_table>
					<tr>
						<logic:equal name="mdAdmissionForm" property="newTrackRecord.currentLevel" value="1">
							<logic:equal name="mdAdmissionForm" property="newTrackRecord.recommendation.code" value="LN">
								<td><fmt:message key="page.prompt.signOffComment.notPossible"/></td>
							</logic:equal>
						</logic:equal>	
						<logic:notEqual name="mdAdmissionForm" property="newTrackRecord.currentLevel" value="1">
							<logic:equal name="mdAdmissionForm" property="selectedResponse" value="DISAGREE">
								<th><fmt:message key="page.prompt.signOffComment.disagree"/></th>
							</logic:equal>
						</logic:notEqual>
						<logic:equal name="mdAdmissionForm" property="selectedResponse" value="AGREE">
							<th><fmt:message key="page.prompt.signoffComment"/></th>
						</logic:equal>
						<logic:equal name="mdAdmissionForm" property="newTrackRecord.currentLevel" value="1">
							<logic:notEqual name="mdAdmissionForm" property="newTrackRecord.recommendation.code" value="LN">
								<th><fmt:message key="page.prompt.signoffComment"/></th>
							</logic:notEqual>
						</logic:equal>	
					</tr>
					<tr>
						<td><html:textarea name="mdAdmissionForm" property="newTrackRecord.signoffComment" cols="100" rows="5" ></html:textarea></td>
					</tr>
				</sakai:group_table>	
			</tr>
			<logic:notEmpty  name="mdAdmissionForm" property="signoffStaffList">	
				<tr><td>
					<sakai:group_table>
								<tr>
									<td colspan="3"><fmt:message key="instruction.confirmation.selectPerson"/></td>
								</tr>	
								<tr>
									<td colspan="3"><strong><fmt:message key="table.heading.signoff.staffList"/></strong><br/><fmt:message key="note.confirmation.alternativePersonOnRoutingList"/><td>
								</tr>
								<logic:iterate name="mdAdmissionForm" property="signoffStaffList" id="record">
									<tr>
										<td><html:radio property="selectedAssignTo" idName="record" value="personnelNumber"></html:radio></td>
										<td><bean:write name="record" property="name"/></td>
										<td><bean:write name="record" property="emailAddress"/></td>
									</tr>
								</logic:iterate>							
					</sakai:group_table>
				</td></tr>
			</logic:notEmpty>
			<logic:equal name="mdAdmissionForm" property="newTrackRecord.currentLevel" value="F">
				<logic:equal name="mdAdmissionForm" property="selectedResponse" value="AGREE">
					<tr><td><fmt:message key="note.confirmation.final"/></td></tr>
				</logic:equal>
			</logic:equal>
	</sakai:flat_list>		
		<sakai:actions>
			<logic:equal name="mdAdmissionForm" property="newTrackRecord.currentLevel" value="1">
				<logic:equal name="mdAdmissionForm" property="newTrackRecord.recommendation.code" value="LN">
					<html:submit property="action"><fmt:message key="button.referBack"/></html:submit>
				</logic:equal>
				<logic:notEqual name="mdAdmissionForm" property="newTrackRecord.recommendation.code" value="LN">
					<html:submit property="action"><fmt:message key="button.signoff"/></html:submit>
				</logic:notEqual>
			</logic:equal>		
			<logic:notEqual name="mdAdmissionForm" property="newTrackRecord.currentLevel" value="1">	
				<logic:equal name="mdAdmissionForm" property="selectedResponse" value="AGREE">
					<html:submit property="action"><fmt:message key="button.signoff"/></html:submit>
				</logic:equal>
				<logic:equal name="mdAdmissionForm" property="selectedResponse" value="DISAGREE">
					<html:submit property="action"><fmt:message key="button.referBack"/></html:submit>
				</logic:equal>
			</logic:notEqual>
			<html:submit property="action"><fmt:message key="button.back"/></html:submit>
		</sakai:actions>
		</html:form>
</sakai:html>