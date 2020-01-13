<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setBundle basename="za.ac.unisa.lms.tools.biodetails.ApplicationResources"/>

<sakai:html>

<!-- Form -->
	<html:form action="/addressDetails">		
		<html:hidden property="page" value="3"/>
		
		<sakai:messages/>
		<sakai:messages message="true"/>		
		<h3><fmt:message key="page.heading.address"/></h3>	
		
		<sakai:instruction>
			<fmt:message key="page.search.instruction1"/>
		</sakai:instruction>
			
		<sakai:group_heading><fmt:message key="page.subheading.search"/></sakai:group_heading>					
		
<!-- Search input-->
		<sakai:group_table>
		<tr>
			<td><html:radio property="searchType" value="postal"/><strong><fmt:message key="page.postalCode"/>:</strong></td>
			<td><html:text property="searchPostalCode"/>&nbsp;</td>
		</tr>
		<tr>
			<td><strong>OR</strong></td>
			<td>&nbsp;</td>
		</tr>			
		<tr>
			<td><html:radio property="searchType" value="suburb"/><strong><fmt:message key="page.address.suburb"/>:</strong></td>
			<td><html:text property="searchSuburb"/>&nbsp;<fmt:message key="page.address.instruction11"/></td>
		</tr>		
	</sakai:group_table>	
	<logic:notEmpty name="postalList">
			<hr/>
			<sakai:instruction><fmt:message key="page.search.type1"/></sakai:instruction>			
			<br/><strong><fmt:message key="page.search.select"/></strong>&nbsp;
			<html:select property="searchResult">
				<html:options collection="postalList" property="value" labelProperty="label"/>
			</html:select>	
			<br/><br/>
	</logic:notEmpty>
	<sakai:actions>
		<logic:notEmpty name="postalList">
		    <html:hidden property="goto" value="select"/>
		</logic:notEmpty>
		<logic:empty name="postalList">
				<html:hidden property="goto" value="search"/>
		</logic:empty>  
		<html:submit property="action"><fmt:message key="button.continue"/></html:submit>
		<logic:notEmpty name="postalList">
			<html:submit property="action">
				<fmt:message key="button.searchagain" />
			</html:submit> 
		</logic:notEmpty>
		<html:submit property="action"><fmt:message key="button.back"/></html:submit> 
		<html:submit property="action"><fmt:message key="button.advisor"/></html:submit>
		<html:submit property="action"><fmt:message key="button.cancel"/></html:submit>
	</sakai:actions>
	
	</html:form>
</sakai:html>