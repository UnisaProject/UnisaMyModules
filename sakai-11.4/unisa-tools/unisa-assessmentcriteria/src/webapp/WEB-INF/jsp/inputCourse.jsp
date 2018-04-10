<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>

<fmt:setBundle basename="za.ac.unisa.lms.tools.assessmentcriteria.ApplicationResources"/>

<sakai:html>
	<html:form action="/assessmentCriteria">
		<html:hidden property="currentPage" value="input"/>
		<sakai:messages/>
		<sakai:messages message="true"/>
		<logic:equal  name="assessmentCriteriaForm"  property="siteId" value="Course Admin">
			<sakai:heading>
				<fmt:message key="heading.assessmentCriteria"/>
			</sakai:heading>		
			<sakai:instruction>
				<fmt:message key="page.note1"/>&nbsp;<fmt:message key="page.mandatory"/>
			</sakai:instruction>
			<sakai:group_heading>
				<fmt:message key="page.instruction1"/> 
			</sakai:group_heading>
			<sakai:group_table>	
				<tr>
					<td><fmt:message key="page.academicYear"/>&nbsp;<fmt:message key="page.mandatory"/></td>
					<td><html:text name="assessmentCriteriaForm" property="academicYear" size="4" maxlength="4"/></td>
				</tr>
				<tr>
					<td><fmt:message key="page.semester"/>&nbsp;<fmt:message key="page.mandatory"/></td>
					<td>
						<sakai:group_table>
							<logic:iterate name="assessmentCriteriaForm" property="listSemester" id="record" indexId="index">
								<tr>
									<td><html:radio property="semester" idName="record" value="code"></html:radio></td>
									<td><bean:write name="record" property="engDescription"/></td>
								</tr>
							</logic:iterate>
						</sakai:group_table>
					</td>				
				</tr>
				<tr>
					<td><fmt:message key="page.studyUnit"/>&nbsp;<fmt:message key="page.mandatory"/></td>
					<td><html:text name="assessmentCriteriaForm" property="studyUnit.code" size="10" maxlength="7"/></td>
				</tr>			
			</sakai:group_table>	
			<sakai:actions>
				<html:submit property="act">
						<fmt:message key="button.continue"/>
				</html:submit>			
			</sakai:actions>		
		</logic:equal>	
	</html:form>
</sakai:html>