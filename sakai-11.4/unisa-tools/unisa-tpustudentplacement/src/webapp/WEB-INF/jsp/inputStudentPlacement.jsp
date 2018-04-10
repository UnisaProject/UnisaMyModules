<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>

<fmt:setBundle basename="za.ac.unisa.lms.tools.tpustudentplacement.ApplicationResources"/>

<sakai:html>	
	<html:form action="/studentPlacement">
		<!--<html:hidden property="currentPage" value="inputPlacement"/>-->
		<sakai:tool_bar>
			<html:link href="schoolMaintenance.do?action=initial">
				<fmt:message key="link.schoolMaintenance"/>
			</html:link>
			<html:link href="supervisorMaintenance.do?action=initial">
				<fmt:message key="link.supervisorMaintenance"/>
			</html:link>
			<html:link href="managementInfo.do?action=initial">
				<fmt:message key="link.managementInfo"/>
			</html:link>
			<html:link href="placementLogMaintenance.do?action=initial">
				<fmt:message key="link.placementLog"/>
			</html:link>
			<logic:notEqual name="studentPlacementForm"  property="coordinatorActive"  value="Y">
			      <u style="color:grey"><fmt:message key="link.coordinator"/> </u>
			</logic:notEqual>
			<logic:equal name="studentPlacementForm"  property="coordinatorActive"  value="Y">
			      <html:link href="coordinatorMaintenance.do?action=initial">
				      <fmt:message key="link.coordinator"/>
			      </html:link>
			</logic:equal>
			<html:link href="mentorMaintenance.do?action=initial">
				      <fmt:message key="link.mentor"/>
		     </html:link>
		</sakai:tool_bar>
		<sakai:messages/>
		<sakai:messages message="true"/>			
		<sakai:instruction>
			<fmt:message key="note.required"/>&nbsp;<fmt:message key="prompt.mandatory"/>
		</sakai:instruction>
		<sakai:group_heading>
			<fmt:message key="inputStudentPlacement.instruction"/> 
		</sakai:group_heading>
		<sakai:group_table>	
			<tr>
				<td><fmt:message key="prompt.acadYear"/>&nbsp;<fmt:message key="prompt.mandatory"/></td>
				<td><html:text name="studentPlacementForm" property="acadYear" size="4" maxlength="4"/></td>
			</tr>
			<tr>
				<td><fmt:message key="prompt.semester"/>&nbsp;<fmt:message key="prompt.mandatory"/></td>
				<td><html:select name="studentPlacementForm" property="semester">
						<html:optionsCollection name="studentPlacementForm" property="listSemester" value="code" label="engDescription"/>
					</html:select>                                           
				</td>
			</tr>			
			<tr>
				<td><fmt:message key="prompt.studentNumber"/>&nbsp;<fmt:message key="prompt.mandatory"/></td>
				<td><html:text name="studentPlacementForm" property="studentNr" size="10" maxlength="8"/></td>
			</tr>					
		</sakai:group_table>	
		<sakai:actions>
			<html:submit property="action">
					<fmt:message key="button.continue"/>
			</html:submit>			
		</sakai:actions>				
	</html:form>
</sakai:html>