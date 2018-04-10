
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setBundle basename="za.ac.unisa.lms.tools.mdregistrations.ApplicationResources"/>

<sakai:html>

<html:form action="/mdregistrations">

		<sakai:messages/>
		<sakai:messages message="true"/>

		<html:hidden property="page" value="walkthrough"/>

		<sakai:heading>
			<fmt:message key="md.heading"/><br/>
		</sakai:heading>
				<sakai:instruction>
			&nbsp;<fmt:message key="md.info1"/><br/>
		</sakai:instruction>
		
		
	<sakai:heading></sakai:heading>
	
	<sakai:group_heading><fmt:message key="md.instruction1"/></sakai:group_heading>
		<sakai:group_table>
		<tr>
		  <td><fmt:message key="md.info4"/></td>
		</tr><tr>
		</tr><tr>
		  <td><strong>&lt;&lt;<html:link href="mdregistrations.do?action=studinput">
				GO</html:link>&gt;&gt;</strong></td>
		</tr>
		</sakai:group_table>	

	<sakai:group_heading><fmt:message key="md.instruction3"/></sakai:group_heading>
		<sakai:group_table>
		<tr>
		  <td><fmt:message key="md.info4"/></td>
		</tr><tr>
		</tr><tr>
		  <td><strong>&lt;&lt;<html:link href="mdregistrations.do?action=studinput&amp;studyUnitAddition=true">
				GO</html:link>&gt;&gt;</strong></td>
		</tr>
		</sakai:group_table>		
		
		
		<sakai:group_heading><fmt:message key="md.instruction2"/></sakai:group_heading>
		<sakai:group_table>
		<tr>
			<td><fmt:message key="page.apply.pay1"/></td>
		</tr><tr>
			<td><fmt:message key="page.apply.pay2"/></td>
		</tr><tr>
			<td><fmt:message key="page.apply.pay3"/></td>
		</tr><tr>
				<td>Pay online&nbsp;<strong>&lt;&lt;<html:link href="/unisa-findtool/default.do?sharedTool=unisa.creditcardpayment">
				GO</html:link>&gt;&gt;</strong></td>
		</tr>
		</sakai:group_table>			

<!--	<sakai:actions>
		<html:submit property="act"><fmt:message key="button.continue"/></html:submit>
	</sakai:actions>
-->

		<div style="display: none;"><br><center><bean:write name="mdRegistrationsForm" property="version"/></center></div>
	</html:form>
</sakai:html>