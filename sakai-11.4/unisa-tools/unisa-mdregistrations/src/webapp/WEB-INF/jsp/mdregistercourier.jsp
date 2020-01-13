<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setBundle basename="za.ac.unisa.lms.tools.mdregistrations.ApplicationResources"/>

<%

response.setDateHeader("Expires",0);
response.setDateHeader("Date",0);
response.setHeader("PRAGMA","NO-CACHE");
response.setHeader("Cache-Control","no-cache");

%>
<sakai:html>
<head>

	<script type="text/javascript" src="/unisa-studentregistration/js/jquery.js"></script>
	<script type="text/javascript" src="/unisa-studentregistration/js/jquery.blockUI.js" ></script> 

</head>

<!-- Form -->
	<html:form action="/mdregistrations">
	<html:hidden property="page" value="stepCourier"/>

		<sakai:messages/>
		<sakai:messages message="true"/>
		
		<sakai:group_heading><fmt:message key="md.heading.courier"/></sakai:group_heading>

		<sakai:group_table>
		<tr>
			<td colspan="3"><fmt:message key="md.pageCourier.instruction"/></td>
		</tr><tr>
			<td colspan="3"><fmt:message key="md.pageCourier.instruct2"/></td>
		</tr><tr>
			<td><fmt:message key="md.pageCourier.courier"/>&nbsp;</td>
			<td><html:text name="mdRegistrationsForm" property="student.courierAddress.line1" maxlength="28" size="30"/></td>
			<td align="left"><fmt:message key="md.pageCourier.couradrinfo"/></td>
		</tr><tr>
			<td>&nbsp;</td>
			<td colspan="2"><html:text name="mdRegistrationsForm" property="student.courierAddress.line2" maxlength="28" size="30"/></td>
		</tr><tr>
			<td>&nbsp;</td>
			<td colspan="2"><html:text name="mdRegistrationsForm" property="student.courierAddress.line3" maxlength="28" size="30"/></td>
		</tr><tr>
			<td>&nbsp;</td>
			<td colspan="2"><html:text name="mdRegistrationsForm" property="student.courierAddress.line4" maxlength="28" size="30"/></td>
		</tr><tr>
			<td>&nbsp;</td>
			<td colspan="2"><html:text name="mdRegistrationsForm" property="student.courierAddress.line5" maxlength="28" size="30"/></td>
		</tr><tr>
			<td>&nbsp;</td>
			<td colspan="2"><html:text name="mdRegistrationsForm" property="student.courierAddress.line6" maxlength="28" size="30"/></td>
		</tr><tr>
			<td><fmt:message key="md.pageCourier.postalcode"/>&nbsp;</td>
			<td><html:text name="mdRegistrationsForm" property="student.courierAddress.areaCode" maxlength="4" size="6"/>&nbsp;
			<html:submit property="action.search3"><fmt:message key="button.searchPostalcode"/></html:submit></td>
			<td align="left"><fmt:message key="md.pageCourier.postcodeinfo"/></td>
		</tr><tr>
			<td><fmt:message key="md.pageCourier.contactnr"/></td>
			<td><html:text name="mdRegistrationsForm" property="student.contactNr" maxlength="40" size="40"/></td>
			<td align="left"><fmt:message key="md.pageCourier.contactnrinfo"/></td>
		</tr>

		</sakai:group_table>

		<sakai:actions>
			<html:submit property="action"><fmt:message key="button.continue"/></html:submit>
			<html:submit property="action"><fmt:message key="button.back"/></html:submit>
			<html:submit property="action"><fmt:message key="button.cancel"/></html:submit>
		</sakai:actions>

	</html:form>
</sakai:html>