<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>

<fmt:setBundle basename="za.ac.unisa.lms.tools.examscriptstats.ApplicationResources"/>

<sakai:html>
	<html:form action="/examScriptStats">
		<html:hidden property="currentPage" value="displayStats"/>
		<html:hidden property="displayAction" value="D"/>
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
		<hr/>
		<sakai:instruction>
			<fmt:message key="page.note2"/>
		</sakai:instruction>
		<sakai:group_heading>
			<fmt:message key="page.totals"/> 
		</sakai:group_heading>		
		<sakai:group_table>	
			<tr>
				<td><fmt:message key="page.expected"/></td>
				<td><bean:write name="examScriptStatsForm" property="totalScriptsExpected"/></td>			
				<td><fmt:message key="page.scriptsReceived"/></td>
				<td><bean:write name="examScriptStatsForm" property="totalScriptsReceived"/></td>				
				<td><fmt:message key="page.mcqReceived"/></td>
				<td><bean:write name="examScriptStatsForm" property="totalMCQReceived"/></td>
			</tr>
			<tr>
				<td><fmt:message key="page.absent"/></td>
				<td><bean:write name="examScriptStatsForm" property="totalStudentsAbsent"/></td>			
				<td><fmt:message key="page.scriptsOutstanding"/></td>
				<td><bean:write name="examScriptStatsForm" property="totalScriptsOutstanding"/></td>				
				<td><fmt:message key="page.mcqOutstanding"/></td>
				<td><bean:write name="examScriptStatsForm" property="totalMCQOutstanding"/></td>
			</tr>		
		</sakai:group_table>		
		<sakai:flat_list>
			<tr>
				<td align="left" colspan="5">
				 	<logic:notEmpty  name="examScriptStatsForm"  property="note">
				 		<bean:write name="examScriptStatsForm"  property="note"/>
				 	</logic:notEmpty>	
				</td>
				<td align="right" colspan="3">					
					<sakai:actions>
						<logic:equal name="examScriptStatsForm"  property="pageUpFlag" value="Y">
							<html:submit property="action" onclick="javascript:setPageUp();">
								<fmt:message key="button.previous"/>
							</html:submit>								
						</logic:equal>	
						<logic:notEqual name="examScriptStatsForm"  property="pageUpFlag" value="Y">	
							<html:submit property="action" disabled="true">
								<fmt:message key="button.previous"/>
							</html:submit>
						</logic:notEqual>
						<html:submit property="action" onclick="javascript:setFirst();">
								<fmt:message key="button.first"/>
						</html:submit>
						<logic:equal name="examScriptStatsForm"  property="pageDownFlag" value="Y">
							<html:submit property="action" onclick="javascript:setPageDown();">
								<fmt:message key="button.next"/>
							</html:submit>								
						</logic:equal>	
						<logic:notEqual name="examScriptStatsForm"  property="pageDownFlag" value="Y">	
							<html:submit property="action" disabled="true">
								<fmt:message key="button.next"/>
							</html:submit>
						</logic:notEqual>	
					</sakai:actions>
				</td>
			</tr>		
			<tr>
				<th align="left"><fmt:message key="venuestats.code"/></th>
				<th align="left"><fmt:message key="venuestats.desc"/></th>
				<th align="left"><fmt:message key="venuestats.expected"/></th>
				<th align="left"><fmt:message key="venuestats.received"/></th>
				<th align="left"><fmt:message key="venuestats.absent"/></th>
				<th align="left"><fmt:message key="venuestats.outstanding"/></th>
				<th align="left"><fmt:message key="venuestats.mcqReceived"/></th>
				<th align="left"><fmt:message key="venuestats.mcqOutstanding"/></th>
			</tr>
			<logic:notEmpty name="examScriptStatsForm" property="listVenueStats">
				<logic:iterate name="examScriptStatsForm" property="listVenueStats" id="record">
					<tr>
						<td>
							<html:link href="examScriptStats.do?action=linkStudentScripts" paramName="record" paramProperty="venueCode" paramId="selectedVenue">                                   							
							<fmt:message key="page.linkViewText"/>
							</html:link>
							<bean:write name="record" property="venueCode"/>
						</td>
						<td><bean:write name="record" property="venueDescription"/></td>
						<td align=right><bean:write name="record" property="totalScriptsExpected"/></td>
						<td align=right><bean:write name="record" property="totalScriptsReceived"/></td>
						<td align=right><bean:write name="record" property="totalStudentsAbsent"/></td>
						<td align=right><bean:write name="record" property="totalScriptsOutstanding"/></td>
						<td align=right><bean:write name="record" property="totalMCQReceived"/></td>
						<td align=right><bean:write name="record" property="totalMCQOutstanding"/></td>
					</tr>
				</logic:iterate>
			</logic:notEmpty>	
		</sakai:flat_list>
		<script language="javascript">
			function setPageUp(){
				document.forms["examScriptStatsForm"].elements["displayAction"].value="PU";
			}
			function setPageDown(){
				document.forms["examScriptStatsForm"].elements["displayAction"].value="PD";
			}
			function setFirst(){
				document.forms["examScriptStatsForm"].elements["displayAction"].value="D";
			}		
		</script>
	</html:form>
</sakai:html>