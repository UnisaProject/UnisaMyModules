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
	    document.forumMessageForm.action='topicmessage.do?action=button.save'
	    document.forumMessageForm.submit();
	}
</script>
<sakai:html>
	<sakai:tool_bar>
		<html:link href="forumtopic.do?act=returnToForum">
			<fmt:message key="forum.link.returnToForums"/>
		</html:link>
		&nbsp;	<!-- Sifiso Changes:2018/05/17: Add Space between buttons for Sakai 11.4 Links -->
		<html:link href="forumtopic.do?act=showTopics">
			<fmt:message key="forum.link.returnToTopics"/>
		</html:link>
	</sakai:tool_bar>
	<sakai:messages/>
	<sakai:messages message="true"/>
	<sakai:heading><bean:write name="forumMessageForm" property="forumDesc"/> : <bean:write name="forumMessageForm" property="messageTopic"/></sakai:heading>
	<sakai:instruction>
		<fmt:message key="forum.message.information"/><br>
		<fmt:message key="forum.messageview.prompt"/>
	</sakai:instruction>
		<sakai:flat_list>
			<html:form action="/topicmessage.do">
			<sakai:actions>
				<tr>
					<td></td>
					<td align="right"> 
		 				<fmt:message key="label.view"/>&nbsp;
		    			<bean:write name="forumMessageForm" property="start"/>&nbsp;
						<fmt:message key="label.hypen"/>&nbsp;
						<bean:write name="forumMessageForm" property="end"/>&nbsp;
						<fmt:message key="label.of"/>&nbsp;
						<bean:write name="forumMessageForm" property="numberOfItems"/>&nbsp;
						<fmt:message key="label.views"/>
					</td>
				</tr>
				<tr>
					<td></td>
					<td align="right">
						<html:submit property="action">
							<fmt:message key="button.first"/>
						</html:submit>
						<html:submit property="action">
							<fmt:message key="button.previous"/>
						</html:submit>
			 			<html:select name="forumMessageForm" property="msgRecords" onchange = "submit();">
							<html:option value="10">Show 10 items </html:option>			 
							<html:option value="20">Show 20 items </html:option>				
							<html:option value="30">Show 30 items </html:option>
							<html:option value="50">Show 50 items </html:option>								
						</html:select>
						<html:submit property="action">
 							<fmt:message key="button.next"/>
						</html:submit>
						<html:submit property="action">
							<fmt:message key="button.last"/>
						</html:submit>			
					</td>
				</tr>
			</sakai:actions>
			<input type="hidden" name="action" value= "showMessages"/>
			<input type="hidden" name="upload" value="<%=request.getParameter("upload") %>"/>
	   		<input type="hidden" name="topicId" value="<%=request.getParameter("topicId") %>"/>
	   		<input type="hidden" name="hidden" value="<%=request.getParameter("hidden") %>"/>
	   		<html:hidden name="forumMessageForm" property="cancel" value="messages"/>	
			</html:form>
			<tr>
				<th width="20%"><fmt:message key="forum.topic.label.author"/></th>
				<th><fmt:message key="forum.topic.label.message"/></th>
			</tr>
			<tr bgcolor="#FFDAB5">
			<td width="20%"><bean:write name="forumMessageForm" property="forumMessage.author"/></td>
			<td><bean:write name="forumMessageForm" property="forumMessage.message" filter="false"/> (<bean:write name="forumMessageForm" property="forumMessage.messageDate"/>)</td>
			</tr>
			<logic:notEmpty name="forumMessageForm" property="topicMessages">
			<logic:iterate name="forumMessageForm" property="topicMessages" id="c" indexId="cindex">
				<logic:equal name="c" property="coloured" value="0">
					<tr>
				</logic:equal>
				<logic:equal name="c" property="coloured" value="1">
					<tr bgcolor="#eeeeee">
				</logic:equal>
					<td width="20%" valign="top">
						<bean:write name="c" property="author"/>
					</td>
					<td>
						<bean:write name="c" property="message" filter="false"/> (<bean:write name="c" property="messageDate"/>)
						<c:if test="${forumMessageForm.anyReplyDeledable == true}">
							<html:link href="topicmessage.do?action=confirmMessageDelete" paramName="c" paramProperty="messageId" paramId="forumMessage.messageId">
								<img src="/library/image/sakai/delete.gif" alt="Click to delete the message" />
							</html:link>
						</c:if>
						<c:if test="${forumMessageForm.anyReplyDeledable == false}">
							<logic:equal name="forumDetailsForm" property="forum.userId" value="${c.userId}">
								<html:link href="topicmessage.do?action=confirmMessageDelete" paramName="c" paramProperty="messageId" paramId="forumMessage.messageId">
									<img src="/library/image/sakai/delete.gif" alt="Click to delete the message" />
								</html:link>
							</logic:equal>
						</c:if>
						<logic:notEqual name="c" property="attachment" value="">
							<logic:equal name="c" property="fileType" value="F">
								<!--
								<html:link href="/discussionForums/${c.attachment}" target="_new">
									Attachment
								</html:link> -->
								<!-- Added by mphahsm on 2016/05 to open/download attachment from the method -->
								<html:link href="topicmessage.do?action=readAttachment&attachment=${c.attachment}">
									Attachment
								</html:link>
								<!-- End of mphahsm -->
							</logic:equal>
							<logic:equal name="c" property="fileType" value="L">
								<html:link href="javascript:window.open('${c.attachment}','_new')" target="_new">
									${c.attachment}
								</html:link>
							</logic:equal>						
						</logic:notEqual>
					</td>
				</tr>
			</logic:iterate>
			</logic:notEmpty>
		</sakai:flat_list>
	<br/><br/><br/>
	<c:if test="${forumMessageForm.replyAddable == true}">
		<logic:equal name="forumMessageForm" property="hidden" value="0">
		<sakai:group_heading><fmt:message key="forum.label.messageReply"/></sakai:group_heading>
		<sakai:instruction>
			<fmt:message key="forum.message.add.prompt"/>
		</sakai:instruction>

			<html:form action="/topicmessage.do">
			
			<sakai:group_table>
				<tr bgcolor="#FFFDD0" cellpadding="0" cellspacing="0">
					<td>
						<fmt:message key="forum.message.add.label"/><sakai:required/>
					</td>
					<td>
						<sakai:new_html_area name="forumMessageForm" property="forumMessage.messageReply" cols="66" rows="6"></sakai:new_html_area>
					</td>
				</tr>
			</sakai:group_table>
			<sakai:group_heading><fmt:message key="forum.label.attachments"/></sakai:group_heading>	
			<sakai:group_table>
				<logic:equal name="forumMessageForm" property="upload" value="no">
					<tr><td><fmt:message key="forum.info.attachments"/></td></tr>
				</logic:equal>
				<logic:equal name="forumMessageForm" property="upload" value="upload">
					<logic:notEqual name="forumMessageForm" property="filename" value="">
						<tr>
							<td><bean:write name="forumMessageForm" property="filename"/></td> 
							<td><html:link href="topicmessage.do?action=clearAttachment&fname=${forumMessageForm.filename}">Clear</html:link></td>
						</tr>
					</logic:notEqual>
					<logic:notEqual name="forumMessageForm" property="addressLink" value="">
						<tr>
							<td><a href="javascript:window.open('${forumMessageForm.addressLink}','_new')" target="_new">${forumMessageForm.addressLink}</a></td> 
							<td><html:link href="topicmessage.do?action=clearAttachment&fname=clearUrl">Clear</html:link></td>
						</tr>
					</logic:notEqual>
					
				</logic:equal>				
			</sakai:group_table>				
	   	<sakai:actions>
	   		<input type="hidden" name="upload" value="<%=request.getParameter("upload") %>"/>
	   		<input type="hidden" name="topicId" value="<%=request.getParameter("topicId") %>"/>
	   		<input type="hidden" name="hidden" value="<%=request.getParameter("hidden") %>"/>
	   		<input type="hidden" name="inputFileName" value="<%=request.getAttribute("inputFileName") %>"/>
	   		<input type="hidden" name="forumId" value="<%=request.getParameter("forumId") %>"/>
	   		<html:hidden name="forumMessageForm" property="cancel" value="topics"/>	
	   		<html:submit styleClass="active" property="action"><fmt:message key="button.add.attachment"/></html:submit>
	   		<br><br><hr><br>
	   		<html:submit styleClass="active" property="action" onclick="setAction()" styleId="sbmt"><fmt:message key="button.save"/></html:submit>
			<div id="test2" style="display:none">Processing..</div><html:submit styleClass="active" property="action"><fmt:message key="button.back"/></html:submit> 
		</sakai:actions>
		</html:form>
		</logic:equal>
	</c:if>
</sakai:html>
