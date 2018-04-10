<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>

<fmt:setBundle basename="za.ac.unisa.lms.tools.mdrpm.ApplicationResources"/>
<script language="javascript">
	function doAction(){
		document.mdRpmForm.action='mdRpm.do?act=saveTitle';
		document.mdRpmForm.submit();
	}
</script>
<sakai:html>
	<html:form action="/mdRpm">
	<html:hidden property="currentPage" value="titleMnt"/>
		<sakai:messages/>
		<sakai:messages message="true"/>
		<sakai:heading>
			<fmt:message key="heading.titleMnt"/>
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
			<!--<tr>				
				<sakai:group_table>
					<tr>
						<th colspan="4"><fmt:message key="heading.table.promotor"/></th>
					</tr>
					<tr>
						<th>&nbsp;</th>
						<th>&nbsp;</th>						
						<th><fmt:message key="column.promotor.name"/></th>	
						<th><fmt:message key="column.promotor.personnelNr"/></th>
					</tr>
					<logic:iterate name="mdRpmForm" property="rpm.promotorList" id="record" indexId="index">
						<tr>
							<td>&nbsp;</td>
							<td><logic:equal name="record" property="isSupervisor" value="true">Supervisor</logic:equal>
								<logic:equal name="record" property="isSupervisor" value="false">Assistant</logic:equal>
							</td>							
							<td><bean:write name="record" property="person.name"/></td>
							<td><bean:write name="record" property="person.personnelNumber"/></td>									
						</tr>
					</logic:iterate>					
				</sakai:group_table>
			</tr>  -->	
		</sakai:flat_list>	
		<sakai:flat_list>		
			<tr>
				<th colspan="4"><fmt:message key="heading.general.titleMnt.history"/></th>
			</tr>
				<sakai:group_table>
					<logic:empty name="titleHistoryList">
						<tr>
							<td><fmt:message key="note.noTitleAmendmentRecords"/></td>
						</tr>	
					</logic:empty>
					<logic:notEmpty name="titleHistoryList">
						<tr>
						<tr>						
							<th><fmt:message key="column.titleHistory.title"/></th>	
							<th><fmt:message key="column.titleHistory.date"/></th>					
							<th><fmt:message key="column.titleHistory.name"/></th>	
							<th><fmt:message key="column.titleHistory.comment"/></th>
						</tr>
						<logic:iterate name="titleHistoryList" id="record" indexId="index">
							<tr>							
								<td><bean:write name="record" property="title"/></td>
								<td><bean:write name="record" property="updatedOn"/></td>
								<td><bean:write name="record" property="updatedBy"/></td>
								<td><bean:write name="record" property="comment"/></td>									
							</tr>
						</logic:iterate>
					</logic:notEmpty>
				</sakai:group_table>		
			</tr>		
		</sakai:flat_list>
		<sakai:flat_list>		
			<tr>
				<th colspan="4"><fmt:message key="heading.general.titleMnt"/></th>
			</tr>
			<tr>
				<sakai:group_table>
					<!-- <tr>
						<th><fmt:message key="page.prompt.proposedTitle"/></th>
					</tr> -->
					<tr>
						<td><html:textarea name="mdRpmForm" property="rpm.title" cols="100" rows="5" ></html:textarea></td>
					</tr>
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
			<html:submit property="act" onclick="disabled=true;doAction();">
					<fmt:message key="button.saveTitle"/>
			</html:submit>	
			<html:submit property="act">
					<fmt:message key="button.back"/>
			</html:submit>	
		</sakai:actions>	
	</html:form>
</sakai:html>		