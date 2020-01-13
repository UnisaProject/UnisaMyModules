<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>

<fmt:setBundle basename="za.ac.unisa.lms.tools.exampaperonline.ApplicationResources"/>


<sakai:html>	
	<html:form action="/retrieveDocument">	
		<html:hidden property="currentPage" value="retrieveStep2"/>
		<html:hidden property="xpoAction" value=""/>
		<sakai:messages/>
		<sakai:messages message="true"/>			
		<sakai:heading>
			<fmt:message key="retrieveStep2.heading"/>
		</sakai:heading>
		<sakai:group_table>	
			<tr>
				<td><fmt:message key="prompt.examination"/>&nbsp;</td>			
				<td><%=(String)request.getAttribute("examPeriodDesc")%>&nbsp;
				<bean:write name="examPaperOnlineForm" property="selectedPaper.examYear"/></td>
			</tr>
			<tr>
				<td><fmt:message key="prompt.paper"/>&nbsp;</td>
				<td><bean:write name="examPaperOnlineForm" property="selectedPaper.studyUnit"/>
				&nbsp;<%=(String)request.getAttribute("contentTypeDesc")%>
				&nbsp;<bean:write name="examPaperOnlineForm" property="selectedPaper.paperNo" />&nbsp;
				<bean:write name="examPaperOnlineForm" property="equivalentStr" /></td>
			</tr>		
		</sakai:group_table>	
		<hr/>
		<sakai:group_heading>
			<fmt:message key="retrieveStep2.groupheading2"/> 
		</sakai:group_heading>	
		<sakai:group_table>
			<tr>			
				<td><fmt:message key="prompt.user"/>&nbsp;</td>			
				<td><bean:write name="examPaperOnlineForm" property="selectedPaper.fromUser" />&nbsp;&nbsp;-&nbsp;&nbsp;<bean:write name="examPaperOnlineForm" property="selectedPaper.fromRole" /></td>
			</tr><tr>
				<td><fmt:message key="prompt.dateSent"/>&nbsp;</td>	
				<td><bean:write name="examPaperOnlineForm" property="selectedPaper.dateSent" /></td>
			</tr><tr>
				<td><fmt:message key="prompt.response"/>&nbsp;</td>	
				<td><bean:write name="examPaperOnlineForm" property="selectedPaper.senderResponseText" /></td>
			</tr>
		</sakai:group_table>
		<sakai:group_heading>
			<fmt:message key="retrieveStep2.groupheading1"/> 
		</sakai:group_heading>	
		<sakai:instruction>
			<fmt:message key="retrieveStep2.note1"/>
		</sakai:instruction>
		<sakai:group_table>
			<logic:empty name="examPaperOnlineForm" property="selectedPaper.documentList">
				<tr><td colspan="2">
					<fmt:message key="retrieveStep2.note2"/>
				</td></tr> 
			</logic:empty>
			<logic:notEmpty name="examPaperOnlineForm" property="selectedPaper.documentList">
				<logic:iterate name="examPaperOnlineForm" property="selectedPaper.documentList" id="record">
					<bean:define id="studyUnit" name="examPaperOnlineForm" property="selectedPaper.studyUnit"/>
					<bean:define id="examYear" name="examPaperOnlineForm" property="selectedPaper.examYear"/>
					<bean:define id="examPeriod" name="examPaperOnlineForm" property="selectedPaper.examPeriod"/>
					<bean:define id="paperNo" name="examPaperOnlineForm" property="selectedPaper.paperNo"/>
					<bean:define id="docType" name="examPaperOnlineForm" property="selectedPaper.docType"/>					
					<bean:define id="pathAndFileName" name="record" property="pathAndFileName"/>		
					<% 
						java.util.HashMap params = new java.util.HashMap();
						params.put("xpoStudyUnit",studyUnit);
						params.put("xpoExamYear",examYear);
						params.put("xpoExamPeriod",examPeriod);
						params.put("xpoPaperNo",paperNo);
						params.put("xpoDocType",docType);
						params.put("xpoPathAndFileName",pathAndFileName);
						params.put("xpoAction","Open");						
						pageContext.setAttribute("params",params);
					%>
					<tr> 				
						<td>							
							<bean:write name="record" property="fileName"/>
						</td>
						<td><html:link href="retrieveDocument.do?action=open" scope="page" name="params">                                   							
									<fmt:message key="link.open"/>
							</html:link>
						</td>
					</tr>
				</logic:iterate>
			</logic:notEmpty>
		</sakai:group_table>
		<sakai:actions>				
			<html:submit property="action" onclick="javascript:onClickSetXpoActionDefault();">
				<fmt:message key="button.back"/>
			</html:submit>					
		</sakai:actions>	
		<script language="javascript">			
		function onClickSetXpoActionDefault(){				
			document.forms["examPaperOnlineForm"].elements["xpoAction"].value="";						}
		</script>		
	</html:form>
</sakai:html>