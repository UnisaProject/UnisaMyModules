<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>

<fmt:setBundle basename="za.ac.unisa.lms.tools.exampaperonline.ApplicationResources"/>


<sakai:html>	
	<html:form action="/examPaperOnline">		
		<sakai:messages/>
		<sakai:messages message="true"/>		
		<sakai:group_table>	
			<tr>
				<td><fmt:message key="page.user"/>&nbsp;</td>
				<td><bean:write name="examPaperOnlineForm" property="userId"/></td>
			</tr>
			<tr>
				<td><fmt:message key="page.examYear"/>&nbsp;</td>
				<td><bean:write name="examPaperOnlineForm" property="examYear"/></td>
			</tr>
			<tr>
				<td><fmt:message key="page.examPeriod"/>&nbsp;</td>
				<td><bean:write name="examPaperOnlineForm" property="examPeriod"/></td>
			</tr>
			<tr>
				<td><fmt:message key="page.modules"/>&nbsp;</td>
				<td><bean:write name="examPaperOnlineForm" property="modules"/></td>							
			</tr>
			<tr>
				<td><fmt:message key="page.paperNumber"/>&nbsp;</td>
				<td><bean:write name="examPaperOnlineForm" property="paperNo"/></td>
			</tr>
			<tr>
				<td><fmt:message key="page.toAdr"/>&nbsp;</td>
				<td><bean:write name="examPaperOnlineForm" property="toAdr"/></td>
			</tr>
			<tr>
				<td><fmt:message key="page.fileName"/>&nbsp;</td>
				<td><bean:write name="examPaperOnlineForm" property="uploadExamPaper"/></td>
			</tr>
		</sakai:group_table>	
		<sakai:instruction>
			<fmt:message key="note.uploadSuccess"/>
		</sakai:instruction>
		<sakai:actions>
			<html:submit property="action">
					<fmt:message key="button.cancel"/>
			</html:submit>			
		</sakai:actions>			
	</html:form>
</sakai:html>