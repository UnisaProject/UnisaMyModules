<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setBundle basename="za.ac.unisa.lms.tools.regdetails.ApplicationResources"/>

<sakai:html>
	<html:form action="/finalYearStudent">
	<html:hidden property="goto" value="3"/>
	
	<sakai:heading><fmt:message key="page.heading.finalyear"/></sakai:heading>
	<sakai:messages/>
	<sakai:messages message="true"/>
	<br>

	<sakai:group_table>
	  <tr>
	     <td><strong><fmt:message key="page.student"/></strong></td>
		 <td><bean:write name="regDetailsForm" property="studentNr" /></td>
	  </tr>
	  <tr>
	     <td><strong><fmt:message key="page.finalyear.qualification"/></strong></td>
		 <td><bean:write name="regDetailsForm" property="qual.qualCode" /></td>
	  </tr>
		<tr>
			<td colspan="3"><strong><fmt:message key="page.step2.heading.finalyear"/></strong></td>
		</tr>
		<tr>
			<td><fmt:message key="table.heading.code"/></td>
			<td><fmt:message key="table.heading.codeDesc"/></td>
			<td><fmt:message key="table.heading.semester"/></td>
			<td><fmt:message key="table.heading.language"/></td>
		</tr>
		<logic:iterate name="regDetailsForm" property="finalYearStudyUnits" id="studyUnit" indexId="index">
		  <tr>
			 <td><bean:write name="studyUnit" property="code"/>&nbsp;</td>
			 <td><bean:write name="studyUnit" property="desc"/>&nbsp;</td>
			 <td>
			 	<logic:notEqual name="regDetailsForm" property='<%= "finalYearStudyUnits[" + index + "].regPeriodStatic" %>' value="true">
			     	<html:select property='<%= "finalYearStudyUnits[" + index + "].regPeriod" %>'>
			 			<logic:equal name="regDetailsForm" property='<%= "finalYearStudyUnits[" + index + "].regPeriod1" %>' value="true">
							<html:option value="1">1st semester</html:option>
						</logic:equal>
						<logic:equal name="regDetailsForm" property='<%= "finalYearStudyUnits[" + index + "].regPeriod2" %>' value="true">
							<html:option value="2">2nd semester</html:option>
						</logic:equal>
						<logic:equal name="regDetailsForm" property='<%= "finalYearStudyUnits[" + index + "].regPeriod0" %>' value="true">
							<html:option value="0">Year module</html:option>
						</logic:equal>
						<!-- 
							<logic:equal name="regDetailsForm" property='<%= "finalYearStudyUnits[" + index + "].regPeriod6" %>' value="true">
								<html:option value="6">Cycle 2</html:option>
							</logic:equal>
						 -->
					</html:select>&nbsp;
				</logic:notEqual>
				<logic:equal name="regDetailsForm" property='<%= "finalYearStudyUnits[" + index + "].regPeriodStatic" %>' value="true">
					<logic:equal name="regDetailsForm" property='<%= "finalYearStudyUnits[" + index + "].regPeriod1" %>' value="true">
						1st semester
					</logic:equal>
					<logic:equal name="regDetailsForm" property='<%= "finalYearStudyUnits[" + index + "].regPeriod2" %>' value="true">
						2nd semester
					</logic:equal>
					<logic:equal name="regDetailsForm" property='<%= "finalYearStudyUnits[" + index + "].regPeriod0" %>' value="true">
						Year module
					</logic:equal>
					<!-- 
						<logic:equal name="regDetailsForm" property='<%= "finalYearStudyUnits[" + index + "].regPeriod6" %>' value="true">
							Cycle 2
						</logic:equal>
					 -->
				</logic:equal>
				</td><td>
					<logic:equal name="regDetailsForm" property='<%= "finalYearStudyUnits[" + index + "].language" %>' value="">
						<html:select property='<%= "finalYearStudyUnits[" + index + "].language" %>'>
							<html:option value="E2">English</html:option>
							<html:option value="A2">Afrikaans</html:option>
						</html:select>&nbsp;
					</logic:equal>
					<logic:equal name="regDetailsForm" property='<%= "finalYearStudyUnits[" + index + "].language" %>' value="E">
						English
					</logic:equal>
					<logic:equal name="regDetailsForm" property='<%= "finalYearStudyUnits[" + index + "].language" %>' value="A">
						Afrikaans
					</logic:equal>
					<logic:equal name="regDetailsForm" property='<%= "finalYearStudyUnits[" + index + "].language" %>' value="E2">
						<html:select property='<%= "finalYearStudyUnits[" + index + "].language" %>'>
							<html:option value="E2">English</html:option>
							<html:option value="A2">Afrikaans</html:option>
						</html:select>&nbsp;
					</logic:equal>
					<logic:equal name="regDetailsForm" property='<%= "finalYearStudyUnits[" + index + "].language" %>' value="A2">
						<html:select property='<%= "finalYearStudyUnits[" + index + "].language" %>'>
							<html:option value="A2">Afrikaans</html:option>
							<html:option value="E2">English</html:option>
						</html:select>&nbsp;
					</logic:equal>
				</td>
				<td><html:submit property='<%= "action.remove[" + index + "]" %>'><fmt:message key="button.remove"/></html:submit></td>
		  </tr>	
		</logic:iterate>
	</sakai:group_table>
	
	<sakai:actions>
			<html:submit property="action"><fmt:message key="button.continue"/></html:submit>
			<html:submit property="action"><fmt:message key="button.back"/></html:submit>
			<html:submit property="action"><fmt:message key="button.cancel"/></html:submit>
	</sakai:actions>
	
	</html:form>
</sakai:html> 