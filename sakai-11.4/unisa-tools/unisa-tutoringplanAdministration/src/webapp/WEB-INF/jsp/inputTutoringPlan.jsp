<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>

<fmt:setBundle basename="za.ac.unisa.lms.tools.tutoringplan.ApplicationResources"/>

<sakai:html>
	<sakai:tool_bar>
		<html:link href="tutoringPlanDetails.do?action=initial" >
			<fmt:message key="link.detailList"/>
		</html:link>
	</sakai:tool_bar>	
	<html:form action="/tutoringPlan">
		<html:hidden property="currentPage" value="inputTutoringPlan"/>
		<sakai:messages/>
		<sakai:messages message="true"/>		
		<sakai:heading>
			<fmt:message key="heading.captureTutoringInfo"/>
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
				<td><fmt:message key="prompt.studyUnit"/>&nbsp;<fmt:message key="note.mandatory"/></td>
				<td><html:text name="tutoringPlanForm" property="inputStudyUnit" size="10" maxlength="7"/></td>
			</tr>
		</sakai:group_table>	
		<sakai:actions>
			<html:submit property="action">
					<fmt:message key="button.continue"/>
			</html:submit>			
		</sakai:actions>		
	</html:form>
</sakai:html>