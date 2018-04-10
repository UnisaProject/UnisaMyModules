<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ page import="java.util.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setBundle basename="za.ac.unisa.lms.tools.telecentre.ApplicationResources"/>
<sakai:html>
	<sakai:tool_bar>
		<html:link href="telecentre.do?action=displayStartingPage" >
			<fmt:message key="link.displayVisit.unselected"/>
		</html:link>
		<html:link href="telecentre.do?action=exportVisit" >
			<fmt:message key="link.exportVisitList.unselected"/>
		</html:link>
		<html:link href="telecentre.do?action=extendHours" >
			<fmt:message key="link.extendHours.unselected"/>
		</html:link>
		<html:link href="telecentre.do?action=createCentres" >
			<fmt:message key="profile.create.link.selected"/>
		</html:link>		
		<html:link href="telecentre.do?action=updateCentres" >
			<fmt:message key="profile.update.link.unselected"/>
		</html:link>
		<html:link href="telecentre.do?action=removeCentres" >
			<fmt:message key="profile.remove.link.unselected"/>
		</html:link>
		<html:link href="telecentre.do?action=activateCentres" >
			<fmt:message key="profile.activate.link.unselected"/>
		</html:link>
		<html:link href="telecentre.do?action=manageAdmins" >
			<fmt:message key="profile.manage.admin.unselected"/>
		</html:link>	
	</sakai:tool_bar>
	<sakai:messages/>
	<sakai:heading>
	     <fmt:message key="profile.create.heading"/>                
	</sakai:heading>
	<sakai:instruction>
	     <fmt:message key="profile.create.instruction"/>                
	</sakai:instruction>
	<html:form action="/telecentre.do">
	<sakai:group_table>
		<tr>
			<td><fmt:message key="profile.create.enterName"/></td>
			<td><html:text property="telecentreName" size="60" maxlength="70" value=""></html:text></td>
			<td></td>
		</tr>
		<tr>
			<td><fmt:message key="profile.create.enterEmail"/></td>
			<td><html:text property="telecentreEmail" size="40" maxlength="50" value=""></html:text></td>
			<td></td>
		</tr>
		<tr>
			<td><fmt:message key="profile.create.selectProvince"/></td>
			<td>
				<html:select name="telecentreForm" property="province">
					<html:option value="-1">Choose Province</html:option>
					<html:options collection="provinceList" property="value" labelProperty="label"/>
				</html:select>
			</td>
			<td></td>
		</tr>
		<tr>
			<td><fmt:message key="profile.create.selectActive"/></td>
			<td>
				<html:select name="telecentreForm" property="telecentreStatus">
					<html:options collection="activeStatusList" property="value" labelProperty="label"/>
				</html:select>
			</td>
		</tr>
		<tr>
			<td>
				<sakai:actions>
					<html:submit styleClass="button" property="action">
						<fmt:message key="telecentre.main"/>
					</html:submit>				
					<html:submit styleClass="button" property="action">
						<fmt:message key="profile.create.submitBtn"/>
					</html:submit>
				</sakai:actions>
			</td><td></td><td></td>
		</tr>
	</sakai:group_table>
	</html:form>
</sakai:html>
