
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setBundle basename="za.ac.unisa.lms.tools.registrationstatus.ApplicationResources"/>

<sakai:html>
	<br/>

	<html:form action="/registrationStatus">
		<sakai:messages/>
		<sakai:messages message="true"/>
		<sakai:heading><fmt:message key="page.heading2"/>&nbsp;<bean:write name="creationDate"/></sakai:heading>
	<br/>
	<sakai:instruction><fmt:message key="page.notofficial"/></sakai:instruction>
	
<%-- List of applied for study units --%>
		<logic:notEmpty name="registrationStatusForm" property="applyForStudyUnits">
		<sakai:heading><fmt:message key="page.details1"/></sakai:heading><br/>
		<%-- Workflow - Edmund 2017 Removed as per SRSR137408
			<logic:notEmpty name="registrationStatusForm" property="applyForStudyUnits">
				<logic:notEmpty name="registrationStatusForm" property="reasonDate">
					<sakai:heading><fmt:message key="page.heading.reason"/></sakai:heading>
					<sakai:flat_list>
					<tr>
						<td><strong><bean:write name="registrationStatusForm" property="reasonDate"/>&nbsp;-&nbsp;
						<bean:write name="registrationStatusForm" property="reasonDesc"/></strong></td>
					</tr>
					</sakai:flat_list>
					<br/>
				</logic:notEmpty>
			</logic:notEmpty>
		--%>
			<table>
			<tr>
				<td><fmt:message key="page.studentnumber"/>&nbsp;</td>
				<td><bean:write name="registrationStatusForm" property="student.number"/>&nbsp;</td>
			</tr>
			<tr>
				<td><fmt:message key="page.qual"/>&nbsp;</td>
				<td><bean:write name="registrationStatusForm" property="qual.qualCode"/>&nbsp;-&nbsp;<bean:write name="registrationStatusForm" property="qual.qualDesc"/></td>
			</tr>
			<logic:notEqual name="registrationStatusForm" property="qual.specCode" value="">
				<tr>
					<td><fmt:message key="page.spec"/></td>
					<td colspan="2"><bean:write name="registrationStatusForm" property="qual.specCode"/>&nbsp;-&nbsp;<bean:write name="registrationStatusForm" property="qual.specDesc"/></td>
				</tr>
			</logic:notEqual>
			</tr>
			</table>
			<sakai:flat_list>
			<tr>
				<th align="left"><fmt:message key="table.heading.code"/>&nbsp;</th>
				<th align="left"><fmt:message key="table.heading.codeDesc"/>&nbsp;</th>
				<th align="left"><fmt:message key="table.heading.regPeriod"/>&nbsp;</th>
				<th align="left"><fmt:message key="table.heading.language"/>&nbsp;</th>
				<th align="left"><fmt:message key="table.heading.status"/>&nbsp;</th>
			</tr>
			<logic:iterate name="registrationStatusForm" property="applyForStudyUnits" id="record" indexId="i">
			<tr>
				<td><bean:write name="record" property="code"/>&nbsp;</td>
				<td><bean:write name="record" property="desc"/>&nbsp;</td>
				<logic:equal name="record" property="regPeriod" value="0">
				<td>Year module</td>
				</logic:equal>
				<logic:equal name="record" property="regPeriod" value="6">
				<td>Mid-year module</td>
				</logic:equal>
				<logic:equal name="record" property="regPeriod" value="1">
				<td>1st semester</td>
				</logic:equal>
				<logic:equal name="record" property="regPeriod" value="2">
				<td>2nd semester</td>
				</logic:equal>
				<logic:equal name="record" property="language" value="E">				
				<td>English</td>
				</logic:equal>
				<logic:equal name="record" property="language" value="A">				
				<td>Afrikaans</td>
				</logic:equal>
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
			<br/>
		</logic:notEmpty>


<%-- Finances --%>
		<logic:notEmpty name="registrationStatusForm" property="applyForStudyUnits">
		<logic:notEmpty name="registrationStatusForm" property="totalFee">
			<sakai:heading><fmt:message key="page.heading.finance"/></sakai:heading>
			<sakai:flat_list>
			<tr><td>
				<sakai:flat_list>
				<tr>
					<td><fmt:message key="page.fees.totalreg"/>&nbsp;</td>
					<td align="right"><strong>
						R&nbsp;<bean:write name="registrationStatusForm" property="totalFee" format="0.00"/></strong>
				</tr><tr>
					<td><fmt:message key="page.fees.dueimm"/>&nbsp;</td>
					<td align="right"><strong>
					R&nbsp;<bean:write name="registrationStatusForm" property="paymentDue" format="0.00"/></strong>
				</tr>
			</sakai:flat_list>
			</td>
			<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
			<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
			<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
			<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
			<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
			<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
			<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
			</tr>
			</sakai:flat_list>
			<br/>
		</logic:notEmpty>
		</logic:notEmpty>


<%-- List of outstanding docs --%>
		<logic:notEmpty name="registrationStatusForm" property="flagDocuments">
		<sakai:heading><fmt:message key="page.docs"/></sakai:heading>
			<sakai:flat_list>
			<logic:iterate name="registrationStatusForm" property="flagDocuments" id="record" indexId="i">
				<tr><td><bean:write name="record" property="docCode"/>&nbsp;</td>
				<logic:iterate name="record" property="docLines" id="docLine" indexId="s">
					<logic:equal name="s" value="0">
						<td><bean:write name="docLine"/>&nbsp;</td></tr>
					</logic:equal>
					<logic:notEqual name="s" value="0">
						<tr><td>&nbsp;</td><td><bean:write name="docLine"/>&nbsp;</td></tr>
					</logic:notEqual>
				</logic:iterate>
				</tr>
			</logic:iterate>
			</sakai:flat_list>
			<br/>
		</logic:notEmpty>

<%-- Already registered --%>
		<logic:equal name="registrationStatusForm" property="applyType" value="RG">
			<table>
			<tr>
				<td><fmt:message key="page.studentnumber"/>&nbsp;
				<bean:write name="registrationStatusForm" property="student.number"/>&nbsp;</td>
			</tr><tr>
			<td><fmt:message key="page.registered1"/>&nbsp;<bean:write name="registrationStatusForm" property="registrationDate"/>
				<fmt:message key="page.registered2"/>
				<a HREF='http://my.unisa.ac.za' onClick="window.open('http://my.unisa.ac.za', 'nextPage', 'height=640,width=630,menubar=yes'); return false;" target="_blank">
				here</a>
				<fmt:message key="page.registered3"/></td>
			</tr>
			</table>
			<br/>
		</logic:equal>


<%-- Not applied --%>
		<logic:equal name="registrationStatusForm" property="applyType" value="NF">
			<table>
				<tr>
					<td><fmt:message key="page.studentnumber"/>&nbsp;
					<bean:write name="registrationStatusForm" property="student.number"/>&nbsp;</td>
				</tr><tr>
					<td><fmt:message key="page.notApplied1"/>&nbsp;
					<a HREF='http://registration.unisa.ac.za' onClick="window.open('http://registration.unisa.ac.za', 'nextPage', 'height=640,width=630,menubar=yes'); return false;" target="_blank">
					here</a>
					<fmt:message key="page.notApplied2"/></td>
				</tr>
			</table>
			<br/>
		</logic:equal>

		<html:submit property="action"><fmt:message key="button.back"/></html:submit>

	</html:form>
</sakai:html>