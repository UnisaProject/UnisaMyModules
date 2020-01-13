
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setBundle basename="za.ac.unisa.lms.tools.studentregistration.ApplicationResources"/>

<sakai:html>

<html:form action="/applyForStudentNumber">
		<html:hidden property="page" value="applyType"/>
		
		<sakai:messages/>
		<sakai:messages message="true"/>

		<sakai:heading>
			<fmt:message key="page.apply.heading"/><br/>
		</sakai:heading>
		
		<sakai:instruction>
			<fmt:message key="page.apply.info17"/><br/><br/>
			<fmt:message key="page.apply.info18"/><br/><br/>
			<fmt:message key="page.apply.info21"/><br/><br/>
			<fmt:message key="page.apply.info4"/><br/><br/>
		</sakai:instruction>

	<sakai:heading></sakai:heading>
	
	<sakai:group_heading><fmt:message key="page.apply.instruction2"/></sakai:group_heading>
		<sakai:group_table>
		<tr>
		  <td><fmt:message key="page.apply.info29"/>&nbsp;<strong>&lt;&lt;<html:link href="SelectPageNew.do?act=applyType">
				GO</html:link>&gt;&gt;</strong></td>
		</tr>
		<tr>
		  <td><fmt:message key="page.apply.info30"/>&nbsp;<strong>&lt;&lt;<html:link href="SelectPageRet.do?act=studinput">
				GO</html:link>&gt;&gt;</strong></td>
		</tr>
		</sakai:group_table>	
		
	<sakai:group_heading><fmt:message key="page.apply.instruction3"/></sakai:group_heading>
		<sakai:group_table>
		<tr>
			<td><fmt:message key="page.apply.docs1"/></td>
		</tr><tr>
			<td><fmt:message key="page.apply.docs2"/></td>
		</tr><tr>
			<td> · <fmt:message key="page.apply.docs3"/></td>
		</tr><tr>
			<td> · <fmt:message key="page.apply.docs4"/></td>
		</tr><tr>
			<td>Submit your documents online&nbsp;<strong>&lt;&lt;<html:link href="uploadEntry.do">
			GO</html:link>&gt;&gt;</strong></td>
		</tr>
		</sakai:group_table>
		
		<sakai:group_heading><fmt:message key="page.apply.instruction4"/></sakai:group_heading>
		<sakai:group_table>
		<tr>
			<td><fmt:message key="page.apply.info40"/></td>
		</tr><tr>
			<td><fmt:message key="page.apply.info41"/></td>
		</tr><tr>
			<td><strong><fmt:message key="page.apply.info42"/></strong></td>
		</tr><tr>
			<td><fmt:message key="page.apply.info43"/></td>
		</tr><tr>
				<td>Pay online&nbsp;<strong>&lt;&lt;<html:link href="/unisa-findtool/default.do?sharedTool=unisa.creditcardpayment">
				GO</html:link>&gt;&gt;</strong></td>
		</tr>
		</sakai:group_table>			

	</html:form>
</sakai:html>