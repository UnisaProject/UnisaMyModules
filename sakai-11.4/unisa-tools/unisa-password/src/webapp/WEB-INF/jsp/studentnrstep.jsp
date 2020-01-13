<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ page import="java.util.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setBundle basename="za.ac.unisa.lms.tools.password.ApplicationResources"/>

<sakai:html>
<html:form action="/forgotPassword.do"   >
	<sakai:heading><fmt:message key="forget.heading"/></sakai:heading>
	
	<sakai:messages/>
	<sakai:messages message="true"/>
	
	<p>
	<fmt:message key="forget.info1"/>
<p>
	<sakai:group_heading> <fmt:message key="forgotpassword.studentnrstep"/> </sakai:group_heading>
      <input type="text" style="display: none" />
	
		<sakai:group_table>
		<tr>
			<td colspan="2">
				<fmt:message key="forgotpassword.required"/> <sakai:required/>
			</td>
		</tr>
		<tr>
			<td> <fmt:message key="forget.stno"/> <sakai:required/></td>
			<td> <html:text property="studentNr"  styleId="cont"  size="10" maxlength="8"></html:text> </td>
		</tr>
	</sakai:group_table>

	<sakai:actions>
		<html:hidden property="atstep" value="validatestunr"/>
         <html:submit  property="action">	<fmt:message key="button.continue"/>
		</html:submit>
	</sakai:actions>

</html:form>
</sakai:html>