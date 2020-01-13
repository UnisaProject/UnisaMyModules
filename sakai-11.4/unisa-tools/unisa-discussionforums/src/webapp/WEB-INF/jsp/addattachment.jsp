
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ page import="java.util.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<fmt:setBundle basename="za.ac.unisa.lms.tools.discussionforums.ApplicationResources"/>

<sakai:html>
	<sakai:messages/>
	<sakai:heading><fmt:message key="forum.add.attachment"/></sakai:heading>	
	<sakai:instruction>
		<fmt:message key="forum.attachment.instruction"/>
	</sakai:instruction>
	<html:form action="/topicmessage.do" method="post" enctype="multipart/form-data">
	<html:hidden property="upload" value="upload"/>	
	<input type="hidden" name="topicId" value="${forumMessageForm.forumMessage.topicId}"/>
	<input type="hidden" name="forumId" value="${forumMessageForm.forumMessage.forumId}"/>
	<input type="hidden" name="hidden" value="<%=request.getParameter("hidden") %>"/>
		<sakai:group_table>
			<tr>
				<td>
					<fmt:message key="forum.label.upload.file"/>
				</td>
				<td>					
					<html:file name="forumMessageForm"  property="theFile"/>
				</td>
			</tr>
			<tr>
				<td>
					<fmt:message key="forum.label.upload.link"/>
				</td>
				<td>
					<html:hidden name="forumMessageForm" property="forumMessage.messageReply" value="${forumMessageForm.forumMessage.messageReply}"/>
					<html:text name="forumMessageForm" property="addressLink" style="width: 350px" size="20" maxlength="60"/>
				</td>
			</tr>
			
		</sakai:group_table>
   		<sakai:actions>
   			<html:hidden name="forumMessageForm" property="cancel" value="messages"/>	
			<html:submit styleClass="active" property="action"><fmt:message key="button.continue"/></html:submit>
			<html:submit styleClass="active" property="action"><fmt:message key="button.cancel"/></html:submit>
		</sakai:actions>
	</html:form>
</sakai:html>