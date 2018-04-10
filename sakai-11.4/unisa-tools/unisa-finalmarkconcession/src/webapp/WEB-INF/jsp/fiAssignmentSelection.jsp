<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>

<fmt:setBundle basename="za.ac.unisa.lms.tools.finalmarkconcession.ApplicationResources"/>
<script language="javascript">
	function doAction() {
		document.finalMarkConcessionForm.action = 'finalMarkConcession.do?act=nextStep';  
		document.finalMarkConcessionForm.submit();
	}
</script>
<sakai:html>
	<html:form action="/finalMarkConcession">
		<html:hidden property="currentPage" value="fiAssignmentSelection"/>
		<sakai:messages/>
		<sakai:messages message="true"/>
		<sakai:heading>		
			<fmt:message key="heading.import"/>	
		</sakai:heading>		
		<sakai:group_table>	
			<tr>
				<td><fmt:message key="prompt.examYear"/></td>
				<td colspan="2"><bean:write name="finalMarkConcessionForm" property="examYear"/></td>				
			</tr>
			<tr>
				<td><fmt:message key="prompt.examPeriod"/></td>
				<td colspan="2">
					<bean:write name="finalMarkConcessionForm" property="examPeriodDesc"/>&nbsp;
				</td>				
			</tr>
			<tr>
				<td><fmt:message key="prompt.studyUnit"/></td>				
				<td colspan="2"><bean:write name="finalMarkConcessionForm" property="studyUnitSearchCriteria"/></td>				
			</tr>	
		</sakai:group_table>
		<hr/>		
		<sakai:instruction>
			<fmt:message key="note.import.note1"/>
		</sakai:instruction>	
		<sakai:instruction>
			<fmt:message key="note.import.note2"/><br/>
			<sakai:group_table>
				<tr valign="top"><td><fmt:message key="note.import.caption2a"/></td><td><fmt:message key="note.import.note2a"/></td></tr>
				<tr valign="top"><td><fmt:message key="note.import.caption2b"/></td><td><fmt:message key="note.import.note2b"/></td></tr>
				<tr valign="top"><td><fmt:message key="note.import.caption2c"/></td><td><fmt:message key="note.import.note2c"/></td></tr>
			</sakai:group_table>		
		</sakai:instruction>			
		<sakai:heading>		
			<fmt:message key="heading.import.selectItem"/>	
		</sakai:heading>	
		<sakai:group_table>
			<tr>				
				<th align="left"><fmt:message key="prompt.gradeBookObject.name"/></th>	
				<th align="left"><fmt:message key="prompt.gradeBookObject.pointsPossible"/></th>				
			</tr>
			<logic:empty name="finalMarkConcessionForm" property="listFiAssignments">
				<tr>
					<td><fmt:message key="note.gradeBookObject.noRecords"/></td><td>&nbsp;</td>
				</tr>
			</logic:empty>				
			<logic:notEmpty name="finalMarkConcessionForm" property="listFiAssignments">
				<logic:iterate name="finalMarkConcessionForm" property="listFiAssignments" id="record">				
					<tr>
						<td><html:radio property="selectedGBObject" idName="record" value="id"></html:radio>				
						<!--<html:link href="finalMarkConcession.do?act=displayFiAssignmentStudentMarks" paramName="record" paramProperty="id" paramId="selectedGBObject">                                   							
								<fmt:message key="link.select"/>&nbsp;								
							</html:link>-->							
							<bean:write name="record" property="name"/></td>
						<td><bean:write name="record" property="pointsPossible"/></td>
					</tr>
				</logic:iterate>
			</logic:notEmpty>	
		</sakai:group_table>
		<sakai:actions>
			<logic:notEmpty name="finalMarkConcessionForm" property="listFiAssignments">
				<html:submit property="act" onclick="javascript:disabled=true;doAction();">
						<fmt:message key="button.continue"/>
				</html:submit>	
			</logic:notEmpty>	
			<logic:empty name="finalMarkConcessionForm" property="listFiAssignments">
				<html:submit property="act"  disabled="true">
						<fmt:message key="button.continue"/>
				</html:submit>
			</logic:empty>
			<html:submit property="act">
				<fmt:message key="button.cancel"/>
			</html:submit>		
		</sakai:actions>	
	</html:form>
</sakai:html>		