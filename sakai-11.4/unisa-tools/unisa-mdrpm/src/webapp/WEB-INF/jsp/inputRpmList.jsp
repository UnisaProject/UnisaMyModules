<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>

<fmt:setBundle basename="za.ac.unisa.lms.tools.mdrpm.ApplicationResources"/>

<sakai:html>
	<html:form action="/mdRpm">
		<html:hidden property="currentPage" value="inputRpmList"/>
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
				<td><bean:write name="mdRpmForm" property="qualification.description"/></td>
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
				<td><fmt:message key="page.prompt.listCriteria"/>&nbsp;<fmt:message key="prompt.mandatory"/></td>
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
	</html:form>
</sakai:html>	
		