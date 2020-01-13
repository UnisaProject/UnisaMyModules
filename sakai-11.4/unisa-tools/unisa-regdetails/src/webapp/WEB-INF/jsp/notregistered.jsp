
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setBundle basename="za.ac.unisa.lms.tools.regdetails.ApplicationResources"/>

<sakai:html>

		<sakai:heading><fmt:message key="page.heading"/></sakai:heading>		
		<table>	
		<tr>
			<td><fmt:message key="page.notregistered.message"/></td>
		</tr>
		<tr>
			<td>&nbsp;</td>
		</tr>	
		<tr>
			<td><fmt:message key="page.notregistered.message2"/><bean:write name="regLink" filter="false"/>
			</td>
		</tr>
		</table>
		
</sakai:html>