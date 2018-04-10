<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>

<fmt:setBundle basename="za.ac.unisa.lms.tools.tpustudentplacement.ApplicationResources"/>

<sakai:html>	
	<html:form action="/supervisorMaintenance">
		<sakai:messages/>
		<sakai:messages message="true"/>	
			<sakai:heading>
			<fmt:message key="heading.adjustStudentAllocation"/> 
		</sakai:heading>
				
		<sakai:instruction>
			 <fmt:message key="note.required"/>&nbsp;<fmt:message key="prompt.mandatory"/>
		</sakai:instruction>
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
				<td><fmt:message key="prompt.supervisor"/></td>
				<td><bean:write name="studentPlacementForm" property="supervisorFullName"/></td>
			</tr>			
			<tr>
				<td><fmt:message key="prompt.studentAllocation"/></td>
				<td><html:text name="studentPlacementForm" property="studentsAllowed" size="10" maxlength="8"/></td>
			</tr>					
		</sakai:group_table>	
		<sakai:actions>
			<html:submit property="action">
					<fmt:message key="button.saveAllowedAlloc"/>
			</html:submit>
			<html:submit property="action">
					<fmt:message key="button.back"/>
			</html:submit>					
		</sakai:actions>				
	</html:form>
</sakai:html>