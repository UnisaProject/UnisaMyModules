<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ page import="java.util.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setBundle basename="za.ac.unisa.lms.tools.password.ApplicationResources"/>
<script language="JavaScript">
function setAction() {
		document.getElementById("sbmt").style.visibility='hidden';
		document.getElementById("process").style.display="block";  
	}
</script>
<sakai:html>
<html:form action="/forgotPassword.do"   >
	<sakai:heading><fmt:message key="forget.headingsstep3"/></sakai:heading>
	
	<sakai:messages/>
	<sakai:messages message="true"/>
     <sakai:group_table>
     <tr>
     <td><fmt:message key="forgotpassword.indicate"/></td>
     </tr>
     <tr>
     <td><fmt:message key="forgotpassword.selectOption"/></td>
     </tr>
     <tr>
     <td><html:radio property="foreignChoiceDelivery" value="SMS"> <fmt:message key="sms.label"/></html:radio> &nbsp; 
     <b><bean:write name="forgetPasswordForm" property="cellNum"/></b></td>
     </tr>
      <tr>
     <td>
      <fmt:message key="forgotpassword.or"/></td>
      </tr>
     <tr>
     <td><html:radio property="foreignChoiceDelivery" value="EMAIL"><fmt:message key="email.label"/></html:radio> &nbsp;<html:text property="personalEmail"  styleId="cont"  size="20" maxlength="255"></html:text></td>
     </tr>
     <tr>
     <td><fmt:message key="forgotpassword.assistanceOptio"/></td>
     </tr>
     </sakai:group_table>
	<sakai:actions>
		<html:hidden property="atstep" value="processnewstudent"/>
		
		<html:submit styleClass="active" property="action" onclick="setAction()" styleId="sbmt"><fmt:message key="button.continue"/></html:submit>
			<div id="process" style="display:none">Processing..</div>
   	  <html:submit property="action">
        	<fmt:message key="button.back"/>
	  </html:submit>
	</sakai:actions>

</html:form>
</sakai:html>