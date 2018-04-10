<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>

<fmt:setBundle basename="za.ac.unisa.lms.tools.exampaperonline.ApplicationResources"/>


<sakai:html>	
	<html:form action="/uploadDocument">	
		<html:hidden property="currentPage" value="uploadStep1"/>
		<html:hidden property="xpoAction" value=""/>
		<sakai:messages/>
		<sakai:messages message="true"/>	
		<sakai:heading>
			<fmt:message key="uploadStep1.heading"/>
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
		<sakai:instruction>
			<fmt:message key="note.required"/>
		</sakai:instruction>
		<sakai:group_table>	
			<tr>
				<td><fmt:message key="prompt.selectRecipient"/>&nbsp;<fmt:message key="prompt.required"/></td>
				<td>
					<html:select name="examPaperOnlineForm" property="uploadSelectedRecipientIndex">
						<logic:iterate name="examPaperOnlineForm" property="listRecipients" id="record" indexId="index">
							<html:option value='<%= "" + index.toString() + "" %>'><logic:notEmpty name="record" property="person"><bean:write name="record" property="person.name"/>&nbsp;&nbsp;&nbsp;-&nbsp;&nbsp;&nbsp;</logic:notEmpty><bean:write name="record" property="role"/></html:option>	
						</logic:iterate>
					</html:select>					
				</td>
			</tr>
			<logic:equal name="examPaperOnlineForm" property="firstUpload" value="true">
				<tr>
					<td><fmt:message key="prompt.nrOfDocumentsToBeSubmitted"/>&nbsp;<fmt:message key="prompt.required"/>&nbsp;</td>
					<td><html:radio name="examPaperOnlineForm" property="uploadNrOfDocs" value="1">
							<fmt:message key="prompt.nrOfDocsSubmitted1"/><BR/>
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<fmt:message key="prompt.nrOfDocsSubmitted1DescLine1"/><BR/>
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<fmt:message key="prompt.nrOfDocsSubmitted1DescLine2"/>
						</html:radio>
					</td>
				</tr><tr>			
					<td>&nbsp;</td>
					<td>
						<html:radio name="examPaperOnlineForm" property="uploadNrOfDocs" value="2">
						<fmt:message key="prompt.nrOfDocsSubmitted2"/><BR>
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<fmt:message key="prompt.nrOfDocsSubmitted2DescLine1"/><BR/>
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<fmt:message key="prompt.nrOfDocsSubmitted2DescLine2"/>
						</html:radio>
					</td>								
				</tr>	
			</logic:equal>			
		</sakai:group_table>	
		<sakai:actions>
			<html:submit property="action" onclick="javascript:onClickSetXpoActionDefault();">
					<fmt:message key="button.continue"/>
			</html:submit>		
			<html:submit property="action" onclick="javascript:onClickSetXpoActionDefault();">
					<fmt:message key="button.back"/>
			</html:submit>		
			<html:submit property="action" onclick="javascript:onClickSetXpoActionDefault();">
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