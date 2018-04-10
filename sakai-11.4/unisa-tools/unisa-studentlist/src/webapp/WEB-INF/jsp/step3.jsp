<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>


<fmt:setBundle basename="za.ac.unisa.lms.tools.studentlist.ApplicationResources"/>

<sakai:html>
	<sakai:messages/>
		<sakai:messages message="true"/>	
	<sakai:heading><fmt:message key="page.general.heading"/></sakai:heading>
	<sakai:instruction>
		<fmt:message key="page.step3.instruction"/>
	</sakai:instruction>
		
	<sakai:group_heading><fmt:message key="page.step3.groupheading"/></sakai:group_heading>
	
	<html:form action="studentlistaction.do?action=step4">
		<sakai:group_table>
			<tr>
				<td><html:checkbox property="studentNumber"/></td>
				<td><fmt:message key="page.step3.details.studentnumber"/></td>
				<td><html:checkbox property="emailAddress"/></td>
				<td><fmt:message key="page.step3.details.emailaddress"/></td>
				<!--td><html:checkbox property="homePhoneNumber"/></td>
				<td><fmt:message key="page.step3.details.homephone"/></td-->
			</tr>
			<tr>
				<td><html:checkbox property="title"/></td>
				<td><fmt:message key="page.step3.details.title"/></td>
				 <td><html:checkbox property="gender"/></td>
				<td><fmt:message key="page.step3.details.gender"/></td>
				<!--td><html:checkbox property="workPhoneNumber"/></td>
				<td><fmt:message key="page.step3.details.workphone"/></td-->
			</tr>
			<tr>
				<td><html:checkbox property="initials"/></td>
				<td><fmt:message key="page.step3.details.initials"/></td>
				<td><html:checkbox property="disabilityType"/></td>
				<td><fmt:message key="page.step3.details.disabilitytype"/></td>	
				<!--td><html:checkbox property="cellularNumber"/></td>
				<td><fmt:message key="page.step3.details.cellularnumber"/></td-->
			</tr>
			<tr>
				<td><html:checkbox property="firstNames"/></td>
				<td><fmt:message key="page.step3.details.firstnames"/></td>
				<td><html:checkbox property="correspondenceLanguage"/></td>
				<td><fmt:message key="page.step3.details.correspondencelanguage"/></td>
				<!--td><html:checkbox property="faxNumber"/></td>
				<td><fmt:message key="page.step3.details.faxnumber"/></td-->
			</tr>
			<tr>
				<td><html:checkbox property="lastName"/></td>
				<td><fmt:message key="page.step3.details.lastname"/></td>
				<td><html:checkbox property="homeLanguage"/></td>
				<td><fmt:message key="page.step3.details.homelanguage"/></td>
			</tr>
			<tr>
				<td><html:checkbox property="postalAddress"/></td>
				<td><fmt:message key="page.step3.details.postaladdress"/></td>
				<td><html:checkbox property="registrationStatus"/></td>
				<td><fmt:message key="page.step3.details.registrationstatus"/></td>	
			</tr>
			<tr>
				<td><html:checkbox property="postalCode"/></td>
				<td><fmt:message key="page.step3.details.postalcode"/></td>
			    <logic:equal name="studentlistform"  property = "groupOption" value="groupOption">
				<td><html:checkbox property="moduleRegDate"/></td>
				<td><fmt:message key="page.step3.details.moduleRegDate"/></td>	
			</logic:equal>
			</tr>		
			<tr>
				<td><html:checkbox property="region"/></td>
				<td><fmt:message key="page.step3.details.region"/></td>
			    <logic:equal name="studentlistform"  property = "groupOption" value="groupOption">
				<td><html:checkbox property="tutGroupNr"/></td>
				<td><fmt:message key="page.step3.details.tutGroupNr"/></td>
			</logic:equal>
			</tr>			
			<tr>
				<td><html:checkbox property="resRegion"/></td>
				<td><fmt:message key="page.step3.details.resregion"/></td>	
			</tr>
			<tr>
				<sakai:actions>
					<td colspan="3">
						<html:submit value="Continue" property="button"/>
						<html:submit value="Back" property="button"/>
						<html:cancel value="Cancel" titleKey="button.cancel"/>
					</td>
				</sakai:actions>
			</tr>
		</sakai:group_table>
	</html:form>
</sakai:html>