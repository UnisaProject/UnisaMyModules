<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>

<fmt:setBundle basename="za.ac.unisa.lms.tools.examscriptstats.ApplicationResources"/>

<sakai:html>
	<html:form action="/examScriptStats">
		<html:hidden property="currentPage" value="input"/>
		<sakai:messages/>
		<sakai:messages message="true"/>
		<sakai:heading>
			<fmt:message key="heading.examScriptStats"/>
		</sakai:heading>		
		<sakai:instruction>
			<fmt:message key="page.note1"/>&nbsp;<fmt:message key="page.mandatory"/>
		</sakai:instruction>
		<sakai:group_heading>
			<fmt:message key="page.instruction1"/> 
		</sakai:group_heading>
		<sakai:group_table>	
			<tr>
				<td><fmt:message key="page.examYear"/>&nbsp;<fmt:message key="page.mandatory"/></td>
				<td><html:text name="examScriptStatsForm" property="examYear" size="4" maxlength="4"/></td>
			</tr>
			<tr>
				<td><fmt:message key="page.examPeriod"/>&nbsp;<fmt:message key="page.mandatory"/></td>
				<td><html:select name="examScriptStatsForm" property="examPeriod">
						<html:optionsCollection name="examScriptStatsForm" property="listExamPeriods" value="code" label="engDescription"/>
					</html:select>                                           
				</td>
			</tr>			
			<tr>
				<td><fmt:message key="page.studyUnit"/>&nbsp;<fmt:message key="page.mandatory"/></td>
				<td><html:text name="examScriptStatsForm" property="studyUnit" size="10" maxlength="7"/></td>
			</tr>
			<tr>
				<td><fmt:message key="page.paperNumber"/>&nbsp;<fmt:message key="page.mandatory"/></td>
				<td><html:text name="examScriptStatsForm" property="paperNr" size="3" maxlength="2"/></td>
			</tr>		
		</sakai:group_table>	
		<sakai:actions>
			<html:submit property="action">
					<fmt:message key="button.display"/>
			</html:submit>			
		</sakai:actions>				
	</html:form>
</sakai:html>