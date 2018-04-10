<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<fmt:setBundle basename="za.ac.unisa.lms.tools.biodetails.ApplicationResources"/>

<sakai:html>

<!-- Form -->
	<html:form action="/addressDetails">
		<html:hidden property="page" value="2"/>
		<!-- first part of page 2 -->
		<html:hidden property="goto" value="2a"/>
		<html:hidden property="action" value="nextStep"/>

		<sakai:messages/>
		<sakai:messages message="true"/>
		<sakai:heading><fmt:message key="page.heading.address"/></sakai:heading>

		<sakai:instruction>
			<fmt:message key="page.address.instruction1"/>&nbsp;<fmt:message key="page.address.instruction10"/><br/>
			<fmt:message key="page.address.instruction6"/><br/>
			<fmt:message key="page.required.instruction"/>&nbsp;<sakai:required/>
		</sakai:instruction>
		<logic:empty name="bioDetailsForm" property="postalType">
			<sakai:group_heading><fmt:message key="page.heading.step2.choose"/></sakai:group_heading>
		</logic:empty>
		<logic:notEmpty name="bioDetailsForm" property="postalType">
			<sakai:group_heading><fmt:message key="page.heading.step2.adress"/></sakai:group_heading>
		</logic:notEmpty>
<!-- Delivery type-->
		<sakai:group_table>
		<logic:equal name="bioDetailsForm" property="addressType" value="postal">
		<tr>
			<td><html:radio property="postalType" value="street"  onclick="this.form.submit()"/><fmt:message key="page.address.eng.postal.type1"/></td>
		</tr><tr>
			<td><html:radio property="postalType" value="po" onclick="this.form.submit()"/><fmt:message key="page.address.eng.postal.type2"/></td>
		</tr><tr>
			<td><html:radio property="postalType" value="private" onclick="this.form.submit()"/><fmt:message key="page.address.eng.postal.type3"/></td>
		</tr><tr>
			<td><html:radio property="postalType" value="cluster" onclick="this.form.submit()"/><fmt:message key="page.address.eng.postal.type5"/></td>
		</tr><tr>
			<td><html:radio property="postalType" value="postnet" onclick="this.form.submit()"/><fmt:message key="page.address.eng.postal.type6"/></td>
		</tr>
		</logic:equal>
		</sakai:group_table>
	</html:form>
<!-- Form -->
	<html:form action="/addressDetails">
		<logic:empty name="bioDetailsForm" property="postalType">
		<html:hidden property="page" value="2"/>
		<html:hidden property="goto" value="2b"/>
			<sakai:actions>
				<html:submit property="action"><fmt:message key="button.continue"/></html:submit>
				<html:submit property="action"><fmt:message key="button.back"/></html:submit>
				<html:submit property="action"><fmt:message key="button.advisor"/></html:submit>
				<html:submit property="action"><fmt:message key="button.cancel"/></html:submit>
			</sakai:actions>
		</logic:empty>
	</html:form>

<!-- Address input -->
	<logic:notEmpty name="bioDetailsForm" property="postalType">
	<html:form action="/addressDetails">
		<html:hidden property="page" value="3"/>
		<!-- second part of page 2 -->
		<html:hidden property="goto" value="2b"/>
	<hr/>
	<sakai:instruction>
			<fmt:message key="page.address.step2.instruction2"/><br>
			<fmt:message key="page.address.step2.instruction3"/>
	</sakai:instruction>
	<sakai:group_table>

	<%// eng street address option %>
	<logic:equal name="bioDetailsForm" property="postalType" value="street">
			<tr>
				<td><fmt:message key="page.address.eng.postal.type1"/>&nbsp;<sakai:required/></td>
				<td><html:text property="postalStreetLine1" size="30" maxlength="28"/>&nbsp;</td>
			</tr><tr>
				<td>&nbsp;</td>
				<td><html:text property="postalStreetLine2" size="30" maxlength="28"/>&nbsp;</td>
			</tr><tr>
				<td>&nbsp;</td>
				<td><html:text property="postalStreetLine3" size="30" maxlength="28"/>&nbsp;</td>
			</tr>
	</logic:equal>

	<%// rest of eng address options %>
	<logic:equal name="bioDetailsForm" property="addressType" value="postal">
		<logic:notEqual name="bioDetailsForm" property="postalType" value="street">
		<tr>
			<td>
				<logic:equal name="bioDetailsForm" property="postalType" value="po">
					<fmt:message key="page.address.eng.postal.type2"/>
				</logic:equal>
				<logic:equal name="bioDetailsForm" property="postalType" value="private">
						<fmt:message key="page.address.eng.postal.type3"/>
				</logic:equal>
				<logic:equal name="bioDetailsForm" property="postalType" value="cluster">
					<fmt:message key="page.address.eng.postal.type5"/>
				</logic:equal>
				<logic:equal name="bioDetailsForm" property="postalType" value="postnet">
					<fmt:message key="page.address.eng.postal.type6"/>
				</logic:equal>
				<sakai:required/>
			</td>
			<td><html:text property="postalLine1" size="30" maxlength="10"/>&nbsp;</td>
			<logic:notEqual name="bioDetailsForm" property="postalType" value="private">
				<logic:notEqual name="bioDetailsForm" property="postalType" value="postnet">
					<td><strong><fmt:message key="page.address.step2.instruction4"/></strong></td>
				</logic:notEqual>
			</logic:notEqual>
			<logic:equal name="bioDetailsForm" property="postalType" value="private">
				<td><strong><fmt:message key="page.address.step2.instruction7"/></strong></td>
			</logic:equal>
			<logic:equal name="bioDetailsForm" property="postalType" value="postnet">
				<td><strong><fmt:message key="page.address.step2.instruction8"/></strong></td>
			</logic:equal>
		</tr>
		</logic:notEqual>
	</logic:equal>

	<%// postnet has extra line: private bag number %>
	<logic:equal name="bioDetailsForm" property="addressType" value="postal">
		<logic:equal name="bioDetailsForm" property="postalType" value="postnet">
			<td><fmt:message key="page.address.eng.postal.type3"/>&nbsp;<sakai:required/></td>
			<td><html:text property="postalLine2" size="30" maxlength="10"/>&nbsp;</td>
			<td><strong><fmt:message key="page.address.step2.instruction7"/></strong></td>
		</logic:equal>
	</logic:equal>
	<logic:equal name="bioDetailsForm" property="addressType" value="postal">
		<logic:equal name="bioDetailsForm" property="postalType" value="street">
				<tr>
					<td><fmt:message key="page.address.suburb"/>&nbsp;<sakai:required/></td>
					<td colspan="2"><html:text name="bioDetailsForm" property="postalStreetSuburb" size="30" maxlength="28" readonly="true" /></td>
				</tr>
		</logic:equal>
		<logic:notEqual name="bioDetailsForm" property="postalType" value="street">
				<tr>
					<td><fmt:message key="page.address.suburb"/>&nbsp;<sakai:required/></td>
					<td colspan="2"><html:text name="bioDetailsForm" property="postalSuburb" size="30" maxlength="28" readonly="true" /></td>
				</tr>
		</logic:notEqual>
	</logic:equal>
	<logic:equal name="bioDetailsForm" property="addressType" value="postal">
		<logic:equal name="bioDetailsForm" property="postalType" value="street">
				<tr>
					<td><fmt:message key="page.postalCode"/>&nbsp;<sakai:required/></td>
					<td><html:text name="bioDetailsForm" property="postalStreetCode" maxlength="4" size="6" readonly="true" /></td>
					<td><html:submit property="action"><fmt:message key="button.searchPostSub"/></html:submit></td>
				</tr>
		</logic:equal>
		<logic:notEqual name="bioDetailsForm" property="postalType" value="street">
				<tr>
					<td><fmt:message key="page.postalCode"/>&nbsp;<sakai:required/></td>
					<td><html:text name="bioDetailsForm" property="postalCode" maxlength="4" size="6" readonly="true" /></td>
					<td><html:submit property="action"><fmt:message key="button.searchPostSub"/></html:submit></td>
				</tr>
		</logic:notEqual>
	</logic:equal>
	</sakai:group_table>

	<sakai:actions>
		<html:submit property="action"><fmt:message key="button.continue"/></html:submit>
		<html:submit property="action"><fmt:message key="button.back"/></html:submit>
		<html:submit property="action"><fmt:message key="button.advisor"/></html:submit>
		<html:submit property="action"><fmt:message key="button.cancel"/></html:submit>
	</sakai:actions>

	</html:form>
	</logic:notEmpty>

</sakai:html>