<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setBundle basename="za.ac.unisa.lms.tools.regdetails.ApplicationResources"/>

<sakai:html>

<!-- Form -->
	<html:form action="/additions">
	<html:hidden property="goto" value="1"/>

		<sakai:messages/>
		<sakai:messages message="true"/>
		<sakai:heading><fmt:message key="page.heading.additions"/></sakai:heading>
		<p>
			<strong><fmt:message key="page.info.additions"/></strong>
		</p>

		<%-- List of applied for study units --%>
		<sakai:heading><fmt:message key="page.heading.step1a"/></sakai:heading>
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
			</logic:iterate>
		</sakai:flat_list>

		<sakai:actions>
			<html:submit property="action"><fmt:message key="button.continue"/></html:submit>
			<html:submit property="action"><fmt:message key="button.cancel"/></html:submit>
		</sakai:actions>
	</html:form>

</sakai:html>