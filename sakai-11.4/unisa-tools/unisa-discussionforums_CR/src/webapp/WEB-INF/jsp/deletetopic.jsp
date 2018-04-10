<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ page import="java.util.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<fmt:setBundle basename="za.ac.unisa.lms.tools.discussionforums.ApplicationResources"/>

<sakai:html>
	<sakai:heading><fmt:message key="forum.topic.delete"/></sakai:heading>
	<sakai:messages/>
	<html:form action="forumtopic">
		<sakai:group_table>
			<tr>
				<td><bean:write name="forumTopicsForm" property="topicForumName"/>&nbsp;<bean:write name="forumTopicsForm" property="forumTopicDetails.topicTitle"/></td>
			</tr>
		</sakai:group_table>
   		<sakai:actions>
			<html:submit styleClass="active" property="act"><fmt:message key="button.delete"/></html:submit>
			<html:submit styleClass="active" property="act"><fmt:message key="button.back"/></html:submit>
		</sakai:actions>
	</html:form>
</sakai:html>