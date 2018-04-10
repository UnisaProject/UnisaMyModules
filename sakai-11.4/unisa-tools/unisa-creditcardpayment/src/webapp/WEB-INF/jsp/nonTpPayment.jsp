<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setBundle basename="za.ac.unisa.lms.tools.creditcardpayment.ApplicationResources"/>

<sakai:html>
	<html:form action="/creditCardPayment.do">
	<html:hidden property="page" value="nontpinput"/>
	
	<sakai:heading>
		<fmt:message key="page.heading"/>
	</sakai:heading>
	<sakai:messages/>
	<sakai:messages message="true"/>
	<br>
	
	<sakai:instruction>
		<fmt:message key="page.nontp.info1"/><br/><br/>
		<fmt:message key="page.required.instruction"/>&nbsp;<sakai:required/>
	</sakai:instruction>

	<sakai:group_heading><fmt:message key="page.studentinfo"/> </sakai:group_heading>
	<sakai:group_table>
		<tr>
			<td><strong><fmt:message key="page.studentNr"/></strong></td>
			<td><bean:write name="creditCardPaymentForm" property="student.studentNumber"/></td>
		</tr><tr>
			<td><strong><fmt:message key="page.name"/></strong></td>
			<td><bean:write name="creditCardPaymentForm" property="student.name"/></td>
		</tr><tr>
			<td><strong><fmt:message key="page.qualCode"/></strong></td>
			<td><bean:write name="creditCardPaymentForm" property="qual.qualCode"/>&nbsp;-&nbsp;<bean:write name="creditCardPaymentForm" property="qual.qualDesc"/></td>
		</tr><tr>
			<td><strong><fmt:message key="page.regstatus"/></strong></td>
			<td><bean:write name="creditCardPaymentForm" property="regStatusDescription"/></td>
		</tr><tr>
			<td colspan="2"><sakai:instruction><fmt:message key="page.email.info"/></sakai:instruction></td>
		</tr><tr>
			<td><strong><fmt:message key="page.email"/></strong>&nbsp;<sakai:required/></td>
			<td><html:text property="student.emailAddress"/></td>
		</tr>
	</sakai:group_table>
	
	<sakai:group_heading><fmt:message key="page.accountinfo"/> </sakai:group_heading>
	<sakai:group_table>
	  <tr>
		<td><strong><fmt:message key="page.balance"/></strong></td>
		<td>R&nbsp;<bean:write name="creditCardPaymentForm" property="balance" format="0.00"/>&nbsp;<bean:write name="creditCardPaymentForm" property="creditDebitIndicator"/></td>
		</tr>
		 <tr>
		<td><strong><fmt:message key="page.libfinebalance"/></strong></td>
		<td>R&nbsp;<bean:write name="creditCardPaymentForm" property="libraryFineFee" format="0.00"/>&nbsp;<bean:write name="creditCardPaymentForm" property="libCreditDebitIndicator"/></td>
		</tr>
	</sakai:group_table>
	
	<sakai:group_heading><fmt:message key="page.paymentinfo"/> </sakai:group_heading>
	<sakai:group_table>
		<logic:equal name="creditCardPaymentForm" property="canChooseLibraryCard"  value="true">
		<tr>
			<td><strong><fmt:message key="page.library"/></strong></td>
			<td><html:checkbox property="payLibraryFee">&nbsp;<bean:write name="creditCardPaymentForm" property="libraryFeeText"/></html:checkbox></td>
		</tr>
		</logic:equal>
		<logic:equal name="creditCardPaymentForm" property="canChooseMatric" value="true">
		<tr>
			<td><strong><fmt:message key="page.matric"/></strong><br/><fmt:message key="page.matric.info"/></td>
			<td><html:checkbox property="payMatricFirstAppFee">&nbsp;<bean:write name="creditCardPaymentForm" property="matricFirstAppFeeText"/></html:checkbox></td>
		</tr>
		</logic:equal>
		<logic:equal name="creditCardPaymentForm" property="canChooseThreeGDataBundle"  value="true">
		<tr>
			<td><strong><fmt:message key="page.threeGDataBundle"/></strong></td>
			<td><html:checkbox property="payThreeGDataBundleFee">&nbsp;<bean:write name="creditCardPaymentForm" property="threeGDataBundleFeeText"/></html:checkbox></td>
			<html:hidden property="payThreeGDataBundleFee" value="false"/>
		</tr>
		</logic:equal>
		<tr>
			<td><strong><fmt:message key="page.libfinefeetopay.amount"/></strong>&nbsp;<sakai:required/></td>
			<td>R&nbsp;<html:text property="libraryFineFeeAmountInput" size="8" maxlength="10"/></td>
		</tr>
		<tr>
			<td><strong><fmt:message key="page.studyfee.amount"/></strong>&nbsp;<sakai:required/></td>
			<td>R&nbsp;<html:text property="studyFeeAmountInput" size="8" maxlength="10"/></td>
		</tr>
	</sakai:group_table>
	
	
	<sakai:group_heading><fmt:message key="page.cardinfo"/> </sakai:group_heading>
	<sakai:group_table>
		<tr>
			<td><strong><fmt:message key="page.cc.number"/></strong>&nbsp;<sakai:required/></td>
			<% String cnr="";
			if (request.getParameter("cnumber") != null){
			 cnr = request.getParameter("cnumber");}%>
			<td><input type="text" name="cnumber" size="17" maxlength="16" autocomplete="off" value="<%=cnr%>"/></td>
		</tr><tr>
			<td><strong><fmt:message key="page.cc.holder"/></strong>&nbsp;<sakai:required/></td>
			<td><html:text property="cardHolder" size="30" maxlength="28"/></td>
		</tr><tr>
			<td><strong><fmt:message key="page.cc.expiry"/></strong>&nbsp;<sakai:required/></td>
			<td>MM
				<html:select property="expiryMonth">
 					<html:optionsCollection name="creditCardPaymentForm" property="monthList" value="value" label="label"/>
				</html:select>/
				<html:select property="expiryYear">
				<html:optionsCollection name="creditCardPaymentForm" property="yearList" value="value" label="label"/>
				</html:select>YYYY
			</td>
		</tr><tr>
			<td><strong><fmt:message key="page.budget"/></strong>&nbsp;<sakai:required/></td>
			<td><html:select property="budgetPeriod">
 					<html:optionsCollection name="creditCardPaymentForm" property="budgetList" value="value" label="label"/>
				</html:select></td>
		</tr><tr>
			<% String cvvnr="";
			if (request.getParameter("cvvnumber") != null){
			 cvvnr = request.getParameter("cvvnumber");}%>
			<td><strong><fmt:message key="page.cvv"/></strong>&nbsp;<sakai:required/></td>
			<td><input type="text" name="cvvnumber" size="3" maxlength="3" autocomplete="off" value ="<%=cvvnr%>"/></td>
		</tr><tr>
			<td><strong><fmt:message key="page.total"/>&nbsp;<sakai:required/></strong></td>
			<td>R&nbsp;<html:text property="ccTotalAmountInput" size="8" maxlength="10"/></td>	
		</tr>
	</sakai:group_table>
	<p><font color ="red"><fmt:message key="page.warning"/></font></p>
	<sakai:actions>
		<html:submit property="action"><fmt:message key="button.paynow"/></html:submit>
		<html:submit property="action"><fmt:message key="button.back"/></html:submit>
		<html:submit property="action"><fmt:message key="button.cancel"/></html:submit>
		<html:submit property="action"><fmt:message key="button.logout"/></html:submit>
    </sakai:actions>
	
	</html:form>
</sakai:html> 