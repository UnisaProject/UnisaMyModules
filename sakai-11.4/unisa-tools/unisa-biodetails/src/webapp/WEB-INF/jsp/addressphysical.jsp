<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<fmt:setBundle basename="za.ac.unisa.lms.tools.biodetails.ApplicationResources"/>

<sakai:html>

<!-- Form -->
	<html:form action="/addressDetails">		
		<!-- first part of page 2 -->
		<html:hidden property="goto" value="physical"/>
		
		<sakai:messages/>
		<sakai:messages message="true"/>		
		<sakai:heading><fmt:message key="page.heading.address"/></sakai:heading>	
		<sakai:instruction>
			<fmt:message key="page.address.instruction10"/><br/>
			<fmt:message key="page.address.instruction6"/><br/>
			<fmt:message key="page.required.instruction"/>&nbsp;<sakai:required/>
		</sakai:instruction>
		
		<sakai:group_heading><fmt:message key="page.subheading.physical"/></sakai:group_heading>
		<sakai:group_table>
		<tr>
			<td><fmt:message key="page.address.adv.physical"/>&nbsp;<sakai:required/></td>
			<td><html:text name="bioDetailsForm" property="physicalLine1" size="30" maxlength="28"/></td>
		</tr>
		<tr>
			<td>&nbsp;</td>
			<td><html:text name="bioDetailsForm" property="physicalLine2" size="30" maxlength="28"/></td>
		</tr>
		<tr>
			<td>&nbsp;</td>
			<td><html:text name="bioDetailsForm" property="physicalLine3" size="30" maxlength="28"/></td>
		</tr>
		<tr>
			<td>&nbsp;</td>
			<td><html:text name="bioDetailsForm" property="physicalLine4" size="30" maxlength="28"/></td>
		</tr>
	<!--  	<tr>
			<td>&nbsp;</td>
			<td><html:text name="bioDetailsForm" property="physicalLine5" size="30" maxlength="28"/></td>
		</tr>
		<tr>
			<td>&nbsp;</td>
			<td><html:text name="bioDetailsForm" property="physicalLine6" size="30" maxlength="28"/></td>
		</tr>-->
		<tr>
			<td><fmt:message key="page.address.suburb"/>&nbsp;<sakai:required/></td>
			<td colspan="2"><html:text name="bioDetailsForm" property="physicalSuburb" size="30" maxlength="28" readonly="true" /></td>
		</tr>
		<logic:notEmpty name="bioDetailsForm" property="physicalTown">
			<logic:notEqual name="bioDetailsForm" property="physicalTown" value="">	
		<tr>
			<td><fmt:message key="page.address.town"/>&nbsp;<sakai:required/></td>
			<td colspan="2"><html:text name="bioDetailsForm" property="physicalTown" size="30" maxlength="28" readonly="true" /></td>
		</tr>
			</logic:notEqual>
		</logic:notEmpty>
		<tr>
			<td><fmt:message key="page.postalCode"/>&nbsp;</td>
			<td><html:text name="bioDetailsForm" property="physicalPostalCode" maxlength="4" size="6" readonly="true"/>&nbsp;
			<html:submit property="action"><fmt:message key="button.searchPostSub"/></html:submit></td>
			<td align="left">&nbsp;</td>
		</tr>
	</sakai:group_table>	
	<sakai:actions>
		<html:submit property="action"><fmt:message key="button.continue"/></html:submit>
		<html:submit property="action"><fmt:message key="button.back"/></html:submit>
		<html:submit property="action"><fmt:message key="button.advisor"/></html:submit>
		<html:submit property="action"><fmt:message key="button.cancel"/></html:submit>
	</sakai:actions>
	
	</html:form>
	
</sakai:html>