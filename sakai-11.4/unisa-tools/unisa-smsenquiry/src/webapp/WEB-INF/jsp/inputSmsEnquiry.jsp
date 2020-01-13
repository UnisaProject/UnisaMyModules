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
		<html:hidden property="currentPage" value="input"/>
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
	</html:form>
</sakai:html>