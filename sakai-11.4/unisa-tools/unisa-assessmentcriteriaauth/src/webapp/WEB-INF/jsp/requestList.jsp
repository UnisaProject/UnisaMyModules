<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>

<fmt:setBundle basename="za.ac.unisa.lms.tools.assessmentcriteriaauth.ApplicationResources"/>

<sakai:html>
<!-- Toolbar -->
	<sakai:tool_bar>
		<html:link href="assessmentCriteriaAuth.do?action=inputStatusList">
			<fmt:message key="link.assessmentCriteriaStatusList"/>
		</html:link>
	</sakai:tool_bar>
	<html:form action="/assessmentCriteriaAuth">
		<html:hidden property="currentPage" value="requestList"/>
		<sakai:messages/>
		<sakai:messages message="true"/>
		<sakai:heading>
			<fmt:message key="heading.assessmentCriteriaAuth"/>
		</sakai:heading>
		<sakai:instruction>
			<fmt:message key="page.note1"/>&nbsp;<fmt:message key="page.mandatory"/>
		</sakai:instruction>
		<sakai:group_heading>
			<fmt:message key="page.instruction1"/> 
		</sakai:group_heading>
		<logic:empty name="assessmentCriteriaAuthForm" property="requestList">
			<sakai:instruction>
				<fmt:message key="page.note4"/>
			</sakai:instruction>
		</logic:empty>
		<logic:notEmpty name="assessmentCriteriaAuthForm" property="requestList">
			<sakai:group_table>	
				<logic:iterate name="assessmentCriteriaAuthForm" property="requestList" id="courses" indexId="index">
					<tr>
						<td><html:radio property="selectedCourseIndex" value='<%= "" + index.toString() + "" %>'></html:radio></td>
						<td>
							<bean:write name="courses" property="courseCode"/>-
							<bean:write name="courses" property="year"/>-
							<bean:write name="courses" property="semesterType"/>
						</td>				
					</tr>			 
				</logic:iterate>
			</sakai:group_table>				
			<sakai:actions>
				<html:submit property="action">
						<fmt:message key="button.continue"/>
				</html:submit>			
			</sakai:actions>	
		</logic:notEmpty>		
	</html:form>
</sakai:html>