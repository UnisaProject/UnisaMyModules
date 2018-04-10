<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ page import="java.util.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<fmt:setBundle basename="za.ac.unisa.lms.tools.tutorprebooks.ApplicationResources"/>

<sakai:html>

	<sakai:group_heading>
		<fmt:message key="tutorprebooks.reports"/> 
	</sakai:group_heading>
	
	<br>
		
	<html:form action="/tutorMain">
		
		<sakai:actions>
			<html:submit property="action">
				<fmt:message key="button.completed" />
			</html:submit>
			<html:submit property="action">
				<fmt:message key="button.outstanding" />
			</html:submit>
			<html:submit property="action">
				<fmt:message key="button.back" />
			</html:submit>
		</sakai:actions>
		
		<p>
						
		<c:if test="${mainForm.completedButtonClick == true}">
			<logic:empty name="mainForm" property="completedPrescribedBooksList">
				<sakai:group_heading>
					<fmt:message key="tutorprebooks.noresults.reports" /> [${mainForm.year}/${mainForm.acadPeriod}]
				</sakai:group_heading>
			</logic:empty>
			<logic:notEmpty name="mainForm" property="completedPrescribedBooksList">
				<sakai:group_heading>
				<fmt:message key="tutorprebooks.completed.reports" /> [${mainForm.year}/${mainForm.acadPeriod}]
				</sakai:group_heading>
				<br>
				<table border="1">
					<tr>
						<th><fmt:message key="tutorprebooks.field.college"/></th>
						<th><fmt:message key="tutorprebooks.field.school"/></th>
						<th><fmt:message key="tutorprebooks.field.department"/></th>
						<th><fmt:message key="tutorprebooks.field.module"/></th>
						<th><fmt:message key="tutorprebooks.field.no_of_tutorbooks"/></th>
						<th><fmt:message key="tutorprebooks.field.modifiedBy"/></th>
					</tr>
					<logic:iterate name="mainForm" property="completedPrescribedBooksList" id="c" indexId="cindex">				
						<tr>
							<td><bean:write name="c" property="collegeName"/></td>	
							<td><bean:write name="c" property="schoolName"/></td>	
							<td><bean:write name="c" property="departmentName"/></td>
							<td><bean:write name="c" property="module"/></td>
						    <td><bean:write name="c" property="noOfTutorBooks"/></td>
						    <td><bean:write name="c" property="modifiedBy"/></td>					                 				
						</tr>		
					</logic:iterate>
				</table>		
			</logic:notEmpty>
		</c:if>
		
		<c:if test="${mainForm.outstandingButtonClick == true}">
			<logic:empty name="mainForm" property="outstandingPrescribedBooksList">
				<sakai:group_heading>
					<fmt:message key="tutorprebooks.noresults.reports" /> [${mainForm.year}/${mainForm.acadPeriod}]
				</sakai:group_heading>
			</logic:empty>
			<logic:notEmpty name="mainForm" property="outstandingPrescribedBooksList">
				<sakai:group_heading>
					<fmt:message key="tutorprebooks.outstanding.reports" /> [${mainForm.year}/${mainForm.acadPeriod}]
				</sakai:group_heading>
				<br>
				<table border="1">
					<tr>
						<th><fmt:message key="tutorprebooks.field.college"/></th>
						<th><fmt:message key="tutorprebooks.field.school"/></th>
						<th><fmt:message key="tutorprebooks.field.department"/></th>
						<th><fmt:message key="tutorprebooks.field.module"/></th>
					</tr>
					<logic:iterate name="mainForm" property="outstandingPrescribedBooksList" id="c" indexId="cindex">				
						<tr>
							<td><bean:write name="c" property="collegeName"/></td>	
							<td><bean:write name="c" property="schoolName"/></td>	
							<td><bean:write name="c" property="departmentName"/></td>
							<td><bean:write name="c" property="module"/></td>					                 				
						</tr>		
					</logic:iterate>
				</table>		
			</logic:notEmpty>
		</c:if>
					
 	</html:form>
</sakai:html>