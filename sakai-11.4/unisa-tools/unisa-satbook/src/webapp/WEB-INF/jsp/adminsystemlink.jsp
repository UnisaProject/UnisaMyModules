<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c_rt" %>

<fmt:setBundle basename="za.ac.unisa.lms.tools.satbook.ApplicationResources"/>
<sakai:html>
	<html:form action="/satbookAdmin.do">
		<logic:equal name="adminForm" property="systemID" value="2">
			<fmt:message key="dailyview.heading.venue"/>
		<sakai:tool_bar>
		<html:link href="satbookMonthly.do?action=monthlyview">
			<fmt:message key="button.to.monthlyview"/>
		</html:link>
		<br>
		<html:link href="satbookMonthly.do?action=adminLink">
				<fmt:message key="adminsystemlink.heading2"/>
		</html:link>
		<html:link href="satbookAdmin.do?action=adminVenues">
			<fmt:message key="adminvenues.heading"/>
		</html:link>
		<html:link href="satbookAdmin.do?action=adminmaterial">
			<fmt:message key="adminmaterial.heading"/>
		</html:link>
		<html:link href="satbookAdmin.do?action=adminBookingType">
			<fmt:message key="adminbookingtype.heading"/>
		</html:link>
		<br>
		<html:link href="satbookAdmin.do?action=sendgroupemail">
			<fmt:message key="sendgroupemail.heading"/>
		</html:link>
		<html:link href="satbookAdmin.do?action=emailschedule">
			<fmt:message key="emailschedule.heading"/>
		</html:link>
		</sakai:tool_bar>
	</logic:equal>
	<logic:equal name="adminForm" property="systemID" value="1">
		<sakai:tool_bar>
		<html:link href="satbookMonthly.do?action=monthlyview">
			<fmt:message key="button.to.monthlyview"/>
		</html:link>
		<br>
		<html:link href="satbookAdmin.do?action=adminlecturers">
			<fmt:message key="adminlecturers.heading"/>
		</html:link>
		<html:link href="satbookAdmin.do?action=admininstitutions">
			<fmt:message key="admininstitutions.heading"/>
		</html:link>
		<html:link href="satbookAdmin.do?action=admininstitutiondays">
			<fmt:message key="admininstitutiondays.heading1"/>
		</html:link>
		<html:link href="satbookAdmin.do?action=adminclassrooms">
			<fmt:message key="adminclassrooms.heading"/>
		</html:link>
		<html:link href="satbookAdmin.do?action=adminmaterial">
			<fmt:message key="adminmaterial.heading"/>
		</html:link>
		<html:link href="satbookAdmin.do?action=adminassistanttype">
			<fmt:message key="adminassistanttype.heading"/>
		</html:link>
		<html:link href="satbookAdmin.do?action=adminassistants">
			<fmt:message key="adminassistants.heading"/>
		</html:link>
		<br>
		<html:link href="satbookAdmin.do?action=sendgroupemail">
			<fmt:message key="sendgroupemail.heading"/>
		</html:link>
		<html:link href="satbookAdmin.do?action=emailschedule">
			<fmt:message key="emailschedule.heading"/>
		</html:link>
		</sakai:tool_bar>
  	</logic:equal>
	<sakai:messages/>
	<sakai:messages message="true"/>

    <sakai:heading><fmt:message key="adminsystemlink.instruction1"/></sakai:heading>
    <br>
	<sakai:instruction><fmt:message key="adminsystemlink.instruction2"/></sakai:instruction>
	<sakai:flat_list>
	<tr>
		<td>
			<fmt:message key="adminsystemlink.tableheading.administrator"/><sakai:required/>
		</td><td>
			<fmt:message key="adminsystemlink.tableheading.system"/><sakai:required/>
		</td>
	</tr>		
	<logic:iterate name="adminForm" property="adminList" id="adminLinkForm" indexId="i">
		<tr>
			<td>
				<bean:write name="adminLinkForm" property="administrator"/>
			</td>
			<td>
				<!--<bean:write name="adminLinkForm" property="system"/>-->
				<html:select name="adminForm" property='<%= "recordIndexed["+i+"].system" %>'>
						<html:options collection="systemList" property="value" labelProperty="label"/> 
				</html:select>
			</td>
		</tr>
	</logic:iterate>
	</sakai:flat_list>
	 <sakai:actions>
  		<html:submit property="action">
	        <fmt:message key="adminsystemlink.button.save"/>
		</html:submit>
	  </sakai:actions> 	
</html:form>
</sakai:html>