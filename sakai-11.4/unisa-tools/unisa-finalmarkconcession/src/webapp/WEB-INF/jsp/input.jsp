<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>

<fmt:setBundle basename="za.ac.unisa.lms.tools.finalmarkconcession.ApplicationResources"/>

<sakai:html>
	<!--<sakai:tool_bar>
		<html:link href="authorisation.do?action=codAuthRequestList" >
			<fmt:message key="link.codAuthorisation"/>
		</html:link>
		<html:link href="authorisation.do?action=deanAuthRequestList" >
			<fmt:message key="link.deanAuthorisation"/>
		</html:link>	
	</sakai:tool_bar>-->		
	<html:form action="/finalMarkConcession">
		<sakai:heading><fmt:message key="heading.main"/></sakai:heading>
		<html:hidden name="finalMarkConcessionForm" property="currentPage" value="input"/>
		<html:hidden property="displayAction" value="D"/>
		<sakai:messages/>
		<sakai:messages message="true"/>
		<sakai:instruction>
			<fmt:message key="note.required"/><fmt:message key="prompt.required"/>
		</sakai:instruction>
		<sakai:group_table>	
			<tr>
				<td><fmt:message key="prompt.examYear"/><fmt:message key="prompt.required"/></td>
				<td colspan="2"><html:text name="finalMarkConcessionForm" property="examYear" size="4" maxlength="4"/></td>				
			</tr>
			<tr>
				<td><fmt:message key="prompt.examPeriod"/><fmt:message key="prompt.required"/></td>
				<td colspan="2">
					<html:select name="finalMarkConcessionForm" property="examPeriod">
					<html:optionsCollection name="finalMarkConcessionForm" property="examPeriodCodes" value="code" label="engDescription"/>
					</html:select>
				</td>				
			</tr>
		</sakai:group_table>	
		<sakai:group_table>					
			<tr><td colspan="3"><b><fmt:message key="prompt.action"/></b></td></tr>
			<tr><td colspan="3"><sakai:group_table>
						<tr><td colspan="2"><b><fmt:message key="prompt.actionView"/></b></td></tr>
						<tr><td colspan="2">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<logic:equal name="finalMarkConcessionForm" property="selectedAction" value="VIEWLIST">
								<input type="radio" name="selectedAction" value="VIEWLIST" checked></input>List and View individual FI concession student records
							</logic:equal>	
							<logic:notEqual name="finalMarkConcessionForm" property="selectedAction" value="VIEWLIST">
								<input type="radio" name="selectedAction" value="VIEWLIST"></input>List and View individual FI concession student records
							</logic:notEqual> 
						</td></tr>							
						<tr><td colspan="2"><b><fmt:message key="prompt.actionEdit"/></b></td></tr>
						<tr>
							<td colspan="2">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<fmt:message key="prompt.enterStudyUnit"/><fmt:message key="prompt.required"/>&nbsp;&nbsp;&nbsp;&nbsp;<html:text name="finalMarkConcessionForm" property="studyUnitSearchCriteria" size="10" maxlength="8"/></td>
						</tr>		
						<tr><td colspan="2">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<logic:equal name="finalMarkConcessionForm" property="selectedAction" value="EDITINIT">
								<input type="radio" name="selectedAction" value="EDITINIT" checked></input>Capture assessment type and support provided for a group of students
							</logic:equal>	
							<logic:notEqual name="finalMarkConcessionForm" property="selectedAction" value="EDITINIT">
								<input type="radio" name="selectedAction" value="EDITINIT"></input>Capture assessment type and support provided for a group of students
							</logic:notEqual> 
						</td></tr>						
						<tr><td colspan="2">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<logic:equal name="finalMarkConcessionForm" property="selectedAction" value="EDITLIST">
								<input type="radio" name="selectedAction" value="EDITLIST" checked></input>List and Edit individual student FI concession records
							</logic:equal> 
							<logic:notEqual name="finalMarkConcessionForm" property="selectedAction" value="EDITLIST">
								<input type="radio" name="selectedAction" value="EDITLIST"></input>List and Edit individual student FI concession records
							</logic:notEqual> 
						</td></tr>			
						<tr><td colspan="2">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<logic:equal name="finalMarkConcessionForm" property="selectedAction" value="EDITUPLMRK">
								<input type="radio" name="selectedAction" value="EDITUPLMRK" checked></input>Import batch assessment results from Gradebook
							</logic:equal>	
							<logic:notEqual name="finalMarkConcessionForm" property="selectedAction" value="EDITUPLMRK">
								<input type="radio" name="selectedAction" value="EDITUPLMRK"></input>Import batch assessment results from Gradebook
							</logic:notEqual> 
						</td></tr>		
						<tr><td colspan="2">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<logic:equal name="finalMarkConcessionForm" property="selectedAction" value="EDITREQAUTH">
								<input type="radio" name="selectedAction" value="EDITREQAUTH" checked></input>Request batch authorisation for a group of students
							</logic:equal>	
							<logic:notEqual name="finalMarkConcessionForm" property="selectedAction" value="EDITREQAUTH">
								<input type="radio" name="selectedAction" value="EDITREQAUTH"></input>Request batch authorisation for a group of students
							</logic:notEqual> 
						</td></tr>			
						<tr><td><b><fmt:message key="prompt.actionAuthorise"/></b></td></tr>
						<tr><td colspan="2">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<logic:equal name="finalMarkConcessionForm" property="selectedAction" value="AUTHCOD">
								<input type="radio" name="selectedAction" value="AUTHCOD" checked></input>COD Authorisation
							</logic:equal>	
							<logic:notEqual name="finalMarkConcessionForm" property="selectedAction" value="AUTHCOD">
								<input type="radio" name="selectedAction" value="AUTHCOD"></input>COD Authorisation
							</logic:notEqual> 
						</td></tr>				
						<tr><td colspan="2">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<logic:equal name="finalMarkConcessionForm" property="selectedAction" value="AUTHDN">
								<input type="radio" name="selectedAction" value="AUTHDN" checked></input>Dean/Director Authorisation
							</logic:equal>	
							<logic:notEqual name="finalMarkConcessionForm" property="selectedAction" value="AUTHDN">
								<input type="radio" name="selectedAction" value="AUTHDN"></input>Dean/Director Authorisation
							</logic:notEqual> 
						</td></tr>						
					</sakai:group_table>	
				</td>
			</tr>	
					
		</sakai:group_table>
		<sakai:actions>
			<html:submit property="act">
					<fmt:message key="button.continue"/>
			</html:submit>
		</sakai:actions>
		<sakai:instruction>
			<fmt:message key="note.assistance"/>
		</sakai:instruction>	
	</html:form>
</sakai:html>