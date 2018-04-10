<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>

<fmt:setBundle basename="za.ac.unisa.lms.tools.tutoringplan.ApplicationResources"/>

<sakai:html>	
	<html:form action="/tutoringPlanDetails">
		<html:hidden property="currentPage" value="inputDetailList"/>
		<sakai:messages/>
		<sakai:messages message="true"/>		
		<sakai:heading>
			<fmt:message key="heading.tutoringPlanDetails"/>
		</sakai:heading>		
		<sakai:instruction>
			<fmt:message key="note.required"/>&nbsp;<fmt:message key="note.mandatory"/>
		</sakai:instruction>
		<sakai:group_heading>
			<fmt:message key="instr.inputTutoringPlan.enterCourse"/> 
		</sakai:group_heading>	
		<sakai:group_table>	
			<tr>
				<td><fmt:message key="prompt.acadYear"/>&nbsp;<fmt:message key="note.mandatory"/></td>
				<td><html:text name="tutoringPlanForm" property="acadYear" size="4" maxlength="4"/></td>
			</tr>
			<tr>
				<td><fmt:message key="prompt.semester"/>&nbsp;<fmt:message key="note.mandatory"/></td>
				<td>
					<sakai:group_table>
						<logic:iterate name="tutoringPlanForm" property="listSemester" id="record" indexId="index">
							<tr>
								<td><html:radio property="semester" idName="record" value="code"></html:radio></td>
								<td><bean:write name="record" property="engDescription"/></td>
							</tr>
						</logic:iterate>
					</sakai:group_table>
				</td>				
			</tr>
			<tr>
				<td><fmt:message key="prompt.searchLevel"/><fmt:message key="note.mandatory"/></td>
				<td><html:radio name="tutoringPlanForm" property="searchCriteria" value="C"></html:radio>
					<fmt:message key="prompt.college"/>
				</td>
				<td><bean:write name="tutoringPlanForm" property="userCollege.description"/>
			</tr>		
			<tr>
				<td></td>
				<td><html:radio name="tutoringPlanForm" property="searchCriteria" value="S"></html:radio>
					<fmt:message key="prompt.school"/>
				</td>
				<td>
					<html:select name="tutoringPlanForm" property="selectedSchool">
					<html:optionsCollection name="tutoringPlanForm"  property="listSchool" value="code" label="description"/>
					</html:select><BR>
				</td>
			</tr>	
			<tr>
				<td></td>
				<td><html:radio name="tutoringPlanForm" property="searchCriteria" value="D"></html:radio>
					<fmt:message key="prompt.department"/>
				</td>
				<td>
					<html:select name="tutoringPlanForm" property="selectedDepartment">
					<html:optionsCollection name="tutoringPlanForm"  property="listDepartment" value="code" label="description"/>
					</html:select><BR>
				</td>
			</tr>
		</sakai:group_table>	
		<sakai:actions>
			<html:submit property="action">
					<fmt:message key="button.display"/>
			</html:submit>	
			<html:submit property="action">
					<fmt:message key="button.back"/>
			</html:submit>		
		</sakai:actions>		
	</html:form>
</sakai:html>