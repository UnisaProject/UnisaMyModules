<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>


<fmt:setBundle basename="za.ac.unisa.lms.tools.smsenquiry.ApplicationResources"/>

<sakai:html>	
	<html:form action="/smsEnquiry">
		<html:hidden property="currentPage" value="logEntryDetail"/>
		<sakai:messages/>
		<sakai:messages message="true"/>
		<sakai:heading>
				<fmt:message key="heading.smsDetail"/>
		</sakai:heading>
		<sakai:group_table>	
			<tr>		
				<td colspan="2"><sakai:heading>
						<fmt:message key="heading.smsSender"/>
				</sakai:heading></td>
			</tr>
			<tr>
				<td><fmt:message key="page.sender.name"/></td>
				<td><bean:write name="smsEnquiryForm" property="senderName"/></td>
			</tr>
			<tr>
				<td><fmt:message key="page.sender.email"/></td>
				<td><bean:write name="smsEnquiryForm" property="senderEmail"/></td>
			</tr>
			<tr>
				<td><fmt:message key="page.sender.telno"/></td>
				<td><bean:write name="smsEnquiryForm" property="senderPhoneNumber"/></td>
			</tr>
			<tr>		
				<td colspan="2"><sakai:heading>
						<fmt:message key="heading.smsBatchInfo"/>
				</sakai:heading></td>
			</tr>
			<tr>
				<td><fmt:message key="page.batchNumber"/></td>
				<td><bean:write name="smsEnquiryForm" property="batchNumber"/></td>
			</tr>
			<tr>
				<td><fmt:message key="page.message"/></td>
				<td><bean:write name="smsEnquiryForm" property="message"/></td>
			</tr>			
			<tr>		
				<td colspan="2"><sakai:heading>
					<fmt:message key="heading.smsMTNStatus"/>
				</sakai:heading></td>	
			<tr>
				<td><fmt:message key="page.sms.status"/></td>
				<td><bean:write name="smsEnquiryForm" property="smsStatus"/></td>
			</tr>
			<tr>
				<td><fmt:message key="page.sms.desc"/></td>
				<td><bean:write name="smsEnquiryForm" property="smsStatusDesc"/></td>
			</tr>			
		</sakai:group_table>
		<sakai:actions>
			<html:submit property="action">
				<fmt:message key="button.cancel"/>
			</html:submit>			
		</sakai:actions>		
	</html:form>
</sakai:html>