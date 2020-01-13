<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>

<fmt:setBundle basename="za.ac.unisa.lms.tools.finalmarkconcession.ApplicationResources"/>

<sakai:html>
	<html:form action="/finalMarkConcession">
		<html:hidden property="currentPage" value="editFiStudentList"/>
		<html:hidden property="displayAction" value="D"/>
		<sakai:messages/>
		<sakai:messages message="true"/>
		<sakai:heading>		
			<fmt:message key="heading.listAndEdit"/>	
		</sakai:heading>
		<sakai:instruction>
			<fmt:message key="note.required"/><fmt:message key="prompt.required"/>
		</sakai:instruction>
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
			<tr>
				<td><fmt:message key="prompt.status"/><fmt:message key="prompt.required"/></td>
				<td colspan="2">
					<html:select name="finalMarkConcessionForm" property="statusSelected">
					<html:optionsCollection name="finalMarkConcessionForm" property="listStatus" value="code" label="engDescription"/>
					</html:select>
				</td>				
			</tr>			
		</sakai:group_table>
		<sakai:actions>
			<html:submit property="act">
					<fmt:message key="button.display"/>
			</html:submit>	
			<html:submit property="act">
					<fmt:message key="button.cancel"/>
			</html:submit>				
		</sakai:actions>	
		<!--<sakai:instruction>
			<fmt:message key="note.assistance"/>
		</sakai:instruction>-->		
		<hr/>
				
		<sakai:flat_list>			
			<tr>	
				<td colspan="6" align="left"><html:link href="finalMarkConcession.do?act=extractFile">
						<fmt:message key="link.extractFile"/>						
					</html:link>
				</td>		
				<td colspan="5" align="right">						
					<fmt:message key="note.abbrevFields"/>
				</td>
			</tr>
			<tr>	
				<!-- remove comment start -->						
				<td align="right" colspan="11">					
					<sakai:actions>
						<logic:equal name="finalMarkConcessionForm"  property="pageUpFlag" value="Y">
							<html:submit property="act" onclick="javascript:setPageUp();">
								<fmt:message key="button.previous"/>
							</html:submit>								
						</logic:equal>	
						<logic:notEqual name="finalMarkConcessionForm"  property="pageUpFlag" value="Y">	
							<html:submit property="act" disabled="true">
								<fmt:message key="button.previous"/>
							</html:submit>
						</logic:notEqual>
						<html:submit property="act" onclick="javascript:setFirst();">
								<fmt:message key="button.first"/>
						</html:submit>
						<logic:equal name="finalMarkConcessionForm"  property="pageDownFlag" value="Y">
							<html:submit property="act" onclick="javascript:setPageDown();">
								<fmt:message key="button.next"/>
							</html:submit>								
						</logic:equal>	
						<logic:notEqual name="finalMarkConcessionForm"  property="pageDownFlag" value="Y">	
							<html:submit property="act" disabled="true">
								<fmt:message key="button.next"/>
							</html:submit>
						</logic:notEqual>	
					</sakai:actions>
				</td>
				<!-- remove comment end -->	
			</tr>		
			<tr>				
				<th align="left"><fmt:message key="fiStudents.action"/></th> 
				<th align="left"><fmt:message key="fiStudents.number"/></th>
				<th align="left"><fmt:message key="fiStudents.name"/></th>
				<th align="left"><fmt:message key="fiStudents.register"/></th>
				<th align="left"><fmt:message key="fiStudents.finalMark"/></th>
				<th align="left"><fmt:message key="fiStudents.resultDesc"/></th>
				<th align="left"><fmt:message key="fiStudents.primaryLecturer"/></th>				
				<th align="left"><fmt:message key="fiStudents.status"/></th>
				<th align="left" colspan="3" align="center"><fmt:message key="fiStudents.concession"/></th>
				<th align="left"><fmt:message key="fiStudents.revisedFinalMark"/></th>
			</tr>
			
			<tr>
				<th align="left"  colspan="8"><fmt:message key="note.whocaneditstudentdetails"/></th>
				<th align="left" ><fmt:message key="fiStudents.assess"/></th>
				<th align="left" ><fmt:message key="fiStudents.support"/></th>
				<th align="left"><fmt:message key="fiStudents.mark"/></th>
				<th>&nbsp;</th>
			</tr>
			<logic:notEmpty name="finalMarkConcessionForm" property="fiStudents">
				<logic:iterate name="finalMarkConcessionForm" property="fiStudents" id="record">
					<bean:define id="studyUnit" name="record" property="studyUnit"/>
					<bean:define id="studentNumber" name="record" property="studentNumber"/>
					<% 
						java.util.HashMap params = new java.util.HashMap();
						params.put("studyUnit",studyUnit);
						params.put("studentNumber",studentNumber);
						pageContext.setAttribute("params",params);
					%>
					<tr> 
						<td style="white-space: nowrap">
							<logic:equal name="record" property="accessLevel" value="update">
								<html:link href="finalMarkConcession.do?act=editConcessionForm" scope="page" name="params">                                   							
									<fmt:message key="link.edit"/>
								</html:link>
							</logic:equal>
							<logic:equal name="record" property="accessLevel" value="read">
								<fmt:message key="link.edit"/>
							</logic:equal>
							<html:link href="finalMarkConcession.do?act=viewConcessionForm" scope="page" name="params">                                   							
							<fmt:message key="link.view"/>
							</html:link>							
						</td>							
						<td><bean:write name="record" property="studentNumber"/></td>
						<td><bean:write name="record" property="name"/></td>						
						<td><bean:write name="record" property="academicYear"/>/<bean:write name="record" property="semesterPeriod"/></td>
						<td align=right><bean:write name="record" property="finalMark"/></td>
						<td><bean:write name="record" property="resultAbbr"/></td>
						<td><bean:write name="record" property="primaryLecturer.name"/></td>						
						<td><bean:write name="record" property="status"/></td>
						<td>
							<logic:equal name="record" property="assessmentAbbr" value="OT">
								<span title="<bean:write name="record" property="assessmentDesc"/>-<bean:write name="record" property="assessmentOtherDesc"/>"><bean:write name="record" property="assessmentAbbr"/></span>
							</logic:equal>	
							<logic:notEqual name="record" property="assessmentAbbr" value="OT">
								<span title="<bean:write name="record" property="assessmentDesc"/>"><bean:write name="record" property="assessmentAbbr"/></span>
							</logic:notEqual>	
						</td>
						<td>
							<logic:equal name="record" property="supportAbbr" value="OT">
								<span title="<bean:write name="record" property="supportDesc"/>-<bean:write name="record" property="supportOtherDesc"/>"><bean:write name="record" property="supportAbbr"/></span>
							</logic:equal>
							<logic:notEqual name="record" property="supportAbbr" value="OT">
								<span title="<bean:write name="record" property="supportDesc"/>"><bean:write name="record" property="supportAbbr"/></span>
							</logic:notEqual>
						</td>
						<td><bean:write name="record" property="concessionMark"/></td>						
						<td><bean:write name="record" property="revisedFinalMark"/></td>
					</tr>
				</logic:iterate>
			</logic:notEmpty>	
		</sakai:flat_list>
		<script language="javascript">
			function setPageUp(){
				document.forms["finalMarkConcessionForm"].elements["displayAction"].value="PU";
			}
			function setPageDown(){
				document.forms["finalMarkConcessionForm"].elements["displayAction"].value="PD";
			}
			function setFirst(){
				document.forms["finalMarkConcessionForm"].elements["displayAction"].value="D";
			}		
		</script>
	</html:form>
</sakai:html>