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
		<fmt:message key="page.generalheading"/>
	</sakai:heading>
	<sakai:instruction>
		<fmt:message key="page.step3.instruction"/>
		<fmt:message key="page.step3.notice"/>
	</sakai:instruction>
	<sakai:group_heading>
		<fmt:message key="page.step3.groupheading"/>
	</sakai:group_heading>
	
	
	<html:form action="bulkEmailAction.do?action=step3">
		
		<sakai:group_table>
			
			<tr>
				<td><fmt:message key="page.step3.subject"/><html:text property="messageSubject" size="50" maxlength="50"/></td>
			</tr>
			<tr>
				<td><fmt:message key="page.step3.message"/></td>
			</tr>
			<tr>
				<td><html:textarea property="messageText" cols="35" rows="10"/></td>
			</tr>
			
			<tr>
				<td><fmt:message key="page.step3.reply"/></td>
				
			</tr>
			<tr>
				<td><html:radio property="reply" value="dont" />
				<fmt:message key="page.step3.reply.dont"/>
				</td>
			</tr>
			<tr>
				<td><html:radio property="reply" value="do"/>
				<fmt:message key="page.step3.reply.do"/>
				<html:text property="replyAddress" size="50" maxlength="50"/>
				</td>
			</tr>
			
		</sakai:group_table>
		
		<html:submit value="Send mail" property="button"/>
		<html:submit value="Preview" property="button"/>
		<html:submit value="Back" property="button"/>
		<html:cancel titleKey="button.cancel"/>
 	</html:form>
	
	
	
</sakai:html>