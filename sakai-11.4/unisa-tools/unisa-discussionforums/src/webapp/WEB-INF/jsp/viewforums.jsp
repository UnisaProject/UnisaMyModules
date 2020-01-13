<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ page import="java.util.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<fmt:setBundle basename="za.ac.unisa.lms.tools.discussionforums.ApplicationResources"/>



<sakai:html>
	<c:if test="${forumDetailsForm.addForum == true}">
		<sakai:tool_bar>
			<html:link href="forums.do?action=createForum&editForum=false">
				<fmt:message key="forum.link.createForum"/>
			</html:link>
		</sakai:tool_bar>
	</c:if>
	<sakai:messages/>
	<sakai:messages message="true"/>
	<sakai:heading><fmt:message key="forum.index.heading"/>&nbsp;<bean:write name="forumDetailsForm" property="title"/></sakai:heading>
	
	<!-- Unisa Changes:2018/10/10: Add styling for table border used in sakai:flat_list tag -->
	<!-- Style can be added in body according to this: https://developer.mozilla.org/en-US/docs/Web/HTML/Element/style#A_scoped_stylesheet -->
	<style type="text/css">
		table.listHier, tr, th, td{
			border: none !important
		}
	</style>
	<!-- End Unisa Changes -->
	
	<logic:empty name="forumDetailsForm" property="siteForums">
		<sakai:flat_list>
			<tr><td colspan="2"><fmt:message key="forum.forumsNotAvailable"/></td></tr>
		</sakai:flat_list>
	</logic:empty>
	<logic:notEmpty name="forumDetailsForm" property="siteForums">
		<sakai:instruction>
			<fmt:message key="forum.index.information0.new"></fmt:message><br><br>
			<fmt:message key="forum.index.information1.new"></fmt:message>
			<fmt:message key="forum.index.information3.new"></fmt:message><br><br>
			<fmt:message key="forum.index.information4.new"></fmt:message><br><br>
		</sakai:instruction>
		<sakai:flat_list>
			<tr>
				<th  width="50%">					
					<logic:equal name="forumDetailsForm" property="sortIcon" value="1">
						<html:link href="forums.do?action=showForums&sortBy=Forum_Name&sortOrder=Asc&sortIcon=2"><fmt:message key="forum.label.forumName"/><img src="/library/image/sakai/sortascending.gif"/></html:link>
					</logic:equal>
					<logic:equal name="forumDetailsForm" property="sortIcon" value="2">
						<html:link href="forums.do?action=showForums&sortBy=Forum_Name&sortOrder=Desc&sortIcon=1"><fmt:message key="forum.label.forumName"/><img src="/library/image/sakai/sortdescending.gif"/></html:link>
					</logic:equal>	
					<logic:notEqual name="forumDetailsForm" property="sortIcon" value="1">
						<logic:notEqual name="forumDetailsForm" property="sortIcon" value="2">
							<html:link href="forums.do?action=showForums&sortBy=Forum_Name&sortOrder=Asc&sortIcon=1"><fmt:message key="forum.label.forumName"/></html:link>
						</logic:notEqual>
					</logic:notEqual>						
									
				</th>
				<th><fmt:message key="forum.label.topics"/></th>
				<th><fmt:message key="forum.label.posts"/></th>
				<th>
					<logic:equal name="forumDetailsForm" property="sortIcon" value="3">
						<html:link href="forums.do?action=showForums&sortBy=Last_Post_Date&sortOrder=Asc&sortIcon=4"><fmt:message key="forum.label.lastPost"/><img src="/library/image/sakai/sortascending.gif"/></html:link>
					</logic:equal>
					
					<logic:equal name="forumDetailsForm" property="sortIcon" value="4">
						<html:link href="forums.do?action=showForums&sortBy=Last_Post_Date&sortOrder=Desc&sortIcon=3"><fmt:message key="forum.label.lastPost"/><img src="/library/image/sakai/sortdescending.gif"/></html:link>
					</logic:equal>
					
					<logic:notEqual name="forumDetailsForm" property="sortIcon" value="3">
						<logic:notEqual name="forumDetailsForm" property="sortIcon" value="4">
							<html:link href="forums.do?action=showForums&sortBy=Last_Post_Date&sortOrder=Asc&sortIcon=3"><fmt:message key="forum.label.lastPost"/></html:link>
						</logic:notEqual>
					</logic:notEqual>										
				</th>
				<th>
					<logic:equal name="forumDetailsForm" property="sortIcon" value="5">
						<html:link href="forums.do?action=showForums&sortBy=Creation_Date&sortOrder=Asc&sortIcon=6"><fmt:message key="forum.label.creationDate"/><img src="/library/image/sakai/sortascending.gif"/></html:link>
					</logic:equal>
					<logic:equal name="forumDetailsForm" property="sortIcon" value="6">
						<html:link href="forums.do?action=showForums&sortBy=Creation_Date&sortOrder=Desc&sortIcon=5"><fmt:message key="forum.label.creationDate"/><img src="/library/image/sakai/sortdescending.gif"/></html:link>
					</logic:equal>		
					<logic:notEqual name="forumDetailsForm" property="sortIcon" value="5">
						<logic:notEqual name="forumDetailsForm" property="sortIcon" value="6">
							<html:link href="forums.do?action=showForums&sortBy=Creation_Date&sortOrder=Asc&sortIcon=5"><fmt:message key="forum.label.creationDate"/></html:link>
						</logic:notEqual>
					</logic:notEqual>
				</th>
			</tr>
			<logic:iterate name="forumDetailsForm" property="siteForums" id="c" indexId="cindex">
				<tr>
					<td width="50%">
						<html:link href="forumtopic.do?action=showTopics" paramName="c" paramProperty="forumId" paramId="forumId">
							<bean:write name="c" property="forumName"/>
						</html:link>
						<c:if test="${forumDetailsForm.updateAnyForum == true}">
							<logic:notEqual name="c" property="forumName" value="General Subject Related Discussions">
								<html:link href="forums.do?action=editForum&editForum=true" paramName="c" paramProperty="forumId" paramId="forumId">
									<img src="./images/edit.gif" alt="Click to edit a forum" />
								</html:link>
								<html:link href="forums.do?action=editForum&editForum=false" paramName="c" paramProperty="forumId" paramId="forumId">
									<img src="/library/image/sakai/delete.gif" alt="Click to delete the forum" />
								</html:link>
							</logic:notEqual>
						</c:if>						
						<c:if test="${forumDetailsForm.updateAnyForum == false}">
							<c:if test="${forumDetailsForm.updateOwnForum == true}">
								<logic:equal name="forumDetailsForm" property="forum.userId" value="${c.userId}">
									<logic:notEqual name="c" property="forumName" value="General Subject Related Discussions">
										<html:link href="forums.do?action=editForum&editForum=true" paramName="c" paramProperty="forumId" paramId="forumId">
											<img src="./images/edit.gif" alt="Click to edit a forum" />
										</html:link>
										<html:link href="forums.do?action=editForum&editForum=false" paramName="c" paramProperty="forumId" paramId="forumId">
											<img src="/library/image/sakai/delete.gif" alt="Click to delete the forum" />
										</html:link>
									</logic:notEqual>
								</logic:equal>
							</c:if>
						</c:if>						
					</td>
					<td>
						<bean:write name="c" property="forumTopicsCount"/>
					</td>
					<td>
						<bean:write name="c" property="forumPosts"/>
					</td>
					<td>
						<logic:empty name="c" property="lastPostDate">
							<bean:write name="c" property="lastPoster"/>
						</logic:empty>
						<logic:notEmpty  name="c" property="lastPostDate">
							<bean:write name="c" property="lastPostDate"/>&nbsp;<fmt:message key="by.text"/>&nbsp;<bean:write name="c" property="lastPoster"/>
						</logic:notEmpty>
					</td>
					<td>
						<bean:write name="c" property="creationDate"/>
					</td>
				</tr>
				<tr>
					<td width="45%">
						<bean:write name="c" property="forumDescription" filter="false"/>
					</td>
				</tr>
			</logic:iterate>
		</sakai:flat_list>
	</logic:notEmpty>
</sakai:html>
