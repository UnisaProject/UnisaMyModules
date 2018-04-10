<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<fmt:setBundle basename="za.ac.unisa.lms.tools.maintainstaff.ApplicationResources"/>

<script language="JavaScript">
	function setAction() {
		document.courseForm.action = 'course.do?act=addCourse&addMembers=yes';
		document.courseForm.submit();
	}
</script>

<sakai:html>
<html:form action="/course"> 
	<html:hidden property="atstep" value="confirm"/>
	<html:hidden property="addMembers" value=""/>
	
	<sakai:group_heading>
		<logic:equal name="courseForm" property="selectedView" value="L">
			<fmt:message key="course.add.info1"/>
		</logic:equal>
		<logic:equal name="courseForm" property="selectedView" value="E">
			<fmt:message key="course.add.info1.ex"/>
		</logic:equal>
		<logic:notEqual name="courseForm" property="course" value="">
			<bean:write name="courseForm" property="course"/> 
		</logic:notEqual> 
		
	</sakai:group_heading>

	<p>
	<sakai:messages/>
	<sakai:messages message="true"/>
	</p>
	
	<p>
	<logic:equal name="courseForm" property="selectedView" value="L">
		<fmt:message key="course.add.info2"/>
	</logic:equal>
	<fmt:message key="course.add.info3"/> <br> 
	<fmt:message key="required"/> <sakai:required/>
	</p>
	
	<p>
	<table>
		<tr>
			<td colspan='2'> <b><fmt:message key="course.add.info4"/> <sakai:required/></b> </td>
		</tr>
		<logic:equal name="courseForm" property="displayCourse" value="true">
			<tr>
				<td><fmt:message key="course.tab.course"/> <sakai:required/> </td>
				<td><html:text property="course" size="10" maxlength="10"/></td>
			</tr>
		</logic:equal>
		<tr>
			<td><fmt:message key="course.year"/> <sakai:required/> </td>
			<td>
				<html:select name="courseForm" property='year'>
					<html:options collection="yearOptions" property="value" labelProperty="label"/>
				</html:select>
			</td>
		</tr>
		<tr>
			<td>
				<logic:equal name="courseForm" property="selectedView" value="L">
					<fmt:message key="course.acadperiod"/> <v/>
				</logic:equal> 
				<logic:equal name="courseForm" property="selectedView" value="E">
					<fmt:message key="person.add.experiod"/> <v/>
				</logic:equal>
			</td>
			<td>
				<html:select name="courseForm" property="acadPeriod">
					<html:options collection="acadPeriodOptions" property="value" labelProperty="label"/>
				</html:select>
			</td>
		</tr>
		<logic:equal name="courseForm" property="selectedView" value="L">
			<tr><td> &nbsp; </td></tr>
			<tr>
				<td colspan='2'><b><fmt:message key="course.add.info5"/> <sakai:required/></b></td>
			</tr>
			<tr>
				<td><fmt:message key="course.tab.network"/> <sakai:required/> </td>
				<td><html:text property="primlNetworkCode" size="10" maxlength="10"/></td>
			</tr>
		</logic:equal>
		<tr><td> &nbsp; </td></tr>
		<tr>
			<td colspan=2><b><fmt:message key="course.add.info6"/></b></td>
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
	</p>
	
	<hr>	
	<p>
	<table>
		<logic:iterate name="courseForm" property="courseList" id="record" indexId="i">
			<tr><td colspan='3'> <bean:write name="record" property="heading"/></td></tr>
			<tr>
				<td> &nbsp; </td>
				<td> <fmt:message key="course.tab.role"/> </td>
				<td> 
					<html:select name="courseForm" property='<%= "recordIndexed["+i+"].role" %>'>
						<html:options collection="roleOptions" property="value" labelProperty="label"/>
					</html:select> 
				</td>
			</tr>
			<tr>
				<td> &nbsp; </td>
				<td> <fmt:message key="course.tab.network"/> </td>
				<td> <html:text name="courseForm" property='<%= "recordIndexed["+i+"].networkCode" %>' size="10" maxlength="10"/></td>
			</tr>
			<tr><td> &nbsp; </td></tr>
		</logic:iterate>
	</table>
	</p>
	
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