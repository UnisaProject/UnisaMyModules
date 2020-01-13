<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setBundle basename="za.ac.unisa.lms.tools.regdetails.ApplicationResources"/>

<sakai:html>

<!-- Form -->
<html:form action="/additions" >
	<html:hidden property="goto" value="7"/>

		<sakai:messages/>
		<sakai:messages message="true"/>
		<sakai:heading><fmt:message key="page.heading.additions"/></sakai:heading>
		
		<sakai:group_heading><fmt:message key="page.selfhelp.error"/></sakai:group_heading>
		<sakai:group_table>
		<tr>
		    <td><strong><fmt:message key="page.selfhelp.info1"/></strong></td>
		</tr><tr>
			<td><strong><font color="red"><bean:write name="regDetailsForm" property="selfhelpErrorMsg"/></font>
				</strong></td>
		</tr><tr>
			<td><strong><fmt:message key="page.selfhelp.info2"/></strong></td>
		</tr><tr>
		    <td><strong><fmt:message key="page.selfhelp.info3"/></strong></td>
		</tr>

	</sakai:group_table>

	<sakai:actions>
			<html:submit property="action"><fmt:message key="button.back"/></html:submit>
			<html:submit property="action"><fmt:message key="button.studentaffairs"/></html:submit>
		</sakai:actions>
</html:form>

</sakai:html>