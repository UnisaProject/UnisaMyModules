<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ page import="java.util.*"%>
<%@ page import="java.lang.String"%>

<fmt:setBundle basename="za.ac.unisa.lms.tools.exampaperonline.ApplicationResources"/>


<sakai:html>	
	<html:form action="/uploadDocument">	
		<html:hidden property="toRole" value='<%=(String)request.getAttribute("toRole")%>'/>
		<html:hidden property="toRecipientName" value='<%=(String)request.getAttribute("toRecipientName")%>'/>				
		<html:hidden property="xpoToAdr" value='<%=(String)request.getAttribute("xpoToAdr")%>'/>		
		<html:hidden property="xpoExamYear" value='<%=(String)request.getAttribute("xpoExamYear")%>'/>
		<html:hidden property="xpoExamPeriod" value='<%=(String)request.getAttribute("xpoExamPeriod")%>'/>
		<html:hidden property="xpoStudyUnit"  value='<%=(String)request.getAttribute("xpoStudyUnit")%>'/>
		<html:hidden property="xpoPaperNo" value='<%=(String)request.getAttribute("xpoPaperNo")%>'/>
		<html:hidden property="xpoDocType" value='<%=(String)request.getAttribute("xpoDocType")%>'/>		
		<html:hidden property="xpoUploadDocument1" value='<%=(String)request.getAttribute("xpoUploadDocument1")%>'/>
		<html:hidden property="xpoUploadDocument2" value='<%=(String)request.getAttribute("xpoUploadDocument2")%>'/>
		<html:hidden property="xpoUploadDocument3" value='<%=(String)request.getAttribute("xpoUploadDocument3")%>'/>
		<html:hidden property="xpoUploadDocument4" value='<%=(String)request.getAttribute("xpoUploadDocument4")%>'/>
		<html:hidden property="xpoAction" value=""/>
		<logic:equal name="examPaperOnlineForm" property="paperContent" value="MEMO">
			<html:hidden property="xpoIsMemo" value="Yes"/>
		</logic:equal>
		<logic:notEqual name="examPaperOnlineForm" property="paperContent" value="MEMO">
			<html:hidden property="xpoIsMemo" value="No"/>
		</logic:notEqual>
		<html:hidden property="currentPage" value="uploadStep3"/>
		
		<sakai:messages/>
		<sakai:messages message="true"/>	
		<sakai:heading>
			<fmt:message key="uploadStep3.heading"/>
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
		<sakai:group_heading>
			<fmt:message key="uploadStep3.groupheading1"/> 
		</sakai:group_heading>		
		<sakai:group_table>	
			<tr>
				<td><fmt:message key="prompt.recipient"/></td>
				<% String name = "";
						if (!request.getAttribute("toRecipientName").toString().equalsIgnoreCase("")){
							name = request.getAttribute("toRecipientName").toString() + " - " +  request.getAttribute("toRole").toString();
						} else {
							name = request.getAttribute("toRole").toString();
						}
					%>
				<td><%=name%></td>
			</tr>
			<logic:equal name="examPaperOnlineForm" property="uploadDocumentDetailRequired" value="true">
				<tr>
					<td><fmt:message key="prompt.documents"/></td>
					<td><logic:notEmpty name="examPaperOnlineForm" property="uploadDocument1"><bean:write name="examPaperOnlineForm" property="uploadDocument1"/><BR></logic:notEmpty>
						<logic:notEmpty name="examPaperOnlineForm" property="uploadDocument2"><bean:write name="examPaperOnlineForm" property="uploadDocument2"/><BR></logic:notEmpty>
						<logic:notEmpty name="examPaperOnlineForm" property="uploadDocument3"><bean:write name="examPaperOnlineForm" property="uploadDocument3"/><BR></logic:notEmpty>
						<logic:notEmpty name="examPaperOnlineForm" property="uploadDocument4"><bean:write name="examPaperOnlineForm" property="uploadDocument4"/><BR></logic:notEmpty>
					</td>
				</tr>
			</logic:equal>
			<logic:equal name="examPaperOnlineForm" property="uploadApprovalStatusRequired" value="true">
				<tr>
					<td><fmt:message key="prompt.response"/></td>
					<td><bean:write name="examPaperOnlineForm" property="uploadSendersResponse"/></td>				
				</tr>			
				<tr>
					<td>&nbsp;</td>
					<td><bean:write name="examPaperOnlineForm" property="uploadAdditionalText"/></td>
				</tr>	
			</logic:equal>
			<logic:equal name="examPaperOnlineForm" property="uploadApprovalStatusRequired" value="false">
				<tr>
					<td><fmt:message key="prompt.remarks"/></td>
					<td><bean:write name="examPaperOnlineForm" property="uploadAdditionalText"/></td>
				</tr>	
			</logic:equal>				
		</sakai:group_table>	
		<sakai:actions>
			<html:submit property="action" onclick="javascript:onClickSetXpoActionUpload();">
					<fmt:message key="button.upload"/>
			</html:submit>		
			<html:submit property="action"  onclick="javascript:onClickSetXpoActionDefault();">
					<fmt:message key="button.back"/>
			</html:submit>		
			<html:submit property="action"  onclick="javascript:onClickSetXpoActionDefault();">
					<fmt:message key="button.cancel"/>
			</html:submit>			
		</sakai:actions>		
		<script language="javascript">
			function onClickSetXpoActionUpload(){				
				document.forms["examPaperOnlineForm"].elements["xpoAction"].value="Upload";			
			}
			function onClickSetXpoActionDefault(){				
				document.forms["examPaperOnlineForm"].elements["xpoAction"].value="";			
			}
		</script>	
	</html:form>
</sakai:html>