<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ page import="java.util.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setBundle basename="za.ac.unisa.lms.tools.password.ApplicationResources"/>

<sakai:html>
  <html:form action="/forgotPassword.do" >
  	<script language="javascript">
		function windowOpen(){
			var url="<bean:write name="forgetPasswordForm" property="myUnisaPath"/>";
			window.open(url,'mywindow');
			}
	</script>
	
   <sakai:group_heading><fmt:message key="forgot.unisa.password.heading"/></sakai:group_heading> <br>
    <fmt:message key="forget.confirm.info1"/>  <br> <br>
  <fmt:message key="forgot.unisa.password.instr1"/> <font color="red"><bean:write name="forgetPasswordForm" property="password"/></font>.<br><br>
   <fmt:message key="forget.confirm.info2"/> <br>
   <fmt:message key="forget.confirm.info3"/> <br>
 <fmt:message key="forget.confirm.info3a"/> <br><br>
 <fmt:message key="forget.confirm.info3b"/> 

  <fmt:message key="forget.confirm.info9"/><a href="javascript:windowOpen()"> 
  	<b><bean:write name="forgetPasswordForm" property="myUnisaPath" filter="false"/></b> </a>

  </html:form>
</sakai:html>
