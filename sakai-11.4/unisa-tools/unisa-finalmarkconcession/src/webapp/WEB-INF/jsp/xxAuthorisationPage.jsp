<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>

<fmt:setBundle basename="za.ac.unisa.lms.tools.finalmarkconcession.ApplicationResources"/>

<sakai:html>		
	<html:form action="/authorisation">
		<logic:equal name="finalMarkConcessionForm" property="authorisationType" value="AUTHREQCOD">
			<sakai:heading><fmt:message key="heading.altExamOptCodAuthorisation"/></sakai:heading>
		</logic:equal>
		<logic:equal name="finalMarkConcessionForm" property="authorisationType" value="AUTHREQDN">
			<sakai:heading><fmt:message key="heading.altExamOptDeanAuthorisation"/></sakai:heading>
		</logic:equal>
		<html:hidden name="finalMarkConcessionForm" property="currentPage" value="authPage"/>
		<sakai:messages/>
		<sakai:messages message="true"/>		
		<sakai:group_table>
			<tr>
				<td><fmt:message key="prompt.student"/></td>
				<td><bean:write name="finalMarkConcessionForm" property="recordSelected.studentNumber"/></td>
				<td><bean:write name="finalMarkConcessionForm" property="recordSelected.name"/></td>
			</tr>
			<tr>
			     <td><fmt:message key="prompt.student.module"/></td>
				 <td><bean:write name="finalMarkConcessionForm" property="recordSelected.studyUnit"/></td>	
				 <td><bean:write name="finalMarkConcessionForm" property="recordSelected.academicYear"/>/<bean:write name="finalMarkConcessionForm" property="recordSelected.semesterType"/></td>
			</tr>
			<tr>
			     <td><fmt:message key="prompt.student.result"/></td>
				 <td><bean:write name="finalMarkConcessionForm" property="recordSelected.finalMark"/></td>	
				 <td><bean:write name="finalMarkConcessionForm" property="recordSelected.resultDesc"/></td>
			</tr>
			<tr>
				<td><fmt:message key="prompt.student.qual"/></td>
				<td><bean:write name="finalMarkConcessionForm" property="recordSelected.qualification.qualCode"/></td>
				<td><bean:write name="finalMarkConcessionForm" property="recordSelected.qualification.qualDesc"/></td>
			</tr>
			<tr>
			     <td><fmt:message key="prompt.student.status"/></td>
				 <td><bean:write name="finalMarkConcessionForm" property="recordSelected.statusDesc"/></td>	
				 <td>
			</tr>
		</sakai:group_table>
		<hr>
		<sakai:group_heading>
			<fmt:message key="heading.studentContactInfo"/>
		</sakai:group_heading>
		<sakai:group_table>				
			<tr>
				<td><fmt:message key="prompt.student.contactNumbers"/></td>
				<td>
					<logic:notEmpty name="finalMarkConcessionForm" property="recordSelected.contactDetails.homeNumber">
					  	<fmt:message key="prompt.student.homeNumber"/>
					  	<bean:write name="finalMarkConcessionForm" property="recordSelected.contactDetails.homeNumber"/>
					  	<BR>
					</logic:notEmpty>
					<logic:notEmpty name="finalMarkConcessionForm" property="recordSelected.contactDetails.workNumber">
						<fmt:message key="prompt.student.workNumber"/>	
						<bean:write name="finalMarkConcessionForm" property="recordSelected.contactDetails.workNumber"/>
						<BR>
					</logic:notEmpty>	
					<logic:notEmpty name="finalMarkConcessionForm" property="recordSelected.contactDetails.cellNumber">
						<fmt:message key="prompt.student.cellNumber"/>	
						<bean:write name="finalMarkConcessionForm" property="recordSelected.contactDetails.cellNumber"/>
					</logic:notEmpty>	
				</td>				
				<td><fmt:message key="prompt.student.address"/></td>
				<td>
					<logic:notEmpty name="finalMarkConcessionForm" property="recordSelected.contactDetails.addressLine1">
						<bean:write name="finalMarkConcessionForm" property="recordSelected.contactDetails.addressLine1"/>
					</logic:notEmpty>
					<logic:notEmpty name="finalMarkConcessionForm" property="recordSelected.contactDetails.addressLine2">
						<BR><bean:write name="finalMarkConcessionForm" property="recordSelected.contactDetails.addressLine2"/>
					</logic:notEmpty>
					<logic:notEmpty name="finalMarkConcessionForm" property="recordSelected.contactDetails.addressLine3">
						<BR><bean:write name="finalMarkConcessionForm" property="recordSelected.contactDetails.addressLine3"/>
					</logic:notEmpty>
					<logic:notEmpty name="finalMarkConcessionForm" property="recordSelected.contactDetails.addressLine4">
						<BR><bean:write name="finalMarkConcessionForm" property="recordSelected.contactDetails.addressLine4"/>
					</logic:notEmpty>
					<logic:notEmpty name="finalMarkConcessionForm" property="recordSelected.contactDetails.addressLine5">
						<BR><bean:write name="finalMarkConcessionForm" property="recordSelected.contactDetails.addressLine5"/>
					</logic:notEmpty>
					<logic:notEmpty name="finalMarkConcessionForm" property="recordSelected.contactDetails.addressLine6">
						<BR><bean:write name="finalMarkConcessionForm" property="recordSelected.contactDetails.addressLine6"/>
					</logic:notEmpty>
					<logic:notEmpty name="finalMarkConcessionForm" property="recordSelected.contactDetails.postalCode">
						<BR><bean:write name="finalMarkConcessionForm" property="recordSelected.contactDetails.postalCode"/>
					</logic:notEmpty>
				</td>
			</tr>
			<logic:notEmpty name="finalMarkConcessionForm" property="recordSelected.contactDetails.emailAddress">
				<tr>
					<td><fmt:message key="prompt.student.emailAddress"/></td>
					<td><bean:write name="finalMarkConcessionForm" property="recordSelected.contactDetails.emailAddress"/></td>		
					<td></td>
					<td></td>	
				</tr>
			</logic:notEmpty>
		</sakai:group_table>
		<sakai:group_heading>
			<fmt:message key="heading.additionalExamInfo"/>
		</sakai:group_heading>
		<sakai:group_table>
				<tr>
					<td><fmt:message key="prompt.alternativeAssess"/></td>
					<td><bean:write name="finalMarkConcessionForm" property="alternativeExamOpportunity.alternativeAccessOptDesc"/>
					</td>
				</tr>
				<logic:notEmpty name="finalMarkConcessionForm" property="alternativeExamOpportunity.alternativeAssessOtherDesc">
					<tr>
						<td><fmt:message key="prompt.alternativeAssessOtherDesc"/></td>
						<td><bean:write name="finalMarkConcessionForm" property="alternativeExamOpportunity.alternativeAssessOtherDesc"/></td>
					</tr>
				</logic:notEmpty>
				<tr>
					<td><fmt:message key="prompt.academicSupport"/></td>
					<td><bean:write name="finalMarkConcessionForm" property="alternativeExamOpportunity.academicSupportDesc"/></td>
				</tr>
				<logic:notEmpty name="finalMarkConcessionForm" property="alternativeExamOpportunity.academicSupportOtherDesc">
					<tr>
						<td><fmt:message key="prompt.academicSupportOtherDesc"/></td>
						<td><bean:write name="finalMarkConcessionForm" property="alternativeExamOpportunity.academicSupportOtherDesc"/></td>
					</tr>
				</logic:notEmpty>
				<tr>
					<td><fmt:message key="prompt.finalAssessMark"/></td>
					<td><bean:write name="finalMarkConcessionForm" property="alternativeExamOpportunity.finalMark"/></td>
				</tr>
				<tr>
					<td><fmt:message key="prompt.responsibleLecturer"/></td>
					<td><bean:write name="finalMarkConcessionForm" property="alternativeExamOpportunity.responsibleLecturer.name"/></td>
				</tr>
			</sakai:group_table>
		<hr>
		<sakai:group_heading>
				<fmt:message key="heading.reject"/> 
			</sakai:group_heading>
		<sakai:group_table>
			<tr>
				<td colspan="2">
					<sakai:instruction>
						<fmt:message key="instruction.authComment"/>
					</sakai:instruction>
				</td>
			</tr>
			<tr>
				<td><fmt:message key="prompt.comment"/></td>
				<td><html:textarea name="finalMarkConcessionForm" property="authComment" cols="50" rows="5"/></td>
			</tr>
		</sakai:group_table>	
		<sakai:actions>
			<html:submit property="action">
					<fmt:message key="button.sendBack"/>
				</html:submit>
		</sakai:actions>
		<logic:equal name="finalMarkConcessionForm" property="authorisationType" value="AUTHREQCOD">
			<hr>	
			<sakai:group_heading>
				<fmt:message key="heading.authorise"/> 
			</sakai:group_heading>
			<sakai:instruction>
				<fmt:message key="note.requestAuthorisation.2"/>
			</sakai:instruction>
			<sakai:instruction>
				<fmt:message key="note.requestAuthorisation.3"/>
			</sakai:instruction>
			<sakai:group_table>		
				<tr>
					<td colspan="2"><fmt:message key="prompt.dean"/></td>
				<tr>
				<logic:iterate name="deanList" id="record">
					<tr>
						<td><html:radio property="selectedAuthoriser" idName="record" value="personnelNumber"></html:radio></td>
						<td><bean:write name="record" property="name"/></td>
						<td><bean:write name="record" property="emailAddress"/></td>
					</tr>
				</logic:iterate>
				<tr>
					<td colspan="2"><fmt:message key="prompt.deputyDean"/></td>
				<tr>
				<logic:iterate name="finalMarkConcessionForm" property="authDepartment.deputyDeanList" id="record">
					<tr>
						<td><html:radio property="selectedAuthoriser" idName="record" value="personnelNumber"></html:radio></td>
						<td><bean:write name="record" property="name"/></td>
						<td><bean:write name="record" property="emailAddress"/></td>
					</tr>
				</logic:iterate>
			</sakai:group_table>	
			<sakai:actions>
				<html:submit property="action">
					<fmt:message key="button.requestAuthDean"/>
				</html:submit>
			</sakai:actions>
		</logic:equal>		
		<hr>
		<sakai:actions>			
			<logic:equal name="finalMarkConcessionForm" property="authorisationType" value="AUTHREQDN">
				<html:submit property="action">
					<fmt:message key="button.authorise"/>
				</html:submit>
			</logic:equal>				
			<html:submit property="action">
				<fmt:message key="button.cancel"/>
			</html:submit>
		</sakai:actions>
				
	</html:form>
</sakai:html>