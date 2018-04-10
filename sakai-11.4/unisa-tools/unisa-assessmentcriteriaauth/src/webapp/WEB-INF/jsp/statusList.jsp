<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>

<fmt:setBundle basename="za.ac.unisa.lms.tools.assessmentcriteriaauth.ApplicationResources"/>

<sakai:html>
	<style>
		@media Print
		{
			h3 {font-size:9pt;width:100%}
			h2 {font-size:9pt;font-weight:bold;font-family:Verdana,Arial,Helvetica}
			table.itemSummary {font-size:7pt}
			table.listHier {font-size:7pt}	
			h1, h2, h3, h4, h5, h6 { page-break-after:avoid;page-break-inside:avoid }
			table { page-break-inside:avoid }
			.portletBody h3{font-size:9pt}
			.portletBody {width: 100%; margin: 0; float: none;}
			body {background: white}
			#content #container, #container2  {width: 100%; margin: 0; float: none;}
		}
	</style>
	<html:form action="/assessmentCriteriaAuth">
		<html:hidden property="currentPage" value="StatusList"/>
		<sakai:messages/>
		<sakai:messages message="true"/>
		<sakai:heading>
			<fmt:message key="heading.assessmentCriteriaStatusList"/>
		</sakai:heading>
		<sakai:instruction>
			<fmt:message key="page.note1"/>&nbsp;<fmt:message key="page.mandatory"/>
		</sakai:instruction>
		<sakai:group_table>	
			<tr>
				<td><fmt:message key="page.department"/>&nbsp;<fmt:message key="page.mandatory"/></td>
				<td>
					<sakai:group_table>
						<html:select name="assessmentCriteriaAuthForm" property="selectedStatusDepartment">
								<html:optionsCollection name="assessmentCriteriaAuthForm"  property="listStatusDepartment" value="code" label="description"/>
						</html:select>
					</sakai:group_table>
				</td>				
			</tr>			
			<tr>
				<td><fmt:message key="page.academicYear"/>&nbsp;<fmt:message key="page.mandatory"/></td>
				<td><html:text name="assessmentCriteriaAuthForm" property="academicYear" size="4" maxlength="4"/></td>
			</tr>
			<tr>
				<td><fmt:message key="page.semester"/>&nbsp;<fmt:message key="page.mandatory"/></td>
				<td>
					<sakai:group_table>
						<logic:iterate name="assessmentCriteriaAuthForm" property="listSemester" id="record" indexId="index">
							<tr>
								<td><html:radio property="semester" idName="record" value="code"></html:radio></td>
								<td><bean:write name="record" property="engDescription"/></td>
							</tr>
						</logic:iterate>
					</sakai:group_table>
				</td>				
			</tr>			
		</sakai:group_table>	
		<sakai:actions>
			<html:submit property="action">
					<fmt:message key="button.list"/>
			</html:submit>			
		</sakai:actions>
		<hr/>
		<sakai:flat_list>
		<tr>
			<th><fmt:message key="table.heading.studyUnit"/></th>
			<th><fmt:message key="table.heading.status"/></th>
			<th><fmt:message key="table.heading.lastActionDate"/></th>
			<th><fmt:message key="table.heading.primaryLecturer"/></th>
		</tr>
		<logic:iterate name="statusList" id="record" indexId="i">
			<tr>
				<td><bean:write name="record" property="studyUnit"/></td>
				<td><bean:write name="record" property="status"/></td>
				<td><bean:write name="record" property="lastActionDate"/></td>
				<td><bean:write name="record" property="primaryLecturer"/></td>
			</tr>
		</logic:iterate>
		</sakai:flat_list>	
		<sakai:actions>
			<html:button property="action" onclick="window.print()">
					<fmt:message key="button.printPage" />
			</html:button>
			<html:submit property="action">
					<fmt:message key="button.cancel"/>
			</html:submit>		
		</sakai:actions>		
	</html:form>
</sakai:html>