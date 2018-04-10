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
		<html:hidden name="finalMarkConcessionForm" property="currentPage" value="inputViewFiList"/>
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
			<tr>
				<td><fmt:message key="prompt.status"/><fmt:message key="prompt.required"/></td>
				<td colspan="2">
					<html:select name="finalMarkConcessionForm" property="statusSelected">
					<html:optionsCollection name="finalMarkConcessionForm" property="listStatus" value="code" label="engDescription"/>
					</html:select>
				</td>				
			</tr>	
			<tr>
				<td><fmt:message key="prompt.searchLevel"/><fmt:message key="prompt.required"/></td>
				<td><html:radio name="finalMarkConcessionForm" property="searchCriteria" value="C"></html:radio>
					<fmt:message key="prompt.college"/>
				</td>
				<td><bean:write name="finalMarkConcessionForm" property="userCollege.description"/>
			</tr>			
			<tr>
				<td></td>
				<td><html:radio name="finalMarkConcessionForm" property="searchCriteria" value="D"></html:radio>
					<fmt:message key="prompt.department"/>
				</td>
				<td>
					<html:select name="finalMarkConcessionForm" property="selectedDepartment">
					<html:optionsCollection name="finalMarkConcessionForm"  property="listDepartments" value="code" label="description"/>
					</html:select><BR>
				</td>
			</tr>
			<tr>
				<td></td>
				<td><html:radio name="finalMarkConcessionForm" property="searchCriteria" value="S"></html:radio>
					<fmt:message key="prompt.studyUnit"/>
				</td>
				<td><html:text name="finalMarkConcessionForm" property="studyUnitSearchCriteria" size="10" maxlength="8"/></td>
			</tr>			
		</sakai:group_table>
		<sakai:actions>
			<html:submit property="act">
					<fmt:message key="button.display"/>
			</html:submit>	
			<!--<html:submit property="action">
					<fmt:message key="button.codAuth"/>
			</html:submit>	
			<html:submit property="action">
					<fmt:message key="button.deanAuth"/>
			</html:submit>-->				
		</sakai:actions>	
		<!--<sakai:instruction>
			<fmt:message key="note.assistance"/>
		</sakai:instruction>-->	
	</html:form>
</sakai:html>
