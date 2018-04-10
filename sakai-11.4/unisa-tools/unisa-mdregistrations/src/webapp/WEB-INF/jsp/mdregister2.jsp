<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setBundle basename="za.ac.unisa.lms.tools.mdregistrations.ApplicationResources"/>

<sakai:html>

<!-- Form -->
	<html:form action="/mdregistrations">
	<html:hidden property="page" value="step2"/>

		<sakai:messages/>
		<sakai:messages message="true"/>

		<sakai:group_heading><fmt:message key="md.heading.p2"/></sakai:group_heading>

		<sakai:group_table>
		
		<tr>
			<td><strong><fmt:message key="md.page2.rules"/></strong></td></tr>

		<logic:iterate name="mdRegistrationsForm" property="qualifRules" id="record" indexId="index"> 
		<tr>
			<td><bean:write name="mdRegistrationsForm" property='<%="qualifRules[" + index + "]"%>'/></td>
		</tr>
		</logic:iterate>		
		
		<logic:equal name="mdRegistrationsForm" property="studyUnitAddition" value="true">
			<tr>
				<td><br/><strong><fmt:message key="md.page2.currentReg"/></strong></td>
			</tr>
			<sakai:flat_list >
			<tr>
				<th align="left"><fmt:message key="md.page2.studyunit"/>&nbsp;</th>
				<th align="left"><fmt:message key="md.page2.description"/>&nbsp;</th>
				<th align="left"><fmt:message key="md.page2.statusCode"/>&nbsp;</th>
				<th align="left"><fmt:message key="md.page2.language"/>&nbsp;</th>
			</tr>
			<logic:notEmpty name="mdRegistrationsForm" property="currentRegStudyUnitsList">
				<logic:iterate name="mdRegistrationsForm" property="currentRegStudyUnitsList" id="studyUnitRecord" indexId="i">
				<tr>
					<td><bean:write name="studyUnitRecord" property="code"/>&nbsp;</td>
					<td><bean:write name="studyUnitRecord" property="desc"/></td>
					<td><bean:write name="studyUnitRecord" property="statusDesc"/>&nbsp;</td>
					<td><bean:write name="studyUnitRecord" property="languageDesc"/>&nbsp;</td>
				</tr>
				</logic:iterate>
			</logic:notEmpty>
			</sakai:flat_list>		
		</logic:equal>
		
		</sakai:group_table>
		<sakai:group_table>
		<tr>
			<td><br/><strong><fmt:message key="md.page2.studyunits"/></strong></td>
		</tr>
		<tr></tr>
		<logic:equal name="mdRegistrationsForm" property="listType" value="S">
	    	<logic:iterate name="mdRegistrationsForm" property="selectedAdditionalStudyUnits" id="studyUnit" indexId="index">
			<tr><td colspan="1">
				<html:select property='<%= "selectedAdditionalStudyUnits[" + index + "]" %>'>
					<html:options collection="regStudyUnits" property="value" labelProperty="label" />
				</html:select>
			</td></tr>
			</logic:iterate>
		</logic:equal>
		<logic:equal name="mdRegistrationsForm" property="listType" value="T">
	 		<logic:iterate name="mdRegistrationsForm" property="selectedAdditionalStudyUnits" id="studyUnit" indexId="index">
			<tr><td colspan="5"><html:text property='<%= "selectedAdditionalStudyUnits[" + index + "]" %>' size="10" maxlength="7"/>
			</td></tr>
			</logic:iterate>
		</logic:equal>
		
		</sakai:group_table>		

		<sakai:actions>
			<html:submit property="action"><fmt:message key="button.continue"/></html:submit>
			<html:submit property="action"><fmt:message key="button.back"/></html:submit>
			<html:submit property="action"><fmt:message key="button.cancel"/></html:submit>
		</sakai:actions>
	</html:form>
</sakai:html>