<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<fmt:setBundle basename="za.ac.unisa.lms.tools.libmaint.ApplicationResources"/>

<sakai:html>
<html:form action="/maintenance"> 
	<html:hidden property="atstep" value="placement"/>
	
	<sakai:group_heading>
		<fmt:message key="heading"/><br>
		<fmt:message key="main.heading1"/><fmt:message key="plc.heading"/>
	</sakai:group_heading>
	<p>
	<sakai:messages/>
	<sakai:messages message="true"/>
	<p>
	<sakai:instruction><fmt:message key="info1"/></sakai:instruction>
	
	</p>
	<sakai:flat_list>
		<tr>
			<th></th>
			<th><fmt:message key="id"/></th>
			<th><fmt:message key="plc.placement"/></th>
			<th>Ranking</th>
			<th><fmt:message key="enabled"/></th>
		</tr>
		<logic:notEmpty name="maintenanceForm" property="dataList">
			<logic:iterate name="maintenanceForm" property="dataList" id="record" indexId="i">
				<tr>
					<td><html:checkbox name="maintenanceForm" property='<%= "recordIndexed["+i+"].checked" %>'/></td>
					<td><bean:write name="record" property="placementId"/></td>
					<td><bean:write name="record" property="placement"/></td>
					<td><bean:write name="record" property="placementRank"/></td>
					<td><bean:write name="record" property="enabled"/></td>
				</tr>
			</logic:iterate>
		</logic:notEmpty>
	</sakai:flat_list>
	
	</p>
	<sakai:actions>
		<html:submit property="act">
			<fmt:message key="button.ranking"/>
		</html:submit>
		<html:submit property="act">
			<fmt:message key="button.add"/>
		</html:submit>
		<html:submit property="act">
			<fmt:message key="button.remove"/>
		</html:submit>
		<html:submit property="act">
			<fmt:message key="button.edit"/>
		</html:submit>
		<html:submit property="act">
			<fmt:message key="button.back"/>
		</html:submit>
	</sakai:actions>
	
</html:form>
</sakai:html>