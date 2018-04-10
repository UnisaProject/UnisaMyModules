<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>

<fmt:setBundle basename="za.ac.unisa.lms.tools.finalmarkconcession.ApplicationResources"/>
<script language="javascript">
	function doAction() {
		document.finalMarkConcessionForm.action = 'finalMarkConcession.do?act=saveConcessionForm';  
		document.finalMarkConcessionForm.submit();
	}
</script>

<sakai:html>
	<html:form action="/finalMarkConcession">
		<sakai:heading><fmt:message key="heading.alternativeExamOpt"/></sakai:heading>
		<html:hidden name="finalMarkConcessionForm" property="currentPage" value="editConcessionForm"/>
		<sakai:messages/>
		<sakai:messages message="true"/>
		<sakai:instruction>
			<fmt:message key="note.required"/><fmt:message key="prompt.required"/>
		</sakai:instruction>
		<sakai:group_table>
			<tr>
				<td><fmt:message key="prompt.student"/></td>
				<td><bean:write name="finalMarkConcessionForm" property="recordSelected.studentNumber"/></td>
				<td><bean:write name="finalMarkConcessionForm" property="recordSelected.name"/></td>
				<td><a href='<%=(String)request.getAttribute("acadHistUrl")%>' target="_blank"><strong>Academic Record</strong></a></td>
			</tr>
			<tr>
			     <td><fmt:message key="prompt.student.module"/></td>
				 <td><bean:write name="finalMarkConcessionForm" property="recordSelected.studyUnit"/></td>	
				 <td colspan="2"><bean:write name="finalMarkConcessionForm" property="recordSelected.academicYear"/>/<bean:write name="finalMarkConcessionForm" property="recordSelected.semesterType"/></td>		     
			</tr>
			<tr>
			     <td><fmt:message key="prompt.student.result"/></td>
				 <td><bean:write name="finalMarkConcessionForm" property="recordSelected.finalMark"/></td>	
				 <td colspan="2"><bean:write name="finalMarkConcessionForm" property="recordSelected.resultDesc"/></td>
			</tr>
			<tr>
				<td><fmt:message key="prompt.student.qual"/></td>
				<td><bean:write name="finalMarkConcessionForm" property="recordSelected.qualification.qualCode"/></td>
				<td colspan="2"><bean:write name="finalMarkConcessionForm" property="recordSelected.qualification.qualDesc"/></td>
			</tr>
			<tr>
			     <td><fmt:message key="prompt.student.status"/></td>
				 <td colspan=2>
				 		<bean:write name="finalMarkConcessionForm" property="alternativeExamOpportunity.statusDesc"/>
				 		<logic:equal name="finalMarkConcessionForm" property="alternativeExamOpportunity.status" value="DECLINED">
					 		<BR>
					 		(<logic:notEqual name="finalMarkConcessionForm" property="alternativeExamOpportunity.adminDeclineOpt" value="OT">
					 			<bean:write name="finalMarkConcessionForm" property="alternativeExamOpportunity.adminDeclineOptDesc"/>
					 		</logic:notEqual>
					 		<logic:notEmpty name="finalMarkConcessionForm" property="alternativeExamOpportunity.adminDeclineOther">
					 			<bean:write name="finalMarkConcessionForm" property="alternativeExamOpportunity.adminDeclineOther"/>
					 		</logic:notEmpty>)
					 	</logic:equal>
				 </td>	
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
				<td><fmt:message key="prompt.alternativeAssess"/><fmt:message key="prompt.required"/></td>
				<td>
					<html:select name="finalMarkConcessionForm" property="alternativeExamOpportunity.alternativeAssessOpt">
					           <logic:equal name="finalMarkConcessionForm" property="recordSelected.academicLevel" value="postgraduate">
			                   <html:optionsCollection name="finalMarkConcessionForm" property="listAlternativeAssess" value="code" label="engDescription"/>
					    </logic:equal>
					    <logic:notEqual name="finalMarkConcessionForm" property="recordSelected.academicLevel" value="postgraduate">
			                   <html:optionsCollection name="finalMarkConcessionForm" property="listAlternativeAssess" value="code" label="engDescription"/>
					    </logic:notEqual>
				</html:select>
				</td>
			</tr>  
			<logic:equal name="finalMarkConcessionForm" property="removeAssessOptOther" value="false">                                                           
				<tr>
					<td colspan="2"><sakai:instruction><fmt:message key="note.alternativeAssessOther"/></sakai:instruction>
				</tr>
				<tr>
					<td><fmt:message key="prompt.alternativeAssessOther"/></td>
					<td><html:textarea name="finalMarkConcessionForm" property="alternativeExamOpportunity.alternativeAssessOtherDesc" cols="50" rows="5"/></td>
				</tr>
			</logic:equal>  
			<tr>
				<td><fmt:message key="prompt.academicSupport"/><fmt:message key="prompt.required"/></td>
				<td>
					<html:select name="finalMarkConcessionForm" property="alternativeExamOpportunity.academicSupportOpt">
					<html:optionsCollection name="finalMarkConcessionForm" property="listAcademicSupport" value="code" label="engDescription"/>
					</html:select>
				</td>
			</tr>
			<tr>
				<td colspan="2"><sakai:instruction><fmt:message key="note.alternativeAssessOther"/></sakai:instruction>
			</tr>
			<tr>
				<td><fmt:message key="prompt.academicSupportOther"/></td>
				<td><html:textarea name="finalMarkConcessionForm" property="alternativeExamOpportunity.academicSupportOtherDesc" cols="50" rows="5"/></td>
			</tr>
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
				<td>
					<html:text name="finalMarkConcessionForm" property="alternativeExamOpportunity.concessionMark" size="4" maxlength="3"/>
					<html:submit property="act">                      
						<fmt:message key="button.calculateFinalMark"/>
					</html:submit>	
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
								%>
								&nbsp;&nbsp;&nbsp;&nbsp;<fmt:message key="note.yearMarkBenefitStudent"/>
								<%}else{ %>
								&nbsp;&nbsp;&nbsp;&nbsp;<fmt:message key="note.yearMarkNotBenefitStudent"/>
								<%}
							}%>
				 		</logic:notEmpty>
				 	</logic:notEmpty>
				</td>
			</tr>
			<tr>
				<td><fmt:message key="prompt.zeromarkreason"/></td>
		        <td>
				      <html:select name="finalMarkConcessionForm" property="alternativeExamOpportunity.zeroMarkReason">
					               <html:optionsCollection name="finalMarkConcessionForm" property="listZeroMarkReason" value="code" label="engDescription"/>
					  </html:select>
			   </td>
			</tr>
			<tr>
				<td><fmt:message key="prompt.responsibleLecturer"/><fmt:message key="prompt.required"/></td>
				<td>
					<html:select name="finalMarkConcessionForm" property="alternativeExamOpportunity.responsibleLecturer.personnelNumber">
					<html:optionsCollection name="finalMarkConcessionForm" property="listLecturer" value="personnelNumber" label="name"/>
					</html:select>
				</td>
			</tr>
		</sakai:group_table>
		<sakai:actions>
			<logic:notEqual name="finalMarkConcessionForm" property="alternativeExamOpportunity.status" value="AUTHREQCOD">
				<html:submit property="act" onclick="javascript:disabled=true;doAction();">
						<fmt:message key="button.save"/>
				</html:submit>	
				<html:submit property="act">
					<fmt:message key="button.requestAuthorisation"/>
				</html:submit>
			</logic:notEqual>
			<logic:equal name="finalMarkConcessionForm" property="alternativeExamOpportunity.status" value="AUTHREQCOD">
				<html:submit property="act">
					<fmt:message key="button.cancelRequest"/>
				</html:submit>
			</logic:equal>
			<html:submit property="act">
					<fmt:message key="button.back"/>
			</html:submit>				
		</sakai:actions>				
	</html:form>
</sakai:html>
