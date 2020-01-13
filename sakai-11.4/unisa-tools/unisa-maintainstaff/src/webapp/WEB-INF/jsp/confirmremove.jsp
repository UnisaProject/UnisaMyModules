<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<fmt:setBundle basename="za.ac.unisa.lms.tools.maintainstaff.ApplicationResources"/>

<sakai:html>
<html:form action="/course"> 
	<html:hidden property="atstep" value="remove"/>
	<fmt:message key="course.remove.confirm"/> <bean:write name="courseForm" property="course"/>

	<sakai:messages/>
	<sakai:messages message="true"/>
	
	<logic:notEmpty name="courseForm" property="courseList">
		<sakai:flat_list>
			<tr>
				<th><fmt:message key="course.tab.name"/></th>
				<th><fmt:message key="course.tab.network"/></th>
				<th><fmt:message key="course.tab.staffno"/></th>
				<logic:notEqual name="courseForm" property="selectedView" value="E">
					<th><fmt:message key="course.tab.regperiod"/></th>
				</logic:notEqual>
				<logic:equal name="courseForm" property="selectedView" value="E">
					<th><fmt:message key="person.add.experiod"/></th>
				</logic:equal>
			</tr>
			<logic:iterate name="courseForm" property="courseList" id="record" indexId="i">
				<logic:equal name="record" property="remove" value="true">
					<tr>
						<td><bean:write name="record" property="name"/></td>
						<td><bean:write name="record" property="networkCode"/></td>
						<td><bean:write name="record" property="staffNo"/></td>
						<td><bean:write name="record" property="acadPeriodDesc"/></td>
					</tr>
				</logic:equal>
			</logic:iterate>
		</sakai:flat_list>
	</logic:notEmpty>
	
	<p>
	<sakai:actions>
		<html:submit property="act">
			<fmt:message key="button.remove"/>
		</html:submit>		
		<html:submit property="act">
			<fmt:message key="button.back"/>
		</html:submit>
		<html:submit property="act">
			<fmt:message key="button.cancel"/>
		</html:submit>
	</sakai:actions>
	</p>
	
</html:form>
</sakai:html>