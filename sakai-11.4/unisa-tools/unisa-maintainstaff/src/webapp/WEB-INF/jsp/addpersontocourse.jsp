<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<fmt:setBundle basename="za.ac.unisa.lms.tools.maintainstaff.ApplicationResources"/>

<script language="JavaScript">
	function setAction() {
		document.personForm.action = 'person.do?act=addPerson';
		document.personForm.submit();
	}
</script>

<sakai:html>
<html:form action="/person"> 
	<html:hidden property="atstep" value="add"/>

	<sakai:group_heading>
		<fmt:message key="person.add.info1"/>
		<bean:write name="personForm" property="personName"/> : <bean:write name="personForm" property="personNetworkCode"/>
	</sakai:group_heading>

	<p>
	<sakai:messages/>
	<sakai:messages message="true"/>
	</p>
	
	<p>
	<fmt:message key="person.add.info2"/>
	
	<p>
	<fmt:message key="person.add.nrtoadd"/> <sakai:required/> </td>
	<html:select property="noOfRecords" onchange="setAction();" >
		<html:options collection="noOfRecordsOptions" property="value" labelProperty="label"/>
	</html:select>
	<hr>
	
	<p>
	<table>
		<logic:notEmpty name="personForm" property="courseList">
			<logic:iterate name="personForm" property="courseList" id="record" indexId="i">
			<tr>
				<td colspan="3"><bean:write name="record" property="heading"/> </td>
			</tr>
			<tr>
				<td> &nbsp; </td>
				<td> <fmt:message key="course.tab.course"/> </td>
				<td> <html:text name="personForm" property='<%= "recordIndexed["+i+"].course" %>' size="10" maxlength="10"/></td>
			</tr>
			<tr>
				<td> &nbsp; </td>
				<td>
					<logic:notEqual name="personForm" property="selectedView" value="E"> 
						<fmt:message key="person.add.regperiod"/> </td>
					</logic:notEqual>
					<logic:equal name="personForm" property="selectedView" value="E"> 
						<fmt:message key="person.add.experiod"/> 
					</logic:equal>
				</td>
				<td>
					<html:select name="personForm" property='<%= "recordIndexed["+i+"].acadYear" %>'>
						<html:options collection="yearList" property="value" labelProperty="label"/>
					</html:select>
					<html:select name="personForm" property='<%= "recordIndexed["+i+"].semesterPeriod" %>'>
						<html:options collection="periodList" property="value" labelProperty="label"/>
					</html:select>
				</td>
			</tr>
			<tr>
				<td> &nbsp; </td>
				<td><fmt:message key="course.tab.role"/> </td>
				<td>
					<html:select name="personForm" property='<%= "recordIndexed["+i+"].role" %>'>
						<html:options collection="roleOptions" property="value" labelProperty="label"/>
					</html:select>
				</td>
			</tr>
			<tr>
				<td> &nbsp; </td>
				<td>
					<logic:equal name="personForm" property="selectedView" value="E"> 
						<left><fmt:message key="course.tab.paperno"/></left> 
					</logic:equal>
				</td>
				<td>
					<logic:equal name="personForm" property="selectedView" value="E"> 
						<html:select name="personForm" property='<%= "recordIndexed["+i+"].paperNo" %>'>
							<html:options collection="paperNrOptions" property="value" labelProperty="label"/>
						</html:select>
					</logic:equal>
				</td>
			
			</tr>
			<tr><td> &nbsp; </td></tr>
			</logic:iterate>
		</logic:notEmpty>
	</table>

	<p>
	<sakai:actions>
		<html:submit styleClass="active" property="act">
			<fmt:message key="button.submit"/>
		</html:submit>
		<html:submit styleClass="active" property="act">
			<fmt:message key="button.cancel"/>
		</html:submit>
	</sakai:actions>

</html:form>
</sakai:html>