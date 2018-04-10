<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ page import="java.util.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setBundle basename="za.ac.unisa.lms.tools.auditview.ApplicationResources"/>

<sakai:html>

	<sakai:tool_bar>	 
       	<html:link href="auditView.do?action=input"><input type="image" align="right" src="/library/image/printer.png" onClick="window.print()"/></html:link> 
	</sakai:tool_bar>
	<html:form action="/auditView" >
	
	<sakai:group_heading> <fmt:message key="auditview.heading"/> </sakai:group_heading>
	<p><fmt:message key="event.archivenote"/></p>
	<fmt:message key="auditview.info"/>
	
	<p>
	
	<sakai:messages/>
	<sakai:messages message="true"/>
	
	<sakai:group_table>
		<tr>
			<td><fmt:message key="auditview.userid"/>&nbsp;<font color="red"><sakai:required/></font></td>
			<td><html:text property="userId" size="15"></html:text> </td>
		</tr>
	  <tr>
			
		<td><fmt:message key="auditview.eventset"/> </td>
		<td><html:select name="auditViewForm"  property="eventset" >
			<html:option value="${auditViewForm.current}">
				<fmt:message key="current.desc"/>
		</html:option>
		<html:option value="${auditViewForm.archived}">
				<fmt:message key="archive.desc"/>
		</html:option>
		<html:option value="${auditViewForm.prevYear}">
				<fmt:message key="prevYear.desc"/>
		</html:option>
			<html:option value="${auditViewForm.prevYearLessOne}">
				<fmt:message key="prevYearlessone.desc"/>
		</html:option>		
		</html:select></td>
			
		</tr>
	</sakai:group_table>
	<sakai:actions>
		<html:hidden property="action" value="input"/>
		<html:submit>
			<fmt:message key="button.continue"/>
		</html:submit>
	</sakai:actions>
	<hr/>
	<sakai:flat_list>
		<tr>
			<th><fmt:message key="event.eventdate"/></th>
			<th><fmt:message key="event.eventid"/></th>
			<th><fmt:message key="event.event"/></th>
			<th><fmt:message key="event.eventcode"/></th>
			<th><fmt:message key="event.sessionip"/></th>
			<th><fmt:message key="event.sessionserver"/></th>
			<th><fmt:message key="event.sessionuseragent"/></th>
			<th><fmt:message key="event.ref"/></th>
		</tr>
		<logic:iterate name="auditViewForm" property="subeventList" id="record" indexId="i">
		<logic:notEmpty name="record" property="sessionId">
		<tr>
			<td colspan="8"><sakai:heading>
				<hr/>
					Session START: <bean:write name="record" property="sessionStart"/><br>
					Session END: <bean:write name="record" property="sessionEnd"/><br>
					(<bean:write name="record" property="sessionId"/>)
					</sakai:heading>
			</td>
		</tr>
		</logic:notEmpty>
		<tr>
			<td><bean:write name="record" property="eventDate"/>&nbsp;</td>
			<td><bean:write name="record" property="eventId"/>&nbsp;</td>
			<td><bean:write name="record" property="event"/>&nbsp;</td>
			<td><bean:write name="record" property="eventCode"/>&nbsp;</td>
			<td><bean:write name="record" property="sessionIp"/>&nbsp;</td>
			<td><bean:write name="record" property="sessionServer"/>&nbsp;</td>
			<td><bean:write name="record" property="sessionUserAgent"/>&nbsp;</td>
			<td><bean:write name="record" property="ref"/>&nbsp;</td>
		</tr>
		</logic:iterate>
	</sakai:flat_list>
	
	
	<html:hidden property="page"/>
	<html:hidden property="oldUserId"/>
	
	<sakai:actions>
	<logic:notEqual name="auditViewForm" property="page" value="0">
		<html:submit property="pageButton" value="Previous"/>
	</logic:notEqual>
	<logic:equal name="auditViewForm" property="hasNext" value="true">
		<html:submit property="pageButton" value="Next"/>
	</logic:equal>
	</sakai:actions>
	
	</html:form>
	
	
</sakai:html>