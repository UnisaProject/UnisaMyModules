<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<fmt:setBundle basename="za.ac.unisa.lms.tools.maintainstaff.ApplicationResources"/>

<sakai:html>
<html:form action="/course"> 
	<html:hidden property="atstep" value="addpersonssave"/>
	<html:hidden property="display" value="displaycourse"/>
	    <html:hidden property="changeview" value=""/>
	
	<sakai:group_heading>
		<fmt:message key="persons.add.info1"/> <bean:write name="courseForm" property="course"/>
		<bean:write name="courseForm" property="acadPeriodDesc"/>
	</sakai:group_heading>

	<p>
	<sakai:messages/>
	<sakai:messages message="true"/>
	</p>
	
		<p>
	<sakai:flat_list>
		<tr>
			<th> # </th>
			<th align="left"> <fmt:message key="course.tab.name"/> </th>
			<logic:equal name="courseForm" property="selectedView" value="E">
				<th><fmt:message key="course.tab.paperno"/></th>
			</logic:equal>
			<th align="left"> <fmt:message key="course.tab.role"/> </th>
		</tr>
		<logic:iterate name="courseForm" property="courseList" id="record" indexId="i">
			<logic:notEqual name="record" property="networkCode" value="">
				<tr>
					<td> <%= i+1 %> </td>
					<td> <bean:write name="record" property="name"/> </td>
					<logic:equal name="courseForm" property="selectedView" value="E">
						<td><bean:write name="courseForm" property="paperNo"/></td>
					</logic:equal>
					<td> <bean:write name="record" property="roleDesc"/></td>
				</tr>
			</logic:notEqual>
		</logic:iterate>
	</sakai:flat_list>
	</p>
	
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