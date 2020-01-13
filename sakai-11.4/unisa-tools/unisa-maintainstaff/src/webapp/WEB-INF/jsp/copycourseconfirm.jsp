<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<fmt:setBundle basename="za.ac.unisa.lms.tools.maintainstaff.ApplicationResources"/>

<sakai:html>

<html:form action="/main"> 
	
	<sakai:group_heading>
		<fmt:message key="copy.confirm"/>
	</sakai:group_heading>
	
	<sakai:messages/>
	<sakai:messages message="true"/>
	
	<p>
	<sakai:flat_list>
		<tr>
			<th> # </th>
			<th align="left"> <fmt:message key="course.tab.course"/>  </th>
			<th align="left">
				<logic:notEqual name="mainForm" property="selectedView" value="E"> 
					<fmt:message key="copy.enter3reg"/>
				</logic:notEqual>
				<logic:equal name="mainForm" property="selectedView" value="E">
					<fmt:message key="copy.enter3ex"/> 
				</logic:equal> 
			</th>
			<th align="left">
				<logic:notEqual name="mainForm" property="selectedView" value="E">
					<fmt:message key="copy.enter4reg"/>
				</logic:notEqual>
				<logic:equal name="mainForm" property="selectedView" value="E">
					<fmt:message key="copy.enter4ex"/>
				</logic:equal>
			</th>
		</tr>
		<logic:iterate name="mainForm" property="courseList" id="record" indexId="i">
			<tr>
				<td> <%= i+1 %> </td>
				<td> <bean:write name="record" property="value"/></td>
				<td> <bean:write name="mainForm" property="fromPeriodDesc"/></td>
				<td> <bean:write name="mainForm" property="toPeriodDesc"/></td>
			</tr>
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