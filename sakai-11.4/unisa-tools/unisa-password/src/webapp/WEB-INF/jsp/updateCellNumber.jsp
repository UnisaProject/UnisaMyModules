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
<html:form action="/forgotPassword.do" >
	<sakai:group_heading><fmt:message key="forget.deliverymethod.heading"/></sakai:group_heading> <br>
	<sakai:messages/>
	<sakai:messages message="true"/>
	
	<fmt:message key="forgot.updatecellnr.info1"/> <br> <br> 
	<fmt:message key="forgot.updatecellnr.info2"/><br>
     <sakai:group_table>	 
	 <tr>
	 <td><fmt:message key="forgot.updatecellnr.selectrequest"/> <sakai:required/></td>
	 <td>	
	 <html:select name="forgetPasswordForm"  property="selectRequest">
	 <html:option  value="Update cell number">Update cell number</html:option>
	 <html:option  value="No cell number available">No cell number available</html:option>
	 </html:select>
	 </td>
	 </tr>
	 <tr>
	 <td><fmt:message key="forgot.updatecellnr.message"/> <sakai:required/></td>
	 <td><html:text property="message" size="20" maxlength="100"></html:text></td>
	 </tr>
	 <tr>
	 <td><fmt:message key="forgot.updatecellnr.altcontact"/> <sakai:required/></td>
	 <td><html:text property="altContactDetails" size="20" maxlength="50"></html:text></td>
	 </tr>
	 </sakai:group_table>
	<fmt:message key="forgot.updatecellnr.info3"/>
		
	<sakai:actions>
		<html:hidden property="atstep" value="updateCellNumber"/>
			<html:submit styleClass="active" property="action" onclick="setAction()" styleId="sbmt"><fmt:message key="button.continue"/></html:submit>
			<div id="process" style="display:none">Processing..</div>
	  <html:submit property="action">
        	<fmt:message key="button.back"/>
	  </html:submit>
	</sakai:actions>

</html:form>
</sakai:html>