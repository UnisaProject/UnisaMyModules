<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>

<fmt:setBundle basename="za.ac.unisa.lms.tools.smsenquiry.ApplicationResources"/>

<sakai:html>
	<!-- Toolbar -->
	<sakai:tool_bar>
		<html:link href="smsEnquiry.do?action=inputSmsBatchSearch">
			<fmt:message key="link.smsBatchSearch"/>
		</html:link>
	</sakai:tool_bar>
	<html:form action="/smsEnquiry">
		<html:hidden property="currentPage" value="refCellList"/>
		<sakai:messages/>
		<sakai:messages message="true"/>
		<sakai:heading>
				<fmt:message key="heading.smsEnquiry"/>
		</sakai:heading>
		<sakai:instruction>
			<fmt:message key="note.searchOption"/>
		</sakai:instruction>
		<sakai:group_table>	
			<tr>
				<td><fmt:message key="page.option1"/></td>
				<td><html:text name="smsEnquiryForm" property="searchBatchNumber" size="9" maxlength="7"/></td>
			</tr>
			<tr>
				<td><fmt:message key="page.option2"/></td>
				<td><html:text name="smsEnquiryForm" property="searchStudentNumber" size="10" maxlength="8"/></td>
			</tr>
			<tr>
				<td><fmt:message key="page.option3"/></td>
				<td><html:text name="smsEnquiryForm" property="searchCellNumber" size="20" maxlength="20"/><fmt:message key="page.option3.eg"/></td>
			</tr>
		</sakai:group_table>
		<sakai:actions>
			<html:submit property="action">
				<fmt:message key="button.display"/>
			</html:submit>			
		</sakai:actions>
		<hr/>	
		<sakai:heading>
				<fmt:message key="heading.results"/>
		</sakai:heading>	
		<sakai:group_table>	
			<tr>
				<td><fmt:message key="page.studentNumber"/></td>
				<td><bean:write name="smsEnquiryForm" property="studentNumber"/></td>
				<td>&nbsp;</td>				
			</tr>
			<tr>
				<td><fmt:message key="page.cellphoneNumber"/></td>
				<td><bean:write name="smsEnquiryForm" property="cellNumber"/></td>
				<td>&nbsp;</td>
			</tr>	
			<tr>
				<td width="150"><fmt:message key="page.view"/>&nbsp;</td>
				<td>				
					<sakai:group_table>
						<logic:iterate name="smsEnquiryForm" property="listMessageStatus" id="record">
							<tr>
								<td><html:radio property="selectedMessageStatus" idName="record" value="code"></html:radio></td>								
								<td><bean:write name="record" property="engDescription"/></td>
							</tr>
						</logic:iterate>
					</sakai:group_table>				
				</td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td><fmt:message key="page.includeData"/></td>
				<td><fmt:message key="page.responsibilityCode"/></td>
				<td><html:text name="smsEnquiryForm" property="searchResponsibilityCode" size="9" maxlength="6"/>&nbsp;&nbsp;&nbsp;<fmt:message key="page.or"/></td>
			</tr>		
			<tr>
				<td>&nbsp;</td>
				<td><fmt:message key="page.personnelNr"/></td>
				<td><html:text name="smsEnquiryForm" property="searchPersonnelNumber" size="9" maxlength="8"/></td>
			</tr>				
		</sakai:group_table>	
		<sakai:group_table>
			<tr><td>
				<sakai:group_heading>
					<fmt:message key="note.search"/>
				</sakai:group_heading>
			</td></tr>
		</sakai:group_table>	
		<sakai:flat_list>	
			<tr>
				<td align="right" colspan="3">					
					<sakai:actions>
						<logic:equal name="smsEnquiryForm"  property="pageUpFlag" value="Y">
							<html:submit property="action">
								<fmt:message key="button.previous"/>
							</html:submit>								
						</logic:equal>	
						<logic:notEqual name="smsEnquiryForm"  property="pageUpFlag" value="Y">	
							<html:submit property="action" disabled="true">
								<fmt:message key="button.previous"/>
							</html:submit>
						</logic:notEqual>
						<html:submit property="action">
								<fmt:message key="button.first"/>
						</html:submit>
						<logic:equal name="smsEnquiryForm"  property="pageDownFlag" value="Y">
							<html:submit property="action">
								<fmt:message key="button.next"/>
							</html:submit>								
						</logic:equal>	
						<logic:notEqual name="smsEnquiryForm"  property="pageDownFlag" value="Y">	
							<html:submit property="action" disabled="true">
								<fmt:message key="button.next"/>
							</html:submit>
						</logic:notEqual>	
					</sakai:actions>
				</td>
			</tr>		
			<tr>
				<th><fmt:message key="page.log.colheading.sentOn"/></th>
				<th><fmt:message key="page.log.colheading.messageStatus"/></th>
				<th><fmt:message key="page.log.colheading.message"/></th>				
			</tr>
			<logic:iterate name="smsEnquiryForm" property="listSmsLog" id="record" indexId="index">
				<bean:define id="batchNumber" name="record" property="batchNr"></bean:define>
				<bean:define id="sequenceNumber" name="record" property="sequenceNr"></bean:define>
				<%
					java.util.HashMap params = new java.util.HashMap();
					params.put ("batchNr",batchNumber);
					params.put ("sequenceNr",sequenceNumber);
					request.setAttribute("paramsName",params);
				%>
				<tr>						
					<td>
						<bean:write name="record" property="sendOn"/>
						<html:link href="smsEnquiry.do?action=getLogEntryDetail" name="paramsName">
						<fmt:message key="link.view"/>	
						</html:link>						
					</td>
					<td><bean:write name="record" property="messageStatus"/></td>
					<td><bean:write name="record" property="message"/></td>					
			</tr>
			</logic:iterate>
		</sakai:flat_list>
	</html:form>
</sakai:html>