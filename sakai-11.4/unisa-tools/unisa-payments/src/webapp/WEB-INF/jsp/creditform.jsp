<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setBundle basename="za.ac.unisa.lms.tools.payments.ApplicationResources"/>

<sakai:html>
	<html:form action="/CreditCardPayment.do">
	<sakai:heading><fmt:message key="function.headingCredit"/></sakai:heading>
	<sakai:messages/>
	<sakai:messages message="true"/>
	
	<fmt:message key="function.info1"/>
	<fmt:message key="function.info2"/>
	<fmt:message key="function.info3"/>
	<br>
	
	<sakai:instruction>
		<fmt:message key="function.info4"/> <sakai:required/>
	</sakai:instruction>
	<sakai:group_heading> <fmt:message key="function.student"/> </sakai:group_heading>
	<sakai:group_table>
		<tr>
			<td>
				<fmt:message key="function.stno"/>
			</td>
			<td> <bean:write name="creditCardPaymentForm" property="stno"/></td>
			<td>
				<fmt:message key="function.birthdate"/>
			</td>
			<td><bean:write name="creditCardPaymentForm" property="birthDate"/></td>
		</tr>
		<tr>
			<td>
				<fmt:message key="function.degree"/>
			</td>
			<td><bean:write name="creditCardPaymentForm" property="degree"/></td>
		</tr>
		<tr>
			<td>
				<fmt:message key="function.surname"/>
			</td>
			<td> <bean:write name="creditCardPaymentForm" property="surname"/></td>
			<td>
				<fmt:message key="function.title"/>
			</td>
			<td><bean:write name="creditCardPaymentForm" property="title"/></td>
		</tr>
		<tr>
			<td>
				<fmt:message key="function.fullnames"/>
			</td>
			<td> <bean:write name="creditCardPaymentForm" property="firstNames"/></td>
			<td></td>
			<td></td>
		</tr>	
		<tr>
			<td>
				<fmt:message key="function.postaladdress"/>
			</td>
			<logic:iterate id="record" name="creditCardPaymentForm" property="postal" indexId = "i">
				<logic:equal name="i" value="0">
					<td><bean:write name="record"/></td>
				</logic:equal>
				<logic:greaterThan name="i" value="0">
					<tr>
						<td>&nbsp;</td>
						<td><bean:write name="record"/></td>
					</tr>
				</logic:greaterThan>
			</logic:iterate>
			
		</tr>
		<tr>
			<td> </td>
			<td>
				<bean:write name="creditCardPaymentForm" property="postalCode"/>
			</td>
		</tr>	
		<tr> <td> <br><br> </td></tr>
		<tr>
			<td>
				<fmt:message key="function.email"/>
				<sakai:required/>
			</td>
			<td colspan='3'>
				<html:text property="emailAddress"></html:text>
				<b><fmt:message key="function.pleasecheck"/></b>
			</td>
		</tr>	
		<tr>
			<td>
				<fmt:message key="function.home"/>
			</td>
			<td>
				<html:text property="homePhone"></html:text>
			</td>
			<td>
				<fmt:message key="function.cell"/>
			</td>
			<td>
				<html:text property="cell"></html:text>
			</td>
		</tr>	
	</sakai:group_table>
 	<sakai:group_heading> <fmt:message key="function.payment"/> </sakai:group_heading>
	<sakai:group_table>
		<tr>
			<td>
				<fmt:message key="function.method"/>
			</td>
			<td>
				<fmt:message key="function.credit"/>
			</td>
		</tr>
		<tr>
			<td width="200">
				<fmt:message key="function.matric"/>
			</td>
			<td width="250">
				<fmt:message key="function.rand"/>
				<html:text property="matricExempFees" size="10" maxlength="10"></html:text> &nbsp; 
			</td>
			<td width="130">
				<fmt:message key="function.studyfees"/> 
			</td>
			<td width="120">
				<fmt:message key="function.rand"/>
				<html:text property="studyFees" size="10" maxlength="10"></html:text> &nbsp; 
			</td>
		</tr>	

		<tr>
			<td>
				<fmt:message key="function.creditcardnr"/>
				<sakai:required/>
			</td>
			<td colspan='3'>
				<html:text property="creditCardNumber1" size="5" maxlength="4"></html:text> &nbsp; 
				<html:text property="creditCardNumber2" size="5" maxlength="4"></html:text> &nbsp; 
				<html:text property="creditCardNumber3" size="5" maxlength="4"></html:text> &nbsp; 
				<html:password property="creditCardNumber4" size="5" maxlength="4"></html:password> &nbsp; 
			</td>
		</tr>
		<tr>
			<td>
				<fmt:message key="function.cvnnr"/> 
				<sakai:required/>
			</td>
			<td>
				<html:password property="cvNo" size="4" maxlength="3"></html:password>
			</td>
		</tr>					
		<tr>
			<td>
				<fmt:message key="function.confirmcreditcard"/> &nbsp;
				<sakai:required/>
			</td>
			<td colspan='3'>
				<html:text property="confCreditCard1" size="5" maxlength="4"></html:text> &nbsp; 
				<html:text property="confCreditCard2" size="5" maxlength="4"></html:text> &nbsp; 
				<html:text property="confCreditCard3" size="5" maxlength="4"></html:text> &nbsp; 
				<html:password property="confCreditCard4" size="5" maxlength="4"></html:password> &nbsp; 
			</td>
		</tr>
		<tr>
			<td>
				<fmt:message key="function.confirmcvnr"/>
				<sakai:required/> 
			</td>
			<td>	
				<html:password property="confCVno" size="4" maxlength="3"></html:password>
			</td>
		</tr>		
		<tr>
			<td>
				<fmt:message key="function.expire"/>
				<sakai:required/>
			</td>
			<td>
				<fmt:message key="function.month"/>
				<html:text property="expiryMonth" size="3" maxlength="2"></html:text>/
				<html:text property="expiryYear" size="3" maxlength="2"></html:text>
				<fmt:message key="function.year"/>
			</td>
			<td>
				<fmt:message key="function.budgetoption"/> 
				<sakai:required/>
			</td>
			<td>
				<html:select property="budgetOption">
					<html:options collection="answer" property="value" labelProperty="label"/>
				</html:select>	
			</td>
		</tr>	
	</sakai:group_table>
 	<sakai:group_heading> <fmt:message key="function.cardholder"/> </sakai:group_heading>
	<sakai:group_table>		
		<tr>
			<td>
				<fmt:message key="function.surname"/>
				<sakai:required/>
			</td>
			<td>
				<html:text property="cardholderSurname" size="30" maxlength="40"></html:text>
			</td>
			<td>
				<fmt:message key="function.id"/>
				<sakai:required/> 
			</td>
			<td>
				<html:text property="cardHolderId" size="15" maxlength="13"></html:text>
			</td>
		</tr>		
	</sakai:group_table>
	<sakai:actions>
		<html:hidden property="action" value="confirm"/>
		<html:submit titleKey="button.continue">
			<fmt:message key="button.continue"/>
		</html:submit>
		<html:cancel titleKey="button.cancel"/>
	</sakai:actions>
	</html:form>
</sakai:html> 