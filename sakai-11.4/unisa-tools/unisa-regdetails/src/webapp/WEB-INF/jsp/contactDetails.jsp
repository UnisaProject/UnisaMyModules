<%@ page import="za.ac.unisa.lms.tools.regdetails.forms.RegDetailsForm"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setBundle basename="za.ac.unisa.lms.tools.regdetails.ApplicationResources"/>

<sakai:html>

<!-- Form -->
	<html:form action="/additions">
	<html:hidden property="goto" value="step1"/>
		<sakai:messages/>
		<sakai:messages message="true"/>
		<td><sakai:instruction><fmt:message key="page.instruction.addresscontact" /></sakai:instruction>
		<sakai:heading><fmt:message key="page.heading.addresscontact"/></sakai:heading>	

<!-- Address Details -->
	
	<table border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td><sakai:group_heading><fmt:message key="page.group.heading.address"/></sakai:group_heading></td>
	</tr>
	<tr><td>
		<table border="0" cellpadding="3" cellspacing="1" width="100%">
		<tr>
			<td style="width:50%"><fmt:message key="page.label.postaladdress"/>&nbsp;</td>
			<logic:iterate id="record" name="regDetailsForm" property="postal" indexId = "i">
				<logic:equal name="i" value="0">
					<td><bean:write name="record"/></td>
				</logic:equal>
				<logic:greaterThan name="i" value="0">
					<tr>
						<td>&nbsp;</td>
						<td><bean:write name="record"/></td>
					</tr>
				</logic:greaterThan>
			</logic:iterate> 
		</tr>
		<tr>
			<td style="width:50%"><fmt:message key="page.label.postalcode"/>&nbsp;</td>
			<td><bean:write name="regDetailsForm" property="postalAddress.postalCode"/>&nbsp;</td>
		</tr>
		<tr>
			<td style="width:50%"><fmt:message key="page.label.physicaladdress"/>&nbsp;</td>
			<logic:iterate id="record" name="regDetailsForm" property="physical" indexId = "i">
				<logic:equal name="i" value="0">
					<td><bean:write name="record"/></td>
				</logic:equal>
				<logic:greaterThan name="i" value="0">
					<tr>
						<td>&nbsp;</td>
						<td><bean:write name="record"/></td>
					</tr>
				</logic:greaterThan>
			</logic:iterate> 
		</tr><tr>
			<td style="width:50%"><fmt:message key="page.label.postalcode"/>&nbsp;</td>
			<td>
				<logic:equal name="regDetailsForm" property="physicalAddress.postalCode" value="0">
					<td>&nbsp;</td>
				</logic:equal>
				<logic:notEqual name="regDetailsForm" property="physicalAddress.postalCode" value="0">
					<bean:write name="regDetailsForm" property="physicalAddress.postalCode"/>&nbsp;
				</logic:notEqual>
			</td>
		</tr>
		<tr><td colspan="2"><font color="red"><fmt:message key="page.label.courieraddressNote"/>&nbsp;</font></td></tr>
		<tr>
			<td style="width:50%">
				<fmt:message key="page.label.courieraddress"/>&nbsp;
			</td>
			<logic:iterate id="record" name="regDetailsForm" property="courier" indexId = "i">
				<logic:equal name="i" value="0">
					<td><bean:write name="record"/></td>
				</logic:equal>
				<logic:greaterThan name="i" value="0">
					<tr>
						<td>&nbsp;</td>
						<td><bean:write name="record"/></td>
					</tr>
				</logic:greaterThan>
			</logic:iterate> 
		</tr><tr>
			<td style="width:50%"><fmt:message key="page.label.postalcode"/>&nbsp;</td>
			<td>
				<logic:equal name="regDetailsForm" property="courierAddress.postalCode" value="0">
					<td>&nbsp;</td>
				</logic:equal>
				<logic:notEqual name="regDetailsForm" property="courierAddress.postalCode" value="0">
					<bean:write name="regDetailsForm" property="courierAddress.postalCode"/>&nbsp;
				</logic:notEqual>
			</td>
		</tr><tr>
			<td style="width:50%"><fmt:message key="page.label.contactnumber"/>&nbsp;</td>
			<td><bean:write name="regDetailsForm" property="contactNumber"/>&nbsp;</td>
		</tr>
		</table>
	</td></tr>
<!-- Contact details-->
	<tr>
		<td><sakai:group_heading><fmt:message key="page.group.heading.contact"/></sakai:group_heading></td>
	</tr>
	<tr><td>
		<table border="0" cellpadding="3" cellspacing="1" width="100%">
		<tr>
			<td style="width:50%"><fmt:message key="page.label.homenumber"/>&nbsp;</td>
			<td><bean:write name="regDetailsForm" property="homeNumber"/>&nbsp;</td>
		</tr><tr>
			<td style="width:50%"><fmt:message key="page.label.worknumber"/>&nbsp;</td>
			<td><bean:write name="regDetailsForm" property="workNumber"/>&nbsp;</td>
		</tr><tr>
			<td style="width:50%"><fmt:message key="page.label.cellnumber"/>&nbsp;</td>
			<td><bean:write name="regDetailsForm" property="cellNumber"/>&nbsp;</td>
		</tr><tr>
			<td style="width:50%"><fmt:message key="page.label.faxnumber"/>&nbsp;</td>
			<td><bean:write name="regDetailsForm" property="faxNumber"/>&nbsp;</td>
		</tr><tr>
			<td style="width:50%"><fmt:message key="page.label.emailaddress"/>&nbsp;</td>
			<td><bean:write name="regDetailsForm" property="email"/>&nbsp;</td>
		</tr>
		</table>	
	</td></tr>
	</table>
	
	   <sakai:actions>	  
			<html:submit property="action"><fmt:message key="button.continue"/></html:submit> 	
			<html:submit property="action"><fmt:message key="button.updatedetails"/></html:submit>
			<html:submit property="action"><fmt:message key="button.cancel"/></html:submit>
	   </sakai:actions>		
	</html:form>
</sakai:html>