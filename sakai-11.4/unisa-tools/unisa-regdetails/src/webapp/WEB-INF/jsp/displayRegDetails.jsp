<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setBundle basename="za.ac.unisa.lms.tools.regdetails.ApplicationResources"/>

<sakai:html>

<%-- Toolbar --%>
<logic:equal name="regDetailsForm" property="nonFormal" value="false">
<logic:notEqual name="showLinks" value="false">
	<sakai:tool_bar>
		<html:link href="additions.do?action=displayContactDetails">
			<fmt:message key="link.add"/>
		</html:link>
		<html:link href="exchange.do?action=step1">
			<fmt:message key="link.exchange"/>
		</html:link>
		<html:link href="cancel.do?action=step1">
			<fmt:message key="link.cancel"/>
		</html:link>
		<html:link href="croc.do?action=step1">
			<fmt:message key="link.croc"/>
		</html:link>
		
	</sakai:tool_bar>
</logic:notEqual>
</logic:equal>

<br/>

	<html:form action="/displayRegDetails">
		<sakai:messages/>
		<sakai:messages message="true"/>
		<html:hidden property="action" value="displayRegDetails"/>
		<sakai:heading><fmt:message key="page.heading"/></sakai:heading>
<br/>
		<%-- List of applied for study units --%>
		<logic:notEmpty name="regDetailsForm" property="applyForStudyUnits">
		<sakai:heading><fmt:message key="page.details1"/></sakai:heading>
			<sakai:flat_list>
			<tr>
				<th align="left"><fmt:message key="table.heading.code"/>&nbsp;</th>
				<th align="left"><fmt:message key="table.heading.codeDesc"/>&nbsp;</th>
				<th align="left"><fmt:message key="table.heading.status"/>&nbsp;</th>
			</tr>
			<logic:iterate name="regDetailsForm" property="applyForStudyUnits" id="record" indexId="i">
			<tr>
				<td><bean:write name="record" property="code"/>&nbsp;</td>
				<td><bean:write name="record" property="desc"/>&nbsp;</td>
				<td>
					<logic:equal name="record" property="statusIndicator" value="TN">
						<fmt:message key="page.status.temp1"/>
					</logic:equal>
					<logic:equal name="record" property="statusIndicator" value="TP">
						<fmt:message key="page.status.temp1"/>
					</logic:equal>
					<logic:equal name="record" property="statusIndicator" value="A">
						<fmt:message key="page.status.app.received1"/>
					</logic:equal>
						<bean:write name="record" property="status"/>
					<logic:equal name="record" property="statusIndicator" value="TN">
						<fmt:message key="page.status.temp2"/>
					</logic:equal>
					<logic:equal name="record" property="statusIndicator" value="TP">
						<fmt:message key="page.status.temp3"/>
					</logic:equal>
					<logic:equal name="record" property="statusIndicator" value="A">
						<fmt:message key="page.status.app.received2"/>
					</logic:equal>		
				</td>
			</tr>
			<logic:equal name="record" property="wflIndicator" value="true">
			<tr>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>
					<bean:write name="record" property="wflTimestamp"/>,&nbsp;Registration incomplete:&nbsp;
				    <bean:write name="record" property="wflDescription"/>&nbsp;
				</td>
			</tr>
			</logic:equal>
			</logic:iterate>
			</sakai:flat_list>
			<br/>
		</logic:notEmpty>

		<%-- List of registered study units --%>
		<logic:notEmpty name="regDetailsForm" property="studyUnits">
			<sakai:heading><fmt:message key="page.details2"/></sakai:heading>
			<table>
			<tr>
				<td><fmt:message key="page.qual"/>&nbsp;</td>
				<td><bean:write name="regDetailsForm" property="qual.qualCode"/>&nbsp;-&nbsp;<bean:write name="regDetailsForm" property="qual.qualDesc"/></td>
			</tr>
			<logic:equal name="regDetailsForm" property="qual.specCode" value="">
				<tr>
					<td><fmt:message key="page.spec"/></td>
					<td><fmt:message key="page.additions.nospec"/></td>
				</tr>
			</logic:equal>
			<logic:notEqual name="regDetailsForm" property="qual.specCode" value="">
				<tr>
					<td><fmt:message key="page.spec"/></td>
					<td colspan="2"><bean:write name="regDetailsForm" property="qual.specCode"/>&nbsp;-&nbsp;<bean:write name="regDetailsForm" property="qual.specDesc"/></td>
				</tr>
			</logic:notEqual>
			</tr>
			</table>
			<sakai:flat_list>
			<tr>
				<th align="left"><fmt:message key="table.heading.code"/>&nbsp;</th>
				<th align="left"><fmt:message key="table.heading.codeDesc"/>&nbsp;</th>
				<th align="left"><fmt:message key="table.heading.status"/>&nbsp;</th>
				<th align="left"><fmt:message key="table.heading.language"/>&nbsp;</th>
				<th align="left"><fmt:message key="table.heading.semester"/>&nbsp;</th>
				<th align="left"><fmt:message key="table.heading.examPeriod"/>&nbsp;</th>
				<!-- <th align="left"><fmt:message key="table.heading.ndp"/>&nbsp;</th> -->
			</tr>
			<logic:iterate name="regDetailsForm" property="studyUnits" id="record" indexId="i">
			<tr>
				<td><bean:write name="record" property="code"/>&nbsp;</td>
				<td><bean:write name="record" property="desc"/>&nbsp;</td>
				<td><bean:write name="record" property="status"/>&nbsp;</td>
				<td><bean:write name="record" property="language"/>&nbsp;</td>
				<td><bean:write name="record" property="regPeriod"/>&nbsp;</td>
				<td><bean:write name="record" property="examPeriod"/>&nbsp;</td>
				<!-- <td><bean:write name="record" property="ndp"/>&nbsp;</td> -->
			</tr>
			</logic:iterate>
			</sakai:flat_list>
			<br/>
		</logic:notEmpty>

		<%-- List of supplementary study units --%>
		<logic:notEmpty name="regDetailsForm" property="supplementStudyUnits">
			<logic:equal name="showNotRegLink" value="true">
				<sakai:heading><fmt:message key="page.details2"/></sakai:heading>
				<table>
				<tr>
					<td><fmt:message key="page.notRegistered"/>&nbsp;</td>
				</tr>
				</table>
			</logic:equal>
			<sakai:heading><fmt:message key="page.details3"/></sakai:heading>
			<sakai:flat_list>
			<tr>
				<th align="left"><fmt:message key="table.heading.code"/>&nbsp;</th>
				<th align="left"><fmt:message key="table.heading.codeDesc"/>&nbsp;</th>
				<th align="left"><fmt:message key="table.heading.examPeriod"/>&nbsp;</th>
			</tr>
			<logic:iterate name="regDetailsForm" property="supplementStudyUnits" id="record" indexId="i">
			<tr>
				<td><bean:write name="record" property="code"/>&nbsp;</td>
				<td><bean:write name="record" property="desc"/>&nbsp;</td>
				<td><bean:write name="record" property="examPeriod"/>&nbsp;</td>
			</tr>
			</logic:iterate>
			</sakai:flat_list>
			<br/>
		</logic:notEmpty>

		<%-- Student annual only!! --%>
		<logic:empty name="regDetailsForm" property="studyUnits">
		<logic:empty name="regDetailsForm" property="supplementStudyUnits">
		<logic:empty name="regDetailsForm" property="applyForStudyUnits">
		<logic:equal name="showNotRegLink" value="true">
			<table>
			<sakai:heading><fmt:message key="page.details2"/></sakai:heading>
				<tr>
					<td><fmt:message key="page.notRegistered"/>&nbsp;</td>
				</tr>
			</table>
		</logic:equal>
		</logic:empty>
		</logic:empty>
		</logic:empty>
		<div style="display: none;"><br><center><bean:write name="regDetailsForm" property="version"/></center></div>
		<br/>
	</html:form>
	
</sakai:html>