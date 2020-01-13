<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setBundle basename="za.ac.unisa.lms.tools.mdregistrations.ApplicationResources"/>

<sakai:html>

<!-- Form -->
	<html:form action="/mdregistrations">
	<html:hidden property="page" value="step3"/>

		<sakai:messages/>
		<sakai:messages message="true"/>

		<sakai:group_heading><fmt:message key="md.heading.p3"/></sakai:group_heading>

		<sakai:group_table>
 		<tr></tr>
		<tr></tr>

		<logic:notEmpty name="mdRegistrationsForm" property="additionalStudyUnits">
		<tr>
			<td><strong><fmt:message key="md.page3.studyunit"/></strong></td>
			<td><strong><fmt:message key="md.page3.description"/></strong></td>
			<td><strong><fmt:message key="md.page3.semester"/></strong></td>
			<td><strong><fmt:message key="md.page3.language"/></strong></td>
			<td>&nbsp;</td>
		</tr>
		<logic:iterate name="mdRegistrationsForm" property="additionalStudyUnits" id="additionalStudyUnits" indexId="index">
			 <logic:present name="additionalStudyUnits" property="code">
			 	<tr>
			 		<td><bean:write name="additionalStudyUnits" property="code"/>&nbsp;&nbsp;</td>
			 		<td><bean:write name="additionalStudyUnits" property="desc"/>&nbsp;</td>
					<logic:equal name="mdRegistrationsForm" property='<%= "additionalStudyUnits[" + index + "].regPeriod" %>' value="closed">
			 				<td><fmt:message key="md.page3.noopen"/></td>
			 				<td colspan="2">&nbsp;</td>
			 		</logic:equal>
			 		
			 		<logic:notEqual name="mdRegistrationsForm" property='<%= "additionalStudyUnits[" + index + "].regPeriod" %>' value="closed">
			 		<td>
			 			<logic:notEqual name="mdRegistrationsForm" property='<%= "additionalStudyUnits[" + index + "].regPeriodStatic" %>' value="true">
			 				<html:select property='<%= "additionalStudyUnits[" + index + "].regPeriod" %>'>
			 					<logic:equal name="mdRegistrationsForm" property='<%= "additionalStudyUnits[" + index + "].regPeriod1" %>' value="true">
									<html:option value="1">1st semester</html:option>
								</logic:equal>
								<logic:equal name="mdRegistrationsForm" property='<%= "additionalStudyUnits[" + index + "].regPeriod2" %>' value="true">
									<html:option value="2">2nd semester</html:option>
								</logic:equal>
								<logic:equal name="mdRegistrationsForm" property='<%= "additionalStudyUnits[" + index + "].regPeriod0" %>' value="true">
									<html:option value="0">Year module</html:option>
								</logic:equal>
							</html:select>&nbsp;
						</logic:notEqual>
						<logic:equal name="mdRegistrationsForm" property='<%= "additionalStudyUnits[" + index + "].regPeriodStatic" %>' value="true">
								<logic:equal name="mdRegistrationsForm" property='<%= "additionalStudyUnits[" + index + "].regPeriod1" %>' value="true">
									1st semester
								</logic:equal>
								<logic:equal name="mdRegistrationsForm" property='<%= "additionalStudyUnits[" + index + "].regPeriod2" %>' value="true">
									2nd semester
								</logic:equal>
								<logic:equal name="mdRegistrationsForm" property='<%= "additionalStudyUnits[" + index + "].regPeriod0" %>' value="true">
									Year module
								</logic:equal>
						</logic:equal>
					<td>
						<logic:equal name="mdRegistrationsForm" property='<%= "additionalStudyUnits[" + index + "].language" %>' value="">
							<html:select property='<%= "additionalStudyUnits[" + index + "].language" %>'>
								<html:option value="E2">English</html:option>
								<html:option value="A2">Afrikaans</html:option>
							</html:select>&nbsp;
						</logic:equal>
						<logic:equal name="mdRegistrationsForm" property='<%= "additionalStudyUnits[" + index + "].language" %>' value="E">
							English
						</logic:equal>
						<logic:equal name="mdRegistrationsForm" property='<%= "additionalStudyUnits[" + index + "].language" %>' value="A">
							Afrikaans
						</logic:equal>
						<logic:equal name="mdRegistrationsForm" property='<%= "additionalStudyUnits[" + index + "].language" %>' value="E2">
							<html:select property='<%= "additionalStudyUnits[" + index + "].language" %>'>
								<html:option value="E2">English</html:option>
								<html:option value="A2">Afrikaans</html:option>
							</html:select>&nbsp;
						</logic:equal>
						<logic:equal name="mdRegistrationsForm" property='<%= "additionalStudyUnits[" + index + "].language" %>' value="A2">
							<html:select property='<%= "additionalStudyUnits[" + index + "].language" %>'>
								<html:option value="A2">Afrikaans</html:option>
								<html:option value="E2">English</html:option>
							</html:select>&nbsp;
						</logic:equal>
					</td>
			 		</logic:notEqual>
			 		
			 		
				</tr>
			</logic:present>
		</logic:iterate>
		</logic:notEmpty>



		</tr>
		</sakai:group_table>

		<sakai:actions>
			<html:submit property="action"><fmt:message key="button.continue"/></html:submit>
			<html:submit property="action"><fmt:message key="button.back"/></html:submit>
			<html:submit property="action"><fmt:message key="button.cancel"/></html:submit>
		</sakai:actions>
	</html:form>
</sakai:html>