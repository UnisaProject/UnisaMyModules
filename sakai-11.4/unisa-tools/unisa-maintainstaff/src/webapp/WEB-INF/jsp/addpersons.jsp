<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<fmt:setBundle basename="za.ac.unisa.lms.tools.maintainstaff.ApplicationResources"/>

<script language="JavaScript">
	function setAction() {
		document.courseForm.action = 'course.do?act=addPersons';
		document.courseForm.submit();
	}
</script>

<sakai:html>
<html:form action="/course"> 
	<html:hidden property="atstep" value="addpersonsconfirm"/>
	
	<sakai:group_heading>
		<fmt:message key="person.add.info1"/> <bean:write name="courseForm" property="course"/>
	</sakai:group_heading>

	<p>
	<sakai:messages/>
	<sakai:messages message="true"/>
	</p>
	
	<p>
	<table>
		<tr>
			<td>
				<logic:equal name="courseForm" property="selectedView" value="L"> 
					<fmt:message key="person.add.regperiod"/> </td>
				</logic:equal>
				<logic:equal name="courseForm" property="selectedView" value="J"> 
					<fmt:message key="person.add.regperiod"/> </td>
				</logic:equal>
				<logic:equal name="courseForm" property="selectedView" value="E"> 
					<fmt:message key="person.add.experiod"/> 
				</logic:equal>
			</td>
			<td>
				<html:select name="courseForm" property='year'>
					<html:options collection="yearOptions" property="value" labelProperty="label"/>
				</html:select>
				<html:select name="courseForm" property='acadPeriod'>
					<html:options collection="periodOptions" property="value" labelProperty="label"/>
				</html:select>
			</td>	
		</tr>
		<tr>
			<td>
				<logic:equal name="courseForm" property="selectedView" value="E"> 
					<fmt:message key="course.tab.paperno"/> 
				</logic:equal>
			</td>
			<td>
				<logic:equal name="courseForm" property="selectedView" value="E"> 
					<html:select name="courseForm" property='paperNo'>
						<html:options collection="paperNrOptions" property="value" labelProperty="label"/>
					</html:select>
				</logic:equal>
			</td>
			
		</tr>
		<tr>
			<td><fmt:message key="course.nrtoadd"/> <sakai:required/> </td>
				<td>
				<html:select property="noOfRecords" onchange="setAction();" >
					<html:options collection="noOfRecordsOptions" property="value" labelProperty="label"/>
				</html:select>
				</td>
			</tr>
	</table>
	<hr>
	
	<p>
	<table>
		<logic:iterate name="courseForm" property="courseList" id="record" indexId="i">
			<tr><td colspan='3'> <bean:write name="record" property="heading"/></td></tr>
			<tr>
				<td> &nbsp; </td>
				<td> <fmt:message key="course.tab.role"/> <sakai:required/></td>
				<td> 
					<html:select name="courseForm" property='<%= "recordIndexed["+i+"].role" %>'>
						<html:options collection="roleOptions" property="value" labelProperty="label"/>
					</html:select> 
				</td>
			</tr>
			<tr>
				<td> &nbsp; </td>
				<td> <fmt:message key="course.tab.network"/> <sakai:required/></td>
				<td> <html:text name="courseForm" property='<%= "recordIndexed["+i+"].networkCode" %>' size="10" maxlength="10"/></td>
			</tr>
			<logic:notEqual name="courseForm" property="selectedView" value="L"> 
				<tr>
					<td> &nbsp; </td>
					<td> <fmt:message key="course.tab.staffno"/><sakai:required/> </td>
					<td> <html:text name="courseForm" property='<%= "recordIndexed["+i+"].staffNo" %>' size="10" maxlength="10"/></td>
				</tr>
			</logic:notEqual>
			<tr><td> &nbsp; </td></tr>
		</logic:iterate>
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