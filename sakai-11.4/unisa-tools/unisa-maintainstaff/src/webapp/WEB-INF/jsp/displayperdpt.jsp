<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<fmt:setBundle basename="za.ac.unisa.lms.tools.maintainstaff.ApplicationResources"/>

<script language="JavaScript">
	function setAction() {
		document.mainForm.action = 'main.do?act=displayPerDpt';
		document.mainForm.submit();
	}
</script>

<sakai:html>
<html:form action="/main"> 
	<sakai:messages/>
	<sakai:messages message="true"/>
	
	<sakai:tool_bar>
		<html:link href="main.do?act=hodDisplay">
			<fmt:message key="link.edit.hod"/>
		</html:link>
	</sakai:tool_bar>
	
	<sakai:group_heading>
		<fmt:message key="management.heading"/>
		<html:select property="selectedDepartment" onchange="setAction();">
			<html:options collection="departmentOptions" property="value" labelProperty="label"/>
		</html:select>
	</sakai:group_heading>
	<p>
	
	<fmt:message key="management.listdate"/> <bean:write name="mainForm" property="displayDate"/>
	<p> 
	<table>
		<tr>
			<td><fmt:message key="management.dean"/> </td>
			<td>
				<logic:notEmpty name="mainForm" property="dean"> 
					<bean:write name="mainForm" property="dean"/>
				</logic:notEmpty>
			</td>
		</tr>
		<tr><td>&nbsp;</td></tr>
		<tr>
			<td><fmt:message key="management.director"/> </td>
			<logic:notEmpty name="mainForm" property="director"> 
				<td><bean:write name="mainForm" property="director"/></td>
			</logic:notEmpty>
		</tr>
		<tr><td>&nbsp;</td></tr>
		<tr>
			<td><fmt:message key="management.chair"/> </td>
			<logic:notEmpty name="mainForm" property="chair"> 
				<td><bean:write name="mainForm" property="chair"/></td>
			</logic:notEmpty>
		</tr>
		<tr><td>&nbsp;</td></tr>
		<tr>
			<td valign='top'><fmt:message key="management.standin"/> </td>
			<td>
				<logic:notEmpty name="mainForm" property="standInChairs"> 
					<logic:iterate name="mainForm" property="standInChairs" id="record" indexId="i">
						<bean:write name="record" property="label"/> <br>
					</logic:iterate>
				</logic:notEmpty>
			</td>
		</tr>
	</table>
	
		<p>
	<sakai:actions>
		<html:submit styleClass="active" property="act">
			<fmt:message key="button.cancel"/>
		</html:submit>
	</sakai:actions>
</html:form>
</sakai:html>