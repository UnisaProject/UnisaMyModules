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
		<html:hidden property="frompage" value="add"/>

		<sakai:heading><fmt:message key="page.heading.add"/></sakai:heading>
		<sakai:messages/>

		<sakai:instruction><fmt:message key="page.instruction4"/></sakai:instruction>

		<sakai:group_table>
			<tr>
				<td><strong><fmt:message key="page.heading.activity"/></strong></td>
				<td><html:select property="selectedActivity">
				<html:options collection="list" property="value" labelProperty="label"/>
				</html:select></td>
				<td><fmt:message key="page.note.activity"/></td>
			</tr><tr>
				<td><strong><fmt:message key="page.heading.activitydate"/></strong></td>
				<td><html:text name="mdActivityForm" property="newActivity.activityDate" size="10" maxlength="8"/></td>
				<td><strong><fmt:message key="page.format"/></strong></td>
			</tr><tr>
				<td colspan="3"><sakai:instruction><fmt:message key="page.instruction6"/></sakai:instruction></td>
			</tr><tr>
				<td><strong><fmt:message key="page.heading.comments"/></strong></td>
				<td colspan="2"><html:textarea name="mdActivityForm" property="newActivity.comments" rows="6" cols="100" /></td>
			</tr>	
		</sakai:group_table>
<br/>
		<sakai:actions>
			<html:button property="act" onclick="disabled=true;doAction()"><fmt:message key="button.update"/></html:button>
			<html:submit property="act"><fmt:message key="button.cancel"/></html:submit>
		</sakai:actions>
		</html:form>
</sakai:html>
