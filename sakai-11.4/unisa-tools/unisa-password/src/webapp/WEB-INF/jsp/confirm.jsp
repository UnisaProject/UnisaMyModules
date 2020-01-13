<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ page import="java.util.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setBundle basename="za.ac.unisa.lms.tools.password.ApplicationResources"/>

<sakai:html>
  <html:form action="/forgotPassword.do">
  	<script language="javascript">
		function windowOpen(){
			var url="<bean:write name="forgetPasswordForm" property="mylifePath"/>";
			window.open(url,'mywindow');
			}
		function windowOpen2(){
			var url="<bean:write name="forgetPasswordForm" property="myUnisaPath"/>";
			window.open(url,'mywindow');
			}
	</script>	
      <sakai:group_heading><fmt:message key="forget.finalscrn.heading"/></sakai:group_heading>  <p>
      
     <logic:equal name="forgetPasswordForm" property="confirm" value="nocell">
     <fmt:message key="forget.confirm.nocellinfo" />
     </logic:equal>      
      
      
    <logic:equal name="forgetPasswordForm" property="confirm" value="confirm">
         <logic:equal name="forgetPasswordForm" property="foreignChoiceDelivery" value="EMAIL">
	     <fmt:message key="forget.confirm.info.foreign"/>
	     </logic:equal>
	     <logic:notEqual name="forgetPasswordForm" property="foreignChoiceDelivery" value="EMAIL">
	     <fmt:message key="forget.confirm.info6"/>
	   </logic:notEqual>
	    
    	     <b><bean:write name="forgetPasswordForm" property="studentNr"/></b>. <br><br>
    	     <fmt:message key="forget.confirm.info6a"/> <br><br>
    	     <fmt:message key="forget.confirm.info9" /> 
    	     <a href="javascript:windowOpen2()"> <b><bean:write name="forgetPasswordForm" property="myUnisaPath" filter="false" /></b> </a>
     </logic:equal>
  </html:form>
</sakai:html>
