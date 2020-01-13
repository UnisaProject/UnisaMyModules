<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>

<fmt:setBundle basename="za.ac.unisa.lms.tools.assmarkerreallocation.ApplicationResources"/>

<sakai:html>
	<html:form action="/assMarkerReallocation">
		<html:hidden property="currentPage" value="input"/>
		<sakai:messages/>
		<sakai:messages message="true"/>
		<sakai:heading>
			<fmt:message key="heading.input"/>
		</sakai:heading>		
		<sakai:instruction>
			<fmt:message key="note.required"/>&nbsp;<fmt:message key="prompt.mandatory"/>
		</sakai:instruction>
		<sakai:group_heading>
			<fmt:message key="instruction.input1"/> 
		</sakai:group_heading>
		<sakai:group_table>	
			<tr>
				<td><fmt:message key="prompt.acadYear"/>&nbsp;<fmt:message key="prompt.mandatory"/></td>
				<td><html:select name="assMarkerReallocationForm"  property="acadYear">
						<html:optionsCollection name="assMarkerReallocationForm" property="listYear" value="year" label="year"/>
					</html:select>                                           
				</td>
			</tr>
			<tr>
				<td><fmt:message key="prompt.semester"/>&nbsp;<fmt:message key="prompt.mandatory"/></td>
				<td><html:select name="assMarkerReallocationForm" property="semester">
						<html:optionsCollection name="assMarkerReallocationForm" property="listSemester" value="code" label="engDescription"/>
					</html:select>                                           
				</td>
			</tr>			
			<tr>
				<td><fmt:message key="prompt.studyUnit"/>&nbsp;<fmt:message key="prompt.mandatory"/></td>
				<td><html:text name="assMarkerReallocationForm" property="studyUnit" size="10" maxlength="7"/></td>
			</tr>
				
		</sakai:group_table>	
		<sakai:actions>
			<html:submit property="action">
					<fmt:message key="button.continue"/>
			</html:submit>			
		</sakai:actions>				
	</html:form>
</sakai:html>