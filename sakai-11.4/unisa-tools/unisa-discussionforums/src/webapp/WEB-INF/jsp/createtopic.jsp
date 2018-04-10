<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ page import="java.util.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<fmt:setBundle basename="za.ac.unisa.lms.tools.discussionforums.ApplicationResources"/>
<script language="JavaScript">
function setAction() {
		document.getElementById("sbmt").style.visibility='hidden';
		document.getElementById("test2").style.display="block";  
	    document.forumTopicsForm.action='forumtopic.do?act=Save'
	    document.forumTopicsForm.submit();
	}
</script>
<sakai:html>
	<sakai:messages/>
	<sakai:heading><fmt:message key="forum.label.createTopic"/>&nbsp;<bean:write name="forumTopicsForm" property="topicForumName"/></sakai:heading>
	<sakai:instruction>
		<fmt:message key="forum.prompt.createTopic"/><br><fmt:message key="forum.field.required"/>&nbsp;<sakai:required/>
	</sakai:instruction>
	<html:form action="/forumtopic.do">
		<sakai:group_table>
			<tr>
				<td>
					<fmt:message key="forum.label.topicTitle"/>&nbsp;<sakai:required/>
				</td>
				<td>
					<html:text  property="forumTopicDetails.topicTitle" size="75" maxlength="250"/>
				</td>
			</tr>
			<tr>
				<td>
					<fmt:message key="forum.label.topicMessage"/>&nbsp;<sakai:required/>
				</td>
				<td>
					<sakai:new_html_area property="forumTopicDetails.topicMessage" cols="66" rows="6"></sakai:new_html_area>
				</td>
			</tr>
		</sakai:group_table>
   		<sakai:actions>
			<html:submit styleClass="active" property="act" onclick="setAction()" styleId="sbmt">Save</html:submit>
			<div id="test2" style="display:none">Processing..</div>
			<html:submit styleClass="active" property="act"><fmt:message key="button.back"/></html:submit>
		</sakai:actions>
	</html:form>
</sakai:html>