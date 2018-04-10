<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page import="java.util.*" %>
<fmt:setBundle basename="za.ac.unisa.lms.tools.mdapplications.ApplicationResources"/>

<sakai:html>

	<html:form action="/mdapplications">
	<html:hidden property="page" value="step0"/>

		<sakai:heading>
			<fmt:message key="md.heading"/><br/>
		</sakai:heading>
		
		<sakai:instruction>
			&nbsp;<fmt:message key="md.info2"/><br/>
		</sakai:instruction>

	<sakai:heading></sakai:heading>
	
	<sakai:group_heading><fmt:message key="md.instruction1"/></sakai:group_heading>
		<sakai:group_table>
		<tr>
		  <td><fmt:message key="md.info4"/></td>
		</tr><tr>
		</tr><tr>
		  <td><strong>&lt;&lt;<html:link href="mdapplications.do?action=studinput&select=app">
				GO</html:link>&gt;&gt;</strong></td>
		</tr>
		</sakai:group_table>	
		
	<sakai:group_heading><fmt:message key="md.instruction2"/></sakai:group_heading>
		<sakai:group_table>
		<tr>
			<td colspan="2"><fmt:message key="md.info5"/></td>
		</tr><tr>
			<td colspan="2"><strong><fmt:message key="md.info6"/></strong></td>
		</tr>
		<tr>
			<td width="50%">
				<table border="0" cellspacing="0" cellpadding="0" width="100%">
					<tr>
						<td> · <fmt:message key="md.doc.d1"/></td>
					</tr><tr>
						<td> · <fmt:message key="md.doc.d2"/></td>				
					</tr><tr>
						<td> · <fmt:message key="md.doc.d3"/></td>
					</tr><tr>
						<td> · <fmt:message key="md.doc.d4"/></td>
					</tr><tr>
						<td> · <fmt:message key="md.doc.d5"/></td>
					</tr><tr>
						<td> · <fmt:message key="md.doc.d6"/></td>
					</tr><tr>
						<td> · <fmt:message key="md.doc.d7"/></td>
					</tr><tr>
						<td> · <fmt:message key="md.doc.d8"/></td>
					</tr><tr>
						<td> · <fmt:message key="md.doc.d9"/></td>
					</tr><tr>
						<td> · <fmt:message key="md.doc.d10"/></td>
					</tr><tr>
						<td> · <fmt:message key="md.doc.d21"/></td>
					</tr><tr>
						<td> · <fmt:message key="md.doc.d23"/></td>
				 	</tr>
				</table>
			</td>
			<td width="50%" valign="top">
				<table border="0" cellspacing="0" cellpadding="0" width="100%">
					<tr>
						<td> · <fmt:message key="md.doc.d11"/></td>
					</tr><tr>
						<td> · <fmt:message key="md.doc.d12"/></td>
					</tr><tr>
						<td> · <fmt:message key="md.doc.d13"/></td>
					</tr><tr>
						<td> · <fmt:message key="md.doc.d14"/></td>
					</tr><tr>
						<td> · <fmt:message key="md.doc.d15"/></td>
					</tr><tr>
						<td> · <fmt:message key="md.doc.d16"/></td>
					</tr><tr>
						<td> · <fmt:message key="md.doc.d17"/></td>
					</tr><tr>
						<td> · <fmt:message key="md.doc.d18"/></td>
					</tr><tr>
						<td> · <fmt:message key="md.doc.d22"/></td>
					</tr><tr>
						<td> · <fmt:message key="md.doc.d24"/></td>
					</tr><tr>
						<td> · <fmt:message key="md.doc.d19"/></td>
					</tr>
				</table>
			</td>
<!--	</tr><tr>
			<td>&nbsp;&nbsp;&nbsp;<fmt:message key="md.doc.md1b"/><a HREF='http://www.ets.org'>
				<fmt:message key="md.doc.md1c"/>
			</a>or <a HREF='http://www.ielts.org'>
				<fmt:message key="md.doc.md1e"/></a>&nbsp;<fmt:message key="md.doc.md1f"/></td>
		</tr><tr>
			<td> · <fmt:message key="md.doc.md2"/></td>-->
		</tr><tr>
				<td colspan="2">Submit your documents online&nbsp;<strong>&lt;&lt;<html:link href="mdapplications.do?action=studinput&select=doc">
				GO</html:link>&gt;&gt;</strong></td>
		</tr>
		</sakai:group_table>
		<BR/>
 		<div style="display: none;"><br><bean:write name="mdApplicationsForm" property="version"/></div>
		<BR/>
	</html:form>
</sakai:html>