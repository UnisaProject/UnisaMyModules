<%@ page import="za.ac.unisa.lms.tools.mdapplications.forms.MdApplicationsForm"%>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setBundle basename="za.ac.unisa.lms.tools.mdapplications.ApplicationResources"/>

<sakai:html>

<!-- Form -->
	<html:form action="/mdapplications">
	<html:hidden property="page" value="step2"/>

		<sakai:messages/>
		<sakai:messages message="true"/>
		<sakai:heading><fmt:message key="md.heading"/></sakai:heading>

		<sakai:group_heading><fmt:message key="md.subheading2"/></sakai:group_heading>

		<sakai:group_table>
		<tr>
			<td><fmt:message key="md.page2.spes"/>&nbsp;</td>
			<td colspan="2">
				<html:select property="selectedSpes">
 					<html:options collection="speslist" property="value" labelProperty="label"/>
				</html:select>
			</td>
		</tr><tr>
			<td colspan="3"><fmt:message key="md.page2.instruction"/>&nbsp;<sakai:required/></td>
		</tr><tr>
			<td><fmt:message key="md.page2.home"/>&nbsp;<sakai:required/></td>
			<td><html:text name="mdApplicationsForm" property="student.homePhone" maxlength="28" size="30"/></td>
			<td align="left"><fmt:message key="md.page2.eg1"/></td>
		</tr><tr>
			<td><fmt:message key="md.page2.work"/></td>
			<td><html:text name="mdApplicationsForm" property="student.workPhone" maxlength="28" size="30"/></td>
			<td align="left"><fmt:message key="md.page2.eg1"/></td>
		</tr><tr>
			<td><fmt:message key="md.page2.cell"/></td>
			<td><html:text name="mdApplicationsForm" property="student.cellNr" maxlength="20" size="25"/></td>
			<td align="left"><fmt:message key="md.page2.eg2"/></td>
		</tr><tr>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td align="left"><fmt:message key="md.page2.eg3"/></td>
		</tr><tr>
			<td><fmt:message key="md.page2.fax"/></td>
			<td><html:text name="mdApplicationsForm" property="student.faxNr" maxlength="28" size="30"/></td>
			<td align="left"><fmt:message key="md.page2.eg1"/></td>
		</tr><tr>
			<td><fmt:message key="md.page2.emailaddress"/>&nbsp;<sakai:required/></td>
			<td colspan="2">
				<!-- changes -->	 
				<logic:equal name="mdApplicationsForm" property="student.emailAddressGood" value="true">
					<bean:write name="mdApplicationsForm" property="student.emailAddress"/>
				</logic:equal>
				<logic:notEqual name="mdApplicationsForm" property="student.emailAddressGood" value="true">
					<html:text name="mdApplicationsForm" property="student.emailAddress" maxlength="60" size="40" />
				</logic:notEqual>
			</td>
		</tr><tr>
			<td><fmt:message key="md.page2.postal"/>&nbsp;<sakai:required/></td>
			<td colspan="2"><html:text name="mdApplicationsForm" property="student.postalAddress.line1" maxlength="28" size="30"/></td>
		</tr><tr>
			<td>&nbsp;</td>
			<td colspan="2"><html:text name="mdApplicationsForm" property="student.postalAddress.line2" maxlength="28" size="30"/></td>
		</tr><tr>
			<td>&nbsp;</td>
			<td colspan="2"><html:text name="mdApplicationsForm" property="student.postalAddress.line3" maxlength="28" size="30"/></td>
		</tr><tr>
			<td>&nbsp;</td>
			<td><html:text name="mdApplicationsForm" property="student.postalAddress.line4" maxlength="28" size="30"/></td>
		</tr><tr>
			<td>&nbsp;</td>
			<td colspan="2"><html:text name="mdApplicationsForm" property="student.postalAddress.line5" maxlength="28" size="30"/></td>
		</tr><tr>
			<td>&nbsp;</td>
			<td colspan="2"><html:text name="mdApplicationsForm" property="student.postalAddress.line6" maxlength="28" size="30"/></td>
		</tr><tr>
			<td><fmt:message key="md.page2.postalcode"/>&nbsp;<sakai:required/></td>
			<td><html:text name="mdApplicationsForm" property="student.postalAddress.areaCode" maxlength="4" size="6"/></td>
			<td align="left"><fmt:message key="md.page2.postcodeinfo"/></td>
		</tr><tr>
			<td><fmt:message key="md.page2.country"/></td>
			<td colspan="2">
				<html:select property="selectedCountry">
 					<html:options collection="countrylist" property="value" labelProperty="label"/>
				</html:select>
			</td>
		</tr><tr>
			<td><fmt:message key="md.page2.physical"/>&nbsp;<sakai:required/></td>
			<td colspan="2"><html:text name="mdApplicationsForm" property="student.physicalAddress.line1" maxlength="28" size="30"/></td>
		</tr><tr>
			<td>&nbsp;</td>
			<td colspan="2"><html:text name="mdApplicationsForm" property="student.physicalAddress.line2" maxlength="28" size="30"/></td>
		</tr><tr>
			<td>&nbsp;</td>
			<td colspan="2"><html:text name="mdApplicationsForm" property="student.physicalAddress.line3" maxlength="28" size="30"/></td>
		</tr><tr>
			<td>&nbsp;</td>
			<td colspan="2"><html:text name="mdApplicationsForm" property="student.physicalAddress.line4" maxlength="28" size="30"/></td>
		</tr><tr>
			<td>&nbsp;</td>
			<td colspan="2"><html:text name="mdApplicationsForm" property="student.physicalAddress.line5" maxlength="28" size="30"/></td>
		</tr><tr>
			<td>&nbsp;</td>
			<td colspan="2"><html:text name="mdApplicationsForm" property="student.physicalAddress.line6" maxlength="28" size="30"/></td>
		</tr><tr>
			<td><fmt:message key="md.page2.postalcode"/>&nbsp;</td>
			<td><html:text name="mdApplicationsForm" property="student.physicalAddress.areaCode" maxlength="4" size="6"/></td>
			<td align="left"><fmt:message key="md.page2.postcodeinfo"/></td>
		</tr><tr>
			<td><fmt:message key="md.page2.courier"/>&nbsp;</td>
			<td><html:text name="mdApplicationsForm" property="student.courierAddress.line1" maxlength="28" size="30"/></td>
			<td align="left"><fmt:message key="md.page2.courier2"/></td>
		</tr><tr>
			<td>&nbsp;</td>
			<td colspan="2"><html:text name="mdApplicationsForm" property="student.courierAddress.line2" maxlength="28" size="30"/></td>
		</tr><tr>
			<td>&nbsp;</td>
			<td colspan="2"><html:text name="mdApplicationsForm" property="student.courierAddress.line3" maxlength="28" size="30"/></td>
		</tr><tr>
			<td>&nbsp;</td>
			<td colspan="2"><html:text name="mdApplicationsForm" property="student.courierAddress.line4" maxlength="28" size="30"/></td>
		</tr><tr>
			<td>&nbsp;</td>
			<td colspan="2"><html:text name="mdApplicationsForm" property="student.courierAddress.line5" maxlength="28" size="30"/></td>
		</tr><tr>
			<td>&nbsp;</td>
			<td colspan="2"><html:text name="mdApplicationsForm" property="student.courierAddress.line6" maxlength="28" size="30"/></td>
		</tr><tr>
			<td><fmt:message key="md.page2.postalcode"/>&nbsp;</td>
			<td><html:text name="mdApplicationsForm" property="student.courierAddress.areaCode" maxlength="4" size="6"/></td>
			<td align="left"><fmt:message key="md.page2.postcodeinfo"/></td>
		</tr><tr>
			<td><fmt:message key="md.page2.contactnr"/></td>
			<td><html:text name="mdApplicationsForm" property="student.contactNr" maxlength="40" size="40"/></td>
			<td align="left"><fmt:message key="md.page2.eg1"/></td>
		</tr>

		</sakai:group_table>

		<sakai:actions>
			<html:submit property="action"><fmt:message key="button.continue"/></html:submit>
			<html:submit property="action"><fmt:message key="button.back"/></html:submit>
			<html:submit property="action"><fmt:message key="button.cancel"/></html:submit>
		</sakai:actions>
	</html:form>
</sakai:html>