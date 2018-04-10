<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<fmt:setBundle basename="za.ac.unisa.lms.tools.maintainstaff.ApplicationResources"/>
<sakai:html>
<html:form action="person">
	<html:hidden property="atstep" value="viewChangeLogs"/>
	<sakai:group_heading>
		<fmt:message key="course.logs.info"/> 
		<bean:write name="personForm" property="personNetworkCode"/>
	</sakai:group_heading>
	<sakai:messages/>
	<sakai:messages message="true"/>
		
	<p>
	<table>
		<tr>
			<td>
				<logic:equal name="personForm" property="selectedView" value="L"> 
					<fmt:message key="person.add.regperiod"/> 
				</logic:equal>
				<logic:equal name="personForm" property="selectedView" value="E"> 
					<fmt:message key="person.add.experiod"/> 
				</logic:equal>
			</td>
			<td>
				<html:select name="personForm" property='yearList'>
					<html:options collection="yearOptions" property="value" labelProperty="label"/>
				</html:select>
				<html:select name="personForm" property='periodList'>
					<html:options collection="periodOptions" property="value" labelProperty="label"/>
				</html:select>
			</td>
		</tr>
	</table>
	<p>
	<sakai:actions>
  		<html:submit property="act">
	    	<fmt:message key="button.personLogsDisplay"/>
		</html:submit>
	</sakai:actions> 
	<table>
		<logic:notEmpty name="personForm" property="logsList">
		<tr bgcolor="#eeeeee">
			<td width="15%">#</td>
			<td width="15%">Date</td>
			<td width="15%">Log</td>
		</tr>
		
			<logic:iterate name="personForm" property="logsList" id="c" indexId="cindex">
				<tr>
					<td><bean:write name="c" property="count"/></td>
					<td><bean:write name="c" property="audDate"/></td>
					<td><bean:write name="c" property="audLog"/></td>
				</tr>
			</logic:iterate>
		</logic:notEmpty>
		
	</table>
	
	<sakai:actions>
		<html:submit property="act">
			<fmt:message key="button.back"/>
		</html:submit>
		<html:submit property="act">
			<fmt:message key="button.cancel"/>
		</html:submit>
	</sakai:actions>	
	</p>	
	
</html:form>
</sakai:html>