
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
	<logic:equal name="forumDetailsForm" property="editForum" value="false">
		<sakai:heading><fmt:message key="forum.createforum.heading"/>&nbsp;<bean:write name="forumDetailsForm" property="forum.siteId"/></sakai:heading>
	</logic:equal>
	<logic:equal name="forumDetailsForm" property="editForum" value="true">
		<sakai:heading><fmt:message key="forum.editforum.heading"/></sakai:heading>
	</logic:equal>
	<sakai:instruction>
		<logic:equal name="forumDetailsForm" property="editForum" value="false">
			<fmt:message key="forum.createforum.prompt1"/> <fmt:message key="forum.createforum.prompt2"/><br>
		</logic:equal>
		<logic:equal name="forumDetailsForm" property="editForum" value="true">
			<fmt:message key="forum.createforum.prompt3"/><br>
		</logic:equal>
		<fmt:message key="forum.field.required"/>&nbsp;<sakai:required/>
	</sakai:instruction>
	<html:form action="forums">
		<html:hidden property="tmpForumName"/>
		<sakai:group_table>
			<tr>
				<td>
					<fmt:message key="forum.label.forumName"/>&nbsp;<sakai:required/>
				</td>
				<td>
					<html:text  property="forum.forumName" size="75" maxlength="100"/>
				</td>
			</tr>
			
			<!-- Commented out by mphahsm on 2016/11
			<tr>
				<td>
					<fmt:message key="forum.label.forumDescription"/>
				</td>
				<td>
					<sakai:new_html_area property="forum.forumDescription"></sakai:new_html_area>
				</td>
			</tr> End of mphahsm comment-->
			
			<!-- Added by mphahsm on 2016/11: To avoid the use of CKeditor for the forum description and allow plain text -->
			<tr>
				<td>
					<fmt:message key="forum.label.forumDescription"/>
				</td>
				<td>
					<html:textarea property="forum.forumDescription" rows="4" cols="60" />
				</td>
			</tr>
			<!-- End of mphahsm Add -->
		</sakai:group_table>
   		<sakai:actions>
			<html:submit styleClass="active" property="action"><fmt:message key="button.save"/></html:submit>
			<html:submit styleClass="active" property="action"><fmt:message key="button.back"/></html:submit>
		</sakai:actions>
	</html:form>
</sakai:html>