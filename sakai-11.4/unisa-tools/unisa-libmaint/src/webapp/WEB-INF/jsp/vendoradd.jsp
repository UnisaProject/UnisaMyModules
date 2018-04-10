<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<fmt:setBundle basename="za.ac.unisa.lms.tools.libmaint.ApplicationResources"/>

<sakai:html>
<html:form action="/maintenance"> 
	<html:hidden property="atstep" value="vendor"/>

	<sakai:group_heading>
		<fmt:message key="heading"/> <br>
		<fmt:message key="vendor.add"/>
	</sakai:group_heading>
	</p>
	
	<p>
	<sakai:messages/>
	<sakai:messages message="true"/>
	</p>
	
	<table>
		<tr>
			<td><fmt:message key="vendor"/> <sakai:required/> </td>
			<td><html:text property="vendor" size="50" maxlength="50"/></td>
		</tr>
		<tr>
			<td><fmt:message key="url.oncampus"/> <sakai:required/> </td>
			<td><html:text property="onCampusURL" size="50" maxlength="150"/></td>
		</tr>
		<tr>
			<td><fmt:message key="url.offcampus"/> <sakai:required/> </td>
			<td><html:text property="offCampusURL" size="50" maxlength="150"/></td>
		</tr>	
		<tr>
			<td><fmt:message key="logo"/> <fmt:message key="vendor.url"/><sakai:required/> </td>
			<td>
				<fmt:message key="logo.url"/>
				<html:text property="logoURL" size="50" maxlength="150"/>
			</td>
		</tr>	
		<tr>
			<td><fmt:message key="vendor.logo"/> <sakai:required/> </td>
			<td>
			<html:text property="logoFileName" size="50" maxlength="50"/></td>
		</tr>	
		<tr>
			<td><fmt:message key="enabled"/> <sakai:required/> </td>
			<td>
				<html:select property="enabled">
					<html:options collection="enabledOptionList" property="value" labelProperty="label"/>
				</html:select>
			</td>
		</tr>
	</table>
	
	</p>
	<sakai:actions>
		<html:submit property="act">
			<fmt:message key="button.save"/>
		</html:submit>
		<html:submit property="act">
			<fmt:message key="button.cancel"/>
		</html:submit>
	</sakai:actions>
</html:form>
</sakai:html>