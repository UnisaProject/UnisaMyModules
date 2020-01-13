<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page import="java.util.*" %>
<fmt:setBundle basename="za.ac.unisa.lms.tools.graduationceremony.ApplicationResources"/>

<sakai:html>
<!-- Form -->
	<html:form action="/graduationceremony">
	
		<sakai:messages/>
		<sakai:messages message="true"/>		
		<sakai:heading><fmt:message key="ceremstu.heading"/></sakai:heading>

							
		<sakai:group_table>
		<tr></tr>
		<tr></tr>
		<tr></tr>
		<tr>
			<td><fmt:message key="ceremstu.apply.studnumber"/></td>
			<td><bean:write name="graduationCeremonyForm" property="student.studentnumber" /></td>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<td><fmt:message key="ceremstu.request.venue"/></td>
			<td><bean:write name="graduationCeremonyForm" property="ceremony.venue" />, <bean:write name="graduationCeremonyForm" property="ceremony.venueline1" />, <bean:write name="graduationCeremonyForm" property="ceremony.venueline2" /></td>
			<td>&nbsp;</td>
		</tr>	
		<tr>
			<td><fmt:message key="ceremstu.request.ceremdate"/></td>
			<td><bean:write name="graduationCeremonyForm" property="ceremony.ceremonydate" /> at <bean:write name="graduationCeremonyForm" property="ceremony.ceremonytime" /></td>
			<td>&nbsp;</td>
		</tr>	
		<tr>
			<td colspan="3"><fmt:message key="page.longline"/></td>
		</tr>	
		<tr>
			<td><fmt:message key="ceremstu.request.surname"/></td>
			<td><bean:write name="graduationCeremonyForm" property="student.surname" /></td>
			<td>&nbsp;</td>
		</tr>	
		<tr>
			<td><fmt:message key="ceremstu.request.name"/></td>
			<td><bean:write name="graduationCeremonyForm" property="student.firstnames" /></td>
			<td>&nbsp;</td>
		</tr>	
		<tr>
			<td><fmt:message key="ceremstu.request.qualification"/></td>
			<td><bean:write name="graduationCeremonyForm" property="ceremony.qualdescription" /></td>
			<td>&nbsp;</td>
		</tr>	
		<tr>
			<td><fmt:message key="ceremstu.request.home"/></td>
			<td><bean:write name="graduationCeremonyForm" property="address.homenumber" /></td>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<td><fmt:message key="ceremstu.request.work"/></td>
			<td><bean:write name="graduationCeremonyForm" property="address.worknumber" /></td>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<td><fmt:message key="ceremstu.request.cell"/></td>
			<td><bean:write name="graduationCeremonyForm" property="address.cellnumber" /></td>
			<td>&nbsp;</td>
		</tr>
		</sakai:group_table>
		
		<sakai:actions>	
		<logic:equal name="graduationCeremonyForm" property="pagedisplay" value="U">
			<html:submit property="action"><fmt:message key="button.frwcontinue"/></html:submit>
			<html:submit property="action"><fmt:message key="button.back"/></html:submit>
		</logic:equal>
		<logic:equal name="graduationCeremonyForm" property="pagedisplay" value="X">
			<html:submit property="action"><fmt:message key="button.frwcontinue"/></html:submit>
			<html:submit property="action"><fmt:message key="button.dressorder"/></html:submit>
			<html:submit property="action"><fmt:message key="button.back"/></html:submit>
		</logic:equal>
		<logic:equal name="graduationCeremonyForm" property="pagedisplay" value="D">
			<html:submit property="action"><fmt:message key="button.back"/></html:submit>
		</logic:equal>
		</sakai:actions>
	</html:form>
	
</sakai:html>