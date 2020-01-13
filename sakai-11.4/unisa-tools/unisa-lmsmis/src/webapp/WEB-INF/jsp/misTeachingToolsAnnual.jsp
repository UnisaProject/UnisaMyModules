<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ page import="java.util.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setBundle basename="za.ac.unisa.lms.tools.lmsmis.ApplicationResources"/>
<sakai:html>
		<html:form action="/misAction.do" >
			<sakai:messages/>
			<sakai:messages message="true"/>
			<sakai:heading><fmt:message key="mis.headingscr1"/></sakai:heading><br>
			<fmt:message key="mis.admini3"/>
			<sakai:group_table>
				<tr>
					<td>
						<label>Show:<font color ="red"><sakai:required/></font></label>

					</td>
					<td>
						<html:select property ="action">
							<html:option value="misoverview"><fmt:message key="head.ann"/></html:option>
							<html:option value="misTeachMonthly"><fmt:message key="head.ann1"/> <bean:write name="misForm" property="previousYear"/></html:option>
							<html:option value="misTeachMonthlyCurrent"><fmt:message key="head.ann2"/> <bean:write name="misForm" property="currentYear"/></html:option>
							<html:option value="misTeachAnnual"><fmt:message key="head.ann3"/></html:option>
							<html:option value="misAdminAnnual"><fmt:message key="head.ann6"/></html:option>
							<html:option value="misAdminMonthly"><fmt:message key="head.ann4"/> <bean:write name="misForm" property="previousYear"/></html:option>
							<html:option value="misAdminMonthlyCurrent"><fmt:message key="head.ann4"/> <bean:write name="misForm" property="currentYear"/></html:option>
					</html:select>
					</td>
				</tr>
			</sakai:group_table>
			<sakai:actions>
				<html:submit styleClass="active" property="action"><fmt:message key="button.display"/></html:submit>
			</sakai:actions>
		</html:form>
		<hr></hr>
		<sakai:group_heading>
			<fmt:message key ="head.ann3"/>
		</sakai:group_heading>
		<sakai:group_heading>
			<fmt:message key = "mis.admin1"/> <bean:write name="misForm" property="unisaDate"/>
		</sakai:group_heading>
		<sakai:flat_list>
		<table class="listHier" cellspacing="0">
				<tr>
					<th width ="50%"><fmt:message key ="mis.action"/></th>
					<th style="text-align: right"><bean:write name="misForm" property="previousYear"/></th>
					<th style="text-align: right"><bean:write name="misForm" property="currentYear"/></th>
				</tr>
				<tr>
					<td width="50%"><fmt:message key ="teach.tools1"/></td>
					<logic:notEmpty name="misForm" property="messagePosted">
					<logic:iterate name="misForm" property="messagePosted" id="record" indexId="i">
						<td align='right'><bean:write name="record" property="value"/></td>
					</logic:iterate>
				</logic:notEmpty>
				</tr>
				<tr>
					<td><fmt:message key ="teach.tools2"/></td>
					<logic:notEmpty name="misForm" property="itemsAdded">
					<logic:iterate name="misForm" property="itemsAdded" id="record" indexId="i">
						<td align='right'><bean:write name="record" property="value"/></td>
					</logic:iterate>
				</logic:notEmpty>
				</tr>
				<tr>
					<td><fmt:message key ="teach.tools3"/></td>
					<logic:notEmpty name="misForm" property="annouceAdded">
					<logic:iterate name="misForm" property="annouceAdded" id="record" indexId="i">
						<td align='right'><bean:write name="record" property="value"/></td>
					</logic:iterate>
				</logic:notEmpty>
				</tr>
				<tr>
					<td><fmt:message key ="teach.tools4"/></td>
					<logic:notEmpty name="misForm" property="emailsSent">
					<logic:iterate name="misForm" property="emailsSent" id="record" indexId="i">
						<td align='right'><bean:write name="record" property="value"/></td>
					</logic:iterate>
				</logic:notEmpty>
				</tr>
				<tr>
					<td><fmt:message key ="teach.tools5"/></td>
					<logic:notEmpty name="misForm" property="calendarItems">
					<logic:iterate name="misForm" property="calendarItems" id="record" indexId="i">
						<td align='right'><bean:write name="record" property="value"/></td>
					</logic:iterate>
				</logic:notEmpty>
				</tr>
				<tr>
					<td><fmt:message key ="teach.tools6"/></td>
					<logic:notEmpty name="misForm" property="listCompiled">
					<logic:iterate name="misForm" property="listCompiled" id="record" indexId="i">
						<td align='right'><bean:write name="record" property="value"/></td>
					</logic:iterate>
				</logic:notEmpty>
				</tr>
				<tr>
					<td><fmt:message key ="teach.tools7"/></td>
					<logic:notEmpty name="misForm" property="listsDownloaded">
					<logic:iterate name="misForm" property="listsDownloaded" id="record" indexId="i">
						<td align='right'><bean:write name="record" property="value"/></td>
					</logic:iterate>
				</logic:notEmpty>
				</tr>
				<tr>
					<td><fmt:message key ="teach.tools8"/></td>
					<logic:notEmpty name="misForm" property="messageUpdated">
					<logic:iterate name="misForm" property="messageUpdated" id="record" indexId="i">
						<td align='right'><bean:write name="record" property="value"/></td>
					</logic:iterate>
				</logic:notEmpty>
				</tr>
				<tr>
					<td><fmt:message key ="teach.tools9"/></td>
					<logic:notEmpty name="misForm" property="fileAdded">
					<logic:iterate name="misForm" property="fileAdded" id="record" indexId="i">
						<td align='right'><bean:write name="record" property="value"/></td>
					</logic:iterate>
				</logic:notEmpty>
				</tr>
				<tr>
					<td><fmt:message key ="teach.tools10"/></td>
					<logic:notEmpty name="misForm" property="websitesAdded">
					<logic:iterate name="misForm" property="websitesAdded" id="record" indexId="i">
						<td align='right'><bean:write name="record" property="value"/></td>
					</logic:iterate>
				</logic:notEmpty>
				</tr>
				<tr>
					<td><fmt:message key ="teach.tools11"/></td>
					<logic:notEmpty name="misForm" property="resourceRead">
					<logic:iterate name="misForm" property="resourceRead" id="record" indexId="i">
						<td align='right'><bean:write name="record" property="value"/></td>
					</logic:iterate>
				</logic:notEmpty>
				</tr>
			</table>
			</sakai:flat_list>
</sakai:html>