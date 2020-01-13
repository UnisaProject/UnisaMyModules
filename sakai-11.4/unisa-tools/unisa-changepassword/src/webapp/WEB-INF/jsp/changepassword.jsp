<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ page import="java.util.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setBundle basename="za.ac.unisa.lms.tools.changepassword.ApplicationResources"/>

<sakai:html>
<html:form action="/changepassword.do" >
	<script language="javascript">
		function windowOpen(){
			var url="<bean:write name="changePasswordForm" property="mylifePath"/>";
			window.open(url,'mywindow');
			}
		</script>
	<sakai:heading><fmt:message key="function.heading"/></sakai:heading> 
	<sakai:messages/>
	<sakai:messages message="true"/>
	<sakai:instruction>
	<fmt:message key="function.instruction"/> <br> <br>

	<fmt:message key="function.instruction.required"/>
	</sakai:instruction>
	<sakai:group_table>
		<tr>
			<td><fmt:message key="function.oldpassword"/></td>
			<td><html:password property="oldpassword"/>
		</tr>
		<tr>
			<td><fmt:message key="function.newpassword"/></td>
			<td><html:password property="newpassword"/></td>
		</tr>
		<tr>
			<td><fmt:message key="function.confirmpassword"/></td>
			<td><html:password property="confirmpassword"/></td>
		</tr>
	</sakai:group_table>
	<sakai:actions>
		<html:hidden property="action" value="validate"/>
		<html:submit titleKey="button.submit">
			<fmt:message key="button.submit"/>
		</html:submit>
	</sakai:actions>
</html:form>

<!-- me -->
</sakai:html>
