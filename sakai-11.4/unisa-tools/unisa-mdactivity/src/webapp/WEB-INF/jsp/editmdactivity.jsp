<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setBundle basename="za.ac.unisa.lms.tools.mdactivity.ApplicationResources"/>

<sakai:html>
<script language="javascript">
	function doAction(){
		document.mdActivityForm.action='displaymdactivity.do?act=updateActivity';
		document.mdActivityForm.submit();
	}
</script>

	<html:form action="/displaymdactivity">
	<html:hidden property="goto" value="display"/>
	<html:hidden property="frompage" value="edit"/>

		<sakai:heading><fmt:message key="page.heading.update"/></sakai:heading>
		<sakai:messages/>

		<sakai:instruction><fmt:message key="page.instruction5"/></sakai:instruction>

		<sakai:group_table>
			<tr>
				<td><strong><fmt:message key="page.heading.activity"/></strong></td>
				<td colspan="2"><bean:write name="mdActivityForm" property="updateActivity.activityCode"/>&nbsp;-&nbsp;<bean:write name="mdActivityForm" property="updateActivity.activityDescr"/></td>
			</tr><tr>
				<td><strong><fmt:message key="page.heading.activitydate"/></strong></td>
				<td colspan="2"><bean:write name="mdActivityForm" property="updateActivity.activityDate"/></td>
			</tr><tr>
				<td><strong><fmt:message key="page.heading.feedbackdate"/></strong></td>
				<logic:equal name="mdActivityForm" property="updateFeedbackDate" value="true">
					<td  colspan="2"><html:text name="mdActivityForm" property="updateActivity.feedbackDate" size="10" maxlength="8"/>&nbsp;&nbsp;&nbsp;<strong><fmt:message key="page.format"/></strong></td>				
				</logic:equal>
				<logic:notEqual name="mdActivityForm" property="updateFeedbackDate" value="true">
					<td colspan="2"><bean:write name="mdActivityForm" property="updateActivity.feedbackDate"/></td>
				</logic:notEqual>
				</tr><tr>
				<td><strong><fmt:message key="page.heading.previouscomment"/></strong></td>
				<td colspan="2"><bean:write name="mdActivityForm" property="updateActivity.comments"/></td>
			</tr><tr>
				<td><strong><fmt:message key="page.heading.comment"/></strong></td>
				<td colspan="2"><html:textarea name="mdActivityForm" property="updateActivity.extraComments" rows="6" cols="100"/></td>
		</sakai:group_table>

<br/>
		<sakai:actions>
			<html:button property="act" onclick="disabled=true;doAction()"><fmt:message key="button.update"/></html:button>
			<html:submit property="act"><fmt:message key="button.cancel"/></html:submit>
		</sakai:actions>
		</html:form>
</sakai:html>
