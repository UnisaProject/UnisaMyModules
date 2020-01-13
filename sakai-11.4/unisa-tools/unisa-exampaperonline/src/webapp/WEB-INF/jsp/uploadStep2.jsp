<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>

<fmt:setBundle basename="za.ac.unisa.lms.tools.exampaperonline.ApplicationResources"/>


<sakai:html>	
	<html:form action="/uploadDocument">	
		<html:hidden property="currentPage" value="uploadStep2"/>
		<html:hidden property="xpoAction" value=""/>
		<sakai:messages/>
		<sakai:messages message="true"/>	
		<sakai:heading>
			<fmt:message key="uploadStep2.heading"/>
		</sakai:heading>				
		<sakai:group_table>	
			<tr>
				<td><fmt:message key="prompt.examination"/>&nbsp;</td>			
				<td><bean:write name="examPaperOnlineForm" property="examPeriodDesc"/>&nbsp;<bean:write name="examPaperOnlineForm" property="examYear"/></td>
			</tr>
			<tr>
				<td><fmt:message key="prompt.paper"/>&nbsp;</td>
				<td><bean:write name="examPaperOnlineForm" property="studyUnit" />&nbsp;
				<logic:equal name="examPaperOnlineForm" property="paperContent" value="QUESTION">Question paper</logic:equal>
				<logic:equal name="examPaperOnlineForm" property="paperContent" value="MEMO">Memorandum paper</logic:equal>&nbsp;
				<bean:write name="examPaperOnlineForm" property="paperNo" />&nbsp;
				<bean:write name="examPaperOnlineForm" property="equivalentStr" /></td>
			</tr>
		</sakai:group_table>	
		<hr/>	
		<logic:equal name="examPaperOnlineForm" property="uploadDocumentDetailRequired" value="true">
			<sakai:instruction>
				<fmt:message key="uploadStep2.note1"/>&nbsp;<fmt:message key="note.required"/>
			</sakai:instruction>
		</logic:equal>
		<logic:equal name="examPaperOnlineForm" property="uploadDocumentDetailRequired" value="false">
			<sakai:instruction>
				<fmt:message key="uploadStep2.note2"/>&nbsp;<fmt:message key="note.required"/>
			</sakai:instruction>
		</logic:equal>				
		<logic:equal name="examPaperOnlineForm" property="uploadDocumentDetailRequired" value="true">
			<sakai:group_table>	
				<logic:equal name="examPaperOnlineForm" property="uploadNrOfDocs" value="1">
					<logic:equal name="examPaperOnlineForm" property="documentsToUploadShown" value="EDIT">
						<tr>
							<td><fmt:message key="prompt.uploadDocument"/>&nbsp;<fmt:message key="prompt.required"/></td>
							<td><html:file name="examPaperOnlineForm" property="uploadDocument1"></html:file></td>	
						</tr>				
					</logic:equal>
					<logic:equal name="examPaperOnlineForm" property="documentsToUploadShown" value="PRINT">
						<tr>
							<td><fmt:message key="prompt.uploadDocument"/>&nbsp;<fmt:message key="prompt.required"/></td>
							<td><html:file name="examPaperOnlineForm" property="uploadDocument3"></html:file></td>	
						</tr>				
					</logic:equal>
				</logic:equal>	
				<logic:equal name="examPaperOnlineForm" property="uploadNrOfDocs" value="2">
					<logic:equal name="examPaperOnlineForm" property="documentsToUploadShown" value="EDIT">
						<tr>
							<td><fmt:message key="prompt.uploadDocument1"/>&nbsp;<fmt:message key="prompt.required"/></td>
							<td><html:file name="examPaperOnlineForm" property="uploadDocument1"></html:file></td>	
						</tr>	
						<tr>
							<td><fmt:message key="prompt.uploadDocument2"/>&nbsp;<fmt:message key="prompt.required"/></td>
							<td><html:file name="examPaperOnlineForm" property="uploadDocument2"></html:file></td>	
						</tr>			
					</logic:equal>	
					<logic:equal name="examPaperOnlineForm" property="documentsToUploadShown" value="PRINT">
						<tr>
							<td><fmt:message key="prompt.uploadDocument1"/>&nbsp;<fmt:message key="prompt.required"/></td>
							<td><html:file name="examPaperOnlineForm" property="uploadDocument3"></html:file></td>	
						</tr>	
						<tr>
							<td><fmt:message key="prompt.uploadDocument2"/>&nbsp;<fmt:message key="prompt.required"/></td>
							<td><html:file name="examPaperOnlineForm" property="uploadDocument4"></html:file></td>	
						</tr>			
					</logic:equal>	
				</logic:equal>	
			</sakai:group_table>
		</logic:equal>
		<logic:equal name="examPaperOnlineForm" property="uploadApprovalStatusRequired" value="false">			
			<sakai:group_table>	
			<logic:notEmpty name="examPaperOnlineForm" property="uploadPrevRemark">	
				<tr>
					<td colspan="2">
						<fmt:message key="prompt.prevRemarks"/>&nbsp;<bean:write name="examPaperOnlineForm" property="uploadPrevSender"/><br/>
					    <html:textarea name="examPaperOnlineForm" property="uploadPrevRemark" cols="100" rows="3" disabled="true"/>
					</td>
				</tr>
			</logic:notEmpty>
			<tr>
				<!--<td><fmt:message key="prompt.remarks"/>&nbsp;<fmt:message key="prompt.required"/></td>  -->
				<td colspan="2"><fmt:message key="prompt.remarks"/>&nbsp;<fmt:message key="prompt.required"/><br/>
				    <html:textarea name="examPaperOnlineForm" property="uploadAdditionalText" cols="100" rows="8"/>
				</td>
			</tr>			
		</sakai:group_table>
		</logic:equal>
		<logic:equal name="examPaperOnlineForm" property="uploadApprovalStatusRequired" value="true">
			<sakai:group_table>
				<tr>
					<td><fmt:message key="prompt.approvalStatus"/>&nbsp;<fmt:message key="prompt.required"/></td>
					<td>
						<logic:iterate name="examPaperOnlineForm" property="listSenderResponses" id="record">
							<bean:define id="recordValue">
								<bean:write name="record" property="code"/>
							</bean:define>
							<html:radio  name="examPaperOnlineForm" property="uploadSendersResponse" value="<%=recordValue%>">
								<bean:write name="record" property="engDescription"/>
							</html:radio>								
						</logic:iterate>	
					</td>					
				</tr>
				<logic:notEmpty name="examPaperOnlineForm" property="uploadPrevRemark">			
					<tr>
						<td colspan="2">
							<fmt:message key="prompt.prevRemarks"/>&nbsp;<bean:write name="examPaperOnlineForm" property="uploadPrevSender"/><br/>
					    	<html:textarea name="examPaperOnlineForm" property="uploadPrevRemark" cols="100" rows="3" disabled="true"/>
					    </td>
					</tr>
				</logic:notEmpty>
				<tr>
					<!-- <td><fmt:message key="prompt.remarks"/>&nbsp;<fmt:message key="prompt.required"/></td> -->
					<td colspan="2">
						<fmt:message key="prompt.remarks"/>&nbsp;<fmt:message key="prompt.required"/><br/>
						<html:textarea name="examPaperOnlineForm" property="uploadAdditionalText" cols="100" rows="8"/>						
					</td>
				</tr>			
			</sakai:group_table>
		</logic:equal>
		<sakai:actions>
			<html:submit property="action"  onclick="javascript:onClickSetXpoActionDefault();">
					<fmt:message key="button.continue"/>
			</html:submit>		
			<html:submit property="action"  onclick="javascript:onClickSetXpoActionDefault();">
					<fmt:message key="button.back"/>
			</html:submit>		
			<html:submit property="action"  onclick="javascript:onClickSetXpoActionDefault();">
					<fmt:message key="button.cancel"/>
			</html:submit>			
		</sakai:actions>
		<script language="javascript">			
			function onClickSetXpoActionDefault(){				
				document.forms["examPaperOnlineForm"].elements["xpoAction"].value="";			
			}
		</script>				
	</html:form>
</sakai:html>