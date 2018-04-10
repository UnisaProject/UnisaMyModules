
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setBundle basename="za.ac.unisa.lms.tools.studentregistration.ApplicationResources"/>

<sakai:html>

<%-- Toolbar --%>
<sakai:tool_bar>
	<html:link href="studentRegistration.do?action=applyForStudentNr">
		<fmt:message key="page.main.link.apply"/>
	</html:link>
	<html:link href="studentRegistration.do?action=walkThroughStep1&type=U">
		<fmt:message key="page.main.link.undergrad"/>
	</html:link>
	<html:link href="studentRegistration.do?action=walkThroughStep1&type=P">
		<fmt:message key="page.main.link.postgrad"/>
	</html:link>
	<html:link href="studentRegistration.do?action=walkThroughStep1&type=S">
		<fmt:message key="page.main.link.short"/>
	</html:link>
</sakai:tool_bar>


<br/>

	<html:form action="/studentRegistration">
		<html:hidden property="page" value="walk1a"/>

		<sakai:messages/>
		<sakai:messages message="true"/>

		<logic:equal name="studentRegistrationForm" property="registrationType" value="U">
			<sakai:heading>
				<fmt:message key="page.walk.heading.under"/><br/>
			</sakai:heading>
		</logic:equal>
		<logic:equal name="studentRegistrationForm" property="registrationType" value="P">
			<sakai:heading>
				<fmt:message key="page.walk.heading.post"/><br/>
			</sakai:heading>
		</logic:equal>
		<logic:equal name="studentRegistrationForm" property="registrationType" value="S">
			<sakai:heading>
				<fmt:message key="page.walk.heading.short"/><br/>
			</sakai:heading>
		</logic:equal>
	<br/>

	<sakai:flat_list>
		<tr>
			<td width="5%"><fmt:message key="page.walk.question1"/></td>
			<td align="left" width="5%"><html:radio property="question1" value="Y"/>Yes</td>
		</tr><tr>
			<td width="5%">&nbsp;</td>
			<td align="left" width="5%"><html:radio property="question1" value="N"/>No</td>
		</tr>
	</sakai:flat_list>

	 <sakai:actions>
			<html:submit property="action"><fmt:message key="button.continue"/></html:submit>
			<html:submit property="action"><fmt:message key="button.cancel"/></html:submit>
		</sakai:actions>

	</html:form>

</sakai:html>