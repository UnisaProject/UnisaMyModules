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
		document.forumTopicsForm.action = 'forumtopic.do?act=showTopics';
		document.forumTopicsForm.submit();
	}		
</script>

<sakai:html>
	<sakai:tool_bar>
		<html:link href="forumtopic.do?act=returnToForum">
			<fmt:message key="forum.link.returnToForums"/>
		</html:link>
		&nbsp;	<!-- Sifiso Changes:2018/05/17: Add Space between buttons for Sakai 11.4 Links -->
		<c:if test="${forumTopicsForm.topicAddable == true}">
			<html:link href="forumtopic.do?act=createTopic">
				<fmt:message key="forum.link.createTopic"/>
			</html:link>
		</c:if>
	</sakai:tool_bar>
	<sakai:messages/>
	<sakai:messages message="true"/>
	
	<!-- Unisa Changes:2018/10/10: Add styling for table border used in sakai:flat_list tag -->
	<!-- Style can be added in body according to this: https://developer.mozilla.org/en-US/docs/Web/HTML/Element/style#A_scoped_stylesheet -->
	<style type="text/css">
		table.listHier, tr, th, td{
			border: none !important
		}
	</style>
	<!-- End Unisa Changes -->
	
	<logic:notEmpty name="forumTopicsForm" property="topicForumName">
		<sakai:heading><bean:write name="forumTopicsForm" property="topicForumName"/></sakai:heading>
	</logic:notEmpty>
	<logic:empty name="forumTopicsForm" property="topics">
	<sakai:flat_list>
		<tr><td colspan="2"><fmt:message key="forum.topicsNotAvailable"/></td></tr>
	</sakai:flat_list>
	</logic:empty>
	<html:form action="/forumtopic.do">
	<logic:notEmpty name="forumTopicsForm" property="topics">
		<sakai:instruction>
			<fmt:message key="forum.topic.information"/>
			<fmt:message key="forum.topicview.prompt"/>
		</sakai:instruction>
		<sakai:flat_list>
		<sakai:actions>
		<tr>
			<td></td><td></td><td></td><td></td>
			<td align="right"> 
		 		<fmt:message key="label.view"/>&nbsp;
		    	<bean:write name="forumTopicsForm" property="start"/>
				<fmt:message key="label.hypen"/>&nbsp;
				<bean:write name="forumTopicsForm" property="end"/>&nbsp;
				<fmt:message key="label.of"/>&nbsp;
				<bean:write name="forumTopicsForm" property="numberOfItems"/>&nbsp;
				<fmt:message key="label.views"/>
			</td>
		</tr>
		<tr>
		<td></td><td></td><td></td><td></td>
		<td align="right">
			<html:submit property="act">
				<fmt:message key="button.first"/>
			</html:submit>
			<html:submit property="act">
				<fmt:message key="button.previous"/>
			</html:submit>
			 <html:select name="forumTopicsForm" property="records" onchange = "setAction()">
				<html:option value="10">Show 10 items </html:option>			 
				<html:option value="20">Show 20 items </html:option>				
				<html:option value="30">Show 30 items </html:option>
				<html:option value="50">Show 50 items </html:option>								
			</html:select>
			<html:submit property="act">
 				<fmt:message key="button.next"/>
			</html:submit>
			<html:submit property="act">
				<fmt:message key="button.last"/>
			</html:submit>			
		</td>
		</tr>
		</sakai:actions>
		</sakai:flat_list>
			<sakai:flat_list>
			<tr>
				<th width="45%">
					<logic:equal name="forumTopicsForm" property="sortIcon" value="1">
						<html:link href="forumtopic.do?act=showTopics&sortBy=Topic_Subject&sortOrder=Asc&sortIcon=2&records=${forumTopicsForm.records}"><fmt:message key="forum.topic.label.topic"/><img src="/library/image/sakai/sortascending.gif"/></html:link>
					</logic:equal>
					<logic:equal name="forumTopicsForm" property="sortIcon" value="2">
						<html:link href="forumtopic.do?act=showTopics&sortBy=Topic_Subject&sortOrder=Desc&sortIcon=1&records=${forumTopicsForm.records}"><fmt:message key="forum.topic.label.topic"/><img src="/library/image/sakai/sortdescending.gif"/></html:link>
					</logic:equal>
					<logic:notEqual name="forumTopicsForm" property="sortIcon" value="1">
						<logic:notEqual name="forumTopicsForm" property="sortIcon" value="2">
							<html:link href="forumtopic.do?act=showTopics&sortBy=Topic_Subject&sortOrder=Asc&sortIcon=1&records=${forumTopicsForm.records}"><fmt:message key="forum.topic.label.topic"/></html:link>
						</logic:notEqual>
					</logic:notEqual>				
				</th>
				
				<th><b><fmt:message key="forum.topic.label.replies"/></b></th>
				<th><b><fmt:message key="forum.topic.label.author"/></b></th>
		
				<logic:equal name="forumTopicsForm" property="sortIcon" value="3">
					<th><html:link href="forumtopic.do?act=showTopics&sortBy=Last_Post_Date&sortOrder=Asc&sortIcon=4&records=${forumTopicsForm.records}"><fmt:message key="forum.topic.label.lastPost"/><img src="/library/image/sakai/sortascending.gif"/></html:link></th>
				</logic:equal>
				
				<logic:equal name="forumTopicsForm" property="sortIcon" value="4">
					<th><html:link href="forumtopic.do?act=showTopics&sortBy=Last_Post_Date&sortOrder=Desc&sortIcon=3&records=${forumTopicsForm.records}"><fmt:message key="forum.topic.label.lastPost"/><img src="/library/image/sakai/sortdescending.gif"/></html:link></th>
				</logic:equal>	
				<logic:notEqual name="forumTopicsForm" property="sortIcon" value="3">
					<logic:notEqual name="forumTopicsForm" property="sortIcon" value="4">
						<th><html:link href="forumtopic.do?act=showTopics&sortBy=Last_Post_Date&sortOrder=Asc&sortIcon=3&records=${forumTopicsForm.records}"><fmt:message key="forum.topic.label.lastPost"/></html:link></th>
					</logic:notEqual>
				</logic:notEqual>
				<td></td>								
			</tr>
			<logic:iterate name="forumTopicsForm" property="topics" id="c" indexId="cindex">
				<logic:notEqual name="c" property="replies" value="-1">
				<tr>
					<td>
						<html:link href="topicmessage.do?act=showMessages&topicViewed=true&hidden=0&upload=no&cancel=no&forumId=${forumTopicsForm.forumId}&forumDesc=${forumTopicsForm.topicForumName}&siteId=" paramName="c" paramProperty="topicId" paramId="topicId">
							<bean:write name="c" property="topicTitle"/>
						</html:link>
						<c:if test="${forumTopicsForm.topicDeletable == false}">						
							<logic:notEqual name="c" property="topicTitle" value="General Discussions">
								<logic:equal name="forumDetailsForm" property="forum.userId" value="${c.userId}">
									<html:link href="forumtopic.do?act=confirmTopicDelete" paramName="c" paramProperty="topicId" paramId="topicId">
										<img src="/library/image/sakai/delete.gif" alt="Click to delete the topic" />
									</html:link>
									<html:link href="forumtopic.do?act=editTopic" paramName="c" paramProperty="topicId" paramId="topicId">
										<html:img page="/images/edit.gif" alt="Click to edit the topic" />
									</html:link>
								</logic:equal>
							</logic:notEqual>
						</c:if>
						<c:if test="${forumTopicsForm.topicDeletable == true}">						
							<logic:notEqual name="c" property="topicTitle" value="General Discussions">
								<html:link href="forumtopic.do?act=confirmTopicDelete" paramName="c" paramProperty="topicId" paramId="topicId">
									<img src="/library/image/sakai/delete.gif" alt="Click to delete the topic" />
								</html:link>
									<html:link href="forumtopic.do?act=editTopic" paramName="c" paramProperty="topicId" paramId="topicId">
										<html:img page="/images/edit.gif" alt="Click to edit the topic" />
									</html:link>
							</logic:notEqual>
						</c:if>
					</td>
					<td>
						<bean:write name="c" property="replies"/>
					</td>
					<td>
						<bean:write name="c" property="topicAuthor"/>
					</td>
					
					<logic:empty  name="c" property="lastPostDate">
						<td>No Posting</td>
					</logic:empty>
					<logic:notEmpty name="c" property="lastPostDate">
					<td>
						<bean:write name="c" property="lastPostDate"/>&nbsp;<fmt:message key="by.text"/>&nbsp;<bean:write name="c" property="lastPostUser"/>						
					</td>
					</logic:notEmpty>
				</tr>
				</logic:notEqual>
			</logic:iterate>
		</sakai:flat_list>
	</logic:notEmpty>
	<input type="hidden" name="act" value= "returnToForum"/>
   	<sakai:actions>
		<html:submit styleClass="active" property="act"><fmt:message key="button.backToForums"/></html:submit>
	</sakai:actions>
	</html:form>
</sakai:html>