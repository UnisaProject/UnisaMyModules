<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c_rt" %>

<fmt:setBundle basename="za.ac.unisa.lms.tools.satbook.ApplicationResources"/>

<sakai:html>

	<html:form action="/satbookAdmin.do">

    <sakai:heading><fmt:message key="adminassistantsedit.heading"/></sakai:heading>

	<sakai:messages/>
	<sakai:messages message="true"/>

	<sakai:instruction> <fmt:message key="adminassistantsedit.instruction"/> </sakai:instruction>
	<sakai:instruction> <fmt:message key="info.required"/> <sakai:required/></sakai:instruction>

	<sakai:group_table>
		<tr>
			<td><fmt:message key="adminassistantsedit.type"/><sakai:required/></td>
			<td>
				<html:select name="assistant" property="assistantTypeId">
					<html:options collection="assistantTypeList" property="value" labelProperty="label"/>
				</html:select>
			</td>
		</tr><tr>
			<td><fmt:message key="adminassistantsedit.name"/><sakai:required/></td>
			<td><html:text name="assistant" property="assistantName" size="30" maxlength="40"> </html:text>
			</td>
		</tr><tr>
			<td><fmt:message key="adminassistantsedit.email"/></td>
			<td><html:text name="assistant" property="assistantEmail" size="30" maxlength="60"> </html:text>	</td>
		</tr>
	</sakai:group_table>

	<sakai:actions>
		<html:submit property="action">
			<fmt:message key="adminassistants.button.save"/>
		</html:submit>

		<html:submit property="action">
			<fmt:message key="button.monthlyview.adminview"/>
		</html:submit>
		<html:submit property="action">
			<fmt:message key="button.to.monthlyview"/>
		</html:submit>
	</sakai:actions>

	</html:form>
</sakai:html>