<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>

<fmt:setBundle basename="za.ac.unisa.lms.tools.exampaperonline.ApplicationResources"/>


<sakai:html>	
	<html:form action="/resetDocument">	
		<html:hidden property="currentPage" value="resetStep3"/>
		<html:hidden property="xpoExamYear" value='<%=(String)request.getAttribute("xpoExamYear")%>'/>
		<html:hidden property="xpoExamPeriod" value='<%=(String)request.getAttribute("xpoExamPeriod")%>'/>
		<html:hidden property="xpoStudyUnit"  value='<%=(String)request.getAttribute("xpoStudyUnit")%>'/>
		<html:hidden property="xpoPaperNo" value='<%=(String)request.getAttribute("xpoPaperNo")%>'/>
		<html:hidden property="xpoIsMemo" value='<%=(String)request.getAttribute("xpoIsMemo")%>'/>
		<html:hidden property="xpoAction" value=""/>
		<sakai:messages/>
		<sakai:messages message="true"/>	
		<sakai:heading>
			<fmt:message key="resetStep3.heading"/>
		</sakai:heading>	
		<sakai:group_table>	
			<tr>
				<td><fmt:message key="prompt.examination"/>&nbsp;</td>			
				<td><%=(String)request.getAttribute("examPeriodDesc")%>&nbsp;<bean:write name="examPaperOnlineForm" property="selectedPaper.examYear"/></td>
			</tr>
			<tr>
				<td><fmt:message key="prompt.paper"/>&nbsp;</td>
				<td><bean:write name="examPaperOnlineForm" property="selectedPaper.studyUnit"/>&nbsp;
				<%=(String)request.getAttribute("contentTypeDesc")%>&nbsp;
				<bean:write name="examPaperOnlineForm" property="selectedPaper.paperNo" />&nbsp;
				<bean:write name="examPaperOnlineForm" property="equivalentStr" /></td>
			</tr>		
		</sakai:group_table>	
		<hr/>	
		<sakai:instruction>
			<fmt:message key="resetStep3.note1"/>			
		</sakai:instruction>
		<sakai:instruction>
			<fmt:message key="resetStep3.note2"/>
		</sakai:instruction>	
		<sakai:group_table>
			<tr>
				<td>
					<fmt:message key="prompt.reset.reason"/>&nbsp;<fmt:message key="prompt.required"/><br/>
					<html:textarea name="examPaperOnlineForm" property="resetReason" cols="100" rows="8"/>						
				</td>
			</tr>	
		</sakai:group_table>
		<sakai:actions>		
			<html:submit property="action"  onclick="javascript:onClickSetXpoActionReset();">
					<fmt:message key="button.resetPaper"/>
			</html:submit>					
			<html:submit property="action"  onclick="javascript:onClickSetXpoActionDefault();">
					<fmt:message key="button.back"/>
			</html:submit>			
		</sakai:actions>	
		<script language="javascript">	
		function onClickSetXpoActionReset(){				
			document.forms["examPaperOnlineForm"].elements["xpoAction"].value="Reset";			
		}		
			function onClickSetXpoActionDefault(){				
				document.forms["examPaperOnlineForm"].elements["xpoAction"].value="";			
			}
		</script>			
	</html:form>
</sakai:html>