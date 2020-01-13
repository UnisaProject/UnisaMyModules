
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setBundle basename="za.ac.unisa.lms.tools.biodetails.ApplicationResources"/>

<head>
	<style>
		.tdLabel {
		    width: 300px;
		    overflow: hidden;
		    text-overflow: ellipsis;
		}
	</style>

</head>

<sakai:html>
	
<!-- Toolbar -->
	<sakai:tool_bar>
		<html:link href="addressDetails.do?action=displayStep1">
			<fmt:message key="link.address"/>
		</html:link>
		<html:link href="contactDetailsAction.do?action=display">
			<fmt:message key="link.contact"/>
		</html:link>
		<html:link href="examcentreaction.do?action=display">
			<fmt:message key="link.exam"/>
		</html:link>
		<html:link href="emailoptionsaction.do?action=display">
			<fmt:message key="link.email"/>
		</html:link>
	</sakai:tool_bar>
	
<!-- Form -->
	<html:form action="/displayBioDetails">
		<sakai:messages/>
		<sakai:messages message="true"/>
		<html:hidden property="action" value="nextStep"/>
		<sakai:heading><fmt:message key="page.heading"/></sakai:heading>	
		
<!-- Personal information-->
		<sakai:group_heading><fmt:message key="page.sub.heading.info"/></sakai:group_heading>
		<sakai:group_table>
		<tr>
			<td class="tdLabel"><fmt:message key="page.title"/>&nbsp;</td>
			<td><bean:write name="bioDetailsForm" property="title"/>&nbsp;</td>
		</tr><tr>
			<td class="tdLabel"><fmt:message key="page.initials"/>&nbsp;</td>
			<td><bean:write name="bioDetailsForm" property="initials"/>&nbsp;</td>
		</tr><tr>
			<td class="tdLabel"><fmt:message key="page.firstNames"/>&nbsp;</td>
			<td><bean:write name="bioDetailsForm" property="firstNames"/>&nbsp;</td>
		</tr><tr>
			<td class="tdLabel"><fmt:message key="page.surname"/>&nbsp;</td>
			<td><bean:write name="bioDetailsForm" property="surname"/>&nbsp;</td>
		</tr>
		<logic:notEmpty name="bioDetailsForm" property="prevSurname">
			<tr>
				<td class="tdLabel"><fmt:message key="page.prevSurname"/>&nbsp;</td>
				<td><bean:write name="bioDetailsForm" property="prevSurname"/>&nbsp;</td>
			</tr>
		</logic:notEmpty>
		<tr>
			<td class="tdLabel"><fmt:message key="page.gender"/>&nbsp;</td>
			<td><bean:write name="bioDetailsForm" property="gender"/>&nbsp;</td>
		</tr><tr>
			<td class="tdLabel"><fmt:message key="page.birthDate"/>&nbsp;</td>
			<td><bean:write name="bioDetailsForm" property="birthDate"/>&nbsp;</td>
		</tr><tr>
			<td class="tdLabel"><fmt:message key="page.id"/>&nbsp;</td>
			<td><bean:write name="bioDetailsForm" property="identityNr"/>&nbsp;</td>
		</tr><tr>
			<td class="tdLabel"><fmt:message key="page.nationality"/>&nbsp;</td>
			<td><bean:write name="bioDetailsForm" property="nationality"/>&nbsp;</td>
		</tr><tr>
			<td class="tdLabel"><fmt:message key="page.ethnic"/>&nbsp;</td>
			<td><bean:write name="bioDetailsForm" property="ethnicGroup"/>&nbsp;</td>
		</tr><tr>
			<td class="tdLabel"><fmt:message key="page.homeLang"/>&nbsp;</td>
			<td><bean:write name="bioDetailsForm" property="homeLang"/>&nbsp;</td>
		</tr><tr>
			<td class="tdLabel"><fmt:message key="page.disability"/>&nbsp;</td>
			<td><bean:write name="bioDetailsForm" property="disability"/>&nbsp;</td>
		</tr><tr>
			<td class="tdLabel"><fmt:message key="page.regRegion"/>&nbsp;</td>
			<td><bean:write name="bioDetailsForm" property="regRegion"/>&nbsp;</td>
		</tr>
		</sakai:group_table>
		
<!-- Address Details -->
		<sakai:group_heading><fmt:message key="page.sub.heading.adress"/></sakai:group_heading>
		<sakai:group_table>
			<logic:notEmpty name="bioDetailsForm" property="postal">
				<tr>
					<td class="tdLabel"><fmt:message key="page.postal"/>&nbsp;</td>
					<td>
						<logic:iterate id="record" name="bioDetailsForm" property="postal" indexId = "i">
							<logic:equal name="i" value="0">
								<bean:write name="record"/><br/>
							</logic:equal>
							<logic:greaterThan name="i" value="0">
								<bean:write name="record"/><br/>
							</logic:greaterThan>
						</logic:iterate> 
					</td>
				</tr><tr>
					<logic:notEmpty name="bioDetailsForm" property="postalSuburb">
						<td class="tdLabel"><fmt:message key="page.suburb"/>&nbsp;</td>
						<td><bean:write name="bioDetailsForm" property="postalSuburb"/>&nbsp;</td>
					</logic:notEmpty>
					<logic:empty name="bioDetailsForm" property="postalSuburb">
						<logic:notEmpty name="bioDetailsForm" property="postalStreetSuburb">
							<td class="tdLabel"><fmt:message key="page.suburb"/>&nbsp;</td>
							<td><bean:write name="bioDetailsForm" property="postalStreetSuburb"/>&nbsp;</td>
						</logic:notEmpty>
					</logic:empty>
				</tr><tr>
						<logic:notEmpty name="bioDetailsForm" property="postalCode">
							<logic:notEqual name="bioDetailsForm" property="postalCode" value="0000">
								<td class="tdLabel"><fmt:message key="page.postalCode"/>&nbsp;</td>
								<td><bean:write name="bioDetailsForm" property="postalCode"/>&nbsp;</td>
							</logic:notEqual>
						</logic:notEmpty>
						<logic:empty name="bioDetailsForm" property="postalCode">
							<logic:notEqual name="bioDetailsForm" property="postalStreetCode" value="0">
								<td class="tdLabel"><fmt:message key="page.postalCode"/>&nbsp;</td>
								<td><bean:write name="bioDetailsForm" property="postalStreetCode"/>&nbsp;</td>
							</logic:notEqual>
						</logic:empty>
				</tr>
			</logic:notEmpty>
			<logic:notEmpty name="bioDetailsForm" property="physical">
				<tr>
					<td class="tdLabel"><fmt:message key="page.physical"/>&nbsp;</td>
					<td>
						<sakai:group_table>
							<logic:iterate id="record" name="bioDetailsForm" property="physical" indexId = "i">
								<logic:equal name="i" value="0">
									<bean:write name="record"/><br/>
								</logic:equal>
								<logic:greaterThan name="i" value="0">
									<bean:write name="record"/><br/>
								</logic:greaterThan>
							</logic:iterate> 
						</sakai:group_table>
					</td>
				</tr>
				<logic:notEmpty name="bioDetailsForm" property="physicalSuburb">
					<tr>
						<td class="tdLabel"><fmt:message key="page.suburb"/>&nbsp;</td>
						<td><bean:write name="bioDetailsForm" property="physicalSuburb"/>&nbsp;</td>
					</tr>
				</logic:notEmpty>
				<logic:notEmpty name="bioDetailsForm" property="physicalTown">
					<tr>
						<td class="tdLabel"><fmt:message key="page.town"/>&nbsp;</td>
						<td><bean:write name="bioDetailsForm" property="physicalTown"/>&nbsp;</td>
					</tr>
				</logic:notEmpty>
				<tr>
					<logic:notEqual name="bioDetailsForm" property="physicalPostalCode" value="0">
						<td class="tdLabel"><fmt:message key="page.postalCode"/>&nbsp;</td>
						<td><bean:write name="bioDetailsForm" property="physicalPostalCode"/>&nbsp;</td>
					</logic:notEqual>
				</tr>
			</logic:notEmpty>
			<logic:notEmpty name="bioDetailsForm" property="physical">
				<tr>
					<td class="tdLabel"><fmt:message key="page.courier"/>&nbsp;</td>
					<td>
						<sakai:group_table>
							<logic:iterate id="record" name="bioDetailsForm" property="courier" indexId = "i">
								<logic:equal name="i" value="0">
									<bean:write name="record"/><br/>
								</logic:equal>
								<logic:greaterThan name="i" value="0">
									<bean:write name="record"/><br/>
								</logic:greaterThan>
							</logic:iterate> 
						</sakai:group_table>
					</td>
				</tr>
				<logic:notEmpty name="bioDetailsForm" property="courierSuburb">
					<tr>
						<td class="tdLabel"><fmt:message key="page.suburb"/>&nbsp;</td>
						<td><bean:write name="bioDetailsForm" property="courierSuburb"/>&nbsp;</td>
					</tr>
				</logic:notEmpty>
				<logic:notEmpty name="bioDetailsForm" property="courierTown">
					<tr>
						<td class="tdLabel"><fmt:message key="page.town"/>&nbsp;</td>
						<td><bean:write name="bioDetailsForm" property="courierTown"/>&nbsp;</td>
					</tr>
				</logic:notEmpty>
				<tr>
					<logic:notEqual name="bioDetailsForm" property="courierPostalCode" value="0">
						<td class="tdLabel"><fmt:message key="page.postalCode"/>&nbsp;</td>
						<td><bean:write name="bioDetailsForm" property="courierPostalCode"/>&nbsp;</td>
					</logic:notEqual>
				</tr><tr>
					<td class="tdLabel"><fmt:message key="page.contact"/>&nbsp;</td>
					<td><bean:write name="bioDetailsForm" property="contactNr"/>&nbsp;</td>
				</tr>
			</logic:notEmpty>
		</sakai:group_table>

<!-- Contact details-->
		<sakai:group_heading><fmt:message key="page.sub.heading.contact"/></sakai:group_heading>
		<sakai:group_table>
		<tr>
			<td class="tdLabel"><fmt:message key="page.home"/>&nbsp;</td>
			<td><bean:write name="bioDetailsForm" property="homeNr"/>&nbsp;</td>
		</tr><tr>
			<td class="tdLabel"><fmt:message key="page.work"/>&nbsp;</td>
			<td><bean:write name="bioDetailsForm" property="workNr"/>&nbsp;</td>
		</tr><tr>
			<td class="tdLabel"><fmt:message key="page.cell"/>&nbsp;</td>
			<td><bean:write name="bioDetailsForm" property="cellNr"/>&nbsp;</td>
		</tr><tr>
			<td class="tdLabel"><fmt:message key="page.fax"/>&nbsp;</td>
			<td><bean:write name="bioDetailsForm" property="faxNr"/>&nbsp;</td>
		</tr><tr>
			<td class="tdLabel"><fmt:message key="page.email"/>&nbsp;</td>
			<td><bean:write name="bioDetailsForm" property="email"/>&nbsp;</td>
		</tr>
		<logic:equal name="bioDetailsForm" property="emailUpdatableFromWeb" value="true">
		<tr>
			<td class="tdLabel"><fmt:message key="page.email.status"/>&nbsp;</td>
			<logic:equal name="bioDetailsForm" property="emailStatus" value="Y">
				<td><fmt:message key="page.email.verified"/></td>
			</logic:equal>
			<logic:equal name="bioDetailsForm" property="emailStatus" value="N">
				<td><fmt:message key="page.email.notverified"/></td>
			</logic:equal>
		</tr>
		</logic:equal>
		</sakai:group_table>	
		
<!-- Exam centre-->
		<sakai:group_heading><fmt:message key="page.sub.heading.exam"/></sakai:group_heading>
		<sakai:group_table>
		<tr>
			<td class="tdLabel"><fmt:message key="page.exam"/>&nbsp;</td>
			<td>(<bean:write name="bioDetailsForm" property="examCentreCode"/>) <bean:write name="bioDetailsForm" property="examCentre"/>&nbsp;</td>
		</tr>		
		</sakai:group_table>
<!-- Correspondence options-->
		<sakai:group_heading><fmt:message key="page.sub.heading.correspondance"/></sakai:group_heading>		
		<sakai:group_table>
<!-- Comment out by Johannes Ngoepe 04/05/2006 -->		
<!--		<tr>
			<td><fmt:message key="page.reg"/>&nbsp;</td>
			<logic:equal  name="bioDetailsForm" property="regOption" value="Y">
				<td><fmt:message key="page.form.yes"/></td>
			</logic:equal>
			<logic:equal  name="bioDetailsForm" property="regOption" value="N">
				<td><fmt:message key="page.form.no"/></td>
			</logic:equal>									
		</tr>
-->
		<tr>
			<td class="tdLabel"><fmt:message key="page.assignments"/>&nbsp;</td>
			<logic:equal  name="bioDetailsForm" property="assignOption" value="Y">
				<td><fmt:message key="page.form.yes"/></td>
			</logic:equal>
			<logic:equal  name="bioDetailsForm" property="assignOption" value="N">
				<td><fmt:message key="page.form.no"/></td>
			</logic:equal>			
		</tr>
		<tr>
			<td class="tdLabel"><fmt:message key="page.exam.block"/>&nbsp;</td>
			<logic:equal  name="bioDetailsForm" property="blockExamResults" value="Y">
				<td><fmt:message key="page.form.yes"/></td>
			</logic:equal>
			<logic:equal  name="bioDetailsForm" property="blockExamResults" value="N">
				<td><fmt:message key="page.form.no"/></td>
			</logic:equal>			
		</tr>
		</sakai:group_table>	
		<sakai:actions>
	    <logic:equal name="bioDetailsForm" property="originatedFrom" value="unisa.regdetails">
			<html:submit property="action" >
			<fmt:message key="button.regdetails" />
			</html:submit>
		</logic:equal>
		</sakai:actions>
		
		<!-- Added by Edmund Wilschewski 03/06/2015 -->			
		<div style="display: none;"><br><bean:write name="bioDetailsForm" property="version"/></div>
		<br/>
	</html:form>
</sakai:html>