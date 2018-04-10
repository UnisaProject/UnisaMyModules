<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c_rt" %>


<fmt:setBundle basename="za.ac.unisa.lms.tools.studentlist.ApplicationResources"/>

<sakai:html>
		<sakai:group_table>
			<tr><td colspan="3"><fmt:message key="page.generatelist.studentcount"/><c_rt:out value="${studentCount}"/></td></tr>
			<%-- <tr>
			<tr><td colspan="3"><fmt:message key="page.generatelist.allstudentcount"/><c_rt:out value="${studentlistform.registedStudents}"/></td></tr>  --%>
			<tr>
				<td><fmt:message key="page.generatelist.datelist"/><c_rt:out value="${listDate}"/></td>
			</tr>
			<tr>
				<td><fmt:message key="page.generatelist.courses"/>
					<c_rt:out value="${selectedCourseList}"/>
				</td>
			</tr>
		</sakai:group_table>
		<sakai:group_table>
			
			<tr>	
				<c_rt:if test="${studentlistform.studentNumber == 'true'}">
						<th><fmt:message key="page.generatelist.studentnumberheading"/></th>	
				</c_rt:if>
				
				<c_rt:if test="${studentlistform.title == 'true'}">
						<th><fmt:message key="page.generatelist.titleheading"/></th>	
				</c_rt:if>
				
				<c_rt:if test="${studentlistform.initials == 'true'}">
						<th><fmt:message key="page.generatelist.initialsheading"/></th>	
				</c_rt:if>
				
				
				
				<c_rt:if test="${studentlistform.firstNames == 'true'}">
						<th><fmt:message key="page.generatelist.firstnamesheading"/></th>	
				</c_rt:if>
				
				<c_rt:if test="${studentlistform.lastName == 'true'}">
						<th><fmt:message key="page.generatelist.lastnameheading"/></th>	
				</c_rt:if>
				
				<c_rt:if test="${studentlistform.homePhoneNumber == 'true'}">
						<th><fmt:message key="page.generatelist.homephoneheading"/></th>	
				</c_rt:if>
				
				<c_rt:if test="${studentlistform.workPhoneNumber == 'true'}">
						<th><fmt:message key="page.generatelist.workphoneheading"/></th>	
				</c_rt:if>
				
				<c_rt:if test="${studentlistform.cellularNumber == 'true'}">
						<th><fmt:message key="page.generatelist.cellularheading"/></th>	
				</c_rt:if>
				
				<c_rt:if test="${studentlistform.faxNumber == 'true'}">
						<th><fmt:message key="page.generatelist.faxheading"/></th>	
				</c_rt:if>
				
				<c_rt:if test="${studentlistform.postalAddress == 'true'}">
						<th><fmt:message key="page.generatelist.postaladdressheading"/></th>	
				</c_rt:if>
				
				<c_rt:if test="${studentlistform.postalCode == 'true'}">
						<th><fmt:message key="page.generatelist.postalcodeheading"/></th>	
				</c_rt:if>
				
				<c_rt:if test="${studentlistform.emailAddress == 'true'}">
						<th><fmt:message key="page.generatelist.emailaddressheading"/></th>	
				</c_rt:if>
				
				<c_rt:if test="${studentlistform.gender == 'true'}">
						<th><fmt:message key="page.generatelist.genderheading"/></th>	
				</c_rt:if>
				
				<c_rt:if test="${studentlistform.correspondenceLanguage == 'true'}">
						<th><fmt:message key="page.generatelist.correspondencelanguageheading"/></th>	
				</c_rt:if>
				<c_rt:if test="${studentlistform.homeLanguage == 'true'}">
						<th><fmt:message key="page.generatelist.homelanguageheading"/></th>	
				</c_rt:if>				
				<c_rt:if test="${studentlistform.disabilityType == 'true'}">
						<th><fmt:message key="page.generatelist.disabilityTypeHeading"/></th>	
				</c_rt:if>
				<c_rt:if test="${studentlistform.region == 'true'}">
						<th><fmt:message key="page.generatelist.regregionheading"/></th>	
				</c_rt:if>
				
				<c_rt:if test="${studentlistform.resRegion == 'true'}">
						<th><fmt:message key="page.generatelist.resregionheading"/></th>	
				</c_rt:if>
				
				<c_rt:if test="${studentlistform.registrationStatus == 'true'}">
						<th><fmt:message key="page.generatelist.registrationstatusheading"/></th>	
				</c_rt:if>
				<c_rt:if test="${studentlistform.moduleRegDate == 'true'}">
						<th><fmt:message key="page.generatelist.moduleregstatusheading"/></th>	
				</c_rt:if>
				<c_rt:if test="${studentlistform.tutGroupNr == 'true'}">
						<th><fmt:message key="page.generatelist.groupnumberheading"/></th>	
				</c_rt:if>
			</tr>
			<logic:iterate id="studentDetails" name="studentDetails">
				<tr>
					<c_rt:if test="${studentlistform.studentNumber == 'true'}">
						<td><bean:write name="studentDetails" property="studentNumber"/></td>
					</c_rt:if>
					<c_rt:if test="${studentlistform.title == 'true'}">
						<td><bean:write name="studentDetails" property="title"/></td>
					</c_rt:if>
					<c_rt:if test="${studentlistform.initials == 'true'}">
						<td><bean:write name="studentDetails" property="initials"/></td>
					</c_rt:if>
					
					<c_rt:if test="${studentlistform.firstNames == 'true'}">
						<td><bean:write name="studentDetails" property="firstNames"/></td>
					</c_rt:if>
					<c_rt:if test="${studentlistform.lastName == 'true'}">
						<td><bean:write name="studentDetails" property="lastName"/></td>
					</c_rt:if>
					<c_rt:if test="${studentlistform.homePhoneNumber == 'true'}">
						<td><bean:write name="studentDetails" property="homePhoneNumber"/></td>
					</c_rt:if>
					<c_rt:if test="${studentlistform.workPhoneNumber == 'true'}">
						<td><bean:write name="studentDetails" property="workPhoneNumber"/></td>
					</c_rt:if>
					<c_rt:if test="${studentlistform.cellularNumber == 'true'}">
						<td><bean:write name="studentDetails" property="cellularNumber"/></td>
					</c_rt:if>
					<c_rt:if test="${studentlistform.faxNumber == 'true'}">
						<td><bean:write name="studentDetails" property="faxNumber"/></td>
					</c_rt:if>
					<c_rt:if test="${studentlistform.postalAddress == 'true'}">
						<td><bean:write name="studentDetails" property="postalAddress"/></td>
					</c_rt:if>
					<c_rt:if test="${studentlistform.postalCode == 'true'}">
						<td><bean:write name="studentDetails" property="postalCode"/></td>
					</c_rt:if>
					<c_rt:if test="${studentlistform.emailAddress == 'true'}">
						<td><bean:write name="studentDetails" property="emailAddress"/></td>
					</c_rt:if>
					<c_rt:if test="${studentlistform.gender == 'true'}">
						<td><bean:write name="studentDetails" property="gender"/></td>
					</c_rt:if>
					<c_rt:if test="${studentlistform.correspondenceLanguage == 'true'}">
						<td><bean:write name="studentDetails" property="correspondenceLanguage"/></td>
					</c_rt:if>
					<c_rt:if test="${studentlistform.homeLanguage == 'true'}">
						<td><bean:write name="studentDetails" property="homeLanguage"/></td>
					</c_rt:if>				
					<c_rt:if test="${studentlistform.region == 'true'}">
						<td><bean:write name="studentDetails" property="region"/></td>
					</c_rt:if>
					<c_rt:if test="${studentlistform.disabilityType == 'true'}">
						<td><bean:write name="studentDetails" property="disability"/></td>
					</c_rt:if>
					<c_rt:if test="${studentlistform.resRegion == 'true'}">
						<td><bean:write name="studentDetails" property="resRegion"/></td>
					</c_rt:if>					
					<c_rt:if test="${studentlistform.registrationStatus == 'true'}">
						<td><bean:write name="studentDetails" property="registrationStatus"/></td>
					</c_rt:if>
							<c_rt:if test="${studentlistform.moduleRegDate == 'true'}">
						<td><bean:write name="studentDetails" property="registrationDate"/></td>
					</c_rt:if>					
					<c_rt:if test="${studentlistform.tutGroupNr == 'true'}">
						<td><bean:write name="studentDetails" property="groupNumber"/></td>
					</c_rt:if>
				</tr>
			</logic:iterate>
			<tr>
				<html:form action="studentlistaction.do?action=afterGeneration">
					<sakai:actions>
						<td><html:cancel value="Cancel" titleKey="button.cancel"/></td>
					</sakai:actions>
				</html:form>
			</tr>
				
		</sakai:group_table>
			<br/><br/>
			
</sakai:html>