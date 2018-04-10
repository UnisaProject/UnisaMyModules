<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setBundle basename="za.ac.unisa.lms.tools.biodetails.ApplicationResources"/>
<%
response.setDateHeader("Expires",0);
response.setDateHeader("Date",0);
response.setHeader("PRAGMA","NO-CACHE");
response.setHeader("Cache-Control","no-cache");
%>
<sakai:html>
<!-- Form -->
	<html:form action="/addressDetails">
		<sakai:messages/>
		<sakai:messages message="true"/>		
		<sakai:heading><fmt:message key="page.heading.advisor"/></sakai:heading>			
		<sakai:instruction>
			<fmt:message key="page.address.instruction8"/><br/>
			<logic:notEqual name="bioDetailsForm" property="addressType" value="iAddress" >
			<fmt:message key="page.address.instruction9"/><br/>			
			</logic:notEqual>		
			<fmt:message key="page.required.instruction"/>&nbsp;<sakai:required/>
		</sakai:instruction>
		<logic:notEqual name="bioDetailsForm" property="addressType" value="iAddress" >		
			<sakai:group_heading><fmt:message key="page.subheading.assistance"/></sakai:group_heading>		
		</logic:notEqual>
		<logic:equal name="bioDetailsForm" property="addressType" value="iAddress" >
			<sakai:group_heading><fmt:message key="page.subheading.international"/></sakai:group_heading>
		</logic:equal>
		<sakai:group_table>
		<tr>
			<td colspan="2"></td>
		</tr><tr>
			<td><fmt:message key="page.address.adv.effectDate"/>&nbsp;<sakai:required/></td>
			<td><html:text property="adrYear" size="4" maxlength="4"/> / <html:text property="adrMonth" size="2" maxlength="2"/> / <html:text property="adrDay" size="2" maxlength="2"/></td>
		</tr>
		<logic:equal name="bioDetailsForm" property="addressType" value="iAddress" >	
			<logic:notEmpty name="countryList">
				<tr>
					<td><fmt:message key="page.address.adv.country"/>&nbsp;</td>
					<td>
						<html:select property="addressCountryCode">
							<html:options collection="countryList" property="value" labelProperty="label"/>
						</html:select>	
					</td>
				</tr>
			</logic:notEmpty>
			<tr>
				<td>&nbsp;</td>
				<td><fmt:message key="page.address.instruction14"/></td>
			</tr>
		</logic:equal>
		<tr>
			<td><fmt:message key="page.address.adv.postal"/>&nbsp;</td>
			<td><html:text name="bioDetailsForm" property="advisorPostalLine1" size="28" maxlength="28"/></td>
		</tr><tr>
			<td>&nbsp;</td>
			<td><html:text name="bioDetailsForm" property="advisorPostalLine2" size="28" maxlength="28"/></td>
		</tr><tr>
			<td>&nbsp;</td>
			<td><html:text name="bioDetailsForm" property="advisorPostalLine3" size="28" maxlength="28"/></td>
		</tr>
		<tr>
			<td>&nbsp;</td>
			<td><html:text name="bioDetailsForm" property="advisorPostalLine4" size="28" maxlength="28"/></td>
		</tr><tr>
			<td>&nbsp;</td>
			<td><html:text name="bioDetailsForm" property="advisorPostalLine5" size="28" maxlength="28"/></td>
		</tr><tr>
			<td>&nbsp;</td>
			<td><html:text name="bioDetailsForm" property="advisorPostalLine6" size="28" maxlength="28"/></td>
		</tr>
		<logic:notEqual name="bioDetailsForm" property="addressType" value="iAddress" >				
			<tr>
				<td><fmt:message key="page.postalCode"/>&nbsp;</td>
				<td><html:text name="bioDetailsForm" property="advisorPostalCode" size="4" maxlength="4"/>&nbsp;<fmt:message key="page.address.adv.restrict"/></td>
			</tr>
		</logic:notEqual>
		<logic:equal name="bioDetailsForm" property="addressType" value="iAddress" >	
			<tr>
				<td>&nbsp;</td>
				<td><fmt:message key="page.address.instruction13"/></td>
			</tr>
		</logic:equal>
		<tr>
			<td><fmt:message key="page.address.adv.physical"/>&nbsp;</td>
			<td><html:text name="bioDetailsForm" property="advisorPhysicalLine1" size="28" maxlength="28"/></td>
		</tr><tr>
			<td>&nbsp;</td>
			<td><html:text name="bioDetailsForm" property="advisorPhysicalLine2" size="28" maxlength="28"/></td>
		</tr><tr>
			<td>&nbsp;</td>
			<td><html:text name="bioDetailsForm" property="advisorPhysicalLine3" size="28" maxlength="28"/></td>
		</tr><tr>
			<td>&nbsp;</td>
			<td><html:text name="bioDetailsForm" property="advisorPhysicalLine4" size="28" maxlength="28"/></td>
		</tr><tr>
			<td>&nbsp;</td>
			<td><html:text name="bioDetailsForm" property="advisorPhysicalLine5" size="28" maxlength="28"/></td>
		</tr><tr>
			<td>&nbsp;</td>
			<td><html:text name="bioDetailsForm" property="advisorPhysicalLine6" size="28" maxlength="28"/></td>
		</tr>
		<logic:notEqual name="bioDetailsForm" property="addressType" value="iAddress" >				
			<tr>
				<td><fmt:message key="page.postalCode"/>&nbsp;</td>
				<td><html:text name="bioDetailsForm" property="advisorPhysicalPostalCode" size="4" maxlength="4"/>&nbsp;<fmt:message key="page.address.adv.restrict"/></td>
			</tr>
		</logic:notEqual>
		<logic:equal name="bioDetailsForm" property="addressType" value="iAddress" >	
			<tr>
				<td>&nbsp;</td>
				<td><fmt:message key="page.address.instruction13"/></td>
			</tr>
		</logic:equal>
		<tr>
			<td><fmt:message key="page.address.adv.courier"/>&nbsp;</td>
			<td><html:text name="bioDetailsForm" property="advisorCourierLine1" size="28" maxlength="28"/></td>
		</tr><tr>
			<td>&nbsp;</td>
			<td><html:text name="bioDetailsForm" property="advisorCourierLine2" size="28" maxlength="28"/></td>
		</tr><tr>
			<td>&nbsp;</td>
			<td><html:text name="bioDetailsForm" property="advisorCourierLine3" size="28" maxlength="28"/></td>
		</tr><tr>
			<td>&nbsp;</td>
			<td><html:text name="bioDetailsForm" property="advisorCourierLine4" size="28" maxlength="28"/></td>
		</tr><tr>
			<td>&nbsp;</td>
			<td><html:text name="bioDetailsForm" property="advisorCourierLine5" size="28" maxlength="28"/></td>
		</tr><tr>
			<td>&nbsp;</td>
			<td><html:text name="bioDetailsForm" property="advisorCourierLine6" size="28" maxlength="28"/></td>
		</tr>
		<logic:notEqual name="bioDetailsForm" property="addressType" value="iAddress" >				
			<tr>
				<td><fmt:message key="page.postalCode"/>&nbsp;</td>
				<td><html:text name="bioDetailsForm" property="advisorCourierPostalCode" size="4" maxlength="4"/>&nbsp;<fmt:message key="page.address.adv.restrict"/></td>
			</tr>
		</logic:notEqual>
		<logic:equal name="bioDetailsForm" property="addressType" value="iAddress" >	
			<tr>
				<td>&nbsp;</td>
				<td><fmt:message key="page.address.instruction13"/></td>
			</tr>
		</logic:equal>
		<tr>
			<td><fmt:message key="page.contact"/></td>
			<td><html:text name="bioDetailsForm" property="advisorContactNr" size="40" maxlength="28"/></td>
		</tr>			
		</sakai:group_table>
		<sakai:actions>	
			<html:submit property="action"><fmt:message key="button.request"/></html:submit>
			<html:submit titleKey="button.cancel" property="action"><fmt:message key="button.cancel"/></html:submit>									
		</sakai:actions>		
	</html:form>
</sakai:html>
