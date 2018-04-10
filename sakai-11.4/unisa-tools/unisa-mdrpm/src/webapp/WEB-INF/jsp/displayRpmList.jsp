<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>

<fmt:setBundle basename="za.ac.unisa.lms.tools.mdrpm.ApplicationResources"/>

<sakai:html>
	<html:form action="/mdRpm">
		<html:hidden property="currentPage" value="displayRpmList"/>
		<sakai:messages/>
		<sakai:messages message="true"/>
		<sakai:group_table>
		<tr>
				<td><strong><fmt:message key="page.prompt.college"/></strong></td>
				<td colspan="2"><bean:write name="mdRpmForm" property="college.description" /></td>				
			</tr>
			<tr>
				<td><strong><fmt:message key="page.prompt.qualification"/></strong></td>
				<td><html:text name="mdRpmForm" property="qualification.code" size="10" maxlength="8"/></td>
				<td><bean:write name="mdRpmForm" property="qualification.description" /></td>
			</tr>
			<tr>
				<td><strong><fmt:message key="page.prompt.speciality"/></strong></td>
				<td><html:text name="mdRpmForm" property="qualification.speciality.code" size="10" maxlength="8"/></td>
				<td><bean:write name="mdRpmForm" property="qualification.speciality.description"/></td>
			</tr>
			<tr>
				<td><strong><fmt:message key="page.prompt.studyUnit"/></strong></td>
				<td><html:text name="mdRpmForm" property="studyUnit.code" size="10" maxlength="8"/></td>
				<td><bean:write name="mdRpmForm" property="studyUnit.engLongDesc" /></td>
			</tr>
			<tr>
				<td><strong><fmt:message key="page.prompt.supervisor"/></strong></td>
				<td colspan="2">					
					<logic:notEmpty name="mdRpmForm" property="supervisorList">
						<html:select name="mdRpmForm" property="supervisor.personnelNumber">
							<html:optionsCollection name="mdRpmForm" property="supervisorList" value="value" label="label"/>
						</html:select> 
					</logic:notEmpty>
					<!--<html:submit property="act">
						<fmt:message key="button.supevisors"/>
					</html:submit>-->	
				</td>				
			</tr>
			<tr>
				<td><strong><fmt:message key="page.prompt.listCriteria"/>&nbsp;<fmt:message key="prompt.mandatory"/></strong></td>
				<td colspan="2"><html:select name="mdRpmForm" property="criteriaSelected">
						<html:optionsCollection name="mdRpmForm" property="criteriaList" value="value" label="label"/>
					</html:select>                                           
				</td>
			</tr>			
			<tr>
				<td><strong><fmt:message key="page.prompt.studentNr"/></strong></td>
				<td><html:text name="mdRpmForm" property="studentNr" size="10" maxlength="8"/></td>
				<td>&nbsp;</td>
			</tr>
		</sakai:group_table>
		<sakai:actions>		
			<html:submit property="act">
					<fmt:message key="button.display"/>
			</html:submit>	
		</sakai:actions>	
		<hr/>
		<sakai:heading>
			<fmt:message key="heading.table.rpmlist"/>
		</sakai:heading>
		<sakai:flat_list>
			<tr>
				<th><fmt:message key="column.rpm.studentNr"/></th>
				<th><fmt:message key="column.rpm.studentName"/></th>				
				<th><fmt:message key="column.rpm.qualification"/></th>	
				<th><fmt:message key="column.rpm.speciality"/></th>	
				<th><fmt:message key="column.rpm.studyUnit"/></th>	
				<th><fmt:message key="column.rpm.proposedResult"/></th>	
				<th><fmt:message key="column.rpm.status"/></th>	
				<th><fmt:message key="column.rpm.referLevel"/></th>	
				<th><fmt:message key="column.rpm.referTo"/></th>	
				<th><fmt:message key="column.rpm.supervisor"/></th>		
				<th><fmt:message key="column.rpm.title"/></th>
			</tr>
			<logic:iterate name="mdRpmForm" property="rpmList" id="record" indexId="index">
			<bean:define id="module" name="record" property="studyUnit"/>
			<bean:define id="studentNr" name="record" property="studentNr"/>
			<bean:define id="qualCode" name="record" property="qualCode"/>
			<bean:define id="specCode" name="record" property="specCode"/>
					<% 
						java.util.HashMap params = new java.util.HashMap();
						params.put("module", module);
						params.put("studentNumber",studentNr);
						params.put("qualCode",qualCode);
						params.put("specCode",specCode);
						params.put("calledFrom","displayRpmList");
						pageContext.setAttribute("params",params);
					%>
					<tr> 
						<td style="white-space: nowrap">
								<html:link href="mdRpm.do?act=rpmAction" scope="page" name="params">                                   							
									<bean:write name="record" property="studentNr"/>
								</html:link>
						</td>					
						<td><bean:write name="record" property="studentName"/></td>
						<td><bean:write name="record" property="qualCode"/></td>
						<td><bean:write name="record" property="specCode"/></td>
						<td><bean:write name="record" property="studyUnit"/></td>
						<td><bean:write name="record" property="proposedResult"/></td>
						<td><bean:write name="record" property="workListItem.status"/></td>
						<td><bean:write name="record" property="workListItem.referToLevel"/></td>
						<td><bean:write name="record" property="workListItem.referToPersno"/></td>
						<logic:equal name="record" property="workListItem.status" value="FINAL">
							<td><bean:write name="record" property="supervisor"/></td>
							<td><bean:write name="record" property="title"/></td>	
						</logic:equal>
						<logic:notEqual name="record" property="workListItem.status" value="FINAL">
							<td><html:link href="mdRpm.do?act=supervisorMnt" scope="page" name="params"><bean:write name="record" property="supervisor"/></html:link></td>
							<td><html:link href="mdRpm.do?act=titleMnt" scope="page" name="params"> <bean:write name="record" property="title"/></html:link></td>	
						</logic:notEqual>				
				</tr>
			</logic:iterate>	
		</sakai:flat_list>					
	</html:form>
</sakai:html>	