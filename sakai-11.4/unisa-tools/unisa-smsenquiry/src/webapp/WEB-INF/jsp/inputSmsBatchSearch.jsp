<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>

<fmt:setBundle basename="za.ac.unisa.lms.tools.smsenquiry.ApplicationResources"/>

<sakai:html>	
	<html:form action="/smsEnquiry">
		<html:hidden property="currentPage" value="inputBatchSearch"/>
		<sakai:messages/>
		<sakai:messages message="true"/>
		<sakai:heading>
				<fmt:message key="heading.smsRequestSearch"/>
		</sakai:heading>	
		<sakai:instruction>
			<fmt:message key="note.mandatory"/>&nbsp;<fmt:message key="page.mandatory"/>
		</sakai:instruction>	
		<sakai:group_table>	
			<tr>
				<td><fmt:message key="page.fromDate"/>&nbsp;<fmt:message key="page.mandatory"/></td>
				<td colspan="2"><html:text name="smsEnquiryForm" property="searchFromDate" size="8" maxlength="10"/><fmt:message key="page.dateFormat"/></td>
				</tr>
			<tr>
				<td><fmt:message key="page.toDate"/>&nbsp;<fmt:message key="page.mandatory"/></td>
				<td colspan="2"><html:text name="smsEnquiryForm" property="searchToDate" size="8" maxlength="10"/><fmt:message key="page.dateFormat"/></td>
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
		<sakai:actions>
			<html:submit property="action">
				<fmt:message key="button.display"/>
			</html:submit>
			<html:submit property="action">
				<fmt:message key="button.cancel"/>
			</html:submit>			
		</sakai:actions>	
	</html:form>
</sakai:html>