<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<fmt:setBundle basename="za.ac.unisa.lms.tools.bulkemail.ApplicationResources"/>
<sakai:html>
	<sakai:messages/>
	<sakai:messages message="true"/>
	<sakai:heading>
		<fmt:message key="page.preview.heading"/>
	</sakai:heading>
	
	<html:form action="bulkEmailAction.do?action=preview">
		
		<sakai:group_table>
			<tr>
				<td><fmt:message key="page.preview.subject"/></td>
				<td><bean:write name="bulkEmailForm" property="messageSubject"/></td>
			</tr>
			
			<tr>
				<td><fmt:message key="page.preview.sendfrom"/></td>
				<td><bean:write name="bulkEmailForm" property="replyAddress"/></td>
			</tr>
			<tr>
				<td><fmt:message key="page.preview.date"/></td>
				<td><bean:write name="bulkEmailForm" property="date"/></td>
			</tr>
			<tr>
				<td><fmt:message key="page.preview.to"/></td>
				<td>
					<logic:iterate id="sites" name="mySites" indexId="index">
						(<bean:write name="sites"/>) &nbsp;
					</logic:iterate>
				</td>
			</tr>
			<tr>
				<td><fmt:message key="page.preview.message"/></td>
				<td colspan="5"><bean:write name="bulkEmailForm" property="messageText"/></td>
			</tr>
		</sakai:group_table>
		
		<html:submit value="Send mail" property="button"/>
		<html:submit value="Revise" property="button"/>
		<html:cancel titleKey="button.cancel"/>
 	</html:form>
	
	
	
</sakai:html>