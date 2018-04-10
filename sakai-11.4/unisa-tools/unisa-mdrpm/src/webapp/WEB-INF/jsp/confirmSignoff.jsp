<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>

<fmt:setBundle basename="za.ac.unisa.lms.tools.mdrpm.ApplicationResources"/>
<script language="javascript">
	function doAction(){
		document.mdRpmForm.action='mdRpm.do?act=signoff';
		document.mdRpmForm.submit();
	}
</script>
<sakai:html>
	<html:form action="/mdRpm">
	<html:hidden property="currentPage" value="confirmSignoff"/>
		<sakai:messages/>
		<sakai:messages message="true"/>
		<sakai:heading>
			<fmt:message key="heading.confirmSignoff"/>
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
			<tr>
				<th><fmt:message key="heading.general.rpm.details"/></th>
			</tr>
			<tr>
				<td>
					<sakai:group_table>		
						<tr>
							<td colspan="2"><strong><fmt:message key="page.prompt.title"/></strong></td>
						</tr>	
						<tr>				
							<td></td><td><bean:write name="mdRpmForm" property="rpm.title"/></td>
						</tr>
						<tr>
							<td colspan="2"><strong><fmt:message key="page.prompt.supervisors"/></strong></td>
						</tr>	
						<tr>				
							<td></td><td><bean:write name="mdRpmForm" property="supervisorsStr"/></td>
						</tr>
						<tr>
							<td colspan="2"><strong><fmt:message key="page.prompt.proposedResult"/></strong></td>
						</tr>	
						<tr>
							<td></td><td><bean:write name="mdRpmForm" property="tracking.proposedResult.engDescription"/></td>
						</tr>	
					</sakai:group_table>
				</td>	
			</tr>				
		</sakai:flat_list>		
		<sakai:flat_list>
		<!-- Supervisor result proposal -->
			<logic:equal name="mdRpmForm" property="tracking.currentLevel" value="Sup">
				<tr>
					<th><fmt:message key="heading.general.rpm.signoffRequest.sup"/></th>
				</tr>
				<logic:notEmpty name="mdRpmForm" property="tracking.comment">
					<tr>
						<sakai:group_table>
							<tr>
								<th><fmt:message key="page.prompt.yourComment"/></th>
								<td><bean:write name="mdRpmForm" property="tracking.comment"/></td>
							</tr>
						</sakai:group_table>	
					</tr>
				</logic:notEmpty>
				<tr>
					<td>
						<sakai:group_table>
							<tr>
								<td colspan="3"><fmt:message key="instruction.signoffRequest.agree"/></td>
							</tr>	
							<tr>
								<td colspan="3"><strong><fmt:message key="heading.signoff.staffList.agree"/></strong><td>
							</tr>
							<logic:iterate name="mdRpmForm" property="signoffStaffList" id="record">
								<tr>
									<td><html:radio property="selectedApprover" idName="record" value="personnelNumber"></html:radio></td>
									<td><bean:write name="record" property="name"/></td>
									<td><bean:write name="record" property="emailAddress"/></td>
								</tr>
							</logic:iterate>							
						</sakai:group_table>
					</td>
				</tr>
			</logic:equal>
			<!-- Not supervisor -->
			<logic:notEqual name="mdRpmForm" property="tracking.currentLevel" value="Sup">
				<!-- Final sign-off - aggreement -->
				<logic:equal name="mdRpmForm" property="tracking.currentLevel" value="F">
					<logic:equal name="mdRpmForm" property="selectedResponse" value="AGREE">
						<tr>
							<th><fmt:message key="heading.general.rpm.signoffRequestFinal"/></th>
						</tr>
						<logic:notEmpty name="mdRpmForm" property="tracking.comment">
							<tr>
								<sakai:group_table>
									<tr>
										<th><fmt:message key="page.prompt.yourComment"/></th>
										<td><bean:write name="mdRpmForm" property="tracking.comment"/></td>
									</tr>
								</sakai:group_table>	
							</tr>
						</logic:notEmpty>
						<sakai:group_table>
							<tr>						
								<td>&nbsp;<fmt:message key="note.finalSignoff"/></td>
							</tr>
						</sakai:group_table>	
					</logic:equal>
				</logic:equal>
				<!-- Not final sign-off - aggreement -->
				<logic:notEqual name="mdRpmForm" property="tracking.currentLevel" value="F">
					<logic:equal name="mdRpmForm" property="selectedResponse" value="AGREE">
						<tr>
							<th><fmt:message key="heading.general.rpm.signoffRequest.agree"/></th>
						</tr>
						<logic:notEmpty name="mdRpmForm" property="tracking.comment">
							<tr>
								<sakai:group_table>
									<tr>
										<th><fmt:message key="page.prompt.yourComment"/></th>
										<td><bean:write name="mdRpmForm" property="tracking.comment"/></td>
									</tr>
								</sakai:group_table>	
							</tr>
						</logic:notEmpty>
						<tr>
							<td><sakai:group_table>
								<tr>
									<td colspan="3"><fmt:message key="instruction.signoffRequest.agree"/></td>
								</tr>	
								<tr>
									<td colspan="3"><strong><fmt:message key="heading.signoff.staffList.agree"/></strong><td>
								</tr>
								<logic:iterate name="mdRpmForm" property="signoffStaffList" id="record">
									<tr>
										<td><html:radio property="selectedApprover" idName="record" value="personnelNumber"></html:radio></td>
										<td><bean:write name="record" property="name"/></td>
										<td><bean:write name="record" property="emailAddress"/></td>
									</tr>
								</logic:iterate>
							
							</sakai:group_table></td>
						</tr>
					</logic:equal>
				</logic:notEqual>
				<!-- Disagreement with proposed result -->
				<logic:equal name="mdRpmForm" property="selectedResponse" value="DISAGREE">	
					<tr>
							<th><fmt:message key="heading.general.rpm.signoffRequest.disagree"/></th>
					</tr>
					<logic:notEmpty name="mdRpmForm" property="tracking.comment">
						<tr>
							<sakai:group_table>
								<tr>
									<th><fmt:message key="page.prompt.yourComment"/></th>
									<td><bean:write name="mdRpmForm" property="tracking.comment"/></td>
								</tr>
							</sakai:group_table>	
						</tr>
					</logic:notEmpty>
					<tr>
						<td><sakai:group_table>
							<tr>
								<td colspan="3"><fmt:message key="instruction.signoffRequest.disagree"/></td>
							</tr>
							<tr>
								<td colspan="3"><strong><fmt:message key="heading.signoff.staffList.disagree"/></strong><td>
							</tr>
							<logic:iterate name="mdRpmForm" property="signoffStaffList" id="record">
								<tr>
									<td><html:radio property="selectedApprover" idName="record" value="personnelNumber"></html:radio></td>
									<td><bean:write name="record" property="name"/></td>
									<td><bean:write name="record" property="emailAddress"/></td>
								</tr>
							</logic:iterate>					
						</sakai:group_table></td>
					</tr>
				</logic:equal>
		</logic:notEqual>		
		</sakai:flat_list>
		<sakai:actions>					
			<html:button property="act" onclick="disabled=true;doAction();">
				<fmt:message key="button.signoff"/>
			</html:button>
			<html:submit property="act">
				<fmt:message key="button.back"/>
			</html:submit>	
		</sakai:actions>		
		
	</html:form>
</sakai:html>		