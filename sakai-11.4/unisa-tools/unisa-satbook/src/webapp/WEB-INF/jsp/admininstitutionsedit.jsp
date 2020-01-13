<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c_rt" %>

<fmt:setBundle basename="za.ac.unisa.lms.tools.satbook.ApplicationResources"/>

<sakai:html>

	<html:form action="/satbookAdmin.do">
	<sakai:messages/>
	<sakai:messages message="true"/>

    <sakai:heading><fmt:message key="admininstitutionsedit.heading"/></sakai:heading>
    <sakai:instruction><fmt:message key="admininstitutionsedit.instruction"/></sakai:instruction>
   	<sakai:instruction> <fmt:message key="info.required"/> <sakai:required/> </sakai:instruction>
	</p>
	<sakai:group_table>
		<tr>
			<td>
				<fmt:message key="admininstitutionsedit.institution"/> <sakai:required/>
			</td>
			<td><html:text name="institution" property="instName" size="50"></html:text></td>
		</tr>
	</sakai:group_table>

	<sakai:actions>
		<html:submit property="action">
			<fmt:message key="admininstitutionsedit.button.save"/>
		</html:submit>
		<html:submit property="action">
			<fmt:message key="admininstitutionsedit.button.back"/>
		</html:submit>
	</sakai:actions>
	</html:form>
</sakai:html>
