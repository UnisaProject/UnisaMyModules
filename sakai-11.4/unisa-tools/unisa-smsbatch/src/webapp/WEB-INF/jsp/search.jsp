<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>

<fmt:setBundle basename="za.ac.unisa.lms.tools.smsbatch.ApplicationResources"/>

<sakai:html>
	
<!-- Form -->
	<html:form action="/smsbatch">		
		
	<sakai:heading><fmt:message key="page.heading"/></sakai:heading>
		
	<table>
	<logic:equal name="smsBatchForm" property="regCriteriaType" value="M">
		<tr>
  			<td><i><fmt:message key="page.search.module.instruction1"/></i></td></tr>
    	<tr>
	</logic:equal>
	<logic:equal name="smsBatchForm" property="regCriteriaType" value="Q">
		<tr>
  			<td><i><fmt:message key="page.search.qual.instruction1"/></i></td></tr>
    	<tr>
	</logic:equal>
  		
    </table>
	<p><font color="red"><html:errors /></font></p>						
		
<!-- Search input-->
		<table>
		<tr>
  			<td><strong><fmt:message key="page.registration.period"/>&nbsp;&nbsp;<bean:write name="smsBatchForm" property="registrationPeriod"/></strong></td></tr>
    	<tr>
    	<tr><td>&nbsp;</td></tr>
		<tr>
			<td><html:radio property="searchType" value="code"/><strong><fmt:message key="page.search.code"/>:</strong></td>
			<td><html:text property="searchCode"/>&nbsp;</td>
		</tr>
		<tr>
			<td><strong>OR</strong></td>
			<td>&nbsp;</td>
		</tr>			
		<tr>
			<td><html:radio property="searchType" value="description"/><strong><fmt:message key="page.search.description"/>:</strong></td>
			<td><html:text property="searchDescription"/>&nbsp;</td>
		</tr>		
	</table>	
	<logic:notEmpty name="searchList">
			<hr/><br/><i>
			<fmt:message key="page.instruction3"/></i><br/><br/>
			<strong>
			<logic:equal name="smsBatchForm" property="regCriteriaType" value="M">
				<fmt:message key="page.search.select.module"/></strong>&nbsp;
			</logic:equal>
			<logic:equal name="smsBatchForm" property="regCriteriaType" value="Q">
				<fmt:message key="page.search.select.qual"/></strong>&nbsp;
			</logic:equal>
			<html:select property="selectedSearchList" multiple="true">
				<html:options collection="searchList" property="value" labelProperty="label"/>
			</html:select>	
			<br/><br/>
	</logic:notEmpty>
	
		<logic:notEmpty name="searchList">			
		    <html:hidden property="page" value="select"/>
		</logic:notEmpty>
		<logic:empty name="searchList">
				<html:hidden property="page" value="search"/>
		</logic:empty>  
		<br/>
		<logic:notEmpty name="searchList">
			<html:submit property="act"><fmt:message key="button.continue"/></html:submit>
		</logic:notEmpty>	
		<html:submit property="act"><fmt:message key="button.search"/></html:submit>
		<html:submit property="act"><fmt:message key="button.back"/></html:submit> 
		<html:submit property="act"><fmt:message key="button.cancel"/></html:submit>
	
	</html:form>
</sakai:html>