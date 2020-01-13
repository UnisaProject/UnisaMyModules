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
			<fmt:message key ="mis.ann"/>
		</sakai:group_heading>
		<sakai:group_heading>
			<fmt:message key = "mis.admin1"/> <bean:write name="misForm" property="unisaDate"/>
		</sakai:group_heading>
		<sakai:flat_list>
			<tr>
				<th><fmt:message key="mis.stu"/></th>
				<th style="text-align:right"><bean:write name="misForm" property="previousYear"/></th>
				<th style="text-align:right"><bean:write name="misForm" property="currentYear"/></th>
			</tr>
			<tr>
				<td><fmt:message key ="ann.stu"/></td>
				<logic:notEmpty name="misForm" property="studentsJoin">
					<logic:iterate name="misForm" property="studentsJoin" id="record" indexId="i">
						<td align='right'><bean:write name="record" property="value"/></td>
					</logic:iterate>
				</logic:notEmpty>
			</tr>
			<tr>
				<td><fmt:message key ="ann.stu1"/></td>
				<logic:notEmpty name="misForm" property="studentsVisits">
					<logic:iterate name="misForm" property="studentsVisits" id="record" indexId="i">
						<td align='right'><bean:write name="record" property="value"/></td>
					</logic:iterate>
				</logic:notEmpty>
			</tr>
			<tr>
				<td><fmt:message key ="ann.stu2"/></td>
				<logic:notEmpty name="misForm" property="activeStudents">
					<logic:iterate name="misForm" property="activeStudents" id="record" indexId="i">
						<td align='right'><bean:write name="record" property="value"/></td>
					</logic:iterate>
				</logic:notEmpty>
			</tr></br>
			&nbsp;&nbsp;
			<tr>
				<th><fmt:message key="mis.lect"/></th>
				<th style="text-align: right"><bean:write name="misForm" property="previousYear"/></th>
				<th style="text-align: right"><bean:write name="misForm" property="currentYear"/></th>
			</tr>
			<tr>
				<td><fmt:message key ="ann.lect1"/></td>
				<logic:notEmpty name="misForm" property="lecturerVisits">
					<logic:iterate name="misForm" property="lecturerVisits" id="record" indexId="i">
						<td align='right'><bean:write name="record" property="value"/></td>
					</logic:iterate>
				</logic:notEmpty>
			</tr>
			<tr>
				<td><fmt:message key ="ann.lect2"/></td>
				<logic:notEmpty name="misForm" property="lecturerLogins">
					<logic:iterate name="misForm" property="lecturerLogins" id="record" indexId="i">
						<td align='right'><bean:write name="record" property="value"/></td>
					</logic:iterate>
				</logic:notEmpty>
			</tr>
		</sakai:flat_list>
		<br><br>
			<b><fmt:message key ="ann.lect3"></fmt:message></b>
</sakai:html>