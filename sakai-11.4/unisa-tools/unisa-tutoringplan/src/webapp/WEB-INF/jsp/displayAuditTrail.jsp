<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>

<fmt:setBundle basename="za.ac.unisa.lms.tools.tutoringplan.ApplicationResources"/>

<sakai:html>
	<html:form action="/tutoringPlan">
		<html:hidden property="currentPage" value="displayAuditTrail"/>
		<sakai:messages/>
		<sakai:messages message="true"/>		
		<sakai:heading>
			<fmt:message key="heading.auditTrail"/>
		</sakai:heading>				
		<sakai:instruction>
			<fmt:message key="note.required"/>&nbsp;<fmt:message key="note.mandatory"/>
		</sakai:instruction>		
		<sakai:group_table>	
			<tr>
				<td><fmt:message key="prompt.studyUnit"/>&nbsp;</td>
				<td><bean:write name="tutoringPlanForm" property="studyUnit.code"/></td>
				<td><bean:write name="tutoringPlanForm" property="studyUnit.engLongDesc"/></td>
			</tr>
			<tr>
				<td><fmt:message key="prompt.yearSemester"/>&nbsp;</td>			
				<td><bean:write name="tutoringPlanForm" property="acadYear"/>/<bean:write name="tutoringPlanForm" property="semesterType"/></td>
				<td>&nbsp;</td>
			</tr>
		</sakai:group_table>		
		<hr/>
		<sakai:heading>
			<fmt:message key="heading.auditTrail.list"/>
		</sakai:heading>
		<sakai:flat_list>
			<tr>
				<th rowspan="2"><fmt:message key="column.audit.updatedOn"/></th>
				<th rowspan="2"><fmt:message key="column.audit.action"/></th>
				<th rowspan="2"><fmt:message key="column.audit.comment"/></th>								
				<th colspan="2" align="center"><fmt:message key="column.audit.updatedBy"/></th>
			</tr>
			<tr>
				<th><fmt:message key="column.audit.userNetworkCode"/></th>
				<th><fmt:message key="column.audit.userName"/></th>
			</tr>
			<logic:iterate name="listAudit" id="record" indexId="index">
				<tr>
					<td><bean:write name="record" property="updatedOn"/></td>
					<td><bean:write name="record" property="action"/></td>					
					<td><bean:write name="record" property="comment"/></td>					
					<td><bean:write name="record" property="user.novellUserId"/></td>
					<td><bean:write name="record" property="user.name"/></td>					
				</tr>
			</logic:iterate>
		</sakai:flat_list>	
		<sakai:actions>			
			<html:submit property="action">
					<fmt:message key="button.back"/>
			</html:submit>		
			<html:submit property="action">
					<fmt:message key="button.cancel"/>
			</html:submit>			
		</sakai:actions>		
	</html:form>
</sakai:html>