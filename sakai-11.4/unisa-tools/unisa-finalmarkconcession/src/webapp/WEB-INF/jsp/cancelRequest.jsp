<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>

<fmt:setBundle basename="za.ac.unisa.lms.tools.finalmarkconcession.ApplicationResources"/>
<script language="javascript">
	function doAction() {
		document.finalMarkConcessionForm.action = 'finalMarkConcession.do?act=cancelRequest';  
		document.finalMarkConcessionForm.submit();
	}
</script>
<sakai:html>		
	<html:form action="/finalMarkConcession">
		<sakai:heading><fmt:message key="heading.altExamOptCancelRequest"/></sakai:heading>
		<html:hidden name="finalMarkConcessionForm" property="currentPage" value="cancelRequest"/>
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
				 <td colspan="2"><bean:write name="finalMarkConcessionForm" property="alternativeExamOpportunity.statusDesc"/></td>	
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
			<sakai:group_table>
				<tr>
					<td><fmt:message key="prompt.alternativeAssess"/></td>
						<td><bean:write name="finalMarkConcessionForm" property="alternativeExamOpportunity.alternativeAssessOpt"/>&nbsp;-&nbsp;<bean:write name="finalMarkConcessionForm" property="alternativeExamOpportunity.alternativeAssessOptDesc"/>
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
					<td><bean:write name="finalMarkConcessionForm" property="alternativeExamOpportunity.academicSupportOpt"/>&nbsp;-&nbsp;<bean:write name="finalMarkConcessionForm" property="alternativeExamOpportunity.academicSupportDesc"/></td>
				</tr>
				<logic:notEmpty name="finalMarkConcessionForm" property="alternativeExamOpportunity.academicSupportOtherDesc">
					<tr>
						<td><fmt:message key="prompt.academicSupportOtherDesc"/></td>
						<td><bean:write name="finalMarkConcessionForm" property="alternativeExamOpportunity.academicSupportOtherDesc"/></td>
					</tr>
				</logic:notEmpty>
				<tr>
					<td><fmt:message key="prompt.examperiodyearmarkweight"/></td>
					<td>
				         <bean:write name="finalMarkConcessionForm" property="recordSelected.yearMarkWeight"/>&#37;
					 </td>  
				</tr>
				<tr>
					<td><fmt:message key="prompt.yearmarkearned"/></td>
			        <td>
				         <bean:write name="finalMarkConcessionForm" property="recordSelected.yearMarkEarned" />
				    </td>
				</tr>			
				<tr>
					<td><fmt:message key="prompt.concessionMark"/></td>
					<td><bean:write name="finalMarkConcessionForm" property="alternativeExamOpportunity.concessionMark"/>
						<logic:notEmpty name="finalMarkConcessionForm" property="alternativeExamOpportunity.finalMark">
					 		<logic:notEmpty name="finalMarkConcessionForm" property="alternativeExamOpportunity.concessionMark">
					 			<bean:define id="finalMark" name="finalMarkConcessionForm" property="alternativeExamOpportunity.finalMark"/>
								<bean:define id="concessionMark" name="finalMarkConcessionForm" property="alternativeExamOpportunity.concessionMark"/>	
								<%
								int finalMrk = Integer.parseInt(finalMark.toString().trim());
								int concessionMrk = Integer.parseInt(concessionMark.toString().trim());
								if (finalMrk != 0) {
									if (finalMrk > concessionMrk){
										if (concessionMrk==0) {
										%>
										&nbsp;&nbsp;&nbsp;&nbsp;<fmt:message key="note.concessionMarkNA"/>
										<%}}
								}%>
					 		</logic:notEmpty>
					 	</logic:notEmpty>
					 </td>
				</tr>
				<tr>
					<td><fmt:message key="prompt.finalAssessMark"/></td>
					<td><bean:write name="finalMarkConcessionForm" property="alternativeExamOpportunity.finalMark"/>
						<logic:notEmpty name="finalMarkConcessionForm" property="alternativeExamOpportunity.finalMark">
					 		<logic:notEmpty name="finalMarkConcessionForm" property="alternativeExamOpportunity.concessionMark">
					 			<bean:define id="finalMark" name="finalMarkConcessionForm" property="alternativeExamOpportunity.finalMark"/>
								<bean:define id="concessionMark" name="finalMarkConcessionForm" property="alternativeExamOpportunity.concessionMark"/>	
								<%
								int finalMrk = Integer.parseInt(finalMark.toString().trim());
								int concessionMrk = Integer.parseInt(concessionMark.toString().trim());
								if (finalMrk != 0) {
									if (finalMrk > concessionMrk){
										if (concessionMrk==0) {
										%>
										&nbsp;
										<%}else{ %>
										&nbsp;&nbsp;&nbsp;&nbsp;<fmt:message key="note.yearMarkBenefitStudent"/>
										<%}
									} else{ %>
									&nbsp;&nbsp;&nbsp;&nbsp;<fmt:message key="note.yearMarkNotBenefitStudent"/>
									<%}
								}%>
					 		</logic:notEmpty>
					 	</logic:notEmpty>
				 	</td>
				 </tr>
				<logic:equal name="finalMarkConcessionForm" property="alternativeExamOpportunity.finalMark" value="0">
				    <tr>
				         <td><fmt:message key="prompt.zeromarkreason"/></td>
		                 <td>
				               <bean:write name="finalMarkConcessionForm" property="alternativeExamOpportunity.zeroMarkReasonDesc"/>
					     </td>
			        </tr>
			    </logic:equal>
				<tr>
					<td><fmt:message key="prompt.responsibleLecturer"/></td>
					<td><bean:write name="finalMarkConcessionForm" property="alternativeExamOpportunity.responsibleLecturer.name"/></td>
				</tr>
			</sakai:group_table>
		</sakai:group_table>
		<hr>
		<sakai:group_table>
			<tr><td>
				<sakai:instruction>
					<td><fmt:message key="prompt.cancelRequest"/>
				</sakai:instruction>
			</td></tr>
		</sakai:group_table>
		<sakai:actions>			
			<html:submit property="act" onclick="javascript:disabled=true;doAction();">
					<fmt:message key="button.yes"/>
			</html:submit>	
			<html:submit property="act">
					<fmt:message key="button.no"/>
			</html:submit>				
		</sakai:actions>
				
	</html:form>
</sakai:html>