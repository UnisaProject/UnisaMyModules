<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>

<fmt:setBundle basename="za.ac.unisa.lms.tools.tpustudentplacement.ApplicationResources"/>

<sakai:html>	
	<html:form action="/studentPlacement">
		<sakai:messages/>
		<sakai:messages message="true"/>			
		<sakai:heading>
			<fmt:message key="heading.studentPlacement.studentContactDetails"/> 
		</sakai:heading>
		<sakai:group_table>					
			<tr>
				<td><fmt:message key="prompt.studentName"/></td>
				<td><bean:write name="studentPlacementForm" property="student.name"/></td>
			</tr>	
			<logic:notEmpty name="studentPlacementForm" property="student.contactInfo.cellNumber">
				<tr>
					<td><fmt:message key="prompt.cellNumber"/></td>
					<td><bean:write name="studentPlacementForm" property="student.contactInfo.cellNumber"/></td>
				</tr>			
			</logic:notEmpty>
			<logic:notEmpty name="studentPlacementForm" property="student.contactInfo.homeNumber">
				<tr>
					<td><fmt:message key="prompt.homeNumber"/></td>
					<td><bean:write name="studentPlacementForm" property="student.contactInfo.homeNumber"/></td>
				</tr>			
			</logic:notEmpty>	
			<logic:notEmpty name="studentPlacementForm" property="student.contactInfo.workNumber">
				<tr>
					<td><fmt:message key="prompt.workNumber"/></td>
					<td><bean:write name="studentPlacementForm" property="student.contactInfo.workNumber"/></td>
				</tr>			
			</logic:notEmpty>	
			<logic:notEmpty name="studentPlacementForm" property="student.contactInfo.faxNumber">
				<tr>
					<td><fmt:message key="prompt.faxNumber"/></td>
					<td><bean:write name="studentPlacementForm" property="student.contactInfo.faxNumber"/></td>
				</tr>			
			</logic:notEmpty>	
			<logic:notEmpty name="studentPlacementForm" property="student.contactInfo.emailAddress">
				<tr>
					<td><fmt:message key="prompt.emailAddress"/></td>
					<td><bean:write name="studentPlacementForm" property="student.contactInfo.emailAddress"/></td>
				</tr>			
			</logic:notEmpty>	
		</sakai:group_table>
		<logic:notEmpty name="studentPlacementForm" property="student.postalAddress.line1">
			<sakai:group_heading>
				<fmt:message key="prompt.postalAddress"/>
			</sakai:group_heading>		
			<sakai:group_table>
				<logic:notEmpty name="studentPlacementForm" property="student.postalAddress.line1">
					<tr>
						<td><bean:write name="studentPlacementForm" property="student.postalAddress.line1"/></td>			
					</tr>			
				</logic:notEmpty>
				<logic:notEmpty name="studentPlacementForm" property="student.postalAddress.line2">
					<tr>
						<td><bean:write name="studentPlacementForm" property="student.postalAddress.line2"/></td>			
					</tr>			
				</logic:notEmpty>
				<logic:notEmpty name="studentPlacementForm" property="student.postalAddress.line3">
					<tr>
						<td><bean:write name="studentPlacementForm" property="student.postalAddress.line3"/></td>			
					</tr>			
				</logic:notEmpty>
				<logic:notEmpty name="studentPlacementForm" property="student.postalAddress.line4">
					<tr>
						<td><bean:write name="studentPlacementForm" property="student.postalAddress.line4"/></td>			
					</tr>			
				</logic:notEmpty>
				<logic:notEmpty name="studentPlacementForm" property="student.postalAddress.line5">
					<tr>
						<td><bean:write name="studentPlacementForm" property="student.postalAddress.line5"/></td>			
					</tr>			
				</logic:notEmpty>
				<logic:notEmpty name="studentPlacementForm" property="student.postalAddress.line6">
					<tr>
						<td><bean:write name="studentPlacementForm" property="student.postalAddress.line6"/></td>			
					</tr>			
				</logic:notEmpty>
				<logic:notEmpty name="studentPlacementForm" property="student.postalAddress.postalCode">
					<tr>
						<td><bean:write name="studentPlacementForm" property="student.postalAddress.postalCode"/></td>			
					</tr>			
				</logic:notEmpty>
			</sakai:group_table>		
		</logic:notEmpty>
		<logic:notEmpty name="studentPlacementForm" property="student.physicalAddress.line1">
			<sakai:group_heading>
				<fmt:message key="prompt.physicalAddress"/>
			</sakai:group_heading>
			<sakai:group_table>			
				<logic:notEmpty name="studentPlacementForm" property="student.physicalAddress.line1">
					<tr>
						<td><bean:write name="studentPlacementForm" property="student.physicalAddress.line1"/></td>
					</tr>			
				</logic:notEmpty>	
				<logic:notEmpty name="studentPlacementForm" property="student.physicalAddress.line2">
					<tr>
						<td><bean:write name="studentPlacementForm" property="student.physicalAddress.line2"/></td>
					</tr>			
				</logic:notEmpty>	
				<logic:notEmpty name="studentPlacementForm" property="student.physicalAddress.line3">
					<tr>
						<td><bean:write name="studentPlacementForm" property="student.physicalAddress.line3"/></td>
					</tr>			
				</logic:notEmpty>	
				<logic:notEmpty name="studentPlacementForm" property="student.physicalAddress.line4">
					<tr>
						<td><bean:write name="studentPlacementForm" property="student.physicalAddress.line4"/></td>
					</tr>			
				</logic:notEmpty>	
				<logic:notEmpty name="studentPlacementForm" property="student.physicalAddress.line5">
					<tr>
						<td><bean:write name="studentPlacementForm" property="student.physicalAddress.line5"/></td>
					</tr>			
				</logic:notEmpty>	
				<logic:notEmpty name="studentPlacementForm" property="student.physicalAddress.line6">
					<tr>
						<td><bean:write name="studentPlacementForm" property="student.physicalAddress.line6"/></td>
					</tr>			
				</logic:notEmpty>	
				<logic:notEmpty name="studentPlacementForm" property="student.physicalAddress.postalCode">
					<tr>
						<td><bean:write name="studentPlacementForm" property="student.physicalAddress.postalCode"/></td>
					</tr>			
				</logic:notEmpty>
			</sakai:group_table>	
		</logic:notEmpty>
		<sakai:actions>		
			<html:submit property="action">
					<fmt:message key="button.back"/>
			</html:submit>			
		</sakai:actions>				
	</html:form>
</sakai:html>