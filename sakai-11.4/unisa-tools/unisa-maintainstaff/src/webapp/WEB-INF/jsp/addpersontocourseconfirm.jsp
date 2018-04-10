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
	<html:hidden property="atstep" value="save"/>

	<sakai:group_heading>
		<fmt:message key="person.add.info1"/>
		<bean:write name="personForm" property="personName"/> : <bean:write name="personForm" property="personNetworkCode"/>
	</sakai:group_heading>

	<p>
	<sakai:messages/>
	<sakai:messages message="true"/>
	</p>

	<p>
	<sakai:flat_list>
		<tr>
			<th> # </th>
			<th align="left"> <fmt:message key="course.tab.course"/> </th>
			<logic:equal name="personForm" property="selectedView" value="E">
				<th><fmt:message key="course.tab.paperno"/></th>
			</logic:equal>
			<th align="left"> <fmt:message key="course.tab.role"/> </th>
			<logic:notEqual name="personForm" property="selectedView" value="E">
				<th><fmt:message key="course.tab.regperiod"/></th>
			</logic:notEqual>
			<logic:equal name="personForm" property="selectedView" value="E">
				<th><fmt:message key="person.add.experiod"/></th>
			</logic:equal>
		</tr>
		<logic:iterate name="personForm" property="courseList" id="record" indexId="i">
			<tr>
				<td> <%= i+1 %> </td>
				<td><bean:write name="record" property="course"/></td>
				<logic:equal name="personForm" property="selectedView" value="E">
					<td><bean:write name="record" property="paperNo"/></td>
				</logic:equal>
				<td> <bean:write name="record" property="roleDesc"/></td>
				<td> <bean:write name="record" property="acadPeriodDesc"/></td>
			</tr>
		</logic:iterate>
	</sakai:flat_list>

	<p>
	<sakai:actions>
		<html:submit styleClass="active" property="act">
			<fmt:message key="button.finish"/>
		</html:submit>
		<html:submit styleClass="active" property="act">
			<fmt:message key="button.back"/>
		</html:submit>
		<html:submit styleClass="active" property="act">
			<fmt:message key="button.cancel"/>
		</html:submit>
	</sakai:actions>

</html:form>
</sakai:html>