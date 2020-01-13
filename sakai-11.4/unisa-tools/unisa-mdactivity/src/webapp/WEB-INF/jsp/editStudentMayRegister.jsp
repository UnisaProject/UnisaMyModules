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

	<html:form action="/displaymdactivity">
	<html:hidden property="goto" value="display"/>
	<html:hidden property="frompage" value="editReg"/>

		<sakai:heading><fmt:message key="page.heading.UpdateStudentMayRegister"/></sakai:heading>
		<sakai:messages/>

			<sakai:group_table>
			<tr>
				<td><strong><fmt:message key="page.heading.studentnr"/></strong></td>
				<td colspan="3"><bean:write name="mdActivityForm" property="student.number"/></td>
			</tr><tr>
				<td><strong><fmt:message key="page.heading.name"/></strong></td>
				<td colspan="3"><bean:write name="mdActivityForm" property="student.name"/></td>
			</tr><tr>
				<td><strong><fmt:message key="page.heading.qualification"/></strong></td>
				<td colspan="3"><bean:write name="mdActivityForm" property="qualification.qualCode"/>&nbsp;&nbsp;&nbsp;(<bean:write name="mdActivityForm" property="qualification.qualDesc"/>)</td>				
			</tr><tr>
				<td><strong><fmt:message key="page.heading.speciality"/></strong></td>
				<td colspan="3"><bean:write name="mdActivityForm" property="qualification.specCode"/>&nbsp;&nbsp;&nbsp;(<bean:write name="mdActivityForm" property="qualification.specDesc"/>)</td>
			</tr><tr>
				<td><strong><fmt:message key="page.heading.studyunit"/></strong></td>
				<td><bean:write name="mdActivityForm" property="studyUnitCode"/></td>
				<td><strong><fmt:message key="page.heading.disType"/></strong></td>
				<td><bean:write name="mdActivityForm" property="disType"/></td>
			</tr><tr>
				<td><strong><fmt:message key="page.heading.distitle"/></strong></td>
				<td colspan="3"><bean:write name="mdActivityForm" property="disTitle"/></td>
			</tr>
			</sakai:group_table>
			<hr/>
			<sakai:group_table><tr>
				<td><strong><fmt:message key="page.heading.edit.permission"/></strong></td>	
				<td colspan="3"><html:radio property="inputPermission" value="N"/>No
				    <html:radio property="inputPermission" value="Y"/>Yes
				</td>
			</tr><tr>	    
				 <td><strong><fmt:message key="page.heading.edit.permission.reason"/></strong></td>   
				 <td colspan="3"><html:textarea name="mdActivityForm" property="regReason" rows="5" cols="50"/></td>	
			</tr>
		</sakai:group_table>
		<sakai:actions>
		    <html:button property="act" onclick="disabled=true;doAction()"><fmt:message key="button.save"/></html:button>			     
		    <html:submit property="act"><fmt:message key="button.cancel"/></html:submit>
		</sakai:actions>

		</html:form>
</sakai:html>
