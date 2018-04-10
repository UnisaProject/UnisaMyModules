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
		<sakai:heading><fmt:message key="ceremstu.request"/></sakai:heading>
		

		<sakai:group_table>
		<tr>
			<td><fmt:message key="ceremstu.apply.studnumber"/></td>
			<td><bean:write name="graduationCeremonyForm" property="student.studentnumber" /></td>
		</tr>
		<tr>
			<td><fmt:message key="ceremstu.request.surname"/></td>
			<td><bean:write name="graduationCeremonyForm" property="student.surname" /></td>
		</tr>
		<tr>
			<td><fmt:message key="ceremstu.request.name"/></td>
			<td><bean:write name="graduationCeremonyForm" property="student.firstnames" /></td>
		</tr>
		<tr>
			<td><fmt:message key="ceremstu.request.qualification"/></td>
			<td><bean:write name="graduationCeremonyForm" property="ceremony.qualdescription" /></td>
		</tr>
		<tr>
			<td><fmt:message key="ceremstu.request.ceremonynr"/></td>
			<td><bean:write name="graduationCeremonyForm" property="ceremony.ceremonynr" /></td>
		</tr>
		<tr>
			<td><fmt:message key="ceremstu.request.venue"/></td>
			<td><bean:write name="graduationCeremonyForm" property="ceremony.venue" />, <bean:write name="graduationCeremonyForm" property="ceremony.venueline1" />, <bean:write name="graduationCeremonyForm" property="ceremony.venueline2" /></td>	
		</tr>
		<tr>
			<td><fmt:message key="ceremstu.request.ceremdate"/></td>
			<td><bean:write name="graduationCeremonyForm" property="ceremony.ceremonydate" /> at <bean:write name="graduationCeremonyForm" property="ceremony.ceremonytime" /></td>
		</tr>
		<!-- <tr>
			<td><fmt:message key="ceremstu.request.language"/></td>
			<td><bean:write name="graduationCeremonyForm" property="student.corrlanguage" /></td>
		</tr> -->
		<tr>
			<td colspan="2"><fmt:message key="page.longline"/></td>
		</tr>	
		</sakai:group_table>

		<sakai:heading><fmt:message key="ceremstu.request.adr1"/></sakai:heading>
		<sakai:heading><fmt:message key="ceremstu.request.adr2"/></sakai:heading>
		<sakai:group_table>
		<tr>
			<td><fmt:message key="ceremstu.request.address1"/></td>
			<td><html:text name="graduationCeremonyForm" property="address.addressline1" size="35" disabled="true"/></td>
			<td><html:text name="graduationCeremonyForm" property="addrchg.addressline1" maxlength="28" size="35"/></td>
		</tr>
		<tr>
			<td>&nbsp;</td>
			<td><html:text name="graduationCeremonyForm" property="address.addressline2" size="35" disabled="true"/></td>
			<td><html:text name="graduationCeremonyForm" property="addrchg.addressline2" maxlength="28" size="35"/></td>
		</tr>
		<tr>
			<td>&nbsp;</td>
			<td><html:text name="graduationCeremonyForm" property="address.addressline3" size="35" disabled="true"/></td>
			<td><html:text name="graduationCeremonyForm" property="addrchg.addressline3" maxlength="28" size="35"/></td>
		</tr>
		<tr>
			<td>&nbsp;</td>
			<td><html:text name="graduationCeremonyForm" property="address.addressline4" size="35" disabled="true"/></td>
			<td><html:text name="graduationCeremonyForm" property="addrchg.addressline4" maxlength="28" size="35"/></td>
		</tr>
		<tr>
			<td>&nbsp;</td>
			<td><html:text name="graduationCeremonyForm" property="address.addressline5" size="35" disabled="true"/></td>
			<td><html:text name="graduationCeremonyForm" property="addrchg.addressline5" maxlength="28" size="35"/></td>
		</tr>
		<tr>
			<td>&nbsp;</td>
			<td><html:text name="graduationCeremonyForm" property="address.addressline6" size="35" disabled="true"/></td>
			<td><html:text name="graduationCeremonyForm" property="addrchg.addressline6" maxlength="28" size="35"/></td>
		</tr>
		<tr>
			<td><fmt:message key="ceremstu.request.address2"/></td>
			<td><html:text name="graduationCeremonyForm" property="address.postcode" disabled="true"/>Only if within South Africa</td>
			<td><html:text name="graduationCeremonyForm" property="addrchg.postcode" maxlength="4" size="6"/></td>
		</tr>
		<tr>
			<td><fmt:message key="ceremstu.request.work"/></td>
			<td><html:text name="graduationCeremonyForm" property="address.worknumber" size="35" disabled="true"/></td>
			<td><html:text name="graduationCeremonyForm" property="addrchg.worknumber" maxlength="28" size="35"/></td>
		</tr>
		<tr>
			<td><fmt:message key="ceremstu.request.home"/></td>
			<td><html:text name="graduationCeremonyForm" property="address.homenumber" size="35" disabled="true"/></td>
			<td><html:text name="graduationCeremonyForm" property="addrchg.homenumber" maxlength="28" size="35"/></td>
		</tr>
		<tr>
			<td colspan="3"><fmt:message key="page.longline"/></td>
		</tr>	
		</sakai:group_table>
		
		<sakai:group_table>
		<!-- <tr>
			<td><fmt:message key="ceremstu.request.clanguage"/></td>
			<td><html:radio property="ceremony.certificatelang" value="E"/><fmt:message key="page.eng"/></td>
		</tr>
		<tr>
			<td>&nbsp;</td>
			<td><html:radio property="ceremony.certificatelang" value="A"/><fmt:message key="page.afr"/></td>
		</tr> -->		
		<logic:equal name="graduationCeremonyForm" property="pagetype" value="P">
		<tr>
			<td><fmt:message key="ceremstu.request.presence"/></td>
			<td><html:radio property="ceremony.presentflag" value="Y"/><fmt:message key="page.present"/></td>
		</tr>
		<tr>
			<td>&nbsp;</td>
			<td><html:radio property="ceremony.presentflag" value="N"/><fmt:message key="page.absent"/>
		</tr>
		<tr>
			<td><fmt:message key="ceremstu.request.guests1"/></td>
			<td><html:text name="graduationCeremonyForm" property="ceremony.guests" maxlength="2" size="4"/></td>
		</tr>
		<!-- <tr>
			<td colspan="2"><fmt:message key="ceremstu.request.guests2"/></td>
		</tr> -->
		</logic:equal>
		<logic:equal name="graduationCeremonyForm" property="pagetype" value="A">
		<tr>
			<td><fmt:message key="ceremstu.request.shortdiploma"/></td>
			<td><html:radio property="ceremony.presentflag" value="Y" disabled="true"/><fmt:message key="page.present"/></td>
		</tr>
		<tr>
			<td>&nbsp;</td>
			<td><html:radio property="ceremony.presentflag" value="N"/><fmt:message key="page.absent"/>
		</tr>
		</logic:equal>
<!-- 	<tr>
			<td><fmt:message key="ceremstu.request.spelling1"/></td>
			<td><html:textarea name="graduationCeremonyForm" property="ceremony.punctuation" rows="5" cols="30"/></td>
		</tr>   -->
		<tr>
			<td colspan="2"><fmt:message key="ceremstu.request.spelling2"/></td>
		</tr>
		<!-- <tr>
			<td colspan="2"><fmt:message key="ceremstu.request.spelling3"/></td>
		</tr> -->
		<tr>
			<td colspan="2"><fmt:message key="page.longline"/></td>
		</tr>	
		</sakai:group_table>

		<sakai:heading><fmt:message key="ceremstu.accept.heading"/></sakai:heading>
		<sakai:group_table>
		<tr>
			<td><fmt:message key="ceremstu.accept.sub"/></td>
			<td></td>
		</tr>
		<tr>
			<td><fmt:message key="ceremstu.accept.name"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<html:text name="graduationCeremonyForm" property="confirmname" size="30"/></td>
		</tr>
		<tr>
			<td><fmt:message key="ceremstu.accept.date"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<html:text name="graduationCeremonyForm" property="confirmdate" size="14"/>&nbsp;yyyy-mm-dd</td>
		</tr>
		</sakai:group_table>
		
		<sakai:actions>	
			<html:submit property="action"><fmt:message key="button.update"/></html:submit>
			<html:submit property="action"><fmt:message key="button.back"/></html:submit>
			<fmt:message key="ceremstu.submit.note"/>
		</sakai:actions>
	</html:form>
	
</sakai:html>