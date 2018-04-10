<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c_rt" %>

<fmt:setBundle basename="za.ac.unisa.lms.tools.satbook.ApplicationResources"/>

<sakai:html>

	<html:form action="/satbookAdmin.do">

    <sakai:heading><fmt:message key="adminassistanttypeedit.heading"/></sakai:heading>

	<sakai:messages/>
	<sakai:messages message="true"/>

	<sakai:instruction> <fmt:message key="adminassistanttypeedit.instruction"/> </sakai:instruction>
	<sakai:instruction> <fmt:message key="info.required"/> <sakai:required/> </sakai:instruction>

	<sakai:group_table>
		<tr>
			<td><fmt:message key="adminassistanttypeedit.assistanttype"/> <sakai:required/></td>
			<td>
				<html:text name="assType" property="assTypeName" size="30" maxlength="40"> </html:text>
			</td>
		</tr><tr>
			<td><fmt:message key="adminclassrooms.tableheading.active"/></td>
			<td>
				<html:radio name="assType" property="assTypeAct" value="true">
					Yes
				</html:radio>
				<html:radio name="assType" property="assTypeAct" value="false">
					No
				</html:radio>
			</td>
		</tr>
	</sakai:group_table>

	<sakai:actions>
		<html:submit property="action">
			<fmt:message key="adminassistanttype.button.save"/>
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