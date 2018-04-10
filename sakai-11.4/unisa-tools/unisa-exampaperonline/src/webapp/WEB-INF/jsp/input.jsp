<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>

<fmt:setBundle basename="za.ac.unisa.lms.tools.exampaperonline.ApplicationResources"/>


<sakai:html>
	<!--<sakai:tool_bar>
		<html:link href="retrieveDocument.do?action=initial">
			<fmt:message key="link.retrieveDocuments"/>
		</html:link>
	</sakai:tool_bar>-->	
	<html:form action="/examPaperOnline">	
		<html:hidden property="currentPage" value="input"/>
		<html:hidden property="xpoAction"/>	
		<html:hidden property="xpoUrl"/>
		<sakai:messages/>
		<sakai:messages message="true"/>
		<sakai:instruction>
			<fmt:message key="input.note1"/>&nbsp;<!--<fmt:message key="note.required"/>-->
		</sakai:instruction>			
		<sakai:group_table>	
			<tr>
				<td><fmt:message key="prompt.examYear"/>&nbsp;</td>
				<td><html:text name="examPaperOnlineForm" property="examYear" size="4" maxlength="4"/></td>
			</tr>
			<tr>
				<td><fmt:message key="prompt.examPeriod"/>&nbsp;</td>
				<td>
					<html:select name="examPaperOnlineForm" property="examPeriod">
					<html:optionsCollection name="examPaperOnlineForm" property="listExamPeriods" value="code" label="engDescription"/>
					</html:select>
				</td>
			</tr>			
			<tr>
				<td><fmt:message key="prompt.studyUnit"/>&nbsp;</td>
				<td><html:text name="examPaperOnlineForm" property="studyUnit" size="10" maxlength="7"/></td>								
			</tr>
			<tr>
				<td><fmt:message key="prompt.paperNumber"/>&nbsp;</td>
				<td><html:text name="examPaperOnlineForm" property="paperNo" size="2" maxlength="1"/></td>
			</tr>
			<tr>
				<td><fmt:message key="prompt.paperContent"/>&nbsp;</td>
				<td><sakai:group_table>
						<logic:iterate name="examPaperOnlineForm" property="listPaperContentTypes" id="record" indexId="index">
							<tr>
								<td><html:radio property="paperContent" idName="record" value="code"></html:radio></td>
								<td><bean:write name="record" property="engDescription"/></td>	
							</tr>							
						</logic:iterate>
					</sakai:group_table>
				</td>								
			</tr>			
			<tr>
				<td><fmt:message key="prompt.selectAction"/>&nbsp;</td>
				<td>
					<html:select name="examPaperOnlineForm" property="toolAction">
					<html:optionsCollection name="examPaperOnlineForm" property="listToolActions" value="code" label="engDescription"/>
					</html:select>
				</td>				
			</tr>				
		</sakai:group_table>	
		<sakai:actions>
			<html:submit property="action"  onclick="javascript:onClickSetXpoActionDefault();">
					<fmt:message key="button.continue"/>
			</html:submit>	
			<html:submit property="action"  onclick="javascript:onClickSetXpoActionDefault();">
					<fmt:message key="button.clear"/>
			</html:submit>			
		</sakai:actions>
		<script language="javascript">			
			function onClickSetXpoActionDefault(){				
				document.forms["examPaperOnlineForm"].elements["xpoAction"].value="";			
			}				
		</script>	
	</html:form>
</sakai:html>