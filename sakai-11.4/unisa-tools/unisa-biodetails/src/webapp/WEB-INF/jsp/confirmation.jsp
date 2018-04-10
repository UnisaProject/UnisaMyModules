<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setBundle basename="za.ac.unisa.lms.tools.biodetails.ApplicationResources"/>

<sakai:html>
<head>
	<script type="text/javascript" src="/unisa-studentregistration/js/jquery.js"></script>
    <script language="JavaScript" type="text/javascript">  
    
	//Call when document is ready
	$(document).ready(function() {
		//Uncheck radio buttons as sometimes they are cached when going back
		//Reset Radio Button (Twice...)
		$('input:radio[name="disclaimer"]').removeAttr('checked');
		$("input:radio[name='disclaimer']").each(function(i) {
		       this.checked = false;
		});

	});
	</script>
</head>

<!-- Toolbar -->


<!-- Form -->
	<html:form action="/addressDetails.do">
	<html:hidden property="goto" value="step4"/>

		<sakai:messages/>
		<sakai:messages message="true"/>
		<h3><fmt:message key="page.heading.address"/></h3>
		<sakai:instruction><fmt:message key="page.address.instruction7"/></sakai:instruction>

		<sakai:group_heading><fmt:message key="page.heading.step3.adress"/></sakai:group_heading>
		<sakai:group_table>
		<logic:equal name="bioDetailsForm" property="postalType" value="street">
			<tr>
				<td><fmt:message key="page.address.eng.postal.type1"/></td>
				<td><bean:write name="bioDetailsForm" property="postalStreetLine1"/>&nbsp;</td>
			</tr>
			<logic:notEqual name="bioDetailsForm" property="postalStreetLine2" value="">
			<tr>
				<td>&nbsp;</td>
				<td><bean:write name="bioDetailsForm" property="postalStreetLine2"/>&nbsp;</td>
			</tr>
			</logic:notEqual>
			<logic:notEqual name="bioDetailsForm" property="postalStreetLine3" value="">
			<tr>
				<td>&nbsp;</td>
				<td><bean:write name="bioDetailsForm" property="postalStreetLine3"/>&nbsp;</td>
			</tr>
			</logic:notEqual>
		</logic:equal>

		<logic:equal name="bioDetailsForm" property="postalType" value="straat">
			<tr>
				<td><fmt:message key="page.address.afr.postal.type1"/></td>
				<td><bean:write name="bioDetailsForm" property="postalStreetLine1"/>&nbsp;</td>
			</tr>
			<logic:notEqual name="bioDetailsForm" property="postalStreetLine2" value="">
			<tr>
				<td>&nbsp;</td>
				<td><bean:write name="bioDetailsForm" property="postalStreetLine2"/>&nbsp;</td>
			</tr>
			</logic:notEqual>
			<logic:notEqual name="bioDetailsForm" property="postalStreetLine3" value="">
			<tr>
				<td>&nbsp;</td>
				<td><bean:write name="bioDetailsForm" property="postalStreetLine3"/>&nbsp;</td>
			</tr>
			</logic:notEqual>
		</logic:equal>
		<logic:equal name="bioDetailsForm" property="addressType" value="postal">
				<logic:equal name="bioDetailsForm" property="postalType" value="po">
					<tr><td><fmt:message key="page.address.eng.postal.type2"/></td>
				</logic:equal>
				<logic:equal name="bioDetailsForm" property="postalType" value="private">
					<tr><td><fmt:message key="page.address.eng.postal.type3"/></td>
				</logic:equal>
				<logic:equal name="bioDetailsForm" property="postalType" value="cluster">
					<tr><td><fmt:message key="page.address.eng.postal.type5"/></td>
				</logic:equal>
				<logic:equal name="bioDetailsForm" property="postalType" value="postnet">
					<tr><td><fmt:message key="page.address.eng.postal.type6"/></td>
				</logic:equal>
				<logic:notEqual name="bioDetailsForm" property="postalType" value="street">
					<logic:notEqual name="bioDetailsForm" property="postalType" value="straat">
						<td><bean:write name="bioDetailsForm" property="postalLine1"/>&nbsp;</td>
						</tr>
						<logic:equal name="bioDetailsForm" property="postalType" value="postnet">
							<tr>
								<td><fmt:message key="page.address.eng.postal.type3"/></td>
								<td><bean:write name="bioDetailsForm" property="postalLine2"/>&nbsp;</td>
							</tr>
						</logic:equal>
					</logic:notEqual>
				</logic:notEqual>
		</logic:equal>
		<logic:equal name="bioDetailsForm" property="addressType" value="pos">
			<logic:equal name="bioDetailsForm" property="postalType" value="po">
				<tr><td><fmt:message key="page.address.afr.postal.type2"/></td>
			</logic:equal>
			<logic:equal name="bioDetailsForm" property="postalType" value="private">
				<tr><td><fmt:message key="page.address.afr.postal.type3"/></td>
			</logic:equal>
			<logic:equal name="bioDetailsForm" property="postalType" value="postnet">
				<tr><td><fmt:message key="page.address.afr.postal.type5"/></td>
			</logic:equal>
			<logic:notEqual name="bioDetailsForm" property="postalType" value="street">
				<logic:notEqual name="bioDetailsForm" property="postalType" value="straat">
					<td><bean:write name="bioDetailsForm" property="postalLine1"/>&nbsp;</td>
					</tr>
					<logic:equal name="bioDetailsForm" property="postalType" value="postnet">
						<tr>
							<td><fmt:message key="page.address.afr.postal.type3"/></td>
							<td><bean:write name="bioDetailsForm" property="postalLine2"/>&nbsp;</td>
						</tr>
					</logic:equal>
				</logic:notEqual>
			</logic:notEqual>
			
		</logic:equal>
		<logic:equal name="bioDetailsForm" property="postalType" value="street">
			<tr>
				<td><fmt:message key="page.address.suburb"/></td>
				<td><bean:write name="bioDetailsForm" property="postalStreetSuburb"/></td>
			</tr><tr>
				<td><fmt:message key="page.postalCode"/></td>
				<td><bean:write name="bioDetailsForm" property="postalStreetCode"/></td>
			</tr>
		</logic:equal>
		<logic:equal name="bioDetailsForm" property="postalType" value="straat">
			<tr>
				<td><fmt:message key="page.address.suburb"/></td>
				<td><bean:write name="bioDetailsForm" property="postalStreetSuburb"/></td>
			</tr><tr>
				<td><fmt:message key="page.postalCode"/></td>
				<td><bean:write name="bioDetailsForm" property="postalStreetCode"/></td>
			</tr>
		</logic:equal>
		<logic:notEqual name="bioDetailsForm" property="postalType" value="street">
			<logic:notEqual name="bioDetailsForm" property="postalType" value="straat">
				<tr>
					<td><fmt:message key="page.address.suburb"/></td>
					<td><bean:write name="bioDetailsForm" property="postalSuburb"/></td>
				</tr><tr>
					<td><fmt:message key="page.postalCode"/></td>
					<td><bean:write name="bioDetailsForm" property="postalCode"/></td>
				</tr>
			</logic:notEqual>
		</logic:notEqual>
		
		<tr>
			<td colspan="2">
				<html:radio property="disclaimer" value="1"/>&nbsp;
				<fmt:message key="page.disclaimer.postal"/><br/>
				<fmt:message key="page.disclaimer.additional1"/><br/>
				<fmt:message key="page.disclaimer.additional2"/><br/>
				<fmt:message key="page.disclaimer.additional3"/><br/>
				<fmt:message key="page.disclaimer.additional4"/><br/>
				<fmt:message key="page.disclaimer.additional5"/><br/>
				<fmt:message key="page.disclaimer.additional6"/><br/>
				<fmt:message key="page.disclaimer.additional7"/><br/><br/>
			</td>
		</tr>
		</sakai:group_table>
		<sakai:actions>
			<html:submit property="action"><fmt:message key="button.submit"/></html:submit>
			<html:submit property="action"><fmt:message key="button.back"/></html:submit>
			<html:submit property="action"><fmt:message key="button.advisor"/></html:submit>
			<html:submit property="action"><fmt:message key="button.cancel"/></html:submit>
		</sakai:actions>

	</html:form>
</sakai:html>