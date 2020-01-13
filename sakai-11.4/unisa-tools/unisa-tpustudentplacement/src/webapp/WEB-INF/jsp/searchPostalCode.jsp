<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setBundle basename="za.ac.unisa.lms.tools.tpustudentplacement.ApplicationResources"/>

<sakai:html>

<!-- Form -->
	<html:form action="/postalCodeSearch">	
		<sakai:messages/>
		<sakai:messages message="true"/>		
		<sakai:heading><fmt:message key="heading.searchPostalCode"/></sakai:heading>
<!-- Search input-->
<!-- Box Type-->		
		<sakai:group_heading><fmt:message key="postalCodeSearch.instruction4"/></sakai:group_heading>		
		<sakai:group_table>
			<tr>
				<td><html:radio property="postalBoxType" value="B"/><strong><fmt:message key="prompt.box"/></strong></td>
				<td><html:radio property="postalBoxType" value="S"/><strong><fmt:message key="prompt.street"/></strong></td>
			</tr>				
		</sakai:group_table>	
<!-- Search on type (postal code or suburb)-->		
		<sakai:group_heading><fmt:message key="postalCodeSearch.instruction1"/></sakai:group_heading>
		<sakai:group_table>
		<tr>
			<td><html:radio property="searchType" value="postal"/><strong><fmt:message key="prompt.postalCode"/>:</strong></td>
			<td><html:text property="searchPostalCode"/>&nbsp;</td>
		</tr>
		<tr>
			<td><strong>OR</strong></td>
			<td>&nbsp;</td>
		</tr>			
		<tr>
			<td><html:radio property="searchType" value="suburb"/><strong><fmt:message key="prompt.suburb"/>:</strong></td>
			<td><html:text property="searchSuburb"/>&nbsp;<fmt:message key="postalCodeSearch.instruction2"/></td>
		</tr>		
	</sakai:group_table>
	
	<logic:notEmpty name="postalList">
			<sakai:actions>
				<html:submit property="action"><fmt:message key="button.display"/></html:submit>
			</sakai:actions>		
			<hr/>
			<sakai:instruction><fmt:message key="postalCodeSearch.instruction3"/></sakai:instruction>			
			<br/><strong><fmt:message key="prompt.postalCodes"/></strong>
			<br/><html:select property="searchResult" size="5">
					<html:options collection="postalList" property="value" labelProperty="label"/>
				</html:select>	
			<br/><br/>
	</logic:notEmpty>
	<sakai:actions>		
		<logic:notEmpty name="postalList">			
		    <html:hidden property="goto" value="selectPostalCode"/>
		</logic:notEmpty>
		<logic:empty name="postalList">
				<html:hidden property="goto" value="searchPostalCode"/>
		</logic:empty>  
		<logic:empty name="postalList">
			<html:submit property="action"><fmt:message key="button.display"/></html:submit>
		</logic:empty>
		<logic:notEmpty name="postalList">
			<html:submit property="action"><fmt:message key="button.continue"/></html:submit>
		</logic:notEmpty>
		<html:submit property="action"><fmt:message key="button.back"/></html:submit> 
	</sakai:actions>
	
	</html:form>
</sakai:html>