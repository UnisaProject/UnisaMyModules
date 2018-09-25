<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
 
<fmt:setBundle basename="za.ac.unisa.lms.tools.smsbatch.ApplicationResources"/>

<sakai:html> 
  <html:form action="/smsbatch">
  	<html:hidden property="page" value="1"/>
  	 <sakai:heading><fmt:message key="page.heading"/></sakai:heading>
  	
  	<table>
  		<tr>
  			<td><i><fmt:message key="page.instruction1"/></i></td></tr>
    	<tr>
    </table>
	<p><font color="red"><html:errors/></font></p>
  	<table>
  		<tr>
  			<td><strong><fmt:message key="page.registration.criteria"/></strong></td>
    	</tr><tr>
			<td><html:radio property="regCriteriaType" value="A"/><fmt:message key="page.all"/></td>
		</tr><tr>
			<td><html:radio property="regCriteriaType" value="Q"/><fmt:message key="page.qualification.code"/></td>
		</tr><tr>
			<td><html:radio property="regCriteriaType" value="M"/><fmt:message key="page.module.code"/></td>
		</tr><tr>
			<td>&nbsp;</td>
  		</tr><tr>
  			<td><strong><fmt:message key="page.geo.criteria"/></strong></td>
    	</tr><tr>
			<td><html:radio property="geoCriteriaType" value="A"/><fmt:message key="page.all"/></td>
		</tr><tr>
			<td><html:radio property="geoCriteriaType" value="S"/><fmt:message key="page.all.rsa"/></td>
		</tr><tr>
			<td><html:radio property="geoCriteriaType" value="C"/><fmt:message key="page.countries"/></td>
		</tr><tr>
			<td><html:radio property="geoCriteriaType" value="R"/><fmt:message key="page.regions"/></td>
		</tr><tr>
			<td><html:radio property="geoCriteriaType" value="M"/><fmt:message key="page.mag.districts"/></td>
		</tr>
  	</table> 
  	
  	<br/>
  	<html:submit property="act"><fmt:message key="button.continue"/></html:submit>	
	<html:submit property="act"><fmt:message key="button.cancel"/></html:submit>
  </html:form>
</sakai:html>
