<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setBundle basename="za.ac.unisa.lms.tools.mdregistrations.ApplicationResources"/>

<sakai:html>

<!-- Form -->
	<html:form action="/mdregistrations">
	<html:hidden property="page" value="step4"/>

		<sakai:messages/>
		<sakai:messages message="true"/>

		<sakai:group_heading><fmt:message key="md.heading.p2"/></sakai:group_heading>
		<fmt:message key="md.page4.instruction"/></br></br>
		<fmt:message key="md.page4.instruct2"/>
		<sakai:group_heading><fmt:message key="md.heading.p4"/></sakai:group_heading>
		
		<sakai:group_table>
		<tr>
			<td colspan="6"><strong><fmt:message key="md.page4.studyunits"/>&nbsp;</strong></td>
		</tr><tr>
		
		<logic:notEmpty name="mdRegistrationsForm" property="additionalStudyUnits">
		<tr>
			<td><strong><fmt:message key="md.page3.studyunit"/></strong></td>
			<td><strong><fmt:message key="md.page3.description"/></strong></td>
			<td><strong><fmt:message key="md.page3.semester"/></strong></td>
			<td><strong><fmt:message key="md.page3.language"/></strong></td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
		</tr>
		<logic:iterate name="mdRegistrationsForm" property="additionalStudyUnits" id="additionalStudyUnits" indexId="index">
			 <logic:present name="additionalStudyUnits" property="code">
			 	<tr>
			 		<td><bean:write name="additionalStudyUnits" property="code"/>&nbsp;&nbsp;</td>
			 		<td><bean:write name="additionalStudyUnits" property="desc"/>&nbsp;</td>
				 	<td><bean:write name="additionalStudyUnits" property="regPeriod"/>&nbsp;</td>
		 			<logic:equal name="mdRegistrationsForm" property='<%= "additionalStudyUnits[" + index + "].language" %>' value="E2">
							<td>English</td>
					</logic:equal>
		 			<logic:equal name="mdRegistrationsForm" property='<%= "additionalStudyUnits[" + index + "].language" %>' value="A2">
							<td>Afrikaans</td>
					</logic:equal>
<!--			 	<td><bean:write name="additionalStudyUnits" property="regPeriod"/>&nbsp;</td>
					<td><bean:write name="additionalStudyUnits" property="language"/>&nbsp;</td>  -->
				</tr>
			</logic:present>
		</logic:iterate>
		</logic:notEmpty>
		</tr><tr>
		</tr><tr>
			<td colspan="6"><fmt:message key="md.page4.payment"/></td>
		</tr><tr>
			<td colspan="6"><fmt:message key="md.page4.payment.inf"/></td>
		</tr><tr>
			<td colspan="6"><strong><fmt:message key="md.page4.declare"/></strong></td>
		</tr><tr>
			<td colspan="6"><fmt:message key="md.page4.declare1"/></td>
		</tr><tr>
			<td colspan="6"><fmt:message key="md.page4.declare2"/></td>
		</tr><tr>
			<td colspan="6"><fmt:message key="md.page4.declare3"/></td>
		</tr><tr>
		</tr><tr>
			<td colspan="6"><fmt:message key="md.page4.declare4"/></td>
		</tr><tr>
		</tr><tr>
			<td colspan="4">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<html:radio property ="agree" value="Y"/><fmt:message key="md.page4.agree"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<html:radio property ="agree" value="N"/><fmt:message key="md.page4.disagree"/>
			</td>
		</tr><tr>
			<td colspan="2"></td>
		</tr>
		</sakai:group_table>

		<sakai:actions>
			<html:submit property="action"><fmt:message key="button.submitreg"/></html:submit>
			<html:submit property="action"><fmt:message key="button.back"/></html:submit>
			<html:submit property="action"><fmt:message key="button.cancel"/></html:submit>
		</sakai:actions>
	</html:form>
</sakai:html>