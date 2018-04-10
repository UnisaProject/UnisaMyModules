<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<fmt:setBundle
	basename="za.ac.unisa.lms.tools.honsadmission.ApplicationResources" />
<script language="javascript">
	function doAction() {
		document.honsAdmissionForm.action = 'honsAdmission.do?act=signOff';  
		document.honsAdmissionForm.submit();
	}
</script>

<sakai:html>
<html:form action="/honsAdmission" >
	<sakai:messages />
	<sakai:messages message="true" />
	<sakai:heading>
		<logic:equal name="honsAdmissionForm" property="access" value="update">
			<fmt:message key="page.review.heading" />
		</logic:equal>
		<logic:notEqual name="honsAdmissionForm" property="access" value="update">
			<fmt:message key="page.view.heading" />
		</logic:notEqual>
			
	</sakai:heading>
	
	<sakai:group_heading>
		<fmt:message key="page.review.subHeading.stuPersInfo" />
	</sakai:group_heading>
	<sakai:group_table>
		<tr>
			<td><strong><fmt:message key="prompt.studentNumber"/></strong></td>
			<td><bean:write name="honsAdmissionForm" property="application.student.number"/></td>
		</tr><tr>
			<td><strong><fmt:message key="prompt.studentName"/></strong></td>
			<td><bean:write name="honsAdmissionForm" property="application.student.name"/></td>
		</tr>
	</sakai:group_table>
	
	<sakai:group_heading>
		<fmt:message key="page.review.subHeading.stuAcadInfo" />
	</sakai:group_heading>		
		<sakai:group_table>
		<tr>
			<td><strong><fmt:message key="prompt.qualification"/></strong></td>
			<td><bean:write name="honsAdmissionForm" property="application.qualification.qualDesc"/></td>
		</tr><tr>
			<td><strong><fmt:message key="prompt.speciality"/></strong></td>
			<td><bean:write name="honsAdmissionForm" property="application.qualification.specDesc"/></td>
		</tr><tr>
			<td><strong><fmt:message key="prompt.choiceNr"/></strong></td>
			<td><bean:write name="honsAdmissionForm" property="application.choice"/></td>
		</tr>
		</sakai:group_table>
		<sakai:group_table>
			<tr><td>
				<sakai:group_heading><fmt:message key="page.review.groupHeading.prevQual"/></sakai:group_heading>
				<logic:equal name="honsAdmissionForm" property="prevUnisaQuals" value="true">
					&nbsp;&nbsp;&nbsp;<a href='<%=(String)request.getAttribute("acadHistUrl")%>' target="_blank"><strong>Unisa Academic Record</strong></a>
				</logic:equal>
				<sakai:group_table>
					<tr>						
						<th align="left"><fmt:message key="column.qualification"/>&nbsp;</th>
						<th align="left"><fmt:message key="column.institution"/>&nbsp;</th>
						<th align="left"><fmt:message key="column.compyear"/>&nbsp;</th>
						<th align="left"><fmt:message key="column.source"/>&nbsp;</th>
						<th align="left"><fmt:message key="column.comment"/>&nbsp;</th>
					</tr>
					<logic:notEmpty name="honsAdmissionForm" property="application.listPrevQual">
						<logic:iterate name="honsAdmissionForm" property="application.listPrevQual" id="prevQualRecord" indexId="i">
						<tr>
							<td><bean:write name="prevQualRecord" property="qualification"/>&nbsp;</td>
							<td><bean:write name="prevQualRecord" property="institution"/></td>
							<td><bean:write name="prevQualRecord" property="yearCompleted"/>&nbsp;</td>
							<td><bean:write name="prevQualRecord" property="accreditationSource"/>&nbsp;</td>
							<td><bean:write name="prevQualRecord" property="comment"/>&nbsp;</td>
						</tr>
						</logic:iterate>
					</logic:notEmpty>
				</sakai:group_table>		
			</td></tr>
		</sakai:group_table>	
		
	
	<sakai:group_heading>
		<fmt:message key="page.review.subHeading.stuDocs" />
	</sakai:group_heading>
		<sakai:group_table>
		  	<logic:notEmpty name="honsAdmissionForm" property="documentList">
				<logic:iterate name="honsAdmissionForm" property="documentList" id="uFile" indexId="i">
				<bean:define id="uniqueId" name="uFile" property="uniqueId"/>
				<bean:define id="version" name="uFile" property="uniflowVersion"/>
				<% 
					java.util.HashMap params = new java.util.HashMap();
					params.put("docUniqueId",uniqueId);
					params.put("uniflowVersion",version);
					pageContext.setAttribute("params",params);
				%>
				<tr>
				  <td><html:link href="honsAdmission.do?act=getFile" name="params">
				  <bean:write name="uFile" property="displayName"/></html:link></td>
				</tr>				
				</logic:iterate>
			</logic:notEmpty>
		</sakai:group_table>
	<br/>
	<sakai:group_heading>
		<fmt:message key="page.review.subHeading.referrals"/>
	</sakai:group_heading>
	<sakai:flat_list>
		<tr>
			<th align="left"><fmt:message key="column.referQual"/>&nbsp;</th>			
			<th align="left"><fmt:message key="column.referLevel"/>&nbsp;</th>
			<th align="left"><fmt:message key="column.referBy"/>&nbsp;</th>
			<th align="left"><fmt:message key="column.referOn"/>&nbsp;</th>
			<th align="left"><fmt:message key="column.advisorComment"/>&nbsp;</th>			
		</tr>
		<logic:notEmpty name="honsAdmissionForm" property="application.listReferral">
			<logic:iterate name="honsAdmissionForm" property="application.listReferral" id="referRecord" indexId="i">
			<tr>
				<td>
					<bean:write name="referRecord" property="qualCode"/>&nbsp;
					<bean:write name="referRecord" property="specCode"/>&nbsp;
					<logic:equal name="referRecord" property="type" value="AR">(Appeal)</logic:equal>
				</td>
				 <td>
			    	<logic:equal name="referRecord" property="level" value="F">Final</logic:equal>
					<logic:notEqual name="referRecord" property="level" value="F"><bean:write name="referRecord" property="level"/></logic:notEqual>					
				</td>
				<td><bean:write name="referRecord" property="referUser"/>&nbsp;</td>
				<td><bean:write name="referRecord" property="referDate"/>&nbsp;</td>
				<td><bean:write name="referRecord" property="noteToAcademic"/></td>				
			</tr>
			</logic:iterate>
		</logic:notEmpty>
	</sakai:flat_list>	
	<br/>
	<sakai:group_heading>
		<fmt:message key="page.review.subHeading.recommendation" />
	</sakai:group_heading>
	<sakai:flat_list>
		<tr>
			<td colspan="2">
				<logic:equal name="honsAdmissionForm" property="access" value="update">
					<html:radio property="application.signOff.recommendation" value="ANR"/>
				</logic:equal>
				<logic:notEqual name="honsAdmissionForm" property="access" value="update">
					<html:radio property="application.signOff.recommendation" value="ANR" disabled="true"/>
				</logic:notEqual>	
				<fmt:message key="recommendation.ANR"/>
			</td>				
		</tr><tr>
			<td>&nbsp;&nbsp;&nbsp;&nbsp;</td><td><fmt:message key="prompt.ANRcomment" /></td>
		</tr><tr>
			<td colspan="2">
				<logic:equal name="honsAdmissionForm" property="access" value="update">
					<html:radio property="application.signOff.recommendation" value="AWR"/>
				</logic:equal>
				<logic:notEqual name="honsAdmissionForm" property="access" value="update">
					<html:radio property="application.signOff.recommendation" value="AWR" disabled="true"/>
				</logic:notEqual>
				<fmt:message key="recommendation.AWR"/>
			</td>
		</tr><tr>
			<td>&nbsp;&nbsp;&nbsp;&nbsp;</td><td><fmt:message key="prompt.AWRcomment" /><BR/>
				<logic:equal name="honsAdmissionForm" property="access" value="update">
					<html:textarea name="honsAdmissionForm" property="recommendationAWRComment" rows="3" cols="100"/>
				</logic:equal>	
				<logic:notEqual name="honsAdmissionForm" property="access" value="update">
					<html:textarea name="honsAdmissionForm" property="recommendationAWRComment" rows="3" cols="100" disabled="true"/>
				</logic:notEqual>
			</td>					
		</tr><tr>		
			<td colspan="2">
				<logic:equal name="honsAdmissionForm" property="access" value="update">
					<html:radio property="application.signOff.recommendation" value="DCL"/>
				</logic:equal>
				<logic:notEqual name="honsAdmissionForm" property="access" value="update">
					<html:radio property="application.signOff.recommendation" value="DCL" disabled="true"/>
				</logic:notEqual>	
				<fmt:message key="recommendation.DCL"/>
			</td>
		</tr><tr>
			<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
			<td><fmt:message key="prompt.DCLcomment" /><BR/>
				<logic:equal name="honsAdmissionForm" property="access" value="update">
					<html:textarea name="honsAdmissionForm" property="recommendationDCLComment" rows="3" cols="100"/>
				</logic:equal>
				<logic:notEqual name="honsAdmissionForm" property="access" value="update">
					<html:textarea name="honsAdmissionForm" property="recommendationDCLComment" rows="3" cols="100" disabled="true"/>
				</logic:notEqual>
			</td>
		</tr>
	</sakai:flat_list>	
	<br/>
	<sakai:flat_list>
		<tr>
		  <td><fmt:message key="page.review.subHeadingf.signcomment"/>&nbsp;</td>
		</tr><tr>
		  <td>
			  <logic:equal name="honsAdmissionForm" property="access" value="update">
			  		<html:textarea name="honsAdmissionForm" property="application.signOff.comment" rows="3" cols="100"/>
			  </logic:equal>
			   <logic:notEqual name="honsAdmissionForm" property="access" value="update">
			  		<html:textarea name="honsAdmissionForm" property="application.signOff.comment" rows="3" cols="100" disabled="true"/>
			  </logic:notEqual>
		  </td>
		</tr>
	</sakai:flat_list>	
	<br/>
	<sakai:group_heading>
		<fmt:message key="page.review.subHeading.signOffs" />
	</sakai:group_heading>
	<sakai:flat_list>
		<tr>
			<th align="left"><fmt:message key="column.signOffQual"/>&nbsp;</th>
			<th align="left"><fmt:message key="column.signOffLevel"/>&nbsp;</th>
			<th align="left"><fmt:message key="column.signOffUser"/>&nbsp;</th>
			<th align="left"><fmt:message key="column.signOffDate"/>&nbsp;</th>
			<th align="left">&nbsp;</th>
		</tr>
		<logic:notEmpty name="honsAdmissionForm" property="application.listSignOff">
			<logic:iterate name="honsAdmissionForm" property="application.listSignOff" id="signoffRecord" indexId="index">
				<bean:define id="signOffIndex" value="<%=index.toString()%>"/>
				<logic:notEmpty name="signoffRecord" property="user">
				<tr>
					<td>
						<bean:write name="signoffRecord" property="qualification.qualCode" />&nbsp;
						<bean:write name="signoffRecord" property="qualification.specCode" />&nbsp;
						<logic:equal name="signoffRecord" property="referType" value="AR">(Appeal)</logic:equal>
					</td>
				    <td>
				    	<logic:equal name="signoffRecord" property="level" value="F">Final</logic:equal>
						<logic:notEqual name="signoffRecord" property="level" value="F"><bean:write name="signoffRecord" property="level"/></logic:notEqual>					
					</td>
					<td><bean:write name="signoffRecord" property="user" />&nbsp;</td>
					<td><bean:write name="signoffRecord" property="date"/></td>
					<td><html:link href="honsAdmission.do?act=viewRecommendation" paramName="signOffIndex" paramId="selectedSignOffIndex">
						       <fmt:message key="link.recommendation" />
						    </html:link></td>
					<!-- <td style="word-break:break-all;"><bean:write name="signoffRecord" property="comment"/>&nbsp;</td> -->
				</tr>
				</logic:notEmpty>
			</logic:iterate>
		</logic:notEmpty>
	</sakai:flat_list>	
	</br>
	</br>		
	<sakai:actions>
		<logic:equal name="honsAdmissionForm" property="access" value="update">
			<html:submit property="act" onclick="javascript:disabled=true;doAction();">
					<fmt:message key="button.signOff"/>
			</html:submit>
		</logic:equal>
		<logic:notEqual name="honsAdmissionForm" property="access" value="update">
			<html:submit property="act" disabled="true">
					<fmt:message key="button.signOff"/>
			</html:submit>
		</logic:notEqual>
		<html:submit property="act">
				<fmt:message key="button.back"/>
		</html:submit>
	</sakai:actions>
</html:form>
</sakai:html>
