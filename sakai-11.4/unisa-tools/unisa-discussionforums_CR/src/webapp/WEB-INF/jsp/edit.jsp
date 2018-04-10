<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ page import="java.util.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<fmt:setBundle basename="za.ac.unisa.lms.tools.discussionforums.ApplicationResources"/>

<sakai:html>
	<sakai:heading>Edit Topic Title</sakai:heading>
	<sakai:messages/>
	<html:form action="forumtopic">
		<sakai:group_table>
			<tr>
			<td>
				<html:text name="forumTopicsForm" property="topicname" size="75" maxlength="250" value="${forumTopicsForm.topicname}"/>
				
            </td>
			</tr>
		</sakai:group_table>
   		<sakai:actions>
			<html:submit styleClass="active" property="act"><fmt:message key="button.edit"/></html:submit>
			<html:submit styleClass="active" property="act"><fmt:message key="button.back"/></html:submit>
		</sakai:actions>
	</html:form>
</sakai:html>