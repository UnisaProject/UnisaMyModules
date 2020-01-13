<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setBundle basename="za.ac.unisa.lms.tools.creditcardpayment.ApplicationResources"/>

<sakai:html>
	<html:form action="/creditCardPayment.do" >
	<html:hidden property="page" value="tpinput"/>
	
	<sakai:heading><fmt:message key="page.heading"/></sakai:heading>
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
		 <tr>
			<td><strong><fmt:message key="page.minimum.due"/></strong><br/><fmt:message key="page.minimumdue.info"/></td>
			<td>R&nbsp;<bean:write name="creditCardPaymentForm" property="dueImmediately" format="0.00"/></td>
		</tr><tr>
			<td><strong><fmt:message key="page.minimum.regfee"/></strong><br/><fmt:message key="page.minimum.info"/></td>
			<td>R&nbsp;<bean:write name="creditCardPaymentForm" property="minimumForReg" format="0.00"/></td>
		</tr><tr>
			<td><strong><fmt:message key="page.full.amount"/></strong><br/><fmt:message key="page.full.amount.info"/></td>
			<td>R&nbsp;<bean:write name="creditCardPaymentForm" property="fullAccount" format="0.00"/></td>
	</sakai:group_table>
	
	<sakai:group_heading><fmt:message key="page.paymentinfo"/></sakai:group_heading>
	<sakai:group_table>
	    <tr>
			<td><strong><fmt:message key="page.libfinefeetopay.amount"/></strong>&nbsp;<sakai:required/></td>
			<td>R&nbsp;<html:text property="libraryFineFeeAmountInput" size="8" maxlength="10"/></td>
		</tr>
		
		<tr>
		<logic:notEqual name="creditCardPaymentForm" property="minimumStudyFee"  value="0">
			<td><strong><fmt:message key="page.studyfee.amount"/></strong>&nbsp;<sakai:required/><br/><fmt:message key="page.tp.totalamount.info"/>&nbsp;<bean:write name="creditCardPaymentForm" property="minimumStudyFee" format="0.00"/>)</td>
		</logic:notEqual>
		
		<logic:equal name="creditCardPaymentForm" property="minimumStudyFee"  value="0">
			<td><strong><fmt:message key="page.studyfee.amount"/></strong>&nbsp;<sakai:required/></td>
		</logic:equal>
			<td>R&nbsp;<html:text property="studyFeeAmountInput" size="8" maxlength="10"/></td>
		</tr><tr>
			<td><strong><fmt:message key="page.library"/></strong>&nbsp;<sakai:required/><br/><fmt:message key="page.library.info"/></td>
			<td>R&nbsp;<strong><html:text property="formattedLibraryFeeForStudent" disabled="true" size="8"/></strong></td>
		</tr>
		<logic:equal name="creditCardPaymentForm" property="smartCardChoice" value="true">
		<tr>
			<td>&nbsp;</td>
			<td><html:checkbox property="cancel">&nbsp;<fmt:message key="page.smartcard.info1"/>&nbsp;R<bean:write name="creditCardPaymentForm" property="formattedLibraryFeeForStudent"/>&nbsp;<fmt:message key="page.smartcard.info2"/></html:checkbox></td>
		</tr>
		</logic:equal>
		<tr>
			<td><strong><fmt:message key="page.matric"/></strong>&nbsp;<sakai:required/><br/><fmt:message key="page.matric.info"/></td>
			<td>R&nbsp;<html:text property="formattedMatricFeeForStudent" disabled="true" size="8"/></td>
		</tr>
	</sakai:group_table>
	
	
	<sakai:group_heading><fmt:message key="page.cardinfo"/></sakai:group_heading>
	<sakai:group_table>
		<tr>
			<td><strong><fmt:message key="page.cc.number"/></strong>&nbsp;<sakai:required/></td>
			<% String cnr="";
			if (request.getParameter("cnumber") != null){
			 cnr = request.getParameter("cnumber");}%>
			<td><input type="text" name="cnumber" size="17" maxlength="16" autocomplete="off" value="<%=cnr%>"/></td>
		</tr><tr>
			<td><strong><fmt:message key="page.cc.holder"/></strong>&nbsp;<sakai:required/><br/><fmt:message key="page.cc.oncard"/></td>
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
			<td><strong><fmt:message key="page.total"/>&nbsp;</strong><sakai:required/><br/><fmt:message key="page.totalamount.info"/></td>
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