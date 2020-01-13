<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setBundle basename="za.ac.unisa.lms.tools.biodetails.ApplicationResources"/>

<sakai:html>
	
<!-- Form -->
	<html:form action="/addressDetails">
	<html:hidden property="page" value="1"/>
	<html:hidden property="goto" value="2a"/>
	
		<sakai:messages/>
		<sakai:messages message="true"/>		
		<sakai:heading><fmt:message key="page.heading.address"/></sakai:heading>	
		
		<sakai:instruction>
			<fmt:message key="page.address.instruction1"/><br/><br/>
			<fmt:message key="page.address.instruction6"/><br/><br/>
			<strong><fmt:message key="page.address.instruction2"/></strong>
			<fmt:message key="page.address.instruction3"/>
			<fmt:message key="page.address.instruction4"/>
			<fmt:message key="page.address.instruction5"/>
		</sakai:instruction>
			
		<sakai:group_heading><fmt:message key="page.heading.step1.adress1"/></sakai:group_heading>					
<!-- Address type RSA -->
		<sakai:group_table>
		<tr>
			<td><strong><fmt:message key="page.heading.step1.adress2"/></strong></td>
		</tr><tr>
			<td><html:radio property="addressType" value="postal"/><fmt:message key="page.address.type1"/></td>
		</tr><tr>
			<td><html:radio property="addressType" value="physical"/><fmt:message key="page.address.type3"/></td>
		</tr><tr>
			<td><html:radio property="addressType" value="courier"/><fmt:message key="page.address.type5"/></td>
		</tr>
<!-- Address type International -->
		<tr>
			<td><strong><fmt:message key="page.heading.step1.adress3"/></strong></td>
		</tr><tr>
			<td><html:radio property="addressType" value="iAddress"/><fmt:message key="page.address.type4"/></td>
		</tr>
		</sakai:group_table>
		
		<sakai:actions>		
			<html:submit property="action"><fmt:message key="button.continue"/></html:submit>
			<html:submit property="action"><fmt:message key="button.advisor"/></html:submit>
			<html:submit property="action"><fmt:message key="button.cancel"/></html:submit>
		</sakai:actions>
<!-- Added by Edmund Wilschewski 03/06/2015 -->			
		<div style="display: none;" align="center"><br><bean:write name="bioDetailsForm" property="version"/></div>
		<br/>
	</html:form>
</sakai:html>