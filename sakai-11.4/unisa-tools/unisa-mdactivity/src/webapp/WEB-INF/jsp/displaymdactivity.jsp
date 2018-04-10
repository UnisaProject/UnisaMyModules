<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setBundle basename="za.ac.unisa.lms.tools.mdactivity.ApplicationResources"/>

<sakai:html>
	<script language="javascript">
		function doAction(){
			document.mdActivityForm.action='displaymdactivity.do?act=save';
			document.mdActivityForm.submit();
		}
	</script>

	<sakai:tool_bar>
		<html:link href="displaymdactivity.do?act=addactivity">
			<fmt:message key="link.new"/>
		</html:link>
	</sakai:tool_bar>

	<html:form action="/displaymdactivity">

		<sakai:heading><fmt:message key="page.heading"/></sakai:heading>
		<sakai:messages/>

		<sakai:instruction>
			<fmt:message key="page.instruction3a"/>
			<br/><br/>
			<fmt:message key="page.instruction3b"/>
			</sakai:instruction>

		<sakai:group_table>
			<tr>
				<td><strong><fmt:message key="page.heading.studentnr"/></strong></td>
				<td colspan="3"><bean:write name="mdActivityForm" property="student.number"/></td>
			</tr><tr>
				<td><strong><fmt:message key="page.heading.name"/></strong></td>
				<td colspan="3"><bean:write name="mdActivityForm" property="student.name"/></td>
			</tr><tr>
			</tr><tr>
				<td><strong><fmt:message key="page.heading.qualification"/></strong></td>
				<td colspan="3"><bean:write name="mdActivityForm" property="qualificationCode"/></td>
			</tr><tr>
				<td><strong><fmt:message key="page.heading.studyunit"/></strong></td>
				<td><bean:write name="mdActivityForm" property="studyUnitCode"/></td>
				<td><strong><fmt:message key="page.heading.disType"/></strong></td>
				<td><bean:write name="mdActivityForm" property="disType"/></td>
			</tr><tr>
				<td><strong><fmt:message key="page.heading.distitle"/></strong></td>
				<td colspan="3"><bean:write name="mdActivityForm" property="disTitle"/></td>
			</tr><tr>
				<td><strong><fmt:message key="page.heading.permission"/></strong></td>						
				
				<td><html:radio property="regPermission" value="N"/>N
				    <html:radio property="regPermission" value="Y"/>Y
				    &nbsp;<logic:equal name="mdActivityForm" property="readOnly" value="false"><sakai:actions>
				    <html:button property="act" onclick="disabled=true;doAction()"><fmt:message key="button.save"/></html:button>			        
		           </sakai:actions></logic:equal>
		        </td>	
			</tr>
		</sakai:group_table>
		<sakai:flat_list>
		<tr>
			<th align="left"><fmt:message key="page.heading.promoter"/>&nbsp;</th>
			<th align="left"><fmt:message key="page.heading.department"/>&nbsp;</th>
			<th align="left"><fmt:message key="page.heading.supervisor"/>&nbsp;</th>
		</tr>
		<logic:notEmpty name="mdActivityForm" property="promotorList">
			<logic:iterate name="mdActivityForm" property="promotorList" id="promotor" indexId="i">
			<tr>
				<td><bean:write name="promotor" property="name"/>&nbsp;</td>
				<td><bean:write name="promotor" property="department"/>&nbsp;<bean:write name="promotor" property="departmentDesc"/></td>
				<td><bean:write name="promotor" property="supervisor"/>&nbsp;</td>
			</tr>
			</logic:iterate>
		</logic:notEmpty>
		</sakai:flat_list>

		<sakai:flat_list>
		<tr>
			<th align="left">&nbsp;</th>
			<th align="left"><fmt:message key="table.heading.user"/>&nbsp;</th>
			<th align="left"><fmt:message key="table.heading.activity"/>&nbsp;</th>
			<th align="left"><fmt:message key="table.heading.activitydate"/>&nbsp;</th>
			<th align="left"><fmt:message key="table.heading.feedbackdate"/>&nbsp;</th>
			<th align="left"><fmt:message key="table.heading.comments"/>&nbsp;</th>
		</tr>
		<logic:notEmpty name="mdActivityForm" property="activityRecords">
			<logic:iterate name="mdActivityForm" property="activityRecords" id="activity" indexId="i">
			<tr>
				<td><html:radio property="selectedActivityRecord" value='<%= "" + i.toString() + "" %>'>
						</html:radio></td>
				<td><bean:write name="activity" property="userName"/>&nbsp;</td>
				<td><bean:write name="activity" property="activityCode"/>&nbsp;-&nbsp;<bean:write name="activity" property="activityDescr"/></td>
				<td><bean:write name="activity" property="activityDate"/>&nbsp;</td>
				<td><bean:write name="activity" property="feedbackDate"/>&nbsp;</td>
				<td><bean:write name="activity" property="comments"/>&nbsp;</td>
			</tr>
			</logic:iterate>
		</logic:notEmpty>
		</sakai:flat_list>
<br/>
		<sakai:actions>
			<html:submit property="act"><fmt:message key="button.edit"/></html:submit>
			<html:submit property="act"><fmt:message key="button.cancel"/></html:submit>
		</sakai:actions>
		</html:form>
</sakai:html>
