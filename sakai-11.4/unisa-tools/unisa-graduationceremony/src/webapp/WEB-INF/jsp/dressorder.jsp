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
		<sakai:heading><fmt:message key="ceremstu.dress.top"/></sakai:heading>
		
		<sakai:instruction>
		<fmt:message key="ceremstu.dress.heading"/><br/>	
		</sakai:instruction>
							
		<sakai:group_table>
		<tr>
			<td><fmt:message key="ceremstu.apply.studnumber"/></td>
			<td><bean:write name="graduationCeremonyForm" property="student.studentnumber" /></td>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<td><fmt:message key="ceremstu.dress.name"/></td>
			<td><bean:write name="graduationCeremonyForm" property="displayname" /></td>
			<td>&nbsp;</td>
		</tr>	
		<tr>
			<td><fmt:message key="ceremstu.dress.address"/></td>
			<td><bean:write  name="graduationCeremonyForm" property="addrchg.addressline1" /></td>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<td>&nbsp;</td>
			<td><bean:write  name="graduationCeremonyForm" property="addrchg.addressline2" /></td>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<td>&nbsp;</td>
			<td><bean:write  name="graduationCeremonyForm" property="addrchg.addressline3" /></td>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<td>&nbsp;</td>
			<td><bean:write  name="graduationCeremonyForm" property="addrchg.addressline4" /></td>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<td>&nbsp;</td>
			<td><bean:write  name="graduationCeremonyForm" property="addrchg.addressline5" /></td>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<td>&nbsp;</td>
			<td><bean:write  name="graduationCeremonyForm" property="addrchg.addressline6" /></td>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<td>&nbsp;</td>
			<td><bean:write  name="graduationCeremonyForm" property="addrchg.postcode" /></td>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<td><fmt:message key="ceremstu.request.home"/></td>
			<td><bean:write  name="graduationCeremonyForm" property="addrchg.homenumber" /></td>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<td><fmt:message key="ceremstu.request.work"/></td>
			<td><bean:write  name="graduationCeremonyForm" property="addrchg.worknumber" /></td>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<td><fmt:message key="ceremstu.request.cell"/></td>
			<td><bean:write  name="graduationCeremonyForm" property="addrchg.cellnumber" /></td>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<td><fmt:message key="ceremstu.request.ceremdate"/></td>
			<td colspan="2"><bean:write name="graduationCeremonyForm" property="ceremony.ceremonydate" /> at <bean:write name="graduationCeremonyForm" property="ceremony.venue" />
		</tr>	
		<tr>
			<td colspan="3"><fmt:message key="page.longline"/></td>
		</tr>	

		</sakai:group_table>
		<sakai:instruction>
		<fmt:message key="ceremstu.dress.instruc"/><br/>	
		</sakai:instruction>

		<sakai:group_table>
		<tr>
			<td><fmt:message key="ceremstu.dress.hire"/></td>
			<td colspan="2"><html:radio property="ceremony.purchase" value="P"/><fmt:message key="dress.purchase"/></td>
		</tr>
		<tr>
			<td>&nbsp;</td>
			<td colspan="2"><html:radio property="ceremony.purchase" value="H"/><fmt:message key="dress.hire"/></td>
		</tr>
		<tr>
			<td><fmt:message key="ceremstu.dress.height"/></td>
			<td colspan="2"><html:text name="graduationCeremonyForm" property="ceremony.height" maxlength="4" size="4"/></td>
		</tr>	
		<tr>
			<td><fmt:message key="ceremstu.dress.circumference"/></td>
			<td colspan="2"><html:text name="graduationCeremonyForm" property="ceremony.capsize" maxlength="4" size="4"/> (if a cap is required)</td>
		</tr>	
		<tr>
			<td><fmt:message key="ceremstu.accept.name"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<html:text name="graduationCeremonyForm" property="confirmname" size="30"/></td>
		</tr>
		<tr>
			<td><fmt:message key="ceremstu.accept.date"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<html:text name="graduationCeremonyForm" property="confirmdate" size="14"/>&nbsp;yyyy-mm-dd</td>
		</tr>
		
		</sakai:group_table>
		
		<sakai:actions>	
			<html:submit property="action"><fmt:message key="button.sendsupplier"/></html:submit>
			<html:submit property="action"><fmt:message key="button.back"/></html:submit>
		</sakai:actions>
	</html:form>
	
</sakai:html>