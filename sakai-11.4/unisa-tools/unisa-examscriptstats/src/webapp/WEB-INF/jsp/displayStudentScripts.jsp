<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>

<fmt:setBundle basename="za.ac.unisa.lms.tools.examscriptstats.ApplicationResources"/>

<sakai:html>
	<html:form action="/examScriptStats">
		<html:hidden property="currentPage" value="displayStudentScripts"/>
		<html:hidden property="displayStudentAction" value="D"/>
		<sakai:messages/>
		<sakai:messages message="true"/>
		<sakai:heading>
			<fmt:message key="heading.studentScriptsOutstanding"/>
		</sakai:heading>			
		<sakai:group_table>	
			<tr>
				<td><fmt:message key="page.examYear"/></td>
				<td><bean:write name="examScriptStatsForm" property="examYear"/></td>
				<td><fmt:message key="page.examPeriod"/></td>
				<td><bean:write name="examScriptStatsForm" property="examPeriod"/></td>				
			</tr>
			<tr>
				<td><fmt:message key="page.studyUnit"/></td>
				<td><bean:write name="examScriptStatsForm" property="studyUnit"/></td>
				<td><fmt:message key="page.paperNumber"/></td>
				<td><bean:write name="examScriptStatsForm" property="paperNr"/></td>
			</tr>	
			<tr>
				<td><fmt:message key="page.venue"/></td>
				<td><bean:write name="examScriptStatsForm" property="selectedVenue"/></td>
				<td colspan="2"><bean:write name="examScriptStatsForm" property="selectedVenueDesc"/></td>				
			</tr>		
		</sakai:group_table>			
		<sakai:flat_list>
			<tr>
				<td align="right" colspan="3">					
					<sakai:actions>
						<logic:equal name="examScriptStatsForm"  property="studentPageUpFlag" value="Y">
							<html:submit property="action" onclick="javascript:setPageUp();">
								<fmt:message key="button.previous"/>
							</html:submit>								
						</logic:equal>	
						<logic:notEqual name="examScriptStatsForm"  property="studentPageUpFlag" value="Y">	
							<html:submit property="action" disabled="true">
								<fmt:message key="button.previous"/>
							</html:submit>
						</logic:notEqual>
						<html:submit property="action" onclick="javascript:setFirst();">
								<fmt:message key="button.first"/>
						</html:submit>
						<logic:equal name="examScriptStatsForm"  property="studentPageDownFlag" value="Y">
							<html:submit property="action" onclick="javascript:setPageDown();">
								<fmt:message key="button.next"/>
							</html:submit>								
						</logic:equal>	
						<logic:notEqual name="examScriptStatsForm"  property="studentPageDownFlag" value="Y">	
							<html:submit property="action" disabled="true">
								<fmt:message key="button.next"/>
							</html:submit>
						</logic:notEqual>	
					</sakai:actions>
				</td>
			</tr>
			<tr>
				<th align="left"><fmt:message key="studentscript.student"/></th>
				<th align="left"><fmt:message key="studentscript.comment"/></th>
				<th align="left"><fmt:message key="studentscript.outstanding"/></th>				
			</tr>
			<logic:notEmpty name="examScriptStatsForm" property="listStudentScriptsOutstanding">
				<logic:iterate name="examScriptStatsForm" property="listStudentScriptsOutstanding" id="record">
					<tr>						
						<td><bean:write name="record" property="student"/></td>
						<td><bean:write name="record" property="comment"/></td>
						<td><bean:write name="record" property="outstanding"/></td>						
					</tr>
				</logic:iterate>
			</logic:notEmpty>	
		</sakai:flat_list>	
		<sakai:actions>
			<html:submit property="action">
					<fmt:message key="button.back"/>
			</html:submit>			
		</sakai:actions>
		<script language="javascript">
			function setPageUp(){
				document.forms["examScriptStatsForm"].elements["displayStudentAction"].value="PU";
			}
			function setPageDown(){
				document.forms["examScriptStatsForm"].elements["displayStudentAction"].value="PD";
			}
			function setFirst(){
				document.forms["examScriptStatsForm"].elements["displayStudentAction"].value="D";
			}		
		</script>		
	</html:form>
</sakai:html>