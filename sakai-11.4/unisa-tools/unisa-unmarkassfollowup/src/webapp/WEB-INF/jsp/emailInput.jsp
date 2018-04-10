<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>

<fmt:setBundle basename="za.ac.unisa.lms.tools.unmarkassfollowup.ApplicationResources"/>


<sakai:html>
	<html:form action="/unmarkAssFollowUp">	
	<html:hidden property="currentPage" value="email"/>	
		<sakai:messages/>
		<sakai:messages message="true"/>
		<sakai:group_heading>
			<fmt:message key="instruction.input"/> 
		</sakai:group_heading>
		<sakai:instruction>
			<fmt:message key="note.required"/>&nbsp;<fmt:message key="prompt.mandatory"/>
		</sakai:instruction>				
		<sakai:group_table>	
			<tr>
				<td><fmt:message key="prompt.recipient"/>&nbsp;<fmt:message key="prompt.mandatory"/></td>
				<td>
					<html:select name="unmarkAssFollowUpForm" property="selectedRecipientIndex">
						<logic:iterate name="unmarkAssFollowUpForm" property="listRecipient" id="record" indexId="index">
							<html:option value='<%= "" + index.toString() + "" %>'><logic:notEmpty name="record" property="person"><bean:write name="record" property="person.name"/>&nbsp;&nbsp;&nbsp;-&nbsp;&nbsp;&nbsp;</logic:notEmpty><bean:write name="record" property="role"/></html:option>	
						</logic:iterate>
					</html:select>					
				</td>
			</tr>
			<tr>
				<td><fmt:message key="prompt.message"/>&nbsp;<fmt:message key="prompt.mandatory"/></td>
				<td><html:textarea name="unmarkAssFollowUpForm" property="emailMessage" cols="100" rows="8"/></td>				
			</tr>
		</sakai:group_table>
		<sakai:actions>
			<html:submit property="action">
					<fmt:message key="button.sendEmail"/>
			</html:submit>	
			<html:submit property="action">
					<fmt:message key="button.cancel"/>
			</html:submit>		
			<html:submit property="action">
					<fmt:message key="button.back"/>
			</html:submit>		
		</sakai:actions>			
	</html:form>
</sakai:html>