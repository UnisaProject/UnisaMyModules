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
			<fmt:message key ="mis.admin"/> <bean:write name="misForm" property="selectedYear"/>
		</sakai:group_heading>
		<sakai:instruction>
			<fmt:message key ="mis.admini1"/><br><fmt:message key ="mis.admini2"/>
		</sakai:instruction>
		<sakai:group_heading>
			<fmt:message key = "mis.admin1"/> <bean:write name="misForm" property="unisaDate"/>
		</sakai:group_heading>
		<sakai:flat_list>

				<tr>
					<th><fmt:message key ="mis.action"/></th>
					<th style="text-align: right"><fmt:message key ="mis.jan"/></th>
					<th style="text-align: right"><fmt:message key ="mis.feb"/></th>
					<th style="text-align: right"><fmt:message key ="mis.mar"/></th>
					<th style="text-align: right"><fmt:message key ="mis.apr"/></th>
					<th style="text-align: right"><fmt:message key ="mis.may"/></th>
					<th style="text-align: right"><fmt:message key ="mis.jun"/></th>
					<th style="text-align: right"><fmt:message key ="mis.july"/></th>
					<th style="text-align: right"><fmt:message key ="mis.aug"/></th>
					<th style="text-align: right"><fmt:message key ="mis.sep"/></th>
					<th style="text-align: right"><fmt:message key ="mis.oct"/></th>
					<th style="text-align: right"><fmt:message key ="mis.nov"/></th>
					<th style="text-align: right"><fmt:message key ="mis.dec"/></th>

				</tr>
				<tr>
					<td><fmt:message key ="act.adm1"/></td>
					<logic:notEmpty name="misForm" property="passwordChanges">
					<logic:iterate name="misForm" property="passwordChanges" id="record" indexId="i">
						<td align='right'><bean:write name="record" property="value"/></td>
					</logic:iterate>
				</logic:notEmpty>
				</tr>
				<tr>
					<td><fmt:message key ="act.adm2"/></td>
					<logic:notEmpty name="misForm" property="registrationDetails">
					<logic:iterate name="misForm" property="registrationDetails" id="record" indexId="i">
						<td align='right'><bean:write name="record" property="value"/></td>
					</logic:iterate>
				</logic:notEmpty>
				</tr>
				<tr>
					<td><fmt:message key ="act.adm3"/></td>
					<logic:notEmpty name="misForm" property="addTransactions">
					<logic:iterate name="misForm" property="addTransactions" id="record" indexId="i">
						<td align='right'><bean:write name="record" property="value"/></td>
					</logic:iterate>
				</logic:notEmpty>
				</tr>

				<tr>
					<td><fmt:message key ="act.adm4"/></td>
					<logic:notEmpty name="misForm" property="cancelTransactions">
					<logic:iterate name="misForm" property="cancelTransactions" id="record" indexId="i">
						<td align='right'><bean:write name="record" property="value"/></td>
					</logic:iterate>
				</logic:notEmpty>
				</tr>

				<tr>
					<td><fmt:message key ="act.adm5"/></td>
					<logic:notEmpty name="misForm" property="semesterChanges">
					<logic:iterate name="misForm" property="semesterChanges" id="record" indexId="i">
						<td align='right'><bean:write name="record" property="value"/></td>
					</logic:iterate>
				</logic:notEmpty>
				</tr>

				<tr>
					<td><fmt:message key ="act.adm6"/></td>
					<logic:notEmpty name="misForm" property="emailsLecturer">
					<logic:iterate name="misForm" property="emailsLecturer" id="record" indexId="i">
						<td align='right'><bean:write name="record" property="value"/></td>
					</logic:iterate>
				</logic:notEmpty>
				</tr>

				<tr>
					<td><fmt:message key ="act.adm7"/></td>
					<logic:notEmpty name="misForm" property="classUpdates">
					<logic:iterate name="misForm" property="classUpdates" id="record" indexId="i">
						<td align='right'><bean:write name="record" property="value"/></td>
					</logic:iterate>
				</logic:notEmpty>
				</tr>

				<tr>
					<td><fmt:message key ="act.adm8"/></td>
					<logic:notEmpty name="misForm" property="academicRecord">
					<logic:iterate name="misForm" property="academicRecord" id="record" indexId="i">
						<td align='right'><bean:write name="record" property="value"/></td>
					</logic:iterate>
				</logic:notEmpty>
				</tr>

				<tr>
					<td><fmt:message key ="act.adm9"/></td>
					<logic:notEmpty name="misForm" property="bioDetials">
					<logic:iterate name="misForm" property="bioDetials" id="record" indexId="i">
						<td align='right'><bean:write name="record" property="value"/></td>
					</logic:iterate>
				</logic:notEmpty>
				</tr>

				<tr>
					<td><fmt:message key ="act.adm10"/></td>
					<logic:notEmpty name="misForm" property="directAddress">
					<logic:iterate name="misForm" property="directAddress" id="record" indexId="i">
						<td align='right'><bean:write name="record" property="value"/></td>
					</logic:iterate>
				</logic:notEmpty>
				</tr>

				<tr>
					<td><fmt:message key ="act.adm11"/></td>
					<logic:notEmpty name="misForm" property="indirectAddress">
					<logic:iterate name="misForm" property="indirectAddress" id="record" indexId="i">
						<td align='right'><bean:write name="record" property="value"/></td>
					</logic:iterate>
				</logic:notEmpty>
				</tr>

				<tr>
					<td><fmt:message key ="act.adm12"/></td>
					<logic:notEmpty name="misForm" property="contactChanges">
					<logic:iterate name="misForm" property="contactChanges" id="record" indexId="i">
						<td align='right'><bean:write name="record" property="value"/></td>
					</logic:iterate>
				</logic:notEmpty>
				</tr>

				<tr>
					<td><fmt:message key ="act.adm13"/></td>
					<logic:notEmpty name="misForm" property="optionChanges">
					<logic:iterate name="misForm" property="optionChanges" id="record" indexId="i">
						<td align='right'><bean:write name="record" property="value"/></td>
					</logic:iterate>
				</logic:notEmpty>
				</tr>

				<tr>
					<td><fmt:message key ="act.adm14"/></td>
					<logic:notEmpty name="misForm" property="centreChanges">
					<logic:iterate name="misForm" property="centreChanges" id="record" indexId="i">
						<td align='right'><bean:write name="record" property="value"/></td>
					</logic:iterate>
				</logic:notEmpty>
				</tr>

				<tr>
					<td><fmt:message key ="act.adm15"/></td>
					<logic:notEmpty name="misForm" property="cardPayments">
					<logic:iterate name="misForm" property="cardPayments" id="record" indexId="i">
						<td align='right'><bean:write name="record" property="value"/></td>
					</logic:iterate>
				</logic:notEmpty>
				</tr>

				<tr>
					<td><fmt:message key ="act.adm16"/></td>
					<logic:notEmpty name="misForm" property="currentFinancial">
					<logic:iterate name="misForm" property="currentFinancial" id="record" indexId="i">
						<td align='right'><bean:write name="record" property="value"/></td>
					</logic:iterate>
				</logic:notEmpty>
				</tr>

				<tr>
					<td><fmt:message key ="act.adm17"/></td>
					<logic:notEmpty name="misForm" property="historicFinancial">
					<logic:iterate name="misForm" property="historicFinancial" id="record" indexId="i">
						<td align='right'><bean:write name="record" property="value"/></td>
					</logic:iterate>
				</logic:notEmpty>
				</tr>
				<tr>
					<td><fmt:message key ="act.adm18"/></td>
					<logic:notEmpty name="misForm" property="feeQuotation">
					<logic:iterate name="misForm" property="feeQuotation" id="record" indexId="i">
						<td align='right'><bean:write name="record" property="value"/></td>
					</logic:iterate>
				</logic:notEmpty>
				</tr>
				<tr>
					<td><fmt:message key ="act.adm19"/></td>
					<logic:notEmpty name="misForm" property="parcelInformation">
					<logic:iterate name="misForm" property="parcelInformation" id="record" indexId="i">
						<td align='right'><bean:write name="record" property="value"/></td>
					</logic:iterate>
				</logic:notEmpty>
				</tr>
				<tr>
					<td><fmt:message key ="act.adm20"/></td>
					<logic:notEmpty name="misForm" property="ebookShop">
					<logic:iterate name="misForm" property="ebookShop" id="record" indexId="i">
						<td align='right'><bean:write name="record" property="value"/></td>
					</logic:iterate>
				</logic:notEmpty>
				</tr>
				<tr>
					<td><fmt:message key ="act.adm21"/></td>
					<logic:notEmpty name="misForm" property="ebookshopAdditions">
					<logic:iterate name="misForm" property="ebookshopAdditions" id="record" indexId="i">
						<td align='right'><bean:write name="record" property="value"/></td>
					</logic:iterate>
				</logic:notEmpty>
				</tr>
				<tr>
					<td><fmt:message key ="act.adm22"/></td>
					<logic:notEmpty name="misForm" property="shopEdits">
					<logic:iterate name="misForm" property="shopEdits" id="record" indexId="i">
						<td align='right'><bean:write name="record" property="value"/></td>
					</logic:iterate>
				</logic:notEmpty>
				</tr>
				<tr>
					<td><fmt:message key ="act.adm23"/></td>
					<logic:notEmpty name="misForm" property="shopDeleted">
					<logic:iterate name="misForm" property="shopDeleted" id="record" indexId="i">
						<td align='right'><bean:write name="record" property="value"/></td>
					</logic:iterate>
				</logic:notEmpty>
				</tr>
					<tr>
					<td><fmt:message key ="act.adm24"/></td>
					<logic:notEmpty name="misForm" property="toasterWrites">
					<logic:iterate name="misForm" property="toasterWrites" id="record" indexId="i">
						<td align='right'><bean:write name="record" property="value"/></td>
					</logic:iterate>
				</logic:notEmpty>
				</tr>
					<tr>
					<td><fmt:message key ="act.adm25"/></td>
					<logic:notEmpty name="misForm" property="toasterNoWrites">
					<logic:iterate name="misForm" property="toasterNoWrites" id="record" indexId="i">
						<td align='right'><bean:write name="record" property="value"/></td>
					</logic:iterate>
				</logic:notEmpty>
				</tr>
					<tr>
					<td><fmt:message key ="act.adm26"/></td>
					<logic:notEmpty name="misForm" property="toasterCancels">
					<logic:iterate name="misForm" property="toasterCancels" id="record" indexId="i">
						<td align='right'><bean:write name="record" property="value"/></td>
					</logic:iterate>
				</logic:notEmpty>
				</tr>

		</sakai:flat_list>

</sakai:html>