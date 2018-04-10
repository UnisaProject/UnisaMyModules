<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setBundle basename="za.ac.unisa.lms.tools.creditcardpayment.ApplicationResources"/>


<script type="text/javascript">
function hide(divID) {
  var item = document.getElementById(divID);
  if (item) {
    item.style.display='none';
  }
}
</script>

<sakai:html>
	<html:form action="/creditCardPayment.do">
	<html:hidden property="page" value="qualinput"/>
	<sakai:heading><fmt:message key="page.heading"/></sakai:heading>
	<sakai:messages/>
	<sakai:messages message="true"/>
	<br>
	
	<sakai:instruction>
	<fmt:message key="page.input.info3"/>
	</sakai:instruction>
	<sakai:group_table>
		<tr>
			<td><strong><fmt:message key="page.studentNr"/></strong></td>
			<td><bean:write name="creditCardPaymentForm" property="student.studentNumber"/></td>
		</tr>
		<tr>
		<logic:notEmpty name="creditCardPaymentForm" property="qual.qualDesc">
			<td><strong><fmt:message key="page.qualCode"/></strong></td>
			<td><html:text property="qual.qualCode" size="5" maxlength="5" onchange="hide('desc')"/><div style="display:inline" id="desc">&nbsp;-&nbsp;<bean:write name="creditCardPaymentForm" property="qual.qualDesc"/></div></td>
		</logic:notEmpty> 
		<logic:empty name="creditCardPaymentForm" property="qual.qualDesc">
			<td><strong><fmt:message key="page.qualCode"/></strong></td>
			<td><html:text property="qual.qualCode" size="5" maxlength="5"/></td>
		</logic:empty>
		</tr>
	</sakai:group_table>
	
	<sakai:actions>
		<sakai:actions>
			<html:submit property="action"><fmt:message key="button.continue"/></html:submit>
			<logic:equal name="creditCardPaymentForm" property="studentUser" value="false">
				<html:submit property="action"><fmt:message key="button.back"/></html:submit>
			</logic:equal>
			<html:submit property="action"><fmt:message key="button.cancel"/></html:submit>
		</sakai:actions>
	</sakai:actions>
	</html:form>
</sakai:html> 